package com.example.glypha_primer_parcial_giardina_saracco.data.model;

import java.io.Serializable;

public class User implements Serializable {

    private String name;

    private String rol;

    private String mail;

    private String about;

    public User(String name, String rol, String mail, String about) {
        this.name = name;
        this.rol = rol;
        this.mail = mail;
        this.about = about;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }
}
