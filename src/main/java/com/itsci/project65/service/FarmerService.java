package com.itsci.project65.service;

import com.itsci.project65.model.Farmer;
import com.itsci.project65.dto.LoginRequest;
import com.itsci.project65.dto.LoginResponse;
import org.springframework.web.multipart.MultipartFile;

public interface FarmerService {
    Farmer createFarmer(Farmer farmer, MultipartFile file);
    Farmer updateFarmer(int farmerId, Farmer farmer, MultipartFile file);
    Farmer getFarmerById(int farmerId);
    void deleteFarmer(int farmerId);
    Farmer login(String username, String password);
    LoginResponse authenticateAndGenerateToken(LoginRequest loginRequest);
}
