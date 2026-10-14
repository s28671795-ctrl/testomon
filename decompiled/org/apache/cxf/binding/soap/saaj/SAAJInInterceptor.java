/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.binding.soap.saaj;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import javax.xml.namespace.QName;
import javax.xml.soap.AttachmentPart;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Namespace;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.dom.DOMSource;
import org.apache.cxf.Bus;
import org.apache.cxf.attachment.AttachmentDataSource;
import org.apache.cxf.binding.soap.Soap11;
import org.apache.cxf.binding.soap.Soap12;
import org.apache.cxf.binding.soap.SoapFault;
import org.apache.cxf.binding.soap.SoapHeader;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.binding.soap.interceptor.ReadHeadersInterceptor;
import org.apache.cxf.binding.soap.saaj.SAAJFactoryResolver;
import org.apache.cxf.binding.soap.saaj.SAAJStreamWriter;
import org.apache.cxf.binding.soap.saaj.SAAJUtils;
import org.apache.cxf.common.i18n.BundleUtils;
import org.apache.cxf.common.injection.NoJSR250Annotations;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.databinding.DataBinding;
import org.apache.cxf.headers.Header;
import org.apache.cxf.headers.HeaderManager;
import org.apache.cxf.headers.HeaderProcessor;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Attachment;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.PhaseInterceptor;
import org.apache.cxf.staxutils.StaxUtils;
import org.apache.cxf.staxutils.W3CDOMStreamWriter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

@NoJSR250Annotations
public class SAAJInInterceptor
extends AbstractSoapInterceptor {
    public static final SAAJInInterceptor INSTANCE = new SAAJInInterceptor();
    private static final ResourceBundle BUNDLE = BundleUtils.getBundle(SAAJInInterceptor.class);
    private static final String BODY_FILLED_IN = SAAJInInterceptor.class.getName() + ".BODY_DONE";
    private SAAJPreInInterceptor preInterceptor = SAAJPreInInterceptor.INSTANCE;
    private List<PhaseInterceptor<? extends Message>> extras = new ArrayList<PhaseInterceptor<? extends Message>>(1);

    public SAAJInInterceptor() {
        super("pre-protocol");
        this.extras.add(this.preInterceptor);
    }

    public SAAJInInterceptor(String phase) {
        super(phase);
    }

    @Override
    public Collection<PhaseInterceptor<? extends Message>> getAdditionalInterceptors() {
        return this.extras;
    }

    @Override
    public void handleMessage(SoapMessage message) throws Fault {
        if (this.isGET(message)) {
            return;
        }
        Boolean bodySet = (Boolean)message.get(BODY_FILLED_IN);
        if (Boolean.TRUE.equals(bodySet)) {
            return;
        }
        message.put(BODY_FILLED_IN, (Object)Boolean.TRUE);
        try {
            XMLStreamReader xmlReader;
            SOAPMessage soapMessage = message.getContent(SOAPMessage.class);
            if (soapMessage == null) {
                MessageFactory factory2 = this.preInterceptor.getFactory(message);
                soapMessage = factory2.createMessage();
                message.setContent(SOAPMessage.class, soapMessage);
            }
            if ((xmlReader = message.getContent(XMLStreamReader.class)) == null) {
                return;
            }
            SOAPPart part = soapMessage.getSOAPPart();
            Document node = (Document)message.getContent(Node.class);
            if (node != part && node != null) {
                StaxUtils.copy(node, (XMLStreamWriter)new SAAJStreamWriter(part));
            } else {
                SOAPEnvelope env = soapMessage.getSOAPPart().getEnvelope();
                if (node == null) {
                    SAAJInInterceptor.adjustPrefixes(env, (String)message.get("envelope.prefix"), (String)message.get("body.prefix"));
                }
                Object events = (List)message.get("envelope.events");
                SAAJInInterceptor.applyEvents((List<XMLEvent>)events, env);
                SOAPBody body2 = soapMessage.getSOAPBody();
                events = (List)message.get("body.events");
                SAAJInInterceptor.applyEvents((List<XMLEvent>)events, body2);
            }
            message.setContent(Node.class, soapMessage.getSOAPPart());
            Collection<Attachment> atts = message.getAttachments();
            if (atts != null) {
                for (Attachment a : atts) {
                    if (a.getDataHandler().getDataSource() instanceof AttachmentDataSource) {
                        try {
                            ((AttachmentDataSource)a.getDataHandler().getDataSource()).cache(message);
                        }
                        catch (IOException e) {
                            throw new Fault(e);
                        }
                    }
                    AttachmentPart ap = soapMessage.createAttachmentPart(a.getDataHandler());
                    Iterator<String> i = a.getHeaderNames();
                    while (i != null && i.hasNext()) {
                        String h2 = i.next();
                        String val = a.getHeader(h2);
                        ap.addMimeHeader(h2, val);
                    }
                    if (StringUtils.isEmpty(ap.getContentId())) {
                        ap.setContentId(a.getId());
                    }
                    soapMessage.addAttachmentPart(ap);
                }
            }
            if (message.hasHeaders()) {
                SAAJInInterceptor.replaceHeaders(soapMessage, message);
            }
            if (soapMessage.getSOAPPart().getEnvelope().getHeader() == null) {
                soapMessage.getSOAPPart().getEnvelope().addHeader();
            }
            boolean secureReader = StaxUtils.isSecureReader(xmlReader, message);
            StaxUtils.copy(xmlReader, new SAAJStreamWriter(soapMessage.getSOAPPart(), (Element)soapMessage.getSOAPPart().getEnvelope().getBody()), true, !secureReader);
            DOMSource bodySource = new DOMSource(soapMessage.getSOAPPart().getEnvelope().getBody());
            xmlReader = StaxUtils.createXMLStreamReader(bodySource);
            xmlReader.nextTag();
            xmlReader.nextTag();
            message.setContent(XMLStreamReader.class, xmlReader);
        }
        catch (SOAPException soape) {
            throw new SoapFault(new org.apache.cxf.common.i18n.Message("SOAPHANDLERINTERCEPTOR_EXCEPTION", BUNDLE, new Object[0]), (Throwable)soape, message.getVersion().getSender());
        }
        catch (XMLStreamException e) {
            throw new SoapFault(new org.apache.cxf.common.i18n.Message("SOAPHANDLERINTERCEPTOR_EXCEPTION", BUNDLE, new Object[0]), (Throwable)e, message.getVersion().getSender());
        }
    }

    private static void adjustPrefixes(SOAPEnvelope env, String envPrefix, String bodyPrefix) throws SOAPException {
        SAAJUtils.adjustPrefix(env, envPrefix);
        SAAJUtils.adjustPrefix(env.getBody(), bodyPrefix);
        SAAJUtils.adjustPrefix(env.getHeader(), envPrefix);
    }

    private static void applyEvents(List<XMLEvent> events, SOAPElement el) throws SOAPException {
        if (events != null) {
            for (XMLEvent ev : events) {
                if (ev.isNamespace()) {
                    el.addNamespaceDeclaration(((Namespace)ev).getPrefix(), ((Namespace)ev).getNamespaceURI());
                    continue;
                }
                if (!ev.isAttribute()) continue;
                el.addAttribute(((Attribute)ev).getName(), ((Attribute)ev).getValue());
            }
        }
    }

    public static void replaceHeaders(SOAPMessage soapMessage, SoapMessage message) throws SOAPException {
        SOAPHeader header = SAAJUtils.getHeader(soapMessage);
        if (header == null) {
            return;
        }
        Element elem = DOMUtils.getFirstElement(header);
        elem = (Element)DOMUtils.getDomElement(elem);
        while (elem != null) {
            Object obj;
            Bus b = message.getExchange() == null ? null : message.getExchange().getBus();
            HeaderProcessor p = null;
            if (b != null && b.getExtension(HeaderManager.class) != null) {
                p = b.getExtension(HeaderManager.class).getHeaderProcessor(elem.getNamespaceURI());
            }
            DataBinding dataBinding = null;
            if (p == null || p.getDataBinding() == null) {
                obj = elem;
            } else {
                dataBinding = p.getDataBinding();
                obj = p.getDataBinding().createReader(Node.class).read(elem);
            }
            SoapHeader shead = new SoapHeader(new QName(elem.getNamespaceURI(), elem.getLocalName()), obj, dataBinding);
            shead.setDirection(Header.Direction.DIRECTION_IN);
            String mu = elem.getAttributeNS(message.getVersion().getNamespace(), message.getVersion().getAttrNameMustUnderstand());
            String act = elem.getAttributeNS(message.getVersion().getNamespace(), message.getVersion().getAttrNameRole());
            shead.setActor(act);
            shead.setMustUnderstand(Boolean.valueOf(mu) != false || "1".equals(mu));
            Header oldHdr = message.getHeader(new QName(elem.getNamespaceURI(), elem.getLocalName()));
            if (oldHdr != null) {
                message.getHeaders().remove(oldHdr);
            }
            message.getHeaders().add(shead);
            elem = DOMUtils.getNextElement(elem);
        }
    }

    public static class SAAJPreInInterceptor
    extends AbstractSoapInterceptor {
        public static final SAAJPreInInterceptor INSTANCE = new SAAJPreInInterceptor();
        private MessageFactory factory11;
        private MessageFactory factory12;

        public SAAJPreInInterceptor() {
            super("read");
            this.addBefore(ReadHeadersInterceptor.class.getName());
        }

        @Override
        public void handleMessage(SoapMessage message) throws Fault {
            if (this.isGET(message)) {
                return;
            }
            if (this.isRequestor(message) && message.getExchange().getInMessage() == null) {
                return;
            }
            try {
                XMLStreamReader xmlReader = message.getContent(XMLStreamReader.class);
                if (xmlReader == null) {
                    return;
                }
                if (xmlReader.nextTag() == 1) {
                    ReadHeadersInterceptor.readVersion(xmlReader, message);
                }
                MessageFactory factory2 = this.getFactory(message);
                SOAPMessage soapMessage = factory2.createMessage();
                message.setContent(SOAPMessage.class, soapMessage);
                SOAPPart part = soapMessage.getSOAPPart();
                message.setContent(Node.class, part);
                message.put(W3CDOMStreamWriter.class, new SAAJStreamWriter(part));
                message.put(BODY_FILLED_IN, (Object)Boolean.FALSE);
            }
            catch (RuntimeException ex) {
                throw ex;
            }
            catch (Exception e) {
                throw new SoapFault("XML_STREAM_EXC", BUNDLE, (Throwable)e, message.getVersion().getSender());
            }
        }

        public synchronized MessageFactory getFactory(SoapMessage message) throws SOAPException {
            if (message.getVersion() instanceof Soap11) {
                if (this.factory11 == null) {
                    this.factory11 = SAAJFactoryResolver.createMessageFactory(message.getVersion());
                }
                return this.factory11;
            }
            if (message.getVersion() instanceof Soap12) {
                if (this.factory12 == null) {
                    this.factory12 = SAAJFactoryResolver.createMessageFactory(message.getVersion());
                }
                return this.factory12;
            }
            return SAAJFactoryResolver.createMessageFactory(null);
        }
    }
}

