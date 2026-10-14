/*
 * Decompiled with CFR 0.152.
 */
package liquibase.snapshot.jvm;

import java.sql.SQLException;
import liquibase.CatalogAndSchema;
import liquibase.database.Database;
import liquibase.diff.compare.DatabaseObjectComparatorFactory;
import liquibase.exception.DatabaseException;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.snapshot.DatabaseSnapshot;
import liquibase.snapshot.InvalidExampleException;
import liquibase.snapshot.jvm.JdbcSnapshotGenerator;
import liquibase.structure.DatabaseObject;
import liquibase.structure.core.Catalog;

public class CatalogSnapshotGenerator
extends JdbcSnapshotGenerator {
    public CatalogSnapshotGenerator() {
        super(Catalog.class);
    }

    @Override
    protected DatabaseObject snapshotObject(DatabaseObject example, DatabaseSnapshot snapshot) throws DatabaseException, InvalidExampleException {
        if (!(example instanceof Catalog)) {
            throw new UnexpectedLiquibaseException("Unexpected example type: " + example.getClass().getName());
        }
        Database database = snapshot.getDatabase();
        Catalog match = null;
        String catalogName = example.getName();
        if (catalogName == null && database.supports(Catalog.class)) {
            catalogName = database.getDefaultCatalogName();
        }
        example = new Catalog(catalogName);
        try {
            for (String potentialCatalogName : this.getDatabaseCatalogNames(database)) {
                Catalog catalog = new Catalog(potentialCatalogName);
                if (!DatabaseObjectComparatorFactory.getInstance().isSameObject(catalog, example, snapshot.getSchemaComparisons(), database)) continue;
                if (match == null) {
                    match = catalog;
                    continue;
                }
                throw new InvalidExampleException("Found multiple catalogs matching " + example.getName());
            }
        }
        catch (SQLException e) {
            throw new DatabaseException(e);
        }
        if (match != null && this.isDefaultCatalog(match, database)) {
            match.setDefault(true);
        }
        return match;
    }

    protected boolean isDefaultCatalog(Catalog match, Database database) {
        if (CatalogAndSchema.CatalogAndSchemaCase.ORIGINAL_CASE.equals((Object)database.getSchemaAndCatalogCase())) {
            return match.getName() == null || match.getName().equals(database.getDefaultCatalogName());
        }
        return match.getName() == null || match.getName().equalsIgnoreCase(database.getDefaultCatalogName());
    }

    @Override
    protected void addTo(DatabaseObject foundObject, DatabaseSnapshot snapshot) throws DatabaseException, InvalidExampleException {
    }
}

