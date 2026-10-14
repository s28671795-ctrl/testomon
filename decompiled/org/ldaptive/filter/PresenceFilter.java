/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.filter;

import org.ldaptive.LdapUtils;
import org.ldaptive.asn1.ContextDERTag;
import org.ldaptive.asn1.DEREncoder;
import org.ldaptive.asn1.DERTag;
import org.ldaptive.asn1.OctetStringType;
import org.ldaptive.filter.Filter;

public class PresenceFilter
implements Filter {
    private static final int HASH_CODE_SEED = 10093;
    private final String attributeDesc;

    public PresenceFilter(String name) {
        this.attributeDesc = name;
    }

    public String getAttributeDesc() {
        return this.attributeDesc;
    }

    @Override
    public DEREncoder getEncoder() {
        return new OctetStringType((DERTag)new ContextDERTag(Filter.Type.PRESENCE.ordinal(), false), this.attributeDesc);
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof PresenceFilter) {
            PresenceFilter v = (PresenceFilter)o;
            return LdapUtils.areEqual(this.attributeDesc, v.attributeDesc);
        }
        return false;
    }

    public int hashCode() {
        return LdapUtils.computeHashCode(10093, this.attributeDesc);
    }
}

