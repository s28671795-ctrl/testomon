/*
 * Decompiled with CFR 0.152.
 */
package org.apache.neethi.builders.converters;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import javax.xml.namespace.QName;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public abstract class AbstractDOMConverter {
    public QName getQName(Element s2) {
        String name = s2.getLocalName();
        if (name == null) {
            name = s2.getTagName();
        }
        return new QName(s2.getNamespaceURI(), name);
    }

    public Map<QName, String> getAttributes(Element s2) {
        HashMap<QName, String> mp = new HashMap<QName, String>();
        NamedNodeMap attrs = s2.getAttributes();
        for (int x = 0; x < attrs.getLength(); ++x) {
            Attr attr = (Attr)attrs.item(x);
            String name = attr.getLocalName();
            if (name == null) {
                name = attr.getName();
            }
            mp.put(new QName(attr.getNamespaceURI(), name), attr.getValue());
        }
        return mp;
    }

    public Iterator<Element> getChildren(Element s2) {
        LinkedList<Element> children = new LinkedList<Element>();
        for (Node nd = s2.getFirstChild(); nd != null; nd = nd.getNextSibling()) {
            if (!(nd instanceof Element)) continue;
            children.add((Element)nd);
        }
        return children.listIterator();
    }
}

