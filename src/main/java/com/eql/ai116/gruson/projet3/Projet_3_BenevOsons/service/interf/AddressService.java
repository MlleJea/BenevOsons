package com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.service.interf;

import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.Address;

public interface AddressService {

     String formatAddress(Address address);
     Address addressWithLatLon(Address address);
     Address cityWithLatLon (String postalCode);
     double extractLatitude(String geoJson);
     double extractLongitude(String geoJson);
     double haversine(double lat1, double lon1, double lat2, double lon2);

    }
