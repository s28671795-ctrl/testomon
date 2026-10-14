/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.tools.jdbc;

import java.sql.SQLException;
import java.sql.Savepoint;

public class MockSavepoint
implements Savepoint {
    private final String name;

    MockSavepoint() {
        this(null);
    }

    MockSavepoint(String name) {
        this.name = name;
    }

    @Override
    public int getSavepointId() throws SQLException {
        return this.name != null ? this.name.hashCode() : this.hashCode();
    }

    @Override
    public String getSavepointName() throws SQLException {
        return this.name != null ? this.name : this.toString();
    }
}

