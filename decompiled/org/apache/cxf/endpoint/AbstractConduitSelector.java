/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.endpoint;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Logger;
import org.apache.cxf.BusException;
import org.apache.cxf.common.util.PropertyUtils;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.endpoint.ConduitSelector;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageUtils;
import org.apache.cxf.service.model.EndpointInfo;
import org.apache.cxf.transport.Conduit;
import org.apache.cxf.transport.ConduitInitiator;
import org.apache.cxf.transport.ConduitInitiatorManager;
import org.apache.cxf.transport.MessageObserver;
import org.apache.cxf.ws.addressing.AttributedURIType;
import org.apache.cxf.ws.addressing.EndpointReferenceType;

public abstract class AbstractConduitSelector
implements ConduitSelector,
Closeable {
    public static final String CONDUIT_COMPARE_FULL_URL = "org.apache.cxf.ConduitSelector.compareFullUrl";
    protected static final String KEEP_CONDUIT_ALIVE = "KeepConduitAlive";
    protected List<Conduit> conduits = new CopyOnWriteArrayList<Conduit>();
    protected Endpoint endpoint;

    public AbstractConduitSelector() {
    }

    public AbstractConduitSelector(Conduit c) {
        if (c != null) {
            this.conduits.add(c);
        }
    }

    @Override
    public void close() {
        for (Conduit c : this.conduits) {
            c.close();
        }
        this.conduits.clear();
    }

    protected void removeConduit(Conduit conduit) {
        if (conduit != null) {
            conduit.close();
            this.conduits.remove(conduit);
        }
    }

    protected Conduit getSelectedConduit(Message message) {
        Conduit c = this.findCompatibleConduit(message);
        if (c == null) {
            Exchange exchange = message.getExchange();
            EndpointInfo ei = this.endpoint.getEndpointInfo();
            String transportID = ei.getTransportId();
            try {
                ConduitInitiatorManager conduitInitiatorMgr = exchange.getBus().getExtension(ConduitInitiatorManager.class);
                if (conduitInitiatorMgr != null) {
                    ConduitInitiator conduitInitiator = conduitInitiatorMgr.getConduitInitiator(transportID);
                    if (conduitInitiator != null) {
                        c = this.createConduit(message, exchange, conduitInitiator);
                    } else {
                        this.getLogger().warning("ConduitInitiator not found: " + ei.getAddress());
                    }
                } else {
                    this.getLogger().warning("ConduitInitiatorManager not found");
                }
            }
            catch (IOException | BusException ex) {
                throw new Fault(ex);
            }
        }
        if (c != null && c.getTarget() != null && c.getTarget().getAddress() != null) {
            this.replaceEndpointAddressPropertyIfNeeded(message, c.getTarget().getAddress().getValue(), c);
        }
        message.resetContextCache();
        message.put(Conduit.class, c);
        return c;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected Conduit createConduit(Message message, Exchange exchange, ConduitInitiator conduitInitiator) throws IOException {
        Conduit c;
        Endpoint endpoint = this.endpoint;
        synchronized (endpoint) {
            if (!this.conduits.isEmpty() && (c = this.findCompatibleConduit(message)) != null) {
                return c;
            }
            EndpointInfo ei = this.endpoint.getEndpointInfo();
            String add = (String)message.get(Message.ENDPOINT_ADDRESS);
            String basePath = (String)message.get(Message.BASE_PATH);
            if (StringUtils.isEmpty(add) || add.equals(ei.getAddress())) {
                c = conduitInitiator.getConduit(ei, exchange.getBus());
                this.replaceEndpointAddressPropertyIfNeeded(message, add, c);
            } else {
                EndpointReferenceType epr = new EndpointReferenceType();
                AttributedURIType ad = new AttributedURIType();
                ad.setValue(StringUtils.isEmpty(basePath) ? add : basePath);
                epr.setAddress(ad);
                c = conduitInitiator.getConduit(ei, epr, exchange.getBus());
            }
            MessageObserver observer = exchange.get(MessageObserver.class);
            if (observer != null) {
                c.setMessageObserver(observer);
            } else {
                this.getLogger().warning("MessageObserver not found");
            }
            this.conduits.add(c);
        }
        return c;
    }

    protected boolean replaceEndpointAddressPropertyIfNeeded(Message message, String endpointAddress, Conduit cond) {
        return false;
    }

    @Override
    public Endpoint getEndpoint() {
        return this.endpoint;
    }

    @Override
    public void setEndpoint(Endpoint ep) {
        this.endpoint = ep;
    }

    @Override
    public void complete(Exchange exchange) {
        if (PropertyUtils.isTrue(exchange.get(KEEP_CONDUIT_ALIVE))) {
            return;
        }
        try {
            if (exchange.getInMessage() != null) {
                Conduit c = exchange.getOutMessage().get(Conduit.class);
                if (c == null) {
                    this.getSelectedConduit(exchange.getInMessage()).close(exchange.getInMessage());
                } else {
                    c.close(exchange.getInMessage());
                }
            }
        }
        catch (IOException iOException) {
            // empty catch block
        }
    }

    protected abstract Logger getLogger();

    protected Conduit findCompatibleConduit(Message message) {
        Conduit c = message.get(Conduit.class);
        if (c == null && message.getExchange() != null && message.getExchange().getOutMessage() != null && message.getExchange().getOutMessage() != message) {
            c = message.getExchange().getOutMessage().get(Conduit.class);
        }
        if (c != null) {
            return c;
        }
        ContextualBooleanGetter cbg = new ContextualBooleanGetter(message);
        for (Conduit c2 : this.conduits) {
            if (c2.getTarget() == null || c2.getTarget().getAddress() == null || c2.getTarget().getAddress().getValue() == null) continue;
            String conduitAddress = c2.getTarget().getAddress().getValue();
            EndpointInfo ei = this.endpoint.getEndpointInfo();
            String actualAddress = ei.getAddress();
            String messageAddress = (String)message.get(Message.ENDPOINT_ADDRESS);
            if (messageAddress != null) {
                actualAddress = messageAddress;
            }
            if (!this.matchAddresses(conduitAddress, actualAddress, cbg)) continue;
            return c2;
        }
        for (Conduit c2 : this.conduits) {
            if (c2.getTarget() != null && c2.getTarget().getAddress() != null && c2.getTarget().getAddress().getValue() != null) continue;
            return c2;
        }
        return null;
    }

    private boolean matchAddresses(String conduitAddress, String actualAddress, ContextualBooleanGetter cbg) {
        if (conduitAddress.length() == actualAddress.length()) {
            if (conduitAddress.equalsIgnoreCase(actualAddress)) {
                return true;
            }
            return !cbg.isFullComparison() && this.matchAddressSubstrings(conduitAddress, actualAddress);
        }
        return !cbg.isFullComparison() && this.matchAddressSubstrings(conduitAddress, actualAddress);
    }

    private boolean matchAddressSubstrings(String conduitAddress, String actualAddress) {
        int idx = conduitAddress.indexOf(58);
        if (idx == actualAddress.indexOf(58)) {
            if (idx <= 0) {
                return true;
            }
            return conduitAddress.substring(0, idx).equalsIgnoreCase(actualAddress.substring(0, idx));
        }
        return false;
    }

    private static final class ContextualBooleanGetter {
        private Boolean value;
        private final Message message;

        ContextualBooleanGetter(Message message) {
            this.message = message;
        }

        public boolean isFullComparison() {
            if (this.value == null) {
                this.value = MessageUtils.getContextualBoolean(this.message, AbstractConduitSelector.CONDUIT_COMPARE_FULL_URL, false);
            }
            return this.value;
        }
    }
}

