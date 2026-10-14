/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.model.folders;

public interface Folder {
    public int getId();

    public String getName();

    public Folder getParent();

    public int getClientId();
}

