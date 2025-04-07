package com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.service.impl;

import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.Adress;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.Grade;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.Organization;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.Skill;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.SkillTypes;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.User;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.Volunteer;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.dto.RegistrationDto;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.dto.SkillDto;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.security.RoleName;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.exception.ResourceNotFoundException;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.repository.SkillRepository;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.repository.SkillTypesRepository;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.repository.UserRepository;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.repository.VolonteerRepository;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.service.interf.SpaceService;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SpaceServiceImpl implements SpaceService {

    Logger logger = LogManager.getLogger();

    // Attributs
    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;
    private VolonteerRepository volonteerRepository;
    private SkillTypesRepository skillTypesRepository;
    private SkillRepository skillRepository;

    /// USER
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
        throw new ResourceNotFoundException("Utilisateur non trouvé avec l'ID : " + id);
    }

    @Transactional
    @Override
    public String updateUser(Long id, RegistrationDto registrationDto) {
        logger.info("Début de la mise à jour de l'utilisateur avec l'ID : " + id);

        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent()) {
            throw new ResourceNotFoundException("Utilisateur non trouvé avec l'ID : " + id);
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

        if (registrationDto.getAdressList() != null && !registrationDto.getAdressList().isEmpty()) {
            user.setUserAdressList(registrationDto.getAdressList());
        }

        if (user instanceof Volunteer volunteer && registrationDto.getRoleName() == RoleName.VOLUNTEER) {
            if (registrationDto.getBirthDate() != null) {
                volunteer.setBirthdate(registrationDto.getBirthDate());
            }
        }

        if (user instanceof Organization organization && registrationDto.getRoleName() == RoleName.ORGANIZATION) {
            if (registrationDto.getRna() != null) {
                organization.setrna(registrationDto.getRna());
            }
        }
        userRepository.save(user);

        logger.info("Mise à jour réussie de l'utilisateur avec l'ID : " + id);
        return "Mise à jour effectuée avec succès.";
    }

    /// SKILLS
    @Override
    public List<Skill> displaySkill(Long id) {
        List<Skill> skills = volonteerRepository.findUserSkillsByUserId(id);
        if (skills.isEmpty()) {
            throw new ResourceNotFoundException("Aucune compétence trouvée pour l'utilisateur avec l'ID : " + id);
        }
        return skills;
    }

    @Override
    public List<SkillTypes> displaySkillTypes() {
        return skillTypesRepository.findAll();
    }

    @Override
    public List<String> displayGrades() {

        return Arrays.asList(Grade.values()).stream()
                .map(Grade::getNumber)
                .collect(Collectors.toList());
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
        if (volunteer.isPresent()) {
            Volunteer vol = volunteer.get();
            skill.setSkillsVolunteerList(Collections.singletonList(vol));
            List<Skill> skills = vol.getVolunteerSkillsList();
            skills.add(skill);
            vol.setVolunteerSkillsList(skills);
        } else {
            throw new ResourceNotFoundException("Volontaire non trouvé avec l'ID : " + skillDto.getVolunteerId());
        }
        return skillRepository.save(skill);
    }

    @Transactional
    @Override
    public Skill updateSkill(SkillDto skillDto) {
        Optional<Skill> optionalSkill = skillRepository.findById(skillDto.getSkillId());
        if (!optionalSkill.isPresent()) {
            throw new ResourceNotFoundException("Compétence non trouvée avec l'ID : " + skillDto.getSkillId());
        }
        Skill skillToUpdate = optionalSkill.get();
        skillToUpdate.setLabelSkill(skillDto.getLabelSkill());
        skillToUpdate.setGrade(skillDto.getGrade());

        String skillTypeLabel = skillDto.getSkillTypeLabel();
        SkillTypes skillTypes;
        if (skillTypesRepository.existsByLabel(skillTypeLabel)) {
            skillTypes = skillTypesRepository.findByLabel(skillTypeLabel);
            skillToUpdate.setSkillType(skillTypes);
        } else {
            skillTypes = new SkillTypes();
            skillTypes.setLabel(skillTypeLabel);
            skillTypes = skillTypesRepository.save(skillTypes);
            skillToUpdate.setSkillType(skillTypes);
        }

        Optional<Volunteer> optionalVolunteer = volonteerRepository.findById(skillDto.getVolunteerId());
        if (!optionalVolunteer.isPresent()) {
            throw new ResourceNotFoundException("Volontaire non trouvé avec l'ID : " + skillDto.getVolunteerId());
        }
        Volunteer volunteer = optionalVolunteer.get();
        if (skillToUpdate.getSkillsVolunteerList() == null || !skillToUpdate.getSkillsVolunteerList().contains(volunteer)) {
            skillToUpdate.setSkillsVolunteerList(Collections.singletonList(volunteer));
            volunteer.getVolunteerSkillsList().add(skillToUpdate);
        }

        return skillRepository.save(skillToUpdate);
    }

    @Override
    public Boolean deleteSkill(Long id) {
        Optional<Skill> optionalSkill = skillRepository.findById(id);
        if (optionalSkill.isPresent()) {
            skillRepository.deleteById(id);
            return true;
        }
        return false;
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