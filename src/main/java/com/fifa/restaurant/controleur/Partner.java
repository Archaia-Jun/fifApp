package com.fifa.restaurant.controleur;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Partner {

    public String name;
    public String head;
    public String mail;
    public String zipCode;
    public String city;
    public String type;

    public Partner() {
    }

    public Partner(String name, String head, String mail, String zipCode, String city, String type) {
        this.name = name;
        this.head = head;
        this.mail = mail;
        this.zipCode = zipCode;
        this.city = city;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }


    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }


    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

