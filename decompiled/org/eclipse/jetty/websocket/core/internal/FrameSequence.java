/*
 * Decompiled with CFR 0.152.
 */
package org.eclipse.jetty.websocket.core.internal;

import org.eclipse.jetty.util.thread.AutoLock;
import org.eclipse.jetty.websocket.core.OpCode;
import org.eclipse.jetty.websocket.core.exception.ProtocolException;

public class FrameSequence {
    private final AutoLock lock = new AutoLock();
    private byte state = (byte)-1;

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void check(byte opcode, boolean fin) throws ProtocolException {
        try (AutoLock l = this.lock.lock();){
            if (this.state == 8) {
                throw new ProtocolException(OpCode.name(opcode) + " after CLOSE");
            }
            switch (opcode) {
                case -1: {
                    throw new ProtocolException("UNDEFINED OpCode: " + OpCode.name(opcode));
                }
                case 0: {
                    if (this.state == -1) {
                        throw new ProtocolException("CONTINUATION after fin==true");
                    }
                    if (!fin) return;
                    this.state = (byte)-1;
                    return;
                }
                case 8: {
                    this.state = (byte)8;
                    return;
                }
                case 9: 
                case 10: {
                    return;
                }
                default: {
                    if (this.state != -1) {
                        throw new ProtocolException("DataFrame before fin==true");
                    }
                    if (fin) return;
                    this.state = opcode;
                    return;
                }
            }
        }
    }
}

