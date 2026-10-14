/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  jakarta.xml.bind.annotation.XmlAccessType
 *  jakarta.xml.bind.annotation.XmlAccessorType
 *  jakarta.xml.bind.annotation.XmlElement
 *  jakarta.xml.bind.annotation.XmlElementWrapper
 *  jakarta.xml.bind.annotation.XmlSchemaType
 *  jakarta.xml.bind.annotation.XmlType
 *  jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter
 */
package org.jooq.conf;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlSchemaType;
import jakarta.xml.bind.annotation.XmlType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import org.jooq.SQLDialect;
import org.jooq.conf.BackslashEscaping;
import org.jooq.conf.DiagnosticsConnection;
import org.jooq.conf.ExecuteWithoutWhere;
import org.jooq.conf.FetchIntermediateResult;
import org.jooq.conf.InterpreterNameLookupCaseSensitivity;
import org.jooq.conf.InterpreterSearchSchema;
import org.jooq.conf.InvocationOrder;
import org.jooq.conf.LocaleAdapter;
import org.jooq.conf.MigrationSchema;
import org.jooq.conf.NestedCollectionEmulation;
import org.jooq.conf.ParamCastMode;
import org.jooq.conf.ParamType;
import org.jooq.conf.ParseNameCase;
import org.jooq.conf.ParseSearchSchema;
import org.jooq.conf.ParseUnknownFunctions;
import org.jooq.conf.ParseUnsupportedSyntax;
import org.jooq.conf.ParseWithMetaLookups;
import org.jooq.conf.QueryPoolable;
import org.jooq.conf.RenderDefaultNullability;
import org.jooq.conf.RenderFormatting;
import org.jooq.conf.RenderImplicitJoinType;
import org.jooq.conf.RenderImplicitWindowRange;
import org.jooq.conf.RenderKeywordCase;
import org.jooq.conf.RenderKeywordStyle;
import org.jooq.conf.RenderMapping;
import org.jooq.conf.RenderNameCase;
import org.jooq.conf.RenderNameStyle;
import org.jooq.conf.RenderOptionalKeyword;
import org.jooq.conf.RenderQuotedNames;
import org.jooq.conf.RenderTable;
import org.jooq.conf.SQLDialectAdapter;
import org.jooq.conf.SettingsBase;
import org.jooq.conf.StatementType;
import org.jooq.conf.ThrowExceptions;
import org.jooq.conf.TransformUnneededArithmeticExpressions;
import org.jooq.conf.Transformation;
import org.jooq.conf.UpdateUnchangedRecords;
import org.jooq.conf.WriteIfReadonly;
import org.jooq.util.jaxb.tools.XMLAppendable;
import org.jooq.util.jaxb.tools.XMLBuilder;

@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlType(name="Settings", propOrder={})
public class Settings
extends SettingsBase
implements Serializable,
Cloneable,
XMLAppendable {
    private static final long serialVersionUID = 31800L;
    @XmlElement(defaultValue="true")
    protected Boolean forceIntegerTypesOnZeroScaleDecimals = true;
    @XmlElement(defaultValue="true")
    protected Boolean renderCatalog = true;
    @XmlElement(defaultValue="true")
    protected Boolean renderSchema = true;
    @XmlElement(defaultValue="ALWAYS")
    @XmlSchemaType(name="string")
    protected RenderTable renderTable = RenderTable.ALWAYS;
    protected RenderMapping renderMapping;
    @XmlElement(defaultValue="EXPLICIT_DEFAULT_QUOTED")
    @XmlSchemaType(name="string")
    protected RenderQuotedNames renderQuotedNames = RenderQuotedNames.EXPLICIT_DEFAULT_QUOTED;
    @XmlElement(defaultValue="AS_IS")
    @XmlSchemaType(name="string")
    protected RenderNameCase renderNameCase = RenderNameCase.AS_IS;
    @XmlElement(defaultValue="QUOTED")
    @XmlSchemaType(name="string")
    protected RenderNameStyle renderNameStyle = RenderNameStyle.QUOTED;
    @XmlElement(defaultValue=":")
    protected String renderNamedParamPrefix = ":";
    @XmlElement(defaultValue="AS_IS")
    @XmlSchemaType(name="string")
    protected RenderKeywordCase renderKeywordCase = RenderKeywordCase.AS_IS;
    @XmlElement(defaultValue="AS_IS")
    @XmlSchemaType(name="string")
    protected RenderKeywordStyle renderKeywordStyle = RenderKeywordStyle.AS_IS;
    @XmlElement(type=String.class)
    @XmlJavaTypeAdapter(value=LocaleAdapter.class)
    protected Locale renderLocale;
    @XmlElement(defaultValue="false")
    protected Boolean renderFormatted = false;
    protected RenderFormatting renderFormatting;
    @XmlElement(defaultValue="DEFAULT")
    @XmlSchemaType(name="string")
    protected RenderOptionalKeyword renderOptionalAssociativityParentheses = RenderOptionalKeyword.DEFAULT;
    @XmlElement(defaultValue="DEFAULT")
    @XmlSchemaType(name="string")
    protected RenderOptionalKeyword renderOptionalAsKeywordForTableAliases = RenderOptionalKeyword.DEFAULT;
    @XmlElement(defaultValue="DEFAULT")
    @XmlSchemaType(name="string")
    protected RenderOptionalKeyword renderOptionalAsKeywordForFieldAliases = RenderOptionalKeyword.DEFAULT;
    @XmlElement(defaultValue="DEFAULT")
    @XmlSchemaType(name="string")
    protected RenderOptionalKeyword renderOptionalInnerKeyword = RenderOptionalKeyword.DEFAULT;
    @XmlElement(defaultValue="DEFAULT")
    @XmlSchemaType(name="string")
    protected RenderOptionalKeyword renderOptionalOuterKeyword = RenderOptionalKeyword.DEFAULT;
    @XmlElement(defaultValue="OFF")
    @XmlSchemaType(name="string")
    protected RenderImplicitWindowRange renderImplicitWindowRange = RenderImplicitWindowRange.OFF;
    @XmlElement(defaultValue="false")
    protected Boolean renderScalarSubqueriesForStoredFunctions = false;
    @XmlElement(defaultValue="DEFAULT")
    @XmlSchemaType(name="string")
    protected RenderImplicitJoinType renderImplicitJoinType = RenderImplicitJoinType.DEFAULT;
    @XmlElement(defaultValue="IMPLICIT_NULL")
    @XmlSchemaType(name="string")
    protected RenderDefaultNullability renderDefaultNullability = RenderDefaultNullability.IMPLICIT_NULL;
    @XmlElement(defaultValue="false")
    protected Boolean renderCoalesceToEmptyStringInConcat = false;
    @XmlElement(defaultValue="true")
    protected Boolean renderOrderByRownumberForEmulatedPagination = true;
    @XmlElement(defaultValue="true")
    protected Boolean renderOutputForSQLServerReturningClause = true;
    @XmlElement(defaultValue="true")
    protected Boolean renderGroupConcatMaxLenSessionVariable = true;
    @XmlElement(defaultValue="false")
    protected Boolean renderParenthesisAroundSetOperationQueries = false;
    @XmlElement(defaultValue="true")
    protected Boolean renderVariablesInDerivedTablesForEmulations = true;
    @XmlElement(defaultValue="true")
    protected Boolean renderRowConditionForSeekClause = true;
    @XmlElement(defaultValue="false")
    protected Boolean renderRedundantConditionForSeekClause = false;
    @XmlElement(defaultValue="false")
    protected Boolean renderPlainSQLTemplatesAsRaw = false;
    @XmlElement(defaultValue=".")
    protected String namePathSeparator = ".";
    @XmlElement(defaultValue="false")
    protected Boolean bindOffsetDateTimeType = false;
    @XmlElement(defaultValue="false")
    protected Boolean bindOffsetTimeType = false;
    @XmlElement(defaultValue="true")
    protected Boolean fetchTriggerValuesAfterSQLServerOutput = true;
    @XmlElement(defaultValue="WHEN_RESULT_REQUESTED")
    @XmlSchemaType(name="string")
    protected FetchIntermediateResult fetchIntermediateResult = FetchIntermediateResult.WHEN_RESULT_REQUESTED;
    @XmlElement(defaultValue="true")
    protected Boolean diagnosticsDuplicateStatements = true;
    @XmlElement(defaultValue="true")
    protected Boolean diagnosticsDuplicateStatementsUsingTransformPatterns = true;
    @XmlElement(defaultValue="true")
    protected Boolean diagnosticsMissingWasNullCall = true;
    @XmlElement(defaultValue="true")
    protected Boolean diagnosticsRepeatedStatements = true;
    @XmlElement(defaultValue="true")
    protected Boolean diagnosticsConsecutiveAggregation = true;
    @XmlElement(defaultValue="true")
    protected Boolean diagnosticsConcatenationInPredicate = true;
    @XmlElement(defaultValue="true")
    protected Boolean diagnosticsPossiblyWrongExpression = true;
    @XmlElement(defaultValue="true")
    protected Boolean diagnosticsTooManyColumnsFetched = true;
    @XmlElement(defaultValue="true")
    protected Boolean diagnosticsTooManyRowsFetched = true;
    @XmlElement(defaultValue="true")
    protected Boolean diagnosticsUnnecessaryWasNullCall = true;
    @XmlElement(defaultValue="true")
    protected Boolean diagnosticsPatterns = true;
    @XmlElement(defaultValue="true")
    protected Boolean diagnosticsTrivialCondition = true;
    @XmlElement(defaultValue="true")
    protected Boolean diagnosticsNullCondition = true;
    @XmlElement(defaultValue="false")
    protected Boolean transformPatterns = false;
    @XmlElement(defaultValue="true")
    protected Boolean transformPatternsLogging = true;
    @XmlElement(defaultValue="true")
    protected Boolean transformPatternsUnnecessaryDistinct = true;
    @XmlElement(defaultValue="true")
    protected Boolean transformPatternsUnnecessaryScalarSubquery = true;
    @XmlElement(defaultValue="true")
    protected Boolean transformPatternsUnnecessaryInnerJoin = true;
    @XmlElement(defaultValue="true")
    protected Boolean transformPatternsUnnecessaryGroupByExpressions = true;
    @XmlElement(defaultValue="true")
    protected Boolean transformPatternsUnnecessaryOrderByExpressions = true;
    @XmlElement(defaultValue="true")
    protected Boolean transformPatternsUnnecessaryExistsSubqueryClauses = true;
    @XmlElement(defaultValue="true")
    protected Boolean transformPatternsCountConstant = true;
    @XmlElement(defaultValue="true")
    protected Boolean transformPatternsTrim = true;
    @XmlElement(defaultValue="true")
    protected Boolean transformPatternsNotAnd = true;
    @XmlElement(defaultValue="true")
    protected Boolean transformPatternsNotOr = true;
    @XmlElement(defaultValue="true")
    protected Boolean transformPatternsNotNot = true;
    @XmlElement(defaultValue="true")
    protected Boolean transformPatternsNotComparison = true;
    @XmlElement(defaultValue="true")
    protected Boolean transformPatternsNotNotDistinct = true;
    @XmlElement(defaultValue="true")
    protected Boolean transformPatternsDistinctFromNull = true;
    @XmlElement(defaultValue="true")
    protected Boolean transformPatternsNormaliseAssociativeOps = true;
    @XmlElement(defaultValue="true")
    protected Boolean transformPatternsNormaliseInListSingleElementToComparison = true;
    @XmlElement(defaultValue="true")
    protected Boolean transformPatternsNormaliseFieldCompareValue = true;
    @XmlElement(defaultValue="true")
    protected Boolean transformPatternsNormaliseCoalesceToNvl = true;
    @XmlElement(defaultValue="true")
    protected Boolean transformPatternsOrEqToIn = true;
    @XmlElement(defaultValue="true")
    protected Boolean transformPatternsAndNeToNotIn = true;
    @XmlElement(defaultValue="true")
    protected Boolean transformPatternsMergeOrComparison = true;
    @XmlElement(defaultValue="true")
    protected Boolean transformPatternsMergeAndComparison = true;
    @XmlElement(defaultValue="true")
    protected Boolean transformPatternsMergeInLists = true;
    @XmlElement(defaultValue="true")
    protected Boolean transformPatternsMergeRangePredicates = true;
    @XmlElement(defaultValue="true")
    protected Boolean transformPatternsMergeBetweenSymmetricPredicates = true;
    @XmlElement(defaultValue="true")
    protected Boolean transformPatternsCaseSearchedToCaseSimple = true;
    @XmlElement(defaultValue="true")
    protected Boolean transformPatternsCaseElseNull = true;
    @XmlElement(defaultValue="true")
    protected Boolean transformPatternsUnreachableCaseClauses = true;
    @XmlElement(defaultValue="true")
    protected Boolean transformPatternsUnreachableDecodeClauses = true;
    @XmlElement(defaultValue="true")
    protected Boolean transformPatternsCaseDistinctToDecode = true;
    @XmlElement(defaultValue="true")
    protected Boolean transformPatternsCaseMergeWhenWhen = true;
    @XmlElement(defaultValue="true")
    protected Boolean transformPatternsCaseMergeWhenElse = true;
    @XmlElement(defaultValue="true")
    protected Boolean transformPatternsCaseToCaseAbbreviation = true;
    @XmlElement(defaultValue="true")
    protected Boolean transformPatternsSimplifyCaseAbbreviation = true;
    @XmlElement(defaultValue="true")
    protected Boolean transformPatternsFlattenCaseAbbreviation = true;
    @XmlElement(defaultValue="true")
    protected Boolean transformPatternsFlattenDecode = true;
    @XmlElement(defaultValue="true")
    protected Boolean transformPatternsFlattenCase = true;
    @XmlElement(defaultValue="true")
    protected Boolean transformPatternsTrivialCaseAbbreviation = true;
    @XmlElement(defaultValue="true")
    protected Boolean transformPatternsTrivialPredicates = true;
    @XmlElement(defaultValue="true")
    protected Boolean transformPatternsTrivialBitwiseOperations = true;
    @XmlElement(defaultValue="true")
    protected Boolean transformPatternsBitSet = true;
    @XmlElement(defaultValue="true")
    protected Boolean transformPatternsBitGet = true;
    @XmlElement(defaultValue="true")
    protected Boolean transformPatternsScalarSubqueryCountAsteriskGtZero = true;
    @XmlElement(defaultValue="true")
    protected Boolean transformPatternsScalarSubqueryCountExpressionGtZero = true;
    @XmlElement(defaultValue="true")
    protected Boolean transformPatternsEmptyScalarSubquery = true;
    @XmlElement(defaultValue="true")
    protected Boolean transformPatternsNegNeg = true;
    @XmlElement(defaultValue="true")
    protected Boolean transformPatternsBitNotBitNot = true;
    @XmlElement(defaultValue="true")
    protected Boolean transformPatternsBitNotBitNand = true;
    @XmlElement(defaultValue="true")
    protected Boolean transformPatternsBitNotBitNor = true;
    @XmlElement(defaultValue="true")
    protected Boolean transformPatternsBitNotBitXNor = true;
    @XmlElement(defaultValue="true")
    protected Boolean transformPatternsNullOnNullInput = true;
    @XmlElement(defaultValue="true")
    protected Boolean transformPatternsIdempotentFunctionRepetition = true;
    @XmlElement(defaultValue="true")
    protected Boolean transformPatternsArithmeticComparisons = true;
    @XmlElement(defaultValue="true")
    protected Boolean transformPatternsArithmeticExpressions = true;
    @XmlElement(defaultValue="true")
    protected Boolean transformPatternsTrigonometricFunctions = true;
    @XmlElement(defaultValue="true")
    protected Boolean transformPatternsLogarithmicFunctions = true;
    @XmlElement(defaultValue="true")
    protected Boolean transformPatternsHyperbolicFunctions = true;
    @XmlElement(defaultValue="true")
    protected Boolean transformPatternsInverseHyperbolicFunctions = true;
    @XmlElement(defaultValue="false")
    protected Boolean transformInlineBindValuesForFieldComparisons = false;
    @XmlElement(defaultValue="false")
    protected Boolean transformAnsiJoinToTableLists = false;
    @XmlElement(defaultValue="WHEN_NEEDED")
    @XmlSchemaType(name="string")
    protected Transformation transformInConditionSubqueryWithLimitToDerivedTable = Transformation.WHEN_NEEDED;
    @XmlElement(defaultValue="WHEN_NEEDED")
    @XmlSchemaType(name="string")
    protected Transformation transformQualify = Transformation.WHEN_NEEDED;
    @XmlElement(defaultValue="false")
    protected Boolean transformTableListsToAnsiJoin = false;
    @XmlElement(defaultValue="NEVER")
    @XmlSchemaType(name="string")
    protected Transformation transformRownum = Transformation.NEVER;
    @XmlElement(defaultValue="NEVER")
    @XmlSchemaType(name="string")
    protected TransformUnneededArithmeticExpressions transformUnneededArithmeticExpressions = TransformUnneededArithmeticExpressions.NEVER;
    @XmlElement(defaultValue="WHEN_NEEDED")
    @XmlSchemaType(name="string")
    protected Transformation transformGroupByColumnIndex = Transformation.WHEN_NEEDED;
    @XmlElement(defaultValue="WHEN_NEEDED")
    @XmlSchemaType(name="string")
    protected Transformation transformInlineCTE = Transformation.WHEN_NEEDED;
    @XmlElement(defaultValue="DEFAULT")
    @XmlSchemaType(name="string")
    protected BackslashEscaping backslashEscaping = BackslashEscaping.DEFAULT;
    @XmlElement(defaultValue="INDEXED")
    @XmlSchemaType(name="string")
    protected ParamType paramType = ParamType.INDEXED;
    @XmlElement(defaultValue="DEFAULT")
    @XmlSchemaType(name="string")
    protected ParamCastMode paramCastMode = ParamCastMode.DEFAULT;
    @XmlElement(defaultValue="PREPARED_STATEMENT")
    @XmlSchemaType(name="string")
    protected StatementType statementType = StatementType.PREPARED_STATEMENT;
    @XmlElement(defaultValue="0")
    protected Integer inlineThreshold = 0;
    @XmlElement(defaultValue="DEFAULT")
    @XmlSchemaType(name="string")
    protected InvocationOrder transactionListenerStartInvocationOrder = InvocationOrder.DEFAULT;
    @XmlElement(defaultValue="DEFAULT")
    @XmlSchemaType(name="string")
    protected InvocationOrder transactionListenerEndInvocationOrder = InvocationOrder.DEFAULT;
    @XmlElement(defaultValue="DEFAULT")
    @XmlSchemaType(name="string")
    protected InvocationOrder migrationListenerStartInvocationOrder = InvocationOrder.DEFAULT;
    @XmlElement(defaultValue="DEFAULT")
    @XmlSchemaType(name="string")
    protected InvocationOrder migrationListenerEndInvocationOrder = InvocationOrder.DEFAULT;
    @XmlElement(defaultValue="DEFAULT")
    @XmlSchemaType(name="string")
    protected InvocationOrder visitListenerStartInvocationOrder = InvocationOrder.DEFAULT;
    @XmlElement(defaultValue="DEFAULT")
    @XmlSchemaType(name="string")
    protected InvocationOrder visitListenerEndInvocationOrder = InvocationOrder.DEFAULT;
    @XmlElement(defaultValue="DEFAULT")
    @XmlSchemaType(name="string")
    protected InvocationOrder recordListenerStartInvocationOrder = InvocationOrder.DEFAULT;
    @XmlElement(defaultValue="DEFAULT")
    @XmlSchemaType(name="string")
    protected InvocationOrder recordListenerEndInvocationOrder = InvocationOrder.DEFAULT;
    @XmlElement(defaultValue="DEFAULT")
    @XmlSchemaType(name="string")
    protected InvocationOrder executeListenerStartInvocationOrder = InvocationOrder.DEFAULT;
    @XmlElement(defaultValue="DEFAULT")
    @XmlSchemaType(name="string")
    protected InvocationOrder executeListenerEndInvocationOrder = InvocationOrder.DEFAULT;
    @XmlElement(defaultValue="true")
    protected Boolean executeLogging = true;
    @XmlElement(defaultValue="true")
    protected Boolean executeLoggingSQLExceptions = true;
    @XmlElement(defaultValue="true")
    protected Boolean diagnosticsLogging = true;
    @XmlElement(defaultValue="DEFAULT")
    @XmlSchemaType(name="string")
    protected DiagnosticsConnection diagnosticsConnection = DiagnosticsConnection.DEFAULT;
    @XmlElement(defaultValue="true")
    protected Boolean updateRecordVersion = true;
    @XmlElement(defaultValue="true")
    protected Boolean updateRecordTimestamp = true;
    @XmlElement(defaultValue="false")
    protected Boolean executeWithOptimisticLocking = false;
    @XmlElement(defaultValue="false")
    protected Boolean executeWithOptimisticLockingExcludeUnversioned = false;
    @XmlElement(defaultValue="true")
    protected Boolean attachRecords = true;
    @XmlElement(defaultValue="true")
    protected Boolean insertUnchangedRecords = true;
    @XmlElement(defaultValue="NEVER")
    @XmlSchemaType(name="string")
    protected UpdateUnchangedRecords updateUnchangedRecords = UpdateUnchangedRecords.NEVER;
    @XmlElement(defaultValue="false")
    protected Boolean updatablePrimaryKeys = false;
    @XmlElement(defaultValue="true")
    protected Boolean reflectionCaching = true;
    @XmlElement(defaultValue="true")
    protected Boolean cacheRecordMappers = true;
    @XmlElement(defaultValue="true")
    protected Boolean cacheParsingConnection = true;
    @XmlElement(defaultValue="8192")
    protected Integer cacheParsingConnectionLRUCacheSize = 8192;
    @XmlElement(defaultValue="true")
    protected Boolean cachePreparedStatementInLoader = true;
    @XmlElement(defaultValue="THROW_ALL")
    @XmlSchemaType(name="string")
    protected ThrowExceptions throwExceptions = ThrowExceptions.THROW_ALL;
    @XmlElement(defaultValue="true")
    protected Boolean fetchWarnings = true;
    @XmlElement(defaultValue="0")
    protected Integer fetchServerOutputSize = 0;
    @XmlElement(defaultValue="true")
    protected Boolean returnIdentityOnUpdatableRecord = true;
    @XmlElement(defaultValue="false")
    protected Boolean returnDefaultOnUpdatableRecord = false;
    @XmlElement(defaultValue="false")
    protected Boolean returnComputedOnUpdatableRecord = false;
    @XmlElement(defaultValue="false")
    protected Boolean returnAllOnUpdatableRecord = false;
    @XmlElement(defaultValue="true")
    protected Boolean returnRecordToPojo = true;
    @XmlElement(defaultValue="true")
    protected Boolean mapJPAAnnotations = true;
    @XmlElement(defaultValue="false")
    protected Boolean mapRecordComponentParameterNames = false;
    @XmlElement(defaultValue="true")
    protected Boolean mapConstructorPropertiesParameterNames = true;
    @XmlElement(defaultValue="false")
    protected Boolean mapConstructorParameterNames = false;
    @XmlElement(defaultValue="true")
    protected Boolean mapConstructorParameterNamesInKotlin = true;
    @XmlElement(defaultValue="DEFAULT")
    @XmlSchemaType(name="string")
    protected QueryPoolable queryPoolable = QueryPoolable.DEFAULT;
    @XmlElement(defaultValue="0")
    protected Integer queryTimeout = 0;
    @XmlElement(defaultValue="0")
    protected Integer maxRows = 0;
    @XmlElement(defaultValue="0")
    protected Integer fetchSize = 0;
    @XmlElement(defaultValue="2147483647")
    protected Integer batchSize = Integer.MAX_VALUE;
    @XmlElement(defaultValue="true")
    protected Boolean debugInfoOnStackTrace = true;
    @XmlElement(defaultValue="false")
    protected Boolean inListPadding = false;
    @XmlElement(defaultValue="2")
    protected Integer inListPadBase = 2;
    @XmlElement(defaultValue=";")
    protected String delimiter = ";";
    @XmlElement(defaultValue="false")
    protected Boolean emulateOnDuplicateKeyUpdateOnPrimaryKeyOnly = false;
    @XmlElement(defaultValue="DEFAULT")
    @XmlSchemaType(name="string")
    protected NestedCollectionEmulation emulateMultiset = NestedCollectionEmulation.DEFAULT;
    @XmlElement(defaultValue="false")
    protected Boolean emulateComputedColumns = false;
    @XmlElement(defaultValue="LOG_DEBUG")
    @XmlSchemaType(name="string")
    protected ExecuteWithoutWhere executeUpdateWithoutWhere = ExecuteWithoutWhere.LOG_DEBUG;
    @XmlElement(defaultValue="LOG_DEBUG")
    @XmlSchemaType(name="string")
    protected ExecuteWithoutWhere executeDeleteWithoutWhere = ExecuteWithoutWhere.LOG_DEBUG;
    @XmlElement(type=String.class, defaultValue="DEFAULT")
    @XmlJavaTypeAdapter(value=SQLDialectAdapter.class)
    protected SQLDialect interpreterDialect;
    @XmlElement(defaultValue="DEFAULT")
    @XmlSchemaType(name="string")
    protected InterpreterNameLookupCaseSensitivity interpreterNameLookupCaseSensitivity = InterpreterNameLookupCaseSensitivity.DEFAULT;
    @XmlElement(type=String.class)
    @XmlJavaTypeAdapter(value=LocaleAdapter.class)
    protected Locale interpreterLocale;
    @XmlElement(defaultValue="false")
    protected Boolean interpreterDelayForeignKeyDeclarations = false;
    @XmlElement(defaultValue="false")
    protected Boolean metaIncludeSystemIndexes = false;
    @XmlElement(defaultValue="false")
    protected Boolean metaIncludeSystemSequences = false;
    @XmlElement(defaultValue="false")
    protected Boolean migrationAllowsUndo = false;
    @XmlElement(defaultValue="false")
    protected Boolean migrationRevertUntracked = false;
    @XmlElement(defaultValue="false")
    protected Boolean migrationAutoBaseline = false;
    @XmlElement(defaultValue="true")
    protected Boolean migrationAutoValidation = true;
    @XmlElement(defaultValue="true")
    protected Boolean migrationIgnoreDefaultTimestampPrecisionDiffs = true;
    @XmlElement(type=String.class)
    @XmlJavaTypeAdapter(value=LocaleAdapter.class)
    protected Locale locale;
    @XmlElement(type=String.class, defaultValue="DEFAULT")
    @XmlJavaTypeAdapter(value=SQLDialectAdapter.class)
    protected SQLDialect parseDialect;
    @XmlElement(type=String.class)
    @XmlJavaTypeAdapter(value=LocaleAdapter.class)
    protected Locale parseLocale;
    @XmlElement(defaultValue="YYYY-MM-DD")
    protected String parseDateFormat = "YYYY-MM-DD";
    @XmlElement(defaultValue="YYYY-MM-DD HH24:MI:SS.FF")
    protected String parseTimestampFormat = "YYYY-MM-DD HH24:MI:SS.FF";
    @XmlElement(defaultValue=":")
    protected String parseNamedParamPrefix = ":";
    @XmlElement(defaultValue="DEFAULT")
    @XmlSchemaType(name="string")
    protected ParseNameCase parseNameCase = ParseNameCase.DEFAULT;
    @XmlElement(defaultValue="OFF")
    @XmlSchemaType(name="string")
    protected ParseWithMetaLookups parseWithMetaLookups = ParseWithMetaLookups.OFF;
    @XmlElement(defaultValue="WHEN_NEEDED")
    @XmlSchemaType(name="string")
    protected Transformation parseAppendMissingTableReferences = Transformation.WHEN_NEEDED;
    @XmlElement(defaultValue="false")
    protected Boolean parseSetCommands = false;
    @XmlElement(defaultValue="IGNORE")
    @XmlSchemaType(name="string")
    protected ParseUnsupportedSyntax parseUnsupportedSyntax = ParseUnsupportedSyntax.IGNORE;
    @XmlElement(defaultValue="FAIL")
    @XmlSchemaType(name="string")
    protected ParseUnknownFunctions parseUnknownFunctions = ParseUnknownFunctions.FAIL;
    @XmlElement(defaultValue="false")
    protected Boolean parseIgnoreCommercialOnlyFeatures = false;
    @XmlElement(defaultValue="false")
    protected Boolean parseIgnoreComments = false;
    @XmlElement(defaultValue="[jooq ignore start]")
    protected String parseIgnoreCommentStart = "[jooq ignore start]";
    @XmlElement(defaultValue="[jooq ignore stop]")
    protected String parseIgnoreCommentStop = "[jooq ignore stop]";
    @XmlElement(defaultValue="false")
    protected Boolean parseRetainCommentsBetweenQueries = false;
    @XmlElement(defaultValue="true")
    protected Boolean parseMetaDefaultExpressions = true;
    @XmlElement(defaultValue="IGNORE")
    @XmlSchemaType(name="string")
    protected WriteIfReadonly readonlyTableRecordInsert = WriteIfReadonly.IGNORE;
    @XmlElement(defaultValue="IGNORE")
    @XmlSchemaType(name="string")
    protected WriteIfReadonly readonlyUpdatableRecordUpdate = WriteIfReadonly.IGNORE;
    @XmlElement(defaultValue="IGNORE")
    @XmlSchemaType(name="string")
    protected WriteIfReadonly readonlyInsert = WriteIfReadonly.IGNORE;
    @XmlElement(defaultValue="IGNORE")
    @XmlSchemaType(name="string")
    protected WriteIfReadonly readonlyUpdate = WriteIfReadonly.IGNORE;
    @XmlElement(defaultValue="true")
    protected Boolean applyWorkaroundFor7962 = true;
    @XmlElementWrapper(name="interpreterSearchPath")
    @XmlElement(name="schema")
    protected List<InterpreterSearchSchema> interpreterSearchPath;
    @XmlElementWrapper(name="migrationSchemata")
    @XmlElement(name="schema")
    protected List<MigrationSchema> migrationSchemata;
    @XmlElementWrapper(name="parseSearchPath")
    @XmlElement(name="schema")
    protected List<ParseSearchSchema> parseSearchPath;

    public Boolean isForceIntegerTypesOnZeroScaleDecimals() {
        return this.forceIntegerTypesOnZeroScaleDecimals;
    }

    public void setForceIntegerTypesOnZeroScaleDecimals(Boolean value) {
        this.forceIntegerTypesOnZeroScaleDecimals = value;
    }

    public Boolean isRenderCatalog() {
        return this.renderCatalog;
    }

    public void setRenderCatalog(Boolean value) {
        this.renderCatalog = value;
    }

    public Boolean isRenderSchema() {
        return this.renderSchema;
    }

    public void setRenderSchema(Boolean value) {
        this.renderSchema = value;
    }

    public RenderTable getRenderTable() {
        return this.renderTable;
    }

    public void setRenderTable(RenderTable value) {
        this.renderTable = value;
    }

    public RenderMapping getRenderMapping() {
        return this.renderMapping;
    }

    public void setRenderMapping(RenderMapping value) {
        this.renderMapping = value;
    }

    public RenderQuotedNames getRenderQuotedNames() {
        return this.renderQuotedNames;
    }

    public void setRenderQuotedNames(RenderQuotedNames value) {
        this.renderQuotedNames = value;
    }

    public RenderNameCase getRenderNameCase() {
        return this.renderNameCase;
    }

    public void setRenderNameCase(RenderNameCase value) {
        this.renderNameCase = value;
    }

    @Deprecated
    public RenderNameStyle getRenderNameStyle() {
        return this.renderNameStyle;
    }

    @Deprecated
    public void setRenderNameStyle(RenderNameStyle value) {
        this.renderNameStyle = value;
    }

    public String getRenderNamedParamPrefix() {
        return this.renderNamedParamPrefix;
    }

    public void setRenderNamedParamPrefix(String value) {
        this.renderNamedParamPrefix = value;
    }

    public RenderKeywordCase getRenderKeywordCase() {
        return this.renderKeywordCase;
    }

    public void setRenderKeywordCase(RenderKeywordCase value) {
        this.renderKeywordCase = value;
    }

    @Deprecated
    public RenderKeywordStyle getRenderKeywordStyle() {
        return this.renderKeywordStyle;
    }

    @Deprecated
    public void setRenderKeywordStyle(RenderKeywordStyle value) {
        this.renderKeywordStyle = value;
    }

    public Locale getRenderLocale() {
        return this.renderLocale;
    }

    public void setRenderLocale(Locale value) {
        this.renderLocale = value;
    }

    public Boolean isRenderFormatted() {
        return this.renderFormatted;
    }

    public void setRenderFormatted(Boolean value) {
        this.renderFormatted = value;
    }

    public RenderFormatting getRenderFormatting() {
        return this.renderFormatting;
    }

    public void setRenderFormatting(RenderFormatting value) {
        this.renderFormatting = value;
    }

    public RenderOptionalKeyword getRenderOptionalAssociativityParentheses() {
        return this.renderOptionalAssociativityParentheses;
    }

    public void setRenderOptionalAssociativityParentheses(RenderOptionalKeyword value) {
        this.renderOptionalAssociativityParentheses = value;
    }

    public RenderOptionalKeyword getRenderOptionalAsKeywordForTableAliases() {
        return this.renderOptionalAsKeywordForTableAliases;
    }

    public void setRenderOptionalAsKeywordForTableAliases(RenderOptionalKeyword value) {
        this.renderOptionalAsKeywordForTableAliases = value;
    }

    public RenderOptionalKeyword getRenderOptionalAsKeywordForFieldAliases() {
        return this.renderOptionalAsKeywordForFieldAliases;
    }

    public void setRenderOptionalAsKeywordForFieldAliases(RenderOptionalKeyword value) {
        this.renderOptionalAsKeywordForFieldAliases = value;
    }

    public RenderOptionalKeyword getRenderOptionalInnerKeyword() {
        return this.renderOptionalInnerKeyword;
    }

    public void setRenderOptionalInnerKeyword(RenderOptionalKeyword value) {
        this.renderOptionalInnerKeyword = value;
    }

    public RenderOptionalKeyword getRenderOptionalOuterKeyword() {
        return this.renderOptionalOuterKeyword;
    }

    public void setRenderOptionalOuterKeyword(RenderOptionalKeyword value) {
        this.renderOptionalOuterKeyword = value;
    }

    public RenderImplicitWindowRange getRenderImplicitWindowRange() {
        return this.renderImplicitWindowRange;
    }

    public void setRenderImplicitWindowRange(RenderImplicitWindowRange value) {
        this.renderImplicitWindowRange = value;
    }

    public Boolean isRenderScalarSubqueriesForStoredFunctions() {
        return this.renderScalarSubqueriesForStoredFunctions;
    }

    public void setRenderScalarSubqueriesForStoredFunctions(Boolean value) {
        this.renderScalarSubqueriesForStoredFunctions = value;
    }

    public RenderImplicitJoinType getRenderImplicitJoinType() {
        return this.renderImplicitJoinType;
    }

    public void setRenderImplicitJoinType(RenderImplicitJoinType value) {
        this.renderImplicitJoinType = value;
    }

    public RenderDefaultNullability getRenderDefaultNullability() {
        return this.renderDefaultNullability;
    }

    public void setRenderDefaultNullability(RenderDefaultNullability value) {
        this.renderDefaultNullability = value;
    }

    public Boolean isRenderCoalesceToEmptyStringInConcat() {
        return this.renderCoalesceToEmptyStringInConcat;
    }

    public void setRenderCoalesceToEmptyStringInConcat(Boolean value) {
        this.renderCoalesceToEmptyStringInConcat = value;
    }

    public Boolean isRenderOrderByRownumberForEmulatedPagination() {
        return this.renderOrderByRownumberForEmulatedPagination;
    }

    public void setRenderOrderByRownumberForEmulatedPagination(Boolean value) {
        this.renderOrderByRownumberForEmulatedPagination = value;
    }

    public Boolean isRenderOutputForSQLServerReturningClause() {
        return this.renderOutputForSQLServerReturningClause;
    }

    public void setRenderOutputForSQLServerReturningClause(Boolean value) {
        this.renderOutputForSQLServerReturningClause = value;
    }

    public Boolean isRenderGroupConcatMaxLenSessionVariable() {
        return this.renderGroupConcatMaxLenSessionVariable;
    }

    public void setRenderGroupConcatMaxLenSessionVariable(Boolean value) {
        this.renderGroupConcatMaxLenSessionVariable = value;
    }

    public Boolean isRenderParenthesisAroundSetOperationQueries() {
        return this.renderParenthesisAroundSetOperationQueries;
    }

    public void setRenderParenthesisAroundSetOperationQueries(Boolean value) {
        this.renderParenthesisAroundSetOperationQueries = value;
    }

    public Boolean isRenderVariablesInDerivedTablesForEmulations() {
        return this.renderVariablesInDerivedTablesForEmulations;
    }

    public void setRenderVariablesInDerivedTablesForEmulations(Boolean value) {
        this.renderVariablesInDerivedTablesForEmulations = value;
    }

    public Boolean isRenderRowConditionForSeekClause() {
        return this.renderRowConditionForSeekClause;
    }

    public void setRenderRowConditionForSeekClause(Boolean value) {
        this.renderRowConditionForSeekClause = value;
    }

    public Boolean isRenderRedundantConditionForSeekClause() {
        return this.renderRedundantConditionForSeekClause;
    }

    public void setRenderRedundantConditionForSeekClause(Boolean value) {
        this.renderRedundantConditionForSeekClause = value;
    }

    public Boolean isRenderPlainSQLTemplatesAsRaw() {
        return this.renderPlainSQLTemplatesAsRaw;
    }

    public void setRenderPlainSQLTemplatesAsRaw(Boolean value) {
        this.renderPlainSQLTemplatesAsRaw = value;
    }

    public String getNamePathSeparator() {
        return this.namePathSeparator;
    }

    public void setNamePathSeparator(String value) {
        this.namePathSeparator = value;
    }

    public Boolean isBindOffsetDateTimeType() {
        return this.bindOffsetDateTimeType;
    }

    public void setBindOffsetDateTimeType(Boolean value) {
        this.bindOffsetDateTimeType = value;
    }

    public Boolean isBindOffsetTimeType() {
        return this.bindOffsetTimeType;
    }

    public void setBindOffsetTimeType(Boolean value) {
        this.bindOffsetTimeType = value;
    }

    public Boolean isFetchTriggerValuesAfterSQLServerOutput() {
        return this.fetchTriggerValuesAfterSQLServerOutput;
    }

    public void setFetchTriggerValuesAfterSQLServerOutput(Boolean value) {
        this.fetchTriggerValuesAfterSQLServerOutput = value;
    }

    public FetchIntermediateResult getFetchIntermediateResult() {
        return this.fetchIntermediateResult;
    }

    public void setFetchIntermediateResult(FetchIntermediateResult value) {
        this.fetchIntermediateResult = value;
    }

    public Boolean isDiagnosticsDuplicateStatements() {
        return this.diagnosticsDuplicateStatements;
    }

    public void setDiagnosticsDuplicateStatements(Boolean value) {
        this.diagnosticsDuplicateStatements = value;
    }

    public Boolean isDiagnosticsDuplicateStatementsUsingTransformPatterns() {
        return this.diagnosticsDuplicateStatementsUsingTransformPatterns;
    }

    public void setDiagnosticsDuplicateStatementsUsingTransformPatterns(Boolean value) {
        this.diagnosticsDuplicateStatementsUsingTransformPatterns = value;
    }

    public Boolean isDiagnosticsMissingWasNullCall() {
        return this.diagnosticsMissingWasNullCall;
    }

    public void setDiagnosticsMissingWasNullCall(Boolean value) {
        this.diagnosticsMissingWasNullCall = value;
    }

    public Boolean isDiagnosticsRepeatedStatements() {
        return this.diagnosticsRepeatedStatements;
    }

    public void setDiagnosticsRepeatedStatements(Boolean value) {
        this.diagnosticsRepeatedStatements = value;
    }

    public Boolean isDiagnosticsConsecutiveAggregation() {
        return this.diagnosticsConsecutiveAggregation;
    }

    public void setDiagnosticsConsecutiveAggregation(Boolean value) {
        this.diagnosticsConsecutiveAggregation = value;
    }

    public Boolean isDiagnosticsConcatenationInPredicate() {
        return this.diagnosticsConcatenationInPredicate;
    }

    public void setDiagnosticsConcatenationInPredicate(Boolean value) {
        this.diagnosticsConcatenationInPredicate = value;
    }

    public Boolean isDiagnosticsPossiblyWrongExpression() {
        return this.diagnosticsPossiblyWrongExpression;
    }

    public void setDiagnosticsPossiblyWrongExpression(Boolean value) {
        this.diagnosticsPossiblyWrongExpression = value;
    }

    public Boolean isDiagnosticsTooManyColumnsFetched() {
        return this.diagnosticsTooManyColumnsFetched;
    }

    public void setDiagnosticsTooManyColumnsFetched(Boolean value) {
        this.diagnosticsTooManyColumnsFetched = value;
    }

    public Boolean isDiagnosticsTooManyRowsFetched() {
        return this.diagnosticsTooManyRowsFetched;
    }

    public void setDiagnosticsTooManyRowsFetched(Boolean value) {
        this.diagnosticsTooManyRowsFetched = value;
    }

    public Boolean isDiagnosticsUnnecessaryWasNullCall() {
        return this.diagnosticsUnnecessaryWasNullCall;
    }

    public void setDiagnosticsUnnecessaryWasNullCall(Boolean value) {
        this.diagnosticsUnnecessaryWasNullCall = value;
    }

    public Boolean isDiagnosticsPatterns() {
        return this.diagnosticsPatterns;
    }

    public void setDiagnosticsPatterns(Boolean value) {
        this.diagnosticsPatterns = value;
    }

    public Boolean isDiagnosticsTrivialCondition() {
        return this.diagnosticsTrivialCondition;
    }

    public void setDiagnosticsTrivialCondition(Boolean value) {
        this.diagnosticsTrivialCondition = value;
    }

    public Boolean isDiagnosticsNullCondition() {
        return this.diagnosticsNullCondition;
    }

    public void setDiagnosticsNullCondition(Boolean value) {
        this.diagnosticsNullCondition = value;
    }

    public Boolean isTransformPatterns() {
        return this.transformPatterns;
    }

    public void setTransformPatterns(Boolean value) {
        this.transformPatterns = value;
    }

    public Boolean isTransformPatternsLogging() {
        return this.transformPatternsLogging;
    }

    public void setTransformPatternsLogging(Boolean value) {
        this.transformPatternsLogging = value;
    }

    public Boolean isTransformPatternsUnnecessaryDistinct() {
        return this.transformPatternsUnnecessaryDistinct;
    }

    public void setTransformPatternsUnnecessaryDistinct(Boolean value) {
        this.transformPatternsUnnecessaryDistinct = value;
    }

    public Boolean isTransformPatternsUnnecessaryScalarSubquery() {
        return this.transformPatternsUnnecessaryScalarSubquery;
    }

    public void setTransformPatternsUnnecessaryScalarSubquery(Boolean value) {
        this.transformPatternsUnnecessaryScalarSubquery = value;
    }

    public Boolean isTransformPatternsUnnecessaryInnerJoin() {
        return this.transformPatternsUnnecessaryInnerJoin;
    }

    public void setTransformPatternsUnnecessaryInnerJoin(Boolean value) {
        this.transformPatternsUnnecessaryInnerJoin = value;
    }

    public Boolean isTransformPatternsUnnecessaryGroupByExpressions() {
        return this.transformPatternsUnnecessaryGroupByExpressions;
    }

    public void setTransformPatternsUnnecessaryGroupByExpressions(Boolean value) {
        this.transformPatternsUnnecessaryGroupByExpressions = value;
    }

    public Boolean isTransformPatternsUnnecessaryOrderByExpressions() {
        return this.transformPatternsUnnecessaryOrderByExpressions;
    }

    public void setTransformPatternsUnnecessaryOrderByExpressions(Boolean value) {
        this.transformPatternsUnnecessaryOrderByExpressions = value;
    }

    public Boolean isTransformPatternsUnnecessaryExistsSubqueryClauses() {
        return this.transformPatternsUnnecessaryExistsSubqueryClauses;
    }

    public void setTransformPatternsUnnecessaryExistsSubqueryClauses(Boolean value) {
        this.transformPatternsUnnecessaryExistsSubqueryClauses = value;
    }

    public Boolean isTransformPatternsCountConstant() {
        return this.transformPatternsCountConstant;
    }

    public void setTransformPatternsCountConstant(Boolean value) {
        this.transformPatternsCountConstant = value;
    }

    public Boolean isTransformPatternsTrim() {
        return this.transformPatternsTrim;
    }

    public void setTransformPatternsTrim(Boolean value) {
        this.transformPatternsTrim = value;
    }

    public Boolean isTransformPatternsNotAnd() {
        return this.transformPatternsNotAnd;
    }

    public void setTransformPatternsNotAnd(Boolean value) {
        this.transformPatternsNotAnd = value;
    }

    public Boolean isTransformPatternsNotOr() {
        return this.transformPatternsNotOr;
    }

    public void setTransformPatternsNotOr(Boolean value) {
        this.transformPatternsNotOr = value;
    }

    public Boolean isTransformPatternsNotNot() {
        return this.transformPatternsNotNot;
    }

    public void setTransformPatternsNotNot(Boolean value) {
        this.transformPatternsNotNot = value;
    }

    public Boolean isTransformPatternsNotComparison() {
        return this.transformPatternsNotComparison;
    }

    public void setTransformPatternsNotComparison(Boolean value) {
        this.transformPatternsNotComparison = value;
    }

    public Boolean isTransformPatternsNotNotDistinct() {
        return this.transformPatternsNotNotDistinct;
    }

    public void setTransformPatternsNotNotDistinct(Boolean value) {
        this.transformPatternsNotNotDistinct = value;
    }

    public Boolean isTransformPatternsDistinctFromNull() {
        return this.transformPatternsDistinctFromNull;
    }

    public void setTransformPatternsDistinctFromNull(Boolean value) {
        this.transformPatternsDistinctFromNull = value;
    }

    public Boolean isTransformPatternsNormaliseAssociativeOps() {
        return this.transformPatternsNormaliseAssociativeOps;
    }

    public void setTransformPatternsNormaliseAssociativeOps(Boolean value) {
        this.transformPatternsNormaliseAssociativeOps = value;
    }

    public Boolean isTransformPatternsNormaliseInListSingleElementToComparison() {
        return this.transformPatternsNormaliseInListSingleElementToComparison;
    }

    public void setTransformPatternsNormaliseInListSingleElementToComparison(Boolean value) {
        this.transformPatternsNormaliseInListSingleElementToComparison = value;
    }

    public Boolean isTransformPatternsNormaliseFieldCompareValue() {
        return this.transformPatternsNormaliseFieldCompareValue;
    }

    public void setTransformPatternsNormaliseFieldCompareValue(Boolean value) {
        this.transformPatternsNormaliseFieldCompareValue = value;
    }

    public Boolean isTransformPatternsNormaliseCoalesceToNvl() {
        return this.transformPatternsNormaliseCoalesceToNvl;
    }

    public void setTransformPatternsNormaliseCoalesceToNvl(Boolean value) {
        this.transformPatternsNormaliseCoalesceToNvl = value;
    }

    public Boolean isTransformPatternsOrEqToIn() {
        return this.transformPatternsOrEqToIn;
    }

    public void setTransformPatternsOrEqToIn(Boolean value) {
        this.transformPatternsOrEqToIn = value;
    }

    public Boolean isTransformPatternsAndNeToNotIn() {
        return this.transformPatternsAndNeToNotIn;
    }

    public void setTransformPatternsAndNeToNotIn(Boolean value) {
        this.transformPatternsAndNeToNotIn = value;
    }

    public Boolean isTransformPatternsMergeOrComparison() {
        return this.transformPatternsMergeOrComparison;
    }

    public void setTransformPatternsMergeOrComparison(Boolean value) {
        this.transformPatternsMergeOrComparison = value;
    }

    public Boolean isTransformPatternsMergeAndComparison() {
        return this.transformPatternsMergeAndComparison;
    }

    public void setTransformPatternsMergeAndComparison(Boolean value) {
        this.transformPatternsMergeAndComparison = value;
    }

    public Boolean isTransformPatternsMergeInLists() {
        return this.transformPatternsMergeInLists;
    }

    public void setTransformPatternsMergeInLists(Boolean value) {
        this.transformPatternsMergeInLists = value;
    }

    public Boolean isTransformPatternsMergeRangePredicates() {
        return this.transformPatternsMergeRangePredicates;
    }

    public void setTransformPatternsMergeRangePredicates(Boolean value) {
        this.transformPatternsMergeRangePredicates = value;
    }

    public Boolean isTransformPatternsMergeBetweenSymmetricPredicates() {
        return this.transformPatternsMergeBetweenSymmetricPredicates;
    }

    public void setTransformPatternsMergeBetweenSymmetricPredicates(Boolean value) {
        this.transformPatternsMergeBetweenSymmetricPredicates = value;
    }

    public Boolean isTransformPatternsCaseSearchedToCaseSimple() {
        return this.transformPatternsCaseSearchedToCaseSimple;
    }

    public void setTransformPatternsCaseSearchedToCaseSimple(Boolean value) {
        this.transformPatternsCaseSearchedToCaseSimple = value;
    }

    public Boolean isTransformPatternsCaseElseNull() {
        return this.transformPatternsCaseElseNull;
    }

    public void setTransformPatternsCaseElseNull(Boolean value) {
        this.transformPatternsCaseElseNull = value;
    }

    public Boolean isTransformPatternsUnreachableCaseClauses() {
        return this.transformPatternsUnreachableCaseClauses;
    }

    public void setTransformPatternsUnreachableCaseClauses(Boolean value) {
        this.transformPatternsUnreachableCaseClauses = value;
    }

    public Boolean isTransformPatternsUnreachableDecodeClauses() {
        return this.transformPatternsUnreachableDecodeClauses;
    }

    public void setTransformPatternsUnreachableDecodeClauses(Boolean value) {
        this.transformPatternsUnreachableDecodeClauses = value;
    }

    public Boolean isTransformPatternsCaseDistinctToDecode() {
        return this.transformPatternsCaseDistinctToDecode;
    }

    public void setTransformPatternsCaseDistinctToDecode(Boolean value) {
        this.transformPatternsCaseDistinctToDecode = value;
    }

    public Boolean isTransformPatternsCaseMergeWhenWhen() {
        return this.transformPatternsCaseMergeWhenWhen;
    }

    public void setTransformPatternsCaseMergeWhenWhen(Boolean value) {
        this.transformPatternsCaseMergeWhenWhen = value;
    }

    public Boolean isTransformPatternsCaseMergeWhenElse() {
        return this.transformPatternsCaseMergeWhenElse;
    }

    public void setTransformPatternsCaseMergeWhenElse(Boolean value) {
        this.transformPatternsCaseMergeWhenElse = value;
    }

    public Boolean isTransformPatternsCaseToCaseAbbreviation() {
        return this.transformPatternsCaseToCaseAbbreviation;
    }

    public void setTransformPatternsCaseToCaseAbbreviation(Boolean value) {
        this.transformPatternsCaseToCaseAbbreviation = value;
    }

    public Boolean isTransformPatternsSimplifyCaseAbbreviation() {
        return this.transformPatternsSimplifyCaseAbbreviation;
    }

    public void setTransformPatternsSimplifyCaseAbbreviation(Boolean value) {
        this.transformPatternsSimplifyCaseAbbreviation = value;
    }

    public Boolean isTransformPatternsFlattenCaseAbbreviation() {
        return this.transformPatternsFlattenCaseAbbreviation;
    }

    public void setTransformPatternsFlattenCaseAbbreviation(Boolean value) {
        this.transformPatternsFlattenCaseAbbreviation = value;
    }

    public Boolean isTransformPatternsFlattenDecode() {
        return this.transformPatternsFlattenDecode;
    }

    public void setTransformPatternsFlattenDecode(Boolean value) {
        this.transformPatternsFlattenDecode = value;
    }

    public Boolean isTransformPatternsFlattenCase() {
        return this.transformPatternsFlattenCase;
    }

    public void setTransformPatternsFlattenCase(Boolean value) {
        this.transformPatternsFlattenCase = value;
    }

    public Boolean isTransformPatternsTrivialCaseAbbreviation() {
        return this.transformPatternsTrivialCaseAbbreviation;
    }

    public void setTransformPatternsTrivialCaseAbbreviation(Boolean value) {
        this.transformPatternsTrivialCaseAbbreviation = value;
    }

    public Boolean isTransformPatternsTrivialPredicates() {
        return this.transformPatternsTrivialPredicates;
    }

    public void setTransformPatternsTrivialPredicates(Boolean value) {
        this.transformPatternsTrivialPredicates = value;
    }

    public Boolean isTransformPatternsTrivialBitwiseOperations() {
        return this.transformPatternsTrivialBitwiseOperations;
    }

    public void setTransformPatternsTrivialBitwiseOperations(Boolean value) {
        this.transformPatternsTrivialBitwiseOperations = value;
    }

    public Boolean isTransformPatternsBitSet() {
        return this.transformPatternsBitSet;
    }

    public void setTransformPatternsBitSet(Boolean value) {
        this.transformPatternsBitSet = value;
    }

    public Boolean isTransformPatternsBitGet() {
        return this.transformPatternsBitGet;
    }

    public void setTransformPatternsBitGet(Boolean value) {
        this.transformPatternsBitGet = value;
    }

    public Boolean isTransformPatternsScalarSubqueryCountAsteriskGtZero() {
        return this.transformPatternsScalarSubqueryCountAsteriskGtZero;
    }

    public void setTransformPatternsScalarSubqueryCountAsteriskGtZero(Boolean value) {
        this.transformPatternsScalarSubqueryCountAsteriskGtZero = value;
    }

    public Boolean isTransformPatternsScalarSubqueryCountExpressionGtZero() {
        return this.transformPatternsScalarSubqueryCountExpressionGtZero;
    }

    public void setTransformPatternsScalarSubqueryCountExpressionGtZero(Boolean value) {
        this.transformPatternsScalarSubqueryCountExpressionGtZero = value;
    }

    public Boolean isTransformPatternsEmptyScalarSubquery() {
        return this.transformPatternsEmptyScalarSubquery;
    }

    public void setTransformPatternsEmptyScalarSubquery(Boolean value) {
        this.transformPatternsEmptyScalarSubquery = value;
    }

    public Boolean isTransformPatternsNegNeg() {
        return this.transformPatternsNegNeg;
    }

    public void setTransformPatternsNegNeg(Boolean value) {
        this.transformPatternsNegNeg = value;
    }

    public Boolean isTransformPatternsBitNotBitNot() {
        return this.transformPatternsBitNotBitNot;
    }

    public void setTransformPatternsBitNotBitNot(Boolean value) {
        this.transformPatternsBitNotBitNot = value;
    }

    public Boolean isTransformPatternsBitNotBitNand() {
        return this.transformPatternsBitNotBitNand;
    }

    public void setTransformPatternsBitNotBitNand(Boolean value) {
        this.transformPatternsBitNotBitNand = value;
    }

    public Boolean isTransformPatternsBitNotBitNor() {
        return this.transformPatternsBitNotBitNor;
    }

    public void setTransformPatternsBitNotBitNor(Boolean value) {
        this.transformPatternsBitNotBitNor = value;
    }

    public Boolean isTransformPatternsBitNotBitXNor() {
        return this.transformPatternsBitNotBitXNor;
    }

    public void setTransformPatternsBitNotBitXNor(Boolean value) {
        this.transformPatternsBitNotBitXNor = value;
    }

    public Boolean isTransformPatternsNullOnNullInput() {
        return this.transformPatternsNullOnNullInput;
    }

    public void setTransformPatternsNullOnNullInput(Boolean value) {
        this.transformPatternsNullOnNullInput = value;
    }

    public Boolean isTransformPatternsIdempotentFunctionRepetition() {
        return this.transformPatternsIdempotentFunctionRepetition;
    }

    public void setTransformPatternsIdempotentFunctionRepetition(Boolean value) {
        this.transformPatternsIdempotentFunctionRepetition = value;
    }

    public Boolean isTransformPatternsArithmeticComparisons() {
        return this.transformPatternsArithmeticComparisons;
    }

    public void setTransformPatternsArithmeticComparisons(Boolean value) {
        this.transformPatternsArithmeticComparisons = value;
    }

    public Boolean isTransformPatternsArithmeticExpressions() {
        return this.transformPatternsArithmeticExpressions;
    }

    public void setTransformPatternsArithmeticExpressions(Boolean value) {
        this.transformPatternsArithmeticExpressions = value;
    }

    public Boolean isTransformPatternsTrigonometricFunctions() {
        return this.transformPatternsTrigonometricFunctions;
    }

    public void setTransformPatternsTrigonometricFunctions(Boolean value) {
        this.transformPatternsTrigonometricFunctions = value;
    }

    public Boolean isTransformPatternsLogarithmicFunctions() {
        return this.transformPatternsLogarithmicFunctions;
    }

    public void setTransformPatternsLogarithmicFunctions(Boolean value) {
        this.transformPatternsLogarithmicFunctions = value;
    }

    public Boolean isTransformPatternsHyperbolicFunctions() {
        return this.transformPatternsHyperbolicFunctions;
    }

    public void setTransformPatternsHyperbolicFunctions(Boolean value) {
        this.transformPatternsHyperbolicFunctions = value;
    }

    public Boolean isTransformPatternsInverseHyperbolicFunctions() {
        return this.transformPatternsInverseHyperbolicFunctions;
    }

    public void setTransformPatternsInverseHyperbolicFunctions(Boolean value) {
        this.transformPatternsInverseHyperbolicFunctions = value;
    }

    public Boolean isTransformInlineBindValuesForFieldComparisons() {
        return this.transformInlineBindValuesForFieldComparisons;
    }

    public void setTransformInlineBindValuesForFieldComparisons(Boolean value) {
        this.transformInlineBindValuesForFieldComparisons = value;
    }

    public Boolean isTransformAnsiJoinToTableLists() {
        return this.transformAnsiJoinToTableLists;
    }

    public void setTransformAnsiJoinToTableLists(Boolean value) {
        this.transformAnsiJoinToTableLists = value;
    }

    @Deprecated
    public Transformation getTransformInConditionSubqueryWithLimitToDerivedTable() {
        return this.transformInConditionSubqueryWithLimitToDerivedTable;
    }

    @Deprecated
    public void setTransformInConditionSubqueryWithLimitToDerivedTable(Transformation value) {
        this.transformInConditionSubqueryWithLimitToDerivedTable = value;
    }

    public Transformation getTransformQualify() {
        return this.transformQualify;
    }

    public void setTransformQualify(Transformation value) {
        this.transformQualify = value;
    }

    public Boolean isTransformTableListsToAnsiJoin() {
        return this.transformTableListsToAnsiJoin;
    }

    public void setTransformTableListsToAnsiJoin(Boolean value) {
        this.transformTableListsToAnsiJoin = value;
    }

    public Transformation getTransformRownum() {
        return this.transformRownum;
    }

    public void setTransformRownum(Transformation value) {
        this.transformRownum = value;
    }

    public TransformUnneededArithmeticExpressions getTransformUnneededArithmeticExpressions() {
        return this.transformUnneededArithmeticExpressions;
    }

    public void setTransformUnneededArithmeticExpressions(TransformUnneededArithmeticExpressions value) {
        this.transformUnneededArithmeticExpressions = value;
    }

    public Transformation getTransformGroupByColumnIndex() {
        return this.transformGroupByColumnIndex;
    }

    public void setTransformGroupByColumnIndex(Transformation value) {
        this.transformGroupByColumnIndex = value;
    }

    public Transformation getTransformInlineCTE() {
        return this.transformInlineCTE;
    }

    public void setTransformInlineCTE(Transformation value) {
        this.transformInlineCTE = value;
    }

    public BackslashEscaping getBackslashEscaping() {
        return this.backslashEscaping;
    }

    public void setBackslashEscaping(BackslashEscaping value) {
        this.backslashEscaping = value;
    }

    public ParamType getParamType() {
        return this.paramType;
    }

    public void setParamType(ParamType value) {
        this.paramType = value;
    }

    public ParamCastMode getParamCastMode() {
        return this.paramCastMode;
    }

    public void setParamCastMode(ParamCastMode value) {
        this.paramCastMode = value;
    }

    public StatementType getStatementType() {
        return this.statementType;
    }

    public void setStatementType(StatementType value) {
        this.statementType = value;
    }

    public Integer getInlineThreshold() {
        return this.inlineThreshold;
    }

    public void setInlineThreshold(Integer value) {
        this.inlineThreshold = value;
    }

    public InvocationOrder getTransactionListenerStartInvocationOrder() {
        return this.transactionListenerStartInvocationOrder;
    }

    public void setTransactionListenerStartInvocationOrder(InvocationOrder value) {
        this.transactionListenerStartInvocationOrder = value;
    }

    public InvocationOrder getTransactionListenerEndInvocationOrder() {
        return this.transactionListenerEndInvocationOrder;
    }

    public void setTransactionListenerEndInvocationOrder(InvocationOrder value) {
        this.transactionListenerEndInvocationOrder = value;
    }

    public InvocationOrder getMigrationListenerStartInvocationOrder() {
        return this.migrationListenerStartInvocationOrder;
    }

    public void setMigrationListenerStartInvocationOrder(InvocationOrder value) {
        this.migrationListenerStartInvocationOrder = value;
    }

    public InvocationOrder getMigrationListenerEndInvocationOrder() {
        return this.migrationListenerEndInvocationOrder;
    }

    public void setMigrationListenerEndInvocationOrder(InvocationOrder value) {
        this.migrationListenerEndInvocationOrder = value;
    }

    public InvocationOrder getVisitListenerStartInvocationOrder() {
        return this.visitListenerStartInvocationOrder;
    }

    public void setVisitListenerStartInvocationOrder(InvocationOrder value) {
        this.visitListenerStartInvocationOrder = value;
    }

    public InvocationOrder getVisitListenerEndInvocationOrder() {
        return this.visitListenerEndInvocationOrder;
    }

    public void setVisitListenerEndInvocationOrder(InvocationOrder value) {
        this.visitListenerEndInvocationOrder = value;
    }

    public InvocationOrder getRecordListenerStartInvocationOrder() {
        return this.recordListenerStartInvocationOrder;
    }

    public void setRecordListenerStartInvocationOrder(InvocationOrder value) {
        this.recordListenerStartInvocationOrder = value;
    }

    public InvocationOrder getRecordListenerEndInvocationOrder() {
        return this.recordListenerEndInvocationOrder;
    }

    public void setRecordListenerEndInvocationOrder(InvocationOrder value) {
        this.recordListenerEndInvocationOrder = value;
    }

    public InvocationOrder getExecuteListenerStartInvocationOrder() {
        return this.executeListenerStartInvocationOrder;
    }

    public void setExecuteListenerStartInvocationOrder(InvocationOrder value) {
        this.executeListenerStartInvocationOrder = value;
    }

    public InvocationOrder getExecuteListenerEndInvocationOrder() {
        return this.executeListenerEndInvocationOrder;
    }

    public void setExecuteListenerEndInvocationOrder(InvocationOrder value) {
        this.executeListenerEndInvocationOrder = value;
    }

    public Boolean isExecuteLogging() {
        return this.executeLogging;
    }

    public void setExecuteLogging(Boolean value) {
        this.executeLogging = value;
    }

    public Boolean isExecuteLoggingSQLExceptions() {
        return this.executeLoggingSQLExceptions;
    }

    public void setExecuteLoggingSQLExceptions(Boolean value) {
        this.executeLoggingSQLExceptions = value;
    }

    public Boolean isDiagnosticsLogging() {
        return this.diagnosticsLogging;
    }

    public void setDiagnosticsLogging(Boolean value) {
        this.diagnosticsLogging = value;
    }

    public DiagnosticsConnection getDiagnosticsConnection() {
        return this.diagnosticsConnection;
    }

    public void setDiagnosticsConnection(DiagnosticsConnection value) {
        this.diagnosticsConnection = value;
    }

    public Boolean isUpdateRecordVersion() {
        return this.updateRecordVersion;
    }

    public void setUpdateRecordVersion(Boolean value) {
        this.updateRecordVersion = value;
    }

    public Boolean isUpdateRecordTimestamp() {
        return this.updateRecordTimestamp;
    }

    public void setUpdateRecordTimestamp(Boolean value) {
        this.updateRecordTimestamp = value;
    }

    public Boolean isExecuteWithOptimisticLocking() {
        return this.executeWithOptimisticLocking;
    }

    public void setExecuteWithOptimisticLocking(Boolean value) {
        this.executeWithOptimisticLocking = value;
    }

    public Boolean isExecuteWithOptimisticLockingExcludeUnversioned() {
        return this.executeWithOptimisticLockingExcludeUnversioned;
    }

    public void setExecuteWithOptimisticLockingExcludeUnversioned(Boolean value) {
        this.executeWithOptimisticLockingExcludeUnversioned = value;
    }

    public Boolean isAttachRecords() {
        return this.attachRecords;
    }

    public void setAttachRecords(Boolean value) {
        this.attachRecords = value;
    }

    public Boolean isInsertUnchangedRecords() {
        return this.insertUnchangedRecords;
    }

    public void setInsertUnchangedRecords(Boolean value) {
        this.insertUnchangedRecords = value;
    }

    public UpdateUnchangedRecords getUpdateUnchangedRecords() {
        return this.updateUnchangedRecords;
    }

    public void setUpdateUnchangedRecords(UpdateUnchangedRecords value) {
        this.updateUnchangedRecords = value;
    }

    public Boolean isUpdatablePrimaryKeys() {
        return this.updatablePrimaryKeys;
    }

    public void setUpdatablePrimaryKeys(Boolean value) {
        this.updatablePrimaryKeys = value;
    }

    public Boolean isReflectionCaching() {
        return this.reflectionCaching;
    }

    public void setReflectionCaching(Boolean value) {
        this.reflectionCaching = value;
    }

    public Boolean isCacheRecordMappers() {
        return this.cacheRecordMappers;
    }

    public void setCacheRecordMappers(Boolean value) {
        this.cacheRecordMappers = value;
    }

    public Boolean isCacheParsingConnection() {
        return this.cacheParsingConnection;
    }

    public void setCacheParsingConnection(Boolean value) {
        this.cacheParsingConnection = value;
    }

    public Integer getCacheParsingConnectionLRUCacheSize() {
        return this.cacheParsingConnectionLRUCacheSize;
    }

    public void setCacheParsingConnectionLRUCacheSize(Integer value) {
        this.cacheParsingConnectionLRUCacheSize = value;
    }

    public Boolean isCachePreparedStatementInLoader() {
        return this.cachePreparedStatementInLoader;
    }

    public void setCachePreparedStatementInLoader(Boolean value) {
        this.cachePreparedStatementInLoader = value;
    }

    public ThrowExceptions getThrowExceptions() {
        return this.throwExceptions;
    }

    public void setThrowExceptions(ThrowExceptions value) {
        this.throwExceptions = value;
    }

    public Boolean isFetchWarnings() {
        return this.fetchWarnings;
    }

    public void setFetchWarnings(Boolean value) {
        this.fetchWarnings = value;
    }

    public Integer getFetchServerOutputSize() {
        return this.fetchServerOutputSize;
    }

    public void setFetchServerOutputSize(Integer value) {
        this.fetchServerOutputSize = value;
    }

    public Boolean isReturnIdentityOnUpdatableRecord() {
        return this.returnIdentityOnUpdatableRecord;
    }

    public void setReturnIdentityOnUpdatableRecord(Boolean value) {
        this.returnIdentityOnUpdatableRecord = value;
    }

    public Boolean isReturnDefaultOnUpdatableRecord() {
        return this.returnDefaultOnUpdatableRecord;
    }

    public void setReturnDefaultOnUpdatableRecord(Boolean value) {
        this.returnDefaultOnUpdatableRecord = value;
    }

    public Boolean isReturnComputedOnUpdatableRecord() {
        return this.returnComputedOnUpdatableRecord;
    }

    public void setReturnComputedOnUpdatableRecord(Boolean value) {
        this.returnComputedOnUpdatableRecord = value;
    }

    public Boolean isReturnAllOnUpdatableRecord() {
        return this.returnAllOnUpdatableRecord;
    }

    public void setReturnAllOnUpdatableRecord(Boolean value) {
        this.returnAllOnUpdatableRecord = value;
    }

    public Boolean isReturnRecordToPojo() {
        return this.returnRecordToPojo;
    }

    public void setReturnRecordToPojo(Boolean value) {
        this.returnRecordToPojo = value;
    }

    public Boolean isMapJPAAnnotations() {
        return this.mapJPAAnnotations;
    }

    public void setMapJPAAnnotations(Boolean value) {
        this.mapJPAAnnotations = value;
    }

    public Boolean isMapRecordComponentParameterNames() {
        return this.mapRecordComponentParameterNames;
    }

    public void setMapRecordComponentParameterNames(Boolean value) {
        this.mapRecordComponentParameterNames = value;
    }

    public Boolean isMapConstructorPropertiesParameterNames() {
        return this.mapConstructorPropertiesParameterNames;
    }

    public void setMapConstructorPropertiesParameterNames(Boolean value) {
        this.mapConstructorPropertiesParameterNames = value;
    }

    public Boolean isMapConstructorParameterNames() {
        return this.mapConstructorParameterNames;
    }

    public void setMapConstructorParameterNames(Boolean value) {
        this.mapConstructorParameterNames = value;
    }

    public Boolean isMapConstructorParameterNamesInKotlin() {
        return this.mapConstructorParameterNamesInKotlin;
    }

    public void setMapConstructorParameterNamesInKotlin(Boolean value) {
        this.mapConstructorParameterNamesInKotlin = value;
    }

    public QueryPoolable getQueryPoolable() {
        return this.queryPoolable;
    }

    public void setQueryPoolable(QueryPoolable value) {
        this.queryPoolable = value;
    }

    public Integer getQueryTimeout() {
        return this.queryTimeout;
    }

    public void setQueryTimeout(Integer value) {
        this.queryTimeout = value;
    }

    public Integer getMaxRows() {
        return this.maxRows;
    }

    public void setMaxRows(Integer value) {
        this.maxRows = value;
    }

    public Integer getFetchSize() {
        return this.fetchSize;
    }

    public void setFetchSize(Integer value) {
        this.fetchSize = value;
    }

    public Integer getBatchSize() {
        return this.batchSize;
    }

    public void setBatchSize(Integer value) {
        this.batchSize = value;
    }

    public Boolean isDebugInfoOnStackTrace() {
        return this.debugInfoOnStackTrace;
    }

    public void setDebugInfoOnStackTrace(Boolean value) {
        this.debugInfoOnStackTrace = value;
    }

    public Boolean isInListPadding() {
        return this.inListPadding;
    }

    public void setInListPadding(Boolean value) {
        this.inListPadding = value;
    }

    public Integer getInListPadBase() {
        return this.inListPadBase;
    }

    public void setInListPadBase(Integer value) {
        this.inListPadBase = value;
    }

    public String getDelimiter() {
        return this.delimiter;
    }

    public void setDelimiter(String value) {
        this.delimiter = value;
    }

    public Boolean isEmulateOnDuplicateKeyUpdateOnPrimaryKeyOnly() {
        return this.emulateOnDuplicateKeyUpdateOnPrimaryKeyOnly;
    }

    public void setEmulateOnDuplicateKeyUpdateOnPrimaryKeyOnly(Boolean value) {
        this.emulateOnDuplicateKeyUpdateOnPrimaryKeyOnly = value;
    }

    public NestedCollectionEmulation getEmulateMultiset() {
        return this.emulateMultiset;
    }

    public void setEmulateMultiset(NestedCollectionEmulation value) {
        this.emulateMultiset = value;
    }

    public Boolean isEmulateComputedColumns() {
        return this.emulateComputedColumns;
    }

    public void setEmulateComputedColumns(Boolean value) {
        this.emulateComputedColumns = value;
    }

    public ExecuteWithoutWhere getExecuteUpdateWithoutWhere() {
        return this.executeUpdateWithoutWhere;
    }

    public void setExecuteUpdateWithoutWhere(ExecuteWithoutWhere value) {
        this.executeUpdateWithoutWhere = value;
    }

    public ExecuteWithoutWhere getExecuteDeleteWithoutWhere() {
        return this.executeDeleteWithoutWhere;
    }

    public void setExecuteDeleteWithoutWhere(ExecuteWithoutWhere value) {
        this.executeDeleteWithoutWhere = value;
    }

    public SQLDialect getInterpreterDialect() {
        return this.interpreterDialect;
    }

    public void setInterpreterDialect(SQLDialect value) {
        this.interpreterDialect = value;
    }

    public InterpreterNameLookupCaseSensitivity getInterpreterNameLookupCaseSensitivity() {
        return this.interpreterNameLookupCaseSensitivity;
    }

    public void setInterpreterNameLookupCaseSensitivity(InterpreterNameLookupCaseSensitivity value) {
        this.interpreterNameLookupCaseSensitivity = value;
    }

    public Locale getInterpreterLocale() {
        return this.interpreterLocale;
    }

    public void setInterpreterLocale(Locale value) {
        this.interpreterLocale = value;
    }

    public Boolean isInterpreterDelayForeignKeyDeclarations() {
        return this.interpreterDelayForeignKeyDeclarations;
    }

    public void setInterpreterDelayForeignKeyDeclarations(Boolean value) {
        this.interpreterDelayForeignKeyDeclarations = value;
    }

    public Boolean isMetaIncludeSystemIndexes() {
        return this.metaIncludeSystemIndexes;
    }

    public void setMetaIncludeSystemIndexes(Boolean value) {
        this.metaIncludeSystemIndexes = value;
    }

    public Boolean isMetaIncludeSystemSequences() {
        return this.metaIncludeSystemSequences;
    }

    public void setMetaIncludeSystemSequences(Boolean value) {
        this.metaIncludeSystemSequences = value;
    }

    public Boolean isMigrationAllowsUndo() {
        return this.migrationAllowsUndo;
    }

    public void setMigrationAllowsUndo(Boolean value) {
        this.migrationAllowsUndo = value;
    }

    public Boolean isMigrationRevertUntracked() {
        return this.migrationRevertUntracked;
    }

    public void setMigrationRevertUntracked(Boolean value) {
        this.migrationRevertUntracked = value;
    }

    public Boolean isMigrationAutoBaseline() {
        return this.migrationAutoBaseline;
    }

    public void setMigrationAutoBaseline(Boolean value) {
        this.migrationAutoBaseline = value;
    }

    public Boolean isMigrationAutoValidation() {
        return this.migrationAutoValidation;
    }

    public void setMigrationAutoValidation(Boolean value) {
        this.migrationAutoValidation = value;
    }

    public Boolean isMigrationIgnoreDefaultTimestampPrecisionDiffs() {
        return this.migrationIgnoreDefaultTimestampPrecisionDiffs;
    }

    public void setMigrationIgnoreDefaultTimestampPrecisionDiffs(Boolean value) {
        this.migrationIgnoreDefaultTimestampPrecisionDiffs = value;
    }

    public Locale getLocale() {
        return this.locale;
    }

    public void setLocale(Locale value) {
        this.locale = value;
    }

    public SQLDialect getParseDialect() {
        return this.parseDialect;
    }

    public void setParseDialect(SQLDialect value) {
        this.parseDialect = value;
    }

    public Locale getParseLocale() {
        return this.parseLocale;
    }

    public void setParseLocale(Locale value) {
        this.parseLocale = value;
    }

    public String getParseDateFormat() {
        return this.parseDateFormat;
    }

    public void setParseDateFormat(String value) {
        this.parseDateFormat = value;
    }

    public String getParseTimestampFormat() {
        return this.parseTimestampFormat;
    }

    public void setParseTimestampFormat(String value) {
        this.parseTimestampFormat = value;
    }

    public String getParseNamedParamPrefix() {
        return this.parseNamedParamPrefix;
    }

    public void setParseNamedParamPrefix(String value) {
        this.parseNamedParamPrefix = value;
    }

    public ParseNameCase getParseNameCase() {
        return this.parseNameCase;
    }

    public void setParseNameCase(ParseNameCase value) {
        this.parseNameCase = value;
    }

    public ParseWithMetaLookups getParseWithMetaLookups() {
        return this.parseWithMetaLookups;
    }

    public void setParseWithMetaLookups(ParseWithMetaLookups value) {
        this.parseWithMetaLookups = value;
    }

    public Transformation getParseAppendMissingTableReferences() {
        return this.parseAppendMissingTableReferences;
    }

    public void setParseAppendMissingTableReferences(Transformation value) {
        this.parseAppendMissingTableReferences = value;
    }

    public Boolean isParseSetCommands() {
        return this.parseSetCommands;
    }

    public void setParseSetCommands(Boolean value) {
        this.parseSetCommands = value;
    }

    public ParseUnsupportedSyntax getParseUnsupportedSyntax() {
        return this.parseUnsupportedSyntax;
    }

    public void setParseUnsupportedSyntax(ParseUnsupportedSyntax value) {
        this.parseUnsupportedSyntax = value;
    }

    public ParseUnknownFunctions getParseUnknownFunctions() {
        return this.parseUnknownFunctions;
    }

    public void setParseUnknownFunctions(ParseUnknownFunctions value) {
        this.parseUnknownFunctions = value;
    }

    public Boolean isParseIgnoreCommercialOnlyFeatures() {
        return this.parseIgnoreCommercialOnlyFeatures;
    }

    public void setParseIgnoreCommercialOnlyFeatures(Boolean value) {
        this.parseIgnoreCommercialOnlyFeatures = value;
    }

    public Boolean isParseIgnoreComments() {
        return this.parseIgnoreComments;
    }

    public void setParseIgnoreComments(Boolean value) {
        this.parseIgnoreComments = value;
    }

    public String getParseIgnoreCommentStart() {
        return this.parseIgnoreCommentStart;
    }

    public void setParseIgnoreCommentStart(String value) {
        this.parseIgnoreCommentStart = value;
    }

    public String getParseIgnoreCommentStop() {
        return this.parseIgnoreCommentStop;
    }

    public void setParseIgnoreCommentStop(String value) {
        this.parseIgnoreCommentStop = value;
    }

    public Boolean isParseRetainCommentsBetweenQueries() {
        return this.parseRetainCommentsBetweenQueries;
    }

    public void setParseRetainCommentsBetweenQueries(Boolean value) {
        this.parseRetainCommentsBetweenQueries = value;
    }

    public Boolean isParseMetaDefaultExpressions() {
        return this.parseMetaDefaultExpressions;
    }

    public void setParseMetaDefaultExpressions(Boolean value) {
        this.parseMetaDefaultExpressions = value;
    }

    public WriteIfReadonly getReadonlyTableRecordInsert() {
        return this.readonlyTableRecordInsert;
    }

    public void setReadonlyTableRecordInsert(WriteIfReadonly value) {
        this.readonlyTableRecordInsert = value;
    }

    public WriteIfReadonly getReadonlyUpdatableRecordUpdate() {
        return this.readonlyUpdatableRecordUpdate;
    }

    public void setReadonlyUpdatableRecordUpdate(WriteIfReadonly value) {
        this.readonlyUpdatableRecordUpdate = value;
    }

    public WriteIfReadonly getReadonlyInsert() {
        return this.readonlyInsert;
    }

    public void setReadonlyInsert(WriteIfReadonly value) {
        this.readonlyInsert = value;
    }

    public WriteIfReadonly getReadonlyUpdate() {
        return this.readonlyUpdate;
    }

    public void setReadonlyUpdate(WriteIfReadonly value) {
        this.readonlyUpdate = value;
    }

    public Boolean isApplyWorkaroundFor7962() {
        return this.applyWorkaroundFor7962;
    }

    public void setApplyWorkaroundFor7962(Boolean value) {
        this.applyWorkaroundFor7962 = value;
    }

    public List<InterpreterSearchSchema> getInterpreterSearchPath() {
        if (this.interpreterSearchPath == null) {
            this.interpreterSearchPath = new ArrayList<InterpreterSearchSchema>();
        }
        return this.interpreterSearchPath;
    }

    public void setInterpreterSearchPath(List<InterpreterSearchSchema> interpreterSearchPath) {
        this.interpreterSearchPath = interpreterSearchPath;
    }

    public List<MigrationSchema> getMigrationSchemata() {
        if (this.migrationSchemata == null) {
            this.migrationSchemata = new ArrayList<MigrationSchema>();
        }
        return this.migrationSchemata;
    }

    public void setMigrationSchemata(List<MigrationSchema> migrationSchemata) {
        this.migrationSchemata = migrationSchemata;
    }

    public List<ParseSearchSchema> getParseSearchPath() {
        if (this.parseSearchPath == null) {
            this.parseSearchPath = new ArrayList<ParseSearchSchema>();
        }
        return this.parseSearchPath;
    }

    public void setParseSearchPath(List<ParseSearchSchema> parseSearchPath) {
        this.parseSearchPath = parseSearchPath;
    }

    public Settings withForceIntegerTypesOnZeroScaleDecimals(Boolean value) {
        this.setForceIntegerTypesOnZeroScaleDecimals(value);
        return this;
    }

    public Settings withRenderCatalog(Boolean value) {
        this.setRenderCatalog(value);
        return this;
    }

    public Settings withRenderSchema(Boolean value) {
        this.setRenderSchema(value);
        return this;
    }

    public Settings withRenderTable(RenderTable value) {
        this.setRenderTable(value);
        return this;
    }

    public Settings withRenderMapping(RenderMapping value) {
        this.setRenderMapping(value);
        return this;
    }

    public Settings withRenderQuotedNames(RenderQuotedNames value) {
        this.setRenderQuotedNames(value);
        return this;
    }

    public Settings withRenderNameCase(RenderNameCase value) {
        this.setRenderNameCase(value);
        return this;
    }

    @Deprecated
    public Settings withRenderNameStyle(RenderNameStyle value) {
        this.setRenderNameStyle(value);
        return this;
    }

    public Settings withRenderNamedParamPrefix(String value) {
        this.setRenderNamedParamPrefix(value);
        return this;
    }

    public Settings withRenderKeywordCase(RenderKeywordCase value) {
        this.setRenderKeywordCase(value);
        return this;
    }

    @Deprecated
    public Settings withRenderKeywordStyle(RenderKeywordStyle value) {
        this.setRenderKeywordStyle(value);
        return this;
    }

    public Settings withRenderLocale(Locale value) {
        this.setRenderLocale(value);
        return this;
    }

    public Settings withRenderFormatted(Boolean value) {
        this.setRenderFormatted(value);
        return this;
    }

    public Settings withRenderFormatting(RenderFormatting value) {
        this.setRenderFormatting(value);
        return this;
    }

    public Settings withRenderOptionalAssociativityParentheses(RenderOptionalKeyword value) {
        this.setRenderOptionalAssociativityParentheses(value);
        return this;
    }

    public Settings withRenderOptionalAsKeywordForTableAliases(RenderOptionalKeyword value) {
        this.setRenderOptionalAsKeywordForTableAliases(value);
        return this;
    }

    public Settings withRenderOptionalAsKeywordForFieldAliases(RenderOptionalKeyword value) {
        this.setRenderOptionalAsKeywordForFieldAliases(value);
        return this;
    }

    public Settings withRenderOptionalInnerKeyword(RenderOptionalKeyword value) {
        this.setRenderOptionalInnerKeyword(value);
        return this;
    }

    public Settings withRenderOptionalOuterKeyword(RenderOptionalKeyword value) {
        this.setRenderOptionalOuterKeyword(value);
        return this;
    }

    public Settings withRenderImplicitWindowRange(RenderImplicitWindowRange value) {
        this.setRenderImplicitWindowRange(value);
        return this;
    }

    public Settings withRenderScalarSubqueriesForStoredFunctions(Boolean value) {
        this.setRenderScalarSubqueriesForStoredFunctions(value);
        return this;
    }

    public Settings withRenderImplicitJoinType(RenderImplicitJoinType value) {
        this.setRenderImplicitJoinType(value);
        return this;
    }

    public Settings withRenderDefaultNullability(RenderDefaultNullability value) {
        this.setRenderDefaultNullability(value);
        return this;
    }

    public Settings withRenderCoalesceToEmptyStringInConcat(Boolean value) {
        this.setRenderCoalesceToEmptyStringInConcat(value);
        return this;
    }

    public Settings withRenderOrderByRownumberForEmulatedPagination(Boolean value) {
        this.setRenderOrderByRownumberForEmulatedPagination(value);
        return this;
    }

    public Settings withRenderOutputForSQLServerReturningClause(Boolean value) {
        this.setRenderOutputForSQLServerReturningClause(value);
        return this;
    }

    public Settings withRenderGroupConcatMaxLenSessionVariable(Boolean value) {
        this.setRenderGroupConcatMaxLenSessionVariable(value);
        return this;
    }

    public Settings withRenderParenthesisAroundSetOperationQueries(Boolean value) {
        this.setRenderParenthesisAroundSetOperationQueries(value);
        return this;
    }

    public Settings withRenderVariablesInDerivedTablesForEmulations(Boolean value) {
        this.setRenderVariablesInDerivedTablesForEmulations(value);
        return this;
    }

    public Settings withRenderRowConditionForSeekClause(Boolean value) {
        this.setRenderRowConditionForSeekClause(value);
        return this;
    }

    public Settings withRenderRedundantConditionForSeekClause(Boolean value) {
        this.setRenderRedundantConditionForSeekClause(value);
        return this;
    }

    public Settings withRenderPlainSQLTemplatesAsRaw(Boolean value) {
        this.setRenderPlainSQLTemplatesAsRaw(value);
        return this;
    }

    public Settings withNamePathSeparator(String value) {
        this.setNamePathSeparator(value);
        return this;
    }

    public Settings withBindOffsetDateTimeType(Boolean value) {
        this.setBindOffsetDateTimeType(value);
        return this;
    }

    public Settings withBindOffsetTimeType(Boolean value) {
        this.setBindOffsetTimeType(value);
        return this;
    }

    public Settings withFetchTriggerValuesAfterSQLServerOutput(Boolean value) {
        this.setFetchTriggerValuesAfterSQLServerOutput(value);
        return this;
    }

    public Settings withFetchIntermediateResult(FetchIntermediateResult value) {
        this.setFetchIntermediateResult(value);
        return this;
    }

    public Settings withDiagnosticsDuplicateStatements(Boolean value) {
        this.setDiagnosticsDuplicateStatements(value);
        return this;
    }

    public Settings withDiagnosticsDuplicateStatementsUsingTransformPatterns(Boolean value) {
        this.setDiagnosticsDuplicateStatementsUsingTransformPatterns(value);
        return this;
    }

    public Settings withDiagnosticsMissingWasNullCall(Boolean value) {
        this.setDiagnosticsMissingWasNullCall(value);
        return this;
    }

    public Settings withDiagnosticsRepeatedStatements(Boolean value) {
        this.setDiagnosticsRepeatedStatements(value);
        return this;
    }

    public Settings withDiagnosticsConsecutiveAggregation(Boolean value) {
        this.setDiagnosticsConsecutiveAggregation(value);
        return this;
    }

    public Settings withDiagnosticsConcatenationInPredicate(Boolean value) {
        this.setDiagnosticsConcatenationInPredicate(value);
        return this;
    }

    public Settings withDiagnosticsPossiblyWrongExpression(Boolean value) {
        this.setDiagnosticsPossiblyWrongExpression(value);
        return this;
    }

    public Settings withDiagnosticsTooManyColumnsFetched(Boolean value) {
        this.setDiagnosticsTooManyColumnsFetched(value);
        return this;
    }

    public Settings withDiagnosticsTooManyRowsFetched(Boolean value) {
        this.setDiagnosticsTooManyRowsFetched(value);
        return this;
    }

    public Settings withDiagnosticsUnnecessaryWasNullCall(Boolean value) {
        this.setDiagnosticsUnnecessaryWasNullCall(value);
        return this;
    }

    public Settings withDiagnosticsPatterns(Boolean value) {
        this.setDiagnosticsPatterns(value);
        return this;
    }

    public Settings withDiagnosticsTrivialCondition(Boolean value) {
        this.setDiagnosticsTrivialCondition(value);
        return this;
    }

    public Settings withDiagnosticsNullCondition(Boolean value) {
        this.setDiagnosticsNullCondition(value);
        return this;
    }

    public Settings withTransformPatterns(Boolean value) {
        this.setTransformPatterns(value);
        return this;
    }

    public Settings withTransformPatternsLogging(Boolean value) {
        this.setTransformPatternsLogging(value);
        return this;
    }

    public Settings withTransformPatternsUnnecessaryDistinct(Boolean value) {
        this.setTransformPatternsUnnecessaryDistinct(value);
        return this;
    }

    public Settings withTransformPatternsUnnecessaryScalarSubquery(Boolean value) {
        this.setTransformPatternsUnnecessaryScalarSubquery(value);
        return this;
    }

    public Settings withTransformPatternsUnnecessaryInnerJoin(Boolean value) {
        this.setTransformPatternsUnnecessaryInnerJoin(value);
        return this;
    }

    public Settings withTransformPatternsUnnecessaryGroupByExpressions(Boolean value) {
        this.setTransformPatternsUnnecessaryGroupByExpressions(value);
        return this;
    }

    public Settings withTransformPatternsUnnecessaryOrderByExpressions(Boolean value) {
        this.setTransformPatternsUnnecessaryOrderByExpressions(value);
        return this;
    }

    public Settings withTransformPatternsUnnecessaryExistsSubqueryClauses(Boolean value) {
        this.setTransformPatternsUnnecessaryExistsSubqueryClauses(value);
        return this;
    }

    public Settings withTransformPatternsCountConstant(Boolean value) {
        this.setTransformPatternsCountConstant(value);
        return this;
    }

    public Settings withTransformPatternsTrim(Boolean value) {
        this.setTransformPatternsTrim(value);
        return this;
    }

    public Settings withTransformPatternsNotAnd(Boolean value) {
        this.setTransformPatternsNotAnd(value);
        return this;
    }

    public Settings withTransformPatternsNotOr(Boolean value) {
        this.setTransformPatternsNotOr(value);
        return this;
    }

    public Settings withTransformPatternsNotNot(Boolean value) {
        this.setTransformPatternsNotNot(value);
        return this;
    }

    public Settings withTransformPatternsNotComparison(Boolean value) {
        this.setTransformPatternsNotComparison(value);
        return this;
    }

    public Settings withTransformPatternsNotNotDistinct(Boolean value) {
        this.setTransformPatternsNotNotDistinct(value);
        return this;
    }

    public Settings withTransformPatternsDistinctFromNull(Boolean value) {
        this.setTransformPatternsDistinctFromNull(value);
        return this;
    }

    public Settings withTransformPatternsNormaliseAssociativeOps(Boolean value) {
        this.setTransformPatternsNormaliseAssociativeOps(value);
        return this;
    }

    public Settings withTransformPatternsNormaliseInListSingleElementToComparison(Boolean value) {
        this.setTransformPatternsNormaliseInListSingleElementToComparison(value);
        return this;
    }

    public Settings withTransformPatternsNormaliseFieldCompareValue(Boolean value) {
        this.setTransformPatternsNormaliseFieldCompareValue(value);
        return this;
    }

    public Settings withTransformPatternsNormaliseCoalesceToNvl(Boolean value) {
        this.setTransformPatternsNormaliseCoalesceToNvl(value);
        return this;
    }

    public Settings withTransformPatternsOrEqToIn(Boolean value) {
        this.setTransformPatternsOrEqToIn(value);
        return this;
    }

    public Settings withTransformPatternsAndNeToNotIn(Boolean value) {
        this.setTransformPatternsAndNeToNotIn(value);
        return this;
    }

    public Settings withTransformPatternsMergeOrComparison(Boolean value) {
        this.setTransformPatternsMergeOrComparison(value);
        return this;
    }

    public Settings withTransformPatternsMergeAndComparison(Boolean value) {
        this.setTransformPatternsMergeAndComparison(value);
        return this;
    }

    public Settings withTransformPatternsMergeInLists(Boolean value) {
        this.setTransformPatternsMergeInLists(value);
        return this;
    }

    public Settings withTransformPatternsMergeRangePredicates(Boolean value) {
        this.setTransformPatternsMergeRangePredicates(value);
        return this;
    }

    public Settings withTransformPatternsMergeBetweenSymmetricPredicates(Boolean value) {
        this.setTransformPatternsMergeBetweenSymmetricPredicates(value);
        return this;
    }

    public Settings withTransformPatternsCaseSearchedToCaseSimple(Boolean value) {
        this.setTransformPatternsCaseSearchedToCaseSimple(value);
        return this;
    }

    public Settings withTransformPatternsCaseElseNull(Boolean value) {
        this.setTransformPatternsCaseElseNull(value);
        return this;
    }

    public Settings withTransformPatternsUnreachableCaseClauses(Boolean value) {
        this.setTransformPatternsUnreachableCaseClauses(value);
        return this;
    }

    public Settings withTransformPatternsUnreachableDecodeClauses(Boolean value) {
        this.setTransformPatternsUnreachableDecodeClauses(value);
        return this;
    }

    public Settings withTransformPatternsCaseDistinctToDecode(Boolean value) {
        this.setTransformPatternsCaseDistinctToDecode(value);
        return this;
    }

    public Settings withTransformPatternsCaseMergeWhenWhen(Boolean value) {
        this.setTransformPatternsCaseMergeWhenWhen(value);
        return this;
    }

    public Settings withTransformPatternsCaseMergeWhenElse(Boolean value) {
        this.setTransformPatternsCaseMergeWhenElse(value);
        return this;
    }

    public Settings withTransformPatternsCaseToCaseAbbreviation(Boolean value) {
        this.setTransformPatternsCaseToCaseAbbreviation(value);
        return this;
    }

    public Settings withTransformPatternsSimplifyCaseAbbreviation(Boolean value) {
        this.setTransformPatternsSimplifyCaseAbbreviation(value);
        return this;
    }

    public Settings withTransformPatternsFlattenCaseAbbreviation(Boolean value) {
        this.setTransformPatternsFlattenCaseAbbreviation(value);
        return this;
    }

    public Settings withTransformPatternsFlattenDecode(Boolean value) {
        this.setTransformPatternsFlattenDecode(value);
        return this;
    }

    public Settings withTransformPatternsFlattenCase(Boolean value) {
        this.setTransformPatternsFlattenCase(value);
        return this;
    }

    public Settings withTransformPatternsTrivialCaseAbbreviation(Boolean value) {
        this.setTransformPatternsTrivialCaseAbbreviation(value);
        return this;
    }

    public Settings withTransformPatternsTrivialPredicates(Boolean value) {
        this.setTransformPatternsTrivialPredicates(value);
        return this;
    }

    public Settings withTransformPatternsTrivialBitwiseOperations(Boolean value) {
        this.setTransformPatternsTrivialBitwiseOperations(value);
        return this;
    }

    public Settings withTransformPatternsBitSet(Boolean value) {
        this.setTransformPatternsBitSet(value);
        return this;
    }

    public Settings withTransformPatternsBitGet(Boolean value) {
        this.setTransformPatternsBitGet(value);
        return this;
    }

    public Settings withTransformPatternsScalarSubqueryCountAsteriskGtZero(Boolean value) {
        this.setTransformPatternsScalarSubqueryCountAsteriskGtZero(value);
        return this;
    }

    public Settings withTransformPatternsScalarSubqueryCountExpressionGtZero(Boolean value) {
        this.setTransformPatternsScalarSubqueryCountExpressionGtZero(value);
        return this;
    }

    public Settings withTransformPatternsEmptyScalarSubquery(Boolean value) {
        this.setTransformPatternsEmptyScalarSubquery(value);
        return this;
    }

    public Settings withTransformPatternsNegNeg(Boolean value) {
        this.setTransformPatternsNegNeg(value);
        return this;
    }

    public Settings withTransformPatternsBitNotBitNot(Boolean value) {
        this.setTransformPatternsBitNotBitNot(value);
        return this;
    }

    public Settings withTransformPatternsBitNotBitNand(Boolean value) {
        this.setTransformPatternsBitNotBitNand(value);
        return this;
    }

    public Settings withTransformPatternsBitNotBitNor(Boolean value) {
        this.setTransformPatternsBitNotBitNor(value);
        return this;
    }

    public Settings withTransformPatternsBitNotBitXNor(Boolean value) {
        this.setTransformPatternsBitNotBitXNor(value);
        return this;
    }

    public Settings withTransformPatternsNullOnNullInput(Boolean value) {
        this.setTransformPatternsNullOnNullInput(value);
        return this;
    }

    public Settings withTransformPatternsIdempotentFunctionRepetition(Boolean value) {
        this.setTransformPatternsIdempotentFunctionRepetition(value);
        return this;
    }

    public Settings withTransformPatternsArithmeticComparisons(Boolean value) {
        this.setTransformPatternsArithmeticComparisons(value);
        return this;
    }

    public Settings withTransformPatternsArithmeticExpressions(Boolean value) {
        this.setTransformPatternsArithmeticExpressions(value);
        return this;
    }

    public Settings withTransformPatternsTrigonometricFunctions(Boolean value) {
        this.setTransformPatternsTrigonometricFunctions(value);
        return this;
    }

    public Settings withTransformPatternsLogarithmicFunctions(Boolean value) {
        this.setTransformPatternsLogarithmicFunctions(value);
        return this;
    }

    public Settings withTransformPatternsHyperbolicFunctions(Boolean value) {
        this.setTransformPatternsHyperbolicFunctions(value);
        return this;
    }

    public Settings withTransformPatternsInverseHyperbolicFunctions(Boolean value) {
        this.setTransformPatternsInverseHyperbolicFunctions(value);
        return this;
    }

    public Settings withTransformInlineBindValuesForFieldComparisons(Boolean value) {
        this.setTransformInlineBindValuesForFieldComparisons(value);
        return this;
    }

    public Settings withTransformAnsiJoinToTableLists(Boolean value) {
        this.setTransformAnsiJoinToTableLists(value);
        return this;
    }

    @Deprecated
    public Settings withTransformInConditionSubqueryWithLimitToDerivedTable(Transformation value) {
        this.setTransformInConditionSubqueryWithLimitToDerivedTable(value);
        return this;
    }

    public Settings withTransformQualify(Transformation value) {
        this.setTransformQualify(value);
        return this;
    }

    public Settings withTransformTableListsToAnsiJoin(Boolean value) {
        this.setTransformTableListsToAnsiJoin(value);
        return this;
    }

    public Settings withTransformRownum(Transformation value) {
        this.setTransformRownum(value);
        return this;
    }

    public Settings withTransformUnneededArithmeticExpressions(TransformUnneededArithmeticExpressions value) {
        this.setTransformUnneededArithmeticExpressions(value);
        return this;
    }

    public Settings withTransformGroupByColumnIndex(Transformation value) {
        this.setTransformGroupByColumnIndex(value);
        return this;
    }

    public Settings withTransformInlineCTE(Transformation value) {
        this.setTransformInlineCTE(value);
        return this;
    }

    public Settings withBackslashEscaping(BackslashEscaping value) {
        this.setBackslashEscaping(value);
        return this;
    }

    public Settings withParamType(ParamType value) {
        this.setParamType(value);
        return this;
    }

    public Settings withParamCastMode(ParamCastMode value) {
        this.setParamCastMode(value);
        return this;
    }

    public Settings withStatementType(StatementType value) {
        this.setStatementType(value);
        return this;
    }

    public Settings withInlineThreshold(Integer value) {
        this.setInlineThreshold(value);
        return this;
    }

    public Settings withTransactionListenerStartInvocationOrder(InvocationOrder value) {
        this.setTransactionListenerStartInvocationOrder(value);
        return this;
    }

    public Settings withTransactionListenerEndInvocationOrder(InvocationOrder value) {
        this.setTransactionListenerEndInvocationOrder(value);
        return this;
    }

    public Settings withMigrationListenerStartInvocationOrder(InvocationOrder value) {
        this.setMigrationListenerStartInvocationOrder(value);
        return this;
    }

    public Settings withMigrationListenerEndInvocationOrder(InvocationOrder value) {
        this.setMigrationListenerEndInvocationOrder(value);
        return this;
    }

    public Settings withVisitListenerStartInvocationOrder(InvocationOrder value) {
        this.setVisitListenerStartInvocationOrder(value);
        return this;
    }

    public Settings withVisitListenerEndInvocationOrder(InvocationOrder value) {
        this.setVisitListenerEndInvocationOrder(value);
        return this;
    }

    public Settings withRecordListenerStartInvocationOrder(InvocationOrder value) {
        this.setRecordListenerStartInvocationOrder(value);
        return this;
    }

    public Settings withRecordListenerEndInvocationOrder(InvocationOrder value) {
        this.setRecordListenerEndInvocationOrder(value);
        return this;
    }

    public Settings withExecuteListenerStartInvocationOrder(InvocationOrder value) {
        this.setExecuteListenerStartInvocationOrder(value);
        return this;
    }

    public Settings withExecuteListenerEndInvocationOrder(InvocationOrder value) {
        this.setExecuteListenerEndInvocationOrder(value);
        return this;
    }

    public Settings withExecuteLogging(Boolean value) {
        this.setExecuteLogging(value);
        return this;
    }

    public Settings withExecuteLoggingSQLExceptions(Boolean value) {
        this.setExecuteLoggingSQLExceptions(value);
        return this;
    }

    public Settings withDiagnosticsLogging(Boolean value) {
        this.setDiagnosticsLogging(value);
        return this;
    }

    public Settings withDiagnosticsConnection(DiagnosticsConnection value) {
        this.setDiagnosticsConnection(value);
        return this;
    }

    public Settings withUpdateRecordVersion(Boolean value) {
        this.setUpdateRecordVersion(value);
        return this;
    }

    public Settings withUpdateRecordTimestamp(Boolean value) {
        this.setUpdateRecordTimestamp(value);
        return this;
    }

    public Settings withExecuteWithOptimisticLocking(Boolean value) {
        this.setExecuteWithOptimisticLocking(value);
        return this;
    }

    public Settings withExecuteWithOptimisticLockingExcludeUnversioned(Boolean value) {
        this.setExecuteWithOptimisticLockingExcludeUnversioned(value);
        return this;
    }

    public Settings withAttachRecords(Boolean value) {
        this.setAttachRecords(value);
        return this;
    }

    public Settings withInsertUnchangedRecords(Boolean value) {
        this.setInsertUnchangedRecords(value);
        return this;
    }

    public Settings withUpdateUnchangedRecords(UpdateUnchangedRecords value) {
        this.setUpdateUnchangedRecords(value);
        return this;
    }

    public Settings withUpdatablePrimaryKeys(Boolean value) {
        this.setUpdatablePrimaryKeys(value);
        return this;
    }

    public Settings withReflectionCaching(Boolean value) {
        this.setReflectionCaching(value);
        return this;
    }

    public Settings withCacheRecordMappers(Boolean value) {
        this.setCacheRecordMappers(value);
        return this;
    }

    public Settings withCacheParsingConnection(Boolean value) {
        this.setCacheParsingConnection(value);
        return this;
    }

    public Settings withCacheParsingConnectionLRUCacheSize(Integer value) {
        this.setCacheParsingConnectionLRUCacheSize(value);
        return this;
    }

    public Settings withCachePreparedStatementInLoader(Boolean value) {
        this.setCachePreparedStatementInLoader(value);
        return this;
    }

    public Settings withThrowExceptions(ThrowExceptions value) {
        this.setThrowExceptions(value);
        return this;
    }

    public Settings withFetchWarnings(Boolean value) {
        this.setFetchWarnings(value);
        return this;
    }

    public Settings withFetchServerOutputSize(Integer value) {
        this.setFetchServerOutputSize(value);
        return this;
    }

    public Settings withReturnIdentityOnUpdatableRecord(Boolean value) {
        this.setReturnIdentityOnUpdatableRecord(value);
        return this;
    }

    public Settings withReturnDefaultOnUpdatableRecord(Boolean value) {
        this.setReturnDefaultOnUpdatableRecord(value);
        return this;
    }

    public Settings withReturnComputedOnUpdatableRecord(Boolean value) {
        this.setReturnComputedOnUpdatableRecord(value);
        return this;
    }

    public Settings withReturnAllOnUpdatableRecord(Boolean value) {
        this.setReturnAllOnUpdatableRecord(value);
        return this;
    }

    public Settings withReturnRecordToPojo(Boolean value) {
        this.setReturnRecordToPojo(value);
        return this;
    }

    public Settings withMapJPAAnnotations(Boolean value) {
        this.setMapJPAAnnotations(value);
        return this;
    }

    public Settings withMapRecordComponentParameterNames(Boolean value) {
        this.setMapRecordComponentParameterNames(value);
        return this;
    }

    public Settings withMapConstructorPropertiesParameterNames(Boolean value) {
        this.setMapConstructorPropertiesParameterNames(value);
        return this;
    }

    public Settings withMapConstructorParameterNames(Boolean value) {
        this.setMapConstructorParameterNames(value);
        return this;
    }

    public Settings withMapConstructorParameterNamesInKotlin(Boolean value) {
        this.setMapConstructorParameterNamesInKotlin(value);
        return this;
    }

    public Settings withQueryPoolable(QueryPoolable value) {
        this.setQueryPoolable(value);
        return this;
    }

    public Settings withQueryTimeout(Integer value) {
        this.setQueryTimeout(value);
        return this;
    }

    public Settings withMaxRows(Integer value) {
        this.setMaxRows(value);
        return this;
    }

    public Settings withFetchSize(Integer value) {
        this.setFetchSize(value);
        return this;
    }

    public Settings withBatchSize(Integer value) {
        this.setBatchSize(value);
        return this;
    }

    public Settings withDebugInfoOnStackTrace(Boolean value) {
        this.setDebugInfoOnStackTrace(value);
        return this;
    }

    public Settings withInListPadding(Boolean value) {
        this.setInListPadding(value);
        return this;
    }

    public Settings withInListPadBase(Integer value) {
        this.setInListPadBase(value);
        return this;
    }

    public Settings withDelimiter(String value) {
        this.setDelimiter(value);
        return this;
    }

    public Settings withEmulateOnDuplicateKeyUpdateOnPrimaryKeyOnly(Boolean value) {
        this.setEmulateOnDuplicateKeyUpdateOnPrimaryKeyOnly(value);
        return this;
    }

    public Settings withEmulateMultiset(NestedCollectionEmulation value) {
        this.setEmulateMultiset(value);
        return this;
    }

    public Settings withEmulateComputedColumns(Boolean value) {
        this.setEmulateComputedColumns(value);
        return this;
    }

    public Settings withExecuteUpdateWithoutWhere(ExecuteWithoutWhere value) {
        this.setExecuteUpdateWithoutWhere(value);
        return this;
    }

    public Settings withExecuteDeleteWithoutWhere(ExecuteWithoutWhere value) {
        this.setExecuteDeleteWithoutWhere(value);
        return this;
    }

    public Settings withInterpreterDialect(SQLDialect value) {
        this.setInterpreterDialect(value);
        return this;
    }

    public Settings withInterpreterNameLookupCaseSensitivity(InterpreterNameLookupCaseSensitivity value) {
        this.setInterpreterNameLookupCaseSensitivity(value);
        return this;
    }

    public Settings withInterpreterLocale(Locale value) {
        this.setInterpreterLocale(value);
        return this;
    }

    public Settings withInterpreterDelayForeignKeyDeclarations(Boolean value) {
        this.setInterpreterDelayForeignKeyDeclarations(value);
        return this;
    }

    public Settings withMetaIncludeSystemIndexes(Boolean value) {
        this.setMetaIncludeSystemIndexes(value);
        return this;
    }

    public Settings withMetaIncludeSystemSequences(Boolean value) {
        this.setMetaIncludeSystemSequences(value);
        return this;
    }

    public Settings withMigrationAllowsUndo(Boolean value) {
        this.setMigrationAllowsUndo(value);
        return this;
    }

    public Settings withMigrationRevertUntracked(Boolean value) {
        this.setMigrationRevertUntracked(value);
        return this;
    }

    public Settings withMigrationAutoBaseline(Boolean value) {
        this.setMigrationAutoBaseline(value);
        return this;
    }

    public Settings withMigrationAutoValidation(Boolean value) {
        this.setMigrationAutoValidation(value);
        return this;
    }

    public Settings withMigrationIgnoreDefaultTimestampPrecisionDiffs(Boolean value) {
        this.setMigrationIgnoreDefaultTimestampPrecisionDiffs(value);
        return this;
    }

    public Settings withLocale(Locale value) {
        this.setLocale(value);
        return this;
    }

    public Settings withParseDialect(SQLDialect value) {
        this.setParseDialect(value);
        return this;
    }

    public Settings withParseLocale(Locale value) {
        this.setParseLocale(value);
        return this;
    }

    public Settings withParseDateFormat(String value) {
        this.setParseDateFormat(value);
        return this;
    }

    public Settings withParseTimestampFormat(String value) {
        this.setParseTimestampFormat(value);
        return this;
    }

    public Settings withParseNamedParamPrefix(String value) {
        this.setParseNamedParamPrefix(value);
        return this;
    }

    public Settings withParseNameCase(ParseNameCase value) {
        this.setParseNameCase(value);
        return this;
    }

    public Settings withParseWithMetaLookups(ParseWithMetaLookups value) {
        this.setParseWithMetaLookups(value);
        return this;
    }

    public Settings withParseAppendMissingTableReferences(Transformation value) {
        this.setParseAppendMissingTableReferences(value);
        return this;
    }

    public Settings withParseSetCommands(Boolean value) {
        this.setParseSetCommands(value);
        return this;
    }

    public Settings withParseUnsupportedSyntax(ParseUnsupportedSyntax value) {
        this.setParseUnsupportedSyntax(value);
        return this;
    }

    public Settings withParseUnknownFunctions(ParseUnknownFunctions value) {
        this.setParseUnknownFunctions(value);
        return this;
    }

    public Settings withParseIgnoreCommercialOnlyFeatures(Boolean value) {
        this.setParseIgnoreCommercialOnlyFeatures(value);
        return this;
    }

    public Settings withParseIgnoreComments(Boolean value) {
        this.setParseIgnoreComments(value);
        return this;
    }

    public Settings withParseIgnoreCommentStart(String value) {
        this.setParseIgnoreCommentStart(value);
        return this;
    }

    public Settings withParseIgnoreCommentStop(String value) {
        this.setParseIgnoreCommentStop(value);
        return this;
    }

    public Settings withParseRetainCommentsBetweenQueries(Boolean value) {
        this.setParseRetainCommentsBetweenQueries(value);
        return this;
    }

    public Settings withParseMetaDefaultExpressions(Boolean value) {
        this.setParseMetaDefaultExpressions(value);
        return this;
    }

    public Settings withReadonlyTableRecordInsert(WriteIfReadonly value) {
        this.setReadonlyTableRecordInsert(value);
        return this;
    }

    public Settings withReadonlyUpdatableRecordUpdate(WriteIfReadonly value) {
        this.setReadonlyUpdatableRecordUpdate(value);
        return this;
    }

    public Settings withReadonlyInsert(WriteIfReadonly value) {
        this.setReadonlyInsert(value);
        return this;
    }

    public Settings withReadonlyUpdate(WriteIfReadonly value) {
        this.setReadonlyUpdate(value);
        return this;
    }

    public Settings withApplyWorkaroundFor7962(Boolean value) {
        this.setApplyWorkaroundFor7962(value);
        return this;
    }

    public Settings withInterpreterSearchPath(InterpreterSearchSchema ... values2) {
        if (values2 != null) {
            for (InterpreterSearchSchema value : values2) {
                this.getInterpreterSearchPath().add(value);
            }
        }
        return this;
    }

    public Settings withInterpreterSearchPath(Collection<InterpreterSearchSchema> values2) {
        if (values2 != null) {
            this.getInterpreterSearchPath().addAll(values2);
        }
        return this;
    }

    public Settings withInterpreterSearchPath(List<InterpreterSearchSchema> interpreterSearchPath) {
        this.setInterpreterSearchPath(interpreterSearchPath);
        return this;
    }

    public Settings withMigrationSchemata(MigrationSchema ... values2) {
        if (values2 != null) {
            for (MigrationSchema value : values2) {
                this.getMigrationSchemata().add(value);
            }
        }
        return this;
    }

    public Settings withMigrationSchemata(Collection<MigrationSchema> values2) {
        if (values2 != null) {
            this.getMigrationSchemata().addAll(values2);
        }
        return this;
    }

    public Settings withMigrationSchemata(List<MigrationSchema> migrationSchemata) {
        this.setMigrationSchemata(migrationSchemata);
        return this;
    }

    public Settings withParseSearchPath(ParseSearchSchema ... values2) {
        if (values2 != null) {
            for (ParseSearchSchema value : values2) {
                this.getParseSearchPath().add(value);
            }
        }
        return this;
    }

    public Settings withParseSearchPath(Collection<ParseSearchSchema> values2) {
        if (values2 != null) {
            this.getParseSearchPath().addAll(values2);
        }
        return this;
    }

    public Settings withParseSearchPath(List<ParseSearchSchema> parseSearchPath) {
        this.setParseSearchPath(parseSearchPath);
        return this;
    }

    @Override
    public final void appendTo(XMLBuilder builder) {
        builder.append("forceIntegerTypesOnZeroScaleDecimals", this.forceIntegerTypesOnZeroScaleDecimals);
        builder.append("renderCatalog", this.renderCatalog);
        builder.append("renderSchema", this.renderSchema);
        builder.append("renderTable", (Object)this.renderTable);
        builder.append("renderMapping", this.renderMapping);
        builder.append("renderQuotedNames", (Object)this.renderQuotedNames);
        builder.append("renderNameCase", (Object)this.renderNameCase);
        builder.append("renderNameStyle", (Object)this.renderNameStyle);
        builder.append("renderNamedParamPrefix", this.renderNamedParamPrefix);
        builder.append("renderKeywordCase", (Object)this.renderKeywordCase);
        builder.append("renderKeywordStyle", (Object)this.renderKeywordStyle);
        builder.append("renderLocale", this.renderLocale);
        builder.append("renderFormatted", this.renderFormatted);
        builder.append("renderFormatting", this.renderFormatting);
        builder.append("renderOptionalAssociativityParentheses", (Object)this.renderOptionalAssociativityParentheses);
        builder.append("renderOptionalAsKeywordForTableAliases", (Object)this.renderOptionalAsKeywordForTableAliases);
        builder.append("renderOptionalAsKeywordForFieldAliases", (Object)this.renderOptionalAsKeywordForFieldAliases);
        builder.append("renderOptionalInnerKeyword", (Object)this.renderOptionalInnerKeyword);
        builder.append("renderOptionalOuterKeyword", (Object)this.renderOptionalOuterKeyword);
        builder.append("renderImplicitWindowRange", (Object)this.renderImplicitWindowRange);
        builder.append("renderScalarSubqueriesForStoredFunctions", this.renderScalarSubqueriesForStoredFunctions);
        builder.append("renderImplicitJoinType", (Object)this.renderImplicitJoinType);
        builder.append("renderDefaultNullability", (Object)this.renderDefaultNullability);
        builder.append("renderCoalesceToEmptyStringInConcat", this.renderCoalesceToEmptyStringInConcat);
        builder.append("renderOrderByRownumberForEmulatedPagination", this.renderOrderByRownumberForEmulatedPagination);
        builder.append("renderOutputForSQLServerReturningClause", this.renderOutputForSQLServerReturningClause);
        builder.append("renderGroupConcatMaxLenSessionVariable", this.renderGroupConcatMaxLenSessionVariable);
        builder.append("renderParenthesisAroundSetOperationQueries", this.renderParenthesisAroundSetOperationQueries);
        builder.append("renderVariablesInDerivedTablesForEmulations", this.renderVariablesInDerivedTablesForEmulations);
        builder.append("renderRowConditionForSeekClause", this.renderRowConditionForSeekClause);
        builder.append("renderRedundantConditionForSeekClause", this.renderRedundantConditionForSeekClause);
        builder.append("renderPlainSQLTemplatesAsRaw", this.renderPlainSQLTemplatesAsRaw);
        builder.append("namePathSeparator", this.namePathSeparator);
        builder.append("bindOffsetDateTimeType", this.bindOffsetDateTimeType);
        builder.append("bindOffsetTimeType", this.bindOffsetTimeType);
        builder.append("fetchTriggerValuesAfterSQLServerOutput", this.fetchTriggerValuesAfterSQLServerOutput);
        builder.append("fetchIntermediateResult", (Object)this.fetchIntermediateResult);
        builder.append("diagnosticsDuplicateStatements", this.diagnosticsDuplicateStatements);
        builder.append("diagnosticsDuplicateStatementsUsingTransformPatterns", this.diagnosticsDuplicateStatementsUsingTransformPatterns);
        builder.append("diagnosticsMissingWasNullCall", this.diagnosticsMissingWasNullCall);
        builder.append("diagnosticsRepeatedStatements", this.diagnosticsRepeatedStatements);
        builder.append("diagnosticsConsecutiveAggregation", this.diagnosticsConsecutiveAggregation);
        builder.append("diagnosticsConcatenationInPredicate", this.diagnosticsConcatenationInPredicate);
        builder.append("diagnosticsPossiblyWrongExpression", this.diagnosticsPossiblyWrongExpression);
        builder.append("diagnosticsTooManyColumnsFetched", this.diagnosticsTooManyColumnsFetched);
        builder.append("diagnosticsTooManyRowsFetched", this.diagnosticsTooManyRowsFetched);
        builder.append("diagnosticsUnnecessaryWasNullCall", this.diagnosticsUnnecessaryWasNullCall);
        builder.append("diagnosticsPatterns", this.diagnosticsPatterns);
        builder.append("diagnosticsTrivialCondition", this.diagnosticsTrivialCondition);
        builder.append("diagnosticsNullCondition", this.diagnosticsNullCondition);
        builder.append("transformPatterns", this.transformPatterns);
        builder.append("transformPatternsLogging", this.transformPatternsLogging);
        builder.append("transformPatternsUnnecessaryDistinct", this.transformPatternsUnnecessaryDistinct);
        builder.append("transformPatternsUnnecessaryScalarSubquery", this.transformPatternsUnnecessaryScalarSubquery);
        builder.append("transformPatternsUnnecessaryInnerJoin", this.transformPatternsUnnecessaryInnerJoin);
        builder.append("transformPatternsUnnecessaryGroupByExpressions", this.transformPatternsUnnecessaryGroupByExpressions);
        builder.append("transformPatternsUnnecessaryOrderByExpressions", this.transformPatternsUnnecessaryOrderByExpressions);
        builder.append("transformPatternsUnnecessaryExistsSubqueryClauses", this.transformPatternsUnnecessaryExistsSubqueryClauses);
        builder.append("transformPatternsCountConstant", this.transformPatternsCountConstant);
        builder.append("transformPatternsTrim", this.transformPatternsTrim);
        builder.append("transformPatternsNotAnd", this.transformPatternsNotAnd);
        builder.append("transformPatternsNotOr", this.transformPatternsNotOr);
        builder.append("transformPatternsNotNot", this.transformPatternsNotNot);
        builder.append("transformPatternsNotComparison", this.transformPatternsNotComparison);
        builder.append("transformPatternsNotNotDistinct", this.transformPatternsNotNotDistinct);
        builder.append("transformPatternsDistinctFromNull", this.transformPatternsDistinctFromNull);
        builder.append("transformPatternsNormaliseAssociativeOps", this.transformPatternsNormaliseAssociativeOps);
        builder.append("transformPatternsNormaliseInListSingleElementToComparison", this.transformPatternsNormaliseInListSingleElementToComparison);
        builder.append("transformPatternsNormaliseFieldCompareValue", this.transformPatternsNormaliseFieldCompareValue);
        builder.append("transformPatternsNormaliseCoalesceToNvl", this.transformPatternsNormaliseCoalesceToNvl);
        builder.append("transformPatternsOrEqToIn", this.transformPatternsOrEqToIn);
        builder.append("transformPatternsAndNeToNotIn", this.transformPatternsAndNeToNotIn);
        builder.append("transformPatternsMergeOrComparison", this.transformPatternsMergeOrComparison);
        builder.append("transformPatternsMergeAndComparison", this.transformPatternsMergeAndComparison);
        builder.append("transformPatternsMergeInLists", this.transformPatternsMergeInLists);
        builder.append("transformPatternsMergeRangePredicates", this.transformPatternsMergeRangePredicates);
        builder.append("transformPatternsMergeBetweenSymmetricPredicates", this.transformPatternsMergeBetweenSymmetricPredicates);
        builder.append("transformPatternsCaseSearchedToCaseSimple", this.transformPatternsCaseSearchedToCaseSimple);
        builder.append("transformPatternsCaseElseNull", this.transformPatternsCaseElseNull);
        builder.append("transformPatternsUnreachableCaseClauses", this.transformPatternsUnreachableCaseClauses);
        builder.append("transformPatternsUnreachableDecodeClauses", this.transformPatternsUnreachableDecodeClauses);
        builder.append("transformPatternsCaseDistinctToDecode", this.transformPatternsCaseDistinctToDecode);
        builder.append("transformPatternsCaseMergeWhenWhen", this.transformPatternsCaseMergeWhenWhen);
        builder.append("transformPatternsCaseMergeWhenElse", this.transformPatternsCaseMergeWhenElse);
        builder.append("transformPatternsCaseToCaseAbbreviation", this.transformPatternsCaseToCaseAbbreviation);
        builder.append("transformPatternsSimplifyCaseAbbreviation", this.transformPatternsSimplifyCaseAbbreviation);
        builder.append("transformPatternsFlattenCaseAbbreviation", this.transformPatternsFlattenCaseAbbreviation);
        builder.append("transformPatternsFlattenDecode", this.transformPatternsFlattenDecode);
        builder.append("transformPatternsFlattenCase", this.transformPatternsFlattenCase);
        builder.append("transformPatternsTrivialCaseAbbreviation", this.transformPatternsTrivialCaseAbbreviation);
        builder.append("transformPatternsTrivialPredicates", this.transformPatternsTrivialPredicates);
        builder.append("transformPatternsTrivialBitwiseOperations", this.transformPatternsTrivialBitwiseOperations);
        builder.append("transformPatternsBitSet", this.transformPatternsBitSet);
        builder.append("transformPatternsBitGet", this.transformPatternsBitGet);
        builder.append("transformPatternsScalarSubqueryCountAsteriskGtZero", this.transformPatternsScalarSubqueryCountAsteriskGtZero);
        builder.append("transformPatternsScalarSubqueryCountExpressionGtZero", this.transformPatternsScalarSubqueryCountExpressionGtZero);
        builder.append("transformPatternsEmptyScalarSubquery", this.transformPatternsEmptyScalarSubquery);
        builder.append("transformPatternsNegNeg", this.transformPatternsNegNeg);
        builder.append("transformPatternsBitNotBitNot", this.transformPatternsBitNotBitNot);
        builder.append("transformPatternsBitNotBitNand", this.transformPatternsBitNotBitNand);
        builder.append("transformPatternsBitNotBitNor", this.transformPatternsBitNotBitNor);
        builder.append("transformPatternsBitNotBitXNor", this.transformPatternsBitNotBitXNor);
        builder.append("transformPatternsNullOnNullInput", this.transformPatternsNullOnNullInput);
        builder.append("transformPatternsIdempotentFunctionRepetition", this.transformPatternsIdempotentFunctionRepetition);
        builder.append("transformPatternsArithmeticComparisons", this.transformPatternsArithmeticComparisons);
        builder.append("transformPatternsArithmeticExpressions", this.transformPatternsArithmeticExpressions);
        builder.append("transformPatternsTrigonometricFunctions", this.transformPatternsTrigonometricFunctions);
        builder.append("transformPatternsLogarithmicFunctions", this.transformPatternsLogarithmicFunctions);
        builder.append("transformPatternsHyperbolicFunctions", this.transformPatternsHyperbolicFunctions);
        builder.append("transformPatternsInverseHyperbolicFunctions", this.transformPatternsInverseHyperbolicFunctions);
        builder.append("transformInlineBindValuesForFieldComparisons", this.transformInlineBindValuesForFieldComparisons);
        builder.append("transformAnsiJoinToTableLists", this.transformAnsiJoinToTableLists);
        builder.append("transformInConditionSubqueryWithLimitToDerivedTable", (Object)this.transformInConditionSubqueryWithLimitToDerivedTable);
        builder.append("transformQualify", (Object)this.transformQualify);
        builder.append("transformTableListsToAnsiJoin", this.transformTableListsToAnsiJoin);
        builder.append("transformRownum", (Object)this.transformRownum);
        builder.append("transformUnneededArithmeticExpressions", (Object)this.transformUnneededArithmeticExpressions);
        builder.append("transformGroupByColumnIndex", (Object)this.transformGroupByColumnIndex);
        builder.append("transformInlineCTE", (Object)this.transformInlineCTE);
        builder.append("backslashEscaping", (Object)this.backslashEscaping);
        builder.append("paramType", (Object)this.paramType);
        builder.append("paramCastMode", (Object)this.paramCastMode);
        builder.append("statementType", (Object)this.statementType);
        builder.append("inlineThreshold", this.inlineThreshold);
        builder.append("transactionListenerStartInvocationOrder", (Object)this.transactionListenerStartInvocationOrder);
        builder.append("transactionListenerEndInvocationOrder", (Object)this.transactionListenerEndInvocationOrder);
        builder.append("migrationListenerStartInvocationOrder", (Object)this.migrationListenerStartInvocationOrder);
        builder.append("migrationListenerEndInvocationOrder", (Object)this.migrationListenerEndInvocationOrder);
        builder.append("visitListenerStartInvocationOrder", (Object)this.visitListenerStartInvocationOrder);
        builder.append("visitListenerEndInvocationOrder", (Object)this.visitListenerEndInvocationOrder);
        builder.append("recordListenerStartInvocationOrder", (Object)this.recordListenerStartInvocationOrder);
        builder.append("recordListenerEndInvocationOrder", (Object)this.recordListenerEndInvocationOrder);
        builder.append("executeListenerStartInvocationOrder", (Object)this.executeListenerStartInvocationOrder);
        builder.append("executeListenerEndInvocationOrder", (Object)this.executeListenerEndInvocationOrder);
        builder.append("executeLogging", this.executeLogging);
        builder.append("executeLoggingSQLExceptions", this.executeLoggingSQLExceptions);
        builder.append("diagnosticsLogging", this.diagnosticsLogging);
        builder.append("diagnosticsConnection", (Object)this.diagnosticsConnection);
        builder.append("updateRecordVersion", this.updateRecordVersion);
        builder.append("updateRecordTimestamp", this.updateRecordTimestamp);
        builder.append("executeWithOptimisticLocking", this.executeWithOptimisticLocking);
        builder.append("executeWithOptimisticLockingExcludeUnversioned", this.executeWithOptimisticLockingExcludeUnversioned);
        builder.append("attachRecords", this.attachRecords);
        builder.append("insertUnchangedRecords", this.insertUnchangedRecords);
        builder.append("updateUnchangedRecords", (Object)this.updateUnchangedRecords);
        builder.append("updatablePrimaryKeys", this.updatablePrimaryKeys);
        builder.append("reflectionCaching", this.reflectionCaching);
        builder.append("cacheRecordMappers", this.cacheRecordMappers);
        builder.append("cacheParsingConnection", this.cacheParsingConnection);
        builder.append("cacheParsingConnectionLRUCacheSize", this.cacheParsingConnectionLRUCacheSize);
        builder.append("cachePreparedStatementInLoader", this.cachePreparedStatementInLoader);
        builder.append("throwExceptions", (Object)this.throwExceptions);
        builder.append("fetchWarnings", this.fetchWarnings);
        builder.append("fetchServerOutputSize", this.fetchServerOutputSize);
        builder.append("returnIdentityOnUpdatableRecord", this.returnIdentityOnUpdatableRecord);
        builder.append("returnDefaultOnUpdatableRecord", this.returnDefaultOnUpdatableRecord);
        builder.append("returnComputedOnUpdatableRecord", this.returnComputedOnUpdatableRecord);
        builder.append("returnAllOnUpdatableRecord", this.returnAllOnUpdatableRecord);
        builder.append("returnRecordToPojo", this.returnRecordToPojo);
        builder.append("mapJPAAnnotations", this.mapJPAAnnotations);
        builder.append("mapRecordComponentParameterNames", this.mapRecordComponentParameterNames);
        builder.append("mapConstructorPropertiesParameterNames", this.mapConstructorPropertiesParameterNames);
        builder.append("mapConstructorParameterNames", this.mapConstructorParameterNames);
        builder.append("mapConstructorParameterNamesInKotlin", this.mapConstructorParameterNamesInKotlin);
        builder.append("queryPoolable", (Object)this.queryPoolable);
        builder.append("queryTimeout", this.queryTimeout);
        builder.append("maxRows", this.maxRows);
        builder.append("fetchSize", this.fetchSize);
        builder.append("batchSize", this.batchSize);
        builder.append("debugInfoOnStackTrace", this.debugInfoOnStackTrace);
        builder.append("inListPadding", this.inListPadding);
        builder.append("inListPadBase", this.inListPadBase);
        builder.append("delimiter", this.delimiter);
        builder.append("emulateOnDuplicateKeyUpdateOnPrimaryKeyOnly", this.emulateOnDuplicateKeyUpdateOnPrimaryKeyOnly);
        builder.append("emulateMultiset", (Object)this.emulateMultiset);
        builder.append("emulateComputedColumns", this.emulateComputedColumns);
        builder.append("executeUpdateWithoutWhere", (Object)this.executeUpdateWithoutWhere);
        builder.append("executeDeleteWithoutWhere", (Object)this.executeDeleteWithoutWhere);
        builder.append("interpreterDialect", (Object)this.interpreterDialect);
        builder.append("interpreterNameLookupCaseSensitivity", (Object)this.interpreterNameLookupCaseSensitivity);
        builder.append("interpreterLocale", this.interpreterLocale);
        builder.append("interpreterDelayForeignKeyDeclarations", this.interpreterDelayForeignKeyDeclarations);
        builder.append("metaIncludeSystemIndexes", this.metaIncludeSystemIndexes);
        builder.append("metaIncludeSystemSequences", this.metaIncludeSystemSequences);
        builder.append("migrationAllowsUndo", this.migrationAllowsUndo);
        builder.append("migrationRevertUntracked", this.migrationRevertUntracked);
        builder.append("migrationAutoBaseline", this.migrationAutoBaseline);
        builder.append("migrationAutoValidation", this.migrationAutoValidation);
        builder.append("migrationIgnoreDefaultTimestampPrecisionDiffs", this.migrationIgnoreDefaultTimestampPrecisionDiffs);
        builder.append("locale", this.locale);
        builder.append("parseDialect", (Object)this.parseDialect);
        builder.append("parseLocale", this.parseLocale);
        builder.append("parseDateFormat", this.parseDateFormat);
        builder.append("parseTimestampFormat", this.parseTimestampFormat);
        builder.append("parseNamedParamPrefix", this.parseNamedParamPrefix);
        builder.append("parseNameCase", (Object)this.parseNameCase);
        builder.append("parseWithMetaLookups", (Object)this.parseWithMetaLookups);
        builder.append("parseAppendMissingTableReferences", (Object)this.parseAppendMissingTableReferences);
        builder.append("parseSetCommands", this.parseSetCommands);
        builder.append("parseUnsupportedSyntax", (Object)this.parseUnsupportedSyntax);
        builder.append("parseUnknownFunctions", (Object)this.parseUnknownFunctions);
        builder.append("parseIgnoreCommercialOnlyFeatures", this.parseIgnoreCommercialOnlyFeatures);
        builder.append("parseIgnoreComments", this.parseIgnoreComments);
        builder.append("parseIgnoreCommentStart", this.parseIgnoreCommentStart);
        builder.append("parseIgnoreCommentStop", this.parseIgnoreCommentStop);
        builder.append("parseRetainCommentsBetweenQueries", this.parseRetainCommentsBetweenQueries);
        builder.append("parseMetaDefaultExpressions", this.parseMetaDefaultExpressions);
        builder.append("readonlyTableRecordInsert", (Object)this.readonlyTableRecordInsert);
        builder.append("readonlyUpdatableRecordUpdate", (Object)this.readonlyUpdatableRecordUpdate);
        builder.append("readonlyInsert", (Object)this.readonlyInsert);
        builder.append("readonlyUpdate", (Object)this.readonlyUpdate);
        builder.append("applyWorkaroundFor7962", this.applyWorkaroundFor7962);
        builder.append("interpreterSearchPath", "schema", this.interpreterSearchPath);
        builder.append("migrationSchemata", "schema", this.migrationSchemata);
        builder.append("parseSearchPath", "schema", this.parseSearchPath);
    }

    public String toString() {
        XMLBuilder builder = XMLBuilder.nonFormatting();
        this.appendTo(builder);
        return builder.toString();
    }

    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (this.getClass() != that.getClass()) {
            return false;
        }
        Settings other = (Settings)that;
        if (this.forceIntegerTypesOnZeroScaleDecimals == null ? other.forceIntegerTypesOnZeroScaleDecimals != null : !this.forceIntegerTypesOnZeroScaleDecimals.equals(other.forceIntegerTypesOnZeroScaleDecimals)) {
            return false;
        }
        if (this.renderCatalog == null ? other.renderCatalog != null : !this.renderCatalog.equals(other.renderCatalog)) {
            return false;
        }
        if (this.renderSchema == null ? other.renderSchema != null : !this.renderSchema.equals(other.renderSchema)) {
            return false;
        }
        if (this.renderTable == null ? other.renderTable != null : !this.renderTable.equals((Object)other.renderTable)) {
            return false;
        }
        if (this.renderMapping == null ? other.renderMapping != null : !this.renderMapping.equals(other.renderMapping)) {
            return false;
        }
        if (this.renderQuotedNames == null ? other.renderQuotedNames != null : !this.renderQuotedNames.equals((Object)other.renderQuotedNames)) {
            return false;
        }
        if (this.renderNameCase == null ? other.renderNameCase != null : !this.renderNameCase.equals((Object)other.renderNameCase)) {
            return false;
        }
        if (this.renderNameStyle == null ? other.renderNameStyle != null : !this.renderNameStyle.equals((Object)other.renderNameStyle)) {
            return false;
        }
        if (this.renderNamedParamPrefix == null ? other.renderNamedParamPrefix != null : !this.renderNamedParamPrefix.equals(other.renderNamedParamPrefix)) {
            return false;
        }
        if (this.renderKeywordCase == null ? other.renderKeywordCase != null : !this.renderKeywordCase.equals((Object)other.renderKeywordCase)) {
            return false;
        }
        if (this.renderKeywordStyle == null ? other.renderKeywordStyle != null : !this.renderKeywordStyle.equals((Object)other.renderKeywordStyle)) {
            return false;
        }
        if (this.renderLocale == null ? other.renderLocale != null : !this.renderLocale.equals(other.renderLocale)) {
            return false;
        }
        if (this.renderFormatted == null ? other.renderFormatted != null : !this.renderFormatted.equals(other.renderFormatted)) {
            return false;
        }
        if (this.renderFormatting == null ? other.renderFormatting != null : !this.renderFormatting.equals(other.renderFormatting)) {
            return false;
        }
        if (this.renderOptionalAssociativityParentheses == null ? other.renderOptionalAssociativityParentheses != null : !this.renderOptionalAssociativityParentheses.equals((Object)other.renderOptionalAssociativityParentheses)) {
            return false;
        }
        if (this.renderOptionalAsKeywordForTableAliases == null ? other.renderOptionalAsKeywordForTableAliases != null : !this.renderOptionalAsKeywordForTableAliases.equals((Object)other.renderOptionalAsKeywordForTableAliases)) {
            return false;
        }
        if (this.renderOptionalAsKeywordForFieldAliases == null ? other.renderOptionalAsKeywordForFieldAliases != null : !this.renderOptionalAsKeywordForFieldAliases.equals((Object)other.renderOptionalAsKeywordForFieldAliases)) {
            return false;
        }
        if (this.renderOptionalInnerKeyword == null ? other.renderOptionalInnerKeyword != null : !this.renderOptionalInnerKeyword.equals((Object)other.renderOptionalInnerKeyword)) {
            return false;
        }
        if (this.renderOptionalOuterKeyword == null ? other.renderOptionalOuterKeyword != null : !this.renderOptionalOuterKeyword.equals((Object)other.renderOptionalOuterKeyword)) {
            return false;
        }
        if (this.renderImplicitWindowRange == null ? other.renderImplicitWindowRange != null : !this.renderImplicitWindowRange.equals((Object)other.renderImplicitWindowRange)) {
            return false;
        }
        if (this.renderScalarSubqueriesForStoredFunctions == null ? other.renderScalarSubqueriesForStoredFunctions != null : !this.renderScalarSubqueriesForStoredFunctions.equals(other.renderScalarSubqueriesForStoredFunctions)) {
            return false;
        }
        if (this.renderImplicitJoinType == null ? other.renderImplicitJoinType != null : !this.renderImplicitJoinType.equals((Object)other.renderImplicitJoinType)) {
            return false;
        }
        if (this.renderDefaultNullability == null ? other.renderDefaultNullability != null : !this.renderDefaultNullability.equals((Object)other.renderDefaultNullability)) {
            return false;
        }
        if (this.renderCoalesceToEmptyStringInConcat == null ? other.renderCoalesceToEmptyStringInConcat != null : !this.renderCoalesceToEmptyStringInConcat.equals(other.renderCoalesceToEmptyStringInConcat)) {
            return false;
        }
        if (this.renderOrderByRownumberForEmulatedPagination == null ? other.renderOrderByRownumberForEmulatedPagination != null : !this.renderOrderByRownumberForEmulatedPagination.equals(other.renderOrderByRownumberForEmulatedPagination)) {
            return false;
        }
        if (this.renderOutputForSQLServerReturningClause == null ? other.renderOutputForSQLServerReturningClause != null : !this.renderOutputForSQLServerReturningClause.equals(other.renderOutputForSQLServerReturningClause)) {
            return false;
        }
        if (this.renderGroupConcatMaxLenSessionVariable == null ? other.renderGroupConcatMaxLenSessionVariable != null : !this.renderGroupConcatMaxLenSessionVariable.equals(other.renderGroupConcatMaxLenSessionVariable)) {
            return false;
        }
        if (this.renderParenthesisAroundSetOperationQueries == null ? other.renderParenthesisAroundSetOperationQueries != null : !this.renderParenthesisAroundSetOperationQueries.equals(other.renderParenthesisAroundSetOperationQueries)) {
            return false;
        }
        if (this.renderVariablesInDerivedTablesForEmulations == null ? other.renderVariablesInDerivedTablesForEmulations != null : !this.renderVariablesInDerivedTablesForEmulations.equals(other.renderVariablesInDerivedTablesForEmulations)) {
            return false;
        }
        if (this.renderRowConditionForSeekClause == null ? other.renderRowConditionForSeekClause != null : !this.renderRowConditionForSeekClause.equals(other.renderRowConditionForSeekClause)) {
            return false;
        }
        if (this.renderRedundantConditionForSeekClause == null ? other.renderRedundantConditionForSeekClause != null : !this.renderRedundantConditionForSeekClause.equals(other.renderRedundantConditionForSeekClause)) {
            return false;
        }
        if (this.renderPlainSQLTemplatesAsRaw == null ? other.renderPlainSQLTemplatesAsRaw != null : !this.renderPlainSQLTemplatesAsRaw.equals(other.renderPlainSQLTemplatesAsRaw)) {
            return false;
        }
        if (this.namePathSeparator == null ? other.namePathSeparator != null : !this.namePathSeparator.equals(other.namePathSeparator)) {
            return false;
        }
        if (this.bindOffsetDateTimeType == null ? other.bindOffsetDateTimeType != null : !this.bindOffsetDateTimeType.equals(other.bindOffsetDateTimeType)) {
            return false;
        }
        if (this.bindOffsetTimeType == null ? other.bindOffsetTimeType != null : !this.bindOffsetTimeType.equals(other.bindOffsetTimeType)) {
            return false;
        }
        if (this.fetchTriggerValuesAfterSQLServerOutput == null ? other.fetchTriggerValuesAfterSQLServerOutput != null : !this.fetchTriggerValuesAfterSQLServerOutput.equals(other.fetchTriggerValuesAfterSQLServerOutput)) {
            return false;
        }
        if (this.fetchIntermediateResult == null ? other.fetchIntermediateResult != null : !this.fetchIntermediateResult.equals((Object)other.fetchIntermediateResult)) {
            return false;
        }
        if (this.diagnosticsDuplicateStatements == null ? other.diagnosticsDuplicateStatements != null : !this.diagnosticsDuplicateStatements.equals(other.diagnosticsDuplicateStatements)) {
            return false;
        }
        if (this.diagnosticsDuplicateStatementsUsingTransformPatterns == null ? other.diagnosticsDuplicateStatementsUsingTransformPatterns != null : !this.diagnosticsDuplicateStatementsUsingTransformPatterns.equals(other.diagnosticsDuplicateStatementsUsingTransformPatterns)) {
            return false;
        }
        if (this.diagnosticsMissingWasNullCall == null ? other.diagnosticsMissingWasNullCall != null : !this.diagnosticsMissingWasNullCall.equals(other.diagnosticsMissingWasNullCall)) {
            return false;
        }
        if (this.diagnosticsRepeatedStatements == null ? other.diagnosticsRepeatedStatements != null : !this.diagnosticsRepeatedStatements.equals(other.diagnosticsRepeatedStatements)) {
            return false;
        }
        if (this.diagnosticsConsecutiveAggregation == null ? other.diagnosticsConsecutiveAggregation != null : !this.diagnosticsConsecutiveAggregation.equals(other.diagnosticsConsecutiveAggregation)) {
            return false;
        }
        if (this.diagnosticsConcatenationInPredicate == null ? other.diagnosticsConcatenationInPredicate != null : !this.diagnosticsConcatenationInPredicate.equals(other.diagnosticsConcatenationInPredicate)) {
            return false;
        }
        if (this.diagnosticsPossiblyWrongExpression == null ? other.diagnosticsPossiblyWrongExpression != null : !this.diagnosticsPossiblyWrongExpression.equals(other.diagnosticsPossiblyWrongExpression)) {
            return false;
        }
        if (this.diagnosticsTooManyColumnsFetched == null ? other.diagnosticsTooManyColumnsFetched != null : !this.diagnosticsTooManyColumnsFetched.equals(other.diagnosticsTooManyColumnsFetched)) {
            return false;
        }
        if (this.diagnosticsTooManyRowsFetched == null ? other.diagnosticsTooManyRowsFetched != null : !this.diagnosticsTooManyRowsFetched.equals(other.diagnosticsTooManyRowsFetched)) {
            return false;
        }
        if (this.diagnosticsUnnecessaryWasNullCall == null ? other.diagnosticsUnnecessaryWasNullCall != null : !this.diagnosticsUnnecessaryWasNullCall.equals(other.diagnosticsUnnecessaryWasNullCall)) {
            return false;
        }
        if (this.diagnosticsPatterns == null ? other.diagnosticsPatterns != null : !this.diagnosticsPatterns.equals(other.diagnosticsPatterns)) {
            return false;
        }
        if (this.diagnosticsTrivialCondition == null ? other.diagnosticsTrivialCondition != null : !this.diagnosticsTrivialCondition.equals(other.diagnosticsTrivialCondition)) {
            return false;
        }
        if (this.diagnosticsNullCondition == null ? other.diagnosticsNullCondition != null : !this.diagnosticsNullCondition.equals(other.diagnosticsNullCondition)) {
            return false;
        }
        if (this.transformPatterns == null ? other.transformPatterns != null : !this.transformPatterns.equals(other.transformPatterns)) {
            return false;
        }
        if (this.transformPatternsLogging == null ? other.transformPatternsLogging != null : !this.transformPatternsLogging.equals(other.transformPatternsLogging)) {
            return false;
        }
        if (this.transformPatternsUnnecessaryDistinct == null ? other.transformPatternsUnnecessaryDistinct != null : !this.transformPatternsUnnecessaryDistinct.equals(other.transformPatternsUnnecessaryDistinct)) {
            return false;
        }
        if (this.transformPatternsUnnecessaryScalarSubquery == null ? other.transformPatternsUnnecessaryScalarSubquery != null : !this.transformPatternsUnnecessaryScalarSubquery.equals(other.transformPatternsUnnecessaryScalarSubquery)) {
            return false;
        }
        if (this.transformPatternsUnnecessaryInnerJoin == null ? other.transformPatternsUnnecessaryInnerJoin != null : !this.transformPatternsUnnecessaryInnerJoin.equals(other.transformPatternsUnnecessaryInnerJoin)) {
            return false;
        }
        if (this.transformPatternsUnnecessaryGroupByExpressions == null ? other.transformPatternsUnnecessaryGroupByExpressions != null : !this.transformPatternsUnnecessaryGroupByExpressions.equals(other.transformPatternsUnnecessaryGroupByExpressions)) {
            return false;
        }
        if (this.transformPatternsUnnecessaryOrderByExpressions == null ? other.transformPatternsUnnecessaryOrderByExpressions != null : !this.transformPatternsUnnecessaryOrderByExpressions.equals(other.transformPatternsUnnecessaryOrderByExpressions)) {
            return false;
        }
        if (this.transformPatternsUnnecessaryExistsSubqueryClauses == null ? other.transformPatternsUnnecessaryExistsSubqueryClauses != null : !this.transformPatternsUnnecessaryExistsSubqueryClauses.equals(other.transformPatternsUnnecessaryExistsSubqueryClauses)) {
            return false;
        }
        if (this.transformPatternsCountConstant == null ? other.transformPatternsCountConstant != null : !this.transformPatternsCountConstant.equals(other.transformPatternsCountConstant)) {
            return false;
        }
        if (this.transformPatternsTrim == null ? other.transformPatternsTrim != null : !this.transformPatternsTrim.equals(other.transformPatternsTrim)) {
            return false;
        }
        if (this.transformPatternsNotAnd == null ? other.transformPatternsNotAnd != null : !this.transformPatternsNotAnd.equals(other.transformPatternsNotAnd)) {
            return false;
        }
        if (this.transformPatternsNotOr == null ? other.transformPatternsNotOr != null : !this.transformPatternsNotOr.equals(other.transformPatternsNotOr)) {
            return false;
        }
        if (this.transformPatternsNotNot == null ? other.transformPatternsNotNot != null : !this.transformPatternsNotNot.equals(other.transformPatternsNotNot)) {
            return false;
        }
        if (this.transformPatternsNotComparison == null ? other.transformPatternsNotComparison != null : !this.transformPatternsNotComparison.equals(other.transformPatternsNotComparison)) {
            return false;
        }
        if (this.transformPatternsNotNotDistinct == null ? other.transformPatternsNotNotDistinct != null : !this.transformPatternsNotNotDistinct.equals(other.transformPatternsNotNotDistinct)) {
            return false;
        }
        if (this.transformPatternsDistinctFromNull == null ? other.transformPatternsDistinctFromNull != null : !this.transformPatternsDistinctFromNull.equals(other.transformPatternsDistinctFromNull)) {
            return false;
        }
        if (this.transformPatternsNormaliseAssociativeOps == null ? other.transformPatternsNormaliseAssociativeOps != null : !this.transformPatternsNormaliseAssociativeOps.equals(other.transformPatternsNormaliseAssociativeOps)) {
            return false;
        }
        if (this.transformPatternsNormaliseInListSingleElementToComparison == null ? other.transformPatternsNormaliseInListSingleElementToComparison != null : !this.transformPatternsNormaliseInListSingleElementToComparison.equals(other.transformPatternsNormaliseInListSingleElementToComparison)) {
            return false;
        }
        if (this.transformPatternsNormaliseFieldCompareValue == null ? other.transformPatternsNormaliseFieldCompareValue != null : !this.transformPatternsNormaliseFieldCompareValue.equals(other.transformPatternsNormaliseFieldCompareValue)) {
            return false;
        }
        if (this.transformPatternsNormaliseCoalesceToNvl == null ? other.transformPatternsNormaliseCoalesceToNvl != null : !this.transformPatternsNormaliseCoalesceToNvl.equals(other.transformPatternsNormaliseCoalesceToNvl)) {
            return false;
        }
        if (this.transformPatternsOrEqToIn == null ? other.transformPatternsOrEqToIn != null : !this.transformPatternsOrEqToIn.equals(other.transformPatternsOrEqToIn)) {
            return false;
        }
        if (this.transformPatternsAndNeToNotIn == null ? other.transformPatternsAndNeToNotIn != null : !this.transformPatternsAndNeToNotIn.equals(other.transformPatternsAndNeToNotIn)) {
            return false;
        }
        if (this.transformPatternsMergeOrComparison == null ? other.transformPatternsMergeOrComparison != null : !this.transformPatternsMergeOrComparison.equals(other.transformPatternsMergeOrComparison)) {
            return false;
        }
        if (this.transformPatternsMergeAndComparison == null ? other.transformPatternsMergeAndComparison != null : !this.transformPatternsMergeAndComparison.equals(other.transformPatternsMergeAndComparison)) {
            return false;
        }
        if (this.transformPatternsMergeInLists == null ? other.transformPatternsMergeInLists != null : !this.transformPatternsMergeInLists.equals(other.transformPatternsMergeInLists)) {
            return false;
        }
        if (this.transformPatternsMergeRangePredicates == null ? other.transformPatternsMergeRangePredicates != null : !this.transformPatternsMergeRangePredicates.equals(other.transformPatternsMergeRangePredicates)) {
            return false;
        }
        if (this.transformPatternsMergeBetweenSymmetricPredicates == null ? other.transformPatternsMergeBetweenSymmetricPredicates != null : !this.transformPatternsMergeBetweenSymmetricPredicates.equals(other.transformPatternsMergeBetweenSymmetricPredicates)) {
            return false;
        }
        if (this.transformPatternsCaseSearchedToCaseSimple == null ? other.transformPatternsCaseSearchedToCaseSimple != null : !this.transformPatternsCaseSearchedToCaseSimple.equals(other.transformPatternsCaseSearchedToCaseSimple)) {
            return false;
        }
        if (this.transformPatternsCaseElseNull == null ? other.transformPatternsCaseElseNull != null : !this.transformPatternsCaseElseNull.equals(other.transformPatternsCaseElseNull)) {
            return false;
        }
        if (this.transformPatternsUnreachableCaseClauses == null ? other.transformPatternsUnreachableCaseClauses != null : !this.transformPatternsUnreachableCaseClauses.equals(other.transformPatternsUnreachableCaseClauses)) {
            return false;
        }
        if (this.transformPatternsUnreachableDecodeClauses == null ? other.transformPatternsUnreachableDecodeClauses != null : !this.transformPatternsUnreachableDecodeClauses.equals(other.transformPatternsUnreachableDecodeClauses)) {
            return false;
        }
        if (this.transformPatternsCaseDistinctToDecode == null ? other.transformPatternsCaseDistinctToDecode != null : !this.transformPatternsCaseDistinctToDecode.equals(other.transformPatternsCaseDistinctToDecode)) {
            return false;
        }
        if (this.transformPatternsCaseMergeWhenWhen == null ? other.transformPatternsCaseMergeWhenWhen != null : !this.transformPatternsCaseMergeWhenWhen.equals(other.transformPatternsCaseMergeWhenWhen)) {
            return false;
        }
        if (this.transformPatternsCaseMergeWhenElse == null ? other.transformPatternsCaseMergeWhenElse != null : !this.transformPatternsCaseMergeWhenElse.equals(other.transformPatternsCaseMergeWhenElse)) {
            return false;
        }
        if (this.transformPatternsCaseToCaseAbbreviation == null ? other.transformPatternsCaseToCaseAbbreviation != null : !this.transformPatternsCaseToCaseAbbreviation.equals(other.transformPatternsCaseToCaseAbbreviation)) {
            return false;
        }
        if (this.transformPatternsSimplifyCaseAbbreviation == null ? other.transformPatternsSimplifyCaseAbbreviation != null : !this.transformPatternsSimplifyCaseAbbreviation.equals(other.transformPatternsSimplifyCaseAbbreviation)) {
            return false;
        }
        if (this.transformPatternsFlattenCaseAbbreviation == null ? other.transformPatternsFlattenCaseAbbreviation != null : !this.transformPatternsFlattenCaseAbbreviation.equals(other.transformPatternsFlattenCaseAbbreviation)) {
            return false;
        }
        if (this.transformPatternsFlattenDecode == null ? other.transformPatternsFlattenDecode != null : !this.transformPatternsFlattenDecode.equals(other.transformPatternsFlattenDecode)) {
            return false;
        }
        if (this.transformPatternsFlattenCase == null ? other.transformPatternsFlattenCase != null : !this.transformPatternsFlattenCase.equals(other.transformPatternsFlattenCase)) {
            return false;
        }
        if (this.transformPatternsTrivialCaseAbbreviation == null ? other.transformPatternsTrivialCaseAbbreviation != null : !this.transformPatternsTrivialCaseAbbreviation.equals(other.transformPatternsTrivialCaseAbbreviation)) {
            return false;
        }
        if (this.transformPatternsTrivialPredicates == null ? other.transformPatternsTrivialPredicates != null : !this.transformPatternsTrivialPredicates.equals(other.transformPatternsTrivialPredicates)) {
            return false;
        }
        if (this.transformPatternsTrivialBitwiseOperations == null ? other.transformPatternsTrivialBitwiseOperations != null : !this.transformPatternsTrivialBitwiseOperations.equals(other.transformPatternsTrivialBitwiseOperations)) {
            return false;
        }
        if (this.transformPatternsBitSet == null ? other.transformPatternsBitSet != null : !this.transformPatternsBitSet.equals(other.transformPatternsBitSet)) {
            return false;
        }
        if (this.transformPatternsBitGet == null ? other.transformPatternsBitGet != null : !this.transformPatternsBitGet.equals(other.transformPatternsBitGet)) {
            return false;
        }
        if (this.transformPatternsScalarSubqueryCountAsteriskGtZero == null ? other.transformPatternsScalarSubqueryCountAsteriskGtZero != null : !this.transformPatternsScalarSubqueryCountAsteriskGtZero.equals(other.transformPatternsScalarSubqueryCountAsteriskGtZero)) {
            return false;
        }
        if (this.transformPatternsScalarSubqueryCountExpressionGtZero == null ? other.transformPatternsScalarSubqueryCountExpressionGtZero != null : !this.transformPatternsScalarSubqueryCountExpressionGtZero.equals(other.transformPatternsScalarSubqueryCountExpressionGtZero)) {
            return false;
        }
        if (this.transformPatternsEmptyScalarSubquery == null ? other.transformPatternsEmptyScalarSubquery != null : !this.transformPatternsEmptyScalarSubquery.equals(other.transformPatternsEmptyScalarSubquery)) {
            return false;
        }
        if (this.transformPatternsNegNeg == null ? other.transformPatternsNegNeg != null : !this.transformPatternsNegNeg.equals(other.transformPatternsNegNeg)) {
            return false;
        }
        if (this.transformPatternsBitNotBitNot == null ? other.transformPatternsBitNotBitNot != null : !this.transformPatternsBitNotBitNot.equals(other.transformPatternsBitNotBitNot)) {
            return false;
        }
        if (this.transformPatternsBitNotBitNand == null ? other.transformPatternsBitNotBitNand != null : !this.transformPatternsBitNotBitNand.equals(other.transformPatternsBitNotBitNand)) {
            return false;
        }
        if (this.transformPatternsBitNotBitNor == null ? other.transformPatternsBitNotBitNor != null : !this.transformPatternsBitNotBitNor.equals(other.transformPatternsBitNotBitNor)) {
            return false;
        }
        if (this.transformPatternsBitNotBitXNor == null ? other.transformPatternsBitNotBitXNor != null : !this.transformPatternsBitNotBitXNor.equals(other.transformPatternsBitNotBitXNor)) {
            return false;
        }
        if (this.transformPatternsNullOnNullInput == null ? other.transformPatternsNullOnNullInput != null : !this.transformPatternsNullOnNullInput.equals(other.transformPatternsNullOnNullInput)) {
            return false;
        }
        if (this.transformPatternsIdempotentFunctionRepetition == null ? other.transformPatternsIdempotentFunctionRepetition != null : !this.transformPatternsIdempotentFunctionRepetition.equals(other.transformPatternsIdempotentFunctionRepetition)) {
            return false;
        }
        if (this.transformPatternsArithmeticComparisons == null ? other.transformPatternsArithmeticComparisons != null : !this.transformPatternsArithmeticComparisons.equals(other.transformPatternsArithmeticComparisons)) {
            return false;
        }
        if (this.transformPatternsArithmeticExpressions == null ? other.transformPatternsArithmeticExpressions != null : !this.transformPatternsArithmeticExpressions.equals(other.transformPatternsArithmeticExpressions)) {
            return false;
        }
        if (this.transformPatternsTrigonometricFunctions == null ? other.transformPatternsTrigonometricFunctions != null : !this.transformPatternsTrigonometricFunctions.equals(other.transformPatternsTrigonometricFunctions)) {
            return false;
        }
        if (this.transformPatternsLogarithmicFunctions == null ? other.transformPatternsLogarithmicFunctions != null : !this.transformPatternsLogarithmicFunctions.equals(other.transformPatternsLogarithmicFunctions)) {
            return false;
        }
        if (this.transformPatternsHyperbolicFunctions == null ? other.transformPatternsHyperbolicFunctions != null : !this.transformPatternsHyperbolicFunctions.equals(other.transformPatternsHyperbolicFunctions)) {
            return false;
        }
        if (this.transformPatternsInverseHyperbolicFunctions == null ? other.transformPatternsInverseHyperbolicFunctions != null : !this.transformPatternsInverseHyperbolicFunctions.equals(other.transformPatternsInverseHyperbolicFunctions)) {
            return false;
        }
        if (this.transformInlineBindValuesForFieldComparisons == null ? other.transformInlineBindValuesForFieldComparisons != null : !this.transformInlineBindValuesForFieldComparisons.equals(other.transformInlineBindValuesForFieldComparisons)) {
            return false;
        }
        if (this.transformAnsiJoinToTableLists == null ? other.transformAnsiJoinToTableLists != null : !this.transformAnsiJoinToTableLists.equals(other.transformAnsiJoinToTableLists)) {
            return false;
        }
        if (this.transformInConditionSubqueryWithLimitToDerivedTable == null ? other.transformInConditionSubqueryWithLimitToDerivedTable != null : !this.transformInConditionSubqueryWithLimitToDerivedTable.equals((Object)other.transformInConditionSubqueryWithLimitToDerivedTable)) {
            return false;
        }
        if (this.transformQualify == null ? other.transformQualify != null : !this.transformQualify.equals((Object)other.transformQualify)) {
            return false;
        }
        if (this.transformTableListsToAnsiJoin == null ? other.transformTableListsToAnsiJoin != null : !this.transformTableListsToAnsiJoin.equals(other.transformTableListsToAnsiJoin)) {
            return false;
        }
        if (this.transformRownum == null ? other.transformRownum != null : !this.transformRownum.equals((Object)other.transformRownum)) {
            return false;
        }
        if (this.transformUnneededArithmeticExpressions == null ? other.transformUnneededArithmeticExpressions != null : !this.transformUnneededArithmeticExpressions.equals((Object)other.transformUnneededArithmeticExpressions)) {
            return false;
        }
        if (this.transformGroupByColumnIndex == null ? other.transformGroupByColumnIndex != null : !this.transformGroupByColumnIndex.equals((Object)other.transformGroupByColumnIndex)) {
            return false;
        }
        if (this.transformInlineCTE == null ? other.transformInlineCTE != null : !this.transformInlineCTE.equals((Object)other.transformInlineCTE)) {
            return false;
        }
        if (this.backslashEscaping == null ? other.backslashEscaping != null : !this.backslashEscaping.equals((Object)other.backslashEscaping)) {
            return false;
        }
        if (this.paramType == null ? other.paramType != null : !this.paramType.equals((Object)other.paramType)) {
            return false;
        }
        if (this.paramCastMode == null ? other.paramCastMode != null : !this.paramCastMode.equals((Object)other.paramCastMode)) {
            return false;
        }
        if (this.statementType == null ? other.statementType != null : !this.statementType.equals((Object)other.statementType)) {
            return false;
        }
        if (this.inlineThreshold == null ? other.inlineThreshold != null : !this.inlineThreshold.equals(other.inlineThreshold)) {
            return false;
        }
        if (this.transactionListenerStartInvocationOrder == null ? other.transactionListenerStartInvocationOrder != null : !this.transactionListenerStartInvocationOrder.equals((Object)other.transactionListenerStartInvocationOrder)) {
            return false;
        }
        if (this.transactionListenerEndInvocationOrder == null ? other.transactionListenerEndInvocationOrder != null : !this.transactionListenerEndInvocationOrder.equals((Object)other.transactionListenerEndInvocationOrder)) {
            return false;
        }
        if (this.migrationListenerStartInvocationOrder == null ? other.migrationListenerStartInvocationOrder != null : !this.migrationListenerStartInvocationOrder.equals((Object)other.migrationListenerStartInvocationOrder)) {
            return false;
        }
        if (this.migrationListenerEndInvocationOrder == null ? other.migrationListenerEndInvocationOrder != null : !this.migrationListenerEndInvocationOrder.equals((Object)other.migrationListenerEndInvocationOrder)) {
            return false;
        }
        if (this.visitListenerStartInvocationOrder == null ? other.visitListenerStartInvocationOrder != null : !this.visitListenerStartInvocationOrder.equals((Object)other.visitListenerStartInvocationOrder)) {
            return false;
        }
        if (this.visitListenerEndInvocationOrder == null ? other.visitListenerEndInvocationOrder != null : !this.visitListenerEndInvocationOrder.equals((Object)other.visitListenerEndInvocationOrder)) {
            return false;
        }
        if (this.recordListenerStartInvocationOrder == null ? other.recordListenerStartInvocationOrder != null : !this.recordListenerStartInvocationOrder.equals((Object)other.recordListenerStartInvocationOrder)) {
            return false;
        }
        if (this.recordListenerEndInvocationOrder == null ? other.recordListenerEndInvocationOrder != null : !this.recordListenerEndInvocationOrder.equals((Object)other.recordListenerEndInvocationOrder)) {
            return false;
        }
        if (this.executeListenerStartInvocationOrder == null ? other.executeListenerStartInvocationOrder != null : !this.executeListenerStartInvocationOrder.equals((Object)other.executeListenerStartInvocationOrder)) {
            return false;
        }
        if (this.executeListenerEndInvocationOrder == null ? other.executeListenerEndInvocationOrder != null : !this.executeListenerEndInvocationOrder.equals((Object)other.executeListenerEndInvocationOrder)) {
            return false;
        }
        if (this.executeLogging == null ? other.executeLogging != null : !this.executeLogging.equals(other.executeLogging)) {
            return false;
        }
        if (this.executeLoggingSQLExceptions == null ? other.executeLoggingSQLExceptions != null : !this.executeLoggingSQLExceptions.equals(other.executeLoggingSQLExceptions)) {
            return false;
        }
        if (this.diagnosticsLogging == null ? other.diagnosticsLogging != null : !this.diagnosticsLogging.equals(other.diagnosticsLogging)) {
            return false;
        }
        if (this.diagnosticsConnection == null ? other.diagnosticsConnection != null : !this.diagnosticsConnection.equals((Object)other.diagnosticsConnection)) {
            return false;
        }
        if (this.updateRecordVersion == null ? other.updateRecordVersion != null : !this.updateRecordVersion.equals(other.updateRecordVersion)) {
            return false;
        }
        if (this.updateRecordTimestamp == null ? other.updateRecordTimestamp != null : !this.updateRecordTimestamp.equals(other.updateRecordTimestamp)) {
            return false;
        }
        if (this.executeWithOptimisticLocking == null ? other.executeWithOptimisticLocking != null : !this.executeWithOptimisticLocking.equals(other.executeWithOptimisticLocking)) {
            return false;
        }
        if (this.executeWithOptimisticLockingExcludeUnversioned == null ? other.executeWithOptimisticLockingExcludeUnversioned != null : !this.executeWithOptimisticLockingExcludeUnversioned.equals(other.executeWithOptimisticLockingExcludeUnversioned)) {
            return false;
        }
        if (this.attachRecords == null ? other.attachRecords != null : !this.attachRecords.equals(other.attachRecords)) {
            return false;
        }
        if (this.insertUnchangedRecords == null ? other.insertUnchangedRecords != null : !this.insertUnchangedRecords.equals(other.insertUnchangedRecords)) {
            return false;
        }
        if (this.updateUnchangedRecords == null ? other.updateUnchangedRecords != null : !this.updateUnchangedRecords.equals((Object)other.updateUnchangedRecords)) {
            return false;
        }
        if (this.updatablePrimaryKeys == null ? other.updatablePrimaryKeys != null : !this.updatablePrimaryKeys.equals(other.updatablePrimaryKeys)) {
            return false;
        }
        if (this.reflectionCaching == null ? other.reflectionCaching != null : !this.reflectionCaching.equals(other.reflectionCaching)) {
            return false;
        }
        if (this.cacheRecordMappers == null ? other.cacheRecordMappers != null : !this.cacheRecordMappers.equals(other.cacheRecordMappers)) {
            return false;
        }
        if (this.cacheParsingConnection == null ? other.cacheParsingConnection != null : !this.cacheParsingConnection.equals(other.cacheParsingConnection)) {
            return false;
        }
        if (this.cacheParsingConnectionLRUCacheSize == null ? other.cacheParsingConnectionLRUCacheSize != null : !this.cacheParsingConnectionLRUCacheSize.equals(other.cacheParsingConnectionLRUCacheSize)) {
            return false;
        }
        if (this.cachePreparedStatementInLoader == null ? other.cachePreparedStatementInLoader != null : !this.cachePreparedStatementInLoader.equals(other.cachePreparedStatementInLoader)) {
            return false;
        }
        if (this.throwExceptions == null ? other.throwExceptions != null : !this.throwExceptions.equals((Object)other.throwExceptions)) {
            return false;
        }
        if (this.fetchWarnings == null ? other.fetchWarnings != null : !this.fetchWarnings.equals(other.fetchWarnings)) {
            return false;
        }
        if (this.fetchServerOutputSize == null ? other.fetchServerOutputSize != null : !this.fetchServerOutputSize.equals(other.fetchServerOutputSize)) {
            return false;
        }
        if (this.returnIdentityOnUpdatableRecord == null ? other.returnIdentityOnUpdatableRecord != null : !this.returnIdentityOnUpdatableRecord.equals(other.returnIdentityOnUpdatableRecord)) {
            return false;
        }
        if (this.returnDefaultOnUpdatableRecord == null ? other.returnDefaultOnUpdatableRecord != null : !this.returnDefaultOnUpdatableRecord.equals(other.returnDefaultOnUpdatableRecord)) {
            return false;
        }
        if (this.returnComputedOnUpdatableRecord == null ? other.returnComputedOnUpdatableRecord != null : !this.returnComputedOnUpdatableRecord.equals(other.returnComputedOnUpdatableRecord)) {
            return false;
        }
        if (this.returnAllOnUpdatableRecord == null ? other.returnAllOnUpdatableRecord != null : !this.returnAllOnUpdatableRecord.equals(other.returnAllOnUpdatableRecord)) {
            return false;
        }
        if (this.returnRecordToPojo == null ? other.returnRecordToPojo != null : !this.returnRecordToPojo.equals(other.returnRecordToPojo)) {
            return false;
        }
        if (this.mapJPAAnnotations == null ? other.mapJPAAnnotations != null : !this.mapJPAAnnotations.equals(other.mapJPAAnnotations)) {
            return false;
        }
        if (this.mapRecordComponentParameterNames == null ? other.mapRecordComponentParameterNames != null : !this.mapRecordComponentParameterNames.equals(other.mapRecordComponentParameterNames)) {
            return false;
        }
        if (this.mapConstructorPropertiesParameterNames == null ? other.mapConstructorPropertiesParameterNames != null : !this.mapConstructorPropertiesParameterNames.equals(other.mapConstructorPropertiesParameterNames)) {
            return false;
        }
        if (this.mapConstructorParameterNames == null ? other.mapConstructorParameterNames != null : !this.mapConstructorParameterNames.equals(other.mapConstructorParameterNames)) {
            return false;
        }
        if (this.mapConstructorParameterNamesInKotlin == null ? other.mapConstructorParameterNamesInKotlin != null : !this.mapConstructorParameterNamesInKotlin.equals(other.mapConstructorParameterNamesInKotlin)) {
            return false;
        }
        if (this.queryPoolable == null ? other.queryPoolable != null : !this.queryPoolable.equals((Object)other.queryPoolable)) {
            return false;
        }
        if (this.queryTimeout == null ? other.queryTimeout != null : !this.queryTimeout.equals(other.queryTimeout)) {
            return false;
        }
        if (this.maxRows == null ? other.maxRows != null : !this.maxRows.equals(other.maxRows)) {
            return false;
        }
        if (this.fetchSize == null ? other.fetchSize != null : !this.fetchSize.equals(other.fetchSize)) {
            return false;
        }
        if (this.batchSize == null ? other.batchSize != null : !this.batchSize.equals(other.batchSize)) {
            return false;
        }
        if (this.debugInfoOnStackTrace == null ? other.debugInfoOnStackTrace != null : !this.debugInfoOnStackTrace.equals(other.debugInfoOnStackTrace)) {
            return false;
        }
        if (this.inListPadding == null ? other.inListPadding != null : !this.inListPadding.equals(other.inListPadding)) {
            return false;
        }
        if (this.inListPadBase == null ? other.inListPadBase != null : !this.inListPadBase.equals(other.inListPadBase)) {
            return false;
        }
        if (this.delimiter == null ? other.delimiter != null : !this.delimiter.equals(other.delimiter)) {
            return false;
        }
        if (this.emulateOnDuplicateKeyUpdateOnPrimaryKeyOnly == null ? other.emulateOnDuplicateKeyUpdateOnPrimaryKeyOnly != null : !this.emulateOnDuplicateKeyUpdateOnPrimaryKeyOnly.equals(other.emulateOnDuplicateKeyUpdateOnPrimaryKeyOnly)) {
            return false;
        }
        if (this.emulateMultiset == null ? other.emulateMultiset != null : !this.emulateMultiset.equals((Object)other.emulateMultiset)) {
            return false;
        }
        if (this.emulateComputedColumns == null ? other.emulateComputedColumns != null : !this.emulateComputedColumns.equals(other.emulateComputedColumns)) {
            return false;
        }
        if (this.executeUpdateWithoutWhere == null ? other.executeUpdateWithoutWhere != null : !this.executeUpdateWithoutWhere.equals((Object)other.executeUpdateWithoutWhere)) {
            return false;
        }
        if (this.executeDeleteWithoutWhere == null ? other.executeDeleteWithoutWhere != null : !this.executeDeleteWithoutWhere.equals((Object)other.executeDeleteWithoutWhere)) {
            return false;
        }
        if (this.interpreterDialect == null ? other.interpreterDialect != null : !this.interpreterDialect.equals((Object)other.interpreterDialect)) {
            return false;
        }
        if (this.interpreterNameLookupCaseSensitivity == null ? other.interpreterNameLookupCaseSensitivity != null : !this.interpreterNameLookupCaseSensitivity.equals((Object)other.interpreterNameLookupCaseSensitivity)) {
            return false;
        }
        if (this.interpreterLocale == null ? other.interpreterLocale != null : !this.interpreterLocale.equals(other.interpreterLocale)) {
            return false;
        }
        if (this.interpreterDelayForeignKeyDeclarations == null ? other.interpreterDelayForeignKeyDeclarations != null : !this.interpreterDelayForeignKeyDeclarations.equals(other.interpreterDelayForeignKeyDeclarations)) {
            return false;
        }
        if (this.metaIncludeSystemIndexes == null ? other.metaIncludeSystemIndexes != null : !this.metaIncludeSystemIndexes.equals(other.metaIncludeSystemIndexes)) {
            return false;
        }
        if (this.metaIncludeSystemSequences == null ? other.metaIncludeSystemSequences != null : !this.metaIncludeSystemSequences.equals(other.metaIncludeSystemSequences)) {
            return false;
        }
        if (this.migrationAllowsUndo == null ? other.migrationAllowsUndo != null : !this.migrationAllowsUndo.equals(other.migrationAllowsUndo)) {
            return false;
        }
        if (this.migrationRevertUntracked == null ? other.migrationRevertUntracked != null : !this.migrationRevertUntracked.equals(other.migrationRevertUntracked)) {
            return false;
        }
        if (this.migrationAutoBaseline == null ? other.migrationAutoBaseline != null : !this.migrationAutoBaseline.equals(other.migrationAutoBaseline)) {
            return false;
        }
        if (this.migrationAutoValidation == null ? other.migrationAutoValidation != null : !this.migrationAutoValidation.equals(other.migrationAutoValidation)) {
            return false;
        }
        if (this.migrationIgnoreDefaultTimestampPrecisionDiffs == null ? other.migrationIgnoreDefaultTimestampPrecisionDiffs != null : !this.migrationIgnoreDefaultTimestampPrecisionDiffs.equals(other.migrationIgnoreDefaultTimestampPrecisionDiffs)) {
            return false;
        }
        if (this.locale == null ? other.locale != null : !this.locale.equals(other.locale)) {
            return false;
        }
        if (this.parseDialect == null ? other.parseDialect != null : !this.parseDialect.equals((Object)other.parseDialect)) {
            return false;
        }
        if (this.parseLocale == null ? other.parseLocale != null : !this.parseLocale.equals(other.parseLocale)) {
            return false;
        }
        if (this.parseDateFormat == null ? other.parseDateFormat != null : !this.parseDateFormat.equals(other.parseDateFormat)) {
            return false;
        }
        if (this.parseTimestampFormat == null ? other.parseTimestampFormat != null : !this.parseTimestampFormat.equals(other.parseTimestampFormat)) {
            return false;
        }
        if (this.parseNamedParamPrefix == null ? other.parseNamedParamPrefix != null : !this.parseNamedParamPrefix.equals(other.parseNamedParamPrefix)) {
            return false;
        }
        if (this.parseNameCase == null ? other.parseNameCase != null : !this.parseNameCase.equals((Object)other.parseNameCase)) {
            return false;
        }
        if (this.parseWithMetaLookups == null ? other.parseWithMetaLookups != null : !this.parseWithMetaLookups.equals((Object)other.parseWithMetaLookups)) {
            return false;
        }
        if (this.parseAppendMissingTableReferences == null ? other.parseAppendMissingTableReferences != null : !this.parseAppendMissingTableReferences.equals((Object)other.parseAppendMissingTableReferences)) {
            return false;
        }
        if (this.parseSetCommands == null ? other.parseSetCommands != null : !this.parseSetCommands.equals(other.parseSetCommands)) {
            return false;
        }
        if (this.parseUnsupportedSyntax == null ? other.parseUnsupportedSyntax != null : !this.parseUnsupportedSyntax.equals((Object)other.parseUnsupportedSyntax)) {
            return false;
        }
        if (this.parseUnknownFunctions == null ? other.parseUnknownFunctions != null : !this.parseUnknownFunctions.equals((Object)other.parseUnknownFunctions)) {
            return false;
        }
        if (this.parseIgnoreCommercialOnlyFeatures == null ? other.parseIgnoreCommercialOnlyFeatures != null : !this.parseIgnoreCommercialOnlyFeatures.equals(other.parseIgnoreCommercialOnlyFeatures)) {
            return false;
        }
        if (this.parseIgnoreComments == null ? other.parseIgnoreComments != null : !this.parseIgnoreComments.equals(other.parseIgnoreComments)) {
            return false;
        }
        if (this.parseIgnoreCommentStart == null ? other.parseIgnoreCommentStart != null : !this.parseIgnoreCommentStart.equals(other.parseIgnoreCommentStart)) {
            return false;
        }
        if (this.parseIgnoreCommentStop == null ? other.parseIgnoreCommentStop != null : !this.parseIgnoreCommentStop.equals(other.parseIgnoreCommentStop)) {
            return false;
        }
        if (this.parseRetainCommentsBetweenQueries == null ? other.parseRetainCommentsBetweenQueries != null : !this.parseRetainCommentsBetweenQueries.equals(other.parseRetainCommentsBetweenQueries)) {
            return false;
        }
        if (this.parseMetaDefaultExpressions == null ? other.parseMetaDefaultExpressions != null : !this.parseMetaDefaultExpressions.equals(other.parseMetaDefaultExpressions)) {
            return false;
        }
        if (this.readonlyTableRecordInsert == null ? other.readonlyTableRecordInsert != null : !this.readonlyTableRecordInsert.equals((Object)other.readonlyTableRecordInsert)) {
            return false;
        }
        if (this.readonlyUpdatableRecordUpdate == null ? other.readonlyUpdatableRecordUpdate != null : !this.readonlyUpdatableRecordUpdate.equals((Object)other.readonlyUpdatableRecordUpdate)) {
            return false;
        }
        if (this.readonlyInsert == null ? other.readonlyInsert != null : !this.readonlyInsert.equals((Object)other.readonlyInsert)) {
            return false;
        }
        if (this.readonlyUpdate == null ? other.readonlyUpdate != null : !this.readonlyUpdate.equals((Object)other.readonlyUpdate)) {
            return false;
        }
        if (this.applyWorkaroundFor7962 == null ? other.applyWorkaroundFor7962 != null : !this.applyWorkaroundFor7962.equals(other.applyWorkaroundFor7962)) {
            return false;
        }
        if (this.interpreterSearchPath == null ? other.interpreterSearchPath != null : !this.interpreterSearchPath.equals(other.interpreterSearchPath)) {
            return false;
        }
        if (this.migrationSchemata == null ? other.migrationSchemata != null : !this.migrationSchemata.equals(other.migrationSchemata)) {
            return false;
        }
        return !(this.parseSearchPath == null ? other.parseSearchPath != null : !this.parseSearchPath.equals(other.parseSearchPath));
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.forceIntegerTypesOnZeroScaleDecimals == null ? 0 : this.forceIntegerTypesOnZeroScaleDecimals.hashCode());
        result = 31 * result + (this.renderCatalog == null ? 0 : this.renderCatalog.hashCode());
        result = 31 * result + (this.renderSchema == null ? 0 : this.renderSchema.hashCode());
        result = 31 * result + (this.renderTable == null ? 0 : this.renderTable.hashCode());
        result = 31 * result + (this.renderMapping == null ? 0 : this.renderMapping.hashCode());
        result = 31 * result + (this.renderQuotedNames == null ? 0 : this.renderQuotedNames.hashCode());
        result = 31 * result + (this.renderNameCase == null ? 0 : this.renderNameCase.hashCode());
        result = 31 * result + (this.renderNameStyle == null ? 0 : this.renderNameStyle.hashCode());
        result = 31 * result + (this.renderNamedParamPrefix == null ? 0 : this.renderNamedParamPrefix.hashCode());
        result = 31 * result + (this.renderKeywordCase == null ? 0 : this.renderKeywordCase.hashCode());
        result = 31 * result + (this.renderKeywordStyle == null ? 0 : this.renderKeywordStyle.hashCode());
        result = 31 * result + (this.renderLocale == null ? 0 : this.renderLocale.hashCode());
        result = 31 * result + (this.renderFormatted == null ? 0 : this.renderFormatted.hashCode());
        result = 31 * result + (this.renderFormatting == null ? 0 : this.renderFormatting.hashCode());
        result = 31 * result + (this.renderOptionalAssociativityParentheses == null ? 0 : this.renderOptionalAssociativityParentheses.hashCode());
        result = 31 * result + (this.renderOptionalAsKeywordForTableAliases == null ? 0 : this.renderOptionalAsKeywordForTableAliases.hashCode());
        result = 31 * result + (this.renderOptionalAsKeywordForFieldAliases == null ? 0 : this.renderOptionalAsKeywordForFieldAliases.hashCode());
        result = 31 * result + (this.renderOptionalInnerKeyword == null ? 0 : this.renderOptionalInnerKeyword.hashCode());
        result = 31 * result + (this.renderOptionalOuterKeyword == null ? 0 : this.renderOptionalOuterKeyword.hashCode());
        result = 31 * result + (this.renderImplicitWindowRange == null ? 0 : this.renderImplicitWindowRange.hashCode());
        result = 31 * result + (this.renderScalarSubqueriesForStoredFunctions == null ? 0 : this.renderScalarSubqueriesForStoredFunctions.hashCode());
        result = 31 * result + (this.renderImplicitJoinType == null ? 0 : this.renderImplicitJoinType.hashCode());
        result = 31 * result + (this.renderDefaultNullability == null ? 0 : this.renderDefaultNullability.hashCode());
        result = 31 * result + (this.renderCoalesceToEmptyStringInConcat == null ? 0 : this.renderCoalesceToEmptyStringInConcat.hashCode());
        result = 31 * result + (this.renderOrderByRownumberForEmulatedPagination == null ? 0 : this.renderOrderByRownumberForEmulatedPagination.hashCode());
        result = 31 * result + (this.renderOutputForSQLServerReturningClause == null ? 0 : this.renderOutputForSQLServerReturningClause.hashCode());
        result = 31 * result + (this.renderGroupConcatMaxLenSessionVariable == null ? 0 : this.renderGroupConcatMaxLenSessionVariable.hashCode());
        result = 31 * result + (this.renderParenthesisAroundSetOperationQueries == null ? 0 : this.renderParenthesisAroundSetOperationQueries.hashCode());
        result = 31 * result + (this.renderVariablesInDerivedTablesForEmulations == null ? 0 : this.renderVariablesInDerivedTablesForEmulations.hashCode());
        result = 31 * result + (this.renderRowConditionForSeekClause == null ? 0 : this.renderRowConditionForSeekClause.hashCode());
        result = 31 * result + (this.renderRedundantConditionForSeekClause == null ? 0 : this.renderRedundantConditionForSeekClause.hashCode());
        result = 31 * result + (this.renderPlainSQLTemplatesAsRaw == null ? 0 : this.renderPlainSQLTemplatesAsRaw.hashCode());
        result = 31 * result + (this.namePathSeparator == null ? 0 : this.namePathSeparator.hashCode());
        result = 31 * result + (this.bindOffsetDateTimeType == null ? 0 : this.bindOffsetDateTimeType.hashCode());
        result = 31 * result + (this.bindOffsetTimeType == null ? 0 : this.bindOffsetTimeType.hashCode());
        result = 31 * result + (this.fetchTriggerValuesAfterSQLServerOutput == null ? 0 : this.fetchTriggerValuesAfterSQLServerOutput.hashCode());
        result = 31 * result + (this.fetchIntermediateResult == null ? 0 : this.fetchIntermediateResult.hashCode());
        result = 31 * result + (this.diagnosticsDuplicateStatements == null ? 0 : this.diagnosticsDuplicateStatements.hashCode());
        result = 31 * result + (this.diagnosticsDuplicateStatementsUsingTransformPatterns == null ? 0 : this.diagnosticsDuplicateStatementsUsingTransformPatterns.hashCode());
        result = 31 * result + (this.diagnosticsMissingWasNullCall == null ? 0 : this.diagnosticsMissingWasNullCall.hashCode());
        result = 31 * result + (this.diagnosticsRepeatedStatements == null ? 0 : this.diagnosticsRepeatedStatements.hashCode());
        result = 31 * result + (this.diagnosticsConsecutiveAggregation == null ? 0 : this.diagnosticsConsecutiveAggregation.hashCode());
        result = 31 * result + (this.diagnosticsConcatenationInPredicate == null ? 0 : this.diagnosticsConcatenationInPredicate.hashCode());
        result = 31 * result + (this.diagnosticsPossiblyWrongExpression == null ? 0 : this.diagnosticsPossiblyWrongExpression.hashCode());
        result = 31 * result + (this.diagnosticsTooManyColumnsFetched == null ? 0 : this.diagnosticsTooManyColumnsFetched.hashCode());
        result = 31 * result + (this.diagnosticsTooManyRowsFetched == null ? 0 : this.diagnosticsTooManyRowsFetched.hashCode());
        result = 31 * result + (this.diagnosticsUnnecessaryWasNullCall == null ? 0 : this.diagnosticsUnnecessaryWasNullCall.hashCode());
        result = 31 * result + (this.diagnosticsPatterns == null ? 0 : this.diagnosticsPatterns.hashCode());
        result = 31 * result + (this.diagnosticsTrivialCondition == null ? 0 : this.diagnosticsTrivialCondition.hashCode());
        result = 31 * result + (this.diagnosticsNullCondition == null ? 0 : this.diagnosticsNullCondition.hashCode());
        result = 31 * result + (this.transformPatterns == null ? 0 : this.transformPatterns.hashCode());
        result = 31 * result + (this.transformPatternsLogging == null ? 0 : this.transformPatternsLogging.hashCode());
        result = 31 * result + (this.transformPatternsUnnecessaryDistinct == null ? 0 : this.transformPatternsUnnecessaryDistinct.hashCode());
        result = 31 * result + (this.transformPatternsUnnecessaryScalarSubquery == null ? 0 : this.transformPatternsUnnecessaryScalarSubquery.hashCode());
        result = 31 * result + (this.transformPatternsUnnecessaryInnerJoin == null ? 0 : this.transformPatternsUnnecessaryInnerJoin.hashCode());
        result = 31 * result + (this.transformPatternsUnnecessaryGroupByExpressions == null ? 0 : this.transformPatternsUnnecessaryGroupByExpressions.hashCode());
        result = 31 * result + (this.transformPatternsUnnecessaryOrderByExpressions == null ? 0 : this.transformPatternsUnnecessaryOrderByExpressions.hashCode());
        result = 31 * result + (this.transformPatternsUnnecessaryExistsSubqueryClauses == null ? 0 : this.transformPatternsUnnecessaryExistsSubqueryClauses.hashCode());
        result = 31 * result + (this.transformPatternsCountConstant == null ? 0 : this.transformPatternsCountConstant.hashCode());
        result = 31 * result + (this.transformPatternsTrim == null ? 0 : this.transformPatternsTrim.hashCode());
        result = 31 * result + (this.transformPatternsNotAnd == null ? 0 : this.transformPatternsNotAnd.hashCode());
        result = 31 * result + (this.transformPatternsNotOr == null ? 0 : this.transformPatternsNotOr.hashCode());
        result = 31 * result + (this.transformPatternsNotNot == null ? 0 : this.transformPatternsNotNot.hashCode());
        result = 31 * result + (this.transformPatternsNotComparison == null ? 0 : this.transformPatternsNotComparison.hashCode());
        result = 31 * result + (this.transformPatternsNotNotDistinct == null ? 0 : this.transformPatternsNotNotDistinct.hashCode());
        result = 31 * result + (this.transformPatternsDistinctFromNull == null ? 0 : this.transformPatternsDistinctFromNull.hashCode());
        result = 31 * result + (this.transformPatternsNormaliseAssociativeOps == null ? 0 : this.transformPatternsNormaliseAssociativeOps.hashCode());
        result = 31 * result + (this.transformPatternsNormaliseInListSingleElementToComparison == null ? 0 : this.transformPatternsNormaliseInListSingleElementToComparison.hashCode());
        result = 31 * result + (this.transformPatternsNormaliseFieldCompareValue == null ? 0 : this.transformPatternsNormaliseFieldCompareValue.hashCode());
        result = 31 * result + (this.transformPatternsNormaliseCoalesceToNvl == null ? 0 : this.transformPatternsNormaliseCoalesceToNvl.hashCode());
        result = 31 * result + (this.transformPatternsOrEqToIn == null ? 0 : this.transformPatternsOrEqToIn.hashCode());
        result = 31 * result + (this.transformPatternsAndNeToNotIn == null ? 0 : this.transformPatternsAndNeToNotIn.hashCode());
        result = 31 * result + (this.transformPatternsMergeOrComparison == null ? 0 : this.transformPatternsMergeOrComparison.hashCode());
        result = 31 * result + (this.transformPatternsMergeAndComparison == null ? 0 : this.transformPatternsMergeAndComparison.hashCode());
        result = 31 * result + (this.transformPatternsMergeInLists == null ? 0 : this.transformPatternsMergeInLists.hashCode());
        result = 31 * result + (this.transformPatternsMergeRangePredicates == null ? 0 : this.transformPatternsMergeRangePredicates.hashCode());
        result = 31 * result + (this.transformPatternsMergeBetweenSymmetricPredicates == null ? 0 : this.transformPatternsMergeBetweenSymmetricPredicates.hashCode());
        result = 31 * result + (this.transformPatternsCaseSearchedToCaseSimple == null ? 0 : this.transformPatternsCaseSearchedToCaseSimple.hashCode());
        result = 31 * result + (this.transformPatternsCaseElseNull == null ? 0 : this.transformPatternsCaseElseNull.hashCode());
        result = 31 * result + (this.transformPatternsUnreachableCaseClauses == null ? 0 : this.transformPatternsUnreachableCaseClauses.hashCode());
        result = 31 * result + (this.transformPatternsUnreachableDecodeClauses == null ? 0 : this.transformPatternsUnreachableDecodeClauses.hashCode());
        result = 31 * result + (this.transformPatternsCaseDistinctToDecode == null ? 0 : this.transformPatternsCaseDistinctToDecode.hashCode());
        result = 31 * result + (this.transformPatternsCaseMergeWhenWhen == null ? 0 : this.transformPatternsCaseMergeWhenWhen.hashCode());
        result = 31 * result + (this.transformPatternsCaseMergeWhenElse == null ? 0 : this.transformPatternsCaseMergeWhenElse.hashCode());
        result = 31 * result + (this.transformPatternsCaseToCaseAbbreviation == null ? 0 : this.transformPatternsCaseToCaseAbbreviation.hashCode());
        result = 31 * result + (this.transformPatternsSimplifyCaseAbbreviation == null ? 0 : this.transformPatternsSimplifyCaseAbbreviation.hashCode());
        result = 31 * result + (this.transformPatternsFlattenCaseAbbreviation == null ? 0 : this.transformPatternsFlattenCaseAbbreviation.hashCode());
        result = 31 * result + (this.transformPatternsFlattenDecode == null ? 0 : this.transformPatternsFlattenDecode.hashCode());
        result = 31 * result + (this.transformPatternsFlattenCase == null ? 0 : this.transformPatternsFlattenCase.hashCode());
        result = 31 * result + (this.transformPatternsTrivialCaseAbbreviation == null ? 0 : this.transformPatternsTrivialCaseAbbreviation.hashCode());
        result = 31 * result + (this.transformPatternsTrivialPredicates == null ? 0 : this.transformPatternsTrivialPredicates.hashCode());
        result = 31 * result + (this.transformPatternsTrivialBitwiseOperations == null ? 0 : this.transformPatternsTrivialBitwiseOperations.hashCode());
        result = 31 * result + (this.transformPatternsBitSet == null ? 0 : this.transformPatternsBitSet.hashCode());
        result = 31 * result + (this.transformPatternsBitGet == null ? 0 : this.transformPatternsBitGet.hashCode());
        result = 31 * result + (this.transformPatternsScalarSubqueryCountAsteriskGtZero == null ? 0 : this.transformPatternsScalarSubqueryCountAsteriskGtZero.hashCode());
        result = 31 * result + (this.transformPatternsScalarSubqueryCountExpressionGtZero == null ? 0 : this.transformPatternsScalarSubqueryCountExpressionGtZero.hashCode());
        result = 31 * result + (this.transformPatternsEmptyScalarSubquery == null ? 0 : this.transformPatternsEmptyScalarSubquery.hashCode());
        result = 31 * result + (this.transformPatternsNegNeg == null ? 0 : this.transformPatternsNegNeg.hashCode());
        result = 31 * result + (this.transformPatternsBitNotBitNot == null ? 0 : this.transformPatternsBitNotBitNot.hashCode());
        result = 31 * result + (this.transformPatternsBitNotBitNand == null ? 0 : this.transformPatternsBitNotBitNand.hashCode());
        result = 31 * result + (this.transformPatternsBitNotBitNor == null ? 0 : this.transformPatternsBitNotBitNor.hashCode());
        result = 31 * result + (this.transformPatternsBitNotBitXNor == null ? 0 : this.transformPatternsBitNotBitXNor.hashCode());
        result = 31 * result + (this.transformPatternsNullOnNullInput == null ? 0 : this.transformPatternsNullOnNullInput.hashCode());
        result = 31 * result + (this.transformPatternsIdempotentFunctionRepetition == null ? 0 : this.transformPatternsIdempotentFunctionRepetition.hashCode());
        result = 31 * result + (this.transformPatternsArithmeticComparisons == null ? 0 : this.transformPatternsArithmeticComparisons.hashCode());
        result = 31 * result + (this.transformPatternsArithmeticExpressions == null ? 0 : this.transformPatternsArithmeticExpressions.hashCode());
        result = 31 * result + (this.transformPatternsTrigonometricFunctions == null ? 0 : this.transformPatternsTrigonometricFunctions.hashCode());
        result = 31 * result + (this.transformPatternsLogarithmicFunctions == null ? 0 : this.transformPatternsLogarithmicFunctions.hashCode());
        result = 31 * result + (this.transformPatternsHyperbolicFunctions == null ? 0 : this.transformPatternsHyperbolicFunctions.hashCode());
        result = 31 * result + (this.transformPatternsInverseHyperbolicFunctions == null ? 0 : this.transformPatternsInverseHyperbolicFunctions.hashCode());
        result = 31 * result + (this.transformInlineBindValuesForFieldComparisons == null ? 0 : this.transformInlineBindValuesForFieldComparisons.hashCode());
        result = 31 * result + (this.transformAnsiJoinToTableLists == null ? 0 : this.transformAnsiJoinToTableLists.hashCode());
        result = 31 * result + (this.transformInConditionSubqueryWithLimitToDerivedTable == null ? 0 : this.transformInConditionSubqueryWithLimitToDerivedTable.hashCode());
        result = 31 * result + (this.transformQualify == null ? 0 : this.transformQualify.hashCode());
        result = 31 * result + (this.transformTableListsToAnsiJoin == null ? 0 : this.transformTableListsToAnsiJoin.hashCode());
        result = 31 * result + (this.transformRownum == null ? 0 : this.transformRownum.hashCode());
        result = 31 * result + (this.transformUnneededArithmeticExpressions == null ? 0 : this.transformUnneededArithmeticExpressions.hashCode());
        result = 31 * result + (this.transformGroupByColumnIndex == null ? 0 : this.transformGroupByColumnIndex.hashCode());
        result = 31 * result + (this.transformInlineCTE == null ? 0 : this.transformInlineCTE.hashCode());
        result = 31 * result + (this.backslashEscaping == null ? 0 : this.backslashEscaping.hashCode());
        result = 31 * result + (this.paramType == null ? 0 : this.paramType.hashCode());
        result = 31 * result + (this.paramCastMode == null ? 0 : this.paramCastMode.hashCode());
        result = 31 * result + (this.statementType == null ? 0 : this.statementType.hashCode());
        result = 31 * result + (this.inlineThreshold == null ? 0 : this.inlineThreshold.hashCode());
        result = 31 * result + (this.transactionListenerStartInvocationOrder == null ? 0 : this.transactionListenerStartInvocationOrder.hashCode());
        result = 31 * result + (this.transactionListenerEndInvocationOrder == null ? 0 : this.transactionListenerEndInvocationOrder.hashCode());
        result = 31 * result + (this.migrationListenerStartInvocationOrder == null ? 0 : this.migrationListenerStartInvocationOrder.hashCode());
        result = 31 * result + (this.migrationListenerEndInvocationOrder == null ? 0 : this.migrationListenerEndInvocationOrder.hashCode());
        result = 31 * result + (this.visitListenerStartInvocationOrder == null ? 0 : this.visitListenerStartInvocationOrder.hashCode());
        result = 31 * result + (this.visitListenerEndInvocationOrder == null ? 0 : this.visitListenerEndInvocationOrder.hashCode());
        result = 31 * result + (this.recordListenerStartInvocationOrder == null ? 0 : this.recordListenerStartInvocationOrder.hashCode());
        result = 31 * result + (this.recordListenerEndInvocationOrder == null ? 0 : this.recordListenerEndInvocationOrder.hashCode());
        result = 31 * result + (this.executeListenerStartInvocationOrder == null ? 0 : this.executeListenerStartInvocationOrder.hashCode());
        result = 31 * result + (this.executeListenerEndInvocationOrder == null ? 0 : this.executeListenerEndInvocationOrder.hashCode());
        result = 31 * result + (this.executeLogging == null ? 0 : this.executeLogging.hashCode());
        result = 31 * result + (this.executeLoggingSQLExceptions == null ? 0 : this.executeLoggingSQLExceptions.hashCode());
        result = 31 * result + (this.diagnosticsLogging == null ? 0 : this.diagnosticsLogging.hashCode());
        result = 31 * result + (this.diagnosticsConnection == null ? 0 : this.diagnosticsConnection.hashCode());
        result = 31 * result + (this.updateRecordVersion == null ? 0 : this.updateRecordVersion.hashCode());
        result = 31 * result + (this.updateRecordTimestamp == null ? 0 : this.updateRecordTimestamp.hashCode());
        result = 31 * result + (this.executeWithOptimisticLocking == null ? 0 : this.executeWithOptimisticLocking.hashCode());
        result = 31 * result + (this.executeWithOptimisticLockingExcludeUnversioned == null ? 0 : this.executeWithOptimisticLockingExcludeUnversioned.hashCode());
        result = 31 * result + (this.attachRecords == null ? 0 : this.attachRecords.hashCode());
        result = 31 * result + (this.insertUnchangedRecords == null ? 0 : this.insertUnchangedRecords.hashCode());
        result = 31 * result + (this.updateUnchangedRecords == null ? 0 : this.updateUnchangedRecords.hashCode());
        result = 31 * result + (this.updatablePrimaryKeys == null ? 0 : this.updatablePrimaryKeys.hashCode());
        result = 31 * result + (this.reflectionCaching == null ? 0 : this.reflectionCaching.hashCode());
        result = 31 * result + (this.cacheRecordMappers == null ? 0 : this.cacheRecordMappers.hashCode());
        result = 31 * result + (this.cacheParsingConnection == null ? 0 : this.cacheParsingConnection.hashCode());
        result = 31 * result + (this.cacheParsingConnectionLRUCacheSize == null ? 0 : this.cacheParsingConnectionLRUCacheSize.hashCode());
        result = 31 * result + (this.cachePreparedStatementInLoader == null ? 0 : this.cachePreparedStatementInLoader.hashCode());
        result = 31 * result + (this.throwExceptions == null ? 0 : this.throwExceptions.hashCode());
        result = 31 * result + (this.fetchWarnings == null ? 0 : this.fetchWarnings.hashCode());
        result = 31 * result + (this.fetchServerOutputSize == null ? 0 : this.fetchServerOutputSize.hashCode());
        result = 31 * result + (this.returnIdentityOnUpdatableRecord == null ? 0 : this.returnIdentityOnUpdatableRecord.hashCode());
        result = 31 * result + (this.returnDefaultOnUpdatableRecord == null ? 0 : this.returnDefaultOnUpdatableRecord.hashCode());
        result = 31 * result + (this.returnComputedOnUpdatableRecord == null ? 0 : this.returnComputedOnUpdatableRecord.hashCode());
        result = 31 * result + (this.returnAllOnUpdatableRecord == null ? 0 : this.returnAllOnUpdatableRecord.hashCode());
        result = 31 * result + (this.returnRecordToPojo == null ? 0 : this.returnRecordToPojo.hashCode());
        result = 31 * result + (this.mapJPAAnnotations == null ? 0 : this.mapJPAAnnotations.hashCode());
        result = 31 * result + (this.mapRecordComponentParameterNames == null ? 0 : this.mapRecordComponentParameterNames.hashCode());
        result = 31 * result + (this.mapConstructorPropertiesParameterNames == null ? 0 : this.mapConstructorPropertiesParameterNames.hashCode());
        result = 31 * result + (this.mapConstructorParameterNames == null ? 0 : this.mapConstructorParameterNames.hashCode());
        result = 31 * result + (this.mapConstructorParameterNamesInKotlin == null ? 0 : this.mapConstructorParameterNamesInKotlin.hashCode());
        result = 31 * result + (this.queryPoolable == null ? 0 : this.queryPoolable.hashCode());
        result = 31 * result + (this.queryTimeout == null ? 0 : this.queryTimeout.hashCode());
        result = 31 * result + (this.maxRows == null ? 0 : this.maxRows.hashCode());
        result = 31 * result + (this.fetchSize == null ? 0 : this.fetchSize.hashCode());
        result = 31 * result + (this.batchSize == null ? 0 : this.batchSize.hashCode());
        result = 31 * result + (this.debugInfoOnStackTrace == null ? 0 : this.debugInfoOnStackTrace.hashCode());
        result = 31 * result + (this.inListPadding == null ? 0 : this.inListPadding.hashCode());
        result = 31 * result + (this.inListPadBase == null ? 0 : this.inListPadBase.hashCode());
        result = 31 * result + (this.delimiter == null ? 0 : this.delimiter.hashCode());
        result = 31 * result + (this.emulateOnDuplicateKeyUpdateOnPrimaryKeyOnly == null ? 0 : this.emulateOnDuplicateKeyUpdateOnPrimaryKeyOnly.hashCode());
        result = 31 * result + (this.emulateMultiset == null ? 0 : this.emulateMultiset.hashCode());
        result = 31 * result + (this.emulateComputedColumns == null ? 0 : this.emulateComputedColumns.hashCode());
        result = 31 * result + (this.executeUpdateWithoutWhere == null ? 0 : this.executeUpdateWithoutWhere.hashCode());
        result = 31 * result + (this.executeDeleteWithoutWhere == null ? 0 : this.executeDeleteWithoutWhere.hashCode());
        result = 31 * result + (this.interpreterDialect == null ? 0 : this.interpreterDialect.hashCode());
        result = 31 * result + (this.interpreterNameLookupCaseSensitivity == null ? 0 : this.interpreterNameLookupCaseSensitivity.hashCode());
        result = 31 * result + (this.interpreterLocale == null ? 0 : this.interpreterLocale.hashCode());
        result = 31 * result + (this.interpreterDelayForeignKeyDeclarations == null ? 0 : this.interpreterDelayForeignKeyDeclarations.hashCode());
        result = 31 * result + (this.metaIncludeSystemIndexes == null ? 0 : this.metaIncludeSystemIndexes.hashCode());
        result = 31 * result + (this.metaIncludeSystemSequences == null ? 0 : this.metaIncludeSystemSequences.hashCode());
        result = 31 * result + (this.migrationAllowsUndo == null ? 0 : this.migrationAllowsUndo.hashCode());
        result = 31 * result + (this.migrationRevertUntracked == null ? 0 : this.migrationRevertUntracked.hashCode());
        result = 31 * result + (this.migrationAutoBaseline == null ? 0 : this.migrationAutoBaseline.hashCode());
        result = 31 * result + (this.migrationAutoValidation == null ? 0 : this.migrationAutoValidation.hashCode());
        result = 31 * result + (this.migrationIgnoreDefaultTimestampPrecisionDiffs == null ? 0 : this.migrationIgnoreDefaultTimestampPrecisionDiffs.hashCode());
        result = 31 * result + (this.locale == null ? 0 : this.locale.hashCode());
        result = 31 * result + (this.parseDialect == null ? 0 : this.parseDialect.hashCode());
        result = 31 * result + (this.parseLocale == null ? 0 : this.parseLocale.hashCode());
        result = 31 * result + (this.parseDateFormat == null ? 0 : this.parseDateFormat.hashCode());
        result = 31 * result + (this.parseTimestampFormat == null ? 0 : this.parseTimestampFormat.hashCode());
        result = 31 * result + (this.parseNamedParamPrefix == null ? 0 : this.parseNamedParamPrefix.hashCode());
        result = 31 * result + (this.parseNameCase == null ? 0 : this.parseNameCase.hashCode());
        result = 31 * result + (this.parseWithMetaLookups == null ? 0 : this.parseWithMetaLookups.hashCode());
        result = 31 * result + (this.parseAppendMissingTableReferences == null ? 0 : this.parseAppendMissingTableReferences.hashCode());
        result = 31 * result + (this.parseSetCommands == null ? 0 : this.parseSetCommands.hashCode());
        result = 31 * result + (this.parseUnsupportedSyntax == null ? 0 : this.parseUnsupportedSyntax.hashCode());
        result = 31 * result + (this.parseUnknownFunctions == null ? 0 : this.parseUnknownFunctions.hashCode());
        result = 31 * result + (this.parseIgnoreCommercialOnlyFeatures == null ? 0 : this.parseIgnoreCommercialOnlyFeatures.hashCode());
        result = 31 * result + (this.parseIgnoreComments == null ? 0 : this.parseIgnoreComments.hashCode());
        result = 31 * result + (this.parseIgnoreCommentStart == null ? 0 : this.parseIgnoreCommentStart.hashCode());
        result = 31 * result + (this.parseIgnoreCommentStop == null ? 0 : this.parseIgnoreCommentStop.hashCode());
        result = 31 * result + (this.parseRetainCommentsBetweenQueries == null ? 0 : this.parseRetainCommentsBetweenQueries.hashCode());
        result = 31 * result + (this.parseMetaDefaultExpressions == null ? 0 : this.parseMetaDefaultExpressions.hashCode());
        result = 31 * result + (this.readonlyTableRecordInsert == null ? 0 : this.readonlyTableRecordInsert.hashCode());
        result = 31 * result + (this.readonlyUpdatableRecordUpdate == null ? 0 : this.readonlyUpdatableRecordUpdate.hashCode());
        result = 31 * result + (this.readonlyInsert == null ? 0 : this.readonlyInsert.hashCode());
        result = 31 * result + (this.readonlyUpdate == null ? 0 : this.readonlyUpdate.hashCode());
        result = 31 * result + (this.applyWorkaroundFor7962 == null ? 0 : this.applyWorkaroundFor7962.hashCode());
        result = 31 * result + (this.interpreterSearchPath == null ? 0 : this.interpreterSearchPath.hashCode());
        result = 31 * result + (this.migrationSchemata == null ? 0 : this.migrationSchemata.hashCode());
        result = 31 * result + (this.parseSearchPath == null ? 0 : this.parseSearchPath.hashCode());
        return result;
    }
}

