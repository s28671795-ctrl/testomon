/*
 * Decompiled with CFR 0.152.
 */
package liquibase.logging.mdc.customobjects;

import liquibase.diff.DiffResult;
import liquibase.diff.ObjectDifferences;
import liquibase.logging.mdc.CustomMdcObject;
import liquibase.structure.DatabaseObject;

public class DiffResultsSummary
implements CustomMdcObject {
    private int missing;
    private int unexpected;
    private int changed;

    public DiffResultsSummary() {
    }

    public DiffResultsSummary(DiffResult diffResult) {
        this.missing = 0;
        for (DatabaseObject databaseObject : diffResult.getMissingObjects()) {
            if (!diffResult.getReferenceSnapshot().getSnapshotControl().shouldInclude(databaseObject)) continue;
            ++this.missing;
        }
        this.unexpected = 0;
        for (DatabaseObject databaseObject : diffResult.getUnexpectedObjects()) {
            if (!diffResult.getReferenceSnapshot().getSnapshotControl().shouldInclude(databaseObject)) continue;
            ++this.unexpected;
        }
        this.changed = diffResult.getChangedObjects().entrySet().stream().filter(entry -> diffResult.getReferenceSnapshot().getSnapshotControl().shouldInclude((DatabaseObject)entry.getKey())).reduce(0, (subtotal, element) -> subtotal + ((ObjectDifferences)element.getValue()).getDifferences().size(), Integer::sum);
    }

    public int getMissing() {
        return this.missing;
    }

    public void setMissing(int missing) {
        this.missing = missing;
    }

    public int getUnexpected() {
        return this.unexpected;
    }

    public void setUnexpected(int unexpected) {
        this.unexpected = unexpected;
    }

    public int getChanged() {
        return this.changed;
    }

    public void setChanged(int changed) {
        this.changed = changed;
    }
}

