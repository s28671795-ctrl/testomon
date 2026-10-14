/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.function.Supplier;
import org.jooq.Block;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.CreateProcedureAsStep;
import org.jooq.CreateProcedureCharacteristicsStep;
import org.jooq.CreateProcedureParametersStep;
import org.jooq.DDLQuery;
import org.jooq.Function5;
import org.jooq.LanguageContext;
import org.jooq.Name;
import org.jooq.Parameter;
import org.jooq.Pro;
import org.jooq.QueryPart;
import org.jooq.Replacer;
import org.jooq.SQLDialect;
import org.jooq.Statement;
import org.jooq.Traverser;
import org.jooq.impl.AbstractDDLQuery;
import org.jooq.impl.BlockImpl;
import org.jooq.impl.CustomStatement;
import org.jooq.impl.DSL;
import org.jooq.impl.Keywords;
import org.jooq.impl.Names;
import org.jooq.impl.ParameterList;
import org.jooq.impl.QOM;
import org.jooq.impl.QualifiedImpl;
import org.jooq.impl.QueryPartList;
import org.jooq.impl.QueryPartListView;
import org.jooq.impl.Tools;

@Pro
final class CreateProcedureImpl
extends AbstractDDLQuery
implements QOM.CreateProcedure,
CreateProcedureParametersStep,
CreateProcedureCharacteristicsStep,
CreateProcedureAsStep {
    final Name procedure;
    final boolean orReplace;
    QueryPartListView<? extends Parameter<?>> parameters;
    QOM.SQLDataAccess sqlDataAccess;
    Statement body;
    static final Set<SQLDialect> NO_SUPPORT_OR_REPLACE = SQLDialect.supportedBy(SQLDialect.MYSQL);
    static final Set<SQLDialect> SUPPORT_OR_ALTER = SQLDialect.supportedBy(SQLDialect.FIREBIRD, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER);
    static final Set<SQLDialect> NO_SUPPORT_PARAMS_EMPTY = SQLDialect.supportedBy(SQLDialect.ORACLE);
    static final Set<SQLDialect> NO_SUPPORT_SQL_DATA_ACCESS = SQLDialect.supportedBy(SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.INFORMIX, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB);
    static final Set<SQLDialect> NO_SUPPORT_PROC_PARAM_PARENS = SQLDialect.supportedBy(SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER);
    static final Set<SQLDialect> REQUIRE_AS = SQLDialect.supportedBy(SQLDialect.AURORA_POSTGRES, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.VERTICA, SQLDialect.YUGABYTEDB);
    static final Set<SQLDialect> REQUIRE_LANGUAGE = SQLDialect.supportedBy(SQLDialect.AURORA_POSTGRES, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB);
    static final Set<SQLDialect> REQUIRE_BODY_AS_STRING = SQLDialect.supportedBy(SQLDialect.AURORA_POSTGRES, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB);
    static final Set<SQLDialect> REQUIRES_RETURN_TYPE_LPS = SQLDialect.supportedBy(SQLDialect.AURORA_MYSQL, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.SQLSERVER);
    static final Set<SQLDialect> REQUIRES_PARAM_TYPE_LPS = SQLDialect.supportedBy(SQLDialect.AURORA_MYSQL, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.FIREBIRD, SQLDialect.HANA, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.SQLSERVER);

    CreateProcedureImpl(Configuration configuration, Name procedure, boolean orReplace) {
        this(configuration, procedure, orReplace, null, null, null);
    }

    CreateProcedureImpl(Configuration configuration, Name procedure, boolean orReplace, Collection<? extends Parameter<?>> parameters, QOM.SQLDataAccess sqlDataAccess, Statement body2) {
        super(configuration);
        this.procedure = procedure;
        this.orReplace = orReplace;
        this.parameters = new QueryPartList((Iterable<Parameter<?>>)parameters);
        this.sqlDataAccess = sqlDataAccess;
        this.body = body2;
    }

    @Override
    public final CreateProcedureImpl parameters(Parameter<?> ... parameters) {
        return this.parameters(Arrays.asList(parameters));
    }

    @Override
    public final CreateProcedureImpl parameters(Collection<? extends Parameter<?>> parameters) {
        this.parameters = new QueryPartList((Iterable<Parameter<?>>)parameters);
        return this;
    }

    @Override
    public final CreateProcedureImpl noSQL() {
        this.sqlDataAccess = QOM.SQLDataAccess.NO_SQL;
        return this;
    }

    @Override
    public final CreateProcedureImpl containsSQL() {
        this.sqlDataAccess = QOM.SQLDataAccess.CONTAINS_SQL;
        return this;
    }

    @Override
    public final CreateProcedureImpl readsSQLData() {
        this.sqlDataAccess = QOM.SQLDataAccess.READS_SQL_DATA;
        return this;
    }

    @Override
    public final CreateProcedureImpl modifiesSQLData() {
        this.sqlDataAccess = QOM.SQLDataAccess.MODIFIES_SQL_DATA;
        return this;
    }

    @Override
    public final CreateProcedureImpl as(Statement ... body2) {
        return this.as((Statement)DSL.statements(body2));
    }

    @Override
    public final CreateProcedureImpl as(Collection<? extends Statement> body2) {
        return this.as((Statement)DSL.statements(body2));
    }

    @Override
    public final CreateProcedureImpl as(Statement body2) {
        this.body = body2;
        return this;
    }

    @Override
    public final void accept(Context<?> ctx) {
        ctx.visit(Keywords.K_CREATE).sql(' ');
        CreateProcedureImpl.acceptOrReplace(ctx, this.orReplace, NO_SUPPORT_OR_REPLACE.contains((Object)ctx.dialect()), () -> ctx.dsl().dropProcedureIfExists(this.procedure));
        ctx.visit(Keywords.K_PROCEDURE).sql(' ').visit(new QualifiedImpl(this.procedure));
        CreateProcedureImpl.acceptParameters(ctx, this.parameters, true);
        CreateProcedureImpl.acceptLanguage(ctx);
        CreateProcedureImpl.acceptSQLDataAccess(ctx, this.sqlDataAccess);
        ctx.languageContext(LanguageContext.PROCEDURE, c -> CreateProcedureImpl.acceptBody(c, REQUIRE_AS.contains((Object)c.dialect()), this.procedure, this.body));
        if (ctx.family() == SQLDialect.INFORMIX) {
            ctx.formatSeparator().visit(Keywords.K_END).sql(' ').visit(Keywords.K_PROCEDURE);
        }
    }

    static final void acceptOrReplace(Context<?> ctx, boolean orReplace, boolean support, Supplier<? extends DDLQuery> dropIfExists) {
        if (orReplace) {
            if (support) {
                Tools.prependSQL(ctx, dropIfExists.get());
            } else if (SUPPORT_OR_ALTER.contains((Object)ctx.dialect())) {
                ctx.visit(Keywords.K_OR).sql(' ').visit(Keywords.K_ALTER).sql(' ');
            } else {
                ctx.visit(Keywords.K_OR).sql(' ').visit(Keywords.K_REPLACE).sql(' ');
            }
        }
    }

    static final void acceptBody(Context<?> ctx, boolean requireAs, Name name, Statement body2) {
        BlockImpl s2;
        boolean hana;
        ctx.data(Tools.ExtendedDataKey.DATA_TOP_SCOPE_ROUTINE_NAME, name);
        if (requireAs) {
            if (ctx.family() == SQLDialect.EXASOL) {
                ctx.formatSeparator().visit(Keywords.K_IS);
            } else {
                ctx.formatSeparator().visit(Keywords.K_AS);
            }
        }
        boolean informix = ctx.family() == SQLDialect.INFORMIX;
        boolean bl = hana = ctx.family() == SQLDialect.HANA;
        BlockImpl s1 = (BlockImpl)(body2 instanceof BlockImpl && ((BlockImpl)body2).alwaysWrapInBeginEnd ? body2 : (informix || BlockImpl.NO_SUPPORT_BLOCK_SCOPE.contains((Object)ctx.dialect()) ? DSL.statements(body2) : DSL.begin(body2)));
        Block block = s2 = hana ? DSL.begin(DSL.statement("declare exit handler for sql_error_code 19999 begin end;"), DSL.statements(s1.statements)) : s1;
        if (informix) {
            ctx.formatIndentStart();
        }
        if (REQUIRE_BODY_AS_STRING.contains((Object)ctx.dialect())) {
            BlockImpl.bodyAsString(ctx.formatSeparator(), null, c -> c.visit(s2));
        } else if (BlockImpl.NO_SUPPORT_BLOCK_SCOPE.contains((Object)ctx.dialect())) {
            ctx.formatSeparator().visit(DSL.begin(CustomStatement.of(x -> Tools.increment(ctx.data(), Tools.SimpleDataKey.DATA_BLOCK_NESTING, () -> BlockImpl.scopeDeclarations(ctx, c -> c.visit(s2))))));
        } else if (BlockImpl.NO_SUPPORT_NESTED_DECLARATIONS.contains((Object)ctx.dialect()) || !s2.alwaysWrapInBeginEnd) {
            Tools.increment(ctx.data(), Tools.SimpleDataKey.DATA_BLOCK_NESTING, () -> BlockImpl.scopeDeclarations(ctx.formatIndentStart(), c -> c.formatIndentEnd().formatSeparator().visit(s2)));
        } else {
            Tools.increment(ctx.data(), Tools.SimpleDataKey.DATA_BLOCK_NESTING, () -> ctx.formatSeparator().visit(s2));
        }
        if (informix) {
            ctx.formatIndentEnd();
        }
    }

    static final void acceptParameters(Context<?> ctx, Collection<? extends Parameter<?>> parameters, boolean procedure) {
        boolean parens;
        boolean bl = parens = !procedure || !NO_SUPPORT_PROC_PARAM_PARENS.contains((Object)ctx.dialect());
        if (Tools.isEmpty(parameters)) {
            if (parens && !NO_SUPPORT_PARAMS_EMPTY.contains((Object)ctx.dialect())) {
                ctx.sql("()");
            }
        } else {
            if (parens) {
                ctx.sql('(');
            } else {
                ctx.formatSeparator();
            }
            ctx.declareParameters(true, c -> c.visit(new ParameterList(parameters)));
            if (parens) {
                ctx.sql(')');
            } else {
                ctx.formatSeparator();
            }
        }
        ctx.formatSeparator();
    }

    static final void acceptSQLDataAccess(Context<?> ctx, QOM.SQLDataAccess access) {
        if (access != null && !NO_SUPPORT_SQL_DATA_ACCESS.contains((Object)ctx.dialect())) {
            switch (access) {
                case CONTAINS_SQL: {
                    ctx.visit(Keywords.K_CONTAINS).sql(' ').visit(Keywords.K_SQL).formatSeparator();
                    break;
                }
                case MODIFIES_SQL_DATA: {
                    ctx.visit(Keywords.K_MODIFIES).sql(' ').visit(Keywords.K_SQL).sql(' ').visit(Keywords.K_DATA).formatSeparator();
                    break;
                }
                case NO_SQL: {
                    ctx.visit(Keywords.K_NO).sql(' ').visit(Keywords.K_SQL).formatSeparator();
                    break;
                }
                case READS_SQL_DATA: {
                    ctx.visit(Keywords.K_READS).sql(' ').visit(Keywords.K_SQL).sql(' ').visit(Keywords.K_DATA).formatSeparator();
                }
            }
        }
    }

    static final void acceptLanguage(Context<?> ctx) {
        if (REQUIRE_LANGUAGE.contains((Object)ctx.dialect())) {
            ctx.visit(Keywords.K_LANGUAGE).sql(' ').visit(Names.N_PLPGSQL).formatSeparator();
        }
    }

    @Override
    public final Name $procedure() {
        return this.procedure;
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
    public final QOM.SQLDataAccess $sqlDataAccess() {
        return this.sqlDataAccess;
    }

    @Override
    public final Statement $body() {
        return this.body;
    }

    @Override
    public final QOM.CreateProcedure $procedure(Name newValue) {
        return this.$constructor().apply(newValue, this.$orReplace(), this.$parameters(), this.$sqlDataAccess(), this.$body());
    }

    @Override
    public final QOM.CreateProcedure $orReplace(boolean newValue) {
        return this.$constructor().apply(this.$procedure(), newValue, this.$parameters(), this.$sqlDataAccess(), this.$body());
    }

    @Override
    public final QOM.CreateProcedure $parameters(Collection<? extends Parameter<?>> newValue) {
        return this.$constructor().apply(this.$procedure(), this.$orReplace(), newValue, this.$sqlDataAccess(), this.$body());
    }

    @Override
    public final QOM.CreateProcedure $sqlDataAccess(QOM.SQLDataAccess newValue) {
        return this.$constructor().apply(this.$procedure(), this.$orReplace(), this.$parameters(), newValue, this.$body());
    }

    @Override
    public final QOM.CreateProcedure $body(Statement newValue) {
        return this.$constructor().apply(this.$procedure(), this.$orReplace(), this.$parameters(), this.$sqlDataAccess(), newValue);
    }

    public final Function5<? super Name, ? super Boolean, ? super Collection<? extends Parameter<?>>, ? super QOM.SQLDataAccess, ? super Statement, ? extends QOM.CreateProcedure> $constructor() {
        return (a1, a2, a3, a4, a5) -> new CreateProcedureImpl(this.configuration(), (Name)a1, (boolean)a2, (Collection<? extends Parameter<?>>)a3, (QOM.SQLDataAccess)((Object)a4), (Statement)a5);
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.$procedure(), this.$orReplace(), this.$parameters(), this.$sqlDataAccess(), this.$body(), (a1, a2, a3, a4, a5) -> this.$constructor().apply((Name)a1, (Boolean)a2, (Collection<Parameter<?>>)a3, (QOM.SQLDataAccess)((Object)a4), (Statement)a5), replacer);
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        return QOM.traverse(traverser, this, this.$procedure(), this.$parameters(), this.$body());
    }
}

