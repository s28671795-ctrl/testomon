/*
 * Decompiled with CFR 0.152.
 */
package liquibase.serializer.core.string;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import liquibase.changelog.ChangeLogChild;
import liquibase.changelog.ChangeSet;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.serializer.ChangeLogSerializer;
import liquibase.serializer.LiquibaseSerializable;
import liquibase.util.StringUtil;

public class StringChangeLogSerializer
implements ChangeLogSerializer {
    private static final int INDENT_LENGTH = 4;
    private final FieldFilter fieldFilter;

    public StringChangeLogSerializer() {
        this(new FieldFilter());
    }

    public StringChangeLogSerializer(FieldFilter fieldFilter) {
        this.fieldFilter = fieldFilter;
    }

    @Override
    public String[] getValidFileExtensions() {
        return new String[]{"txt"};
    }

    @Override
    public String serialize(LiquibaseSerializable object, boolean pretty) {
        return object.getSerializedObjectName() + ":" + this.serializeObject(object, 1);
    }

    private String serializeObject(LiquibaseSerializable objectToSerialize, int indent) {
        try {
            StringBuilder buffer = new StringBuilder();
            buffer.append("[");
            TreeSet<String> values2 = new TreeSet<String>();
            for (String field2 : objectToSerialize.getSerializableFields()) {
                Object value = objectToSerialize.getSerializableFieldValue(field2);
                if (value == null || !this.fieldFilter.include(objectToSerialize, field2, value)) continue;
                if (value instanceof LiquibaseSerializable) {
                    values2.add(this.indent(indent) + this.serializeObject((LiquibaseSerializable)value, indent + 1));
                    continue;
                }
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
                    valueString = valueString.replaceFirst("(\\.[0-9]+)0+$", "$1");
                    valueString = valueString.replaceFirst("\\.0+$", "");
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
            if (object instanceof LiquibaseSerializable) {
                returnString.append(this.indent(indent)).append(this.serializeObject((LiquibaseSerializable)object, indent + 1)).append(",\n");
                continue;
            }
            returnString.append(this.indent(indent)).append(object.toString()).append(",\n");
        }
        returnString = new StringBuilder(returnString.toString().replaceFirst(",$", ""));
        returnString.append(this.indent(indent - 1)).append("]");
        return returnString.toString();
    }

    private String serializeObject(Collection collection, int indent) {
        if (collection.isEmpty()) {
            return "[]";
        }
        StringBuilder returnString = new StringBuilder("[\n");
        for (Object object : collection) {
            if (object instanceof LiquibaseSerializable) {
                returnString.append(this.indent(indent)).append(this.serializeObject((LiquibaseSerializable)object, indent + 1)).append(",\n");
                continue;
            }
            returnString.append(this.indent(indent)).append(object.toString()).append(",\n");
        }
        returnString = new StringBuilder(returnString.toString().replaceFirst(",$", ""));
        returnString.append(this.indent(indent - 1)).append("]");
        return returnString.toString();
    }

    private String serializeObject(Map collection, int indent) {
        if (collection.isEmpty()) {
            return "[]";
        }
        StringBuilder returnString = new StringBuilder("{\n");
        for (Object key : new TreeSet(collection.keySet())) {
            returnString.append(this.indent(indent)).append(key.toString()).append("=\"").append(collection.get(key)).append("\",\n");
        }
        return String.format("%s%s}", returnString.toString().replaceFirst(",$", ""), this.indent(indent - 1));
    }

    @Override
    public <T extends ChangeLogChild> void write(List<T> children, OutputStream out) throws IOException {
    }

    @Override
    public void append(ChangeSet changeSet, File changeLogFile) throws IOException {
    }

    @Override
    public int getPriority() {
        return 1;
    }

    public static class FieldFilter {
        public boolean include(Object obj, String field2, Object value) {
            return true;
        }
    }
}

