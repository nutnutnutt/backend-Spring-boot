package com.itsci.project65.dto;

import jakarta.validation.constraints.NotBlank;

public class LoginRequest {
    
    @NotBlank(message = "Farmer username is required")
    private String farmerUserName;
    
    @NotBlank(message = "Farmer password is required")
    private String farmerPassword;
    
    public LoginRequest() {}
    
    public LoginRequest(String farmerUserName, String farmerPassword) {
        this.farmerUserName = farmerUserName;
        this.farmerPassword = farmerPassword;
    }
    
    public String getFarmerUserName() {
        return farmerUserName;
    }
    
    public void setFarmerUserName(String farmerUserName) {
        this.farmerUserName = farmerUserName;
    }
    
    public String getFarmerPassword() {
        return farmerPassword;
    }
    
    public void setFarmerPassword(String farmerPassword) {
        this.farmerPassword = farmerPassword;
    }
}
