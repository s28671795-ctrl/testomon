/*
 * Decompiled with CFR 0.152.
 */
package org.jvnet.hk2.internal;

import java.util.HashMap;
import java.util.LinkedList;
import org.glassfish.hk2.api.DescriptorVisibility;
import org.glassfish.hk2.api.Injectee;
import org.glassfish.hk2.api.InstantiationData;
import org.glassfish.hk2.api.InstantiationService;
import org.glassfish.hk2.api.Visibility;

@Visibility(value=DescriptorVisibility.LOCAL)
public class InstantiationServiceImpl
implements InstantiationService {
    private final HashMap<Long, LinkedList<Injectee>> injecteeStack = new HashMap();

    @Override
    public synchronized InstantiationData getInstantiationData() {
        long tid = Thread.currentThread().getId();
        LinkedList<Injectee> threadStack = this.injecteeStack.get(tid);
        if (threadStack == null) {
            return null;
        }
        if (threadStack.isEmpty()) {
            return null;
        }
        final Injectee head = threadStack.getLast();
        return new InstantiationData(){

            @Override
            public Injectee getParentInjectee() {
                return head;
            }

            public String toString() {
                return "InstantiationData(" + head + "," + System.identityHashCode(this) + ")";
            }
        };
    }

    public synchronized void pushInjecteeParent(Injectee injectee) {
        long tid = Thread.currentThread().getId();
        LinkedList<Injectee> threadStack = this.injecteeStack.get(tid);
        if (threadStack == null) {
            threadStack = new LinkedList();
            this.injecteeStack.put(tid, threadStack);
        }
        threadStack.addLast(injectee);
    }

    public synchronized void popInjecteeParent() {
        long tid = Thread.currentThread().getId();
        LinkedList<Injectee> threadStack = this.injecteeStack.get(tid);
        if (threadStack == null) {
            return;
        }
        threadStack.removeLast();
        if (threadStack.isEmpty()) {
            this.injecteeStack.remove(tid);
        }
    }

    public String toString() {
        return "InstantiationServiceImpl(" + this.injecteeStack.keySet() + "," + System.identityHashCode(this) + ")";
    }
}

