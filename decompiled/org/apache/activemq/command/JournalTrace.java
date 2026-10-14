/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.command;

import org.apache.activemq.command.DataStructure;
import org.apache.activemq.util.IntrospectionSupport;

public class JournalTrace
implements DataStructure {
    public static final byte DATA_STRUCTURE_TYPE = 53;
    private String message;

    public JournalTrace() {
    }

    public JournalTrace(String message) {
        this.message = message;
    }

    @Override
    public byte getDataStructureType() {
        return 53;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean isMarshallAware() {
        return false;
    }

    public String toString() {
        return IntrospectionSupport.toString(this, JournalTrace.class);
    }
}

