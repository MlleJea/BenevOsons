package com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.service.interf;

import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.exception.AuthenticationFailedException;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.exception.EmailAlreadyExistsException;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.exception.InvalidCredentialsException;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.exception.InvalidPasswordException;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.exception.PasswordDontMatchException;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.exception.RegistrationFailedException;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.dto.AuthenticationDto;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.dto.RegistrationDto;
import com.eql.ai116.gruson.projet3.Projet_3_BenevOsons.entity.dto.UserDto;

public interface SecurityService {
   UserDto register(RegistrationDto registrationDto) throws EmailAlreadyExistsException, RegistrationFailedException, PasswordDontMatchException, InvalidPasswordException;
   UserDto authenticate(AuthenticationDto authenticationDto) throws AuthenticationFailedException, InvalidCredentialsException;
}