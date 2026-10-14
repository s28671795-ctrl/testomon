/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression.function;

import org.h2.engine.SessionLocal;
import org.h2.expression.ExpressionVisitor;
import org.h2.expression.Operation0;
import org.h2.expression.function.NamedExpression;
import org.h2.value.TypeInfo;
import org.h2.value.Value;

public final class CurrentDateTimeValueFunction
extends Operation0
implements NamedExpression {
    public static final int CURRENT_DATE = 0;
    public static final int CURRENT_TIME = 1;
    public static final int LOCALTIME = 2;
    public static final int CURRENT_TIMESTAMP = 3;
    public static final int LOCALTIMESTAMP = 4;
    private static final int[] TYPES = new int[]{17, 19, 18, 21, 20};
    private static final String[] NAMES = new String[]{"CURRENT_DATE", "CURRENT_TIME", "LOCALTIME", "CURRENT_TIMESTAMP", "LOCALTIMESTAMP"};
    private final int function;
    private final int scale;
    private final TypeInfo type;

    public static String getName(int n) {
        return NAMES[n];
    }

    public CurrentDateTimeValueFunction(int n, int n2) {
        this.function = n;
        this.scale = n2;
        if (n2 < 0) {
            n2 = n >= 3 ? 6 : 0;
        }
        this.type = TypeInfo.getTypeInfo(TYPES[n], 0L, n2, null);
    }

    @Override
    public Value getValue(SessionLocal sessionLocal) {
        return sessionLocal.currentTimestamp().castTo(this.type, sessionLocal);
    }

    @Override
    public StringBuilder getUnenclosedSQL(StringBuilder stringBuilder, int n) {
        stringBuilder.append(this.getName());
        if (this.scale >= 0) {
            stringBuilder.append('(').append(this.scale).append(')');
        }
        return stringBuilder;
    }

    @Override
    public boolean isEverything(ExpressionVisitor expressionVisitor) {
        switch (expressionVisitor.getType()) {
            case 2: {
                return false;
            }
        }
        return true;
    }

    @Override
    public TypeInfo getType() {
        return this.type;
    }

    @Override
    public int getCost() {
        return 1;
    }

    @Override
    public String getName() {
        return NAMES[this.function];
    }
}

