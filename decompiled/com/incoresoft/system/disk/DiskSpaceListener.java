/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.system.disk;

import com.incoresoft.system.disk.CombinedDiskSpaceInfo;

public interface DiskSpaceListener {
    public void onLowDiskSpaceRecognized(CombinedDiskSpaceInfo var1);
}

