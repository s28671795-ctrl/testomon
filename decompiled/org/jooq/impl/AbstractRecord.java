/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.io.IOException;
import java.io.Writer;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.jooq.Attachable;
import org.jooq.CSVFormat;
import org.jooq.ChartFormat;
import org.jooq.ContextConverter;
import org.jooq.Converter;
import org.jooq.DataType;
import org.jooq.EmbeddableRecord;
import org.jooq.Field;
import org.jooq.JSONFormat;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.Record10;
import org.jooq.Record11;
import org.jooq.Record12;
import org.jooq.Record13;
import org.jooq.Record14;
import org.jooq.Record15;
import org.jooq.Record16;
import org.jooq.Record17;
import org.jooq.Record18;
import org.jooq.Record19;
import org.jooq.Record2;
import org.jooq.Record20;
import org.jooq.Record21;
import org.jooq.Record22;
import org.jooq.Record3;
import org.jooq.Record4;
import org.jooq.Record5;
import org.jooq.Record6;
import org.jooq.Record7;
import org.jooq.Record8;
import org.jooq.Record9;
import org.jooq.RecordMapper;
import org.jooq.Result;
import org.jooq.TXTFormat;
import org.jooq.Table;
import org.jooq.UniqueKey;
import org.jooq.XMLFormat;
import org.jooq.conf.SettingsTools;
import org.jooq.exception.InvalidResultException;
import org.jooq.exception.MappingException;
import org.jooq.impl.AbstractResult;
import org.jooq.impl.AbstractRow;
import org.jooq.impl.AbstractStore;
import org.jooq.impl.Convert;
import org.jooq.impl.DefaultRecordMapper;
import org.jooq.impl.EmbeddableTableField;
import org.jooq.impl.ResultImpl;
import org.jooq.impl.ThreadGuard;
import org.jooq.impl.ThrowingFunction;
import org.jooq.impl.Tools;
import org.jooq.tools.StringUtils;
import org.w3c.dom.Document;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

abstract class AbstractRecord
extends AbstractStore
implements Record {
    final AbstractRow<? extends AbstractRecord> fields;
    final Object[] values;
    final Object[] originals;
    final BitSet changed;
    boolean fetched;

    @Deprecated
    AbstractRecord(Collection<? extends Field<?>> fields) {
        this(Tools.row0(fields.toArray(Tools.EMPTY_FIELD)));
    }

    @Deprecated
    AbstractRecord(Field<?> ... fields) {
        this(Tools.row0(fields));
    }

    AbstractRecord(AbstractRow<?> fields) {
        int size = fields.size();
        this.fields = fields;
        this.values = new Object[size];
        this.originals = new Object[size];
        this.changed = new BitSet(size);
    }

    final List<Attachable> getAttachables() {
        ArrayList<Attachable> result = null;
        int size = this.size();
        for (int i = 0; i < size; ++i) {
            Object object = this.values[i];
            if (!(object instanceof Attachable)) continue;
            Attachable a = (Attachable)object;
            if (result == null) {
                result = new ArrayList<Attachable>();
            }
            result.add(a);
        }
        return result == null ? Collections.emptyList() : result;
    }

    @Override
    public final Field<?>[] fields() {
        return this.fields.fields();
    }

    @Override
    public final Stream<Field<?>> fieldStream() {
        return this.fields.fieldStream();
    }

    @Override
    public final <T> Field<T> field(Field<T> field2) {
        return this.fields.field(field2);
    }

    @Override
    public final Field<?> field(String name) {
        return this.fields.field(name);
    }

    @Override
    public final <T> Field<T> field(String name, Class<T> type) {
        return this.fields.field(name, type);
    }

    @Override
    public final <T> Field<T> field(String name, DataType<T> dataType) {
        return this.fields.field(name, dataType);
    }

    @Override
    public final Field<?> field(Name name) {
        return this.fields.field(name);
    }

    @Override
    public final <T> Field<T> field(Name name, Class<T> type) {
        return this.fields.field(name, type);
    }

    @Override
    public final <T> Field<T> field(Name name, DataType<T> dataType) {
        return this.fields.field(name, dataType);
    }

    @Override
    public final Field<?> field(int index) {
        return index >= 0 && index < this.fields.size() ? this.fields.field(index) : null;
    }

    @Override
    public final <T> Field<T> field(int index, Class<T> type) {
        return this.fields.field(index, type);
    }

    @Override
    public final <T> Field<T> field(int index, DataType<T> dataType) {
        return this.fields.field(index, dataType);
    }

    @Override
    public final Field<?>[] fields(Field<?> ... f) {
        return this.fields.fields(f);
    }

    @Override
    public final Field<?>[] fields(String ... fieldNames) {
        return this.fields.fields(fieldNames);
    }

    @Override
    public final Field<?>[] fields(Name ... fieldNames) {
        return this.fields.fields(fieldNames);
    }

    @Override
    public final Field<?>[] fields(int ... fieldIndexes) {
        return this.fields.fields(fieldIndexes);
    }

    @Override
    public final int indexOf(Field<?> field2) {
        return this.fields.indexOf(field2);
    }

    @Override
    public final int indexOf(String fieldName) {
        return this.fields.indexOf(fieldName);
    }

    @Override
    public final int indexOf(Name fieldName) {
        return this.fields.indexOf(fieldName);
    }

    @Override
    public final Class<?>[] types() {
        return this.fields.types();
    }

    @Override
    public final Class<?> type(int fieldIndex) {
        return this.fields.type(fieldIndex);
    }

    @Override
    public final Class<?> type(String fieldName) {
        return this.fields.type(fieldName);
    }

    @Override
    public final Class<?> type(Name fieldName) {
        return this.fields.type(fieldName);
    }

    @Override
    public final DataType<?>[] dataTypes() {
        return this.fields.dataTypes();
    }

    @Override
    public final DataType<?> dataType(int fieldIndex) {
        return this.fields.dataType(fieldIndex);
    }

    @Override
    public final DataType<?> dataType(String fieldName) {
        return this.fields.dataType(fieldName);
    }

    @Override
    public final DataType<?> dataType(Name fieldName) {
        return this.fields.dataType(fieldName);
    }

    @Override
    public final int size() {
        return this.fields.size();
    }

    @Override
    public final <T> T get(Field<T> field2) {
        int index = this.fields.indexOf(field2);
        if (index >= 0) {
            return (T)this.get(index);
        }
        if (Tools.nonReplacingEmbeddable(field2)) {
            return (T)Tools.newRecord(this.fetched, ((EmbeddableTableField)field2).recordType).operate(new TransferRecordState(Tools.embeddedFields(field2)));
        }
        throw Tools.indexFail(this.fields, field2);
    }

    @Override
    public final <U> U get(Field<?> field2, Class<? extends U> type) {
        Object t2 = this.get(field2);
        return Tools.converterOrFail(this, t2, field2.getType(), type).from(t2, Tools.converterContext(this));
    }

    @Override
    public final <T, U> U get(Field<T> field2, Converter<? super T, ? extends U> converter) {
        return ContextConverter.scoped(converter).from(this.get(field2), Tools.converterContext(this));
    }

    @Override
    public final Object get(int index) {
        return this.values[this.safeIndex(index)];
    }

    @Override
    public final <U> U get(int index, Class<? extends U> type) {
        Object t2 = this.get(index);
        return Tools.converterOrFail(this, t2, this.field(this.safeIndex(index)).getType(), type).from(t2, Tools.converterContext(this));
    }

    @Override
    public final <U> U get(int index, Converter<?, ? extends U> converter) {
        return Convert.convert(this.get(index), converter);
    }

    @Override
    public final Object get(String fieldName) {
        return this.get(Tools.indexOrFail(this.fields, fieldName));
    }

    public final <T> T get(String fieldName, Class<? extends T> type) {
        return this.get(Tools.indexOrFail(this.fields, fieldName), type);
    }

    @Override
    public final <U> U get(String fieldName, Converter<?, ? extends U> converter) {
        return Convert.convert(this.get(fieldName), converter);
    }

    @Override
    public final Object get(Name fieldName) {
        return this.get(Tools.indexOrFail(this.fields, fieldName));
    }

    public final <T> T get(Name fieldName, Class<? extends T> type) {
        return this.get(Tools.indexOrFail(this.fields, fieldName), type);
    }

    @Override
    public final <U> U get(Name fieldName, Converter<?, ? extends U> converter) {
        return Convert.convert(this.get(fieldName), converter);
    }

    @Deprecated
    protected final void setValue(int index, Object value) {
        this.set(index, value);
    }

    protected final void set(int index, Object value) {
        this.set(index, this.field(index), value);
    }

    @Override
    public final <T> void set(Field<T> field2, T value) {
        int index = this.fields.indexOf(field2);
        this.set(field2, index, value);
    }

    final <T> void set(Field<T> field2, int index, T value) {
        if (index >= 0) {
            this.set(index, field2, value);
        } else if (Tools.nonReplacingEmbeddable(field2)) {
            Object[] objectArray;
            Field<?>[] f = Tools.embeddedFields(field2);
            if (value instanceof EmbeddableRecord) {
                EmbeddableRecord e = (EmbeddableRecord)value;
                objectArray = e.intoArray();
            } else {
                objectArray = new Object[f.length];
            }
            Object[] v = objectArray;
            for (int i = 0; i < f.length; ++i) {
                this.set(Tools.indexOrFail(this.fields, f[i]), f[i], v[i]);
            }
        } else {
            throw Tools.indexFail(this.fields, field2);
        }
    }

    final void set(int index, Field<?> field2, Object value) {
        UniqueKey<?> key = this.getPrimaryKey();
        if (key == null || !key.getFields().contains(field2)) {
            this.changed.set(index);
        } else if (this.changed.get(index)) {
            this.changed.set(index);
        } else if (SettingsTools.updatablePrimaryKeys(Tools.settings(this))) {
            this.changed.set(index);
        } else if (this.originals[index] == null) {
            this.changed.set(index);
        } else {
            this.changed.set(index, this.changed.get(index) || !StringUtils.equals(this.values[index], value));
            if (this.changed.get(index)) {
                this.changed(true);
            }
        }
        this.values[index] = value;
    }

    @Override
    public final <T, U> void set(Field<T> field2, U value, Converter<? extends T, ? super U> converter) {
        this.set(field2, ContextConverter.scoped(converter).to(value, Tools.converterContext(this)));
    }

    @Override
    public <T> Record with(Field<T> field2, T value) {
        this.set(field2, value);
        return this;
    }

    @Override
    public <T, U> Record with(Field<T> field2, U value, Converter<? extends T, ? super U> converter) {
        this.set(field2, value, converter);
        return this;
    }

    final void setValues(Field<?>[] fields, AbstractRecord record) {
        this.fetched = record.fetched;
        for (Field<?> field2 : fields) {
            int targetIndex = Tools.indexOrFail(this.fields, field2);
            int sourceIndex = Tools.indexOrFail(record.fields, field2);
            this.values[targetIndex] = record.get(sourceIndex);
            this.originals[targetIndex] = record.original(sourceIndex);
            this.changed.set(targetIndex, record.changed(sourceIndex));
        }
    }

    final void intern0(int fieldIndex) {
        this.safeIndex(fieldIndex);
        if (this.field(fieldIndex).getType() == String.class) {
            this.values[fieldIndex] = this.intern((String)this.values[fieldIndex]);
            this.originals[fieldIndex] = this.intern((String)this.originals[fieldIndex]);
        }
    }

    final int safeIndex(int index) {
        if (index >= 0 && index < this.values.length) {
            return index;
        }
        throw new IllegalArgumentException("No field at index " + index + " in Record type " + String.valueOf(this.fields));
    }

    final String intern(String string) {
        return string == null ? null : string.intern();
    }

    UniqueKey<?> getPrimaryKey() {
        return null;
    }

    @Override
    public Record original() {
        return Tools.newRecord(this.fetched, this.getClass(), this.fields, this.configuration()).operate(record -> {
            for (int i = 0; i < this.originals.length; ++i) {
                record.values[i] = record.originals[i] = this.originals[i];
            }
            return record;
        });
    }

    @Override
    public final <T> T original(Field<T> field2) {
        return (T)this.original(Tools.indexOrFail(this.fields, field2));
    }

    @Override
    public final Object original(int fieldIndex) {
        return this.originals[this.safeIndex(fieldIndex)];
    }

    @Override
    public final Object original(String fieldName) {
        return this.original(Tools.indexOrFail(this.fields, fieldName));
    }

    @Override
    public final Object original(Name fieldName) {
        return this.original(Tools.indexOrFail(this.fields, fieldName));
    }

    @Override
    public final boolean changed() {
        return !this.changed.isEmpty();
    }

    @Override
    public final boolean changed(Field<?> field2) {
        return this.changed(Tools.indexOrFail(this.fields, field2));
    }

    @Override
    public final boolean changed(int fieldIndex) {
        return this.changed.get(this.safeIndex(fieldIndex));
    }

    @Override
    public final boolean changed(String fieldName) {
        return this.changed(Tools.indexOrFail(this.fields, fieldName));
    }

    @Override
    public final boolean changed(Name fieldName) {
        return this.changed(Tools.indexOrFail(this.fields, fieldName));
    }

    @Override
    public final void changed(boolean c) {
        this.changed.set(0, this.values.length, c);
        if (!c) {
            System.arraycopy(this.values, 0, this.originals, 0, this.values.length);
        }
    }

    @Override
    public final void changed(Field<?> field2, boolean c) {
        this.changed(Tools.indexOrFail(this.fields, field2), c);
    }

    @Override
    public final void changed(int fieldIndex, boolean c) {
        this.safeIndex(fieldIndex);
        this.changed.set(fieldIndex, c);
        if (!c) {
            this.originals[fieldIndex] = this.values[fieldIndex];
        }
    }

    @Override
    public final void changed(String fieldName, boolean c) {
        this.changed(Tools.indexOrFail(this.fields, fieldName), c);
    }

    @Override
    public final void changed(Name fieldName, boolean c) {
        this.changed(Tools.indexOrFail(this.fields, fieldName), c);
    }

    @Override
    public final void reset() {
        this.changed.clear();
        System.arraycopy(this.originals, 0, this.values, 0, this.originals.length);
    }

    @Override
    public final void reset(Field<?> field2) {
        this.reset(Tools.indexOrFail(this.fields, field2));
    }

    @Override
    public final void reset(int fieldIndex) {
        this.safeIndex(fieldIndex);
        this.changed.clear(fieldIndex);
        this.values[fieldIndex] = this.originals[fieldIndex];
    }

    @Override
    public final void reset(String fieldName) {
        this.reset(Tools.indexOrFail(this.fields, fieldName));
    }

    @Override
    public final void reset(Name fieldName) {
        this.reset(Tools.indexOrFail(this.fields, fieldName));
    }

    @Override
    public final Object[] intoArray() {
        return (Object[])this.into((Object)Object[].class);
    }

    @Override
    public final List<Object> intoList() {
        return Arrays.asList(this.intoArray());
    }

    @Override
    public final Stream<Object> intoStream() {
        return (Stream)((Object)this.into((Object)Stream.class));
    }

    @Override
    public final Map<String, Object> intoMap() {
        LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
        int size = this.fields.size();
        for (int i = 0; i < size; ++i) {
            Field<?> field2 = this.fields.field(i);
            if (map.put(field2.getName(), this.get(i)) == null) continue;
            throw new InvalidResultException("Field " + field2.getName() + " is not unique in Record : " + String.valueOf(this));
        }
        return map;
    }

    @Override
    public final Record into(Field<?> ... f) {
        return Tools.newRecord(this.fetched, Record.class, Tools.row0(f), this.configuration()).operate(new TransferRecordState(f));
    }

    @Override
    public final <T1> Record1<T1> into(Field<T1> field1) {
        return (Record1)this.into((Object)new Field[]{field1});
    }

    @Override
    public final <T1, T2> Record2<T1, T2> into(Field<T1> field1, Field<T2> field2) {
        return (Record2)this.into((Object)new Field[]{field1, field2});
    }

    @Override
    public final <T1, T2, T3> Record3<T1, T2, T3> into(Field<T1> field1, Field<T2> field2, Field<T3> field3) {
        return (Record3)this.into((Object)new Field[]{field1, field2, field3});
    }

    @Override
    public final <T1, T2, T3, T4> Record4<T1, T2, T3, T4> into(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4) {
        return (Record4)this.into((Object)new Field[]{field1, field2, field3, field4});
    }

    @Override
    public final <T1, T2, T3, T4, T5> Record5<T1, T2, T3, T4, T5> into(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5) {
        return (Record5)this.into((Object)new Field[]{field1, field2, field3, field4, field5});
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6> Record6<T1, T2, T3, T4, T5, T6> into(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6) {
        return (Record6)this.into((Object)new Field[]{field1, field2, field3, field4, field5, field6});
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7> Record7<T1, T2, T3, T4, T5, T6, T7> into(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7) {
        return (Record7)this.into((Object)new Field[]{field1, field2, field3, field4, field5, field6, field7});
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8> Record8<T1, T2, T3, T4, T5, T6, T7, T8> into(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8) {
        return (Record8)this.into((Object)new Field[]{field1, field2, field3, field4, field5, field6, field7, field8});
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9> Record9<T1, T2, T3, T4, T5, T6, T7, T8, T9> into(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9) {
        return (Record9)this.into((Object)new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9});
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> Record10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> into(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10) {
        return (Record10)this.into((Object)new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10});
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> Record11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> into(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11) {
        return (Record11)this.into((Object)new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11});
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> Record12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> into(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12) {
        return (Record12)this.into((Object)new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12});
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> Record13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> into(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13) {
        return (Record13)this.into((Object)new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13});
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> Record14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14> into(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14) {
        return (Record14)this.into((Object)new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14});
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> Record15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15> into(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15) {
        return (Record15)this.into((Object)new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15});
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> Record16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> into(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16) {
        return (Record16)this.into((Object)new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16});
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> Record17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> into(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17) {
        return (Record17)this.into((Object)new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17});
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> Record18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18> into(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18) {
        return (Record18)this.into((Object)new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18});
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> Record19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> into(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19) {
        return (Record19)this.into((Object)new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19});
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> Record20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20> into(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20) {
        return (Record20)this.into((Object)new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20});
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> Record21<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21> into(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20, Field<T21> field21) {
        return (Record21)this.into((Object)new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21});
    }

    @Override
    public final <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> Record22<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22> into(Field<T1> field1, Field<T2> field2, Field<T3> field3, Field<T4> field4, Field<T5> field5, Field<T6> field6, Field<T7> field7, Field<T8> field8, Field<T9> field9, Field<T10> field10, Field<T11> field11, Field<T12> field12, Field<T13> field13, Field<T14> field14, Field<T15> field15, Field<T16> field16, Field<T17> field17, Field<T18> field18, Field<T19> field19, Field<T20> field20, Field<T21> field21, Field<T22> field22) {
        return (Record22)this.into((Object)new Field[]{field1, field2, field3, field4, field5, field6, field7, field8, field9, field10, field11, field12, field13, field14, field15, field16, field17, field18, field19, field20, field21, field22});
    }

    @Override
    public final <E> E into(Class<? extends E> type) {
        return this.fields.fields.mapper(Tools.configuration(this), type).map(this);
    }

    @Override
    public <E> E into(E object) {
        if (object == null) {
            throw new NullPointerException("Cannot copy Record into null");
        }
        Class<?> type = object.getClass();
        try {
            return (E)new DefaultRecordMapper(this.fields.fields, type, object, this.configuration()).map(this);
        }
        catch (MappingException e) {
            throw e;
        }
        catch (Exception e) {
            throw new MappingException("An error occurred when mapping record to " + String.valueOf(type), e);
        }
    }

    @Override
    public final <R extends Record> R into(Table<R> table) {
        return Tools.newRecord(this.fetched, table, this.configuration()).operate(new TransferRecordState(table.fields()));
    }

    final <R extends Record> R intoRecord(R record) {
        return (R)Tools.newRecord(this.fetched, () -> record, this.configuration()).operate(new TransferRecordState(null));
    }

    final <R extends Record> R intoRecord(Class<R> type) {
        return (R)Tools.newRecord(this.fetched, type, this.fields, this.configuration()).operate(new TransferRecordState(null));
    }

    @Override
    public final ResultSet intoResultSet() {
        return this.asResult().intoResultSet();
    }

    @Override
    public final <E> E map(RecordMapper<Record, E> mapper2) {
        return mapper2.map(this);
    }

    private final void from0(Object source2, int[] targetIndexMapping) {
        if (source2 == null) {
            return;
        }
        this.from((Record)Tools.configuration(this).recordUnmapperProvider().provide(source2.getClass(), this.fields.fields).unmap(this.prepareArrayOrIterableForUnmap(source2, targetIndexMapping)), targetIndexMapping);
        Tools.resetChangedOnNotNull(this);
    }

    protected void resetChangedOnNotNull() {
        Tools.resetChangedOnNotNull(this);
    }

    private final Object prepareArrayOrIterableForUnmap(Object source2, int[] targetIndexMapping) {
        List<Object> iterable;
        if (targetIndexMapping == null) {
            return source2;
        }
        boolean array = source2 instanceof Object[];
        Iterable<Object> iterable2 = array ? Arrays.asList((Object[])source2) : (iterable = source2 instanceof Iterable ? (Iterable)source2 : null);
        if (iterable != null) {
            Object[] result = new Object[this.size()];
            Iterator it = iterable.iterator();
            for (int i = 0; it.hasNext() && i < targetIndexMapping.length; ++i) {
                int index = targetIndexMapping[i];
                Object o = it.next();
                if (index < 0 || index >= result.length) continue;
                result[index] = o;
            }
            return array ? result : Arrays.asList(result);
        }
        return source2;
    }

    @Override
    public final void from(Object source2) {
        this.from0(source2, null);
    }

    @Override
    public final void from(Object source2, Field<?> ... f) {
        this.from0(source2, this.fields.fields.indexesOf(f));
    }

    @Override
    public final void from(Object source2, String ... fieldNames) {
        this.from0(source2, this.fields.fields.indexesOf(fieldNames));
    }

    @Override
    public final void from(Object source2, Name ... fieldNames) {
        this.from0(source2, this.fields.fields.indexesOf(fieldNames));
    }

    @Override
    public final void from(Object source2, int ... fieldIndexes) {
        this.from0(source2, fieldIndexes);
    }

    @Override
    public final void fromMap(Map<String, ?> map) {
        this.from(map);
    }

    @Override
    public final void fromMap(Map<String, ?> map, Field<?> ... f) {
        this.from(map, f);
    }

    @Override
    public final void fromMap(Map<String, ?> map, String ... fieldNames) {
        this.from(map, fieldNames);
    }

    @Override
    public final void fromMap(Map<String, ?> map, Name ... fieldNames) {
        this.from(map, fieldNames);
    }

    @Override
    public final void fromMap(Map<String, ?> map, int ... fieldIndexes) {
        this.from(map, fieldIndexes);
    }

    @Override
    public final void fromArray(Object ... array) {
        this.from(array);
    }

    @Override
    public final void fromArray(Object[] array, Field<?> ... f) {
        this.from((Object)array, f);
    }

    @Override
    public final void fromArray(Object[] array, String ... fieldNames) {
        this.from((Object)array, fieldNames);
    }

    @Override
    public final void fromArray(Object[] array, Name ... fieldNames) {
        this.from((Object)array, fieldNames);
    }

    @Override
    public final void fromArray(Object[] array, int ... fieldIndexes) {
        this.from((Object)array, fieldIndexes);
    }

    protected final void from(Record source2) {
        for (Field<?> field2 : this.fields.fields.fields) {
            Field<?> sourceField = source2.field(field2);
            if (sourceField == null || !source2.changed(sourceField)) continue;
            Tools.setValue((Record)this, field2, source2, sourceField);
        }
    }

    final void from(Record source2, int[] indexMapping) {
        int s2 = source2.size();
        int t2 = indexMapping == null ? s2 : indexMapping.length;
        for (int i = 0; i < s2 && i < t2; ++i) {
            int j;
            int n = j = indexMapping == null ? i : indexMapping[i];
            if (source2.field(j) == null || !source2.changed(j)) continue;
            this.set(this.field(j), j, source2.get(j));
        }
    }

    @Override
    public final void format(Writer writer, TXTFormat format) {
        this.asResult().format(writer, format);
    }

    @Override
    public final void formatCSV(Writer writer, CSVFormat format) {
        this.asResult().formatCSV(writer, format);
    }

    @Override
    public final void formatJSON(Writer writer, JSONFormat format) {
        format = format.mutable(true);
        try {
            switch (format.recordFormat()) {
                case ARRAY: {
                    AbstractResult.formatJSONArray0(this, this.fields, format, 0, writer);
                    break;
                }
                case OBJECT: {
                    AbstractResult.formatJSONMap0(this, this.fields, format, 0, writer);
                    break;
                }
                default: {
                    throw new IllegalArgumentException("Format not supported: " + String.valueOf(format));
                }
            }
            writer.flush();
        }
        catch (IOException e) {
            throw new org.jooq.exception.IOException("Exception while writing JSON", e);
        }
    }

    @Override
    public final void formatXML(Writer writer, XMLFormat format) {
        format = format.mutable(true);
        try {
            AbstractResult.formatXMLRecord(writer, format, 0, this, this.fields);
            writer.flush();
        }
        catch (IOException e) {
            throw new org.jooq.exception.IOException("Exception while writing XML", e);
        }
    }

    @Override
    public final void formatHTML(Writer writer) {
        Result<AbstractRecord> result = this.asResult();
        result.formatHTML(writer);
    }

    @Override
    public final void formatChart(Writer writer, ChartFormat format) {
        Result<AbstractRecord> result = this.asResult();
        result.formatChart(writer, format);
    }

    @Override
    public final void formatInsert(Writer writer) {
        this.formatInsert(writer, null, this.fields.fields.fields);
    }

    @Override
    public final void formatInsert(Writer writer, Table<?> table, Field<?> ... f) {
        Result<AbstractRecord> result = this.asResult();
        result.formatInsert(writer, table, f);
    }

    @Override
    public final Document intoXML(XMLFormat format) {
        Result<AbstractRecord> result = this.asResult();
        return result.intoXML(format);
    }

    @Override
    public final <H extends ContentHandler> H intoXML(H handler, XMLFormat format) throws SAXException {
        Result<AbstractRecord> result = this.asResult();
        return result.intoXML(handler, format);
    }

    public String toString() {
        return ThreadGuard.run(ThreadGuard.RECORD_TOSTRING, () -> this.asResult().toString(), () -> this.valuesRow().toString());
    }

    @Override
    public int compareTo(Record that) {
        if (that == this) {
            return 0;
        }
        if (that == null) {
            throw new NullPointerException();
        }
        if (this.size() != that.size()) {
            throw new ClassCastException(String.format("Trying to compare incomparable records (wrong degree):\n%s\n%s", this, that));
        }
        Class<?>[] thisTypes = this.fieldsRow().types();
        Class<?>[] thatTypes = that.fieldsRow().types();
        if (!Arrays.asList(thisTypes).equals(Arrays.asList(thatTypes))) {
            throw new ClassCastException(String.format("Trying to compare incomparable records (type mismatch):\n%s\n%s", this, that));
        }
        for (int i = 0; i < this.size(); ++i) {
            int compare;
            Object thisValue = this.get(i);
            Object thatValue = that.get(i);
            if (thisValue == null && thatValue == null) continue;
            if (thisValue == null) {
                return 1;
            }
            if (thatValue == null) {
                return -1;
            }
            if (thisValue.getClass().isArray() && thatValue.getClass().isArray()) {
                if (thisValue.getClass() == byte[].class) {
                    compare = this.compare((byte[])thisValue, (byte[])thatValue);
                    if (compare == 0) continue;
                    return compare;
                }
                if (!thisValue.getClass().getComponentType().isPrimitive()) {
                    compare = this.compare((Object[])thisValue, (Object[])thatValue);
                    if (compare == 0) continue;
                    return compare;
                }
                throw new ClassCastException(String.format("Unsupported data type in natural ordering: %s", thisValue.getClass()));
            }
            compare = this.compare0(thisValue, thatValue);
            if (compare == 0) continue;
            return compare;
        }
        return 0;
    }

    final int compare(byte[] array1, byte[] array2) {
        int length = Math.min(array1.length, array2.length);
        for (int i = 0; i < length; ++i) {
            int v1 = array1[i] & 0xFF;
            int v2 = array2[i] & 0xFF;
            if (v1 == v2) continue;
            return v1 < v2 ? -1 : 1;
        }
        return array1.length - array2.length;
    }

    final int compare(Object[] array1, Object[] array2) {
        int length = Math.min(array1.length, array2.length);
        for (int i = 0; i < length; ++i) {
            int compare = this.compare0(array1[i], array2[i]);
            if (compare == 0) continue;
            return compare;
        }
        return array1.length - array2.length;
    }

    final int compare0(Object object1, Object object2) {
        return object1 == object2 ? 0 : (object1 == null ? -1 : (object2 == null ? 1 : object1.hashCode() - object2.hashCode()));
    }

    @Override
    public final <T> T getValue(Field<T> field2) {
        return this.get(field2);
    }

    @Override
    public final <T> T getValue(Field<?> field2, Class<? extends T> type) {
        return this.get(field2, type);
    }

    @Override
    public final <T, U> U getValue(Field<T> field2, Converter<? super T, ? extends U> converter) {
        return this.get(field2, converter);
    }

    @Override
    public final Object getValue(int index) {
        return this.get(index);
    }

    @Override
    public final <T> T getValue(int index, Class<? extends T> type) {
        return this.get(index, type);
    }

    @Override
    public final <U> U getValue(int index, Converter<?, ? extends U> converter) {
        return this.get(index, converter);
    }

    @Override
    public final Object getValue(String fieldName) {
        return this.get(fieldName);
    }

    @Override
    public final <T> T getValue(String fieldName, Class<? extends T> type) {
        return this.get(fieldName, type);
    }

    @Override
    public final <U> U getValue(String fieldName, Converter<?, ? extends U> converter) {
        return this.get(fieldName, converter);
    }

    @Override
    public final Object getValue(Name fieldName) {
        return this.get(fieldName);
    }

    @Override
    public final <T> T getValue(Name fieldName, Class<? extends T> type) {
        return this.get(fieldName, type);
    }

    @Override
    public final <U> U getValue(Name fieldName, Converter<?, ? extends U> converter) {
        return this.get(fieldName, converter);
    }

    @Override
    public final <T> void setValue(Field<T> field2, T value) {
        this.set(field2, value);
    }

    @Override
    public final <T, U> void setValue(Field<T> field2, U value, Converter<? extends T, ? super U> converter) {
        this.set(field2, value, converter);
    }

    final Result<AbstractRecord> asResult() {
        ResultImpl<AbstractRecord> result = new ResultImpl<AbstractRecord>(this.configuration(), this.fields);
        result.add(this);
        return result;
    }

    class TransferRecordState<R extends Record>
    implements ThrowingFunction<R, R, MappingException> {
        private final Field<?>[] targetFields;

        TransferRecordState(Field<?>[] targetFields) {
            this.targetFields = targetFields;
        }

        @Override
        public R apply(R target) throws MappingException {
            AbstractRecord source2 = AbstractRecord.this;
            try {
                if (target instanceof AbstractRecord) {
                    AbstractRecord t2 = (AbstractRecord)target;
                    for (int targetIndex = 0; targetIndex < (this.targetFields != null ? this.targetFields.length : t2.size()); ++targetIndex) {
                        Field<?> targetField = this.targetFields != null ? this.targetFields[targetIndex] : t2.field(targetIndex);
                        int sourceIndex = AbstractRecord.this.fields.indexOf(targetField);
                        if (sourceIndex < 0) continue;
                        DataType targetType = targetField.getDataType();
                        t2.values[targetIndex] = targetType.convert(AbstractRecord.this.values[sourceIndex]);
                        t2.originals[targetIndex] = targetType.convert(AbstractRecord.this.originals[sourceIndex]);
                        t2.changed.set(targetIndex, AbstractRecord.this.changed.get(sourceIndex));
                    }
                } else {
                    for (Field<?> targetField : target.fields()) {
                        Field<?> sourceField = AbstractRecord.this.field(targetField);
                        if (sourceField == null) continue;
                        Tools.setValue(target, targetField, source2, sourceField);
                    }
                }
                return target;
            }
            catch (Exception e) {
                throw new MappingException("An error occurred when mapping record to " + String.valueOf(target), e);
            }
        }
    }
}

