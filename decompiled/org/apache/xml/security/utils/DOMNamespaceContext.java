/*
 * Decompiled with CFR 0.152.
 */
package org.apache.xml.security.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.xml.namespace.NamespaceContext;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class DOMNamespaceContext
implements NamespaceContext {
    private Map<String, String> namespaceMap = new HashMap<String, String>();

    public DOMNamespaceContext(Node contextNode) {
        this.addNamespaces(contextNode);
    }

    @Override
    public String getNamespaceURI(String arg0) {
        return this.namespaceMap.get(arg0);
    }

    @Override
    public String getPrefix(String arg0) {
        for (Map.Entry<String, String> entry : this.namespaceMap.entrySet()) {
            if (!entry.getValue().equals(arg0)) continue;
            return entry.getKey();
        }
        return null;
    }

    @Override
    public Iterator<String> getPrefixes(String arg0) {
        return this.namespaceMap.keySet().iterator();
    }

    private void addNamespaces(Node element) {
        if (element.getParentNode() != null) {
            this.addNamespaces(element.getParentNode());
        }
        if (element instanceof Element) {
            Element el = (Element)element;
            NamedNodeMap map = el.getAttributes();
            for (int x = 0; x < map.getLength(); ++x) {
                Attr attr = (Attr)map.item(x);
                if (!"xmlns".equals(attr.getPrefix())) continue;
                this.namespaceMap.put(attr.getLocalName(), attr.getValue());
            }
        }
    }
}

