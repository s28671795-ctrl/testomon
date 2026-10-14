/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.filter;

import java.util.Objects;
import java.util.stream.Stream;
import org.ldaptive.LdapUtils;
import org.ldaptive.asn1.BooleanType;
import org.ldaptive.asn1.ConstructedDEREncoder;
import org.ldaptive.asn1.ContextDERTag;
import org.ldaptive.asn1.DEREncoder;
import org.ldaptive.asn1.DERTag;
import org.ldaptive.asn1.OctetStringType;
import org.ldaptive.filter.Filter;

public class ExtensibleFilter
implements Filter {
    private static final int HASH_CODE_SEED = 10061;
    private final String matchingRuleID;
    private final String attributeDesc;
    private final byte[] assertionValue;
    private final boolean dnAttributes;

    public ExtensibleFilter(String matchingRule, String type, String value) {
        this(matchingRule, type, LdapUtils.utf8Encode(value, false), false);
    }

    public ExtensibleFilter(String matchingRule, String type, String value, boolean dnAttrs) {
        this(matchingRule, type, LdapUtils.utf8Encode(value, false), dnAttrs);
    }

    public ExtensibleFilter(String matchingRule, String type, byte[] value) {
        this(matchingRule, type, value, false);
    }

    public ExtensibleFilter(String matchingRule, String type, byte[] value, boolean dnAttrs) {
        if (matchingRule == null && type == null) {
            throw new IllegalArgumentException("Either the matching rule or the type must be specified");
        }
        if (value == null) {
            throw new IllegalArgumentException("A match value must be specified");
        }
        this.matchingRuleID = matchingRule;
        this.attributeDesc = type;
        this.assertionValue = value;
        this.dnAttributes = dnAttrs;
    }

    public String getMatchingRuleID() {
        return this.matchingRuleID;
    }

    public String getAttributeDesc() {
        return this.attributeDesc;
    }

    public byte[] getAssertionValue() {
        return this.assertionValue;
    }

    public boolean getDnAttributes() {
        return this.dnAttributes;
    }

    @Override
    public DEREncoder getEncoder() {
        DEREncoder[] encoders = new DEREncoder[]{this.matchingRuleID != null ? new OctetStringType((DERTag)new ContextDERTag(1, false), this.matchingRuleID) : null, this.attributeDesc != null ? new OctetStringType((DERTag)new ContextDERTag(2, false), this.attributeDesc) : null, this.assertionValue != null ? new OctetStringType((DERTag)new ContextDERTag(3, false), this.assertionValue) : null, this.dnAttributes ? new BooleanType(new ContextDERTag(4, false), true) : null};
        return new ConstructedDEREncoder(new ContextDERTag(Filter.Type.EXTENSIBLE_MATCH.ordinal(), true), (DEREncoder[])Stream.of(encoders).filter(Objects::nonNull).toArray(DEREncoder[]::new));
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof ExtensibleFilter) {
            ExtensibleFilter v = (ExtensibleFilter)o;
            return LdapUtils.areEqual(this.matchingRuleID, v.matchingRuleID) && LdapUtils.areEqual(this.attributeDesc, v.attributeDesc) && LdapUtils.areEqual(this.assertionValue, v.assertionValue) && LdapUtils.areEqual(this.dnAttributes, v.dnAttributes);
        }
        return false;
    }

    public int hashCode() {
        return LdapUtils.computeHashCode(10061, this.matchingRuleID, this.attributeDesc, this.assertionValue, this.dnAttributes);
    }

    public String toString() {
        return this.getClass().getName() + "@" + this.hashCode() + "::" + "matchingRuleID=" + this.matchingRuleID + ", " + "attributeDesc=" + this.attributeDesc + ", " + "assertionValue=" + LdapUtils.utf8Encode(this.assertionValue) + ", " + "dnAttributes=" + this.dnAttributes;
    }
}

