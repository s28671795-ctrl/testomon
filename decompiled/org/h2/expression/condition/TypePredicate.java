/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression.condition;

import java.util.Arrays;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.condition.SimplePredicate;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueBoolean;
import org.h2.value.ValueNull;

public final class TypePredicate
extends SimplePredicate {
    private final TypeInfo[] typeList;
    private int[] valueTypes;

    public TypePredicate(Expression expression, boolean bl, boolean bl2, TypeInfo[] typeInfoArray) {
        super(expression, bl, bl2);
        this.typeList = typeInfoArray;
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
        stringBuilder.append(" OF (");
        for (int i = 0; i < this.typeList.length; ++i) {
            if (i > 0) {
                stringBuilder.append(", ");
            }
            this.typeList[i].getSQL(stringBuilder, n);
        }
        return stringBuilder.append(')');
    }

    @Override
    public Expression optimize(SessionLocal sessionLocal) {
        int n = this.typeList.length;
        this.valueTypes = new int[n];
        for (int i = 0; i < n; ++i) {
            this.valueTypes[i] = this.typeList[i].getValueType();
        }
        Arrays.sort(this.valueTypes);
        return super.optimize(sessionLocal);
    }

    @Override
    public Value getValue(SessionLocal sessionLocal) {
        Value value = this.left.getValue(sessionLocal);
        if (value == ValueNull.INSTANCE) {
            return ValueNull.INSTANCE;
        }
        return ValueBoolean.get(Arrays.binarySearch(this.valueTypes, value.getValueType()) >= 0 ^ this.not);
    }

    @Override
    public boolean getWhenValue(SessionLocal sessionLocal, Value value) {
        if (!this.whenOperand) {
            return super.getWhenValue(sessionLocal, value);
        }
        if (value == ValueNull.INSTANCE) {
            return false;
        }
        return Arrays.binarySearch(this.valueTypes, value.getValueType()) >= 0 ^ this.not;
    }

    @Override
    public Expression getNotIfPossible(SessionLocal sessionLocal) {
        if (this.whenOperand) {
            return null;
        }
        return new TypePredicate(this.left, !this.not, false, this.typeList);
    }
}

