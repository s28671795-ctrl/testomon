/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.items;

import com.incoresoft.middleware.model.Model;
import com.incoresoft.middleware.model.user.User;
import com.incoresoft.plugin.commons.database.CRUDRepository;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.jooq.Condition;
import org.jooq.DSLContext;

public abstract class BaseItemManager<M extends Model> {
    protected final CRUDRepository<?, M> repository;

    protected BaseItemManager(@NotNull CRUDRepository<?, M> repository) {
        this.repository = Objects.requireNonNull(repository);
    }

    public List<M> getAll() {
        return this.repository.getAll(new Condition[0]);
    }

    public List<M> getAll(User user) {
        return this.repository.getAll(user.isRoot() ? null : Integer.valueOf(user.getClientId()));
    }

    public List<M> getAll(String exactName, Integer clientId) {
        return this.repository.getAll(exactName, clientId);
    }

    public Optional<M> get(int id) {
        return this.repository.get(id);
    }

    public M add(M item) {
        return this.repository.add(item);
    }

    public void addAll(List<M> items) {
        this.repository.addAll(items);
    }

    public M update(M item) {
        return this.repository.update(item);
    }

    public M update(M item, DSLContext transaction) {
        return this.repository.update(item, transaction);
    }

    public void delete(int id) {
        this.repository.delete(id);
    }
}

