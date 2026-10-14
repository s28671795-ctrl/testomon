/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.keyinfo;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.opensaml.security.credential.Credential;
import org.opensaml.xmlsec.keyinfo.KeyInfoGeneratorFactory;

public class KeyInfoGeneratorManager {
    private final Map<Class<? extends Credential>, KeyInfoGeneratorFactory> factories = new HashMap<Class<? extends Credential>, KeyInfoGeneratorFactory>(5);

    public void registerFactory(@Nonnull KeyInfoGeneratorFactory factory2) {
        this.factories.put(factory2.getCredentialType(), factory2);
    }

    public void deregisterFactory(@Nonnull KeyInfoGeneratorFactory factory2) {
        this.factories.remove(factory2.getCredentialType());
    }

    @Nonnull
    public Collection<KeyInfoGeneratorFactory> getFactories() {
        return Collections.unmodifiableCollection(this.factories.values());
    }

    @Nullable
    public KeyInfoGeneratorFactory getFactory(@Nonnull Credential credential) {
        Constraint.isNotNull(credential, "Credential cannot be null");
        return this.factories.get(credential.getCredentialType());
    }
}

