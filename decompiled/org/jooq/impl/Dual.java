/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Name;
import org.jooq.Pro;
import org.jooq.Record;
import org.jooq.SelectField;
import org.jooq.Table;
import org.jooq.TableOptions;
import org.jooq.impl.AbstractTable;
import org.jooq.impl.DSL;
import org.jooq.impl.FieldsImpl;
import org.jooq.impl.Keywords;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.RecordImpl1;
import org.jooq.impl.TableAlias;

final class Dual
extends AbstractTable<Record>
implements QOM.Dual {
    private static final Table<Record> FORCED_DUAL = DSL.select(new Field[]{DSL.inline("X").as("DUMMY")}).asTable("DUAL");
    @Pro
    private static final Name DUAL_ACCESS = DSL.unquotedName("MSysResources");
    @Pro
    private static final Name DUAL_DB2 = DSL.unquotedName("SYSIBM", "DUAL");
    @Pro
    private static final Name DUAL_INFORMIX = DSL.unquotedName("systables");
    private static final Name DUAL_INFORMIX1 = DSL.unquotedName("tabid");
    @Pro
    private static final Name DUAL_SYBASE = DSL.unquotedName("SYS", "DUMMY");
    @Pro
    private static final Name DUAL_ORACLE = DSL.unquotedName("DUAL");
    private static final Name DUAL_FIREBIRD = DSL.unquotedName("RDB$DATABASE");
    private static final Name DUAL_CUBRID = DSL.unquotedName("db_root");
    private static final Name DUAL_DERBY = DSL.unquotedName("SYSIBM", "SYSDUMMY1");
    private final boolean force;

    Dual() {
        this(false);
    }

    Dual(boolean force) {
        super(TableOptions.expression(), Names.N_DUAL, null);
        this.force = force;
    }

    @Override
    public final Class<? extends Record> getRecordType() {
        return RecordImpl1.class;
    }

    @Override
    public final Table<Record> as(Name alias) {
        if (this.force) {
            return FORCED_DUAL.as(alias);
        }
        return new TableAlias<Record>(this, alias);
    }

    @Override
    public final Table<Record> as(Name alias, Name ... fieldAliases) {
        if (this.force) {
            return FORCED_DUAL.as(alias, fieldAliases);
        }
        return new TableAlias<Record>(this, alias, fieldAliases);
    }

    @Override
    public boolean declaresTables() {
        return true;
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (this.force) {
            ctx.visit(FORCED_DUAL);
        } else {
            switch (ctx.family()) {
                case AURORA_POSTGRES: 
                case COCKROACHDB: 
                case ASE: 
                case REDSHIFT: 
                case SQLDATAWAREHOUSE: 
                case SQLSERVER: 
                case VERTICA: 
                case H2: 
                case POSTGRES: 
                case SQLITE: 
                case YUGABYTEDB: {
                    break;
                }
                case FIREBIRD: {
                    ctx.visit(DUAL_FIREBIRD);
                    break;
                }
                case HSQLDB: {
                    ctx.sql('(').visit(Keywords.K_VALUES).sql("(1)) ").visit(Keywords.K_AS).sql(' ').visit(Names.N_DUAL).sql('(').visit(Names.N_DUAL).sql(')');
                    break;
                }
                case CUBRID: {
                    ctx.visit(DUAL_CUBRID);
                    break;
                }
                case BIGQUERY: {
                    ctx.visit(Keywords.K_UNNEST).sql("([").visit(Keywords.K_STRUCT).sql("(1 ").visit(Keywords.K_AS).sql(' ').visit(Names.N_DUAL).sql(")]) ").visit(Keywords.K_AS).sql(' ').visit(Names.N_DUAL);
                    break;
                }
                case ACCESS: {
                    ctx.sqlIndentStart('(').visit(Keywords.K_SELECT).sql(' ').visit(Names.N_COUNT).sql("(*) ").visit(Names.N_DUAL).formatSeparator().visit(Keywords.K_FROM).sql(' ').visit(DUAL_ACCESS).sqlIndentEnd(") ").visit(Keywords.K_AS).sql(' ').visit(Names.N_DUAL);
                    break;
                }
                case DB2: {
                    ctx.visit(DUAL_DB2);
                    break;
                }
                case INFORMIX: {
                    ctx.sqlIndentStart('(').visit(Keywords.K_SELECT).sql(" 1 ").visit(Keywords.K_AS).sql(' ').visit(Names.N_DUAL).formatSeparator().visit(Keywords.K_FROM).sql(' ').visit(DUAL_INFORMIX).formatSeparator().visit(Keywords.K_WHERE).sql(' ').visit(DSL.condition("{0} = 1", DUAL_INFORMIX1)).sqlIndentEnd(") ").visit(Keywords.K_AS).sql(' ').visit(Names.N_DUAL);
                    break;
                }
                case HANA: 
                case SYBASE: {
                    ctx.visit(DUAL_SYBASE);
                    break;
                }
                case INGRES: 
                case TERADATA: {
                    ctx.sqlIndentStart('(').visit(Keywords.K_SELECT).sql(" 1 ").visit(Keywords.K_AS).sql(' ').visit(Names.N_DUAL.quotedName()).sqlIndentEnd(") ").visit(Keywords.K_AS).sql(' ').visit(Names.N_DUAL.quotedName());
                    break;
                }
                case ORACLE: {
                    ctx.visit(DUAL_ORACLE);
                    break;
                }
                case DERBY: {
                    ctx.visit(DUAL_DERBY);
                    break;
                }
                default: {
                    ctx.visit(Keywords.K_DUAL);
                }
            }
        }
    }

    @Override
    final FieldsImpl<Record> fields0() {
        return new FieldsImpl<Record>(new SelectField[0]);
    }
}

