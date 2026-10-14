/*
 * Decompiled with CFR 0.152.
 */
package liquibase.sql.visitor;

import liquibase.database.Database;
import liquibase.sql.visitor.AppendSqlVisitor;

public class AppendSqlIfNotPresentVisitor
extends AppendSqlVisitor {
    @Override
    public String modifySql(String sql, Database database) {
        String returnValue = sql;
        String appendValue = this.getValue();
        if (!returnValue.endsWith(appendValue)) {
            returnValue = returnValue + appendValue;
        }
        return returnValue;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof AppendSqlIfNotPresentVisitor)) {
            return false;
        }
        AppendSqlIfNotPresentVisitor other = (AppendSqlIfNotPresentVisitor)o;
        return other.canEqual(this);
    }

    protected boolean canEqual(Object other) {
        return other instanceof AppendSqlIfNotPresentVisitor;
    }

    public int hashCode() {
        boolean result = true;
        return 1;
    }
}

