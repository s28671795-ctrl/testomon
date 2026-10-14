/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.util.mariadb;

import org.jooq.Field;
import org.jooq.impl.DSL;

public class MariaDBDSL
extends DSL {
    protected MariaDBDSL() {
    }

    public static Field<String> decode(String cryptString, String keyString) {
        return MariaDBDSL.decode(MariaDBDSL.val(cryptString), MariaDBDSL.val(keyString));
    }

    public static Field<String> decode(Field<String> cryptString, Field<String> keyString) {
        return MariaDBDSL.function("decode", String.class, cryptString, keyString);
    }

    public static Field<String> encode(String string, String keyString) {
        return MariaDBDSL.encode(MariaDBDSL.val(string), MariaDBDSL.val(keyString));
    }

    public static Field<String> encode(Field<String> string, Field<String> keyString) {
        return MariaDBDSL.function("encode", String.class, string, keyString);
    }

    public static Field<String> aesDecrypt(String cryptString, String keyString) {
        return MariaDBDSL.aesDecrypt(MariaDBDSL.val(cryptString), MariaDBDSL.val(keyString));
    }

    public static Field<String> aesDecrypt(Field<String> cryptString, Field<String> keyString) {
        return MariaDBDSL.function("aes_decrypt", String.class, cryptString, keyString);
    }

    public static Field<String> aesEncrypt(String string, String keyString) {
        return MariaDBDSL.aesEncrypt(MariaDBDSL.val(string), MariaDBDSL.val(keyString));
    }

    public static Field<String> aesEncrypt(Field<String> string, Field<String> keyString) {
        return MariaDBDSL.function("aes_encrypt", String.class, string, keyString);
    }

    public static Field<String> desDecrypt(String cryptString) {
        return MariaDBDSL.desDecrypt(MariaDBDSL.val(cryptString));
    }

    public static Field<String> desDecrypt(Field<String> cryptString) {
        return MariaDBDSL.function("des_decrypt", String.class, cryptString);
    }

    public static Field<String> desDecrypt(String cryptString, String keyString) {
        return MariaDBDSL.desDecrypt(MariaDBDSL.val(cryptString), MariaDBDSL.val(keyString));
    }

    public static Field<String> desDecrypt(Field<String> cryptString, Field<String> keyString) {
        return MariaDBDSL.function("des_decrypt", String.class, cryptString, keyString);
    }

    public static Field<String> desEncrypt(String string) {
        return MariaDBDSL.desEncrypt(MariaDBDSL.val(string));
    }

    public static Field<String> desEncrypt(Field<String> string) {
        return MariaDBDSL.function("des_encrypt", String.class, string);
    }

    public static Field<String> desEncrypt(String string, String keyString) {
        return MariaDBDSL.desEncrypt(MariaDBDSL.val(string), MariaDBDSL.val(keyString));
    }

    public static Field<String> desEncrypt(Field<String> string, Field<String> keyString) {
        return MariaDBDSL.function("des_encrypt", String.class, string, keyString);
    }

    public static Field<String> compress(String string) {
        return MariaDBDSL.compress(MariaDBDSL.val(string));
    }

    public static Field<String> compress(Field<String> string) {
        return MariaDBDSL.function("compress", String.class, string);
    }

    public static Field<String> uncompress(String string) {
        return MariaDBDSL.uncompress(MariaDBDSL.val(string));
    }

    public static Field<String> uncompress(Field<String> string) {
        return MariaDBDSL.function("uncompress", String.class, string);
    }

    public static Field<Integer> uncompressedLength(String string) {
        return MariaDBDSL.uncompressedLength(MariaDBDSL.val(string));
    }

    public static Field<Integer> uncompressedLength(Field<String> string) {
        return MariaDBDSL.function("uncompressed_length", Integer.class, string);
    }

    public static Field<String> sha1(String string) {
        return MariaDBDSL.sha1(MariaDBDSL.val(string));
    }

    public static Field<String> sha1(Field<String> string) {
        return MariaDBDSL.function("sha1", String.class, string);
    }

    public static Field<String> password(String string) {
        return MariaDBDSL.password(MariaDBDSL.val(string));
    }

    public static Field<String> password(Field<String> string) {
        return MariaDBDSL.function("password", String.class, string);
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

