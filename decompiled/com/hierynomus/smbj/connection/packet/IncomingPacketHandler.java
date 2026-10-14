/*
 * Decompiled with CFR 0.152.
 */
package com.hierynomus.smbj.connection.packet;

import com.hierynomus.protocol.transport.TransportException;
import com.hierynomus.smb.SMBPacketData;

public interface IncomingPacketHandler {
    public void handle(SMBPacketData<?> var1) throws TransportException;

    public IncomingPacketHandler setNext(IncomingPacketHandler var1);
}

