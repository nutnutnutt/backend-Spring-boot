package com.itsci.project65.dto;

public class OwnerLoginResponse {
    
    private String token;
    private String type = "Bearer";
    private Integer ownerId;
    private String ownerUserName;
    private String message;
    
    public OwnerLoginResponse() {}
    
    public OwnerLoginResponse(String token, Integer ownerId, String ownerUserName, String message) {
        this.token = token;
        this.ownerId = ownerId;
        this.ownerUserName = ownerUserName;
        this.message = message;
    }
    
    public String getToken() {
        return token;
    }
    
    public void setToken(String token) {
        this.token = token;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public Integer getOwnerId() {
        return ownerId;
    }
    
    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }
    
    public String getOwnerUserName() {
        return ownerUserName;
    }
    
    public void setOwnerUserName(String ownerUserName) {
        this.ownerUserName = ownerUserName;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
}
