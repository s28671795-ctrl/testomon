/*
 * Decompiled with CFR 0.152.
 */
package liquibase.change;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import liquibase.change.Change;
import liquibase.change.ChangeParameterMetaData;
import liquibase.database.Database;
import liquibase.servicelocator.PrioritizedService;
import liquibase.structure.DatabaseObject;

public class ChangeMetaData
implements PrioritizedService {
    public static final int PRIORITY_DEFAULT = 1;
    private final String name;
    private final String description;
    private final int priority;
    private Map<String, ChangeParameterMetaData> parameters;
    private Set<String> appliesTo;
    private final HashMap<String, String> databaseNotes = new HashMap();

    public ChangeMetaData(String name, String description, int priority, String[] appliesTo, Map<String, String> databaseNotes, Set<ChangeParameterMetaData> parameters) {
        if (parameters == null) {
            parameters = new HashSet<ChangeParameterMetaData>();
        }
        if (appliesTo != null && appliesTo.length == 0) {
            appliesTo = null;
        }
        this.name = name;
        this.description = description;
        this.priority = priority;
        this.parameters = new HashMap<String, ChangeParameterMetaData>();
        for (ChangeParameterMetaData param2 : parameters) {
            this.parameters.put(param2.getParameterName(), param2);
        }
        this.parameters = Collections.unmodifiableMap(this.parameters);
        this.appliesTo = null;
        if (appliesTo != null && appliesTo.length > 0) {
            this.appliesTo = Collections.unmodifiableSet(new HashSet<String>(Arrays.asList(appliesTo)));
        }
        if (databaseNotes != null) {
            this.databaseNotes.putAll(databaseNotes);
        }
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    @Override
    public int getPriority() {
        return this.priority;
    }

    public String getNotes(String database) {
        return this.databaseNotes.get(database);
    }

    public Map<String, ChangeParameterMetaData> getParameters() {
        return Collections.unmodifiableMap(this.parameters);
    }

    public Map<String, ChangeParameterMetaData> getSetParameters(Change change) {
        HashMap<String, ChangeParameterMetaData> returnMap = new HashMap<String, ChangeParameterMetaData>();
        for (Map.Entry<String, ChangeParameterMetaData> entry : this.getParameters().entrySet()) {
            if (entry.getValue().getCurrentValue(change) == null) continue;
            returnMap.put(entry.getKey(), entry.getValue());
        }
        return Collections.unmodifiableMap(returnMap);
    }

    public Map<String, ChangeParameterMetaData> getRequiredParameters(Database database) {
        HashMap<String, ChangeParameterMetaData> returnMap = new HashMap<String, ChangeParameterMetaData>();
        for (ChangeParameterMetaData metaData : this.parameters.values()) {
            if (!metaData.isRequiredFor(database)) continue;
            returnMap.put(metaData.getParameterName(), metaData);
        }
        return returnMap;
    }

    public Map<String, ChangeParameterMetaData> getOptionalParameters(Database database) {
        HashMap<String, ChangeParameterMetaData> returnMap = new HashMap<String, ChangeParameterMetaData>();
        for (ChangeParameterMetaData metaData : this.parameters.values()) {
            if (metaData.isRequiredFor(database)) continue;
            returnMap.put(metaData.getParameterName(), metaData);
        }
        return returnMap;
    }

    public Set<String> getAppliesTo() {
        return this.appliesTo;
    }

    public boolean appliesTo(DatabaseObject databaseObject) {
        return this.appliesTo != null && this.appliesTo.contains(databaseObject.getObjectTypeName());
    }
}

