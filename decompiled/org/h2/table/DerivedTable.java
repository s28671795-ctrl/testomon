/*
 * Decompiled with CFR 0.152.
 */
package org.h2.table;

import java.util.ArrayList;
import org.h2.command.query.Query;
import org.h2.engine.SessionLocal;
import org.h2.expression.ExpressionVisitor;
import org.h2.expression.Parameter;
import org.h2.index.QueryExpressionIndex;
import org.h2.message.DbException;
import org.h2.table.Column;
import org.h2.table.QueryExpressionTable;
import org.h2.table.Table;
import org.h2.table.TableType;
import org.h2.util.StringUtils;

public final class DerivedTable
extends QueryExpressionTable {
    private String querySQL;
    private Query topQuery;

    public DerivedTable(SessionLocal sessionLocal, String string, Column[] columnArray, Query query, Query query2) {
        super(sessionLocal.getDatabase().getMainSchema(), 0, string);
        this.setTemporary(true);
        this.topQuery = query2;
        query.prepareExpressions();
        try {
            this.querySQL = query.getPlanSQL(0);
            ArrayList<Parameter> arrayList = query.getParameters();
            this.index = new QueryExpressionIndex(this, this.querySQL, arrayList, false);
            this.tables = new ArrayList<Table>(query.getTables());
            this.setColumns(this.initColumns(sessionLocal, columnArray, query, true));
            this.viewQuery = query;
        }
        catch (DbException dbException) {
            if (dbException.getErrorCode() == 90156) {
                throw dbException;
            }
            dbException.addSQL(this.getCreateSQL());
            throw dbException;
        }
    }

    @Override
    public boolean isQueryComparable() {
        if (!super.isQueryComparable()) {
            return false;
        }
        return this.topQuery == null || this.topQuery.isEverything(ExpressionVisitor.QUERY_COMPARABLE_VISITOR);
    }

    @Override
    public boolean canDrop() {
        return false;
    }

    @Override
    public TableType getTableType() {
        return null;
    }

    @Override
    public Query getTopQuery() {
        return this.topQuery;
    }

    @Override
    public String getCreateSQL() {
        return null;
    }

    @Override
    public StringBuilder getSQL(StringBuilder stringBuilder, int n) {
        return StringUtils.indent(stringBuilder.append("(\n"), this.querySQL, 4, true).append(')');
    }
}

