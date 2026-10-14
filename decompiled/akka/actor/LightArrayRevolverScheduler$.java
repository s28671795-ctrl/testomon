/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.Cancellable;
import akka.actor.LightArrayRevolverScheduler;
import akka.util.Unsafe;

public final class LightArrayRevolverScheduler$ {
    public static final LightArrayRevolverScheduler$ MODULE$ = new LightArrayRevolverScheduler$();
    public static final long akka$actor$LightArrayRevolverScheduler$$taskOffset = Unsafe.instance.objectFieldOffset(LightArrayRevolverScheduler.TaskHolder.class.getDeclaredField("task"));
    public static final Runnable akka$actor$LightArrayRevolverScheduler$$CancelledTask = new Runnable(){

        public void run() {
        }
    };
    public static final Runnable akka$actor$LightArrayRevolverScheduler$$ExecutedTask = new Runnable(){

        public void run() {
        }
    };
    private static final LightArrayRevolverScheduler.TimerTask akka$actor$LightArrayRevolverScheduler$$NotCancellable = new LightArrayRevolverScheduler.TimerTask(){

        public boolean cancel() {
            return false;
        }

        public boolean isCancelled() {
            return false;
        }

        public void run() {
        }
    };
    private static final Cancellable akka$actor$LightArrayRevolverScheduler$$InitialRepeatMarker = new Cancellable(){

        public boolean cancel() {
            return false;
        }

        public boolean isCancelled() {
            return false;
        }
    };

    public LightArrayRevolverScheduler.TimerTask akka$actor$LightArrayRevolverScheduler$$NotCancellable() {
        return akka$actor$LightArrayRevolverScheduler$$NotCancellable;
    }

    public Cancellable akka$actor$LightArrayRevolverScheduler$$InitialRepeatMarker() {
        return akka$actor$LightArrayRevolverScheduler$$InitialRepeatMarker;
    }

    private LightArrayRevolverScheduler$() {
    }
}

