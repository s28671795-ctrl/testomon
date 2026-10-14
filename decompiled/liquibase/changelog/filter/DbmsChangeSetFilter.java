/*
 * Decompiled with CFR 0.152.
 */
package liquibase.changelog.filter;

import java.util.ArrayList;
import liquibase.changelog.ChangeSet;
import liquibase.changelog.filter.ChangeSetFilter;
import liquibase.changelog.filter.ChangeSetFilterResult;
import liquibase.database.Database;
import liquibase.database.DatabaseList;
import liquibase.sql.visitor.SqlVisitor;
import liquibase.util.StringUtil;

public class DbmsChangeSetFilter
implements ChangeSetFilter {
    private final Database database;
    public static final String MDC_NAME = "dbmsMismatch";
    public static final String DISPLAY_NAME = "DBMS mismatch";

    public DbmsChangeSetFilter(Database database) {
        this.database = database;
    }

    @Override
    public ChangeSetFilterResult accepts(ChangeSet changeSet) {
        if (this.database == null) {
            return new ChangeSetFilterResult(true, "No database connection, cannot evaluate dbms attribute", this.getClass(), this.getMdcName(), this.getDisplayName());
        }
        ArrayList<SqlVisitor> visitorsToRemove = new ArrayList<SqlVisitor>();
        for (SqlVisitor visitor : changeSet.getSqlVisitors()) {
            if (DatabaseList.definitionMatches(visitor.getApplicableDbms(), this.database, true)) continue;
            visitorsToRemove.add(visitor);
        }
        changeSet.getSqlVisitors().removeAll(visitorsToRemove);
        String dbmsList = changeSet.getDbmsSet() == null || changeSet.getDbmsSet().isEmpty() ? "all databases" : "'" + StringUtil.join(changeSet.getDbmsSet(), ", ") + "'";
        if (DatabaseList.definitionMatches(changeSet.getDbmsSet(), this.database, true)) {
            return new ChangeSetFilterResult(true, "Database '" + this.database.getShortName() + "' matches " + dbmsList, this.getClass(), this.getMdcName(), this.getDisplayName());
        }
        return new ChangeSetFilterResult(false, "Database '" + this.database.getShortName() + "' does not match " + dbmsList, this.getClass(), this.getMdcName(), this.getDisplayName());
    }

    @Override
    public String getMdcName() {
        return MDC_NAME;
    }

    @Override
    public String getDisplayName() {
        return DISPLAY_NAME;
    }
}

