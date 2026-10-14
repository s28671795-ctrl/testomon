/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.keyinfo.impl;

import java.util.ArrayList;
import java.util.Collection;
import javax.annotation.Nonnull;
import org.opensaml.security.credential.Credential;
import org.opensaml.security.credential.impl.CollectionCredentialResolver;
import org.opensaml.xmlsec.keyinfo.KeyInfoCredentialResolver;

public class CollectionKeyInfoCredentialResolver
extends CollectionCredentialResolver
implements KeyInfoCredentialResolver {
    public CollectionKeyInfoCredentialResolver() {
        this(new ArrayList<Credential>());
    }

    public CollectionKeyInfoCredentialResolver(@Nonnull Collection<Credential> credentials) {
        super(credentials);
    }
}

