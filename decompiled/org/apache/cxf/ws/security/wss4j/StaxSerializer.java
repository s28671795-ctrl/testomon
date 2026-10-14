/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.wss4j;

import com.ctc.wstx.api.WstxInputProperties;
import com.ctc.wstx.sr.InputElementStack;
import com.ctc.wstx.util.InternCache;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.SequenceInputStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Vector;
import javax.xml.namespace.NamespaceContext;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.dom.DOMResult;
import org.apache.cxf.binding.soap.saaj.SAAJStreamWriter;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.helpers.LoadingByteArrayOutputStream;
import org.apache.cxf.staxutils.OverlayW3CDOMStreamWriter;
import org.apache.cxf.staxutils.StaxUtils;
import org.apache.xml.security.encryption.AbstractSerializer;
import org.apache.xml.security.encryption.XMLEncryptionException;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

public class StaxSerializer
extends AbstractSerializer {
    XMLInputFactory factory;
    boolean validFactory;

    boolean addNamespaces(XMLStreamReader reader, Node ctx) {
        try {
            NamespaceContext nsctx = reader.getNamespaceContext();
            if (nsctx instanceof InputElementStack) {
                InputElementStack ies = (InputElementStack)nsctx;
                InternCache ic = InternCache.getInstance();
                HashMap<String, String> storedNamespaces = new HashMap<String, String>();
                for (Node wk = ctx; wk != null; wk = wk.getParentNode()) {
                    NamedNodeMap atts = wk.getAttributes();
                    if (atts == null) continue;
                    for (int i = 0; i < atts.getLength(); ++i) {
                        Node att = atts.item(i);
                        String nodeName = att.getNodeName();
                        if (!"xmlns".equals(nodeName) && !nodeName.startsWith("xmlns:") || storedNamespaces.containsKey(att.getNodeName())) continue;
                        String prefix = att.getLocalName();
                        if ("xmlns".equals(prefix)) {
                            prefix = "";
                        }
                        prefix = ic.intern(prefix);
                        ies.addNsBinding(prefix, att.getNodeValue());
                        storedNamespaces.put(nodeName, att.getNodeValue());
                    }
                }
            }
            return true;
        }
        catch (Throwable throwable) {
            return false;
        }
    }

    private XMLStreamReader createWstxReader(byte[] source2, Node ctx) throws XMLEncryptionException {
        try {
            XMLStreamReader reader;
            if (this.factory == null) {
                this.factory = StaxUtils.createXMLInputFactory(true);
                try {
                    this.factory.setProperty("com.ctc.wstx.fragmentMode", WstxInputProperties.PARSING_MODE_FRAGMENT);
                    this.factory.setProperty("org.codehaus.stax2.reportPrologWhitespace", Boolean.TRUE);
                    this.validFactory = true;
                }
                catch (Throwable t2) {
                    this.validFactory = false;
                }
            }
            if (this.validFactory && this.addNamespaces(reader = this.factory.createXMLStreamReader(new ByteArrayInputStream(source2)), ctx)) {
                return reader;
            }
        }
        catch (Throwable throwable) {
            // empty catch block
        }
        return null;
    }

    @Override
    public Node deserialize(byte[] source2, Node ctx) throws XMLEncryptionException {
        XMLStreamReader reader = this.createWstxReader(source2, ctx);
        if (reader != null) {
            return this.deserialize(ctx, reader, false);
        }
        return this.deserialize(ctx, new InputSource(this.createStreamContext(source2, ctx)));
    }

    InputStream createStreamContext(byte[] source2, Node ctx) throws XMLEncryptionException {
        Vector<ByteArrayInputStream> v = new Vector<ByteArrayInputStream>(2);
        LoadingByteArrayOutputStream byteArrayOutputStream = new LoadingByteArrayOutputStream();
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter((OutputStream)byteArrayOutputStream, StandardCharsets.UTF_8);
            outputStreamWriter.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?><dummy");
            HashMap<String, String> storedNamespaces = new HashMap<String, String>();
            for (Node wk = ctx; wk != null; wk = wk.getParentNode()) {
                NamedNodeMap atts = wk.getAttributes();
                if (atts == null) continue;
                for (int i = 0; i < atts.getLength(); ++i) {
                    Node att = atts.item(i);
                    String nodeName = att.getNodeName();
                    if (!"xmlns".equals(nodeName) && !nodeName.startsWith("xmlns:") || storedNamespaces.containsKey(att.getNodeName())) continue;
                    outputStreamWriter.write(" ");
                    outputStreamWriter.write(nodeName);
                    outputStreamWriter.write("=\"");
                    outputStreamWriter.write(att.getNodeValue());
                    outputStreamWriter.write("\"");
                    storedNamespaces.put(nodeName, att.getNodeValue());
                }
            }
            outputStreamWriter.write(">");
            outputStreamWriter.close();
            v.add(byteArrayOutputStream.createInputStream());
            v.addElement(new ByteArrayInputStream(source2));
            byteArrayOutputStream = new LoadingByteArrayOutputStream();
            outputStreamWriter = new OutputStreamWriter((OutputStream)byteArrayOutputStream, StandardCharsets.UTF_8);
            outputStreamWriter.write("</dummy>");
            outputStreamWriter.close();
            v.add(byteArrayOutputStream.createInputStream());
        }
        catch (IOException e) {
            throw new XMLEncryptionException(e);
        }
        return new SequenceInputStream(v.elements());
    }

    @Override
    public Node deserialize(String source2, Node ctx) throws XMLEncryptionException {
        String fragment = StaxSerializer.createContext(source2, ctx);
        return this.deserialize(ctx, new InputSource(new StringReader(fragment)));
    }

    @Override
    public byte[] serializeToByteArray(Element element) throws Exception {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();){
            XMLStreamWriter writer = StaxUtils.createXMLStreamWriter(baos);
            StaxUtils.copy(element, writer);
            writer.close();
            byte[] byArray = baos.toByteArray();
            return byArray;
        }
    }

    private Node deserialize(Node ctx, InputSource inputSource) throws XMLEncryptionException {
        XMLStreamReader reader = StaxUtils.createXMLStreamReader(inputSource);
        return this.deserialize(ctx, reader, true);
    }

    private Node deserialize(Node ctx, XMLStreamReader reader, boolean wrapped) throws XMLEncryptionException {
        Document contextDocument = null;
        contextDocument = 9 == ctx.getNodeType() ? (Document)ctx : ctx.getOwnerDocument();
        XMLStreamWriter writer = null;
        try {
            if (ctx instanceof SOAPElement) {
                SOAPElement el;
                for (el = (SOAPElement)ctx; el != null && !(el instanceof SOAPEnvelope); el = el.getParentElement()) {
                }
                Element element = ctx.getOwnerDocument().createElementNS("dummy", "dummy");
                writer = new SAAJStreamWriter((SOAPEnvelope)el, element);
                return this.appendNewChild(reader, wrapped, contextDocument, writer, element);
            }
            if (DOMUtils.isJava9SAAJ()) {
                Element element = ctx.getOwnerDocument().createElementNS("dummy", "dummy");
                writer = new OverlayW3CDOMStreamWriter(ctx.getOwnerDocument(), element);
                return this.appendNewChild(reader, wrapped, contextDocument, writer, element);
            }
            DocumentFragment dummyFragment = contextDocument.createDocumentFragment();
            writer = StaxUtils.createXMLStreamWriter(new DOMResult(dummyFragment));
            StaxUtils.copy(reader, writer);
            if (wrapped) {
                DocumentFragment result = contextDocument.createDocumentFragment();
                Node child = dummyFragment.getFirstChild().getFirstChild();
                if (child != null && child.getNextSibling() == null) {
                    return child;
                }
                while (child != null) {
                    Node nextChild = child.getNextSibling();
                    result.appendChild(child);
                    child = nextChild;
                }
                dummyFragment = result;
            }
            return dummyFragment;
        }
        catch (XMLStreamException ex) {
            throw new XMLEncryptionException(ex);
        }
    }

    private Node appendNewChild(XMLStreamReader reader, boolean wrapped, Document contextDocument, XMLStreamWriter writer, Element element) throws XMLStreamException {
        StaxUtils.copy(reader, writer);
        DocumentFragment result = contextDocument.createDocumentFragment();
        Node child = element.getFirstChild();
        if (wrapped) {
            child = child.getFirstChild();
        }
        if (child != null && child.getNextSibling() == null) {
            return child;
        }
        while (child != null) {
            Node nextChild = child.getNextSibling();
            result.appendChild(child);
            child = nextChild;
        }
        return result;
    }
}

