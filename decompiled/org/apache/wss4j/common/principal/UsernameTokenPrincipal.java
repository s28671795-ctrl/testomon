/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.common.principal;

import java.security.Principal;

public interface UsernameTokenPrincipal
extends Principal {
    public boolean isPasswordDigest();

    public String getPassword();

    public byte[] getNonce();

    public String getCreatedTime();

    public String getPasswordType();
}

