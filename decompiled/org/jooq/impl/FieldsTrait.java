/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.stream.Stream;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.Fields;
import org.jooq.Name;
import org.jooq.impl.FieldsImpl;

interface FieldsTrait
extends Fields {
    @Override
    default public Field<?>[] fields() {
        return this.fieldsRow().fields();
    }

    @Override
    default public Stream<Field<?>> fieldStream() {
        return this.fieldsRow().fieldStream();
    }

    @Override
    default public <T> Field<T> field(Field<T> field2) {
        return this.fieldsRow().field(field2);
    }

    @Override
    @Deprecated
    default public Field<?> field(String name) {
        return this.fieldsRow().field(name);
    }

    @Override
    @Deprecated
    default public <T> Field<T> field(String name, Class<T> type) {
        return this.fieldsRow().field(name, type);
    }

    @Override
    @Deprecated
    default public <T> Field<T> field(String name, DataType<T> dataType) {
        return this.fieldsRow().field(name, dataType);
    }

    @Override
    @Deprecated
    default public Field<?> field(Name name) {
        return this.fieldsRow().field(name);
    }

    @Override
    @Deprecated
    default public <T> Field<T> field(Name name, Class<T> type) {
        return this.fieldsRow().field(name, type);
    }

    @Override
    @Deprecated
    default public <T> Field<T> field(Name name, DataType<T> dataType) {
        return this.fieldsRow().field(name, dataType);
    }

    @Override
    default public Field<?> field(int index) {
        return this.fieldsRow().field(index);
    }

    @Override
    default public <T> Field<T> field(int index, Class<T> type) {
        return this.fieldsRow().field(index, type);
    }

    @Override
    default public <T> Field<T> field(int index, DataType<T> dataType) {
        return this.fieldsRow().field(index, dataType);
    }

    @Override
    default public Field<?>[] fields(Field<?> ... fields) {
        return this.fieldsRow().fields(fields);
    }

    @Override
    default public Field<?>[] fields(String ... names) {
        return this.fieldsRow().fields(names);
    }

    @Override
    default public Field<?>[] fields(Name ... names) {
        return this.fieldsRow().fields(names);
    }

    @Override
    default public Field<?>[] fields(int ... indexes) {
        return this.fieldsRow().fields(indexes);
    }

    @Override
    default public int indexOf(Field<?> field2) {
        return this.fieldsRow().indexOf(field2);
    }

    @Override
    default public int indexOf(String name) {
        return this.fieldsRow().indexOf(name);
    }

    @Override
    default public int indexOf(Name name) {
        return this.fieldsRow().indexOf(name);
    }

    @Override
    default public Class<?>[] types() {
        return FieldsImpl.fieldsRow0(this).types();
    }

    @Override
    default public Class<?> type(int index) {
        return FieldsImpl.fieldsRow0(this).type(index);
    }

    @Override
    default public Class<?> type(String name) {
        return FieldsImpl.fieldsRow0(this).type(name);
    }

    @Override
    default public Class<?> type(Name name) {
        return FieldsImpl.fieldsRow0(this).type(name);
    }

    @Override
    default public DataType<?>[] dataTypes() {
        return FieldsImpl.fieldsRow0(this).dataTypes();
    }

    @Override
    default public DataType<?> dataType(int index) {
        return FieldsImpl.fieldsRow0(this).dataType(index);
    }

    @Override
    default public DataType<?> dataType(String name) {
        return FieldsImpl.fieldsRow0(this).dataType(name);
    }

    @Override
    default public DataType<?> dataType(Name name) {
        return FieldsImpl.fieldsRow0(this).dataType(name);
    }
}

