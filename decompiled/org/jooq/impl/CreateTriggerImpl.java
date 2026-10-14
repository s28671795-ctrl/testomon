/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.Context;
import org.jooq.CreateTriggerActionStep;
import org.jooq.CreateTriggerEventOfStep;
import org.jooq.CreateTriggerEventOnStep;
import org.jooq.CreateTriggerEventOrStep;
import org.jooq.CreateTriggerEventStep;
import org.jooq.CreateTriggerForStep;
import org.jooq.CreateTriggerReferencingStep;
import org.jooq.CreateTriggerWhenStep;
import org.jooq.Field;
import org.jooq.Function16;
import org.jooq.LanguageContext;
import org.jooq.Name;
import org.jooq.Operator;
import org.jooq.Pro;
import org.jooq.QueryPart;
import org.jooq.Replacer;
import org.jooq.SQL;
import org.jooq.SQLDialect;
import org.jooq.Statement;
import org.jooq.Table;
import org.jooq.Traverser;
import org.jooq.impl.AbstractDDLQuery;
import org.jooq.impl.CreateProcedureImpl;
import org.jooq.impl.DSL;
import org.jooq.impl.Keywords;
import org.jooq.impl.QOM;
import org.jooq.impl.QualifiedImpl;
import org.jooq.impl.QueryPartCollectionView;
import org.jooq.impl.QueryPartList;
import org.jooq.impl.QueryPartListView;
import org.jooq.impl.Tools;

@Pro
final class CreateTriggerImpl
extends AbstractDDLQuery
implements QOM.CreateTrigger,
CreateTriggerEventStep,
CreateTriggerEventOfStep,
CreateTriggerEventOrStep,
CreateTriggerEventOnStep,
CreateTriggerReferencingStep,
CreateTriggerForStep,
CreateTriggerWhenStep,
CreateTriggerActionStep {
    final Name trigger;
    final boolean orReplace;
    boolean before;
    boolean after;
    boolean insteadOf;
    boolean insert;
    boolean update;
    boolean delete;
    QueryPartListView<? extends Field<?>> of;
    Table<?> on;
    Name referencingOldAs;
    Name referencingNewAs;
    boolean forEachRow;
    boolean forEachStatement;
    Condition when;
    Statement body;
    private static final Set<SQLDialect> NO_SUPPORT_REFERENCING = SQLDialect.supportedBy(SQLDialect.AURORA_MYSQL, SQLDialect.FIREBIRD, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.SQLSERVER);
    private static final Set<SQLDialect> NO_SUPPORT_WHEN = SQLDialect.supportedBy(SQLDialect.AURORA_MYSQL, SQLDialect.FIREBIRD, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.SQLSERVER);
    private static final Set<SQLDialect> NO_SUPPORT_QUALIFY_OF = SQLDialect.supportedBy(SQLDialect.HSQLDB, SQLDialect.ORACLE);
    private static final Set<SQLDialect> NO_SUPPORT_OR_REPLACE = SQLDialect.supportedBy(SQLDialect.AURORA_POSTGRES, SQLDialect.FIREBIRD, SQLDialect.MYSQL, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB);
    private static final Set<SQLDialect> NO_SUPPORT_FOR_EACH = SQLDialect.supportedBy(SQLDialect.FIREBIRD);
    private static final Set<SQLDialect> REQUIRE_AS = SQLDialect.supportedBy(SQLDialect.FIREBIRD);

    CreateTriggerImpl(Configuration configuration, Name trigger, boolean orReplace) {
        this(configuration, trigger, orReplace, false, false, false, false, false, false, null, null, null, null, false, false, null, null);
    }

    CreateTriggerImpl(Configuration configuration, Name trigger, boolean orReplace, boolean before, boolean after, boolean insteadOf, boolean insert, boolean update, boolean delete, Collection<? extends Field<?>> of, Table<?> on, Name referencingOldAs, Name referencingNewAs, boolean forEachRow, boolean forEachStatement, Condition when, Statement body2) {
        super(configuration);
        this.trigger = trigger;
        this.orReplace = orReplace;
        this.before = before;
        this.after = after;
        this.insteadOf = insteadOf;
        this.insert = insert;
        this.update = update;
        this.delete = delete;
        this.of = new QueryPartList((Iterable<Field<?>>)of);
        this.on = on;
        this.referencingOldAs = referencingOldAs;
        this.referencingNewAs = referencingNewAs;
        this.forEachRow = forEachRow;
        this.forEachStatement = forEachStatement;
        this.when = when;
        this.body = body2;
    }

    @Override
    public final CreateTriggerImpl beforeInsert() {
        this.before = true;
        this.after = false;
        this.insteadOf = false;
        this.insert = true;
        this.update = false;
        this.delete = false;
        return this;
    }

    @Override
    public final CreateTriggerImpl beforeUpdate() {
        this.before = true;
        this.after = false;
        this.insteadOf = false;
        this.insert = false;
        this.update = true;
        this.delete = false;
        return this;
    }

    @Override
    public final CreateTriggerImpl beforeDelete() {
        this.before = true;
        this.after = false;
        this.insteadOf = false;
        this.insert = false;
        this.update = false;
        this.delete = true;
        return this;
    }

    @Override
    public final CreateTriggerImpl afterInsert() {
        this.before = false;
        this.after = true;
        this.insteadOf = false;
        this.insert = true;
        this.update = false;
        this.delete = false;
        return this;
    }

    @Override
    public final CreateTriggerImpl afterUpdate() {
        this.before = false;
        this.after = true;
        this.insteadOf = false;
        this.insert = false;
        this.update = true;
        this.delete = false;
        return this;
    }

    @Override
    public final CreateTriggerImpl afterDelete() {
        this.before = false;
        this.after = true;
        this.insteadOf = false;
        this.insert = false;
        this.update = false;
        this.delete = true;
        return this;
    }

    @Override
    public final CreateTriggerImpl insteadOfUpdate() {
        this.before = false;
        this.after = false;
        this.insteadOf = true;
        this.insert = false;
        this.update = true;
        this.delete = false;
        return this;
    }

    @Override
    public final CreateTriggerImpl insteadOfInsert() {
        this.before = false;
        this.after = false;
        this.insteadOf = true;
        this.insert = true;
        this.update = false;
        this.delete = false;
        return this;
    }

    @Override
    public final CreateTriggerImpl insteadOfDelete() {
        this.before = false;
        this.after = false;
        this.insteadOf = true;
        this.insert = false;
        this.update = false;
        this.delete = true;
        return this;
    }

    @Override
    public final CreateTriggerImpl of(String ... of) {
        return this.of((Field[])Tools.fieldsByName(of));
    }

    @Override
    public final CreateTriggerImpl of(Name ... of) {
        return this.of((Field[])Tools.fieldsByName(of));
    }

    @Override
    public final CreateTriggerImpl of(Field<?> ... of) {
        return this.of(Arrays.asList(of));
    }

    @Override
    public final CreateTriggerImpl of(Collection<? extends Field<?>> of) {
        this.of = new QueryPartList((Iterable<Field<?>>)of);
        return this;
    }

    @Override
    public final CreateTriggerImpl orInsert() {
        this.insert = true;
        return this;
    }

    @Override
    public final CreateTriggerImpl orUpdate() {
        this.update = true;
        return this;
    }

    @Override
    public final CreateTriggerImpl orDelete() {
        this.delete = true;
        return this;
    }

    @Override
    public final CreateTriggerImpl on(String on) {
        return this.on((Table)DSL.table(DSL.name(on)));
    }

    @Override
    public final CreateTriggerImpl on(Name on) {
        return this.on((Table)DSL.table(on));
    }

    @Override
    public final CreateTriggerImpl on(Table<?> on) {
        this.on = on;
        return this;
    }

    @Override
    public final CreateTriggerImpl referencingOldAs(String referencingOldAs) {
        return this.referencingOldAs(DSL.name(referencingOldAs));
    }

    @Override
    public final CreateTriggerImpl referencingOldAs(Name referencingOldAs) {
        this.referencingOldAs = referencingOldAs;
        return this;
    }

    @Override
    public final CreateTriggerImpl referencingNewAs(String referencingNewAs) {
        return this.referencingNewAs(DSL.name(referencingNewAs));
    }

    @Override
    public final CreateTriggerImpl referencingNewAs(Name referencingNewAs) {
        this.referencingNewAs = referencingNewAs;
        return this;
    }

    @Override
    public final CreateTriggerImpl forEachRow() {
        this.forEachRow = true;
        return this;
    }

    @Override
    public final CreateTriggerImpl forEachStatement() {
        this.forEachStatement = true;
        return this;
    }

    @Override
    public final CreateTriggerImpl when(Field<Boolean> when) {
        return this.when(DSL.condition(when));
    }

    @Override
    public final CreateTriggerImpl when(Condition ... when) {
        return this.when(DSL.condition(Operator.AND, when));
    }

    @Override
    public final CreateTriggerImpl when(Collection<? extends Condition> when) {
        return this.when(DSL.condition(Operator.AND, when));
    }

    @Override
    public final CreateTriggerImpl when(Condition when) {
        this.when = when;
        return this;
    }

    @Override
    public final CreateTriggerImpl when(String when, QueryPart ... parts) {
        return this.when(DSL.condition(when, parts));
    }

    @Override
    public final CreateTriggerImpl when(String when, Object ... bindings) {
        return this.when(DSL.condition(when, bindings));
    }

    @Override
    public final CreateTriggerImpl when(String when) {
        return this.when(DSL.condition(when));
    }

    @Override
    public final CreateTriggerImpl when(SQL when) {
        return this.when(DSL.condition(when));
    }

    @Override
    public final CreateTriggerImpl as(Statement ... body2) {
        return this.as((Statement)DSL.statements(body2));
    }

    @Override
    public final CreateTriggerImpl as(Collection<? extends Statement> body2) {
        return this.as((Statement)DSL.statements(body2));
    }

    @Override
    public final CreateTriggerImpl as(Statement body2) {
        this.body = body2;
        return this;
    }

    @Override
    public final void accept(Context<?> ctx) {
        ctx.data(Tools.ExtendedDataKey.DATA_TOP_SCOPE_TRIGGER_NAME, this.trigger);
        ctx.visit(Keywords.K_CREATE).sql(' ');
        CreateProcedureImpl.acceptOrReplace(ctx, this.orReplace, NO_SUPPORT_OR_REPLACE.contains((Object)ctx.dialect()), () -> ctx.dsl().dropTriggerIfExists(this.trigger));
        ctx.visit(Keywords.K_TRIGGER).sql(' ').visit(new QualifiedImpl(this.trigger)).formatSeparator();
        if (ctx.family() == SQLDialect.SQLSERVER) {
            ctx.visit(Keywords.K_ON).sql(' ').visit(this.on).formatSeparator();
        }
        if (this.before) {
            if (ctx.family() == SQLDialect.SQLSERVER) {
                ctx.visit(Keywords.K_FOR);
            } else {
                ctx.visit(Keywords.K_BEFORE);
            }
        } else if (this.after) {
            ctx.visit(Keywords.K_AFTER);
        } else if (this.insteadOf) {
            ctx.visit(Keywords.K_INSTEAD).sql(' ').visit(Keywords.K_OF);
        }
        boolean or = false;
        if (this.insert) {
            ctx.sql(' ').visit(Keywords.K_INSERT);
            or = true;
        }
        if (this.update) {
            if (or) {
                ctx.sql(' ').visit(Keywords.K_OR);
            }
            ctx.sql(' ').visit(Keywords.K_UPDATE);
            if (!this.of.isEmpty()) {
                ctx.sql(' ').visit(Keywords.K_OF).sql(' ').visit(NO_SUPPORT_QUALIFY_OF.contains((Object)ctx.dialect()) ? QueryPartCollectionView.wrap(this.of).qualify(false) : QueryPartCollectionView.wrap(this.of));
            }
            or = true;
        }
        if (this.delete) {
            if (or) {
                ctx.sql(' ').visit(Keywords.K_OR);
            }
            ctx.sql(' ').visit(Keywords.K_DELETE);
        }
        if (ctx.family() != SQLDialect.SQLSERVER) {
            ctx.sql(' ').visit(Keywords.K_ON).sql(' ').visit(this.on);
        } else {
            ctx.sql(' ').visit(Keywords.K_AS);
        }
        if (!(NO_SUPPORT_REFERENCING.contains((Object)ctx.dialect()) || this.referencingOldAs == null && this.referencingNewAs == null)) {
            ctx.formatSeparator().visit(Keywords.K_REFERENCING);
            if (this.referencingOldAs != null) {
                ctx.sql(' ').visit(Keywords.K_OLD).sql(' ').visit(Keywords.K_AS).sql(' ').visit(this.referencingOldAs);
            }
            if (this.referencingNewAs != null) {
                ctx.sql(' ').visit(Keywords.K_NEW).sql(' ').visit(Keywords.K_AS).sql(' ').visit(this.referencingNewAs);
            }
        }
        if (!NO_SUPPORT_FOR_EACH.contains((Object)ctx.family())) {
            if (this.forEachRow) {
                ctx.formatSeparator().visit(Keywords.K_FOR).sql(' ').visit(Keywords.K_EACH).sql(' ').visit(Keywords.K_ROW);
            } else if (this.forEachStatement && ctx.family() == SQLDialect.DB2) {
                ctx.formatSeparator().visit(Keywords.K_FOR).sql(' ').visit(Keywords.K_EACH).sql(' ').visit(Keywords.K_STATEMENT);
            }
        }
        Statement s1 = this.body;
        if (this.when != null) {
            if (NO_SUPPORT_WHEN.contains((Object)ctx.dialect())) {
                s1 = DSL.if_(this.when).then(s1);
            } else {
                ctx.formatSeparator().visit(Keywords.K_WHEN).sql(" (").visit(this.when).sql(')');
            }
        }
        Statement s2 = s1;
        ctx.languageContext(LanguageContext.TRIGGER, c -> CreateProcedureImpl.acceptBody(c, REQUIRE_AS.contains((Object)c.dialect()), this.trigger, s2));
    }

    @Override
    public final Name $trigger() {
        return this.trigger;
    }

    @Override
    public final boolean $orReplace() {
        return this.orReplace;
    }

    @Override
    public final boolean $before() {
        return this.before;
    }

    @Override
    public final boolean $after() {
        return this.after;
    }

    @Override
    public final boolean $insteadOf() {
        return this.insteadOf;
    }

    @Override
    public final boolean $insert() {
        return this.insert;
    }

    @Override
    public final boolean $update() {
        return this.update;
    }

    @Override
    public final boolean $delete() {
        return this.delete;
    }

    @Override
    public final QOM.UnmodifiableList<? extends Field<?>> $of() {
        return QOM.unmodifiable(this.of);
    }

    @Override
    public final Table<?> $on() {
        return this.on;
    }

    @Override
    public final Name $referencingOldAs() {
        return this.referencingOldAs;
    }

    @Override
    public final Name $referencingNewAs() {
        return this.referencingNewAs;
    }

    @Override
    public final boolean $forEachRow() {
        return this.forEachRow;
    }

    @Override
    public final boolean $forEachStatement() {
        return this.forEachStatement;
    }

    @Override
    public final Condition $when() {
        return this.when;
    }

    @Override
    public final Statement $body() {
        return this.body;
    }

    @Override
    public final QOM.CreateTrigger $trigger(Name newValue) {
        return this.$constructor().apply(newValue, this.$orReplace(), this.$before(), this.$after(), this.$insteadOf(), this.$insert(), this.$update(), this.$delete(), this.$of(), this.$on(), this.$referencingOldAs(), this.$referencingNewAs(), this.$forEachRow(), this.$forEachStatement(), this.$when(), this.$body());
    }

    @Override
    public final QOM.CreateTrigger $orReplace(boolean newValue) {
        return this.$constructor().apply(this.$trigger(), newValue, this.$before(), this.$after(), this.$insteadOf(), this.$insert(), this.$update(), this.$delete(), this.$of(), this.$on(), this.$referencingOldAs(), this.$referencingNewAs(), this.$forEachRow(), this.$forEachStatement(), this.$when(), this.$body());
    }

    @Override
    public final QOM.CreateTrigger $before(boolean newValue) {
        return this.$constructor().apply(this.$trigger(), this.$orReplace(), newValue, this.$after(), this.$insteadOf(), this.$insert(), this.$update(), this.$delete(), this.$of(), this.$on(), this.$referencingOldAs(), this.$referencingNewAs(), this.$forEachRow(), this.$forEachStatement(), this.$when(), this.$body());
    }

    @Override
    public final QOM.CreateTrigger $after(boolean newValue) {
        return this.$constructor().apply(this.$trigger(), this.$orReplace(), this.$before(), newValue, this.$insteadOf(), this.$insert(), this.$update(), this.$delete(), this.$of(), this.$on(), this.$referencingOldAs(), this.$referencingNewAs(), this.$forEachRow(), this.$forEachStatement(), this.$when(), this.$body());
    }

    @Override
    public final QOM.CreateTrigger $insteadOf(boolean newValue) {
        return this.$constructor().apply(this.$trigger(), this.$orReplace(), this.$before(), this.$after(), newValue, this.$insert(), this.$update(), this.$delete(), this.$of(), this.$on(), this.$referencingOldAs(), this.$referencingNewAs(), this.$forEachRow(), this.$forEachStatement(), this.$when(), this.$body());
    }

    @Override
    public final QOM.CreateTrigger $insert(boolean newValue) {
        return this.$constructor().apply(this.$trigger(), this.$orReplace(), this.$before(), this.$after(), this.$insteadOf(), newValue, this.$update(), this.$delete(), this.$of(), this.$on(), this.$referencingOldAs(), this.$referencingNewAs(), this.$forEachRow(), this.$forEachStatement(), this.$when(), this.$body());
    }

    @Override
    public final QOM.CreateTrigger $update(boolean newValue) {
        return this.$constructor().apply(this.$trigger(), this.$orReplace(), this.$before(), this.$after(), this.$insteadOf(), this.$insert(), newValue, this.$delete(), this.$of(), this.$on(), this.$referencingOldAs(), this.$referencingNewAs(), this.$forEachRow(), this.$forEachStatement(), this.$when(), this.$body());
    }

    @Override
    public final QOM.CreateTrigger $delete(boolean newValue) {
        return this.$constructor().apply(this.$trigger(), this.$orReplace(), this.$before(), this.$after(), this.$insteadOf(), this.$insert(), this.$update(), newValue, this.$of(), this.$on(), this.$referencingOldAs(), this.$referencingNewAs(), this.$forEachRow(), this.$forEachStatement(), this.$when(), this.$body());
    }

    @Override
    public final QOM.CreateTrigger $of(Collection<? extends Field<?>> newValue) {
        return this.$constructor().apply(this.$trigger(), this.$orReplace(), this.$before(), this.$after(), this.$insteadOf(), this.$insert(), this.$update(), this.$delete(), newValue, this.$on(), this.$referencingOldAs(), this.$referencingNewAs(), this.$forEachRow(), this.$forEachStatement(), this.$when(), this.$body());
    }

    @Override
    public final QOM.CreateTrigger $on(Table<?> newValue) {
        return this.$constructor().apply(this.$trigger(), this.$orReplace(), this.$before(), this.$after(), this.$insteadOf(), this.$insert(), this.$update(), this.$delete(), this.$of(), newValue, this.$referencingOldAs(), this.$referencingNewAs(), this.$forEachRow(), this.$forEachStatement(), this.$when(), this.$body());
    }

    @Override
    public final QOM.CreateTrigger $referencingOldAs(Name newValue) {
        return this.$constructor().apply(this.$trigger(), this.$orReplace(), this.$before(), this.$after(), this.$insteadOf(), this.$insert(), this.$update(), this.$delete(), this.$of(), this.$on(), newValue, this.$referencingNewAs(), this.$forEachRow(), this.$forEachStatement(), this.$when(), this.$body());
    }

    @Override
    public final QOM.CreateTrigger $referencingNewAs(Name newValue) {
        return this.$constructor().apply(this.$trigger(), this.$orReplace(), this.$before(), this.$after(), this.$insteadOf(), this.$insert(), this.$update(), this.$delete(), this.$of(), this.$on(), this.$referencingOldAs(), newValue, this.$forEachRow(), this.$forEachStatement(), this.$when(), this.$body());
    }

    @Override
    public final QOM.CreateTrigger $forEachRow(boolean newValue) {
        return this.$constructor().apply(this.$trigger(), this.$orReplace(), this.$before(), this.$after(), this.$insteadOf(), this.$insert(), this.$update(), this.$delete(), this.$of(), this.$on(), this.$referencingOldAs(), this.$referencingNewAs(), newValue, this.$forEachStatement(), this.$when(), this.$body());
    }

    @Override
    public final QOM.CreateTrigger $forEachStatement(boolean newValue) {
        return this.$constructor().apply(this.$trigger(), this.$orReplace(), this.$before(), this.$after(), this.$insteadOf(), this.$insert(), this.$update(), this.$delete(), this.$of(), this.$on(), this.$referencingOldAs(), this.$referencingNewAs(), this.$forEachRow(), newValue, this.$when(), this.$body());
    }

    @Override
    public final QOM.CreateTrigger $when(Condition newValue) {
        return this.$constructor().apply(this.$trigger(), this.$orReplace(), this.$before(), this.$after(), this.$insteadOf(), this.$insert(), this.$update(), this.$delete(), this.$of(), this.$on(), this.$referencingOldAs(), this.$referencingNewAs(), this.$forEachRow(), this.$forEachStatement(), newValue, this.$body());
    }

    @Override
    public final QOM.CreateTrigger $body(Statement newValue) {
        return this.$constructor().apply(this.$trigger(), this.$orReplace(), this.$before(), this.$after(), this.$insteadOf(), this.$insert(), this.$update(), this.$delete(), this.$of(), this.$on(), this.$referencingOldAs(), this.$referencingNewAs(), this.$forEachRow(), this.$forEachStatement(), this.$when(), newValue);
    }

    public final Function16<? super Name, ? super Boolean, ? super Boolean, ? super Boolean, ? super Boolean, ? super Boolean, ? super Boolean, ? super Boolean, ? super Collection<? extends Field<?>>, ? super Table<?>, ? super Name, ? super Name, ? super Boolean, ? super Boolean, ? super Condition, ? super Statement, ? extends QOM.CreateTrigger> $constructor() {
        return (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16) -> new CreateTriggerImpl(this.configuration(), (Name)a1, (boolean)a2, (boolean)a3, (boolean)a4, (boolean)a5, (boolean)a6, (boolean)a7, (boolean)a8, (Collection<? extends Field<?>>)a9, (Table<?>)a10, (Name)a11, (Name)a12, (boolean)a13, (boolean)a14, (Condition)a15, (Statement)a16);
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.$trigger(), this.$orReplace(), this.$before(), this.$after(), this.$insteadOf(), this.$insert(), this.$update(), this.$delete(), this.$of(), this.$on(), this.$referencingOldAs(), this.$referencingNewAs(), this.$forEachRow(), this.$forEachStatement(), this.$when(), this.$body(), (a1, a2, a3, a4, a5, a6, a7, a8, a9, a10, a11, a12, a13, a14, a15, a16) -> this.$constructor().apply((Name)a1, (Boolean)a2, (Boolean)a3, (Boolean)a4, (Boolean)a5, (Boolean)a6, (Boolean)a7, (Boolean)a8, (Collection<Field<?>>)a9, (Table<?>)a10, (Name)a11, (Name)a12, (Boolean)a13, (Boolean)a14, (Condition)a15, (Statement)a16), replacer);
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        return QOM.traverse(traverser, this, this.$trigger(), this.$of(), this.$on(), this.$referencingOldAs(), this.$referencingNewAs(), this.$when(), this.$body());
    }
}

