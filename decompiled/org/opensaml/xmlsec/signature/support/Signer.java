/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.signature.support;

import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;
import javax.annotation.Nonnull;
import org.opensaml.xmlsec.signature.Signature;
import org.opensaml.xmlsec.signature.support.SignatureException;
import org.opensaml.xmlsec.signature.support.SignerProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Signer {
    private static SignerProvider signerInstance;

    protected Signer() {
    }

    public static void signObjects(@Nonnull List<Signature> signatures) throws SignatureException {
        SignerProvider signer = Signer.getSignerProvider();
        Signer.getLogger().debug("Using a signer of implementation: {}", (Object)signer.getClass().getName());
        for (Signature signature : signatures) {
            signer.signObject(signature);
        }
    }

    public static void signObject(@Nonnull Signature signature) throws SignatureException {
        SignerProvider signer = Signer.getSignerProvider();
        Signer.getLogger().debug("Using a signer of implemenation: {}", (Object)signer.getClass().getName());
        signer.signObject(signature);
    }

    @Nonnull
    private static SignerProvider getSignerProvider() throws SignatureException {
        if (signerInstance == null) {
            ServiceLoader<SignerProvider> loader = ServiceLoader.load(SignerProvider.class);
            Iterator<SignerProvider> iterator2 = loader.iterator();
            if (iterator2.hasNext()) {
                signerInstance = iterator2.next();
            } else {
                throw new SignatureException("Could not load a signer implementation via service API");
            }
        }
        return signerInstance;
    }

    @Nonnull
    private static Logger getLogger() {
        return LoggerFactory.getLogger(Signer.class);
    }
}

