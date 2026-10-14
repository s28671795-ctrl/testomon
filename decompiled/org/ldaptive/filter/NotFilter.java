/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.filter;

import org.ldaptive.LdapUtils;
import org.ldaptive.asn1.ConstructedDEREncoder;
import org.ldaptive.asn1.ContextDERTag;
import org.ldaptive.asn1.DEREncoder;
import org.ldaptive.filter.Filter;
import org.ldaptive.filter.FilterSet;

public class NotFilter
implements FilterSet {
    private static final int HASH_CODE_SEED = 10079;
    private Filter filterComponent;

    public NotFilter() {
    }

    public NotFilter(Filter component) {
        this.filterComponent = component;
    }

    @Override
    public Filter.Type getType() {
        return Filter.Type.NOT;
    }

    @Override
    public void add(Filter component) {
        if (this.filterComponent != null) {
            throw new IllegalStateException("Filter component has already been set");
        }
        this.filterComponent = component;
    }

    @Override
    public DEREncoder getEncoder() {
        return new ConstructedDEREncoder(new ContextDERTag(Filter.Type.NOT.ordinal(), true), this.filterComponent.getEncoder());
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof NotFilter) {
            NotFilter v = (NotFilter)o;
            return LdapUtils.areEqual(this.filterComponent, v.filterComponent);
        }
        return false;
    }

    public int hashCode() {
        return LdapUtils.computeHashCode(10079, this.filterComponent);
    }

    public String toString() {
        return this.getClass().getName() + "@" + this.hashCode() + "::" + "filterComponent=" + this.filterComponent;
    }
}

