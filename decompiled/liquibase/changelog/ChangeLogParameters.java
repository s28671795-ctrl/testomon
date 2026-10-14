/*
 * Decompiled with CFR 0.152.
 */
package liquibase.changelog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import liquibase.ContextExpression;
import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Labels;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.changelog.ExpressionExpander;
import liquibase.database.Database;
import liquibase.database.DatabaseList;
import liquibase.exception.DatabaseException;
import liquibase.exception.UnknownChangeLogParameterException;
import liquibase.structure.core.Schema;
import liquibase.structure.core.Sequence;
import liquibase.util.StringUtil;

public class ChangeLogParameters {
    private final List<ChangeLogParameter> globalParameters = new ArrayList<ChangeLogParameter>();
    private final Map<String, List<ChangeLogParameter>> localParameters = new HashMap<String, List<ChangeLogParameter>>();
    private final ExpressionExpander expressionExpander;
    private String filterDatabase;
    private Contexts filterContexts;
    private LabelExpression filterLabels;

    public ChangeLogParameters() {
        this(null);
    }

    public ChangeLogParameters(Database database) {
        this.globalParameters.addAll(System.getenv().entrySet().stream().map(e -> new ChangeLogParameter((String)e.getKey(), e.getValue())).collect(Collectors.toList()));
        this.globalParameters.addAll(System.getProperties().entrySet().stream().map(e -> new ChangeLogParameter(String.valueOf(e.getKey()), e.getValue())).collect(Collectors.toList()));
        if (database != null) {
            this.set("database.autoIncrementClause", database.getAutoIncrementClause(null, null, null, null));
            this.set("database.currentDateTimeFunction", database.getCurrentDateTimeFunction());
            this.set("database.databaseChangeLogLockTableName", database.getDatabaseChangeLogLockTableName());
            this.set("database.databaseChangeLogTableName", database.getDatabaseChangeLogTableName());
            try {
                this.set("database.databaseMajorVersion", database.getDatabaseMajorVersion());
            }
            catch (DatabaseException databaseException) {
                // empty catch block
            }
            try {
                this.set("database.databaseMinorVersion", database.getDatabaseMinorVersion());
            }
            catch (DatabaseException databaseException) {
                // empty catch block
            }
            this.set("database.databaseProductName", database.getDatabaseProductName());
            try {
                this.set("database.databaseProductVersion", database.getDatabaseProductVersion());
            }
            catch (DatabaseException databaseException) {
                // empty catch block
            }
            this.set("database.defaultCatalogName", database.getDefaultCatalogName());
            this.set("database.defaultSchemaName", database.getDefaultSchemaName());
            this.set("database.defaultSchemaNamePrefix", StringUtil.trimToNull(database.getDefaultSchemaName()) == null ? "" : "." + database.getDefaultSchemaName());
            this.set("database.lineComment", database.getLineComment());
            this.set("database.liquibaseSchemaName", database.getLiquibaseSchemaName());
            this.set("database.typeName", database.getShortName());
            try {
                this.set("database.isSafeToRunUpdate", database.isSafeToRunUpdate());
            }
            catch (DatabaseException databaseException) {
                // empty catch block
            }
            this.set("database.requiresPassword", database.requiresPassword());
            this.set("database.requiresUsername", database.requiresUsername());
            this.set("database.supportsForeignKeyDisable", database.supportsForeignKeyDisable());
            this.set("database.supportsInitiallyDeferrableColumns", database.supportsInitiallyDeferrableColumns());
            this.set("database.supportsRestrictForeignKeys", database.supportsRestrictForeignKeys());
            this.set("database.supportsSchemas", database.supports(Schema.class));
            this.set("database.supportsSequences", database.supports(Sequence.class));
            this.set("database.supportsTablespaces", database.supportsTablespaces());
            this.set("database.supportsNotNullConstraintNames", database.supportsNotNullConstraintNames());
            this.filterDatabase = database.getShortName();
        }
        this.expressionExpander = new ExpressionExpander(this);
        this.filterContexts = new Contexts();
        this.filterLabels = new LabelExpression();
    }

    public void set(String parameter, Object value) {
        this.set(parameter, value, new ContextExpression(), new Labels(), new String[0]);
    }

    public void setLocal(String parameter, Object value, DatabaseChangeLog changeLog) {
        this.setLocal(parameter, value, changeLog, new ContextExpression(), new Labels(), new String[0]);
    }

    public void set(String key, Object value, ContextExpression contexts, Labels labels, String databases, boolean globalParam, DatabaseChangeLog changeLog) {
        String[] parsedDatabases = null;
        if (databases != null && databases.length() > 0) {
            parsedDatabases = StringUtil.splitAndTrim(databases, ",").toArray(new String[0]);
        }
        if (globalParam) {
            this.set(key, value, contexts, labels, parsedDatabases);
        } else {
            this.setLocal(key, value, changeLog, contexts, labels, parsedDatabases);
        }
    }

    public void set(String key, Object value, String contexts, String labels, String databases, boolean globalParam, DatabaseChangeLog changeLog) {
        this.set(key, value, new ContextExpression(contexts), new Labels(labels), databases, globalParam, changeLog);
    }

    public void set(String key, Object value, ContextExpression contexts, Labels labels, String ... databases) {
        this.globalParameters.add(new ChangeLogParameter(key, value, contexts, labels, databases));
    }

    public void setLocal(String key, Object value, DatabaseChangeLog changeLog, ContextExpression contexts, Labels labels, String ... databases) {
        if (changeLog == null) {
            throw new IllegalArgumentException("changeLog cannot be null when setting a local parameter");
        }
        String changelogKey = this.getLocalKey(changeLog);
        List<ChangeLogParameter> localParams = this.localParameters.get(changelogKey);
        if (localParams == null) {
            localParams = new ArrayList<ChangeLogParameter>();
            this.localParameters.put(changelogKey, localParams);
        }
        localParams.add(new ChangeLogParameter(key, value, contexts, labels, databases));
    }

    public Object getValue(String key, DatabaseChangeLog changeLog) {
        ChangeLogParameter param2 = this.getChangelogParameter(key, changeLog, this.getFilter());
        if (param2 == null) {
            return null;
        }
        return param2.getValue();
    }

    public boolean hasValue(String key, DatabaseChangeLog changeLog) {
        return this.getChangelogParameter(key, changeLog, this.getFilter()) != null;
    }

    public String expandExpressions(String string, DatabaseChangeLog changeLog) throws UnknownChangeLogParameterException {
        return this.expressionExpander.expandExpressions(string, changeLog);
    }

    public Contexts getContexts() {
        return this.filterContexts;
    }

    public void setContexts(Contexts contexts) {
        this.filterContexts = contexts;
    }

    public LabelExpression getLabels() {
        return this.filterLabels;
    }

    public void setLabels(LabelExpression labels) {
        this.filterLabels = labels;
    }

    public String getDatabase() {
        return this.filterDatabase;
    }

    public void setDatabase(String filterDatabase) {
        this.filterDatabase = filterDatabase;
    }

    private Filter getFilter() {
        return new Filter(this.filterDatabase, this.filterContexts, this.filterLabels);
    }

    private ChangeLogParameter getChangelogParameter(String key, DatabaseChangeLog changeLog, Filter filter2) {
        List<ChangeLogParameter> localList = null;
        if (changeLog != null && (localList = this.localParameters.get(this.getLocalKey(changeLog))) != null) {
            localList = new ArrayList<ChangeLogParameter>(localList);
            Collections.reverse(localList);
        }
        for (List paramList : Arrays.asList(this.globalParameters, localList)) {
            if (paramList == null) continue;
            for (ChangeLogParameter parameter : paramList) {
                if (!parameter.getKey().equalsIgnoreCase(key) || filter2 != null && !filter2.matches(parameter)) continue;
                return parameter;
            }
        }
        return null;
    }

    private String getLocalKey(DatabaseChangeLog changeLog) {
        if (changeLog == null) {
            return "null changelog path";
        }
        return changeLog.getLogicalFilePath();
    }

    private static class ChangeLogParameter {
        private final String key;
        private final Object value;
        private final ContextExpression validContexts;
        private final Labels validLabels;
        private final List<String> validDatabases;

        public ChangeLogParameter(String key, Object value) {
            this(key, value, null, null, null);
        }

        public ChangeLogParameter(String key, Object value, ContextExpression validContexts, Labels labels, String[] validDatabases) {
            this.key = key;
            this.value = value;
            this.validContexts = validContexts == null ? new ContextExpression() : validContexts;
            this.validLabels = labels == null ? new Labels() : labels;
            this.validDatabases = validDatabases == null ? null : Arrays.asList(validDatabases);
        }

        public Labels getLabels() {
            return this.validLabels;
        }

        public String toString() {
            return this.getValue().toString();
        }

        public String getKey() {
            return this.key;
        }

        public Object getValue() {
            return this.value;
        }

        public ContextExpression getValidContexts() {
            return this.validContexts;
        }

        public List<String> getValidDatabases() {
            return this.validDatabases;
        }
    }

    private static class Filter {
        private final Contexts contexts;
        private final LabelExpression labels;
        private final String database;

        public Filter(String database, Contexts contexts, LabelExpression labels) {
            this.contexts = contexts;
            this.labels = labels;
            this.database = database;
        }

        public boolean matches(ChangeLogParameter parameter) {
            return !(this.labels != null && !this.labels.matches(parameter.getLabels()) || this.contexts != null && !parameter.getValidContexts().matches(this.contexts) || this.database != null && !DatabaseList.definitionMatches(parameter.getValidDatabases(), this.database, true));
        }
    }
}

