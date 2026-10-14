/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Field;
import org.jooq.impl.AbstractLeadLag;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;

final class Lag<T>
extends AbstractLeadLag<T>
implements QOM.Lag<T> {
    Lag(Field<T> field2, Field<Integer> offset, Field<T> defaultValue) {
        super(Names.N_LAG, field2, offset, defaultValue);
    }

    @Override
    final AbstractLeadLag<T> constructor(Field<T> f, Field<Integer> o, Field<T> def) {
        return new Lag<T>(f, o, def);
    }
}

