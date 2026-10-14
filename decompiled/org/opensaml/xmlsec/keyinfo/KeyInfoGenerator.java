/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.keyinfo;

import javax.annotation.Nullable;
import org.opensaml.security.SecurityException;
import org.opensaml.security.credential.Credential;
import org.opensaml.xmlsec.signature.KeyInfo;

public interface KeyInfoGenerator {
    @Nullable
    public KeyInfo generate(@Nullable Credential var1) throws SecurityException;
}

