/*
 * Decompiled with CFR 0.152.
 */
package liquibase.sql.visitor;

import liquibase.database.Database;
import liquibase.sql.visitor.AbstractSqlVisitor;

public class AppendSqlVisitor
extends AbstractSqlVisitor {
    private String value;

    @Override
    public String getName() {
        return "append";
    }

    @Override
    public String modifySql(String sql, Database database) {
        return sql + this.value;
    }

    @Override
    public String getSerializedObjectNamespace() {
        return "http://www.liquibase.org/xml/ns/dbchangelog";
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

