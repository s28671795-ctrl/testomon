/*
 * Decompiled with CFR 0.152.
 */
package liquibase.snapshot.jvm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import liquibase.Scope;
import liquibase.database.AbstractJdbcDatabase;
import liquibase.database.Database;
import liquibase.database.core.InformixDatabase;
import liquibase.database.core.PostgresDatabase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.diff.DiffStatusListener;
import liquibase.exception.DatabaseException;
import liquibase.snapshot.DatabaseSnapshot;
import liquibase.snapshot.InvalidExampleException;
import liquibase.snapshot.SnapshotGenerator;
import liquibase.snapshot.SnapshotGeneratorChain;
import liquibase.structure.DatabaseObject;

public abstract class JdbcSnapshotGenerator
implements SnapshotGenerator {
    private final Set<DiffStatusListener> statusListeners = new HashSet<DiffStatusListener>();
    private final Class<? extends DatabaseObject> defaultFor;
    private Class<? extends DatabaseObject>[] addsTo;

    protected JdbcSnapshotGenerator(Class<? extends DatabaseObject> defaultFor) {
        this.defaultFor = defaultFor;
    }

    protected JdbcSnapshotGenerator(Class<? extends DatabaseObject> defaultFor, Class<? extends DatabaseObject>[] addsTo) {
        this.defaultFor = defaultFor;
        this.addsTo = addsTo;
    }

    @Override
    public int getPriority(Class<? extends DatabaseObject> objectType, Database database) {
        if (database instanceof AbstractJdbcDatabase) {
            if (this.defaultFor != null && this.defaultFor.isAssignableFrom(objectType)) {
                return 1;
            }
            if (this.addsTo() != null) {
                for (Class<? extends DatabaseObject> type : this.addsTo()) {
                    if (!type.isAssignableFrom(objectType)) continue;
                    return 50;
                }
            }
        }
        return -1;
    }

    @Override
    public Class<? extends DatabaseObject>[] addsTo() {
        return this.addsTo;
    }

    public DatabaseObject snapshot(DatabaseObject example, DatabaseSnapshot snapshot, SnapshotGeneratorChain chain) throws DatabaseException, InvalidExampleException {
        if (this.defaultFor != null && this.defaultFor.isAssignableFrom(example.getClass())) {
            return this.snapshotObject(example, snapshot);
        }
        DatabaseObject chainResponse = chain.snapshot(example, snapshot);
        if (chainResponse == null) {
            return null;
        }
        if (this.shouldAddTo(example.getClass(), snapshot) && this.addsTo() != null) {
            for (Class<? extends DatabaseObject> addType : this.addsTo()) {
                if (!addType.isAssignableFrom(example.getClass()) || chainResponse == null) continue;
                this.addTo(chainResponse, snapshot);
            }
        }
        return chainResponse;
    }

    protected boolean shouldAddTo(Class<? extends DatabaseObject> databaseObjectType, DatabaseSnapshot snapshot) {
        return this.defaultFor != null && snapshot.getSnapshotControl().shouldInclude(this.defaultFor);
    }

    @Override
    public Class<? extends SnapshotGenerator>[] replaces() {
        return null;
    }

    protected abstract DatabaseObject snapshotObject(DatabaseObject var1, DatabaseSnapshot var2) throws DatabaseException, InvalidExampleException;

    protected abstract void addTo(DatabaseObject var1, DatabaseSnapshot var2) throws DatabaseException, InvalidExampleException;

    public void addStatusListener(DiffStatusListener listener) {
        this.statusListeners.add(listener);
    }

    protected void updateListeners(String message) {
        Scope.getCurrentScope().getLog(this.getClass()).fine(message);
        for (DiffStatusListener listener : this.statusListeners) {
            listener.statusUpdate(message);
        }
    }

    protected String cleanNameFromDatabase(String objectName, Database database) {
        if (objectName == null) {
            return null;
        }
        if (!(database instanceof InformixDatabase)) {
            objectName = objectName.trim();
        }
        if (database instanceof PostgresDatabase) {
            return objectName.replaceAll("\"", "");
        }
        return objectName;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected String[] getDatabaseCatalogNames(Database database) throws SQLException, DatabaseException {
        ArrayList<String> returnList = new ArrayList<String>();
        ResultSet catalogs = null;
        try {
            catalogs = ((AbstractJdbcDatabase)database).jdbcCallsCatalogsSchemas() ? ((JdbcConnection)database.getConnection()).getMetaData().getSchemas() : ((JdbcConnection)database.getConnection()).getMetaData().getCatalogs();
            if (catalogs == null) {
                String message = "Unable to retrieve the list of catalog names from the database metadata";
                Scope.getCurrentScope().getLog(this.getClass()).warning(message);
            } else {
                while (catalogs.next()) {
                    if (((AbstractJdbcDatabase)database).jdbcCallsCatalogsSchemas()) {
                        returnList.add(catalogs.getString("TABLE_SCHEM"));
                        continue;
                    }
                    returnList.add(catalogs.getString("TABLE_CAT"));
                }
            }
        }
        finally {
            if (catalogs != null) {
                try {
                    catalogs.close();
                }
                catch (SQLException sQLException) {}
            }
        }
        if (returnList.isEmpty()) {
            returnList.add(database.getDefaultCatalogName());
        }
        return returnList.toArray(new String[0]);
    }
}

