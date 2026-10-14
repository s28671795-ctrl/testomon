/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.command;

import org.apache.activemq.command.Response;

public class IntegerResponse
extends Response {
    public static final byte DATA_STRUCTURE_TYPE = 34;
    int result;

    public IntegerResponse() {
    }

    public IntegerResponse(int result) {
        this.result = result;
    }

    @Override
    public byte getDataStructureType() {
        return 34;
    }

    public int getResult() {
        return this.result;
    }

    public void setResult(int result) {
        this.result = result;
    }
}

