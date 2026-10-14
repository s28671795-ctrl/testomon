/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.core.internal;

import java.nio.channels.ClosedChannelException;
import org.eclipse.jetty.util.thread.AutoLock;
import org.eclipse.jetty.websocket.core.CloseStatus;
import org.eclipse.jetty.websocket.core.Frame;
import org.eclipse.jetty.websocket.core.OpCode;
import org.eclipse.jetty.websocket.core.exception.ProtocolException;

public class WebSocketSessionState {
    private final AutoLock lock = new AutoLock();
    private State _sessionState = State.CONNECTING;
    private byte _incomingContinuation = (byte)-1;
    private byte _outgoingContinuation = (byte)-1;
    CloseStatus _closeStatus = null;

    public void onConnected() {
        try (AutoLock l = this.lock.lock();){
            if (this._sessionState != State.CONNECTING) {
                throw new IllegalStateException(this._sessionState.toString());
            }
            this._sessionState = State.CONNECTED;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void onOpen() {
        try (AutoLock l = this.lock.lock();){
            switch (this._sessionState) {
                case CONNECTED: {
                    this._sessionState = State.OPEN;
                    return;
                }
                case OSHUT: 
                case CLOSED: {
                    return;
                }
                default: {
                    throw new IllegalStateException(this._sessionState.toString());
                }
            }
        }
    }

    private State getState() {
        try (AutoLock l = this.lock.lock();){
            State state = this._sessionState;
            return state;
        }
    }

    public boolean isClosed() {
        return this.getState() == State.CLOSED;
    }

    public boolean isInputOpen() {
        State state = this.getState();
        return state == State.OPEN || state == State.OSHUT;
    }

    public boolean isOutputOpen() {
        State state = this.getState();
        return state == State.CONNECTED || state == State.OPEN || state == State.ISHUT;
    }

    public CloseStatus getCloseStatus() {
        try (AutoLock l = this.lock.lock();){
            CloseStatus closeStatus = this._closeStatus;
            return closeStatus;
        }
    }

    public boolean onClosed(CloseStatus closeStatus) {
        try (AutoLock l = this.lock.lock();){
            if (this._sessionState == State.CLOSED) {
                boolean bl = false;
                return bl;
            }
            this._closeStatus = closeStatus;
            this._sessionState = State.CLOSED;
            boolean bl = true;
            return bl;
        }
    }

    public void onError(Throwable t2) {
        try (AutoLock l = this.lock.lock();){
            if (this._sessionState != State.CLOSED || this._closeStatus == null) {
                throw new IllegalArgumentException();
            }
            if (!this._closeStatus.isAbnormal()) {
                this._closeStatus = new CloseStatus(1011, t2);
            }
            if (this._closeStatus.getCause() == null) {
                this._closeStatus = new CloseStatus(this._closeStatus.getCode(), this._closeStatus.getReason(), t2);
            }
        }
    }

    public boolean onEof() {
        try (AutoLock l = this.lock.lock();){
            switch (this._sessionState) {
                case CLOSED: 
                case ISHUT: {
                    boolean bl = false;
                    return bl;
                }
            }
            if (this._closeStatus == null || CloseStatus.isOrdinary(this._closeStatus.getCode())) {
                this._closeStatus = new CloseStatus(1006, "Session Closed", new ClosedChannelException());
            }
            this._sessionState = State.CLOSED;
            boolean bl = true;
            return bl;
        }
    }

    public boolean onOutgoingFrame(Frame frame) throws Exception {
        byte opcode = frame.getOpCode();
        boolean fin = frame.isFin();
        try (AutoLock l = this.lock.lock();){
            if (!this.isOutputOpen()) {
                throw new ClosedChannelException();
            }
            if (opcode == 8) {
                this._closeStatus = CloseStatus.getCloseStatus(frame);
                if (this._closeStatus.isAbnormal()) {
                    this._sessionState = State.CLOSED;
                    boolean bl = true;
                    return bl;
                }
                switch (this._sessionState) {
                    case CONNECTED: 
                    case OPEN: {
                        this._sessionState = State.OSHUT;
                        boolean bl = false;
                        return bl;
                    }
                    case ISHUT: {
                        this._sessionState = State.CLOSED;
                        boolean bl = true;
                        return bl;
                    }
                }
                throw new IllegalStateException(this._sessionState.toString());
            }
            if (frame.isDataFrame()) {
                this._outgoingContinuation = WebSocketSessionState.checkDataSequence(opcode, fin, this._outgoingContinuation);
            }
        }
        return false;
    }

    public boolean onIncomingFrame(Frame frame) throws ProtocolException, ClosedChannelException {
        byte opcode = frame.getOpCode();
        boolean fin = frame.isFin();
        try (AutoLock l = this.lock.lock();){
            if (!this.isInputOpen()) {
                throw new ClosedChannelException();
            }
            if (opcode == 8) {
                this._closeStatus = CloseStatus.getCloseStatus(frame);
                switch (this._sessionState) {
                    case OPEN: {
                        this._sessionState = State.ISHUT;
                        boolean bl = false;
                        return bl;
                    }
                    case OSHUT: {
                        this._sessionState = State.CLOSED;
                        boolean bl = true;
                        return bl;
                    }
                }
                throw new IllegalStateException(this._sessionState.toString());
            }
            if (frame.isDataFrame()) {
                this._incomingContinuation = WebSocketSessionState.checkDataSequence(opcode, fin, this._incomingContinuation);
            }
        }
        return false;
    }

    public String toString() {
        return String.format("%s@%x{%s,i=%s,o=%s,c=%s}", new Object[]{this.getClass().getSimpleName(), this.hashCode(), this._sessionState, OpCode.name(this._incomingContinuation), OpCode.name(this._outgoingContinuation), this._closeStatus});
    }

    private static byte checkDataSequence(byte opcode, boolean fin, byte lastOpCode) throws ProtocolException {
        switch (opcode) {
            case 1: 
            case 2: {
                if (lastOpCode != -1) {
                    throw new ProtocolException("DataFrame before fin==true");
                }
                if (!fin) {
                    return opcode;
                }
                return -1;
            }
            case 0: {
                if (lastOpCode == -1) {
                    throw new ProtocolException("CONTINUATION after fin==true");
                }
                if (fin) {
                    return -1;
                }
                return lastOpCode;
            }
        }
        return lastOpCode;
    }

    static enum State {
        CONNECTING,
        CONNECTED,
        OPEN,
        ISHUT,
        OSHUT,
        CLOSED;

    }
}

