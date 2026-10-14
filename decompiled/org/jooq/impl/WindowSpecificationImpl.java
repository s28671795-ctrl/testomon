/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import org.jooq.Context;
import org.jooq.Field;
import org.jooq.GroupField;
import org.jooq.OrderField;
import org.jooq.Pro;
import org.jooq.QueryPart;
import org.jooq.Replacer;
import org.jooq.SQLDialect;
import org.jooq.SortField;
import org.jooq.Traverser;
import org.jooq.WindowSpecificationExcludeStep;
import org.jooq.WindowSpecificationFinalStep;
import org.jooq.WindowSpecificationOrderByStep;
import org.jooq.WindowSpecificationPartitionByStep;
import org.jooq.WindowSpecificationRowsAndStep;
import org.jooq.conf.RenderImplicitWindowRange;
import org.jooq.impl.AbstractAggregateFunction;
import org.jooq.impl.AbstractQueryPart;
import org.jooq.impl.AbstractWindowFunction;
import org.jooq.impl.CumeDist;
import org.jooq.impl.DSL;
import org.jooq.impl.DenseRank;
import org.jooq.impl.FirstValue;
import org.jooq.impl.GroupFieldList;
import org.jooq.impl.Keywords;
import org.jooq.impl.Lag;
import org.jooq.impl.LastValue;
import org.jooq.impl.Lead;
import org.jooq.impl.NthValue;
import org.jooq.impl.Ntile;
import org.jooq.impl.PercentRank;
import org.jooq.impl.QOM;
import org.jooq.impl.Rank;
import org.jooq.impl.RowNumber;
import org.jooq.impl.SortFieldList;
import org.jooq.impl.Tools;
import org.jooq.impl.WindowDefinitionImpl;
import org.jooq.tools.StringUtils;

final class WindowSpecificationImpl
extends AbstractQueryPart
implements WindowSpecificationPartitionByStep,
WindowSpecificationRowsAndStep,
WindowSpecificationExcludeStep {
    private static final Set<SQLDialect> OMIT_PARTITION_BY_ONE = SQLDialect.supportedBy(SQLDialect.AURORA_MYSQL, SQLDialect.CUBRID, SQLDialect.HANA, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.SQLITE, SQLDialect.SYBASE);
    private static final Set<SQLDialect> REQUIRES_ORDER_BY_IN_LEAD_LAG = SQLDialect.supportedBy(SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.HANA, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.ORACLE, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.VERTICA);
    private static final Set<SQLDialect> REQUIRES_ORDER_BY_IN_NTILE = SQLDialect.supportedBy(SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.HANA, SQLDialect.INFORMIX, SQLDialect.ORACLE, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.VERTICA);
    private static final Set<SQLDialect> REQUIRES_ORDER_BY_IN_RANK_DENSE_RANK = SQLDialect.supportedBy(SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.EXASOL, SQLDialect.H2, SQLDialect.HANA, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.ORACLE, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.TERADATA, SQLDialect.VERTICA);
    private static final Set<SQLDialect> REQUIRES_ORDER_BY_IN_PERCENT_RANK_CUME_DIST = SQLDialect.supportedBy(SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.HANA, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.ORACLE, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.TERADATA, SQLDialect.VERTICA);
    @Pro
    private static final Set<SQLDialect> REQUIRES_ORDER_BY_IN_FIRST_LAST_NTH_VALUE = SQLDialect.supportedBy(SQLDialect.BIGQUERY, SQLDialect.HANA, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.VERTICA);
    @Pro
    private static final Set<SQLDialect> REQUIRES_ORDER_BY_IN_ROW_NUMBER = SQLDialect.supportedBy(SQLDialect.EXASOL, SQLDialect.ORACLE, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.TERADATA);
    private final WindowDefinitionImpl windowDefinition;
    private final GroupFieldList partitionBy;
    private final SortFieldList orderBy;
    private Integer frameStart;
    private Integer frameEnd;
    private QOM.FrameUnits frameUnits;
    private QOM.FrameExclude exclude;
    private boolean partitionByOne;

    WindowSpecificationImpl() {
        this(null);
    }

    WindowSpecificationImpl(WindowDefinitionImpl windowDefinition) {
        this.windowDefinition = windowDefinition;
        this.partitionBy = new GroupFieldList();
        this.orderBy = new SortFieldList();
    }

    WindowSpecificationImpl copy() {
        WindowSpecificationImpl copy = new WindowSpecificationImpl(this.windowDefinition);
        copy.partitionBy.addAll(this.partitionBy);
        copy.orderBy.addAll(this.orderBy);
        copy.frameStart = this.frameStart;
        copy.frameEnd = this.frameEnd;
        copy.frameUnits = this.frameUnits;
        copy.exclude = this.exclude;
        copy.partitionByOne = this.partitionByOne;
        return copy;
    }

    @Override
    public final void accept(Context<?> ctx) {
        boolean indent;
        if (ctx.family() == SQLDialect.JAVA) {
            this.acceptJava(ctx);
            return;
        }
        SortFieldList o = this.orderBy;
        AbstractWindowFunction w = (AbstractWindowFunction)ctx.data(Tools.ExtendedDataKey.DATA_WINDOW_FUNCTION);
        if (o.isEmpty()) {
            boolean ordered;
            boolean bl = ordered = w instanceof Ntile && REQUIRES_ORDER_BY_IN_NTILE.contains((Object)ctx.dialect()) || w instanceof Lead && REQUIRES_ORDER_BY_IN_LEAD_LAG.contains((Object)ctx.dialect()) || w instanceof Lag && REQUIRES_ORDER_BY_IN_LEAD_LAG.contains((Object)ctx.dialect()) || w instanceof Rank && REQUIRES_ORDER_BY_IN_RANK_DENSE_RANK.contains((Object)ctx.dialect()) || w instanceof DenseRank && REQUIRES_ORDER_BY_IN_RANK_DENSE_RANK.contains((Object)ctx.dialect()) || w instanceof PercentRank && REQUIRES_ORDER_BY_IN_PERCENT_RANK_CUME_DIST.contains((Object)ctx.dialect()) || w instanceof CumeDist && REQUIRES_ORDER_BY_IN_PERCENT_RANK_CUME_DIST.contains((Object)ctx.dialect()) || w instanceof FirstValue && REQUIRES_ORDER_BY_IN_FIRST_LAST_NTH_VALUE.contains((Object)ctx.dialect()) || w instanceof LastValue && REQUIRES_ORDER_BY_IN_FIRST_LAST_NTH_VALUE.contains((Object)ctx.dialect()) || w instanceof NthValue && REQUIRES_ORDER_BY_IN_FIRST_LAST_NTH_VALUE.contains((Object)ctx.dialect()) || w instanceof RowNumber && REQUIRES_ORDER_BY_IN_ROW_NUMBER.contains((Object)ctx.dialect());
            if (ordered) {
                Field<Integer> constant = switch (ctx.family()) {
                    case SQLDialect.ORACLE, SQLDialect.VERTICA -> DSL.one();
                    default -> DSL.field(DSL.select(DSL.one()));
                };
                o = new SortFieldList();
                o.add(constant.sortDefault());
            }
        }
        boolean hasWindowDefinitions = this.windowDefinition != null;
        boolean hasPartitionBy = !this.partitionBy.isEmpty();
        boolean hasOrderBy = !o.isEmpty();
        boolean hasFrame = this.frameStart != null || hasOrderBy && !RenderImplicitWindowRange.OFF.equals((Object)ctx.settings().getRenderImplicitWindowRange()) && w instanceof AbstractAggregateFunction;
        int clauses = 0;
        if (hasWindowDefinitions) {
            ++clauses;
        }
        if (hasPartitionBy) {
            ++clauses;
        }
        if (hasOrderBy) {
            ++clauses;
        }
        if (hasFrame) {
            ++clauses;
        }
        boolean bl = indent = clauses > 1;
        if (indent) {
            ctx.formatIndentStart().formatNewLine();
        }
        if (this.windowDefinition != null) {
            ctx.declareWindows(false, c -> c.visit(this.windowDefinition));
        }
        if (!(!hasPartitionBy || this.partitionByOne && OMIT_PARTITION_BY_ONE.contains((Object)ctx.dialect()))) {
            if (hasWindowDefinitions) {
                ctx.formatSeparator();
            }
            ctx.visit(Keywords.K_PARTITION_BY).separatorRequired(true).visit(this.partitionBy);
        }
        if (hasOrderBy) {
            if (hasWindowDefinitions || hasPartitionBy) {
                ctx.formatSeparator();
            }
            ctx.visit(Keywords.K_ORDER_BY).separatorRequired(true).visit(o);
        }
        if (hasFrame) {
            if (hasWindowDefinitions || hasPartitionBy || hasOrderBy) {
                ctx.formatSeparator();
            }
            QOM.FrameUnits u = this.frameUnits;
            Integer s2 = this.frameStart;
            Integer e = this.frameEnd;
            if (s2 == null) {
                switch (StringUtils.defaultIfNull(ctx.settings().getRenderImplicitWindowRange(), RenderImplicitWindowRange.OFF)) {
                    case RANGE_ALL: {
                        u = QOM.FrameUnits.RANGE;
                        s2 = Integer.MIN_VALUE;
                        e = Integer.MAX_VALUE;
                        break;
                    }
                    case RANGE_UNBOUNDED_PRECEDING: {
                        u = QOM.FrameUnits.RANGE;
                        s2 = Integer.MIN_VALUE;
                        e = 0;
                        break;
                    }
                    case ROWS_ALL: {
                        u = QOM.FrameUnits.ROWS;
                        s2 = Integer.MIN_VALUE;
                        e = Integer.MAX_VALUE;
                        break;
                    }
                    case ROWS_UNBOUNDED_PRECEDING: {
                        u = QOM.FrameUnits.ROWS;
                        s2 = Integer.MIN_VALUE;
                        e = 0;
                    }
                }
            }
            ctx.visit(u.keyword).sql(' ');
            if (e != null) {
                ctx.visit(Keywords.K_BETWEEN).sql(' ');
                this.toSQLRows(ctx, s2);
                ctx.sql(' ').visit(Keywords.K_AND).sql(' ');
                this.toSQLRows(ctx, e);
            } else {
                this.toSQLRows(ctx, s2);
            }
            if (this.exclude != null) {
                ctx.sql(' ').visit(Keywords.K_EXCLUDE).sql(' ').visit(this.exclude.keyword);
            }
        }
        if (indent) {
            ctx.formatIndentEnd().formatNewLine();
        }
    }

    @Pro
    private final void acceptJava(Context<?> ctx) {
        String s2 = "";
        if (!Tools.isEmpty(this.partitionBy)) {
            ctx.sql(s2).sql("partitionBy(").visit(this.partitionBy).sql(')');
            s2 = ".";
        }
        if (!Tools.isEmpty(this.orderBy)) {
            ctx.sql(s2).sql("orderBy(").visit(this.orderBy).sql(')');
            s2 = ".";
        }
        if (this.frameStart != null) {
            ctx.sql(s2).sql(this.frameUnits.keyword.toString());
            if (this.frameEnd != null) {
                ctx.sql("Between");
            }
            if (this.frameStart.equals(Integer.MIN_VALUE)) {
                ctx.sql("UnboundedPreceding()");
            } else if (this.frameStart.equals(Integer.MAX_VALUE)) {
                ctx.sql("UnboundedFollowing()");
            } else if (this.frameStart < 0) {
                ctx.sql("Preceding(").sql(this.frameStart).sql(')');
            } else if (this.frameStart > 0) {
                ctx.sql("Following(").sql(this.frameStart).sql(')');
            } else {
                ctx.sql("CurrentRow()");
            }
            if (this.frameEnd != null) {
                ctx.sql(".and");
                if (this.frameEnd.equals(Integer.MIN_VALUE)) {
                    ctx.sql("UnboundedPreceding()");
                } else if (this.frameEnd.equals(Integer.MAX_VALUE)) {
                    ctx.sql("UnboundedFollowing()");
                } else if (this.frameEnd < 0) {
                    ctx.sql("Preceding(").sql(this.frameEnd).sql(')');
                } else if (this.frameEnd > 0) {
                    ctx.sql("Following(").sql(this.frameEnd).sql(')');
                } else {
                    ctx.sql("CurrentRow()");
                }
            }
            if (this.exclude != null) {
                switch (this.exclude) {
                    case CURRENT_ROW: {
                        ctx.sql(".excludeCurrentRow()");
                        break;
                    }
                    case GROUP: {
                        ctx.sql(".excludeGroup()");
                        break;
                    }
                    case NO_OTHERS: {
                        ctx.sql(".excludeNoOthers()");
                        break;
                    }
                    case TIES: {
                        ctx.sql(".excludeTies()");
                    }
                }
            }
        }
    }

    private final void toSQLRows(Context<?> ctx, Integer rows) {
        if (rows == Integer.MIN_VALUE) {
            ctx.visit(Keywords.K_UNBOUNDED_PRECEDING);
        } else if (rows == Integer.MAX_VALUE) {
            ctx.visit(Keywords.K_UNBOUNDED_FOLLOWING);
        } else if (rows < 0) {
            ctx.sql(-rows.intValue()).sql(' ').visit(Keywords.K_PRECEDING);
        } else if (rows > 0) {
            ctx.sql(rows).sql(' ').visit(Keywords.K_FOLLOWING);
        } else {
            ctx.visit(Keywords.K_CURRENT_ROW);
        }
    }

    @Override
    public final WindowSpecificationPartitionByStep partitionBy(GroupField ... fields) {
        return this.partitionBy(Arrays.asList(fields));
    }

    @Override
    public final WindowSpecificationPartitionByStep partitionBy(Collection<? extends GroupField> fields) {
        this.partitionBy.addAll(fields);
        return this;
    }

    @Override
    @Deprecated
    public final WindowSpecificationOrderByStep partitionByOne() {
        this.partitionByOne = true;
        this.partitionBy.add(DSL.one());
        return this;
    }

    @Override
    public final WindowSpecificationOrderByStep orderBy(OrderField<?> ... fields) {
        return this.orderBy(Arrays.asList(fields));
    }

    @Override
    public final WindowSpecificationOrderByStep orderBy(Collection<? extends OrderField<?>> fields) {
        this.orderBy.addAll(Tools.sortFields(fields));
        return this;
    }

    @Override
    public final WindowSpecificationExcludeStep rowsUnboundedPreceding() {
        this.frameUnits = QOM.FrameUnits.ROWS;
        this.frameStart = Integer.MIN_VALUE;
        return this;
    }

    @Override
    public final WindowSpecificationExcludeStep rowsPreceding(int number) {
        this.frameUnits = QOM.FrameUnits.ROWS;
        this.frameStart = -number;
        return this;
    }

    @Override
    public final WindowSpecificationExcludeStep rowsCurrentRow() {
        this.frameUnits = QOM.FrameUnits.ROWS;
        this.frameStart = 0;
        return this;
    }

    @Override
    public final WindowSpecificationExcludeStep rowsUnboundedFollowing() {
        this.frameUnits = QOM.FrameUnits.ROWS;
        this.frameStart = Integer.MAX_VALUE;
        return this;
    }

    @Override
    public final WindowSpecificationExcludeStep rowsFollowing(int number) {
        this.frameUnits = QOM.FrameUnits.ROWS;
        this.frameStart = number;
        return this;
    }

    @Override
    public final WindowSpecificationRowsAndStep rowsBetweenUnboundedPreceding() {
        this.rowsUnboundedPreceding();
        return this;
    }

    @Override
    public final WindowSpecificationRowsAndStep rowsBetweenPreceding(int number) {
        this.rowsPreceding(number);
        return this;
    }

    @Override
    public final WindowSpecificationRowsAndStep rowsBetweenCurrentRow() {
        this.rowsCurrentRow();
        return this;
    }

    @Override
    public final WindowSpecificationRowsAndStep rowsBetweenUnboundedFollowing() {
        this.rowsUnboundedFollowing();
        return this;
    }

    @Override
    public final WindowSpecificationRowsAndStep rowsBetweenFollowing(int number) {
        this.rowsFollowing(number);
        return this;
    }

    @Override
    public final WindowSpecificationExcludeStep rangeUnboundedPreceding() {
        this.frameUnits = QOM.FrameUnits.RANGE;
        this.frameStart = Integer.MIN_VALUE;
        return this;
    }

    @Override
    public final WindowSpecificationExcludeStep rangePreceding(int number) {
        this.frameUnits = QOM.FrameUnits.RANGE;
        this.frameStart = -number;
        return this;
    }

    @Override
    public final WindowSpecificationExcludeStep rangeCurrentRow() {
        this.frameUnits = QOM.FrameUnits.RANGE;
        this.frameStart = 0;
        return this;
    }

    @Override
    public final WindowSpecificationExcludeStep rangeUnboundedFollowing() {
        this.frameUnits = QOM.FrameUnits.RANGE;
        this.frameStart = Integer.MAX_VALUE;
        return this;
    }

    @Override
    public final WindowSpecificationExcludeStep rangeFollowing(int number) {
        this.frameUnits = QOM.FrameUnits.RANGE;
        this.frameStart = number;
        return this;
    }

    @Override
    public final WindowSpecificationRowsAndStep rangeBetweenUnboundedPreceding() {
        this.rangeUnboundedPreceding();
        return this;
    }

    @Override
    public final WindowSpecificationRowsAndStep rangeBetweenPreceding(int number) {
        this.rangePreceding(number);
        return this;
    }

    @Override
    public final WindowSpecificationRowsAndStep rangeBetweenCurrentRow() {
        this.rangeCurrentRow();
        return this;
    }

    @Override
    public final WindowSpecificationRowsAndStep rangeBetweenUnboundedFollowing() {
        this.rangeUnboundedFollowing();
        return this;
    }

    @Override
    public final WindowSpecificationRowsAndStep rangeBetweenFollowing(int number) {
        this.rangeFollowing(number);
        return this;
    }

    @Override
    public final WindowSpecificationExcludeStep groupsUnboundedPreceding() {
        this.frameUnits = QOM.FrameUnits.GROUPS;
        this.frameStart = Integer.MIN_VALUE;
        return this;
    }

    @Override
    public final WindowSpecificationExcludeStep groupsPreceding(int number) {
        this.frameUnits = QOM.FrameUnits.GROUPS;
        this.frameStart = -number;
        return this;
    }

    @Override
    public final WindowSpecificationExcludeStep groupsCurrentRow() {
        this.frameUnits = QOM.FrameUnits.GROUPS;
        this.frameStart = 0;
        return this;
    }

    @Override
    public final WindowSpecificationExcludeStep groupsUnboundedFollowing() {
        this.frameUnits = QOM.FrameUnits.GROUPS;
        this.frameStart = Integer.MAX_VALUE;
        return this;
    }

    @Override
    public final WindowSpecificationExcludeStep groupsFollowing(int number) {
        this.frameUnits = QOM.FrameUnits.GROUPS;
        this.frameStart = number;
        return this;
    }

    @Override
    public final WindowSpecificationRowsAndStep groupsBetweenUnboundedPreceding() {
        this.groupsUnboundedPreceding();
        return this;
    }

    @Override
    public final WindowSpecificationRowsAndStep groupsBetweenPreceding(int number) {
        this.groupsPreceding(number);
        return this;
    }

    @Override
    public final WindowSpecificationRowsAndStep groupsBetweenCurrentRow() {
        this.groupsCurrentRow();
        return this;
    }

    @Override
    public final WindowSpecificationRowsAndStep groupsBetweenUnboundedFollowing() {
        this.groupsUnboundedFollowing();
        return this;
    }

    @Override
    public final WindowSpecificationRowsAndStep groupsBetweenFollowing(int number) {
        this.groupsFollowing(number);
        return this;
    }

    @Override
    public final WindowSpecificationExcludeStep andUnboundedPreceding() {
        this.frameEnd = Integer.MIN_VALUE;
        return this;
    }

    @Override
    public final WindowSpecificationExcludeStep andPreceding(int number) {
        this.frameEnd = -number;
        return this;
    }

    @Override
    public final WindowSpecificationExcludeStep andCurrentRow() {
        this.frameEnd = 0;
        return this;
    }

    @Override
    public final WindowSpecificationExcludeStep andUnboundedFollowing() {
        this.frameEnd = Integer.MAX_VALUE;
        return this;
    }

    @Override
    public final WindowSpecificationExcludeStep andFollowing(int number) {
        this.frameEnd = number;
        return this;
    }

    @Override
    public final WindowSpecificationFinalStep excludeCurrentRow() {
        this.exclude = QOM.FrameExclude.CURRENT_ROW;
        return this;
    }

    @Override
    public final WindowSpecificationFinalStep excludeGroup() {
        this.exclude = QOM.FrameExclude.GROUP;
        return this;
    }

    @Override
    public final WindowSpecificationFinalStep excludeTies() {
        this.exclude = QOM.FrameExclude.TIES;
        return this;
    }

    @Override
    public final WindowSpecificationFinalStep excludeNoOthers() {
        this.exclude = QOM.FrameExclude.NO_OTHERS;
        return this;
    }

    @Override
    public final WindowDefinitionImpl $windowDefinition() {
        return this.windowDefinition;
    }

    public final QOM.UnmodifiableList<? extends Field<?>> $partitionBy() {
        return QOM.unmodifiable(this.partitionBy);
    }

    @Override
    public final QOM.UnmodifiableList<? extends SortField<?>> $orderBy() {
        return QOM.unmodifiable(this.orderBy);
    }

    @Override
    public final QOM.FrameUnits $frameUnits() {
        return this.frameUnits;
    }

    @Override
    public final Integer $frameStart() {
        return this.frameStart;
    }

    @Override
    public final Integer $frameEnd() {
        return this.frameEnd;
    }

    @Override
    public final QOM.FrameExclude $exclude() {
        return this.exclude;
    }

    @Override
    public final <R> R $traverse(Traverser<?, R> traverser) {
        return QOM.traverse(traverser, this, this.$windowDefinition(), this.$partitionBy(), this.$orderBy());
    }

    @Override
    public final QueryPart $replace(Replacer replacer) {
        return QOM.replace(this, this.$windowDefinition(), this.$partitionBy(), this.$orderBy(), (d, p, o) -> {
            WindowSpecificationImpl r = new WindowSpecificationImpl((WindowDefinitionImpl)d);
            r.partitionBy(p.toArray(Tools.EMPTY_FIELD)).orderBy((OrderField[])o.toArray(Tools.EMPTY_SORTFIELD));
            r.frameUnits = this.frameUnits;
            r.frameStart = this.frameStart;
            r.frameEnd = this.frameEnd;
            r.exclude = this.exclude;
            return r;
        }, replacer);
    }
}

