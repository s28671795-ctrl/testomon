/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport.protocol;

import org.apache.activemq.command.WireFormatInfo;
import org.apache.activemq.transport.protocol.ProtocolVerifier;

public class OpenWireProtocolVerifier
implements ProtocolVerifier {
    protected final boolean sizePrefixDisabled;

    public OpenWireProtocolVerifier(boolean sizePrefixDisabled) {
        this.sizePrefixDisabled = sizePrefixDisabled;
    }

    @Override
    public boolean isProtocol(byte[] value) {
        int remainingLen;
        if (value.length < 8) {
            throw new IllegalArgumentException("Protocol header length changed " + value.length);
        }
        int start2 = !this.sizePrefixDisabled ? 4 : 0;
        int j = 0;
        if (value[start2] != 1) {
            return false;
        }
        WireFormatInfo info = new WireFormatInfo();
        byte[] magic = info.getMagic();
        int useLen = (remainingLen = value.length - ++start2) > magic.length ? magic.length : remainingLen;
        useLen += start2;
        for (int i = start2; i < useLen; ++i) {
            if (value[i] != magic[j]) {
                return false;
            }
            ++j;
        }
        return true;
    }
}

