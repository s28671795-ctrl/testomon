/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.staxutils.transform;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.xml.namespace.NamespaceContext;

public class DelegatingNamespaceContext
implements NamespaceContext {
    private final NamespaceContext nc;
    private final Map<String, String> nsMap;
    private final Deque<Map<String, String>> namespaces = new ArrayDeque<Map<String, String>>();
    private final Deque<Map<String, String>> prefixes = new ArrayDeque<Map<String, String>>();

    public DelegatingNamespaceContext(NamespaceContext nc, Map<String, String> nsMap) {
        this.nc = nc;
        this.nsMap = nsMap;
    }

    public void down() {
        this.namespaces.addFirst(new HashMap(8));
        this.prefixes.addFirst(new HashMap(8));
    }

    public void up() {
        this.namespaces.removeFirst();
        this.prefixes.removeFirst();
    }

    public void addPrefix(String prefix, String ns) {
        if (!this.namespaces.isEmpty()) {
            this.namespaces.getFirst().put(prefix, ns);
            this.prefixes.getFirst().put(ns, prefix);
        }
    }

    public String findUniquePrefix(String ns) {
        String prefix;
        if (ns.isEmpty()) {
            return null;
        }
        String existingPrefix = this.getPrefix(ns);
        if (existingPrefix != null) {
            return existingPrefix;
        }
        int i = 0;
        while (this.getNamespaceURI(prefix = "ps" + ++i) != null) {
        }
        this.addPrefix(prefix, ns);
        return prefix;
    }

    @Override
    public String getNamespaceURI(String prefix) {
        if (!this.namespaces.isEmpty()) {
            Map<String, String> cache = this.namespaces.getFirst();
            for (Map<String, String> nss : this.namespaces) {
                String ns = nss.get(prefix);
                if (ns == null) continue;
                if (cache != nss) {
                    cache.put(prefix, ns);
                }
                return ns;
            }
        }
        if ("xml".equals(prefix)) {
            return "http://www.w3.org/XML/1998/namespace";
        }
        if ("xmlns".equals(prefix)) {
            return "http://www.w3.org/2000/xmlns/";
        }
        String ns = this.nc.getNamespaceURI(prefix);
        if (ns != null && ns.length() > 0) {
            this.addPrefix(prefix, ns);
        }
        return ns;
    }

    @Override
    public String getPrefix(String ns) {
        if (ns.isEmpty()) {
            return null;
        }
        String value = this.nsMap.get(ns);
        if (value != null && value.isEmpty()) {
            return null;
        }
        if (value != null) {
            ns = value;
        }
        if (!this.prefixes.isEmpty()) {
            Map<String, String> cache = this.prefixes.getFirst();
            for (Map<String, String> pfs : this.prefixes) {
                String prefix = pfs.get(ns);
                if (prefix == null || !ns.equals(this.getNamespaceURI(prefix))) continue;
                if (pfs != cache) {
                    cache.put(ns, prefix);
                }
                return prefix;
            }
        }
        if ("http://www.w3.org/XML/1998/namespace".equals(ns)) {
            return "xml";
        }
        if ("http://www.w3.org/2000/xmlns/".equals(ns)) {
            return "xmlns";
        }
        String prefix = this.nc.getPrefix(ns);
        if (prefix != null) {
            this.addPrefix(prefix, ns);
        }
        return prefix;
    }

    @Override
    public Iterator<String> getPrefixes(String ns) {
        ArrayList<String> pl = new ArrayList<String>(this.namespaces.size());
        for (Map<String, String> nsp : this.namespaces) {
            for (Map.Entry<String, String> nse : nsp.entrySet()) {
                if (!ns.equals(nse.getValue()) || !ns.equals(this.getNamespaceURI(nse.getKey()))) continue;
                pl.add(nse.getKey());
            }
        }
        return pl.iterator();
    }
}

