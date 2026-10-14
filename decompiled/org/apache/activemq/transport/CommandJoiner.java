/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import org.apache.activemq.command.Command;
import org.apache.activemq.command.LastPartialCommand;
import org.apache.activemq.command.PartialCommand;
import org.apache.activemq.openwire.OpenWireFormat;
import org.apache.activemq.transport.Transport;
import org.apache.activemq.transport.TransportFilter;
import org.apache.activemq.util.ByteArrayInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandJoiner
extends TransportFilter {
    private static final Logger LOG = LoggerFactory.getLogger(CommandJoiner.class);
    private ByteArrayOutputStream out = new ByteArrayOutputStream();
    private OpenWireFormat wireFormat;

    public CommandJoiner(Transport next, OpenWireFormat wireFormat) {
        super(next);
        this.wireFormat = wireFormat;
    }

    @Override
    public void onCommand(Object o) {
        Command command = (Command)o;
        byte type = command.getDataStructureType();
        if (type == 60 || type == 61) {
            PartialCommand header = (PartialCommand)command;
            byte[] partialData = header.getData();
            try {
                this.out.write(partialData);
            }
            catch (IOException e) {
                this.getTransportListener().onException(e);
            }
            if (type == 61) {
                try {
                    byte[] fullData = this.out.toByteArray();
                    this.out.reset();
                    DataInputStream dataIn = new DataInputStream(new ByteArrayInputStream(fullData));
                    Command completeCommand = (Command)this.wireFormat.unmarshal(dataIn);
                    LastPartialCommand lastCommand = (LastPartialCommand)command;
                    lastCommand.configure(completeCommand);
                    this.getTransportListener().onCommand(completeCommand);
                }
                catch (IOException e) {
                    LOG.warn("Failed to unmarshal partial command: " + command);
                    this.getTransportListener().onException(e);
                }
            }
        } else {
            this.getTransportListener().onCommand(command);
        }
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        this.out = null;
    }

    @Override
    public String toString() {
        return this.next.toString();
    }
}

