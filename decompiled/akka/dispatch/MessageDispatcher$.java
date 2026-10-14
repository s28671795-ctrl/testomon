/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch;

import akka.actor.ActorRef;
import akka.dispatch.MessageDispatcher;
import akka.util.Index;
import java.util.Comparator;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

public final class MessageDispatcher$ {
    public static final MessageDispatcher$ MODULE$ = new MessageDispatcher$();
    private static Index<MessageDispatcher, ActorRef> actors;
    private static final int UNSCHEDULED;
    private static final int SCHEDULED;
    private static final int RESCHEDULED;
    private static volatile boolean bitmap$0;

    static {
        UNSCHEDULED = 0;
        SCHEDULED = 1;
        RESCHEDULED = 2;
    }

    public int UNSCHEDULED() {
        return UNSCHEDULED;
    }

    public int SCHEDULED() {
        return SCHEDULED;
    }

    public int RESCHEDULED() {
        return RESCHEDULED;
    }

    public final boolean debug() {
        return false;
    }

    private Index<MessageDispatcher, ActorRef> actors$lzycompute() {
        MessageDispatcher$ messageDispatcher$ = this;
        synchronized (messageDispatcher$) {
            if (!bitmap$0) {
                actors = new Index(16, new Comparator<ActorRef>(){

                    public Comparator<ActorRef> reversed() {
                        return Comparator.super.reversed();
                    }

                    public Comparator<ActorRef> thenComparing(Comparator<? super ActorRef> x$1) {
                        return Comparator.super.thenComparing(x$1);
                    }

                    public <U> Comparator<ActorRef> thenComparing(Function<? super ActorRef, ? extends U> x$1, Comparator<? super U> x$2) {
                        return Comparator.super.thenComparing(x$1, x$2);
                    }

                    public <U extends Comparable<? super U>> Comparator<ActorRef> thenComparing(Function<? super ActorRef, ? extends U> x$1) {
                        return Comparator.super.thenComparing(x$1);
                    }

                    public Comparator<ActorRef> thenComparingInt(ToIntFunction<? super ActorRef> x$1) {
                        return Comparator.super.thenComparingInt(x$1);
                    }

                    public Comparator<ActorRef> thenComparingLong(ToLongFunction<? super ActorRef> x$1) {
                        return Comparator.super.thenComparingLong(x$1);
                    }

                    public Comparator<ActorRef> thenComparingDouble(ToDoubleFunction<? super ActorRef> x$1) {
                        return Comparator.super.thenComparingDouble(x$1);
                    }

                    public int compare(ActorRef a, ActorRef b) {
                        return a.compareTo(b);
                    }
                });
                bitmap$0 = true;
            }
        }
        return actors;
    }

    public Index<MessageDispatcher, ActorRef> actors() {
        if (!bitmap$0) {
            return this.actors$lzycompute();
        }
        return actors;
    }

    public void printActors() {
    }

    private MessageDispatcher$() {
    }
}

