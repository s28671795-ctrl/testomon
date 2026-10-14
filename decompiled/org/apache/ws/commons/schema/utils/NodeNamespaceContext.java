/*
 * Decompiled with CFR 0.152.
 */
package org.apache.ws.commons.schema.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.apache.ws.commons.schema.utils.NamespacePrefixList;
import org.apache.ws.commons.schema.utils.PrefixCollector;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public final class NodeNamespaceContext
implements NamespacePrefixList,
Serializable {
    private static final long serialVersionUID = 1L;
    private static final boolean DOM_LEVEL_3;
    private static final Collection<String> XML_NS_PREFIX_COLLECTION;
    private static final Collection<String> XMLNS_ATTRIBUTE_COLLECTION;
    private final Map<String, String> declarations;
    private String[] prefixes;

    private NodeNamespaceContext(Map<String, String> decls) {
        this.declarations = decls;
    }

    public static String getNamespacePrefix(Element el, String ns) {
        if (DOM_LEVEL_3) {
            return NodeNamespaceContext.getNamespacePrefixDomLevel3(el, ns);
        }
        return NodeNamespaceContext.getNamespaceContext(el).getPrefix(ns);
    }

    private static String getNamespacePrefixDomLevel3(Element el, String ns) {
        return el.lookupPrefix(ns);
    }

    public static String getNamespaceURI(Element el, String pfx) {
        if ("xml".equals(pfx)) {
            return "http://www.w3.org/XML/1998/namespace";
        }
        if ("xmlns".equals(pfx)) {
            return "http://www.w3.org/2000/xmlns/";
        }
        if (DOM_LEVEL_3) {
            return NodeNamespaceContext.getNamespaceURIDomLevel3(el, pfx);
        }
        return NodeNamespaceContext.getNamespaceContext(el).getNamespaceURI(pfx);
    }

    private static String getNamespaceURIDomLevel3(Element el, String pfx) {
        if ("".equals(pfx)) {
            pfx = null;
        }
        return el.lookupNamespaceURI(pfx);
    }

    public static NodeNamespaceContext getNamespaceContext(Node pNode) {
        final HashMap<String, String> declarations = new HashMap<String, String>();
        new PrefixCollector(){

            @Override
            protected void declare(String pPrefix, String pNamespaceURI) {
                declarations.put(pPrefix, pNamespaceURI);
            }
        }.searchAllPrefixDeclarations(pNode);
        return new NodeNamespaceContext(declarations);
    }

    @Override
    public String[] getDeclaredPrefixes() {
        if (this.prefixes == null) {
            Set<String> keys2 = this.declarations.keySet();
            this.prefixes = keys2.toArray(new String[keys2.size()]);
        }
        return this.prefixes;
    }

    @Override
    public String getNamespaceURI(String pPrefix) {
        if (pPrefix == null) {
            throw new IllegalArgumentException("The prefix must not be null.");
        }
        if ("xml".equals(pPrefix)) {
            return "http://www.w3.org/XML/1998/namespace";
        }
        if ("xmlns".equals(pPrefix)) {
            return "http://www.w3.org/2000/xmlns/";
        }
        String uri = this.declarations.get(pPrefix);
        return uri == null ? "" : uri;
    }

    @Override
    public String getPrefix(String pNamespaceURI) {
        if (pNamespaceURI == null) {
            throw new IllegalArgumentException("The namespace URI must not be null.");
        }
        if ("http://www.w3.org/XML/1998/namespace".equals(pNamespaceURI)) {
            return "xml";
        }
        if ("http://www.w3.org/2000/xmlns/".equals(pNamespaceURI)) {
            return "xmlns";
        }
        for (Map.Entry<String, String> entry : this.declarations.entrySet()) {
            if (!pNamespaceURI.equals(entry.getValue())) continue;
            return entry.getKey();
        }
        return null;
    }

    @Override
    public Iterator<String> getPrefixes(String pNamespaceURI) {
        if (pNamespaceURI == null) {
            throw new IllegalArgumentException("The namespace URI must not be null.");
        }
        if ("http://www.w3.org/XML/1998/namespace".equals(pNamespaceURI)) {
            return XML_NS_PREFIX_COLLECTION.iterator();
        }
        if ("http://www.w3.org/2000/xmlns/".equals(pNamespaceURI)) {
            return XMLNS_ATTRIBUTE_COLLECTION.iterator();
        }
        ArrayList<String> list = new ArrayList<String>();
        for (Map.Entry<String, String> entry : this.declarations.entrySet()) {
            if (!pNamespaceURI.equals(entry.getValue())) continue;
            list.add(entry.getKey());
        }
        return list.iterator();
    }

    static {
        XML_NS_PREFIX_COLLECTION = Collections.singletonList("xml");
        XMLNS_ATTRIBUTE_COLLECTION = Collections.singletonList("xmlns");
        boolean level3 = false;
        try {
            Class<?> cls = Class.forName("org.w3c.dom.UserDataHandler", false, Node.class.getClassLoader());
            Node.class.getMethod("getUserData", String.class);
            Node.class.getMethod("setUserData", String.class, Object.class, cls);
            level3 = true;
        }
        catch (Throwable e) {
            level3 = false;
        }
        DOM_LEVEL_3 = level3;
    }
}

