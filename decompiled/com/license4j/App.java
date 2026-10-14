/*
 * Decompiled with CFR 0.152.
 */
package com.license4j;

import com.license4j.LicenseKeyPair;
import com.license4j.LicenseManager;
import com.license4j.exceptions.LicenseSecurityException;
import com.license4j.hardware.HardwareID;

public class App {
    public static void main(String[] args2) {
        HardwareID.getHardwareIDFromHDDSerial();
        try {
            LicenseKeyPair kp = LicenseManager.createKeyPair("Test", "Description", "RSA", "SHA512withRSA", 1024);
            System.out.println(kp.getPrivateKey());
        }
        catch (LicenseSecurityException e) {
            throw new RuntimeException(e);
        }
    }
}

