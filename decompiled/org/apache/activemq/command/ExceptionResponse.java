/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.command;

import org.apache.activemq.command.Response;

public class ExceptionResponse
extends Response {
    public static final byte DATA_STRUCTURE_TYPE = 31;
    Throwable exception;

    public ExceptionResponse() {
    }

    public ExceptionResponse(Throwable e) {
        this.setException(e);
    }

    @Override
    public byte getDataStructureType() {
        return 31;
    }

    public Throwable getException() {
        return this.exception;
    }

    public void setException(Throwable exception) {
        this.exception = exception;
    }

    @Override
    public boolean isException() {
        return true;
    }
}

