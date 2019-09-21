package com.example.xihad.pixlups;

public class UserData {

    private String username,email,password,identity,description,image;

    public UserData() {

    }

    public UserData(String username, String email, String password, String identity, String description, String image) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.identity = identity;
        this.description = description;
        this.image = image;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
