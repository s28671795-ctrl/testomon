/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression.function;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import org.h2.engine.Mode;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.TypedValueExpression;
import org.h2.expression.function.Function1;
import org.h2.message.DbException;
import org.h2.util.StringUtils;
import org.h2.value.DataType;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueInteger;
import org.h2.value.ValueNull;
import org.h2.value.ValueVarbinary;
import org.h2.value.ValueVarchar;

public final class StringFunction1
extends Function1 {
    public static final int UPPER = 0;
    public static final int LOWER = 1;
    public static final int ASCII = 2;
    public static final int CHAR = 3;
    public static final int STRINGENCODE = 4;
    public static final int STRINGDECODE = 5;
    public static final int STRINGTOUTF8 = 6;
    public static final int UTF8TOSTRING = 7;
    public static final int HEXTORAW = 8;
    public static final int RAWTOHEX = 9;
    public static final int SPACE = 10;
    public static final int QUOTE_IDENT = 11;
    private static final String[] NAMES = new String[]{"UPPER", "LOWER", "ASCII", "CHAR", "STRINGENCODE", "STRINGDECODE", "STRINGTOUTF8", "UTF8TOSTRING", "HEXTORAW", "RAWTOHEX", "SPACE", "QUOTE_IDENT"};
    private final int function;

    public StringFunction1(Expression expression, int n) {
        super(expression);
        this.function = n;
    }

    @Override
    public Value getValue(SessionLocal sessionLocal) {
        Value value = this.arg.getValue(sessionLocal);
        if (value == ValueNull.INSTANCE) {
            return ValueNull.INSTANCE;
        }
        switch (this.function) {
            case 0: {
                value = ValueVarchar.get(value.getString().toUpperCase(), sessionLocal);
                break;
            }
            case 1: {
                value = ValueVarchar.get(value.getString().toLowerCase(), sessionLocal);
                break;
            }
            case 2: {
                String string = value.getString();
                value = string.isEmpty() ? ValueNull.INSTANCE : ValueInteger.get(string.charAt(0));
                break;
            }
            case 3: {
                value = ValueVarchar.get(String.valueOf((char)value.getInt()), sessionLocal);
                break;
            }
            case 4: {
                value = ValueVarchar.get(StringUtils.javaEncode(value.getString()), sessionLocal);
                break;
            }
            case 5: {
                value = ValueVarchar.get(StringUtils.javaDecode(value.getString()), sessionLocal);
                break;
            }
            case 6: {
                value = ValueVarbinary.getNoCopy(value.getString().getBytes(StandardCharsets.UTF_8));
                break;
            }
            case 7: {
                value = ValueVarchar.get(new String(value.getBytesNoCopy(), StandardCharsets.UTF_8), sessionLocal);
                break;
            }
            case 8: {
                value = StringFunction1.hexToRaw(value.getString(), sessionLocal);
                break;
            }
            case 9: {
                value = ValueVarchar.get(StringFunction1.rawToHex(value, sessionLocal.getMode()), sessionLocal);
                break;
            }
            case 10: {
                byte[] byArray = new byte[Math.max(0, value.getInt())];
                Arrays.fill(byArray, (byte)32);
                value = ValueVarchar.get(new String(byArray, StandardCharsets.ISO_8859_1), sessionLocal);
                break;
            }
            case 11: {
                value = ValueVarchar.get(StringUtils.quoteIdentifier(value.getString()), sessionLocal);
                break;
            }
            default: {
                throw DbException.getInternalError("function=" + this.function);
            }
        }
        return value;
    }

    private static Value hexToRaw(String string, SessionLocal sessionLocal) {
        if (sessionLocal.getMode().getEnum() == Mode.ModeEnum.Oracle) {
            return ValueVarbinary.get(StringUtils.convertHexToBytes(string));
        }
        int n = string.length();
        if (n % 4 != 0) {
            throw DbException.get(22018, string);
        }
        StringBuilder stringBuilder = new StringBuilder(n / 4);
        for (int i = 0; i < n; i += 4) {
            try {
                stringBuilder.append((char)Integer.parseInt(string.substring(i, i + 4), 16));
                continue;
            }
            catch (NumberFormatException numberFormatException) {
                throw DbException.get(22018, string);
            }
        }
        return ValueVarchar.get(stringBuilder.toString(), sessionLocal);
    }

    private static String rawToHex(Value value, Mode mode) {
        if (DataType.isBinaryStringOrSpecialBinaryType(value.getValueType())) {
            return StringUtils.convertBytesToHex(value.getBytesNoCopy());
        }
        String string = value.getString();
        if (mode.getEnum() == Mode.ModeEnum.Oracle) {
            return StringUtils.convertBytesToHex(string.getBytes(StandardCharsets.UTF_8));
        }
        int n = string.length();
        StringBuilder stringBuilder = new StringBuilder(4 * n);
        for (int i = 0; i < n; ++i) {
            String string2 = Integer.toHexString(string.charAt(i) & 0xFFFF);
            for (int j = string2.length(); j < 4; ++j) {
                stringBuilder.append('0');
            }
            stringBuilder.append(string2);
        }
        return stringBuilder.toString();
    }

    @Override
    public Expression optimize(SessionLocal sessionLocal) {
        this.arg = this.arg.optimize(sessionLocal);
        switch (this.function) {
            case 0: 
            case 1: 
            case 4: 
            case 10: 
            case 11: {
                this.type = TypeInfo.TYPE_VARCHAR;
                break;
            }
            case 2: {
                this.type = TypeInfo.TYPE_INTEGER;
                break;
            }
            case 3: {
                this.type = TypeInfo.getTypeInfo(2, 1L, 0, null);
                break;
            }
            case 5: {
                TypeInfo typeInfo = this.arg.getType();
                this.type = DataType.isCharacterStringType(typeInfo.getValueType()) ? TypeInfo.getTypeInfo(2, typeInfo.getPrecision(), 0, null) : TypeInfo.TYPE_VARCHAR;
                break;
            }
            case 6: {
                this.type = TypeInfo.TYPE_VARBINARY;
                break;
            }
            case 7: {
                TypeInfo typeInfo = this.arg.getType();
                this.type = DataType.isBinaryStringType(typeInfo.getValueType()) ? TypeInfo.getTypeInfo(2, typeInfo.getPrecision(), 0, null) : TypeInfo.TYPE_VARCHAR;
                break;
            }
            case 8: {
                TypeInfo typeInfo = this.arg.getType();
                if (sessionLocal.getMode().getEnum() == Mode.ModeEnum.Oracle) {
                    if (DataType.isCharacterStringType(typeInfo.getValueType())) {
                        this.type = TypeInfo.getTypeInfo(6, typeInfo.getPrecision() / 2L, 0, null);
                        break;
                    }
                    this.type = TypeInfo.TYPE_VARBINARY;
                    break;
                }
                if (DataType.isCharacterStringType(typeInfo.getValueType())) {
                    this.type = TypeInfo.getTypeInfo(2, typeInfo.getPrecision() / 4L, 0, null);
                    break;
                }
                this.type = TypeInfo.TYPE_VARCHAR;
                break;
            }
            case 9: {
                TypeInfo typeInfo = this.arg.getType();
                long l = typeInfo.getPrecision();
                int n = DataType.isBinaryStringOrSpecialBinaryType(typeInfo.getValueType()) ? 2 : (sessionLocal.getMode().getEnum() == Mode.ModeEnum.Oracle ? 6 : 4);
                this.type = TypeInfo.getTypeInfo(2, l <= Long.MAX_VALUE / (long)n ? l * (long)n : Long.MAX_VALUE, 0, null);
                break;
            }
            default: {
                throw DbException.getInternalError("function=" + this.function);
            }
        }
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

