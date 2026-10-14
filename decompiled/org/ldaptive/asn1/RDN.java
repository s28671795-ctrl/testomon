/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.asn1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import org.ldaptive.LdapUtils;
import org.ldaptive.asn1.AttributeValueAssertion;
import org.ldaptive.asn1.ConstructedDEREncoder;
import org.ldaptive.asn1.DERBuffer;
import org.ldaptive.asn1.DEREncoder;
import org.ldaptive.asn1.DERParser;
import org.ldaptive.asn1.DERPath;
import org.ldaptive.asn1.UniversalDERTag;
import org.ldaptive.transcode.ValueTranscoder;

@Deprecated
public class RDN
implements DEREncoder {
    private static final int HASH_CODE_SEED = 619;
    private static final DERPath RDN_PATH = new DERPath("/SEQ/SET");
    private final AttributeValueAssertion[] attributeValueAssertions;

    public RDN(Collection<AttributeValueAssertion> assertions) {
        this.attributeValueAssertions = assertions.toArray(new AttributeValueAssertion[0]);
    }

    public RDN(AttributeValueAssertion ... assertions) {
        this.attributeValueAssertions = assertions;
    }

    public AttributeValueAssertion[] getAttributeValueAssertions() {
        return this.attributeValueAssertions;
    }

    public AttributeValueAssertion.Value[] getAttributeValues(String oid) {
        ArrayList<AttributeValueAssertion.Value> values2 = new ArrayList<AttributeValueAssertion.Value>();
        for (AttributeValueAssertion type : this.attributeValueAssertions) {
            if (!type.getOid().equals(oid)) continue;
            values2.add(type.getValue());
        }
        return values2.toArray(new AttributeValueAssertion.Value[0]);
    }

    public AttributeValueAssertion.Value getAttributeValue(String oid) {
        AttributeValueAssertion.Value[] values2 = this.getAttributeValues(oid);
        if (values2 == null || values2.length == 0) {
            return null;
        }
        return values2[0];
    }

    public <T> T[] getAttributeValues(String oid, ValueTranscoder<T> transcoder) {
        ArrayList<T> values2 = new ArrayList<T>();
        for (AttributeValueAssertion type : this.attributeValueAssertions) {
            if (!type.getOid().equals(oid)) continue;
            values2.add(transcoder.decodeBinaryValue(type.getValue().getBytes()));
        }
        return values2.toArray();
    }

    public <T> T getAttributeValue(String oid, ValueTranscoder<T> transcoder) {
        T[] values2 = this.getAttributeValues(oid, transcoder);
        if (values2 == null || values2.length == 0) {
            return null;
        }
        return values2[0];
    }

    @Override
    public byte[] encode() {
        ArrayList<DEREncoder> typeEncoders = new ArrayList<DEREncoder>();
        for (AttributeValueAssertion types : this.attributeValueAssertions) {
            typeEncoders.add(types::encode);
        }
        ConstructedDEREncoder se = new ConstructedDEREncoder(UniversalDERTag.SET, typeEncoders.toArray(new DEREncoder[0]));
        return se.encode();
    }

    public static RDN[] decode(DERBuffer encoded) {
        ArrayList rdns = new ArrayList();
        DERParser parser = new DERParser();
        parser.registerHandler(RDN_PATH, (p, e) -> {
            rdns.add(new RDN(AttributeValueAssertion.decode(e)));
            e.position(e.limit());
        });
        parser.parse(encoded);
        return rdns.toArray(new RDN[0]);
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof RDN) {
            RDN v = (RDN)o;
            return LdapUtils.areEqual(this.attributeValueAssertions, v.attributeValueAssertions);
        }
        return false;
    }

    public int hashCode() {
        return LdapUtils.computeHashCode(619, new Object[]{this.attributeValueAssertions});
    }

    public String toString() {
        return "[" + this.getClass().getName() + "@" + this.hashCode() + "::" + "attributeValueAssertions=" + Arrays.toString(this.attributeValueAssertions) + "]";
    }
}

