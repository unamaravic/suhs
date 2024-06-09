package com.example.workflow.model;

public class Address {
    private String naziv;
    private String mjesto;
    private String pbr;

    public Address() {
    }

    public Address(String naziv, String mjesto, String pbr) {
        this.naziv = naziv;
        this.mjesto = mjesto;
        this.pbr = pbr;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getMjesto() {
        return mjesto;
    }

    public void setMjesto(String mjesto) {
        this.mjesto = mjesto;
    }

    public String getPbr() {
        return pbr;
    }

    public void setPbr(String pbr) {
        this.pbr = pbr;
    }
}