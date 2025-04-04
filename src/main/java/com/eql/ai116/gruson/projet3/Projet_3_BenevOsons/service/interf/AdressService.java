package com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.service.interf;

import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.Adress;

public interface AdressService {

     String formatAddress(Adress adress);
     Adress adressWithLatLon(Adress adress);
     double extractLatitude(String geoJson);
     double extractLongitude(String geoJson);

    }
