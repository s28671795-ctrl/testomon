/*
 * Decompiled with CFR 0.152.
 */
package liquibase.structure;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import liquibase.GlobalConfiguration;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.parser.core.ParsedNode;
import liquibase.parser.core.ParsedNodeException;
import liquibase.resource.ResourceAccessor;
import liquibase.serializer.LiquibaseSerializable;
import liquibase.structure.DatabaseObject;
import liquibase.structure.core.Schema;
import liquibase.util.ISODateFormat;
import liquibase.util.ObjectUtil;
import liquibase.util.StringUtil;

public abstract class AbstractDatabaseObject
implements DatabaseObject {
    private final Map<String, Object> attributes = new HashMap<String, Object>();
    private static final String CURLY_BRACKET_REGEX = "(.*)!\\{(.*)\\}";
    public static final Pattern CURLY_BRACKET_PATTERN = Pattern.compile("(.*)!\\{(.*)\\}");
    private String snapshotId;

    @Override
    public String getObjectTypeName() {
        return StringUtil.lowerCaseFirst(this.getClass().getSimpleName());
    }

    @Override
    public String getSnapshotId() {
        return this.snapshotId;
    }

    @Override
    public void setSnapshotId(String snapshotId) {
        if (this.snapshotId != null) {
            throw new UnexpectedLiquibaseException("snapshotId already set");
        }
        this.snapshotId = snapshotId;
    }

    @Override
    public boolean snapshotByDefault() {
        return true;
    }

    public int compareTo(Object o) {
        AbstractDatabaseObject that = (AbstractDatabaseObject)o;
        if (this.getSchema() != null && that.getSchema() != null) {
            int compare;
            if (this.shouldIncludeCatalogInSpecification()) {
                String thisCatalogName = this.getSchema().getCatalogName();
                String thatCatalogName = that.getSchema().getCatalogName();
                if (thisCatalogName != null && thatCatalogName != null) {
                    int compare2 = thisCatalogName.compareToIgnoreCase(thatCatalogName);
                    if (compare2 != 0) {
                        return compare2;
                    }
                } else {
                    if (thisCatalogName != null) {
                        return 1;
                    }
                    if (thatCatalogName != null) {
                        return -1;
                    }
                }
            }
            if ((compare = StringUtil.trimToEmpty(this.getSchema().getName()).compareToIgnoreCase(StringUtil.trimToEmpty(that.getSchema().getName()))) != 0) {
                return compare;
            }
        }
        String thisName = this.getName();
        String thatName = that.getName();
        if (thisName != null && thatName != null) {
            return thisName.compareTo(thatName);
        }
        if (thisName != null) {
            return 1;
        }
        if (thatName != null) {
            return -1;
        }
        return 0;
    }

    @Override
    public Set<String> getAttributes() {
        return this.attributes.keySet();
    }

    @Override
    public <T> T getAttribute(String attribute, Class<T> type) {
        return (T)this.attributes.get(attribute);
    }

    @Override
    public <T> T getAttribute(String attribute, T defaultValue) {
        Object value = this.attributes.get(attribute);
        if (value == null) {
            return defaultValue;
        }
        return (T)value;
    }

    @Override
    public DatabaseObject setAttribute(String attribute, Object value) {
        if (value == null) {
            this.attributes.remove(attribute);
        } else {
            this.attributes.put(attribute, value);
        }
        return this;
    }

    @Override
    public String getSerializedObjectName() {
        return this.getObjectTypeName();
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
        TreeSet<String> fields = new TreeSet<String>(this.attributes.keySet());
        fields.add("snapshotId");
        return fields;
    }

    @Override
    public Object getSerializableFieldValue(String field2) {
        if ("snapshotId".equals(field2)) {
            return this.snapshotId;
        }
        if (!this.attributes.containsKey(field2)) {
            throw new UnexpectedLiquibaseException("Unknown field " + field2);
        }
        Object value = this.attributes.get(field2);
        try {
            if (value instanceof Schema) {
                Schema clone = new Schema(((Schema)value).getCatalogName(), ((Schema)value).getName());
                clone.setSnapshotId(((DatabaseObject)value).getSnapshotId());
                return clone;
            }
            if (value instanceof DatabaseObject) {
                DatabaseObject clone = (DatabaseObject)value.getClass().getConstructor(new Class[0]).newInstance(new Object[0]);
                clone.setName(((DatabaseObject)value).getName());
                clone.setSnapshotId(((DatabaseObject)value).getSnapshotId());
                return clone;
            }
        }
        catch (Exception e) {
            throw new UnexpectedLiquibaseException(e);
        }
        return value;
    }

    @Override
    public LiquibaseSerializable.SerializationType getSerializableFieldType(String field2) {
        return LiquibaseSerializable.SerializationType.NAMED_FIELD;
    }

    @Override
    public void load(ParsedNode parsedNode, ResourceAccessor resourceAccessor) throws ParsedNodeException {
        for (ParsedNode child : parsedNode.getChildren()) {
            Matcher matcher;
            String name = child.getName();
            if ("snapshotId".equals(name)) {
                this.snapshotId = child.getValue(String.class);
                continue;
            }
            Class propertyType = ObjectUtil.getPropertyType(this, name);
            if (propertyType != null && Collection.class.isAssignableFrom(propertyType) && !(child.getValue() instanceof Collection)) {
                if (this.attributes.get(name) == null) {
                    this.setAttribute(name, new ArrayList());
                }
                ((List)((Object)this.getAttribute(name, (Object)List.class))).add(child.getValue());
                continue;
            }
            Object childValue = child.getValue();
            if (childValue instanceof String && (matcher = CURLY_BRACKET_PATTERN.matcher((String)childValue)).matches()) {
                String stringValue = matcher.group(1);
                try {
                    Class<?> aClass = Class.forName(matcher.group(2));
                    if (Date.class.isAssignableFrom(aClass)) {
                        Date date = new ISODateFormat().parse(stringValue);
                        childValue = aClass.getConstructor(Long.TYPE).newInstance(date.getTime());
                    } else {
                        childValue = Enum.class.isAssignableFrom(aClass) ? Enum.valueOf(aClass, stringValue) : aClass.getConstructor(String.class).newInstance(stringValue);
                    }
                }
                catch (Exception e) {
                    throw new UnexpectedLiquibaseException(e);
                }
            }
            this.attributes.put(name, childValue);
        }
    }

    @Override
    public ParsedNode serialize() {
        throw new RuntimeException("TODO");
    }

    public String toString() {
        return this.getName();
    }

    public boolean shouldIncludeCatalogInSpecification() {
        return GlobalConfiguration.INCLUDE_CATALOG_IN_SPECIFICATION.getCurrentValue();
    }
}

