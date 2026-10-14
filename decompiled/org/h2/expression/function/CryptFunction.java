/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression.function;

import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.TypedValueExpression;
import org.h2.expression.function.FunctionN;
import org.h2.message.DbException;
import org.h2.security.BlockCipher;
import org.h2.security.CipherFactory;
import org.h2.util.MathUtils;
import org.h2.util.Utils;
import org.h2.value.DataType;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueVarbinary;

public final class CryptFunction
extends FunctionN {
    public static final int ENCRYPT = 0;
    public static final int DECRYPT = 1;
    private static final String[] NAMES = new String[]{"ENCRYPT", "DECRYPT"};
    private final int function;

    public CryptFunction(Expression expression, Expression expression2, Expression expression3, int n) {
        super(new Expression[]{expression, expression2, expression3});
        this.function = n;
    }

    @Override
    public Value getValue(SessionLocal sessionLocal, Value value, Value value2, Value value3) {
        BlockCipher blockCipher = CipherFactory.getBlockCipher(value.getString());
        blockCipher.setKey(CryptFunction.getPaddedArrayCopy(value2.getBytesNoCopy(), blockCipher.getKeyLength()));
        byte[] byArray = CryptFunction.getPaddedArrayCopy(value3.getBytesNoCopy(), 16);
        switch (this.function) {
            case 0: {
                blockCipher.encrypt(byArray, 0, byArray.length);
                break;
            }
            case 1: {
                blockCipher.decrypt(byArray, 0, byArray.length);
                break;
            }
            default: {
                throw DbException.getInternalError("function=" + this.function);
            }
        }
        return ValueVarbinary.getNoCopy(byArray);
    }

    private static byte[] getPaddedArrayCopy(byte[] byArray, int n) {
        return Utils.copyBytes(byArray, MathUtils.roundUpInt(byArray.length, n));
    }

    @Override
    public Expression optimize(SessionLocal sessionLocal) {
        boolean bl = this.optimizeArguments(sessionLocal, true);
        TypeInfo typeInfo = this.args[2].getType();
        TypeInfo typeInfo2 = this.type = DataType.isBinaryStringType(typeInfo.getValueType()) ? TypeInfo.getTypeInfo(6, typeInfo.getPrecision(), 0, null) : TypeInfo.TYPE_VARBINARY;
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

