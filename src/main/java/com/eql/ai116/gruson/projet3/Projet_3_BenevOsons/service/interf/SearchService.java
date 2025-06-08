package com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.service.interf;

import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.Mission;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.dto.MissionSearchCriteriaDTO;

import java.util.List;

public interface SearchService {

    List<Mission> searchMissionsWithFilters(MissionSearchCriteriaDTO criteria);

}
