/*
 * Decompiled with CFR 0.152.
 */
package org.h2.table;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import org.h2.api.IntervalQualifier;
import org.h2.command.Command;
import org.h2.command.ParserBase;
import org.h2.constraint.Constraint;
import org.h2.constraint.ConstraintCheck;
import org.h2.constraint.ConstraintDomain;
import org.h2.constraint.ConstraintReferential;
import org.h2.constraint.ConstraintUnique;
import org.h2.engine.Constants;
import org.h2.engine.DbObject;
import org.h2.engine.NullsDistinct;
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
import org.h2.index.IndexType;
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
import org.h2.table.TableView;
import org.h2.util.DateTimeUtils;
import org.h2.util.MathUtils;
import org.h2.util.NetworkConnectionInfo;
import org.h2.util.StringUtils;
import org.h2.util.TimeZoneProvider;
import org.h2.util.Utils;
import org.h2.util.geometry.EWKTUtils;
import org.h2.value.CompareMode;
import org.h2.value.DataType;
import org.h2.value.ExtTypeInfoEnum;
import org.h2.value.ExtTypeInfoGeometry;
import org.h2.value.ExtTypeInfoRow;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueBigint;
import org.h2.value.ValueBoolean;
import org.h2.value.ValueDouble;
import org.h2.value.ValueInteger;
import org.h2.value.ValueNull;
import org.h2.value.ValueToObjectConverter2;
import org.h2.value.ValueVarchar;

public final class InformationSchemaTable
extends MetaTable {
    private static final String CHARACTER_SET_NAME = "Unicode";
    private static final int INFORMATION_SCHEMA_CATALOG_NAME = 0;
    private static final int CHECK_CONSTRAINTS = 1;
    private static final int COLLATIONS = 2;
    private static final int COLUMNS = 3;
    private static final int COLUMN_PRIVILEGES = 4;
    private static final int CONSTRAINT_COLUMN_USAGE = 5;
    private static final int DOMAINS = 6;
    private static final int DOMAIN_CONSTRAINTS = 7;
    private static final int ELEMENT_TYPES = 8;
    private static final int FIELDS = 9;
    private static final int KEY_COLUMN_USAGE = 10;
    private static final int PARAMETERS = 11;
    private static final int REFERENTIAL_CONSTRAINTS = 12;
    private static final int ROUTINES = 13;
    private static final int SCHEMATA = 14;
    private static final int SEQUENCES = 15;
    private static final int TABLES = 16;
    private static final int TABLE_CONSTRAINTS = 17;
    private static final int TABLE_PRIVILEGES = 18;
    private static final int TRIGGERS = 19;
    private static final int VIEWS = 20;
    private static final int CONSTANTS = 21;
    private static final int ENUM_VALUES = 22;
    private static final int INDEXES = 23;
    private static final int INDEX_COLUMNS = 24;
    private static final int IN_DOUBT = 25;
    private static final int LOCKS = 26;
    private static final int QUERY_STATISTICS = 27;
    private static final int RIGHTS = 28;
    private static final int ROLES = 29;
    private static final int SESSIONS = 30;
    private static final int SESSION_STATE = 31;
    private static final int SETTINGS = 32;
    private static final int SYNONYMS = 33;
    private static final int USERS = 34;
    public static final int META_TABLE_TYPE_COUNT = 35;
    private final boolean isView;

    public InformationSchemaTable(Schema schema, int n, int n2) {
        super(schema, n, n2);
        Column[] columnArray;
        String string = null;
        boolean bl = true;
        switch (n2) {
            case 0: {
                this.setMetaTableName("INFORMATION_SCHEMA_CATALOG_NAME");
                bl = false;
                columnArray = new Column[]{this.column("CATALOG_NAME")};
                break;
            }
            case 1: {
                this.setMetaTableName("CHECK_CONSTRAINTS");
                columnArray = new Column[]{this.column("CONSTRAINT_CATALOG"), this.column("CONSTRAINT_SCHEMA"), this.column("CONSTRAINT_NAME"), this.column("CHECK_CLAUSE")};
                string = "CONSTRAINT_NAME";
                break;
            }
            case 2: {
                this.setMetaTableName("COLLATIONS");
                columnArray = new Column[]{this.column("COLLATION_CATALOG"), this.column("COLLATION_SCHEMA"), this.column("COLLATION_NAME"), this.column("PAD_ATTRIBUTE"), this.column("LANGUAGE_TAG")};
                break;
            }
            case 3: {
                this.setMetaTableName("COLUMNS");
                columnArray = new Column[]{this.column("TABLE_CATALOG"), this.column("TABLE_SCHEMA"), this.column("TABLE_NAME"), this.column("COLUMN_NAME"), this.column("ORDINAL_POSITION", TypeInfo.TYPE_INTEGER), this.column("COLUMN_DEFAULT"), this.column("IS_NULLABLE"), this.column("DATA_TYPE"), this.column("CHARACTER_MAXIMUM_LENGTH", TypeInfo.TYPE_BIGINT), this.column("CHARACTER_OCTET_LENGTH", TypeInfo.TYPE_BIGINT), this.column("NUMERIC_PRECISION", TypeInfo.TYPE_INTEGER), this.column("NUMERIC_PRECISION_RADIX", TypeInfo.TYPE_INTEGER), this.column("NUMERIC_SCALE", TypeInfo.TYPE_INTEGER), this.column("DATETIME_PRECISION", TypeInfo.TYPE_INTEGER), this.column("INTERVAL_TYPE"), this.column("INTERVAL_PRECISION", TypeInfo.TYPE_INTEGER), this.column("CHARACTER_SET_CATALOG"), this.column("CHARACTER_SET_SCHEMA"), this.column("CHARACTER_SET_NAME"), this.column("COLLATION_CATALOG"), this.column("COLLATION_SCHEMA"), this.column("COLLATION_NAME"), this.column("DOMAIN_CATALOG"), this.column("DOMAIN_SCHEMA"), this.column("DOMAIN_NAME"), this.column("MAXIMUM_CARDINALITY", TypeInfo.TYPE_INTEGER), this.column("DTD_IDENTIFIER"), this.column("IS_IDENTITY"), this.column("IDENTITY_GENERATION"), this.column("IDENTITY_START", TypeInfo.TYPE_BIGINT), this.column("IDENTITY_INCREMENT", TypeInfo.TYPE_BIGINT), this.column("IDENTITY_MAXIMUM", TypeInfo.TYPE_BIGINT), this.column("IDENTITY_MINIMUM", TypeInfo.TYPE_BIGINT), this.column("IDENTITY_CYCLE"), this.column("IS_GENERATED"), this.column("GENERATION_EXPRESSION"), this.column("DECLARED_DATA_TYPE"), this.column("DECLARED_NUMERIC_PRECISION", TypeInfo.TYPE_INTEGER), this.column("DECLARED_NUMERIC_SCALE", TypeInfo.TYPE_INTEGER), this.column("GEOMETRY_TYPE"), this.column("GEOMETRY_SRID", TypeInfo.TYPE_INTEGER), this.column("IDENTITY_BASE", TypeInfo.TYPE_BIGINT), this.column("IDENTITY_CACHE", TypeInfo.TYPE_BIGINT), this.column("COLUMN_ON_UPDATE"), this.column("IS_VISIBLE", TypeInfo.TYPE_BOOLEAN), this.column("DEFAULT_ON_NULL", TypeInfo.TYPE_BOOLEAN), this.column("SELECTIVITY", TypeInfo.TYPE_INTEGER), this.column("REMARKS")};
                string = "TABLE_NAME";
                break;
            }
            case 4: {
                this.setMetaTableName("COLUMN_PRIVILEGES");
                columnArray = new Column[]{this.column("GRANTOR"), this.column("GRANTEE"), this.column("TABLE_CATALOG"), this.column("TABLE_SCHEMA"), this.column("TABLE_NAME"), this.column("COLUMN_NAME"), this.column("PRIVILEGE_TYPE"), this.column("IS_GRANTABLE")};
                string = "TABLE_NAME";
                break;
            }
            case 5: {
                this.setMetaTableName("CONSTRAINT_COLUMN_USAGE");
                columnArray = new Column[]{this.column("TABLE_CATALOG"), this.column("TABLE_SCHEMA"), this.column("TABLE_NAME"), this.column("COLUMN_NAME"), this.column("CONSTRAINT_CATALOG"), this.column("CONSTRAINT_SCHEMA"), this.column("CONSTRAINT_NAME")};
                string = "TABLE_NAME";
                break;
            }
            case 6: {
                this.setMetaTableName("DOMAINS");
                columnArray = new Column[]{this.column("DOMAIN_CATALOG"), this.column("DOMAIN_SCHEMA"), this.column("DOMAIN_NAME"), this.column("DATA_TYPE"), this.column("CHARACTER_MAXIMUM_LENGTH", TypeInfo.TYPE_BIGINT), this.column("CHARACTER_OCTET_LENGTH", TypeInfo.TYPE_BIGINT), this.column("CHARACTER_SET_CATALOG"), this.column("CHARACTER_SET_SCHEMA"), this.column("CHARACTER_SET_NAME"), this.column("COLLATION_CATALOG"), this.column("COLLATION_SCHEMA"), this.column("COLLATION_NAME"), this.column("NUMERIC_PRECISION", TypeInfo.TYPE_INTEGER), this.column("NUMERIC_PRECISION_RADIX", TypeInfo.TYPE_INTEGER), this.column("NUMERIC_SCALE", TypeInfo.TYPE_INTEGER), this.column("DATETIME_PRECISION", TypeInfo.TYPE_INTEGER), this.column("INTERVAL_TYPE"), this.column("INTERVAL_PRECISION", TypeInfo.TYPE_INTEGER), this.column("DOMAIN_DEFAULT"), this.column("MAXIMUM_CARDINALITY", TypeInfo.TYPE_INTEGER), this.column("DTD_IDENTIFIER"), this.column("DECLARED_DATA_TYPE"), this.column("DECLARED_NUMERIC_PRECISION", TypeInfo.TYPE_INTEGER), this.column("DECLARED_NUMERIC_SCALE", TypeInfo.TYPE_INTEGER), this.column("GEOMETRY_TYPE"), this.column("GEOMETRY_SRID", TypeInfo.TYPE_INTEGER), this.column("DOMAIN_ON_UPDATE"), this.column("PARENT_DOMAIN_CATALOG"), this.column("PARENT_DOMAIN_SCHEMA"), this.column("PARENT_DOMAIN_NAME"), this.column("REMARKS")};
                string = "DOMAIN_NAME";
                break;
            }
            case 7: {
                this.setMetaTableName("DOMAIN_CONSTRAINTS");
                columnArray = new Column[]{this.column("CONSTRAINT_CATALOG"), this.column("CONSTRAINT_SCHEMA"), this.column("CONSTRAINT_NAME"), this.column("DOMAIN_CATALOG"), this.column("DOMAIN_SCHEMA"), this.column("DOMAIN_NAME"), this.column("IS_DEFERRABLE"), this.column("INITIALLY_DEFERRED"), this.column("REMARKS")};
                string = "DOMAIN_NAME";
                break;
            }
            case 8: {
                this.setMetaTableName("ELEMENT_TYPES");
                columnArray = new Column[]{this.column("OBJECT_CATALOG"), this.column("OBJECT_SCHEMA"), this.column("OBJECT_NAME"), this.column("OBJECT_TYPE"), this.column("COLLECTION_TYPE_IDENTIFIER"), this.column("DATA_TYPE"), this.column("CHARACTER_MAXIMUM_LENGTH", TypeInfo.TYPE_BIGINT), this.column("CHARACTER_OCTET_LENGTH", TypeInfo.TYPE_BIGINT), this.column("CHARACTER_SET_CATALOG"), this.column("CHARACTER_SET_SCHEMA"), this.column("CHARACTER_SET_NAME"), this.column("COLLATION_CATALOG"), this.column("COLLATION_SCHEMA"), this.column("COLLATION_NAME"), this.column("NUMERIC_PRECISION", TypeInfo.TYPE_INTEGER), this.column("NUMERIC_PRECISION_RADIX", TypeInfo.TYPE_INTEGER), this.column("NUMERIC_SCALE", TypeInfo.TYPE_INTEGER), this.column("DATETIME_PRECISION", TypeInfo.TYPE_INTEGER), this.column("INTERVAL_TYPE"), this.column("INTERVAL_PRECISION", TypeInfo.TYPE_INTEGER), this.column("MAXIMUM_CARDINALITY", TypeInfo.TYPE_INTEGER), this.column("DTD_IDENTIFIER"), this.column("DECLARED_DATA_TYPE"), this.column("DECLARED_NUMERIC_PRECISION", TypeInfo.TYPE_INTEGER), this.column("DECLARED_NUMERIC_SCALE", TypeInfo.TYPE_INTEGER), this.column("GEOMETRY_TYPE"), this.column("GEOMETRY_SRID", TypeInfo.TYPE_INTEGER)};
                break;
            }
            case 9: {
                this.setMetaTableName("FIELDS");
                columnArray = new Column[]{this.column("OBJECT_CATALOG"), this.column("OBJECT_SCHEMA"), this.column("OBJECT_NAME"), this.column("OBJECT_TYPE"), this.column("ROW_IDENTIFIER"), this.column("FIELD_NAME"), this.column("ORDINAL_POSITION", TypeInfo.TYPE_INTEGER), this.column("DATA_TYPE"), this.column("CHARACTER_MAXIMUM_LENGTH", TypeInfo.TYPE_BIGINT), this.column("CHARACTER_OCTET_LENGTH", TypeInfo.TYPE_BIGINT), this.column("CHARACTER_SET_CATALOG"), this.column("CHARACTER_SET_SCHEMA"), this.column("CHARACTER_SET_NAME"), this.column("COLLATION_CATALOG"), this.column("COLLATION_SCHEMA"), this.column("COLLATION_NAME"), this.column("NUMERIC_PRECISION", TypeInfo.TYPE_INTEGER), this.column("NUMERIC_PRECISION_RADIX", TypeInfo.TYPE_INTEGER), this.column("NUMERIC_SCALE", TypeInfo.TYPE_INTEGER), this.column("DATETIME_PRECISION", TypeInfo.TYPE_INTEGER), this.column("INTERVAL_TYPE"), this.column("INTERVAL_PRECISION", TypeInfo.TYPE_INTEGER), this.column("MAXIMUM_CARDINALITY", TypeInfo.TYPE_INTEGER), this.column("DTD_IDENTIFIER"), this.column("DECLARED_DATA_TYPE"), this.column("DECLARED_NUMERIC_PRECISION", TypeInfo.TYPE_INTEGER), this.column("DECLARED_NUMERIC_SCALE", TypeInfo.TYPE_INTEGER), this.column("GEOMETRY_TYPE"), this.column("GEOMETRY_SRID", TypeInfo.TYPE_INTEGER)};
                break;
            }
            case 10: {
                this.setMetaTableName("KEY_COLUMN_USAGE");
                columnArray = new Column[]{this.column("CONSTRAINT_CATALOG"), this.column("CONSTRAINT_SCHEMA"), this.column("CONSTRAINT_NAME"), this.column("TABLE_CATALOG"), this.column("TABLE_SCHEMA"), this.column("TABLE_NAME"), this.column("COLUMN_NAME"), this.column("ORDINAL_POSITION", TypeInfo.TYPE_INTEGER), this.column("POSITION_IN_UNIQUE_CONSTRAINT", TypeInfo.TYPE_INTEGER)};
                string = "TABLE_NAME";
                break;
            }
            case 11: {
                this.setMetaTableName("PARAMETERS");
                columnArray = new Column[]{this.column("SPECIFIC_CATALOG"), this.column("SPECIFIC_SCHEMA"), this.column("SPECIFIC_NAME"), this.column("ORDINAL_POSITION", TypeInfo.TYPE_INTEGER), this.column("PARAMETER_MODE"), this.column("IS_RESULT"), this.column("AS_LOCATOR"), this.column("PARAMETER_NAME"), this.column("DATA_TYPE"), this.column("CHARACTER_MAXIMUM_LENGTH", TypeInfo.TYPE_BIGINT), this.column("CHARACTER_OCTET_LENGTH", TypeInfo.TYPE_BIGINT), this.column("CHARACTER_SET_CATALOG"), this.column("CHARACTER_SET_SCHEMA"), this.column("CHARACTER_SET_NAME"), this.column("COLLATION_CATALOG"), this.column("COLLATION_SCHEMA"), this.column("COLLATION_NAME"), this.column("NUMERIC_PRECISION", TypeInfo.TYPE_INTEGER), this.column("NUMERIC_PRECISION_RADIX", TypeInfo.TYPE_INTEGER), this.column("NUMERIC_SCALE", TypeInfo.TYPE_INTEGER), this.column("DATETIME_PRECISION", TypeInfo.TYPE_INTEGER), this.column("INTERVAL_TYPE"), this.column("INTERVAL_PRECISION", TypeInfo.TYPE_INTEGER), this.column("MAXIMUM_CARDINALITY", TypeInfo.TYPE_INTEGER), this.column("DTD_IDENTIFIER"), this.column("DECLARED_DATA_TYPE"), this.column("DECLARED_NUMERIC_PRECISION", TypeInfo.TYPE_INTEGER), this.column("DECLARED_NUMERIC_SCALE", TypeInfo.TYPE_INTEGER), this.column("PARAMETER_DEFAULT"), this.column("GEOMETRY_TYPE"), this.column("GEOMETRY_SRID", TypeInfo.TYPE_INTEGER)};
                break;
            }
            case 12: {
                this.setMetaTableName("REFERENTIAL_CONSTRAINTS");
                columnArray = new Column[]{this.column("CONSTRAINT_CATALOG"), this.column("CONSTRAINT_SCHEMA"), this.column("CONSTRAINT_NAME"), this.column("UNIQUE_CONSTRAINT_CATALOG"), this.column("UNIQUE_CONSTRAINT_SCHEMA"), this.column("UNIQUE_CONSTRAINT_NAME"), this.column("MATCH_OPTION"), this.column("UPDATE_RULE"), this.column("DELETE_RULE")};
                string = "CONSTRAINT_NAME";
                break;
            }
            case 13: {
                this.setMetaTableName("ROUTINES");
                columnArray = new Column[]{this.column("SPECIFIC_CATALOG"), this.column("SPECIFIC_SCHEMA"), this.column("SPECIFIC_NAME"), this.column("ROUTINE_CATALOG"), this.column("ROUTINE_SCHEMA"), this.column("ROUTINE_NAME"), this.column("ROUTINE_TYPE"), this.column("DATA_TYPE"), this.column("CHARACTER_MAXIMUM_LENGTH", TypeInfo.TYPE_BIGINT), this.column("CHARACTER_OCTET_LENGTH", TypeInfo.TYPE_BIGINT), this.column("CHARACTER_SET_CATALOG"), this.column("CHARACTER_SET_SCHEMA"), this.column("CHARACTER_SET_NAME"), this.column("COLLATION_CATALOG"), this.column("COLLATION_SCHEMA"), this.column("COLLATION_NAME"), this.column("NUMERIC_PRECISION", TypeInfo.TYPE_INTEGER), this.column("NUMERIC_PRECISION_RADIX", TypeInfo.TYPE_INTEGER), this.column("NUMERIC_SCALE", TypeInfo.TYPE_INTEGER), this.column("DATETIME_PRECISION", TypeInfo.TYPE_INTEGER), this.column("INTERVAL_TYPE"), this.column("INTERVAL_PRECISION", TypeInfo.TYPE_INTEGER), this.column("MAXIMUM_CARDINALITY", TypeInfo.TYPE_INTEGER), this.column("DTD_IDENTIFIER"), this.column("ROUTINE_BODY"), this.column("ROUTINE_DEFINITION"), this.column("EXTERNAL_NAME"), this.column("EXTERNAL_LANGUAGE"), this.column("PARAMETER_STYLE"), this.column("IS_DETERMINISTIC"), this.column("DECLARED_DATA_TYPE"), this.column("DECLARED_NUMERIC_PRECISION", TypeInfo.TYPE_INTEGER), this.column("DECLARED_NUMERIC_SCALE", TypeInfo.TYPE_INTEGER), this.column("GEOMETRY_TYPE"), this.column("GEOMETRY_SRID", TypeInfo.TYPE_INTEGER), this.column("REMARKS")};
                break;
            }
            case 14: {
                this.setMetaTableName("SCHEMATA");
                columnArray = new Column[]{this.column("CATALOG_NAME"), this.column("SCHEMA_NAME"), this.column("SCHEMA_OWNER"), this.column("DEFAULT_CHARACTER_SET_CATALOG"), this.column("DEFAULT_CHARACTER_SET_SCHEMA"), this.column("DEFAULT_CHARACTER_SET_NAME"), this.column("SQL_PATH"), this.column("DEFAULT_COLLATION_NAME"), this.column("REMARKS")};
                break;
            }
            case 15: {
                this.setMetaTableName("SEQUENCES");
                columnArray = new Column[]{this.column("SEQUENCE_CATALOG"), this.column("SEQUENCE_SCHEMA"), this.column("SEQUENCE_NAME"), this.column("DATA_TYPE"), this.column("NUMERIC_PRECISION", TypeInfo.TYPE_INTEGER), this.column("NUMERIC_PRECISION_RADIX", TypeInfo.TYPE_INTEGER), this.column("NUMERIC_SCALE", TypeInfo.TYPE_INTEGER), this.column("START_VALUE", TypeInfo.TYPE_BIGINT), this.column("MINIMUM_VALUE", TypeInfo.TYPE_BIGINT), this.column("MAXIMUM_VALUE", TypeInfo.TYPE_BIGINT), this.column("INCREMENT", TypeInfo.TYPE_BIGINT), this.column("CYCLE_OPTION"), this.column("DECLARED_DATA_TYPE"), this.column("DECLARED_NUMERIC_PRECISION", TypeInfo.TYPE_INTEGER), this.column("DECLARED_NUMERIC_SCALE", TypeInfo.TYPE_INTEGER), this.column("BASE_VALUE", TypeInfo.TYPE_BIGINT), this.column("CACHE", TypeInfo.TYPE_BIGINT), this.column("REMARKS")};
                string = "SEQUENCE_NAME";
                break;
            }
            case 16: {
                this.setMetaTableName("TABLES");
                columnArray = new Column[]{this.column("TABLE_CATALOG"), this.column("TABLE_SCHEMA"), this.column("TABLE_NAME"), this.column("TABLE_TYPE"), this.column("IS_INSERTABLE_INTO"), this.column("COMMIT_ACTION"), this.column("STORAGE_TYPE"), this.column("REMARKS"), this.column("LAST_MODIFICATION", TypeInfo.TYPE_BIGINT), this.column("TABLE_CLASS"), this.column("ROW_COUNT_ESTIMATE", TypeInfo.TYPE_BIGINT)};
                string = "TABLE_NAME";
                break;
            }
            case 17: {
                this.setMetaTableName("TABLE_CONSTRAINTS");
                columnArray = new Column[]{this.column("CONSTRAINT_CATALOG"), this.column("CONSTRAINT_SCHEMA"), this.column("CONSTRAINT_NAME"), this.column("CONSTRAINT_TYPE"), this.column("TABLE_CATALOG"), this.column("TABLE_SCHEMA"), this.column("TABLE_NAME"), this.column("IS_DEFERRABLE"), this.column("INITIALLY_DEFERRED"), this.column("ENFORCED"), this.column("NULLS_DISTINCT"), this.column("INDEX_CATALOG"), this.column("INDEX_SCHEMA"), this.column("INDEX_NAME"), this.column("REMARKS")};
                string = "TABLE_NAME";
                break;
            }
            case 18: {
                this.setMetaTableName("TABLE_PRIVILEGES");
                columnArray = new Column[]{this.column("GRANTOR"), this.column("GRANTEE"), this.column("TABLE_CATALOG"), this.column("TABLE_SCHEMA"), this.column("TABLE_NAME"), this.column("PRIVILEGE_TYPE"), this.column("IS_GRANTABLE"), this.column("WITH_HIERARCHY")};
                string = "TABLE_NAME";
                break;
            }
            case 19: {
                this.setMetaTableName("TRIGGERS");
                columnArray = new Column[]{this.column("TRIGGER_CATALOG"), this.column("TRIGGER_SCHEMA"), this.column("TRIGGER_NAME"), this.column("EVENT_MANIPULATION"), this.column("EVENT_OBJECT_CATALOG"), this.column("EVENT_OBJECT_SCHEMA"), this.column("EVENT_OBJECT_TABLE"), this.column("ACTION_ORIENTATION"), this.column("ACTION_TIMING"), this.column("IS_ROLLBACK", TypeInfo.TYPE_BOOLEAN), this.column("JAVA_CLASS"), this.column("QUEUE_SIZE", TypeInfo.TYPE_INTEGER), this.column("NO_WAIT", TypeInfo.TYPE_BOOLEAN), this.column("REMARKS")};
                string = "EVENT_OBJECT_TABLE";
                break;
            }
            case 20: {
                this.setMetaTableName("VIEWS");
                columnArray = new Column[]{this.column("TABLE_CATALOG"), this.column("TABLE_SCHEMA"), this.column("TABLE_NAME"), this.column("VIEW_DEFINITION"), this.column("CHECK_OPTION"), this.column("IS_UPDATABLE"), this.column("INSERTABLE_INTO"), this.column("IS_TRIGGER_UPDATABLE"), this.column("IS_TRIGGER_DELETABLE"), this.column("IS_TRIGGER_INSERTABLE_INTO"), this.column("STATUS"), this.column("REMARKS")};
                string = "TABLE_NAME";
                break;
            }
            case 21: {
                this.setMetaTableName("CONSTANTS");
                bl = false;
                columnArray = new Column[]{this.column("CONSTANT_CATALOG"), this.column("CONSTANT_SCHEMA"), this.column("CONSTANT_NAME"), this.column("VALUE_DEFINITION"), this.column("DATA_TYPE"), this.column("CHARACTER_MAXIMUM_LENGTH", TypeInfo.TYPE_BIGINT), this.column("CHARACTER_OCTET_LENGTH", TypeInfo.TYPE_BIGINT), this.column("CHARACTER_SET_CATALOG"), this.column("CHARACTER_SET_SCHEMA"), this.column("CHARACTER_SET_NAME"), this.column("COLLATION_CATALOG"), this.column("COLLATION_SCHEMA"), this.column("COLLATION_NAME"), this.column("NUMERIC_PRECISION", TypeInfo.TYPE_INTEGER), this.column("NUMERIC_PRECISION_RADIX", TypeInfo.TYPE_INTEGER), this.column("NUMERIC_SCALE", TypeInfo.TYPE_INTEGER), this.column("DATETIME_PRECISION", TypeInfo.TYPE_INTEGER), this.column("INTERVAL_TYPE"), this.column("INTERVAL_PRECISION", TypeInfo.TYPE_INTEGER), this.column("MAXIMUM_CARDINALITY", TypeInfo.TYPE_INTEGER), this.column("DTD_IDENTIFIER"), this.column("DECLARED_DATA_TYPE"), this.column("DECLARED_NUMERIC_PRECISION", TypeInfo.TYPE_INTEGER), this.column("DECLARED_NUMERIC_SCALE", TypeInfo.TYPE_INTEGER), this.column("GEOMETRY_TYPE"), this.column("GEOMETRY_SRID", TypeInfo.TYPE_INTEGER), this.column("REMARKS")};
                string = "CONSTANT_NAME";
                break;
            }
            case 22: {
                this.setMetaTableName("ENUM_VALUES");
                bl = false;
                columnArray = new Column[]{this.column("OBJECT_CATALOG"), this.column("OBJECT_SCHEMA"), this.column("OBJECT_NAME"), this.column("OBJECT_TYPE"), this.column("ENUM_IDENTIFIER"), this.column("VALUE_NAME"), this.column("VALUE_ORDINAL")};
                break;
            }
            case 23: {
                this.setMetaTableName("INDEXES");
                bl = false;
                columnArray = new Column[]{this.column("INDEX_CATALOG"), this.column("INDEX_SCHEMA"), this.column("INDEX_NAME"), this.column("TABLE_CATALOG"), this.column("TABLE_SCHEMA"), this.column("TABLE_NAME"), this.column("INDEX_TYPE_NAME"), this.column("NULLS_DISTINCT"), this.column("IS_GENERATED", TypeInfo.TYPE_BOOLEAN), this.column("REMARKS"), this.column("INDEX_CLASS")};
                string = "TABLE_NAME";
                break;
            }
            case 24: {
                this.setMetaTableName("INDEX_COLUMNS");
                bl = false;
                columnArray = new Column[]{this.column("INDEX_CATALOG"), this.column("INDEX_SCHEMA"), this.column("INDEX_NAME"), this.column("TABLE_CATALOG"), this.column("TABLE_SCHEMA"), this.column("TABLE_NAME"), this.column("COLUMN_NAME"), this.column("ORDINAL_POSITION", TypeInfo.TYPE_INTEGER), this.column("ORDERING_SPECIFICATION"), this.column("NULL_ORDERING"), this.column("IS_UNIQUE", TypeInfo.TYPE_BOOLEAN)};
                string = "TABLE_NAME";
                break;
            }
            case 25: {
                this.setMetaTableName("IN_DOUBT");
                bl = false;
                columnArray = new Column[]{this.column("TRANSACTION_NAME"), this.column("TRANSACTION_STATE")};
                break;
            }
            case 26: {
                this.setMetaTableName("LOCKS");
                bl = false;
                columnArray = new Column[]{this.column("TABLE_SCHEMA"), this.column("TABLE_NAME"), this.column("SESSION_ID", TypeInfo.TYPE_INTEGER), this.column("LOCK_TYPE")};
                break;
            }
            case 27: {
                this.setMetaTableName("QUERY_STATISTICS");
                bl = false;
                columnArray = new Column[]{this.column("SQL_STATEMENT"), this.column("EXECUTION_COUNT", TypeInfo.TYPE_INTEGER), this.column("MIN_EXECUTION_TIME", TypeInfo.TYPE_DOUBLE), this.column("MAX_EXECUTION_TIME", TypeInfo.TYPE_DOUBLE), this.column("CUMULATIVE_EXECUTION_TIME", TypeInfo.TYPE_DOUBLE), this.column("AVERAGE_EXECUTION_TIME", TypeInfo.TYPE_DOUBLE), this.column("STD_DEV_EXECUTION_TIME", TypeInfo.TYPE_DOUBLE), this.column("MIN_ROW_COUNT", TypeInfo.TYPE_BIGINT), this.column("MAX_ROW_COUNT", TypeInfo.TYPE_BIGINT), this.column("CUMULATIVE_ROW_COUNT", TypeInfo.TYPE_BIGINT), this.column("AVERAGE_ROW_COUNT", TypeInfo.TYPE_DOUBLE), this.column("STD_DEV_ROW_COUNT", TypeInfo.TYPE_DOUBLE)};
                break;
            }
            case 28: {
                this.setMetaTableName("RIGHTS");
                bl = false;
                columnArray = new Column[]{this.column("GRANTEE"), this.column("GRANTEETYPE"), this.column("GRANTEDROLE"), this.column("RIGHTS"), this.column("TABLE_SCHEMA"), this.column("TABLE_NAME")};
                string = "TABLE_NAME";
                break;
            }
            case 29: {
                this.setMetaTableName("ROLES");
                bl = false;
                columnArray = new Column[]{this.column("ROLE_NAME"), this.column("REMARKS")};
                break;
            }
            case 30: {
                this.setMetaTableName("SESSIONS");
                bl = false;
                columnArray = new Column[]{this.column("SESSION_ID", TypeInfo.TYPE_INTEGER), this.column("USER_NAME"), this.column("SERVER"), this.column("CLIENT_ADDR"), this.column("CLIENT_INFO"), this.column("SESSION_START", TypeInfo.TYPE_TIMESTAMP_TZ), this.column("ISOLATION_LEVEL"), this.column("EXECUTING_STATEMENT"), this.column("EXECUTING_STATEMENT_START", TypeInfo.TYPE_TIMESTAMP_TZ), this.column("CONTAINS_UNCOMMITTED", TypeInfo.TYPE_BOOLEAN), this.column("SESSION_STATE"), this.column("BLOCKER_ID", TypeInfo.TYPE_INTEGER), this.column("SLEEP_SINCE", TypeInfo.TYPE_TIMESTAMP_TZ)};
                break;
            }
            case 31: {
                this.setMetaTableName("SESSION_STATE");
                bl = false;
                columnArray = new Column[]{this.column("STATE_KEY"), this.column("STATE_COMMAND")};
                break;
            }
            case 32: {
                this.setMetaTableName("SETTINGS");
                bl = false;
                columnArray = new Column[]{this.column("SETTING_NAME"), this.column("SETTING_VALUE")};
                break;
            }
            case 33: {
                this.setMetaTableName("SYNONYMS");
                bl = false;
                columnArray = new Column[]{this.column("SYNONYM_CATALOG"), this.column("SYNONYM_SCHEMA"), this.column("SYNONYM_NAME"), this.column("SYNONYM_FOR"), this.column("SYNONYM_FOR_SCHEMA"), this.column("TYPE_NAME"), this.column("STATUS"), this.column("REMARKS")};
                string = "SYNONYM_NAME";
                break;
            }
            case 34: {
                this.setMetaTableName("USERS");
                bl = false;
                columnArray = new Column[]{this.column("USER_NAME"), this.column("IS_ADMIN", TypeInfo.TYPE_BOOLEAN), this.column("REMARKS")};
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
        this.isView = bl;
    }

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
        String string = this.database.getShortName();
        switch (this.type) {
            case 0: {
                this.informationSchemaCatalogName(sessionLocal, arrayList, string);
                break;
            }
            case 1: {
                this.checkConstraints(sessionLocal, value, value2, arrayList, string);
                break;
            }
            case 2: {
                this.collations(sessionLocal, arrayList, string);
                break;
            }
            case 3: {
                this.columns(sessionLocal, value, value2, arrayList, string);
                break;
            }
            case 4: {
                this.columnPrivileges(sessionLocal, value, value2, arrayList, string);
                break;
            }
            case 5: {
                this.constraintColumnUsage(sessionLocal, value, value2, arrayList, string);
                break;
            }
            case 6: {
                this.domains(sessionLocal, value, value2, arrayList, string);
                break;
            }
            case 7: {
                this.domainConstraints(sessionLocal, value, value2, arrayList, string);
                break;
            }
            case 8: {
                this.elementTypesFields(sessionLocal, arrayList, string, 8);
                break;
            }
            case 9: {
                this.elementTypesFields(sessionLocal, arrayList, string, 9);
                break;
            }
            case 10: {
                this.keyColumnUsage(sessionLocal, value, value2, arrayList, string);
                break;
            }
            case 11: {
                this.parameters(sessionLocal, arrayList, string);
                break;
            }
            case 12: {
                this.referentialConstraints(sessionLocal, value, value2, arrayList, string);
                break;
            }
            case 13: {
                this.routines(sessionLocal, arrayList, string);
                break;
            }
            case 14: {
                this.schemata(sessionLocal, arrayList, string);
                break;
            }
            case 15: {
                this.sequences(sessionLocal, value, value2, arrayList, string);
                break;
            }
            case 16: {
                this.tables(sessionLocal, value, value2, arrayList, string);
                break;
            }
            case 17: {
                this.tableConstraints(sessionLocal, value, value2, arrayList, string);
                break;
            }
            case 18: {
                this.tablePrivileges(sessionLocal, value, value2, arrayList, string);
                break;
            }
            case 19: {
                this.triggers(sessionLocal, value, value2, arrayList, string);
                break;
            }
            case 20: {
                this.views(sessionLocal, value, value2, arrayList, string);
                break;
            }
            case 21: {
                this.constants(sessionLocal, value, value2, arrayList, string);
                break;
            }
            case 22: {
                this.elementTypesFields(sessionLocal, arrayList, string, 22);
                break;
            }
            case 23: {
                this.indexes(sessionLocal, value, value2, arrayList, string, false);
                break;
            }
            case 24: {
                this.indexes(sessionLocal, value, value2, arrayList, string, true);
                break;
            }
            case 25: {
                this.inDoubt(sessionLocal, arrayList);
                break;
            }
            case 26: {
                this.locks(sessionLocal, arrayList);
                break;
            }
            case 27: {
                this.queryStatistics(sessionLocal, arrayList);
                break;
            }
            case 28: {
                this.rights(sessionLocal, value, value2, arrayList);
                break;
            }
            case 29: {
                this.roles(sessionLocal, arrayList);
                break;
            }
            case 30: {
                this.sessions(sessionLocal, arrayList);
                break;
            }
            case 31: {
                this.sessionState(sessionLocal, arrayList);
                break;
            }
            case 32: {
                this.settings(sessionLocal, arrayList);
                break;
            }
            case 33: {
                this.synonyms(sessionLocal, arrayList, string);
                break;
            }
            case 34: {
                this.users(sessionLocal, arrayList);
                break;
            }
            default: {
                throw DbException.getInternalError("type=" + this.type);
            }
        }
        return arrayList;
    }

    private void informationSchemaCatalogName(SessionLocal sessionLocal, ArrayList<Row> arrayList, String string) {
        this.add(sessionLocal, arrayList, string);
    }

    private void checkConstraints(SessionLocal sessionLocal, Value value, Value value2, ArrayList<Row> arrayList, String string) {
        for (Schema schema : this.database.getAllSchemas()) {
            for (Constraint constraint : schema.getAllConstraints()) {
                Object object;
                Table table;
                Constraint.Type type = constraint.getConstraintType();
                if ((type != Constraint.Type.CHECK ? type != Constraint.Type.DOMAIN : this.hideTable(table = ((Constraint)(object = (ConstraintCheck)constraint)).getTable(), sessionLocal)) || !this.checkIndex(sessionLocal, (String)(object = constraint.getName()), value, value2)) continue;
                this.checkConstraints(sessionLocal, arrayList, string, constraint, (String)object);
            }
        }
    }

    private void checkConstraints(SessionLocal sessionLocal, ArrayList<Row> arrayList, String string, Constraint constraint, String string2) {
        this.add(sessionLocal, arrayList, string, constraint.getSchema().getName(), string2, constraint.getExpression().getSQL(0, 2));
    }

    private void collations(SessionLocal sessionLocal, ArrayList<Row> arrayList, String string) {
        String string2 = this.database.getMainSchema().getName();
        this.collations(sessionLocal, arrayList, string, string2, "OFF", null);
        for (Locale locale : CompareMode.getCollationLocales(false)) {
            this.collations(sessionLocal, arrayList, string, string2, CompareMode.getName(locale), locale.toLanguageTag());
        }
    }

    private void collations(SessionLocal sessionLocal, ArrayList<Row> arrayList, String string, String string2, String string3, String string4) {
        if ("und".equals(string4)) {
            string4 = null;
        }
        this.add(sessionLocal, arrayList, string, string2, string3, "NO PAD", string4);
    }

    private void columns(SessionLocal sessionLocal, Value value, Value value2, ArrayList<Row> arrayList, String string) {
        String string2 = this.database.getMainSchema().getName();
        String string3 = this.database.getCompareMode().getName();
        if (value != null && value.equals(value2)) {
            String string4 = value.getString();
            if (string4 == null) {
                return;
            }
            for (Schema schema : this.database.getAllSchemas()) {
                Table table = schema.getTableOrViewByName(sessionLocal, string4);
                if (table == null) continue;
                this.columns(sessionLocal, arrayList, string, string2, string3, table, table.getName());
            }
            Table dbObject = sessionLocal.findLocalTempTable(string4);
            if (dbObject != null) {
                this.columns(sessionLocal, arrayList, string, string2, string3, dbObject, dbObject.getName());
            }
        } else {
            for (Schema schema : this.database.getAllSchemas()) {
                for (Table table : schema.getAllTablesAndViews(sessionLocal)) {
                    String string4 = table.getName();
                    if (!this.checkIndex(sessionLocal, string4, value, value2)) continue;
                    this.columns(sessionLocal, arrayList, string, string2, string3, table, string4);
                }
            }
            for (Table table : sessionLocal.getLocalTempTables()) {
                String string5 = table.getName();
                if (!this.checkIndex(sessionLocal, string5, value, value2)) continue;
                this.columns(sessionLocal, arrayList, string, string2, string3, table, string5);
            }
        }
    }

    private void columns(SessionLocal sessionLocal, ArrayList<Row> arrayList, String string, String string2, String string3, Table table, String string4) {
        if (this.hideTable(table, sessionLocal)) {
            return;
        }
        Column[] columnArray = table.getColumns();
        int n = 0;
        int n2 = columnArray.length;
        while (n < n2) {
            this.columns(sessionLocal, arrayList, string, string2, string3, table, string4, columnArray[n], ++n);
        }
    }

    private void columns(SessionLocal sessionLocal, ArrayList<Row> arrayList, String string, String string2, String string3, Table table, String string4, Column column, int n) {
        ValueBigint valueBigint;
        ValueBigint valueBigint2;
        String string5;
        ValueBigint valueBigint3;
        ValueBigint valueBigint4;
        ValueBigint valueBigint5;
        ValueBigint valueBigint6;
        String string6;
        String string7;
        String string8;
        String string9;
        String string10;
        Sequence sequence2;
        String string11;
        String string12;
        String string13;
        String string14;
        TypeInfo typeInfo = column.getType();
        DataTypeInformation dataTypeInformation = DataTypeInformation.valueOf(typeInfo);
        if (dataTypeInformation.hasCharsetAndCollation) {
            string14 = string;
            string13 = string2;
            string12 = CHARACTER_SET_NAME;
            string11 = string3;
        } else {
            string11 = null;
            string12 = null;
            string13 = null;
            string14 = null;
        }
        Domain domain = column.getDomain();
        String string15 = null;
        String string16 = null;
        String string17 = null;
        if (domain != null) {
            string15 = string;
            string16 = domain.getSchema().getName();
            string17 = domain.getName();
        }
        if ((sequence2 = column.getSequence()) != null) {
            string10 = null;
            string9 = "NEVER";
            string8 = null;
            string7 = "YES";
            string6 = column.isGeneratedAlways() ? "ALWAYS" : "BY DEFAULT";
            valueBigint6 = ValueBigint.get(sequence2.getStartValue());
            valueBigint5 = ValueBigint.get(sequence2.getIncrement());
            valueBigint4 = ValueBigint.get(sequence2.getMaxValue());
            valueBigint3 = ValueBigint.get(sequence2.getMinValue());
            Sequence.Cycle cycle = sequence2.getCycle();
            string5 = cycle.isCycle() ? "YES" : "NO";
            valueBigint2 = cycle != Sequence.Cycle.EXHAUSTED ? ValueBigint.get(sequence2.getBaseValue()) : null;
            valueBigint = ValueBigint.get(sequence2.getCacheSize());
        } else {
            if (column.isGenerated()) {
                string10 = null;
                string9 = "ALWAYS";
                string8 = column.getDefaultSQL();
            } else {
                string10 = column.getDefaultSQL();
                string9 = "NEVER";
                string8 = null;
            }
            string7 = "NO";
            string5 = null;
            string6 = null;
            valueBigint = null;
            valueBigint2 = null;
            valueBigint3 = null;
            valueBigint4 = null;
            valueBigint5 = null;
            valueBigint6 = null;
        }
        this.add(sessionLocal, arrayList, string, table.getSchema().getName(), string4, column.getName(), ValueInteger.get(n), string10, column.isNullable() ? "YES" : "NO", this.identifier(dataTypeInformation.dataType), dataTypeInformation.characterPrecision, dataTypeInformation.characterPrecision, dataTypeInformation.numericPrecision, dataTypeInformation.numericPrecisionRadix, dataTypeInformation.numericScale, dataTypeInformation.datetimePrecision, dataTypeInformation.intervalType, dataTypeInformation.intervalPrecision, string14, string13, string12, string14, string13, string11, string15, string16, string17, dataTypeInformation.maximumCardinality, Integer.toString(n), string7, string6, valueBigint6, valueBigint5, valueBigint4, valueBigint3, string5, string9, string8, dataTypeInformation.declaredDataType, dataTypeInformation.declaredNumericPrecision, dataTypeInformation.declaredNumericScale, dataTypeInformation.geometryType, dataTypeInformation.geometrySrid, valueBigint2, valueBigint, column.getOnUpdateSQL(), ValueBoolean.get(column.getVisible()), ValueBoolean.get(column.isDefaultOnNull()), ValueInteger.get(column.getSelectivity()), column.getComment());
    }

    private void columnPrivileges(SessionLocal sessionLocal, Value value, Value value2, ArrayList<Row> arrayList, String string) {
        for (Right right : this.database.getAllRights()) {
            String string2;
            Table table;
            DbObject dbObject = right.getGrantedObject();
            if (!(dbObject instanceof Table) || this.hideTable(table = (Table)dbObject, sessionLocal) || !this.checkIndex(sessionLocal, string2 = table.getName(), value, value2)) continue;
            DbObject dbObject2 = right.getGrantee();
            int n = right.getRightMask();
            for (Column column : table.getColumns()) {
                this.addPrivileges(sessionLocal, arrayList, dbObject2, string, table, column.getName(), n);
            }
        }
    }

    private void constraintColumnUsage(SessionLocal sessionLocal, Value value, Value value2, ArrayList<Row> arrayList, String string) {
        for (Schema schema : this.database.getAllSchemas()) {
            for (Constraint constraint : schema.getAllConstraints()) {
                this.constraintColumnUsage(sessionLocal, value, value2, arrayList, string, constraint);
            }
        }
    }

    private void constraintColumnUsage(SessionLocal sessionLocal, Value value, Value value2, ArrayList<Row> arrayList, String string, Constraint constraint) {
        switch (constraint.getConstraintType()) {
            case CHECK: 
            case DOMAIN: {
                HashSet<Column> hashSet = new HashSet<Column>();
                constraint.getExpression().isEverything(ExpressionVisitor.getColumnsVisitor(hashSet, null));
                for (Column column : hashSet) {
                    Table table = column.getTable();
                    if (!this.checkIndex(sessionLocal, table.getName(), value, value2) || this.hideTable(table, sessionLocal)) continue;
                    this.addConstraintColumnUsage(sessionLocal, arrayList, string, constraint, column);
                }
                break;
            }
            case REFERENTIAL: {
                Table table = constraint.getRefTable();
                if (this.checkIndex(sessionLocal, table.getName(), value, value2) && !this.hideTable(table, sessionLocal)) {
                    for (Column column : constraint.getReferencedColumns(table)) {
                        this.addConstraintColumnUsage(sessionLocal, arrayList, string, constraint, column);
                    }
                }
            }
            case PRIMARY_KEY: 
            case UNIQUE: {
                Table table = constraint.getTable();
                if (!this.checkIndex(sessionLocal, table.getName(), value, value2) || this.hideTable(table, sessionLocal)) break;
                for (Column column : constraint.getReferencedColumns(table)) {
                    this.addConstraintColumnUsage(sessionLocal, arrayList, string, constraint, column);
                }
                break;
            }
        }
    }

    private void domains(SessionLocal sessionLocal, Value value, Value value2, ArrayList<Row> arrayList, String string) {
        String string2 = this.database.getMainSchema().getName();
        String string3 = this.database.getCompareMode().getName();
        for (Schema schema : this.database.getAllSchemas()) {
            for (Domain domain : schema.getAllDomains()) {
                String string4 = domain.getName();
                if (!this.checkIndex(sessionLocal, string4, value, value2)) continue;
                this.domains(sessionLocal, arrayList, string, string2, string3, domain, string4);
            }
        }
    }

    private void domains(SessionLocal sessionLocal, ArrayList<Row> arrayList, String string, String string2, String string3, Domain domain, String string4) {
        String string5;
        String string6;
        String string7;
        String string8;
        Domain domain2 = domain.getDomain();
        TypeInfo typeInfo = domain.getDataType();
        DataTypeInformation dataTypeInformation = DataTypeInformation.valueOf(typeInfo);
        if (dataTypeInformation.hasCharsetAndCollation) {
            string8 = string;
            string7 = string2;
            string6 = CHARACTER_SET_NAME;
            string5 = string3;
        } else {
            string5 = null;
            string6 = null;
            string7 = null;
            string8 = null;
        }
        this.add(sessionLocal, arrayList, string, domain.getSchema().getName(), string4, dataTypeInformation.dataType, dataTypeInformation.characterPrecision, dataTypeInformation.characterPrecision, string8, string7, string6, string8, string7, string5, dataTypeInformation.numericPrecision, dataTypeInformation.numericPrecisionRadix, dataTypeInformation.numericScale, dataTypeInformation.datetimePrecision, dataTypeInformation.intervalType, dataTypeInformation.intervalPrecision, domain.getDefaultSQL(), dataTypeInformation.maximumCardinality, "TYPE", dataTypeInformation.declaredDataType, dataTypeInformation.declaredNumericPrecision, dataTypeInformation.declaredNumericScale, dataTypeInformation.geometryType, dataTypeInformation.geometrySrid, domain.getOnUpdateSQL(), domain2 != null ? string : null, domain2 != null ? domain2.getSchema().getName() : null, domain2 != null ? domain2.getName() : null, domain.getComment());
    }

    private void domainConstraints(SessionLocal sessionLocal, Value value, Value value2, ArrayList<Row> arrayList, String string) {
        for (Schema schema : this.database.getAllSchemas()) {
            for (Constraint constraint : schema.getAllConstraints()) {
                ConstraintDomain constraintDomain;
                Domain domain;
                String string2;
                if (constraint.getConstraintType() != Constraint.Type.DOMAIN || !this.checkIndex(sessionLocal, string2 = (domain = (constraintDomain = (ConstraintDomain)constraint).getDomain()).getName(), value, value2)) continue;
                this.domainConstraints(sessionLocal, arrayList, string, constraintDomain, domain, string2);
            }
        }
    }

    private void domainConstraints(SessionLocal sessionLocal, ArrayList<Row> arrayList, String string, ConstraintDomain constraintDomain, Domain domain, String string2) {
        this.add(sessionLocal, arrayList, string, constraintDomain.getSchema().getName(), constraintDomain.getName(), string, domain.getSchema().getName(), string2, "NO", "NO", constraintDomain.getComment());
    }

    private void elementTypesFields(SessionLocal sessionLocal, ArrayList<Row> arrayList, String string, int n) {
        String string2 = this.database.getMainSchema().getName();
        String string3 = this.database.getCompareMode().getName();
        for (Schema dbObject : this.database.getAllSchemas()) {
            String string4 = dbObject.getName();
            for (Table table : dbObject.getAllTablesAndViews(sessionLocal)) {
                this.elementTypesFieldsForTable(sessionLocal, arrayList, string, n, string2, string3, string4, table);
            }
            for (Domain domain : dbObject.getAllDomains()) {
                this.elementTypesFieldsRow(sessionLocal, arrayList, string, n, string2, string3, string4, domain.getName(), "DOMAIN", "TYPE", domain.getDataType());
            }
            for (UserDefinedFunction userDefinedFunction : dbObject.getAllFunctionsAndAggregates()) {
                FunctionAlias.JavaMethod[] javaMethodArray;
                if (!(userDefinedFunction instanceof FunctionAlias)) continue;
                String string5 = userDefinedFunction.getName();
                try {
                    javaMethodArray = ((FunctionAlias)userDefinedFunction).getJavaMethods();
                }
                catch (DbException dbException) {
                    continue;
                }
                for (int i = 0; i < javaMethodArray.length; ++i) {
                    FunctionAlias.JavaMethod javaMethod = javaMethodArray[i];
                    TypeInfo typeInfo = javaMethod.getDataType();
                    String string6 = string5 + '_' + (i + 1);
                    if (typeInfo != null && typeInfo.getValueType() != 0) {
                        this.elementTypesFieldsRow(sessionLocal, arrayList, string, n, string2, string3, string4, string6, "ROUTINE", "RESULT", typeInfo);
                    }
                    Class<?>[] classArray = javaMethod.getColumnClasses();
                    int n2 = 1;
                    int n3 = classArray.length;
                    for (int j = javaMethod.hasConnectionParam() ? 1 : 0; j < n3; ++j) {
                        this.elementTypesFieldsRow(sessionLocal, arrayList, string, n, string2, string3, string4, string6, "ROUTINE", Integer.toString(n2), ValueToObjectConverter2.classToType(classArray[j]));
                        ++n2;
                    }
                }
            }
            for (Constant constant : dbObject.getAllConstants()) {
                this.elementTypesFieldsRow(sessionLocal, arrayList, string, n, string2, string3, string4, constant.getName(), "CONSTANT", "TYPE", constant.getValue().getType());
            }
        }
        for (Table table : sessionLocal.getLocalTempTables()) {
            this.elementTypesFieldsForTable(sessionLocal, arrayList, string, n, string2, string3, table.getSchema().getName(), table);
        }
    }

    private void elementTypesFieldsForTable(SessionLocal sessionLocal, ArrayList<Row> arrayList, String string, int n, String string2, String string3, String string4, Table table) {
        if (this.hideTable(table, sessionLocal)) {
            return;
        }
        String string5 = table.getName();
        Column[] columnArray = table.getColumns();
        for (int i = 0; i < columnArray.length; ++i) {
            this.elementTypesFieldsRow(sessionLocal, arrayList, string, n, string2, string3, string4, string5, "TABLE", Integer.toString(i + 1), columnArray[i].getType());
        }
    }

    private void elementTypesFieldsRow(SessionLocal sessionLocal, ArrayList<Row> arrayList, String string, int n, String string2, String string3, String string4, String string5, String string6, String string7, TypeInfo typeInfo) {
        switch (typeInfo.getValueType()) {
            case 36: {
                if (n != 22) break;
                this.enumValues(sessionLocal, arrayList, string, string4, string5, string6, string7, typeInfo);
                break;
            }
            case 40: {
                typeInfo = (TypeInfo)typeInfo.getExtTypeInfo();
                String string8 = string7 + '_';
                if (n == 8) {
                    this.elementTypes(sessionLocal, arrayList, string, string2, string3, string4, string5, string6, string7, string8, typeInfo);
                }
                this.elementTypesFieldsRow(sessionLocal, arrayList, string, n, string2, string3, string4, string5, string6, string8, typeInfo);
                break;
            }
            case 41: {
                ExtTypeInfoRow extTypeInfoRow = (ExtTypeInfoRow)typeInfo.getExtTypeInfo();
                int n2 = 0;
                for (Map.Entry<String, TypeInfo> entry : extTypeInfoRow.getFields()) {
                    typeInfo = entry.getValue();
                    String string9 = entry.getKey();
                    String string10 = string7 + '_' + ++n2;
                    if (n == 9) {
                        this.fields(sessionLocal, arrayList, string, string2, string3, string4, string5, string6, string7, string9, n2, string10, typeInfo);
                    }
                    this.elementTypesFieldsRow(sessionLocal, arrayList, string, n, string2, string3, string4, string5, string6, string10, typeInfo);
                }
                break;
            }
        }
    }

    private void elementTypes(SessionLocal sessionLocal, ArrayList<Row> arrayList, String string, String string2, String string3, String string4, String string5, String string6, String string7, String string8, TypeInfo typeInfo) {
        String string9;
        String string10;
        String string11;
        String string12;
        DataTypeInformation dataTypeInformation = DataTypeInformation.valueOf(typeInfo);
        if (dataTypeInformation.hasCharsetAndCollation) {
            string12 = string;
            string11 = string2;
            string10 = CHARACTER_SET_NAME;
            string9 = string3;
        } else {
            string9 = null;
            string10 = null;
            string11 = null;
            string12 = null;
        }
        this.add(sessionLocal, arrayList, string, string4, string5, string6, string7, dataTypeInformation.dataType, dataTypeInformation.characterPrecision, dataTypeInformation.characterPrecision, string12, string11, string10, string12, string11, string9, dataTypeInformation.numericPrecision, dataTypeInformation.numericPrecisionRadix, dataTypeInformation.numericScale, dataTypeInformation.datetimePrecision, dataTypeInformation.intervalType, dataTypeInformation.intervalPrecision, dataTypeInformation.maximumCardinality, string8, dataTypeInformation.declaredDataType, dataTypeInformation.declaredNumericPrecision, dataTypeInformation.declaredNumericScale, dataTypeInformation.geometryType, dataTypeInformation.geometrySrid);
    }

    private void fields(SessionLocal sessionLocal, ArrayList<Row> arrayList, String string, String string2, String string3, String string4, String string5, String string6, String string7, String string8, int n, String string9, TypeInfo typeInfo) {
        String string10;
        String string11;
        String string12;
        String string13;
        DataTypeInformation dataTypeInformation = DataTypeInformation.valueOf(typeInfo);
        if (dataTypeInformation.hasCharsetAndCollation) {
            string13 = string;
            string12 = string2;
            string11 = CHARACTER_SET_NAME;
            string10 = string3;
        } else {
            string10 = null;
            string11 = null;
            string12 = null;
            string13 = null;
        }
        this.add(sessionLocal, arrayList, string, string4, string5, string6, string7, string8, ValueInteger.get(n), dataTypeInformation.dataType, dataTypeInformation.characterPrecision, dataTypeInformation.characterPrecision, string13, string12, string11, string13, string12, string10, dataTypeInformation.numericPrecision, dataTypeInformation.numericPrecisionRadix, dataTypeInformation.numericScale, dataTypeInformation.datetimePrecision, dataTypeInformation.intervalType, dataTypeInformation.intervalPrecision, dataTypeInformation.maximumCardinality, string9, dataTypeInformation.declaredDataType, dataTypeInformation.declaredNumericPrecision, dataTypeInformation.declaredNumericScale, dataTypeInformation.geometryType, dataTypeInformation.geometrySrid);
    }

    private void keyColumnUsage(SessionLocal sessionLocal, Value value, Value value2, ArrayList<Row> arrayList, String string) {
        for (Schema schema : this.database.getAllSchemas()) {
            for (Constraint constraint : schema.getAllConstraints()) {
                String string2;
                Table table;
                Constraint.Type type = constraint.getConstraintType();
                IndexColumn[] indexColumnArray = null;
                if (type == Constraint.Type.UNIQUE || type == Constraint.Type.PRIMARY_KEY) {
                    indexColumnArray = ((ConstraintUnique)constraint).getColumns();
                } else if (type == Constraint.Type.REFERENTIAL) {
                    indexColumnArray = ((ConstraintReferential)constraint).getColumns();
                }
                if (indexColumnArray == null || this.hideTable(table = constraint.getTable(), sessionLocal) || !this.checkIndex(sessionLocal, string2 = table.getName(), value, value2)) continue;
                this.keyColumnUsage(sessionLocal, arrayList, string, constraint, type, indexColumnArray, table, string2);
            }
        }
    }

    private void keyColumnUsage(SessionLocal sessionLocal, ArrayList<Row> arrayList, String string, Constraint constraint, Constraint.Type type, IndexColumn[] indexColumnArray, Table table, String string2) {
        ConstraintUnique constraintUnique = type == Constraint.Type.REFERENTIAL ? constraint.getReferencedConstraint() : null;
        for (int i = 0; i < indexColumnArray.length; ++i) {
            IndexColumn indexColumn = indexColumnArray[i];
            ValueInteger valueInteger = ValueInteger.get(i + 1);
            ValueInteger valueInteger2 = null;
            if (constraintUnique != null) {
                Column column = ((ConstraintReferential)constraint).getRefColumns()[i].column;
                IndexColumn[] indexColumnArray2 = constraintUnique.getColumns();
                for (int j = 0; j < indexColumnArray2.length; ++j) {
                    if (!indexColumnArray2[j].column.equals(column)) continue;
                    valueInteger2 = ValueInteger.get(j + 1);
                    break;
                }
            }
            this.add(sessionLocal, arrayList, string, constraint.getSchema().getName(), constraint.getName(), string, table.getSchema().getName(), string2, indexColumn.columnName, valueInteger, valueInteger2);
        }
    }

    private void parameters(SessionLocal sessionLocal, ArrayList<Row> arrayList, String string) {
        String string2 = this.database.getMainSchema().getName();
        String string3 = this.database.getCompareMode().getName();
        for (Schema schema : this.database.getAllSchemas()) {
            for (UserDefinedFunction userDefinedFunction : schema.getAllFunctionsAndAggregates()) {
                FunctionAlias.JavaMethod[] javaMethodArray;
                if (!(userDefinedFunction instanceof FunctionAlias)) continue;
                try {
                    javaMethodArray = ((FunctionAlias)userDefinedFunction).getJavaMethods();
                }
                catch (DbException dbException) {
                    continue;
                }
                for (int i = 0; i < javaMethodArray.length; ++i) {
                    FunctionAlias.JavaMethod javaMethod = javaMethodArray[i];
                    Class<?>[] classArray = javaMethod.getColumnClasses();
                    int n = 1;
                    int n2 = classArray.length;
                    for (int j = javaMethod.hasConnectionParam() ? 1 : 0; j < n2; ++j) {
                        this.parameters(sessionLocal, arrayList, string, string2, string3, schema.getName(), userDefinedFunction.getName() + '_' + (i + 1), ValueToObjectConverter2.classToType(classArray[j]), n);
                        ++n;
                    }
                }
            }
        }
    }

    private void parameters(SessionLocal sessionLocal, ArrayList<Row> arrayList, String string, String string2, String string3, String string4, String string5, TypeInfo typeInfo, int n) {
        String string6;
        String string7;
        String string8;
        String string9;
        DataTypeInformation dataTypeInformation = DataTypeInformation.valueOf(typeInfo);
        if (dataTypeInformation.hasCharsetAndCollation) {
            string9 = string;
            string8 = string2;
            string7 = CHARACTER_SET_NAME;
            string6 = string3;
        } else {
            string6 = null;
            string7 = null;
            string8 = null;
            string9 = null;
        }
        this.add(sessionLocal, arrayList, string, string4, string5, ValueInteger.get(n), "IN", "NO", DataType.isLargeObject(typeInfo.getValueType()) ? "YES" : "NO", "P" + n, this.identifier(dataTypeInformation.dataType), dataTypeInformation.characterPrecision, dataTypeInformation.characterPrecision, string9, string8, string7, string9, string8, string6, dataTypeInformation.numericPrecision, dataTypeInformation.numericPrecisionRadix, dataTypeInformation.numericScale, dataTypeInformation.datetimePrecision, dataTypeInformation.intervalType, dataTypeInformation.intervalPrecision, dataTypeInformation.maximumCardinality, Integer.toString(n), dataTypeInformation.declaredDataType, dataTypeInformation.declaredNumericPrecision, dataTypeInformation.declaredNumericScale, null, dataTypeInformation.geometryType, dataTypeInformation.geometrySrid);
    }

    private void referentialConstraints(SessionLocal sessionLocal, Value value, Value value2, ArrayList<Row> arrayList, String string) {
        for (Schema schema : this.database.getAllSchemas()) {
            for (Constraint constraint : schema.getAllConstraints()) {
                String string2;
                if (constraint.getConstraintType() != Constraint.Type.REFERENTIAL || this.hideTable(constraint.getTable(), sessionLocal) || !this.checkIndex(sessionLocal, string2 = constraint.getName(), value, value2)) continue;
                this.referentialConstraints(sessionLocal, arrayList, string, (ConstraintReferential)constraint, string2);
            }
        }
    }

    private void referentialConstraints(SessionLocal sessionLocal, ArrayList<Row> arrayList, String string, ConstraintReferential constraintReferential, String string2) {
        ConstraintUnique constraintUnique = constraintReferential.getReferencedConstraint();
        this.add(sessionLocal, arrayList, string, constraintReferential.getSchema().getName(), string2, string, constraintUnique.getSchema().getName(), constraintUnique.getName(), "NONE", constraintReferential.getUpdateAction().getSqlName(), constraintReferential.getDeleteAction().getSqlName());
    }

    private void routines(SessionLocal sessionLocal, ArrayList<Row> arrayList, String string) {
        boolean bl = sessionLocal.getUser().isAdmin();
        String string2 = this.database.getMainSchema().getName();
        String string3 = this.database.getCompareMode().getName();
        for (Schema schema : this.database.getAllSchemas()) {
            String string4 = schema.getName();
            for (UserDefinedFunction userDefinedFunction : schema.getAllFunctionsAndAggregates()) {
                String string5 = userDefinedFunction.getName();
                if (userDefinedFunction instanceof FunctionAlias) {
                    FunctionAlias.JavaMethod[] javaMethodArray;
                    FunctionAlias functionAlias = (FunctionAlias)userDefinedFunction;
                    try {
                        javaMethodArray = functionAlias.getJavaMethods();
                    }
                    catch (DbException dbException) {
                        continue;
                    }
                    for (int i = 0; i < javaMethodArray.length; ++i) {
                        String string6;
                        FunctionAlias.JavaMethod javaMethod = javaMethodArray[i];
                        TypeInfo typeInfo = javaMethod.getDataType();
                        if (typeInfo != null && typeInfo.getValueType() == 0) {
                            string6 = "PROCEDURE";
                            typeInfo = null;
                        } else {
                            string6 = "FUNCTION";
                        }
                        String string7 = functionAlias.getJavaClassName();
                        this.routines(sessionLocal, arrayList, string, string2, string3, string4, string5, string5 + '_' + (i + 1), string6, bl ? functionAlias.getSource() : null, string7 != null ? string7 + '.' + functionAlias.getJavaMethodName() : null, typeInfo, functionAlias.isDeterministic(), functionAlias.getComment());
                    }
                    continue;
                }
                this.routines(sessionLocal, arrayList, string, string2, string3, string4, string5, string5, "AGGREGATE", null, userDefinedFunction.getJavaClassName(), TypeInfo.TYPE_NULL, false, userDefinedFunction.getComment());
            }
        }
    }

    private void routines(SessionLocal sessionLocal, ArrayList<Row> arrayList, String string, String string2, String string3, String string4, String string5, String string6, String string7, String string8, String string9, TypeInfo typeInfo, boolean bl, String string10) {
        String string11;
        String string12;
        String string13;
        String string14;
        DataTypeInformation dataTypeInformation;
        DataTypeInformation dataTypeInformation2 = dataTypeInformation = typeInfo != null ? DataTypeInformation.valueOf(typeInfo) : DataTypeInformation.NULL;
        if (dataTypeInformation.hasCharsetAndCollation) {
            string14 = string;
            string13 = string2;
            string12 = CHARACTER_SET_NAME;
            string11 = string3;
        } else {
            string11 = null;
            string12 = null;
            string13 = null;
            string14 = null;
        }
        this.add(sessionLocal, arrayList, string, string4, string6, string, string4, string5, string7, this.identifier(dataTypeInformation.dataType), dataTypeInformation.characterPrecision, dataTypeInformation.characterPrecision, string14, string13, string12, string14, string13, string11, dataTypeInformation.numericPrecision, dataTypeInformation.numericPrecisionRadix, dataTypeInformation.numericScale, dataTypeInformation.datetimePrecision, dataTypeInformation.intervalType, dataTypeInformation.intervalPrecision, dataTypeInformation.maximumCardinality, "RESULT", "EXTERNAL", string8, string9, "JAVA", "GENERAL", bl ? "YES" : "NO", dataTypeInformation.declaredDataType, dataTypeInformation.declaredNumericPrecision, dataTypeInformation.declaredNumericScale, dataTypeInformation.geometryType, dataTypeInformation.geometrySrid, string10);
    }

    private void schemata(SessionLocal sessionLocal, ArrayList<Row> arrayList, String string) {
        String string2 = this.database.getMainSchema().getName();
        String string3 = this.database.getCompareMode().getName();
        for (Schema schema : this.database.getAllSchemas()) {
            this.add(sessionLocal, arrayList, string, schema.getName(), this.identifier(schema.getOwner().getName()), string, string2, CHARACTER_SET_NAME, null, string3, schema.getComment());
        }
    }

    private void sequences(SessionLocal sessionLocal, Value value, Value value2, ArrayList<Row> arrayList, String string) {
        for (Schema schema : this.database.getAllSchemas()) {
            for (Sequence sequence2 : schema.getAllSequences()) {
                String string2;
                if (sequence2.getBelongsToTable() || !this.checkIndex(sessionLocal, string2 = sequence2.getName(), value, value2)) continue;
                this.sequences(sessionLocal, arrayList, string, sequence2, string2);
            }
        }
    }

    private void sequences(SessionLocal sessionLocal, ArrayList<Row> arrayList, String string, Sequence sequence2, String string2) {
        DataTypeInformation dataTypeInformation = DataTypeInformation.valueOf(sequence2.getDataType());
        Sequence.Cycle cycle = sequence2.getCycle();
        this.add(sessionLocal, arrayList, string, sequence2.getSchema().getName(), string2, dataTypeInformation.dataType, ValueInteger.get(sequence2.getEffectivePrecision()), dataTypeInformation.numericPrecisionRadix, dataTypeInformation.numericScale, ValueBigint.get(sequence2.getStartValue()), ValueBigint.get(sequence2.getMinValue()), ValueBigint.get(sequence2.getMaxValue()), ValueBigint.get(sequence2.getIncrement()), cycle.isCycle() ? "YES" : "NO", dataTypeInformation.declaredDataType, dataTypeInformation.declaredNumericPrecision, dataTypeInformation.declaredNumericScale, cycle != Sequence.Cycle.EXHAUSTED ? ValueBigint.get(sequence2.getBaseValue()) : null, ValueBigint.get(sequence2.getCacheSize()), sequence2.getComment());
    }

    private void tables(SessionLocal sessionLocal, Value value, Value value2, ArrayList<Row> arrayList, String string) {
        for (Schema dbObject : this.database.getAllSchemas()) {
            for (Table table : dbObject.getAllTablesAndViews(sessionLocal)) {
                String string2 = table.getName();
                if (!this.checkIndex(sessionLocal, string2, value, value2)) continue;
                this.tables(sessionLocal, arrayList, string, table, string2);
            }
        }
        for (Table table : sessionLocal.getLocalTempTables()) {
            String string3 = table.getName();
            if (!this.checkIndex(sessionLocal, string3, value, value2)) continue;
            this.tables(sessionLocal, arrayList, string, table, string3);
        }
    }

    private void tables(SessionLocal sessionLocal, ArrayList<Row> arrayList, String string, Table table, String string2) {
        String string3;
        String string4;
        if (this.hideTable(table, sessionLocal)) {
            return;
        }
        if (table.isTemporary()) {
            string4 = table.getOnCommitTruncate() ? "DELETE" : (table.getOnCommitDrop() ? "DROP" : "PRESERVE");
            string3 = table.isGlobalTemporary() ? "GLOBAL TEMPORARY" : "LOCAL TEMPORARY";
        } else {
            string4 = null;
            switch (table.getTableType()) {
                case TABLE_LINK: {
                    string3 = "TABLE LINK";
                    break;
                }
                case EXTERNAL_TABLE_ENGINE: {
                    string3 = "EXTERNAL";
                    break;
                }
                default: {
                    string3 = table.isPersistIndexes() ? "CACHED" : "MEMORY";
                }
            }
        }
        long l = table.getMaxDataModificationId();
        this.add(sessionLocal, arrayList, string, table.getSchema().getName(), string2, table.getSQLTableType(), table.isInsertable() ? "YES" : "NO", string4, string3, table.getComment(), l != Long.MAX_VALUE ? ValueBigint.get(l) : null, table.getClass().getName(), ValueBigint.get(table.getRowCountApproximation(sessionLocal)));
    }

    private void tableConstraints(SessionLocal sessionLocal, Value value, Value value2, ArrayList<Row> arrayList, String string) {
        for (Schema schema : this.database.getAllSchemas()) {
            for (Constraint constraint : schema.getAllConstraints()) {
                String string2;
                Table table;
                Constraint.Type type = constraint.getConstraintType();
                if (type == Constraint.Type.DOMAIN || this.hideTable(table = constraint.getTable(), sessionLocal) || !this.checkIndex(sessionLocal, string2 = table.getName(), value, value2)) continue;
                this.tableConstraints(sessionLocal, arrayList, string, constraint, type, table, string2);
            }
        }
    }

    private void tableConstraints(SessionLocal sessionLocal, ArrayList<Row> arrayList, String string, Constraint constraint, Constraint.Type type, Table table, String string2) {
        Index index = constraint.getIndex();
        boolean bl = type != Constraint.Type.REFERENTIAL ? true : this.database.getReferentialIntegrity() && table.getCheckForeignKeyConstraints() && constraint.getRefTable().getCheckForeignKeyConstraints();
        this.add(sessionLocal, arrayList, string, constraint.getSchema().getName(), constraint.getName(), type.getSqlName(), string, table.getSchema().getName(), string2, "NO", "NO", bl ? "YES" : "NO", type == Constraint.Type.UNIQUE ? InformationSchemaTable.nullsDistinctToString(((ConstraintUnique)constraint).getNullsDistinct()) : null, index != null ? string : null, index != null ? index.getSchema().getName() : null, index != null ? index.getName() : null, constraint.getComment());
    }

    private void tablePrivileges(SessionLocal sessionLocal, Value value, Value value2, ArrayList<Row> arrayList, String string) {
        for (Right right : this.database.getAllRights()) {
            String string2;
            Table table;
            DbObject dbObject = right.getGrantedObject();
            if (!(dbObject instanceof Table) || this.hideTable(table = (Table)dbObject, sessionLocal) || !this.checkIndex(sessionLocal, string2 = table.getName(), value, value2)) continue;
            this.addPrivileges(sessionLocal, arrayList, right.getGrantee(), string, table, null, right.getRightMask());
        }
    }

    private void triggers(SessionLocal sessionLocal, Value value, Value value2, ArrayList<Row> arrayList, String string) {
        for (Schema schema : this.database.getAllSchemas()) {
            for (TriggerObject triggerObject : schema.getAllTriggers()) {
                Table table = triggerObject.getTable();
                String string2 = table.getName();
                if (!this.checkIndex(sessionLocal, string2, value, value2)) continue;
                int n = triggerObject.getTypeMask();
                if ((n & 1) != 0) {
                    this.triggers(sessionLocal, arrayList, string, triggerObject, "INSERT", table, string2);
                }
                if ((n & 2) != 0) {
                    this.triggers(sessionLocal, arrayList, string, triggerObject, "UPDATE", table, string2);
                }
                if ((n & 4) != 0) {
                    this.triggers(sessionLocal, arrayList, string, triggerObject, "DELETE", table, string2);
                }
                if ((n & 8) == 0) continue;
                this.triggers(sessionLocal, arrayList, string, triggerObject, "SELECT", table, string2);
            }
        }
    }

    private void triggers(SessionLocal sessionLocal, ArrayList<Row> arrayList, String string, TriggerObject triggerObject, String string2, Table table, String string3) {
        Object[] objectArray = new Object[14];
        objectArray[0] = string;
        objectArray[1] = triggerObject.getSchema().getName();
        objectArray[2] = triggerObject.getName();
        objectArray[3] = string2;
        objectArray[4] = string;
        objectArray[5] = table.getSchema().getName();
        objectArray[6] = string3;
        Object object = objectArray[7] = triggerObject.isRowBased() ? "ROW" : "STATEMENT";
        objectArray[8] = triggerObject.isInsteadOf() ? "INSTEAD OF" : (triggerObject.isBefore() ? "BEFORE" : "AFTER");
        objectArray[9] = ValueBoolean.get(triggerObject.isOnRollback());
        objectArray[10] = triggerObject.getTriggerClassName();
        objectArray[11] = ValueInteger.get(triggerObject.getQueueSize());
        objectArray[12] = ValueBoolean.get(triggerObject.isNoWait());
        objectArray[13] = triggerObject.getComment();
        this.add(sessionLocal, arrayList, objectArray);
    }

    private void views(SessionLocal sessionLocal, Value value, Value value2, ArrayList<Row> arrayList, String string) {
        Object object;
        for (Schema dbObject : this.database.getAllSchemas()) {
            object = dbObject.getAllTablesAndViews(sessionLocal).iterator();
            while (object.hasNext()) {
                String string2;
                Table table = object.next();
                if (!table.isView() || !this.checkIndex(sessionLocal, string2 = table.getName(), value, value2)) continue;
                this.views(sessionLocal, arrayList, string, table, string2);
            }
        }
        for (Table table : sessionLocal.getLocalTempTables()) {
            if (!table.isView() || !this.checkIndex(sessionLocal, (String)(object = table.getName()), value, value2)) continue;
            this.views(sessionLocal, arrayList, string, table, (String)object);
        }
    }

    private void views(SessionLocal sessionLocal, ArrayList<Row> arrayList, String string, Table table, String string2) {
        String string3;
        String string4 = "VALID";
        if (table instanceof TableView) {
            TableView tableView = (TableView)table;
            string3 = tableView.getQuerySQL();
            if (tableView.isInvalid()) {
                string4 = "INVALID";
            }
        } else {
            string3 = null;
        }
        int n = 0;
        ArrayList<TriggerObject> arrayList2 = table.getTriggers();
        if (arrayList2 != null) {
            for (TriggerObject triggerObject : arrayList2) {
                if (!triggerObject.isInsteadOf()) continue;
                n |= triggerObject.getTypeMask();
            }
        }
        this.add(sessionLocal, arrayList, string, table.getSchema().getName(), string2, string3, "NONE", "NO", "NO", (n & 2) != 0 ? "YES" : "NO", (n & 4) != 0 ? "YES" : "NO", (n & 1) != 0 ? "YES" : "NO", string4, table.getComment());
    }

    private void constants(SessionLocal sessionLocal, Value value, Value value2, ArrayList<Row> arrayList, String string) {
        String string2 = this.database.getMainSchema().getName();
        String string3 = this.database.getCompareMode().getName();
        for (Schema schema : this.database.getAllSchemas()) {
            for (Constant constant : schema.getAllConstants()) {
                String string4 = constant.getName();
                if (!this.checkIndex(sessionLocal, string4, value, value2)) continue;
                this.constants(sessionLocal, arrayList, string, string2, string3, constant, string4);
            }
        }
    }

    private void constants(SessionLocal sessionLocal, ArrayList<Row> arrayList, String string, String string2, String string3, Constant constant, String string4) {
        String string5;
        String string6;
        String string7;
        String string8;
        ValueExpression valueExpression = constant.getValue();
        TypeInfo typeInfo = valueExpression.getType();
        DataTypeInformation dataTypeInformation = DataTypeInformation.valueOf(typeInfo);
        if (dataTypeInformation.hasCharsetAndCollation) {
            string8 = string;
            string7 = string2;
            string6 = CHARACTER_SET_NAME;
            string5 = string3;
        } else {
            string5 = null;
            string6 = null;
            string7 = null;
            string8 = null;
        }
        this.add(sessionLocal, arrayList, string, constant.getSchema().getName(), string4, valueExpression.getSQL(0), dataTypeInformation.dataType, dataTypeInformation.characterPrecision, dataTypeInformation.characterPrecision, string8, string7, string6, string8, string7, string5, dataTypeInformation.numericPrecision, dataTypeInformation.numericPrecisionRadix, dataTypeInformation.numericScale, dataTypeInformation.datetimePrecision, dataTypeInformation.intervalType, dataTypeInformation.intervalPrecision, dataTypeInformation.maximumCardinality, "TYPE", dataTypeInformation.declaredDataType, dataTypeInformation.declaredNumericPrecision, dataTypeInformation.declaredNumericScale, dataTypeInformation.geometryType, dataTypeInformation.geometrySrid, constant.getComment());
    }

    private void enumValues(SessionLocal sessionLocal, ArrayList<Row> arrayList, String string, String string2, String string3, String string4, String string5, TypeInfo typeInfo) {
        ExtTypeInfoEnum extTypeInfoEnum = (ExtTypeInfoEnum)typeInfo.getExtTypeInfo();
        if (extTypeInfoEnum == null) {
            return;
        }
        int n = 0;
        int n2 = sessionLocal.zeroBasedEnums() ? 0 : 1;
        int n3 = extTypeInfoEnum.getCount();
        while (n < n3) {
            this.add(sessionLocal, arrayList, string, string2, string3, string4, string5, extTypeInfoEnum.getEnumerator(n), ValueInteger.get(n2));
            ++n;
            ++n2;
        }
    }

    private void indexes(SessionLocal sessionLocal, Value value, Value value2, ArrayList<Row> arrayList, String string, boolean bl) {
        if (value != null && value.equals(value2)) {
            String string2 = value.getString();
            if (string2 == null) {
                return;
            }
            for (Schema schema : this.database.getAllSchemas()) {
                Table table = schema.getTableOrViewByName(sessionLocal, string2);
                if (table == null) continue;
                this.indexes(sessionLocal, arrayList, string, bl, table, table.getName());
            }
            Table dbObject = sessionLocal.findLocalTempTable(string2);
            if (dbObject != null) {
                this.indexes(sessionLocal, arrayList, string, bl, dbObject, dbObject.getName());
            }
        } else {
            for (Schema schema : this.database.getAllSchemas()) {
                for (Table table : schema.getAllTablesAndViews(sessionLocal)) {
                    String string2 = table.getName();
                    if (!this.checkIndex(sessionLocal, string2, value, value2)) continue;
                    this.indexes(sessionLocal, arrayList, string, bl, table, string2);
                }
            }
            for (Table table : sessionLocal.getLocalTempTables()) {
                String string3 = table.getName();
                if (!this.checkIndex(sessionLocal, string3, value, value2)) continue;
                this.indexes(sessionLocal, arrayList, string, bl, table, string3);
            }
        }
    }

    private void indexes(SessionLocal sessionLocal, ArrayList<Row> arrayList, String string, boolean bl, Table table, String string2) {
        if (this.hideTable(table, sessionLocal)) {
            return;
        }
        ArrayList<Index> arrayList2 = table.getIndexes();
        if (arrayList2 == null) {
            return;
        }
        for (Index index : arrayList2) {
            if (index.getCreateSQL() == null) continue;
            if (bl) {
                this.indexColumns(sessionLocal, arrayList, string, table, string2, index);
                continue;
            }
            this.indexes(sessionLocal, arrayList, string, table, string2, index);
        }
    }

    private void indexes(SessionLocal sessionLocal, ArrayList<Row> arrayList, String string, Table table, String string2, Index index) {
        IndexType indexType = index.getIndexType();
        this.add(sessionLocal, arrayList, string, index.getSchema().getName(), index.getName(), string, table.getSchema().getName(), string2, indexType.getSQL(false), InformationSchemaTable.nullsDistinctToString(indexType.getNullsDistinct()), ValueBoolean.get(indexType.getBelongsToConstraint()), index.getComment(), index.getClass().getName());
    }

    private void indexColumns(SessionLocal sessionLocal, ArrayList<Row> arrayList, String string, Table table, String string2, Index index) {
        IndexColumn[] indexColumnArray = index.getIndexColumns();
        int n = index.getUniqueColumnCount();
        int n2 = 0;
        int n3 = indexColumnArray.length;
        while (n2 < n3) {
            IndexColumn indexColumn = indexColumnArray[n2];
            int n4 = indexColumn.sortType;
            Object[] objectArray = new Object[11];
            objectArray[0] = string;
            objectArray[1] = index.getSchema().getName();
            objectArray[2] = index.getName();
            objectArray[3] = string;
            objectArray[4] = table.getSchema().getName();
            objectArray[5] = string2;
            objectArray[6] = indexColumn.column.getName();
            objectArray[7] = ValueInteger.get(++n2);
            Object object = objectArray[8] = (n4 & 1) == 0 ? "ASC" : "DESC";
            objectArray[9] = (n4 & 2) != 0 ? "FIRST" : ((n4 & 4) != 0 ? "LAST" : null);
            objectArray[10] = ValueBoolean.get(n2 <= n);
            this.add(sessionLocal, arrayList, objectArray);
        }
    }

    private void inDoubt(SessionLocal sessionLocal, ArrayList<Row> arrayList) {
        if (sessionLocal.getUser().isAdmin()) {
            ArrayList<InDoubtTransaction> arrayList2 = this.database.getInDoubtTransactions();
            for (InDoubtTransaction inDoubtTransaction : arrayList2) {
                this.add(sessionLocal, arrayList, inDoubtTransaction.getTransactionName(), inDoubtTransaction.getStateDescription());
            }
        }
    }

    private void locks(SessionLocal sessionLocal, ArrayList<Row> arrayList) {
        if (sessionLocal.getUser().isAdmin()) {
            for (SessionLocal sessionLocal2 : this.database.getSessions(false)) {
                this.locks(sessionLocal, arrayList, sessionLocal2);
            }
        } else {
            this.locks(sessionLocal, arrayList, sessionLocal);
        }
    }

    private void locks(SessionLocal sessionLocal, ArrayList<Row> arrayList, SessionLocal sessionLocal2) {
        for (Table table : sessionLocal2.getLocks()) {
            this.add(sessionLocal, arrayList, table.getSchema().getName(), table.getName(), ValueInteger.get(sessionLocal2.getId()), table.isLockedExclusivelyBy(sessionLocal2) ? "WRITE" : "READ");
        }
    }

    private void queryStatistics(SessionLocal sessionLocal, ArrayList<Row> arrayList) {
        QueryStatisticsData queryStatisticsData = this.database.getQueryStatisticsData();
        if (queryStatisticsData != null) {
            for (QueryStatisticsData.QueryEntry queryEntry : queryStatisticsData.getQueries()) {
                this.add(sessionLocal, arrayList, queryEntry.sqlStatement, ValueInteger.get(queryEntry.count), ValueDouble.get((double)queryEntry.executionTimeMinNanos / 1000000.0), ValueDouble.get((double)queryEntry.executionTimeMaxNanos / 1000000.0), ValueDouble.get((double)queryEntry.executionTimeCumulativeNanos / 1000000.0), ValueDouble.get(queryEntry.executionTimeMeanNanos / 1000000.0), ValueDouble.get(queryEntry.getExecutionTimeStandardDeviation() / 1000000.0), ValueBigint.get(queryEntry.rowCountMin), ValueBigint.get(queryEntry.rowCountMax), ValueBigint.get(queryEntry.rowCountCumulative), ValueDouble.get(queryEntry.rowCountMean), ValueDouble.get(queryEntry.getRowCountStandardDeviation()));
            }
        }
    }

    private void rights(SessionLocal sessionLocal, Value value, Value value2, ArrayList<Row> arrayList) {
        if (!sessionLocal.getUser().isAdmin()) {
            return;
        }
        for (Right right : this.database.getAllRights()) {
            String string;
            Role role = right.getGrantedRole();
            DbObject dbObject = right.getGrantee();
            String string2 = string = dbObject.getType() == 2 ? "USER" : "ROLE";
            if (role == null) {
                String string3;
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
                String string4 = dbObject3 != null ? dbObject3.getName() : "";
                String string5 = string3 = schema != null ? schema.getName() : "";
                if (!this.checkIndex(sessionLocal, string4, value, value2)) continue;
                this.add(sessionLocal, arrayList, this.identifier(dbObject.getName()), string, null, right.getRights(), string3, string4);
                continue;
            }
            this.add(sessionLocal, arrayList, this.identifier(dbObject.getName()), string, this.identifier(role.getName()), null, null, null);
        }
    }

    private void roles(SessionLocal sessionLocal, ArrayList<Row> arrayList) {
        boolean bl = sessionLocal.getUser().isAdmin();
        for (RightOwner rightOwner : this.database.getAllUsersAndRoles()) {
            if (!(rightOwner instanceof Role)) continue;
            Role role = (Role)rightOwner;
            if (!bl && !sessionLocal.getUser().isRoleGranted(role)) continue;
            this.add(sessionLocal, arrayList, this.identifier(role.getName()), role.getComment());
        }
    }

    private void sessions(SessionLocal sessionLocal, ArrayList<Row> arrayList) {
        if (sessionLocal.getUser().isAdmin()) {
            for (SessionLocal sessionLocal2 : this.database.getSessions(false)) {
                this.sessions(sessionLocal, arrayList, sessionLocal2);
            }
        } else {
            this.sessions(sessionLocal, arrayList, sessionLocal);
        }
    }

    private void sessions(SessionLocal sessionLocal, ArrayList<Row> arrayList, SessionLocal sessionLocal2) {
        NetworkConnectionInfo networkConnectionInfo = sessionLocal2.getNetworkConnectionInfo();
        Command command = sessionLocal2.getCurrentCommand();
        int n = sessionLocal2.getBlockingSessionId();
        User user = sessionLocal2.getUser();
        if (user == null) {
            return;
        }
        this.add(sessionLocal, arrayList, ValueInteger.get(sessionLocal2.getId()), user.getName(), networkConnectionInfo == null ? null : networkConnectionInfo.getServer(), networkConnectionInfo == null ? null : networkConnectionInfo.getClient(), networkConnectionInfo == null ? null : networkConnectionInfo.getClientInfo(), sessionLocal2.getSessionStart(), sessionLocal2.getIsolationLevel().getSQL(), command == null ? null : command.toString(), command == null ? null : sessionLocal2.getCommandStartOrEnd(), ValueBoolean.get(sessionLocal2.hasPendingTransaction()), String.valueOf((Object)sessionLocal2.getState()), n == 0 ? null : ValueInteger.get(n), sessionLocal2.getState() == SessionLocal.State.SLEEP ? sessionLocal2.getCommandStartOrEnd() : null);
    }

    private void sessionState(SessionLocal sessionLocal, ArrayList<Row> arrayList) {
        TimeZoneProvider timeZoneProvider;
        String string;
        for (String string2 : sessionLocal.getVariableNames()) {
            Value value = sessionLocal.getVariable(string2);
            StringBuilder stringBuilder = new StringBuilder().append("SET @").append(string2).append(' ');
            value.getSQL(stringBuilder, 0);
            this.add(sessionLocal, arrayList, "@" + string2, stringBuilder.toString());
        }
        for (Table table : sessionLocal.getLocalTempTables()) {
            this.add(sessionLocal, arrayList, "TABLE " + table.getName(), table.getCreateSQL());
        }
        String[] stringArray = sessionLocal.getSchemaSearchPath();
        if (stringArray != null && stringArray.length > 0) {
            StringBuilder stringBuilder = new StringBuilder("SET SCHEMA_SEARCH_PATH ");
            int n = stringArray.length;
            for (int i = 0; i < n; ++i) {
                if (i > 0) {
                    stringBuilder.append(", ");
                }
                StringUtils.quoteIdentifier(stringBuilder, (String)stringArray[i]);
            }
            this.add(sessionLocal, arrayList, "SCHEMA_SEARCH_PATH", stringBuilder.toString());
        }
        if ((string = sessionLocal.getCurrentSchemaName()) != null) {
            this.add(sessionLocal, arrayList, "SCHEMA", StringUtils.quoteIdentifier(new StringBuilder("SET SCHEMA "), string).toString());
        }
        if (!(timeZoneProvider = sessionLocal.currentTimeZone()).equals(DateTimeUtils.getTimeZone())) {
            this.add(sessionLocal, arrayList, "TIME ZONE", StringUtils.quoteStringSQL(new StringBuilder("SET TIME ZONE "), timeZoneProvider.getId()).toString());
        }
    }

    private void settings(SessionLocal sessionLocal, ArrayList<Row> arrayList) {
        Object object = this.database.getAllSettings().iterator();
        while (object.hasNext()) {
            Setting objectArray = object.next();
            String string3 = objectArray.getStringValue();
            if (string3 == null) {
                string3 = Integer.toString(objectArray.getIntValue());
            }
            this.add(sessionLocal, arrayList, this.identifier(objectArray.getName()), string3);
        }
        this.add(sessionLocal, arrayList, "info.BUILD_ID", "224");
        this.add(sessionLocal, arrayList, "info.VERSION_MAJOR", "2");
        this.add(sessionLocal, arrayList, "info.VERSION_MINOR", "2");
        this.add(sessionLocal, arrayList, "info.VERSION", Constants.FULL_VERSION);
        if (sessionLocal.getUser().isAdmin()) {
            String[] stringArray = object = new String[]{"java.runtime.version", "java.vm.name", "java.vendor", "os.name", "os.arch", "os.version", "sun.os.patch.level", "file.separator", "path.separator", "line.separator", "user.country", "user.language", "user.variant", "file.encoding"};
            int n = stringArray.length;
            for (int i = 0; i < n; ++i) {
                String string4 = stringArray[i];
                this.add(sessionLocal, arrayList, "property." + string4, Utils.getProperty(string4, ""));
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
        this.add(sessionLocal, arrayList, "WRITE_DELAY", Integer.toString(this.database.getWriteDelay()));
        for (Map.Entry<String, String> entry : this.database.getSettings().getSortedSettings()) {
            this.add(sessionLocal, arrayList, entry.getKey(), entry.getValue());
        }
        this.database.getStore().getMvStore().populateInfo((string, string2) -> this.add(sessionLocal, arrayList, string, string2));
    }

    private void synonyms(SessionLocal sessionLocal, ArrayList<Row> arrayList, String string) {
        for (TableSynonym tableSynonym : this.database.getAllSynonyms()) {
            this.add(sessionLocal, arrayList, string, tableSynonym.getSchema().getName(), tableSynonym.getName(), tableSynonym.getSynonymForName(), tableSynonym.getSynonymForSchema().getName(), "SYNONYM", "VALID", tableSynonym.getComment());
        }
    }

    private void users(SessionLocal sessionLocal, ArrayList<Row> arrayList) {
        User user = sessionLocal.getUser();
        if (user.isAdmin()) {
            for (RightOwner rightOwner : this.database.getAllUsersAndRoles()) {
                if (!(rightOwner instanceof User)) continue;
                this.users(sessionLocal, arrayList, (User)rightOwner);
            }
        } else {
            this.users(sessionLocal, arrayList, user);
        }
    }

    private void users(SessionLocal sessionLocal, ArrayList<Row> arrayList, User user) {
        this.add(sessionLocal, arrayList, this.identifier(user.getName()), ValueBoolean.get(user.isAdmin()), user.getComment());
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
            this.add(sessionLocal, arrayList, null, this.identifier(dbObject.getName()), string, table.getSchema().getName(), table.getName(), string3, string4, "NO");
        } else {
            this.add(sessionLocal, arrayList, null, this.identifier(dbObject.getName()), string, table.getSchema().getName(), table.getName(), string2, string3, string4);
        }
    }

    private static String nullsDistinctToString(NullsDistinct nullsDistinct) {
        if (nullsDistinct != null) {
            switch (nullsDistinct) {
                case DISTINCT: {
                    return "YES";
                }
                case ALL_DISTINCT: {
                    return "ALL";
                }
                case NOT_DISTINCT: {
                    return "NO";
                }
            }
        }
        return null;
    }

    @Override
    public long getMaxDataModificationId() {
        switch (this.type) {
            case 15: 
            case 25: 
            case 26: 
            case 30: 
            case 31: 
            case 32: {
                return Long.MAX_VALUE;
            }
        }
        return this.database.getModificationDataId();
    }

    @Override
    public boolean isView() {
        return this.isView;
    }

    @Override
    public long getRowCount(SessionLocal sessionLocal) {
        return this.getRowCount(sessionLocal, false);
    }

    @Override
    public long getRowCountApproximation(SessionLocal sessionLocal) {
        return this.getRowCount(sessionLocal, true);
    }

    private long getRowCount(SessionLocal sessionLocal, boolean bl) {
        switch (this.type) {
            case 0: {
                return 1L;
            }
            case 2: {
                Locale[] localeArray = CompareMode.getCollationLocales(bl);
                if (localeArray == null) break;
                return localeArray.length + 1;
            }
            case 14: {
                return sessionLocal.getDatabase().getAllSchemas().size();
            }
            case 25: {
                if (sessionLocal.getUser().isAdmin()) {
                    return sessionLocal.getDatabase().getInDoubtTransactions().size();
                }
                return 0L;
            }
            case 29: {
                if (!sessionLocal.getUser().isAdmin()) break;
                long l = 0L;
                for (RightOwner rightOwner : sessionLocal.getDatabase().getAllUsersAndRoles()) {
                    if (!(rightOwner instanceof Role)) continue;
                    ++l;
                }
                return l;
            }
            case 30: {
                if (sessionLocal.getUser().isAdmin()) {
                    return sessionLocal.getDatabase().getSessionCount();
                }
                return 1L;
            }
            case 34: {
                if (sessionLocal.getUser().isAdmin()) {
                    long l = 0L;
                    for (RightOwner rightOwner : sessionLocal.getDatabase().getAllUsersAndRoles()) {
                        if (!(rightOwner instanceof User)) continue;
                        ++l;
                    }
                    return l;
                }
                return 1L;
            }
        }
        if (bl) {
            return 1000L;
        }
        throw DbException.getInternalError(this.toString());
    }

    @Override
    public boolean canGetRowCount(SessionLocal sessionLocal) {
        switch (this.type) {
            case 0: 
            case 2: 
            case 14: 
            case 25: 
            case 30: 
            case 34: {
                return true;
            }
            case 29: {
                if (!sessionLocal.getUser().isAdmin()) break;
                return true;
            }
        }
        return false;
    }

    static final class DataTypeInformation {
        static final DataTypeInformation NULL = new DataTypeInformation(null, null, null, null, null, null, null, null, null, false, null, null, null, null, null);
        final String dataType;
        final Value characterPrecision;
        final Value numericPrecision;
        final Value numericPrecisionRadix;
        final Value numericScale;
        final Value datetimePrecision;
        final Value intervalPrecision;
        final Value intervalType;
        final Value maximumCardinality;
        final boolean hasCharsetAndCollation;
        final String declaredDataType;
        final Value declaredNumericPrecision;
        final Value declaredNumericScale;
        final String geometryType;
        final Value geometrySrid;

        static DataTypeInformation valueOf(TypeInfo typeInfo) {
            int n = typeInfo.getValueType();
            String string = Value.getTypeName(n);
            ValueBigint valueBigint = null;
            ValueInteger valueInteger = null;
            ValueInteger valueInteger2 = null;
            ValueInteger valueInteger3 = null;
            ValueInteger valueInteger4 = null;
            ValueInteger valueInteger5 = null;
            ValueInteger valueInteger6 = null;
            String string2 = null;
            boolean bl = false;
            String string3 = null;
            ValueInteger valueInteger7 = null;
            ValueInteger valueInteger8 = null;
            String string4 = null;
            ValueInteger valueInteger9 = null;
            switch (n) {
                case 1: 
                case 2: 
                case 3: 
                case 4: {
                    bl = true;
                }
                case 5: 
                case 6: 
                case 7: 
                case 35: 
                case 38: {
                    valueBigint = ValueBigint.get(typeInfo.getPrecision());
                    break;
                }
                case 9: 
                case 10: 
                case 11: 
                case 12: {
                    valueInteger = ValueInteger.get(MathUtils.convertLongToInt(typeInfo.getPrecision()));
                    valueInteger2 = ValueInteger.get(0);
                    valueInteger3 = ValueInteger.get(2);
                    string3 = string;
                    break;
                }
                case 13: {
                    valueInteger = ValueInteger.get(MathUtils.convertLongToInt(typeInfo.getPrecision()));
                    valueInteger2 = ValueInteger.get(typeInfo.getScale());
                    valueInteger3 = ValueInteger.get(10);
                    String string5 = string3 = typeInfo.getExtTypeInfo() != null ? "DECIMAL" : "NUMERIC";
                    if (typeInfo.getDeclaredPrecision() >= 0L) {
                        valueInteger7 = valueInteger;
                    }
                    if (typeInfo.getDeclaredScale() < 0) break;
                    valueInteger8 = valueInteger2;
                    break;
                }
                case 14: 
                case 15: {
                    valueInteger = ValueInteger.get(MathUtils.convertLongToInt(typeInfo.getPrecision()));
                    valueInteger3 = ValueInteger.get(2);
                    long l = typeInfo.getDeclaredPrecision();
                    if (l >= 0L) {
                        string3 = "FLOAT";
                        if (l <= 0L) break;
                        valueInteger7 = ValueInteger.get((int)l);
                        break;
                    }
                    string3 = string;
                    break;
                }
                case 16: {
                    valueInteger = ValueInteger.get(MathUtils.convertLongToInt(typeInfo.getPrecision()));
                    valueInteger3 = ValueInteger.get(10);
                    string3 = string;
                    if (typeInfo.getDeclaredPrecision() < 0L) break;
                    valueInteger7 = valueInteger;
                    break;
                }
                case 22: 
                case 23: 
                case 24: 
                case 25: 
                case 26: 
                case 27: 
                case 28: 
                case 29: 
                case 30: 
                case 31: 
                case 32: 
                case 33: 
                case 34: {
                    string2 = IntervalQualifier.valueOf(n - 22).toString();
                    string = "INTERVAL";
                    valueInteger5 = ValueInteger.get(MathUtils.convertLongToInt(typeInfo.getPrecision()));
                }
                case 17: 
                case 18: 
                case 19: 
                case 20: 
                case 21: {
                    valueInteger4 = ValueInteger.get(typeInfo.getScale());
                    break;
                }
                case 37: {
                    Integer n2;
                    ExtTypeInfoGeometry extTypeInfoGeometry = (ExtTypeInfoGeometry)typeInfo.getExtTypeInfo();
                    if (extTypeInfoGeometry == null) break;
                    int n3 = extTypeInfoGeometry.getType();
                    if (n3 != 0) {
                        string4 = EWKTUtils.formatGeometryTypeAndDimensionSystem(new StringBuilder(), n3).toString();
                    }
                    if ((n2 = extTypeInfoGeometry.getSrid()) == null) break;
                    valueInteger9 = ValueInteger.get(n2);
                    break;
                }
                case 40: {
                    valueInteger6 = ValueInteger.get(MathUtils.convertLongToInt(typeInfo.getPrecision()));
                }
            }
            return new DataTypeInformation(string, valueBigint, valueInteger, valueInteger3, valueInteger2, valueInteger4, valueInteger5, string2 != null ? ValueVarchar.get(string2) : ValueNull.INSTANCE, valueInteger6, bl, string3, valueInteger7, valueInteger8, string4, valueInteger9);
        }

        private DataTypeInformation(String string, Value value, Value value2, Value value3, Value value4, Value value5, Value value6, Value value7, Value value8, boolean bl, String string2, Value value9, Value value10, String string3, Value value11) {
            this.dataType = string;
            this.characterPrecision = value;
            this.numericPrecision = value2;
            this.numericPrecisionRadix = value3;
            this.numericScale = value4;
            this.datetimePrecision = value5;
            this.intervalPrecision = value6;
            this.intervalType = value7;
            this.maximumCardinality = value8;
            this.hasCharsetAndCollation = bl;
            this.declaredDataType = string2;
            this.declaredNumericPrecision = value9;
            this.declaredNumericScale = value10;
            this.geometryType = string3;
            this.geometrySrid = value11;
        }
    }
}

