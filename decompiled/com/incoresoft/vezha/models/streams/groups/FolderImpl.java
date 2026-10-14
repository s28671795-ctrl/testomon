/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.models.streams.groups;

import com.incoresoft.middleware.model.folders.Folder;
import java.util.Objects;
import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;

public class FolderImpl
implements Folder {
    private final int folderId;
    private final String name;
    private final Folder parent;
    private final int clientId;

    public FolderImpl(int folderId, @NotNull String name, @Nullable Folder parent, int clientId) {
        this.folderId = folderId;
        this.name = Objects.requireNonNull(name);
        this.parent = parent;
        this.clientId = clientId;
    }

    @Override
    public int getId() {
        return this.folderId;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Folder getParent() {
        return this.parent;
    }

    @Override
    public int getClientId() {
        return this.clientId;
    }
}

