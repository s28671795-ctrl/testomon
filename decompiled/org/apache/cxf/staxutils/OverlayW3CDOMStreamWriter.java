/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.staxutils;

import java.util.LinkedList;
import java.util.List;
import javax.xml.stream.XMLStreamException;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.staxutils.W3CDOMStreamWriter;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

public class OverlayW3CDOMStreamWriter
extends W3CDOMStreamWriter {
    protected boolean isOverlaid = true;
    List<Boolean> isOverlaidStack = new LinkedList<Boolean>();
    Boolean textOverlay;

    public OverlayW3CDOMStreamWriter(Document document) {
        super(document);
    }

    public OverlayW3CDOMStreamWriter(Element e) {
        super(e);
    }

    public OverlayW3CDOMStreamWriter(Document doc, Element e) {
        super(doc, e);
    }

    public OverlayW3CDOMStreamWriter(Document doc, DocumentFragment frag) {
        super(doc, frag);
        this.isOverlaid = false;
    }

    @Override
    protected void createAndAddElement(String prefix, String local, String namespace) {
        super.createAndAddElement(prefix, local, namespace);
        if (this.isOverlaid) {
            try {
                this.getCurrentNode().setUserData("new", "new", null);
            }
            catch (Throwable throwable) {
                // empty catch block
            }
        }
    }

    @Override
    public void writeEndElement() throws XMLStreamException {
        if (this.isOverlaid) {
            for (Node nd = this.getCurrentNode().getFirstChild(); nd != null; nd = nd.getNextSibling()) {
                try {
                    this.getCurrentNode().setUserData("new", null, null);
                    continue;
                }
                catch (Throwable throwable) {
                    // empty catch block
                }
            }
        }
        this.isOverlaid = this.isOverlaidStack.remove(0);
        super.writeEndElement();
        this.textOverlay = null;
    }

    @Override
    public void writeStartElement(String local) throws XMLStreamException {
        this.isOverlaidStack.add(0, this.isOverlaid);
        if (this.isOverlaid) {
            for (Node nd2 = (nd = this.getCurrentNode()) == null ? this.getDocument().getDocumentElement() : nd.getFirstChild(); nd2 != null; nd2 = nd2.getNextSibling()) {
                Object userData = null;
                try {
                    userData = nd2.getUserData("new");
                }
                catch (Throwable throwable) {
                    // empty catch block
                }
                if (nd2.getNodeType() != 1 || !local.equals(nd2.getLocalName()) || !StringUtils.isEmpty(nd2.getNamespaceURI()) || userData == null) continue;
                this.adjustOverlaidNode(nd2, null);
                this.setChild((Element)nd2, false);
                if (nd2.getFirstChild() == null) {
                    this.isOverlaid = false;
                    this.textOverlay = null;
                }
                return;
            }
        }
        super.writeStartElement(local);
        this.isOverlaid = false;
        this.textOverlay = Boolean.FALSE;
    }

    protected void adjustOverlaidNode(Node nd2, String pfx) {
    }

    @Override
    public void writeStartElement(String namespace, String local) throws XMLStreamException {
        this.isOverlaidStack.add(0, this.isOverlaid);
        if (this.isOverlaid) {
            for (Node nd2 = (nd = this.getCurrentNode()) == null ? this.getDocument().getDocumentElement() : nd.getFirstChild(); nd2 != null; nd2 = nd2.getNextSibling()) {
                Object userData = null;
                try {
                    userData = nd2.getUserData("new");
                }
                catch (Throwable throwable) {
                    // empty catch block
                }
                if (nd2.getNodeType() != 1 || !local.equals(nd2.getLocalName()) || !namespace.equals(nd2.getNamespaceURI()) || userData != null) continue;
                this.adjustOverlaidNode(nd2, "");
                this.setChild((Element)nd2, false);
                if (nd2.getFirstChild() == null) {
                    this.isOverlaid = false;
                    this.textOverlay = null;
                }
                return;
            }
        }
        super.writeStartElement(namespace, local);
        this.isOverlaid = false;
        this.textOverlay = false;
    }

    @Override
    public void writeStartElement(String prefix, String local, String namespace) throws XMLStreamException {
        if (prefix == null || prefix.isEmpty()) {
            this.writeStartElement(namespace, local);
        } else {
            this.isOverlaidStack.add(0, this.isOverlaid);
            if (this.isOverlaid) {
                for (Node nd2 = (nd = this.getCurrentNode()) == null ? this.getDocument().getDocumentElement() : nd.getFirstChild(); nd2 != null; nd2 = nd2.getNextSibling()) {
                    Object userData = null;
                    try {
                        userData = nd2.getUserData("new");
                    }
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                    if (nd2.getNodeType() != 1 || !local.equals(nd2.getLocalName()) || !namespace.equals(nd2.getNamespaceURI()) || userData != null) continue;
                    this.adjustOverlaidNode(nd2, prefix);
                    this.setChild((Element)nd2, false);
                    if (nd2.getFirstChild() == null) {
                        this.isOverlaid = false;
                        this.textOverlay = null;
                    }
                    return;
                }
            }
            super.writeStartElement(prefix, local, namespace);
            this.isOverlaid = false;
            this.textOverlay = false;
        }
    }

    @Override
    public void writeCharacters(String text) throws XMLStreamException {
        if (!this.isOverlaid || this.textOverlay == null) {
            super.writeCharacters(text);
        } else if (!this.textOverlay.booleanValue()) {
            Element nd = this.getCurrentNode();
            Node txt = nd.getFirstChild();
            if (txt instanceof Text && ((Text)txt).getTextContent().startsWith(text)) {
                this.textOverlay = true;
                return;
            }
            super.writeCharacters(text);
        }
    }
}

