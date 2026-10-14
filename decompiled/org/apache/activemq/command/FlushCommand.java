/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.command;

import org.apache.activemq.command.BaseCommand;
import org.apache.activemq.command.Command;
import org.apache.activemq.command.Response;
import org.apache.activemq.state.CommandVisitor;

public class FlushCommand
extends BaseCommand {
    public static final byte DATA_STRUCTURE_TYPE = 15;
    public static final Command COMMAND = new FlushCommand();

    @Override
    public byte getDataStructureType() {
        return 15;
    }

    @Override
    public Response visit(CommandVisitor visitor) throws Exception {
        return visitor.processFlush(this);
    }
}

