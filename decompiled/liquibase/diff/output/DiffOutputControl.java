/*
 * Decompiled with CFR 0.152.
 */
package liquibase.diff.output;

import java.util.HashSet;
import java.util.Set;
import liquibase.CatalogAndSchema;
import liquibase.ContextExpression;
import liquibase.Labels;
import liquibase.database.Database;
import liquibase.database.InternalDatabase;
import liquibase.database.ObjectQuotingStrategy;
import liquibase.database.core.H2Database;
import liquibase.diff.compare.CompareControl;
import liquibase.diff.output.ObjectChangeFilter;
import liquibase.diff.output.changelog.ChangeGeneratorFactory;
import liquibase.diff.output.changelog.core.MissingDataExternalFileChangeGenerator;
import liquibase.servicelocator.LiquibaseService;
import liquibase.structure.DatabaseObject;
import liquibase.structure.DatabaseObjectCollection;
import liquibase.structure.core.Schema;

public class DiffOutputControl {
    private final Set<CatalogAndSchema> includeSchemas = new HashSet<CatalogAndSchema>();
    private boolean includeSchema;
    private boolean includeCatalog;
    private boolean includeTablespace;
    private boolean isReplaceIfExistsSet = false;
    private CompareControl.SchemaComparison[] schemaComparisons;
    private final DatabaseObjectCollection alreadyHandledMissing = new DatabaseObjectCollection(new DatabaseForHash());
    private final DatabaseObjectCollection alreadyHandledUnexpected = new DatabaseObjectCollection(new DatabaseForHash());
    private final DatabaseObjectCollection alreadyHandledChanged = new DatabaseObjectCollection(new DatabaseForHash());
    private ObjectQuotingStrategy objectQuotingStrategy;
    private ContextExpression context;
    private Labels labels;
    private ObjectChangeFilter objectChangeFilter;
    private boolean respectSchemaAndCatalogCase = false;
    private boolean considerCatalogsAsSchemas = false;

    public DiffOutputControl() {
        this.includeSchema = true;
        this.includeCatalog = true;
        this.includeTablespace = true;
    }

    public DiffOutputControl(boolean includeCatalog, boolean includeSchema, boolean includeTablespace, CompareControl.SchemaComparison[] schemaComparisons) {
        this.includeSchema = includeSchema;
        this.includeCatalog = includeCatalog;
        this.includeTablespace = includeTablespace;
        this.schemaComparisons = schemaComparisons;
    }

    public CompareControl.SchemaComparison[] getSchemaComparisons() {
        return this.schemaComparisons;
    }

    public boolean getIncludeSchema() {
        return this.includeSchema;
    }

    public DiffOutputControl setIncludeSchema(boolean includeSchema) {
        this.includeSchema = includeSchema;
        return this;
    }

    public boolean getIncludeCatalog() {
        return this.includeCatalog;
    }

    public DiffOutputControl setIncludeCatalog(boolean includeCatalog) {
        this.includeCatalog = includeCatalog;
        return this;
    }

    public boolean getIncludeTablespace() {
        return this.includeTablespace;
    }

    public DiffOutputControl setIncludeTablespace(boolean includeTablespace) {
        this.includeTablespace = includeTablespace;
        return this;
    }

    public DiffOutputControl setDataDir(String dataDir) {
        if (dataDir != null) {
            ChangeGeneratorFactory.getInstance().register(new MissingDataExternalFileChangeGenerator(dataDir));
        }
        return this;
    }

    public void setAlreadyHandledMissing(DatabaseObject missingObject) {
        this.alreadyHandledMissing.add(missingObject);
    }

    public boolean alreadyHandledMissing(DatabaseObject missingObject, Database accordingTo) {
        return this.alreadyHandledMissing.contains(missingObject, this.schemaComparisons);
    }

    public void setAlreadyHandledUnexpected(DatabaseObject unexpectedObject) {
        this.alreadyHandledUnexpected.add(unexpectedObject);
    }

    public boolean alreadyHandledUnexpected(DatabaseObject unexpectedObject, Database accordingTo) {
        return this.alreadyHandledUnexpected.contains(unexpectedObject, this.schemaComparisons);
    }

    public void setAlreadyHandledChanged(DatabaseObject changedObject) {
        this.alreadyHandledChanged.add(changedObject);
    }

    public boolean alreadyHandledChanged(DatabaseObject changedObject, Database accordingTo) {
        return this.alreadyHandledChanged.contains(changedObject, this.schemaComparisons);
    }

    public DiffOutputControl addIncludedSchema(Schema schema) {
        this.includeSchemas.add(schema.toCatalogAndSchema());
        return this;
    }

    public DiffOutputControl addIncludedSchema(CatalogAndSchema schema) {
        this.includeSchemas.add(schema);
        return this;
    }

    public boolean shouldOutput(DatabaseObject object, Database accordingTo) {
        if (!this.includeSchemas.isEmpty()) {
            Schema schema = object.getSchema();
            if (schema == null) {
                return true;
            }
            CatalogAndSchema objectCatalogAndSchema = schema.toCatalogAndSchema().standardize(accordingTo);
            for (CatalogAndSchema catalogAndSchema : this.includeSchemas) {
                CatalogAndSchema stdCatalogAndSchema = catalogAndSchema.standardize(accordingTo);
                if (!objectCatalogAndSchema.equals(stdCatalogAndSchema, accordingTo)) continue;
                return true;
            }
            return false;
        }
        return true;
    }

    public ObjectChangeFilter getObjectChangeFilter() {
        return this.objectChangeFilter;
    }

    public DiffOutputControl setObjectChangeFilter(ObjectChangeFilter objectChangeFilter) {
        this.objectChangeFilter = objectChangeFilter;
        return this;
    }

    public ContextExpression getContext() {
        return this.context;
    }

    public DiffOutputControl setContext(ContextExpression contextExpression) {
        this.context = contextExpression;
        return this;
    }

    public Labels getLabels() {
        return this.labels;
    }

    public DiffOutputControl setLabels(Labels labels) {
        this.labels = labels;
        return this;
    }

    public ObjectQuotingStrategy getObjectQuotingStrategy() {
        return this.objectQuotingStrategy;
    }

    public DiffOutputControl setObjectQuotingStrategy(ObjectQuotingStrategy objectQuotingStrategy) {
        this.objectQuotingStrategy = objectQuotingStrategy;
        return this;
    }

    public boolean shouldRespectSchemaAndCatalogCase() {
        return this.respectSchemaAndCatalogCase;
    }

    public void setRespectSchemaAndCatalogCase(boolean respectSchemaAndCatalogCase) {
        this.respectSchemaAndCatalogCase = respectSchemaAndCatalogCase;
    }

    public boolean considerCatalogsAsSchemas() {
        return this.considerCatalogsAsSchemas;
    }

    public void setConsiderCatalogsAsSchemas(boolean considerCatalogsAsSchemas) {
        this.considerCatalogsAsSchemas = considerCatalogsAsSchemas;
    }

    public boolean isReplaceIfExistsSet() {
        return this.isReplaceIfExistsSet;
    }

    public void setReplaceIfExistsSet(boolean isReplaceIfExistsSet) {
        this.isReplaceIfExistsSet = isReplaceIfExistsSet;
    }

    @LiquibaseService(skip=true)
    private static class DatabaseForHash
    extends H2Database
    implements InternalDatabase {
        private DatabaseForHash() {
        }

        @Override
        public boolean isCaseSensitive() {
            return true;
        }
    }
}

