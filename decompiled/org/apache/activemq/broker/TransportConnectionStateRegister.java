/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker;

import java.util.List;
import java.util.Map;
import org.apache.activemq.broker.TransportConnectionState;
import org.apache.activemq.command.ConnectionId;
import org.apache.activemq.command.ConsumerId;
import org.apache.activemq.command.ProducerId;
import org.apache.activemq.command.SessionId;

public interface TransportConnectionStateRegister {
    public TransportConnectionState registerConnectionState(ConnectionId var1, TransportConnectionState var2);

    public TransportConnectionState unregisterConnectionState(ConnectionId var1);

    public List<TransportConnectionState> listConnectionStates();

    public Map<ConnectionId, TransportConnectionState> mapStates();

    public TransportConnectionState lookupConnectionState(String var1);

    public TransportConnectionState lookupConnectionState(ConsumerId var1);

    public TransportConnectionState lookupConnectionState(ProducerId var1);

    public TransportConnectionState lookupConnectionState(SessionId var1);

    public TransportConnectionState lookupConnectionState(ConnectionId var1);

    public boolean isEmpty();

    public boolean doesHandleMultipleConnectionStates();

    public void intialize(TransportConnectionStateRegister var1);

    public void clear();
}

