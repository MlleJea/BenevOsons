package com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.service.interf;

import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.Adress;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.Mission;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.Period;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.SkillTypes;

import java.util.List;

public interface SearchService {

    List<Mission> searchByCity (String city);

    List<Mission> searchBySkills (Long skillTypeId);

    List<Mission> searchByPeriod (Period period);

    List<Mission> searchByDistance (Adress adress);
}
