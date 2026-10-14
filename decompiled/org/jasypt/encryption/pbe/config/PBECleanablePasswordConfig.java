/*
 * Decompiled with CFR 0.152.
 */
package org.jasypt.encryption.pbe.config;

public interface PBECleanablePasswordConfig {
    public char[] getPasswordCharArray();

    public void cleanPassword();
}

