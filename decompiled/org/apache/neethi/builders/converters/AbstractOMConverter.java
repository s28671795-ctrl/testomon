/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.axiom.om.OMAttribute
 *  org.apache.axiom.om.OMElement
 */
package org.apache.neethi.builders.converters;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.xml.namespace.QName;
import org.apache.axiom.om.OMAttribute;
import org.apache.axiom.om.OMElement;

public abstract class AbstractOMConverter {
    public QName getQName(OMElement s2) {
        if (s2.getNamespace() == null) {
            return new QName(s2.getLocalName());
        }
        return new QName(s2.getNamespace().getNamespaceURI(), s2.getLocalName());
    }

    public Map<QName, String> getAttributes(OMElement s2) {
        HashMap<QName, String> mp = new HashMap<QName, String>();
        Iterator it = s2.getAllAttributes();
        while (it.hasNext()) {
            OMAttribute attr = (OMAttribute)it.next();
            if (attr.getNamespace() == null) {
                mp.put(new QName("", attr.getLocalName()), attr.getAttributeValue());
                continue;
            }
            mp.put(new QName(attr.getNamespace().getNamespaceURI(), attr.getLocalName()), attr.getAttributeValue());
        }
        return mp;
    }

    public Iterator<OMElement> getChildren(OMElement el) {
        return el.getChildElements();
    }
}

