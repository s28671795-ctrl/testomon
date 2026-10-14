/*
 * Decompiled with CFR 0.152.
 */
package liquibase.serializer.core.string;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Map;
import java.util.TreeSet;
import liquibase.GlobalConfiguration;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.serializer.LiquibaseSerializable;
import liquibase.serializer.SnapshotSerializer;
import liquibase.snapshot.DatabaseSnapshot;
import liquibase.util.StringUtil;

public class StringSnapshotSerializer
implements SnapshotSerializer {
    private static final int INDENT_LENGTH = 4;

    @Override
    public String[] getValidFileExtensions() {
        return new String[]{"checksum"};
    }

    @Override
    public String serialize(LiquibaseSerializable object, boolean pretty) {
        return object.getSerializedObjectName() + ":" + this.serializeObject(object, 1);
    }

    private String serializeObject(Object object, int indent) {
        return object instanceof LiquibaseSerializable ? this.serializeObject((LiquibaseSerializable)object, indent) : object.toString();
    }

    private String serializeObject(LiquibaseSerializable objectToSerialize, int indent) {
        try {
            StringBuilder buffer = new StringBuilder();
            buffer.append("[");
            TreeSet<String> values2 = new TreeSet<String>();
            for (String field2 : objectToSerialize.getSerializableFields()) {
                Object value = objectToSerialize.getSerializableFieldValue(field2);
                if (value instanceof LiquibaseSerializable) {
                    values2.add(this.indent(indent) + this.serializeObject((LiquibaseSerializable)value, indent + 1));
                    continue;
                }
                if (value == null) continue;
                if (value instanceof Map) {
                    values2.add(this.indent(indent) + field2 + "=" + this.serializeObject((Map)value, indent + 1));
                    continue;
                }
                if (value instanceof Collection) {
                    values2.add(this.indent(indent) + field2 + "=" + this.serializeObject((Collection)value, indent + 1));
                    continue;
                }
                if (value instanceof Object[]) {
                    values2.add(this.indent(indent) + field2 + "=" + this.serializeObject((Object[])value, indent + 1));
                    continue;
                }
                String valueString = value.toString();
                if ((value instanceof Double || value instanceof Float) && valueString.contains(".")) {
                    valueString = valueString.replaceFirst("0*$", "");
                }
                values2.add(this.indent(indent) + field2 + "=\"" + valueString + "\"");
            }
            if (!values2.isEmpty()) {
                buffer.append("\n");
                buffer.append(StringUtil.join(values2, "\n"));
                buffer.append("\n");
            }
            buffer.append(this.indent(indent - 1)).append("]");
            return buffer.toString().replace("\r\n", "\n").replace("\r", "\n");
        }
        catch (Exception e) {
            throw new UnexpectedLiquibaseException(e);
        }
    }

    private String indent(int indent) {
        return StringUtil.repeat(" ", 4 * indent);
    }

    private String serializeObject(Object[] collection, int indent) {
        if (collection.length == 0) {
            return "[]";
        }
        StringBuilder returnString = new StringBuilder("[\n");
        for (Object object : collection) {
            returnString.append(this.indent(indent)).append(this.serializeObject(object, indent + 1)).append(",\n");
        }
        String result = returnString.toString().replaceFirst(",$", "");
        return result + this.indent(indent - 1) + "]";
    }

    private String serializeObject(Collection collection, int indent) {
        if (collection.isEmpty()) {
            return "[]";
        }
        StringBuilder returnString = new StringBuilder("[\n");
        for (Object object : collection) {
            returnString.append(this.indent(indent));
            returnString.append(this.serializeObject(object, indent + 1));
            returnString.append(",\n");
        }
        String result = returnString.toString().replaceFirst(",$", "");
        return result + this.indent(indent - 1) + "]";
    }

    private String serializeObject(Map collection, int indent) {
        if (collection.isEmpty()) {
            return "[]";
        }
        StringBuilder returnString = new StringBuilder("{\n");
        TreeSet sortedCollection = new TreeSet((o1, o2) -> {
            if (o1 instanceof Comparable) {
                return ((Comparable)o1).compareTo(o2);
            }
            if (o1 instanceof Class) {
                return ((Class)o1).getName().compareTo(((Class)o2).getName());
            }
            throw new ClassCastException(o1.getClass().getName() + " cannot be cast to java.lang.Comparable or java.lang.Class");
        });
        sortedCollection.addAll(collection.keySet());
        for (Object key : sortedCollection) {
            returnString.append(this.indent(indent)).append(key.toString()).append("=\"").append(collection.get(key)).append("\",\n");
        }
        String result = returnString.toString().replaceFirst(",$", "");
        return result + this.indent(indent - 1) + "}";
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

