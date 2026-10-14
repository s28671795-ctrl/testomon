/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.database;

public enum DBType {
    mysql("com.mysql.cj.jdbc.Driver", false),
    postgresql("org.postgresql.Driver", true),
    sqlserver("com.microsoft.sqlserver.jdbc.SQLServerDriver", false),
    h2("org.h2.Driver", false),
    oracle("oracle.jdbc.driver.OracleDriver", true),
    sqlite("org.sqlite.JDBC", true);

    private final String driverName;
    private final boolean isSchemaSpecifiable;

    public boolean isInternalDB() {
        return this == sqlite || this == h2;
    }

    public String getDriverName() {
        return this.driverName;
    }

    public boolean isSchemaSpecifiable() {
        return this.isSchemaSpecifiable;
    }

    private DBType(String driverName, boolean isSchemaSpecifiable) {
        this.driverName = driverName;
        this.isSchemaSpecifiable = isSchemaSpecifiable;
    }
}

