/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.plugin.commons.items;

import com.incoresoft.plugin.commons.analytics.dto.items.ItemRenameDTO;

public record ItemRename(int id, String name) {
    public ItemRename(int id, ItemRenameDTO renameDTO) {
        this(id, renameDTO.name());
    }
}

