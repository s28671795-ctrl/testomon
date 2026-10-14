/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.CreateFunctionAsStep;
import org.jooq.CreateFunctionCharacteristicsStep;
import org.jooq.CreateFunctionParametersStep;
import org.jooq.CreateFunctionReturnsStep;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.Function9;
import org.jooq.LanguageContext;
import org.jooq.Name;
import org.jooq.Parameter;
import org.jooq.Pro;
import org.jooq.QueryPart;
import org.jooq.Replacer;
import org.jooq.SQLDialect;
import org.jooq.Select;
import org.jooq.Statement;
import org.jooq.Traverser;
import org.jooq.impl.AbstractDDLQuery;
import org.jooq.impl.CreateProcedureImpl;
import org.jooq.impl.DSL;
import org.jooq.impl.Keywords;
import org.jooq.impl.QOM;
import org.jooq.impl.QualifiedImpl;
import org.jooq.impl.QueryPartList;
import org.jooq.impl.QueryPartListView;
import org.jooq.impl.Tools;

@Pro
final class CreateFunctionImpl
extends AbstractDDLQuery
implements QOM.CreateFunction,
CreateFunctionParametersStep,
CreateFunctionReturnsStep,
CreateFunctionCharacteristicsStep,
CreateFunctionAsStep {
    final Name function;
    final boolean orReplace;
    QueryPartListView<? extends Parameter<?>> parameters;
    DataType<?> returns;
    QueryPartListView<? extends Field<?>> returnsTable;
    QOM.SQLDataAccess sqlDataAccess;
    QOM.NullCallClause returnsNullOnNullInput;
    QOM.Deterministic deterministic;
    Statement body;
    static final Set<SQLDialect> NO_SUPPORT_OR_REPLACE = SQLDialect.supportedBy(SQLDialect.AURORA_MYSQL, SQLDialect.MYSQL);
    static final Set<SQLDialect> NO_SUPPORT_RETURNS_NULL = SQLDialect.supportedBy(SQLDialect.AURORA_MYSQL, SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.VERTICA);
    static final Set<SQLDialect> REQUIRES_RETURN_NAME = SQLDialect.supportedBy(SQLDialect.HANA);
    static final Set<SQLDialect> REQUIRES_RETURN_LAST_STATEMENT = SQLDialect.supportedBy(SQLDialect.EXASOL);
    static final Set<SQLDialect> REQUIRES_WITH_CHARACTERISTICS = SQLDialect.supportedBy(SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER);
    static final Set<SQLDialect> NO_SUPPORT_DETERMINISTIC = SQLDialect.supportedBy(SQLDialect.EXASOL, SQLDialect.INFORMIX, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.VERTICA);
    static final Set<SQLDialect> NO_SUPPORT_NOT_DETERMINISTIC = SQLDialect.supportedBy(SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.INFORMIX, SQLDialect.ORACLE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.VERTICA);
    static final Set<SQLDialect> REQUIRES_RETURN_FOR_SELECT = SQLDialect.supportedBy(SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB);
    static final Set<SQLDialect> DECLARE_WITH_RETURN = SQLDialect.supportedBy(SQLDialect.ORACLE, SQLDialect.VERTICA);

    CreateFunctionImpl(Configuration configuration, Name function, boolean orReplace) {
        this(configuration, function, orReplace, null, null, null, null, null, null, null);
    }

    CreateFunctionImpl(Configuration configuration, Name function, boolean orReplace, Collection<? extends Parameter<?>> parameters, DataType<?> returns, Collection<? extends Field<?>> returnsTable, QOM.SQLDataAccess sqlDataAccess, QOM.NullCallClause returnsNullOnNullInput, QOM.Deterministic deterministic, Statement body2) {
        super(configuration);
        this.function = function;
        this.orReplace = orReplace;
        this.parameters = new QueryPartList((Iterable<Parameter<?>>)parameters);
        this.returns = returns;
        this.returnsTable = new QueryPartList((Iterable<Field<?>>)returnsTable);
        this.sqlDataAccess = sqlDataAccess;
        this.returnsNullOnNullInput = returnsNullOnNullInput;
        this.deterministic = deterministic;
        this.body = body2;
    }

    @Override
    public final CreateFunctionImpl parameters(Parameter<?> ... parameters) {
        return this.parameters(Arrays.asList(parameters));
    }

    @Override
    public final CreateFunctionImpl parameters(Collection<? extends Parameter<?>> parameters) {
        this.parameters = new QueryPartList((Iterable<Parameter<?>>)parameters);
        return this;
    }

    @Override
    public final CreateFunctionImpl returns(DataType<?> returns) {
        this.returns = returns;
        return this;
    }

    @Override
    public final CreateFunctionImpl returnsTable(Field<?> ... returnsTable) {
        return this.returnsTable(Arrays.asList(returnsTable));
    }

    @Override
    public final CreateFunctionImpl returnsTable(Collection<? extends Field<?>> returnsTable) {
        this.returnsTable = new QueryPartList((Iterable<Field<?>>)returnsTable);
        return this;
    }

    @Override
    public final CreateFunctionImpl noSQL() {
        this.sqlDataAccess = QOM.SQLDataAccess.NO_SQL;
        return this;
    }

    @Override
    public final CreateFunctionImpl containsSQL() {
        this.sqlDataAccess = QOM.SQLDataAccess.CONTAINS_SQL;
        return this;
    }

    @Override
    public final CreateFunctionImpl readsSQLData() {
        this.sqlDataAccess = QOM.SQLDataAccess.READS_SQL_DATA;
        return this;
    }

    @Override
    public final CreateFunctionImpl modifiesSQLData() {
        this.sqlDataAccess = QOM.SQLDataAccess.MODIFIES_SQL_DATA;
        return this;
    }

    @Override
    public final CreateFunctionImpl returnsNullOnNullInput() {
        this.returnsNullOnNullInput = QOM.NullCallClause.RETURNS_NULL_ON_NULL_INPUT;
        return this;
    }

    @Override
    public final CreateFunctionImpl calledOnNullInput() {
        this.returnsNullOnNullInput = QOM.NullCallClause.CALLED_ON_NULL_INPUT;
        return this;
    }

    @Override
    public final CreateFunctionImpl deterministic() {
        this.deterministic = QOM.Deterministic.DETERMINISTIC;
        return this;
    }

    @Override
    public final CreateFunctionImpl notDeterministic() {
        this.deterministic = QOM.Deterministic.NOT_DETERMINISTIC;
        return this;
    }

    @Override
    public final CreateFunctionImpl as(Statement ... body2) {
        return this.as((Statement)DSL.statements(body2));
    }

    @Override
    public final CreateFunctionImpl as(Collection<? extends Statement> body2) {
        return this.as((Statement)DSL.statements(body2));
    }

    @Override
    public final CreateFunctionImpl as(Statement body2) {
        this.body = body2;
        return this;
    }

    @Override
    public final void accept(Context<?> ctx) {
        ctx.visit(Keywords.K_CREATE).sql(' ');
        CreateProcedureImpl.acceptOrReplace(ctx, this.orReplace, NO_SUPPORT_OR_REPLACE.contains((Object)ctx.dialect()), () -> ctx.dsl().dropFunctionIfExists(this.function));
        ctx.visit(Keywords.K_FUNCTION).sql(' ').visit(new QualifiedImpl(this.function));
        CreateProcedureImpl.acceptParameters(ctx, this.parameters, false);
        ctx.visit(DECLARE_WITH_RETURN.contains((Object)ctx.dialect()) ? Keywords.K_RETURN : Keywords.K_RETURNS).sql(' ');
        if (REQUIRES_RETURN_NAME.contains((Object)ctx.family())) {
            ctx.visit(DSL.unquotedName("result")).sql(' ');
        }
        if (CreateProcedureImpl.REQUIRES_RETURN_TYPE_LPS.contains((Object)ctx.family())) {
            Tools.toSQLDDLTypeDeclaration(ctx, this.returns);
        } else {
            ctx.sql(this.returns.getTypeName(ctx.configuration()));
        }
        ctx.formatSeparator();
        CreateProcedureImpl.acceptLanguage(ctx);
        this.acceptDeterministic(ctx);
        CreateProcedureImpl.acceptSQLDataAccess(ctx, this.sqlDataAccess);
        Statement s1 = this.body;
        if (this.returnsNullOnNullInput != null) {
            if (REQUIRES_WITH_CHARACTERISTICS.contains((Object)ctx.dialect())) {
                ctx.visit(Keywords.K_WITH).sql(' ');
            }
            if (this.returnsNullOnNullInput == QOM.NullCallClause.RETURNS_NULL_ON_NULL_INPUT) {
                if (!NO_SUPPORT_RETURNS_NULL.contains((Object)ctx.dialect())) {
                    ctx.visit(this.returnsNullOnNullInput.keyword).formatSeparator();
                } else {
                    s1 = REQUIRES_RETURN_LAST_STATEMENT.contains((Object)ctx.dialect()) ? DSL.statements(DSL.if_(DSL.not(DSL.row(this.parameters).isNotNull())).then(DSL.return_(DSL.inline(null, this.returns))), this.body) : DSL.if_(DSL.not(DSL.row(this.parameters).isNotNull())).then(DSL.return_(DSL.inline(null, this.returns))).else_(this.body);
                }
            } else if (!NO_SUPPORT_RETURNS_NULL.contains((Object)ctx.dialect())) {
                ctx.visit(this.returnsNullOnNullInput.keyword).formatSeparator();
            }
        }
        if (s1 instanceof Select && REQUIRES_RETURN_FOR_SELECT.contains((Object)ctx.dialect())) {
            s1 = DSL.return_(s1);
        }
        Statement s2 = s1;
        ctx.languageContext(LanguageContext.FUNCTION, c -> CreateProcedureImpl.acceptBody(c, CreateProcedureImpl.REQUIRE_AS.contains((Object)ctx.dialect()), this.function, s2));
        if (ctx.family() == SQLDialect.INFORMIX) {
            ctx.formatSeparator().visit(Keywords.K_END).sql(' ').visit(Keywords.K_FUNCTION);
        }
    }

    private final void acceptDeterministic(Context<?> ctx) {
        if (this.deterministic == null) {
            return;
        }
        if (ctx.family() == SQLDialect.POSTGRES || ctx.family() == SQLDialect.YUGABYTEDB) {
            ctx.visit(this.deterministic == QOM.Deterministic.DETERMINISTIC ? Keywords.K_IMMUTABLE : Keywords.K_VOLATILE).formatSeparator();
        } else if (this.deterministic == QOM.Deterministic.DETERMINISTIC) {
            if (!NO_SUPPORT_DETERMINISTIC.contains((Object)ctx.dialect())) {
                ctx.visit(this.deterministic.keyword).formatSeparator();
            }
        } else if (!NO_SUPPORT_NOT_DETERMINISTIC.contains((Object)ctx.dialect())) {
            ctx.visit(this.deterministic.keyword).formatSeparator();
        }
    }

    @Override
    public final Name $function() {
        return this.function;
    }

    @Override
    public final boolean $orReplace() {
        return this.orReplace;
    }

    @Override
    public final QOM.UnmodifiableList<? extends Parameter<?>> $parameters() {
        return QOM.unmodifiable(this.parameters);
    }

    @Override
    public final DataType<?> $returns() {
        return this.returns;
    }

    @Override
    public final QOM.UnmodifiableList<? extends Field<?>> $returnsTable() {
        return QOM.unmodifiable(this.returnsTable);
    }

    @Override
    public final QOM.SQLDataAccess $sqlDataAccess() {
        return this.sqlDataAccess;
    }

    @Override
    public final QOM.NullCallClause $returnsNullOnNullInput() {
        return this.returnsNullOnNullInput;
    }

    @Override
    public final QOM.Deterministic $deterministic() {
        return this.deterministic;
    }

    @Override
    public final Statement $body() {
        return this.body;
    }

    @Override
    public final QOM.CreateFunction $function(Name newValue) {
        return this.$constructor().apply(newValue, this.$orReplace(), this.$parameters(), this.$returns(), this.$returnsTable(), this.$sqlDataAccess(), this.$returnsNullOnNullInput(), this.$deterministic(), this.$body());
    }

    @Override
    public final QOM.CreateFunction $orReplace(boolean newValue) {
        return this.$constructor().apply(this.$function(), newValue, this.$parameters(), this.$returns(), this.$returnsTable(), this.$sqlDataAccess(), this.$returnsNullOnNullInput(), this.$deterministic(), this.$body());
    }

    @Override
    public final QOM.CreateFunction $parameters(Collection<? extends Parameter<?>> newValue) {
        return this.$constructor().apply(this.$function(), this.$orReplace(), newValue, this.$returns(), this.$returnsTable(), this.$sqlDataAccess(), this.$returnsNullOnNullInput(), this.$deterministic(), this.$body());
    }

    @Override
    public final QOM.CreateFunction $returns(DataType<?> newValue) {
        return this.$constructor().apply(this.$function(), this.$orReplace(), this.$parameters(), newValue, this.$returnsTable(), this.$sqlDataAccess(), this.$returnsNullOnNullInput(), this.$deterministic(), this.$body());
    }

    @Override
    public final QOM.CreateFunction $returnsTable(Collection<? extends Field<?>> newValue) {
        return this.$constructor().apply(this.$function(), this.$orReplace(), this.$parameters(), this.$returns(), newValue, this.$sqlDataAccess(), this.$returnsNullOnNullInput(), this.$deterministic(), this.$body());
    }

    @Override
    public final QOM.CreateFunction $sqlDataAccess(QOM.SQLDataAccess newValue) {
        return this.$constructor().apply(this.$function(), this.$orReplace(), this.$parameters(), this.$returns(), this.$returnsTable(), newValue, this.$returnsNullOnNullInput(), this.$deterministic(), this.$body());
    }

    @Override
    public final QOM.CreateFunction $returnsNullOnNullInput(QOM.NullCallClause newValue) {
        return this.$constructor().apply(this.$function(), this.$orReplace(), this.$parameters(), this.$returns(), this.$returnsTable(), this.$sqlDataAccess(), newValue, this.$deterministic(), this.$body());
    }

    @Override
    public final QOM.CreateFunction $deterministic(QOM.Deterministic newValue) {
        return this.$constructor().apply(this.$function(), this.$orReplace(), this.$parameters(), this.$returns(), this.$returnsTable(), this.$sqlDataAccess(), this.$returnsNullOnNullInput(), newValue, this.$body());
    }

    @Override
    public final QOM.CreateFunction $body(Statement newValue) {
        return this.$constructor().apply(this.$function(), this.$orReplace(), this.$parameters(), this.$returns(), this.$returnsTable(), this.$sqlDataAccess(), this.$returnsNullOnNullInput(), this.$deterministic(), newValue);
    }

    public final Function9<? super Name, ? super Boolean, ? super Collection<? extends Parameter<?>>, ? super DataType<?>, ? super Collection<? extends Field<?>>, ? super QOM.SQLDataAccess, ? super QOM.NullCallClause, ? super QOM.Deterministic, ? super Statement, ? extends QOM.CreateFunction> $constructor() {
        return (a1, a2, a3, a4, a5, a6, a7, a8, a9) -> new CreateFunctionImpl(this.configuration(), (Name)a1, (boolean)a2, (Collection<? extends Parameter<?>>)a3, (DataType<?>)a4, (Collection<? extends Field<?>>)a5, (QOM.SQLDataAccess)((Object)a6), (QOM.NullCallClause)((Object)a7), (QOM.Deterministic)((Object)a8), (Statement)a9);
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.$function(), this.$orReplace(), this.$parameters(), this.$returns(), this.$returnsTable(), this.$sqlDataAccess(), this.$returnsNullOnNullInput(), this.$deterministic(), this.$body(), (a1, a2, a3, a4, a5, a6, a7, a8, a9) -> this.$constructor().apply((Name)a1, (Boolean)a2, (Collection<Parameter<?>>)a3, (DataType<?>)a4, (Collection<Field<?>>)a5, (QOM.SQLDataAccess)((Object)a6), (QOM.NullCallClause)((Object)a7), (QOM.Deterministic)((Object)a8), (Statement)a9), replacer);
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        return QOM.traverse(traverser, this, this.$function(), this.$parameters(), this.$returns(), this.$returnsTable(), this.$body());
    }
}

