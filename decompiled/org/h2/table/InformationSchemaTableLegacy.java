/*
 * Decompiled with CFR 0.152.
 */
package org.h2.table;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import org.h2.command.Command;
import org.h2.command.ParserBase;
import org.h2.command.dml.Help;
import org.h2.constraint.Constraint;
import org.h2.constraint.ConstraintActionType;
import org.h2.constraint.ConstraintCheck;
import org.h2.constraint.ConstraintDomain;
import org.h2.constraint.ConstraintReferential;
import org.h2.constraint.ConstraintUnique;
import org.h2.engine.Constants;
import org.h2.engine.DbObject;
import org.h2.engine.QueryStatisticsData;
import org.h2.engine.Right;
import org.h2.engine.RightOwner;
import org.h2.engine.Role;
import org.h2.engine.SessionLocal;
import org.h2.engine.Setting;
import org.h2.engine.User;
import org.h2.expression.ExpressionVisitor;
import org.h2.expression.ValueExpression;
import org.h2.index.Index;
import org.h2.index.MetaIndex;
import org.h2.message.DbException;
import org.h2.result.Row;
import org.h2.result.SearchRow;
import org.h2.schema.Constant;
import org.h2.schema.Domain;
import org.h2.schema.FunctionAlias;
import org.h2.schema.Schema;
import org.h2.schema.SchemaObject;
import org.h2.schema.Sequence;
import org.h2.schema.TriggerObject;
import org.h2.schema.UserDefinedFunction;
import org.h2.store.InDoubtTransaction;
import org.h2.table.Column;
import org.h2.table.IndexColumn;
import org.h2.table.MetaTable;
import org.h2.table.Table;
import org.h2.table.TableSynonym;
import org.h2.table.TableType;
import org.h2.table.TableView;
import org.h2.tools.Csv;
import org.h2.util.DateTimeUtils;
import org.h2.util.MathUtils;
import org.h2.util.NetworkConnectionInfo;
import org.h2.util.StringUtils;
import org.h2.util.TimeZoneProvider;
import org.h2.util.Utils;
import org.h2.value.CompareMode;
import org.h2.value.DataType;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueBigint;
import org.h2.value.ValueBoolean;
import org.h2.value.ValueDouble;
import org.h2.value.ValueInteger;
import org.h2.value.ValueSmallint;
import org.h2.value.ValueToObjectConverter2;

public final class InformationSchemaTableLegacy
extends MetaTable {
    private static final String CHARACTER_SET_NAME = "Unicode";
    private static final int TABLES = 0;
    private static final int COLUMNS = 1;
    private static final int INDEXES = 2;
    private static final int TABLE_TYPES = 3;
    private static final int TYPE_INFO = 4;
    private static final int CATALOGS = 5;
    private static final int SETTINGS = 6;
    private static final int HELP = 7;
    private static final int SEQUENCES = 8;
    private static final int USERS = 9;
    private static final int ROLES = 10;
    private static final int RIGHTS = 11;
    private static final int FUNCTION_ALIASES = 12;
    private static final int SCHEMATA = 13;
    private static final int TABLE_PRIVILEGES = 14;
    private static final int COLUMN_PRIVILEGES = 15;
    private static final int COLLATIONS = 16;
    private static final int VIEWS = 17;
    private static final int IN_DOUBT = 18;
    private static final int CROSS_REFERENCES = 19;
    private static final int FUNCTION_COLUMNS = 20;
    private static final int CONSTRAINTS = 21;
    private static final int CONSTANTS = 22;
    private static final int DOMAINS = 23;
    private static final int TRIGGERS = 24;
    private static final int SESSIONS = 25;
    private static final int LOCKS = 26;
    private static final int SESSION_STATE = 27;
    private static final int QUERY_STATISTICS = 28;
    private static final int SYNONYMS = 29;
    private static final int TABLE_CONSTRAINTS = 30;
    private static final int DOMAIN_CONSTRAINTS = 31;
    private static final int KEY_COLUMN_USAGE = 32;
    private static final int REFERENTIAL_CONSTRAINTS = 33;
    private static final int CHECK_CONSTRAINTS = 34;
    private static final int CONSTRAINT_COLUMN_USAGE = 35;
    public static final int META_TABLE_TYPE_COUNT = 36;

    public InformationSchemaTableLegacy(Schema schema, int n, int n2) {
        super(schema, n, n2);
        Column[] columnArray;
        String string = null;
        switch (n2) {
            case 0: {
                this.setMetaTableName("TABLES");
                columnArray = new Column[]{this.column("TABLE_CATALOG"), this.column("TABLE_SCHEMA"), this.column("TABLE_NAME"), this.column("TABLE_TYPE"), this.column("STORAGE_TYPE"), this.column("SQL"), this.column("REMARKS"), this.column("LAST_MODIFICATION", TypeInfo.TYPE_BIGINT), this.column("ID", TypeInfo.TYPE_INTEGER), this.column("TYPE_NAME"), this.column("TABLE_CLASS"), this.column("ROW_COUNT_ESTIMATE", TypeInfo.TYPE_BIGINT)};
                string = "TABLE_NAME";
                break;
            }
            case 1: {
                this.setMetaTableName("COLUMNS");
                columnArray = new Column[]{this.column("TABLE_CATALOG"), this.column("TABLE_SCHEMA"), this.column("TABLE_NAME"), this.column("COLUMN_NAME"), this.column("ORDINAL_POSITION", TypeInfo.TYPE_INTEGER), this.column("COLUMN_DEFAULT"), this.column("IS_NULLABLE"), this.column("DATA_TYPE", TypeInfo.TYPE_INTEGER), this.column("CHARACTER_MAXIMUM_LENGTH", TypeInfo.TYPE_INTEGER), this.column("CHARACTER_OCTET_LENGTH", TypeInfo.TYPE_INTEGER), this.column("NUMERIC_PRECISION", TypeInfo.TYPE_INTEGER), this.column("NUMERIC_PRECISION_RADIX", TypeInfo.TYPE_INTEGER), this.column("NUMERIC_SCALE", TypeInfo.TYPE_INTEGER), this.column("DATETIME_PRECISION", TypeInfo.TYPE_INTEGER), this.column("INTERVAL_TYPE"), this.column("INTERVAL_PRECISION", TypeInfo.TYPE_INTEGER), this.column("CHARACTER_SET_NAME"), this.column("COLLATION_NAME"), this.column("DOMAIN_CATALOG"), this.column("DOMAIN_SCHEMA"), this.column("DOMAIN_NAME"), this.column("IS_GENERATED"), this.column("GENERATION_EXPRESSION"), this.column("TYPE_NAME"), this.column("NULLABLE", TypeInfo.TYPE_INTEGER), this.column("IS_COMPUTED", TypeInfo.TYPE_BOOLEAN), this.column("SELECTIVITY", TypeInfo.TYPE_INTEGER), this.column("SEQUENCE_NAME"), this.column("REMARKS"), this.column("SOURCE_DATA_TYPE", TypeInfo.TYPE_SMALLINT), this.column("COLUMN_TYPE"), this.column("COLUMN_ON_UPDATE"), this.column("IS_VISIBLE"), this.column("CHECK_CONSTRAINT")};
                string = "TABLE_NAME";
                break;
            }
            case 2: {
                this.setMetaTableName("INDEXES");
                columnArray = new Column[]{this.column("TABLE_CATALOG"), this.column("TABLE_SCHEMA"), this.column("TABLE_NAME"), this.column("NON_UNIQUE", TypeInfo.TYPE_BOOLEAN), this.column("INDEX_NAME"), this.column("ORDINAL_POSITION", TypeInfo.TYPE_SMALLINT), this.column("COLUMN_NAME"), this.column("CARDINALITY", TypeInfo.TYPE_INTEGER), this.column("PRIMARY_KEY", TypeInfo.TYPE_BOOLEAN), this.column("INDEX_TYPE_NAME"), this.column("IS_GENERATED", TypeInfo.TYPE_BOOLEAN), this.column("INDEX_TYPE", TypeInfo.TYPE_SMALLINT), this.column("ASC_OR_DESC"), this.column("PAGES", TypeInfo.TYPE_INTEGER), this.column("FILTER_CONDITION"), this.column("REMARKS"), this.column("SQL"), this.column("ID", TypeInfo.TYPE_INTEGER), this.column("SORT_TYPE", TypeInfo.TYPE_INTEGER), this.column("CONSTRAINT_NAME"), this.column("INDEX_CLASS")};
                string = "TABLE_NAME";
                break;
            }
            case 3: {
                this.setMetaTableName("TABLE_TYPES");
                columnArray = new Column[]{this.column("TYPE")};
                break;
            }
            case 4: {
                this.setMetaTableName("TYPE_INFO");
                columnArray = new Column[]{this.column("TYPE_NAME"), this.column("DATA_TYPE", TypeInfo.TYPE_INTEGER), this.column("PRECISION", TypeInfo.TYPE_INTEGER), this.column("PREFIX"), this.column("SUFFIX"), this.column("PARAMS"), this.column("AUTO_INCREMENT", TypeInfo.TYPE_BOOLEAN), this.column("MINIMUM_SCALE", TypeInfo.TYPE_SMALLINT), this.column("MAXIMUM_SCALE", TypeInfo.TYPE_SMALLINT), this.column("RADIX", TypeInfo.TYPE_INTEGER), this.column("POS", TypeInfo.TYPE_INTEGER), this.column("CASE_SENSITIVE", TypeInfo.TYPE_BOOLEAN), this.column("NULLABLE", TypeInfo.TYPE_SMALLINT), this.column("SEARCHABLE", TypeInfo.TYPE_SMALLINT)};
                break;
            }
            case 5: {
                this.setMetaTableName("CATALOGS");
                columnArray = new Column[]{this.column("CATALOG_NAME")};
                break;
            }
            case 6: {
                this.setMetaTableName("SETTINGS");
                columnArray = new Column[]{this.column("NAME"), this.column("VALUE")};
                break;
            }
            case 7: {
                this.setMetaTableName("HELP");
                columnArray = new Column[]{this.column("ID", TypeInfo.TYPE_INTEGER), this.column("SECTION"), this.column("TOPIC"), this.column("SYNTAX"), this.column("TEXT")};
                break;
            }
            case 8: {
                this.setMetaTableName("SEQUENCES");
                columnArray = new Column[]{this.column("SEQUENCE_CATALOG"), this.column("SEQUENCE_SCHEMA"), this.column("SEQUENCE_NAME"), this.column("DATA_TYPE"), this.column("NUMERIC_PRECISION", TypeInfo.TYPE_INTEGER), this.column("NUMERIC_PRECISION_RADIX", TypeInfo.TYPE_INTEGER), this.column("NUMERIC_SCALE", TypeInfo.TYPE_INTEGER), this.column("START_VALUE", TypeInfo.TYPE_BIGINT), this.column("MINIMUM_VALUE", TypeInfo.TYPE_BIGINT), this.column("MAXIMUM_VALUE", TypeInfo.TYPE_BIGINT), this.column("INCREMENT", TypeInfo.TYPE_BIGINT), this.column("CYCLE_OPTION"), this.column("DECLARED_DATA_TYPE"), this.column("DECLARED_NUMERIC_PRECISION", TypeInfo.TYPE_INTEGER), this.column("DECLARED_NUMERIC_SCALE", TypeInfo.TYPE_INTEGER), this.column("CURRENT_VALUE", TypeInfo.TYPE_BIGINT), this.column("IS_GENERATED", TypeInfo.TYPE_BOOLEAN), this.column("REMARKS"), this.column("CACHE", TypeInfo.TYPE_BIGINT), this.column("ID", TypeInfo.TYPE_INTEGER), this.column("MIN_VALUE", TypeInfo.TYPE_BIGINT), this.column("MAX_VALUE", TypeInfo.TYPE_BIGINT), this.column("IS_CYCLE", TypeInfo.TYPE_BOOLEAN)};
                break;
            }
            case 9: {
                this.setMetaTableName("USERS");
                columnArray = new Column[]{this.column("NAME"), this.column("ADMIN"), this.column("REMARKS"), this.column("ID", TypeInfo.TYPE_INTEGER)};
                break;
            }
            case 10: {
                this.setMetaTableName("ROLES");
                columnArray = new Column[]{this.column("NAME"), this.column("REMARKS"), this.column("ID", TypeInfo.TYPE_INTEGER)};
                break;
            }
            case 11: {
                this.setMetaTableName("RIGHTS");
                columnArray = new Column[]{this.column("GRANTEE"), this.column("GRANTEETYPE"), this.column("GRANTEDROLE"), this.column("RIGHTS"), this.column("TABLE_SCHEMA"), this.column("TABLE_NAME"), this.column("ID", TypeInfo.TYPE_INTEGER)};
                string = "TABLE_NAME";
                break;
            }
            case 12: {
                this.setMetaTableName("FUNCTION_ALIASES");
                columnArray = new Column[]{this.column("ALIAS_CATALOG"), this.column("ALIAS_SCHEMA"), this.column("ALIAS_NAME"), this.column("JAVA_CLASS"), this.column("JAVA_METHOD"), this.column("DATA_TYPE", TypeInfo.TYPE_INTEGER), this.column("TYPE_NAME"), this.column("COLUMN_COUNT", TypeInfo.TYPE_INTEGER), this.column("RETURNS_RESULT", TypeInfo.TYPE_SMALLINT), this.column("REMARKS"), this.column("ID", TypeInfo.TYPE_INTEGER), this.column("SOURCE")};
                break;
            }
            case 20: {
                this.setMetaTableName("FUNCTION_COLUMNS");
                columnArray = new Column[]{this.column("ALIAS_CATALOG"), this.column("ALIAS_SCHEMA"), this.column("ALIAS_NAME"), this.column("JAVA_CLASS"), this.column("JAVA_METHOD"), this.column("COLUMN_COUNT", TypeInfo.TYPE_INTEGER), this.column("POS", TypeInfo.TYPE_INTEGER), this.column("COLUMN_NAME"), this.column("DATA_TYPE", TypeInfo.TYPE_INTEGER), this.column("TYPE_NAME"), this.column("PRECISION", TypeInfo.TYPE_INTEGER), this.column("SCALE", TypeInfo.TYPE_SMALLINT), this.column("RADIX", TypeInfo.TYPE_SMALLINT), this.column("NULLABLE", TypeInfo.TYPE_SMALLINT), this.column("COLUMN_TYPE", TypeInfo.TYPE_SMALLINT), this.column("REMARKS"), this.column("COLUMN_DEFAULT")};
                break;
            }
            case 13: {
                this.setMetaTableName("SCHEMATA");
                columnArray = new Column[]{this.column("CATALOG_NAME"), this.column("SCHEMA_NAME"), this.column("SCHEMA_OWNER"), this.column("DEFAULT_CHARACTER_SET_NAME"), this.column("DEFAULT_COLLATION_NAME"), this.column("IS_DEFAULT", TypeInfo.TYPE_BOOLEAN), this.column("REMARKS"), this.column("ID", TypeInfo.TYPE_INTEGER)};
                break;
            }
            case 14: {
                this.setMetaTableName("TABLE_PRIVILEGES");
                columnArray = new Column[]{this.column("GRANTOR"), this.column("GRANTEE"), this.column("TABLE_CATALOG"), this.column("TABLE_SCHEMA"), this.column("TABLE_NAME"), this.column("PRIVILEGE_TYPE"), this.column("IS_GRANTABLE")};
                string = "TABLE_NAME";
                break;
            }
            case 15: {
                this.setMetaTableName("COLUMN_PRIVILEGES");
                columnArray = new Column[]{this.column("GRANTOR"), this.column("GRANTEE"), this.column("TABLE_CATALOG"), this.column("TABLE_SCHEMA"), this.column("TABLE_NAME"), this.column("COLUMN_NAME"), this.column("PRIVILEGE_TYPE"), this.column("IS_GRANTABLE")};
                string = "TABLE_NAME";
                break;
            }
            case 16: {
                this.setMetaTableName("COLLATIONS");
                columnArray = new Column[]{this.column("NAME"), this.column("KEY")};
                break;
            }
            case 17: {
                this.setMetaTableName("VIEWS");
                columnArray = new Column[]{this.column("TABLE_CATALOG"), this.column("TABLE_SCHEMA"), this.column("TABLE_NAME"), this.column("VIEW_DEFINITION"), this.column("CHECK_OPTION"), this.column("IS_UPDATABLE"), this.column("STATUS"), this.column("REMARKS"), this.column("ID", TypeInfo.TYPE_INTEGER)};
                string = "TABLE_NAME";
                break;
            }
            case 18: {
                this.setMetaTableName("IN_DOUBT");
                columnArray = new Column[]{this.column("TRANSACTION"), this.column("STATE")};
                break;
            }
            case 19: {
                this.setMetaTableName("CROSS_REFERENCES");
                columnArray = new Column[]{this.column("PKTABLE_CATALOG"), this.column("PKTABLE_SCHEMA"), this.column("PKTABLE_NAME"), this.column("PKCOLUMN_NAME"), this.column("FKTABLE_CATALOG"), this.column("FKTABLE_SCHEMA"), this.column("FKTABLE_NAME"), this.column("FKCOLUMN_NAME"), this.column("ORDINAL_POSITION", TypeInfo.TYPE_SMALLINT), this.column("UPDATE_RULE", TypeInfo.TYPE_SMALLINT), this.column("DELETE_RULE", TypeInfo.TYPE_SMALLINT), this.column("FK_NAME"), this.column("PK_NAME"), this.column("DEFERRABILITY", TypeInfo.TYPE_SMALLINT)};
                string = "PKTABLE_NAME";
                break;
            }
            case 21: {
                this.setMetaTableName("CONSTRAINTS");
                columnArray = new Column[]{this.column("CONSTRAINT_CATALOG"), this.column("CONSTRAINT_SCHEMA"), this.column("CONSTRAINT_NAME"), this.column("CONSTRAINT_TYPE"), this.column("TABLE_CATALOG"), this.column("TABLE_SCHEMA"), this.column("TABLE_NAME"), this.column("UNIQUE_INDEX_NAME"), this.column("CHECK_EXPRESSION"), this.column("COLUMN_LIST"), this.column("REMARKS"), this.column("SQL"), this.column("ID", TypeInfo.TYPE_INTEGER)};
                string = "TABLE_NAME";
                break;
            }
            case 22: {
                this.setMetaTableName("CONSTANTS");
                columnArray = new Column[]{this.column("CONSTANT_CATALOG"), this.column("CONSTANT_SCHEMA"), this.column("CONSTANT_NAME"), this.column("DATA_TYPE", TypeInfo.TYPE_INTEGER), this.column("REMARKS"), this.column("SQL"), this.column("ID", TypeInfo.TYPE_INTEGER)};
                break;
            }
            case 23: {
                this.setMetaTableName("DOMAINS");
                columnArray = new Column[]{this.column("DOMAIN_CATALOG"), this.column("DOMAIN_SCHEMA"), this.column("DOMAIN_NAME"), this.column("DOMAIN_DEFAULT"), this.column("DOMAIN_ON_UPDATE"), this.column("DATA_TYPE", TypeInfo.TYPE_INTEGER), this.column("PRECISION", TypeInfo.TYPE_INTEGER), this.column("SCALE", TypeInfo.TYPE_INTEGER), this.column("TYPE_NAME"), this.column("PARENT_DOMAIN_CATALOG"), this.column("PARENT_DOMAIN_SCHEMA"), this.column("PARENT_DOMAIN_NAME"), this.column("SELECTIVITY", TypeInfo.TYPE_INTEGER), this.column("REMARKS"), this.column("SQL"), this.column("ID", TypeInfo.TYPE_INTEGER), this.column("COLUMN_DEFAULT"), this.column("IS_NULLABLE"), this.column("CHECK_CONSTRAINT")};
                break;
            }
            case 24: {
                this.setMetaTableName("TRIGGERS");
                columnArray = new Column[]{this.column("TRIGGER_CATALOG"), this.column("TRIGGER_SCHEMA"), this.column("TRIGGER_NAME"), this.column("TRIGGER_TYPE"), this.column("TABLE_CATALOG"), this.column("TABLE_SCHEMA"), this.column("TABLE_NAME"), this.column("BEFORE", TypeInfo.TYPE_BOOLEAN), this.column("JAVA_CLASS"), this.column("QUEUE_SIZE", TypeInfo.TYPE_INTEGER), this.column("NO_WAIT", TypeInfo.TYPE_BOOLEAN), this.column("REMARKS"), this.column("SQL"), this.column("ID", TypeInfo.TYPE_INTEGER)};
                break;
            }
            case 25: {
                this.setMetaTableName("SESSIONS");
                columnArray = new Column[]{this.column("ID", TypeInfo.TYPE_INTEGER), this.column("USER_NAME"), this.column("SERVER"), this.column("CLIENT_ADDR"), this.column("CLIENT_INFO"), this.column("SESSION_START", TypeInfo.TYPE_TIMESTAMP_TZ), this.column("ISOLATION_LEVEL"), this.column("STATEMENT"), this.column("STATEMENT_START", TypeInfo.TYPE_TIMESTAMP_TZ), this.column("CONTAINS_UNCOMMITTED", TypeInfo.TYPE_BOOLEAN), this.column("STATE"), this.column("BLOCKER_ID", TypeInfo.TYPE_INTEGER), this.column("SLEEP_SINCE", TypeInfo.TYPE_TIMESTAMP_TZ)};
                break;
            }
            case 26: {
                this.setMetaTableName("LOCKS");
                columnArray = new Column[]{this.column("TABLE_SCHEMA"), this.column("TABLE_NAME"), this.column("SESSION_ID", TypeInfo.TYPE_INTEGER), this.column("LOCK_TYPE")};
                break;
            }
            case 27: {
                this.setMetaTableName("SESSION_STATE");
                columnArray = new Column[]{this.column("KEY"), this.column("SQL")};
                break;
            }
            case 28: {
                this.setMetaTableName("QUERY_STATISTICS");
                columnArray = new Column[]{this.column("SQL_STATEMENT"), this.column("EXECUTION_COUNT", TypeInfo.TYPE_INTEGER), this.column("MIN_EXECUTION_TIME", TypeInfo.TYPE_DOUBLE), this.column("MAX_EXECUTION_TIME", TypeInfo.TYPE_DOUBLE), this.column("CUMULATIVE_EXECUTION_TIME", TypeInfo.TYPE_DOUBLE), this.column("AVERAGE_EXECUTION_TIME", TypeInfo.TYPE_DOUBLE), this.column("STD_DEV_EXECUTION_TIME", TypeInfo.TYPE_DOUBLE), this.column("MIN_ROW_COUNT", TypeInfo.TYPE_BIGINT), this.column("MAX_ROW_COUNT", TypeInfo.TYPE_BIGINT), this.column("CUMULATIVE_ROW_COUNT", TypeInfo.TYPE_BIGINT), this.column("AVERAGE_ROW_COUNT", TypeInfo.TYPE_DOUBLE), this.column("STD_DEV_ROW_COUNT", TypeInfo.TYPE_DOUBLE)};
                break;
            }
            case 29: {
                this.setMetaTableName("SYNONYMS");
                columnArray = new Column[]{this.column("SYNONYM_CATALOG"), this.column("SYNONYM_SCHEMA"), this.column("SYNONYM_NAME"), this.column("SYNONYM_FOR"), this.column("SYNONYM_FOR_SCHEMA"), this.column("TYPE_NAME"), this.column("STATUS"), this.column("REMARKS"), this.column("ID", TypeInfo.TYPE_INTEGER)};
                string = "SYNONYM_NAME";
                break;
            }
            case 30: {
                this.setMetaTableName("TABLE_CONSTRAINTS");
                columnArray = new Column[]{this.column("CONSTRAINT_CATALOG"), this.column("CONSTRAINT_SCHEMA"), this.column("CONSTRAINT_NAME"), this.column("CONSTRAINT_TYPE"), this.column("TABLE_CATALOG"), this.column("TABLE_SCHEMA"), this.column("TABLE_NAME"), this.column("IS_DEFERRABLE"), this.column("INITIALLY_DEFERRED"), this.column("REMARKS"), this.column("SQL"), this.column("ID", TypeInfo.TYPE_INTEGER)};
                string = "TABLE_NAME";
                break;
            }
            case 31: {
                this.setMetaTableName("DOMAIN_CONSTRAINTS");
                columnArray = new Column[]{this.column("CONSTRAINT_CATALOG"), this.column("CONSTRAINT_SCHEMA"), this.column("CONSTRAINT_NAME"), this.column("DOMAIN_CATALOG"), this.column("DOMAIN_SCHEMA"), this.column("DOMAIN_NAME"), this.column("IS_DEFERRABLE"), this.column("INITIALLY_DEFERRED"), this.column("REMARKS"), this.column("SQL"), this.column("ID", TypeInfo.TYPE_INTEGER)};
                break;
            }
            case 32: {
                this.setMetaTableName("KEY_COLUMN_USAGE");
                columnArray = new Column[]{this.column("CONSTRAINT_CATALOG"), this.column("CONSTRAINT_SCHEMA"), this.column("CONSTRAINT_NAME"), this.column("TABLE_CATALOG"), this.column("TABLE_SCHEMA"), this.column("TABLE_NAME"), this.column("COLUMN_NAME"), this.column("ORDINAL_POSITION", TypeInfo.TYPE_INTEGER), this.column("POSITION_IN_UNIQUE_CONSTRAINT", TypeInfo.TYPE_INTEGER), this.column("INDEX_CATALOG"), this.column("INDEX_SCHEMA"), this.column("INDEX_NAME")};
                string = "TABLE_NAME";
                break;
            }
            case 33: {
                this.setMetaTableName("REFERENTIAL_CONSTRAINTS");
                columnArray = new Column[]{this.column("CONSTRAINT_CATALOG"), this.column("CONSTRAINT_SCHEMA"), this.column("CONSTRAINT_NAME"), this.column("UNIQUE_CONSTRAINT_CATALOG"), this.column("UNIQUE_CONSTRAINT_SCHEMA"), this.column("UNIQUE_CONSTRAINT_NAME"), this.column("MATCH_OPTION"), this.column("UPDATE_RULE"), this.column("DELETE_RULE")};
                break;
            }
            case 34: {
                this.setMetaTableName("CHECK_CONSTRAINTS");
                columnArray = new Column[]{this.column("CONSTRAINT_CATALOG"), this.column("CONSTRAINT_SCHEMA"), this.column("CONSTRAINT_NAME"), this.column("CHECK_CLAUSE")};
                break;
            }
            case 35: {
                this.setMetaTableName("CONSTRAINT_COLUMN_USAGE");
                columnArray = new Column[]{this.column("TABLE_CATALOG"), this.column("TABLE_SCHEMA"), this.column("TABLE_NAME"), this.column("COLUMN_NAME"), this.column("CONSTRAINT_CATALOG"), this.column("CONSTRAINT_SCHEMA"), this.column("CONSTRAINT_NAME")};
                string = "TABLE_NAME";
                break;
            }
            default: {
                throw DbException.getInternalError("type=" + n2);
            }
        }
        this.setColumns(columnArray);
        if (string == null) {
            this.indexColumn = -1;
            this.metaIndex = null;
        } else {
            this.indexColumn = this.getColumn(this.database.sysIdentifier(string)).getColumnId();
            IndexColumn[] indexColumnArray = IndexColumn.wrap(new Column[]{columnArray[this.indexColumn]});
            this.metaIndex = new MetaIndex(this, indexColumnArray, false);
        }
    }

    private static String replaceNullWithEmpty(String string) {
        return string == null ? "" : string;
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public ArrayList<Row> generateRows(SessionLocal sessionLocal, SearchRow searchRow, SearchRow searchRow2) {
        Value value = null;
        Value value2 = null;
        if (this.indexColumn >= 0) {
            if (searchRow != null) {
                value = searchRow.getValue(this.indexColumn);
            }
            if (searchRow2 != null) {
                value2 = searchRow2.getValue(this.indexColumn);
            }
        }
        ArrayList<Row> arrayList = Utils.newSmallArrayList();
        String string3 = this.database.getShortName();
        boolean bl = sessionLocal.getUser().isAdmin();
        switch (this.type) {
            case 0: {
                for (Table table : this.getAllTables(sessionLocal)) {
                    void string18;
                    String string4 = table.getName();
                    if (!this.checkIndex(sessionLocal, string4, value, value2) || this.hideTable(table, sessionLocal)) continue;
                    String typeInfo = table.isTemporary() ? (table.isGlobalTemporary() ? "GLOBAL TEMPORARY" : "LOCAL TEMPORARY") : (table.isPersistIndexes() ? "CACHED" : "MEMORY");
                    String string19 = table.getCreateSQL();
                    if (!bl && string19 != null && string19.contains("--hide--")) {
                        String functionAlias = "-";
                    }
                    this.add(sessionLocal, arrayList, string3, table.getSchema().getName(), string4, table.getTableType().toString(), typeInfo, string18, InformationSchemaTableLegacy.replaceNullWithEmpty(table.getComment()), ValueBigint.get(table.getMaxDataModificationId()), ValueInteger.get(table.getId()), null, table.getClass().getName(), ValueBigint.get(table.getRowCountApproximation(sessionLocal)));
                }
                break;
            }
            case 1: {
                ArrayList<Table> arrayList2;
                if (value != null && value.equals(value2)) {
                    String string7 = value.getString();
                    if (string7 == null) break;
                    arrayList2 = this.getTablesByName(sessionLocal, string7);
                } else {
                    arrayList2 = this.getAllTables(sessionLocal);
                }
                for (Table table : arrayList2) {
                    String dbObject = table.getName();
                    if (!this.checkIndex(sessionLocal, dbObject, value, value2) || this.hideTable(table, sessionLocal)) continue;
                    Column[] functionAlias = table.getColumns();
                    String string5 = this.database.getCompareMode().getName();
                    for (int i = 0; i < functionAlias.length; ++i) {
                        boolean bl2;
                        Column column = functionAlias[i];
                        Domain domain = column.getDomain();
                        TypeInfo typeInfo = column.getType();
                        ValueInteger valueInteger = ValueInteger.get(MathUtils.convertLongToInt(typeInfo.getPrecision()));
                        ValueInteger valueInteger2 = ValueInteger.get(typeInfo.getScale());
                        Sequence sequence2 = column.getSequence();
                        int n = typeInfo.getValueType();
                        switch (n) {
                            case 17: 
                            case 18: 
                            case 19: 
                            case 20: 
                            case 21: 
                            case 27: 
                            case 31: 
                            case 33: 
                            case 34: {
                                bl2 = true;
                                break;
                            }
                            default: {
                                bl2 = false;
                            }
                        }
                        boolean bl3 = column.isGenerated();
                        boolean bl4 = DataType.isIntervalType(n);
                        String string6 = column.getCreateSQLWithoutName();
                        this.add(sessionLocal, arrayList, string3, table.getSchema().getName(), dbObject, column.getName(), ValueInteger.get(i + 1), bl3 ? null : column.getDefaultSQL(), column.isNullable() ? "YES" : "NO", ValueInteger.get(DataType.convertTypeToSQLType(typeInfo)), valueInteger, valueInteger, valueInteger, ValueInteger.get(10), valueInteger2, bl2 ? valueInteger2 : null, bl4 ? string6.substring(9) : null, bl4 ? valueInteger : null, CHARACTER_SET_NAME, string5, domain != null ? string3 : null, domain != null ? domain.getSchema().getName() : null, domain != null ? domain.getName() : null, bl3 ? "ALWAYS" : "NEVER", bl3 ? column.getDefaultSQL() : null, this.identifier(bl4 ? "INTERVAL" : typeInfo.getDeclaredTypeName()), ValueInteger.get(column.isNullable() ? 1 : 0), ValueBoolean.get(bl3), ValueInteger.get(column.getSelectivity()), sequence2 == null ? null : sequence2.getName(), InformationSchemaTableLegacy.replaceNullWithEmpty(column.getComment()), null, string6, column.getOnUpdateSQL(), ValueBoolean.get(column.getVisible()), null);
                    }
                }
                break;
            }
            case 2: {
                ArrayList<Table> arrayList3;
                if (value != null && value.equals(value2)) {
                    String n = value.getString();
                    if (n == null) break;
                    arrayList3 = this.getTablesByName(sessionLocal, n);
                } else {
                    arrayList3 = this.getAllTables(sessionLocal);
                }
                for (Table inputStreamReader : arrayList3) {
                    String userDefinedFunction = inputStreamReader.getName();
                    if (!this.checkIndex(sessionLocal, userDefinedFunction, value, value2) || this.hideTable(inputStreamReader, sessionLocal)) continue;
                    ArrayList<Index> string25 = inputStreamReader.getIndexes();
                    ArrayList<Constraint> arrayList2 = inputStreamReader.getConstraints();
                    for (int i = 0; string25 != null && i < string25.size(); ++i) {
                        Index index = string25.get(i);
                        if (index.getCreateSQL() == null) continue;
                        String string7 = null;
                        for (int j = 0; arrayList2 != null && j < arrayList2.size(); ++j) {
                            Constraint constraint = arrayList2.get(j);
                            if (!constraint.usesIndex(index)) continue;
                            if (index.getIndexType().isPrimaryKey()) {
                                if (constraint.getConstraintType() != Constraint.Type.PRIMARY_KEY) continue;
                                string7 = constraint.getName();
                                continue;
                            }
                            string7 = constraint.getName();
                        }
                        IndexColumn[] indexColumnArray = index.getIndexColumns();
                        int n = index.getUniqueColumnCount();
                        String string8 = index.getClass().getName();
                        for (int j = 0; j < indexColumnArray.length; ++j) {
                            IndexColumn indexColumn = indexColumnArray[j];
                            Column column = indexColumn.column;
                            this.add(sessionLocal, arrayList, string3, inputStreamReader.getSchema().getName(), userDefinedFunction, ValueBoolean.get(j >= n), index.getName(), ValueSmallint.get((short)(j + 1)), column.getName(), ValueInteger.get(0), ValueBoolean.get(index.getIndexType().isPrimaryKey()), index.getIndexType().getSQL(false), ValueBoolean.get(index.getIndexType().getBelongsToConstraint()), ValueSmallint.get((short)3), (indexColumn.sortType & 1) != 0 ? "D" : "A", ValueInteger.get(0), "", InformationSchemaTableLegacy.replaceNullWithEmpty(index.getComment()), index.getCreateSQL(), ValueInteger.get(index.getId()), ValueInteger.get(indexColumn.sortType), string7, string8);
                        }
                    }
                }
                break;
            }
            case 3: {
                this.add(sessionLocal, arrayList, TableType.TABLE.toString());
                this.add(sessionLocal, arrayList, TableType.TABLE_LINK.toString());
                this.add(sessionLocal, arrayList, TableType.SYSTEM_TABLE.toString());
                this.add(sessionLocal, arrayList, TableType.VIEW.toString());
                this.add(sessionLocal, arrayList, TableType.EXTERNAL_TABLE_ENGINE.toString());
                break;
            }
            case 4: {
                int byArray = 42;
                for (int i = 1; i < byArray; ++i) {
                    DataType sequence3 = DataType.getDataType(i);
                    this.add(sessionLocal, arrayList, Value.getTypeName(sequence3.type), ValueInteger.get(sequence3.sqlType), ValueInteger.get(MathUtils.convertLongToInt(sequence3.maxPrecision)), sequence3.prefix, sequence3.suffix, sequence3.params, ValueBoolean.FALSE, ValueSmallint.get(MathUtils.convertIntToShort(sequence3.minScale)), ValueSmallint.get(MathUtils.convertIntToShort(sequence3.maxScale)), DataType.isNumericType(i) ? ValueInteger.get(10) : null, ValueInteger.get(sequence3.type), ValueBoolean.get(sequence3.caseSensitive), ValueSmallint.get((short)1), ValueSmallint.get((short)3));
                }
                break;
            }
            case 5: {
                this.add(sessionLocal, arrayList, string3);
                break;
            }
            case 6: {
                Object object = this.database.getAllSettings().iterator();
                while (object.hasNext()) {
                    Setting exception = object.next();
                    String user = exception.getStringValue();
                    if (user == null) {
                        user = Integer.toString(exception.getIntValue());
                    }
                    this.add(sessionLocal, arrayList, this.identifier(exception.getName()), user);
                }
                this.add(sessionLocal, arrayList, "info.BUILD_ID", "224");
                this.add(sessionLocal, arrayList, "info.VERSION_MAJOR", "2");
                this.add(sessionLocal, arrayList, "info.VERSION_MINOR", "2");
                this.add(sessionLocal, arrayList, "info.VERSION", Constants.FULL_VERSION);
                if (bl) {
                    String[] schemaObject = object = new String[]{"java.runtime.version", "java.vm.name", "java.vendor", "os.name", "os.arch", "os.version", "sun.os.patch.level", "file.separator", "path.separator", "line.separator", "user.country", "user.language", "user.variant", "file.encoding"};
                    int role = schemaObject.length;
                    for (int userDefinedFunction = 0; userDefinedFunction < role; ++userDefinedFunction) {
                        String string26 = schemaObject[userDefinedFunction];
                        this.add(sessionLocal, arrayList, "property." + string26, Utils.getProperty(string26, ""));
                    }
                }
                this.add(sessionLocal, arrayList, "DEFAULT_NULL_ORDERING", this.database.getDefaultNullOrdering().name());
                this.add(sessionLocal, arrayList, "EXCLUSIVE", this.database.getExclusiveSession() == null ? "FALSE" : "TRUE");
                this.add(sessionLocal, arrayList, "MODE", this.database.getMode().getName());
                this.add(sessionLocal, arrayList, "QUERY_TIMEOUT", Integer.toString(sessionLocal.getQueryTimeout()));
                this.add(sessionLocal, arrayList, "TIME ZONE", sessionLocal.currentTimeZone().getId());
                this.add(sessionLocal, arrayList, "TRUNCATE_LARGE_LENGTH", sessionLocal.isTruncateLargeLength() ? "TRUE" : "FALSE");
                this.add(sessionLocal, arrayList, "VARIABLE_BINARY", sessionLocal.isVariableBinary() ? "TRUE" : "FALSE");
                this.add(sessionLocal, arrayList, "OLD_INFORMATION_SCHEMA", sessionLocal.isOldInformationSchema() ? "TRUE" : "FALSE");
                object = sessionLocal.getNonKeywords();
                if (object != null) {
                    this.add(sessionLocal, arrayList, "NON_KEYWORDS", ParserBase.formatNonKeywords((BitSet)object));
                }
                this.add(sessionLocal, arrayList, "RETENTION_TIME", Integer.toString(this.database.getRetentionTime()));
                for (Map.Entry<String, String> string30 : this.database.getSettings().getSortedSettings()) {
                    this.add(sessionLocal, arrayList, string30.getKey(), string30.getValue());
                }
                this.database.getStore().getMvStore().populateInfo((string, string2) -> this.add(sessionLocal, arrayList, string, string2));
                break;
            }
            case 7: {
                String string16 = "/org/h2/res/help.csv";
                try {
                    byte[] right = Utils.getResource(string16);
                    InputStreamReader inputStreamReader = new InputStreamReader((InputStream)new ByteArrayInputStream(right), StandardCharsets.UTF_8);
                    Csv table = new Csv();
                    table.setLineCommentCharacter('#');
                    ResultSet typeInfo = table.read(inputStreamReader, null);
                    int n = typeInfo.getMetaData().getColumnCount() - 1;
                    String[] stringArray = new String[5];
                    int n2 = 0;
                    while (typeInfo.next()) {
                        for (int i = 0; i < n; ++i) {
                            String string9 = typeInfo.getString(1 + i);
                            switch (i) {
                                case 2: {
                                    string9 = Help.stripAnnotationsFromSyntax(string9);
                                    break;
                                }
                                case 3: {
                                    string9 = Help.processHelpText(string9);
                                }
                            }
                            stringArray[i] = string9.trim();
                        }
                        this.add(sessionLocal, arrayList, ValueInteger.get(n2), stringArray[0], stringArray[1], stringArray[2], stringArray[3]);
                        ++n2;
                    }
                    break;
                }
                catch (Exception schema) {
                    throw DbException.convert(schema);
                }
            }
            case 8: {
                for (SchemaObject schema : this.getAllSchemaObjects(3)) {
                    Sequence sequence3 = (Sequence)schema;
                    TypeInfo table = sequence3.getDataType();
                    String networkConnectionInfo = Value.getTypeName(table.getValueType());
                    ValueInteger valueInteger = ValueInteger.get(table.getScale());
                    this.add(sessionLocal, arrayList, string3, sequence3.getSchema().getName(), sequence3.getName(), networkConnectionInfo, ValueInteger.get(sequence3.getEffectivePrecision()), ValueInteger.get(10), valueInteger, ValueBigint.get(sequence3.getStartValue()), ValueBigint.get(sequence3.getMinValue()), ValueBigint.get(sequence3.getMaxValue()), ValueBigint.get(sequence3.getIncrement()), sequence3.getCycle().isCycle() ? "YES" : "NO", networkConnectionInfo, ValueInteger.get((int)table.getPrecision()), valueInteger, ValueBigint.get(sequence3.getCurrentValue()), ValueBoolean.get(sequence3.getBelongsToTable()), InformationSchemaTableLegacy.replaceNullWithEmpty(sequence3.getComment()), ValueBigint.get(sequence3.getCacheSize()), ValueInteger.get(sequence3.getId()), ValueBigint.get(sequence3.getMinValue()), ValueBigint.get(sequence3.getMaxValue()), ValueBoolean.get(sequence3.getCycle().isCycle()));
                }
                break;
            }
            case 9: {
                for (RightOwner rightOwner : this.database.getAllUsersAndRoles()) {
                    if (!(rightOwner instanceof User)) continue;
                    User schema = (User)rightOwner;
                    if (!bl && sessionLocal.getUser() != schema) continue;
                    this.add(sessionLocal, arrayList, this.identifier(schema.getName()), String.valueOf(schema.isAdmin()), InformationSchemaTableLegacy.replaceNullWithEmpty(schema.getComment()), ValueInteger.get(schema.getId()));
                }
                break;
            }
            case 10: {
                for (RightOwner right : this.database.getAllUsersAndRoles()) {
                    if (!(right instanceof Role)) continue;
                    Role dbObject = (Role)right;
                    if (!bl && !sessionLocal.getUser().isRoleGranted(dbObject)) continue;
                    this.add(sessionLocal, arrayList, this.identifier(dbObject.getName()), InformationSchemaTableLegacy.replaceNullWithEmpty(dbObject.getComment()), ValueInteger.get(dbObject.getId()));
                }
                break;
            }
            case 11: {
                if (!bl) break;
                for (Right right : this.database.getAllRights()) {
                    String string10;
                    Role dbObject = right.getGrantedRole();
                    DbObject locale = right.getGrantee();
                    String string11 = string10 = locale.getType() == 2 ? "USER" : "ROLE";
                    if (dbObject == null) {
                        String string12;
                        DbObject dbObject2 = right.getGrantedObject();
                        Schema schema = null;
                        DbObject dbObject3 = null;
                        if (dbObject2 != null) {
                            if (dbObject2 instanceof Schema) {
                                schema = (Schema)dbObject2;
                            } else if (dbObject2 instanceof Table) {
                                dbObject3 = (Table)dbObject2;
                                schema = ((SchemaObject)dbObject3).getSchema();
                            }
                        }
                        String string13 = dbObject3 != null ? dbObject3.getName() : "";
                        String string14 = string12 = schema != null ? schema.getName() : "";
                        if (!this.checkIndex(sessionLocal, string13, value, value2)) continue;
                        this.add(sessionLocal, arrayList, this.identifier(locale.getName()), string10, "", right.getRights(), string12, string13, ValueInteger.get(right.getId()));
                        continue;
                    }
                    this.add(sessionLocal, arrayList, this.identifier(locale.getName()), string10, this.identifier(dbObject.getName()), "", "", "", ValueInteger.get(right.getId()));
                }
                break;
            }
            case 12: {
                for (Schema schema : this.database.getAllSchemas()) {
                    for (UserDefinedFunction tableView : schema.getAllFunctionsAndAggregates()) {
                        if (tableView instanceof FunctionAlias) {
                            FunctionAlias.JavaMethod[] javaMethodArray;
                            FunctionAlias value3 = (FunctionAlias)tableView;
                            try {
                                javaMethodArray = value3.getJavaMethods();
                            }
                            catch (DbException dbException) {
                                continue;
                            }
                            for (FunctionAlias.JavaMethod javaMethod : javaMethodArray) {
                                TypeInfo typeInfo = javaMethod.getDataType();
                                if (typeInfo == null) {
                                    typeInfo = TypeInfo.TYPE_NULL;
                                }
                                this.add(sessionLocal, arrayList, string3, value3.getSchema().getName(), value3.getName(), value3.getJavaClassName(), value3.getJavaMethodName(), ValueInteger.get(DataType.convertTypeToSQLType(typeInfo)), typeInfo.getDeclaredTypeName(), ValueInteger.get(javaMethod.getParameterCount()), ValueSmallint.get(typeInfo.getValueType() == 0 ? (short)1 : 2), InformationSchemaTableLegacy.replaceNullWithEmpty(value3.getComment()), ValueInteger.get(value3.getId()), value3.getSource());
                            }
                            continue;
                        }
                        this.add(sessionLocal, arrayList, string3, this.database.getMainSchema().getName(), tableView.getName(), tableView.getJavaClassName(), "", ValueInteger.get(0), "NULL", ValueInteger.get(1), ValueSmallint.get((short)2), InformationSchemaTableLegacy.replaceNullWithEmpty(tableView.getComment()), ValueInteger.get(tableView.getId()), "");
                    }
                }
                break;
            }
            case 20: {
                for (Schema table : this.database.getAllSchemas()) {
                    for (UserDefinedFunction constraintReferential : table.getAllFunctionsAndAggregates()) {
                        FunctionAlias.JavaMethod[] javaMethodArray;
                        if (!(constraintReferential instanceof FunctionAlias)) continue;
                        FunctionAlias table2 = (FunctionAlias)constraintReferential;
                        try {
                            javaMethodArray = table2.getJavaMethods();
                        }
                        catch (DbException dbException) {
                            continue;
                        }
                        for (FunctionAlias.JavaMethod javaMethod : javaMethodArray) {
                            Object object;
                            TypeInfo typeInfo = javaMethod.getDataType();
                            if (typeInfo != null && typeInfo.getValueType() != 0) {
                                object = DataType.getDataType(typeInfo.getValueType());
                                this.add(sessionLocal, arrayList, string3, table2.getSchema().getName(), table2.getName(), table2.getJavaClassName(), table2.getJavaMethodName(), ValueInteger.get(javaMethod.getParameterCount()), ValueInteger.get(0), "P0", ValueInteger.get(DataType.convertTypeToSQLType(typeInfo)), typeInfo.getDeclaredTypeName(), ValueInteger.get(MathUtils.convertLongToInt(object.defaultPrecision)), ValueSmallint.get(MathUtils.convertIntToShort(object.defaultScale)), ValueSmallint.get((short)10), ValueSmallint.get((short)2), ValueSmallint.get((short)5), "", null);
                            }
                            object = javaMethod.getColumnClasses();
                            for (int i = 0; i < ((Class<?>[])object).length; ++i) {
                                if (javaMethod.hasConnectionParam() && i == 0) continue;
                                Class<?> clazz = object[i];
                                TypeInfo typeInfo2 = ValueToObjectConverter2.classToType(clazz);
                                DataType dataType = DataType.getDataType(typeInfo2.getValueType());
                                this.add(sessionLocal, arrayList, string3, table2.getSchema().getName(), table2.getName(), table2.getJavaClassName(), table2.getJavaMethodName(), ValueInteger.get(javaMethod.getParameterCount()), ValueInteger.get(i + (javaMethod.hasConnectionParam() ? 0 : 1)), "P" + (i + 1), ValueInteger.get(DataType.convertTypeToSQLType(typeInfo2)), typeInfo2.getDeclaredTypeName(), ValueInteger.get(MathUtils.convertLongToInt(dataType.defaultPrecision)), ValueSmallint.get(MathUtils.convertIntToShort(dataType.defaultScale)), ValueSmallint.get((short)10), ValueSmallint.get(clazz.isPrimitive() ? (short)0 : 1), ValueSmallint.get((short)1), "", null);
                            }
                        }
                    }
                }
                break;
            }
            case 13: {
                String string24 = this.database.getCompareMode().getName();
                for (Schema inDoubtTransaction : this.database.getAllSchemas()) {
                    this.add(sessionLocal, arrayList, string3, inDoubtTransaction.getName(), this.identifier(inDoubtTransaction.getOwner().getName()), CHARACTER_SET_NAME, string24, ValueBoolean.get(inDoubtTransaction.getId() == 0), InformationSchemaTableLegacy.replaceNullWithEmpty(inDoubtTransaction.getComment()), ValueInteger.get(inDoubtTransaction.getId()));
                }
                break;
            }
            case 14: {
                for (Right schemaObject : this.database.getAllRights()) {
                    String indexColumnArray;
                    Table type;
                    DbObject constraint = schemaObject.getGrantedObject();
                    if (!(constraint instanceof Table) || this.hideTable(type = (Table)constraint, sessionLocal) || !this.checkIndex(sessionLocal, indexColumnArray = type.getName(), value, value2)) continue;
                    this.addPrivileges(sessionLocal, arrayList, schemaObject.getGrantee(), string3, type, null, schemaObject.getRightMask());
                }
                break;
            }
            case 15: {
                for (Right schemaObject : this.database.getAllRights()) {
                    String constraintUnique;
                    Table valueExpression;
                    DbObject constraint = schemaObject.getGrantedObject();
                    if (!(constraint instanceof Table) || this.hideTable(valueExpression = (Table)constraint, sessionLocal) || !this.checkIndex(sessionLocal, constraintUnique = valueExpression.getName(), value, value2)) continue;
                    DbObject dbObject = schemaObject.getGrantee();
                    int n = schemaObject.getRightMask();
                    for (Column column : valueExpression.getColumns()) {
                        this.addPrivileges(sessionLocal, arrayList, dbObject, string3, valueExpression, column.getName(), n);
                    }
                }
                break;
            }
            case 16: {
                for (Locale domain2 : CompareMode.getCollationLocales(false)) {
                    this.add(sessionLocal, arrayList, CompareMode.getName(domain2), domain2.toString());
                }
                break;
            }
            case 17: {
                for (Table schemaObject : this.getAllTables(sessionLocal)) {
                    String domain;
                    if (schemaObject.getTableType() != TableType.VIEW || !this.checkIndex(sessionLocal, domain = schemaObject.getName(), value, value2)) continue;
                    TableView table = (TableView)schemaObject;
                    this.add(sessionLocal, arrayList, string3, schemaObject.getSchema().getName(), domain, schemaObject.getCreateSQL(), "NONE", "NO", table.isInvalid() ? "INVALID" : "VALID", InformationSchemaTableLegacy.replaceNullWithEmpty(table.getComment()), ValueInteger.get(table.getId()));
                }
                break;
            }
            case 18: {
                ArrayList<InDoubtTransaction> arrayList6 = this.database.getInDoubtTransactions();
                if (arrayList6 == null || !bl) break;
                for (InDoubtTransaction triggerObject : arrayList6) {
                    this.add(sessionLocal, arrayList, triggerObject.getTransactionName(), triggerObject.getStateDescription());
                }
                break;
            }
            case 19: {
                for (SchemaObject schemaObject : this.getAllSchemaObjects(5)) {
                    Constraint constraint = (Constraint)schemaObject;
                    if (constraint.getConstraintType() != Constraint.Type.REFERENTIAL) continue;
                    ConstraintReferential sessionLocal2 = (ConstraintReferential)constraint;
                    IndexColumn[] constraintCheck = sessionLocal2.getColumns();
                    IndexColumn[] indexColumnArray = sessionLocal2.getRefColumns();
                    Table table = sessionLocal2.getTable();
                    Table table3 = sessionLocal2.getRefTable();
                    String string15 = table3.getName();
                    if (!this.checkIndex(sessionLocal, string15, value, value2)) continue;
                    ValueSmallint valueSmallint = ValueSmallint.get(InformationSchemaTableLegacy.getRefAction(sessionLocal2.getUpdateAction()));
                    ValueSmallint valueSmallint2 = ValueSmallint.get(InformationSchemaTableLegacy.getRefAction(sessionLocal2.getDeleteAction()));
                    for (int i = 0; i < constraintCheck.length; ++i) {
                        this.add(sessionLocal, arrayList, string3, table3.getSchema().getName(), table3.getName(), indexColumnArray[i].column.getName(), string3, table.getSchema().getName(), table.getName(), constraintCheck[i].column.getName(), ValueSmallint.get((short)(i + 1)), valueSmallint, valueSmallint2, sessionLocal2.getName(), sessionLocal2.getReferencedConstraint().getName(), ValueSmallint.get((short)7));
                    }
                }
                break;
            }
            case 21: {
                for (SchemaObject schemaObject : this.getAllSchemaObjects(5)) {
                    void iterator2;
                    String string16;
                    Constraint constraint = (Constraint)schemaObject;
                    Constraint.Type sessionLocal3 = constraint.getConstraintType();
                    Object var13_167 = null;
                    IndexColumn[] indexColumnArray = null;
                    Table table = constraint.getTable();
                    if (this.hideTable(table, sessionLocal)) continue;
                    Index index = constraint.getIndex();
                    String string17 = null;
                    if (index != null) {
                        string17 = index.getName();
                    }
                    if (!this.checkIndex(sessionLocal, string16 = table.getName(), value, value2)) continue;
                    if (sessionLocal3 == Constraint.Type.CHECK) {
                        String string18 = constraint.getExpression().getSQL(0);
                    } else if (sessionLocal3 == Constraint.Type.UNIQUE || sessionLocal3 == Constraint.Type.PRIMARY_KEY) {
                        indexColumnArray = ((ConstraintUnique)constraint).getColumns();
                    } else if (sessionLocal3 == Constraint.Type.REFERENTIAL) {
                        indexColumnArray = ((ConstraintReferential)constraint).getColumns();
                    }
                    String string19 = null;
                    if (indexColumnArray != null) {
                        StringBuilder stringBuilder = new StringBuilder();
                        int n = indexColumnArray.length;
                        for (int i = 0; i < n; ++i) {
                            if (i > 0) {
                                stringBuilder.append(',');
                            }
                            stringBuilder.append(indexColumnArray[i].column.getName());
                        }
                        string19 = stringBuilder.toString();
                    }
                    this.add(sessionLocal, arrayList, string3, constraint.getSchema().getName(), constraint.getName(), sessionLocal3 == Constraint.Type.PRIMARY_KEY ? sessionLocal3.getSqlName() : sessionLocal3.name(), string3, table.getSchema().getName(), string16, string17, iterator2, string19, InformationSchemaTableLegacy.replaceNullWithEmpty(constraint.getComment()), constraint.getCreateSQL(), ValueInteger.get(constraint.getId()));
                }
                break;
            }
            case 22: {
                for (SchemaObject schemaObject : this.getAllSchemaObjects(11)) {
                    Constant i = (Constant)schemaObject;
                    ValueExpression string34 = i.getValue();
                    this.add(sessionLocal, arrayList, string3, i.getSchema().getName(), i.getName(), ValueInteger.get(DataType.convertTypeToSQLType(string34.getType())), InformationSchemaTableLegacy.replaceNullWithEmpty(i.getComment()), string34.getSQL(0), ValueInteger.get(i.getId()));
                }
                break;
            }
            case 23: {
                for (SchemaObject table : this.getAllSchemaObjects(12)) {
                    Domain timeZoneProvider = (Domain)table;
                    Domain n = timeZoneProvider.getDomain();
                    TypeInfo typeInfo = timeZoneProvider.getDataType();
                    this.add(sessionLocal, arrayList, string3, timeZoneProvider.getSchema().getName(), timeZoneProvider.getName(), timeZoneProvider.getDefaultSQL(), timeZoneProvider.getOnUpdateSQL(), ValueInteger.get(DataType.convertTypeToSQLType(typeInfo)), ValueInteger.get(MathUtils.convertLongToInt(typeInfo.getPrecision())), ValueInteger.get(typeInfo.getScale()), typeInfo.getDeclaredTypeName(), n != null ? string3 : null, n != null ? n.getSchema().getName() : null, n != null ? n.getName() : null, ValueInteger.get(50), InformationSchemaTableLegacy.replaceNullWithEmpty(timeZoneProvider.getComment()), timeZoneProvider.getCreateSQL(), ValueInteger.get(timeZoneProvider.getId()), timeZoneProvider.getDefaultSQL(), "YES", null);
                }
                break;
            }
            case 24: {
                for (SchemaObject stringBuilder : this.getAllSchemaObjects(4)) {
                    TriggerObject queryEntry = (TriggerObject)stringBuilder;
                    Table type = queryEntry.getTable();
                    this.add(sessionLocal, arrayList, string3, queryEntry.getSchema().getName(), queryEntry.getName(), queryEntry.getTypeNameList(new StringBuilder()).toString(), string3, type.getSchema().getName(), type.getName(), ValueBoolean.get(queryEntry.isBefore()), queryEntry.getTriggerClassName(), ValueInteger.get(queryEntry.getQueueSize()), ValueBoolean.get(queryEntry.isNoWait()), InformationSchemaTableLegacy.replaceNullWithEmpty(queryEntry.getComment()), queryEntry.getCreateSQL(), ValueInteger.get(queryEntry.getId()));
                }
                break;
            }
            case 25: {
                for (SessionLocal domain : this.database.getSessions(false)) {
                    if (!bl && domain != sessionLocal) continue;
                    NetworkConnectionInfo networkConnectionInfo = domain.getNetworkConnectionInfo();
                    Command command = domain.getCurrentCommand();
                    int n = domain.getBlockingSessionId();
                    this.add(sessionLocal, arrayList, ValueInteger.get(domain.getId()), domain.getUser().getName(), networkConnectionInfo == null ? null : networkConnectionInfo.getServer(), networkConnectionInfo == null ? null : networkConnectionInfo.getClient(), networkConnectionInfo == null ? null : networkConnectionInfo.getClientInfo(), domain.getSessionStart(), domain.getIsolationLevel().getSQL(), command == null ? null : command.toString(), command == null ? null : domain.getCommandStartOrEnd(), ValueBoolean.get(domain.hasPendingTransaction()), String.valueOf((Object)domain.getState()), n == 0 ? null : ValueInteger.get(n), domain.getState() == SessionLocal.State.SLEEP ? domain.getCommandStartOrEnd() : null);
                }
                break;
            }
            case 26: {
                for (SessionLocal type : this.database.getSessions(false)) {
                    if (!bl && type != sessionLocal) continue;
                    for (Table table : type.getLocks()) {
                        this.add(sessionLocal, arrayList, table.getSchema().getName(), table.getName(), ValueInteger.get(type.getId()), table.isLockedExclusivelyBy(type) ? "WRITE" : "READ");
                    }
                }
                break;
            }
            case 27: {
                TimeZoneProvider constraintReferential;
                String schemaObject;
                for (String table : sessionLocal.getVariableNames()) {
                    Value value3 = sessionLocal.getVariable(table);
                    StringBuilder stringBuilder = new StringBuilder().append("SET @").append(table).append(' ');
                    value3.getSQL(stringBuilder, 0);
                    this.add(sessionLocal, arrayList, "@" + table, stringBuilder.toString());
                }
                for (Table schemaObject2 : sessionLocal.getLocalTempTables()) {
                    this.add(sessionLocal, arrayList, "TABLE " + schemaObject2.getName(), schemaObject2.getCreateSQL());
                }
                String[] stringArray = sessionLocal.getSchemaSearchPath();
                if (stringArray != null && stringArray.length > 0) {
                    StringBuilder schemaObject3 = new StringBuilder("SET SCHEMA_SEARCH_PATH ");
                    int type = stringArray.length;
                    for (int constraint = 0; constraint < type; ++constraint) {
                        if (constraint > 0) {
                            schemaObject3.append(", ");
                        }
                        StringUtils.quoteIdentifier(schemaObject3, (String)stringArray[constraint]);
                    }
                    this.add(sessionLocal, arrayList, "SCHEMA_SEARCH_PATH", schemaObject3.toString());
                }
                if ((schemaObject = sessionLocal.getCurrentSchemaName()) != null) {
                    this.add(sessionLocal, arrayList, "SCHEMA", StringUtils.quoteIdentifier(new StringBuilder("SET SCHEMA "), schemaObject).toString());
                }
                if ((constraintReferential = sessionLocal.currentTimeZone()).equals(DateTimeUtils.getTimeZone())) break;
                this.add(sessionLocal, arrayList, "TIME ZONE", StringUtils.quoteStringSQL(new StringBuilder("SET TIME ZONE "), constraintReferential.getId()).toString());
                break;
            }
            case 28: {
                QueryStatisticsData queryStatisticsData = this.database.getQueryStatisticsData();
                if (queryStatisticsData == null) break;
                for (QueryStatisticsData.QueryEntry constraint : queryStatisticsData.getQueries()) {
                    this.add(sessionLocal, arrayList, constraint.sqlStatement, ValueInteger.get(constraint.count), ValueDouble.get((double)constraint.executionTimeMinNanos / 1000000.0), ValueDouble.get((double)constraint.executionTimeMaxNanos / 1000000.0), ValueDouble.get((double)constraint.executionTimeCumulativeNanos / 1000000.0), ValueDouble.get(constraint.executionTimeMeanNanos / 1000000.0), ValueDouble.get(constraint.getExecutionTimeStandardDeviation() / 1000000.0), ValueBigint.get(constraint.rowCountMin), ValueBigint.get(constraint.rowCountMax), ValueBigint.get(constraint.rowCountCumulative), ValueDouble.get(constraint.rowCountMean), ValueDouble.get(constraint.getRowCountStandardDeviation()));
                }
                break;
            }
            case 29: {
                for (TableSynonym schemaObject : this.database.getAllSynonyms()) {
                    this.add(sessionLocal, arrayList, string3, schemaObject.getSchema().getName(), schemaObject.getName(), schemaObject.getSynonymForName(), schemaObject.getSynonymForSchema().getName(), "SYNONYM", "VALID", InformationSchemaTableLegacy.replaceNullWithEmpty(schemaObject.getComment()), ValueInteger.get(schemaObject.getId()));
                }
                break;
            }
            case 30: {
                for (SchemaObject schemaObject : this.getAllSchemaObjects(5)) {
                    String string20;
                    Table table;
                    Constraint constraint = (Constraint)schemaObject;
                    Constraint.Type object = constraint.getConstraintType();
                    if (object == Constraint.Type.DOMAIN || this.hideTable(table = constraint.getTable(), sessionLocal) || !this.checkIndex(sessionLocal, string20 = table.getName(), value, value2)) continue;
                    this.add(sessionLocal, arrayList, string3, constraint.getSchema().getName(), constraint.getName(), object.getSqlName(), string3, table.getSchema().getName(), string20, "NO", "NO", InformationSchemaTableLegacy.replaceNullWithEmpty(constraint.getComment()), constraint.getCreateSQL(), ValueInteger.get(constraint.getId()));
                }
                break;
            }
            case 31: {
                for (SchemaObject schemaObject : this.getAllSchemaObjects(5)) {
                    if (((Constraint)schemaObject).getConstraintType() != Constraint.Type.DOMAIN) continue;
                    ConstraintDomain constraintDomain = (ConstraintDomain)schemaObject;
                    Domain domain = constraintDomain.getDomain();
                    this.add(sessionLocal, arrayList, string3, constraintDomain.getSchema().getName(), constraintDomain.getName(), string3, domain.getSchema().getName(), domain.getName(), "NO", "NO", InformationSchemaTableLegacy.replaceNullWithEmpty(constraintDomain.getComment()), constraintDomain.getCreateSQL(), ValueInteger.get(constraintDomain.getId()));
                }
                break;
            }
            case 32: {
                for (SchemaObject schemaObject : this.getAllSchemaObjects(5)) {
                    String string21;
                    Table table;
                    void var13_174;
                    Constraint constraint = (Constraint)schemaObject;
                    Constraint.Type type = constraint.getConstraintType();
                    Object var13_175 = null;
                    if (type == Constraint.Type.UNIQUE || type == Constraint.Type.PRIMARY_KEY) {
                        IndexColumn[] indexColumnArray = ((ConstraintUnique)constraint).getColumns();
                    } else if (type == Constraint.Type.REFERENTIAL) {
                        IndexColumn[] indexColumnArray = ((ConstraintReferential)constraint).getColumns();
                    }
                    if (var13_174 == null || this.hideTable(table = constraint.getTable(), sessionLocal) || !this.checkIndex(sessionLocal, string21 = table.getName(), value, value2)) continue;
                    ConstraintUnique constraintUnique = type == Constraint.Type.REFERENTIAL ? constraint.getReferencedConstraint() : null;
                    Index index = constraint.getIndex();
                    for (int i = 0; i < ((void)var13_174).length; ++i) {
                        void var19_254 = var13_174[i];
                        ValueInteger valueInteger = ValueInteger.get(i + 1);
                        ValueInteger valueInteger3 = null;
                        if (constraintUnique != null) {
                            Column column = ((ConstraintReferential)constraint).getRefColumns()[i].column;
                            IndexColumn[] indexColumnArray = constraintUnique.getColumns();
                            for (int j = 0; j < indexColumnArray.length; ++j) {
                                if (!indexColumnArray[j].column.equals(column)) continue;
                                valueInteger3 = ValueInteger.get(j + 1);
                                break;
                            }
                        }
                        this.add(sessionLocal, arrayList, string3, constraint.getSchema().getName(), constraint.getName(), string3, table.getSchema().getName(), string21, var19_254.columnName, valueInteger, valueInteger3, index != null ? string3 : null, index != null ? index.getSchema().getName() : null, index != null ? index.getName() : null);
                    }
                }
                break;
            }
            case 33: {
                for (SchemaObject schemaObject : this.getAllSchemaObjects(5)) {
                    ConstraintReferential constraintReferential;
                    Table table;
                    if (((Constraint)schemaObject).getConstraintType() != Constraint.Type.REFERENTIAL || this.hideTable(table = (constraintReferential = (ConstraintReferential)schemaObject).getTable(), sessionLocal)) continue;
                    ConstraintUnique constraintUnique = constraintReferential.getReferencedConstraint();
                    this.add(sessionLocal, arrayList, string3, constraintReferential.getSchema().getName(), constraintReferential.getName(), string3, constraintUnique.getSchema().getName(), constraintUnique.getName(), "NONE", constraintReferential.getUpdateAction().getSqlName(), constraintReferential.getDeleteAction().getSqlName());
                }
                break;
            }
            case 34: {
                for (SchemaObject schemaObject : this.getAllSchemaObjects(5)) {
                    ConstraintCheck constraintCheck;
                    Table table;
                    Constraint constraint = (Constraint)schemaObject;
                    Constraint.Type type = constraint.getConstraintType();
                    if (type == Constraint.Type.CHECK ? this.hideTable(table = (constraintCheck = (ConstraintCheck)schemaObject).getTable(), sessionLocal) : type != Constraint.Type.DOMAIN) continue;
                    this.add(sessionLocal, arrayList, string3, schemaObject.getSchema().getName(), schemaObject.getName(), constraint.getExpression().getSQL(0, 2));
                }
                break;
            }
            case 35: {
                block109: for (SchemaObject schemaObject : this.getAllSchemaObjects(5)) {
                    Constraint constraint = (Constraint)schemaObject;
                    switch (constraint.getConstraintType()) {
                        case CHECK: 
                        case DOMAIN: {
                            Object object = new HashSet();
                            constraint.getExpression().isEverything(ExpressionVisitor.getColumnsVisitor((HashSet<Column>)object, null));
                            Iterator iterator2 = ((HashSet)object).iterator();
                            while (iterator2.hasNext()) {
                                Column column = (Column)iterator2.next();
                                Table table = column.getTable();
                                if (!this.checkIndex(sessionLocal, table.getName(), value, value2) || this.hideTable(table, sessionLocal)) continue;
                                this.addConstraintColumnUsage(sessionLocal, arrayList, string3, constraint, column);
                            }
                            continue block109;
                        }
                        case REFERENTIAL: {
                            Object object = constraint.getRefTable();
                            if (this.checkIndex(sessionLocal, ((DbObject)object).getName(), value, value2) && !this.hideTable((Table)object, sessionLocal)) {
                                for (Column column : constraint.getReferencedColumns((Table)object)) {
                                    this.addConstraintColumnUsage(sessionLocal, arrayList, string3, constraint, column);
                                }
                            }
                        }
                        case PRIMARY_KEY: 
                        case UNIQUE: {
                            Object object = constraint.getTable();
                            if (!this.checkIndex(sessionLocal, ((DbObject)object).getName(), value, value2) || this.hideTable((Table)object, sessionLocal)) break;
                            for (Column column : constraint.getReferencedColumns((Table)object)) {
                                this.addConstraintColumnUsage(sessionLocal, arrayList, string3, constraint, column);
                            }
                            break;
                        }
                    }
                }
                break;
            }
            default: {
                throw DbException.getInternalError("type=" + this.type);
            }
        }
        return arrayList;
    }

    private static short getRefAction(ConstraintActionType constraintActionType) {
        switch (constraintActionType) {
            case CASCADE: {
                return 0;
            }
            case RESTRICT: {
                return 1;
            }
            case SET_DEFAULT: {
                return 4;
            }
            case SET_NULL: {
                return 2;
            }
        }
        throw DbException.getInternalError("action=" + (Object)((Object)constraintActionType));
    }

    private void addConstraintColumnUsage(SessionLocal sessionLocal, ArrayList<Row> arrayList, String string, Constraint constraint, Column column) {
        Table table = column.getTable();
        this.add(sessionLocal, arrayList, string, table.getSchema().getName(), table.getName(), column.getName(), string, constraint.getSchema().getName(), constraint.getName());
    }

    private void addPrivileges(SessionLocal sessionLocal, ArrayList<Row> arrayList, DbObject dbObject, String string, Table table, String string2, int n) {
        if ((n & 1) != 0) {
            this.addPrivilege(sessionLocal, arrayList, dbObject, string, table, string2, "SELECT");
        }
        if ((n & 4) != 0) {
            this.addPrivilege(sessionLocal, arrayList, dbObject, string, table, string2, "INSERT");
        }
        if ((n & 8) != 0) {
            this.addPrivilege(sessionLocal, arrayList, dbObject, string, table, string2, "UPDATE");
        }
        if ((n & 2) != 0) {
            this.addPrivilege(sessionLocal, arrayList, dbObject, string, table, string2, "DELETE");
        }
    }

    private void addPrivilege(SessionLocal sessionLocal, ArrayList<Row> arrayList, DbObject dbObject, String string, Table table, String string2, String string3) {
        User user;
        String string4 = "NO";
        if (dbObject.getType() == 2 && (user = (User)dbObject).isAdmin()) {
            string4 = "YES";
        }
        if (string2 == null) {
            this.add(sessionLocal, arrayList, null, this.identifier(dbObject.getName()), string, table.getSchema().getName(), table.getName(), string3, string4);
        } else {
            this.add(sessionLocal, arrayList, null, this.identifier(dbObject.getName()), string, table.getSchema().getName(), table.getName(), string2, string3, string4);
        }
    }

    private ArrayList<SchemaObject> getAllSchemaObjects(int n) {
        ArrayList<SchemaObject> arrayList = new ArrayList<SchemaObject>();
        for (Schema schema : this.database.getAllSchemas()) {
            schema.getAll(n, arrayList);
        }
        return arrayList;
    }

    private ArrayList<Table> getAllTables(SessionLocal sessionLocal) {
        ArrayList<Table> arrayList = new ArrayList<Table>();
        for (Schema schema : this.database.getAllSchemas()) {
            arrayList.addAll(schema.getAllTablesAndViews(sessionLocal));
        }
        arrayList.addAll(sessionLocal.getLocalTempTables());
        return arrayList;
    }

    private ArrayList<Table> getTablesByName(SessionLocal sessionLocal, String string) {
        ArrayList<Table> arrayList = new ArrayList<Table>(1);
        for (Schema schema : this.database.getAllSchemas()) {
            Table table = schema.getTableOrViewByName(sessionLocal, string);
            if (table == null) continue;
            arrayList.add(table);
        }
        Table table = sessionLocal.findLocalTempTable(string);
        if (table != null) {
            arrayList.add(table);
        }
        return arrayList;
    }

    @Override
    public long getMaxDataModificationId() {
        switch (this.type) {
            case 6: 
            case 8: 
            case 18: 
            case 25: 
            case 26: 
            case 27: {
                return Long.MAX_VALUE;
            }
        }
        return this.database.getModificationDataId();
    }
}

