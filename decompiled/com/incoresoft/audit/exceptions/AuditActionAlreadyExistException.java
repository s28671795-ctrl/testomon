/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.audit.exceptions;

import com.incoresoft.audit.AuditSourceActionContainer;

public class AuditActionAlreadyExistException
extends RuntimeException {
    public AuditActionAlreadyExistException(AuditSourceActionContainer registered, AuditSourceActionContainer failed) {
        super(String.format("Failed to register %s from  %s. Action with %s was already registered by %s from %s", failed.action().getName(), failed.source(), registered.action().getId(), registered.action().getName(), registered.source()));
    }
}

