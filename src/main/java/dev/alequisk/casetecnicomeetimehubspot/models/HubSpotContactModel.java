package dev.alequisk.casetecnicomeetimehubspot.models;

public class HubSpotContactModel {
    private final String email;
    private final String name;
    private final String lastName;
    private final String phone;
    private final String company;
    private final String website;
    private final String lifecyclestage;


    public HubSpotContactModel(String email, String name, String lastName, String phone, String company, String website, String lifecyclestage) {
        this.email = email;
        this.name = name;
        this.lastName = lastName;
        this.phone = phone;
        this.company = company;
        this.website = website;
        this.lifecyclestage = lifecyclestage;
    }

    public String getLifecyclestage() {
        return lifecyclestage;
    }

    public String getWebsite() {
        return website;
    }

    public String getCompany() {
        return company;
    }

    public String getLastName() {
        return lastName;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }
}
