/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.license;

import com.incoresoft.license.License;
import com.incoresoft.license.exceptions.LicenseActivationException;
import com.incoresoft.license.licensestates.DemoState;
import com.incoresoft.license.licensestates.LicenseState;
import com.incoresoft.license.licensestates.NormalState;
import com.incoresoft.license.model.LicenseData;
import com.incoresoft.license.model.LicenseStatus;
import com.incoresoft.license.model.LicenseType;
import com.incoresoft.license.utils.LicenseValidationManager;
import com.incoresoft.middleware.utils.TextUtils;
import com.license4j.ActivationStatus;
import com.license4j.ValidationStatus;
import com.license4j.exceptions.LicenseSecurityException;
import com.license4j.licensetext.LicenseText;
import com.license4j.validator.LicenseValidator;
import java.security.SignatureException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LicenseImpl
implements License {
    private static final Logger log = LoggerFactory.getLogger(LicenseImpl.class);
    private volatile LicenseSnapshot snapshot;
    private final String licenseKey;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private final LicenseValidationManager licenseValidationManager;

    public LicenseImpl(String licenseKey, String activatedLicenseText, LicenseValidationManager licenseValidationManager) {
        this.licenseValidationManager = licenseValidationManager;
        this.licenseKey = licenseKey;
        LicenseStatus status = this.checkLicenseText(activatedLicenseText);
        log.info("License text validation " + String.valueOf((Object)status));
    }

    @Override
    public LicenseStatus getStatus() {
        LicenseSnapshot current = this.snapshot;
        if (current.validationStatus() != ValidationStatus.LICENSE_VALID) {
            return LicenseValidationManager.getByStatus(current.validationStatus());
        }
        return LicenseValidationManager.getByStatus(current.activationStatus());
    }

    @Override
    public synchronized LicenseStatus activateLicense() throws LicenseSecurityException, SignatureException {
        com.license4j.License activatedLicense = this.licenseValidationManager.validateLicenseKey(this.licenseKey);
        if ((activatedLicense = LicenseValidator.autoActivate(activatedLicense, "https://activate.incoresoft.com/api/v1/activations/activate")).getValidationStatus() == ValidationStatus.LICENSE_VALID && activatedLicense.isActivationCompleted()) {
            this.publishNormal(activatedLicense);
            return LicenseStatus.ACTIVATION_COMPLETED;
        }
        this.publishDemo(activatedLicense);
        if (activatedLicense.getActivationStatus() == ActivationStatus.LICENSE_NOT_FOUND_ON_ACTIVATION_SERVER) {
            return LicenseStatus.LICENSE_NOT_FOUND_ON_ACTIVATION_SERVER;
        }
        if (activatedLicense.getValidationStatus() == ValidationStatus.LICENSE_EXPIRED) {
            return LicenseStatus.LICENSE_EXPIRED;
        }
        throw new LicenseActivationException(LicenseValidationManager.getByStatus(activatedLicense.getActivationStatus()), activatedLicense.getValidationStatus());
    }

    @Override
    public synchronized LicenseStatus deactivateLicense() {
        LicenseSnapshot current = this.snapshot;
        if (current == null || current.license() == null) {
            return LicenseStatus.DEACTIVATION_NOT_ALLOWED_FOR_LICENSE;
        }
        if (TextUtils.isBlank(this.licenseKey)) {
            this.publishDemo(this.licenseValidationManager.validateLicenseText(""));
            return LicenseStatus.DEACTIVATION_COMPLETED;
        }
        com.license4j.License deactivatedLicense = LicenseValidator.autoDeactivate(current.license(), "https://activate.incoresoft.com/api/v1/activations/deactivate");
        if (deactivatedLicense.getActivationStatus() == ActivationStatus.DEACTIVATION_COMPLETED) {
            this.publishDemo(current.license());
            return LicenseStatus.DEACTIVATION_COMPLETED;
        }
        log.info("License deactivation failed - " + String.valueOf((Object)deactivatedLicense.getActivationStatus()));
        return LicenseValidationManager.getByStatus(deactivatedLicense.getActivationStatus());
    }

    @Override
    public void updateLicense() {
        try {
            this.activateLicense();
        }
        catch (LicenseSecurityException | SignatureException e) {
            throw new RuntimeException(e);
        }
    }

    private LicenseStatus checkLicenseText(String text) {
        com.license4j.License tempLicense = this.licenseValidationManager.validateLicenseText(text);
        if (tempLicense.getValidationStatus() == ValidationStatus.LICENSE_VALID && tempLicense.getActivationStatus() == ActivationStatus.ACTIVATION_COMPLETED) {
            this.publishNormal(tempLicense);
            return LicenseStatus.LICENSE_VALID;
        }
        if (tempLicense.getValidationStatus() != ValidationStatus.LICENSE_VALID) {
            this.publishDemo(tempLicense);
            log.warn("License text validation failed - " + String.valueOf((Object)tempLicense.getValidationStatus()));
            return LicenseValidationManager.getByStatus(tempLicense.getValidationStatus());
        }
        this.publishDemo(tempLicense);
        log.warn("License text validation failed - " + String.valueOf((Object)tempLicense.getActivationStatus()));
        return LicenseValidationManager.getByStatus(tempLicense.getActivationStatus());
    }

    @Override
    public String getLicenseText() {
        return this.snapshot.licenseString();
    }

    @Override
    public String getLicenseKey() {
        return this.licenseKey;
    }

    @Override
    public Map<String, String> getFeatures() {
        return this.snapshot.state().features();
    }

    @Override
    public boolean isDemo() {
        return this.snapshot.state().isDemo();
    }

    @Override
    public boolean isValid() {
        return this.snapshot.state().isValid();
    }

    @Override
    public boolean isActivated() {
        return this.snapshot.state().isActivated();
    }

    @Override
    public LicenseData getLicenseData() {
        LicenseSnapshot current = this.snapshot;
        LicenseState currentState = current.state();
        LicenseDetails details = current.details();
        LicenseStatus status = this.getLicenseStatus(current);
        LicenseData.Address address = new LicenseData.Address(details.country(), details.city(), details.street(), details.zipCode());
        return new LicenseData(details.licenseId(), details.productId(), this.getLicenseKey() != null ? this.getLicenseKey() : details.licenseKey(), status.name(), details.licenseId() == 3L ? LicenseType.Trial.name() : LicenseType.TimeLimited.name(), currentState.isValid(), currentState.isActivated(), details.distributor(), details.company(), details.email(), details.phone(), null, address, LicenseImpl.toLocalDate(currentState.generationDate()).format(formatter), LicenseImpl.toLocalDate(currentState.activationDate()).format(formatter), LicenseImpl.toLocalDate(currentState.expireDate()).format(formatter), LicenseImpl.toLocalDate(currentState.maintenanceExpireDate()).format(formatter), details.features());
    }

    private LicenseStatus getLicenseStatus(LicenseSnapshot current) {
        LicenseStatus status;
        if (!current.hasLicenseText()) {
            status = LicenseStatus.NO_LICENSE;
        } else {
            LicenseStatus validationStatus = LicenseValidationManager.getByStatus(current.validationStatus());
            LicenseStatus activationStatus = LicenseValidationManager.getByStatus(current.activationStatus());
            status = validationStatus != LicenseStatus.LICENSE_VALID ? validationStatus : activationStatus;
        }
        return status;
    }

    @Override
    public LocalDate generationDate() {
        return LicenseImpl.toLocalDate(this.snapshot.state().generationDate());
    }

    @Override
    public LocalDate activationDate() {
        return LicenseImpl.toLocalDate(this.snapshot.state().activationDate());
    }

    @Override
    public LocalDate expireDate() {
        return LicenseImpl.toLocalDate(this.snapshot.state().expireDate());
    }

    @Override
    public LocalDate maintenanceExpireDate() {
        return LicenseImpl.toLocalDate(this.snapshot.state().maintenanceExpireDate());
    }

    @Override
    public Optional<Long> licenseTextGenerationTimestamp() {
        return this.snapshot.state().licenseTextGenerationTimestamp();
    }

    @Override
    public synchronized void setDemoState() {
        this.publishDemo(this.snapshot.license());
    }

    private void publishNormal(com.license4j.License license) {
        this.publish(license, new NormalState(license));
    }

    private void publishDemo(com.license4j.License license) {
        this.publish(license, new DemoState(license));
    }

    private void publish(com.license4j.License license, LicenseState newState) {
        this.snapshot = LicenseSnapshot.of(license, newState);
    }

    private static LocalDate toLocalDate(Instant instant) {
        return instant.atZone(ZoneId.systemDefault()).toLocalDate();
    }

    private record LicenseSnapshot(com.license4j.License license, LicenseState state, ValidationStatus validationStatus, ActivationStatus activationStatus, String licenseString, boolean hasLicenseText, LicenseDetails details) {
        static LicenseSnapshot of(com.license4j.License license, LicenseState state) {
            LicenseText licenseText = license.getLicenseText();
            return new LicenseSnapshot(license, state, license.getValidationStatus(), license.getActivationStatus(), license.getLicenseString(), licenseText != null, licenseText != null ? LicenseDetails.of(licenseText) : LicenseDetails.EMPTY);
        }
    }

    private record LicenseDetails(long licenseId, String productId, String licenseKey, String distributor, String company, String email, String phone, String country, String city, String street, String zipCode, Map<String, Object> features) {
        private static final LicenseDetails EMPTY = LicenseDetails.of(new LicenseText());

        static LicenseDetails of(LicenseText licenseText) {
            return new LicenseDetails(licenseText.getLicenseID(), licenseText.getLicenseValidProductID(), licenseText.getLicenseKey(), LicenseDetails.getOrEmptyString(licenseText.getDistributor()), LicenseDetails.getOrEmptyString(licenseText.getCompany()), LicenseDetails.getOrEmptyString(licenseText.getEmail()), LicenseDetails.getOrEmptyString(licenseText.getPhone()), LicenseDetails.getOrEmptyString(licenseText.getCountry()), LicenseDetails.getOrEmptyString(licenseText.getCity()), LicenseDetails.getOrEmptyString(licenseText.getStreet()), LicenseDetails.getOrEmptyString(licenseText.getZipCode()), LicenseDetails.parseFeatures(licenseText.getCustomUnsignedFeatures()));
        }

        private static String getOrEmptyString(String str) {
            return str != null ? str : "";
        }

        private static Map<String, Object> parseFeatures(Map<String, String> features) {
            return Objects.requireNonNullElseGet(features, Map::of).entrySet().stream().collect(Collectors.toUnmodifiableMap(Map.Entry::getKey, entry -> {
                String value = (String)entry.getValue();
                try {
                    return Integer.parseInt(value);
                }
                catch (NumberFormatException e) {
                    return value;
                }
            }));
        }
    }
}

