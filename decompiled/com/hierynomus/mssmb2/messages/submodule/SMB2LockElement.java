/*
 * Decompiled with CFR 0.152.
 */
package com.hierynomus.mssmb2.messages.submodule;

import com.hierynomus.mssmb2.SMB2LockFlag;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public class SMB2LockElement {
    private static final List<EnumSet<SMB2LockFlag>> VALID_FLAG_COMBINATIONS = Arrays.asList(EnumSet.of(SMB2LockFlag.SMB2_LOCKFLAG_SHARED_LOCK), EnumSet.of(SMB2LockFlag.SMB2_LOCKFLAG_EXCLUSIVE_LOCK), EnumSet.of(SMB2LockFlag.SMB2_LOCKFLAG_SHARED_LOCK, SMB2LockFlag.SMB2_LOCKFLAG_FAIL_IMMEDIATELY), EnumSet.of(SMB2LockFlag.SMB2_LOCKFLAG_EXCLUSIVE_LOCK, SMB2LockFlag.SMB2_LOCKFLAG_FAIL_IMMEDIATELY), EnumSet.of(SMB2LockFlag.SMB2_LOCKFLAG_UNLOCK));
    private final long offset;
    private final long length;
    private final Set<SMB2LockFlag> lockFlags;

    public SMB2LockElement(long offset, long length, Set<SMB2LockFlag> lockFlags) {
        EnumSet<SMB2LockFlag> enumLockFlags;
        this.offset = offset;
        this.length = length;
        EnumSet<SMB2LockFlag> enumSet = enumLockFlags = lockFlags != null ? EnumSet.copyOf(lockFlags) : EnumSet.noneOf(SMB2LockFlag.class);
        if (!VALID_FLAG_COMBINATIONS.contains(enumLockFlags)) {
            throw new IllegalArgumentException("Invalid lock flags combination. Check SMB2 document 2.2.26.1 SMB2_LOCK_ELEMENT Structure.");
        }
        this.lockFlags = lockFlags;
    }

    public long getOffset() {
        return this.offset;
    }

    public long getLength() {
        return this.length;
    }

    public Set<SMB2LockFlag> getLockFlags() {
        return this.lockFlags;
    }

    public String toString() {
        return "SMB2LockElement{offset=" + this.offset + ", length=" + this.length + ", lockFlags=" + this.lockFlags + '}';
    }
}

