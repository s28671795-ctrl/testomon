/*
 * Decompiled with CFR 0.152.
 */
package com.hierynomus.mssmb2;

import com.hierynomus.smb.SMBHeader;
import com.hierynomus.smb.SMBPacketData;

public class DeadLetterPacketData
extends SMBPacketData<SMBHeader> {
    public DeadLetterPacketData(SMBHeader header) {
        super(header);
    }
}

