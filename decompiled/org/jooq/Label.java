/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.Named;
import org.jooq.Pro;
import org.jooq.SQLDialect;
import org.jooq.Statement;
import org.jooq.Support;
import org.jooq.impl.QOM;

@Pro
public interface Label
extends Named,
QOM.Label {
    @Support(value={SQLDialect.AURORA_POSTGRES, SQLDialect.DB2, SQLDialect.FIREBIRD, SQLDialect.H2, SQLDialect.HSQLDB, SQLDialect.INFORMIX, SQLDialect.MARIADB, SQLDialect.MYSQL, SQLDialect.ORACLE, SQLDialect.POSTGRES, SQLDialect.SQLDATAWAREHOUSE, SQLDialect.SQLSERVER, SQLDialect.YUGABYTEDB})
    @NotNull
    public Statement label(Statement var1);
}

