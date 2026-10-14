/*
 * Decompiled with CFR 0.152.
 */
package org.apache.commons.net.tftp;

import java.net.DatagramPacket;
import java.net.InetAddress;
import org.apache.commons.net.tftp.TFTP;
import org.apache.commons.net.tftp.TFTPPacketException;
import org.apache.commons.net.tftp.TFTPRequestPacket;

public final class TFTPReadRequestPacket
extends TFTPRequestPacket {
    public TFTPReadRequestPacket(InetAddress destination, int port, String fileName, int mode) {
        super(destination, port, 1, fileName, mode);
    }

    TFTPReadRequestPacket(DatagramPacket datagram) throws TFTPPacketException {
        super(1, datagram);
    }

    @Override
    public String toString() {
        return super.toString() + " RRQ " + this.getFilename() + " " + TFTP.getModeName(this.getMode());
    }
}

