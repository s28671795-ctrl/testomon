/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.net.tftp;

import java.net.DatagramPacket;
import java.net.InetAddress;
import org.apache.commons.net.tftp.TFTP;
import org.apache.commons.net.tftp.TFTPPacketException;
import org.apache.commons.net.tftp.TFTPRequestPacket;

public final class TFTPWriteRequestPacket
extends TFTPRequestPacket {
    public TFTPWriteRequestPacket(InetAddress destination, int port, String fileName, int mode) {
        super(destination, port, 2, fileName, mode);
    }

    TFTPWriteRequestPacket(DatagramPacket datagram) throws TFTPPacketException {
        super(2, datagram);
    }

    @Override
    public String toString() {
        return super.toString() + " WRQ " + this.getFilename() + " " + TFTP.getModeName(this.getMode());
    }
}

