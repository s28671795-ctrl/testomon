/*
 * Decompiled with CFR 0.152.
 */
package org.h2.jdbc.meta;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import org.h2.command.dml.Help;
import org.h2.constraint.Constraint;
import org.h2.constraint.ConstraintActionType;
import org.h2.constraint.ConstraintReferential;
import org.h2.constraint.ConstraintUnique;
import org.h2.engine.Database;
import org.h2.engine.DbObject;
import org.h2.engine.Mode;
import org.h2.engine.Right;
import org.h2.engine.SessionLocal;
import org.h2.engine.User;
import org.h2.expression.condition.CompareLike;
import org.h2.index.Index;
import org.h2.jdbc.meta.DatabaseMetaLocalBase;
import org.h2.message.DbException;
import org.h2.mode.DefaultNullOrdering;
import org.h2.result.ResultInterface;
import org.h2.result.SimpleResult;
import org.h2.result.SortOrder;
import org.h2.schema.FunctionAlias;
import org.h2.schema.Schema;
import org.h2.schema.SchemaObject;
import org.h2.schema.UserDefinedFunction;
import org.h2.table.Column;
import org.h2.table.IndexColumn;
import org.h2.table.Table;
import org.h2.table.TableSynonym;
import org.h2.util.MathUtils;
import org.h2.util.StringUtils;
import org.h2.util.Utils;
import org.h2.value.DataType;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueBigint;
import org.h2.value.ValueBoolean;
import org.h2.value.ValueInteger;
import org.h2.value.ValueNull;
import org.h2.value.ValueSmallint;
import org.h2.value.ValueToObjectConverter2;
import org.h2.value.ValueVarchar;

public final class DatabaseMetaLocal
extends DatabaseMetaLocalBase {
    private static final Value YES = ValueVarchar.get("YES");
    private static final Value NO = ValueVarchar.get("NO");
    private static final ValueSmallint BEST_ROW_SESSION = ValueSmallint.get((short)2);
    private static final ValueSmallint BEST_ROW_NOT_PSEUDO = ValueSmallint.get((short)1);
    private static final ValueInteger COLUMN_NO_NULLS = ValueInteger.get(0);
    private static final ValueSmallint COLUMN_NO_NULLS_SMALL = ValueSmallint.get((short)0);
    private static final ValueInteger COLUMN_NULLABLE = ValueInteger.get(1);
    private static final ValueSmallint COLUMN_NULLABLE_UNKNOWN_SMALL = ValueSmallint.get((short)2);
    private static final ValueSmallint IMPORTED_KEY_CASCADE = ValueSmallint.get((short)0);
    private static final ValueSmallint IMPORTED_KEY_RESTRICT = ValueSmallint.get((short)1);
    private static final ValueSmallint IMPORTED_KEY_DEFAULT = ValueSmallint.get((short)4);
    private static final ValueSmallint IMPORTED_KEY_SET_NULL = ValueSmallint.get((short)2);
    private static final ValueSmallint IMPORTED_KEY_NOT_DEFERRABLE = ValueSmallint.get((short)7);
    private static final ValueSmallint PROCEDURE_COLUMN_IN = ValueSmallint.get((short)1);
    private static final ValueSmallint PROCEDURE_COLUMN_RETURN = ValueSmallint.get((short)5);
    private static final ValueSmallint PROCEDURE_NO_RESULT = ValueSmallint.get((short)1);
    private static final ValueSmallint PROCEDURE_RETURNS_RESULT = ValueSmallint.get((short)2);
    private static final ValueSmallint TABLE_INDEX_HASHED = ValueSmallint.get((short)2);
    private static final ValueSmallint TABLE_INDEX_OTHER = ValueSmallint.get((short)3);
    private static final String[] TABLE_TYPES = new String[]{"BASE TABLE", "GLOBAL TEMPORARY", "LOCAL TEMPORARY", "SYNONYM", "VIEW"};
    private static final ValueSmallint TYPE_NULLABLE = ValueSmallint.get((short)1);
    private static final ValueSmallint TYPE_SEARCHABLE = ValueSmallint.get((short)3);
    private static final Value NO_USAGE_RESTRICTIONS = ValueVarchar.get("NO_USAGE_RESTRICTIONS");
    private final SessionLocal session;

    public DatabaseMetaLocal(SessionLocal sessionLocal) {
        this.session = sessionLocal;
    }

    @Override
    public final DefaultNullOrdering defaultNullOrdering() {
        return this.session.getDatabase().getDefaultNullOrdering();
    }

    @Override
    public String getSQLKeywords() {
        StringBuilder stringBuilder = new StringBuilder(103).append("CURRENT_CATALOG,CURRENT_SCHEMA,GROUPS,IF,ILIKE,KEY,");
        Mode mode = this.session.getMode();
        if (mode.limit) {
            stringBuilder.append("LIMIT,");
        }
        if (mode.minusIsExcept) {
            stringBuilder.append("MINUS,");
        }
        stringBuilder.append("OFFSET,QUALIFY,REGEXP,ROWNUM,");
        if (mode.topInSelect || mode.topInDML) {
            stringBuilder.append("TOP,");
        }
        return stringBuilder.append("_ROWID_").toString();
    }

    @Override
    public String getNumericFunctions() {
        return this.getFunctions("Functions (Numeric)");
    }

    @Override
    public String getStringFunctions() {
        return this.getFunctions("Functions (String)");
    }

    @Override
    public String getSystemFunctions() {
        return this.getFunctions("Functions (System)");
    }

    @Override
    public String getTimeDateFunctions() {
        return this.getFunctions("Functions (Time and Date)");
    }

    private String getFunctions(String string) {
        this.checkClosed();
        StringBuilder stringBuilder = new StringBuilder();
        try {
            ResultSet resultSet = Help.getTable();
            while (resultSet.next()) {
                String string2;
                int n;
                if (!resultSet.getString(1).trim().equals(string)) continue;
                if (stringBuilder.length() != 0) {
                    stringBuilder.append(',');
                }
                if ((n = (string2 = resultSet.getString(2).trim()).indexOf(32)) >= 0) {
                    StringUtils.trimSubstring(stringBuilder, string2, 0, n);
                    continue;
                }
                stringBuilder.append(string2);
            }
        }
        catch (Exception exception) {
            throw DbException.convert(exception);
        }
        return stringBuilder.toString();
    }

    @Override
    public String getSearchStringEscape() {
        return this.session.getDatabase().getSettings().defaultEscape;
    }

    @Override
    public ResultInterface getProcedures(String string, String string2, String string3) {
        this.checkClosed();
        SimpleResult simpleResult = new SimpleResult();
        simpleResult.addColumn("PROCEDURE_CAT", TypeInfo.TYPE_VARCHAR);
        simpleResult.addColumn("PROCEDURE_SCHEM", TypeInfo.TYPE_VARCHAR);
        simpleResult.addColumn("PROCEDURE_NAME", TypeInfo.TYPE_VARCHAR);
        simpleResult.addColumn("RESERVED1", TypeInfo.TYPE_NULL);
        simpleResult.addColumn("RESERVED2", TypeInfo.TYPE_NULL);
        simpleResult.addColumn("RESERVED3", TypeInfo.TYPE_NULL);
        simpleResult.addColumn("REMARKS", TypeInfo.TYPE_VARCHAR);
        simpleResult.addColumn("PROCEDURE_TYPE", TypeInfo.TYPE_SMALLINT);
        simpleResult.addColumn("SPECIFIC_NAME", TypeInfo.TYPE_VARCHAR);
        if (!this.checkCatalogName(string)) {
            return simpleResult;
        }
        Database database = this.session.getDatabase();
        Value value = this.getString(database.getShortName());
        CompareLike compareLike = this.getLike(string3);
        for (Schema schema : this.getSchemasForPattern(string2)) {
            Value value2 = this.getString(schema.getName());
            for (UserDefinedFunction userDefinedFunction : schema.getAllFunctionsAndAggregates()) {
                String string4 = userDefinedFunction.getName();
                if (compareLike != null && !compareLike.test(string4)) continue;
                Value value3 = this.getString(string4);
                if (userDefinedFunction instanceof FunctionAlias) {
                    FunctionAlias.JavaMethod[] javaMethodArray;
                    try {
                        javaMethodArray = ((FunctionAlias)userDefinedFunction).getJavaMethods();
                    }
                    catch (DbException dbException) {
                        continue;
                    }
                    for (int i = 0; i < javaMethodArray.length; ++i) {
                        FunctionAlias.JavaMethod javaMethod = javaMethodArray[i];
                        TypeInfo typeInfo = javaMethod.getDataType();
                        this.getProceduresAdd(simpleResult, value, value2, value3, userDefinedFunction.getComment(), typeInfo == null || typeInfo.getValueType() != 0 ? PROCEDURE_RETURNS_RESULT : PROCEDURE_NO_RESULT, this.getString(string4 + '_' + (i + 1)));
                    }
                    continue;
                }
                this.getProceduresAdd(simpleResult, value, value2, value3, userDefinedFunction.getComment(), PROCEDURE_RETURNS_RESULT, value3);
            }
        }
        simpleResult.sortRows(new SortOrder(this.session, new int[]{1, 2, 8}));
        return simpleResult;
    }

    private void getProceduresAdd(SimpleResult simpleResult, Value value, Value value2, Value value3, String string, ValueSmallint valueSmallint, Value value4) {
        simpleResult.addRow(value, value2, value3, ValueNull.INSTANCE, ValueNull.INSTANCE, ValueNull.INSTANCE, this.getString(string), valueSmallint, value4);
    }

    @Override
    public ResultInterface getProcedureColumns(String string, String string2, String string3, String string4) {
        this.checkClosed();
        SimpleResult simpleResult = new SimpleResult();
        simpleResult.addColumn("PROCEDURE_CAT", TypeInfo.TYPE_VARCHAR);
        simpleResult.addColumn("PROCEDURE_SCHEM", TypeInfo.TYPE_VARCHAR);
        simpleResult.addColumn("PROCEDURE_NAME", TypeInfo.TYPE_VARCHAR);
        simpleResult.addColumn("COLUMN_NAME", TypeInfo.TYPE_VARCHAR);
        simpleResult.addColumn("COLUMN_TYPE", TypeInfo.TYPE_SMALLINT);
        simpleResult.addColumn("DATA_TYPE", TypeInfo.TYPE_INTEGER);
        simpleResult.addColumn("TYPE_NAME", TypeInfo.TYPE_VARCHAR);
        simpleResult.addColumn("PRECISION", TypeInfo.TYPE_INTEGER);
        simpleResult.addColumn("LENGTH", TypeInfo.TYPE_INTEGER);
        simpleResult.addColumn("SCALE", TypeInfo.TYPE_SMALLINT);
        simpleResult.addColumn("RADIX", TypeInfo.TYPE_SMALLINT);
        simpleResult.addColumn("NULLABLE", TypeInfo.TYPE_SMALLINT);
        simpleResult.addColumn("REMARKS", TypeInfo.TYPE_VARCHAR);
        simpleResult.addColumn("COLUMN_DEF", TypeInfo.TYPE_VARCHAR);
        simpleResult.addColumn("SQL_DATA_TYPE", TypeInfo.TYPE_INTEGER);
        simpleResult.addColumn("SQL_DATETIME_SUB", TypeInfo.TYPE_INTEGER);
        simpleResult.addColumn("CHAR_OCTET_LENGTH", TypeInfo.TYPE_INTEGER);
        simpleResult.addColumn("ORDINAL_POSITION", TypeInfo.TYPE_INTEGER);
        simpleResult.addColumn("IS_NULLABLE", TypeInfo.TYPE_VARCHAR);
        simpleResult.addColumn("SPECIFIC_NAME", TypeInfo.TYPE_VARCHAR);
        if (!this.checkCatalogName(string)) {
            return simpleResult;
        }
        Database database = this.session.getDatabase();
        Value value = this.getString(database.getShortName());
        CompareLike compareLike = this.getLike(string3);
        for (Schema schema : this.getSchemasForPattern(string2)) {
            Value value2 = this.getString(schema.getName());
            for (UserDefinedFunction userDefinedFunction : schema.getAllFunctionsAndAggregates()) {
                FunctionAlias.JavaMethod[] javaMethodArray;
                if (!(userDefinedFunction instanceof FunctionAlias)) continue;
                String string5 = userDefinedFunction.getName();
                if (compareLike != null && !compareLike.test(string5)) continue;
                Value value3 = this.getString(string5);
                try {
                    javaMethodArray = ((FunctionAlias)userDefinedFunction).getJavaMethods();
                }
                catch (DbException dbException) {
                    continue;
                }
                int n = javaMethodArray.length;
                for (int i = 0; i < n; ++i) {
                    FunctionAlias.JavaMethod javaMethod = javaMethodArray[i];
                    Value value4 = this.getString(string5 + '_' + (i + 1));
                    TypeInfo typeInfo = javaMethod.getDataType();
                    if (typeInfo != null && typeInfo.getValueType() != 0) {
                        this.getProcedureColumnAdd(simpleResult, value, value2, value3, value4, typeInfo, javaMethod.getClass().isPrimitive(), 0);
                    }
                    Class<?>[] classArray = javaMethod.getColumnClasses();
                    int n2 = 1;
                    int n3 = classArray.length;
                    for (int j = javaMethod.hasConnectionParam() ? 1 : 0; j < n3; ++j) {
                        Class<?> clazz = classArray[j];
                        this.getProcedureColumnAdd(simpleResult, value, value2, value3, value4, ValueToObjectConverter2.classToType(clazz), clazz.isPrimitive(), n2);
                        ++n2;
                    }
                }
            }
        }
        simpleResult.sortRows(new SortOrder(this.session, new int[]{1, 2, 19}));
        return simpleResult;
    }

    private void getProcedureColumnAdd(SimpleResult simpleResult, Value value, Value value2, Value value3, Value value4, TypeInfo typeInfo, boolean bl, int n) {
        int n2 = typeInfo.getValueType();
        DataType dataType = DataType.getDataType(n2);
        ValueInteger valueInteger = ValueInteger.get(MathUtils.convertLongToInt(typeInfo.getPrecision()));
        simpleResult.addRow(value, value2, value3, this.getString(n == 0 ? "RESULT" : "P" + n), n == 0 ? PROCEDURE_COLUMN_RETURN : PROCEDURE_COLUMN_IN, ValueInteger.get(DataType.convertTypeToSQLType(typeInfo)), this.getDataTypeName(typeInfo), valueInteger, valueInteger, dataType.supportsScale ? ValueSmallint.get(MathUtils.convertIntToShort(dataType.defaultScale)) : ValueNull.INSTANCE, DatabaseMetaLocal.getRadix(n2, true), bl ? COLUMN_NO_NULLS_SMALL : COLUMN_NULLABLE_UNKNOWN_SMALL, ValueNull.INSTANCE, ValueNull.INSTANCE, ValueNull.INSTANCE, ValueNull.INSTANCE, DataType.isBinaryStringType(n2) || DataType.isCharacterStringType(n2) ? valueInteger : ValueNull.INSTANCE, ValueInteger.get(n), ValueVarchar.EMPTY, value4);
    }

    @Override
    public ResultInterface getTables(String string, String string2, String string3, String[] stringArray) {
        HashSet<String> hashSet;
        SimpleResult simpleResult = new SimpleResult();
        simpleResult.addColumn("TABLE_CAT", TypeInfo.TYPE_VARCHAR);
        simpleResult.addColumn("TABLE_SCHEM", TypeInfo.TYPE_VARCHAR);
        simpleResult.addColumn("TABLE_NAME", TypeInfo.TYPE_VARCHAR);
        simpleResult.addColumn("TABLE_TYPE", TypeInfo.TYPE_VARCHAR);
        simpleResult.addColumn("REMARKS", TypeInfo.TYPE_VARCHAR);
        simpleResult.addColumn("TYPE_CAT", TypeInfo.TYPE_VARCHAR);
        simpleResult.addColumn("TYPE_SCHEM", TypeInfo.TYPE_VARCHAR);
        simpleResult.addColumn("TYPE_NAME", TypeInfo.TYPE_VARCHAR);
        simpleResult.addColumn("SELF_REFERENCING_COL_NAME", TypeInfo.TYPE_VARCHAR);
        simpleResult.addColumn("REF_GENERATION", TypeInfo.TYPE_VARCHAR);
        if (!this.checkCatalogName(string)) {
            return simpleResult;
        }
        Database database = this.session.getDatabase();
        Value value = this.getString(database.getShortName());
        if (stringArray != null) {
            hashSet = new HashSet<String>(8);
            for (String iterator2 : stringArray) {
                int n = Arrays.binarySearch(TABLE_TYPES, iterator2);
                if (n >= 0) {
                    hashSet.add(TABLE_TYPES[n]);
                    continue;
                }
                if (!iterator2.equals("TABLE")) continue;
                hashSet.add("BASE TABLE");
            }
            if (hashSet.isEmpty()) {
                return simpleResult;
            }
        } else {
            hashSet = null;
        }
        for (Schema schema : this.getSchemasForPattern(string2)) {
            Value value2 = this.getString(schema.getName());
            for (SchemaObject schemaObject : this.getTablesForPattern(schema, string3)) {
                Value value3 = this.getString(schemaObject.getName());
                if (schemaObject instanceof Table) {
                    Table table = (Table)schemaObject;
                    if (table.isHidden()) continue;
                    this.getTablesAdd(simpleResult, value, value2, value3, table, false, hashSet);
                    continue;
                }
                this.getTablesAdd(simpleResult, value, value2, value3, ((TableSynonym)schemaObject).getSynonymFor(), true, hashSet);
            }
        }
        simpleResult.sortRows(new SortOrder(this.session, new int[]{3, 1, 2}));
        return simpleResult;
    }

    private void getTablesAdd(SimpleResult simpleResult, Value value, Value value2, Value value3, Table table, boolean bl, HashSet<String> hashSet) {
        String string;
        String string2 = string = bl ? "SYNONYM" : table.getSQLTableType();
        if (hashSet != null && !hashSet.contains(string)) {
            return;
        }
        simpleResult.addRow(value, value2, value3, this.getString(string), this.getString(table.getComment()), ValueNull.INSTANCE, ValueNull.INSTANCE, ValueNull.INSTANCE, ValueNull.INSTANCE, ValueNull.INSTANCE);
    }

    @Override
    public ResultInterface getSchemas() {
        return this.getSchemas(null, null);
    }

    @Override
    public ResultInterface getCatalogs() {
        this.checkClosed();
        SimpleResult simpleResult = new SimpleResult();
        simpleResult.addColumn("TABLE_CAT", TypeInfo.TYPE_VARCHAR);
        simpleResult.addRow(this.getString(this.session.getDatabase().getShortName()));
        return simpleResult;
    }

    @Override
    public ResultInterface getTableTypes() {
        SimpleResult simpleResult = new SimpleResult();
        simpleResult.addColumn("TABLE_TYPE", TypeInfo.TYPE_VARCHAR);
        simpleResult.addRow(this.getString("BASE TABLE"));
        simpleResult.addRow(this.getString("GLOBAL TEMPORARY"));
        simpleResult.addRow(this.getString("LOCAL TEMPORARY"));
        simpleResult.addRow(this.getString("SYNONYM"));
        simpleResult.addRow(this.getString("VIEW"));
        return simpleResult;
    }

    @Override
    public ResultInterface getColumns(String string, String string2, String string3, String string4) {
        SimpleResult simpleResult = new SimpleResult();
        simpleResult.addColumn("TABLE_CAT", TypeInfo.TYPE_VARCHAR);
        simpleResult.addColumn("TABLE_SCHEM", TypeInfo.TYPE_VARCHAR);
        simpleResult.addColumn("TABLE_NAME", TypeInfo.TYPE_VARCHAR);
        simpleResult.addColumn("COLUMN_NAME", TypeInfo.TYPE_VARCHAR);
        simpleResult.addColumn("DATA_TYPE", TypeInfo.TYPE_INTEGER);
        simpleResult.addColumn("TYPE_NAME", TypeInfo.TYPE_VARCHAR);
        simpleResult.addColumn("COLUMN_SIZE", TypeInfo.TYPE_INTEGER);
        simpleResult.addColumn("BUFFER_LENGTH", TypeInfo.TYPE_INTEGER);
        simpleResult.addColumn("DECIMAL_DIGITS", TypeInfo.TYPE_INTEGER);
        simpleResult.addColumn("NUM_PREC_RADIX", TypeInfo.TYPE_INTEGER);
        simpleResult.addColumn("NULLABLE", TypeInfo.TYPE_INTEGER);
        simpleResult.addColumn("REMARKS", TypeInfo.TYPE_VARCHAR);
        simpleResult.addColumn("COLUMN_DEF", TypeInfo.TYPE_VARCHAR);
        simpleResult.addColumn("SQL_DATA_TYPE", TypeInfo.TYPE_INTEGER);
        simpleResult.addColumn("SQL_DATETIME_SUB", TypeInfo.TYPE_INTEGER);
        simpleResult.addColumn("CHAR_OCTET_LENGTH", TypeInfo.TYPE_INTEGER);
        simpleResult.addColumn("ORDINAL_POSITION", TypeInfo.TYPE_INTEGER);
        simpleResult.addColumn("IS_NULLABLE", TypeInfo.TYPE_VARCHAR);
        simpleResult.addColumn("SCOPE_CATALOG", TypeInfo.TYPE_VARCHAR);
        simpleResult.addColumn("SCOPE_SCHEMA", TypeInfo.TYPE_VARCHAR);
        simpleResult.addColumn("SCOPE_TABLE", TypeInfo.TYPE_VARCHAR);
        simpleResult.addColumn("SOURCE_DATA_TYPE", TypeInfo.TYPE_SMALLINT);
        simpleResult.addColumn("IS_AUTOINCREMENT", TypeInfo.TYPE_VARCHAR);
        simpleResult.addColumn("IS_GENERATEDCOLUMN", TypeInfo.TYPE_VARCHAR);
        if (!this.checkCatalogName(string)) {
            return simpleResult;
        }
        Database database = this.session.getDatabase();
        Value value = this.getString(database.getShortName());
        CompareLike compareLike = this.getLike(string4);
        for (Schema schema : this.getSchemasForPattern(string2)) {
            Value value2 = this.getString(schema.getName());
            for (SchemaObject schemaObject : this.getTablesForPattern(schema, string3)) {
                SchemaObject schemaObject2;
                Value value3 = this.getString(schemaObject.getName());
                if (schemaObject instanceof Table) {
                    schemaObject2 = (Table)schemaObject;
                    if (((Table)schemaObject2).isHidden()) continue;
                    this.getColumnsAdd(simpleResult, value, value2, value3, (Table)schemaObject2, compareLike);
                    continue;
                }
                schemaObject2 = (TableSynonym)schemaObject;
                Table table = ((TableSynonym)schemaObject2).getSynonymFor();
                this.getColumnsAdd(simpleResult, value, value2, value3, table, compareLike);
            }
        }
        simpleResult.sortRows(new SortOrder(this.session, new int[]{1, 2, 16}));
        return simpleResult;
    }

    private void getColumnsAdd(SimpleResult simpleResult, Value value, Value value2, Value value3, Table table, CompareLike compareLike) {
        int n = 0;
        for (Column column : table.getColumns()) {
            if (!column.getVisible()) continue;
            ++n;
            String string = column.getName();
            if (compareLike != null && !compareLike.test(string)) continue;
            TypeInfo typeInfo = column.getType();
            ValueInteger valueInteger = ValueInteger.get(MathUtils.convertLongToInt(typeInfo.getPrecision()));
            boolean bl = column.isNullable();
            boolean bl2 = column.isGenerated();
            simpleResult.addRow(value, value2, value3, this.getString(string), ValueInteger.get(DataType.convertTypeToSQLType(typeInfo)), this.getDataTypeName(typeInfo), valueInteger, ValueNull.INSTANCE, ValueInteger.get(typeInfo.getScale()), DatabaseMetaLocal.getRadix(typeInfo.getValueType(), false), bl ? COLUMN_NULLABLE : COLUMN_NO_NULLS, this.getString(column.getComment()), bl2 ? ValueNull.INSTANCE : this.getString(column.getDefaultSQL()), ValueNull.INSTANCE, ValueNull.INSTANCE, valueInteger, ValueInteger.get(n), bl ? YES : NO, ValueNull.INSTANCE, ValueNull.INSTANCE, ValueNull.INSTANCE, ValueNull.INSTANCE, column.isIdentity() ? YES : NO, bl2 ? YES : NO);
        }
    }

    @Override
    public ResultInterface getColumnPrivileges(String string, String string2, String string3, String string4) {
        if (string3 == null) {
            throw DbException.getInvalidValueException("table", null);
        }
        this.checkClosed();
        SimpleResult simpleResult = new SimpleResult();
        simpleResult.addColumn("TABLE_CAT", TypeInfo.TYPE_VARCHAR);
        simpleResult.addColumn("TABLE_SCHEM", TypeInfo.TYPE_VARCHAR);
        simpleResult.addColumn("TABLE_NAME", TypeInfo.TYPE_VARCHAR);
        simpleResult.addColumn("COLUMN_NAME", TypeInfo.TYPE_VARCHAR);
        simpleResult.addColumn("GRANTOR", TypeInfo.TYPE_VARCHAR);
        simpleResult.addColumn("GRANTEE", TypeInfo.TYPE_VARCHAR);
        simpleResult.addColumn("PRIVILEGE", TypeInfo.TYPE_VARCHAR);
        simpleResult.addColumn("IS_GRANTABLE", TypeInfo.TYPE_VARCHAR);
        if (!this.checkCatalogName(string)) {
            return simpleResult;
        }
        Database database = this.session.getDatabase();
        Value value = this.getString(database.getShortName());
        CompareLike compareLike = this.getLike(string4);
        for (Right right : database.getAllRights()) {
            Schema schema;
            String string5;
            Table table;
            DbObject dbObject = right.getGrantedObject();
            if (!(dbObject instanceof Table) || (table = (Table)dbObject).isHidden() || !database.equalsIdentifiers(string3, string5 = table.getName()) || !this.checkSchema(string2, schema = table.getSchema())) continue;
            this.addPrivileges(simpleResult, value, schema.getName(), string5, right.getGrantee(), right.getRightMask(), compareLike, table.getColumns());
        }
        simpleResult.sortRows(new SortOrder(this.session, new int[]{3, 6}));
        return simpleResult;
    }

    @Override
    public ResultInterface getTablePrivileges(String string, String string2, String string3) {
        this.checkClosed();
        SimpleResult simpleResult = new SimpleResult();
        simpleResult.addColumn("TABLE_CAT", TypeInfo.TYPE_VARCHAR);
        simpleResult.addColumn("TABLE_SCHEM", TypeInfo.TYPE_VARCHAR);
        simpleResult.addColumn("TABLE_NAME", TypeInfo.TYPE_VARCHAR);
        simpleResult.addColumn("GRANTOR", TypeInfo.TYPE_VARCHAR);
        simpleResult.addColumn("GRANTEE", TypeInfo.TYPE_VARCHAR);
        simpleResult.addColumn("PRIVILEGE", TypeInfo.TYPE_VARCHAR);
        simpleResult.addColumn("IS_GRANTABLE", TypeInfo.TYPE_VARCHAR);
        if (!this.checkCatalogName(string)) {
            return simpleResult;
        }
        Database database = this.session.getDatabase();
        Value value = this.getString(database.getShortName());
        CompareLike compareLike = this.getLike(string2);
        CompareLike compareLike2 = this.getLike(string3);
        for (Right right : database.getAllRights()) {
            Table table;
            DbObject dbObject = right.getGrantedObject();
            if (!(dbObject instanceof Table) || (table = (Table)dbObject).isHidden()) continue;
            String string4 = table.getName();
            if (compareLike2 != null && !compareLike2.test(string4)) continue;
            Schema schema = table.getSchema();
            String string5 = schema.getName();
            if (string2 != null && (string2.isEmpty() ? schema != database.getMainSchema() : !compareLike.test(string5))) continue;
            this.addPrivileges(simpleResult, value, string5, string4, right.getGrantee(), right.getRightMask(), null, null);
        }
        simpleResult.sortRows(new SortOrder(this.session, new int[]{1, 2, 5}));
        return simpleResult;
    }

    private void addPrivileges(SimpleResult simpleResult, Value value, String string, String string2, DbObject dbObject, int n, CompareLike compareLike, Column[] columnArray) {
        boolean bl;
        Value value2 = this.getString(string);
        Value value3 = this.getString(string2);
        Value value4 = this.getString(dbObject.getName());
        boolean bl2 = bl = dbObject.getType() == 2 && ((User)dbObject).isAdmin();
        if ((n & 1) != 0) {
            this.addPrivilege(simpleResult, value, value2, value3, value4, "SELECT", bl, compareLike, columnArray);
        }
        if ((n & 4) != 0) {
            this.addPrivilege(simpleResult, value, value2, value3, value4, "INSERT", bl, compareLike, columnArray);
        }
        if ((n & 8) != 0) {
            this.addPrivilege(simpleResult, value, value2, value3, value4, "UPDATE", bl, compareLike, columnArray);
        }
        if ((n & 2) != 0) {
            this.addPrivilege(simpleResult, value, value2, value3, value4, "DELETE", bl, compareLike, columnArray);
        }
    }

    private void addPrivilege(SimpleResult simpleResult, Value value, Value value2, Value value3, Value value4, String string, boolean bl, CompareLike compareLike, Column[] columnArray) {
        if (columnArray == null) {
            simpleResult.addRow(value, value2, value3, ValueNull.INSTANCE, value4, this.getString(string), bl ? YES : NO);
        } else {
            for (Column column : columnArray) {
                String string2 = column.getName();
                if (compareLike != null && !compareLike.test(string2)) continue;
                simpleResult.addRow(value, value2, value3, this.getString(string2), ValueNull.INSTANCE, value4, this.getString(string), bl ? YES : NO);
            }
        }
    }

    @Override
    public ResultInterface getBestRowIdentifier(String string, String string2, String string3, int n, boolean bl) {
        if (string3 == null) {
            throw DbException.getInvalidValueException("table", null);
        }
        this.checkClosed();
        SimpleResult simpleResult = new SimpleResult();
        simpleResult.addColumn("SCOPE", TypeInfo.TYPE_SMALLINT);
        simpleResult.addColumn("COLUMN_NAME", TypeInfo.TYPE_VARCHAR);
        simpleResult.addColumn("DATA_TYPE", TypeInfo.TYPE_INTEGER);
        simpleResult.addColumn("TYPE_NAME", TypeInfo.TYPE_VARCHAR);
        simpleResult.addColumn("COLUMN_SIZE", TypeInfo.TYPE_INTEGER);
        simpleResult.addColumn("BUFFER_LENGTH", TypeInfo.TYPE_INTEGER);
        simpleResult.addColumn("DECIMAL_DIGITS", TypeInfo.TYPE_SMALLINT);
        simpleResult.addColumn("PSEUDO_COLUMN", TypeInfo.TYPE_SMALLINT);
        if (!this.checkCatalogName(string)) {
            return simpleResult;
        }
        for (Schema schema : this.getSchemas(string2)) {
            ArrayList<Constraint> arrayList;
            Table table = schema.findTableOrView(this.session, string3);
            if (table == null || table.isHidden() || (arrayList = table.getConstraints()) == null) continue;
            for (Constraint constraint : arrayList) {
                if (constraint.getConstraintType() != Constraint.Type.PRIMARY_KEY) continue;
                for (IndexColumn indexColumn : ((ConstraintUnique)constraint).getColumns()) {
                    Column column = indexColumn.column;
                    TypeInfo typeInfo = column.getType();
                    DataType dataType = DataType.getDataType(typeInfo.getValueType());
                    simpleResult.addRow(BEST_ROW_SESSION, this.getString(column.getName()), ValueInteger.get(DataType.convertTypeToSQLType(typeInfo)), this.getDataTypeName(typeInfo), ValueInteger.get(MathUtils.convertLongToInt(typeInfo.getPrecision())), ValueNull.INSTANCE, dataType.supportsScale ? ValueSmallint.get(MathUtils.convertIntToShort(typeInfo.getScale())) : ValueNull.INSTANCE, BEST_ROW_NOT_PSEUDO);
                }
            }
        }
        return simpleResult;
    }

    private Value getDataTypeName(TypeInfo typeInfo) {
        return this.getString(typeInfo.getDeclaredTypeName());
    }

    @Override
    public ResultInterface getPrimaryKeys(String string, String string2, String string3) {
        if (string3 == null) {
            throw DbException.getInvalidValueException("table", null);
        }
        this.checkClosed();
        SimpleResult simpleResult = new SimpleResult();
        simpleResult.addColumn("TABLE_CAT", TypeInfo.TYPE_VARCHAR);
        simpleResult.addColumn("TABLE_SCHEM", TypeInfo.TYPE_VARCHAR);
        simpleResult.addColumn("TABLE_NAME", TypeInfo.TYPE_VARCHAR);
        simpleResult.addColumn("COLUMN_NAME", TypeInfo.TYPE_VARCHAR);
        simpleResult.addColumn("KEY_SEQ", TypeInfo.TYPE_SMALLINT);
        simpleResult.addColumn("PK_NAME", TypeInfo.TYPE_VARCHAR);
        if (!this.checkCatalogName(string)) {
            return simpleResult;
        }
        Database database = this.session.getDatabase();
        Value value = this.getString(database.getShortName());
        for (Schema schema : this.getSchemas(string2)) {
            ArrayList<Constraint> arrayList;
            Table table = schema.findTableOrView(this.session, string3);
            if (table == null || table.isHidden() || (arrayList = table.getConstraints()) == null) continue;
            for (Constraint constraint : arrayList) {
                if (constraint.getConstraintType() != Constraint.Type.PRIMARY_KEY) continue;
                Value value2 = this.getString(schema.getName());
                Value value3 = this.getString(table.getName());
                Value value4 = this.getString(constraint.getName());
                IndexColumn[] indexColumnArray = ((ConstraintUnique)constraint).getColumns();
                int n = 0;
                int n2 = indexColumnArray.length;
                while (n < n2) {
                    simpleResult.addRow(value, value2, value3, this.getString(indexColumnArray[n].column.getName()), ValueSmallint.get((short)(++n)), value4);
                }
            }
        }
        simpleResult.sortRows(new SortOrder(this.session, new int[]{3}));
        return simpleResult;
    }

    @Override
    public ResultInterface getImportedKeys(String string, String string2, String string3) {
        if (string3 == null) {
            throw DbException.getInvalidValueException("table", null);
        }
        SimpleResult simpleResult = this.initCrossReferenceResult();
        if (!this.checkCatalogName(string)) {
            return simpleResult;
        }
        Database database = this.session.getDatabase();
        Value value = this.getString(database.getShortName());
        for (Schema schema : this.getSchemas(string2)) {
            ArrayList<Constraint> arrayList;
            Table table = schema.findTableOrView(this.session, string3);
            if (table == null || table.isHidden() || (arrayList = table.getConstraints()) == null) continue;
            for (Constraint constraint : arrayList) {
                ConstraintReferential constraintReferential;
                Table table2;
                if (constraint.getConstraintType() != Constraint.Type.REFERENTIAL || (table2 = (constraintReferential = (ConstraintReferential)constraint).getTable()) != table) continue;
                Table table3 = constraintReferential.getRefTable();
                this.addCrossReferenceResult(simpleResult, value, table3.getSchema().getName(), table3, table2.getSchema().getName(), table2, constraintReferential);
            }
        }
        simpleResult.sortRows(new SortOrder(this.session, new int[]{1, 2, 8}));
        return simpleResult;
    }

    @Override
    public ResultInterface getExportedKeys(String string, String string2, String string3) {
        if (string3 == null) {
            throw DbException.getInvalidValueException("table", null);
        }
        SimpleResult simpleResult = this.initCrossReferenceResult();
        if (!this.checkCatalogName(string)) {
            return simpleResult;
        }
        Database database = this.session.getDatabase();
        Value value = this.getString(database.getShortName());
        for (Schema schema : this.getSchemas(string2)) {
            ArrayList<Constraint> arrayList;
            Table table = schema.findTableOrView(this.session, string3);
            if (table == null || table.isHidden() || (arrayList = table.getConstraints()) == null) continue;
            for (Constraint constraint : arrayList) {
                ConstraintReferential constraintReferential;
                Table table2;
                if (constraint.getConstraintType() != Constraint.Type.REFERENTIAL || (table2 = (constraintReferential = (ConstraintReferential)constraint).getRefTable()) != table) continue;
                Table table3 = constraintReferential.getTable();
                this.addCrossReferenceResult(simpleResult, value, table2.getSchema().getName(), table2, table3.getSchema().getName(), table3, constraintReferential);
            }
        }
        simpleResult.sortRows(new SortOrder(this.session, new int[]{5, 6, 8}));
        return simpleResult;
    }

    @Override
    public ResultInterface getCrossReference(String string, String string2, String string3, String string4, String string5, String string6) {
        if (string3 == null) {
            throw DbException.getInvalidValueException("primaryTable", null);
        }
        if (string6 == null) {
            throw DbException.getInvalidValueException("foreignTable", null);
        }
        SimpleResult simpleResult = this.initCrossReferenceResult();
        if (!this.checkCatalogName(string) || !this.checkCatalogName(string4)) {
            return simpleResult;
        }
        Database database = this.session.getDatabase();
        Value value = this.getString(database.getShortName());
        for (Schema schema : this.getSchemas(string5)) {
            ArrayList<Constraint> arrayList;
            Table table = schema.findTableOrView(this.session, string6);
            if (table == null || table.isHidden() || (arrayList = table.getConstraints()) == null) continue;
            for (Constraint constraint : arrayList) {
                Schema schema2;
                Table table2;
                ConstraintReferential constraintReferential;
                Table table3;
                if (constraint.getConstraintType() != Constraint.Type.REFERENTIAL || (table3 = (constraintReferential = (ConstraintReferential)constraint).getTable()) != table || !database.equalsIdentifiers((table2 = constraintReferential.getRefTable()).getName(), string3) || !this.checkSchema(string2, schema2 = table2.getSchema())) continue;
                this.addCrossReferenceResult(simpleResult, value, schema2.getName(), table2, table3.getSchema().getName(), table3, constraintReferential);
            }
        }
        simpleResult.sortRows(new SortOrder(this.session, new int[]{5, 6, 8}));
        return simpleResult;
    }

    private SimpleResult initCrossReferenceResult() {
        this.checkClosed();
        SimpleResult simpleResult = new SimpleResult();
        simpleResult.addColumn("PKTABLE_CAT", TypeInfo.TYPE_VARCHAR);
        simpleResult.addColumn("PKTABLE_SCHEM", TypeInfo.TYPE_VARCHAR);
        simpleResult.addColumn("PKTABLE_NAME", TypeInfo.TYPE_VARCHAR);
        simpleResult.addColumn("PKCOLUMN_NAME", TypeInfo.TYPE_VARCHAR);
        simpleResult.addColumn("FKTABLE_CAT", TypeInfo.TYPE_VARCHAR);
        simpleResult.addColumn("FKTABLE_SCHEM", TypeInfo.TYPE_VARCHAR);
        simpleResult.addColumn("FKTABLE_NAME", TypeInfo.TYPE_VARCHAR);
        simpleResult.addColumn("FKCOLUMN_NAME", TypeInfo.TYPE_VARCHAR);
        simpleResult.addColumn("KEY_SEQ", TypeInfo.TYPE_SMALLINT);
        simpleResult.addColumn("UPDATE_RULE", TypeInfo.TYPE_SMALLINT);
        simpleResult.addColumn("DELETE_RULE", TypeInfo.TYPE_SMALLINT);
        simpleResult.addColumn("FK_NAME", TypeInfo.TYPE_VARCHAR);
        simpleResult.addColumn("PK_NAME", TypeInfo.TYPE_VARCHAR);
        simpleResult.addColumn("DEFERRABILITY", TypeInfo.TYPE_SMALLINT);
        return simpleResult;
    }

    private void addCrossReferenceResult(SimpleResult simpleResult, Value value, String string, Table table, String string2, Table table2, ConstraintReferential constraintReferential) {
        Value value2 = this.getString(string);
        Value value3 = this.getString(table.getName());
        Value value4 = this.getString(string2);
        Value value5 = this.getString(table2.getName());
        IndexColumn[] indexColumnArray = constraintReferential.getRefColumns();
        IndexColumn[] indexColumnArray2 = constraintReferential.getColumns();
        ValueSmallint valueSmallint = DatabaseMetaLocal.getRefAction(constraintReferential.getUpdateAction());
        ValueSmallint valueSmallint2 = DatabaseMetaLocal.getRefAction(constraintReferential.getDeleteAction());
        Value value6 = this.getString(constraintReferential.getName());
        Value value7 = this.getString(constraintReferential.getReferencedConstraint().getName());
        int n = indexColumnArray2.length;
        for (int i = 0; i < n; ++i) {
            simpleResult.addRow(value, value2, value3, this.getString(indexColumnArray[i].column.getName()), value, value4, value5, this.getString(indexColumnArray2[i].column.getName()), ValueSmallint.get((short)(i + 1)), valueSmallint, valueSmallint2, value6, value7, IMPORTED_KEY_NOT_DEFERRABLE);
        }
    }

    private static ValueSmallint getRefAction(ConstraintActionType constraintActionType) {
        switch (constraintActionType) {
            case CASCADE: {
                return IMPORTED_KEY_CASCADE;
            }
            case RESTRICT: {
                return IMPORTED_KEY_RESTRICT;
            }
            case SET_DEFAULT: {
                return IMPORTED_KEY_DEFAULT;
            }
            case SET_NULL: {
                return IMPORTED_KEY_SET_NULL;
            }
        }
        throw DbException.getInternalError("action=" + (Object)((Object)constraintActionType));
    }

    @Override
    public ResultInterface getTypeInfo() {
        this.checkClosed();
        SimpleResult simpleResult = new SimpleResult();
        simpleResult.addColumn("TYPE_NAME", TypeInfo.TYPE_VARCHAR);
        simpleResult.addColumn("DATA_TYPE", TypeInfo.TYPE_INTEGER);
        simpleResult.addColumn("PRECISION", TypeInfo.TYPE_INTEGER);
        simpleResult.addColumn("LITERAL_PREFIX", TypeInfo.TYPE_VARCHAR);
        simpleResult.addColumn("LITERAL_SUFFIX", TypeInfo.TYPE_VARCHAR);
        simpleResult.addColumn("CREATE_PARAMS", TypeInfo.TYPE_VARCHAR);
        simpleResult.addColumn("NULLABLE", TypeInfo.TYPE_SMALLINT);
        simpleResult.addColumn("CASE_SENSITIVE", TypeInfo.TYPE_BOOLEAN);
        simpleResult.addColumn("SEARCHABLE", TypeInfo.TYPE_SMALLINT);
        simpleResult.addColumn("UNSIGNED_ATTRIBUTE", TypeInfo.TYPE_BOOLEAN);
        simpleResult.addColumn("FIXED_PREC_SCALE", TypeInfo.TYPE_BOOLEAN);
        simpleResult.addColumn("AUTO_INCREMENT", TypeInfo.TYPE_BOOLEAN);
        simpleResult.addColumn("LOCAL_TYPE_NAME", TypeInfo.TYPE_VARCHAR);
        simpleResult.addColumn("MINIMUM_SCALE", TypeInfo.TYPE_SMALLINT);
        simpleResult.addColumn("MAXIMUM_SCALE", TypeInfo.TYPE_SMALLINT);
        simpleResult.addColumn("SQL_DATA_TYPE", TypeInfo.TYPE_INTEGER);
        simpleResult.addColumn("SQL_DATETIME_SUB", TypeInfo.TYPE_INTEGER);
        simpleResult.addColumn("NUM_PREC_RADIX", TypeInfo.TYPE_INTEGER);
        int n = 42;
        for (int i = 1; i < n; ++i) {
            DataType dataType = DataType.getDataType(i);
            Value value = this.getString(Value.getTypeName(dataType.type));
            simpleResult.addRow(value, ValueInteger.get(dataType.sqlType), ValueInteger.get(MathUtils.convertLongToInt(dataType.maxPrecision)), this.getString(dataType.prefix), this.getString(dataType.suffix), this.getString(dataType.params), TYPE_NULLABLE, ValueBoolean.get(dataType.caseSensitive), TYPE_SEARCHABLE, ValueBoolean.FALSE, ValueBoolean.get(dataType.type == 13), ValueBoolean.get(DataType.isNumericType(i)), value, ValueSmallint.get(MathUtils.convertIntToShort(dataType.minScale)), ValueSmallint.get(MathUtils.convertIntToShort(dataType.maxScale)), ValueNull.INSTANCE, ValueNull.INSTANCE, DatabaseMetaLocal.getRadix(dataType.type, false));
        }
        simpleResult.sortRows(new SortOrder(this.session, new int[]{1}));
        return simpleResult;
    }

    private static Value getRadix(int n, boolean bl) {
        if (DataType.isNumericType(n)) {
            int n2 = n == 13 || n == 16 ? 10 : 2;
            return bl ? ValueSmallint.get((short)n2) : ValueInteger.get(n2);
        }
        return ValueNull.INSTANCE;
    }

    @Override
    public ResultInterface getIndexInfo(String string, String string2, String string3, boolean bl, boolean bl2) {
        if (string3 == null) {
            throw DbException.getInvalidValueException("table", null);
        }
        this.checkClosed();
        SimpleResult simpleResult = new SimpleResult();
        simpleResult.addColumn("TABLE_CAT", TypeInfo.TYPE_VARCHAR);
        simpleResult.addColumn("TABLE_SCHEM", TypeInfo.TYPE_VARCHAR);
        simpleResult.addColumn("TABLE_NAME", TypeInfo.TYPE_VARCHAR);
        simpleResult.addColumn("NON_UNIQUE", TypeInfo.TYPE_BOOLEAN);
        simpleResult.addColumn("INDEX_QUALIFIER", TypeInfo.TYPE_VARCHAR);
        simpleResult.addColumn("INDEX_NAME", TypeInfo.TYPE_VARCHAR);
        simpleResult.addColumn("TYPE", TypeInfo.TYPE_SMALLINT);
        simpleResult.addColumn("ORDINAL_POSITION", TypeInfo.TYPE_SMALLINT);
        simpleResult.addColumn("COLUMN_NAME", TypeInfo.TYPE_VARCHAR);
        simpleResult.addColumn("ASC_OR_DESC", TypeInfo.TYPE_VARCHAR);
        simpleResult.addColumn("CARDINALITY", TypeInfo.TYPE_BIGINT);
        simpleResult.addColumn("PAGES", TypeInfo.TYPE_BIGINT);
        simpleResult.addColumn("FILTER_CONDITION", TypeInfo.TYPE_VARCHAR);
        if (!this.checkCatalogName(string)) {
            return simpleResult;
        }
        Database database = this.session.getDatabase();
        Value value = this.getString(database.getShortName());
        for (Schema schema : this.getSchemas(string2)) {
            Table table = schema.findTableOrView(this.session, string3);
            if (table == null || table.isHidden()) continue;
            this.getIndexInfo(value, this.getString(schema.getName()), table, bl, bl2, simpleResult, database);
        }
        simpleResult.sortRows(new SortOrder(this.session, new int[]{3, 6, 5, 7}));
        return simpleResult;
    }

    private void getIndexInfo(Value value, Value value2, Table table, boolean bl, boolean bl2, SimpleResult simpleResult, Database database) {
        ArrayList<Index> arrayList = table.getIndexes();
        if (arrayList != null) {
            block0: for (Index index : arrayList) {
                if (index.getCreateSQL() == null) continue;
                int n = index.getUniqueColumnCount();
                if (bl && n == 0) continue;
                Value value3 = this.getString(table.getName());
                Value value4 = this.getString(index.getName());
                IndexColumn[] indexColumnArray = index.getIndexColumns();
                ValueSmallint valueSmallint = index.getIndexType().isHash() ? TABLE_INDEX_HASHED : TABLE_INDEX_OTHER;
                int n2 = indexColumnArray.length;
                for (int i = 0; i < n2; ++i) {
                    boolean bl3;
                    IndexColumn indexColumn = indexColumnArray[i];
                    boolean bl4 = bl3 = i >= n;
                    if (bl && bl3) continue block0;
                    simpleResult.addRow(value, value2, value3, ValueBoolean.get(bl3), value, value4, valueSmallint, ValueSmallint.get((short)(i + 1)), this.getString(indexColumn.column.getName()), this.getString((indexColumn.sortType & 1) != 0 ? "D" : "A"), ValueBigint.get(bl2 ? index.getRowCountApproximation(this.session) : index.getRowCount(this.session)), ValueBigint.get(index.getDiskSpaceUsed() / (long)database.getPageSize()), ValueNull.INSTANCE);
                }
            }
        }
    }

    @Override
    public ResultInterface getSchemas(String string, String string2) {
        this.checkClosed();
        SimpleResult simpleResult = new SimpleResult();
        simpleResult.addColumn("TABLE_SCHEM", TypeInfo.TYPE_VARCHAR);
        simpleResult.addColumn("TABLE_CATALOG", TypeInfo.TYPE_VARCHAR);
        if (!this.checkCatalogName(string)) {
            return simpleResult;
        }
        CompareLike compareLike = this.getLike(string2);
        Collection<Schema> collection = this.session.getDatabase().getAllSchemas();
        Value value = this.getString(this.session.getDatabase().getShortName());
        if (compareLike == null) {
            for (Schema schema : collection) {
                simpleResult.addRow(this.getString(schema.getName()), value);
            }
        } else {
            for (Schema schema : collection) {
                String string3 = schema.getName();
                if (!compareLike.test(string3)) continue;
                simpleResult.addRow(this.getString(schema.getName()), value);
            }
        }
        simpleResult.sortRows(new SortOrder(this.session, new int[]{0}));
        return simpleResult;
    }

    @Override
    public ResultInterface getPseudoColumns(String string, String string2, String string3, String string4) {
        SimpleResult simpleResult = this.getPseudoColumnsResult();
        if (!this.checkCatalogName(string)) {
            return simpleResult;
        }
        Database database = this.session.getDatabase();
        Value value = this.getString(database.getShortName());
        CompareLike compareLike = this.getLike(string4);
        for (Schema schema : this.getSchemasForPattern(string2)) {
            Value value2 = this.getString(schema.getName());
            for (SchemaObject schemaObject : this.getTablesForPattern(schema, string3)) {
                SchemaObject schemaObject2;
                Value value3 = this.getString(schemaObject.getName());
                if (schemaObject instanceof Table) {
                    schemaObject2 = (Table)schemaObject;
                    if (((Table)schemaObject2).isHidden()) continue;
                    this.getPseudoColumnsAdd(simpleResult, value, value2, value3, (Table)schemaObject2, compareLike);
                    continue;
                }
                schemaObject2 = (TableSynonym)schemaObject;
                Table table = ((TableSynonym)schemaObject2).getSynonymFor();
                this.getPseudoColumnsAdd(simpleResult, value, value2, value3, table, compareLike);
            }
        }
        simpleResult.sortRows(new SortOrder(this.session, new int[]{1, 2, 3}));
        return simpleResult;
    }

    private void getPseudoColumnsAdd(SimpleResult simpleResult, Value value, Value value2, Value value3, Table table, CompareLike compareLike) {
        Column column = table.getRowIdColumn();
        if (column != null) {
            this.getPseudoColumnsAdd(simpleResult, value, value2, value3, compareLike, column);
        }
        for (Column column2 : table.getColumns()) {
            if (column2.getVisible()) continue;
            this.getPseudoColumnsAdd(simpleResult, value, value2, value3, compareLike, column2);
        }
    }

    private void getPseudoColumnsAdd(SimpleResult simpleResult, Value value, Value value2, Value value3, CompareLike compareLike, Column column) {
        String string = column.getName();
        if (compareLike != null && !compareLike.test(string)) {
            return;
        }
        TypeInfo typeInfo = column.getType();
        ValueInteger valueInteger = ValueInteger.get(MathUtils.convertLongToInt(typeInfo.getPrecision()));
        simpleResult.addRow(value, value2, value3, this.getString(string), ValueInteger.get(DataType.convertTypeToSQLType(typeInfo)), valueInteger, ValueInteger.get(typeInfo.getScale()), DatabaseMetaLocal.getRadix(typeInfo.getValueType(), false), NO_USAGE_RESTRICTIONS, this.getString(column.getComment()), valueInteger, column.isNullable() ? YES : NO);
    }

    @Override
    void checkClosed() {
        if (this.session.isClosed()) {
            throw DbException.get(90121);
        }
    }

    Value getString(String string) {
        return string != null ? ValueVarchar.get(string, this.session) : ValueNull.INSTANCE;
    }

    private boolean checkCatalogName(String string) {
        if (string != null && !string.isEmpty()) {
            Database database = this.session.getDatabase();
            return database.equalsIdentifiers(string, database.getShortName());
        }
        return true;
    }

    private Collection<Schema> getSchemas(String string) {
        Database database = this.session.getDatabase();
        if (string == null) {
            return database.getAllSchemas();
        }
        if (string.isEmpty()) {
            return Collections.singleton(database.getMainSchema());
        }
        Schema schema = database.findSchema(string);
        if (schema != null) {
            return Collections.singleton(schema);
        }
        return Collections.emptySet();
    }

    private Collection<Schema> getSchemasForPattern(String string) {
        Database database = this.session.getDatabase();
        if (string == null) {
            return database.getAllSchemas();
        }
        if (string.isEmpty()) {
            return Collections.singleton(database.getMainSchema());
        }
        ArrayList<Schema> arrayList = Utils.newSmallArrayList();
        CompareLike compareLike = this.getLike(string);
        for (Schema schema : database.getAllSchemas()) {
            if (!compareLike.test(schema.getName())) continue;
            arrayList.add(schema);
        }
        return arrayList;
    }

    private Collection<? extends SchemaObject> getTablesForPattern(Schema schema, String string) {
        Collection<Table> collection = schema.getAllTablesAndViews(this.session);
        Collection<TableSynonym> collection2 = schema.getAllSynonyms();
        if (string == null) {
            if (collection.isEmpty()) {
                return collection2;
            }
            if (collection2.isEmpty()) {
                return collection;
            }
            ArrayList<SchemaObject> arrayList = new ArrayList<SchemaObject>(collection.size() + collection2.size());
            arrayList.addAll(collection);
            arrayList.addAll(collection2);
            return arrayList;
        }
        if (collection.isEmpty() && collection2.isEmpty()) {
            return Collections.emptySet();
        }
        ArrayList arrayList = Utils.newSmallArrayList();
        CompareLike compareLike = this.getLike(string);
        for (Table schemaObject : collection) {
            if (!compareLike.test(schemaObject.getName())) continue;
            arrayList.add(schemaObject);
        }
        for (TableSynonym tableSynonym : collection2) {
            if (!compareLike.test(tableSynonym.getName())) continue;
            arrayList.add(tableSynonym);
        }
        return arrayList;
    }

    private boolean checkSchema(String string, Schema schema) {
        if (string == null) {
            return true;
        }
        if (string.isEmpty()) {
            return schema == this.session.getDatabase().getMainSchema();
        }
        return this.session.getDatabase().equalsIdentifiers(string, schema.getName());
    }

    private CompareLike getLike(String string) {
        if (string == null) {
            return null;
        }
        CompareLike compareLike = new CompareLike(this.session.getDatabase().getCompareMode(), "\\", null, false, false, null, null, CompareLike.LikeType.LIKE);
        compareLike.initPattern(string, Character.valueOf('\\'));
        return compareLike;
    }
}

