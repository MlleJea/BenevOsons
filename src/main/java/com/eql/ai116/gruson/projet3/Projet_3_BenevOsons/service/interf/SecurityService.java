package com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.service.interf;

import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.exception.AuthenticationFailedException;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.exception.EmailAlreadyExistsException;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.exception.RegistrationFailedException;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.dto.AuthenticationDto;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.dto.RegistrationDto;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.dto.UserDto;

public interface SecurityService {
   UserDto register(RegistrationDto registrationDto) throws EmailAlreadyExistsException, RegistrationFailedException;
   UserDto authenticate(AuthenticationDto authenticationDto) throws AuthenticationFailedException;
}