/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Collection;
import org.jooq.Field;
import org.jooq.impl.AbstractRecord;
import org.jooq.impl.AbstractRow;
import org.jooq.impl.FieldsImpl;
import org.jooq.impl.InternalRecord;
import org.jooq.impl.RowImplN;
import org.jooq.impl.Tools;

final class RecordImplN
extends AbstractRecord
implements InternalRecord {
    RecordImplN(AbstractRow<?> row) {
        super(row);
    }

    @Deprecated(forRemoval=true, since="3.14")
    RecordImplN(Collection<? extends Field<?>> fields) {
        super(fields);
    }

    RecordImplN(RowImplN fields) {
        super(fields);
    }

    @Override
    public RowImplN fieldsRow() {
        return new RowImplN((FieldsImpl<?>)this.fields.fields);
    }

    @Override
    public final RowImplN valuesRow() {
        return new RowImplN(Tools.fieldsArray(this.values, this.fields.fields.fields));
    }
}

