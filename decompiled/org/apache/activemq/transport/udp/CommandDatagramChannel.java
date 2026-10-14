/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport.udp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import org.apache.activemq.command.Command;
import org.apache.activemq.command.Endpoint;
import org.apache.activemq.openwire.BooleanStream;
import org.apache.activemq.openwire.OpenWireFormat;
import org.apache.activemq.transport.reliable.ReplayBuffer;
import org.apache.activemq.transport.udp.ByteBufferPool;
import org.apache.activemq.transport.udp.CommandChannelSupport;
import org.apache.activemq.transport.udp.DatagramHeaderMarshaller;
import org.apache.activemq.transport.udp.UdpTransport;
import org.apache.activemq.util.ByteArrayInputStream;
import org.apache.activemq.util.ByteArrayOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandDatagramChannel
extends CommandChannelSupport {
    private static final Logger LOG = LoggerFactory.getLogger(CommandDatagramChannel.class);
    private DatagramChannel channel;
    private ByteBufferPool bufferPool;
    private Object readLock = new Object();
    private ByteBuffer readBuffer;
    private Object writeLock = new Object();
    private int defaultMarshalBufferSize = 65536;
    private volatile int receiveCounter;

    public CommandDatagramChannel(UdpTransport transport, OpenWireFormat wireFormat, int datagramSize, SocketAddress targetAddress, DatagramHeaderMarshaller headerMarshaller, DatagramChannel channel, ByteBufferPool bufferPool) {
        super(transport, wireFormat, datagramSize, targetAddress, headerMarshaller);
        this.channel = channel;
        this.bufferPool = bufferPool;
    }

    @Override
    public void start() throws Exception {
        this.bufferPool.setDefaultSize(this.datagramSize);
        this.bufferPool.start();
        this.readBuffer = this.bufferPool.borrowBuffer();
    }

    @Override
    public void stop() throws Exception {
        this.bufferPool.stop();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Command read() throws IOException {
        Command answer = null;
        Endpoint from = null;
        Object object = this.readLock;
        synchronized (object) {
            SocketAddress address;
            do {
                this.readBuffer.clear();
                address = this.channel.receive(this.readBuffer);
                this.readBuffer.flip();
            } while (this.readBuffer.limit() == 0);
            ++this.receiveCounter;
            from = this.headerMarshaller.createEndpoint(this.readBuffer, address);
            int remaining = this.readBuffer.remaining();
            byte[] data = new byte[remaining];
            this.readBuffer.get(data);
            DataInputStream dataIn = new DataInputStream(new ByteArrayInputStream(data));
            answer = (Command)this.wireFormat.unmarshal(dataIn);
        }
        if (answer != null) {
            answer.setFrom(from);
            if (LOG.isDebugEnabled()) {
                LOG.debug("Channel: " + this.name + " received from: " + from + " about to process: " + answer);
            }
        }
        return answer;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void write(Command command, SocketAddress address) throws IOException {
        Object object = this.writeLock;
        synchronized (object) {
            ByteArrayOutputStream largeBuffer = new ByteArrayOutputStream(this.defaultMarshalBufferSize);
            this.wireFormat.marshal(command, new DataOutputStream(largeBuffer));
            byte[] data = largeBuffer.toByteArray();
            int size = data.length;
            ByteBuffer writeBuffer = this.bufferPool.borrowBuffer();
            writeBuffer.clear();
            this.headerMarshaller.writeHeader(command, writeBuffer);
            if (size > writeBuffer.remaining()) {
                int offset = 0;
                boolean lastFragment = false;
                int length = data.length;
                int fragment = 0;
                while (!lastFragment) {
                    if (fragment > 0) {
                        writeBuffer = this.bufferPool.borrowBuffer();
                        writeBuffer.clear();
                        this.headerMarshaller.writeHeader(command, writeBuffer);
                    }
                    int chunkSize = writeBuffer.remaining();
                    BooleanStream bs = null;
                    if (this.wireFormat.isTightEncodingEnabled()) {
                        bs = new BooleanStream();
                        bs.writeBoolean(true);
                    }
                    chunkSize -= 9;
                    chunkSize = bs != null ? (chunkSize -= bs.marshalledSize()) : --chunkSize;
                    if (!this.wireFormat.isSizePrefixDisabled()) {
                        writeBuffer.putInt(chunkSize);
                        chunkSize -= 4;
                    }
                    boolean bl = lastFragment = offset + chunkSize >= length;
                    if (chunkSize + offset > length) {
                        chunkSize = length - offset;
                    }
                    if (lastFragment) {
                        writeBuffer.put((byte)61);
                    } else {
                        writeBuffer.put((byte)60);
                    }
                    if (bs != null) {
                        bs.marshal(writeBuffer);
                    }
                    int commandId = command.getCommandId();
                    if (fragment > 0) {
                        commandId = this.sequenceGenerator.getNextSequenceId();
                    }
                    writeBuffer.putInt(commandId);
                    if (bs == null) {
                        writeBuffer.put((byte)1);
                    }
                    writeBuffer.putInt(chunkSize);
                    writeBuffer.put(data, offset, chunkSize);
                    offset += chunkSize;
                    this.sendWriteBuffer(commandId, address, writeBuffer, false);
                    ++fragment;
                }
            } else {
                writeBuffer.put(data);
                this.sendWriteBuffer(command.getCommandId(), address, writeBuffer, false);
            }
        }
    }

    public ByteBufferPool getBufferPool() {
        return this.bufferPool;
    }

    public void setBufferPool(ByteBufferPool bufferPool) {
        this.bufferPool = bufferPool;
    }

    protected void sendWriteBuffer(int commandId, SocketAddress address, ByteBuffer writeBuffer, boolean redelivery) throws IOException {
        ReplayBuffer bufferCache = this.getReplayBuffer();
        if (bufferCache != null && !redelivery) {
            bufferCache.addBuffer(commandId, writeBuffer);
        }
        writeBuffer.flip();
        if (LOG.isDebugEnabled()) {
            String text = redelivery ? "REDELIVERING" : "sending";
            LOG.debug("Channel: " + this.name + " " + text + " datagram: " + commandId + " to: " + address);
        }
        this.channel.send(writeBuffer, address);
    }

    @Override
    public void sendBuffer(int commandId, Object buffer) throws IOException {
        if (buffer != null) {
            ByteBuffer writeBuffer = (ByteBuffer)buffer;
            this.sendWriteBuffer(commandId, this.getReplayAddress(), writeBuffer, true);
        } else if (LOG.isWarnEnabled()) {
            LOG.warn("Request for buffer: " + commandId + " is no longer present");
        }
    }

    @Override
    public int getReceiveCounter() {
        return this.receiveCounter;
    }
}

