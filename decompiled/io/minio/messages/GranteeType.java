/*
 * Decompiled with CFR 0.152.
 */
package io.minio.messages;

import org.simpleframework.xml.Root;
import org.simpleframework.xml.convert.Convert;
import org.simpleframework.xml.convert.Converter;
import org.simpleframework.xml.stream.InputNode;
import org.simpleframework.xml.stream.OutputNode;

@Root(name="Type")
@Convert(value=GranteeTypeConverter.class)
public enum GranteeType {
    CANONICAL_USER("CanonicalUser"),
    AMAZON_CUSTOMER_BY_EMAIL("AmazonCustomerByEmail"),
    GROUP("Group");

    private final String value;

    private GranteeType(String value) {
        this.value = value;
    }

    public String toString() {
        return this.value;
    }

    public static GranteeType fromString(String granteeTypeString) {
        for (GranteeType granteeType : GranteeType.values()) {
            if (!granteeTypeString.equals(granteeType.value)) continue;
            return granteeType;
        }
        throw new IllegalArgumentException("Unknown grantee type '" + granteeTypeString + "'");
    }

    public static class GranteeTypeConverter
    implements Converter<GranteeType> {
        @Override
        public GranteeType read(InputNode node) throws Exception {
            return GranteeType.fromString(node.getValue());
        }

        @Override
        public void write(OutputNode node, GranteeType granteeType) throws Exception {
            node.setValue(granteeType.toString());
        }
    }
}

