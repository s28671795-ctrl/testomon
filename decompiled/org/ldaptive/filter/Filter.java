/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.filter;

import org.ldaptive.asn1.DEREncoder;

public interface Filter {
    public DEREncoder getEncoder();

    public static enum Type {
        AND,
        OR,
        NOT,
        EQUALITY,
        SUBSTRING,
        GREATER_OR_EQUAL,
        LESS_OR_EQUAL,
        PRESENCE,
        APPROXIMATE_MATCH,
        EXTENSIBLE_MATCH;

    }
}

