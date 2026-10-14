/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.license.model;

import java.util.Map;

public class LicenseData {
    private long id;
    private String productStringId;
    private String key;
    private String status;
    private String type;
    private boolean isValid;
    private boolean isActivated;
    private String distributor;
    private String userCompany;
    private String userEmail;
    private String phone;
    private String fax;
    private Address address;
    private String licenseGenerationDate;
    private String licenseActivationDate;
    private String licenseExpireDate;
    private String maintenanceExpireDate;
    private Map<String, Object> license;

    public long getId() {
        return this.id;
    }

    public String getProductStringId() {
        return this.productStringId;
    }

    public String getKey() {
        return this.key;
    }

    public String getStatus() {
        return this.status;
    }

    public String getType() {
        return this.type;
    }

    public boolean isValid() {
        return this.isValid;
    }

    public boolean isActivated() {
        return this.isActivated;
    }

    public String getDistributor() {
        return this.distributor;
    }

    public String getUserCompany() {
        return this.userCompany;
    }

    public String getUserEmail() {
        return this.userEmail;
    }

    public String getPhone() {
        return this.phone;
    }

    public String getFax() {
        return this.fax;
    }

    public Address getAddress() {
        return this.address;
    }

    public String getLicenseGenerationDate() {
        return this.licenseGenerationDate;
    }

    public String getLicenseActivationDate() {
        return this.licenseActivationDate;
    }

    public String getLicenseExpireDate() {
        return this.licenseExpireDate;
    }

    public String getMaintenanceExpireDate() {
        return this.maintenanceExpireDate;
    }

    public Map<String, Object> getLicense() {
        return this.license;
    }

    public LicenseData(long id, String productStringId, String key, String status, String type, boolean isValid, boolean isActivated, String distributor, String userCompany, String userEmail, String phone, String fax, Address address, String licenseGenerationDate, String licenseActivationDate, String licenseExpireDate, String maintenanceExpireDate, Map<String, Object> license) {
        this.id = id;
        this.productStringId = productStringId;
        this.key = key;
        this.status = status;
        this.type = type;
        this.isValid = isValid;
        this.isActivated = isActivated;
        this.distributor = distributor;
        this.userCompany = userCompany;
        this.userEmail = userEmail;
        this.phone = phone;
        this.fax = fax;
        this.address = address;
        this.licenseGenerationDate = licenseGenerationDate;
        this.licenseActivationDate = licenseActivationDate;
        this.licenseExpireDate = licenseExpireDate;
        this.maintenanceExpireDate = maintenanceExpireDate;
        this.license = license;
    }

    public static class Address {
        private String country;
        private String city;
        private String street;
        private String zipCode;

        public String getCountry() {
            return this.country;
        }

        public String getCity() {
            return this.city;
        }

        public String getStreet() {
            return this.street;
        }

        public String getZipCode() {
            return this.zipCode;
        }

        public Address(String country, String city, String street, String zipCode) {
            this.country = country;
            this.city = city;
            this.street = street;
            this.zipCode = zipCode;
        }
    }
}

