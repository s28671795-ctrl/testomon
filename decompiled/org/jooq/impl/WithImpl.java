/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.jooq.Clause;
import org.jooq.CommonTableExpression;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.DerivedColumnList;
import org.jooq.Field;
import org.jooq.InsertSetStep;
import org.jooq.MergeUsingStep;
import org.jooq.Name;
import org.jooq.Pro;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.Record10;
import org.jooq.Record11;
import org.jooq.Record12;
import org.jooq.Record13;
import org.jooq.Record14;
import org.jooq.Record15;
import org.jooq.Record16;
import org.jooq.Record17;
import org.jooq.Record18;
import org.jooq.Record19;
import org.jooq.Record2;
import org.jooq.Record20;
import org.jooq.Record21;
import org.jooq.Record22;
import org.jooq.Record3;
import org.jooq.Record4;
import org.jooq.Record5;
import org.jooq.Record6;
import org.jooq.Record7;
import org.jooq.Record8;
import org.jooq.Record9;
import org.jooq.Replacer;
import org.jooq.ResultQuery;
import org.jooq.SQL;
import org.jooq.SQLDialect;
import org.jooq.SelectField;
import org.jooq.SelectFieldOrAsterisk;
import org.jooq.SelectSelectStep;
import org.jooq.SelectWhereStep;
import org.jooq.Table;
import org.jooq.TableLike;
import org.jooq.Traverser;
import org.jooq.WithAsStep;
import org.jooq.WithAsStep1;
import org.jooq.WithAsStep10;
import org.jooq.WithAsStep11;
import org.jooq.WithAsStep12;
import org.jooq.WithAsStep13;
import org.jooq.WithAsStep14;
import org.jooq.WithAsStep15;
import org.jooq.WithAsStep16;
import org.jooq.WithAsStep17;
import org.jooq.WithAsStep18;
import org.jooq.WithAsStep19;
import org.jooq.WithAsStep2;
import org.jooq.WithAsStep20;
import org.jooq.WithAsStep21;
import org.jooq.WithAsStep22;
import org.jooq.WithAsStep3;
import org.jooq.WithAsStep4;
import org.jooq.WithAsStep5;
import org.jooq.WithAsStep6;
import org.jooq.WithAsStep7;
import org.jooq.WithAsStep8;
import org.jooq.WithAsStep9;
import org.jooq.WithStep;
import org.jooq.impl.AbstractQueryPart;
import org.jooq.impl.CommonTableExpressionList;
import org.jooq.impl.DSL;
import org.jooq.impl.DeleteImpl;
import org.jooq.impl.InsertImpl;
import org.jooq.impl.Keywords;
import org.jooq.impl.MergeImpl;
import org.jooq.impl.QOM;
import org.jooq.impl.SelectImpl;
import org.jooq.impl.Tools;
import org.jooq.impl.TopLevelCte;
import org.jooq.impl.Transformations;
import org.jooq.impl.UpdateImpl;

final class WithImpl
extends AbstractQueryPart
implements WithAsStep,
WithAsStep1,
WithAsStep2,
WithAsStep3,
WithAsStep4,
WithAsStep5,
WithAsStep6,
WithAsStep7,
WithAsStep8,
WithAsStep9,
WithAsStep10,
WithAsStep11,
WithAsStep12,
WithAsStep13,
WithAsStep14,
WithAsStep15,
WithAsStep16,
WithAsStep17,
WithAsStep18,
WithAsStep19,
WithAsStep20,
WithAsStep21,
WithAsStep22,
WithStep,
QOM.With {
    private static final Clause[] CLAUSES = new Clause[]{Clause.WITH};
    @Pro
    static final Set<SQLDialect> NO_RECURSIVE_KEYWORD = SQLDialect.supportedBy(SQLDialect.DB2, SQLDialect.INFORMIX, SQLDialect.ORACLE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER);
    @Pro
    static final Set<SQLDialect> NO_SUBQUERY_CTE = SQLDialect.supportedBy(SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.TERADATA);
    final CommonTableExpressionList ctes;
    final boolean recursive;
    private Configuration configuration;
    private transient Name alias;
    private transient Name[] fieldAliases;
    private transient BiFunction<? super Field<?>, ? super Integer, ? extends String> fieldNameFunction;

    WithImpl(Configuration configuration, boolean recursive) {
        this.configuration = configuration;
        this.recursive = recursive;
        this.ctes = new CommonTableExpressionList();
    }

    @Override
    public final void accept(Context<?> ctx) {
        CommonTableExpressionList list;
        if (Transformations.transformInlineCTE(ctx.configuration())) {
            return;
        }
        CommonTableExpression<?> updatableCte = Tools.extractUpdatableCte(ctx);
        if (updatableCte != null) {
            list = new CommonTableExpressionList();
            list.addAll(this.ctes);
            list.add(updatableCte);
        } else {
            list = this.ctes;
        }
        if (!list.isEmpty()) {
            TopLevelCte cte;
            if (ctx.scopeLevel() > 0 && NO_SUBQUERY_CTE.contains((Object)ctx.dialect()) && (cte = (TopLevelCte)ctx.data(Tools.SimpleDataKey.DATA_TOP_LEVEL_CTE)) != null) {
                cte.addAll(list);
                cte.recursive |= this.recursive;
                return;
            }
            WithImpl.acceptWithRecursive(ctx, this.recursive);
            ctx.data(Tools.BooleanDataKey.DATA_LIST_ALREADY_INDENTED, true, c1 -> c1.formatIndentStart().formatSeparator().declareCTE(true, c2 -> c2.visit(list)).formatIndentEnd().formatSeparator());
        }
    }

    static final void acceptWithRecursive(Context<?> ctx, boolean recursive) {
        ctx.visit(Keywords.K_WITH);
        if (recursive && !NO_RECURSIVE_KEYWORD.contains((Object)ctx.dialect())) {
            ctx.sql(' ').visit(Keywords.K_RECURSIVE);
        }
    }

    @Override
    public final Clause[] clauses(Context<?> ctx) {
        return CLAUSES;
    }

    private final WithStep as0(ResultQuery query, Boolean materialized) {
        DerivedColumnList dcl = this.fieldNameFunction != null ? DSL.name(this.alias).fields(this.fieldNameFunction) : DSL.name(this.alias).fields(this.fieldAliases);
        CommonTableExpression cte = materialized == null ? dcl.as(query) : (materialized != false ? dcl.asMaterialized(query) : dcl.asNotMaterialized(query));
        this.ctes.add(cte);
        this.alias = null;
        this.fieldAliases = null;
        this.fieldNameFunction = null;
        return this;
    }

    public final WithStep as(ResultQuery query) {
        return this.as0(query, null);
    }

    public final WithStep asMaterialized(ResultQuery query) {
        return this.as0(query, true);
    }

    public final WithStep asNotMaterialized(ResultQuery query) {
        return this.as0(query, false);
    }

    @Override
    public final WithImpl with(String a) {
        return this.with(DSL.name(a));
    }

    @Override
    public final WithImpl with(String a, String ... f) {
        return this.with(DSL.name(a), Tools.map(f, s2 -> DSL.name(s2), Name[]::new));
    }

    @Override
    public final WithImpl with(String a, Collection<String> f) {
        return this.with(DSL.name(a), Tools.map(f, s2 -> DSL.name(s2), Name[]::new));
    }

    @Override
    public final WithImpl with(Name a) {
        return this.with(a, new Name[0]);
    }

    @Override
    public final WithImpl with(Name a, Name ... f) {
        this.alias = a;
        this.fieldAliases = f;
        return this;
    }

    @Override
    public final WithImpl with(Name a, Collection<? extends Name> f) {
        return this.with(a, f.toArray(Tools.EMPTY_NAME));
    }

    @Override
    public final WithImpl with(String a, Function<? super Field<?>, ? extends String> f) {
        this.alias = DSL.name(a);
        this.fieldNameFunction = (field2, i) -> (String)f.apply((Field<?>)field2);
        return this;
    }

    @Override
    public final WithImpl with(String a, BiFunction<? super Field<?>, ? super Integer, ? extends String> f) {
        this.alias = DSL.name(a);
        this.fieldNameFunction = f;
        return this;
    }

    @Override
    public final WithImpl with(String a, String fieldAlias1) {
        return this.with(DSL.name(a), Tools.names(new String[]{fieldAlias1}));
    }

    @Override
    public final WithImpl with(String a, String fieldAlias1, String fieldAlias2) {
        return this.with(DSL.name(a), Tools.names(new String[]{fieldAlias1, fieldAlias2}));
    }

    @Override
    public final WithImpl with(String a, String fieldAlias1, String fieldAlias2, String fieldAlias3) {
        return this.with(DSL.name(a), Tools.names(new String[]{fieldAlias1, fieldAlias2, fieldAlias3}));
    }

    @Override
    public final WithImpl with(String a, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4) {
        return this.with(DSL.name(a), Tools.names(new String[]{fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4}));
    }

    @Override
    public final WithImpl with(String a, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5) {
        return this.with(DSL.name(a), Tools.names(new String[]{fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5}));
    }

    @Override
    public final WithImpl with(String a, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6) {
        return this.with(DSL.name(a), Tools.names(new String[]{fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6}));
    }

    @Override
    public final WithImpl with(String a, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7) {
        return this.with(DSL.name(a), Tools.names(new String[]{fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7}));
    }

    @Override
    public final WithImpl with(String a, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8) {
        return this.with(DSL.name(a), Tools.names(new String[]{fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8}));
    }

    @Override
    public final WithImpl with(String a, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9) {
        return this.with(DSL.name(a), Tools.names(new String[]{fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9}));
    }

    @Override
    public final WithImpl with(String a, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10) {
        return this.with(DSL.name(a), Tools.names(new String[]{fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10}));
    }

    @Override
    public final WithImpl with(String a, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11) {
        return this.with(DSL.name(a), Tools.names(new String[]{fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11}));
    }

    @Override
    public final WithImpl with(String a, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11, String fieldAlias12) {
        return this.with(DSL.name(a), Tools.names(new String[]{fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12}));
    }

    @Override
    public final WithImpl with(String a, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11, String fieldAlias12, String fieldAlias13) {
        return this.with(DSL.name(a), Tools.names(new String[]{fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13}));
    }

    @Override
    public final WithImpl with(String a, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11, String fieldAlias12, String fieldAlias13, String fieldAlias14) {
        return this.with(DSL.name(a), Tools.names(new String[]{fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14}));
    }

    @Override
    public final WithImpl with(String a, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11, String fieldAlias12, String fieldAlias13, String fieldAlias14, String fieldAlias15) {
        return this.with(DSL.name(a), Tools.names(new String[]{fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15}));
    }

    @Override
    public final WithImpl with(String a, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11, String fieldAlias12, String fieldAlias13, String fieldAlias14, String fieldAlias15, String fieldAlias16) {
        return this.with(DSL.name(a), Tools.names(new String[]{fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15, fieldAlias16}));
    }

    @Override
    public final WithImpl with(String a, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11, String fieldAlias12, String fieldAlias13, String fieldAlias14, String fieldAlias15, String fieldAlias16, String fieldAlias17) {
        return this.with(DSL.name(a), Tools.names(new String[]{fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15, fieldAlias16, fieldAlias17}));
    }

    @Override
    public final WithImpl with(String a, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11, String fieldAlias12, String fieldAlias13, String fieldAlias14, String fieldAlias15, String fieldAlias16, String fieldAlias17, String fieldAlias18) {
        return this.with(DSL.name(a), Tools.names(new String[]{fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15, fieldAlias16, fieldAlias17, fieldAlias18}));
    }

    @Override
    public final WithImpl with(String a, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11, String fieldAlias12, String fieldAlias13, String fieldAlias14, String fieldAlias15, String fieldAlias16, String fieldAlias17, String fieldAlias18, String fieldAlias19) {
        return this.with(DSL.name(a), Tools.names(new String[]{fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15, fieldAlias16, fieldAlias17, fieldAlias18, fieldAlias19}));
    }

    @Override
    public final WithImpl with(String a, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11, String fieldAlias12, String fieldAlias13, String fieldAlias14, String fieldAlias15, String fieldAlias16, String fieldAlias17, String fieldAlias18, String fieldAlias19, String fieldAlias20) {
        return this.with(DSL.name(a), Tools.names(new String[]{fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15, fieldAlias16, fieldAlias17, fieldAlias18, fieldAlias19, fieldAlias20}));
    }

    @Override
    public final WithImpl with(String a, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11, String fieldAlias12, String fieldAlias13, String fieldAlias14, String fieldAlias15, String fieldAlias16, String fieldAlias17, String fieldAlias18, String fieldAlias19, String fieldAlias20, String fieldAlias21) {
        return this.with(DSL.name(a), Tools.names(new String[]{fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15, fieldAlias16, fieldAlias17, fieldAlias18, fieldAlias19, fieldAlias20, fieldAlias21}));
    }

    @Override
    public final WithImpl with(String a, String fieldAlias1, String fieldAlias2, String fieldAlias3, String fieldAlias4, String fieldAlias5, String fieldAlias6, String fieldAlias7, String fieldAlias8, String fieldAlias9, String fieldAlias10, String fieldAlias11, String fieldAlias12, String fieldAlias13, String fieldAlias14, String fieldAlias15, String fieldAlias16, String fieldAlias17, String fieldAlias18, String fieldAlias19, String fieldAlias20, String fieldAlias21, String fieldAlias22) {
        return this.with(DSL.name(a), Tools.names(new String[]{fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15, fieldAlias16, fieldAlias17, fieldAlias18, fieldAlias19, fieldAlias20, fieldAlias21, fieldAlias22}));
    }

    @Override
    public final WithImpl with(Name a, Name fieldAlias1) {
        return this.with(a, new Name[]{fieldAlias1});
    }

    @Override
    public final WithImpl with(Name a, Name fieldAlias1, Name fieldAlias2) {
        return this.with(a, new Name[]{fieldAlias1, fieldAlias2});
    }

    @Override
    public final WithImpl with(Name a, Name fieldAlias1, Name fieldAlias2, Name fieldAlias3) {
        return this.with(a, new Name[]{fieldAlias1, fieldAlias2, fieldAlias3});
    }

    @Override
    public final WithImpl with(Name a, Name fieldAlias1, Name fieldAlias2, Name fieldAlias3, Name fieldAlias4) {
        return this.with(a, new Name[]{fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4});
    }

    @Override
    public final WithImpl with(Name a, Name fieldAlias1, Name fieldAlias2, Name fieldAlias3, Name fieldAlias4, Name fieldAlias5) {
        return this.with(a, new Name[]{fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5});
    }

    @Override
    public final WithImpl with(Name a, Name fieldAlias1, Name fieldAlias2, Name fieldAlias3, Name fieldAlias4, Name fieldAlias5, Name fieldAlias6) {
        return this.with(a, new Name[]{fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6});
    }

    @Override
    public final WithImpl with(Name a, Name fieldAlias1, Name fieldAlias2, Name fieldAlias3, Name fieldAlias4, Name fieldAlias5, Name fieldAlias6, Name fieldAlias7) {
        return this.with(a, new Name[]{fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7});
    }

    @Override
    public final WithImpl with(Name a, Name fieldAlias1, Name fieldAlias2, Name fieldAlias3, Name fieldAlias4, Name fieldAlias5, Name fieldAlias6, Name fieldAlias7, Name fieldAlias8) {
        return this.with(a, new Name[]{fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8});
    }

    @Override
    public final WithImpl with(Name a, Name fieldAlias1, Name fieldAlias2, Name fieldAlias3, Name fieldAlias4, Name fieldAlias5, Name fieldAlias6, Name fieldAlias7, Name fieldAlias8, Name fieldAlias9) {
        return this.with(a, new Name[]{fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9});
    }

    @Override
    public final WithImpl with(Name a, Name fieldAlias1, Name fieldAlias2, Name fieldAlias3, Name fieldAlias4, Name fieldAlias5, Name fieldAlias6, Name fieldAlias7, Name fieldAlias8, Name fieldAlias9, Name fieldAlias10) {
        return this.with(a, new Name[]{fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10});
    }

    @Override
    public final WithImpl with(Name a, Name fieldAlias1, Name fieldAlias2, Name fieldAlias3, Name fieldAlias4, Name fieldAlias5, Name fieldAlias6, Name fieldAlias7, Name fieldAlias8, Name fieldAlias9, Name fieldAlias10, Name fieldAlias11) {
        return this.with(a, new Name[]{fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11});
    }

    @Override
    public final WithImpl with(Name a, Name fieldAlias1, Name fieldAlias2, Name fieldAlias3, Name fieldAlias4, Name fieldAlias5, Name fieldAlias6, Name fieldAlias7, Name fieldAlias8, Name fieldAlias9, Name fieldAlias10, Name fieldAlias11, Name fieldAlias12) {
        return this.with(a, new Name[]{fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12});
    }

    @Override
    public final WithImpl with(Name a, Name fieldAlias1, Name fieldAlias2, Name fieldAlias3, Name fieldAlias4, Name fieldAlias5, Name fieldAlias6, Name fieldAlias7, Name fieldAlias8, Name fieldAlias9, Name fieldAlias10, Name fieldAlias11, Name fieldAlias12, Name fieldAlias13) {
        return this.with(a, new Name[]{fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13});
    }

    @Override
    public final WithImpl with(Name a, Name fieldAlias1, Name fieldAlias2, Name fieldAlias3, Name fieldAlias4, Name fieldAlias5, Name fieldAlias6, Name fieldAlias7, Name fieldAlias8, Name fieldAlias9, Name fieldAlias10, Name fieldAlias11, Name fieldAlias12, Name fieldAlias13, Name fieldAlias14) {
        return this.with(a, new Name[]{fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14});
    }

    @Override
    public final WithImpl with(Name a, Name fieldAlias1, Name fieldAlias2, Name fieldAlias3, Name fieldAlias4, Name fieldAlias5, Name fieldAlias6, Name fieldAlias7, Name fieldAlias8, Name fieldAlias9, Name fieldAlias10, Name fieldAlias11, Name fieldAlias12, Name fieldAlias13, Name fieldAlias14, Name fieldAlias15) {
        return this.with(a, new Name[]{fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15});
    }

    @Override
    public final WithImpl with(Name a, Name fieldAlias1, Name fieldAlias2, Name fieldAlias3, Name fieldAlias4, Name fieldAlias5, Name fieldAlias6, Name fieldAlias7, Name fieldAlias8, Name fieldAlias9, Name fieldAlias10, Name fieldAlias11, Name fieldAlias12, Name fieldAlias13, Name fieldAlias14, Name fieldAlias15, Name fieldAlias16) {
        return this.with(a, new Name[]{fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15, fieldAlias16});
    }

    @Override
    public final WithImpl with(Name a, Name fieldAlias1, Name fieldAlias2, Name fieldAlias3, Name fieldAlias4, Name fieldAlias5, Name fieldAlias6, Name fieldAlias7, Name fieldAlias8, Name fieldAlias9, Name fieldAlias10, Name fieldAlias11, Name fieldAlias12, Name fieldAlias13, Name fieldAlias14, Name fieldAlias15, Name fieldAlias16, Name fieldAlias17) {
        return this.with(a, new Name[]{fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15, fieldAlias16, fieldAlias17});
    }

    @Override
    public final WithImpl with(Name a, Name fieldAlias1, Name fieldAlias2, Name fieldAlias3, Name fieldAlias4, Name fieldAlias5, Name fieldAlias6, Name fieldAlias7, Name fieldAlias8, Name fieldAlias9, Name fieldAlias10, Name fieldAlias11, Name fieldAlias12, Name fieldAlias13, Name fieldAlias14, Name fieldAlias15, Name fieldAlias16, Name fieldAlias17, Name fieldAlias18) {
        return this.with(a, new Name[]{fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15, fieldAlias16, fieldAlias17, fieldAlias18});
    }

    @Override
    public final WithImpl with(Name a, Name fieldAlias1, Name fieldAlias2, Name fieldAlias3, Name fieldAlias4, Name fieldAlias5, Name fieldAlias6, Name fieldAlias7, Name fieldAlias8, Name fieldAlias9, Name fieldAlias10, Name fieldAlias11, Name fieldAlias12, Name fieldAlias13, Name fieldAlias14, Name fieldAlias15, Name fieldAlias16, Name fieldAlias17, Name fieldAlias18, Name fieldAlias19) {
        return this.with(a, new Name[]{fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15, fieldAlias16, fieldAlias17, fieldAlias18, fieldAlias19});
    }

    @Override
    public final WithImpl with(Name a, Name fieldAlias1, Name fieldAlias2, Name fieldAlias3, Name fieldAlias4, Name fieldAlias5, Name fieldAlias6, Name fieldAlias7, Name fieldAlias8, Name fieldAlias9, Name fieldAlias10, Name fieldAlias11, Name fieldAlias12, Name fieldAlias13, Name fieldAlias14, Name fieldAlias15, Name fieldAlias16, Name fieldAlias17, Name fieldAlias18, Name fieldAlias19, Name fieldAlias20) {
        return this.with(a, new Name[]{fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15, fieldAlias16, fieldAlias17, fieldAlias18, fieldAlias19, fieldAlias20});
    }

    @Override
    public final WithImpl with(Name a, Name fieldAlias1, Name fieldAlias2, Name fieldAlias3, Name fieldAlias4, Name fieldAlias5, Name fieldAlias6, Name fieldAlias7, Name fieldAlias8, Name fieldAlias9, Name fieldAlias10, Name fieldAlias11, Name fieldAlias12, Name fieldAlias13, Name fieldAlias14, Name fieldAlias15, Name fieldAlias16, Name fieldAlias17, Name fieldAlias18, Name fieldAlias19, Name fieldAlias20, Name fieldAlias21) {
        return this.with(a, new Name[]{fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15, fieldAlias16, fieldAlias17, fieldAlias18, fieldAlias19, fieldAlias20, fieldAlias21});
    }

    @Override
    public final WithImpl with(Name a, Name fieldAlias1, Name fieldAlias2, Name fieldAlias3, Name fieldAlias4, Name fieldAlias5, Name fieldAlias6, Name fieldAlias7, Name fieldAlias8, Name fieldAlias9, Name fieldAlias10, Name fieldAlias11, Name fieldAlias12, Name fieldAlias13, Name fieldAlias14, Name fieldAlias15, Name fieldAlias16, Name fieldAlias17, Name fieldAlias18, Name fieldAlias19, Name fieldAlias20, Name fieldAlias21, Name fieldAlias22) {
        return this.with(a, new Name[]{fieldAlias1, fieldAlias2, fieldAlias3, fieldAlias4, fieldAlias5, fieldAlias6, fieldAlias7, fieldAlias8, fieldAlias9, fieldAlias10, fieldAlias11, fieldAlias12, fieldAlias13, fieldAlias14, fieldAlias15, fieldAlias16, fieldAlias17, fieldAlias18, fieldAlias19, fieldAlias20, fieldAlias21, fieldAlias22});
    }

    @Override
    public final WithImpl with(CommonTableExpression<?> ... tables) {
        return this.with(Arrays.asList(tables));
    }

    @Override
    public final WithImpl with(Collection<? extends CommonTableExpression<?>> tables) {
        for (CommonTableExpression<?> table : tables) {
            this.ctes.add(table);
        }
        return this;
    }

    @Override
    public final <R extends Record> SelectWhereStep<R> selectFrom(TableLike<R> table) {
        return new SelectImpl(this.configuration, this).from((TableLike)table);
    }

    @Override
    public final SelectWhereStep<Record> selectFrom(Name table) {
        return new SelectImpl(this.configuration, this).from(table);
    }

    @Override
    public final SelectWhereStep<Record> selectFrom(SQL sql) {
        return new SelectImpl(this.configuration, this).from(sql);
    }

    @Override
    public final SelectWhereStep<Record> selectFrom(String sql) {
        return new SelectImpl(this.configuration, this).from(sql);
    }

    @Override
    public final SelectWhereStep<Record> selectFrom(String sql, Object ... bindings) {
        return new SelectImpl(this.configuration, this).from(sql, bindings);
    }

    @Override
    public final SelectWhereStep<Record> selectFrom(String sql, QueryPart ... parts) {
        return new SelectImpl(this.configuration, this).from(sql, parts);
    }

    @Override
    public final SelectSelectStep<Record> select(Collection<? extends SelectFieldOrAsterisk> fields) {
        return new SelectImpl(this.configuration, this).select((Collection)fields);
    }

    @Override
    public final SelectSelectStep<Record> select(SelectFieldOrAsterisk ... fields) {
        return new SelectImpl(this.configuration, this).select(fields);
    }

    @Override
    public final <T1> SelectSelectStep<Record1<T1>> select(SelectField<T1> field1) {
        return this.select(new SelectField[]{field1});
    }

    @Override
    public final <T1, T2> SelectSelectStep<Record2<T1, T2>> select(SelectField<T1> field1, SelectField<T2> field2) {
        return this.select(new SelectField[]{field1, field2});
    }

    @Override
    public final <T1, T2, T3> SelectSelectStep<Record3<T1, T2, T3>> select(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3) {
        return this.select(new SelectField[]{field1, field2, field3});
    }

    @Override
    public final <T1, T2, T3, T4> SelectSelectStep<Record4<T1, T2, T3, T4>> select(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4) {
        return this.select(new SelectField[]{field1, field2, field3, field4});
    }

    @Override
    public final <T1, T2, T3, T4, T5> SelectSelectStep<Record5<T1, T2, T3, T4, T5>> select(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5) {
        return this.select(new SelectField[]{field1, field2, field3, field4, field5});
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6> SelectSelectStep<Record6<T1, T2, T3, T4, T5, T6>> select(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6) {
        return this.select(new SelectField[]{field1, field2, field3, field4, field5, field6});
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7> SelectSelectStep<Record7<T1, T2, T3, T4, T5, T6, T7>> select(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7) {
        return this.select(new SelectField[]{field1, field2, field3, field4, field5, field6, field7});
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8> SelectSelectStep<Record8<T1, T2, T3, T4, T5, T6, T7, T8>> select(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8) {
        return this.select(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8});
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9> SelectSelectStep<Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> select(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9) {
        return this.select(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9});
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> SelectSelectStep<Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> select(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10) {
        return this.select(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10});
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> SelectSelectStep<Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>> select(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11) {
        return this.select(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11});
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> SelectSelectStep<Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>> select(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12) {
        return this.select(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12});
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> SelectSelectStep<Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> select(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13) {
        return this.select(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13});
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> SelectSelectStep<Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> select(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14) {
        return this.select(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14});
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> SelectSelectStep<Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>> select(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15) {
        return this.select(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15});
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> SelectSelectStep<Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>> select(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16) {
        return this.select(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16});
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> SelectSelectStep<Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>> select(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16, SelectField<T17> field17) {
        return this.select(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17});
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> SelectSelectStep<Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>> select(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16, SelectField<T17> field17, SelectField<T18> field18) {
        return this.select(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18});
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> SelectSelectStep<Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>> select(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16, SelectField<T17> field17, SelectField<T18> field18, SelectField<T19> field19) {
        return this.select(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19});
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> SelectSelectStep<Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>> select(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16, SelectField<T17> field17, SelectField<T18> field18, SelectField<T19> field19, SelectField<T20> field20) {
        return this.select(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20});
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> SelectSelectStep<Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>> select(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16, SelectField<T17> field17, SelectField<T18> field18, SelectField<T19> field19, SelectField<T20> field20, SelectField<T21> field21) {
        return this.select(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21});
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> SelectSelectStep<Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>> select(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16, SelectField<T17> field17, SelectField<T18> field18, SelectField<T19> field19, SelectField<T20> field20, SelectField<T21> field21, SelectField<T22> field22) {
        return this.select(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21, field22});
    }

    @Override
    public final SelectSelectStep<Record> selectDistinct(Collection<? extends SelectFieldOrAsterisk> fields) {
        return new SelectImpl(this.configuration, this, true).select((Collection)fields);
    }

    @Override
    public final SelectSelectStep<Record> selectDistinct(SelectFieldOrAsterisk ... fields) {
        return new SelectImpl(this.configuration, this, true).select(fields);
    }

    @Override
    public final <T1> SelectSelectStep<Record1<T1>> selectDistinct(SelectField<T1> field1) {
        return this.selectDistinct(new SelectField[]{field1});
    }

    @Override
    public final <T1, T2> SelectSelectStep<Record2<T1, T2>> selectDistinct(SelectField<T1> field1, SelectField<T2> field2) {
        return this.selectDistinct(new SelectField[]{field1, field2});
    }

    @Override
    public final <T1, T2, T3> SelectSelectStep<Record3<T1, T2, T3>> selectDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3) {
        return this.selectDistinct(new SelectField[]{field1, field2, field3});
    }

    @Override
    public final <T1, T2, T3, T4> SelectSelectStep<Record4<T1, T2, T3, T4>> selectDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4) {
        return this.selectDistinct(new SelectField[]{field1, field2, field3, field4});
    }

    @Override
    public final <T1, T2, T3, T4, T5> SelectSelectStep<Record5<T1, T2, T3, T4, T5>> selectDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5) {
        return this.selectDistinct(new SelectField[]{field1, field2, field3, field4, field5});
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6> SelectSelectStep<Record6<T1, T2, T3, T4, T5, T6>> selectDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6) {
        return this.selectDistinct(new SelectField[]{field1, field2, field3, field4, field5, field6});
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7> SelectSelectStep<Record7<T1, T2, T3, T4, T5, T6, T7>> selectDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7) {
        return this.selectDistinct(new SelectField[]{field1, field2, field3, field4, field5, field6, field7});
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8> SelectSelectStep<Record8<T1, T2, T3, T4, T5, T6, T7, T8>> selectDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8) {
        return this.selectDistinct(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8});
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9> SelectSelectStep<Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> selectDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9) {
        return this.selectDistinct(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9});
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> SelectSelectStep<Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> selectDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10) {
        return this.selectDistinct(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10});
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> SelectSelectStep<Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>> selectDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11) {
        return this.selectDistinct(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11});
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> SelectSelectStep<Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>> selectDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12) {
        return this.selectDistinct(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12});
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> SelectSelectStep<Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> selectDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13) {
        return this.selectDistinct(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13});
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> SelectSelectStep<Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> selectDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14) {
        return this.selectDistinct(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14});
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> SelectSelectStep<Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>> selectDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15) {
        return this.selectDistinct(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15});
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> SelectSelectStep<Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>> selectDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16) {
        return this.selectDistinct(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16});
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> SelectSelectStep<Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>> selectDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16, SelectField<T17> field17) {
        return this.selectDistinct(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17});
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> SelectSelectStep<Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>> selectDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16, SelectField<T17> field17, SelectField<T18> field18) {
        return this.selectDistinct(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18});
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> SelectSelectStep<Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>> selectDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16, SelectField<T17> field17, SelectField<T18> field18, SelectField<T19> field19) {
        return this.selectDistinct(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19});
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> SelectSelectStep<Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>> selectDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16, SelectField<T17> field17, SelectField<T18> field18, SelectField<T19> field19, SelectField<T20> field20) {
        return this.selectDistinct(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20});
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> SelectSelectStep<Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21>> selectDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16, SelectField<T17> field17, SelectField<T18> field18, SelectField<T19> field19, SelectField<T20> field20, SelectField<T21> field21) {
        return this.selectDistinct(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21});
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> SelectSelectStep<Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22>> selectDistinct(SelectField<T1> field1, SelectField<T2> field2, SelectField<T3> field3, SelectField<T4> field4, SelectField<T5> field5, SelectField<T6> field6, SelectField<T7> field7, SelectField<T8> field8, SelectField<T9> field9, SelectField<T10> field10, SelectField<T11> field11, SelectField<T12> field12, SelectField<T13> field13, SelectField<T14> field14, SelectField<T15> field15, SelectField<T16> field16, SelectField<T17> field17, SelectField<T18> field18, SelectField<T19> field19, SelectField<T20> field20, SelectField<T21> field21, SelectField<T22> field22) {
        return this.selectDistinct(new SelectField[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21, field22});
    }

    @Override
    public final SelectSelectStep<Record1<Integer>> selectZero() {
        return new SelectImpl(this.configuration, this).select(DSL.zero());
    }

    @Override
    public final SelectSelectStep<Record1<Integer>> selectOne() {
        return new SelectImpl(this.configuration, this).select(DSL.one());
    }

    @Override
    public final SelectSelectStep<Record1<Integer>> selectCount() {
        return new SelectImpl(this.configuration, this).select(DSL.count());
    }

    @Override
    public final <R extends Record> InsertSetStep<R> insertInto(Table<R> into) {
        return new InsertImpl(this.configuration, this, into);
    }

    public final <R extends Record, T1> InsertImpl insertInto(Table<R> into, Field<T1> field1) {
        return this.insertInto((Table)into, Arrays.asList(field1));
    }

    public final <R extends Record, T1, T2> InsertImpl insertInto(Table<R> into, Field<T1> field1, Field<T2> field2) {
        return this.insertInto((Table)into, Arrays.asList(field1, field2));
    }

    public final <R extends Record, T1, T2, T3> InsertImpl insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3) {
        return this.insertInto((Table)into, Arrays.asList(field1, field2, field3));
    }

    public final <R extends Record, T1, T2, T3, T4> InsertImpl insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4) {
        return this.insertInto((Table)into, Arrays.asList(field1, field2, field3, field4));
    }

    public final <R extends Record, T1, T2, T3, T4, T5> InsertImpl insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5) {
        return this.insertInto((Table)into, Arrays.asList(field1, field2, field3, field4, field5));
    }

    public final <R extends Record, T1, T2, T3, T4, T5, T6> InsertImpl insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6) {
        return this.insertInto((Table)into, Arrays.asList(field1, field2, field3, field4, field5, field6));
    }

    public final <R extends Record, T1, T2, T3, T4, T5, T6, T7> InsertImpl insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7) {
        return this.insertInto((Table)into, Arrays.asList(field1, field2, field3, field4, field5, field6, field7));
    }

    public final <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8> InsertImpl insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8) {
        return this.insertInto((Table)into, Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8));
    }

    public final <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9> InsertImpl insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9) {
        return this.insertInto((Table)into, Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8, field9));
    }

    public final <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> InsertImpl insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10) {
        return this.insertInto((Table)into, Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10));
    }

    public final <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> InsertImpl insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11) {
        return this.insertInto((Table)into, Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11));
    }

    public final <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> InsertImpl insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12) {
        return this.insertInto((Table)into, Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12));
    }

    public final <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> InsertImpl insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13) {
        return this.insertInto((Table)into, Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13));
    }

    public final <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> InsertImpl insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14) {
        return this.insertInto((Table)into, Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14));
    }

    public final <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> InsertImpl insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15) {
        return this.insertInto((Table)into, Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15));
    }

    public final <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> InsertImpl insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16) {
        return this.insertInto((Table)into, Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16));
    }

    public final <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> InsertImpl insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17) {
        return this.insertInto((Table)into, Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17));
    }

    public final <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> InsertImpl insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18) {
        return this.insertInto((Table)into, Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18));
    }

    public final <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> InsertImpl insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19) {
        return this.insertInto((Table)into, Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19));
    }

    public final <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> InsertImpl insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20) {
        return this.insertInto((Table)into, Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20));
    }

    public final <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> InsertImpl insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20, Field<T21> field21) {
        return this.insertInto((Table)into, Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21));
    }

    public final <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> InsertImpl insertInto(Table<R> into, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20, Field<T21> field21, Field<T22> field22) {
        return this.insertInto((Table)into, Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21, field22));
    }

    public final <R extends Record> InsertImpl insertInto(Table<R> into, Field<?> ... fields) {
        return this.insertInto((Table)into, Arrays.asList(fields));
    }

    public final <R extends Record> InsertImpl insertInto(Table<R> into, Collection<? extends Field<?>> fields) {
        return new InsertImpl(this.configuration, this, into, fields);
    }

    public final <R extends Record> UpdateImpl update(Table<R> table) {
        return new UpdateImpl<R>(this.configuration, this, table);
    }

    @Override
    public final <R extends Record> MergeUsingStep<R> mergeInto(Table<R> table) {
        return new MergeImpl(this.configuration, this, table);
    }

    @Deprecated(forRemoval=true, since="3.14")
    public final <R extends Record, T1> MergeImpl mergeInto(Table<R> table, Field<T1> field1) {
        return this.mergeInto((Table)table, Arrays.asList(field1));
    }

    @Deprecated(forRemoval=true, since="3.14")
    public final <R extends Record, T1, T2> MergeImpl mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2) {
        return this.mergeInto((Table)table, Arrays.asList(field1, field2));
    }

    @Deprecated(forRemoval=true, since="3.14")
    public final <R extends Record, T1, T2, T3> MergeImpl mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3) {
        return this.mergeInto((Table)table, Arrays.asList(field1, field2, field3));
    }

    @Deprecated(forRemoval=true, since="3.14")
    public final <R extends Record, T1, T2, T3, T4> MergeImpl mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4) {
        return this.mergeInto((Table)table, Arrays.asList(field1, field2, field3, field4));
    }

    @Deprecated(forRemoval=true, since="3.14")
    public final <R extends Record, T1, T2, T3, T4, T5> MergeImpl mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5) {
        return this.mergeInto((Table)table, Arrays.asList(field1, field2, field3, field4, field5));
    }

    @Deprecated(forRemoval=true, since="3.14")
    public final <R extends Record, T1, T2, T3, T4, T5, T6> MergeImpl mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6) {
        return this.mergeInto((Table)table, Arrays.asList(field1, field2, field3, field4, field5, field6));
    }

    @Deprecated(forRemoval=true, since="3.14")
    public final <R extends Record, T1, T2, T3, T4, T5, T6, T7> MergeImpl mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7) {
        return this.mergeInto((Table)table, Arrays.asList(field1, field2, field3, field4, field5, field6, field7));
    }

    @Deprecated(forRemoval=true, since="3.14")
    public final <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8> MergeImpl mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8) {
        return this.mergeInto((Table)table, Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8));
    }

    @Deprecated(forRemoval=true, since="3.14")
    public final <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9> MergeImpl mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9) {
        return this.mergeInto((Table)table, Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8, field9));
    }

    @Deprecated(forRemoval=true, since="3.14")
    public final <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> MergeImpl mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10) {
        return this.mergeInto((Table)table, Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10));
    }

    @Deprecated(forRemoval=true, since="3.14")
    public final <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> MergeImpl mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11) {
        return this.mergeInto((Table)table, Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11));
    }

    @Deprecated(forRemoval=true, since="3.14")
    public final <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> MergeImpl mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12) {
        return this.mergeInto((Table)table, Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12));
    }

    @Deprecated(forRemoval=true, since="3.14")
    public final <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> MergeImpl mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13) {
        return this.mergeInto((Table)table, Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13));
    }

    @Deprecated(forRemoval=true, since="3.14")
    public final <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> MergeImpl mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14) {
        return this.mergeInto((Table)table, Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14));
    }

    @Deprecated(forRemoval=true, since="3.14")
    public final <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> MergeImpl mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15) {
        return this.mergeInto((Table)table, Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15));
    }

    @Deprecated(forRemoval=true, since="3.14")
    public final <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> MergeImpl mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16) {
        return this.mergeInto((Table)table, Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16));
    }

    @Deprecated(forRemoval=true, since="3.14")
    public final <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> MergeImpl mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17) {
        return this.mergeInto((Table)table, Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17));
    }

    @Deprecated(forRemoval=true, since="3.14")
    public final <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> MergeImpl mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18) {
        return this.mergeInto((Table)table, Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18));
    }

    @Deprecated(forRemoval=true, since="3.14")
    public final <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> MergeImpl mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19) {
        return this.mergeInto((Table)table, Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19));
    }

    @Deprecated(forRemoval=true, since="3.14")
    public final <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> MergeImpl mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20) {
        return this.mergeInto((Table)table, Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20));
    }

    @Deprecated(forRemoval=true, since="3.14")
    public final <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> MergeImpl mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20, Field<T21> field21) {
        return this.mergeInto((Table)table, Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21));
    }

    @Deprecated(forRemoval=true, since="3.14")
    public final <R extends Record, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> MergeImpl mergeInto(Table<R> table, Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20, Field<T21> field21, Field<T22> field22) {
        return this.mergeInto((Table)table, Arrays.asList(field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21, field22));
    }

    public final <R extends Record> MergeImpl mergeInto(Table<R> table, Field<?> ... fields) {
        return this.mergeInto((Table)table, Arrays.asList(fields));
    }

    public final <R extends Record> MergeImpl mergeInto(Table<R> table, Collection<? extends Field<?>> fields) {
        return new MergeImpl(this.configuration, this, table, fields);
    }

    public final <R extends Record> DeleteImpl delete(Table<R> table) {
        return new DeleteImpl<R>(this.configuration, this, table);
    }

    @Override
    public final QOM.UnmodifiableList<? extends CommonTableExpression<?>> $commonTableExpressions() {
        return QOM.unmodifiable(this.ctes);
    }

    @Override
    public final WithImpl $commonTableExpressions(QOM.UnmodifiableList<? extends CommonTableExpression<?>> c) {
        return new WithImpl(this.configuration, this.recursive).with(c);
    }

    @Override
    public final boolean $recursive() {
        return this.recursive;
    }

    @Override
    public final WithImpl $recursive(boolean r) {
        return new WithImpl(this.configuration, r).with((Collection)this.ctes);
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        return QOM.traverse(traverser, this, this.ctes);
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.ctes, this.recursive, (c, r) -> new WithImpl(this.configuration, (boolean)r).with((Collection)c), replacer);
    }
}

