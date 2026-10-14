/*
 * Decompiled with CFR 0.152.
 */
package org.jvnet.hk2.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.ListIterator;
import org.jvnet.hk2.internal.ServiceLocatorImpl;
import org.jvnet.hk2.internal.SystemDescriptor;

public class IndexedListData {
    private final ArrayList<SystemDescriptor<?>> unsortedList = new ArrayList();
    private volatile boolean sorted = true;

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public Collection<SystemDescriptor<?>> getSortedList() {
        if (this.sorted) {
            return this.unsortedList;
        }
        IndexedListData indexedListData = this;
        synchronized (indexedListData) {
            if (this.sorted) {
                return this.unsortedList;
            }
            if (this.unsortedList.size() <= 1) {
                this.sorted = true;
                return this.unsortedList;
            }
            Collections.sort(this.unsortedList, ServiceLocatorImpl.DESCRIPTOR_COMPARATOR);
            this.sorted = true;
            return this.unsortedList;
        }
    }

    public synchronized void addDescriptor(SystemDescriptor<?> descriptor) {
        this.unsortedList.add(descriptor);
        this.sorted = this.unsortedList.size() <= 1;
        descriptor.addList(this);
    }

    public synchronized void removeDescriptor(SystemDescriptor<?> descriptor) {
        ListIterator<SystemDescriptor<?>> iterator2 = this.unsortedList.listIterator();
        while (iterator2.hasNext()) {
            SystemDescriptor<?> candidate = iterator2.next();
            if (ServiceLocatorImpl.DESCRIPTOR_COMPARATOR.compare(descriptor, candidate) != 0) continue;
            iterator2.remove();
            break;
        }
        this.sorted = this.unsortedList.size() <= 1;
        descriptor.removeList(this);
    }

    public synchronized boolean isEmpty() {
        return this.unsortedList.isEmpty();
    }

    public synchronized void unSort() {
        if (this.unsortedList.size() > 1) {
            this.sorted = false;
        }
    }

    public synchronized void clear() {
        for (SystemDescriptor<?> descriptor : this.unsortedList) {
            descriptor.removeList(this);
        }
        this.unsortedList.clear();
    }

    public synchronized int size() {
        return this.unsortedList.size();
    }
}

