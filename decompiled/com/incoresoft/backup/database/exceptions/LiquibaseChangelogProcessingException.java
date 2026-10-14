/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.backup.database.exceptions;

public class LiquibaseChangelogProcessingException
extends RuntimeException {
    public LiquibaseChangelogProcessingException(Exception e) {
        super(e);
    }

    public LiquibaseChangelogProcessingException() {
        super("Liquibase processed changelog file already exists");
    }
}

