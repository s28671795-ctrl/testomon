/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression.analysis;

import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.analysis.WindowFrameBoundType;
import org.h2.table.ColumnResolver;

public class WindowFrameBound {
    private final WindowFrameBoundType type;
    private Expression value;
    private boolean isVariable;
    private int expressionIndex = -1;

    public WindowFrameBound(WindowFrameBoundType windowFrameBoundType, Expression expression) {
        this.type = windowFrameBoundType;
        this.value = windowFrameBoundType == WindowFrameBoundType.PRECEDING || windowFrameBoundType == WindowFrameBoundType.FOLLOWING ? expression : null;
    }

    public WindowFrameBoundType getType() {
        return this.type;
    }

    public Expression getValue() {
        return this.value;
    }

    public boolean isParameterized() {
        return this.type == WindowFrameBoundType.PRECEDING || this.type == WindowFrameBoundType.FOLLOWING;
    }

    public boolean isVariable() {
        return this.isVariable;
    }

    public int getExpressionIndex() {
        return this.expressionIndex;
    }

    void setExpressionIndex(int n) {
        this.expressionIndex = n;
    }

    void mapColumns(ColumnResolver columnResolver, int n, int n2) {
        if (this.value != null) {
            this.value.mapColumns(columnResolver, n, n2);
        }
    }

    void optimize(SessionLocal sessionLocal) {
        if (this.value != null) {
            this.value = this.value.optimize(sessionLocal);
            if (!this.value.isConstant()) {
                this.isVariable = true;
            }
        }
    }

    void updateAggregate(SessionLocal sessionLocal, int n) {
        if (this.value != null) {
            this.value.updateAggregate(sessionLocal, n);
        }
    }

    public StringBuilder getSQL(StringBuilder stringBuilder, boolean bl, int n) {
        if (this.type == WindowFrameBoundType.PRECEDING || this.type == WindowFrameBoundType.FOLLOWING) {
            this.value.getUnenclosedSQL(stringBuilder, n).append(' ');
        }
        return stringBuilder.append(this.type.getSQL());
    }
}

