/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.policy.model;

public class Header {
    private final String name;
    private final String namespace;

    public Header(String name, String namespace) {
        this.name = name;
        this.namespace = namespace;
    }

    public String getName() {
        return this.name;
    }

    public String getNamespace() {
        return this.namespace;
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof Header)) {
            return false;
        }
        Header that = (Header)object;
        if (this.name != null && !this.name.equals(that.name) || this.name == null && that.name != null) {
            return false;
        }
        return (this.namespace == null || this.namespace.equals(that.namespace)) && (this.namespace != null || that.namespace == null);
    }

    public int hashCode() {
        int result = 17;
        if (this.name != null) {
            result = 31 * result + this.name.hashCode();
        }
        if (this.namespace != null) {
            result = 31 * result + this.namespace.hashCode();
        }
        return 31 * result + super.hashCode();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('{');
        if (this.namespace != null) {
            stringBuilder.append(this.namespace);
        } else {
            stringBuilder.append('*');
        }
        stringBuilder.append('}');
        if (this.name != null) {
            stringBuilder.append(this.name);
        } else {
            stringBuilder.append('*');
        }
        return stringBuilder.toString();
    }
}

