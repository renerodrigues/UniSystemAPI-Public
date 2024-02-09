package com.uniSystemAPI.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.uniSystemAPI.domain.usuario.UsuarioRepository;

@Service
public class AuthorizationService implements UserDetailsService {

    @Autowired
    UsuarioRepository  repository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByLoginIgnoreCase(username);
    }
}
