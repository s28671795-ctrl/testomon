/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.BiFunction;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jooq.AggregateFilterStep;
import org.jooq.AggregateFunction;
import org.jooq.AlterDatabaseStep;
import org.jooq.AlterDomainDropConstraintCascadeStep;
import org.jooq.AlterDomainRenameConstraintStep;
import org.jooq.AlterDomainStep;
import org.jooq.AlterIndexOnStep;
import org.jooq.AlterSchemaStep;
import org.jooq.AlterSequenceFlagsStep;
import org.jooq.AlterSequenceStep;
import org.jooq.AlterTableAddStep;
import org.jooq.AlterTableDropStep;
import org.jooq.AlterTableStep;
import org.jooq.AlterTypeStep;
import org.jooq.ArrayAggOrderByStep;
import org.jooq.Block;
import org.jooq.CaseConditionStep;
import org.jooq.CaseValueStep;
import org.jooq.CaseWhenStep;
import org.jooq.Catalog;
import org.jooq.CharacterSet;
import org.jooq.Collation;
import org.jooq.Comment;
import org.jooq.CommentOnFinalStep;
import org.jooq.CommentOnIsStep;
import org.jooq.CommonTableExpression;
import org.jooq.Comparator;
import org.jooq.Condition;
import org.jooq.Constraint;
import org.jooq.ConstraintEnforcementStep;
import org.jooq.ConstraintForeignKeyOnStep;
import org.jooq.ConstraintTypeStep;
import org.jooq.Context;
import org.jooq.CreateDomainConstraintStep;
import org.jooq.CreateDomainDefaultStep;
import org.jooq.CreateFunctionCharacteristicsStep;
import org.jooq.CreateFunctionParametersStep;
import org.jooq.CreateIndexIncludeStep;
import org.jooq.CreateIndexStep;
import org.jooq.CreateIndexWhereStep;
import org.jooq.CreateProcedureCharacteristicsStep;
import org.jooq.CreateProcedureParametersStep;
import org.jooq.CreateSequenceFlagsStep;
import org.jooq.CreateTableCommentStep;
import org.jooq.CreateTableElementListStep;
import org.jooq.CreateTableOnCommitStep;
import org.jooq.CreateTableStorageStep;
import org.jooq.CreateTableWithDataStep;
import org.jooq.CreateTriggerEventOrStep;
import org.jooq.CreateTriggerEventStep;
import org.jooq.CreateTriggerReferencingStep;
import org.jooq.CreateTriggerWhenStep;
import org.jooq.DDLQuery;
import org.jooq.DSLContext;
import org.jooq.DataType;
import org.jooq.DatePart;
import org.jooq.Declaration;
import org.jooq.Delete;
import org.jooq.DeleteLimitStep;
import org.jooq.DeleteReturningStep;
import org.jooq.DeleteUsingStep;
import org.jooq.DeleteWhereStep;
import org.jooq.DerivedColumnList;
import org.jooq.Domain;
import org.jooq.DropDomainCascadeStep;
import org.jooq.DropIndexCascadeStep;
import org.jooq.DropIndexOnStep;
import org.jooq.DropSchemaStep;
import org.jooq.DropTableStep;
import org.jooq.DropTypeStep;
import org.jooq.Field;
import org.jooq.FieldOrRow;
import org.jooq.ForByStep;
import org.jooq.ForInStep;
import org.jooq.Function1;
import org.jooq.Function2;
import org.jooq.Function3;
import org.jooq.Function4;
import org.jooq.Geometry;
import org.jooq.GrantOnStep;
import org.jooq.GrantToStep;
import org.jooq.GrantWithGrantOptionStep;
import org.jooq.GroupConcatOrderByStep;
import org.jooq.GroupConcatSeparatorStep;
import org.jooq.GroupField;
import org.jooq.IfElseStep;
import org.jooq.Index;
import org.jooq.Insert;
import org.jooq.InsertOnConflictDoUpdateStep;
import org.jooq.InsertOnDuplicateSetMoreStep;
import org.jooq.InsertOnDuplicateStep;
import org.jooq.InsertReturningStep;
import org.jooq.InsertSetStep;
import org.jooq.InsertValuesStepN;
import org.jooq.JSON;
import org.jooq.JSONArrayAggNullStep;
import org.jooq.JSONArrayAggOrderByStep;
import org.jooq.JSONArrayAggReturningStep;
import org.jooq.JSONArrayNullStep;
import org.jooq.JSONArrayReturningStep;
import org.jooq.JSONEntry;
import org.jooq.JSONObjectAggNullStep;
import org.jooq.JSONObjectAggReturningStep;
import org.jooq.JSONObjectNullStep;
import org.jooq.JSONObjectReturningStep;
import org.jooq.JSONTableColumnsStep;
import org.jooq.JSONValueDefaultStep;
import org.jooq.JSONValueOnStep;
import org.jooq.JoinType;
import org.jooq.Keyword;
import org.jooq.Label;
import org.jooq.LanguageContext;
import org.jooq.LikeEscapeStep;
import org.jooq.LoopStep;
import org.jooq.Merge;
import org.jooq.MergeMatchedStep;
import org.jooq.MergeUsingStep;
import org.jooq.Meta;
import org.jooq.Name;
import org.jooq.OrderedAggregateFunction;
import org.jooq.OrderedAggregateFunctionOfDeferredType;
import org.jooq.Param;
import org.jooq.ParamMode;
import org.jooq.Parameter;
import org.jooq.ParseContext;
import org.jooq.ParseListener;
import org.jooq.Period;
import org.jooq.PeriodSpecification;
import org.jooq.Privilege;
import org.jooq.Pro;
import org.jooq.QualifiedAsterisk;
import org.jooq.Queries;
import org.jooq.Query;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.Result;
import org.jooq.ResultQuery;
import org.jooq.RevokeFromStep;
import org.jooq.RevokeOnStep;
import org.jooq.Row;
import org.jooq.Row2;
import org.jooq.SQL;
import org.jooq.SQLDialect;
import org.jooq.SQLDialectCategory;
import org.jooq.Schema;
import org.jooq.Select;
import org.jooq.SelectField;
import org.jooq.SelectFieldOrAsterisk;
import org.jooq.Sequence;
import org.jooq.SortField;
import org.jooq.SortOrder;
import org.jooq.Statement;
import org.jooq.Table;
import org.jooq.TableElement;
import org.jooq.TableField;
import org.jooq.TableLike;
import org.jooq.TableOnStep;
import org.jooq.TableOptionalOnStep;
import org.jooq.TableOuterJoinStep;
import org.jooq.TablePartitionByStep;
import org.jooq.Truncate;
import org.jooq.TruncateCascadeStep;
import org.jooq.TruncateIdentityStep;
import org.jooq.Update;
import org.jooq.UpdateFromStep;
import org.jooq.UpdateLimitStep;
import org.jooq.UpdateReturningStep;
import org.jooq.UpdateSetFirstStep;
import org.jooq.UpdateWhereStep;
import org.jooq.User;
import org.jooq.Variable;
import org.jooq.VersionsBetweenAndStep;
import org.jooq.VisitListener;
import org.jooq.WindowBeforeOverStep;
import org.jooq.WindowDefinition;
import org.jooq.WindowFinalStep;
import org.jooq.WindowFromFirstLastStep;
import org.jooq.WindowIgnoreNullsStep;
import org.jooq.WindowOverStep;
import org.jooq.WindowSpecification;
import org.jooq.WindowSpecificationExcludeStep;
import org.jooq.WindowSpecificationFinalStep;
import org.jooq.WindowSpecificationOrderByStep;
import org.jooq.WindowSpecificationRowsAndStep;
import org.jooq.WindowSpecificationRowsStep;
import org.jooq.WithStep;
import org.jooq.XML;
import org.jooq.XMLAggOrderByStep;
import org.jooq.XMLAttributes;
import org.jooq.XMLTableColumnPathStep;
import org.jooq.XMLTableColumnsStep;
import org.jooq.XMLTablePassingStep;
import org.jooq.conf.ParseSearchSchema;
import org.jooq.conf.ParseUnknownFunctions;
import org.jooq.conf.ParseUnsupportedSyntax;
import org.jooq.conf.ParseWithMetaLookups;
import org.jooq.conf.RenderKeywordCase;
import org.jooq.conf.RenderNameCase;
import org.jooq.conf.RenderQuotedNames;
import org.jooq.conf.SettingsTools;
import org.jooq.impl.AbstractDDLQuery;
import org.jooq.impl.AbstractField;
import org.jooq.impl.AbstractName;
import org.jooq.impl.AbstractRoutine;
import org.jooq.impl.AbstractRow;
import org.jooq.impl.AbstractScope;
import org.jooq.impl.BlockImpl;
import org.jooq.impl.CombineOperator;
import org.jooq.impl.CurrentDate;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultDataType;
import org.jooq.impl.DeleteQueryImpl;
import org.jooq.impl.FieldProxy;
import org.jooq.impl.InsertImpl;
import org.jooq.impl.Internal;
import org.jooq.impl.JSONExists;
import org.jooq.impl.JSONValue;
import org.jooq.impl.JoinTable;
import org.jooq.impl.Keywords;
import org.jooq.impl.Lazy;
import org.jooq.impl.NullStatement;
import org.jooq.impl.ParseListeners;
import org.jooq.impl.ParserException;
import org.jooq.impl.QOM;
import org.jooq.impl.QualifiedAsteriskImpl;
import org.jooq.impl.QualifiedAsteriskProxy;
import org.jooq.impl.QueryPartList;
import org.jooq.impl.RowAssignment;
import org.jooq.impl.RowBetweenCondition;
import org.jooq.impl.RowInCondition;
import org.jooq.impl.RowIsDistinctFrom;
import org.jooq.impl.RowSubqueryCondition;
import org.jooq.impl.SQLConcatenationImpl;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.ScopeStack;
import org.jooq.impl.SelectQueryImpl;
import org.jooq.impl.TableFieldImpl;
import org.jooq.impl.Tools;
import org.jooq.impl.Transformations;
import org.jooq.impl.UpdateImpl;
import org.jooq.impl.UpdateQueryImpl;
import org.jooq.impl.Val;
import org.jooq.impl.VariableImpl;
import org.jooq.impl.WithImpl;
import org.jooq.tools.StringUtils;
import org.jooq.tools.reflect.Reflect;
import org.jooq.types.DayToSecond;
import org.jooq.types.Interval;
import org.jooq.types.YearToMonth;
import org.jooq.types.YearToSecond;

final class DefaultParseContext
extends AbstractScope
implements ParseContext {
    @Pro
    private static final Set<SQLDialect> SUPPORTS_TSQL_ALIASING_SYNTAX = SQLDialect.supportedBy(SQLDialect.ASE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER);
    @Pro
    private static final Set<SQLDialect> SUPPORTS_TSQL_QUOTING_SYNTAX = SQLDialect.supportedBy(SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.SYBASE);
    static final Set<SQLDialect> SUPPORTS_HASH_COMMENT_SYNTAX = SQLDialect.supportedBy(SQLDialect.AURORA_MYSQL, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL);
    private static final Pattern P_SEARCH_PATH = Pattern.compile("(?i:select\\s+(pg_catalog\\s*\\.\\s*)?set_config\\s*\\(\\s*'search_path'\\s*,\\s*'([^']*)'\\s*,\\s*\\w+\\s*\\))");
    private static final Set<String> ALTER_KEYWORDS = new HashSet<String>(Arrays.asList("ADD", "ALTER", "COMMENT", "DROP", "MODIFY", "RENAME"));
    private static final String[] KEYWORDS_IN_STATEMENTS = new String[]{"ALTER", "BEGIN", "COMMENT", "CREATE", "DECLARE", "DELETE", "DROP", "END", "GO", "GRANT", "INSERT", "MERGE", "RENAME", "REVOKE", "SELECT", "SET", "TRUNCATE", "UPDATE", "USE", "VALUES", "WITH"};
    private static final String[] KEYWORDS_IN_SELECT = new String[]{"CONNECT BY", "EXCEPT", "FETCH FIRST", "FETCH NEXT", "FOR JSON", "FOR KEY SHARE", "FOR NO KEY UPDATE", "FOR SHARE", "FOR UPDATE", "FOR XML", "FROM", "GROUP BY", "HAVING", "INTERSECT", "INTO", "LIMIT", "MINUS", "OFFSET", "ON", "ORDER BY", "PARTITION BY", "QUALIFY", "RETURNING", "ROWS", "START WITH", "UNION", "WHERE", "WINDOW"};
    private static final String[] KEYWORDS_IN_FROM = new String[]{"CROSS APPLY", "CROSS JOIN", "FULL JOIN", "FULL OUTER JOIN", "INNER JOIN", "JOIN", "LEFT ANTI JOIN", "LEFT JOIN", "LEFT OUTER JOIN", "LEFT SEMI JOIN", "NATURAL FULL JOIN", "NATURAL FULL OUTER JOIN", "NATURAL INNER JOIN", "NATURAL JOIN", "NATURAL LEFT JOIN", "NATURAL LEFT OUTER JOIN", "NATURAL RIGHT JOIN", "NATURAL RIGHT OUTER JOIN", "ON", "OUTER APPLY", "PARTITION BY", "RIGHT ANTI JOIN", "RIGHT JOIN", "RIGHT OUTER JOIN", "RIGHT SEMI JOIN", "STRAIGHT_JOIN", "USING"};
    private static final String[] KEYWORDS_IN_SELECT_FROM;
    private static final String[] KEYWORDS_IN_UPDATE_FROM;
    private static final String[] KEYWORDS_IN_DELETE_FROM;
    private static final String[] PIVOT_KEYWORDS;
    private static final Lazy<DDLQuery> IGNORE;
    private static final Lazy<Query> IGNORE_NO_DELIMITER;
    private final DSLContext dsl;
    private final Locale locale;
    private final Meta meta;
    private char[] sql;
    private final ParseWithMetaLookups metaLookups;
    private boolean metaLookupsForceIgnore;
    private final Consumer<Param<?>> bindParamListener;
    private int positionBeforeWhitespace;
    private int position = 0;
    private boolean ignoreHints = true;
    private final Object[] bindings;
    private int bindIndex = 0;
    private final Map<String, Param<?>> bindParams = new LinkedHashMap();
    private String delimiter = ";";
    private LanguageContext languageContext = LanguageContext.QUERY;
    private EnumSet<FunctionKeyword> forbidden = EnumSet.noneOf(FunctionKeyword.class);
    private ParseScope scope = new ParseScope();
    @Pro
    private final ParseListener listener;

    final Queries parse() {
        return this.wrap(() -> {
            Query query;
            ArrayList<Query> result = new ArrayList<Query>();
            int p = this.positionBeforeWhitespace;
            do {
                this.parseDelimiterSpecifications();
                while (this.parseDelimiterIf(false)) {
                    p = this.positionBeforeWhitespace;
                }
                this.retainComments(result, p);
                query = this.patchParsedQuery(this.parseQuery(false, false));
                if (query == IGNORE.get() || query == IGNORE_NO_DELIMITER.get() || query == null) continue;
                result.add(query);
            } while (this.parseDelimiterIf(true) && (p = this.positionBeforeWhitespace) >= 0 && !this.done());
            if (query != null) {
                this.retainComments(result, p);
            }
            return this.done("Unexpected token or missing query delimiter", this.dsl.queries(result));
        });
    }

    private final void retainComments(List<Query> result, int p) {
        if (Boolean.TRUE.equals(this.settings().isParseRetainCommentsBetweenQueries()) && p < this.position) {
            for (int i = p; i < this.position; ++i) {
                if (this.character(i) == ' ') continue;
                result.add(new IgnoreQuery(this.substring(p, this.position)));
                break;
            }
        }
    }

    private final Query patchParsedQuery(Query query) {
        String string;
        Matcher matcher;
        if (Boolean.TRUE.equals(this.configuration().data("org.jooq.ddl.parse-for-ddldatabase")) && query instanceof Select && (matcher = P_SEARCH_PATH.matcher(string = this.configuration().deriveSettings(s2 -> s2.withRenderFormatted(false).withRenderKeywordCase(RenderKeywordCase.LOWER).withRenderNameCase(RenderNameCase.LOWER).withRenderQuotedNames(RenderQuotedNames.NEVER).withRenderSchema(false)).dsl().render(query))).find()) {
            String schema = matcher.group(2);
            if (!StringUtils.isBlank(schema)) {
                return this.configuration().dsl().setSchema(schema);
            }
            return IGNORE.get();
        }
        return query;
    }

    final Query parseQuery0() {
        return this.wrap(() -> this.done("Unexpected clause", this.parseQuery(false, false)));
    }

    final Statement parseStatement0() {
        return this.wrap(() -> this.done("Unexpected content", this.parseStatementAndSemicolonIf()));
    }

    @Pro
    final Block parseStatements0() {
        return this.wrap(() -> {
            this.scope.proceduralScope.scopeStart();
            List<Statement> result = this.parseStatementsAndPeek(new String[0]);
            this.scope.proceduralScope.scopeEnd();
            return this.done("Unexpected content", result.size() == 1 && result.get(0) instanceof Block ? (Block)result.get(0) : DSL.statements(result));
        });
    }

    final ResultQuery<?> parseResultQuery0() {
        return this.wrap(() -> this.done("Unexpected content after end of query input", (ResultQuery)this.parseQuery(true, false)));
    }

    final Select<?> parseSelect0() {
        return this.wrap(() -> this.done("Unexpected content after end of query input", (Select)this.parseQuery(true, true)));
    }

    final Table<?> parseTable0() {
        return this.wrap(() -> this.done("Unexpected content after end of table input", this.parseTable()));
    }

    final Field<?> parseField0() {
        return this.wrap(() -> this.done("Unexpected content after end of field input", this.parseField()));
    }

    final Row parseRow0() {
        return this.wrap(() -> this.done("Unexpected content after end of row input", this.parseRow()));
    }

    final Condition parseCondition0() {
        return this.wrap(() -> this.done("Unexpected content after end of condition input", this.parseCondition()));
    }

    final Name parseName0() {
        return this.wrap(() -> this.done("Unexpected content after end of name input", this.parseName()));
    }

    @Pro
    final List<Parameter> parseParameters() {
        ArrayList<Parameter> result = new ArrayList<Parameter>();
        this.parseKeyword("CREATE");
        if (this.parseKeywordIf("PROCEDURE") || this.parseKeywordIf("PROC") || this.parseKeywordIf("FUNCTION")) {
            this.parseName();
            boolean parens = this.parseIf('(');
            while (this.parseIf('@')) {
                Name name = this.parseIdentifier();
                this.parseKeywordIf("AS");
                DataType<?> type = this.parseDataType();
                Field<?> defaultValue = null;
                if (this.parseIf('=')) {
                    defaultValue = this.parseField();
                }
                this.parseKeywordIf("VARYING");
                if (!this.parseKeywordIf("OUTPUT") && !this.parseKeywordIf("OUT")) {
                    this.parseKeywordIf("READONLY");
                }
                result.add(AbstractRoutine.createParameter(name.last(), type, defaultValue != null, false));
                if (this.parseIf(',')) continue;
            }
            if (parens) {
                this.parse(')');
            }
        } else {
            throw this.expected("FUNCTION", "PROCEDURE");
        }
        this.parseKeyword("RETURNS", "WITH", "FOR REPLICATION", "AS");
        return result;
    }

    private final void parseDelimiterSpecifications() {
        while (this.parseKeywordIf("DELIMITER")) {
            this.delimiter(this.parseUntilEOL().trim());
        }
    }

    private final boolean parseDelimiterIf(boolean optional) {
        if (this.parseIf(this.delimiter())) {
            return true;
        }
        if (this.peekKeyword("GO")) {
            this.positionInc(2);
            String line = this.parseUntilEOLIf();
            if (line != null && !"".equals(line.trim())) {
                throw this.exception("GO must be only token on line");
            }
            this.parseWhitespaceIf();
            return true;
        }
        return optional;
    }

    private final Query parseQuery(boolean parseResultQuery, boolean parseSelect) {
        if (this.done()) {
            return null;
        }
        this.scope.scopeStart();
        boolean previousMetaLookupsForceIgnore = this.metaLookupsForceIgnore();
        Query result = null;
        LanguageContext previous = this.languageContext;
        try {
            this.languageContext = LanguageContext.QUERY;
            switch (this.characterUpper()) {
                case 'A': {
                    if (parseResultQuery || !this.peekKeyword("ALTER")) break;
                    result = this.metaLookupsForceIgnore(true).parseAlter();
                    Query query = result;
                    return query;
                }
                case 'B': {
                    if (!parseResultQuery && this.peekKeyword("BEGIN WORK", "BEGIN TRANSACTION", "BEGIN TRAN")) {
                        result = this.parseStartTransaction();
                        Query query = result;
                        return query;
                    }
                    if (!parseResultQuery && this.parseKeywordIf("BT")) {
                        Query query = this.dsl.startTransaction();
                        return query;
                    }
                    if (parseResultQuery || !this.peekKeyword("BEGIN")) break;
                    this.languageContext = previous;
                    result = this.parseBlock(false);
                    Block block = result;
                    return block;
                }
                case 'C': {
                    if (!parseResultQuery && this.peekKeyword("CREATE")) {
                        result = this.metaLookupsForceIgnore(true).parseCreate();
                        DDLQuery dDLQuery = result;
                        return dDLQuery;
                    }
                    if (!parseResultQuery && this.peekKeyword("COMMENT ON")) {
                        result = this.metaLookupsForceIgnore(true).parseCommentOn();
                        DDLQuery dDLQuery = result;
                        return dDLQuery;
                    }
                    if (!parseResultQuery && this.parseKeywordIf("CT")) {
                        result = this.metaLookupsForceIgnore(true).parseCreateTable(false);
                        DDLQuery dDLQuery = result;
                        return dDLQuery;
                    }
                    if (!parseResultQuery && this.parseKeywordIf("CV")) {
                        result = this.metaLookupsForceIgnore(true).parseCreateView(false);
                        DDLQuery dDLQuery = result;
                        return dDLQuery;
                    }
                    if (!this.ignoreProEdition() && this.peekKeyword("CALL") && this.requireProEdition()) {
                        result = this.parseCall();
                        Query query = result;
                        return query;
                    }
                    if (!parseResultQuery && this.peekKeyword("COMMIT")) {
                        result = this.parseCommit();
                        Query query = result;
                        return query;
                    }
                    if (!this.parseKeywordIf("CONNECT")) break;
                    throw this.notImplemented("CONNECT");
                }
                case 'D': {
                    if (!parseResultQuery && !this.ignoreProEdition() && this.peekKeyword("DECLARE") && this.requireProEdition()) {
                        result = this.parseBlock(true);
                        Block block = result;
                        return block;
                    }
                    if (!parseSelect && this.peekKeyword("DELETE", "DEL")) {
                        result = this.parseDelete(null, parseResultQuery);
                        Query query = result;
                        return query;
                    }
                    if (!parseResultQuery && this.peekKeyword("DROP")) {
                        result = this.metaLookupsForceIgnore(true).parseDrop();
                        DDLQuery dDLQuery = result;
                        return dDLQuery;
                    }
                    if (parseResultQuery || !this.peekKeyword("DO")) break;
                    result = this.parseDo();
                    Block block = result;
                    return block;
                }
                case 'E': {
                    if (!parseResultQuery && this.peekKeyword("EXECUTE BLOCK AS")) {
                        result = this.parseBlock(true);
                        Block block = result;
                        return block;
                    }
                    if (!parseResultQuery && this.peekKeyword("EXEC")) {
                        result = this.parseExec();
                        Query query = result;
                        return query;
                    }
                    if (!this.ignoreProEdition() && this.peekKeyword("EXECUTE PROCEDURE") && this.requireProEdition()) {
                        result = this.parseCall();
                        Query query = result;
                        return query;
                    }
                    if (parseResultQuery || !this.parseKeywordIf("ET", "END TRANSACTION")) break;
                    Query query = this.dsl.commit();
                    return query;
                }
                case 'G': {
                    if (parseResultQuery || !this.peekKeyword("GRANT")) break;
                    result = this.metaLookupsForceIgnore(true).parseGrant();
                    DDLQuery dDLQuery = result;
                    return dDLQuery;
                }
                case 'I': {
                    if (parseSelect || !this.peekKeyword("INSERT", "INS")) break;
                    result = this.parseInsert(null, parseResultQuery);
                    Query query = result;
                    return query;
                }
                case 'L': {
                    if (!this.parseKeywordIf("LOAD")) break;
                    throw this.notImplemented("LOAD");
                }
                case 'M': {
                    if (parseResultQuery || !this.peekKeyword("MERGE")) break;
                    result = this.parseMerge(null);
                    Merge<?> merge = result;
                    return merge;
                }
                case 'O': {
                    if (parseResultQuery || !this.peekKeyword("OPEN")) break;
                    result = this.parseOpen();
                    Query query = result;
                    return query;
                }
                case 'R': {
                    if (!parseResultQuery && this.peekKeyword("RENAME")) {
                        result = this.metaLookupsForceIgnore(true).parseRename();
                        DDLQuery dDLQuery = result;
                        return dDLQuery;
                    }
                    if (!parseResultQuery && this.peekKeyword("REVOKE")) {
                        result = this.metaLookupsForceIgnore(true).parseRevoke();
                        DDLQuery dDLQuery = result;
                        return dDLQuery;
                    }
                    if (this.parseKeywordIf("REPLACE")) {
                        throw this.notImplemented("REPLACE");
                    }
                    if (!parseResultQuery && this.peekKeyword("RELEASE")) {
                        result = this.parseReleaseSavepoint();
                        Query query = result;
                        return query;
                    }
                    if (parseResultQuery || !this.peekKeyword("ROLLBACK")) break;
                    result = this.parseRollback();
                    Query query = result;
                    return query;
                }
                case 'S': {
                    if (this.peekSelect(false)) {
                        SelectQueryImpl<Record> selectQueryImpl = result = this.parseSelect();
                        return selectQueryImpl;
                    }
                    if (!parseResultQuery && this.peekKeyword("SET")) {
                        result = this.parseSet();
                        Query query = result;
                        return query;
                    }
                    if (!parseResultQuery && this.peekKeyword("SAVE", "SAVEPOINT")) {
                        result = this.parseSavepoint();
                        Query query = result;
                        return query;
                    }
                    if (parseResultQuery || !this.peekKeyword("START")) break;
                    result = this.parseStartTransaction();
                    Query query = result;
                    return query;
                }
                case 'T': {
                    if (!parseSelect && this.peekKeyword("TABLE")) {
                        SelectQueryImpl<Record> selectQueryImpl = result = this.parseSelect();
                        return selectQueryImpl;
                    }
                    if (parseResultQuery || !this.peekKeyword("TRUNCATE")) break;
                    result = this.parseTruncate();
                    Truncate<?> truncate = result;
                    return truncate;
                }
                case 'U': {
                    if (!parseSelect && this.peekKeyword("UPDATE", "UPD")) {
                        result = this.parseUpdate(null, parseResultQuery);
                        Query query = result;
                        return query;
                    }
                    if (!parseResultQuery && this.peekKeyword("USE")) {
                        result = this.parseUse();
                        Query query = result;
                        return query;
                    }
                    if (!this.parseKeywordIf("UPSERT")) break;
                    throw this.notImplemented("UPSERT");
                }
                case 'V': {
                    if (!parseSelect && this.peekKeyword("VALUES")) {
                        SelectQueryImpl<Record> selectQueryImpl = result = this.parseSelect();
                        return selectQueryImpl;
                    }
                }
                case 'W': {
                    if (!this.peekKeyword("WITH")) break;
                    result = this.parseWith(parseSelect);
                    Query query = result;
                    return query;
                }
                case '(': {
                    if (this.peekKeyword("WITH", false, true, false)) {
                        result = this.parseWith(true);
                        Query query = result;
                        return query;
                    }
                    SelectQueryImpl<Record> selectQueryImpl = result = this.parseSelect();
                    return selectQueryImpl;
                }
                case '{': {
                    if (this.ignoreProEdition() || !this.peekKeyword("{ CALL") || !this.requireProEdition()) break;
                    result = this.parseParenthesised('{', (? super ParseContext c) -> this.parseCall(), '}');
                    Query query = result;
                    return query;
                }
            }
            try {
                throw this.exception("Unsupported query type");
            }
            catch (ParserException e) {
                this.scope.scopeClear();
                throw e;
            }
        }
        finally {
            this.scope.scopeEnd(result);
            this.scope.scopeResolve();
            this.metaLookupsForceIgnore(previousMetaLookupsForceIgnore);
            this.languageContext = previous;
        }
    }

    private final Query parseWith(boolean parseSelect) {
        return this.parseWith(parseSelect, null);
    }

    private final Query parseWith(boolean parseSelect, Integer degree) {
        Query result;
        int parens = 0;
        while (this.parseIf('(')) {
            ++parens;
        }
        this.parseKeyword("WITH");
        boolean recursive = this.parseKeywordIf("RECURSIVE");
        ArrayList cte = new ArrayList();
        do {
            if (this.parseKeywordIf("FUNCTION")) {
                throw this.notImplemented("WITH FUNCTION");
            }
            Name name = this.parseIdentifier();
            DerivedColumnList dcl = null;
            if (this.parseIf('(')) {
                List<Name> columnNames = this.parseIdentifiers();
                this.parse(')');
                dcl = name.fields(columnNames.toArray(Tools.EMPTY_NAME));
            }
            this.parseKeyword("AS");
            boolean materialized = this.parseKeywordIf("MATERIALIZED");
            boolean notMaterialized = !materialized && this.parseKeywordIf("NOT MATERIALIZED");
            this.parse('(');
            ResultQuery resultQuery = (ResultQuery)this.parseQuery(true, false);
            this.parse(')');
            cte.add(dcl != null ? (materialized ? dcl.asMaterialized(resultQuery) : (notMaterialized ? dcl.asNotMaterialized(resultQuery) : dcl.as(resultQuery))) : (materialized ? name.asMaterialized(resultQuery) : (notMaterialized ? name.asNotMaterialized(resultQuery) : name.as(resultQuery))));
        } while (this.parseIf(','));
        WithStep with = new WithImpl(this.dsl.configuration(), recursive).with((CommonTableExpression[])cte.toArray(Tools.EMPTY_COMMON_TABLE_EXPRESSION));
        if (!parseSelect && this.peekKeyword("DELETE", "DEL")) {
            result = this.parseDelete((WithImpl)with, false);
        } else if (!parseSelect && this.peekKeyword("INSERT", "INS")) {
            result = this.parseInsert((WithImpl)with, false);
        } else if (!parseSelect && this.peekKeyword("MERGE")) {
            result = this.parseMerge((WithImpl)with);
        } else if (this.peekSelect(true)) {
            result = this.parseSelect(degree, (WithImpl)with);
        } else if (!parseSelect && this.peekKeyword("UPDATE", "UPD")) {
            result = this.parseUpdate((WithImpl)with, false);
        } else {
            if (!this.parseWhitespaceIf()) {
                // empty if block
            }
            if (this.done()) {
                throw this.exception("Missing statement after WITH");
            }
            throw this.exception("Unsupported statement after WITH");
        }
        while (parens-- > 0) {
            this.parse(')');
        }
        return result;
    }

    private final Field<?> parseScalarSubqueryIf() {
        int p = this.position();
        try {
            if (this.peekSelectOrWith(true)) {
                this.parse('(');
                SelectQueryImpl<Record> select = this.parseWithOrSelect();
                this.parse(')');
                if (Tools.degree(select) != 1) {
                    throw this.exception("Select list must contain exactly one column");
                }
                return DSL.field(select);
            }
        }
        catch (ParserException e) {
            if (e.getMessage().contains("Token ')' expected")) {
                this.position(p);
            }
            throw e;
        }
        return null;
    }

    private final SelectQueryImpl<Record> parseWithOrSelect() {
        return this.parseWithOrSelect(null);
    }

    private final SelectQueryImpl<Record> parseWithOrSelect(Integer degree) {
        return this.peekKeyword("WITH") ? (SelectQueryImpl)this.parseWith(true, degree) : this.parseSelect(degree, null);
    }

    private final SelectQueryImpl<Record> parseSelect() {
        return this.parseSelect(null, null);
    }

    /*
     * Enabled aggressive block sorting
     */
    private final SelectQueryImpl<Record> parseSelect(Integer degree, WithImpl with) {
        SelectQueryImpl<Record> result;
        block47: {
            String name;
            block46: {
                block45: {
                    block57: {
                        block56: {
                            block49: {
                                boolean jsonb;
                                block53: {
                                    block55: {
                                        block54: {
                                            block52: {
                                                block51: {
                                                    block50: {
                                                        block48: {
                                                            this.scope.scopeStart();
                                                            result = this.parseQueryExpressionBody(degree, with, null);
                                                            List<SortField> orderBy = null;
                                                            for (Field<?> field2 : result.getSelect()) {
                                                                if (Tools.aliased(field2) == null) continue;
                                                                this.scope.scope(field2);
                                                            }
                                                            if (this.parseKeywordIf("ORDER")) {
                                                                if (!this.ignoreProEdition() && this.parseKeywordIf("SIBLINGS BY") && this.requireProEdition()) {
                                                                    result.addOrderBy(this.parseList(',', (? super ParseContext c) -> c.parseSortField()));
                                                                    result.setOrderBySiblings(true);
                                                                } else {
                                                                    if (!this.parseKeywordIf("BY")) {
                                                                        throw this.expected("SIBLINGS BY", "BY");
                                                                    }
                                                                    orderBy = this.parseList(',', (? super ParseContext c) -> c.parseSortField());
                                                                    result.addOrderBy(orderBy);
                                                                }
                                                            }
                                                            if (orderBy != null && this.parseKeywordIf("SEEK")) {
                                                                List<Field> seek;
                                                                boolean before = this.parseKeywordIf("BEFORE");
                                                                if (!before) {
                                                                    this.parseKeywordIf("AFTER");
                                                                }
                                                                if ((seek = this.parseList(',', (? super ParseContext c) -> c.parseField())).size() != orderBy.size()) {
                                                                    throw this.exception("ORDER BY size (" + orderBy.size() + ") and SEEK size (" + seek.size() + ") must match");
                                                                }
                                                                if (before) {
                                                                    result.addSeekBefore(seek);
                                                                } else {
                                                                    result.addSeekAfter(seek);
                                                                }
                                                                if (!result.getLimit().isApplicable()) {
                                                                    this.parseLimit(result, false);
                                                                }
                                                            } else if (!result.getLimit().isApplicable()) {
                                                                this.parseLimit(result, true);
                                                            }
                                                            if (!this.parseKeywordIf("FOR")) break block47;
                                                            if (!this.parseKeywordIf("KEY SHARE")) break block48;
                                                            result.setForKeyShare(true);
                                                            break block49;
                                                        }
                                                        if (!this.parseKeywordIf("NO KEY UPDATE")) break block50;
                                                        result.setForNoKeyUpdate(true);
                                                        break block49;
                                                    }
                                                    if (!this.parseKeywordIf("SHARE")) break block51;
                                                    result.setForShare(true);
                                                    break block49;
                                                }
                                                if (!this.parseKeywordIf("UPDATE")) break block52;
                                                result.setForUpdate(true);
                                                break block49;
                                            }
                                            if (this.ignoreProEdition() || !this.parseKeywordIf("XML") || !this.requireProEdition()) break block53;
                                            if (!this.parseKeywordIf("RAW")) break block54;
                                            if (this.parseIf('(') && this.asTrue(name = this.parseStringLiteral()) && this.parse(')')) {
                                                result.setForXMLRaw(true, name);
                                                break block45;
                                            } else {
                                                result.setForXMLRaw(true);
                                            }
                                            break block45;
                                        }
                                        if (!this.parseKeywordIf("AUTO")) break block55;
                                        result.setForXMLAuto(true);
                                        break block45;
                                    }
                                    if (this.parseKeywordIf("PATH")) {
                                        if (this.parseIf('(') && this.asTrue(name = this.parseStringLiteral()) && this.parse(')')) {
                                            result.setForXMLPath(true, name);
                                            break block45;
                                        } else {
                                            result.setForXMLPath(true);
                                        }
                                        break block45;
                                    } else {
                                        if (!this.parseKeywordIf("EXPLICIT")) {
                                            throw this.expected("AUTO", "RAW", "PATH", "EXPLICIT");
                                        }
                                        result.setForXMLExplicit(true);
                                    }
                                    break block45;
                                }
                                if (!this.ignoreProEdition() && (jsonb = this.parseKeywordIf("JSONB", "JSON")) && this.requireProEdition()) {
                                    result.setForJSONB(jsonb);
                                    if (this.parseKeywordIf("AUTO")) {
                                        result.setForJSONAuto(true);
                                        break block46;
                                    } else {
                                        if (!this.parseKeywordIf("PATH")) {
                                            throw this.expected("AUTO", "PATH");
                                        }
                                        result.setForJSONPath(true);
                                    }
                                    break block46;
                                } else {
                                    throw this.expected("UPDATE", "NO KEY UPDATE", "SHARE", "KEY SHARE", "XML", "JSON");
                                }
                            }
                            if (this.parseKeywordIf("OF")) {
                                if (SelectQueryImpl.NO_SUPPORT_FOR_UPDATE_OF_FIELDS.contains((Object)this.parseDialect())) {
                                    result.setForUpdateOf(this.parseList(',', (? super ParseContext t2) -> t2.parseTable()).toArray(Tools.EMPTY_TABLE));
                                } else {
                                    result.setForUpdateOf(this.parseList(',', (? super ParseContext c) -> c.parseField()));
                                }
                            }
                            if (!this.parseKeywordIf("NOWAIT")) break block56;
                            result.setForUpdateNoWait();
                            break block47;
                        }
                        if (this.ignoreProEdition() || !this.parseKeywordIf("WAIT") || !this.requireProEdition()) break block57;
                        result.setForUpdateWait(Tools.asInt(this.parseUnsignedIntegerLiteral()));
                        break block47;
                    }
                    if (this.parseKeywordIf("SKIP LOCKED")) {
                        result.setForUpdateSkipLocked();
                    }
                    break block47;
                }
                while (this.parseIf(',')) {
                    if (this.parseKeywordIf("ELEMENTS")) {
                        if (this.parseKeywordIf("XSINIL")) {
                            result.setForXMLElementsXsinil(true);
                            continue;
                        }
                        if (this.parseKeywordIf("ABSENT")) {
                            result.setForXMLElementsAbsent(true);
                            continue;
                        }
                        result.setForXMLElements(true);
                        continue;
                    }
                    if (this.parseKeywordIf("BINARY BASE64")) {
                        result.setForXMLBinaryBase64(true);
                        continue;
                    }
                    if (this.parseKeywordIf("TYPE")) {
                        result.setForXMLType(true);
                        continue;
                    }
                    if (!this.parseKeywordIf("ROOT")) {
                        throw this.expected("ELEMENTS", "ROOT");
                    }
                    if (this.parseIf('(') && this.asTrue(name = this.parseStringLiteral()) && this.parse(')')) {
                        result.setForXMLRoot(true, name);
                        continue;
                    }
                    result.setForXMLRoot(true);
                }
                break block47;
            }
            while (this.parseIf(',')) {
                if (this.parseKeywordIf("INCLUDE_NULL_VALUES")) {
                    result.setForJSONIncludeNullValues(true);
                    continue;
                }
                if (this.parseKeywordIf("WITHOUT_ARRAY_WRAPPER")) {
                    result.setForJSONWithoutArrayWrapper(true);
                    continue;
                }
                if (!this.parseKeywordIf("ROOT")) {
                    throw this.expected("INCLUDE_NULL_VALUES", "ROOT", "WITHOUT_ARRAY_WRAPPER");
                }
                if (this.parseIf('(') && this.asTrue(name = this.parseStringLiteral()) && this.parse(')')) {
                    result.setForJSONRoot(true, name);
                    continue;
                }
                result.setForJSONRoot(true);
            }
        }
        if (this.parseKeywordIf("WITH CHECK OPTION")) {
            result.setWithCheckOption();
        } else if (this.parseKeywordIf("WITH READ ONLY")) {
            result.setWithReadOnly();
        }
        this.scope.scopeEnd(result);
        return result;
    }

    private final void parseLimit(SelectQueryImpl<Record> result, boolean offset) {
        boolean offsetStandard = false;
        boolean offsetPostgres = false;
        if (offset && this.parseKeywordIf("OFFSET")) {
            result.addOffset(this.parseField());
            if (this.parseKeywordIf("ROWS", "ROW")) {
                offsetStandard = true;
            } else if (this.peekKeyword("FETCH")) {
                offsetStandard = true;
            } else {
                offsetPostgres = true;
            }
        }
        if (!offsetStandard && this.parseKeywordIf("LIMIT")) {
            Field<?> limit = this.parseField();
            if (offsetPostgres) {
                result.addLimit(limit);
                if (this.parseKeywordIf("PERCENT")) {
                    result.setLimitPercent(true);
                }
                if (this.parseKeywordIf("WITH TIES")) {
                    result.setWithTies(true);
                }
            } else if (offset && this.parseIf(',')) {
                result.addLimit(limit, this.parseField());
            } else {
                if (this.parseKeywordIf("PERCENT")) {
                    result.setLimitPercent(true);
                }
                if (this.parseKeywordIf("WITH TIES")) {
                    result.setWithTies(true);
                }
                if (offset && this.parseKeywordIf("OFFSET")) {
                    result.addLimit(this.parseField(), limit);
                } else {
                    result.addLimit(limit);
                }
            }
        } else if (!offsetPostgres && this.parseKeywordIf("FETCH")) {
            this.parseAndGetKeyword("FIRST", "NEXT");
            if (this.parseAndGetKeywordIf("ROW", "ROWS") != null) {
                result.addLimit(DSL.inline(1L));
            } else {
                result.addLimit(this.parseField());
                if (this.parseKeywordIf("PERCENT")) {
                    result.setLimitPercent(true);
                }
                this.parseAndGetKeyword("ROW", "ROWS");
            }
            if (this.parseKeywordIf("WITH TIES")) {
                result.setWithTies(true);
            } else {
                this.parseKeyword("ONLY");
            }
        } else if (!offsetStandard && !offsetPostgres && this.parseKeywordIf("ROWS")) {
            Long from = this.parseUnsignedIntegerLiteral();
            if (this.parseKeywordIf("TO")) {
                Long to = this.parseUnsignedIntegerLiteral();
                result.addLimit(to - from);
                result.addOffset(from - 1L);
            } else {
                result.addLimit(from);
            }
        }
    }

    private final SelectQueryImpl<Record> parseQueryExpressionBody(Integer degree, WithImpl with, SelectQueryImpl<Record> prefix) {
        CombineOperator combine;
        Select<Record> lhs;
        SelectQueryImpl<Record> local = lhs = this.parseQueryTerm(degree, with, prefix);
        block6: while ((combine = this.parseCombineOperatorIf(false)) != null) {
            this.scope.scopeEnd(local);
            this.scope.scopeStart();
            if (degree == null) {
                degree = Tools.degree(lhs);
            }
            local = this.degreeCheck(degree, this.parseQueryTerm(degree, null, null));
            SelectQueryImpl<Record> rhs = local;
            switch (combine) {
                case UNION: {
                    lhs = lhs.union(rhs);
                    continue block6;
                }
                case UNION_ALL: {
                    lhs = lhs.unionAll(rhs);
                    continue block6;
                }
                case EXCEPT: {
                    lhs = lhs.except(rhs);
                    continue block6;
                }
                case EXCEPT_ALL: {
                    lhs = lhs.exceptAll(rhs);
                    continue block6;
                }
            }
            throw this.internalError();
        }
        return lhs;
    }

    private final SelectQueryImpl<Record> parseQueryTerm(Integer degree, WithImpl with, SelectQueryImpl<Record> prefix) {
        CombineOperator combine;
        Select<Record> lhs;
        SelectQueryImpl<Record> local = lhs = prefix != null ? prefix : this.parseQueryPrimary(degree, with);
        block4: while ((combine = this.parseCombineOperatorIf(true)) != null) {
            this.scope.scopeEnd(local);
            this.scope.scopeStart();
            if (degree == null) {
                degree = Tools.degree(lhs);
            }
            local = this.degreeCheck(degree, this.parseQueryPrimary(degree, null));
            SelectQueryImpl<Record> rhs = local;
            switch (combine) {
                case INTERSECT: {
                    lhs = lhs.intersect(rhs);
                    continue block4;
                }
                case INTERSECT_ALL: {
                    lhs = lhs.intersectAll(rhs);
                    continue block4;
                }
            }
            throw this.internalError();
        }
        return lhs;
    }

    private final SelectQueryImpl<Record> degreeCheck(int expected, SelectQueryImpl<Record> s2) {
        if (expected == 0) {
            return s2;
        }
        int actual = Tools.degree(s2);
        if (actual == 0) {
            return s2;
        }
        if (expected != actual) {
            throw this.exception("Select list must contain " + expected + " columns. Got: " + actual);
        }
        return s2;
    }

    private final SelectQueryImpl<Record> parseQueryPrimary(Integer degree, WithImpl with) {
        List<SelectFieldOrAsterisk> select;
        boolean withTies;
        boolean percent;
        Field<?> offset;
        Field<?> limit;
        List<Field> distinctOn;
        boolean distinct2;
        String hints;
        block43: {
            if (this.parseIf('(')) {
                SelectQueryImpl<Record> result = this.parseSelect(degree, with);
                this.parse(')');
                return result;
            }
            if (this.peekKeyword("VALUES")) {
                return (SelectQueryImpl)this.dsl.selectQuery(this.parseTableValueConstructor());
            }
            if (this.peekKeyword("TABLE")) {
                return (SelectQueryImpl)this.dsl.selectQuery(this.parseExplicitTable());
            }
            this.ignoreHints(false);
            this.parseKeywordUndocumentedAlternatives("SELECT", "SEL");
            hints = this.parseHints();
            distinct2 = this.parseKeywordIf("DISTINCT", "UNIQUE");
            distinctOn = null;
            if (distinct2) {
                if (this.parseKeywordIf("ON")) {
                    this.parse('(');
                    distinctOn = this.parseList(',', (? super ParseContext c) -> c.parseField());
                    this.parse(')');
                }
            } else {
                this.parseKeywordIf("ALL");
            }
            limit = null;
            offset = null;
            percent = false;
            withTies = false;
            if (this.parseKeywordIf("TOP")) {
                limit = this.parseField();
                boolean bl = percent = !this.ignoreProEdition() && this.parseKeywordIf("PERCENT") && this.requireProEdition();
                if (this.parseKeywordIf("START AT")) {
                    offset = this.parseField();
                } else if (this.parseKeywordIf("WITH TIES")) {
                    withTies = true;
                }
            } else if (this.parseKeywordIf("SKIP")) {
                offset = this.parseField();
                if (this.parseKeywordIf("FIRST")) {
                    limit = this.parseField();
                }
            } else if (this.parseKeywordIf("FIRST")) {
                limit = this.parseField();
            }
            select = this.parseSelectList();
            if (degree != null && !degree.equals(0) && !degree.equals(select.size())) {
                for (SelectFieldOrAsterisk s2 : select) {
                    if (s2 instanceof Field) continue;
                    break block43;
                }
                throw this.exception("Select list must contain " + degree + " columns. Got: " + select.size());
            }
        }
        Table<?> intoTable = null;
        ArrayList intoVariables = null;
        List<Table> from = null;
        if (this.parseKeywordIf("INTO")) {
            if (this.proEdition()) {
                int positionBeforeName = this.position();
                boolean at = this.peek('@');
                List<Name> names = at ? this.parseList(',', (? super ParseContext c) -> this.parseVariableName()) : this.parseList(',', (? super ParseContext c) -> this.parseName());
                Name first = names.get(0);
                if (SelectQueryImpl.EMULATE_SELECT_INTO_AS_CTAS.contains((Object)this.parseDialect()) || at || names.size() > 1 || !first.qualified() && this.scope.proceduralScope.get(first.last()) != null) {
                    intoVariables = new ArrayList();
                    for (Name name : names) {
                        intoVariables.add(this.scope.proceduralScope.getOrThrow(name.last(), () -> this.exception("Local variable not defined: " + String.valueOf(name))));
                    }
                } else {
                    intoTable = this.lookupTable(positionBeforeName, first);
                }
            } else {
                intoTable = this.parseTableName();
            }
        }
        if (this.parseKeywordIf("FROM")) {
            from = this.parseList(',', ParseContext::parseTable);
        }
        if (from != null && from.size() == 1 && from.get(0).getName().equalsIgnoreCase("dual")) {
            from = null;
        }
        if (from != null) {
            for (Table table : from) {
                this.scope.scope(table);
            }
        }
        SelectQueryImpl<Record> result = new SelectQueryImpl<Record>(this.dsl.configuration(), with);
        if (hints != null) {
            result.addHint(hints);
        }
        if (distinct2) {
            result.setDistinct(distinct2);
        }
        if (distinctOn != null) {
            result.addDistinctOn(distinctOn);
        }
        if (!select.isEmpty()) {
            result.addSelect(select);
        }
        if (intoTable != null) {
            result.setInto(intoTable);
        }
        if (intoVariables != null) {
            result.setInto(intoVariables);
        }
        if (from != null) {
            result.addFrom(from);
        }
        boolean where = false;
        boolean connectBy = false;
        boolean startWith = false;
        boolean groupBy = false;
        boolean having = false;
        while (!where && (where = this.parseQueryPrimaryWhere(result)) || !connectBy && (connectBy = this.parseQueryPrimaryConnectBy(result)) || !startWith && (startWith = this.parseQueryPrimaryStartWith(result)) || !groupBy && (groupBy = this.parseQueryPrimaryGroupBy(result)) || !having && (having = this.parseQueryPrimaryHaving(result))) {
        }
        if (startWith && !connectBy) {
            throw this.expected("CONNECT BY");
        }
        if (this.parseKeywordIf("WINDOW")) {
            result.addWindow(this.parseWindowDefinitions());
        }
        if (this.parseKeywordIf("QUALIFY")) {
            result.addQualify(this.parseCondition());
        }
        if (limit != null) {
            if (offset != null) {
                result.addLimit(offset, limit);
            } else {
                result.addLimit(limit);
            }
        }
        if (percent) {
            result.setLimitPercent(percent);
        }
        if (withTies) {
            result.setWithTies(true);
        }
        return result;
    }

    private final boolean parseQueryPrimaryWhere(SelectQueryImpl<Record> result) {
        if (this.parseKeywordIf("WHERE")) {
            result.addConditions(this.parseCondition());
            return true;
        }
        return false;
    }

    private final boolean parseQueryPrimaryHaving(SelectQueryImpl<Record> result) {
        if (this.parseKeywordIf("HAVING")) {
            result.addHaving(this.parseCondition());
            return true;
        }
        return false;
    }

    private final boolean parseQueryPrimaryGroupBy(SelectQueryImpl<Record> result) {
        if (this.parseKeywordIf("GROUP BY")) {
            if (!this.parseKeywordIf("ALL") && this.parseKeywordIf("DISTINCT")) {
                result.setGroupByDistinct(true);
            }
            if (this.parseIf('(', ')', true)) {
                this.parse(')');
                result.addGroupBy(new GroupField[0]);
            } else if (this.parseKeywordIf("ROLLUP")) {
                this.parse('(');
                result.addGroupBy(DSL.rollup(this.parseList(',', (? super ParseContext c) -> c.parseField()).toArray(Tools.EMPTY_FIELD)));
                this.parse(')');
            } else if (this.parseKeywordIf("CUBE")) {
                this.parse('(');
                result.addGroupBy(DSL.cube(this.parseList(',', (? super ParseContext c) -> c.parseField()).toArray(Tools.EMPTY_FIELD)));
                this.parse(')');
            } else if (this.parseKeywordIf("GROUPING SETS")) {
                this.parse('(');
                List<List> fieldSets = this.parseList(',', (? super ParseContext c) -> this.parseFieldsOrEmptyParenthesised());
                this.parse(')');
                result.addGroupBy(DSL.groupingSets(fieldSets.toArray(Tools.EMPTY_COLLECTION)));
            } else {
                List<GroupField> groupBy = this.parseOrdinaryGroupingSets();
                if (this.parseKeywordIf("WITH ROLLUP")) {
                    result.addGroupBy(DSL.rollup(groupBy.toArray(Tools.EMPTY_FIELD)));
                } else {
                    result.addGroupBy(groupBy);
                }
            }
            return true;
        }
        return false;
    }

    private final List<GroupField> parseOrdinaryGroupingSets() {
        ArrayList<GroupField> result = new ArrayList<GroupField>();
        do {
            if (this.peekKeyword("ROW")) {
                result.add(this.parseField());
                continue;
            }
            FieldOrRow fr = this.parseFieldOrRow();
            if (fr instanceof Field) {
                Field f = (Field)fr;
                result.add(f);
                continue;
            }
            result.addAll(Arrays.asList(((Row)fr).fields()));
        } while (this.parseIf(','));
        return result;
    }

    private final boolean parseQueryPrimaryConnectBy(SelectQueryImpl<Record> result) {
        if (!this.ignoreProEdition() && this.parseKeywordIf("CONNECT BY") && this.requireProEdition()) {
            if (this.parseKeywordIf("NOCYCLE")) {
                result.addConnectByNoCycle(this.parseCondition());
            } else {
                result.addConnectBy(this.parseCondition());
            }
            return true;
        }
        return false;
    }

    private final boolean parseQueryPrimaryStartWith(SelectQueryImpl<Record> result) {
        if (!this.ignoreProEdition() && this.parseKeywordIf("START WITH") && this.requireProEdition()) {
            result.setConnectByStartWith(this.parseCondition());
            return true;
        }
        return false;
    }

    private final List<WindowDefinition> parseWindowDefinitions() {
        return this.parseList(',', (? super ParseContext c) -> {
            Name name = this.parseIdentifier();
            this.parseKeyword("AS");
            this.parse('(');
            WindowDefinition result = name.as(this.parseWindowSpecificationIf(null, true));
            this.parse(')');
            return result;
        });
    }

    /*
     * WARNING - void declaration
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private final WindowSpecification parseWindowSpecificationIf(Name windowName, boolean orderByAllowed) {
        void var7_18;
        boolean groups2;
        WindowSpecificationRowsStep s2;
        WindowDefinition s1;
        WindowSpecificationOrderByStep windowSpecificationOrderByStep = windowName != null ? windowName.as() : (s1 = this.parseKeywordIf("PARTITION BY") ? DSL.partitionBy(this.parseList(',', (? super ParseContext c) -> c.parseField())) : null);
        if (this.parseKeywordIf("ORDER BY")) {
            if (!orderByAllowed) throw this.exception("ORDER BY not allowed");
            s2 = s1 == null ? DSL.orderBy(this.parseList(',', (? super ParseContext c) -> c.parseSortField())) : s1.orderBy(this.parseList(',', (? super ParseContext c) -> c.parseSortField()));
        } else {
            s2 = s1;
        }
        boolean rows = this.parseKeywordIf("ROWS");
        boolean range = !rows && this.parseKeywordIf("RANGE");
        boolean bl = groups2 = !rows && !range && this.parseKeywordIf("GROUPS");
        if ((rows || range || groups2) && !orderByAllowed) {
            throw this.exception("ROWS, RANGE, or GROUPS not allowed");
        }
        if (rows || range || groups2) {
            WindowSpecificationExcludeStep s4;
            if (this.parseKeywordIf("BETWEEN")) {
                Long n;
                WindowSpecificationRowsAndStep s3;
                if (this.parseKeywordIf("UNBOUNDED")) {
                    if (this.parseKeywordIf("PRECEDING")) {
                        s3 = s2 == null ? (rows ? DSL.rowsBetweenUnboundedPreceding() : (range ? DSL.rangeBetweenUnboundedPreceding() : DSL.groupsBetweenUnboundedPreceding())) : (rows ? s2.rowsBetweenUnboundedPreceding() : (range ? s2.rangeBetweenUnboundedPreceding() : s2.groupsBetweenUnboundedPreceding()));
                    } else {
                        if (!this.parseKeywordIf("FOLLOWING")) throw this.expected("FOLLOWING", "PRECEDING");
                        s3 = s2 == null ? (rows ? DSL.rowsBetweenUnboundedFollowing() : (range ? DSL.rangeBetweenUnboundedFollowing() : DSL.groupsBetweenUnboundedFollowing())) : (rows ? s2.rowsBetweenUnboundedFollowing() : (range ? s2.rangeBetweenUnboundedFollowing() : s2.groupsBetweenUnboundedFollowing()));
                    }
                } else if (this.parseKeywordIf("CURRENT ROW")) {
                    s3 = s2 == null ? (rows ? DSL.rowsBetweenCurrentRow() : (range ? DSL.rangeBetweenCurrentRow() : DSL.groupsBetweenCurrentRow())) : (rows ? s2.rowsBetweenCurrentRow() : (range ? s2.rangeBetweenCurrentRow() : s2.groupsBetweenCurrentRow()));
                } else {
                    n = this.parseUnsignedIntegerLiteralIf();
                    if (n == null) throw this.expected("CURRENT ROW", "UNBOUNDED", "integer literal");
                    if (this.parseKeywordIf("PRECEDING")) {
                        s3 = s2 == null ? (rows ? DSL.rowsBetweenPreceding(n.intValue()) : (range ? DSL.rangeBetweenPreceding(n.intValue()) : DSL.groupsBetweenPreceding(n.intValue()))) : (rows ? s2.rowsBetweenPreceding(n.intValue()) : (range ? s2.rangeBetweenPreceding(n.intValue()) : s2.groupsBetweenPreceding(n.intValue())));
                    } else {
                        if (!this.parseKeywordIf("FOLLOWING")) throw this.expected("FOLLOWING", "PRECEDING");
                        s3 = s2 == null ? (rows ? DSL.rowsBetweenFollowing(n.intValue()) : (range ? DSL.rangeBetweenFollowing(n.intValue()) : DSL.groupsBetweenFollowing(n.intValue()))) : (rows ? s2.rowsBetweenFollowing(n.intValue()) : (range ? s2.rangeBetweenFollowing(n.intValue()) : s2.groupsBetweenFollowing(n.intValue())));
                    }
                }
                this.parseKeyword("AND");
                if (this.parseKeywordIf("UNBOUNDED")) {
                    if (this.parseKeywordIf("PRECEDING")) {
                        s4 = s3.andUnboundedPreceding();
                    } else {
                        if (!this.parseKeywordIf("FOLLOWING")) throw this.expected("FOLLOWING", "PRECEDING");
                        s4 = s3.andUnboundedFollowing();
                    }
                } else if (this.parseKeywordIf("CURRENT ROW")) {
                    s4 = s3.andCurrentRow();
                } else {
                    n = this.parseUnsignedIntegerLiteral();
                    if (!this.asTrue(n)) throw this.expected("CURRENT ROW", "UNBOUNDED", "integer literal");
                    if (this.parseKeywordIf("PRECEDING")) {
                        s4 = s3.andPreceding(n.intValue());
                    } else {
                        if (!this.parseKeywordIf("FOLLOWING")) throw this.expected("FOLLOWING", "PRECEDING");
                        s4 = s3.andFollowing(n.intValue());
                    }
                }
            } else if (this.parseKeywordIf("UNBOUNDED")) {
                if (this.parseKeywordIf("PRECEDING")) {
                    s4 = s2 == null ? (rows ? DSL.rowsUnboundedPreceding() : (range ? DSL.rangeUnboundedPreceding() : DSL.groupsUnboundedPreceding())) : (rows ? s2.rowsUnboundedPreceding() : (range ? s2.rangeUnboundedPreceding() : s2.groupsUnboundedPreceding()));
                } else {
                    if (!this.parseKeywordIf("FOLLOWING")) throw this.expected("FOLLOWING", "PRECEDING");
                    s4 = s2 == null ? (rows ? DSL.rowsUnboundedFollowing() : (range ? DSL.rangeUnboundedFollowing() : DSL.groupsUnboundedFollowing())) : (rows ? s2.rowsUnboundedFollowing() : (range ? s2.rangeUnboundedFollowing() : s2.groupsUnboundedFollowing()));
                }
            } else if (this.parseKeywordIf("CURRENT ROW")) {
                s4 = s2 == null ? (rows ? DSL.rowsCurrentRow() : (range ? DSL.rangeCurrentRow() : DSL.groupsCurrentRow())) : (rows ? s2.rowsCurrentRow() : (range ? s2.rangeCurrentRow() : s2.groupsCurrentRow()));
            } else {
                Long n = this.parseUnsignedIntegerLiteral();
                if (!this.asTrue(n)) throw this.expected("BETWEEN", "CURRENT ROW", "UNBOUNDED", "integer literal");
                if (this.parseKeywordIf("PRECEDING")) {
                    s4 = s2 == null ? (rows ? DSL.rowsPreceding(n.intValue()) : (range ? DSL.rangePreceding(n.intValue()) : DSL.groupsPreceding(n.intValue()))) : (rows ? s2.rowsPreceding(n.intValue()) : (range ? s2.rangePreceding(n.intValue()) : s2.groupsPreceding(n.intValue())));
                } else {
                    if (!this.parseKeywordIf("FOLLOWING")) throw this.expected("FOLLOWING", "PRECEDING");
                    s4 = s2 == null ? (rows ? DSL.rowsFollowing(n.intValue()) : (range ? DSL.rangeFollowing(n.intValue()) : DSL.groupsFollowing(n.intValue()))) : (rows ? s2.rowsFollowing(n.intValue()) : (range ? s2.rangeFollowing(n.intValue()) : s2.groupsFollowing(n.intValue())));
                }
            }
            if (this.parseKeywordIf("EXCLUDE")) {
                if (this.parseKeywordIf("CURRENT ROW")) {
                    WindowSpecificationFinalStep windowSpecificationFinalStep = s4.excludeCurrentRow();
                } else if (this.parseKeywordIf("TIES")) {
                    WindowSpecificationFinalStep windowSpecificationFinalStep = s4.excludeTies();
                } else if (this.parseKeywordIf("GROUP")) {
                    WindowSpecificationFinalStep windowSpecificationFinalStep = s4.excludeGroup();
                } else {
                    if (!this.parseKeywordIf("NO OTHERS")) throw this.expected("CURRENT ROW", "TIES", "GROUP", "NO OTHERS");
                    WindowSpecificationFinalStep windowSpecificationFinalStep = s4.excludeNoOthers();
                }
            } else {
                WindowSpecificationExcludeStep windowSpecificationExcludeStep = s4;
            }
        } else {
            WindowSpecificationRowsStep windowSpecificationRowsStep = s2;
        }
        if (var7_18 != null) {
            return var7_18;
        }
        if (windowName != null) {
            return null;
        }
        windowName = this.parseIdentifierIf();
        if (windowName == null) return null;
        return this.parseWindowSpecificationIf(windowName, orderByAllowed);
    }

    private final Query parseDelete(WithImpl with, boolean parseResultQuery) {
        DeleteUsingStep<?> s5;
        DeleteUsingStep<?> s4;
        DeleteUsingStep<?> s2;
        this.parseKeyword("DELETE", "DEL");
        Field<?> limit = null;
        if (this.parseKeywordIf("TOP")) {
            limit = this.parseField();
        }
        this.parseKeywordIf("FROM");
        Table<?> table = this.scope.scope(this.parseJoinedTable(() -> this.peekKeyword(KEYWORDS_IN_DELETE_FROM)));
        DeleteUsingStep<?> s1 = with == null ? this.dsl.delete(table) : with.delete((Table)table);
        DeleteWhereStep<?> deleteWhereStep = s2 = this.parseKeywordIf("USING", "FROM") ? s1.using(this.parseList(',', (? super ParseContext t2) -> this.scope.scope(this.parseJoinedTable(() -> this.peekKeyword(KEYWORDS_IN_DELETE_FROM))))) : s1;
        DeleteUsingStep<?> s3 = this.parseKeywordIf("ALL") ? s2 : (this.parseKeywordIf("WHERE") ? s2.where(this.parseCondition()) : s2);
        DeleteLimitStep<Object> deleteLimitStep = s4 = this.parseKeywordIf("ORDER BY") ? s3.orderBy(this.parseList(',', (? super ParseContext c) -> c.parseSortField())) : s3;
        DeleteReturningStep<Object> deleteReturningStep = limit != null || this.parseKeywordIf("LIMIT") ? s4.limit(limit != null ? limit : this.parseField()) : (s5 = s4);
        return (parseResultQuery ? this.parseKeyword("RETURNING") : this.parseKeywordIf("RETURNING")) ? s5.returning(this.parseSelectList()) : s5;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private final Query parseInsert(WithImpl with, boolean parseResultQuery) {
        Name alias;
        this.scope.scopeStart();
        this.parseKeywordUndocumentedAlternatives("INSERT", "INS");
        this.parseKeywordIf("INTO");
        SelectField<Object> table = this.parseTableNameIf();
        if (table == null) {
            table = DSL.table(this.parseSelect());
        }
        if (this.parseKeywordIf("AS")) {
            table = table.as(this.parseIdentifier());
        } else if (!this.peekKeyword("DEFAULT VALUES", "SEL", "SELECT", "SET", "VALUES") && (alias = this.parseIdentifierIf()) != null) {
            table = table.as(alias);
        }
        this.scope.scope((Table<?>)table);
        InsertSetStep<Object> s1 = with == null ? this.dsl.insertInto(table) : with.insertInto(table);
        Field[] fields = null;
        if (!this.peekSelectOrWith(true) && this.parseIf('(') && !this.parseIf(')')) {
            fields = this.parseList(',', (? super ParseContext c) -> this.parseField()).toArray(Tools.EMPTY_FIELD);
            this.parse(')');
        }
        try {
            InsertReturningStep<Object> returning;
            InsertOnDuplicateStep<Object> onDuplicate;
            boolean hasExpressions = Tools.anyMatch(fields, f -> !(f instanceof TableField));
            if (hasExpressions || this.parseKeywordIf("VALUES")) {
                ArrayList<List<Object>> allValues = new ArrayList<List<Object>>();
                if (hasExpressions) {
                    allValues.add(Arrays.asList(fields));
                    fields = null;
                }
                while (!hasExpressions || this.parseIf(',')) {
                    this.parse('(');
                    if (fields == null && this.parseIf(')')) break;
                    List<Field> values2 = this.parseList(',', (? super ParseContext c) -> c.parseKeywordIf("DEFAULT") ? DSL.default_() : c.parseField());
                    if (fields != null && fields.length != values2.size()) {
                        throw this.exception("Insert field size (" + fields.length + ") must match values size (" + values2.size() + ")");
                    }
                    allValues.add(values2);
                    this.parse(')');
                    if (this.parseIf(',')) continue;
                }
                InsertValuesStepN<?> step2 = fields != null ? s1.columns(fields) : (InsertValuesStepN<?>)((Object)s1);
                for (List list : allValues) {
                    step2 = step2.values(list);
                }
                onDuplicate = step2;
                returning = onDuplicate;
            } else if (this.parseKeywordIf("SET")) {
                Map<Field<?>, Object> map = this.parseSetClauseList();
                onDuplicate = s1.set(map);
                returning = onDuplicate;
            } else if (this.peekSelectOrWith(true)) {
                Field[] f2 = fields;
                onDuplicate = this.newScope(() -> {
                    SelectQueryImpl<Record> select = this.parseWithOrSelect();
                    return f2 == null ? s1.select(select) : s1.columns(f2).select(select);
                });
                returning = onDuplicate;
            } else {
                if (!this.parseKeywordIf("DEFAULT VALUES")) throw this.expected("DEFAULT VALUES", "WITH", "SELECT", "SET", "VALUES");
                if (fields != null) {
                    throw this.notImplemented("DEFAULT VALUES without INSERT field list");
                }
                onDuplicate = s1.defaultValues();
                returning = onDuplicate;
            }
            if (this.parseKeywordIf("ON")) {
                if (this.parseKeywordIf("DUPLICATE KEY UPDATE")) {
                    InsertOnDuplicateSetMoreStep<Object> where;
                    this.parseKeywordIf("SET");
                    InsertOnDuplicateSetMoreStep<Object> insertOnDuplicateSetMoreStep = where = this.parseKeywordIf("ALL TO EXCLUDED") ? onDuplicate.onDuplicateKeyUpdate().setAllToExcluded() : onDuplicate.onDuplicateKeyUpdate().set(this.data(Tools.BooleanDataKey.DATA_PARSE_ON_CONFLICT, true, c -> c.parseSetClauseList()));
                    returning = this.parseKeywordIf("WHERE") ? where.where(this.parseCondition()) : where;
                } else if (this.parseKeywordIf("DUPLICATE KEY IGNORE")) {
                    returning = onDuplicate.onDuplicateKeyIgnore();
                } else {
                    Object where;
                    InsertOnConflictDoUpdateStep<Object> doUpdate;
                    if (!this.parseKeywordIf("CONFLICT")) throw this.expected("CONFLICT", "DUPLICATE");
                    if (this.parseKeywordIf("ON CONSTRAINT")) {
                        doUpdate = onDuplicate.onConflictOnConstraint(this.parseName());
                    } else if (this.parseIf('(')) {
                        where = onDuplicate.onConflict(this.parseList(',', (? super ParseContext c) -> this.parseFieldName()));
                        this.parse(')');
                        doUpdate = this.parseKeywordIf("WHERE") ? where.where(this.parseCondition()) : where;
                    } else {
                        doUpdate = onDuplicate.onConflict(new Field[0]);
                    }
                    this.parseKeyword("DO");
                    if (this.parseKeywordIf("NOTHING")) {
                        returning = doUpdate.doNothing();
                    } else {
                        if (!this.parseKeywordIf("UPDATE SET")) throw this.expected("NOTHING", "UPDATE");
                        Object object = where = this.parseKeywordIf("ALL TO EXCLUDED") ? doUpdate.doUpdate().setAllToExcluded() : doUpdate.doUpdate().set(this.data(Tools.BooleanDataKey.DATA_PARSE_ON_CONFLICT, true, c -> c.parseSetClauseList()));
                        returning = this.parseKeywordIf("WHERE") ? where.where(this.parseCondition()) : where;
                    }
                }
            }
            InsertOnDuplicateStep<Object> insertOnDuplicateStep = (parseResultQuery ? this.parseKeyword("RETURNING") : this.parseKeywordIf("RETURNING")) ? returning.returning(this.parseSelectList()) : returning;
            return insertOnDuplicateStep;
        }
        finally {
            this.scope.scopeEnd((Query)((InsertImpl)s1).getDelegate());
        }
    }

    private final Query parseUpdate(WithImpl with, boolean parseResultQuery) {
        UpdateWhereStep s6;
        UpdateWhereStep s5;
        UpdateWhereStep s3;
        UpdateFromStep s2;
        this.parseKeywordUndocumentedAlternatives("UPDATE", "UPD");
        Field<?> limit = null;
        if (this.parseKeywordIf("TOP")) {
            limit = this.parseField();
        }
        Table<?> table = this.scope.scope(this.parseJoinedTable(() -> this.peekKeyword(KEYWORDS_IN_UPDATE_FROM)));
        UpdateSetFirstStep<Object> s1 = with == null ? this.dsl.update(table) : with.update((Table)table);
        List<Table> from = this.parseKeywordIf("FROM") ? this.parseList(',', (? super ParseContext t2) -> this.scope.scope(this.parseJoinedTable(() -> this.peekKeyword(KEYWORDS_IN_UPDATE_FROM)))) : null;
        this.parseKeyword("SET");
        if (this.peek('(')) {
            Row row = this.parseRow();
            this.parse('=');
            if (this.peekSelectOrWith(true)) {
                ((UpdateQueryImpl)((UpdateImpl)s1).getDelegate()).addValues0(row, this.parseWithOrSelect(row.size()));
            } else {
                ((UpdateQueryImpl)((UpdateImpl)s1).getDelegate()).addValues0(row, this.parseRow(row.size()));
            }
            s2 = (UpdateFromStep)((Object)s1);
        } else {
            Map<Field<?>, Object> map = this.parseSetClauseList();
            s2 = s1.set(map);
        }
        UpdateWhereStep updateWhereStep = from != null ? s2.from(from) : (s3 = this.parseKeywordIf("FROM") ? s2.from(this.parseList(',', (? super ParseContext t2) -> this.parseJoinedTable(() -> this.peekKeyword(KEYWORDS_IN_UPDATE_FROM)))) : s2);
        UpdateWhereStep s4 = this.parseKeywordIf("ALL") ? s3 : (this.parseKeywordIf("WHERE") ? s3.where(this.parseCondition()) : s3);
        UpdateLimitStep updateLimitStep = s5 = this.parseKeywordIf("ORDER BY") ? s4.orderBy(this.parseList(',', (? super ParseContext c) -> c.parseSortField())) : s4;
        UpdateReturningStep updateReturningStep = limit != null || this.parseKeywordIf("LIMIT") ? s5.limit(limit != null ? limit : this.parseField()) : (s6 = s5);
        return (parseResultQuery ? this.parseKeyword("RETURNING") : this.parseKeywordIf("RETURNING")) ? s6.returning(this.parseSelectList()) : s6;
    }

    private final Map<Field<?>, Object> parseSetClauseList() {
        LinkedHashMap map = new LinkedHashMap();
        do {
            TableField<?, ?> field2;
            if (map.containsKey(field2 = this.parseFieldName())) {
                throw this.exception("Duplicate column in set clause list: " + String.valueOf(field2));
            }
            this.parse('=');
            Field<Object> value = this.parseKeywordIf("DEFAULT") ? DSL.default_() : this.parseField();
            map.put(field2, value);
        } while (this.parseIf(','));
        return map;
    }

    private final Merge<?> parseMerge(WithImpl with) {
        MergeMatchedStep<Object> s3;
        this.parseKeyword("MERGE");
        this.parseKeywordIf("INTO");
        SelectField<Object> target = this.parseTableName();
        if (this.parseKeywordIf("AS") || !this.peekKeyword("USING")) {
            target = target.as(this.parseIdentifier());
        }
        this.parseKeyword("USING");
        Table<?> table = null;
        SelectQueryImpl<Record> using2 = null;
        if (this.parseIf('(')) {
            using2 = this.parseSelect();
            this.parse(')');
        } else {
            table = this.parseTableName();
        }
        TableLike<?> usingTable = this.parseCorrelationNameIf(table != null ? table : using2, () -> this.peekKeyword("ON"));
        this.parseKeyword("ON");
        Condition on = this.parseCondition();
        boolean update = false;
        boolean insert = false;
        Field<?>[] insertColumns = null;
        List<Field> insertValues = null;
        Condition insertWhere = null;
        Condition updateAnd = null;
        Condition updateWhere = null;
        Condition deleteWhere = null;
        MergeUsingStep<?> s1 = with == null ? this.dsl.mergeInto(target) : with.mergeInto(target);
        MergeMatchedStep<Object> s2 = s1.using(usingTable).on(on);
        while (true) {
            if (this.parseKeywordIf("WHEN MATCHED")) {
                update = true;
                if (this.parseKeywordIf("AND")) {
                    updateAnd = this.parseCondition();
                }
                if (this.parseKeywordIf("THEN DELETE")) {
                    s2 = updateAnd != null ? s2.whenMatchedAnd(updateAnd).thenDelete() : s2.whenMatchedThenDelete();
                    continue;
                }
                this.parseKeyword("THEN UPDATE SET");
                Map<Field<?>, Object> updateSet = this.parseSetClauseList();
                if (updateAnd == null && this.parseKeywordIf("WHERE")) {
                    updateWhere = this.parseCondition();
                }
                if (updateAnd == null && this.parseKeywordIf("DELETE WHERE")) {
                    deleteWhere = this.parseCondition();
                }
                if (updateAnd != null) {
                    s2.whenMatchedAnd(updateAnd).thenUpdate().set(updateSet);
                    continue;
                }
                s3 = s2.whenMatchedThenUpdate().set(updateSet);
                MergeMatchedStep<Object> s4 = updateWhere != null ? s3.where(updateWhere) : s3;
                s2 = deleteWhere != null ? s4.deleteWhere(deleteWhere) : s3;
                continue;
            }
            if (insert || !(insert = this.parseKeywordIf("WHEN NOT MATCHED"))) break;
            if (this.parseKeywordIf("AND")) {
                insertWhere = this.parseCondition();
            }
            this.parseKeyword("THEN INSERT");
            this.parse('(');
            insertColumns = Tools.fieldsByName(this.parseIdentifiers().toArray(Tools.EMPTY_NAME));
            this.parse(')');
            this.parseKeyword("VALUES");
            this.parse('(');
            insertValues = this.parseList(',', (? super ParseContext c) -> c.parseKeywordIf("DEFAULT") ? DSL.default_() : c.parseField());
            this.parse(')');
            if (insertColumns.length != insertValues.size()) {
                throw this.exception("Insert column size (" + insertColumns.length + ") must match values size (" + insertValues.size() + ")");
            }
            if (insertWhere != null || !this.parseKeywordIf("WHERE")) continue;
            insertWhere = this.parseCondition();
        }
        if (!update && !insert) {
            throw this.exception("At least one of UPDATE or INSERT clauses is required");
        }
        s3 = insert ? (insertWhere != null ? s2.whenNotMatchedThenInsert(insertColumns).values(insertValues).where(insertWhere) : s2.whenNotMatchedThenInsert(insertColumns).values(insertValues)) : s2;
        return s3;
    }

    private final Query parseOpen() {
        this.parseKeyword("OPEN");
        this.parseKeyword("SCHEMA");
        return this.parseSetSchema();
    }

    private final Query parseSet() {
        this.parseKeyword("SET");
        if (this.parseKeywordIf("CATALOG")) {
            return this.parseSetCatalog();
        }
        if (this.parseKeywordIf("CURRENT SCHEMA")) {
            return this.parseSetSchema();
        }
        if (this.parseKeywordIf("CURRENT SQLID")) {
            return this.parseSetSchema();
        }
        if (this.parseKeywordIf("GENERATOR")) {
            return this.parseSetGenerator();
        }
        if (this.parseKeywordIf("SCHEMA")) {
            return this.parseSetSchema();
        }
        if (this.parseKeywordIf("SEARCH_PATH")) {
            return this.parseSetSearchPath();
        }
        return this.parseSetCommand();
    }

    private final Query parseSetCommand() {
        if (Boolean.TRUE.equals(this.settings().isParseSetCommands())) {
            Name name = this.parseIdentifier();
            this.parseIf('=');
            Long value = this.parseSignedIntegerLiteralIf();
            return this.dsl.set(name, value != null ? DSL.inline(value) : DSL.inline(this.parseStringLiteral()));
        }
        this.parseUntilEOL();
        return IGNORE_NO_DELIMITER.get();
    }

    private final Query parseSetCatalog() {
        return this.dsl.setCatalog(this.parseCatalogName());
    }

    private final Query parseUse() {
        this.parseKeyword("USE");
        this.parseKeywordIf("DATABASE");
        return this.dsl.setCatalog(this.parseCatalogName());
    }

    private final Query parseSetSchema() {
        this.parseIf('=');
        return this.peek('\'') ? this.dsl.setSchema(this.parseStringLiteral()) : this.dsl.setSchema(this.parseSchemaName());
    }

    private final Query parseSetSearchPath() {
        if (!this.parseIf('=')) {
            this.parseKeyword("TO");
        }
        Schema schema = null;
        do {
            Schema s2 = this.parseSchemaName();
            if (schema != null) continue;
            schema = s2;
        } while (this.parseIf(','));
        return this.dsl.setSchema(schema);
    }

    private final DDLQuery parseCommentOn() {
        CommentOnIsStep s1;
        this.parseKeyword("COMMENT ON");
        if (this.parseKeywordIf("COLUMN")) {
            s1 = this.dsl.commentOnColumn(this.parseFieldName());
        } else if (this.parseKeywordIf("TABLE")) {
            Table<?> table = this.parseTableName();
            if (this.parseIf('(')) {
                CommentOnIsStep s12 = this.dsl.commentOnColumn(table.getQualifiedName().append(this.parseIdentifier()));
                this.parseKeyword("IS");
                CommentOnFinalStep s2 = s12.is(this.parseStringLiteral());
                this.parse(')');
                return s2;
            }
            s1 = this.dsl.commentOnTable(table);
        } else if (this.parseKeywordIf("VIEW")) {
            s1 = this.dsl.commentOnView(this.parseTableName());
        } else {
            if (this.parseAndGetKeywordIf("ACCESS METHOD", "AUDIT POLICY", "COLLATION", "CONVERSION", "DATABASE", "DOMAIN", "EDITION", "EXTENSION", "EVENT TRIGGER", "FOREIGN DATA WRAPPER", "FOREIGN TABLE", "INDEX", "INDEXTYPE", "LANGUAGE", "LARGE OBJECT", "MATERIALIZED VIEW", "MINING MODEL", "OPERATOR", "PROCEDURAL LANGUAGE", "PUBLICATION", "ROLE", "SCHEMA", "SEQUENCE", "SERVER", "STATISTICS", "SUBSCRIPTION", "TABLESPACE", "TEXT SEARCH CONFIGURATION", "TEXT SEARCH DICTIONARY", "TEXT SEARCH PARSER", "TEXT SEARCH TEMPLATE", "TYPE", "VIEW") != null) {
                this.parseIdentifier();
                this.parseKeyword("IS");
                this.parseStringLiteral();
                return IGNORE.get();
            }
            if (this.parseKeywordIf("CONSTRAINT")) {
                this.parseIdentifier();
                this.parseKeyword("ON");
                this.parseKeywordIf("DOMAIN");
                this.parseName();
                this.parseKeyword("IS");
                this.parseStringLiteral();
                return IGNORE.get();
            }
            if (this.parseAndGetKeywordIf("POLICY", "RULE", "TRIGGER") != null) {
                this.parseIdentifier();
                this.parseKeyword("ON");
                this.parseIdentifier();
                this.parseKeyword("IS");
                this.parseStringLiteral();
                return IGNORE.get();
            }
            if (this.parseKeywordIf("TRANSFORM FOR")) {
                this.parseIdentifier();
                this.parseKeyword("LANGUAGE");
                this.parseIdentifier();
                this.parseKeyword("IS");
                this.parseStringLiteral();
                return IGNORE.get();
            }
            throw this.unsupportedClause();
        }
        this.parseKeyword("IS");
        return s1.is(this.parseStringLiteral());
    }

    private final DDLQuery parseCreate() {
        this.parseKeyword("CREATE");
        switch (this.characterUpper()) {
            case 'C': {
                if (!this.parseKeywordIf("CACHED TABLE")) break;
                return this.parseCreateTable(false);
            }
            case 'D': {
                if (this.parseKeywordIf("DATABASE")) {
                    return this.parseCreateDatabase();
                }
                if (!this.parseKeywordIf("DOMAIN")) break;
                return this.parseCreateDomain();
            }
            case 'E': {
                if (!this.parseKeywordIf("EXTENSION")) break;
                return this.parseCreateExtension();
            }
            case 'F': {
                if (this.parseKeywordIf("FORCE VIEW")) {
                    return this.parseCreateView(false);
                }
                if (this.parseKeywordIf("FULLTEXT INDEX") && this.requireUnsupportedSyntax()) {
                    return this.parseCreateIndex(false);
                }
                if (this.ignoreProEdition() || !this.parseKeywordIf("FUNCTION") || !this.requireProEdition()) break;
                return this.parseCreateFunction(false);
            }
            case 'G': {
                if (this.parseKeywordIf("GENERATOR")) {
                    return this.parseCreateSequence();
                }
                if (!this.parseKeywordIf("GLOBAL TEMP TABLE", "GLOBAL TEMPORARY TABLE")) break;
                return this.parseCreateTable(true);
            }
            case 'I': {
                if (!this.parseKeywordIf("INDEX")) break;
                return this.parseCreateIndex(false);
            }
            case 'M': {
                if (!this.parseKeywordIf("MEMORY TABLE")) break;
                return this.parseCreateTable(false);
            }
            case 'O': {
                if (!this.parseKeywordIf("OR")) break;
                this.parseKeyword("REPLACE", "ALTER");
                if (!this.ignoreProEdition() && this.parseKeywordIf("TRIGGER") && this.requireProEdition()) {
                    return this.parseCreateTrigger(true);
                }
                if (this.parseKeywordIf("VIEW", "FORCE VIEW")) {
                    return this.parseCreateView(true);
                }
                if (!this.ignoreProEdition() && this.parseKeywordIf("FUNCTION") && this.requireProEdition()) {
                    return this.parseCreateFunction(true);
                }
                if (this.parseKeywordIf("PACKAGE")) {
                    throw this.notImplemented("CREATE PACKAGE", "https://github.com/jOOQ/jOOQ/issues/9190");
                }
                if (!this.ignoreProEdition() && this.parseKeywordIf("PROC", "PROCEDURE") && this.requireProEdition()) {
                    return this.parseCreateProcedure(true);
                }
                throw this.expected("FUNCTION", "PACKAGE", "PROCEDURE", "TRIGGER", "VIEW");
            }
            case 'P': {
                if (this.parseKeywordIf("PACKAGE")) {
                    throw this.notImplemented("CREATE PACKAGE", "https://github.com/jOOQ/jOOQ/issues/9190");
                }
                if (this.ignoreProEdition() || !this.parseKeywordIf("PROC", "PROCEDURE") || !this.requireProEdition()) break;
                return this.parseCreateProcedure(false);
            }
            case 'R': {
                if (!this.parseKeywordIf("ROLE")) break;
                throw this.notImplemented("CREATE ROLE", "https://github.com/jOOQ/jOOQ/issues/10167");
            }
            case 'S': {
                if (this.parseKeywordIf("SCHEMA")) {
                    return this.parseCreateSchema();
                }
                if (this.parseKeywordIf("SEQUENCE")) {
                    return this.parseCreateSequence();
                }
                if (this.parseKeywordIf("SPATIAL INDEX") && this.requireUnsupportedSyntax()) {
                    return this.parseCreateIndex(false);
                }
                if (!this.parseKeywordIf("SYNONYM")) break;
                throw this.notImplemented("CREATE SYNONYM", "https://github.com/jOOQ/jOOQ/issues/9574");
            }
            case 'T': {
                if (this.parseKeywordIf("TABLE")) {
                    return this.parseCreateTable(false);
                }
                if (this.parseKeywordIf("TEMP TABLE", "TEMPORARY TABLE")) {
                    return this.parseCreateTable(true);
                }
                if (!this.ignoreProEdition() && this.parseKeywordIf("TRIGGER") && this.requireProEdition()) {
                    return this.parseCreateTrigger(false);
                }
                if (this.parseKeywordIf("TYPE")) {
                    return this.parseCreateType();
                }
                if (!this.parseKeywordIf("TABLESPACE")) break;
                throw this.notImplemented("CREATE TABLESPACE");
            }
            case 'U': {
                if (this.parseKeywordIf("UNIQUE INDEX")) {
                    return this.parseCreateIndex(true);
                }
                if (!this.parseKeywordIf("USER")) break;
                throw this.notImplemented("CREATE USER", "https://github.com/jOOQ/jOOQ/issues/10167");
            }
            case 'V': {
                if (this.parseKeywordIf("VIEW")) {
                    return this.parseCreateView(false);
                }
                if (!this.parseKeywordIf("VIRTUAL") || !this.parseKeyword("TABLE")) break;
                return this.parseCreateTable(false);
            }
        }
        throw this.expected("FUNCTION", "GENERATOR", "GLOBAL TEMPORARY TABLE", "INDEX", "OR ALTER", "OR REPLACE", "PROCEDURE", "SCHEMA", "SEQUENCE", "TABLE", "TEMPORARY TABLE", "TRIGGER", "TYPE", "UNIQUE INDEX", "VIEW");
    }

    private final Query parseAlter() {
        this.parseKeyword("ALTER");
        switch (this.characterUpper()) {
            case 'D': {
                if (this.parseKeywordIf("DATABASE")) {
                    return this.parseAlterDatabase();
                }
                if (!this.parseKeywordIf("DOMAIN")) break;
                return this.parseAlterDomain();
            }
            case 'E': {
                if (!this.parseKeywordIf("EXTENSION")) break;
                throw this.notImplemented("ALTER EXTENSION");
            }
            case 'F': {
                if (!this.parseKeywordIf("FUNCTION")) break;
                throw this.notImplemented("ALTER FUNCTION", "https://github.com/jOOQ/jOOQ/issues/9190");
            }
            case 'I': {
                if (!this.parseKeywordIf("INDEX")) break;
                return this.parseAlterIndex();
            }
            case 'P': {
                if (this.parseKeywordIf("PACKAGE")) {
                    throw this.notImplemented("ALTER PACKAGE", "https://github.com/jOOQ/jOOQ/issues/9190");
                }
                if (!this.parseKeywordIf("PROCEDURE")) break;
                throw this.notImplemented("ALTER PROCEDURE", "https://github.com/jOOQ/jOOQ/issues/9190");
            }
            case 'R': {
                if (!this.parseKeywordIf("ROLE")) break;
                throw this.notImplemented("ALTER ROLE", "https://github.com/jOOQ/jOOQ/issues/10167");
            }
            case 'S': {
                if (this.parseKeywordIf("SCHEMA")) {
                    return this.parseAlterSchema();
                }
                if (this.parseKeywordIf("SEQUENCE")) {
                    return this.parseAlterSequence();
                }
                if (this.parseKeywordIf("SESSION")) {
                    return this.parseAlterSession();
                }
                if (!this.parseKeywordIf("SYNONYM")) break;
                throw this.notImplemented("ALTER SYNONYM", "https://github.com/jOOQ/jOOQ/issues/9574");
            }
            case 'T': {
                if (this.parseKeywordIf("TABLE")) {
                    return this.parseAlterTable();
                }
                if (this.parseKeywordIf("TYPE")) {
                    return this.parseAlterType();
                }
                if (this.parseKeywordIf("TABLESPACE")) {
                    throw this.notImplemented("ALTER TABLESPACE");
                }
                if (!this.parseKeywordIf("TRIGGER")) break;
                throw this.notImplemented("ALTER TRIGGER", "https://github.com/jOOQ/jOOQ/issues/6956");
            }
            case 'U': {
                if (!this.parseKeywordIf("USER")) break;
                throw this.notImplemented("ALTER USER", "https://github.com/jOOQ/jOOQ/issues/10167");
            }
            case 'V': {
                if (!this.parseKeywordIf("VIEW")) break;
                return this.parseAlterView();
            }
        }
        throw this.expected("DOMAIN", "INDEX", "SCHEMA", "SEQUENCE", "SESSION", "TABLE", "TYPE", "VIEW");
    }

    private final DDLQuery parseDrop() {
        this.parseKeyword("DROP");
        switch (this.characterUpper()) {
            case 'D': {
                if (this.parseKeywordIf("DATABASE")) {
                    return this.parseDropDatabase();
                }
                if (!this.parseKeywordIf("DOMAIN")) break;
                return this.parseCascadeRestrictIf(this.parseIfExists(this::parseDomainName, this.dsl::dropDomainIfExists, this.dsl::dropDomain), DropDomainCascadeStep::cascade, DropDomainCascadeStep::restrict);
            }
            case 'E': {
                if (!this.parseKeywordIf("EXTENSION")) break;
                return this.parseDropExtension();
            }
            case 'F': {
                if (this.ignoreProEdition() || !this.parseKeywordIf("FUNCTION") || !this.requireProEdition()) break;
                return this.parseDropFunction();
            }
            case 'G': {
                if (!this.parseKeywordIf("GENERATOR")) break;
                return this.parseDropSequence();
            }
            case 'I': {
                if (!this.parseKeywordIf("INDEX")) break;
                return this.parseDropIndex();
            }
            case 'P': {
                if (this.parseKeywordIf("PACKAGE")) {
                    throw this.notImplemented("DROP PACKAGE", "https://github.com/jOOQ/jOOQ/issues/9190");
                }
                if (this.ignoreProEdition() || !this.parseKeywordIf("PROC", "PROCEDURE") || !this.requireProEdition()) break;
                return this.parseDropProcedure();
            }
            case 'R': {
                if (!this.parseKeywordIf("ROLE")) break;
                throw this.notImplemented("DROP ROLE", "https://github.com/jOOQ/jOOQ/issues/10167");
            }
            case 'S': {
                if (this.parseKeywordIf("SEQUENCE")) {
                    return this.parseDropSequence();
                }
                if (!this.parseKeywordIf("SCHEMA")) break;
                return this.parseCascadeRestrictIf(this.parseIfExists(this::parseSchemaName, this.dsl::dropSchemaIfExists, this.dsl::dropSchema), DropSchemaStep::cascade, DropSchemaStep::restrict);
            }
            case 'T': {
                if (this.parseKeywordIf("TABLE")) {
                    return this.parseDropTable(false);
                }
                if (this.parseKeywordIf("TEMPORARY TABLE")) {
                    return this.parseDropTable(true);
                }
                if (!this.ignoreProEdition() && this.parseKeywordIf("TRIGGER") && this.requireProEdition()) {
                    return this.parseDropTrigger();
                }
                if (this.parseKeywordIf("TYPE")) {
                    return this.parseCascadeRestrictIf(this.parseIfExists(this::parseIdentifiers, this.dsl::dropTypeIfExists, this.dsl::dropType), DropTypeStep::cascade, DropTypeStep::restrict);
                }
                if (!this.parseKeywordIf("TABLESPACE")) break;
                throw this.notImplemented("DROP TABLESPACE");
            }
            case 'U': {
                if (!this.parseKeywordIf("USER")) break;
                throw this.notImplemented("DROP USER", "https://github.com/jOOQ/jOOQ/issues/10167");
            }
            case 'V': {
                if (!this.parseKeywordIf("VIEW")) break;
                return this.parseDropView();
            }
        }
        throw this.expected("GENERATOR", "FUNCTION", "INDEX", "PROCEDURE", "SCHEMA", "SEQUENCE", "TABLE", "TEMPORARY TABLE", "TRIGGER", "TYPE", "VIEW");
    }

    private final Truncate<?> parseTruncate() {
        TruncateCascadeStep<?> step2;
        this.parseKeyword("TRUNCATE");
        this.parseKeywordIf("TABLE");
        Table<?> table = this.parseTableName();
        boolean continueIdentity = this.parseKeywordIf("CONTINUE IDENTITY");
        boolean restartIdentity = !continueIdentity && this.parseKeywordIf("RESTART IDENTITY");
        boolean cascade = this.parseKeywordIf("CASCADE");
        boolean restrict = !cascade && this.parseKeywordIf("RESTRICT");
        TruncateIdentityStep<?> step1 = this.dsl.truncate(table);
        TruncateCascadeStep<?> truncateCascadeStep = continueIdentity ? step1.continueIdentity() : (step2 = restartIdentity ? step1.restartIdentity() : step1);
        return cascade ? step2.cascade() : (restrict ? step2.restrict() : step2);
    }

    private final DDLQuery parseGrant() {
        this.parseKeyword("GRANT");
        Privilege privilege = this.parsePrivilege();
        ArrayList<Privilege> privileges = null;
        while (this.parseIf(',')) {
            if (privileges == null) {
                privileges = new ArrayList<Privilege>();
                privileges.add(privilege);
            }
            privileges.add(this.parsePrivilege());
        }
        this.parseKeyword("ON");
        this.parseKeywordIf("TABLE");
        Table<?> table = this.parseTableName();
        this.parseKeyword("TO");
        User user = this.parseKeywordIf("PUBLIC") ? null : this.parseUser();
        GrantOnStep s1 = privileges == null ? this.dsl.grant(privilege) : this.dsl.grant(privileges);
        GrantToStep s2 = s1.on(table);
        GrantWithGrantOptionStep s3 = user == null ? s2.toPublic() : s2.to(user);
        return this.parseKeywordIf("WITH GRANT OPTION") ? s3.withGrantOption() : s3;
    }

    private final DDLQuery parseRevoke() {
        this.parseKeyword("REVOKE");
        boolean grantOptionFor = this.parseKeywordIf("GRANT OPTION FOR");
        Privilege privilege = this.parsePrivilege();
        ArrayList<Privilege> privileges = null;
        while (this.parseIf(',')) {
            if (privileges == null) {
                privileges = new ArrayList<Privilege>();
                privileges.add(privilege);
            }
            privileges.add(this.parsePrivilege());
        }
        this.parseKeyword("ON");
        this.parseKeywordIf("TABLE");
        Table<?> table = this.parseTableName();
        RevokeOnStep s1 = grantOptionFor ? (privileges == null ? this.dsl.revokeGrantOptionFor(privilege) : this.dsl.revokeGrantOptionFor(privileges)) : (privileges == null ? this.dsl.revoke(privilege) : this.dsl.revoke(privileges));
        this.parseKeyword("FROM");
        User user = this.parseKeywordIf("PUBLIC") ? null : this.parseUser();
        RevokeFromStep s2 = s1.on(table);
        return user == null ? s2.fromPublic() : s2.from(user);
    }

    private final Query parseExec() {
        if (this.parseKeywordIf("EXEC SP_RENAME")) {
            if (this.parseKeywordIf("@OBJNAME")) {
                this.parse('=');
            }
            Name oldName = this.dsl.parser().parseName(this.parseStringLiteral());
            this.parse(',');
            if (this.parseKeywordIf("@NEWNAME")) {
                this.parse('=');
            }
            Name newName = this.dsl.parser().parseName(this.parseStringLiteral());
            String objectType = "TABLE";
            if (this.parseIf(',')) {
                if (this.parseKeywordIf("@OBJTYPE")) {
                    this.parse('=');
                }
                if (!this.parseKeywordIf("NULL")) {
                    objectType = this.parseStringLiteral();
                }
            }
            if ("TABLE".equalsIgnoreCase(objectType)) {
                return this.dsl.alterTable(oldName).renameTo(newName.unqualifiedName());
            }
            if ("INDEX".equalsIgnoreCase(objectType)) {
                return this.dsl.alterIndex(oldName).renameTo(newName.unqualifiedName());
            }
            if ("COLUMN".equalsIgnoreCase(objectType)) {
                return this.dsl.alterTable(oldName.qualifier()).renameColumn(oldName.unqualifiedName()).to(newName.unqualifiedName());
            }
            throw this.exception("Unsupported object type: " + objectType);
        }
        if (!this.ignoreProEdition() && this.requireProEdition()) {
            return this.parseCall();
        }
        throw this.unsupportedClause();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private final Block parseBlock(boolean allowDeclareSection) {
        LanguageContext previous = this.languageContext;
        try {
            if (this.languageContext == LanguageContext.QUERY) {
                this.languageContext = LanguageContext.BLOCK;
            }
            ArrayList<Statement> statements = new ArrayList<Statement>();
            this.scope.proceduralScope.scopeStart();
            if (allowDeclareSection && !this.ignoreProEdition() && this.parseKeywordIf("DECLARE") && this.requireProEdition()) {
                statements.addAll(this.parseDeclarations());
            } else {
                this.parseKeywordIf("EXECUTE BLOCK AS");
            }
            this.parseKeyword("BEGIN");
            this.parseKeywordIf("ATOMIC", "NOT ATOMIC");
            statements.addAll(this.parseStatementsAndPeek("END"));
            this.parseKeyword("END");
            this.parseLabelReferenceIf();
            this.parseIf(';');
            this.scope.proceduralScope.scopeEnd();
            Block block = this.dsl.begin(statements);
            return block;
        }
        finally {
            this.languageContext = previous;
        }
    }

    private final void parseSemicolonAfterNonBlocks(Statement result) {
        if (!(result instanceof Block)) {
            this.parseIf(';');
        } else if (result instanceof BlockImpl && !((BlockImpl)result).alwaysWrapInBeginEnd) {
            this.parseIf(';');
        }
    }

    private final Statement parseStatementAndSemicolon() {
        Statement result = this.parseStatementAndSemicolonIf();
        if (result == null) {
            throw this.expected("Statement");
        }
        return result;
    }

    final Statement parseStatementAndSemicolonIf() {
        Statement result = this.parseStatement();
        this.parseSemicolonAfterNonBlocks(result);
        return result;
    }

    private final List<Statement> parseStatements(boolean peek, String ... keywords) {
        ArrayList<Statement> statements = new ArrayList<Statement>();
        while (!(peek && this.peekKeyword(keywords) || !peek && this.parseKeywordIf(keywords))) {
            Statement parsed;
            Label label = this.parseLabelDeclarationIf();
            Statement stored = parsed = this.parseStatement();
            if (parsed == null) break;
            if (label != null) {
                stored = label.label(parsed);
            }
            statements.add(stored);
            this.parseSemicolonAfterNonBlocks(parsed);
        }
        return statements;
    }

    private final List<Statement> parseStatementsAndPeek(String ... keywords) {
        return this.parseStatements(true, keywords);
    }

    private final List<Statement> parseStatementsAndKeyword(String ... keywords) {
        return this.parseStatements(false, keywords);
    }

    @Pro
    private final Label parseLabelDeclarationIf() {
        if (this.parseIf("<<")) {
            Label label = this.parseLabelReference();
            this.parse(">>");
            return label;
        }
        int p = this.position();
        Label label = this.parseLabelReferenceIf();
        if (label != null && this.parseIf(':') && !this.parseIf('=')) {
            return label;
        }
        this.position(p);
        return null;
    }

    @Pro
    private final Label parseLabelReference() {
        Label result = this.parseLabelReferenceIf();
        if (result == null) {
            throw this.expected("Label");
        }
        return result;
    }

    @Pro
    private final Label parseLabelReferenceIf() {
        Name identifier = this.parseIdentifierIf();
        if (identifier != null) {
            return DSL.label(identifier);
        }
        return null;
    }

    @Pro
    private final List<Statement> tryParseDeclarations() {
        try {
            return this.parseDeclarations();
        }
        catch (ParserException parserException) {
            return Collections.emptyList();
        }
    }

    @Pro
    private final List<Statement> parseDeclarations() {
        ArrayList<Statement> declarations = new ArrayList<Statement>();
        do {
            declarations.add(this.parseDeclaration());
            this.parse(';');
        } while (!this.peekKeyword("BEGIN"));
        return declarations;
    }

    private final Query parseStartTransaction() {
        this.parseKeyword("START", "BEGIN");
        this.parseKeyword("WORK", "TRAN", "TRANSACTION");
        this.parseKeywordIf("READ WRITE");
        return this.dsl.startTransaction();
    }

    private final Query parseSavepoint() {
        if (this.parseKeywordIf("SAVEPOINT")) {
            Name n = this.parseIdentifier();
            this.parseKeywordIf("UNIQUE");
            this.parseKeywordIf("ON ROLLBACK RETAIN CURSORS");
            return this.dsl.savepoint(n);
        }
        this.parseKeyword("SAVE");
        this.parseKeyword("TRAN", "TRANSACTION");
        return this.dsl.savepoint(this.parseIdentifier());
    }

    private final Query parseReleaseSavepoint() {
        this.parseKeyword("RELEASE");
        this.parseKeywordIf("TO");
        this.parseKeywordIf("SAVEPOINT");
        return this.dsl.releaseSavepoint(this.parseIdentifier());
    }

    private final Query parseCommit() {
        this.parseKeyword("COMMIT");
        this.parseKeywordIf("WORK", "TRAN", "TRANSACTION");
        return this.dsl.commit();
    }

    private final Query parseRollback() {
        this.parseKeyword("ROLLBACK");
        if (this.parseKeywordIf("TRAN", "TRANSACTION TO SAVEPOINT", "TRANSACTION TO", "TRANSACTION", "WORK TO SAVEPOINT", "TO SAVEPOINT", "TO")) {
            return this.dsl.rollback().toSavepoint(this.parseIdentifier());
        }
        this.parseKeywordIf("WORK");
        return this.dsl.rollback();
    }

    private final Block parseDo() {
        this.parseKeyword("DO");
        return (Block)this.dsl.parser().parseQuery(this.parseStringLiteral());
    }

    private final Statement parseStatement() {
        switch (this.characterUpper()) {
            case 'C': {
                if (!this.ignoreProEdition() && this.peekKeyword("CALL") && this.requireProEdition()) {
                    return this.parseCall();
                }
                if (this.ignoreProEdition() || !this.peekKeyword("CONTINUE") || !this.requireProEdition()) break;
                return this.parseContinueStatement();
            }
            case 'D': {
                if (!this.ignoreProEdition() && this.peekKeyword("DECLARE") && this.requireProEdition()) {
                    if (BlockImpl.SUPPORTS_DECLARE_BLOCK_KEYWORD.contains((Object)this.parseDialect())) {
                        return this.parseBlock(true);
                    }
                    return this.parseDeclareStatement();
                }
                if (this.ignoreProEdition() || !this.peekKeyword("DEFINE") || !this.requireProEdition()) break;
                return this.parseDeclareStatement();
            }
            case 'E': {
                if (!this.ignoreProEdition() && this.peekKeyword("EXECUTE PROCEDURE", "EXEC") && this.requireProEdition()) {
                    return this.parseCall();
                }
                if (!this.ignoreProEdition() && this.peekKeyword("EXECUTE") && !this.peekKeyword("EXECUTE BLOCK") && this.requireProEdition()) {
                    return this.parseExecuteStatement();
                }
                if (this.ignoreProEdition() || !this.peekKeyword("EXIT") || !this.requireProEdition()) break;
                return this.parseExitStatement();
            }
            case 'F': {
                if (this.ignoreProEdition() || !this.peekKeyword("FOR") || !this.requireProEdition()) break;
                return this.parseForStatement();
            }
            case 'G': {
                if (this.ignoreProEdition() || !this.peekKeyword("GOTO") || !this.requireProEdition()) break;
                return this.parseGotoStatement();
            }
            case 'I': {
                if (!this.ignoreProEdition() && this.peekKeyword("IF") && this.requireProEdition()) {
                    return this.parseIfStatement();
                }
                if (this.ignoreProEdition() || !this.peekKeyword("ITERATE") || !this.requireProEdition()) break;
                return this.parseContinueStatement();
            }
            case 'L': {
                if (!this.ignoreProEdition() && this.peekKeyword("LEAVE") && this.requireProEdition()) {
                    return this.parseExitStatement();
                }
                if (!this.ignoreProEdition() && this.peekKeyword("LET") && this.requireProEdition()) {
                    return this.parseAssignment();
                }
                if (this.ignoreProEdition() || !this.peekKeyword("LOOP") || !this.requireProEdition()) break;
                return this.parseLoopStatement();
            }
            case 'N': {
                if (!this.peekKeyword("NULL")) break;
                return this.parseNullStatement();
            }
            case 'R': {
                if (!this.ignoreProEdition() && this.peekKeyword("REPEAT") && this.requireProEdition()) {
                    return this.parseRepeatStatement();
                }
                if (!this.ignoreProEdition() && this.peekKeyword("RETURN") && this.requireProEdition()) {
                    return this.parseReturnStatement();
                }
                if (this.ignoreProEdition() || !this.peekKeyword("RAISE") || !this.requireProEdition()) break;
                return this.parseRaise();
            }
            case 'S': {
                if (!this.ignoreProEdition() && this.peekKeyword("SET") && this.requireProEdition()) {
                    return this.parseAssignment();
                }
                if (this.ignoreProEdition() || !this.peekKeyword("SIGNAL") || !this.requireProEdition()) break;
                return this.parseSignal();
            }
            case 'W': {
                if (this.ignoreProEdition() || !this.peekKeyword("WHILE") || !this.requireProEdition()) break;
                return this.parseWhileStatement();
            }
        }
        Statement assignment = this.parseAssignmentIf();
        if (assignment != null) {
            return assignment;
        }
        Query call = this.parseCallIf();
        if (call != null) {
            return call;
        }
        return this.parseQuery(false, false);
    }

    private final Statement parseNullStatement() {
        this.parseKeyword("NULL");
        return new NullStatement();
    }

    @Pro
    private final Statement parseIfStatement() {
        IfElseStep previous;
        this.parseKeyword("IF");
        Condition c1 = this.parseCondition();
        boolean then = this.parseKeywordIf("THEN");
        IfElseStep ifElseStep = previous = then ? DSL.if_(c1).then(this.parseStatementsAndPeek("ELIF", "ELSIF", "ELSEIF", "ELSE", "END IF")) : DSL.if_(c1).then(this.parseStatementAndSemicolonIf());
        if (then) {
            while (this.parseKeywordIf("ELIF", "ELSIF", "ELSEIF")) {
                Condition c2 = this.parseCondition();
                this.parseKeyword("THEN");
                previous = previous.elsif(c2).then(this.parseStatementsAndPeek("ELIF", "ELSIF", "ELSEIF", "ELSE", "END IF"));
            }
        }
        Statement result = this.parseKeywordIf("ELSE") ? (then ? previous.else_(this.parseStatementsAndPeek("END IF")) : previous.else_(this.parseStatementAndSemicolonIf())) : previous;
        if (then) {
            this.parseKeyword("END IF");
            this.parseLabelReferenceIf();
        }
        return result;
    }

    @Pro
    private final Statement parseGotoStatement() {
        this.parseKeyword("GOTO");
        return DSL.goto_(this.parseLabelReference());
    }

    @Pro
    private final Statement parseExecuteStatement() {
        Statement statement = this.parseExecuteStatementIf();
        if (statement == null) {
            throw this.expected("EXECUTE", "EXECUTE IMMEDIATE", "EXECUTE STATEMENT");
        }
        return statement;
    }

    @Pro
    private final Statement parseExecuteStatementIf() {
        if (this.parseKeywordIf("EXECUTE")) {
            this.parseKeywordIf("IMMEDIATE", "STATEMENT");
            return DSL.execute(this.parseField());
        }
        return null;
    }

    @Pro
    private final Query parseCall() {
        Query query = this.parseCallIf();
        if (query == null) {
            throw this.expected("CALL", "EXEC", "EXECUTE PROCEDURE", "<procedure name>");
        }
        return query;
    }

    @Pro
    private final Query parseCallIf() {
        int p = this.position();
        if (this.parseKeywordIf("CALL")) {
            switch (this.parseFamily()) {
                case H2: 
                case HSQLDB: {
                    int p2 = this.position();
                    try {
                        return DSL.call(this.parseName()).args(this.parseFieldsOrEmptyParenthesised());
                    }
                    catch (ParserException e1) {
                        this.position(p2);
                        try {
                            return DSL.select(this.parseField());
                        }
                        catch (ParserException e2) {
                            this.position(p2);
                            throw e1;
                        }
                    }
                }
            }
            return DSL.call(this.parseName()).args(this.parseFieldsOrEmptyOptionallyParenthesised());
        }
        if (this.parseKeywordIf("EXECUTE PROCEDURE")) {
            return DSL.call(this.parseName()).args(this.parseFieldsOrEmptyOptionallyParenthesised());
        }
        if (this.parseKeywordIf("EXEC")) {
            return DSL.call(this.parseName()).args(this.peekEndOfStatement() || this.peekKeyword("GO") ? Collections.emptyList() : this.parseList(',', (? super ParseContext c) -> c.parseField()));
        }
        Name name = this.parseNameIf();
        if (name != null) {
            List<Object> args2;
            List<Object> list = args2 = this.peek('(') ? this.parseFieldsOrEmptyParenthesised() : Collections.emptyList();
            if (this.peekEndOfStatement()) {
                return DSL.call(name).args(args2 == null ? Collections.emptyList() : args2);
            }
            this.position(p);
        }
        return null;
    }

    @Pro
    private final List<Field<?>> parseFieldsOrEmptyOptionallyParenthesised() {
        if (this.peek('(')) {
            return this.parseFieldsOrEmptyParenthesised();
        }
        if (this.peekEndOfStatement()) {
            return Collections.emptyList();
        }
        return this.parseList(',', (? super ParseContext c) -> c.parseField());
    }

    @Pro
    private final boolean peekEndOfStatement() {
        return this.peek(';') || this.peek('}') || this.done();
    }

    @Pro
    private final Statement parseContinueStatement() {
        this.parseKeyword("CONTINUE", "ITERATE");
        if (this.parseKeywordIf("WHEN")) {
            return DSL.continueWhen(this.parseCondition());
        }
        Label label = this.parseLabelReferenceIf();
        if (label != null) {
            if (this.parseKeywordIf("WHEN")) {
                return DSL.continue_(label).when(this.parseCondition());
            }
            return DSL.continue_(label);
        }
        return DSL.continue_();
    }

    @Pro
    private final Statement parseExitStatement() {
        this.parseKeyword("EXIT", "LEAVE");
        if (this.parseKeywordIf("WHEN")) {
            return DSL.exitWhen(this.parseCondition());
        }
        Label label = this.parseLabelReferenceIf();
        if (label != null) {
            if (this.parseKeywordIf("WHEN")) {
                return DSL.exit(label).when(this.parseCondition());
            }
            return DSL.exit(label);
        }
        return DSL.exit();
    }

    @Pro
    private final Statement parseLoopStatement() {
        this.parseKeyword("LOOP");
        List<Statement> statements = this.parseStatementsAndKeyword("END LOOP");
        this.parseLabelReferenceIf();
        return DSL.loop(statements);
    }

    @Pro
    private final Statement parseForStatement() {
        List<Statement> statements;
        ForByStep<Long> s3;
        this.parseKeyword("FOR");
        ForInStep<Long> s1 = DSL.for_(DSL.var(this.parseVariableName(), SQLDataType.BIGINT));
        this.parseKeyword("IN");
        boolean reverse = this.parseKeywordIf("REVERSE");
        Field<?> lower = this.parseField();
        this.parse("..");
        Field<?> upper = this.parseField();
        ForByStep<Long> s2 = reverse ? s1.inReverse(lower, upper) : s1.in(lower, upper);
        LoopStep loopStep = s3 = this.parseKeywordIf("BY") ? s2.by(this.parseField()) : s2;
        if (this.parseKeywordIf("LOOP")) {
            statements = this.parseStatementsAndKeyword("END LOOP");
        } else if (this.parseKeywordIf("DO")) {
            statements = this.parseStatementsAndKeyword("END FOR");
        } else {
            throw this.expected("DO", "LOOP");
        }
        return s3.loop(statements);
    }

    @Pro
    private final Statement parseWhileStatement() {
        this.parseKeyword("WHILE");
        Condition condition = this.parseCondition();
        if (this.parseKeywordIf("LOOP")) {
            return DSL.while_(condition).loop(this.parseStatementsAndKeyword("END LOOP"));
        }
        if (this.parseKeywordIf("DO")) {
            return DSL.while_(condition).loop(this.parseStatementsAndKeyword("END WHILE"));
        }
        return DSL.while_(condition).loop(this.parseStatementAndSemicolonIf());
    }

    @Pro
    private final Statement parseReturnStatement() {
        this.parseKeyword("RETURN");
        return this.peek(';') ? DSL.return_() : DSL.return_(this.parseField());
    }

    @Pro
    private final Statement parseRepeatStatement() {
        this.parseKeyword("REPEAT");
        List<Statement> statements = this.parseStatementsAndKeyword("UNTIL");
        Condition condition = this.parseCondition();
        this.parseKeyword("END REPEAT");
        return DSL.repeat(statements).until(condition);
    }

    @Pro
    private final Statement parseDeclareStatement() {
        this.parseKeyword("DECLARE", "DEFINE");
        return this.parseDeclaration();
    }

    @Pro
    private final Statement parseDeclaration() {
        if (this.parseKeywordIf("TYPE")) {
            throw this.notImplemented("Local TYPE declarations");
        }
        if (this.parseKeywordIf("FUNCTION")) {
            throw this.notImplemented("Local FUNCTION declarations");
        }
        if (this.parseKeywordIf("PROCEDURE")) {
            throw this.notImplemented("Local PROCEDURE declarations");
        }
        List<Name> names = this.parseList(',', (? super ParseContext c) -> this.parseVariableName());
        ArrayList<Statement> declarations = new ArrayList<Statement>();
        if (names.size() == 1) {
            Name name = names.get(0);
            do {
                if (!this.parseKeywordIf("CONSTANT")) {
                    this.parseKeywordIf("AS");
                }
                DataType<?> dataType = this.parseDataType();
                boolean notNull = this.parseKeywordIf("NOT NULL");
                if (!notNull) {
                    this.parseKeywordIf("NULL");
                }
                Variable<?> variable = DSL.var(name, dataType);
                if (this.scope.proceduralScope.inScope()) {
                    this.scope.proceduralScope.set(variable.getName(), variable);
                }
                Declaration<?> declaration = DSL.declare(variable);
                if (this.parseIf('=') || this.parseIf(":=") || this.parseKeywordIf("DEFAULT")) {
                    declarations.add(declaration.set(this.parseField()));
                    continue;
                }
                if (notNull) {
                    throw this.expected("Default value for non null variables");
                }
                declarations.add(declaration);
            } while (this.parseIf(',') && (name = this.parseVariableName()) != null);
        } else {
            this.parseKeywordIf("AS");
            DataType<?> dataType = this.parseDataType();
            Field<?> defaultValue = this.parseIf('=') || this.parseKeywordIf("DEFAULT") ? this.parseField() : null;
            for (Name name : names) {
                Variable<?> variable = DSL.var(name, dataType);
                if (this.scope.proceduralScope.inScope()) {
                    this.scope.proceduralScope.set(variable.getName(), variable);
                }
                Declaration<?> declare = DSL.declare(variable);
                if (defaultValue != null) {
                    declarations.add(declare.set(defaultValue));
                    continue;
                }
                declarations.add(declare);
            }
        }
        if (declarations.size() == 1) {
            return (Statement)declarations.get(0);
        }
        return DSL.statements(declarations);
    }

    @Pro
    private final Statement parseAssignment() {
        Statement result = this.parseAssignmentIf();
        if (result == null) {
            throw this.expected("Assignment");
        }
        return result;
    }

    @Pro
    private final Statement parseAssignmentIf() {
        int p = this.position();
        if (this.parseKeywordIf("SET", "LET")) {
            if (this.peek('(')) {
                Row row = this.parseTuple();
                this.parse('=');
                return this.peekSelectOrWith(true) ? new RowAssignment(row, this.parseParenthesised(c -> this.parseWithOrSelect(row.size()))) : new RowAssignment(row, this.parseTuple(row.size()));
            }
            Name name = this.parseVariableName();
            if (this.parseIf('=') || this.parseIf(":=")) {
                Field<?> field2 = this.parseField();
                return DSL.var(name, field2.getDataType()).set(field2);
            }
        } else {
            Name name = this.parseVariableNameIf();
            if (name != null && (this.parseIf('=') || this.parseIf(":="))) {
                Field<?> field3 = this.parseField();
                return DSL.var(name, field3.getDataType()).set(field3);
            }
        }
        this.position(p);
        return null;
    }

    @Pro
    private final Statement parseRaise() {
        Statement result = this.parseRaiseIf();
        if (result == null) {
            throw this.expected("Raise");
        }
        return result;
    }

    @Pro
    private final Statement parseRaiseIf() {
        if (this.parseKeywordIf("RAISE")) {
            this.parseKeywordIf("EXCEPTION");
            this.parseKeyword("SQLSTATE");
            Field<?> value = this.parseField();
            Field<?> messageText = this.parseKeywordIf("USING") && this.parseKeyword("MESSAGE") && this.parse('=') ? this.parseField() : null;
            return messageText == null ? DSL.signalSQLState(value) : DSL.signalSQLState(value).setMessageText(messageText);
        }
        return null;
    }

    @Pro
    private final Statement parseSignal() {
        Statement result = this.parseSignalIf();
        if (result == null) {
            throw this.expected("Signal");
        }
        return result;
    }

    @Pro
    private final Statement parseSignalIf() {
        if (this.parseKeywordIf("SIGNAL")) {
            this.parseKeyword("SQLSTATE");
            this.parseKeywordIf("VALUE");
            Field<?> value = this.parseField();
            Field<String> messageText = null;
            if (this.parseKeywordIf("SET")) {
                this.parseKeyword("MESSAGE_TEXT");
                this.parse('=');
                messageText = this.parseField();
            } else if (this.parseIf('(')) {
                messageText = this.parseField();
                this.parse(')');
            }
            return messageText == null ? DSL.signalSQLState(value) : DSL.signalSQLState(value).setMessageText(messageText);
        }
        return null;
    }

    @Pro
    private final Name parseVariableNameIf() {
        this.parseIf('@');
        return this.parseNameIf();
    }

    @Pro
    private final Name parseVariableName() {
        Name name = this.parseVariableNameIf();
        if (name == null) {
            throw this.expected("Variable name");
        }
        return name;
    }

    private final Privilege parsePrivilege() {
        if (this.parseKeywordIf("SELECT")) {
            return DSL.privilege(Keywords.K_SELECT);
        }
        if (this.parseKeywordIf("INSERT")) {
            return DSL.privilege(Keywords.K_INSERT);
        }
        if (this.parseKeywordIf("UPDATE")) {
            return DSL.privilege(Keywords.K_UPDATE);
        }
        if (this.parseKeywordIf("DELETE")) {
            return DSL.privilege(Keywords.K_DELETE);
        }
        throw this.expected("DELETE", "INSERT", "SELECT", "UPDATE");
    }

    private final User parseUser() {
        return DSL.user(this.parseName());
    }

    private final DDLQuery parseCreateView(boolean orReplace) {
        boolean ifNotExists = !orReplace && this.parseKeywordIf("IF NOT EXISTS");
        Table<?> view = this.parseTableName();
        Field<?>[] fields = Tools.EMPTY_FIELD;
        if (this.parseIf('(')) {
            fields = this.parseList(',', (? super ParseContext c) -> this.parseFieldName()).toArray(fields);
            this.parse(')');
        }
        this.parseKeyword("AS");
        SelectQueryImpl<Record> select = this.parseWithOrSelect();
        int degree = Tools.degree(select);
        if (fields.length > 0 && fields.length != degree) {
            throw this.exception("Select list size (" + degree + ") must match declared field size (" + fields.length + ")");
        }
        return ifNotExists ? this.dsl.createViewIfNotExists(view, fields).as(select) : (orReplace ? this.dsl.createOrReplaceView(view, fields).as(select) : this.dsl.createView(view, fields).as(select));
    }

    private final DDLQuery parseCreateExtension() {
        this.parseKeywordIf("IF NOT EXISTS");
        this.parseIdentifier();
        this.parseKeywordIf("WITH");
        if (this.parseKeywordIf("SCHEMA")) {
            this.parseIdentifier();
        }
        if (this.parseKeywordIf("VERSION") && this.parseIdentifierIf() == null) {
            this.parseStringLiteral();
        }
        if (this.parseKeywordIf("FROM") && this.parseIdentifierIf() == null) {
            this.parseStringLiteral();
        }
        this.parseKeywordIf("CASCADE");
        return IGNORE.get();
    }

    private final DDLQuery parseDropExtension() {
        boolean ifExists = this.parseKeywordIf("IF EXISTS");
        this.parseIdentifiers();
        boolean bl = ifExists = ifExists || this.parseKeywordIf("IF EXISTS");
        if (!this.parseKeywordIf("CASCADE")) {
            this.parseKeywordIf("RESTRICT");
        }
        return IGNORE.get();
    }

    private final DDLQuery parseAlterView() {
        boolean ifExists = this.parseKeywordIf("IF EXISTS");
        Table<?> oldName = this.parseTableName();
        Field<?>[] fields = Tools.EMPTY_FIELD;
        if (this.parseIf('(')) {
            fields = this.parseList(',', (? super ParseContext c) -> this.parseFieldName()).toArray(fields);
            this.parse(')');
        }
        if (this.parseKeywordIf("AS")) {
            SelectQueryImpl<Record> select = this.parseWithOrSelect();
            int degree = Tools.degree(select);
            if (fields.length > 0 && fields.length != degree) {
                throw this.exception("Select list size (" + degree + ") must match declared field size (" + fields.length + ")");
            }
            if (fields.length == 0) {
                return this.dsl.alterView(oldName).as(select);
            }
            return this.dsl.alterView(oldName, fields).as(select);
        }
        if (fields.length > 0) {
            throw this.expected("AS");
        }
        if (this.parseKeywordIf("RENAME")) {
            this.parseKeyword("AS", "TO");
            Table<?> newName = this.parseTableName();
            return ifExists ? this.dsl.alterViewIfExists(oldName).renameTo(newName) : this.dsl.alterView(oldName).renameTo(newName);
        }
        if (this.parseKeywordIf("OWNER TO") && this.parseUser() != null) {
            return IGNORE.get();
        }
        if (this.parseKeywordIf("SET")) {
            return this.dsl.alterView(oldName).comment(this.parseOptionsDescription());
        }
        throw this.expected("AS", "OWNER TO", "RENAME", "SET");
    }

    private final Comment parseOptionsDescription() {
        this.parseKeyword("OPTIONS");
        this.parse('(');
        this.parseKeyword("DESCRIPTION");
        this.parse('=');
        Comment comment = this.parseComment();
        this.parse(')');
        return comment;
    }

    private final DDLQuery parseDropView() {
        return this.parseIfExists(this::parseTableName, this.dsl::dropViewIfExists, this.dsl::dropView);
    }

    private final DDLQuery parseCreateSequence() {
        boolean ifNotExists = this.parseKeywordIf("IF NOT EXISTS");
        Sequence<?> schemaName = this.parseSequenceName();
        CreateSequenceFlagsStep s2 = ifNotExists ? this.dsl.createSequenceIfNotExists(schemaName) : this.dsl.createSequence(schemaName);
        boolean startWith = false;
        boolean incrementBy = false;
        boolean minvalue = false;
        boolean maxvalue = false;
        boolean cycle = false;
        boolean cache = false;
        while (true) {
            Field<Long> field2;
            if (!startWith && (startWith |= (field2 = this.parseSequenceStartWithIf()) != null)) {
                s2 = s2.startWith(field2);
                continue;
            }
            if (!incrementBy && (incrementBy |= (field2 = this.parseSequenceIncrementByIf()) != null)) {
                s2 = s2.incrementBy(field2);
                continue;
            }
            if (!minvalue && (minvalue |= (field2 = this.parseSequenceMinvalueIf()) != null)) {
                s2 = s2.minvalue(field2);
                continue;
            }
            if (!minvalue && (minvalue |= this.parseSequenceNoMinvalueIf())) {
                s2 = s2.noMinvalue();
                continue;
            }
            if (!maxvalue && (maxvalue |= (field2 = this.parseSequenceMaxvalueIf()) != null)) {
                s2 = s2.maxvalue(field2);
                continue;
            }
            if (!maxvalue && (maxvalue |= this.parseSequenceNoMaxvalueIf())) {
                s2 = s2.noMaxvalue();
                continue;
            }
            if (!cycle && (cycle |= this.parseKeywordIf("CYCLE"))) {
                s2 = s2.cycle();
                continue;
            }
            if (!cycle && (cycle |= this.parseSequenceNoCycleIf())) {
                s2 = s2.noCycle();
                continue;
            }
            if (!cache && (cache |= (field2 = this.parseSequenceCacheIf()) != null)) {
                s2 = s2.cache(field2);
                continue;
            }
            if (cache || !(cache |= this.parseSequenceNoCacheIf())) break;
            s2 = s2.noCache();
        }
        return s2;
    }

    private final DDLQuery parseAlterSequence() {
        AlterSequenceStep<?> s2;
        boolean ifExists = this.parseKeywordIf("IF EXISTS");
        Sequence<?> sequenceName = this.parseSequenceName();
        AlterSequenceStep<?> alterSequenceStep = s2 = ifExists ? this.dsl.alterSequenceIfExists(sequenceName) : this.dsl.alterSequence(sequenceName);
        if (this.parseKeywordIf("RENAME")) {
            this.parseKeyword("AS", "TO");
            return s2.renameTo(this.parseSequenceName());
        }
        if (this.parseKeywordIf("OWNER TO") && this.parseUser() != null) {
            return IGNORE.get();
        }
        boolean found = false;
        boolean restart = false;
        boolean startWith = false;
        boolean incrementBy = false;
        boolean minvalue = false;
        boolean maxvalue = false;
        boolean cycle = false;
        boolean cache = false;
        AlterSequenceFlagsStep<Object> s1 = s2;
        while (true) {
            Field<Long> field2;
            if (!startWith && (startWith |= (field2 = this.parseSequenceStartWithIf()) != null)) {
                s1 = s1.startWith(field2);
            } else if (!incrementBy && (incrementBy |= (field2 = this.parseSequenceIncrementByIf()) != null)) {
                s1 = s1.incrementBy(field2);
            } else if (!minvalue && (minvalue |= (field2 = this.parseSequenceMinvalueIf()) != null)) {
                s1 = s1.minvalue(field2);
            } else if (!minvalue && (minvalue |= this.parseSequenceNoMinvalueIf())) {
                s1 = s1.noMinvalue();
            } else if (!maxvalue && (maxvalue |= (field2 = this.parseSequenceMaxvalueIf()) != null)) {
                s1 = s1.maxvalue(field2);
            } else if (!maxvalue && (maxvalue |= this.parseSequenceNoMaxvalueIf())) {
                s1 = s1.noMaxvalue();
            } else if (!cycle && (cycle |= this.parseKeywordIf("CYCLE"))) {
                s1 = s1.cycle();
            } else if (!cycle && (cycle |= this.parseSequenceNoCycleIf())) {
                s1 = s1.noCycle();
            } else if (!cache && (cache |= (field2 = this.parseSequenceCacheIf()) != null)) {
                s1 = s1.cache(field2);
            } else if (!cache && (cache |= this.parseSequenceNoCacheIf())) {
                s1 = s1.noCache();
            } else {
                if (restart || !(restart |= this.parseKeywordIf("RESTART"))) break;
                s1 = this.parseKeywordIf("WITH") ? s1.restartWith(this.parseUnsignedIntegerOrBindVariable()) : s1.restart();
            }
            found = true;
        }
        if (!found) {
            throw this.expected("CACHE", "CYCLE", "INCREMENT BY", "MAXVALUE", "MINVALUE", "NO CACHE", "NO CYCLE", "NO MAXVALUE", "NO MINVALUE", "OWNER TO", "RENAME TO", "RESTART", "START WITH");
        }
        return s1;
    }

    private final boolean parseSequenceNoCacheIf() {
        return this.parseKeywordIf("NO CACHE", "NOCACHE");
    }

    private final Field<Long> parseSequenceCacheIf() {
        Field<Long> field2;
        if (this.parseKeywordIf("CACHE")) {
            if (!this.parseIf("=")) {
                // empty if block
            }
            field2 = this.parseUnsignedIntegerOrBindVariable();
        } else {
            field2 = null;
        }
        return field2;
    }

    private final boolean parseSequenceNoCycleIf() {
        return this.parseKeywordIf("NO CYCLE", "NOCYCLE");
    }

    private final boolean parseSequenceNoMaxvalueIf() {
        return this.parseKeywordIf("NO MAXVALUE", "NOMAXVALUE");
    }

    private final Field<Long> parseSequenceMaxvalueIf() {
        Field<Long> field2;
        if (this.parseKeywordIf("MAXVALUE")) {
            if (!this.parseIf("=")) {
                // empty if block
            }
            field2 = this.parseUnsignedIntegerOrBindVariable();
        } else {
            field2 = null;
        }
        return field2;
    }

    private final boolean parseSequenceNoMinvalueIf() {
        return this.parseKeywordIf("NO MINVALUE", "NOMINVALUE");
    }

    private final Field<Long> parseSequenceMinvalueIf() {
        Field<Long> field2;
        if (this.parseKeywordIf("MINVALUE")) {
            if (!this.parseIf("=")) {
                // empty if block
            }
            field2 = this.parseUnsignedIntegerOrBindVariable();
        } else {
            field2 = null;
        }
        return field2;
    }

    private final Field<Long> parseSequenceIncrementByIf() {
        Field<Long> field2;
        if (this.parseKeywordIf("INCREMENT")) {
            if (this.parseKeywordIf("BY") || !this.parseIf("=")) {
                // empty if block
            }
            field2 = this.parseUnsignedIntegerOrBindVariable();
        } else {
            field2 = null;
        }
        return field2;
    }

    private final Field<Long> parseSequenceStartWithIf() {
        Field<Long> field2;
        if (this.parseKeywordIf("START")) {
            if (this.parseKeywordIf("WITH") || !this.parseIf("=")) {
                // empty if block
            }
            field2 = this.parseUnsignedIntegerOrBindVariable();
        } else {
            field2 = null;
        }
        return field2;
    }

    private final Query parseAlterSession() {
        this.parseKeyword("SET CURRENT_SCHEMA");
        this.parse('=');
        return this.dsl.setSchema(this.parseSchemaName());
    }

    private final DDLQuery parseSetGenerator() {
        Sequence<?> sequenceName = this.parseSequenceName();
        this.parseKeyword("TO");
        return this.dsl.alterSequence(sequenceName).restartWith(this.parseUnsignedIntegerLiteral());
    }

    private final DDLQuery parseDropSequence() {
        boolean ifExists = this.parseKeywordIf("IF EXISTS");
        Sequence<?> sequenceName = this.parseSequenceName();
        ifExists = ifExists || this.parseKeywordIf("IF EXISTS");
        this.parseKeywordIf("RESTRICT");
        return ifExists ? this.dsl.dropSequenceIfExists(sequenceName) : this.dsl.dropSequence(sequenceName);
    }

    /*
     * WARNING - void declaration
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private final DDLQuery parseCreateTable(boolean temporary) {
        CreateTableCommentStep storageStep;
        void var4_25;
        Function<CreateTableOnCommitStep, CreateTableCommentStep> onCommit;
        CreateTableElementListStep asStep;
        CreateTableElementListStep elementListStep;
        boolean ifNotExists = this.parseKeywordIf("IF NOT EXISTS");
        Table<Record> tableName = DSL.table(this.parseTableName().getQualifiedName());
        if (this.parseKeywordIf("USING")) {
            this.parseIdentifier();
        }
        ArrayList fields = new ArrayList();
        ArrayList<Constraint> constraints = new ArrayList<Constraint>();
        ArrayList<Index> indexes = new ArrayList<Index>();
        boolean primary = false;
        boolean identity = false;
        boolean readonly = false;
        boolean ctas = false;
        if (!this.peekSelectOrWith(true) && this.parseIf('(')) {
            do {
                int p = this.position();
                ConstraintTypeStep constraint = this.parseConstraintNameSpecification();
                if (this.parsePrimaryKeyClusteredNonClusteredKeywordIf()) {
                    if (primary) {
                        throw this.exception("Duplicate primary key specification");
                    }
                    primary = true;
                    constraints.add(this.parsePrimaryKeySpecification(constraint));
                    continue;
                }
                if (this.parseKeywordIf("UNIQUE")) {
                    if (!this.parseKeywordIf("KEY")) {
                        this.parseKeywordIf("INDEX");
                    }
                    this.parseUsingIndexTypeIf();
                    Name index = this.parseIdentifierIf();
                    if (index != null) {
                        constraint = DSL.constraint(index);
                    }
                    constraints.add(this.parseUniqueSpecification(constraint));
                    continue;
                }
                if (this.parseKeywordIf("FOREIGN KEY")) {
                    constraints.add(this.parseForeignKeySpecification(constraint));
                    continue;
                }
                if (this.parseKeywordIf("CHECK")) {
                    constraints.add(this.parseCheckSpecification(constraint));
                    continue;
                }
                if (constraint == null && this.parseIndexOrKeyIf()) {
                    this.parseUsingIndexTypeIf();
                    int p2 = this.position();
                    if (this.parseIf('(') || this.parseDataTypeIf(false) == null && this.parseIdentifierIf() != null && this.parseUsingIndexTypeIf() && this.parseIf('(')) {
                        this.position(p2);
                        indexes.add(this.parseIndexSpecification(tableName));
                        this.parseUsingIndexTypeIf();
                        continue;
                    }
                    this.position(p);
                } else if (constraint != null) {
                    throw this.expected("CHECK", "CONSTRAINT", "FOREIGN KEY", "INDEX", "KEY", "PRIMARY KEY", "UNIQUE");
                }
                Name fieldName = this.parseIdentifier();
                boolean skipType = this.peek(',') || this.peek(')');
                ParseInlineConstraints inlineConstraints = this.parseInlineConstraints(fieldName, !skipType ? this.parseDataType() : SQLDataType.OTHER, constraints, primary, identity, readonly);
                primary = inlineConstraints.primary;
                identity = inlineConstraints.identity;
                fields.add(DSL.field(fieldName, inlineConstraints.type, inlineConstraints.fieldComment));
            } while (this.parseIf(','));
            if (fields.isEmpty()) {
                throw this.expected("At least one column");
            }
            this.parse(')');
        } else {
            ctas = true;
        }
        CreateTableElementListStep createTableElementListStep = ifNotExists ? (temporary ? this.dsl.createTemporaryTableIfNotExists(tableName) : this.dsl.createTableIfNotExists(tableName)) : (elementListStep = temporary ? this.dsl.createTemporaryTable(tableName) : this.dsl.createTable(tableName));
        if (!fields.isEmpty()) {
            elementListStep = elementListStep.columns(fields);
        }
        CreateTableElementListStep constraintStep = constraints.isEmpty() ? elementListStep : elementListStep.constraints(constraints);
        CreateTableElementListStep createTableElementListStep2 = asStep = indexes.isEmpty() ? constraintStep : constraintStep.indexes(indexes);
        if (temporary && this.parseKeywordIf("ON COMMIT")) {
            if (this.parseKeywordIf("DELETE ROWS")) {
                onCommit = CreateTableOnCommitStep::onCommitDeleteRows;
            } else if (this.parseKeywordIf("DROP")) {
                onCommit = CreateTableOnCommitStep::onCommitDrop;
            } else {
                if (!this.parseKeywordIf("PRESERVE ROWS")) throw this.unsupportedClause();
                onCommit = CreateTableOnCommitStep::onCommitPreserveRows;
            }
        } else {
            onCommit = s2 -> s2;
        }
        if (this.parseKeywordIf("AS") || fields.isEmpty() && this.peekSelectOrWith(true)) {
            boolean previousMetaLookupsForceIgnore = this.metaLookupsForceIgnore();
            CreateTableWithDataStep withDataStep = asStep.as((Select)this.metaLookupsForceIgnore(false).parseQuery(true, true));
            this.metaLookupsForceIgnore(previousMetaLookupsForceIgnore);
            CreateTableOnCommitStep createTableOnCommitStep = this.parseKeywordIf("WITH DATA") ? withDataStep.withData() : (this.parseKeywordIf("WITH NO DATA") ? withDataStep.withNoData() : withDataStep);
        } else {
            if (ctas) {
                throw this.expected("AS, WITH, SELECT, or (");
            }
            CreateTableElementListStep createTableElementListStep3 = asStep;
            if (this.parseKeywordIf("STRICT", "WITHOUT ROWID") && this.parseIf(',')) {
                this.parseKeyword("STRICT", "WITHOUT ROWID");
            }
        }
        CreateTableCommentStep commentStep = onCommit.apply((CreateTableOnCommitStep)var4_25);
        ArrayList<SQL> storage = new ArrayList<SQL>();
        Comment comment = null;
        boolean first = true;
        while (true) {
            boolean optional = first || !this.parseIf(',');
            Keyword keyword = null;
            keyword = this.parseAndGetKeywordIf("AUTO_INCREMENT");
            if (keyword != null) {
                this.parseIf('=');
                storage.add(DSL.sql("{0} {1}", keyword, this.parseFieldUnsignedNumericLiteral(Sign.NONE)));
            } else {
                keyword = this.parseAndGetKeywordIf("AVG_ROW_LENGTH");
                if (keyword != null) {
                    this.parseIf('=');
                    storage.add(DSL.sql("{0} {1}", keyword, this.parseFieldUnsignedNumericLiteral(Sign.NONE)));
                } else {
                    keyword = this.parseAndGetKeywordIf("CHARACTER SET");
                    if (keyword != null) {
                        this.parseIf('=');
                        storage.add(DSL.sql("{0} {1}", keyword, this.parseIdentifier()));
                    } else {
                        keyword = this.parseAndGetKeywordIf("DEFAULT CHARACTER SET");
                        if (keyword != null || (keyword = this.parseAndGetKeywordIf("DEFAULT CHARSET")) != null) {
                            this.parseIf('=');
                            storage.add(DSL.sql("{0} {1}", keyword, this.parseIdentifier()));
                        } else {
                            keyword = this.parseAndGetKeywordIf("CHECKSUM");
                            if (keyword != null) {
                                this.parseIf('=');
                                storage.add(DSL.sql("{0} {1}", keyword, this.parseZeroOne()));
                            } else {
                                keyword = this.parseAndGetKeywordIf("COLLATE");
                                if (keyword != null) {
                                    this.parseIf('=');
                                    storage.add(DSL.sql("{0} {1}", keyword, this.parseIdentifier()));
                                } else {
                                    keyword = this.parseAndGetKeywordIf("DEFAULT COLLATE");
                                    if (keyword != null) {
                                        this.parseIf('=');
                                        storage.add(DSL.sql("{0} {1}", keyword, this.parseIdentifier()));
                                    } else if (!this.peekKeyword("COMMENT ON") && this.parseKeywordIf("COMMENT")) {
                                        if (!this.parseIf('=')) {
                                            this.parseKeywordIf("IS");
                                        }
                                        comment = this.parseComment();
                                    } else if (this.peekKeyword("OPTIONS")) {
                                        comment = this.parseOptionsDescription();
                                    } else {
                                        keyword = this.parseAndGetKeywordIf("COMPRESSION");
                                        if (keyword != null) {
                                            this.parseIf('=');
                                            storage.add(DSL.sql("{0} {1}", keyword, this.parseStringLiteral()));
                                        } else {
                                            keyword = this.parseAndGetKeywordIf("CONNECTION");
                                            if (keyword != null) {
                                                this.parseIf('=');
                                                storage.add(DSL.sql("{0} {1}", keyword, this.parseStringLiteral()));
                                            } else {
                                                keyword = this.parseAndGetKeywordIf("DATA DIRECTORY");
                                                if (keyword != null) {
                                                    this.parseIf('=');
                                                    storage.add(DSL.sql("{0} {1}", keyword, this.parseStringLiteral()));
                                                } else {
                                                    keyword = this.parseAndGetKeywordIf("INDEX DIRECTORY");
                                                    if (keyword != null) {
                                                        this.parseIf('=');
                                                        storage.add(DSL.sql("{0} {1}", keyword, this.parseStringLiteral()));
                                                    } else {
                                                        keyword = this.parseAndGetKeywordIf("DELAY_KEY_WRITE");
                                                        if (keyword != null) {
                                                            this.parseIf('=');
                                                            storage.add(DSL.sql("{0} {1}", keyword, this.parseZeroOne()));
                                                        } else {
                                                            keyword = this.parseAndGetKeywordIf("ENCRYPTION");
                                                            if (keyword != null) {
                                                                this.parseIf('=');
                                                                storage.add(DSL.sql("{0} {1}", keyword, this.parseStringLiteral()));
                                                            } else {
                                                                keyword = this.parseAndGetKeywordIf("ENGINE");
                                                                if (keyword != null) {
                                                                    this.parseIf('=');
                                                                    storage.add(DSL.sql("{0} {1}", keyword, this.parseIdentifier()));
                                                                } else {
                                                                    keyword = this.parseAndGetKeywordIf("INSERT_METHOD");
                                                                    if (keyword != null) {
                                                                        this.parseIf('=');
                                                                        storage.add(DSL.sql("{0} {1}", keyword, this.parseAndGetKeyword("NO", "FIRST", "LAST")));
                                                                    } else {
                                                                        keyword = this.parseAndGetKeywordIf("KEY_BLOCK_SIZE");
                                                                        if (keyword != null) {
                                                                            this.parseIf('=');
                                                                            storage.add(DSL.sql("{0} {1}", keyword, this.parseFieldUnsignedNumericLiteral(Sign.NONE)));
                                                                        } else {
                                                                            keyword = this.parseAndGetKeywordIf("MAX_ROWS");
                                                                            if (keyword != null) {
                                                                                this.parseIf('=');
                                                                                storage.add(DSL.sql("{0} {1}", keyword, this.parseFieldUnsignedNumericLiteral(Sign.NONE)));
                                                                            } else {
                                                                                keyword = this.parseAndGetKeywordIf("MIN_ROWS");
                                                                                if (keyword != null) {
                                                                                    this.parseIf('=');
                                                                                    storage.add(DSL.sql("{0} {1}", keyword, this.parseFieldUnsignedNumericLiteral(Sign.NONE)));
                                                                                } else {
                                                                                    keyword = this.parseAndGetKeywordIf("PACK_KEYS");
                                                                                    if (keyword != null) {
                                                                                        this.parseIf('=');
                                                                                        storage.add(DSL.sql("{0} {1}", keyword, this.parseZeroOneDefault()));
                                                                                    } else {
                                                                                        keyword = this.parseAndGetKeywordIf("PASSWORD");
                                                                                        if (keyword != null) {
                                                                                            this.parseIf('=');
                                                                                            storage.add(DSL.sql("{0} {1}", keyword, this.parseStringLiteral()));
                                                                                        } else {
                                                                                            keyword = this.parseAndGetKeywordIf("ROW_FORMAT");
                                                                                            if (keyword != null) {
                                                                                                this.parseIf('=');
                                                                                                storage.add(DSL.sql("{0} {1}", keyword, this.parseAndGetKeyword("DEFAULT", "DYNAMIC", "FIXED", "COMPRESSED", "REDUNDANT", "COMPACT")));
                                                                                            } else {
                                                                                                keyword = this.parseAndGetKeywordIf("STATS_AUTO_RECALC");
                                                                                                if (keyword != null) {
                                                                                                    this.parseIf('=');
                                                                                                    storage.add(DSL.sql("{0} {1}", keyword, this.parseZeroOneDefault()));
                                                                                                } else {
                                                                                                    keyword = this.parseAndGetKeywordIf("STATS_PERSISTENT");
                                                                                                    if (keyword != null) {
                                                                                                        this.parseIf('=');
                                                                                                        storage.add(DSL.sql("{0} {1}", keyword, this.parseZeroOneDefault()));
                                                                                                    } else {
                                                                                                        keyword = this.parseAndGetKeywordIf("STATS_SAMPLE_PAGES");
                                                                                                        if (keyword != null) {
                                                                                                            this.parseIf('=');
                                                                                                            storage.add(DSL.sql("{0} {1}", keyword, this.parseFieldUnsignedNumericLiteral(Sign.NONE)));
                                                                                                        } else {
                                                                                                            keyword = this.parseAndGetKeywordIf("TABLESPACE");
                                                                                                            if (keyword != null) {
                                                                                                                storage.add(DSL.sql("{0} {1}", keyword, this.parseIdentifier()));
                                                                                                                keyword = this.parseAndGetKeywordIf("STORAGE");
                                                                                                                if (keyword != null) {
                                                                                                                    storage.add(DSL.sql("{0} {1}", keyword, this.parseAndGetKeyword("DISK", "MEMORY", "DEFAULT")));
                                                                                                                }
                                                                                                            } else {
                                                                                                                keyword = this.parseAndGetKeywordIf("UNION");
                                                                                                                if (keyword != null) {
                                                                                                                    this.parseIf('=');
                                                                                                                    this.parse('(');
                                                                                                                    storage.add(DSL.sql("{0} ({1})", keyword, DSL.list(this.parseIdentifiers())));
                                                                                                                    this.parse(')');
                                                                                                                } else {
                                                                                                                    if (optional) break;
                                                                                                                    throw this.expected("storage clause after ','");
                                                                                                                }
                                                                                                            }
                                                                                                        }
                                                                                                    }
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            first = false;
        }
        CreateTableStorageStep createTableStorageStep = storageStep = comment != null ? commentStep.comment(comment) : commentStep;
        if (storage.size() <= 0) return storageStep;
        return storageStep.storage(new SQLConcatenationImpl(storage.toArray(Tools.EMPTY_QUERYPART)));
    }

    private final ParseInlineConstraints parseInlineConstraints(Name fieldName, DataType<?> type, List<? super Constraint> constraints, boolean primary, boolean identity, boolean readonly) {
        boolean nullable = false;
        boolean defaultValue = false;
        boolean computed = false;
        boolean onUpdate = false;
        boolean unique = false;
        Constraint uniqueConstraint = null;
        boolean comment = false;
        boolean compress = false;
        boolean sparse = false;
        Comment fieldComment = null;
        identity |= type.identity();
        readonly |= type.readonly();
        while (true) {
            block41: {
                block42: {
                    block43: {
                        if (!nullable) {
                            if (this.parseKeywordIf("NULL")) {
                                type = type.nullable(true);
                                nullable = true;
                                continue;
                            }
                            if (this.parseNotNullOptionalEnable()) {
                                type = type.nullable(false);
                                nullable = true;
                                continue;
                            }
                        }
                        if (defaultValue) break block41;
                        if (!identity && this.parseKeywordIf("IDENTITY")) {
                            if (this.parseIf('(')) {
                                this.parseSignedIntegerLiteral();
                                this.parse(',');
                                this.parseSignedIntegerLiteral();
                                this.parse(')');
                            }
                            type = type.identity(true);
                            defaultValue = true;
                            identity = true;
                            continue;
                        }
                        if (!this.ignoreProEdition() && this.parseKeywordIf("READONLY") && this.requireProEdition()) {
                            type = type.readonly(true);
                            continue;
                        }
                        if (this.parseKeywordIf("DEFAULT")) {
                            if (this.parseSerialIf()) {
                                type = type.identity(true);
                                continue;
                            }
                            this.parseKeywordIf("ON NULL");
                            type = type.defaultValue(this.toField(this.parseConcat()));
                            this.parseKeywordIf("WITH VALUES");
                            defaultValue = true;
                            continue;
                        }
                        if (computed || this.ignoreProEdition()) break block42;
                        if (this.parseKeywordIf("AS")) break block43;
                        if (!this.parseKeywordIf("COMPUTED")) break block42;
                        if (!this.parseKeywordIf("BY")) {
                            // empty if block
                        }
                    }
                    if (this.requireProEdition()) {
                        type = this.parseGeneratedExpression(type);
                        computed = true;
                        defaultValue = true;
                        continue;
                    }
                }
                if (!(identity && computed || !this.parseKeywordIf("GENERATED"))) {
                    boolean always = this.parseKeywordIf("ALWAYS");
                    if (!always) {
                        this.parseKeyword("BY DEFAULT");
                        this.parseKeywordIf("ON NULL");
                    }
                    if (always ? this.parseKeywordIf("AS IDENTITY") : this.parseKeyword("AS IDENTITY")) {
                        this.parseIdentityOptionIf();
                        type = type.identity(true);
                        identity = true;
                    } else if (!this.ignoreProEdition() && this.parseKeyword("AS") && this.requireProEdition()) {
                        type = this.parseGeneratedExpression(type);
                        computed = true;
                        defaultValue = true;
                    }
                    defaultValue = true;
                    continue;
                }
            }
            if (!onUpdate && this.parseKeywordIf("ON UPDATE")) {
                this.parseConcat();
                onUpdate = true;
                continue;
            }
            ConstraintTypeStep inlineConstraint = this.parseConstraintNameSpecification();
            if (!primary && this.parsePrimaryKeyClusteredNonClusteredKeywordIf()) {
                constraints.add(this.parseConstraintEnforcementIf(inlineConstraint == null ? DSL.primaryKey(fieldName) : inlineConstraint.primaryKey(fieldName)));
                if (uniqueConstraint != null) {
                    constraints.remove(uniqueConstraint);
                }
                primary = true;
                unique = true;
                continue;
            }
            if (this.parseKeywordIf("UNIQUE")) {
                if (!this.parseKeywordIf("KEY")) {
                    this.parseKeywordIf("INDEX");
                }
                if (!unique) {
                    uniqueConstraint = this.parseConstraintEnforcementIf(inlineConstraint == null ? DSL.unique(fieldName) : inlineConstraint.unique(fieldName));
                    constraints.add(uniqueConstraint);
                }
                unique = true;
                continue;
            }
            if (this.parseKeywordIf("CHECK")) {
                constraints.add(this.parseCheckSpecification(inlineConstraint));
                continue;
            }
            if (this.parseKeywordIf("FOREIGN KEY REFERENCES", "REFERENCES")) {
                constraints.add(this.parseForeignKeyReferenceSpecification(inlineConstraint, new Field[]{DSL.field(fieldName)}));
                continue;
            }
            if (!nullable) {
                if (this.parseKeywordIf("NULL")) {
                    type = type.nullable(true);
                    nullable = true;
                    continue;
                }
                if (this.parseNotNullOptionalEnable()) {
                    type = type.nullable(false);
                    nullable = true;
                    continue;
                }
            }
            if (inlineConstraint != null) {
                throw this.expected("CHECK", "NOT NULL", "NULL", "PRIMARY KEY", "REFERENCES", "UNIQUE");
            }
            if (!identity && (this.parseKeywordIf("AUTO_INCREMENT") || this.parseKeywordIf("AUTOINCREMENT"))) {
                type = type.identity(true);
                identity = true;
                continue;
            }
            if (!comment) {
                if (!this.peekKeyword("COMMENT ON") && this.parseKeywordIf("COMMENT")) {
                    if (!this.parseIf('=')) {
                        this.parseKeywordIf("IS");
                    }
                    fieldComment = this.parseComment();
                    comment = true;
                    continue;
                }
                if (this.peekKeyword("OPTIONS")) {
                    fieldComment = this.parseOptionsDescription();
                    comment = true;
                    continue;
                }
            }
            if (!compress) {
                if (!this.ignoreProEdition() && this.parseKeywordIf("NO COMPRESS") && this.requireProEdition()) {
                    compress = true;
                    continue;
                }
                if (!this.ignoreProEdition() && this.parseKeywordIf("COMPRESS") && this.requireProEdition()) {
                    if (this.parseIf('(')) {
                        this.parseList(',', (? super ParseContext c) -> c.parseField());
                        this.parse(')');
                    } else {
                        this.parseSignedIntegerLiteralIf();
                    }
                    compress = true;
                    continue;
                }
            }
            if (sparse || this.ignoreProEdition() || !this.parseKeywordIf("SPARSE") || !this.requireProEdition()) break;
        }
        return new ParseInlineConstraints(type, fieldComment, primary, identity, readonly);
    }

    @Pro
    private final DataType<?> parseGeneratedExpression(DataType<?> type) {
        type = type.generatedAlwaysAs(this.parseField());
        if (this.parseKeywordIf("STORED", "PERSISTED", "PERSISTENT")) {
            return type.stored();
        }
        if (this.parseKeywordIf("VIRTUAL")) {
            return type.virtual();
        }
        return type;
    }

    private final void parseIdentityOptionIf() {
        if (this.parseIf('(')) {
            boolean identityOption = false;
            while (true) {
                if (identityOption) {
                    this.parseIf(',');
                }
                if (this.parseKeywordIf("START WITH")) {
                    if (!this.parseKeywordIf("LIMIT VALUE")) {
                        this.parseUnsignedIntegerOrBindVariable();
                    }
                    identityOption = true;
                    continue;
                }
                if (this.parseKeywordIf("INCREMENT BY") || this.parseKeywordIf("MAXVALUE") || this.parseKeywordIf("MINVALUE") || this.parseKeywordIf("CACHE")) {
                    this.parseUnsignedIntegerOrBindVariable();
                    identityOption = true;
                    continue;
                }
                if (this.parseKeywordIf("NOMAXVALUE") || this.parseKeywordIf("NOMINVALUE") || this.parseKeywordIf("CYCLE") || this.parseKeywordIf("NOCYCLE") || this.parseKeywordIf("NOCACHE") || this.parseKeywordIf("ORDER") || this.parseKeywordIf("NOORDER")) {
                    identityOption = true;
                    continue;
                }
                if (this.parseSignedIntegerLiteralIf() == null) break;
                identityOption = true;
            }
            if (!identityOption) {
                throw this.unsupportedClause();
            }
            this.parse(')');
        }
    }

    private final boolean parseSerialIf() {
        String s2;
        int i = this.position();
        if (this.parseFunctionNameIf("NEXTVAL") && this.parseIf('(') && (s2 = this.parseStringLiteralIf()) != null && s2.toLowerCase().endsWith("_seq") && this.parseIf("::") && this.parseKeywordIf("REGCLASS") && this.parseIf(')')) {
            return true;
        }
        this.position(i);
        return false;
    }

    private final boolean parsePrimaryKeyClusteredNonClusteredKeywordIf() {
        if (!this.parseKeywordIf("PRIMARY KEY")) {
            return false;
        }
        if (!this.parseKeywordIf("CLUSTERED")) {
            this.parseKeywordIf("NONCLUSTERED");
        }
        if (!this.parseKeywordIf("ASC")) {
            this.parseKeywordIf("DESC");
        }
        return true;
    }

    private final DDLQuery parseCreateType() {
        List<Object> values2;
        Name name = this.parseName();
        this.parseKeyword("AS ENUM");
        this.parse('(');
        if (!this.parseIf(')')) {
            values2 = this.parseList(',', ParseContext::parseStringLiteral);
            this.parse(')');
        } else {
            values2 = new ArrayList();
        }
        return this.dsl.createType(name).asEnum(values2);
    }

    private final Index parseIndexSpecification(Table<?> table) {
        Name name = this.parseIdentifierIf();
        this.parseUsingIndexTypeIf();
        return Internal.createIndex(name == null ? AbstractName.NO_NAME : name, table, this.parseParenthesisedSortSpecification(), false);
    }

    private final boolean parseConstraintConflictClauseIf() {
        return this.parseKeywordIf("ON CONFLICT") && this.parseKeyword("ROLLBACK", "ABORT", "FAIL", "IGNORE", "REPLACE");
    }

    private final Constraint parseConstraintEnforcementIf(ConstraintEnforcementStep e) {
        boolean onConflict = false;
        boolean deferrable = false;
        boolean initially = false;
        while (!onConflict && (onConflict = this.parseConstraintConflictClauseIf()) || !deferrable && (deferrable = this.parseConstraintDeferrableIf()) || !initially && (initially = this.parseConstraintInitiallyIf())) {
        }
        if (this.parseKeywordIf("ENABLE", "ENFORCED")) {
            return e.enforced();
        }
        if (this.parseKeywordIf("DISABLE", "NOT ENFORCED")) {
            return e.notEnforced();
        }
        return e;
    }

    private final boolean parseConstraintDeferrableIf() {
        return this.parseKeywordIf("DEFERRABLE", "NOT DEFERRABLE");
    }

    private final boolean parseConstraintInitiallyIf() {
        return this.parseKeywordIf("INITIALLY") && this.parseKeyword("DEFERRED", "IMMEDIATE");
    }

    private final Constraint parsePrimaryKeySpecification(ConstraintTypeStep constraint) {
        this.parseUsingIndexTypeIf();
        Field<?>[] fieldNames = this.parseKeyColumnList();
        ConstraintEnforcementStep e = constraint == null ? DSL.primaryKey(fieldNames) : constraint.primaryKey(fieldNames);
        this.parseUsingIndexTypeIf();
        return this.parseConstraintEnforcementIf(e);
    }

    private final Constraint parseUniqueSpecification(ConstraintTypeStep constraint) {
        Name constraintName;
        this.parseUsingIndexTypeIf();
        if (constraint == null && (constraintName = this.parseIdentifierIf()) != null) {
            constraint = DSL.constraint(constraintName);
        }
        Field<?>[] fieldNames = this.parseKeyColumnList();
        ConstraintEnforcementStep e = constraint == null ? DSL.unique(fieldNames) : constraint.unique(fieldNames);
        this.parseUsingIndexTypeIf();
        return this.parseConstraintEnforcementIf(e);
    }

    private final Field<?>[] parseKeyColumnList() {
        SortField<?>[] fieldExpressions = this.parseParenthesisedSortSpecification();
        Field[] fieldNames = new Field[fieldExpressions.length];
        for (int i = 0; i < fieldExpressions.length; ++i) {
            if (fieldExpressions[i].$sortOrder() == SortOrder.DESC) {
                throw this.notImplemented("DESC sorting in constraints");
            }
            fieldNames[i] = fieldExpressions[i].$field();
        }
        return fieldNames;
    }

    private final Constraint parseCheckSpecification(ConstraintTypeStep constraint) {
        boolean parens = this.parseIf('(');
        Condition condition = this.parseCondition();
        if (parens) {
            this.parse(')');
        }
        ConstraintEnforcementStep e = constraint == null ? DSL.check(condition) : constraint.check(condition);
        return this.parseConstraintEnforcementIf(e);
    }

    private final Constraint parseForeignKeySpecification(ConstraintTypeStep constraint) {
        Name constraintName = this.parseIdentifierIf();
        if (constraintName != null && constraint == null) {
            constraint = DSL.constraint(constraintName);
        }
        this.parse('(');
        Field<?>[] referencing = this.parseList(',', (? super ParseContext c) -> this.parseFieldName()).toArray(Tools.EMPTY_FIELD);
        this.parse(')');
        this.parseKeyword("REFERENCES");
        return this.parseForeignKeyReferenceSpecification(constraint, referencing);
    }

    private final Constraint parseForeignKeyReferenceSpecification(ConstraintTypeStep constraint, Field<?>[] referencing) {
        Table<?> referencedTable = this.parseTableName();
        Field<?>[] referencedFields = Tools.EMPTY_FIELD;
        if (this.parseIf('(')) {
            referencedFields = this.parseList(',', (? super ParseContext c) -> this.parseFieldName()).toArray(Tools.EMPTY_FIELD);
            this.parse(')');
            if (referencing.length != referencedFields.length) {
                throw this.exception("Number of referencing columns (" + referencing.length + ") must match number of referenced columns (" + referencedFields.length + ")");
            }
        }
        ConstraintForeignKeyOnStep e = constraint == null ? DSL.foreignKey(referencing).references(referencedTable, referencedFields) : constraint.foreignKey(referencing).references(referencedTable, referencedFields);
        boolean onDelete = false;
        boolean onUpdate = false;
        while (!(onDelete && onUpdate || !this.parseKeywordIf("ON"))) {
            if (!onDelete && this.parseKeywordIf("DELETE")) {
                onDelete = true;
                if (this.parseKeywordIf("CASCADE")) {
                    e = e.onDeleteCascade();
                    continue;
                }
                if (this.parseKeywordIf("NO ACTION")) {
                    e = e.onDeleteNoAction();
                    continue;
                }
                if (this.parseKeywordIf("RESTRICT")) {
                    e = e.onDeleteRestrict();
                    continue;
                }
                if (this.parseKeywordIf("SET DEFAULT")) {
                    e = e.onDeleteSetDefault();
                    continue;
                }
                if (this.parseKeywordIf("SET NULL")) {
                    e = e.onDeleteSetNull();
                    continue;
                }
                throw this.expected("CASCADE", "NO ACTION", "RESTRICT", "SET DEFAULT", "SET NULL");
            }
            if (!onUpdate && this.parseKeywordIf("UPDATE")) {
                onUpdate = true;
                if (this.parseKeywordIf("CASCADE")) {
                    e = e.onUpdateCascade();
                    continue;
                }
                if (this.parseKeywordIf("NO ACTION")) {
                    e = e.onUpdateNoAction();
                    continue;
                }
                if (this.parseKeywordIf("RESTRICT")) {
                    e = e.onUpdateRestrict();
                    continue;
                }
                if (this.parseKeywordIf("SET DEFAULT")) {
                    e = e.onUpdateSetDefault();
                    continue;
                }
                if (this.parseKeywordIf("SET NULL")) {
                    e = e.onUpdateSetNull();
                    continue;
                }
                throw this.expected("CASCADE", "NO ACTION", "RESTRICT", "SET DEFAULT", "SET NULL");
            }
            throw this.expected("DELETE", "UPDATE");
        }
        return this.parseConstraintEnforcementIf(e);
    }

    private final <O, S extends QueryPart> S parseIfExists(Supplier<? extends O> part, Function<? super O, ? extends S> stepIfExists, Function<? super O, ? extends S> step) {
        boolean ifExists = this.parseKeywordIf("IF EXISTS");
        O q = part.get();
        ifExists = ifExists || this.parseKeywordIf("IF EXISTS");
        return (S)(ifExists ? (QueryPart)stepIfExists.apply(q) : (QueryPart)step.apply(q));
    }

    private final <S2 extends QueryPart, S1 extends S2> S2 parseCascadeRestrictIf(S1 step, Function<? super S1, ? extends S2> stepCascade, Function<? super S1, ? extends S2> stepRestrict) {
        boolean restrict;
        boolean cascade = this.parseKeywordIf("CASCADE");
        boolean bl = restrict = !cascade && this.parseKeywordIf("RESTRICT");
        return (S2)(cascade ? (QueryPart)stepCascade.apply(step) : (restrict ? (QueryPart)stepRestrict.apply(step) : step));
    }

    private final DDLQuery parseAlterTable() {
        Table<Object> tableName;
        boolean ifTableExists = this.parseKeywordIf("IF EXISTS");
        if (this.peekKeyword("ONLY")) {
            Name only = this.parseIdentifier();
            int p = this.position();
            tableName = this.parseTableNameIf();
            if (tableName == null || !tableName.getQualifiedName().qualified() && tableName.getUnqualifiedName().quoted() == Name.Quoted.UNQUOTED && ALTER_KEYWORDS.contains(tableName.getName().toUpperCase())) {
                tableName = DSL.table(only);
                this.position(p);
            }
        } else {
            tableName = this.parseTableName();
        }
        AlterTableStep s1 = ifTableExists ? this.dsl.alterTableIfExists(tableName) : this.dsl.alterTable(tableName);
        switch (this.characterUpper()) {
            case 'A': {
                if (this.parseKeywordIf("ADD")) {
                    return this.parseAlterTableAdd(s1, tableName);
                }
                if (!this.parseKeywordIf("ALTER")) break;
                if (this.parseKeywordIf("CONSTRAINT")) {
                    return this.parseAlterTableAlterConstraint(s1);
                }
                if (!this.parseKeywordIf("COLUMN")) {
                    // empty if block
                }
                return this.parseAlterTableAlterColumn(s1);
            }
            case 'C': {
                if (!this.parseKeywordIf("COMMENT")) break;
                if (!this.parseIf('=')) {
                    this.parseKeywordIf("IS");
                }
                return this.dsl.commentOnTable(tableName).is(this.parseStringLiteral());
            }
            case 'D': {
                if (!this.parseKeywordIf("DROP")) break;
                if (this.parseKeywordIf("CONSTRAINT")) {
                    return this.parseCascadeRestrictIf(this.parseIfExists(this::parseIdentifier, s1::dropConstraintIfExists, s1::dropConstraint), AlterTableDropStep::cascade, AlterTableDropStep::restrict);
                }
                if (this.parseKeywordIf("UNIQUE")) {
                    return this.parseCascadeRestrictIf(s1.dropUnique(this.peek('(') ? DSL.unique(this.parseKeyColumnList()) : DSL.constraint(this.parseIdentifier())), AlterTableDropStep::cascade, AlterTableDropStep::restrict);
                }
                if (this.parseKeywordIf("PRIMARY KEY")) {
                    Name identifier = this.parseIdentifierIf();
                    return this.parseCascadeRestrictIf(identifier == null ? s1.dropPrimaryKey() : s1.dropPrimaryKey(identifier), AlterTableDropStep::cascade, AlterTableDropStep::restrict);
                }
                if (this.parseKeywordIf("FOREIGN KEY")) {
                    return s1.dropForeignKey(this.parseIdentifier());
                }
                if (this.parseKeywordIf("INDEX") || this.parseKeywordIf("KEY")) {
                    return this.dsl.dropIndex(this.parseIdentifier()).on(tableName);
                }
                this.parseKeywordIf("COLUMN");
                boolean ifColumnExists = this.parseKeywordIf("IF EXISTS");
                boolean parens = this.parseIf('(');
                TableField<?, ?> field2 = this.parseFieldName();
                ArrayList fields = null;
                if (!ifColumnExists) {
                    while (true) {
                        if (!this.parseIf(',')) {
                            if (!this.parseKeywordIf("DROP")) break;
                            if (!this.parseKeywordIf("COLUMN")) {
                                // empty if block
                            }
                        }
                        if (fields == null) {
                            fields = new ArrayList();
                            fields.add(field2);
                        }
                        fields.add(this.parseFieldName());
                    }
                }
                if (parens) {
                    this.parse(')');
                }
                return this.parseCascadeRestrictIf(fields == null ? (ifColumnExists ? s1.dropColumnIfExists(field2) : s1.dropColumn(field2)) : s1.dropColumns(fields), AlterTableDropStep::cascade, AlterTableDropStep::restrict);
            }
            case 'M': {
                if (!this.parseKeywordIf("MODIFY")) break;
                if (this.parseKeywordIf("CONSTRAINT")) {
                    return this.parseAlterTableAlterConstraint(s1);
                }
                if (!this.parseKeywordIf("COLUMN")) {
                    // empty if block
                }
                return this.parseAlterTableAlterColumn(s1);
            }
            case 'O': {
                if (!this.parseKeywordIf("OWNER TO") || this.parseUser() == null) break;
                return IGNORE.get();
            }
            case 'R': {
                if (!this.parseKeywordIf("RENAME")) break;
                if (this.parseKeywordIf("AS", "TO")) {
                    Table<?> newName = this.parseTableName();
                    return s1.renameTo(newName);
                }
                if (this.parseKeywordIf("COLUMN")) {
                    Name oldName = this.parseIdentifier();
                    this.parseKeyword("AS", "TO");
                    Name newName = this.parseIdentifier();
                    return s1.renameColumn(oldName).to(newName);
                }
                if (this.parseKeywordIf("INDEX")) {
                    Name oldName = this.parseIdentifier();
                    this.parseKeyword("AS", "TO");
                    Name newName = this.parseIdentifier();
                    return s1.renameIndex(oldName).to(newName);
                }
                if (!this.parseKeywordIf("CONSTRAINT")) break;
                Name oldName = this.parseIdentifier();
                this.parseKeyword("AS", "TO");
                Name newName = this.parseIdentifier();
                return s1.renameConstraint(oldName).to(newName);
            }
            case 'S': {
                if (!this.parseKeywordIf("SET")) break;
                return s1.comment(this.parseOptionsDescription());
            }
        }
        throw this.expected("ADD", "ALTER", "COMMENT", "DROP", "MODIFY", "OWNER TO", "RENAME", "SET");
    }

    private final DDLQuery parseAlterTableAdd(AlterTableStep s1, Table<?> tableName) {
        ArrayList<TableElement> list = new ArrayList<TableElement>();
        if (this.parseIndexOrKeyIf()) {
            Name name = this.parseIdentifierIf();
            return name == null ? this.dsl.createIndex().on(tableName, this.parseParenthesisedSortSpecification()) : this.dsl.createIndex(name).on(tableName, this.parseParenthesisedSortSpecification());
        }
        if (this.parseIf('(')) {
            do {
                this.parseAlterTableAddFieldsOrConstraints(list);
            } while (this.parseIf(','));
            this.parse(')');
        } else {
            if (this.parseKeywordIf("COLUMN IF NOT EXISTS") || this.parseKeywordIf("IF NOT EXISTS")) {
                return this.parseAlterTableAddFieldFirstBeforeLast(s1.addColumnIfNotExists(this.parseAlterTableAddField(null)));
            }
            do {
                this.parseAlterTableAddFieldsOrConstraints(list);
            } while (this.parseKeywordIf("ADD") || this.parseIf(',') && (this.parseKeywordIf("ADD") || !this.peekKeyword("ALTER", "COMMENT", "DROP", "MODIFY", "OWNER TO", "RENAME")));
        }
        if (list.size() == 1) {
            Object e = list.get(0);
            if (e instanceof Constraint) {
                Constraint c = (Constraint)e;
                return s1.add(c);
            }
            return this.parseAlterTableAddFieldFirstBeforeLast(s1.add((Field)list.get(0)));
        }
        return this.parseAlterTableAddFieldFirstBeforeLast(s1.add(list));
    }

    private final DDLQuery parseAlterTableAddFieldFirstBeforeLast(AlterTableAddStep step) {
        if (this.parseKeywordIf("FIRST")) {
            return step.first();
        }
        if (this.parseKeywordIf("BEFORE")) {
            return step.before(this.parseFieldName());
        }
        if (this.parseKeywordIf("AFTER")) {
            return step.after(this.parseFieldName());
        }
        return step;
    }

    private final boolean parseIndexOrKeyIf() {
        return (this.parseKeywordIf("SPATIAL INDEX") || this.parseKeywordIf("SPATIAL KEY") || this.parseKeywordIf("FULLTEXT INDEX") || this.parseKeywordIf("FULLTEXT KEY")) && this.requireUnsupportedSyntax() || this.parseKeywordIf("INDEX") || this.parseKeywordIf("KEY");
    }

    private final void parseAlterTableAddFieldsOrConstraints(List<TableElement> list) {
        ConstraintTypeStep constraint = this.parseConstraintNameSpecification();
        if (this.parsePrimaryKeyClusteredNonClusteredKeywordIf()) {
            list.add(this.parsePrimaryKeySpecification(constraint));
        } else if (this.parseKeywordIf("UNIQUE")) {
            if (!this.parseKeywordIf("KEY", "INDEX")) {
                // empty if block
            }
            list.add(this.parseUniqueSpecification(constraint));
        } else if (this.parseKeywordIf("FOREIGN KEY")) {
            list.add(this.parseForeignKeySpecification(constraint));
        } else if (this.parseKeywordIf("CHECK")) {
            list.add(this.parseCheckSpecification(constraint));
        } else {
            if (constraint != null) {
                throw this.expected("CHECK", "FOREIGN KEY", "PRIMARY KEY", "UNIQUE");
            }
            if (!this.parseKeywordIf("COLUMN")) {
                // empty if block
            }
            this.parseAlterTableAddField(list);
        }
    }

    private final ConstraintTypeStep parseConstraintNameSpecification() {
        if (this.parseKeywordIf("CONSTRAINT") && !this.peekKeyword("PRIMARY KEY", "UNIQUE", "FOREIGN KEY", "CHECK")) {
            return DSL.constraint(this.parseIdentifier());
        }
        return null;
    }

    private final Field<?> parseAlterTableAddField(List<TableElement> list) {
        Name fieldName = this.parseIdentifier();
        DataType<Object> type = this.parseDataTypeIf(true);
        if (type == null) {
            type = SQLDataType.OTHER;
        }
        int p = list == null ? -1 : list.size();
        ParseInlineConstraints inline2 = this.parseInlineConstraints(fieldName, type, list, false, false, false);
        Field<?> result = DSL.field(fieldName, inline2.type, inline2.fieldComment);
        if (list != null) {
            list.add(p, result);
        }
        return result;
    }

    private final DDLQuery parseAlterTableAlterColumn(AlterTableStep s1) {
        boolean paren = this.parseIf('(');
        TableField<?, ?> field2 = this.parseFieldName();
        if (!paren) {
            if (this.parseKeywordIf("CONSTRAINT") && this.parseIdentifier() != null) {
                if (this.parseKeywordIf("NULL")) {
                    return s1.alter(field2).dropNotNull();
                }
                if (this.parseNotNullOptionalEnable()) {
                    return s1.alter(field2).setNotNull();
                }
                throw this.expected("NOT NULL", "NULL");
            }
            if (this.parseKeywordIf("DROP NOT NULL", "SET NULL", "NULL")) {
                return s1.alter(field2).dropNotNull();
            }
            if (this.parseKeywordIf("DROP DEFAULT")) {
                return s1.alter(field2).dropDefault();
            }
            if (this.parseKeywordIf("SET NOT NULL") || this.parseNotNullOptionalEnable()) {
                return s1.alter(field2).setNotNull();
            }
            if (this.parseKeywordIf("SET DEFAULT")) {
                return s1.alter(field2).default_(this.toField(this.parseConcat()));
            }
            if (this.parseKeywordIf("TO", "RENAME TO", "RENAME AS")) {
                return s1.renameColumn(field2).to(this.parseFieldName());
            }
            if (this.parseKeywordIf("TYPE", "SET DATA TYPE")) {
                // empty if block
            }
        }
        DataType<?> type = this.parseDataType();
        if (this.parseKeywordIf("NULL")) {
            type = type.nullable(true);
        } else if (this.parseNotNullOptionalEnable()) {
            type = type.nullable(false);
        }
        if (paren) {
            this.parse(')');
        }
        return s1.alter(field2).set(type);
    }

    private final boolean parseNotNullOptionalEnable() {
        boolean bl;
        if (this.parseKeywordIf("NOT NULL")) {
            if (!this.parseKeywordIf("ENABLE")) {
                // empty if block
            }
            if (!this.parseConstraintConflictClauseIf()) {
                // empty if block
            }
            bl = true;
        } else {
            bl = false;
        }
        return bl;
    }

    private final DDLQuery parseAlterTableAlterConstraint(AlterTableStep s1) {
        this.requireProEdition();
        ConstraintTypeStep constraint = DSL.constraint(this.parseIdentifier());
        if (this.parseKeywordIf("ENABLE", "ENFORCED")) {
            return s1.alterConstraint(constraint).enforced();
        }
        if (this.parseKeywordIf("DISABLE", "NOT ENFORCED")) {
            return s1.alterConstraint(constraint).notEnforced();
        }
        throw this.expected("ENABLE", "ENFORCED", "DISABLE", "NOT ENFORCED");
    }

    private final DDLQuery parseAlterType() {
        AlterTypeStep s1 = this.dsl.alterType(this.parseName());
        if (this.parseKeywordIf("ADD VALUE")) {
            return s1.addValue(this.parseStringLiteral());
        }
        if (this.parseKeywordIf("OWNER TO") && this.parseUser() != null) {
            return IGNORE.get();
        }
        if (this.parseKeywordIf("RENAME TO")) {
            return s1.renameTo(this.parseIdentifier());
        }
        if (this.parseKeywordIf("RENAME VALUE")) {
            return s1.renameValue(this.parseStringLiteral()).to(this.parseKeyword("TO") ? this.parseStringLiteral() : null);
        }
        if (this.parseKeywordIf("SET SCHEMA")) {
            return s1.setSchema(this.parseIdentifier());
        }
        throw this.expected("ADD VALUE", "OWNER TO", "RENAME TO", "RENAME VALUE", "SET SCHEMA");
    }

    private final DDLQuery parseRename() {
        this.parseKeyword("RENAME");
        switch (this.characterUpper()) {
            case 'C': {
                if (!this.parseKeywordIf("COLUMN")) break;
                TableField<?, ?> oldName = this.parseFieldName();
                this.parseKeyword("AS", "TO");
                return this.dsl.alterTable(oldName.getTable()).renameColumn(oldName).to(this.parseFieldName());
            }
            case 'D': {
                if (!this.parseKeywordIf("DATABASE")) break;
                Catalog oldName = this.parseCatalogName();
                this.parseKeyword("AS", "TO");
                return this.dsl.alterDatabase(oldName).renameTo(this.parseCatalogName());
            }
            case 'I': {
                if (!this.parseKeywordIf("INDEX")) break;
                Name oldName = this.parseIndexName();
                this.parseKeyword("AS", "TO");
                return this.dsl.alterIndex(oldName).renameTo(this.parseIndexName());
            }
            case 'S': {
                if (this.parseKeywordIf("SCHEMA")) {
                    Schema oldName = this.parseSchemaName();
                    this.parseKeyword("AS", "TO");
                    return this.dsl.alterSchema(oldName).renameTo(this.parseSchemaName());
                }
                if (!this.parseKeywordIf("SEQUENCE")) break;
                Sequence<?> oldName = this.parseSequenceName();
                this.parseKeyword("AS", "TO");
                return this.dsl.alterSequence(oldName).renameTo(this.parseSequenceName());
            }
            case 'V': {
                if (!this.parseKeywordIf("VIEW")) break;
                Table<?> oldName = this.parseTableName();
                this.parseKeyword("AS", "TO");
                return this.dsl.alterView(oldName).renameTo(this.parseTableName());
            }
        }
        this.parseKeywordIf("TABLE");
        Table<?> oldName = this.parseTableName();
        this.parseKeyword("AS", "TO");
        return this.dsl.alterTable(oldName).renameTo(this.parseTableName());
    }

    private final DDLQuery parseDropTable(boolean temporary) {
        DropTableStep s1;
        boolean restrict;
        boolean bl;
        boolean ifExists = this.parseKeywordIf("IF EXISTS");
        Table<?> tableName = this.parseTableName();
        boolean bl2 = ifExists = ifExists || this.parseKeywordIf("IF EXISTS");
        if (this.parseKeywordIf("CASCADE")) {
            if (!this.parseKeywordIf("CONSTRAINTS")) {
                // empty if block
            }
            bl = true;
        } else {
            bl = false;
        }
        boolean cascade = bl;
        boolean bl3 = restrict = !cascade && this.parseKeywordIf("RESTRICT");
        DropTableStep dropTableStep = ifExists ? this.dsl.dropTableIfExists(tableName) : (s1 = temporary ? this.dsl.dropTemporaryTable(tableName) : this.dsl.dropTable(tableName));
        return cascade ? s1.cascade() : (restrict ? s1.restrict() : s1);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Pro
    private final DDLQuery parseCreateTrigger(boolean orReplace) {
        LanguageContext previous = this.languageContext;
        try {
            CreateTriggerEventOrStep s2;
            this.languageContext = LanguageContext.TRIGGER;
            Name name = this.parseIdentifier();
            CreateTriggerEventStep s1 = orReplace ? this.dsl.createOrReplaceTrigger(name) : this.dsl.createTrigger(name);
            boolean insert = false;
            boolean update = false;
            boolean delete = false;
            Table<?> table = null;
            if (this.parseKeywordIf("ON")) {
                table = this.parseTableName();
            }
            if (this.parseKeywordIf("BEFORE", "FOR")) {
                if (insert |= this.parseKeywordIf("INSERT")) {
                    s2 = s1.beforeInsert();
                } else if (update |= this.parseKeywordIf("UPDATE")) {
                    s2 = this.parseKeywordIf("OF") ? s1.beforeUpdate().of(this.parseList(',', (? super ParseContext c) -> c.parseField())) : s1.beforeUpdate();
                } else {
                    if (!(delete |= this.parseKeywordIf("DELETE"))) throw this.expected("INSERT", "UPDATE", "DELETE");
                    s2 = s1.beforeDelete();
                }
            } else if (this.parseKeywordIf("AFTER")) {
                if (insert |= this.parseKeywordIf("INSERT")) {
                    s2 = s1.afterInsert();
                } else if (update |= this.parseKeywordIf("UPDATE")) {
                    s2 = this.parseKeywordIf("OF") ? s1.afterUpdate().of(this.parseList(',', (? super ParseContext c) -> c.parseField())) : s1.afterUpdate();
                } else {
                    if (!(delete |= this.parseKeywordIf("DELETE"))) throw this.expected("INSERT", "UPDATE", "DELETE");
                    s2 = s1.afterDelete();
                }
            } else {
                if (!this.parseKeywordIf("INSTEAD OF")) throw this.expected("BEFORE", "AFTER", "INSTEAD OF");
                if (insert |= this.parseKeywordIf("INSERT")) {
                    s2 = s1.insteadOfInsert();
                } else if (update |= this.parseKeywordIf("UPDATE")) {
                    s2 = this.parseKeywordIf("OF") ? s1.insteadOfUpdate().of(this.parseList(',', (? super ParseContext c) -> c.parseField())) : s1.insteadOfUpdate();
                } else {
                    if (!(delete |= this.parseKeywordIf("DELETE"))) throw this.expected("INSERT", "UPDATE", "DELETE");
                    s2 = s1.insteadOfDelete();
                }
            }
            while (this.parseKeywordIf("OR") || this.parseIf(',') || this.peekKeyword("INSERT", "UPDATE", "DELETE")) {
                if (!insert && (insert |= this.parseKeywordIf("INSERT"))) {
                    s2 = s2.orInsert();
                    continue;
                }
                if (!update && (update |= this.parseKeywordIf("UPDATE"))) {
                    if (this.parseKeywordIf("OF")) {
                        s2 = s2.orUpdate().of(this.parseList(',', (? super ParseContext c) -> c.parseField()));
                        continue;
                    }
                    s2 = s2.orUpdate();
                    continue;
                }
                if (delete || !(delete |= this.parseKeywordIf("DELETE"))) throw this.exception("INSERT, UPDATE, DELETE may appear only once");
                s2 = s2.orDelete();
            }
            if (table == null && this.parseKeyword("ON")) {
                table = this.parseTableName();
            }
            CreateTriggerReferencingStep s3 = s2.on(table);
            boolean referencingOld = false;
            boolean referencingNew = false;
            if (this.parseKeywordIf("REFERENCING")) {
                while (true) {
                    if (!referencingOld && (referencingOld |= this.parseKeywordIf("OLD"))) {
                        this.parseKeywordIf("ROW");
                        this.parseKeywordIf("AS");
                        s3 = s3.referencingOldAs(this.parseIdentifier());
                        continue;
                    }
                    if (referencingNew || !(referencingNew |= this.parseKeywordIf("NEW"))) break;
                    this.parseKeywordIf("ROW");
                    this.parseKeywordIf("AS");
                    s3 = s3.referencingNewAs(this.parseIdentifier());
                }
                if (!referencingOld && !referencingNew) {
                    throw this.expected("OLD", "NEW");
                }
            }
            CreateTriggerWhenStep s4 = this.parseKeywordIf("FOR EACH ROW") ? s3.forEachRow() : (this.parseKeywordIf("FOR EACH STATEMENT") ? s3.forEachStatement() : s3);
            CreateTriggerWhenStep s5 = this.parseKeywordIf("WHEN") ? this.parseParenthesised(c -> s4.when(c.parseCondition())) : s4;
            this.parseKeywordIf("AS");
            DDLQuery dDLQuery = this.peekKeyword("BEGIN") ? s5.as((Statement)this.parseBlock(false)) : s5.as(this.parseStatementAndSemicolon());
            return dDLQuery;
        }
        finally {
            this.languageContext = previous;
        }
    }

    @Pro
    private final DDLQuery parseDropTrigger() {
        return this.parseIfExists(this::parseIdentifier, this.dsl::dropTriggerIfExists, this.dsl::dropTrigger);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Pro
    private final DDLQuery parseCreateFunction(boolean orReplace) {
        LanguageContext previous = this.languageContext;
        try {
            CreateFunctionCharacteristicsStep s5;
            CreateFunctionCharacteristicsStep s4;
            this.languageContext = LanguageContext.FUNCTION;
            Name name = this.parseName();
            List<Parameter<?>> parameters = this.parseParametersIf();
            this.parseKeyword("RETURN", "RETURNS", "RETURNING");
            DataType<?> type = this.parseDataType();
            QOM.SQLDataAccess access = null;
            Boolean deterministic = null;
            Boolean returnsNullOnNull = null;
            while (access == null && (access = this.parseSQLDataAccessIf()) != null || deterministic == null && (deterministic = this.parseDeterministicIf()) != null || returnsNullOnNull == null && (returnsNullOnNull = this.parseReturnsNullOnNullInputIf()) != null) {
            }
            boolean language2 = this.parseLanguageIf();
            Statement statement = this.parseCreateProcedureOrFunctionAsBlock();
            if (!language2) {
                this.parseLanguageIf();
            }
            CreateFunctionParametersStep s1 = orReplace ? this.dsl.createOrReplaceFunction(name) : this.dsl.createFunction(name);
            CreateFunctionParametersStep s2 = parameters != null ? s1.parameters(parameters) : s1;
            CreateFunctionCharacteristicsStep s3 = s2.returns(type);
            CreateFunctionCharacteristicsStep createFunctionCharacteristicsStep = access == QOM.SQLDataAccess.CONTAINS_SQL ? s3.containsSQL() : (access == QOM.SQLDataAccess.MODIFIES_SQL_DATA ? s3.modifiesSQLData() : (access == QOM.SQLDataAccess.NO_SQL ? s3.noSQL() : (s4 = access == QOM.SQLDataAccess.READS_SQL_DATA ? s3.readsSQLData() : s3)));
            CreateFunctionCharacteristicsStep createFunctionCharacteristicsStep2 = deterministic == null ? s4 : (s5 = deterministic != false ? s4.deterministic() : s4.notDeterministic());
            CreateFunctionCharacteristicsStep s6 = returnsNullOnNull == null ? s5 : (returnsNullOnNull != false ? s5.returnsNullOnNullInput() : s5.calledOnNullInput());
            DDLQuery dDLQuery = s6.as(statement);
            return dDLQuery;
        }
        finally {
            this.languageContext = previous;
        }
    }

    @Pro
    private final Boolean parseReturnsNullOnNullInputIf() {
        if (this.parseKeywordIf("RETURNS NULL ON NULL INPUT")) {
            return true;
        }
        if (this.parseKeywordIf("CALLED ON NULL INPUT")) {
            return false;
        }
        return null;
    }

    @Pro
    private final Boolean parseDeterministicIf() {
        if (this.parseKeywordIf("DETERMINISTIC", "IMMUTABLE")) {
            return true;
        }
        if (this.parseKeywordIf("NOT DETERMINISTIC", "VOLATILE")) {
            return false;
        }
        return null;
    }

    @Pro
    private final DDLQuery parseDropFunction() {
        return this.parseIfExists(this::parseName, this.dsl::dropFunctionIfExists, this.dsl::dropFunction);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Pro
    private final DDLQuery parseCreateProcedure(boolean orReplace) {
        LanguageContext previous = this.languageContext;
        try {
            CreateProcedureParametersStep s2;
            this.languageContext = LanguageContext.PROCEDURE;
            Name name = this.parseName();
            List<Parameter<?>> parameters = this.parseParametersIf();
            QOM.SQLDataAccess access = this.parseSQLDataAccessIf();
            boolean language2 = this.parseLanguageIf();
            Statement statement = this.parseCreateProcedureOrFunctionAsBlock();
            if (!language2) {
                this.parseLanguageIf();
            }
            CreateProcedureParametersStep s1 = orReplace ? this.dsl.createOrReplaceProcedure(name) : this.dsl.createProcedure(name);
            CreateProcedureCharacteristicsStep createProcedureCharacteristicsStep = s2 = parameters != null ? s1.parameters(parameters) : s1;
            CreateProcedureCharacteristicsStep s3 = access == QOM.SQLDataAccess.CONTAINS_SQL ? s2.containsSQL() : (access == QOM.SQLDataAccess.MODIFIES_SQL_DATA ? s2.modifiesSQLData() : (access == QOM.SQLDataAccess.NO_SQL ? s2.noSQL() : (access == QOM.SQLDataAccess.READS_SQL_DATA ? s2.readsSQLData() : s2)));
            DDLQuery dDLQuery = s3.as(statement);
            return dDLQuery;
        }
        finally {
            this.languageContext = previous;
        }
    }

    @Pro
    private final boolean parseLanguageIf() {
        return this.parseKeywordIf("LANGUAGE") && this.parseKeyword("SQL", "PLPGSQL");
    }

    @Pro
    private final Statement parseCreateProcedureOrFunctionAsBlock() {
        if (this.parseKeywordIf("AS", "IS")) {
            String string = this.parseStringLiteralIf();
            if (string != null) {
                return this.dsl.parser().parseQuery(string);
            }
            if (this.peekKeyword("BEGIN")) {
                return this.parseBlock(false);
            }
            List<Statement> statements = this.tryParseDeclarations();
            statements.addAll(((BlockImpl)this.parseBlock((boolean)false)).statements);
            return DSL.begin(statements);
        }
        return this.parseStatement();
    }

    @Pro
    private final DDLQuery parseDropProcedure() {
        return this.parseIfExists(this::parseName, this.dsl::dropProcedureIfExists, this.dsl::dropProcedure);
    }

    @Pro
    private final List<Parameter<?>> parseParametersIf() {
        ArrayList parameters = null;
        boolean parens = this.parseIf('(');
        if (parens && this.parseIf(')')) {
            return parameters;
        }
        if (!parens && this.peekKeyword("AS", "BEGIN", "CONTAINS SQL", "IS", "MODIFIES SQL DATA", "NO SQL", "READS SQL DATA", "RETURNS", "RETURN", "RETURNING")) {
            return parameters;
        }
        do {
            ParamMode mode = this.parseParamModeIf();
            Name paramName = this.parseVariableName();
            this.parseKeywordIf("AS");
            DataType<?> type = this.parseDataType();
            if (parameters == null) {
                parameters = new ArrayList();
            }
            parameters.add(mode == ParamMode.OUT ? DSL.out(paramName, type) : (mode == ParamMode.INOUT ? DSL.inOut(paramName, type) : DSL.in(paramName, type)));
        } while (this.parseIf(','));
        if (parens) {
            this.parse(')');
        }
        return parameters;
    }

    @Pro
    private final ParamMode parseParamModeIf() {
        if (this.parseKeywordIf("OUT")) {
            return ParamMode.OUT;
        }
        if (this.parseKeywordIf("INOUT")) {
            return ParamMode.INOUT;
        }
        if (this.parseKeywordIf("IN")) {
            if (this.parseKeywordIf("OUT")) {
                return ParamMode.INOUT;
            }
            return ParamMode.IN;
        }
        return ParamMode.IN;
    }

    @Pro
    private final QOM.SQLDataAccess parseSQLDataAccessIf() {
        if (this.parseKeywordIf("NO SQL")) {
            return QOM.SQLDataAccess.NO_SQL;
        }
        if (this.parseKeywordIf("CONTAINS SQL")) {
            return QOM.SQLDataAccess.CONTAINS_SQL;
        }
        if (this.parseKeywordIf("READS SQL DATA")) {
            return QOM.SQLDataAccess.READS_SQL_DATA;
        }
        if (this.parseKeywordIf("MODIFIES SQL DATA")) {
            return QOM.SQLDataAccess.MODIFIES_SQL_DATA;
        }
        return null;
    }

    private final DDLQuery parseCreateDomain() {
        ConstraintTypeStep constraint;
        boolean ifNotExists = this.parseKeywordIf("IF NOT EXISTS");
        Domain<?> domainName = this.parseDomainName();
        this.parseKeyword("AS");
        DataType<?> dataType = this.parseDataType();
        CreateDomainDefaultStep<?> s1 = ifNotExists ? this.dsl.createDomainIfNotExists(domainName).as(dataType) : this.dsl.createDomain(domainName).as(dataType);
        CreateDomainConstraintStep s2 = this.parseKeywordIf("DEFAULT") ? s1.default_(this.parseField()) : s1;
        ArrayList<Constraint> constraints = new ArrayList<Constraint>();
        while (true) {
            constraint = this.parseConstraintNameSpecification();
            if (!this.parseKeywordIf("CHECK")) break;
            constraints.add(this.parseCheckSpecification(constraint));
        }
        if (constraint != null) {
            throw this.expected("CHECK", "CONSTRAINT");
        }
        if (!constraints.isEmpty()) {
            s2 = s2.constraints(constraints);
        }
        return s2;
    }

    private final DDLQuery parseAlterDomain() {
        AlterDomainStep<?> s1;
        boolean ifExists = this.parseKeywordIf("IF EXISTS");
        Domain<?> domainName = this.parseDomainName();
        AlterDomainStep<?> alterDomainStep = s1 = ifExists ? this.dsl.alterDomainIfExists(domainName) : this.dsl.alterDomain(domainName);
        if (this.parseKeywordIf("ADD")) {
            ConstraintTypeStep constraint = this.parseConstraintNameSpecification();
            if (this.parseKeywordIf("CHECK")) {
                return s1.add(this.parseCheckSpecification(constraint));
            }
            throw this.expected("CHECK", "CONSTRAINT");
        }
        if (this.parseKeywordIf("DROP CONSTRAINT")) {
            AlterDomainDropConstraintCascadeStep s2;
            boolean ifConstraintExists = this.parseKeywordIf("IF EXISTS");
            ConstraintTypeStep constraint = DSL.constraint(this.parseIdentifier());
            AlterDomainDropConstraintCascadeStep alterDomainDropConstraintCascadeStep = s2 = ifConstraintExists ? s1.dropConstraintIfExists(constraint) : s1.dropConstraint(constraint);
            return this.parseKeywordIf("CASCADE") ? s2.cascade() : (this.parseKeywordIf("RESTRICT") ? s2.restrict() : s2);
        }
        if (this.parseKeywordIf("RENAME")) {
            if (this.parseKeywordIf("TO", "AS")) {
                return s1.renameTo(this.parseDomainName());
            }
            if (this.parseKeywordIf("CONSTRAINT")) {
                boolean ifConstraintExists = this.parseKeywordIf("IF EXISTS");
                ConstraintTypeStep oldName = DSL.constraint(this.parseIdentifier());
                AlterDomainRenameConstraintStep s2 = ifConstraintExists ? s1.renameConstraintIfExists(oldName) : s1.renameConstraint(oldName);
                this.parseKeyword("AS", "TO");
                return s2.to(DSL.constraint(this.parseIdentifier()));
            }
            throw this.expected("CONSTRAINT", "TO", "AS");
        }
        if (this.parseKeywordIf("SET DEFAULT")) {
            return s1.setDefault(this.parseField());
        }
        if (this.parseKeywordIf("DROP DEFAULT")) {
            return s1.dropDefault();
        }
        if (this.parseKeywordIf("SET NOT NULL")) {
            return s1.setNotNull();
        }
        if (this.parseKeywordIf("DROP NOT NULL")) {
            return s1.dropNotNull();
        }
        if (this.parseKeywordIf("OWNER TO")) {
            this.parseUser();
            return IGNORE.get();
        }
        throw this.expected("ADD", "DROP", "RENAME", "SET", "OWNER TO");
    }

    private final DDLQuery parseCreateDatabase() {
        boolean ifNotExists = this.parseKeywordIf("IF NOT EXISTS");
        Catalog catalogName = this.parseCatalogName();
        this.parseMySQLCreateDatabaseFlagsIf();
        return ifNotExists ? this.dsl.createDatabaseIfNotExists(catalogName) : this.dsl.createDatabase(catalogName);
    }

    private final void parseMySQLCreateDatabaseFlagsIf() {
        while (true) {
            if (this.parseKeywordIf("DEFAULT CHARACTER SET", "CHARACTER SET")) {
                if (!this.parseIf("=")) {
                    // empty if block
                }
                this.parseCharacterSet();
                continue;
            }
            if (this.parseKeywordIf("DEFAULT COLLATE", "COLLATE")) {
                if (!this.parseIf("=")) {
                    // empty if block
                }
                this.parseCollation();
                continue;
            }
            if (!this.parseKeywordIf("DEFAULT ENCRYPTION", "ENCRYPTION")) break;
            if (!this.parseIf("=")) {
                // empty if block
            }
            this.parseCharacterLiteral();
        }
    }

    private final DDLQuery parseAlterDatabase() {
        AlterDatabaseStep s1;
        boolean ifExists = this.parseKeywordIf("IF EXISTS");
        Catalog catalogName = this.parseCatalogName();
        AlterDatabaseStep alterDatabaseStep = s1 = ifExists ? this.dsl.alterDatabaseIfExists(catalogName) : this.dsl.alterDatabase(catalogName);
        if (this.parseKeywordIf("RENAME")) {
            this.parseKeyword("AS", "TO");
            return s1.renameTo(this.parseCatalogName());
        }
        if (this.parseKeywordIf("OWNER TO") && this.parseUser() != null) {
            return IGNORE.get();
        }
        if (this.parseAlterDatabaseFlags(true)) {
            return IGNORE.get();
        }
        throw this.expected("OWNER TO", "RENAME TO");
    }

    private final boolean parseAlterDatabaseFlags(boolean throwOnFail) {
        this.parseKeywordIf("DEFAULT");
        if (this.parseCharacterSetSpecificationIf() != null) {
            return true;
        }
        if (this.parseCollateSpecificationIf() != null) {
            return true;
        }
        if (this.parseKeywordIf("ENCRYPTION")) {
            this.parseIf('=');
            this.parseStringLiteral();
            return true;
        }
        if (throwOnFail) {
            throw this.expected("CHARACTER SET", "COLLATE", "DEFAULT ENCRYPTION");
        }
        return false;
    }

    private final DDLQuery parseDropDatabase() {
        return this.parseIfExists(this::parseCatalogName, this.dsl::dropDatabaseIfExists, this.dsl::dropDatabase);
    }

    private final DDLQuery parseCreateSchema() {
        boolean ifNotExists = this.parseKeywordIf("IF NOT EXISTS");
        boolean authorization = this.parseKeywordIf("AUTHORIZATION");
        Schema schemaName = this.parseSchemaName();
        if (!authorization && this.parseKeywordIf("AUTHORIZATION")) {
            this.parseUser();
        }
        this.parseMySQLCreateDatabaseFlagsIf();
        return ifNotExists ? this.dsl.createSchemaIfNotExists(schemaName) : this.dsl.createSchema(schemaName);
    }

    private final DDLQuery parseAlterSchema() {
        AlterSchemaStep s1;
        boolean ifExists = this.parseKeywordIf("IF EXISTS");
        Schema schemaName = this.parseSchemaName();
        AlterSchemaStep alterSchemaStep = s1 = ifExists ? this.dsl.alterSchemaIfExists(schemaName) : this.dsl.alterSchema(schemaName);
        if (this.parseKeywordIf("RENAME")) {
            this.parseKeyword("AS", "TO");
            return s1.renameTo(this.parseSchemaName());
        }
        if (this.parseKeywordIf("OWNER TO") && this.parseUser() != null) {
            return IGNORE.get();
        }
        if (this.parseAlterDatabaseFlags(false)) {
            return IGNORE.get();
        }
        throw this.expected("OWNER TO", "RENAME TO");
    }

    private final DDLQuery parseCreateIndex(boolean unique) {
        CreateIndexIncludeStep s3;
        boolean excludeNullKeys;
        boolean ifNotExists = this.parseKeywordIf("IF NOT EXISTS");
        Name indexName = this.parseIndexNameIf();
        this.parseUsingIndexTypeIf();
        SortField<?>[] fields = null;
        if (this.peek('(')) {
            fields = this.parseParenthesisedSortSpecification();
        }
        this.parseKeyword("ON");
        Table<?> tableName = this.parseTableName();
        this.parseUsingIndexTypeIf();
        if (fields == null) {
            fields = this.parseParenthesisedSortSpecification();
        }
        this.parseUsingIndexTypeIf();
        Name[] include = null;
        if (this.parseKeywordIf("INCLUDE", "COVERING", "STORING")) {
            this.parse('(');
            include = this.parseIdentifiers().toArray(Tools.EMPTY_NAME);
            this.parse(')');
        }
        this.parseKeywordIf("VISIBLE");
        Condition condition = this.parseKeywordIf("WHERE") ? this.parseCondition() : null;
        boolean bl = excludeNullKeys = condition == null && this.parseKeywordIf("EXCLUDE NULL KEYS");
        CreateIndexStep s1 = ifNotExists ? (unique ? this.dsl.createUniqueIndexIfNotExists(indexName) : this.dsl.createIndexIfNotExists(indexName)) : (unique ? (indexName == null ? this.dsl.createUniqueIndex() : this.dsl.createUniqueIndex(indexName)) : (indexName == null ? this.dsl.createIndex() : this.dsl.createIndex(indexName)));
        CreateIndexIncludeStep s2 = s1.on(tableName, fields);
        CreateIndexWhereStep createIndexWhereStep = s3 = include != null ? s2.include(include) : s2;
        return condition != null ? s3.where(condition) : (excludeNullKeys ? s3.excludeNullKeys() : s3);
    }

    private SortField<?>[] parseParenthesisedSortSpecification() {
        this.parse('(');
        SortField<?>[] fields = this.parseList(',', (? super ParseContext c) -> c.parseSortField()).toArray(Tools.EMPTY_SORTFIELD);
        this.parse(')');
        return fields;
    }

    private final boolean parseUsingIndexTypeIf() {
        if (this.parseKeywordIf("USING")) {
            this.parseIdentifier();
        }
        return true;
    }

    private final DDLQuery parseAlterIndex() {
        boolean ifExists = this.parseKeywordIf("IF EXISTS");
        Name indexName = this.parseIndexName();
        this.parseKeyword("RENAME");
        this.parseKeyword("AS", "TO");
        Name newName = this.parseIndexName();
        AlterIndexOnStep s1 = ifExists ? this.dsl.alterIndexIfExists(indexName) : this.dsl.alterIndex(indexName);
        return s1.renameTo(newName);
    }

    private final DDLQuery parseDropIndex() {
        DropIndexOnStep s2;
        boolean ifExists = this.parseKeywordIf("IF EXISTS");
        Name indexName = this.parseIndexName();
        ifExists = ifExists || this.parseKeywordIf("IF EXISTS");
        boolean on = this.parseKeywordIf("ON");
        Table<?> onTable = on ? this.parseTableName() : null;
        DropIndexOnStep s1 = ifExists ? this.dsl.dropIndexIfExists(indexName) : this.dsl.dropIndex(indexName);
        DropIndexCascadeStep dropIndexCascadeStep = s2 = on ? s1.on(onTable) : s1;
        return this.parseKeywordIf("CASCADE") ? s2.cascade() : (this.parseKeywordIf("RESTRICT") ? s2.restrict() : s2);
    }

    @Override
    public final Condition parseCondition() {
        return this.toCondition(this.parseOr());
    }

    private final QueryPart parseOr() {
        QueryPart condition = this.parseXor();
        while (this.parseKeywordIf("OR")) {
            condition = this.toCondition(condition).or(this.toCondition(this.parseXor()));
        }
        return condition;
    }

    private final QueryPart parseXor() {
        QueryPart condition = this.parseAnd();
        while (this.parseKeywordIf("XOR")) {
            condition = this.toCondition(condition).xor(this.toCondition(this.parseAnd()));
        }
        return condition;
    }

    private final QueryPart parseAnd() {
        QueryPart condition = this.parseNot();
        while (!this.forbidden.contains((Object)FunctionKeyword.FK_AND) && this.parseKeywordIf("AND") || this.parseCategory() == SQLDialectCategory.MYSQL && this.parseIf("&&")) {
            condition = this.toCondition(condition).and(this.toCondition(this.parseNot()));
        }
        return condition;
    }

    private final QueryPart parseNot() {
        int not = this.parseNot0();
        QueryPart condition = this.parsePredicate();
        if (this.parseKeywordIf("SELECTIVITY")) {
            this.parseFieldUnsignedNumericLiteral(Sign.NONE);
        }
        for (int i = 0; i < not; ++i) {
            condition = this.toCondition(condition).not();
        }
        return condition;
    }

    private final int parseNot0() {
        int not = 0;
        while (this.parseKeywordIf("NOT") || this.parseCategory() == SQLDialectCategory.MYSQL && this.parseIf('!')) {
            ++not;
        }
        return not;
    }

    private final QueryPart parsePredicate() {
        Comparator comp;
        TSQLOuterJoinComparator outer;
        Condition r = this.listener.parseCondition(this);
        if (r != null) {
            return r;
        }
        switch (this.characterUpper()) {
            case 'D': {
                if (!this.parseKeywordIf("DELETING")) break;
                return DSL.deleting();
            }
            case 'E': {
                if (this.parseFunctionNameIf("EQUAL_NULL")) {
                    return this.parseEqualNull();
                }
                if (!this.parseKeywordIf("EXISTS")) break;
                return DSL.exists(this.parseParenthesised(c -> this.parseWithOrSelect()));
            }
            case 'I': {
                if (!this.parseKeywordIf("INSERTING")) break;
                return DSL.inserting();
            }
            case 'J': {
                Condition condition = this.parsePredicateJSONExistsIf();
                if (condition == null) break;
                return condition;
            }
            case 'R': {
                if (!this.parseKeywordIf("REGEXP_LIKE")) break;
                return this.parseFunctionArgs2(Field::likeRegex);
            }
            case 'S': {
                if (!this.ignoreProEdition() && this.parseFunctionNameIf("ST_CONTAINS", "SDO_CONTAINS") && this.requireProEdition()) {
                    return this.parseFunctionArgs2(DSL::stContains);
                }
                if (!this.ignoreProEdition() && this.parseFunctionNameIf("ST_CROSSES") && this.requireProEdition()) {
                    return this.parseFunctionArgs2(DSL::stCrosses);
                }
                if (!this.ignoreProEdition() && this.parseFunctionNameIf("ST_DISJOINT") && this.requireProEdition()) {
                    return this.parseFunctionArgs2(DSL::stDisjoint);
                }
                if (!this.ignoreProEdition() && this.parseFunctionNameIf("ST_EQUALS", "SDO_EQUAL") && this.requireProEdition()) {
                    return this.parseFunctionArgs2(DSL::stEquals);
                }
                if (!this.ignoreProEdition() && this.parseFunctionNameIf("ST_INTERSECTS") && this.requireProEdition()) {
                    return this.parseFunctionArgs2(DSL::stIntersects);
                }
                if (!this.ignoreProEdition() && this.parseFunctionNameIf("ST_ISCLOSED") && this.requireProEdition()) {
                    return DSL.stIsClosed(this.parseFieldParenthesised());
                }
                if (!this.ignoreProEdition() && this.parseFunctionNameIf("ST_ISEMPTY") && this.requireProEdition()) {
                    return DSL.stIsEmpty(this.parseFieldParenthesised());
                }
                if (!this.ignoreProEdition() && this.parseFunctionNameIf("ST_OVERLAPS", "SDO_OVERLAPS") && this.requireProEdition()) {
                    return this.parseFunctionArgs2(DSL::stOverlaps);
                }
                if (!this.ignoreProEdition() && this.parseFunctionNameIf("ST_TOUCHES", "SDO_TOUCH") && this.requireProEdition()) {
                    return this.parseFunctionArgs2(DSL::stTouches);
                }
                if (this.ignoreProEdition() || !this.parseFunctionNameIf("ST_WITHIN", "SDO_INSIDE") || !this.requireProEdition()) break;
                return this.parseFunctionArgs2(DSL::stWithin);
            }
            case 'U': {
                if (this.parseKeywordIf("UNIQUE")) {
                    return DSL.unique(this.parseParenthesised(c -> this.parseWithOrSelect()));
                }
                if (!this.parseKeywordIf("UPDATING")) break;
                return DSL.updating();
            }
            case 'X': {
                Condition condition = this.parsePredicateXMLExistsIf();
                if (condition == null) break;
                return condition;
            }
        }
        boolean notOp = false;
        FieldOrRow left = this.parseConcat();
        int p = this.position();
        boolean not = this.parseKeywordIf("NOT");
        boolean isField = left instanceof Field;
        if (!not && !this.ignoreProEdition() && (outer = this.parseTSQLOuterJoinComparatorIf()) != null && this.requireProEdition()) {
            Condition result = null;
            switch (outer) {
                case LEFT: {
                    result = ((Field)left).eq(this.toField(this.parseConcat()).plus());
                    break;
                }
                case RIGHT: {
                    result = ((Field)left).plus().eq(this.toField(this.parseConcat()));
                }
            }
            return result;
        }
        if (!not && (comp = this.parseComparatorIf()) != null) {
            Condition result;
            boolean any;
            boolean all = this.parseKeywordIf("ALL");
            boolean bl = any = !all && this.parseKeywordIf("ANY", "SOME");
            if (all || any) {
                this.parse('(');
            }
            Condition condition = all ? (isField ? (this.peekSelectOrWith(true) ? ((Field)left).compare(comp, DSL.all(this.parseWithOrSelect(1))) : ((Field)left).compare(comp, DSL.all(this.parseList(',', (? super ParseContext c) -> c.parseField()).toArray(Tools.EMPTY_FIELD)))) : new RowSubqueryCondition((Row)left, DSL.all(this.parseWithOrSelect(((Row)left).size())), comp)) : (any ? (isField ? (this.peekSelectOrWith(true) ? ((Field)left).compare(comp, DSL.any(this.parseWithOrSelect(1))) : ((Field)left).compare(comp, DSL.any(this.parseList(',', (? super ParseContext c) -> c.parseField()).toArray(Tools.EMPTY_FIELD)))) : new RowSubqueryCondition((Row)left, DSL.any(this.parseWithOrSelect(((Row)left).size())), comp)) : (result = isField ? ((Field)left).compare(comp, this.toField(this.parseConcat())) : AbstractRow.compare((Row)left, comp, this.parseRow(((Row)left).size(), true))));
            if (all || any) {
                this.parse(')');
            }
            return result;
        }
        if (!not && this.parseKeywordIf("IS")) {
            not = this.parseKeywordIf("NOT");
            if (this.parseKeywordIf("NULL")) {
                return not ? (isField ? ((Field)left).isNotNull() : ((Row)left).isNotNull()) : (isField ? ((Field)left).isNull() : ((Row)left).isNull());
            }
            if (isField && this.parseKeywordIf("JSON")) {
                return not ? ((Field)left).isNotJson() : ((Field)left).isJson();
            }
            if (isField && this.parseKeywordIf("DOCUMENT")) {
                return not ? ((Field)left).isNotDocument() : ((Field)left).isDocument();
            }
            boolean bl = not = this.parseKeywordIf("DISTINCT FROM") == not;
            if (left instanceof Field) {
                Field f = (Field)left;
                Field<?> right = this.toField(this.parseConcat());
                return not ? f.isNotDistinctFrom(right) : f.isDistinctFrom(right);
            }
            Row right = this.parseRow(((Row)left).size(), true);
            return new RowIsDistinctFrom((Row)left, right, not);
        }
        if (!not && this.parseIf("@>")) {
            return this.toField(left).contains(this.toField(this.parseConcat()));
        }
        if (!this.forbidden.contains((Object)FunctionKeyword.FK_IN) && this.parseKeywordIf("IN")) {
            Condition result;
            if (isField && !this.peek('(')) {
                result = not ? ((Field)left).notIn(this.parseConcat()) : ((Field)left).in(this.parseConcat());
            } else {
                this.parse('(');
                result = this.peek(')') ? (not ? (isField ? ((Field)left).notIn(Tools.EMPTY_FIELD) : new RowInCondition((Row)left, new QueryPartList(), true)) : (isField ? ((Field)left).in(Tools.EMPTY_FIELD) : new RowInCondition((Row)left, new QueryPartList(), false))) : (this.peekSelectOrWith(true) ? (not ? (isField ? ((Field)left).notIn(this.parseWithOrSelect(1)) : new RowSubqueryCondition((Row)left, this.parseWithOrSelect(((Row)left).size()), Comparator.NOT_IN)) : (isField ? ((Field)left).in(this.parseWithOrSelect(1)) : new RowSubqueryCondition((Row)left, this.parseWithOrSelect(((Row)left).size()), Comparator.IN))) : (not ? (isField ? ((Field)left).notIn((Collection<?>)this.parseList(',', (? super ParseContext c) -> c.parseField())) : new RowInCondition((Row)left, new QueryPartList<Row>((Iterable<Row>)this.parseList(',', (? super ParseContext c) -> this.parseRow(((Row)left).size()))), true)) : (isField ? ((Field)left).in((Collection<?>)this.parseList(',', (? super ParseContext c) -> c.parseField())) : new RowInCondition((Row)left, new QueryPartList<Row>((Iterable<Row>)this.parseList(',', (? super ParseContext c) -> this.parseRow(((Row)left).size()))), false))));
                this.parse(')');
            }
            return result;
        }
        if (this.parseKeywordIf("BETWEEN")) {
            FieldOrRow r2;
            boolean symmetric = !this.parseKeywordIf("ASYMMETRIC") && this.parseKeywordIf("SYMMETRIC");
            FieldOrRow r1 = isField ? this.parseConcat() : this.parseRow(((Row)left).size());
            this.parseKeyword("AND");
            FieldOrRow fieldOrRow = r2 = isField ? this.parseConcat() : this.parseRow(((Row)left).size());
            return symmetric ? (not ? (isField ? ((Field)left).notBetweenSymmetric((Field)r1, (Field)r2) : new RowBetweenCondition((Row)left, (Row)r1, not, symmetric, (Row)r2)) : (isField ? ((Field)left).betweenSymmetric((Field)r1, (Field)r2) : new RowBetweenCondition((Row)left, (Row)r1, not, symmetric, (Row)r2))) : (not ? (isField ? ((Field)left).notBetween((Field)r1, (Field)r2) : new RowBetweenCondition((Row)left, (Row)r1, not, symmetric, (Row)r2)) : (isField ? ((Field)left).between((Field)r1, (Field)r2) : new RowBetweenCondition((Row)left, (Row)r1, not, symmetric, (Row)r2)));
        }
        if (isField && (this.parseKeywordIf("LIKE") || this.parseOperatorIf("~~") || (notOp = this.parseOperatorIf("!~~")))) {
            if (this.parseKeywordIf("ANY")) {
                List<Field> fields;
                this.parse('(');
                if (this.peekSelectOrWith(true)) {
                    SelectQueryImpl<Record> select = this.parseWithOrSelect();
                    this.parse(')');
                    LikeEscapeStep result = not ^ notOp ? ((Field)left).notLike(DSL.any(select)) : ((Field)left).like(DSL.any(select));
                    return this.parseEscapeClauseIf(result);
                }
                if (this.parseIf(')')) {
                    fields = Collections.emptyList();
                } else {
                    fields = this.parseList(',', (? super ParseContext c) -> this.toField(this.parseConcat()));
                    this.parse(')');
                }
                Field[] fieldArray = fields.toArray(new Field[0]);
                LikeEscapeStep result = not ^ notOp ? ((Field)left).notLike(DSL.any(fieldArray)) : ((Field)left).like(DSL.any(fieldArray));
                return this.parseEscapeClauseIf(result);
            }
            if (this.parseKeywordIf("ALL")) {
                List<Field> fields;
                this.parse('(');
                if (this.peekSelectOrWith(true)) {
                    SelectQueryImpl<Record> select = this.parseWithOrSelect();
                    this.parse(')');
                    LikeEscapeStep result = not ^ notOp ? ((Field)left).notLike(DSL.all(select)) : ((Field)left).like(DSL.all(select));
                    return this.parseEscapeClauseIf(result);
                }
                if (this.parseIf(')')) {
                    fields = Collections.emptyList();
                } else {
                    fields = this.parseList(',', (? super ParseContext c) -> this.toField(this.parseConcat()));
                    this.parse(')');
                }
                Field[] fieldArray = fields.toArray(new Field[0]);
                LikeEscapeStep result = not ^ notOp ? ((Field)left).notLike(DSL.all(fieldArray)) : ((Field)left).like(DSL.all(fieldArray));
                return this.parseEscapeClauseIf(result);
            }
            Field<String> right = this.toField(this.parseConcat());
            LikeEscapeStep like = not ^ notOp ? ((Field)left).notLike(right) : ((Field)left).like(right);
            return this.parseEscapeClauseIf(like);
        }
        if (isField && (this.parseKeywordIf("ILIKE") || this.parseOperatorIf("~~*") || (notOp = this.parseOperatorIf("!~~*")))) {
            Field<String> right = this.toField(this.parseConcat());
            LikeEscapeStep like = not ^ notOp ? ((Field)left).notLikeIgnoreCase(right) : ((Field)left).likeIgnoreCase(right);
            return this.parseEscapeClauseIf(like);
        }
        if (isField && (this.parseKeywordIf("REGEXP") || this.parseKeywordIf("RLIKE") || this.parseKeywordIf("LIKE_REGEX") || this.parseOperatorIf("~") || (notOp = this.parseOperatorIf("!~")))) {
            Field<String> right = this.toField(this.parseConcat());
            return not ^ notOp ? ((Field)left).notLikeRegex(right) : ((Field)left).likeRegex(right);
        }
        if (isField && this.parseKeywordIf("SIMILAR TO")) {
            Field<String> right = this.toField(this.parseConcat());
            LikeEscapeStep like = not ? ((Field)left).notSimilarTo(right) : ((Field)left).similarTo(right);
            return this.parseEscapeClauseIf(like);
        }
        if (left instanceof Row && ((Row)left).size() == 2 && this.parseKeywordIf("OVERLAPS")) {
            Row leftRow = (Row)left;
            Row rightRow = this.parseRow(2);
            Row2<?, ?> leftRow2 = DSL.row(leftRow.field(0), leftRow.field(1));
            Row2<?, ?> rightRow2 = DSL.row(rightRow.field(0), rightRow.field(1));
            return leftRow2.overlaps(rightRow2);
        }
        this.position(p);
        return left;
    }

    private final Condition parseEqualNull() {
        Condition result;
        this.parse('(');
        FieldOrRow left = this.parseConcat();
        this.parse(',');
        if (left instanceof Field) {
            Field f = (Field)left;
            result = f.isNotDistinctFrom(this.toField(this.parseConcat()));
        } else {
            result = new RowIsDistinctFrom((Row)left, this.parseRow(((Row)left).size(), true), true);
        }
        this.parse(')');
        return result;
    }

    private final Condition parsePredicateXMLExistsIf() {
        if (this.parseKeywordIf("XMLEXISTS")) {
            this.parse('(');
            Field<String> xpath = this.parseField();
            QOM.XMLPassingMechanism m4 = this.parseXMLPassingMechanism();
            Field<XML> xml = this.parseField();
            this.parse(')');
            if (m4 == QOM.XMLPassingMechanism.BY_REF) {
                return DSL.xmlexists(xpath).passingByRef(xml);
            }
            if (m4 == QOM.XMLPassingMechanism.BY_VALUE) {
                return DSL.xmlexists(xpath).passingByValue(xml);
            }
            return DSL.xmlexists(xpath).passing(xml);
        }
        return null;
    }

    private final Condition parsePredicateJSONExistsIf() {
        if (this.parseKeywordIf("JSON_EXISTS")) {
            this.parse('(');
            Field<JSON> json = this.parseField();
            this.parse(',');
            Field<String> path = this.parseField();
            JSONExists.Behaviour b = this.parseJSONExistsOnErrorBehaviourIf();
            this.parse(')');
            if (b == JSONExists.Behaviour.ERROR) {
                return DSL.jsonExists(json, path).errorOnError();
            }
            if (b == JSONExists.Behaviour.TRUE) {
                return DSL.jsonExists(json, path).trueOnError();
            }
            if (b == JSONExists.Behaviour.FALSE) {
                return DSL.jsonExists(json, path).falseOnError();
            }
            if (b == JSONExists.Behaviour.UNKNOWN) {
                return DSL.jsonExists(json, path).unknownOnError();
            }
            return DSL.jsonExists(json, path);
        }
        return null;
    }

    private final QueryPart parseEscapeClauseIf(LikeEscapeStep like) {
        return this.parseKeywordIf("ESCAPE") ? like.escape(this.parseCharacterLiteral()) : like;
    }

    @Override
    public final Table<?> parseTable() {
        return this.parseJoinedTable(() -> this.peekKeyword(KEYWORDS_IN_SELECT_FROM));
    }

    private final Table<?> parseLateral(BooleanSupplier forbiddenKeywords) {
        if (this.parseKeywordIf("LATERAL")) {
            return DSL.lateral(this.parseTableFactor(forbiddenKeywords));
        }
        return this.parseTableFactor(forbiddenKeywords);
    }

    private final <R extends Record> Table<R> t(TableLike<R> table) {
        return this.t(table, false);
    }

    private final <R extends Record> Table<R> t(TableLike<R> table, boolean dummyAlias) {
        Table<R> t2;
        return table instanceof Table ? (t2 = (Table<R>)table) : (dummyAlias ? table.asTable("x") : table.asTable());
    }

    /*
     * WARNING - void declaration
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private final Table<?> parseTableFactor(BooleanSupplier forbiddenKeywords) {
        void var2_46;
        Table<?> table = this.listener.parseTable(this);
        if (table == null) {
            if (this.parseFunctionNameIf("OLD TABLE")) {
                this.parse('(');
                Query query = this.parseQuery(false, false);
                this.parse(')');
                if (query instanceof Merge) {
                    q = (Merge)query;
                    Table table2 = DSL.oldTable(q);
                } else if (query instanceof Update) {
                    Update update = (Update)query;
                    Table table3 = DSL.oldTable(update);
                } else {
                    if (!(query instanceof Delete)) throw this.expected("UPDATE", "DELETE", "MERGE");
                    Delete delete = (Delete)query;
                    Table table4 = DSL.oldTable(delete);
                }
            } else if (this.parseFunctionNameIf("NEW TABLE")) {
                this.parse('(');
                Query query = this.parseQuery(false, false);
                this.parse(')');
                if (query instanceof Merge) {
                    q = (Merge)query;
                    Table table5 = DSL.newTable(q);
                } else if (query instanceof Insert) {
                    Insert insert = (Insert)query;
                    Table table6 = DSL.newTable(insert);
                } else {
                    if (!(query instanceof Update)) throw this.expected("INSERT", "UPDATE", "MERGE");
                    Update update = (Update)query;
                    Table table7 = DSL.newTable(update);
                }
            } else if (this.parseFunctionNameIf("FINAL TABLE")) {
                this.parse('(');
                Query query = this.parseQuery(false, false);
                this.parse(')');
                if (query instanceof Merge) {
                    q = (Merge)query;
                    Table table8 = DSL.finalTable(q);
                } else if (query instanceof Insert) {
                    Insert insert = (Insert)query;
                    Table table9 = DSL.finalTable(insert);
                } else {
                    if (!(query instanceof Update)) throw this.expected("INSERT", "UPDATE", "MERGE");
                    Update update = (Update)query;
                    Table table10 = DSL.finalTable(update);
                }
            } else if (this.parseFunctionNameIf("UNNEST", "TABLE")) {
                this.parse('(');
                if (this.parseFunctionNameIf("GENERATOR")) {
                    this.parse('(');
                    Field<?> field2 = this.parseFunctionArgumentIf("TIMELIMIT");
                    Field<Integer> field3 = this.parseFunctionArgumentIf("ROWCOUNT");
                    if (field2 == null) {
                        Field<?> field4 = this.parseFunctionArgumentIf("TIMELIMIT");
                    }
                    this.parse(')');
                    Table<Record1<Integer>> table11 = DSL.generateSeries(DSL.one(), field3);
                } else {
                    void var3_73;
                    Field<?> field5 = this.parseField();
                    if (!field5.getType().isArray()) {
                        Field<T[]> field6 = field5.coerce(field5.getDataType().getArrayDataType());
                    }
                    Table<?> table12 = DSL.unnest(var3_73);
                }
                this.parse(')');
            } else if (this.parseFunctionNameIf("GENERATE_SERIES", "SYSTEM_RANGE")) {
                this.parse('(');
                Field<Integer> field7 = this.toField(this.parseConcat());
                this.parse(',');
                Field<Integer> field8 = this.toField(this.parseConcat());
                Field<Integer> step = this.parseIf(',') ? this.toField(this.parseConcat()) : null;
                this.parse(')');
                Table<Record1<Integer>> table13 = step == null ? DSL.generateSeries(field7, field8) : DSL.generateSeries(field7, field8, step);
            } else if (this.parseFunctionNameIf("JSON_TABLE")) {
                this.parse('(');
                Field<JSON> field9 = this.parseField();
                this.parse(',');
                Field<String> field10 = this.toField(this.parseConcat());
                s1 = (JSONTableColumnsStep)DSL.jsonTable(field9, field10);
                this.parseKeyword("COLUMNS");
                this.parse('(');
                do {
                    Name name = this.parseIdentifier();
                    if (this.parseKeywordIf("FOR ORDINALITY")) {
                        s1 = s1.column(name).forOrdinality();
                        continue;
                    }
                    s2 = s1.column(name, this.parseDataType());
                    JSONTableColumnsStep jSONTableColumnsStep = s1 = this.parseKeywordIf("PATH") ? s2.path(this.parseStringLiteral()) : s2;
                } while (this.parseIf(','));
                this.parse(')');
                this.parse(')');
                JSONTableColumnsStep jSONTableColumnsStep = s1;
            } else if (this.parseFunctionNameIf("OPENJSON") && this.requireProEdition()) {
                Object var2_20 = null;
                this.parse('(');
                Field<JSON> field11 = this.parseField();
                Param<String> param2 = this.parseIf(',') ? this.toField(this.parseConcat()) : DSL.inline("$");
                s1 = (JSONTableColumnsStep)DSL.jsonTable(field11, param2);
                this.parse(')');
                this.parseKeyword("WITH");
                this.parse('(');
                do {
                    Name name = this.parseIdentifier();
                    s2 = s1.column(name, this.parseDataType());
                    String p = this.parseStringLiteralIf();
                    JSONTableColumnsStep jSONTableColumnsStep = s1 = p != null ? s2.path(p) : s2;
                } while (this.parseIf(','));
                this.parse(')');
                JSONTableColumnsStep jSONTableColumnsStep = s1;
            } else if (this.peekFunctionNameIf("VALUES")) {
                Table<?> table14 = this.parseTableValueConstructor();
            } else if (this.parseFunctionNameIf("XMLTABLE")) {
                void var6_104;
                Field<XML> passing;
                this.parse('(');
                XMLTablePassingStep xMLTablePassingStep = DSL.xmltable(this.toField(this.parseConcat()));
                QOM.XMLPassingMechanism xMLPassingMechanism = this.parseXMLPassingMechanismIf();
                Field<XML> field12 = passing = xMLPassingMechanism == null ? null : this.parseField();
                XMLTableColumnsStep xMLTableColumnsStep = (XMLTableColumnsStep)(xMLPassingMechanism == QOM.XMLPassingMechanism.BY_REF ? xMLTablePassingStep.passingByRef(passing) : (xMLPassingMechanism == QOM.XMLPassingMechanism.BY_VALUE ? xMLTablePassingStep.passingByValue(passing) : (xMLPassingMechanism == QOM.XMLPassingMechanism.DEFAULT ? xMLTablePassingStep.passing(passing) : xMLTablePassingStep)));
                this.parseKeyword("COLUMNS");
                do {
                    XMLTableColumnPathStep xMLTableColumnPathStep;
                    void var6_101;
                    Name fieldName = this.parseIdentifier();
                    if (this.parseKeywordIf("FOR ORDINALITY")) {
                        XMLTableColumnsStep xMLTableColumnsStep2 = var6_101.column(fieldName).forOrdinality();
                        continue;
                    }
                    XMLTableColumnPathStep s3 = var6_101.column(fieldName, this.parseDataType());
                    XMLTableColumnsStep xMLTableColumnsStep3 = xMLTableColumnPathStep = this.parseKeywordIf("PATH") ? s3.path(this.parseStringLiteral()) : s3;
                } while (this.parseIf(','));
                this.parse(')');
                void var2_23 = var6_104;
            } else if (this.parseIf('(')) {
                if (this.peekKeyword("SELECT", "SEL", "WITH")) {
                    SelectQueryImpl<Record> selectQueryImpl = this.parseWithOrSelect();
                    this.parse(')');
                    SelectQueryImpl<Record> selectQueryImpl2 = this.parseQueryExpressionBody(null, null, selectQueryImpl);
                } else if (this.peekKeyword("VALUES")) {
                    Table<?> table15 = this.parseTableValueConstructor();
                    this.parse(')');
                } else {
                    Table<?> table16 = this.parseJoinedTable(forbiddenKeywords);
                    this.parse(')');
                }
            } else {
                Table<?> table17 = this.parseTableName();
            }
        }
        if (!this.ignoreProEdition() && this.parseKeywordIf("VERSIONS BETWEEN") && this.requireProEdition()) {
            boolean minvalue;
            this.forbidden.add(FunctionKeyword.FK_AND);
            boolean bl = this.parseKeywordIf("SCN");
            if (!bl) {
                this.parseKeyword("TIMESTAMP");
            }
            if (minvalue = this.parseKeywordIf("MINVALUE")) {
                if (bl) {
                    VersionsBetweenAndStep versionsBetweenAndStep = this.t((TableLike)var2_28).versionsBetweenScnMinvalue();
                } else {
                    VersionsBetweenAndStep versionsBetweenAndStep = this.t((TableLike)var2_28).versionsBetweenTimestampMinvalue();
                }
            } else if (bl) {
                VersionsBetweenAndStep versionsBetweenAndStep = this.t((TableLike)var2_28).versionsBetweenScn(this.parseField());
            } else {
                VersionsBetweenAndStep versionsBetweenAndStep = this.t((TableLike)var2_28).versionsBetweenTimestamp(this.parseField());
            }
            this.parseKeyword("AND");
            this.forbidden.remove((Object)FunctionKeyword.FK_AND);
            boolean bl2 = this.parseKeywordIf("MAXVALUE");
            if (bl2) {
                Table table18 = var3_85.andMaxvalue();
            } else if (bl) {
                Table table19 = var3_85.and(this.parseField());
            } else {
                Table table20 = var3_85.and(this.parseField());
            }
        } else if (!this.ignoreProEdition() && this.parseForPeriodIf() && this.requireProEdition()) {
            void var3_87;
            boolean bl = false;
            do {
                Period<Timestamp> period;
                this.forbidden.add(FunctionKeyword.FK_AND);
                boolean bl3 = this.parseKeywordIf("PORTION OF");
                BiFunction<Table, PeriodSpecification, Table> f = (t1, t2) -> bl3 ? t1.forPortionOf((PeriodSpecification)t2) : t1.for_((PeriodSpecification)t2);
                Period<Timestamp> period2 = period = this.parseKeywordIf("SYSTEM_TIME", "SYSTEM TIME") ? DSL.systemTime() : DSL.period(this.parseIdentifier());
                if (this.parseKeywordIf("ALL")) {
                    TableLike tableLike = f.apply(this.t((TableLike)var2_33), period.all());
                } else if (this.parseKeywordIf("AS OF")) {
                    TableLike tableLike = f.apply(this.t((TableLike)var2_33), period.asOf(this.parseField()));
                } else if (this.parseKeywordIf("BETWEEN")) {
                    if (!this.parseKeywordIf("ASYMMETRIC")) {
                        // empty if block
                    }
                    TableLike tableLike = f.apply(this.t((TableLike)var2_33), period.between(this.parseField()).and(this.parseKeyword("AND") ? this.parseField() : null));
                } else if (this.parseKeywordIf("FROM")) {
                    TableLike tableLike = f.apply(this.t((TableLike)var2_33), period.from(this.parseField()).to(this.parseKeyword("TO") ? this.parseField() : null));
                } else {
                    if (!this.parseKeywordIf("CONTAINED IN")) throw this.expected("ALL", "AS OF", "BETWEEN", "FROM", "CONTAINED IN");
                    this.parse('(');
                    TableLike tableLike = f.apply(this.t((TableLike)var2_33), period.containedIn(this.parseField(), this.parse(",") ? this.parseField() : null));
                    this.parse(')');
                }
                this.forbidden.remove((Object)FunctionKeyword.FK_AND);
            } while (++var3_87 < 2 && this.parseForPeriodIf());
        } else if (!this.ignoreProEdition() && this.parseKeywordIf("AS OF") && this.requireProEdition()) {
            boolean bl = this.parseKeywordIf("SCN");
            if (!bl) {
                this.parseKeyword("TIMESTAMP");
            }
            if (bl) {
                Table table21 = this.t((TableLike)var2_28).asOfScn(this.parseField());
            } else {
                Table table22 = this.t((TableLike)var2_28).asOfTimestamp(this.parseField());
            }
        }
        if (this.parseKeywordIf("WITH ORDINALITY")) {
            void var2_42;
            Table<Record> table23 = this.t((TableLike)var2_42).withOrdinality();
        }
        if (!this.ignoreProEdition() && this.parseKeywordIf("PIVOT") && this.requireProEdition()) {
            void var2_44;
            this.parse('(');
            ArrayList<void> arrayList = new ArrayList<void>();
            ArrayList<SelectField<Object>> in = new ArrayList<SelectField<Object>>();
            do {
                void var6_112;
                Name alias;
                Field<?> field13;
                if ((field13 = this.parseAggregateFunctionIf(true)) == null) {
                    throw this.expected("Aggregate function");
                }
                if (this.parseKeywordIf("AS")) {
                    SelectField selectField = field13.as(this.parseIdentifier());
                } else if (!this.peekKeyword(PIVOT_KEYWORDS) && (alias = this.parseIdentifierIf()) != null) {
                    SelectField selectField = field13.as(alias);
                }
                arrayList.add(var6_112);
            } while (this.parseIf(','));
            this.parseKeyword("FOR");
            boolean bl = this.parseIf('(');
            Name name = this.parseIdentifier();
            if (bl) {
                this.parse(')');
            }
            this.parseKeyword("IN");
            this.parse('(');
            do {
                SelectField<Object> field14 = this.parseField();
                if (this.parseKeywordIf("AS")) {
                    field14 = field14.as(this.parseIdentifier());
                } else {
                    Name alias = this.parseIdentifierIf();
                    if (alias != null) {
                        field14 = field14.as(alias);
                    }
                }
                in.add(field14);
            } while (this.parseIf(','));
            this.parse(')');
            this.parse(')');
            Table<Record> table24 = this.t((TableLike)var2_44).pivot(arrayList).for_(DSL.field(name)).in((Collection<Field<Object>>)in);
        }
        TableLike<?> tableLike = this.parseCorrelationNameIf((TableLike<?>)var2_46, forbiddenKeywords);
        int n = this.position();
        if (!this.peekKeyword("WITH CHECK OPTION", "WITH READ ONLY") && this.parseKeywordIf("WITH")) {
            if (!this.ignoreProEdition() && this.parseIf('(') && this.requireProEdition()) {
                int n2 = this.position();
                this.parseSQLServerTableHints();
                Table<?> table25 = this.t(tableLike).with(this.substring(n2, this.position()));
                this.parse(')');
                return this.t((TableLike)var2_64);
            } else {
                this.position(n);
            }
            return this.t((TableLike)var2_64);
        } else if (!this.ignoreProEdition() && this.parseIf('(') && this.requireProEdition()) {
            int n3 = this.position();
            Keyword hint = this.parseSQLServerTableHintTokenIf();
            if (hint != null) {
                Table<?> table26 = this.t(tableLike).with(this.substring(n3, this.position()));
                this.parse(')');
                return this.t((TableLike)var2_64);
            } else {
                this.position(n);
            }
            return this.t((TableLike)var2_64);
        } else {
            while (true) {
                void var2_51;
                if (this.parseKeywordIf("USE KEY", "USE INDEX")) {
                    if (this.parseKeywordIf("FOR JOIN")) {
                        Table table27 = this.t((TableLike)var2_51).useIndexForJoin(this.parseParenthesisedIdentifiers());
                        continue;
                    }
                    if (this.parseKeywordIf("FOR ORDER BY")) {
                        Table table28 = this.t((TableLike)var2_51).useIndexForOrderBy(this.parseParenthesisedIdentifiers());
                        continue;
                    }
                    if (this.parseKeywordIf("FOR GROUP BY")) {
                        Table table29 = this.t((TableLike)var2_51).useIndexForGroupBy(this.parseParenthesisedIdentifiers());
                        continue;
                    }
                    Table table30 = this.t((TableLike)var2_51).useIndex(this.parseParenthesisedIdentifiers());
                    continue;
                }
                if (this.parseKeywordIf("FORCE KEY", "FORCE INDEX")) {
                    if (this.parseKeywordIf("FOR JOIN")) {
                        Table table31 = this.t((TableLike)var2_51).forceIndexForJoin(this.parseParenthesisedIdentifiers());
                        continue;
                    }
                    if (this.parseKeywordIf("FOR ORDER BY")) {
                        Table table32 = this.t((TableLike)var2_51).forceIndexForOrderBy(this.parseParenthesisedIdentifiers());
                        continue;
                    }
                    if (this.parseKeywordIf("FOR GROUP BY")) {
                        Table table33 = this.t((TableLike)var2_51).forceIndexForGroupBy(this.parseParenthesisedIdentifiers());
                        continue;
                    }
                    Table table34 = this.t((TableLike)var2_51).forceIndex(this.parseParenthesisedIdentifiers());
                    continue;
                }
                if (!this.parseKeywordIf("IGNORE KEY", "IGNORE INDEX")) return this.t((TableLike)var2_64);
                if (this.parseKeywordIf("FOR JOIN")) {
                    Table table35 = this.t((TableLike)var2_51).ignoreIndexForJoin(this.parseParenthesisedIdentifiers());
                    continue;
                }
                if (this.parseKeywordIf("FOR ORDER BY")) {
                    Table table36 = this.t((TableLike)var2_51).ignoreIndexForOrderBy(this.parseParenthesisedIdentifiers());
                    continue;
                }
                if (this.parseKeywordIf("FOR GROUP BY")) {
                    Table table37 = this.t((TableLike)var2_51).ignoreIndexForGroupBy(this.parseParenthesisedIdentifiers());
                    continue;
                }
                Table table38 = this.t((TableLike)var2_51).ignoreIndex(this.parseParenthesisedIdentifiers());
            }
        }
    }

    private final boolean parseForPeriodIf() {
        return this.peekKeyword("FOR") && !this.peekKeyword("FOR JSON") && !this.peekKeyword("FOR KEY SHARE") && !this.peekKeyword("FOR NO KEY UPDATE") && !this.peekKeyword("FOR SHARE") && !this.peekKeyword("FOR UPDATE") && !this.peekKeyword("FOR XML") && this.parseKeyword("FOR");
    }

    private final String[] parseParenthesisedIdentifiers() {
        return this.parseParenthesised(c -> Tools.map(this.parseIdentifiers(), Name::last, String[]::new));
    }

    private final Field<?> parseFunctionArgumentIf(String parameterName) {
        if (this.parseKeywordIf(parameterName) && this.parse("=>")) {
            return this.parseField();
        }
        return null;
    }

    private final TableLike<?> parseCorrelationNameIf(TableLike<?> result, BooleanSupplier forbiddenKeywords) {
        Name alias = null;
        List<Name> columnAliases = null;
        if (this.parseKeywordIf("AS")) {
            alias = this.parseIdentifier();
        } else if (!forbiddenKeywords.getAsBoolean()) {
            alias = this.parseIdentifierIf();
        }
        if (alias != null) {
            if (this.parseIf('(')) {
                columnAliases = this.parseIdentifiers();
                this.parse(')');
            }
            result = columnAliases != null ? this.t((TableLike)result, true).as(alias, columnAliases) : this.t((TableLike)result, true).as(alias);
        }
        return result;
    }

    @Pro
    private final void parseSQLServerTableHints() {
        do {
            if (this.parseKeywordIf("INDEX")) {
                if (this.parseIf('(')) {
                    this.parseIdentifiers();
                    this.parse(')');
                    continue;
                }
                this.parse('=');
                this.parse('(');
                this.parseIdentifier();
                this.parse(')');
                continue;
            }
            if (this.parseKeywordIf("FORCESEEK")) {
                if (!this.parseIf('(')) continue;
                this.parseIdentifier();
                this.parse('(');
                this.parseIdentifiers();
                this.parse(')');
                this.parse(')');
                continue;
            }
            if (this.parseKeywordIf("SPATIAL_WINDOW_MAX_CELLS")) {
                this.parse('=');
                this.parseUnsignedIntegerLiteral();
                continue;
            }
            if (this.parseSQLServerTableHintTokenIf() != null) continue;
            throw this.exception("Unsupported or missing table hint");
        } while (this.parseIf(','));
    }

    private final Keyword parseSQLServerTableHintTokenIf() {
        return this.parseAndGetKeywordIf("FORCESCAN", "FORCESEEK", "HOLDLOCK", "NOEXPAND", "NOLOCK", "NOWAIT", "PAGLOCK", "READCOMMITTED", "READCOMMITTEDLOCK", "READPAST", "READUNCOMMITTED", "REPEATABLEREAD", "ROWLOCK", "SERIALIZABLE", "SNAPSHOT", "TABLOCK", "TABLOCKX", "UPDLOCK", "XLOCK", "KEEPIDENTITY", "KEEPDEFAULTS", "IGNORE_CONSTRAINTS", "IGNORE_TRIGGERS");
    }

    private final Row parseTableValueConstructorRow(Integer degree) {
        if (this.parseKeywordIf("ROW")) {
            return this.parseTuple(degree);
        }
        Field<?> r = null;
        if (degree == null || degree == 1) {
            r = this.parseScalarSubqueryIf();
        }
        if (r != null) {
            return DSL.row(r);
        }
        if (this.peek('(')) {
            return this.parseTuple(degree);
        }
        if (degree == null || degree == 1) {
            return DSL.row(this.parseField());
        }
        throw this.exception("Expected row of degree: " + degree);
    }

    private final Table<?> parseTableValueConstructor() {
        this.parseKeyword("VALUES");
        ArrayList<Row> rows = new ArrayList<Row>();
        Integer degree = null;
        do {
            Row row = this.parseTableValueConstructorRow(degree);
            rows.add(row);
            if (degree != null) continue;
            degree = row.size();
        } while (this.parseIf(','));
        return DSL.values0(rows.toArray(Tools.EMPTY_ROW));
    }

    private final Table<?> parseExplicitTable() {
        this.parseKeyword("TABLE");
        return this.parseTableName();
    }

    private final Row parseTuple() {
        return this.parseTuple(null, false);
    }

    private final Row parseTuple(Integer degree) {
        return this.parseTuple(degree, false);
    }

    private final Row parseTupleIf(Integer degree) {
        return this.parseTupleIf(degree, false);
    }

    private final Row parseTuple(Integer degree, boolean allowDoubleParens) {
        Row row;
        this.parse('(');
        List<FieldOrRow> fieldsOrRows = allowDoubleParens ? this.parseList(',', (? super ParseContext c) -> this.parseFieldOrRow()) : this.parseList(',', (? super ParseContext c) -> c.parseField());
        if (fieldsOrRows.size() == 0) {
            row = DSL.row(new SelectField[0]);
        } else if (fieldsOrRows.get(0) instanceof Field) {
            row = DSL.row(fieldsOrRows);
        } else if (fieldsOrRows.size() == 1) {
            row = (Row)fieldsOrRows.get(0);
        } else {
            throw this.exception("Unsupported row size");
        }
        if (degree != null && row.size() != degree.intValue()) {
            throw this.exception("Expected row of degree: " + degree + ". Got: " + row.size());
        }
        this.parse(')');
        return row;
    }

    private final Row parseTupleIf(Integer degree, boolean allowDoubleParens) {
        if (this.peek('(')) {
            return this.parseTuple(degree, allowDoubleParens);
        }
        return null;
    }

    private final Table<?> parseJoinedTable(BooleanSupplier forbiddenKeywords) {
        Table<?> result = this.parseLateral(forbiddenKeywords);
        Table<?> joined;
        while ((joined = this.parseJoinedTableIf(result, forbiddenKeywords)) != null) {
            result = joined;
        }
        return result;
    }

    private final Table<?> parseJoinedTableIf(Table<?> left, BooleanSupplier forbiddenKeywords) {
        TablePartitionByStep s1;
        JoinType joinType;
        int p = this.position();
        if (!this.ignoreProEdition() && this.parseKeywordIf("PARTITION BY") && this.requireProEdition()) {
            this.parse('(');
            TableOuterJoinStep<Record> s0 = left.partitionBy(this.parseList(',', (? super ParseContext c) -> c.parseField()));
            this.parse(')');
            JoinType joinType2 = this.parseJoinTypeIf();
            if (joinType2 != null) {
                switch (joinType2) {
                    case LEFT_OUTER_JOIN: 
                    case FULL_OUTER_JOIN: 
                    case RIGHT_OUTER_JOIN: {
                        TablePartitionByStep<Record> s12;
                        Table<?> right = this.parseJoinedTable(forbiddenKeywords);
                        TableOnStep<Record> s2 = s12 = s0.join(right, joinType2);
                        if (this.parseKeywordIf("PARTITION BY")) {
                            this.parse('(');
                            s2 = s12.partitionBy(this.parseList(',', (? super ParseContext c) -> c.parseField()));
                            this.parse(')');
                        }
                        if (this.parseKeywordIf("ON")) {
                            return s2.on(this.parseCondition());
                        }
                        if (this.parseKeywordIf("USING")) {
                            return this.parseJoinUsing(s2);
                        }
                        throw this.expected("ON", "USING");
                    }
                }
                throw this.expected("OUTER JOIN");
            }
            this.position(p);
        }
        if ((joinType = this.parseJoinTypeIf()) == null) {
            return null;
        }
        Table<?> right = joinType.qualified() ? this.parseJoinedTable(forbiddenKeywords) : this.parseLateral(forbiddenKeywords);
        TableOptionalOnStep<Record> s0 = left.join(right, joinType);
        TableOnStep s2 = s1 = (TablePartitionByStep)((Object)s0);
        switch (joinType) {
            case LEFT_OUTER_JOIN: 
            case FULL_OUTER_JOIN: 
            case RIGHT_OUTER_JOIN: {
                if (!this.ignoreProEdition() && this.parseKeywordIf("PARTITION BY") && this.requireProEdition()) {
                    this.parse('(');
                    s2 = s1.partitionBy(this.parseList(',', (? super ParseContext c) -> c.parseField()));
                    this.parse(')');
                }
            }
            case JOIN: 
            case STRAIGHT_JOIN: 
            case LEFT_SEMI_JOIN: 
            case LEFT_ANTI_JOIN: {
                if (this.parseKeywordIf("ON")) {
                    return s2.on(this.parseCondition());
                }
                if (this.parseKeywordIf("USING")) {
                    return this.parseJoinUsing(s2);
                }
                if (joinType == JoinType.JOIN) {
                    return s0;
                }
                throw this.expected("ON", "USING");
            }
            case CROSS_JOIN: {
                if (this.parseKeywordIf("ON")) {
                    return left.join(right).on(this.parseCondition());
                }
                if (!this.parseKeywordIf("USING")) break;
                return this.parseJoinUsing(left.join(right));
            }
        }
        return s0;
    }

    private final Table<?> parseJoinUsing(TableOnStep<?> join) {
        Table<Record> result;
        this.parse('(');
        if (this.parseIf(')')) {
            result = join.using(new Field[0]);
        } else {
            result = join.using(Tools.fieldsByName(this.parseIdentifiers().toArray(Tools.EMPTY_NAME)));
            this.parse(')');
        }
        return result;
    }

    private final List<SelectFieldOrAsterisk> parseSelectList() {
        ArrayList<SelectFieldOrAsterisk> result = new ArrayList<SelectFieldOrAsterisk>();
        do {
            if (this.parseIf('*')) {
                if (this.parseKeywordIf("EXCEPT")) {
                    this.parse('(');
                    result.add(DSL.asterisk().except(this.parseList(',', (? super ParseContext c) -> this.parseFieldName()).toArray(Tools.EMPTY_FIELD)));
                    this.parse(')');
                    continue;
                }
                result.add(DSL.asterisk());
                continue;
            }
            QualifiedAsterisk qa = this.parseQualifiedAsteriskIf();
            if (qa != null) {
                if (this.parseKeywordIf("EXCEPT")) {
                    this.parse('(');
                    result.add(qa.except(this.parseList(',', (? super ParseContext c) -> this.parseFieldName()).toArray(Tools.EMPTY_FIELD)));
                    this.parse(')');
                    continue;
                }
                result.add(qa);
                continue;
            }
            Name alias = null;
            SelectField<?> field2 = null;
            int p = this.position();
            if (SUPPORTS_TSQL_ALIASING_SYNTAX.contains((Object)this.parseDialect()) && (alias = this.parseIdentifierIf()) != null) {
                if (this.parseIf('=')) {
                    field2 = this.parseField();
                } else {
                    this.position(p);
                    alias = null;
                }
            }
            if (field2 == null) {
                field2 = this.parseSelectField();
                if (this.parseKeywordIf("AS")) {
                    alias = this.parseIdentifier(true, false);
                } else if (!this.peekKeyword(KEYWORDS_IN_SELECT) && !this.peekKeyword(KEYWORDS_IN_STATEMENTS)) {
                    alias = this.parseIdentifierIf(true, false);
                }
            }
            result.add(alias == null ? field2 : field2.as(alias));
        } while (this.parseIf(','));
        return result;
    }

    @Override
    public final SortField<?> parseSortField() {
        Field<?> field2 = this.parseField();
        SortField<?> sort = this.parseKeywordIf("DESC") ? field2.desc() : (this.parseKeywordIf("ASC") ? field2.asc() : field2.sortDefault());
        if (this.parseKeywordIf("NULLS FIRST")) {
            sort = sort.nullsFirst();
        } else if (this.parseKeywordIf("NULLS LAST")) {
            sort = sort.nullsLast();
        }
        return sort;
    }

    private final List<Field<?>> parseFieldsOrEmptyParenthesised() {
        this.parse('(');
        if (this.parseIf(')')) {
            return Collections.emptyList();
        }
        List<Field<?>> result = this.parseList(',', (? super ParseContext c) -> c.parseField());
        this.parse(')');
        return result;
    }

    private final SelectField<?> parseSelectField() {
        return (SelectField)((Object)this.parseFieldOrRow());
    }

    private final Row parseRow() {
        return this.parseRow(null);
    }

    private final Row parseRowIf() {
        return this.parseRowIf(null);
    }

    private final Row parseRow(Integer degree) {
        this.parseFunctionNameIf("ROW");
        return this.parseTuple(degree);
    }

    private final Row parseRowIf(Integer degree) {
        this.parseFunctionNameIf("ROW");
        return this.parseTupleIf(degree);
    }

    private final Row parseRow(Integer degree, boolean allowDoubleParens) {
        this.parseFunctionNameIf("ROW");
        return this.parseTuple(degree, allowDoubleParens);
    }

    public final FieldOrRow parseFieldOrRow() {
        return this.toFieldOrRow(this.parseOr());
    }

    @Override
    public final Field<?> parseField() {
        return this.toField(this.parseOr());
    }

    private final String parseHints() {
        StringBuilder sb = new StringBuilder();
        do {
            int i;
            int p = this.position();
            if (!this.parseIf('/', false)) continue;
            this.parse('*', false);
            block4: for (i = this.position(); i < this.sql.length; ++i) {
                switch (this.sql[i]) {
                    case '*': {
                        if (i + 1 < this.sql.length && this.sql[i + 1] == '/') break block4;
                    }
                    default: {
                        continue block4;
                    }
                }
            }
            this.position(i + 2);
            if (sb.length() > 0) {
                sb.append(' ');
            }
            sb.append(this.substring(p, this.position()));
        } while (this.parseWhitespaceIf());
        this.ignoreHints(true);
        return sb.length() > 0 ? sb.toString() : null;
    }

    private final Condition toCondition(QueryPart part) {
        if (part == null) {
            return null;
        }
        if (part instanceof Condition) {
            Condition c = (Condition)part;
            return c;
        }
        if (part instanceof Field) {
            Field f = (Field)part;
            DataType dataType = f.getDataType();
            Class type = dataType.getType();
            if (dataType.isBoolean()) {
                return DSL.condition(f);
            }
            if (dataType.isNumeric()) {
                return f.ne(DSL.zero());
            }
            if (type == Object.class && (part instanceof TableFieldImpl || part instanceof Val)) {
                return DSL.condition((Field)part);
            }
            throw this.expected("Boolean field");
        }
        throw this.expected("Condition");
    }

    private final FieldOrRow toFieldOrRow(QueryPart part) {
        if (part == null) {
            return null;
        }
        if (part instanceof Field) {
            Field f = (Field)part;
            return f;
        }
        if (part instanceof Row) {
            Row r = (Row)part;
            return r;
        }
        throw this.expected("Field or row");
    }

    private final Field<?> toField(QueryPart part) {
        if (part == null) {
            return null;
        }
        if (part instanceof Field) {
            Field f = (Field)part;
            return f;
        }
        throw this.expected("Field");
    }

    private final FieldOrRow parseConcat() {
        Field<?> r = this.parseCollated();
        if (r instanceof Field) {
            while (this.parseIf("||") || this.parseFamily() == SQLDialect.ORACLE && this.parseTokensIf('|', '|')) {
                r = this.concatOperator(r, this.toField(this.parseCollated()));
            }
        }
        return r;
    }

    private final Field<?> concatOperator(Field<?> a1, Field<?> a2) {
        if (a1.getDataType().isArray() && a2.getDataType().isArray()) {
            return DSL.arrayConcat(a1, a2);
        }
        return DSL.concat(a1, a2);
    }

    private final FieldOrRow parseCollated() {
        SelectField<String> r = this.parseNumericOp();
        if (r instanceof Field) {
            if (this.parseKeywordIf("COLLATE")) {
                r = ((Field)r).collate(this.parseCollation());
            }
            if (this.parseFamily() == SQLDialect.TERADATA) {
                int p = this.position();
                int i = 0;
                block0: while (this.parseIf('(')) {
                    do {
                        if (this.parseKeywordIf("TITLE")) {
                            this.parseStringLiteral();
                            continue;
                        }
                        if (this.parseKeywordIf("NAMED")) {
                            r = r.as(this.parseIdentifier());
                            continue;
                        }
                        DataType<?> t2 = this.parseDataTypeIf(false);
                        if (t2 == null) {
                            if (i == 0) {
                                this.position(p);
                                break block0;
                            }
                            throw this.expected("Data type");
                        }
                        r = r.cast(t2);
                    } while (this.parseIf(','));
                    this.parse(')');
                    ++i;
                }
            }
        }
        return r;
    }

    private final Field<?> parseFieldNumericOpParenthesised() {
        this.parse('(');
        Field<?> r = this.toField(this.parseNumericOp());
        this.parse(')');
        return r;
    }

    private final Field<?> parseFieldParenthesised() {
        this.parse('(');
        Field<?> r = this.parseField();
        this.parse(')');
        return r;
    }

    private final <Q extends QueryPart> Q parseFunctionArgs1(Function1<? super Field, ? extends Q> finisher) {
        this.parse('(');
        Field<?> f1 = this.parseField();
        this.parse(')');
        return (Q)((QueryPart)finisher.apply(f1));
    }

    private final <Q extends QueryPart> Q parseFunctionArgs2(Function1<? super Field, ? extends Q> finisher1, Function2<? super Field, ? super Field, ? extends Q> finisher2) {
        this.parse('(');
        Field<?> f1 = this.parseField();
        Field<?> f2 = this.parseIf(',') ? this.parseField() : null;
        this.parse(')');
        return (Q)(f2 == null ? (QueryPart)finisher1.apply(f1) : (QueryPart)finisher2.apply(f1, f2));
    }

    private final <Q extends QueryPart> Q parseFunctionArgs2(Function2<? super Field, ? super Field, ? extends Q> finisher) {
        return this.parseFunctionArgs2(this::parseField, finisher);
    }

    private final <Q extends QueryPart> Q parseFunctionArgs2(Supplier<? extends Field<?>> argument, Function2<? super Field, ? super Field, ? extends Q> finisher) {
        this.parse('(');
        Field<?> f1 = argument.get();
        this.parse(',');
        Field<?> f2 = argument.get();
        this.parse(')');
        return (Q)((QueryPart)finisher.apply(f1, f2));
    }

    private final <Q extends QueryPart> Q parseFunctionArgs3(Function2<? super Field, ? super Field, ? extends Q> finisher2, Function3<? super Field, ? super Field, ? super Field, ? extends Q> finisher3) {
        this.parse('(');
        Field<?> f1 = this.parseField();
        this.parse(',');
        Field<?> f2 = this.parseField();
        Field<?> f3 = this.parseIf(',') ? this.parseField() : null;
        this.parse(')');
        return (Q)(f3 == null ? (QueryPart)finisher2.apply(f1, f2) : (QueryPart)finisher3.apply(f1, f2, f3));
    }

    private final <Q extends QueryPart> Q parseFunctionArgs3(Function3<? super Field, ? super Field, ? super Field, ? extends Q> finisher) {
        this.parse('(');
        Field<?> f1 = this.parseField();
        this.parse(',');
        Field<?> f2 = this.parseField();
        this.parse(',');
        Field<?> f3 = this.parseField();
        this.parse(')');
        return (Q)((QueryPart)finisher.apply(f1, f2, f3));
    }

    private final <Q extends QueryPart> Q parseFunctionArgs4(Function4<? super Field, ? super Field, ? super Field, ? super Field, ? extends Q> finisher) {
        this.parse('(');
        Field<?> f1 = this.parseField();
        this.parse(',');
        Field<?> f2 = this.parseField();
        this.parse(',');
        Field<?> f3 = this.parseField();
        this.parse(',');
        Field<?> f4 = this.parseField();
        this.parse(')');
        return (Q)((QueryPart)finisher.apply(f1, f2, f3, f4));
    }

    private final boolean parseEmptyParens() {
        return this.parse('(') && this.parse(')');
    }

    private final boolean parseEmptyParensIf() {
        if (!this.parseIf('(') || !this.parse(')')) {
            // empty if block
        }
        return true;
    }

    private final FieldOrRow parseNumericOp() {
        Field<Object> l = this.parseSum();
        if (l instanceof Field) {
            while (true) {
                Field r;
                if (this.parseIf("<<")) {
                    l = ((Field)l).shl((Field)this.parseSum());
                    continue;
                }
                if (this.parseIf(">>")) {
                    l = ((Field)l).shr((Field)this.parseSum());
                    continue;
                }
                if (this.parseIf("->>")) {
                    r = (Field)this.parseSum();
                    if (r.getDataType().isNumeric()) {
                        if (l.getType() == JSON.class) {
                            l = DSL.jsonGetElementAsText(l, (Field<Integer>)r);
                            continue;
                        }
                        l = DSL.jsonbGetElementAsText(l, (Field<Integer>)r);
                        continue;
                    }
                    if (l.getType() == JSON.class) {
                        l = DSL.jsonGetAttributeAsText(l, (Field<String>)r);
                        continue;
                    }
                    l = DSL.jsonbGetAttributeAsText(l, (Field<String>)r);
                    continue;
                }
                if (!this.parseIf("->")) break;
                r = (Field)this.parseSum();
                if (r.getDataType().isNumeric()) {
                    if (l.getType() == JSON.class) {
                        l = DSL.jsonGetElement(l, (Field<Integer>)r);
                        continue;
                    }
                    l = DSL.jsonbGetElement(l, (Field<Integer>)r);
                    continue;
                }
                if (l.getType() == JSON.class) {
                    l = DSL.jsonGetAttribute(l, (Field<String>)r);
                    continue;
                }
                l = DSL.jsonbGetAttribute(l, (Field<String>)r);
            }
        }
        return l;
    }

    private final FieldOrRow parseSum() {
        FieldOrRow r = this.parseFactor();
        if (r instanceof Field) {
            while (true) {
                if (this.parseIf('+')) {
                    r = this.parseSumRightOperand(r, true);
                    continue;
                }
                if (this.peek("->") || !this.parseIf('-')) break;
                r = this.parseSumRightOperand(r, false);
            }
        }
        return r;
    }

    private final Field parseSumRightOperand(FieldOrRow r, boolean add) {
        Field rhs = (Field)this.parseFactor();
        DatePart part = !this.ignoreProEdition() && this.parseKeywordIf("YEAR", "YEARS") && this.requireProEdition() ? DatePart.YEAR : (!this.ignoreProEdition() && this.parseKeywordIf("MONTH", "MONTHS") && this.requireProEdition() ? DatePart.MONTH : (!this.ignoreProEdition() && this.parseKeywordIf("DAY", "DAYS") && this.requireProEdition() ? DatePart.DAY : (!this.ignoreProEdition() && this.parseKeywordIf("HOUR", "HOURS") && this.requireProEdition() ? DatePart.HOUR : (!this.ignoreProEdition() && this.parseKeywordIf("MINUTE", "MINUTES") && this.requireProEdition() ? DatePart.MINUTE : (!this.ignoreProEdition() && this.parseKeywordIf("SECOND", "SECONDS") && this.requireProEdition() ? DatePart.SECOND : null)))));
        Field lhs = (Field)r;
        if (part != null) {
            if (add) {
                return DSL.dateAdd((Field<Date>)lhs, (Field<? extends Number>)rhs, part);
            }
            return DSL.dateSub((Field<Date>)lhs, (Field<? extends Number>)rhs, part);
        }
        if (add && Arrays.asList(SQLDialect.SQLSERVER, SQLDialect.SQLDATAWAREHOUSE).contains((Object)this.parseFamily()) && (lhs.getDataType().isString() && rhs.getDataType().isString() || lhs.getDataType().isBinary() && rhs.getDataType().isBinary())) {
            return lhs.concat(rhs);
        }
        if (add) {
            return lhs.add(rhs);
        }
        if (lhs.getDataType().isDate() && rhs.getDataType().isDate()) {
            return DSL.dateDiff((Field<Date>)lhs, (Field<Date>)rhs);
        }
        if (lhs.getDataType().isTimestamp() && rhs.getDataType().isTimestamp()) {
            return DSL.timestampDiff((Field<Timestamp>)lhs, (Field<Timestamp>)rhs);
        }
        return lhs.sub(rhs);
    }

    private final FieldOrRow parseFactor() {
        Field r = this.parseExp();
        if (r instanceof Field) {
            while (true) {
                if (!this.peek("*=") && this.parseIf('*')) {
                    r = ((Field)r).mul((Field)this.parseExp());
                    continue;
                }
                if (this.parseIf('/')) {
                    r = ((Field)r).div((Field)this.parseExp());
                    continue;
                }
                if (this.parseIf('%')) {
                    r = ((Field)r).mod((Field)this.parseExp());
                    continue;
                }
                if (this.parseFamily() != SQLDialect.ACCESS && this.parseFamily() != SQLDialect.TERADATA || !this.parseKeywordIf("MOD")) break;
                r = r.mod((Field)this.parseExp());
            }
        }
        return r;
    }

    private final FieldOrRow parseExp() {
        Field<BigDecimal> r = this.parseUnaryOps();
        if (r instanceof Field) {
            while (!this.peek("^=") && this.parseIf('^') || this.parseIf("**")) {
                r = ((Field)r).pow(this.toField(this.parseUnaryOps()));
            }
        }
        return r;
    }

    private final FieldOrRow parseUnaryOps() {
        FieldOrRow r;
        if (!this.ignoreProEdition() && this.parseKeywordIf("CONNECT_BY_ROOT") && this.requireProEdition()) {
            return DSL.connectByRoot(this.toField(this.parseTerm()));
        }
        if (this.parseIf('~')) {
            return this.toField(this.parseUnaryOps()).bitNot();
        }
        Sign sign = this.parseSign();
        if (sign == Sign.NONE) {
            r = this.parseTerm();
        } else if (sign == Sign.PLUS) {
            r = this.toField(this.parseTerm());
        } else {
            r = this.parseFieldUnsignedNumericLiteralIf(Sign.MINUS);
            if (r == null) {
                r = this.toField(this.parseTerm()).neg();
            }
        }
        if (!this.ignoreProEdition() && this.parseTokensIf('(', '+', ')') && this.requireProEdition()) {
            r = this.toField(r).plus();
        }
        int p = this.position();
        if (r instanceof TableField && this.parseIf('(')) {
            if (this.parseFamily() == SQLDialect.TERADATA && this.parseDataTypeIf(false) != null) {
                this.position(p);
            } else {
                throw this.exception("Unknown function");
            }
        }
        while (this.parseIf("::")) {
            r = DSL.cast(this.toField(r), this.parseDataType());
        }
        if (!SUPPORTS_TSQL_QUOTING_SYNTAX.contains((Object)this.parseDialect()) && this.parseIf('[')) {
            r = DSL.arrayGet(this.toField(r), this.parseField());
            this.parse(']');
        }
        r = this.parseMethodCallIf(r);
        return r;
    }

    private final FieldOrRow parseMethodCallIf(FieldOrRow r) {
        FieldOrRow previous;
        while ((previous = r) != (r = this.parseMethodCallIf0(r))) {
        }
        return r;
    }

    private final FieldOrRow parseMethodCallIf0(FieldOrRow r) {
        if (r instanceof Field) {
            Field f = (Field)r;
            switch (this.parseFamily()) {
                case ORACLE: {
                    int p = this.position();
                    if (this.parseIf('.')) {
                        if (this.parseKeywordIf("SDO_POINT.X")) {
                            return DSL.stX(f);
                        }
                        if (this.parseKeywordIf("SDO_POINT.Y")) {
                            return DSL.stY(f);
                        }
                        if (this.parseKeywordIf("SDO_POINT.Z")) {
                            return DSL.stZ(f);
                        }
                        if (this.parseKeywordIf("SDO_SRID")) {
                            return DSL.stSrid(f);
                        }
                        if (this.parseFunctionNameIf("GET_WKB") && this.parseEmptyParens()) {
                            return DSL.stAsBinary(f);
                        }
                        if (this.parseFunctionNameIf("GET_WKT") && this.parseEmptyParens()) {
                            return DSL.stAsText(f);
                        }
                        this.position(p);
                    }
                }
                case SQLSERVER: {
                    int p = this.position();
                    if (!this.parseIf('.')) break;
                    if (this.peek("ST")) {
                        if (this.parseFunctionNameIf("STAREA") && this.parseEmptyParens()) {
                            return DSL.stArea(f);
                        }
                        if (this.parseFunctionNameIf("STASBINARY") && this.parseEmptyParens()) {
                            return DSL.stAsBinary(f);
                        }
                        if (this.parseFunctionNameIf("STASTEXT") && this.parseEmptyParens()) {
                            return DSL.stAsText(f);
                        }
                        if (this.parseFunctionNameIf("STCENTROID") && this.parseEmptyParens()) {
                            return DSL.stCentroid(f);
                        }
                        if (this.parseFunctionNameIf("STCONTAINS")) {
                            return this.parseFunctionArgs1(a -> DSL.stContains(f, a));
                        }
                        if (this.parseFunctionNameIf("STCROSSES")) {
                            return this.parseFunctionArgs1(a -> DSL.stCrosses((Field<Geometry>)f, (Field<Geometry>)a));
                        }
                        if (this.parseFunctionNameIf("STDIFFERENCE")) {
                            return this.parseFunctionArgs1(a -> DSL.stDifference(f, a));
                        }
                        if (this.parseFunctionNameIf("STDISJOINT")) {
                            return this.parseFunctionArgs1(a -> DSL.stDisjoint(f, a));
                        }
                        if (this.parseFunctionNameIf("STDISTANCE")) {
                            return this.parseFunctionArgs1(a -> DSL.stDistance(f, a));
                        }
                        if (this.parseFunctionNameIf("STENDPOINT") && this.parseEmptyParens()) {
                            return DSL.stEndPoint(f);
                        }
                        if (this.parseFunctionNameIf("STEXTERIORRING") && this.parseEmptyParens()) {
                            return DSL.stExteriorRing(f);
                        }
                        if (this.parseFunctionNameIf("STEQUALS")) {
                            return this.parseFunctionArgs1(a -> DSL.stEquals(f, a));
                        }
                        if (this.parseFunctionNameIf("STGEOMETRYN")) {
                            return this.parseFunctionArgs1(a -> DSL.stGeometryN(f, (Field<? extends Number>)a));
                        }
                        if (this.parseFunctionNameIf("STGEOMETRYTYPE") && this.parseEmptyParens()) {
                            return DSL.stGeometryType(f);
                        }
                        if (this.parseFunctionNameIf("STISCLOSED") && this.parseEmptyParens()) {
                            return DSL.stIsClosed(f);
                        }
                        if (this.parseFunctionNameIf("STISEMPTY") && this.parseEmptyParens()) {
                            return DSL.stIsEmpty(f);
                        }
                        if (this.parseFunctionNameIf("STINTERIORRINGN")) {
                            return this.parseFunctionArgs1(a -> DSL.stInteriorRingN((Field<Geometry>)f, (Field<? extends Number>)a));
                        }
                        if (this.parseFunctionNameIf("STINTERSECTION")) {
                            return DSL.stIntersection(f, this.parseFieldParenthesised());
                        }
                        if (this.parseFunctionNameIf("STINTERSECTS")) {
                            return DSL.stIntersects(f, this.parseFieldParenthesised());
                        }
                        if (this.parseFunctionNameIf("STLENGTH") && this.parseEmptyParens()) {
                            return DSL.stLength(f);
                        }
                        if (this.parseFunctionNameIf("STNUMINTERIORRING") && this.parseEmptyParens()) {
                            return DSL.stNumInteriorRings(f);
                        }
                        if (this.parseFunctionNameIf("STNUMGEOMETRIES") && this.parseEmptyParens()) {
                            return DSL.stNumGeometries(f);
                        }
                        if (this.parseFunctionNameIf("STNUMPOINTS") && this.parseEmptyParens()) {
                            return DSL.stNumPoints(f);
                        }
                        if (this.parseFunctionNameIf("STOVERLAPS")) {
                            return DSL.stOverlaps(f, this.parseFieldParenthesised());
                        }
                        if (this.parseFunctionNameIf("STPOINTN")) {
                            return this.parseFunctionArgs1(a -> DSL.stPointN(f, (Field<? extends Number>)a));
                        }
                        if (this.parseKeywordIf("STSRID")) {
                            return DSL.stSrid(f);
                        }
                        if (this.parseFunctionNameIf("STSTARTPOINT") && this.parseEmptyParens()) {
                            return DSL.stStartPoint(f);
                        }
                        if (this.parseFunctionNameIf("STTOUCHES")) {
                            return DSL.stTouches(f, this.parseFieldParenthesised());
                        }
                        if (this.parseFunctionNameIf("STUNION")) {
                            return DSL.stUnion(f, this.parseFieldParenthesised());
                        }
                        if (this.parseFunctionNameIf("STWITHIN")) {
                            return this.parseFunctionArgs1(a -> DSL.stWithin(f, a));
                        }
                        if (this.parseKeywordOrIdentifierIf("STX")) {
                            return DSL.stX(f);
                        }
                        if (this.parseKeywordOrIdentifierIf("STY")) {
                            return DSL.stY(f);
                        }
                        if (this.parseKeywordOrIdentifierIf("STZ")) {
                            return DSL.stZ(f);
                        }
                    } else {
                        if (this.parseFunctionNameIf("EXIST")) {
                            return this.parseFunctionArgs1(a -> DSL.xmlexists(a).passing(f));
                        }
                        if (this.parseFunctionNameIf("QUERY")) {
                            return this.parseFunctionArgs1(a -> DSL.xmlquery(a).passing(f));
                        }
                    }
                    this.position(p);
                    break;
                }
            }
        }
        return r;
    }

    private final Sign parseSign() {
        Sign sign = Sign.NONE;
        while (true) {
            if (this.parseIf('+')) {
                sign = sign == Sign.NONE ? Sign.PLUS : sign;
                continue;
            }
            if (!this.parseIf('-')) break;
            sign = sign == Sign.NONE ? Sign.MINUS : sign.invert();
        }
        return sign;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private final FieldOrRow parseTerm() {
        FieldOrRow field2 = this.listener.parseField(this);
        if (field2 != null) {
            return field2;
        }
        switch (this.characterUpper()) {
            case ':': 
            case '?': 
            case '@': {
                field2 = this.parseBindVariableIf();
                if (field2 == null) break;
                return field2;
            }
            case '\"': {
                if (this.parseFamily() != SQLDialect.ACCESS) break;
                return DSL.inline(this.parseStringLiteral());
            }
            case '\'': {
                return DSL.inline(this.parseStringLiteral());
            }
            case '$': {
                field2 = this.parseBindVariableIf();
                if (field2 != null) {
                    return field2;
                }
                String value = this.parseDollarQuotedStringLiteralIf();
                if (value == null) break;
                return DSL.inline(value);
            }
            case 'A': {
                if (this.parseFunctionNameIf("ABS")) {
                    return DSL.abs(this.parseFieldNumericOpParenthesised());
                }
                if (this.parseFunctionNameIf("ASC", "ASCII", "ASCII_VAL")) {
                    return DSL.ascii(this.parseFieldParenthesised());
                }
                if (this.parseFunctionNameIf("ACOS")) {
                    return DSL.acos(this.parseFieldNumericOpParenthesised());
                }
                if (this.parseFunctionNameIf("ACOSH")) {
                    return DSL.acosh(this.parseFieldNumericOpParenthesised());
                }
                if (this.parseFunctionNameIf("ACOTH")) {
                    return DSL.acoth(this.parseFieldNumericOpParenthesised());
                }
                if (this.parseFunctionNameIf("ASIN")) {
                    return DSL.asin(this.parseFieldNumericOpParenthesised());
                }
                if (this.parseFunctionNameIf("ASINH")) {
                    return DSL.asinh(this.parseFieldNumericOpParenthesised());
                }
                if (this.parseFunctionNameIf("ATAN", "ATN")) {
                    return DSL.atan(this.parseFieldNumericOpParenthesised());
                }
                if (this.parseFunctionNameIf("ATANH")) {
                    return DSL.atanh(this.parseFieldNumericOpParenthesised());
                }
                if (this.parseFunctionNameIf("ATN2", "ATAN2")) {
                    return this.parseFunctionArgs2(() -> this.toField(this.parseNumericOp()), DSL::atan2);
                }
                if (this.parseFunctionNameIf("ASCII_CHAR")) {
                    return DSL.chr(this.parseFieldParenthesised());
                }
                field2 = this.parseArrayValueConstructorIf();
                if (field2 != null) {
                    return field2;
                }
                if (this.parseFunctionNameIf("ADD_YEARS")) {
                    return this.parseFieldAddDatePart(DatePart.YEAR);
                }
                if (this.parseFunctionNameIf("ADD_MONTHS")) {
                    return this.parseFieldAddDatePart(DatePart.MONTH);
                }
                if (this.parseFunctionNameIf("ADD_DAYS")) {
                    return this.parseFieldAddDatePart(DatePart.DAY);
                }
                if (this.parseFunctionNameIf("ADD_HOURS")) {
                    return this.parseFieldAddDatePart(DatePart.HOUR);
                }
                if (this.parseFunctionNameIf("ADD_MINUTES")) {
                    return this.parseFieldAddDatePart(DatePart.MINUTE);
                }
                if (this.parseFunctionNameIf("ADD_SECONDS")) {
                    return this.parseFieldAddDatePart(DatePart.SECOND);
                }
                if (this.parseFunctionNameIf("ARRAY_GET")) {
                    return this.parseFunctionArgs2((f1, f2) -> DSL.arrayGet((Field<T[]>)f1, f2));
                }
                if (this.parseFunctionNameIf("ARRAY_CAT", "ARRAY_CONCAT")) {
                    return this.parseFunctionArgs2((f1, f2) -> DSL.arrayConcat((Field<T[]>)f1, (Field<T[]>)f2));
                }
                if (this.parseFunctionNameIf("ARRAY_REMOVE")) {
                    return this.parseFunctionArgs2((f1, f2) -> DSL.arrayRemove((Field<T[]>)f1, f2));
                }
                if (this.parseFunctionNameIf("ARRAY_REPLACE")) {
                    return this.parseFunctionArgs3((f1, f2, f3) -> DSL.arrayReplace(f1, f2, f3));
                }
                if (this.parseFunctionNameIf("ARRAY_APPEND")) {
                    return this.parseFunctionArgs2((f1, f2) -> DSL.arrayAppend((Field<T[]>)f1, f2));
                }
                if (this.parseFunctionNameIf("ARRAY_PREPEND")) {
                    return this.parseFunctionArgs2((f1, f2) -> DSL.arrayPrepend(f1, (Field<T[]>)f2));
                }
                field2 = this.parseFieldArrayConstructIf();
                if (field2 == null) break;
                return field2;
            }
            case 'B': {
                if (this.parseFunctionNameIf("BIT_LENGTH")) {
                    return DSL.bitLength(this.parseFieldParenthesised());
                }
                if (this.parseFunctionNameIf("BITGET", "BIT_GET")) {
                    return this.parseFunctionArgs2(DSL::bitGet);
                }
                if (this.parseFunctionNameIf("BITSET", "BIT_SET")) {
                    return this.parseFunctionArgs3(DSL::bitSet, DSL::bitSet);
                }
                if (this.parseFunctionNameIf("BITCOUNT", "BIT_COUNT")) {
                    return DSL.bitCount(this.parseFieldNumericOpParenthesised());
                }
                if (this.parseKeywordIf("BIT_LSHIFT")) {
                    return this.parseFunctionArgs2(() -> this.toField(this.parseNumericOp()), (? super Field f1, ? super Field f2) -> DSL.shl(f1, (Field<? extends Number>)f2));
                }
                if (this.parseKeywordIf("BIT_RSHIFT")) {
                    return this.parseFunctionArgs2(() -> this.toField(this.parseNumericOp()), (? super Field f1, ? super Field f2) -> DSL.shr(f1, (Field<? extends Number>)f2));
                }
                if (this.parseFunctionNameIf("BYTE_LENGTH")) {
                    return DSL.octetLength(this.parseFieldParenthesised());
                }
                field2 = this.parseFieldBitwiseFunctionIf();
                if (field2 != null) {
                    return field2;
                }
                Boolean value = this.parseBitLiteralIf();
                if (value == null) break;
                return DSL.inline(value);
            }
            case 'C': {
                field2 = this.parseFieldConcatIf();
                if (field2 != null) {
                    return field2;
                }
                if (this.parseFunctionNameIf("CURRENT_CATALOG") && this.parseEmptyParens()) {
                    return DSL.currentCatalog();
                }
                if (this.parseFunctionNameIf("CURRENT_DATABASE") && this.parseEmptyParens()) {
                    return DSL.currentCatalog();
                }
                if (this.parseKeywordIf("CURRENT_SCHEMA", "CURRENT SCHEMA") && this.parseEmptyParensIf()) {
                    return DSL.currentSchema();
                }
                if (this.parseKeywordIf("CURRENT_USER", "CURRENT USER", "CURRENTUSER") && this.parseEmptyParensIf()) {
                    return DSL.currentUser();
                }
                if (this.parseFunctionNameIf("CHR", "CHAR")) {
                    return DSL.chr(this.parseFieldParenthesised());
                }
                if (this.parseFunctionNameIf("CHARINDEX")) {
                    return this.parseFunctionArgs3((f1, f2) -> DSL.position((Field<String>)f2, (Field<String>)f1), (f1, f2, f3) -> DSL.position((Field<String>)f2, (Field<String>)f1, (Field<? extends Number>)f3));
                }
                if (this.parseFunctionNameIf("CHAR_LENGTH")) {
                    return DSL.charLength(this.parseFieldParenthesised());
                }
                if (this.parseFunctionNameIf("CARDINALITY")) {
                    return DSL.cardinality(this.parseFieldParenthesised());
                }
                if (this.parseFunctionNameIf("CEILING", "CEIL")) {
                    return DSL.ceil(this.parseFieldNumericOpParenthesised());
                }
                if (this.parseFunctionNameIf("COSH")) {
                    return DSL.cosh(this.parseFieldNumericOpParenthesised());
                }
                if (this.parseFunctionNameIf("COS")) {
                    return DSL.cos(this.parseFieldNumericOpParenthesised());
                }
                if (this.parseFunctionNameIf("COTH")) {
                    return DSL.coth(this.parseFieldNumericOpParenthesised());
                }
                if (this.parseFunctionNameIf("COT")) {
                    return DSL.cot(this.parseFieldNumericOpParenthesised());
                }
                field2 = this.parseNextvalCurrvalIf(SequenceMethod.CURRVAL);
                if (field2 != null) {
                    return field2;
                }
                if (this.parseFunctionNameIf("CENTURY")) {
                    return DSL.century(this.parseFieldParenthesised());
                }
                if (this.parseKeywordIf("CURRENT_DATE", "CURRENT DATE") && this.parseEmptyParensIf()) {
                    return DSL.currentDate();
                }
                if (this.parseKeywordIf("CURRENT_TIMESTAMP", "CURRENT TIMESTAMP")) {
                    Field<?> precision = null;
                    if (this.parseIf('(') && !this.parseIf(')')) {
                        precision = this.parseField();
                        this.parse(')');
                    }
                    return precision != null ? DSL.currentTimestamp(precision) : DSL.currentTimestamp();
                }
                if (this.parseKeywordIf("CURRENT_TIME", "CURRENT TIME") && this.parseEmptyParensIf()) {
                    return DSL.currentTime();
                }
                if (this.parseFunctionNameIf("CURDATE") && this.parseEmptyParens()) {
                    return DSL.currentDate();
                }
                if (this.parseFunctionNameIf("CURTIME") && this.parseEmptyParens()) {
                    return DSL.currentTime();
                }
                field2 = this.parseFieldCaseIf();
                if (field2 != null) {
                    return field2;
                }
                field2 = this.parseFieldCastIf();
                if (field2 != null) {
                    return field2;
                }
                field2 = this.parseFieldCoalesceIf();
                if (field2 != null) {
                    return field2;
                }
                field2 = this.parseFieldCumeDistIf();
                if (field2 != null) {
                    return field2;
                }
                field2 = this.parseFieldConvertIf();
                if (field2 != null) {
                    return field2;
                }
                field2 = this.parseFieldChooseIf();
                if (field2 != null) {
                    return field2;
                }
                if (!this.ignoreProEdition() && this.parseKeywordIf("CONNECT_BY_ISCYCLE") && this.requireProEdition()) {
                    return DSL.connectByIsCycle();
                }
                if (this.ignoreProEdition() || !this.parseKeywordIf("CONNECT_BY_ISLEAF") || !this.requireProEdition()) break;
                return DSL.connectByIsLeaf();
            }
            case 'D': {
                if (this.parseFunctionNameIf("DATABASE") && this.parseEmptyParens()) {
                    return DSL.currentCatalog();
                }
                if (this.parseFunctionNameIf("DB_NAME") && this.parseEmptyParens()) {
                    return DSL.currentCatalog();
                }
                if (this.parseFunctionNameIf("DBINFO") && this.parse('(') && this.parseStringLiteral("dbname") != null && this.parse(')')) {
                    return DSL.currentCatalog();
                }
                if (this.parseFunctionNameIf("DIGITS")) {
                    return DSL.digits(this.parseFieldParenthesised());
                }
                field2 = this.parseFieldDateLiteralIf();
                if (field2 != null) {
                    return field2;
                }
                field2 = this.parseFieldDateTruncIf();
                if (field2 != null) {
                    return field2;
                }
                field2 = this.parseFieldDateAddIf();
                if (field2 != null) {
                    return field2;
                }
                field2 = this.parseFieldDateDiffIf();
                if (field2 != null) {
                    return field2;
                }
                field2 = this.parseFieldDatePartIf();
                if (field2 != null) {
                    return field2;
                }
                field2 = this.parseFieldDenseRankIf();
                if (field2 != null) {
                    return field2;
                }
                if (this.parseFunctionNameIf("DECADE")) {
                    return DSL.decade(this.parseFieldParenthesised());
                }
                if (this.parseFunctionNameIf("DAY") || this.parseFunctionNameIf("DAYOFMONTH")) {
                    return DSL.day(this.parseFieldParenthesised());
                }
                if (this.parseFunctionNameIf("DAYOFWEEK_ISO")) {
                    return DSL.isoDayOfWeek(this.parseFieldParenthesised());
                }
                if (this.parseFunctionNameIf("DAYOFWEEK") || this.parseFunctionNameIf("DAY_OF_WEEK")) {
                    return DSL.dayOfWeek(this.parseFieldParenthesised());
                }
                if (this.parseFunctionNameIf("DAYOFYEAR") || this.parseFunctionNameIf("DAY_OF_YEAR")) {
                    return DSL.dayOfYear(this.parseFieldParenthesised());
                }
                if (this.parseFunctionNameIf("DEGREES") || this.parseFunctionNameIf("DEGREE") || this.parseFunctionNameIf("DEG")) {
                    return DSL.deg(this.parseFieldNumericOpParenthesised());
                }
                if (this.parseFunctionNameIf("DATALENGTH")) {
                    return DSL.octetLength(this.parseFieldParenthesised());
                }
                field2 = this.parseFieldDecodeIf();
                if (field2 != null) {
                    return field2;
                }
                if (!this.parseKeywordIf("DEFAULT")) break;
                return DSL.default_();
            }
            case 'E': {
                if (this.characterNext() == '\'') {
                    return DSL.inline(this.parseStringLiteral());
                }
                field2 = this.parseFieldExtractIf();
                if (field2 != null) {
                    return field2;
                }
                if (this.parseFunctionNameIf("EXP")) {
                    return DSL.exp(this.parseFieldNumericOpParenthesised());
                }
                if (this.parseFunctionNameIf("EPOCH")) {
                    return DSL.epoch(this.parseFieldParenthesised());
                }
                field2 = this.parseFieldChooseIf();
                if (field2 == null) break;
                return field2;
            }
            case 'F': {
                if (this.parseFunctionNameIf("FLOOR")) {
                    return DSL.floor(this.parseFieldNumericOpParenthesised());
                }
                field2 = this.parseFieldFirstValueIf();
                if (field2 != null) {
                    return field2;
                }
                field2 = this.parseFieldFieldIf();
                if (field2 == null) break;
                return field2;
            }
            case 'G': {
                if (this.parseKeywordIf("GETDATE") && this.parseEmptyParens()) {
                    return DSL.currentTimestamp();
                }
                if (this.parseFunctionNameIf("GENGUID", "GENERATE_UUID", "GEN_RANDOM_UUID") && this.parseEmptyParens()) {
                    return DSL.uuid();
                }
                if (this.parseFunctionNameIf("GET_BIT", "GETBIT")) {
                    return this.parseFunctionArgs2(DSL::bitGet);
                }
                field2 = this.parseFieldGreatestIf();
                if (field2 != null) {
                    return field2;
                }
                if (!this.ignoreProEdition() && this.parseFunctionNameIf("GROUP_ID") && this.requireProEdition() && this.parseEmptyParens()) {
                    return DSL.groupId();
                }
                field2 = this.parseFieldGroupingIdIf();
                if (field2 != null) {
                    return field2;
                }
                if (this.parseFunctionNameIf("GROUPING")) {
                    return DSL.grouping(this.parseFieldParenthesised());
                }
                if (!this.ignoreProEdition() && (this.parseFunctionNameIf("GEOMETRY::STGEOMFROMWKB") || this.parseFunctionNameIf("GEOGRAPHY::STGEOMFROMWKB")) && this.requireProEdition()) {
                    return this.parseFunctionArgs2(DSL::stGeomFromWKB, DSL::stGeomFromWKB);
                }
                if (this.ignoreProEdition() || !this.parseFunctionNameIf("GEOMETRY::STGEOMFROMTEXT") && !this.parseFunctionNameIf("GEOGRAPHY::STGEOMFROMTEXT") || !this.requireProEdition()) break;
                return this.parseFunctionArgs2(DSL::stGeomFromText, DSL::stGeomFromText);
            }
            case 'H': {
                if (this.parseFunctionNameIf("HOUR")) {
                    return DSL.hour(this.parseFieldParenthesised());
                }
                if (this.parseFunctionNameIf("HASH_MD5")) {
                    return DSL.md5(this.parseFieldParenthesised());
                }
                if (!this.parseFunctionNameIf("HEX")) break;
                return DSL.toHex(this.parseFieldParenthesised());
            }
            case 'I': {
                field2 = this.parseFieldIntervalLiteralIf();
                if (field2 != null) {
                    return field2;
                }
                if (this.parseFunctionNameIf("ISO_DAY_OF_WEEK")) {
                    return DSL.isoDayOfWeek(this.parseFieldParenthesised());
                }
                if (this.parseFunctionNameIf("INSTR")) {
                    return this.parseFunctionArgs3(DSL::position, DSL::position);
                }
                if (this.parseFunctionNameIf("INSERT")) {
                    return this.parseFunctionArgs4(DSL::insert);
                }
                if (this.parseFunctionNameIf("IFNULL")) {
                    return this.parseFunctionArgs2((f1, f2) -> DSL.ifnull(f1, f2));
                }
                if (this.parseFunctionNameIf("ISJSON")) {
                    return this.parseFunctionArgs1(f -> DSL.case_(f.isJson()).when((Field<Boolean>)DSL.trueCondition(), DSL.one()).when((Field<Boolean>)DSL.falseCondition(), DSL.zero()));
                }
                if (this.parseFunctionNameIf("ISNULL")) {
                    return this.parseFunctionArgs2((? super Field f) -> f.isNull(), (? super Field f1, ? super Field f2) -> DSL.isnull(f1, f2));
                }
                field2 = this.parseFieldIfIf();
                if (field2 == null) break;
                return field2;
            }
            case 'J': {
                field2 = this.parseFieldJSONArrayConstructorIf();
                if (field2 != null) {
                    return field2;
                }
                field2 = this.parseFieldJSONObjectConstructorIf();
                if (field2 != null) {
                    return field2;
                }
                field2 = this.parseFieldJSONValueIf();
                if (field2 != null) {
                    return field2;
                }
                field2 = this.parseFieldJSONLiteralIf();
                if (field2 != null) {
                    return field2;
                }
                if (this.parseFunctionNameIf("JSON_KEYS")) {
                    return this.parseFunctionArgs1(DSL::jsonKeys);
                }
                if (this.parseFunctionNameIf("JSON_INSERT")) {
                    return this.parseFunctionArgs3(DSL::jsonInsert);
                }
                if (this.parseFunctionNameIf("JSON_REMOVE")) {
                    return this.parseFunctionArgs2(DSL::jsonRemove);
                }
                if (this.parseFunctionNameIf("JSON_REPLACE")) {
                    return this.parseFunctionArgs3(DSL::jsonReplace);
                }
                if (this.parseFunctionNameIf("JSON_SET")) {
                    return this.parseFunctionArgs3(DSL::jsonSet);
                }
                if (this.parseFunctionNameIf("JSON_VALID")) {
                    return this.parseFunctionArgs1(f -> DSL.case_(f.isJson()).when((Field<Boolean>)DSL.trueCondition(), DSL.one()).when((Field<Boolean>)DSL.falseCondition(), DSL.zero()));
                }
                if (this.parseFunctionNameIf("JSONB_KEYS")) {
                    return this.parseFunctionArgs1(DSL::jsonbKeys);
                }
                if (this.parseFunctionNameIf("JSONB_INSERT")) {
                    return this.parseFunctionArgs3(DSL::jsonbInsert);
                }
                if (this.parseFunctionNameIf("JSONB_REMOVE")) {
                    return this.parseFunctionArgs2(DSL::jsonbRemove);
                }
                if (this.parseFunctionNameIf("JSONB_REPLACE")) {
                    return this.parseFunctionArgs3(DSL::jsonbReplace);
                }
                if (!this.parseFunctionNameIf("JSONB_SET")) break;
                return this.parseFunctionArgs3(DSL::jsonbSet);
            }
            case 'L': {
                if (this.parseFunctionNameIf("LOWER", "LCASE")) {
                    return DSL.lower(this.parseFieldParenthesised());
                }
                if (this.parseFunctionNameIf("LPAD")) {
                    return this.parseFunctionArgs3(DSL::lpad, DSL::lpad);
                }
                if (this.parseFunctionNameIf("LTRIM")) {
                    return this.parseFunctionArgs2(DSL::ltrim, DSL::ltrim);
                }
                if (this.parseFunctionNameIf("LEFT")) {
                    return this.parseFunctionArgs2(DSL::left);
                }
                if (this.parseFunctionNameIf("LENGTH", "LEN")) {
                    return DSL.length(this.parseFieldParenthesised());
                }
                if (this.parseFunctionNameIf("LENGTHB")) {
                    return DSL.octetLength(this.parseFieldParenthesised());
                }
                if (this.parseFunctionNameIf("LN", "LOGN")) {
                    return DSL.ln(this.parseFieldNumericOpParenthesised());
                }
                if (this.parseFunctionNameIf("LOG10")) {
                    return DSL.log10(this.parseFieldNumericOpParenthesised());
                }
                field2 = this.parseFieldLogIf();
                if (field2 != null) {
                    return field2;
                }
                field2 = this.parseFieldLocateIf();
                if (field2 != null) {
                    return field2;
                }
                if (!this.ignoreProEdition() && this.parseKeywordIf("LEVEL") && this.requireProEdition()) {
                    return DSL.level();
                }
                if (this.parseKeywordIf("LSHIFT", "LEFT_SHIFT")) {
                    return this.parseFunctionArgs2(() -> this.toField(this.parseNumericOp()), (? super Field f1, ? super Field f2) -> DSL.shl(f1, (Field<? extends Number>)f2));
                }
                field2 = this.parseFieldLeastIf();
                if (field2 != null) {
                    return field2;
                }
                field2 = this.parseFieldLeadLagIf();
                if (field2 != null) {
                    return field2;
                }
                field2 = this.parseFieldLastValueIf();
                if (field2 == null) break;
                return field2;
            }
            case 'M': {
                if (this.parseFunctionNameIf("MOD")) {
                    return this.parseFunctionArgs2(Field::mod);
                }
                if (this.parseFunctionNameIf("MICROSECOND")) {
                    return DSL.microsecond(this.parseFieldParenthesised());
                }
                if (this.parseFunctionNameIf("MILLENNIUM")) {
                    return DSL.millennium(this.parseFieldParenthesised());
                }
                if (this.parseFunctionNameIf("MILLISECOND")) {
                    return DSL.millisecond(this.parseFieldParenthesised());
                }
                if (this.parseFunctionNameIf("MINUTE")) {
                    return DSL.minute(this.parseFieldParenthesised());
                }
                if (this.parseFunctionNameIf("MONTH")) {
                    return DSL.month(this.parseFieldParenthesised());
                }
                if (this.parseFunctionNameIf("MID")) {
                    return this.parseFunctionArgs3(DSL::mid);
                }
                if (this.parseFunctionNameIf("MD5")) {
                    return DSL.md5(this.parseFieldParenthesised());
                }
                field2 = this.parseMultisetValueConstructorIf();
                if (field2 != null) {
                    return field2;
                }
                field2 = this.parseFieldGreatestIf();
                if (field2 != null) {
                    return field2;
                }
                field2 = this.parseFieldLeastIf();
                if (field2 != null) {
                    return field2;
                }
                field2 = this.parseFieldDecodeIf();
                if (field2 == null) break;
                return field2;
            }
            case 'N': {
                if (this.characterNext() == '\'') {
                    return DSL.inline((Object)this.parseStringLiteral(), SQLDataType.NVARCHAR);
                }
                field2 = this.parseFieldNewIdIf();
                if (field2 != null) {
                    return field2;
                }
                if (this.parseFunctionNameIf("NVL2")) {
                    return this.parseFunctionArgs3((f1, f2, f3) -> DSL.nvl2(f1, f2, f3));
                }
                if (this.parseFunctionNameIf("NVL")) {
                    return this.parseFunctionArgs2((f1, f2) -> DSL.nvl(f1, f2));
                }
                if (this.parseFunctionNameIf("NULLIF")) {
                    return this.parseFunctionArgs2((f1, f2) -> DSL.nullif(f1, f2));
                }
                field2 = this.parseFieldNtileIf();
                if (field2 != null) {
                    return field2;
                }
                field2 = this.parseFieldNthValueIf();
                if (field2 != null) {
                    return field2;
                }
                field2 = this.parseNextValueIf();
                if (field2 != null) {
                    return field2;
                }
                field2 = this.parseNextvalCurrvalIf(SequenceMethod.NEXTVAL);
                if (field2 != null) {
                    return field2;
                }
                if (!this.parseFunctionNameIf("NOW") || !this.parse('(')) break;
                if (this.parseIf(')')) {
                    return DSL.now();
                }
                Field<Integer> precision = this.parseField();
                this.parse(')');
                return DSL.now(precision);
            }
            case 'O': {
                if (this.parseFunctionNameIf("OREPLACE")) {
                    return this.parseFunctionArgs3(DSL::replace, DSL::replace);
                }
                field2 = this.parseFieldOverlayIf();
                if (field2 != null) {
                    return field2;
                }
                field2 = this.parseFieldTranslateIf();
                if (field2 != null) {
                    return field2;
                }
                if (this.parseFunctionNameIf("OCTET_LENGTH")) {
                    return DSL.octetLength(this.parseFieldParenthesised());
                }
                field2 = this.parseFieldObjectConstructIf();
                if (field2 != null) {
                    return field2;
                }
                if (!this.parseFunctionNameIf("OBJECT_KEYS")) break;
                return this.parseFunctionArgs1(DSL::jsonKeys);
            }
            case 'P': {
                field2 = this.parseFieldPositionIf();
                if (field2 != null) {
                    return field2;
                }
                field2 = this.parseFieldPercentRankIf();
                if (field2 != null) {
                    return field2;
                }
                if (this.parseFunctionNameIf("POWER", "POW")) {
                    return this.parseFunctionArgs2(() -> this.toField(this.parseNumericOp()), DSL::power);
                }
                if (this.parseFunctionNameIf("PI") && this.parseEmptyParens()) {
                    return DSL.pi();
                }
                if (this.ignoreProEdition() || !this.parseKeywordIf("PRIOR") || !this.requireProEdition()) break;
                return DSL.prior(this.toField(this.parseConcat()));
            }
            case 'Q': {
                if (this.characterNext() == '\'') {
                    return DSL.inline(this.parseStringLiteral());
                }
                if (this.parseFunctionNameIf("QUARTER")) {
                    return DSL.quarter(this.parseFieldParenthesised());
                }
            }
            case 'R': {
                if (this.parseFunctionNameIf("REPLACE")) {
                    return this.parseFunctionArgs3(DSL::replace, DSL::replace);
                }
                field2 = this.parseFieldRegexpReplaceIf();
                if (field2 != null) {
                    return field2;
                }
                if (this.parseFunctionNameIf("REPEAT", "REPLICATE")) {
                    return this.parseFunctionArgs2(DSL::repeat);
                }
                if (this.parseFunctionNameIf("REVERSE")) {
                    return DSL.reverse(this.parseFieldParenthesised());
                }
                if (this.parseFunctionNameIf("RPAD")) {
                    return this.parseFunctionArgs3(DSL::rpad, DSL::rpad);
                }
                if (this.parseFunctionNameIf("RTRIM")) {
                    return this.parseFunctionArgs2(DSL::rtrim, DSL::rtrim);
                }
                if (this.parseFunctionNameIf("RIGHT")) {
                    return this.parseFunctionArgs2(DSL::right);
                }
                if (this.parseFunctionNameIf("RANDOM_UUID") && this.parseEmptyParens()) {
                    return DSL.uuid();
                }
                if (this.parseFunctionNameIf("ROW_NUMBER", "ROWNUMBER") && this.parseEmptyParens()) {
                    return this.parseWindowFunction(null, null, DSL.rowNumber());
                }
                field2 = this.parseFieldRankIf();
                if (field2 != null) {
                    return field2;
                }
                field2 = this.parseFieldRoundIf();
                if (field2 != null) {
                    return field2;
                }
                if (!this.ignoreProEdition() && this.parseKeywordIf("ROWNUM") && this.requireProEdition()) {
                    return DSL.rownum();
                }
                if (this.parseFunctionNameIf("RADIANS") || this.parseFunctionNameIf("RADIAN") || this.parseFunctionNameIf("RAD")) {
                    return DSL.rad(this.parseFieldNumericOpParenthesised());
                }
                if (this.parseFunctionNameIf("RAND", "RANDOM") && this.parseEmptyParens()) {
                    return DSL.rand();
                }
                if (this.parseFunctionNameIf("RATIO_TO_REPORT")) {
                    return this.parseFunctionArgs1(f -> this.parseWindowFunction(null, null, DSL.ratioToReport(f)));
                }
                if (this.parseKeywordIf("RSHIFT", "RIGHT_SHIFT")) {
                    return this.parseFunctionArgs2(() -> this.toField(this.parseNumericOp()), (? super Field f1, ? super Field f2) -> DSL.shr(f1, (Field<? extends Number>)f2));
                }
                if (!this.parseFunctionNameIf("ROW")) break;
                return this.parseTuple();
            }
            case 'S': {
                field2 = this.parseFieldSubstringIf();
                if (field2 != null) {
                    return field2;
                }
                if (this.parseFunctionNameIf("SUBSTRING_INDEX")) {
                    return this.parseFunctionArgs3(DSL::substringIndex);
                }
                if (this.parseFunctionNameIf("SPACE")) {
                    return DSL.space(this.parseFieldParenthesised());
                }
                if (this.parseFunctionNameIf("SPLIT_PART")) {
                    return this.parseFunctionArgs3(DSL::splitPart);
                }
                if (this.parseFunctionNameIf("STR_REPLACE")) {
                    return this.parseFunctionArgs3(DSL::replace, DSL::replace);
                }
                if (this.parseFunctionNameIf("SCHEMA") && this.parseEmptyParensIf()) {
                    return DSL.currentSchema();
                }
                if (this.parseFunctionNameIf("STRREVERSE")) {
                    return DSL.reverse(this.parseFieldParenthesised());
                }
                if (this.parseFunctionNameIf("STRTOK")) {
                    return this.parseFunctionArgs3(DSL::splitPart);
                }
                if (this.parseFunctionNameIf("SYSUUID") && this.parseEmptyParensIf()) {
                    return DSL.uuid();
                }
                if (this.parseFunctionNameIf("SET_BIT", "SETBIT")) {
                    return this.parseFunctionArgs3(DSL::bitSet, DSL::bitSet);
                }
                if (this.parseFunctionNameIf("SECOND")) {
                    return DSL.second(this.parseFieldParenthesised());
                }
                if (!this.ignoreProEdition() && this.parseFunctionNameIf("SEQ4", "SEQ8") && this.parseEmptyParens() && this.requireProEdition()) {
                    return DSL.rownum();
                }
                if (this.parseFunctionNameIf("SIGN", "SGN")) {
                    return DSL.sign(this.parseFieldParenthesised());
                }
                if (this.parseFunctionNameIf("SQRT", "SQR")) {
                    return DSL.sqrt(this.parseFieldNumericOpParenthesised());
                }
                if (this.parseFunctionNameIf("SQUARE")) {
                    return DSL.square(this.parseFieldNumericOpParenthesised());
                }
                if (this.parseFunctionNameIf("SINH")) {
                    return DSL.sinh(this.parseFieldNumericOpParenthesised());
                }
                if (this.parseFunctionNameIf("SIN")) {
                    return DSL.sin(this.parseFieldNumericOpParenthesised());
                }
                if (this.parseKeywordIf("SHL", "SHIFTLEFT")) {
                    return this.parseFunctionArgs2(() -> this.toField(this.parseNumericOp()), (? super Field f1, ? super Field f2) -> DSL.shl(f1, (Field<? extends Number>)f2));
                }
                if (this.parseKeywordIf("SHR", "SHIFTRIGHT")) {
                    return this.parseFunctionArgs2(() -> this.toField(this.parseNumericOp()), (? super Field f1, ? super Field f2) -> DSL.shr(f1, (Field<? extends Number>)f2));
                }
                field2 = this.parseFieldSysConnectByPathIf();
                if (field2 != null) {
                    return field2;
                }
                if (!this.ignoreProEdition() && this.parseFunctionNameIf("ST_AREA") && this.requireProEdition()) {
                    return DSL.stArea(this.parseFieldParenthesised());
                }
                if (!this.ignoreProEdition() && this.parseFunctionNameIf("SDO_GEOM.SDO_AREA") && this.requireProEdition()) {
                    return this.parseFunctionArgs3((f1, f2) -> DSL.stArea(f1), (f1, f2, f3) -> DSL.stArea(f1));
                }
                if (!this.ignoreProEdition() && this.parseFunctionNameIf("ST_ASBINARY") && this.requireProEdition()) {
                    return DSL.stAsBinary(this.parseFieldParenthesised());
                }
                if (!this.ignoreProEdition() && this.parseFunctionNameIf("ST_ASTEXT") && this.requireProEdition()) {
                    return DSL.stAsText(this.parseFieldParenthesised());
                }
                if (!this.ignoreProEdition() && this.parseFunctionNameIf("ST_CENTROID") && this.requireProEdition()) {
                    return DSL.stCentroid(this.parseFieldParenthesised());
                }
                if (!this.ignoreProEdition() && this.parseFunctionNameIf("SDO_GEOM.SDO_CENTROID") && this.requireProEdition()) {
                    return this.parseFunctionArgs2((f1, f2) -> DSL.stCentroid(f1));
                }
                if (!this.ignoreProEdition() && this.parseFunctionNameIf("ST_DIFFERENCE") && this.requireProEdition()) {
                    return this.parseFunctionArgs2((f1, f2) -> DSL.stDifference(f1, f2));
                }
                if (!this.ignoreProEdition() && this.parseFunctionNameIf("SDO_GEOM.SDO_DIFFERENCE") && this.requireProEdition()) {
                    return this.parseFunctionArgs3((f1, f2, f3) -> DSL.stDifference(f1, f2));
                }
                if (!this.ignoreProEdition() && this.parseFunctionNameIf("ST_DISTANCE") && this.requireProEdition()) {
                    return this.parseFunctionArgs2(DSL::stDistance);
                }
                if (!this.ignoreProEdition() && this.parseFunctionNameIf("SDO_GEOM.SDO_DISTANCE") && this.requireProEdition()) {
                    return this.parseFunctionArgs3((f1, f2, f3) -> DSL.stDistance(f1, f2));
                }
                if (!this.ignoreProEdition() && this.parseFunctionNameIf("ST_ENDPOINT") && this.requireProEdition()) {
                    return DSL.stEndPoint(this.parseFieldParenthesised());
                }
                if (!this.ignoreProEdition() && this.parseFunctionNameIf("ST_EXTERIORRING") && this.requireProEdition()) {
                    return DSL.stExteriorRing(this.parseFieldParenthesised());
                }
                if (!this.ignoreProEdition() && this.parseFunctionNameIf("ST_GEOMETRYN") && this.requireProEdition()) {
                    return this.parseFunctionArgs2((f1, f2) -> DSL.stGeometryN(f1, (Field<? extends Number>)f2));
                }
                if (!this.ignoreProEdition() && this.parseFunctionNameIf("ST_GEOMETRYTYPE") && this.requireProEdition()) {
                    return DSL.stGeometryType(this.parseFieldParenthesised());
                }
                if (!this.ignoreProEdition() && this.parseFunctionNameIf("ST_GEOMFROMWKB") && this.requireProEdition()) {
                    return this.parseFunctionArgs2(DSL::stGeomFromWKB, DSL::stGeomFromWKB);
                }
                if (!this.ignoreProEdition() && this.parseFunctionNameIf("ST_GEOMFROMTEXT", "SDO_GEOMETRY") && this.requireProEdition()) {
                    return this.parseFunctionArgs2((? super Field t1) -> t1.getDataType().isBinary() ? DSL.stGeomFromWKB(t1) : DSL.stGeomFromText(t1), (? super Field t1, ? super Field t2) -> t1.getDataType().isBinary() ? DSL.stGeomFromWKB((Field<byte[]>)t1, (Field<? extends Number>)t2) : DSL.stGeomFromText((Field<String>)t1, (Field<? extends Number>)t2));
                }
                if (!this.ignoreProEdition() && this.parseFunctionNameIf("ST_INTERIORRINGN") && this.requireProEdition()) {
                    return this.parseFunctionArgs2(DSL::stInteriorRingN);
                }
                if (!this.ignoreProEdition() && this.parseFunctionNameIf("ST_INTERSECTION") && this.requireProEdition()) {
                    return this.parseFunctionArgs2((f1, f2) -> DSL.stIntersection(f1, f2));
                }
                if (!this.ignoreProEdition() && this.parseFunctionNameIf("SDO_GEOM.SDO_INTERSECTION") && this.requireProEdition()) {
                    return this.parseFunctionArgs3((f1, f2, f3) -> DSL.stIntersection(f1, f2));
                }
                if (!this.ignoreProEdition() && this.parseFunctionNameIf("ST_LENGTH") && this.requireProEdition()) {
                    return DSL.stLength(this.parseFieldParenthesised());
                }
                if (!this.ignoreProEdition() && this.parseFunctionNameIf("SDO_GEOM.SDO_LENGTH") && this.requireProEdition()) {
                    return this.parseFunctionArgs2((f1, f2) -> DSL.stLength(f1));
                }
                if (!this.ignoreProEdition() && this.parseFunctionNameIf("ST_NUMINTERIORRING", "ST_NUMINTERIORRINGS") && this.requireProEdition()) {
                    return DSL.stNumInteriorRings(this.parseFieldParenthesised());
                }
                if (!this.ignoreProEdition() && this.parseFunctionNameIf("ST_NUMGEOMETRIES", "SDO_UTIL.GETNUMELEM") && this.requireProEdition()) {
                    return DSL.stNumGeometries(this.parseFieldParenthesised());
                }
                if (!this.ignoreProEdition() && this.parseFunctionNameIf("ST_NUMPOINTS") && this.requireProEdition()) {
                    return DSL.stNumPoints(this.parseFieldParenthesised());
                }
                if (!this.ignoreProEdition() && this.parseFunctionNameIf("ST_POINTN") && this.requireProEdition()) {
                    return this.parseFunctionArgs2((f1, f2) -> DSL.stPointN(f1, (Field<? extends Number>)f2));
                }
                if (!this.ignoreProEdition() && this.parseFunctionNameIf("ST_SRID") && this.requireProEdition()) {
                    return DSL.stSrid(this.parseFieldParenthesised());
                }
                if (!this.ignoreProEdition() && this.parseFunctionNameIf("ST_STARTPOINT") && this.requireProEdition()) {
                    return DSL.stStartPoint(this.parseFieldParenthesised());
                }
                if (!this.ignoreProEdition() && this.parseFunctionNameIf("ST_UNION") && this.requireProEdition()) {
                    return this.parseFunctionArgs2((f1, f2) -> DSL.stUnion(f1, f2));
                }
                if (!this.ignoreProEdition() && this.parseFunctionNameIf("SDO_GEOM.SDO_UNION") && this.requireProEdition()) {
                    return this.parseFunctionArgs3((f1, f2, f3) -> DSL.stUnion(f1, f2));
                }
                if (!this.ignoreProEdition() && this.parseFunctionNameIf("ST_X") && this.requireProEdition()) {
                    return DSL.stX(this.parseFieldParenthesised());
                }
                if (!this.ignoreProEdition() && this.parseFunctionNameIf("ST_Y") && this.requireProEdition()) {
                    return DSL.stY(this.parseFieldParenthesised());
                }
                if (this.ignoreProEdition() || !this.parseFunctionNameIf("ST_Z") || !this.requireProEdition()) break;
                return DSL.stZ(this.parseFieldParenthesised());
            }
            case 'T': {
                field2 = this.parseBooleanValueExpressionIf();
                if (field2 != null) {
                    return field2;
                }
                field2 = this.parseFieldTrimIf();
                if (field2 != null) {
                    return field2;
                }
                field2 = this.parseFieldTranslateIf();
                if (field2 != null) {
                    return field2;
                }
                if (this.parseFunctionNameIf("TO_CHAR")) {
                    return this.parseFunctionArgs2(DSL::toChar, DSL::toChar);
                }
                if (this.parseFunctionNameIf("TO_HEX")) {
                    return DSL.toHex(this.parseFieldParenthesised());
                }
                if (this.parseFunctionNameIf("TANH")) {
                    return DSL.tanh(this.parseFieldNumericOpParenthesised());
                }
                if (this.parseFunctionNameIf("TAN")) {
                    return DSL.tan(this.parseFieldNumericOpParenthesised());
                }
                if (this.parseFunctionNameIf("TO_NUMBER")) {
                    return this.parseFunctionArgs1(f -> DSL.cast(f, SQLDataType.NUMERIC));
                }
                if (this.parseFunctionNameIf("TIMEZONE_HOUR")) {
                    return DSL.timezoneHour(this.parseFieldParenthesised());
                }
                if (this.parseFunctionNameIf("TIMEZONE_MINUTE")) {
                    return DSL.timezoneMinute(this.parseFieldParenthesised());
                }
                if (this.parseFunctionNameIf("TIMEZONE")) {
                    return DSL.timezone(this.parseFieldParenthesised());
                }
                field2 = this.parseFieldTimestampLiteralIf();
                if (field2 != null) {
                    return field2;
                }
                field2 = this.parseFieldTimeLiteralIf();
                if (field2 != null) {
                    return field2;
                }
                if (this.parseFunctionNameIf("TO_DATE")) {
                    return this.parseFunctionArgs2((? super Field f1) -> DSL.toDate((Field<String>)f1, DSL.inline(this.settings().getParseDateFormat())), DSL::toDate);
                }
                if (this.parseFunctionNameIf("TO_TIMESTAMP")) {
                    return this.parseFunctionArgs2((? super Field f1) -> DSL.toTimestamp((Field<String>)f1, DSL.inline(this.settings().getParseTimestampFormat())), DSL::toTimestamp);
                }
                if (this.parseFunctionNameIf("TIMESTAMPDIFF")) {
                    return this.parseFunctionArgs2((f1, f2) -> DSL.timestampDiff((Field<Timestamp>)f1, (Field<Timestamp>)f2));
                }
                field2 = this.parseFieldTruncIf();
                if (field2 == null) break;
                return field2;
            }
            case 'U': {
                if (this.parseFunctionNameIf("UPPER", "UCASE")) {
                    return DSL.upper(this.parseFieldParenthesised());
                }
                if (this.parseFunctionNameIf("UUID", "UUID_GENERATE", "UUID_STRING") && this.parseEmptyParens()) {
                    return DSL.uuid();
                }
                if (!this.parseFunctionNameIf("UNIX_TIMESTAMP")) break;
                return DSL.epoch(this.parseFieldParenthesised());
            }
            case 'V': {
                if (Boolean.TRUE.equals(this.data(Tools.BooleanDataKey.DATA_PARSE_ON_CONFLICT)) && (this.parseFunctionNameIf("VALUES") || this.parseFunctionNameIf("VALUE"))) {
                    return DSL.excluded(this.parseFieldParenthesised());
                }
            }
            case 'W': {
                if (this.parseFunctionNameIf("WIDTH_BUCKET")) {
                    return this.parseFunctionArgs4((f1, f2, f3, f4) -> DSL.widthBucket(f1, f2, f3, f4));
                }
                if (!this.parseFunctionNameIf("WEEK")) break;
                return DSL.week(this.parseFieldParenthesised());
            }
            case 'X': {
                byte[] value = this.parseBinaryLiteralIf();
                if (value != null) {
                    return DSL.inline(value);
                }
                if (this.parseFunctionNameIf("XMLCOMMENT")) {
                    return DSL.xmlcomment(this.parseField());
                }
                if (this.parseFunctionNameIf("XMLTYPE")) {
                    return DSL.cast(this.parseField(), SQLDataType.XML);
                }
                field2 = this.parseFieldXMLConcatIf();
                if (field2 != null) {
                    return field2;
                }
                field2 = this.parseFieldXMLElementIf();
                if (field2 != null) {
                    return field2;
                }
                field2 = this.parseFieldXMLPIIf();
                if (field2 != null) {
                    return field2;
                }
                field2 = this.parseFieldXMLForestIf();
                if (field2 != null) {
                    return field2;
                }
                field2 = this.parseFieldXMLParseIf();
                if (field2 != null) {
                    return field2;
                }
                field2 = this.parseFieldXMLDocumentIf();
                if (field2 != null) {
                    return field2;
                }
                field2 = this.parseFieldXMLQueryIf();
                if (field2 != null) {
                    return field2;
                }
                field2 = this.parseFieldXMLSerializeIf();
                if (field2 == null) break;
                return field2;
            }
            case 'Y': {
                if (!this.parseFunctionNameIf("YEAR")) break;
                return DSL.year(this.parseFieldParenthesised());
            }
            case 'Z': {
                if (!this.parseFunctionNameIf("ZEROIFNULL")) break;
                return DSL.coalesce(this.parseFieldParenthesised(), new Field[]{DSL.zero()});
            }
            case '-': 
            case '.': 
            case '0': 
            case '1': 
            case '2': 
            case '3': 
            case '4': 
            case '5': 
            case '6': 
            case '7': 
            case '8': 
            case '9': {
                field2 = this.parseFieldUnsignedNumericLiteralIf(Sign.NONE);
                if (field2 == null) break;
                return field2;
            }
            case '{': {
                this.parse('{', false);
                switch (this.characterUpper()) {
                    case 'D': {
                        this.parseKeyword("D");
                        field2 = DSL.inline(this.parseDateLiteral());
                        break;
                    }
                    case 'F': {
                        this.parseKeyword("FN");
                        field2 = this.parseTerm();
                        break;
                    }
                    case 'T': {
                        if (this.parseKeywordIf("TS")) {
                            field2 = DSL.inline(this.parseTimestampLiteral());
                            break;
                        }
                        this.parseKeyword("T");
                        field2 = DSL.inline(this.parseTimeLiteral());
                        break;
                    }
                    default: {
                        throw this.exception("Unsupported JDBC escape literal");
                    }
                }
                this.parse('}');
                return field2;
            }
            case '(': {
                int p = this.position();
                EnumSet<FunctionKeyword> fk = this.forbidden;
                try {
                    FieldOrRow r;
                    if (!this.forbidden.isEmpty()) {
                        this.forbidden = EnumSet.noneOf(FunctionKeyword.class);
                    }
                    if ((r = this.parseScalarSubqueryIf()) != null) {
                        Field<?> field3 = r;
                        return field3;
                    }
                    this.parse('(');
                    r = this.parseFieldOrRow();
                    ArrayList list = null;
                    if (r instanceof Field) {
                        FieldOrRow f5 = r;
                        while (this.parseIf(',')) {
                            if (list == null) {
                                list = new ArrayList();
                                list.add((Field<?>)f5);
                            }
                            list.add(this.parseField());
                        }
                    }
                    this.parse(')');
                    FieldOrRow fieldOrRow = list != null ? DSL.row(list) : r;
                    return fieldOrRow;
                }
                finally {
                    this.forbidden = fk;
                }
            }
        }
        field2 = this.parseAggregateFunctionIf();
        if (field2 != null) {
            return field2;
        }
        field2 = this.parseBooleanValueExpressionIf();
        if (field2 != null) {
            return field2;
        }
        return this.parseFieldNameOrSequenceExpression();
    }

    private final Field<?> parseFieldAddDatePart(DatePart part) {
        return this.parseFunctionArgs2((f1, f2) -> DSL.dateAdd((Field<Date>)f1, (Field<? extends Number>)f2, part));
    }

    private final boolean peekSelectOrWith(boolean peekIntoParens) {
        return this.peekKeyword("WITH", false, peekIntoParens, false) || this.peekSelect(peekIntoParens);
    }

    private final boolean peekSelect(boolean peekIntoParens) {
        return this.peekKeyword("SELECT", false, peekIntoParens, false) || this.peekKeyword("SEL", false, peekIntoParens, false);
    }

    private final Field<?> parseFieldSysConnectByPathIf() {
        if (!this.ignoreProEdition() && this.parseFunctionNameIf("SYS_CONNECT_BY_PATH") && this.requireProEdition()) {
            this.parse('(');
            Field<?> x = this.parseField();
            this.parse(',');
            String y = this.parseStringLiteral();
            this.parse(')');
            return DSL.sysConnectByPath(x, y);
        }
        return null;
    }

    private final Field<?> parseFieldBitwiseFunctionIf() {
        int p = this.position();
        char c1 = this.character(p + 1);
        char c2 = this.character(p + 2);
        boolean agg = false;
        if (c1 != 'I' && c1 != 'i') {
            return null;
        }
        if (c2 != 'T' && c2 != 't' && c2 != 'N' && c2 != 'n') {
            return null;
        }
        if (this.parseKeywordIf("BIT_AND") || this.parseKeywordIf("BITAND") || this.parseKeywordIf("BIN_AND") || (agg = this.parseKeywordIf("BIT_AND_AGG")) || (agg = this.parseKeywordIf("BITAND_AGG")) || (agg = this.parseKeywordIf("BIN_AND_AGG"))) {
            this.parse('(');
            if (this.parseKeywordIf("DISTINCT", "ALL")) {
                agg = true;
            }
            Field<?> x = this.toField(this.parseNumericOp());
            if (agg && this.parse(')') || this.parseIf(')')) {
                return this.parseAggregateFunctionIf(false, DSL.bitAndAgg(x));
            }
            this.parse(',');
            Field<?> y = this.toField(this.parseNumericOp());
            this.parse(')');
            return DSL.bitAnd(x, y);
        }
        if (this.parseKeywordIf("BIT_NAND") || this.parseKeywordIf("BITNAND") || this.parseKeywordIf("BIN_NAND") || (agg = this.parseKeywordIf("BIT_NAND_AGG")) || (agg = this.parseKeywordIf("BITNAND_AGG")) || (agg = this.parseKeywordIf("BIN_NAND_AGG"))) {
            this.parse('(');
            if (this.parseKeywordIf("DISTINCT", "ALL")) {
                agg = true;
            }
            Field<?> x = this.toField(this.parseNumericOp());
            if (agg && this.parse(')') || this.parseIf(')')) {
                return this.parseAggregateFunctionIf(false, DSL.bitNandAgg(x));
            }
            this.parse(',');
            Field<?> y = this.toField(this.parseNumericOp());
            this.parse(')');
            return DSL.bitNand(x, y);
        }
        if (this.parseKeywordIf("BIT_OR") || this.parseKeywordIf("BITOR") || this.parseKeywordIf("BIN_OR") || (agg = this.parseKeywordIf("BIT_OR_AGG")) || (agg = this.parseKeywordIf("BITOR_AGG")) || (agg = this.parseKeywordIf("BIN_OR_AGG"))) {
            this.parse('(');
            if (this.parseKeywordIf("DISTINCT", "ALL")) {
                agg = true;
            }
            Field<?> x = this.toField(this.parseNumericOp());
            if (agg && this.parse(')') || this.parseIf(')')) {
                return this.parseAggregateFunctionIf(false, DSL.bitOrAgg(x));
            }
            this.parse(',');
            Field<?> y = this.toField(this.parseNumericOp());
            this.parse(')');
            return DSL.bitOr(x, y);
        }
        if (this.parseKeywordIf("BIT_NOR") || this.parseKeywordIf("BITNOR") || this.parseKeywordIf("BIN_NOR") || (agg = this.parseKeywordIf("BIT_NOR_AGG")) || (agg = this.parseKeywordIf("BITNOR_AGG")) || (agg = this.parseKeywordIf("BIN_NOR_AGG"))) {
            this.parse('(');
            if (this.parseKeywordIf("DISTINCT", "ALL")) {
                agg = true;
            }
            Field<?> x = this.toField(this.parseNumericOp());
            if (agg && this.parse(')') || this.parseIf(')')) {
                return this.parseAggregateFunctionIf(false, DSL.bitNorAgg(x));
            }
            this.parse(',');
            Field<?> y = this.toField(this.parseNumericOp());
            this.parse(')');
            return DSL.bitNor(x, y);
        }
        if (this.parseKeywordIf("BIT_XOR") || this.parseKeywordIf("BITXOR") || this.parseKeywordIf("BIN_XOR") || (agg = this.parseKeywordIf("BIT_XOR_AGG")) || (agg = this.parseKeywordIf("BITXOR_AGG")) || (agg = this.parseKeywordIf("BIN_XOR_AGG"))) {
            this.parse('(');
            Field<?> x = this.toField(this.parseNumericOp());
            if (agg && this.parse(')') || this.parseIf(')')) {
                return this.parseAggregateFunctionIf(false, DSL.bitXorAgg(x));
            }
            this.parse(',');
            Field<?> y = this.toField(this.parseNumericOp());
            this.parse(')');
            return DSL.bitXor(x, y);
        }
        if (this.parseKeywordIf("BIT_XNOR") || this.parseKeywordIf("BITXNOR") || this.parseKeywordIf("BIN_XNOR") || (agg = this.parseKeywordIf("BIT_XNOR_AGG")) || (agg = this.parseKeywordIf("BITXNOR_AGG")) || (agg = this.parseKeywordIf("BIN_XNOR_AGG"))) {
            this.parse('(');
            Field<?> x = this.toField(this.parseNumericOp());
            if (agg && this.parse(')') || this.parseIf(')')) {
                return this.parseAggregateFunctionIf(false, DSL.bitXNorAgg(x));
            }
            this.parse(',');
            Field<?> y = this.toField(this.parseNumericOp());
            this.parse(')');
            return DSL.bitXNor(x, y);
        }
        if (this.parseKeywordIf("BIT_NOT", "BITNOT", "BIN_NOT")) {
            this.parse('(');
            Field<?> x = this.toField(this.parseNumericOp());
            this.parse(')');
            return DSL.bitNot(x);
        }
        if (this.parseKeywordIf("BIN_SHL", "BITSHIFTLEFT")) {
            this.parse('(');
            Field<?> x = this.toField(this.parseNumericOp());
            this.parse(',');
            Field<?> y = this.toField(this.parseNumericOp());
            this.parse(')');
            return DSL.shl(x, y);
        }
        if (this.parseKeywordIf("BIN_SHR", "BITSHIFTRIGHT")) {
            this.parse('(');
            Field<?> x = this.toField(this.parseNumericOp());
            this.parse(',');
            Field<?> y = this.toField(this.parseNumericOp());
            this.parse(')');
            return DSL.shr(x, y);
        }
        return null;
    }

    private final Field<?> parseFieldNewIdIf() {
        if (this.parseFunctionNameIf("NEWID")) {
            this.parse('(');
            Long l = this.parseSignedIntegerLiteralIf();
            if (l != null && l != -1L) {
                throw this.expected("No argument or -1 expected");
            }
            this.parse(')');
            return DSL.uuid();
        }
        return null;
    }

    private final Field<?> parseNextValueIf() {
        if (this.parseKeywordIf("NEXT VALUE FOR")) {
            return DSL.sequence(this.parseName()).nextval();
        }
        return null;
    }

    private final Field<?> parseNextvalCurrvalIf(SequenceMethod method2) {
        if (this.parseFunctionNameIf(method2.name())) {
            this.parse('(');
            Name name = this.parseNameIf();
            Sequence<BigInteger> s2 = name != null ? DSL.sequence(name) : DSL.sequence(this.dsl.parser().parseName(this.parseStringLiteral()));
            this.parse(')');
            if (method2 == SequenceMethod.NEXTVAL) {
                return s2.nextval();
            }
            if (method2 == SequenceMethod.CURRVAL) {
                return s2.currval();
            }
            throw this.exception("Only NEXTVAL and CURRVAL methods supported");
        }
        return null;
    }

    private final Field<?> parseFieldXMLSerializeIf() {
        if (this.parseFunctionNameIf("XMLSERIALIZE")) {
            this.parse('(');
            boolean content = this.parseKeywordIf("CONTENT");
            if (!content) {
                this.parseKeywordIf("DOCUMENT");
            }
            Field<XML> value = this.parseField();
            this.parseKeyword("AS");
            DataType<?> type = this.parseCastDataType();
            this.parse(')');
            return content ? DSL.xmlserializeContent(value, type) : DSL.xmlserializeDocument(value, type);
        }
        return null;
    }

    private final Field<?> parseFieldXMLConcatIf() {
        if (this.parseFunctionNameIf("XMLCONCAT")) {
            this.parse('(');
            List<Field> fields = this.parseList(',', (? super ParseContext c) -> c.parseField());
            this.parse(')');
            return DSL.xmlconcat(fields);
        }
        return null;
    }

    private final Field<?> parseFieldXMLElementIf() {
        if (this.parseFunctionNameIf("XMLELEMENT")) {
            this.parse('(');
            this.parseKeywordIf("NAME");
            if (this.parseIf(')')) {
                return DSL.xmlelement(DSL.systemName("NAME"), new Field[0]);
            }
            Name name = this.parseIdentifier();
            XMLAttributes attr = null;
            ArrayList content = new ArrayList();
            while (this.parseIf(',')) {
                if (attr == null && this.parseKeywordIf("XMLATTRIBUTES")) {
                    this.parse('(');
                    List<Field<?>> attrs = this.parseAliasedXMLContent();
                    this.parse(')');
                    attr = DSL.xmlattributes(attrs);
                    continue;
                }
                content.add(this.parseField());
            }
            this.parse(')');
            return attr == null ? DSL.xmlelement(name, content) : DSL.xmlelement(name, attr, content);
        }
        return null;
    }

    private final Field<?> parseFieldXMLDocumentIf() {
        if (!this.ignoreProEdition() && this.parseFunctionNameIf("XMLDOCUMENT") && this.requireProEdition()) {
            this.parse('(');
            Field<XML> content = this.parseField();
            this.parse(')');
            return DSL.xmldocument(content);
        }
        return null;
    }

    private final Field<?> parseFieldXMLPIIf() {
        if (this.parseFunctionNameIf("XMLPI")) {
            this.parse('(');
            this.parseKeyword("NAME");
            Name target = this.parseIdentifier();
            Field<?> content = this.parseIf(',') ? this.parseField() : null;
            this.parse(')');
            return content == null ? DSL.xmlpi(target) : DSL.xmlpi(target, content);
        }
        return null;
    }

    private final Field<?> parseFieldXMLForestIf() {
        if (this.parseFunctionNameIf("XMLFOREST")) {
            this.parse('(');
            List<Field<?>> content = this.parseAliasedXMLContent();
            this.parse(')');
            return DSL.xmlforest(content);
        }
        return null;
    }

    private final Field<?> parseFieldXMLParseIf() {
        if (this.parseFunctionNameIf("XMLPARSE")) {
            QOM.DocumentOrContent documentOrContent;
            this.parse('(');
            if (this.parseKeywordIf("DOCUMENT")) {
                documentOrContent = QOM.DocumentOrContent.DOCUMENT;
            } else if (this.parseKeywordIf("CONTENT")) {
                documentOrContent = QOM.DocumentOrContent.CONTENT;
            } else {
                throw this.expected("CONTENT", "DOCUMENT");
            }
            Field<String> xml = this.parseField();
            this.parse(')');
            return documentOrContent == QOM.DocumentOrContent.DOCUMENT ? DSL.xmlparseDocument(xml) : DSL.xmlparseContent(xml);
        }
        return null;
    }

    private final Field<?> parseFieldXMLQueryIf() {
        if (this.parseFunctionNameIf("XMLQUERY")) {
            this.parse('(');
            Field<String> xpath = this.parseField();
            QOM.XMLPassingMechanism m4 = this.parseXMLPassingMechanism();
            Field<XML> xml = this.parseField();
            this.parseKeywordIf("RETURNING CONTENT");
            this.parse(')');
            if (m4 == QOM.XMLPassingMechanism.BY_REF) {
                return DSL.xmlquery(xpath).passingByRef(xml);
            }
            if (m4 == QOM.XMLPassingMechanism.BY_VALUE) {
                return DSL.xmlquery(xpath).passingByValue(xml);
            }
            return DSL.xmlquery(xpath).passing(xml);
        }
        return null;
    }

    private final QOM.XMLPassingMechanism parseXMLPassingMechanism() {
        QOM.XMLPassingMechanism result = this.parseXMLPassingMechanismIf();
        if (result == null) {
            throw this.expected("PASSING");
        }
        return result;
    }

    private final QOM.XMLPassingMechanism parseXMLPassingMechanismIf() {
        if (!this.parseKeywordIf("PASSING")) {
            return null;
        }
        if (!this.parseKeywordIf("BY")) {
            return QOM.XMLPassingMechanism.DEFAULT;
        }
        if (this.parseKeywordIf("REF")) {
            return QOM.XMLPassingMechanism.BY_REF;
        }
        if (this.parseKeywordIf("VALUE")) {
            return QOM.XMLPassingMechanism.BY_VALUE;
        }
        throw this.expected("REF", "VALUE");
    }

    private final List<Field<?>> parseAliasedXMLContent() {
        ArrayList result = new ArrayList();
        do {
            SelectField<Object> field2 = this.parseField();
            if (this.parseKeywordIf("AS")) {
                field2 = field2.as(this.parseIdentifier(true, false));
            }
            result.add((Field<?>)field2);
        } while (this.parseIf(','));
        return result;
    }

    private final AggregateFilterStep<?> parseXMLAggFunctionIf() {
        if (this.parseFunctionNameIf("XMLAGG")) {
            XMLAggOrderByStep<XML> s1;
            this.parse('(');
            this.parseKeywordIf("ALL");
            AggregateFilterStep<XML> s2 = s1 = DSL.xmlagg(this.parseField());
            if (this.parseKeywordIf("ORDER BY")) {
                s2 = s1.orderBy(this.parseList(',', (? super ParseContext c) -> c.parseSortField()));
            }
            this.parse(')');
            return s2;
        }
        return null;
    }

    private final Field<?> parseFieldJSONValueIf() {
        if (this.parseFunctionNameIf("JSON_VALUE")) {
            this.parse('(');
            Field<JSON> json = this.parseField();
            this.parse(',');
            Field<String> path = this.parseField();
            JSONValueOnStep<JSON> s1 = DSL.jsonValue(json, path);
            JSONValue.Behaviour behaviour = this.parseJSONValueBehaviourIf();
            while (behaviour != null) {
                boolean onError2;
                JSONValueDefaultStep<JSON> s2 = behaviour == JSONValue.Behaviour.DEFAULT ? s1.default_(this.parseField()) : null;
                boolean onEmpty = this.parseKeywordIf("ON EMPTY");
                boolean bl = onError2 = !onEmpty && this.parseKeyword("ON ERROR");
                if (onEmpty) {
                    switch (behaviour) {
                        case ERROR: {
                            s1 = s1.errorOnEmpty();
                            break;
                        }
                        case NULL: {
                            s1 = s1.nullOnEmpty();
                            break;
                        }
                        case DEFAULT: {
                            s1 = s2.onEmpty();
                        }
                    }
                } else if (onError2) {
                    switch (behaviour) {
                        case ERROR: {
                            s1 = s1.errorOnError();
                            break;
                        }
                        case NULL: {
                            s1 = s1.nullOnError();
                            break;
                        }
                        case DEFAULT: {
                            s1 = s2.onError();
                        }
                    }
                }
                behaviour = this.parseJSONValueBehaviourIf();
            }
            DataType<?> returning = this.parseJSONReturningIf();
            this.parse(')');
            return returning == null ? s1 : s1.returning(returning);
        }
        return null;
    }

    private final JSONValue.Behaviour parseJSONValueBehaviourIf() {
        if (!this.ignoreProEdition() && this.parseKeywordIf("ERROR") && this.requireProEdition()) {
            return JSONValue.Behaviour.ERROR;
        }
        if (!this.ignoreProEdition() && this.parseKeywordIf("NULL") && this.requireProEdition()) {
            return JSONValue.Behaviour.NULL;
        }
        if (!this.ignoreProEdition() && this.parseKeywordIf("DEFAULT") && this.requireProEdition()) {
            return JSONValue.Behaviour.DEFAULT;
        }
        return null;
    }

    private final JSONExists.Behaviour parseJSONExistsOnErrorBehaviourIf() {
        if (!this.ignoreProEdition() && this.parseKeywordIf("ERROR") && this.parseKeyword("ON ERROR") && this.requireProEdition()) {
            return JSONExists.Behaviour.ERROR;
        }
        if (!this.ignoreProEdition() && this.parseKeywordIf("TRUE") && this.parseKeyword("ON ERROR") && this.requireProEdition()) {
            return JSONExists.Behaviour.TRUE;
        }
        if (!this.ignoreProEdition() && this.parseKeywordIf("FALSE") && this.parseKeyword("ON ERROR") && this.requireProEdition()) {
            return JSONExists.Behaviour.FALSE;
        }
        if (!this.ignoreProEdition() && this.parseKeywordIf("UNKNOWN") && this.parseKeyword("ON ERROR") && this.requireProEdition()) {
            return JSONExists.Behaviour.UNKNOWN;
        }
        return null;
    }

    private final DataType<?> parseJSONReturningIf() {
        return this.parseKeywordIf("RETURNING") ? this.parseDataType() : null;
    }

    private final Field<?> parseFieldJSONLiteralIf() {
        if (this.parseKeywordIf("JSON")) {
            if (this.parseIf('{')) {
                if (this.parseIf('}')) {
                    return DSL.jsonObject();
                }
                List<JSONEntry> entries = this.parseList(',', (? super ParseContext ctx) -> {
                    Field<String> key = this.parseField();
                    this.parse(':');
                    return DSL.key(key).value(this.parseField());
                });
                this.parse('}');
                return DSL.jsonObject(entries);
            }
            if (this.parseIf('[')) {
                if (this.parseIf(']')) {
                    return DSL.jsonArray(new Field[0]);
                }
                List<Field> fields = this.parseList(',', (? super ParseContext c) -> this.parseField());
                this.parse(']');
                return DSL.jsonArray(fields);
            }
            throw this.expected("[", "{");
        }
        return null;
    }

    private final Field<?> parseFieldJSONArrayConstructorIf() {
        boolean jsonb = false;
        if (this.parseFunctionNameIf("JSON_ARRAY", "JSON_BUILD_ARRAY") || (jsonb = this.parseFunctionNameIf("JSONB_BUILD_ARRAY"))) {
            JSONArrayNullStep<Serializable> s1;
            this.parse('(');
            if (this.parseIf(')')) {
                return jsonb ? DSL.jsonbArray(new Field[0]) : DSL.jsonArray(new Field[0]);
            }
            List<Field> result = null;
            QOM.JSONOnNull onNull = this.parseJSONNullTypeIf();
            DataType<?> returning = this.parseJSONReturningIf();
            if (onNull == null && returning == null) {
                result = this.parseList(',', (? super ParseContext c) -> c.parseField());
                onNull = this.parseJSONNullTypeIf();
                returning = this.parseJSONReturningIf();
            }
            this.parse(')');
            JSONArrayNullStep<Serializable> jSONArrayNullStep = result == null ? (jsonb ? DSL.jsonbArray(new Field[0]) : DSL.jsonArray(new Field[0])) : (s1 = jsonb ? DSL.jsonbArray(result) : DSL.jsonArray(result));
            JSONArrayReturningStep<Serializable> s2 = onNull == QOM.JSONOnNull.NULL_ON_NULL ? s1.nullOnNull() : (onNull == QOM.JSONOnNull.ABSENT_ON_NULL ? s1.absentOnNull() : s1);
            return returning == null ? s2 : s2.returning(returning);
        }
        return null;
    }

    private final AggregateFilterStep<?> parseJSONArrayAggFunctionIf() {
        boolean jsonb = false;
        if (this.parseFunctionNameIf("JSON_ARRAYAGG", "JSON_AGG", "JSON_GROUP_ARRAY") || (jsonb = this.parseFunctionNameIf("JSONB_AGG"))) {
            DataType<?> returning;
            QOM.JSONOnNull onNull;
            this.parse('(');
            boolean distinct2 = this.parseSetQuantifier();
            JSONArrayAggOrderByStep<Serializable> s1 = jsonb ? (distinct2 ? DSL.jsonbArrayAggDistinct(this.parseField()) : DSL.jsonbArrayAgg(this.parseField())) : (distinct2 ? DSL.jsonArrayAggDistinct(this.parseField()) : DSL.jsonArrayAgg(this.parseField()));
            JSONArrayAggNullStep<Serializable> s2 = s1;
            JSONArrayAggReturningStep<Serializable> s3 = s1;
            AggregateFilterStep<Serializable> result = s1;
            if (this.parseKeywordIf("ORDER BY")) {
                s2 = s1.orderBy(this.parseList(',', (? super ParseContext c) -> c.parseSortField()));
                s3 = s2;
                result = s2;
            }
            if ((onNull = this.parseJSONNullTypeIf()) != null) {
                s3 = onNull == QOM.JSONOnNull.ABSENT_ON_NULL ? s2.absentOnNull() : s2.nullOnNull();
                result = s3;
            }
            if ((returning = this.parseJSONReturningIf()) != null) {
                result = s3.returning(returning);
            }
            this.parse(')');
            return result;
        }
        return null;
    }

    private final Field<?> parseFieldArrayConstructIf() {
        boolean absentOnNull = false;
        if ((this.parseFunctionNameIf("ARRAY_CONSTRUCT") || (absentOnNull = this.parseFunctionNameIf("ARRAY_CONSTRUCT_COMPACT"))) && this.requireProEdition()) {
            this.parse('(');
            if (this.parseIf(')')) {
                return absentOnNull ? DSL.jsonArray(new Field[0]).absentOnNull() : DSL.jsonArray(new Field[0]);
            }
            List<Field> result = this.parseList(',', (? super ParseContext c) -> c.parseField());
            this.parse(')');
            return absentOnNull ? DSL.jsonArray(result).absentOnNull() : DSL.jsonArray(result);
        }
        return null;
    }

    private final Field<?> parseFieldObjectConstructIf() {
        boolean nullOnNull = false;
        if ((this.parseFunctionNameIf("OBJECT_CONSTRUCT") || (nullOnNull = this.parseFunctionNameIf("OBJECT_CONSTRUCT_KEEP_NULL"))) && this.requireProEdition()) {
            this.parse('(');
            if (this.parseIf(')')) {
                return nullOnNull ? DSL.jsonObject() : DSL.jsonObject().absentOnNull();
            }
            List<JSONEntry> result = this.parseList(',', (? super ParseContext c) -> this.parseJSONEntry(false));
            this.parse(')');
            return nullOnNull ? DSL.jsonObject(result) : DSL.jsonObject(result).absentOnNull();
        }
        return null;
    }

    private final Field<?> parseFieldJSONObjectConstructorIf() {
        boolean jsonb = false;
        if (this.parseFunctionNameIf("JSON_OBJECT", "JSON_BUILD_OBJECT") || (jsonb = this.parseFunctionNameIf("JSONB_BUILD_OBJECT"))) {
            JSONObjectNullStep<Serializable> s1;
            List<Object> result;
            this.parse('(');
            if (this.parseIf(')')) {
                return jsonb ? DSL.jsonbObject() : DSL.jsonObject();
            }
            QOM.JSONOnNull onNull = this.parseJSONNullTypeIf();
            DataType<?> returning = this.parseJSONReturningIf();
            if (onNull == null && returning == null) {
                result = this.parseList(',', (? super ParseContext c) -> this.parseJSONEntry());
                onNull = this.parseJSONNullTypeIf();
                returning = this.parseJSONReturningIf();
            } else {
                result = new ArrayList();
            }
            this.parse(')');
            JSONObjectNullStep<Serializable> jSONObjectNullStep = s1 = jsonb ? DSL.jsonbObject(result) : DSL.jsonObject(result);
            JSONObjectReturningStep<Serializable> s2 = onNull == QOM.JSONOnNull.NULL_ON_NULL ? s1.nullOnNull() : (onNull == QOM.JSONOnNull.ABSENT_ON_NULL ? s1.absentOnNull() : s1);
            return returning == null ? s2 : s2.returning(returning);
        }
        return null;
    }

    private final AggregateFilterStep<?> parseJSONObjectAggFunctionIf() {
        boolean jsonb = false;
        if (this.parseFunctionNameIf("JSON_OBJECTAGG", "JSON_OBJECT_AGG", "JSON_GROUP_OBJECT") || (jsonb = this.parseFunctionNameIf("JSONB_OBJECT_AGG"))) {
            DataType<?> returning;
            this.parse('(');
            this.parseKeywordIf("ALL");
            JSONObjectAggNullStep<Serializable> s1 = jsonb ? DSL.jsonbObjectAgg(this.parseJSONEntry()) : DSL.jsonObjectAgg(this.parseJSONEntry());
            JSONObjectAggReturningStep<Serializable> s2 = s1;
            AggregateFilterStep<Serializable> result = s1;
            QOM.JSONOnNull onNull = this.parseJSONNullTypeIf();
            if (onNull != null) {
                s2 = onNull == QOM.JSONOnNull.ABSENT_ON_NULL ? s1.absentOnNull() : s1.nullOnNull();
                result = s2;
            }
            if ((returning = this.parseJSONReturningIf()) != null) {
                result = s2.returning(returning);
            }
            this.parse(')');
            return result;
        }
        if (this.parseFunctionNameIf("OBJECT_AGG") && this.requireProEdition()) {
            this.parse('(');
            JSONObjectAggNullStep<JSON> result = DSL.jsonObjectAgg(this.parseJSONEntry());
            this.parse(')');
            return result;
        }
        return null;
    }

    private final QOM.JSONOnNull parseJSONNullTypeIf() {
        if (this.parseKeywordIf("NULL ON NULL")) {
            return QOM.JSONOnNull.NULL_ON_NULL;
        }
        if (this.parseKeywordIf("ABSENT ON NULL")) {
            return QOM.JSONOnNull.ABSENT_ON_NULL;
        }
        return null;
    }

    private final JSONEntry<?> parseJSONEntry() {
        return this.parseJSONEntry(true);
    }

    private final JSONEntry<?> parseJSONEntry(boolean supportKeyValue) {
        boolean valueRequired = supportKeyValue && this.parseKeywordIf("KEY");
        Field<String> key = this.parseField();
        if (!supportKeyValue || !this.parseKeywordIf("VALUE")) {
            if (valueRequired) {
                throw this.expected("VALUE");
            }
            this.parse(',');
        }
        return DSL.key(key).value(this.parseField());
    }

    private final Field<?> parseArrayValueConstructorIf() {
        if (this.parseKeywordIf("ARRAY")) {
            if (this.parseIf('[')) {
                List<Object> fields;
                if (this.parseIf(']')) {
                    fields = Collections.emptyList();
                } else {
                    fields = this.parseList(',', (? super ParseContext c) -> c.parseField());
                    this.parse(']');
                }
                return DSL.array(fields);
            }
            if (this.parseIf('(')) {
                SelectQueryImpl<Record> select = this.parseWithOrSelect(1);
                this.parse(')');
                return DSL.array(select);
            }
            throw this.expected("[", "(");
        }
        return null;
    }

    private final Field<?> parseMultisetValueConstructorIf() {
        if (this.parseKeywordIf("MULTISET")) {
            if (this.parseIf('(')) {
                SelectQueryImpl<Record> select = this.parseWithOrSelect();
                this.parse(')');
                return DSL.multiset(select);
            }
            throw this.expected("(");
        }
        return null;
    }

    private final Field<?> parseFieldLogIf() {
        if (this.parseFunctionNameIf("LOG")) {
            this.parse('(');
            Field<?> f1 = this.toField(this.parseNumericOp());
            Field<?> f2 = this.parseIf(',') ? this.toField(this.parseNumericOp()) : null;
            this.parse(')');
            switch (this.parseFamily()) {
                case SQLSERVER: 
                case SQLDATAWAREHOUSE: {
                    return f2 == null ? DSL.ln(f1) : DSL.log(f1, f2);
                }
                case AURORA_POSTGRES: 
                case COCKROACHDB: 
                case REDSHIFT: 
                case TERADATA: 
                case VERTICA: 
                case POSTGRES: 
                case SQLITE: 
                case YUGABYTEDB: {
                    return f2 == null ? DSL.log10(f1) : DSL.log(f2, f1);
                }
            }
            return f2 == null ? DSL.ln(f1) : DSL.log(f2, f1);
        }
        return null;
    }

    private final Field<?> parseFieldTruncIf() {
        boolean forceNumericPrecision = false;
        if (this.parseFunctionNameIf("TRUNC") || (forceNumericPrecision |= this.parseFunctionNameIf("TRUNCATE", "TRUNCNUM"))) {
            this.parse('(');
            Field<?> arg1 = this.parseField();
            if (forceNumericPrecision && this.parse(',') || this.parseIf(',')) {
                String part;
                if (!forceNumericPrecision && (part = this.parseStringLiteralIf()) != null) {
                    DatePart p;
                    if ("YY".equals(part = part.toUpperCase()) || "YYYY".equals(part) || "YEAR".equals(part)) {
                        p = DatePart.YEAR;
                    } else if ("MM".equals(part) || "MONTH".equals(part)) {
                        p = DatePart.MONTH;
                    } else if ("DD".equals(part)) {
                        p = DatePart.DAY;
                    } else if ("HH".equals(part)) {
                        p = DatePart.HOUR;
                    } else if ("MI".equals(part)) {
                        p = DatePart.MINUTE;
                    } else if ("SS".equals(part)) {
                        p = DatePart.SECOND;
                    } else {
                        throw this.exception("Unsupported date part");
                    }
                    this.parse(')');
                    return DSL.trunc(arg1, p);
                }
                Field<Integer> arg2 = this.toField(this.parseNumericOp());
                this.parse(')');
                return DSL.trunc(arg1, arg2);
            }
            this.parse(')');
            if (arg1 instanceof CurrentDate) {
                return arg1;
            }
            if (arg1.getDataType().isDateTime()) {
                return DSL.trunc(arg1, DatePart.DAY);
            }
            if (arg1.getDataType().isNumeric()) {
                return DSL.trunc(arg1, DSL.inline(0));
            }
            return DSL.trunc(arg1);
        }
        return null;
    }

    private final Field<?> parseFieldRoundIf() {
        if (this.parseFunctionNameIf("ROUND")) {
            this.parse('(');
            Field<?> arg1 = this.toField(this.parseNumericOp());
            Field<Integer> arg2 = this.parseIf(',') ? this.toField(this.parseNumericOp()) : null;
            this.parse(')');
            return arg2 == null ? DSL.round(arg1) : DSL.round(arg1, arg2);
        }
        return null;
    }

    private final Field<?> parseFieldLeastIf() {
        if (this.parseFunctionNameIf("LEAST", "MINVALUE")) {
            this.parse('(');
            List<Field> fields = this.parseList(',', (? super ParseContext c) -> c.parseField());
            this.parse(')');
            return DSL.least(fields.get(0), fields.size() > 1 ? fields.subList(1, fields.size()).toArray(Tools.EMPTY_FIELD) : Tools.EMPTY_FIELD);
        }
        return null;
    }

    private final Field<?> parseFieldGreatestIf() {
        if (this.parseFunctionNameIf("GREATEST", "MAXVALUE")) {
            this.parse('(');
            List<Field> fields = this.parseList(',', (? super ParseContext c) -> c.parseField());
            this.parse(')');
            return DSL.greatest(fields.get(0), fields.size() > 1 ? fields.subList(1, fields.size()).toArray(Tools.EMPTY_FIELD) : Tools.EMPTY_FIELD);
        }
        return null;
    }

    private final Field<?> parseFieldGroupingIdIf() {
        if (!this.ignoreProEdition() && this.parseFunctionNameIf("GROUPING_ID") && this.requireProEdition()) {
            this.parse('(');
            List<Field> fields = this.parseList(',', (? super ParseContext c) -> c.parseField());
            this.parse(')');
            return DSL.groupingId(fields.toArray(Tools.EMPTY_FIELD));
        }
        return null;
    }

    private final Field<?> parseFieldTimestampLiteralIf() {
        int p = this.position();
        if (this.parseKeywordIf("TIMESTAMP")) {
            if (this.parseKeywordIf("WITHOUT TIME ZONE")) {
                return DSL.inline(this.parseTimestampLiteral());
            }
            if (this.parseIf('(')) {
                Field<?> f = this.parseField();
                this.parse(')');
                return DSL.timestamp(f);
            }
            if (this.peek('\'')) {
                return DSL.inline(this.parseTimestampLiteral());
            }
            this.position(p);
            return DSL.field(this.parseIdentifier());
        }
        return null;
    }

    private final Timestamp parseTimestampLiteral() {
        try {
            return Timestamp.valueOf(this.parseStringLiteral());
        }
        catch (IllegalArgumentException e) {
            throw this.exception("Illegal timestamp literal");
        }
    }

    private final Field<?> parseFieldTimeLiteralIf() {
        int p = this.position();
        if (this.parseKeywordIf("TIME")) {
            if (this.parseKeywordIf("WITHOUT TIME ZONE")) {
                return DSL.inline(this.parseTimeLiteral());
            }
            if (this.parseIf('(')) {
                Field<?> f = this.parseField();
                this.parse(')');
                return DSL.time(f);
            }
            if (this.peek('\'')) {
                return DSL.inline(this.parseTimeLiteral());
            }
            this.position(p);
            return DSL.field(this.parseIdentifier());
        }
        return null;
    }

    private final Time parseTimeLiteral() {
        try {
            return Time.valueOf(this.parseStringLiteral());
        }
        catch (IllegalArgumentException e) {
            throw this.exception("Illegal time literal");
        }
    }

    private final Field<?> parseFieldIntervalLiteralIf() {
        int p = this.position();
        if (this.parseKeywordIf("INTERVAL")) {
            if (this.peek('\'')) {
                return DSL.inline(this.parseIntervalLiteral());
            }
            Long interval = this.parseUnsignedIntegerLiteralIf();
            if (interval != null) {
                DatePart part = this.parseIntervalDatePart();
                long l = interval;
                int i = Tools.asInt(l);
                switch (part) {
                    case YEAR: {
                        return DSL.inline(new YearToMonth(i));
                    }
                    case QUARTER: {
                        return DSL.inline(new YearToMonth(0, 3 * i));
                    }
                    case MONTH: {
                        return DSL.inline(new YearToMonth(0, i));
                    }
                    case WEEK: {
                        return DSL.inline(new DayToSecond(7 * i));
                    }
                    case DAY: {
                        return DSL.inline(new DayToSecond(i));
                    }
                    case HOUR: {
                        return DSL.inline(new DayToSecond(0, i));
                    }
                    case MINUTE: {
                        return DSL.inline(new DayToSecond(0, 0, i));
                    }
                    case SECOND: {
                        return DSL.inline(new DayToSecond(0, 0, 0, i));
                    }
                    case MILLISECOND: {
                        return DSL.inline(new DayToSecond(0, 0, 0, Tools.asInt(l / 1000L), (int)(l % 1000L * 1000000L)));
                    }
                    case MICROSECOND: {
                        return DSL.inline(new DayToSecond(0, 0, 0, Tools.asInt(l / 1000000L), (int)(l % 1000000L * 1000L)));
                    }
                    case NANOSECOND: {
                        return DSL.inline(new DayToSecond(0, 0, 0, Tools.asInt(l / 1000000000L), (int)(l % 1000000000L)));
                    }
                }
            } else {
                this.position(p);
                return DSL.field(this.parseIdentifier());
            }
        }
        return null;
    }

    private final Interval parsePostgresIntervalLiteralIf() {
        int p;
        block17: {
            p = this.position();
            if (this.parseIf('\'')) {
                this.parseIf('@');
                Number year = null;
                Number month = null;
                Number day = null;
                Number hour = null;
                Number minute = null;
                Number second = null;
                block7: do {
                    Number n;
                    boolean minus2;
                    if (!(minus2 = this.parseIf('-'))) {
                        this.parseIf('+');
                    }
                    if ((n = this.parseUnsignedNumericLiteralIf(minus2 ? Sign.MINUS : Sign.NONE)) == null) break block17;
                    switch (this.characterUpper()) {
                        case 'D': {
                            if (!this.parseKeywordIf("D") && !this.parseKeywordIf("DAY") && !this.parseKeywordIf("DAYS")) continue block7;
                            if (day == null) {
                                day = n;
                                break;
                            }
                            throw this.exception("Day part already defined");
                        }
                        case 'H': {
                            if (!this.parseKeywordIf("H") && !this.parseKeywordIf("HOUR") && !this.parseKeywordIf("HOURS")) continue block7;
                            if (hour == null) {
                                hour = n;
                                break;
                            }
                            throw this.exception("Hour part already defined");
                        }
                        case 'M': {
                            if (this.parseKeywordIf("M") || this.parseKeywordIf("MIN") || this.parseKeywordIf("MINS") || this.parseKeywordIf("MINUTE") || this.parseKeywordIf("MINUTES")) {
                                if (minute == null) {
                                    minute = n;
                                    break;
                                }
                                throw this.exception("Minute part already defined");
                            }
                            if (!this.parseKeywordIf("MON") && !this.parseKeywordIf("MONS") && !this.parseKeywordIf("MONTH") && !this.parseKeywordIf("MONTHS")) continue block7;
                            if (month == null) {
                                month = n;
                                break;
                            }
                            throw this.exception("Month part already defined");
                        }
                        case 'S': {
                            if (!this.parseKeywordIf("S") && !this.parseKeywordIf("SEC") && !this.parseKeywordIf("SECS") && !this.parseKeywordIf("SECOND") && !this.parseKeywordIf("SECONDS")) continue block7;
                            if (second == null) {
                                second = n;
                                break;
                            }
                            throw this.exception("Second part already defined");
                        }
                        case 'Y': {
                            if (!this.parseKeywordIf("Y") && !this.parseKeywordIf("YEAR") && !this.parseKeywordIf("YEARS")) continue block7;
                            if (year == null) {
                                year = n;
                                break;
                            }
                            throw this.exception("Year part already defined");
                        }
                        default: {
                            break block17;
                        }
                    }
                } while (!this.parseIf('\''));
                int months = (month == null ? 0 : month.intValue()) + (year == null ? 0 : Tools.asInt((long)(year.doubleValue() * 12.0)));
                double seconds = (month == null ? 0.0 : month.doubleValue() % 1.0 * 30.0 * 86400.0) + (day == null ? 0.0 : day.doubleValue() * 86400.0) + (hour == null ? 0.0 : hour.doubleValue() * 3600.0) + (minute == null ? 0.0 : minute.doubleValue() * 60.0) + (second == null ? 0.0 : second.doubleValue());
                return new YearToSecond(new YearToMonth(0, months), new DayToSecond(0, 0, 0, Tools.asInt((long)seconds), Tools.asInt((long)(seconds % 1.0 * 1.0E9))));
            }
        }
        this.position(p);
        return null;
    }

    private final boolean parseIntervalPrecisionKeywordIf(String keyword) {
        if (this.parseKeywordIf(keyword)) {
            if (this.parseIf('(')) {
                this.parseUnsignedIntegerLiteral();
                this.parse(')');
            }
            return true;
        }
        return false;
    }

    private final Interval parseIntervalLiteral() {
        Interval result = this.parsePostgresIntervalLiteralIf();
        if (result != null) {
            return result;
        }
        String string = this.parseStringLiteral();
        String message = "Illegal interval literal";
        if (this.parseIntervalPrecisionKeywordIf("YEAR")) {
            if (this.parseKeywordIf("TO") && this.parseIntervalPrecisionKeywordIf("MONTH")) {
                return this.requireNotNull(YearToMonth.yearToMonth(string), message);
            }
            return this.requireNotNull(YearToMonth.year(string), message);
        }
        if (this.parseIntervalPrecisionKeywordIf("MONTH")) {
            return this.requireNotNull(YearToMonth.month(string), message);
        }
        if (this.parseIntervalPrecisionKeywordIf("DAY")) {
            if (this.parseKeywordIf("TO")) {
                if (this.parseIntervalPrecisionKeywordIf("SECOND")) {
                    return this.requireNotNull(DayToSecond.dayToSecond(string), message);
                }
                if (this.parseIntervalPrecisionKeywordIf("MINUTE")) {
                    return this.requireNotNull(DayToSecond.dayToMinute(string), message);
                }
                if (this.parseIntervalPrecisionKeywordIf("HOUR")) {
                    return this.requireNotNull(DayToSecond.dayToHour(string), message);
                }
                throw this.expected("HOUR", "MINUTE", "SECOND");
            }
            return this.requireNotNull(DayToSecond.day(string), message);
        }
        if (this.parseIntervalPrecisionKeywordIf("HOUR")) {
            if (this.parseKeywordIf("TO")) {
                if (this.parseIntervalPrecisionKeywordIf("SECOND")) {
                    return this.requireNotNull(DayToSecond.hourToSecond(string), message);
                }
                if (this.parseIntervalPrecisionKeywordIf("MINUTE")) {
                    return this.requireNotNull(DayToSecond.hourToMinute(string), message);
                }
                throw this.expected("MINUTE", "SECOND");
            }
            return this.requireNotNull(DayToSecond.hour(string), message);
        }
        if (this.parseIntervalPrecisionKeywordIf("MINUTE")) {
            if (this.parseKeywordIf("TO") && this.parseIntervalPrecisionKeywordIf("SECOND")) {
                return this.requireNotNull(DayToSecond.minuteToSecond(string), message);
            }
            return this.requireNotNull(DayToSecond.minute(string), message);
        }
        if (this.parseIntervalPrecisionKeywordIf("SECOND")) {
            return this.requireNotNull(DayToSecond.second(string), message);
        }
        DayToSecond ds = DayToSecond.valueOf(string);
        if (ds != null) {
            return ds;
        }
        YearToMonth ym = YearToMonth.valueOf(string);
        if (ym != null) {
            return ym;
        }
        YearToSecond ys = YearToSecond.valueOf(string);
        if (ys != null) {
            return ys;
        }
        throw this.exception(message);
    }

    private final <T> T requireNotNull(T value, String message) {
        if (value != null) {
            return value;
        }
        throw this.exception(message);
    }

    private final Field<?> parseFieldDateLiteralIf() {
        int p = this.position();
        if (this.parseKeywordIf("DATE")) {
            if (this.parseIf('(')) {
                Field<?> f = this.parseField();
                this.parse(')');
                return DSL.date(f);
            }
            if (this.peek('\'')) {
                return DSL.inline(this.parseDateLiteral());
            }
            if (this.parseDialect() == SQLDialect.TERADATA) {
                return DSL.currentDate();
            }
            this.position(p);
            return DSL.field(this.parseIdentifier());
        }
        return null;
    }

    private final Field<?> parseFieldDateTruncIf() {
        if (this.parseFunctionNameIf("DATE_TRUNC", "DATETIME_TRUNC")) {
            DatePart part;
            Field<?> field2;
            this.parse('(');
            switch (this.parseFamily()) {
                case BIGQUERY: {
                    field2 = this.parseField();
                    this.parse(',');
                    part = this.parseDatePart();
                    break;
                }
                default: {
                    part = this.parseDatePart();
                    this.parse(',');
                    field2 = this.parseField();
                }
            }
            this.parse(')');
            return DSL.trunc(field2, part);
        }
        return null;
    }

    private final Field<?> parseFieldDateAddIf() {
        boolean sub = false;
        if (this.parseFunctionNameIf("DATEADD")) {
            this.parse('(');
            DatePart part = this.parseDatePart();
            this.parse(',');
            Field<?> interval = this.parseField();
            this.parse(',');
            Field<Date> date = this.parseField();
            this.parse(')');
            return DSL.dateAdd(date, interval, part);
        }
        if (this.parseFunctionNameIf("DATE_ADD") || (sub = this.parseFunctionNameIf("DATE_SUB"))) {
            this.parse('(');
            Field<Date> d = this.parseField();
            Field<Date> date = d.getDataType().isDateTime() ? d : d.coerce(SQLDataType.TIMESTAMP);
            this.parse(',');
            Field<?> interval = this.parseFieldIntervalLiteralIf();
            this.parse(')');
            return sub ? DSL.dateSub(date, interval) : DSL.dateAdd(date, interval);
        }
        return null;
    }

    private final Field<?> parseFieldDateDiffIf() {
        if (this.parseFunctionNameIf("DATEDIFF")) {
            this.parse('(');
            DatePart datePart = this.parseDatePartIf();
            if (datePart != null) {
                this.parse(',');
            }
            Field<Date> d1 = this.parseField();
            if (this.parseIf(',')) {
                Field<Date> d2 = this.parseField();
                this.parse(')');
                if (datePart != null) {
                    return DSL.dateDiff(datePart, d1, d2);
                }
                return DSL.dateDiff(d1, d2);
            }
            this.parse(')');
            if (datePart != null) {
                return DSL.dateDiff(DSL.field(datePart.toName()), d1);
            }
            throw this.unsupportedClause();
        }
        if (this.parseFamily() == SQLDialect.BIGQUERY && this.parseFunctionNameIf("DATE_DIFF")) {
            this.parse('(');
            Field<Date> d1 = this.parseField();
            this.parse(',');
            Field<Date> d2 = this.parseField();
            this.parse(',');
            DatePart datePart = this.parseDatePart();
            this.parse(')');
            return DSL.dateDiff(datePart, d1, d2);
        }
        return null;
    }

    private final Date parseDateLiteral() {
        try {
            return Date.valueOf(this.parseStringLiteral());
        }
        catch (IllegalArgumentException e) {
            throw this.exception("Illegal date literal");
        }
    }

    private final Field<?> parseFieldExtractIf() {
        if (this.parseFunctionNameIf("EXTRACT")) {
            this.parse('(');
            DatePart part = this.parseDatePart();
            this.parseKeyword("FROM");
            Field<?> field2 = this.parseField();
            this.parse(')');
            return DSL.extract(field2, part);
        }
        return null;
    }

    private final Field<?> parseFieldDatePartIf() {
        if (this.parseFunctionNameIf("DATEPART", "DATE_PART")) {
            this.parse('(');
            DatePart part = this.parseDatePart();
            this.parse(',');
            Field<?> field2 = this.parseField();
            this.parse(')');
            return DSL.extract(field2, part);
        }
        return null;
    }

    private final DatePart parseDatePart() {
        DatePart result = this.parseDatePartIf();
        if (result == null) {
            throw this.expected("DatePart");
        }
        return result;
    }

    private final DatePart parseDatePartIf() {
        int p = this.position();
        boolean string = this.parseIf('\'');
        DatePart result = this.parseDatePartIf0();
        if (result == null) {
            this.position(p);
        } else if (string) {
            this.parse('\'');
        }
        if (this.parseIf("::")) {
            this.parseDataType();
        }
        return result;
    }

    private final DatePart parseDatePartIf0() {
        char character = this.characterUpper();
        switch (character) {
            case 'C': {
                if (!this.parseKeywordIf("CENTURY") && !this.parseKeywordIf("CENTURIES")) break;
                return DatePart.CENTURY;
            }
            case 'D': {
                if (this.parseKeywordIf("DAYOFYEAR") || this.parseKeywordIf("DAY_OF_YEAR") || this.parseKeywordIf("DOY") || this.parseKeywordIf("DY")) {
                    return DatePart.DAY_OF_YEAR;
                }
                if (this.parseKeywordIf("DAY_OF_WEEK") || this.parseKeywordIf("DAYOFWEEK") || this.parseKeywordIf("DW")) {
                    return DatePart.DAY_OF_WEEK;
                }
                if (this.parseKeywordIf("DAY") || this.parseKeywordIf("DAYS") || this.parseKeywordIf("DD") || this.parseKeywordIf("D")) {
                    return DatePart.DAY;
                }
                if (!this.parseKeywordIf("DECADE") && !this.parseKeywordIf("DECADES")) break;
                return DatePart.DECADE;
            }
            case 'E': {
                if (!this.parseKeywordIf("EPOCH")) break;
                return DatePart.EPOCH;
            }
            case 'H': {
                if (!this.parseKeywordIf("HOUR") && !this.parseKeywordIf("HOURS") && !this.parseKeywordIf("HH")) break;
                return DatePart.HOUR;
            }
            case 'I': {
                if (this.parseKeywordIf("ISODOW") || this.parseKeywordIf("ISO_DAY_OF_WEEK")) {
                    return DatePart.ISO_DAY_OF_WEEK;
                }
            }
            case 'M': {
                if (this.parseKeywordIf("MINUTE") || this.parseKeywordIf("MINUTES") || this.parseKeywordIf("MI")) {
                    return DatePart.MINUTE;
                }
                if (this.parseKeywordIf("MILLENNIUM") || this.parseKeywordIf("MILLENNIUMS") || this.parseKeywordIf("MILLENNIA")) {
                    return DatePart.MILLENNIUM;
                }
                if (this.parseKeywordIf("MICROSECOND") || this.parseKeywordIf("MICROSECONDS") || this.parseKeywordIf("MCS")) {
                    return DatePart.MICROSECOND;
                }
                if (this.parseKeywordIf("MILLISECOND") || this.parseKeywordIf("MILLISECONDS") || this.parseKeywordIf("MS")) {
                    return DatePart.MILLISECOND;
                }
                if (!this.parseKeywordIf("MONTH") && !this.parseKeywordIf("MONTHS") && !this.parseKeywordIf("MM") && !this.parseKeywordIf("M")) break;
                return DatePart.MONTH;
            }
            case 'N': {
                if (this.parseKeywordIf("N")) {
                    return DatePart.MINUTE;
                }
                if (!this.parseKeywordIf("NANOSECOND") && !this.parseKeywordIf("NANOSECONDS") && !this.parseKeywordIf("NS")) break;
                return DatePart.NANOSECOND;
            }
            case 'Q': {
                if (!this.parseKeywordIf("QUARTER") && !this.parseKeywordIf("QUARTERS") && !this.parseKeywordIf("QQ") && !this.parseKeywordIf("Q")) break;
                return DatePart.QUARTER;
            }
            case 'S': {
                if (!this.parseKeywordIf("SECOND") && !this.parseKeywordIf("SECONDS") && !this.parseKeywordIf("SS") && !this.parseKeywordIf("S")) break;
                return DatePart.SECOND;
            }
            case 'T': {
                if (this.parseKeywordIf("TIMEZONE")) {
                    return DatePart.TIMEZONE;
                }
                if (this.parseKeywordIf("TIMEZONE_HOUR")) {
                    return DatePart.TIMEZONE_HOUR;
                }
                if (!this.parseKeywordIf("TIMEZONE_MINUTE")) break;
                return DatePart.TIMEZONE_MINUTE;
            }
            case 'W': {
                if (this.parseKeywordIf("WEEK") || this.parseKeywordIf("WEEKS") || this.parseKeywordIf("WEEK_OF_YEAR") || this.parseKeywordIf("WK") || this.parseKeywordIf("WW")) {
                    return DatePart.WEEK;
                }
                if (!this.parseKeywordIf("WEEKDAY") && !this.parseKeywordIf("W")) break;
                return DatePart.DAY_OF_WEEK;
            }
            case 'Y': {
                if (this.parseKeywordIf("YEAR") || this.parseKeywordIf("YEARS") || this.parseKeywordIf("YYYY") || this.parseKeywordIf("YY")) {
                    return DatePart.YEAR;
                }
                if (!this.parseKeywordIf("Y")) break;
                return DatePart.DAY_OF_YEAR;
            }
        }
        return null;
    }

    private final DatePart parseIntervalDatePart() {
        char character = this.characterUpper();
        switch (character) {
            case 'D': {
                if (!this.parseKeywordIf("DAY") && !this.parseKeywordIf("DAYS")) break;
                return DatePart.DAY;
            }
            case 'H': {
                if (!this.parseKeywordIf("HOUR") && !this.parseKeywordIf("HOURS")) break;
                return DatePart.HOUR;
            }
            case 'M': {
                if (this.parseKeywordIf("MINUTE") || this.parseKeywordIf("MINUTES")) {
                    return DatePart.MINUTE;
                }
                if (this.parseKeywordIf("MICROSECOND") || this.parseKeywordIf("MICROSECONDS")) {
                    return DatePart.MICROSECOND;
                }
                if (this.parseKeywordIf("MILLISECOND") || this.parseKeywordIf("MILLISECONDS")) {
                    return DatePart.MILLISECOND;
                }
                if (!this.parseKeywordIf("MONTH") && !this.parseKeywordIf("MONTHS")) break;
                return DatePart.MONTH;
            }
            case 'N': {
                if (!this.parseKeywordIf("NANOSECOND") && !this.parseKeywordIf("NANOSECONDS")) break;
                return DatePart.NANOSECOND;
            }
            case 'Q': {
                if (!this.parseKeywordIf("QUARTER") && !this.parseKeywordIf("QUARTERS")) break;
                return DatePart.QUARTER;
            }
            case 'S': {
                if (!this.parseKeywordIf("SECOND") && !this.parseKeywordIf("SECONDS")) break;
                return DatePart.SECOND;
            }
            case 'W': {
                if (!this.parseKeywordIf("WEEK") && !this.parseKeywordIf("WEEKS")) break;
                return DatePart.WEEK;
            }
            case 'Y': {
                if (!this.parseKeywordIf("YEAR") && !this.parseKeywordIf("YEARS")) break;
                return DatePart.YEAR;
            }
        }
        throw this.expected("Interval DatePart");
    }

    private final Field<?> parseFieldConcatIf() {
        if (this.parseFunctionNameIf("CONCAT")) {
            this.parse('(');
            Field<String> result = DSL.concat(this.parseList(',', (? super ParseContext c) -> c.parseField()).toArray(Tools.EMPTY_FIELD));
            this.parse(')');
            return result;
        }
        return null;
    }

    private final Field<?> parseFieldOverlayIf() {
        if (this.parseFunctionNameIf("OVERLAY")) {
            this.parse('(');
            Field<String> f1 = this.parseField();
            this.parseKeyword("PLACING");
            Field<String> f2 = this.parseField();
            this.parseKeyword("FROM");
            Field<?> f3 = this.parseField();
            Field<?> f4 = this.parseKeywordIf("FOR") ? this.parseField() : (this.settings().getParseDialect() == SQLDialect.DB2 ? DSL.inline(1) : null);
            this.parse(')');
            return f4 == null ? DSL.overlay(f1, f2, f3) : DSL.overlay(f1, f2, f3, f4);
        }
        return null;
    }

    private final Field<?> parseFieldPositionIf() {
        if (this.parseFunctionNameIf("POSITION")) {
            this.parse('(');
            this.forbidden.add(FunctionKeyword.FK_IN);
            Field<String> f1 = this.parseField();
            this.parseKeyword("IN");
            this.forbidden.remove((Object)FunctionKeyword.FK_IN);
            Field<String> f2 = this.parseField();
            this.parse(')');
            return DSL.position(f2, f1);
        }
        return null;
    }

    private final Field<?> parseFieldLocateIf() {
        boolean locate = this.parseFunctionNameIf("LOCATE");
        if (locate || this.parseFunctionNameIf("LOCATE_IN_STRING")) {
            this.parse('(');
            Field<String> f1 = this.parseField();
            this.parse(',');
            Field<String> f2 = this.parseField();
            Field<?> f3 = this.parseIf(',') ? this.parseField() : null;
            this.parse(')');
            if (locate) {
                switch (this.parseFamily()) {
                    case HANA: 
                    case INGRES: 
                    case SYBASE: {
                        return f3 == null ? DSL.position(f1, f2) : DSL.position(f1, f2, f3);
                    }
                }
            }
            if (locate) {
                return f3 == null ? DSL.position(f2, f1) : DSL.position(f2, f1, f3);
            }
            return f3 == null ? DSL.position(f1, f2) : DSL.position(f1, f2, f3);
        }
        return null;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private final Field<?> parseFieldRegexpReplaceIf() {
        boolean ifx;
        boolean all = this.parseFunctionNameIf("REGEXP_REPLACE_ALL");
        boolean first = !all && this.parseFunctionNameIf("REGEXP_REPLACE_FIRST");
        boolean bl = ifx = !all && !first && this.parseFunctionNameIf("REGEX_REPLACE");
        if (all || first || ifx || this.parseFunctionNameIf("REGEXP_REPLACE")) {
            Field<String> replacement;
            this.parse('(');
            Field<String> field2 = this.parseField();
            this.parse(',');
            Field<String> pattern = this.parseField();
            Field<String> field3 = replacement = this.parseIf(',') ? this.parseField() : null;
            if (replacement == null) {
                replacement = DSL.inline("");
            } else if (ifx) {
                if (this.parseIf(',')) {
                    if (1L != this.parseUnsignedIntegerLiteral()) throw this.expected("Only a limit of 1 is currently supported");
                    first = true;
                }
            } else if (!all && !first) {
                if (this.parseIf(',')) {
                    String s2 = this.parseStringLiteralIf();
                    if (s2 != null) {
                        if (s2.contains("g")) {
                            all = true;
                        }
                    } else {
                        Long i1 = this.parseUnsignedIntegerLiteral();
                        this.parse(',');
                        Long i2 = this.parseUnsignedIntegerLiteral();
                        if (!Long.valueOf(1L).equals(i1) || !Long.valueOf(1L).equals(i2)) throw this.expected("Only start and occurence values of 1 are currently supported");
                        all = true;
                    }
                }
                if (!all) {
                    switch (this.parseFamily()) {
                        case AURORA_POSTGRES: 
                        case COCKROACHDB: 
                        case POSTGRES: 
                        case YUGABYTEDB: 
                        case MEMSQL: {
                            first = true;
                        }
                    }
                }
            }
            this.parse(')');
            return first ? DSL.regexpReplaceFirst(field2, pattern, replacement) : DSL.regexpReplaceAll(field2, pattern, replacement);
        }
        if (!this.parseFunctionNameIf("REPLACE_REGEXPR")) return null;
        this.parse('(');
        this.forbidden.add(FunctionKeyword.FK_IN);
        Field<String> pattern = this.parseField();
        this.parseKeyword("IN");
        this.forbidden.remove((Object)FunctionKeyword.FK_IN);
        Field<String> field4 = this.parseField();
        Field<String> replacement = this.parseKeywordIf("WITH") ? this.parseField() : DSL.inline("");
        first = this.parseKeywordIf("OCCURRENCE") && !this.parseKeywordIf("ALL") && this.parse("1");
        this.parse(')');
        return first ? DSL.regexpReplaceFirst(field4, pattern, replacement) : DSL.regexpReplaceAll(field4, pattern, replacement);
    }

    private final Field<?> parseFieldSubstringIf() {
        boolean substr;
        boolean substring = this.parseFunctionNameIf("SUBSTRING");
        boolean bl = substr = !substring && this.parseFunctionNameIf("SUBSTR");
        if (substring || substr) {
            boolean keywords = !substr;
            this.parse('(');
            Field<String> f1 = this.parseField();
            if (substr || !(keywords = this.parseKeywordIf("FROM"))) {
                this.parse(',');
            }
            Field<?> f2 = this.toField(this.parseNumericOp());
            Field<?> f3 = keywords && this.parseKeywordIf("FOR") || !keywords && this.parseIf(',') ? this.toField(this.parseNumericOp()) : null;
            this.parse(')');
            return f3 == null ? DSL.substring(f1, f2) : DSL.substring(f1, f2, f3);
        }
        return null;
    }

    private final Field<?> parseFieldTrimIf() {
        if (this.parseFunctionNameIf("TRIM")) {
            boolean both;
            this.parse('(');
            int p = this.position();
            boolean leading = this.parseKeywordIf("LEADING", "L");
            boolean trailing = !leading && this.parseKeywordIf("TRAILING", "T");
            boolean bl = both = !leading && !trailing && this.parseKeywordIf("BOTH", "B");
            if (leading || trailing || both) {
                if (this.parseIf(',') || this.parseIf(')')) {
                    this.position(p);
                } else if (this.parseKeywordIf("FROM")) {
                    Field<String> f = this.parseField();
                    this.parse(')');
                    return leading ? DSL.ltrim(f) : (trailing ? DSL.rtrim(f) : DSL.trim(f));
                }
            }
            if (this.parseKeywordIf("FROM")) {
                if (this.parseIf(',') || this.parseIf(')')) {
                    this.position(p);
                } else {
                    Field<String> f = this.parseField();
                    this.parse(')');
                    return DSL.trim(f);
                }
            }
            Field<String> f1 = this.parseField();
            if (this.parseKeywordIf("FROM")) {
                Field<String> f2 = this.parseField();
                this.parse(')');
                return leading ? DSL.ltrim(f2, f1) : (trailing ? DSL.rtrim(f2, f1) : DSL.trim(f2, f1));
            }
            Field<String> f2 = this.parseIf(',') ? this.parseField() : null;
            this.parse(')');
            return f2 == null ? DSL.trim(f1) : DSL.trim(f1, f2);
        }
        return null;
    }

    private final Field<?> parseFieldTranslateIf() {
        if (this.parseFunctionNameIf("TRANSLATE", "OTRANSLATE")) {
            if (this.parseFamily() == SQLDialect.DB2) {
                return this.parseFunctionArgs3((f1, f2, f3) -> DSL.translate((Field<String>)f1, (Field<String>)f3, (Field<String>)f2));
            }
            return this.parseFunctionArgs3(DSL::translate);
        }
        return null;
    }

    private final Field<?> parseFieldDecodeIf() {
        if (this.parseFunctionNameIf("DECODE", "DECODE_ORACLE", "MAP")) {
            this.parse('(');
            List<Field> fields = this.parseList(',', (? super ParseContext c) -> c.parseField());
            int size = fields.size();
            if (size < 3) {
                throw this.expected("At least three arguments to DECODE()");
            }
            this.parse(')');
            return DSL.decode(fields.get(0), fields.get(1), fields.get(2), size == 3 ? Tools.EMPTY_FIELD : fields.subList(3, size).toArray(Tools.EMPTY_FIELD));
        }
        return null;
    }

    private final Field<?> parseFieldChooseIf() {
        if (this.parseFunctionNameIf("CHOOSE", "ELT")) {
            this.parse('(');
            Field<Integer> index = this.parseField();
            this.parse(',');
            List<Field> fields = this.parseList(',', (? super ParseContext c) -> c.parseField());
            this.parse(')');
            return DSL.choose(index, fields.toArray(Tools.EMPTY_FIELD));
        }
        return null;
    }

    private final Field<?> parseFieldIfIf() {
        if (this.parseFunctionNameIf("IF", "IIF")) {
            this.parse('(');
            Condition c = this.parseCondition();
            this.parse(',');
            Field<?> f1 = this.parseField();
            this.parse(',');
            Field<?> f2 = this.parseField();
            this.parse(')');
            return DSL.iif(c, f1, f2);
        }
        return null;
    }

    private final Field<?> parseFieldCoalesceIf() {
        if (this.parseFunctionNameIf("COALESCE")) {
            this.parse('(');
            List<Field> fields = this.parseList(',', (? super ParseContext c) -> c.parseField());
            this.parse(')');
            Field<?>[] a = Tools.EMPTY_FIELD;
            return DSL.coalesce(fields.get(0), fields.size() == 1 ? a : fields.subList(1, fields.size()).toArray(a));
        }
        return null;
    }

    private final <T> Field<?> parseFieldFieldIf() {
        if (this.parseFunctionNameIf("FIELD")) {
            this.parse('(');
            Field<?> f1 = this.parseField();
            this.parse(',');
            List<Field> f2 = this.parseList(',', (? super ParseContext c) -> c.parseField());
            this.parse(')');
            return DSL.field(f1, f2.toArray(Tools.EMPTY_FIELD));
        }
        return null;
    }

    private final Field<?> parseFieldCaseIf() {
        if (this.parseKeywordIf("CASE")) {
            if (this.parseKeywordIf("WHEN")) {
                CaseConditionStep<?> step = null;
                do {
                    Condition condition = this.parseCondition();
                    this.parseKeyword("THEN");
                    Field<?> value = this.parseField();
                    CaseConditionStep<?> caseConditionStep = step = step == null ? DSL.when(condition, value) : step.when(condition, value);
                } while (this.parseKeywordIf("WHEN"));
                Field<?> result = this.parseKeywordIf("ELSE") ? step.otherwise(this.parseField()) : step;
                this.parseKeyword("END");
                return result;
            }
            CaseValueStep<?> init2 = DSL.choose(this.parseField());
            CaseWhenStep<?, ?> step = null;
            this.parseKeyword("WHEN");
            do {
                Field<?> when = this.parseField();
                this.parseKeyword("THEN");
                Field<?> then = this.parseField();
                CaseWhenStep<?, ?> caseWhenStep = step = step == null ? init2.when(when, then) : step.when(when, then);
            } while (this.parseKeywordIf("WHEN"));
            Field<Object> result = this.parseKeywordIf("ELSE") ? step.otherwise(this.parseField()) : step;
            this.parseKeyword("END");
            return result;
        }
        return null;
    }

    private final Field<?> parseFieldCastIf() {
        boolean coerce;
        boolean cast = this.parseFunctionNameIf("CAST");
        boolean bl = coerce = !cast && this.parseFunctionNameIf("COERCE");
        if (cast || coerce) {
            this.parse('(');
            Field<?> field2 = this.parseField();
            this.parseKeyword("AS");
            DataType<?> type = this.parseCastDataType();
            this.parse(')');
            return cast ? DSL.cast(field2, type) : DSL.coerce(field2, type);
        }
        return null;
    }

    private final Field<?> parseFieldConvertIf() {
        if (this.parseFunctionNameIf("CONVERT")) {
            this.parse('(');
            DataType<?> type = this.parseDataType();
            this.parse(',');
            Field<?> field2 = this.parseField();
            Long style = null;
            if (!this.ignoreProEdition() && this.parseIf(',') && this.requireProEdition()) {
                style = this.parseUnsignedIntegerLiteral();
            }
            this.parse(')');
            if (style == null) {
                return DSL.cast(field2, type);
            }
            return DSL.convert(type, field2, style.intValue());
        }
        return null;
    }

    private final Field<Boolean> parseBooleanValueExpressionIf() {
        TruthValue truth = this.parseTruthValueIf();
        if (truth != null) {
            switch (truth) {
                case T_TRUE: {
                    return DSL.inline(true);
                }
                case T_FALSE: {
                    return DSL.inline(false);
                }
                case T_NULL: {
                    return DSL.inline((Boolean)null);
                }
            }
            throw this.exception("Truth value not supported: " + String.valueOf((Object)truth));
        }
        return null;
    }

    private final Field<?> parseAggregateFunctionIf() {
        return this.parseAggregateFunctionIf(false);
    }

    private final Field<?> parseAggregateFunctionIf(boolean basic) {
        return this.parseAggregateFunctionIf(basic, null);
    }

    private final Field<?> parseAggregateFunctionIf(boolean basic, AggregateFunction<?> f) {
        AggregateFunction<?> agg = null;
        AggregateFilterStep<?> filter2 = null;
        WindowBeforeOverStep<Object> over = null;
        AggregateFunction<?> keep = null;
        Field<Object> result = null;
        Condition condition = null;
        agg = f != null ? f : this.parseCountIf();
        filter2 = agg;
        over = agg;
        keep = agg;
        if (filter2 == null) {
            Field<?> field2 = this.parseGeneralSetFunctionIf();
            if (field2 != null && !(field2 instanceof AggregateFunction)) {
                return field2;
            }
            agg = (AggregateFunction<?>)field2;
            filter2 = agg;
            over = agg;
            keep = agg;
        }
        if (filter2 == null && !basic) {
            agg = this.parseBinarySetFunctionIf();
            filter2 = agg;
            over = agg;
        }
        if (filter2 == null && !basic) {
            filter2 = this.parseOrderedSetFunctionIf();
            over = filter2;
        }
        if (filter2 == null && !basic) {
            filter2 = this.parseArrayAggFunctionIf();
            over = filter2;
        }
        if (filter2 == null && !basic) {
            filter2 = this.parseMultisetAggFunctionIf();
            over = filter2;
        }
        if (filter2 == null && !basic) {
            filter2 = this.parseXMLAggFunctionIf();
            over = filter2;
        }
        if (filter2 == null && !basic) {
            filter2 = this.parseJSONArrayAggFunctionIf();
            over = filter2;
        }
        if (filter2 == null && !basic) {
            filter2 = this.parseJSONObjectAggFunctionIf();
            over = filter2;
        }
        if (filter2 == null) {
            over = this.parseCountIfIf();
        }
        if (filter2 == null && over == null) {
            if (!basic) {
                return this.parseSpecialAggregateFunctionIf();
            }
            return null;
        }
        if (keep != null && filter2 != null && !basic && !this.ignoreProEdition() && this.parseKeywordIf("KEEP")) {
            this.requireProEdition();
            this.parse('(');
            this.parseKeyword("DENSE_RANK");
            boolean first = this.parseKeywordIf("FIRST");
            if (!first) {
                this.parseKeyword("LAST");
            }
            this.parseKeyword("ORDER BY");
            List<SortField> sort = this.parseList(',', (? super ParseContext c) -> c.parseSortField());
            this.parse(')');
            if (first) {
                over = agg.keepDenseRankFirstOrderBy(sort);
                result = over;
            } else {
                over = agg.keepDenseRankLastOrderBy(sort);
                result = over;
            }
        } else if (filter2 != null && !basic && this.parseKeywordIf("FILTER")) {
            this.parse('(');
            this.parseKeyword("WHERE");
            condition = this.parseCondition();
            this.parse(')');
            over = filter2.filterWhere(condition);
            result = over;
        } else {
            result = filter2 != null ? filter2 : over;
        }
        if (!basic && this.parseKeywordIf("OVER")) {
            Object nameOrSpecification = this.parseWindowNameOrSpecification(filter2 != null);
            if (nameOrSpecification instanceof Name) {
                Name n = (Name)nameOrSpecification;
                result = over.over(n);
            } else if (nameOrSpecification instanceof WindowSpecification) {
                WindowSpecification w = (WindowSpecification)nameOrSpecification;
                result = over.over(w);
            } else {
                result = over.over();
            }
        }
        return result;
    }

    private final Field<?> parseSpecialAggregateFunctionIf() {
        if (this.parseFunctionNameIf("GROUP_CONCAT")) {
            GroupConcatOrderByStep s1;
            this.parse('(');
            if (this.parseKeywordIf("DISTINCT")) {
                s1 = DSL.groupConcatDistinct(this.parseField());
            } else {
                if (!this.parseKeywordIf("ALL")) {
                    // empty if block
                }
                s1 = DSL.groupConcat(this.parseField());
            }
            GroupConcatSeparatorStep s2 = this.parseKeywordIf("ORDER BY") ? s1.orderBy(this.parseList(',', (? super ParseContext c) -> c.parseSortField())) : s1;
            AggregateFunction<String> s3 = this.parseKeywordIf("SEPARATOR") ? s2.separator(this.parseStringLiteral()) : s2;
            this.parse(')');
            return s3;
        }
        return null;
    }

    private final Object parseWindowNameOrSpecification(boolean orderByAllowed) {
        QueryPart result;
        if (this.parseIf('(')) {
            result = this.parseWindowSpecificationIf(null, orderByAllowed);
            this.parse(')');
        } else {
            result = this.parseIdentifier();
        }
        return result;
    }

    private final Field<?> parseFieldRankIf() {
        if (this.parseFunctionNameIf("RANK")) {
            this.parse('(');
            if (this.parseIf(')')) {
                return this.parseWindowFunction(null, null, DSL.rank());
            }
            this.parseKeywordIf("ALL");
            List<Field> args2 = this.parseList(',', (? super ParseContext c) -> c.parseField());
            this.parse(')');
            return DSL.rank(args2).withinGroupOrderBy(this.parseWithinGroupN());
        }
        return null;
    }

    private final Field<?> parseFieldDenseRankIf() {
        if (this.parseFunctionNameIf("DENSE_RANK", "DENSERANK")) {
            this.parse('(');
            if (this.parseIf(')')) {
                return this.parseWindowFunction(null, null, DSL.denseRank());
            }
            this.parseKeywordIf("ALL");
            List<Field> args2 = this.parseList(',', (? super ParseContext c) -> c.parseField());
            this.parse(')');
            return DSL.denseRank(args2).withinGroupOrderBy(this.parseWithinGroupN());
        }
        return null;
    }

    private final Field<?> parseFieldPercentRankIf() {
        if (this.parseFunctionNameIf("PERCENT_RANK")) {
            this.parse('(');
            if (this.parseIf(')')) {
                return this.parseWindowFunction(null, null, DSL.percentRank());
            }
            this.parseKeywordIf("ALL");
            List<Field> args2 = this.parseList(',', (? super ParseContext c) -> c.parseField());
            this.parse(')');
            return DSL.percentRank(args2).withinGroupOrderBy(this.parseWithinGroupN());
        }
        return null;
    }

    private final Field<?> parseFieldCumeDistIf() {
        if (this.parseFunctionNameIf("CUME_DIST")) {
            this.parse('(');
            if (this.parseIf(')')) {
                return this.parseWindowFunction(null, null, DSL.cumeDist());
            }
            this.parseKeywordIf("ALL");
            List<Field> args2 = this.parseList(',', (? super ParseContext c) -> c.parseField());
            this.parse(')');
            return DSL.cumeDist(args2).withinGroupOrderBy(this.parseWithinGroupN());
        }
        return null;
    }

    private final Field<?> parseFieldNtileIf() {
        if (this.parseFunctionNameIf("NTILE")) {
            this.parse('(');
            int number = Tools.asInt(this.parseUnsignedIntegerLiteral());
            this.parse(')');
            return this.parseWindowFunction(null, null, DSL.ntile(number));
        }
        return null;
    }

    private final Field<?> parseFieldLeadLagIf() {
        boolean lag;
        boolean lead = this.parseFunctionNameIf("LEAD");
        boolean bl = lag = !lead && this.parseFunctionNameIf("LAG");
        if (lead || lag) {
            this.parse('(');
            Field<?> f1 = this.parseField();
            Integer f2 = null;
            Field<?> f3 = null;
            if (this.parseIf(',')) {
                f2 = Tools.asInt(this.parseUnsignedIntegerLiteral());
                if (this.parseIf(',')) {
                    f3 = this.parseField();
                }
            }
            WindowIgnoreNullsStep<?> s1 = lead ? (f2 == null ? DSL.lead(f1) : (f3 == null ? DSL.lead(f1, f2) : DSL.lead(f1, (int)f2, f3))) : (f2 == null ? DSL.lag(f1) : (f3 == null ? DSL.lag(f1, f2) : DSL.lag(f1, (int)f2, f3)));
            WindowOverStep<?> s2 = this.parseWindowRespectIgnoreNulls(s1, s1);
            this.parse(')');
            return this.parseWindowFunction(null, s1, s2);
        }
        return null;
    }

    private final Field<?> parseFieldFirstValueIf() {
        if (this.parseFunctionNameIf("FIRST_VALUE")) {
            this.parse('(');
            Field<?> arg = this.parseField();
            WindowIgnoreNullsStep<?> s1 = DSL.firstValue(arg);
            WindowOverStep<?> s2 = this.parseWindowRespectIgnoreNulls(s1, s1);
            this.parse(')');
            return this.parseWindowFunction(null, s1, s2);
        }
        return null;
    }

    private final Field<?> parseFieldLastValueIf() {
        if (this.parseFunctionNameIf("LAST_VALUE")) {
            this.parse('(');
            Field<?> arg = this.parseField();
            WindowIgnoreNullsStep<?> s1 = DSL.lastValue(arg);
            WindowOverStep<?> s2 = this.parseWindowRespectIgnoreNulls(s1, s1);
            this.parse(')');
            return this.parseWindowFunction(null, s1, s2);
        }
        return null;
    }

    private final Field<?> parseFieldNthValueIf() {
        if (this.parseFunctionNameIf("NTH_VALUE")) {
            this.parse('(');
            Field<?> f1 = this.parseField();
            this.parse(',');
            Field<Integer> f2 = this.parseField();
            WindowFromFirstLastStep<?> s1 = DSL.nthValue(f1, f2);
            WindowIgnoreNullsStep s2 = this.parseWindowFromFirstLast(s1, s1);
            WindowOverStep<?> s3 = this.parseWindowRespectIgnoreNulls(s2, s2);
            this.parse(')');
            return this.parseWindowFunction(s1, s2, s3);
        }
        return null;
    }

    private final Field<?> parseWindowFunction(WindowFromFirstLastStep s1, WindowIgnoreNullsStep s2, WindowOverStep<?> s3) {
        WindowFinalStep<?> windowFinalStep;
        s2 = this.parseWindowFromFirstLast(s1, s2);
        s3 = this.parseWindowRespectIgnoreNulls(s2, s3);
        this.parseKeyword("OVER");
        Object nameOrSpecification = this.parseWindowNameOrSpecification(true);
        if (nameOrSpecification instanceof Name) {
            Name n = (Name)nameOrSpecification;
            windowFinalStep = s3.over(n);
        } else if (nameOrSpecification instanceof WindowSpecification) {
            WindowSpecification w = (WindowSpecification)nameOrSpecification;
            windowFinalStep = s3.over(w);
        } else {
            windowFinalStep = s3.over();
        }
        return windowFinalStep;
    }

    private final WindowOverStep<?> parseWindowRespectIgnoreNulls(WindowIgnoreNullsStep s2, WindowOverStep<?> s3) {
        if (s2 != null) {
            s3 = this.parseKeywordIf("RESPECT NULLS") ? s2.respectNulls() : (this.parseKeywordIf("IGNORE NULLS") ? s2.ignoreNulls() : s2);
        }
        return s3;
    }

    private final WindowIgnoreNullsStep parseWindowFromFirstLast(WindowFromFirstLastStep s1, WindowIgnoreNullsStep s2) {
        if (s1 != null) {
            s2 = this.parseKeywordIf("FROM FIRST") ? s1.fromFirst() : (this.parseKeywordIf("FROM LAST") ? s1.fromLast() : s1);
        }
        return s2;
    }

    private final AggregateFunction<?> parseBinarySetFunctionIf() {
        switch (this.characterUpper()) {
            case 'C': {
                if (this.parseFunctionNameIf("CORR")) {
                    return this.parseBinarySetFunction(DSL::corr);
                }
                if (this.parseFunctionNameIf("COVAR_POP")) {
                    return this.parseBinarySetFunction(DSL::covarPop);
                }
                if (!this.parseFunctionNameIf("COVAR_SAMP")) break;
                return this.parseBinarySetFunction(DSL::covarSamp);
            }
            case 'R': {
                if (this.parseFunctionNameIf("REGR_AVGX")) {
                    return this.parseBinarySetFunction(DSL::regrAvgX);
                }
                if (this.parseFunctionNameIf("REGR_AVGY")) {
                    return this.parseBinarySetFunction(DSL::regrAvgY);
                }
                if (this.parseFunctionNameIf("REGR_COUNT")) {
                    return this.parseBinarySetFunction(DSL::regrCount);
                }
                if (this.parseFunctionNameIf("REGR_INTERCEPT")) {
                    return this.parseBinarySetFunction(DSL::regrIntercept);
                }
                if (this.parseFunctionNameIf("REGR_R2")) {
                    return this.parseBinarySetFunction(DSL::regrR2);
                }
                if (this.parseFunctionNameIf("REGR_SLOPE")) {
                    return this.parseBinarySetFunction(DSL::regrSlope);
                }
                if (this.parseFunctionNameIf("REGR_SXX")) {
                    return this.parseBinarySetFunction(DSL::regrSXX);
                }
                if (this.parseFunctionNameIf("REGR_SXY")) {
                    return this.parseBinarySetFunction(DSL::regrSXY);
                }
                if (!this.parseFunctionNameIf("REGR_SYY")) break;
                return this.parseBinarySetFunction(DSL::regrSYY);
            }
        }
        return null;
    }

    private final AggregateFunction<?> parseBinarySetFunction(BiFunction<? super Field<? extends Number>, ? super Field<? extends Number>, ? extends AggregateFunction<?>> function) {
        this.parse('(');
        this.parseKeywordIf("ALL");
        Field<?> arg1 = this.parseField();
        this.parse(',');
        Field<?> arg2 = this.parseField();
        this.parse(')');
        return function.apply(arg1, arg2);
    }

    private final AggregateFilterStep<?> parseOrderedSetFunctionIf() {
        boolean optionalWithinGroup = false;
        OrderedAggregateFunction<Object> orderedN = this.parseHypotheticalSetFunctionIf();
        if (orderedN == null) {
            orderedN = this.parseInverseDistributionFunctionIf();
        }
        if (orderedN == null) {
            orderedN = this.parseListaggFunctionIf();
            boolean bl = optionalWithinGroup = orderedN != null;
        }
        if (orderedN != null) {
            return orderedN.withinGroupOrderBy(this.parseWithinGroupN(optionalWithinGroup));
        }
        OrderedAggregateFunctionOfDeferredType ordered1 = this.parseModeIf();
        if (ordered1 != null) {
            return ordered1.withinGroupOrderBy(this.parseWithinGroup1());
        }
        return null;
    }

    private final AggregateFilterStep<?> parseArrayAggFunctionIf() {
        if (this.parseKeywordIf("ARRAY_AGG")) {
            this.parse('(');
            boolean distinct2 = this.parseSetQuantifier();
            Field<?> a1 = this.parseField();
            List<SortField> sort = null;
            if (this.parseKeywordIf("ORDER BY")) {
                sort = this.parseList(',', (? super ParseContext c) -> c.parseSortField());
            }
            this.parse(')');
            ArrayAggOrderByStep<?[]> s1 = distinct2 ? DSL.arrayAggDistinct(a1) : DSL.arrayAgg(a1);
            return sort == null ? s1 : s1.orderBy(sort);
        }
        return null;
    }

    private final AggregateFilterStep<?> parseMultisetAggFunctionIf() {
        if (this.parseKeywordIf("MULTISET_AGG")) {
            this.parse('(');
            this.parseKeywordIf("ALL");
            List<Field> fields = this.parseList(',', (? super ParseContext c) -> c.parseField());
            List<SortField> sort = null;
            if (this.parseKeywordIf("ORDER BY")) {
                sort = this.parseList(',', (? super ParseContext c) -> c.parseSortField());
            }
            this.parse(')');
            ArrayAggOrderByStep<Result<Record>> s1 = DSL.multisetAgg(fields);
            return sort == null ? s1 : s1.orderBy(sort);
        }
        return null;
    }

    private final List<SortField<?>> parseWithinGroupN() {
        return this.parseWithinGroupN(false);
    }

    private final List<SortField<?>> parseWithinGroupN(boolean optional) {
        if (optional) {
            if (!this.parseKeywordIf("WITHIN GROUP")) {
                return Collections.emptyList();
            }
        } else {
            this.parseKeyword("WITHIN GROUP");
        }
        this.parse('(');
        this.parseKeyword("ORDER BY");
        List<SortField<?>> result = this.parseList(',', (? super ParseContext c) -> c.parseSortField());
        this.parse(')');
        return result;
    }

    private final SortField<?> parseWithinGroup1() {
        this.parseKeyword("WITHIN GROUP");
        this.parse('(');
        this.parseKeyword("ORDER BY");
        SortField<?> result = this.parseSortField();
        this.parse(')');
        return result;
    }

    private final OrderedAggregateFunction<?> parseHypotheticalSetFunctionIf() {
        OrderedAggregateFunction<Number> ordered;
        if (this.parseFunctionNameIf("RANK")) {
            this.parse('(');
            ordered = DSL.rank(this.parseList(',', (? super ParseContext c) -> c.parseField()));
            this.parse(')');
        } else if (this.parseFunctionNameIf("DENSE_RANK")) {
            this.parse('(');
            ordered = DSL.denseRank(this.parseList(',', (? super ParseContext c) -> c.parseField()));
            this.parse(')');
        } else if (this.parseFunctionNameIf("PERCENT_RANK")) {
            this.parse('(');
            ordered = DSL.percentRank(this.parseList(',', (? super ParseContext c) -> c.parseField()));
            this.parse(')');
        } else if (this.parseFunctionNameIf("CUME_DIST")) {
            this.parse('(');
            ordered = DSL.cumeDist(this.parseList(',', (? super ParseContext c) -> c.parseField()));
            this.parse(')');
        } else {
            ordered = null;
        }
        return ordered;
    }

    private final OrderedAggregateFunction<BigDecimal> parseInverseDistributionFunctionIf() {
        OrderedAggregateFunction<BigDecimal> ordered;
        if (this.parseFunctionNameIf("PERCENTILE_CONT")) {
            this.parse('(');
            this.parseKeywordIf("ALL");
            ordered = DSL.percentileCont(this.parseField());
            this.parse(')');
        } else if (this.parseFunctionNameIf("PERCENTILE_DISC")) {
            this.parse('(');
            this.parseKeywordIf("ALL");
            ordered = DSL.percentileDisc(this.parseField());
            this.parse(')');
        } else {
            ordered = null;
        }
        return ordered;
    }

    private final OrderedAggregateFunction<String> parseListaggFunctionIf() {
        OrderedAggregateFunction<String> ordered;
        if (this.parseFunctionNameIf("LISTAGG")) {
            this.parse('(');
            boolean distinct2 = this.parseSetQuantifier();
            Field<?> field2 = this.parseField();
            ordered = this.parseIf(',') ? (distinct2 ? DSL.listAggDistinct(field2, this.parseStringLiteral()) : DSL.listAgg(field2, this.parseStringLiteral())) : (distinct2 ? DSL.listAggDistinct(field2) : DSL.listAgg(field2));
            this.parse(')');
        } else {
            ordered = null;
        }
        return ordered;
    }

    private final OrderedAggregateFunctionOfDeferredType parseModeIf() {
        OrderedAggregateFunctionOfDeferredType ordered;
        if (this.parseFunctionNameIf("MODE")) {
            this.parse('(');
            this.parse(')');
            ordered = DSL.mode();
        } else {
            ordered = null;
        }
        return ordered;
    }

    private final Field<?> parseGeneralSetFunctionIf() {
        ComputationalOperation operation = this.parseComputationalOperationIf();
        if (operation == null) {
            return null;
        }
        this.parse('(');
        boolean distinct2 = switch (operation) {
            case ComputationalOperation.ANY, ComputationalOperation.ANY_VALUE, ComputationalOperation.AVG, ComputationalOperation.EVERY, ComputationalOperation.MAX, ComputationalOperation.MIN, ComputationalOperation.SUM, ComputationalOperation.PRODUCT -> this.parseSetQuantifier();
            default -> {
                this.parseKeywordIf("ALL");
                yield false;
            }
        };
        Field<Boolean> arg = this.parseField();
        switch (operation) {
            case MAX: 
            case MIN: {
                if (distinct2 || !this.parseIf(',')) break;
                List<Field> fields = this.parseList(',', (? super ParseContext c) -> c.parseField());
                this.parse(')');
                return operation == ComputationalOperation.MAX ? DSL.greatest(arg, fields.toArray(Tools.EMPTY_FIELD)) : DSL.least(arg, fields.toArray(Tools.EMPTY_FIELD));
            }
        }
        this.parse(')');
        switch (operation) {
            case ANY_VALUE: {
                return DSL.anyValue(arg);
            }
            case AVG: {
                return distinct2 ? DSL.avgDistinct(arg) : DSL.avg(arg);
            }
            case MAX: {
                return distinct2 ? DSL.maxDistinct(arg) : DSL.max(arg);
            }
            case MIN: {
                return distinct2 ? DSL.minDistinct(arg) : DSL.min(arg);
            }
            case SUM: {
                return distinct2 ? DSL.sumDistinct(arg) : DSL.sum(arg);
            }
            case PRODUCT: {
                return distinct2 ? DSL.productDistinct(arg) : DSL.product(arg);
            }
            case MEDIAN: {
                return DSL.median(arg);
            }
            case EVERY: {
                return DSL.every(arg);
            }
            case ANY: {
                return DSL.boolOr(arg);
            }
            case STDDEV_POP: {
                return DSL.stddevPop(arg);
            }
            case STDDEV_SAMP: {
                return DSL.stddevSamp(arg);
            }
            case VAR_POP: {
                return DSL.varPop(arg);
            }
            case VAR_SAMP: {
                return DSL.varSamp(arg);
            }
        }
        throw this.exception("Unsupported computational operation");
    }

    private final AggregateFunction<?> parseCountIf() {
        if (this.parseFunctionNameIf("COUNT")) {
            this.parse('(');
            boolean distinct2 = this.parseSetQuantifier();
            if (this.parseIf('*') && this.parse(')')) {
                if (distinct2) {
                    return DSL.countDistinct(DSL.asterisk());
                }
                return DSL.count();
            }
            Field<?>[] fields = null;
            QualifiedAsterisk asterisk = null;
            Row row = this.parseRowIf();
            if (row != null) {
                fields = row.fields();
            } else {
                asterisk = this.parseQualifiedAsteriskIf();
                if (asterisk == null) {
                    Field[] fieldArray;
                    if (distinct2) {
                        fieldArray = this.parseList(',', (? super ParseContext c) -> c.parseField()).toArray(Tools.EMPTY_FIELD);
                    } else {
                        Field[] fieldArray2 = new Field[1];
                        fieldArray = fieldArray2;
                        fieldArray2[0] = this.parseField();
                    }
                    fields = fieldArray;
                }
            }
            this.parse(')');
            if (distinct2) {
                if (fields == null) {
                    return DSL.countDistinct(asterisk);
                }
                if (fields.length == 1) {
                    return DSL.countDistinct(fields[0]);
                }
                return DSL.countDistinct(fields);
            }
            if (fields == null) {
                return DSL.count(asterisk);
            }
            return DSL.count(fields[0]);
        }
        return null;
    }

    private final WindowBeforeOverStep<Integer> parseCountIfIf() {
        if (this.parseFunctionNameIf("COUNTIF", "COUNT_IF")) {
            this.parse('(');
            Condition condition = this.parseCondition();
            this.parse(')');
            return DSL.count().filterWhere(condition);
        }
        return null;
    }

    private final boolean parseSetQuantifier() {
        boolean distinct2 = this.parseKeywordIf("DISTINCT");
        if (!distinct2) {
            this.parseKeywordIf("ALL");
        }
        return distinct2;
    }

    private final Domain<?> parseDomainName() {
        return DSL.domain(this.parseName());
    }

    private final Catalog parseCatalogName() {
        return DSL.catalog(this.parseName());
    }

    private final Schema parseSchemaName() {
        return DSL.schema(this.parseName());
    }

    private final Table<?> parseTableName() {
        return this.lookupTable(this.position(), this.parseName());
    }

    private final Table<?> parseTableNameIf() {
        int positionBeforeName = this.position();
        Name name = this.parseNameIf();
        if (name == null) {
            return null;
        }
        return this.lookupTable(positionBeforeName, name);
    }

    private final Field<?> parseFieldNameOrSequenceExpression() {
        Variable<?> variable;
        Name name;
        int positionBeforeName;
        block5: {
            List<Object> arguments;
            block8: {
                block6: {
                    block7: {
                        positionBeforeName = this.position();
                        name = this.parseName();
                        if (name.qualified()) {
                            String first = name.first();
                            String last = name.last();
                            if ("NEXTVAL".equalsIgnoreCase(last)) {
                                return DSL.sequence(name.qualifier()).nextval();
                            }
                            if ("CURRVAL".equalsIgnoreCase(last)) {
                                return DSL.sequence(name.qualifier()).currval();
                            }
                            if (Boolean.TRUE.equals(this.data(Tools.BooleanDataKey.DATA_PARSE_ON_CONFLICT)) && "EXCLUDED".equalsIgnoreCase(first)) {
                                return DSL.excluded(DSL.field(name.unqualifiedName()));
                            }
                        }
                        if (this.dsl.settings().getParseUnknownFunctions() != ParseUnknownFunctions.IGNORE || !this.peek('(') || this.peekTokens('(', '+', ')')) break block5;
                        int p = this.position();
                        this.parse('(');
                        if (this.parseIf(')')) break block6;
                        if (this.parseFamily() != SQLDialect.TERADATA || this.parseDataTypeIf(false) == null) break block7;
                        this.parse(')');
                        this.position(p);
                        break block5;
                    }
                    arguments = this.parseList(',', (? super ParseContext c) -> c.parseField());
                    this.parse(')');
                    break block8;
                }
                arguments = new ArrayList();
            }
            return DSL.function(name, Object.class, arguments.toArray(Tools.EMPTY_FIELD));
        }
        if (this.scope.proceduralScope.inScope() && !name.qualified() && (variable = this.scope.proceduralScope.get(name.last())) != null) {
            return variable;
        }
        return this.lookupField(positionBeforeName, name);
    }

    private final TableField<?, ?> parseFieldName() {
        return (TableField)this.lookupField(this.position, this.parseName());
    }

    private final Sequence<?> parseSequenceName() {
        return DSL.sequence(this.parseName());
    }

    private final Name parseIndexName() {
        Name result = this.parseNameIf();
        if (result == null) {
            throw this.expected("Identifier");
        }
        return result;
    }

    private final Name parseIndexNameIf() {
        if (!this.peekKeyword("ON")) {
            return this.parseNameIf();
        }
        return null;
    }

    private final Collation parseCollation() {
        return DSL.collation(this.parseNameOrStringLiteral());
    }

    private final CharacterSet parseCharacterSet() {
        return DSL.characterSet(this.parseNameOrStringLiteral());
    }

    public final Name parseNameOrStringLiteral() {
        Name result = this.parseNameIf();
        if (result == null) {
            return DSL.name(this.parseStringLiteral());
        }
        return result;
    }

    @Override
    public final Name parseName() {
        Name result = this.parseNameIf();
        if (result == null) {
            throw this.expected("Identifier");
        }
        return result;
    }

    @Override
    public final Name parseNameIf() {
        Name identifier = this.parseIdentifierIf();
        if (identifier == null) {
            return null;
        }
        if (this.peek('.') && !this.peek("..")) {
            return this.parseNameQualified('.', identifier);
        }
        if (this.parseFamily() == SQLDialect.ACCESS && this.peek('!')) {
            return this.parseNameQualified('!', identifier);
        }
        return identifier;
    }

    private final Name parseNameQualified(char separator, Name firstPart) {
        ArrayList<Name> result = new ArrayList<Name>();
        result.add(firstPart);
        while (this.parseIf(separator)) {
            result.add(this.parseIdentifier());
        }
        return DSL.name(result.toArray(Tools.EMPTY_NAME));
    }

    private final QualifiedAsterisk parseQualifiedAsteriskIf() {
        int positionBeforeName = this.position();
        Name i1 = this.parseIdentifierIf();
        if (i1 == null) {
            return null;
        }
        if (this.parseIf('.')) {
            ArrayList<Name> result = null;
            do {
                Name i2;
                if ((i2 = this.parseIdentifierIf()) != null) {
                    if (result == null) {
                        result = new ArrayList<Name>();
                        result.add(i1);
                    }
                } else {
                    this.parse('*');
                    return this.lookupQualifiedAsterisk(positionBeforeName, result == null ? i1 : DSL.name(result.toArray(Tools.EMPTY_NAME)));
                }
                result.add(i2);
            } while (this.parseIf('.'));
        }
        this.position(positionBeforeName);
        return null;
    }

    private final List<Name> parseIdentifiers() {
        LinkedHashSet<Name> result = new LinkedHashSet<Name>();
        do {
            if (result.add(this.parseIdentifier())) continue;
            throw this.exception("Duplicate identifier encountered");
        } while (this.parseIf(','));
        return new ArrayList<Name>(result);
    }

    @Override
    public final Name parseIdentifier() {
        return this.parseIdentifier(false, false);
    }

    private final Name parseIdentifier(boolean allowAposQuotes, boolean allowPartAsStart) {
        Name result = this.parseIdentifierIf(allowAposQuotes, allowPartAsStart);
        if (result == null) {
            throw this.expected("Identifier");
        }
        return result;
    }

    @Override
    public final Name parseIdentifierIf() {
        return this.parseIdentifierIf(false, false);
    }

    private final Name parseIdentifierIf(boolean allowAposQuotes, boolean allowPartAsStart) {
        char quoteEnd = this.parseQuote(allowAposQuotes);
        boolean quoted = quoteEnd != '\u0000';
        int start2 = this.position();
        StringBuilder sb = new StringBuilder();
        if (quoted) {
            char c;
            while ((c = this.character()) != quoteEnd && this.hasMore() && this.positionInc() || this.character(this.position + 1) == quoteEnd && this.hasMore(1) && this.positionInc(2)) {
                sb.append(c);
            }
        } else if (allowPartAsStart ? this.isIdentifierPart() : this.isIdentifierStart()) {
            do {
                sb.append(this.character());
                this.positionInc();
            } while (this.isIdentifierPart() && this.hasMore());
        }
        if (this.position() == start2) {
            return null;
        }
        String name = Tools.normaliseNameCase(this.configuration(), sb.toString(), quoted, this.locale);
        if (quoted) {
            if (this.character() != quoteEnd) {
                throw this.exception("Quoted identifier must terminate in " + quoteEnd + ". Start of identifier: " + StringUtils.abbreviate(sb.toString(), 30));
            }
            this.positionInc();
            this.parseWhitespaceIf();
            return DSL.quotedName(name);
        }
        this.parseWhitespaceIf();
        return DSL.unquotedName(name);
    }

    private final char parseQuote(boolean allowAposQuotes) {
        return (char)(this.parseIf('\"', false) ? 34 : (this.parseIf('`', false) ? 96 : (this.parseIf('[', false) ? 93 : (allowAposQuotes && this.parseIf('\'', false) ? 39 : 0))));
    }

    private final DataType<?> parseCastDataType() {
        char character = this.characterUpper();
        switch (character) {
            case 'S': {
                if (!this.parseKeywordIf("SIGNED")) break;
                if (!this.parseKeywordIf("INTEGER")) {
                    // empty if block
                }
                return SQLDataType.BIGINT;
            }
            case 'U': {
                if (!this.parseKeywordIf("UNSIGNED")) break;
                if (!this.parseKeywordIf("INTEGER")) {
                    // empty if block
                }
                return SQLDataType.BIGINTUNSIGNED;
            }
        }
        return this.parseDataType();
    }

    @Override
    public final DataType<?> parseDataType() {
        DataType<?> result = this.parseDataTypeIf(true);
        if (result == null) {
            throw this.expected("Data type");
        }
        return result;
    }

    private final DataType<?> parseDataTypeIf(boolean parseUnknownTypes) {
        DataType<Object> result = this.parseDataTypePrefixIf(parseUnknownTypes);
        if (result != null) {
            boolean array;
            do {
                array = this.parseKeywordIf("ARRAY");
                if (this.parseIf('[')) {
                    this.parseUnsignedIntegerLiteralIf();
                    this.parse(']');
                    array = true;
                }
                if (!array) continue;
                result = result.getArrayDataType();
            } while (array);
        }
        return result;
    }

    private final DataType<?> parseDataTypePrefixIf(boolean parseUnknownTypes) {
        Name name;
        char character = this.characterUpper();
        if (character == '[' || character == '\"' || character == '`') {
            character = this.characterNextUpper();
        }
        switch (character) {
            case 'A': {
                if (this.parseKeywordOrIdentifierIf("ARRAY")) {
                    return SQLDataType.OTHER.getArrayDataType();
                }
                if (!this.parseKeywordIf("AUTO_INCREMENT")) break;
                this.parseDataTypeIdentityArgsIf();
                return SQLDataType.INTEGER.identity(true);
            }
            case 'B': {
                if (this.parseKeywordOrIdentifierIf("BIGINT")) {
                    return this.parseUnsigned(this.parseAndIgnoreDataTypeLength(SQLDataType.BIGINT));
                }
                if (this.parseKeywordOrIdentifierIf("BIGSERIAL")) {
                    return SQLDataType.BIGINT.identity(true);
                }
                if (this.parseKeywordOrIdentifierIf("BINARY")) {
                    if (this.parseKeywordIf("VARYING")) {
                        return this.parseDataTypeLength(SQLDataType.VARBINARY);
                    }
                    return this.parseDataTypeLength(SQLDataType.BINARY);
                }
                if (this.parseKeywordOrIdentifierIf("BIT")) {
                    return this.parseDataTypeLength(SQLDataType.BIT);
                }
                if (this.parseKeywordOrIdentifierIf("BLOB")) {
                    if (this.parseKeywordIf("SUB_TYPE")) {
                        if (this.parseKeywordIf("0", "BINARY")) {
                            return this.parseDataTypeLength(SQLDataType.BLOB);
                        }
                        if (this.parseKeywordIf("1", "TEXT")) {
                            return this.parseDataTypeLength(SQLDataType.CLOB);
                        }
                        throw this.expected("0", "BINARY", "1", "TEXT");
                    }
                    return this.parseDataTypeLength(SQLDataType.BLOB);
                }
                if (this.parseKeywordOrIdentifierIf("BOOLEAN") || this.parseKeywordOrIdentifierIf("BOOL")) {
                    return SQLDataType.BOOLEAN;
                }
                if (!this.parseKeywordOrIdentifierIf("BYTEA")) break;
                return SQLDataType.BLOB;
            }
            case 'C': {
                if (this.parseKeywordOrIdentifierIf("CHAR") || this.parseKeywordOrIdentifierIf("CHARACTER")) {
                    if (this.parseKeywordIf("VARYING")) {
                        return this.parseDataTypeCollation(this.parseDataTypeLength(SQLDataType.VARCHAR, SQLDataType.VARBINARY, () -> this.parseKeywordIf("FOR BIT DATA")));
                    }
                    if (this.parseKeywordIf("LARGE OBJECT")) {
                        return this.parseDataTypeCollation(this.parseDataTypeLength(SQLDataType.CLOB));
                    }
                    return this.parseDataTypeCollation(this.parseDataTypeLength(SQLDataType.CHAR, SQLDataType.BINARY, () -> this.parseKeywordIf("FOR BIT DATA")));
                }
                if (this.parseKeywordOrIdentifierIf("CITEXT")) {
                    return this.parseDataTypeCollation(this.parseAndIgnoreDataTypeLength(SQLDataType.CLOB));
                }
                if (!this.parseKeywordOrIdentifierIf("CLOB")) break;
                return this.parseDataTypeCollation(this.parseDataTypeLength(SQLDataType.CLOB));
            }
            case 'D': {
                if (this.parseKeywordOrIdentifierIf("DATE")) {
                    return SQLDataType.DATE;
                }
                if (this.parseKeywordOrIdentifierIf("DATETIME")) {
                    return this.parseDataTypePrecisionIf(SQLDataType.TIMESTAMP);
                }
                if (this.parseKeywordOrIdentifierIf("DECIMAL") || this.parseKeywordOrIdentifierIf("DEC")) {
                    return this.parseDataTypePrecisionScaleIf(SQLDataType.DECIMAL);
                }
                if (!this.parseKeywordOrIdentifierIf("DOUBLE PRECISION") && !this.parseKeywordOrIdentifierIf("DOUBLE")) break;
                return this.parseAndIgnoreDataTypePrecisionScaleIf(SQLDataType.DOUBLE);
            }
            case 'E': {
                if (!this.parseKeywordOrIdentifierIf("ENUM")) break;
                return this.parseDataTypeCollation(this.parseDataTypeEnum());
            }
            case 'F': {
                if (!this.parseKeywordOrIdentifierIf("FLOAT")) break;
                return this.parseAndIgnoreDataTypePrecisionScaleIf(SQLDataType.FLOAT);
            }
            case 'G': {
                if (!this.ignoreProEdition() && (this.parseKeywordOrIdentifierIf("GEOMETRY") || this.parseKeywordOrIdentifierIf("SDO_GEOMETRY")) && this.requireProEdition()) {
                    return this.parseAndIgnoreDataTypeLength(SQLDataType.GEOMETRY);
                }
                if (this.ignoreProEdition() || !this.parseKeywordOrIdentifierIf("GEOGRAPHY") || !this.requireProEdition()) break;
                return this.parseAndIgnoreDataTypeLength(SQLDataType.GEOGRAPHY);
            }
            case 'I': {
                if (this.parseKeywordOrIdentifierIf("INTEGER") || this.parseKeywordOrIdentifierIf("INT") || this.parseKeywordOrIdentifierIf("INT4")) {
                    return this.parseUnsigned(this.parseAndIgnoreDataTypeLength(SQLDataType.INTEGER));
                }
                if (this.parseKeywordOrIdentifierIf("INT2")) {
                    return SQLDataType.SMALLINT;
                }
                if (this.parseKeywordOrIdentifierIf("INT8")) {
                    return SQLDataType.BIGINT;
                }
                if (this.parseKeywordIf("INTERVAL")) {
                    if (this.parseKeywordIf("YEAR")) {
                        this.parseDataTypePrecisionIf();
                        this.parseKeyword("TO MONTH");
                        return SQLDataType.INTERVALYEARTOMONTH;
                    }
                    if (this.parseKeywordIf("DAY")) {
                        this.parseDataTypePrecisionIf();
                        this.parseKeyword("TO SECOND");
                        this.parseDataTypePrecisionIf();
                        return SQLDataType.INTERVALDAYTOSECOND;
                    }
                    return SQLDataType.INTERVAL;
                }
                if (!this.parseKeywordIf("IDENTITY")) break;
                this.parseDataTypeIdentityArgsIf();
                return SQLDataType.INTEGER.identity(true);
            }
            case 'J': {
                if (this.parseKeywordOrIdentifierIf("JSON")) {
                    return SQLDataType.JSON;
                }
                if (!this.parseKeywordOrIdentifierIf("JSONB")) break;
                return SQLDataType.JSONB;
            }
            case 'L': {
                if (this.parseKeywordOrIdentifierIf("LONGBLOB")) {
                    return SQLDataType.BLOB;
                }
                if (this.parseKeywordOrIdentifierIf("LONGTEXT")) {
                    return this.parseDataTypeCollation(SQLDataType.CLOB);
                }
                if (this.parseKeywordOrIdentifierIf("LONG NVARCHAR")) {
                    return this.parseDataTypeCollation(this.parseDataTypeLength(SQLDataType.LONGNVARCHAR));
                }
                if (this.parseKeywordOrIdentifierIf("LONG VARBINARY") || this.parseKeywordOrIdentifierIf("LONGVARBINARY")) {
                    return this.parseDataTypeCollation(this.parseDataTypeLength(SQLDataType.LONGVARBINARY));
                }
                if (!this.parseKeywordOrIdentifierIf("LONG VARCHAR") && !this.parseKeywordOrIdentifierIf("LONGVARCHAR")) break;
                return this.parseDataTypeCollation(this.parseDataTypeLength(SQLDataType.LONGVARCHAR, SQLDataType.LONGVARBINARY, () -> this.parseKeywordIf("FOR BIT DATA")));
            }
            case 'M': {
                if (this.parseKeywordOrIdentifierIf("MEDIUMBLOB")) {
                    return SQLDataType.BLOB;
                }
                if (this.parseKeywordOrIdentifierIf("MEDIUMINT")) {
                    return this.parseUnsigned(this.parseAndIgnoreDataTypeLength(SQLDataType.INTEGER));
                }
                if (!this.parseKeywordOrIdentifierIf("MEDIUMTEXT")) break;
                return this.parseDataTypeCollation(SQLDataType.CLOB);
            }
            case 'N': {
                if (this.parseKeywordIf("NATIONAL CHARACTER") || this.parseKeywordIf("NATIONAL CHAR")) {
                    if (this.parseKeywordIf("VARYING")) {
                        return this.parseDataTypeCollation(this.parseDataTypeLength(SQLDataType.NVARCHAR));
                    }
                    if (this.parseKeywordIf("LARGE OBJECT")) {
                        return this.parseDataTypeCollation(this.parseDataTypeLength(SQLDataType.NCLOB));
                    }
                    return this.parseDataTypeCollation(this.parseDataTypeLength(SQLDataType.NCHAR));
                }
                if (this.parseKeywordOrIdentifierIf("NCHAR")) {
                    if (this.parseKeywordIf("VARYING")) {
                        return this.parseDataTypeCollation(this.parseDataTypeLength(SQLDataType.NVARCHAR));
                    }
                    if (this.parseKeywordIf("LARGE OBJECT")) {
                        return this.parseDataTypeCollation(this.parseDataTypeLength(SQLDataType.NCLOB));
                    }
                    return this.parseDataTypeCollation(this.parseDataTypeLength(SQLDataType.NCHAR));
                }
                if (this.parseKeywordOrIdentifierIf("NCLOB")) {
                    return this.parseDataTypeCollation(this.parseDataTypeLength(SQLDataType.NCLOB));
                }
                if (this.parseKeywordOrIdentifierIf("NUMBER") || this.parseKeywordOrIdentifierIf("NUMERIC")) {
                    return this.parseDataTypePrecisionScaleIf(SQLDataType.NUMERIC);
                }
                if (!this.parseKeywordOrIdentifierIf("NVARCHAR") && !this.parseKeywordOrIdentifierIf("NVARCHAR2")) break;
                return this.parseDataTypeCollation(this.parseDataTypeLength(SQLDataType.NVARCHAR));
            }
            case 'O': {
                if (!this.parseKeywordOrIdentifierIf("OTHER")) break;
                return SQLDataType.OTHER;
            }
            case 'R': {
                if (!this.parseKeywordOrIdentifierIf("REAL")) break;
                return this.parseAndIgnoreDataTypePrecisionScaleIf(SQLDataType.REAL);
            }
            case 'S': {
                if (this.parseKeywordOrIdentifierIf("SERIAL4") || this.parseKeywordOrIdentifierIf("SERIAL")) {
                    return SQLDataType.INTEGER.identity(true);
                }
                if (this.parseKeywordOrIdentifierIf("SERIAL8")) {
                    return SQLDataType.BIGINT.identity(true);
                }
                if (this.parseKeywordOrIdentifierIf("SET")) {
                    return this.parseDataTypeCollation(this.parseDataTypeEnum());
                }
                if (this.parseKeywordOrIdentifierIf("SMALLINT")) {
                    return this.parseUnsigned(this.parseAndIgnoreDataTypeLength(SQLDataType.SMALLINT));
                }
                if (this.parseKeywordOrIdentifierIf("SMALLSERIAL") || this.parseKeywordOrIdentifierIf("SERIAL2")) {
                    return SQLDataType.SMALLINT.identity(true);
                }
                if (!this.parseKeywordOrIdentifierIf("STRING")) break;
                return this.parseDataTypeCollation(this.parseDataTypeLength(SQLDataType.VARCHAR));
            }
            case 'T': {
                if (this.parseKeywordOrIdentifierIf("TEXT")) {
                    return this.parseDataTypeCollation(this.parseAndIgnoreDataTypeLength(SQLDataType.CLOB));
                }
                if (this.parseKeywordOrIdentifierIf("TIMESTAMPTZ")) {
                    return this.parseDataTypePrecisionIf(SQLDataType.TIMESTAMPWITHTIMEZONE);
                }
                if (this.parseKeywordOrIdentifierIf("TIMESTAMP")) {
                    Integer precision = this.parseDataTypePrecisionIf();
                    if (this.parseKeywordOrIdentifierIf("WITH TIME ZONE")) {
                        return precision == null ? SQLDataType.TIMESTAMPWITHTIMEZONE : SQLDataType.TIMESTAMPWITHTIMEZONE(precision);
                    }
                    if (!this.parseKeywordOrIdentifierIf("WITHOUT TIME ZONE")) {
                        // empty if block
                    }
                    return precision == null ? SQLDataType.TIMESTAMP : SQLDataType.TIMESTAMP(precision);
                }
                if (this.parseKeywordOrIdentifierIf("TIMETZ")) {
                    return this.parseDataTypePrecisionIf(SQLDataType.TIMEWITHTIMEZONE);
                }
                if (this.parseKeywordOrIdentifierIf("TIME")) {
                    Integer precision = this.parseDataTypePrecisionIf();
                    if (this.parseKeywordOrIdentifierIf("WITH TIME ZONE")) {
                        return precision == null ? SQLDataType.TIMEWITHTIMEZONE : SQLDataType.TIMEWITHTIMEZONE(precision);
                    }
                    if (!this.parseKeywordOrIdentifierIf("WITHOUT TIME ZONE")) {
                        // empty if block
                    }
                    return precision == null ? SQLDataType.TIME : SQLDataType.TIME(precision);
                }
                if (this.parseKeywordOrIdentifierIf("TINYBLOB")) {
                    return SQLDataType.BLOB;
                }
                if (this.parseKeywordOrIdentifierIf("TINYINT")) {
                    return this.parseUnsigned(this.parseAndIgnoreDataTypeLength(SQLDataType.TINYINT));
                }
                if (!this.parseKeywordOrIdentifierIf("TINYTEXT")) break;
                return this.parseDataTypeCollation(SQLDataType.CLOB);
            }
            case 'U': {
                if (this.parseKeywordOrIdentifierIf("UUID")) {
                    return SQLDataType.UUID;
                }
                if (!this.parseKeywordOrIdentifierIf("UNIQUEIDENTIFIER")) break;
                return SQLDataType.UUID;
            }
            case 'V': {
                if (this.parseKeywordOrIdentifierIf("VARCHAR") || this.parseKeywordOrIdentifierIf("VARCHAR2") || this.parseKeywordOrIdentifierIf("VARCHAR_IGNORECASE")) {
                    return this.parseDataTypeCollation(this.parseDataTypeLength(SQLDataType.VARCHAR, SQLDataType.VARBINARY, () -> this.parseKeywordIf("FOR BIT DATA")));
                }
                if (!this.parseKeywordOrIdentifierIf("VARBINARY")) break;
                return this.parseDataTypeLength(SQLDataType.VARBINARY);
            }
            case 'X': {
                if (!this.parseKeywordOrIdentifierIf("XML")) break;
                return SQLDataType.XML;
            }
            case 'Y': {
                if (!this.parseKeywordOrIdentifierIf("YEAR")) break;
                return this.parseDataTypeLength(SQLDataType.YEAR);
            }
        }
        if (parseUnknownTypes && (name = this.parseNameIf()) != null) {
            return this.parseDataTypeLength(new DefaultDataType<Object>(this.dsl.dialect(), Object.class, name));
        }
        return null;
    }

    private final void parseDataTypeIdentityArgsIf() {
        if (this.parseIf('(')) {
            this.parseList(',', (? super ParseContext c) -> c.parseField());
            this.parse(')');
        }
    }

    private final boolean parseKeywordOrIdentifierIf(String keyword) {
        int p = this.position();
        char quoteEnd = this.parseQuote(false);
        boolean result = this.parseKeywordIf(keyword);
        if (!result) {
            this.position(p);
        } else if (quoteEnd != '\u0000') {
            this.parse(quoteEnd);
        }
        return result;
    }

    private final DataType<?> parseUnsigned(DataType result) {
        if (this.parseKeywordIf("UNSIGNED")) {
            if (result == SQLDataType.TINYINT) {
                return SQLDataType.TINYINTUNSIGNED;
            }
            if (result == SQLDataType.SMALLINT) {
                return SQLDataType.SMALLINTUNSIGNED;
            }
            if (result == SQLDataType.INTEGER) {
                return SQLDataType.INTEGERUNSIGNED;
            }
            if (result == SQLDataType.BIGINT) {
                return SQLDataType.BIGINTUNSIGNED;
            }
        }
        return result;
    }

    private final DataType<?> parseAndIgnoreDataTypeLength(DataType<?> result) {
        if (this.parseIf('(')) {
            this.parseUnsignedIntegerLiteral();
            this.parse(')');
        }
        return result;
    }

    private final DataType<?> parseDataTypeLength(DataType<?> in) {
        return this.parseDataTypeLength(in, in, () -> false);
    }

    private final DataType<?> parseDataTypeLength(DataType<?> in, DataType<?> alternative, BooleanSupplier alternativeIfTrue) {
        Integer length = null;
        if (this.parseIf('(')) {
            if (!this.parseKeywordIf("MAX")) {
                length = Tools.asInt(this.parseUnsignedIntegerLiteral());
            }
            if (!(in != SQLDataType.VARCHAR && in != SQLDataType.CHAR || this.parseKeywordIf("BYTE"))) {
                this.parseKeywordIf("CHAR");
            }
            this.parse(')');
        }
        DataType<?> result = alternativeIfTrue.getAsBoolean() ? alternative : in;
        return length == null ? result : result.length(length);
    }

    private final DataType<?> parseDataTypeCollation(DataType<?> result) {
        Collation col;
        CharacterSet cs = this.parseCharacterSetSpecificationIf();
        if (cs != null) {
            result = result.characterSet(cs);
        }
        if ((col = this.parseCollateSpecificationIf()) != null) {
            result = result.collation(col);
        }
        return result;
    }

    private final CharacterSet parseCharacterSetSpecificationIf() {
        if (this.parseKeywordIf("CHARACTER SET", "CHARSET")) {
            this.parseIf('=');
            return this.parseCharacterSet();
        }
        return null;
    }

    private final Collation parseCollateSpecificationIf() {
        if (this.parseKeywordIf("COLLATE")) {
            this.parseIf('=');
            return this.parseCollation();
        }
        return null;
    }

    private final DataType<?> parseAndIgnoreDataTypePrecisionScaleIf(DataType<?> result) {
        if (this.parseIf('(')) {
            this.parseUnsignedIntegerLiteral();
            if (this.parseIf(',')) {
                this.parseUnsignedIntegerLiteral();
            }
            this.parse(')');
        }
        return result;
    }

    private final Integer parseDataTypePrecisionIf() {
        Integer precision = null;
        if (this.parseIf('(')) {
            precision = Tools.asInt(this.parseUnsignedIntegerLiteral());
            this.parse(')');
        }
        return precision;
    }

    private final DataType<?> parseDataTypePrecisionIf(DataType<?> result) {
        if (this.parseIf('(')) {
            int precision = Tools.asInt(this.parseUnsignedIntegerLiteral());
            result = result.precision(precision);
            this.parse(')');
        }
        return result;
    }

    private final DataType<?> parseDataTypePrecisionScaleIf(DataType<?> result) {
        if (this.parseIf('(')) {
            int precision = this.parseIf('*') ? 38 : Tools.asInt(this.parseUnsignedIntegerLiteral());
            result = this.parseIf(',') ? result.precision(precision, Tools.asInt(this.parseSignedIntegerLiteral())) : result.precision(precision);
            this.parse(')');
        }
        return result;
    }

    private final DataType<?> parseDataTypeEnum() {
        this.parse('(');
        ArrayList<String> literals = new ArrayList<String>();
        int length = 0;
        do {
            String literal = this.parseStringLiteral();
            length = Math.max(length, literal.length());
            literals.add(literal);
        } while (this.parseIf(','));
        this.parse(')');
        String className = "GeneratedEnum" + (literals.hashCode() & 0x7FFFFFF);
        StringBuilder content = new StringBuilder();
        content.append("package org.jooq.impl;\nenum ").append(className).append(" implements org.jooq.EnumType {\n");
        for (int i = 0; i < literals.size(); ++i) {
            content.append("  E").append(i).append("(\"").append(((String)literals.get(i)).replace("\"", "\\\"")).append("\"),\n");
        }
        content.append("  ;\n  final String literal;\n  private ").append(className).append("(String literal) { this.literal = literal; }\n  @Override\n  public String getName() {\n    return getClass().getName();\n  }\n  @Override\n  public String getLiteral() {\n    return literal;\n  }\n}");
        return SQLDataType.VARCHAR(length).asEnumDataType((Class)Reflect.compile("org.jooq.impl." + className, content.toString()).get());
    }

    private final char parseCharacterLiteral() {
        this.parse('\'', false);
        char c = this.character();
        if (c == '\'') {
            this.parse('\'', false);
        }
        this.positionInc();
        this.parse('\'');
        return c;
    }

    private final Field<?> parseBindVariableIf() {
        Object paramName;
        int p = this.position();
        switch (this.character()) {
            case '?': {
                this.parse('?');
                paramName = "" + this.bindIndex;
                break;
            }
            default: {
                String prefix = StringUtils.defaultIfNull(this.settings().getParseNamedParamPrefix(), ":");
                if (this.parseIf(prefix, false)) {
                    if (":".equals(prefix)) {
                        this.parseWhitespaceIf();
                    }
                    Name identifier = this.parseIdentifier(false, true);
                    paramName = identifier.last();
                    if (!"$".equals(prefix) || !((String)paramName).endsWith("$")) break;
                    this.position(p);
                    return null;
                }
                return null;
            }
        }
        Object binding = this.nextBinding();
        if (binding instanceof Field) {
            Field f = (Field)binding;
            return f;
        }
        Param<Object> param2 = DSL.param((String)paramName, binding);
        if (this.bindParamListener != null) {
            this.bindParams.put((String)paramName, param2);
        }
        return param2;
    }

    private final Comment parseComment() {
        return DSL.comment(this.parseStringLiteral());
    }

    private final String parseStringLiteral(String literal) {
        String value = this.parseStringLiteral();
        if (!literal.equals(value)) {
            throw this.expected("String literal: '" + literal + "'");
        }
        return value;
    }

    @Override
    public final String parseStringLiteral() {
        String result = this.parseStringLiteralIf();
        if (result == null) {
            throw this.expected("String literal");
        }
        return result;
    }

    @Override
    public final String parseStringLiteralIf() {
        if (this.parseIf('q', '\'', false) || this.parseIf('Q', '\'', false)) {
            return this.parseOracleQuotedStringLiteral();
        }
        if (this.parseIf('e', '\'', false) || this.parseIf('E', '\'', false)) {
            return this.parseUnquotedStringLiteral(true, '\'');
        }
        if (this.peek('\'')) {
            return this.parseUnquotedStringLiteral(false, '\'');
        }
        if (this.parseIf('n', '\'', false) || this.parseIf('N', '\'', false)) {
            return this.parseUnquotedStringLiteral(true, '\'');
        }
        if (this.peek('\"')) {
            return this.parseUnquotedStringLiteral(false, '\"');
        }
        if (this.peek('$')) {
            return this.parseDollarQuotedStringLiteralIf();
        }
        return null;
    }

    private final Boolean parseBitLiteralIf() {
        if (this.parseIf("B'", false) || this.parseIf("b'", false)) {
            boolean result;
            boolean bl = result = !this.parseIf('0') && this.parseIf('1');
            if (this.parseIf('0') || this.parseIf('1')) {
                throw this.exception("Currently, only BIT(1) literals are supported");
            }
            this.parse('\'');
            return result;
        }
        return null;
    }

    private final byte[] parseBinaryLiteralIf() {
        if (this.parseIf("X'", false) || this.parseIf("x'", false)) {
            if (this.parseIf('\'')) {
                return Tools.EMPTY_BYTE;
            }
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            char c1 = '\u0000';
            while (true) {
                if (this.hasMore() && (c1 = this.character()) == ' ') {
                    this.positionInc();
                    continue;
                }
                char c2 = this.characterNext();
                if (c1 == '\'') break;
                if (c2 == '\'') {
                    throw this.exception("Unexpected token: \"'\"");
                }
                try {
                    buffer.write(Integer.parseInt("" + c1 + c2, 16));
                }
                catch (NumberFormatException e) {
                    throw this.exception("Illegal character for binary literal");
                }
                this.positionInc(2);
                if (!this.hasMore()) break;
            }
            if (c1 == '\'') {
                this.positionInc();
                this.parseWhitespaceIf();
                return buffer.toByteArray();
            }
            throw this.exception("Binary literal not terminated");
        }
        return null;
    }

    private final String parseOracleQuotedStringLiteral() {
        char end;
        this.parse('\'', false);
        char start2 = this.character();
        switch (start2) {
            case '[': {
                end = ']';
                this.positionInc();
                break;
            }
            case '{': {
                end = '}';
                this.positionInc();
                break;
            }
            case '(': {
                end = ')';
                this.positionInc();
                break;
            }
            case '<': {
                end = '>';
                this.positionInc();
                break;
            }
            case '\t': 
            case '\n': 
            case '\r': 
            case ' ': {
                throw this.exception("Illegal quote string character");
            }
            default: {
                end = start2;
                this.positionInc();
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int i = this.position(); i < this.sql.length; ++i) {
            char c = this.character(i);
            if (c == end) {
                if (this.character(i + 1) == '\'') {
                    this.position(i + 2);
                    this.parseWhitespaceIf();
                    return sb.toString();
                }
                ++i;
            }
            sb.append(c);
        }
        throw this.exception("Quoted string literal not terminated");
    }

    private final String parseDollarQuotedStringLiteralIf() {
        char c;
        int i;
        int previous = this.position();
        if (!this.peek('$')) {
            return null;
        }
        this.parse('$');
        int openTokenStart = previous;
        int openTokenEnd = previous;
        int closeTokenStart = -1;
        int closeTokenEnd = -1;
        for (i = this.position(); i < this.sql.length; ++i) {
            c = this.character(i);
            if (!Character.isJavaIdentifierPart(c)) {
                return null;
            }
            ++openTokenEnd;
            if (c == '$') break;
        }
        this.position(openTokenEnd + 1);
        for (i = this.position(); i < this.sql.length; ++i) {
            c = this.character(i);
            if (c == '$') {
                if (closeTokenStart == -1) {
                    closeTokenStart = i;
                    continue;
                }
                closeTokenEnd = i;
                if (openTokenEnd - openTokenStart == closeTokenEnd - closeTokenStart) break;
                closeTokenStart = closeTokenEnd;
                continue;
            }
            if (closeTokenStart <= -1 || this.character(i) == this.character(i - (closeTokenStart - openTokenStart))) continue;
            closeTokenStart = -1;
        }
        if (closeTokenEnd != -1) {
            this.position(closeTokenEnd + 1);
            return this.substring(openTokenEnd + 1, closeTokenStart);
        }
        this.position(previous);
        return null;
    }

    /*
     * Enabled aggressive block sorting
     */
    private final String parseUnquotedStringLiteral(boolean postgresEscaping, char delim) {
        this.parse(delim, false);
        StringBuilder sb = new StringBuilder();
        int i = this.position();
        while (true) {
            block23: {
                if (i >= this.sql.length) {
                    throw this.exception("String literal not terminated");
                }
                int c1 = this.character(i);
                switch (c1) {
                    case 92: {
                        if (!postgresEscaping) break;
                        char c2 = this.character(++i);
                        switch (c2) {
                            case 'b': {
                                c1 = 8;
                                break;
                            }
                            case 'n': {
                                c1 = 10;
                                break;
                            }
                            case 't': {
                                c1 = 9;
                                break;
                            }
                            case 'r': {
                                c1 = 13;
                                break;
                            }
                            case 'f': {
                                c1 = 12;
                                break;
                            }
                            case 'x': {
                                char c3 = this.character(i + 1);
                                char c4 = this.character(i + 2);
                                int d3 = Character.digit(c3, 16);
                                if (d3 == -1) {
                                    throw this.exception("Illegal hexadecimal byte value");
                                }
                                ++i;
                                int d4 = Character.digit(c4, 16);
                                if (d4 != -1) {
                                    c1 = (char)(16 * d3 + d4);
                                    ++i;
                                    break;
                                }
                                c1 = (char)d3;
                                break;
                            }
                            case 'u': {
                                c1 = (char)Integer.parseInt(new String(this.sql, i + 1, 4), 16);
                                i += 4;
                                break;
                            }
                            case 'U': {
                                sb.appendCodePoint(Integer.parseInt(new String(this.sql, i + 1, 8), 16));
                                i += 8;
                                break block23;
                            }
                            default: {
                                char c4;
                                char c3;
                                if (Character.digit(c2, 8) != -1) {
                                    c3 = this.character(i + 1);
                                    if (Character.digit(c3, 8) != -1) {
                                        if (Character.digit(c4 = this.character(++i + 1), 8) != -1) {
                                            ++i;
                                            c1 = (char)Integer.parseInt("" + c2 + c3 + c4, 8);
                                            break;
                                        }
                                        c1 = (char)Integer.parseInt("" + c2 + c3, 8);
                                        break;
                                    }
                                    c1 = (char)Integer.parseInt("" + c2, 8);
                                    break;
                                }
                                c1 = c2;
                                break;
                            }
                        }
                        break;
                    }
                    case 34: {
                        if (delim != '\"' || this.parseFamily() != SQLDialect.ACCESS) break;
                    }
                    case 39: {
                        if (this.character(i + 1) != delim) {
                            this.position(i + 1);
                            this.parseWhitespaceIf();
                            return sb.toString();
                        }
                        ++i;
                    }
                }
                sb.append((char)c1);
            }
            ++i;
        }
    }

    private final Field<Number> parseFieldUnsignedNumericLiteral(Sign sign) {
        Field<Number> result = this.parseFieldUnsignedNumericLiteralIf(sign);
        if (result == null) {
            throw this.expected("Unsigned numeric literal");
        }
        return result;
    }

    private final Field<Number> parseFieldUnsignedNumericLiteralIf(Sign sign) {
        Number r = this.parseUnsignedNumericLiteralIf(sign);
        return r == null ? null : DSL.inline(r);
    }

    private final Number parseUnsignedNumericLiteralIf(Sign sign) {
        int p = this.position();
        boolean decimal = false;
        this.parseDigits();
        if (decimal |= this.parseIf('.', false)) {
            this.parseDigits();
        }
        if (p == this.position()) {
            return null;
        }
        if (this.parseIf('e', false) || this.parseIf('E', false)) {
            this.parseIf('-', false);
            this.parseDigits();
            String s2 = this.substring(p, this.position());
            this.parseWhitespaceIf();
            return sign == Sign.MINUS ? -Double.parseDouble(s2) : Double.parseDouble(s2);
        }
        String s3 = this.substring(p, this.position());
        this.parseWhitespaceIf();
        if (decimal) {
            return sign == Sign.MINUS ? new BigDecimal(s3).negate() : new BigDecimal(s3);
        }
        try {
            return sign == Sign.MINUS ? -Long.valueOf(s3).longValue() : Long.valueOf(s3);
        }
        catch (Exception e1) {
            return sign == Sign.MINUS ? new BigInteger(s3).negate() : new BigInteger(s3);
        }
    }

    private void parseDigits() {
        char c;
        while ((c = this.character()) >= '0' && c <= '9') {
            this.positionInc();
        }
    }

    private final Field<Integer> parseZeroOne() {
        if (this.parseIf('0')) {
            return DSL.zero();
        }
        if (this.parseIf('1')) {
            return DSL.one();
        }
        throw this.expected("0 or 1");
    }

    private final Field<Integer> parseZeroOneDefault() {
        if (this.parseIf('0')) {
            return DSL.zero();
        }
        if (this.parseIf('1')) {
            return DSL.one();
        }
        if (this.parseKeywordIf("DEFAULT")) {
            return DSL.defaultValue(SQLDataType.INTEGER);
        }
        throw this.expected("0 or 1");
    }

    @Override
    public final Long parseSignedIntegerLiteral() {
        Long result = this.parseSignedIntegerLiteralIf();
        if (result == null) {
            throw this.expected("Signed integer");
        }
        return result;
    }

    @Override
    public final Long parseSignedIntegerLiteralIf() {
        Sign sign = this.parseSign();
        Long unsigned = sign == Sign.MINUS ? this.parseUnsignedIntegerLiteral() : this.parseUnsignedIntegerLiteralIf();
        return unsigned == null ? null : Long.valueOf(sign == Sign.MINUS ? -unsigned.longValue() : unsigned);
    }

    private final <T> List<T> parseList(char separator, Function<? super ParseContext, ? extends T> element) {
        return this.parseList((? super ParseContext c) -> c.parseIf(separator), element);
    }

    @Override
    public final <T> List<T> parseList(String separator, Function<? super ParseContext, ? extends T> element) {
        return this.parseList((? super ParseContext c) -> c.parseIf(separator), element);
    }

    @Override
    public final <T> List<T> parseList(Predicate<? super ParseContext> separator, Function<? super ParseContext, ? extends T> element) {
        ArrayList<T> result = new ArrayList<T>();
        do {
            result.add(element.apply(this));
        } while (separator.test(this));
        return result;
    }

    @Override
    public final <T> T parseParenthesised(Function<? super ParseContext, ? extends T> content) {
        return this.parseParenthesised('(', content, ')');
    }

    @Override
    public final <T> T parseParenthesised(char open, Function<? super ParseContext, ? extends T> content, char close) {
        this.parse(open);
        T result = content.apply(this);
        this.parse(close);
        return result;
    }

    @Override
    public final <T> T parseParenthesised(String open, Function<? super ParseContext, ? extends T> content, String close) {
        this.parse(open);
        T result = content.apply(this);
        this.parse(close);
        return result;
    }

    private final Field<Long> parseUnsignedIntegerOrBindVariable() {
        Long i = this.parseUnsignedIntegerLiteralIf();
        if (i != null) {
            return DSL.inline(i);
        }
        Field<Long> f = this.parseBindVariableIf();
        if (f != null) {
            return f;
        }
        throw this.expected("Unsigned integer or bind variable");
    }

    @Override
    public final Long parseUnsignedIntegerLiteral() {
        Long result = this.parseUnsignedIntegerLiteralIf();
        if (result == null) {
            throw this.expected("Unsigned integer literal");
        }
        return result;
    }

    @Override
    public final Long parseUnsignedIntegerLiteralIf() {
        int p = this.position();
        this.parseDigits();
        if (p == this.position()) {
            return null;
        }
        String s2 = this.substring(p, this.position());
        this.parseWhitespaceIf();
        return Long.valueOf(s2);
    }

    private final JoinType parseJoinTypeIf() {
        if (this.parseKeywordIf("CROSS")) {
            if (this.parseKeywordIf("JOIN")) {
                return JoinType.CROSS_JOIN;
            }
            if (this.parseKeywordIf("APPLY")) {
                return JoinType.CROSS_APPLY;
            }
        } else {
            if (this.parseKeywordIf("INNER") && this.parseKeyword("JOIN")) {
                return JoinType.JOIN;
            }
            if (this.parseKeywordIf("JOIN")) {
                return JoinType.JOIN;
            }
            if (this.parseKeywordIf("LEFT")) {
                if (this.parseKeywordIf("SEMI") && this.parseKeyword("JOIN")) {
                    return JoinType.LEFT_SEMI_JOIN;
                }
                if (this.parseKeywordIf("ANTI") && this.parseKeyword("JOIN")) {
                    return JoinType.LEFT_ANTI_JOIN;
                }
                if (!this.parseKeywordIf("OUTER")) {
                    // empty if block
                }
                if (this.parseKeyword("JOIN")) {
                    return JoinType.LEFT_OUTER_JOIN;
                }
            } else {
                if (this.parseKeywordIf("RIGHT")) {
                    if (!this.parseKeywordIf("OUTER")) {
                        // empty if block
                    }
                    if (this.parseKeyword("JOIN")) {
                        return JoinType.RIGHT_OUTER_JOIN;
                    }
                }
                if (this.parseKeywordIf("FULL")) {
                    if (!this.parseKeywordIf("OUTER")) {
                        // empty if block
                    }
                    if (this.parseKeyword("JOIN")) {
                        return JoinType.FULL_OUTER_JOIN;
                    }
                }
                if (this.parseKeywordIf("OUTER APPLY")) {
                    return JoinType.OUTER_APPLY;
                }
                if (this.parseKeywordIf("NATURAL")) {
                    if (this.parseKeywordIf("LEFT")) {
                        if (!this.parseKeywordIf("OUTER")) {
                            // empty if block
                        }
                        if (this.parseKeyword("JOIN")) {
                            return JoinType.NATURAL_LEFT_OUTER_JOIN;
                        }
                    }
                    if (this.parseKeywordIf("RIGHT")) {
                        if (!this.parseKeywordIf("OUTER")) {
                            // empty if block
                        }
                        if (this.parseKeyword("JOIN")) {
                            return JoinType.NATURAL_RIGHT_OUTER_JOIN;
                        }
                    }
                    if (this.parseKeywordIf("FULL")) {
                        if (!this.parseKeywordIf("OUTER")) {
                            // empty if block
                        }
                        if (this.parseKeyword("JOIN")) {
                            return JoinType.NATURAL_FULL_OUTER_JOIN;
                        }
                    }
                    if (!this.parseKeywordIf("INNER")) {
                        // empty if block
                    }
                    if (this.parseKeyword("JOIN")) {
                        return JoinType.NATURAL_JOIN;
                    }
                } else if (this.parseKeywordIf("STRAIGHT_JOIN")) {
                    return JoinType.STRAIGHT_JOIN;
                }
            }
        }
        return null;
    }

    private final TruthValue parseTruthValueIf() {
        if (this.parseKeywordIf("TRUE")) {
            return TruthValue.T_TRUE;
        }
        if (this.parseKeywordIf("FALSE")) {
            return TruthValue.T_FALSE;
        }
        if (this.parseKeywordIf("NULL")) {
            return TruthValue.T_NULL;
        }
        return null;
    }

    private final CombineOperator parseCombineOperatorIf(boolean intersectOnly) {
        if (!intersectOnly && this.parseKeywordIf("UNION")) {
            if (this.parseKeywordIf("ALL")) {
                return CombineOperator.UNION_ALL;
            }
            if (this.parseKeywordIf("DISTINCT")) {
                return CombineOperator.UNION;
            }
            return CombineOperator.UNION;
        }
        if (!intersectOnly && this.parseKeywordIf("EXCEPT", "MINUS")) {
            if (this.parseKeywordIf("ALL")) {
                return CombineOperator.EXCEPT_ALL;
            }
            if (this.parseKeywordIf("DISTINCT")) {
                return CombineOperator.EXCEPT;
            }
            return CombineOperator.EXCEPT;
        }
        if (intersectOnly && this.parseKeywordIf("INTERSECT")) {
            if (this.parseKeywordIf("ALL")) {
                return CombineOperator.INTERSECT_ALL;
            }
            if (this.parseKeywordIf("DISTINCT")) {
                return CombineOperator.INTERSECT;
            }
            return CombineOperator.INTERSECT;
        }
        return null;
    }

    private final ComputationalOperation parseComputationalOperationIf() {
        switch (this.characterUpper()) {
            case 'A': {
                if (this.parseFunctionNameIf("ANY")) {
                    return ComputationalOperation.ANY;
                }
                if (this.parseFunctionNameIf("ANY_VALUE")) {
                    return ComputationalOperation.ANY_VALUE;
                }
                if (!this.parseFunctionNameIf("AVG")) break;
                return ComputationalOperation.AVG;
            }
            case 'B': {
                if (this.parseFunctionNameIf("BOOL_AND", "BOOLAND_AGG")) {
                    return ComputationalOperation.EVERY;
                }
                if (!this.parseFunctionNameIf("BOOL_OR", "BOOLOR_AGG")) break;
                return ComputationalOperation.ANY;
            }
            case 'E': {
                if (!this.parseFunctionNameIf("EVERY")) break;
                return ComputationalOperation.EVERY;
            }
            case 'L': {
                if (this.parseFunctionNameIf("LOGICAL_AND")) {
                    return ComputationalOperation.EVERY;
                }
                if (!this.parseFunctionNameIf("LOGICAL_OR")) break;
                return ComputationalOperation.ANY;
            }
            case 'M': {
                if (this.parseFunctionNameIf("MAX")) {
                    return ComputationalOperation.MAX;
                }
                if (this.parseFunctionNameIf("MEDIAN")) {
                    return ComputationalOperation.MEDIAN;
                }
                if (this.parseFunctionNameIf("MIN")) {
                    return ComputationalOperation.MIN;
                }
                if (!this.parseFunctionNameIf("MUL")) break;
                return ComputationalOperation.PRODUCT;
            }
            case 'P': {
                if (!this.parseFunctionNameIf("PRODUCT")) break;
                return ComputationalOperation.PRODUCT;
            }
            case 'S': {
                if (this.parseFunctionNameIf("SUM")) {
                    return ComputationalOperation.SUM;
                }
                if (this.parseFunctionNameIf("SOME")) {
                    return ComputationalOperation.ANY;
                }
                if (this.parseFunctionNameIf("STDDEV", "STDEVP", "STDDEV_POP")) {
                    return ComputationalOperation.STDDEV_POP;
                }
                if (this.parseFamily() == SQLDialect.INFORMIX && this.parseFunctionNameIf("STDEV")) {
                    return ComputationalOperation.STDDEV_POP;
                }
                if (!this.parseFunctionNameIf("STDDEV_SAMP", "STDEV", "STDEV_SAMP")) break;
                return ComputationalOperation.STDDEV_SAMP;
            }
            case 'V': {
                if (this.parseFunctionNameIf("VAR_POP", "VARIANCE", "VARP")) {
                    return ComputationalOperation.VAR_POP;
                }
                if (this.parseFamily() == SQLDialect.HANA && this.parseFunctionNameIf("VAR")) {
                    return ComputationalOperation.VAR_POP;
                }
                if (!this.parseFunctionNameIf("VAR_SAMP", "VARIANCE_SAMP", "VAR")) break;
                return ComputationalOperation.VAR_SAMP;
            }
        }
        return null;
    }

    private final Comparator parseComparatorIf() {
        if (this.parseFamily() == SQLDialect.ORACLE) {
            if (this.parseTokensIf('!', '=') || this.parseTokensIf('<', '>') || this.parseTokensIf('^', '=')) {
                return Comparator.NOT_EQUALS;
            }
            if (this.parseTokensIf('>', '=')) {
                return Comparator.GREATER_OR_EQUAL;
            }
            if (this.parseTokensIf('<', '=')) {
                return Comparator.LESS_OR_EQUAL;
            }
        }
        if (this.parseIf("=") || this.parseKeywordIf("EQ")) {
            return Comparator.EQUALS;
        }
        if (this.parseIf("!=") || this.parseIf("<>") || this.parseIf("^=") || this.parseKeywordIf("NE")) {
            return Comparator.NOT_EQUALS;
        }
        if (this.parseIf(">=") || this.parseKeywordIf("GE")) {
            return Comparator.GREATER_OR_EQUAL;
        }
        if (this.parseIf(">") || this.parseKeywordIf("GT")) {
            return Comparator.GREATER;
        }
        if (this.parseIf("<=>")) {
            return Comparator.IS_NOT_DISTINCT_FROM;
        }
        if (this.parseIf("<=") || this.parseKeywordIf("LE")) {
            return Comparator.LESS_OR_EQUAL;
        }
        if (this.parseIf("<") || this.parseKeywordIf("LT")) {
            return Comparator.LESS;
        }
        return null;
    }

    private final TSQLOuterJoinComparator parseTSQLOuterJoinComparatorIf() {
        if (this.parseIf("*=")) {
            return TSQLOuterJoinComparator.LEFT;
        }
        if (this.parseIf("=*")) {
            return TSQLOuterJoinComparator.RIGHT;
        }
        return null;
    }

    private final String parseUntilEOL() {
        String result = this.parseUntilEOLIf();
        if (result == null) {
            throw this.expected("Content before EOL");
        }
        return result;
    }

    private final String parseUntilEOLIf() {
        int start2;
        int stop;
        for (stop = start2 = this.position(); stop < this.sql.length; ++stop) {
            char c = this.character(stop);
            if (c == '\r') {
                if (this.character(stop + 1) != '\n') break;
                ++stop;
                break;
            }
            if (c == '\n') break;
        }
        if (start2 == stop) {
            return null;
        }
        this.position(stop);
        this.parseWhitespaceIf();
        return this.substring(start2, stop);
    }

    private final boolean parseTokens(char ... tokens) {
        boolean result = this.parseTokensIf(tokens);
        if (!result) {
            throw this.expected(new String(tokens));
        }
        return result;
    }

    private final boolean parseTokensIf(char ... tokens) {
        int p = this.position();
        for (char token : tokens) {
            if (this.parseIf(token)) continue;
            this.position(p);
            return false;
        }
        return true;
    }

    private final boolean peekTokens(char ... tokens) {
        int p = this.position();
        for (char token : tokens) {
            if (this.parseIf(token)) continue;
            this.position(p);
            return false;
        }
        this.position(p);
        return true;
    }

    @Override
    public final boolean parse(String string) {
        boolean result = this.parseIf(string);
        if (!result) {
            throw this.expected(string);
        }
        return result;
    }

    @Override
    public final boolean parseIf(String string) {
        return this.parseIf(string, true);
    }

    private final boolean parseIf(String string, boolean skipAfterWhitespace) {
        boolean result = this.peek(string);
        if (result) {
            this.positionInc(string.length());
            if (skipAfterWhitespace) {
                this.parseWhitespaceIf();
            }
        }
        return result;
    }

    @Override
    public final boolean parse(char c) {
        return this.parse(c, true);
    }

    private final boolean parse(char c, boolean skipAfterWhitespace) {
        if (!this.parseIf(c, skipAfterWhitespace)) {
            throw this.expected("Token '" + c + "'");
        }
        return true;
    }

    @Override
    public final boolean parseIf(char c) {
        return this.parseIf(c, true);
    }

    private final boolean parseIf(char c, boolean skipAfterWhitespace) {
        boolean result = this.peek(c);
        if (result) {
            this.positionInc();
            if (skipAfterWhitespace) {
                this.parseWhitespaceIf();
            }
        }
        return result;
    }

    private final boolean parseIf(char c, char peek, boolean skipAfterWhitespace) {
        if (this.character() != c) {
            return false;
        }
        if (this.characterNext() != peek) {
            return false;
        }
        this.positionInc();
        if (skipAfterWhitespace) {
            this.parseWhitespaceIf();
        }
        return true;
    }

    private final boolean peekFunctionNameIf(String name) {
        return this.peekKeyword(name, false, false, true);
    }

    @Override
    public final boolean parseFunctionNameIf(String name) {
        return this.peekKeyword(name, true, false, true);
    }

    private final boolean parseFunctionNameIf(String name1, String name2) {
        return this.parseFunctionNameIf(name1) || this.parseFunctionNameIf(name2);
    }

    private final boolean parseFunctionNameIf(String name1, String name2, String name3) {
        return this.parseFunctionNameIf(name1) || this.parseFunctionNameIf(name2) || this.parseFunctionNameIf(name3);
    }

    @Override
    public final boolean parseFunctionNameIf(String ... names) {
        return Tools.anyMatch(names, n -> this.parseFunctionNameIf((String)n));
    }

    private final boolean parseOperator(String operator) {
        if (!this.parseOperatorIf(operator)) {
            throw this.expected("Operator '" + operator + "'");
        }
        return true;
    }

    private final boolean parseOperatorIf(String operator) {
        return this.peekOperator(operator, true);
    }

    private final boolean peekOperator(String operator) {
        return this.peekOperator(operator, false);
    }

    private final boolean peekOperator(String operator, boolean updatePosition) {
        int length = operator.length();
        int p = this.position();
        if (this.sql.length < p + length) {
            return false;
        }
        int pos = this.afterWhitespace(p, false);
        int i = 0;
        while (i < length) {
            if (this.sql[pos] != operator.charAt(i)) {
                return false;
            }
            ++i;
            ++pos;
        }
        if (this.isOperatorPart(pos)) {
            return false;
        }
        if (updatePosition) {
            this.position(pos);
            this.parseWhitespaceIf();
        }
        return true;
    }

    @Override
    public final boolean parseKeyword(String keyword) {
        if (!this.parseKeywordIf(keyword)) {
            throw this.expected("Keyword '" + keyword + "'");
        }
        return true;
    }

    private final boolean parseKeyword(String keyword1, String keyword2) {
        if (this.parseKeywordIf(keyword1, keyword2)) {
            return true;
        }
        throw this.expected(keyword1, keyword2);
    }

    private final boolean parseKeyword(String keyword1, String keyword2, String keyword3) {
        if (this.parseKeywordIf(keyword1, keyword2, keyword3)) {
            return true;
        }
        throw this.expected(keyword1, keyword2, keyword3);
    }

    private final boolean parseKeywordUndocumentedAlternatives(String keyword, String undocumented1) {
        if (this.parseKeywordIf(undocumented1)) {
            return true;
        }
        return this.parseKeyword(keyword);
    }

    @Override
    public final boolean parseKeywordIf(String keyword) {
        return this.peekKeyword(keyword, true, false, false);
    }

    private final boolean parseKeywordIf(String keyword1, String keyword2) {
        return this.parseKeywordIf(keyword1) || this.parseKeywordIf(keyword2);
    }

    private final boolean parseKeywordIf(String keyword1, String keyword2, String keyword3) {
        return this.parseKeywordIf(keyword1) || this.parseKeywordIf(keyword2) || this.parseKeywordIf(keyword3);
    }

    @Override
    public final boolean parseKeywordIf(String ... keywords) {
        return Tools.anyMatch(keywords, k -> this.parseKeywordIf((String)k));
    }

    @Override
    public final boolean parseKeyword(String ... keywords) {
        if (this.parseKeywordIf(keywords)) {
            return true;
        }
        throw this.expected(keywords);
    }

    private final Keyword parseAndGetKeyword(String ... keywords) {
        Keyword result = this.parseAndGetKeywordIf(keywords);
        if (result == null) {
            throw this.expected(keywords);
        }
        return result;
    }

    private final Keyword parseAndGetKeywordIf(String ... keywords) {
        return Tools.findAny(keywords, k -> this.parseKeywordIf((String)k), k -> DSL.keyword(k.toLowerCase()));
    }

    private final Keyword parseAndGetKeywordIf(String keyword) {
        if (this.parseKeywordIf(keyword)) {
            return DSL.keyword(keyword.toLowerCase());
        }
        return null;
    }

    @Override
    public final boolean peek(char c) {
        return this.character() == c;
    }

    @Override
    public final boolean peek(String string) {
        return this.peek(string, this.position());
    }

    private final boolean peek(String string, int p) {
        int length = string.length();
        if (this.sql.length < p + length) {
            return false;
        }
        for (int i = 0; i < length; ++i) {
            if (this.sql[p + i] == string.charAt(i)) continue;
            return false;
        }
        return true;
    }

    @Override
    public final boolean peekKeyword(String ... keywords) {
        return Tools.anyMatch(keywords, k -> this.peekKeyword((String)k));
    }

    @Override
    public final boolean peekKeyword(String keyword) {
        return this.peekKeyword(keyword, false, false, false);
    }

    private final boolean peekKeyword(String keyword1, String keyword2) {
        return this.peekKeyword(keyword1) || this.peekKeyword(keyword2);
    }

    private final boolean peekKeyword(String keyword1, String keyword2, String keyword3) {
        return this.peekKeyword(keyword1) || this.peekKeyword(keyword2) || this.peekKeyword(keyword3);
    }

    private final boolean peekKeyword(String keyword, boolean updatePosition, boolean peekIntoParens, boolean requireFunction) {
        int length = keyword.length();
        int p = this.position();
        if (this.sql.length < p + length) {
            return false;
        }
        int skip = this.afterWhitespace(p, peekIntoParens) - p;
        block3: for (int i = 0; i < length; ++i) {
            char c = keyword.charAt(i);
            int pos = p + i + skip;
            switch (c) {
                case ' ': {
                    if (!Character.isWhitespace(this.character(pos))) {
                        return false;
                    }
                    skip += this.afterWhitespace(pos) - pos - 1;
                    continue block3;
                }
                default: {
                    if (this.upper(this.character(pos)) == c) continue block3;
                    return false;
                }
            }
        }
        int pos = p + length + skip;
        if (this.isIdentifierPart(pos) || this.character(pos) == '.') {
            return false;
        }
        if (requireFunction && this.character(this.afterWhitespace(pos)) != '(') {
            return false;
        }
        if (updatePosition) {
            this.positionInc(length + skip);
            this.parseWhitespaceIf();
        }
        return true;
    }

    private final boolean parseWhitespaceIf() {
        this.positionBeforeWhitespace = this.position();
        this.position(this.afterWhitespace(this.positionBeforeWhitespace));
        return this.positionBeforeWhitespace != this.position();
    }

    private final int afterWhitespace(int p) {
        return this.afterWhitespace(p, false);
    }

    /*
     * Unable to fully structure code
     */
    private final int afterWhitespace(int p, boolean peekIntoParens) {
        blockCommentNestLevel = 0;
        ignoreComment = false;
        ignoreCommentStart = this.settings().getParseIgnoreCommentStart();
        ignoreCommentStop = this.settings().getParseIgnoreCommentStop();
        checkIgnoreComment = Boolean.FALSE.equals(this.settings().isParseIgnoreComments()) == false;
        block17: for (i = p; i < this.sql.length; ++i) {
            switch (this.sql[i]) {
                case '\t': 
                case '\n': 
                case '\r': 
                case ' ': {
                    p = i + 1;
                    continue block17;
                }
                case '(': {
                    if (!peekIntoParens) break block17;
                    continue block17;
                }
                case '/': {
                    if (i + 1 >= this.sql.length || this.sql[i + 1] != '*') ** GOTO lbl40
                    i += 2;
                    ++blockCommentNestLevel;
                    while (i < this.sql.length) {
                        if (ignoreComment = this.peekIgnoreComment(ignoreComment, ignoreCommentStart, ignoreCommentStop, checkIgnoreComment, i)) ** GOTO lbl-1000
                        switch (this.sql[i]) {
                            case '/': {
                                if (i + 1 < this.sql.length && this.sql[i + 1] == '*') {
                                    i += 2;
                                    ++blockCommentNestLevel;
                                }
                                ** GOTO lbl35
                            }
                            case '+': {
                                if (!this.ignoreHints() && i + 1 < this.sql.length && (this.sql[i + 1] >= 'A' && this.sql[i + 1] <= 'Z' || this.sql[i + 1] >= 'a' && this.sql[i + 1] <= 'z')) {
                                    blockCommentNestLevel = 0;
                                    break block17;
                                }
                                ** GOTO lbl35
                            }
                            case '*': {
                                if (i + 1 < this.sql.length && this.sql[i + 1] == '/') {
                                    p = ++i + 1;
                                    if (--blockCommentNestLevel == 0) continue block17;
                                }
                            }
lbl35:
                            // 6 sources

                            default: lbl-1000:
                            // 2 sources

                            {
                                ++i;
                                break;
                            }
                        }
                    }
                    break block17;
lbl40:
                    // 1 sources

                    if (i + 1 >= this.sql.length || this.sql[i + 1] != '/') break block17;
                    i += 2;
                    while (i < this.sql.length) {
                        if (!(ignoreComment = this.peekIgnoreComment(ignoreComment, ignoreCommentStart, ignoreCommentStop, checkIgnoreComment, i))) {
                            switch (this.sql[i]) {
                                case '\n': 
                                case '\r': {
                                    p = i + 1;
                                    continue block17;
                                }
                            }
                        }
                        ++i;
                    }
                    p = i;
                    break block17;
                }
                case '#': 
                case '-': {
                    if ((this.sql[i] != '-' || i + 1 >= this.sql.length || this.sql[i + 1] != '-') && (this.sql[i] != '#' || !DefaultParseContext.SUPPORTS_HASH_COMMENT_SYNTAX.contains((Object)this.parseDialect()))) break block17;
                    i = this.sql[i] == '-' ? (i += 2) : ++i;
                    while (i < this.sql.length) {
                        if (!(ignoreComment = this.peekIgnoreComment(ignoreComment, ignoreCommentStart, ignoreCommentStop, checkIgnoreComment, i))) {
                            switch (this.sql[i]) {
                                case '\n': 
                                case '\r': {
                                    p = i + 1;
                                    continue block17;
                                }
                            }
                        }
                        ++i;
                    }
                    p = i;
                    break block17;
                }
                default: {
                    p = i;
                    break block17;
                }
            }
        }
        if (blockCommentNestLevel > 0) {
            throw this.exception("Nested block comment not properly closed");
        }
        return p;
    }

    private final boolean peekIgnoreComment(boolean ignoreComment, String ignoreCommentStart, String ignoreCommentStop, boolean checkIgnoreComment, int i) {
        if (checkIgnoreComment) {
            ignoreComment = !ignoreComment ? this.peek(ignoreCommentStart, i) : !this.peek(ignoreCommentStop, i);
        }
        return ignoreComment;
    }

    private final char upper(char c) {
        return c >= 'a' && c <= 'z' ? (char)(c - 32) : c;
    }

    DefaultParseContext(DSLContext dsl, Meta meta, ParseWithMetaLookups metaLookups, String sqlString, Object[] bindings) {
        super(dsl.configuration());
        this.dsl = dsl;
        this.locale = SettingsTools.parseLocale(dsl.settings());
        this.meta = meta;
        this.metaLookups = metaLookups;
        this.sql = sqlString != null ? sqlString.toCharArray() : new char[]{};
        this.bindings = bindings;
        this.bindParamListener = (Consumer)dsl.configuration().data("org.jooq.parser.param-collector");
        this.listener = ParseListeners.get(dsl.configuration());
        this.listener.parseStart(this);
        this.parseWhitespaceIf();
    }

    @Override
    public final SQLDialect parseDialect() {
        SQLDialect result = this.settings().getParseDialect();
        if (result == null) {
            result = SQLDialect.DEFAULT;
        }
        return result;
    }

    @Override
    public final SQLDialect parseFamily() {
        return this.parseDialect().family();
    }

    @Override
    public final SQLDialectCategory parseCategory() {
        return this.parseDialect().category();
    }

    @Override
    public final LanguageContext languageContext() {
        return this.languageContext;
    }

    private final ParseWithMetaLookups metaLookups() {
        if (this.metaLookupsForceIgnore()) {
            return ParseWithMetaLookups.OFF;
        }
        if (Transformations.transformAppendMissingTableReferences(this.configuration)) {
            return this.metaLookups == ParseWithMetaLookups.OFF ? ParseWithMetaLookups.IGNORE_ON_FAILURE : this.metaLookups;
        }
        return this.metaLookups;
    }

    private final boolean metaLookupsForceIgnore() {
        return this.metaLookupsForceIgnore;
    }

    private final DefaultParseContext metaLookupsForceIgnore(boolean m4) {
        this.metaLookupsForceIgnore = m4;
        return this;
    }

    private final boolean proEdition() {
        return this.configuration().commercial();
    }

    private final boolean ignoreProEdition() {
        return !this.proEdition() && Boolean.TRUE.equals(this.settings().isParseIgnoreCommercialOnlyFeatures());
    }

    private final boolean requireProEdition() {
        if (!this.proEdition()) {
            throw this.exception("Feature only supported in pro edition");
        }
        return true;
    }

    private final boolean requireUnsupportedSyntax() {
        if (this.dsl.configuration().settings().getParseUnsupportedSyntax() == ParseUnsupportedSyntax.FAIL) {
            throw this.exception("Syntax not supported");
        }
        return true;
    }

    private final String substring(int startPosition, int endPosition) {
        return new String(this.sql, startPosition, endPosition - startPosition);
    }

    private final ParserException internalError() {
        return this.exception("Internal Error");
    }

    private final ParserException expected(String object) {
        return this.init(new ParserException(this.mark(), object + " expected"));
    }

    private final ParserException expected(String ... objects) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < objects.length; ++i) {
            if (i == 0) {
                sb.append(objects[i]);
                continue;
            }
            if (i == 1 && objects.length == 2) {
                sb.append(" or ").append(objects[i]);
                continue;
            }
            if (i == objects.length - 1) {
                sb.append(", or ").append(objects[i]);
                continue;
            }
            sb.append(", ").append(objects[i]);
        }
        return this.init(new ParserException(this.mark(), sb.toString() + " expected"));
    }

    private final ParserException notImplemented(String feature) {
        return this.notImplemented(feature, "https://github.com/jOOQ/jOOQ/issues/10171");
    }

    private final ParserException notImplemented(String feature, String link) {
        return this.init(new ParserException(this.mark(), feature + " not yet implemented. If you're interested in this feature, please comment on " + link));
    }

    private final ParserException unsupportedClause() {
        return this.init(new ParserException(this.mark(), "Unsupported clause"));
    }

    @Override
    public final ParserException exception(String message) {
        return this.init(new ParserException(this.mark(), message));
    }

    private final ParserException init(ParserException e) {
        int[] line = this.line();
        return e.position(this.position).line(line[0]).column(line[1]);
    }

    private final Object nextBinding() {
        if (this.bindIndex++ < this.bindings.length) {
            return this.bindings[this.bindIndex - 1];
        }
        if (this.bindings.length == 0) {
            return null;
        }
        throw this.exception("No binding provided for bind index " + this.bindIndex);
    }

    private final int[] line() {
        int line = 1;
        int column = 1;
        for (int i = 0; i < this.position; ++i) {
            if (this.sql[i] == '\r') {
                ++line;
                column = 1;
                if (i + 1 >= this.sql.length || this.sql[i + 1] != '\n') continue;
                ++i;
                continue;
            }
            if (this.sql[i] == '\n') {
                ++line;
                column = 1;
                continue;
            }
            ++column;
        }
        return new int[]{line, column};
    }

    private final char characterUpper() {
        return Character.toUpperCase(this.character());
    }

    @Override
    public final char character() {
        return this.character(this.position);
    }

    @Override
    public final char character(int pos) {
        return pos >= 0 && pos < this.sql.length ? this.sql[pos] : (char)' ';
    }

    private final char characterNextUpper() {
        return Character.toUpperCase(this.characterNext());
    }

    private final char characterNext() {
        return this.character(this.position + 1);
    }

    @Override
    public final char[] characters() {
        return this.sql;
    }

    @Override
    public final ParseContext characters(char[] newCharacters) {
        this.sql = newCharacters;
        return this;
    }

    @Override
    public final int position() {
        return this.position;
    }

    @Override
    public final boolean position(int newPosition) {
        this.position = newPosition;
        return true;
    }

    private final boolean positionInc() {
        return this.positionInc(1);
    }

    private final boolean positionInc(int inc) {
        return this.position(this.position + inc);
    }

    private final String delimiter() {
        return this.delimiter;
    }

    private final void delimiter(String newDelimiter) {
        this.delimiter = newDelimiter;
    }

    private final boolean ignoreHints() {
        return this.ignoreHints;
    }

    private final void ignoreHints(boolean newIgnoreHints) {
        this.ignoreHints = newIgnoreHints;
    }

    private final boolean isOperatorPart(int pos) {
        return this.isOperatorPart(this.character(pos));
    }

    private final boolean isOperatorPart(char character) {
        switch (character) {
            case '!': 
            case '#': 
            case '%': 
            case '&': 
            case '*': 
            case '+': 
            case '-': 
            case '/': 
            case ':': 
            case '<': 
            case '=': 
            case '>': 
            case '?': 
            case '@': 
            case '^': 
            case '|': 
            case '~': {
                return true;
            }
        }
        return false;
    }

    private final boolean isIdentifierPart() {
        return this.isIdentifierPart(this.character());
    }

    private final boolean isIdentifierPart(int pos) {
        return this.isIdentifierPart(this.character(pos));
    }

    private final boolean isIdentifierPart(char character) {
        return Character.isJavaIdentifierPart(character) || (character == '@' || character == '#') && character != this.delimiter.charAt(0);
    }

    private final boolean isIdentifierStart() {
        return this.isIdentifierStart(this.character());
    }

    private final boolean isIdentifierStart(int pos) {
        return this.isIdentifierStart(this.character(pos));
    }

    private final boolean isIdentifierStart(char character) {
        return Character.isJavaIdentifierStart(character) || (character == '@' || character == '#') && character != this.delimiter.charAt(0);
    }

    private final boolean hasMore() {
        return this.position < this.sql.length;
    }

    private final boolean hasMore(int offset) {
        return this.position + offset < this.sql.length;
    }

    private final boolean done() {
        return this.position >= this.sql.length && (this.bindings.length == 0 || this.bindings.length == this.bindIndex);
    }

    private final <Q extends QueryPart> Q done(String message, Q result) {
        if (this.done()) {
            return this.notify(result);
        }
        throw this.exception(message);
    }

    private final <Q extends QueryPart> Q wrap(Supplier<Q> supplier) {
        ParserException suppressed = null;
        try {
            QueryPart queryPart = (QueryPart)supplier.get();
            return (Q)queryPart;
        }
        catch (ParserException e) {
            suppressed = e;
            throw suppressed;
        }
        finally {
            try {
                this.listener.parseEnd(this);
            }
            catch (Throwable t2) {
                if (suppressed != null) {
                    t2.addSuppressed(suppressed);
                }
                throw t2;
            }
        }
    }

    private final <Q extends QueryPart> Q notify(Q result) {
        if (this.bindParamListener != null) {
            LinkedHashMap params = new LinkedHashMap();
            this.dsl.configuration().deriveAppending(VisitListener.onVisitStart(ctx -> {
                Param p;
                QueryPart patt525115$temp = ctx.queryPart();
                if (patt525115$temp instanceof Param && !params.containsKey((p = (Param)patt525115$temp).getParamName())) {
                    params.put(p.getParamName(), p);
                }
            })).dsl().render(result);
            for (String name : this.bindParams.keySet()) {
                this.bindParamListener.accept((Param)params.get(name));
            }
        }
        return result;
    }

    private final boolean asTrue(Object o) {
        return true;
    }

    private final String mark() {
        int[] line = this.line();
        return "[" + line[0] + ":" + line[1] + "] " + (this.position > 50 ? "..." : "") + this.substring(Math.max(0, this.position - 50), this.position) + "[*]" + this.substring(this.position, Math.min(this.sql.length, this.position + 80)) + (this.sql.length > this.position + 80 ? "..." : "");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private final <T> T newScope(Supplier<T> scoped) {
        ParseScope old = this.scope;
        try {
            this.scope = new ParseScope();
            T t2 = scoped.get();
            return t2;
        }
        finally {
            this.scope = old;
        }
    }

    private final ScopeStack.Value<Field<?>> resolveInTableScope(Iterable<ScopeStack.Value<Table<?>>> tables, Name lookupName, FieldProxy<?> lookup, ScopeStack.Value<Field<?>> found) {
        for (ScopeStack.Value<Table<?>> t2 : tables) {
            Table<?> table = t2.value();
            if (table instanceof JoinTable) {
                JoinTable j = (JoinTable)table;
                found = this.resolveInTableScope(Arrays.asList(new ScopeStack.Value(t2.scopeLevel(), j.lhs), new ScopeStack.Value(t2.scopeLevel(), j.rhs)), lookupName, lookup, found);
                continue;
            }
            if (lookupName.qualified()) {
                Name q = lookupName.qualifier();
                boolean x = q.qualified();
                if ((!x || !q.equals(t2.value().getQualifiedName())) && (x || !q.last().equals(t2.value().getName())) || (found = ScopeStack.Value.of(t2.scopeLevel(), t2.value().field(lookup.getName()))) == null && (!Transformations.transformAppendMissingTableReferences(this.configuration) || (found = ScopeStack.Value.of(t2.scopeLevel(), lookup.resolve())) == null)) continue;
                break;
            }
            ScopeStack.Value<Field<?>> f = ScopeStack.Value.of(t2.scopeLevel(), t2.value().field(lookup.getName()));
            if (f == null) continue;
            if (found == null || found.scopeLevel() < f.scopeLevel()) {
                found = f;
                continue;
            }
            this.position(lookup.position());
            throw this.exception("Ambiguous field identifier");
        }
        return found;
    }

    private final Table<?> lookupTable(int positionBeforeName, Name name) {
        if (this.meta != null) {
            List<Table<?>> tables = this.meta.getTables(name);
            if (!tables.isEmpty()) {
                for (Table<?> table : tables) {
                    if (table.getQualifiedName().qualified() != name.qualified()) continue;
                    return tables.get(0);
                }
            }
            if (!name.qualified()) {
                for (ParseSearchSchema schema : this.settings().getParseSearchPath()) {
                    tables = this.meta.getTables(DSL.name(schema.getCatalog(), schema.getSchema()).append(name));
                    if (tables.size() != 1) continue;
                    return tables.get(0);
                }
            }
        }
        if (this.metaLookups() == ParseWithMetaLookups.THROW_ON_FAILURE) {
            this.position(positionBeforeName);
            throw this.exception("Unknown table identifier");
        }
        return DSL.table(name);
    }

    private final QualifiedAsterisk lookupQualifiedAsterisk(int positionBeforeName, Name name) {
        if (this.metaLookups() == ParseWithMetaLookups.OFF || this.scope.lookupQualifiedAsterisks.scopeLevel() < 0) {
            return DSL.table(name).asterisk();
        }
        QualifiedAsteriskProxy asterisk = this.scope.lookupQualifiedAsterisks.get(name);
        if (asterisk == null) {
            asterisk = new QualifiedAsteriskProxy((QualifiedAsteriskImpl)DSL.table(name).asterisk(), positionBeforeName);
            this.scope.lookupQualifiedAsterisks.set(name, asterisk);
        }
        return asterisk;
    }

    private final Field<?> lookupField(int positionBeforeName, Name name) {
        if (this.metaLookups() == ParseWithMetaLookups.OFF || this.scope.lookupFields.scopeLevel() < 0) {
            return DSL.field(name);
        }
        FieldProxy<Object> field2 = this.scope.lookupFields.get(name);
        if (field2 == null) {
            field2 = new FieldProxy((AbstractField)DSL.field(name), positionBeforeName);
            this.scope.lookupFields.set(name, field2);
        }
        return field2;
    }

    public String toString() {
        return this.mark();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final <T> T data(Object key, Object value, Function<? super DefaultParseContext, ? extends T> function) {
        Object previous = this.data(key, value);
        try {
            T t2 = function.apply(this);
            return t2;
        }
        finally {
            if (previous == null) {
                this.data().remove(key);
            } else {
                this.data(key, previous);
            }
        }
    }

    static {
        TreeSet<String> set = new TreeSet<String>(Arrays.asList(KEYWORDS_IN_FROM));
        set.addAll(Arrays.asList(KEYWORDS_IN_STATEMENTS));
        set.addAll(Arrays.asList("CONNECT BY", "CREATE", "EXCEPT", "FETCH FIRST", "FETCH NEXT", "FOR JSON", "FOR KEY SHARE", "FOR NO KEY UPDATE", "FOR SHARE", "FOR UPDATE", "FOR XML", "FORCE KEY", "FORCE INDEX", "GROUP BY", "HAVING", "IGNORE KEY", "IGNORE INDEX", "INTERSECT", "INTO", "LIMIT", "MINUS", "OFFSET", "ORDER BY", "QUALIFY", "RETURNING", "ROWS", "START WITH", "UNION", "USE KEY", "USE INDEX", "WHERE", "WINDOW"));
        KEYWORDS_IN_SELECT_FROM = set.toArray(Tools.EMPTY_STRING);
        set = new TreeSet<String>(Arrays.asList(KEYWORDS_IN_FROM));
        set.addAll(Arrays.asList("FROM", "SET", "WHERE", "ORDER BY", "LIMIT", "RETURNING"));
        KEYWORDS_IN_UPDATE_FROM = set.toArray(Tools.EMPTY_STRING);
        set = new TreeSet<String>(Arrays.asList(KEYWORDS_IN_FROM));
        set.addAll(Arrays.asList("FROM", "USING", "ALL", "WHERE", "ORDER BY", "LIMIT", "RETURNING"));
        set.addAll(Arrays.asList(KEYWORDS_IN_STATEMENTS));
        KEYWORDS_IN_DELETE_FROM = set.toArray(Tools.EMPTY_STRING);
        PIVOT_KEYWORDS = new String[]{"FOR"};
        IGNORE = Lazy.of(() -> new IgnoreQuery());
        IGNORE_NO_DELIMITER = Lazy.of(() -> new IgnoreQuery());
    }

    static final class IgnoreQuery
    extends AbstractDDLQuery
    implements QOM.UEmpty {
        final String sql;

        IgnoreQuery() {
            this("/* ignored */");
        }

        IgnoreQuery(String sql) {
            super(Tools.CONFIG.get());
            this.sql = sql;
        }

        @Override
        public void accept(Context<?> ctx) {
            ctx.sql(this.sql);
        }
    }

    private class ParseScope {
        private boolean scopeClear = false;
        private final ScopeStack<Name, Table<?>> tableScope = new ScopeStack();
        private final ScopeStack<Name, Field<?>> fieldScope = new ScopeStack();
        private final ScopeStack<Name, QualifiedAsteriskProxy> lookupQualifiedAsterisks = new ScopeStack();
        private final ScopeStack<Name, FieldProxy<?>> lookupFields = new ScopeStack();
        @Pro
        private final ScopeStack<String, Variable<?>> proceduralScope = new ScopeStack(VariableImpl.NO_VARIABLE);

        private ParseScope() {
        }

        private final Table<?> scope(Table<?> table) {
            this.tableScope.set(table.getQualifiedName(), table);
            return table;
        }

        private final Field<?> scope(Field<?> field2) {
            this.fieldScope.set(field2.getQualifiedName(), field2);
            return field2;
        }

        private final void scopeResolve() {
            if (!this.lookupFields.isEmpty()) {
                this.unknownField(this.lookupFields.iterator().next());
            }
            if (!this.lookupQualifiedAsterisks.isEmpty()) {
                this.unknownTable(this.lookupQualifiedAsterisks.iterator().next());
            }
        }

        private final void scopeStart() {
            this.tableScope.scopeStart();
            this.fieldScope.scopeStart();
            this.lookupFields.scopeStart();
            this.lookupFields.setAll(null);
            this.lookupQualifiedAsterisks.scopeStart();
            this.lookupQualifiedAsterisks.setAll(null);
        }

        private final void scopeEnd(Query scopeOwner) {
            ArrayList<FieldProxy> fields = new ArrayList<FieldProxy>();
            block0: for (QualifiedAsteriskProxy qualifiedAsteriskProxy : DefaultParseContext.this.scope.lookupQualifiedAsterisks.iterableAtScopeLevel()) {
                for (Table<?> t2 : DefaultParseContext.this.scope.tableScope) {
                    if (!t2.getName().equals(qualifiedAsteriskProxy.$table().getName())) continue;
                    qualifiedAsteriskProxy.delegate((QualifiedAsteriskImpl)t2.asterisk());
                    continue block0;
                }
                this.unknownTable(qualifiedAsteriskProxy);
            }
            for (FieldProxy fieldProxy : DefaultParseContext.this.scope.lookupFields.iterableAtScopeLevel()) {
                ScopeStack.Value<Field<?>> found = null;
                for (Field<?> f : DefaultParseContext.this.scope.fieldScope) {
                    if (!f.getName().equals(fieldProxy.getName())) continue;
                    if (found != null) {
                        DefaultParseContext.this.position(fieldProxy.position());
                        throw DefaultParseContext.this.exception("Ambiguous field identifier");
                    }
                    found = new ScopeStack.Value(0, f);
                }
                found = DefaultParseContext.this.resolveInTableScope(DefaultParseContext.this.scope.tableScope.valueIterable(), fieldProxy.getQualifiedName(), fieldProxy, found);
                if (found != null && !(found.value() instanceof FieldProxy)) {
                    fieldProxy.delegate((AbstractField)found.value());
                    continue;
                }
                fieldProxy.scopeOwner(scopeOwner);
                fields.add(fieldProxy);
            }
            DefaultParseContext.this.scope.lookupQualifiedAsterisks.scopeEnd();
            DefaultParseContext.this.scope.lookupFields.scopeEnd();
            DefaultParseContext.this.scope.tableScope.scopeEnd();
            DefaultParseContext.this.scope.fieldScope.scopeEnd();
            for (FieldProxy fieldProxy : fields) {
                if (DefaultParseContext.this.scope.lookupFields.get(fieldProxy.getQualifiedName()) != null) continue;
                if (DefaultParseContext.this.scope.lookupFields.inScope()) {
                    DefaultParseContext.this.scope.lookupFields.set(fieldProxy.getQualifiedName(), fieldProxy);
                    continue;
                }
                this.unknownField(fieldProxy);
            }
        }

        private final void scopeClear() {
            this.scopeClear = true;
        }

        private final void unknownField(FieldProxy<?> field2) {
            if (!this.scopeClear) {
                if (Transformations.transformAppendMissingTableReferences(DefaultParseContext.this.configuration) && field2.scopeOwner != null) {
                    Table<Record> t2 = field2.getTable();
                    SelectQueryImpl s2 = Tools.selectQueryImpl(field2.scopeOwner);
                    if (s2 != null) {
                        if (!s2.getFrom().contains(t2)) {
                            s2.getFrom().add(t2);
                        }
                        return;
                    }
                    UpdateQueryImpl<?> u = Tools.updateQueryImpl(field2.scopeOwner);
                    if (u != null) {
                        if (!u.table().equals(t2) && !u.getFrom().contains(t2)) {
                            u.getFrom().add(t2);
                        }
                        return;
                    }
                    DeleteQueryImpl<?> d = Tools.deleteQueryImpl(field2.scopeOwner);
                    if (d != null) {
                        if (!d.table().equals(t2) && !d.getUsing().contains(t2)) {
                            d.getUsing().add(t2);
                        }
                        return;
                    }
                }
                if (DefaultParseContext.this.metaLookups() == ParseWithMetaLookups.THROW_ON_FAILURE) {
                    DefaultParseContext.this.position(field2.position());
                    throw DefaultParseContext.this.exception("Unknown field identifier");
                }
            }
        }

        private final void unknownTable(QualifiedAsteriskProxy asterisk) {
            if (!this.scopeClear && DefaultParseContext.this.metaLookups() == ParseWithMetaLookups.THROW_ON_FAILURE) {
                DefaultParseContext.this.position(asterisk.position());
                throw DefaultParseContext.this.exception("Unknown table identifier");
            }
        }
    }

    private record ParseInlineConstraints(DataType<?> type, Comment fieldComment, boolean primary, boolean identity, boolean readonly) {
    }

    private static enum Sign {
        NONE,
        PLUS,
        MINUS;


        final Sign invert() {
            if (this == PLUS) {
                return MINUS;
            }
            if (this == MINUS) {
                return PLUS;
            }
            return NONE;
        }
    }

    static enum FunctionKeyword {
        FK_AND,
        FK_IN;

    }

    private static enum TSQLOuterJoinComparator {
        LEFT,
        RIGHT;

    }

    private static enum SequenceMethod {
        NEXTVAL,
        CURRVAL;

    }

    private static enum TruthValue {
        T_TRUE,
        T_FALSE,
        T_NULL;

    }

    private static enum ComputationalOperation {
        ANY_VALUE,
        AVG,
        MAX,
        MIN,
        SUM,
        PRODUCT,
        EVERY,
        ANY,
        SOME,
        COUNT,
        STDDEV_POP,
        STDDEV_SAMP,
        VAR_POP,
        VAR_SAMP,
        MEDIAN;

    }
}

