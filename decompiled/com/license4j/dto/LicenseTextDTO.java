/*
 * Decompiled with CFR 0.152.
 */
package com.license4j.dto;

import com.license4j.licensetext.LicenseText;
import java.util.Map;

public final class LicenseTextDTO {
    private final String licenseKey;
    private final int licenseFileType;
    private final String distributor;
    private final String fullName;
    private final String email;
    private final String company;
    private final String phone;
    private final String street;
    private final String city;
    private final String zipCode;
    private final String country;
    private final long licenseID;
    private final long licenseGenerationDateTime;
    private final String licenseHardwareID;
    private final int licenseQuantity;
    private final String licenseProductName;
    private final String licenseValidProductID;
    private final String licenseValidProductEdition;
    private final String licenseValidProductVersion;
    private final int licenseValidityPeriod;
    private final int licenseMaintenancePeriod;
    private final long activationID;
    private final boolean activationRequired;
    private final boolean activationCompleted;
    private final long activationCompletedDateTime;
    private final int activationPeriod;
    private final int activationReturnType;
    private final int activationHardwareIDSelection;
    private final int datetimeCheck;
    private final String datetimeNTPServer;
    private final int datetimeNTPServerTimeout;
    private final String datetimeWebServer;
    private final int datetimeWebServerTimeout;
    private final Map<String, String> customUnsignedFeatures;
    private final String licenseSignature;
    private final String licensePublicKey;
    private final boolean licenseTextGenerationTimeEqualToActivationTime;
    private final long timestamp;

    public LicenseTextDTO(LicenseText entity) {
        this.licenseKey = entity.getLicenseKey();
        this.licenseFileType = entity.getLicenseFileType();
        this.distributor = entity.getDistributor();
        this.fullName = entity.getFullName();
        this.email = entity.getEmail();
        this.company = entity.getCompany();
        this.phone = entity.getPhone();
        this.street = entity.getStreet();
        this.city = entity.getCity();
        this.zipCode = entity.getZipCode();
        this.country = entity.getCountry();
        this.licenseID = entity.getLicenseID();
        this.licenseGenerationDateTime = entity.getLicenseGenerationDateTime();
        this.licenseHardwareID = entity.getLicenseHardwareID();
        this.licenseQuantity = entity.getLicenseQuantity();
        this.licenseProductName = entity.getLicenseProductName();
        this.licenseValidProductID = entity.getLicenseValidProductID();
        this.licenseValidProductEdition = entity.getLicenseValidProductEdition();
        this.licenseValidProductVersion = entity.getLicenseValidProductVersion();
        this.licenseValidityPeriod = entity.getLicenseValidityPeriod();
        this.licenseMaintenancePeriod = entity.getLicenseMaintenancePeriod();
        this.activationID = entity.getActivationID();
        this.activationRequired = entity.isActivationRequired();
        this.activationCompleted = entity.isActivationCompleted();
        this.activationCompletedDateTime = entity.getActivationCompletedDateTime();
        this.activationPeriod = entity.getActivationPeriod();
        this.activationReturnType = entity.getActivationReturnType();
        this.activationHardwareIDSelection = entity.getActivationHardwareIDSelection();
        this.datetimeCheck = entity.getDatetimeCheck();
        this.datetimeNTPServer = entity.getDatetimeNTPServer();
        this.datetimeNTPServerTimeout = entity.getDatetimeNTPServerTimeout();
        this.datetimeWebServer = entity.getDatetimeWebServer();
        this.datetimeWebServerTimeout = entity.getDatetimeWebServerTimeout();
        this.customUnsignedFeatures = entity.getCustomUnsignedFeatures();
        this.licenseSignature = entity.getLicenseSignature();
        this.licensePublicKey = entity.getLicensePublicKey();
        this.licenseTextGenerationTimeEqualToActivationTime = entity.isLicenseTextGenerationTimeEqualToActivationTime();
        this.timestamp = entity.getTimestamp();
    }

    public String getLicenseKey() {
        return this.licenseKey;
    }

    public int getLicenseFileType() {
        return this.licenseFileType;
    }

    public String getDistributor() {
        return this.distributor;
    }

    public String getFullName() {
        return this.fullName;
    }

    public String getEmail() {
        return this.email;
    }

    public String getCompany() {
        return this.company;
    }

    public String getPhone() {
        return this.phone;
    }

    public String getStreet() {
        return this.street;
    }

    public String getCity() {
        return this.city;
    }

    public String getZipCode() {
        return this.zipCode;
    }

    public String getCountry() {
        return this.country;
    }

    public long getLicenseID() {
        return this.licenseID;
    }

    public long getLicenseGenerationDateTime() {
        return this.licenseGenerationDateTime;
    }

    public String getLicenseHardwareID() {
        return this.licenseHardwareID;
    }

    public int getLicenseQuantity() {
        return this.licenseQuantity;
    }

    public String getLicenseProductName() {
        return this.licenseProductName;
    }

    public String getLicenseValidProductID() {
        return this.licenseValidProductID;
    }

    public String getLicenseValidProductEdition() {
        return this.licenseValidProductEdition;
    }

    public String getLicenseValidProductVersion() {
        return this.licenseValidProductVersion;
    }

    public int getLicenseValidityPeriod() {
        return this.licenseValidityPeriod;
    }

    public int getLicenseMaintenancePeriod() {
        return this.licenseMaintenancePeriod;
    }

    public long getActivationID() {
        return this.activationID;
    }

    public boolean isActivationRequired() {
        return this.activationRequired;
    }

    public boolean isActivationCompleted() {
        return this.activationCompleted;
    }

    public long getActivationCompletedDateTime() {
        return this.activationCompletedDateTime;
    }

    public int getActivationPeriod() {
        return this.activationPeriod;
    }

    public int getActivationReturnType() {
        return this.activationReturnType;
    }

    public int getActivationHardwareIDSelection() {
        return this.activationHardwareIDSelection;
    }

    public int getDatetimeCheck() {
        return this.datetimeCheck;
    }

    public String getDatetimeNTPServer() {
        return this.datetimeNTPServer;
    }

    public int getDatetimeNTPServerTimeout() {
        return this.datetimeNTPServerTimeout;
    }

    public String getDatetimeWebServer() {
        return this.datetimeWebServer;
    }

    public int getDatetimeWebServerTimeout() {
        return this.datetimeWebServerTimeout;
    }

    public Map<String, String> getCustomUnsignedFeatures() {
        return this.customUnsignedFeatures;
    }

    public String getLicenseSignature() {
        return this.licenseSignature;
    }

    public String getLicensePublicKey() {
        return this.licensePublicKey;
    }

    public boolean isLicenseTextGenerationTimeEqualToActivationTime() {
        return this.licenseTextGenerationTimeEqualToActivationTime;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof LicenseTextDTO)) {
            return false;
        }
        LicenseTextDTO other = (LicenseTextDTO)o;
        if (this.getLicenseFileType() != other.getLicenseFileType()) {
            return false;
        }
        if (this.getLicenseID() != other.getLicenseID()) {
            return false;
        }
        if (this.getLicenseGenerationDateTime() != other.getLicenseGenerationDateTime()) {
            return false;
        }
        if (this.getLicenseQuantity() != other.getLicenseQuantity()) {
            return false;
        }
        if (this.getLicenseValidityPeriod() != other.getLicenseValidityPeriod()) {
            return false;
        }
        if (this.getLicenseMaintenancePeriod() != other.getLicenseMaintenancePeriod()) {
            return false;
        }
        if (this.getActivationID() != other.getActivationID()) {
            return false;
        }
        if (this.isActivationRequired() != other.isActivationRequired()) {
            return false;
        }
        if (this.isActivationCompleted() != other.isActivationCompleted()) {
            return false;
        }
        if (this.getActivationCompletedDateTime() != other.getActivationCompletedDateTime()) {
            return false;
        }
        if (this.getActivationPeriod() != other.getActivationPeriod()) {
            return false;
        }
        if (this.getActivationReturnType() != other.getActivationReturnType()) {
            return false;
        }
        if (this.getActivationHardwareIDSelection() != other.getActivationHardwareIDSelection()) {
            return false;
        }
        if (this.getDatetimeCheck() != other.getDatetimeCheck()) {
            return false;
        }
        if (this.getDatetimeNTPServerTimeout() != other.getDatetimeNTPServerTimeout()) {
            return false;
        }
        if (this.getDatetimeWebServerTimeout() != other.getDatetimeWebServerTimeout()) {
            return false;
        }
        if (this.isLicenseTextGenerationTimeEqualToActivationTime() != other.isLicenseTextGenerationTimeEqualToActivationTime()) {
            return false;
        }
        if (this.getTimestamp() != other.getTimestamp()) {
            return false;
        }
        String this$licenseKey = this.getLicenseKey();
        String other$licenseKey = other.getLicenseKey();
        if (this$licenseKey == null ? other$licenseKey != null : !this$licenseKey.equals(other$licenseKey)) {
            return false;
        }
        String this$distributor = this.getDistributor();
        String other$distributor = other.getDistributor();
        if (this$distributor == null ? other$distributor != null : !this$distributor.equals(other$distributor)) {
            return false;
        }
        String this$fullName = this.getFullName();
        String other$fullName = other.getFullName();
        if (this$fullName == null ? other$fullName != null : !this$fullName.equals(other$fullName)) {
            return false;
        }
        String this$email = this.getEmail();
        String other$email = other.getEmail();
        if (this$email == null ? other$email != null : !this$email.equals(other$email)) {
            return false;
        }
        String this$company = this.getCompany();
        String other$company = other.getCompany();
        if (this$company == null ? other$company != null : !this$company.equals(other$company)) {
            return false;
        }
        String this$phone = this.getPhone();
        String other$phone = other.getPhone();
        if (this$phone == null ? other$phone != null : !this$phone.equals(other$phone)) {
            return false;
        }
        String this$street = this.getStreet();
        String other$street = other.getStreet();
        if (this$street == null ? other$street != null : !this$street.equals(other$street)) {
            return false;
        }
        String this$city = this.getCity();
        String other$city = other.getCity();
        if (this$city == null ? other$city != null : !this$city.equals(other$city)) {
            return false;
        }
        String this$zipCode = this.getZipCode();
        String other$zipCode = other.getZipCode();
        if (this$zipCode == null ? other$zipCode != null : !this$zipCode.equals(other$zipCode)) {
            return false;
        }
        String this$country = this.getCountry();
        String other$country = other.getCountry();
        if (this$country == null ? other$country != null : !this$country.equals(other$country)) {
            return false;
        }
        String this$licenseHardwareID = this.getLicenseHardwareID();
        String other$licenseHardwareID = other.getLicenseHardwareID();
        if (this$licenseHardwareID == null ? other$licenseHardwareID != null : !this$licenseHardwareID.equals(other$licenseHardwareID)) {
            return false;
        }
        String this$licenseProductName = this.getLicenseProductName();
        String other$licenseProductName = other.getLicenseProductName();
        if (this$licenseProductName == null ? other$licenseProductName != null : !this$licenseProductName.equals(other$licenseProductName)) {
            return false;
        }
        String this$licenseValidProductID = this.getLicenseValidProductID();
        String other$licenseValidProductID = other.getLicenseValidProductID();
        if (this$licenseValidProductID == null ? other$licenseValidProductID != null : !this$licenseValidProductID.equals(other$licenseValidProductID)) {
            return false;
        }
        String this$licenseValidProductEdition = this.getLicenseValidProductEdition();
        String other$licenseValidProductEdition = other.getLicenseValidProductEdition();
        if (this$licenseValidProductEdition == null ? other$licenseValidProductEdition != null : !this$licenseValidProductEdition.equals(other$licenseValidProductEdition)) {
            return false;
        }
        String this$licenseValidProductVersion = this.getLicenseValidProductVersion();
        String other$licenseValidProductVersion = other.getLicenseValidProductVersion();
        if (this$licenseValidProductVersion == null ? other$licenseValidProductVersion != null : !this$licenseValidProductVersion.equals(other$licenseValidProductVersion)) {
            return false;
        }
        String this$datetimeNTPServer = this.getDatetimeNTPServer();
        String other$datetimeNTPServer = other.getDatetimeNTPServer();
        if (this$datetimeNTPServer == null ? other$datetimeNTPServer != null : !this$datetimeNTPServer.equals(other$datetimeNTPServer)) {
            return false;
        }
        String this$datetimeWebServer = this.getDatetimeWebServer();
        String other$datetimeWebServer = other.getDatetimeWebServer();
        if (this$datetimeWebServer == null ? other$datetimeWebServer != null : !this$datetimeWebServer.equals(other$datetimeWebServer)) {
            return false;
        }
        Map<String, String> this$customUnsignedFeatures = this.getCustomUnsignedFeatures();
        Map<String, String> other$customUnsignedFeatures = other.getCustomUnsignedFeatures();
        if (this$customUnsignedFeatures == null ? other$customUnsignedFeatures != null : !((Object)this$customUnsignedFeatures).equals(other$customUnsignedFeatures)) {
            return false;
        }
        String this$licenseSignature = this.getLicenseSignature();
        String other$licenseSignature = other.getLicenseSignature();
        if (this$licenseSignature == null ? other$licenseSignature != null : !this$licenseSignature.equals(other$licenseSignature)) {
            return false;
        }
        String this$licensePublicKey = this.getLicensePublicKey();
        String other$licensePublicKey = other.getLicensePublicKey();
        return !(this$licensePublicKey == null ? other$licensePublicKey != null : !this$licensePublicKey.equals(other$licensePublicKey));
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        result = result * 59 + this.getLicenseFileType();
        long $licenseID = this.getLicenseID();
        result = result * 59 + (int)($licenseID >>> 32 ^ $licenseID);
        long $licenseGenerationDateTime = this.getLicenseGenerationDateTime();
        result = result * 59 + (int)($licenseGenerationDateTime >>> 32 ^ $licenseGenerationDateTime);
        result = result * 59 + this.getLicenseQuantity();
        result = result * 59 + this.getLicenseValidityPeriod();
        result = result * 59 + this.getLicenseMaintenancePeriod();
        long $activationID = this.getActivationID();
        result = result * 59 + (int)($activationID >>> 32 ^ $activationID);
        result = result * 59 + (this.isActivationRequired() ? 79 : 97);
        result = result * 59 + (this.isActivationCompleted() ? 79 : 97);
        long $activationCompletedDateTime = this.getActivationCompletedDateTime();
        result = result * 59 + (int)($activationCompletedDateTime >>> 32 ^ $activationCompletedDateTime);
        result = result * 59 + this.getActivationPeriod();
        result = result * 59 + this.getActivationReturnType();
        result = result * 59 + this.getActivationHardwareIDSelection();
        result = result * 59 + this.getDatetimeCheck();
        result = result * 59 + this.getDatetimeNTPServerTimeout();
        result = result * 59 + this.getDatetimeWebServerTimeout();
        result = result * 59 + (this.isLicenseTextGenerationTimeEqualToActivationTime() ? 79 : 97);
        long $timestamp = this.getTimestamp();
        result = result * 59 + (int)($timestamp >>> 32 ^ $timestamp);
        String $licenseKey = this.getLicenseKey();
        result = result * 59 + ($licenseKey == null ? 43 : $licenseKey.hashCode());
        String $distributor = this.getDistributor();
        result = result * 59 + ($distributor == null ? 43 : $distributor.hashCode());
        String $fullName = this.getFullName();
        result = result * 59 + ($fullName == null ? 43 : $fullName.hashCode());
        String $email = this.getEmail();
        result = result * 59 + ($email == null ? 43 : $email.hashCode());
        String $company = this.getCompany();
        result = result * 59 + ($company == null ? 43 : $company.hashCode());
        String $phone = this.getPhone();
        result = result * 59 + ($phone == null ? 43 : $phone.hashCode());
        String $street = this.getStreet();
        result = result * 59 + ($street == null ? 43 : $street.hashCode());
        String $city = this.getCity();
        result = result * 59 + ($city == null ? 43 : $city.hashCode());
        String $zipCode = this.getZipCode();
        result = result * 59 + ($zipCode == null ? 43 : $zipCode.hashCode());
        String $country = this.getCountry();
        result = result * 59 + ($country == null ? 43 : $country.hashCode());
        String $licenseHardwareID = this.getLicenseHardwareID();
        result = result * 59 + ($licenseHardwareID == null ? 43 : $licenseHardwareID.hashCode());
        String $licenseProductName = this.getLicenseProductName();
        result = result * 59 + ($licenseProductName == null ? 43 : $licenseProductName.hashCode());
        String $licenseValidProductID = this.getLicenseValidProductID();
        result = result * 59 + ($licenseValidProductID == null ? 43 : $licenseValidProductID.hashCode());
        String $licenseValidProductEdition = this.getLicenseValidProductEdition();
        result = result * 59 + ($licenseValidProductEdition == null ? 43 : $licenseValidProductEdition.hashCode());
        String $licenseValidProductVersion = this.getLicenseValidProductVersion();
        result = result * 59 + ($licenseValidProductVersion == null ? 43 : $licenseValidProductVersion.hashCode());
        String $datetimeNTPServer = this.getDatetimeNTPServer();
        result = result * 59 + ($datetimeNTPServer == null ? 43 : $datetimeNTPServer.hashCode());
        String $datetimeWebServer = this.getDatetimeWebServer();
        result = result * 59 + ($datetimeWebServer == null ? 43 : $datetimeWebServer.hashCode());
        Map<String, String> $customUnsignedFeatures = this.getCustomUnsignedFeatures();
        result = result * 59 + ($customUnsignedFeatures == null ? 43 : ((Object)$customUnsignedFeatures).hashCode());
        String $licenseSignature = this.getLicenseSignature();
        result = result * 59 + ($licenseSignature == null ? 43 : $licenseSignature.hashCode());
        String $licensePublicKey = this.getLicensePublicKey();
        result = result * 59 + ($licensePublicKey == null ? 43 : $licensePublicKey.hashCode());
        return result;
    }

    public String toString() {
        return "LicenseTextDTO(licenseKey=" + this.getLicenseKey() + ", licenseFileType=" + this.getLicenseFileType() + ", distributor=" + this.getDistributor() + ", fullName=" + this.getFullName() + ", email=" + this.getEmail() + ", company=" + this.getCompany() + ", phone=" + this.getPhone() + ", street=" + this.getStreet() + ", city=" + this.getCity() + ", zipCode=" + this.getZipCode() + ", country=" + this.getCountry() + ", licenseID=" + this.getLicenseID() + ", licenseGenerationDateTime=" + this.getLicenseGenerationDateTime() + ", licenseHardwareID=" + this.getLicenseHardwareID() + ", licenseQuantity=" + this.getLicenseQuantity() + ", licenseProductName=" + this.getLicenseProductName() + ", licenseValidProductID=" + this.getLicenseValidProductID() + ", licenseValidProductEdition=" + this.getLicenseValidProductEdition() + ", licenseValidProductVersion=" + this.getLicenseValidProductVersion() + ", licenseValidityPeriod=" + this.getLicenseValidityPeriod() + ", licenseMaintenancePeriod=" + this.getLicenseMaintenancePeriod() + ", activationID=" + this.getActivationID() + ", activationRequired=" + this.isActivationRequired() + ", activationCompleted=" + this.isActivationCompleted() + ", activationCompletedDateTime=" + this.getActivationCompletedDateTime() + ", activationPeriod=" + this.getActivationPeriod() + ", activationReturnType=" + this.getActivationReturnType() + ", activationHardwareIDSelection=" + this.getActivationHardwareIDSelection() + ", datetimeCheck=" + this.getDatetimeCheck() + ", datetimeNTPServer=" + this.getDatetimeNTPServer() + ", datetimeNTPServerTimeout=" + this.getDatetimeNTPServerTimeout() + ", datetimeWebServer=" + this.getDatetimeWebServer() + ", datetimeWebServerTimeout=" + this.getDatetimeWebServerTimeout() + ", customUnsignedFeatures=" + String.valueOf(this.getCustomUnsignedFeatures()) + ", licenseSignature=" + this.getLicenseSignature() + ", licensePublicKey=" + this.getLicensePublicKey() + ", licenseTextGenerationTimeEqualToActivationTime=" + this.isLicenseTextGenerationTimeEqualToActivationTime() + ", timestamp=" + this.getTimestamp() + ")";
    }

    public LicenseTextDTO(String licenseKey, int licenseFileType, String distributor, String fullName, String email, String company, String phone, String street, String city, String zipCode, String country, long licenseID, long licenseGenerationDateTime, String licenseHardwareID, int licenseQuantity, String licenseProductName, String licenseValidProductID, String licenseValidProductEdition, String licenseValidProductVersion, int licenseValidityPeriod, int licenseMaintenancePeriod, long activationID, boolean activationRequired, boolean activationCompleted, long activationCompletedDateTime, int activationPeriod, int activationReturnType, int activationHardwareIDSelection, int datetimeCheck, String datetimeNTPServer, int datetimeNTPServerTimeout, String datetimeWebServer, int datetimeWebServerTimeout, Map<String, String> customUnsignedFeatures, String licenseSignature, String licensePublicKey, boolean licenseTextGenerationTimeEqualToActivationTime, long timestamp) {
        this.licenseKey = licenseKey;
        this.licenseFileType = licenseFileType;
        this.distributor = distributor;
        this.fullName = fullName;
        this.email = email;
        this.company = company;
        this.phone = phone;
        this.street = street;
        this.city = city;
        this.zipCode = zipCode;
        this.country = country;
        this.licenseID = licenseID;
        this.licenseGenerationDateTime = licenseGenerationDateTime;
        this.licenseHardwareID = licenseHardwareID;
        this.licenseQuantity = licenseQuantity;
        this.licenseProductName = licenseProductName;
        this.licenseValidProductID = licenseValidProductID;
        this.licenseValidProductEdition = licenseValidProductEdition;
        this.licenseValidProductVersion = licenseValidProductVersion;
        this.licenseValidityPeriod = licenseValidityPeriod;
        this.licenseMaintenancePeriod = licenseMaintenancePeriod;
        this.activationID = activationID;
        this.activationRequired = activationRequired;
        this.activationCompleted = activationCompleted;
        this.activationCompletedDateTime = activationCompletedDateTime;
        this.activationPeriod = activationPeriod;
        this.activationReturnType = activationReturnType;
        this.activationHardwareIDSelection = activationHardwareIDSelection;
        this.datetimeCheck = datetimeCheck;
        this.datetimeNTPServer = datetimeNTPServer;
        this.datetimeNTPServerTimeout = datetimeNTPServerTimeout;
        this.datetimeWebServer = datetimeWebServer;
        this.datetimeWebServerTimeout = datetimeWebServerTimeout;
        this.customUnsignedFeatures = customUnsignedFeatures;
        this.licenseSignature = licenseSignature;
        this.licensePublicKey = licensePublicKey;
        this.licenseTextGenerationTimeEqualToActivationTime = licenseTextGenerationTimeEqualToActivationTime;
        this.timestamp = timestamp;
    }
}

