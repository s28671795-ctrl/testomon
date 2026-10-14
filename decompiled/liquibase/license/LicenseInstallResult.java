/*
 * Decompiled with CFR 0.152.
 */
package liquibase.license;

import java.util.ArrayList;
import java.util.List;

public class LicenseInstallResult {
    public int code;
    public List<String> messages;

    public LicenseInstallResult(int code) {
        this.code = code;
        this.messages = new ArrayList<String>();
    }

    public LicenseInstallResult(int code, String message) {
        this.code = code;
        this.messages = new ArrayList<String>();
        this.messages.add(message);
    }

    public LicenseInstallResult(int code, List<String> results) {
        this.code = code;
        this.messages = new ArrayList<String>();
        this.messages.addAll(results);
    }

    public void add(LicenseInstallResult result) {
        if (result.code != 0) {
            this.code = result.code;
        }
        this.messages.addAll(result.messages);
    }
}

