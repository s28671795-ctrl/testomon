/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.filter;

import org.ldaptive.LdapUtils;
import org.ldaptive.filter.AbstractAttributeValueAssertionFilter;
import org.ldaptive.filter.Filter;

public class EqualityFilter
extends AbstractAttributeValueAssertionFilter {
    private static final int HASH_CODE_SEED = 10039;

    public EqualityFilter(String name, String value) {
        super(Filter.Type.EQUALITY, name, LdapUtils.utf8Encode(value, false));
    }

    public EqualityFilter(String name, byte[] value) {
        super(Filter.Type.EQUALITY, name, value);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        return o instanceof EqualityFilter && super.equals(o);
    }

    @Override
    public int hashCode() {
        return LdapUtils.computeHashCode(10039, new Object[]{this.filterType, this.attributeDesc, this.assertionValue});
    }
}

