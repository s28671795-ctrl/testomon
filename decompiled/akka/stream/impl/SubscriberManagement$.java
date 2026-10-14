/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.stream.impl.ActorPublisher$;
import akka.stream.impl.SubscriberManagement;

public final class SubscriberManagement$ {
    public static final SubscriberManagement$ MODULE$ = new SubscriberManagement$();
    private static final SubscriberManagement.ErrorCompleted ShutDown = new SubscriberManagement.ErrorCompleted(ActorPublisher$.MODULE$.NormalShutdownReason());

    public SubscriberManagement.ErrorCompleted ShutDown() {
        return ShutDown;
    }

    private SubscriberManagement$() {
    }
}

