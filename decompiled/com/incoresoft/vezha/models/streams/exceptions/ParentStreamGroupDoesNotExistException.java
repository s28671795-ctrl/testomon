/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.vezha.models.streams.exceptions;

public class ParentStreamGroupDoesNotExistException
extends RuntimeException {
    private final int parentGroupId;

    public ParentStreamGroupDoesNotExistException(int parentGroupId) {
        this.parentGroupId = parentGroupId;
    }

    public int getParentGroupId() {
        return this.parentGroupId;
    }
}

