package com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.service.impl;

import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.Adress;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.Mission;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.Period;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.SkillTypes;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.repository.SearchRepository;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.service.interf.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

    SearchRepository searchRepository;

    @Override
    public List<Mission> searchByCity(String city) {
        return searchRepository.findByCity(city);
    }

    @Override
    public List<Mission> searchBySkills(Long skillTypeId) {
        return searchRepository.findBySkillTypeId(skillTypeId);
    }

    @Override
    public List<Mission> searchByPeriod(Period period) {
        return List.of();
    }

    @Override
    public List<Mission> searchByDistance(Adress adress) {
        return List.of();
    }

    ///  Setter
    @Autowired
    public void setSearchRepository(SearchRepository searchRepository) {
        this.searchRepository = searchRepository;
    }
}
