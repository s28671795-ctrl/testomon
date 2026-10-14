/*
 * Decompiled with CFR 0.152.
 */
package org.h2.mode;

import java.util.ArrayList;
import java.util.HashSet;
import org.h2.constraint.Constraint;
import org.h2.engine.RightOwner;
import org.h2.engine.SessionLocal;
import org.h2.engine.User;
import org.h2.index.Index;
import org.h2.message.DbException;
import org.h2.result.Row;
import org.h2.result.SearchRow;
import org.h2.schema.Schema;
import org.h2.schema.TriggerObject;
import org.h2.server.pg.PgServer;
import org.h2.table.Column;
import org.h2.table.MetaTable;
import org.h2.table.Table;
import org.h2.util.StringUtils;
import org.h2.util.Utils;
import org.h2.value.DataType;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueArray;
import org.h2.value.ValueBoolean;
import org.h2.value.ValueDouble;
import org.h2.value.ValueInteger;
import org.h2.value.ValueSmallint;

public final class PgCatalogTable
extends MetaTable {
    private static final int PG_AM = 0;
    private static final int PG_ATTRDEF = 1;
    private static final int PG_ATTRIBUTE = 2;
    private static final int PG_AUTHID = 3;
    private static final int PG_CLASS = 4;
    private static final int PG_CONSTRAINT = 5;
    private static final int PG_DATABASE = 6;
    private static final int PG_DESCRIPTION = 7;
    private static final int PG_GROUP = 8;
    private static final int PG_INDEX = 9;
    private static final int PG_INHERITS = 10;
    private static final int PG_NAMESPACE = 11;
    private static final int PG_PROC = 12;
    private static final int PG_ROLES = 13;
    private static final int PG_SETTINGS = 14;
    private static final int PG_TABLESPACE = 15;
    private static final int PG_TRIGGER = 16;
    private static final int PG_TYPE = 17;
    private static final int PG_USER = 18;
    public static final int META_TABLE_TYPE_COUNT = 19;
    private static final Object[][] PG_EXTRA_TYPES = new Object[][]{{18, "char", 1, 0}, {19, "name", 64, 18}, {22, "int2vector", -1, 21}, {24, "regproc", 4, 0}, {1005, "_int2", -1, 21}, {1007, "_int4", -1, 23}, {1015, "_varchar", -1, 1043}, {2205, "regclass", 4, 0}};

    public PgCatalogTable(Schema schema, int n, int n2) {
        super(schema, n, n2);
        Column[] columnArray;
        switch (n2) {
            case 0: {
                this.setMetaTableName("PG_AM");
                columnArray = new Column[]{this.column("OID", TypeInfo.TYPE_INTEGER), this.column("AMNAME", TypeInfo.TYPE_VARCHAR)};
                break;
            }
            case 1: {
                this.setMetaTableName("PG_ATTRDEF");
                columnArray = new Column[]{this.column("OID", TypeInfo.TYPE_INTEGER), this.column("ADSRC", TypeInfo.TYPE_INTEGER), this.column("ADRELID", TypeInfo.TYPE_INTEGER), this.column("ADNUM", TypeInfo.TYPE_INTEGER), this.column("ADBIN", TypeInfo.TYPE_VARCHAR)};
                break;
            }
            case 2: {
                this.setMetaTableName("PG_ATTRIBUTE");
                columnArray = new Column[]{this.column("OID", TypeInfo.TYPE_INTEGER), this.column("ATTRELID", TypeInfo.TYPE_INTEGER), this.column("ATTNAME", TypeInfo.TYPE_VARCHAR), this.column("ATTTYPID", TypeInfo.TYPE_INTEGER), this.column("ATTLEN", TypeInfo.TYPE_INTEGER), this.column("ATTNUM", TypeInfo.TYPE_INTEGER), this.column("ATTTYPMOD", TypeInfo.TYPE_INTEGER), this.column("ATTNOTNULL", TypeInfo.TYPE_BOOLEAN), this.column("ATTISDROPPED", TypeInfo.TYPE_BOOLEAN), this.column("ATTHASDEF", TypeInfo.TYPE_BOOLEAN)};
                break;
            }
            case 3: {
                this.setMetaTableName("PG_AUTHID");
                columnArray = new Column[]{this.column("OID", TypeInfo.TYPE_INTEGER), this.column("ROLNAME", TypeInfo.TYPE_VARCHAR), this.column("ROLSUPER", TypeInfo.TYPE_BOOLEAN), this.column("ROLINHERIT", TypeInfo.TYPE_BOOLEAN), this.column("ROLCREATEROLE", TypeInfo.TYPE_BOOLEAN), this.column("ROLCREATEDB", TypeInfo.TYPE_BOOLEAN), this.column("ROLCATUPDATE", TypeInfo.TYPE_BOOLEAN), this.column("ROLCANLOGIN", TypeInfo.TYPE_BOOLEAN), this.column("ROLCONNLIMIT", TypeInfo.TYPE_BOOLEAN), this.column("ROLPASSWORD", TypeInfo.TYPE_BOOLEAN), this.column("ROLVALIDUNTIL", TypeInfo.TYPE_TIMESTAMP_TZ), this.column("ROLCONFIG", TypeInfo.getTypeInfo(40, -1L, 0, TypeInfo.TYPE_VARCHAR))};
                break;
            }
            case 4: {
                this.setMetaTableName("PG_CLASS");
                columnArray = new Column[]{this.column("OID", TypeInfo.TYPE_INTEGER), this.column("RELNAME", TypeInfo.TYPE_VARCHAR), this.column("RELNAMESPACE", TypeInfo.TYPE_INTEGER), this.column("RELKIND", TypeInfo.TYPE_CHAR), this.column("RELAM", TypeInfo.TYPE_INTEGER), this.column("RELTUPLES", TypeInfo.TYPE_DOUBLE), this.column("RELTABLESPACE", TypeInfo.TYPE_INTEGER), this.column("RELPAGES", TypeInfo.TYPE_INTEGER), this.column("RELHASINDEX", TypeInfo.TYPE_BOOLEAN), this.column("RELHASRULES", TypeInfo.TYPE_BOOLEAN), this.column("RELHASOIDS", TypeInfo.TYPE_BOOLEAN), this.column("RELCHECKS", TypeInfo.TYPE_SMALLINT), this.column("RELTRIGGERS", TypeInfo.TYPE_INTEGER)};
                break;
            }
            case 5: {
                this.setMetaTableName("PG_CONSTRAINT");
                columnArray = new Column[]{this.column("OID", TypeInfo.TYPE_INTEGER), this.column("CONNAME", TypeInfo.TYPE_VARCHAR), this.column("CONTYPE", TypeInfo.TYPE_VARCHAR), this.column("CONRELID", TypeInfo.TYPE_INTEGER), this.column("CONFRELID", TypeInfo.TYPE_INTEGER), this.column("CONKEY", TypeInfo.getTypeInfo(40, -1L, 0, TypeInfo.TYPE_SMALLINT))};
                break;
            }
            case 6: {
                this.setMetaTableName("PG_DATABASE");
                columnArray = new Column[]{this.column("OID", TypeInfo.TYPE_INTEGER), this.column("DATNAME", TypeInfo.TYPE_VARCHAR), this.column("ENCODING", TypeInfo.TYPE_INTEGER), this.column("DATLASTSYSOID", TypeInfo.TYPE_INTEGER), this.column("DATALLOWCONN", TypeInfo.TYPE_BOOLEAN), this.column("DATCONFIG", TypeInfo.getTypeInfo(40, -1L, 0, TypeInfo.TYPE_VARCHAR)), this.column("DATACL", TypeInfo.getTypeInfo(40, -1L, 0, TypeInfo.TYPE_VARCHAR)), this.column("DATDBA", TypeInfo.TYPE_INTEGER), this.column("DATTABLESPACE", TypeInfo.TYPE_INTEGER)};
                break;
            }
            case 7: {
                this.setMetaTableName("PG_DESCRIPTION");
                columnArray = new Column[]{this.column("OBJOID", TypeInfo.TYPE_INTEGER), this.column("OBJSUBID", TypeInfo.TYPE_INTEGER), this.column("CLASSOID", TypeInfo.TYPE_INTEGER), this.column("DESCRIPTION", TypeInfo.TYPE_VARCHAR)};
                break;
            }
            case 8: {
                this.setMetaTableName("PG_GROUP");
                columnArray = new Column[]{this.column("OID", TypeInfo.TYPE_INTEGER), this.column("GRONAME", TypeInfo.TYPE_VARCHAR)};
                break;
            }
            case 9: {
                this.setMetaTableName("PG_INDEX");
                columnArray = new Column[]{this.column("OID", TypeInfo.TYPE_INTEGER), this.column("INDEXRELID", TypeInfo.TYPE_INTEGER), this.column("INDRELID", TypeInfo.TYPE_INTEGER), this.column("INDISCLUSTERED", TypeInfo.TYPE_BOOLEAN), this.column("INDISUNIQUE", TypeInfo.TYPE_BOOLEAN), this.column("INDISPRIMARY", TypeInfo.TYPE_BOOLEAN), this.column("INDEXPRS", TypeInfo.TYPE_VARCHAR), this.column("INDKEY", TypeInfo.getTypeInfo(40, -1L, 0, TypeInfo.TYPE_INTEGER)), this.column("INDPRED", TypeInfo.TYPE_VARCHAR)};
                break;
            }
            case 10: {
                this.setMetaTableName("PG_INHERITS");
                columnArray = new Column[]{this.column("INHRELID", TypeInfo.TYPE_INTEGER), this.column("INHPARENT", TypeInfo.TYPE_INTEGER), this.column("INHSEQNO", TypeInfo.TYPE_INTEGER)};
                break;
            }
            case 11: {
                this.setMetaTableName("PG_NAMESPACE");
                columnArray = new Column[]{this.column("ID", TypeInfo.TYPE_INTEGER), this.column("NSPNAME", TypeInfo.TYPE_VARCHAR)};
                break;
            }
            case 12: {
                this.setMetaTableName("PG_PROC");
                columnArray = new Column[]{this.column("OID", TypeInfo.TYPE_INTEGER), this.column("PRONAME", TypeInfo.TYPE_VARCHAR), this.column("PRORETTYPE", TypeInfo.TYPE_INTEGER), this.column("PROARGTYPES", TypeInfo.getTypeInfo(40, -1L, 0, TypeInfo.TYPE_INTEGER)), this.column("PRONAMESPACE", TypeInfo.TYPE_INTEGER)};
                break;
            }
            case 13: {
                this.setMetaTableName("PG_ROLES");
                columnArray = new Column[]{this.column("OID", TypeInfo.TYPE_INTEGER), this.column("ROLNAME", TypeInfo.TYPE_VARCHAR), this.column("ROLSUPER", TypeInfo.TYPE_CHAR), this.column("ROLCREATEROLE", TypeInfo.TYPE_CHAR), this.column("ROLCREATEDB", TypeInfo.TYPE_CHAR)};
                break;
            }
            case 14: {
                this.setMetaTableName("PG_SETTINGS");
                columnArray = new Column[]{this.column("OID", TypeInfo.TYPE_INTEGER), this.column("NAME", TypeInfo.TYPE_VARCHAR), this.column("SETTING", TypeInfo.TYPE_VARCHAR)};
                break;
            }
            case 15: {
                this.setMetaTableName("PG_TABLESPACE");
                columnArray = new Column[]{this.column("OID", TypeInfo.TYPE_INTEGER), this.column("SPCNAME", TypeInfo.TYPE_VARCHAR), this.column("SPCLOCATION", TypeInfo.TYPE_VARCHAR), this.column("SPCOWNER", TypeInfo.TYPE_INTEGER), this.column("SPCACL", TypeInfo.getTypeInfo(40, -1L, 0, TypeInfo.TYPE_VARCHAR))};
                break;
            }
            case 16: {
                this.setMetaTableName("PG_TRIGGER");
                columnArray = new Column[]{this.column("OID", TypeInfo.TYPE_INTEGER), this.column("TGCONSTRRELID", TypeInfo.TYPE_INTEGER), this.column("TGFOID", TypeInfo.TYPE_INTEGER), this.column("TGARGS", TypeInfo.TYPE_INTEGER), this.column("TGNARGS", TypeInfo.TYPE_INTEGER), this.column("TGDEFERRABLE", TypeInfo.TYPE_BOOLEAN), this.column("TGINITDEFERRED", TypeInfo.TYPE_BOOLEAN), this.column("TGCONSTRNAME", TypeInfo.TYPE_VARCHAR), this.column("TGRELID", TypeInfo.TYPE_INTEGER)};
                break;
            }
            case 17: {
                this.setMetaTableName("PG_TYPE");
                columnArray = new Column[]{this.column("OID", TypeInfo.TYPE_INTEGER), this.column("TYPNAME", TypeInfo.TYPE_VARCHAR), this.column("TYPNAMESPACE", TypeInfo.TYPE_INTEGER), this.column("TYPLEN", TypeInfo.TYPE_INTEGER), this.column("TYPTYPE", TypeInfo.TYPE_VARCHAR), this.column("TYPDELIM", TypeInfo.TYPE_VARCHAR), this.column("TYPRELID", TypeInfo.TYPE_INTEGER), this.column("TYPELEM", TypeInfo.TYPE_INTEGER), this.column("TYPBASETYPE", TypeInfo.TYPE_INTEGER), this.column("TYPTYPMOD", TypeInfo.TYPE_INTEGER), this.column("TYPNOTNULL", TypeInfo.TYPE_BOOLEAN), this.column("TYPINPUT", TypeInfo.TYPE_VARCHAR)};
                break;
            }
            case 18: {
                this.setMetaTableName("PG_USER");
                columnArray = new Column[]{this.column("OID", TypeInfo.TYPE_INTEGER), this.column("USENAME", TypeInfo.TYPE_VARCHAR), this.column("USECREATEDB", TypeInfo.TYPE_BOOLEAN), this.column("USESUPER", TypeInfo.TYPE_BOOLEAN)};
                break;
            }
            default: {
                throw DbException.getInternalError("type=" + n2);
            }
        }
        this.setColumns(columnArray);
        this.indexColumn = -1;
        this.metaIndex = null;
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public ArrayList<Row> generateRows(SessionLocal sessionLocal, SearchRow searchRow, SearchRow searchRow2) {
        ArrayList<Row> arrayList = Utils.newSmallArrayList();
        String string = this.database.getShortName();
        boolean bl = sessionLocal.getUser().isAdmin();
        switch (this.type) {
            case 0: {
                void dbObject;
                String[] stringArray = new String[]{"btree", "hash"};
                boolean i = false;
                int user = stringArray.length;
                while (dbObject < user) {
                    this.add(sessionLocal, arrayList, ValueInteger.get((int)dbObject), stringArray[dbObject]);
                    ++dbObject;
                }
                break;
            }
            case 1: {
                break;
            }
            case 2: {
                for (Schema dbObject : this.database.getAllSchemas()) {
                    for (Table table : dbObject.getAllTablesAndViews(sessionLocal)) {
                        if (this.hideTable(table, sessionLocal)) continue;
                        this.pgAttribute(sessionLocal, arrayList, table);
                    }
                }
                for (Table schema : sessionLocal.getLocalTempTables()) {
                    if (this.hideTable(schema, sessionLocal)) continue;
                    this.pgAttribute(sessionLocal, arrayList, schema);
                }
                break;
            }
            case 3: {
                break;
            }
            case 4: {
                for (Schema rightOwner : this.database.getAllSchemas()) {
                    for (Table table : rightOwner.getAllTablesAndViews(sessionLocal)) {
                        if (this.hideTable(table, sessionLocal)) continue;
                        this.pgClass(sessionLocal, arrayList, table);
                    }
                }
                for (Table i : sessionLocal.getLocalTempTables()) {
                    if (this.hideTable(i, sessionLocal)) continue;
                    this.pgClass(sessionLocal, arrayList, i);
                }
                break;
            }
            case 5: {
                this.pgConstraint(sessionLocal, arrayList);
                break;
            }
            case 6: {
                int n = Integer.MAX_VALUE;
                for (RightOwner rightOwner : this.database.getAllUsersAndRoles()) {
                    int n2;
                    if (!(rightOwner instanceof User) || !((User)rightOwner).isAdmin() || (n2 = rightOwner.getId()) >= n) continue;
                    n = n2;
                }
                this.add(sessionLocal, arrayList, ValueInteger.get(100001), string, ValueInteger.get(6), ValueInteger.get(100000), ValueBoolean.TRUE, null, null, ValueInteger.get(n), ValueInteger.get(0));
                break;
            }
            case 7: {
                this.add(sessionLocal, arrayList, ValueInteger.get(0), ValueInteger.get(0), ValueInteger.get(-1), string);
                break;
            }
            case 8: 
            case 9: 
            case 10: {
                break;
            }
            case 11: {
                for (Schema rightOwner : this.database.getAllSchemas()) {
                    this.add(sessionLocal, arrayList, ValueInteger.get(rightOwner.getId()), rightOwner.getName());
                }
                break;
            }
            case 12: {
                break;
            }
            case 13: {
                for (RightOwner rightOwner : this.database.getAllUsersAndRoles()) {
                    if (!bl && sessionLocal.getUser() != rightOwner) continue;
                    String string2 = rightOwner instanceof User && ((User)rightOwner).isAdmin() ? "t" : "f";
                    this.add(sessionLocal, arrayList, ValueInteger.get(rightOwner.getId()), this.identifier(rightOwner.getName()), string2, string2, string2);
                }
                break;
            }
            case 14: {
                void var8_28;
                String[][] stringArrayArray = new String[][]{{"autovacuum", "on"}, {"stats_start_collector", "on"}, {"stats_row_level", "on"}};
                boolean bl2 = false;
                int n = stringArrayArray.length;
                while (var8_28 < n) {
                    String[] stringArray = stringArrayArray[var8_28];
                    this.add(sessionLocal, arrayList, ValueInteger.get((int)var8_28), stringArray[0], stringArray[1]);
                    ++var8_28;
                }
                break;
            }
            case 15: {
                this.add(sessionLocal, arrayList, ValueInteger.get(0), "main", "?", ValueInteger.get(0), null);
                break;
            }
            case 16: {
                break;
            }
            case 17: {
                void var8_30;
                HashSet<Integer> hashSet = new HashSet<Integer>();
                boolean bl3 = true;
                int n = 42;
                while (var8_30 < n) {
                    int n3;
                    DataType dataType = DataType.getDataType((int)var8_30);
                    if (dataType.type != 40 && (n3 = PgServer.convertType(TypeInfo.getTypeInfo(dataType.type))) != 705 && hashSet.add(n3)) {
                        this.add(sessionLocal, arrayList, ValueInteger.get(n3), Value.getTypeName(dataType.type), ValueInteger.get(-1000), ValueInteger.get(-1), "b", ",", ValueInteger.get(0), ValueInteger.get(0), ValueInteger.get(0), ValueInteger.get(-1), ValueBoolean.FALSE, null);
                    }
                    ++var8_30;
                }
                for (Object[] objectArray : PG_EXTRA_TYPES) {
                    this.add(sessionLocal, arrayList, ValueInteger.get((Integer)objectArray[0]), objectArray[1], ValueInteger.get(-1000), ValueInteger.get((Integer)objectArray[2]), "b", ",", ValueInteger.get(0), ValueInteger.get((Integer)objectArray[3]), ValueInteger.get(0), ValueInteger.get(-1), ValueBoolean.FALSE, null);
                }
                break;
            }
            case 18: {
                for (RightOwner rightOwner : this.database.getAllUsersAndRoles()) {
                    if (!(rightOwner instanceof User)) continue;
                    User user = (User)rightOwner;
                    if (!bl && sessionLocal.getUser() != user) continue;
                    ValueBoolean valueBoolean = ValueBoolean.get(user.isAdmin());
                    this.add(sessionLocal, arrayList, ValueInteger.get(user.getId()), this.identifier(user.getName()), valueBoolean, valueBoolean);
                }
                break;
            }
            default: {
                throw DbException.getInternalError("type=" + this.type);
            }
        }
        return arrayList;
    }

    private void pgAttribute(SessionLocal sessionLocal, ArrayList<Row> arrayList, Table table) {
        Column[] columnArray = table.getColumns();
        int n = table.getId();
        int n2 = 0;
        while (n2 < columnArray.length) {
            Column hasSQL = columnArray[n2++];
            this.addAttribute(sessionLocal, arrayList, n * 10000 + n2, n, hasSQL, n2);
        }
        for (Index index : table.getIndexes()) {
            if (index.getCreateSQL() == null) continue;
            columnArray = index.getColumns();
            int n3 = 0;
            while (n3 < columnArray.length) {
                Column column = columnArray[n3++];
                int n4 = index.getId();
                this.addAttribute(sessionLocal, arrayList, 1000000 * n4 + n * 10000 + n3, n4, column, n3);
            }
        }
    }

    private void pgClass(SessionLocal sessionLocal, ArrayList<Row> arrayList, Table table) {
        ArrayList<TriggerObject> arrayList2 = table.getTriggers();
        this.addClass(sessionLocal, arrayList, table.getId(), table.getName(), table.getSchema().getId(), table.isView() ? "v" : "r", false, arrayList2 != null ? arrayList2.size() : 0);
        ArrayList<Index> arrayList3 = table.getIndexes();
        if (arrayList3 != null) {
            for (Index index : arrayList3) {
                if (index.getCreateSQL() == null) continue;
                this.addClass(sessionLocal, arrayList, index.getId(), index.getName(), index.getSchema().getId(), "i", true, 0);
            }
        }
    }

    private void pgConstraint(SessionLocal sessionLocal, ArrayList<Row> arrayList) {
        for (Schema schema : this.database.getAllSchemasNoMeta()) {
            for (Constraint constraint : schema.getAllConstraints()) {
                Table table;
                Constraint.Type type = constraint.getConstraintType();
                if (type == Constraint.Type.DOMAIN || this.hideTable(table = constraint.getTable(), sessionLocal)) continue;
                ArrayList<ValueSmallint> arrayList2 = new ArrayList<ValueSmallint>();
                for (Column column : constraint.getReferencedColumns(table)) {
                    arrayList2.add(ValueSmallint.get((short)(column.getColumnId() + 1)));
                }
                Table table2 = constraint.getRefTable();
                this.add(sessionLocal, arrayList, ValueInteger.get(constraint.getId()), constraint.getName(), StringUtils.toLowerEnglish(type.getSqlName().substring(0, 1)), ValueInteger.get(table.getId()), ValueInteger.get(table2 != null && table2 != table && !this.hideTable(table2, sessionLocal) ? table.getId() : 0), ValueArray.get(TypeInfo.TYPE_SMALLINT, arrayList2.toArray(Value.EMPTY_VALUES), null));
            }
        }
    }

    private void addAttribute(SessionLocal sessionLocal, ArrayList<Row> arrayList, int n, int n2, Column column, int n3) {
        long l = column.getType().getPrecision();
        this.add(sessionLocal, arrayList, ValueInteger.get(n), ValueInteger.get(n2), column.getName(), ValueInteger.get(PgServer.convertType(column.getType())), ValueInteger.get(l > 255L ? -1 : (int)l), ValueInteger.get(n3), ValueInteger.get(-1), ValueBoolean.get(!column.isNullable()), ValueBoolean.FALSE, ValueBoolean.FALSE);
    }

    private void addClass(SessionLocal sessionLocal, ArrayList<Row> arrayList, int n, String string, int n2, String string2, boolean bl, int n3) {
        this.add(sessionLocal, arrayList, ValueInteger.get(n), string, ValueInteger.get(n2), string2, ValueInteger.get(0), ValueDouble.get(0.0), ValueInteger.get(0), ValueInteger.get(0), ValueBoolean.get(bl), ValueBoolean.FALSE, ValueBoolean.FALSE, ValueSmallint.get((short)0), ValueInteger.get(n3));
    }

    @Override
    public long getMaxDataModificationId() {
        return this.database.getModificationDataId();
    }
}

