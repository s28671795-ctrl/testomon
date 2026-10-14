/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.binding.soap.interceptor;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import org.apache.cxf.binding.soap.wsdl.extensions.SoapBody;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.databinding.DataReader;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.interceptor.AbstractInDatabindingInterceptor;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageContentsList;
import org.apache.cxf.service.model.BindingInfo;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.apache.cxf.service.model.EndpointInfo;
import org.apache.cxf.service.model.MessageInfo;
import org.apache.cxf.service.model.MessagePartInfo;
import org.apache.cxf.service.model.ServiceInfo;
import org.apache.cxf.service.model.ServiceModelUtil;
import org.apache.cxf.staxutils.DepthXMLStreamReader;
import org.apache.cxf.staxutils.StaxUtils;
import org.apache.cxf.wsdl.interceptors.BareInInterceptor;

public class RPCInInterceptor
extends AbstractInDatabindingInterceptor {
    private static final QName SOAP12_RESULT = new QName("http://www.w3.org/2003/05/soap-rpc", "result");
    private static final Logger LOG = LogUtils.getL7dLogger(RPCInInterceptor.class);

    public RPCInInterceptor() {
        super("unmarshal");
    }

    private BindingOperationInfo getOperation(Message message, QName opName) {
        BindingOperationInfo bop = ServiceModelUtil.getOperation(message.getExchange(), opName);
        if (bop == null) {
            Endpoint ep = message.getExchange().getEndpoint();
            if (ep == null) {
                return null;
            }
            BindingInfo service2 = ep.getEndpointInfo().getBinding();
            boolean output = !this.isRequestor(message);
            for (BindingOperationInfo info : service2.getOperations()) {
                if (!info.getName().getLocalPart().equals(opName.getLocalPart())) continue;
                SoapBody body2 = null;
                body2 = output ? info.getOutput().getExtensor(SoapBody.class) : info.getInput().getExtensor(SoapBody.class);
                if (body2 == null || !opName.getNamespaceURI().equals(body2.getNamespaceURI())) continue;
                return info;
            }
        }
        return bop;
    }

    @Override
    public void handleMessage(Message message) {
        if (this.isGET(message)) {
            LOG.fine("RPCInInterceptor skipped in HTTP GET method");
            return;
        }
        DepthXMLStreamReader xmlReader = this.getXMLStreamReader(message);
        BindingOperationInfo operation = null;
        if (!StaxUtils.toNextElement(xmlReader)) {
            message.setContent(Exception.class, new RuntimeException("There must be a method name element."));
        }
        String opName = xmlReader.getLocalName();
        if (this.isRequestor(message) && opName.endsWith("Response")) {
            opName = opName.substring(0, opName.length() - 8);
        }
        if (message.getExchange().getBindingOperationInfo() == null) {
            operation = this.getOperation(message, new QName(xmlReader.getNamespaceURI(), opName));
            if (operation == null) {
                new BareInInterceptor().handleMessage(message);
                return;
            }
            this.setMessage(message, operation);
        } else {
            operation = message.getExchange().getBindingOperationInfo();
            if (!operation.getName().getLocalPart().equals(opName)) {
                String sa = (String)message.get("SOAPAction");
                throw new Fault("SOAP_ACTION_MISMATCH_OP", LOG, null, sa, opName);
            }
        }
        DataReader<XMLStreamReader> dr = this.getDataReader(message, XMLStreamReader.class);
        MessageInfo msg = !this.isRequestor(message) ? operation.getOperationInfo().getInput() : operation.getOperationInfo().getOutput();
        message.put(MessageInfo.class, msg);
        MessageContentsList parameters = new MessageContentsList();
        StaxUtils.nextEvent(xmlReader);
        boolean hasNext = true;
        for (MessagePartInfo part : msg.getMessageParts()) {
            if (hasNext) {
                hasNext = StaxUtils.toNextElement(xmlReader);
            }
            if (!hasNext) continue;
            QName qn = xmlReader.getName();
            if (qn.equals(SOAP12_RESULT)) {
                try {
                    while (xmlReader.getEventType() != 2) {
                        xmlReader.next();
                    }
                    xmlReader.next();
                }
                catch (XMLStreamException xMLStreamException) {
                    // empty catch block
                }
                StaxUtils.toNextElement(xmlReader);
                qn = xmlReader.getName();
            }
            Iterator<MessagePartInfo> partItr = msg.getMessageParts().iterator();
            while (!qn.getLocalPart().equals(part.getConcreteName().getLocalPart()) && partItr.hasNext()) {
                part = partItr.next();
            }
            if (!qn.getLocalPart().equals(part.getConcreteName().getLocalPart())) {
                throw new Fault(new org.apache.cxf.common.i18n.Message("UNKNOWN_RPC_LIT_PART", LOG, qn));
            }
            try {
                parameters.put(part, dr.read(part, xmlReader));
            }
            catch (Fault f) {
                if (!this.isRequestor(message)) {
                    f.setFaultCode(Fault.FAULT_CODE_CLIENT);
                }
                throw f;
            }
        }
        message.setContent(List.class, parameters);
    }

    private void setMessage(Message message, BindingOperationInfo operation) {
        Exchange ex = message.getExchange();
        ex.put(BindingOperationInfo.class, operation);
        ex.setOneWay(operation.getOperationInfo().isOneWay());
        message.put("javax.xml.ws.wsdl.operation", operation.getName());
        ServiceInfo si = operation.getBinding().getService();
        QName serviceQName = si.getName();
        message.put("javax.xml.ws.wsdl.service", serviceQName);
        QName interfaceQName = si.getInterface().getName();
        message.put("javax.xml.ws.wsdl.interface", interfaceQName);
        EndpointInfo endpointInfo = ex.getEndpoint().getEndpointInfo();
        QName portQName = endpointInfo.getName();
        message.put("javax.xml.ws.wsdl.port", portQName);
        URI wsdlDescription = endpointInfo.getProperty("URI", URI.class);
        if (wsdlDescription == null) {
            String address = endpointInfo.getAddress();
            try {
                wsdlDescription = new URI(address + "?wsdl");
            }
            catch (URISyntaxException uRISyntaxException) {
                // empty catch block
            }
            endpointInfo.setProperty("URI", wsdlDescription);
        }
        message.put("javax.xml.ws.wsdl.description", wsdlDescription);
        this.setOperationSchemaValidation(message);
    }
}

