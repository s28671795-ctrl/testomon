/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.staxutils;

import java.util.ArrayList;
import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.stream.Location;
import javax.xml.stream.XMLStreamException;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.staxutils.AbstractDOMStreamReader;
import org.apache.cxf.staxutils.StaxUtils;
import org.apache.cxf.staxutils.W3CNamespaceContext;
import org.w3c.dom.Attr;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;
import org.w3c.dom.TypeInfo;

public class W3CDOMStreamReader
extends AbstractDOMStreamReader<Node, Node> {
    private Node content;
    private Document document;
    private W3CNamespaceContext context;
    private String sysId;

    public W3CDOMStreamReader(Element element) {
        super(new AbstractDOMStreamReader.ElementFrame(element, null));
        this.content = element;
        this.newFrame(this.getCurrentFrame());
        this.document = element.getOwnerDocument();
    }

    public W3CDOMStreamReader(Element element, String systemId) {
        this(element);
        this.sysId = systemId;
    }

    public W3CDOMStreamReader(Document doc) {
        super(new AbstractDOMStreamReader.ElementFrame<Node, Node>((Node)doc, false){

            @Override
            public boolean isDocument() {
                return true;
            }
        });
        this.document = doc;
    }

    public W3CDOMStreamReader(DocumentFragment docfrag) {
        super(new AbstractDOMStreamReader.ElementFrame<Node, Node>((Node)docfrag, true){

            @Override
            public boolean isDocumentFragment() {
                return true;
            }
        });
        this.document = docfrag.getOwnerDocument();
    }

    public Document getDocument() {
        return this.document;
    }

    @Override
    public String getSystemId() {
        try {
            return this.sysId == null ? this.document.getDocumentURI() : this.sysId;
        }
        catch (Throwable throwable) {
            return this.sysId;
        }
    }

    @Override
    protected final void newFrame(AbstractDOMStreamReader.ElementFrame<Node, Node> frame) {
        NamedNodeMap nodes;
        Node element = this.getCurrentNode();
        frame.uris = new ArrayList<String>();
        frame.prefixes = new ArrayList<String>();
        frame.attributes = new ArrayList<Object>();
        if (this.context == null) {
            this.context = new W3CNamespaceContext();
        }
        if (element instanceof Element) {
            this.context.setElement((Element)element);
        }
        if ((nodes = element.getAttributes()) != null) {
            for (int i = 0; i < nodes.getLength(); ++i) {
                Node node = nodes.item(i);
                String prefix = node.getPrefix();
                String localName = node.getLocalName();
                String value = node.getNodeValue();
                String name = node.getNodeName();
                if (prefix == null) {
                    prefix = "";
                }
                if (name != null && "xmlns".equals(name)) {
                    frame.uris.add(value);
                    frame.prefixes.add("");
                    continue;
                }
                if (!prefix.isEmpty() && "xmlns".equals(prefix)) {
                    frame.uris.add(value);
                    frame.prefixes.add(localName);
                    continue;
                }
                if (name.startsWith("xmlns:")) {
                    frame.uris.add(value);
                    frame.prefixes.add(name.substring(6));
                    continue;
                }
                frame.attributes.add(node);
            }
        }
    }

    public final Node getCurrentNode() {
        return (Node)this.getCurrentFrame().element;
    }

    public final Element getCurrentElement() {
        return (Element)this.getCurrentFrame().element;
    }

    @Override
    protected AbstractDOMStreamReader.ElementFrame<Node, Node> getChildFrame() {
        return new AbstractDOMStreamReader.ElementFrame<Node, Node>((Node)this.getCurrentFrame().currentChild, this.getCurrentFrame());
    }

    @Override
    protected boolean hasMoreChildren() {
        if (this.getCurrentFrame().currentChild == null) {
            return this.getCurrentNode().getFirstChild() != null;
        }
        return ((Node)this.getCurrentFrame().currentChild).getNextSibling() != null;
    }

    @Override
    protected int nextChild() {
        AbstractDOMStreamReader.ElementFrame frame = this.getCurrentFrame();
        this.content = frame.currentChild == null ? this.getCurrentNode().getFirstChild() : ((Node)frame.currentChild).getNextSibling();
        frame.currentChild = this.content;
        switch (this.content.getNodeType()) {
            case 1: {
                return 1;
            }
            case 3: {
                return 4;
            }
            case 8: {
                return 5;
            }
            case 4: {
                return 12;
            }
            case 5: {
                return 9;
            }
            case 7: {
                return 3;
            }
        }
        throw new IllegalStateException("Found type: " + this.content.getClass().getName());
    }

    @Override
    public String getElementText() throws XMLStreamException {
        String result = DOMUtils.getRawContent(this.content);
        AbstractDOMStreamReader.ElementFrame frame = this.getCurrentFrame();
        frame.ended = true;
        this.currentEvent = 2;
        this.endElement();
        return result != null ? result : "";
    }

    @Override
    public String getNamespaceURI(String prefix) {
        AbstractDOMStreamReader.ElementFrame frame = this.getCurrentFrame();
        while (null != frame) {
            int index = frame.prefixes.indexOf(prefix);
            if (index != -1) {
                return frame.uris.get(index);
            }
            if (frame.parent == null && frame.getElement() instanceof Element) {
                return ((Element)frame.getElement()).lookupNamespaceURI(prefix);
            }
            frame = frame.parent;
        }
        return null;
    }

    @Override
    public String getAttributeValue(String ns, String local) {
        Attr at = ns == null || ns.isEmpty() ? this.getCurrentElement().getAttributeNode(local) : this.getCurrentElement().getAttributeNodeNS(ns, local);
        if (at == null) {
            return null;
        }
        return at.getNodeValue();
    }

    @Override
    public int getAttributeCount() {
        return this.getCurrentFrame().attributes.size();
    }

    Attr getAttribute(int i) {
        return (Attr)this.getCurrentFrame().attributes.get(i);
    }

    private String getLocalName(Attr attr) {
        String name = attr.getLocalName();
        if (name == null) {
            name = attr.getNodeName();
        }
        return name;
    }

    @Override
    public QName getAttributeName(int i) {
        Attr at = this.getAttribute(i);
        String prefix = at.getPrefix();
        String ln = this.getLocalName(at);
        String ns = at.getNamespaceURI();
        if (prefix == null) {
            return new QName(ns, ln);
        }
        return new QName(ns, ln, prefix);
    }

    @Override
    public String getAttributeNamespace(int i) {
        return this.getAttribute(i).getNamespaceURI();
    }

    @Override
    public String getAttributeLocalName(int i) {
        Attr attr = this.getAttribute(i);
        return this.getLocalName(attr);
    }

    @Override
    public String getAttributePrefix(int i) {
        return this.getAttribute(i).getPrefix();
    }

    @Override
    public String getAttributeType(int i) {
        Attr attr = this.getAttribute(i);
        if (attr.isId()) {
            return "ID";
        }
        TypeInfo schemaType = null;
        try {
            schemaType = attr.getSchemaTypeInfo();
        }
        catch (Throwable t2) {
            schemaType = null;
        }
        return schemaType == null ? "CDATA" : (schemaType.getTypeName() == null ? "CDATA" : schemaType.getTypeName());
    }

    @Override
    public String getAttributeValue(int i) {
        return this.getAttribute(i).getValue();
    }

    @Override
    public boolean isAttributeSpecified(int i) {
        return this.getAttribute(i).getValue() != null;
    }

    @Override
    public int getNamespaceCount() {
        return this.getCurrentFrame().prefixes.size();
    }

    @Override
    public String getNamespacePrefix(int i) {
        return this.getCurrentFrame().prefixes.get(i);
    }

    @Override
    public String getNamespaceURI(int i) {
        return this.getCurrentFrame().uris.get(i);
    }

    @Override
    public NamespaceContext getNamespaceContext() {
        return this.context;
    }

    @Override
    public String getText() {
        if (this.content instanceof Text) {
            return ((Text)this.content).getData();
        }
        if (this.content instanceof Comment) {
            return ((Comment)this.content).getData();
        }
        return DOMUtils.getRawContent(this.getCurrentNode());
    }

    @Override
    public char[] getTextCharacters() {
        return this.getText().toCharArray();
    }

    @Override
    public int getTextStart() {
        return 0;
    }

    @Override
    public int getTextLength() {
        return this.getText().length();
    }

    @Override
    public String getEncoding() {
        return null;
    }

    @Override
    public QName getName() {
        Node el = this.getCurrentNode();
        String prefix = this.getPrefix();
        String ln = this.getLocalName();
        return new QName(el.getNamespaceURI(), ln, prefix);
    }

    @Override
    public String getLocalName() {
        String ln = this.getCurrentNode().getLocalName();
        if (ln == null && (ln = this.getCurrentNode().getNodeName()).indexOf(58) != -1) {
            ln = ln.substring(ln.indexOf(58) + 1);
        }
        return ln;
    }

    @Override
    public String getNamespaceURI() {
        String ln = this.getCurrentNode().getLocalName();
        if (ln == null) {
            ln = this.getCurrentNode().getNodeName();
            ln = ln.indexOf(58) == -1 ? this.getNamespaceURI("") : this.getNamespaceURI(ln.substring(0, ln.indexOf(58)));
            return ln;
        }
        return this.getCurrentNode().getNamespaceURI();
    }

    @Override
    public String getPrefix() {
        String prefix = this.getCurrentNode().getPrefix();
        if (prefix == null) {
            String nodeName = this.getCurrentNode().getNodeName();
            prefix = nodeName.indexOf(58) != -1 ? nodeName.substring(0, nodeName.indexOf(58)) : "";
        }
        return prefix;
    }

    @Override
    public String getPITarget() {
        return ((ProcessingInstruction)this.content).getTarget();
    }

    @Override
    public String getPIData() {
        return ((ProcessingInstruction)this.content).getData();
    }

    @Override
    public Location getLocation() {
        try {
            Object o = this.getCurrentNode().getUserData("location");
            if (o instanceof Location) {
                return (Location)o;
            }
        }
        catch (Throwable throwable) {
            // empty catch block
        }
        return super.getLocation();
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

