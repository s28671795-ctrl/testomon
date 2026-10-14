/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.keyinfo.impl;

import java.util.List;
import javax.annotation.Nonnull;
import org.opensaml.security.credential.impl.AbstractChainingCredentialResolver;
import org.opensaml.xmlsec.keyinfo.KeyInfoCredentialResolver;

public class ChainingKeyInfoCredentialResolver
extends AbstractChainingCredentialResolver<KeyInfoCredentialResolver>
implements KeyInfoCredentialResolver {
    public ChainingKeyInfoCredentialResolver(@Nonnull List<KeyInfoCredentialResolver> resolverChain) {
        super(resolverChain);
    }
}

