/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.QueryPart;
import org.jooq.QueryPartInternal;

interface AutoAlias<Q extends QueryPart>
extends QueryPartInternal {
    public Q autoAlias(Context<?> var1, Q var2);
}

