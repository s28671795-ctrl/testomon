/*
 * Decompiled with CFR 0.152.
 */
package liquibase.changelog.visitor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;
import liquibase.CatalogAndSchema;
import liquibase.change.Change;
import liquibase.changelog.ChangeSet;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.changelog.filter.ChangeSetFilterResult;
import liquibase.changelog.visitor.ChangeSetVisitor;
import liquibase.database.Database;
import liquibase.database.core.FirebirdDatabase;
import liquibase.dbdoc.AuthorListWriter;
import liquibase.dbdoc.AuthorWriter;
import liquibase.dbdoc.ChangeLogListWriter;
import liquibase.dbdoc.ChangeLogWriter;
import liquibase.dbdoc.ColumnWriter;
import liquibase.dbdoc.PendingChangesWriter;
import liquibase.dbdoc.PendingSQLWriter;
import liquibase.dbdoc.RecentChangesWriter;
import liquibase.dbdoc.TableListWriter;
import liquibase.dbdoc.TableWriter;
import liquibase.exception.LiquibaseException;
import liquibase.resource.OpenOptions;
import liquibase.resource.Resource;
import liquibase.resource.ResourceAccessor;
import liquibase.snapshot.DatabaseSnapshot;
import liquibase.snapshot.SnapshotControl;
import liquibase.snapshot.SnapshotGeneratorFactory;
import liquibase.structure.DatabaseObject;
import liquibase.structure.core.Column;
import liquibase.structure.core.Schema;
import liquibase.structure.core.Table;
import liquibase.util.StreamUtil;
import liquibase.util.StringUtil;

public class DBDocVisitor
implements ChangeSetVisitor {
    private static final int MAX_RECENT_CHANGE = 50;
    private final Database database;
    private final SortedSet<ChangeLogInfo> changeLogs;
    private final Map<DatabaseObject, List<Change>> changesByObject;
    private final Map<String, List<Change>> changesByAuthor;
    private final Map<DatabaseObject, List<Change>> changesToRunByObject;
    private final Map<String, List<Change>> changesToRunByAuthor;
    private final List<Change> changesToRun;
    private List<Change> recentChanges;
    private String rootChangeLogName;
    private DatabaseChangeLog rootChangeLog;

    public DBDocVisitor(Database database) {
        this.database = database;
        this.changesByObject = new HashMap<DatabaseObject, List<Change>>();
        this.changesByAuthor = new HashMap<String, List<Change>>();
        this.changeLogs = new TreeSet<ChangeLogInfo>();
        this.changesToRunByObject = new HashMap<DatabaseObject, List<Change>>();
        this.changesToRunByAuthor = new HashMap<String, List<Change>>();
        this.changesToRun = new ArrayList<Change>();
        this.recentChanges = new ArrayList<Change>();
    }

    @Override
    public ChangeSetVisitor.Direction getDirection() {
        return ChangeSetVisitor.Direction.FORWARD;
    }

    @Override
    public void visit(ChangeSet changeSet, DatabaseChangeLog databaseChangeLog, Database database, Set<ChangeSetFilterResult> filterResults) throws LiquibaseException {
        ChangeSet.RunStatus runStatus = this.database.getRunStatus(changeSet);
        if (this.rootChangeLogName == null) {
            this.rootChangeLogName = changeSet.getFilePath();
        }
        if (this.rootChangeLog == null) {
            this.rootChangeLog = databaseChangeLog;
        }
        if (!this.changesByAuthor.containsKey(changeSet.getAuthor())) {
            this.changesByAuthor.put(changeSet.getAuthor(), new ArrayList());
        }
        if (!this.changesToRunByAuthor.containsKey(changeSet.getAuthor())) {
            this.changesToRunByAuthor.put(changeSet.getAuthor(), new ArrayList());
        }
        boolean toRun = runStatus.equals((Object)ChangeSet.RunStatus.NOT_RAN) || runStatus.equals((Object)ChangeSet.RunStatus.RUN_AGAIN);
        for (Change change : changeSet.getChanges()) {
            if (toRun) {
                this.changesToRunByAuthor.get(changeSet.getAuthor()).add(change);
                this.changesToRun.add(change);
                continue;
            }
            this.changesByAuthor.get(changeSet.getAuthor()).add(change);
            this.recentChanges.add(0, change);
        }
        ChangeLogInfo changeLogInfo = new ChangeLogInfo(changeSet.getChangeLog().getLogicalFilePath(), changeSet.getChangeLog().getPhysicalFilePath());
        if (!this.changeLogs.contains(changeLogInfo)) {
            this.changeLogs.add(changeLogInfo);
        }
        for (Change change : changeSet.getChanges()) {
            Set<DatabaseObject> affectedDatabaseObjects = change.getAffectedDatabaseObjects(database);
            if (affectedDatabaseObjects == null) continue;
            for (DatabaseObject dbObject : affectedDatabaseObjects) {
                if (toRun) {
                    if (!this.changesToRunByObject.containsKey(dbObject)) {
                        this.changesToRunByObject.put(dbObject, new ArrayList());
                    }
                    this.changesToRunByObject.get(dbObject).add(change);
                    continue;
                }
                if (!this.changesByObject.containsKey(dbObject)) {
                    this.changesByObject.put(dbObject, new ArrayList());
                }
                this.changesByObject.get(dbObject).add(change);
            }
        }
    }

    public void writeHTML(Resource rootOutputDir, ResourceAccessor resourceAccessor, CatalogAndSchema ... schemaList) throws IOException, LiquibaseException {
        ChangeLogWriter changeLogWriter = new ChangeLogWriter(resourceAccessor, rootOutputDir);
        AuthorWriter authorWriter = new AuthorWriter(rootOutputDir, this.database);
        TableWriter tableWriter = new TableWriter(rootOutputDir, this.database);
        ColumnWriter columnWriter = new ColumnWriter(rootOutputDir, this.database);
        PendingChangesWriter pendingChangesWriter = new PendingChangesWriter(rootOutputDir, this.database);
        RecentChangesWriter recentChangesWriter = new RecentChangesWriter(rootOutputDir, this.database);
        PendingSQLWriter pendingSQLWriter = new PendingSQLWriter(rootOutputDir, this.database, this.rootChangeLog);
        CatalogAndSchema[] computedSchemaList = schemaList;
        if (schemaList == null) {
            computedSchemaList = new CatalogAndSchema[]{this.database.getDefaultSchema()};
        }
        DatabaseSnapshot snapshot = SnapshotGeneratorFactory.getInstance().createSnapshot(computedSchemaList, this.database, new SnapshotControl(this.database));
        if (schemaList != null && schemaList.length != 0 && !(this.database instanceof FirebirdDatabase)) {
            this.validateRequiredSchemas(snapshot, computedSchemaList);
        }
        this.copyFile("liquibase/dbdoc/stylesheet.css", rootOutputDir);
        this.copyFile("liquibase/dbdoc/index.html", rootOutputDir);
        this.copyFile("liquibase/dbdoc/globalnav.html", rootOutputDir);
        this.copyFile("liquibase/dbdoc/overview-summary.html", rootOutputDir);
        new ChangeLogListWriter(rootOutputDir).writeHTML(this.changeLogs);
        TreeSet<Table> tables = new TreeSet<Table>(snapshot.get(Table.class));
        tables.removeIf(this.database::isLiquibaseObject);
        new TableListWriter(rootOutputDir).writeHTML(tables);
        new AuthorListWriter(rootOutputDir).writeHTML(new TreeSet<String>(this.changesByAuthor.keySet()));
        for (String author : this.changesByAuthor.keySet()) {
            authorWriter.writeHTML(author, this.changesByAuthor.get(author), this.changesToRunByAuthor.get(author), this.rootChangeLogName);
        }
        for (Table table : tables) {
            if (this.database.isLiquibaseObject(table)) continue;
            tableWriter.writeHTML(table, this.changesByObject.get(table), this.changesToRunByObject.get(table), this.rootChangeLogName, table.getAttribute("schema", new Schema()).toString());
        }
        for (Column column : snapshot.get(Column.class)) {
            if (this.shouldNotWriteColumnHtml(column)) continue;
            columnWriter.writeHTML(column, this.changesByObject.get(column), this.changesToRunByObject.get(column), this.rootChangeLogName);
        }
        for (ChangeLogInfo changeLog : this.changeLogs) {
            changeLogWriter.writeChangeLog(changeLog.logicalPath, changeLog.physicalPath);
        }
        pendingChangesWriter.writeHTML("index", null, this.changesToRun, this.rootChangeLogName);
        pendingSQLWriter.writeHTML("sql", null, this.changesToRun, this.rootChangeLogName);
        if (this.recentChanges.size() > 50) {
            this.recentChanges = this.recentChanges.subList(0, 50);
        }
        recentChangesWriter.writeHTML("index", this.recentChanges, null, this.rootChangeLogName);
    }

    private void validateRequiredSchemas(DatabaseSnapshot snapshot, CatalogAndSchema[] schemaList) throws LiquibaseException {
        Set<Schema> schemasFoundAtDb = snapshot.get(Schema.class);
        if (schemasFoundAtDb == null || schemasFoundAtDb.isEmpty()) {
            throw new LiquibaseException("Could not find any of the required schemas at the configured database.");
        }
        Set schemasNamesAtDb = schemasFoundAtDb.stream().filter(s2 -> !StringUtil.isEmpty(s2.getName())).map(s2 -> s2.getName().toLowerCase()).collect(Collectors.toSet());
        Set requiredSchemaNames = Arrays.stream(schemaList).filter(s2 -> !StringUtil.isEmpty(s2.getSchemaName())).map(s2 -> s2.getSchemaName().toLowerCase()).collect(Collectors.toSet());
        ArrayList<String> notFoundSchemas = new ArrayList<String>();
        for (String required : requiredSchemaNames) {
            if (schemasNamesAtDb.contains(required)) continue;
            notFoundSchemas.add(required);
        }
        if (!notFoundSchemas.isEmpty()) {
            throw new LiquibaseException("The following schema(s) could not be found at database: " + StringUtil.join(notFoundSchemas, ","));
        }
    }

    private boolean shouldNotWriteColumnHtml(Column column) {
        return this.database.isLiquibaseObject(column.getRelation()) || Boolean.TRUE.equals(column.getComputed());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void copyFile(String fileToCopy, Resource rootOutputDir) throws IOException {
        InputStream inputStream2 = this.getClass().getClassLoader().getResourceAsStream(fileToCopy);
        try (OutputStream outputStream2 = null;){
            if (inputStream2 == null) {
                throw new IOException("Can not find " + fileToCopy);
            }
            outputStream2 = rootOutputDir.resolve(fileToCopy.replaceFirst(".*\\/", "")).openOutputStream(new OpenOptions());
            StreamUtil.copy(inputStream2, outputStream2);
        }
    }

    private static class ChangeLogInfo
    implements Comparable<ChangeLogInfo> {
        public String logicalPath;
        public String physicalPath;

        private ChangeLogInfo(String logicalPath, String physicalPath) {
            this.logicalPath = logicalPath;
            this.physicalPath = physicalPath;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || this.getClass() != o.getClass()) {
                return false;
            }
            ChangeLogInfo that = (ChangeLogInfo)o;
            return this.logicalPath.equals(that.logicalPath);
        }

        public int hashCode() {
            return this.logicalPath.hashCode();
        }

        @Override
        public int compareTo(ChangeLogInfo o) {
            return this.logicalPath.compareTo(o.logicalPath);
        }

        public String toString() {
            return this.logicalPath;
        }
    }
}

