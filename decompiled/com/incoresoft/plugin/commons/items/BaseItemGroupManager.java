/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.items;

import com.incoresoft.middleware.model.user.User;
import com.incoresoft.plugin.commons.database.CRUDRepository;
import com.incoresoft.plugin.commons.items.BaseItemManager;
import com.incoresoft.plugin.commons.items.ItemGroup;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BooleanSupplier;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

public class BaseItemGroupManager<G extends ItemGroup>
extends BaseItemManager<G> {
    protected final G root;

    protected BaseItemGroupManager(@NotNull CRUDRepository<?, G> repository, @NotNull G root) {
        super(repository);
        this.root = (ItemGroup)Objects.requireNonNull(root);
    }

    @Override
    public List<G> getAll() {
        return this.preparedList(super.getAll(), () -> true);
    }

    @Override
    public List<G> getAll(User user) {
        return this.preparedList(super.getAll(user), () -> true);
    }

    @Override
    public List<G> getAll(String exactName, Integer clientId) {
        return this.preparedList(super.getAll(exactName, clientId), () -> StringUtils.containsIgnoreCase(((ItemGroup)this.root).name, exactName));
    }

    public G root() {
        return this.root;
    }

    public boolean exists(int id) {
        if (id == ((ItemGroup)this.root).getId()) {
            return true;
        }
        return this.repository.exists(id);
    }

    @Override
    public Optional<G> get(int id) {
        if (id == ((ItemGroup)this.root).getId()) {
            return Optional.of(this.root);
        }
        return this.repository.get(id);
    }

    @Override
    public G update(G item) {
        if (((ItemGroup)item).getId() == ((ItemGroup)this.root).getId()) {
            return this.root;
        }
        return (G)((ItemGroup)this.repository.update(item));
    }

    @Override
    public void delete(int id) {
        if (id == ((ItemGroup)this.root).getId()) {
            throw new IllegalArgumentException("Can't delete root group");
        }
        this.repository.delete(id);
    }

    public boolean isGroupParentToItself(G group) {
        if (((ItemGroup)group).id() == null) {
            return false;
        }
        HashSet<Integer> restrictedParentIds = new HashSet<Integer>();
        Object currentGroup = group;
        while (!restrictedParentIds.contains(((ItemGroup)this.root).getId())) {
            if (restrictedParentIds.contains(((ItemGroup)currentGroup).getId())) {
                return true;
            }
            restrictedParentIds.add(((ItemGroup)currentGroup).getId());
            Optional<G> groupOptional = this.get(((ItemGroup)currentGroup).getParentId());
            if (groupOptional.isEmpty()) {
                return false;
            }
            currentGroup = (ItemGroup)groupOptional.get();
        }
        return false;
    }

    public boolean hasParentWithId(G child, int parentId) {
        Object current = child;
        while (!((ItemGroup)current).isRoot()) {
            if (((ItemGroup)current).getId() == parentId) {
                return true;
            }
            current = (ItemGroup)this.get(((ItemGroup)current).getParentId()).orElseThrow();
        }
        return false;
    }

    protected List<G> preparedList(List<G> groups2, BooleanSupplier includeRoot) {
        ArrayList<G> preparedList = new ArrayList<G>();
        if (includeRoot.getAsBoolean()) {
            preparedList.add(this.root);
        }
        preparedList.addAll(groups2);
        return preparedList;
    }
}

