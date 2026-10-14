/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.keyinfo.impl;

import java.security.Key;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.collection.LazyMap;
import net.shibboleth.utilities.java.support.collection.LazySet;
import org.opensaml.security.credential.Credential;
import org.opensaml.xmlsec.signature.KeyInfo;

public class KeyInfoResolutionContext {
    private KeyInfo keyInfo;
    private final Set<String> keyNames;
    private Key key;
    private final Collection<Credential> resolvedCredentials;
    private final Map<String, Object> properties;

    public KeyInfoResolutionContext(@Nonnull Collection<Credential> credentials) {
        this.resolvedCredentials = Collections.unmodifiableCollection(credentials);
        this.properties = new LazyMap<String, Object>();
        this.keyNames = new LazySet<String>();
    }

    @Nullable
    public KeyInfo getKeyInfo() {
        return this.keyInfo;
    }

    public void setKeyInfo(@Nullable KeyInfo newKeyInfo) {
        this.keyInfo = newKeyInfo;
    }

    @Nonnull
    public Set<String> getKeyNames() {
        return this.keyNames;
    }

    @Nullable
    public Key getKey() {
        return this.key;
    }

    public void setKey(@Nullable Key newKey) {
        this.key = newKey;
    }

    @Nonnull
    public Collection<Credential> getResolvedCredentials() {
        return this.resolvedCredentials;
    }

    @Nonnull
    public Map<String, Object> getProperties() {
        return this.properties;
    }
}

