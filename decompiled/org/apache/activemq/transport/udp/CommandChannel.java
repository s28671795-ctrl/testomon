/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport.udp;

import java.io.IOException;
import java.net.SocketAddress;
import org.apache.activemq.Service;
import org.apache.activemq.command.Command;
import org.apache.activemq.transport.reliable.ReplayBuffer;
import org.apache.activemq.transport.reliable.Replayer;
import org.apache.activemq.transport.udp.DatagramHeaderMarshaller;

public interface CommandChannel
extends Replayer,
Service {
    public Command read() throws IOException;

    public void write(Command var1, SocketAddress var2) throws IOException;

    public int getDatagramSize();

    public void setDatagramSize(int var1);

    public DatagramHeaderMarshaller getHeaderMarshaller();

    public void setHeaderMarshaller(DatagramHeaderMarshaller var1);

    public void setTargetAddress(SocketAddress var1);

    public void setReplayAddress(SocketAddress var1);

    public void setReplayBuffer(ReplayBuffer var1);

    public int getReceiveCounter();
}

