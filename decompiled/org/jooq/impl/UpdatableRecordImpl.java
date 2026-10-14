/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import org.jooq.ConditionProvider;
import org.jooq.Configuration;
import org.jooq.DeleteQuery;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.InsertQuery;
import org.jooq.Internal;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.SelectQuery;
import org.jooq.StoreQuery;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableLike;
import org.jooq.TableRecord;
import org.jooq.UniqueKey;
import org.jooq.UpdatableRecord;
import org.jooq.conf.SettingsTools;
import org.jooq.conf.UpdateUnchangedRecords;
import org.jooq.exception.DataChangedException;
import org.jooq.exception.NoDataFoundException;
import org.jooq.impl.AbstractRecord;
import org.jooq.impl.RecordDelegate;
import org.jooq.impl.TableRecordImpl;
import org.jooq.impl.Tools;
import org.jooq.tools.JooqLogger;
import org.jooq.tools.StringUtils;

@Internal
public class UpdatableRecordImpl<R extends UpdatableRecord<R>>
extends TableRecordImpl<R>
implements UpdatableRecord<R> {
    private static final JooqLogger log = JooqLogger.getLogger(UpdatableRecordImpl.class);
    private static final Set<SQLDialect> NO_SUPPORT_FOR_UPDATE = SQLDialect.supportedBy(SQLDialect.ACCESS, SQLDialect.SQLITE);
    private static final Set<SQLDialect> NO_SUPPORT_MERGE_RETURNING = SQLDialect.supportedBy(SQLDialect.ACCESS, SQLDialect.ASE, SQLDialect.BIGQUERY, SQLDialect.DB2, SQLDialect.DERBY, SQLDialect.IGNITE, SQLDialect.SNOWFLAKE, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SYBASE, SQLDialect.TERADATA, SQLDialect.VERTICA);

    public UpdatableRecordImpl(Table<R> table) {
        super(table);
    }

    @Override
    public Record key() {
        AbstractRecord result = Tools.newRecord(this.fetched, AbstractRecord.class, Tools.row0(this.getPrimaryKey().getFieldsArray())).operate(null);
        result.setValues(result.fields.fields.fields, this);
        return result;
    }

    @Override
    public final <O extends TableRecord<O>> O fetchChild(ForeignKey<O, R> key) {
        return (O)((TableRecord)Tools.filterOne(this.fetchChildren(key)));
    }

    @Override
    public final <O extends TableRecord<O>> Result<O> fetchChildren(ForeignKey<O, R> key) {
        return key.fetchChildren((R)this);
    }

    @Override
    public final <O extends TableRecord<O>> Table<O> children(ForeignKey<O, R> key) {
        return key.children((R)this);
    }

    final UniqueKey<R> getPrimaryKey() {
        return this.getTable().getPrimaryKey();
    }

    @Override
    public final int store() {
        return this.store(this.fields.fields.fields);
    }

    @Override
    public final int store(Field<?> ... storeFields) {
        int[] result = new int[1];
        RecordDelegate.delegate(this.configuration(), this, RecordDelegate.RecordLifecycleType.STORE).operate(record -> {
            result[0] = this.store0(storeFields);
            return record;
        });
        return result[0];
    }

    @Override
    public final int store(Collection<? extends Field<?>> storeFields) {
        return this.store(storeFields.toArray(Tools.EMPTY_FIELD));
    }

    @Override
    public final int update() {
        return this.update(this.fields.fields.fields);
    }

    @Override
    public int update(Field<?> ... storeFields) {
        return this.storeUpdate(storeFields, this.getPrimaryKey().getFieldsArray());
    }

    @Override
    public final int update(Collection<? extends Field<?>> storeFields) {
        return this.update(storeFields.toArray(Tools.EMPTY_FIELD));
    }

    @Override
    public final int merge() {
        return this.merge(this.fields.fields.fields);
    }

    @Override
    public int merge(Field<?> ... storeFields) {
        return this.storeMerge(storeFields, this.getPrimaryKey().getFieldsArray());
    }

    @Override
    public final int merge(Collection<? extends Field<?>> storeFields) {
        return this.merge(storeFields.toArray(Tools.EMPTY_FIELD));
    }

    private final int store0(Field<?>[] storeFields) {
        TableField<R, ?>[] keys2 = this.getPrimaryKey().getFieldsArray();
        boolean executeUpdate = false;
        if (SettingsTools.updatablePrimaryKeys(Tools.settings(this))) {
            executeUpdate = this.fetched;
        } else {
            for (TableField field2 : keys2) {
                if (this.changed(field2) || !field2.getDataType().nullable() && this.get(field2) == null) {
                    executeUpdate = false;
                    break;
                }
                executeUpdate = true;
            }
        }
        int result = 0;
        result = executeUpdate ? this.storeUpdate(storeFields, keys2) : this.storeInsert(storeFields);
        return result;
    }

    private final int storeUpdate(Field<?>[] storeFields, TableField<R, ?>[] keys2) {
        int[] result = new int[1];
        RecordDelegate.delegate(this.configuration(), this, RecordDelegate.RecordLifecycleType.UPDATE).operate(record -> {
            result[0] = this.storeUpdate0(storeFields, keys2);
            return record;
        });
        return result[0];
    }

    private final int storeUpdate0(Field<?>[] storeFields, TableField<R, ?>[] keys2) {
        return this.storeMergeOrUpdate0(storeFields, keys2, (StoreQuery<R> & ConditionProvider)this.create().updateQuery(this.getTable()), false);
    }

    private final int storeMerge(Field<?>[] storeFields, TableField<R, ?>[] keys2) {
        int[] result = new int[1];
        RecordDelegate.delegate(this.configuration(), this, RecordDelegate.RecordLifecycleType.MERGE).operate(record -> {
            result[0] = this.storeMerge0(storeFields, keys2);
            return record;
        });
        return Math.min(result[0], 1);
    }

    private final int storeMerge0(Field<?>[] storeFields, TableField<R, ?>[] keys2) {
        if (this.lockingActive()) {
            if (this.lockValuePresent()) {
                return this.storeUpdate0(storeFields, keys2);
            }
            return this.storeInsert0(storeFields);
        }
        InsertQuery merge = this.create().insertQuery(this.getTable());
        merge.onDuplicateKeyUpdate(true);
        return this.storeMergeOrUpdate0(storeFields, keys2, (StoreQuery<R> & ConditionProvider)merge, true);
    }

    private final boolean lockingActive() {
        return this.isExecuteWithOptimisticLocking() && (this.isTimestampOrVersionAvailable() || this.isExecuteWithOptimisticLockingIncludeUnversioned());
    }

    private final boolean lockValuePresent() {
        return this.getRecordVersion() != null || this.getRecordTimestamp() != null || this.getTable().getRecordVersion() == null && this.getTable().getRecordTimestamp() == null && this.fetched;
    }

    private final <Q extends StoreQuery<R> & ConditionProvider> int storeMergeOrUpdate0(Field<?>[] storeFields, TableField<R, ?>[] keys2, Q query, boolean merge) {
        List<Field<?>> changedFields = this.addChangedValues(storeFields, query, merge);
        if (!merge) {
            Tools.addConditions(query, this, keys2);
        }
        if (changedFields.isEmpty()) {
            switch (StringUtils.defaultIfNull(this.create().settings().getUpdateUnchangedRecords(), UpdateUnchangedRecords.NEVER)) {
                case NEVER: {
                    if (log.isDebugEnabled()) {
                        log.debug((Object)"Query is not executable", query);
                    }
                    return 0;
                }
                case SET_PRIMARY_KEY_TO_ITSELF: {
                    for (Field<Object> field2 : keys2) {
                        query.addValue(field2, field2);
                    }
                    break;
                }
                case SET_NON_PRIMARY_KEY_TO_THEMSELVES: {
                    for (Field<Object> field3 : storeFields) {
                        if (Arrays.asList(keys2).contains(field3)) continue;
                        query.addValue(field3, field3);
                    }
                    break;
                }
                case SET_NON_PRIMARY_KEY_TO_RECORD_VALUES: {
                    for (Field<Object> field4 : storeFields) {
                        if (Arrays.asList(keys2).contains(field4)) continue;
                        this.changed(field4, true);
                    }
                    this.addChangedValues(storeFields, query, merge);
                }
            }
        }
        BigInteger version = this.addRecordVersion(query, merge);
        Timestamp timestamp = this.addRecordTimestamp(query, merge);
        if (this.isExecuteWithOptimisticLocking()) {
            if (this.isTimestampOrVersionAvailable()) {
                this.addConditionForVersionAndTimestamp(query);
            } else if (this.isExecuteWithOptimisticLockingIncludeUnversioned()) {
                this.checkIfChanged(keys2);
            }
        }
        Collection<Field<?>> key = merge && NO_SUPPORT_MERGE_RETURNING.contains((Object)this.create().dialect()) ? null : this.setReturningIfNeeded(query);
        int n = query.execute();
        this.checkIfChanged(n, version, timestamp);
        if (n > 0) {
            for (Field<?> changedField : changedFields) {
                this.changed(changedField, false);
            }
            this.getReturningIfNeeded(query, key);
        }
        return n;
    }

    @Override
    public final int delete() {
        int[] result = new int[1];
        RecordDelegate.delegate(this.configuration(), this, RecordDelegate.RecordLifecycleType.DELETE).operate(record -> {
            result[0] = this.delete0();
            return record;
        });
        return result[0];
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private final int delete0() {
        TableField<R, ?>[] keys2 = this.getPrimaryKey().getFieldsArray();
        try {
            DeleteQuery delete1 = this.create().deleteQuery(this.getTable());
            Tools.addConditions(delete1, this, keys2);
            if (this.isExecuteWithOptimisticLocking()) {
                if (this.isTimestampOrVersionAvailable()) {
                    this.addConditionForVersionAndTimestamp(delete1);
                } else if (this.isExecuteWithOptimisticLockingIncludeUnversioned()) {
                    this.checkIfChanged(keys2);
                }
            }
            int result = delete1.execute();
            this.checkIfChanged(result, null, null);
            int n = result;
            return n;
        }
        finally {
            this.changed(true);
            this.fetched = false;
        }
    }

    @Override
    public final void refresh() {
        this.refresh(this.fields.fields.fields);
    }

    @Override
    public final void refresh(Field<?> ... refreshFields) {
        SelectQuery<Record> select = this.create().selectQuery();
        select.addSelect(refreshFields);
        select.addFrom((TableLike<?>)this.getTable());
        Tools.addConditions(select, this, this.getPrimaryKey().getFieldsArray());
        if (select.execute() != 1) {
            throw new NoDataFoundException("Exactly one row expected for refresh. Record does not exist in database.");
        }
        AbstractRecord source2 = (AbstractRecord)select.getResult().get(0);
        RecordDelegate.delegate(this.configuration(), this, RecordDelegate.RecordLifecycleType.REFRESH).operate(record -> {
            this.setValues(refreshFields, source2);
            return record;
        });
    }

    @Override
    public final void refresh(Collection<? extends Field<?>> refreshFields) {
        this.refresh(refreshFields.toArray(Tools.EMPTY_FIELD));
    }

    @Override
    public final R copy() {
        return (R)((UpdatableRecord)Tools.newRecord(false, this.getTable(), this.configuration()).operate(copy -> {
            List key = this.getPrimaryKey().getFields();
            for (Field<?> field2 : this.fields.fields.fields) {
                if (key.contains(field2)) continue;
                copy.set(field2, this.get(field2));
            }
            return copy;
        }));
    }

    private final boolean isExecuteWithOptimisticLocking() {
        Configuration configuration = this.configuration();
        return configuration != null && Boolean.TRUE.equals(configuration.settings().isExecuteWithOptimisticLocking());
    }

    private final boolean isExecuteWithOptimisticLockingIncludeUnversioned() {
        Configuration configuration = this.configuration();
        return configuration == null || !Boolean.TRUE.equals(configuration.settings().isExecuteWithOptimisticLockingExcludeUnversioned());
    }

    private final void addConditionForVersionAndTimestamp(ConditionProvider query) {
        TableField v = this.getTable().getRecordVersion();
        TableField t2 = this.getTable().getRecordTimestamp();
        if (v != null) {
            Tools.addCondition(query, this, v);
        }
        if (t2 != null) {
            Tools.addCondition(query, this, t2);
        }
    }

    private final void checkIfChanged(TableField<R, ?>[] keys2) {
        UpdatableRecord record;
        SelectQuery select = this.create().selectQuery(this.getTable());
        Tools.addConditions(select, this, keys2);
        if (!NO_SUPPORT_FOR_UPDATE.contains((Object)this.create().dialect())) {
            select.setForUpdate(true);
        }
        if ((record = (UpdatableRecord)select.fetchOne()) == null) {
            throw new DataChangedException("Database record no longer exists");
        }
        for (Field<?> field2 : this.fields.fields.fields) {
            Object thatObject;
            Object thisObject = this.original(field2);
            if (StringUtils.equals(thisObject, thatObject = record.original(field2))) continue;
            if (thisObject == null && !this.fetched) {
                throw new DataChangedException("Cannot detect whether unversioned record has been changed. Either make sure the record is fetched from the database, or use a version or timestamp column to version the record.");
            }
            throw new DataChangedException("Database record has been changed");
        }
    }

    private final void checkIfChanged(int result, BigInteger version, Timestamp timestamp) {
        if (result > 0) {
            this.setRecordVersionAndTimestamp(version, timestamp);
        } else if (this.isExecuteWithOptimisticLocking()) {
            throw new DataChangedException("Database record has been changed or doesn't exist any longer");
        }
    }
}

