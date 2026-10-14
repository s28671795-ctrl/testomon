/*
 * Decompiled with CFR 0.152.
 */
package org.h2.api;

import org.h2.command.ddl.CreateTableData;
import org.h2.table.Table;

public interface TableEngine {
    public Table createTable(CreateTableData var1);
}

