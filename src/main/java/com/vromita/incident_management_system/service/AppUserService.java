package com.vromita.incident_management_system.service;

import com.vromita.incident_management_system.repository.AppUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AppUserService implements UserDetailsService {

    private final AppUserRepository appUserRepository;

    public AppUserService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username){

        return appUserRepository.findByUsername(username).orElseThrow(() ->
        new UsernameNotFoundException("User not found with username: " + username));
    }


}
