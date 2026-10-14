/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import org.jooq.Clause;
import org.jooq.Condition;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Name;
import org.jooq.Named;
import org.jooq.Pro;
import org.jooq.QueryPart;
import org.jooq.SQLDialect;
import org.jooq.Select;
import org.jooq.Table;
import org.jooq.TableLike;
import org.jooq.conf.RenderOptionalKeyword;
import org.jooq.impl.AbstractQueryPart;
import org.jooq.impl.AliasedSelect;
import org.jooq.impl.ArrayTable;
import org.jooq.impl.CommonTableExpressionImpl;
import org.jooq.impl.DSL;
import org.jooq.impl.DerivedTable;
import org.jooq.impl.JoinTable;
import org.jooq.impl.Keywords;
import org.jooq.impl.NoAutoAlias;
import org.jooq.impl.QOM;
import org.jooq.impl.QueryPartListView;
import org.jooq.impl.SelectFieldList;
import org.jooq.impl.SelectQueryImpl;
import org.jooq.impl.SimpleCheckQueryPart;
import org.jooq.impl.TableImpl;
import org.jooq.impl.Tools;
import org.jooq.impl.Values;

final class Alias<Q extends QueryPart>
extends AbstractQueryPart
implements QOM.UEmpty,
SimpleCheckQueryPart {
    private static final Clause[] CLAUSES_TABLE_REFERENCE = new Clause[]{Clause.TABLE, Clause.TABLE_REFERENCE};
    private static final Clause[] CLAUSES_TABLE_ALIAS = new Clause[]{Clause.TABLE, Clause.TABLE_ALIAS};
    private static final Clause[] CLAUSES_FIELD_REFERENCE = new Clause[]{Clause.FIELD, Clause.FIELD_REFERENCE};
    private static final Clause[] CLAUSES_FIELD_ALIAS = new Clause[]{Clause.FIELD, Clause.FIELD_ALIAS};
    private static final Set<SQLDialect> NO_SUPPORT_ALIASED_JOIN_TABLES = SQLDialect.supportedBy(SQLDialect.AURORA_MYSQL, SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.FIREBIRD, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.SNOWFLAKE, SQLDialect.SQLITE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER);
    private static final Set<SQLDialect> SUPPORT_AS_REQUIRED = SQLDialect.supportedBy(SQLDialect.ACCESS, SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.BIGQUERY, SQLDialect.COCKROACHDB, SQLDialect.DERBY, SQLDialect.HSQLDB, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.SQLITE, SQLDialect.YUGABYTEDB);
    private static final Set<SQLDialect> SUPPORT_DERIVED_COLUMN_NAMES_SPECIAL1 = SQLDialect.supportedBy(SQLDialect.ASE, SQLDialect.CUBRID, SQLDialect.FIREBIRD, SQLDialect.INFORMIX, SQLDialect.INGRES, SQLDialect.MYSQL_8_0, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.SYBASE, SQLDialect.TERADATA);
    private static final Set<SQLDialect> SUPPORT_DERIVED_COLUMN_NAMES_SPECIAL2 = SQLDialect.supportedUntil(SQLDialect.ACCESS, SQLDialect.AURORA_MYSQL, SQLDialect.BIGQUERY, SQLDialect.HANA, SQLDialect.IGNITE, SQLDialect.INGRES, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL_8_0, SQLDialect.ORACLE, SQLDialect.SQLITE);
    @Pro
    private static final Set<SQLDialect> SUPPORT_DERIVED_COLUMN_NAMES_SPECIAL3 = SQLDialect.supportedUntil(SQLDialect.H2_1_4_200);
    final Q wrapped;
    final Q wrapping;
    final Name alias;
    final Name[] fieldAliases;
    final Predicate<Context<?>> wrapInParentheses;

    Alias(Q wrapped, Q wrapping, Name alias) {
        this(wrapped, wrapping, alias, null, c -> false);
    }

    Alias(Q wrapped, Q wrapping, Name alias, Name[] fieldAliases, Predicate<Context<?>> wrapInParentheses) {
        this.wrapped = wrapped;
        this.wrapping = wrapping;
        this.alias = alias;
        this.fieldAliases = fieldAliases;
        this.wrapInParentheses = wrapInParentheses;
    }

    final Q wrapped() {
        return this.wrapped;
    }

    @Override
    public final boolean isSimple(Context<?> ctx) {
        return this.wrapped instanceof Table && !ctx.declareTables() || this.wrapped instanceof Field && !ctx.declareFields();
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (ctx.family() == SQLDialect.JAVA) {
            ctx.visit((QueryPart)this.wrapped).sql(".as(").visit(QueryPartListView.wrap((QueryPart[])Tools.combine(this.alias, this.fieldAliases != null ? this.fieldAliases : Tools.EMPTY_NAME))).sql(')');
        } else if (Boolean.TRUE.equals(ctx.data(Tools.BooleanDataKey.DATA_UNALIAS_ALIASED_EXPRESSIONS)) && this.wrapped instanceof Field) {
            this.toSQLWrapped(ctx);
        } else if (ctx.declareAliases() && (ctx.declareFields() || ctx.declareTables())) {
            boolean aliasedJoinTable = this.wrapped instanceof JoinTable;
            if (!aliasedJoinTable) {
                ctx.declareAliases(false);
            }
            if (SelectQueryImpl.EMULATE_SELECT_INTO_PROJECTION.contains((Object)ctx.dialect()) && this.wrapped instanceof Field && !this.alias.qualified() && this.alias.unqualifiedName().quoted() == Name.Quoted.UNQUOTED && this.alias.last().startsWith("@")) {
                this.acceptDeclareAliasTSQL(ctx);
            } else {
                this.acceptDeclareAliasStandard(ctx);
            }
            if (!aliasedJoinTable) {
                ctx.declareAliases(true);
            }
        } else {
            ctx.qualify(false, c -> c.visit(this.alias));
        }
    }

    private final void acceptDeclareAliasTSQL(Context<?> ctx) {
        ctx.visit(this.alias).sql(" = ");
        this.toSQLWrapped(ctx);
    }

    private final void acceptDeclareAliasStandard(Context<?> context) {
        if (this.wrapped instanceof TableImpl) {
            context.scopeMarkStart((QueryPart)this.wrapping);
        }
        SQLDialect dialect = context.dialect();
        SQLDialect family = context.family();
        boolean emulatedDerivedColumnList = false;
        if (context.family() == SQLDialect.SNOWFLAKE) {
            boolean bl = emulatedDerivedColumnList = this.fieldAliases != null && Tools.anyQuoted(context.settings(), this.fieldAliases);
        }
        if (this.fieldAliases != null && SUPPORT_DERIVED_COLUMN_NAMES_SPECIAL1.contains((Object)dialect) && (this.wrapped instanceof TableImpl || this.wrapped instanceof CommonTableExpressionImpl)) {
            Tools.visitSubquery(context, DSL.select(DSL.asterisk()).from((TableLike<?>)((Object)NoAutoAlias.noAutoAlias((Table)this.wrapped).as(this.alias))), 1);
        } else if (this.fieldAliases != null && (emulatedDerivedColumnList || SUPPORT_DERIVED_COLUMN_NAMES_SPECIAL2.contains((Object)dialect) || SUPPORT_DERIVED_COLUMN_NAMES_SPECIAL3.contains((Object)dialect))) {
            emulatedDerivedColumnList = true;
            if (this.wrapped instanceof Values && Values.NO_SUPPORT_VALUES.contains((Object)dialect)) {
                context.data(Tools.SimpleDataKey.DATA_SELECT_ALIASES, this.fieldAliases, t2 -> this.toSQLWrapped((Context<?>)t2));
            } else {
                List<Name> names;
                Select select;
                Q q = this.wrapped;
                if (q instanceof Select) {
                    Select s2;
                    select = s2 = (Select)q;
                } else {
                    q = this.wrapped;
                    if (q instanceof DerivedTable) {
                        DerivedTable d = (DerivedTable)q;
                        select = d.query();
                    } else {
                        select = DSL.select(DSL.asterisk()).from((TableLike<?>)((Object)NoAutoAlias.noAutoAlias((Table)this.wrapped).as(this.alias)));
                    }
                }
                Select wrappedAsSelect = select;
                List<Field<?>> select2 = wrappedAsSelect.getSelect();
                if (SUPPORT_DERIVED_COLUMN_NAMES_SPECIAL3.contains((Object)dialect) && (names = Tools.map(select2, Named::getUnqualifiedName)).size() > 0 && names.size() == new HashSet<Name>(names).size()) {
                    this.toSQLWrapped(context);
                    emulatedDerivedColumnList = false;
                }
                if (emulatedDerivedColumnList) {
                    if (AliasedSelect.avoidAliasPushdown(context, wrappedAsSelect) || !(this.wrapped instanceof Select) && !(this.wrapped instanceof DerivedTable)) {
                        SelectFieldList fields = new SelectFieldList();
                        block7: for (int i = 0; i < this.fieldAliases.length; ++i) {
                            switch (family) {
                                case ACCESS: {
                                    fields.add(DSL.field("''").as(this.fieldAliases[i]));
                                    continue block7;
                                }
                                case HANA: {
                                    if (i < select2.size()) {
                                        fields.add(DSL.field("null").cast(select2.get(i)).as(this.fieldAliases[i]));
                                        continue block7;
                                    }
                                    fields.add(DSL.field("null").as(this.fieldAliases[i]));
                                    continue block7;
                                }
                                default: {
                                    fields.add(DSL.field("null").as(this.fieldAliases[i]));
                                }
                            }
                        }
                        Tools.visitSubquery(context, DSL.select(fields).where((Condition)DSL.falseCondition()).unionAll(wrappedAsSelect), 1);
                    } else {
                        context.sql('(').visit(new AliasedSelect(wrappedAsSelect, true, false, false, this.fieldAliases)).sql(')');
                    }
                }
            }
        } else {
            this.toSQLWrapped(context);
        }
        this.toSQLAs(context);
        context.sql(' ').qualify(false, c -> c.visit(this.alias));
        if (this.fieldAliases != null && !emulatedDerivedColumnList) {
            this.toSQLDerivedColumnList(context);
        } else {
            switch (family) {
                case AURORA_POSTGRES: 
                case COCKROACHDB: 
                case HSQLDB: 
                case POSTGRES: 
                case YUGABYTEDB: {
                    Q o = this.wrapped;
                    if (!context.declareTables() || !(o instanceof ArrayTable)) break;
                    context.sql('(').visit(QueryPartListView.wrap((QueryPart[])((ArrayTable)o).fields()).qualify(false)).sql(')');
                    break;
                }
            }
        }
        if (this.wrapped instanceof TableImpl) {
            context.scopeMarkEnd((QueryPart)this.wrapping);
        }
    }

    final void toSQLAs(Context<?> ctx) {
        if (Boolean.TRUE.equals(ctx.data(Tools.BooleanDataKey.DATA_AS_REQUIRED))) {
            ctx.sql(' ').visit(Keywords.K_AS);
        } else if (this.wrapped instanceof Field) {
            if (ctx.settings().getRenderOptionalAsKeywordForFieldAliases() == RenderOptionalKeyword.DEFAULT && SUPPORT_AS_REQUIRED.contains((Object)ctx.dialect())) {
                ctx.sql(' ').visit(Keywords.K_AS);
            } else if (ctx.settings().getRenderOptionalAsKeywordForFieldAliases() == RenderOptionalKeyword.ON) {
                ctx.sql(' ').visit(Keywords.K_AS);
            }
        } else if (ctx.settings().getRenderOptionalAsKeywordForTableAliases() == RenderOptionalKeyword.DEFAULT && SUPPORT_AS_REQUIRED.contains((Object)ctx.dialect())) {
            ctx.sql(' ').visit(Keywords.K_AS);
        } else if (ctx.family() != SQLDialect.ORACLE && ctx.settings().getRenderOptionalAsKeywordForTableAliases() == RenderOptionalKeyword.ON) {
            ctx.sql(' ').visit(Keywords.K_AS);
        }
    }

    private final void toSQLWrapped(Context<?> ctx) {
        boolean wrap = this.wrapInParentheses.test(ctx);
        if (wrap) {
            ctx.data(Tools.BooleanDataKey.DATA_WRAP_DERIVED_TABLES_IN_PARENTHESES, false, c -> this.toSQLWrapped((Context<?>)c, wrap));
        } else {
            this.toSQLWrapped(ctx, wrap);
        }
    }

    private final void toSQLWrapped(Context<?> ctx, boolean wrap) {
        boolean nestedJoinTable = this.wrapped instanceof JoinTable;
        if (wrap) {
            if (nestedJoinTable) {
                ctx.sqlIndentStart('(');
            } else {
                ctx.sql('(');
            }
        }
        if (nestedJoinTable && NO_SUPPORT_ALIASED_JOIN_TABLES.contains((Object)ctx.dialect())) {
            ctx.visit(DSL.select(DSL.asterisk()).from((TableLike<?>)((Table)this.wrapped)));
        } else {
            ctx.visit((QueryPart)this.wrapped);
        }
        if (wrap) {
            if (nestedJoinTable) {
                ctx.sqlIndentEnd(')');
            } else {
                ctx.sql(')');
            }
        }
    }

    private final void toSQLDerivedColumnList(Context<?> ctx) {
        ctx.sql(" (").visit(QueryPartListView.wrap((QueryPart[])this.fieldAliases)).sql(')');
    }

    @Override
    public final Clause[] clauses(Context<?> ctx) {
        if (ctx.declareFields() || ctx.declareTables()) {
            if (this.wrapped instanceof Table) {
                return CLAUSES_TABLE_ALIAS;
            }
            return CLAUSES_FIELD_ALIAS;
        }
        if (this.wrapped instanceof Table) {
            return CLAUSES_TABLE_REFERENCE;
        }
        return CLAUSES_FIELD_REFERENCE;
    }

    @Override
    public final boolean declaresFields() {
        return true;
    }

    @Override
    public final boolean declaresTables() {
        return true;
    }
}

