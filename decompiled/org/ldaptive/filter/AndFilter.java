/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.filter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.ldaptive.LdapUtils;
import org.ldaptive.asn1.ConstructedDEREncoder;
import org.ldaptive.asn1.ContextDERTag;
import org.ldaptive.asn1.DEREncoder;
import org.ldaptive.asn1.NullType;
import org.ldaptive.filter.Filter;
import org.ldaptive.filter.FilterSet;

public class AndFilter
implements FilterSet {
    private static final int HASH_CODE_SEED = 10009;
    private final List<Filter> filterComponents = new ArrayList<Filter>();

    public AndFilter() {
    }

    public AndFilter(Filter ... components) {
        Stream.of(components).forEach(this.filterComponents::add);
    }

    @Override
    public Filter.Type getType() {
        return Filter.Type.AND;
    }

    @Override
    public void add(Filter component) {
        this.filterComponents.add(component);
    }

    @Override
    public DEREncoder getEncoder() {
        if (this.filterComponents.size() == 0) {
            return new NullType(new ContextDERTag(Filter.Type.AND.ordinal(), true));
        }
        return new ConstructedDEREncoder(new ContextDERTag(Filter.Type.AND.ordinal(), true), (DEREncoder[])this.filterComponents.stream().map(Filter::getEncoder).toArray(DEREncoder[]::new));
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof AndFilter) {
            AndFilter v = (AndFilter)o;
            return LdapUtils.areEqual(this.filterComponents, v.filterComponents);
        }
        return false;
    }

    public int hashCode() {
        return LdapUtils.computeHashCode(10009, this.filterComponents);
    }

    public String toString() {
        return this.getClass().getName() + "@" + this.hashCode() + "::" + "filterComponents=" + this.filterComponents;
    }
}

