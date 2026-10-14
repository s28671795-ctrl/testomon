/*
 * Decompiled with CFR 0.152.
 */
package com.hierynomus.mssmb2;

import com.hierynomus.protocol.commons.EnumWithValue;

public enum SMB2LockFlag implements EnumWithValue<SMB2LockFlag>
{
    SMB2_LOCKFLAG_SHARED_LOCK(1L),
    SMB2_LOCKFLAG_EXCLUSIVE_LOCK(2L),
    SMB2_LOCKFLAG_UNLOCK(4L),
    SMB2_LOCKFLAG_FAIL_IMMEDIATELY(16L);

    private long value;

    private SMB2LockFlag(long value) {
        this.value = value;
    }

    @Override
    public long getValue() {
        return this.value;
    }
}

