/*
 * Decompiled with CFR 0.152.
 */
package liquibase.change;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;
import liquibase.ChecksumVersion;
import liquibase.Scope;
import liquibase.change.Change;
import liquibase.change.ChangeFactory;
import liquibase.change.ChangeMetaData;
import liquibase.change.ChangeParameterMetaData;
import liquibase.change.ChangeStatus;
import liquibase.change.ChangeWithColumns;
import liquibase.change.CheckSum;
import liquibase.change.ColumnConfig;
import liquibase.change.DatabaseChange;
import liquibase.change.DatabaseChangeNote;
import liquibase.change.DatabaseChangeProperty;
import liquibase.change.visitor.ChangeVisitor;
import liquibase.changelog.ChangeSet;
import liquibase.database.Database;
import liquibase.exception.LiquibaseException;
import liquibase.exception.RollbackImpossibleException;
import liquibase.exception.SetupException;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.exception.ValidationErrors;
import liquibase.exception.Warnings;
import liquibase.executor.Executor;
import liquibase.executor.ExecutorService;
import liquibase.executor.LoggingExecutor;
import liquibase.parser.core.ParsedNode;
import liquibase.parser.core.ParsedNodeException;
import liquibase.plugin.AbstractPlugin;
import liquibase.resource.ResourceAccessor;
import liquibase.serializer.LiquibaseSerializable;
import liquibase.serializer.core.string.StringChangeLogSerializer;
import liquibase.sqlgenerator.SqlGeneratorFactory;
import liquibase.statement.SqlStatement;
import liquibase.structure.DatabaseObject;
import liquibase.util.BooleanUtil;
import liquibase.util.ObjectUtil;
import liquibase.util.StringUtil;

public abstract class AbstractChange
extends AbstractPlugin
implements Change {
    protected static final String NODENAME_COLUMN = "column";
    private static final Pattern ALPHABET = Pattern.compile("([A-Z])");
    private ChangeSet changeSet;

    @Override
    public void finishInitialization() throws SetupException {
    }

    @Override
    public ChangeMetaData createChangeMetaData() {
        try {
            DatabaseChange databaseChange = this.getClass().getAnnotation(DatabaseChange.class);
            if (databaseChange == null) {
                throw new UnexpectedLiquibaseException("No @DatabaseChange annotation for " + this.getClass().getName());
            }
            HashSet<ChangeParameterMetaData> params = new HashSet<ChangeParameterMetaData>();
            for (PropertyDescriptor property : ObjectUtil.getDescriptors(this.getClass())) {
                DatabaseChangeProperty annotation;
                if (this.isInvalidProperty(property)) continue;
                Method readMethod = property.getReadMethod();
                Method writeMethod = property.getWriteMethod();
                if (readMethod == null) {
                    try {
                        readMethod = this.getClass().getMethod("is" + StringUtil.upperCaseFirst(property.getName()), new Class[0]);
                    }
                    catch (NoSuchMethodException | SecurityException exception) {
                        // empty catch block
                    }
                }
                if (readMethod == null || writeMethod == null || (annotation = this.findDatabaseChangePropertyAnnotationMatchingCurrentChecksumVersion(readMethod)) != null && !annotation.isChangeProperty()) continue;
                params.add(this.createChangeParameterMetadata(property, readMethod));
            }
            HashMap<String, String> notes = new HashMap<String, String>();
            for (DatabaseChangeNote note : databaseChange.databaseNotes()) {
                notes.put(note.database(), note.notes());
            }
            return new ChangeMetaData(databaseChange.name(), databaseChange.description(), databaseChange.priority(), databaseChange.appliesTo(), notes, params);
        }
        catch (IntrospectionException | UnexpectedLiquibaseException e) {
            throw new UnexpectedLiquibaseException(e);
        }
    }

    private DatabaseChangeProperty findDatabaseChangePropertyAnnotationMatchingCurrentChecksumVersion(Method readMethod) {
        DatabaseChangeProperty[] annotations = (DatabaseChangeProperty[])readMethod.getAnnotationsByType(DatabaseChangeProperty.class);
        if (annotations.length == 1) {
            return annotations[0];
        }
        if (annotations.length > 1) {
            Optional<DatabaseChangeProperty> versionMatchingAnnotation = Arrays.stream(annotations).filter(ann -> Arrays.stream(ann.version()).anyMatch(annotationVersion -> annotationVersion == Scope.getCurrentScope().getChecksumVersion())).findFirst();
            return versionMatchingAnnotation.orElseGet(() -> Arrays.stream(annotations).filter(ann -> ann.version().length == 0).findFirst().orElse(null));
        }
        return null;
    }

    protected boolean isInvalidProperty(PropertyDescriptor property) {
        return "metaClass".equals(property.getDisplayName());
    }

    protected ChangeParameterMetaData createChangeParameterMetadata(String parameterName) {
        try {
            PropertyDescriptor property = null;
            for (PropertyDescriptor prop : ObjectUtil.getDescriptors(this.getClass())) {
                if (!prop.getDisplayName().equals(parameterName)) continue;
                property = prop;
                break;
            }
            if (property == null) {
                throw new UnexpectedLiquibaseException("Could not find property " + parameterName);
            }
            Method readMethod = property.getReadMethod();
            if (readMethod == null) {
                readMethod = this.getClass().getMethod("is" + StringUtil.upperCaseFirst(property.getName()), new Class[0]);
            }
            return this.createChangeParameterMetadata(property, readMethod);
        }
        catch (IntrospectionException | NoSuchMethodException | SecurityException e) {
            throw new UnexpectedLiquibaseException(e);
        }
    }

    private ChangeParameterMetaData createChangeParameterMetadata(PropertyDescriptor property, Method readMethod) {
        String parameterName = property.getDisplayName();
        String displayName = ALPHABET.matcher(parameterName).replaceAll(" $1");
        displayName = displayName.substring(0, 1).toUpperCase() + displayName.substring(1);
        Type type = readMethod.getGenericReturnType();
        DatabaseChangeProperty changePropertyAnnotation = this.findDatabaseChangePropertyAnnotationMatchingCurrentChecksumVersion(readMethod);
        String mustEqualExisting = this.createMustEqualExistingMetaData(parameterName, changePropertyAnnotation);
        String description = this.createDescriptionMetaData(parameterName, changePropertyAnnotation);
        Map<String, Object> examples = this.createExampleValueMetaData(parameterName, changePropertyAnnotation);
        String since = this.createSinceMetaData(parameterName, changePropertyAnnotation);
        LiquibaseSerializable.SerializationType serializationType = this.createSerializationTypeMetaData(parameterName, changePropertyAnnotation);
        String[] requiredForDatabase = this.createRequiredDatabasesMetaData(parameterName, changePropertyAnnotation);
        String[] supportsDatabase = this.createSupportedDatabasesMetaData(parameterName, changePropertyAnnotation);
        String[] alternatePropertyNames = this.createAlternateParameterNames(changePropertyAnnotation);
        return new ChangeParameterMetaData(this, parameterName, displayName, description, examples, since, type, requiredForDatabase, supportsDatabase, mustEqualExisting, serializationType, alternatePropertyNames).withAccessors(readMethod, property.getWriteMethod());
    }

    protected String createSinceMetaData(String parameterName, DatabaseChangeProperty changePropertyAnnotation) {
        if (changePropertyAnnotation == null) {
            return null;
        }
        return StringUtil.trimToNull(changePropertyAnnotation.since());
    }

    protected String createDescriptionMetaData(String parameterName, DatabaseChangeProperty changePropertyAnnotation) {
        if (changePropertyAnnotation == null) {
            return null;
        }
        return StringUtil.trimToNull(changePropertyAnnotation.description());
    }

    protected LiquibaseSerializable.SerializationType createSerializationTypeMetaData(String parameterName, DatabaseChangeProperty changePropertyAnnotation) {
        if (changePropertyAnnotation == null) {
            return LiquibaseSerializable.SerializationType.NAMED_FIELD;
        }
        return changePropertyAnnotation.serializationType();
    }

    protected String createMustEqualExistingMetaData(String parameterName, DatabaseChangeProperty changePropertyAnnotation) {
        if (changePropertyAnnotation == null) {
            return null;
        }
        return changePropertyAnnotation.mustEqualExisting();
    }

    protected Map<String, Object> createExampleValueMetaData(String parameterName, DatabaseChangeProperty changePropertyAnnotation) {
        if (changePropertyAnnotation == null) {
            return null;
        }
        HashMap<String, Object> examples = new HashMap<String, Object>();
        examples.put("all", StringUtil.trimToNull(changePropertyAnnotation.exampleValue()));
        return examples;
    }

    protected String[] createRequiredDatabasesMetaData(String parameterName, DatabaseChangeProperty changePropertyAnnotation) {
        if (changePropertyAnnotation == null) {
            return new String[]{"COMPUTE"};
        }
        return changePropertyAnnotation.requiredForDatabase();
    }

    protected String[] createSupportedDatabasesMetaData(String parameterName, DatabaseChangeProperty changePropertyAnnotation) {
        if (changePropertyAnnotation == null) {
            return new String[]{"COMPUTE"};
        }
        return changePropertyAnnotation.supportsDatabase();
    }

    protected String[] createAlternateParameterNames(DatabaseChangeProperty changePropertyAnnotation) {
        if (changePropertyAnnotation == null) {
            return new String[0];
        }
        return changePropertyAnnotation.alternatePropertyNames();
    }

    @Override
    @DatabaseChangeProperty(isChangeProperty=false)
    public ChangeSet getChangeSet() {
        return this.changeSet;
    }

    @Override
    public void setChangeSet(ChangeSet changeSet) {
        this.changeSet = changeSet;
    }

    @Override
    public boolean generateStatementsVolatile(Database database) {
        SqlStatement[] statements = this.generateStatements(database);
        if (statements == null) {
            return false;
        }
        for (SqlStatement statement : statements) {
            if (!SqlGeneratorFactory.getInstance().generateStatementsVolatile(statement, database)) continue;
            return true;
        }
        return false;
    }

    @Override
    public boolean generateRollbackStatementsVolatile(Database database) {
        if (this.generateStatementsVolatile(database)) {
            return true;
        }
        SqlStatement[] statements = this.generateStatements(database);
        if (statements == null) {
            return false;
        }
        for (SqlStatement statement : statements) {
            if (!SqlGeneratorFactory.getInstance().generateRollbackStatementsVolatile(statement, database)) continue;
            return true;
        }
        return false;
    }

    @Override
    public boolean supports(Database database) {
        if (this.generateStatementsVolatile(database)) {
            return true;
        }
        SqlStatement[] statements = this.generateStatements(database);
        if (statements == null) {
            return true;
        }
        for (SqlStatement statement : statements) {
            if (SqlGeneratorFactory.getInstance().supports(statement, database)) continue;
            return false;
        }
        return true;
    }

    @Override
    public Warnings warn(Database database) {
        Warnings warnings = new Warnings();
        if (this.generateStatementsVolatile(database)) {
            return warnings;
        }
        SqlStatement[] statements = this.generateStatements(database);
        if (statements == null) {
            return warnings;
        }
        for (SqlStatement statement : statements) {
            if (SqlGeneratorFactory.getInstance().supports(statement, database)) {
                warnings.addAll(SqlGeneratorFactory.getInstance().warn(statement, database));
                continue;
            }
            if (!statement.skipOnUnsupported()) continue;
            warnings.addWarning(statement.getClass().getName() + " is not supported on " + database.getDisplayName() + ", but " + Scope.getCurrentScope().getSingleton(ChangeFactory.class).getChangeMetaData(this).getName() + " will still execute");
        }
        return warnings;
    }

    @Override
    public ValidationErrors validate(Database database) {
        ValidationErrors changeValidationErrors = new ValidationErrors(this);
        for (ChangeParameterMetaData param2 : Scope.getCurrentScope().getSingleton(ChangeFactory.class).getChangeMetaData(this).getParameters().values()) {
            if (!param2.isRequiredFor(database)) continue;
            changeValidationErrors.checkRequiredField(param2.getParameterName(), param2.getCurrentValue(this), " on " + database.getShortName());
        }
        if (changeValidationErrors.hasErrors()) {
            return changeValidationErrors;
        }
        if (!this.generateStatementsVolatile(database)) {
            String unsupportedWarning = Scope.getCurrentScope().getSingleton(ChangeFactory.class).getChangeMetaData(this).getName() + " is not supported on " + database.getDisplayName();
            boolean sawUnsupportedError = false;
            SqlStatement[] statements = this.generateStatements(database);
            if (statements != null) {
                for (SqlStatement statement : statements) {
                    boolean supported = SqlGeneratorFactory.getInstance().supports(statement, database);
                    if (!supported && !sawUnsupportedError) {
                        if (statement.skipOnUnsupported()) continue;
                        changeValidationErrors.addError(unsupportedWarning);
                        sawUnsupportedError = true;
                        continue;
                    }
                    changeValidationErrors.addAll(SqlGeneratorFactory.getInstance().validate(statement, database));
                }
            }
        }
        return changeValidationErrors;
    }

    @Override
    public ChangeStatus checkStatus(Database database) {
        return new ChangeStatus().unknown("Not implemented");
    }

    public boolean shouldExecuteChange(Database database) {
        Boolean shouldExecute = (Boolean)((Object)Scope.getCurrentScope().get("shouldExecute", Boolean.class));
        Executor executor2 = Scope.getCurrentScope().getSingleton(ExecutorService.class).getExecutor("jdbc", database);
        return !(executor2 instanceof LoggingExecutor) && (shouldExecute == null || BooleanUtil.isTrue(shouldExecute));
    }

    @Override
    public SqlStatement[] generateRollbackStatements(Database database) throws RollbackImpossibleException {
        return this.generateRollbackStatementsFromInverse(database);
    }

    @Override
    public boolean supportsRollback(Database database) {
        return this.createInverses() != null;
    }

    @Override
    public CheckSum generateCheckSum() {
        return CheckSum.compute(new StringChangeLogSerializer(new StringChangeLogSerializer.FieldFilter(){

            @Override
            public boolean include(Object obj, String field2, Object value) {
                if (Arrays.stream(AbstractChange.this.getExcludedFieldFilters(Scope.getCurrentScope().getChecksumVersion())).anyMatch(filter2 -> filter2.equals(field2))) {
                    return false;
                }
                return super.include(obj, field2, value);
            }
        }).serialize(this, false));
    }

    public String[] getExcludedFieldFilters(ChecksumVersion version) {
        return new String[0];
    }

    private SqlStatement[] generateRollbackStatementsFromInverse(Database database) throws RollbackImpossibleException {
        Change[] inverses = this.createInverses();
        if (inverses == null) {
            throw new RollbackImpossibleException("No inverse to " + this.getClass().getName() + " created");
        }
        ArrayList<SqlStatement> statements = new ArrayList<SqlStatement>();
        try {
            for (Change inverse : inverses) {
                if (!inverse.supports(database)) {
                    throw new RollbackImpossibleException(Scope.getCurrentScope().getSingleton(ChangeFactory.class).getChangeMetaData(inverse).getName() + " is not supported on " + database.getDisplayName());
                }
                statements.addAll(Arrays.asList(inverse.generateStatements(database)));
            }
        }
        catch (LiquibaseException e) {
            throw new RollbackImpossibleException(e);
        }
        return statements.toArray(SqlStatement.EMPTY_SQL_STATEMENT);
    }

    protected Change[] createInverses() {
        return null;
    }

    @DatabaseChangeProperty(isChangeProperty=false)
    @Deprecated
    public ResourceAccessor getResourceAccessor() {
        return Scope.getCurrentScope().getResourceAccessor();
    }

    @Override
    public void setResourceAccessor(ResourceAccessor resourceAccessor) {
        Scope.getCurrentScope().getLog(this.getClass()).info("As of Liquibase 4.0, cannot set resource accessor on " + this.getClass().getName() + ". Must add it to the Scope");
    }

    @Override
    public Set<DatabaseObject> getAffectedDatabaseObjects(Database database) {
        if (this.generateStatementsVolatile(database)) {
            return new HashSet<DatabaseObject>();
        }
        HashSet<DatabaseObject> affectedObjects = new HashSet<DatabaseObject>();
        SqlStatement[] statements = this.generateStatements(database);
        if (statements != null) {
            for (SqlStatement statement : statements) {
                affectedObjects.addAll(SqlGeneratorFactory.getInstance().getAffectedDatabaseObjects(statement, database));
            }
        }
        return affectedObjects;
    }

    @Override
    public Set<String> getSerializableFields() {
        return Scope.getCurrentScope().getSingleton(ChangeFactory.class).getChangeMetaData(this).getParameters().keySet();
    }

    @Override
    public Object getSerializableFieldValue(String field2) {
        ChangeParameterMetaData fieldMetaData = Scope.getCurrentScope().getSingleton(ChangeFactory.class).getChangeMetaData(this).getParameters().get(field2);
        if (fieldMetaData == null) {
            return null;
        }
        return fieldMetaData.getCurrentValue(this);
    }

    @Override
    public String getSerializedObjectName() {
        return Scope.getCurrentScope().getSingleton(ChangeFactory.class).getChangeMetaData(this).getName();
    }

    @Override
    public LiquibaseSerializable.SerializationType getSerializableFieldType(String field2) {
        return Scope.getCurrentScope().getSingleton(ChangeFactory.class).getChangeMetaData(this).getParameters().get(field2).getSerializationType();
    }

    @Override
    public String getSerializedObjectNamespace() {
        return "http://www.liquibase.org/xml/ns/dbchangelog-ext";
    }

    @Override
    public String getSerializableFieldNamespace(String field2) {
        return this.getSerializedObjectNamespace();
    }

    @Override
    public String toString() {
        return Scope.getCurrentScope().getSingleton(ChangeFactory.class).getChangeMetaData(this).getName();
    }

    @Override
    public void load(ParsedNode parsedNode, ResourceAccessor resourceAccessor) throws ParsedNodeException {
        ChangeMetaData metaData = Scope.getCurrentScope().getSingleton(ChangeFactory.class).getChangeMetaData(this);
        try {
            Collection<ChangeParameterMetaData> changeParameters = metaData.getParameters().values();
            block6: for (ChangeParameterMetaData param2 : changeParameters) {
                if (Collection.class.isAssignableFrom(param2.getDataTypeClass())) {
                    Object nodeValue;
                    if (param2.getDataTypeClassParameters().length != 1) continue;
                    Class collectionType = (Class)param2.getDataTypeClassParameters()[0];
                    if (ColumnConfig.class.isAssignableFrom(collectionType)) {
                        ArrayList<ParsedNode> columnNodes = new ArrayList<ParsedNode>(parsedNode.getChildren(null, param2.getParameterName()));
                        columnNodes.addAll(parsedNode.getChildren(null, NODENAME_COLUMN));
                        Object nodeValue2 = parsedNode.getValue();
                        if (nodeValue2 instanceof ParsedNode) {
                            columnNodes.add((ParsedNode)nodeValue2);
                        } else if (nodeValue2 instanceof Collection) {
                            for (Object nodeValueChild : (Collection)nodeValue2) {
                                if (!(nodeValueChild instanceof ParsedNode)) continue;
                                columnNodes.add((ParsedNode)nodeValueChild);
                            }
                        }
                        for (ParsedNode child : columnNodes) {
                            if (!NODENAME_COLUMN.equals(child.getName()) && !"columns".equals(child.getName())) continue;
                            List<ParsedNode> columnChildren = child.getChildren(null, NODENAME_COLUMN);
                            if (columnChildren != null && !columnChildren.isEmpty()) {
                                for (ParsedNode parsedNode2 : columnChildren) {
                                    ColumnConfig columnConfig = this.createEmptyColumnConfig(collectionType);
                                    columnConfig.load(parsedNode2, resourceAccessor);
                                    ((ChangeWithColumns)((Object)this)).addColumn(columnConfig);
                                }
                                continue;
                            }
                            ColumnConfig columnConfig = this.createEmptyColumnConfig(collectionType);
                            columnConfig.load(child, resourceAccessor);
                            ((ChangeWithColumns)((Object)this)).addColumn(columnConfig);
                        }
                        continue;
                    }
                    if (!LiquibaseSerializable.class.isAssignableFrom(collectionType) || collectionType.isInterface() || Modifier.isAbstract(collectionType.getModifiers())) continue;
                    String elementName = ((LiquibaseSerializable)collectionType.getConstructor(new Class[0]).newInstance(new Object[0])).getSerializedObjectName();
                    ArrayList<ParsedNode> nodes = new ArrayList<ParsedNode>(parsedNode.getChildren(null, param2.getParameterName()));
                    if (!elementName.equals(param2.getParameterName())) {
                        nodes.addAll(parsedNode.getChildren(null, elementName));
                    }
                    if ((nodeValue = parsedNode.getValue()) instanceof ParsedNode) {
                        nodes.add((ParsedNode)nodeValue);
                    } else if (nodeValue instanceof Collection) {
                        for (Object nodeValueChild : (Collection)nodeValue) {
                            if (!(nodeValueChild instanceof ParsedNode)) continue;
                            nodes.add((ParsedNode)nodeValueChild);
                        }
                    }
                    for (ParsedNode node : nodes) {
                        if (!node.getName().equals(elementName) && !node.getName().equals(param2.getParameterName())) continue;
                        List<ParsedNode> childNodes = node.getChildren(null, elementName);
                        if (childNodes != null && !childNodes.isEmpty()) {
                            for (ParsedNode childNode : childNodes) {
                                LiquibaseSerializable childObject = (LiquibaseSerializable)collectionType.getConstructor(new Class[0]).newInstance(new Object[0]);
                                childObject.load(childNode, resourceAccessor);
                                ((Collection)param2.getCurrentValue(this)).add(childObject);
                            }
                            continue;
                        }
                        LiquibaseSerializable liquibaseSerializable = (LiquibaseSerializable)collectionType.getConstructor(new Class[0]).newInstance(new Object[0]);
                        liquibaseSerializable.load(node, resourceAccessor);
                        ((Collection)param2.getCurrentValue(this)).add(liquibaseSerializable);
                    }
                    continue;
                }
                if (LiquibaseSerializable.class.isAssignableFrom(param2.getDataTypeClass())) {
                    if (param2.getDataTypeClass().isInterface() || Modifier.isAbstract(param2.getDataTypeClass().getModifiers())) continue;
                    try {
                        ParsedNode child = parsedNode.getChild(null, param2.getParameterName());
                        if (child == null) continue;
                        LiquibaseSerializable serializableChild = (LiquibaseSerializable)param2.getDataTypeClass().getConstructor(new Class[0]).newInstance(new Object[0]);
                        serializableChild.load(child, resourceAccessor);
                        param2.setValue(this, serializableChild);
                        continue;
                    }
                    catch (ReflectiveOperationException e) {
                        throw new UnexpectedLiquibaseException(e);
                    }
                }
                ArrayList<String> parameterNamesToCheck = new ArrayList<String>();
                parameterNamesToCheck.add(param2.getParameterName());
                if (param2.getAlternateParameterNames() != null) {
                    parameterNamesToCheck.addAll(Arrays.asList(param2.getAlternateParameterNames()));
                }
                for (String paramName : parameterNamesToCheck) {
                    Object childValue = parsedNode.getChildValue((String)null, paramName, param2.getDataTypeClass());
                    if (childValue == null && param2.getSerializationType() == LiquibaseSerializable.SerializationType.DIRECT_VALUE) {
                        childValue = parsedNode.getValue();
                    }
                    if (null == childValue) continue;
                    param2.setValue(this, childValue);
                    continue block6;
                }
            }
        }
        catch (ReflectiveOperationException e) {
            throw new UnexpectedLiquibaseException(e);
        }
        this.customLoadLogic(parsedNode, resourceAccessor);
        try {
            this.finishInitialization();
        }
        catch (SetupException e) {
            throw new ParsedNodeException(e);
        }
    }

    protected ColumnConfig createEmptyColumnConfig(Class collectionType) throws ReflectiveOperationException {
        return (ColumnConfig)collectionType.getConstructor(new Class[0]).newInstance(new Object[0]);
    }

    protected void customLoadLogic(ParsedNode parsedNode, ResourceAccessor resourceAccessor) throws ParsedNodeException {
    }

    @Override
    public ParsedNode serialize() throws ParsedNodeException {
        ParsedNode node = new ParsedNode(null, this.getSerializedObjectName());
        ChangeMetaData metaData = Scope.getCurrentScope().getSingleton(ChangeFactory.class).getChangeMetaData(this);
        for (ChangeParameterMetaData param2 : metaData.getSetParameters(this).values()) {
            Object currentValue = param2.getCurrentValue(this);
            if ((currentValue = this.serializeValue(currentValue)) == null) continue;
            node.addChild(null, param2.getParameterName(), currentValue);
        }
        return node;
    }

    protected Object serializeValue(Object value) throws ParsedNodeException {
        if (value instanceof Collection) {
            ArrayList<Object> returnList = new ArrayList<Object>();
            for (Object obj : (Collection)value) {
                Object objValue = this.serializeValue(obj);
                if (objValue == null) continue;
                returnList.add(objValue);
            }
            if (((Collection)value).isEmpty()) {
                return null;
            }
            return returnList;
        }
        if (value instanceof LiquibaseSerializable) {
            return ((LiquibaseSerializable)value).serialize();
        }
        return value;
    }

    @Override
    public String getDescription() {
        ChangeMetaData metaData = Scope.getCurrentScope().getSingleton(ChangeFactory.class).getChangeMetaData(this);
        String description = metaData.getName();
        TreeSet<String> names = new TreeSet<String>();
        for (Map.Entry<String, ChangeParameterMetaData> entry : metaData.getParameters().entrySet()) {
            Object currentValue;
            String lowerCaseKey = entry.getKey().toLowerCase();
            if ((!lowerCaseKey.endsWith("name") || lowerCaseKey.contains("schema") || lowerCaseKey.contains("catalog")) && !lowerCaseKey.equals("path") || (currentValue = entry.getValue().getCurrentValue(this)) == null) continue;
            names.add(entry.getKey() + "=" + currentValue);
        }
        if (!names.isEmpty()) {
            description = description + " " + StringUtil.join(names, ", ");
        }
        return description;
    }

    @Override
    public void modify(ChangeVisitor changeVisitor) throws ParsedNodeException {
    }

    @Override
    public boolean equals(Object reference) {
        return this == reference;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.changeSet);
    }
}

