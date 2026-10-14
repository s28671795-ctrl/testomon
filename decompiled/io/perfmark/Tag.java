/*
 * Decompiled with CFR 0.152.
 */
package io.perfmark;

import javax.annotation.Nullable;

public final class Tag {
    @Nullable
    final String tagName;
    final long tagId;

    Tag(@Nullable String tagName, long tagId) {
        this.tagName = tagName;
        this.tagId = tagId;
    }
}

