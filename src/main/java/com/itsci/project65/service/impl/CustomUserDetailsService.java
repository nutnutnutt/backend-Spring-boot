package com.itsci.project65.service.impl;

import com.itsci.project65.repository.EquipmentOwnerRepository;
import com.itsci.project65.repository.FarmerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private EquipmentOwnerRepository equipmentOwnerRepository;

    @Autowired
    private FarmerRepository farmerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // First, try to find the user as an EquipmentOwner
        UserDetails user = equipmentOwnerRepository.findByOwnerUserName(username);
        if (user != null) {
            return user;
        }

        // If not found, try to find the user as a Farmer
        user = farmerRepository.findByFarmerUserName(username);
        if (user != null) {
            return user;
        }

        // If user is not found in either repository, throw an exception
        throw new UsernameNotFoundException("User not found with username: " + username);
    }
}
