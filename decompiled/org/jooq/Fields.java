/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.Name;
import org.jooq.Row;

public interface Fields {
    @NotNull
    public @NotNull Field<?> @NotNull [] fields();

    @NotNull
    public Row fieldsRow();

    @NotNull
    public Stream<Field<?>> fieldStream();

    @Nullable
    public <T> Field<T> field(Field<T> var1);

    @Nullable
    public Field<?> field(String var1);

    @Nullable
    public <T> Field<T> field(String var1, Class<T> var2);

    @Nullable
    public <T> Field<T> field(String var1, DataType<T> var2);

    @Nullable
    public Field<?> field(Name var1);

    @Nullable
    public <T> Field<T> field(Name var1, Class<T> var2);

    @Nullable
    public <T> Field<T> field(Name var1, DataType<T> var2);

    @Nullable
    public Field<?> field(int var1);

    @Nullable
    public <T> Field<T> field(int var1, Class<T> var2);

    @Nullable
    public <T> Field<T> field(int var1, DataType<T> var2);

    public @Nullable Field<?> @NotNull [] fields(Field<?> ... var1);

    public @Nullable Field<?> @NotNull [] fields(String ... var1);

    public @Nullable Field<?> @NotNull [] fields(Name ... var1);

    public @Nullable Field<?> @NotNull [] fields(int ... var1);

    public int indexOf(Field<?> var1);

    public int indexOf(String var1);

    public int indexOf(Name var1);

    @NotNull
    public @NotNull Class<?> @NotNull [] types();

    @Nullable
    public Class<?> type(int var1);

    @Nullable
    public Class<?> type(String var1);

    @Nullable
    public Class<?> type(Name var1);

    @NotNull
    public @NotNull DataType<?> @NotNull [] dataTypes();

    @Nullable
    public DataType<?> dataType(int var1);

    @Nullable
    public DataType<?> dataType(String var1);

    @Nullable
    public DataType<?> dataType(Name var1);
}

