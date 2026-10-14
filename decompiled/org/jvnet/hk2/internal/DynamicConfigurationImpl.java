/*
 * Decompiled with CFR 0.152.
 */
package org.jvnet.hk2.internal;

import java.util.LinkedList;
import org.glassfish.hk2.api.ActiveDescriptor;
import org.glassfish.hk2.api.Descriptor;
import org.glassfish.hk2.api.DescriptorType;
import org.glassfish.hk2.api.DynamicConfiguration;
import org.glassfish.hk2.api.Factory;
import org.glassfish.hk2.api.FactoryDescriptors;
import org.glassfish.hk2.api.Filter;
import org.glassfish.hk2.api.MultiException;
import org.glassfish.hk2.api.TwoPhaseResource;
import org.glassfish.hk2.utilities.FactoryDescriptorsImpl;
import org.glassfish.hk2.utilities.reflection.Pretty;
import org.jvnet.hk2.internal.AutoActiveDescriptor;
import org.jvnet.hk2.internal.Collector;
import org.jvnet.hk2.internal.ServiceLocatorImpl;
import org.jvnet.hk2.internal.SystemDescriptor;
import org.jvnet.hk2.internal.Utilities;

public class DynamicConfigurationImpl
implements DynamicConfiguration {
    private final ServiceLocatorImpl locator;
    private final LinkedList<SystemDescriptor<?>> allDescriptors = new LinkedList();
    private final LinkedList<Filter> allUnbindFilters = new LinkedList();
    private final LinkedList<Filter> allIdempotentFilters = new LinkedList();
    private final LinkedList<TwoPhaseResource> allResources = new LinkedList();
    private final Object lock = new Object();
    private boolean committed = false;

    public DynamicConfigurationImpl(ServiceLocatorImpl locator) {
        this.locator = locator;
    }

    @Override
    public <T> ActiveDescriptor<T> bind(Descriptor key) {
        return this.bind(key, true);
    }

    @Override
    public <T> ActiveDescriptor<T> bind(Descriptor key, boolean requiresDeepCopy) {
        this.checkState();
        DynamicConfigurationImpl.checkDescriptor(key);
        SystemDescriptor sd = new SystemDescriptor(key, requiresDeepCopy, this.locator, this.locator.getNextServiceId());
        this.allDescriptors.add(sd);
        return sd;
    }

    @Override
    public FactoryDescriptors bind(FactoryDescriptors factoryDescriptors) {
        return this.bind(factoryDescriptors, true);
    }

    @Override
    public FactoryDescriptors bind(FactoryDescriptors factoryDescriptors, boolean requiresDeepCopy) {
        if (factoryDescriptors == null) {
            throw new IllegalArgumentException("factoryDescriptors is null");
        }
        Descriptor asService = factoryDescriptors.getFactoryAsAService();
        Descriptor asFactory = factoryDescriptors.getFactoryAsAFactory();
        DynamicConfigurationImpl.checkDescriptor(asService);
        DynamicConfigurationImpl.checkDescriptor(asFactory);
        String implClassService = asService.getImplementation();
        String implClassFactory = asFactory.getImplementation();
        if (!implClassService.equals(implClassFactory)) {
            throw new IllegalArgumentException("The implementation classes must match (" + implClassService + "/" + implClassFactory + ")");
        }
        if (!asService.getDescriptorType().equals((Object)DescriptorType.CLASS)) {
            throw new IllegalArgumentException("The getFactoryAsService descriptor must be of type CLASS");
        }
        if (!asFactory.getDescriptorType().equals((Object)DescriptorType.PROVIDE_METHOD)) {
            throw new IllegalArgumentException("The getFactoryAsFactory descriptor must be of type PROVIDE_METHOD");
        }
        SystemDescriptor boundAsService = new SystemDescriptor(asService, requiresDeepCopy, this.locator, this.locator.getNextServiceId());
        SystemDescriptor boundAsFactory = new SystemDescriptor(asFactory, requiresDeepCopy, this.locator, this.locator.getNextServiceId());
        if (asService instanceof ActiveDescriptor) {
            boundAsFactory.setFactoryIds(boundAsService.getLocatorId(), boundAsService.getServiceId());
        }
        this.allDescriptors.add(boundAsFactory);
        this.allDescriptors.add(boundAsService);
        return new FactoryDescriptorsImpl(boundAsService, boundAsFactory);
    }

    @Override
    public <T> ActiveDescriptor<T> addActiveDescriptor(ActiveDescriptor<T> activeDescriptor) throws IllegalArgumentException {
        return this.addActiveDescriptor(activeDescriptor, true);
    }

    @Override
    public <T> ActiveDescriptor<T> addActiveDescriptor(ActiveDescriptor<T> activeDescriptor, boolean requiresDeepCopy) throws IllegalArgumentException {
        this.checkState();
        DynamicConfigurationImpl.checkDescriptor(activeDescriptor);
        if (!activeDescriptor.isReified()) {
            throw new IllegalArgumentException();
        }
        DynamicConfigurationImpl.checkReifiedDescriptor(activeDescriptor);
        SystemDescriptor retVal = new SystemDescriptor(activeDescriptor, requiresDeepCopy, this.locator, this.locator.getNextServiceId());
        this.allDescriptors.add(retVal);
        return retVal;
    }

    @Override
    public <T> ActiveDescriptor<T> addActiveDescriptor(Class<T> rawClass) throws IllegalArgumentException {
        AutoActiveDescriptor<T> ad = Utilities.createAutoDescriptor(rawClass, this.locator);
        DynamicConfigurationImpl.checkReifiedDescriptor(ad);
        ActiveDescriptor<T> retVal = this.addActiveDescriptor(ad, false);
        ad.resetSelfDescriptor(retVal);
        return retVal;
    }

    @Override
    public <T> FactoryDescriptors addActiveFactoryDescriptor(Class<? extends Factory<T>> rawFactoryClass) throws MultiException, IllegalArgumentException {
        Collector collector = new Collector();
        Utilities.checkFactoryType(rawFactoryClass, collector);
        collector.throwIfErrors();
        ActiveDescriptor<? extends Factory<T>> factoryDescriptor = this.addActiveDescriptor(rawFactoryClass);
        AutoActiveDescriptor<? extends Factory<T>> userMethodDescriptor = Utilities.createAutoFactoryDescriptor(rawFactoryClass, factoryDescriptor, this.locator);
        ActiveDescriptor<? extends Factory<T>> methodDescriptor = this.addActiveDescriptor(userMethodDescriptor);
        return new FactoryDescriptorsImpl(factoryDescriptor, methodDescriptor);
    }

    @Override
    public void addUnbindFilter(Filter unbindFilter) throws IllegalArgumentException {
        if (unbindFilter == null) {
            throw new IllegalArgumentException();
        }
        this.checkState();
        this.allUnbindFilters.add(unbindFilter);
    }

    @Override
    public void addIdempotentFilter(Filter ... idempotentFilter) throws IllegalArgumentException {
        if (idempotentFilter == null) {
            throw new IllegalArgumentException();
        }
        this.checkState();
        for (Filter iFilter : idempotentFilter) {
            if (iFilter != null) continue;
            throw new IllegalArgumentException();
        }
        for (Filter iFilter : idempotentFilter) {
            this.allIdempotentFilters.add(iFilter);
        }
    }

    @Override
    public void registerTwoPhaseResources(TwoPhaseResource ... resources) {
        this.checkState();
        if (resources == null) {
            return;
        }
        for (int lcv = 0; lcv < resources.length; ++lcv) {
            TwoPhaseResource resource = resources[lcv];
            if (resource == null) continue;
            this.allResources.add(resource);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void commit() throws MultiException {
        Object object = this.lock;
        synchronized (object) {
            this.checkState();
            this.committed = true;
        }
        this.locator.addConfiguration(this);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void checkState() {
        Object object = this.lock;
        synchronized (object) {
            if (this.committed) {
                throw new IllegalStateException();
            }
        }
    }

    private static void checkDescriptor(Descriptor d) {
        if (d == null) {
            throw new IllegalArgumentException();
        }
        if (d.getImplementation() == null) {
            throw new IllegalArgumentException();
        }
        if (d.getAdvertisedContracts() == null) {
            throw new IllegalArgumentException();
        }
        if (d.getDescriptorType() == null) {
            throw new IllegalArgumentException();
        }
        if (d.getDescriptorVisibility() == null) {
            throw new IllegalArgumentException();
        }
        if (d.getMetadata() == null) {
            throw new IllegalArgumentException();
        }
        if (d.getQualifiers() == null) {
            throw new IllegalArgumentException();
        }
    }

    private static void checkReifiedDescriptor(ActiveDescriptor<?> d) {
        if (d.isProxiable() == null) {
            return;
        }
        if (!d.isProxiable().booleanValue()) {
            return;
        }
        if (Utilities.isUnproxiableScope(d.getScopeAnnotation())) {
            throw new IllegalArgumentException();
        }
    }

    LinkedList<SystemDescriptor<?>> getAllDescriptors() {
        return this.allDescriptors;
    }

    LinkedList<Filter> getUnbindFilters() {
        return this.allUnbindFilters;
    }

    LinkedList<Filter> getIdempotentFilters() {
        return this.allIdempotentFilters;
    }

    LinkedList<TwoPhaseResource> getResources() {
        return this.allResources;
    }

    public String toString() {
        return "DynamicConfigurationImpl(" + this.locator + "," + Pretty.collection(this.allDescriptors) + "," + Pretty.collection(this.allUnbindFilters) + "," + Pretty.collection(this.allResources) + "," + System.identityHashCode(this) + ")";
    }
}

