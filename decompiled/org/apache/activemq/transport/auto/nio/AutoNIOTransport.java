/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport.auto.nio;

import java.io.IOException;
import java.net.Socket;
import java.net.URI;
import java.net.UnknownHostException;
import javax.net.SocketFactory;
import org.apache.activemq.transport.nio.NIOTransport;
import org.apache.activemq.transport.tcp.TcpTransport;
import org.apache.activemq.wireformat.WireFormat;

public class AutoNIOTransport
extends NIOTransport {
    boolean doneInitBuffer = false;

    public AutoNIOTransport(WireFormat format, Socket socket, TcpTransport.InitBuffer initBuffer) throws IOException {
        super(format, socket, initBuffer);
    }

    public AutoNIOTransport(WireFormat wireFormat, Socket socket) throws IOException {
        super(wireFormat, socket);
    }

    public AutoNIOTransport(WireFormat wireFormat, SocketFactory socketFactory, URI remoteLocation, URI localLocation) throws UnknownHostException, IOException {
        super(wireFormat, socketFactory, remoteLocation, localLocation);
    }

    @Override
    protected int readFromBuffer() throws IOException {
        int readSize = 0;
        if (!this.doneInitBuffer) {
            if (this.initBuffer == null || this.initBuffer.readSize < 8) {
                throw new IOException("Protocol type could not be determined.");
            }
            if (this.nextFrameSize == -1) {
                readSize = 4;
                this.initBuffer.buffer.flip();
                if (this.initBuffer.buffer.remaining() < 8) {
                    throw new IOException("Protocol type could not be determined.");
                }
                for (int i = 0; i < 4; ++i) {
                    this.currentBuffer.put(this.initBuffer.buffer.get());
                }
            } else {
                for (int i = 0; i < 4; ++i) {
                    this.currentBuffer.put(this.initBuffer.buffer.get());
                }
                readSize = 4;
                this.doneInitBuffer = true;
            }
        } else {
            readSize += this.channel.read(this.currentBuffer);
        }
        return readSize;
    }
}

