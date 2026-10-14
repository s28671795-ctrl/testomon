/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport.udp;

import java.io.IOException;
import java.net.SocketAddress;
import org.apache.activemq.command.Command;
import org.apache.activemq.openwire.OpenWireFormat;
import org.apache.activemq.transport.reliable.ReplayBuffer;
import org.apache.activemq.transport.udp.CommandChannel;
import org.apache.activemq.transport.udp.DatagramHeaderMarshaller;
import org.apache.activemq.transport.udp.UdpTransport;
import org.apache.activemq.util.IntSequenceGenerator;

public abstract class CommandChannelSupport
implements CommandChannel {
    protected OpenWireFormat wireFormat;
    protected int datagramSize = 4096;
    protected SocketAddress targetAddress;
    protected SocketAddress replayAddress;
    protected final String name;
    protected final IntSequenceGenerator sequenceGenerator;
    protected DatagramHeaderMarshaller headerMarshaller;
    private ReplayBuffer replayBuffer;

    public CommandChannelSupport(UdpTransport transport, OpenWireFormat wireFormat, int datagramSize, SocketAddress targetAddress, DatagramHeaderMarshaller headerMarshaller) {
        this.wireFormat = wireFormat;
        this.datagramSize = datagramSize;
        this.targetAddress = targetAddress;
        this.headerMarshaller = headerMarshaller;
        this.name = transport.toString();
        this.sequenceGenerator = transport.getSequenceGenerator();
        this.replayAddress = targetAddress;
        if (this.sequenceGenerator == null) {
            throw new IllegalArgumentException("No sequenceGenerator on the given transport: " + transport);
        }
    }

    public void write(Command command) throws IOException {
        this.write(command, this.targetAddress);
    }

    @Override
    public int getDatagramSize() {
        return this.datagramSize;
    }

    @Override
    public void setDatagramSize(int datagramSize) {
        this.datagramSize = datagramSize;
    }

    public SocketAddress getTargetAddress() {
        return this.targetAddress;
    }

    @Override
    public void setTargetAddress(SocketAddress targetAddress) {
        this.targetAddress = targetAddress;
    }

    public SocketAddress getReplayAddress() {
        return this.replayAddress;
    }

    @Override
    public void setReplayAddress(SocketAddress replayAddress) {
        this.replayAddress = replayAddress;
    }

    public String toString() {
        return "CommandChannel#" + this.name;
    }

    @Override
    public DatagramHeaderMarshaller getHeaderMarshaller() {
        return this.headerMarshaller;
    }

    @Override
    public void setHeaderMarshaller(DatagramHeaderMarshaller headerMarshaller) {
        this.headerMarshaller = headerMarshaller;
    }

    public ReplayBuffer getReplayBuffer() {
        return this.replayBuffer;
    }

    @Override
    public void setReplayBuffer(ReplayBuffer replayBuffer) {
        this.replayBuffer = replayBuffer;
    }
}

