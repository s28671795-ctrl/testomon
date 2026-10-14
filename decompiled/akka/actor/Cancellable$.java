/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.Cancellable;
import akka.annotation.InternalApi;

public final class Cancellable$ {
    public static final Cancellable$ MODULE$ = new Cancellable$();
    private static final Cancellable alreadyCancelled = new Cancellable(){

        public boolean cancel() {
            return false;
        }

        public boolean isCancelled() {
            return true;
        }
    };
    @InternalApi
    private static final Cancellable initialNotCancelled = new Cancellable(){

        public boolean cancel() {
            return false;
        }

        public boolean isCancelled() {
            return false;
        }
    };

    public Cancellable alreadyCancelled() {
        return alreadyCancelled;
    }

    public Cancellable initialNotCancelled() {
        return initialNotCancelled;
    }

    private Cancellable$() {
    }
}

