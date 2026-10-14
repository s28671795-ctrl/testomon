/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.filter;

import org.ldaptive.LdapUtils;
import org.ldaptive.asn1.ConstructedDEREncoder;
import org.ldaptive.asn1.ContextDERTag;
import org.ldaptive.asn1.DEREncoder;
import org.ldaptive.asn1.OctetStringType;
import org.ldaptive.filter.Filter;

public abstract class AbstractAttributeValueAssertionFilter
implements Filter {
    protected final Filter.Type filterType;
    protected final String attributeDesc;
    protected final byte[] assertionValue;

    public AbstractAttributeValueAssertionFilter(Filter.Type type, String name, byte[] value) {
        this.filterType = type;
        this.attributeDesc = name;
        this.assertionValue = value;
    }

    public String getAttributeDesc() {
        return this.attributeDesc;
    }

    public byte[] getAssertionValue() {
        return this.assertionValue;
    }

    @Override
    public DEREncoder getEncoder() {
        return new ConstructedDEREncoder(new ContextDERTag(this.filterType.ordinal(), true), new OctetStringType(this.attributeDesc), new OctetStringType(this.assertionValue));
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof AbstractAttributeValueAssertionFilter) {
            AbstractAttributeValueAssertionFilter v = (AbstractAttributeValueAssertionFilter)o;
            return LdapUtils.areEqual((Object)this.filterType, (Object)v.filterType) && LdapUtils.areEqual(this.attributeDesc, v.attributeDesc) && LdapUtils.areEqual(this.assertionValue, v.assertionValue);
        }
        return false;
    }

    public abstract int hashCode();

    public String toString() {
        return this.getClass().getName() + "@" + this.hashCode() + "::" + "filterType=" + (Object)((Object)this.filterType) + ", " + "attributeDesc=" + this.attributeDesc + ", " + "assertionValue=" + LdapUtils.utf8Encode(this.assertionValue);
    }
}

