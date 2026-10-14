/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport.protocol;

import java.nio.charset.StandardCharsets;
import org.apache.activemq.transport.protocol.ProtocolVerifier;

public class StompProtocolVerifier
implements ProtocolVerifier {
    @Override
    public boolean isProtocol(byte[] value) {
        String frameStart = new String(value, StandardCharsets.US_ASCII);
        return frameStart.startsWith("CONNECT") || frameStart.startsWith("STOMP");
    }
}

