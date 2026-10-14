/*
 * Decompiled with CFR 0.152.
 */
package liquibase.sql.visitor;

import liquibase.database.Database;
import liquibase.sql.visitor.AbstractSqlVisitor;

public class ReplaceSqlVisitor
extends AbstractSqlVisitor {
    private String replace;
    private String with;

    @Override
    public String getName() {
        return "replace";
    }

    public String getReplace() {
        return this.replace;
    }

    public void setReplace(String replace) {
        this.replace = replace;
    }

    public String getWith() {
        return this.with;
    }

    public void setWith(String with) {
        this.with = with;
    }

    @Override
    public String modifySql(String sql, Database database) {
        return sql.replace(this.getReplace(), this.getWith());
    }

    @Override
    public String getSerializedObjectNamespace() {
        return "http://www.liquibase.org/xml/ns/dbchangelog";
    }
}

