/*
 * Decompiled with CFR 0.152.
 */
package liquibase.diff;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import liquibase.Scope;
import liquibase.database.Database;
import liquibase.diff.DiffGenerator;
import liquibase.diff.DiffResult;
import liquibase.diff.compare.CompareControl;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.snapshot.DatabaseSnapshot;
import liquibase.snapshot.EmptyDatabaseSnapshot;
import liquibase.snapshot.InvalidExampleException;
import liquibase.snapshot.SnapshotControl;
import liquibase.snapshot.SnapshotGeneratorFactory;

public class DiffGeneratorFactory {
    private static DiffGeneratorFactory instance;
    private final List<DiffGenerator> implementedGenerators = new ArrayList<DiffGenerator>();

    protected DiffGeneratorFactory() {
        try {
            for (DiffGenerator diffGenerator : Scope.getCurrentScope().getServiceLocator().findInstances(DiffGenerator.class)) {
                this.register(diffGenerator);
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static synchronized DiffGeneratorFactory getInstance() {
        if (instance == null) {
            instance = new DiffGeneratorFactory();
        }
        return instance;
    }

    public void register(DiffGenerator generator) {
        this.implementedGenerators.add(0, generator);
    }

    public DiffGenerator getGenerator(Database referenceDatabase, Database comparisonDatabase) {
        TreeSet<DiffGenerator> foundGenerators = new TreeSet<DiffGenerator>((o1, o2) -> -1 * Integer.compare(o1.getPriority(), o2.getPriority()));
        for (DiffGenerator diffGenerator : this.implementedGenerators) {
            if (!diffGenerator.supports(referenceDatabase, comparisonDatabase)) continue;
            foundGenerators.add(diffGenerator);
        }
        if (foundGenerators.isEmpty()) {
            throw new UnexpectedLiquibaseException("Cannot find DiffGenerator for " + referenceDatabase.getShortName() + ", " + comparisonDatabase.getShortName());
        }
        try {
            return (DiffGenerator)((DiffGenerator)foundGenerators.iterator().next()).getClass().getConstructor(new Class[0]).newInstance(new Object[0]);
        }
        catch (Exception e) {
            throw new UnexpectedLiquibaseException(e);
        }
    }

    public DiffResult compare(Database referenceDatabase, Database comparisonDatabase, CompareControl compareControl) throws LiquibaseException {
        return this.compare(referenceDatabase, comparisonDatabase, new SnapshotControl(referenceDatabase), new SnapshotControl(comparisonDatabase), compareControl);
    }

    public DiffResult compare(Database referenceDatabase, Database comparisonDatabase, SnapshotControl referenceSnapshotControl, SnapshotControl comparisonSnapshotControl, CompareControl compareControl) throws LiquibaseException {
        DatabaseSnapshot referenceSnapshot = SnapshotGeneratorFactory.getInstance().createSnapshot(referenceDatabase.getDefaultSchema(), referenceDatabase, referenceSnapshotControl);
        DatabaseSnapshot comparisonSnapshot = null;
        comparisonSnapshot = comparisonDatabase == null ? new EmptyDatabaseSnapshot(referenceDatabase) : SnapshotGeneratorFactory.getInstance().createSnapshot(comparisonDatabase.getDefaultSchema(), comparisonDatabase, comparisonSnapshotControl);
        return this.getGenerator(referenceDatabase, comparisonDatabase).compare(referenceSnapshot, comparisonSnapshot, compareControl);
    }

    public DiffResult compare(DatabaseSnapshot referenceSnapshot, DatabaseSnapshot comparisonSnapshot, CompareControl compareControl) throws DatabaseException {
        Database comparisonDatabase;
        Database referenceDatabase = referenceSnapshot.getDatabase();
        if (comparisonSnapshot != null && referenceDatabase != null) {
            if (referenceDatabase.getDefaultCatalogName() == null || referenceDatabase.getDefaultCatalogName().isEmpty()) {
                referenceDatabase.setDefaultCatalogName(comparisonSnapshot.getDatabase().getDefaultCatalogName());
            }
            if (referenceDatabase.getDefaultSchemaName() == null || referenceDatabase.getDefaultSchemaName().isEmpty()) {
                referenceDatabase.setDefaultSchemaName(comparisonSnapshot.getDatabase().getDefaultSchemaName());
            }
        }
        if (comparisonSnapshot == null) {
            comparisonDatabase = referenceSnapshot.getDatabase();
            try {
                comparisonSnapshot = new EmptyDatabaseSnapshot(referenceDatabase, referenceSnapshot.getSnapshotControl());
            }
            catch (InvalidExampleException e) {
                throw new UnexpectedLiquibaseException(e);
            }
        } else {
            comparisonDatabase = comparisonSnapshot.getDatabase();
        }
        return this.getGenerator(referenceDatabase, comparisonDatabase).compare(referenceSnapshot, comparisonSnapshot, compareControl);
    }
}

