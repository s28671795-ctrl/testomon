/*
 * Decompiled with CFR 0.152.
 */
package org.jasypt.util.password;

import org.jasypt.digest.StandardStringDigester;
import org.jasypt.util.password.PasswordEncryptor;

public final class StrongPasswordEncryptor
implements PasswordEncryptor {
    private final StandardStringDigester digester = new StandardStringDigester();

    public StrongPasswordEncryptor() {
        this.digester.setAlgorithm("SHA-256");
        this.digester.setIterations(100000);
        this.digester.setSaltSizeBytes(16);
        this.digester.initialize();
    }

    @Override
    public String encryptPassword(String password) {
        return this.digester.digest(password);
    }

    @Override
    public boolean checkPassword(String plainPassword, String encryptedPassword) {
        return this.digester.matches(plainPassword, encryptedPassword);
    }
}

