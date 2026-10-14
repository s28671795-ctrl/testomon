/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.binding.soap.interceptor;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.events.XMLEvent;
import org.apache.cxf.Bus;
import org.apache.cxf.annotations.SchemaValidation;
import org.apache.cxf.binding.soap.Soap11;
import org.apache.cxf.binding.soap.Soap12;
import org.apache.cxf.binding.soap.SoapFault;
import org.apache.cxf.binding.soap.SoapHeader;
import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.SoapVersion;
import org.apache.cxf.binding.soap.SoapVersionFactory;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.common.i18n.Message;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.databinding.DataBinding;
import org.apache.cxf.databinding.DataReader;
import org.apache.cxf.headers.Header;
import org.apache.cxf.headers.HeaderManager;
import org.apache.cxf.headers.HeaderProcessor;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.helpers.ServiceUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.MessageUtils;
import org.apache.cxf.staxutils.PartialXMLStreamReader;
import org.apache.cxf.staxutils.StaxUtils;
import org.apache.cxf.staxutils.W3CDOMStreamWriter;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class ReadHeadersInterceptor
extends AbstractSoapInterceptor {
    public static final String ENVELOPE_EVENTS = "envelope.events";
    public static final String BODY_EVENTS = "body.events";
    public static final String ENVELOPE_PREFIX = "envelope.prefix";
    public static final String BODY_PREFIX = "body.prefix";
    private static final Logger LOG = LogUtils.getL7dLogger(ReadHeadersInterceptor.class);
    private Bus bus;
    private SoapVersion version;

    public ReadHeadersInterceptor(Bus b) {
        super("read");
        this.bus = b;
    }

    public ReadHeadersInterceptor(Bus b, SoapVersion v) {
        super("read");
        this.version = v;
        this.bus = b;
    }

    public ReadHeadersInterceptor(Bus b, String phase) {
        super(phase);
        this.bus = b;
    }

    public static SoapVersion readVersion(XMLStreamReader xmlReader, SoapMessage message) {
        String ns = xmlReader.getNamespaceURI();
        String lcname = xmlReader.getLocalName();
        if (ns == null || "".equals(ns)) {
            throw new SoapFault(new Message("NO_NAMESPACE", LOG, lcname), Soap11.getInstance().getVersionMismatch());
        }
        SoapVersion soapVersion = SoapVersionFactory.getInstance().getSoapVersion(ns);
        if (soapVersion == null) {
            throw new SoapFault(new Message("INVALID_VERSION", LOG, ns, lcname), Soap11.getInstance().getVersionMismatch());
        }
        if (!"Envelope".equals(lcname)) {
            throw new SoapFault(new Message("INVALID_ENVELOPE", LOG, lcname), soapVersion.getSender());
        }
        message.setVersion(soapVersion);
        return soapVersion;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void handleMessage(SoapMessage message) {
        if (this.isGET(message)) {
            LOG.fine("ReadHeadersInterceptor skipped in HTTP GET method");
            return;
        }
        String verb = (String)message.get("org.apache.cxf.request.method");
        if (verb != null && !"POST".equals(verb)) {
            Fault formula405 = new Fault("HTTP verb was not GET or POST", LOG);
            formula405.setStatusCode(405);
            throw formula405;
        }
        XMLStreamReader xmlReader = message.getContent(XMLStreamReader.class);
        boolean closeNeeded = false;
        if (xmlReader == null) {
            InputStream in = message.getContent(InputStream.class);
            if (in == null) {
                throw new RuntimeException("Can't find input stream in message");
            }
            xmlReader = StaxUtils.createXMLStreamReader(in);
            closeNeeded = true;
        }
        try {
            if (xmlReader.getEventType() == 1 || xmlReader.nextTag() == 1) {
                void var10_17;
                SoapVersion soapVersion = ReadHeadersInterceptor.readVersion(xmlReader, message);
                if (soapVersion == Soap12.getInstance() && this.version == Soap11.getInstance()) {
                    message.setVersion(this.version);
                    throw new SoapFault(new Message("INVALID_11_VERSION", LOG, new Object[0]), this.version.getVersionMismatch());
                }
                PartialXMLStreamReader filteredReader = new PartialXMLStreamReader(xmlReader, message.getVersion().getBody());
                Node nd = message.getContent(Node.class);
                W3CDOMStreamWriter writer = message.get(W3CDOMStreamWriter.class);
                Document doc = null;
                if (writer != null) {
                    StaxUtils.copy((XMLStreamReader)filteredReader, (XMLStreamWriter)writer);
                    doc = writer.getDocument();
                } else if (nd instanceof Document) {
                    doc = (Document)nd;
                    StaxUtils.readDocElements(doc, doc, filteredReader, false, false);
                } else {
                    HeadersProcessor processor;
                    HashMap<String, String> bodyNC;
                    boolean bl = MessageUtils.getContextualBoolean(message, "org.apache.cxf.binding.soap.addNamespaceContext", false);
                    HashMap<String, String> hashMap = bodyNC = bl ? new HashMap<String, String>() : null;
                    if (bl) {
                        this.addCurrentNamespaceDecls(xmlReader, bodyNC);
                    }
                    if ((doc = (processor = new HeadersProcessor(soapVersion)).process(filteredReader)) != null) {
                        message.setContent(Node.class, doc);
                    } else {
                        message.put(ENVELOPE_EVENTS, (Object)processor.getEnvAttributeAndNamespaceEvents());
                        message.put(BODY_EVENTS, (Object)processor.getBodyAttributeAndNamespaceEvents());
                        message.put(ENVELOPE_PREFIX, (Object)processor.getEnvelopePrefix());
                        message.put(BODY_PREFIX, (Object)processor.getBodyPrefix());
                    }
                    if (bl) {
                        this.addCurrentNamespaceDecls(xmlReader, bodyNC);
                        message.put("soap.body.ns.context", (Object)bodyNC);
                    }
                }
                Object var10_14 = null;
                if (doc != null) {
                    Element element = doc.getDocumentElement();
                    QName header = soapVersion.getHeader();
                    QName body2 = soapVersion.getBody();
                    List<Element> elemList = DOMUtils.findAllElementsByTagNameNS(element, header.getNamespaceURI(), header.getLocalPart());
                    ArrayList arrayList = new ArrayList();
                    List<Element> list = DOMUtils.getChildrenWithName(element, body2.getNamespaceURI(), body2.getLocalPart());
                    for (Element elem : elemList) {
                        Element hel = DOMUtils.getFirstElement(elem);
                        while (hel != null) {
                            Object obj;
                            if (elem.hasAttributes()) {
                                NamedNodeMap nnp = elem.getAttributes();
                                for (int ct = 0; ct < nnp.getLength(); ++ct) {
                                    Node headerAttrNode;
                                    Node attr = nnp.item(ct);
                                    Node node = headerAttrNode = hel.hasAttributes() ? hel.getAttributes().getNamedItemNS(attr.getNamespaceURI(), attr.getLocalName()) : null;
                                    if (headerAttrNode != null) continue;
                                    Attr attribute = hel.getOwnerDocument().createAttributeNS(attr.getNamespaceURI(), attr.getNodeName());
                                    attribute.setNodeValue(attr.getNodeValue());
                                    hel.setAttributeNodeNS(attribute);
                                }
                            }
                            HeaderProcessor p = this.bus == null ? null : this.bus.getExtension(HeaderManager.class).getHeaderProcessor(hel.getNamespaceURI());
                            DataBinding dataBinding = null;
                            if (p == null || p.getDataBinding() == null) {
                                obj = hel;
                            } else {
                                dataBinding = p.getDataBinding();
                                DataReader<Node> dataReader = dataBinding.createReader(Node.class);
                                dataReader.setAttachments(message.getAttachments());
                                dataReader.setProperty(DataReader.ENDPOINT, message.getExchange().getEndpoint());
                                dataReader.setProperty(Message.class.getName(), message);
                                obj = dataReader.read(hel);
                            }
                            SoapHeader shead = new SoapHeader(new QName(hel.getNamespaceURI(), hel.getLocalName()), obj, dataBinding);
                            String mu = hel.getAttributeNS(soapVersion.getNamespace(), soapVersion.getAttrNameMustUnderstand());
                            String act = hel.getAttributeNS(soapVersion.getNamespace(), soapVersion.getAttrNameRole());
                            if (!StringUtils.isEmpty(act)) {
                                shead.setActor(act);
                            }
                            shead.setMustUnderstand(Boolean.valueOf(mu) != false || "1".equals(mu));
                            shead.setDirection(Header.Direction.DIRECTION_IN);
                            message.getHeaders().add(shead);
                            hel = DOMUtils.getNextElement(hel);
                        }
                    }
                }
                if (ServiceUtils.isSchemaValidationEnabled(SchemaValidation.SchemaValidationType.IN, message)) {
                    message.getInterceptorChain().add(new CheckClosingTagsInterceptor());
                }
                if (ServiceUtils.isSchemaValidationEnabled(SchemaValidation.SchemaValidationType.IN, message) && var10_17 != null && var10_17.isEmpty()) {
                    throw new SoapFault(new Message("NO_SOAP_BODY", LOG, "no soap body"), soapVersion.getSender());
                }
            }
            if (!closeNeeded) return;
        }
        catch (XMLStreamException e) {
            try {
                throw new SoapFault(new Message("XML_STREAM_EXC", LOG, e.getMessage()), (Throwable)e, message.getVersion().getSender());
            }
            catch (Throwable throwable) {
                if (!closeNeeded) throw throwable;
                try {
                    StaxUtils.close(xmlReader);
                    throw throwable;
                }
                catch (XMLStreamException e2) {
                    throw new SoapFault(new Message("XML_STREAM_EXC", LOG, e2.getMessage()), (Throwable)e2, message.getVersion().getSender());
                }
            }
        }
        try {
            StaxUtils.close(xmlReader);
            return;
        }
        catch (XMLStreamException e) {
            throw new SoapFault(new Message("XML_STREAM_EXC", LOG, e.getMessage()), (Throwable)e, message.getVersion().getSender());
        }
    }

    private void addCurrentNamespaceDecls(XMLStreamReader xmlReader, Map<String, String> bodyNsMap) {
        for (int i = 0; i < xmlReader.getNamespaceCount(); ++i) {
            String nsuri = xmlReader.getNamespaceURI(i);
            if ("".equals(nsuri)) {
                bodyNsMap.remove("");
                continue;
            }
            bodyNsMap.put(xmlReader.getNamespacePrefix(i), nsuri);
        }
    }

    private static class HeadersProcessor {
        private static XMLEventFactory eventFactory;
        private final String ns;
        private final String header;
        private final String body;
        private final String envelope;
        private final List<XMLEvent> events = new ArrayList<XMLEvent>(8);
        private List<XMLEvent> envEvents;
        private List<XMLEvent> bodyEvents;
        private StaxUtils.StreamToDOMContext context;
        private Document doc;
        private Node parent;
        private QName lastStartElementQName;
        private String envelopePrefix;
        private String bodyPrefix;

        HeadersProcessor(SoapVersion version) {
            this.header = version.getHeader().getLocalPart();
            this.ns = version.getEnvelope().getNamespaceURI();
            this.envelope = version.getEnvelope().getLocalPart();
            this.body = version.getBody().getLocalPart();
        }

        public Document process(XMLStreamReader reader) throws XMLStreamException {
            int read = 0;
            int event = reader.getEventType();
            while (reader.hasNext()) {
                switch (event) {
                    case 1: {
                        int i;
                        ++read;
                        this.addEvent(eventFactory.createStartElement(new QName(reader.getNamespaceURI(), reader.getLocalName(), reader.getPrefix()), null, null));
                        for (i = 0; i < reader.getNamespaceCount(); ++i) {
                            this.addEvent(eventFactory.createNamespace(reader.getNamespacePrefix(i), reader.getNamespaceURI(i)));
                        }
                        for (i = 0; i < reader.getAttributeCount(); ++i) {
                            this.addEvent(eventFactory.createAttribute(reader.getAttributePrefix(i), reader.getAttributeNamespace(i), reader.getAttributeLocalName(i), reader.getAttributeValue(i)));
                        }
                        if (this.doc == null) break;
                        StaxUtils.readDocElements(this.doc, this.parent, reader, this.context);
                        break;
                    }
                    case 2: {
                        if (read > 0) {
                            this.addEvent(eventFactory.createEndElement(new QName(reader.getNamespaceURI(), reader.getLocalName(), reader.getPrefix()), null));
                        }
                        --read;
                        break;
                    }
                    case 4: {
                        String s2 = reader.getText();
                        if (s2 == null) break;
                        this.addEvent(eventFactory.createCharacters(s2));
                        break;
                    }
                    case 5: {
                        this.addEvent(eventFactory.createComment(reader.getText()));
                        break;
                    }
                    case 12: {
                        this.addEvent(eventFactory.createCData(reader.getText()));
                        break;
                    }
                    case 7: 
                    case 8: 
                    case 10: 
                    case 13: {
                        break;
                    }
                }
                event = reader.next();
            }
            return this.doc;
        }

        private void addEvent(XMLEvent event) {
            if (event.isStartElement()) {
                this.lastStartElementQName = event.asStartElement().getName();
                if (this.header.equals(this.lastStartElementQName.getLocalPart()) && this.ns.equals(this.lastStartElementQName.getNamespaceURI())) {
                    this.context = new StaxUtils.StreamToDOMContext(true, false, false);
                    this.doc = DOMUtils.createDocument();
                    this.parent = this.doc;
                    try {
                        for (XMLEvent ev : this.events) {
                            this.parent = StaxUtils.readDocElement(this.doc, this.parent, ev, this.context);
                        }
                    }
                    catch (XMLStreamException e) {
                        throw new Fault(e);
                    }
                } else {
                    if (this.ns.equals(this.lastStartElementQName.getNamespaceURI())) {
                        if (this.body.equals(this.lastStartElementQName.getLocalPart())) {
                            this.bodyPrefix = this.lastStartElementQName.getPrefix();
                        } else if (this.envelope.equals(this.lastStartElementQName.getLocalPart())) {
                            this.envelopePrefix = this.lastStartElementQName.getPrefix();
                        }
                    }
                    this.events.add(event);
                }
            } else {
                if (event.isNamespace() || event.isAttribute()) {
                    String lastEl = this.lastStartElementQName.getLocalPart();
                    if (this.body.equals(lastEl) && this.ns.equals(this.lastStartElementQName.getNamespaceURI())) {
                        if (this.bodyEvents == null) {
                            this.bodyEvents = new ArrayList<XMLEvent>();
                        }
                        this.bodyEvents.add(event);
                    } else if (this.envelope.equals(lastEl) && this.ns.equals(this.lastStartElementQName.getNamespaceURI())) {
                        if (this.envEvents == null) {
                            this.envEvents = new ArrayList<XMLEvent>();
                        }
                        this.envEvents.add(event);
                    }
                }
                this.events.add(event);
            }
        }

        public List<XMLEvent> getBodyAttributeAndNamespaceEvents() {
            if (this.bodyEvents == null) {
                return Collections.emptyList();
            }
            return Collections.unmodifiableList(this.bodyEvents);
        }

        public List<XMLEvent> getEnvAttributeAndNamespaceEvents() {
            if (this.envEvents == null) {
                return Collections.emptyList();
            }
            return Collections.unmodifiableList(this.envEvents);
        }

        public String getEnvelopePrefix() {
            return this.envelopePrefix;
        }

        public String getBodyPrefix() {
            return this.bodyPrefix;
        }

        static {
            try {
                eventFactory = XMLEventFactory.newInstance();
            }
            catch (Throwable t2) {
                eventFactory = StaxUtils.createWoodstoxEventFactory();
            }
        }
    }

    public static class CheckClosingTagsInterceptor
    extends AbstractSoapInterceptor {
        public CheckClosingTagsInterceptor() {
            super("post-logical");
        }

        @Override
        public void handleMessage(SoapMessage message) throws Fault {
            XMLStreamReader xmlReader = message.getContent(XMLStreamReader.class);
            if (xmlReader != null) {
                try {
                    while (xmlReader.hasNext()) {
                        if (xmlReader.next() != 8) continue;
                        return;
                    }
                }
                catch (XMLStreamException e) {
                    throw new SoapFault(e.getMessage(), (Throwable)e, message.getVersion().getSender());
                }
            }
        }
    }
}

