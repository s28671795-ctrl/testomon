/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression.function;

import java.nio.charset.StandardCharsets;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.TypedValueExpression;
import org.h2.expression.function.Function1_2;
import org.h2.message.DbException;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueInteger;
import org.h2.value.ValueVarchar;

public final class SoundexFunction
extends Function1_2 {
    public static final int SOUNDEX = 0;
    public static final int DIFFERENCE = 1;
    private static final String[] NAMES = new String[]{"SOUNDEX", "DIFFERENCE"};
    private static final byte[] SOUNDEX_INDEX = "71237128722455712623718272\u0000\u0000\u0000\u0000\u0000\u000071237128722455712623718272".getBytes(StandardCharsets.ISO_8859_1);
    private final int function;

    public SoundexFunction(Expression expression, Expression expression2, int n) {
        super(expression, expression2);
        this.function = n;
    }

    @Override
    public Value getValue(SessionLocal sessionLocal, Value value, Value value2) {
        switch (this.function) {
            case 0: {
                value = ValueVarchar.get(new String(SoundexFunction.getSoundex(value.getString()), StandardCharsets.ISO_8859_1), sessionLocal);
                break;
            }
            case 1: {
                value = ValueInteger.get(SoundexFunction.getDifference(value.getString(), value2.getString()));
                break;
            }
            default: {
                throw DbException.getInternalError("function=" + this.function);
            }
        }
        return value;
    }

    private static int getDifference(String string, String string2) {
        byte[] byArray = SoundexFunction.getSoundex(string);
        byte[] byArray2 = SoundexFunction.getSoundex(string2);
        int n = 0;
        for (int i = 0; i < 4; ++i) {
            if (byArray[i] != byArray2[i]) continue;
            ++n;
        }
        return n;
    }

    private static byte[] getSoundex(String string) {
        byte[] byArray = new byte[]{48, 48, 48, 48};
        byte by = 48;
        int n = 0;
        int n2 = string.length();
        for (int i = 0; i < n2 && n < 4; ++i) {
            byte by2;
            char c = string.charAt(i);
            if (c < 'A' || c > 'z' || (by2 = SOUNDEX_INDEX[c - 65]) == 0) continue;
            if (n == 0) {
                byArray[n++] = (byte)(c & 0xDF);
                by = by2;
                continue;
            }
            if (by2 <= 54) {
                if (by2 == by) continue;
                byArray[n++] = by = by2;
                continue;
            }
            if (by2 != 55) continue;
            by = by2;
        }
        return byArray;
    }

    @Override
    public Expression optimize(SessionLocal sessionLocal) {
        this.left = this.left.optimize(sessionLocal);
        if (this.right != null) {
            this.right = this.right.optimize(sessionLocal);
        }
        switch (this.function) {
            case 0: {
                this.type = TypeInfo.getTypeInfo(2, 4L, 0, null);
                break;
            }
            case 1: {
                this.type = TypeInfo.TYPE_INTEGER;
                break;
            }
            default: {
                throw DbException.getInternalError("function=" + this.function);
            }
        }
        if (this.left.isConstant() && (this.right == null || this.right.isConstant())) {
            return TypedValueExpression.getTypedIfNull(this.getValue(sessionLocal), this.type);
        }
        return this;
    }

    @Override
    public String getName() {
        return NAMES[this.function];
    }
}

