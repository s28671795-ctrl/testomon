/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.dn;

import java.nio.ByteBuffer;
import java.util.function.Function;
import org.ldaptive.LdapUtils;

public class NameValue {
    private static final int HASH_CODE_SEED = 5011;
    private final String attributeName;
    private final ByteBuffer attributeValue;

    public NameValue(String name, String value) {
        this(name, LdapUtils.utf8Encode(value));
    }

    public NameValue(String name, byte[] value) {
        this.attributeName = name;
        this.attributeValue = value != null ? ByteBuffer.wrap(value) : null;
    }

    public String getName() {
        return this.attributeName;
    }

    public byte[] getBinaryValue() {
        return this.attributeValue != null ? this.attributeValue.array() : null;
    }

    public String getStringValue() {
        return this.attributeValue != null ? LdapUtils.utf8Encode(this.attributeValue.array()) : null;
    }

    public <T> T getValue(Function<byte[], T> func) {
        return this.attributeValue != null ? (T)func.apply(this.attributeValue.array()) : null;
    }

    public boolean hasName(String name) {
        return this.attributeName.equalsIgnoreCase(name);
    }

    public String format() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.attributeName).append("=").append(LdapUtils.utf8Encode(this.attributeValue != null ? this.attributeValue.array() : null));
        return sb.toString();
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof NameValue) {
            NameValue v = (NameValue)o;
            return LdapUtils.areEqual(this.attributeName != null ? this.attributeName.toLowerCase() : null, v.attributeName != null ? v.attributeName.toLowerCase() : null) && LdapUtils.areEqual(this.attributeValue, v.attributeValue);
        }
        return false;
    }

    public int hashCode() {
        return LdapUtils.computeHashCode(5011, this.attributeName != null ? this.attributeName.toLowerCase() : null, this.attributeValue);
    }

    public String toString() {
        return this.getClass().getName() + "@" + this.hashCode() + "::" + "name=" + this.attributeName + ", " + "value=" + this.getStringValue();
    }
}

