/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.database;

import com.incoresoft.middleware.connector.Connector;
import com.incoresoft.middleware.database.DBType;
import com.incoresoft.middleware.database.RecordLoader;
import javax.sql.DataSource;
import org.pf4j.ExtensionPoint;

public interface DatabaseConnector
extends ExtensionPoint,
Connector {
    public DataSource getDataSource();

    public DBType getDBType();

    public void load(RecordLoader var1);

    public boolean checkStructure(String var1, ClassLoader var2);
}

