/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.filter;

import org.ldaptive.LdapUtils;
import org.ldaptive.filter.AbstractAttributeValueAssertionFilter;
import org.ldaptive.filter.Filter;

public class GreaterOrEqualFilter
extends AbstractAttributeValueAssertionFilter {
    private static final int HASH_CODE_SEED = 10067;

    public GreaterOrEqualFilter(String name, String value) {
        super(Filter.Type.GREATER_OR_EQUAL, name, LdapUtils.utf8Encode(value, false));
    }

    public GreaterOrEqualFilter(String name, byte[] value) {
        super(Filter.Type.GREATER_OR_EQUAL, name, value);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        return o instanceof GreaterOrEqualFilter && super.equals(o);
    }

    @Override
    public int hashCode() {
        return LdapUtils.computeHashCode(10067, new Object[]{this.filterType, this.attributeDesc, this.assertionValue});
    }
}

