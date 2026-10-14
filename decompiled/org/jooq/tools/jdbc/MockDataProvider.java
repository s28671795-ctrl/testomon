/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.tools.jdbc;

import java.sql.SQLException;
import org.jooq.tools.jdbc.MockExecuteContext;
import org.jooq.tools.jdbc.MockResult;

@FunctionalInterface
public interface MockDataProvider {
    public MockResult[] execute(MockExecuteContext var1) throws SQLException;
}

