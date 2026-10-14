/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Name;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.TableOptions;
import org.jooq.impl.AbstractAutoAliasTable;
import org.jooq.impl.AbstractTable;
import org.jooq.impl.ArrayTable;
import org.jooq.impl.ArrayTableEmulation;
import org.jooq.impl.FieldsImpl;
import org.jooq.impl.Keywords;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.QueryPartListView;
import org.jooq.impl.RecordImplN;
import org.jooq.impl.Tools;

final class ArrayOfValues
extends AbstractAutoAliasTable<Record>
implements QOM.UNotYetImplemented {
    private final Field<?>[] array;
    private final FieldsImpl<Record> field;

    ArrayOfValues(Field<?>[] array) {
        this(array, Names.N_ARRAY_TABLE);
    }

    ArrayOfValues(Field<?>[] array, Name alias) {
        this(array, alias, null);
    }

    ArrayOfValues(Field<?>[] array, Name alias, Name[] fieldAliases) {
        super(alias, ArrayTable.fieldAliases(fieldAliases));
        Class<Object> arrayType = !Tools.isEmpty(array) ? array[0].getType() : Object.class;
        this.array = array;
        this.field = ArrayTable.init(arrayType, this.alias, this.fieldAliases[0]);
    }

    final ArrayOfValues construct(Name newAlias, Name[] newFieldAliases) {
        return new ArrayOfValues(this.array, newAlias, newFieldAliases);
    }

    @Override
    public final Class<? extends Record> getRecordType() {
        return RecordImplN.class;
    }

    @Override
    final FieldsImpl<Record> fields0() {
        return this.field;
    }

    @Override
    public final void accept(Context<?> ctx) {
        switch (ctx.family()) {
            case ACCESS: 
            case ASE: 
            case AURORA_MYSQL: 
            case DB2: 
            case EXASOL: 
            case HANA: 
            case INFORMIX: 
            case INGRES: 
            case MEMSQL: 
            case ORACLE: 
            case REDSHIFT: 
            case SNOWFLAKE: 
            case SQLDATAWAREHOUSE: 
            case SQLSERVER: 
            case SYBASE: 
            case TERADATA: 
            case VERTICA: 
            case CUBRID: 
            case DERBY: 
            case FIREBIRD: 
            case IGNITE: 
            case MARIADB: 
            case MYSQL: 
            case SQLITE: {
                ctx.visit(new ArrayTableEmulation(this.array, this.fieldAliases));
                break;
            }
            default: {
                ctx.visit(new StandardUnnest());
            }
        }
    }

    private class StandardUnnest
    extends DialectArrayTable {
        private StandardUnnest() {
        }

        @Override
        public final void accept(Context<?> ctx) {
            ctx.visit(Keywords.K_UNNEST).sql('(').visit(Keywords.K_ARRAY).sql('[').visit(QueryPartListView.wrap((QueryPart[])ArrayOfValues.this.array)).sql(']').sql(")");
        }
    }

    private abstract class DialectArrayTable
    extends AbstractTable<Record>
    implements QOM.UTransient {
        DialectArrayTable() {
            super(TableOptions.expression(), ArrayOfValues.this.alias);
        }

        @Override
        public final Class<? extends Record> getRecordType() {
            return RecordImplN.class;
        }

        @Override
        final FieldsImpl<Record> fields0() {
            return ArrayOfValues.this.fields0();
        }
    }
}

