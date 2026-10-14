/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression.condition;

import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.ExpressionVisitor;
import org.h2.expression.ValueExpression;
import org.h2.expression.condition.Condition;
import org.h2.message.DbException;
import org.h2.table.ColumnResolver;
import org.h2.table.TableFilter;
import org.h2.util.json.JSONBytesSource;
import org.h2.util.json.JSONItemType;
import org.h2.util.json.JSONStringSource;
import org.h2.util.json.JSONValidationTarget;
import org.h2.util.json.JSONValidationTargetWithUniqueKeys;
import org.h2.util.json.JSONValidationTargetWithoutUniqueKeys;
import org.h2.value.Value;
import org.h2.value.ValueBoolean;
import org.h2.value.ValueJson;
import org.h2.value.ValueNull;

public final class IsJsonPredicate
extends Condition {
    private Expression left;
    private final boolean not;
    private final boolean whenOperand;
    private final boolean withUniqueKeys;
    private final JSONItemType itemType;

    public IsJsonPredicate(Expression expression, boolean bl, boolean bl2, boolean bl3, JSONItemType jSONItemType) {
        this.left = expression;
        this.whenOperand = bl2;
        this.not = bl;
        this.withUniqueKeys = bl3;
        this.itemType = jSONItemType;
    }

    @Override
    public boolean needParentheses() {
        return true;
    }

    @Override
    public StringBuilder getUnenclosedSQL(StringBuilder stringBuilder, int n) {
        return this.getWhenSQL(this.left.getSQL(stringBuilder, n, 0), n);
    }

    @Override
    public StringBuilder getWhenSQL(StringBuilder stringBuilder, int n) {
        stringBuilder.append(" IS");
        if (this.not) {
            stringBuilder.append(" NOT");
        }
        stringBuilder.append(" JSON");
        switch (this.itemType) {
            case VALUE: {
                break;
            }
            case ARRAY: {
                stringBuilder.append(" ARRAY");
                break;
            }
            case OBJECT: {
                stringBuilder.append(" OBJECT");
                break;
            }
            case SCALAR: {
                stringBuilder.append(" SCALAR");
                break;
            }
            default: {
                throw DbException.getInternalError("itemType=" + (Object)((Object)this.itemType));
            }
        }
        if (this.withUniqueKeys) {
            stringBuilder.append(" WITH UNIQUE KEYS");
        }
        return stringBuilder;
    }

    @Override
    public Expression optimize(SessionLocal sessionLocal) {
        this.left = this.left.optimize(sessionLocal);
        if (!this.whenOperand && this.left.isConstant()) {
            return ValueExpression.getBoolean(this.getValue(sessionLocal));
        }
        return this;
    }

    @Override
    public Value getValue(SessionLocal sessionLocal) {
        Value value = this.left.getValue(sessionLocal);
        if (value == ValueNull.INSTANCE) {
            return ValueNull.INSTANCE;
        }
        return ValueBoolean.get(this.getValue(value));
    }

    @Override
    public boolean getWhenValue(SessionLocal sessionLocal, Value value) {
        if (!this.whenOperand) {
            return super.getWhenValue(sessionLocal, value);
        }
        if (value == ValueNull.INSTANCE) {
            return false;
        }
        return this.getValue(value);
    }

    private boolean getValue(Value value) {
        boolean bl;
        switch (value.getValueType()) {
            case 5: 
            case 6: 
            case 7: {
                byte[] byArray = value.getBytesNoCopy();
                JSONValidationTarget jSONValidationTarget = this.withUniqueKeys ? new JSONValidationTargetWithUniqueKeys() : new JSONValidationTargetWithoutUniqueKeys();
                try {
                    bl = this.itemType.includes(JSONBytesSource.parse(byArray, jSONValidationTarget)) ^ this.not;
                }
                catch (RuntimeException runtimeException) {
                    bl = this.not;
                }
                break;
            }
            case 38: {
                Object object = ((ValueJson)value).getItemType();
                if (!this.itemType.includes((JSONItemType)((Object)object))) {
                    bl = this.not;
                    break;
                }
                if (!this.withUniqueKeys || object == JSONItemType.SCALAR) {
                    bl = !this.not;
                    break;
                }
            }
            case 1: 
            case 2: 
            case 3: 
            case 4: {
                Object object = value.getString();
                JSONValidationTarget jSONValidationTarget = this.withUniqueKeys ? new JSONValidationTargetWithUniqueKeys() : new JSONValidationTargetWithoutUniqueKeys();
                try {
                    bl = this.itemType.includes(JSONStringSource.parse(object, jSONValidationTarget)) ^ this.not;
                }
                catch (RuntimeException runtimeException) {
                    bl = this.not;
                }
                break;
            }
            default: {
                bl = this.not;
            }
        }
        return bl;
    }

    @Override
    public boolean isWhenConditionOperand() {
        return this.whenOperand;
    }

    @Override
    public Expression getNotIfPossible(SessionLocal sessionLocal) {
        if (this.whenOperand) {
            return null;
        }
        return new IsJsonPredicate(this.left, !this.not, false, this.withUniqueKeys, this.itemType);
    }

    @Override
    public void setEvaluatable(TableFilter tableFilter, boolean bl) {
        this.left.setEvaluatable(tableFilter, bl);
    }

    @Override
    public void updateAggregate(SessionLocal sessionLocal, int n) {
        this.left.updateAggregate(sessionLocal, n);
    }

    @Override
    public void mapColumns(ColumnResolver columnResolver, int n, int n2) {
        this.left.mapColumns(columnResolver, n, n2);
    }

    @Override
    public boolean isEverything(ExpressionVisitor expressionVisitor) {
        return this.left.isEverything(expressionVisitor);
    }

    @Override
    public int getCost() {
        int n = this.left.getCost();
        n = !(this.left.getType().getValueType() != 38 || this.withUniqueKeys && this.itemType != JSONItemType.SCALAR) ? ++n : (n += 10);
        return n;
    }

    @Override
    public int getSubexpressionCount() {
        return 1;
    }

    @Override
    public Expression getSubexpression(int n) {
        if (n == 0) {
            return this.left;
        }
        throw new IndexOutOfBoundsException();
    }
}

