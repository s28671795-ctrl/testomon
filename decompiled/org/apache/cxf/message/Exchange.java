/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.message;

import org.apache.cxf.Bus;
import org.apache.cxf.binding.Binding;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.StringMap;
import org.apache.cxf.service.Service;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.apache.cxf.transport.Conduit;
import org.apache.cxf.transport.Destination;
import org.apache.cxf.transport.Session;

public interface Exchange
extends StringMap {
    public Message getInMessage();

    public void setInMessage(Message var1);

    public Message getOutMessage();

    public void setOutMessage(Message var1);

    public Message getInFaultMessage();

    public void setInFaultMessage(Message var1);

    public Message getOutFaultMessage();

    public void setOutFaultMessage(Message var1);

    public Session getSession();

    public Destination getDestination();

    public void setDestination(Destination var1);

    public Conduit getConduit(Message var1);

    public void setConduit(Conduit var1);

    public boolean isOneWay();

    public boolean isSynchronous();

    public void setSynchronous(boolean var1);

    public void setOneWay(boolean var1);

    @Override
    public void clear();

    public Bus getBus();

    public Service getService();

    public Endpoint getEndpoint();

    public Binding getBinding();

    public BindingOperationInfo getBindingOperationInfo();
}

