/*
 * Decompiled with CFR 0.152.
 */
package org.jasypt.digest;

public interface StringDigester {
    public String digest(String var1);

    public boolean matches(String var1, String var2);
}

