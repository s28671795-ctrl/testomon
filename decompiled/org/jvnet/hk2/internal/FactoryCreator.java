/*
 * Decompiled with CFR 0.152.
 */
package org.jvnet.hk2.internal;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import org.glassfish.hk2.api.ActiveDescriptor;
import org.glassfish.hk2.api.Factory;
import org.glassfish.hk2.api.Injectee;
import org.glassfish.hk2.api.InstanceLifecycleEventType;
import org.glassfish.hk2.api.InstantiationService;
import org.glassfish.hk2.api.MultiException;
import org.glassfish.hk2.api.ServiceHandle;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.general.ThreadSpecificObject;
import org.glassfish.hk2.utilities.reflection.Pretty;
import org.jvnet.hk2.internal.Creator;
import org.jvnet.hk2.internal.InstanceLifecycleEventImpl;
import org.jvnet.hk2.internal.InstantiationServiceImpl;
import org.jvnet.hk2.internal.ServiceHandleImpl;
import org.jvnet.hk2.internal.SystemDescriptor;

public class FactoryCreator<T>
implements Creator<T> {
    private static final Object MAP_VALUE = new Object();
    private final ConcurrentHashMap<ThreadSpecificObject<ActiveDescriptor<?>>, Object> cycleFinder = new ConcurrentHashMap();
    private final ServiceLocator locator;
    private final ActiveDescriptor<?> factoryDescriptor;
    private final InstantiationServiceImpl instantiationService;

    FactoryCreator(ServiceLocator locator, ActiveDescriptor<?> factoryDescriptor) {
        this.locator = locator;
        this.factoryDescriptor = factoryDescriptor;
        if (!factoryDescriptor.isReified()) {
            factoryDescriptor = locator.reifyDescriptor(factoryDescriptor);
        }
        InstantiationServiceImpl found = null;
        for (Injectee factoryInjectee : factoryDescriptor.getInjectees()) {
            if (!InstantiationService.class.equals((Object)factoryInjectee.getRequiredType())) continue;
            found = locator.getService(InstantiationServiceImpl.class, new Annotation[0]);
            break;
        }
        this.instantiationService = found;
    }

    @Override
    public List<Injectee> getInjectees() {
        return Collections.emptyList();
    }

    private ServiceHandle<Factory<T>> getFactoryHandle() {
        try {
            return this.locator.getServiceHandle(this.factoryDescriptor);
        }
        catch (Throwable th) {
            throw new MultiException(th);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public T create(ServiceHandle<?> root, SystemDescriptor<?> eventThrower) throws MultiException {
        T retVal;
        Factory<T> retValFactory;
        ServiceHandle<Factory<T>> handle2 = this.getFactoryHandle();
        eventThrower.invokeInstanceListeners(new InstanceLifecycleEventImpl(InstanceLifecycleEventType.PRE_PRODUCTION, null, eventThrower));
        ThreadSpecificObject<ActiveDescriptor<Factory<T>>> tso = new ThreadSpecificObject<ActiveDescriptor<Factory<T>>>(handle2.getActiveDescriptor());
        if (this.cycleFinder.containsKey(tso)) {
            HashSet<String> impls = new HashSet<String>();
            for (ThreadSpecificObject candidate : this.cycleFinder.keySet()) {
                if (candidate.getThreadIdentifier() != tso.getThreadIdentifier()) continue;
                impls.add(((ActiveDescriptor)candidate.getIncomingObject()).getImplementation());
            }
            throw new AssertionError((Object)("A cycle was detected involving these Factory implementations: " + Pretty.collection(impls)));
        }
        this.cycleFinder.put(tso, MAP_VALUE);
        try {
            retValFactory = handle2.getService();
        }
        finally {
            this.cycleFinder.remove(tso);
        }
        if (this.instantiationService != null) {
            Injectee parentInjectee = null;
            if (root != null && root instanceof ServiceHandleImpl) {
                parentInjectee = ((ServiceHandleImpl)root).getOriginalRequest();
            }
            this.instantiationService.pushInjecteeParent(parentInjectee);
        }
        try {
            retVal = retValFactory.provide();
        }
        finally {
            if (this.instantiationService != null) {
                this.instantiationService.popInjecteeParent();
            }
        }
        eventThrower.invokeInstanceListeners(new InstanceLifecycleEventImpl(InstanceLifecycleEventType.POST_PRODUCTION, retVal, eventThrower));
        return retVal;
    }

    @Override
    public void dispose(T instance) {
        try {
            ServiceHandle<Factory<T>> handle2 = this.getFactoryHandle();
            Factory<T> factory2 = handle2.getService();
            factory2.dispose(instance);
        }
        catch (Throwable th) {
            if (th instanceof MultiException) {
                throw (MultiException)th;
            }
            throw new MultiException(th);
        }
    }

    public String toString() {
        return "FactoryCreator(" + this.locator + "," + this.factoryDescriptor + "," + System.identityHashCode(this) + ")";
    }
}

