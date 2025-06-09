package com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.service.impl;

import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.Address;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.Mission;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.dto.MissionSearchCriteriaDTO;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.exception.ResourceNotFoundException;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.repository.SearchRepository;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.repository.UserRepository;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.service.interf.AddressService;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.service.interf.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

    SearchRepository searchRepository;
    AddressService addressService;
    UserRepository userRepository;

    @Override
    public List<Mission> searchMissionsWithFilters(MissionSearchCriteriaDTO criteria) {
        List<Mission> missions = searchRepository.findMissionsWithFilters(
                criteria.getCity(),
                criteria.getSkillTypeIds(),
                criteria.getStartDate(),
                criteria.getEndDate()
        );
        if (criteria.getUserLatitude() != null &&
                criteria.getUserLongitude() != null &&
                criteria.getRadiusKm() != null) {

            missions = missions.stream()
                    .filter(mission -> {
                        if (mission.getAddress().getLatitude() == null || mission.getAddress().getLongitude() == null) {
                            return false;
                        }
                        double distance = addressService.haversine(criteria.getUserLatitude(),
                                criteria.getUserLongitude(),
                                mission.getAddress().getLatitude(),
                                mission.getAddress().getLongitude());
                        return distance <= criteria.getRadiusKm();
                    })
                    .sorted(Comparator.comparingDouble(mission ->
                            addressService.haversine(
                                    criteria.getUserLatitude(),
                                    criteria.getUserLongitude(),
                                    mission.getAddress().getLatitude(),
                                    mission.getAddress().getLongitude())))
                    .toList();
        }

        return missions;
    }

    @Override
    public List<Address> getVolunteerAddresses(Long id){
        List<Address> addresses = userRepository.findUserAddressesByUserId(id);
        if(addresses.isEmpty()){
            throw  new ResourceNotFoundException("Aucune adresse trouvée pour l'utilisateur avec l'ID : " +id);
        }
        return addresses;
    }

    ///  Setter
    @Autowired
    public void setSearchRepository(SearchRepository searchRepository) {
        this.searchRepository = searchRepository;
    }

    @Autowired
    public void setAddressService(AddressService addressService) {
        this.addressService = addressService;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
