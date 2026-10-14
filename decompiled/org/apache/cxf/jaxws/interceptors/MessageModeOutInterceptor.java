/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.jaxws.interceptors;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import javax.activation.DataSource;
import javax.xml.namespace.QName;
import javax.xml.soap.Detail;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Schema;
import org.apache.cxf.annotations.SchemaValidation;
import org.apache.cxf.attachment.AttachmentDeserializer;
import org.apache.cxf.binding.soap.Soap12;
import org.apache.cxf.binding.soap.SoapFault;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.binding.soap.saaj.SAAJOutInterceptor;
import org.apache.cxf.binding.soap.saaj.SAAJStreamWriter;
import org.apache.cxf.binding.soap.saaj.SAAJUtils;
import org.apache.cxf.common.util.PropertyUtils;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.helpers.ServiceUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.io.CachedOutputStream;
import org.apache.cxf.message.Message;
import org.apache.cxf.message.MessageContentsList;
import org.apache.cxf.message.MessageImpl;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.service.model.BindingFaultInfo;
import org.apache.cxf.service.model.BindingMessageInfo;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.apache.cxf.staxutils.StaxUtils;
import org.apache.cxf.ws.addressing.EndpointReferenceUtils;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class MessageModeOutInterceptor
extends AbstractPhaseInterceptor<Message> {
    MessageModeOutInterceptorInternal internal;
    SAAJOutInterceptor saajOut;
    Class<?> type;
    QName bindingName;

    public MessageModeOutInterceptor(SAAJOutInterceptor saajOut, QName bname) {
        super("prepare-send");
        this.saajOut = saajOut;
        this.bindingName = bname;
        this.internal = new MessageModeOutInterceptorInternal();
    }

    public MessageModeOutInterceptor(Class<?> t2, QName bname) {
        super("prepare-send");
        this.type = t2;
        this.bindingName = bname;
    }

    @Override
    public void handleMessage(Message message) throws Fault {
        MessageContentsList list;
        Source ds;
        BindingOperationInfo bop = message.getExchange().getBindingOperationInfo();
        if (bop != null && !this.bindingName.equals(bop.getBinding().getName())) {
            return;
        }
        if (this.saajOut != null) {
            this.doSoap(message);
        } else if (DataSource.class.isAssignableFrom(this.type)) {
            MessageContentsList list2 = (MessageContentsList)message.getContent(List.class);
            DataSource ds2 = (DataSource)list2.get(0);
            String ct = ds2.getContentType();
            if (ct.toLowerCase().contains("multipart/related")) {
                MessageImpl msg = new MessageImpl();
                msg.setExchange(message.getExchange());
                msg.put("Content-Type", ct);
                try {
                    msg.setContent(InputStream.class, ds2.getInputStream());
                    AttachmentDeserializer deser = new AttachmentDeserializer(msg);
                    deser.initializeAttachments();
                }
                catch (IOException ex) {
                    throw new Fault(ex);
                }
                message.setAttachments(msg.getAttachments());
                final InputStream in = msg.getContent(InputStream.class);
                final String ct2 = (String)msg.get("Content-Type");
                list2.set(0, new DataSource(){

                    @Override
                    public String getContentType() {
                        return ct2;
                    }

                    @Override
                    public InputStream getInputStream() throws IOException {
                        return in;
                    }

                    @Override
                    public String getName() {
                        return ct2;
                    }

                    @Override
                    public OutputStream getOutputStream() throws IOException {
                        return null;
                    }
                });
            } else if (!ct.toLowerCase().contains("xml")) {
                OutputStream out = message.getContent(OutputStream.class);
                message.put("Content-Type", ct);
                try {
                    InputStream in = ds2.getInputStream();
                    IOUtils.copy(in, out);
                    in.close();
                    out.flush();
                    out.close();
                }
                catch (IOException e) {
                    throw new Fault(e);
                }
                list2.remove(0);
                out = new CachedOutputStream();
                message.setContent(OutputStream.class, out);
                XMLStreamWriter writer = StaxUtils.createXMLStreamWriter(out);
                message.setContent(XMLStreamWriter.class, writer);
            }
        } else if (ServiceUtils.isSchemaValidationEnabled(SchemaValidation.SchemaValidationType.OUT, message) && Source.class.isAssignableFrom(this.type) && !((ds = (Source)(list = (MessageContentsList)message.getContent(List.class)).get(0)) instanceof DOMSource)) {
            try {
                ds = new DOMSource(StaxUtils.read(ds));
            }
            catch (XMLStreamException e) {
                throw new Fault(e);
            }
            list.set(0, ds);
            this.validatePossibleFault(message, bop, ((DOMSource)ds).getNode());
        }
    }

    private void validatePossibleFault(Message message, BindingOperationInfo bop, Node ds) {
        Element el = DOMUtils.getFirstElement(ds);
        if (!"Fault".equals(el.getLocalName())) {
            return;
        }
        message.put(Message.RESPONSE_CODE, 500);
        el = DOMUtils.getFirstElement(el);
        while (el != null && !"detail".equals(el.getLocalName())) {
            el = DOMUtils.getNextElement(el);
        }
        if (el != null) {
            Schema schema = EndpointReferenceUtils.getSchema(message.getExchange().getService().getServiceInfos().get(0), message.getExchange().getBus());
            try {
                this.validateFaultDetail(el, schema, bop);
            }
            catch (Exception e) {
                throw new Fault(e);
            }
            message.put("schema-validation-enabled", Boolean.FALSE);
        }
    }

    private void validateFaultDetail(Element detail, Schema schema, BindingOperationInfo bop) throws Exception {
        if (detail != null) {
            Element el = DOMUtils.getFirstElement(detail);
            while (el != null) {
                QName qn = DOMUtils.getElementQName(el);
                for (BindingFaultInfo bfi : bop.getFaults()) {
                    if (!bfi.getFaultInfo().getMessagePartByIndex(0).getConcreteName().equals(qn)) continue;
                    schema.newValidator().validate(new DOMSource(DOMUtils.getDomElement(el)));
                }
                el = DOMUtils.getNextElement(el);
            }
        }
    }

    private void validateFault(SoapMessage message, SOAPFault fault, BindingOperationInfo bop) {
        if (ServiceUtils.isSchemaValidationEnabled(SchemaValidation.SchemaValidationType.OUT, message)) {
            Schema schema = EndpointReferenceUtils.getSchema(message.getExchange().getService().getServiceInfos().get(0), message.getExchange().getBus());
            Detail d = fault.getDetail();
            try {
                this.validateFaultDetail(d, schema, bop);
            }
            catch (Exception e) {
                throw new SoapFault(e.getMessage(), (Throwable)e, message.getVersion().getReceiver());
            }
            message.put("schema-validation-enabled", (Object)Boolean.FALSE);
        }
    }

    private void doSoap(Message message) {
        MessageContentsList list = (MessageContentsList)message.getContent(List.class);
        if (list == null || list.isEmpty()) {
            return;
        }
        Object o = list.get(0);
        if (o instanceof SOAPMessage) {
            SOAPMessage soapMessage = (SOAPMessage)o;
            if (soapMessage.countAttachments() > 0) {
                message.put("write.attachments", Boolean.TRUE);
            }
            try {
                if (message instanceof SoapMessage) {
                    String namespace;
                    SoapMessage cxfSoapMessage = (SoapMessage)message;
                    String cxfNamespace = cxfSoapMessage.getVersion().getNamespace();
                    SOAPHeader soapHeader = soapMessage.getSOAPHeader();
                    String string = namespace = soapHeader == null ? null : soapHeader.getNamespaceURI();
                    if ("http://www.w3.org/2003/05/soap-envelope".equals(namespace) && !namespace.equals(cxfNamespace)) {
                        cxfSoapMessage.setVersion(Soap12.getInstance());
                        cxfSoapMessage.put("Content-Type", (Object)cxfSoapMessage.getVersion().getContentType());
                    }
                }
            }
            catch (SOAPException cxfSoapMessage) {
                // empty catch block
            }
            try {
                Object enc = soapMessage.getProperty("javax.xml.soap.character-set-encoding");
                if (enc instanceof String) {
                    message.put(Message.ENCODING, enc);
                }
            }
            catch (SOAPException enc) {
                // empty catch block
            }
            try {
                Object xmlDec = soapMessage.getProperty("javax.xml.soap.write-xml-declaration");
                if (xmlDec != null) {
                    boolean b = PropertyUtils.isTrue(xmlDec);
                    message.put("org.apache.cxf.stax.force-start-document", b);
                }
            }
            catch (SOAPException sOAPException) {
                // empty catch block
            }
        }
        message.getInterceptorChain().add(this.internal);
    }

    private class MessageModeOutInterceptorInternal
    extends AbstractSoapInterceptor {
        MessageModeOutInterceptorInternal() {
            super("pre-protocol");
            this.addBefore(SAAJOutInterceptor.class.getName());
        }

        @Override
        public void handleMessage(SoapMessage message) throws Fault {
            MessageContentsList list = (MessageContentsList)message.getContent(List.class);
            Object o = list.remove(0);
            SOAPMessage soapMessage = null;
            if (o instanceof SOAPMessage) {
                soapMessage = (SOAPMessage)o;
                if (soapMessage.countAttachments() > 0) {
                    message.put("write.attachments", (Object)Boolean.TRUE);
                }
            } else {
                try {
                    MessageFactory factory2 = MessageModeOutInterceptor.this.saajOut.getFactory(message);
                    soapMessage = factory2.createMessage();
                    SOAPPart part = soapMessage.getSOAPPart();
                    if (o instanceof Source) {
                        StaxUtils.copy((Source)o, (XMLStreamWriter)new SAAJStreamWriter(part));
                    }
                }
                catch (SOAPException | XMLStreamException e) {
                    throw new SoapFault("Error creating SOAPMessage", (Throwable)e, message.getVersion().getSender());
                }
            }
            BindingOperationInfo bop = message.getExchange().getBindingOperationInfo();
            DocumentFragment frag = soapMessage.getSOAPPart().createDocumentFragment();
            try {
                SOAPBody body2 = SAAJUtils.getBody(soapMessage);
                Node nd = body2.getFirstChild();
                while (nd != null) {
                    if (nd instanceof SOAPFault) {
                        message.put(Message.RESPONSE_CODE, (Object)500);
                        MessageModeOutInterceptor.this.validateFault(message, (SOAPFault)nd, bop);
                    }
                    body2.removeChild(nd);
                    nd = DOMUtils.getDomElement(nd);
                    frag.appendChild(nd);
                    nd = SAAJUtils.getBody(soapMessage).getFirstChild();
                }
                message.setContent(SOAPMessage.class, soapMessage);
                if (!message.containsKey(SAAJOutInterceptor.ORIGINAL_XML_WRITER)) {
                    XMLStreamWriter origWriter = message.getContent(XMLStreamWriter.class);
                    message.put(SAAJOutInterceptor.ORIGINAL_XML_WRITER, (Object)origWriter);
                }
                SAAJStreamWriter writer = new SAAJStreamWriter(soapMessage.getSOAPPart());
                message.setContent(XMLStreamWriter.class, writer);
                message.setContent(SOAPMessage.class, soapMessage);
                int index = 0;
                boolean client = this.isRequestor(message);
                BindingMessageInfo bmsg = null;
                if (client && bop != null) {
                    bmsg = bop.getInput();
                } else if (bop != null && bop.getOutput() != null) {
                    bmsg = bop.getOutput();
                }
                if (bmsg != null && bmsg.getMessageParts() != null && bmsg.getMessageParts().size() > 0) {
                    index = bmsg.getMessageParts().get(0).getIndex();
                }
                list.set(index, frag);
                Object buffer = message.getContextualProperty("org.apache.cxf.output.buffering");
                if (buffer == null) {
                    message.put("org.apache.cxf.output.buffering", (Object)Boolean.FALSE);
                }
            }
            catch (Exception ex) {
                throw new Fault(ex);
            }
            if (bop != null && bop.isUnwrapped()) {
                bop = bop.getWrappedOperation();
                message.getExchange().put(BindingOperationInfo.class, bop);
            }
            message.getInterceptorChain().add(SAAJOutInterceptor.SAAJOutEndingInterceptor.INSTANCE);
        }
    }
}

