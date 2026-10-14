/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import org.jooq.Condition;
import org.jooq.Configuration;
import org.jooq.DAO;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.RecordContext;
import org.jooq.RecordListener;
import org.jooq.RecordListenerProvider;
import org.jooq.RecordMapper;
import org.jooq.SQLDialect;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableLike;
import org.jooq.UniqueKey;
import org.jooq.UpdatableRecord;
import org.jooq.conf.Settings;
import org.jooq.conf.SettingsTools;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultRecordListenerProvider;
import org.jooq.impl.FieldsImpl;
import org.jooq.impl.Tools;

public abstract class DAOImpl<R extends UpdatableRecord<R>, P, T>
implements DAO<R, P, T> {
    private final Table<R> table;
    private final Class<P> type;
    private RecordMapper<R, P> mapper;
    private Configuration configuration;

    protected DAOImpl(Table<R> table, Class<P> type) {
        this(table, type, null);
    }

    protected DAOImpl(Table<R> table, Class<P> type, Configuration configuration) {
        this.table = table;
        this.type = type;
        this.setConfiguration(configuration);
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = Tools.configuration(configuration);
        this.mapper = ((FieldsImpl)this.table.recordType()).mapper(this.configuration, this.type);
    }

    public DSLContext ctx() {
        return this.configuration().dsl();
    }

    @Override
    public Configuration configuration() {
        return this.configuration;
    }

    @Override
    public Settings settings() {
        return Tools.settings(this.configuration());
    }

    @Override
    public SQLDialect dialect() {
        return Tools.configuration(this.configuration()).dialect();
    }

    @Override
    public SQLDialect family() {
        return this.dialect().family();
    }

    @Override
    public RecordMapper<R, P> mapper() {
        return this.mapper;
    }

    @Override
    public void insert(P object) {
        this.insert((Collection<P>)Collections.singletonList(object));
    }

    @Override
    public void insert(P ... objects) {
        this.insert((Collection<P>)Arrays.asList(objects));
    }

    @Override
    public void insert(Collection<P> objects) {
        if (objects.size() > 1) {
            if (!Boolean.FALSE.equals(this.settings().isReturnRecordToPojo())) {
                for (UpdatableRecord record : this.records(objects, false)) {
                    record.insert();
                }
            } else {
                this.ctx().batchInsert(this.records(objects, false)).execute();
            }
        } else if (objects.size() == 1) {
            ((UpdatableRecord)this.records(objects, false).get(0)).insert();
        }
    }

    @Override
    public void update(P object) {
        this.update((Collection<P>)Collections.singletonList(object));
    }

    @Override
    public void update(P ... objects) {
        this.update((Collection<P>)Arrays.asList(objects));
    }

    @Override
    public void update(Collection<P> objects) {
        if (objects.size() > 1) {
            if (this.returnAnyOnUpdatableRecord()) {
                for (UpdatableRecord record : this.records(objects, true)) {
                    record.update();
                }
            } else {
                this.ctx().batchUpdate(this.records(objects, true)).execute();
            }
        } else if (objects.size() == 1) {
            ((UpdatableRecord)this.records(objects, true).get(0)).update();
        }
    }

    @Override
    public void merge(P object) {
        this.merge((Collection<P>)Collections.singletonList(object));
    }

    @Override
    public void merge(P ... objects) {
        this.merge((Collection<P>)Arrays.asList(objects));
    }

    @Override
    public void merge(Collection<P> objects) {
        if (objects.size() > 1) {
            if (this.returnAnyOnUpdatableRecord()) {
                for (UpdatableRecord record : this.records(objects, false)) {
                    record.merge();
                }
            } else {
                this.ctx().batchMerge(this.records(objects, false)).execute();
            }
        } else if (objects.size() == 1) {
            ((UpdatableRecord)this.records(objects, false).get(0)).merge();
        }
    }

    @Override
    public void delete(P object) {
        this.delete((Collection<P>)Collections.singletonList(object));
    }

    @Override
    public void delete(P ... objects) {
        this.delete((Collection<P>)Arrays.asList(objects));
    }

    @Override
    public void delete(Collection<P> objects) {
        if (objects.size() > 1) {
            if (this.returnAnyOnUpdatableRecord()) {
                for (UpdatableRecord record : this.records(objects, true)) {
                    record.delete();
                }
            } else {
                this.ctx().batchDelete(this.records(objects, true)).execute();
            }
        } else if (objects.size() == 1) {
            ((UpdatableRecord)this.records(objects, true).get(0)).delete();
        }
    }

    @Override
    public void deleteById(T ids) {
        this.deleteById((Collection<T>)Collections.singletonList(ids));
    }

    @Override
    public void deleteById(T ... ids) {
        this.deleteById((Collection<T>)Arrays.asList(ids));
    }

    @Override
    public void deleteById(Collection<T> ids) {
        Field<?>[] pk = this.pk();
        if (pk != null) {
            this.ctx().delete(this.table).where(this.equal(pk, ids)).execute();
        }
    }

    @Override
    public boolean exists(P object) {
        return this.existsById(this.getId(object));
    }

    @Override
    public boolean existsById(T id) {
        Field<?>[] pk = this.pk();
        if (pk != null) {
            return this.ctx().selectCount().from((TableLike<?>)this.table).where(this.equal(pk, id)).fetchOne(0, Integer.class) > 0;
        }
        return false;
    }

    @Override
    public long count() {
        return this.ctx().selectCount().from((TableLike<?>)this.table).fetchOne(0, Long.class);
    }

    @Override
    public List<P> findAll() {
        return this.ctx().selectFrom(this.table).fetch(this.mapper());
    }

    @Override
    public P findById(T id) {
        Field<?>[] pk = this.pk();
        if (pk != null) {
            return this.ctx().selectFrom(this.table).where(this.equal(pk, id)).fetchOne(this.mapper());
        }
        return null;
    }

    @Override
    public Optional<P> findOptionalById(T id) {
        return Optional.ofNullable(this.findById(id));
    }

    @Override
    public <Z> List<P> fetchRange(Field<Z> field2, Z lowerInclusive, Z upperInclusive) {
        return this.ctx().selectFrom(this.table).where(lowerInclusive == null ? (upperInclusive == null ? DSL.noCondition() : field2.le(upperInclusive)) : (upperInclusive == null ? field2.ge(lowerInclusive) : field2.between(lowerInclusive, upperInclusive))).fetch(this.mapper());
    }

    @Override
    public <Z> List<P> fetch(Field<Z> field2, Z ... values2) {
        return this.fetch(field2, (Collection<? extends Z>)Arrays.asList(values2));
    }

    @Override
    public <Z> List<P> fetch(Field<Z> field2, Collection<? extends Z> values2) {
        return this.ctx().selectFrom(this.table).where(field2.in(values2)).fetch(this.mapper());
    }

    @Override
    public <Z> P fetchOne(Field<Z> field2, Z value) {
        return this.ctx().selectFrom(this.table).where(field2.equal(value)).fetchOne(this.mapper());
    }

    @Override
    public <Z> Optional<P> fetchOptional(Field<Z> field2, Z value) {
        return Optional.ofNullable(this.fetchOne(field2, value));
    }

    @Override
    public Table<R> getTable() {
        return this.table;
    }

    @Override
    public Class<P> getType() {
        return this.type;
    }

    protected T compositeKeyRecord(Object ... values2) {
        UniqueKey<R> key = this.table.getPrimaryKey();
        if (key == null) {
            return null;
        }
        TableField<R, ?>[] fields = key.getFieldsArray();
        Record result = this.configuration().dsl().newRecord(fields);
        for (int i = 0; i < values2.length; ++i) {
            result.set(fields[i], fields[i].getDataType().convert(values2[i]));
        }
        return (T)result;
    }

    private Condition equal(Field<?>[] pk, T id) {
        if (pk.length == 1) {
            return pk[0].equal(pk[0].getDataType().convert((Object)id));
        }
        return DSL.row(pk).equal((Record)id);
    }

    private Condition equal(Field<?>[] pk, Collection<T> ids) {
        if (pk.length == 1) {
            if (ids.size() == 1) {
                return this.equal(pk, ids.iterator().next());
            }
            return pk[0].in(pk[0].getDataType().convert(ids));
        }
        return DSL.row(pk).in(ids.toArray(Tools.EMPTY_RECORD));
    }

    private Field<?>[] pk() {
        UniqueKey<R> key = this.table.getPrimaryKey();
        return key == null ? null : key.getFieldsArray();
    }

    private List<R> records(Collection<P> objects, boolean forUpdate) {
        DSLContext ctx;
        IdentityHashMap mapping;
        ArrayList<UpdatableRecord> result = new ArrayList<UpdatableRecord>(objects.size());
        Field<?>[] pk = this.pk();
        IdentityHashMap identityHashMap = mapping = !Boolean.FALSE.equals(this.settings().isReturnRecordToPojo()) ? new IdentityHashMap() : null;
        if (mapping != null) {
            Consumer<RecordContext> end = c -> {
                Record record = c.record();
                if (record != null) {
                    record.into(mapping.get(record));
                }
            };
            ctx = this.configuration().deriveAppending(RecordListener.onStoreEnd(end).onInsertEnd(end).onUpdateEnd(end).onDeleteEnd(end)).dsl();
        } else {
            ctx = this.ctx();
        }
        for (P object : objects) {
            UpdatableRecord record = (UpdatableRecord)ctx.newRecord(this.table, object);
            if (mapping != null) {
                mapping.put(record, object);
            }
            if (forUpdate && pk != null) {
                for (Field<?> field2 : pk) {
                    record.changed(field2, false);
                }
            }
            Tools.resetChangedOnNotNull(record);
            result.add(record);
        }
        return result;
    }

    private RecordListenerProvider[] providers(RecordListenerProvider[] providers, IdentityHashMap<R, Object> mapping) {
        RecordListenerProvider[] result = Arrays.copyOf(providers, providers.length + 1);
        Consumer<RecordContext> end = ctx -> {
            Record record = ctx.record();
            if (record != null) {
                record.into(mapping.get(record));
            }
        };
        result[providers.length] = new DefaultRecordListenerProvider(RecordListener.onStoreEnd(end).onInsertEnd(end).onUpdateEnd(end).onDeleteEnd(end));
        return result;
    }

    private final boolean returnAnyOnUpdatableRecord() {
        return !Boolean.FALSE.equals(this.settings().isReturnRecordToPojo()) && SettingsTools.returnAnyOnUpdatableRecord(this.settings());
    }
}

