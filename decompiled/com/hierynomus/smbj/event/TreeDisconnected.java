/*
 * Decompiled with CFR 0.152.
 */
package com.hierynomus.smbj.event;

import com.hierynomus.smbj.event.SessionEvent;

public class TreeDisconnected
extends SessionEvent {
    private long treeId;

    public TreeDisconnected(long sessionId, long treeId) {
        super(sessionId);
        this.treeId = treeId;
    }

    public long getTreeId() {
        return this.treeId;
    }
}

