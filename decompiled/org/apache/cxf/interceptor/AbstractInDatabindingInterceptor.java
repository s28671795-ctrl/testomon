/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.interceptor;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamReader;
import javax.xml.validation.Schema;
import org.apache.cxf.annotations.SchemaValidation;
import org.apache.cxf.common.i18n.BundleUtils;
import org.apache.cxf.databinding.DataReader;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.helpers.ServiceUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.service.Service;
import org.apache.cxf.service.model.BindingMessageInfo;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.apache.cxf.service.model.EndpointInfo;
import org.apache.cxf.service.model.MessageInfo;
import org.apache.cxf.service.model.MessagePartInfo;
import org.apache.cxf.service.model.OperationInfo;
import org.apache.cxf.service.model.ServiceInfo;
import org.apache.cxf.service.model.ServiceModelUtil;
import org.apache.cxf.staxutils.DepthXMLStreamReader;
import org.apache.cxf.ws.addressing.EndpointReferenceUtils;
import org.apache.ws.commons.schema.constants.Constants;
import org.w3c.dom.Node;

public abstract class AbstractInDatabindingInterceptor
extends AbstractPhaseInterceptor<Message> {
    public static final String NO_VALIDATE_PARTS = AbstractInDatabindingInterceptor.class.getName() + ".novalidate-parts";
    private static final ResourceBundle BUNDLE = BundleUtils.getBundle(AbstractInDatabindingInterceptor.class);

    public AbstractInDatabindingInterceptor(String phase) {
        super(phase);
    }

    public AbstractInDatabindingInterceptor(String i, String phase) {
        super(i, phase);
    }

    protected boolean supportsDataReader(Message message, Class<?> input) {
        Class<?>[] cls;
        Service service2 = ServiceModelUtil.getService(message.getExchange());
        for (Class<?> c : cls = service2.getDataBinding().getSupportedReaderFormats()) {
            if (!c.equals(input)) continue;
            return true;
        }
        return false;
    }

    protected <T> DataReader<T> getDataReader(Message message, Class<T> input) {
        Service service2 = ServiceModelUtil.getService(message.getExchange());
        DataReader<T> dataReader = service2.getDataBinding().createReader(input);
        if (dataReader == null) {
            throw new Fault(new org.apache.cxf.common.i18n.Message("NO_DATAREADER", BUNDLE, service2.getName()));
        }
        dataReader.setAttachments(message.getAttachments());
        dataReader.setProperty(DataReader.ENDPOINT, message.getExchange().getEndpoint());
        dataReader.setProperty(Message.class.getName(), message);
        this.setDataReaderValidation(service2, message, dataReader);
        return dataReader;
    }

    protected DataReader<XMLStreamReader> getDataReader(Message message) {
        return this.getDataReader(message, XMLStreamReader.class);
    }

    protected DataReader<Node> getNodeDataReader(Message message) {
        return this.getDataReader(message, Node.class);
    }

    protected boolean shouldValidate(Message message) {
        return ServiceUtils.isSchemaValidationEnabled(SchemaValidation.SchemaValidationType.IN, message);
    }

    protected void setDataReaderValidation(Service service2, Message message, DataReader<?> reader) {
        if (this.shouldValidate(message)) {
            Schema schema = EndpointReferenceUtils.getSchema(service2.getServiceInfos().get(0), message.getExchange().getBus());
            reader.setSchema(schema);
        } else {
            reader.setSchema(null);
        }
    }

    protected void setOperationSchemaValidation(Message message) {
        SchemaValidation.SchemaValidationType validationType = ServiceUtils.getSchemaValidationType(message);
        message.put("schema-validation-enabled", validationType);
    }

    protected DepthXMLStreamReader getXMLStreamReader(Message message) {
        XMLStreamReader xr = message.getContent(XMLStreamReader.class);
        if (xr == null) {
            return null;
        }
        if (xr instanceof DepthXMLStreamReader) {
            return (DepthXMLStreamReader)xr;
        }
        DepthXMLStreamReader dr = new DepthXMLStreamReader(xr);
        message.setContent(XMLStreamReader.class, dr);
        return dr;
    }

    protected MessagePartInfo findMessagePart(Exchange exchange, Collection<OperationInfo> operations, QName name, boolean client, int index, Message message) {
        Endpoint ep = exchange.getEndpoint();
        MessagePartInfo lastChoice = null;
        BindingOperationInfo lastBoi = null;
        BindingMessageInfo lastMsgInfo = null;
        Iterator<OperationInfo> itr = operations.iterator();
        while (itr.hasNext()) {
            OperationInfo op = itr.next();
            BindingOperationInfo boi = ep.getEndpointInfo().getBinding().getOperation(op);
            if (boi == null) continue;
            BindingMessageInfo msgInfo = client ? boi.getOutput() : boi.getInput();
            if (msgInfo == null) {
                itr.remove();
                continue;
            }
            List<MessagePartInfo> bodyParts = msgInfo.getMessageParts();
            if (bodyParts.isEmpty() || bodyParts.size() <= index) {
                itr.remove();
                continue;
            }
            MessagePartInfo p = msgInfo.getMessageParts().get(index);
            if (name.getNamespaceURI() == null || name.getNamespaceURI().isEmpty()) {
                name = new QName(p.getMessageInfo().getName().getNamespaceURI(), name.getLocalPart());
            }
            if (name.equals(p.getConcreteName())) {
                exchange.put(BindingOperationInfo.class, boi);
                exchange.setOneWay(op.isOneWay());
                return p;
            }
            if (Constants.XSD_ANYTYPE.equals(p.getTypeQName())) {
                lastChoice = p;
                lastBoi = boi;
                lastMsgInfo = msgInfo;
                continue;
            }
            itr.remove();
        }
        if (lastChoice != null) {
            this.setMessage(message, lastBoi, client, lastBoi.getBinding().getService(), lastMsgInfo.getMessageInfo());
        }
        return lastChoice;
    }

    protected MessageInfo setMessage(Message message, BindingOperationInfo operation, boolean requestor, ServiceInfo si, MessageInfo msgInfo) {
        message.put(MessageInfo.class, msgInfo);
        Exchange ex = message.getExchange();
        ex.put(BindingOperationInfo.class, operation);
        ex.setOneWay(operation.getOperationInfo().isOneWay());
        boolean synthetic = Boolean.TRUE.equals(operation.getProperty("operation.is.synthetic"));
        if (!synthetic) {
            message.put("javax.xml.ws.wsdl.operation", operation.getName());
        }
        this.setOperationSchemaValidation(message);
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
        return msgInfo;
    }

    protected BindingOperationInfo getBindingOperationInfo(Exchange exchange, QName name, boolean client) {
        BindingOperationInfo bop;
        String local = name.getLocalPart();
        if (client && local.endsWith("Response")) {
            local = local.substring(0, local.length() - 8);
        }
        if ((bop = ServiceModelUtil.getOperation(exchange, local)) != null) {
            exchange.put(BindingOperationInfo.class, bop);
        }
        return bop;
    }

    protected MessageInfo getMessageInfo(Message message, BindingOperationInfo operation) {
        return this.getMessageInfo(message, operation, this.isRequestor(message));
    }

    protected MessageInfo getMessageInfo(Message message, BindingOperationInfo operation, boolean requestor) {
        MessageInfo msgInfo;
        OperationInfo intfOp = operation.getOperationInfo();
        if (requestor) {
            msgInfo = intfOp.getOutput();
            message.put(MessageInfo.class, intfOp.getOutput());
        } else {
            msgInfo = intfOp.getInput();
            message.put(MessageInfo.class, intfOp.getInput());
        }
        return msgInfo;
    }
}

