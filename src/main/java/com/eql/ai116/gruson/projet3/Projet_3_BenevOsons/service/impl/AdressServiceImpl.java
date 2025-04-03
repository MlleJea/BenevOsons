package com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.service.impl;

import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.Adress;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AdressServiceImpl {

    private static final String API_URL = "https://api-adresse.data.gouv.fr/search/?q=";
    Logger logger = LogManager.getLogger();


    public String formatAddress(Adress adress) {
        return adress.getStreetNumber() + "+" + adress.getStreetName() + "&postcode=" + adress.getPostalCode();
    }

    public Adress adressWithLatLon(Adress adress) {

        String adressToString = formatAddress(adress);
        String url = API_URL + adressToString;
        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<String> reponse = restTemplate.getForEntity(url, String.class);

            String geoJSON = reponse.getBody();
            Double latitude = extractLatitude(geoJSON);
            Double longitude = extractLongitude(geoJSON);

            adress.setLatitude(latitude);
            adress.setLongitude(longitude);
        } catch (Exception e) {
            logger.info("Impossible d'accéder à l'adresse "+ adressToString);
        }
        return adress;
    }

    private double extractLatitude(String geoJson) {
        int startIndex = geoJson.indexOf(",", geoJson.indexOf("\"coordinates\":[")) + 1;
        int endIndex = geoJson.indexOf("]", startIndex);
        return Double.parseDouble(geoJson.substring(startIndex, endIndex));
    }

    private double extractLongitude(String geoJson) {
        int startIndex = geoJson.indexOf("\"coordinates\":[") + 15;
        int endIndex = geoJson.indexOf(",", startIndex);
        return Double.parseDouble(geoJson.substring(startIndex, endIndex));
    }
}