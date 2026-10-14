/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.binding.soap.interceptor;

import java.util.List;
import java.util.logging.Logger;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.events.XMLEvent;
import org.apache.cxf.binding.soap.wsdl.extensions.SoapBody;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.helpers.NSStack;
import org.apache.cxf.interceptor.AbstractOutDatabindingInterceptor;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageContentsList;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.apache.cxf.service.model.MessagePartInfo;
import org.apache.cxf.staxutils.CachingXmlEventWriter;
import org.apache.cxf.staxutils.StaxUtils;

public class RPCOutInterceptor
extends AbstractOutDatabindingInterceptor {
    private static final Logger LOG = LogUtils.getL7dLogger(RPCOutInterceptor.class);

    public RPCOutInterceptor() {
        super("marshal");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void handleMessage(Message message) {
        XMLStreamWriter origXmlWriter = null;
        try {
            NSStack nsStack = new NSStack();
            nsStack.push();
            BindingOperationInfo operation = message.getExchange().getBindingOperationInfo();
            assert (operation.getName() != null);
            XMLStreamWriter xmlWriter = this.getXMLStreamWriter(message);
            CachingXmlEventWriter cache = null;
            if (this.shouldBuffer(message)) {
                origXmlWriter = xmlWriter;
                cache = new CachingXmlEventWriter();
                try {
                    cache.setNamespaceContext(xmlWriter.getNamespaceContext());
                }
                catch (XMLStreamException xMLStreamException) {
                    // empty catch block
                }
                message.setContent(XMLStreamWriter.class, cache);
                xmlWriter = cache;
            }
            List<MessagePartInfo> parts = null;
            boolean output = false;
            if (!this.isRequestor(message)) {
                if (operation.getOutput() == null) {
                    if (origXmlWriter == null) return;
                    message.setContent(XMLStreamWriter.class, origXmlWriter);
                    return;
                }
                parts = operation.getOutput().getMessageParts();
                output = true;
            } else {
                parts = operation.getInput().getMessageParts();
                output = false;
            }
            MessageContentsList objs = MessageContentsList.getContentsList(message);
            if (objs == null) {
                this.addOperationNode(nsStack, message, xmlWriter, output, operation);
                xmlWriter.writeEndElement();
                if (origXmlWriter == null) return;
                message.setContent(XMLStreamWriter.class, origXmlWriter);
                return;
            }
            for (MessagePartInfo part : parts) {
                Object o;
                if (!objs.hasValue(part) || (o = objs.get(part)) != null) continue;
                throw new Fault(new org.apache.cxf.common.i18n.Message("BP_2211_RPCLIT_CANNOT_BE_NULL", LOG, part.getConcreteName()));
            }
            this.addOperationNode(nsStack, message, xmlWriter, output, operation);
            this.writeParts(message, message.getExchange(), operation, objs, parts);
            xmlWriter.writeEndElement();
            if (cache != null) {
                try {
                    for (XMLEvent event : cache.getEvents()) {
                        StaxUtils.writeEvent(event, origXmlWriter);
                    }
                }
                catch (XMLStreamException e) {
                    throw new Fault(e);
                }
            }
            if (origXmlWriter == null) return;
            message.setContent(XMLStreamWriter.class, origXmlWriter);
            return;
        }
        catch (XMLStreamException e) {
            try {
                throw new Fault(e);
            }
            catch (Throwable throwable) {
                if (origXmlWriter == null) throw throwable;
                message.setContent(XMLStreamWriter.class, origXmlWriter);
                throw throwable;
            }
        }
    }

    protected String addOperationNode(NSStack nsStack, Message message, XMLStreamWriter xmlWriter, boolean output, BindingOperationInfo boi) throws XMLStreamException {
        String nsUri;
        String ns = boi.getName().getNamespaceURI();
        SoapBody body2 = null;
        body2 = output ? boi.getOutput().getExtensor(SoapBody.class) : boi.getInput().getExtensor(SoapBody.class);
        if (body2 != null && !StringUtils.isEmpty(nsUri = body2.getNamespaceURI())) {
            ns = nsUri;
        }
        nsStack.add(ns);
        String prefix = nsStack.getPrefix(ns);
        String name = output ? boi.getName().getLocalPart() + "Response" : boi.getName().getLocalPart();
        StaxUtils.writeStartElement(xmlWriter, prefix, name, ns);
        return ns;
    }

    @Override
    protected XMLStreamWriter getXMLStreamWriter(Message message) {
        return message.getContent(XMLStreamWriter.class);
    }
}

