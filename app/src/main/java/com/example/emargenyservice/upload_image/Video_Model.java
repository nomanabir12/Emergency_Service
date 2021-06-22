package com.example.emargenyservice.upload_image;

public class Video_Model {
    String email,uri;

    public Video_Model() {
    }

    public Video_Model(String email, String uri) {
        this.email = email;
        this.uri = uri;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
