/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.items;

import com.incoresoft.middleware.model.Model;
import com.incoresoft.middleware.model.folders.Folder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class ItemGroup
implements Model,
Folder {
    public static final int IMPOSSIBLE_PARENT = -1;
    public static final String DEFAULT_PARENT_NAME = "root";
    public static final int ROOT_ID = 0;
    protected final Integer id;
    protected final String name;
    protected final int parentId;
    protected final int clientId;

    protected ItemGroup(@Nullable Integer id, @NotNull String name, int parentId, int clientId) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.clientId = clientId;
    }

    public Integer id() {
        return this.id;
    }

    @Override
    public int getId() {
        return this.id();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Folder getParent() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getClientId() {
        return this.clientId;
    }

    public boolean isRoot() {
        return this.id != null && this.id == 0;
    }

    public int getParentId() {
        return this.parentId;
    }
}

