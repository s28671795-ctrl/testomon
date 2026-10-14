/*
 * Decompiled with CFR 0.152.
 */
package liquibase.structure;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import liquibase.database.Database;
import liquibase.diff.compare.CompareControl;
import liquibase.diff.compare.DatabaseObjectCollectionComparator;
import liquibase.diff.compare.DatabaseObjectComparatorFactory;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.parser.core.ParsedNode;
import liquibase.parser.core.ParsedNodeException;
import liquibase.resource.ResourceAccessor;
import liquibase.serializer.LiquibaseSerializable;
import liquibase.structure.DatabaseObject;

public class DatabaseObjectCollection
implements LiquibaseSerializable {
    private final Map<Class<? extends DatabaseObject>, Map<String, Set<DatabaseObject>>> cache = Collections.synchronizedMap(new LinkedHashMap());
    private final Database database;

    public DatabaseObjectCollection(Database database) {
        this.database = database;
    }

    @Override
    public String getSerializedObjectName() {
        return "objects";
    }

    @Override
    public String getSerializedObjectNamespace() {
        return "http://www.liquibase.org/xml/ns/snapshot";
    }

    @Override
    public String getSerializableFieldNamespace(String field2) {
        return this.getSerializedObjectNamespace();
    }

    @Override
    public Set<String> getSerializableFields() {
        TreeSet<String> types = new TreeSet<String>();
        for (Class<? extends DatabaseObject> type : this.cache.keySet()) {
            types.add(type.getName());
        }
        return types;
    }

    @Override
    public Object getSerializableFieldValue(String field2) {
        TreeSet<DatabaseObject> objects = new TreeSet<DatabaseObject>(new DatabaseObjectCollectionComparator());
        try {
            Map<String, Set<DatabaseObject>> map = this.cache.get(Class.forName(field2));
            if (map == null) {
                return null;
            }
            for (Set<DatabaseObject> set : map.values()) {
                objects.addAll(set);
            }
            return objects;
        }
        catch (ClassNotFoundException e) {
            throw new UnexpectedLiquibaseException(e);
        }
    }

    @Override
    public LiquibaseSerializable.SerializationType getSerializableFieldType(String field2) {
        return LiquibaseSerializable.SerializationType.NAMED_FIELD;
    }

    public void add(DatabaseObject databaseObject) {
        String[] hashes;
        if (databaseObject == null) {
            return;
        }
        Map collectionMap = this.cache.computeIfAbsent(databaseObject.getClass(), k -> new ConcurrentHashMap());
        for (String hash : hashes = DatabaseObjectComparatorFactory.getInstance().hash(databaseObject, null, this.database)) {
            Set collection = collectionMap.computeIfAbsent(hash, k -> new HashSet());
            collection.add(databaseObject);
        }
    }

    public <DatabaseObjectType extends DatabaseObject> DatabaseObjectType get(DatabaseObjectType example, CompareControl.SchemaComparison[] schemaComparisons) {
        Map<String, Set<DatabaseObject>> databaseObjectsByHash = this.cache.get(example.getClass());
        if (databaseObjectsByHash == null) {
            return null;
        }
        String[] hashes = DatabaseObjectComparatorFactory.getInstance().hash(example, null, this.database);
        TreeSet<Set<DatabaseObject>> objectSets = new TreeSet<Set<DatabaseObject>>((o1, o2) -> {
            int sizeComparison = Integer.compare(o1.size(), o2.size());
            if (sizeComparison == 0) {
                return o1.toString().compareTo(o2.toString());
            }
            return sizeComparison;
        });
        for (String hash : hashes) {
            Set<DatabaseObject> databaseObjects = databaseObjectsByHash.get(hash);
            if (databaseObjects == null) continue;
            objectSets.add(databaseObjects);
        }
        for (Set set : objectSets) {
            for (DatabaseObject obj : set) {
                if (!DatabaseObjectComparatorFactory.getInstance().isSameObject(obj, example, schemaComparisons, this.database)) continue;
                return (DatabaseObjectType)obj;
            }
        }
        return null;
    }

    public <DatabaseObjectType extends DatabaseObject> Set<DatabaseObjectType> get(Class<DatabaseObjectType> type) {
        HashSet<DatabaseObject> returnSet = new HashSet<DatabaseObject>();
        Map<String, Set<DatabaseObject>> allFound = this.cache.get(type);
        if (allFound != null) {
            for (Set<DatabaseObject> objects : allFound.values()) {
                returnSet.addAll(objects);
            }
        }
        return Collections.unmodifiableSet(returnSet);
    }

    public boolean contains(DatabaseObject wantedObject, CompareControl.SchemaComparison[] schemaComparisons) {
        return this.get(wantedObject, schemaComparisons) != null;
    }

    @Override
    public void load(ParsedNode parsedNode, ResourceAccessor resourceAccessor) throws ParsedNodeException {
        throw new RuntimeException("TODO");
    }

    @Override
    public ParsedNode serialize() {
        throw new RuntimeException("TODO");
    }

    public Map<Class<? extends DatabaseObject>, Set<? extends DatabaseObject>> toMap() {
        Map<Class<? extends DatabaseObject>, Set<? extends DatabaseObject>> returnMap = Collections.synchronizedMap(new LinkedHashMap());
        for (Class<? extends DatabaseObject> type : this.cache.keySet()) {
            returnMap.put(type, this.get(type));
        }
        return returnMap;
    }
}

