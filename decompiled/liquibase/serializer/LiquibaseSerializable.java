/*
 * Decompiled with CFR 0.152.
 */
package liquibase.serializer;

import java.util.Set;
import liquibase.parser.core.ParsedNode;
import liquibase.parser.core.ParsedNodeException;
import liquibase.resource.ResourceAccessor;

public interface LiquibaseSerializable {
    public static final String STANDARD_CHANGELOG_NAMESPACE = "http://www.liquibase.org/xml/ns/dbchangelog";
    public static final String GENERIC_CHANGELOG_EXTENSION_NAMESPACE = "http://www.liquibase.org/xml/ns/dbchangelog-ext";
    public static final String STANDARD_SNAPSHOT_NAMESPACE = "http://www.liquibase.org/xml/ns/snapshot";
    public static final String GENERIC_SNAPSHOT_EXTENSION_NAMESPACE = "http://www.liquibase.org/xml/ns/snapshot-ext";

    public String getSerializedObjectName();

    public Set<String> getSerializableFields();

    public Object getSerializableFieldValue(String var1);

    public SerializationType getSerializableFieldType(String var1);

    public String getSerializableFieldNamespace(String var1);

    public String getSerializedObjectNamespace();

    public void load(ParsedNode var1, ResourceAccessor var2) throws ParsedNodeException;

    public ParsedNode serialize() throws ParsedNodeException;

    public static enum SerializationType {
        NAMED_FIELD,
        NESTED_OBJECT,
        DIRECT_VALUE;

    }
}

