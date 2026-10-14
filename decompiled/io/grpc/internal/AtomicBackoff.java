/*
 * Decompiled with CFR 0.152.
 */
package io.grpc.internal;

import com.google.common.base.Preconditions;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public final class AtomicBackoff {
    private static final Logger log = Logger.getLogger(AtomicBackoff.class.getName());
    private final String name;
    private final AtomicLong value = new AtomicLong();

    public AtomicBackoff(String name, long value) {
        Preconditions.checkArgument(value > 0L, "value must be positive");
        this.name = name;
        this.value.set(value);
    }

    public State getState() {
        return new State(this.value.get());
    }

    @ThreadSafe
    public final class State {
        private final long savedValue;

        private State(long value) {
            this.savedValue = value;
        }

        public long get() {
            return this.savedValue;
        }

        public void backoff() {
            long newValue = Math.max(this.savedValue * 2L, this.savedValue);
            boolean swapped = AtomicBackoff.this.value.compareAndSet(this.savedValue, newValue);
            assert (AtomicBackoff.this.value.get() >= newValue);
            if (swapped) {
                log.log(Level.WARNING, "Increased {0} to {1}", new Object[]{AtomicBackoff.this.name, newValue});
            }
        }
    }
}

