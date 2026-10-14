/*
 * Decompiled with CFR 0.152.
 */
package org.simpleframework.xml.stream;

import java.util.Iterator;
import java.util.LinkedHashMap;
import org.simpleframework.xml.stream.NamespaceMap;
import org.simpleframework.xml.stream.OutputNode;

class PrefixResolver
extends LinkedHashMap<String, String>
implements NamespaceMap {
    private final OutputNode source;

    public PrefixResolver(OutputNode source2) {
        this.source = source2;
    }

    @Override
    public String getPrefix() {
        return this.source.getPrefix();
    }

    @Override
    public String setReference(String reference) {
        return this.setReference(reference, "");
    }

    @Override
    public String setReference(String reference, String prefix) {
        String parent = this.resolvePrefix(reference);
        if (parent != null) {
            return null;
        }
        return this.put(reference, prefix);
    }

    @Override
    public String getPrefix(String reference) {
        String prefix;
        int size = this.size();
        if (size > 0 && (prefix = (String)this.get(reference)) != null) {
            return prefix;
        }
        return this.resolvePrefix(reference);
    }

    @Override
    public String getReference(String prefix) {
        if (this.containsValue(prefix)) {
            for (String reference : this) {
                String value = (String)this.get(reference);
                if (value == null || !value.equals(prefix)) continue;
                return reference;
            }
        }
        return this.resolveReference(prefix);
    }

    private String resolveReference(String prefix) {
        NamespaceMap parent = this.source.getNamespaces();
        if (parent != null) {
            return parent.getReference(prefix);
        }
        return null;
    }

    private String resolvePrefix(String reference) {
        String prefix;
        NamespaceMap parent = this.source.getNamespaces();
        if (parent != null && !this.containsValue(prefix = parent.getPrefix(reference))) {
            return prefix;
        }
        return null;
    }

    @Override
    public Iterator<String> iterator() {
        return this.keySet().iterator();
    }
}

