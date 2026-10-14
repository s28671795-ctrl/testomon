/*
 * Decompiled with CFR 0.152.
 */
package org.h2.engine;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;
import org.h2.engine.NullsDistinct;
import org.h2.util.StringUtils;
import org.h2.value.DataType;

public class Mode {
    private static final HashMap<String, Mode> MODES = new HashMap();
    public boolean aliasColumnName;
    public boolean convertOnlyToSmallerScale;
    public boolean indexDefinitionInCreateTable;
    public boolean squareBracketQuotedNames;
    public boolean systemColumns;
    public NullsDistinct nullsDistinct = NullsDistinct.DISTINCT;
    public boolean treatEmptyStringsAsNull;
    public boolean greatestLeastIgnoreNulls;
    public boolean sysDummy1;
    public boolean allowPlusForStringConcat;
    public boolean logIsLogBase10;
    public boolean swapLogFunctionParameters;
    public boolean regexpReplaceBackslashReferences;
    public boolean swapConvertFunctionParameters;
    public boolean isolationLevelInSelectOrInsertStatement;
    public boolean onDuplicateKeyUpdate;
    public boolean replaceInto;
    public boolean insertOnConflict;
    public Pattern supportedClientInfoPropertiesRegEx;
    public boolean supportPoundSymbolForColumnNames;
    public boolean allowEmptyInPredicate;
    public CharPadding charPadding = CharPadding.ALWAYS;
    public boolean allowDB2TimestampFormat;
    public boolean discardWithTableHints;
    public boolean dateTimeValueWithinTransaction;
    public boolean zeroExLiteralsAreBinaryStrings;
    public boolean allowUnrelatedOrderByExpressionsInDistinctQueries;
    public boolean alterTableExtensionsMySQL;
    public boolean alterTableModifyColumn;
    public boolean alterTableModifyColumnPreserveNullability;
    public boolean mySqlTableOptions;
    public boolean deleteIdentifierFrom;
    public boolean truncateTableRestartIdentity;
    public boolean decimalSequences;
    public boolean allowEmptySchemaValuesAsDefaultSchema;
    public boolean allNumericTypesHavePrecision;
    public boolean forBitData;
    public boolean charAndByteLengthUnits;
    public boolean nextvalAndCurrvalPseudoColumns;
    public boolean nextValueReturnsDifferentValues;
    public boolean updateSequenceOnManualIdentityInsertion;
    public boolean takeInsertedIdentity;
    public boolean takeGeneratedSequenceValue;
    public boolean identityColumnsHaveDefaultOnNull;
    public boolean mergeWhere;
    public boolean allowUsingFromClauseInUpdateStatement;
    public boolean createUniqueConstraintForReferencedColumns;
    public ExpressionNames expressionNames = ExpressionNames.OPTIMIZED_SQL;
    public ViewExpressionNames viewExpressionNames = ViewExpressionNames.AS_IS;
    public boolean topInSelect;
    public boolean topInDML;
    public boolean limit;
    public boolean minusIsExcept;
    public boolean identityDataType;
    public boolean serialDataTypes;
    public boolean identityClause;
    public boolean autoIncrementClause;
    public boolean dateIsTimestamp0;
    public boolean numericIsDecfloat;
    public Set<String> disallowedTypes = Collections.emptySet();
    public HashMap<String, DataType> typeByNameMap = new HashMap();
    public boolean groupByColumnIndex;
    public boolean numericWithBooleanComparison;
    public boolean acceptsCommaAsJsonKeyValueSeparator;
    private final String name;
    private final ModeEnum modeEnum;

    private Mode(ModeEnum modeEnum) {
        this.name = modeEnum.name();
        this.modeEnum = modeEnum;
    }

    private static void add(Mode mode) {
        MODES.put(StringUtils.toUpperEnglish(mode.name), mode);
    }

    public static Mode getInstance(String string) {
        return MODES.get(StringUtils.toUpperEnglish(string));
    }

    public static Mode getRegular() {
        return Mode.getInstance(ModeEnum.REGULAR.name());
    }

    public String getName() {
        return this.name;
    }

    public ModeEnum getEnum() {
        return this.modeEnum;
    }

    public String toString() {
        return this.name;
    }

    static {
        Mode mode = new Mode(ModeEnum.REGULAR);
        mode.allowEmptyInPredicate = true;
        mode.dateTimeValueWithinTransaction = true;
        mode.topInSelect = true;
        mode.limit = true;
        mode.minusIsExcept = true;
        mode.identityDataType = true;
        mode.serialDataTypes = true;
        mode.autoIncrementClause = true;
        Mode.add(mode);
        mode = new Mode(ModeEnum.STRICT);
        mode.dateTimeValueWithinTransaction = true;
        Mode.add(mode);
        mode = new Mode(ModeEnum.LEGACY);
        mode.allowEmptyInPredicate = true;
        mode.dateTimeValueWithinTransaction = true;
        mode.topInSelect = true;
        mode.limit = true;
        mode.minusIsExcept = true;
        mode.identityDataType = true;
        mode.serialDataTypes = true;
        mode.autoIncrementClause = true;
        mode.identityClause = true;
        mode.updateSequenceOnManualIdentityInsertion = true;
        mode.takeInsertedIdentity = true;
        mode.identityColumnsHaveDefaultOnNull = true;
        mode.nextvalAndCurrvalPseudoColumns = true;
        mode.topInDML = true;
        mode.mergeWhere = true;
        mode.createUniqueConstraintForReferencedColumns = true;
        mode.numericWithBooleanComparison = true;
        mode.greatestLeastIgnoreNulls = true;
        Mode.add(mode);
        mode = new Mode(ModeEnum.DB2);
        mode.aliasColumnName = true;
        mode.sysDummy1 = true;
        mode.isolationLevelInSelectOrInsertStatement = true;
        mode.supportedClientInfoPropertiesRegEx = Pattern.compile("ApplicationName|ClientAccountingInformation|ClientUser|ClientCorrelationToken");
        mode.allowDB2TimestampFormat = true;
        mode.forBitData = true;
        mode.takeInsertedIdentity = true;
        mode.nextvalAndCurrvalPseudoColumns = true;
        mode.expressionNames = ExpressionNames.NUMBER;
        mode.viewExpressionNames = ViewExpressionNames.EXCEPTION;
        mode.limit = true;
        mode.minusIsExcept = true;
        mode.numericWithBooleanComparison = true;
        Mode.add(mode);
        mode = new Mode(ModeEnum.Derby);
        mode.aliasColumnName = true;
        mode.nullsDistinct = NullsDistinct.NOT_DISTINCT;
        mode.sysDummy1 = true;
        mode.isolationLevelInSelectOrInsertStatement = true;
        mode.supportedClientInfoPropertiesRegEx = null;
        mode.forBitData = true;
        mode.takeInsertedIdentity = true;
        mode.expressionNames = ExpressionNames.NUMBER;
        mode.viewExpressionNames = ViewExpressionNames.EXCEPTION;
        Mode.add(mode);
        mode = new Mode(ModeEnum.HSQLDB);
        mode.allowPlusForStringConcat = true;
        mode.identityColumnsHaveDefaultOnNull = true;
        mode.supportedClientInfoPropertiesRegEx = null;
        mode.expressionNames = ExpressionNames.C_NUMBER;
        mode.topInSelect = true;
        mode.limit = true;
        mode.minusIsExcept = true;
        mode.numericWithBooleanComparison = true;
        Mode.add(mode);
        mode = new Mode(ModeEnum.MSSQLServer);
        mode.aliasColumnName = true;
        mode.squareBracketQuotedNames = true;
        mode.nullsDistinct = NullsDistinct.NOT_DISTINCT;
        mode.greatestLeastIgnoreNulls = true;
        mode.allowPlusForStringConcat = true;
        mode.swapLogFunctionParameters = true;
        mode.swapConvertFunctionParameters = true;
        mode.supportPoundSymbolForColumnNames = true;
        mode.discardWithTableHints = true;
        mode.supportedClientInfoPropertiesRegEx = null;
        mode.zeroExLiteralsAreBinaryStrings = true;
        mode.truncateTableRestartIdentity = true;
        mode.takeInsertedIdentity = true;
        DataType dataType = DataType.createNumeric(19, 4);
        dataType.type = 13;
        dataType.sqlType = 2;
        dataType.specialPrecisionScale = true;
        mode.typeByNameMap.put("MONEY", dataType);
        dataType = DataType.createNumeric(10, 4);
        dataType.type = 13;
        dataType.sqlType = 2;
        dataType.specialPrecisionScale = true;
        mode.typeByNameMap.put("SMALLMONEY", dataType);
        mode.typeByNameMap.put("UNIQUEIDENTIFIER", DataType.getDataType(39));
        mode.allowEmptySchemaValuesAsDefaultSchema = true;
        mode.expressionNames = ExpressionNames.EMPTY;
        mode.viewExpressionNames = ViewExpressionNames.EXCEPTION;
        mode.topInSelect = true;
        mode.topInDML = true;
        mode.identityClause = true;
        mode.numericWithBooleanComparison = true;
        Mode.add(mode);
        mode = new Mode(ModeEnum.MariaDB);
        mode.indexDefinitionInCreateTable = true;
        mode.regexpReplaceBackslashReferences = true;
        mode.onDuplicateKeyUpdate = true;
        mode.replaceInto = true;
        mode.charPadding = CharPadding.NEVER;
        mode.supportedClientInfoPropertiesRegEx = Pattern.compile(".*");
        mode.zeroExLiteralsAreBinaryStrings = true;
        mode.allowUnrelatedOrderByExpressionsInDistinctQueries = true;
        mode.alterTableExtensionsMySQL = true;
        mode.alterTableModifyColumn = true;
        mode.mySqlTableOptions = true;
        mode.deleteIdentifierFrom = true;
        mode.truncateTableRestartIdentity = true;
        mode.allNumericTypesHavePrecision = true;
        mode.nextValueReturnsDifferentValues = true;
        mode.updateSequenceOnManualIdentityInsertion = true;
        mode.takeInsertedIdentity = true;
        mode.identityColumnsHaveDefaultOnNull = true;
        mode.expressionNames = ExpressionNames.ORIGINAL_SQL;
        mode.viewExpressionNames = ViewExpressionNames.MYSQL_STYLE;
        mode.limit = true;
        mode.autoIncrementClause = true;
        mode.typeByNameMap.put("YEAR", DataType.getDataType(10));
        mode.groupByColumnIndex = true;
        mode.numericWithBooleanComparison = true;
        mode.acceptsCommaAsJsonKeyValueSeparator = true;
        Mode.add(mode);
        mode = new Mode(ModeEnum.MySQL);
        mode.indexDefinitionInCreateTable = true;
        mode.regexpReplaceBackslashReferences = true;
        mode.onDuplicateKeyUpdate = true;
        mode.replaceInto = true;
        mode.charPadding = CharPadding.NEVER;
        mode.supportedClientInfoPropertiesRegEx = Pattern.compile(".*");
        mode.zeroExLiteralsAreBinaryStrings = true;
        mode.allowUnrelatedOrderByExpressionsInDistinctQueries = true;
        mode.alterTableExtensionsMySQL = true;
        mode.alterTableModifyColumn = true;
        mode.mySqlTableOptions = true;
        mode.deleteIdentifierFrom = true;
        mode.truncateTableRestartIdentity = true;
        mode.allNumericTypesHavePrecision = true;
        mode.updateSequenceOnManualIdentityInsertion = true;
        mode.takeInsertedIdentity = true;
        mode.identityColumnsHaveDefaultOnNull = true;
        mode.createUniqueConstraintForReferencedColumns = true;
        mode.expressionNames = ExpressionNames.ORIGINAL_SQL;
        mode.viewExpressionNames = ViewExpressionNames.MYSQL_STYLE;
        mode.limit = true;
        mode.autoIncrementClause = true;
        mode.typeByNameMap.put("YEAR", DataType.getDataType(10));
        mode.groupByColumnIndex = true;
        mode.numericWithBooleanComparison = true;
        mode.acceptsCommaAsJsonKeyValueSeparator = true;
        Mode.add(mode);
        mode = new Mode(ModeEnum.Oracle);
        mode.aliasColumnName = true;
        mode.convertOnlyToSmallerScale = true;
        mode.nullsDistinct = NullsDistinct.ALL_DISTINCT;
        mode.treatEmptyStringsAsNull = true;
        mode.regexpReplaceBackslashReferences = true;
        mode.supportPoundSymbolForColumnNames = true;
        mode.supportedClientInfoPropertiesRegEx = Pattern.compile(".*\\..*");
        mode.alterTableModifyColumn = true;
        mode.alterTableModifyColumnPreserveNullability = true;
        mode.decimalSequences = true;
        mode.charAndByteLengthUnits = true;
        mode.nextvalAndCurrvalPseudoColumns = true;
        mode.mergeWhere = true;
        mode.minusIsExcept = true;
        mode.expressionNames = ExpressionNames.ORIGINAL_SQL;
        mode.viewExpressionNames = ViewExpressionNames.EXCEPTION;
        mode.dateIsTimestamp0 = true;
        mode.typeByNameMap.put("BINARY_FLOAT", DataType.getDataType(14));
        mode.typeByNameMap.put("BINARY_DOUBLE", DataType.getDataType(15));
        Mode.add(mode);
        mode = new Mode(ModeEnum.PostgreSQL);
        mode.aliasColumnName = true;
        mode.systemColumns = true;
        mode.greatestLeastIgnoreNulls = true;
        mode.logIsLogBase10 = true;
        mode.regexpReplaceBackslashReferences = true;
        mode.insertOnConflict = true;
        mode.supportedClientInfoPropertiesRegEx = Pattern.compile("ApplicationName");
        mode.charPadding = CharPadding.IN_RESULT_SETS;
        mode.nextValueReturnsDifferentValues = true;
        mode.takeGeneratedSequenceValue = true;
        mode.expressionNames = ExpressionNames.POSTGRESQL_STYLE;
        mode.allowUsingFromClauseInUpdateStatement = true;
        mode.limit = true;
        mode.serialDataTypes = true;
        mode.numericIsDecfloat = true;
        HashSet<String> hashSet = new HashSet<String>();
        hashSet.add("NUMBER");
        hashSet.add("TINYINT");
        hashSet.add("BLOB");
        hashSet.add("VARCHAR_IGNORECASE");
        mode.disallowedTypes = hashSet;
        dataType = DataType.getDataType(38);
        mode.typeByNameMap.put("JSONB", dataType);
        dataType = DataType.createNumeric(19, 2);
        dataType.type = 13;
        dataType.sqlType = 2;
        dataType.specialPrecisionScale = true;
        mode.typeByNameMap.put("MONEY", dataType);
        dataType = DataType.getDataType(11);
        mode.typeByNameMap.put("OID", dataType);
        mode.dateTimeValueWithinTransaction = true;
        mode.groupByColumnIndex = true;
        Mode.add(mode);
    }

    public static enum CharPadding {
        ALWAYS,
        IN_RESULT_SETS,
        NEVER;

    }

    public static enum ViewExpressionNames {
        AS_IS,
        EXCEPTION,
        MYSQL_STYLE;

    }

    public static enum ExpressionNames {
        OPTIMIZED_SQL,
        ORIGINAL_SQL,
        EMPTY,
        NUMBER,
        C_NUMBER,
        POSTGRESQL_STYLE;

    }

    public static enum ModeEnum {
        REGULAR,
        STRICT,
        LEGACY,
        DB2,
        Derby,
        MariaDB,
        MSSQLServer,
        HSQLDB,
        MySQL,
        Oracle,
        PostgreSQL;

    }
}

