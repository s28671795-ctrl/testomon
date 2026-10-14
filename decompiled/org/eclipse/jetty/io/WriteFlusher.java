/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.io;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.WritePendingException;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import org.eclipse.jetty.io.Connection;
import org.eclipse.jetty.io.DatagramChannelEndPoint;
import org.eclipse.jetty.io.EndPoint;
import org.eclipse.jetty.util.BufferUtil;
import org.eclipse.jetty.util.Callback;
import org.eclipse.jetty.util.thread.Invocable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class WriteFlusher {
    private static final Logger LOG = LoggerFactory.getLogger(WriteFlusher.class);
    private static final boolean DEBUG = LOG.isDebugEnabled();
    private static final ByteBuffer[] EMPTY_BUFFERS = new ByteBuffer[]{BufferUtil.EMPTY_BUFFER};
    private static final EnumMap<StateType, Set<StateType>> __stateTransitions = new EnumMap(StateType.class);
    private static final State __IDLE = new IdleState();
    private static final State __WRITING = new WritingState();
    private static final State __COMPLETING = new CompletingState();
    private final EndPoint _endPoint;
    private final AtomicReference<State> _state = new AtomicReference();

    protected WriteFlusher(EndPoint endPoint) {
        this._state.set(__IDLE);
        this._endPoint = endPoint;
    }

    private boolean updateState(State previous, State next) {
        if (!this.isTransitionAllowed(previous, next)) {
            throw new IllegalStateException();
        }
        boolean updated = this._state.compareAndSet(previous, next);
        if (DEBUG) {
            LOG.debug("update {}:{}{}{}", this, previous, updated ? "-->" : "!->", next);
        }
        return updated;
    }

    private boolean isTransitionAllowed(State currentState, State newState) {
        Set<StateType> allowedNewStateTypes = __stateTransitions.get((Object)currentState.getType());
        if (!allowedNewStateTypes.contains((Object)newState.getType())) {
            LOG.warn("{}: {} -> {} not allowed", this, currentState, newState);
            return false;
        }
        return true;
    }

    public Invocable.InvocationType getCallbackInvocationType() {
        State s2 = this._state.get();
        return s2 instanceof PendingState ? ((PendingState)s2).getCallbackInvocationType() : Invocable.InvocationType.BLOCKING;
    }

    protected abstract void onIncompleteFlush();

    public void write(Callback callback, ByteBuffer ... buffers) throws WritePendingException {
        this.write(callback, (SocketAddress)null, buffers);
    }

    public void write(Callback callback, SocketAddress address, ByteBuffer ... buffers) throws WritePendingException {
        Objects.requireNonNull(callback);
        if (this.isFailed()) {
            this.fail(callback, new Throwable[0]);
            return;
        }
        if (DEBUG) {
            LOG.debug("write: {} {}", (Object)this, (Object)BufferUtil.toDetailString(buffers));
        }
        if (!this.updateState(__IDLE, __WRITING)) {
            throw new WritePendingException();
        }
        try {
            buffers = this.flush(address, buffers);
            if (buffers != null) {
                PendingState pending;
                if (DEBUG) {
                    LOG.debug("flush incomplete {}", (Object)this);
                }
                if (this.updateState(__WRITING, pending = new PendingState(callback, address, buffers))) {
                    this.onIncompleteFlush();
                } else {
                    this.fail(callback, new Throwable[0]);
                }
                return;
            }
            if (this.updateState(__WRITING, __IDLE)) {
                callback.succeeded();
            } else {
                this.fail(callback, new Throwable[0]);
            }
        }
        catch (Throwable e) {
            if (DEBUG) {
                LOG.debug("write exception", e);
            }
            if (this.updateState(__WRITING, new FailedState(e))) {
                callback.failed(e);
            }
            this.fail(callback, e);
        }
    }

    private void fail(Callback callback, Throwable ... suppressed) {
        Throwable cause;
        block4: while (true) {
            State state = this._state.get();
            switch (state.getType()) {
                case FAILED: {
                    FailedState failed = (FailedState)state;
                    cause = failed.getCause();
                    break block4;
                }
                case IDLE: {
                    for (Throwable t2 : suppressed) {
                        LOG.warn("Failed Write Cause", t2);
                    }
                    return;
                }
                default: {
                    IllegalStateException t3 = new IllegalStateException();
                    if (!this._state.compareAndSet(state, new FailedState(t3))) continue block4;
                    cause = t3;
                    break block4;
                }
            }
            break;
        }
        for (Throwable t4 : suppressed) {
            if (t4 == cause) continue;
            cause.addSuppressed(t4);
        }
        callback.failed(cause);
    }

    public void completeWrite() {
        State previous;
        if (DEBUG) {
            LOG.debug("completeWrite: {}", (Object)this);
        }
        if ((previous = this._state.get()).getType() != StateType.PENDING) {
            return;
        }
        PendingState pending = (PendingState)previous;
        if (!this.updateState(pending, __COMPLETING)) {
            return;
        }
        Callback callback = pending._callback;
        try {
            ByteBuffer[] buffers = pending._buffers;
            SocketAddress address = pending._address;
            buffers = this.flush(address, buffers);
            if (buffers != null) {
                if (DEBUG) {
                    LOG.debug("flushed incomplete {}", (Object)BufferUtil.toDetailString(buffers));
                }
                if (buffers != pending._buffers) {
                    pending = new PendingState(callback, address, buffers);
                }
                if (this.updateState(__COMPLETING, pending)) {
                    this.onIncompleteFlush();
                } else {
                    this.fail(callback, new Throwable[0]);
                }
                return;
            }
            if (this.updateState(__COMPLETING, __IDLE)) {
                callback.succeeded();
            } else {
                this.fail(callback, new Throwable[0]);
            }
        }
        catch (Throwable e) {
            if (DEBUG) {
                LOG.debug("completeWrite exception", e);
            }
            if (this.updateState(__COMPLETING, new FailedState(e))) {
                callback.failed(e);
            }
            this.fail(callback, e);
        }
    }

    protected ByteBuffer[] flush(SocketAddress address, ByteBuffer[] buffers) throws IOException {
        boolean progress = true;
        while (progress && buffers != null) {
            Connection connection;
            long before = BufferUtil.remaining(buffers);
            boolean flushed = address == null ? this._endPoint.flush(buffers) : ((DatagramChannelEndPoint)this._endPoint).send(address, buffers);
            long after = BufferUtil.remaining(buffers);
            long written = before - after;
            if (LOG.isDebugEnabled()) {
                LOG.debug("Flushed={} written={} remaining={} {}", flushed, written, after, this);
            }
            if (written > 0L && (connection = this._endPoint.getConnection()) instanceof Listener) {
                ((Listener)((Object)connection)).onFlushed(written);
            }
            if (flushed) {
                return null;
            }
            progress = written > 0L;
            int index = 0;
            while (true) {
                if (index == buffers.length) {
                    buffers = null;
                    index = 0;
                    break;
                }
                int remaining = buffers[index].remaining();
                if (remaining > 0) break;
                ++index;
                progress = true;
            }
            if (index <= 0) continue;
            buffers = Arrays.copyOfRange(buffers, index, buffers.length);
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("!fully flushed {}", (Object)this);
        }
        return buffers == null ? EMPTY_BUFFERS : buffers;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean onFail(Throwable cause) {
        block5: while (true) {
            State current = this._state.get();
            switch (current.getType()) {
                case FAILED: 
                case IDLE: {
                    if (!DEBUG) return false;
                    LOG.debug("ignored: {} {}", (Object)cause, (Object)this);
                    LOG.trace("IGNORED", cause);
                    return false;
                }
                case PENDING: {
                    PendingState pending;
                    if (DEBUG) {
                        LOG.debug("failed: {}", (Object)this, (Object)cause);
                    }
                    if (!this.updateState(pending = (PendingState)current, new FailedState(cause))) continue block5;
                    pending._callback.failed(cause);
                    return true;
                }
                case WRITING: 
                case COMPLETING: {
                    if (DEBUG) {
                        LOG.debug("failed: {}", (Object)this, (Object)cause);
                    }
                    if (this.updateState(current, new FailedState(cause))) return true;
                    continue block5;
                }
            }
            break;
        }
        throw new IllegalStateException();
    }

    public void onClose() {
        switch (this._state.get().getType()) {
            case FAILED: 
            case IDLE: {
                return;
            }
        }
        this.onFail(new ClosedChannelException());
    }

    boolean isFailed() {
        return this.isState(StateType.FAILED);
    }

    boolean isIdle() {
        return this.isState(StateType.IDLE);
    }

    public boolean isPending() {
        return this.isState(StateType.PENDING);
    }

    private boolean isState(StateType type) {
        return this._state.get().getType() == type;
    }

    public String toStateString() {
        switch (this._state.get().getType()) {
            case WRITING: {
                return "W";
            }
            case PENDING: {
                return "P";
            }
            case COMPLETING: {
                return "C";
            }
            case IDLE: {
                return "-";
            }
            case FAILED: {
                return "F";
            }
        }
        return "?";
    }

    public String toString() {
        State s2 = this._state.get();
        return String.format("WriteFlusher@%x{%s}->%s", this.hashCode(), s2, s2 instanceof PendingState ? ((PendingState)s2)._callback : null);
    }

    static {
        __stateTransitions.put(StateType.IDLE, EnumSet.of(StateType.WRITING));
        __stateTransitions.put(StateType.WRITING, EnumSet.of(StateType.IDLE, StateType.PENDING, StateType.FAILED));
        __stateTransitions.put(StateType.PENDING, EnumSet.of(StateType.COMPLETING, StateType.IDLE, StateType.FAILED));
        __stateTransitions.put(StateType.COMPLETING, EnumSet.of(StateType.IDLE, StateType.PENDING, StateType.FAILED));
        __stateTransitions.put(StateType.FAILED, EnumSet.noneOf(StateType.class));
    }

    private static class State {
        private final StateType _type;

        private State(StateType stateType) {
            this._type = stateType;
        }

        public StateType getType() {
            return this._type;
        }

        public String toString() {
            return String.format("%s", new Object[]{this._type});
        }
    }

    private static enum StateType {
        IDLE,
        WRITING,
        PENDING,
        COMPLETING,
        FAILED;

    }

    private class PendingState
    extends State {
        private final Callback _callback;
        private final SocketAddress _address;
        private final ByteBuffer[] _buffers;

        private PendingState(Callback callback, SocketAddress address, ByteBuffer[] buffers) {
            super(StateType.PENDING);
            this._callback = callback;
            this._address = address;
            this._buffers = buffers;
        }

        Invocable.InvocationType getCallbackInvocationType() {
            return Invocable.getInvocationType(this._callback);
        }
    }

    private static class FailedState
    extends State {
        private final Throwable _cause;

        private FailedState(Throwable cause) {
            super(StateType.FAILED);
            this._cause = cause;
        }

        public Throwable getCause() {
            return this._cause;
        }
    }

    public static interface Listener {
        public void onFlushed(long var1) throws IOException;
    }

    private static class IdleState
    extends State {
        private IdleState() {
            super(StateType.IDLE);
        }
    }

    private static class WritingState
    extends State {
        private WritingState() {
            super(StateType.WRITING);
        }
    }

    private static class CompletingState
    extends State {
        private CompletingState() {
            super(StateType.COMPLETING);
        }
    }
}

