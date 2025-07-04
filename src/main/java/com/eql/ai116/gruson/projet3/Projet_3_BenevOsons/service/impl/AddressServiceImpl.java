package com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.service.impl;

import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.Address;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.repository.AddressRepository;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.service.interf.AddressService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;


@Service
public class AddressServiceImpl implements AddressService {

    private static final String API_URL = "https://api-adresse.data.gouv.fr/search/?q=";
    private static final int EARTH_RADIUS_KM = 6371;

    Logger logger = LogManager.getLogger();

    @Autowired
    private AddressRepository addressRepository;


    @Override
    public String formatAddress(Address address) {
        return address.getStreetNumber() + "+" + address.getStreetName() + "&postcode=" + address.getPostalCode();
    }

    @Override
    public void processAddress(List<Address> addresses) {
        for (Address address : addresses) {
            Address addressWithLatLon = addressWithLatLon(address);
            Optional<Address> addressIsAlreadyExisting = addressRepository.findFirstByLatitudeAndLongitude(
                    addressWithLatLon.getLatitude(), addressWithLatLon.getLongitude());
            if (addressIsAlreadyExisting.isEmpty()) {
                addressRepository.save(addressWithLatLon);
            } else {
                address.setAddressId(addressIsAlreadyExisting.get().getAddressId());
            }
        }
    }
    @Override
    public Address cityWithLatLon(String postalCode){

        Address address = new Address();
        String url = API_URL + postalCode;
        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            String geoJSON = response.getBody();
            Double latitude = extractLatitude(geoJSON);
            Double longitude = extractLongitude(geoJSON);

            address.setLatitude(latitude);
            address.setLongitude(longitude);
        } catch (Exception e) {
            logger.error("Erreur lors de la récupération des coordonnées pour le code postal " + postalCode, e);
        }

        return address;
    }

    @Override
    public Address addressWithLatLon(Address address) {

        String addressToString = formatAddress(address);
        String url = API_URL + addressToString;
        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            String geoJSON = response.getBody();
            Double latitude = extractLatitude(geoJSON);
            Double longitude = extractLongitude(geoJSON);

            address.setLatitude(latitude);
            address.setLongitude(longitude);
        } catch (Exception e) {
            logger.info("Impossible d'accéder à l'adresse "+ addressToString);
        }
        return address;
    }

    @Override
    public double extractLatitude(String geoJson) {
        int startIndex = geoJson.indexOf(",", geoJson.indexOf("\"coordinates\":[")) + 1;
        int endIndex = geoJson.indexOf("]", startIndex);
        return Double.parseDouble(geoJson.substring(startIndex, endIndex));
    }

    @Override
    public double extractLongitude(String geoJson) {
        int startIndex = geoJson.indexOf("\"coordinates\":[") + 15;
        int endIndex = geoJson.indexOf(",", startIndex);
        return Double.parseDouble(geoJson.substring(startIndex, endIndex));
    }

    @Override
    public double haversine(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.pow(Math.sin(dLat / 2), 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.pow(Math.sin(dLon / 2), 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS_KM * c;
    }
}