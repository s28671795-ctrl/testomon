/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.system.disk;

import com.incoresoft.system.disk.DiskSpaceInfo;

public class CombinedDiskSpaceInfo {
    private DiskSpaceInfo system;
    private DiskSpaceInfo storage;

    public boolean isEmpty() {
        return this.system == null && this.storage == null;
    }

    public void setSystem(DiskSpaceInfo system) {
        this.system = system;
    }

    public void setStorage(DiskSpaceInfo storage) {
        this.storage = storage;
    }

    public DiskSpaceInfo getSystem() {
        return this.system;
    }

    public DiskSpaceInfo getStorage() {
        return this.storage;
    }
}

