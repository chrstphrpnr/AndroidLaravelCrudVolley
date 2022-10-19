package com.example.androidlaravelvolleycrud;

public class Hospital {

    String id, hospital_name;

    public Hospital() {

    }

    public Hospital(String id, String hospital_name) {
        this.id = id;
        this.hospital_name = hospital_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHospital_name() {
        return hospital_name;
    }

    public void setHospital_name(String hospital_name) {
        this.hospital_name = hospital_name;
    }
}
