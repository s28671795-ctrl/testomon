/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import org.jooq.ArrayRecord;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Name;
import org.jooq.Named;
import org.jooq.Param;
import org.jooq.Pro;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.Table;
import org.jooq.TableOptions;
import org.jooq.exception.DataTypeException;
import org.jooq.impl.AbstractAutoAliasTable;
import org.jooq.impl.AbstractTable;
import org.jooq.impl.Array;
import org.jooq.impl.ArrayConstant;
import org.jooq.impl.ArrayTableEmulation;
import org.jooq.impl.AutoAlias;
import org.jooq.impl.DSL;
import org.jooq.impl.FieldsImpl;
import org.jooq.impl.Keywords;
import org.jooq.impl.Names;
import org.jooq.impl.QOM;
import org.jooq.impl.RecordImplN;
import org.jooq.impl.Tools;

final class ArrayTable
extends AbstractAutoAliasTable<Record>
implements QOM.UNotYetImplemented {
    private final Field<?> array;
    private final FieldsImpl<Record> field;

    ArrayTable(Field<?> array) {
        this(array, Names.N_ARRAY_TABLE);
    }

    ArrayTable(Field<?> array, Name alias) {
        this(array, alias, (Name[])null);
    }

    ArrayTable(Field<?> array, Name alias, Name[] fieldAliases) {
        this(array, alias, ArrayTable.init(ArrayTable.arrayType(array), alias, ArrayTable.fieldAliases(fieldAliases)[0]));
    }

    private ArrayTable(Field<?> array, Name alias, FieldsImpl<Record> fields) {
        super(alias, Tools.map(fields.fields, Named::getUnqualifiedName, Name[]::new));
        this.array = array;
        this.field = fields;
    }

    private static final Class<?> arrayType(Field<?> array) {
        Class<Object> arrayType;
        if (array.getDataType().getType().isArray()) {
            arrayType = array.getDataType().getArrayComponentType();
        } else if (array instanceof ArrayConstant) {
            ArrayConstant a = (ArrayConstant)array;
            arrayType = a.getBaseType().getType();
        } else {
            arrayType = ArrayRecord.class.isAssignableFrom(array.getDataType().getType()) ? ((ArrayRecord)Tools.newArrayRecord(array.getDataType().getType())).getDataType().getType() : Object.class;
        }
        return arrayType;
    }

    static final Name[] fieldAliases(Name[] fieldAliases) {
        Name[] nameArray;
        if (Tools.isEmpty(fieldAliases)) {
            Name[] nameArray2 = new Name[1];
            nameArray = nameArray2;
            nameArray2[0] = Names.N_COLUMN_VALUE;
        } else {
            nameArray = fieldAliases;
        }
        return nameArray;
    }

    static final FieldsImpl<Record> init(Class<?> arrayType, Name alias, Name fieldAlias) {
        if (Record.class.isAssignableFrom(arrayType)) {
            try {
                return new FieldsImpl<Record>(Tools.map(((Record)arrayType.getDeclaredConstructor(new Class[0]).newInstance(new Object[0])).fields(), f -> DSL.field(alias.append(f.getUnqualifiedName()), f.getDataType())));
            }
            catch (Exception e) {
                throw new DataTypeException("Bad UDT Type : " + String.valueOf(arrayType), e);
            }
        }
        return new FieldsImpl<Record>(DSL.field(alias.unqualifiedName().append(fieldAlias.unqualifiedName()), DSL.getDataType(arrayType)));
    }

    final ArrayTable construct(Name newAlias, Name[] newFieldAliases) {
        return new ArrayTable(this.array, newAlias, newFieldAliases);
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
        ctx.visit(this.table(ctx.configuration()));
    }

    private final QueryPart table(Configuration configuration) {
        boolean isArray = this.array.getDataType().getType().isArray();
        switch (configuration.family()) {
            case ORACLE: {
                if (isArray && this.array instanceof Param) {
                    return this.emulateParam();
                }
                if (isArray && this.array instanceof Array) {
                    return this.emulateArray();
                }
                return new OracleArrayTable();
            }
            case ACCESS: 
            case ASE: 
            case AURORA_MYSQL: 
            case DB2: 
            case EXASOL: 
            case HANA: 
            case INFORMIX: 
            case INGRES: 
            case MEMSQL: 
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
                if (isArray && this.array instanceof Param) {
                    return this.emulateParam();
                }
                if (isArray && this.array instanceof Array) {
                    return this.emulateArray();
                }
                if (this.array instanceof ArrayConstant) {
                    return new OracleArrayTable();
                }
                return DSL.table("{0}", new QueryPart[]{this.array});
            }
        }
        return new StandardUnnest();
    }

    private final QueryPart emulateParam() {
        return new ArrayTableEmulation((Object[])((Param)this.array).getValue(), this.fieldAliases);
    }

    private final QueryPart emulateArray() {
        return new ArrayTableEmulation(((Array)this.array).fields.fields, this.fieldAliases);
    }

    @Pro
    private class OracleArrayTable
    extends DialectArrayTable {
        private OracleArrayTable() {
        }

        @Override
        public final void accept(Context<?> ctx) {
            ctx.visit(Keywords.K_TABLE).sql(" (").visit(ArrayTable.this.array).sql(')');
        }
    }

    private class StandardUnnest
    extends DialectArrayTable {
        private StandardUnnest() {
        }

        @Override
        public final void accept(Context<?> ctx) {
            ctx.visit(Keywords.K_UNNEST).sql('(').visit(ArrayTable.this.array).sql(")");
        }
    }

    private abstract class DialectArrayTable
    extends AbstractTable<Record>
    implements AutoAlias<Table<Record>>,
    QOM.UTransient {
        DialectArrayTable() {
            super(TableOptions.expression(), ArrayTable.this.alias);
        }

        @Override
        public final boolean declaresTables() {
            return true;
        }

        @Override
        public final Class<? extends Record> getRecordType() {
            return RecordImplN.class;
        }

        @Override
        final FieldsImpl<Record> fields0() {
            return ArrayTable.this.fields0();
        }

        @Override
        public final Table<Record> autoAlias(Context<?> ctx, Table<Record> t2) {
            return t2.as(ArrayTable.this.alias, ArrayTable.this.fieldAliases);
        }
    }
}

