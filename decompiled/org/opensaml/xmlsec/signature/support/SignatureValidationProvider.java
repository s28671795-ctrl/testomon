/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.signature.support;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.ThreadSafe;
import org.opensaml.security.credential.Credential;
import org.opensaml.xmlsec.signature.Signature;
import org.opensaml.xmlsec.signature.support.SignatureException;

@ThreadSafe
public interface SignatureValidationProvider {
    public void validate(@Nonnull Signature var1, @Nonnull Credential var2) throws SignatureException;
}

