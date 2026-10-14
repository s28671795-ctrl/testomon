/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.filter;

import org.ldaptive.filter.Filter;
import org.ldaptive.filter.FilterParseException;

@FunctionalInterface
public interface FilterFunction {
    public Filter parse(String var1) throws FilterParseException;
}

