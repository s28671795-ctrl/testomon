/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.util.postgres;

import org.jetbrains.annotations.NotNull;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.SQLDialect;
import org.jooq.Support;
import org.jooq.Table;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;

public class PostgresDSL
extends DSL {
    protected PostgresDSL() {
    }

    @Deprecated
    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T> Condition arrayOverlap(T[] left, T[] right) {
        return PostgresDSL.arrayOverlap(PostgresDSL.val(left), PostgresDSL.val(right));
    }

    @Deprecated
    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T> Condition arrayOverlap(T[] left, Field<T[]> right) {
        return PostgresDSL.arrayOverlap(PostgresDSL.val(left), right);
    }

    @Deprecated
    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T> Condition arrayOverlap(Field<T[]> left, T[] right) {
        return PostgresDSL.arrayOverlap(left, PostgresDSL.val(right));
    }

    @Deprecated
    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T> Condition arrayOverlap(Field<T[]> left, Field<T[]> right) {
        return DSL.condition("{0} && {1}", left, right);
    }

    @Deprecated
    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T> Field<T[]> arrayAppend(T[] array, T value) {
        return PostgresDSL.arrayAppend0(PostgresDSL.val(array), PostgresDSL.val(value));
    }

    @Deprecated
    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T> Field<T[]> arrayAppend(T[] array, Field<T> value) {
        return PostgresDSL.arrayAppend0(PostgresDSL.val(array), value);
    }

    @Deprecated
    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T> Field<T[]> arrayAppend(Field<T[]> array, T value) {
        return PostgresDSL.arrayAppend0(array, PostgresDSL.val(value));
    }

    @Deprecated
    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T> Field<T[]> arrayAppend(Field<T[]> array, Field<T> value) {
        return PostgresDSL.arrayAppend0(array, value);
    }

    static <T> Field<T[]> arrayAppend0(Field<T[]> array, Field<T> value) {
        return PostgresDSL.function("array_append", PostgresDSL.nullSafeDataType(array), array, value);
    }

    @Deprecated
    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T> Field<T[]> arrayPrepend(T value, T[] array) {
        return PostgresDSL.arrayPrepend0(PostgresDSL.val(value), PostgresDSL.val(array));
    }

    @Deprecated
    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T> Field<T[]> arrayPrepend(Field<T> value, T[] array) {
        return PostgresDSL.arrayPrepend0(value, PostgresDSL.val(array));
    }

    @Deprecated
    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T> Field<T[]> arrayPrepend(T value, Field<T[]> array) {
        return PostgresDSL.arrayPrepend0(PostgresDSL.val(value), array);
    }

    @Deprecated
    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T> Field<T[]> arrayPrepend(Field<T> value, Field<T[]> array) {
        return PostgresDSL.arrayPrepend0(value, array);
    }

    static <T> Field<T[]> arrayPrepend0(Field<T> value, Field<T[]> array) {
        return PostgresDSL.function("array_prepend", PostgresDSL.nullSafeDataType(array), value, array);
    }

    @Deprecated
    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T> Field<T[]> arrayCat(T[] array1, T[] array2) {
        return PostgresDSL.arrayCat(PostgresDSL.val(array1), PostgresDSL.val(array2));
    }

    @Deprecated
    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T> Field<T[]> arrayCat(T[] array1, Field<T[]> array2) {
        return PostgresDSL.arrayCat(PostgresDSL.val(array1), array2);
    }

    @Deprecated
    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T> Field<T[]> arrayCat(Field<T[]> array1, T[] array2) {
        return PostgresDSL.arrayCat(array1, PostgresDSL.val(array2));
    }

    @Deprecated
    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T> Field<T[]> arrayCat(Field<T[]> array1, Field<T[]> array2) {
        return PostgresDSL.function("array_cat", PostgresDSL.nullSafeDataType(array1), array1, array2);
    }

    @Deprecated
    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T> Field<T[]> arrayRemove(T[] array, T element) {
        return PostgresDSL.arrayRemove0(PostgresDSL.val(array), PostgresDSL.val(element));
    }

    @Deprecated
    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T> Field<T[]> arrayRemove(Field<T[]> array, T element) {
        return PostgresDSL.arrayRemove0(PostgresDSL.nullSafe(array), PostgresDSL.val(element));
    }

    @Deprecated
    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T> Field<T[]> arrayRemove(T[] array, Field<T> element) {
        return PostgresDSL.arrayRemove0(PostgresDSL.val(array), element);
    }

    @Deprecated
    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T> Field<T[]> arrayRemove(Field<T[]> array, Field<T> element) {
        return PostgresDSL.arrayRemove0(array, element);
    }

    static <T> Field<T[]> arrayRemove0(Field<T[]> array, Field<T> element) {
        return PostgresDSL.function("array_remove", array.getDataType(), array, element);
    }

    @Deprecated
    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T> Field<T[]> arrayReplace(T[] array, T search, T replace) {
        return PostgresDSL.arrayReplace0(PostgresDSL.val(array), PostgresDSL.val(search), PostgresDSL.val(replace));
    }

    @Deprecated
    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T> Field<T[]> arrayReplace(T[] array, Field<T> search, Field<T> replace) {
        return PostgresDSL.arrayReplace0(PostgresDSL.val(array), search, replace);
    }

    @Deprecated
    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T> Field<T[]> arrayReplace(Field<T[]> array, T search, T replace) {
        return PostgresDSL.arrayReplace0(PostgresDSL.nullSafe(array), PostgresDSL.val(search), PostgresDSL.val(replace));
    }

    @Deprecated
    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T> Field<T[]> arrayReplace(Field<T[]> array, Field<T> search, Field<T> replace) {
        return PostgresDSL.arrayReplace0(array, search, replace);
    }

    static <T> Field<T[]> arrayReplace0(Field<T[]> array, Field<T> search, Field<T> replace) {
        return PostgresDSL.function("array_replace", array.getDataType(), array, search, replace);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T> Field<T[]> arrayFill(T value, Integer[] dimensions) {
        return PostgresDSL.arrayFill(PostgresDSL.val(value), PostgresDSL.val(dimensions));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T> Field<T[]> arrayFill(Field<T> value, Integer[] dimensions) {
        return PostgresDSL.arrayFill(PostgresDSL.nullSafe(value), PostgresDSL.val(dimensions));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T> Field<T[]> arrayFill(T value, Field<Integer[]> dimensions) {
        return PostgresDSL.arrayFill(PostgresDSL.val(value), dimensions);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T> Field<T[]> arrayFill(Field<T> value, Field<Integer[]> dimensions) {
        return PostgresDSL.function("array_fill", PostgresDSL.nullSafeDataType(value).getArrayDataType(), value, dimensions);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T> Field<T[]> arrayFill(T value, Integer[] dimensions, Integer[] bounds) {
        return PostgresDSL.arrayFill(PostgresDSL.val(value), PostgresDSL.val(dimensions), PostgresDSL.val(bounds));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T> Field<T[]> arrayFill(Field<T> value, Integer[] dimensions, Integer[] bounds) {
        return PostgresDSL.arrayFill(PostgresDSL.nullSafe(value), PostgresDSL.val(dimensions), PostgresDSL.val(bounds));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T> Field<T[]> arrayFill(T value, Field<Integer[]> dimensions, Field<Integer[]> bounds) {
        return PostgresDSL.arrayFill(PostgresDSL.val(value), dimensions, bounds);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    public static <T> Field<T[]> arrayFill(Field<T> value, Field<Integer[]> dimensions, Field<Integer[]> bounds) {
        return PostgresDSL.function("array_fill", PostgresDSL.nullSafeDataType(value).getArrayDataType(), value, dimensions, bounds);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<Integer> arrayLength(Object[] array) {
        return PostgresDSL.arrayLength(PostgresDSL.val(array));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<Integer> arrayLength(Field<? extends Object[]> array) {
        return PostgresDSL.field("{array_length}({0}, 1)", SQLDataType.INTEGER, new QueryPart[]{array});
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<String> arrayToString(Object[] array, String delimiter) {
        return PostgresDSL.arrayToString(PostgresDSL.val(array), PostgresDSL.val((Object)delimiter, String.class));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<String> arrayToString(Object[] array, Field<String> delimiter) {
        return PostgresDSL.arrayToString(PostgresDSL.val(array), delimiter);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<String> arrayToString(Field<? extends Object[]> array, String delimiter) {
        return PostgresDSL.arrayToString(array, PostgresDSL.val((Object)delimiter, String.class));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<String> arrayToString(Field<? extends Object[]> array, Field<String> delimiter) {
        return PostgresDSL.function("array_to_string", SQLDataType.VARCHAR, array, delimiter);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<String[]> stringToArray(String string, String delimiter) {
        return PostgresDSL.stringToArray(PostgresDSL.val((Object)string, String.class), PostgresDSL.val((Object)delimiter, String.class));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<String[]> stringToArray(String string, Field<String> delimiter) {
        return PostgresDSL.stringToArray(PostgresDSL.val((Object)string, String.class), delimiter);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<String[]> stringToArray(Field<String> string, String delimiter) {
        return PostgresDSL.stringToArray(string, PostgresDSL.val((Object)delimiter, String.class));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<String[]> stringToArray(Field<String> string, Field<String> delimiter) {
        return PostgresDSL.function("string_to_array", SQLDataType.VARCHAR.getArrayDataType(), string, delimiter);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<String[]> stringToArray(String string, String delimiter, String nullString) {
        return PostgresDSL.stringToArray(PostgresDSL.val((Object)string, String.class), PostgresDSL.val((Object)delimiter, String.class), PostgresDSL.val((Object)nullString, String.class));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<String[]> stringToArray(String string, Field<String> delimiter, Field<String> nullString) {
        return PostgresDSL.stringToArray(PostgresDSL.val((Object)string, String.class), delimiter, nullString);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<String[]> stringToArray(Field<String> string, String delimiter, String nullString) {
        return PostgresDSL.stringToArray(string, PostgresDSL.val((Object)delimiter, String.class), PostgresDSL.val((Object)nullString, String.class));
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.POSTGRES, SQLDialect.YUGABYTEDB})
    @NotNull
    public static Field<String[]> stringToArray(Field<String> string, Field<String> delimiter, Field<String> nullString) {
        return PostgresDSL.function("string_to_array", SQLDataType.VARCHAR.getArrayDataType(), string, delimiter, nullString);
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.POSTGRES})
    @NotNull
    public static Table<Record> only(Table<?> table) {
        return PostgresDSL.table("{only} {0}", new QueryPart[]{table});
    }

    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.POSTGRES})
    @Deprecated(forRemoval=true, since="3.16")
    @NotNull
    public static Field<Long> oid(Table<?> table) {
        return PostgresDSL.field("{0}.oid", Long.class, table);
    }
}

