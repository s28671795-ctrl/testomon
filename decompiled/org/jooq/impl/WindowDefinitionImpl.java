/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Collection;
import org.jooq.Context;
import org.jooq.GroupField;
import org.jooq.Name;
import org.jooq.OrderField;
import org.jooq.QueryPart;
import org.jooq.Replacer;
import org.jooq.SQLDialect;
import org.jooq.SortField;
import org.jooq.Traverser;
import org.jooq.WindowDefinition;
import org.jooq.WindowSpecification;
import org.jooq.WindowSpecificationExcludeStep;
import org.jooq.WindowSpecificationRowsAndStep;
import org.jooq.WindowSpecificationRowsStep;
import org.jooq.impl.AbstractQueryPart;
import org.jooq.impl.Keywords;
import org.jooq.impl.QOM;
import org.jooq.impl.QueryPartList;
import org.jooq.impl.SelectQueryImpl;
import org.jooq.impl.Tools;
import org.jooq.impl.WindowSpecificationImpl;

final class WindowDefinitionImpl
extends AbstractQueryPart
implements WindowDefinition {
    private final Name name;
    private final WindowSpecification window;

    WindowDefinitionImpl(Name name, WindowSpecification window) {
        this.name = name;
        this.window = window;
    }

    final Name getName() {
        return this.name;
    }

    @Override
    public final void accept(Context<?> ctx) {
        if (ctx.family() == SQLDialect.JAVA) {
            ctx.sql("/* window definitions not yet supported. See known limitations: https://github.com/jOOQ/jOOQ/issues/11911 */");
        }
        if (ctx.declareWindows()) {
            ctx.visit(this.name).sql(' ').visit(Keywords.K_AS).sql(" (");
            if (this.window != null) {
                ctx.visit(this.window);
            }
            ctx.sql(')');
        } else if (!SelectQueryImpl.NO_SUPPORT_WINDOW_CLAUSE.contains((Object)ctx.dialect())) {
            ctx.visit(this.name);
        } else if (this.window != null) {
            ctx.visit(this.window);
        } else {
            QueryPartList windows = (QueryPartList)ctx.data(Tools.SimpleDataKey.DATA_WINDOW_DEFINITIONS);
            if (windows != null) {
                for (WindowDefinition w : windows) {
                    if (!((WindowDefinitionImpl)w).getName().equals(this.name)) continue;
                    if (w == this) break;
                    ctx.visit(w);
                    break;
                }
            }
        }
    }

    @Override
    public final boolean declaresWindows() {
        return true;
    }

    @Override
    public final WindowSpecificationRowsStep orderBy(OrderField<?> ... fields) {
        return new WindowSpecificationImpl(this).orderBy((OrderField[])fields);
    }

    @Override
    public final WindowSpecificationRowsStep orderBy(Collection<? extends OrderField<?>> fields) {
        return new WindowSpecificationImpl(this).orderBy((Collection)fields);
    }

    @Override
    public final WindowSpecificationExcludeStep rowsUnboundedPreceding() {
        return new WindowSpecificationImpl(this).rowsUnboundedPreceding();
    }

    @Override
    public final WindowSpecificationExcludeStep rowsPreceding(int number) {
        return new WindowSpecificationImpl(this).rowsPreceding(number);
    }

    @Override
    public final WindowSpecificationExcludeStep rowsCurrentRow() {
        return new WindowSpecificationImpl(this).rowsCurrentRow();
    }

    @Override
    public final WindowSpecificationExcludeStep rowsUnboundedFollowing() {
        return new WindowSpecificationImpl(this).rowsUnboundedFollowing();
    }

    @Override
    public final WindowSpecificationExcludeStep rowsFollowing(int number) {
        return new WindowSpecificationImpl(this).rowsFollowing(number);
    }

    @Override
    public final WindowSpecificationRowsAndStep rowsBetweenUnboundedPreceding() {
        return new WindowSpecificationImpl(this).rowsBetweenUnboundedPreceding();
    }

    @Override
    public final WindowSpecificationRowsAndStep rowsBetweenPreceding(int number) {
        return new WindowSpecificationImpl(this).rowsBetweenPreceding(number);
    }

    @Override
    public final WindowSpecificationRowsAndStep rowsBetweenCurrentRow() {
        return new WindowSpecificationImpl(this).rowsBetweenCurrentRow();
    }

    @Override
    public final WindowSpecificationRowsAndStep rowsBetweenUnboundedFollowing() {
        return new WindowSpecificationImpl(this).rowsBetweenUnboundedFollowing();
    }

    @Override
    public final WindowSpecificationRowsAndStep rowsBetweenFollowing(int number) {
        return new WindowSpecificationImpl(this).rowsBetweenFollowing(number);
    }

    @Override
    public final WindowSpecificationExcludeStep rangeUnboundedPreceding() {
        return new WindowSpecificationImpl(this).rangeUnboundedPreceding();
    }

    @Override
    public final WindowSpecificationExcludeStep rangePreceding(int number) {
        return new WindowSpecificationImpl(this).rangePreceding(number);
    }

    @Override
    public final WindowSpecificationExcludeStep rangeCurrentRow() {
        return new WindowSpecificationImpl(this).rangeCurrentRow();
    }

    @Override
    public final WindowSpecificationExcludeStep rangeUnboundedFollowing() {
        return new WindowSpecificationImpl(this).rangeUnboundedFollowing();
    }

    @Override
    public final WindowSpecificationExcludeStep rangeFollowing(int number) {
        return new WindowSpecificationImpl(this).rangeFollowing(number);
    }

    @Override
    public final WindowSpecificationRowsAndStep rangeBetweenUnboundedPreceding() {
        return new WindowSpecificationImpl(this).rangeBetweenUnboundedPreceding();
    }

    @Override
    public final WindowSpecificationRowsAndStep rangeBetweenPreceding(int number) {
        return new WindowSpecificationImpl(this).rangeBetweenPreceding(number);
    }

    @Override
    public final WindowSpecificationRowsAndStep rangeBetweenCurrentRow() {
        return new WindowSpecificationImpl(this).rangeBetweenCurrentRow();
    }

    @Override
    public final WindowSpecificationRowsAndStep rangeBetweenUnboundedFollowing() {
        return new WindowSpecificationImpl(this).rangeBetweenUnboundedFollowing();
    }

    @Override
    public final WindowSpecificationRowsAndStep rangeBetweenFollowing(int number) {
        return new WindowSpecificationImpl(this).rangeBetweenFollowing(number);
    }

    @Override
    public final WindowSpecificationExcludeStep groupsUnboundedPreceding() {
        return new WindowSpecificationImpl(this).groupsUnboundedPreceding();
    }

    @Override
    public final WindowSpecificationExcludeStep groupsPreceding(int number) {
        return new WindowSpecificationImpl(this).groupsPreceding(number);
    }

    @Override
    public final WindowSpecificationExcludeStep groupsCurrentRow() {
        return new WindowSpecificationImpl(this).groupsCurrentRow();
    }

    @Override
    public final WindowSpecificationExcludeStep groupsUnboundedFollowing() {
        return new WindowSpecificationImpl(this).groupsUnboundedFollowing();
    }

    @Override
    public final WindowSpecificationExcludeStep groupsFollowing(int number) {
        return new WindowSpecificationImpl(this).groupsFollowing(number);
    }

    @Override
    public final WindowSpecificationRowsAndStep groupsBetweenUnboundedPreceding() {
        return new WindowSpecificationImpl(this).groupsBetweenUnboundedPreceding();
    }

    @Override
    public final WindowSpecificationRowsAndStep groupsBetweenPreceding(int number) {
        return new WindowSpecificationImpl(this).groupsBetweenPreceding(number);
    }

    @Override
    public final WindowSpecificationRowsAndStep groupsBetweenCurrentRow() {
        return new WindowSpecificationImpl(this).groupsBetweenCurrentRow();
    }

    @Override
    public final WindowSpecificationRowsAndStep groupsBetweenUnboundedFollowing() {
        return new WindowSpecificationImpl(this).groupsBetweenUnboundedFollowing();
    }

    @Override
    public final WindowSpecificationRowsAndStep groupsBetweenFollowing(int number) {
        return new WindowSpecificationImpl(this).groupsBetweenFollowing(number);
    }

    @Override
    public final Name $name() {
        return this.name;
    }

    @Override
    public final WindowSpecification $windowSpecification() {
        return this.window;
    }

    @Override
    public final WindowDefinition $windowDefinition() {
        return this;
    }

    @Override
    public final QOM.UnmodifiableList<? extends GroupField> $partitionBy() {
        return QOM.unmodifiable(this.$windowSpecification() == null ? new QueryPartList() : this.$windowSpecification().$partitionBy());
    }

    @Override
    public final QOM.UnmodifiableList<? extends SortField<?>> $orderBy() {
        return QOM.unmodifiable(this.$windowSpecification() == null ? new QueryPartList() : this.$windowSpecification().$orderBy());
    }

    @Override
    public final QOM.FrameUnits $frameUnits() {
        return Tools.apply(this.$windowSpecification(), t2 -> t2.$frameUnits());
    }

    @Override
    public final Integer $frameStart() {
        return Tools.apply(this.$windowSpecification(), t2 -> t2.$frameStart());
    }

    @Override
    public final Integer $frameEnd() {
        return Tools.apply(this.$windowSpecification(), t2 -> t2.$frameEnd());
    }

    @Override
    public final QOM.FrameExclude $exclude() {
        return Tools.apply(this.$windowSpecification(), t2 -> t2.$exclude());
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        return QOM.traverse(traverser, this, this.$name(), this.$windowSpecification());
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.name, this.window, WindowDefinitionImpl::new, replacer);
    }
}

