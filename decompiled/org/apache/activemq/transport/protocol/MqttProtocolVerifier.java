/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport.protocol;

import java.nio.ByteBuffer;
import org.apache.activemq.transport.protocol.ProtocolVerifier;

public class MqttProtocolVerifier
implements ProtocolVerifier {
    @Override
    public boolean isProtocol(byte[] value) {
        ByteBuffer buf = ByteBuffer.wrap(value);
        if (buf.get() != 16 || !this.validateRemainingLength(buf) || buf.get() != 0) {
            return false;
        }
        byte b = buf.get();
        return (b == 4 || b == 6) && buf.get() == 77;
    }

    private boolean validateRemainingLength(ByteBuffer buffer) {
        int msb = -128;
        for (int i = 0; i < 4; i = (int)((byte)(i + 1))) {
            if ((buffer.get() & msb) == msb) continue;
            return true;
        }
        return false;
    }
}

