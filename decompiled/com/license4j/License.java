/*
 * Decompiled with CFR 0.152.
 */
package com.license4j;

import com.license4j.ActivationStatus;
import com.license4j.LicenseKey;
import com.license4j.ValidationStatus;
import com.license4j.exceptions.LicenseSecurityException;
import com.license4j.hardware.HardwareID;
import com.license4j.licensetext.LicenseText;
import com.license4j.util.Crypto;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Instant;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class License {
    private static final Logger log = LoggerFactory.getLogger(License.class);
    private LicenseText licenseText;
    private LicenseKey licenseKey;
    private ValidationStatus validationStatus;
    private ActivationStatus activationStatus;
    private String publicKey;
    private long timestamp;

    public License(LicenseText licenseText, String publicKey, ValidationStatus validationStatus) {
        this.licenseText = licenseText;
        this.validationStatus = validationStatus;
        this.publicKey = publicKey;
    }

    public License(LicenseKey licenseKey, String publicKey, ValidationStatus validationStatus) {
        this.licenseKey = licenseKey;
        this.validationStatus = validationStatus;
        this.publicKey = publicKey;
    }

    public License(ValidationStatus validationStatus) {
        this.validationStatus = validationStatus;
    }

    public ActivationStatus getActivationStatus() {
        if (this.activationStatus == null) {
            if (this.licenseText != null) {
                this.activationStatus = this.licenseText.isActivationRequired() ? ActivationStatus.ACTIVATION_REQUIRED : (this.licenseText.isActivationCompleted() ? ActivationStatus.ACTIVATION_COMPLETED : ActivationStatus.ACTIVATION_NOT_REQUIRED);
            } else if (this.licenseKey != null) {
                this.activationStatus = this.licenseKey.isActivationRequired() ? ActivationStatus.ACTIVATION_REQUIRED : (this.licenseKey.isActivationCompleted() ? ActivationStatus.ACTIVATION_COMPLETED : ActivationStatus.ACTIVATION_NOT_REQUIRED);
            }
        }
        return this.activationStatus;
    }

    public String getLicenseString() {
        if (this.licenseText != null) {
            try {
                return this.licenseText.getContent(this.publicKey);
            }
            catch (LicenseSecurityException e) {
                log.error("Error while getting license string", e);
            }
        } else if (this.licenseKey != null) {
            if (this.getActivationStatus() == ActivationStatus.ACTIVATION_COMPLETED) {
                return this.licenseKey.getTheKey() + "-" + this.licenseKey.getHardwareIDValidationMethod();
            }
            return this.licenseKey.getTheKey();
        }
        return null;
    }

    public String getManualActivationRequestString() {
        if (this.isActivationNotRequired()) {
            return null;
        }
        String requestStringBuilder = this.getHostName() + "@" + this.getOS() + "@" + String.valueOf(this.getHardwareKeyBuilder()) + "@" + this.publicKey.hashCode() + "@" + this.getLicenseString() + "@";
        return this.encryptString(requestStringBuilder);
    }

    public String getManualActivationRequestStringWithCustomHardwareID(String customHardwareID) {
        if (this.isActivationNotRequired()) {
            return null;
        }
        if (Objects.requireNonNullElse(customHardwareID, "").isEmpty()) {
            return null;
        }
        String result = this.getHostName() + "@" + this.getOS() + "@cid-" + customHardwareID + "@" + this.publicKey.hashCode() + "@" + this.getLicenseString() + "@";
        return this.encryptString(result);
    }

    public String getManualActivationRequestStringWithUserInformation(String fullname, String registeredto, String email, String company, String street, String telnumber, String faxnumber, String city, String zip2, String country) {
        if (this.isActivationNotRequired()) {
            return null;
        }
        StringBuilder result = new StringBuilder().append(this.getHostName()).append("@").append(this.getOS()).append("@").append((CharSequence)this.getHardwareKeyBuilder()).append("@").append(this.publicKey.hashCode()).append("@").append(this.getLicenseString()).append("@").append("false");
        this.addUserInputs(result, fullname, registeredto, email, company, street, telnumber, faxnumber, city, zip2, country);
        return this.encryptString(result.toString());
    }

    public String getManualActivationRequestStringWithCustomHardwareIDWithUserInformation(String customHardwareID, boolean modifyOnlyOnFirstActivation, String fullname, String registeredto, String email, String company, String street, String telnumber, String faxnumber, String city, String zip2, String country) {
        if (this.isActivationNotRequired()) {
            return null;
        }
        if (Objects.requireNonNullElse(customHardwareID, "").isEmpty()) {
            return null;
        }
        StringBuilder result = new StringBuilder().append(this.getHostName()).append("@").append(this.getOS()).append("@cid-").append(customHardwareID).append("@").append(this.publicKey.hashCode()).append("@").append(this.getLicenseString()).append("@").append(modifyOnlyOnFirstActivation ? "true" : "false");
        this.addUserInputs(result, fullname, registeredto, email, company, street, telnumber, faxnumber, city, zip2, country);
        return this.encryptString(result.toString());
    }

    public String getManualDeactivationRequestString() {
        if (this.isActivationNotCompleted()) {
            return null;
        }
        String baseString = String.join((CharSequence)"@", this.getHostName(), this.getOS(), String.valueOf(this.publicKey.hashCode()), this.getLicenseString());
        StringBuilder builder = new StringBuilder(baseString);
        Optional.ofNullable(this.getActivationID()).ifPresent(activationID -> builder.append("@").append((String)activationID));
        return this.encryptString(builder.toString(), "rManuelDeActivate");
    }

    public boolean isActivationRequired() {
        return this.licenseText != null && this.licenseText.isActivationRequired() || this.licenseKey != null && this.licenseKey.isActivationRequired();
    }

    public boolean isActivationCompleted() {
        return this.licenseText != null && this.licenseText.isActivationCompleted() || this.licenseKey != null && this.licenseKey.isActivationCompleted();
    }

    public int getLicenseActivationDaysRemaining(Instant currentDate) {
        if (this.licenseText != null) {
            return this.licenseText.getLicenseActivationDaysRemaining(currentDate);
        }
        if (this.licenseKey != null) {
            return this.licenseKey.getLicenseActivationDaysRemaining(currentDate);
        }
        return -1;
    }

    public int getLicenseActivationPeriod(Date currentDate) {
        if (this.licenseText != null) {
            return this.licenseText.getActivationPeriod();
        }
        if (this.licenseKey != null) {
            return this.licenseKey.getActivationPeriod();
        }
        return -1;
    }

    protected final void setServerVersion(int serverVersion) {
    }

    private String getHostName() {
        Optional<String> remoteAddress;
        try {
            remoteAddress = Optional.of(InetAddress.getLocalHost().getHostName());
        }
        catch (UnknownHostException e) {
            remoteAddress = Optional.empty();
        }
        return remoteAddress.filter(name -> !name.isEmpty()).orElse("unknown");
    }

    private String getOS() {
        return Objects.requireNonNullElse(System.getProperty("os.name"), "unknown");
    }

    private String getActivationID() {
        return Optional.ofNullable(this.licenseText).map(LicenseText::getActivationID).map(String::valueOf).orElse(null);
    }

    private String sanitizeInput(String input) {
        return Objects.requireNonNullElse(input, "~").isEmpty() ? "~" : input;
    }

    private String encryptString(String data) {
        return this.encryptString(data, "rManuelActivate");
    }

    private String encryptString(String data, String key) {
        String encryptedString = "";
        try {
            encryptedString = Crypto.encrypt(data, key);
        }
        catch (Exception exception) {
            // empty catch block
        }
        return encryptedString;
    }

    private boolean isActivationNotRequired() {
        return this.activationStatus == ActivationStatus.ACTIVATION_NOT_REQUIRED;
    }

    private boolean isActivationNotCompleted() {
        return this.activationStatus != ActivationStatus.ACTIVATION_COMPLETED;
    }

    private StringBuilder getHardwareKeyBuilder() {
        String[] hardwareIDs;
        StringBuilder result = new StringBuilder();
        for (String hardwareID : hardwareIDs = new String[]{HardwareID.getHardwareIDFromHostName(), HardwareID.getHardwareIDFromEthernetAddress(), HardwareID.getHardwareIDFromVolumeSerialNumber(), HardwareID.getHardwareIDFromHDDSerial()}) {
            if (hardwareID == null) continue;
            if (result.length() > 0) {
                result.append("!");
            }
            result.append(hardwareID);
        }
        return result;
    }

    private void addUserInputs(StringBuilder result, String fullname, String registeredto, String email, String company, String street, String telnumber, String faxnumber, String city, String zip2, String country) {
        String[] userInputs;
        for (String userInput : userInputs = new String[]{fullname, registeredto, email, company, street, telnumber, faxnumber, city, zip2, country}) {
            result.append("@").append(this.sanitizeInput(userInput));
        }
    }

    public LicenseText getLicenseText() {
        return this.licenseText;
    }

    public LicenseKey getLicenseKey() {
        return this.licenseKey;
    }

    public ValidationStatus getValidationStatus() {
        return this.validationStatus;
    }

    public String getPublicKey() {
        return this.publicKey;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public void setLicenseText(LicenseText licenseText) {
        this.licenseText = licenseText;
    }

    public void setLicenseKey(LicenseKey licenseKey) {
        this.licenseKey = licenseKey;
    }

    public void setValidationStatus(ValidationStatus validationStatus) {
        this.validationStatus = validationStatus;
    }

    public void setActivationStatus(ActivationStatus activationStatus) {
        this.activationStatus = activationStatus;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}

