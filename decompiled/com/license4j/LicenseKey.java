/*
 * Decompiled with CFR 0.152.
 */
package com.license4j;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class LicenseKey {
    private String internalString;
    private String nameForValidation;
    private String companyForValidation;
    private String hardwareID;
    private String hardwareIDValidationMethod;
    private String theKey;
    private Instant generationDateTime;
    private boolean activationRequired;
    private boolean activationCompleted;
    private int activationPeriod;

    public LicenseKey(String key) {
        this.theKey = key;
    }

    public LicenseKey() {
        this.internalString = "";
        this.nameForValidation = "";
        this.companyForValidation = "";
        this.hardwareID = "";
        this.activationRequired = false;
        this.activationCompleted = false;
        this.activationPeriod = -1;
        this.generationDateTime = Instant.now();
    }

    public LicenseKey(String internalString, String hardwareID) {
        this.internalString = internalString != null ? internalString : "";
        this.nameForValidation = "";
        this.companyForValidation = "";
        this.hardwareID = hardwareID != null ? hardwareID : "";
        this.generationDateTime = Instant.now();
    }

    public LicenseKey(String internalString, String nameForValidation, String companyForValidation, String hardwareID) {
        this.internalString = Objects.requireNonNullElse(internalString, "");
        this.nameForValidation = Objects.requireNonNullElse(nameForValidation, "");
        this.companyForValidation = Objects.requireNonNullElse(companyForValidation, "");
        this.hardwareID = Objects.requireNonNullElse(hardwareID, "");
        this.generationDateTime = Instant.now();
    }

    public void setHardwareID(String hardwareID) {
        this.hardwareID = hardwareID == null || hardwareID.isEmpty() ? "" : hardwareID.trim();
    }

    public void setActivationRequired(boolean activationRequired) {
        this.activationRequired = activationRequired;
        if (this.getActivationPeriod() == -1) {
            this.setActivationPeriod(0);
        }
    }

    public void setActivationPeriod(int activationPeriod) {
        if (activationPeriod != 0 && activationPeriod != 10 && activationPeriod != 20 && activationPeriod != 30 && activationPeriod != 60 && activationPeriod != 90) {
            System.err.println("Activation period should be one of 0, 10, 20, 30, 60, 90 for license key. It is set as 0.");
            this.activationPeriod = 0;
        } else {
            this.activationPeriod = activationPeriod;
        }
    }

    public int getLicenseActivationDaysRemaining(Instant currentDate) {
        return this.activationRequired ? this.timeDifference(this.generationDateTime.plus((long)this.activationPeriod, ChronoUnit.DAYS), currentDate) : -1;
    }

    private int timeDifference(Instant time1, Instant time2) {
        long diffInDays;
        if (time2 == null) {
            time2 = Instant.now();
        }
        return (diffInDays = ChronoUnit.DAYS.between(time2, time1)) < 0L ? 0 : (int)diffInDays + 1;
    }

    public void setActivationCompleted() {
        this.activationCompleted = true;
    }

    public String getInternalString() {
        return this.internalString;
    }

    public String getNameForValidation() {
        return this.nameForValidation;
    }

    public String getCompanyForValidation() {
        return this.companyForValidation;
    }

    public String getHardwareID() {
        return this.hardwareID;
    }

    public String getHardwareIDValidationMethod() {
        return this.hardwareIDValidationMethod;
    }

    public String getTheKey() {
        return this.theKey;
    }

    public Instant getGenerationDateTime() {
        return this.generationDateTime;
    }

    public boolean isActivationRequired() {
        return this.activationRequired;
    }

    public boolean isActivationCompleted() {
        return this.activationCompleted;
    }

    public int getActivationPeriod() {
        return this.activationPeriod;
    }

    public void setInternalString(String internalString) {
        this.internalString = internalString;
    }

    public void setNameForValidation(String nameForValidation) {
        this.nameForValidation = nameForValidation;
    }

    public void setCompanyForValidation(String companyForValidation) {
        this.companyForValidation = companyForValidation;
    }

    public void setHardwareIDValidationMethod(String hardwareIDValidationMethod) {
        this.hardwareIDValidationMethod = hardwareIDValidationMethod;
    }

    public void setTheKey(String theKey) {
        this.theKey = theKey;
    }

    public void setGenerationDateTime(Instant generationDateTime) {
        this.generationDateTime = generationDateTime;
    }

    public void setActivationCompleted(boolean activationCompleted) {
        this.activationCompleted = activationCompleted;
    }
}

