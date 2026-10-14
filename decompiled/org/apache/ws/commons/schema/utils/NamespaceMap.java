/*
 * Decompiled with CFR 0.152.
 */
package org.apache.ws.commons.schema.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.apache.ws.commons.schema.utils.NamespacePrefixList;

public class NamespaceMap
extends HashMap<String, Object>
implements NamespacePrefixList {
    private static final long serialVersionUID = 1L;

    public NamespaceMap() {
    }

    public NamespaceMap(Map<String, Object> map) {
        super(map);
    }

    public void add(String prefix, String namespaceURI) {
        this.put(prefix, namespaceURI);
    }

    @Override
    public String[] getDeclaredPrefixes() {
        Set keys2 = this.keySet();
        return keys2.toArray(new String[keys2.size()]);
    }

    @Override
    public String getNamespaceURI(String prefix) {
        if (prefix == null) {
            throw new IllegalArgumentException("The prefix must not be null.");
        }
        if ("xml".equals(prefix)) {
            return "http://www.w3.org/XML/1998/namespace";
        }
        if ("xmlns".equals(prefix)) {
            return "http://www.w3.org/2000/xmlns/";
        }
        Object namespaceURI = this.get(prefix);
        return namespaceURI == null ? "" : namespaceURI.toString();
    }

    @Override
    public String getPrefix(String namespaceURI) {
        for (Map.Entry entry : this.entrySet()) {
            if (!entry.getValue().toString().equals(namespaceURI)) continue;
            return (String)entry.getKey();
        }
        return null;
    }

    @Override
    public Iterator<String> getPrefixes(String namespaceURI) {
        ArrayList list = new ArrayList();
        for (Map.Entry entry : this.entrySet()) {
            if (!entry.getValue().toString().equals(namespaceURI)) continue;
            list.add(entry.getKey());
        }
        return list.iterator();
    }
}

