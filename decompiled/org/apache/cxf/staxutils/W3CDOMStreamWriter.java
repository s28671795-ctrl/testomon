/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.staxutils;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
import java.util.Map;
import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.helpers.MapNamespaceContext;
import org.apache.cxf.staxutils.StaxUtils;
import org.apache.cxf.staxutils.W3CNamespaceContext;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class W3CDOMStreamWriter
implements XMLStreamWriter {
    static final String XML_NS = "http://www.w3.org/2000/xmlns/";
    private final Deque<Node> stack = new ArrayDeque<Node>();
    private Document document;
    private Node currentNode;
    private NamespaceContext context = new W3CNamespaceContext();
    private boolean nsRepairing;
    private Map<String, Object> properties = Collections.emptyMap();

    public W3CDOMStreamWriter() {
        this.document = DOMUtils.newDocument();
    }

    public W3CDOMStreamWriter(DocumentBuilder builder) {
        this.document = builder.newDocument();
    }

    public W3CDOMStreamWriter(Document document) {
        this.document = document;
    }

    public W3CDOMStreamWriter(DocumentFragment frag) {
        this.document = frag.getOwnerDocument();
        this.currentNode = frag;
    }

    public W3CDOMStreamWriter(Document document, DocumentFragment frag) {
        this.document = document;
        this.currentNode = frag;
    }

    public W3CDOMStreamWriter(Element e) {
        this.document = e.getOwnerDocument();
        this.currentNode = e;
        ((W3CNamespaceContext)this.context).setElement(e);
    }

    public W3CDOMStreamWriter(Document owner, Element e) {
        this.document = owner;
        this.currentNode = e;
        ((W3CNamespaceContext)this.context).setElement(e);
    }

    public Element getCurrentNode() {
        if (this.currentNode instanceof Element) {
            return (Element)this.currentNode;
        }
        return null;
    }

    public DocumentFragment getCurrentFragment() {
        if (this.currentNode instanceof DocumentFragment) {
            return (DocumentFragment)this.currentNode;
        }
        return null;
    }

    public void setNsRepairing(boolean b) {
        this.nsRepairing = b;
    }

    public boolean isNsRepairing() {
        return this.nsRepairing;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }

    public Document getDocument() {
        return this.document;
    }

    @Override
    public void writeStartElement(String local) throws XMLStreamException {
        this.createAndAddElement(null, local, null);
    }

    protected void newChild(Element element) {
        this.setChild(element, true);
    }

    protected void setChild(Element element, boolean append) {
        Node appendedChildNode = null;
        if (this.currentNode != null) {
            this.stack.push(this.currentNode);
            if (append) {
                appendedChildNode = this.currentNode.appendChild(element);
            }
        } else if (append) {
            appendedChildNode = this.document.appendChild(element);
        }
        if (!(this.context instanceof W3CNamespaceContext)) {
            W3CNamespaceContext childContext = new W3CNamespaceContext();
            childContext.setOutNamespaceContext(this.context);
            this.context = childContext;
        }
        ((W3CNamespaceContext)this.context).setElement(element);
        this.currentNode = appendedChildNode != null ? DOMUtils.getDomElement(appendedChildNode) : element;
    }

    @Override
    public void writeStartElement(String namespace, String local) throws XMLStreamException {
        this.createAndAddElement(null, local, namespace);
    }

    @Override
    public void writeStartElement(String prefix, String local, String namespace) throws XMLStreamException {
        if (prefix == null || prefix.isEmpty()) {
            this.writeStartElement(namespace, local);
        } else {
            this.createAndAddElement(prefix, local, namespace);
            if (this.nsRepairing && !prefix.equals(this.getNamespaceContext().getPrefix(namespace))) {
                this.writeNamespace(prefix, namespace);
            }
        }
    }

    protected Element createElementNS(String ns, String pfx, String local) {
        Element element = this.document.createElementNS(ns, pfx != null ? pfx + ':' + local : local);
        element = (Element)DOMUtils.getDomElement(element);
        return element;
    }

    protected void createAndAddElement(String prefix, String local, String namespace) {
        if (prefix == null) {
            if (namespace == null) {
                this.newChild(this.createElementNS(null, null, local));
            } else {
                this.newChild(this.createElementNS(namespace, null, local));
            }
        } else {
            this.newChild(this.createElementNS(namespace, prefix, local));
        }
    }

    @Override
    public void writeEmptyElement(String namespace, String local) throws XMLStreamException {
        this.writeStartElement(namespace, local);
        this.writeEndElement();
    }

    @Override
    public void writeEmptyElement(String prefix, String local, String namespace) throws XMLStreamException {
        this.writeStartElement(prefix, local, namespace);
        this.writeEndElement();
    }

    @Override
    public void writeEmptyElement(String local) throws XMLStreamException {
        this.writeStartElement(local);
        this.writeEndElement();
    }

    @Override
    public void writeEndElement() throws XMLStreamException {
        this.currentNode = !this.stack.isEmpty() ? this.stack.pop() : null;
        if (this.context instanceof W3CNamespaceContext && this.currentNode instanceof Element) {
            ((W3CNamespaceContext)this.context).setElement((Element)this.currentNode);
        } else if (this.context instanceof MapNamespaceContext) {
            ((MapNamespaceContext)this.context).setTargetNode(this.currentNode);
        }
    }

    @Override
    public void writeEndDocument() throws XMLStreamException {
    }

    @Override
    public void writeAttribute(String local, String value) throws XMLStreamException {
        Attr a = local.startsWith("xmlns") && (local.length() == 5 || local.charAt(5) == ':') ? this.document.createAttributeNS(XML_NS, local) : this.document.createAttributeNS(null, local);
        a.setValue(value);
        ((Element)this.currentNode).setAttributeNode(a);
    }

    @Override
    public void writeAttribute(String prefix, String namespace, String local, String value) throws XMLStreamException {
        Attr a = this.document.createAttributeNS(namespace, !prefix.isEmpty() ? prefix + ':' + local : local);
        a.setValue(value);
        ((Element)this.currentNode).setAttributeNodeNS(a);
        if (this.nsRepairing && !prefix.equals(this.getNamespaceContext().getPrefix(namespace))) {
            this.writeNamespace(prefix, namespace);
        }
    }

    @Override
    public void writeAttribute(String namespace, String local, String value) throws XMLStreamException {
        Attr a = this.document.createAttributeNS(namespace, local);
        a.setValue(value);
        ((Element)this.currentNode).setAttributeNodeNS(a);
    }

    @Override
    public void writeNamespace(String prefix, String namespace) throws XMLStreamException {
        if (prefix.isEmpty()) {
            this.writeDefaultNamespace(namespace);
        } else {
            Attr attr = this.document.createAttributeNS(XML_NS, "xmlns:" + prefix);
            attr.setValue(namespace);
            ((Element)this.currentNode).setAttributeNodeNS(attr);
        }
    }

    @Override
    public void writeDefaultNamespace(String namespace) throws XMLStreamException {
        Attr attr = this.document.createAttributeNS(XML_NS, "xmlns");
        attr.setValue(namespace);
        ((Element)this.currentNode).setAttributeNodeNS(attr);
    }

    @Override
    public void writeComment(String value) throws XMLStreamException {
        Node nd = this.document.createComment(value);
        nd = DOMUtils.getDomElement(nd);
        if (this.currentNode == null) {
            this.document.appendChild(nd);
        } else {
            this.currentNode.appendChild(nd);
        }
    }

    @Override
    public void writeProcessingInstruction(String target) throws XMLStreamException {
        if (this.currentNode == null) {
            this.document.appendChild(this.document.createProcessingInstruction(target, null));
        } else {
            this.currentNode.appendChild(this.document.createProcessingInstruction(target, null));
        }
    }

    @Override
    public void writeProcessingInstruction(String target, String data) throws XMLStreamException {
        if (this.currentNode == null) {
            this.document.appendChild(this.document.createProcessingInstruction(target, data));
        } else {
            this.currentNode.appendChild(this.document.createProcessingInstruction(target, data));
        }
    }

    @Override
    public void writeCData(String data) throws XMLStreamException {
        this.currentNode.appendChild(DOMUtils.getDomElement(this.document.createCDATASection(data)));
    }

    @Override
    public void writeDTD(String arg0) throws XMLStreamException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void writeEntityRef(String ref) throws XMLStreamException {
        this.currentNode.appendChild(this.document.createEntityReference(ref));
    }

    @Override
    public void writeStartDocument() throws XMLStreamException {
    }

    @Override
    public void writeStartDocument(String version) throws XMLStreamException {
        try {
            this.document.setXmlVersion(version);
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    @Override
    public void writeStartDocument(String encoding, String version) throws XMLStreamException {
        try {
            this.document.setXmlVersion(version);
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    @Override
    public void writeCharacters(String text) throws XMLStreamException {
        Node nd = this.document.createTextNode(text);
        nd = DOMUtils.getDomElement(nd);
        if (this.currentNode != null) {
            this.currentNode.appendChild(nd);
        } else {
            this.document.appendChild(nd);
        }
    }

    @Override
    public void writeCharacters(char[] text, int start2, int len) throws XMLStreamException {
        this.writeCharacters(new String(text, start2, len));
    }

    @Override
    public String getPrefix(String uri) throws XMLStreamException {
        return this.context == null ? null : this.context.getPrefix(uri);
    }

    @Override
    public void setPrefix(String arg0, String arg1) throws XMLStreamException {
    }

    @Override
    public void setDefaultNamespace(String arg0) throws XMLStreamException {
    }

    @Override
    public void setNamespaceContext(NamespaceContext ctx) throws XMLStreamException {
        this.context = ctx;
    }

    @Override
    public NamespaceContext getNamespaceContext() {
        return this.context;
    }

    @Override
    public Object getProperty(String name) {
        return this.properties.get(name);
    }

    @Override
    public void close() throws XMLStreamException {
    }

    @Override
    public void flush() throws XMLStreamException {
    }

    public String toString() {
        if (this.document == null) {
            return "<null>";
        }
        if (this.document.getDocumentElement() == null) {
            return "<null document element>";
        }
        try {
            return StaxUtils.toString(this.document);
        }
        catch (Throwable t2) {
            t2.printStackTrace();
            return super.toString();
        }
    }
}

