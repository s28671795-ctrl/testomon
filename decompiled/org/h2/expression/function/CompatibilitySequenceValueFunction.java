/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression.function;

import org.h2.command.Parser;
import org.h2.engine.Database;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.ExpressionColumn;
import org.h2.expression.ExpressionVisitor;
import org.h2.expression.function.Function1_2;
import org.h2.message.DbException;
import org.h2.schema.Schema;
import org.h2.schema.Sequence;
import org.h2.util.StringUtils;
import org.h2.value.TypeInfo;
import org.h2.value.Value;

public final class CompatibilitySequenceValueFunction
extends Function1_2 {
    private final boolean current;

    public CompatibilitySequenceValueFunction(Expression expression, Expression expression2, boolean bl) {
        super(expression, expression2);
        this.current = bl;
    }

    /*
     * WARNING - void declaration
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public Value getValue(SessionLocal sessionLocal, Value value, Value value2) {
        void var8_9;
        Sequence sequence2;
        Object object;
        String string;
        Object object2;
        Object object3;
        if (value2 == null) {
            object3 = new Parser(sessionLocal);
            object2 = value.getString();
            Expression expression = ((Parser)object3).parseExpression((String)object2);
            if (!(expression instanceof ExpressionColumn)) throw DbException.getSyntaxError((String)object2, 1);
            ExpressionColumn expressionColumn = (ExpressionColumn)expression;
            string = expressionColumn.getOriginalTableAliasName();
            if (string == null) {
                string = sessionLocal.getCurrentSchemaName();
                object = object2;
            } else {
                object = expressionColumn.getColumnName(sessionLocal, -1);
            }
        } else {
            string = value.getString();
            object = value2.getString();
        }
        if ((object2 = ((Database)(object3 = sessionLocal.getDatabase())).findSchema(string)) == null) {
            string = StringUtils.toUpperEnglish(string);
            object2 = ((Database)object3).getSchema(string);
        }
        if ((sequence2 = ((Schema)object2).findSequence((String)object)) != null) return (this.current ? sessionLocal.getCurrentValueFor((Sequence)var8_9) : sessionLocal.getNextValueFor((Sequence)var8_9, null)).convertTo(this.type);
        object = StringUtils.toUpperEnglish((String)object);
        Sequence sequence3 = ((Schema)object2).getSequence((String)object);
        return (this.current ? sessionLocal.getCurrentValueFor((Sequence)var8_9) : sessionLocal.getNextValueFor((Sequence)var8_9, null)).convertTo(this.type);
    }

    @Override
    public Expression optimize(SessionLocal sessionLocal) {
        this.left = this.left.optimize(sessionLocal);
        if (this.right != null) {
            this.right = this.right.optimize(sessionLocal);
        }
        this.type = sessionLocal.getMode().decimalSequences ? TypeInfo.TYPE_NUMERIC_BIGINT : TypeInfo.TYPE_BIGINT;
        return this;
    }

    @Override
    public boolean isEverything(ExpressionVisitor expressionVisitor) {
        switch (expressionVisitor.getType()) {
            case 0: 
            case 2: 
            case 8: {
                return false;
            }
            case 5: {
                if (this.current) break;
                return false;
            }
        }
        return super.isEverything(expressionVisitor);
    }

    @Override
    public String getName() {
        return this.current ? "CURRVAL" : "NEXTVAL";
    }
}

