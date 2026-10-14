/*
 * Decompiled with CFR 0.152.
 */
package org.jvnet.hk2.internal;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import javax.inject.Singleton;
import org.glassfish.hk2.api.ActiveDescriptor;
import org.glassfish.hk2.api.Context;
import org.glassfish.hk2.api.MultiException;
import org.glassfish.hk2.api.ServiceHandle;
import org.glassfish.hk2.utilities.BuilderHelper;
import org.glassfish.hk2.utilities.ContextualInput;
import org.glassfish.hk2.utilities.cache.Cache;
import org.glassfish.hk2.utilities.cache.Computable;
import org.glassfish.hk2.utilities.reflection.Logger;
import org.jvnet.hk2.internal.ServiceLocatorImpl;
import org.jvnet.hk2.internal.SystemDescriptor;

@Singleton
public class SingletonContext
implements Context<Singleton> {
    private int generationNumber = Integer.MIN_VALUE;
    private final ServiceLocatorImpl locator;
    private final Cache<ContextualInput<Object>, Object> valueCache = new Cache<ContextualInput<Object>, Object>(new Computable<ContextualInput<Object>, Object>(){

        @Override
        public Object compute(ContextualInput<Object> a) {
            ActiveDescriptor<Object> activeDescriptor = a.getDescriptor();
            Object cachedVal = activeDescriptor.getCache();
            if (cachedVal != null) {
                return cachedVal;
            }
            Object createdVal = activeDescriptor.create(a.getRoot());
            activeDescriptor.setCache(createdVal);
            if (activeDescriptor instanceof SystemDescriptor) {
                ((SystemDescriptor)activeDescriptor).setSingletonGeneration(SingletonContext.this.generationNumber++);
            }
            return createdVal;
        }
    }, new Cache.CycleHandler<ContextualInput<Object>>(){

        @Override
        public void handleCycle(ContextualInput<Object> key) {
            throw new MultiException(new IllegalStateException("A circular dependency involving Singleton service " + key.getDescriptor().getImplementation() + " was found.  Full descriptor is " + key.getDescriptor()));
        }
    });

    SingletonContext(ServiceLocatorImpl impl) {
        this.locator = impl;
    }

    @Override
    public Class<? extends Annotation> getScope() {
        return Singleton.class;
    }

    @Override
    public <T> T findOrCreate(ActiveDescriptor<T> activeDescriptor, ServiceHandle<?> root) {
        try {
            return (T)this.valueCache.compute(new ContextualInput<T>(activeDescriptor, root));
        }
        catch (Throwable th) {
            if (th instanceof MultiException) {
                throw (MultiException)th;
            }
            throw new MultiException(th);
        }
    }

    @Override
    public boolean containsKey(ActiveDescriptor<?> descriptor) {
        return this.valueCache.containsKey(new ContextualInput(descriptor, null));
    }

    @Override
    public boolean isActive() {
        return true;
    }

    @Override
    public boolean supportsNullCreation() {
        return false;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void shutdown() {
        List<ActiveDescriptor<?>> all = this.locator.getDescriptors(BuilderHelper.allFilter());
        long myLocatorId = this.locator.getLocatorId();
        TreeSet<SystemDescriptor<Object>> singlesOnly = new TreeSet<SystemDescriptor<Object>>(new GenerationComparator());
        for (ActiveDescriptor<?> activeDescriptor : all) {
            if (activeDescriptor.getScope() == null || !activeDescriptor.getScope().equals(Singleton.class.getName())) continue;
            SingletonContext singletonContext = this;
            synchronized (singletonContext) {
                if (activeDescriptor.getCache() == null) {
                    continue;
                }
            }
            if (activeDescriptor.getLocatorId() == null || activeDescriptor.getLocatorId() != myLocatorId) continue;
            SystemDescriptor oneAsObject = (SystemDescriptor)activeDescriptor;
            singlesOnly.add(oneAsObject);
        }
        for (SystemDescriptor systemDescriptor : singlesOnly) {
            this.destroyOne(systemDescriptor);
        }
    }

    @Override
    public void destroyOne(ActiveDescriptor<?> one) {
        this.valueCache.remove(new ContextualInput(one, null));
        Object value = one.getCache();
        one.releaseCache();
        if (value == null) {
            return;
        }
        try {
            one.dispose(value);
        }
        catch (Throwable th) {
            Logger.getLogger().debug("SingletonContext", "releaseOne", th);
        }
    }

    private static class GenerationComparator
    implements Comparator<SystemDescriptor<Object>>,
    Serializable {
        private static final long serialVersionUID = -6931828935035131179L;

        private GenerationComparator() {
        }

        @Override
        public int compare(SystemDescriptor<Object> o1, SystemDescriptor<Object> o2) {
            if (o1.getSingletonGeneration() > o2.getSingletonGeneration()) {
                return -1;
            }
            if (o1.getSingletonGeneration() == o2.getSingletonGeneration()) {
                return 0;
            }
            return 1;
        }
    }
}

