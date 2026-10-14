/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Set;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Name;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.Replacer;
import org.jooq.Row;
import org.jooq.SQLDialect;
import org.jooq.SelectFieldOrAsterisk;
import org.jooq.Traverser;
import org.jooq.impl.AbstractRowAsField;
import org.jooq.impl.DSL;
import org.jooq.impl.Keywords;
import org.jooq.impl.QOM;
import org.jooq.impl.RecordDataType;
import org.jooq.impl.SelectFieldList;
import org.jooq.impl.Tools;

final class RowAsField<ROW extends Row, REC extends Record>
extends AbstractRowAsField<REC>
implements QOM.RowAsField<REC> {
    static final Set<SQLDialect> NO_NATIVE_SUPPORT = SQLDialect.supportedBy(SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.AURORA_MYSQL, SQLDialect.BIGQUERY, SQLDialect.CUBRID, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.IGNITE, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.REDSHIFT, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA);
    final ROW row;

    RowAsField(ROW row) {
        this(row, DSL.name("nested"));
    }

    RowAsField(ROW row, Name as) {
        super(as, new RecordDataType((Row)row));
        this.row = row;
    }

    final ROW fields0() {
        return this.row;
    }

    @Override
    final Class<REC> getRecordType() {
        return Tools.recordType(this.row.size());
    }

    @Override
    final void acceptDefault(Context<?> ctx) {
        if (NO_NATIVE_SUPPORT.contains((Object)ctx.dialect())) {
            ctx.data(Tools.BooleanDataKey.DATA_LIST_ALREADY_INDENTED, true, c -> c.visit(new SelectFieldList((SelectFieldOrAsterisk[])this.emulatedFields((Configuration)ctx.configuration()).fields.fields)));
        } else if (ctx.family() == SQLDialect.JAVA) {
            ctx.visit((QueryPart)this.row);
        } else if (ctx.family() == SQLDialect.INFORMIX) {
            ctx.visit((QueryPart)this.row);
        } else {
            ctx.visit(Keywords.K_ROW).sql(' ').visit((QueryPart)this.row);
        }
    }

    @Override
    public Field<REC> as(Name alias) {
        return new RowAsField<ROW, REC>(this.row, alias);
    }

    @Override
    public final Field<?> $aliased() {
        return new RowAsField<ROW, REC>(this.row);
    }

    @Override
    public final Name $alias() {
        return this.getQualifiedName();
    }

    @Override
    public final Row $row() {
        return this.row;
    }

    public final <T> T $traverse(Traverser<?, T> traverser) {
        return QOM.traverse(traverser, this, this.row);
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.row, r -> new RowAsField<Row, REC>((Row)r, this.getQualifiedName()), replacer);
    }
}

