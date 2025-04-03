package com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.service.impl;

import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.Adress;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.Organization;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.Skill;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.SkillTypes;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.User;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.Volunteer;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.dto.RegistrationDto;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.dto.SkillDto;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.security.RoleName;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.repository.SkillRepository;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.repository.SkillTypesRepository;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.repository.UserRepository;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.repository.VolonteerRepository;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.service.interf.SpaceService;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class SpaceServiceImpl implements SpaceService {

    Logger logger = LogManager.getLogger();

    /// Attributs
    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;
    private VolonteerRepository volonteerRepository;
    private SkillTypesRepository skillTypesRepository;
    private SkillRepository skillRepository;


    /// User
    @Transactional
    @Override
    public User displayUser(Long id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()) {
            User userToDisplay = user.get();
            List<Adress> addresses = userRepository.findUserAdressesByUserId(id);
            userToDisplay.setUserAdressList(addresses);

            return userToDisplay;
        }
        throw new RuntimeException("Utilisateur non trouvé avec l'ID : " + id);
    }

    @Transactional
    @Override
    public ResponseEntity<Object> updateUser(Long id, RegistrationDto registrationDto) {
        logger.info("Début de la mise à jour de l'utilisateur avec l'ID : " + id);

        try {
            // Vérifier si l'utilisateur existe
            Optional<User> optionalUser = userRepository.findById(id);
            if (!optionalUser.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utilisateur non trouvé.");
            }

            User user = optionalUser.get();

            if (registrationDto.getName() != null) {
                user.setName(registrationDto.getName());
            }

            if (registrationDto.getPassword() != null) {
                user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));
            }

            if (registrationDto.getPhoneNumber() != null) {
                user.setPhoneNumber(registrationDto.getPhoneNumber());
            }

            if (registrationDto.getAdressList()!= null && !registrationDto.getAdressList().isEmpty()) {
                user.setUserAdressList(registrationDto.getAdressList());
            }

            // Volunteer
            if (user instanceof Volunteer && registrationDto.getRoleName() == RoleName.VOLUNTEER) {
                Volunteer volunteer = (Volunteer) user;
                if (registrationDto.getFirstName() != null) {
                    volunteer.setFirstName(registrationDto.getFirstName());
                }
                if (registrationDto.getBirthDate() != null) {
                    volunteer.setBirthdate(registrationDto.getBirthDate());
                }
            }

            // Organization
            if (user instanceof Organization && registrationDto.getRoleName() == RoleName.ORGANIZATION) {
            }
            userRepository.save(user);

            logger.info("Mise à jour réussie de l'utilisateur avec l'ID : " + id);
            return ResponseEntity.status(HttpStatus.OK).body("Mise à jour effectuée avec succès.");

        } catch (Exception e) {
            logger.error("Erreur lors de la mise à jour de l'utilisateur avec l'ID : " + id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Une erreur est survenue lors de la mise à jour.");
        }
    }


    /// Skills
    @Override
    public List<Skill> displaySkill(Long id) {
        return volonteerRepository.findUserSkillsByUserId(id);
    }

    @Override
    public List<SkillTypes> displaySkillTypes() {
        return skillTypesRepository.findAll();
    }

    @Transactional
    @Override
    public Skill addNewSkill(SkillDto skillDto) {
        Skill skill = new Skill();
        skill.setLabelSkill(skillDto.getLabelSkill());
        skill.setGrade(skillDto.getGrade());

        String skillLabel = skillDto.getSkillTypeLabel();

        SkillTypes skillTypes;
        if (skillTypesRepository.existsByLabel(skillLabel)) {
            skillTypes = skillTypesRepository.findByLabel(skillLabel);
        } else {
            skillTypes = new SkillTypes();
            skillTypes.setLabel(skillLabel);
            skillTypes = skillTypesRepository.save(skillTypes);
        }
        skill.setSkillType(skillTypes);
        Optional<Volunteer> volunteer = volonteerRepository.findById(skillDto.getVolunteerId());

        if(!volunteer.isEmpty()) {
            skill.setSkillsVolunteerList(Collections.singletonList(volunteer.get()));
            List<Skill> skills = volunteer.get().getVolunteerSkillsList();
            skills.add(skill);
            volunteer.get().setVolunteerSkillsList(skills);
        }
        return skillRepository.save(skill);
    }

    @Transactional
    @Override
    public Skill updateSkill(SkillDto skillDto) {
        return null;
    }

    @Override
    public Boolean deleteSkill(Long id) {
        return null;
    }


    /// Setters
    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setVolonteerRepository(VolonteerRepository volonteerRepository) {
        this.volonteerRepository = volonteerRepository;
    }

    @Autowired
    public void setSkillTypesRepository(SkillTypesRepository skillTypesRepository) {
        this.skillTypesRepository = skillTypesRepository;
    }

    @Autowired
    public void setSkillRepository(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }
}
