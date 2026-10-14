/*
 * Decompiled with CFR 0.152.
 */
package liquibase.license;

import java.util.Date;
import liquibase.license.LicenseInfo;
import liquibase.license.LicenseInstallResult;
import liquibase.license.Location;
import liquibase.plugin.Plugin;

public interface LicenseService
extends Plugin {
    public int getPriority();

    public boolean licenseIsInstalled();

    public boolean licenseIsValid(String var1);

    public String getLicenseInfo();

    public LicenseInfo getLicenseInfoObject();

    public LicenseInstallResult installLicense(Location ... var1);

    public void disable();

    default public void reset() {
    }

    public boolean licenseIsAboutToExpire();

    public int daysTilExpiration();

    default public Date getExpirationDate() {
        return null;
    }
}

