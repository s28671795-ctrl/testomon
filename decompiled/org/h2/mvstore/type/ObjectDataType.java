/*
 * Decompiled with CFR 0.152.
 */
package org.h2.mvstore.type;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;
import org.h2.mvstore.DataUtils;
import org.h2.mvstore.WriteBuffer;
import org.h2.mvstore.type.BasicDataType;
import org.h2.mvstore.type.DataType;
import org.h2.mvstore.type.StringDataType;
import org.h2.util.Utils;

public class ObjectDataType
extends BasicDataType<Object> {
    static final int TYPE_NULL = 0;
    static final int TYPE_BOOLEAN = 1;
    static final int TYPE_BYTE = 2;
    static final int TYPE_SHORT = 3;
    static final int TYPE_INT = 4;
    static final int TYPE_LONG = 5;
    static final int TYPE_BIG_INTEGER = 6;
    static final int TYPE_FLOAT = 7;
    static final int TYPE_DOUBLE = 8;
    static final int TYPE_BIG_DECIMAL = 9;
    static final int TYPE_CHAR = 10;
    static final int TYPE_STRING = 11;
    static final int TYPE_UUID = 12;
    static final int TYPE_DATE = 13;
    static final int TYPE_ARRAY = 14;
    static final int TYPE_SERIALIZED_OBJECT = 19;
    static final int TAG_BOOLEAN_TRUE = 32;
    static final int TAG_INTEGER_NEGATIVE = 33;
    static final int TAG_INTEGER_FIXED = 34;
    static final int TAG_LONG_NEGATIVE = 35;
    static final int TAG_LONG_FIXED = 36;
    static final int TAG_BIG_INTEGER_0 = 37;
    static final int TAG_BIG_INTEGER_1 = 38;
    static final int TAG_BIG_INTEGER_SMALL = 39;
    static final int TAG_FLOAT_0 = 40;
    static final int TAG_FLOAT_1 = 41;
    static final int TAG_FLOAT_FIXED = 42;
    static final int TAG_DOUBLE_0 = 43;
    static final int TAG_DOUBLE_1 = 44;
    static final int TAG_DOUBLE_FIXED = 45;
    static final int TAG_BIG_DECIMAL_0 = 46;
    static final int TAG_BIG_DECIMAL_1 = 47;
    static final int TAG_BIG_DECIMAL_SMALL = 48;
    static final int TAG_BIG_DECIMAL_SMALL_SCALED = 49;
    static final int TAG_INTEGER_0_15 = 64;
    static final int TAG_LONG_0_7 = 80;
    static final int TAG_STRING_0_15 = 88;
    static final int TAG_BYTE_ARRAY_0_15 = 104;
    static final int FLOAT_ZERO_BITS = Float.floatToIntBits(0.0f);
    static final int FLOAT_ONE_BITS = Float.floatToIntBits(1.0f);
    static final long DOUBLE_ZERO_BITS = Double.doubleToLongBits(0.0);
    static final long DOUBLE_ONE_BITS = Double.doubleToLongBits(1.0);
    static final Class<?>[] COMMON_CLASSES = new Class[]{Boolean.TYPE, Byte.TYPE, Short.TYPE, Character.TYPE, Integer.TYPE, Long.TYPE, Float.TYPE, Double.TYPE, Object.class, Boolean.class, Byte.class, Short.class, Character.class, Integer.class, Long.class, BigInteger.class, Float.class, Double.class, BigDecimal.class, String.class, UUID.class, Date.class};
    private AutoDetectDataType<Object> last = this.selectDataType(0);

    @Override
    public Object[] createStorage(int n) {
        return new Object[n];
    }

    @Override
    public int compare(Object object, Object object2) {
        int n = ObjectDataType.getTypeId(object);
        int n2 = n - ObjectDataType.getTypeId(object2);
        if (n2 == 0) {
            return this.newType(n).compare(object, object2);
        }
        return Integer.signum(n2);
    }

    @Override
    public int getMemory(Object object) {
        return this.switchType(object).getMemory(object);
    }

    @Override
    public void write(WriteBuffer writeBuffer, Object object) {
        this.switchType(object).write(writeBuffer, object);
    }

    private AutoDetectDataType<Object> newType(int n) {
        if (n == this.last.typeId) {
            return this.last;
        }
        return this.selectDataType(n);
    }

    private AutoDetectDataType selectDataType(int n) {
        switch (n) {
            case 0: {
                return NullType.INSTANCE;
            }
            case 1: {
                return BooleanType.INSTANCE;
            }
            case 2: {
                return ByteType.INSTANCE;
            }
            case 3: {
                return ShortType.INSTANCE;
            }
            case 10: {
                return CharacterType.INSTANCE;
            }
            case 4: {
                return IntegerType.INSTANCE;
            }
            case 5: {
                return LongType.INSTANCE;
            }
            case 7: {
                return FloatType.INSTANCE;
            }
            case 8: {
                return DoubleType.INSTANCE;
            }
            case 6: {
                return BigIntegerType.INSTANCE;
            }
            case 9: {
                return BigDecimalType.INSTANCE;
            }
            case 11: {
                return StringType.INSTANCE;
            }
            case 12: {
                return UUIDType.INSTANCE;
            }
            case 13: {
                return DateType.INSTANCE;
            }
            case 14: {
                return new ObjectArrayType();
            }
            case 19: {
                return new SerializedObjectType(this);
            }
        }
        throw DataUtils.newMVStoreException(3, "Unsupported type {0}", n);
    }

    @Override
    public Object read(ByteBuffer byteBuffer) {
        int n;
        int n2 = byteBuffer.get();
        if (n2 <= 19) {
            n = n2;
        } else {
            switch (n2) {
                case 32: {
                    n = 1;
                    break;
                }
                case 33: 
                case 34: {
                    n = 4;
                    break;
                }
                case 35: 
                case 36: {
                    n = 5;
                    break;
                }
                case 37: 
                case 38: 
                case 39: {
                    n = 6;
                    break;
                }
                case 40: 
                case 41: 
                case 42: {
                    n = 7;
                    break;
                }
                case 43: 
                case 44: 
                case 45: {
                    n = 8;
                    break;
                }
                case 46: 
                case 47: 
                case 48: 
                case 49: {
                    n = 9;
                    break;
                }
                default: {
                    if (n2 >= 64 && n2 <= 79) {
                        n = 4;
                        break;
                    }
                    if (n2 >= 88 && n2 <= 103) {
                        n = 11;
                        break;
                    }
                    if (n2 >= 80 && n2 <= 87) {
                        n = 5;
                        break;
                    }
                    if (n2 >= 104 && n2 <= 119) {
                        n = 14;
                        break;
                    }
                    throw DataUtils.newMVStoreException(6, "Unknown tag {0}", n2);
                }
            }
        }
        AutoDetectDataType<Object> autoDetectDataType = this.last;
        if (n != autoDetectDataType.typeId) {
            this.last = autoDetectDataType = this.newType(n);
        }
        return autoDetectDataType.read(byteBuffer, n2);
    }

    private static int getTypeId(Object object) {
        if (object instanceof Integer) {
            return 4;
        }
        if (object instanceof String) {
            return 11;
        }
        if (object instanceof Long) {
            return 5;
        }
        if (object instanceof Double) {
            return 8;
        }
        if (object instanceof Float) {
            return 7;
        }
        if (object instanceof Boolean) {
            return 1;
        }
        if (object instanceof UUID) {
            return 12;
        }
        if (object instanceof Byte) {
            return 2;
        }
        if (object instanceof Short) {
            return 3;
        }
        if (object instanceof Character) {
            return 10;
        }
        if (object == null) {
            return 0;
        }
        if (ObjectDataType.isDate(object)) {
            return 13;
        }
        if (ObjectDataType.isBigInteger(object)) {
            return 6;
        }
        if (ObjectDataType.isBigDecimal(object)) {
            return 9;
        }
        if (object.getClass().isArray()) {
            return 14;
        }
        return 19;
    }

    AutoDetectDataType<Object> switchType(Object object) {
        int n = ObjectDataType.getTypeId(object);
        AutoDetectDataType<Object> autoDetectDataType = this.last;
        if (n != autoDetectDataType.typeId) {
            this.last = autoDetectDataType = this.newType(n);
        }
        return autoDetectDataType;
    }

    static boolean isBigInteger(Object object) {
        return object != null && object.getClass() == BigInteger.class;
    }

    static boolean isBigDecimal(Object object) {
        return object != null && object.getClass() == BigDecimal.class;
    }

    static boolean isDate(Object object) {
        return object != null && object.getClass() == Date.class;
    }

    static boolean isArray(Object object) {
        return object != null && object.getClass().isArray();
    }

    public static byte[] serialize(Object object) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(object);
            return byteArrayOutputStream.toByteArray();
        }
        catch (Throwable throwable) {
            throw DataUtils.newIllegalArgumentException("Could not serialize {0}", object, throwable);
        }
    }

    public static Object deserialize(byte[] byArray) {
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byArray);
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            return objectInputStream.readObject();
        }
        catch (Throwable throwable) {
            throw DataUtils.newIllegalArgumentException("Could not deserialize {0}", Arrays.toString(byArray), throwable);
        }
    }

    public static int compareNotNull(byte[] byArray, byte[] byArray2) {
        if (byArray == byArray2) {
            return 0;
        }
        int n = Math.min(byArray.length, byArray2.length);
        for (int i = 0; i < n; ++i) {
            int n2 = byArray[i] & 0xFF;
            int n3 = byArray2[i] & 0xFF;
            if (n2 == n3) continue;
            return n2 > n3 ? 1 : -1;
        }
        return Integer.signum(byArray.length - byArray2.length);
    }

    static class SerializedObjectType
    extends AutoDetectDataType<Object> {
        private int averageSize = 10000;

        SerializedObjectType(ObjectDataType objectDataType) {
            super(objectDataType, 19);
        }

        @Override
        public Object[] createStorage(int n) {
            return new Object[n];
        }

        @Override
        public int compare(Object object, Object object2) {
            if (object == object2) {
                return 0;
            }
            DataType<Object> dataType = this.getType(object);
            DataType<Object> dataType2 = this.getType(object2);
            if (dataType != this || dataType2 != this) {
                if (dataType == dataType2) {
                    return dataType.compare(object, object2);
                }
                return super.compare(object, object2);
            }
            if (object instanceof Comparable && object.getClass().isAssignableFrom(object2.getClass())) {
                return ((Comparable)object).compareTo(object2);
            }
            if (object2 instanceof Comparable && object2.getClass().isAssignableFrom(object.getClass())) {
                return -((Comparable)object2).compareTo(object);
            }
            byte[] byArray = ObjectDataType.serialize(object);
            byte[] byArray2 = ObjectDataType.serialize(object2);
            return ObjectDataType.compareNotNull(byArray, byArray2);
        }

        @Override
        public int getMemory(Object object) {
            DataType<Object> dataType = this.getType(object);
            if (dataType == this) {
                return this.averageSize;
            }
            return dataType.getMemory(object);
        }

        @Override
        public void write(WriteBuffer writeBuffer, Object object) {
            DataType<Object> dataType = this.getType(object);
            if (dataType != this) {
                dataType.write(writeBuffer, object);
                return;
            }
            byte[] byArray = ObjectDataType.serialize(object);
            int n = byArray.length * 2;
            this.averageSize = (int)(((long)n + 15L * (long)this.averageSize) / 16L);
            writeBuffer.put((byte)19).putVarInt(byArray.length).put(byArray);
        }

        @Override
        public Object read(ByteBuffer byteBuffer) {
            return this.read(byteBuffer, byteBuffer.get());
        }

        @Override
        public Object read(ByteBuffer byteBuffer, int n) {
            int n2 = DataUtils.readVarInt(byteBuffer);
            byte[] byArray = Utils.newBytes(n2);
            int n3 = byArray.length * 2;
            this.averageSize = (int)(((long)n3 + 15L * (long)this.averageSize) / 16L);
            byteBuffer.get(byArray);
            return ObjectDataType.deserialize(byArray);
        }
    }

    static class ObjectArrayType
    extends AutoDetectDataType<Object> {
        private final ObjectDataType elementType = new ObjectDataType();

        ObjectArrayType() {
            super(14);
        }

        @Override
        public Object[] createStorage(int n) {
            return new Object[n];
        }

        @Override
        public int getMemory(Object object) {
            int n;
            block4: {
                block2: {
                    int n2;
                    Class<?> clazz;
                    block6: {
                        block5: {
                            block3: {
                                if (!ObjectDataType.isArray(object)) {
                                    return super.getMemory(object);
                                }
                                n = 64;
                                clazz = object.getClass().getComponentType();
                                if (!clazz.isPrimitive()) break block2;
                                n2 = Array.getLength(object);
                                if (clazz != Boolean.TYPE && clazz != Byte.TYPE) break block3;
                                n += n2;
                                break block4;
                            }
                            if (clazz != Character.TYPE && clazz != Short.TYPE) break block5;
                            n += n2 * 2;
                            break block4;
                        }
                        if (clazz != Integer.TYPE && clazz != Float.TYPE) break block6;
                        n += n2 * 4;
                        break block4;
                    }
                    if (clazz != Double.TYPE && clazz != Long.TYPE) break block4;
                    n += n2 * 8;
                    break block4;
                }
                for (Object object2 : (Object[])object) {
                    if (object2 == null) continue;
                    n += this.elementType.getMemory(object2);
                }
            }
            return n * 2;
        }

        @Override
        public int compare(Object object, Object object2) {
            Class<?> clazz;
            if (!ObjectDataType.isArray(object) || !ObjectDataType.isArray(object2)) {
                return super.compare(object, object2);
            }
            if (object == object2) {
                return 0;
            }
            Class<?> clazz2 = object.getClass().getComponentType();
            if (clazz2 != (clazz = object2.getClass().getComponentType())) {
                Integer n = Holder.getCommonClassId(clazz2);
                Integer n2 = Holder.getCommonClassId(clazz);
                if (n != null) {
                    if (n2 != null) {
                        return n.compareTo(n2);
                    }
                    return -1;
                }
                if (n2 != null) {
                    return 1;
                }
                return clazz2.getName().compareTo(clazz.getName());
            }
            int n = Array.getLength(object);
            int n3 = Array.getLength(object2);
            int n4 = Math.min(n, n3);
            if (clazz2.isPrimitive()) {
                if (clazz2 == Byte.TYPE) {
                    byte[] byArray = (byte[])object;
                    byte[] byArray2 = (byte[])object2;
                    return ObjectDataType.compareNotNull(byArray, byArray2);
                }
                for (int i = 0; i < n4; ++i) {
                    int n5;
                    if (clazz2 == Boolean.TYPE) {
                        n5 = Integer.signum((((boolean[])object)[i] ? 1 : 0) - (((boolean[])object2)[i] ? 1 : 0));
                    } else if (clazz2 == Character.TYPE) {
                        n5 = Integer.signum(((char[])object)[i] - ((char[])object2)[i]);
                    } else if (clazz2 == Short.TYPE) {
                        n5 = Integer.signum(((short[])object)[i] - ((short[])object2)[i]);
                    } else if (clazz2 == Integer.TYPE) {
                        int n6 = ((int[])object)[i];
                        int n7 = ((int[])object2)[i];
                        n5 = Integer.compare(n6, n7);
                    } else if (clazz2 == Float.TYPE) {
                        n5 = Float.compare(((float[])object)[i], ((float[])object2)[i]);
                    } else if (clazz2 == Double.TYPE) {
                        n5 = Double.compare(((double[])object)[i], ((double[])object2)[i]);
                    } else {
                        long l = ((long[])object)[i];
                        long l2 = ((long[])object2)[i];
                        n5 = Long.compare(l, l2);
                    }
                    if (n5 == 0) continue;
                    return n5;
                }
            } else {
                Object[] objectArray = (Object[])object;
                Object[] objectArray2 = (Object[])object2;
                for (int i = 0; i < n4; ++i) {
                    int n8 = this.elementType.compare(objectArray[i], objectArray2[i]);
                    if (n8 == 0) continue;
                    return n8;
                }
            }
            return Integer.compare(n, n3);
        }

        @Override
        public void write(WriteBuffer writeBuffer, Object object) {
            Object[] objectArray;
            if (!ObjectDataType.isArray(object)) {
                super.write(writeBuffer, object);
                return;
            }
            Class<?> clazz = object.getClass().getComponentType();
            Integer n = Holder.getCommonClassId(clazz);
            if (n != null) {
                if (clazz.isPrimitive()) {
                    if (clazz == Byte.TYPE) {
                        byte[] byArray = (byte[])object;
                        int n2 = byArray.length;
                        if (n2 <= 15) {
                            writeBuffer.put((byte)(104 + n2));
                        } else {
                            writeBuffer.put((byte)14).put((byte)n.intValue()).putVarInt(n2);
                        }
                        writeBuffer.put(byArray);
                        return;
                    }
                    int n3 = Array.getLength(object);
                    writeBuffer.put((byte)14).put((byte)n.intValue()).putVarInt(n3);
                    for (int i = 0; i < n3; ++i) {
                        if (clazz == Boolean.TYPE) {
                            writeBuffer.put((byte)(((boolean[])object)[i] ? 1 : 0));
                            continue;
                        }
                        if (clazz == Character.TYPE) {
                            writeBuffer.putChar(((char[])object)[i]);
                            continue;
                        }
                        if (clazz == Short.TYPE) {
                            writeBuffer.putShort(((short[])object)[i]);
                            continue;
                        }
                        if (clazz == Integer.TYPE) {
                            writeBuffer.putInt(((int[])object)[i]);
                            continue;
                        }
                        if (clazz == Float.TYPE) {
                            writeBuffer.putFloat(((float[])object)[i]);
                            continue;
                        }
                        if (clazz == Double.TYPE) {
                            writeBuffer.putDouble(((double[])object)[i]);
                            continue;
                        }
                        writeBuffer.putLong(((long[])object)[i]);
                    }
                    return;
                }
                writeBuffer.put((byte)14).put((byte)n.intValue());
            } else {
                writeBuffer.put((byte)14).put((byte)-1);
                objectArray = clazz.getName();
                StringDataType.INSTANCE.write(writeBuffer, (String)objectArray);
            }
            objectArray = (Object[])object;
            int n4 = objectArray.length;
            writeBuffer.putVarInt(n4);
            for (Object object2 : objectArray) {
                this.elementType.write(writeBuffer, object2);
            }
        }

        @Override
        public Object read(ByteBuffer byteBuffer) {
            return this.read(byteBuffer, byteBuffer.get());
        }

        @Override
        public Object read(ByteBuffer byteBuffer, int n) {
            Object object;
            Class<?> clazz;
            if (n != 14) {
                int n2 = n - 104;
                byte[] byArray = Utils.newBytes(n2);
                byteBuffer.get(byArray);
                return byArray;
            }
            byte by = byteBuffer.get();
            if (by == -1) {
                String string = StringDataType.INSTANCE.read(byteBuffer);
                try {
                    clazz = Class.forName(string);
                }
                catch (Exception exception) {
                    throw DataUtils.newMVStoreException(8, "Could not get class {0}", string, exception);
                }
            } else {
                clazz = COMMON_CLASSES[by];
            }
            int n3 = DataUtils.readVarInt(byteBuffer);
            try {
                object = Array.newInstance(clazz, n3);
            }
            catch (Exception exception) {
                throw DataUtils.newMVStoreException(8, "Could not create array of type {0} length {1}", clazz, n3, exception);
            }
            if (clazz.isPrimitive()) {
                for (int i = 0; i < n3; ++i) {
                    if (clazz == Boolean.TYPE) {
                        ((boolean[])object)[i] = byteBuffer.get() == 1;
                        continue;
                    }
                    if (clazz == Byte.TYPE) {
                        ((byte[])object)[i] = byteBuffer.get();
                        continue;
                    }
                    if (clazz == Character.TYPE) {
                        ((char[])object)[i] = byteBuffer.getChar();
                        continue;
                    }
                    if (clazz == Short.TYPE) {
                        ((short[])object)[i] = byteBuffer.getShort();
                        continue;
                    }
                    if (clazz == Integer.TYPE) {
                        ((int[])object)[i] = byteBuffer.getInt();
                        continue;
                    }
                    if (clazz == Float.TYPE) {
                        ((float[])object)[i] = byteBuffer.getFloat();
                        continue;
                    }
                    if (clazz == Double.TYPE) {
                        ((double[])object)[i] = byteBuffer.getDouble();
                        continue;
                    }
                    ((long[])object)[i] = byteBuffer.getLong();
                }
            } else {
                Object[] objectArray = (Object[])object;
                for (int i = 0; i < n3; ++i) {
                    objectArray[i] = this.elementType.read(byteBuffer);
                }
            }
            return object;
        }
    }

    static class DateType
    extends AutoDetectDataType<Date> {
        static final DateType INSTANCE = new DateType();

        private DateType() {
            super(13);
        }

        public Date[] createStorage(int n) {
            return new Date[n];
        }

        @Override
        public int getMemory(Date date) {
            return 40;
        }

        @Override
        public int compare(Date date, Date date2) {
            return date.compareTo(date2);
        }

        @Override
        public void write(WriteBuffer writeBuffer, Date date) {
            writeBuffer.put((byte)13);
            writeBuffer.putLong(date.getTime());
        }

        @Override
        public Date read(ByteBuffer byteBuffer) {
            return this.read(byteBuffer, byteBuffer.get());
        }

        @Override
        public Date read(ByteBuffer byteBuffer, int n) {
            long l = byteBuffer.getLong();
            return new Date(l);
        }
    }

    static class UUIDType
    extends AutoDetectDataType<UUID> {
        static final UUIDType INSTANCE = new UUIDType();

        private UUIDType() {
            super(12);
        }

        public UUID[] createStorage(int n) {
            return new UUID[n];
        }

        @Override
        public int getMemory(UUID uUID) {
            return 40;
        }

        @Override
        public int compare(UUID uUID, UUID uUID2) {
            return uUID.compareTo(uUID2);
        }

        @Override
        public void write(WriteBuffer writeBuffer, UUID uUID) {
            writeBuffer.put((byte)12);
            writeBuffer.putLong(uUID.getMostSignificantBits());
            writeBuffer.putLong(uUID.getLeastSignificantBits());
        }

        @Override
        public UUID read(ByteBuffer byteBuffer) {
            return this.read(byteBuffer, byteBuffer.get());
        }

        @Override
        public UUID read(ByteBuffer byteBuffer, int n) {
            long l = byteBuffer.getLong();
            long l2 = byteBuffer.getLong();
            return new UUID(l, l2);
        }
    }

    static class StringType
    extends AutoDetectDataType<String> {
        static final StringType INSTANCE = new StringType();

        private StringType() {
            super(11);
        }

        public String[] createStorage(int n) {
            return new String[n];
        }

        @Override
        public int getMemory(String string) {
            return 24 + 2 * string.length();
        }

        @Override
        public int compare(String string, String string2) {
            return string.compareTo(string2);
        }

        @Override
        public void write(WriteBuffer writeBuffer, String string) {
            int n = string.length();
            if (n <= 15) {
                writeBuffer.put((byte)(88 + n));
            } else {
                writeBuffer.put((byte)11).putVarInt(n);
            }
            writeBuffer.putStringData(string, n);
        }

        @Override
        public String read(ByteBuffer byteBuffer) {
            return this.read(byteBuffer, byteBuffer.get());
        }

        @Override
        public String read(ByteBuffer byteBuffer, int n) {
            int n2 = n == 11 ? DataUtils.readVarInt(byteBuffer) : n - 88;
            return DataUtils.readString(byteBuffer, n2);
        }
    }

    static class BigDecimalType
    extends AutoDetectDataType<BigDecimal> {
        static final BigDecimalType INSTANCE = new BigDecimalType();

        private BigDecimalType() {
            super(9);
        }

        public BigDecimal[] createStorage(int n) {
            return new BigDecimal[n];
        }

        @Override
        public int compare(BigDecimal bigDecimal, BigDecimal bigDecimal2) {
            return bigDecimal.compareTo(bigDecimal2);
        }

        @Override
        public int getMemory(BigDecimal bigDecimal) {
            return 150;
        }

        @Override
        public void write(WriteBuffer writeBuffer, BigDecimal bigDecimal) {
            if (BigDecimal.ZERO.equals(bigDecimal)) {
                writeBuffer.put((byte)46);
            } else if (BigDecimal.ONE.equals(bigDecimal)) {
                writeBuffer.put((byte)47);
            } else {
                int n = bigDecimal.scale();
                BigInteger bigInteger = bigDecimal.unscaledValue();
                int n2 = bigInteger.bitLength();
                if (n2 < 64) {
                    if (n == 0) {
                        writeBuffer.put((byte)48);
                    } else {
                        writeBuffer.put((byte)49).putVarInt(n);
                    }
                    writeBuffer.putVarLong(bigInteger.longValue());
                } else {
                    byte[] byArray = bigInteger.toByteArray();
                    writeBuffer.put((byte)9).putVarInt(n).putVarInt(byArray.length).put(byArray);
                }
            }
        }

        @Override
        public BigDecimal read(ByteBuffer byteBuffer) {
            return this.read(byteBuffer, byteBuffer.get());
        }

        @Override
        public BigDecimal read(ByteBuffer byteBuffer, int n) {
            switch (n) {
                case 46: {
                    return BigDecimal.ZERO;
                }
                case 47: {
                    return BigDecimal.ONE;
                }
                case 48: {
                    return BigDecimal.valueOf(DataUtils.readVarLong(byteBuffer));
                }
                case 49: {
                    int n2 = DataUtils.readVarInt(byteBuffer);
                    return BigDecimal.valueOf(DataUtils.readVarLong(byteBuffer), n2);
                }
            }
            int n3 = DataUtils.readVarInt(byteBuffer);
            int n4 = DataUtils.readVarInt(byteBuffer);
            byte[] byArray = Utils.newBytes(n4);
            byteBuffer.get(byArray);
            BigInteger bigInteger = new BigInteger(byArray);
            return new BigDecimal(bigInteger, n3);
        }
    }

    static class BigIntegerType
    extends AutoDetectDataType<BigInteger> {
        static final BigIntegerType INSTANCE = new BigIntegerType();

        private BigIntegerType() {
            super(6);
        }

        public BigInteger[] createStorage(int n) {
            return new BigInteger[n];
        }

        @Override
        public int compare(BigInteger bigInteger, BigInteger bigInteger2) {
            return bigInteger.compareTo(bigInteger2);
        }

        @Override
        public int getMemory(BigInteger bigInteger) {
            return 100;
        }

        @Override
        public void write(WriteBuffer writeBuffer, BigInteger bigInteger) {
            if (BigInteger.ZERO.equals(bigInteger)) {
                writeBuffer.put((byte)37);
            } else if (BigInteger.ONE.equals(bigInteger)) {
                writeBuffer.put((byte)38);
            } else {
                int n = bigInteger.bitLength();
                if (n <= 63) {
                    writeBuffer.put((byte)39).putVarLong(bigInteger.longValue());
                } else {
                    byte[] byArray = bigInteger.toByteArray();
                    writeBuffer.put((byte)6).putVarInt(byArray.length).put(byArray);
                }
            }
        }

        @Override
        public BigInteger read(ByteBuffer byteBuffer) {
            return this.read(byteBuffer, byteBuffer.get());
        }

        @Override
        public BigInteger read(ByteBuffer byteBuffer, int n) {
            switch (n) {
                case 37: {
                    return BigInteger.ZERO;
                }
                case 38: {
                    return BigInteger.ONE;
                }
                case 39: {
                    return BigInteger.valueOf(DataUtils.readVarLong(byteBuffer));
                }
            }
            int n2 = DataUtils.readVarInt(byteBuffer);
            byte[] byArray = Utils.newBytes(n2);
            byteBuffer.get(byArray);
            return new BigInteger(byArray);
        }
    }

    static class DoubleType
    extends AutoDetectDataType<Double> {
        static final DoubleType INSTANCE = new DoubleType();

        private DoubleType() {
            super(8);
        }

        public Double[] createStorage(int n) {
            return new Double[n];
        }

        @Override
        public int compare(Double d, Double d2) {
            return d.compareTo(d2);
        }

        @Override
        public int getMemory(Double d) {
            return 30;
        }

        @Override
        public void write(WriteBuffer writeBuffer, Double d) {
            double d2 = d;
            long l = Double.doubleToLongBits(d2);
            if (l == DOUBLE_ZERO_BITS) {
                writeBuffer.put((byte)43);
            } else if (l == DOUBLE_ONE_BITS) {
                writeBuffer.put((byte)44);
            } else {
                long l2 = Long.reverse(l);
                if (l2 >= 0L && l2 <= 0x1FFFFFFFFFFFFL) {
                    writeBuffer.put((byte)8);
                    writeBuffer.putVarLong(l2);
                } else {
                    writeBuffer.put((byte)45);
                    writeBuffer.putDouble(d2);
                }
            }
        }

        @Override
        public Double read(ByteBuffer byteBuffer) {
            return this.read(byteBuffer, byteBuffer.get());
        }

        @Override
        public Double read(ByteBuffer byteBuffer, int n) {
            switch (n) {
                case 43: {
                    return 0.0;
                }
                case 44: {
                    return 1.0;
                }
                case 45: {
                    return byteBuffer.getDouble();
                }
            }
            return Double.longBitsToDouble(Long.reverse(DataUtils.readVarLong(byteBuffer)));
        }
    }

    static class FloatType
    extends AutoDetectDataType<Float> {
        static final FloatType INSTANCE = new FloatType();

        private FloatType() {
            super(7);
        }

        public Float[] createStorage(int n) {
            return new Float[n];
        }

        @Override
        public int compare(Float f, Float f2) {
            return f.compareTo(f2);
        }

        @Override
        public int getMemory(Float f) {
            return 24;
        }

        @Override
        public void write(WriteBuffer writeBuffer, Float f) {
            float f2 = f.floatValue();
            int n = Float.floatToIntBits(f2);
            if (n == FLOAT_ZERO_BITS) {
                writeBuffer.put((byte)40);
            } else if (n == FLOAT_ONE_BITS) {
                writeBuffer.put((byte)41);
            } else {
                int n2 = Integer.reverse(n);
                if (n2 >= 0 && n2 <= 0x1FFFFF) {
                    writeBuffer.put((byte)7).putVarInt(n2);
                } else {
                    writeBuffer.put((byte)42).putFloat(f2);
                }
            }
        }

        @Override
        public Float read(ByteBuffer byteBuffer) {
            return this.read(byteBuffer, byteBuffer.get());
        }

        @Override
        public Float read(ByteBuffer byteBuffer, int n) {
            switch (n) {
                case 40: {
                    return Float.valueOf(0.0f);
                }
                case 41: {
                    return Float.valueOf(1.0f);
                }
                case 42: {
                    return Float.valueOf(byteBuffer.getFloat());
                }
            }
            return Float.valueOf(Float.intBitsToFloat(Integer.reverse(DataUtils.readVarInt(byteBuffer))));
        }
    }

    static class LongType
    extends AutoDetectDataType<Long> {
        static final LongType INSTANCE = new LongType();

        private LongType() {
            super(5);
        }

        public Long[] createStorage(int n) {
            return new Long[n];
        }

        @Override
        public int compare(Long l, Long l2) {
            return l.compareTo(l2);
        }

        @Override
        public int getMemory(Long l) {
            return 30;
        }

        @Override
        public void write(WriteBuffer writeBuffer, Long l) {
            long l2 = l;
            if (l2 < 0L) {
                if (-l2 < 0L || -l2 > 0x1FFFFFFFFFFFFL) {
                    writeBuffer.put((byte)36);
                    writeBuffer.putLong(l2);
                } else {
                    writeBuffer.put((byte)35);
                    writeBuffer.putVarLong(-l2);
                }
            } else if (l2 <= 7L) {
                writeBuffer.put((byte)(80L + l2));
            } else if (l2 <= 0x1FFFFFFFFFFFFL) {
                writeBuffer.put((byte)5);
                writeBuffer.putVarLong(l2);
            } else {
                writeBuffer.put((byte)36);
                writeBuffer.putLong(l2);
            }
        }

        @Override
        public Long read(ByteBuffer byteBuffer) {
            return this.read(byteBuffer, byteBuffer.get());
        }

        @Override
        public Long read(ByteBuffer byteBuffer, int n) {
            switch (n) {
                case 5: {
                    return DataUtils.readVarLong(byteBuffer);
                }
                case 35: {
                    return -DataUtils.readVarLong(byteBuffer);
                }
                case 36: {
                    return byteBuffer.getLong();
                }
            }
            return n - 80;
        }
    }

    static class IntegerType
    extends AutoDetectDataType<Integer> {
        static final IntegerType INSTANCE = new IntegerType();

        private IntegerType() {
            super(4);
        }

        public Integer[] createStorage(int n) {
            return new Integer[n];
        }

        @Override
        public int compare(Integer n, Integer n2) {
            return n.compareTo(n2);
        }

        @Override
        public int getMemory(Integer n) {
            return 24;
        }

        @Override
        public void write(WriteBuffer writeBuffer, Integer n) {
            int n2 = n;
            if (n2 < 0) {
                if (-n2 < 0 || -n2 > 0x1FFFFF) {
                    writeBuffer.put((byte)34).putInt(n2);
                } else {
                    writeBuffer.put((byte)33).putVarInt(-n2);
                }
            } else if (n2 <= 15) {
                writeBuffer.put((byte)(64 + n2));
            } else if (n2 <= 0x1FFFFF) {
                writeBuffer.put((byte)4).putVarInt(n2);
            } else {
                writeBuffer.put((byte)34).putInt(n2);
            }
        }

        @Override
        public Integer read(ByteBuffer byteBuffer) {
            return this.read(byteBuffer, byteBuffer.get());
        }

        @Override
        public Integer read(ByteBuffer byteBuffer, int n) {
            switch (n) {
                case 4: {
                    return DataUtils.readVarInt(byteBuffer);
                }
                case 33: {
                    return -DataUtils.readVarInt(byteBuffer);
                }
                case 34: {
                    return byteBuffer.getInt();
                }
            }
            return n - 64;
        }
    }

    static class ShortType
    extends AutoDetectDataType<Short> {
        static final ShortType INSTANCE = new ShortType();

        private ShortType() {
            super(3);
        }

        public Short[] createStorage(int n) {
            return new Short[n];
        }

        @Override
        public int compare(Short s2, Short s3) {
            return s2.compareTo(s3);
        }

        @Override
        public int getMemory(Short s2) {
            return 24;
        }

        @Override
        public void write(WriteBuffer writeBuffer, Short s2) {
            writeBuffer.put((byte)3);
            writeBuffer.putShort(s2);
        }

        @Override
        public Short read(ByteBuffer byteBuffer) {
            return this.read(byteBuffer, byteBuffer.get());
        }

        @Override
        public Short read(ByteBuffer byteBuffer, int n) {
            return byteBuffer.getShort();
        }
    }

    static class CharacterType
    extends AutoDetectDataType<Character> {
        static final CharacterType INSTANCE = new CharacterType();

        private CharacterType() {
            super(10);
        }

        public Character[] createStorage(int n) {
            return new Character[n];
        }

        @Override
        public int compare(Character c, Character c2) {
            return c.compareTo(c2);
        }

        @Override
        public int getMemory(Character c) {
            return 24;
        }

        @Override
        public void write(WriteBuffer writeBuffer, Character c) {
            writeBuffer.put((byte)10);
            writeBuffer.putChar(c.charValue());
        }

        @Override
        public Character read(ByteBuffer byteBuffer) {
            return Character.valueOf(byteBuffer.getChar());
        }

        @Override
        public Character read(ByteBuffer byteBuffer, int n) {
            return Character.valueOf(byteBuffer.getChar());
        }
    }

    static class ByteType
    extends AutoDetectDataType<Byte> {
        static final ByteType INSTANCE = new ByteType();

        private ByteType() {
            super(2);
        }

        public Byte[] createStorage(int n) {
            return new Byte[n];
        }

        @Override
        public int compare(Byte by, Byte by2) {
            return by.compareTo(by2);
        }

        @Override
        public int getMemory(Byte by) {
            return 1;
        }

        @Override
        public void write(WriteBuffer writeBuffer, Byte by) {
            writeBuffer.put((byte)2);
            writeBuffer.put(by);
        }

        @Override
        public Byte read(ByteBuffer byteBuffer) {
            return byteBuffer.get();
        }

        @Override
        public Object read(ByteBuffer byteBuffer, int n) {
            return byteBuffer.get();
        }
    }

    static class BooleanType
    extends AutoDetectDataType<Boolean> {
        static final BooleanType INSTANCE = new BooleanType();

        private BooleanType() {
            super(1);
        }

        public Boolean[] createStorage(int n) {
            return new Boolean[n];
        }

        @Override
        public int compare(Boolean bl, Boolean bl2) {
            return bl.compareTo(bl2);
        }

        @Override
        public int getMemory(Boolean bl) {
            return 0;
        }

        @Override
        public void write(WriteBuffer writeBuffer, Boolean bl) {
            int n = bl != false ? 32 : 1;
            writeBuffer.put((byte)n);
        }

        @Override
        public Boolean read(ByteBuffer byteBuffer) {
            return byteBuffer.get() == 32 ? Boolean.TRUE : Boolean.FALSE;
        }

        @Override
        public Boolean read(ByteBuffer byteBuffer, int n) {
            return n == 1 ? Boolean.FALSE : Boolean.TRUE;
        }
    }

    static class NullType
    extends AutoDetectDataType<Object> {
        static final NullType INSTANCE = new NullType();

        private NullType() {
            super(0);
        }

        @Override
        public Object[] createStorage(int n) {
            return null;
        }

        @Override
        public int compare(Object object, Object object2) {
            return 0;
        }

        @Override
        public int getMemory(Object object) {
            return 0;
        }

        @Override
        public void write(WriteBuffer writeBuffer, Object object) {
            writeBuffer.put((byte)0);
        }

        @Override
        public Object read(ByteBuffer byteBuffer) {
            return null;
        }

        @Override
        public Object read(ByteBuffer byteBuffer, int n) {
            return null;
        }
    }

    static abstract class AutoDetectDataType<T>
    extends BasicDataType<T> {
        private final ObjectDataType base;
        final int typeId;

        AutoDetectDataType(int n) {
            this.base = null;
            this.typeId = n;
        }

        AutoDetectDataType(ObjectDataType objectDataType, int n) {
            this.base = objectDataType;
            this.typeId = n;
        }

        @Override
        public int getMemory(T t2) {
            return this.getType(t2).getMemory(t2);
        }

        @Override
        public void write(WriteBuffer writeBuffer, T t2) {
            this.getType(t2).write(writeBuffer, t2);
        }

        DataType<Object> getType(Object object) {
            return this.base.switchType(object);
        }

        abstract Object read(ByteBuffer var1, int var2);
    }

    private static class Holder {
        private static final HashMap<Class<?>, Integer> COMMON_CLASSES_MAP = new HashMap(32);

        private Holder() {
        }

        static Integer getCommonClassId(Class<?> clazz) {
            return COMMON_CLASSES_MAP.get(clazz);
        }

        static {
            int n = COMMON_CLASSES.length;
            for (int i = 0; i < n; ++i) {
                COMMON_CLASSES_MAP.put(COMMON_CLASSES[i], i);
            }
        }
    }
}

