/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.command;

import org.apache.activemq.command.DataStructure;
import org.apache.activemq.command.Response;

public class DataResponse
extends Response {
    public static final byte DATA_STRUCTURE_TYPE = 32;
    DataStructure data;

    public DataResponse() {
    }

    public DataResponse(DataStructure data) {
        this.data = data;
    }

    @Override
    public byte getDataStructureType() {
        return 32;
    }

    public DataStructure getData() {
        return this.data;
    }

    public void setData(DataStructure data) {
        this.data = data;
    }
}

