package br.com.telematica.ciloapi.model.dto;

import java.util.Date;

public class ResponseAuthenticationDTO {
    private String token;
    private String role;
    private Date dateRequest;

    public ResponseAuthenticationDTO() {
    }
    
    public ResponseAuthenticationDTO(String token, String role, Date date) {
        this.token = token;
        this.role = role;
        this.dateRequest = date;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Date getDateRequest() {
        return dateRequest;
    }

    public void setDateRequest(Date dateRequest) {
        this.dateRequest = dateRequest;
    }

    @Override
    public String toString() {
        return "ResponseAuthenticationDTO [token=" + token + ", role=" + role + ", dateRequest=" + dateRequest + "]";
    }
}
