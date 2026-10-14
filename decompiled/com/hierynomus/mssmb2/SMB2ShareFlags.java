/*
 * Decompiled with CFR 0.152.
 */
package com.hierynomus.mssmb2;

import com.hierynomus.protocol.commons.EnumWithValue;

public enum SMB2ShareFlags implements EnumWithValue<SMB2ShareFlags>
{
    SMB2_SHAREFLAG_MANUAL_CACHING(0L),
    SMB2_SHAREFLAG_AUTO_CACHING(16L),
    SMB2_SHAREFLAG_VDO_CACHING(32L),
    SMB2_SHAREFLAG_NO_CACHING(48L),
    SMB2_SHAREFLAG_DFS(1L),
    SMB2_SHAREFLAG_DFS_ROOT(2L),
    SMB2_SHAREFLAG_RESTRICT_EXCLUSIVE_OPENS(256L),
    SMB2_SHAREFLAG_FORCE_SHARED_DELETE(512L),
    SMB2_SHAREFLAG_ALLOW_NAMESPACE_CACHING(1024L),
    SMB2_SHAREFLAG_ACCESS_BASED_DIRECTORY_ENUM(2048L),
    SMB2_SHAREFLAG_FORCE_LEVELII_OPLOCK(4096L),
    SMB2_SHAREFLAG_ENABLE_HASH_V1(8192L),
    SMB2_SHAREFLAG_ENABLE_HASH_V2(16384L),
    SMB2_SHAREFLAG_ENCRYPT_DATA(32768L),
    SMB2_SHAREFLAG_IDENTITY_REMOTING(262144L);

    private long value;

    private SMB2ShareFlags(long value) {
        this.value = value;
    }

    @Override
    public long getValue() {
        return this.value;
    }
}

