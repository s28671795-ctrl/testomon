/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression.function;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import org.h2.engine.Mode;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.TypedValueExpression;
import org.h2.expression.function.FunctionN;
import org.h2.message.DbException;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueBoolean;
import org.h2.value.ValueNull;
import org.h2.value.ValueVarchar;

public final class RegexpFunction
extends FunctionN {
    public static final int REGEXP_LIKE = 0;
    public static final int REGEXP_REPLACE = 1;
    public static final int REGEXP_SUBSTR = 2;
    private static final String[] NAMES = new String[]{"REGEXP_LIKE", "REGEXP_REPLACE", "REGEXP_SUBSTR"};
    private final int function;

    public RegexpFunction(int n) {
        super(new Expression[n == 0 ? 3 : 6]);
        this.function = n;
    }

    @Override
    public Value getValue(SessionLocal sessionLocal) {
        Value value = this.args[0].getValue(sessionLocal);
        Value value2 = this.args[1].getValue(sessionLocal);
        int n = this.args.length;
        switch (this.function) {
            case 0: {
                Value value3;
                Value value4 = value3 = n >= 3 ? this.args[2].getValue(sessionLocal) : null;
                if (value == ValueNull.INSTANCE || value2 == ValueNull.INSTANCE || value3 == ValueNull.INSTANCE) {
                    return ValueNull.INSTANCE;
                }
                String string = value2.getString();
                String string2 = value3 != null ? value3.getString() : null;
                int n2 = RegexpFunction.makeRegexpFlags(string2, false);
                try {
                    value = ValueBoolean.get(Pattern.compile(string, n2).matcher(value.getString()).find());
                    break;
                }
                catch (PatternSyntaxException patternSyntaxException) {
                    throw DbException.get(22025, patternSyntaxException, string);
                }
            }
            case 1: {
                Value value5;
                String string = value.getString();
                if (sessionLocal.getMode().getEnum() == Mode.ModeEnum.Oracle) {
                    String string3;
                    String string4 = this.args[2].getValue(sessionLocal).getString();
                    int n3 = n >= 4 ? this.args[3].getValue(sessionLocal).getInt() : 1;
                    int n4 = n >= 5 ? this.args[4].getValue(sessionLocal).getInt() : 0;
                    String string5 = string3 = n >= 6 ? this.args[5].getValue(sessionLocal).getString() : null;
                    if (string == null) {
                        value = ValueNull.INSTANCE;
                        break;
                    }
                    String string6 = value2.getString();
                    value = RegexpFunction.regexpReplace(sessionLocal, string, string6 != null ? string6 : "", string4 != null ? string4 : "", n3, n4, string3);
                    break;
                }
                if (n > 4) {
                    throw DbException.get(7001, this.getName(), "3..4");
                }
                Value value6 = this.args[2].getValue(sessionLocal);
                Value value7 = value5 = n == 4 ? this.args[3].getValue(sessionLocal) : null;
                if (value == ValueNull.INSTANCE || value2 == ValueNull.INSTANCE || value6 == ValueNull.INSTANCE || value5 == ValueNull.INSTANCE) {
                    value = ValueNull.INSTANCE;
                    break;
                }
                value = RegexpFunction.regexpReplace(sessionLocal, string, value2.getString(), value6.getString(), 1, 0, value5 != null ? value5.getString() : null);
                break;
            }
            case 2: {
                Value value8 = n >= 3 ? this.args[2].getValue(sessionLocal) : null;
                Value value9 = n >= 4 ? this.args[3].getValue(sessionLocal) : null;
                Value value10 = n >= 5 ? this.args[4].getValue(sessionLocal) : null;
                Value value11 = n >= 6 ? this.args[5].getValue(sessionLocal) : null;
                value = RegexpFunction.regexpSubstr(value, value2, value8, value9, value10, value11, sessionLocal);
                break;
            }
            default: {
                throw DbException.getInternalError("function=" + this.function);
            }
        }
        return value;
    }

    private static Value regexpReplace(SessionLocal sessionLocal, String string, String string2, String string3, int n, int n2, String string4) {
        int n3;
        Mode mode = sessionLocal.getMode();
        if (mode.regexpReplaceBackslashReferences && (string3.indexOf(92) >= 0 || string3.indexOf(36) >= 0)) {
            StringBuilder stringBuilder = new StringBuilder();
            for (n3 = 0; n3 < string3.length(); ++n3) {
                char c = string3.charAt(n3);
                if (c == '$') {
                    stringBuilder.append('\\');
                } else if (c == '\\' && ++n3 < string3.length()) {
                    c = string3.charAt(n3);
                    stringBuilder.append((char)(c >= '0' && c <= '9' ? 36 : 92));
                }
                stringBuilder.append(c);
            }
            string3 = stringBuilder.toString();
        }
        boolean bl = mode.getEnum() == Mode.ModeEnum.PostgreSQL;
        n3 = RegexpFunction.makeRegexpFlags(string4, bl);
        if (bl && (string4 == null || string4.isEmpty() || !string4.contains("g"))) {
            n2 = 1;
        }
        try {
            Matcher matcher = Pattern.compile(string2, n3).matcher(string).region(n - 1, string.length());
            if (n2 == 0) {
                return ValueVarchar.get(matcher.replaceAll(string3), sessionLocal);
            }
            StringBuffer stringBuffer = new StringBuffer();
            int n4 = 1;
            while (matcher.find()) {
                if (n4 == n2) {
                    matcher.appendReplacement(stringBuffer, string3);
                    break;
                }
                ++n4;
            }
            matcher.appendTail(stringBuffer);
            return ValueVarchar.get(stringBuffer.toString(), sessionLocal);
        }
        catch (PatternSyntaxException patternSyntaxException) {
            throw DbException.get(22025, patternSyntaxException, string2);
        }
        catch (IllegalArgumentException | StringIndexOutOfBoundsException runtimeException) {
            throw DbException.get(22025, runtimeException, string3);
        }
    }

    private static Value regexpSubstr(Value value, Value value2, Value value3, Value value4, Value value5, Value value6, SessionLocal sessionLocal) {
        if (value == ValueNull.INSTANCE || value2 == ValueNull.INSTANCE || value3 == ValueNull.INSTANCE || value4 == ValueNull.INSTANCE || value6 == ValueNull.INSTANCE) {
            return ValueNull.INSTANCE;
        }
        String string = value2.getString();
        int n = value3 != null ? value3.getInt() - 1 : 0;
        int n2 = value4 != null ? value4.getInt() : 1;
        String string2 = value5 != null ? value5.getString() : null;
        int n3 = value6 != null ? value6.getInt() : 0;
        int n4 = RegexpFunction.makeRegexpFlags(string2, false);
        try {
            Matcher matcher = Pattern.compile(string, n4).matcher(value.getString());
            boolean bl = matcher.find(n);
            for (int i = 1; i < n2 && bl; ++i) {
                bl = matcher.find();
            }
            if (!bl) {
                return ValueNull.INSTANCE;
            }
            return ValueVarchar.get(matcher.group(n3), sessionLocal);
        }
        catch (PatternSyntaxException patternSyntaxException) {
            throw DbException.get(22025, patternSyntaxException, string);
        }
        catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            return ValueNull.INSTANCE;
        }
    }

    private static int makeRegexpFlags(String string, boolean bl) {
        int n = 64;
        if (string != null) {
            block7: for (int i = 0; i < string.length(); ++i) {
                switch (string.charAt(i)) {
                    case 'i': {
                        n |= 2;
                        continue block7;
                    }
                    case 'c': {
                        n &= 0xFFFFFFFD;
                        continue block7;
                    }
                    case 'n': {
                        n |= 0x20;
                        continue block7;
                    }
                    case 'm': {
                        n |= 8;
                        continue block7;
                    }
                    case 'g': {
                        if (bl) continue block7;
                    }
                    default: {
                        throw DbException.get(90008, string);
                    }
                }
            }
        }
        return n;
    }

    @Override
    public Expression optimize(SessionLocal sessionLocal) {
        int n;
        int n2;
        boolean bl = this.optimizeArguments(sessionLocal, true);
        switch (this.function) {
            case 0: {
                n2 = 2;
                n = 3;
                this.type = TypeInfo.TYPE_BOOLEAN;
                break;
            }
            case 1: {
                n2 = 3;
                n = 6;
                this.type = TypeInfo.TYPE_VARCHAR;
                break;
            }
            case 2: {
                n2 = 2;
                n = 6;
                this.type = TypeInfo.TYPE_VARCHAR;
                break;
            }
            default: {
                throw DbException.getInternalError("function=" + this.function);
            }
        }
        int n3 = this.args.length;
        if (n3 < n2 || n3 > n) {
            throw DbException.get(7001, this.getName(), n2 + ".." + n);
        }
        if (bl) {
            return TypedValueExpression.getTypedIfNull(this.getValue(sessionLocal), this.type);
        }
        return this;
    }

    @Override
    public String getName() {
        return NAMES[this.function];
    }
}

