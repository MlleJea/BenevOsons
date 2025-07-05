package com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.service.impl;

import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.Address;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.Mission;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.dto.MissionSearchCriteriaDTO;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.exception.ResourceNotFoundException;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.repository.SearchRepository;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.repository.UserRepository;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.service.interf.AddressService;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.service.interf.SearchService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

    private Logger logger = LogManager.getLogger();

    private SearchRepository searchRepository;
    private AddressService addressService;
    private UserRepository userRepository;

    @Override
    public List<Mission> searchMissionsWithFilters(MissionSearchCriteriaDTO criteria) {

        List<Mission> missions = searchRepository.findMissionsWithFilters(null, criteria.getSkillTypeIds(), criteria.getStartDate(), criteria.getEndDate());

        Double searchLat = criteria.getUserLatitude();
        Double searchLon = criteria.getUserLongitude();


        if ((searchLat == null || searchLon == null) && criteria.getPostalCode() != null && criteria.getRadiusKm() != null) {
            Address center = addressService.cityWithLatLon(criteria.getPostalCode());
            searchLat = center.getLatitude();
            searchLon = center.getLongitude();
        }


        if (searchLat != null && searchLon != null && criteria.getRadiusKm() != null) {
            final double lat = searchLat;
            final double lon = searchLon;

            missions = missions.stream().filter(mission -> {
                Address missionAddress = mission.getAddress();
                if (missionAddress == null || missionAddress.getLatitude() == null || missionAddress.getLongitude() == null) {
                    return false;
                }
                double distance = addressService.haversine(lat, lon, missionAddress.getLatitude(), missionAddress.getLongitude());
                return distance <= criteria.getRadiusKm();
            }).sorted(Comparator.comparingDouble(mission -> {
                Address missionAddress = mission.getAddress();
                return addressService.haversine(lat, lon, missionAddress.getLatitude(), missionAddress.getLongitude());
            })).toList();
        }

        logger.info("Recherche terminée. {} mission(s) trouvée(s)", missions.size());
        return missions;
    }

    @Override
    public List<Address> getVolunteerAddresses(Long id) {
        List<Address> addresses = userRepository.findUserAddressesByUserId(id);
        if (addresses.isEmpty()) {
            throw new ResourceNotFoundException("Aucune adresse trouvée pour l'utilisateur avec l'ID : " + id);
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
