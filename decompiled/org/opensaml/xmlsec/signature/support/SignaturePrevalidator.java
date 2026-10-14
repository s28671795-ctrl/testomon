/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.signature.support;

import javax.annotation.Nonnull;
import org.opensaml.xmlsec.signature.Signature;
import org.opensaml.xmlsec.signature.support.SignatureException;

public interface SignaturePrevalidator {
    public void validate(@Nonnull Signature var1) throws SignatureException;
}

