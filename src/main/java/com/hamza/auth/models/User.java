package com.hamza.auth.models;

import java.util.Date;

public class User {
    private int id;
    private String fullName;
    private String email;
    private Date birthDate;
    private String password;
    private String validationToken;
    private boolean validated;
    

    public User(int id, String fullName, String email, String password, Date birthDate) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.birthDate = birthDate;
    }

    public User() {
	}

	// Getters and Setters
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getValidationToken() {
        return validationToken;
    }

    public void setValidationToken(String validationToken) {
        this.validationToken = validationToken;
    }

    public boolean isValidated() {
        return validated;
    }

    public void setValidated(boolean validated) {
        this.validated = validated;
    }
}