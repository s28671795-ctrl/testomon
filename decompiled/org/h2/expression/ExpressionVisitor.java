/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression;

import java.util.HashSet;
import org.h2.command.query.AllColumnsForPlan;
import org.h2.engine.DbObject;
import org.h2.table.Column;
import org.h2.table.ColumnResolver;
import org.h2.table.Table;
import org.h2.table.TableFilter;

public final class ExpressionVisitor {
    public static final int INDEPENDENT = 0;
    public static final ExpressionVisitor INDEPENDENT_VISITOR;
    public static final int OPTIMIZABLE_AGGREGATE = 1;
    public static final int DETERMINISTIC = 2;
    public static final ExpressionVisitor DETERMINISTIC_VISITOR;
    public static final int EVALUATABLE = 3;
    public static final ExpressionVisitor EVALUATABLE_VISITOR;
    private static final int CACHED = 8;
    private static final ExpressionVisitor[] INDEPENDENT_VISITORS;
    private static final ExpressionVisitor[] EVALUATABLE_VISITORS;
    public static final int SET_MAX_DATA_MODIFICATION_ID = 4;
    public static final int READONLY = 5;
    public static final ExpressionVisitor READONLY_VISITOR;
    public static final int NOT_FROM_RESOLVER = 6;
    public static final int GET_DEPENDENCIES = 7;
    public static final int QUERY_COMPARABLE = 8;
    public static final int GET_COLUMNS1 = 9;
    public static final int GET_COLUMNS2 = 10;
    public static final int DECREMENT_QUERY_LEVEL = 11;
    public static final ExpressionVisitor QUERY_COMPARABLE_VISITOR;
    private final int type;
    private final int queryLevel;
    private final HashSet<?> set;
    private final AllColumnsForPlan columns1;
    private final Table table;
    private final long[] maxDataModificationId;
    private final ColumnResolver resolver;

    private ExpressionVisitor(int n, int n2, HashSet<?> hashSet, AllColumnsForPlan allColumnsForPlan, Table table, ColumnResolver columnResolver, long[] lArray) {
        this.type = n;
        this.queryLevel = n2;
        this.set = hashSet;
        this.columns1 = allColumnsForPlan;
        this.table = table;
        this.resolver = columnResolver;
        this.maxDataModificationId = lArray;
    }

    private ExpressionVisitor(int n) {
        this.type = n;
        this.queryLevel = 0;
        this.set = null;
        this.columns1 = null;
        this.table = null;
        this.resolver = null;
        this.maxDataModificationId = null;
    }

    private ExpressionVisitor(int n, int n2) {
        this.type = n;
        this.queryLevel = n2;
        this.set = null;
        this.columns1 = null;
        this.table = null;
        this.resolver = null;
        this.maxDataModificationId = null;
    }

    public static ExpressionVisitor getDependenciesVisitor(HashSet<DbObject> hashSet) {
        return new ExpressionVisitor(7, 0, hashSet, null, null, null, null);
    }

    public static ExpressionVisitor getOptimizableVisitor(Table table) {
        return new ExpressionVisitor(1, 0, null, null, table, null, null);
    }

    public static ExpressionVisitor getNotFromResolverVisitor(ColumnResolver columnResolver) {
        return new ExpressionVisitor(6, 0, null, null, null, columnResolver, null);
    }

    public static ExpressionVisitor getColumnsVisitor(AllColumnsForPlan allColumnsForPlan) {
        return new ExpressionVisitor(9, 0, null, allColumnsForPlan, null, null, null);
    }

    public static ExpressionVisitor getColumnsVisitor(HashSet<Column> hashSet, Table table) {
        return new ExpressionVisitor(10, 0, hashSet, null, table, null, null);
    }

    public static ExpressionVisitor getMaxModificationIdVisitor() {
        return new ExpressionVisitor(4, 0, null, null, null, null, new long[1]);
    }

    public static ExpressionVisitor getDecrementQueryLevelVisitor(HashSet<ColumnResolver> hashSet, int n) {
        return new ExpressionVisitor(11, n, hashSet, null, null, null, null);
    }

    public void addDependency(DbObject dbObject) {
        this.set.add(dbObject);
    }

    void addColumn1(Column column) {
        this.columns1.add(column);
    }

    void addColumn2(Column column) {
        if (this.table == null || this.table == column.getTable()) {
            this.set.add(column);
        }
    }

    public HashSet<DbObject> getDependencies() {
        return this.set;
    }

    public ExpressionVisitor incrementQueryLevel(int n) {
        if (this.type == 0) {
            return (n += this.queryLevel) < 8 ? INDEPENDENT_VISITORS[n] : new ExpressionVisitor(0, n);
        }
        if (this.type == 3) {
            return (n += this.queryLevel) < 8 ? EVALUATABLE_VISITORS[n] : new ExpressionVisitor(3, n);
        }
        return this;
    }

    public ColumnResolver getResolver() {
        return this.resolver;
    }

    public HashSet<ColumnResolver> getColumnResolvers() {
        return this.set;
    }

    public void addDataModificationId(long l) {
        long l2 = this.maxDataModificationId[0];
        if (l > l2) {
            this.maxDataModificationId[0] = l;
        }
    }

    public long getMaxDataModificationId() {
        return this.maxDataModificationId[0];
    }

    int getQueryLevel() {
        assert (this.type == 0 || this.type == 3 || this.type == 11);
        return this.queryLevel;
    }

    public Table getTable() {
        return this.table;
    }

    public int getType() {
        return this.type;
    }

    public static void allColumnsForTableFilters(TableFilter[] tableFilterArray, AllColumnsForPlan allColumnsForPlan) {
        for (TableFilter tableFilter : tableFilterArray) {
            if (tableFilter.getSelect() == null) continue;
            tableFilter.getSelect().isEverything(ExpressionVisitor.getColumnsVisitor(allColumnsForPlan));
        }
    }

    static {
        int n;
        INDEPENDENT_VISITOR = new ExpressionVisitor(0);
        DETERMINISTIC_VISITOR = new ExpressionVisitor(2);
        EVALUATABLE_VISITOR = new ExpressionVisitor(3);
        ExpressionVisitor[] expressionVisitorArray = new ExpressionVisitor[8];
        expressionVisitorArray[0] = INDEPENDENT_VISITOR;
        for (n = 1; n < 8; ++n) {
            expressionVisitorArray[n] = new ExpressionVisitor(0, n);
        }
        INDEPENDENT_VISITORS = expressionVisitorArray;
        expressionVisitorArray = new ExpressionVisitor[8];
        expressionVisitorArray[0] = EVALUATABLE_VISITOR;
        for (n = 1; n < 8; ++n) {
            expressionVisitorArray[n] = new ExpressionVisitor(3, n);
        }
        EVALUATABLE_VISITORS = expressionVisitorArray;
        READONLY_VISITOR = new ExpressionVisitor(5);
        QUERY_COMPARABLE_VISITOR = new ExpressionVisitor(8);
    }
}

