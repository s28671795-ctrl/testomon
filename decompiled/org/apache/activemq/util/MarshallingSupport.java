/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.util;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UTFDataFormatException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.apache.activemq.util.DataByteArrayInputStream;
import org.apache.activemq.util.DataByteArrayOutputStream;
import org.fusesource.hawtbuf.UTF8Buffer;

public final class MarshallingSupport {
    public static final byte NULL = 0;
    public static final byte BOOLEAN_TYPE = 1;
    public static final byte BYTE_TYPE = 2;
    public static final byte CHAR_TYPE = 3;
    public static final byte SHORT_TYPE = 4;
    public static final byte INTEGER_TYPE = 5;
    public static final byte LONG_TYPE = 6;
    public static final byte DOUBLE_TYPE = 7;
    public static final byte FLOAT_TYPE = 8;
    public static final byte STRING_TYPE = 9;
    public static final byte BYTE_ARRAY_TYPE = 10;
    public static final byte MAP_TYPE = 11;
    public static final byte LIST_TYPE = 12;
    public static final byte BIG_STRING_TYPE = 13;

    private MarshallingSupport() {
    }

    public static void marshalPrimitiveMap(Map<String, Object> map, DataOutputStream out) throws IOException {
        if (map == null) {
            out.writeInt(-1);
        } else {
            out.writeInt(map.size());
            for (String name : map.keySet()) {
                out.writeUTF(name);
                Object value = map.get(name);
                MarshallingSupport.marshalPrimitive(out, value);
            }
        }
    }

    public static Map<String, Object> unmarshalPrimitiveMap(DataInputStream in) throws IOException {
        return MarshallingSupport.unmarshalPrimitiveMap(in, Integer.MAX_VALUE);
    }

    public static Map<String, Object> unmarshalPrimitiveMap(DataInputStream in, boolean force) throws IOException {
        return MarshallingSupport.unmarshalPrimitiveMap(in, Integer.MAX_VALUE, force);
    }

    public static Map<String, Object> unmarshalPrimitiveMap(DataInputStream in, int maxPropertySize) throws IOException {
        return MarshallingSupport.unmarshalPrimitiveMap(in, maxPropertySize, false);
    }

    public static Map<String, Object> unmarshalPrimitiveMap(DataInputStream in, int maxPropertySize, boolean force) throws IOException {
        int size = in.readInt();
        if (size > maxPropertySize) {
            throw new IOException("Primitive map is larger than the allowed size: " + size);
        }
        if (size < 0) {
            return null;
        }
        HashMap<String, Object> rc = new HashMap<String, Object>(size);
        for (int i = 0; i < size; ++i) {
            String name = in.readUTF();
            rc.put(name, MarshallingSupport.unmarshalPrimitive(in, force));
        }
        return rc;
    }

    public static void marshalPrimitiveList(List<Object> list, DataOutputStream out) throws IOException {
        out.writeInt(list.size());
        for (Object element : list) {
            MarshallingSupport.marshalPrimitive(out, element);
        }
    }

    public static List<Object> unmarshalPrimitiveList(DataInputStream in) throws IOException {
        return MarshallingSupport.unmarshalPrimitiveList(in, false);
    }

    public static List<Object> unmarshalPrimitiveList(DataInputStream in, boolean force) throws IOException {
        int size = in.readInt();
        ArrayList<Object> answer = new ArrayList<Object>(size);
        while (size-- > 0) {
            answer.add(MarshallingSupport.unmarshalPrimitive(in, force));
        }
        return answer;
    }

    public static void marshalPrimitive(DataOutputStream out, Object value) throws IOException {
        if (value == null) {
            MarshallingSupport.marshalNull(out);
        } else if (value.getClass() == Boolean.class) {
            MarshallingSupport.marshalBoolean(out, (Boolean)value);
        } else if (value.getClass() == Byte.class) {
            MarshallingSupport.marshalByte(out, (Byte)value);
        } else if (value.getClass() == Character.class) {
            MarshallingSupport.marshalChar(out, ((Character)value).charValue());
        } else if (value.getClass() == Short.class) {
            MarshallingSupport.marshalShort(out, (Short)value);
        } else if (value.getClass() == Integer.class) {
            MarshallingSupport.marshalInt(out, (Integer)value);
        } else if (value.getClass() == Long.class) {
            MarshallingSupport.marshalLong(out, (Long)value);
        } else if (value.getClass() == Float.class) {
            MarshallingSupport.marshalFloat(out, ((Float)value).floatValue());
        } else if (value.getClass() == Double.class) {
            MarshallingSupport.marshalDouble(out, (Double)value);
        } else if (value.getClass() == byte[].class) {
            MarshallingSupport.marshalByteArray(out, (byte[])value);
        } else if (value.getClass() == String.class) {
            MarshallingSupport.marshalString(out, (String)value);
        } else if (value.getClass() == UTF8Buffer.class) {
            MarshallingSupport.marshalString(out, value.toString());
        } else if (value instanceof Map) {
            out.writeByte(11);
            MarshallingSupport.marshalPrimitiveMap((Map)value, out);
        } else if (value instanceof List) {
            out.writeByte(12);
            MarshallingSupport.marshalPrimitiveList((List)value, out);
        } else {
            throw new IOException("Object is not a primitive: " + value);
        }
    }

    public static Object unmarshalPrimitive(DataInputStream in) throws IOException {
        return MarshallingSupport.unmarshalPrimitive(in, false);
    }

    public static Object unmarshalPrimitive(DataInputStream in, boolean force) throws IOException {
        Object value = null;
        byte type = in.readByte();
        switch (type) {
            case 2: {
                value = in.readByte();
                break;
            }
            case 1: {
                value = in.readBoolean() ? Boolean.TRUE : Boolean.FALSE;
                break;
            }
            case 3: {
                value = Character.valueOf(in.readChar());
                break;
            }
            case 4: {
                value = in.readShort();
                break;
            }
            case 5: {
                value = in.readInt();
                break;
            }
            case 6: {
                value = in.readLong();
                break;
            }
            case 8: {
                value = new Float(in.readFloat());
                break;
            }
            case 7: {
                value = new Double(in.readDouble());
                break;
            }
            case 10: {
                value = new byte[in.readInt()];
                in.readFully((byte[])value);
                break;
            }
            case 9: {
                if (force) {
                    value = in.readUTF();
                    break;
                }
                value = MarshallingSupport.readUTF(in, in.readUnsignedShort());
                break;
            }
            case 13: {
                if (force) {
                    value = MarshallingSupport.readUTF8(in);
                    break;
                }
                value = MarshallingSupport.readUTF(in, in.readInt());
                break;
            }
            case 11: {
                value = MarshallingSupport.unmarshalPrimitiveMap(in, true);
                break;
            }
            case 12: {
                value = MarshallingSupport.unmarshalPrimitiveList(in, true);
                break;
            }
            case 0: {
                value = null;
                break;
            }
            default: {
                throw new IOException("Unknown primitive type: " + type);
            }
        }
        return value;
    }

    public static UTF8Buffer readUTF(DataInputStream in, int length) throws IOException {
        byte[] data = new byte[length];
        in.readFully(data);
        return new UTF8Buffer(data);
    }

    public static void marshalNull(DataOutputStream out) throws IOException {
        out.writeByte(0);
    }

    public static void marshalBoolean(DataOutputStream out, boolean value) throws IOException {
        out.writeByte(1);
        out.writeBoolean(value);
    }

    public static void marshalByte(DataOutputStream out, byte value) throws IOException {
        out.writeByte(2);
        out.writeByte(value);
    }

    public static void marshalChar(DataOutputStream out, char value) throws IOException {
        out.writeByte(3);
        out.writeChar(value);
    }

    public static void marshalShort(DataOutputStream out, short value) throws IOException {
        out.writeByte(4);
        out.writeShort(value);
    }

    public static void marshalInt(DataOutputStream out, int value) throws IOException {
        out.writeByte(5);
        out.writeInt(value);
    }

    public static void marshalLong(DataOutputStream out, long value) throws IOException {
        out.writeByte(6);
        out.writeLong(value);
    }

    public static void marshalFloat(DataOutputStream out, float value) throws IOException {
        out.writeByte(8);
        out.writeFloat(value);
    }

    public static void marshalDouble(DataOutputStream out, double value) throws IOException {
        out.writeByte(7);
        out.writeDouble(value);
    }

    public static void marshalByteArray(DataOutputStream out, byte[] value) throws IOException {
        MarshallingSupport.marshalByteArray(out, value, 0, value.length);
    }

    public static void marshalByteArray(DataOutputStream out, byte[] value, int offset, int length) throws IOException {
        out.writeByte(10);
        out.writeInt(length);
        out.write(value, offset, length);
    }

    public static void marshalString(DataOutputStream out, String s2) throws IOException {
        if (s2.length() < 8191) {
            out.writeByte(9);
            out.writeUTF(s2);
        } else {
            out.writeByte(13);
            MarshallingSupport.writeUTF8(out, s2);
        }
    }

    public static void writeUTF8(DataOutput dataOut, String text) throws IOException {
        if (text != null) {
            long utfCount = MarshallingSupport.countUTFBytes(text);
            dataOut.writeInt((int)utfCount);
            byte[] buffer = new byte[(int)utfCount];
            int len = MarshallingSupport.writeUTFBytesToBuffer(text, (int)utfCount, buffer, 0);
            dataOut.write(buffer, 0, len);
            assert (utfCount == (long)len);
        } else {
            dataOut.writeInt(-1);
        }
    }

    public static long countUTFBytes(String str) {
        int utfCount = 0;
        int length = str.length();
        for (int i = 0; i < length; ++i) {
            char charValue = str.charAt(i);
            if (charValue > '\u0000' && charValue <= '\u007f') {
                ++utfCount;
                continue;
            }
            if (charValue <= '\u07ff') {
                utfCount += 2;
                continue;
            }
            utfCount += 3;
        }
        return utfCount;
    }

    public static int writeUTFBytesToBuffer(String str, long count, byte[] buffer, int offset) throws IOException {
        int length = str.length();
        for (int i = 0; i < length; ++i) {
            char charValue = str.charAt(i);
            if (charValue > '\u0000' && charValue <= '\u007f') {
                buffer[offset++] = (byte)charValue;
                continue;
            }
            if (charValue <= '\u07ff') {
                buffer[offset++] = (byte)(0xC0 | 0x1F & charValue >> 6);
                buffer[offset++] = (byte)(0x80 | 0x3F & charValue);
                continue;
            }
            buffer[offset++] = (byte)(0xE0 | 0xF & charValue >> 12);
            buffer[offset++] = (byte)(0x80 | 0x3F & charValue >> 6);
            buffer[offset++] = (byte)(0x80 | 0x3F & charValue);
        }
        return offset;
    }

    public static String readUTF8(DataInput dataIn) throws IOException {
        int utflen = dataIn.readInt();
        if (utflen > -1) {
            byte[] bytearr = new byte[utflen];
            char[] chararr = new char[utflen];
            dataIn.readFully(bytearr, 0, utflen);
            return MarshallingSupport.convertUTF8WithBuf(bytearr, chararr, 0, utflen);
        }
        return null;
    }

    public static String convertUTF8WithBuf(byte[] buf, char[] out, int offset, int utfSize) throws UTFDataFormatException {
        int count = 0;
        int s2 = 0;
        while (count < utfSize) {
            byte b;
            if ((out[s2] = (char)buf[offset + count++]) < '\u0080') {
                ++s2;
                continue;
            }
            char a = out[s2];
            if ((a & 0xE0) == 192) {
                if (count >= utfSize) {
                    throw new UTFDataFormatException();
                }
                if (((b = buf[offset + count++]) & 0xC0) != 128) {
                    throw new UTFDataFormatException();
                }
                out[s2++] = (char)((a & 0x1F) << 6 | b & 0x3F);
                continue;
            }
            if ((a & 0xF0) == 224) {
                if (count + 1 >= utfSize) {
                    throw new UTFDataFormatException();
                }
                b = buf[offset + count++];
                byte c = buf[offset + count++];
                if ((b & 0xC0) != 128 || (c & 0xC0) != 128) {
                    throw new UTFDataFormatException();
                }
                out[s2++] = (char)((a & 0xF) << 12 | (b & 0x3F) << 6 | c & 0x3F);
                continue;
            }
            throw new UTFDataFormatException();
        }
        return new String(out, 0, s2);
    }

    public static String propertiesToString(Properties props) throws IOException {
        String result = "";
        if (props != null) {
            DataByteArrayOutputStream dataOut = new DataByteArrayOutputStream();
            props.store(dataOut, "");
            result = new String(dataOut.getData(), 0, dataOut.size());
            dataOut.close();
        }
        return result;
    }

    public static Properties stringToProperties(String str) throws IOException {
        Properties result = new Properties();
        if (str != null && str.length() > 0) {
            DataByteArrayInputStream dataIn = new DataByteArrayInputStream(str.getBytes());
            result.load(dataIn);
            dataIn.close();
        }
        return result;
    }

    public static String truncate64(String text) {
        if (text.length() > 63) {
            text = text.substring(0, 45) + "..." + text.substring(text.length() - 12);
        }
        return text;
    }
}

