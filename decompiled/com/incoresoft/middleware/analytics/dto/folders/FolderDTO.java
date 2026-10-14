/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.analytics.dto.folders;

import com.alibaba.fastjson.annotation.JSONField;
import com.incoresoft.middleware.model.folders.Folder;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;

public class FolderDTO {
    private final Folder inner;

    public FolderDTO(@NotNull Folder folder) {
        this.inner = Objects.requireNonNull(folder);
    }

    @JSONField(name="id")
    public int getId() {
        return this.inner.getId();
    }

    @JSONField(name="name")
    public String getName() {
        return this.inner.getName();
    }
}

