/*
 * Decompiled with CFR 0.152.
 */
package com.license4j.validator;

import com.license4j.License;
import com.license4j.ValidationStatus;
import com.license4j.exceptions.LicenseSecurityException;
import com.license4j.hardware.HardwareID;
import com.license4j.licensetext.LicenseText;
import com.license4j.util.SignatureParams;
import com.license4j.validator.ValidatorUtils;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LicenseTextValidator {
    private static final Logger log = LoggerFactory.getLogger(LicenseTextValidator.class);

    public static synchronized License validate(String licenseString, String publicKey, String productID, String productEdition, String currentProductVersion) throws LicenseSecurityException {
        LicenseText licenseText = LicenseTextValidator.initLicenseText(licenseString, publicKey);
        Instant currentDateInstant = Instant.now();
        Instant realCurrentDate = ValidatorUtils.getRealCurrentDate(licenseText);
        if (realCurrentDate != null && ChronoUnit.SECONDS.between(realCurrentDate, currentDateInstant) > 86400L) {
            throw new LicenseSecurityException("INCORRECT_SYSTEM_TIME");
        }
        if (licenseText == null) {
            throw new LicenseSecurityException("Given license text is null.");
        }
        String baseProperties = licenseText.getBaseProperties();
        String licenseSignature = licenseText.getLicenseSignature();
        if (licenseSignature == null) {
            throw new LicenseSecurityException("Signature in given license text is null.");
        }
        SignatureParams signatureParams = new SignatureParams(publicKey);
        if (Instant.ofEpochMilli(licenseText.getLicenseGenerationDateTime()).isAfter(LocalDate.of(2023, 10, 14).atStartOfDay(ZoneId.systemDefault()).toInstant()) && !signatureParams.validateLicenseText(baseProperties, licenseSignature)) {
            return new License(licenseText, publicKey, ValidationStatus.LICENSE_INVALID);
        }
        if (!HardwareID.validate(licenseText)) {
            return new License(licenseText, publicKey, ValidationStatus.MISMATCH_HARDWARE_ID);
        }
        Instant currentProductReleaseDateInstant = null;
        return LicenseTextValidator.checkLicenseConditions(licenseText, publicKey, productID, productEdition, currentProductVersion, currentDateInstant, currentProductReleaseDateInstant);
    }

    private static LicenseText initLicenseText(String licenseString, String publicKey) throws LicenseSecurityException {
        LicenseText licenseText;
        try {
            licenseText = new LicenseText(licenseString, publicKey);
        }
        catch (LicenseSecurityException var23) {
            log.debug("EX1168", var23);
            throw new LicenseSecurityException(var23.getLocalizedMessage());
        }
        return licenseText;
    }

    private static License checkLicenseConditions(LicenseText licenseText, String publicKey, String productID, String productEdition, String currentProductVersion, Instant currentDate, Instant currentProductReleaseDate) throws LicenseSecurityException {
        String validProductID = licenseText.getLicenseValidProductID();
        String validProductEdition = licenseText.getLicenseValidProductEdition();
        String validProductVersion = licenseText.getLicenseValidProductVersion();
        Instant licenseMaintenanceExpireDate = licenseText.getLicenseMaintenanceExpireDate();
        if (productID != null && validProductID != null && !productID.equalsIgnoreCase(validProductID)) {
            return new License(licenseText, publicKey, ValidationStatus.MISMATCH_PRODUCT_ID);
        }
        if (productEdition != null && validProductEdition != null && !productEdition.equalsIgnoreCase(validProductEdition)) {
            return new License(licenseText, publicKey, ValidationStatus.MISMATCH_PRODUCT_EDITION);
        }
        if (currentProductVersion != null && validProductVersion != null && !currentProductVersion.equalsIgnoreCase(validProductVersion)) {
            return new License(licenseText, publicKey, ValidationStatus.MISMATCH_PRODUCT_VERSION);
        }
        if (licenseText.isGenerationDateTimeInFuture(currentDate)) {
            return new License(licenseText, publicKey, ValidationStatus.INCORRECT_SYSTEM_TIME);
        }
        if (licenseText.getLicenseExpireDaysRemaining(currentDate) == 0) {
            return new License(licenseText, publicKey, ValidationStatus.LICENSE_EXPIRED);
        }
        if (licenseMaintenanceExpireDate != null && currentProductReleaseDate != null && currentProductReleaseDate.isAfter(licenseMaintenanceExpireDate)) {
            return new License(licenseText, publicKey, ValidationStatus.LICENSE_MAINTENANCE_EXPIRED);
        }
        return new License(licenseText, publicKey, ValidationStatus.LICENSE_VALID);
    }
}

