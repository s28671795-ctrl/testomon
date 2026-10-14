/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.databinding.stax;

import java.util.List;
import java.util.logging.Logger;
import javax.xml.stream.XMLStreamReader;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.databinding.DataReader;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.interceptor.AbstractInDatabindingInterceptor;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageContentsList;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.apache.cxf.staxutils.DepthXMLStreamReader;
import org.apache.cxf.staxutils.StaxUtils;

public class StaxDataBindingInterceptor
extends AbstractInDatabindingInterceptor {
    private static final Logger LOG = LogUtils.getL7dLogger(StaxDataBindingInterceptor.class);

    public StaxDataBindingInterceptor() {
        super("unmarshal");
    }

    @Override
    public void handleMessage(Message message) {
        if (this.isGET(message) && message.getContent(List.class) != null) {
            LOG.fine("StaxDataBindingInterceptor skipped in HTTP GET method");
            return;
        }
        DepthXMLStreamReader xmlReader = this.getXMLStreamReader(message);
        DataReader<XMLStreamReader> dr = this.getDataReader(message);
        MessageContentsList parameters = new MessageContentsList();
        Exchange exchange = message.getExchange();
        BindingOperationInfo bop = exchange.getBindingOperationInfo();
        if (!StaxUtils.toNextElement(xmlReader) && bop != null) {
            return;
        }
        if (bop == null) {
            Endpoint ep = exchange.getEndpoint();
            bop = ep.getBinding().getBindingInfo().getOperations().iterator().next();
        }
        message.getExchange().put(BindingOperationInfo.class, bop);
        if (this.isRequestor(message)) {
            parameters.put(bop.getOutput().getMessageParts().get(0), dr.read(xmlReader));
        } else {
            parameters.put(bop.getInput().getMessageParts().get(0), dr.read(xmlReader));
        }
        if (!parameters.isEmpty()) {
            message.setContent(List.class, parameters);
        }
    }
}

