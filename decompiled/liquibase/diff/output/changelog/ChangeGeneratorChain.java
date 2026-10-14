/*
 * Decompiled with CFR 0.152.
 */
package liquibase.diff.output.changelog;

import java.util.Iterator;
import java.util.SortedSet;
import liquibase.change.Change;
import liquibase.database.Database;
import liquibase.diff.ObjectDifferences;
import liquibase.diff.output.DiffOutputControl;
import liquibase.diff.output.changelog.AbstractChangeGenerator;
import liquibase.diff.output.changelog.ChangeGenerator;
import liquibase.diff.output.changelog.ChangedObjectChangeGenerator;
import liquibase.diff.output.changelog.MissingObjectChangeGenerator;
import liquibase.diff.output.changelog.UnexpectedObjectChangeGenerator;
import liquibase.structure.DatabaseObject;

public class ChangeGeneratorChain {
    private Iterator<ChangeGenerator> changeGenerators;

    public ChangeGeneratorChain(SortedSet<ChangeGenerator> changeGenerators) {
        if (changeGenerators != null) {
            this.changeGenerators = changeGenerators.iterator();
        }
    }

    public Change[] fixMissing(DatabaseObject missingObject, DiffOutputControl control, Database referenceDatabase, Database comparisionDatabase) {
        if (missingObject == null) {
            return null;
        }
        if (control.getObjectChangeFilter() != null && !control.getObjectChangeFilter().includeMissing(missingObject, referenceDatabase, comparisionDatabase)) {
            return null;
        }
        if (this.changeGenerators == null) {
            return null;
        }
        if (!this.changeGenerators.hasNext()) {
            return null;
        }
        if (control.alreadyHandledMissing(missingObject, comparisionDatabase)) {
            return null;
        }
        MissingObjectChangeGenerator changeGenerator = (MissingObjectChangeGenerator)this.changeGenerators.next();
        Change[] changes = changeGenerator.fixMissing(missingObject, control, referenceDatabase, comparisionDatabase, this);
        if (changes == null) {
            return null;
        }
        if (changes.length == 0) {
            return null;
        }
        changes = changeGenerator.fixSchema(changes, control.getSchemaComparisons());
        this.respectSchemaAndCatalogCaseIfNeeded(control, changeGenerator);
        changes = changeGenerator.fixOutputAsSchema(changes, control.getSchemaComparisons());
        return changes;
    }

    public Change[] fixUnexpected(DatabaseObject unexpectedObject, DiffOutputControl control, Database referenceDatabase, Database comparisionDatabase) {
        if (unexpectedObject == null) {
            return null;
        }
        if (control.getObjectChangeFilter() != null && !control.getObjectChangeFilter().includeUnexpected(unexpectedObject, referenceDatabase, comparisionDatabase)) {
            return null;
        }
        if (this.changeGenerators == null) {
            return null;
        }
        if (!this.changeGenerators.hasNext()) {
            return null;
        }
        if (control.alreadyHandledUnexpected(unexpectedObject, comparisionDatabase)) {
            return null;
        }
        UnexpectedObjectChangeGenerator changeGenerator = (UnexpectedObjectChangeGenerator)this.changeGenerators.next();
        Change[] changes = changeGenerator.fixUnexpected(unexpectedObject, control, referenceDatabase, comparisionDatabase, this);
        if (changes == null) {
            return null;
        }
        if (changes.length == 0) {
            return null;
        }
        changes = changeGenerator.fixSchema(changes, control.getSchemaComparisons());
        this.respectSchemaAndCatalogCaseIfNeeded(control, changeGenerator);
        changes = changeGenerator.fixOutputAsSchema(changes, control.getSchemaComparisons());
        return changes;
    }

    private void respectSchemaAndCatalogCaseIfNeeded(DiffOutputControl control, ChangeGenerator changeGenerator) {
        if (changeGenerator instanceof AbstractChangeGenerator) {
            AbstractChangeGenerator abstractChangeGenerator = (AbstractChangeGenerator)changeGenerator;
            abstractChangeGenerator.setRespectSchemaAndCatalogCase(control.shouldRespectSchemaAndCatalogCase());
        }
    }

    public Change[] fixChanged(DatabaseObject changedObject, ObjectDifferences differences, DiffOutputControl control, Database referenceDatabase, Database comparisionDatabase) {
        if (changedObject == null) {
            return null;
        }
        if (control.getObjectChangeFilter() != null && !control.getObjectChangeFilter().includeChanged(changedObject, differences, referenceDatabase, comparisionDatabase)) {
            return null;
        }
        if (this.changeGenerators == null) {
            return null;
        }
        if (!this.changeGenerators.hasNext()) {
            return null;
        }
        if (control.alreadyHandledChanged(changedObject, comparisionDatabase)) {
            return null;
        }
        ChangedObjectChangeGenerator changeGenerator = (ChangedObjectChangeGenerator)this.changeGenerators.next();
        Change[] changes = changeGenerator.fixChanged(changedObject, differences, control, referenceDatabase, comparisionDatabase, this);
        if (changes == null) {
            return null;
        }
        if (changes.length == 0) {
            return null;
        }
        changes = changeGenerator.fixSchema(changes, control.getSchemaComparisons());
        this.respectSchemaAndCatalogCaseIfNeeded(control, changeGenerator);
        changes = changeGenerator.fixOutputAsSchema(changes, control.getSchemaComparisons());
        return changes;
    }
}

