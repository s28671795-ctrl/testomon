/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression.function;

import org.h2.expression.Expression;
import org.h2.expression.Operation1;
import org.h2.expression.function.NamedExpression;

public abstract class Function1
extends Operation1
implements NamedExpression {
    protected Function1(Expression expression) {
        super(expression);
    }

    @Override
    public StringBuilder getUnenclosedSQL(StringBuilder stringBuilder, int n) {
        return this.arg.getUnenclosedSQL(stringBuilder.append(this.getName()).append('('), n).append(')');
    }
}

