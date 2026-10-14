/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.staxutils;

import java.util.ArrayList;
import java.util.Iterator;
import javax.xml.namespace.NamespaceContext;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class W3CNamespaceContext
implements NamespaceContext {
    private Element currentNode;
    private NamespaceContext outNamespaceContext;

    public W3CNamespaceContext() {
    }

    public W3CNamespaceContext(Element el) {
        this.currentNode = el;
    }

    public void setOutNamespaceContext(NamespaceContext context) {
        this.outNamespaceContext = context;
    }

    @Override
    public String getNamespaceURI(String prefix) {
        return this.getNamespaceURI(this.currentNode, !prefix.isEmpty() ? "xmlns:" + prefix : "xmlns");
    }

    private String getNamespaceURI(Element e, String name) {
        String result;
        if (e == null) {
            return null;
        }
        if (this.outNamespaceContext != null && (result = this.outNamespaceContext.getNamespaceURI(name)) != null) {
            return result;
        }
        Attr attr = e.getAttributeNode(name);
        if (attr == null) {
            Node n = e.getParentNode();
            if (n instanceof Element && n != e) {
                return this.getNamespaceURI((Element)n, name);
            }
        } else {
            return attr.getValue();
        }
        return null;
    }

    @Override
    public String getPrefix(String uri) {
        return this.getPrefix(this.currentNode, uri);
    }

    private String getPrefix(Element e, String uri) {
        Node n;
        String result;
        if (e == null) {
            return null;
        }
        if (this.outNamespaceContext != null && (result = this.outNamespaceContext.getPrefix(uri)) != null) {
            return result;
        }
        NamedNodeMap attributes = e.getAttributes();
        if (attributes != null) {
            for (int i = 0; i < attributes.getLength(); ++i) {
                Attr a = (Attr)attributes.item(i);
                String val = a.getValue();
                if (val == null || !val.equals(uri)) continue;
                String name = a.getLocalName();
                if ("xmlns".equals(name)) {
                    return "";
                }
                return name;
            }
        }
        if ((n = e.getParentNode()) instanceof Element && n != e) {
            return this.getPrefix((Element)n, uri);
        }
        return null;
    }

    @Override
    public Iterator<String> getPrefixes(String uri) {
        ArrayList<String> prefixes = new ArrayList<String>();
        String prefix = this.getPrefix(uri);
        if (prefix != null) {
            prefixes.add(prefix);
        }
        return prefixes.iterator();
    }

    public Element getElement() {
        return this.currentNode;
    }

    public void setElement(Element node) {
        this.currentNode = node;
    }
}

