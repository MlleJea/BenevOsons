package com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.service.impl;

import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.Adress;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.Mission;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.Period;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.dto.MissionSearchCriteriaDTO;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.repository.SearchRepository;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.service.interf.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

    SearchRepository searchRepository;

    @Override
    public List<Mission> searchMissionsWithFilters(MissionSearchCriteriaDTO criteria) {
        return searchRepository.findMissionsWithFilters(
                criteria.getCity(),
                criteria.getSkillTypeIds(),
                criteria.getStartDate(),
                criteria.getEndDate());
    }

    ///  Setter
    @Autowired
    public void setSearchRepository(SearchRepository searchRepository) {
        this.searchRepository = searchRepository;
    }
}
