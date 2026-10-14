/*
 * Decompiled with CFR 0.152.
 */
package org.apache.neethi.builders.converters;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public abstract class AbstractStaxConverter {
    public QName getQName(XMLStreamReader s2) {
        if (s2.getEventType() == 7) {
            try {
                s2.nextTag();
            }
            catch (XMLStreamException xMLStreamException) {
                // empty catch block
            }
        }
        return new QName(s2.getNamespaceURI(), s2.getLocalName());
    }

    public Map<QName, String> getAttributes(XMLStreamReader s2) {
        int x;
        HashMap<QName, String> mp = new HashMap<QName, String>();
        for (x = 0; x < s2.getAttributeCount(); ++x) {
            mp.put(new QName(s2.getAttributeNamespace(x), s2.getAttributeLocalName(x)), s2.getAttributeValue(x));
        }
        for (x = 0; x < s2.getNamespaceCount(); ++x) {
            String pfx = s2.getNamespacePrefix(x);
            if (pfx == null) {
                mp.put(new QName("http://www.w3.org/2000/xmlns/", "xmlns"), s2.getNamespaceURI(x));
                continue;
            }
            mp.put(new QName("http://www.w3.org/2000/xmlns/", pfx, "xmlns"), s2.getNamespaceURI(x));
        }
        return mp;
    }

    public Iterator<XMLStreamReader> getChildren(final XMLStreamReader s2) {
        final QName base = s2.getName();
        try {
            int evt = s2.getEventType();
            if (s2.hasNext()) {
                evt = s2.next();
            }
            while (s2.hasNext() && evt != 2 && evt != 1) {
                evt = s2.next();
            }
        }
        catch (Exception ex) {
            return null;
        }
        return new Iterator<XMLStreamReader>(){
            boolean first = true;

            @Override
            public boolean hasNext() {
                if (this.first) {
                    this.first = false;
                    return s2.getEventType() != 2;
                }
                try {
                    int evt = s2.next();
                    while (s2.hasNext() && evt != 2 && evt != 1) {
                        evt = s2.next();
                    }
                    if (evt == 2 && !s2.getName().equals(base)) {
                        evt = s2.next();
                        while (s2.hasNext() && evt != 2 && evt != 1) {
                            evt = s2.next();
                        }
                    }
                    return evt == 1;
                }
                catch (Exception ex) {
                    return false;
                }
            }

            @Override
            public XMLStreamReader next() {
                return s2;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }
}

