/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.wsdl.interceptors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
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
import org.apache.cxf.message.MessageUtils;
import org.apache.cxf.service.Service;
import org.apache.cxf.service.model.BindingMessageInfo;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.apache.cxf.service.model.MessageInfo;
import org.apache.cxf.service.model.MessagePartInfo;
import org.apache.cxf.service.model.OperationInfo;
import org.apache.cxf.service.model.ServiceInfo;
import org.apache.cxf.service.model.ServiceModelUtil;
import org.apache.cxf.staxutils.DepthXMLStreamReader;
import org.apache.cxf.staxutils.StaxUtils;
import org.apache.ws.commons.schema.XmlSchemaElement;
import org.apache.ws.commons.schema.constants.Constants;

public class DocLiteralInInterceptor
extends AbstractInDatabindingInterceptor {
    public static final String KEEP_PARAMETERS_WRAPPER = DocLiteralInInterceptor.class.getName() + ".DocLiteralInInterceptor.keep-parameters-wrapper";
    private static final Logger LOG = LogUtils.getL7dLogger(DocLiteralInInterceptor.class);

    public DocLiteralInInterceptor() {
        super("unmarshal");
    }

    @Override
    public void handleMessage(Message message) {
        if (this.isGET(message) && message.getContent(List.class) != null) {
            LOG.fine("DocLiteralInInterceptor skipped in HTTP GET method");
            return;
        }
        DepthXMLStreamReader xmlReader = this.getXMLStreamReader(message);
        MessageContentsList parameters = new MessageContentsList();
        Exchange exchange = message.getExchange();
        BindingOperationInfo bop = exchange.getBindingOperationInfo();
        boolean client = this.isRequestor(message);
        if (bop != null && !StaxUtils.toNextElement(xmlReader)) {
            return;
        }
        Service service2 = ServiceModelUtil.getService(message.getExchange());
        bop = this.getBindingOperationInfo(xmlReader, exchange, bop, client);
        boolean forceDocLitBare = false;
        if (bop != null && bop.getBinding() != null) {
            forceDocLitBare = Boolean.TRUE.equals(bop.getBinding().getService().getProperty("soap.force.doclit.bare"));
        }
        DataReader<XMLStreamReader> dr = this.getDataReader(message);
        try {
            if (!forceDocLitBare && bop != null && bop.isUnwrappedCapable()) {
                ServiceInfo si = bop.getBinding().getService();
                MessageInfo msgInfo = this.setMessage(message, bop, client, si);
                this.setDataReaderValidation(service2, message, dr);
                if (this.shouldWrapParameters(msgInfo, message)) {
                    QName startQName = xmlReader.getName();
                    MessagePartInfo mpi = msgInfo.getFirstMessagePart();
                    if (!mpi.getConcreteName().equals(startQName)) {
                        throw new Fault("UNEXPECTED_WRAPPER_ELEMENT", LOG, null, startQName, mpi.getConcreteName());
                    }
                    Object wrappedObject = dr.read(mpi, xmlReader);
                    parameters.put(mpi, wrappedObject);
                } else {
                    bop = bop.getUnwrappedOperation();
                    msgInfo = this.setMessage(message, bop, client, si);
                    List<MessagePartInfo> messageParts = msgInfo.getMessageParts();
                    Iterator<MessagePartInfo> itr = messageParts.iterator();
                    if (xmlReader.getEventType() == 1) {
                        StaxUtils.nextEvent(xmlReader);
                    }
                    this.getPara(xmlReader, dr, parameters, itr, message);
                }
            } else {
                BindingMessageInfo msgInfo = null;
                Endpoint ep = exchange.getEndpoint();
                ServiceInfo si = ep.getEndpointInfo().getService();
                if (bop != null) {
                    if (client) {
                        msgInfo = bop.getOutput();
                    } else {
                        msgInfo = bop.getInput();
                        if (bop.getOutput() == null) {
                            exchange.setOneWay(true);
                        }
                    }
                    if (msgInfo == null) {
                        return;
                    }
                    this.setMessage(message, bop, client, si, msgInfo.getMessageInfo());
                }
                ArrayList<OperationInfo> operations = null;
                operations = new ArrayList<OperationInfo>();
                operations.addAll(si.getInterface().getOperations());
                if (xmlReader == null || !StaxUtils.toNextElement(xmlReader)) {
                    this.getBindingOperationForEmptyBody(operations, ep, exchange);
                    return;
                }
                this.setDataReaderValidation(service2, message, dr);
                int paramNum = 0;
                do {
                    QName elName = xmlReader.getName();
                    Object o = null;
                    if (!client && msgInfo != null && msgInfo.getMessageParts() != null && msgInfo.getMessageParts().isEmpty()) {
                        return;
                    }
                    MessagePartInfo p = msgInfo != null && msgInfo.getMessageParts() != null && msgInfo.getMessageParts().size() > 0 ? (msgInfo.getMessageParts().size() > paramNum ? msgInfo.getMessageParts().get(paramNum) : null) : this.findMessagePart(exchange, operations, elName, client, paramNum, message);
                    if (!forceDocLitBare) {
                        this.validatePart(p, elName, message);
                    }
                    o = dr.read(p, xmlReader);
                    if (forceDocLitBare && parameters.isEmpty()) {
                        parameters.add(o);
                    } else {
                        parameters.put(p, o);
                    }
                    ++paramNum;
                    if (message.getContent(XMLStreamReader.class) != null && o != xmlReader) continue;
                    xmlReader = null;
                } while (xmlReader != null && StaxUtils.toNextElement(xmlReader));
            }
            message.setContent(List.class, parameters);
        }
        catch (Fault f) {
            if (!this.isRequestor(message)) {
                f.setFaultCode(Fault.FAULT_CODE_CLIENT);
            }
            throw f;
        }
    }

    private void getBindingOperationForEmptyBody(Collection<OperationInfo> operations, Endpoint ep, Exchange exchange) {
        for (OperationInfo op : operations) {
            MessageInfo bmsg = op.getInput();
            int bPartsNum = bmsg.getMessagePartsNumber();
            if (bPartsNum != 0 && (bPartsNum != 1 || !Constants.XSD_ANYTYPE.equals(bmsg.getFirstMessagePart().getTypeQName()))) continue;
            BindingOperationInfo boi = ep.getEndpointInfo().getBinding().getOperation(op);
            exchange.put(BindingOperationInfo.class, boi);
            exchange.setOneWay(op.isOneWay());
        }
    }

    private BindingOperationInfo getBindingOperationInfo(DepthXMLStreamReader xmlReader, Exchange exchange, BindingOperationInfo bop, boolean client) {
        if (bop != null && bop.isUnwrapped()) {
            bop = bop.getWrappedOperation();
        }
        if (bop == null) {
            QName startQName = xmlReader == null ? new QName("http://cxf.apache.org/jaxws/provider", "invoke") : xmlReader.getName();
            bop = this.getBindingOperationInfo(exchange, startQName, client);
        }
        return bop;
    }

    private void validatePart(MessagePartInfo p, QName elName, Message m4) {
        OperationInfo op;
        Boolean b;
        if (p == null) {
            throw new Fault(new org.apache.cxf.common.i18n.Message("NO_PART_FOUND", LOG, elName), Fault.FAULT_CODE_CLIENT);
        }
        boolean synth = false;
        if (p.getMessageInfo() != null && p.getMessageInfo().getOperation() != null && (b = (Boolean)(op = p.getMessageInfo().getOperation()).getProperty("operation.is.synthetic")) != null) {
            synth = b;
        }
        if (MessageUtils.getContextualBoolean(m4, "soap.no.validate.parts", false)) {
            synth = true;
        }
        if (synth) {
            return;
        }
        if (p.isElement() ? p.getConcreteName() != null && !elName.equals(p.getConcreteName()) && !synth : !elName.equals(p.getName()) && !elName.equals(p.getConcreteName()) && !synth) {
            throw new Fault("UNEXPECTED_ELEMENT", LOG, null, elName, p.getConcreteName());
        }
    }

    private void getPara(DepthXMLStreamReader xmlReader, DataReader<XMLStreamReader> dr, MessageContentsList parameters, Iterator<MessagePartInfo> itr, Message message) {
        boolean hasNext = true;
        while (itr.hasNext()) {
            MessagePartInfo part = itr.next();
            if (hasNext) {
                hasNext = StaxUtils.toNextElement(xmlReader);
            }
            Object obj = null;
            if (hasNext) {
                QName rname = xmlReader.getName();
                while (part != null && !rname.equals(part.getConcreteName())) {
                    if (part.getXmlSchema() instanceof XmlSchemaElement) {
                        parameters.put(part, null);
                    }
                    if (itr.hasNext()) {
                        part = itr.next();
                        continue;
                    }
                    part = null;
                }
                if (part == null) {
                    return;
                }
                if (rname.equals(part.getConcreteName())) {
                    obj = dr.read(part, xmlReader);
                }
            }
            parameters.put(part, obj);
        }
    }

    private MessageInfo setMessage(Message message, BindingOperationInfo operation, boolean requestor, ServiceInfo si) {
        MessageInfo msgInfo = this.getMessageInfo(message, operation, requestor);
        return this.setMessage(message, operation, requestor, si, msgInfo);
    }

    @Override
    protected BindingOperationInfo getBindingOperationInfo(Exchange exchange, QName name, boolean client) {
        BindingOperationInfo bop = ServiceModelUtil.getOperationForWrapperElement(exchange, name, client);
        if (bop == null) {
            bop = super.getBindingOperationInfo(exchange, name, client);
        }
        if (bop != null) {
            exchange.put(BindingOperationInfo.class, bop);
        }
        return bop;
    }

    protected boolean shouldWrapParameters(MessageInfo msgInfo, Message message) {
        Object keepParametersWrapperFlag = message.get(KEEP_PARAMETERS_WRAPPER);
        if (keepParametersWrapperFlag == null) {
            return msgInfo.getFirstMessagePart().getTypeClass() != null;
        }
        return Boolean.parseBoolean(keepParametersWrapperFlag.toString());
    }
}

