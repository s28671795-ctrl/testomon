/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.command;

import org.apache.activemq.command.Command;
import org.apache.activemq.command.PartialCommand;
import org.apache.activemq.command.Response;
import org.apache.activemq.state.CommandVisitor;

public class LastPartialCommand
extends PartialCommand {
    public static final byte DATA_STRUCTURE_TYPE = 61;

    @Override
    public byte getDataStructureType() {
        return 61;
    }

    @Override
    public Response visit(CommandVisitor visitor) throws Exception {
        throw new IllegalStateException("The transport layer should filter out LastPartialCommand instances but received: " + this);
    }

    public void configure(Command completeCommand) {
        completeCommand.setFrom(this.getFrom());
    }
}

