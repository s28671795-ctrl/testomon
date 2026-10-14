/*
 * Decompiled with CFR 0.152.
 */
package liquibase.snapshot;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import liquibase.CatalogAndSchema;
import liquibase.Scope;
import liquibase.database.Database;
import liquibase.database.DatabaseConnection;
import liquibase.database.LiquibaseTableNamesFactory;
import liquibase.database.ObjectQuotingStrategy;
import liquibase.database.OfflineConnection;
import liquibase.database.core.PostgresDatabase;
import liquibase.diff.compare.DatabaseObjectComparatorFactory;
import liquibase.exception.DatabaseException;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.executor.ExecutorService;
import liquibase.snapshot.DatabaseSnapshot;
import liquibase.snapshot.EmptyDatabaseSnapshot;
import liquibase.snapshot.InvalidExampleException;
import liquibase.snapshot.JdbcDatabaseSnapshot;
import liquibase.snapshot.SnapshotControl;
import liquibase.snapshot.SnapshotGenerator;
import liquibase.snapshot.SnapshotGeneratorComparator;
import liquibase.statement.core.RawSqlStatement;
import liquibase.structure.DatabaseObject;
import liquibase.structure.core.Schema;
import liquibase.structure.core.Table;

public class SnapshotGeneratorFactory {
    private static SnapshotGeneratorFactory instance;
    private final List<SnapshotGenerator> generators = new ArrayList<SnapshotGenerator>();

    protected SnapshotGeneratorFactory() {
        try {
            for (SnapshotGenerator generator : Scope.getCurrentScope().getServiceLocator().findInstances(SnapshotGenerator.class)) {
                this.register(generator);
            }
        }
        catch (Exception e) {
            throw new UnexpectedLiquibaseException(e);
        }
    }

    public static synchronized SnapshotGeneratorFactory getInstance() {
        if (instance == null) {
            instance = new SnapshotGeneratorFactory();
        }
        return instance;
    }

    public static synchronized void reset() {
        instance = new SnapshotGeneratorFactory();
    }

    public static synchronized void resetAll() {
        instance = null;
    }

    public void register(SnapshotGenerator generator) {
        this.generators.add(generator);
    }

    public void unregister(SnapshotGenerator generator) {
        this.generators.remove(generator);
    }

    public void unregister(Class generatorClass) {
        SnapshotGenerator toRemove = null;
        for (SnapshotGenerator existingGenerator : this.generators) {
            if (!existingGenerator.getClass().equals(generatorClass)) continue;
            toRemove = existingGenerator;
        }
        this.unregister(toRemove);
    }

    protected SortedSet<SnapshotGenerator> getGenerators(Class<? extends DatabaseObject> generatorClass, Database database) {
        TreeSet<SnapshotGenerator> validGenerators = new TreeSet<SnapshotGenerator>(new SnapshotGeneratorComparator(generatorClass, database));
        for (SnapshotGenerator generator : this.generators) {
            if (generator.getPriority(generatorClass, database) <= 0) continue;
            validGenerators.add(generator);
        }
        return validGenerators;
    }

    public boolean has(DatabaseObject example, Database database) throws DatabaseException, InvalidExampleException {
        ArrayList<Class<? extends DatabaseObject>> types = new ArrayList<Class<? extends DatabaseObject>>(this.getContainerTypes(example.getClass(), database));
        types.add(example.getClass());
        LiquibaseTableNamesFactory liquibaseTableNamesFactory = Scope.getCurrentScope().getSingleton(LiquibaseTableNamesFactory.class);
        List<String> liquibaseTableNames = liquibaseTableNamesFactory.getLiquibaseTableNames(database);
        if (example instanceof Table && liquibaseTableNames.stream().anyMatch(tableName -> example.getName().equals(tableName))) {
            try {
                Scope.getCurrentScope().getSingleton(ExecutorService.class).getExecutor("jdbc", database).queryForInt(new RawSqlStatement("SELECT COUNT(*) FROM " + database.escapeObjectName(database.getLiquibaseCatalogName(), database.getLiquibaseSchemaName(), example.getName(), Table.class)));
                return true;
            }
            catch (DatabaseException e) {
                if (database instanceof PostgresDatabase) {
                    database.rollback();
                }
                return false;
            }
        }
        SnapshotControl snapshotControl = new SnapshotControl(database, false, types.toArray(new Class[0]));
        snapshotControl.setWarnIfObjectNotFound(false);
        if (this.createSnapshot(example, database, snapshotControl) != null) {
            return true;
        }
        CatalogAndSchema catalogAndSchema = example.getSchema() == null ? database.getDefaultSchema() : example.getSchema().toCatalogAndSchema();
        DatabaseSnapshot snapshot = this.createSnapshot(catalogAndSchema, database, new SnapshotControl(database, false, example.getClass()).setWarnIfObjectNotFound(false));
        for (DatabaseObject obj : snapshot.get(example.getClass())) {
            if (!DatabaseObjectComparatorFactory.getInstance().isSameObject(example, obj, null, database)) continue;
            return true;
        }
        return false;
    }

    public DatabaseSnapshot createSnapshot(CatalogAndSchema example, Database database, SnapshotControl snapshotControl) throws DatabaseException, InvalidExampleException {
        return this.createSnapshot(new CatalogAndSchema[]{example}, database, snapshotControl);
    }

    public DatabaseSnapshot createSnapshot(CatalogAndSchema[] examples, Database database, SnapshotControl snapshotControl) throws DatabaseException, InvalidExampleException {
        if (database == null) {
            return null;
        }
        DatabaseObject[] schemas = new Schema[examples.length];
        for (int i = 0; i < schemas.length; ++i) {
            examples[i] = examples[i].customize(database);
            schemas[i] = new Schema(examples[i].getCatalogName(), examples[i].getSchemaName());
        }
        Scope.getCurrentScope().getLog(SnapshotGeneratorFactory.class).info("Creating snapshot");
        return this.createSnapshot(schemas, database, snapshotControl);
    }

    public DatabaseSnapshot createSnapshot(DatabaseObject[] examples, Database database, SnapshotControl snapshotControl) throws DatabaseException, InvalidExampleException {
        DatabaseConnection conn = database.getConnection();
        if (conn == null) {
            return new EmptyDatabaseSnapshot(database, snapshotControl);
        }
        if (conn instanceof OfflineConnection) {
            DatabaseSnapshot snapshot = ((OfflineConnection)conn).getSnapshot(examples);
            if (snapshot == null) {
                throw new DatabaseException("No snapshotFile parameter specified for offline database");
            }
            return snapshot;
        }
        return new JdbcDatabaseSnapshot(examples, database, snapshotControl);
    }

    public <T extends DatabaseObject> T createSnapshot(T example, Database database) throws DatabaseException, InvalidExampleException {
        return this.createSnapshot(example, database, new SnapshotControl(database));
    }

    public <T extends DatabaseObject> T createSnapshot(T example, Database database, SnapshotControl snapshotControl) throws DatabaseException, InvalidExampleException {
        DatabaseSnapshot snapshot = this.createSnapshot(new DatabaseObject[]{example}, database, snapshotControl);
        return snapshot.get(example);
    }

    public Table getDatabaseChangeLogTable(SnapshotControl snapshotControl, Database database) throws DatabaseException {
        ObjectQuotingStrategy currentStrategy = database.getObjectQuotingStrategy();
        database.setObjectQuotingStrategy(ObjectQuotingStrategy.LEGACY);
        try {
            Table liquibaseTable = (Table)new Table().setName(database.getDatabaseChangeLogTableName()).setSchema(new Schema(database.getLiquibaseCatalogName(), database.getLiquibaseSchemaName()));
            Table table = this.createSnapshot(liquibaseTable, database, snapshotControl);
            return table;
        }
        catch (InvalidExampleException e) {
            throw new UnexpectedLiquibaseException(e);
        }
        finally {
            database.setObjectQuotingStrategy(currentStrategy);
        }
    }

    public Table getDatabaseChangeLogLockTable(Database database) throws DatabaseException {
        ObjectQuotingStrategy currentStrategy = database.getObjectQuotingStrategy();
        database.setObjectQuotingStrategy(ObjectQuotingStrategy.LEGACY);
        try {
            Table example = (Table)new Table().setName(database.getDatabaseChangeLogLockTableName()).setSchema(new Schema(database.getLiquibaseCatalogName(), database.getLiquibaseSchemaName()));
            Table table = this.createSnapshot(example, database);
            return table;
        }
        catch (InvalidExampleException e) {
            throw new UnexpectedLiquibaseException(e);
        }
        finally {
            database.setObjectQuotingStrategy(currentStrategy);
        }
    }

    public boolean hasDatabaseChangeLogTable(Database database) throws DatabaseException {
        ObjectQuotingStrategy currentStrategy = database.getObjectQuotingStrategy();
        database.setObjectQuotingStrategy(ObjectQuotingStrategy.LEGACY);
        try {
            boolean bl = this.has(new Table().setName(database.getDatabaseChangeLogTableName()).setSchema(new Schema(database.getLiquibaseCatalogName(), database.getLiquibaseSchemaName())), database);
            return bl;
        }
        catch (InvalidExampleException e) {
            throw new UnexpectedLiquibaseException(e);
        }
        finally {
            database.setObjectQuotingStrategy(currentStrategy);
        }
    }

    public boolean hasDatabaseChangeLogLockTable(Database database) throws DatabaseException {
        ObjectQuotingStrategy currentStrategy = database.getObjectQuotingStrategy();
        database.setObjectQuotingStrategy(ObjectQuotingStrategy.LEGACY);
        try {
            boolean bl = this.has(new Table().setName(database.getDatabaseChangeLogLockTableName()).setSchema(new Schema(database.getLiquibaseCatalogName(), database.getLiquibaseSchemaName())), database);
            return bl;
        }
        catch (InvalidExampleException e) {
            throw new UnexpectedLiquibaseException(e);
        }
        finally {
            database.setObjectQuotingStrategy(currentStrategy);
        }
    }

    public Set<Class<? extends DatabaseObject>> getContainerTypes(Class<? extends DatabaseObject> type, Database database) {
        HashSet<Class<? extends DatabaseObject>> returnSet = new HashSet<Class<? extends DatabaseObject>>();
        this.getContainerTypes(type, database, returnSet);
        return returnSet;
    }

    private void getContainerTypes(Class<? extends DatabaseObject> type, Database database, Set<Class<? extends DatabaseObject>> returnSet) {
        SnapshotGenerator generator;
        Class<? extends DatabaseObject>[] addsTo;
        if (!returnSet.add(type)) {
            return;
        }
        SortedSet<SnapshotGenerator> generators = this.getGenerators(type, database);
        if (generators != null && !generators.isEmpty() && (addsTo = (generator = (SnapshotGenerator)generators.iterator().next()).addsTo()) != null) {
            for (Class<? extends DatabaseObject> newType : addsTo) {
                returnSet.add(newType);
                this.getContainerTypes(newType, database, returnSet);
            }
        }
    }
}

