/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.QueryPart;
import org.jooq.impl.ScopeMappable;

interface ScopeMappableWrapper<WRAPPER extends ScopeMappableWrapper<WRAPPER, WRAPPED>, WRAPPED extends QueryPart>
extends ScopeMappable {
    public WRAPPER wrap(WRAPPED var1);
}

