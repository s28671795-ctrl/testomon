/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Collection;
import java.util.Set;
import org.jooq.Clause;
import org.jooq.Condition;
import org.jooq.ConstraintForeignKeyOnStep;
import org.jooq.ConstraintForeignKeyReferencesStep1;
import org.jooq.ConstraintForeignKeyReferencesStep10;
import org.jooq.ConstraintForeignKeyReferencesStep11;
import org.jooq.ConstraintForeignKeyReferencesStep12;
import org.jooq.ConstraintForeignKeyReferencesStep13;
import org.jooq.ConstraintForeignKeyReferencesStep14;
import org.jooq.ConstraintForeignKeyReferencesStep15;
import org.jooq.ConstraintForeignKeyReferencesStep16;
import org.jooq.ConstraintForeignKeyReferencesStep17;
import org.jooq.ConstraintForeignKeyReferencesStep18;
import org.jooq.ConstraintForeignKeyReferencesStep19;
import org.jooq.ConstraintForeignKeyReferencesStep2;
import org.jooq.ConstraintForeignKeyReferencesStep20;
import org.jooq.ConstraintForeignKeyReferencesStep21;
import org.jooq.ConstraintForeignKeyReferencesStep22;
import org.jooq.ConstraintForeignKeyReferencesStep3;
import org.jooq.ConstraintForeignKeyReferencesStep4;
import org.jooq.ConstraintForeignKeyReferencesStep5;
import org.jooq.ConstraintForeignKeyReferencesStep6;
import org.jooq.ConstraintForeignKeyReferencesStep7;
import org.jooq.ConstraintForeignKeyReferencesStep8;
import org.jooq.ConstraintForeignKeyReferencesStep9;
import org.jooq.ConstraintForeignKeyReferencesStepN;
import org.jooq.ConstraintTypeStep;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.Keyword;
import org.jooq.Name;
import org.jooq.Pro;
import org.jooq.QueryPart;
import org.jooq.SQLDialect;
import org.jooq.Table;
import org.jooq.impl.AbstractName;
import org.jooq.impl.AbstractNamed;
import org.jooq.impl.DSL;
import org.jooq.impl.Keywords;
import org.jooq.impl.QOM;
import org.jooq.impl.QueryPartListView;
import org.jooq.impl.Tools;

final class ConstraintImpl
extends AbstractNamed
implements QOM.UNotYetImplemented,
ConstraintTypeStep,
ConstraintForeignKeyOnStep,
ConstraintForeignKeyReferencesStepN,
ConstraintForeignKeyReferencesStep1,
ConstraintForeignKeyReferencesStep2,
ConstraintForeignKeyReferencesStep3,
ConstraintForeignKeyReferencesStep4,
ConstraintForeignKeyReferencesStep5,
ConstraintForeignKeyReferencesStep6,
ConstraintForeignKeyReferencesStep7,
ConstraintForeignKeyReferencesStep8,
ConstraintForeignKeyReferencesStep9,
ConstraintForeignKeyReferencesStep10,
ConstraintForeignKeyReferencesStep11,
ConstraintForeignKeyReferencesStep12,
ConstraintForeignKeyReferencesStep13,
ConstraintForeignKeyReferencesStep14,
ConstraintForeignKeyReferencesStep15,
ConstraintForeignKeyReferencesStep16,
ConstraintForeignKeyReferencesStep17,
ConstraintForeignKeyReferencesStep18,
ConstraintForeignKeyReferencesStep19,
ConstraintForeignKeyReferencesStep20,
ConstraintForeignKeyReferencesStep21,
ConstraintForeignKeyReferencesStep22 {
    private static final Clause[] CLAUSES = new Clause[]{Clause.CONSTRAINT};
    private static final Set<SQLDialect> NO_SUPPORT_PK = SQLDialect.supportedBy(SQLDialect.BIGQUERY);
    private static final Set<SQLDialect> NO_SUPPORT_UK = SQLDialect.supportedBy(SQLDialect.BIGQUERY, SQLDialect.IGNITE);
    private static final Set<SQLDialect> NO_SUPPORT_FK = SQLDialect.supportedBy(SQLDialect.BIGQUERY, SQLDialect.IGNITE);
    private static final Set<SQLDialect> NO_SUPPORT_CHECK = SQLDialect.supportedBy(SQLDialect.BIGQUERY, SQLDialect.IGNITE);
    @Pro
    private static final Set<SQLDialect> NO_SUPPORT_ON_DELETE_DEFAULT = SQLDialect.supportedBy(SQLDialect.HANA, SQLDialect.INFORMIX, SQLDialect.ORACLE);
    @Pro
    private static final Set<SQLDialect> NO_SUPPORT_ON_UPDATE_DEFAULT = SQLDialect.supportedBy(SQLDialect.HANA, SQLDialect.INFORMIX, SQLDialect.ORACLE);
    private Field<?>[] unique;
    private Field<?>[] primaryKey;
    private Field<?>[] foreignKey;
    private Table<?> referencesTable;
    private Field<?>[] references;
    private Action onDelete;
    private Action onUpdate;
    private Condition check;
    private boolean enforced = true;

    ConstraintImpl() {
        this(null);
    }

    ConstraintImpl(Name name) {
        super(name, null);
    }

    final Field<?>[] $unique() {
        return this.unique;
    }

    final Field<?>[] $primaryKey() {
        return this.primaryKey;
    }

    final Field<?>[] $foreignKey() {
        return this.foreignKey;
    }

    final Table<?> $referencesTable() {
        return this.referencesTable;
    }

    final Field<?>[] $references() {
        return this.references;
    }

    final Action $onDelete() {
        return this.onDelete;
    }

    final Action $onUpdate() {
        return this.onUpdate;
    }

    final Condition $check() {
        return this.check;
    }

    final boolean $enforced() {
        return this.enforced;
    }

    @Override
    public final Clause[] clauses(Context<?> ctx) {
        return CLAUSES;
    }

    @Override
    public final void accept(Context<?> ctx) {
        boolean named;
        boolean bl = named = !this.getQualifiedName().equals(AbstractName.NO_NAME);
        if (named && Boolean.TRUE.equals(ctx.data(Tools.BooleanDataKey.DATA_CONSTRAINT_REFERENCE))) {
            ctx.visit(this.getQualifiedName());
        } else {
            boolean qualify = ctx.qualify();
            if (named && ctx.family() != SQLDialect.INFORMIX) {
                ctx.visit(Keywords.K_CONSTRAINT).sql(' ').visit(this.getUnqualifiedName()).formatIndentStart().formatSeparator();
            }
            if (this.unique != null) {
                ctx.visit(Keywords.K_UNIQUE).sql(" (").visit(QueryPartListView.wrap((QueryPart[])this.unique).qualify(false)).sql(')');
                if (ctx.family() == SQLDialect.SQLDATAWAREHOUSE) {
                    ctx.sql(' ').visit(Keywords.K_NOT_ENFORCED);
                }
            } else if (this.primaryKey != null) {
                ctx.visit(Keywords.K_PRIMARY_KEY);
                if (ctx.family() == SQLDialect.SQLDATAWAREHOUSE) {
                    ctx.sql(' ').visit(Keywords.K_NONCLUSTERED);
                }
                ctx.sql(" (").visit(QueryPartListView.wrap((QueryPart[])this.primaryKey).qualify(false)).sql(')');
                if (ctx.family() == SQLDialect.SQLDATAWAREHOUSE) {
                    ctx.sql(' ').visit(Keywords.K_NOT_ENFORCED);
                }
            } else if (this.foreignKey != null) {
                ctx.visit(Keywords.K_FOREIGN_KEY).sql(" (").visit(QueryPartListView.wrap((QueryPart[])this.foreignKey).qualify(false)).sql(')').formatSeparator().visit(Keywords.K_REFERENCES).sql(' ').visit(this.referencesTable);
                if (this.references.length > 0) {
                    ctx.sql(" (").visit(QueryPartListView.wrap((QueryPart[])this.references).qualify(false)).sql(')');
                }
                if (!(this.onDelete == null || this.onDelete == Action.NO_ACTION && NO_SUPPORT_ON_DELETE_DEFAULT.contains((Object)ctx.dialect()))) {
                    ctx.sql(' ').visit(Keywords.K_ON_DELETE).sql(' ').visit(this.onDelete.keyword);
                }
                if (!(this.onUpdate == null || this.onUpdate == Action.NO_ACTION && NO_SUPPORT_ON_UPDATE_DEFAULT.contains((Object)ctx.dialect()))) {
                    ctx.sql(' ').visit(Keywords.K_ON_UPDATE).sql(' ').visit(this.onUpdate.keyword);
                }
            } else if (this.check != null) {
                ctx.visit(Keywords.K_CHECK).sql(" (").qualify(false, c -> c.visit(this.check)).sql(')');
            }
            if (!this.enforced) {
                ConstraintImpl.acceptEnforced(ctx, this.enforced);
            }
            if (named) {
                if (ctx.family() == SQLDialect.INFORMIX) {
                    ctx.sql(' ').visit(Keywords.K_CONSTRAINT).sql(' ').visit(this.getUnqualifiedName());
                } else {
                    ctx.formatIndentEnd();
                }
            }
        }
    }

    static void acceptEnforced(Context<?> ctx, boolean enforced) {
        switch (ctx.family()) {
            case ORACLE: {
                if (enforced) {
                    ctx.sql(' ').visit(Keywords.K_ENABLE);
                    break;
                }
                ctx.sql(' ').visit(Keywords.K_DISABLE);
                break;
            }
            default: {
                if (enforced) {
                    ctx.sql(' ').visit(Keywords.K_ENFORCED);
                    break;
                }
                ctx.sql(' ').visit(Keywords.K_NOT).sql(' ').visit(Keywords.K_ENFORCED);
            }
        }
    }

    @Override
    public final ConstraintImpl unique(String ... fields) {
        return this.unique((Field[])Tools.fieldsByName(fields));
    }

    @Override
    public final ConstraintImpl unique(Name ... fields) {
        return this.unique((Field[])Tools.fieldsByName(fields));
    }

    @Override
    public final ConstraintImpl unique(Field<?> ... fields) {
        this.unique = fields;
        return this;
    }

    @Override
    public final ConstraintImpl unique(Collection<? extends Field<?>> fields) {
        return this.unique((Field[])fields.toArray(Tools.EMPTY_FIELD));
    }

    @Override
    public final ConstraintImpl check(Condition condition) {
        this.check = condition;
        return this;
    }

    @Override
    public final ConstraintImpl primaryKey(String ... fields) {
        return this.primaryKey((Field[])Tools.fieldsByName(fields));
    }

    @Override
    public final ConstraintImpl primaryKey(Name ... fields) {
        return this.primaryKey((Field[])Tools.fieldsByName(fields));
    }

    @Override
    public final ConstraintImpl primaryKey(Field<?> ... fields) {
        this.primaryKey = fields;
        return this;
    }

    @Override
    public final ConstraintImpl primaryKey(Collection<? extends Field<?>> fields) {
        return this.primaryKey((Field[])fields.toArray(Tools.EMPTY_FIELD));
    }

    @Override
    public final ConstraintImpl foreignKey(String ... fields) {
        return this.foreignKey((Field[])Tools.fieldsByName(fields));
    }

    @Override
    public final ConstraintImpl foreignKey(Name ... fields) {
        return this.foreignKey((Field[])Tools.fieldsByName(fields));
    }

    @Override
    public final ConstraintImpl foreignKey(Field<?> ... fields) {
        this.foreignKey = fields;
        return this;
    }

    @Override
    public final ConstraintImpl foreignKey(Collection<? extends Field<?>> fields) {
        return this.foreignKey((Field[])fields.toArray(Tools.EMPTY_FIELD));
    }

    @Override
    public final ConstraintImpl references(String table) {
        return this.references((Table)DSL.table(DSL.name(table)), (Field[])Tools.EMPTY_FIELD);
    }

    @Override
    public final ConstraintImpl references(String table, String ... fields) {
        return this.references((Table)DSL.table(DSL.name(table)), (Field[])Tools.fieldsByName(fields));
    }

    @Override
    public final ConstraintImpl references(String table, Collection<? extends String> fields) {
        return this.references(table, fields.toArray(Tools.EMPTY_STRING));
    }

    @Override
    public final ConstraintImpl references(Name table) {
        return this.references((Table)DSL.table(table), (Field[])Tools.EMPTY_FIELD);
    }

    @Override
    public final ConstraintImpl references(Name table, Name ... fields) {
        return this.references((Table)DSL.table(table), (Field[])Tools.fieldsByName(fields));
    }

    @Override
    public final ConstraintImpl references(Name table, Collection<? extends Name> fields) {
        return this.references(table, fields.toArray(Tools.EMPTY_NAME));
    }

    public final ConstraintImpl references(Table table) {
        return this.references(table, (Field[])Tools.EMPTY_FIELD);
    }

    @Override
    public final ConstraintImpl references(Table<?> table, Field<?> ... fields) {
        this.referencesTable = table;
        this.references = fields;
        return this;
    }

    @Override
    public final ConstraintImpl references(Table<?> table, Collection<? extends Field<?>> fields) {
        return this.references((Table)table, (Field[])fields.toArray(Tools.EMPTY_FIELD));
    }

    @Override
    public final ConstraintImpl onDeleteNoAction() {
        this.onDelete = Action.NO_ACTION;
        return this;
    }

    @Override
    public final ConstraintImpl onDeleteRestrict() {
        this.onDelete = Action.RESTRICT;
        return this;
    }

    @Override
    public final ConstraintImpl onDeleteCascade() {
        this.onDelete = Action.CASCADE;
        return this;
    }

    @Override
    public final ConstraintImpl onDeleteSetNull() {
        this.onDelete = Action.SET_NULL;
        return this;
    }

    @Override
    public final ConstraintImpl onDeleteSetDefault() {
        this.onDelete = Action.SET_DEFAULT;
        return this;
    }

    @Override
    public final ConstraintImpl onUpdateNoAction() {
        this.onUpdate = Action.NO_ACTION;
        return this;
    }

    @Override
    public final ConstraintImpl onUpdateRestrict() {
        this.onUpdate = Action.RESTRICT;
        return this;
    }

    @Override
    public final ConstraintImpl onUpdateCascade() {
        this.onUpdate = Action.CASCADE;
        return this;
    }

    @Override
    public final ConstraintImpl onUpdateSetNull() {
        this.onUpdate = Action.SET_NULL;
        return this;
    }

    @Override
    public final ConstraintImpl onUpdateSetDefault() {
        this.onUpdate = Action.SET_DEFAULT;
        return this;
    }

    public final <T1> ConstraintImpl foreignKey(Field<T1> field1) {
        return this.foreignKey(new Field[]{field1});
    }

    public final <T1, T2> ConstraintImpl foreignKey(Field<T1> field1, Field<T2> field2) {
        return this.foreignKey(new Field[]{field1, field2});
    }

    public final <T1, T2, T3> ConstraintImpl foreignKey(Field<T1> field1, Field<T2> field2, Field<T3> field3) {
        return this.foreignKey(new Field[]{field1, field2, field3});
    }

    public final <T1, T2, T3, T4> ConstraintImpl foreignKey(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4) {
        return this.foreignKey(new Field[]{field1, field2, field3, field4});
    }

    public final <T1, T2, T3, T4, T5> ConstraintImpl foreignKey(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5) {
        return this.foreignKey(new Field[]{field1, field2, field3, field4, field5});
    }

    public final <T1, T2, T3, T4, T5, T6> ConstraintImpl foreignKey(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6) {
        return this.foreignKey(new Field[]{field1, field2, field3, field4, field5, field6});
    }

    public final <T1, T2, T3, T4, T5, T6, T7> ConstraintImpl foreignKey(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7) {
        return this.foreignKey(new Field[]{field1, field2, field3, field4, field5, field6, field7});
    }

    public final <T1, T2, T3, T4, T5, T6, T7, T8> ConstraintImpl foreignKey(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8) {
        return this.foreignKey(new Field[]{field1, field2, field3, field4, field5, field6, field7, field8});
    }

    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9> ConstraintImpl foreignKey(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9) {
        return this.foreignKey(new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9});
    }

    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> ConstraintImpl foreignKey(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10) {
        return this.foreignKey(new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10});
    }

    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> ConstraintImpl foreignKey(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11) {
        return this.foreignKey(new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11});
    }

    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> ConstraintImpl foreignKey(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12) {
        return this.foreignKey(new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12});
    }

    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> ConstraintImpl foreignKey(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13) {
        return this.foreignKey(new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13});
    }

    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> ConstraintImpl foreignKey(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14) {
        return this.foreignKey(new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14});
    }

    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> ConstraintImpl foreignKey(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15) {
        return this.foreignKey(new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15});
    }

    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> ConstraintImpl foreignKey(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16) {
        return this.foreignKey(new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16});
    }

    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> ConstraintImpl foreignKey(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17) {
        return this.foreignKey(new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17});
    }

    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> ConstraintImpl foreignKey(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18) {
        return this.foreignKey(new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18});
    }

    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> ConstraintImpl foreignKey(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19) {
        return this.foreignKey(new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19});
    }

    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> ConstraintImpl foreignKey(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20) {
        return this.foreignKey(new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20});
    }

    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> ConstraintImpl foreignKey(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20, Field<T21> field21) {
        return this.foreignKey(new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21});
    }

    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> ConstraintImpl foreignKey(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20, Field<T21> field21, Field<T22> field22) {
        return this.foreignKey(new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21, field22});
    }

    public final ConstraintImpl foreignKey(Name field1) {
        return this.foreignKey(new Name[]{field1});
    }

    public final ConstraintImpl foreignKey(Name field1, Name field2) {
        return this.foreignKey(new Name[]{field1, field2});
    }

    public final ConstraintImpl foreignKey(Name field1, Name field2, Name field3) {
        return this.foreignKey(new Name[]{field1, field2, field3});
    }

    public final ConstraintImpl foreignKey(Name field1, Name field2, Name field3, Name field4) {
        return this.foreignKey(new Name[]{field1, field2, field3, field4});
    }

    public final ConstraintImpl foreignKey(Name field1, Name field2, Name field3, Name field4, Name field5) {
        return this.foreignKey(new Name[]{field1, field2, field3, field4, field5});
    }

    public final ConstraintImpl foreignKey(Name field1, Name field2, Name field3, Name field4, Name field5, Name field6) {
        return this.foreignKey(new Name[]{field1, field2, field3, field4, field5, field6});
    }

    public final ConstraintImpl foreignKey(Name field1, Name field2, Name field3, Name field4, Name field5, Name field6, Name field7) {
        return this.foreignKey(new Name[]{field1, field2, field3, field4, field5, field6, field7});
    }

    public final ConstraintImpl foreignKey(Name field1, Name field2, Name field3, Name field4, Name field5, Name field6, Name field7, Name field8) {
        return this.foreignKey(new Name[]{field1, field2, field3, field4, field5, field6, field7, field8});
    }

    public final ConstraintImpl foreignKey(Name field1, Name field2, Name field3, Name field4, Name field5, Name field6, Name field7, Name field8, Name field9) {
        return this.foreignKey(new Name[]{field1, field2, field3, field4, field5, field6, field7, field8, field9});
    }

    public final ConstraintImpl foreignKey(Name field1, Name field2, Name field3, Name field4, Name field5, Name field6, Name field7, Name field8, Name field9, Name field10) {
        return this.foreignKey(new Name[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10});
    }

    public final ConstraintImpl foreignKey(Name field1, Name field2, Name field3, Name field4, Name field5, Name field6, Name field7, Name field8, Name field9, Name field10, Name field11) {
        return this.foreignKey(new Name[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11});
    }

    public final ConstraintImpl foreignKey(Name field1, Name field2, Name field3, Name field4, Name field5, Name field6, Name field7, Name field8, Name field9, Name field10, Name field11, Name field12) {
        return this.foreignKey(new Name[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12});
    }

    public final ConstraintImpl foreignKey(Name field1, Name field2, Name field3, Name field4, Name field5, Name field6, Name field7, Name field8, Name field9, Name field10, Name field11, Name field12, Name field13) {
        return this.foreignKey(new Name[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13});
    }

    public final ConstraintImpl foreignKey(Name field1, Name field2, Name field3, Name field4, Name field5, Name field6, Name field7, Name field8, Name field9, Name field10, Name field11, Name field12, Name field13, Name field14) {
        return this.foreignKey(new Name[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14});
    }

    public final ConstraintImpl foreignKey(Name field1, Name field2, Name field3, Name field4, Name field5, Name field6, Name field7, Name field8, Name field9, Name field10, Name field11, Name field12, Name field13, Name field14, Name field15) {
        return this.foreignKey(new Name[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15});
    }

    public final ConstraintImpl foreignKey(Name field1, Name field2, Name field3, Name field4, Name field5, Name field6, Name field7, Name field8, Name field9, Name field10, Name field11, Name field12, Name field13, Name field14, Name field15, Name field16) {
        return this.foreignKey(new Name[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16});
    }

    public final ConstraintImpl foreignKey(Name field1, Name field2, Name field3, Name field4, Name field5, Name field6, Name field7, Name field8, Name field9, Name field10, Name field11, Name field12, Name field13, Name field14, Name field15, Name field16, Name field17) {
        return this.foreignKey(new Name[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17});
    }

    public final ConstraintImpl foreignKey(Name field1, Name field2, Name field3, Name field4, Name field5, Name field6, Name field7, Name field8, Name field9, Name field10, Name field11, Name field12, Name field13, Name field14, Name field15, Name field16, Name field17, Name field18) {
        return this.foreignKey(new Name[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18});
    }

    public final ConstraintImpl foreignKey(Name field1, Name field2, Name field3, Name field4, Name field5, Name field6, Name field7, Name field8, Name field9, Name field10, Name field11, Name field12, Name field13, Name field14, Name field15, Name field16, Name field17, Name field18, Name field19) {
        return this.foreignKey(new Name[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19});
    }

    public final ConstraintImpl foreignKey(Name field1, Name field2, Name field3, Name field4, Name field5, Name field6, Name field7, Name field8, Name field9, Name field10, Name field11, Name field12, Name field13, Name field14, Name field15, Name field16, Name field17, Name field18, Name field19, Name field20) {
        return this.foreignKey(new Name[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20});
    }

    public final ConstraintImpl foreignKey(Name field1, Name field2, Name field3, Name field4, Name field5, Name field6, Name field7, Name field8, Name field9, Name field10, Name field11, Name field12, Name field13, Name field14, Name field15, Name field16, Name field17, Name field18, Name field19, Name field20, Name field21) {
        return this.foreignKey(new Name[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21});
    }

    public final ConstraintImpl foreignKey(Name field1, Name field2, Name field3, Name field4, Name field5, Name field6, Name field7, Name field8, Name field9, Name field10, Name field11, Name field12, Name field13, Name field14, Name field15, Name field16, Name field17, Name field18, Name field19, Name field20, Name field21, Name field22) {
        return this.foreignKey(new Name[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21, field22});
    }

    public final ConstraintImpl foreignKey(String field1) {
        return this.foreignKey(new String[]{field1});
    }

    public final ConstraintImpl foreignKey(String field1, String field2) {
        return this.foreignKey(new String[]{field1, field2});
    }

    public final ConstraintImpl foreignKey(String field1, String field2, String field3) {
        return this.foreignKey(new String[]{field1, field2, field3});
    }

    public final ConstraintImpl foreignKey(String field1, String field2, String field3, String field4) {
        return this.foreignKey(new String[]{field1, field2, field3, field4});
    }

    public final ConstraintImpl foreignKey(String field1, String field2, String field3, String field4, String field5) {
        return this.foreignKey(new String[]{field1, field2, field3, field4, field5});
    }

    public final ConstraintImpl foreignKey(String field1, String field2, String field3, String field4, String field5, String field6) {
        return this.foreignKey(new String[]{field1, field2, field3, field4, field5, field6});
    }

    public final ConstraintImpl foreignKey(String field1, String field2, String field3, String field4, String field5, String field6, String field7) {
        return this.foreignKey(new String[]{field1, field2, field3, field4, field5, field6, field7});
    }

    public final ConstraintImpl foreignKey(String field1, String field2, String field3, String field4, String field5, String field6, String field7, String field8) {
        return this.foreignKey(new String[]{field1, field2, field3, field4, field5, field6, field7, field8});
    }

    public final ConstraintImpl foreignKey(String field1, String field2, String field3, String field4, String field5, String field6, String field7, String field8, String field9) {
        return this.foreignKey(new String[]{field1, field2, field3, field4, field5, field6, field7, field8, field9});
    }

    public final ConstraintImpl foreignKey(String field1, String field2, String field3, String field4, String field5, String field6, String field7, String field8, String field9, String field10) {
        return this.foreignKey(new String[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10});
    }

    public final ConstraintImpl foreignKey(String field1, String field2, String field3, String field4, String field5, String field6, String field7, String field8, String field9, String field10, String field11) {
        return this.foreignKey(new String[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11});
    }

    public final ConstraintImpl foreignKey(String field1, String field2, String field3, String field4, String field5, String field6, String field7, String field8, String field9, String field10, String field11, String field12) {
        return this.foreignKey(new String[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12});
    }

    public final ConstraintImpl foreignKey(String field1, String field2, String field3, String field4, String field5, String field6, String field7, String field8, String field9, String field10, String field11, String field12, String field13) {
        return this.foreignKey(new String[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13});
    }

    public final ConstraintImpl foreignKey(String field1, String field2, String field3, String field4, String field5, String field6, String field7, String field8, String field9, String field10, String field11, String field12, String field13, String field14) {
        return this.foreignKey(new String[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14});
    }

    public final ConstraintImpl foreignKey(String field1, String field2, String field3, String field4, String field5, String field6, String field7, String field8, String field9, String field10, String field11, String field12, String field13, String field14, String field15) {
        return this.foreignKey(new String[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15});
    }

    public final ConstraintImpl foreignKey(String field1, String field2, String field3, String field4, String field5, String field6, String field7, String field8, String field9, String field10, String field11, String field12, String field13, String field14, String field15, String field16) {
        return this.foreignKey(new String[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16});
    }

    public final ConstraintImpl foreignKey(String field1, String field2, String field3, String field4, String field5, String field6, String field7, String field8, String field9, String field10, String field11, String field12, String field13, String field14, String field15, String field16, String field17) {
        return this.foreignKey(new String[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17});
    }

    public final ConstraintImpl foreignKey(String field1, String field2, String field3, String field4, String field5, String field6, String field7, String field8, String field9, String field10, String field11, String field12, String field13, String field14, String field15, String field16, String field17, String field18) {
        return this.foreignKey(new String[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18});
    }

    public final ConstraintImpl foreignKey(String field1, String field2, String field3, String field4, String field5, String field6, String field7, String field8, String field9, String field10, String field11, String field12, String field13, String field14, String field15, String field16, String field17, String field18, String field19) {
        return this.foreignKey(new String[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19});
    }

    public final ConstraintImpl foreignKey(String field1, String field2, String field3, String field4, String field5, String field6, String field7, String field8, String field9, String field10, String field11, String field12, String field13, String field14, String field15, String field16, String field17, String field18, String field19, String field20) {
        return this.foreignKey(new String[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20});
    }

    public final ConstraintImpl foreignKey(String field1, String field2, String field3, String field4, String field5, String field6, String field7, String field8, String field9, String field10, String field11, String field12, String field13, String field14, String field15, String field16, String field17, String field18, String field19, String field20, String field21) {
        return this.foreignKey(new String[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21});
    }

    public final ConstraintImpl foreignKey(String field1, String field2, String field3, String field4, String field5, String field6, String field7, String field8, String field9, String field10, String field11, String field12, String field13, String field14, String field15, String field16, String field17, String field18, String field19, String field20, String field21, String field22) {
        return this.foreignKey(new String[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21, field22});
    }

    public final ConstraintImpl references(Table table, Field t1) {
        return this.references(table, new Field[]{t1});
    }

    public final ConstraintImpl references(Table table, Field t1, Field t2) {
        return this.references(table, new Field[]{t1, t2});
    }

    public final ConstraintImpl references(Table table, Field t1, Field t2, Field t3) {
        return this.references(table, new Field[]{t1, t2, t3});
    }

    public final ConstraintImpl references(Table table, Field t1, Field t2, Field t3, Field t4) {
        return this.references(table, new Field[]{t1, t2, t3, t4});
    }

    public final ConstraintImpl references(Table table, Field t1, Field t2, Field t3, Field t4, Field t5) {
        return this.references(table, new Field[]{t1, t2, t3, t4, t5});
    }

    public final ConstraintImpl references(Table table, Field t1, Field t2, Field t3, Field t4, Field t5, Field t6) {
        return this.references(table, new Field[]{t1, t2, t3, t4, t5, t6});
    }

    public final ConstraintImpl references(Table table, Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7) {
        return this.references(table, new Field[]{t1, t2, t3, t4, t5, t6, t7});
    }

    public final ConstraintImpl references(Table table, Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8) {
        return this.references(table, new Field[]{t1, t2, t3, t4, t5, t6, t7, t8});
    }

    public final ConstraintImpl references(Table table, Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9) {
        return this.references(table, new Field[]{t1, t2, t3, t4, t5, t6, t7, t8, t9});
    }

    public final ConstraintImpl references(Table table, Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10) {
        return this.references(table, new Field[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10});
    }

    public final ConstraintImpl references(Table table, Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11) {
        return this.references(table, new Field[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11});
    }

    public final ConstraintImpl references(Table table, Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12) {
        return this.references(table, new Field[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12});
    }

    public final ConstraintImpl references(Table table, Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13) {
        return this.references(table, new Field[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13});
    }

    public final ConstraintImpl references(Table table, Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14) {
        return this.references(table, new Field[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14});
    }

    public final ConstraintImpl references(Table table, Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14, Field t15) {
        return this.references(table, new Field[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15});
    }

    public final ConstraintImpl references(Table table, Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14, Field t15, Field t16) {
        return this.references(table, new Field[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16});
    }

    public final ConstraintImpl references(Table table, Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14, Field t15, Field t16, Field t17) {
        return this.references(table, new Field[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17});
    }

    public final ConstraintImpl references(Table table, Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14, Field t15, Field t16, Field t17, Field t18) {
        return this.references(table, new Field[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18});
    }

    public final ConstraintImpl references(Table table, Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14, Field t15, Field t16, Field t17, Field t18, Field t19) {
        return this.references(table, new Field[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19});
    }

    public final ConstraintImpl references(Table table, Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14, Field t15, Field t16, Field t17, Field t18, Field t19, Field t20) {
        return this.references(table, new Field[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20});
    }

    public final ConstraintImpl references(Table table, Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14, Field t15, Field t16, Field t17, Field t18, Field t19, Field t20, Field t21) {
        return this.references(table, new Field[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21});
    }

    public final ConstraintImpl references(Table table, Field t1, Field t2, Field t3, Field t4, Field t5, Field t6, Field t7, Field t8, Field t9, Field t10, Field t11, Field t12, Field t13, Field t14, Field t15, Field t16, Field t17, Field t18, Field t19, Field t20, Field t21, Field t22) {
        return this.references(table, new Field[]{t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12, t13, t14, t15, t16, t17, t18, t19, t20, t21, t22});
    }

    @Override
    public final ConstraintImpl references(Name table, Name field1) {
        return this.references(table, new Name[]{field1});
    }

    @Override
    public final ConstraintImpl references(Name table, Name field1, Name field2) {
        return this.references(table, new Name[]{field1, field2});
    }

    @Override
    public final ConstraintImpl references(Name table, Name field1, Name field2, Name field3) {
        return this.references(table, new Name[]{field1, field2, field3});
    }

    @Override
    public final ConstraintImpl references(Name table, Name field1, Name field2, Name field3, Name field4) {
        return this.references(table, new Name[]{field1, field2, field3, field4});
    }

    @Override
    public final ConstraintImpl references(Name table, Name field1, Name field2, Name field3, Name field4, Name field5) {
        return this.references(table, new Name[]{field1, field2, field3, field4, field5});
    }

    @Override
    public final ConstraintImpl references(Name table, Name field1, Name field2, Name field3, Name field4, Name field5, Name field6) {
        return this.references(table, new Name[]{field1, field2, field3, field4, field5, field6});
    }

    @Override
    public final ConstraintImpl references(Name table, Name field1, Name field2, Name field3, Name field4, Name field5, Name field6, Name field7) {
        return this.references(table, new Name[]{field1, field2, field3, field4, field5, field6, field7});
    }

    @Override
    public final ConstraintImpl references(Name table, Name field1, Name field2, Name field3, Name field4, Name field5, Name field6, Name field7, Name field8) {
        return this.references(table, new Name[]{field1, field2, field3, field4, field5, field6, field7, field8});
    }

    @Override
    public final ConstraintImpl references(Name table, Name field1, Name field2, Name field3, Name field4, Name field5, Name field6, Name field7, Name field8, Name field9) {
        return this.references(table, new Name[]{field1, field2, field3, field4, field5, field6, field7, field8, field9});
    }

    @Override
    public final ConstraintImpl references(Name table, Name field1, Name field2, Name field3, Name field4, Name field5, Name field6, Name field7, Name field8, Name field9, Name field10) {
        return this.references(table, new Name[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10});
    }

    @Override
    public final ConstraintImpl references(Name table, Name field1, Name field2, Name field3, Name field4, Name field5, Name field6, Name field7, Name field8, Name field9, Name field10, Name field11) {
        return this.references(table, new Name[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11});
    }

    @Override
    public final ConstraintImpl references(Name table, Name field1, Name field2, Name field3, Name field4, Name field5, Name field6, Name field7, Name field8, Name field9, Name field10, Name field11, Name field12) {
        return this.references(table, new Name[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12});
    }

    @Override
    public final ConstraintImpl references(Name table, Name field1, Name field2, Name field3, Name field4, Name field5, Name field6, Name field7, Name field8, Name field9, Name field10, Name field11, Name field12, Name field13) {
        return this.references(table, new Name[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13});
    }

    @Override
    public final ConstraintImpl references(Name table, Name field1, Name field2, Name field3, Name field4, Name field5, Name field6, Name field7, Name field8, Name field9, Name field10, Name field11, Name field12, Name field13, Name field14) {
        return this.references(table, new Name[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14});
    }

    @Override
    public final ConstraintImpl references(Name table, Name field1, Name field2, Name field3, Name field4, Name field5, Name field6, Name field7, Name field8, Name field9, Name field10, Name field11, Name field12, Name field13, Name field14, Name field15) {
        return this.references(table, new Name[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15});
    }

    @Override
    public final ConstraintImpl references(Name table, Name field1, Name field2, Name field3, Name field4, Name field5, Name field6, Name field7, Name field8, Name field9, Name field10, Name field11, Name field12, Name field13, Name field14, Name field15, Name field16) {
        return this.references(table, new Name[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16});
    }

    @Override
    public final ConstraintImpl references(Name table, Name field1, Name field2, Name field3, Name field4, Name field5, Name field6, Name field7, Name field8, Name field9, Name field10, Name field11, Name field12, Name field13, Name field14, Name field15, Name field16, Name field17) {
        return this.references(table, new Name[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17});
    }

    @Override
    public final ConstraintImpl references(Name table, Name field1, Name field2, Name field3, Name field4, Name field5, Name field6, Name field7, Name field8, Name field9, Name field10, Name field11, Name field12, Name field13, Name field14, Name field15, Name field16, Name field17, Name field18) {
        return this.references(table, new Name[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18});
    }

    @Override
    public final ConstraintImpl references(Name table, Name field1, Name field2, Name field3, Name field4, Name field5, Name field6, Name field7, Name field8, Name field9, Name field10, Name field11, Name field12, Name field13, Name field14, Name field15, Name field16, Name field17, Name field18, Name field19) {
        return this.references(table, new Name[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19});
    }

    @Override
    public final ConstraintImpl references(Name table, Name field1, Name field2, Name field3, Name field4, Name field5, Name field6, Name field7, Name field8, Name field9, Name field10, Name field11, Name field12, Name field13, Name field14, Name field15, Name field16, Name field17, Name field18, Name field19, Name field20) {
        return this.references(table, new Name[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20});
    }

    @Override
    public final ConstraintImpl references(Name table, Name field1, Name field2, Name field3, Name field4, Name field5, Name field6, Name field7, Name field8, Name field9, Name field10, Name field11, Name field12, Name field13, Name field14, Name field15, Name field16, Name field17, Name field18, Name field19, Name field20, Name field21) {
        return this.references(table, new Name[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21});
    }

    @Override
    public final ConstraintImpl references(Name table, Name field1, Name field2, Name field3, Name field4, Name field5, Name field6, Name field7, Name field8, Name field9, Name field10, Name field11, Name field12, Name field13, Name field14, Name field15, Name field16, Name field17, Name field18, Name field19, Name field20, Name field21, Name field22) {
        return this.references(table, new Name[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21, field22});
    }

    @Override
    public final ConstraintImpl references(String table, String field1) {
        return this.references(table, new String[]{field1});
    }

    @Override
    public final ConstraintImpl references(String table, String field1, String field2) {
        return this.references(table, new String[]{field1, field2});
    }

    @Override
    public final ConstraintImpl references(String table, String field1, String field2, String field3) {
        return this.references(table, new String[]{field1, field2, field3});
    }

    @Override
    public final ConstraintImpl references(String table, String field1, String field2, String field3, String field4) {
        return this.references(table, new String[]{field1, field2, field3, field4});
    }

    @Override
    public final ConstraintImpl references(String table, String field1, String field2, String field3, String field4, String field5) {
        return this.references(table, new String[]{field1, field2, field3, field4, field5});
    }

    @Override
    public final ConstraintImpl references(String table, String field1, String field2, String field3, String field4, String field5, String field6) {
        return this.references(table, new String[]{field1, field2, field3, field4, field5, field6});
    }

    @Override
    public final ConstraintImpl references(String table, String field1, String field2, String field3, String field4, String field5, String field6, String field7) {
        return this.references(table, new String[]{field1, field2, field3, field4, field5, field6, field7});
    }

    @Override
    public final ConstraintImpl references(String table, String field1, String field2, String field3, String field4, String field5, String field6, String field7, String field8) {
        return this.references(table, new String[]{field1, field2, field3, field4, field5, field6, field7, field8});
    }

    @Override
    public final ConstraintImpl references(String table, String field1, String field2, String field3, String field4, String field5, String field6, String field7, String field8, String field9) {
        return this.references(table, new String[]{field1, field2, field3, field4, field5, field6, field7, field8, field9});
    }

    @Override
    public final ConstraintImpl references(String table, String field1, String field2, String field3, String field4, String field5, String field6, String field7, String field8, String field9, String field10) {
        return this.references(table, new String[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10});
    }

    @Override
    public final ConstraintImpl references(String table, String field1, String field2, String field3, String field4, String field5, String field6, String field7, String field8, String field9, String field10, String field11) {
        return this.references(table, new String[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11});
    }

    @Override
    public final ConstraintImpl references(String table, String field1, String field2, String field3, String field4, String field5, String field6, String field7, String field8, String field9, String field10, String field11, String field12) {
        return this.references(table, new String[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12});
    }

    @Override
    public final ConstraintImpl references(String table, String field1, String field2, String field3, String field4, String field5, String field6, String field7, String field8, String field9, String field10, String field11, String field12, String field13) {
        return this.references(table, new String[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13});
    }

    @Override
    public final ConstraintImpl references(String table, String field1, String field2, String field3, String field4, String field5, String field6, String field7, String field8, String field9, String field10, String field11, String field12, String field13, String field14) {
        return this.references(table, new String[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14});
    }

    @Override
    public final ConstraintImpl references(String table, String field1, String field2, String field3, String field4, String field5, String field6, String field7, String field8, String field9, String field10, String field11, String field12, String field13, String field14, String field15) {
        return this.references(table, new String[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15});
    }

    @Override
    public final ConstraintImpl references(String table, String field1, String field2, String field3, String field4, String field5, String field6, String field7, String field8, String field9, String field10, String field11, String field12, String field13, String field14, String field15, String field16) {
        return this.references(table, new String[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16});
    }

    @Override
    public final ConstraintImpl references(String table, String field1, String field2, String field3, String field4, String field5, String field6, String field7, String field8, String field9, String field10, String field11, String field12, String field13, String field14, String field15, String field16, String field17) {
        return this.references(table, new String[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17});
    }

    @Override
    public final ConstraintImpl references(String table, String field1, String field2, String field3, String field4, String field5, String field6, String field7, String field8, String field9, String field10, String field11, String field12, String field13, String field14, String field15, String field16, String field17, String field18) {
        return this.references(table, new String[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18});
    }

    @Override
    public final ConstraintImpl references(String table, String field1, String field2, String field3, String field4, String field5, String field6, String field7, String field8, String field9, String field10, String field11, String field12, String field13, String field14, String field15, String field16, String field17, String field18, String field19) {
        return this.references(table, new String[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19});
    }

    @Override
    public final ConstraintImpl references(String table, String field1, String field2, String field3, String field4, String field5, String field6, String field7, String field8, String field9, String field10, String field11, String field12, String field13, String field14, String field15, String field16, String field17, String field18, String field19, String field20) {
        return this.references(table, new String[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20});
    }

    @Override
    public final ConstraintImpl references(String table, String field1, String field2, String field3, String field4, String field5, String field6, String field7, String field8, String field9, String field10, String field11, String field12, String field13, String field14, String field15, String field16, String field17, String field18, String field19, String field20, String field21) {
        return this.references(table, new String[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21});
    }

    @Override
    public final ConstraintImpl references(String table, String field1, String field2, String field3, String field4, String field5, String field6, String field7, String field8, String field9, String field10, String field11, String field12, String field13, String field14, String field15, String field16, String field17, String field18, String field19, String field20, String field21, String field22) {
        return this.references(table, new String[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21, field22});
    }

    @Override
    public final ConstraintImpl enforced() {
        this.enforced = true;
        return this;
    }

    @Override
    public final ConstraintImpl notEnforced() {
        this.enforced = false;
        return this;
    }

    final boolean matchingPrimaryKey(Field<?> identity) {
        return identity != null && this.primaryKey != null && this.primaryKey.length == 1 && this.primaryKey[0].getName().equals(identity.getName());
    }

    final boolean supported(Context<?> ctx) {
        return this.primaryKey != null && !NO_SUPPORT_PK.contains((Object)ctx.dialect()) || this.unique != null && !NO_SUPPORT_UK.contains((Object)ctx.dialect()) || this.references != null && !NO_SUPPORT_FK.contains((Object)ctx.dialect()) || this.check != null && !NO_SUPPORT_CHECK.contains((Object)ctx.dialect());
    }

    static enum Action {
        NO_ACTION("no action"),
        RESTRICT("restrict"),
        CASCADE("cascade"),
        SET_NULL("set null"),
        SET_DEFAULT("set default");

        Keyword keyword;

        private Action(String sql) {
            this.keyword = DSL.keyword(sql);
        }
    }
}

