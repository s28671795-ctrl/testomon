/*
 * Decompiled with CFR 0.152.
 */
package liquibase.diff.compare;

import java.util.List;
import java.util.Set;
import liquibase.database.Database;
import liquibase.diff.ObjectDifferences;
import liquibase.diff.compare.CompareControl;
import liquibase.diff.compare.DatabaseObjectComparator;
import liquibase.structure.DatabaseObject;

public class DatabaseObjectComparatorChain {
    private CompareControl.SchemaComparison[] schemaComparisons;
    private final List<DatabaseObjectComparator> comparators;
    private int nextIndex;

    public DatabaseObjectComparatorChain(List<DatabaseObjectComparator> comparators, CompareControl.SchemaComparison[] schemaComparisons) {
        this.comparators = comparators;
        this.schemaComparisons = schemaComparisons;
    }

    protected DatabaseObjectComparatorChain copy() {
        return new DatabaseObjectComparatorChain(this.comparators, this.schemaComparisons);
    }

    public CompareControl.SchemaComparison[] getSchemaComparisons() {
        return this.schemaComparisons;
    }

    public boolean isSameObject(DatabaseObject object1, DatabaseObject object2, Database accordingTo) {
        if (object1 == null && object2 == null) {
            return true;
        }
        if (object1 == null && object2 != null) {
            return false;
        }
        if (object1 != null && object2 == null) {
            return false;
        }
        if (this.comparators == null) {
            return true;
        }
        DatabaseObjectComparator next = this.getNextComparator();
        if (next == null) {
            return true;
        }
        return next.isSameObject(object1, object2, accordingTo, this);
    }

    public String[] hash(DatabaseObject object, Database accordingTo) {
        if (object == null) {
            return null;
        }
        DatabaseObjectComparator next = this.getNextComparator();
        if (next == null) {
            return null;
        }
        return next.hash(object, accordingTo, this);
    }

    private DatabaseObjectComparator getNextComparator() {
        if (this.comparators == null) {
            return null;
        }
        if (this.nextIndex >= this.comparators.size()) {
            return null;
        }
        DatabaseObjectComparator next = this.comparators.get(this.nextIndex);
        ++this.nextIndex;
        return next;
    }

    public ObjectDifferences findDifferences(DatabaseObject object1, DatabaseObject object2, Database accordingTo, CompareControl compareControl, Set<String> exclude) {
        if (object1 == null && object2 == null) {
            return new ObjectDifferences(compareControl);
        }
        if (object1 == null && object2 != null) {
            return new ObjectDifferences(compareControl).addDifference("Reference value was null", "this", null, null);
        }
        if (object1 != null && object2 == null) {
            return new ObjectDifferences(compareControl).addDifference("Compared value was null", "this", null, null);
        }
        DatabaseObjectComparator next = this.getNextComparator();
        if (next == null) {
            return new ObjectDifferences(compareControl);
        }
        return next.findDifferences(object1, object2, accordingTo, compareControl, this, exclude);
    }

    public void setSchemaComparisons(CompareControl.SchemaComparison[] schemaComparisons) {
        this.schemaComparisons = schemaComparisons;
    }
}

