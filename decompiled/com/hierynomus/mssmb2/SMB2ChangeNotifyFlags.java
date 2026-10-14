/*
 * Decompiled with CFR 0.152.
 */
package com.hierynomus.mssmb2;

import com.hierynomus.protocol.commons.EnumWithValue;

public enum SMB2ChangeNotifyFlags implements EnumWithValue<SMB2ChangeNotifyFlags>
{
    WATCH_TREE(1L);

    private long value;

    private SMB2ChangeNotifyFlags(long value) {
        this.value = value;
    }

    @Override
    public long getValue() {
        return this.value;
    }
}

