/*
 * Decompiled with CFR 0.152.
 */
package liquibase.sql;

import java.util.Collection;
import java.util.HashSet;
import liquibase.sql.Sql;
import liquibase.structure.DatabaseObject;

public class SingleLineComment
implements Sql {
    private final String sql;
    private final String lineCommentToken;

    public SingleLineComment(String sql, String lineCommentToken) {
        this.sql = sql;
        this.lineCommentToken = lineCommentToken;
    }

    @Override
    public Collection<? extends DatabaseObject> getAffectedDatabaseObjects() {
        return new HashSet();
    }

    @Override
    public String getEndDelimiter() {
        return "\n";
    }

    @Override
    public String toSql() {
        return this.lineCommentToken + ' ' + this.sql;
    }

    public String toString() {
        return this.toSql();
    }
}

