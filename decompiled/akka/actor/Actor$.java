/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.ActorRef;
import java.io.Serializable;
import scala.Function1;

public final class Actor$ {
    public static final Actor$ MODULE$ = new Actor$();
    private static final ActorRef noSender = null;
    private static final Object akka$actor$Actor$$NotHandled = new Object();
    private static final Function1<Object, Object> akka$actor$Actor$$notHandledFun = (Function1<Object, Object> & Serializable)x$2 -> MODULE$.akka$actor$Actor$$NotHandled();

    public final ActorRef noSender() {
        return noSender;
    }

    public final Object akka$actor$Actor$$NotHandled() {
        return akka$actor$Actor$$NotHandled;
    }

    public final Function1<Object, Object> akka$actor$Actor$$notHandledFun() {
        return akka$actor$Actor$$notHandledFun;
    }

    private Actor$() {
    }
}

