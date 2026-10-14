/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.ldaptive.LdapUtils;
import org.ldaptive.filter.FilterUtils;

public class FilterTemplate {
    private static final int HASH_CODE_SEED = 311;
    private String searchFilter;
    private final Map<String, Object> parameters = new HashMap<String, Object>();

    public FilterTemplate() {
    }

    public FilterTemplate(String filter2) {
        this.setFilter(filter2);
    }

    public FilterTemplate(String filter2, Object[] params) {
        this.setFilter(filter2);
        this.setParameters(params);
    }

    public String getFilter() {
        return this.searchFilter;
    }

    public void setFilter(String filter2) {
        this.searchFilter = filter2;
    }

    public Map<String, Object> getParameters() {
        return Collections.unmodifiableMap(this.parameters);
    }

    public void setParameter(int position, Object value) {
        this.parameters.put(Integer.toString(position), value);
    }

    public void setParameter(String name, Object value) {
        this.parameters.put(name, value);
    }

    public void setParameters(Object[] values2) {
        int i = 0;
        for (Object o : values2) {
            this.parameters.put(Integer.toString(i++), o);
        }
    }

    public String format() {
        String s2 = this.searchFilter;
        if (!this.parameters.isEmpty()) {
            for (Map.Entry<String, Object> e : this.parameters.entrySet()) {
                String encoded = FilterTemplate.encode(e.getValue());
                if (encoded == null) continue;
                s2 = s2.replace("{" + e.getKey() + "}", encoded);
            }
        }
        return s2;
    }

    public static String encodeValue(byte[] value) {
        if (value == null) {
            return null;
        }
        char[] c = LdapUtils.hexEncode(value);
        StringBuilder sb = new StringBuilder(c.length + c.length / 2);
        for (int i = 0; i < c.length; i += 2) {
            sb.append('\\').append(c[i]).append(c[i + 1]);
        }
        return sb.toString();
    }

    public static String encodeValue(String value) {
        if (value == null) {
            return null;
        }
        return FilterUtils.escape(value);
    }

    protected static String encode(Object obj) {
        if (obj == null) {
            return null;
        }
        String str = obj instanceof String ? FilterTemplate.encodeValue((String)obj) : (obj instanceof byte[] ? FilterTemplate.encodeValue((byte[])obj) : FilterTemplate.encodeValue(obj.toString()));
        return str;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof FilterTemplate) {
            FilterTemplate v = (FilterTemplate)o;
            return LdapUtils.areEqual(this.searchFilter, v.searchFilter) && LdapUtils.areEqual(this.parameters, v.parameters);
        }
        return false;
    }

    public int hashCode() {
        return LdapUtils.computeHashCode(311, this.searchFilter, this.parameters);
    }

    public String toString() {
        return "[" + this.getClass().getName() + "@" + this.hashCode() + "::" + "filter=" + this.searchFilter + ", " + "parameters=" + this.parameters + "]";
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final FilterTemplate object = new FilterTemplate();

        protected Builder() {
        }

        public Builder filter(String filter2) {
            this.object.setFilter(filter2);
            return this;
        }

        public Builder parameter(String name, String value) {
            this.object.setParameter(name, (Object)value);
            return this;
        }

        public Builder parameter(String name, Object value) {
            this.object.setParameter(name, value);
            return this;
        }

        public Builder parameter(int pos, String value) {
            this.object.setParameter(pos, (Object)value);
            return this;
        }

        public Builder parameter(int pos, Object value) {
            this.object.setParameter(pos, value);
            return this;
        }

        public Builder parameters(Object ... values2) {
            this.object.setParameters(values2);
            return this;
        }

        public FilterTemplate build() {
            return this.object;
        }
    }
}

