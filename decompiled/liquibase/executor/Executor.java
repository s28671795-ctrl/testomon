/*
 * Decompiled with CFR 0.152.
 */
package liquibase.executor;

import java.util.List;
import java.util.Map;
import liquibase.change.Change;
import liquibase.changelog.ChangeSet;
import liquibase.database.Database;
import liquibase.exception.DatabaseException;
import liquibase.exception.ValidationErrors;
import liquibase.plugin.Plugin;
import liquibase.resource.ResourceAccessor;
import liquibase.sql.visitor.SqlVisitor;
import liquibase.statement.SqlStatement;

public interface Executor
extends Plugin {
    public String getName();

    public int getPriority();

    public ValidationErrors validate(ChangeSet var1);

    public void modifyChangeSet(ChangeSet var1);

    public void setResourceAccessor(ResourceAccessor var1);

    public void setDatabase(Database var1);

    public <T> T queryForObject(SqlStatement var1, Class<T> var2) throws DatabaseException;

    public <T> T queryForObject(SqlStatement var1, Class<T> var2, List<SqlVisitor> var3) throws DatabaseException;

    public long queryForLong(SqlStatement var1) throws DatabaseException;

    public long queryForLong(SqlStatement var1, List<SqlVisitor> var2) throws DatabaseException;

    public int queryForInt(SqlStatement var1) throws DatabaseException;

    public int queryForInt(SqlStatement var1, List<SqlVisitor> var2) throws DatabaseException;

    public List queryForList(SqlStatement var1, Class var2) throws DatabaseException;

    public List queryForList(SqlStatement var1, Class var2, List<SqlVisitor> var3) throws DatabaseException;

    public List<Map<String, ?>> queryForList(SqlStatement var1) throws DatabaseException;

    public List<Map<String, ?>> queryForList(SqlStatement var1, List<SqlVisitor> var2) throws DatabaseException;

    public void execute(Change var1) throws DatabaseException;

    public void execute(Change var1, List<SqlVisitor> var2) throws DatabaseException;

    public void execute(SqlStatement var1) throws DatabaseException;

    public void execute(SqlStatement var1, List<SqlVisitor> var2) throws DatabaseException;

    public int update(SqlStatement var1) throws DatabaseException;

    public int update(SqlStatement var1, List<SqlVisitor> var2) throws DatabaseException;

    public void comment(String var1) throws DatabaseException;

    public boolean updatesDatabase();

    default public boolean supports(Database database) {
        return true;
    }
}

