/*
 * Decompiled with CFR 0.152.
 */
package org.jvnet.hk2.internal;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.glassfish.hk2.api.DescriptorType;
import org.glassfish.hk2.api.DescriptorVisibility;
import org.glassfish.hk2.api.PerLookup;
import org.glassfish.hk2.api.ServiceHandle;
import org.glassfish.hk2.utilities.AbstractActiveDescriptor;
import org.jvnet.hk2.internal.ServiceLocatorImpl;

public class ConstantActiveDescriptor<T>
extends AbstractActiveDescriptor<T> {
    private static final long serialVersionUID = 3663054975929743877L;
    private T theOne;
    private Long locatorId;

    public ConstantActiveDescriptor() {
    }

    public ConstantActiveDescriptor(T theOne, ServiceLocatorImpl locator) {
        super(new HashSet<Type>(), PerLookup.class, null, new HashSet<Annotation>(), DescriptorType.CLASS, DescriptorVisibility.NORMAL, 0, null, null, locator.getPerLocatorUtilities().getAutoAnalyzerName(theOne.getClass()), null);
        this.theOne = theOne;
        this.locatorId = locator.getLocatorId();
    }

    public ConstantActiveDescriptor(T theOne, Set<Type> advertisedContracts, Class<? extends Annotation> scope, String name, Set<Annotation> qualifiers, DescriptorVisibility visibility, int ranking, Boolean proxy, Boolean proxyForSameScope, String analyzerName, long locatorId, Map<String, List<String>> metadata) {
        super(advertisedContracts, scope, name, qualifiers, DescriptorType.CLASS, visibility, ranking, proxy, proxyForSameScope, analyzerName, metadata);
        if (theOne == null) {
            throw new IllegalArgumentException();
        }
        this.theOne = theOne;
        this.locatorId = locatorId;
    }

    @Override
    public String getImplementation() {
        return this.theOne.getClass().getName();
    }

    @Override
    public Long getLocatorId() {
        return this.locatorId;
    }

    @Override
    public T getCache() {
        return this.theOne;
    }

    @Override
    public boolean isCacheSet() {
        return true;
    }

    @Override
    public Class<?> getImplementationClass() {
        return this.theOne.getClass();
    }

    @Override
    public Type getImplementationType() {
        return this.theOne.getClass();
    }

    @Override
    public void setImplementationType(Type t2) {
        throw new AssertionError((Object)"Can not set type of a constant descriptor");
    }

    @Override
    public T create(ServiceHandle<?> root) {
        return this.theOne;
    }

    @Override
    public void dispose(T instance) {
    }
}

