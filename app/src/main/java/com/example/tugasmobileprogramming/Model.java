package com.example.tugasmobileprogramming;

public class Model {
    private final String name;
    private final String address;
    private final String region;
    private final String phone;
    private final String province;

    public Model(String name, String address, String region, String phone, String province) {
        this.name = name;
        this.address = address;
        this.region = region;
        this.phone = phone;
        this.province = province;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getRegion() {
        return region;
    }

    public String getPhone() {
        return phone;
    }

    public String getProvince() {
        return province;
    }
}
