/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Field;
import org.jooq.impl.AbstractLeadLag;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;

final class Lead<T>
extends AbstractLeadLag<T>
implements QOM.Lead<T> {
    Lead(Field<T> field2, Field<Integer> offset, Field<T> defaultValue) {
        super(Names.N_LEAD, field2, offset, defaultValue);
    }

    @Override
    final AbstractLeadLag<T> constructor(Field<T> f, Field<Integer> o, Field<T> def) {
        return new Lead<T>(f, o, def);
    }
}

