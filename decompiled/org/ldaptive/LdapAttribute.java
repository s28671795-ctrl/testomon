/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive;

import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.ldaptive.LdapUtils;
import org.ldaptive.dn.DefaultAttributeValueEscaper;

public class LdapAttribute {
    private static final int HASH_CODE_SEED = 10223;
    private static final String[] DEFAULT_BINARY_ATTRIBUTES = new String[]{"photo", "personalSignature", "audio", "jpegPhoto", "javaSerializedData", "thumbnailPhoto", "thumbnailLogo", "userCertificate", "cACertificate", "authorityRevocationList", "certificateRevocationList", "crossCertificatePair", "x500UniqueIdentifier"};
    private static final String[] BINARY_ATTRIBUTES;
    private String attributeName;
    private Set<ByteBuffer> attributeValues = new LinkedHashSet<ByteBuffer>();
    private boolean binary;

    public LdapAttribute() {
    }

    public LdapAttribute(String type) {
        this.setName(type);
    }

    public LdapAttribute(String type, byte[] ... value) {
        this.setName(type);
        this.addBinaryValues(value);
    }

    public LdapAttribute(String type, String ... value) {
        this.setName(type);
        this.addStringValues(value);
    }

    public void setName(String type) {
        block3: {
            block2: {
                this.attributeName = type;
                if (this.getOptions().contains("binary")) break block2;
                if (!Stream.of(BINARY_ATTRIBUTES).anyMatch(this.attributeName::equals)) break block3;
            }
            this.setBinary(true);
        }
    }

    public boolean isBinary() {
        return this.binary;
    }

    public void setBinary(boolean b) {
        this.binary = b;
    }

    public void configureBinary(String ... attrNames) {
        if (this.binary) {
            return;
        }
        if (attrNames != null && attrNames.length > 0) {
            for (String s2 : attrNames) {
                if (!this.attributeName.equals(s2)) continue;
                this.binary = true;
                break;
            }
        }
    }

    public String getName() {
        return this.attributeName;
    }

    public String getName(boolean withOptions) {
        if (withOptions) {
            return this.attributeName;
        }
        int optionIndex = this.attributeName.indexOf(";");
        return optionIndex > 0 ? this.attributeName.substring(0, optionIndex) : this.attributeName;
    }

    public List<String> getOptions() {
        String[] split;
        if (this.attributeName.indexOf(";") > 0 && (split = this.attributeName.split(";")).length > 1) {
            return IntStream.range(1, split.length).mapToObj(i -> split[i]).collect(Collectors.toUnmodifiableList());
        }
        return Collections.emptyList();
    }

    public byte[] getBinaryValue() {
        return this.attributeValues.isEmpty() ? null : this.attributeValues.iterator().next().array();
    }

    public Collection<byte[]> getBinaryValues() {
        if (this.attributeValues.isEmpty()) {
            return Collections.emptySet();
        }
        return this.attributeValues.stream().map(ByteBuffer::array).collect(Collectors.toUnmodifiableList());
    }

    public String getStringValue() {
        if (this.attributeValues.isEmpty()) {
            return null;
        }
        ByteBuffer val = this.attributeValues.iterator().next();
        return this.binary ? LdapUtils.base64Encode(val.array()) : LdapUtils.utf8Encode(val.array());
    }

    public Collection<String> getStringValues() {
        if (this.attributeValues.isEmpty()) {
            return Collections.emptySet();
        }
        return this.attributeValues.stream().map(v -> {
            if (this.binary) {
                return LdapUtils.base64Encode(v.array());
            }
            return LdapUtils.utf8Encode(v.array(), false);
        }).collect(Collectors.toUnmodifiableList());
    }

    public <T> T getValue(Function<byte[], T> func) {
        return this.attributeValues.isEmpty() ? null : (T)func.apply(this.attributeValues.iterator().next().array());
    }

    public <T> Collection<T> getValues(Function<byte[], T> func) {
        return this.attributeValues.stream().filter(Objects::nonNull).map(ByteBuffer::array).map(func).collect(Collectors.toUnmodifiableList());
    }

    public void addBinaryValues(byte[] ... value) {
        Stream.of(value).filter(Objects::nonNull).map(ByteBuffer::wrap).forEach(this.attributeValues::add);
    }

    public void addBinaryValues(Collection<byte[]> values2) {
        values2.stream().filter(Objects::nonNull).map(ByteBuffer::wrap).forEach(this.attributeValues::add);
    }

    public void addStringValues(String ... value) {
        Stream.of(value).filter(Objects::nonNull).map(v -> {
            if (this.binary) {
                try {
                    return LdapUtils.base64Decode(v);
                }
                catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException("Error decoding " + v + " for " + this.attributeName, e);
                }
            }
            return LdapUtils.utf8Encode(v, false);
        }).filter(Objects::nonNull).map(ByteBuffer::wrap).forEach(this.attributeValues::add);
    }

    public void addStringValues(Collection<String> values2) {
        values2.stream().filter(Objects::nonNull).map(v -> {
            if (this.binary) {
                try {
                    return LdapUtils.base64Decode(v);
                }
                catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException("Error decoding " + v + " for " + this.attributeName, e);
                }
            }
            return LdapUtils.utf8Encode(v, false);
        }).filter(Objects::nonNull).map(ByteBuffer::wrap).forEach(this.attributeValues::add);
    }

    public void addBufferValues(ByteBuffer ... values2) {
        Stream.of(values2).filter(Objects::nonNull).forEach(this.attributeValues::add);
    }

    public void addBufferValues(Collection<ByteBuffer> values2) {
        values2.stream().filter(Objects::nonNull).forEach(this.attributeValues::add);
    }

    public <T> void addValues(Function<T, byte[]> func, T ... value) {
        Stream.of(value).filter(Objects::nonNull).map(func::apply).filter(Objects::nonNull).map(ByteBuffer::wrap).forEach(this.attributeValues::add);
    }

    public <T> void addValues(Function<T, byte[]> func, Collection<T> values2) {
        values2.stream().filter(Objects::nonNull).map(func::apply).filter(Objects::nonNull).map(ByteBuffer::wrap).forEach(this.attributeValues::add);
    }

    public void removeBinaryValues(byte[] ... value) {
        Stream.of(value).filter(Objects::nonNull).map(ByteBuffer::wrap).forEach(this.attributeValues::remove);
    }

    public void removeBinaryValues(Collection<byte[]> values2) {
        values2.stream().filter(Objects::nonNull).map(ByteBuffer::wrap).forEach(this.attributeValues::add);
    }

    public void removeStringValues(String ... value) {
        Stream.of(value).filter(Objects::nonNull).map(v -> {
            if (this.binary) {
                try {
                    return LdapUtils.base64Decode(v);
                }
                catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException("Error decoding " + v + " for " + this.attributeName, e);
                }
            }
            return LdapUtils.utf8Encode(v, false);
        }).filter(Objects::nonNull).map(ByteBuffer::wrap).forEach(this.attributeValues::remove);
    }

    public void removeStringValues(Collection<String> values2) {
        values2.stream().filter(Objects::nonNull).map(v -> {
            if (this.binary) {
                try {
                    return LdapUtils.base64Decode(v);
                }
                catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException("Error decoding " + v + " for " + this.attributeName, e);
                }
            }
            return LdapUtils.utf8Encode(v, false);
        }).filter(Objects::nonNull).map(ByteBuffer::wrap).forEach(this.attributeValues::remove);
    }

    public void removeBufferValues(ByteBuffer ... values2) {
        Stream.of(values2).filter(Objects::nonNull).forEach(this.attributeValues::remove);
    }

    public void removeBufferValues(Collection<ByteBuffer> values2) {
        values2.stream().filter(Objects::nonNull).forEach(this.attributeValues::remove);
    }

    public int size() {
        return this.attributeValues.size();
    }

    public void clear() {
        this.attributeValues.clear();
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof LdapAttribute) {
            LdapAttribute v = (LdapAttribute)o;
            return LdapUtils.areEqual(this.attributeName != null ? this.attributeName.toLowerCase() : null, v.attributeName != null ? v.attributeName.toLowerCase() : null) && LdapUtils.areEqual(this.attributeValues, v.attributeValues);
        }
        return false;
    }

    public int hashCode() {
        return LdapUtils.computeHashCode(10223, this.attributeName != null ? this.attributeName.toLowerCase() : null, this.attributeValues);
    }

    public String toString() {
        return this.getClass().getName() + "@" + this.hashCode() + "::" + "name=" + this.attributeName + ", " + "values=" + this.getStringValues() + ", " + "binary=" + this.binary;
    }

    public static LdapAttribute sort(LdapAttribute la) {
        LdapAttribute sorted2 = new LdapAttribute(la.getName());
        if (la.isBinary()) {
            sorted2.setBinary(true);
            Set newValues = la.getBinaryValues().stream().sorted((o1, o2) -> {
                ByteBuffer bb1 = ByteBuffer.wrap(o1);
                ByteBuffer bb2 = ByteBuffer.wrap(o2);
                return bb1.compareTo(bb2);
            }).collect(Collectors.toCollection(LinkedHashSet::new));
            sorted2.addBinaryValues(newValues);
        } else {
            Set newValues = la.getStringValues().stream().sorted(Comparator.comparing(String::toString)).collect(Collectors.toCollection(LinkedHashSet::new));
            sorted2.addStringValues(newValues);
        }
        return sorted2;
    }

    @Deprecated
    public static String escapeValue(String value) {
        DefaultAttributeValueEscaper escaper = new DefaultAttributeValueEscaper();
        return escaper.escape(value);
    }

    public static Builder builder() {
        return new Builder();
    }

    static {
        String[] split = System.getProperty("org.ldaptive.attribute.binary", "").split(",");
        BINARY_ATTRIBUTES = LdapUtils.concatArrays(DEFAULT_BINARY_ATTRIBUTES, new String[][]{split});
    }

    public static class Builder {
        private final LdapAttribute object = new LdapAttribute();

        protected Builder() {
        }

        public Builder name(String name) {
            this.object.setName(name);
            return this;
        }

        public <T> Builder values(Function<T, byte[]> func, T ... value) {
            this.object.addValues(func, value);
            return this;
        }

        public Builder values(byte[] ... values2) {
            this.object.addBinaryValues(values2);
            return this;
        }

        public Builder binaryValues(Collection<byte[]> values2) {
            this.object.addBinaryValues(values2);
            return this;
        }

        public Builder values(String ... values2) {
            this.object.addStringValues(values2);
            return this;
        }

        public Builder stringValues(Collection<String> values2) {
            this.object.addStringValues(values2);
            return this;
        }

        public Builder values(ByteBuffer ... values2) {
            this.object.addBufferValues(values2);
            return this;
        }

        public Builder bufferValues(Collection<ByteBuffer> values2) {
            this.object.addBufferValues(values2);
            return this;
        }

        public Builder binary(boolean b) {
            this.object.setBinary(b);
            return this;
        }

        public LdapAttribute build() {
            return this.object;
        }
    }
}

