/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.common.jaxb;

import com.sun.xml.bind.marshaller.NamespacePrefixMapper;
import java.util.Map;

public final class NamespaceMapper
extends NamespacePrefixMapper {
    private static final String[] EMPTY_STRING = new String[0];
    private final Map<String, String> nspref;
    private String[] nsctxt = EMPTY_STRING;

    public NamespaceMapper(Map<String, String> nspref) {
        this.nspref = nspref;
    }

    @Override
    public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {
        String prefix = this.nspref.get(namespaceUri);
        if (prefix != null) {
            return prefix;
        }
        return suggestion;
    }

    public void setContextualNamespace(String[] contextualNamespaceDecls) {
        this.nsctxt = contextualNamespaceDecls;
    }

    @Override
    public String[] getContextualNamespaceDecls() {
        return this.nsctxt;
    }
}

