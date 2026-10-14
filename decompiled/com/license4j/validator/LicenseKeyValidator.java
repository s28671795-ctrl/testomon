/*
 * Decompiled with CFR 0.152.
 */
package com.license4j.validator;

import com.license4j.License;
import com.license4j.LicenseKey;
import com.license4j.ValidationStatus;
import com.license4j.hardware.HardwareID;
import com.license4j.validator.ValidatorUtils;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LicenseKeyValidator {
    private static final Logger log = LoggerFactory.getLogger(LicenseKeyValidator.class);
    private static final int MINIMUM_PUBLIC_KEY_LENGTH = 32;
    private static final int LICENSE_STRING_VALID_LENGTH = 29;
    private static final String SIGNATURE_ALGORITHM = "SHA512withECDSA";
    private static final List<Integer> VALID_LICENSE_LENGTHS = List.of(Integer.valueOf(65), Integer.valueOf(67), Integer.valueOf(68), Integer.valueOf(69), Integer.valueOf(70), Integer.valueOf(29));

    private LicenseKeyValidator() {
        throw new IllegalStateException("Utility class");
    }

    public static synchronized License validateLicenseKey(String licenseKeyString, String publicKey, String internalString, String nameForValidation, String companyForValidation, int hardwareIdValidation) {
        publicKey = publicKey.replace("\n", "");
        if (LicenseKeyValidator.isInvalidLicenseString(licenseKeyString) || LicenseKeyValidator.isPublicKeyInvalid(publicKey)) {
            return new License(ValidationStatus.LICENSE_INVALID);
        }
        if (licenseKeyString.trim().length() == 29) {
            return LicenseKeyValidator.updateLicenseKey(licenseKeyString, publicKey, internalString, nameForValidation, companyForValidation, hardwareIdValidation);
        }
        return new License(ValidationStatus.LICENSE_INVALID);
    }

    private static boolean isPublicKeyInvalid(String publicKey) {
        return !publicKey.contains(SIGNATURE_ALGORITHM) || publicKey.length() < 32;
    }

    private static boolean isInvalidLicenseString(String licenseKeyString) {
        return licenseKeyString == null || licenseKeyString.contains("0") || licenseKeyString.contains("O") || !licenseKeyString.contains("-") || !VALID_LICENSE_LENGTHS.contains(licenseKeyString.length());
    }

    private static synchronized License updateLicenseKey(String licenseString, String publicKey, String internalString, String nameforValidation, String companyforValidation, int hardwareIdValidation) {
        if (licenseString == null || licenseString.length() != 29 || licenseString.contains("0") || licenseString.contains("O") || !licenseString.contains("-")) {
            return new License(ValidationStatus.LICENSE_INVALID);
        }
        licenseString = licenseString.toUpperCase(Locale.US);
        String hardwareID = HardwareID.getHardwareID(hardwareIdValidation);
        LicenseKey licenseKey = new LicenseKey(internalString, LicenseKeyValidator.sanitize(nameforValidation), LicenseKeyValidator.sanitize(companyforValidation), LicenseKeyValidator.sanitize(hardwareID));
        licenseKey.setTheKey(licenseString);
        try {
            int publicKeyHashcode = (publicKey + publicKey.substring(16, 90)).hashCode();
            String internalHashcode = String.valueOf((licenseKey.getInternalString() + publicKeyHashcode + licenseKey.getNameForValidation() + licenseKey.getCompanyForValidation() + licenseKey.getHardwareID()).hashCode());
            if (ValidatorUtils.checkLicenseString(licenseString, internalHashcode)) {
                return new License(licenseKey, publicKey, ValidationStatus.LICENSE_VALID);
            }
            internalHashcode = String.valueOf((licenseKey.getInternalString() + publicKeyHashcode + "-ac" + licenseKey.getNameForValidation() + licenseKey.getCompanyForValidation() + licenseKey.getHardwareID()).hashCode());
            if (ValidatorUtils.checkLicenseString(licenseString, internalHashcode)) {
                licenseKey.setActivationCompleted(true);
                return new License(licenseKey, publicKey, ValidationStatus.LICENSE_VALID);
            }
            hardwareID = String.valueOf(publicKeyHashcode);
            return LicenseKeyValidator.handleDateRangeLicenseValidation(licenseKey, hardwareID, publicKey, licenseString);
        }
        catch (Exception e) {
            log.debug("EX1150", e);
            return new License(licenseKey, publicKey, ValidationStatus.LICENSE_INVALID);
        }
    }

    private static String sanitize(String input) {
        return input == null ? "" : input;
    }

    private static License handleDateRangeLicenseValidation(LicenseKey licenseKey, String hardwareID, String publicKey, String licenseString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        Instant nextDay = Instant.now().plus(1L, ChronoUnit.DAYS);
        Instant startDate = LocalDate.of(2020, Month.JANUARY, 1).atStartOfDay(ZoneId.systemDefault()).toInstant();
        while (nextDay.isAfter(startDate)) {
            for (int i = 0; i <= 5; ++i) {
                int activationDays = LicenseKeyValidator.calculateActivationDays(i);
                String formattedDate = dateFormat.format(Date.from(nextDay));
                String internalString = licenseKey.getInternalString() + hardwareID + "-ar-" + activationDays + "-" + formattedDate + licenseKey.getNameForValidation() + licenseKey.getCompanyForValidation() + licenseKey.getHardwareID();
                String internalHashcode = String.valueOf(internalString.hashCode());
                if (!ValidatorUtils.checkLicenseString(licenseString, internalHashcode)) continue;
                return LicenseKeyValidator.handleValidLicense(licenseKey, publicKey, nextDay, activationDays);
            }
            nextDay = nextDay.minus(1L, ChronoUnit.DAYS);
        }
        return new License(licenseKey, publicKey, ValidationStatus.LICENSE_INVALID);
    }

    private static int calculateActivationDays(int index) {
        if (index == 4) {
            return 60;
        }
        if (index == 5) {
            return 90;
        }
        return index * 10;
    }

    private static License handleValidLicense(LicenseKey licenseKey, String publicKey, Instant nextDay, int activationDays) {
        long diff = ChronoUnit.DAYS.between(nextDay.plus((long)activationDays, ChronoUnit.DAYS), Instant.now());
        licenseKey.setActivationRequired(true);
        licenseKey.setActivationPeriod(activationDays);
        licenseKey.setGenerationDateTime(nextDay);
        if (diff <= 0L || activationDays == 0) {
            return new License(licenseKey, publicKey, ValidationStatus.LICENSE_VALID);
        }
        return new License(licenseKey, publicKey, ValidationStatus.LICENSE_EXPIRED);
    }
}

