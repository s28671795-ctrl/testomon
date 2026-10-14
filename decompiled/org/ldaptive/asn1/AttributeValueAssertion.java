/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.asn1;

import java.util.ArrayList;
import org.ldaptive.LdapUtils;
import org.ldaptive.asn1.AbstractDERType;
import org.ldaptive.asn1.ConstructedDEREncoder;
import org.ldaptive.asn1.DERBuffer;
import org.ldaptive.asn1.DEREncoder;
import org.ldaptive.asn1.DERParser;
import org.ldaptive.asn1.DERPath;
import org.ldaptive.asn1.DERTag;
import org.ldaptive.asn1.OidType;
import org.ldaptive.asn1.UniversalDERTag;

@Deprecated
public class AttributeValueAssertion
extends AbstractDERType
implements DEREncoder {
    private static final int HASH_CODE_SEED = 613;
    private static final DERPath PATH = new DERPath("/SEQ");
    private final String attributeOid;
    private final Value attributeValue;

    public AttributeValueAssertion(String oid, Value value) {
        super(value.getTag());
        this.attributeOid = oid;
        this.attributeValue = value;
    }

    public String getOid() {
        return this.attributeOid;
    }

    public Value getValue() {
        return this.attributeValue;
    }

    @Override
    public byte[] encode() {
        ConstructedDEREncoder se = new ConstructedDEREncoder(UniversalDERTag.SEQ, () -> {
            OidType type = new OidType(this.attributeOid);
            return type.encode();
        }, () -> this.encode(new byte[][]{this.attributeValue.getBytes()}));
        return se.encode();
    }

    public static AttributeValueAssertion[] decode(DERBuffer encoded) {
        ArrayList assertions = new ArrayList();
        DERParser parser = new DERParser();
        parser.registerHandler(PATH, (p, e) -> {
            if (UniversalDERTag.OID.getTagNo() != p.readTag(e).getTagNo()) {
                throw new IllegalArgumentException("Expected OID tag");
            }
            int seqLimit = e.limit();
            int oidLength = p.readLength(e);
            e.limit(e.position() + oidLength);
            String oid = OidType.decode(e);
            e.limit(seqLimit);
            DERTag tag = p.readTag(e);
            p.readLength(e);
            assertions.add(new AttributeValueAssertion(oid, new Value(tag, e.getRemainingBytes())));
        });
        parser.parse(encoded);
        return assertions.toArray(new AttributeValueAssertion[0]);
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof AttributeValueAssertion) {
            AttributeValueAssertion v = (AttributeValueAssertion)o;
            return LdapUtils.areEqual(this.attributeOid, v.attributeOid) && LdapUtils.areEqual(this.attributeValue, v.attributeValue);
        }
        return false;
    }

    public int hashCode() {
        return LdapUtils.computeHashCode(613, this.attributeOid, this.attributeValue);
    }

    public String toString() {
        return "[" + this.getClass().getName() + "@" + this.hashCode() + "::" + "attributeOid=" + this.attributeOid + ", " + "attributeValue=" + this.attributeValue + "]";
    }

    public static class Value {
        private static final int HASH_CODE_SEED = 617;
        private final DERTag attributeValueTag;
        private final byte[] attributeValueBytes;

        public Value(DERTag tag, byte[] bytes) {
            this.attributeValueTag = tag;
            this.attributeValueBytes = bytes;
        }

        public DERTag getTag() {
            return this.attributeValueTag;
        }

        public byte[] getBytes() {
            return this.attributeValueBytes;
        }

        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (o instanceof Value) {
                Value v = (Value)o;
                return LdapUtils.areEqual(this.attributeValueTag, v.attributeValueTag) && LdapUtils.areEqual(this.attributeValueBytes, v.attributeValueBytes);
            }
            return false;
        }

        public int hashCode() {
            return LdapUtils.computeHashCode(617, this.attributeValueTag, this.attributeValueBytes);
        }

        public String toString() {
            return "[" + this.getClass().getName() + "@" + this.hashCode() + "::" + "attributeValueTag=" + this.attributeValueTag + ", " + "attributeValueBytes=" + LdapUtils.utf8Encode(this.attributeValueBytes) + "]";
        }
    }
}

