/*
 * Decompiled with CFR 0.152.
 */
package liquibase.diff;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import liquibase.database.Database;
import liquibase.diff.Difference;
import liquibase.diff.compare.CompareControl;
import liquibase.diff.compare.DatabaseObjectComparatorFactory;
import liquibase.structure.DatabaseObject;
import liquibase.structure.core.DataType;

public class ObjectDifferences {
    private final CompareControl compareControl;
    private final HashMap<String, Difference> differences = new HashMap();

    public ObjectDifferences(CompareControl compareControl) {
        this.compareControl = compareControl;
    }

    public Set<Difference> getDifferences() {
        return Collections.unmodifiableSet(new TreeSet<Difference>(this.differences.values()));
    }

    public Difference getDifference(String field2) {
        return this.differences.get(field2);
    }

    public boolean isDifferent(String field2) {
        return this.differences.containsKey(field2);
    }

    public ObjectDifferences addDifference(String changedField, Object referenceValue, Object compareToValue) {
        this.differences.put(changedField, new Difference(changedField, referenceValue, compareToValue));
        return this;
    }

    public ObjectDifferences addDifference(String message, String changedField, Object referenceValue, Object compareToValue) {
        this.differences.put(changedField, new Difference(message, changedField, referenceValue, compareToValue));
        return this;
    }

    public boolean hasDifferences() {
        return !this.differences.isEmpty();
    }

    public void compare(String attribute, DatabaseObject referenceObject, DatabaseObject compareToObject, CompareFunction compareFunction) {
        this.compare(null, attribute, referenceObject, compareToObject, compareFunction);
    }

    public void compare(String message, String attribute, DatabaseObject referenceObject, DatabaseObject compareToObject, CompareFunction compareFunction) {
        boolean different;
        if (this.compareControl.isSuppressedField(referenceObject.getClass(), attribute)) {
            return;
        }
        Object referenceValue = referenceObject.getAttribute(attribute, Object.class);
        Object compareValue = compareToObject.getAttribute(attribute, Object.class);
        referenceValue = this.undoCollection(referenceValue, compareValue);
        compareValue = this.undoCollection(compareValue, referenceValue);
        if (referenceValue == null && compareValue == null) {
            different = false;
        } else if (referenceValue == null && compareValue != null || referenceValue != null && compareValue == null) {
            different = true;
        } else {
            boolean bl = different = !compareFunction.areEqual(referenceValue, compareValue);
        }
        if (different) {
            this.addDifference(message, attribute, referenceValue, compareValue);
        }
    }

    protected Object undoCollection(Object potentialCollection, Object otherObject) {
        if (otherObject != null && potentialCollection instanceof Collection && !(otherObject instanceof Collection) && ((Collection)potentialCollection).size() == 1 && ((Collection)potentialCollection).iterator().next().getClass().equals(otherObject.getClass())) {
            potentialCollection = ((Collection)potentialCollection).iterator().next();
        }
        return potentialCollection;
    }

    public boolean removeDifference(String attribute) {
        return this.differences.remove(attribute) != null;
    }

    public CompareControl.SchemaComparison[] getSchemaComparisons() {
        return this.compareControl.getSchemaComparisons();
    }

    public static interface CompareFunction {
        public boolean areEqual(Object var1, Object var2);
    }

    public static class UnOrderedCollectionCompareFunction
    implements CompareFunction {
        private final StandardCompareFunction compareFunction;

        public UnOrderedCollectionCompareFunction(StandardCompareFunction compareFunction) {
            this.compareFunction = compareFunction;
        }

        @Override
        public boolean areEqual(Object referenceValue, Object compareToValue) {
            if (referenceValue == null && compareToValue == null) {
                return true;
            }
            if (referenceValue == null || compareToValue == null) {
                return false;
            }
            if (!(referenceValue instanceof Collection) || !(compareToValue instanceof Collection)) {
                return false;
            }
            if (((Collection)referenceValue).size() != ((Collection)compareToValue).size()) {
                return false;
            }
            for (Object referenceObj : (Collection)referenceValue) {
                Object foundMatch = null;
                ArrayList unmatchedCompareToValues = new ArrayList((Collection)compareToValue);
                for (Object compareObj : unmatchedCompareToValues) {
                    if (!this.compareFunction.areEqual(referenceObj, compareObj)) continue;
                    foundMatch = compareObj;
                    break;
                }
                if (foundMatch == null) {
                    return false;
                }
                unmatchedCompareToValues.remove(foundMatch);
            }
            return true;
        }
    }

    public static class OrderedCollectionCompareFunction
    implements CompareFunction {
        private final StandardCompareFunction compareFunction;

        public OrderedCollectionCompareFunction(StandardCompareFunction compareFunction) {
            this.compareFunction = compareFunction;
        }

        @Override
        public boolean areEqual(Object referenceValue, Object compareToValue) {
            if (referenceValue == null && compareToValue == null) {
                return true;
            }
            if (referenceValue == null || compareToValue == null) {
                return false;
            }
            if (!(referenceValue instanceof Collection) || !(compareToValue instanceof Collection)) {
                return false;
            }
            if (((Collection)referenceValue).size() != ((Collection)compareToValue).size()) {
                return false;
            }
            Iterator referenceIterator = ((Collection)referenceValue).iterator();
            Iterator compareIterator = ((Collection)compareToValue).iterator();
            if (((Collection)referenceValue).size() != ((Collection)compareToValue).size()) {
                return false;
            }
            while (referenceIterator.hasNext()) {
                Object compareObj;
                Object referenceObj = referenceIterator.next();
                if (this.compareFunction.areEqual(referenceObj, compareObj = compareIterator.next())) continue;
                return false;
            }
            return true;
        }
    }

    public static class DataTypeCompareFunction
    implements CompareFunction {
        private final Database accordingTo;

        public DataTypeCompareFunction(Database accordingTo) {
            this.accordingTo = accordingTo;
        }

        @Override
        public boolean areEqual(Object referenceValue, Object compareToValue) {
            if (referenceValue == null && compareToValue == null) {
                return true;
            }
            if (referenceValue == null || compareToValue == null) {
                return false;
            }
            DataType referenceType = (DataType)referenceValue;
            DataType compareToType = (DataType)compareToValue;
            if (!referenceType.getTypeName().equalsIgnoreCase(compareToType.getTypeName())) {
                return false;
            }
            if (compareToType.toString().contains("(") && referenceType.toString().contains("(")) {
                return compareToType.toString().equalsIgnoreCase(referenceType.toString());
            }
            return true;
        }
    }

    public static class DatabaseObjectNameCompareFunction
    implements CompareFunction {
        private final Database accordingTo;
        private final Class<? extends DatabaseObject> type;

        public DatabaseObjectNameCompareFunction(Class<? extends DatabaseObject> type, Database accordingTo) {
            this.type = type;
            this.accordingTo = accordingTo;
        }

        @Override
        public boolean areEqual(Object referenceValue, Object compareToValue) {
            if (referenceValue instanceof Collection) {
                if (!(compareToValue instanceof Collection)) {
                    return false;
                }
                if (((Collection)referenceValue).size() != ((Collection)compareToValue).size()) {
                    return false;
                }
                Iterator referenceIterator = ((Collection)referenceValue).iterator();
                Iterator compareToIterator = ((Collection)compareToValue).iterator();
                while (referenceIterator.hasNext()) {
                    if (this.areEqual(referenceIterator.next(), compareToIterator.next())) continue;
                    return false;
                }
                return true;
            }
            if (referenceValue == null && compareToValue == null) {
                return true;
            }
            if (referenceValue == null || compareToValue == null) {
                return false;
            }
            String object1Name = referenceValue instanceof DatabaseObject ? this.accordingTo.correctObjectName((String)((Object)((DatabaseObject)referenceValue).getAttribute("name", String.class)), this.type) : referenceValue.toString();
            String object2Name = compareToValue instanceof DatabaseObject ? this.accordingTo.correctObjectName((String)((Object)((DatabaseObject)compareToValue).getAttribute("name", String.class)), this.type) : compareToValue.toString();
            if (object1Name == null && object2Name == null) {
                return true;
            }
            if (object1Name == null || object2Name == null) {
                return false;
            }
            if (this.accordingTo.isCaseSensitive()) {
                return object1Name.equals(object2Name);
            }
            return object1Name.equalsIgnoreCase(object2Name);
        }
    }

    public static class ToStringCompareFunction
    implements CompareFunction {
        private final boolean caseSensitive;

        public ToStringCompareFunction(boolean caseSensitive) {
            this.caseSensitive = caseSensitive;
        }

        @Override
        public boolean areEqual(Object referenceValue, Object compareToValue) {
            if (referenceValue == null && compareToValue == null) {
                return true;
            }
            if (referenceValue == null || compareToValue == null) {
                return false;
            }
            if (this.caseSensitive) {
                return referenceValue.toString().equals(compareToValue.toString());
            }
            return referenceValue.toString().equalsIgnoreCase(compareToValue.toString());
        }
    }

    public static class StandardCompareFunction
    implements CompareFunction {
        private final CompareControl.SchemaComparison[] schemaComparisons;
        private final Database accordingTo;

        public StandardCompareFunction(CompareControl.SchemaComparison[] schemaComparisons, Database accordingTo) {
            this.schemaComparisons = schemaComparisons;
            this.accordingTo = accordingTo;
        }

        @Override
        public boolean areEqual(Object referenceValue, Object compareToValue) {
            if (referenceValue == null && compareToValue == null) {
                return true;
            }
            if (referenceValue == null || compareToValue == null) {
                return false;
            }
            if (referenceValue instanceof DatabaseObject && compareToValue instanceof DatabaseObject) {
                return DatabaseObjectComparatorFactory.getInstance().isSameObject((DatabaseObject)referenceValue, (DatabaseObject)compareToValue, this.schemaComparisons, this.accordingTo);
            }
            if (referenceValue instanceof Number && compareToValue instanceof Number && !referenceValue.getClass().equals(compareToValue.getClass())) {
                referenceValue = new BigDecimal(referenceValue.toString());
                compareToValue = new BigDecimal(compareToValue.toString());
            }
            if (referenceValue instanceof Number && referenceValue instanceof Comparable) {
                return compareToValue instanceof Number && ((Comparable)referenceValue).compareTo(compareToValue) == 0;
            }
            return referenceValue.equals(compareToValue);
        }
    }
}

