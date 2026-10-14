/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Collection;
import java.util.Set;
import org.jooq.Context;
import org.jooq.DataType;
import org.jooq.GroupField;
import org.jooq.Name;
import org.jooq.OrderField;
import org.jooq.Pro;
import org.jooq.QueryPart;
import org.jooq.SQLDialect;
import org.jooq.Traverser;
import org.jooq.WindowDefinition;
import org.jooq.WindowExcludeStep;
import org.jooq.WindowFinalStep;
import org.jooq.WindowFromFirstLastStep;
import org.jooq.WindowIgnoreNullsStep;
import org.jooq.WindowOrderByStep;
import org.jooq.WindowOverStep;
import org.jooq.WindowPartitionByStep;
import org.jooq.WindowRowsAndStep;
import org.jooq.WindowRowsStep;
import org.jooq.WindowSpecification;
import org.jooq.impl.AbstractField;
import org.jooq.impl.CustomQueryPart;
import org.jooq.impl.DSL;
import org.jooq.impl.Keywords;
import org.jooq.impl.QOM;
import org.jooq.impl.QueryPartList;
import org.jooq.impl.ScopeMappable;
import org.jooq.impl.SelectQueryImpl;
import org.jooq.impl.Tools;
import org.jooq.impl.WindowDefinitionImpl;
import org.jooq.impl.WindowSpecificationImpl;

abstract class AbstractWindowFunction<T>
extends AbstractField<T>
implements WindowFromFirstLastStep<T>,
WindowPartitionByStep<T>,
WindowRowsStep<T>,
WindowRowsAndStep<T>,
WindowExcludeStep<T>,
QOM.WindowFunction<T>,
ScopeMappable {
    private static final Set<SQLDialect> SUPPORT_NO_PARENS_WINDOW_REFERENCE = SQLDialect.supportedBy(SQLDialect.AURORA_MYSQL, SQLDialect.AURORA_POSTGRES, SQLDialect.COCKROACHDB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLITE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB);
    WindowSpecificationImpl windowSpecification;
    WindowDefinitionImpl windowDefinition;
    Name windowName;
    QOM.NullTreatment nullTreatment;
    QOM.FromFirstOrLast fromFirstOrLast;

    AbstractWindowFunction(Name name, DataType<T> type) {
        super(name, type);
    }

    @Pro
    final WindowSpecificationImpl spec() {
        return this.windowSpecification != null ? this.windowSpecification : new WindowSpecificationImpl(this.windowDefinition != null ? this.windowDefinition : new WindowDefinitionImpl(this.windowName, null));
    }

    final QueryPart window(Context<?> ctx) {
        if (this.windowSpecification != null) {
            return CustomQueryPart.of(c -> c.sql('(').visit(this.windowSpecification).sql(')'));
        }
        if (this.windowDefinition != null) {
            if (SUPPORT_NO_PARENS_WINDOW_REFERENCE.contains((Object)ctx.dialect())) {
                return this.windowDefinition;
            }
            return CustomQueryPart.of(c -> c.sql('(').visit(this.windowDefinition).sql(')'));
        }
        if (this.windowName != null) {
            if (!SelectQueryImpl.NO_SUPPORT_WINDOW_CLAUSE.contains((Object)ctx.dialect())) {
                return this.windowName;
            }
            QueryPartList windows = (QueryPartList)ctx.data(Tools.SimpleDataKey.DATA_WINDOW_DEFINITIONS);
            if (windows != null) {
                for (WindowDefinition window : windows) {
                    if (!((WindowDefinitionImpl)window).getName().equals(this.windowName)) continue;
                    return CustomQueryPart.of(c -> c.sql('(').visit(window).sql(')'));
                }
            } else {
                return this.windowName;
            }
        }
        return null;
    }

    final boolean isWindow() {
        return this.windowSpecification != null || this.windowDefinition != null || this.windowName != null;
    }

    final void acceptOverClause(Context<?> ctx) {
        QueryPart window = this.window(ctx);
        if (window == null) {
            return;
        }
        switch (ctx.family()) {
            case JAVA: {
                ctx.sql(".over(").visit(this.windowSpecification != null ? this.windowSpecification : (this.windowDefinition != null ? this.windowDefinition : this.windowName)).sql(')');
                break;
            }
            default: {
                ctx.sql(' ').visit(Keywords.K_OVER).sql(' ');
                ctx.data(Tools.ExtendedDataKey.DATA_WINDOW_FUNCTION, this, c -> c.visit(window));
            }
        }
    }

    @Pro
    final void acceptNullTreatmentDB2(Context<?> ctx) {
        switch (ctx.family()) {
            case DB2: {
                if (this.nullTreatment == QOM.NullTreatment.IGNORE_NULLS) {
                    ctx.sql(", 'IGNORE NULLS'");
                    break;
                }
                if (this.nullTreatment != QOM.NullTreatment.RESPECT_NULLS) break;
                ctx.sql(", 'RESPECT NULLS'");
            }
        }
    }

    @Pro
    final void acceptNullTreatmentVertica(Context<?> ctx) {
        switch (ctx.family()) {
            case VERTICA: {
                if (this.nullTreatment != QOM.NullTreatment.IGNORE_NULLS) break;
                ctx.sql(' ').visit(Keywords.K_IGNORE_NULLS);
            }
        }
    }

    final void acceptNullTreatment(Context<?> ctx) {
        switch (ctx.family()) {
            case JAVA: {
                if (this.nullTreatment == QOM.NullTreatment.IGNORE_NULLS) {
                    ctx.sql(".ignoreNulls()");
                    break;
                }
                if (this.nullTreatment != QOM.NullTreatment.RESPECT_NULLS) break;
                ctx.sql(".respectNulls()");
                break;
            }
            case DB2: 
            case VERTICA: {
                break;
            }
            default: {
                this.acceptNullTreatmentStandard(ctx);
            }
        }
    }

    final void acceptNullTreatmentStandard(Context<?> ctx) {
        switch (ctx.family()) {
            case VERTICA: {
                break;
            }
            default: {
                if (this.nullTreatment == QOM.NullTreatment.IGNORE_NULLS) {
                    ctx.sql(' ').visit(Keywords.K_IGNORE_NULLS);
                    break;
                }
                if (this.nullTreatment != QOM.NullTreatment.RESPECT_NULLS) break;
                ctx.sql(' ').visit(Keywords.K_RESPECT_NULLS);
            }
        }
    }

    final void acceptFromFirstOrLast(Context<?> ctx) {
        switch (ctx.family()) {
            case JAVA: {
                if (this.fromFirstOrLast == QOM.FromFirstOrLast.FROM_LAST) {
                    ctx.sql(".fromLast()");
                    break;
                }
                if (this.fromFirstOrLast != QOM.FromFirstOrLast.FROM_FIRST) break;
                ctx.sql(".fromFirst()");
                break;
            }
            default: {
                if (this.fromFirstOrLast == QOM.FromFirstOrLast.FROM_LAST) {
                    ctx.sql(' ').visit(Keywords.K_FROM).sql(' ').visit(Keywords.K_LAST);
                    break;
                }
                if (this.fromFirstOrLast != QOM.FromFirstOrLast.FROM_FIRST) break;
                ctx.sql(' ').visit(Keywords.K_FROM).sql(' ').visit(Keywords.K_FIRST);
            }
        }
    }

    @Override
    public final WindowOverStep<T> ignoreNulls() {
        this.nullTreatment = QOM.NullTreatment.IGNORE_NULLS;
        return this;
    }

    @Override
    public final WindowOverStep<T> respectNulls() {
        this.nullTreatment = QOM.NullTreatment.RESPECT_NULLS;
        return this;
    }

    @Override
    public final WindowIgnoreNullsStep<T> fromFirst() {
        this.fromFirstOrLast = QOM.FromFirstOrLast.FROM_FIRST;
        return this;
    }

    @Override
    public final WindowIgnoreNullsStep<T> fromLast() {
        this.fromFirstOrLast = QOM.FromFirstOrLast.FROM_LAST;
        return this;
    }

    @Override
    public final WindowPartitionByStep<T> over() {
        this.windowSpecification = new WindowSpecificationImpl();
        return this;
    }

    @Override
    public final WindowFinalStep<T> over(WindowSpecification specification) {
        WindowSpecificationImpl w;
        this.windowSpecification = specification instanceof WindowSpecificationImpl ? (w = (WindowSpecificationImpl)specification) : new WindowSpecificationImpl((WindowDefinitionImpl)specification);
        return this;
    }

    @Override
    public final WindowFinalStep<T> over(WindowDefinition definition) {
        this.windowDefinition = (WindowDefinitionImpl)definition;
        return this;
    }

    @Override
    public final WindowFinalStep<T> over(String n) {
        return this.over(DSL.name(n));
    }

    @Override
    public final WindowFinalStep<T> over(Name n) {
        this.windowName = n;
        return this;
    }

    @Override
    public final WindowOrderByStep<T> partitionBy(GroupField ... fields) {
        this.windowSpecification.partitionBy(fields);
        return this;
    }

    @Override
    public final WindowOrderByStep<T> partitionBy(Collection<? extends GroupField> fields) {
        this.windowSpecification.partitionBy((Collection)fields);
        return this;
    }

    @Override
    @Deprecated
    public final WindowOrderByStep<T> partitionByOne() {
        this.windowSpecification.partitionByOne();
        return this;
    }

    @Override
    public AbstractWindowFunction<T> orderBy(OrderField<?> ... fields) {
        this.windowSpecification.orderBy((OrderField[])fields);
        return this;
    }

    @Override
    public AbstractWindowFunction<T> orderBy(Collection<? extends OrderField<?>> fields) {
        this.windowSpecification.orderBy((Collection)fields);
        return this;
    }

    @Override
    public final WindowExcludeStep<T> rowsUnboundedPreceding() {
        this.windowSpecification.rowsUnboundedPreceding();
        return this;
    }

    @Override
    public final WindowExcludeStep<T> rowsPreceding(int number) {
        this.windowSpecification.rowsPreceding(number);
        return this;
    }

    @Override
    public final WindowExcludeStep<T> rowsCurrentRow() {
        this.windowSpecification.rowsCurrentRow();
        return this;
    }

    @Override
    public final WindowExcludeStep<T> rowsUnboundedFollowing() {
        this.windowSpecification.rowsUnboundedFollowing();
        return this;
    }

    @Override
    public final WindowExcludeStep<T> rowsFollowing(int number) {
        this.windowSpecification.rowsFollowing(number);
        return this;
    }

    @Override
    public final WindowRowsAndStep<T> rowsBetweenUnboundedPreceding() {
        this.windowSpecification.rowsBetweenUnboundedPreceding();
        return this;
    }

    @Override
    public final WindowRowsAndStep<T> rowsBetweenPreceding(int number) {
        this.windowSpecification.rowsBetweenPreceding(number);
        return this;
    }

    @Override
    public final WindowRowsAndStep<T> rowsBetweenCurrentRow() {
        this.windowSpecification.rowsBetweenCurrentRow();
        return this;
    }

    @Override
    public final WindowRowsAndStep<T> rowsBetweenUnboundedFollowing() {
        this.windowSpecification.rowsBetweenUnboundedFollowing();
        return this;
    }

    @Override
    public final WindowRowsAndStep<T> rowsBetweenFollowing(int number) {
        this.windowSpecification.rowsBetweenFollowing(number);
        return this;
    }

    @Override
    public final WindowExcludeStep<T> rangeUnboundedPreceding() {
        this.windowSpecification.rangeUnboundedPreceding();
        return this;
    }

    @Override
    public final WindowExcludeStep<T> rangePreceding(int number) {
        this.windowSpecification.rangePreceding(number);
        return this;
    }

    @Override
    public final WindowExcludeStep<T> rangeCurrentRow() {
        this.windowSpecification.rangeCurrentRow();
        return this;
    }

    @Override
    public final WindowExcludeStep<T> rangeUnboundedFollowing() {
        this.windowSpecification.rangeUnboundedFollowing();
        return this;
    }

    @Override
    public final WindowExcludeStep<T> rangeFollowing(int number) {
        this.windowSpecification.rangeFollowing(number);
        return this;
    }

    @Override
    public final WindowRowsAndStep<T> rangeBetweenUnboundedPreceding() {
        this.windowSpecification.rangeBetweenUnboundedPreceding();
        return this;
    }

    @Override
    public final WindowRowsAndStep<T> rangeBetweenPreceding(int number) {
        this.windowSpecification.rangeBetweenPreceding(number);
        return this;
    }

    @Override
    public final WindowRowsAndStep<T> rangeBetweenCurrentRow() {
        this.windowSpecification.rangeBetweenCurrentRow();
        return this;
    }

    @Override
    public final WindowRowsAndStep<T> rangeBetweenUnboundedFollowing() {
        this.windowSpecification.rangeBetweenUnboundedFollowing();
        return this;
    }

    @Override
    public final WindowRowsAndStep<T> rangeBetweenFollowing(int number) {
        this.windowSpecification.rangeBetweenFollowing(number);
        return this;
    }

    @Override
    public final WindowExcludeStep<T> groupsUnboundedPreceding() {
        this.windowSpecification.groupsUnboundedPreceding();
        return this;
    }

    @Override
    public final WindowExcludeStep<T> groupsPreceding(int number) {
        this.windowSpecification.groupsPreceding(number);
        return this;
    }

    @Override
    public final WindowExcludeStep<T> groupsCurrentRow() {
        this.windowSpecification.groupsCurrentRow();
        return this;
    }

    @Override
    public final WindowExcludeStep<T> groupsUnboundedFollowing() {
        this.windowSpecification.groupsUnboundedFollowing();
        return this;
    }

    @Override
    public final WindowExcludeStep<T> groupsFollowing(int number) {
        this.windowSpecification.groupsFollowing(number);
        return this;
    }

    @Override
    public final WindowRowsAndStep<T> groupsBetweenUnboundedPreceding() {
        this.windowSpecification.groupsBetweenUnboundedPreceding();
        return this;
    }

    @Override
    public final WindowRowsAndStep<T> groupsBetweenPreceding(int number) {
        this.windowSpecification.groupsBetweenPreceding(number);
        return this;
    }

    @Override
    public final WindowRowsAndStep<T> groupsBetweenCurrentRow() {
        this.windowSpecification.groupsBetweenCurrentRow();
        return this;
    }

    @Override
    public final WindowRowsAndStep<T> groupsBetweenUnboundedFollowing() {
        this.windowSpecification.groupsBetweenUnboundedFollowing();
        return this;
    }

    @Override
    public final WindowRowsAndStep<T> groupsBetweenFollowing(int number) {
        this.windowSpecification.groupsBetweenFollowing(number);
        return this;
    }

    @Override
    public final WindowExcludeStep<T> andUnboundedPreceding() {
        this.windowSpecification.andUnboundedPreceding();
        return this;
    }

    @Override
    public final WindowExcludeStep<T> andPreceding(int number) {
        this.windowSpecification.andPreceding(number);
        return this;
    }

    @Override
    public final WindowExcludeStep<T> andCurrentRow() {
        this.windowSpecification.andCurrentRow();
        return this;
    }

    @Override
    public final WindowExcludeStep<T> andUnboundedFollowing() {
        this.windowSpecification.andUnboundedFollowing();
        return this;
    }

    @Override
    public final WindowExcludeStep<T> andFollowing(int number) {
        this.windowSpecification.andFollowing(number);
        return this;
    }

    @Override
    public final WindowFinalStep<T> excludeCurrentRow() {
        this.windowSpecification.excludeCurrentRow();
        return this;
    }

    @Override
    public final WindowFinalStep<T> excludeGroup() {
        this.windowSpecification.excludeGroup();
        return this;
    }

    @Override
    public final WindowFinalStep<T> excludeTies() {
        this.windowSpecification.excludeTies();
        return this;
    }

    @Override
    public final WindowFinalStep<T> excludeNoOthers() {
        this.windowSpecification.excludeNoOthers();
        return this;
    }

    @Override
    public final WindowSpecification $windowSpecification() {
        return this.windowSpecification;
    }

    final AbstractWindowFunction<T> $windowSpecification(WindowSpecification s2) {
        this.windowSpecification = (WindowSpecificationImpl)s2;
        return this;
    }

    @Override
    public final WindowDefinition $windowDefinition() {
        return this.windowDefinition;
    }

    final AbstractWindowFunction<T> $windowDefinition(WindowDefinition d) {
        this.windowDefinition = (WindowDefinitionImpl)d;
        return this;
    }

    public final QOM.NullTreatment $nullTreatment() {
        return this.nullTreatment;
    }

    final AbstractWindowFunction<T> $nullTreatment(QOM.NullTreatment n) {
        this.nullTreatment = n;
        return this;
    }

    public final QOM.FromFirstOrLast $fromFirstOrLast() {
        return this.fromFirstOrLast;
    }

    final AbstractWindowFunction<T> $fromFirstOrLast(QOM.FromFirstOrLast f) {
        this.fromFirstOrLast = f;
        return this;
    }

    @Override
    public <R> R $traverse(Traverser<?, R> traverser) {
        return QOM.traverse(traverser, this, this.$windowDefinition(), this.$windowSpecification());
    }
}

