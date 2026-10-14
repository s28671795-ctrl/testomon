/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.backup.database.exceptions;

public class BackupDatabaseStructureInvalidException
extends RuntimeException {
    public BackupDatabaseStructureInvalidException(Exception e) {
        super(e);
    }

    public BackupDatabaseStructureInvalidException(String message) {
        super(message);
    }
}

