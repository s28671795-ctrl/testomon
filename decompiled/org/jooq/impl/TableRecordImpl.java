/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.DataType;
import org.jooq.Field;
import org.jooq.Fields;
import org.jooq.ForeignKey;
import org.jooq.InsertQuery;
import org.jooq.Internal;
import org.jooq.SQLDialect;
import org.jooq.StoreQuery;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableRecord;
import org.jooq.UpdatableRecord;
import org.jooq.Update;
import org.jooq.conf.Settings;
import org.jooq.conf.SettingsTools;
import org.jooq.conf.WriteIfReadonly;
import org.jooq.exception.DataTypeException;
import org.jooq.impl.AbstractDMLQuery;
import org.jooq.impl.AbstractQualifiedRecord;
import org.jooq.impl.DSL;
import org.jooq.impl.FieldsImpl;
import org.jooq.impl.RecordDelegate;
import org.jooq.impl.Tools;
import org.jooq.tools.JooqLogger;
import org.jooq.tools.StringUtils;

@Internal
public class TableRecordImpl<R extends TableRecord<R>>
extends AbstractQualifiedRecord<R>
implements TableRecord<R> {
    private static final JooqLogger log = JooqLogger.getLogger(TableRecordImpl.class);
    private static final Set<SQLDialect> REFRESH_GENERATED_KEYS = SQLDialect.supportedBy(SQLDialect.DERBY, SQLDialect.H2, SQLDialect.MARIADB, SQLDialect.MEMSQL, SQLDialect.MYSQL, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER);
    private static final Set<SQLDialect> REFRESH_GENERATED_KEYS_ON_UPDATE = SQLDialect.supportedBy(SQLDialect.HSQLDB);
    private static final long[] TRUNCATE = new long[]{1000L, 100L, 10L, 1L};

    public TableRecordImpl(Table<R> table) {
        super(table);
    }

    @Override
    public final Table<R> getTable() {
        return (Table)this.getQualifier();
    }

    @Override
    public final R original() {
        return (R)((TableRecord)super.original());
    }

    @Override
    public final <O extends UpdatableRecord<O>> O fetchParent(ForeignKey<R, O> key) {
        return (O)((UpdatableRecord)key.fetchParent(this));
    }

    @Override
    public final <O extends UpdatableRecord<O>> Table<O> parent(ForeignKey<R, O> key) {
        return key.parent(this);
    }

    @Override
    public final int insert() {
        return this.insert(this.fields.fields.fields);
    }

    @Override
    public final int insert(Field<?> ... storeFields) {
        return this.storeInsert(storeFields);
    }

    @Override
    public final int insert(Collection<? extends Field<?>> storeFields) {
        return this.insert(storeFields.toArray(Tools.EMPTY_FIELD));
    }

    final int storeInsert(Field<?>[] storeFields) {
        int[] result = new int[1];
        RecordDelegate.delegate(this.configuration(), this, RecordDelegate.RecordLifecycleType.INSERT).operate(record -> {
            result[0] = this.storeInsert0(storeFields);
            return record;
        });
        return result[0];
    }

    final int storeInsert0(Field<?>[] storeFields) {
        DSLContext create = this.create();
        InsertQuery<R> insert = create.insertQuery(this.getTable());
        List<Field<?>> changedFields = this.addChangedValues(storeFields, insert, false);
        if (changedFields.isEmpty()) {
            if (Boolean.FALSE.equals(create.settings().isInsertUnchangedRecords())) {
                if (log.isDebugEnabled()) {
                    log.debug((Object)"Query is not executable", insert);
                }
                return 0;
            }
            insert.setDefaultValues();
        }
        BigInteger version = this.addRecordVersion(insert, false);
        Timestamp timestamp = this.addRecordTimestamp(insert, false);
        Collection<Field<?>> key = this.setReturningIfNeeded(insert);
        int result = insert.execute();
        if (result > 0) {
            for (Field<?> changedField : changedFields) {
                this.changed(changedField, false);
            }
            this.setRecordVersionAndTimestamp(version, timestamp);
            this.getReturningIfNeeded(insert, key);
            this.fetched = true;
        }
        return result;
    }

    final void getReturningIfNeeded(StoreQuery<R> query, Collection<Field<?>> key) {
        if (!Tools.isEmpty(key)) {
            Configuration c;
            TableRecord record = (TableRecord)query.getReturnedRecord();
            if (record != null) {
                for (Field<?> field2 : key) {
                    int index = Tools.indexOrFail((Fields)this.fieldsRow(), field2);
                    Object value = record.get(field2);
                    this.values[index] = value;
                    this.originals[index] = value;
                }
            }
            if (SettingsTools.returnAnyNonIdentityOnUpdatableRecord((c = this.configuration()).settings()) && (REFRESH_GENERATED_KEYS.contains((Object)c.dialect()) && record == null || REFRESH_GENERATED_KEYS_ON_UPDATE.contains((Object)c.dialect()) && query instanceof Update || AbstractDMLQuery.EMULATE_RETURNING_WITH_OUTPUT.contains((Object)c.dialect()) && Boolean.FALSE.equals(c.settings().isRenderOutputForSQLServerReturningClause())) && this instanceof UpdatableRecord) {
                ((UpdatableRecord)((Object)this)).refresh(key.toArray(Tools.EMPTY_FIELD));
            }
        }
    }

    final Collection<Field<?>> setReturningIfNeeded(StoreQuery<R> query) {
        Collection<Field<?>> key = null;
        if (this.configuration() != null && SettingsTools.returnAnyOnUpdatableRecord(this.configuration().settings()) && !Tools.isEmpty(key = this.getReturning(query))) {
            query.setReturning(key);
        }
        return key;
    }

    final void setRecordVersionAndTimestamp(BigInteger version, Timestamp timestamp) {
        Object value;
        int fieldIndex;
        TableField<R, ?> field2;
        if (version != null) {
            field2 = this.getTable().getRecordVersion();
            fieldIndex = Tools.indexOrFail((Fields)this.fields, field2);
            value = field2.getDataType().convert((Object)version);
            this.values[fieldIndex] = value;
            this.originals[fieldIndex] = value;
            this.changed.clear(fieldIndex);
        }
        if (timestamp != null) {
            field2 = this.getTable().getRecordTimestamp();
            fieldIndex = Tools.indexOrFail((Fields)this.fields, field2);
            value = field2.getDataType().convert((Object)timestamp);
            this.values[fieldIndex] = value;
            this.originals[fieldIndex] = value;
            this.changed.clear(fieldIndex);
        }
    }

    final List<Field<?>> addChangedValues(Field<?>[] storeFields, StoreQuery<R> query, boolean forUpdate) {
        FieldsImpl f = new FieldsImpl(storeFields);
        ArrayList result = new ArrayList();
        for (Field<?> field2 : this.fields.fields.fields) {
            if (!this.changed(field2) || f.field(field2) == null || !this.writable(field2, forUpdate)) continue;
            this.addValue(query, field2, forUpdate);
            result.add(field2);
        }
        return result;
    }

    final boolean writable(Field<?> field2, boolean forUpdate) {
        Settings s2 = Tools.settings(this);
        WriteIfReadonly w = forUpdate ? StringUtils.defaultIfNull(s2.getReadonlyUpdatableRecordUpdate(), WriteIfReadonly.IGNORE) : StringUtils.defaultIfNull(s2.getReadonlyTableRecordInsert(), WriteIfReadonly.IGNORE);
        switch (w) {
            case IGNORE: {
                return !field2.getDataType().readonly();
            }
            case THROW: {
                if (!field2.getDataType().readonly()) break;
                throw new DataTypeException("Cannot write to field: " + String.valueOf(field2));
            }
        }
        return true;
    }

    final <T> void addValue(StoreQuery<?> store, Field<T> field2, Object value, boolean forUpdate) {
        store.addValue(field2, Tools.field(value, field2));
        if (forUpdate) {
            ((InsertQuery)store).addValueForUpdate(field2, DSL.excluded(field2));
        }
    }

    final <T> void addValue(StoreQuery<?> store, Field<T> field2, boolean forUpdate) {
        this.addValue(store, field2, this.get(field2), forUpdate);
    }

    final Timestamp addRecordTimestamp(StoreQuery<?> store, boolean forUpdate) {
        Timestamp result = null;
        TableField<R, ?> timestamp = this.getTable().getRecordTimestamp();
        if (timestamp != null && this.isUpdateRecordTimestamp()) {
            result = new Timestamp(this.configuration().clock().millis());
            result = TableRecordImpl.truncate(result, timestamp.getDataType());
            this.addValue(store, timestamp, result, forUpdate);
        }
        return result;
    }

    private static final Timestamp truncate(Timestamp ts, DataType<?> type) {
        if (type.isDate()) {
            return new Timestamp(ts.getYear(), ts.getMonth(), ts.getDate(), 0, 0, 0, 0);
        }
        if (!type.precisionDefined() || type.precision() >= 3) {
            return ts;
        }
        return new Timestamp(ts.getTime() / TRUNCATE[type.precision()] * TRUNCATE[type.precision()]);
    }

    final BigInteger addRecordVersion(StoreQuery<?> store, boolean forUpdate) {
        BigInteger result = null;
        TableField<R, ?> version = this.getTable().getRecordVersion();
        if (version != null && this.isUpdateRecordVersion()) {
            Object value = this.get(version);
            result = value == null ? BigInteger.ONE : new BigInteger(value.toString()).add(BigInteger.ONE);
            this.addValue(store, version, result, forUpdate);
        }
        return result;
    }

    final Object getRecordVersion() {
        TableField<R, ?> field2 = this.getTable().getRecordVersion();
        return field2 != null ? this.get(field2) : null;
    }

    final Object getRecordTimestamp() {
        TableField<R, ?> field2 = this.getTable().getRecordTimestamp();
        return field2 != null ? this.get(field2) : null;
    }

    final boolean isUpdateRecordVersion() {
        Configuration configuration = this.configuration();
        return configuration != null ? !Boolean.FALSE.equals(configuration.settings().isUpdateRecordVersion()) : true;
    }

    final boolean isUpdateRecordTimestamp() {
        Configuration configuration = this.configuration();
        return configuration != null ? !Boolean.FALSE.equals(configuration.settings().isUpdateRecordTimestamp()) : true;
    }

    final boolean isTimestampOrVersionAvailable() {
        return this.getTable().getRecordTimestamp() != null && this.isUpdateRecordTimestamp() || this.getTable().getRecordVersion() != null && this.isUpdateRecordVersion();
    }

    final Collection<Field<?>> getReturning(StoreQuery<R> query) {
        Settings s2 = this.configuration().settings();
        if (Boolean.TRUE.equals(s2.isReturnAllOnUpdatableRecord())) {
            return Arrays.asList(this.fields());
        }
        LinkedHashSet result = new LinkedHashSet();
        if (!Boolean.FALSE.equals(s2.isReturnIdentityOnUpdatableRecord()) && (query instanceof InsertQuery || SettingsTools.updatablePrimaryKeys(s2))) {
            Tools.let(this.getTable().getIdentity(), i -> result.add(i.getField()));
            Tools.let(this.getPrimaryKey(), k -> result.addAll(k.getFields()));
        }
        if (Boolean.TRUE.equals(s2.isReturnDefaultOnUpdatableRecord())) {
            for (Field<?> f : this.fields()) {
                if (!f.getDataType().defaulted()) continue;
                result.add(f);
            }
        }
        if (Boolean.TRUE.equals(s2.isReturnComputedOnUpdatableRecord())) {
            for (Field<?> f : this.fields()) {
                if (!f.getDataType().computed()) continue;
                result.add(f);
            }
        }
        return result;
    }
}

