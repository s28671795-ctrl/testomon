/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.activemq.broker.TransportConnectionState;
import org.apache.activemq.broker.TransportConnectionStateRegister;
import org.apache.activemq.command.ConnectionId;
import org.apache.activemq.command.ConsumerId;
import org.apache.activemq.command.ProducerId;
import org.apache.activemq.command.SessionId;

public class SingleTransportConnectionStateRegister
implements TransportConnectionStateRegister {
    private TransportConnectionState connectionState;
    private ConnectionId connectionId;

    @Override
    public TransportConnectionState registerConnectionState(ConnectionId connectionId, TransportConnectionState state) {
        TransportConnectionState rc = this.connectionState;
        this.connectionState = state;
        this.connectionId = connectionId;
        return rc;
    }

    @Override
    public synchronized TransportConnectionState unregisterConnectionState(ConnectionId connectionId) {
        TransportConnectionState rc = null;
        if (connectionId != null && this.connectionState != null && this.connectionId != null && this.connectionId.equals(connectionId)) {
            rc = this.connectionState;
            this.connectionState = null;
            connectionId = null;
        }
        return rc;
    }

    @Override
    public synchronized List<TransportConnectionState> listConnectionStates() {
        ArrayList<TransportConnectionState> rc = new ArrayList<TransportConnectionState>();
        if (this.connectionState != null) {
            rc.add(this.connectionState);
        }
        return rc;
    }

    @Override
    public synchronized TransportConnectionState lookupConnectionState(String connectionId) {
        TransportConnectionState cs = this.connectionState;
        if (cs == null) {
            throw new IllegalStateException("Cannot lookup a connectionId for a connection that had not been registered: " + connectionId);
        }
        return cs;
    }

    @Override
    public synchronized TransportConnectionState lookupConnectionState(ConsumerId id) {
        TransportConnectionState cs = this.connectionState;
        if (cs == null) {
            throw new IllegalStateException("Cannot lookup a consumer from a connection that had not been registered: " + id.getParentId().getParentId());
        }
        return cs;
    }

    @Override
    public synchronized TransportConnectionState lookupConnectionState(ProducerId id) {
        TransportConnectionState cs = this.connectionState;
        if (cs == null) {
            throw new IllegalStateException("Cannot lookup a producer from a connection that had not been registered: " + id.getParentId().getParentId());
        }
        return cs;
    }

    @Override
    public synchronized TransportConnectionState lookupConnectionState(SessionId id) {
        TransportConnectionState cs = this.connectionState;
        if (cs == null) {
            throw new IllegalStateException("Cannot lookup a session from a connection that had not been registered: " + id.getParentId());
        }
        return cs;
    }

    @Override
    public synchronized TransportConnectionState lookupConnectionState(ConnectionId connectionId) {
        TransportConnectionState cs = this.connectionState;
        return cs;
    }

    @Override
    public synchronized boolean doesHandleMultipleConnectionStates() {
        return false;
    }

    @Override
    public synchronized boolean isEmpty() {
        return this.connectionState == null;
    }

    @Override
    public void intialize(TransportConnectionStateRegister other) {
        if (other.isEmpty()) {
            this.clear();
        } else {
            Map<ConnectionId, TransportConnectionState> map = other.mapStates();
            Iterator<Map.Entry<ConnectionId, TransportConnectionState>> i = map.entrySet().iterator();
            Map.Entry<ConnectionId, TransportConnectionState> entry = i.next();
            this.connectionId = entry.getKey();
            this.connectionState = entry.getValue();
        }
    }

    @Override
    public Map<ConnectionId, TransportConnectionState> mapStates() {
        HashMap<ConnectionId, TransportConnectionState> map = new HashMap<ConnectionId, TransportConnectionState>();
        if (!this.isEmpty()) {
            map.put(this.connectionId, this.connectionState);
        }
        return map;
    }

    @Override
    public void clear() {
        this.connectionState = null;
        this.connectionId = null;
    }
}

