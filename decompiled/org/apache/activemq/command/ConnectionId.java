/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.command;

import org.apache.activemq.command.ConsumerId;
import org.apache.activemq.command.DataStructure;
import org.apache.activemq.command.ProducerId;
import org.apache.activemq.command.SessionId;

public class ConnectionId
implements DataStructure,
Comparable<ConnectionId> {
    public static final byte DATA_STRUCTURE_TYPE = 120;
    protected String value;

    public ConnectionId() {
    }

    public ConnectionId(String connectionId) {
        this.value = connectionId;
    }

    public ConnectionId(ConnectionId id) {
        this.value = id.getValue();
    }

    public ConnectionId(SessionId id) {
        this.value = id.getConnectionId();
    }

    public ConnectionId(ProducerId id) {
        this.value = id.getConnectionId();
    }

    public ConnectionId(ConsumerId id) {
        this.value = id.getConnectionId();
    }

    public int hashCode() {
        return this.value.hashCode();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || o.getClass() != ConnectionId.class) {
            return false;
        }
        ConnectionId id = (ConnectionId)o;
        return this.value.equals(id.value);
    }

    @Override
    public byte getDataStructureType() {
        return 120;
    }

    public String toString() {
        return this.value;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String connectionId) {
        this.value = connectionId;
    }

    @Override
    public boolean isMarshallAware() {
        return false;
    }

    @Override
    public int compareTo(ConnectionId o) {
        return this.value.compareTo(o.value);
    }
}

