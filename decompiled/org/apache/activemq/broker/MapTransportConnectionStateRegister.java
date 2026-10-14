/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.activemq.broker.TransportConnectionState;
import org.apache.activemq.broker.TransportConnectionStateRegister;
import org.apache.activemq.command.ConnectionId;
import org.apache.activemq.command.ConsumerId;
import org.apache.activemq.command.ProducerId;
import org.apache.activemq.command.SessionId;

public class MapTransportConnectionStateRegister
implements TransportConnectionStateRegister {
    private Map<ConnectionId, TransportConnectionState> connectionStates = new ConcurrentHashMap<ConnectionId, TransportConnectionState>();

    @Override
    public TransportConnectionState registerConnectionState(ConnectionId connectionId, TransportConnectionState state) {
        TransportConnectionState rc = this.connectionStates.put(connectionId, state);
        return rc;
    }

    @Override
    public TransportConnectionState unregisterConnectionState(ConnectionId connectionId) {
        TransportConnectionState rc = this.connectionStates.remove(connectionId);
        if (rc.getReferenceCounter().get() > 1) {
            rc.decrementReference();
            this.connectionStates.put(connectionId, rc);
        }
        return rc;
    }

    @Override
    public List<TransportConnectionState> listConnectionStates() {
        ArrayList<TransportConnectionState> rc = new ArrayList<TransportConnectionState>();
        rc.addAll(this.connectionStates.values());
        return rc;
    }

    @Override
    public TransportConnectionState lookupConnectionState(String connectionId) {
        return this.connectionStates.get(new ConnectionId(connectionId));
    }

    @Override
    public TransportConnectionState lookupConnectionState(ConsumerId id) {
        TransportConnectionState cs = this.lookupConnectionState(id.getConnectionId());
        if (cs == null) {
            throw new IllegalStateException("Cannot lookup a consumer from a connection that had not been registered: " + id.getParentId().getParentId());
        }
        return cs;
    }

    @Override
    public TransportConnectionState lookupConnectionState(ProducerId id) {
        TransportConnectionState cs = this.lookupConnectionState(id.getConnectionId());
        if (cs == null) {
            throw new IllegalStateException("Cannot lookup a producer from a connection that had not been registered: " + id.getParentId().getParentId());
        }
        return cs;
    }

    @Override
    public TransportConnectionState lookupConnectionState(SessionId id) {
        TransportConnectionState cs = this.lookupConnectionState(id.getConnectionId());
        if (cs == null) {
            throw new IllegalStateException("Cannot lookup a session from a connection that had not been registered: " + id.getParentId());
        }
        return cs;
    }

    @Override
    public TransportConnectionState lookupConnectionState(ConnectionId connectionId) {
        TransportConnectionState cs = this.connectionStates.get(connectionId);
        if (cs == null) {
            throw new IllegalStateException("Cannot lookup a connection that had not been registered: " + connectionId);
        }
        return cs;
    }

    @Override
    public boolean doesHandleMultipleConnectionStates() {
        return true;
    }

    @Override
    public boolean isEmpty() {
        return this.connectionStates.isEmpty();
    }

    @Override
    public void clear() {
        this.connectionStates.clear();
    }

    @Override
    public void intialize(TransportConnectionStateRegister other) {
        this.connectionStates.clear();
        this.connectionStates.putAll(other.mapStates());
    }

    @Override
    public Map<ConnectionId, TransportConnectionState> mapStates() {
        HashMap<ConnectionId, TransportConnectionState> map = new HashMap<ConnectionId, TransportConnectionState>(this.connectionStates);
        return map;
    }
}

