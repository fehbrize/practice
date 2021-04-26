package com.validity.monolithstarter.service;

public class Entry {
    private String entryId;
    private String firstName;
    private String lastName;
    private String company;
    private String email;
    private String address1;
    private String address2;
    private String zip;
    private String city;
    private String longState;
    private String shortState;
    private String phone;



    public Entry(String entryId, String firstName, String lastName, String company, String email, String address1, String address2,
                 String zip, String city, String longState, String shortState, String phone) {
        this.entryId = entryId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.company = company;
        this.email = email;
        this.address1 = address1;
        this.address2 = address2;
        this.zip = zip;
        this.city = city;
        this.longState = longState;
        this.shortState = shortState;
        this.phone = phone;
    }

    public String getEntryId() {
        return entryId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCompany() {
        return company;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress1() {
        return address1;
    }

    public String getAddress2() {
        return address2;
    }

    public String getZip() {
        return zip;
    }

    public String getCity() {
        return city;
    }

    public String getLongState() {
        return longState;
    }

    public String getShortState() {
        return shortState;
    }

    public String getPhone() {
        return phone;
    }
}
