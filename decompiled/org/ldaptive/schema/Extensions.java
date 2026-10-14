/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.schema;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.ldaptive.LdapUtils;
import org.ldaptive.schema.SchemaUtils;

public class Extensions {
    private static final int HASH_CODE_SEED = 1171;
    private final Map<String, List<String>> extensions = new LinkedHashMap<String, List<String>>();

    public Extensions() {
    }

    public Extensions(String name, List<String> values2) {
        this.addExtension(name, values2);
    }

    public Set<String> getNames() {
        return this.extensions.keySet();
    }

    public List<String> getValues(String name) {
        return this.extensions.get(name);
    }

    public String getValue(String name) {
        List<String> values2 = this.getValues(name);
        if (values2 == null || values2.isEmpty()) {
            return null;
        }
        return values2.iterator().next();
    }

    public Map<String, List<String>> getAllValues() {
        return Collections.unmodifiableMap(this.extensions);
    }

    public void addExtension(String name) {
        this.extensions.put(name, new ArrayList(0));
    }

    public void addExtension(String name, List<String> values2) {
        this.extensions.put(name, values2);
    }

    public int size() {
        return this.extensions.size();
    }

    public boolean isEmpty() {
        return this.extensions.isEmpty();
    }

    public String format() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, List<String>> entry : this.extensions.entrySet()) {
            sb.append(entry.getKey()).append(" ");
            if (entry.getValue() == null || entry.getValue().isEmpty()) continue;
            sb.append(SchemaUtils.formatDescriptors(entry.getValue().toArray(new String[0])));
        }
        return sb.toString();
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof Extensions) {
            Extensions v = (Extensions)o;
            return LdapUtils.areEqual(this.extensions, v.extensions);
        }
        return false;
    }

    public int hashCode() {
        return LdapUtils.computeHashCode(1171, this.extensions);
    }

    public String toString() {
        return "[" + this.getClass().getName() + "@" + this.hashCode() + "::" + "extensions=" + this.extensions + "]";
    }
}

