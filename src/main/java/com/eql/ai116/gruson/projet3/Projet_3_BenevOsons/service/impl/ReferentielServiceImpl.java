package com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.service.impl;

import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.Grade;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.SkillTypes;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.repository.SkillTypesRepository;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.service.interf.ReferentielService;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.crypto.dsig.Reference;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReferentielServiceImpl implements ReferentielService {

    private SkillTypesRepository skillTypesRepository;


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

    @Autowired
    public void setSkillTypesRepository(SkillTypesRepository skillTypesRepository) {
        this.skillTypesRepository = skillTypesRepository;
    }
}
