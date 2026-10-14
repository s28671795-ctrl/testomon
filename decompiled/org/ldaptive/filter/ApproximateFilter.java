/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.filter;

import org.ldaptive.LdapUtils;
import org.ldaptive.filter.AbstractAttributeValueAssertionFilter;
import org.ldaptive.filter.Filter;

public class ApproximateFilter
extends AbstractAttributeValueAssertionFilter {
    private static final int HASH_CODE_SEED = 10037;

    public ApproximateFilter(String name, String value) {
        super(Filter.Type.APPROXIMATE_MATCH, name, LdapUtils.utf8Encode(value, false));
    }

    public ApproximateFilter(String name, byte[] value) {
        super(Filter.Type.APPROXIMATE_MATCH, name, value);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        return o instanceof ApproximateFilter && super.equals(o);
    }

    @Override
    public int hashCode() {
        return LdapUtils.computeHashCode(10037, new Object[]{this.filterType, this.attributeDesc, this.assertionValue});
    }
}

