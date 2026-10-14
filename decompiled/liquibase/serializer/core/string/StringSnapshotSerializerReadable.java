/*
 * Decompiled with CFR 0.152.
 */
package liquibase.serializer.core.string;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;
import java.util.TreeSet;
import liquibase.GlobalConfiguration;
import liquibase.database.Database;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.serializer.LiquibaseSerializable;
import liquibase.serializer.SnapshotSerializer;
import liquibase.snapshot.DatabaseSnapshot;
import liquibase.snapshot.SnapshotControl;
import liquibase.structure.CatalogLevelObject;
import liquibase.structure.DatabaseLevelObject;
import liquibase.structure.DatabaseObject;
import liquibase.structure.core.Catalog;
import liquibase.structure.core.Column;
import liquibase.structure.core.Schema;
import liquibase.structure.core.Table;
import liquibase.structure.core.View;
import liquibase.util.StringUtil;

public class StringSnapshotSerializerReadable
implements SnapshotSerializer {
    private static final int INDENT_LENGTH = 4;

    @Override
    public String[] getValidFileExtensions() {
        return new String[]{"txt"};
    }

    @Override
    public String serialize(LiquibaseSerializable object, boolean pretty) {
        try {
            StringBuilder buffer = new StringBuilder();
            DatabaseSnapshot snapshot = (DatabaseSnapshot)object;
            Database database = snapshot.getDatabase();
            buffer.append("Database snapshot for ").append(database.getConnection().getURL()).append("\n");
            this.addDivider(buffer);
            buffer.append("Database type: ").append(database.getDatabaseProductName()).append("\n");
            buffer.append("Database version: ").append(database.getDatabaseProductVersion()).append("\n");
            buffer.append("Database user: ").append(database.getConnection().getConnectionUserName()).append("\n");
            SnapshotControl snapshotControl = snapshot.getSnapshotControl();
            List includedTypes = this.sort(snapshotControl.getTypesToInclude());
            buffer.append("Included types:\n").append(StringUtil.indent(StringUtil.join((Collection)includedTypes, "\n", Class::getName))).append("\n");
            List<Schema> schemas = this.sort(snapshot.get(Schema.class), Comparator.comparing(Schema::toString));
            for (Schema schema : schemas) {
                if (database.supports(Schema.class)) {
                    buffer.append("\nCatalog & Schema: ").append(schema.getCatalogName()).append(" / ").append(schema.getName()).append("\n");
                } else {
                    buffer.append("\nCatalog: ").append(schema.getCatalogName()).append("\n");
                }
                StringBuilder catalogBuffer = new StringBuilder();
                for (Class type : includedTypes) {
                    if (type.equals(Schema.class) || type.equals(Catalog.class) || type.equals(Column.class)) continue;
                    ArrayList objects = new ArrayList(snapshot.get(type));
                    ListIterator iterator2 = objects.listIterator();
                    while (iterator2.hasNext()) {
                        DatabaseObject next = (DatabaseObject)iterator2.next();
                        if (next instanceof DatabaseLevelObject) continue;
                        Schema objectSchema = next.getSchema();
                        if (objectSchema == null) {
                            if (next instanceof CatalogLevelObject && ((CatalogLevelObject)((Object)next)).getCatalog().equals(schema.getCatalog())) continue;
                            iterator2.remove();
                            continue;
                        }
                        if (objectSchema.equals(schema)) continue;
                        iterator2.remove();
                    }
                    this.outputObjects(objects, type, catalogBuffer);
                }
                buffer.append(StringUtil.indent(catalogBuffer.toString(), 4));
            }
            return buffer.toString().replace("\r\n", "\n").replace("\r", "\n");
        }
        catch (Exception e) {
            throw new UnexpectedLiquibaseException(e);
        }
    }

    protected void outputObjects(List objects, Class type, StringBuilder catalogBuffer) {
        List databaseObjects = this.sort(objects);
        if (!databaseObjects.isEmpty()) {
            catalogBuffer.append(type.getName()).append(":\n");
            StringBuilder typeBuffer = new StringBuilder();
            for (DatabaseObject databaseObject : databaseObjects) {
                typeBuffer.append(databaseObject.getName()).append("\n");
                typeBuffer.append(StringUtil.indent(this.serialize(databaseObject, null), 4)).append("\n");
            }
            catalogBuffer.append(StringUtil.indent(typeBuffer.toString(), 4)).append("\n");
        }
    }

    private String serialize(DatabaseObject databaseObject, DatabaseObject parentObject) {
        StringBuilder buffer = new StringBuilder();
        List attributes = this.sort(databaseObject.getAttributes());
        for (String attribute : attributes) {
            Object value;
            if ("name".equals(attribute) || "schema".equals(attribute) || "catalog".equals(attribute) || (value = databaseObject.getAttribute(attribute, Object.class)) instanceof Schema) continue;
            if (value instanceof DatabaseObject) {
                if (parentObject != null && ((DatabaseObject)value).getSnapshotId() != null && ((DatabaseObject)value).getSnapshotId().equals(parentObject.getSnapshotId())) continue;
                boolean expandContainedObjects = this.shouldExpandNestedObject(value, databaseObject);
                value = expandContainedObjects ? ((DatabaseObject)value).getName() + "\n" + StringUtil.indent(this.serialize((DatabaseObject)value, databaseObject), 4) : databaseObject.getSerializableFieldValue(attribute);
            } else if (value instanceof Collection) {
                if (((Collection)value).isEmpty()) {
                    value = null;
                } else if (((Collection)value).iterator().next() instanceof DatabaseObject) {
                    value = StringUtil.join(new TreeSet((Collection)value), "\n", obj -> {
                        if (obj instanceof DatabaseObject) {
                            if (this.shouldExpandNestedObject(obj, databaseObject)) {
                                return ((DatabaseObject)obj).getName() + "\n" + StringUtil.indent(this.serialize((DatabaseObject)obj, databaseObject), 4);
                            }
                            return ((DatabaseObject)obj).getName();
                        }
                        return obj.toString();
                    });
                    value = "\n" + StringUtil.indent((String)value, 4);
                } else {
                    value = databaseObject.getSerializableFieldValue(attribute);
                }
            } else {
                value = databaseObject.getSerializableFieldValue(attribute);
            }
            if (value == null) continue;
            buffer.append(attribute).append(": ").append(value).append("\n");
        }
        return buffer.toString().replaceFirst("\n$", "");
    }

    protected boolean shouldExpandNestedObject(Object nestedValue, DatabaseObject container) {
        return container instanceof Table || container instanceof View;
    }

    protected void addDivider(StringBuilder buffer) {
        buffer.append("-----------------------------------------------------------------\n");
    }

    private List sort(Collection objects) {
        return this.sort(objects, (o1, o2) -> {
            if (o1 instanceof Comparable) {
                return ((Comparable)o1).compareTo(o2);
            }
            if (o1 instanceof Class) {
                return ((Class)o1).getName().compareTo(((Class)o2).getName());
            }
            throw new ClassCastException(o1.getClass().getName() + " cannot be cast to java.lang.Comparable or java.lang.Class");
        });
    }

    private <T> List<T> sort(Collection objects, Comparator<T> comparator) {
        ArrayList<T> returnList = new ArrayList<T>(objects);
        returnList.sort(comparator);
        return returnList;
    }

    @Override
    public void write(DatabaseSnapshot snapshot, OutputStream out) throws IOException {
        out.write(this.serialize(snapshot, true).getBytes(GlobalConfiguration.OUTPUT_FILE_ENCODING.getCurrentValue()));
    }

    @Override
    public int getPriority() {
        return 1;
    }
}

