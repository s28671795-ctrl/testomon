/*
 * Decompiled with CFR 0.152.
 */
package liquibase.snapshot;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Stream;
import liquibase.database.Database;
import liquibase.database.core.FirebirdDatabase;
import liquibase.diff.output.ObjectChangeFilter;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.parser.core.ParsedNode;
import liquibase.parser.core.ParsedNodeException;
import liquibase.resource.ResourceAccessor;
import liquibase.serializer.LiquibaseSerializable;
import liquibase.snapshot.SnapshotGeneratorFactory;
import liquibase.snapshot.SnapshotListener;
import liquibase.structure.DatabaseObject;
import liquibase.structure.core.Catalog;
import liquibase.structure.core.DatabaseObjectFactory;

public class SnapshotControl
implements LiquibaseSerializable {
    private Set<Class<? extends DatabaseObject>> types;
    private ObjectChangeFilter objectChangeFilter;
    private SnapshotListener snapshotListener;
    private boolean warnIfObjectNotFound = true;

    public SnapshotControl(Database database) {
        this.setTypes(DatabaseObjectFactory.getInstance().getStandardTypes(), database);
    }

    @SafeVarargs
    public SnapshotControl(Database database, Class<? extends DatabaseObject> ... types) {
        this(database, true, types);
    }

    @SafeVarargs
    public SnapshotControl(Database database, boolean expandTypesIfNeeded, Class<? extends DatabaseObject> ... types) {
        if (types == null || types.length == 0) {
            this.setTypes(DatabaseObjectFactory.getInstance().getStandardTypes(), database);
        } else if (expandTypesIfNeeded) {
            this.setTypes(new HashSet<Class<? extends DatabaseObject>>(Arrays.asList(types)), database);
        } else {
            this.types = new HashSet<Class<? extends DatabaseObject>>(Arrays.asList(types));
        }
    }

    public SnapshotControl(Database database, String types) {
        this.setTypes(DatabaseObjectFactory.getInstance().parseTypes(types), database);
    }

    public SnapshotControl(Database database, ObjectChangeFilter objectChangeFilter, Class<? extends DatabaseObject> ... types) {
        this(database, true, types);
        this.objectChangeFilter = objectChangeFilter;
    }

    public SnapshotListener getSnapshotListener() {
        return this.snapshotListener;
    }

    public void setSnapshotListener(SnapshotListener snapshotListener) {
        this.snapshotListener = snapshotListener;
    }

    @Override
    public String getSerializedObjectName() {
        return "snapshotControl";
    }

    @Override
    public Set<String> getSerializableFields() {
        return new HashSet<String>(Collections.singletonList("includedType"));
    }

    @Override
    public Object getSerializableFieldValue(String field2) {
        if ("includedType".equals(field2)) {
            TreeSet<String> typesNames = new TreeSet<String>();
            for (Class<? extends DatabaseObject> type : this.getTypesToInclude()) {
                typesNames.add(type.getName());
            }
            return typesNames;
        }
        throw new UnexpectedLiquibaseException("Unknown field " + field2);
    }

    @Override
    public LiquibaseSerializable.SerializationType getSerializableFieldType(String field2) {
        if ("includedType".equals(field2)) {
            return LiquibaseSerializable.SerializationType.NESTED_OBJECT;
        }
        throw new UnexpectedLiquibaseException("Unknown field " + field2);
    }

    @Override
    public String getSerializedObjectNamespace() {
        return "http://www.liquibase.org/xml/ns/snapshot";
    }

    @Override
    public String getSerializableFieldNamespace(String field2) {
        return this.getSerializedObjectNamespace();
    }

    private void setTypes(Set<Class<? extends DatabaseObject>> types, Database database) {
        this.types = new HashSet<Class<? extends DatabaseObject>>();
        Stream<Object> objectStream = types.stream();
        if (database != null) {
            objectStream = objectStream.filter(t2 -> database.supports((Class<? extends DatabaseObject>)t2) || database instanceof FirebirdDatabase && t2.equals(Catalog.class));
        }
        objectStream.forEach(type -> this.addType((Class<? extends DatabaseObject>)type, database));
    }

    public boolean addType(Class<? extends DatabaseObject> type, Database database) {
        boolean added = this.types.add(type);
        if (added) {
            for (Class<? extends DatabaseObject> container : SnapshotGeneratorFactory.getInstance().getContainerTypes(type, database)) {
                this.addType(container, database);
            }
        }
        return added;
    }

    public Set<Class<? extends DatabaseObject>> getTypesToInclude() {
        return this.types;
    }

    public boolean shouldInclude(Class<? extends DatabaseObject> type) {
        return this.types.contains(type);
    }

    @Override
    public void load(ParsedNode parsedNode, ResourceAccessor resourceAccessor) throws ParsedNodeException {
        throw new RuntimeException("TODO");
    }

    @Override
    public ParsedNode serialize() {
        throw new RuntimeException("TODO");
    }

    public boolean isWarnIfObjectNotFound() {
        return this.warnIfObjectNotFound;
    }

    public SnapshotControl setWarnIfObjectNotFound(boolean warnIfObjectNotFound) {
        this.warnIfObjectNotFound = warnIfObjectNotFound;
        return this;
    }

    public <T extends DatabaseObject> boolean shouldInclude(T example) {
        if (this.objectChangeFilter != null) {
            return this.objectChangeFilter.include(example);
        }
        return this.shouldInclude((T)((Object)example.getClass()));
    }
}

