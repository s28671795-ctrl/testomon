/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression.function;

import java.util.ArrayList;
import org.h2.command.Parser;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.ExpressionVisitor;
import org.h2.expression.function.Function1_2;
import org.h2.index.Index;
import org.h2.message.DbException;
import org.h2.mvstore.db.MVSpatialIndex;
import org.h2.table.Column;
import org.h2.table.Table;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueBigint;
import org.h2.value.ValueNull;

public final class TableInfoFunction
extends Function1_2 {
    public static final int DISK_SPACE_USED = 0;
    public static final int ESTIMATED_ENVELOPE = 1;
    private static final String[] NAMES = new String[]{"DISK_SPACE_USED", "ESTIMATED_ENVELOPE"};
    private final int function;

    public TableInfoFunction(Expression expression, Expression expression2, int n) {
        super(expression, expression2);
        this.function = n;
    }

    @Override
    public Value getValue(SessionLocal sessionLocal, Value value, Value value2) {
        Table table = new Parser(sessionLocal).parseTableName(value.getString());
        block0 : switch (this.function) {
            case 0: {
                value = ValueBigint.get(table.getDiskSpaceUsed());
                break;
            }
            case 1: {
                Column column = table.getColumn(value2.getString());
                ArrayList<Index> arrayList = table.getIndexes();
                if (arrayList != null) {
                    int n = arrayList.size();
                    for (int i = 1; i < n; ++i) {
                        Index index = arrayList.get(i);
                        if (!(index instanceof MVSpatialIndex) || !index.isFirstColumn(column)) continue;
                        value = ((MVSpatialIndex)index).getEstimatedBounds(sessionLocal);
                        break block0;
                    }
                }
                value = ValueNull.INSTANCE;
                break;
            }
            default: {
                throw DbException.getInternalError("function=" + this.function);
            }
        }
        return value;
    }

    @Override
    public Expression optimize(SessionLocal sessionLocal) {
        this.left = this.left.optimize(sessionLocal);
        if (this.right != null) {
            this.right = this.right.optimize(sessionLocal);
        }
        switch (this.function) {
            case 0: {
                this.type = TypeInfo.TYPE_BIGINT;
                break;
            }
            case 1: {
                this.type = TypeInfo.TYPE_GEOMETRY;
                break;
            }
            default: {
                throw DbException.getInternalError("function=" + this.function);
            }
        }
        return this;
    }

    @Override
    public boolean isEverything(ExpressionVisitor expressionVisitor) {
        switch (expressionVisitor.getType()) {
            case 2: {
                return false;
            }
        }
        return super.isEverything(expressionVisitor);
    }

    @Override
    public String getName() {
        return NAMES[this.function];
    }
}

