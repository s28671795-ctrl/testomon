/*
 * Decompiled with CFR 0.152.
 */
package org.h2.mode;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import org.h2.engine.CastDataProvider;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.ValueExpression;
import org.h2.message.DbException;
import org.h2.mode.FunctionInfo;
import org.h2.mode.ModeFunction;
import org.h2.util.DateTimeUtils;
import org.h2.util.StringUtils;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueBigint;
import org.h2.value.ValueInteger;
import org.h2.value.ValueNull;
import org.h2.value.ValueTimestamp;
import org.h2.value.ValueTimestampTimeZone;
import org.h2.value.ValueVarchar;

public final class FunctionsMySQL
extends ModeFunction {
    private static final int UNIX_TIMESTAMP = 1001;
    private static final int FROM_UNIXTIME = 1002;
    private static final int DATE = 1003;
    private static final int LAST_INSERT_ID = 1004;
    private static final HashMap<String, FunctionInfo> FUNCTIONS = new HashMap();
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String[] FORMAT_REPLACE;

    public static int unixTimestamp(SessionLocal sessionLocal, Value value) {
        long l;
        if (value instanceof ValueTimestampTimeZone) {
            ValueTimestampTimeZone valueTimestampTimeZone = (ValueTimestampTimeZone)value;
            long l2 = valueTimestampTimeZone.getTimeNanos();
            l = DateTimeUtils.absoluteDayFromDateValue(valueTimestampTimeZone.getDateValue()) * 86400L + l2 / 1000000000L - (long)valueTimestampTimeZone.getTimeZoneOffsetSeconds();
        } else {
            ValueTimestamp valueTimestamp = (ValueTimestamp)value.convertTo(TypeInfo.TYPE_TIMESTAMP, (CastDataProvider)sessionLocal);
            long l3 = valueTimestamp.getTimeNanos();
            l = sessionLocal.currentTimeZone().getEpochSecondsFromLocal(valueTimestamp.getDateValue(), l3);
        }
        return (int)l;
    }

    public static String fromUnixTime(int n) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_TIME_FORMAT, Locale.ENGLISH);
        return simpleDateFormat.format(new Date((long)n * 1000L));
    }

    public static String fromUnixTime(int n, String string) {
        string = FunctionsMySQL.convertToSimpleDateFormat(string);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(string, Locale.ENGLISH);
        return simpleDateFormat.format(new Date((long)n * 1000L));
    }

    private static String convertToSimpleDateFormat(String string) {
        String[] stringArray = FORMAT_REPLACE;
        for (int i = 0; i < stringArray.length; i += 2) {
            string = StringUtils.replaceAll(string, stringArray[i], stringArray[i + 1]);
        }
        return string;
    }

    public static FunctionsMySQL getFunction(String string) {
        FunctionInfo functionInfo = FUNCTIONS.get(string);
        return functionInfo != null ? new FunctionsMySQL(functionInfo) : null;
    }

    FunctionsMySQL(FunctionInfo functionInfo) {
        super(functionInfo);
    }

    @Override
    protected void checkParameterCount(int n) {
        int n2;
        int n3;
        switch (this.info.type) {
            case 1001: {
                n3 = 0;
                n2 = 1;
                break;
            }
            case 1002: {
                n3 = 1;
                n2 = 2;
                break;
            }
            case 1003: {
                n3 = 1;
                n2 = 1;
                break;
            }
            case 1004: {
                n3 = 0;
                n2 = 1;
                break;
            }
            default: {
                throw DbException.getInternalError("type=" + this.info.type);
            }
        }
        if (n < n3 || n > n2) {
            throw DbException.get(7001, this.info.name, n3 + ".." + n2);
        }
    }

    @Override
    public Expression optimize(SessionLocal sessionLocal) {
        boolean bl = this.optimizeArguments(sessionLocal);
        this.type = TypeInfo.getTypeInfo(this.info.returnDataType);
        if (bl) {
            return ValueExpression.get(this.getValue(sessionLocal));
        }
        return this;
    }

    @Override
    public Value getValue(SessionLocal sessionLocal) {
        Value value;
        Value[] valueArray = this.getArgumentsValues(sessionLocal, this.args);
        if (valueArray == null) {
            return ValueNull.INSTANCE;
        }
        Value value2 = FunctionsMySQL.getNullOrValue(sessionLocal, this.args, valueArray, 0);
        Value value3 = FunctionsMySQL.getNullOrValue(sessionLocal, this.args, valueArray, 1);
        block1 : switch (this.info.type) {
            case 1001: {
                value = ValueInteger.get(FunctionsMySQL.unixTimestamp(sessionLocal, value2 == null ? sessionLocal.currentTimestamp() : value2));
                break;
            }
            case 1002: {
                value = ValueVarchar.get(value3 == null ? FunctionsMySQL.fromUnixTime(value2.getInt()) : FunctionsMySQL.fromUnixTime(value2.getInt(), value3.getString()));
                break;
            }
            case 1003: {
                switch (value2.getValueType()) {
                    case 17: {
                        value = value2;
                        break block1;
                    }
                    default: {
                        try {
                            value2 = value2.convertTo(TypeInfo.TYPE_TIMESTAMP, (CastDataProvider)sessionLocal);
                            break;
                        }
                        catch (DbException dbException) {
                            value = ValueNull.INSTANCE;
                            break block1;
                        }
                    }
                    case 20: 
                    case 21: 
                }
                value = value2.convertToDate(sessionLocal);
                break;
            }
            case 1004: {
                if (this.args.length == 0) {
                    value = sessionLocal.getLastIdentity();
                    if (value == ValueNull.INSTANCE) {
                        value = ValueBigint.get(0L);
                        break;
                    }
                    value = value.convertToBigint(null);
                    break;
                }
                value = value2;
                if (value == ValueNull.INSTANCE) {
                    sessionLocal.setLastIdentity(ValueNull.INSTANCE);
                    break;
                }
                value = value.convertToBigint(null);
                sessionLocal.setLastIdentity(value);
                break;
            }
            default: {
                throw DbException.getInternalError("type=" + this.info.type);
            }
        }
        return value;
    }

    static {
        FUNCTIONS.put("UNIX_TIMESTAMP", new FunctionInfo("UNIX_TIMESTAMP", 1001, -1, 11, true, false));
        FUNCTIONS.put("FROM_UNIXTIME", new FunctionInfo("FROM_UNIXTIME", 1002, -1, 2, true, true));
        FUNCTIONS.put("DATE", new FunctionInfo("DATE", 1003, 1, 17, true, true));
        FUNCTIONS.put("LAST_INSERT_ID", new FunctionInfo("LAST_INSERT_ID", 1004, -1, 12, false, false));
        FORMAT_REPLACE = new String[]{"%a", "EEE", "%b", "MMM", "%c", "MM", "%d", "dd", "%e", "d", "%H", "HH", "%h", "hh", "%I", "hh", "%i", "mm", "%j", "DDD", "%k", "H", "%l", "h", "%M", "MMMM", "%m", "MM", "%p", "a", "%r", "hh:mm:ss a", "%S", "ss", "%s", "ss", "%T", "HH:mm:ss", "%W", "EEEE", "%w", "F", "%Y", "yyyy", "%y", "yy", "%%", "%"};
    }
}

