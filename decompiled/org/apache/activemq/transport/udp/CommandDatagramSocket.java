/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport.udp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;
import org.apache.activemq.command.Command;
import org.apache.activemq.command.Endpoint;
import org.apache.activemq.openwire.BooleanStream;
import org.apache.activemq.openwire.OpenWireFormat;
import org.apache.activemq.transport.reliable.ReplayBuffer;
import org.apache.activemq.transport.udp.CommandChannelSupport;
import org.apache.activemq.transport.udp.DatagramHeaderMarshaller;
import org.apache.activemq.transport.udp.UdpTransport;
import org.apache.activemq.util.ByteArrayInputStream;
import org.apache.activemq.util.ByteArrayOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandDatagramSocket
extends CommandChannelSupport {
    private static final Logger LOG = LoggerFactory.getLogger(CommandDatagramSocket.class);
    private DatagramSocket channel;
    private Object readLock = new Object();
    private Object writeLock = new Object();
    private volatile int receiveCounter;

    public CommandDatagramSocket(UdpTransport transport, OpenWireFormat wireFormat, int datagramSize, SocketAddress targetAddress, DatagramHeaderMarshaller headerMarshaller, DatagramSocket channel) {
        super(transport, wireFormat, datagramSize, targetAddress, headerMarshaller);
        this.channel = channel;
    }

    @Override
    public void start() throws Exception {
    }

    @Override
    public void stop() throws Exception {
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
            DatagramPacket datagram = this.createDatagramPacket();
            this.channel.receive(datagram);
            ++this.receiveCounter;
            DataInputStream dataIn = new DataInputStream(new ByteArrayInputStream(datagram.getData(), 0, datagram.getLength()));
            from = this.headerMarshaller.createEndpoint(datagram, dataIn);
            answer = (Command)this.wireFormat.unmarshal(dataIn);
        }
        if (answer != null) {
            answer.setFrom(from);
            if (LOG.isDebugEnabled()) {
                LOG.debug("Channel: " + this.name + " about to process: " + answer);
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
            ByteArrayOutputStream writeBuffer = this.createByteArrayOutputStream();
            DataOutputStream dataOut = new DataOutputStream(writeBuffer);
            this.headerMarshaller.writeHeader(command, dataOut);
            int offset = writeBuffer.size();
            this.wireFormat.marshal(command, dataOut);
            if (this.remaining(writeBuffer) >= 0) {
                this.sendWriteBuffer(address, writeBuffer, command.getCommandId());
            } else {
                byte[] data = writeBuffer.toByteArray();
                boolean lastFragment = false;
                int length = data.length;
                int fragment = 0;
                while (!lastFragment) {
                    writeBuffer = this.createByteArrayOutputStream();
                    this.headerMarshaller.writeHeader(command, dataOut);
                    int chunkSize = this.remaining(writeBuffer);
                    BooleanStream bs = null;
                    if (this.wireFormat.isTightEncodingEnabled()) {
                        bs = new BooleanStream();
                        bs.writeBoolean(true);
                    }
                    chunkSize -= 9;
                    chunkSize = bs != null ? (chunkSize -= bs.marshalledSize()) : --chunkSize;
                    if (!this.wireFormat.isSizePrefixDisabled()) {
                        dataOut.writeInt(chunkSize);
                        chunkSize -= 4;
                    }
                    boolean bl = lastFragment = offset + chunkSize >= length;
                    if (chunkSize + offset > length) {
                        chunkSize = length - offset;
                    }
                    if (lastFragment) {
                        dataOut.write(61);
                    } else {
                        dataOut.write(60);
                    }
                    if (bs != null) {
                        bs.marshal(dataOut);
                    }
                    int commandId = command.getCommandId();
                    if (fragment > 0) {
                        commandId = this.sequenceGenerator.getNextSequenceId();
                    }
                    dataOut.writeInt(commandId);
                    if (bs == null) {
                        dataOut.write(1);
                    }
                    dataOut.writeInt(chunkSize);
                    dataOut.write(data, offset, chunkSize);
                    offset += chunkSize;
                    this.sendWriteBuffer(address, writeBuffer, commandId);
                    ++fragment;
                }
            }
        }
    }

    @Override
    public int getDatagramSize() {
        return this.datagramSize;
    }

    @Override
    public void setDatagramSize(int datagramSize) {
        this.datagramSize = datagramSize;
    }

    protected void sendWriteBuffer(SocketAddress address, ByteArrayOutputStream writeBuffer, int commandId) throws IOException {
        byte[] data = writeBuffer.toByteArray();
        this.sendWriteBuffer(commandId, address, data, false);
    }

    protected void sendWriteBuffer(int commandId, SocketAddress address, byte[] data, boolean redelivery) throws IOException {
        ReplayBuffer bufferCache = this.getReplayBuffer();
        if (bufferCache != null && !redelivery) {
            bufferCache.addBuffer(commandId, data);
        }
        if (LOG.isDebugEnabled()) {
            String text = redelivery ? "REDELIVERING" : "sending";
            LOG.debug("Channel: " + this.name + " " + text + " datagram: " + commandId + " to: " + address);
        }
        DatagramPacket packet = new DatagramPacket(data, 0, data.length, address);
        this.channel.send(packet);
    }

    @Override
    public void sendBuffer(int commandId, Object buffer) throws IOException {
        if (buffer != null) {
            byte[] data = (byte[])buffer;
            this.sendWriteBuffer(commandId, this.replayAddress, data, true);
        } else if (LOG.isWarnEnabled()) {
            LOG.warn("Request for buffer: " + commandId + " is no longer present");
        }
    }

    protected DatagramPacket createDatagramPacket() {
        return new DatagramPacket(new byte[this.datagramSize], this.datagramSize);
    }

    protected int remaining(ByteArrayOutputStream buffer) {
        return this.datagramSize - buffer.size();
    }

    protected ByteArrayOutputStream createByteArrayOutputStream() {
        return new ByteArrayOutputStream(this.datagramSize);
    }

    @Override
    public int getReceiveCounter() {
        return this.receiveCounter;
    }
}

