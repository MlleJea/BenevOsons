package com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.service.interf;

import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.dto.AuthenticationDto;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.dto.RegistrationDto;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.dto.UserDto;
import org.springframework.http.ResponseEntity;

public interface SecurityService {
   ResponseEntity<Object> register(RegistrationDto registrationDto);

   ResponseEntity<Object> authenticate(AuthenticationDto authenticationDto);

}
