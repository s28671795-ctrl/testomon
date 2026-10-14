/*
 * Decompiled with CFR 0.152.
 */
package org.jasypt.util.password;

public interface PasswordEncryptor {
    public String encryptPassword(String var1);

    public boolean checkPassword(String var1, String var2);
}

