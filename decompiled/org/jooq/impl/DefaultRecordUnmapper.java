/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.jooq.Configuration;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.RecordType;
import org.jooq.RecordUnmapper;
import org.jooq.exception.MappingException;
import org.jooq.impl.AbstractRecord;
import org.jooq.impl.AbstractRow;
import org.jooq.impl.FieldsImpl;
import org.jooq.impl.Tools;

public class DefaultRecordUnmapper<E, R extends Record>
implements RecordUnmapper<E, R> {
    private final Class<? extends E> type;
    private final RecordType<R> rowType;
    private final AbstractRow<R> row;
    private final Class<? extends AbstractRecord> recordType;
    private final Field<?>[] fields;
    private final Configuration configuration;
    private RecordUnmapper<E, R> delegate;

    public DefaultRecordUnmapper(Class<? extends E> type, RecordType<R> rowType, Configuration configuration) {
        this.type = type;
        this.rowType = rowType;
        this.row = Tools.row0((FieldsImpl)rowType);
        this.recordType = Tools.recordType(rowType.size());
        this.fields = rowType.fields();
        this.configuration = configuration;
        this.init();
    }

    private final void init() {
        this.delegate = this.type.isArray() ? new ArrayUnmapper() : (Map.class.isAssignableFrom(this.type) ? new MapUnmapper() : (Iterable.class.isAssignableFrom(this.type) ? new IterableUnmapper() : new PojoUnmapper()));
    }

    @Override
    public final R unmap(E source2) {
        return this.delegate.unmap(source2);
    }

    private final Record newRecord() {
        return Tools.newRecord(false, this.recordType, this.row, this.configuration).operate(null);
    }

    private static final void setValue(Record record, Object source2, java.lang.reflect.Field member, Field<?> field2) throws IllegalAccessException {
        Class<?> mType = member.getType();
        if (mType.isPrimitive()) {
            if (mType == Byte.TYPE) {
                Tools.setValue(record, field2, member.getByte(source2));
            } else if (mType == Short.TYPE) {
                Tools.setValue(record, field2, member.getShort(source2));
            } else if (mType == Integer.TYPE) {
                Tools.setValue(record, field2, member.getInt(source2));
            } else if (mType == Long.TYPE) {
                Tools.setValue(record, field2, member.getLong(source2));
            } else if (mType == Float.TYPE) {
                Tools.setValue(record, field2, Float.valueOf(member.getFloat(source2)));
            } else if (mType == Double.TYPE) {
                Tools.setValue(record, field2, member.getDouble(source2));
            } else if (mType == Boolean.TYPE) {
                Tools.setValue(record, field2, member.getBoolean(source2));
            } else if (mType == Character.TYPE) {
                Tools.setValue(record, field2, Character.valueOf(member.getChar(source2)));
            }
        } else {
            Tools.setValue(record, field2, member.get(source2));
        }
    }

    private static final String klass(Object o) {
        return o == null ? "null" : o.getClass().toString();
    }

    private final class ArrayUnmapper
    implements RecordUnmapper<E, R> {
        private ArrayUnmapper() {
        }

        @Override
        public final R unmap(E source2) {
            if (source2 instanceof Object[]) {
                Object[] array = (Object[])source2;
                int size = DefaultRecordUnmapper.this.rowType.size();
                AbstractRecord record = (AbstractRecord)DefaultRecordUnmapper.this.newRecord();
                for (int i = 0; i < size && i < array.length; ++i) {
                    Tools.setValue(record, DefaultRecordUnmapper.this.rowType.field(i), i, array[i]);
                }
                return record;
            }
            throw new MappingException("Object[] expected. Got: " + DefaultRecordUnmapper.klass(source2));
        }
    }

    private final class MapUnmapper
    implements RecordUnmapper<E, R> {
        private MapUnmapper() {
        }

        @Override
        public R unmap(E source2) {
            if (source2 instanceof Map) {
                Map map = (Map)source2;
                Record record = DefaultRecordUnmapper.this.newRecord();
                for (int i = 0; i < DefaultRecordUnmapper.this.fields.length; ++i) {
                    String name = DefaultRecordUnmapper.this.fields[i].getName();
                    if (!map.containsKey(name)) continue;
                    Tools.setValue(record, DefaultRecordUnmapper.this.fields[i], map.get(name));
                }
                return record;
            }
            throw new MappingException("Map expected. Got: " + DefaultRecordUnmapper.klass(source2));
        }
    }

    private final class IterableUnmapper
    implements RecordUnmapper<E, R> {
        private IterableUnmapper() {
        }

        @Override
        public final R unmap(E source2) {
            if (source2 instanceof Iterable) {
                Iterable iterable = (Iterable)source2;
                Iterator it = iterable.iterator();
                int size = DefaultRecordUnmapper.this.rowType.size();
                AbstractRecord record = (AbstractRecord)DefaultRecordUnmapper.this.newRecord();
                for (int i = 0; i < size && it.hasNext(); ++i) {
                    Tools.setValue(record, DefaultRecordUnmapper.this.rowType.field(i), i, it.next());
                }
                return record;
            }
            throw new MappingException("Iterable expected. Got: " + DefaultRecordUnmapper.klass(source2));
        }
    }

    private final class PojoUnmapper
    implements RecordUnmapper<E, R> {
        private PojoUnmapper() {
        }

        @Override
        public R unmap(E source2) {
            Record record = DefaultRecordUnmapper.this.newRecord();
            try {
                boolean useAnnotations = Tools.hasColumnAnnotations(DefaultRecordUnmapper.this.configuration, DefaultRecordUnmapper.this.type);
                for (Field<?> field2 : DefaultRecordUnmapper.this.fields) {
                    Method method2;
                    List<java.lang.reflect.Field> members;
                    if (useAnnotations) {
                        members = Tools.getAnnotatedMembers(DefaultRecordUnmapper.this.configuration, DefaultRecordUnmapper.this.type, field2.getName(), true);
                        method2 = Tools.getAnnotatedGetter(DefaultRecordUnmapper.this.configuration, DefaultRecordUnmapper.this.type, field2.getName(), true);
                    } else {
                        members = Tools.getMatchingMembers(DefaultRecordUnmapper.this.configuration, DefaultRecordUnmapper.this.type, field2.getName(), true);
                        method2 = Tools.getMatchingGetter(DefaultRecordUnmapper.this.configuration, DefaultRecordUnmapper.this.type, field2.getName(), true);
                    }
                    if (method2 != null) {
                        Tools.setValue(record, field2, method2.invoke(source2, new Object[0]));
                        continue;
                    }
                    if (members.size() <= 0) continue;
                    DefaultRecordUnmapper.setValue(record, source2, members.get(0), field2);
                }
                return record;
            }
            catch (Exception e) {
                throw new MappingException("An error occurred when mapping record from " + String.valueOf(DefaultRecordUnmapper.this.type), e);
            }
        }
    }
}

