/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Function1;
import org.jooq.Pro;
import org.jooq.RowId;
import org.jooq.Table;
import org.jooq.impl.AbstractField;
import org.jooq.impl.DSL;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;
import org.jooq.tools.StringUtils;

final class QualifiedRowid
extends AbstractField<RowId>
implements QOM.QualifiedRowid {
    final Table<?> table;

    QualifiedRowid(Table<?> table) {
        super(Names.N_ROWID, Tools.allNotNull(SQLDataType.ROWID));
        this.table = table;
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case JAVA: {
                this.acceptJava(ctx);
                break;
            }
            case H2: {
                ctx.visit(this.table.getQualifiedName().append(DSL.systemName("_rowid_")));
                break;
            }
            case POSTGRES: {
                ctx.visit(this.table.getQualifiedName().append(DSL.systemName("ctid")));
                break;
            }
            case SQLSERVER: {
                ctx.visit(this.table.getQualifiedName().append(DSL.systemName("%%physloc%%")));
                break;
            }
            default: {
                ctx.visit(this.table.getQualifiedName().append(DSL.systemName("rowid")));
            }
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        ctx.visit(this.table).sql(".rowid()");
    }

    @Override
    public final Table<?> $arg1() {
        return this.table;
    }

    @Override
    public final QOM.QualifiedRowid $arg1(Table<?> newValue) {
        return this.$constructor().apply(newValue);
    }

    @Override
    public final Function1<? super Table<?>, ? extends QOM.QualifiedRowid> $constructor() {
        return a1 -> new QualifiedRowid((Table<?>)a1);
    }

    @Override
    public boolean equals(Object that) {
        if (that instanceof QOM.QualifiedRowid) {
            QOM.QualifiedRowid o = (QOM.QualifiedRowid)that;
            return StringUtils.equals(this.$table(), o.$table());
        }
        return super.equals(that);
    }
}

