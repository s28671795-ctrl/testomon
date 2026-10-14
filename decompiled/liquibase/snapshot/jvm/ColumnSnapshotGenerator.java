/*
 * Decompiled with CFR 0.152.
 */
package liquibase.snapshot.jvm;

import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import liquibase.Scope;
import liquibase.database.AbstractJdbcDatabase;
import liquibase.database.Database;
import liquibase.database.OfflineConnection;
import liquibase.database.core.AbstractDb2Database;
import liquibase.database.core.FirebirdDatabase;
import liquibase.database.core.MSSQLDatabase;
import liquibase.database.core.MySQLDatabase;
import liquibase.database.core.OracleDatabase;
import liquibase.database.core.PostgresDatabase;
import liquibase.database.core.SybaseASADatabase;
import liquibase.exception.DatabaseException;
import liquibase.executor.ExecutorService;
import liquibase.logging.Logger;
import liquibase.snapshot.CachedRow;
import liquibase.snapshot.DatabaseSnapshot;
import liquibase.snapshot.JdbcDatabaseSnapshot;
import liquibase.snapshot.jvm.ColumnAutoIncrementService;
import liquibase.snapshot.jvm.JdbcSnapshotGenerator;
import liquibase.statement.DatabaseFunction;
import liquibase.statement.core.RawSqlStatement;
import liquibase.structure.DatabaseObject;
import liquibase.structure.core.Column;
import liquibase.structure.core.DataType;
import liquibase.structure.core.Relation;
import liquibase.structure.core.Schema;
import liquibase.structure.core.Table;
import liquibase.structure.core.View;
import liquibase.util.BooleanUtil;
import liquibase.util.SqlUtil;
import liquibase.util.StringUtil;

public class ColumnSnapshotGenerator
extends JdbcSnapshotGenerator {
    private static final String LIQUIBASE_COMPLETE = "liquibase-complete";
    protected static final String COLUMN_DEF_COL = "COLUMN_DEF";
    private static final String POSTGRES_STRING_VALUE_REGEX = "'(.*)'::[\\w .]+";
    private static final Pattern POSTGRES_STRING_VALUE_PATTERN = Pattern.compile("'(.*)'::[\\w .]+");
    private static final String POSTGRES_NUMBER_VALUE_REGEX = "\\(?(\\d*)\\)?::[\\w .]+";
    private static final Pattern POSTGRES_NUMBER_VALUE_PATTERN = Pattern.compile("\\(?(\\d*)\\)?::[\\w .]+");
    private static final String MYSQL_DEFAULT_GENERATED = "DEFAULT_GENERATED";
    private static final String GENERATED_ALWAYS_AS = "GENERATED ALWAYS AS ";
    private static final String YES_VALUE = "YES";
    private static final String IS_GENERATED_COLUMN = "IS_GENERATEDCOLUMN";
    private final ColumnAutoIncrementService columnAutoIncrementService = new ColumnAutoIncrementService();

    public ColumnSnapshotGenerator() {
        super(Column.class, new Class[]{Table.class, View.class});
    }

    @Override
    protected DatabaseObject snapshotObject(DatabaseObject example, DatabaseSnapshot snapshot) throws DatabaseException {
        if (BooleanUtil.isTrue(((Column)example).getComputed()) || BooleanUtil.isTrue(((Column)example).getDescending())) {
            return example;
        }
        Database database = snapshot.getDatabase();
        Relation relation = ((Column)example).getRelation();
        Schema schema = relation.getSchema();
        try {
            Column column = null;
            if (example.getAttribute(LIQUIBASE_COMPLETE, false).booleanValue()) {
                column = (Column)example;
                example.setAttribute(LIQUIBASE_COMPLETE, null);
                return column;
            }
            String catalogName = ((AbstractJdbcDatabase)database).getJdbcCatalogName(schema);
            String schemaName = ((AbstractJdbcDatabase)database).getJdbcSchemaName(schema);
            String tableName = relation.getName();
            String columnName = example.getName();
            JdbcDatabaseSnapshot.CachingDatabaseMetaData databaseMetaData = ((JdbcDatabaseSnapshot)snapshot).getMetaDataFromCache();
            List<CachedRow> metaDataColumns = databaseMetaData.getColumns(catalogName, schemaName, tableName, columnName);
            List<CachedRow> metaDataNotNullConst = databaseMetaData.getNotNullConst(catalogName, schemaName, tableName);
            if (!metaDataColumns.isEmpty()) {
                CachedRow data = metaDataColumns.get(0);
                column = this.readColumn(data, relation, database);
                this.setAutoIncrementDetails(column, database, snapshot);
                this.populateValidateNullableIfNeeded(column, metaDataNotNullConst, database);
            }
            example.setAttribute(LIQUIBASE_COMPLETE, null);
            if (column == null && database instanceof PostgresDatabase && this.looksLikeFunction(example.getName())) {
                ((Column)example).setComputed(true);
                return example;
            }
            return column;
        }
        catch (SQLException | DatabaseException e) {
            throw new DatabaseException(e);
        }
    }

    private void populateValidateNullableIfNeeded(Column column, List<CachedRow> metaDataNotNullConst, Database database) {
        if (!(database instanceof OracleDatabase)) {
            return;
        }
        String name = column.getName();
        for (CachedRow cachedRow : metaDataNotNullConst) {
            Object columnNameObj = cachedRow.get("COLUMN_NAME");
            if (columnNameObj == null) {
                throw new AssertionError((Object)"Please check query to fetch data for notNullConst!. I didn't fetch needed data");
            }
            if (!name.equalsIgnoreCase(columnNameObj.toString())) continue;
            String VALIDATE = "VALIDATED";
            Object validated = cachedRow.get("VALIDATED");
            if (validated == null) break;
            String searchCondition = cachedRow.getString("SEARCH_CONDITION");
            searchCondition = searchCondition == null ? "" : searchCondition.toUpperCase();
            String nullable = cachedRow.getString("NULLABLE");
            String constraintName = cachedRow.getString("CONSTRAINT_NAME");
            if ("NOT VALIDATED".equalsIgnoreCase(validated.toString()) && "Y".equalsIgnoreCase(nullable) && searchCondition.matches("\"?\\w+\" IS NOT NULL")) {
                column.setNullable(false);
                column.setValidateNullable(false);
            }
            if (!Boolean.FALSE.equals(column.isNullable()) || !ColumnSnapshotGenerator.hasValidObjectName(constraintName)) continue;
            column.setAttribute("notNullConstraintName", constraintName);
        }
    }

    private static boolean hasValidObjectName(String objectName) {
        if (StringUtil.isEmpty(objectName)) {
            return false;
        }
        return !objectName.startsWith("SYS_") && !objectName.startsWith("BIN$");
    }

    @Override
    protected void addTo(DatabaseObject foundObject, DatabaseSnapshot snapshot) throws DatabaseException {
        if (!snapshot.getSnapshotControl().shouldInclude(Column.class) || !snapshot.getDatabase().supports(Column.class)) {
            return;
        }
        if (foundObject instanceof Relation) {
            Database database = snapshot.getDatabase();
            Relation relation = (Relation)foundObject;
            try {
                JdbcDatabaseSnapshot.CachingDatabaseMetaData databaseMetaData = ((JdbcDatabaseSnapshot)snapshot).getMetaDataFromCache();
                Schema schema = relation.getSchema();
                List<CachedRow> allColumnsMetadataRs = databaseMetaData.getColumns(((AbstractJdbcDatabase)database).getJdbcCatalogName(schema), ((AbstractJdbcDatabase)database).getJdbcSchemaName(schema), relation.getName(), null);
                List<CachedRow> metaDataNotNullConst = databaseMetaData.getNotNullConst(schema.getCatalogName(), schema.getName(), relation.getName());
                TreeMap<Integer, CachedRow> treeSet = new TreeMap<Integer, CachedRow>();
                for (CachedRow row : allColumnsMetadataRs) {
                    treeSet.put(row.getInt("ORDINAL_POSITION"), row);
                }
                Logger log = Scope.getCurrentScope().getLog(this.getClass());
                int currentOrdinal = 0;
                for (CachedRow row : treeSet.values()) {
                    int rsOrdinal = row.getInt("ORDINAL_POSITION");
                    if (rsOrdinal == ++currentOrdinal) continue;
                    log.fine(String.format("Repairing ORDINAL_POSITION with gaps for table=%s, column name=%s, bad ordinal=%d, new ordinal=%d", relation.getName(), row.getString("COLUMN_NAME"), rsOrdinal, currentOrdinal));
                    row.set("ORDINAL_POSITION", currentOrdinal);
                }
                for (CachedRow row : allColumnsMetadataRs) {
                    Column column = this.readColumn(row, relation, database);
                    this.setAutoIncrementDetails(column, database, snapshot);
                    this.populateValidateNullableIfNeeded(column, metaDataNotNullConst, database);
                    column.setAttribute(LIQUIBASE_COMPLETE, true);
                    relation.getColumns().add(column);
                }
            }
            catch (SQLException e) {
                throw new DatabaseException(e);
            }
        }
    }

    protected void setAutoIncrementDetails(Column column, Database database, DatabaseSnapshot snapshot) {
        Column.AutoIncrementInformation autoIncrementInformation;
        if (column.getAutoIncrementInformation() != null && database.getConnection() != null && !(database.getConnection() instanceof OfflineConnection) && column.getRelation() != null && column.getSchema() != null && (autoIncrementInformation = this.columnAutoIncrementService.obtainSequencesInformation(database, column.getSchema(), snapshot).get(String.format("%s.%s.%s", column.getSchema().getName(), column.getRelation().getName(), column.getName()))) != null) {
            column.setAutoIncrementInformation(autoIncrementInformation);
        }
    }

    protected Column readColumn(CachedRow columnMetadataResultSet, Relation table, Database database) throws SQLException, DatabaseException {
        Object nullable;
        String rawTableName = (String)columnMetadataResultSet.get("TABLE_NAME");
        String rawColumnName = (String)columnMetadataResultSet.get("COLUMN_NAME");
        String rawSchemaName = StringUtil.trimToNull((String)columnMetadataResultSet.get("TABLE_SCHEM"));
        String rawCatalogName = StringUtil.trimToNull((String)columnMetadataResultSet.get("TABLE_CAT"));
        String remarks = StringUtil.trimToNull((String)columnMetadataResultSet.get("REMARKS"));
        if (remarks != null) {
            remarks = remarks.replace("''", "'");
        }
        Integer position = columnMetadataResultSet.getInt("ORDINAL_POSITION");
        Column column = new Column();
        column.setName(StringUtil.trimToNull(rawColumnName));
        column.setRelation(table);
        column.setRemarks(remarks);
        column.setOrder(position);
        Boolean isComputed = columnMetadataResultSet.getBoolean("IS_COMPUTED");
        if (isComputed != null) {
            column.setComputed(isComputed);
        }
        if (columnMetadataResultSet.get("IS_FILESTREAM") != null && ((Boolean)columnMetadataResultSet.get("IS_FILESTREAM")).booleanValue()) {
            column.setAttribute("fileStream", true);
        }
        if (columnMetadataResultSet.get("IS_ROWGUIDCOL") != null && ((Boolean)columnMetadataResultSet.get("IS_ROWGUIDCOL")).booleanValue()) {
            column.setAttribute("rowGuid", true);
        }
        if (database instanceof OracleDatabase) {
            nullable = columnMetadataResultSet.getString("NULLABLE");
            if ("Y".equals(nullable)) {
                column.setNullable(true);
            } else {
                column.setNullable(false);
            }
        } else {
            nullable = columnMetadataResultSet.getInt("NULLABLE");
            if (nullable != null) {
                if ((Integer)nullable == 0) {
                    column.setNullable(false);
                } else if ((Integer)nullable == 1) {
                    column.setNullable(true);
                } else if ((Integer)nullable == 2) {
                    Scope.getCurrentScope().getLog(this.getClass()).info("Unknown nullable state for column " + column + ". Assuming nullable");
                    column.setNullable(true);
                }
            }
        }
        if (database.supportsAutoIncrement() && table instanceof Table) {
            column = this.columnAutoIncrementService.enableColumnAutoIncrementIfAvailable(column, database, columnMetadataResultSet, rawCatalogName, rawSchemaName, rawTableName, rawColumnName);
        }
        DataType type = this.readDataType(columnMetadataResultSet, column, database);
        column.setType(type);
        Object defaultValue = this.readDefaultValue(columnMetadataResultSet, column, database);
        if (defaultValue instanceof DatabaseFunction && ((DatabaseFunction)defaultValue).getValue().matches("\\w+")) {
            defaultValue = new DatabaseFunction(((DatabaseFunction)defaultValue).getValue().toUpperCase());
        }
        column.setDefaultValue(defaultValue);
        column.setDefaultValueConstraintName(columnMetadataResultSet.getString("COLUMN_DEF_NAME"));
        return column;
    }

    protected DataType readDataType(CachedRow columnMetadataResultSet, Column column, Database database) throws DatabaseException {
        if (database instanceof OracleDatabase) {
            String dataType = columnMetadataResultSet.getString("DATA_TYPE_NAME");
            dataType = dataType.replace("VARCHAR2", "VARCHAR");
            dataType = dataType.replace("NVARCHAR2", "NVARCHAR");
            DataType type = new DataType(dataType);
            type.setDataTypeId(columnMetadataResultSet.getInt("DATA_TYPE"));
            if (dataType.equalsIgnoreCase("NUMBER")) {
                type.setColumnSize(columnMetadataResultSet.getInt("DATA_PRECISION"));
                type.setDecimalDigits(columnMetadataResultSet.getInt("DATA_SCALE"));
            } else {
                if ("FLOAT".equalsIgnoreCase(dataType)) {
                    type.setColumnSize(columnMetadataResultSet.getInt("DATA_PRECISION"));
                } else {
                    type.setColumnSize(columnMetadataResultSet.getInt("DATA_LENGTH"));
                }
                boolean isTimeStampDataType = dataType.toUpperCase().contains("TIMESTAMP");
                if (isTimeStampDataType || dataType.equalsIgnoreCase("NCLOB") || dataType.equalsIgnoreCase("BLOB") || dataType.equalsIgnoreCase("CLOB")) {
                    type.setColumnSize(null);
                } else if (dataType.equalsIgnoreCase("NVARCHAR") || dataType.equalsIgnoreCase("NCHAR")) {
                    type.setColumnSize(columnMetadataResultSet.getInt("CHAR_LENGTH"));
                    type.setColumnSizeUnit(DataType.ColumnSizeUnit.CHAR);
                } else {
                    String charUsed = columnMetadataResultSet.getString("CHAR_USED");
                    DataType.ColumnSizeUnit unit = null;
                    if ("C".equals(charUsed)) {
                        unit = DataType.ColumnSizeUnit.CHAR;
                        type.setColumnSize(columnMetadataResultSet.getInt("CHAR_LENGTH"));
                    } else if ("B".equals(charUsed)) {
                        unit = DataType.ColumnSizeUnit.BYTE;
                    }
                    type.setColumnSizeUnit(unit);
                }
            }
            return type;
        }
        String columnTypeName = (String)columnMetadataResultSet.get("TYPE_NAME");
        if (database instanceof MSSQLDatabase) {
            if ("numeric() identity".equalsIgnoreCase(columnTypeName)) {
                columnTypeName = "numeric";
            } else if ("decimal() identity".equalsIgnoreCase(columnTypeName)) {
                columnTypeName = "decimal";
            } else if ("xml".equalsIgnoreCase(columnTypeName)) {
                columnMetadataResultSet.set("COLUMN_SIZE", null);
                columnMetadataResultSet.set("DECIMAL_DIGITS", null);
            } else if ("datetimeoffset".equalsIgnoreCase(columnTypeName) || "time".equalsIgnoreCase(columnTypeName)) {
                columnMetadataResultSet.set("COLUMN_SIZE", columnMetadataResultSet.getInt("DECIMAL_DIGITS"));
                columnMetadataResultSet.set("DECIMAL_DIGITS", null);
            }
        } else if (database instanceof PostgresDatabase && (columnTypeName = database.unescapeDataTypeName(columnTypeName)).startsWith("_")) {
            columnTypeName = columnTypeName.replaceFirst("_", "").concat("[]");
        }
        if (database instanceof FirebirdDatabase) {
            if ("BLOB SUB_TYPE 0".equals(columnTypeName)) {
                columnTypeName = "BLOB";
            }
            if ("BLOB SUB_TYPE 1".equals(columnTypeName)) {
                columnTypeName = "CLOB";
            }
        }
        if (database instanceof MySQLDatabase && "JSON".equalsIgnoreCase(columnTypeName)) {
            return new DataType("JSON");
        }
        if (database instanceof MySQLDatabase && ("ENUM".equalsIgnoreCase(columnTypeName) || "SET".equalsIgnoreCase(columnTypeName))) {
            try {
                String enumValue = Scope.getCurrentScope().getSingleton(ExecutorService.class).getExecutor("jdbc", database).queryForObject(new RawSqlStatement("SELECT COLUMN_TYPE FROM INFORMATION_SCHEMA.COLUMNS\nWHERE TABLE_SCHEMA = '" + column.getSchema().getName() + "'\nAND TABLE_NAME = '" + column.getRelation().getName() + "'\nAND COLUMN_NAME = '" + column.getName() + "'"), String.class);
                enumValue = enumValue.replace("enum(", "ENUM(");
                enumValue = enumValue.replace("set(", "SET(");
                return new DataType(enumValue);
            }
            catch (DatabaseException e) {
                Scope.getCurrentScope().getLog(this.getClass()).warning("Error fetching enum values", e);
            }
        }
        DataType.ColumnSizeUnit columnSizeUnit = DataType.ColumnSizeUnit.BYTE;
        if (database instanceof SybaseASADatabase && (columnMetadataResultSet.getInt("DATA_TYPE") == 12 || columnMetadataResultSet.getInt("DATA_TYPE") == 1) && columnMetadataResultSet.getInt("scale") == 1) {
            columnSizeUnit = DataType.ColumnSizeUnit.CHAR;
        }
        int dataType = columnMetadataResultSet.getInt("DATA_TYPE");
        Integer columnSize = null;
        Integer decimalDigits = null;
        if (!database.dataTypeIsNotModifiable(columnTypeName)) {
            columnSize = columnMetadataResultSet.getInt("COLUMN_SIZE");
            decimalDigits = columnMetadataResultSet.getInt("DECIMAL_DIGITS");
            if (decimalDigits != null && decimalDigits.equals(0) && (dataType != 92 || !(database instanceof PostgresDatabase))) {
                decimalDigits = null;
            }
        }
        Integer radix = columnMetadataResultSet.getInt("NUM_PREC_RADIX");
        Integer characterOctetLength = columnMetadataResultSet.getInt("CHAR_OCTET_LENGTH");
        if (database instanceof AbstractDb2Database) {
            String typeName = columnMetadataResultSet.getString("TYPE_NAME");
            if (("DBCLOB".equalsIgnoreCase(typeName) || "GRAPHIC".equalsIgnoreCase(typeName) || "VARGRAPHIC".equalsIgnoreCase(typeName)) && columnSize != null) {
                columnSize = columnSize / 2;
            }
            if ("TIMESTAMP".equalsIgnoreCase(columnTypeName) && decimalDigits == null) {
                columnTypeName = "DATE";
                dataType = 91;
            }
        }
        if (database instanceof PostgresDatabase && columnSize != null) {
            if (columnSize.equals(Integer.MAX_VALUE)) {
                columnSize = null;
            } else if (columnTypeName.equalsIgnoreCase("numeric") && columnSize.equals(0)) {
                columnSize = null;
            }
        }
        if (database instanceof SybaseASADatabase && ("LONG BINARY".equalsIgnoreCase(columnTypeName) || "LONG VARCHAR".equalsIgnoreCase(columnTypeName) || "LONG NVARCHAR".equalsIgnoreCase(columnTypeName))) {
            columnSize = null;
        }
        DataType type = new DataType(columnTypeName);
        type.setDataTypeId(dataType);
        int jdbcType = columnMetadataResultSet.getInt("DATA_TYPE");
        if (jdbcType == 93 || jdbcType == 2014 || database instanceof SybaseASADatabase && "timestamp with time zone".equalsIgnoreCase(columnTypeName)) {
            if (decimalDigits == null) {
                type.setColumnSize(null);
            } else {
                type.setColumnSize(decimalDigits.intValue() != database.getDefaultFractionalDigitsForTimestamp() ? decimalDigits : null);
            }
            type.setDecimalDigits(null);
        } else {
            type.setColumnSize(columnSize);
            type.setDecimalDigits(decimalDigits);
        }
        type.setRadix(radix);
        type.setCharacterOctetLength(characterOctetLength);
        type.setColumnSizeUnit(columnSizeUnit);
        return type;
    }

    protected Object readDefaultValue(CachedRow columnMetadataResultSet, Column columnInfo, Database database) {
        Object virtColumnDef;
        Object defaultValue;
        if (database instanceof MSSQLDatabase && "(NULL)".equals(defaultValue = columnMetadataResultSet.get(COLUMN_DEF_COL))) {
            columnMetadataResultSet.set(COLUMN_DEF_COL, new DatabaseFunction("null"));
        }
        if (database instanceof OracleDatabase && columnMetadataResultSet.get(COLUMN_DEF_COL) == null) {
            String lowerCaseDefaultValue;
            Object defaultValue2;
            columnMetadataResultSet.set(COLUMN_DEF_COL, columnMetadataResultSet.get("DATA_DEFAULT"));
            if (columnMetadataResultSet.get(COLUMN_DEF_COL) != null && StringUtil.equalsWordNull((String)columnMetadataResultSet.get(COLUMN_DEF_COL))) {
                columnMetadataResultSet.set(COLUMN_DEF_COL, null);
            }
            Object columnDef = columnMetadataResultSet.get(COLUMN_DEF_COL);
            if ("CHAR".equalsIgnoreCase(columnInfo.getType().getTypeName()) && columnDef instanceof String && !((String)columnDef).startsWith("'") && !((String)columnDef).endsWith("'")) {
                return new DatabaseFunction((String)columnDef);
            }
            if (YES_VALUE.equals(columnMetadataResultSet.get("VIRTUAL_COLUMN")) && (virtColumnDef = columnMetadataResultSet.get(COLUMN_DEF_COL)) != null && !StringUtil.equalsWordNull(virtColumnDef.toString())) {
                columnMetadataResultSet.set(COLUMN_DEF_COL, "GENERATED ALWAYS AS (" + virtColumnDef.toString().replace("\"", "") + ")");
            }
            if ((defaultValue2 = columnMetadataResultSet.get(COLUMN_DEF_COL)) instanceof String && (lowerCaseDefaultValue = ((String)defaultValue2).toLowerCase()).contains("iseq$$") && lowerCaseDefaultValue.endsWith(".nextval")) {
                columnMetadataResultSet.set(COLUMN_DEF_COL, null);
            }
        }
        if (database instanceof PostgresDatabase) {
            this.readDefaultValueForPostgresDatabase(columnMetadataResultSet, columnInfo);
        }
        if (database instanceof MySQLDatabase) {
            this.readDefaultValueForMysqlDatabase(columnMetadataResultSet, columnInfo, database);
        }
        if (database instanceof AbstractDb2Database && columnMetadataResultSet.get(COLUMN_DEF_COL) != null && StringUtil.equalsWordNull((String)columnMetadataResultSet.get(COLUMN_DEF_COL))) {
            columnMetadataResultSet.set(COLUMN_DEF_COL, null);
        }
        if (database instanceof SybaseASADatabase) {
            defaultValue = (String)columnMetadataResultSet.get(COLUMN_DEF_COL);
            defaultValue = ((String)defaultValue).replaceAll("(?i)\\bCURRENT\\s+DATE\\b", "{fn CURDATE()}");
            defaultValue = ((String)defaultValue).replaceAll("(?i)\\bCURRENT\\s+TIME\\b", "{fn CURTIME()}");
            defaultValue = ((String)defaultValue).replaceAll("(?i)\\bCURRENT\\s+TIMESTAMP\\b", "{fn NOW()}");
            defaultValue = ((String)defaultValue).replaceAll("(?i)\\bCURRENT\\s+USER\\b", "{fn USER()}");
            columnMetadataResultSet.set(COLUMN_DEF_COL, defaultValue);
            if (YES_VALUE.equals(columnMetadataResultSet.get(IS_GENERATED_COLUMN)) && (virtColumnDef = columnMetadataResultSet.get(COLUMN_DEF_COL)) != null && !StringUtil.equalsWordNull(virtColumnDef.toString())) {
                columnMetadataResultSet.set(COLUMN_DEF_COL, "COMPUTE (" + virtColumnDef + ")");
            }
        }
        return SqlUtil.parseValue(database, columnMetadataResultSet.get(COLUMN_DEF_COL), columnInfo.getType());
    }

    private void readDefaultValueForMysqlDatabase(CachedRow columnMetadataResultSet, Column column, Database database) {
        try {
            String extraValue = Scope.getCurrentScope().getSingleton(ExecutorService.class).getExecutor("jdbc", database).queryForObject(new RawSqlStatement("SELECT EXTRA FROM INFORMATION_SCHEMA.COLUMNS\nWHERE TABLE_SCHEMA = '" + column.getSchema().getName() + "'\nAND TABLE_NAME = '" + column.getRelation().getName() + "'\nAND COLUMN_NAME = '" + column.getName() + "'"), String.class);
            if (extraValue != null && !extraValue.isEmpty() && (extraValue.startsWith("DEFAULT_GENERATED ") || extraValue.toLowerCase(Locale.ENGLISH).contains("on update"))) {
                columnMetadataResultSet.set(COLUMN_DEF_COL, String.format("%s %s", columnMetadataResultSet.get(COLUMN_DEF_COL), extraValue.replace(MYSQL_DEFAULT_GENERATED, "").trim()));
            }
        }
        catch (DatabaseException e) {
            Scope.getCurrentScope().getLog(this.getClass()).warning("Error fetching extra values", e);
        }
    }

    private void readDefaultValueForPostgresDatabase(CachedRow columnMetadataResultSet, Column columnInfo) {
        Object virtColumnDef;
        Object defaultValue;
        if (columnInfo.isAutoIncrement()) {
            columnMetadataResultSet.set(COLUMN_DEF_COL, null);
        }
        if ((defaultValue = columnMetadataResultSet.get(COLUMN_DEF_COL)) instanceof String) {
            Matcher matcher = POSTGRES_STRING_VALUE_PATTERN.matcher((String)defaultValue);
            if (matcher.matches()) {
                defaultValue = matcher.group(1);
            } else {
                matcher = POSTGRES_NUMBER_VALUE_PATTERN.matcher((String)defaultValue);
                if (matcher.matches()) {
                    defaultValue = matcher.group(1);
                }
            }
            columnMetadataResultSet.set(COLUMN_DEF_COL, defaultValue);
        }
        if (YES_VALUE.equals(columnMetadataResultSet.get(IS_GENERATED_COLUMN)) && (virtColumnDef = columnMetadataResultSet.get(COLUMN_DEF_COL)) != null && !StringUtil.equalsWordNull(virtColumnDef.toString()) && !String.valueOf(virtColumnDef).startsWith(GENERATED_ALWAYS_AS)) {
            columnMetadataResultSet.set(COLUMN_DEF_COL, GENERATED_ALWAYS_AS + virtColumnDef + " STORED");
        }
    }

    private boolean looksLikeFunction(String columnName) {
        return columnName.contains("(");
    }
}

