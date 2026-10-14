/*
 * Decompiled with CFR 0.152.
 */
package org.jvnet.hk2.internal;

import javax.inject.Inject;
import javax.inject.Named;
import org.glassfish.hk2.api.ActiveDescriptor;
import org.glassfish.hk2.api.Injectee;
import org.glassfish.hk2.api.InjectionResolver;
import org.glassfish.hk2.api.MultiException;
import org.glassfish.hk2.api.ServiceHandle;
import org.glassfish.hk2.api.UnsatisfiedDependencyException;
import org.jvnet.hk2.internal.ServiceLocatorImpl;

@Named(value="SystemInjectResolver")
public class ThreeThirtyResolver
implements InjectionResolver<Inject> {
    private final ServiceLocatorImpl locator;

    ThreeThirtyResolver(ServiceLocatorImpl locator) {
        this.locator = locator;
    }

    @Override
    public Object resolve(Injectee injectee, ServiceHandle<?> root) {
        ActiveDescriptor<?> ad = this.locator.getInjecteeDescriptor(injectee);
        if (ad == null) {
            if (injectee.isOptional()) {
                return null;
            }
            throw new MultiException(new UnsatisfiedDependencyException(injectee));
        }
        return this.locator.getService(ad, root, injectee);
    }

    @Override
    public boolean isConstructorParameterIndicator() {
        return false;
    }

    @Override
    public boolean isMethodParameterIndicator() {
        return false;
    }

    public String toString() {
        return "ThreeThirtyResolver(" + this.locator + "," + System.identityHashCode(this) + ")";
    }
}

