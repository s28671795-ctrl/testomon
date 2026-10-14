/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.filter;

import org.ldaptive.filter.Filter;

public interface FilterSet
extends Filter {
    public Filter.Type getType();

    public void add(Filter var1);
}

