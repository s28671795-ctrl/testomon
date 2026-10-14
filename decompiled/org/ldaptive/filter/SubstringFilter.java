/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.filter;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.ldaptive.LdapUtils;
import org.ldaptive.asn1.ConstructedDEREncoder;
import org.ldaptive.asn1.ContextDERTag;
import org.ldaptive.asn1.DEREncoder;
import org.ldaptive.asn1.DERTag;
import org.ldaptive.asn1.OctetStringType;
import org.ldaptive.asn1.UniversalDERTag;
import org.ldaptive.filter.Filter;

public class SubstringFilter
implements Filter {
    private static final int HASH_CODE_SEED = 10099;
    private final String attributeDesc;
    private final byte[] subInitial;
    private final byte[][] subAny;
    private final byte[] subFinal;

    public SubstringFilter(String name, String startsWith, String endsWith, String ... contains) {
        if (startsWith == null && endsWith == null && contains == null) {
            throw new IllegalArgumentException("Assertion must have one of subInitial, subAny, or subFinal");
        }
        this.attributeDesc = name;
        byte[][] containsBytes = null;
        if (contains != null) {
            containsBytes = new byte[contains.length][];
            for (int i = 0; i < contains.length; ++i) {
                containsBytes[i] = LdapUtils.utf8Encode(contains[i], false);
            }
        }
        this.subInitial = LdapUtils.utf8Encode(startsWith);
        this.subAny = containsBytes;
        this.subFinal = LdapUtils.utf8Encode(endsWith);
    }

    public SubstringFilter(String name, byte[] startsWith, byte[] endsWith, byte[] ... contains) {
        if (startsWith == null && endsWith == null && contains == null) {
            throw new IllegalArgumentException("Assertion must have one of subInitial, subAny, or subFinal");
        }
        this.attributeDesc = name;
        this.subInitial = startsWith;
        this.subAny = contains;
        this.subFinal = endsWith;
    }

    public String getAttributeDesc() {
        return this.attributeDesc;
    }

    public byte[] getSubInitial() {
        return this.subInitial;
    }

    public byte[][] getSubAny() {
        return this.subAny;
    }

    public byte[] getSubFinal() {
        return this.subFinal;
    }

    private int getAssertionCount() {
        int count;
        int n = count = this.subAny != null ? this.subAny.length : 0;
        if (this.subInitial != null) {
            ++count;
        }
        if (this.subFinal != null) {
            ++count;
        }
        return count;
    }

    @Override
    public DEREncoder getEncoder() {
        DEREncoder[] encoders = new DEREncoder[this.getAssertionCount()];
        int i = 0;
        if (this.subInitial != null) {
            encoders[i++] = new OctetStringType((DERTag)new ContextDERTag(Substrings.INITIAL.ordinal(), false), this.subInitial);
        }
        if (this.subAny != null && this.subAny.length > 0) {
            for (byte[] assertion : this.subAny) {
                encoders[i++] = new OctetStringType((DERTag)new ContextDERTag(Substrings.ANY.ordinal(), false), assertion);
            }
        }
        if (this.subFinal != null) {
            encoders[i] = new OctetStringType((DERTag)new ContextDERTag(Substrings.FINAL.ordinal(), false), this.subFinal);
        }
        return new ConstructedDEREncoder(new ContextDERTag(Filter.Type.SUBSTRING.ordinal(), true), new OctetStringType(this.attributeDesc), new ConstructedDEREncoder(UniversalDERTag.SEQ, encoders));
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof SubstringFilter) {
            SubstringFilter v = (SubstringFilter)o;
            return LdapUtils.areEqual(this.attributeDesc, v.attributeDesc) && LdapUtils.areEqual(this.subInitial, v.subInitial) && LdapUtils.areEqual(this.subAny, v.subAny) && LdapUtils.areEqual(this.subFinal, v.subFinal);
        }
        return false;
    }

    public int hashCode() {
        return LdapUtils.computeHashCode(10099, new Object[]{this.attributeDesc, this.subInitial, this.subAny, this.subFinal});
    }

    public String toString() {
        return this.getClass().getName() + "@" + this.hashCode() + "::" + "attributeDesc=" + this.attributeDesc + ", " + "subInitial=" + LdapUtils.utf8Encode(this.subInitial) + ", " + "subAny=" + (this.subAny == null ? null : Stream.of(this.subAny).map(LdapUtils::utf8Encode).collect(Collectors.toList())) + ", " + "subFinal=" + LdapUtils.utf8Encode(this.subFinal);
    }

    public static enum Substrings {
        INITIAL,
        ANY,
        FINAL;

    }
}

