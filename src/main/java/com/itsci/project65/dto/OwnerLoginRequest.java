package com.itsci.project65.dto;

import jakarta.validation.constraints.NotBlank;

public class OwnerLoginRequest {
    
    @NotBlank(message = "Owner username is required")
    private String ownerUserName;
    
    @NotBlank(message = "Owner password is required")
    private String ownerPassword;
    
    public OwnerLoginRequest() {}
    
    public OwnerLoginRequest(String ownerUserName, String ownerPassword) {
        this.ownerUserName = ownerUserName;
        this.ownerPassword = ownerPassword;
    }
    
    public String getOwnerUserName() {
        return ownerUserName;
    }
    
    public void setOwnerUserName(String ownerUserName) {
        this.ownerUserName = ownerUserName;
    }
    
    public String getOwnerPassword() {
        return ownerPassword;
    }
    
    public void setOwnerPassword(String ownerPassword) {
        this.ownerPassword = ownerPassword;
    }
}
