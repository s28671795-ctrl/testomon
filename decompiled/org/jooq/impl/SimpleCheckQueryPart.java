/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.QueryPartInternal;

interface SimpleCheckQueryPart
extends QueryPartInternal {
    default public boolean isSimple(Context<?> ctx) {
        return true;
    }
}

