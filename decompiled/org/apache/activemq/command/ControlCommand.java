/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.command;

import org.apache.activemq.command.BaseCommand;
import org.apache.activemq.command.Response;
import org.apache.activemq.state.CommandVisitor;

public class ControlCommand
extends BaseCommand {
    public static final byte DATA_STRUCTURE_TYPE = 14;
    private String command;

    @Override
    public byte getDataStructureType() {
        return 14;
    }

    public String getCommand() {
        return this.command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    @Override
    public Response visit(CommandVisitor visitor) throws Exception {
        return visitor.processControlCommand(this);
    }
}

