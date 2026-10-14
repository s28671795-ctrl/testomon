/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.credentials.password;

public interface PasswordEncoder {
    public String encode(String var1);

    public boolean matches(String var1, String var2);
}

