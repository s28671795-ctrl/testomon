/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.keyinfo;

import javax.annotation.Nonnull;
import org.opensaml.security.credential.Credential;
import org.opensaml.xmlsec.keyinfo.KeyInfoGenerator;

public interface KeyInfoGeneratorFactory {
    @Nonnull
    public KeyInfoGenerator newInstance();

    public boolean handles(@Nonnull Credential var1);

    @Nonnull
    public Class<? extends Credential> getCredentialType();
}

