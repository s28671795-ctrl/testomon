/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.helpers;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.xml.namespace.NamespaceContext;
import org.apache.cxf.helpers.DOMUtils;
import org.w3c.dom.Node;

public final class MapNamespaceContext
implements NamespaceContext {
    private Map<String, String> namespaces = new HashMap<String, String>();
    private Node targetNode;

    public MapNamespaceContext() {
    }

    public MapNamespaceContext(Map<String, String> ns) {
        this();
        this.namespaces = ns;
    }

    public MapNamespaceContext(Node node) {
        this();
        this.targetNode = node;
    }

    public void setTargetNode(Node node) {
        this.targetNode = node;
    }

    public void addNamespace(String prefix, String namespaceURI) {
        this.namespaces.put(prefix, namespaceURI);
    }

    public void addNamespaces(Map<String, String> ns) {
        this.namespaces.putAll(ns);
    }

    @Override
    public String getNamespaceURI(String prefix) {
        String uri;
        if (null == prefix) {
            throw new IllegalArgumentException("Null prefix to getNamespaceURI");
        }
        if ("xml".equals(prefix)) {
            return "http://www.w3.org/XML/1998/namespace";
        }
        if ("xmlns".equals(prefix)) {
            return "http://www.w3.org/2000/xmlns/";
        }
        if (this.targetNode != null && (uri = DOMUtils.getNamespace(this.targetNode, prefix)) != null) {
            return uri;
        }
        return this.namespaces.get(prefix);
    }

    @Override
    public String getPrefix(String namespaceURI) {
        if (namespaceURI == null) {
            throw new IllegalArgumentException("Null namespace to getPrefix");
        }
        if ("http://www.w3.org/XML/1998/namespace".equals(namespaceURI)) {
            return "xml";
        }
        if ("http://www.w3.org/2000/xmlns/".equals(namespaceURI)) {
            return "xmlns";
        }
        for (Map.Entry<String, String> e : this.namespaces.entrySet()) {
            if (!e.getValue().equals(namespaceURI)) continue;
            return e.getKey();
        }
        return null;
    }

    @Override
    public Iterator<String> getPrefixes(String namespaceURI) {
        return null;
    }

    public Map<String, String> getUsedNamespaces() {
        return this.namespaces;
    }
}

