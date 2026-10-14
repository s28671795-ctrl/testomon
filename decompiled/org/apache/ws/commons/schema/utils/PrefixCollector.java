/*
 * Decompiled with CFR 0.152.
 */
package org.apache.ws.commons.schema.utils;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public abstract class PrefixCollector {
    protected abstract void declare(String var1, String var2);

    public void searchLocalPrefixDeclarations(Node pNode) {
        short type = pNode.getNodeType();
        if (type == 1 || type == 9) {
            NamedNodeMap map = pNode.getAttributes();
            for (int i = 0; map != null && i < map.getLength(); ++i) {
                Node attr = map.item(i);
                String uri = attr.getNamespaceURI();
                if (!"http://www.w3.org/2000/xmlns/".equals(uri)) continue;
                String localName = attr.getLocalName();
                String prefix = "xmlns".equals(localName) ? "" : localName;
                this.declare(prefix, attr.getNodeValue());
            }
        }
    }

    public void searchAllPrefixDeclarations(Node pNode) {
        Node parent = pNode.getParentNode();
        if (parent != null) {
            this.searchAllPrefixDeclarations(parent);
        }
        this.searchLocalPrefixDeclarations(pNode);
    }
}

