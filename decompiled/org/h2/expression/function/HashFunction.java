/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression.function;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.TypedValueExpression;
import org.h2.expression.function.FunctionN;
import org.h2.message.DbException;
import org.h2.security.SHA3;
import org.h2.util.Bits;
import org.h2.util.StringUtils;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueBigint;
import org.h2.value.ValueNull;
import org.h2.value.ValueVarbinary;

public final class HashFunction
extends FunctionN {
    public static final int HASH = 0;
    public static final int ORA_HASH = 1;
    private static final String[] NAMES = new String[]{"HASH", "ORA_HASH"};
    private final int function;

    public HashFunction(Expression expression, int n) {
        super(new Expression[]{expression});
        this.function = n;
    }

    public HashFunction(Expression expression, Expression expression2, Expression expression3, int n) {
        Expression[] expressionArray;
        if (expression3 == null) {
            Expression[] expressionArray2 = new Expression[2];
            expressionArray2[0] = expression;
            expressionArray = expressionArray2;
            expressionArray2[1] = expression2;
        } else {
            Expression[] expressionArray3 = new Expression[3];
            expressionArray3[0] = expression;
            expressionArray3[1] = expression2;
            expressionArray = expressionArray3;
            expressionArray3[2] = expression3;
        }
        super(expressionArray);
        this.function = n;
    }

    @Override
    public Value getValue(SessionLocal sessionLocal, Value value, Value value2, Value value3) {
        switch (this.function) {
            case 0: {
                value = HashFunction.getHash(value.getString(), value2, value3 == null ? 1 : value3.getInt());
                break;
            }
            case 1: {
                value = HashFunction.oraHash(value, value2 == null ? 0xFFFFFFFFL : value2.getLong(), value3 == null ? 0L : value3.getLong());
                break;
            }
            default: {
                throw DbException.getInternalError("function=" + this.function);
            }
        }
        return value;
    }

    private static Value getHash(String string, Value value, int n) {
        MessageDigest messageDigest;
        if (n <= 0) {
            throw DbException.getInvalidValueException("iterations", n);
        }
        Object object = StringUtils.toUpperEnglish(string);
        int n2 = -1;
        switch (((String)object).hashCode()) {
            case 76158: {
                if (!((String)object).equals("MD5")) break;
                n2 = 0;
                break;
            }
            case 78861104: {
                if (!((String)object).equals("SHA-1")) break;
                n2 = 1;
                break;
            }
            case -1523887821: {
                if (!((String)object).equals("SHA-224")) break;
                n2 = 2;
                break;
            }
            case -1523887726: {
                if (!((String)object).equals("SHA-256")) break;
                n2 = 3;
                break;
            }
            case -1523886674: {
                if (!((String)object).equals("SHA-384")) break;
                n2 = 4;
                break;
            }
            case -1523884971: {
                if (!((String)object).equals("SHA-512")) break;
                n2 = 5;
                break;
            }
            case -1850268089: {
                if (!((String)object).equals("SHA256")) break;
                n2 = 6;
                break;
            }
            case 9509966: {
                if (!((String)object).equals("SHA3-224")) break;
                n2 = 7;
                break;
            }
            case 9510061: {
                if (!((String)object).equals("SHA3-256")) break;
                n2 = 8;
                break;
            }
            case 9511113: {
                if (!((String)object).equals("SHA3-384")) break;
                n2 = 9;
                break;
            }
            case 9512816: {
                if (!((String)object).equals("SHA3-512")) break;
                n2 = 10;
            }
        }
        switch (n2) {
            case 0: 
            case 1: 
            case 2: 
            case 3: 
            case 4: 
            case 5: {
                messageDigest = HashFunction.hashImpl(value, string);
                break;
            }
            case 6: {
                messageDigest = HashFunction.hashImpl(value, "SHA-256");
                break;
            }
            case 7: {
                messageDigest = HashFunction.hashImpl(value, SHA3.getSha3_224());
                break;
            }
            case 8: {
                messageDigest = HashFunction.hashImpl(value, SHA3.getSha3_256());
                break;
            }
            case 9: {
                messageDigest = HashFunction.hashImpl(value, SHA3.getSha3_384());
                break;
            }
            case 10: {
                messageDigest = HashFunction.hashImpl(value, SHA3.getSha3_512());
                break;
            }
            default: {
                throw DbException.getInvalidValueException("algorithm", string);
            }
        }
        object = messageDigest.digest();
        for (n2 = 1; n2 < n; ++n2) {
            object = messageDigest.digest((byte[])object);
        }
        return ValueVarbinary.getNoCopy((byte[])object);
    }

    private static Value oraHash(Value value, long l, long l2) {
        if ((l & 0xFFFFFFFF00000000L) != 0L) {
            throw DbException.getInvalidValueException("bucket", l);
        }
        if ((l2 & 0xFFFFFFFF00000000L) != 0L) {
            throw DbException.getInvalidValueException("seed", l2);
        }
        MessageDigest messageDigest = HashFunction.hashImpl(value, "SHA-1");
        if (messageDigest == null) {
            return ValueNull.INSTANCE;
        }
        if (l2 != 0L) {
            byte[] byArray = new byte[4];
            Bits.writeInt(byArray, 0, (int)l2);
            messageDigest.update(byArray);
        }
        long l3 = Bits.readLong(messageDigest.digest(), 0);
        return ValueBigint.get((l3 & Long.MAX_VALUE) % (l + 1L));
    }

    private static MessageDigest hashImpl(Value value, String string) {
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance(string);
        }
        catch (Exception exception) {
            throw DbException.convert(exception);
        }
        return HashFunction.hashImpl(value, messageDigest);
    }

    /*
     * Unable to fully structure code
     */
    private static MessageDigest hashImpl(Value var0, MessageDigest var1_1) {
        try {
            switch (var0.getValueType()) {
                case 1: 
                case 2: 
                case 4: {
                    var1_1.update(var0.getString().getBytes(StandardCharsets.UTF_8));
                    break;
                }
                case 3: 
                case 7: {
                    var2_2 = new byte[4096];
                    var3_4 = var0.getInputStream();
                    var4_5 = null;
                    while ((var5_6 = var3_4.read(var2_2)) > 0) {
                        var1_1.update(var2_2, 0, var5_6);
                    }
                    if (var3_4 == null) break;
                    if (var4_5 == null) ** GOTO lbl22
                    try {
                        var3_4.close();
                    }
                    catch (Throwable var5_7) {
                        var4_5.addSuppressed(var5_7);
                    }
                    break;
lbl22:
                    // 1 sources

                    var3_4.close();
                    break;
                    catch (Throwable var5_8) {
                        try {
                            var4_5 = var5_8;
                            throw var5_8;
                        }
                        catch (Throwable var6_9) {
                            if (var3_4 != null) {
                                if (var4_5 != null) {
                                    try {
                                        var3_4.close();
                                    }
                                    catch (Throwable var7_10) {
                                        var4_5.addSuppressed(var7_10);
                                    }
                                } else {
                                    var3_4.close();
                                }
                            }
                            throw var6_9;
                        }
                    }
                }
                default: {
                    var1_1.update(var0.getBytesNoCopy());
                }
            }
            return var1_1;
        }
        catch (Exception var2_3) {
            throw DbException.convert(var2_3);
        }
    }

    @Override
    public Expression optimize(SessionLocal sessionLocal) {
        boolean bl = this.optimizeArguments(sessionLocal, true);
        switch (this.function) {
            case 0: {
                this.type = TypeInfo.TYPE_VARBINARY;
                break;
            }
            case 1: {
                this.type = TypeInfo.TYPE_BIGINT;
                break;
            }
            default: {
                throw DbException.getInternalError("function=" + this.function);
            }
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

