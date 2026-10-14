/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Set;
import org.jooq.Context;
import org.jooq.FieldOrRow;
import org.jooq.Function1;
import org.jooq.QueryPart;
import org.jooq.SQLDialect;
import org.jooq.impl.AbstractField;
import org.jooq.impl.Keywords;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.QueryPartList;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.Tools;

final class Rollup
extends AbstractField<Object>
implements QOM.Rollup {
    static final Set<SQLDialect> SUPPORT_WITH_ROLLUP = SQLDialect.supportedBy(SQLDialect.AURORA_MYSQL, SQLDialect.CUBRID, SQLDialect.MARIADB, SQLDialect.MYSQL);
    private QueryPartList<FieldOrRow> arguments;

    Rollup(FieldOrRow ... arguments) {
        super(Names.N_ROLLUP, SQLDataType.OTHER);
        this.arguments = new QueryPartList((QueryPart[])arguments);
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (SUPPORT_WITH_ROLLUP.contains((Object)ctx.dialect())) {
            ctx.visit(this.arguments).formatSeparator().visit(Keywords.K_WITH_ROLLUP);
        } else {
            ctx.visit(Keywords.K_ROLLUP).sql(" (").visit(this.arguments).sql(')');
        }
    }

    @Override
    public final QOM.UnmodifiableList<? extends FieldOrRow> $arg1() {
        return QOM.unmodifiable(this.arguments);
    }

    @Override
    public final Function1<? super QOM.UnmodifiableList<? extends FieldOrRow>, ? extends QOM.Rollup> $constructor() {
        return l -> new Rollup(l.toArray(Tools.EMPTY_FIELD_OR_ROW));
    }
}

