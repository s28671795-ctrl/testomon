/*
 * Decompiled with CFR 0.152.
 */
package org.jvnet.hk2.internal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.glassfish.hk2.api.ActiveDescriptor;
import org.glassfish.hk2.api.Context;
import org.glassfish.hk2.api.Injectee;
import org.glassfish.hk2.api.PerLookup;
import org.glassfish.hk2.api.ServiceHandle;
import org.glassfish.hk2.utilities.reflection.ReflectionHelper;
import org.jvnet.hk2.internal.Closeable;
import org.jvnet.hk2.internal.ServiceLocatorImpl;
import org.jvnet.hk2.internal.Utilities;

public class ServiceHandleImpl<T>
implements ServiceHandle<T> {
    private ActiveDescriptor<T> root;
    private final ServiceLocatorImpl locator;
    private final LinkedList<Injectee> injectees = new LinkedList();
    private final Object lock = new Object();
    private boolean serviceDestroyed = false;
    private boolean serviceSet = false;
    private T service;
    private Object serviceData;
    private final LinkedList<ServiceHandleImpl<?>> subHandles = new LinkedList();

    ServiceHandleImpl(ServiceLocatorImpl locator, ActiveDescriptor<T> root, Injectee injectee) {
        this.root = root;
        this.locator = locator;
        if (injectee != null) {
            this.injectees.add(injectee);
        }
    }

    @Override
    public T getService() {
        return this.getService(this);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Injectee getLastInjectee() {
        Object object = this.lock;
        synchronized (object) {
            return this.injectees.isEmpty() ? null : this.injectees.getLast();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    T getService(ServiceHandle<T> handle2) {
        Closeable closeable;
        if (this.root instanceof Closeable && (closeable = (Closeable)((Object)this.root)).isClosed()) {
            throw new IllegalStateException("This service has been unbound: " + this.root);
        }
        Object object = this.lock;
        synchronized (object) {
            if (this.serviceDestroyed) {
                throw new IllegalStateException("Service has been disposed");
            }
            if (this.serviceSet) {
                return this.service;
            }
            Injectee injectee = this.getLastInjectee();
            Class<?> requiredClass = injectee == null ? null : ReflectionHelper.getRawClass(injectee.getRequiredType());
            this.service = Utilities.createService(this.root, injectee, this.locator, handle2, requiredClass);
            this.serviceSet = true;
            return this.service;
        }
    }

    @Override
    public ActiveDescriptor<T> getActiveDescriptor() {
        return this.root;
    }

    @Override
    public boolean isActive() {
        if (this.serviceDestroyed) {
            return false;
        }
        if (this.serviceSet) {
            return true;
        }
        try {
            Context<T> context = this.locator.resolveContext(this.root.getScopeAnnotation());
            return context.containsKey(this.root);
        }
        catch (IllegalStateException ise) {
            return false;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void close() {
        ArrayList localSubHandles;
        boolean localServiceSet;
        boolean serviceActive;
        if (!this.root.isReified()) {
            return;
        }
        Iterator iterator2 = this.lock;
        synchronized (iterator2) {
            serviceActive = this.isActive();
            if (this.serviceDestroyed) {
                return;
            }
            this.serviceDestroyed = true;
            localServiceSet = this.serviceSet;
            localSubHandles = new ArrayList(this.subHandles);
            this.subHandles.clear();
        }
        if (this.root.getScopeAnnotation().equals(PerLookup.class)) {
            if (localServiceSet) {
                this.root.dispose(this.service);
            }
        } else if (serviceActive) {
            Context<T> context;
            try {
                context = this.locator.resolveContext(this.root.getScopeAnnotation());
            }
            catch (Throwable throwable) {
                return;
            }
            context.destroyOne(this.root);
        }
        for (ServiceHandleImpl serviceHandleImpl : localSubHandles) {
            serviceHandleImpl.destroy();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void setServiceData(Object serviceData) {
        Object object = this.lock;
        synchronized (object) {
            this.serviceData = serviceData;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Object getServiceData() {
        Object object = this.lock;
        synchronized (object) {
            return this.serviceData;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<ServiceHandle<?>> getSubHandles() {
        Object object = this.lock;
        synchronized (object) {
            return new ArrayList(this.subHandles);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void pushInjectee(Injectee push) {
        Object object = this.lock;
        synchronized (object) {
            this.injectees.add(push);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void popInjectee() {
        Object object = this.lock;
        synchronized (object) {
            this.injectees.removeLast();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void addSubHandle(ServiceHandleImpl<?> subHandle) {
        Object object = this.lock;
        synchronized (object) {
            this.subHandles.add(subHandle);
        }
    }

    public Injectee getOriginalRequest() {
        Injectee injectee = this.getLastInjectee();
        return injectee;
    }

    public String toString() {
        return "ServiceHandle(" + this.root + "," + System.identityHashCode(this) + ")";
    }
}

