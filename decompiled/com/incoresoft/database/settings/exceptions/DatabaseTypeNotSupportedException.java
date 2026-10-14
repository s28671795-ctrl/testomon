/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.database.settings.exceptions;

import com.incoresoft.middleware.database.DBType;

public class DatabaseTypeNotSupportedException
extends RuntimeException {
    public DatabaseTypeNotSupportedException(DBType type) {
        super("Unsupported database type: " + String.valueOf((Object)type));
    }
}

