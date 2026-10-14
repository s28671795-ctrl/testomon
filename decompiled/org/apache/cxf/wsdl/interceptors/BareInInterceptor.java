/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.wsdl.interceptors;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamReader;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.databinding.DataReader;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.interceptor.AbstractInDatabindingInterceptor;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageContentsList;
import org.apache.cxf.service.model.BindingMessageInfo;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.apache.cxf.service.model.MessageInfo;
import org.apache.cxf.service.model.MessagePartInfo;
import org.apache.cxf.service.model.OperationInfo;
import org.apache.cxf.service.model.ServiceInfo;
import org.apache.cxf.staxutils.DepthXMLStreamReader;
import org.apache.cxf.staxutils.StaxUtils;

public class BareInInterceptor
extends AbstractInDatabindingInterceptor {
    private static final Logger LOG = LogUtils.getL7dLogger(BareInInterceptor.class);
    private static Set<String> filter = new HashSet<String>();

    public BareInInterceptor() {
        super("unmarshal");
    }

    @Override
    public void handleMessage(Message message) {
        if (this.isGET(message) && message.getContent(List.class) != null) {
            LOG.fine("BareInInterceptor skipped in HTTP GET method");
            return;
        }
        DepthXMLStreamReader xmlReader = this.getXMLStreamReader(message);
        Exchange exchange = message.getExchange();
        DataReader<XMLStreamReader> dr = this.getDataReader(message);
        MessageContentsList parameters = new MessageContentsList();
        Endpoint ep = exchange.getEndpoint();
        BindingOperationInfo bop = exchange.getBindingOperationInfo();
        ServiceInfo si = ep.getEndpointInfo().getService();
        BindingMessageInfo msgInfo = null;
        boolean client = this.isRequestor(message);
        ArrayList<OperationInfo> ops = null;
        if (bop == null) {
            ops = new ArrayList<OperationInfo>();
            ops.addAll(si.getInterface().getOperations());
            if (xmlReader.getEventType() == 2 && !client) {
                for (OperationInfo op : ops) {
                    MessageInfo bmsg = op.getInput();
                    if (bmsg.getMessagePartsNumber() != 0) continue;
                    BindingOperationInfo boi = ep.getEndpointInfo().getBinding().getOperation(op);
                    exchange.put(BindingOperationInfo.class, boi);
                    exchange.setOneWay(op.isOneWay());
                }
            }
        } else {
            this.getMessageInfo(message, bop);
            msgInfo = client ? bop.getOutput() : bop.getInput();
        }
        int paramNum = 0;
        while (StaxUtils.toNextElement(xmlReader)) {
            MessagePartInfo p;
            QName elName = xmlReader.getName();
            Object o = null;
            if (msgInfo != null && msgInfo.getMessageParts() != null) {
                assert (msgInfo.getMessageParts().size() > paramNum);
                p = msgInfo.getMessageParts().get(paramNum);
            } else {
                p = this.findMessagePart(exchange, ops, elName, client, paramNum, message);
            }
            if (p == null) {
                throw new Fault(new org.apache.cxf.common.i18n.Message("NO_PART_FOUND", LOG, elName), Fault.FAULT_CODE_CLIENT);
            }
            try {
                o = dr.read(p, xmlReader);
            }
            catch (Fault fault) {
                if (!this.isRequestor(message)) {
                    fault.setFaultCode(Fault.FAULT_CODE_CLIENT);
                }
                throw fault;
            }
            if (o != null) {
                parameters.put(p, o);
            }
            ++paramNum;
        }
        if (!parameters.isEmpty()) {
            message.setContent(List.class, parameters);
        }
    }

    static {
        filter.add("void");
        filter.add("javax.activation.DataHandler");
    }
}

