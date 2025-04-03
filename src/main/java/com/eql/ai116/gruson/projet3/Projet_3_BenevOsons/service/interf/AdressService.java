package com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.service.interf;

import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.Adress;

public interface AdressService {

    public String formatAddress(Adress adress);
    public Adress adressWithLatLon(Adress adress);
    public double extractLatitude(String geoJson);
    public double extractLongitude(String geoJson);

    }
