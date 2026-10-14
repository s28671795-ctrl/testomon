/*
 * Decompiled with CFR 0.152.
 */
package com.license4j.licensetext;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.license4j.dto.LicenseTextBaseDTO;
import com.license4j.dto.LicenseTextDTO;
import com.license4j.exceptions.LicenseSecurityException;
import com.license4j.util.Crypto;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LicenseText {
    private static final Logger log = LoggerFactory.getLogger(LicenseText.class);
    public static final int TYPE_LICENSE_FILE = 0;
    public static final int TYPE_FLOATING_LICENSE_FILE = 1;
    private String text;
    private String licenseKey;
    private int licenseFileType = 0;
    private String distributor;
    private String fullName;
    private String email;
    private String company;
    private String phone;
    private String street;
    private String city;
    private String zipCode;
    private String country;
    private long licenseID = 0L;
    private long licenseGenerationDateTime = 0L;
    private String licenseHardwareID;
    private int licenseQuantity;
    private String licenseProductName;
    private String licenseValidProductID;
    private String licenseValidProductEdition;
    private String licenseValidProductVersion;
    private int licenseValidityPeriod;
    private int licenseMaintenancePeriod;
    private long activationID = 0L;
    private boolean activationRequired;
    private boolean activationCompleted;
    private long activationCompletedDateTime;
    private int activationPeriod;
    private int activationReturnType;
    private int activationHardwareIDSelection;
    private int datetimeCheck;
    private String datetimeNTPServer;
    private int datetimeNTPServerTimeout;
    private String datetimeWebServer;
    private int datetimeWebServerTimeout;
    private Map<String, String> customUnsignedFeatures = new LinkedHashMap<String, String>();
    private String licenseSignature;
    private String licensePublicKey;
    private boolean licenseTextGenerationTimeEqualToActivationTime;
    private long timestamp = System.currentTimeMillis();

    public LicenseText(String licenseString, String publicKey) throws LicenseSecurityException {
        String json = this.decrypt(licenseString, publicKey);
        LicenseTextDTO dto = JSONObject.parseObject(json, LicenseTextDTO.class);
        this.text = licenseString;
        this.licenseKey = dto.getLicenseKey();
        this.licenseFileType = dto.getLicenseFileType();
        this.distributor = dto.getDistributor();
        this.fullName = dto.getFullName();
        this.email = dto.getEmail();
        this.company = dto.getCompany();
        this.phone = dto.getPhone();
        this.street = dto.getStreet();
        this.city = dto.getCity();
        this.zipCode = dto.getZipCode();
        this.country = dto.getCountry();
        this.licenseID = dto.getLicenseID();
        this.licenseGenerationDateTime = dto.getLicenseGenerationDateTime();
        this.licenseHardwareID = dto.getLicenseHardwareID();
        this.licenseQuantity = dto.getLicenseQuantity();
        this.licenseProductName = dto.getLicenseProductName();
        this.licenseValidProductID = dto.getLicenseValidProductID();
        this.licenseValidProductEdition = dto.getLicenseValidProductEdition();
        this.licenseValidProductVersion = dto.getLicenseValidProductVersion();
        this.licenseValidityPeriod = dto.getLicenseValidityPeriod();
        this.licenseMaintenancePeriod = dto.getLicenseMaintenancePeriod();
        this.activationID = dto.getActivationID();
        this.activationRequired = dto.isActivationRequired();
        this.activationCompleted = dto.isActivationCompleted();
        this.activationCompletedDateTime = dto.getActivationCompletedDateTime();
        this.activationPeriod = dto.getActivationPeriod();
        this.activationReturnType = dto.getActivationReturnType();
        this.activationHardwareIDSelection = dto.getActivationHardwareIDSelection();
        this.datetimeCheck = dto.getDatetimeCheck();
        this.datetimeNTPServer = dto.getDatetimeNTPServer();
        this.datetimeNTPServerTimeout = dto.getDatetimeNTPServerTimeout();
        this.datetimeWebServer = dto.getDatetimeWebServer();
        this.datetimeWebServerTimeout = dto.getDatetimeWebServerTimeout();
        this.customUnsignedFeatures = dto.getCustomUnsignedFeatures();
        this.licenseSignature = dto.getLicenseSignature();
        this.licensePublicKey = dto.getLicensePublicKey();
        this.licenseTextGenerationTimeEqualToActivationTime = dto.isLicenseTextGenerationTimeEqualToActivationTime();
        this.timestamp = dto.getTimestamp();
    }

    private synchronized String decrypt(String licenseString, String publicKey) throws LicenseSecurityException {
        if (publicKey != null) {
            licenseString = this.removeComments(licenseString);
        }
        return this.decryptLicenseString(licenseString, publicKey);
    }

    private String decryptLicenseString(String licenseString, String publicKey) throws LicenseSecurityException {
        try {
            String result;
            String string = result = publicKey != null ? Crypto.decrypt(licenseString, publicKey) : Crypto.decrypt(licenseString);
            if (result == null || result.isBlank()) {
                throw new LicenseSecurityException("License string decryption error.");
            }
            return result;
        }
        catch (Exception e) {
            log.error("Decryption error: {}", (Object)e.getMessage());
            throw new LicenseSecurityException(e.getLocalizedMessage());
        }
    }

    private String removeComments(String licenseString) {
        while (licenseString.contains("/**") && licenseString.contains("*/")) {
            String comment = licenseString.substring(licenseString.indexOf("/**"), licenseString.indexOf("*/") + 2);
            licenseString = licenseString.replace(comment, "");
        }
        return licenseString;
    }

    public final String getBaseProperties() {
        LicenseTextBaseDTO dto = new LicenseTextBaseDTO(this);
        return JSONObject.toJSONString((Object)dto, SerializerFeature.SortField);
    }

    private String getAllProperties() {
        LicenseTextDTO dto = new LicenseTextDTO(this);
        return JSONObject.toJSONString((Object)dto, false);
    }

    public String getContent(String publicKey) throws LicenseSecurityException {
        StringBuilder result = new StringBuilder();
        result.append("# ");
        Optional.ofNullable(this.licenseProductName).ifPresent(lpn -> {
            result.append((String)lpn).append(" ");
            Optional.ofNullable(this.licenseValidProductEdition).ifPresent(lvpe -> result.append((String)lvpe).append(" "));
            Optional.ofNullable(this.licenseValidProductVersion).ifPresent(lvpv -> result.append((String)lvpv).append(" "));
        });
        result.append("License (id: ").append(this.licenseID).append(")\n");
        try {
            String properties = this.getAllProperties();
            if (publicKey != null) {
                publicKey = publicKey.replace("\n", "");
                result.append(Crypto.encrypt(properties, publicKey));
            } else {
                result.append(Crypto.encrypt(properties));
            }
        }
        catch (Exception e) {
            log.error("Encryption error: {}", (Object)e.getMessage());
            throw new LicenseSecurityException(e.getLocalizedMessage());
        }
        return result.toString();
    }

    public Instant getLicenseExpireDate() {
        return this.licenseValidityPeriod > 0 ? this.getStartDate().plus((long)this.licenseValidityPeriod, ChronoUnit.DAYS) : null;
    }

    public boolean isGenerationDateTimeInFuture(Instant currentDate) {
        return this.licenseGenerationDateTime > 0L && currentDate.isBefore(Instant.ofEpochMilli(this.licenseGenerationDateTime).minusSeconds(3600L));
    }

    public int getLicenseExpireDaysRemaining(Instant currentDate) {
        return this.licenseValidityPeriod > 0 ? this.timeDifference(this.getLicenseExpireDate(), currentDate) : -1;
    }

    public Instant getLicenseMaintenanceExpireDate() {
        return this.licenseMaintenancePeriod > 0 ? this.getStartDate().plus((long)this.licenseMaintenancePeriod, ChronoUnit.DAYS) : null;
    }

    public int getLicenseActivationDaysRemaining(Instant currentDate) {
        return this.activationRequired ? this.timeDifference(this.getStartDate(), currentDate) : -1;
    }

    private Instant getStartDate() {
        if (this.licenseTextGenerationTimeEqualToActivationTime) {
            return Instant.ofEpochMilli(this.getActivationCompletedDateTime());
        }
        return Instant.ofEpochMilli(this.getLicenseGenerationDateTime());
    }

    private int timeDifference(Instant time1, Instant time2) {
        if (time2 == null) {
            time2 = Instant.now();
        }
        long diffInDays = ChronoUnit.DAYS.between(time2.truncatedTo(ChronoUnit.DAYS), time1.truncatedTo(ChronoUnit.DAYS));
        return (int)Math.max(0L, diffInDays);
    }

    private int timeDifference(Instant time) {
        return this.timeDifference(time, null);
    }

    public void setActivationCompleted(boolean activationCompleted) {
        this.activationCompleted = activationCompleted;
        if (activationCompleted) {
            this.setActivationRequired(false);
            this.setActivationPeriod(0);
        }
    }

    public void setCustomUnsignedFeatures(JSONObject features) {
        if (features != null && !features.isEmpty()) {
            this.customUnsignedFeatures = new LinkedHashMap<String, String>();
            features.forEach((key, value) -> this.customUnsignedFeatures.put((String)key, value.toString()));
        }
    }

    public String getText() {
        return this.text;
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

    public void setText(String text) {
        this.text = text;
    }

    public void setLicenseKey(String licenseKey) {
        this.licenseKey = licenseKey;
    }

    public void setLicenseFileType(int licenseFileType) {
        this.licenseFileType = licenseFileType;
    }

    public void setDistributor(String distributor) {
        this.distributor = distributor;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setLicenseID(long licenseID) {
        this.licenseID = licenseID;
    }

    public void setLicenseGenerationDateTime(long licenseGenerationDateTime) {
        this.licenseGenerationDateTime = licenseGenerationDateTime;
    }

    public void setLicenseHardwareID(String licenseHardwareID) {
        this.licenseHardwareID = licenseHardwareID;
    }

    public void setLicenseQuantity(int licenseQuantity) {
        this.licenseQuantity = licenseQuantity;
    }

    public void setLicenseProductName(String licenseProductName) {
        this.licenseProductName = licenseProductName;
    }

    public void setLicenseValidProductID(String licenseValidProductID) {
        this.licenseValidProductID = licenseValidProductID;
    }

    public void setLicenseValidProductEdition(String licenseValidProductEdition) {
        this.licenseValidProductEdition = licenseValidProductEdition;
    }

    public void setLicenseValidProductVersion(String licenseValidProductVersion) {
        this.licenseValidProductVersion = licenseValidProductVersion;
    }

    public void setLicenseValidityPeriod(int licenseValidityPeriod) {
        this.licenseValidityPeriod = licenseValidityPeriod;
    }

    public void setLicenseMaintenancePeriod(int licenseMaintenancePeriod) {
        this.licenseMaintenancePeriod = licenseMaintenancePeriod;
    }

    public void setActivationID(long activationID) {
        this.activationID = activationID;
    }

    public void setActivationRequired(boolean activationRequired) {
        this.activationRequired = activationRequired;
    }

    public void setActivationCompletedDateTime(long activationCompletedDateTime) {
        this.activationCompletedDateTime = activationCompletedDateTime;
    }

    public void setActivationPeriod(int activationPeriod) {
        this.activationPeriod = activationPeriod;
    }

    public void setActivationReturnType(int activationReturnType) {
        this.activationReturnType = activationReturnType;
    }

    public void setActivationHardwareIDSelection(int activationHardwareIDSelection) {
        this.activationHardwareIDSelection = activationHardwareIDSelection;
    }

    public void setDatetimeCheck(int datetimeCheck) {
        this.datetimeCheck = datetimeCheck;
    }

    public void setDatetimeNTPServer(String datetimeNTPServer) {
        this.datetimeNTPServer = datetimeNTPServer;
    }

    public void setDatetimeNTPServerTimeout(int datetimeNTPServerTimeout) {
        this.datetimeNTPServerTimeout = datetimeNTPServerTimeout;
    }

    public void setDatetimeWebServer(String datetimeWebServer) {
        this.datetimeWebServer = datetimeWebServer;
    }

    public void setDatetimeWebServerTimeout(int datetimeWebServerTimeout) {
        this.datetimeWebServerTimeout = datetimeWebServerTimeout;
    }

    public void setLicenseSignature(String licenseSignature) {
        this.licenseSignature = licenseSignature;
    }

    public void setLicensePublicKey(String licensePublicKey) {
        this.licensePublicKey = licensePublicKey;
    }

    public void setLicenseTextGenerationTimeEqualToActivationTime(boolean licenseTextGenerationTimeEqualToActivationTime) {
        this.licenseTextGenerationTimeEqualToActivationTime = licenseTextGenerationTimeEqualToActivationTime;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof LicenseText)) {
            return false;
        }
        LicenseText other = (LicenseText)o;
        if (!other.canEqual(this)) {
            return false;
        }
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
        String this$text = this.getText();
        String other$text = other.getText();
        if (this$text == null ? other$text != null : !this$text.equals(other$text)) {
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

    protected boolean canEqual(Object other) {
        return other instanceof LicenseText;
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
        String $text = this.getText();
        result = result * 59 + ($text == null ? 43 : $text.hashCode());
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
        return "LicenseText(text=" + this.getText() + ", licenseKey=" + this.getLicenseKey() + ", licenseFileType=" + this.getLicenseFileType() + ", distributor=" + this.getDistributor() + ", fullName=" + this.getFullName() + ", email=" + this.getEmail() + ", company=" + this.getCompany() + ", phone=" + this.getPhone() + ", street=" + this.getStreet() + ", city=" + this.getCity() + ", zipCode=" + this.getZipCode() + ", country=" + this.getCountry() + ", licenseID=" + this.getLicenseID() + ", licenseGenerationDateTime=" + this.getLicenseGenerationDateTime() + ", licenseHardwareID=" + this.getLicenseHardwareID() + ", licenseQuantity=" + this.getLicenseQuantity() + ", licenseProductName=" + this.getLicenseProductName() + ", licenseValidProductID=" + this.getLicenseValidProductID() + ", licenseValidProductEdition=" + this.getLicenseValidProductEdition() + ", licenseValidProductVersion=" + this.getLicenseValidProductVersion() + ", licenseValidityPeriod=" + this.getLicenseValidityPeriod() + ", licenseMaintenancePeriod=" + this.getLicenseMaintenancePeriod() + ", activationID=" + this.getActivationID() + ", activationRequired=" + this.isActivationRequired() + ", activationCompleted=" + this.isActivationCompleted() + ", activationCompletedDateTime=" + this.getActivationCompletedDateTime() + ", activationPeriod=" + this.getActivationPeriod() + ", activationReturnType=" + this.getActivationReturnType() + ", activationHardwareIDSelection=" + this.getActivationHardwareIDSelection() + ", datetimeCheck=" + this.getDatetimeCheck() + ", datetimeNTPServer=" + this.getDatetimeNTPServer() + ", datetimeNTPServerTimeout=" + this.getDatetimeNTPServerTimeout() + ", datetimeWebServer=" + this.getDatetimeWebServer() + ", datetimeWebServerTimeout=" + this.getDatetimeWebServerTimeout() + ", customUnsignedFeatures=" + String.valueOf(this.getCustomUnsignedFeatures()) + ", licenseSignature=" + this.getLicenseSignature() + ", licensePublicKey=" + this.getLicensePublicKey() + ", licenseTextGenerationTimeEqualToActivationTime=" + this.isLicenseTextGenerationTimeEqualToActivationTime() + ", timestamp=" + this.getTimestamp() + ")";
    }

    public LicenseText() {
    }
}

