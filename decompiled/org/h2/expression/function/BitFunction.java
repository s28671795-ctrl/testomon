/*
 * Decompiled with CFR 0.152.
 */
package org.h2.expression.function;

import java.util.Arrays;
import org.h2.engine.SessionLocal;
import org.h2.expression.Expression;
import org.h2.expression.TypedValueExpression;
import org.h2.expression.aggregate.Aggregate;
import org.h2.expression.aggregate.AggregateType;
import org.h2.expression.function.Function1_2;
import org.h2.message.DbException;
import org.h2.mvstore.db.Store;
import org.h2.util.Bits;
import org.h2.value.DataType;
import org.h2.value.TypeInfo;
import org.h2.value.Value;
import org.h2.value.ValueBigint;
import org.h2.value.ValueBinary;
import org.h2.value.ValueBoolean;
import org.h2.value.ValueInteger;
import org.h2.value.ValueSmallint;
import org.h2.value.ValueTinyint;
import org.h2.value.ValueVarbinary;

public final class BitFunction
extends Function1_2 {
    public static final int BITAND = 0;
    public static final int BITOR = 1;
    public static final int BITXOR = 2;
    public static final int BITNOT = 3;
    public static final int BITNAND = 4;
    public static final int BITNOR = 5;
    public static final int BITXNOR = 6;
    public static final int BITGET = 7;
    public static final int BITCOUNT = 8;
    public static final int LSHIFT = 9;
    public static final int RSHIFT = 10;
    public static final int ULSHIFT = 11;
    public static final int URSHIFT = 12;
    public static final int ROTATELEFT = 13;
    public static final int ROTATERIGHT = 14;
    private static final String[] NAMES = new String[]{"BITAND", "BITOR", "BITXOR", "BITNOT", "BITNAND", "BITNOR", "BITXNOR", "BITGET", "BITCOUNT", "LSHIFT", "RSHIFT", "ULSHIFT", "URSHIFT", "ROTATELEFT", "ROTATERIGHT"};
    private final int function;

    public BitFunction(Expression expression, Expression expression2, int n) {
        super(expression, expression2);
        this.function = n;
    }

    @Override
    public Value getValue(SessionLocal sessionLocal, Value value, Value value2) {
        switch (this.function) {
            case 7: {
                return BitFunction.bitGet(value, value2);
            }
            case 8: {
                return BitFunction.bitCount(value);
            }
            case 9: {
                return BitFunction.shift(value, value2.getLong(), false);
            }
            case 10: {
                long l = value2.getLong();
                return BitFunction.shift(value, l != Long.MIN_VALUE ? -l : Long.MAX_VALUE, false);
            }
            case 11: {
                return BitFunction.shift(value, value2.getLong(), true);
            }
            case 12: {
                return BitFunction.shift(value, -value2.getLong(), true);
            }
            case 13: {
                return BitFunction.rotate(value, value2.getLong(), false);
            }
            case 14: {
                return BitFunction.rotate(value, value2.getLong(), true);
            }
        }
        return BitFunction.getBitwise(this.function, this.type, value, value2);
    }

    private static ValueBoolean bitGet(Value value, Value value2) {
        boolean bl;
        block8: {
            block7: {
                long l = value2.getLong();
                if (l < 0L) break block7;
                switch (value.getValueType()) {
                    case 5: 
                    case 6: {
                        byte[] byArray = value.getBytesNoCopy();
                        int n = (int)(l & 7L);
                        bl = (l >>>= 3) < (long)byArray.length && (byArray[(int)l] & 1 << n) != 0;
                        break block8;
                    }
                    case 9: {
                        bl = l < 8L && (value.getByte() & 1 << (int)l) != 0;
                        break block8;
                    }
                    case 10: {
                        bl = l < 16L && (value.getShort() & 1 << (int)l) != 0;
                        break block8;
                    }
                    case 11: {
                        bl = l < 32L && (value.getInt() & 1 << (int)l) != 0;
                        break block8;
                    }
                    case 12: {
                        bl = (value.getLong() & 1L << (int)l) != 0L;
                        break block8;
                    }
                    default: {
                        throw DbException.getInvalidValueException("bit function parameter", value.getTraceSQL());
                    }
                }
            }
            bl = false;
        }
        return ValueBoolean.get(bl);
    }

    private static ValueBigint bitCount(Value value) {
        long l;
        switch (value.getValueType()) {
            case 5: 
            case 6: {
                int n;
                byte[] byArray = value.getBytesNoCopy();
                int n2 = byArray.length;
                l = 0L;
                int n3 = n2 & 0xFFFFFFF8;
                for (n = 0; n < n3; n += 8) {
                    l += (long)Long.bitCount(Bits.readLong(byArray, n));
                }
                while (n < n2) {
                    l += (long)Integer.bitCount(byArray[n] & 0xFF);
                    ++n;
                }
                break;
            }
            case 9: {
                l = Integer.bitCount(value.getByte() & 0xFF);
                break;
            }
            case 10: {
                l = Integer.bitCount(value.getShort() & 0xFFFF);
                break;
            }
            case 11: {
                l = Integer.bitCount(value.getInt());
                break;
            }
            case 12: {
                l = Long.bitCount(value.getLong());
                break;
            }
            default: {
                throw DbException.getInvalidValueException("bit function parameter", value.getTraceSQL());
            }
        }
        return ValueBigint.get(l);
    }

    private static Value shift(Value value, long l, boolean bl) {
        if (l == 0L) {
            return value;
        }
        int n = value.getValueType();
        switch (n) {
            case 5: 
            case 6: {
                byte[] byArray = value.getBytesNoCopy();
                int n2 = byArray.length;
                if (n2 == 0) {
                    return value;
                }
                byte[] byArray2 = new byte[n2];
                if (l > -8L * (long)n2 && l < 8L * (long)n2) {
                    if (l > 0L) {
                        int n3 = (int)(l >> 3);
                        int n4 = (int)l & 7;
                        if (n4 == 0) {
                            System.arraycopy(byArray, n3, byArray2, 0, n2 - n3);
                        } else {
                            int n5 = 8 - n4;
                            int n6 = 0;
                            int n7 = n3;
                            --n2;
                            while (n7 < n2) {
                                byArray2[n6++] = (byte)(byArray[n7++] << n4 | (byArray[n7] & 0xFF) >>> n5);
                            }
                            byArray2[n6] = (byte)(byArray[n7] << n4);
                        }
                    } else {
                        l = -l;
                        int n8 = (int)(l >> 3);
                        int n9 = (int)l & 7;
                        if (n9 == 0) {
                            System.arraycopy(byArray, 0, byArray2, n8, n2 - n8);
                        } else {
                            int n10 = 8 - n9;
                            int n11 = n8;
                            int n12 = 0;
                            byArray2[n11++] = (byte)((byArray[n12] & 0xFF) >>> n9);
                            while (n11 < n2) {
                                byArray2[n11++] = (byte)(byArray[n12++] << n10 | (byArray[n12] & 0xFF) >>> n9);
                            }
                        }
                    }
                }
                return n == 5 ? ValueBinary.getNoCopy(byArray2) : ValueVarbinary.getNoCopy(byArray2);
            }
            case 9: {
                byte by;
                if (l < 8L) {
                    by = value.getByte();
                    by = l > -8L ? (l > 0L ? (byte)(by << (int)l) : (bl ? (byte)((by & 0xFF) >>> (int)(-l)) : (byte)(by >> (int)(-l)))) : (bl ? (byte)0 : (byte)(by >> 7));
                } else {
                    by = 0;
                }
                return ValueTinyint.get(by);
            }
            case 10: {
                short s2;
                if (l < 16L) {
                    s2 = value.getShort();
                    s2 = l > -16L ? (l > 0L ? (short)(s2 << (int)l) : (bl ? (short)((s2 & 0xFFFF) >>> (int)(-l)) : (short)(s2 >> (int)(-l)))) : (bl ? (short)0 : (short)(s2 >> 15));
                } else {
                    s2 = 0;
                }
                return ValueSmallint.get(s2);
            }
            case 11: {
                int n13;
                if (l < 32L) {
                    n13 = value.getInt();
                    n13 = l > -32L ? (l > 0L ? (n13 <<= (int)l) : (bl ? (n13 >>>= (int)(-l)) : (n13 >>= (int)(-l)))) : (bl ? 0 : (n13 >>= 31));
                } else {
                    n13 = 0;
                }
                return ValueInteger.get(n13);
            }
            case 12: {
                long l2;
                if (l < 64L) {
                    l2 = value.getLong();
                    l2 = l > -64L ? (l > 0L ? (l2 <<= (int)l) : (bl ? (l2 >>>= (int)(-l)) : (l2 >>= (int)(-l)))) : (bl ? 0L : (l2 >>= 63));
                } else {
                    l2 = 0L;
                }
                return ValueBigint.get(l2);
            }
        }
        throw DbException.getInvalidValueException("bit function parameter", value.getTraceSQL());
    }

    private static Value rotate(Value value, long l, boolean bl) {
        int n = value.getValueType();
        switch (n) {
            case 5: 
            case 6: {
                byte[] byArray = value.getBytesNoCopy();
                int n2 = byArray.length;
                if (n2 == 0) {
                    return value;
                }
                long l2 = n2 << 3;
                l %= l2;
                if (bl) {
                    l = -l;
                }
                if (l == 0L) {
                    return value;
                }
                if (l < 0L) {
                    l += l2;
                }
                byte[] byArray2 = new byte[n2];
                int n3 = (int)(l >> 3);
                int n4 = (int)l & 7;
                if (n4 == 0) {
                    System.arraycopy(byArray, n3, byArray2, 0, n2 - n3);
                    System.arraycopy(byArray, 0, byArray2, n2 - n3, n3);
                } else {
                    int n5 = 8 - n4;
                    int n6 = 0;
                    int n7 = n3;
                    while (n6 < n2) {
                        int n8 = n6++;
                        int n9 = byArray[n7] << n4;
                        n7 = (n7 + 1) % n2;
                        byArray2[n8] = (byte)(n9 | (byArray[n7] & 0xFF) >>> n5);
                    }
                }
                return n == 5 ? ValueBinary.getNoCopy(byArray2) : ValueVarbinary.getNoCopy(byArray2);
            }
            case 9: {
                int n10 = (int)l;
                if (bl) {
                    n10 = -n10;
                }
                if ((n10 &= 7) == 0) {
                    return value;
                }
                int n11 = value.getByte() & 0xFF;
                return ValueTinyint.get((byte)(n11 << n10 | n11 >>> 8 - n10));
            }
            case 10: {
                int n12 = (int)l;
                if (bl) {
                    n12 = -n12;
                }
                if ((n12 &= 0xF) == 0) {
                    return value;
                }
                int n13 = value.getShort() & 0xFFFF;
                return ValueSmallint.get((short)(n13 << n12 | n13 >>> 16 - n12));
            }
            case 11: {
                int n14 = (int)l;
                if (bl) {
                    n14 = -n14;
                }
                if ((n14 &= 0x1F) == 0) {
                    return value;
                }
                return ValueInteger.get(Integer.rotateLeft(value.getInt(), n14));
            }
            case 12: {
                int n15 = (int)l;
                if (bl) {
                    n15 = -n15;
                }
                if ((n15 &= 0x3F) == 0) {
                    return value;
                }
                return ValueBigint.get(Long.rotateLeft(value.getLong(), n15));
            }
        }
        throw DbException.getInvalidValueException("bit function parameter", value.getTraceSQL());
    }

    public static Value getBitwise(int n, TypeInfo typeInfo, Value value, Value value2) {
        return typeInfo.getValueType() < 9 ? BitFunction.getBinaryString(n, typeInfo, value, value2) : BitFunction.getNumeric(n, typeInfo, value, value2);
    }

    private static Value getBinaryString(int n, TypeInfo typeInfo, Value value, Value value2) {
        byte[] byArray;
        if (n == 3) {
            byArray = value.getBytes();
            int n2 = byArray.length;
            for (int i = 0; i < n2; ++i) {
                byArray[i] = ~byArray[i];
            }
        } else {
            int n3;
            int n4;
            byte[] byArray2;
            int n5;
            byte[] byArray3 = value.getBytesNoCopy();
            int n6 = byArray3.length;
            if (n6 <= (n5 = (byArray2 = value2.getBytesNoCopy()).length)) {
                n4 = n6;
                n3 = n5;
            } else {
                n4 = n5;
                n3 = n6;
                byte[] byArray4 = byArray3;
                byArray3 = byArray2;
                byArray2 = byArray4;
            }
            int n7 = (int)typeInfo.getPrecision();
            if (n4 > n7) {
                n3 = n4 = n7;
            } else if (n3 > n7) {
                n3 = n7;
            }
            byArray = new byte[n3];
            switch (n) {
                case 0: {
                    int n8;
                    for (n8 = 0; n8 < n4; ++n8) {
                        byArray[n8] = (byte)(byArray3[n8] & byArray2[n8]);
                    }
                    break;
                }
                case 1: {
                    int n8;
                    while (n8 < n4) {
                        byArray[n8] = (byte)(byArray3[n8] | byArray2[n8]);
                        ++n8;
                    }
                    System.arraycopy(byArray2, n8, byArray, n8, n3 - n8);
                    break;
                }
                case 2: {
                    int n8;
                    while (n8 < n4) {
                        byArray[n8] = (byte)(byArray3[n8] ^ byArray2[n8]);
                        ++n8;
                    }
                    System.arraycopy(byArray2, n8, byArray, n8, n3 - n8);
                    break;
                }
                case 4: {
                    int n8;
                    while (n8 < n4) {
                        byArray[n8] = (byte)(~(byArray3[n8] & byArray2[n8]));
                        ++n8;
                    }
                    Arrays.fill(byArray, n8, n3, (byte)-1);
                    break;
                }
                case 5: {
                    int n8;
                    while (n8 < n4) {
                        byArray[n8] = (byte)(~(byArray3[n8] | byArray2[n8]));
                        ++n8;
                    }
                    while (n8 < n3) {
                        byArray[n8] = ~byArray2[n8];
                        ++n8;
                    }
                    break;
                }
                case 6: {
                    int n8;
                    while (n8 < n4) {
                        byArray[n8] = (byte)(~(byArray3[n8] ^ byArray2[n8]));
                        ++n8;
                    }
                    while (n8 < n3) {
                        byArray[n8] = ~byArray2[n8];
                        ++n8;
                    }
                    break;
                }
                default: {
                    throw DbException.getInternalError("function=" + n);
                }
            }
        }
        return typeInfo.getValueType() == 5 ? ValueBinary.getNoCopy(byArray) : ValueVarbinary.getNoCopy(byArray);
    }

    private static Value getNumeric(int n, TypeInfo typeInfo, Value value, Value value2) {
        long l = value.getLong();
        switch (n) {
            case 0: {
                l &= value2.getLong();
                break;
            }
            case 1: {
                l |= value2.getLong();
                break;
            }
            case 2: {
                l ^= value2.getLong();
                break;
            }
            case 3: {
                l ^= 0xFFFFFFFFFFFFFFFFL;
                break;
            }
            case 4: {
                l = l & value2.getLong() ^ 0xFFFFFFFFFFFFFFFFL;
                break;
            }
            case 5: {
                l = (l | value2.getLong()) ^ 0xFFFFFFFFFFFFFFFFL;
                break;
            }
            case 6: {
                l = l ^ value2.getLong() ^ 0xFFFFFFFFFFFFFFFFL;
                break;
            }
            default: {
                throw DbException.getInternalError("function=" + n);
            }
        }
        switch (typeInfo.getValueType()) {
            case 9: {
                return ValueTinyint.get((byte)l);
            }
            case 10: {
                return ValueSmallint.get((short)l);
            }
            case 11: {
                return ValueInteger.get((int)l);
            }
            case 12: {
                return ValueBigint.get(l);
            }
        }
        throw DbException.getInternalError();
    }

    @Override
    public Expression optimize(SessionLocal sessionLocal) {
        this.left = this.left.optimize(sessionLocal);
        if (this.right != null) {
            this.right = this.right.optimize(sessionLocal);
        }
        switch (this.function) {
            case 3: {
                return this.optimizeNot(sessionLocal);
            }
            case 7: {
                this.type = TypeInfo.TYPE_BOOLEAN;
                break;
            }
            case 8: {
                this.type = TypeInfo.TYPE_BIGINT;
                break;
            }
            case 9: 
            case 10: 
            case 11: 
            case 12: 
            case 13: 
            case 14: {
                this.type = BitFunction.checkArgType(this.left);
                break;
            }
            default: {
                this.type = BitFunction.getCommonType(this.left, this.right);
            }
        }
        if (this.left.isConstant() && (this.right == null || this.right.isConstant())) {
            return TypedValueExpression.getTypedIfNull(this.getValue(sessionLocal), this.type);
        }
        return this;
    }

    private Expression optimizeNot(SessionLocal sessionLocal) {
        this.type = BitFunction.checkArgType(this.left);
        if (this.left.isConstant()) {
            return TypedValueExpression.getTypedIfNull(this.getValue(sessionLocal), this.type);
        }
        if (this.left instanceof BitFunction) {
            BitFunction bitFunction = (BitFunction)this.left;
            int n = bitFunction.function;
            switch (n) {
                case 0: 
                case 1: 
                case 2: {
                    n += 4;
                    break;
                }
                case 3: {
                    return bitFunction.left;
                }
                case 4: 
                case 5: 
                case 6: {
                    n -= 4;
                    break;
                }
                default: {
                    return this;
                }
            }
            return new BitFunction(bitFunction.left, bitFunction.right, n).optimize(sessionLocal);
        }
        if (this.left instanceof Aggregate) {
            AggregateType aggregateType;
            Aggregate aggregate = (Aggregate)this.left;
            switch (aggregate.getAggregateType()) {
                case BIT_AND_AGG: {
                    aggregateType = AggregateType.BIT_NAND_AGG;
                    break;
                }
                case BIT_OR_AGG: {
                    aggregateType = AggregateType.BIT_NOR_AGG;
                    break;
                }
                case BIT_XOR_AGG: {
                    aggregateType = AggregateType.BIT_XNOR_AGG;
                    break;
                }
                case BIT_NAND_AGG: {
                    aggregateType = AggregateType.BIT_AND_AGG;
                    break;
                }
                case BIT_NOR_AGG: {
                    aggregateType = AggregateType.BIT_OR_AGG;
                    break;
                }
                case BIT_XNOR_AGG: {
                    aggregateType = AggregateType.BIT_XOR_AGG;
                    break;
                }
                default: {
                    return this;
                }
            }
            Aggregate aggregate2 = new Aggregate(aggregateType, new Expression[]{aggregate.getSubexpression(0)}, aggregate.getSelect(), aggregate.isDistinct());
            aggregate2.setFilterCondition(aggregate.getFilterCondition());
            aggregate2.setOverCondition(aggregate.getOverCondition());
            return aggregate2.optimize(sessionLocal);
        }
        return this;
    }

    private static TypeInfo getCommonType(Expression expression, Expression expression2) {
        TypeInfo typeInfo = BitFunction.checkArgType(expression);
        TypeInfo typeInfo2 = BitFunction.checkArgType(expression2);
        int n = typeInfo.getValueType();
        int n2 = typeInfo2.getValueType();
        boolean bl = DataType.isBinaryStringType(n);
        if (bl != DataType.isBinaryStringType(n2)) {
            throw DbException.getInvalidValueException("bit function parameters", typeInfo2.getSQL(typeInfo.getSQL(new StringBuilder(), 3).append(" vs "), 3).toString());
        }
        if (bl) {
            long l;
            if (n == 5) {
                l = typeInfo.getDeclaredPrecision();
                if (n2 == 5) {
                    l = Math.max(l, typeInfo2.getDeclaredPrecision());
                }
            } else if (n2 == 5) {
                n = 5;
                l = typeInfo2.getDeclaredPrecision();
            } else {
                long l2 = typeInfo.getDeclaredPrecision();
                long l3 = typeInfo2.getDeclaredPrecision();
                l = l2 <= 0L || l3 <= 0L ? -1L : Math.max(l2, l3);
            }
            return TypeInfo.getTypeInfo(n, l, 0, null);
        }
        return TypeInfo.getTypeInfo(Math.max(n, n2));
    }

    public static TypeInfo checkArgType(Expression expression) {
        TypeInfo typeInfo = expression.getType();
        switch (typeInfo.getValueType()) {
            case 0: 
            case 5: 
            case 6: 
            case 9: 
            case 10: 
            case 11: 
            case 12: {
                return typeInfo;
            }
        }
        throw Store.getInvalidExpressionTypeException("bit function argument", expression);
    }

    @Override
    public String getName() {
        return NAMES[this.function];
    }
}

