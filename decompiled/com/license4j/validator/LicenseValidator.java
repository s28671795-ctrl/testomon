/*
 * Decompiled with CFR 0.152.
 */
package com.license4j.validator;

import com.alibaba.fastjson.JSONObject;
import com.license4j.ActivationStatus;
import com.license4j.License;
import com.license4j.ValidationStatus;
import com.license4j.dto.LicenseDataDTO;
import com.license4j.exceptions.LicenseSecurityException;
import com.license4j.hardware.HardwareID;
import com.license4j.licensetext.LicenseText;
import com.license4j.util.HTTP;
import com.license4j.validator.LicenseKeyValidator;
import com.license4j.validator.LicenseTextValidator;
import com.license4j.validator.ValidatorUtils;
import java.security.SignatureException;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LicenseValidator {
    private static final Logger log = LoggerFactory.getLogger(LicenseValidator.class);
    public static final String RELEASE_VERSION = "2.0.5";
    private static final String license_base_url = "http://localhost:7000";
    private static final int RESULT_MIN_LENGTH = 10;

    public static synchronized License validate(String licenseString, String publickey, String productID, String productEdition, String currentProductVersion, Date currentDate, Date currentProductReleaseDate) {
        if (licenseString != null && licenseString.length() >= 32) {
            if (ValidatorUtils.updatePublicKey(publickey = publickey.replace("\n", "")).length() >= 32) {
                if (licenseString.indexOf("l", licenseString.length() - 2) != -1) {
                    licenseString = licenseString.substring(0, licenseString.length() - 1);
                }
                try {
                    return LicenseTextValidator.validate(licenseString, publickey, productID, productEdition, currentProductVersion);
                }
                catch (LicenseSecurityException e) {
                    log.debug("EX1101", e);
                    return new License(ValidationStatus.LICENSE_INVALID);
                }
            }
            return new License(ValidationStatus.LICENSE_INVALID);
        }
        return new License(ValidationStatus.LICENSE_INVALID);
    }

    public static synchronized License validate(String licenseKey, String publicKey, String internalString, String nameForValidation, String companyForValidation, int hardwareIdValidation) {
        return LicenseKeyValidator.validateLicenseKey(licenseKey, publicKey, internalString, nameForValidation, companyForValidation, hardwareIdValidation);
    }

    public static synchronized License autoActivate(License license, String serverURL) throws LicenseSecurityException, SignatureException {
        ValidationStatus validationStatus = license.getValidationStatus();
        if (validationStatus != ValidationStatus.LICENSE_VALID) {
            log.debug("Given License Status is not VALID");
            return license;
        }
        if (!license.isActivationRequired()) {
            license.setActivationStatus(ActivationStatus.ACTIVATION_NOT_REQUIRED);
            return license;
        }
        LicenseDataDTO licenseData = new LicenseDataDTO(license.getPublicKey(), ValidatorUtils.getHostName(), HardwareID.generateHardwareResult(), license, serverURL, false);
        String result = LicenseValidator.changeLicenseStatus(licenseData);
        if (result.length() < 10) {
            license.setActivationStatus(ActivationStatus.getStatus(result));
            return license;
        }
        return LicenseValidator.handleLongResult(license, result, serverURL);
    }

    public static synchronized License autoDeactivate(License license, String url2) {
        if (!LicenseValidator.isLicenseValidAndActivated(license)) {
            return license;
        }
        LicenseDataDTO licenseData = new LicenseDataDTO(license.getPublicKey(), ValidatorUtils.getHostName(), "", license, url2, true);
        String result = LicenseValidator.changeLicenseStatus(licenseData);
        if (result.length() < 10) {
            license.setActivationStatus(ActivationStatus.getStatus(result));
        }
        return license;
    }

    private static boolean isLicenseValidAndActivated(License license) {
        ValidationStatus validationStatus = license.getValidationStatus();
        ActivationStatus activationStatus = license.getActivationStatus();
        if (validationStatus != ValidationStatus.LICENSE_VALID) {
            log.debug("Given License Status is not VALID");
            return false;
        }
        if (activationStatus != ActivationStatus.ACTIVATION_COMPLETED) {
            log.debug("Given License Activation Status is NOT ACTIVATION COMPLETED.");
            return false;
        }
        return true;
    }

    private static License handleLongResult(License license, String result, String serverURL) throws LicenseSecurityException, SignatureException {
        License updatedLicense;
        if (result.contains("!") && result.substring(0, result.indexOf("!")).length() == 65) {
            String var22 = result.substring(result.indexOf("!") + 1);
            updatedLicense = LicenseValidator.validate(serverURL.substring(0, result.indexOf("!")), license.getPublicKey(), null, null, null, Integer.parseInt(var22));
            LicenseValidator.handleValidationStatus(updatedLicense);
        } else {
            if (!result.startsWith("#")) {
                license.setActivationStatus(ActivationStatus.ACTIVATION_SERVER_CONNECTION_ERROR);
                return license;
            }
            updatedLicense = LicenseValidator.validateLicenseText(result, license);
            if (updatedLicense == null) {
                return new License(ValidationStatus.LICENSE_INVALID);
            }
            updatedLicense.setActivationStatus(ActivationStatus.ACTIVATION_COMPLETED);
        }
        return updatedLicense;
    }

    private static void handleValidationStatus(License license) {
        ValidationStatus validationStatus = license.getValidationStatus();
        switch (validationStatus) {
            case LICENSE_VALID: 
            case LICENSE_MAINTENANCE_EXPIRED: 
            case LICENSE_EXPIRED: {
                license.setActivationStatus(ActivationStatus.ACTIVATION_COMPLETED);
                break;
            }
            default: {
                license.setActivationStatus(null);
            }
        }
    }

    private static License validateLicenseText(String result, License license) {
        LicenseText licenseText;
        try {
            licenseText = new LicenseText(result, license.getPublicKey());
        }
        catch (LicenseSecurityException e) {
            log.debug("EX1152", e);
            return null;
        }
        try {
            return LicenseTextValidator.validate(result, license.getPublicKey(), licenseText.getLicenseValidProductID(), licenseText.getLicenseValidProductEdition(), licenseText.getLicenseValidProductVersion());
        }
        catch (LicenseSecurityException e) {
            log.debug("EX1153", e);
            return null;
        }
    }

    private static String changeLicenseStatus(LicenseDataDTO dto) {
        String url2 = dto.getUrl() != null ? dto.getUrl() : license_base_url + (dto.isDeactivate() ? "/autodeactivate" : "/autoactivate");
        String result = HTTP.sendPOST(url2, JSONObject.toJSONString(dto));
        return result != null ? result : "";
    }
}

