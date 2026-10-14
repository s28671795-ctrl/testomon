/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.command;

import org.apache.activemq.command.DataStructure;
import org.apache.activemq.command.SessionId;

public class ProducerId
implements DataStructure {
    public static final byte DATA_STRUCTURE_TYPE = 123;
    protected String connectionId;
    protected long sessionId;
    protected long value;
    protected transient int hashCode;
    protected transient String key;
    protected transient SessionId parentId;

    public ProducerId() {
    }

    public ProducerId(SessionId sessionId, long producerId) {
        this.connectionId = sessionId.getConnectionId();
        this.sessionId = sessionId.getValue();
        this.value = producerId;
    }

    public ProducerId(ProducerId id) {
        this.connectionId = id.getConnectionId();
        this.sessionId = id.getSessionId();
        this.value = id.getValue();
    }

    public ProducerId(String producerKey) {
        int p = producerKey.lastIndexOf(":");
        if (p >= 0) {
            this.value = Long.parseLong(producerKey.substring(p + 1));
            producerKey = producerKey.substring(0, p);
        }
        this.setProducerSessionKey(producerKey);
    }

    public SessionId getParentId() {
        if (this.parentId == null) {
            this.parentId = new SessionId(this);
        }
        return this.parentId;
    }

    public int hashCode() {
        if (this.hashCode == 0) {
            this.hashCode = this.connectionId.hashCode() ^ (int)this.sessionId ^ (int)this.value;
        }
        return this.hashCode;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || o.getClass() != ProducerId.class) {
            return false;
        }
        ProducerId id = (ProducerId)o;
        return this.sessionId == id.sessionId && this.value == id.value && this.connectionId.equals(id.connectionId);
    }

    private void setProducerSessionKey(String sessionKey) {
        int p = sessionKey.lastIndexOf(":");
        if (p >= 0) {
            this.sessionId = Long.parseLong(sessionKey.substring(p + 1));
            sessionKey = sessionKey.substring(0, p);
        }
        this.connectionId = sessionKey;
    }

    public String toString() {
        if (this.key == null) {
            this.key = this.connectionId + ":" + this.sessionId + ":" + this.value;
        }
        return this.key;
    }

    @Override
    public byte getDataStructureType() {
        return 123;
    }

    public String getConnectionId() {
        return this.connectionId;
    }

    public void setConnectionId(String connectionId) {
        this.connectionId = connectionId;
    }

    public long getValue() {
        return this.value;
    }

    public void setValue(long producerId) {
        this.value = producerId;
    }

    public long getSessionId() {
        return this.sessionId;
    }

    public void setSessionId(long sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public boolean isMarshallAware() {
        return false;
    }
}

