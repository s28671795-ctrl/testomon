/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression.function;

import java.text.DateFormatSymbols;
import java.util.Locale;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.TypedValueExpression;
import org.h2.expression.function.Function1;
import org.h2.message.DbException;
import org.h2.util.DateTimeUtils;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueNull;
import org.h2.value.ValueVarchar;

public final class DayMonthNameFunction
extends Function1 {
    public static final int DAYNAME = 0;
    public static final int MONTHNAME = 1;
    private static final String[] NAMES = new String[]{"DAYNAME", "MONTHNAME"};
    private static volatile String[][] MONTHS_AND_WEEKS;
    private final int function;

    public DayMonthNameFunction(Expression expression, int n) {
        super(expression);
        this.function = n;
    }

    @Override
    public Value getValue(SessionLocal sessionLocal) {
        String string;
        Value value = this.arg.getValue(sessionLocal);
        if (value == ValueNull.INSTANCE) {
            return ValueNull.INSTANCE;
        }
        long l = DateTimeUtils.dateAndTimeFromValue(value, sessionLocal)[0];
        switch (this.function) {
            case 0: {
                string = DayMonthNameFunction.getMonthsAndWeeks(1)[DateTimeUtils.getDayOfWeek(l, 0)];
                break;
            }
            case 1: {
                string = DayMonthNameFunction.getMonthsAndWeeks(0)[DateTimeUtils.monthFromDateValue(l) - 1];
                break;
            }
            default: {
                throw DbException.getInternalError("function=" + this.function);
            }
        }
        return ValueVarchar.get(string, sessionLocal);
    }

    private static String[] getMonthsAndWeeks(int n) {
        String[][] stringArray = MONTHS_AND_WEEKS;
        if (stringArray == null) {
            stringArray = new String[2][];
            DateFormatSymbols dateFormatSymbols = DateFormatSymbols.getInstance(Locale.ENGLISH);
            stringArray[0] = dateFormatSymbols.getMonths();
            stringArray[1] = dateFormatSymbols.getWeekdays();
            MONTHS_AND_WEEKS = stringArray;
        }
        return stringArray[n];
    }

    @Override
    public Expression optimize(SessionLocal sessionLocal) {
        this.arg = this.arg.optimize(sessionLocal);
        this.type = TypeInfo.getTypeInfo(2, 20L, 0, null);
        if (this.arg.isConstant()) {
            return TypedValueExpression.getTypedIfNull(this.getValue(sessionLocal), this.type);
        }
        return this;
    }

    @Override
    public String getName() {
        return NAMES[this.function];
    }
}

