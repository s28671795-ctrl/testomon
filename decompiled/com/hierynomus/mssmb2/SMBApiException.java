/*
 * Decompiled with CFR 0.152.
 */
package com.hierynomus.mssmb2;

import com.hierynomus.mserref.NtStatus;
import com.hierynomus.mssmb2.SMB2MessageCommandCode;
import com.hierynomus.mssmb2.SMB2PacketHeader;
import com.hierynomus.smbj.common.SMBRuntimeException;

public class SMBApiException
extends SMBRuntimeException {
    private final SMB2MessageCommandCode failedCommand;
    private long statusCode;

    public SMBApiException(long status, SMB2MessageCommandCode failedCommand, Throwable t2) {
        super(t2);
        this.statusCode = status;
        this.failedCommand = failedCommand;
    }

    public SMBApiException(long status, SMB2MessageCommandCode failedCommand, String message, Throwable t2) {
        super(message, t2);
        this.statusCode = status;
        this.failedCommand = failedCommand;
    }

    public SMBApiException(SMB2PacketHeader header, String message) {
        super(message);
        this.statusCode = header.getStatusCode();
        this.failedCommand = header.getMessage();
    }

    public SMBApiException(SMB2PacketHeader header, String message, Throwable t2) {
        super(message, t2);
        this.statusCode = header.getStatusCode();
        this.failedCommand = header.getMessage();
    }

    public NtStatus getStatus() {
        return NtStatus.valueOf(this.statusCode);
    }

    public long getStatusCode() {
        return this.statusCode;
    }

    public SMB2MessageCommandCode getFailedCommand() {
        return this.failedCommand;
    }

    @Override
    public String getMessage() {
        return String.format("%s (0x%08x): %s", this.getStatus().name(), this.statusCode, super.getMessage());
    }
}

