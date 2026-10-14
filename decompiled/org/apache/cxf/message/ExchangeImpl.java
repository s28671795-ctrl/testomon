/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.message;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.cxf.Bus;
import org.apache.cxf.binding.Binding;
import org.apache.cxf.endpoint.ConduitSelector;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.endpoint.PreexistingConduitSelector;
import org.apache.cxf.message.AbstractWrappedMessage;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageImpl;
import org.apache.cxf.service.Service;
import org.apache.cxf.service.model.BindingInfo;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.apache.cxf.service.model.InterfaceInfo;
import org.apache.cxf.service.model.OperationInfo;
import org.apache.cxf.service.model.ServiceInfo;
import org.apache.cxf.transport.Conduit;
import org.apache.cxf.transport.Destination;
import org.apache.cxf.transport.Session;

public class ExchangeImpl
extends ConcurrentHashMap<String, Object>
implements Exchange {
    private static final long serialVersionUID = -3112077559217623594L;
    private Destination destination;
    private boolean oneWay;
    private boolean synchronous = true;
    private Message inMessage;
    private Message outMessage;
    private Message inFaultMessage;
    private Message outFaultMessage;
    private Session session;
    private Bus bus;
    private Endpoint endpoint;
    private Service service;
    private Binding binding;
    private BindingOperationInfo bindingOp;

    public ExchangeImpl() {
    }

    public ExchangeImpl(ExchangeImpl ex) {
        super(ex);
        this.destination = ex.destination;
        this.oneWay = ex.oneWay;
        this.synchronous = ex.synchronous;
        this.inMessage = ex.inMessage;
        this.outMessage = ex.outMessage;
        this.inFaultMessage = ex.inFaultMessage;
        this.outFaultMessage = ex.outFaultMessage;
        this.session = ex.session;
        this.bus = ex.bus;
        this.endpoint = ex.endpoint;
        this.service = ex.service;
        this.binding = ex.binding;
        this.bindingOp = ex.bindingOp;
    }

    private void resetContextCaches() {
        if (this.inMessage != null) {
            this.inMessage.resetContextCache();
        }
        if (this.outMessage != null) {
            this.outMessage.resetContextCache();
        }
        if (this.inFaultMessage != null) {
            this.inFaultMessage.resetContextCache();
        }
        if (this.outFaultMessage != null) {
            this.outFaultMessage.resetContextCache();
        }
    }

    @Override
    public <T> T get(Class<T> key) {
        T t2 = key.cast(this.get(key.getName()));
        if (t2 == null) {
            if (key == Bus.class) {
                t2 = key.cast(this.bus);
            } else if (key == OperationInfo.class && this.bindingOp != null) {
                t2 = key.cast(this.bindingOp.getOperationInfo());
            } else if (key == BindingOperationInfo.class) {
                t2 = key.cast(this.bindingOp);
            } else if (key == Endpoint.class) {
                t2 = key.cast(this.endpoint);
            } else if (key == Service.class) {
                t2 = key.cast(this.service);
            } else if (key == Binding.class) {
                t2 = key.cast(this.binding);
            } else if (key == BindingInfo.class && this.binding != null) {
                t2 = key.cast(this.binding.getBindingInfo());
            } else if (key == InterfaceInfo.class && this.endpoint != null) {
                t2 = key.cast(this.endpoint.getEndpointInfo().getService().getInterface());
            } else if (key == ServiceInfo.class && this.endpoint != null) {
                t2 = key.cast(this.endpoint.getEndpointInfo().getService());
            }
        }
        return t2;
    }

    @Override
    public void putAll(Map<? extends String, ?> m4) {
        for (Map.Entry<String, ?> e : m4.entrySet()) {
            if (e.getValue() == null) continue;
            super.put(e.getKey(), e.getValue());
        }
    }

    @Override
    public <T> void put(Class<T> key, T value) {
        if (value == null) {
            super.remove(key);
        } else if (key == Bus.class) {
            this.resetContextCaches();
            this.bus = (Bus)value;
        } else if (key == Endpoint.class) {
            this.resetContextCaches();
            this.endpoint = (Endpoint)value;
        } else if (key == Service.class) {
            this.resetContextCaches();
            this.service = (Service)value;
        } else if (key == BindingOperationInfo.class) {
            this.bindingOp = (BindingOperationInfo)value;
        } else if (key == Binding.class) {
            this.binding = (Binding)value;
        } else {
            super.put(key.getName(), value);
        }
    }

    @Override
    public Object put(String key, Object value) {
        this.setMessageContextProperty(this.inMessage, key, value);
        this.setMessageContextProperty(this.outMessage, key, value);
        this.setMessageContextProperty(this.inFaultMessage, key, value);
        this.setMessageContextProperty(this.outFaultMessage, key, value);
        if (value == null) {
            return super.remove(key);
        }
        return super.put(key, value);
    }

    @Override
    public <T> T remove(Class<T> key) {
        return key.cast(super.remove(key.getName()));
    }

    private void setMessageContextProperty(Message m4, String key, Object value) {
        if (m4 == null) {
            return;
        }
        if (m4 instanceof MessageImpl) {
            ((MessageImpl)m4).setContextualProperty(key, value);
        } else if (m4 instanceof AbstractWrappedMessage) {
            ((AbstractWrappedMessage)m4).setContextualProperty(key, value);
        } else {
            m4.resetContextCache();
        }
    }

    @Override
    public Destination getDestination() {
        return this.destination;
    }

    @Override
    public Message getInMessage() {
        return this.inMessage;
    }

    @Override
    public Conduit getConduit(Message message) {
        return this.get(ConduitSelector.class) != null ? this.get(ConduitSelector.class).selectConduit(message) : null;
    }

    @Override
    public Message getOutMessage() {
        return this.outMessage;
    }

    @Override
    public Message getInFaultMessage() {
        return this.inFaultMessage;
    }

    @Override
    public void setInFaultMessage(Message m4) {
        this.inFaultMessage = m4;
        if (null != m4) {
            m4.setExchange(this);
        }
    }

    @Override
    public Message getOutFaultMessage() {
        return this.outFaultMessage;
    }

    @Override
    public void setOutFaultMessage(Message m4) {
        this.outFaultMessage = m4;
        if (null != m4) {
            m4.setExchange(this);
        }
    }

    @Override
    public void setDestination(Destination d) {
        this.destination = d;
    }

    @Override
    public void setInMessage(Message m4) {
        this.inMessage = m4;
        if (null != m4) {
            m4.setExchange(this);
        }
    }

    @Override
    public void setConduit(Conduit c) {
        this.put((Class)ConduitSelector.class, (Object)new PreexistingConduitSelector(c, this.getEndpoint()));
    }

    @Override
    public void setOutMessage(Message m4) {
        this.outMessage = m4;
        if (null != m4) {
            m4.setExchange(this);
        }
    }

    @Override
    public boolean isOneWay() {
        return this.oneWay;
    }

    @Override
    public void setOneWay(boolean b) {
        this.oneWay = b;
    }

    @Override
    public boolean isSynchronous() {
        return this.synchronous;
    }

    @Override
    public void setSynchronous(boolean b) {
        this.synchronous = b;
    }

    @Override
    public Session getSession() {
        return this.session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    @Override
    public void clear() {
        super.clear();
        this.resetContextCaches();
        this.destination = null;
        this.oneWay = false;
        this.inMessage = null;
        this.outMessage = null;
        this.inFaultMessage = null;
        this.outFaultMessage = null;
        this.session = null;
        this.bus = null;
    }

    @Override
    public Bus getBus() {
        return this.bus;
    }

    @Override
    public Endpoint getEndpoint() {
        return this.endpoint;
    }

    @Override
    public Service getService() {
        return this.service;
    }

    @Override
    public Binding getBinding() {
        return this.binding;
    }

    @Override
    public BindingOperationInfo getBindingOperationInfo() {
        return this.bindingOp;
    }
}

