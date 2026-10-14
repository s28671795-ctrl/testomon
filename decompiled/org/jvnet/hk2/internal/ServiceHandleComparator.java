/*
 * Decompiled with CFR 0.152.
 */
package org.jvnet.hk2.internal;

import java.io.Serializable;
import java.util.Comparator;
import org.glassfish.hk2.api.ServiceHandle;
import org.jvnet.hk2.internal.DescriptorComparator;

public class ServiceHandleComparator
implements Comparator<ServiceHandle<?>>,
Serializable {
    private static final long serialVersionUID = -3475592779302344427L;
    private final DescriptorComparator baseComparator = new DescriptorComparator();

    @Override
    public int compare(ServiceHandle<?> o1, ServiceHandle<?> o2) {
        return this.baseComparator.compare(o1.getActiveDescriptor(), o2.getActiveDescriptor());
    }
}

