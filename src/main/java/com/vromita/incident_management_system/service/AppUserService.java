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

    /**
     * Method that return the user by the given username
     * @param username the username identifying the user whose data is required.
     * @throws UsernameNotFoundException if no user is found with the given username
     * @return the user
     */
    @Override
    public UserDetails loadUserByUsername(String username){

        return appUserRepository.findByUsername(username).orElseThrow(() ->
        new UsernameNotFoundException("User not found with username: " + username));
    }


}
