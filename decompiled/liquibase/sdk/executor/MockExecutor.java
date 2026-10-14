/*
 * Decompiled with CFR 0.152.
 */
package liquibase.sdk.executor;

import java.io.StringWriter;
import liquibase.database.core.MockDatabase;
import liquibase.executor.LoggingExecutor;
import liquibase.servicelocator.LiquibaseService;

@LiquibaseService(skip=true)
public class MockExecutor
extends LoggingExecutor {
    public MockExecutor() {
        super(null, new StringWriter(), new MockDatabase());
    }

    public String getRanSql() {
        return this.getOutput().toString();
    }
}

