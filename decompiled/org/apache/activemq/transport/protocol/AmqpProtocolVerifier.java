/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport.protocol;

import org.apache.activemq.transport.protocol.ProtocolVerifier;

public class AmqpProtocolVerifier
implements ProtocolVerifier {
    static final byte[] PREFIX = new byte[]{65, 77, 81, 80};

    @Override
    public boolean isProtocol(byte[] value) {
        for (int i = 0; i < PREFIX.length; ++i) {
            if (value[i] == PREFIX[i]) continue;
            return false;
        }
        return true;
    }
}

