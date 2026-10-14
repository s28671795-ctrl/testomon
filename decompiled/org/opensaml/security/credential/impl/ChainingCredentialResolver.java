/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.security.credential.impl;

import java.util.List;
import javax.annotation.Nonnull;
import org.opensaml.security.credential.CredentialResolver;
import org.opensaml.security.credential.impl.AbstractChainingCredentialResolver;

public class ChainingCredentialResolver
extends AbstractChainingCredentialResolver<CredentialResolver> {
    public ChainingCredentialResolver(@Nonnull List<CredentialResolver> resolverChain) {
        super(resolverChain);
    }
}

