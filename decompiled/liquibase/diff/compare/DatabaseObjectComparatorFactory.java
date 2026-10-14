/*
 * Decompiled with CFR 0.152.
 */
package liquibase.diff.compare;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import liquibase.Scope;
import liquibase.database.Database;
import liquibase.diff.ObjectDifferences;
import liquibase.diff.compare.CompareControl;
import liquibase.diff.compare.DatabaseObjectComparator;
import liquibase.diff.compare.DatabaseObjectComparatorChain;
import liquibase.diff.compare.DatabaseObjectComparatorComparator;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.structure.DatabaseObject;
import liquibase.structure.core.Catalog;
import liquibase.structure.core.Schema;
import liquibase.util.StringUtil;

public class DatabaseObjectComparatorFactory {
    private static DatabaseObjectComparatorFactory instance;
    private final List<DatabaseObjectComparator> comparators = new ArrayList<DatabaseObjectComparator>();
    private final Map<String, List<DatabaseObjectComparator>> validComparatorsByClassAndDatabase = new HashMap<String, List<DatabaseObjectComparator>>();
    private final Map<String, DatabaseObjectComparatorChain> comparatorChainsByClassAndDatabase = new HashMap<String, DatabaseObjectComparatorChain>();

    private DatabaseObjectComparatorFactory() {
        try {
            for (DatabaseObjectComparator comparator : Scope.getCurrentScope().getServiceLocator().findInstances(DatabaseObjectComparator.class)) {
                this.register(comparator);
            }
        }
        catch (Exception e) {
            throw new UnexpectedLiquibaseException(e);
        }
    }

    public static synchronized DatabaseObjectComparatorFactory getInstance() {
        if (instance == null) {
            instance = new DatabaseObjectComparatorFactory();
        }
        return instance;
    }

    public static synchronized void reset() {
        instance = new DatabaseObjectComparatorFactory();
    }

    public static synchronized void resetAll() {
        instance = null;
    }

    public void register(DatabaseObjectComparator generator) {
        this.comparators.add(generator);
    }

    public void unregister(DatabaseObjectComparator generator) {
        this.comparators.remove(generator);
    }

    public void unregister(Class generatorClass) {
        DatabaseObjectComparator toRemove = null;
        for (DatabaseObjectComparator existingGenerator : this.comparators) {
            if (!existingGenerator.getClass().equals(generatorClass)) continue;
            toRemove = existingGenerator;
        }
        this.unregister(toRemove);
    }

    public DatabaseObjectComparator getComparator(Class<? extends DatabaseObject> comparatorClass, Database database) {
        List<DatabaseObjectComparator> comparatorsForType = this.getComparators(comparatorClass, database);
        if (!comparatorsForType.isEmpty()) {
            return comparatorsForType.get(0);
        }
        return null;
    }

    protected List<DatabaseObjectComparator> getComparators(Class<? extends DatabaseObject> comparatorClass, Database database) {
        String key = comparatorClass.getName() + ":" + database.getShortName();
        if (this.validComparatorsByClassAndDatabase.containsKey(key)) {
            return this.validComparatorsByClassAndDatabase.get(key);
        }
        ArrayList<DatabaseObjectComparator> validComparators = new ArrayList<DatabaseObjectComparator>();
        for (DatabaseObjectComparator comparator : this.comparators) {
            if (comparator.getPriority(comparatorClass, database) <= 0) continue;
            validComparators.add(comparator);
        }
        validComparators.sort(new DatabaseObjectComparatorComparator(comparatorClass, database));
        this.validComparatorsByClassAndDatabase.put(key, validComparators);
        return validComparators;
    }

    public boolean isSameObject(DatabaseObject object1, DatabaseObject object2, CompareControl.SchemaComparison[] schemaComparisons, Database accordingTo) {
        if (object1 == null && object2 == null) {
            return true;
        }
        if (object1 instanceof Schema || object2 instanceof Schema) {
            if (object1 == null) {
                object1 = new Schema();
            }
            if (object2 == null) {
                object2 = new Schema();
            }
        } else if (object1 instanceof Catalog || object2 instanceof Catalog) {
            if (object1 == null) {
                object1 = new Catalog();
            }
            if (object2 == null) {
                object2 = new Catalog();
            }
        }
        if (object1 == null || object2 == null) {
            return false;
        }
        String snapshotId1 = object1.getSnapshotId();
        String snapshotId2 = object2.getSnapshotId();
        if (snapshotId1 != null && snapshotId2 != null && snapshotId1.equals(snapshotId2)) {
            return true;
        }
        boolean aHashMatches = false;
        List<String> hash1 = Arrays.asList(this.hash(object1, schemaComparisons, accordingTo));
        List<String> hash2 = Arrays.asList(this.hash(object2, schemaComparisons, accordingTo));
        for (String hash : hash1) {
            if (!hash2.contains(hash)) continue;
            aHashMatches = true;
            break;
        }
        if (!aHashMatches) {
            return false;
        }
        DatabaseObjectComparatorChain comparatorChain = this.createComparatorChain(object1.getClass(), schemaComparisons, accordingTo);
        if (comparatorChain != null) {
            return comparatorChain.isSameObject(object1, object2, accordingTo);
        }
        return false;
    }

    public String[] hash(DatabaseObject databaseObject, CompareControl.SchemaComparison[] schemaComparisons, Database accordingTo) {
        DatabaseObjectComparatorChain comparatorChain;
        String[] hash = null;
        if (databaseObject != null && (comparatorChain = this.createComparatorChain(databaseObject.getClass(), schemaComparisons, accordingTo)) != null) {
            hash = comparatorChain.hash(databaseObject, accordingTo);
        }
        if (hash == null) {
            hash = new String[]{"null"};
        }
        for (int i = 0; i < hash.length; ++i) {
            if (StringUtil.trimToNull(hash[i]) != null) continue;
            hash[i] = "null";
        }
        return hash;
    }

    public ObjectDifferences findDifferences(DatabaseObject object1, DatabaseObject object2, Database accordingTo, CompareControl compareControl) {
        DatabaseObjectComparatorChain comparatorChain = this.createComparatorChain(object1.getClass(), compareControl.getSchemaComparisons(), accordingTo);
        if (comparatorChain != null) {
            return comparatorChain.findDifferences(object1, object2, accordingTo, compareControl, new HashSet<String>());
        }
        return null;
    }

    private DatabaseObjectComparatorChain createComparatorChain(Class<? extends DatabaseObject> databaseObjectType, CompareControl.SchemaComparison[] schemaComparisons, Database database) {
        String key = databaseObjectType.getName() + ":" + database.getShortName();
        if (this.comparatorChainsByClassAndDatabase.containsKey(key)) {
            DatabaseObjectComparatorChain copy = this.comparatorChainsByClassAndDatabase.get(key).copy();
            copy.setSchemaComparisons(schemaComparisons);
            return copy;
        }
        List<DatabaseObjectComparator> comparators = DatabaseObjectComparatorFactory.getInstance().getComparators(databaseObjectType, database);
        if (comparators == null || comparators.isEmpty()) {
            return null;
        }
        DatabaseObjectComparatorChain chain = new DatabaseObjectComparatorChain(comparators, null);
        this.comparatorChainsByClassAndDatabase.put(key, chain);
        chain = chain.copy();
        chain.setSchemaComparisons(schemaComparisons);
        return chain;
    }
}

