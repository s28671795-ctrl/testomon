/*
 * Decompiled with CFR 0.152.
 */
package org.h2.tools;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.h2.api.Trigger;
import org.h2.message.DbException;

public abstract class TriggerAdapter
implements Trigger {
    protected String schemaName;
    protected String triggerName;
    protected String tableName;
    protected boolean before;
    protected int type;

    @Override
    public void init(Connection connection, String string, String string2, String string3, boolean bl, int n) throws SQLException {
        this.schemaName = string;
        this.triggerName = string2;
        this.tableName = string3;
        this.before = bl;
        this.type = n;
    }

    @Override
    public final void fire(Connection connection, Object[] objectArray, Object[] objectArray2) throws SQLException {
        throw DbException.getInternalError();
    }

    public abstract void fire(Connection var1, ResultSet var2, ResultSet var3) throws SQLException;
}

