/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.database;

import com.incoresoft.middleware.model.Model;
import com.incoresoft.plugin.commons.database.CRUDConverter;
import com.incoresoft.plugin.commons.params.PaginationParams;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.OrderField;
import org.jooq.SelectForUpdateStep;
import org.jooq.SelectLimitPercentStep;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableLike;
import org.jooq.UpdatableRecord;
import org.jooq.impl.DSL;

public abstract class CRUDRepository<R extends UpdatableRecord<R>, M extends Model> {
    private static final int DEFAULT_LIMIT = Integer.MAX_VALUE;
    protected final DSLContext context;
    protected final CRUDConverter<R, M> converter;
    protected final Table<R> table;
    protected final TableField<R, Integer> idField;
    protected final TableField<R, String> nameField;
    protected final TableField<R, Integer> clientIdField;

    protected CRUDRepository(@NotNull DSLContext dslContext, @NotNull CRUDConverter<R, M> converter, @NotNull Table<R> table, @NotNull TableField<R, Integer> idField, @NotNull TableField<R, String> nameField, @NotNull TableField<R, Integer> clientIdField) {
        this.context = Objects.requireNonNull(dslContext);
        this.converter = Objects.requireNonNull(converter);
        this.table = Objects.requireNonNull(table);
        this.idField = Objects.requireNonNull(idField);
        this.nameField = Objects.requireNonNull(nameField);
        this.clientIdField = Objects.requireNonNull(clientIdField);
    }

    public List<M> getAll(Integer clientId) {
        Condition[] conditionArray = new Condition[1];
        conditionArray[0] = Optional.ofNullable(clientId).map(this.clientIdField::eq).orElseGet(DSL::noCondition);
        return this.getAll(conditionArray);
    }

    public List<M> getAll(@NotNull Set<String> exactNames) {
        return this.getAll(this.nameField.in((Collection<?>)exactNames));
    }

    public List<M> getAll(String exactName, Integer clientId) {
        return this.getAll(this.nameField.equalIgnoreCase(exactName), this.clientIdField.eq(clientId));
    }

    public List<M> getAll(Condition ... conditions) {
        return this.getAll(List.of(conditions), new OrderField[0]);
    }

    public List<M> getAll(DSLContext transaction, Condition ... conditions) {
        return this.getAll(transaction, List.of(conditions), new OrderField[0]);
    }

    public List<M> getAll(Collection<Condition> conditions, OrderField<?> ... sortFields) {
        return this.getAll(this.context, conditions, sortFields);
    }

    public List<M> getAll(DSLContext context, Collection<Condition> conditions, OrderField<?> ... sortFields) {
        PaginationParams paginationParams = new PaginationParams();
        paginationParams.setOffset(0);
        paginationParams.setLimit(Integer.MAX_VALUE);
        return this.getAll(context, conditions, paginationParams, sortFields);
    }

    public List<M> getAll(Collection<Condition> conditions, PaginationParams paginationParams, OrderField<?> ... sortFields) {
        return this.getAll(this.context, conditions, paginationParams, sortFields);
    }

    public List<M> getAll(DSLContext dslContext, Collection<Condition> conditions, PaginationParams paginationParams, OrderField<?> ... sortFields) {
        SelectForUpdateStep query = dslContext.selectFrom(this.table).where(conditions).orderBy(sortFields).limit(paginationParams.getLimit().orElse(Integer.MAX_VALUE)).offset(paginationParams.getOffset());
        try (Stream<M> stream = query.stream();){
            List<Model> list = stream.map(this.converter).filter(Objects::nonNull).toList();
            return list;
        }
    }

    @Deprecated(forRemoval=true)
    @NotNull
    public List<String> retrieveNames(Pattern pattern) {
        return this.context.select(this.nameField).from((TableLike<?>)this.table).where(this.nameField.likeRegex(pattern.pattern())).fetch(this.nameField);
    }

    public boolean exists(int id) {
        SelectLimitPercentStep query = this.context.select(this.idField).from((TableLike<?>)this.table).where(this.idField.eq(id)).limit(1);
        try (Stream stream = query.stream();){
            boolean bl = stream.findFirst().isPresent();
            return bl;
        }
    }

    public Optional<M> get(int id) {
        return this.get(id, this.context);
    }

    public Optional<M> get(int id, DSLContext context) {
        return context.selectFrom(this.table).where(this.idField.eq(id)).fetchOptional(this.converter);
    }

    public M add(M model) {
        return this.add(model, this.context);
    }

    public M add(M model, DSLContext context) {
        R databaseRecord = this.createNewDatabaseRecord(model, context);
        databaseRecord.insert();
        return (M)((Model)this.converter.from(databaseRecord));
    }

    public void addAll(Collection<M> models) {
        List<UpdatableRecord> recordsToInsert = models.stream().map(this::createNewDatabaseRecord).toList();
        this.context.batchInsert(recordsToInsert).execute();
    }

    @NotNull
    private R createNewDatabaseRecord(M model) {
        return this.createNewDatabaseRecord(model, this.context);
    }

    @NotNull
    private R createNewDatabaseRecord(M model, DSLContext context) {
        UpdatableRecord databaseRecord = (UpdatableRecord)context.newRecord(this.table, this.converter.to(model));
        databaseRecord.reset(this.idField);
        return (R)databaseRecord;
    }

    public M update(M model) {
        return this.update(model, this.context);
    }

    public M update(M model, DSLContext context) {
        UpdatableRecord databaseRecord = (UpdatableRecord)context.newRecord(this.table, this.converter.to(model));
        databaseRecord.changed(this.idField, false);
        databaseRecord.update();
        return (M)((Model)this.converter.from(databaseRecord));
    }

    public void delete(int id) {
        this.delete(id, this.context);
    }

    public void delete(int id, DSLContext context) {
        context.deleteFrom(this.table).where(this.idField.eq(id)).execute();
    }

    public void deleteAll(Collection<Integer> ids) {
        this.deleteAll(ids, this.context);
    }

    public void deleteAll(Collection<Integer> ids, DSLContext transaction) {
        this.context.deleteFrom(this.table).where(this.idField.in(ids)).execute();
    }
}

