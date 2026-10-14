/*
 * Decompiled with CFR 0.152.
 */
package liquibase.license;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LicenseInfo {
    private String issuedTo;
    private Date expirationDate;

    public LicenseInfo(String issuedTo, Date expirationDate) {
        this.issuedTo = issuedTo;
        this.expirationDate = expirationDate;
    }

    public String getIssuedTo() {
        return this.issuedTo;
    }

    public void setIssuedTo(String issuedTo) {
        this.issuedTo = issuedTo;
    }

    public Date getExpirationDate() {
        return this.expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String formatExpirationDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
        return dateFormat.format(this.expirationDate);
    }
}

