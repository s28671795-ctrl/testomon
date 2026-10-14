/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport.reliable;

import java.io.IOException;
import java.util.SortedSet;
import java.util.TreeSet;
import org.apache.activemq.command.Command;
import org.apache.activemq.command.ReplayCommand;
import org.apache.activemq.command.Response;
import org.apache.activemq.openwire.CommandIdComparator;
import org.apache.activemq.transport.FutureResponse;
import org.apache.activemq.transport.ResponseCorrelator;
import org.apache.activemq.transport.Transport;
import org.apache.activemq.transport.reliable.DefaultReplayBuffer;
import org.apache.activemq.transport.reliable.ReplayBuffer;
import org.apache.activemq.transport.reliable.ReplayStrategy;
import org.apache.activemq.transport.reliable.Replayer;
import org.apache.activemq.transport.udp.UdpTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Deprecated
public class ReliableTransport
extends ResponseCorrelator {
    private static final Logger LOG = LoggerFactory.getLogger(ReliableTransport.class);
    private ReplayStrategy replayStrategy;
    private final SortedSet<Command> commands = new TreeSet<Command>(new CommandIdComparator());
    private int expectedCounter = 1;
    private int replayBufferCommandCount = 50;
    private int requestTimeout = 2000;
    private ReplayBuffer replayBuffer;
    private Replayer replayer;
    private UdpTransport udpTransport;

    public ReliableTransport(Transport next, ReplayStrategy replayStrategy) {
        super(next);
        this.replayStrategy = replayStrategy;
    }

    public ReliableTransport(Transport next, UdpTransport udpTransport) throws IOException {
        super(next, udpTransport.getSequenceGenerator());
        this.udpTransport = udpTransport;
        this.replayer = udpTransport.createReplayer();
    }

    public void requestReplay(int fromCommandId, int toCommandId) {
        ReplayCommand replay = new ReplayCommand();
        replay.setFirstNakNumber(fromCommandId);
        replay.setLastNakNumber(toCommandId);
        try {
            this.oneway(replay);
        }
        catch (IOException e) {
            this.getTransportListener().onException(e);
        }
    }

    @Override
    public Object request(Object o) throws IOException {
        Command command = (Command)o;
        FutureResponse response = this.asyncRequest(command, null);
        Response result;
        while ((result = response.getResult(this.requestTimeout)) == null) {
            this.onMissingResponse(command, response);
        }
        return result;
    }

    @Override
    public Object request(Object o, int timeout2) throws IOException {
        Command command = (Command)o;
        FutureResponse response = this.asyncRequest(command, null);
        while (timeout2 > 0) {
            Response result;
            int time = timeout2;
            if (timeout2 > this.requestTimeout) {
                time = this.requestTimeout;
            }
            if ((result = response.getResult(time)) != null) {
                return result;
            }
            this.onMissingResponse(command, response);
            timeout2 -= time;
        }
        return response.getResult(0);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void onCommand(Object o) {
        SortedSet<Command> sortedSet;
        boolean valid;
        Command command = (Command)o;
        if (command.isWireFormatInfo()) {
            super.onCommand(command);
            return;
        }
        if (command.getDataStructureType() == 65) {
            this.replayCommands((ReplayCommand)command);
            return;
        }
        int actualCounter = command.getCommandId();
        boolean bl = valid = this.expectedCounter == actualCounter;
        if (!valid) {
            sortedSet = this.commands;
            synchronized (sortedSet) {
                int nextCounter = actualCounter;
                boolean empty = this.commands.isEmpty();
                if (!empty) {
                    Command nextAvailable = this.commands.first();
                    nextCounter = nextAvailable.getCommandId();
                }
                try {
                    boolean keep = this.replayStrategy.onDroppedPackets(this, this.expectedCounter, actualCounter, nextCounter);
                    if (keep) {
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("Received out of order command which is being buffered for later: " + command);
                        }
                        this.commands.add(command);
                    }
                }
                catch (IOException e) {
                    this.onException(e);
                }
                if (!empty) {
                    command = this.commands.first();
                    boolean bl2 = valid = this.expectedCounter == command.getCommandId();
                    if (valid) {
                        this.commands.remove(command);
                    }
                }
            }
        }
        while (valid) {
            this.replayStrategy.onReceivedPacket(this, this.expectedCounter);
            ++this.expectedCounter;
            super.onCommand(command);
            sortedSet = this.commands;
            synchronized (sortedSet) {
                boolean bl3 = valid = !this.commands.isEmpty();
                if (valid) {
                    command = this.commands.first();
                    boolean bl4 = valid = this.expectedCounter == command.getCommandId();
                    if (valid) {
                        this.commands.remove(command);
                    }
                }
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public int getBufferedCommandCount() {
        SortedSet<Command> sortedSet = this.commands;
        synchronized (sortedSet) {
            return this.commands.size();
        }
    }

    public int getExpectedCounter() {
        return this.expectedCounter;
    }

    public void setExpectedCounter(int expectedCounter) {
        this.expectedCounter = expectedCounter;
    }

    public int getRequestTimeout() {
        return this.requestTimeout;
    }

    public void setRequestTimeout(int requestTimeout) {
        this.requestTimeout = requestTimeout;
    }

    public ReplayStrategy getReplayStrategy() {
        return this.replayStrategy;
    }

    public ReplayBuffer getReplayBuffer() {
        if (this.replayBuffer == null) {
            this.replayBuffer = this.createReplayBuffer();
        }
        return this.replayBuffer;
    }

    public void setReplayBuffer(ReplayBuffer replayBuffer) {
        this.replayBuffer = replayBuffer;
    }

    public int getReplayBufferCommandCount() {
        return this.replayBufferCommandCount;
    }

    public void setReplayBufferCommandCount(int replayBufferSize) {
        this.replayBufferCommandCount = replayBufferSize;
    }

    public void setReplayStrategy(ReplayStrategy replayStrategy) {
        this.replayStrategy = replayStrategy;
    }

    public Replayer getReplayer() {
        return this.replayer;
    }

    public void setReplayer(Replayer replayer) {
        this.replayer = replayer;
    }

    @Override
    public String toString() {
        return this.next.toString();
    }

    @Override
    public void start() throws Exception {
        if (this.udpTransport != null) {
            this.udpTransport.setReplayBuffer(this.getReplayBuffer());
        }
        if (this.replayStrategy == null) {
            throw new IllegalArgumentException("Property replayStrategy not specified");
        }
        super.start();
    }

    protected void onMissingResponse(Command command, FutureResponse response) {
        LOG.debug("Still waiting for response on: " + this + " to command: " + command + " sending replay message");
        int commandId = command.getCommandId();
        this.requestReplay(commandId, commandId);
    }

    protected ReplayBuffer createReplayBuffer() {
        return new DefaultReplayBuffer(this.getReplayBufferCommandCount());
    }

    protected void replayCommands(ReplayCommand command) {
        try {
            if (this.replayer == null) {
                this.onException(new IOException("Cannot replay commands. No replayer property configured"));
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("Processing replay command: " + command);
            }
            this.getReplayBuffer().replayMessages(command.getFirstNakNumber(), command.getLastNakNumber(), this.replayer);
        }
        catch (IOException e) {
            this.onException(e);
        }
    }
}

