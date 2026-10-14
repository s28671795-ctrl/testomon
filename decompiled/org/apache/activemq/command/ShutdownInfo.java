/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.command;

import org.apache.activemq.command.BaseCommand;
import org.apache.activemq.command.Response;
import org.apache.activemq.state.CommandVisitor;

public class ShutdownInfo
extends BaseCommand {
    public static final byte DATA_STRUCTURE_TYPE = 11;

    @Override
    public byte getDataStructureType() {
        return 11;
    }

    @Override
    public Response visit(CommandVisitor visitor) throws Exception {
        return visitor.processShutdown(this);
    }

    @Override
    public boolean isShutdownInfo() {
        return true;
    }
}

