/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.staxutils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.Location;
import javax.xml.stream.StreamFilter;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLResolver;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.DTD;
import javax.xml.stream.events.EntityReference;
import javax.xml.stream.events.Namespace;
import javax.xml.stream.events.StartDocument;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stax.StAXSource;
import javax.xml.transform.stream.StreamSource;
import org.apache.cxf.common.classloader.ClassLoaderUtils;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.common.util.PropertyUtils;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.common.util.SystemPropertyAction;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.message.Message;
import org.apache.cxf.staxutils.DepthExceededStaxException;
import org.apache.cxf.staxutils.DepthRestrictingStreamReader;
import org.apache.cxf.staxutils.DepthXMLStreamReader;
import org.apache.cxf.staxutils.DocumentDepthProperties;
import org.apache.cxf.staxutils.PrettyPrintXMLStreamWriter;
import org.apache.cxf.staxutils.StaxSource;
import org.apache.cxf.staxutils.StreamWriterContentHandler;
import org.apache.cxf.staxutils.W3CDOMStreamReader;
import org.apache.cxf.staxutils.W3CDOMStreamWriter;
import org.apache.cxf.staxutils.WoodstoxHelper;
import org.w3c.dom.Attr;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;
import org.w3c.dom.UserDataHandler;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

public final class StaxUtils {
    public static final String MAX_CHILD_ELEMENTS = "org.apache.cxf.stax.maxChildElements";
    public static final String MAX_ELEMENT_DEPTH = "org.apache.cxf.stax.maxElementDepth";
    public static final String MAX_ATTRIBUTE_COUNT = "org.apache.cxf.stax.maxAttributeCount";
    public static final String MAX_ATTRIBUTE_SIZE = "org.apache.cxf.stax.maxAttributeSize";
    public static final String MAX_TEXT_LENGTH = "org.apache.cxf.stax.maxTextLength";
    public static final String MIN_TEXT_SEGMENT = "org.apache.cxf.stax.minTextSegment";
    public static final String MAX_ELEMENT_COUNT = "org.apache.cxf.stax.maxElementCount";
    public static final String MAX_XML_CHARACTERS = "org.apache.cxf.stax.maxXMLCharacters";
    public static final String ALLOW_INSECURE_PARSER = "org.apache.cxf.stax.allowInsecureParser";
    private static final String INNER_ELEMENT_COUNT_SYSTEM_PROP = "org.apache.cxf.staxutils.innerElementCountThreshold";
    private static final String INNER_ELEMENT_LEVEL_SYSTEM_PROP = "org.apache.cxf.staxutils.innerElementLevelThreshold";
    private static final Logger LOG = LogUtils.getL7dLogger(StaxUtils.class);
    private static final Queue<XMLInputFactory> NS_AWARE_INPUT_FACTORY_POOL;
    private static final XMLInputFactory SAFE_INPUT_FACTORY;
    private static final Queue<XMLOutputFactory> OUTPUT_FACTORY_POOL;
    private static final XMLOutputFactory SAFE_OUTPUT_FACTORY;
    private static final String XML_NS = "http://www.w3.org/2000/xmlns/";
    private static final String[] DEF_PREFIXES;
    private static int innerElementLevelThreshold;
    private static int innerElementCountThreshold;
    private static int maxAttributeCount;
    private static int maxAttributeSize;
    private static int maxTextLength;
    private static int minTextSegment;
    private static long maxElementCount;
    private static long maxXMLCharacters;
    private static boolean allowInsecureParser;

    private StaxUtils() {
    }

    private static int getInteger(String prop, int def) {
        try {
            String s2 = SystemPropertyAction.getPropertyOrNull(prop);
            if (StringUtils.isEmpty(s2)) {
                return def;
            }
            int i = Integer.parseInt(s2);
            if (i < 0) {
                i = def;
            }
            return i;
        }
        catch (Throwable throwable) {
            return def;
        }
    }

    private static long getLong(String prop, long def) {
        try {
            String s2 = SystemPropertyAction.getPropertyOrNull(prop);
            if (StringUtils.isEmpty(s2)) {
                return def;
            }
            long i = Long.parseLong(s2);
            if (i < 0L) {
                i = def;
            }
            return i;
        }
        catch (Throwable throwable) {
            return def;
        }
    }

    public static void setInnerElementLevelThreshold(int i) {
        innerElementLevelThreshold = i != -1 ? i : 500;
        StaxUtils.setProperty(SAFE_INPUT_FACTORY, "com.ctc.wstx.maxElementDepth", (Object)innerElementLevelThreshold);
    }

    public static void setInnerElementCountThreshold(int i) {
        innerElementCountThreshold = i != -1 ? i : 50000;
        StaxUtils.setProperty(SAFE_INPUT_FACTORY, "com.ctc.wstx.maxChildrenPerElement", (Object)innerElementCountThreshold);
    }

    public static boolean isWoodstox() {
        try {
            ClassLoaderUtils.loadClass("org.codehaus.stax2.XMLStreamReader2", StaxUtils.class);
        }
        catch (ClassNotFoundException e) {
            return false;
        }
        return true;
    }

    private static XMLInputFactory getXMLInputFactory() {
        if (SAFE_INPUT_FACTORY != null) {
            return SAFE_INPUT_FACTORY;
        }
        XMLInputFactory f = NS_AWARE_INPUT_FACTORY_POOL.poll();
        if (f == null) {
            f = StaxUtils.createXMLInputFactory(true);
        }
        return f;
    }

    private static void returnXMLInputFactory(XMLInputFactory factory2) {
        if (SAFE_INPUT_FACTORY != factory2) {
            NS_AWARE_INPUT_FACTORY_POOL.offer(factory2);
        }
    }

    private static XMLOutputFactory getXMLOutputFactory() {
        if (SAFE_OUTPUT_FACTORY != null) {
            return SAFE_OUTPUT_FACTORY;
        }
        XMLOutputFactory f = OUTPUT_FACTORY_POOL.poll();
        if (f == null) {
            f = XMLOutputFactory.newInstance();
        }
        return f;
    }

    private static void returnXMLOutputFactory(XMLOutputFactory factory2) {
        if (SAFE_OUTPUT_FACTORY != factory2) {
            OUTPUT_FACTORY_POOL.offer(factory2);
        }
    }

    public static XMLInputFactory createXMLInputFactory(boolean nsAware) {
        XMLInputFactory factory2 = null;
        try {
            factory2 = XMLInputFactory.newInstance();
        }
        catch (Throwable t2) {
            if (LOG.isLoggable(Level.FINE)) {
                LOG.log(Level.FINE, "XMLInputFactory.newInstance() failed with: ", t2);
            }
            factory2 = null;
        }
        if (factory2 == null || !StaxUtils.setRestrictionProperties(factory2)) {
            block10: {
                try {
                    factory2 = StaxUtils.createWoodstoxFactory();
                }
                catch (Throwable t3) {
                    if (!LOG.isLoggable(Level.FINE)) break block10;
                    LOG.log(Level.FINE, "Cannot create Woodstox XMLInputFactory: ", t3);
                }
            }
            if (factory2 == null) {
                throw new RuntimeException("Failed to create XMLInputFactory.");
            }
            if (!StaxUtils.setRestrictionProperties(factory2)) {
                if (allowInsecureParser) {
                    LOG.log(Level.WARNING, "INSECURE_PARSER_DETECTED", factory2.getClass().getName());
                } else {
                    throw new RuntimeException("Cannot create a secure XMLInputFactory, you should either add woodstox or set org.apache.cxf.stax.allowInsecureParser system property to true if an unsafe mode is acceptable.");
                }
            }
        }
        StaxUtils.setProperty(factory2, "javax.xml.stream.isNamespaceAware", (Object)nsAware);
        StaxUtils.setProperty(factory2, "javax.xml.stream.supportDTD", (Object)Boolean.FALSE);
        StaxUtils.setProperty(factory2, "javax.xml.stream.isReplacingEntityReferences", (Object)Boolean.FALSE);
        StaxUtils.setProperty(factory2, "javax.xml.stream.isSupportingExternalEntities", (Object)Boolean.FALSE);
        factory2.setXMLResolver(new XMLResolver(){

            @Override
            public Object resolveEntity(String publicID, String systemID, String baseURI, String namespace) throws XMLStreamException {
                throw new XMLStreamException("Reading external entities is disabled");
            }
        });
        return factory2;
    }

    private static XMLInputFactory createWoodstoxFactory() {
        return WoodstoxHelper.createInputFactory();
    }

    public static XMLEventFactory createWoodstoxEventFactory() {
        return WoodstoxHelper.createEventFactory();
    }

    private static boolean setRestrictionProperties(XMLInputFactory factory2) {
        boolean wstxMaxs = StaxUtils.setProperty(factory2, "com.ctc.wstx.maxAttributesPerElement", (Object)maxAttributeCount) && StaxUtils.setProperty(factory2, "com.ctc.wstx.maxAttributeSize", (Object)maxAttributeSize) && StaxUtils.setProperty(factory2, "com.ctc.wstx.maxChildrenPerElement", (Object)innerElementCountThreshold) && StaxUtils.setProperty(factory2, "com.ctc.wstx.maxElementCount", (Object)maxElementCount) && StaxUtils.setProperty(factory2, "com.ctc.wstx.maxElementDepth", (Object)innerElementLevelThreshold) && StaxUtils.setProperty(factory2, "com.ctc.wstx.maxCharacters", (Object)maxXMLCharacters) && StaxUtils.setProperty(factory2, "com.ctc.wstx.maxTextLength", (Object)maxTextLength);
        return wstxMaxs && StaxUtils.setProperty(factory2, "com.ctc.wstx.minTextSegment", (Object)minTextSegment);
    }

    private static boolean setProperty(XMLInputFactory f, String p, Object o) {
        try {
            f.setProperty(p, o);
            return true;
        }
        catch (Throwable throwable) {
            return false;
        }
    }

    public static XMLStreamWriter createXMLStreamWriter(Writer out) {
        XMLOutputFactory factory2 = StaxUtils.getXMLOutputFactory();
        try {
            XMLStreamWriter xMLStreamWriter = factory2.createXMLStreamWriter(out);
            return xMLStreamWriter;
        }
        catch (XMLStreamException e) {
            throw new RuntimeException("Cant' create XMLStreamWriter", e);
        }
        finally {
            StaxUtils.returnXMLOutputFactory(factory2);
        }
    }

    public static XMLStreamWriter createXMLStreamWriter(OutputStream out) {
        return StaxUtils.createXMLStreamWriter(out, null);
    }

    public static XMLStreamWriter createXMLStreamWriter(OutputStream out, String encoding) {
        XMLOutputFactory factory2 = StaxUtils.getXMLOutputFactory();
        try {
            XMLStreamWriter xMLStreamWriter = factory2.createXMLStreamWriter(out, encoding != null ? encoding : StandardCharsets.UTF_8.name());
            return xMLStreamWriter;
        }
        catch (XMLStreamException e) {
            throw new RuntimeException("Cant' create XMLStreamWriter", e);
        }
        finally {
            StaxUtils.returnXMLOutputFactory(factory2);
        }
    }

    public static XMLStreamWriter createXMLStreamWriter(Result r) {
        Object nd;
        if (r instanceof DOMResult) {
            DOMResult dr = (DOMResult)r;
            nd = dr.getNode();
            if (nd instanceof Document) {
                return new W3CDOMStreamWriter((Document)nd);
            }
            if (nd instanceof Element) {
                return new W3CDOMStreamWriter((Element)nd);
            }
            if (nd instanceof DocumentFragment) {
                return new W3CDOMStreamWriter((DocumentFragment)nd);
            }
        }
        XMLOutputFactory factory2 = StaxUtils.getXMLOutputFactory();
        try {
            nd = factory2.createXMLStreamWriter(r);
            return nd;
        }
        catch (XMLStreamException e) {
            throw new RuntimeException("Cant' create XMLStreamWriter", e);
        }
        finally {
            StaxUtils.returnXMLOutputFactory(factory2);
        }
    }

    public static XMLStreamReader createFilteredReader(XMLStreamReader reader, StreamFilter filter2) {
        XMLInputFactory factory2 = StaxUtils.getXMLInputFactory();
        try {
            XMLStreamReader xMLStreamReader = factory2.createFilteredReader(reader, filter2);
            return xMLStreamReader;
        }
        catch (XMLStreamException e) {
            throw new RuntimeException("Cant' create XMLStreamReader", e);
        }
        finally {
            StaxUtils.returnXMLInputFactory(factory2);
        }
    }

    public static void nextEvent(XMLStreamReader dr) {
        try {
            dr.next();
        }
        catch (XMLStreamException e) {
            throw new RuntimeException("Couldn't parse stream.", e);
        }
    }

    public static boolean toNextText(DepthXMLStreamReader reader) {
        if (reader.getEventType() == 4) {
            return true;
        }
        try {
            int depth = reader.getDepth();
            int event = reader.getEventType();
            while (reader.getDepth() >= depth && reader.hasNext()) {
                if (event == 4 && reader.getDepth() == depth + 1) {
                    return true;
                }
                event = reader.next();
            }
            return false;
        }
        catch (XMLStreamException e) {
            throw new RuntimeException("Couldn't parse stream.", e);
        }
    }

    public static boolean toNextTag(XMLStreamReader reader) {
        try {
            int x = reader.getEventType();
            while (x != 1 && x != 2 && reader.hasNext()) {
                x = reader.next();
            }
        }
        catch (XMLStreamException e) {
            throw new RuntimeException("Couldn't parse stream.", e);
        }
        return true;
    }

    public static boolean toNextTag(DepthXMLStreamReader reader, QName endTag) {
        try {
            int depth = reader.getDepth();
            int event = reader.getEventType();
            while (reader.getDepth() >= depth && reader.hasNext()) {
                if (event == 1 && reader.getName().equals(endTag) && reader.getDepth() == depth + 1) {
                    return true;
                }
                event = reader.next();
            }
            return false;
        }
        catch (XMLStreamException e) {
            throw new RuntimeException("Couldn't parse stream.", e);
        }
    }

    public static void writeStartElement(XMLStreamWriter writer, String prefix, String name, String namespace) throws XMLStreamException {
        if (prefix == null) {
            prefix = "";
        }
        if (!namespace.isEmpty()) {
            writer.writeStartElement(prefix, name, namespace);
            if (!prefix.isEmpty()) {
                writer.writeNamespace(prefix, namespace);
                writer.setPrefix(prefix, namespace);
            } else {
                writer.writeDefaultNamespace(namespace);
                writer.setDefaultNamespace(namespace);
            }
        } else {
            writer.writeStartElement(name);
            writer.writeDefaultNamespace("");
            writer.setDefaultNamespace("");
        }
    }

    public static boolean skipToStartOfElement(XMLStreamReader in) throws XMLStreamException {
        int code = in.getEventType();
        while (code != 8) {
            if (code == 1) {
                return true;
            }
            code = in.next();
        }
        return false;
    }

    public static boolean toNextElement(DepthXMLStreamReader dr) {
        if (dr.getEventType() == 1) {
            return true;
        }
        if (dr.getEventType() == 2) {
            return false;
        }
        try {
            int depth = dr.getDepth();
            int event = dr.getEventType();
            while (dr.getDepth() >= depth && dr.hasNext()) {
                if (event == 1 && dr.getDepth() == depth + 1) {
                    return true;
                }
                if (event == 2) {
                    --depth;
                }
                event = dr.next();
            }
            return false;
        }
        catch (XMLStreamException e) {
            throw new RuntimeException("Couldn't parse stream.", e);
        }
    }

    public static boolean skipToStartOfElement(DepthXMLStreamReader in) throws XMLStreamException {
        int code = in.getEventType();
        while (code != 8) {
            if (code == 1) {
                return true;
            }
            code = in.next();
        }
        return false;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void copy(Source source2, OutputStream os) throws XMLStreamException {
        XMLStreamWriter writer = StaxUtils.createXMLStreamWriter(os);
        try {
            StaxUtils.copy(source2, writer);
        }
        finally {
            try {
                writer.flush();
            }
            catch (XMLStreamException xMLStreamException) {}
            StaxUtils.close(writer);
        }
    }

    public static void copy(Source source2, XMLStreamWriter writer) throws XMLStreamException {
        Source ss;
        if (source2 instanceof StaxSource) {
            ss = (StaxSource)source2;
            if (((StaxSource)ss).getXMLStreamReader() == null) {
                return;
            }
        } else if (source2 instanceof StAXSource) {
            ss = (StAXSource)source2;
            if (((StAXSource)ss).getXMLStreamReader() == null) {
                return;
            }
        } else if (source2 instanceof SAXSource) {
            ss = (SAXSource)source2;
            InputSource src = ((SAXSource)ss).getInputSource();
            if (src == null || src.getSystemId() == null && src.getPublicId() == null) {
                if (((SAXSource)ss).getXMLReader() != null) {
                    StreamWriterContentHandler ch = new StreamWriterContentHandler(writer);
                    XMLReader reader = ((SAXSource)source2).getXMLReader();
                    reader.setContentHandler(ch);
                    try {
                        try {
                            reader.setFeature("http://xml.org/sax/features/namespaces", true);
                        }
                        catch (Throwable throwable) {
                            // empty catch block
                        }
                        try {
                            reader.setProperty("http://xml.org/sax/properties/lexical-handler", ch);
                        }
                        catch (Throwable throwable) {
                            // empty catch block
                        }
                        reader.parse(((SAXSource)source2).getInputSource());
                        return;
                    }
                    catch (Exception e) {
                        throw new XMLStreamException(e.getMessage(), e);
                    }
                }
                if (((SAXSource)ss).getInputSource() == null) {
                    return;
                }
            }
        } else if (source2 instanceof StreamSource && ((StreamSource)(ss = (StreamSource)source2)).getInputStream() == null && ((StreamSource)ss).getReader() == null && ((StreamSource)ss).getSystemId() == null) {
            return;
        }
        XMLStreamReader reader = StaxUtils.createXMLStreamReader(source2);
        StaxUtils.copy(reader, writer);
        reader.close();
    }

    public static Document copy(Document doc) throws XMLStreamException, ParserConfigurationException {
        XMLStreamReader reader = StaxUtils.createXMLStreamReader(doc);
        W3CDOMStreamWriter writer = new W3CDOMStreamWriter();
        StaxUtils.copy(reader, (XMLStreamWriter)writer);
        Document d = writer.getDocument();
        try {
            d.setDocumentURI(doc.getDocumentURI());
        }
        catch (Exception exception) {
            // empty catch block
        }
        return d;
    }

    public static void copy(Document doc, XMLStreamWriter writer) throws XMLStreamException {
        XMLStreamReader reader = StaxUtils.createXMLStreamReader(doc);
        StaxUtils.copy(reader, writer);
    }

    public static void copy(Element node, XMLStreamWriter writer) throws XMLStreamException {
        XMLStreamReader reader = StaxUtils.createXMLStreamReader(node);
        StaxUtils.copy(reader, writer);
    }

    public static void copy(XMLStreamReader reader, OutputStream os) throws XMLStreamException {
        XMLStreamWriter xsw = StaxUtils.createXMLStreamWriter(os);
        StaxUtils.copy(reader, xsw);
        xsw.close();
    }

    public static void writeTo(Node node, OutputStream os) throws XMLStreamException {
        StaxUtils.copy((Source)new DOMSource(node), os);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void writeTo(Node node, OutputStream os, int indent) throws XMLStreamException {
        if (indent > 0) {
            try (PrettyPrintXMLStreamWriter writer = new PrettyPrintXMLStreamWriter(StaxUtils.createXMLStreamWriter(os), indent);){
                StaxUtils.copy((Source)new DOMSource(node), (XMLStreamWriter)writer);
            }
        } else {
            StaxUtils.copy((Source)new DOMSource(node), os);
        }
    }

    public static void writeTo(Node node, Writer os) throws XMLStreamException {
        StaxUtils.writeTo(node, os, 0);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void writeTo(Node node, Writer os, int indent) throws XMLStreamException {
        XMLStreamWriter writer = StaxUtils.createXMLStreamWriter(os);
        if (indent > 0) {
            writer = new PrettyPrintXMLStreamWriter(writer, indent);
        }
        try {
            StaxUtils.copy((Source)new DOMSource(node), writer);
        }
        finally {
            writer.close();
        }
    }

    public static void copy(XMLStreamReader reader, XMLStreamWriter writer) throws XMLStreamException {
        StaxUtils.copy(reader, writer, false, false);
    }

    public static void copy(XMLStreamReader reader, XMLStreamWriter writer, boolean fragment) throws XMLStreamException {
        StaxUtils.copy(reader, writer, fragment, false);
    }

    public static void copy(XMLStreamReader reader, XMLStreamWriter writer, boolean fragment, boolean isThreshold) throws XMLStreamException {
        int read = 0;
        int elementCount = 0;
        ArrayDeque<Integer> countStack = new ArrayDeque<Integer>();
        int event = reader.getEventType();
        while (reader.hasNext()) {
            switch (event) {
                case 1: {
                    ++read;
                    if (isThreshold) {
                        ++elementCount;
                        if (innerElementLevelThreshold != -1 && read >= innerElementLevelThreshold) {
                            throw new DepthExceededStaxException("reach the innerElementLevelThreshold:" + innerElementLevelThreshold);
                        }
                        if (innerElementCountThreshold != -1 && elementCount >= innerElementCountThreshold) {
                            throw new DepthExceededStaxException("reach the innerElementCountThreshold:" + innerElementCountThreshold);
                        }
                        countStack.push(elementCount);
                        elementCount = 0;
                    }
                    StaxUtils.writeStartElement(reader, writer);
                    break;
                }
                case 2: {
                    if (read > 0) {
                        writer.writeEndElement();
                    }
                    if (--read < 0 && fragment) {
                        return;
                    }
                    if (!isThreshold || countStack.isEmpty()) break;
                    elementCount = (Integer)countStack.pop();
                    break;
                }
                case 4: 
                case 6: {
                    String s2 = reader.getText();
                    if (s2 == null) break;
                    writer.writeCharacters(s2);
                    break;
                }
                case 5: {
                    writer.writeComment(reader.getText());
                    break;
                }
                case 12: {
                    writer.writeCData(reader.getText());
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
    }

    private static void writeStartElement(XMLStreamReader reader, XMLStreamWriter writer) throws XMLStreamException {
        String nsPrefix;
        int i;
        String uri = reader.getNamespaceURI();
        String prefix = reader.getPrefix();
        String local = reader.getLocalName();
        if (prefix == null) {
            prefix = "";
        }
        boolean writeElementNS = false;
        if (uri != null) {
            writeElementNS = true;
            Iterator it = CastUtils.cast(writer.getNamespaceContext().getPrefixes(uri));
            if (!it.hasNext() && StringUtils.isEmpty(prefix) && StringUtils.isEmpty(uri) && StringUtils.isEmpty(writer.getNamespaceContext().getNamespaceURI(""))) {
                writeElementNS = false;
            }
            while (it.hasNext()) {
                String s2 = (String)it.next();
                if (s2 == null) {
                    s2 = "";
                }
                if (!s2.equals(prefix)) continue;
                writeElementNS = false;
            }
        }
        if (uri != null) {
            if (prefix.isEmpty() && StringUtils.isEmpty(uri)) {
                writer.writeStartElement(local);
            } else {
                writer.writeStartElement(prefix, local, uri);
            }
        } else {
            writer.writeStartElement(local);
        }
        for (i = 0; i < reader.getNamespaceCount(); ++i) {
            String nsURI = reader.getNamespaceURI(i);
            nsPrefix = reader.getNamespacePrefix(i);
            if (nsPrefix == null) {
                nsPrefix = "";
            }
            if (nsURI == null) {
                nsURI = "";
            }
            if (nsPrefix.isEmpty()) {
                writer.writeDefaultNamespace(nsURI);
                writer.setDefaultNamespace(nsURI);
            } else {
                writer.writeNamespace(nsPrefix, nsURI);
                writer.setPrefix(nsPrefix, nsURI);
            }
            if (!nsURI.equals(uri) || !nsPrefix.equals(prefix)) continue;
            writeElementNS = false;
        }
        if (writeElementNS) {
            if (prefix.isEmpty()) {
                writer.writeDefaultNamespace(uri);
                writer.setDefaultNamespace(uri);
            } else {
                writer.writeNamespace(prefix, uri);
                writer.setPrefix(prefix, uri);
            }
        }
        for (i = 0; i < reader.getAttributeCount(); ++i) {
            String ns = reader.getAttributeNamespace(i);
            nsPrefix = reader.getAttributePrefix(i);
            if (ns == null || ns.isEmpty()) {
                writer.writeAttribute(reader.getAttributeLocalName(i), reader.getAttributeValue(i));
                continue;
            }
            if (nsPrefix == null || nsPrefix.isEmpty()) {
                writer.writeAttribute(reader.getAttributeNamespace(i), reader.getAttributeLocalName(i), reader.getAttributeValue(i));
                continue;
            }
            Iterator it = CastUtils.cast(writer.getNamespaceContext().getPrefixes(ns));
            boolean writeNs = true;
            while (it != null && it.hasNext()) {
                String s3 = (String)it.next();
                if (s3 == null) {
                    s3 = "";
                }
                if (!s3.equals(nsPrefix)) continue;
                writeNs = false;
            }
            if (writeNs) {
                writer.writeNamespace(nsPrefix, ns);
                writer.setPrefix(nsPrefix, ns);
            }
            writer.writeAttribute(reader.getAttributePrefix(i), reader.getAttributeNamespace(i), reader.getAttributeLocalName(i), reader.getAttributeValue(i));
        }
    }

    public static void writeDocument(Document d, XMLStreamWriter writer, boolean repairing) throws XMLStreamException {
        StaxUtils.writeDocument(d, writer, true, repairing);
    }

    public static void writeDocument(Document d, XMLStreamWriter writer, boolean writeProlog, boolean repairing) throws XMLStreamException {
        if (writeProlog) {
            writer.writeStartDocument();
        }
        for (Node node = d.getFirstChild(); node != null; node = node.getNextSibling()) {
            if (!writeProlog && node.getNodeType() != 1) continue;
            StaxUtils.writeNode(node, writer, repairing);
        }
        if (writeProlog) {
            writer.writeEndDocument();
        }
    }

    public static void writeElement(Element e, XMLStreamWriter writer, boolean repairing) throws XMLStreamException {
        StaxUtils.writeElement(e, writer, repairing, true);
    }

    public static void writeElement(Element e, XMLStreamWriter writer, boolean repairing, boolean endElement) throws XMLStreamException {
        boolean declareNamespace;
        String prefix = e.getPrefix();
        String ns = e.getNamespaceURI();
        String localName = e.getLocalName();
        if (prefix == null) {
            prefix = "";
        }
        if (localName == null && (localName = e.getNodeName()) == null) {
            throw new IllegalStateException("Element's local name cannot be null!");
        }
        String decUri = writer.getNamespaceContext().getNamespaceURI(prefix);
        boolean bl = declareNamespace = decUri == null || !decUri.equals(ns);
        if (ns == null || ns.isEmpty()) {
            writer.writeStartElement(localName);
            if (StringUtils.isEmpty(decUri)) {
                declareNamespace = false;
            }
        } else {
            writer.writeStartElement(prefix, localName, ns);
        }
        for (Node attr : StaxUtils.sortElementAttributes(e.getAttributes())) {
            String name = attr.getLocalName();
            String attrPrefix = attr.getPrefix();
            if (attrPrefix == null) {
                attrPrefix = "";
            }
            if (name == null) {
                name = attr.getNodeName();
            }
            if ("xmlns".equals(attrPrefix)) {
                writer.writeNamespace(name, attr.getNodeValue());
                writer.setPrefix(name, attr.getNodeValue());
                if (!name.equals(prefix) || !attr.getNodeValue().equals(ns)) continue;
                declareNamespace = false;
                continue;
            }
            if ("xmlns".equals(name) && "".equals(attrPrefix)) {
                writer.writeDefaultNamespace(attr.getNodeValue());
                writer.setDefaultNamespace(attr.getNodeValue());
                if (attr.getNodeValue().equals(ns)) {
                    declareNamespace = false;
                    continue;
                }
                if (!StringUtils.isEmpty(attr.getNodeValue()) || !StringUtils.isEmpty(ns)) continue;
                declareNamespace = false;
                continue;
            }
            String attns = attr.getNamespaceURI();
            String value = attr.getNodeValue();
            if (attns == null || attns.isEmpty()) {
                writer.writeAttribute(name, value);
                continue;
            }
            if (attrPrefix.isEmpty()) {
                writer.writeAttribute(attns, name, value);
                continue;
            }
            if (repairing && writer.getNamespaceContext().getNamespaceURI(attrPrefix) == null) {
                writer.writeNamespace(attrPrefix, attns);
            }
            writer.writeAttribute(attrPrefix, attns, name, value);
        }
        if (declareNamespace && repairing) {
            if (ns == null) {
                writer.writeNamespace(prefix, "");
                writer.setPrefix(prefix, "");
            } else {
                writer.writeNamespace(prefix, ns);
                writer.setPrefix(prefix, ns);
            }
        }
        for (Node nd = e.getFirstChild(); nd != null; nd = nd.getNextSibling()) {
            StaxUtils.writeNode(nd, writer, repairing);
        }
        if (endElement) {
            writer.writeEndElement();
        }
    }

    private static List<Node> sortElementAttributes(NamedNodeMap attrs) {
        if (attrs.getLength() == 0) {
            return Collections.emptyList();
        }
        ArrayList<Node> sortedAttrs = new ArrayList<Node>(attrs.getLength());
        for (int i = 0; i < attrs.getLength(); ++i) {
            Node attr = attrs.item(i);
            String name = attr.getLocalName();
            if (name == null) {
                name = attr.getNodeName();
            }
            if ("xmlns".equals(attr.getPrefix()) || "xmlns".equals(name)) {
                sortedAttrs.add(0, attr);
                continue;
            }
            sortedAttrs.add(attr);
        }
        return sortedAttrs;
    }

    public static void writeNode(Node n, XMLStreamWriter writer, boolean repairing) throws XMLStreamException {
        switch (n.getNodeType()) {
            case 1: {
                StaxUtils.writeElement((Element)n, writer, repairing);
                break;
            }
            case 3: {
                writer.writeCharacters(((Text)n).getNodeValue());
                break;
            }
            case 8: {
                writer.writeComment(((Comment)n).getData());
                break;
            }
            case 4: {
                writer.writeCData(((CDATASection)n).getData());
                break;
            }
            case 5: {
                writer.writeEntityRef(((org.w3c.dom.EntityReference)n).getNodeValue());
                break;
            }
            case 7: {
                ProcessingInstruction pi = (ProcessingInstruction)n;
                writer.writeProcessingInstruction(pi.getTarget(), pi.getData());
                break;
            }
            case 9: {
                StaxUtils.writeDocument((Document)n, writer, repairing);
                break;
            }
            case 11: {
                DocumentFragment frag = (DocumentFragment)n;
                for (Node child = frag.getFirstChild(); child != null; child = child.getNextSibling()) {
                    StaxUtils.writeNode(child, writer, repairing);
                }
                break;
            }
            case 10: {
                try {
                    if (((DocumentType)n).getTextContent() == null) break;
                    writer.writeDTD(((DocumentType)n).getTextContent());
                }
                catch (UnsupportedOperationException unsupportedOperationException) {}
                break;
            }
            default: {
                throw new IllegalStateException("Found type: " + n.getClass().getName());
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static Document read(Source s2) throws XMLStreamException {
        XMLStreamReader reader = StaxUtils.createXMLStreamReader(s2);
        try {
            Document document = StaxUtils.read(reader);
            return document;
        }
        finally {
            try {
                reader.close();
            }
            catch (Exception exception) {}
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static Document read(InputStream s2) throws XMLStreamException {
        XMLStreamReader reader = StaxUtils.createXMLStreamReader(s2);
        try {
            Document document = StaxUtils.read(reader);
            return document;
        }
        finally {
            try {
                reader.close();
            }
            catch (Exception exception) {}
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static Document read(Reader s2) throws XMLStreamException {
        XMLStreamReader reader = StaxUtils.createXMLStreamReader(s2);
        try {
            Document document = StaxUtils.read(reader);
            return document;
        }
        finally {
            try {
                reader.close();
            }
            catch (Exception exception) {}
        }
    }

    public static Document read(File is) throws XMLStreamException, IOException {
        try (InputStream fin = Files.newInputStream(is.toPath(), new OpenOption[0]);){
            Document document = StaxUtils.read(fin);
            return document;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static Document read(InputSource s2) throws XMLStreamException {
        XMLStreamReader reader = StaxUtils.createXMLStreamReader(s2);
        try {
            Document document = StaxUtils.read(reader);
            return document;
        }
        finally {
            try {
                reader.close();
            }
            catch (Exception exception) {}
        }
    }

    public static Document read(XMLStreamReader reader) throws XMLStreamException {
        return StaxUtils.read(reader, false);
    }

    public static Document read(XMLStreamReader reader, boolean recordLoc) throws XMLStreamException {
        Document doc = DOMUtils.createDocument();
        if (reader.getLocation().getSystemId() != null) {
            try {
                doc.setDocumentURI(reader.getLocation().getSystemId());
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        StaxUtils.readDocElements(doc, doc, reader, true, recordLoc);
        return doc;
    }

    public static Document read(DocumentBuilder builder, XMLStreamReader reader, boolean repairing) throws XMLStreamException {
        Document doc;
        Document document = doc = builder == null ? DOMUtils.createDocument() : builder.newDocument();
        if (reader.getLocation().getSystemId() != null) {
            try {
                doc.setDocumentURI(reader.getLocation().getSystemId());
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        StaxUtils.readDocElements(doc, reader, repairing);
        return doc;
    }

    private static Document getDocument(Node parent) {
        return parent instanceof Document ? (Document)parent : parent.getOwnerDocument();
    }

    private static boolean isDeclared(Element e, String namespaceURI, String prefix) {
        while (e != null) {
            Attr att = prefix != null && !prefix.isEmpty() ? e.getAttributeNodeNS(XML_NS, prefix) : e.getAttributeNode("xmlns");
            if (att != null && att.getNodeValue().equals(namespaceURI)) {
                return true;
            }
            if (e.getParentNode() instanceof Element) {
                e = (Element)e.getParentNode();
                continue;
            }
            if (!StringUtils.isEmpty(prefix) || !StringUtils.isEmpty(namespaceURI)) break;
            return true;
        }
        return false;
    }

    public static void readDocElements(Node parent, XMLStreamReader reader, boolean repairing) throws XMLStreamException {
        Document doc = StaxUtils.getDocument(parent);
        StaxUtils.readDocElements(doc, parent, reader, repairing, false);
    }

    public static void readDocElements(Node parent, XMLStreamReader reader, boolean repairing, boolean isThreshold) throws XMLStreamException {
        Document doc = StaxUtils.getDocument(parent);
        StaxUtils.readDocElements(doc, parent, reader, repairing, false, isThreshold);
    }

    public static void readDocElements(Document doc, Node parent, XMLStreamReader reader, boolean repairing, boolean recordLoc) throws XMLStreamException {
        StaxUtils.readDocElements(doc, parent, reader, repairing, recordLoc, false);
    }

    public static void readDocElements(Document doc, Node parent, XMLStreamReader reader, boolean repairing, boolean recordLoc, boolean isThreshold) throws XMLStreamException {
        ArrayDeque<Node> stack = new ArrayDeque<Node>();
        int event = reader.getEventType();
        int elementCount = 0;
        while (reader.hasNext()) {
            switch (event) {
                case 1: {
                    String prefix;
                    ++elementCount;
                    Element e = !StringUtils.isEmpty(reader.getPrefix()) ? doc.createElementNS(reader.getNamespaceURI(), reader.getPrefix() + ':' + reader.getLocalName()) : doc.createElementNS(reader.getNamespaceURI(), reader.getLocalName());
                    e = (Element)parent.appendChild(e);
                    recordLoc = StaxUtils.addLocation(doc, (Node)e, reader, recordLoc);
                    for (int ns = 0; ns < reader.getNamespaceCount(); ++ns) {
                        String uri = reader.getNamespaceURI(ns);
                        prefix = reader.getNamespacePrefix(ns);
                        StaxUtils.declare(e, uri, prefix);
                    }
                    for (int att = 0; att < reader.getAttributeCount(); ++att) {
                        String name = reader.getAttributeLocalName(att);
                        prefix = reader.getAttributePrefix(att);
                        if (prefix != null && !prefix.isEmpty()) {
                            name = prefix + ':' + name;
                        }
                        Attr attr = doc.createAttributeNS(reader.getAttributeNamespace(att), name);
                        attr.setValue(reader.getAttributeValue(att));
                        e.setAttributeNode(attr);
                    }
                    if (repairing && !StaxUtils.isDeclared(e, reader.getNamespaceURI(), reader.getPrefix())) {
                        StaxUtils.declare(e, reader.getNamespaceURI(), reader.getPrefix());
                    }
                    stack.push(parent);
                    if (isThreshold && innerElementLevelThreshold != -1 && stack.size() >= innerElementLevelThreshold) {
                        throw new DepthExceededStaxException("reach the innerElementLevelThreshold:" + innerElementLevelThreshold);
                    }
                    if (isThreshold && innerElementCountThreshold != -1 && elementCount >= innerElementCountThreshold) {
                        throw new DepthExceededStaxException("reach the innerElementCountThreshold:" + innerElementCountThreshold);
                    }
                    parent = e;
                    break;
                }
                case 2: {
                    if (stack.isEmpty()) {
                        return;
                    }
                    parent = (Node)stack.pop();
                    if (!(parent instanceof Document) && !(parent instanceof DocumentFragment)) break;
                    return;
                }
                case 13: {
                    break;
                }
                case 10: {
                    break;
                }
                case 4: {
                    if (parent == null) break;
                    recordLoc = StaxUtils.addLocation(doc, parent.appendChild(doc.createTextNode(reader.getText())), reader, recordLoc);
                    break;
                }
                case 5: {
                    if (parent == null) break;
                    parent.appendChild(doc.createComment(reader.getText()));
                    break;
                }
                case 12: {
                    recordLoc = StaxUtils.addLocation(doc, parent.appendChild(doc.createCDATASection(reader.getText())), reader, recordLoc);
                    break;
                }
                case 3: {
                    parent.appendChild(doc.createProcessingInstruction(reader.getPITarget(), reader.getPIData()));
                    break;
                }
                case 9: {
                    parent.appendChild(doc.createProcessingInstruction(reader.getPITarget(), reader.getPIData()));
                    break;
                }
            }
            if (!reader.hasNext()) continue;
            event = reader.next();
        }
    }

    public static void readDocElements(Document doc, Node parent, XMLStreamReader reader, StreamToDOMContext context) throws XMLStreamException {
        int event = reader.getEventType();
        while (reader.hasNext()) {
            switch (event) {
                case 1: {
                    String prefix;
                    context.incrementCount();
                    Element e = !StringUtils.isEmpty(reader.getPrefix()) ? doc.createElementNS(reader.getNamespaceURI(), reader.getPrefix() + ":" + reader.getLocalName()) : doc.createElementNS(reader.getNamespaceURI(), reader.getLocalName());
                    e = (Element)parent.appendChild(e);
                    if (context.isRecordLoc()) {
                        context.setRecordLoc(StaxUtils.addLocation(doc, (Node)e, reader.getLocation(), context.isRecordLoc()));
                    }
                    for (int ns = 0; ns < reader.getNamespaceCount(); ++ns) {
                        String uri = reader.getNamespaceURI(ns);
                        prefix = reader.getNamespacePrefix(ns);
                        StaxUtils.declare(e, uri, prefix);
                    }
                    for (int att = 0; att < reader.getAttributeCount(); ++att) {
                        String name = reader.getAttributeLocalName(att);
                        prefix = reader.getAttributePrefix(att);
                        if (prefix != null && prefix.length() > 0) {
                            name = prefix + ":" + name;
                        }
                        Attr attr = doc.createAttributeNS(reader.getAttributeNamespace(att), name);
                        attr.setValue(reader.getAttributeValue(att));
                        e.setAttributeNode(attr);
                    }
                    if (context.isRepairing() && !StaxUtils.isDeclared(e, reader.getNamespaceURI(), reader.getPrefix())) {
                        StaxUtils.declare(e, reader.getNamespaceURI(), reader.getPrefix());
                    }
                    context.pushToStack(parent);
                    if (context.isThreshold() && innerElementLevelThreshold != -1 && context.getStackSize() >= innerElementLevelThreshold) {
                        throw new DepthExceededStaxException("reach the innerElementLevelThreshold:" + innerElementLevelThreshold);
                    }
                    if (context.isThreshold() && innerElementCountThreshold != -1 && context.getCount() >= innerElementCountThreshold) {
                        throw new DepthExceededStaxException("reach the innerElementCountThreshold:" + innerElementCountThreshold);
                    }
                    parent = e;
                    break;
                }
                case 2: {
                    if (context.isStackEmpty()) {
                        return;
                    }
                    parent = context.popFromStack();
                    if (!(parent instanceof Document) && !(parent instanceof DocumentFragment)) break;
                    return;
                }
                case 13: {
                    break;
                }
                case 10: {
                    break;
                }
                case 4: {
                    if (parent == null) break;
                    context.setRecordLoc(StaxUtils.addLocation(doc, parent.appendChild(doc.createTextNode(reader.getText())), reader.getLocation(), context.isRecordLoc()));
                    break;
                }
                case 5: {
                    if (parent == null) break;
                    parent.appendChild(doc.createComment(reader.getText()));
                    break;
                }
                case 12: {
                    context.setRecordLoc(StaxUtils.addLocation(doc, parent.appendChild(doc.createCDATASection(reader.getText())), reader.getLocation(), context.isRecordLoc()));
                    break;
                }
                case 3: {
                    parent.appendChild(doc.createProcessingInstruction(reader.getPITarget(), reader.getPIData()));
                    break;
                }
                case 9: {
                    parent.appendChild(doc.createProcessingInstruction(reader.getPITarget(), reader.getPIData()));
                    break;
                }
            }
            if (!reader.hasNext()) continue;
            event = reader.next();
        }
    }

    public static Node readDocElement(Document doc, Node parent, XMLEvent ev, StreamToDOMContext context) throws XMLStreamException {
        switch (ev.getEventType()) {
            case 1: {
                context.incrementCount();
                StartElement startElem = ev.asStartElement();
                QName name = startElem.getName();
                Element e = !StringUtils.isEmpty(name.getPrefix()) ? doc.createElementNS(name.getNamespaceURI(), name.getPrefix() + ":" + name.getLocalPart()) : doc.createElementNS(name.getNamespaceURI(), name.getLocalPart());
                e = (Element)parent.appendChild(e);
                if (context.isRecordLoc()) {
                    context.setRecordLoc(StaxUtils.addLocation(doc, (Node)e, startElem.getLocation(), context.isRecordLoc()));
                }
                if (context.isRepairing() && !StaxUtils.isDeclared(e, name.getNamespaceURI(), name.getPrefix())) {
                    StaxUtils.declare(e, name.getNamespaceURI(), name.getPrefix());
                }
                context.pushToStack(parent);
                if (context.isThreshold() && innerElementLevelThreshold != -1 && context.getStackSize() >= innerElementLevelThreshold) {
                    throw new DepthExceededStaxException("reach the innerElementLevelThreshold:" + innerElementLevelThreshold);
                }
                if (context.isThreshold() && innerElementCountThreshold != -1 && context.getCount() >= innerElementCountThreshold) {
                    throw new DepthExceededStaxException("reach the innerElementCountThreshold:" + innerElementCountThreshold);
                }
                parent = e;
                break;
            }
            case 2: {
                if (context.isStackEmpty()) {
                    return parent;
                }
                parent = context.popFromStack();
                if (!(parent instanceof Document) && !(parent instanceof DocumentFragment)) break;
                return parent;
            }
            case 13: {
                Namespace ns = (Namespace)ev;
                StaxUtils.declare((Element)parent, ns.getNamespaceURI(), ns.getPrefix());
                break;
            }
            case 10: {
                Attribute at = (Attribute)ev;
                QName qname = at.getName();
                String attName = qname.getLocalPart();
                String attPrefix = qname.getPrefix();
                if (attPrefix != null && attPrefix.length() > 0) {
                    attName = attPrefix + ":" + attName;
                }
                Attr attr = doc.createAttributeNS(qname.getNamespaceURI(), attName);
                attr.setValue(at.getValue());
                ((Element)parent).setAttributeNode(attr);
                break;
            }
            case 4: {
                if (parent == null) break;
                Characters characters = ev.asCharacters();
                context.setRecordLoc(StaxUtils.addLocation(doc, parent.appendChild(doc.createTextNode(characters.getData())), characters.getLocation(), context.isRecordLoc()));
                break;
            }
            case 5: {
                if (parent == null) break;
                parent.appendChild(doc.createComment(((javax.xml.stream.events.Comment)ev).getText()));
                break;
            }
            case 12: {
                Characters characters = ev.asCharacters();
                context.setRecordLoc(StaxUtils.addLocation(doc, parent.appendChild(doc.createCDATASection(characters.getData())), characters.getLocation(), context.isRecordLoc()));
                break;
            }
            case 3: {
                parent.appendChild(doc.createProcessingInstruction(((ProcessingInstruction)((Object)ev)).getTarget(), ((ProcessingInstruction)((Object)ev)).getData()));
                break;
            }
            case 9: {
                EntityReference er = (EntityReference)ev;
                parent.appendChild(doc.createEntityReference(er.getName()));
                break;
            }
        }
        return parent;
    }

    private static boolean addLocation(Document doc, Node node, Location loc, boolean recordLoc) {
        if (recordLoc && loc != null && (loc.getColumnNumber() != 0 || loc.getLineNumber() != 0)) {
            try {
                final int charOffset = loc.getCharacterOffset();
                final int colNum = loc.getColumnNumber();
                final int linNum = loc.getLineNumber();
                final String pubId = loc.getPublicId() == null ? doc.getDocumentURI() : loc.getPublicId();
                final String sysId = loc.getSystemId() == null ? doc.getDocumentURI() : loc.getSystemId();
                Location loc2 = new Location(){

                    @Override
                    public int getCharacterOffset() {
                        return charOffset;
                    }

                    @Override
                    public int getColumnNumber() {
                        return colNum;
                    }

                    @Override
                    public int getLineNumber() {
                        return linNum;
                    }

                    @Override
                    public String getPublicId() {
                        return pubId;
                    }

                    @Override
                    public String getSystemId() {
                        return sysId;
                    }
                };
                node.setUserData("location", loc2, LocationUserDataHandler.INSTANCE);
            }
            catch (Throwable ex) {
                return false;
            }
        }
        return recordLoc;
    }

    private static boolean addLocation(Document doc, Node node, XMLStreamReader reader, boolean recordLoc) {
        return StaxUtils.addLocation(doc, node, reader.getLocation(), recordLoc);
    }

    private static void declare(Element node, String uri, String prefix) {
        String qualname = prefix != null && prefix.length() > 0 ? "xmlns:" + prefix : "xmlns";
        Attr attr = node.getOwnerDocument().createAttributeNS(XML_NS, qualname);
        attr.setValue(uri);
        node.setAttributeNodeNS(attr);
    }

    public static XMLStreamReader createXMLStreamReader(InputSource src) {
        String pubId;
        String sysId = src.getSystemId() == null ? null : src.getSystemId();
        String string = pubId = src.getPublicId() == null ? null : src.getPublicId();
        if (src.getByteStream() != null) {
            if (src.getEncoding() == null) {
                StreamSource ss = new StreamSource(src.getByteStream(), sysId);
                ss.setPublicId(pubId);
                return StaxUtils.createXMLStreamReader(ss);
            }
            return StaxUtils.createXMLStreamReader(src.getByteStream(), src.getEncoding());
        }
        if (src.getCharacterStream() != null) {
            StreamSource ss = new StreamSource(src.getCharacterStream(), sysId);
            ss.setPublicId(pubId);
            return StaxUtils.createXMLStreamReader(ss);
        }
        try {
            URL url2 = new URL(sysId);
            StreamSource ss = new StreamSource(url2.openStream(), sysId);
            ss.setPublicId(pubId);
            return StaxUtils.createXMLStreamReader(ss);
        }
        catch (Exception exception) {
            throw new IllegalArgumentException("InputSource must have a ByteStream or CharacterStream");
        }
    }

    public static XMLStreamReader createXMLStreamReader(InputStream in, String encoding) {
        XMLInputFactory factory2 = StaxUtils.getXMLInputFactory();
        try {
            XMLStreamReader xMLStreamReader = factory2.createXMLStreamReader(in, encoding != null ? encoding : StandardCharsets.UTF_8.name());
            return xMLStreamReader;
        }
        catch (XMLStreamException e) {
            throw new RuntimeException("Couldn't parse stream.", e);
        }
        finally {
            StaxUtils.returnXMLInputFactory(factory2);
        }
    }

    public static XMLStreamReader createXMLStreamReader(InputStream in) {
        XMLInputFactory factory2 = StaxUtils.getXMLInputFactory();
        try {
            XMLStreamReader xMLStreamReader = factory2.createXMLStreamReader(in);
            return xMLStreamReader;
        }
        catch (XMLStreamException e) {
            throw new RuntimeException("Couldn't parse stream.", e);
        }
        finally {
            StaxUtils.returnXMLInputFactory(factory2);
        }
    }

    public static XMLStreamReader createXMLStreamReader(String systemId, InputStream in) {
        XMLInputFactory factory2 = StaxUtils.getXMLInputFactory();
        try {
            XMLStreamReader xMLStreamReader = factory2.createXMLStreamReader(systemId, in);
            return xMLStreamReader;
        }
        catch (XMLStreamException e) {
            throw new RuntimeException("Couldn't parse stream.", e);
        }
        finally {
            StaxUtils.returnXMLInputFactory(factory2);
        }
    }

    public static XMLStreamReader createXMLStreamReader(Element el) {
        return new W3CDOMStreamReader(el);
    }

    public static XMLStreamReader createXMLStreamReader(Document doc) {
        return new W3CDOMStreamReader(doc.getDocumentElement());
    }

    public static XMLStreamReader createXMLStreamReader(Element el, String sysId) {
        return new W3CDOMStreamReader(el, sysId);
    }

    public static XMLStreamReader createXMLStreamReader(Document doc, String sysId) {
        return new W3CDOMStreamReader(doc.getDocumentElement(), sysId);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static XMLStreamReader createXMLStreamReader(Source source2) {
        XMLStreamReader xMLStreamReader;
        if (source2 instanceof DOMSource) {
            DOMSource ds = (DOMSource)source2;
            Node nd = ds.getNode();
            Element el = null;
            if (nd instanceof Document) {
                el = ((Document)nd).getDocumentElement();
            } else if (nd instanceof Element) {
                el = (Element)nd;
            }
            if (null != el) {
                return new W3CDOMStreamReader(el, source2.getSystemId());
            }
        } else {
            SAXSource ss;
            if (source2 instanceof StAXSource) {
                return ((StAXSource)source2).getXMLStreamReader();
            }
            if (source2 instanceof StaxSource) {
                return ((StaxSource)source2).getXMLStreamReader();
            }
            if (source2 instanceof SAXSource && (ss = (SAXSource)source2).getXMLReader() == null) {
                return StaxUtils.createXMLStreamReader(((SAXSource)source2).getInputSource());
            }
        }
        XMLInputFactory factory2 = StaxUtils.getXMLInputFactory();
        try {
            XMLStreamReader reader = null;
            try {
                reader = factory2.createXMLStreamReader(source2);
            }
            catch (UnsupportedOperationException el) {
                // empty catch block
            }
            if (reader == null && source2 instanceof StreamSource) {
                StreamSource ss = (StreamSource)source2;
                reader = ss.getInputStream() != null ? factory2.createXMLStreamReader(ss.getSystemId(), ss.getInputStream()) : factory2.createXMLStreamReader(ss.getSystemId(), ss.getReader());
            }
            xMLStreamReader = reader;
        }
        catch (Throwable throwable) {
            try {
                StaxUtils.returnXMLInputFactory(factory2);
                throw throwable;
            }
            catch (XMLStreamException e) {
                throw new RuntimeException("Couldn't parse stream.", e);
            }
        }
        StaxUtils.returnXMLInputFactory(factory2);
        return xMLStreamReader;
    }

    public static XMLStreamReader createXMLStreamReader(Reader reader) {
        XMLInputFactory factory2 = StaxUtils.getXMLInputFactory();
        try {
            XMLStreamReader xMLStreamReader = factory2.createXMLStreamReader(reader);
            return xMLStreamReader;
        }
        catch (XMLStreamException e) {
            throw new RuntimeException("Couldn't parse stream.", e);
        }
        finally {
            StaxUtils.returnXMLInputFactory(factory2);
        }
    }

    public static QName readQName(XMLStreamReader reader) throws XMLStreamException {
        String value = reader.getElementText();
        if (value == null) {
            return null;
        }
        int index = (value = value.trim()).indexOf(58);
        if (index == -1) {
            return new QName(value);
        }
        String prefix = value.substring(0, index);
        String localName = value.substring(index + 1);
        String ns = reader.getNamespaceURI(prefix);
        if (!StringUtils.isEmpty(prefix) && ns == null) {
            throw new RuntimeException("Invalid QName in mapping: " + value);
        }
        if (ns == null) {
            return new QName(localName);
        }
        return new QName(ns, localName, prefix);
    }

    public static String getUniquePrefix(XMLStreamWriter writer, String namespaceURI, boolean declare) throws XMLStreamException {
        String prefix = writer.getPrefix(namespaceURI);
        if (prefix == null) {
            prefix = StaxUtils.getUniquePrefix(writer);
            if (declare) {
                writer.setPrefix(prefix, namespaceURI);
                writer.writeNamespace(prefix, namespaceURI);
            }
        }
        return prefix;
    }

    public static String getUniquePrefix(XMLStreamWriter writer, String namespaceURI) throws XMLStreamException {
        return StaxUtils.getUniquePrefix(writer, namespaceURI, false);
    }

    public static String getUniquePrefix(XMLStreamWriter writer) {
        NamespaceContext nc = writer.getNamespaceContext();
        if (nc == null) {
            return DEF_PREFIXES[0];
        }
        for (String t2 : DEF_PREFIXES) {
            String uri = nc.getNamespaceURI(t2);
            if (!StringUtils.isEmpty(uri)) continue;
            return t2;
        }
        int n = 10;
        String nsPrefix;
        String uri;
        while (!StringUtils.isEmpty(uri = nc.getNamespaceURI(nsPrefix = "ns" + n))) {
            ++n;
        }
        return nsPrefix;
    }

    public static void printXmlFragment(XMLStreamReader reader) {
        try {
            StringWriter sw = new StringWriter(1024);
            PrettyPrintXMLStreamWriter writer = null;
            try {
                writer = new PrettyPrintXMLStreamWriter(StaxUtils.createXMLStreamWriter(sw), 4);
                StaxUtils.copy(reader, (XMLStreamWriter)writer);
                writer.flush();
            }
            catch (Throwable throwable) {
                StaxUtils.close(writer);
                throw throwable;
            }
            StaxUtils.close(writer);
            LOG.info(sw.toString());
        }
        catch (XMLStreamException e) {
            LOG.severe(e.getMessage());
        }
    }

    private static void writeStartElementEvent(XMLEvent event, XMLStreamWriter writer) throws XMLStreamException {
        StartElement start2 = event.asStartElement();
        QName name = start2.getName();
        String nsURI = name.getNamespaceURI();
        String localName = name.getLocalPart();
        String prefix = name.getPrefix();
        if (prefix != null) {
            writer.writeStartElement(prefix, localName, nsURI);
        } else if (nsURI != null) {
            writer.writeStartElement(localName, nsURI);
        } else {
            writer.writeStartElement(localName);
        }
        Iterator it = CastUtils.cast(start2.getNamespaces());
        while (it != null && it.hasNext()) {
            StaxUtils.writeEvent((XMLEvent)it.next(), writer);
        }
        it = CastUtils.cast(start2.getAttributes());
        while (it != null && it.hasNext()) {
            StaxUtils.writeAttributeEvent((XMLEvent)it.next(), writer);
        }
    }

    private static void writeAttributeEvent(XMLEvent event, XMLStreamWriter writer) throws XMLStreamException {
        Attribute attr = (Attribute)event;
        QName name = attr.getName();
        String nsURI = name.getNamespaceURI();
        String localName = name.getLocalPart();
        String prefix = name.getPrefix();
        String value = attr.getValue();
        if (prefix != null) {
            writer.writeAttribute(prefix, nsURI, localName, value);
        } else if (nsURI != null) {
            writer.writeAttribute(nsURI, localName, value);
        } else {
            writer.writeAttribute(localName, value);
        }
    }

    public static void writeEvent(XMLEvent event, XMLStreamWriter writer) throws XMLStreamException {
        switch (event.getEventType()) {
            case 1: {
                StaxUtils.writeStartElementEvent(event, writer);
                break;
            }
            case 2: {
                writer.writeEndElement();
                break;
            }
            case 10: {
                StaxUtils.writeAttributeEvent(event, writer);
                break;
            }
            case 9: {
                writer.writeEntityRef(((EntityReference)event).getName());
                break;
            }
            case 11: {
                writer.writeDTD(((DTD)event).getDocumentTypeDeclaration());
                break;
            }
            case 3: {
                if (((javax.xml.stream.events.ProcessingInstruction)event).getData() != null) {
                    writer.writeProcessingInstruction(((javax.xml.stream.events.ProcessingInstruction)event).getTarget(), ((javax.xml.stream.events.ProcessingInstruction)event).getData());
                    break;
                }
                writer.writeProcessingInstruction(((javax.xml.stream.events.ProcessingInstruction)event).getTarget());
                break;
            }
            case 13: {
                if (((Namespace)event).isDefaultNamespaceDeclaration()) {
                    writer.writeDefaultNamespace(((Namespace)event).getNamespaceURI());
                    writer.setDefaultNamespace(((Namespace)event).getNamespaceURI());
                    break;
                }
                writer.writeNamespace(((Namespace)event).getPrefix(), ((Namespace)event).getNamespaceURI());
                writer.setPrefix(((Namespace)event).getPrefix(), ((Namespace)event).getNamespaceURI());
                break;
            }
            case 5: {
                writer.writeComment(((javax.xml.stream.events.Comment)event).getText());
                break;
            }
            case 4: 
            case 6: {
                writer.writeCharacters(event.asCharacters().getData());
                break;
            }
            case 12: {
                writer.writeCData(event.asCharacters().getData());
                break;
            }
            case 7: {
                if (((StartDocument)event).encodingSet()) {
                    writer.writeStartDocument(((StartDocument)event).getCharacterEncodingScheme(), ((StartDocument)event).getVersion());
                    break;
                }
                writer.writeStartDocument(((StartDocument)event).getVersion());
                break;
            }
            case 8: {
                writer.writeEndDocument();
                break;
            }
        }
    }

    public static void print(Node node) {
        XMLStreamWriter writer = null;
        try {
            writer = StaxUtils.createXMLStreamWriter(System.out);
            StaxUtils.copy((Source)new DOMSource(node), writer);
            writer.flush();
        }
        catch (XMLStreamException e) {
            throw new RuntimeException(e);
        }
        finally {
            StaxUtils.close(writer);
        }
    }

    public static String toString(Source src) {
        StringWriter sw = new StringWriter(1024);
        XMLStreamWriter writer = null;
        try {
            writer = StaxUtils.createXMLStreamWriter(sw);
            StaxUtils.copy(src, writer);
            writer.flush();
        }
        catch (XMLStreamException e) {
            throw new RuntimeException(e);
        }
        finally {
            StaxUtils.close(writer);
        }
        return sw.toString();
    }

    public static String toString(Node src) {
        return StaxUtils.toString(new DOMSource(src));
    }

    public static String toString(Document doc) {
        StringWriter sw = new StringWriter(1024);
        XMLStreamWriter writer = null;
        try {
            writer = StaxUtils.createXMLStreamWriter(sw);
            StaxUtils.copy(doc, writer);
            writer.flush();
        }
        catch (XMLStreamException e) {
            throw new RuntimeException(e);
        }
        finally {
            StaxUtils.close(writer);
        }
        return sw.toString();
    }

    public static String toString(Element el) {
        return StaxUtils.toString(el, 0);
    }

    public static String toString(Element el, int indent) {
        StringWriter sw = new StringWriter(1024);
        XMLStreamWriter writer = null;
        try {
            writer = StaxUtils.createXMLStreamWriter(sw);
            if (indent > 0) {
                writer = new PrettyPrintXMLStreamWriter(writer, indent);
            }
            StaxUtils.copy(el, writer);
            writer.flush();
        }
        catch (XMLStreamException e) {
            throw new RuntimeException(e);
        }
        finally {
            StaxUtils.close(writer);
        }
        return sw.toString();
    }

    public static void close(XMLStreamReader reader) throws XMLStreamException {
        if (reader != null) {
            reader.close();
        }
    }

    public static void close(XMLStreamWriter writer) {
        if (writer != null) {
            try {
                writer.close();
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
    }

    public static boolean isSecureReader(XMLStreamReader reader, Message message) {
        if (reader instanceof DocumentDepthProperties) {
            return true;
        }
        try {
            if (reader.getProperty("com.ctc.wstx.maxChildrenPerElement") != null) {
                return true;
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return false;
    }

    public static XMLStreamReader configureReader(XMLStreamReader xreader, Message message) throws XMLStreamException {
        Integer messageMaxChildElements = PropertyUtils.getInteger(message, MAX_CHILD_ELEMENTS);
        Integer messageMaxElementDepth = PropertyUtils.getInteger(message, MAX_ELEMENT_DEPTH);
        Integer messageMaxAttributeCount = PropertyUtils.getInteger(message, MAX_ATTRIBUTE_COUNT);
        Integer messageMaxAttributeSize = PropertyUtils.getInteger(message, MAX_ATTRIBUTE_SIZE);
        Integer messageMaxTextLength = PropertyUtils.getInteger(message, MAX_TEXT_LENGTH);
        Long messageMaxElementCount = PropertyUtils.getLong(message, MAX_ELEMENT_COUNT);
        Long messageMaxXMLCharacters = PropertyUtils.getLong(message, MAX_XML_CHARACTERS);
        return StaxUtils.configureReader(xreader, messageMaxChildElements, messageMaxElementDepth, messageMaxAttributeCount, messageMaxAttributeSize, messageMaxTextLength, messageMaxElementCount, messageMaxXMLCharacters);
    }

    public static XMLStreamReader configureReader(XMLStreamReader reader, Integer maxChildElements, Integer maxElementDepth, Integer maxAttributeCount, Integer maxAttributeSize, Integer maxTextLength, Long maxElementCount, Long maxXMLCharacters) throws XMLStreamException {
        try {
            DocumentDepthProperties p = null;
            if (maxChildElements != null) {
                try {
                    StaxUtils.setProperty(reader, "com.ctc.wstx.maxChildrenPerElement", (Object)maxChildElements);
                }
                catch (Throwable t2) {
                    p = new DocumentDepthProperties();
                    p.setInnerElementCountThreshold(maxChildElements);
                }
            }
            if (maxElementDepth != null) {
                try {
                    StaxUtils.setProperty(reader, "com.ctc.wstx.maxElementDepth", (Object)maxElementDepth);
                }
                catch (Throwable t3) {
                    if (p == null) {
                        p = new DocumentDepthProperties();
                    }
                    p.setInnerElementLevelThreshold(maxElementDepth);
                }
            }
            if (maxAttributeCount != null) {
                StaxUtils.setProperty(reader, "com.ctc.wstx.maxAttributesPerElement", (Object)maxAttributeCount);
            }
            if (maxAttributeSize != null) {
                StaxUtils.setProperty(reader, "com.ctc.wstx.maxAttributeSize", (Object)maxAttributeSize);
            }
            if (maxTextLength != null) {
                StaxUtils.setProperty(reader, "com.ctc.wstx.maxTextLength", (Object)maxTextLength);
            }
            if (maxElementCount != null) {
                try {
                    StaxUtils.setProperty(reader, "com.ctc.wstx.maxElementCount", (Object)maxElementCount);
                }
                catch (Throwable t4) {
                    if (p == null) {
                        p = new DocumentDepthProperties();
                    }
                    p.setElementCountThreshold(maxElementCount.intValue());
                }
            }
            if (maxXMLCharacters != null) {
                StaxUtils.setProperty(reader, "com.ctc.wstx.maxCharacters", (Object)maxXMLCharacters);
            }
            if (p != null) {
                reader = new DepthRestrictingStreamReader(reader, p);
            }
        }
        catch (ClassCastException cce) {
            if (allowInsecureParser) {
                LOG.warning("INSTANCE_NOT_XMLSTREAMREADER2");
            }
            throw new XMLStreamException(cce.getMessage(), cce);
        }
        catch (IllegalArgumentException cce) {
            if (allowInsecureParser) {
                LOG.log(Level.WARNING, "SECURE_PROPERTY_NOT_SUPPORTED", cce.getMessage());
            }
            throw new XMLStreamException(cce.getMessage(), cce);
        }
        return reader;
    }

    private static void setProperty(XMLStreamReader reader, String p, Object v) {
        WoodstoxHelper.setProperty(reader, p, v);
    }

    static {
        DEF_PREFIXES = new String[]{"ns1".intern(), "ns2".intern(), "ns3".intern(), "ns4".intern(), "ns5".intern(), "ns6".intern(), "ns7".intern(), "ns8".intern(), "ns9".intern()};
        innerElementLevelThreshold = 100;
        innerElementCountThreshold = 50000;
        maxAttributeCount = 500;
        maxAttributeSize = 65536;
        maxTextLength = 0x8000000;
        minTextSegment = 64;
        maxElementCount = Long.MAX_VALUE;
        maxXMLCharacters = Long.MAX_VALUE;
        int i = StaxUtils.getInteger("org.apache.cxf.staxutils.pool-size", 20);
        NS_AWARE_INPUT_FACTORY_POOL = new ArrayBlockingQueue<XMLInputFactory>(i);
        OUTPUT_FACTORY_POOL = new ArrayBlockingQueue<XMLOutputFactory>(i);
        innerElementCountThreshold = StaxUtils.getInteger(INNER_ELEMENT_COUNT_SYSTEM_PROP, innerElementCountThreshold);
        innerElementLevelThreshold = StaxUtils.getInteger(INNER_ELEMENT_LEVEL_SYSTEM_PROP, innerElementLevelThreshold);
        innerElementCountThreshold = StaxUtils.getInteger(MAX_CHILD_ELEMENTS, innerElementCountThreshold);
        innerElementLevelThreshold = StaxUtils.getInteger(MAX_ELEMENT_DEPTH, innerElementLevelThreshold);
        maxAttributeCount = StaxUtils.getInteger(MAX_ATTRIBUTE_COUNT, maxAttributeCount);
        maxAttributeSize = StaxUtils.getInteger(MAX_ATTRIBUTE_SIZE, maxAttributeSize);
        maxTextLength = StaxUtils.getInteger(MAX_TEXT_LENGTH, maxTextLength);
        minTextSegment = StaxUtils.getInteger(MIN_TEXT_SEGMENT, minTextSegment);
        maxElementCount = StaxUtils.getLong(MAX_ELEMENT_COUNT, maxElementCount);
        maxXMLCharacters = StaxUtils.getLong(MAX_XML_CHARACTERS, maxXMLCharacters);
        String s2 = SystemPropertyAction.getPropertyOrNull(ALLOW_INSECURE_PARSER);
        if (!StringUtils.isEmpty(s2)) {
            allowInsecureParser = "1".equals(s2) || Boolean.parseBoolean(s2);
        }
        XMLInputFactory xif = null;
        try {
            xif = StaxUtils.createXMLInputFactory(true);
            String xifClassName = xif.getClass().getName();
            if (!(xifClassName.contains("ctc.wstx") || xifClassName.contains("xml.xlxp") || xifClassName.contains("xml.xlxp2") || xifClassName.contains("bea.core"))) {
                xif = null;
            }
        }
        catch (Throwable t2) {
            xif = null;
        }
        SAFE_INPUT_FACTORY = xif;
        XMLOutputFactory xof = null;
        try {
            xof = XMLOutputFactory.newInstance();
            String xofClassName = xof.getClass().getName();
            if (!(xofClassName.contains("ctc.wstx") || xofClassName.contains("xml.xlxp") || xofClassName.contains("xml.xlxp2") || xofClassName.contains("bea.core"))) {
                xof = null;
            }
        }
        catch (Throwable throwable) {
            // empty catch block
        }
        SAFE_OUTPUT_FACTORY = xof;
    }

    private static class LocationUserDataHandler
    implements UserDataHandler {
        public static final LocationUserDataHandler INSTANCE = new LocationUserDataHandler();

        private LocationUserDataHandler() {
        }

        @Override
        public void handle(short operation, String key, Object data, Node src, Node dst) {
            if (operation == 1) {
                dst.setUserData(key, data, this);
            }
        }
    }

    public static class StreamToDOMContext {
        private final Deque<Node> stack = new ArrayDeque<Node>();
        private int elementCount;
        private boolean repairing;
        private boolean recordLoc;
        private boolean threshold;

        public StreamToDOMContext(boolean repairing, boolean recordLoc, boolean threshold) {
            this.repairing = repairing;
            this.recordLoc = recordLoc;
            this.threshold = threshold;
        }

        public void setRecordLoc(boolean recordLoc) {
            this.recordLoc = recordLoc;
        }

        public boolean isRecordLoc() {
            return this.recordLoc;
        }

        public boolean isRepairing() {
            return this.repairing;
        }

        public boolean isThreshold() {
            return this.threshold;
        }

        public int incrementCount() {
            return ++this.elementCount;
        }

        public int decreaseCount() {
            return --this.elementCount;
        }

        public int getCount() {
            return this.elementCount;
        }

        public void pushToStack(Node node) {
            this.stack.push(node);
        }

        public Node popFromStack() {
            return this.stack.pop();
        }

        public int getStackSize() {
            return this.stack.size();
        }

        public boolean isStackEmpty() {
            return this.stack.isEmpty();
        }
    }
}

