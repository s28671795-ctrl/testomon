/*
 * Decompiled with CFR 0.152.
 */
package liquibase.snapshot;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import liquibase.CatalogAndSchema;
import liquibase.GlobalConfiguration;
import liquibase.Scope;
import liquibase.database.Database;
import liquibase.database.DatabaseConnection;
import liquibase.database.OfflineConnection;
import liquibase.diff.compare.CompareControl;
import liquibase.diff.compare.DatabaseObjectComparatorFactory;
import liquibase.exception.DatabaseException;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.logging.Logger;
import liquibase.parser.core.ParsedNode;
import liquibase.parser.core.ParsedNodeException;
import liquibase.resource.ResourceAccessor;
import liquibase.serializer.LiquibaseSerializable;
import liquibase.snapshot.InvalidExampleException;
import liquibase.snapshot.RestoredDatabaseSnapshot;
import liquibase.snapshot.ResultSetCache;
import liquibase.snapshot.SnapshotControl;
import liquibase.snapshot.SnapshotGenerator;
import liquibase.snapshot.SnapshotGeneratorChain;
import liquibase.snapshot.SnapshotGeneratorFactory;
import liquibase.snapshot.SnapshotIdService;
import liquibase.snapshot.SnapshotListener;
import liquibase.structure.DatabaseObject;
import liquibase.structure.DatabaseObjectCollection;
import liquibase.structure.core.Catalog;
import liquibase.structure.core.Column;
import liquibase.structure.core.ForeignKey;
import liquibase.structure.core.Index;
import liquibase.structure.core.PrimaryKey;
import liquibase.structure.core.Schema;
import liquibase.structure.core.UniqueConstraint;
import liquibase.util.BooleanUtil;
import liquibase.util.ISODateFormat;
import liquibase.util.ObjectUtil;
import liquibase.util.StringUtil;

public abstract class DatabaseSnapshot
implements LiquibaseSerializable {
    private static final Logger LOGGER = Scope.getCurrentScope().getLog(DatabaseSnapshot.class);
    public static final String ALL_CATALOGS_STRING_SCRATCH_KEY = "DatabaseSnapshot.allCatalogsString";
    private final DatabaseObject[] originalExamples;
    private final HashSet<String> serializableFields;
    private final SnapshotControl snapshotControl;
    private final Database database;
    private final DatabaseObjectCollection allFound;
    private final DatabaseObjectCollection referencedObjects;
    private final Map<Class<? extends DatabaseObject>, Set<DatabaseObject>> knownNull = new ConcurrentHashMap<Class<? extends DatabaseObject>, Set<DatabaseObject>>();
    private final Map<String, Object> snapshotScratchPad = new ConcurrentHashMap<String, Object>();
    private final Map<String, ResultSetCache> resultSetCaches = new ConcurrentHashMap<String, ResultSetCache>();
    private CompareControl.SchemaComparison[] schemaComparisons;
    private Map<String, Object> metadata = new ConcurrentHashMap<String, Object>();

    DatabaseSnapshot(DatabaseObject[] examples, Database database, SnapshotControl snapshotControl) throws DatabaseException, InvalidExampleException {
        this.database = database;
        this.allFound = new DatabaseObjectCollection(database);
        this.referencedObjects = new DatabaseObjectCollection(database);
        this.snapshotControl = snapshotControl;
        this.originalExamples = examples == null ? new DatabaseObject[]{} : examples;
        this.init(examples);
        this.serializableFields = new HashSet();
        this.serializableFields.add("snapshotControl");
        this.serializableFields.add("objects");
        this.serializableFields.add("referencedObjects");
        this.serializableFields.add("database");
        this.serializableFields.add("created");
        this.serializableFields.add("metadata");
    }

    public DatabaseSnapshot(DatabaseObject[] examples, Database database) throws DatabaseException, InvalidExampleException {
        this(examples, database, new SnapshotControl(database));
    }

    protected void init(DatabaseObject[] examples) throws DatabaseException, InvalidExampleException {
        if (examples != null) {
            HashSet<Catalog> catalogs = new HashSet<Catalog>();
            for (DatabaseObject object : examples) {
                if (!(object instanceof Schema)) continue;
                catalogs.add(((Schema)object).getCatalog());
            }
            this.setScratchData("DatabaseSnapshot.allCatalogs", catalogs);
            if (catalogs.size() > 1) {
                ArrayList<String> quotedCatalogs = new ArrayList<String>();
                for (Catalog catalog : catalogs) {
                    quotedCatalogs.add("'" + catalog.getName() + "'");
                }
                if (CatalogAndSchema.CatalogAndSchemaCase.ORIGINAL_CASE.equals((Object)this.database.getSchemaAndCatalogCase())) {
                    this.setScratchData(ALL_CATALOGS_STRING_SCRATCH_KEY, StringUtil.join(quotedCatalogs, ", "));
                } else {
                    this.setScratchData(ALL_CATALOGS_STRING_SCRATCH_KEY, StringUtil.join(quotedCatalogs, ", ").toUpperCase());
                }
            }
            if (this.getDatabase().supports(Catalog.class)) {
                for (Catalog catalog : catalogs) {
                    this.snapshotControl.addType(catalog.getClass(), this.database);
                    this.include(catalog);
                }
            }
            for (DatabaseObject obj : examples) {
                this.snapshotControl.addType(obj.getClass(), this.database);
                this.include(obj);
            }
        }
    }

    public DatabaseSnapshot clone(DatabaseObject[] examples) {
        try {
            RestoredDatabaseSnapshot returnSnapshot = new RestoredDatabaseSnapshot(this.database);
            for (DatabaseObject example : examples) {
                DatabaseObject existingObject = this.get(example);
                if (existingObject == null) continue;
                if (example instanceof Schema) {
                    for (Class<? extends DatabaseObject> type : this.snapshotControl.getTypesToInclude()) {
                        for (DatabaseObject databaseObject : this.get(type)) {
                            if (databaseObject.getSchema() == null) {
                                if (databaseObject instanceof Catalog) {
                                    if (!DatabaseObjectComparatorFactory.getInstance().isSameObject(databaseObject, ((Schema)example).getCatalog(), null, this.database)) continue;
                                    returnSnapshot.allFound.add(databaseObject);
                                    continue;
                                }
                                returnSnapshot.allFound.add(databaseObject);
                                continue;
                            }
                            if (DatabaseObjectComparatorFactory.getInstance().isSameObject(databaseObject.getSchema(), example, null, this.database)) {
                                returnSnapshot.allFound.add(databaseObject);
                                continue;
                            }
                            if (!databaseObject.getClass().getName().contains("Synonym") || databaseObject.getAttribute("private", false).booleanValue()) continue;
                            Schema objectSchema = (Schema)((Object)databaseObject.getAttribute("objectSchema", Schema.class));
                            if (!DatabaseObjectComparatorFactory.getInstance().isSameObject(objectSchema, example, null, this.database)) continue;
                            returnSnapshot.allFound.add(databaseObject);
                        }
                    }
                    continue;
                }
                returnSnapshot.allFound.add(existingObject);
            }
            returnSnapshot.getMetadata().putAll(this.getMetadata());
            return returnSnapshot;
        }
        catch (Exception e) {
            throw new UnexpectedLiquibaseException(e);
        }
    }

    public DatabaseSnapshot merge(DatabaseSnapshot snapshotToMerge) {
        DatabaseSnapshot returnSnapshot = this;
        Map<Class<? extends DatabaseObject>, Set<? extends DatabaseObject>> allFoundMap = snapshotToMerge.allFound.toMap();
        Map<Class<? extends DatabaseObject>, Set<? extends DatabaseObject>> referencedObjectsMap = snapshotToMerge.referencedObjects.toMap();
        for (Set<? extends DatabaseObject> setOfDatabaseObject : allFoundMap.values()) {
            for (DatabaseObject databaseObject : setOfDatabaseObject) {
                returnSnapshot.allFound.add(databaseObject);
            }
        }
        for (Set<? extends DatabaseObject> setOfDatabaseObject : referencedObjectsMap.values()) {
            for (DatabaseObject databaseObject : setOfDatabaseObject) {
                returnSnapshot.referencedObjects.add(databaseObject);
            }
        }
        return returnSnapshot;
    }

    public SnapshotControl getSnapshotControl() {
        return this.snapshotControl;
    }

    @Override
    public String getSerializedObjectName() {
        return "snapshot";
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
        return this.serializableFields;
    }

    @Override
    public Object getSerializableFieldValue(String field2) {
        switch (field2) {
            case "snapshotControl": {
                return this.snapshotControl;
            }
            case "objects": {
                return this.allFound;
            }
            case "referencedObjects": {
                return this.referencedObjects;
            }
            case "metadata": {
                return this.metadata;
            }
            case "created": {
                return new ISODateFormat().format(new Timestamp(new Date().getTime()));
            }
            case "database": {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("shortName", this.database.getShortName());
                map.put("productName", this.database.getDatabaseProductName());
                map.put("url", this.database.getConnection().getURL());
                try {
                    map.put("majorVersion", String.valueOf(this.database.getDatabaseMajorVersion()));
                    map.put("minorVersion", String.valueOf(this.database.getDatabaseMinorVersion()));
                    map.put("productVersion", this.database.getDatabaseProductVersion());
                    map.put("user", this.database.getConnection().getConnectionUserName());
                }
                catch (DatabaseException databaseException) {
                    // empty catch block
                }
                return map;
            }
        }
        throw new UnexpectedLiquibaseException("Unknown field: " + field2);
    }

    @Override
    public LiquibaseSerializable.SerializationType getSerializableFieldType(String field2) {
        switch (field2) {
            case "snapshotControl": 
            case "objects": 
            case "referencedObjects": {
                return LiquibaseSerializable.SerializationType.NESTED_OBJECT;
            }
        }
        throw new UnexpectedLiquibaseException("Unknown field: " + field2);
    }

    public Database getDatabase() {
        return this.database;
    }

    public ResultSetCache getResultSetCache(String key) {
        if (!this.resultSetCaches.containsKey(key)) {
            this.resultSetCaches.put(key, new ResultSetCache());
        }
        return this.resultSetCaches.get(key);
    }

    protected <T extends DatabaseObject> T include(T example) throws DatabaseException, InvalidExampleException {
        T object;
        if (example == null) {
            return null;
        }
        if (this.database.isSystemObject((DatabaseObject)example)) {
            return null;
        }
        if (example instanceof Schema && example.getName() == null && (((Schema)example).getCatalog() == null || ((Schema)example).getCatalogName() == null)) {
            CatalogAndSchema catalogAndSchema = ((Schema)example).toCatalogAndSchema().customize(this.database);
            example = new Schema(catalogAndSchema.getCatalogName(), catalogAndSchema.getSchemaName());
        }
        if (!this.snapshotControl.shouldInclude(example)) {
            LOGGER.fine("Excluding " + example);
            return (T)example;
        }
        T existing = this.get(example);
        if (existing != null) {
            return existing;
        }
        if (this.isKnownNull((DatabaseObject)example)) {
            return null;
        }
        SnapshotListener snapshotListener = this.snapshotControl.getSnapshotListener();
        SnapshotGeneratorChain chain = this.createGeneratorChain(example.getClass(), this.database);
        if (snapshotListener != null) {
            snapshotListener.willSnapshot((DatabaseObject)example, this.database);
        }
        if ((object = chain.snapshot(example, this)) == null) {
            Set collection = this.knownNull.computeIfAbsent(example.getClass(), k -> new HashSet());
            collection.add(example);
            if (example instanceof Schema && this.snapshotControl.isWarnIfObjectNotFound()) {
                Scope.getCurrentScope().getLog(this.getClass()).warning("Did not find schema '" + example + "' to snapshot");
            }
            if (example instanceof Catalog && this.snapshotControl.isWarnIfObjectNotFound()) {
                Scope.getCurrentScope().getLog(this.getClass()).warning("Did not find catalog '" + example + "' to snapshot");
            }
        } else {
            this.allFound.add(object);
            try {
                this.includeNestedObjects(object);
            }
            catch (ReflectiveOperationException e) {
                throw new UnexpectedLiquibaseException(e);
            }
        }
        if (snapshotListener != null) {
            snapshotListener.finishedSnapshot((DatabaseObject)example, object, this.database);
        }
        return object;
    }

    private void includeNestedObjects(DatabaseObject object) throws DatabaseException, InvalidExampleException, ReflectiveOperationException {
        for (String field2 : new HashSet<String>(object.getAttributes())) {
            Class<Object> fieldValue = object.getAttribute(field2, Object.class);
            if ("columns".equals(field2) && (object.getClass() == PrimaryKey.class || object.getClass() == Index.class || object.getClass() == UniqueConstraint.class) && fieldValue != null && !((Collection)((Object)fieldValue)).isEmpty() && this.descendingColumnExists((Collection)((Object)fieldValue))) {
                List columns = (List)((Object)fieldValue);
                for (Column col : columns) {
                    if (col.getSnapshotId() != null) continue;
                    col.setSnapshotId(SnapshotIdService.getInstance().generateId());
                    this.includeNestedObjects(col);
                    this.referencedObjects.add(col);
                }
                continue;
            }
            Object newFieldValue = this.replaceObject(fieldValue);
            if (newFieldValue == null) {
                if (!(object instanceof UniqueConstraint) && !(object instanceof PrimaryKey) && !(object instanceof ForeignKey) || !"backingIndex".equals(field2)) continue;
                object.setAttribute(field2, null);
                continue;
            }
            if (fieldValue == newFieldValue) continue;
            object.setAttribute(field2, newFieldValue);
        }
    }

    private boolean descendingColumnExists(Collection<Column> fieldValue) {
        for (Column column : fieldValue) {
            String columnName = column.getName().toLowerCase();
            if (!BooleanUtil.isTrue(column.getDescending()) && !columnName.endsWith(" asc") && !columnName.endsWith(" desc") && !columnName.endsWith(" random")) continue;
            return true;
        }
        return false;
    }

    private Object replaceObject(Object fieldValue) throws DatabaseException, InvalidExampleException, ReflectiveOperationException {
        if (fieldValue == null) {
            return null;
        }
        if (fieldValue instanceof DatabaseObject) {
            if (((DatabaseObject)fieldValue).getSnapshotId() != null) {
                return fieldValue;
            }
            if (!this.snapshotControl.shouldInclude(((DatabaseObject)fieldValue).getClass())) {
                return fieldValue;
            }
            if (!(fieldValue instanceof Catalog) && this.isWrongSchema((DatabaseObject)fieldValue)) {
                DatabaseObject savedFieldValue = this.referencedObjects.get((DatabaseObject)fieldValue, this.schemaComparisons);
                if (savedFieldValue == null) {
                    savedFieldValue = (DatabaseObject)fieldValue;
                    savedFieldValue.setSnapshotId(SnapshotIdService.getInstance().generateId());
                    this.includeNestedObjects(savedFieldValue);
                    this.referencedObjects.add(savedFieldValue);
                }
                return savedFieldValue;
            }
            if (fieldValue instanceof Catalog && this.isWrongCatalog((DatabaseObject)fieldValue)) {
                DatabaseObject savedFieldValue = this.referencedObjects.get((DatabaseObject)fieldValue, this.schemaComparisons);
                if (savedFieldValue == null) {
                    savedFieldValue = (DatabaseObject)fieldValue;
                    savedFieldValue.setSnapshotId(SnapshotIdService.getInstance().generateId());
                    this.referencedObjects.add(savedFieldValue);
                }
                return savedFieldValue;
            }
            if (((DatabaseObject)fieldValue).getSnapshotId() == null) {
                return this.include((DatabaseObject)fieldValue);
            }
            return fieldValue;
        }
        if (fieldValue instanceof Collection) {
            Iterator fieldValueIterator = new CopyOnWriteArrayList((Collection)fieldValue).iterator();
            ArrayList newValues = new ArrayList();
            while (fieldValueIterator.hasNext()) {
                Object obj = fieldValueIterator.next();
                if (fieldValue instanceof DatabaseObject && !this.snapshotControl.shouldInclude(((DatabaseObject)fieldValue).getClass())) {
                    return fieldValue;
                }
                if (obj instanceof DatabaseObject && ((DatabaseObject)obj).getSnapshotId() == null) {
                    obj = this.include((DatabaseObject)obj);
                }
                if (obj == null) continue;
                newValues.add(obj);
            }
            Collection newCollection = null;
            Class<Object> collectionClass = fieldValue.getClass();
            if (List.class.isAssignableFrom(collectionClass)) {
                collectionClass = ArrayList.class;
            }
            newCollection = (Collection)collectionClass.getConstructor(new Class[0]).newInstance(new Object[0]);
            newCollection.addAll(newValues);
            return newCollection;
        }
        if (fieldValue instanceof Map) {
            Map newMap = (Map)fieldValue.getClass().getConstructor(new Class[0]).newInstance(new Object[0]);
            for (Map.Entry entry : new HashSet(((Map)fieldValue).entrySet())) {
                Object key = this.replaceObject(entry.getKey());
                Object value = this.replaceObject(entry.getValue());
                if (key == null) continue;
                newMap.put(key, value);
            }
            return newMap;
        }
        return fieldValue;
    }

    protected boolean isWrongSchema(DatabaseObject fieldValue) {
        boolean isSchemaExamples = true;
        if (this.originalExamples == null) {
            return false;
        }
        for (DatabaseObject obj : this.originalExamples) {
            if (obj instanceof Schema) continue;
            isSchemaExamples = false;
            break;
        }
        if (!isSchemaExamples) {
            return false;
        }
        for (DatabaseObject obj : this.originalExamples) {
            if (!DatabaseObjectComparatorFactory.getInstance().isSameObject(fieldValue.getSchema(), obj, this.schemaComparisons, this.database)) continue;
            return false;
        }
        return true;
    }

    protected boolean isWrongCatalog(DatabaseObject fieldValue) {
        String fieldCatalog;
        if (fieldValue instanceof Catalog) {
            fieldCatalog = fieldValue.getName();
        } else if (fieldValue instanceof Schema) {
            fieldCatalog = ((Schema)fieldValue).getCatalogName();
        } else {
            Schema fieldSchema = fieldValue.getSchema();
            if (fieldSchema == null) {
                return false;
            }
            fieldCatalog = fieldSchema.getCatalogName();
        }
        if (fieldCatalog == null) {
            return false;
        }
        HashSet<String> catalogNames = new HashSet<String>();
        for (DatabaseObject obj : this.originalExamples) {
            String catalogName = null;
            if (obj instanceof Schema) {
                catalogName = ((Schema)obj).getCatalogName();
            } else if (obj instanceof Catalog) {
                catalogName = obj.getName();
            }
            if (catalogName == null) continue;
            if (CatalogAndSchema.CatalogAndSchemaCase.ORIGINAL_CASE.equals((Object)this.database.getSchemaAndCatalogCase())) {
                catalogNames.add(catalogName);
                continue;
            }
            catalogNames.add(catalogName.toLowerCase());
        }
        if (CatalogAndSchema.CatalogAndSchemaCase.ORIGINAL_CASE.equals((Object)this.database.getSchemaAndCatalogCase())) {
            return !catalogNames.contains(fieldCatalog);
        }
        return !catalogNames.contains(fieldCatalog.toLowerCase());
    }

    public <DatabaseObjectType extends DatabaseObject> DatabaseObjectType get(DatabaseObjectType example) {
        return this.allFound.get(example, this.schemaComparisons);
    }

    public <DatabaseObjectType extends DatabaseObject> Set<DatabaseObjectType> get(Class<DatabaseObjectType> type) {
        return this.allFound.get(type);
    }

    protected SnapshotGeneratorChain createGeneratorChain(Class<? extends DatabaseObject> databaseObjectType, Database database) {
        SortedSet<SnapshotGenerator> generators = SnapshotGeneratorFactory.getInstance().getGenerators(databaseObjectType, database);
        if (generators == null || generators.isEmpty()) {
            return null;
        }
        return new SnapshotGeneratorChain(generators);
    }

    private boolean isKnownNull(DatabaseObject example) {
        Set<DatabaseObject> databaseObjects = this.knownNull.get(example.getClass());
        if (databaseObjects == null) {
            return false;
        }
        for (DatabaseObject obj : databaseObjects) {
            if (!DatabaseObjectComparatorFactory.getInstance().isSameObject(obj, example, this.schemaComparisons, this.database)) continue;
            return true;
        }
        return false;
    }

    @Override
    public void load(ParsedNode parsedNode, ResourceAccessor resourceAccessor) throws ParsedNodeException {
        try {
            HashMap<String, DatabaseObject> referencedObjects = new HashMap<String, DatabaseObject>();
            HashMap<String, DatabaseObject> objects = new HashMap<String, DatabaseObject>();
            HashMap<String, DatabaseObject> allObjects = new HashMap<String, DatabaseObject>();
            ParsedNode databaseNode = parsedNode.getChild(null, "database");
            DatabaseConnection connection = this.getDatabase().getConnection();
            if (databaseNode != null && connection instanceof OfflineConnection) {
                ((OfflineConnection)connection).setDatabaseMajorVersion(databaseNode.getChildValue((String)null, "majorVersion", Integer.class));
                ((OfflineConnection)connection).setDatabaseMinorVersion(databaseNode.getChildValue((String)null, "minorVersion", Integer.class));
                ((OfflineConnection)connection).setProductVersion(databaseNode.getChildValue((String)null, "productVersion", String.class));
                ((OfflineConnection)connection).setConnectionUserName(databaseNode.getChildValue((String)null, "user", String.class));
            }
            this.loadObjects(referencedObjects, allObjects, parsedNode.getChild(null, "referencedObjects"), resourceAccessor);
            this.loadObjects(objects, allObjects, parsedNode.getChild(null, "objects"), resourceAccessor);
            for (DatabaseObject object : allObjects.values()) {
                for (String attr : new ArrayList<String>(object.getAttributes())) {
                    Class<Object> value = object.getAttribute(attr, Object.class);
                    if (value instanceof String && allObjects.containsKey(value)) {
                        if (ObjectUtil.hasProperty(object, attr)) {
                            ObjectUtil.setProperty((Object)object, attr, allObjects.get(value));
                            continue;
                        }
                        object.setAttribute(attr, allObjects.get(value));
                        continue;
                    }
                    if (value instanceof Collection && !((Collection)((Object)value)).isEmpty() && allObjects.containsKey(((Collection)((Object)value)).iterator().next())) {
                        ArrayList<DatabaseObject> newList = new ArrayList<DatabaseObject>();
                        for (Object element : (Collection)((Object)value)) {
                            if (element instanceof String) {
                                newList.add((DatabaseObject)allObjects.get(element));
                                continue;
                            }
                            newList.add((DatabaseObject)element);
                        }
                        if (ObjectUtil.hasProperty(object, attr)) {
                            ObjectUtil.setProperty((Object)object, attr, newList);
                            continue;
                        }
                        object.setAttribute(attr, newList);
                        continue;
                    }
                    if (value == null || !ObjectUtil.hasProperty(object, attr)) continue;
                    if (value instanceof byte[] && ObjectUtil.getPropertyType(object, attr).equals(String.class)) {
                        value = new String((byte[])value, GlobalConfiguration.OUTPUT_FILE_ENCODING.getCurrentValue());
                    }
                    object.setAttribute(attr, null);
                    ObjectUtil.setProperty((Object)object, attr, value);
                }
            }
            for (DatabaseObject object : objects.values()) {
                this.allFound.add(object);
            }
            for (DatabaseObject object : referencedObjects.values()) {
                this.referencedObjects.add(object);
            }
        }
        catch (Exception e) {
            throw new ParsedNodeException(e);
        }
    }

    protected void loadObjects(Map<String, DatabaseObject> objectMap, Map<String, DatabaseObject> allObjects, ParsedNode node, ResourceAccessor resourceAccessor) throws ReflectiveOperationException, ParsedNodeException {
        if (node == null) {
            return;
        }
        for (ParsedNode typeNode : node.getChildren()) {
            Class<?> objectType = Class.forName(typeNode.getName());
            for (ParsedNode objectNode : typeNode.getChildren()) {
                DatabaseObject databaseObject = (DatabaseObject)objectType.getConstructor(new Class[0]).newInstance(new Object[0]);
                databaseObject.load(objectNode, resourceAccessor);
                String key = objectType.getName() + "#" + databaseObject.getSnapshotId();
                objectMap.put(key, databaseObject);
                allObjects.put(key, databaseObject);
            }
        }
    }

    @Override
    public ParsedNode serialize() {
        throw new RuntimeException("TODO");
    }

    public Object getScratchData(String key) {
        return this.snapshotScratchPad.get(key);
    }

    public Object setScratchData(String key, Object data) {
        return this.snapshotScratchPad.put(key, data);
    }

    public CompareControl.SchemaComparison[] getSchemaComparisons() {
        return this.schemaComparisons;
    }

    public void setSchemaComparisons(CompareControl.SchemaComparison[] schemaComparisons) {
        this.schemaComparisons = schemaComparisons;
    }

    public Map<String, Object> getMetadata() {
        return this.metadata;
    }

    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }
}

