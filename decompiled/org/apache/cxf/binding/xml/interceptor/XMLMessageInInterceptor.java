/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.binding.xml.interceptor;

import java.util.List;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import org.apache.cxf.bindings.xformat.XMLBindingMessageFormat;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.interceptor.AbstractInDatabindingInterceptor;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.Message;
import org.apache.cxf.service.model.BindingInfo;
import org.apache.cxf.service.model.BindingMessageInfo;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.apache.cxf.service.model.MessagePartInfo;
import org.apache.cxf.staxutils.DepthXMLStreamReader;
import org.apache.cxf.staxutils.StaxUtils;
import org.apache.cxf.wsdl.interceptors.DocLiteralInInterceptor;

public class XMLMessageInInterceptor
extends AbstractInDatabindingInterceptor {
    private static final Logger LOG = LogUtils.getL7dLogger(XMLMessageInInterceptor.class);

    public XMLMessageInInterceptor() {
        this("unmarshal");
    }

    public XMLMessageInInterceptor(String phase) {
        super(phase);
        this.addBefore(DocLiteralInInterceptor.class.getName());
    }

    @Override
    public void handleMessage(Message message) throws Fault {
        if (this.isGET(message)) {
            LOG.fine("XMLMessageInInterceptor skipped in HTTP GET method");
            return;
        }
        Endpoint ep = message.getExchange().getEndpoint();
        XMLStreamReader xsr = message.getContent(XMLStreamReader.class);
        if (xsr == null) {
            return;
        }
        DepthXMLStreamReader reader = new DepthXMLStreamReader(xsr);
        if (!StaxUtils.toNextElement(reader)) {
            throw new Fault(new org.apache.cxf.common.i18n.Message("NO_OPERATION_ELEMENT", LOG, new Object[0]));
        }
        Exchange ex = message.getExchange();
        QName startQName = reader.getName();
        if (startQName.getLocalPart().equals("XMLFault")) {
            message.getInterceptorChain().abort();
            if (ep.getInFaultObserver() != null) {
                ep.getInFaultObserver().onMessage(message);
                return;
            }
        }
        BindingOperationInfo boi = ex.getBindingOperationInfo();
        boolean isRequestor = this.isRequestor(message);
        if (boi == null) {
            BindingInfo service2 = ep.getEndpointInfo().getBinding();
            boi = this.getBindingOperationInfo(isRequestor, startQName, service2, xsr);
            if (boi != null) {
                ex.put(BindingOperationInfo.class, boi);
                ex.setOneWay(boi.getOperationInfo().isOneWay());
            }
        } else {
            BindingMessageInfo bmi;
            BindingMessageInfo bindingMessageInfo = bmi = isRequestor ? boi.getOutput() : boi.getInput();
            if (this.hasRootNode(bmi, startQName)) {
                try {
                    xsr.nextTag();
                }
                catch (XMLStreamException xse) {
                    throw new Fault(new org.apache.cxf.common.i18n.Message("STAX_READ_EXC", LOG, new Object[0]));
                }
            }
        }
    }

    private BindingOperationInfo getBindingOperationInfo(boolean isRequestor, QName startQName, BindingInfo bi, XMLStreamReader xsr) {
        for (BindingOperationInfo boi : bi.getOperations()) {
            MessagePartInfo p;
            BindingMessageInfo bmi = !isRequestor ? boi.getInput() : boi.getOutput();
            if (this.hasRootNode(bmi, startQName)) {
                try {
                    xsr.nextTag();
                }
                catch (XMLStreamException xse) {
                    throw new Fault(new org.apache.cxf.common.i18n.Message("STAX_READ_EXC", LOG, new Object[0]));
                }
                return boi;
            }
            List<MessagePartInfo> bodyParts = bmi.getMessageParts();
            if (bodyParts.size() != 1 || !(p = (MessagePartInfo)bodyParts.iterator().next()).getConcreteName().equals(startQName)) continue;
            return boi;
        }
        for (BindingOperationInfo boi : bi.getOperations()) {
            if (!startQName.equals(boi.getName())) continue;
            try {
                xsr.nextTag();
            }
            catch (XMLStreamException xse) {
                throw new Fault(new org.apache.cxf.common.i18n.Message("STAX_READ_EXC", LOG, new Object[0]));
            }
            return boi;
        }
        return null;
    }

    private boolean hasRootNode(BindingMessageInfo bmi, QName elName) {
        XMLBindingMessageFormat xmf = bmi.getExtensor(XMLBindingMessageFormat.class);
        return bmi.getMessageParts().size() != 1 && xmf != null && xmf.getRootNode().equals(elName);
    }
}

