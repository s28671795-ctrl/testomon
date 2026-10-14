/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.signature.support;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.ThreadSafe;
import org.opensaml.xmlsec.signature.Signature;
import org.opensaml.xmlsec.signature.support.SignatureException;

@ThreadSafe
public interface SignerProvider {
    public void signObject(@Nonnull Signature var1) throws SignatureException;
}

