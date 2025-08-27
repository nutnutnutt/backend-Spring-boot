package com.itsci.project65.service.impl;

import com.itsci.project65.model.Farmer;
import com.itsci.project65.repository.FarmerRepository;
import com.itsci.project65.service.FarmerService;
import com.itsci.project65.dto.LoginRequest;
import com.itsci.project65.dto.LoginResponse;
import com.itsci.project65.util.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FarmerServiceImpl implements FarmerService {
    @Autowired
    FarmerRepository farmerRepository;
    
    @Autowired
    JwtUtil jwtUtil;

    private final Path root = Paths.get("uploads/images");

    public FarmerServiceImpl() {
        try {
            Files.createDirectories(root);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    private String saveImage(MultipartFile file) {
        try {
            String filename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            Files.copy(file.getInputStream(), this.root.resolve(filename));
            return filename;
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }

    private void deleteImage(String filename) {
        try {
            if (filename != null && !filename.isEmpty()) {
                Path fileToDelete = root.resolve(filename);
                Files.deleteIfExists(fileToDelete);
            }
        } catch (IOException e) {
            // Log the error or handle it as needed
            System.err.println("Could not delete the file: " + filename + ". Error: " + e.getMessage());
        }
    }

    @Override
    public Farmer createFarmer(Farmer farmer, MultipartFile file) {
        if (file != null && !file.isEmpty()) {
            String filename = saveImage(file);
            farmer.setFarmerImg(filename);
        }
        return farmerRepository.save(farmer);
    }

    @Override
    public Farmer updateFarmer(int farmerId, Farmer farmerDetails, MultipartFile file) {
        Farmer existingFarmer = getFarmerById(farmerId);

        if (file != null && !file.isEmpty()) {
            deleteImage(existingFarmer.getFarmerImg());
            String newFilename = saveImage(file);
            existingFarmer.setFarmerImg(newFilename);
        }

        existingFarmer.setFarmerUserName(farmerDetails.getFarmerUserName());
        existingFarmer.setFarmerPassword(farmerDetails.getFarmerPassword());
        existingFarmer.setFarmerCFPassword(farmerDetails.getFarmerCFPassword());
        existingFarmer.setFarmerEmail(farmerDetails.getFarmerEmail());
        existingFarmer.setFarmerFName(farmerDetails.getFarmerFName());
        existingFarmer.setFarmerLName(farmerDetails.getFarmerLName());
        existingFarmer.setFarmerGender(farmerDetails.getFarmerGender());
        existingFarmer.setFarmerDOB(farmerDetails.getFarmerDOB());
        existingFarmer.setFarmerTel(farmerDetails.getFarmerTel());
        existingFarmer.setFarmerHouseNumber(farmerDetails.getFarmerHouseNumber());
        existingFarmer.setFarmerAlley(farmerDetails.getFarmerAlley());
        existingFarmer.setFarmerMoo(farmerDetails.getFarmerMoo());
        existingFarmer.setFarmerSubDistrict(farmerDetails.getFarmerSubDistrict());
        existingFarmer.setFarmerDistrict(farmerDetails.getFarmerDistrict());
        existingFarmer.setFarmerProvince(farmerDetails.getFarmerProvince());
        existingFarmer.setFarmerPostalCode(farmerDetails.getFarmerPostalCode());

        return farmerRepository.save(existingFarmer);
    }

    @Override
    public Farmer getFarmerById(int farmerId) {
        return farmerRepository.findById(farmerId)
                .orElseThrow(() -> new RuntimeException("Farmer not found with id: " + farmerId));
    }

    @Override
    public void deleteFarmer(int farmerId) {
        Farmer existingFarmer = getFarmerById(farmerId);
        deleteImage(existingFarmer.getFarmerImg());
        farmerRepository.delete(existingFarmer);
    }

    @Override
    public Farmer login(String username, String password) {
        Farmer farmer = farmerRepository.findByFarmerUserName(username);
        if (farmer != null && farmer.getFarmerPassword() != null && farmer.getFarmerPassword().equals(password)) {
            return farmer;
        }
        return null;
    }

    @Override
    public LoginResponse authenticateAndGenerateToken(LoginRequest loginRequest) {
        Farmer farmer = login(loginRequest.getFarmerUserName(), loginRequest.getFarmerPassword());
        
        if (farmer != null) {
            String token = jwtUtil.generateTokenForFarmer(farmer.getFarmerUserName(), farmer.getFarmerId());
            return new LoginResponse(token, farmer.getFarmerId(), farmer.getFarmerUserName(), "เข้าสู่ระบบสำเร็จ");
        } else {
            throw new RuntimeException("ชื่อผู้ใช้หรือรหัสผ่านไม่ถูกต้อง");
        }
    }

    
}
