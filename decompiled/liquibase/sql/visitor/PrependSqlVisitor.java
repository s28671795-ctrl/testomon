/*
 * Decompiled with CFR 0.152.
 */
package liquibase.sql.visitor;

import liquibase.database.Database;
import liquibase.sql.visitor.AbstractSqlVisitor;

public class PrependSqlVisitor
extends AbstractSqlVisitor {
    private String value;

    @Override
    public String getName() {
        return "prepend";
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String modifySql(String sql, Database database) {
        return this.value + sql;
    }

    @Override
    public String getSerializedObjectNamespace() {
        return "http://www.liquibase.org/xml/ns/dbchangelog";
    }
}

