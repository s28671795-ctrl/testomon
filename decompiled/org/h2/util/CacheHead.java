/*
 * Decompiled with CFR 0.152.
 */
package org.h2.util;

import org.h2.util.CacheObject;

public class CacheHead
extends CacheObject {
    @Override
    public boolean canRemove() {
        return false;
    }

    @Override
    public int getMemory() {
        return 0;
    }
}

