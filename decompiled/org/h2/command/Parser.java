/*
 * Decompiled with CFR 0.152.
 */
package org.h2.command;

import java.io.Serializable;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.TreeSet;
import org.h2.api.IntervalQualifier;
import org.h2.command.Command;
import org.h2.command.CommandContainer;
import org.h2.command.CommandList;
import org.h2.command.ParserBase;
import org.h2.command.Prepared;
import org.h2.command.Token;
import org.h2.command.ddl.AlterDomainAddConstraint;
import org.h2.command.ddl.AlterDomainDropConstraint;
import org.h2.command.ddl.AlterDomainExpressions;
import org.h2.command.ddl.AlterDomainRename;
import org.h2.command.ddl.AlterDomainRenameConstraint;
import org.h2.command.ddl.AlterIndexRename;
import org.h2.command.ddl.AlterSchemaRename;
import org.h2.command.ddl.AlterSequence;
import org.h2.command.ddl.AlterTableAddConstraint;
import org.h2.command.ddl.AlterTableAlterColumn;
import org.h2.command.ddl.AlterTableDropConstraint;
import org.h2.command.ddl.AlterTableRename;
import org.h2.command.ddl.AlterTableRenameColumn;
import org.h2.command.ddl.AlterTableRenameConstraint;
import org.h2.command.ddl.AlterUser;
import org.h2.command.ddl.AlterView;
import org.h2.command.ddl.Analyze;
import org.h2.command.ddl.CommandWithColumns;
import org.h2.command.ddl.CreateAggregate;
import org.h2.command.ddl.CreateConstant;
import org.h2.command.ddl.CreateDomain;
import org.h2.command.ddl.CreateFunctionAlias;
import org.h2.command.ddl.CreateIndex;
import org.h2.command.ddl.CreateLinkedTable;
import org.h2.command.ddl.CreateMaterializedView;
import org.h2.command.ddl.CreateRole;
import org.h2.command.ddl.CreateSchema;
import org.h2.command.ddl.CreateSequence;
import org.h2.command.ddl.CreateSynonym;
import org.h2.command.ddl.CreateTable;
import org.h2.command.ddl.CreateTrigger;
import org.h2.command.ddl.CreateUser;
import org.h2.command.ddl.CreateView;
import org.h2.command.ddl.DeallocateProcedure;
import org.h2.command.ddl.DefineCommand;
import org.h2.command.ddl.DropAggregate;
import org.h2.command.ddl.DropConstant;
import org.h2.command.ddl.DropDatabase;
import org.h2.command.ddl.DropDomain;
import org.h2.command.ddl.DropFunctionAlias;
import org.h2.command.ddl.DropIndex;
import org.h2.command.ddl.DropMaterializedView;
import org.h2.command.ddl.DropRole;
import org.h2.command.ddl.DropSchema;
import org.h2.command.ddl.DropSequence;
import org.h2.command.ddl.DropSynonym;
import org.h2.command.ddl.DropTable;
import org.h2.command.ddl.DropTrigger;
import org.h2.command.ddl.DropUser;
import org.h2.command.ddl.DropView;
import org.h2.command.ddl.GrantRevoke;
import org.h2.command.ddl.PrepareProcedure;
import org.h2.command.ddl.RefreshMaterializedView;
import org.h2.command.ddl.SequenceOptions;
import org.h2.command.ddl.SetComment;
import org.h2.command.ddl.TruncateTable;
import org.h2.command.dml.AlterTableSet;
import org.h2.command.dml.BackupCommand;
import org.h2.command.dml.Call;
import org.h2.command.dml.CommandWithValues;
import org.h2.command.dml.DataChangeStatement;
import org.h2.command.dml.Delete;
import org.h2.command.dml.ExecuteImmediate;
import org.h2.command.dml.ExecuteProcedure;
import org.h2.command.dml.Explain;
import org.h2.command.dml.Help;
import org.h2.command.dml.Insert;
import org.h2.command.dml.Merge;
import org.h2.command.dml.MergeUsing;
import org.h2.command.dml.NoOperation;
import org.h2.command.dml.RunScriptCommand;
import org.h2.command.dml.ScriptCommand;
import org.h2.command.dml.Set;
import org.h2.command.dml.SetClauseList;
import org.h2.command.dml.SetSessionCharacteristics;
import org.h2.command.dml.SetTypes;
import org.h2.command.dml.TransactionCommand;
import org.h2.command.dml.Update;
import org.h2.command.query.ForUpdate;
import org.h2.command.query.Query;
import org.h2.command.query.QueryOrderBy;
import org.h2.command.query.Select;
import org.h2.command.query.SelectUnion;
import org.h2.command.query.TableValueConstructor;
import org.h2.constraint.ConstraintActionType;
import org.h2.engine.ConnectionInfo;
import org.h2.engine.IsolationLevel;
import org.h2.engine.Mode;
import org.h2.engine.NullsDistinct;
import org.h2.engine.Procedure;
import org.h2.engine.SessionLocal;
import org.h2.engine.User;
import org.h2.expression.Alias;
import org.h2.expression.ArrayConstructorByQuery;
import org.h2.expression.ArrayElementReference;
import org.h2.expression.BinaryOperation;
import org.h2.expression.ConcatenationOperation;
import org.h2.expression.DomainValueExpression;
import org.h2.expression.Expression;
import org.h2.expression.ExpressionColumn;
import org.h2.expression.ExpressionList;
import org.h2.expression.ExpressionWithFlags;
import org.h2.expression.ExpressionWithVariableParameters;
import org.h2.expression.FieldReference;
import org.h2.expression.Format;
import org.h2.expression.Parameter;
import org.h2.expression.Rownum;
import org.h2.expression.SearchedCase;
import org.h2.expression.SequenceValue;
import org.h2.expression.SimpleCase;
import org.h2.expression.Subquery;
import org.h2.expression.TimeZoneOperation;
import org.h2.expression.TypedValueExpression;
import org.h2.expression.UnaryOperation;
import org.h2.expression.ValueExpression;
import org.h2.expression.Variable;
import org.h2.expression.Wildcard;
import org.h2.expression.aggregate.AbstractAggregate;
import org.h2.expression.aggregate.Aggregate;
import org.h2.expression.aggregate.AggregateType;
import org.h2.expression.aggregate.JavaAggregate;
import org.h2.expression.aggregate.ListaggArguments;
import org.h2.expression.analysis.DataAnalysisOperation;
import org.h2.expression.analysis.Window;
import org.h2.expression.analysis.WindowFrame;
import org.h2.expression.analysis.WindowFrameBound;
import org.h2.expression.analysis.WindowFrameBoundType;
import org.h2.expression.analysis.WindowFrameExclusion;
import org.h2.expression.analysis.WindowFrameUnits;
import org.h2.expression.analysis.WindowFunction;
import org.h2.expression.analysis.WindowFunctionType;
import org.h2.expression.condition.BetweenPredicate;
import org.h2.expression.condition.BooleanTest;
import org.h2.expression.condition.CompareLike;
import org.h2.expression.condition.Comparison;
import org.h2.expression.condition.ConditionAndOr;
import org.h2.expression.condition.ConditionAndOrN;
import org.h2.expression.condition.ConditionIn;
import org.h2.expression.condition.ConditionInArray;
import org.h2.expression.condition.ConditionInQuery;
import org.h2.expression.condition.ConditionLocalAndGlobal;
import org.h2.expression.condition.ConditionNot;
import org.h2.expression.condition.ExistsPredicate;
import org.h2.expression.condition.IsJsonPredicate;
import org.h2.expression.condition.NullPredicate;
import org.h2.expression.condition.TypePredicate;
import org.h2.expression.condition.UniquePredicate;
import org.h2.expression.function.ArrayFunction;
import org.h2.expression.function.BitFunction;
import org.h2.expression.function.BuiltinFunctions;
import org.h2.expression.function.CSVWriteFunction;
import org.h2.expression.function.CardinalityExpression;
import org.h2.expression.function.CastSpecification;
import org.h2.expression.function.CoalesceFunction;
import org.h2.expression.function.CompatibilitySequenceValueFunction;
import org.h2.expression.function.CompressFunction;
import org.h2.expression.function.ConcatFunction;
import org.h2.expression.function.CryptFunction;
import org.h2.expression.function.CurrentDateTimeValueFunction;
import org.h2.expression.function.CurrentGeneralValueSpecification;
import org.h2.expression.function.DBObjectFunction;
import org.h2.expression.function.DataTypeSQLFunction;
import org.h2.expression.function.DateTimeFormatFunction;
import org.h2.expression.function.DateTimeFunction;
import org.h2.expression.function.DayMonthNameFunction;
import org.h2.expression.function.FileFunction;
import org.h2.expression.function.HashFunction;
import org.h2.expression.function.JavaFunction;
import org.h2.expression.function.JsonConstructorFunction;
import org.h2.expression.function.LengthFunction;
import org.h2.expression.function.MathFunction;
import org.h2.expression.function.MathFunction1;
import org.h2.expression.function.MathFunction2;
import org.h2.expression.function.NullIfFunction;
import org.h2.expression.function.RandFunction;
import org.h2.expression.function.RegexpFunction;
import org.h2.expression.function.SessionControlFunction;
import org.h2.expression.function.SetFunction;
import org.h2.expression.function.SignalFunction;
import org.h2.expression.function.SoundexFunction;
import org.h2.expression.function.StringFunction;
import org.h2.expression.function.StringFunction1;
import org.h2.expression.function.StringFunction2;
import org.h2.expression.function.SubstringFunction;
import org.h2.expression.function.SysInfoFunction;
import org.h2.expression.function.TableInfoFunction;
import org.h2.expression.function.ToCharFunction;
import org.h2.expression.function.TrimFunction;
import org.h2.expression.function.TruncateValueFunction;
import org.h2.expression.function.XMLFunction;
import org.h2.expression.function.table.ArrayTableFunction;
import org.h2.expression.function.table.CSVReadFunction;
import org.h2.expression.function.table.JavaTableFunction;
import org.h2.expression.function.table.LinkSchemaFunction;
import org.h2.expression.function.table.TableFunction;
import org.h2.index.Index;
import org.h2.message.DbException;
import org.h2.mode.FunctionsPostgreSQL;
import org.h2.mode.ModeFunction;
import org.h2.mode.OnDuplicateKeyValues;
import org.h2.mode.Regclass;
import org.h2.schema.Domain;
import org.h2.schema.FunctionAlias;
import org.h2.schema.Schema;
import org.h2.schema.SchemaObject;
import org.h2.schema.Sequence;
import org.h2.schema.UserAggregate;
import org.h2.schema.UserDefinedFunction;
import org.h2.table.Column;
import org.h2.table.DataChangeDeltaTable;
import org.h2.table.DualTable;
import org.h2.table.FunctionTable;
import org.h2.table.IndexColumn;
import org.h2.table.IndexHints;
import org.h2.table.MaterializedView;
import org.h2.table.QueryExpressionTable;
import org.h2.table.RangeTable;
import org.h2.table.Table;
import org.h2.table.TableFilter;
import org.h2.table.TableView;
import org.h2.util.IntervalUtils;
import org.h2.util.ParserUtil;
import org.h2.util.StringUtils;
import org.h2.util.Utils;
import org.h2.util.geometry.EWKTUtils;
import org.h2.util.json.JSONItemType;
import org.h2.value.CompareMode;
import org.h2.value.DataType;
import org.h2.value.ExtTypeInfoEnum;
import org.h2.value.ExtTypeInfoGeometry;
import org.h2.value.ExtTypeInfoNumeric;
import org.h2.value.ExtTypeInfoRow;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueArray;
import org.h2.value.ValueBigint;
import org.h2.value.ValueDate;
import org.h2.value.ValueDouble;
import org.h2.value.ValueGeometry;
import org.h2.value.ValueInteger;
import org.h2.value.ValueInterval;
import org.h2.value.ValueJson;
import org.h2.value.ValueNull;
import org.h2.value.ValueRow;
import org.h2.value.ValueTime;
import org.h2.value.ValueTimeTimeZone;
import org.h2.value.ValueTimestamp;
import org.h2.value.ValueTimestampTimeZone;
import org.h2.value.ValueUuid;
import org.h2.value.ValueVarchar;

public final class Parser
extends ParserBase {
    private static final String WITH_STATEMENT_SUPPORTS_LIMITED_SUB_STATEMENTS = "WITH statement supports only SELECT, TABLE, VALUES, CREATE TABLE, INSERT, UPDATE, MERGE or DELETE statements";
    private CreateView createView;
    private Prepared currentPrepared;
    private Select currentSelect;
    private List<TableView> cteCleanups;
    private String schemaName;
    private boolean rightsChecked;
    private boolean recompileAlways;
    private int orderInFrom;
    private boolean parseDomainConstraint;

    public Parser(SessionLocal sessionLocal) {
        super(sessionLocal);
    }

    public Prepared prepare(String string) {
        Prepared prepared = this.parse(string, null);
        prepared.prepare();
        if (this.currentTokenType != 93) {
            throw this.getSyntaxError();
        }
        return prepared;
    }

    public Query prepareQueryExpression(String string) {
        Query query = (Query)this.parse(string, null);
        query.prepareExpressions();
        if (this.currentTokenType != 93) {
            throw this.getSyntaxError();
        }
        return query;
    }

    public Command prepareCommand(String string) {
        try {
            Prepared prepared = this.parse(string, null);
            if (this.currentTokenType != 115 && this.currentTokenType != 93) {
                this.addExpected(115);
                throw this.getSyntaxError();
            }
            try {
                prepared.prepare();
            }
            catch (Throwable throwable) {
                CommandContainer.clearCTE(this.session, prepared);
                throw throwable;
            }
            int n = this.token.start();
            if (n < string.length()) {
                string = string.substring(0, n);
            }
            CommandContainer commandContainer = new CommandContainer(this.session, string, prepared);
            while (this.currentTokenType == 115) {
                this.read();
            }
            if (this.currentTokenType != 93) {
                int n2 = this.token.start();
                return this.prepareCommandList(commandContainer, prepared, string, this.sqlCommand.substring(n2), this.getRemainingTokens(n2));
            }
            return commandContainer;
        }
        catch (DbException dbException) {
            throw dbException.addSQL(this.sqlCommand);
        }
    }

    private CommandList prepareCommandList(CommandContainer commandContainer, Prepared prepared, String string, String string2, ArrayList<Token> arrayList) {
        try {
            ArrayList<Prepared> arrayList2 = Utils.newSmallArrayList();
            while (true) {
                if (prepared instanceof DefineCommand) {
                    return new CommandList(this.session, string, commandContainer, arrayList2, this.parameters, string2);
                }
                try {
                    prepared = this.parse(string2, arrayList);
                }
                catch (DbException dbException) {
                    if (dbException.getErrorCode() == 90123) {
                        throw dbException;
                    }
                    return new CommandList(this.session, string, commandContainer, arrayList2, this.parameters, string2);
                }
                arrayList2.add(prepared);
                if (this.currentTokenType != 115 && this.currentTokenType != 93) {
                    this.addExpected(115);
                    throw this.getSyntaxError();
                }
                while (this.currentTokenType == 115) {
                    this.read();
                }
                if (this.currentTokenType == 93) break;
                int n = this.token.start();
                string2 = this.sqlCommand.substring(n);
                arrayList = this.getRemainingTokens(n);
            }
            return new CommandList(this.session, string, commandContainer, arrayList2, this.parameters, null);
        }
        catch (Throwable throwable) {
            commandContainer.clearCTE();
            throw throwable;
        }
    }

    Prepared parse(String string, ArrayList<Token> arrayList) {
        Prepared prepared;
        this.initialize(string, arrayList, false);
        try {
            prepared = this.parse(false);
        }
        catch (DbException dbException) {
            if (dbException.getErrorCode() == 42000) {
                this.resetTokenIndex();
                prepared = this.parse(true);
            }
            throw dbException.addSQL(string);
        }
        return prepared;
    }

    private Prepared parse(boolean bl) {
        Prepared prepared;
        this.expectedList = bl ? new ArrayList() : null;
        this.currentSelect = null;
        this.currentPrepared = null;
        this.createView = null;
        this.cteCleanups = null;
        this.recompileAlways = false;
        this.usedParameters.clear();
        this.read();
        try {
            prepared = this.parsePrepared();
            prepared.setCteCleanups(this.cteCleanups);
        }
        catch (Throwable throwable) {
            if (this.cteCleanups != null) {
                CommandContainer.clearCTE(this.session, this.cteCleanups);
            }
            throw throwable;
        }
        prepared.setPrepareAlways(this.recompileAlways);
        prepared.setParameterList(this.parameters);
        return prepared;
    }

    /*
     * Enabled aggressive block sorting
     */
    private Prepared parsePrepared() {
        int n = this.tokenIndex;
        Prepared prepared = null;
        switch (this.currentTokenType) {
            case 93: 
            case 115: {
                prepared = new NoOperation(this.session);
                this.setSQL(prepared, n);
                return prepared;
            }
            case 92: {
                this.readParameter().setValue(ValueNull.INSTANCE);
                this.read(95);
                n = this.tokenIndex;
                this.read("CALL");
                prepared = this.parseCall();
                break;
            }
            case 69: 
            case 75: 
            case 85: 
            case 105: {
                prepared = this.parseQuery();
                break;
            }
            case 89: {
                this.read();
                prepared = this.parseWithStatementOrQuery(n);
                break;
            }
            case 71: {
                this.read();
                prepared = this.parseSet();
                break;
            }
            case 2: {
                if (this.token.isQuoted()) break;
                switch (this.currentToken.charAt(0) & 0xFFDF) {
                    case 65: {
                        if (this.readIf("ALTER")) {
                            prepared = this.parseAlter();
                            break;
                        }
                        if (!this.readIf("ANALYZE")) break;
                        prepared = this.parseAnalyze();
                        break;
                    }
                    case 66: {
                        if (this.readIf("BACKUP")) {
                            prepared = this.parseBackup();
                            break;
                        }
                        if (!this.readIf("BEGIN")) break;
                        prepared = this.parseBegin();
                        break;
                    }
                    case 67: {
                        if (this.readIf("COMMIT")) {
                            prepared = this.parseCommit();
                            break;
                        }
                        if (this.readIf("CREATE")) {
                            prepared = this.parseCreate();
                            break;
                        }
                        if (this.readIf("CALL")) {
                            prepared = this.parseCall();
                            break;
                        }
                        if (this.readIf("CHECKPOINT")) {
                            prepared = this.parseCheckpoint();
                            break;
                        }
                        if (!this.readIf("COMMENT")) break;
                        prepared = this.parseComment();
                        break;
                    }
                    case 68: {
                        if (this.readIf("DELETE")) {
                            prepared = this.parseDelete(n);
                            break;
                        }
                        if (this.readIf("DROP")) {
                            prepared = this.parseDrop();
                            break;
                        }
                        if (this.readIfCompat("DECLARE")) {
                            prepared = this.parseCreate();
                            break;
                        }
                        if (this.database.getMode().getEnum() == Mode.ModeEnum.MSSQLServer || !this.readIfCompat("DEALLOCATE")) break;
                        prepared = this.parseDeallocate();
                        break;
                    }
                    case 69: {
                        if (this.readIf("EXPLAIN")) {
                            prepared = this.parseExplain();
                            break;
                        }
                        if (this.readIf("EXECUTE")) {
                            if (this.readIf("IMMEDIATE")) {
                                prepared = new ExecuteImmediate(this.session, this.readExpression());
                                break;
                            }
                            if (this.database.getMode().getEnum() == Mode.ModeEnum.MSSQLServer) {
                                prepared = this.parseExecuteSQLServer();
                                break;
                            }
                            prepared = this.parseExecutePostgre();
                            break;
                        }
                        if (this.database.getMode().getEnum() != Mode.ModeEnum.MSSQLServer || !this.readIfCompat("EXEC")) break;
                        prepared = this.parseExecuteSQLServer();
                        break;
                    }
                    case 71: {
                        if (!this.readIf("GRANT")) break;
                        prepared = this.parseGrantRevoke(49);
                        break;
                    }
                    case 72: {
                        if (!this.readIf("HELP")) break;
                        prepared = this.parseHelp();
                        break;
                    }
                    case 73: {
                        if (!this.readIf("INSERT")) break;
                        prepared = this.parseInsert(n);
                        break;
                    }
                    case 77: {
                        if (!this.readIf("MERGE")) break;
                        prepared = this.parseMerge(n);
                        break;
                    }
                    case 80: {
                        if (!this.readIf("PREPARE")) break;
                        prepared = this.parsePrepare();
                        break;
                    }
                    case 82: {
                        if (this.readIf("ROLLBACK")) {
                            prepared = this.parseRollback();
                            break;
                        }
                        if (this.readIf("REVOKE")) {
                            prepared = this.parseGrantRevoke(50);
                            break;
                        }
                        if (this.readIf("RUNSCRIPT")) {
                            prepared = this.parseRunScript();
                            break;
                        }
                        if (this.readIf("RELEASE")) {
                            prepared = this.parseReleaseSavepoint();
                            break;
                        }
                        if (this.database.getMode().replaceInto && this.readIfCompat("REPLACE")) {
                            prepared = this.parseReplace(n);
                            break;
                        }
                        if (!this.readIf("REFRESH")) break;
                        prepared = this.parseRefresh(n);
                        break;
                    }
                    case 83: {
                        if (this.readIf("SAVEPOINT")) {
                            prepared = this.parseSavepoint();
                            break;
                        }
                        if (this.readIf("SCRIPT")) {
                            prepared = this.parseScript();
                            break;
                        }
                        if (this.readIf("SHUTDOWN")) {
                            prepared = this.parseShutdown();
                            break;
                        }
                        if (!this.readIfCompat("SHOW")) break;
                        prepared = this.parseShow();
                        break;
                    }
                    case 84: {
                        if (!this.readIf("TRUNCATE")) break;
                        prepared = this.parseTruncate();
                        break;
                    }
                    case 85: {
                        if (this.readIf("UPDATE")) {
                            prepared = this.parseUpdate(n);
                            break;
                        }
                        if (!this.readIfCompat("USE")) break;
                        prepared = this.parseUse();
                    }
                }
                break;
            }
        }
        if (prepared == null) {
            throw this.getSyntaxError();
        }
        boolean bl = this.readIf(111);
        if (bl) {
            do {
                int n2;
                if ((n2 = (int)this.readLong() - 1) < 0 || n2 >= this.parameters.size()) {
                    throw this.getSyntaxError();
                }
                Parameter parameter = (Parameter)this.parameters.get(n2);
                if (parameter == null) {
                    throw this.getSyntaxError();
                }
                this.read(116);
                Expression expression = this.readExpression();
                expression = expression.optimize(this.session);
                parameter.setValue(expression.getValue(this.session));
            } while (this.readIf(109));
            this.read(112);
            for (Parameter parameter : this.parameters) {
                parameter.checkSet();
            }
            prepared.setWithParamValues(true);
        }
        if (bl || prepared.getSQL() == null) {
            this.setSQL(prepared, n);
        }
        return prepared;
    }

    private Prepared parseBackup() {
        BackupCommand backupCommand = new BackupCommand(this.session);
        this.read(76);
        backupCommand.setFileName(this.readExpression());
        return backupCommand;
    }

    private Prepared parseAnalyze() {
        Analyze analyze = new Analyze(this.session);
        if (this.readIf(75)) {
            Table table = this.readTableOrView();
            analyze.setTable(table);
        }
        if (this.readIf("SAMPLE_SIZE")) {
            analyze.setTop(this.readNonNegativeInt());
        }
        return analyze;
    }

    private TransactionCommand parseBegin() {
        if (!this.readIf("WORK")) {
            this.readIf("TRANSACTION");
        }
        TransactionCommand transactionCommand = new TransactionCommand(this.session, 83);
        return transactionCommand;
    }

    private TransactionCommand parseCommit() {
        if (this.readIf("TRANSACTION")) {
            TransactionCommand transactionCommand = new TransactionCommand(this.session, 78);
            transactionCommand.setTransactionName(this.readIdentifier());
            return transactionCommand;
        }
        TransactionCommand transactionCommand = new TransactionCommand(this.session, 71);
        this.readIf("WORK");
        return transactionCommand;
    }

    private TransactionCommand parseShutdown() {
        int n = 80;
        if (this.readIf("IMMEDIATELY")) {
            n = 81;
        } else if (this.readIf("COMPACT")) {
            n = 82;
        } else if (this.readIf("DEFRAG")) {
            n = 84;
        } else {
            this.readIf("SCRIPT");
        }
        return new TransactionCommand(this.session, n);
    }

    private TransactionCommand parseRollback() {
        TransactionCommand transactionCommand;
        if (this.readIf("TRANSACTION")) {
            TransactionCommand transactionCommand2 = new TransactionCommand(this.session, 79);
            transactionCommand2.setTransactionName(this.readIdentifier());
            return transactionCommand2;
        }
        this.readIf("WORK");
        if (this.readIf(76, "SAVEPOINT")) {
            transactionCommand = new TransactionCommand(this.session, 75);
            transactionCommand.setSavepointName(this.readIdentifier());
        } else {
            transactionCommand = new TransactionCommand(this.session, 72);
        }
        return transactionCommand;
    }

    private Prepared parsePrepare() {
        if (this.readIf("COMMIT")) {
            TransactionCommand transactionCommand = new TransactionCommand(this.session, 77);
            transactionCommand.setTransactionName(this.readIdentifier());
            return transactionCommand;
        }
        return this.parsePrepareProcedure();
    }

    private Prepared parsePrepareProcedure() {
        Object object;
        if (this.database.getMode().getEnum() == Mode.ModeEnum.MSSQLServer) {
            throw this.getSyntaxError();
        }
        String string = this.readIdentifier();
        if (this.readIf(105)) {
            object = Utils.newSmallArrayList();
            int n = 0;
            while (true) {
                Column column = this.parseColumnForTable("C" + n, true);
                ((ArrayList)object).add(column);
                if (!this.readIfMore()) break;
                ++n;
            }
        }
        this.read(7);
        object = this.parsePrepared();
        PrepareProcedure prepareProcedure = new PrepareProcedure(this.session);
        prepareProcedure.setProcedureName(string);
        prepareProcedure.setPrepared((Prepared)object);
        return prepareProcedure;
    }

    private TransactionCommand parseSavepoint() {
        TransactionCommand transactionCommand = new TransactionCommand(this.session, 74);
        transactionCommand.setSavepointName(this.readIdentifier());
        return transactionCommand;
    }

    private Prepared parseReleaseSavepoint() {
        NoOperation noOperation = new NoOperation(this.session);
        this.readIf("SAVEPOINT");
        this.readIdentifier();
        return noOperation;
    }

    private Schema findSchema(String string) {
        if (string == null) {
            return null;
        }
        Schema schema = this.database.findSchema(string);
        if (schema == null && this.equalsToken("SESSION", string)) {
            schema = this.database.getSchema(this.session.getCurrentSchemaName());
        }
        return schema;
    }

    private Schema getSchema(String string) {
        if (string == null) {
            return null;
        }
        Schema schema = this.findSchema(string);
        if (schema == null) {
            throw DbException.get(90079, string);
        }
        return schema;
    }

    private Schema getSchema() {
        return this.getSchema(this.schemaName);
    }

    private Schema getSchemaWithDefault() {
        if (this.schemaName == null) {
            this.schemaName = this.session.getCurrentSchemaName();
        }
        return this.getSchema(this.schemaName);
    }

    private Column readTableColumn(TableFilter tableFilter) {
        String string = this.readIdentifier();
        if (this.readIf(110)) {
            string = this.readTableColumn(tableFilter, string);
        }
        return tableFilter.getTable().getColumn(string);
    }

    private String readTableColumn(TableFilter tableFilter, String string) {
        String string2 = this.readIdentifier();
        if (this.readIf(110)) {
            String string3 = string;
            string = string2;
            string2 = this.readIdentifier();
            if (this.readIf(110)) {
                this.checkDatabaseName(string3);
                string3 = string;
                string = string2;
                string2 = this.readIdentifier();
            }
            if (!this.equalsToken(string3, tableFilter.getTable().getSchema().getName())) {
                throw DbException.get(90079, string3);
            }
        }
        if (!this.equalsToken(string, tableFilter.getTableAlias())) {
            throw DbException.get(42102, string);
        }
        return string2;
    }

    private DataChangeStatement parseUpdate(int n) {
        Update update = new Update(this.session);
        this.currentPrepared = update;
        Expression expression = null;
        if (this.database.getMode().topInDML && this.readIfCompat("TOP")) {
            this.read(105);
            expression = this.readTerm().optimize(this.session);
            this.read(106);
        }
        TableFilter tableFilter = this.readSimpleTableFilter();
        update.setTableFilter(tableFilter);
        int n2 = this.tokenIndex;
        if (this.database.getMode().discardWithTableHints) {
            this.discardWithTableHints();
        }
        update.setSetClauseList(this.readUpdateSetClause(tableFilter));
        if (this.database.getMode().allowUsingFromClauseInUpdateStatement && this.readIfCompat(35)) {
            this.setTokenIndex(n2);
            return this.parseUpdateFrom(tableFilter, n);
        }
        if (this.readIf(87)) {
            update.setCondition(this.readExpression());
        }
        if (expression == null) {
            this.readIfOrderBy();
            expression = this.readFetchOrLimit();
        }
        update.setFetch(expression);
        this.setSQL(update, n);
        return update;
    }

    private MergeUsing parseUpdateFrom(TableFilter tableFilter, int n) {
        MergeUsing mergeUsing = new MergeUsing(this.session, tableFilter);
        this.currentPrepared = mergeUsing;
        SetClauseList setClauseList = this.readUpdateSetClause(tableFilter);
        this.read(35);
        mergeUsing.setSourceTableFilter(this.readTableReference());
        mergeUsing.setOnCondition(this.readIf(87) ? this.readExpression() : ValueExpression.TRUE);
        MergeUsing.WhenMatchedThenUpdate whenMatchedThenUpdate = new MergeUsing.WhenMatchedThenUpdate(mergeUsing);
        whenMatchedThenUpdate.setSetClauseList(setClauseList);
        mergeUsing.addWhen(whenMatchedThenUpdate);
        this.setSQL(mergeUsing, n);
        return mergeUsing;
    }

    private SetClauseList readUpdateSetClause(TableFilter tableFilter) {
        this.read(71);
        SetClauseList setClauseList = new SetClauseList(tableFilter.getTable());
        do {
            Expression[] expressionArray;
            Object object;
            if (this.readIf(105)) {
                object = Utils.newSmallArrayList();
                expressionArray = Utils.newSmallArrayList();
                do {
                    ((ArrayList)object).add(this.readTableColumn(tableFilter));
                    expressionArray.add(this.readUpdateSetClauseArrayIndexes());
                } while (this.readIfMore());
                this.read(95);
                setClauseList.addMultiple((ArrayList<Column>)object, (ArrayList<Expression[]>)expressionArray, this.readExpression());
                continue;
            }
            object = this.readTableColumn(tableFilter);
            expressionArray = this.readUpdateSetClauseArrayIndexes();
            this.read(95);
            setClauseList.addSingle((Column)object, expressionArray, expressionArray == null ? this.readExpressionOrDefault() : this.readExpression());
        } while (this.readIf(109));
        return setClauseList;
    }

    private Expression[] readUpdateSetClauseArrayIndexes() {
        if (this.readIf(117)) {
            ArrayList<Expression> arrayList = Utils.newSmallArrayList();
            do {
                arrayList.add(this.readExpression());
                this.read(118);
            } while (this.readIf(117));
            return arrayList.toArray(new Expression[0]);
        }
        return null;
    }

    private TableFilter readSimpleTableFilter() {
        return new TableFilter(this.session, this.readTableOrView(), this.readFromAlias(null), this.rightsChecked, this.currentSelect, 0, null);
    }

    private Delete parseDelete(int n) {
        Delete delete = new Delete(this.session);
        Expression expression = null;
        if (this.database.getMode().topInDML && this.readIfCompat("TOP")) {
            expression = this.readTerm().optimize(this.session);
        }
        this.currentPrepared = delete;
        if (!this.readIf(35) && this.database.getMode().deleteIdentifierFrom) {
            this.readIdentifierWithSchema();
            this.read(35);
        }
        delete.setTableFilter(this.readSimpleTableFilter());
        if (this.readIf(87)) {
            delete.setCondition(this.readExpression());
        }
        if (expression == null) {
            expression = this.readFetchOrLimit();
        }
        delete.setFetch(expression);
        this.setSQL(delete, n);
        return delete;
    }

    private Expression readFetchOrLimit() {
        Expression expression = null;
        if (this.readIf(32)) {
            if (!this.readIf("FIRST")) {
                this.read("NEXT");
            }
            if (this.readIf(66) || this.readIf("ROWS")) {
                expression = ValueExpression.get(ValueInteger.get(1));
            } else {
                expression = this.readExpression().optimize(this.session);
                if (!this.readIf(66)) {
                    this.read("ROWS");
                }
            }
            this.read("ONLY");
        } else if (this.database.getMode().limit && this.readIfCompat(50)) {
            expression = this.readTerm().optimize(this.session);
        }
        return expression;
    }

    private IndexColumn[] parseIndexColumnList() {
        ArrayList<IndexColumn> arrayList = Utils.newSmallArrayList();
        do {
            arrayList.add(new IndexColumn(this.readIdentifier(), this.parseSortType()));
        } while (this.readIfMore());
        return arrayList.toArray(new IndexColumn[0]);
    }

    private int parseSortType() {
        int n;
        int n2 = n = !this.readIf("ASC") && this.readIf("DESC") ? 1 : 0;
        if (this.readIf("NULLS")) {
            if (this.readIf("FIRST")) {
                n |= 2;
            } else {
                this.read("LAST");
                n |= 4;
            }
        }
        return n;
    }

    private String[] parseColumnList() {
        ArrayList<String> arrayList = Utils.newSmallArrayList();
        do {
            arrayList.add(this.readIdentifier());
        } while (this.readIfMore());
        return arrayList.toArray(new String[0]);
    }

    private Column[] parseColumnList(Table table) {
        ArrayList<Column> arrayList = Utils.newSmallArrayList();
        HashSet<Column> hashSet = new HashSet<Column>();
        if (!this.readIf(106)) {
            do {
                Column column;
                if (!hashSet.add(column = this.parseColumn(table))) {
                    throw DbException.get(42121, column.getTraceSQL());
                }
                arrayList.add(column);
            } while (this.readIfMore());
        }
        return arrayList.toArray(new Column[0]);
    }

    private Column parseColumn(Table table) {
        if (this.currentTokenType == 91) {
            this.read();
            return table.getRowIdColumn();
        }
        return table.getColumn(this.readIdentifier());
    }

    private Prepared parseHelp() {
        HashSet<String> hashSet = new HashSet<String>();
        while (this.currentTokenType != 93) {
            hashSet.add(StringUtils.toUpperEnglish(this.readIdentifierOrKeyword()));
        }
        return new Help(this.session, hashSet.toArray(new String[0]));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Prepared parseShow() {
        Object object;
        Object object2;
        StringBuilder stringBuilder = new StringBuilder("SELECT ");
        if (this.readIf("CLIENT_ENCODING")) {
            stringBuilder.append("'UNICODE' CLIENT_ENCODING");
        } else if (this.readIf("DEFAULT_TRANSACTION_ISOLATION")) {
            stringBuilder.append("'read committed' DEFAULT_TRANSACTION_ISOLATION");
        } else if (this.readIf("TRANSACTION")) {
            this.read("ISOLATION");
            this.read("LEVEL");
            stringBuilder.append("LOWER(ISOLATION_LEVEL) TRANSACTION_ISOLATION FROM INFORMATION_SCHEMA.SESSIONS WHERE SESSION_ID = SESSION_ID()");
        } else if (this.readIf("DATESTYLE")) {
            stringBuilder.append("'ISO' DATESTYLE");
        } else if (this.readIf("SEARCH_PATH")) {
            object2 = this.session.getSchemaSearchPath();
            object = new StringBuilder();
            if (object2 != null) {
                for (int i = 0; i < ((String[])object2).length; ++i) {
                    if (i > 0) {
                        ((StringBuilder)object).append(", ");
                    }
                    ParserUtil.quoteIdentifier((StringBuilder)object, object2[i], 1);
                }
            }
            StringUtils.quoteStringSQL(stringBuilder, ((StringBuilder)object).toString());
            stringBuilder.append(" SEARCH_PATH");
        } else if (this.readIf("SERVER_VERSION")) {
            stringBuilder.append("'8.2.23' SERVER_VERSION");
        } else if (this.readIf("SERVER_ENCODING")) {
            stringBuilder.append("'UTF8' SERVER_ENCODING");
        } else if (this.readIf("SSL")) {
            stringBuilder.append("'off' SSL");
        } else if (this.readIf("TABLES")) {
            object2 = this.database.getMainSchema().getName();
            if (this.readIf(35)) {
                object2 = this.readIdentifier();
            }
            stringBuilder.append("TABLE_NAME, TABLE_SCHEMA FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA=");
            StringUtils.quoteStringSQL(stringBuilder, (String)object2).append(" ORDER BY TABLE_NAME");
        } else if (this.readIf("COLUMNS")) {
            this.read(35);
            object2 = this.readIdentifierWithSchema();
            object = this.getSchema().getName();
            if (this.readIf(35)) {
                object = this.readIdentifier();
            }
            stringBuilder.append("C.COLUMN_NAME FIELD, ");
            boolean bl = this.session.isOldInformationSchema();
            if (bl) {
                stringBuilder.append("C.COLUMN_TYPE");
            } else {
                stringBuilder.append("DATA_TYPE_SQL(");
                StringUtils.quoteStringSQL(stringBuilder, (String)object).append(", ");
                StringUtils.quoteStringSQL(stringBuilder, (String)object2).append(", 'TABLE', C.DTD_IDENTIFIER)");
            }
            stringBuilder.append(" TYPE, C.IS_NULLABLE \"NULL\", CASE (SELECT MAX(I.INDEX_TYPE_NAME) FROM INFORMATION_SCHEMA.INDEXES I ");
            if (!bl) {
                stringBuilder.append("JOIN INFORMATION_SCHEMA.INDEX_COLUMNS IC ");
            }
            stringBuilder.append("WHERE I.TABLE_SCHEMA=C.TABLE_SCHEMA AND I.TABLE_NAME=C.TABLE_NAME ");
            if (bl) {
                stringBuilder.append("AND I.COLUMN_NAME=C.COLUMN_NAME");
            } else {
                stringBuilder.append("AND IC.TABLE_SCHEMA=C.TABLE_SCHEMA AND IC.TABLE_NAME=C.TABLE_NAME AND IC.INDEX_SCHEMA=I.INDEX_SCHEMA AND IC.INDEX_NAME=I.INDEX_NAME AND IC.COLUMN_NAME=C.COLUMN_NAME");
            }
            stringBuilder.append(")WHEN 'PRIMARY KEY' THEN 'PRI' WHEN 'UNIQUE INDEX' THEN 'UNI' ELSE '' END `KEY`, COALESCE(COLUMN_DEFAULT, 'NULL') `DEFAULT` FROM INFORMATION_SCHEMA.COLUMNS C WHERE C.TABLE_SCHEMA=");
            StringUtils.quoteStringSQL(stringBuilder, (String)object).append(" AND C.TABLE_NAME=");
            StringUtils.quoteStringSQL(stringBuilder, (String)object2).append(" ORDER BY C.ORDINAL_POSITION");
        } else if (this.readIf("DATABASES") || this.readIf("SCHEMAS")) {
            stringBuilder.append("SCHEMA_NAME FROM INFORMATION_SCHEMA.SCHEMATA");
        } else if (this.database.getMode().getEnum() == Mode.ModeEnum.PostgreSQL && this.readIf(3)) {
            stringBuilder.append("NAME, SETTING FROM PG_CATALOG.PG_SETTINGS");
        }
        boolean bl = this.session.getAllowLiterals();
        try {
            this.session.setAllowLiterals(true);
            object = this.session.prepare(stringBuilder.toString());
            return object;
        }
        finally {
            this.session.setAllowLiterals(bl);
        }
    }

    private boolean isDerivedTable() {
        int n = this.tokenIndex;
        int n2 = 0;
        while (((Token)this.tokens.get(n)).tokenType() == 105) {
            ++n2;
            ++n;
        }
        boolean bl = this.isDirectQuery(n);
        if (bl && n2 > 0) {
            if ((n = this.scanToCloseParen(n + 1)) < 0) {
                bl = false;
            } else {
                block7: while (true) {
                    switch (((Token)this.tokens.get(n)).tokenType()) {
                        case 93: 
                        case 115: {
                            bl = false;
                            break block7;
                        }
                        case 105: {
                            if ((n = this.scanToCloseParen(n + 1)) >= 0) continue block7;
                            bl = false;
                            break block7;
                        }
                        case 106: {
                            if (--n2 == 0) break block7;
                            ++n;
                            continue block7;
                        }
                        case 46: {
                            bl = false;
                            break block7;
                        }
                        default: {
                            ++n;
                            continue block7;
                        }
                    }
                    break;
                }
            }
        }
        return bl;
    }

    private boolean isQuery() {
        int n = this.tokenIndex;
        int n2 = 0;
        while (((Token)this.tokens.get(n)).tokenType() == 105) {
            ++n2;
            ++n;
        }
        boolean bl = this.isDirectQuery(n);
        if (bl && n2 > 0) {
            ++n;
            block4: while (true) {
                if ((n = this.scanToCloseParen(n)) < 0) {
                    bl = false;
                    break;
                }
                switch (((Token)this.tokens.get(n)).tokenType()) {
                    default: {
                        bl = false;
                        break block4;
                    }
                    case 29: 
                    case 32: 
                    case 43: 
                    case 50: 
                    case 53: 
                    case 59: 
                    case 62: 
                    case 79: 
                    case 93: 
                    case 106: 
                    case 115: {
                        if (--n2 > 0) continue block4;
                    }
                }
                break;
            }
        }
        return bl;
    }

    private int scanToCloseParen(int n) {
        int n2 = 0;
        while (true) {
            switch (((Token)this.tokens.get(n)).tokenType()) {
                case 93: 
                case 115: {
                    return -1;
                }
                case 105: {
                    ++n2;
                    break;
                }
                case 106: {
                    if (--n2 >= 0) break;
                    return n + 1;
                }
            }
            ++n;
        }
    }

    private boolean isQueryQuick() {
        int n = this.tokenIndex;
        while (((Token)this.tokens.get(n)).tokenType() == 105) {
            ++n;
        }
        return this.isDirectQuery(n);
    }

    private boolean isDirectQuery(int n) {
        boolean bl;
        switch (((Token)this.tokens.get(n)).tokenType()) {
            case 69: 
            case 85: 
            case 89: {
                bl = true;
                break;
            }
            case 75: {
                bl = ((Token)this.tokens.get(n + 1)).tokenType() != 105;
                break;
            }
            default: {
                bl = false;
            }
        }
        return bl;
    }

    private Prepared parseMerge(int n) {
        this.read("INTO");
        TableFilter tableFilter = this.readSimpleTableFilter();
        if (this.readIf(83)) {
            return this.parseMergeUsing(tableFilter, n);
        }
        return this.parseMergeInto(tableFilter, n);
    }

    private Prepared parseMergeInto(TableFilter tableFilter, int n) {
        Merge merge = new Merge(this.session, false);
        this.currentPrepared = merge;
        merge.setTable(tableFilter.getTable());
        Table table = merge.getTable();
        if (this.readIf(105)) {
            if (this.isQueryQuick()) {
                merge.setQuery(this.parseQuery());
                this.read(106);
                return merge;
            }
            merge.setColumns(this.parseColumnList(table));
        }
        if (this.readIf(47, 105)) {
            merge.setKeys(this.parseColumnList(table));
        }
        if (this.readIf(85)) {
            this.parseValuesForCommand(merge);
        } else {
            merge.setQuery(this.parseQuery());
        }
        this.setSQL(merge, n);
        return merge;
    }

    private MergeUsing parseMergeUsing(TableFilter tableFilter, int n) {
        MergeUsing mergeUsing = new MergeUsing(this.session, tableFilter);
        this.currentPrepared = mergeUsing;
        mergeUsing.setSourceTableFilter(this.readTableReference());
        this.read(60);
        Expression expression = this.readExpression();
        mergeUsing.setOnCondition(expression);
        this.read(86);
        do {
            boolean bl;
            if (bl = this.readIf("MATCHED")) {
                this.parseWhenMatched(mergeUsing);
                continue;
            }
            this.parseWhenNotMatched(mergeUsing);
        } while (this.readIf(86));
        this.setSQL(mergeUsing, n);
        return mergeUsing;
    }

    private void parseWhenMatched(MergeUsing mergeUsing) {
        MergeUsing.When when;
        Expression expression = this.readIf(4) ? this.readExpression() : null;
        this.read("THEN");
        if (this.readIf("UPDATE")) {
            MergeUsing.WhenMatchedThenUpdate whenMatchedThenUpdate = new MergeUsing.WhenMatchedThenUpdate(mergeUsing);
            whenMatchedThenUpdate.setSetClauseList(this.readUpdateSetClause(mergeUsing.getTargetTableFilter()));
            when = whenMatchedThenUpdate;
        } else {
            this.read("DELETE");
            when = new MergeUsing.WhenMatchedThenDelete(mergeUsing);
        }
        if (expression == null && this.database.getMode().mergeWhere && this.readIf(87)) {
            expression = this.readExpression();
        }
        when.setAndCondition(expression);
        mergeUsing.addWhen(when);
    }

    private void parseWhenNotMatched(MergeUsing mergeUsing) {
        this.read(57);
        this.read("MATCHED");
        Expression expression = this.readIf(4) ? this.readExpression() : null;
        this.read("THEN");
        this.read("INSERT");
        Column[] columnArray = this.readIf(105) ? this.parseColumnList(mergeUsing.getTargetTableFilter().getTable()) : null;
        Boolean bl = this.readIfOverriding();
        this.read(85);
        this.read(105);
        ArrayList<Expression> arrayList = Utils.newSmallArrayList();
        if (!this.readIf(106)) {
            do {
                arrayList.add(this.readExpressionOrDefault());
            } while (this.readIfMore());
        }
        MergeUsing mergeUsing2 = mergeUsing;
        mergeUsing2.getClass();
        MergeUsing.WhenNotMatched whenNotMatched = new MergeUsing.WhenNotMatched(mergeUsing2, columnArray, bl, arrayList.toArray(new Expression[0]));
        whenNotMatched.setAndCondition(expression);
        mergeUsing.addWhen(whenNotMatched);
    }

    /*
     * Unable to fully structure code
     */
    private Insert parseInsert(int var1_1) {
        var2_2 = new Insert(this.session);
        this.currentPrepared = var2_2;
        var3_3 = this.database.getMode();
        if (var3_3.onDuplicateKeyUpdate && this.readIfCompat("IGNORE")) {
            var2_2.setIgnore(true);
        }
        this.read("INTO");
        var4_4 = this.readTableOrView();
        var2_2.setTable(var4_4);
        var5_5 = null;
        if (this.readIf(105)) {
            if (this.isQueryQuick()) {
                var2_2.setQuery(this.parseQuery());
                this.read(106);
                return var2_2;
            }
            var5_5 = this.parseColumnList(var4_4);
            var2_2.setColumns(var5_5);
        }
        var6_6 = this.readIfOverriding();
        var2_2.setOverridingSystem(var6_6);
        var7_7 = false;
        if (this.readIf("DIRECT")) {
            var7_7 = true;
            var2_2.setInsertFromSelect(true);
        }
        if (this.readIfCompat("SORTED")) {
            var7_7 = true;
        }
        if (var7_7) ** GOTO lbl-1000
        if (var6_6 == null && this.readIf(25, 85)) {
            var2_2.addRow(new Expression[0]);
        } else if (this.readIf(85)) {
            this.parseValuesForCommand(var2_2);
        } else if (this.readIf(71)) {
            this.parseInsertSet(var2_2, var4_4, var5_5);
        } else lbl-1000:
        // 2 sources

        {
            var2_2.setQuery(this.parseQuery());
        }
        if (var3_3.onDuplicateKeyUpdate || var3_3.insertOnConflict || var3_3.isolationLevelInSelectOrInsertStatement) {
            this.parseInsertCompatibility(var2_2, var4_4, var3_3);
        }
        this.setSQL(var2_2, var1_1);
        return var2_2;
    }

    private Boolean readIfOverriding() {
        Boolean bl = null;
        if (this.readIf("OVERRIDING", 82, 84)) {
            bl = Boolean.FALSE;
        } else if (this.readIf("OVERRIDING", "SYSTEM", 84)) {
            bl = Boolean.TRUE;
        }
        return bl;
    }

    private void parseInsertSet(Insert insert, Table table, Column[] columnArray) {
        if (columnArray != null) {
            throw this.getSyntaxError();
        }
        ArrayList<Column> arrayList = Utils.newSmallArrayList();
        ArrayList<Expression> arrayList2 = Utils.newSmallArrayList();
        do {
            arrayList.add(this.parseColumn(table));
            this.read(95);
            arrayList2.add(this.readExpressionOrDefault());
        } while (this.readIf(109));
        insert.setColumns(arrayList.toArray(new Column[0]));
        insert.addRow(arrayList2.toArray(new Expression[0]));
    }

    private void parseInsertCompatibility(Insert insert, Table table, Mode mode) {
        if (mode.onDuplicateKeyUpdate && this.readIfCompat(60, "DUPLICATE", 47, "UPDATE")) {
            do {
                Object object;
                String string = this.readIdentifier();
                if (this.readIf(110)) {
                    object = string;
                    Object object2 = this.readIdentifier();
                    if (this.readIf(110)) {
                        if (!table.getSchema().getName().equals(object)) {
                            throw DbException.get(90080);
                        }
                        string = this.readIdentifier();
                    } else {
                        string = object2;
                        object2 = object;
                    }
                    if (!table.getName().equals(object2)) {
                        throw DbException.get(42102, (String)object2);
                    }
                }
                object = table.getColumn(string);
                this.read(95);
                insert.addAssignmentForDuplicate((Column)object, this.readExpressionOrDefault());
            } while (this.readIf(109));
        }
        if (mode.insertOnConflict && this.readIfCompat(60, "CONFLICT", "DO", "NOTHING")) {
            insert.setIgnore(true);
        }
        if (mode.isolationLevelInSelectOrInsertStatement) {
            this.parseIsolationClause();
        }
    }

    private Merge parseReplace(int n) {
        Merge merge = new Merge(this.session, true);
        this.currentPrepared = merge;
        this.read("INTO");
        Table table = this.readTableOrView();
        merge.setTable(table);
        if (this.readIf(105)) {
            if (this.isQueryQuick()) {
                merge.setQuery(this.parseQuery());
                this.read(106);
                return merge;
            }
            merge.setColumns(this.parseColumnList(table));
        }
        if (this.readIf(85)) {
            this.parseValuesForCommand(merge);
        } else {
            merge.setQuery(this.parseQuery());
        }
        this.setSQL(merge, n);
        return merge;
    }

    private RefreshMaterializedView parseRefresh(int n) {
        this.read("MATERIALIZED");
        this.read("VIEW");
        Table table = this.readTableOrView(false);
        if (!(table instanceof MaterializedView)) {
            throw DbException.get(90037, table.getName());
        }
        RefreshMaterializedView refreshMaterializedView = new RefreshMaterializedView(this.session, this.getSchema());
        this.currentPrepared = refreshMaterializedView;
        refreshMaterializedView.setView((MaterializedView)table);
        this.setSQL(refreshMaterializedView, n);
        return refreshMaterializedView;
    }

    private void parseValuesForCommand(CommandWithValues commandWithValues) {
        ArrayList<Expression> arrayList = Utils.newSmallArrayList();
        do {
            arrayList.clear();
            boolean bl = this.readIf(66, 105) ? true : this.readIf(105);
            if (bl) {
                if (!this.readIf(106)) {
                    do {
                        arrayList.add(this.readExpressionOrDefault());
                    } while (this.readIfMore());
                }
            } else {
                arrayList.add(this.readExpressionOrDefault());
            }
            commandWithValues.addRow(arrayList.toArray(new Expression[0]));
        } while (this.readIf(109));
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    private TableFilter readTablePrimary() {
        void var1_18;
        Object object;
        String string;
        block8: {
            block15: {
                Schema schema;
                block14: {
                    block13: {
                        block12: {
                            int n;
                            boolean bl;
                            block11: {
                                block10: {
                                    block9: {
                                        string = null;
                                        if (this.readIf(105)) {
                                            if (this.isDerivedTable()) {
                                                return this.readDerivedTableWithCorrelation();
                                            }
                                            TableFilter tableFilter = this.readTableReference();
                                            this.read(106);
                                            return this.readCorrelation(tableFilter);
                                        }
                                        if (!this.readIf(85)) break block9;
                                        BitSet bitSet = this.openParametersScope();
                                        object = this.parseValues();
                                        string = this.session.getNextSystemIdentifier(this.sqlCommand);
                                        Table table = ((TableValueConstructor)object).toTable(string, null, this.closeParametersScope(bitSet), this.createView != null, this.currentSelect);
                                        break block8;
                                    }
                                    if (!this.readIf(75, 105)) break block10;
                                    ArrayTableFunction arrayTableFunction = this.readTableFunction(1);
                                    FunctionTable functionTable = new FunctionTable(this.database.getMainSchema(), this.session, arrayTableFunction);
                                    break block8;
                                }
                                bl = this.token.isQuoted();
                                object = this.readIdentifier();
                                n = this.tokenIndex;
                                this.schemaName = null;
                                if (!this.readIf(110)) break block11;
                                object = this.readIdentifierWithSchema2((String)object);
                                break block12;
                            }
                            if (bl || !this.readIf(75, 105)) break block12;
                            Table table = this.readDataChangeDeltaTable(this.upperName((String)object), n);
                            break block8;
                        }
                        if (this.schemaName != null) break block13;
                        schema = null;
                        break block14;
                    }
                    schema = this.findSchema(this.schemaName);
                    if (schema != null) break block14;
                    if (!this.isDualTable((String)object)) throw DbException.get(90079, this.schemaName);
                    DualTable dualTable = new DualTable(this.database);
                    break block8;
                }
                boolean bl = this.readIf(105);
                if (bl && this.readIfCompat("INDEX")) {
                    this.readIdentifierWithSchema(null);
                    this.read(106);
                    bl = false;
                }
                if (!bl) break block15;
                Schema schema2 = this.database.getMainSchema();
                if (this.equalsToken((String)object, "SYSTEM_RANGE") || this.equalsToken((String)object, "GENERATE_SERIES")) {
                    Expression expression = this.readExpression();
                    this.read(109);
                    Expression expression2 = this.readExpression();
                    if (this.readIf(109)) {
                        Expression expression3 = this.readExpression();
                        this.read(106);
                        RangeTable rangeTable = new RangeTable(schema2, expression, expression2, expression3);
                        break block8;
                    } else {
                        this.read(106);
                        RangeTable rangeTable = new RangeTable(schema2, expression, expression2);
                    }
                    break block8;
                } else {
                    FunctionTable functionTable = new FunctionTable(schema2, this.session, this.readTableFunction((String)object, schema));
                }
                break block8;
            }
            Table table = this.readTableOrView((String)object, true);
        }
        ArrayList<String> arrayList = null;
        object = null;
        if (this.readIfUseIndex()) {
            object = this.parseIndexHints((Table)var1_18);
            return this.buildTableFilter((Table)var1_18, string, arrayList, (IndexHints)object);
        }
        if ((string = this.readFromAlias(string)) == null) return this.buildTableFilter((Table)var1_18, string, arrayList, (IndexHints)object);
        arrayList = this.readDerivedColumnNames();
        if (!this.readIfUseIndex()) return this.buildTableFilter((Table)var1_18, string, arrayList, (IndexHints)object);
        object = this.parseIndexHints((Table)var1_18);
        return this.buildTableFilter((Table)var1_18, string, arrayList, (IndexHints)object);
    }

    private TableFilter readCorrelation(TableFilter tableFilter) {
        String string = this.readFromAlias(null);
        if (string != null) {
            tableFilter.setAlias(string);
            ArrayList<String> arrayList = this.readDerivedColumnNames();
            if (arrayList != null) {
                tableFilter.setDerivedColumns(arrayList);
            }
        }
        return tableFilter;
    }

    private TableFilter readDerivedTableWithCorrelation() {
        Table table;
        String string;
        BitSet bitSet = this.openParametersScope();
        Query query = this.parseQueryExpression();
        ArrayList<Parameter> arrayList = this.closeParametersScope(bitSet);
        this.read(106);
        ArrayList<String> arrayList2 = null;
        IndexHints indexHints = null;
        if (this.readIfUseIndex()) {
            string = this.session.getNextSystemIdentifier(this.sqlCommand);
            table = query.toTable(string, null, arrayList, this.createView != null, this.currentSelect);
            indexHints = this.parseIndexHints(table);
        } else {
            string = this.readFromAlias(null);
            if (string != null) {
                arrayList2 = this.readDerivedColumnNames();
                Column[] columnArray = null;
                if (arrayList2 != null) {
                    query.init();
                    columnArray = QueryExpressionTable.createQueryColumnTemplateList(arrayList2.toArray(new String[0]), query, new String[1]).toArray(new Column[0]);
                }
                table = query.toTable(string, columnArray, arrayList, this.createView != null, this.currentSelect);
                if (this.readIfUseIndex()) {
                    indexHints = this.parseIndexHints(table);
                }
            } else {
                string = this.session.getNextSystemIdentifier(this.sqlCommand);
                table = query.toTable(string, null, arrayList, this.createView != null, this.currentSelect);
            }
        }
        return this.buildTableFilter(table, string, arrayList2, indexHints);
    }

    private TableFilter buildTableFilter(Table table, String string, ArrayList<String> arrayList, IndexHints indexHints) {
        if (this.database.getMode().discardWithTableHints) {
            this.discardWithTableHints();
        }
        if (string == null && table.isView() && table.isTableExpression()) {
            string = table.getName();
        }
        TableFilter tableFilter = new TableFilter(this.session, table, string, this.rightsChecked, this.currentSelect, this.orderInFrom++, indexHints);
        if (arrayList != null) {
            tableFilter.setDerivedColumns(arrayList);
        }
        return tableFilter;
    }

    private Table readDataChangeDeltaTable(String string, int n) {
        DataChangeStatement dataChangeStatement;
        int n2 = this.tokenIndex;
        DataChangeDeltaTable.ResultOption resultOption = DataChangeDeltaTable.ResultOption.FINAL;
        switch (string) {
            case "OLD": {
                resultOption = DataChangeDeltaTable.ResultOption.OLD;
                if (this.readIf("UPDATE")) {
                    dataChangeStatement = this.parseUpdate(n2);
                    break;
                }
                if (this.readIf("DELETE")) {
                    dataChangeStatement = this.parseDelete(n2);
                    break;
                }
                if (this.readIf("MERGE")) {
                    dataChangeStatement = (DataChangeStatement)this.parseMerge(n2);
                    break;
                }
                if (this.database.getMode().replaceInto && this.readIfCompat("REPLACE")) {
                    dataChangeStatement = this.parseReplace(n2);
                    break;
                }
                throw this.getSyntaxError();
            }
            case "NEW": {
                resultOption = DataChangeDeltaTable.ResultOption.NEW;
            }
            case "FINAL": {
                if (this.readIf("INSERT")) {
                    dataChangeStatement = this.parseInsert(n2);
                    break;
                }
                if (this.readIf("UPDATE")) {
                    dataChangeStatement = this.parseUpdate(n2);
                    break;
                }
                if (this.readIf("MERGE")) {
                    dataChangeStatement = (DataChangeStatement)this.parseMerge(n2);
                    break;
                }
                if (this.database.getMode().replaceInto && this.readIfCompat("REPLACE")) {
                    dataChangeStatement = this.parseReplace(n2);
                    break;
                }
                throw this.getSyntaxError();
            }
            default: {
                this.setTokenIndex(n);
                this.addExpected("OLD TABLE");
                this.addExpected("NEW TABLE");
                this.addExpected("FINAL TABLE");
                throw this.getSyntaxError();
            }
        }
        this.read(106);
        if (this.currentSelect != null) {
            this.currentSelect.setNeverLazy(true);
        }
        return new DataChangeDeltaTable(this.getSchemaWithDefault(), this.session, dataChangeStatement, resultOption);
    }

    private TableFunction readTableFunction(String string, Schema schema) {
        Object object;
        if (schema == null) {
            switch (this.upperName(string)) {
                case "UNNEST": {
                    return this.readUnnestFunction();
                }
                case "TABLE_DISTINCT": {
                    return this.readTableFunction(2);
                }
                case "CSVREAD": {
                    this.recompileAlways = true;
                    return this.readParameters(new CSVReadFunction());
                }
                case "LINK_SCHEMA": {
                    this.recompileAlways = true;
                    return this.readParameters(new LinkSchemaFunction());
                }
            }
        }
        if (!((FunctionAlias)(object = this.getFunctionAliasWithinPath(string, schema))).isDeterministic()) {
            this.recompileAlways = true;
        }
        ArrayList<Expression> arrayList = Utils.newSmallArrayList();
        if (!this.readIf(106)) {
            do {
                arrayList.add(this.readExpression());
            } while (this.readIfMore());
        }
        return new JavaTableFunction((FunctionAlias)object, arrayList.toArray(new Expression[0]));
    }

    private boolean readIfUseIndex() {
        int n = this.tokenIndex;
        if (!this.readIf("USE")) {
            return false;
        }
        if (!this.readIf("INDEX")) {
            this.setTokenIndex(n);
            return false;
        }
        return true;
    }

    private IndexHints parseIndexHints(Table table) {
        this.read(105);
        LinkedHashSet<String> linkedHashSet = new LinkedHashSet<String>();
        if (!this.readIf(106)) {
            do {
                String string = this.readIdentifierWithSchema();
                Index index = table.getIndex(string);
                linkedHashSet.add(index.getName());
            } while (this.readIfMore());
        }
        return IndexHints.createUseIndexHints(linkedHashSet);
    }

    private String readFromAlias(String string) {
        if (this.readIf(7) || this.isIdentifier()) {
            string = this.readIdentifier();
        }
        return string;
    }

    private ArrayList<String> readDerivedColumnNames() {
        if (this.readIf(105)) {
            ArrayList<String> arrayList = new ArrayList<String>();
            do {
                arrayList.add(this.readIdentifier());
            } while (this.readIfMore());
            return arrayList;
        }
        return null;
    }

    private void discardWithTableHints() {
        if (this.readIfCompat(89, 105)) {
            do {
                this.discardTableHint();
            } while (this.readIfMore());
        }
    }

    private void discardTableHint() {
        if (this.readIfCompat("INDEX")) {
            if (this.readIf(105)) {
                do {
                    this.readExpression();
                } while (this.readIfMore());
            } else {
                this.read(95);
                this.readExpression();
            }
        } else {
            this.readExpression();
        }
    }

    private Prepared parseTruncate() {
        this.read(75);
        Table table = this.readTableOrView();
        boolean bl = this.database.getMode().truncateTableRestartIdentity;
        if (this.readIf("CONTINUE", "IDENTITY")) {
            bl = false;
        } else if (this.readIf("RESTART", "IDENTITY")) {
            bl = true;
        }
        TruncateTable truncateTable = new TruncateTable(this.session);
        truncateTable.setTable(table);
        truncateTable.setRestart(bl);
        return truncateTable;
    }

    private boolean readIfExists(boolean bl) {
        if (this.readIf(40, 30)) {
            bl = true;
        }
        return bl;
    }

    private Prepared parseComment() {
        String string;
        int n = 0;
        this.read(60);
        boolean bl = false;
        if (this.readIf(75) || this.readIf("VIEW")) {
            n = 0;
        } else if (this.readIf("COLUMN")) {
            bl = true;
            n = 0;
        } else if (this.readIf("CONSTANT")) {
            n = 11;
        } else if (this.readIf(14)) {
            n = 5;
        } else if (this.readIf("ALIAS")) {
            n = 9;
        } else if (this.readIf("INDEX")) {
            n = 1;
        } else if (this.readIf("ROLE")) {
            n = 7;
        } else if (this.readIf("SCHEMA")) {
            n = 10;
        } else if (this.readIf("SEQUENCE")) {
            n = 3;
        } else if (this.readIf("TRIGGER")) {
            n = 4;
        } else if (this.readIf(82)) {
            n = 2;
        } else if (this.readIf("DOMAIN")) {
            n = 12;
        } else {
            throw this.getSyntaxError();
        }
        SetComment setComment = new SetComment(this.session);
        if (bl) {
            String string2;
            string = this.readIdentifier();
            String string3 = null;
            this.read(110);
            boolean bl2 = this.database.getMode().allowEmptySchemaValuesAsDefaultSchema;
            String string4 = string2 = bl2 && this.currentTokenType == 110 ? null : this.readIdentifier();
            if (this.readIf(110)) {
                string3 = string;
                string = string2;
                String string5 = string2 = bl2 && this.currentTokenType == 110 ? null : this.readIdentifier();
                if (this.readIf(110)) {
                    this.checkDatabaseName(string3);
                    string3 = string;
                    string = string2;
                    string2 = this.readIdentifier();
                }
            }
            if (string2 == null || string == null) {
                throw DbException.getSyntaxError(this.sqlCommand, this.token.start(), "table.column");
            }
            this.schemaName = string3 != null ? string3 : this.session.getCurrentSchemaName();
            setComment.setColumn(true);
            setComment.setColumnName(string2);
        } else {
            string = this.readIdentifierWithSchema();
        }
        setComment.setSchemaName(this.schemaName);
        setComment.setObjectName(string);
        setComment.setObjectType(n);
        this.read(45);
        setComment.setCommentExpression(this.readExpression());
        return setComment;
    }

    private Prepared parseDrop() {
        if (this.readIf(75)) {
            boolean bl = this.readIfExists(false);
            DropTable dropTable = new DropTable(this.session);
            do {
                String string = this.readIdentifierWithSchema();
                dropTable.addTable(this.getSchema(), string);
            } while (this.readIf(109));
            bl = this.readIfExists(bl);
            dropTable.setIfExists(bl);
            if (this.readIf("CASCADE")) {
                dropTable.setDropAction(ConstraintActionType.CASCADE);
                this.readIf("CONSTRAINTS");
            } else if (this.readIf("RESTRICT")) {
                dropTable.setDropAction(ConstraintActionType.RESTRICT);
            } else if (this.readIf("IGNORE")) {
                dropTable.setDropAction(ConstraintActionType.SET_DEFAULT);
            }
            return dropTable;
        }
        if (this.readIf("INDEX")) {
            boolean bl = this.readIfExists(false);
            String string = this.readIdentifierWithSchema();
            DropIndex dropIndex = new DropIndex(this.session, this.getSchema());
            dropIndex.setIndexName(string);
            bl = this.readIfExists(bl);
            dropIndex.setIfExists(bl);
            if (this.readIf(60)) {
                this.readIdentifierWithSchema();
            }
            return dropIndex;
        }
        if (this.readIf(82)) {
            boolean bl = this.readIfExists(false);
            DropUser dropUser = new DropUser(this.session);
            dropUser.setUserName(this.readIdentifier());
            bl = this.readIfExists(bl);
            this.readIf("CASCADE");
            dropUser.setIfExists(bl);
            return dropUser;
        }
        if (this.readIf("SEQUENCE")) {
            boolean bl = this.readIfExists(false);
            String string = this.readIdentifierWithSchema();
            DropSequence dropSequence = new DropSequence(this.session, this.getSchema());
            dropSequence.setSequenceName(string);
            bl = this.readIfExists(bl);
            dropSequence.setIfExists(bl);
            return dropSequence;
        }
        if (this.readIf("CONSTANT")) {
            boolean bl = this.readIfExists(false);
            String string = this.readIdentifierWithSchema();
            DropConstant dropConstant = new DropConstant(this.session, this.getSchema());
            dropConstant.setConstantName(string);
            bl = this.readIfExists(bl);
            dropConstant.setIfExists(bl);
            return dropConstant;
        }
        if (this.readIf("TRIGGER")) {
            boolean bl = this.readIfExists(false);
            String string = this.readIdentifierWithSchema();
            DropTrigger dropTrigger = new DropTrigger(this.session, this.getSchema());
            dropTrigger.setTriggerName(string);
            bl = this.readIfExists(bl);
            dropTrigger.setIfExists(bl);
            return dropTrigger;
        }
        if (this.readIf("MATERIALIZED")) {
            this.read("VIEW");
            boolean bl = this.readIfExists(false);
            String string = this.readIdentifierWithSchema();
            DropMaterializedView dropMaterializedView = new DropMaterializedView(this.session, this.getSchema());
            dropMaterializedView.setViewName(string);
            bl = this.readIfExists(bl);
            dropMaterializedView.setIfExists(bl);
            return dropMaterializedView;
        }
        if (this.readIf("VIEW")) {
            boolean bl = this.readIfExists(false);
            String string = this.readIdentifierWithSchema();
            DropView dropView = new DropView(this.session, this.getSchema());
            dropView.setViewName(string);
            bl = this.readIfExists(bl);
            dropView.setIfExists(bl);
            ConstraintActionType constraintActionType = this.parseCascadeOrRestrict();
            if (constraintActionType != null) {
                dropView.setDropAction(constraintActionType);
            }
            return dropView;
        }
        if (this.readIf("ROLE")) {
            boolean bl = this.readIfExists(false);
            DropRole dropRole = new DropRole(this.session);
            dropRole.setRoleName(this.readIdentifier());
            bl = this.readIfExists(bl);
            dropRole.setIfExists(bl);
            return dropRole;
        }
        if (this.readIf("ALIAS")) {
            boolean bl = this.readIfExists(false);
            String string = this.readIdentifierWithSchema();
            DropFunctionAlias dropFunctionAlias = new DropFunctionAlias(this.session, this.getSchema());
            dropFunctionAlias.setAliasName(string);
            bl = this.readIfExists(bl);
            dropFunctionAlias.setIfExists(bl);
            return dropFunctionAlias;
        }
        if (this.readIf("SCHEMA")) {
            boolean bl = this.readIfExists(false);
            DropSchema dropSchema = new DropSchema(this.session);
            dropSchema.setSchemaName(this.readIdentifier());
            bl = this.readIfExists(bl);
            dropSchema.setIfExists(bl);
            ConstraintActionType constraintActionType = this.parseCascadeOrRestrict();
            if (constraintActionType != null) {
                dropSchema.setDropAction(constraintActionType);
            }
            return dropSchema;
        }
        if (this.readIf(3, "OBJECTS")) {
            DropDatabase dropDatabase = new DropDatabase(this.session);
            dropDatabase.setDropAllObjects(true);
            if (this.readIf("DELETE", "FILES")) {
                dropDatabase.setDeleteFiles(true);
            }
            return dropDatabase;
        }
        if (this.readIf("DOMAIN") || this.readIf("TYPE") || this.readIfCompat("DATATYPE")) {
            return this.parseDropDomain();
        }
        if (this.readIf("AGGREGATE")) {
            return this.parseDropAggregate();
        }
        if (this.readIf("SYNONYM")) {
            boolean bl = this.readIfExists(false);
            String string = this.readIdentifierWithSchema();
            DropSynonym dropSynonym = new DropSynonym(this.session, this.getSchema());
            dropSynonym.setSynonymName(string);
            bl = this.readIfExists(bl);
            dropSynonym.setIfExists(bl);
            return dropSynonym;
        }
        throw this.getSyntaxError();
    }

    private DropDomain parseDropDomain() {
        boolean bl = this.readIfExists(false);
        String string = this.readIdentifierWithSchema();
        DropDomain dropDomain = new DropDomain(this.session, this.getSchema());
        dropDomain.setDomainName(string);
        bl = this.readIfExists(bl);
        dropDomain.setIfDomainExists(bl);
        ConstraintActionType constraintActionType = this.parseCascadeOrRestrict();
        if (constraintActionType != null) {
            dropDomain.setDropAction(constraintActionType);
        }
        return dropDomain;
    }

    private DropAggregate parseDropAggregate() {
        boolean bl = this.readIfExists(false);
        String string = this.readIdentifierWithSchema();
        DropAggregate dropAggregate = new DropAggregate(this.session, this.getSchema());
        dropAggregate.setName(string);
        bl = this.readIfExists(bl);
        dropAggregate.setIfExists(bl);
        return dropAggregate;
    }

    private TableFilter readTableReference() {
        TableFilter tableFilter;
        TableFilter tableFilter2 = tableFilter = this.readTablePrimary();
        while (true) {
            TableFilter tableFilter3;
            switch (this.currentTokenType) {
                case 65: {
                    this.read();
                    this.readIf("OUTER");
                    this.read(46);
                    tableFilter3 = this.readTableReference();
                    Expression expression = this.readJoinSpecification(tableFilter, tableFilter3, true);
                    this.addJoin(tableFilter3, tableFilter, true, expression);
                    tableFilter = tableFilter3;
                    break;
                }
                case 48: {
                    this.read();
                    this.readIf("OUTER");
                    this.read(46);
                    tableFilter3 = this.readTableReference();
                    Expression expression = this.readJoinSpecification(tableFilter, tableFilter3, false);
                    this.addJoin(tableFilter, tableFilter3, true, expression);
                    break;
                }
                case 36: {
                    this.read();
                    throw this.getSyntaxError();
                }
                case 42: {
                    this.read();
                    this.read(46);
                    tableFilter3 = this.readTableReference();
                    Expression expression = this.readJoinSpecification(tableFilter, tableFilter3, false);
                    this.addJoin(tableFilter, tableFilter3, false, expression);
                    break;
                }
                case 46: {
                    this.read();
                    tableFilter3 = this.readTableReference();
                    Expression expression = this.readJoinSpecification(tableFilter, tableFilter3, false);
                    this.addJoin(tableFilter, tableFilter3, false, expression);
                    break;
                }
                case 15: {
                    this.read();
                    this.read(46);
                    tableFilter3 = this.readTablePrimary();
                    this.addJoin(tableFilter, tableFilter3, false, null);
                    break;
                }
                case 56: {
                    this.read();
                    this.read(46);
                    tableFilter3 = this.readTablePrimary();
                    Expression expression = null;
                    for (Column column : tableFilter2.getTable().getColumns()) {
                        Column column2 = tableFilter3.getColumn(tableFilter2.getColumnName(column), true);
                        if (column2 == null) continue;
                        expression = this.addJoinColumn(expression, tableFilter2, tableFilter3, column, column2, false);
                    }
                    this.addJoin(tableFilter, tableFilter3, false, expression);
                    break;
                }
                default: {
                    if (this.expectedList != null) {
                        this.addMultipleExpected(65, 48, 42, 46, 15, 56);
                    }
                    return tableFilter;
                }
            }
            tableFilter2 = tableFilter3;
        }
    }

    private Expression readJoinSpecification(TableFilter tableFilter, TableFilter tableFilter2, boolean bl) {
        Expression expression = null;
        if (this.readIf(60)) {
            expression = this.readExpression();
        } else if (this.readIf(83, 105)) {
            do {
                String string = this.readIdentifier();
                expression = this.addJoinColumn(expression, tableFilter, tableFilter2, tableFilter.getColumn(string, false), tableFilter2.getColumn(string, false), bl);
            } while (this.readIfMore());
        }
        return expression;
    }

    private Expression addJoinColumn(Expression expression, TableFilter tableFilter, TableFilter tableFilter2, Column column, Column column2, boolean bl) {
        if (bl) {
            tableFilter.addCommonJoinColumns(column, column2, tableFilter2);
            tableFilter2.addCommonJoinColumnToExclude(column2);
        } else {
            tableFilter.addCommonJoinColumns(column, column, tableFilter);
            tableFilter2.addCommonJoinColumnToExclude(column2);
        }
        ExpressionColumn expressionColumn = new ExpressionColumn(this.database, tableFilter.getSchemaName(), tableFilter.getTableAlias(), tableFilter.getColumnName(column));
        ExpressionColumn expressionColumn2 = new ExpressionColumn(this.database, tableFilter2.getSchemaName(), tableFilter2.getTableAlias(), tableFilter2.getColumnName(column2));
        Comparison comparison = new Comparison(0, expressionColumn, expressionColumn2, false);
        expression = expression == null ? comparison : new ConditionAndOr(0, expression, comparison);
        return expression;
    }

    private void addJoin(TableFilter tableFilter, TableFilter tableFilter2, boolean bl, Expression expression) {
        if (tableFilter2.getJoin() != null) {
            String string = "SYSTEM_JOIN_" + this.token.start();
            TableFilter tableFilter3 = new TableFilter(this.session, new DualTable(this.database), string, this.rightsChecked, this.currentSelect, tableFilter2.getOrderInFrom(), null);
            tableFilter3.setNestedJoin(tableFilter2);
            tableFilter2 = tableFilter3;
        }
        tableFilter.addJoin(tableFilter2, bl, expression);
    }

    private Prepared parseExecutePostgre() {
        ExecuteProcedure executeProcedure = new ExecuteProcedure(this.session);
        String string = this.readIdentifier();
        Procedure procedure = this.session.getProcedure(string);
        if (procedure == null) {
            throw DbException.get(90077, string);
        }
        executeProcedure.setProcedure(procedure);
        if (this.readIf(105)) {
            int n = 0;
            while (true) {
                executeProcedure.setExpression(n, this.readExpression());
                if (!this.readIfMore()) break;
                ++n;
            }
        }
        return executeProcedure;
    }

    private Prepared parseExecuteSQLServer() {
        Call call = new Call(this.session);
        this.currentPrepared = call;
        String string = null;
        String string2 = this.readIdentifier();
        if (this.readIf(110)) {
            string = string2;
            string2 = this.readIdentifier();
            if (this.readIf(110)) {
                this.checkDatabaseName(string);
                string = string2;
                string2 = this.readIdentifier();
            }
        }
        FunctionAlias functionAlias = this.getFunctionAliasWithinPath(string2, string != null ? this.database.getSchema(string) : null);
        ArrayList<Expression> arrayList = Utils.newSmallArrayList();
        if (this.currentTokenType != 115 && this.currentTokenType != 93) {
            do {
                arrayList.add(this.readExpression());
            } while (this.readIf(109));
        }
        Expression[] expressionArray = arrayList.toArray(new Expression[0]);
        call.setExpression(new JavaFunction(functionAlias, expressionArray));
        return call;
    }

    private FunctionAlias getFunctionAliasWithinPath(String string, Schema schema) {
        UserDefinedFunction userDefinedFunction = this.findUserDefinedFunctionWithinPath(schema, string);
        if (userDefinedFunction instanceof FunctionAlias) {
            return (FunctionAlias)userDefinedFunction;
        }
        throw DbException.get(90022, string);
    }

    private DeallocateProcedure parseDeallocate() {
        this.readIf("PLAN");
        DeallocateProcedure deallocateProcedure = new DeallocateProcedure(this.session);
        deallocateProcedure.setProcedureName(this.readIdentifier());
        return deallocateProcedure;
    }

    private Explain parseExplain() {
        Explain explain = new Explain(this.session);
        if (this.readIf("ANALYZE")) {
            explain.setExecuteCommand(true);
        } else if (this.readIfCompat("PLAN")) {
            this.readIf(33);
        }
        switch (this.currentTokenType) {
            case 69: 
            case 75: 
            case 85: 
            case 89: 
            case 105: {
                Query query = this.parseQuery();
                query.setNeverLazy(true);
                explain.setCommand(query);
                break;
            }
            default: {
                int n = this.tokenIndex;
                if (this.readIf("DELETE")) {
                    explain.setCommand(this.parseDelete(n));
                    break;
                }
                if (this.readIf("UPDATE")) {
                    explain.setCommand(this.parseUpdate(n));
                    break;
                }
                if (this.readIf("INSERT")) {
                    explain.setCommand(this.parseInsert(n));
                    break;
                }
                if (this.readIf("MERGE")) {
                    explain.setCommand(this.parseMerge(n));
                    break;
                }
                throw this.getSyntaxError();
            }
        }
        return explain;
    }

    private Query parseQuery() {
        BitSet bitSet = this.openParametersScope();
        Query query = this.parseQueryExpression();
        ArrayList<Parameter> arrayList = this.closeParametersScope(bitSet);
        query.setParameterList(arrayList);
        query.init();
        return query;
    }

    private Prepared parseWithStatementOrQuery(int n) {
        BitSet bitSet = this.openParametersScope();
        Prepared prepared = this.parseWith();
        ArrayList<Parameter> arrayList = this.closeParametersScope(bitSet);
        prepared.setParameterList(arrayList);
        if (prepared instanceof Query) {
            Query query = (Query)prepared;
            query.init();
        }
        this.setSQL(prepared, n);
        return prepared;
    }

    private Query parseQueryExpression() {
        Query query;
        if (this.readIf(89)) {
            try {
                query = (Query)this.parseWith();
            }
            catch (ClassCastException classCastException) {
                throw DbException.get(42000, "WITH statement supports only query in this context");
            }
            query.setNeverLazy(true);
        } else {
            query = this.parseQueryExpressionBodyAndEndOfQuery();
        }
        return query;
    }

    private Query parseQueryExpressionBodyAndEndOfQuery() {
        int n = this.tokenIndex;
        Query query = this.parseQueryExpressionBody();
        this.parseEndOfQuery(query);
        this.setSQL(query, n);
        return query;
    }

    private Query parseQueryExpressionBody() {
        Query query = this.parseQueryTerm();
        while (true) {
            SelectUnion.UnionType unionType;
            if (this.readIf(79)) {
                if (this.readIf(3)) {
                    unionType = SelectUnion.UnionType.UNION_ALL;
                } else {
                    this.readIf(26);
                    unionType = SelectUnion.UnionType.UNION;
                }
            } else {
                if (!this.readIf(29) && !this.readIfCompat(53)) break;
                unionType = SelectUnion.UnionType.EXCEPT;
            }
            query = new SelectUnion(this.session, unionType, query, this.parseQueryTerm());
        }
        return query;
    }

    private Query parseQueryTerm() {
        Query query = this.parseQueryPrimary();
        while (this.readIf(43)) {
            query = new SelectUnion(this.session, SelectUnion.UnionType.INTERSECT, query, this.parseQueryPrimary());
        }
        return query;
    }

    private void parseEndOfQuery(Query query) {
        Object object;
        Serializable serializable;
        Object object2;
        if (this.readIf(62, "BY")) {
            object2 = this.currentSelect;
            if (query instanceof Select) {
                this.currentSelect = (Select)query;
            }
            serializable = Utils.newSmallArrayList();
            do {
                boolean bl = this.currentTokenType == 94;
                object = new QueryOrderBy();
                Expression expression = this.readExpression();
                if (bl && expression instanceof ValueExpression && expression.getType().getValueType() == 11) {
                    ((QueryOrderBy)object).columnIndexExpr = expression;
                } else if (expression instanceof Parameter) {
                    this.recompileAlways = true;
                    ((QueryOrderBy)object).columnIndexExpr = expression;
                } else {
                    ((QueryOrderBy)object).expression = expression;
                }
                ((QueryOrderBy)object).sortType = this.parseSortType();
                ((ArrayList)serializable).add(object);
            } while (this.readIf(109));
            query.setOrder((ArrayList<QueryOrderBy>)serializable);
            this.currentSelect = object2;
        }
        if (query.getFetch() == null) {
            object2 = this.currentSelect;
            this.currentSelect = null;
            boolean bl = false;
            if (this.readIf(59)) {
                bl = true;
                query.setOffset(this.readExpression().optimize(this.session));
                if (!this.readIf(66)) {
                    this.readIf("ROWS");
                }
            }
            if (this.readIf(32)) {
                bl = true;
                if (!this.readIf("FIRST")) {
                    this.read("NEXT");
                }
                if (this.readIf(66) || this.readIf("ROWS")) {
                    query.setFetch(ValueExpression.get(ValueInteger.get(1)));
                } else {
                    query.setFetch(this.readExpression().optimize(this.session));
                    if (this.readIf("PERCENT")) {
                        query.setFetchPercent(true);
                    }
                    if (!this.readIf(66)) {
                        this.read("ROWS");
                    }
                }
                if (this.readIf(89, "TIES")) {
                    query.setWithTies(true);
                } else {
                    this.read("ONLY");
                }
            }
            if (!bl && this.database.getMode().limit && this.readIfCompat(50)) {
                Expression expression = this.readExpression().optimize(this.session);
                if (this.readIf(59)) {
                    query.setOffset(this.readExpression().optimize(this.session));
                } else if (this.readIf(109)) {
                    object = expression;
                    expression = this.readExpression().optimize(this.session);
                    query.setOffset((Expression)object);
                }
                query.setFetch(expression);
            }
            this.currentSelect = object2;
        }
        if (this.readIf(33)) {
            if (this.readIf("UPDATE")) {
                if (this.readIfCompat("OF")) {
                    do {
                        this.readIdentifierWithSchema();
                    } while (this.readIf(109));
                }
                if (this.readIf("NOWAIT")) {
                    object2 = ForUpdate.NOWAIT;
                } else if (this.readIf("WAIT")) {
                    if (this.currentTokenType != 94 || (serializable = this.token.value(this.session).getBigDecimal()) == null || ((BigDecimal)serializable).signum() < 0 || ((BigDecimal)serializable).compareTo(BigDecimal.valueOf(Integer.MAX_VALUE, 3)) > 0) {
                        throw DbException.getSyntaxError(this.sqlCommand, this.token.start(), "timeout (0..2147483.647)");
                    }
                    this.read();
                    object2 = ForUpdate.wait(((BigDecimal)serializable).movePointRight(3).intValue());
                } else {
                    object2 = this.readIf("SKIP", "LOCKED") ? ForUpdate.SKIP_LOCKED : ForUpdate.DEFAULT;
                }
                query.setForUpdate((ForUpdate)object2);
            } else if (this.readIfCompat("READ") || this.readIfCompat(32)) {
                this.read("ONLY");
            }
        }
        if (this.database.getMode().isolationLevelInSelectOrInsertStatement) {
            this.parseIsolationClause();
        }
    }

    private void parseIsolationClause() {
        if (this.readIfCompat(89)) {
            if (this.readIf("RR") || this.readIf("RS")) {
                if (this.readIf("USE", 4, "KEEP")) {
                    if (this.readIf("SHARE") || this.readIf("UPDATE") || this.readIf("EXCLUSIVE")) {
                        // empty if block
                    }
                    this.read("LOCKS");
                }
            } else if (this.readIf("CS") || this.readIf("UR")) {
                // empty if block
            }
        }
    }

    private Query parseQueryPrimary() {
        if (this.readIf(105)) {
            Query query = this.parseQueryExpressionBodyAndEndOfQuery();
            this.read(106);
            return query;
        }
        int n = this.tokenIndex;
        if (this.readIf(69)) {
            return this.parseSelect(n);
        }
        if (this.readIf(75)) {
            return this.parseExplicitTable(n);
        }
        this.read(85);
        return this.parseValues();
    }

    private void parseSelectFromPart(Select select) {
        block0: do {
            TableFilter tableFilter2 = this.readTableReference();
            select.addTableFilter(tableFilter2, true);
            boolean bl = false;
            while (true) {
                TableFilter tableFilter3;
                TableFilter tableFilter4;
                if ((tableFilter4 = tableFilter2.getNestedJoin()) != null) {
                    tableFilter4.visit(tableFilter -> select.addTableFilter(tableFilter, false));
                }
                if ((tableFilter3 = tableFilter2.getJoin()) == null) continue block0;
                if (bl |= tableFilter3.isJoinOuter()) {
                    select.addTableFilter(tableFilter3, false);
                } else {
                    Expression expression = tableFilter3.getJoinCondition();
                    if (expression != null) {
                        select.addCondition(expression);
                    }
                    tableFilter3.removeJoinCondition();
                    tableFilter2.removeJoin();
                    select.addTableFilter(tableFilter3, true);
                }
                tableFilter2 = tableFilter3;
            }
        } while (this.readIf(109));
    }

    private void parseSelectExpressions(Select select) {
        ArrayList<Expression> arrayList;
        if (this.database.getMode().topInSelect && this.readIfCompat("TOP")) {
            arrayList = this.currentSelect;
            this.currentSelect = null;
            select.setFetch(this.readTerm().optimize(this.session));
            if (this.readIf("PERCENT")) {
                select.setFetchPercent(true);
            }
            if (this.readIf(89, "TIES")) {
                select.setWithTies(true);
            }
            this.currentSelect = arrayList;
        }
        if (this.readIf(26)) {
            if (this.readIf(60, 105)) {
                arrayList = Utils.newSmallArrayList();
                do {
                    arrayList.add(this.readExpression());
                } while (this.readIfMore());
                select.setDistinct(arrayList.toArray(new Expression[0]));
            } else {
                select.setDistinct();
            }
        } else {
            this.readIf(3);
        }
        arrayList = Utils.newSmallArrayList();
        do {
            if (this.readIf(108)) {
                arrayList.add(this.parseWildcard(null, null));
                continue;
            }
            switch (this.currentTokenType) {
                case 32: 
                case 35: 
                case 37: 
                case 38: 
                case 59: 
                case 62: 
                case 64: 
                case 87: 
                case 88: 
                case 93: 
                case 106: 
                case 115: {
                    break;
                }
                default: {
                    Expression expression = this.readExpression();
                    if (this.readIf(7) || this.isIdentifier()) {
                        expression = new Alias(expression, this.readIdentifier(), this.database.getMode().aliasColumnName);
                    }
                    arrayList.add(expression);
                }
            }
        } while (this.readIf(109));
        select.setExpressions(arrayList);
    }

    private Select parseSelect(int n) {
        Object object;
        Object object2;
        Object object3;
        Select select = new Select(this.session, this.currentSelect);
        Select select2 = this.currentSelect;
        Prepared prepared = this.currentPrepared;
        BitSet bitSet = this.openParametersScope();
        this.currentSelect = select;
        this.currentPrepared = select;
        this.parseSelectExpressions(select);
        if (!this.readIf(35)) {
            object3 = new TableFilter(this.session, new DualTable(this.database), null, this.rightsChecked, this.currentSelect, 0, null);
            select.addTableFilter((TableFilter)object3, true);
        } else {
            this.parseSelectFromPart(select);
        }
        if (this.readIf(87)) {
            select.addCondition(this.readExpressionWithGlobalConditions());
        }
        this.currentSelect = select2;
        if (this.readIf(37, "BY")) {
            select.setGroupQuery();
            object3 = Utils.newSmallArrayList();
            do {
                if (this.isToken(105) && this.isOrdinaryGroupingSet()) {
                    if (this.readIf(106)) continue;
                    do {
                        ((ArrayList)object3).add(this.readExpression());
                    } while (this.readIfMore());
                    continue;
                }
                object2 = this.readExpression();
                if (this.database.getMode().groupByColumnIndex && object2 instanceof ValueExpression && ((Expression)object2).getType().getValueType() == 11) {
                    object = select.getExpressions();
                    Iterator iterator2 = ((ArrayList)object).iterator();
                    while (iterator2.hasNext()) {
                        Expression expression = (Expression)iterator2.next();
                        if (!(expression instanceof Wildcard)) continue;
                        throw this.getSyntaxError();
                    }
                    int n2 = ((Expression)object2).getValue(this.session).getInt();
                    if (n2 < 1 || n2 > ((ArrayList)object).size()) {
                        throw DbException.get(90157, Integer.toString(n2), Integer.toString(((ArrayList)object).size()));
                    }
                    ((ArrayList)object3).add(((ArrayList)object).get(n2 - 1));
                    continue;
                }
                ((ArrayList)object3).add(object2);
            } while (this.readIf(109));
            if (!((ArrayList)object3).isEmpty()) {
                select.setGroupBy((ArrayList<Expression>)object3);
            }
        }
        this.currentSelect = select;
        if (this.readIf(38)) {
            select.setGroupQuery();
            select.setHaving(this.readExpressionWithGlobalConditions());
        }
        if (this.readIf(88)) {
            do {
                int n3 = this.token.start();
                object2 = this.readIdentifier();
                this.read(7);
                object = this.readWindowSpecification();
                if (this.currentSelect.addWindow((String)object2, (Window)object)) continue;
                throw DbException.getSyntaxError(this.sqlCommand, n3, "unique identifier");
            } while (this.readIf(109));
        }
        if (this.readIf(64)) {
            select.setWindowQuery();
            select.setQualify(this.readExpressionWithGlobalConditions());
        }
        select.setParameterList(this.closeParametersScope(bitSet));
        this.currentSelect = select2;
        this.currentPrepared = prepared;
        this.setSQL(select, n);
        return select;
    }

    private boolean isOrdinaryGroupingSet() {
        int n = this.scanToCloseParen(this.tokenIndex + 1);
        if (n < 0) {
            return false;
        }
        switch (((Token)this.tokens.get(n)).tokenType()) {
            case 29: 
            case 32: 
            case 33: 
            case 38: 
            case 43: 
            case 50: 
            case 53: 
            case 59: 
            case 62: 
            case 64: 
            case 79: 
            case 88: 
            case 93: 
            case 106: 
            case 109: 
            case 115: {
                this.setTokenIndex(this.tokenIndex + 1);
                return true;
            }
        }
        return false;
    }

    private Query parseExplicitTable(int n) {
        Table table = this.readTableOrView();
        Select select = new Select(this.session, this.currentSelect);
        TableFilter tableFilter = new TableFilter(this.session, table, null, this.rightsChecked, select, this.orderInFrom++, null);
        select.addTableFilter(tableFilter, true);
        select.setExplicitTable();
        this.setSQL(select, n);
        return select;
    }

    private void setSQL(Prepared prepared, int n) {
        ArrayList<Token> arrayList;
        int n2;
        String string = this.sqlCommand;
        int n3 = this.token.start();
        for (n2 = ((Token)this.tokens.get(n)).start(); n2 < n3 && string.charAt(n2) <= ' '; ++n2) {
        }
        while (n2 < n3 && string.charAt(n3 - 1) <= ' ') {
            --n3;
        }
        string = string.substring(n2, n3);
        if (n == 0 && this.currentTokenType == 93) {
            arrayList = this.tokens;
            if (n2 != 0) {
                int n4 = arrayList.size() - 1;
                for (int i = 0; i < n4; ++i) {
                    ((Token)arrayList.get(i)).subtractFromStart(n2);
                }
            }
            this.token.setStart(string.length());
            this.sqlCommand = string;
        } else {
            List list = this.tokens.subList(n, this.tokenIndex);
            arrayList = new ArrayList<Token>(list.size() + 1);
            for (int i = n; i < this.tokenIndex; ++i) {
                Token token = ((Token)this.tokens.get(i)).clone();
                token.subtractFromStart(n2);
                arrayList.add(token);
            }
            arrayList.add(new Token.EndOfInputToken(string.length()));
        }
        prepared.setSQL(string, arrayList);
    }

    private Expression readExpressionOrDefault() {
        if (this.readIf(25)) {
            return ValueExpression.DEFAULT;
        }
        return this.readExpression();
    }

    private Expression readExpressionWithGlobalConditions() {
        Expression expression = this.readCondition();
        if (this.readIf(4)) {
            expression = this.readAnd(new ConditionAndOr(0, expression, this.readCondition()));
        } else if (this.readIf("_LOCAL_AND_GLOBAL_")) {
            expression = this.readAnd(new ConditionLocalAndGlobal(expression, this.readCondition()));
        }
        return this.readExpressionPart2(expression);
    }

    private Expression readExpression() {
        return this.readExpressionPart2(this.readAnd(this.readCondition()));
    }

    private Expression readExpressionPart2(Expression expression) {
        if (!this.readIf(61)) {
            return expression;
        }
        Expression expression2 = this.readAnd(this.readCondition());
        if (!this.readIf(61)) {
            return new ConditionAndOr(1, expression, expression2);
        }
        ArrayList<Expression> arrayList = new ArrayList<Expression>();
        arrayList.add(expression);
        arrayList.add(expression2);
        do {
            arrayList.add(this.readAnd(this.readCondition()));
        } while (this.readIf(61));
        return new ConditionAndOrN(1, arrayList);
    }

    private Expression readAnd(Expression expression) {
        if (!this.readIf(4)) {
            return expression;
        }
        Expression expression2 = this.readCondition();
        if (!this.readIf(4)) {
            return new ConditionAndOr(0, expression, expression2);
        }
        ArrayList<Expression> arrayList = new ArrayList<Expression>();
        arrayList.add(expression);
        arrayList.add(expression2);
        do {
            arrayList.add(this.readCondition());
        } while (this.readIf(4));
        return new ConditionAndOrN(0, arrayList);
    }

    private Expression readCondition() {
        boolean bl;
        Expression expression;
        switch (this.currentTokenType) {
            case 57: {
                this.read();
                return new ConditionNot(this.readCondition());
            }
            case 30: {
                this.read();
                this.read(105);
                Query query = this.parseQuery();
                this.read(106);
                return new ExistsPredicate(query);
            }
            case 80: {
                this.read();
                NullsDistinct nullsDistinct = this.readNullsDistinct(NullsDistinct.DISTINCT);
                this.read(105);
                Query query = this.parseQuery();
                this.read(106);
                return new UniquePredicate(query, nullsDistinct);
            }
        }
        if (this.readIf("INTERSECTS", 105)) {
            Expression expression2 = this.readConcat();
            this.read(109);
            Expression expression3 = this.readConcat();
            this.read(106);
            return new Comparison(8, expression2, expression3, false);
        }
        if (this.expectedList != null) {
            this.addMultipleExpected(57, 30, 80);
            this.addExpected("INTERSECTS");
        }
        Expression expression4 = this.readConcat();
        do {
            expression = expression4;
            int n = this.tokenIndex;
            bl = this.readIf(57);
            if (!bl || this.currentTokenType != 58) continue;
            this.setTokenIndex(n);
            break;
        } while ((expression4 = this.readConditionRightHandSide(expression, bl, false)) != null);
        return expression;
    }

    private Expression readConditionRightHandSide(Expression expression, boolean bl, boolean bl2) {
        if (!bl && this.readIf(45)) {
            expression = this.readConditionIs(expression, bl2);
        } else {
            switch (this.currentTokenType) {
                case 10: {
                    this.read();
                    boolean bl3 = this.readIf(73);
                    if (!bl3) {
                        this.readIf(8);
                    }
                    Expression expression2 = this.readConcat();
                    this.read(4);
                    expression = new BetweenPredicate(expression, bl, bl2, bl3, expression2, this.readConcat());
                    break;
                }
                case 41: {
                    this.read();
                    expression = this.readInPredicate(expression, bl, bl2);
                    break;
                }
                case 49: {
                    this.read();
                    expression = this.readLikePredicate(expression, CompareLike.LikeType.LIKE, bl, bl2);
                    break;
                }
                default: {
                    if (this.readIf("ILIKE")) {
                        expression = this.readLikePredicate(expression, CompareLike.LikeType.ILIKE, bl, bl2);
                        break;
                    }
                    if (this.readIf("REGEXP")) {
                        Expression expression3 = this.readConcat();
                        this.recompileAlways = true;
                        expression = new CompareLike(this.database, expression, bl, bl2, expression3, null, CompareLike.LikeType.REGEXP);
                        break;
                    }
                    if (bl) {
                        if (bl2) {
                            return null;
                        }
                        if (this.expectedList != null) {
                            this.addMultipleExpected(10, 41, 49);
                        }
                        throw this.getSyntaxError();
                    }
                    int n = Parser.getCompareType(this.currentTokenType);
                    if (n < 0) {
                        return null;
                    }
                    this.read();
                    expression = this.readComparison(expression, n, bl2);
                }
            }
        }
        return expression;
    }

    private Expression readConditionIs(Expression expression, boolean bl) {
        boolean bl2 = this.readIf(57);
        switch (this.currentTokenType) {
            case 58: {
                this.read();
                expression = new NullPredicate(expression, bl2, bl);
                break;
            }
            case 26: {
                this.read();
                this.read(35);
                expression = this.readComparison(expression, bl2 ? 6 : 7, bl);
                break;
            }
            case 77: {
                this.read();
                expression = new BooleanTest(expression, bl2, bl, true);
                break;
            }
            case 31: {
                this.read();
                expression = new BooleanTest(expression, bl2, bl, false);
                break;
            }
            case 81: {
                this.read();
                expression = new BooleanTest(expression, bl2, bl, null);
                break;
            }
            default: {
                if (this.readIf("OF")) {
                    expression = this.readTypePredicate(expression, bl2, bl);
                    break;
                }
                if (this.readIf("JSON")) {
                    expression = this.readJsonPredicate(expression, bl2, bl);
                    break;
                }
                if (this.expectedList != null) {
                    this.addMultipleExpected(58, 26, 77, 31, 81);
                }
                if (bl || !this.session.isQuirksMode()) {
                    throw this.getSyntaxError();
                }
                expression = new Comparison(bl2 ? 7 : 6, expression, this.readConcat(), false);
            }
        }
        return expression;
    }

    private TypePredicate readTypePredicate(Expression expression, boolean bl, boolean bl2) {
        this.read(105);
        ArrayList<TypeInfo> arrayList = Utils.newSmallArrayList();
        do {
            arrayList.add(this.parseDataType());
        } while (this.readIfMore());
        return new TypePredicate(expression, bl, bl2, arrayList.toArray(new TypeInfo[0]));
    }

    private Expression readInPredicate(Expression expression, boolean bl, boolean bl2) {
        ArrayList<Expression> arrayList;
        this.read(105);
        if (!bl2 && this.database.getMode().allowEmptyInPredicate && this.readIf(106)) {
            return ValueExpression.getBoolean(bl);
        }
        if (this.isQuery()) {
            Query query = this.parseQuery();
            if (!this.readIfMore()) {
                return new ConditionInQuery(expression, bl, bl2, query, false, 0);
            }
            arrayList = Utils.newSmallArrayList();
            arrayList.add(new Subquery(query));
        } else {
            arrayList = Utils.newSmallArrayList();
        }
        do {
            arrayList.add(this.readExpression());
        } while (this.readIfMore());
        return new ConditionIn(expression, bl, bl2, arrayList);
    }

    private IsJsonPredicate readJsonPredicate(Expression expression, boolean bl, boolean bl2) {
        JSONItemType jSONItemType = this.readIf(84) ? JSONItemType.VALUE : (this.readIf(6) ? JSONItemType.ARRAY : (this.readIf("OBJECT") ? JSONItemType.OBJECT : (this.readIf("SCALAR") ? JSONItemType.SCALAR : JSONItemType.VALUE)));
        boolean bl3 = false;
        if (this.readIf(89, 80)) {
            this.readIf("KEYS");
            bl3 = true;
        } else if (this.readIf("WITHOUT", 80)) {
            this.readIf("KEYS");
        }
        return new IsJsonPredicate(expression, bl, bl2, bl3, jSONItemType);
    }

    private Expression readLikePredicate(Expression expression, CompareLike.LikeType likeType, boolean bl, boolean bl2) {
        Expression expression2 = this.readConcat();
        Expression expression3 = this.readIf("ESCAPE") ? this.readConcat() : null;
        this.recompileAlways = true;
        return new CompareLike(this.database, expression, bl, bl2, expression2, expression3, likeType);
    }

    private Expression readComparison(Expression expression, int n, boolean bl) {
        int n2 = this.tokenIndex;
        if (this.readIf(3, 105)) {
            expression = this.isQuery() ? new ConditionInQuery(expression, false, bl, this.parseQuery(), true, n) : new ConditionInArray(expression, bl, this.readExpression(), true, n);
            this.read(106);
        } else {
            expression = this.readIf(5, 105) ? this.readAnyComparison(expression, n, bl, n2) : (this.readIf(72, 105) ? this.readAnyComparison(expression, n, bl, n2) : new Comparison(n, expression, this.readConcat(), bl));
        }
        return expression;
    }

    private Expression readAnyComparison(Expression expression, int n, boolean bl, int n2) {
        expression = this.isQuery() ? new ConditionInQuery(expression, false, bl, this.parseQuery(), false, n) : new ConditionInArray(expression, bl, this.readExpression(), false, n);
        this.read(106);
        return expression;
    }

    private Expression readConcat() {
        Expression expression = this.readSum();
        block5: while (true) {
            switch (this.currentTokenType) {
                case 104: {
                    this.read();
                    Expression expression2 = this.readSum();
                    if (this.readIf(104)) {
                        ConcatenationOperation concatenationOperation = new ConcatenationOperation();
                        concatenationOperation.addParameter(expression);
                        concatenationOperation.addParameter(expression2);
                        do {
                            concatenationOperation.addParameter(this.readSum());
                        } while (this.readIf(104));
                        concatenationOperation.doneWithParameters();
                        expression = concatenationOperation;
                        continue block5;
                    }
                    expression = new ConcatenationOperation(expression, expression2);
                    continue block5;
                }
                case 119: {
                    expression = this.readTildeCondition(expression, false);
                    continue block5;
                }
                case 122: {
                    expression = this.readTildeCondition(expression, true);
                    continue block5;
                }
            }
            break;
        }
        this.addExpected(104);
        return expression;
    }

    private Expression readSum() {
        Expression expression = this.readFactor();
        while (true) {
            if (this.readIf(103)) {
                expression = new BinaryOperation(BinaryOperation.OpType.PLUS, expression, this.readFactor());
                continue;
            }
            if (!this.readIf(102)) break;
            expression = new BinaryOperation(BinaryOperation.OpType.MINUS, expression, this.readFactor());
        }
        return expression;
    }

    private Expression readFactor() {
        Expression expression = this.readTerm();
        while (true) {
            if (this.readIf(108)) {
                expression = new BinaryOperation(BinaryOperation.OpType.MULTIPLY, expression, this.readTerm());
                continue;
            }
            if (this.readIf(113)) {
                expression = new BinaryOperation(BinaryOperation.OpType.DIVIDE, expression, this.readTerm());
                continue;
            }
            if (!this.readIf(114)) break;
            expression = new MathFunction(expression, this.readTerm(), 1);
        }
        return expression;
    }

    private Expression readTildeCondition(Expression expression, boolean bl) {
        this.read();
        if (this.readIf(108)) {
            expression = new CastSpecification(expression, TypeInfo.TYPE_VARCHAR_IGNORECASE);
        }
        return new CompareLike(this.database, expression, bl, false, this.readSum(), null, CompareLike.LikeType.REGEXP);
    }

    private Expression readAggregate(AggregateType aggregateType, String string) {
        Aggregate aggregate;
        if (this.currentSelect == null) {
            this.expectedList = null;
            throw this.getSyntaxError();
        }
        switch (aggregateType) {
            case COUNT: {
                if (this.readIf(108)) {
                    aggregate = new Aggregate(AggregateType.COUNT_ALL, new Expression[0], this.currentSelect, false);
                    break;
                }
                boolean bl = this.readDistinctAgg();
                Expression expression = this.readExpression();
                if (expression instanceof Wildcard && !bl) {
                    aggregate = new Aggregate(AggregateType.COUNT_ALL, new Expression[0], this.currentSelect, false);
                    break;
                }
                aggregate = new Aggregate(AggregateType.COUNT, new Expression[]{expression}, this.currentSelect, bl);
                break;
            }
            case COVAR_POP: 
            case COVAR_SAMP: 
            case CORR: 
            case REGR_SLOPE: 
            case REGR_INTERCEPT: 
            case REGR_COUNT: 
            case REGR_R2: 
            case REGR_AVGX: 
            case REGR_AVGY: 
            case REGR_SXX: 
            case REGR_SYY: 
            case REGR_SXY: {
                aggregate = new Aggregate(aggregateType, new Expression[]{this.readExpression(), this.readNextArgument()}, this.currentSelect, false);
                break;
            }
            case HISTOGRAM: {
                aggregate = new Aggregate(aggregateType, new Expression[]{this.readExpression()}, this.currentSelect, false);
                break;
            }
            case LISTAGG: {
                ArrayList<QueryOrderBy> arrayList;
                boolean bl = this.readDistinctAgg();
                Expression expression = this.readExpression();
                ListaggArguments listaggArguments = new ListaggArguments();
                if ("STRING_AGG".equals(string)) {
                    this.read(109);
                    listaggArguments.setSeparator(this.readStringOrParameter());
                    arrayList = this.readIfOrderBy();
                } else if ("GROUP_CONCAT".equals(string)) {
                    arrayList = this.readIfOrderBy();
                    if (this.readIf("SEPARATOR")) {
                        listaggArguments.setSeparator(this.readStringOrParameter());
                    }
                } else {
                    if (this.readIf(109)) {
                        listaggArguments.setSeparator(this.readStringOrParameter());
                    }
                    if (this.readIf(60)) {
                        this.read("OVERFLOW");
                        if (this.readIf("TRUNCATE")) {
                            listaggArguments.setOnOverflowTruncate(true);
                            if (this.currentTokenType == 94) {
                                listaggArguments.setFilter(this.readStringOrParameter());
                            }
                            if (!this.readIf(89)) {
                                this.read("WITHOUT");
                                listaggArguments.setWithoutCount(true);
                            }
                            this.read("COUNT");
                        } else {
                            this.read("ERROR");
                        }
                    }
                    arrayList = null;
                }
                Expression[] expressionArray = new Expression[]{expression};
                int n = this.tokenIndex;
                this.read(106);
                if (arrayList == null && this.isToken("WITHIN")) {
                    aggregate = this.readWithinGroup(aggregateType, expressionArray, bl, listaggArguments, false, false);
                    break;
                }
                this.setTokenIndex(n);
                aggregate = new Aggregate(AggregateType.LISTAGG, expressionArray, this.currentSelect, bl);
                aggregate.setExtraArguments(listaggArguments);
                if (arrayList == null) break;
                aggregate.setOrderByList(arrayList);
                break;
            }
            case ARRAY_AGG: {
                boolean bl = this.readDistinctAgg();
                aggregate = new Aggregate(AggregateType.ARRAY_AGG, new Expression[]{this.readExpression()}, this.currentSelect, bl);
                aggregate.setOrderByList(this.readIfOrderBy());
                break;
            }
            case RANK: 
            case DENSE_RANK: 
            case PERCENT_RANK: 
            case CUME_DIST: {
                if (this.isToken(106)) {
                    return this.readWindowFunction(string);
                }
                ArrayList<Expression> arrayList = Utils.newSmallArrayList();
                do {
                    arrayList.add(this.readExpression());
                } while (this.readIfMore());
                aggregate = this.readWithinGroup(aggregateType, arrayList.toArray(new Expression[0]), false, null, true, false);
                break;
            }
            case PERCENTILE_CONT: 
            case PERCENTILE_DISC: {
                Expression expression = this.readExpression();
                this.read(106);
                aggregate = this.readWithinGroup(aggregateType, new Expression[]{expression}, false, null, false, true);
                break;
            }
            case MODE: {
                if (this.readIf(106)) {
                    aggregate = this.readWithinGroup(AggregateType.MODE, new Expression[0], false, null, false, true);
                    break;
                }
                Expression expression = this.readExpression();
                aggregate = new Aggregate(AggregateType.MODE, new Expression[0], this.currentSelect, false);
                if (this.readIf(62)) {
                    this.read("BY");
                    Expression expression2 = this.readExpression();
                    String string2 = expression.getSQL(0);
                    String string3 = expression2.getSQL(0);
                    if (!string2.equals(string3)) {
                        throw DbException.getSyntaxError(42131, this.sqlCommand, this.token.start(), string2, string3);
                    }
                    this.readAggregateOrder(aggregate, expression, true);
                    break;
                }
                this.readAggregateOrder(aggregate, expression, false);
                break;
            }
            case JSON_OBJECTAGG: {
                boolean bl = this.readIf(47);
                Expression expression = this.readExpression();
                if (bl) {
                    this.read(84);
                } else if (!(this.readIf(84) || this.database.getMode().acceptsCommaAsJsonKeyValueSeparator && this.readIf(109))) {
                    this.read(116);
                }
                Expression expression3 = this.readExpression();
                aggregate = new Aggregate(AggregateType.JSON_OBJECTAGG, new Expression[]{expression, expression3}, this.currentSelect, false);
                this.readJsonObjectFunctionFlags(aggregate, false);
                break;
            }
            case JSON_ARRAYAGG: {
                boolean bl = this.readDistinctAgg();
                aggregate = new Aggregate(AggregateType.JSON_ARRAYAGG, new Expression[]{this.readExpression()}, this.currentSelect, bl);
                aggregate.setOrderByList(this.readIfOrderBy());
                aggregate.setFlags(1);
                this.readJsonObjectFunctionFlags(aggregate, true);
                break;
            }
            default: {
                boolean bl = this.readDistinctAgg();
                aggregate = new Aggregate(aggregateType, new Expression[]{this.readExpression()}, this.currentSelect, bl);
            }
        }
        this.read(106);
        this.readFilterAndOver(aggregate);
        return aggregate;
    }

    private Aggregate readWithinGroup(AggregateType aggregateType, Expression[] expressionArray, boolean bl, Object object, boolean bl2, boolean bl3) {
        this.read("WITHIN");
        this.read(37);
        this.read(105);
        this.read(62);
        this.read("BY");
        Aggregate aggregate = new Aggregate(aggregateType, expressionArray, this.currentSelect, bl);
        aggregate.setExtraArguments(object);
        if (bl2) {
            int n = expressionArray.length;
            ArrayList<QueryOrderBy> arrayList = new ArrayList<QueryOrderBy>(n);
            for (int i = 0; i < n; ++i) {
                if (i > 0) {
                    this.read(109);
                }
                arrayList.add(this.parseSortSpecification());
            }
            aggregate.setOrderByList(arrayList);
        } else if (bl3) {
            this.readAggregateOrder(aggregate, this.readExpression(), true);
        } else {
            aggregate.setOrderByList(this.parseSortSpecificationList());
        }
        return aggregate;
    }

    private void readAggregateOrder(Aggregate aggregate, Expression expression, boolean bl) {
        ArrayList<QueryOrderBy> arrayList = new ArrayList<QueryOrderBy>(1);
        QueryOrderBy queryOrderBy = new QueryOrderBy();
        queryOrderBy.expression = expression;
        if (bl) {
            queryOrderBy.sortType = this.parseSortType();
        }
        arrayList.add(queryOrderBy);
        aggregate.setOrderByList(arrayList);
    }

    private ArrayList<QueryOrderBy> readIfOrderBy() {
        if (this.readIf(62, "BY")) {
            return this.parseSortSpecificationList();
        }
        return null;
    }

    private ArrayList<QueryOrderBy> parseSortSpecificationList() {
        ArrayList<QueryOrderBy> arrayList = Utils.newSmallArrayList();
        do {
            arrayList.add(this.parseSortSpecification());
        } while (this.readIf(109));
        return arrayList;
    }

    private QueryOrderBy parseSortSpecification() {
        QueryOrderBy queryOrderBy = new QueryOrderBy();
        queryOrderBy.expression = this.readExpression();
        queryOrderBy.sortType = this.parseSortType();
        return queryOrderBy;
    }

    private Expression readUserDefinedFunctionIf(Schema schema, String string) {
        UserDefinedFunction userDefinedFunction = this.findUserDefinedFunctionWithinPath(schema, string);
        if (userDefinedFunction == null) {
            return null;
        }
        if (userDefinedFunction instanceof FunctionAlias) {
            FunctionAlias functionAlias = (FunctionAlias)userDefinedFunction;
            ArrayList<Expression> arrayList = Utils.newSmallArrayList();
            if (!this.readIf(106)) {
                do {
                    arrayList.add(this.readExpression());
                } while (this.readIfMore());
            }
            return new JavaFunction(functionAlias, arrayList.toArray(new Expression[0]));
        }
        UserAggregate userAggregate = (UserAggregate)userDefinedFunction;
        boolean bl = this.readDistinctAgg();
        ArrayList<Expression> arrayList = Utils.newSmallArrayList();
        do {
            arrayList.add(this.readExpression());
        } while (this.readIfMore());
        Expression[] expressionArray = arrayList.toArray(new Expression[0]);
        JavaAggregate javaAggregate = new JavaAggregate(userAggregate, expressionArray, this.currentSelect, bl);
        this.readFilterAndOver(javaAggregate);
        return javaAggregate;
    }

    private boolean readDistinctAgg() {
        if (this.readIf(26)) {
            return true;
        }
        this.readIf(3);
        return false;
    }

    private void readFilterAndOver(AbstractAggregate abstractAggregate) {
        if (this.readIf("FILTER", 105, 87)) {
            Expression expression = this.readExpression();
            this.read(106);
            abstractAggregate.setFilterCondition(expression);
        }
        this.readOver(abstractAggregate);
    }

    private void readOver(DataAnalysisOperation dataAnalysisOperation) {
        if (this.readIf("OVER")) {
            dataAnalysisOperation.setOverCondition(this.readWindowNameOrSpecification());
            this.currentSelect.setWindowQuery();
        } else if (dataAnalysisOperation.isAggregate()) {
            this.currentSelect.setGroupQuery();
        } else {
            throw this.getSyntaxError();
        }
    }

    private Window readWindowNameOrSpecification() {
        return this.isToken(105) ? this.readWindowSpecification() : new Window(this.readIdentifier(), null, null, null);
    }

    private Window readWindowSpecification() {
        Object object;
        Object object2;
        this.read(105);
        String string = null;
        if (this.currentTokenType == 2) {
            object2 = this.currentToken;
            if (this.token.isQuoted() || !this.equalsToken((String)object2, "PARTITION") && !this.equalsToken((String)object2, "ROWS") && !this.equalsToken((String)object2, "RANGE") && !this.equalsToken((String)object2, "GROUPS")) {
                string = object2;
                this.read();
            }
        }
        object2 = null;
        if (this.readIf("PARTITION", "BY")) {
            object2 = Utils.newSmallArrayList();
            do {
                object = this.readExpression();
                ((ArrayList)object2).add(object);
            } while (this.readIf(109));
        }
        object = this.readIfOrderBy();
        WindowFrame windowFrame = this.readWindowFrame();
        this.read(106);
        return new Window(string, (ArrayList<Expression>)object2, (ArrayList<QueryOrderBy>)object, windowFrame);
    }

    private WindowFrame readWindowFrame() {
        WindowFrame windowFrame;
        WindowFrameBound windowFrameBound;
        WindowFrameBound windowFrameBound2;
        WindowFrameUnits windowFrameUnits;
        if (this.readIf("ROWS")) {
            windowFrameUnits = WindowFrameUnits.ROWS;
        } else if (this.readIf("RANGE")) {
            windowFrameUnits = WindowFrameUnits.RANGE;
        } else if (this.readIf("GROUPS")) {
            windowFrameUnits = WindowFrameUnits.GROUPS;
        } else {
            return null;
        }
        if (this.readIf(10)) {
            windowFrameBound2 = this.readWindowFrameRange();
            this.read(4);
            windowFrameBound = this.readWindowFrameRange();
        } else {
            windowFrameBound2 = this.readWindowFrameStarting();
            windowFrameBound = null;
        }
        int n = this.token.start();
        WindowFrameExclusion windowFrameExclusion = WindowFrameExclusion.EXCLUDE_NO_OTHERS;
        if (this.readIf("EXCLUDE")) {
            if (this.readIf("CURRENT", 66)) {
                windowFrameExclusion = WindowFrameExclusion.EXCLUDE_CURRENT_ROW;
            } else if (this.readIf(37)) {
                windowFrameExclusion = WindowFrameExclusion.EXCLUDE_GROUP;
            } else if (this.readIf("TIES")) {
                windowFrameExclusion = WindowFrameExclusion.EXCLUDE_TIES;
            } else {
                this.read("NO");
                this.read("OTHERS");
            }
        }
        if (!(windowFrame = new WindowFrame(windowFrameUnits, windowFrameBound2, windowFrameBound, windowFrameExclusion)).isValid()) {
            throw DbException.getSyntaxError(this.sqlCommand, n);
        }
        return windowFrame;
    }

    private WindowFrameBound readWindowFrameStarting() {
        if (this.readIf("UNBOUNDED")) {
            this.read("PRECEDING");
            return new WindowFrameBound(WindowFrameBoundType.UNBOUNDED_PRECEDING, null);
        }
        if (this.readIf("CURRENT")) {
            this.read(66);
            return new WindowFrameBound(WindowFrameBoundType.CURRENT_ROW, null);
        }
        Expression expression = this.readExpression();
        this.read("PRECEDING");
        return new WindowFrameBound(WindowFrameBoundType.PRECEDING, expression);
    }

    private WindowFrameBound readWindowFrameRange() {
        if (this.readIf("UNBOUNDED")) {
            if (this.readIf("PRECEDING")) {
                return new WindowFrameBound(WindowFrameBoundType.UNBOUNDED_PRECEDING, null);
            }
            this.read("FOLLOWING");
            return new WindowFrameBound(WindowFrameBoundType.UNBOUNDED_FOLLOWING, null);
        }
        if (this.readIf("CURRENT")) {
            this.read(66);
            return new WindowFrameBound(WindowFrameBoundType.CURRENT_ROW, null);
        }
        Expression expression = this.readExpression();
        if (this.readIf("PRECEDING")) {
            return new WindowFrameBound(WindowFrameBoundType.PRECEDING, expression);
        }
        this.read("FOLLOWING");
        return new WindowFrameBound(WindowFrameBoundType.FOLLOWING, expression);
    }

    private Expression readFunction(Schema schema, String string) {
        Object object;
        String string2 = this.upperName(string);
        if (schema != null) {
            return this.readFunctionWithSchema(schema, string, string2);
        }
        boolean bl = this.database.isAllowBuiltinAliasOverride();
        if (bl && (object = this.readUserDefinedFunctionIf(null, string)) != null) {
            return object;
        }
        object = Aggregate.getAggregateType(string2);
        if (object != null) {
            return this.readAggregate((AggregateType)((Object)object), string2);
        }
        Expression expression = this.readBuiltinFunctionIf(string2);
        if (expression != null) {
            return expression;
        }
        expression = this.readWindowFunction(string2);
        if (expression != null) {
            return expression;
        }
        expression = this.readCompatibilityFunction(string2);
        if (expression != null) {
            return expression;
        }
        if (!bl && (expression = this.readUserDefinedFunctionIf(null, string)) != null) {
            return expression;
        }
        throw DbException.get(90022, string);
    }

    private Expression readFunctionWithSchema(Schema schema, String string, String string2) {
        Expression expression;
        if (this.database.getMode().getEnum() == Mode.ModeEnum.PostgreSQL && schema.getName().equals(this.database.sysIdentifier("PG_CATALOG")) && (expression = FunctionsPostgreSQL.getFunction(string2)) != null) {
            return this.readParameters(expression);
        }
        expression = this.readUserDefinedFunctionIf(schema, string);
        if (expression != null) {
            return expression;
        }
        throw DbException.get(90022, string);
    }

    private Expression readCompatibilityFunction(String string) {
        switch (string) {
            case "ARRAY_APPEND": 
            case "ARRAY_CAT": {
                return new ConcatenationOperation(this.readExpression(), this.readLastArgument());
            }
            case "ARRAY_GET": {
                return new ArrayElementReference(this.readExpression(), this.readLastArgument());
            }
            case "ARRAY_LENGTH": {
                return new CardinalityExpression(this.readSingleArgument(), false);
            }
            case "DECODE": {
                SimpleCase.SimpleWhen simpleWhen;
                Expression expression = this.readExpression();
                boolean bl = expression.isConstant() && !expression.getValue(this.session).containsNull();
                Expression expression2 = this.readNextArgument();
                Expression expression3 = this.readNextArgument();
                SimpleCase.SimpleWhen simpleWhen2 = simpleWhen = this.decodeToWhen(expression, bl, expression2, expression3);
                Expression expression4 = null;
                while (this.readIf(109)) {
                    expression2 = this.readExpression();
                    if (this.readIf(109)) {
                        expression3 = this.readExpression();
                        SimpleCase.SimpleWhen simpleWhen3 = this.decodeToWhen(expression, bl, expression2, expression3);
                        simpleWhen2.setWhen(simpleWhen3);
                        simpleWhen2 = simpleWhen3;
                        continue;
                    }
                    expression4 = expression2;
                    break;
                }
                this.read(106);
                return new SimpleCase(expression, simpleWhen, expression4);
            }
            case "CASEWHEN": {
                return this.readCompatibilityCase(this.readExpression());
            }
            case "NVL2": {
                return this.readCompatibilityCase(new NullPredicate(this.readExpression(), true, false));
            }
            case "CONVERT": {
                Expression expression;
                Column column;
                if (this.database.getMode().swapConvertFunctionParameters) {
                    column = this.parseColumnWithType(null);
                    expression = this.readNextArgument();
                } else {
                    expression = this.readExpression();
                    this.read(109);
                    column = this.parseColumnWithType(null);
                }
                this.read(106);
                return new CastSpecification(expression, column);
            }
            case "IFNULL": {
                return new CoalesceFunction(0, this.readExpression(), this.readLastArgument());
            }
            case "NVL": {
                return this.readCoalesceFunction(0);
            }
            case "DATABASE": {
                this.read(106);
                return new CurrentGeneralValueSpecification(0);
            }
            case "CURDATE": {
                return this.readCurrentDateTimeValueFunction(0, true, string);
            }
            case "TODAY": {
                this.read(106);
                return ModeFunction.getCompatibilityDateTimeValueFunction(this.database, "TODAY", -1);
            }
            case "SCHEMA": {
                this.read(106);
                return new CurrentGeneralValueSpecification(3);
            }
            case "SYSTIMESTAMP": {
                int n = -1;
                if (!this.readIf(106)) {
                    n = this.readInt();
                    if (n < 0 || n > 9) {
                        throw DbException.get(90151, Integer.toString(n), "0", "9");
                    }
                    this.read(106);
                }
                return ModeFunction.getCompatibilityDateTimeValueFunction(this.database, "SYSTIMESTAMP", n);
            }
            case "DAY": 
            case "DAY_OF_MONTH": 
            case "DAYOFMONTH": {
                return new DateTimeFunction(0, 2, this.readSingleArgument(), null);
            }
            case "DAY_OF_WEEK": 
            case "DAYOFWEEK": {
                return new DateTimeFunction(0, 20, this.readSingleArgument(), null);
            }
            case "DAY_OF_YEAR": 
            case "DAYOFYEAR": {
                return new DateTimeFunction(0, 16, this.readSingleArgument(), null);
            }
            case "HOUR": {
                return new DateTimeFunction(0, 3, this.readSingleArgument(), null);
            }
            case "ISO_DAY_OF_WEEK": {
                return new DateTimeFunction(0, 17, this.readSingleArgument(), null);
            }
            case "ISO_WEEK": {
                return new DateTimeFunction(0, 18, this.readSingleArgument(), null);
            }
            case "ISO_YEAR": {
                return new DateTimeFunction(0, 19, this.readSingleArgument(), null);
            }
            case "MINUTE": {
                return new DateTimeFunction(0, 4, this.readSingleArgument(), null);
            }
            case "MONTH": {
                return new DateTimeFunction(0, 1, this.readSingleArgument(), null);
            }
            case "QUARTER": {
                return new DateTimeFunction(0, 12, this.readSingleArgument(), null);
            }
            case "SECOND": {
                return new DateTimeFunction(0, 5, this.readSingleArgument(), null);
            }
            case "WEEK": {
                return new DateTimeFunction(0, 21, this.readSingleArgument(), null);
            }
            case "YEAR": {
                return new DateTimeFunction(0, 0, this.readSingleArgument(), null);
            }
            case "CURTIME": {
                return this.readCurrentDateTimeValueFunction(2, true, "CURTIME");
            }
            case "NOW": {
                return this.readCurrentDateTimeValueFunction(4, true, "NOW");
            }
            case "SYSDATE": {
                this.read(106);
                return ModeFunction.getCompatibilityDateTimeValueFunction(this.database, "SYSDATE", -1);
            }
            case "INSTR": {
                Expression expression = this.readExpression();
                return new StringFunction(this.readNextArgument(), expression, this.readIfArgument(), 0);
            }
            case "POSITION": {
                Expression expression = this.readConcat();
                if (!this.readIf(109)) {
                    this.read(41);
                }
                return new StringFunction(expression, this.readSingleArgument(), null, 0);
            }
            case "LCASE": {
                return new StringFunction1(this.readSingleArgument(), 1);
            }
            case "SUBSTR": {
                return this.readSubstringFunction();
            }
            case "UCASE": {
                return new StringFunction1(this.readSingleArgument(), 0);
            }
            case "CURRVAL": {
                return this.readCompatibilitySequenceValueFunction(true);
            }
            case "NEXTVAL": {
                return this.readCompatibilitySequenceValueFunction(false);
            }
        }
        return null;
    }

    private <T extends ExpressionWithVariableParameters> T readParameters(T t2) {
        if (!this.readIf(106)) {
            do {
                t2.addParameter(this.readExpression());
            } while (this.readIfMore());
        }
        t2.doneWithParameters();
        return t2;
    }

    private SimpleCase.SimpleWhen decodeToWhen(Expression expression, boolean bl, Expression expression2, Expression expression3) {
        if (!(bl || expression2.isConstant() && !expression2.getValue(this.session).containsNull())) {
            expression2 = new Comparison(6, expression, expression2, true);
        }
        return new SimpleCase.SimpleWhen(expression2, expression3);
    }

    private Expression readCompatibilityCase(Expression expression) {
        return new SearchedCase(new Expression[]{expression, this.readNextArgument(), this.readLastArgument()});
    }

    private Expression readCompatibilitySequenceValueFunction(boolean bl) {
        Expression expression = this.readExpression();
        Expression expression2 = this.readIf(109) ? this.readExpression() : null;
        this.read(106);
        return new CompatibilitySequenceValueFunction(expression, expression2, bl);
    }

    private Expression readBuiltinFunctionIf(String string) {
        switch (string) {
            case "ABS": {
                return new MathFunction(this.readSingleArgument(), null, 0);
            }
            case "MOD": {
                return new MathFunction(this.readExpression(), this.readLastArgument(), 1);
            }
            case "SIN": {
                return new MathFunction1(this.readSingleArgument(), 0);
            }
            case "COS": {
                return new MathFunction1(this.readSingleArgument(), 1);
            }
            case "TAN": {
                return new MathFunction1(this.readSingleArgument(), 2);
            }
            case "COT": {
                return new MathFunction1(this.readSingleArgument(), 3);
            }
            case "SINH": {
                return new MathFunction1(this.readSingleArgument(), 4);
            }
            case "COSH": {
                return new MathFunction1(this.readSingleArgument(), 5);
            }
            case "TANH": {
                return new MathFunction1(this.readSingleArgument(), 6);
            }
            case "ASIN": {
                return new MathFunction1(this.readSingleArgument(), 7);
            }
            case "ACOS": {
                return new MathFunction1(this.readSingleArgument(), 8);
            }
            case "ATAN": {
                return new MathFunction1(this.readSingleArgument(), 9);
            }
            case "ATAN2": {
                return new MathFunction2(this.readExpression(), this.readLastArgument(), 0);
            }
            case "LOG": {
                Expression expression = this.readExpression();
                if (this.readIf(109)) {
                    return new MathFunction2(expression, this.readSingleArgument(), 1);
                }
                this.read(106);
                return new MathFunction1(expression, this.database.getMode().logIsLogBase10 ? 10 : 11);
            }
            case "LOG10": {
                return new MathFunction1(this.readSingleArgument(), 10);
            }
            case "LN": {
                return new MathFunction1(this.readSingleArgument(), 11);
            }
            case "EXP": {
                return new MathFunction1(this.readSingleArgument(), 12);
            }
            case "POWER": {
                return new MathFunction2(this.readExpression(), this.readLastArgument(), 2);
            }
            case "SQRT": {
                return new MathFunction1(this.readSingleArgument(), 13);
            }
            case "FLOOR": {
                return new MathFunction(this.readSingleArgument(), null, 2);
            }
            case "CEIL": 
            case "CEILING": {
                return new MathFunction(this.readSingleArgument(), null, 3);
            }
            case "ROUND": {
                return new MathFunction(this.readExpression(), this.readIfArgument(), 4);
            }
            case "ROUNDMAGIC": {
                return new MathFunction(this.readSingleArgument(), null, 5);
            }
            case "SIGN": {
                return new MathFunction(this.readSingleArgument(), null, 6);
            }
            case "TRUNC": 
            case "TRUNCATE": {
                return new MathFunction(this.readExpression(), this.readIfArgument(), 7);
            }
            case "DEGREES": {
                return new MathFunction1(this.readSingleArgument(), 14);
            }
            case "RADIANS": {
                return new MathFunction1(this.readSingleArgument(), 15);
            }
            case "BITAND": {
                return new BitFunction(this.readExpression(), this.readLastArgument(), 0);
            }
            case "BITOR": {
                return new BitFunction(this.readExpression(), this.readLastArgument(), 1);
            }
            case "BITXOR": {
                return new BitFunction(this.readExpression(), this.readLastArgument(), 2);
            }
            case "BITNOT": {
                return new BitFunction(this.readSingleArgument(), null, 3);
            }
            case "BITNAND": {
                return new BitFunction(this.readExpression(), this.readLastArgument(), 4);
            }
            case "BITNOR": {
                return new BitFunction(this.readExpression(), this.readLastArgument(), 5);
            }
            case "BITXNOR": {
                return new BitFunction(this.readExpression(), this.readLastArgument(), 6);
            }
            case "BITGET": {
                return new BitFunction(this.readExpression(), this.readLastArgument(), 7);
            }
            case "BITCOUNT": {
                return new BitFunction(this.readSingleArgument(), null, 8);
            }
            case "LSHIFT": {
                return new BitFunction(this.readExpression(), this.readLastArgument(), 9);
            }
            case "RSHIFT": {
                return new BitFunction(this.readExpression(), this.readLastArgument(), 10);
            }
            case "ULSHIFT": {
                return new BitFunction(this.readExpression(), this.readLastArgument(), 11);
            }
            case "URSHIFT": {
                return new BitFunction(this.readExpression(), this.readLastArgument(), 12);
            }
            case "ROTATELEFT": {
                return new BitFunction(this.readExpression(), this.readLastArgument(), 13);
            }
            case "ROTATERIGHT": {
                return new BitFunction(this.readExpression(), this.readLastArgument(), 14);
            }
            case "EXTRACT": {
                int n = this.readDateTimeField();
                this.read(35);
                return new DateTimeFunction(0, n, this.readSingleArgument(), null);
            }
            case "DATE_TRUNC": {
                return new DateTimeFunction(1, this.readDateTimeField(), this.readLastArgument(), null);
            }
            case "DATEADD": 
            case "TIMESTAMPADD": {
                return new DateTimeFunction(2, this.readDateTimeField(), this.readNextArgument(), this.readLastArgument());
            }
            case "DATEDIFF": 
            case "TIMESTAMPDIFF": {
                return new DateTimeFunction(3, this.readDateTimeField(), this.readNextArgument(), this.readLastArgument());
            }
            case "LAST_DAY": {
                return new DateTimeFunction(4, -1, this.readSingleArgument(), null);
            }
            case "FORMATDATETIME": {
                return this.readDateTimeFormatFunction(0);
            }
            case "PARSEDATETIME": {
                return this.readDateTimeFormatFunction(1);
            }
            case "DAYNAME": {
                return new DayMonthNameFunction(this.readSingleArgument(), 0);
            }
            case "MONTHNAME": {
                return new DayMonthNameFunction(this.readSingleArgument(), 1);
            }
            case "CARDINALITY": {
                return new CardinalityExpression(this.readSingleArgument(), false);
            }
            case "ARRAY_MAX_CARDINALITY": {
                return new CardinalityExpression(this.readSingleArgument(), true);
            }
            case "LOCATE": {
                return new StringFunction(this.readExpression(), this.readNextArgument(), this.readIfArgument(), 0);
            }
            case "INSERT": {
                return new StringFunction(this.readExpression(), this.readNextArgument(), this.readNextArgument(), this.readLastArgument(), 1);
            }
            case "REPLACE": {
                return new StringFunction(this.readExpression(), this.readNextArgument(), this.readIfArgument(), 2);
            }
            case "LPAD": {
                return new StringFunction(this.readExpression(), this.readNextArgument(), this.readIfArgument(), 3);
            }
            case "RPAD": {
                return new StringFunction(this.readExpression(), this.readNextArgument(), this.readIfArgument(), 4);
            }
            case "TRANSLATE": {
                return new StringFunction(this.readExpression(), this.readNextArgument(), this.readLastArgument(), 5);
            }
            case "UPPER": {
                return new StringFunction1(this.readSingleArgument(), 0);
            }
            case "LOWER": {
                return new StringFunction1(this.readSingleArgument(), 1);
            }
            case "ASCII": {
                return new StringFunction1(this.readSingleArgument(), 2);
            }
            case "CHAR": 
            case "CHR": {
                return new StringFunction1(this.readSingleArgument(), 3);
            }
            case "STRINGENCODE": {
                return new StringFunction1(this.readSingleArgument(), 4);
            }
            case "STRINGDECODE": {
                return new StringFunction1(this.readSingleArgument(), 5);
            }
            case "STRINGTOUTF8": {
                return new StringFunction1(this.readSingleArgument(), 6);
            }
            case "UTF8TOSTRING": {
                return new StringFunction1(this.readSingleArgument(), 7);
            }
            case "HEXTORAW": {
                return new StringFunction1(this.readSingleArgument(), 8);
            }
            case "RAWTOHEX": {
                return new StringFunction1(this.readSingleArgument(), 9);
            }
            case "SPACE": {
                return new StringFunction1(this.readSingleArgument(), 10);
            }
            case "QUOTE_IDENT": {
                return new StringFunction1(this.readSingleArgument(), 11);
            }
            case "SUBSTRING": {
                return this.readSubstringFunction();
            }
            case "TO_CHAR": {
                Expression expression;
                Expression expression2;
                Expression expression3 = this.readExpression();
                if (this.readIf(109)) {
                    expression2 = this.readExpression();
                    expression = this.readIf(109) ? this.readExpression() : null;
                } else {
                    expression2 = null;
                    expression = null;
                }
                this.read(106);
                return new ToCharFunction(expression3, expression2, expression);
            }
            case "REPEAT": {
                return new StringFunction2(this.readExpression(), this.readLastArgument(), 2);
            }
            case "CHAR_LENGTH": 
            case "CHARACTER_LENGTH": 
            case "LENGTH": {
                return new LengthFunction(this.readIfSingleArgument(), 0);
            }
            case "OCTET_LENGTH": {
                return new LengthFunction(this.readIfSingleArgument(), 1);
            }
            case "BIT_LENGTH": {
                return new LengthFunction(this.readIfSingleArgument(), 2);
            }
            case "TRIM": {
                return this.readTrimFunction();
            }
            case "LTRIM": {
                return new TrimFunction(this.readExpression(), this.readIfArgument(), 5);
            }
            case "RTRIM": {
                return new TrimFunction(this.readExpression(), this.readIfArgument(), 6);
            }
            case "BTRIM": {
                return new TrimFunction(this.readExpression(), this.readIfArgument(), 7);
            }
            case "REGEXP_LIKE": {
                return this.readParameters(new RegexpFunction(0));
            }
            case "REGEXP_REPLACE": {
                return this.readParameters(new RegexpFunction(1));
            }
            case "REGEXP_SUBSTR": {
                return this.readParameters(new RegexpFunction(2));
            }
            case "XMLATTR": {
                return this.readParameters(new XMLFunction(0));
            }
            case "XMLCDATA": {
                return this.readParameters(new XMLFunction(1));
            }
            case "XMLCOMMENT": {
                return this.readParameters(new XMLFunction(2));
            }
            case "XMLNODE": {
                return this.readParameters(new XMLFunction(3));
            }
            case "XMLSTARTDOC": {
                return this.readParameters(new XMLFunction(4));
            }
            case "XMLTEXT": {
                return this.readParameters(new XMLFunction(5));
            }
            case "TRIM_ARRAY": {
                return new ArrayFunction(this.readExpression(), this.readLastArgument(), null, 0);
            }
            case "ARRAY_CONTAINS": {
                return new ArrayFunction(this.readExpression(), this.readLastArgument(), null, 1);
            }
            case "ARRAY_SLICE": {
                return new ArrayFunction(this.readExpression(), this.readNextArgument(), this.readLastArgument(), 2);
            }
            case "COMPRESS": {
                return new CompressFunction(this.readExpression(), this.readIfArgument(), 0);
            }
            case "EXPAND": {
                return new CompressFunction(this.readSingleArgument(), null, 1);
            }
            case "SOUNDEX": {
                return new SoundexFunction(this.readSingleArgument(), null, 0);
            }
            case "DIFFERENCE": {
                return new SoundexFunction(this.readExpression(), this.readLastArgument(), 1);
            }
            case "JSON_OBJECT": {
                JsonConstructorFunction jsonConstructorFunction = new JsonConstructorFunction(false);
                if (this.currentTokenType != 106 && !this.readJsonObjectFunctionFlags(jsonConstructorFunction, false)) {
                    do {
                        boolean bl = this.readIf(47);
                        jsonConstructorFunction.addParameter(this.readExpression());
                        if (bl) {
                            this.read(84);
                        } else if (!(this.readIf(84) || this.database.getMode().acceptsCommaAsJsonKeyValueSeparator && this.readIf(109))) {
                            this.read(116);
                        }
                        jsonConstructorFunction.addParameter(this.readExpression());
                    } while (this.readIf(109));
                    this.readJsonObjectFunctionFlags(jsonConstructorFunction, false);
                }
                this.read(106);
                jsonConstructorFunction.doneWithParameters();
                return jsonConstructorFunction;
            }
            case "JSON_ARRAY": {
                JsonConstructorFunction jsonConstructorFunction = new JsonConstructorFunction(true);
                jsonConstructorFunction.setFlags(1);
                if (this.currentTokenType != 106 && !this.readJsonObjectFunctionFlags(jsonConstructorFunction, true)) {
                    do {
                        jsonConstructorFunction.addParameter(this.readExpression());
                    } while (this.readIf(109));
                    this.readJsonObjectFunctionFlags(jsonConstructorFunction, true);
                }
                this.read(106);
                jsonConstructorFunction.doneWithParameters();
                return jsonConstructorFunction;
            }
            case "ENCRYPT": {
                return new CryptFunction(this.readExpression(), this.readNextArgument(), this.readLastArgument(), 0);
            }
            case "DECRYPT": {
                return new CryptFunction(this.readExpression(), this.readNextArgument(), this.readLastArgument(), 1);
            }
            case "COALESCE": {
                return this.readCoalesceFunction(0);
            }
            case "GREATEST": {
                return this.readCoalesceFunction(1);
            }
            case "LEAST": {
                return this.readCoalesceFunction(2);
            }
            case "NULLIF": {
                return new NullIfFunction(this.readExpression(), this.readLastArgument());
            }
            case "CONCAT": {
                return this.readConcatFunction(0);
            }
            case "CONCAT_WS": {
                return this.readConcatFunction(1);
            }
            case "HASH": {
                return new HashFunction(this.readExpression(), this.readNextArgument(), this.readIfArgument(), 0);
            }
            case "ORA_HASH": {
                Expression expression = this.readExpression();
                if (this.readIfMore()) {
                    return new HashFunction(expression, this.readExpression(), this.readIfArgument(), 1);
                }
                return new HashFunction(expression, 1);
            }
            case "RAND": 
            case "RANDOM": {
                return new RandFunction(this.readIfSingleArgument(), 0);
            }
            case "SECURE_RAND": {
                return new RandFunction(this.readSingleArgument(), 1);
            }
            case "RANDOM_UUID": 
            case "UUID": {
                this.read(106);
                return new RandFunction(null, 2);
            }
            case "ABORT_SESSION": {
                return new SessionControlFunction(this.readIfSingleArgument(), 0);
            }
            case "CANCEL_SESSION": {
                return new SessionControlFunction(this.readIfSingleArgument(), 1);
            }
            case "AUTOCOMMIT": {
                this.read(106);
                return new SysInfoFunction(0);
            }
            case "DATABASE_PATH": {
                this.read(106);
                return new SysInfoFunction(1);
            }
            case "H2VERSION": {
                this.read(106);
                return new SysInfoFunction(2);
            }
            case "LOCK_MODE": {
                this.read(106);
                return new SysInfoFunction(3);
            }
            case "LOCK_TIMEOUT": {
                this.read(106);
                return new SysInfoFunction(4);
            }
            case "MEMORY_FREE": {
                this.read(106);
                return new SysInfoFunction(5);
            }
            case "MEMORY_USED": {
                this.read(106);
                return new SysInfoFunction(6);
            }
            case "READONLY": {
                this.read(106);
                return new SysInfoFunction(7);
            }
            case "SESSION_ID": {
                this.read(106);
                return new SysInfoFunction(8);
            }
            case "TRANSACTION_ID": {
                this.read(106);
                return new SysInfoFunction(9);
            }
            case "DISK_SPACE_USED": {
                return new TableInfoFunction(this.readIfSingleArgument(), null, 0);
            }
            case "ESTIMATED_ENVELOPE": {
                return new TableInfoFunction(this.readExpression(), this.readLastArgument(), 1);
            }
            case "FILE_READ": {
                return new FileFunction(this.readExpression(), this.readIfArgument(), 0);
            }
            case "FILE_WRITE": {
                return new FileFunction(this.readExpression(), this.readLastArgument(), 1);
            }
            case "DATA_TYPE_SQL": {
                return new DataTypeSQLFunction(this.readExpression(), this.readNextArgument(), this.readNextArgument(), this.readLastArgument());
            }
            case "DB_OBJECT_ID": {
                return new DBObjectFunction(this.readExpression(), this.readNextArgument(), this.readIfArgument(), 0);
            }
            case "DB_OBJECT_SQL": {
                return new DBObjectFunction(this.readExpression(), this.readNextArgument(), this.readIfArgument(), 1);
            }
            case "CSVWRITE": {
                return this.readParameters(new CSVWriteFunction());
            }
            case "SIGNAL": {
                return new SignalFunction(this.readExpression(), this.readLastArgument());
            }
            case "TRUNCATE_VALUE": {
                return new TruncateValueFunction(this.readExpression(), this.readNextArgument(), this.readLastArgument());
            }
            case "ZERO": {
                this.read(106);
                return ValueExpression.get(ValueInteger.get(0));
            }
            case "PI": {
                this.read(106);
                return ValueExpression.get(ValueDouble.get(Math.PI));
            }
        }
        Object object = ModeFunction.getFunction(this.database, string);
        return object != null ? (Expression)this.readParameters(object) : null;
    }

    private Expression readDateTimeFormatFunction(int n) {
        DateTimeFormatFunction dateTimeFormatFunction = new DateTimeFormatFunction(n);
        dateTimeFormatFunction.addParameter(this.readExpression());
        this.read(109);
        dateTimeFormatFunction.addParameter(this.readExpression());
        if (this.readIf(109)) {
            dateTimeFormatFunction.addParameter(this.readExpression());
            if (this.readIf(109)) {
                dateTimeFormatFunction.addParameter(this.readExpression());
            }
        }
        this.read(106);
        dateTimeFormatFunction.doneWithParameters();
        return dateTimeFormatFunction;
    }

    private Expression readTrimFunction() {
        Expression expression;
        int n;
        boolean bl = false;
        if (this.readIf("LEADING")) {
            n = 1;
            bl = true;
        } else if (this.readIf("TRAILING")) {
            n = 2;
            bl = true;
        } else {
            bl = this.readIf("BOTH");
            n = 3;
        }
        Expression expression2 = null;
        if (bl) {
            if (!this.readIf(35)) {
                expression2 = this.readExpression();
                this.read(35);
            }
            expression = this.readExpression();
        } else if (this.readIf(35)) {
            expression = this.readExpression();
        } else {
            expression = this.readExpression();
            if (this.readIf(35)) {
                expression2 = expression;
                expression = this.readExpression();
            } else if (this.readIfCompat(109)) {
                expression2 = this.readExpression();
            }
        }
        this.read(106);
        return new TrimFunction(expression, expression2, n);
    }

    private ArrayTableFunction readUnnestFunction() {
        ArrayTableFunction arrayTableFunction = new ArrayTableFunction(0);
        ArrayList<Column> arrayList = Utils.newSmallArrayList();
        if (!this.readIf(106)) {
            int n = 0;
            do {
                Expression expression = this.readExpression();
                TypeInfo typeInfo = TypeInfo.TYPE_NULL;
                boolean bl = expression.isConstant();
                if (bl || expression instanceof CastSpecification) {
                    if (bl) {
                        expression = expression.optimize(this.session);
                    }
                    TypeInfo typeInfo2 = expression.getType();
                    switch (typeInfo2.getValueType()) {
                        case 38: {
                            typeInfo = TypeInfo.TYPE_JSON;
                            break;
                        }
                        case 40: {
                            typeInfo = (TypeInfo)typeInfo2.getExtTypeInfo();
                        }
                    }
                }
                arrayTableFunction.addParameter(expression);
                arrayList.add(new Column("C" + ++n, typeInfo));
            } while (this.readIfMore());
        }
        if (this.readIf(89, "ORDINALITY")) {
            arrayList.add(new Column("NORD", TypeInfo.TYPE_INTEGER));
        }
        arrayTableFunction.setColumns(arrayList);
        arrayTableFunction.doneWithParameters();
        return arrayTableFunction;
    }

    private ArrayTableFunction readTableFunction(int n) {
        ArrayTableFunction arrayTableFunction = new ArrayTableFunction(n);
        ArrayList<Column> arrayList = Utils.newSmallArrayList();
        do {
            arrayList.add(this.parseColumnWithType(this.readIdentifier()));
            this.read(95);
            arrayTableFunction.addParameter(this.readExpression());
        } while (this.readIfMore());
        arrayTableFunction.setColumns(arrayList);
        arrayTableFunction.doneWithParameters();
        return arrayTableFunction;
    }

    private Expression readSingleArgument() {
        Expression expression = this.readExpression();
        this.read(106);
        return expression;
    }

    private Expression readNextArgument() {
        this.read(109);
        return this.readExpression();
    }

    private Expression readLastArgument() {
        this.read(109);
        Expression expression = this.readExpression();
        this.read(106);
        return expression;
    }

    private Expression readIfSingleArgument() {
        Expression expression;
        if (this.readIf(106)) {
            expression = null;
        } else {
            expression = this.readExpression();
            this.read(106);
        }
        return expression;
    }

    private Expression readIfArgument() {
        Expression expression = this.readIf(109) ? this.readExpression() : null;
        this.read(106);
        return expression;
    }

    private Expression readCoalesceFunction(int n) {
        CoalesceFunction coalesceFunction = new CoalesceFunction(n);
        coalesceFunction.addParameter(this.readExpression());
        while (this.readIfMore()) {
            coalesceFunction.addParameter(this.readExpression());
        }
        if (n == 1 || n == 2) {
            coalesceFunction.setIgnoreNulls(this.readIgnoreNulls(this.database.getMode().greatestLeastIgnoreNulls));
        }
        coalesceFunction.doneWithParameters();
        return coalesceFunction;
    }

    private Expression readConcatFunction(int n) {
        ConcatFunction concatFunction = new ConcatFunction(n);
        concatFunction.addParameter(this.readExpression());
        concatFunction.addParameter(this.readNextArgument());
        if (n == 1) {
            concatFunction.addParameter(this.readNextArgument());
        }
        while (this.readIfMore()) {
            concatFunction.addParameter(this.readExpression());
        }
        concatFunction.doneWithParameters();
        return concatFunction;
    }

    private Expression readSubstringFunction() {
        SubstringFunction substringFunction = new SubstringFunction();
        substringFunction.addParameter(this.readExpression());
        if (this.readIf(35)) {
            substringFunction.addParameter(this.readExpression());
            if (this.readIf(33)) {
                substringFunction.addParameter(this.readExpression());
            }
        } else if (this.readIf(33)) {
            substringFunction.addParameter(ValueExpression.get(ValueInteger.get(1)));
            substringFunction.addParameter(this.readExpression());
        } else {
            this.readCompat(109);
            substringFunction.addParameter(this.readExpression());
            if (this.readIf(109)) {
                substringFunction.addParameter(this.readExpression());
            }
        }
        this.read(106);
        substringFunction.doneWithParameters();
        return substringFunction;
    }

    private int readDateTimeField() {
        int n = -1;
        switch (this.currentTokenType) {
            case 2: {
                if (this.token.isQuoted()) break;
                n = DateTimeFunction.getField(this.currentToken);
                break;
            }
            case 94: {
                if (this.token.value(this.session).getValueType() != 2) break;
                n = DateTimeFunction.getField(this.token.value(this.session).getString());
                break;
            }
            case 90: {
                n = 0;
                break;
            }
            case 55: {
                n = 1;
                break;
            }
            case 24: {
                n = 2;
                break;
            }
            case 39: {
                n = 3;
                break;
            }
            case 54: {
                n = 4;
                break;
            }
            case 68: {
                n = 5;
            }
        }
        if (n < 0) {
            this.addExpected("date-time field");
            throw this.getSyntaxError();
        }
        this.read();
        return n;
    }

    private WindowFunction readWindowFunction(String string) {
        WindowFunctionType windowFunctionType = WindowFunctionType.get(string);
        if (windowFunctionType == null) {
            return null;
        }
        if (this.currentSelect == null) {
            throw this.getSyntaxError();
        }
        int n = WindowFunction.getMinArgumentCount(windowFunctionType);
        Expression[] expressionArray = null;
        if (n > 0) {
            int n2 = WindowFunction.getMaxArgumentCount(windowFunctionType);
            expressionArray = new Expression[n2];
            if (n == n2) {
                for (int i = 0; i < n; ++i) {
                    if (i > 0) {
                        this.read(109);
                    }
                    expressionArray[i] = this.readExpression();
                }
            } else {
                int n3;
                for (n3 = 0; n3 < n2 && (n3 <= 0 || this.readIf(109)); ++n3) {
                    expressionArray[n3] = this.readExpression();
                }
                if (n3 < n) {
                    throw this.getSyntaxError();
                }
                if (n3 != n2) {
                    expressionArray = Arrays.copyOf(expressionArray, n3);
                }
            }
        }
        this.read(106);
        WindowFunction windowFunction = new WindowFunction(windowFunctionType, this.currentSelect, expressionArray);
        switch (windowFunctionType) {
            case NTH_VALUE: {
                this.readFromFirstOrLast(windowFunction);
            }
            case LEAD: 
            case LAG: 
            case FIRST_VALUE: 
            case LAST_VALUE: {
                windowFunction.setIgnoreNulls(this.readIgnoreNulls(false));
            }
        }
        this.readOver(windowFunction);
        return windowFunction;
    }

    private void readFromFirstOrLast(WindowFunction windowFunction) {
        if (this.readIf(35, "LAST")) {
            windowFunction.setFromLast(true);
        } else {
            this.readIf(35, "FIRST");
        }
    }

    private boolean readIgnoreNulls(boolean bl) {
        if (this.readIf("IGNORE", "NULLS")) {
            return true;
        }
        if (this.readIf("RESPECT", "NULLS")) {
            return false;
        }
        return bl;
    }

    private boolean readJsonObjectFunctionFlags(ExpressionWithFlags expressionWithFlags, boolean bl) {
        boolean bl2 = false;
        int n = expressionWithFlags.getFlags();
        if (this.readIf(58, 60, 58)) {
            n &= 0xFFFFFFFE;
            bl2 = true;
        } else if (this.readIf("ABSENT", 60, 58)) {
            n |= 1;
            bl2 = true;
        }
        if (!bl) {
            if (this.readIf(89, 80, "KEYS")) {
                n |= 2;
                bl2 = true;
            } else if (this.readIf("WITHOUT", 80, "KEYS")) {
                n &= 0xFFFFFFFD;
                bl2 = true;
            }
        }
        if (bl2) {
            expressionWithFlags.setFlags(n);
        }
        return bl2;
    }

    private Expression readKeywordCompatibilityFunctionOrColumn() {
        boolean bl = this.nonKeywords != null && this.nonKeywords.get(this.currentTokenType);
        String string = this.currentToken;
        this.read();
        if (this.readIf(105)) {
            return this.readCompatibilityFunction(this.upperName(string));
        }
        if (bl) {
            return this.readIf(110) ? this.readTermObjectDot(string) : new ExpressionColumn(this.database, null, null, string);
        }
        throw this.getSyntaxError();
    }

    private Expression readCurrentDateTimeValueFunction(int n, boolean bl, String string) {
        FunctionAlias functionAlias;
        int n2 = -1;
        if (bl) {
            if (n != 0 && this.currentTokenType != 106 && ((n2 = this.readInt()) < 0 || n2 > 9)) {
                throw DbException.get(90151, Integer.toString(n2), "0", "9");
            }
            this.read(106);
        }
        if (this.database.isAllowBuiltinAliasOverride() && (functionAlias = this.database.getSchema(this.session.getCurrentSchemaName()).findFunction(string != null ? string : CurrentDateTimeValueFunction.getName(n))) != null) {
            Expression[] expressionArray;
            if (n2 >= 0) {
                Expression[] expressionArray2 = new Expression[1];
                expressionArray = expressionArray2;
                expressionArray2[0] = ValueExpression.get(ValueInteger.get(n2));
            } else {
                expressionArray = new Expression[]{};
            }
            return new JavaFunction(functionAlias, expressionArray);
        }
        return new CurrentDateTimeValueFunction(n, n2);
    }

    private Expression readIfWildcardRowidOrSequencePseudoColumn(String string, String string2) {
        if (this.readIf(108)) {
            return this.parseWildcard(string, string2);
        }
        if (this.readIf(91)) {
            return new ExpressionColumn(this.database, string, string2);
        }
        if (this.database.getMode().nextvalAndCurrvalPseudoColumns) {
            return this.readIfSequencePseudoColumn(string, string2);
        }
        return null;
    }

    private Wildcard parseWildcard(String string, String string2) {
        Wildcard wildcard = new Wildcard(string, string2);
        if (this.readIf(29, 105)) {
            ArrayList<ExpressionColumn> arrayList = Utils.newSmallArrayList();
            do {
                String string3 = null;
                String string4 = null;
                String string5 = this.readIdentifier();
                if (this.readIf(110)) {
                    string4 = string5;
                    string5 = this.readIdentifier();
                    if (this.readIf(110)) {
                        string3 = string4;
                        string4 = string5;
                        string5 = this.readIdentifier();
                        if (this.readIf(110)) {
                            this.checkDatabaseName(string3);
                            string3 = string4;
                            string4 = string5;
                            string5 = this.readIdentifier();
                        }
                    }
                }
                arrayList.add(new ExpressionColumn(this.database, string3, string4, string5));
            } while (this.readIfMore());
            wildcard.setExceptColumns(arrayList);
        }
        return wildcard;
    }

    private SequenceValue readIfSequencePseudoColumn(String string, String string2) {
        Sequence sequence2;
        if (string == null) {
            string = this.session.getCurrentSchemaName();
        }
        if (this.isTokenCompat("NEXTVAL")) {
            Sequence sequence3 = this.findSequence(string, string2);
            if (sequence3 != null) {
                this.read();
                return new SequenceValue(sequence3, this.getCurrentPreparedOrSelect());
            }
        } else if (this.isTokenCompat("CURRVAL") && (sequence2 = this.findSequence(string, string2)) != null) {
            this.read();
            return new SequenceValue(sequence2);
        }
        return null;
    }

    private Expression readTermObjectDot(String string) {
        Expression expression = this.readIfWildcardRowidOrSequencePseudoColumn(null, string);
        if (expression != null) {
            return expression;
        }
        String string2 = this.readIdentifier();
        if (this.readIf(105)) {
            return this.readFunction(this.database.getSchema(string), string2);
        }
        if (this.readIf(110)) {
            String string3 = string;
            expression = this.readIfWildcardRowidOrSequencePseudoColumn(string3, string = string2);
            if (expression != null) {
                return expression;
            }
            string2 = this.readIdentifier();
            if (this.readIf(105)) {
                this.checkDatabaseName(string3);
                return this.readFunction(this.database.getSchema(string), string2);
            }
            if (this.readIf(110)) {
                this.checkDatabaseName(string3);
                string3 = string;
                string = string2;
                expression = this.readIfWildcardRowidOrSequencePseudoColumn(string3, string);
                if (expression != null) {
                    return expression;
                }
                string2 = this.readIdentifier();
            }
            return new ExpressionColumn(this.database, string3, string, string2);
        }
        return new ExpressionColumn(this.database, null, string, string2);
    }

    private void checkDatabaseName(String string) {
        if (!this.database.getIgnoreCatalogs() && !this.equalsToken(this.database.getShortName(), string)) {
            throw DbException.get(90013, string);
        }
    }

    private Expression readTerm() {
        Expression expression;
        block8: {
            int n;
            Expression expression2 = expression = this.currentTokenType == 2 ? this.readTermWithIdentifier() : this.readTermWithoutIdentifier();
            while (true) {
                if (this.readIf(117)) {
                    expression = new ArrayElementReference(expression, this.readExpression());
                    this.read(118);
                    continue;
                }
                if (this.readIf(110)) {
                    expression = new FieldReference(expression, this.readIdentifier());
                    continue;
                }
                if (this.readIf(120)) {
                    expression = this.readColonColonAfterTerm(expression);
                    continue;
                }
                TypeInfo typeInfo = this.readIntervalQualifier();
                if (typeInfo != null) {
                    expression = new CastSpecification(expression, typeInfo);
                    continue;
                }
                n = this.tokenIndex;
                if (this.readIf("AT")) {
                    if (this.readIf("TIME", "ZONE")) {
                        expression = new TimeZoneOperation(expression, this.readExpression());
                        continue;
                    }
                    if (this.readIf("LOCAL")) {
                        expression = new TimeZoneOperation(expression, null);
                        continue;
                    }
                    this.setTokenIndex(n);
                    break block8;
                }
                if (!this.readIf("FORMAT")) break block8;
                if (!this.readIf("JSON")) break;
                expression = new Format(expression, Format.FormatEnum.JSON);
            }
            this.setTokenIndex(n);
        }
        return expression;
    }

    private Expression readTermWithoutIdentifier() {
        Expression expression;
        switch (this.currentTokenType) {
            case 101: {
                this.read();
                expression = new Variable(this.session, this.readIdentifier());
                if (!this.readIf(121)) break;
                expression = new SetFunction(expression, this.readExpression());
                break;
            }
            case 92: {
                expression = this.readParameter();
                break;
            }
            case 69: 
            case 75: 
            case 89: {
                expression = new Subquery(this.parseQuery());
                break;
            }
            case 102: {
                this.read();
                if (this.currentTokenType == 94) {
                    expression = ValueExpression.get(this.token.value(this.session).negate());
                    int n = expression.getType().getValueType();
                    if (n == 12 && expression.getValue(this.session).getLong() == Integer.MIN_VALUE) {
                        expression = ValueExpression.get(ValueInteger.get(Integer.MIN_VALUE));
                    } else if (n == 13 && expression.getValue(this.session).getBigDecimal().compareTo(Value.MIN_LONG_DECIMAL) == 0) {
                        expression = ValueExpression.get(ValueBigint.MIN);
                    }
                    this.read();
                    break;
                }
                expression = new UnaryOperation(this.readTerm());
                break;
            }
            case 103: {
                this.read();
                expression = this.readTerm();
                break;
            }
            case 105: {
                TypeInfo typeInfo;
                BinaryOperation binaryOperation;
                this.read();
                if (this.readIf(106)) {
                    expression = ValueExpression.get(ValueRow.EMPTY);
                    break;
                }
                if (this.isQuery()) {
                    expression = new Subquery(this.parseQuery());
                    this.read(106);
                    break;
                }
                expression = this.readExpression();
                if (this.readIfMore()) {
                    ArrayList<Expression> arrayList = Utils.newSmallArrayList();
                    arrayList.add(expression);
                    do {
                        arrayList.add(this.readExpression());
                    } while (this.readIfMore());
                    expression = new ExpressionList(arrayList.toArray(new Expression[0]), false);
                    break;
                }
                if (!(expression instanceof BinaryOperation) || (binaryOperation = (BinaryOperation)expression).getOperationType() != BinaryOperation.OpType.MINUS || (typeInfo = this.readIntervalQualifier()) == null) break;
                binaryOperation.setForcedType(typeInfo);
                break;
            }
            case 6: {
                this.read();
                if (this.readIf(117)) {
                    if (this.readIf(118)) {
                        expression = ValueExpression.get(ValueArray.EMPTY);
                        break;
                    }
                    ArrayList<Expression> arrayList = Utils.newSmallArrayList();
                    do {
                        arrayList.add(this.readExpression());
                    } while (this.readIf(109));
                    this.read(118);
                    expression = new ExpressionList(arrayList.toArray(new Expression[0]), true);
                    break;
                }
                this.read(105);
                Query query = this.parseQuery();
                this.read(106);
                expression = new ArrayConstructorByQuery(query);
                break;
            }
            case 44: {
                this.read();
                expression = this.readInterval();
                break;
            }
            case 66: {
                if (this.readIf(66, 105)) {
                    if (this.readIf(106)) {
                        expression = ValueExpression.get(ValueRow.EMPTY);
                        break;
                    }
                    ArrayList<Expression> arrayList = Utils.newSmallArrayList();
                    do {
                        arrayList.add(this.readExpression());
                    } while (this.readIfMore());
                    expression = new ExpressionList(arrayList.toArray(new Expression[0]), false);
                    break;
                }
                expression = this.readTermWithIdentifier();
                break;
            }
            case 77: {
                this.read();
                expression = ValueExpression.TRUE;
                break;
            }
            case 31: {
                this.read();
                expression = ValueExpression.FALSE;
                break;
            }
            case 81: {
                this.read();
                expression = TypedValueExpression.UNKNOWN;
                break;
            }
            case 67: {
                this.read();
                if (this.readIf(105)) {
                    this.read(106);
                }
                if (this.currentSelect == null && this.currentPrepared == null) {
                    throw this.getSyntaxError();
                }
                expression = new Rownum(this.getCurrentPreparedOrSelect());
                break;
            }
            case 58: {
                this.read();
                expression = ValueExpression.NULL;
                break;
            }
            case 91: {
                this.read();
                expression = new ExpressionColumn(this.database, null, null);
                break;
            }
            case 94: {
                expression = ValueExpression.get(this.token.value(this.session));
                this.read();
                break;
            }
            case 85: {
                if (this.database.getMode().onDuplicateKeyUpdate) {
                    if (this.currentPrepared instanceof Insert) {
                        expression = this.readOnDuplicateKeyValues(((Insert)this.currentPrepared).getTable(), null);
                        break;
                    }
                    if (this.currentPrepared instanceof Update) {
                        Update update = (Update)this.currentPrepared;
                        expression = this.readOnDuplicateKeyValues(update.getTable(), update);
                        break;
                    }
                }
                expression = new Subquery(this.parseQuery());
                break;
            }
            case 11: {
                this.read();
                expression = this.readCase();
                break;
            }
            case 12: {
                this.read();
                this.read(105);
                Expression expression2 = this.readExpression();
                this.read(7);
                Column column = this.parseColumnWithType(null);
                Expression expression3 = this.readIf("FORMAT") ? this.readExpression() : null;
                this.read(106);
                expression = new CastSpecification(expression2, column, expression3);
                break;
            }
            case 16: {
                expression = this.readCurrentGeneralValueSpecification(0);
                break;
            }
            case 17: {
                this.read();
                expression = this.readCurrentDateTimeValueFunction(0, this.readIf(105), null);
                break;
            }
            case 18: {
                expression = this.readCurrentGeneralValueSpecification(1);
                break;
            }
            case 19: {
                expression = this.readCurrentGeneralValueSpecification(2);
                break;
            }
            case 20: {
                expression = this.readCurrentGeneralValueSpecification(3);
                break;
            }
            case 21: {
                this.read();
                expression = this.readCurrentDateTimeValueFunction(1, this.readIf(105), null);
                break;
            }
            case 22: {
                this.read();
                expression = this.readCurrentDateTimeValueFunction(3, this.readIf(105), null);
                break;
            }
            case 23: 
            case 82: {
                expression = this.readCurrentGeneralValueSpecification(4);
                break;
            }
            case 70: {
                expression = this.readCurrentGeneralValueSpecification(5);
                break;
            }
            case 74: {
                expression = this.readCurrentGeneralValueSpecification(6);
                break;
            }
            case 5: 
            case 72: {
                this.read();
                this.read(105);
                expression = this.readAggregate(AggregateType.ANY, "ANY");
                break;
            }
            case 24: 
            case 39: 
            case 54: 
            case 55: 
            case 68: 
            case 90: {
                expression = this.readKeywordCompatibilityFunctionOrColumn();
                break;
            }
            case 48: {
                expression = this.readColumnIfNotFunction();
                if (expression != null) break;
                expression = new StringFunction2(this.readExpression(), this.readLastArgument(), 0);
                break;
            }
            case 51: {
                this.read();
                expression = this.readCurrentDateTimeValueFunction(2, this.readIf(105), null);
                break;
            }
            case 52: {
                this.read();
                expression = this.readCurrentDateTimeValueFunction(4, this.readIf(105), null);
                break;
            }
            case 65: {
                expression = this.readColumnIfNotFunction();
                if (expression != null) break;
                expression = new StringFunction2(this.readExpression(), this.readLastArgument(), 1);
                break;
            }
            case 71: {
                expression = this.readColumnIfNotFunction();
                if (expression != null) break;
                expression = this.readSetFunction();
                break;
            }
            case 84: {
                if (this.parseDomainConstraint) {
                    this.read();
                    expression = new DomainValueExpression();
                    break;
                }
            }
            default: {
                if (!this.isIdentifier()) {
                    throw this.getSyntaxError();
                }
                expression = this.readTermWithIdentifier();
            }
        }
        return expression;
    }

    private Expression readTermWithIdentifier() {
        String string = this.currentToken;
        boolean bl = this.token.isQuoted();
        this.read();
        Expression expression = this.readIf(105) ? this.readFunction(null, string) : (this.readIf(110) ? this.readTermObjectDot(string) : (bl ? new ExpressionColumn(this.database, null, null, string) : this.readTermWithIdentifier(string, bl)));
        return expression;
    }

    private Expression readColonColonAfterTerm(Expression expression) {
        if (this.database.getMode().getEnum() == Mode.ModeEnum.PostgreSQL) {
            if (this.readIfCompat("PG_CATALOG")) {
                this.read(110);
            }
            if (this.readIfCompat("REGCLASS")) {
                return new Regclass(expression);
            }
        }
        return new CastSpecification(expression, this.parseColumnWithType(null));
    }

    private Expression readCurrentGeneralValueSpecification(int n) {
        this.read();
        if (this.readIf(105)) {
            this.read(106);
        }
        return new CurrentGeneralValueSpecification(n);
    }

    private Expression readColumnIfNotFunction() {
        boolean bl = this.nonKeywords != null && this.nonKeywords.get(this.currentTokenType);
        String string = this.currentToken;
        this.read();
        if (this.readIf(105)) {
            return null;
        }
        if (bl) {
            return this.readIf(110) ? this.readTermObjectDot(string) : new ExpressionColumn(this.database, null, null, string);
        }
        throw this.getSyntaxError();
    }

    private Expression readSetFunction() {
        FunctionAlias functionAlias;
        SetFunction setFunction = new SetFunction(this.readExpression(), this.readLastArgument());
        if (this.database.isAllowBuiltinAliasOverride() && (functionAlias = this.database.getSchema(this.session.getCurrentSchemaName()).findFunction(setFunction.getName())) != null) {
            return new JavaFunction(functionAlias, new Expression[]{setFunction.getSubexpression(0), setFunction.getSubexpression(1)});
        }
        return setFunction;
    }

    private Expression readOnDuplicateKeyValues(Table table, Update update) {
        this.read();
        this.read(105);
        Column column = this.readTableColumn(new TableFilter(this.session, table, null, this.rightsChecked, null, 0, null));
        this.read(106);
        return new OnDuplicateKeyValues(column, update);
    }

    private Expression readTermWithIdentifier(String string, boolean bl) {
        switch (string.charAt(0) & 0xFFDF) {
            case 67: {
                if (!this.equalsToken("CURRENT", string)) break;
                if (this.readIf(84, 33)) {
                    return new SequenceValue(this.readSequence());
                }
                if (this.database.getMode().getEnum() != Mode.ModeEnum.DB2) break;
                return this.parseDB2SpecialRegisters(string);
            }
            case 68: {
                if (this.currentTokenType != 94 || this.token.value(this.session).getValueType() != 2 || !this.equalsToken("DATE", string) && !this.equalsToken("D", string)) break;
                String string2 = this.token.value(this.session).getString();
                this.read();
                return ValueExpression.get(ValueDate.parse(string2));
            }
            case 69: {
                if (this.currentTokenType != 94 || this.token.value(this.session).getValueType() != 2 || !this.equalsToken("E", string)) break;
                String string3 = this.token.value(this.session).getString();
                string3 = StringUtils.replaceAll(string3, "\\\\", "\\");
                this.read();
                return ValueExpression.get(ValueVarchar.get(string3));
            }
            case 71: {
                if (this.currentTokenType != 94) break;
                int n = this.token.value(this.session).getValueType();
                if (n == 2 && this.equalsToken("GEOMETRY", string)) {
                    ValueExpression valueExpression = ValueExpression.get(ValueGeometry.get(this.token.value(this.session).getString()));
                    this.read();
                    return valueExpression;
                }
                if (n != 6 || !this.equalsToken("GEOMETRY", string)) break;
                ValueExpression valueExpression = ValueExpression.get(ValueGeometry.getFromEWKB(this.token.value(this.session).getBytesNoCopy()));
                this.read();
                return valueExpression;
            }
            case 74: {
                if (this.currentTokenType != 94) break;
                int n = this.token.value(this.session).getValueType();
                if (n == 2 && this.equalsToken("JSON", string)) {
                    ValueExpression valueExpression = ValueExpression.get(ValueJson.fromJson(this.token.value(this.session).getString()));
                    this.read();
                    return valueExpression;
                }
                if (n != 6 || !this.equalsToken("JSON", string)) break;
                ValueExpression valueExpression = ValueExpression.get(ValueJson.fromJson(this.token.value(this.session).getBytesNoCopy()));
                this.read();
                return valueExpression;
            }
            case 78: {
                if (!this.equalsToken("NEXT", string) || !this.readIf(84, 33)) break;
                return new SequenceValue(this.readSequence(), this.getCurrentPreparedOrSelect());
            }
            case 84: {
                if (this.equalsToken("TIME", string)) {
                    if (this.readIf(89, "TIME", "ZONE")) {
                        if (this.currentTokenType != 94 || this.token.value(this.session).getValueType() != 2) {
                            throw this.getSyntaxError();
                        }
                        String string4 = this.token.value(this.session).getString();
                        this.read();
                        return ValueExpression.get(ValueTimeTimeZone.parse(string4, this.session));
                    }
                    boolean bl2 = this.readIf("WITHOUT", "TIME", "ZONE");
                    if (this.currentTokenType == 94 && this.token.value(this.session).getValueType() == 2) {
                        String string5 = this.token.value(this.session).getString();
                        this.read();
                        return ValueExpression.get(ValueTime.parse(string5, this.session));
                    }
                    if (!bl2) break;
                    throw this.getSyntaxError();
                }
                if (this.equalsToken("TIMESTAMP", string)) {
                    if (this.readIf(89, "TIME", "ZONE")) {
                        if (this.currentTokenType != 94 || this.token.value(this.session).getValueType() != 2) {
                            throw this.getSyntaxError();
                        }
                        String string6 = this.token.value(this.session).getString();
                        this.read();
                        return ValueExpression.get(ValueTimestampTimeZone.parse(string6, this.session));
                    }
                    boolean bl3 = this.readIf("WITHOUT", "TIME", "ZONE");
                    if (this.currentTokenType == 94 && this.token.value(this.session).getValueType() == 2) {
                        String string7 = this.token.value(this.session).getString();
                        this.read();
                        return ValueExpression.get(ValueTimestamp.parse(string7, this.session));
                    }
                    if (!bl3) break;
                    throw this.getSyntaxError();
                }
                if (this.currentTokenType != 94 || this.token.value(this.session).getValueType() != 2) break;
                if (this.equalsToken("T", string)) {
                    String string8 = this.token.value(this.session).getString();
                    this.read();
                    return ValueExpression.get(ValueTime.parse(string8, this.session));
                }
                if (!this.equalsToken("TS", string)) break;
                String string9 = this.token.value(this.session).getString();
                this.read();
                return ValueExpression.get(ValueTimestamp.parse(string9, this.session));
            }
            case 85: {
                if (this.currentTokenType != 94 || this.token.value(this.session).getValueType() != 2 || !this.equalsToken("UUID", string)) break;
                String string10 = this.token.value(this.session).getString();
                this.read();
                return ValueExpression.get(ValueUuid.get(string10));
            }
        }
        return new ExpressionColumn(this.database, null, null, string, bl);
    }

    private Prepared getCurrentPreparedOrSelect() {
        Prepared prepared = this.currentPrepared;
        return prepared != null ? prepared : this.currentSelect;
    }

    private Expression readInterval() {
        boolean bl = this.readIf(102);
        if (!bl) {
            this.readIf(103);
        }
        if (this.currentTokenType != 94 || this.token.value(this.session).getValueType() != 2) {
            this.addExpected("string");
            throw this.getSyntaxError();
        }
        String string = this.token.value(this.session).getString();
        this.read();
        TypeInfo typeInfo = this.readIntervalQualifier();
        try {
            ValueInterval valueInterval = IntervalUtils.parseInterval(IntervalQualifier.valueOf(typeInfo.getValueType() - 22), bl, string);
            if (typeInfo.getDeclaredPrecision() != -1L || typeInfo.getDeclaredScale() != -1) {
                return TypedValueExpression.get(valueInterval.castTo(typeInfo, this.session), typeInfo);
            }
            return ValueExpression.get(valueInterval);
        }
        catch (Exception exception) {
            throw DbException.get(22007, exception, "INTERVAL", string);
        }
    }

    private Expression parseDB2SpecialRegisters(String string) {
        if (this.readIfCompat("TIMESTAMP")) {
            if (this.readIf(89, "TIME", "ZONE")) {
                return this.readCurrentDateTimeValueFunction(3, this.readIf(105), null);
            }
            return this.readCurrentDateTimeValueFunction(4, this.readIf(105), null);
        }
        if (this.readIfCompat("TIME")) {
            return this.readCurrentDateTimeValueFunction(2, false, null);
        }
        if (this.readIfCompat("DATE")) {
            return this.readCurrentDateTimeValueFunction(0, false, null);
        }
        return new ExpressionColumn(this.database, null, null, string);
    }

    private Expression readCase() {
        Expression expression;
        if (this.readIf(86)) {
            SearchedCase searchedCase = new SearchedCase();
            do {
                Expression expression2 = this.readExpression();
                this.read("THEN");
                searchedCase.addParameter(expression2);
                searchedCase.addParameter(this.readExpression());
            } while (this.readIf(86));
            if (this.readIf(27)) {
                searchedCase.addParameter(this.readExpression());
            }
            searchedCase.doneWithParameters();
            expression = searchedCase;
        } else {
            SimpleCase.SimpleWhen simpleWhen;
            Expression expression3 = this.readExpression();
            this.read(86);
            SimpleCase.SimpleWhen simpleWhen2 = simpleWhen = this.readSimpleWhenClause(expression3);
            while (this.readIf(86)) {
                SimpleCase.SimpleWhen simpleWhen3 = this.readSimpleWhenClause(expression3);
                simpleWhen2.setWhen(simpleWhen3);
                simpleWhen2 = simpleWhen3;
            }
            expression = new SimpleCase(expression3, simpleWhen, this.readIf(27) ? this.readExpression() : null);
        }
        this.read(28);
        return expression;
    }

    private SimpleCase.SimpleWhen readSimpleWhenClause(Expression expression) {
        Expression expression2 = this.readWhenOperand(expression);
        if (this.readIf(109)) {
            ArrayList<Expression> arrayList = Utils.newSmallArrayList();
            arrayList.add(expression2);
            do {
                arrayList.add(this.readWhenOperand(expression));
            } while (this.readIf(109));
            this.read("THEN");
            return new SimpleCase.SimpleWhen(arrayList.toArray(new Expression[0]), this.readExpression());
        }
        this.read("THEN");
        return new SimpleCase.SimpleWhen(expression2, this.readExpression());
    }

    private Expression readWhenOperand(Expression expression) {
        int n = this.tokenIndex;
        boolean bl = this.readIf(57);
        Expression expression2 = this.readConditionRightHandSide(expression, bl, true);
        if (expression2 == null) {
            if (bl) {
                this.setTokenIndex(n);
            }
            expression2 = this.readExpression();
        }
        return expression2;
    }

    private String readString() {
        int n = this.token.start();
        Expression expression = this.readExpression();
        try {
            String string = expression.optimize(this.session).getValue(this.session).getString();
            if (string == null || string.length() <= 1000000000) {
                return string;
            }
        }
        catch (DbException dbException) {
            // empty catch block
        }
        throw DbException.getSyntaxError(this.sqlCommand, n, "character string");
    }

    private Expression readStringOrParameter() {
        int n = this.token.start();
        Expression expression = this.readExpression();
        try {
            expression = expression.optimize(this.session);
            if (expression instanceof Parameter) {
                return expression;
            }
            Value value = expression.getValue(this.session);
            int n2 = value.getValueType();
            if ((n2 == 58 || n2 == 2) && expression instanceof ValueExpression) {
                return expression;
            }
            String string = value.getString();
            if (string == null || string.length() <= 1000000000) {
                return string == null ? ValueExpression.NULL : ValueExpression.get(ValueVarchar.get(string, this.database));
            }
        }
        catch (DbException dbException) {
            // empty catch block
        }
        throw DbException.getSyntaxError(this.sqlCommand, n, "character string");
    }

    private String readIdentifierWithSchema(String string) {
        String string2 = this.readIdentifier();
        this.schemaName = string;
        if (this.readIf(110)) {
            string2 = this.readIdentifierWithSchema2(string2);
        }
        return string2;
    }

    private String readIdentifierWithSchema2(String string) {
        this.schemaName = string;
        if (this.database.getMode().allowEmptySchemaValuesAsDefaultSchema && this.readIf(110)) {
            if (this.equalsToken(this.schemaName, this.database.getShortName()) || this.database.getIgnoreCatalogs()) {
                this.schemaName = this.session.getCurrentSchemaName();
                string = this.readIdentifier();
            }
        } else {
            string = this.readIdentifier();
            if (this.currentTokenType == 110 && (this.equalsToken(this.schemaName, this.database.getShortName()) || this.database.getIgnoreCatalogs())) {
                this.read();
                this.schemaName = string;
                string = this.readIdentifier();
            }
        }
        return string;
    }

    private String readIdentifierWithSchema() {
        return this.readIdentifierWithSchema(this.session.getCurrentSchemaName());
    }

    private String readIdentifier() {
        if (!(this.isIdentifier() || this.session.isQuirksMode() && Parser.isKeyword(this.currentTokenType))) {
            throw DbException.getSyntaxError(this.sqlCommand, this.token.start(), "identifier");
        }
        String string = this.currentToken;
        this.read();
        return string;
    }

    private String readIdentifierOrKeyword() {
        if (this.currentTokenType < 2 || this.currentTokenType > 91) {
            this.addExpected("identifier or keyword");
            throw this.getSyntaxError();
        }
        String string = this.currentToken;
        this.read();
        return string;
    }

    /*
     * Unable to fully structure code
     */
    private Column parseColumnForTable(String var1_1, boolean var2_2) {
        block33: {
            block35: {
                block36: {
                    block34: {
                        var4_3 = this.database.getMode();
                        if (var4_3.identityDataType && this.readIfCompat("IDENTITY")) {
                            var3_4 = new Column(var1_1, TypeInfo.TYPE_BIGINT);
                            this.parseCompatibilityIdentityOptions(var3_4);
                            var3_4.setPrimaryKey(true);
                        } else if (var4_3.serialDataTypes && this.readIfCompat("BIGSERIAL")) {
                            var3_4 = new Column(var1_1, TypeInfo.TYPE_BIGINT);
                            var3_4.setIdentityOptions(new SequenceOptions(), false);
                        } else if (var4_3.serialDataTypes && this.readIfCompat("SERIAL")) {
                            var3_4 = new Column(var1_1, TypeInfo.TYPE_INTEGER);
                            var3_4.setIdentityOptions(new SequenceOptions(), false);
                        } else {
                            var3_4 = this.parseColumnWithType(var1_1);
                        }
                        if (this.readIf("INVISIBLE")) {
                            var3_4.setVisible(false);
                        } else if (this.readIf("VISIBLE")) {
                            var3_4.setVisible(true);
                        }
                        var5_5 = false;
                        var6_6 = this.parseNotNullConstraint();
                        if (var3_4.isIdentity()) break block33;
                        if (!this.readIfCompat(7)) break block34;
                        var3_4.setGeneratedExpression(this.readExpression());
                        ** GOTO lbl53
                    }
                    if (!this.readIf(25)) break block35;
                    if (!this.readIf(60, 58)) break block36;
                    var5_5 = true;
                    break block33;
                }
                var3_4.setDefaultExpression(this.session, this.readExpression());
                ** GOTO lbl53
            }
            if (!this.readIf("GENERATED")) ** GOTO lbl53
            var7_7 = this.readIf("ALWAYS");
            if (!var7_7) {
                this.read("BY");
                this.read(25);
            }
            this.read(7);
            if (this.readIf("IDENTITY")) {
                var8_9 = new SequenceOptions();
                if (this.readIf(105)) {
                    this.parseSequenceOptions(var8_9, null, false, false);
                    this.read(106);
                }
                var3_4.setIdentityOptions(var8_9, var7_7);
            } else {
                if (!var7_7) {
                    throw this.getSyntaxError();
                }
                var3_4.setGeneratedExpression(this.readExpression());
lbl53:
                // 4 sources

                if (!var3_4.isGenerated() && this.readIf(60, "UPDATE")) {
                    var3_4.setOnUpdateExpression(this.session, this.readExpression());
                }
                var6_6 = this.parseNotNullConstraint(var6_6);
                if (this.parseCompatibilityIdentity(var3_4, var4_3)) {
                    var6_6 = this.parseNotNullConstraint(var6_6);
                }
            }
        }
        switch (1.$SwitchMap$org$h2$command$Parser$NullConstraintType[var6_6.ordinal()]) {
            case 1: {
                if (var3_4.isIdentity()) {
                    throw DbException.get(90023, var3_4.getName());
                }
                var3_4.setNullable(true);
                break;
            }
            case 2: {
                var3_4.setNullable(false);
                break;
            }
            case 3: {
                if (var3_4.isIdentity()) break;
                var3_4.setNullable(var2_2);
                break;
            }
            default: {
                throw DbException.get(90088, "Internal Error - unhandled case: " + var6_6.name());
            }
        }
        if (!var5_5) {
            if (this.readIf(new Object[]{25, 60, 58})) {
                var5_5 = true;
            } else if (this.readIfCompat("NULL_TO_DEFAULT")) {
                var5_5 = true;
            }
        }
        if (var5_5) {
            var3_4.setDefaultOnNull(true);
        }
        if (!var3_4.isGenerated() && this.readIf("SEQUENCE")) {
            var3_4.setSequence(this.readSequence(), var3_4.isGeneratedAlways());
        }
        if (this.readIf("SELECTIVITY")) {
            var3_4.setSelectivity(this.readNonNegativeInt());
        }
        if (var4_3.mySqlTableOptions) {
            if (this.readIfCompat("CHARACTER")) {
                this.readIf(71);
                this.readMySQLCharset();
            }
            if (this.readIfCompat("COLLATE")) {
                this.readMySQLCharset();
            }
        }
        if ((var7_8 = this.readCommentIf()) != null) {
            var3_4.setComment(var7_8);
        }
        return var3_4;
    }

    private void parseCompatibilityIdentityOptions(Column column) {
        SequenceOptions sequenceOptions = new SequenceOptions();
        if (this.readIf(105)) {
            sequenceOptions.setStartValue(ValueExpression.get(ValueBigint.get(this.readLong())));
            if (this.readIf(109)) {
                sequenceOptions.setIncrement(ValueExpression.get(ValueBigint.get(this.readLong())));
            }
            this.read(106);
        }
        column.setIdentityOptions(sequenceOptions, false);
    }

    private String readCommentIf() {
        if (this.readIf("COMMENT")) {
            this.readIf(45);
            return this.readString();
        }
        return null;
    }

    private Column parseColumnWithType(String string) {
        TypeInfo typeInfo = this.readIfDataType();
        if (typeInfo == null) {
            String string2 = this.readIdentifierWithSchema();
            return Parser.getColumnWithDomain(string, this.getSchema().getDomain(string2));
        }
        return new Column(string, typeInfo);
    }

    private TypeInfo parseDataType() {
        TypeInfo typeInfo = this.readIfDataType();
        if (typeInfo == null) {
            this.addExpected("data type");
            throw this.getSyntaxError();
        }
        return typeInfo;
    }

    private TypeInfo readIfDataType() {
        TypeInfo typeInfo = this.readIfDataType1();
        if (typeInfo != null) {
            while (this.readIf(6)) {
                typeInfo = this.parseArrayType(typeInfo);
            }
        }
        return typeInfo;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private TypeInfo readIfDataType1() {
        int n;
        long l;
        Object object;
        switch (this.currentTokenType) {
            case 2: {
                if (!this.token.isQuoted()) break;
                return null;
            }
            case 44: {
                this.read();
                TypeInfo typeInfo = this.readIntervalQualifier();
                if (typeInfo != null) return typeInfo;
                throw this.intervalQualifierError();
            }
            case 58: {
                this.read();
                return TypeInfo.TYPE_NULL;
            }
            case 66: {
                this.read();
                return this.parseRowType();
            }
            case 6: {
                if (this.session.isQuirksMode()) {
                    this.read();
                    return this.parseArrayType(TypeInfo.TYPE_VARCHAR);
                }
                this.addExpected("data type");
                throw this.getSyntaxError();
            }
            default: {
                if (Parser.isKeyword(this.currentTokenType)) break;
                this.addExpected("data type");
                throw this.getSyntaxError();
            }
        }
        int n2 = this.tokenIndex;
        String string = this.currentToken;
        this.read();
        if (this.currentTokenType == 110) {
            this.setTokenIndex(n2);
            return null;
        }
        String string2 = this.upperName(string);
        switch (string2) {
            case "BINARY": {
                if (this.readIf("VARYING")) {
                    string2 = "BINARY VARYING";
                    break;
                }
                if (this.readIf("LARGE")) {
                    this.read("OBJECT");
                    string2 = "BINARY LARGE OBJECT";
                    break;
                }
                if (!this.variableBinary) break;
                string2 = "VARBINARY";
                break;
            }
            case "CHAR": {
                if (this.readIf("VARYING")) {
                    string2 = "CHAR VARYING";
                    break;
                }
                if (!this.readIf("LARGE")) break;
                this.read("OBJECT");
                string2 = "CHAR LARGE OBJECT";
                break;
            }
            case "CHARACTER": {
                if (this.readIf("VARYING")) {
                    string2 = "CHARACTER VARYING";
                    break;
                }
                if (!this.readIf("LARGE")) break;
                this.read("OBJECT");
                string2 = "CHARACTER LARGE OBJECT";
                break;
            }
            case "DATE": {
                return this.database.getMode().dateIsTimestamp0 ? TypeInfo.getTypeInfo(20, -1L, 0, null) : TypeInfo.TYPE_DATE;
            }
            case "DATETIME": 
            case "DATETIME2": {
                return this.parseDateTimeType(false);
            }
            case "DEC": 
            case "DECIMAL": {
                return this.parseNumericType(true);
            }
            case "DECFLOAT": {
                return this.parseDecfloatType();
            }
            case "DOUBLE": {
                if (!this.readIf("PRECISION")) break;
                string2 = "DOUBLE PRECISION";
                break;
            }
            case "ENUM": {
                return this.parseEnumType();
            }
            case "FLOAT": {
                return this.parseFloatType();
            }
            case "GEOMETRY": {
                return this.parseGeometryType();
            }
            case "LONG": {
                if (!this.readIf("RAW")) break;
                string2 = "LONG RAW";
                break;
            }
            case "NATIONAL": {
                if (this.readIf("CHARACTER")) {
                    if (this.readIf("VARYING")) {
                        string2 = "NATIONAL CHARACTER VARYING";
                        break;
                    }
                    if (this.readIf("LARGE")) {
                        this.read("OBJECT");
                        string2 = "NATIONAL CHARACTER LARGE OBJECT";
                        break;
                    }
                    string2 = "NATIONAL CHARACTER";
                    break;
                }
                this.read("CHAR");
                if (this.readIf("VARYING")) {
                    string2 = "NATIONAL CHAR VARYING";
                    break;
                }
                string2 = "NATIONAL CHAR";
                break;
            }
            case "NCHAR": {
                if (this.readIf("VARYING")) {
                    string2 = "NCHAR VARYING";
                    break;
                }
                if (!this.readIf("LARGE")) break;
                this.read("OBJECT");
                string2 = "NCHAR LARGE OBJECT";
                break;
            }
            case "NUMBER": {
                if (this.database.getMode().disallowedTypes.contains("NUMBER")) {
                    throw DbException.get(50004, "NUMBER");
                }
                if (!this.isToken(105)) {
                    return TypeInfo.getTypeInfo(16, 40L, -1, null);
                }
            }
            case "NUMERIC": {
                return this.parseNumericType(false);
            }
            case "SMALLDATETIME": {
                return this.parseDateTimeType(true);
            }
            case "TIME": {
                return this.parseTimeType();
            }
            case "TIMESTAMP": {
                return this.parseTimestampType();
            }
        }
        if (string.length() == string2.length() && (object = this.database.getSchema(this.session.getCurrentSchemaName()).findDomain(string)) != null) {
            this.setTokenIndex(n2);
            return null;
        }
        object = this.database.getMode();
        DataType dataType = DataType.getTypeByName(string2, (Mode)object);
        if (dataType == null || ((Mode)object).disallowedTypes.contains(string2)) {
            throw DbException.get(50004, string2);
        }
        if (dataType.specialPrecisionScale) {
            l = dataType.defaultPrecision;
            n = dataType.defaultScale;
        } else {
            l = -1L;
            n = -1;
        }
        int n3 = dataType.type;
        if (this.database.getIgnoreCase() && n3 == 2 && !this.equalsToken("VARCHAR_CASESENSITIVE", string2)) {
            n3 = 4;
            dataType = DataType.getDataType(4);
        }
        if ((dataType.supportsPrecision || dataType.supportsScale) && this.readIf(105)) {
            if (!this.readIf("MAX")) {
                if (dataType.supportsPrecision) {
                    l = this.readPrecision(n3);
                    if (l < dataType.minPrecision) {
                        throw Parser.getInvalidPrecisionException(dataType, l);
                    }
                    if (l > dataType.maxPrecision) {
                        if (!this.session.isQuirksMode() && !this.session.isTruncateLargeLength()) throw Parser.getInvalidPrecisionException(dataType, l);
                        switch (dataType.type) {
                            case 1: 
                            case 2: 
                            case 4: 
                            case 5: 
                            case 6: 
                            case 35: 
                            case 38: {
                                l = dataType.maxPrecision;
                                break;
                            }
                            default: {
                                throw Parser.getInvalidPrecisionException(dataType, l);
                            }
                        }
                    }
                    if (dataType.supportsScale && this.readIf(109) && ((n = this.readInt()) < dataType.minScale || n > dataType.maxScale)) {
                        throw DbException.get(90151, Integer.toString(n), Integer.toString(dataType.minScale), Integer.toString(dataType.maxScale));
                    }
                } else {
                    n = this.readInt();
                    if (n < dataType.minScale || n > dataType.maxScale) {
                        throw DbException.get(90151, Integer.toString(n), Integer.toString(dataType.minScale), Integer.toString(dataType.maxScale));
                    }
                }
            }
            this.read(106);
        }
        if (((Mode)object).allNumericTypesHavePrecision && (DataType.isNumericType(dataType.type) || dataType.type == 8)) {
            if (this.readIfCompat(105)) {
                this.readNonNegativeInt();
                this.read(106);
            }
            this.readIf("UNSIGNED");
        }
        if (!((Mode)object).forBitData || !DataType.isStringType(n3) || !this.readIfCompat(33, "BIT", "DATA")) return TypeInfo.getTypeInfo(n3, l, n, null);
        n3 = 6;
        dataType = DataType.getDataType(6);
        return TypeInfo.getTypeInfo(n3, l, n, null);
    }

    private static DbException getInvalidPrecisionException(DataType dataType, long l) {
        return DbException.get(90150, Long.toString(l), Long.toString(dataType.minPrecision), Long.toString(dataType.maxPrecision));
    }

    private static Column getColumnWithDomain(String string, Domain domain) {
        Column column = new Column(string, domain.getDataType());
        column.setComment(domain.getComment());
        column.setDomain(domain);
        return column;
    }

    private TypeInfo parseFloatType() {
        int n;
        int n2 = 15;
        if (this.readIf(105)) {
            n = this.readNonNegativeInt();
            this.read(106);
            if (n < 1 || n > 53) {
                throw DbException.get(90150, Integer.toString(n), "1", "53");
            }
            if (n <= 24) {
                n2 = 14;
            }
        } else {
            n = 0;
        }
        return TypeInfo.getTypeInfo(n2, n, -1, null);
    }

    private TypeInfo parseNumericType(boolean bl) {
        long l = -1L;
        int n = -1;
        if (this.readIf(105)) {
            l = this.readPrecision(13);
            if (l < 1L) {
                throw Parser.getInvalidNumericPrecisionException(l);
            }
            if (l > 100000L) {
                if (this.session.isQuirksMode() || this.session.isTruncateLargeLength()) {
                    l = 100000L;
                } else {
                    throw Parser.getInvalidNumericPrecisionException(l);
                }
            }
            if (this.readIf(109) && ((n = this.readInt()) < 0 || n > 100000)) {
                throw DbException.get(90151, Integer.toString(n), "0", "100000");
            }
            this.read(106);
        } else if (this.database.getMode().numericIsDecfloat) {
            return TypeInfo.TYPE_DECFLOAT;
        }
        return TypeInfo.getTypeInfo(13, l, n, bl ? ExtTypeInfoNumeric.DECIMAL : null);
    }

    private TypeInfo parseDecfloatType() {
        long l = -1L;
        if (this.readIf(105)) {
            l = this.readPrecision(16);
            if (l < 1L || l > 100000L) {
                throw Parser.getInvalidNumericPrecisionException(l);
            }
            this.read(106);
        }
        return TypeInfo.getTypeInfo(16, l, -1, null);
    }

    private static DbException getInvalidNumericPrecisionException(long l) {
        return DbException.get(90150, Long.toString(l), "1", "100000");
    }

    private TypeInfo parseTimeType() {
        int n = -1;
        if (this.readIf(105)) {
            n = this.readNonNegativeInt();
            if (n > 9) {
                throw DbException.get(90151, Integer.toString(n), "0", "9");
            }
            this.read(106);
        }
        int n2 = 18;
        if (this.readIf(89, "TIME", "ZONE")) {
            n2 = 19;
        } else {
            this.readIf("WITHOUT", "TIME", "ZONE");
        }
        return TypeInfo.getTypeInfo(n2, -1L, n, null);
    }

    private TypeInfo parseTimestampType() {
        int n = -1;
        if (this.readIf(105)) {
            n = this.readNonNegativeInt();
            if (this.readIf(109)) {
                n = this.readNonNegativeInt();
            }
            if (n > 9) {
                throw DbException.get(90151, Integer.toString(n), "0", "9");
            }
            this.read(106);
        }
        int n2 = 20;
        if (this.readIf(89, "TIME", "ZONE")) {
            n2 = 21;
        } else {
            this.readIf("WITHOUT", "TIME", "ZONE");
        }
        return TypeInfo.getTypeInfo(n2, -1L, n, null);
    }

    private TypeInfo parseDateTimeType(boolean bl) {
        int n;
        if (bl) {
            n = 0;
        } else {
            n = -1;
            if (this.readIf(105)) {
                n = this.readNonNegativeInt();
                if (n > 9) {
                    throw DbException.get(90151, Integer.toString(n), "0", "9");
                }
                this.read(106);
            }
        }
        return TypeInfo.getTypeInfo(20, -1L, n, null);
    }

    private TypeInfo readIntervalQualifier() {
        IntervalQualifier intervalQualifier;
        int n = -1;
        int n2 = -1;
        block0 : switch (this.currentTokenType) {
            case 90: {
                this.read();
                if (this.readIf(105)) {
                    n = this.readNonNegativeInt();
                    this.read(106);
                }
                if (this.readIf(76, 55)) {
                    intervalQualifier = IntervalQualifier.YEAR_TO_MONTH;
                    break;
                }
                intervalQualifier = IntervalQualifier.YEAR;
                break;
            }
            case 55: {
                this.read();
                if (this.readIf(105)) {
                    n = this.readNonNegativeInt();
                    this.read(106);
                }
                intervalQualifier = IntervalQualifier.MONTH;
                break;
            }
            case 24: {
                this.read();
                if (this.readIf(105)) {
                    n = this.readNonNegativeInt();
                    this.read(106);
                }
                if (this.readIf(76)) {
                    switch (this.currentTokenType) {
                        case 39: {
                            this.read();
                            intervalQualifier = IntervalQualifier.DAY_TO_HOUR;
                            break block0;
                        }
                        case 54: {
                            this.read();
                            intervalQualifier = IntervalQualifier.DAY_TO_MINUTE;
                            break block0;
                        }
                        case 68: {
                            this.read();
                            if (this.readIf(105)) {
                                n2 = this.readNonNegativeInt();
                                this.read(106);
                            }
                            intervalQualifier = IntervalQualifier.DAY_TO_SECOND;
                            break block0;
                        }
                    }
                    throw this.intervalDayError();
                }
                intervalQualifier = IntervalQualifier.DAY;
                break;
            }
            case 39: {
                this.read();
                if (this.readIf(105)) {
                    n = this.readNonNegativeInt();
                    this.read(106);
                }
                if (this.readIf(76)) {
                    switch (this.currentTokenType) {
                        case 54: {
                            this.read();
                            intervalQualifier = IntervalQualifier.HOUR_TO_MINUTE;
                            break block0;
                        }
                        case 68: {
                            this.read();
                            if (this.readIf(105)) {
                                n2 = this.readNonNegativeInt();
                                this.read(106);
                            }
                            intervalQualifier = IntervalQualifier.HOUR_TO_SECOND;
                            break block0;
                        }
                    }
                    throw this.intervalHourError();
                }
                intervalQualifier = IntervalQualifier.HOUR;
                break;
            }
            case 54: {
                this.read();
                if (this.readIf(105)) {
                    n = this.readNonNegativeInt();
                    this.read(106);
                }
                if (this.readIf(76, 68)) {
                    if (this.readIf(105)) {
                        n2 = this.readNonNegativeInt();
                        this.read(106);
                    }
                    intervalQualifier = IntervalQualifier.MINUTE_TO_SECOND;
                    break;
                }
                intervalQualifier = IntervalQualifier.MINUTE;
                break;
            }
            case 68: {
                this.read();
                if (this.readIf(105)) {
                    n = this.readNonNegativeInt();
                    if (this.readIf(109)) {
                        n2 = this.readNonNegativeInt();
                    }
                    this.read(106);
                }
                intervalQualifier = IntervalQualifier.SECOND;
                break;
            }
            default: {
                return null;
            }
        }
        if (n >= 0 && (n == 0 || n > 18)) {
            throw DbException.get(90150, Integer.toString(n), "1", "18");
        }
        if (n2 >= 0 && n2 > 9) {
            throw DbException.get(90151, Integer.toString(n2), "0", "9");
        }
        return TypeInfo.getTypeInfo(intervalQualifier.ordinal() + 22, n, n2, null);
    }

    private DbException intervalQualifierError() {
        if (this.expectedList != null) {
            this.addMultipleExpected(90, 55, 24, 39, 54, 68);
        }
        return this.getSyntaxError();
    }

    private DbException intervalDayError() {
        if (this.expectedList != null) {
            this.addMultipleExpected(39, 54, 68);
        }
        return this.getSyntaxError();
    }

    private DbException intervalHourError() {
        if (this.expectedList != null) {
            this.addMultipleExpected(54, 68);
        }
        return this.getSyntaxError();
    }

    private TypeInfo parseArrayType(TypeInfo typeInfo) {
        int n = -1;
        if (this.readIf(117)) {
            n = this.readNonNegativeInt();
            if (n > 65536) {
                throw DbException.get(90150, Integer.toString(n), "0", "65536");
            }
            this.read(118);
        }
        return TypeInfo.getTypeInfo(40, n, -1, typeInfo);
    }

    private TypeInfo parseEnumType() {
        this.read(105);
        ArrayList<String> arrayList = new ArrayList<String>();
        do {
            arrayList.add(this.readString());
        } while (this.readIfMore());
        return TypeInfo.getTypeInfo(36, -1L, -1, new ExtTypeInfoEnum(arrayList.toArray(new String[0])));
    }

    private TypeInfo parseGeometryType() {
        ExtTypeInfoGeometry extTypeInfoGeometry;
        if (this.readIf(105)) {
            int n = 0;
            if (this.currentTokenType != 2 || this.token.isQuoted()) {
                throw this.getSyntaxError();
            }
            if (!this.readIf("GEOMETRY")) {
                try {
                    n = EWKTUtils.parseGeometryType(this.currentToken);
                    this.read();
                    if (n / 1000 == 0 && this.currentTokenType == 2 && !this.token.isQuoted()) {
                        n += EWKTUtils.parseDimensionSystem(this.currentToken) * 1000;
                        this.read();
                    }
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw this.getSyntaxError();
                }
            }
            Integer n2 = null;
            if (this.readIf(109)) {
                n2 = this.readInt();
            }
            this.read(106);
            extTypeInfoGeometry = new ExtTypeInfoGeometry(n, n2);
        } else {
            extTypeInfoGeometry = null;
        }
        return TypeInfo.getTypeInfo(37, -1L, -1, extTypeInfoGeometry);
    }

    private TypeInfo parseRowType() {
        this.read(105);
        LinkedHashMap<String, TypeInfo> linkedHashMap = new LinkedHashMap<String, TypeInfo>();
        do {
            String string;
            if (linkedHashMap.putIfAbsent(string = this.readIdentifier(), this.parseDataType()) == null) continue;
            throw DbException.get(42121, string);
        } while (this.readIfMore());
        return TypeInfo.getTypeInfo(41, -1L, -1, new ExtTypeInfoRow(linkedHashMap));
    }

    private long readPrecision(int n) {
        long l = this.readPositiveLong();
        if (this.currentTokenType != 2 || this.token.isQuoted()) {
            return l;
        }
        if ((n == 7 || n == 3) && this.currentToken.length() == 1) {
            long l2;
            switch (this.currentToken.charAt(0) & 0xFFDF) {
                case 75: {
                    l2 = 1024L;
                    break;
                }
                case 77: {
                    l2 = 0x100000L;
                    break;
                }
                case 71: {
                    l2 = 0x40000000L;
                    break;
                }
                case 84: {
                    l2 = 0x10000000000L;
                    break;
                }
                case 80: {
                    l2 = 0x4000000000000L;
                    break;
                }
                default: {
                    throw this.getSyntaxError();
                }
            }
            if (l > Long.MAX_VALUE / l2) {
                throw DbException.getInvalidValueException("precision", l + this.currentToken);
            }
            l *= l2;
            this.read();
            if (this.currentTokenType != 2 || this.token.isQuoted()) {
                return l;
            }
        }
        switch (n) {
            case 1: 
            case 2: 
            case 3: 
            case 4: {
                if (this.readIf("CHARACTERS") || this.readIf("OCTETS") || !this.database.getMode().charAndByteLengthUnits || this.readIfCompat("CHAR")) break;
                this.readIfCompat("BYTE");
            }
        }
        return l;
    }

    private Prepared parseCreate() {
        IndexColumn[] indexColumnArray;
        boolean bl = false;
        if (this.readIf(61, "REPLACE")) {
            bl = true;
        }
        boolean bl2 = this.readIf("FORCE");
        if (this.readIf("VIEW")) {
            return this.parseCreateView(bl2, bl);
        }
        if (this.readIf("MATERIALIZED")) {
            this.read("VIEW");
            return this.parseCreateMaterializedView(bl2, bl);
        }
        if (this.readIf("ALIAS")) {
            return this.parseCreateFunctionAlias(bl2);
        }
        if (this.readIf("SEQUENCE")) {
            return this.parseCreateSequence();
        }
        if (this.readIf(82)) {
            return this.parseCreateUser();
        }
        if (this.readIf("TRIGGER")) {
            return this.parseCreateTrigger(bl2);
        }
        if (this.readIf("ROLE")) {
            return this.parseCreateRole();
        }
        if (this.readIf("SCHEMA")) {
            return this.parseCreateSchema();
        }
        if (this.readIf("CONSTANT")) {
            return this.parseCreateConstant();
        }
        if (this.readIf("DOMAIN") || this.readIf("TYPE") || this.readIfCompat("DATATYPE")) {
            return this.parseCreateDomain();
        }
        if (this.readIf("AGGREGATE")) {
            return this.parseCreateAggregate(bl2);
        }
        if (this.readIf("LINKED")) {
            return this.parseCreateLinkedTable(false, false, bl2);
        }
        boolean bl3 = false;
        boolean bl4 = false;
        if (this.readIf("MEMORY")) {
            bl3 = true;
        } else if (this.readIf("CACHED")) {
            bl4 = true;
        }
        if (this.readIf("LOCAL", "TEMPORARY")) {
            if (this.readIf("LINKED")) {
                return this.parseCreateLinkedTable(true, false, bl2);
            }
            this.read(75);
            return this.parseCreateTable(true, false, bl4);
        }
        if (this.readIf("GLOBAL", "TEMPORARY")) {
            if (this.readIf("LINKED")) {
                return this.parseCreateLinkedTable(true, true, bl2);
            }
            this.read(75);
            return this.parseCreateTable(true, true, bl4);
        }
        if (this.readIfCompat("TEMP") || this.readIf("TEMPORARY")) {
            if (this.readIf("LINKED")) {
                return this.parseCreateLinkedTable(true, true, bl2);
            }
            this.read(75);
            return this.parseCreateTable(true, true, bl4);
        }
        if (this.readIf(75)) {
            if (!bl4 && !bl3) {
                bl4 = this.database.getDefaultTableType() == 0;
            }
            return this.parseCreateTable(false, false, bl4);
        }
        if (this.readIf("SYNONYM")) {
            return this.parseCreateSynonym(bl);
        }
        boolean bl5 = false;
        boolean bl6 = false;
        NullsDistinct nullsDistinct = null;
        boolean bl7 = false;
        String string = null;
        Schema schema = null;
        boolean bl8 = false;
        if (this.session.isQuirksMode() && this.readIf(63, 47)) {
            if (this.readIf("HASH")) {
                bl5 = true;
            }
            bl6 = true;
            if (!this.isToken(60)) {
                bl8 = this.readIfNotExists();
                string = this.readIdentifierWithSchema(null);
                schema = this.getSchema();
            }
        } else {
            if (this.readIf(80)) {
                nullsDistinct = this.readNullsDistinct(this.database.getMode().nullsDistinct);
            }
            if (this.readIfCompat("HASH")) {
                bl5 = true;
            } else if (nullsDistinct == null && this.readIf("SPATIAL")) {
                bl7 = true;
            }
            this.read("INDEX");
            if (!this.isToken(60)) {
                bl8 = this.readIfNotExists();
                string = this.readIdentifierWithSchema(null);
                schema = this.getSchema();
            }
        }
        this.read(60);
        String string2 = this.readIdentifierWithSchema();
        this.checkSchema(schema);
        String string3 = this.readCommentIf();
        if (!this.readIf(105)) {
            if (bl5 || bl7) {
                throw this.getSyntaxError();
            }
            this.readCompat(83);
            if (!this.readIf("BTREE")) {
                if (this.readIf("HASH")) {
                    bl5 = true;
                } else {
                    this.read("RTREE");
                    bl7 = true;
                }
            }
            this.read(105);
        }
        CreateIndex createIndex = new CreateIndex(this.session, this.getSchema());
        createIndex.setIfNotExists(bl8);
        createIndex.setPrimaryKey(bl6);
        createIndex.setTableName(string2);
        createIndex.setHash(bl5);
        createIndex.setSpatial(bl7);
        createIndex.setIndexName(string);
        createIndex.setComment(string3);
        int n = 0;
        if (bl7) {
            indexColumnArray = new IndexColumn[]{new IndexColumn(this.readIdentifier())};
            if (nullsDistinct != null) {
                n = 1;
            }
            this.read(106);
        } else {
            indexColumnArray = this.parseIndexColumnList();
            if (nullsDistinct != null) {
                n = indexColumnArray.length;
                if (this.readIf("INCLUDE")) {
                    this.read(105);
                    IndexColumn[] indexColumnArray2 = this.parseIndexColumnList();
                    int n2 = indexColumnArray2.length;
                    indexColumnArray = Arrays.copyOf(indexColumnArray, n + n2);
                    System.arraycopy(indexColumnArray2, 0, indexColumnArray, n, n2);
                }
            } else if (bl6) {
                n = indexColumnArray.length;
            }
        }
        createIndex.setIndexColumns(indexColumnArray);
        createIndex.setUnique(nullsDistinct, n);
        return createIndex;
    }

    private NullsDistinct readNullsDistinct(NullsDistinct nullsDistinct) {
        if (this.readIf("NULLS")) {
            if (this.readIf(26)) {
                return NullsDistinct.DISTINCT;
            }
            if (this.readIf(57, 26)) {
                return NullsDistinct.NOT_DISTINCT;
            }
            if (this.readIf(3, 26)) {
                return NullsDistinct.ALL_DISTINCT;
            }
            throw this.getSyntaxError();
        }
        return nullsDistinct;
    }

    private boolean addRoleOrRight(GrantRevoke grantRevoke) {
        if (this.readIf(69)) {
            grantRevoke.addRight(1);
            return true;
        }
        if (this.readIf("DELETE")) {
            grantRevoke.addRight(2);
            return true;
        }
        if (this.readIf("INSERT")) {
            grantRevoke.addRight(4);
            return true;
        }
        if (this.readIf("UPDATE")) {
            grantRevoke.addRight(8);
            return true;
        }
        if (this.readIfCompat("CONNECT")) {
            return true;
        }
        if (this.readIfCompat("RESOURCE")) {
            return true;
        }
        grantRevoke.addRoleName(this.readIdentifier());
        return false;
    }

    private GrantRevoke parseGrantRevoke(int n) {
        boolean bl;
        GrantRevoke grantRevoke = new GrantRevoke(this.session);
        grantRevoke.setOperationType(n);
        if (this.readIf(3)) {
            this.readIf("PRIVILEGES");
            grantRevoke.addRight(15);
            bl = true;
        } else if (this.readIf("ALTER")) {
            this.read(5);
            this.read("SCHEMA");
            grantRevoke.addRight(16);
            grantRevoke.addTable(null);
            bl = false;
        } else {
            bl = this.addRoleOrRight(grantRevoke);
            while (this.readIf(109)) {
                if (this.addRoleOrRight(grantRevoke) == bl) continue;
                throw DbException.get(90072);
            }
        }
        if (bl && this.readIf(60)) {
            if (this.readIf("SCHEMA")) {
                grantRevoke.setSchema(this.database.getSchema(this.readIdentifier()));
            } else {
                this.readIf(75);
                do {
                    Table table = this.readTableOrView();
                    grantRevoke.addTable(table);
                } while (this.readIf(109));
            }
        }
        this.read(n == 49 ? 76 : 35);
        grantRevoke.setGranteeName(this.readIdentifier());
        return grantRevoke;
    }

    private TableValueConstructor parseValues() {
        ArrayList<ArrayList<Expression>> arrayList = Utils.newSmallArrayList();
        ArrayList<Expression> arrayList2 = this.parseValuesRow(Utils.newSmallArrayList());
        arrayList.add(arrayList2);
        int n = arrayList2.size();
        while (this.readIf(109)) {
            arrayList2 = this.parseValuesRow(new ArrayList<Expression>(n));
            if (arrayList2.size() != n) {
                throw DbException.get(21002);
            }
            arrayList.add(arrayList2);
        }
        return new TableValueConstructor(this.session, arrayList);
    }

    private ArrayList<Expression> parseValuesRow(ArrayList<Expression> arrayList) {
        if (!this.readIf(66, 105) && !this.readIf(105)) {
            arrayList.add(this.readExpression());
            return arrayList;
        }
        do {
            arrayList.add(this.readExpression());
        } while (this.readIfMore());
        return arrayList;
    }

    private Call parseCall() {
        Call call = new Call(this.session);
        this.currentPrepared = call;
        if (this.readIf(75, 105)) {
            call.setTableFunction(this.readTableFunction(1));
            return call;
        }
        int n = this.tokenIndex;
        boolean bl = this.isIdentifier();
        try {
            call.setExpression(this.readExpression());
        }
        catch (DbException dbException) {
            if (bl && dbException.getErrorCode() == 90022) {
                this.setTokenIndex(n);
                String string = null;
                String string2 = this.readIdentifier();
                if (this.readIf(110)) {
                    string = string2;
                    string2 = this.readIdentifier();
                    if (this.readIf(110)) {
                        this.checkDatabaseName(string);
                        string = string2;
                        string2 = this.readIdentifier();
                    }
                }
                this.read(105);
                Schema schema = string != null ? this.database.getSchema(string) : null;
                call.setTableFunction(this.readTableFunction(string2, schema));
                return call;
            }
            throw dbException;
        }
        return call;
    }

    private CreateRole parseCreateRole() {
        CreateRole createRole = new CreateRole(this.session);
        createRole.setIfNotExists(this.readIfNotExists());
        createRole.setRoleName(this.readIdentifier());
        return createRole;
    }

    private CreateSchema parseCreateSchema() {
        String string;
        CreateSchema createSchema = new CreateSchema(this.session);
        createSchema.setIfNotExists(this.readIfNotExists());
        if (this.readIf(9)) {
            string = this.readIdentifier();
            createSchema.setSchemaName(string);
            createSchema.setAuthorization(string);
        } else {
            createSchema.setSchemaName(this.readIdentifier());
            string = this.readIf(9) ? this.readIdentifier() : this.session.getUser().getName();
        }
        createSchema.setAuthorization(string);
        if (this.readIf(89)) {
            createSchema.setTableEngineParams(this.readTableEngineParams());
        }
        return createSchema;
    }

    private ArrayList<String> readTableEngineParams() {
        ArrayList<String> arrayList = Utils.newSmallArrayList();
        do {
            arrayList.add(this.readIdentifier());
        } while (this.readIf(109));
        return arrayList;
    }

    private CreateSequence parseCreateSequence() {
        boolean bl = this.readIfNotExists();
        String string = this.readIdentifierWithSchema();
        CreateSequence createSequence = new CreateSequence(this.session, this.getSchema());
        createSequence.setIfNotExists(bl);
        createSequence.setSequenceName(string);
        SequenceOptions sequenceOptions = new SequenceOptions();
        this.parseSequenceOptions(sequenceOptions, createSequence, true, false);
        createSequence.setOptions(sequenceOptions);
        return createSequence;
    }

    private boolean readIfNotExists() {
        return this.readIf(40, 57, 30);
    }

    private CreateConstant parseCreateConstant() {
        boolean bl = this.readIfNotExists();
        String string = this.readIdentifierWithSchema();
        Schema schema = this.getSchema();
        if (this.isKeyword(string)) {
            throw DbException.get(90114, string);
        }
        this.read(84);
        Expression expression = this.readExpression();
        CreateConstant createConstant = new CreateConstant(this.session, schema);
        createConstant.setConstantName(string);
        createConstant.setExpression(expression);
        createConstant.setIfNotExists(bl);
        return createConstant;
    }

    private CreateAggregate parseCreateAggregate(boolean bl) {
        String string;
        boolean bl2 = this.readIfNotExists();
        String string2 = this.readIdentifierWithSchema();
        if (this.isKeyword(string2) || BuiltinFunctions.isBuiltinFunction(this.database, string = this.upperName(string2)) || Aggregate.getAggregateType(string) != null) {
            throw DbException.get(90076, string2);
        }
        CreateAggregate createAggregate = new CreateAggregate(this.session, this.getSchema());
        createAggregate.setForce(bl);
        createAggregate.setName(string2);
        createAggregate.setIfNotExists(bl2);
        this.read(33);
        createAggregate.setJavaClassMethod(this.readStringOrIdentifier());
        return createAggregate;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private CreateDomain parseCreateDomain() {
        String string;
        boolean bl = this.readIfNotExists();
        String string2 = this.readIdentifierWithSchema();
        Schema schema = this.getSchema();
        CreateDomain createDomain = new CreateDomain(this.session, schema);
        createDomain.setIfNotExists(bl);
        createDomain.setTypeName(string2);
        this.readIf(7);
        TypeInfo typeInfo = this.readIfDataType();
        if (typeInfo != null) {
            createDomain.setDataType(typeInfo);
        } else {
            string = this.readIdentifierWithSchema();
            createDomain.setParentDomain(this.getSchema().getDomain(string));
        }
        if (this.readIf(25)) {
            createDomain.setDefaultExpression(this.readExpression());
        }
        if (this.readIf(60, "UPDATE")) {
            createDomain.setOnUpdateExpression(this.readExpression());
        }
        if (this.readIfCompat("SELECTIVITY")) {
            this.readNonNegativeInt();
        }
        if ((string = this.readCommentIf()) != null) {
            createDomain.setComment(string);
        }
        while (true) {
            String string3;
            if (this.readIf(14)) {
                string3 = this.readIdentifier();
                this.read(13);
            } else {
                if (!this.readIf(13)) break;
                string3 = null;
            }
            AlterDomainAddConstraint alterDomainAddConstraint = new AlterDomainAddConstraint(this.session, schema, bl);
            alterDomainAddConstraint.setConstraintName(string3);
            alterDomainAddConstraint.setDomainName(string2);
            this.parseDomainConstraint = true;
            try {
                alterDomainAddConstraint.setCheckExpression(this.readExpression());
            }
            finally {
                this.parseDomainConstraint = false;
            }
            createDomain.addConstraintCommand(alterDomainAddConstraint);
        }
        return createDomain;
    }

    private CreateTrigger parseCreateTrigger(boolean bl) {
        boolean bl2;
        boolean bl3;
        boolean bl4;
        boolean bl5 = this.readIfNotExists();
        String string = this.readIdentifierWithSchema(null);
        Schema schema = this.getSchema();
        if (this.readIf("INSTEAD", "OF")) {
            bl4 = true;
            bl3 = true;
        } else if (this.readIf("BEFORE")) {
            bl3 = false;
            bl4 = true;
        } else {
            this.read("AFTER");
            bl3 = false;
            bl4 = false;
        }
        int n = 0;
        boolean bl6 = false;
        boolean bl7 = bl2 = this.database.getMode().getEnum() == Mode.ModeEnum.PostgreSQL;
        do {
            if (this.readIf("INSERT")) {
                n |= 1;
                continue;
            }
            if (this.readIf("UPDATE")) {
                n |= 2;
                continue;
            }
            if (this.readIf("DELETE")) {
                n |= 4;
                continue;
            }
            if (this.readIf(69)) {
                n |= 8;
                continue;
            }
            if (this.readIf("ROLLBACK")) {
                bl6 = true;
                continue;
            }
            throw this.getSyntaxError();
        } while (this.readIf(109) || bl2 && this.readIf(61));
        this.read(60);
        String string2 = this.readIdentifierWithSchema();
        this.checkSchema(schema);
        CreateTrigger createTrigger = new CreateTrigger(this.session, this.getSchema());
        createTrigger.setForce(bl);
        createTrigger.setTriggerName(string);
        createTrigger.setIfNotExists(bl5);
        createTrigger.setInsteadOf(bl3);
        createTrigger.setBefore(bl4);
        createTrigger.setOnRollback(bl6);
        createTrigger.setTypeMask(n);
        createTrigger.setTableName(string2);
        if (this.readIf(33, "EACH")) {
            if (this.readIf(66)) {
                createTrigger.setRowBased(true);
            } else {
                this.read("STATEMENT");
            }
        }
        if (this.readIf("QUEUE")) {
            createTrigger.setQueueSize(this.readNonNegativeInt());
        }
        createTrigger.setNoWait(this.readIf("NOWAIT"));
        if (this.readIf(7)) {
            createTrigger.setTriggerSource(this.readString());
        } else {
            this.read("CALL");
            createTrigger.setTriggerClassName(this.readStringOrIdentifier());
        }
        return createTrigger;
    }

    private CreateUser parseCreateUser() {
        CreateUser createUser = new CreateUser(this.session);
        createUser.setIfNotExists(this.readIfNotExists());
        createUser.setUserName(this.readIdentifier());
        createUser.setComment(this.readCommentIf());
        if (this.readIf("PASSWORD")) {
            createUser.setPassword(this.readExpression());
        } else if (this.readIf("SALT")) {
            createUser.setSalt(this.readExpression());
            this.read("HASH");
            createUser.setHash(this.readExpression());
        } else if (this.readIf("IDENTIFIED")) {
            this.read("BY");
            createUser.setPassword(ValueExpression.get(ValueVarchar.get(this.readIdentifier())));
        } else {
            throw this.getSyntaxError();
        }
        if (this.readIf("ADMIN")) {
            createUser.setAdmin(true);
        }
        return createUser;
    }

    private CreateFunctionAlias parseCreateFunctionAlias(boolean bl) {
        String string;
        boolean bl2 = this.readIfNotExists();
        if (this.currentTokenType == 2) {
            string = this.readIdentifierWithSchema();
        } else if (Parser.isKeyword(this.currentTokenType)) {
            string = this.currentToken;
            this.read();
            this.schemaName = this.session.getCurrentSchemaName();
        } else {
            this.addExpected("identifier");
            throw this.getSyntaxError();
        }
        String string2 = this.upperName(string);
        if (this.isReservedFunctionName(string2)) {
            throw DbException.get(90076, string);
        }
        CreateFunctionAlias createFunctionAlias = new CreateFunctionAlias(this.session, this.getSchema());
        createFunctionAlias.setForce(bl);
        createFunctionAlias.setAliasName(string);
        createFunctionAlias.setIfNotExists(bl2);
        createFunctionAlias.setDeterministic(this.readIf("DETERMINISTIC"));
        this.readIfCompat("NOBUFFER");
        if (this.readIf(7)) {
            createFunctionAlias.setSource(this.readString());
        } else {
            this.read(33);
            createFunctionAlias.setJavaClassMethod(this.readStringOrIdentifier());
        }
        return createFunctionAlias;
    }

    private String readStringOrIdentifier() {
        return this.currentTokenType != 2 ? this.readString() : this.readIdentifier();
    }

    private boolean isReservedFunctionName(String string) {
        int n = ParserUtil.getTokenType(string, false, false);
        if (n != 2) {
            if (this.database.isAllowBuiltinAliasOverride()) {
                switch (n) {
                    case 17: 
                    case 21: 
                    case 22: 
                    case 24: 
                    case 39: 
                    case 51: 
                    case 52: 
                    case 54: 
                    case 55: 
                    case 68: 
                    case 90: {
                        return false;
                    }
                }
            }
            return true;
        }
        return Aggregate.getAggregateType(string) != null || BuiltinFunctions.isBuiltinFunction(this.database, string) && !this.database.isAllowBuiltinAliasOverride();
    }

    private Prepared parseWith() {
        ArrayList<TableView> arrayList = new ArrayList<TableView>();
        try {
            return this.parseWith1(arrayList);
        }
        catch (Throwable throwable) {
            CommandContainer.clearCTE(this.session, arrayList);
            throw throwable;
        }
    }

    private Prepared parseWith1(List<TableView> list) {
        Prepared prepared;
        this.readIf("RECURSIVE");
        boolean bl = !this.session.isParsingCreateView();
        do {
            list.add(this.parseSingleCommonTableExpression(bl));
        } while (this.readIf(109));
        Collections.reverse(list);
        int n = this.tokenIndex;
        if (this.isQueryQuick()) {
            prepared = this.parseWithQuery();
        } else if (this.readIfCompat("INSERT")) {
            prepared = this.parseInsert(n);
            prepared.setPrepareAlways(true);
        } else if (this.readIfCompat("UPDATE")) {
            prepared = this.parseUpdate(n);
            prepared.setPrepareAlways(true);
        } else if (this.readIfCompat("MERGE")) {
            prepared = this.parseMerge(n);
            prepared.setPrepareAlways(true);
        } else if (this.readIfCompat("DELETE")) {
            prepared = this.parseDelete(n);
            prepared.setPrepareAlways(true);
        } else if (this.readIfCompat("CREATE")) {
            if (!this.isToken(75)) {
                throw DbException.get(42000, WITH_STATEMENT_SUPPORTS_LIMITED_SUB_STATEMENTS);
            }
            prepared = this.parseCreate();
            prepared.setPrepareAlways(true);
        } else {
            throw DbException.get(42000, WITH_STATEMENT_SUPPORTS_LIMITED_SUB_STATEMENTS);
        }
        if (bl) {
            if (this.cteCleanups == null) {
                this.cteCleanups = new ArrayList<TableView>(list.size());
            }
            this.cteCleanups.addAll(list);
        }
        return prepared;
    }

    private Prepared parseWithQuery() {
        Query query = this.parseQueryExpressionBodyAndEndOfQuery();
        query.setPrepareAlways(true);
        query.setNeverLazy(true);
        return query;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private TableView parseSingleCommonTableExpression(boolean bl) {
        ArrayList<Parameter> arrayList;
        List<Column> list;
        String[] stringArray;
        Object object;
        String string = this.readIdentifierWithSchema();
        Schema schema = this.getSchema();
        ArrayList<Column> arrayList2 = Utils.newSmallArrayList();
        String[] stringArray2 = null;
        if (this.readIf(105)) {
            stringArray2 = this.parseColumnList();
            object = stringArray2;
            int n = ((String[])object).length;
            for (int i = 0; i < n; ++i) {
                stringArray = object[i];
                arrayList2.add(new Column((String)stringArray, TypeInfo.TYPE_VARCHAR));
            }
        }
        if ((object = !bl ? this.getSchema().findTableOrView(this.session, string) : this.session.findLocalTempTable(string)) != null) {
            if (!(object instanceof TableView)) {
                throw DbException.get(42101, string);
            }
            TableView tableView = (TableView)object;
            if (!tableView.isTableExpression()) {
                throw DbException.get(42101, string);
            }
            if (!bl) {
                ((Table)object).lock(this.session, 2);
                this.database.removeSchemaObject(this.session, (SchemaObject)object);
            } else {
                this.session.removeLocalTempTable((Table)object);
            }
        }
        Table table = TableView.createShadowTableForRecursiveTableExpression(bl, this.session, string, schema, arrayList2, this.database);
        stringArray = new String[1];
        BitSet bitSet = this.openParametersScope();
        try {
            this.read(7);
            this.read(105);
            Query query = this.parseQuery();
            if (!bl) {
                query.session = this.session;
            }
            this.read(106);
            list = QueryExpressionTable.createQueryColumnTemplateList(stringArray2, query, stringArray);
        }
        finally {
            arrayList = this.closeParametersScope(bitSet);
            TableView.destroyShadowTableForRecursiveExpression(bl, this.session, table);
        }
        return this.createCTEView(string, stringArray[0], arrayList, list, bl);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private TableView createCTEView(String string, String string2, ArrayList<Parameter> arrayList, List<Column> list, boolean bl) {
        TableView tableView;
        Schema schema = this.getSchemaWithDefault();
        int n = this.database.allocateObjectId();
        Column[] columnArray = list.toArray(new Column[0]);
        this.session.lock();
        try {
            tableView = new TableView(schema, n, string, string2, arrayList, columnArray, this.session, true, false, true, bl);
            if (!tableView.isRecursiveQueryDetected()) {
                if (!bl) {
                    this.database.addSchemaObject(this.session, tableView);
                    tableView.lock(this.session, 2);
                    this.database.removeSchemaObject(this.session, tableView);
                } else {
                    this.session.addLocalTempTable(tableView);
                    this.session.removeLocalTempTable(tableView);
                }
                n = this.database.allocateObjectId();
                tableView = new TableView(schema, n, string, string2, arrayList, columnArray, this.session, false, false, true, bl);
            }
            this.database.unlockMeta(this.session);
        }
        finally {
            this.session.unlock();
        }
        tableView.setTableExpression(true);
        tableView.setTemporary(bl);
        tableView.setHidden(true);
        tableView.setOnCommitDrop(false);
        if (!bl) {
            this.database.addSchemaObject(this.session, tableView);
            tableView.unlock(this.session);
            this.database.unlockMeta(this.session);
        } else {
            this.session.addLocalTempTable(tableView);
        }
        return tableView;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private CreateView parseCreateView(boolean bl, boolean bl2) {
        Object object;
        CreateView createView;
        boolean bl3 = this.readIfNotExists();
        boolean bl4 = this.readIf("TABLE_EXPRESSION");
        String string = this.readIdentifierWithSchema();
        this.createView = createView = new CreateView(this.session, this.getSchema());
        createView.setViewName(string);
        createView.setIfNotExists(bl3);
        createView.setComment(this.readCommentIf());
        createView.setOrReplace(bl2);
        createView.setForce(bl);
        createView.setTableExpression(bl4);
        if (this.readIf(105)) {
            object = this.parseColumnList();
            createView.setColumnNames((String[])object);
        }
        this.read(7);
        object = StringUtils.cache(this.sqlCommand.substring(this.token.start()));
        try {
            Query query;
            this.session.setParsingCreateView(true);
            try {
                query = this.parseQuery();
                query.prepare();
            }
            finally {
                this.session.setParsingCreateView(false);
            }
            createView.setSelect(query);
        }
        catch (DbException dbException) {
            if (bl) {
                createView.setSelectSQL((String)object);
                while (this.currentTokenType != 93) {
                    this.read();
                }
            }
            throw dbException;
        }
        return createView;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private CreateMaterializedView parseCreateMaterializedView(boolean bl, boolean bl2) {
        Query query;
        boolean bl3 = this.readIfNotExists();
        String string = this.readIdentifierWithSchema();
        this.read(7);
        CreateMaterializedView createMaterializedView = new CreateMaterializedView(this.session, this.getSchema());
        createMaterializedView.setViewName(string);
        createMaterializedView.setIfNotExists(bl3);
        createMaterializedView.setComment(this.readCommentIf());
        createMaterializedView.setOrReplace(bl2);
        if (bl) {
            throw new UnsupportedOperationException("not yet implemented");
        }
        String string2 = StringUtils.cache(this.sqlCommand.substring(this.token.start()));
        this.session.setParsingCreateView(true);
        try {
            query = this.parseQuery();
        }
        finally {
            this.session.setParsingCreateView(false);
        }
        createMaterializedView.setSelect(query);
        createMaterializedView.setSelectSQL(string2);
        return createMaterializedView;
    }

    private TransactionCommand parseCheckpoint() {
        TransactionCommand transactionCommand = this.readIf("SYNC") ? new TransactionCommand(this.session, 76) : new TransactionCommand(this.session, 73);
        return transactionCommand;
    }

    private Prepared parseAlter() {
        if (this.readIf(75)) {
            return this.parseAlterTable();
        }
        if (this.readIf(82)) {
            return this.parseAlterUser();
        }
        if (this.readIf("INDEX")) {
            return this.parseAlterIndex();
        }
        if (this.readIf("SCHEMA")) {
            return this.parseAlterSchema();
        }
        if (this.readIf("SEQUENCE")) {
            return this.parseAlterSequence();
        }
        if (this.readIf("VIEW")) {
            return this.parseAlterView();
        }
        if (this.readIf("DOMAIN")) {
            return this.parseAlterDomain();
        }
        throw this.getSyntaxError();
    }

    private void checkSchema(Schema schema) {
        if (schema != null && this.getSchema() != schema) {
            throw DbException.get(90080);
        }
    }

    private AlterIndexRename parseAlterIndex() {
        boolean bl = this.readIfExists(false);
        String string = this.readIdentifierWithSchema();
        Schema schema = this.getSchema();
        AlterIndexRename alterIndexRename = new AlterIndexRename(this.session);
        alterIndexRename.setOldSchema(schema);
        alterIndexRename.setOldName(string);
        alterIndexRename.setIfExists(bl);
        this.read("RENAME");
        this.read(76);
        String string2 = this.readIdentifierWithSchema(schema.getName());
        this.checkSchema(schema);
        alterIndexRename.setNewName(string2);
        return alterIndexRename;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private DefineCommand parseAlterDomain() {
        boolean bl = this.readIfExists(false);
        String string = this.readIdentifierWithSchema();
        Schema schema = this.getSchema();
        if (this.readIf("ADD")) {
            boolean bl2 = false;
            String string2 = null;
            String string3 = null;
            if (this.readIf(14)) {
                bl2 = this.readIfNotExists();
                string2 = this.readIdentifierWithSchema(schema.getName());
                this.checkSchema(schema);
                string3 = this.readCommentIf();
            }
            this.read(13);
            AlterDomainAddConstraint alterDomainAddConstraint = new AlterDomainAddConstraint(this.session, schema, bl2);
            alterDomainAddConstraint.setDomainName(string);
            alterDomainAddConstraint.setConstraintName(string2);
            this.parseDomainConstraint = true;
            try {
                alterDomainAddConstraint.setCheckExpression(this.readExpression());
            }
            finally {
                this.parseDomainConstraint = false;
            }
            alterDomainAddConstraint.setIfDomainExists(bl);
            alterDomainAddConstraint.setComment(string3);
            if (this.readIf("NOCHECK")) {
                alterDomainAddConstraint.setCheckExisting(false);
            } else {
                this.readIf(13);
                alterDomainAddConstraint.setCheckExisting(true);
            }
            return alterDomainAddConstraint;
        }
        if (this.readIf("DROP")) {
            if (this.readIf(14)) {
                boolean bl3 = this.readIfExists(false);
                String string4 = this.readIdentifierWithSchema(schema.getName());
                this.checkSchema(schema);
                AlterDomainDropConstraint alterDomainDropConstraint = new AlterDomainDropConstraint(this.session, this.getSchema(), bl3);
                alterDomainDropConstraint.setConstraintName(string4);
                alterDomainDropConstraint.setDomainName(string);
                alterDomainDropConstraint.setIfDomainExists(bl);
                return alterDomainDropConstraint;
            }
            if (this.readIf(25)) {
                AlterDomainExpressions alterDomainExpressions = new AlterDomainExpressions(this.session, schema, 94);
                alterDomainExpressions.setDomainName(string);
                alterDomainExpressions.setIfDomainExists(bl);
                alterDomainExpressions.setExpression(null);
                return alterDomainExpressions;
            }
            if (this.readIf(60, "UPDATE")) {
                AlterDomainExpressions alterDomainExpressions = new AlterDomainExpressions(this.session, schema, 95);
                alterDomainExpressions.setDomainName(string);
                alterDomainExpressions.setIfDomainExists(bl);
                alterDomainExpressions.setExpression(null);
                return alterDomainExpressions;
            }
        } else {
            if (this.readIf("RENAME")) {
                if (this.readIf(14)) {
                    String string5 = this.readIdentifierWithSchema(schema.getName());
                    this.checkSchema(schema);
                    this.read(76);
                    AlterDomainRenameConstraint alterDomainRenameConstraint = new AlterDomainRenameConstraint(this.session, schema);
                    alterDomainRenameConstraint.setDomainName(string);
                    alterDomainRenameConstraint.setIfDomainExists(bl);
                    alterDomainRenameConstraint.setConstraintName(string5);
                    alterDomainRenameConstraint.setNewConstraintName(this.readIdentifier());
                    return alterDomainRenameConstraint;
                }
                this.read(76);
                String string6 = this.readIdentifierWithSchema(schema.getName());
                this.checkSchema(schema);
                AlterDomainRename alterDomainRename = new AlterDomainRename(this.session, this.getSchema());
                alterDomainRename.setDomainName(string);
                alterDomainRename.setIfDomainExists(bl);
                alterDomainRename.setNewDomainName(string6);
                return alterDomainRename;
            }
            this.read(71);
            if (this.readIf(25)) {
                AlterDomainExpressions alterDomainExpressions = new AlterDomainExpressions(this.session, schema, 94);
                alterDomainExpressions.setDomainName(string);
                alterDomainExpressions.setIfDomainExists(bl);
                alterDomainExpressions.setExpression(this.readExpression());
                return alterDomainExpressions;
            }
            if (this.readIf(60, "UPDATE")) {
                AlterDomainExpressions alterDomainExpressions = new AlterDomainExpressions(this.session, schema, 95);
                alterDomainExpressions.setDomainName(string);
                alterDomainExpressions.setIfDomainExists(bl);
                alterDomainExpressions.setExpression(this.readExpression());
                return alterDomainExpressions;
            }
        }
        throw this.getSyntaxError();
    }

    private DefineCommand parseAlterView() {
        boolean bl = this.readIfExists(false);
        String string = this.readIdentifierWithSchema();
        Schema schema = this.getSchema();
        Table table = schema.findTableOrView(this.session, string);
        if (!(table instanceof TableView) && !bl) {
            throw DbException.get(90037, string);
        }
        if (this.readIf("RENAME", 76)) {
            String string2 = this.readIdentifierWithSchema(schema.getName());
            this.checkSchema(schema);
            AlterTableRename alterTableRename = new AlterTableRename(this.session, this.getSchema());
            alterTableRename.setTableName(string);
            alterTableRename.setNewTableName(string2);
            alterTableRename.setIfTableExists(bl);
            return alterTableRename;
        }
        this.read("RECOMPILE");
        TableView tableView = (TableView)table;
        AlterView alterView = new AlterView(this.session);
        alterView.setIfExists(bl);
        alterView.setView(tableView);
        return alterView;
    }

    private Prepared parseAlterSchema() {
        boolean bl = this.readIfExists(false);
        String string = this.readIdentifierWithSchema();
        Schema schema = this.getSchema();
        this.read("RENAME");
        this.read(76);
        String string2 = this.readIdentifierWithSchema(schema.getName());
        Schema schema2 = this.findSchema(string);
        if (schema2 == null) {
            if (bl) {
                return new NoOperation(this.session);
            }
            throw DbException.get(90079, string);
        }
        AlterSchemaRename alterSchemaRename = new AlterSchemaRename(this.session);
        alterSchemaRename.setOldSchema(schema2);
        this.checkSchema(schema);
        alterSchemaRename.setNewName(string2);
        return alterSchemaRename;
    }

    private AlterSequence parseAlterSequence() {
        boolean bl = this.readIfExists(false);
        String string = this.readIdentifierWithSchema();
        AlterSequence alterSequence = new AlterSequence(this.session, this.getSchema());
        alterSequence.setSequenceName(string);
        alterSequence.setIfExists(bl);
        SequenceOptions sequenceOptions = new SequenceOptions();
        this.parseSequenceOptions(sequenceOptions, null, false, false);
        alterSequence.setOptions(sequenceOptions);
        return alterSequence;
    }

    private boolean parseSequenceOptions(SequenceOptions sequenceOptions, CreateSequence createSequence, boolean bl, boolean bl2) {
        boolean bl3 = false;
        while (true) {
            if (bl && this.readIf(7)) {
                TypeInfo typeInfo = this.parseDataType();
                if (!DataType.isNumericType(typeInfo.getValueType())) {
                    throw DbException.getUnsupportedException(typeInfo.getSQL(new StringBuilder("CREATE SEQUENCE AS "), 3).toString());
                }
                sequenceOptions.setDataType(typeInfo);
            } else if (this.readIf("START", 89) || this.database.getMode().getEnum() == Mode.ModeEnum.PostgreSQL && this.readIfCompat("START")) {
                sequenceOptions.setStartValue(this.readExpression());
            } else if (this.readIf("RESTART")) {
                sequenceOptions.setRestartValue(this.readIf(89) ? this.readExpression() : ValueExpression.DEFAULT);
            } else if (createSequence == null || !this.parseCreateSequenceOption(createSequence)) {
                if (bl2) {
                    int n = this.tokenIndex;
                    if (!this.readIf(71)) break;
                    if (!this.parseBasicSequenceOption(sequenceOptions)) {
                        this.setTokenIndex(n);
                        break;
                    }
                } else if (!this.parseBasicSequenceOption(sequenceOptions)) break;
            }
            bl3 = true;
        }
        return bl3;
    }

    private boolean parseCreateSequenceOption(CreateSequence createSequence) {
        if (this.readIf("BELONGS_TO_TABLE")) {
            createSequence.setBelongsToTable(true);
        } else if (!this.readIfCompat(62)) {
            return false;
        }
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean parseBasicSequenceOption(SequenceOptions sequenceOptions) {
        if (this.readIf("INCREMENT")) {
            this.readIf("BY");
            sequenceOptions.setIncrement(this.readExpression());
            return true;
        } else if (this.readIf("MINVALUE")) {
            sequenceOptions.setMinValue(this.readExpression());
            return true;
        } else if (this.readIf("MAXVALUE")) {
            sequenceOptions.setMaxValue(this.readExpression());
            return true;
        } else if (this.readIf("CYCLE")) {
            sequenceOptions.setCycle(Sequence.Cycle.CYCLE);
            return true;
        } else if (this.readIf("NO")) {
            if (this.readIf("MINVALUE")) {
                sequenceOptions.setMinValue(ValueExpression.NULL);
                return true;
            } else if (this.readIf("MAXVALUE")) {
                sequenceOptions.setMaxValue(ValueExpression.NULL);
                return true;
            } else if (this.readIf("CYCLE")) {
                sequenceOptions.setCycle(Sequence.Cycle.NO_CYCLE);
                return true;
            } else {
                if (!this.readIf("CACHE")) throw this.getSyntaxError();
                sequenceOptions.setCacheSize(ValueExpression.get(ValueBigint.get(1L)));
            }
            return true;
        } else if (this.readIf("EXHAUSTED")) {
            sequenceOptions.setCycle(Sequence.Cycle.EXHAUSTED);
            return true;
        } else if (this.readIf("CACHE")) {
            sequenceOptions.setCacheSize(this.readExpression());
            return true;
        } else if (this.readIfCompat("NOMINVALUE")) {
            sequenceOptions.setMinValue(ValueExpression.NULL);
            return true;
        } else if (this.readIfCompat("NOMAXVALUE")) {
            sequenceOptions.setMaxValue(ValueExpression.NULL);
            return true;
        } else if (this.readIfCompat("NOCYCLE")) {
            sequenceOptions.setCycle(Sequence.Cycle.NO_CYCLE);
            return true;
        } else {
            if (!this.readIfCompat("NOCACHE")) return false;
            sequenceOptions.setCacheSize(ValueExpression.get(ValueBigint.get(1L)));
        }
        return true;
    }

    private AlterUser parseAlterUser() {
        String string = this.readIdentifier();
        if (this.readIf(71)) {
            AlterUser alterUser = new AlterUser(this.session);
            alterUser.setType(19);
            alterUser.setUser(this.database.getUser(string));
            if (this.readIf("PASSWORD")) {
                alterUser.setPassword(this.readExpression());
            } else if (this.readIf("SALT")) {
                alterUser.setSalt(this.readExpression());
                this.read("HASH");
                alterUser.setHash(this.readExpression());
            } else {
                throw this.getSyntaxError();
            }
            return alterUser;
        }
        if (this.readIf("RENAME", 76)) {
            AlterUser alterUser = new AlterUser(this.session);
            alterUser.setType(18);
            alterUser.setUser(this.database.getUser(string));
            alterUser.setNewName(this.readIdentifier());
            return alterUser;
        }
        if (this.readIf("ADMIN")) {
            AlterUser alterUser = new AlterUser(this.session);
            alterUser.setType(17);
            User user = this.database.getUser(string);
            alterUser.setUser(user);
            if (this.readIf(77)) {
                alterUser.setAdmin(true);
            } else if (this.readIf(31)) {
                alterUser.setAdmin(false);
            } else {
                throw this.getSyntaxError();
            }
            return alterUser;
        }
        throw this.getSyntaxError();
    }

    private void readIfEqualOrTo() {
        if (!this.readIf(95)) {
            this.readIf(76);
        }
    }

    private Prepared parseSet() {
        Object object;
        if (this.readIf(101)) {
            Set set = new Set(this.session, 29);
            set.setString(this.readIdentifier());
            this.readIfEqualOrTo();
            set.setExpression(this.readExpression());
            return set;
        }
        if (this.readIf("AUTOCOMMIT")) {
            this.readIfEqualOrTo();
            return new TransactionCommand(this.session, this.readBooleanSetting() ? 69 : 70);
        }
        if (this.readIf("EXCLUSIVE")) {
            this.readIfEqualOrTo();
            Set set = new Set(this.session, 27);
            set.setExpression(this.readExpression());
            return set;
        }
        if (this.readIf("IGNORECASE")) {
            this.readIfEqualOrTo();
            Set set = new Set(this.session, 0);
            set.setInt(this.readBooleanSetting() ? 1 : 0);
            return set;
        }
        if (this.readIf("PASSWORD")) {
            this.readIfEqualOrTo();
            AlterUser alterUser = new AlterUser(this.session);
            alterUser.setType(19);
            alterUser.setUser(this.session.getUser());
            alterUser.setPassword(this.readExpression());
            return alterUser;
        }
        if (this.readIf("SALT")) {
            this.readIfEqualOrTo();
            AlterUser alterUser = new AlterUser(this.session);
            alterUser.setType(19);
            alterUser.setUser(this.session.getUser());
            alterUser.setSalt(this.readExpression());
            this.read("HASH");
            alterUser.setHash(this.readExpression());
            return alterUser;
        }
        if (this.readIf("MODE")) {
            this.readIfEqualOrTo();
            Set set = new Set(this.session, 2);
            set.setString(this.readIdentifier());
            return set;
        }
        if (this.readIf("DATABASE")) {
            this.readIfEqualOrTo();
            this.read("COLLATION");
            return this.parseSetCollation();
        }
        if (this.readIf("COLLATION")) {
            this.readIfEqualOrTo();
            return this.parseSetCollation();
        }
        if (this.readIf("CLUSTER")) {
            this.readIfEqualOrTo();
            Set set = new Set(this.session, 12);
            set.setString(this.readString());
            return set;
        }
        if (this.readIf("DATABASE_EVENT_LISTENER")) {
            this.readIfEqualOrTo();
            Set set = new Set(this.session, 14);
            set.setString(this.readString());
            return set;
        }
        if (this.readIf("ALLOW_LITERALS")) {
            this.readIfEqualOrTo();
            Set set = new Set(this.session, 21);
            int n = this.readIf(3) ? 2 : (this.readIf("NONE") ? 0 : (this.readIf("NUMBERS") ? 1 : this.readNonNegativeInt()));
            set.setInt(n);
            return set;
        }
        if (this.readIf("DEFAULT_TABLE_TYPE")) {
            this.readIfEqualOrTo();
            Set set = new Set(this.session, 6);
            int n = this.readIf("MEMORY") ? 1 : (this.readIf("CACHED") ? 0 : this.readNonNegativeInt());
            set.setInt(n);
            return set;
        }
        if (this.readIf("SCHEMA")) {
            this.readIfEqualOrTo();
            Set set = new Set(this.session, 22);
            set.setExpression(this.readExpressionOrIdentifier());
            return set;
        }
        if (this.readIf("CATALOG")) {
            this.readIfEqualOrTo();
            Set set = new Set(this.session, 40);
            set.setExpression(this.readExpressionOrIdentifier());
            return set;
        }
        if (this.readIf(SetTypes.getTypeName(24))) {
            this.readIfEqualOrTo();
            Set set = new Set(this.session, 24);
            ArrayList<String> arrayList = Utils.newSmallArrayList();
            do {
                arrayList.add(this.readIdentifier());
            } while (this.readIf(109));
            set.setStringArray(arrayList.toArray(new String[0]));
            return set;
        }
        if (this.readIf("JAVA_OBJECT_SERIALIZER")) {
            this.readIfEqualOrTo();
            Set set = new Set(this.session, 32);
            set.setString(this.readString());
            return set;
        }
        if (this.readIf("IGNORE_CATALOGS")) {
            this.readIfEqualOrTo();
            Set set = new Set(this.session, 39);
            set.setInt(this.readBooleanSetting() ? 1 : 0);
            return set;
        }
        if (this.readIf("SESSION")) {
            this.read("CHARACTERISTICS");
            this.read(7);
            this.read("TRANSACTION");
            return this.parseSetTransactionMode();
        }
        if (this.readIf("TRANSACTION")) {
            return this.parseSetTransactionMode();
        }
        if (this.readIf("TIME")) {
            this.read("ZONE");
            Set set = new Set(this.session, 42);
            if (!this.readIf("LOCAL")) {
                set.setExpression(this.readExpression());
            }
            return set;
        }
        if (this.readIf("NON_KEYWORDS")) {
            this.readIfEqualOrTo();
            Set set = new Set(this.session, 41);
            ArrayList<String> arrayList = Utils.newSmallArrayList();
            if (this.currentTokenType != 93 && this.currentTokenType != 115) {
                do {
                    arrayList.add(StringUtils.toUpperEnglish(this.readIdentifierOrKeyword()));
                } while (this.readIf(109));
            }
            set.setStringArray(arrayList.toArray(new String[0]));
            return set;
        }
        if (this.readIf("DEFAULT_NULL_ORDERING")) {
            this.readIfEqualOrTo();
            Set set = new Set(this.session, 44);
            set.setString(this.readIdentifier());
            return set;
        }
        if (this.readIfCompat("LOG")) {
            throw DbException.getUnsupportedException("LOG");
        }
        String string = this.upperName(this.currentToken);
        if (ConnectionInfo.isIgnoredByParser(string)) {
            this.read();
            this.readIfEqualOrTo();
            this.read();
            return new NoOperation(this.session);
        }
        int n = SetTypes.getType(string);
        if (n >= 0) {
            this.read();
            this.readIfEqualOrTo();
            Set set = new Set(this.session, n);
            set.setExpression(this.readExpression());
            return set;
        }
        Mode.ModeEnum modeEnum = this.database.getMode().getEnum();
        if (modeEnum != Mode.ModeEnum.REGULAR && (object = this.readSetCompatibility(modeEnum)) != null) {
            return object;
        }
        if (this.session.isQuirksMode()) {
            switch (string) {
                case "BINARY_COLLATION": 
                case "UUID_COLLATION": {
                    this.read();
                    this.readIfEqualOrTo();
                    this.readIdentifier();
                    return new NoOperation(this.session);
                }
            }
        }
        throw this.getSyntaxError();
    }

    private Prepared parseSetTransactionMode() {
        IsolationLevel isolationLevel;
        this.read("ISOLATION");
        this.read("LEVEL");
        if (this.readIf("READ")) {
            if (this.readIf("UNCOMMITTED")) {
                isolationLevel = IsolationLevel.READ_UNCOMMITTED;
            } else {
                this.read("COMMITTED");
                isolationLevel = IsolationLevel.READ_COMMITTED;
            }
        } else if (this.readIf("REPEATABLE")) {
            this.read("READ");
            isolationLevel = IsolationLevel.REPEATABLE_READ;
        } else if (this.readIf("SNAPSHOT")) {
            isolationLevel = IsolationLevel.SNAPSHOT;
        } else {
            this.read("SERIALIZABLE");
            isolationLevel = IsolationLevel.SERIALIZABLE;
        }
        return new SetSessionCharacteristics(this.session, isolationLevel);
    }

    private Expression readExpressionOrIdentifier() {
        if (this.isIdentifier()) {
            return ValueExpression.get(ValueVarchar.get(this.readIdentifier()));
        }
        return this.readExpression();
    }

    private Prepared parseUse() {
        this.readIfEqualOrTo();
        Set set = new Set(this.session, 22);
        set.setExpression(ValueExpression.get(ValueVarchar.get(this.readIdentifier())));
        return set;
    }

    private Set parseSetCollation() {
        Set set = new Set(this.session, 11);
        String string = this.readIdentifier();
        set.setString(string);
        if (this.equalsToken(string, "OFF")) {
            return set;
        }
        Collator collator = CompareMode.getCollator(string);
        if (collator == null) {
            throw DbException.getInvalidValueException("collation", string);
        }
        if (this.readIf("STRENGTH")) {
            if (this.readIf(63)) {
                set.setInt(0);
            } else if (this.readIf("SECONDARY")) {
                set.setInt(1);
            } else if (this.readIf("TERTIARY")) {
                set.setInt(2);
            } else if (this.readIf("IDENTICAL")) {
                set.setInt(3);
            }
        } else {
            set.setInt(collator.getStrength());
        }
        return set;
    }

    private Prepared readSetCompatibility(Mode.ModeEnum modeEnum) {
        switch (modeEnum) {
            case Derby: {
                if (!this.readIfCompat("CREATE")) break;
                this.readIfEqualOrTo();
                this.read();
                return new NoOperation(this.session);
            }
            case HSQLDB: {
                if (!this.readIfCompat("LOGSIZE")) break;
                this.readIfEqualOrTo();
                Set set = new Set(this.session, 1);
                set.setExpression(this.readExpression());
                return set;
            }
            case MariaDB: 
            case MySQL: {
                if (this.readIfCompat("FOREIGN_KEY_CHECKS")) {
                    this.readIfEqualOrTo();
                    Set set = new Set(this.session, 25);
                    set.setExpression(this.readExpression());
                    return set;
                }
                if (!this.readIfCompat("NAMES")) break;
                this.readIfEqualOrTo();
                this.read();
                return new NoOperation(this.session);
            }
            case PostgreSQL: {
                if (this.readIfCompat("STATEMENT_TIMEOUT")) {
                    this.readIfEqualOrTo();
                    Set set = new Set(this.session, 30);
                    set.setInt(this.readNonNegativeInt());
                    return set;
                }
                if (this.readIfCompat("CLIENT_ENCODING") || this.readIfCompat("CLIENT_MIN_MESSAGES") || this.readIfCompat("JOIN_COLLAPSE_LIMIT")) {
                    this.readIfEqualOrTo();
                    this.read();
                    return new NoOperation(this.session);
                }
                if (this.readIfCompat("DATESTYLE")) {
                    String string;
                    this.readIfEqualOrTo();
                    if (!this.readIf("ISO") && !this.equalsToken(string = this.readString(), "ISO")) {
                        throw this.getSyntaxError();
                    }
                    return new NoOperation(this.session);
                }
                if (!this.readIfCompat("SEARCH_PATH")) break;
                this.readIfEqualOrTo();
                Set set = new Set(this.session, 24);
                ArrayList<String> arrayList = Utils.newSmallArrayList();
                String string = this.database.sysIdentifier("PG_CATALOG");
                boolean bl = false;
                do {
                    String string2;
                    String string3 = string2 = this.currentTokenType == 94 ? this.readString() : this.readIdentifier();
                    if ("$user".equals(string2)) continue;
                    if (string.equals(string2)) {
                        bl = true;
                    }
                    arrayList.add(string2);
                } while (this.readIf(109));
                if (!bl && this.database.findSchema(string) != null) {
                    arrayList.add(0, string);
                }
                set.setStringArray(arrayList.toArray(new String[0]));
                return set;
            }
        }
        return null;
    }

    private RunScriptCommand parseRunScript() {
        RunScriptCommand runScriptCommand = new RunScriptCommand(this.session);
        this.read(35);
        runScriptCommand.setFileNameExpr(this.readExpression());
        if (this.readIf("COMPRESSION")) {
            runScriptCommand.setCompressionAlgorithm(this.readIdentifier());
        }
        if (this.readIf("CIPHER")) {
            runScriptCommand.setCipher(this.readIdentifier());
            if (this.readIf("PASSWORD")) {
                runScriptCommand.setPassword(this.readExpression());
            }
        }
        if (this.readIf("CHARSET")) {
            runScriptCommand.setCharset(Charset.forName(this.readString()));
        }
        if (this.readIf("FROM_1X")) {
            runScriptCommand.setFrom1X();
        } else {
            if (this.readIf("QUIRKS_MODE")) {
                runScriptCommand.setQuirksMode(true);
            }
            if (this.readIf("VARIABLE_BINARY")) {
                runScriptCommand.setVariableBinary(true);
            }
        }
        return runScriptCommand;
    }

    private ScriptCommand parseScript() {
        ScriptCommand scriptCommand = new ScriptCommand(this.session);
        boolean bl = true;
        boolean bl2 = true;
        boolean bl3 = true;
        boolean bl4 = true;
        boolean bl5 = false;
        boolean bl6 = false;
        boolean bl7 = false;
        if (this.readIf("NODATA")) {
            bl = false;
        } else {
            if (this.readIf("SIMPLE")) {
                bl6 = true;
            }
            if (this.readIf("COLUMNS")) {
                bl7 = true;
            }
        }
        if (this.readIf("NOPASSWORDS")) {
            bl2 = false;
        }
        if (this.readIf("NOSETTINGS")) {
            bl3 = false;
        }
        if (this.readIf("NOVERSION")) {
            bl4 = false;
        }
        if (this.readIf("DROP")) {
            bl5 = true;
        }
        if (this.readIf("BLOCKSIZE")) {
            long l = this.readLong();
            scriptCommand.setLobBlockSize(l);
        }
        scriptCommand.setData(bl);
        scriptCommand.setPasswords(bl2);
        scriptCommand.setSettings(bl3);
        scriptCommand.setVersion(bl4);
        scriptCommand.setDrop(bl5);
        scriptCommand.setSimple(bl6);
        scriptCommand.setWithColumns(bl7);
        if (this.readIf(76)) {
            scriptCommand.setFileNameExpr(this.readExpression());
            if (this.readIf("COMPRESSION")) {
                scriptCommand.setCompressionAlgorithm(this.readIdentifier());
            }
            if (this.readIf("CIPHER")) {
                scriptCommand.setCipher(this.readIdentifier());
                if (this.readIf("PASSWORD")) {
                    scriptCommand.setPassword(this.readExpression());
                }
            }
            if (this.readIf("CHARSET")) {
                scriptCommand.setCharset(Charset.forName(this.readString()));
            }
        }
        if (this.readIf("SCHEMA")) {
            HashSet<String> hashSet = new HashSet<String>();
            do {
                hashSet.add(this.readIdentifier());
            } while (this.readIf(109));
            scriptCommand.setSchemaNames(hashSet);
        } else if (this.readIf(75)) {
            ArrayList<Table> arrayList = Utils.newSmallArrayList();
            do {
                arrayList.add(this.readTableOrView());
            } while (this.readIf(109));
            scriptCommand.setTables(arrayList);
        }
        return scriptCommand;
    }

    private boolean isDualTable(String string) {
        return (this.schemaName == null || this.equalsToken(this.schemaName, "SYS")) && this.equalsToken("DUAL", string) || this.database.getMode().sysDummy1 && (this.schemaName == null || this.equalsToken(this.schemaName, "SYSIBM")) && this.equalsToken("SYSDUMMY1", string);
    }

    private Table readTableOrView() {
        return this.readTableOrView(this.readIdentifierWithSchema(null), true);
    }

    private Table readTableOrView(boolean bl) {
        return this.readTableOrView(this.readIdentifierWithSchema(null), bl);
    }

    private Table readTableOrView(String string, boolean bl) {
        if (this.schemaName != null) {
            Table table = this.getSchema().resolveTableOrView(this.session, string, bl);
            if (table != null) {
                return table;
            }
        } else {
            Table table = this.database.getSchema(this.session.getCurrentSchemaName()).resolveTableOrView(this.session, string, bl);
            if (table != null) {
                return table;
            }
            String[] stringArray = this.session.getSchemaSearchPath();
            if (stringArray != null) {
                for (String string2 : stringArray) {
                    Schema schema = this.database.getSchema(string2);
                    table = schema.resolveTableOrView(this.session, string, bl);
                    if (table == null) continue;
                    return table;
                }
            }
        }
        if (this.isDualTable(string)) {
            return new DualTable(this.database);
        }
        throw this.getTableOrViewNotFoundDbException(string);
    }

    private DbException getTableOrViewNotFoundDbException(String string) {
        if (this.schemaName != null) {
            return this.getTableOrViewNotFoundDbException(this.schemaName, string);
        }
        String string2 = this.session.getCurrentSchemaName();
        String[] stringArray = this.session.getSchemaSearchPath();
        if (stringArray == null) {
            return this.getTableOrViewNotFoundDbException(Collections.singleton(string2), string);
        }
        LinkedHashSet<String> linkedHashSet = new LinkedHashSet<String>();
        linkedHashSet.add(string2);
        linkedHashSet.addAll(Arrays.asList(stringArray));
        return this.getTableOrViewNotFoundDbException(linkedHashSet, string);
    }

    private DbException getTableOrViewNotFoundDbException(String string, String string2) {
        return this.getTableOrViewNotFoundDbException(Collections.singleton(string), string2);
    }

    private DbException getTableOrViewNotFoundDbException(java.util.Set<String> set, String string) {
        if (this.database == null || this.database.getFirstUserTable() == null) {
            return DbException.get(42104, string);
        }
        if (this.database.getSettings().caseInsensitiveIdentifiers) {
            return DbException.get(42102, string);
        }
        TreeSet<String> treeSet = new TreeSet<String>();
        for (String string2 : set) {
            this.findTableNameCandidates(string2, string, treeSet);
        }
        if (treeSet.isEmpty()) {
            return DbException.get(42102, string);
        }
        return DbException.get(42103, string, String.join((CharSequence)", ", treeSet));
    }

    private void findTableNameCandidates(String string, String string2, java.util.Set<String> set) {
        Schema schema = this.database.getSchema(string);
        String string3 = StringUtils.toUpperEnglish(string2);
        Collection<Table> collection = schema.getAllTablesAndViews(this.session);
        for (Table table : collection) {
            String string4 = table.getName();
            if (!string3.equals(StringUtils.toUpperEnglish(string4))) continue;
            set.add(string4);
        }
    }

    private UserDefinedFunction findUserDefinedFunctionWithinPath(Schema schema, String string) {
        if (schema != null) {
            return schema.findFunctionOrAggregate(string);
        }
        schema = this.database.getSchema(this.session.getCurrentSchemaName());
        UserDefinedFunction userDefinedFunction = schema.findFunctionOrAggregate(string);
        if (userDefinedFunction != null) {
            return userDefinedFunction;
        }
        String[] stringArray = this.session.getSchemaSearchPath();
        if (stringArray != null) {
            for (String string2 : stringArray) {
                Schema schema2 = this.database.getSchema(string2);
                if (schema2 == schema || (userDefinedFunction = schema2.findFunctionOrAggregate(string)) == null) continue;
                return userDefinedFunction;
            }
        }
        return null;
    }

    private Sequence findSequence(String string, String string2) {
        Sequence sequence2 = this.database.getSchema(string).findSequence(string2);
        if (sequence2 != null) {
            return sequence2;
        }
        String[] stringArray = this.session.getSchemaSearchPath();
        if (stringArray != null) {
            for (String string3 : stringArray) {
                sequence2 = this.database.getSchema(string3).findSequence(string2);
                if (sequence2 == null) continue;
                return sequence2;
            }
        }
        return null;
    }

    private Sequence readSequence() {
        String string = this.readIdentifierWithSchema(null);
        if (this.schemaName != null) {
            return this.getSchema().getSequence(string);
        }
        Sequence sequence2 = this.findSequence(this.session.getCurrentSchemaName(), string);
        if (sequence2 != null) {
            return sequence2;
        }
        throw DbException.get(90036, string);
    }

    private Prepared parseAlterTable() {
        boolean bl = this.readIfExists(false);
        String string = this.readIdentifierWithSchema();
        Schema schema = this.getSchema();
        if (this.readIf("ADD")) {
            DefineCommand defineCommand = this.parseTableConstraintIf(string, schema, bl);
            if (defineCommand != null) {
                return defineCommand;
            }
            return this.parseAlterTableAddColumn(string, schema, bl);
        }
        if (this.readIf(71)) {
            return this.parseAlterTableSet(schema, string, bl);
        }
        if (this.readIf("RENAME")) {
            return this.parseAlterTableRename(schema, string, bl);
        }
        if (this.readIf("DROP")) {
            return this.parseAlterTableDrop(schema, string, bl);
        }
        if (this.readIf("ALTER")) {
            return this.parseAlterTableAlter(schema, string, bl);
        }
        Mode mode = this.database.getMode();
        if (mode.alterTableExtensionsMySQL || mode.alterTableModifyColumn) {
            return this.parseAlterTableCompatibility(schema, string, bl, mode);
        }
        throw this.getSyntaxError();
    }

    private Prepared parseAlterTableAlter(Schema schema, String string, boolean bl) {
        this.readIf("COLUMN");
        boolean bl2 = this.readIfExists(false);
        String string2 = this.readIdentifier();
        Column column = this.columnIfTableExists(schema, string, string2, bl, bl2);
        if (this.readIf("RENAME")) {
            this.read(76);
            AlterTableRenameColumn alterTableRenameColumn = new AlterTableRenameColumn(this.session, schema);
            alterTableRenameColumn.setTableName(string);
            alterTableRenameColumn.setIfTableExists(bl);
            alterTableRenameColumn.setIfExists(bl2);
            alterTableRenameColumn.setOldColumnName(string2);
            String string3 = this.readIdentifier();
            alterTableRenameColumn.setNewColumnName(string3);
            return alterTableRenameColumn;
        }
        if (this.readIf("DROP")) {
            if (this.readIf(25)) {
                if (this.readIf(60, 58)) {
                    AlterTableAlterColumn alterTableAlterColumn = new AlterTableAlterColumn(this.session, schema);
                    alterTableAlterColumn.setTableName(string);
                    alterTableAlterColumn.setIfTableExists(bl);
                    alterTableAlterColumn.setOldColumn(column);
                    alterTableAlterColumn.setType(100);
                    alterTableAlterColumn.setBooleanFlag(false);
                    return alterTableAlterColumn;
                }
                return this.getAlterTableAlterColumnDropDefaultExpression(schema, string, bl, column, 10);
            }
            if (this.readIf("EXPRESSION")) {
                return this.getAlterTableAlterColumnDropDefaultExpression(schema, string, bl, column, 98);
            }
            if (this.readIf("IDENTITY")) {
                return this.getAlterTableAlterColumnDropDefaultExpression(schema, string, bl, column, 99);
            }
            if (this.readIf(60, "UPDATE")) {
                AlterTableAlterColumn alterTableAlterColumn = new AlterTableAlterColumn(this.session, schema);
                alterTableAlterColumn.setTableName(string);
                alterTableAlterColumn.setIfTableExists(bl);
                alterTableAlterColumn.setOldColumn(column);
                alterTableAlterColumn.setType(90);
                alterTableAlterColumn.setDefaultExpression(null);
                return alterTableAlterColumn;
            }
            this.read(57);
            this.read(58);
            AlterTableAlterColumn alterTableAlterColumn = new AlterTableAlterColumn(this.session, schema);
            alterTableAlterColumn.setTableName(string);
            alterTableAlterColumn.setIfTableExists(bl);
            alterTableAlterColumn.setOldColumn(column);
            alterTableAlterColumn.setType(9);
            return alterTableAlterColumn;
        }
        if (this.readIfCompat("TYPE")) {
            return this.parseAlterTableAlterColumnDataType(schema, string, string2, bl, bl2);
        }
        if (this.readIf("SELECTIVITY")) {
            AlterTableAlterColumn alterTableAlterColumn = new AlterTableAlterColumn(this.session, schema);
            alterTableAlterColumn.setTableName(string);
            alterTableAlterColumn.setIfTableExists(bl);
            alterTableAlterColumn.setType(13);
            alterTableAlterColumn.setOldColumn(column);
            alterTableAlterColumn.setSelectivity(this.readExpression());
            return alterTableAlterColumn;
        }
        Prepared prepared = this.parseAlterTableAlterColumnIdentity(schema, string, bl, column);
        if (prepared != null) {
            return prepared;
        }
        if (this.readIf(71)) {
            return this.parseAlterTableAlterColumnSet(schema, string, bl, bl2, string2, column);
        }
        return this.parseAlterTableAlterColumnType(schema, string, string2, bl, bl2, true);
    }

    private Prepared getAlterTableAlterColumnDropDefaultExpression(Schema schema, String string, boolean bl, Column column, int n) {
        AlterTableAlterColumn alterTableAlterColumn = new AlterTableAlterColumn(this.session, schema);
        alterTableAlterColumn.setTableName(string);
        alterTableAlterColumn.setIfTableExists(bl);
        alterTableAlterColumn.setOldColumn(column);
        alterTableAlterColumn.setType(n);
        alterTableAlterColumn.setDefaultExpression(null);
        return alterTableAlterColumn;
    }

    private Prepared parseAlterTableAlterColumnIdentity(Schema schema, String string, boolean bl, Column column) {
        SequenceOptions sequenceOptions;
        Boolean bl2 = null;
        if (this.readIf(71, "GENERATED")) {
            if (this.readIf("ALWAYS")) {
                bl2 = true;
            } else {
                this.read("BY");
                this.read(25);
                bl2 = false;
            }
        }
        if (!this.parseSequenceOptions(sequenceOptions = new SequenceOptions(), null, false, true) && bl2 == null) {
            return null;
        }
        if (column == null) {
            return new NoOperation(this.session);
        }
        if (!column.isIdentity()) {
            AlterTableAlterColumn alterTableAlterColumn = new AlterTableAlterColumn(this.session, schema);
            this.parseAlterColumnUsingIf(alterTableAlterColumn);
            alterTableAlterColumn.setTableName(string);
            alterTableAlterColumn.setIfTableExists(bl);
            alterTableAlterColumn.setType(11);
            alterTableAlterColumn.setOldColumn(column);
            Column column2 = column.getClone();
            column2.setIdentityOptions(sequenceOptions, bl2 != null && bl2 != false);
            alterTableAlterColumn.setNewColumn(column2);
            return alterTableAlterColumn;
        }
        AlterSequence alterSequence = new AlterSequence(this.session, schema);
        alterSequence.setColumn(column, bl2);
        alterSequence.setOptions(sequenceOptions);
        return this.commandIfTableExists(schema, string, bl, alterSequence);
    }

    private Prepared parseAlterTableAlterColumnSet(Schema schema, String string, boolean bl, boolean bl2, String string2, Column column) {
        if (this.readIf("DATA", "TYPE")) {
            return this.parseAlterTableAlterColumnDataType(schema, string, string2, bl, bl2);
        }
        AlterTableAlterColumn alterTableAlterColumn = new AlterTableAlterColumn(this.session, schema);
        alterTableAlterColumn.setTableName(string);
        alterTableAlterColumn.setIfTableExists(bl);
        alterTableAlterColumn.setOldColumn(column);
        NullConstraintType nullConstraintType = this.parseNotNullConstraint();
        switch (nullConstraintType) {
            case NULL_IS_ALLOWED: {
                alterTableAlterColumn.setType(9);
                break;
            }
            case NULL_IS_NOT_ALLOWED: {
                alterTableAlterColumn.setType(8);
                break;
            }
            case NO_NULL_CONSTRAINT_FOUND: {
                if (this.readIf(25)) {
                    if (this.readIf(60, 58)) {
                        alterTableAlterColumn.setType(100);
                        alterTableAlterColumn.setBooleanFlag(true);
                        break;
                    }
                    Expression expression = this.readExpression();
                    alterTableAlterColumn.setType(10);
                    alterTableAlterColumn.setDefaultExpression(expression);
                    break;
                }
                if (this.readIf(60, "UPDATE")) {
                    Expression expression = this.readExpression();
                    alterTableAlterColumn.setType(90);
                    alterTableAlterColumn.setDefaultExpression(expression);
                    break;
                }
                if (this.readIf("INVISIBLE")) {
                    alterTableAlterColumn.setType(87);
                    alterTableAlterColumn.setBooleanFlag(false);
                    break;
                }
                if (!this.readIf("VISIBLE")) break;
                alterTableAlterColumn.setType(87);
                alterTableAlterColumn.setBooleanFlag(true);
                break;
            }
            default: {
                throw DbException.get(90088, "Internal Error - unhandled case: " + nullConstraintType.name());
            }
        }
        return alterTableAlterColumn;
    }

    private Prepared parseAlterTableDrop(Schema schema, String string, boolean bl) {
        Object object;
        Prepared prepared;
        if (this.readIf(14)) {
            boolean bl2 = this.readIfExists(false);
            String string2 = this.readIdentifierWithSchema(schema.getName());
            bl2 = this.readIfExists(bl2);
            this.checkSchema(schema);
            AlterTableDropConstraint alterTableDropConstraint = new AlterTableDropConstraint(this.session, this.getSchema(), bl2);
            alterTableDropConstraint.setTableName(string);
            alterTableDropConstraint.setIfTableExists(bl);
            alterTableDropConstraint.setConstraintName(string2);
            ConstraintActionType constraintActionType = this.parseCascadeOrRestrict();
            if (constraintActionType != null) {
                alterTableDropConstraint.setDropAction(constraintActionType);
            }
            return alterTableDropConstraint;
        }
        if (this.readIf(63, 47)) {
            Table table = this.tableIfTableExists(schema, string, bl);
            if (table == null) {
                return new NoOperation(this.session);
            }
            Index index = table.getPrimaryKey();
            DropIndex dropIndex = new DropIndex(this.session, schema);
            dropIndex.setIndexName(index.getName());
            return dropIndex;
        }
        if (this.database.getMode().alterTableExtensionsMySQL && (prepared = this.parseAlterTableDropCompatibility(schema, string, bl)) != null) {
            return prepared;
        }
        this.readIf("COLUMN");
        boolean bl3 = this.readIfExists(false);
        ArrayList<Column> arrayList = new ArrayList<Column>();
        Table table = this.tableIfTableExists(schema, string, bl);
        boolean bl4 = this.readIf(105);
        do {
            Column column;
            object = this.readIdentifier();
            if (table == null || (column = table.getColumn((String)object, bl3)) == null) continue;
            arrayList.add(column);
        } while (this.readIf(109));
        if (bl4) {
            this.read(106);
        }
        if (table == null || arrayList.isEmpty()) {
            return new NoOperation(this.session);
        }
        object = new AlterTableAlterColumn(this.session, schema);
        ((AlterTableAlterColumn)object).setType(12);
        ((AlterTableAlterColumn)object).setTableName(string);
        ((AlterTableAlterColumn)object).setIfTableExists(bl);
        ((AlterTableAlterColumn)object).setColumnsToRemove(arrayList);
        return object;
    }

    private Prepared parseAlterTableDropCompatibility(Schema schema, String string, boolean bl) {
        if (this.readIfCompat(34, 47)) {
            boolean bl2 = this.readIfExists(false);
            String string2 = this.readIdentifierWithSchema(schema.getName());
            this.checkSchema(schema);
            AlterTableDropConstraint alterTableDropConstraint = new AlterTableDropConstraint(this.session, this.getSchema(), bl2);
            alterTableDropConstraint.setTableName(string);
            alterTableDropConstraint.setIfTableExists(bl);
            alterTableDropConstraint.setConstraintName(string2);
            return alterTableDropConstraint;
        }
        if (this.readIfCompat("INDEX")) {
            boolean bl3 = this.readIfExists(false);
            String string3 = this.readIdentifierWithSchema(schema.getName());
            if (schema.findIndex(this.session, string3) != null) {
                DropIndex dropIndex = new DropIndex(this.session, this.getSchema());
                dropIndex.setIndexName(string3);
                return this.commandIfTableExists(schema, string, bl, dropIndex);
            }
            AlterTableDropConstraint alterTableDropConstraint = new AlterTableDropConstraint(this.session, this.getSchema(), bl3);
            alterTableDropConstraint.setTableName(string);
            alterTableDropConstraint.setIfTableExists(bl);
            alterTableDropConstraint.setConstraintName(string3);
            return alterTableDropConstraint;
        }
        return null;
    }

    private Prepared parseAlterTableRename(Schema schema, String string, boolean bl) {
        if (this.readIf("COLUMN")) {
            String string2 = this.readIdentifier();
            this.read(76);
            AlterTableRenameColumn alterTableRenameColumn = new AlterTableRenameColumn(this.session, schema);
            alterTableRenameColumn.setTableName(string);
            alterTableRenameColumn.setIfTableExists(bl);
            alterTableRenameColumn.setOldColumnName(string2);
            alterTableRenameColumn.setNewColumnName(this.readIdentifier());
            return alterTableRenameColumn;
        }
        if (this.readIf(14)) {
            String string3 = this.readIdentifierWithSchema(schema.getName());
            this.checkSchema(schema);
            this.read(76);
            AlterTableRenameConstraint alterTableRenameConstraint = new AlterTableRenameConstraint(this.session, schema);
            alterTableRenameConstraint.setTableName(string);
            alterTableRenameConstraint.setIfTableExists(bl);
            alterTableRenameConstraint.setConstraintName(string3);
            alterTableRenameConstraint.setNewConstraintName(this.readIdentifier());
            return alterTableRenameConstraint;
        }
        this.read(76);
        String string4 = this.readIdentifierWithSchema(schema.getName());
        this.checkSchema(schema);
        AlterTableRename alterTableRename = new AlterTableRename(this.session, this.getSchema());
        alterTableRename.setTableName(string);
        alterTableRename.setNewTableName(string4);
        alterTableRename.setIfTableExists(bl);
        alterTableRename.setHidden(this.readIf("HIDDEN"));
        return alterTableRename;
    }

    private Prepared parseAlterTableSet(Schema schema, String string, boolean bl) {
        this.read("REFERENTIAL_INTEGRITY");
        int n = 55;
        boolean bl2 = this.readBooleanSetting();
        AlterTableSet alterTableSet = new AlterTableSet(this.session, schema, n, bl2);
        alterTableSet.setTableName(string);
        alterTableSet.setIfTableExists(bl);
        if (this.readIf(13)) {
            alterTableSet.setCheckExisting(true);
        } else if (this.readIf("NOCHECK")) {
            alterTableSet.setCheckExisting(false);
        }
        return alterTableSet;
    }

    private Prepared parseAlterTableCompatibility(Schema schema, String string, boolean bl, Mode mode) {
        if (mode.alterTableExtensionsMySQL) {
            if (this.readIfCompat("AUTO_INCREMENT")) {
                this.readIf(95);
                Expression expression = this.readExpression();
                Table table = this.tableIfTableExists(schema, string, bl);
                if (table == null) {
                    return new NoOperation(this.session);
                }
                Index index = table.findPrimaryKey();
                if (index != null) {
                    for (IndexColumn indexColumn : index.getIndexColumns()) {
                        Column column = indexColumn.column;
                        if (!column.isIdentity()) continue;
                        AlterSequence alterSequence = new AlterSequence(this.session, schema);
                        alterSequence.setColumn(column, null);
                        SequenceOptions sequenceOptions = new SequenceOptions();
                        sequenceOptions.setRestartValue(expression);
                        alterSequence.setOptions(sequenceOptions);
                        return alterSequence;
                    }
                }
                throw DbException.get(42122, "AUTO_INCREMENT PRIMARY KEY");
            }
            if (this.readIfCompat("CHANGE")) {
                this.readIf("COLUMN");
                String string2 = this.readIdentifier();
                String string3 = this.readIdentifier();
                Column column = this.columnIfTableExists(schema, string, string2, bl, false);
                boolean bl2 = column == null ? true : column.isNullable();
                this.parseColumnForTable(string3, bl2);
                AlterTableRenameColumn alterTableRenameColumn = new AlterTableRenameColumn(this.session, schema);
                alterTableRenameColumn.setTableName(string);
                alterTableRenameColumn.setIfTableExists(bl);
                alterTableRenameColumn.setOldColumnName(string2);
                alterTableRenameColumn.setNewColumnName(string3);
                return alterTableRenameColumn;
            }
            if (this.readIfCompat("CONVERT")) {
                this.readIf(76);
                this.readIf("CHARACTER");
                this.readIf(71);
                this.readMySQLCharset();
                if (this.readIf("COLLATE")) {
                    this.readMySQLCharset();
                }
                return new NoOperation(this.session);
            }
        }
        if (mode.alterTableModifyColumn && this.readIfCompat("MODIFY")) {
            AlterTableAlterColumn alterTableAlterColumn;
            this.readIf("COLUMN");
            boolean bl3 = this.readIf(105);
            String string4 = this.readIdentifier();
            NullConstraintType nullConstraintType = this.parseNotNullConstraint();
            switch (nullConstraintType) {
                case NULL_IS_ALLOWED: 
                case NULL_IS_NOT_ALLOWED: {
                    alterTableAlterColumn = new AlterTableAlterColumn(this.session, schema);
                    alterTableAlterColumn.setTableName(string);
                    alterTableAlterColumn.setIfTableExists(bl);
                    Column column = this.columnIfTableExists(schema, string, string4, bl, false);
                    alterTableAlterColumn.setOldColumn(column);
                    if (nullConstraintType == NullConstraintType.NULL_IS_ALLOWED) {
                        alterTableAlterColumn.setType(9);
                        break;
                    }
                    alterTableAlterColumn.setType(8);
                    break;
                }
                case NO_NULL_CONSTRAINT_FOUND: {
                    alterTableAlterColumn = this.parseAlterTableAlterColumnType(schema, string, string4, bl, false, mode.alterTableModifyColumnPreserveNullability);
                    break;
                }
                default: {
                    throw DbException.get(90088, "Internal Error - unhandled case: " + nullConstraintType.name());
                }
            }
            if (bl3) {
                this.read(106);
            }
            return alterTableAlterColumn;
        }
        throw this.getSyntaxError();
    }

    private Table tableIfTableExists(Schema schema, String string, boolean bl) {
        Table table = schema.resolveTableOrView(this.session, string);
        if (table == null && !bl) {
            throw this.getTableOrViewNotFoundDbException(schema.getName(), string);
        }
        return table;
    }

    private Column columnIfTableExists(Schema schema, String string, String string2, boolean bl, boolean bl2) {
        Table table = this.tableIfTableExists(schema, string, bl);
        if (table == null) {
            return null;
        }
        return table.getColumn(string2, bl2);
    }

    private Prepared commandIfTableExists(Schema schema, String string, boolean bl, Prepared prepared) {
        return this.tableIfTableExists(schema, string, bl) == null ? new NoOperation(this.session) : prepared;
    }

    private AlterTableAlterColumn parseAlterTableAlterColumnType(Schema schema, String string, String string2, boolean bl, boolean bl2, boolean bl3) {
        Column column = this.columnIfTableExists(schema, string, string2, bl, bl2);
        Column column2 = this.parseColumnForTable(string2, !bl3 || column == null || column.isNullable());
        AlterTableAlterColumn alterTableAlterColumn = new AlterTableAlterColumn(this.session, schema);
        this.parseAlterColumnUsingIf(alterTableAlterColumn);
        alterTableAlterColumn.setTableName(string);
        alterTableAlterColumn.setIfTableExists(bl);
        alterTableAlterColumn.setType(11);
        alterTableAlterColumn.setOldColumn(column);
        alterTableAlterColumn.setNewColumn(column2);
        return alterTableAlterColumn;
    }

    private AlterTableAlterColumn parseAlterTableAlterColumnDataType(Schema schema, String string, String string2, boolean bl, boolean bl2) {
        Object object;
        Column column = this.columnIfTableExists(schema, string, string2, bl, bl2);
        Column column2 = this.parseColumnWithType(string2);
        if (column != null) {
            String string3;
            Sequence sequence2;
            if (!column.isNullable()) {
                column2.setNullable(false);
            }
            if (!column.getVisible()) {
                column2.setVisible(false);
            }
            if ((object = column.getDefaultExpression()) != null) {
                if (column.isGenerated()) {
                    column2.setGeneratedExpression((Expression)object);
                } else {
                    column2.setDefaultExpression(this.session, (Expression)object);
                }
            }
            if ((object = column.getOnUpdateExpression()) != null) {
                column2.setOnUpdateExpression(this.session, (Expression)object);
            }
            if ((sequence2 = column.getSequence()) != null) {
                column2.setIdentityOptions(new SequenceOptions(sequence2, column2.getType()), column.isGeneratedAlways());
            }
            if ((string3 = column.getComment()) != null) {
                column2.setComment(string3);
            }
        }
        object = new AlterTableAlterColumn(this.session, schema);
        this.parseAlterColumnUsingIf((AlterTableAlterColumn)object);
        ((AlterTableAlterColumn)object).setTableName(string);
        ((AlterTableAlterColumn)object).setIfTableExists(bl);
        ((AlterTableAlterColumn)object).setType(11);
        ((AlterTableAlterColumn)object).setOldColumn(column);
        ((AlterTableAlterColumn)object).setNewColumn(column2);
        return object;
    }

    private AlterTableAlterColumn parseAlterTableAddColumn(String string, Schema schema, boolean bl) {
        this.readIf("COLUMN");
        AlterTableAlterColumn alterTableAlterColumn = new AlterTableAlterColumn(this.session, schema);
        alterTableAlterColumn.setType(7);
        alterTableAlterColumn.setTableName(string);
        alterTableAlterColumn.setIfTableExists(bl);
        if (this.readIf(105)) {
            alterTableAlterColumn.setIfNotExists(false);
            do {
                this.parseTableColumnDefinition(alterTableAlterColumn, schema, string, false);
            } while (this.readIfMore());
        } else {
            boolean bl2 = this.readIfNotExists();
            alterTableAlterColumn.setIfNotExists(bl2);
            this.parseTableColumnDefinition(alterTableAlterColumn, schema, string, false);
            this.parseAlterColumnUsingIf(alterTableAlterColumn);
        }
        if (this.readIf("BEFORE")) {
            alterTableAlterColumn.setAddBefore(this.readIdentifier());
        } else if (this.readIf("AFTER")) {
            alterTableAlterColumn.setAddAfter(this.readIdentifier());
        } else if (this.readIf("FIRST")) {
            alterTableAlterColumn.setAddFirst();
        }
        return alterTableAlterColumn;
    }

    private void parseAlterColumnUsingIf(AlterTableAlterColumn alterTableAlterColumn) {
        if (this.readIf(83)) {
            alterTableAlterColumn.setUsingExpression(this.readExpression());
        }
    }

    private ConstraintActionType parseAction() {
        ConstraintActionType constraintActionType = this.parseCascadeOrRestrict();
        if (constraintActionType != null) {
            return constraintActionType;
        }
        if (this.readIf("NO", "ACTION")) {
            return ConstraintActionType.RESTRICT;
        }
        this.read(71);
        if (this.readIf(58)) {
            return ConstraintActionType.SET_NULL;
        }
        this.read(25);
        return ConstraintActionType.SET_DEFAULT;
    }

    private ConstraintActionType parseCascadeOrRestrict() {
        if (this.readIf("CASCADE")) {
            return ConstraintActionType.CASCADE;
        }
        if (this.readIf("RESTRICT")) {
            return ConstraintActionType.RESTRICT;
        }
        return null;
    }

    private DefineCommand parseTableConstraintIf(String string, Schema schema, boolean bl) {
        AlterTableAddConstraint alterTableAddConstraint;
        String string2 = null;
        String string3 = null;
        boolean bl2 = false;
        if (this.readIf(14)) {
            bl2 = this.readIfNotExists();
            string2 = this.readIdentifierWithSchema(schema.getName());
            this.checkSchema(schema);
            string3 = this.readCommentIf();
        }
        switch (this.currentTokenType) {
            case 63: {
                this.read();
                this.read(47);
                alterTableAddConstraint = new AlterTableAddConstraint(this.session, schema, 6, bl2);
                if (this.readIf("HASH")) {
                    alterTableAddConstraint.setPrimaryKeyHash(true);
                }
                this.read(105);
                alterTableAddConstraint.setIndexColumns(this.parseIndexColumnList());
                if (!this.readIf("INDEX")) break;
                String string4 = this.readIdentifierWithSchema();
                alterTableAddConstraint.setIndex(this.getSchema().findIndex(this.session, string4));
                break;
            }
            case 80: {
                this.read();
                NullsDistinct nullsDistinct = this.readNullsDistinct(this.database.getMode().nullsDistinct);
                boolean bl3 = this.database.getMode().indexDefinitionInCreateTable;
                if (bl3) {
                    if (!this.readIfCompat(47)) {
                        this.readIfCompat("INDEX");
                    }
                    if (!this.isToken(105)) {
                        string2 = this.readIdentifier();
                    }
                }
                this.read(105);
                alterTableAddConstraint = new AlterTableAddConstraint(this.session, schema, 4, bl2);
                alterTableAddConstraint.setNullsDistinct(nullsDistinct);
                if (this.readIf(84, 106)) {
                    alterTableAddConstraint.setIndexColumns(null);
                } else {
                    alterTableAddConstraint.setIndexColumns(this.parseIndexColumnList());
                }
                if (this.readIf("INDEX")) {
                    String string5 = this.readIdentifierWithSchema();
                    alterTableAddConstraint.setIndex(this.getSchema().findIndex(this.session, string5));
                }
                if (!bl3) break;
                this.readIfCompat(83, "BTREE");
                break;
            }
            case 34: {
                this.read();
                this.read(47);
                this.read(105);
                alterTableAddConstraint = new AlterTableAddConstraint(this.session, schema, 5, bl2);
                alterTableAddConstraint.setIndexColumns(this.parseIndexColumnList());
                if (this.readIf("INDEX")) {
                    String string6 = this.readIdentifierWithSchema();
                    alterTableAddConstraint.setIndex(schema.findIndex(this.session, string6));
                }
                this.read("REFERENCES");
                this.parseReferences(alterTableAddConstraint, schema, string);
                break;
            }
            case 13: {
                this.read();
                alterTableAddConstraint = new AlterTableAddConstraint(this.session, schema, 3, bl2);
                alterTableAddConstraint.setCheckExpression(this.readExpression());
                break;
            }
            default: {
                if (string2 == null) {
                    Mode mode = this.database.getMode();
                    if (mode.indexDefinitionInCreateTable) {
                        int n = this.tokenIndex;
                        if (this.readIfCompat(47) || this.readIfCompat("INDEX")) {
                            if (DataType.getTypeByName(this.currentToken, mode) == null) {
                                CreateIndex createIndex = new CreateIndex(this.session, schema);
                                createIndex.setComment(string3);
                                createIndex.setTableName(string);
                                createIndex.setIfTableExists(bl);
                                if (!this.readIf(105)) {
                                    createIndex.setIndexName(this.readIdentifier());
                                    this.read(105);
                                }
                                createIndex.setIndexColumns(this.parseIndexColumnList());
                                if (this.readIf(83)) {
                                    this.read("BTREE");
                                }
                                return createIndex;
                            }
                            this.setTokenIndex(n);
                        }
                    }
                    return null;
                }
                if (this.expectedList != null) {
                    this.addMultipleExpected(63, 80, 34, 13);
                }
                throw this.getSyntaxError();
            }
        }
        if (alterTableAddConstraint.getType() != 6) {
            if (this.readIf("NOCHECK")) {
                alterTableAddConstraint.setCheckExisting(false);
            } else {
                this.readIf(13);
                alterTableAddConstraint.setCheckExisting(true);
            }
        }
        alterTableAddConstraint.setTableName(string);
        alterTableAddConstraint.setIfTableExists(bl);
        alterTableAddConstraint.setConstraintName(string2);
        alterTableAddConstraint.setComment(string3);
        return alterTableAddConstraint;
    }

    private void parseReferences(AlterTableAddConstraint alterTableAddConstraint, Schema schema, String string) {
        String string2;
        if (this.readIf(105)) {
            alterTableAddConstraint.setRefTableName(schema, string);
            alterTableAddConstraint.setRefIndexColumns(this.parseIndexColumnList());
        } else {
            string2 = this.readIdentifierWithSchema(schema.getName());
            alterTableAddConstraint.setRefTableName(this.getSchema(), string2);
            if (this.readIf(105)) {
                alterTableAddConstraint.setRefIndexColumns(this.parseIndexColumnList());
            }
        }
        if (this.readIf("INDEX")) {
            string2 = this.readIdentifierWithSchema();
            alterTableAddConstraint.setRefIndex(this.getSchema().findIndex(this.session, string2));
        }
        while (this.readIf(60)) {
            if (this.readIf("DELETE")) {
                alterTableAddConstraint.setDeleteAction(this.parseAction());
                continue;
            }
            this.read("UPDATE");
            alterTableAddConstraint.setUpdateAction(this.parseAction());
        }
        if (!this.readIf(57, "DEFERRABLE")) {
            this.readIf("DEFERRABLE");
        }
    }

    private CreateLinkedTable parseCreateLinkedTable(boolean bl, boolean bl2, boolean bl3) {
        this.read(75);
        boolean bl4 = this.readIfNotExists();
        String string = this.readIdentifierWithSchema();
        CreateLinkedTable createLinkedTable = new CreateLinkedTable(this.session, this.getSchema());
        createLinkedTable.setTemporary(bl);
        createLinkedTable.setGlobalTemporary(bl2);
        createLinkedTable.setForce(bl3);
        createLinkedTable.setIfNotExists(bl4);
        createLinkedTable.setTableName(string);
        createLinkedTable.setComment(this.readCommentIf());
        this.read(105);
        createLinkedTable.setDriver(this.readString());
        this.read(109);
        createLinkedTable.setUrl(this.readString());
        this.read(109);
        createLinkedTable.setUser(this.readString());
        this.read(109);
        createLinkedTable.setPassword(this.readString());
        this.read(109);
        String string2 = this.readString();
        if (this.readIf(109)) {
            createLinkedTable.setOriginalSchema(string2);
            string2 = this.readString();
        }
        createLinkedTable.setOriginalTable(string2);
        this.read(106);
        if (this.readIf("EMIT", "UPDATES")) {
            createLinkedTable.setEmitUpdates(true);
        } else if (this.readIf("READONLY")) {
            createLinkedTable.setReadOnly(true);
        }
        if (this.readIf("FETCH_SIZE")) {
            createLinkedTable.setFetchSize(this.readNonNegativeInt());
        }
        if (this.readIf("AUTOCOMMIT")) {
            if (this.readIf("ON")) {
                createLinkedTable.setAutoCommit(true);
            } else if (this.readIf("OFF")) {
                createLinkedTable.setAutoCommit(false);
            }
        }
        return createLinkedTable;
    }

    private CreateTable parseCreateTable(boolean bl, boolean bl2, boolean bl3) {
        boolean bl4 = this.readIfNotExists();
        String string = this.readIdentifierWithSchema();
        if (bl && bl2 && this.equalsToken("SESSION", this.schemaName)) {
            this.schemaName = this.session.getCurrentSchemaName();
            bl2 = false;
        }
        Schema schema = this.getSchema();
        CreateTable createTable = new CreateTable(this.session, schema);
        createTable.setPersistIndexes(bl3);
        createTable.setTemporary(bl);
        createTable.setGlobalTemporary(bl2);
        createTable.setIfNotExists(bl4);
        createTable.setTableName(string);
        createTable.setComment(this.readCommentIf());
        if (this.readIf(105) && !this.readIf(106)) {
            do {
                this.parseTableColumnDefinition(createTable, schema, string, true);
            } while (this.readIfMore());
        }
        if (this.database.getMode().mySqlTableOptions) {
            this.parseCreateTableMySQLTableOptions(createTable);
        }
        if (this.readIf("ENGINE")) {
            createTable.setTableEngine(this.readIdentifier());
        }
        if (this.readIf(89)) {
            createTable.setTableEngineParams(this.readTableEngineParams());
        }
        if (bl) {
            if (this.readIf(60, "COMMIT")) {
                if (this.readIf("DROP")) {
                    createTable.setOnCommitDrop();
                } else if (this.readIf("DELETE")) {
                    this.read("ROWS");
                    createTable.setOnCommitTruncate();
                }
            } else if (this.readIf(57)) {
                if (this.readIf("PERSISTENT")) {
                    createTable.setPersistData(false);
                } else {
                    this.read("LOGGED");
                }
            }
            if (this.readIf("TRANSACTIONAL")) {
                createTable.setTransactional(true);
            }
        } else if (!bl3 && this.readIf(57, "PERSISTENT")) {
            createTable.setPersistData(false);
        }
        if (this.readIf("HIDDEN")) {
            createTable.setHidden(true);
        }
        if (this.readIf(7)) {
            this.readIf("SORTED");
            createTable.setQuery(this.parseQuery());
            if (this.readIf(89)) {
                createTable.setWithNoData(this.readIf("NO"));
                this.read("DATA");
            }
        }
        return createTable;
    }

    private void parseTableColumnDefinition(CommandWithColumns commandWithColumns, Schema schema, String string, boolean bl) {
        DefineCommand defineCommand = this.parseTableConstraintIf(string, schema, false);
        if (defineCommand != null) {
            commandWithColumns.addConstraintCommand(defineCommand);
            return;
        }
        String string2 = this.readIdentifier();
        if (bl && (this.currentTokenType == 109 || this.currentTokenType == 106)) {
            commandWithColumns.addColumn(new Column(string2, TypeInfo.TYPE_UNKNOWN));
            return;
        }
        Column column = this.parseColumnForTable(string2, true);
        if (column.hasIdentityOptions() && column.isPrimaryKey()) {
            commandWithColumns.addConstraintCommand(Parser.newPrimaryKeyConstraintCommand(this.session, schema, string, column));
        }
        commandWithColumns.addColumn(column);
        this.readColumnConstraints(commandWithColumns, schema, string, column);
    }

    public static AlterTableAddConstraint newPrimaryKeyConstraintCommand(SessionLocal sessionLocal, Schema schema, String string, Column column) {
        column.setPrimaryKey(false);
        AlterTableAddConstraint alterTableAddConstraint = new AlterTableAddConstraint(sessionLocal, schema, 6, false);
        alterTableAddConstraint.setTableName(string);
        alterTableAddConstraint.setIndexColumns(new IndexColumn[]{new IndexColumn(column.getName())});
        return alterTableAddConstraint;
    }

    private void readColumnConstraints(CommandWithColumns commandWithColumns, Schema schema, String string, Column column) {
        block11: {
            String string2 = column.getComment();
            boolean bl = false;
            boolean bl2 = false;
            Mode mode = this.database.getMode();
            while (true) {
                NullConstraintType nullConstraintType;
                AlterTableAddConstraint alterTableAddConstraint;
                String string3;
                if (this.readIf(14)) {
                    string3 = this.readIdentifier();
                } else {
                    if (string2 == null && (string2 = this.readCommentIf()) != null) {
                        column.setComment(string2);
                        continue;
                    }
                    string3 = null;
                }
                if (!bl && this.readIf(63, 47)) {
                    bl = true;
                    boolean bl3 = this.readIf("HASH");
                    alterTableAddConstraint = new AlterTableAddConstraint(this.session, schema, 6, false);
                    alterTableAddConstraint.setConstraintName(string3);
                    alterTableAddConstraint.setPrimaryKeyHash(bl3);
                    alterTableAddConstraint.setTableName(string);
                    alterTableAddConstraint.setIndexColumns(new IndexColumn[]{new IndexColumn(column.getName())});
                    commandWithColumns.addConstraintCommand(alterTableAddConstraint);
                    continue;
                }
                if (this.readIf(80)) {
                    NullsDistinct nullsDistinct = this.readNullsDistinct(this.database.getMode().nullsDistinct);
                    alterTableAddConstraint = new AlterTableAddConstraint(this.session, schema, 4, false);
                    alterTableAddConstraint.setConstraintName(string3);
                    alterTableAddConstraint.setNullsDistinct(nullsDistinct);
                    alterTableAddConstraint.setIndexColumns(new IndexColumn[]{new IndexColumn(column.getName())});
                    alterTableAddConstraint.setTableName(string);
                    commandWithColumns.addConstraintCommand(alterTableAddConstraint);
                    continue;
                }
                if (!bl2 && (nullConstraintType = this.parseNotNullConstraint()) != NullConstraintType.NO_NULL_CONSTRAINT_FOUND) {
                    bl2 = true;
                    if (nullConstraintType == NullConstraintType.NULL_IS_NOT_ALLOWED) {
                        column.setNullable(false);
                        continue;
                    }
                    if (nullConstraintType != NullConstraintType.NULL_IS_ALLOWED) continue;
                    if (column.isIdentity()) {
                        throw DbException.get(90023, column.getName());
                    }
                    column.setNullable(true);
                    continue;
                }
                if (this.readIf(13)) {
                    AlterTableAddConstraint alterTableAddConstraint2 = new AlterTableAddConstraint(this.session, schema, 3, false);
                    alterTableAddConstraint2.setConstraintName(string3);
                    alterTableAddConstraint2.setTableName(string);
                    alterTableAddConstraint2.setCheckExpression(this.readExpression());
                    commandWithColumns.addConstraintCommand(alterTableAddConstraint2);
                    continue;
                }
                if (this.readIf("REFERENCES")) {
                    AlterTableAddConstraint alterTableAddConstraint3 = new AlterTableAddConstraint(this.session, schema, 5, false);
                    alterTableAddConstraint3.setConstraintName(string3);
                    alterTableAddConstraint3.setIndexColumns(new IndexColumn[]{new IndexColumn(column.getName())});
                    alterTableAddConstraint3.setTableName(string);
                    this.parseReferences(alterTableAddConstraint3, schema, string);
                    commandWithColumns.addConstraintCommand(alterTableAddConstraint3);
                    continue;
                }
                if (string3 != null) break block11;
                if (column.getIdentityOptions() != null || !this.parseCompatibilityIdentity(column, mode)) break;
            }
            return;
        }
        throw this.getSyntaxError();
    }

    private boolean parseCompatibilityIdentity(Column column, Mode mode) {
        if (mode.autoIncrementClause && this.readIfCompat("AUTO_INCREMENT")) {
            this.parseCompatibilityIdentityOptions(column);
            return true;
        }
        if (mode.identityClause && this.readIfCompat("IDENTITY")) {
            this.parseCompatibilityIdentityOptions(column);
            return true;
        }
        return false;
    }

    private void parseCreateTableMySQLTableOptions(CreateTable createTable) {
        boolean bl = false;
        while (true) {
            block20: {
                if (this.readIfCompat("AUTO_INCREMENT")) {
                    this.readIf(95);
                    Expression expression = this.readExpression();
                    AlterTableAddConstraint alterTableAddConstraint = createTable.getPrimaryKey();
                    if (alterTableAddConstraint != null) {
                        for (IndexColumn indexColumn : alterTableAddConstraint.getIndexColumns()) {
                            String string = indexColumn.columnName;
                            for (Column column : createTable.getColumns()) {
                                SequenceOptions sequenceOptions;
                                if (!this.database.equalsIdentifiers(column.getName(), string) || (sequenceOptions = column.getIdentityOptions()) == null) continue;
                                sequenceOptions.setStartValue(expression);
                                break block20;
                            }
                        }
                    }
                    throw DbException.get(42122, "AUTO_INCREMENT PRIMARY KEY");
                }
                if (this.readIfCompat(25)) {
                    if (!this.readIf("CHARACTER", 71)) {
                        this.readIf("CHARSET");
                        this.readIf("COLLATE");
                    }
                    this.readMySQLCharset();
                } else if (this.readIfCompat("CHARACTER")) {
                    this.read(71);
                    this.readMySQLCharset();
                } else if (this.readIfCompat("COLLATE")) {
                    this.readMySQLCharset();
                } else if (this.readIfCompat("CHARSET")) {
                    this.readMySQLCharset();
                } else if (this.readIfCompat("COMMENT")) {
                    this.readIf(95);
                    createTable.setComment(this.readString());
                } else if (this.readIfCompat("ENGINE")) {
                    this.readIf(95);
                    this.readIdentifier();
                } else if (this.readIfCompat("ROW_FORMAT")) {
                    this.readIf(95);
                    this.readIdentifier();
                } else {
                    if (!bl) break;
                    throw this.getSyntaxError();
                }
            }
            bl = this.readIf(109);
        }
    }

    private void readMySQLCharset() {
        this.readIf(95);
        this.readIdentifier();
    }

    private NullConstraintType parseNotNullConstraint(NullConstraintType nullConstraintType) {
        if (nullConstraintType == NullConstraintType.NO_NULL_CONSTRAINT_FOUND) {
            nullConstraintType = this.parseNotNullConstraint();
        }
        return nullConstraintType;
    }

    private NullConstraintType parseNotNullConstraint() {
        NullConstraintType nullConstraintType;
        if (this.readIf(57, 58)) {
            nullConstraintType = NullConstraintType.NULL_IS_NOT_ALLOWED;
        } else if (this.readIfCompat(58)) {
            nullConstraintType = NullConstraintType.NULL_IS_ALLOWED;
        } else {
            return NullConstraintType.NO_NULL_CONSTRAINT_FOUND;
        }
        if (this.database.getMode().getEnum() == Mode.ModeEnum.Oracle) {
            nullConstraintType = this.parseNotNullCompatibility(nullConstraintType);
        }
        return nullConstraintType;
    }

    private NullConstraintType parseNotNullCompatibility(NullConstraintType nullConstraintType) {
        if (this.readIfCompat("ENABLE")) {
            if (!this.readIf("VALIDATE") && this.readIf("NOVALIDATE")) {
                nullConstraintType = NullConstraintType.NULL_IS_ALLOWED;
            }
        } else if (this.readIfCompat("DISABLE")) {
            nullConstraintType = NullConstraintType.NULL_IS_ALLOWED;
            if (!this.readIf("VALIDATE")) {
                this.readIf("NOVALIDATE");
            }
        }
        return nullConstraintType;
    }

    private CreateSynonym parseCreateSynonym(boolean bl) {
        boolean bl2 = this.readIfNotExists();
        String string = this.readIdentifierWithSchema();
        Schema schema = this.getSchema();
        this.read(33);
        String string2 = this.readIdentifierWithSchema();
        Schema schema2 = this.getSchema();
        CreateSynonym createSynonym = new CreateSynonym(this.session, schema);
        createSynonym.setName(string);
        createSynonym.setSynonymFor(string2);
        createSynonym.setSynonymForSchema(schema2);
        createSynonym.setComment(this.readCommentIf());
        createSynonym.setIfNotExists(bl2);
        createSynonym.setOrReplace(bl);
        return createSynonym;
    }

    private static int getCompareType(int n) {
        switch (n) {
            case 95: {
                return 0;
            }
            case 96: {
                return 5;
            }
            case 97: {
                return 3;
            }
            case 98: {
                return 2;
            }
            case 99: {
                return 4;
            }
            case 100: {
                return 1;
            }
            case 107: {
                return 8;
            }
        }
        return -1;
    }

    public void setRightsChecked(boolean bl) {
        this.rightsChecked = bl;
    }

    public Expression parseExpression(String string) {
        this.initialize(string, null, false);
        this.read();
        return this.readExpression();
    }

    public Expression parseDomainConstraintExpression(String string) {
        this.initialize(string, null, false);
        this.read();
        try {
            this.parseDomainConstraint = true;
            Expression expression = this.readExpression();
            return expression;
        }
        finally {
            this.parseDomainConstraint = false;
        }
    }

    public Table parseTableName(String string) {
        this.initialize(string, null, false);
        this.read();
        return this.readTableOrView();
    }

    private static enum NullConstraintType {
        NULL_IS_ALLOWED,
        NULL_IS_NOT_ALLOWED,
        NO_NULL_CONSTRAINT_FOUND;

    }
}

