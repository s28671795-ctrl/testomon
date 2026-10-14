/*
 * Decompiled with CFR 0.152.
 */
package org.jvnet.hk2.internal;

import java.lang.annotation.Annotation;
import org.glassfish.hk2.api.ActiveDescriptor;
import org.glassfish.hk2.api.Context;
import org.glassfish.hk2.api.PerLookup;
import org.glassfish.hk2.api.ServiceHandle;

public class PerLookupContext
implements Context<PerLookup> {
    @Override
    public Class<? extends Annotation> getScope() {
        return PerLookup.class;
    }

    @Override
    public <T> T findOrCreate(ActiveDescriptor<T> activeDescriptor, ServiceHandle<?> root) {
        return activeDescriptor.create(root);
    }

    @Override
    public boolean containsKey(ActiveDescriptor<?> descriptor) {
        return false;
    }

    @Override
    public boolean isActive() {
        return true;
    }

    @Override
    public boolean supportsNullCreation() {
        return true;
    }

    @Override
    public void shutdown() {
    }

    @Override
    public void destroyOne(ActiveDescriptor<?> descriptor) {
    }
}

