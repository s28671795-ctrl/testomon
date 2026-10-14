/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.util.mysql;

import org.jetbrains.annotations.NotNull;
import org.jooq.Field;
import org.jooq.SQLDialect;
import org.jooq.Support;
import org.jooq.impl.DSL;

public class MySQLDSL
extends DSL {
    protected MySQLDSL() {
    }

    @Deprecated(forRemoval=true, since="3.15")
    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.MYSQL})
    @NotNull
    public static Field<String> decode(String cryptString, String keyString) {
        return MySQLDSL.decode(MySQLDSL.val(cryptString), MySQLDSL.val(keyString));
    }

    @Deprecated(forRemoval=true, since="3.15")
    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.MYSQL})
    @NotNull
    public static Field<byte[]> decode(byte[] cryptString, byte[] keyString) {
        return MySQLDSL.decode(MySQLDSL.val(cryptString), MySQLDSL.val(keyString));
    }

    @Deprecated(forRemoval=true, since="3.15")
    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.MYSQL})
    @NotNull
    public static <T> Field<T> decode(Field<T> cryptString, Field<T> keyString) {
        return MySQLDSL.function("decode", cryptString.getType(), cryptString, keyString);
    }

    @Deprecated(forRemoval=true, since="3.15")
    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.MYSQL})
    @NotNull
    public static Field<String> encode(String string, String keyString) {
        return MySQLDSL.encode(MySQLDSL.val(string), MySQLDSL.val(keyString));
    }

    @Deprecated(forRemoval=true, since="3.15")
    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.MYSQL})
    @NotNull
    public static Field<byte[]> encode(byte[] string, byte[] keyString) {
        return MySQLDSL.encode(MySQLDSL.val(string), MySQLDSL.val(keyString));
    }

    @Deprecated(forRemoval=true, since="3.15")
    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.MYSQL})
    @NotNull
    public static <T> Field<T> encode(Field<T> string, Field<T> keyString) {
        return MySQLDSL.function("encode", string.getType(), string, keyString);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.MYSQL})
    @NotNull
    public static Field<String> aesDecrypt(String cryptString, String keyString) {
        return MySQLDSL.aesDecrypt(MySQLDSL.val(cryptString), MySQLDSL.val(keyString));
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.MYSQL})
    @NotNull
    public static Field<byte[]> aesDecrypt(byte[] cryptString, byte[] keyString) {
        return MySQLDSL.aesDecrypt(MySQLDSL.val(cryptString), MySQLDSL.val(keyString));
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.MYSQL})
    @NotNull
    public static <T> Field<T> aesDecrypt(Field<T> cryptString, Field<T> keyString) {
        return MySQLDSL.function("aes_decrypt", cryptString.getType(), cryptString, keyString);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.MYSQL})
    @NotNull
    public static Field<String> aesEncrypt(String string, String keyString) {
        return MySQLDSL.aesEncrypt(MySQLDSL.val(string), MySQLDSL.val(keyString));
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.MYSQL})
    @NotNull
    public static Field<byte[]> aesEncrypt(byte[] string, byte[] keyString) {
        return MySQLDSL.aesEncrypt(MySQLDSL.val(string), MySQLDSL.val(keyString));
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.MYSQL})
    @NotNull
    public static <T> Field<T> aesEncrypt(Field<T> string, Field<T> keyString) {
        return MySQLDSL.function("aes_encrypt", string.getType(), string, keyString);
    }

    @Deprecated(forRemoval=true, since="3.15")
    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.MYSQL})
    @NotNull
    public static Field<String> desDecrypt(String cryptString) {
        return MySQLDSL.desDecrypt(MySQLDSL.val(cryptString));
    }

    @Deprecated(forRemoval=true, since="3.15")
    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.MYSQL})
    @NotNull
    public static Field<byte[]> desDecrypt(byte[] cryptString) {
        return MySQLDSL.desDecrypt(MySQLDSL.val(cryptString));
    }

    @Deprecated(forRemoval=true, since="3.15")
    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.MYSQL})
    @NotNull
    public static <T> Field<T> desDecrypt(Field<T> cryptString) {
        return MySQLDSL.function("des_decrypt", cryptString.getType(), cryptString);
    }

    @Deprecated(forRemoval=true, since="3.15")
    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.MYSQL})
    @NotNull
    public static Field<String> desDecrypt(String cryptString, String keyString) {
        return MySQLDSL.desDecrypt(MySQLDSL.val(cryptString), MySQLDSL.val(keyString));
    }

    @Deprecated(forRemoval=true, since="3.15")
    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.MYSQL})
    @NotNull
    public static Field<byte[]> desDecrypt(byte[] cryptString, byte[] keyString) {
        return MySQLDSL.desDecrypt(MySQLDSL.val(cryptString), MySQLDSL.val(keyString));
    }

    @Deprecated(forRemoval=true, since="3.15")
    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.MYSQL})
    @NotNull
    public static <T> Field<T> desDecrypt(Field<T> cryptString, Field<T> keyString) {
        return MySQLDSL.function("des_decrypt", cryptString.getType(), cryptString, keyString);
    }

    @Deprecated(forRemoval=true, since="3.15")
    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.MYSQL})
    @NotNull
    public static Field<String> desEncrypt(String string) {
        return MySQLDSL.desEncrypt(MySQLDSL.val(string));
    }

    @Deprecated(forRemoval=true, since="3.15")
    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.MYSQL})
    @NotNull
    public static Field<byte[]> desEncrypt(byte[] string) {
        return MySQLDSL.desEncrypt(MySQLDSL.val(string));
    }

    @Deprecated(forRemoval=true, since="3.15")
    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.MYSQL})
    @NotNull
    public static <T> Field<T> desEncrypt(Field<T> string) {
        return MySQLDSL.function("des_encrypt", string.getType(), string);
    }

    @Deprecated(forRemoval=true, since="3.15")
    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.MYSQL})
    @NotNull
    public static Field<String> desEncrypt(String string, String keyString) {
        return MySQLDSL.desEncrypt(MySQLDSL.val(string), MySQLDSL.val(keyString));
    }

    @Deprecated(forRemoval=true, since="3.15")
    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.MYSQL})
    @NotNull
    public static Field<byte[]> desEncrypt(byte[] string, byte[] keyString) {
        return MySQLDSL.desEncrypt(MySQLDSL.val(string), MySQLDSL.val(keyString));
    }

    @Deprecated(forRemoval=true, since="3.15")
    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.MYSQL})
    @NotNull
    public static <T> Field<T> desEncrypt(Field<T> string, Field<T> keyString) {
        return MySQLDSL.function("des_encrypt", string.getType(), string, keyString);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.MYSQL})
    @NotNull
    public static Field<String> compress(String string) {
        return MySQLDSL.compress(MySQLDSL.val(string));
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.MYSQL})
    @NotNull
    public static Field<byte[]> compress(byte[] string) {
        return MySQLDSL.compress(MySQLDSL.val(string));
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.MYSQL})
    @NotNull
    public static <T> Field<T> compress(Field<T> string) {
        return MySQLDSL.function("compress", string.getType(), string);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.MYSQL})
    @NotNull
    public static Field<String> uncompress(String string) {
        return MySQLDSL.uncompress(MySQLDSL.val(string));
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.MYSQL})
    @NotNull
    public static Field<byte[]> uncompress(byte[] string) {
        return MySQLDSL.uncompress(MySQLDSL.val(string));
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.MYSQL})
    @NotNull
    public static <T> Field<T> uncompress(Field<T> string) {
        return MySQLDSL.function("uncompress", string.getType(), string);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.MYSQL})
    @NotNull
    public static Field<Integer> uncompressedLength(String string) {
        return MySQLDSL.uncompressedLength(MySQLDSL.val(string));
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.MYSQL})
    @NotNull
    public static Field<Integer> uncompressedLength(byte[] string) {
        return MySQLDSL.uncompressedLength(MySQLDSL.val(string));
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.MYSQL})
    @NotNull
    public static <T> Field<Integer> uncompressedLength(Field<T> string) {
        return MySQLDSL.function("uncompressed_length", Integer.class, string);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.MYSQL})
    @NotNull
    public static Field<String> sha1(String string) {
        return MySQLDSL.sha1(MySQLDSL.val(string));
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.MYSQL})
    @NotNull
    public static Field<byte[]> sha1(byte[] string) {
        return MySQLDSL.sha1(MySQLDSL.val(string));
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.MYSQL})
    @NotNull
    public static <T> Field<T> sha1(Field<T> string) {
        return MySQLDSL.function("sha1", string.getType(), string);
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.MYSQL})
    @NotNull
    public static Field<String> sha2(String string, int hashLength) {
        return MySQLDSL.sha2(MySQLDSL.val(string), MySQLDSL.val(hashLength));
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.MYSQL})
    @NotNull
    public static Field<byte[]> sha2(byte[] string, int hashLength) {
        return MySQLDSL.sha2(MySQLDSL.val(string), MySQLDSL.val(hashLength));
    }

    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.MYSQL})
    @NotNull
    public static <T> Field<T> sha2(Field<T> string, Field<Integer> hashLength) {
        return MySQLDSL.function("sha2", string.getType(), string, hashLength);
    }

    @Deprecated(forRemoval=true, since="3.15")
    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.MYSQL})
    @NotNull
    public static Field<String> password(String string) {
        return MySQLDSL.password(MySQLDSL.val(string));
    }

    @Deprecated(forRemoval=true, since="3.15")
    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.MYSQL})
    @NotNull
    public static Field<byte[]> password(byte[] string) {
        return MySQLDSL.password(MySQLDSL.val(string));
    }

    @Deprecated(forRemoval=true, since="3.15")
    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.MYSQL})
    @NotNull
    public static <T> Field<T> password(Field<T> string) {
        return MySQLDSL.function("password", string.getType(), string);
    }

    @Deprecated(forRemoval=true)
    @Support(value={SQLDialect.AURORA_MYSQL, SQLDialect.MEMSQL, SQLDialect.MYSQL})
    @NotNull
    public static <T> Field<T> values(Field<T> values2) {
        return MySQLDSL.function("values", values2.getDataType(), values2);
    }

    public static <E extends Enum<E>> E enumType(Class<E> type, int index) {
        if (index <= 0) {
            return null;
        }
        Enum[] values2 = (Enum[])type.getEnumConstants();
        if (index > values2.length) {
            return null;
        }
        return (E)values2[index - 1];
    }
}

