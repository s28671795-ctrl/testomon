/*
 * Decompiled with CFR 0.152.
 */
package liquibase;

import java.lang.reflect.Type;
import java.util.SortedSet;
import java.util.TreeSet;
import liquibase.util.StringUtil;

public class ObjectMetaData {
    public SortedSet<Attribute> attributes = new TreeSet<Attribute>();

    public Attribute getAttribute(String attributeName) {
        for (Attribute attribute : this.attributes) {
            if (!attribute.name.equals(attributeName)) continue;
            return attribute;
        }
        return null;
    }

    public static class Attribute
    implements Comparable<Attribute> {
        public String name;
        public String description;
        public Boolean required;
        public Type type;

        public Attribute() {
        }

        public Attribute(String name) {
            this.name = name;
        }

        @Override
        public int compareTo(Attribute o) {
            return StringUtil.trimToEmpty(this.name).compareTo(StringUtil.trimToEmpty(o.name));
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof Attribute)) {
                return false;
            }
            return this.name.equals(((Attribute)obj).name);
        }

        public int hashCode() {
            return this.name.hashCode();
        }
    }
}

