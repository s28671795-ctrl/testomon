/*
 * Decompiled with CFR 0.152.
 */
package liquibase.license;

import liquibase.Scope;
import liquibase.exception.CommandValidationException;
import liquibase.license.LicenseService;
import liquibase.license.LicenseServiceFactory;
import liquibase.util.StringUtil;

public class LicenseServiceUtils {
    public static final String TRIAL_LICENSE_URL = "https://liquibase.com/trial";
    private static final String BASE_INVALID_LICENSE_MESSAGE = "Using '%s' requires a valid Liquibase Pro or Labs license. Get a free license key at https://liquibase.com/trial.";

    public static boolean isProLicenseValid() {
        LicenseServiceFactory licenseServiceFactory = Scope.getCurrentScope().getSingleton(LicenseServiceFactory.class);
        if (licenseServiceFactory == null) {
            return false;
        }
        LicenseService licenseService = licenseServiceFactory.getLicenseService();
        if (licenseService == null) {
            return false;
        }
        return licenseService.licenseIsValid("Liquibase Pro");
    }

    public static void checkProLicenseAndThrowException(String[] commandNames) throws CommandValidationException {
        if (!LicenseServiceUtils.isProLicenseValid()) {
            throw new CommandValidationException(String.format("Using '%s' requires a valid Liquibase Pro or Labs license. Get a free license key at https://liquibase.com/trial. Add liquibase.licenseKey=<yourKey> into your defaults file or use --license-key=<yourKey> before your command in the CLI.", StringUtil.join(commandNames, " ")));
        }
    }
}

