/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport.nio;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.URI;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import javax.net.SocketFactory;
import org.apache.activemq.openwire.OpenWireFormat;
import org.apache.activemq.transport.nio.NIOInputStream;
import org.apache.activemq.transport.nio.NIOOutputStream;
import org.apache.activemq.transport.nio.SelectorManager;
import org.apache.activemq.transport.nio.SelectorSelection;
import org.apache.activemq.transport.tcp.TcpTransport;
import org.apache.activemq.util.IOExceptionSupport;
import org.apache.activemq.util.ServiceStopper;
import org.apache.activemq.wireformat.WireFormat;

public class NIOTransport
extends TcpTransport {
    protected SocketChannel channel;
    protected SelectorSelection selection;
    protected ByteBuffer inputBuffer;
    protected ByteBuffer currentBuffer;
    protected int nextFrameSize;

    public NIOTransport(WireFormat wireFormat, SocketFactory socketFactory, URI remoteLocation, URI localLocation) throws UnknownHostException, IOException {
        super(wireFormat, socketFactory, remoteLocation, localLocation);
    }

    public NIOTransport(WireFormat wireFormat, Socket socket) throws IOException {
        super(wireFormat, socket);
    }

    public NIOTransport(WireFormat format, Socket socket, TcpTransport.InitBuffer initBuffer) throws IOException {
        super(format, socket, initBuffer);
    }

    @Override
    protected void initializeStreams() throws IOException {
        this.channel = this.socket.getChannel();
        this.channel.configureBlocking(false);
        this.selection = SelectorManager.getInstance().register(this.channel, new SelectorManager.Listener(){

            @Override
            public void onSelect(SelectorSelection selection) {
                NIOTransport.this.serviceRead();
            }

            @Override
            public void onError(SelectorSelection selection, Throwable error) {
                if (error instanceof IOException) {
                    NIOTransport.this.onException((IOException)error);
                } else {
                    NIOTransport.this.onException(IOExceptionSupport.create(error));
                }
            }
        });
        this.currentBuffer = this.inputBuffer = ByteBuffer.allocateDirect(this.getIoBufferSize());
        this.nextFrameSize = -1;
        this.currentBuffer.limit(4);
        NIOOutputStream outPutStream = new NIOOutputStream(this.channel, this.getIoBufferSize());
        this.dataOut = new DataOutputStream(outPutStream);
        this.buffOut = outPutStream;
    }

    protected int readFromBuffer() throws IOException {
        return this.channel.read(this.currentBuffer);
    }

    protected void serviceRead() {
        try {
            while (true) {
                int readSize;
                if ((readSize = this.readFromBuffer()) == -1) {
                    this.onException(new EOFException());
                    this.selection.close();
                } else if (readSize != 0) {
                    this.receiveCounter += readSize;
                    if (this.currentBuffer.hasRemaining()) continue;
                    if (this.nextFrameSize == -1) {
                        long maxFrameSize;
                        assert (this.inputBuffer == this.currentBuffer);
                        this.inputBuffer.flip();
                        this.nextFrameSize = this.inputBuffer.getInt() + 4;
                        if (this.wireFormat instanceof OpenWireFormat && (long)this.nextFrameSize > (maxFrameSize = ((OpenWireFormat)this.wireFormat).getMaxFrameSize())) {
                            throw new IOException("Frame size of " + this.nextFrameSize / 0x100000 + " MB larger than max allowed " + maxFrameSize / 0x100000L + " MB");
                        }
                        if (this.nextFrameSize > this.inputBuffer.capacity()) {
                            this.currentBuffer = ByteBuffer.allocateDirect(this.nextFrameSize);
                            this.currentBuffer.putInt(this.nextFrameSize);
                            continue;
                        }
                        this.inputBuffer.limit(this.nextFrameSize);
                        continue;
                    }
                    this.currentBuffer.flip();
                    Object command = this.wireFormat.unmarshal(new DataInputStream(new NIOInputStream(this.currentBuffer)));
                    this.doConsume(command);
                    this.nextFrameSize = -1;
                    this.inputBuffer.clear();
                    this.inputBuffer.limit(4);
                    this.currentBuffer = this.inputBuffer;
                    continue;
                }
                break;
            }
        }
        catch (IOException e) {
            this.onException(e);
        }
        catch (Throwable e) {
            this.onException(IOExceptionSupport.create(e));
        }
    }

    @Override
    protected void doStart() throws Exception {
        this.connect();
        this.selection.setInterestOps(1);
        this.selection.enable();
    }

    @Override
    protected void doStop(ServiceStopper stopper) throws Exception {
        if (this.selection != null) {
            this.selection.close();
            this.selection = null;
        }
        super.doStop(stopper);
    }
}

