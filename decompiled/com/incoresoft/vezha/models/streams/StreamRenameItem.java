/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.models.streams;

import com.incoresoft.middleware.model.stream.Stream;
import java.util.UUID;

public record StreamRenameItem(Integer id, UUID uuid, String oldName, String newName) {
    public StreamRenameItem(Stream stream, String newName) {
        this(stream.getId(), stream.getUUID(), stream.getName(), newName);
    }
}

