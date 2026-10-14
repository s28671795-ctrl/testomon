/*
 * Decompiled with CFR 0.152.
 */
package org.jvnet.hk2.internal;

import java.util.Collections;
import java.util.Map;
import org.glassfish.hk2.api.ActiveDescriptor;
import org.glassfish.hk2.api.Injectee;
import org.glassfish.hk2.api.InstanceLifecycleEvent;
import org.glassfish.hk2.api.InstanceLifecycleEventType;

public class InstanceLifecycleEventImpl
implements InstanceLifecycleEvent {
    private final InstanceLifecycleEventType eventType;
    private final ActiveDescriptor<?> descriptor;
    private final Object lifecycleObject;
    private final Map<Injectee, Object> knownInjectees;

    InstanceLifecycleEventImpl(InstanceLifecycleEventType eventType, Object lifecycleObject, Map<Injectee, Object> knownInjectees, ActiveDescriptor<?> descriptor) {
        this.eventType = eventType;
        this.lifecycleObject = lifecycleObject;
        this.knownInjectees = knownInjectees == null ? null : Collections.unmodifiableMap(knownInjectees);
        this.descriptor = descriptor;
    }

    InstanceLifecycleEventImpl(InstanceLifecycleEventType eventType, Object lifecycleObject, ActiveDescriptor<?> descriptor) {
        this(eventType, lifecycleObject, null, descriptor);
    }

    @Override
    public InstanceLifecycleEventType getEventType() {
        return this.eventType;
    }

    @Override
    public ActiveDescriptor<?> getActiveDescriptor() {
        return this.descriptor;
    }

    @Override
    public Object getLifecycleObject() {
        return this.lifecycleObject;
    }

    @Override
    public Map<Injectee, Object> getKnownInjectees() {
        return this.knownInjectees;
    }

    public String toString() {
        String descName = this.descriptor == null ? "null" : this.descriptor.getImplementation();
        return "InstanceLifecycleEventImpl(" + (Object)((Object)this.eventType) + "," + descName + "," + System.identityHashCode(this) + ")";
    }
}

