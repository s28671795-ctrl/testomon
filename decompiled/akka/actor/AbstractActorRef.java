/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.RepointableActorRef;
import akka.util.Unsafe;

final class AbstractActorRef {
    static final long cellOffset;
    static final long lookupOffset;

    AbstractActorRef() {
    }

    static {
        try {
            cellOffset = Unsafe.instance.objectFieldOffset(RepointableActorRef.class.getDeclaredField("_cellDoNotCallMeDirectly"));
            lookupOffset = Unsafe.instance.objectFieldOffset(RepointableActorRef.class.getDeclaredField("_lookupDoNotCallMeDirectly"));
        }
        catch (Throwable throwable) {
            throw new ExceptionInInitializerError(throwable);
        }
    }
}

