package com.itsci.project65.dto;

public class LoginResponse {
    
    private String token;
    private String type = "Bearer";
    private Integer farmerId;
    private String username;
    private String message;
    
    public LoginResponse() {}
    
    public LoginResponse(String token, Integer farmerId, String username, String message) {
        this.token = token;
        this.farmerId = farmerId;
        this.username = username;
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
    
    public Integer getFarmerId() {
        return farmerId;
    }
    
    public void setFarmerId(Integer farmerId) {
        this.farmerId = farmerId;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
}
