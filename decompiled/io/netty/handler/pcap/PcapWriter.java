/*
 * Decompiled with CFR 0.152.
 */
package io.netty.handler.pcap;

import io.netty.buffer.ByteBuf;
import io.netty.handler.pcap.PcapHeaders;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;

final class PcapWriter
implements Closeable {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(PcapWriter.class);
    private final OutputStream outputStream;
    private boolean isClosed;

    PcapWriter(OutputStream outputStream2) {
        this.outputStream = outputStream2;
    }

    PcapWriter(OutputStream outputStream2, ByteBuf byteBuf) throws IOException {
        this.outputStream = outputStream2;
        PcapHeaders.writeGlobalHeader(byteBuf);
        byteBuf.readBytes(outputStream2, byteBuf.readableBytes());
    }

    void writePacket(ByteBuf packetHeaderBuf, ByteBuf packet) throws IOException {
        if (this.isClosed) {
            logger.debug("Pcap Write attempted on closed PcapWriter");
        }
        long timestamp = System.currentTimeMillis();
        PcapHeaders.writePacketHeader(packetHeaderBuf, (int)(timestamp / 1000L), (int)(timestamp % 1000L * 1000L), packet.readableBytes(), packet.readableBytes());
        packetHeaderBuf.readBytes(this.outputStream, packetHeaderBuf.readableBytes());
        packet.readBytes(this.outputStream, packet.readableBytes());
    }

    @Override
    public void close() throws IOException {
        if (this.isClosed) {
            logger.debug("PcapWriter is already closed");
        } else {
            this.isClosed = true;
            this.outputStream.flush();
            this.outputStream.close();
            logger.debug("PcapWriter is now closed");
        }
    }
}

