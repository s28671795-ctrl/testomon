/*
 * Decompiled with CFR 0.152.
 */
package liquibase.change;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import liquibase.change.AddColumnConfig;
import liquibase.change.Change;
import liquibase.change.ColumnConfig;
import liquibase.change.core.LoadDataColumnConfig;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.DatabaseList;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.exception.ValidationErrors;
import liquibase.serializer.LiquibaseSerializable;
import liquibase.sqlgenerator.SqlGeneratorFactory;
import liquibase.statement.DatabaseFunction;
import liquibase.statement.SequenceNextValueFunction;
import liquibase.statement.SqlStatement;
import liquibase.util.ObjectUtil;
import liquibase.util.StringUtil;

public class ChangeParameterMetaData {
    public static final String COMPUTE = "COMPUTE";
    public static final String ALL = "all";
    public static final String NONE = "none";
    private final Change change;
    private final String parameterName;
    private final String description;
    private final Map<String, Object> exampleValues;
    private final String displayName;
    private String dataType;
    private Class dataTypeClass;
    private Type[] dataTypeClassParameters = new Type[0];
    private final String since;
    private Set<String> requiredForDatabase;
    private Set<String> supportedDatabases;
    private final String mustEqualExisting;
    private final LiquibaseSerializable.SerializationType serializationType;
    private final String[] requiredForDatabaseArg;
    private final String[] supportedDatabasesArg;
    private Optional<Method> readMethodRef = Optional.empty();
    private Optional<Method> writeMethodRef = Optional.empty();
    private final String[] alternateParameterNames;

    public ChangeParameterMetaData(Change change, String parameterName, String displayName, String description, Map<String, Object> exampleValues, String since, Type dataType, String[] requiredForDatabase, String[] supportedDatabases, String mustEqualExisting, LiquibaseSerializable.SerializationType serializationType) {
        this(change, parameterName, displayName, description, exampleValues, since, dataType, requiredForDatabase, supportedDatabases, mustEqualExisting, serializationType, null);
    }

    public ChangeParameterMetaData(Change change, String parameterName, String displayName, String description, Map<String, Object> exampleValues, String since, Type dataType, String[] requiredForDatabase, String[] supportedDatabases, String mustEqualExisting, LiquibaseSerializable.SerializationType serializationType, String[] alternateParameterNames) {
        if (parameterName == null) {
            throw new UnexpectedLiquibaseException("Unexpected null parameterName");
        }
        if (parameterName.contains(" ")) {
            throw new UnexpectedLiquibaseException("Unexpected space in parameterName");
        }
        if (displayName == null) {
            throw new UnexpectedLiquibaseException("Unexpected null displayName");
        }
        if (dataType == null) {
            throw new UnexpectedLiquibaseException("Unexpected null dataType");
        }
        this.change = change;
        this.parameterName = parameterName;
        this.displayName = displayName;
        this.description = description;
        this.exampleValues = exampleValues;
        if (dataType instanceof Class) {
            this.dataType = StringUtil.lowerCaseFirst(((Class)dataType).getSimpleName());
            this.dataTypeClass = (Class)dataType;
        } else if (dataType instanceof ParameterizedType) {
            this.dataType = StringUtil.lowerCaseFirst(((Class)((ParameterizedType)dataType).getRawType()).getSimpleName() + " of " + StringUtil.lowerCaseFirst(((Class)((ParameterizedType)dataType).getActualTypeArguments()[0]).getSimpleName()));
            this.dataTypeClass = (Class)((ParameterizedType)dataType).getRawType();
            this.dataTypeClassParameters = ((ParameterizedType)dataType).getActualTypeArguments();
        }
        this.mustEqualExisting = mustEqualExisting;
        this.serializationType = serializationType;
        this.since = since;
        this.supportedDatabasesArg = supportedDatabases;
        this.requiredForDatabaseArg = requiredForDatabase;
        this.alternateParameterNames = alternateParameterNames;
    }

    public ChangeParameterMetaData withAccessors(Method readMethod, Method writeMethod) {
        this.readMethodRef = Optional.ofNullable(readMethod);
        this.writeMethodRef = Optional.ofNullable(writeMethod);
        return this;
    }

    protected Set<String> analyzeSupportedDatabases(String[] supportedDatabases) {
        if (supportedDatabases == null) {
            supportedDatabases = new String[]{COMPUTE};
        }
        HashSet<String> computedDatabases = new HashSet<String>();
        if (supportedDatabases.length == 1 && StringUtil.join(supportedDatabases, ",").equals(COMPUTE)) {
            int validDatabases = 0;
            for (Database database : DatabaseFactory.getInstance().getImplementedDatabases()) {
                if (database.getShortName() == null || "unsupported".equals(database.getShortName()) || !this.change.supports(database)) continue;
                try {
                    if (this.change.generateStatementsVolatile(database)) continue;
                    Change testChange = (Change)this.change.getClass().getConstructor(new Class[0]).newInstance(new Object[0]);
                    ValidationErrors originalErrors = ChangeParameterMetaData.getStatementErrors(testChange, database);
                    this.setValue(testChange, this.getExampleValue(database));
                    ValidationErrors finalErrors = ChangeParameterMetaData.getStatementErrors(testChange, database);
                    if (finalErrors.getUnsupportedErrorMessages().isEmpty() || finalErrors.getUnsupportedErrorMessages().size() == originalErrors.getUnsupportedErrorMessages().size()) {
                        computedDatabases.add(database.getShortName());
                    }
                    ++validDatabases;
                }
                catch (Exception exception) {}
            }
            if (validDatabases == 0) {
                return new HashSet<String>(Collections.singletonList(ALL));
            }
            if (computedDatabases.size() == validDatabases) {
                computedDatabases = new HashSet<String>(Collections.singletonList(ALL));
            }
            computedDatabases.remove(NONE);
            return computedDatabases;
        }
        return new HashSet<String>(Arrays.asList(supportedDatabases));
    }

    protected Set<String> analyzeRequiredDatabases(String[] requiredDatabases) {
        if (requiredDatabases == null) {
            requiredDatabases = new String[]{COMPUTE};
        }
        HashSet<Object> computedDatabases = new HashSet();
        if (requiredDatabases.length == 1 && StringUtil.join(requiredDatabases, ",").equals(COMPUTE)) {
            int validDatabases = 0;
            for (Database database : DatabaseFactory.getInstance().getImplementedDatabases()) {
                try {
                    if (this.change.generateStatementsVolatile(database)) continue;
                    Change testChange = (Change)this.change.getClass().getConstructor(new Class[0]).newInstance(new Object[0]);
                    ValidationErrors originalErrors = ChangeParameterMetaData.getStatementErrors(testChange, database);
                    this.setValue(testChange, this.getExampleValue(database));
                    ValidationErrors finalErrors = ChangeParameterMetaData.getStatementErrors(testChange, database);
                    if (!originalErrors.getRequiredErrorMessages().isEmpty() && finalErrors.getRequiredErrorMessages().size() < originalErrors.getRequiredErrorMessages().size()) {
                        computedDatabases.add(database.getShortName());
                    }
                    ++validDatabases;
                }
                catch (Exception exception) {}
            }
            if (validDatabases == 0) {
                return new HashSet<String>();
            }
            if (computedDatabases.size() == validDatabases) {
                computedDatabases = new HashSet<String>(Collections.singletonList(ALL));
            }
            computedDatabases.remove(NONE);
        } else {
            computedDatabases = new HashSet<String>(Arrays.asList(requiredDatabases));
        }
        computedDatabases.remove(NONE);
        return computedDatabases;
    }

    private static ValidationErrors getStatementErrors(Change testChange, Database database) {
        SqlStatement[] statements;
        ValidationErrors errors2 = new ValidationErrors();
        for (SqlStatement statement : statements = testChange.generateStatements(database)) {
            errors2.addAll(SqlGeneratorFactory.getInstance().validate(statement, database));
        }
        return errors2;
    }

    public String getParameterName() {
        return this.parameterName;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public String getDataType() {
        return this.dataType;
    }

    public Set<String> getRequiredForDatabase() {
        if (this.requiredForDatabase == null) {
            this.requiredForDatabase = Collections.unmodifiableSet(this.analyzeRequiredDatabases(this.requiredForDatabaseArg));
        }
        return this.requiredForDatabase;
    }

    public Set<String> getSupportedDatabases() {
        if (this.supportedDatabases == null) {
            this.supportedDatabases = Collections.unmodifiableSet(this.analyzeSupportedDatabases(this.supportedDatabasesArg));
        }
        return this.supportedDatabases;
    }

    public boolean isRequiredFor(Database database) {
        return this.getRequiredForDatabase().contains(ALL) || this.getRequiredForDatabase().contains(database.getShortName());
    }

    public boolean supports(Database database) {
        return this.getSupportedDatabases().contains(ALL) || this.getSupportedDatabases().contains(database.getShortName());
    }

    public Object getCurrentValue(Change change) {
        try {
            return this.getReadMethod(change).invoke((Object)change, new Object[0]);
        }
        catch (UnexpectedLiquibaseException e) {
            throw e;
        }
        catch (Exception e) {
            throw new UnexpectedLiquibaseException(e);
        }
    }

    private Method getReadMethod(Change change) {
        if (this.readMethodRef.isPresent()) {
            return this.readMethodRef.orElseThrow(() -> new UnexpectedLiquibaseException("No readMethod for " + this.parameterName));
        }
        try {
            this.readMethodRef = Optional.empty();
            for (PropertyDescriptor descriptor : ObjectUtil.getDescriptors(change.getClass())) {
                if (!descriptor.getDisplayName().equals(this.parameterName)) continue;
                Method readMethod = descriptor.getReadMethod();
                if (readMethod == null) {
                    readMethod = change.getClass().getMethod("is" + StringUtil.upperCaseFirst(descriptor.getName()), new Class[0]);
                }
                this.readMethodRef = Optional.of(readMethod);
                return readMethod;
            }
        }
        catch (Exception e) {
            throw new UnexpectedLiquibaseException(e);
        }
        throw new UnexpectedLiquibaseException("Could not find readMethod for " + this.parameterName);
    }

    public void setValue(Change change, Object value) {
        if (value instanceof String && !"string".equals(this.dataType)) {
            try {
                switch (this.dataType) {
                    case "bigInteger": {
                        value = new BigInteger((String)value);
                        break;
                    }
                    case "databaseFunction": {
                        value = new DatabaseFunction((String)value);
                        break;
                    }
                    default: {
                        throw new UnexpectedLiquibaseException("Unknown data type: " + this.dataType);
                    }
                }
            }
            catch (Exception e) {
                throw new UnexpectedLiquibaseException("Cannot convert string value '" + value + "' to " + this.dataType + ": " + e.getMessage());
            }
        }
        try {
            Class<?> actualType;
            Method writeMethod = this.getWriteMethod(change);
            Class<?> expectedWriteType = writeMethod.getParameterTypes()[0];
            if (value != null && !expectedWriteType.isAssignableFrom(actualType = value.getClass())) {
                if (expectedWriteType.equals(String.class)) {
                    value = value.toString();
                } else if (!ChangeParameterMetaData.allowUnboxing(actualType, expectedWriteType)) {
                    throw new UnexpectedLiquibaseException("Could not convert " + actualType.getName() + " to " + expectedWriteType.getName());
                }
            }
            writeMethod.invoke((Object)change, value);
        }
        catch (UnexpectedLiquibaseException e) {
            throw e;
        }
        catch (Exception e) {
            throw new UnexpectedLiquibaseException("Error setting " + this.parameterName + " to " + value, e);
        }
    }

    private static boolean allowUnboxing(Class<?> actual, Class<?> expected) {
        if (!expected.isPrimitive()) {
            return false;
        }
        return actual.equals(Boolean.class) && expected.equals(Boolean.TYPE) || actual.equals(Byte.class) && expected.equals(Byte.TYPE) || actual.equals(Character.class) && expected.equals(Character.TYPE) || actual.equals(Double.class) && expected.equals(Double.TYPE) || actual.equals(Float.class) && expected.equals(Float.TYPE) || actual.equals(Integer.class) && expected.equals(Integer.TYPE) || actual.equals(Long.class) && expected.equals(Long.TYPE) || actual.equals(Short.class) && expected.equals(Short.TYPE);
    }

    private Method getWriteMethod(Change change) {
        if (this.writeMethodRef.isPresent()) {
            return this.writeMethodRef.orElseThrow(() -> new UnexpectedLiquibaseException("No writeMethod for " + this.parameterName));
        }
        try {
            this.writeMethodRef = Optional.empty();
            for (PropertyDescriptor descriptor : ObjectUtil.getDescriptors(change.getClass())) {
                if (!descriptor.getDisplayName().equals(this.parameterName)) continue;
                Method writeMethod = descriptor.getWriteMethod();
                if (writeMethod != null) {
                    this.writeMethodRef = Optional.of(writeMethod);
                    return writeMethod;
                }
                break;
            }
        }
        catch (Exception e) {
            throw new UnexpectedLiquibaseException(e);
        }
        throw new UnexpectedLiquibaseException("Could not find writeMethod for " + this.parameterName);
    }

    public String getMustEqualExisting() {
        return this.mustEqualExisting;
    }

    public LiquibaseSerializable.SerializationType getSerializationType() {
        return this.serializationType;
    }

    public Object getExampleValue(Database database) {
        if (this.exampleValues != null) {
            Object exampleValue = null;
            for (Map.Entry<String, Object> entry : this.exampleValues.entrySet()) {
                if (ALL.equalsIgnoreCase(entry.getKey())) {
                    exampleValue = entry.getValue();
                    continue;
                }
                if (!DatabaseList.definitionMatches(entry.getKey(), database, false)) continue;
                return entry.getValue();
            }
            if (exampleValue != null) {
                return exampleValue;
            }
        }
        HashMap<String, String> standardExamples = new HashMap<String, String>();
        standardExamples.put("tableName", "person");
        standardExamples.put("schemaName", "public");
        standardExamples.put("tableSchemaName", "public");
        standardExamples.put("catalogName", "cat");
        standardExamples.put("tableCatalogName", "cat");
        standardExamples.put("columnName", "id");
        standardExamples.put("columnNames", "id, name");
        standardExamples.put("indexName", "idx_address");
        standardExamples.put("columnDataType", "int");
        standardExamples.put("dataType", "int");
        standardExamples.put("sequenceName", "seq_id");
        standardExamples.put("viewName", "v_person");
        standardExamples.put("constraintName", "const_name");
        standardExamples.put("primaryKey", "pk_id");
        if (standardExamples.containsKey(this.parameterName)) {
            return standardExamples.get(this.parameterName);
        }
        for (String prefix : new String[]{"base", "referenced", "new", "old"}) {
            String mainName;
            if (!this.parameterName.startsWith(prefix) || !standardExamples.containsKey(mainName = StringUtil.lowerCaseFirst(this.parameterName.replaceFirst("^" + prefix, "")))) continue;
            return standardExamples.get(mainName);
        }
        switch (this.dataType) {
            case "string": {
                return "A String";
            }
            case "integer": {
                return 3;
            }
            case "boolean": {
                return true;
            }
            case "bigInteger": {
                return new BigInteger("371717");
            }
            case "list": {
                return null;
            }
            case "sequenceNextValueFunction": {
                return new SequenceNextValueFunction("seq_name");
            }
            case "databaseFunction": {
                return new DatabaseFunction("now");
            }
            case "list of columnConfig": {
                ArrayList<ColumnConfig> list = new ArrayList<ColumnConfig>();
                list.add(new ColumnConfig().setName("id").setType("int"));
                return list;
            }
            case "list of addColumnConfig": {
                ArrayList<ColumnConfig> list = new ArrayList<ColumnConfig>();
                list.add(new AddColumnConfig().setName("id").setType("int"));
                return list;
            }
            case "list of loadDataColumnConfig": {
                ArrayList<ColumnConfig> list = new ArrayList<ColumnConfig>();
                list.add(new LoadDataColumnConfig().setName("id").setType("int"));
                return list;
            }
        }
        throw new UnexpectedLiquibaseException("Unknown dataType " + this.dataType + " for " + this.getParameterName());
    }

    public String getDescription() {
        if (this.description != null) {
            return this.description;
        }
        HashMap<String, String> standardDescriptions = new HashMap<String, String>();
        standardDescriptions.put("tableName", "Name of the table");
        standardDescriptions.put("schemaName", "Name of the schema");
        standardDescriptions.put("catalogName", "Name of the catalog");
        standardDescriptions.put("columnName", "Name of the column");
        return StringUtil.trimToEmpty((String)standardDescriptions.get(this.parameterName));
    }

    public String toString() {
        return (this.change != null ? this.change + "." : "") + this.getParameterName();
    }

    public Class getDataTypeClass() {
        return this.dataTypeClass;
    }

    public Type[] getDataTypeClassParameters() {
        return this.dataTypeClassParameters;
    }

    public String getSince() {
        return this.since;
    }

    public String[] getAlternateParameterNames() {
        return this.alternateParameterNames;
    }
}

