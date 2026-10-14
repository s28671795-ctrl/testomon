/*
 * Decompiled with CFR 0.152.
 */
package liquibase.diff.output.changelog;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.xml.parsers.ParserConfigurationException;
import liquibase.GlobalConfiguration;
import liquibase.Labels;
import liquibase.Scope;
import liquibase.change.Change;
import liquibase.change.ReplaceIfExists;
import liquibase.change.core.DeleteDataChange;
import liquibase.change.core.DropForeignKeyConstraintChange;
import liquibase.change.core.InsertDataChange;
import liquibase.change.core.LoadDataChange;
import liquibase.change.core.RawSQLChange;
import liquibase.change.core.UpdateDataChange;
import liquibase.changelog.ChangeSet;
import liquibase.changeset.ChangeSetService;
import liquibase.changeset.ChangeSetServiceFactory;
import liquibase.command.core.helpers.AbstractChangelogCommandStep;
import liquibase.configuration.core.DeprecatedConfigurationValueProvider;
import liquibase.database.AbstractJdbcDatabase;
import liquibase.database.Database;
import liquibase.database.DatabaseConnection;
import liquibase.database.ObjectQuotingStrategy;
import liquibase.database.OfflineConnection;
import liquibase.database.core.AbstractDb2Database;
import liquibase.database.core.DB2Database;
import liquibase.database.core.Db2zDatabase;
import liquibase.database.core.MSSQLDatabase;
import liquibase.database.core.OracleDatabase;
import liquibase.database.core.PostgresDatabase;
import liquibase.diff.DiffResult;
import liquibase.diff.ObjectDifferences;
import liquibase.diff.compare.CompareControl;
import liquibase.diff.compare.DatabaseObjectCollectionComparator;
import liquibase.diff.output.DiffOutputControl;
import liquibase.diff.output.changelog.ChangeGenerator;
import liquibase.diff.output.changelog.ChangeGeneratorFactory;
import liquibase.diff.output.changelog.ChangedObjectChangeGenerator;
import liquibase.diff.output.changelog.MissingObjectChangeGenerator;
import liquibase.diff.output.changelog.UnexpectedObjectChangeGenerator;
import liquibase.exception.DatabaseException;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.executor.Executor;
import liquibase.executor.ExecutorService;
import liquibase.resource.OpenOptions;
import liquibase.resource.PathHandlerFactory;
import liquibase.resource.Resource;
import liquibase.serializer.ChangeLogSerializer;
import liquibase.serializer.ChangeLogSerializerFactory;
import liquibase.snapshot.DatabaseSnapshot;
import liquibase.snapshot.EmptyDatabaseSnapshot;
import liquibase.statement.core.RawParameterizedSqlStatement;
import liquibase.statement.core.RawSqlStatement;
import liquibase.structure.DatabaseObject;
import liquibase.structure.core.Column;
import liquibase.structure.core.StoredDatabaseLogic;
import liquibase.structure.core.Table;
import liquibase.util.DependencyUtil;
import liquibase.util.StreamUtil;
import liquibase.util.StringUtil;

public class DiffToChangeLog {
    public static final String ORDER_ATTRIBUTE = "order";
    public static final String DATABASE_CHANGE_LOG_CLOSING_XML_TAG = "</databaseChangeLog>";
    public static final String EXTERNAL_FILE_DIR_SCOPE_KEY = "DiffToChangeLog.externalFilesDir";
    public static final String DIFF_OUTPUT_CONTROL_SCOPE_KEY = "diffOutputControl";
    public static final String DIFF_SNAPSHOT_DATABASE = "snapshotDatabase";
    private String idRoot = String.valueOf(new Date().getTime());
    private boolean overriddenIdRoot;
    private int changeNumber = 1;
    private String changeSetContext;
    private String changeSetLabels;
    private String changeSetAuthor;
    private String changeSetPath;
    private String[] changeSetRunOnChangeTypes;
    private String[] changeReplaceIfExistsTypes;
    private DiffResult diffResult;
    private final DiffOutputControl diffOutputControl;
    private boolean tryDbaDependencies = true;
    private boolean skipObjectSorting = false;
    private static final Set<Class> loggedOrderFor = new HashSet<Class>();

    public DiffToChangeLog(DiffResult diffResult, DiffOutputControl diffOutputControl, boolean skipObjectSorting) {
        this(diffResult, diffOutputControl);
        this.skipObjectSorting = skipObjectSorting;
    }

    public DiffToChangeLog(DiffResult diffResult, DiffOutputControl diffOutputControl) {
        this.diffResult = diffResult;
        this.diffOutputControl = diffOutputControl;
        this.respectSchemaAndCatalogCaseIfNeeded(diffOutputControl);
    }

    public DiffToChangeLog(DiffOutputControl diffOutputControl) {
        this.diffOutputControl = diffOutputControl;
    }

    private void respectSchemaAndCatalogCaseIfNeeded(DiffOutputControl diffOutputControl) {
        if (this.diffResult.getComparisonSnapshot().getDatabase() instanceof AbstractDb2Database) {
            diffOutputControl.setRespectSchemaAndCatalogCase(true);
        }
    }

    public void setDiffResult(DiffResult diffResult) {
        this.diffResult = diffResult;
    }

    public void setChangeSetContext(String changeSetContext) {
        this.changeSetContext = changeSetContext;
    }

    public void setChangeSetLabels(String changeSetLabels) {
        this.changeSetLabels = changeSetLabels;
    }

    public void print(String changeLogFile) throws ParserConfigurationException, IOException, DatabaseException {
        this.print(changeLogFile, false);
    }

    public void print(String changeLogFile, Boolean overwriteOutputFile) throws ParserConfigurationException, IOException, DatabaseException {
        this.changeSetPath = changeLogFile;
        ChangeLogSerializer changeLogSerializer = ChangeLogSerializerFactory.getInstance().getSerializer(changeLogFile);
        this.print(changeLogFile, changeLogSerializer, overwriteOutputFile);
    }

    public void print(PrintStream out) throws ParserConfigurationException, IOException, DatabaseException {
        this.print(out, ChangeLogSerializerFactory.getInstance().getSerializer("xml"));
    }

    public void print(String changeLogFile, ChangeLogSerializer changeLogSerializer) throws ParserConfigurationException, IOException, DatabaseException {
        this.print(changeLogFile, changeLogSerializer, false);
    }

    public void print(String changeLogFile, final ChangeLogSerializer changeLogSerializer, final Boolean overwriteOutputFile) throws ParserConfigurationException, IOException, DatabaseException {
        this.changeSetPath = changeLogFile;
        PathHandlerFactory pathHandlerFactory = Scope.getCurrentScope().getSingleton(PathHandlerFactory.class);
        final Resource file = pathHandlerFactory.getResource(changeLogFile);
        HashMap<String, Object> newScopeObjects = new HashMap<String, Object>();
        Resource objectsDir = null;
        if (changeLogFile.toLowerCase().endsWith("sql")) {
            DeprecatedConfigurationValueProvider.setData("liquibase.pro.sql.inline", "true");
        } else {
            objectsDir = this.diffResult.getComparisonSnapshot() instanceof EmptyDatabaseSnapshot ? file.resolveSibling("objects") : file.resolveSibling("objects-" + new Date().getTime());
        }
        if (objectsDir != null) {
            if (objectsDir.exists()) {
                throw new UnexpectedLiquibaseException("The generatechangelog command would overwrite your existing stored logic files. To run this command please remove or rename the '" + objectsDir + "' dir");
            }
            newScopeObjects.put(EXTERNAL_FILE_DIR_SCOPE_KEY, objectsDir);
        }
        newScopeObjects.put(DIFF_OUTPUT_CONTROL_SCOPE_KEY, this.diffOutputControl);
        try {
            Database database = this.determineDatabase(this.diffResult.getReferenceSnapshot());
            if (database == null) {
                database = this.determineDatabase(this.diffResult.getComparisonSnapshot());
            }
            newScopeObjects.put(DIFF_SNAPSHOT_DATABASE, database);
            Scope.child(newScopeObjects, new Scope.ScopedRunner(){

                @Override
                public void run() {
                    block14: {
                        try {
                            if (!file.exists()) {
                                DiffToChangeLog.this.printNew(changeLogSerializer, file);
                                break block14;
                            }
                            StringBuilder fileContents = new StringBuilder();
                            ByteArrayOutputStream out = new ByteArrayOutputStream();
                            DiffToChangeLog.this.print(new PrintStream((OutputStream)out, true, GlobalConfiguration.OUTPUT_FILE_ENCODING.getCurrentValue()), changeLogSerializer);
                            String xml = new String(out.toByteArray(), GlobalConfiguration.OUTPUT_FILE_ENCODING.getCurrentValue());
                            if (overwriteOutputFile.booleanValue()) {
                                Scope.getCurrentScope().getLog(this.getClass()).info(file.getUri() + " exists, overwriting");
                                fileContents.append(xml);
                            } else {
                                Scope.getCurrentScope().getLog(this.getClass()).info(file.getUri() + " exists, appending");
                                fileContents = new StringBuilder(StreamUtil.readStreamAsString(file.openInputStream()));
                                String innerXml = xml.replaceFirst("(?ms).*<databaseChangeLog[^>]*>", "");
                                innerXml = innerXml.replaceFirst(DiffToChangeLog.DATABASE_CHANGE_LOG_CLOSING_XML_TAG, "");
                                innerXml = innerXml.trim();
                                if ("".equals(innerXml)) {
                                    Scope.getCurrentScope().getLog(this.getClass()).info("No changes found, nothing to do");
                                    return;
                                }
                                int endTagIndex = fileContents.indexOf(DiffToChangeLog.DATABASE_CHANGE_LOG_CLOSING_XML_TAG);
                                if (endTagIndex == -1) {
                                    fileContents.append(xml);
                                } else {
                                    String lineSeparator = GlobalConfiguration.OUTPUT_LINE_SEPARATOR.getCurrentValue();
                                    String toInsert = "    " + innerXml + lineSeparator;
                                    fileContents.insert(endTagIndex, toInsert);
                                }
                            }
                            try (OutputStream outputStream2 = file.openOutputStream(new OpenOptions());){
                                outputStream2.write(fileContents.toString().getBytes());
                            }
                        }
                        catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            });
        }
        catch (Exception e) {
            Throwable cause = e.getCause();
            if (cause instanceof ParserConfigurationException) {
                throw (ParserConfigurationException)cause;
            }
            if (cause instanceof IOException) {
                throw (IOException)cause;
            }
            if (cause instanceof DatabaseException) {
                throw (DatabaseException)cause;
            }
            throw new RuntimeException(e);
        }
    }

    private Database determineDatabase(DatabaseSnapshot snapshot) {
        Database database = snapshot.getDatabase();
        DatabaseConnection connection = database.getConnection();
        if (!(connection instanceof OfflineConnection) && database instanceof PostgresDatabase) {
            return database;
        }
        return null;
    }

    public void printNew(ChangeLogSerializer changeLogSerializer, Resource file) throws ParserConfigurationException, IOException, DatabaseException {
        List<ChangeSet> changeSets = this.generateChangeSets();
        Scope.getCurrentScope().getLog(this.getClass()).info("changeSets count: " + changeSets.size());
        if (changeSets.isEmpty()) {
            Scope.getCurrentScope().getLog(this.getClass()).info("No changesets to add to the changelog output.");
        } else {
            Scope.getCurrentScope().getLog(this.getClass()).info(file + " does not exist, creating and adding " + changeSets.size() + " changesets.");
            try (OutputStream stream = file.openOutputStream(new OpenOptions());
                 PrintStream out = new PrintStream(stream, true, GlobalConfiguration.OUTPUT_FILE_ENCODING.getCurrentValue());){
                changeLogSerializer.write(changeSets, out);
            }
        }
    }

    public void print(PrintStream out, ChangeLogSerializer changeLogSerializer) throws ParserConfigurationException, IOException, DatabaseException {
        List<ChangeSet> changeSets = this.generateChangeSets();
        changeLogSerializer.write(changeSets, out);
        out.flush();
    }

    public List<ChangeSet> generateChangeSets() {
        Change[] changes;
        ChangeGeneratorFactory changeGeneratorFactory = ChangeGeneratorFactory.getInstance();
        DatabaseObjectCollectionComparator comparator = new DatabaseObjectCollectionComparator();
        String created = null;
        if (GlobalConfiguration.GENERATE_CHANGESET_CREATED_VALUES.getCurrentValue().booleanValue()) {
            created = new SimpleDateFormat("yyyy-MM-dd HH:mmZ").format(new Date());
        }
        List<Class<? extends DatabaseObject>> types = this.getOrderedOutputTypes(ChangedObjectChangeGenerator.class);
        ArrayList<ChangeSet> updateChangeSets = new ArrayList<ChangeSet>();
        Database comparisonDatabase = this.diffResult.getComparisonSnapshot().getDatabase();
        if (comparisonDatabase instanceof AbstractJdbcDatabase) {
            ((AbstractJdbcDatabase)comparisonDatabase).set("diffResult", this.diffResult);
        }
        for (Class<? extends DatabaseObject> clazz : types) {
            ObjectQuotingStrategy objectQuotingStrategy = this.diffOutputControl.getObjectQuotingStrategy();
            for (Map.Entry<? extends DatabaseObject, ObjectDifferences> entry : this.diffResult.getChangedObjects(clazz, comparator).entrySet()) {
                if (this.diffResult.getReferenceSnapshot().getDatabase().isLiquibaseObject(entry.getKey()) || this.diffResult.getReferenceSnapshot().getDatabase().isSystemObject(entry.getKey())) continue;
                changes = changeGeneratorFactory.fixChanged(entry.getKey(), entry.getValue(), this.diffOutputControl, this.diffResult.getReferenceSnapshot().getDatabase(), this.diffResult.getComparisonSnapshot().getDatabase());
                this.setReplaceIfExistsTrueIfApplicable(changes);
                this.addToChangeSets(changes, updateChangeSets, objectQuotingStrategy, created);
            }
        }
        types = this.getOrderedOutputTypes(MissingObjectChangeGenerator.class);
        ArrayList<DatabaseObject> missingObjects = new ArrayList<DatabaseObject>();
        for (Class<? extends DatabaseObject> clazz : types) {
            for (DatabaseObject databaseObject : this.diffResult.getMissingObjects(clazz, this.getDatabaseObjectCollectionComparator())) {
                if (databaseObject == null || this.diffResult.getReferenceSnapshot().getDatabase().isLiquibaseObject(databaseObject) || this.diffResult.getReferenceSnapshot().getDatabase().isSystemObject(databaseObject)) continue;
                missingObjects.add(databaseObject);
            }
        }
        ArrayList<ChangeSet> arrayList = new ArrayList<ChangeSet>();
        for (DatabaseObject databaseObject : this.sortMissingObjects(missingObjects, this.diffResult.getReferenceSnapshot().getDatabase())) {
            ObjectQuotingStrategy objectQuotingStrategy = this.diffOutputControl.getObjectQuotingStrategy();
            changes = changeGeneratorFactory.fixMissing(databaseObject, this.diffOutputControl, this.diffResult.getReferenceSnapshot().getDatabase(), this.diffResult.getComparisonSnapshot().getDatabase());
            this.setReplaceIfExistsTrueIfApplicable(changes);
            this.addToChangeSets(changes, arrayList, objectQuotingStrategy, created);
        }
        ArrayList<ChangeSet> arrayList2 = new ArrayList<ChangeSet>();
        types = this.getOrderedOutputTypes(UnexpectedObjectChangeGenerator.class);
        for (Class<? extends DatabaseObject> clazz : types) {
            ObjectQuotingStrategy quotingStrategy = this.diffOutputControl.getObjectQuotingStrategy();
            for (DatabaseObject object : this.sortUnexpectedObjects(this.diffResult.getUnexpectedObjects(clazz, comparator), this.diffResult.getReferenceSnapshot().getDatabase())) {
                if (this.diffResult.getComparisonSnapshot().getDatabase().isLiquibaseObject(object) || this.diffResult.getComparisonSnapshot().getDatabase().isSystemObject(object)) continue;
                Change[] changes2 = changeGeneratorFactory.fixUnexpected(object, this.diffOutputControl, this.diffResult.getReferenceSnapshot().getDatabase(), this.diffResult.getComparisonSnapshot().getDatabase());
                this.setReplaceIfExistsTrueIfApplicable(changes2);
                this.addToChangeSets(changes2, arrayList2, quotingStrategy, created);
            }
        }
        if (comparisonDatabase instanceof AbstractJdbcDatabase) {
            ((AbstractJdbcDatabase)comparisonDatabase).set("diffResult", null);
        }
        ArrayList<ChangeSet> arrayList3 = new ArrayList<ChangeSet>();
        arrayList3.addAll(arrayList);
        arrayList3.addAll(arrayList2);
        arrayList3.addAll(updateChangeSets);
        List<ChangeSet> list = this.bringDropFKToTop(arrayList3);
        return list;
    }

    private void setReplaceIfExistsTrueIfApplicable(Change[] changes) {
        if (changes != null && this.diffOutputControl.isReplaceIfExistsSet()) {
            for (Change change : changes) {
                if (!(change instanceof ReplaceIfExists)) continue;
                ((ReplaceIfExists)((Object)change)).setReplaceIfExists(true);
            }
        }
    }

    private List<ChangeSet> bringDropFKToTop(List<ChangeSet> changeSets) {
        List dropFk = changeSets.stream().filter(cs -> cs.getChanges().stream().anyMatch(DropForeignKeyConstraintChange.class::isInstance)).collect(Collectors.toList());
        if (dropFk.isEmpty()) {
            return changeSets;
        }
        ArrayList<ChangeSet> returnList = new ArrayList<ChangeSet>();
        changeSets.stream().forEach(cs -> {
            if (dropFk.contains(cs)) {
                returnList.add((ChangeSet)cs);
            }
        });
        changeSets.stream().forEach(cs -> {
            if (!dropFk.contains(cs)) {
                returnList.add((ChangeSet)cs);
            }
        });
        return returnList;
    }

    private DatabaseObjectCollectionComparator getDatabaseObjectCollectionComparator() {
        return new DatabaseObjectCollectionComparator(){

            @Override
            public int compare(DatabaseObject o1, DatabaseObject o2) {
                int order;
                if (o1 instanceof Column && o1.getAttribute(DiffToChangeLog.ORDER_ATTRIBUTE, Integer.class) != null && o2.getAttribute(DiffToChangeLog.ORDER_ATTRIBUTE, Integer.class) != null) {
                    int i = ((Integer)((Object)o1.getAttribute(DiffToChangeLog.ORDER_ATTRIBUTE, Integer.class))).compareTo((Integer)((Object)o2.getAttribute(DiffToChangeLog.ORDER_ATTRIBUTE, Integer.class)));
                    if (i != 0) {
                        return i;
                    }
                } else if (o1 instanceof StoredDatabaseLogic && o1.getAttribute(DiffToChangeLog.ORDER_ATTRIBUTE, Integer.class) != null && o2.getAttribute(DiffToChangeLog.ORDER_ATTRIBUTE, Integer.class) != null && (order = ((Long)((Object)o1.getAttribute(DiffToChangeLog.ORDER_ATTRIBUTE, Long.class))).compareTo((Long)((Object)o2.getAttribute(DiffToChangeLog.ORDER_ATTRIBUTE, Long.class)))) != 0) {
                    return order;
                }
                return super.compare(o1, o2);
            }
        };
    }

    private List<DatabaseObject> sortUnexpectedObjects(Collection<? extends DatabaseObject> unexpectedObjects, Database database) {
        return this.sortObjects("unexpected", unexpectedObjects, database);
    }

    private List<DatabaseObject> sortMissingObjects(Collection<DatabaseObject> missingObjects, Database database) {
        return this.sortObjects("missing", missingObjects, database);
    }

    private List<DatabaseObject> sortObjects(String type, Collection<DatabaseObject> objects, Database database) {
        if (!objects.isEmpty() && this.supportsSortingObjects(database) && database.getConnection() != null && !(database.getConnection() instanceof OfflineConnection)) {
            ArrayList<String> schemas = new ArrayList<String>();
            CompareControl.SchemaComparison[] schemaComparisons = this.diffOutputControl.getSchemaComparisons();
            if (schemaComparisons != null) {
                for (CompareControl.SchemaComparison comparison : schemaComparisons) {
                    String schemaName = comparison.getReferenceSchema().getSchemaName();
                    if (schemaName == null) {
                        schemaName = database.getDefaultSchemaName();
                    }
                    schemas.add(schemaName);
                }
            }
            if (schemas.isEmpty()) {
                schemas.add(database.getDefaultSchemaName());
            }
            try {
                ArrayList dependencyOrder = new ArrayList();
                DependencyUtil.NodeValueListener<String> nameListener = dependencyOrder::add;
                DependencyUtil.DependencyGraph<String> graph = new DependencyUtil.DependencyGraph<String>(nameListener);
                this.addDependencies(graph, schemas, database);
                graph.computeDependencies();
                if (!dependencyOrder.isEmpty()) {
                    ArrayList<DatabaseObject> toSort = new ArrayList<DatabaseObject>();
                    ArrayList<DatabaseObject> toNotSort = new ArrayList<DatabaseObject>();
                    for (DatabaseObject obj : objects) {
                        if (!(obj instanceof Column)) {
                            String schemaName = null;
                            if (obj.getSchema() != null) {
                                schemaName = obj.getSchema().getName();
                            }
                            String objectName = obj.getName();
                            String name = schemaName + "." + objectName;
                            if (dependencyOrder.contains(name) || dependencyOrder.contains(DiffToChangeLog.convertStoredLogicObjectName(schemaName, objectName, database))) {
                                toSort.add(obj);
                                continue;
                            }
                            toNotSort.add(obj);
                            continue;
                        }
                        toNotSort.add(obj);
                    }
                    toSort.sort((o1, o2) -> {
                        Integer x;
                        if (database instanceof PostgresDatabase && (x = DiffToChangeLog.determineOrderingForTablesAndStoredLogic(o1, o2)) != null) {
                            return x;
                        }
                        String o1Schema = null;
                        if (o1.getSchema() != null) {
                            o1Schema = o1.getSchema().getName();
                        }
                        String o2Schema = null;
                        if (o2.getSchema() != null) {
                            o2Schema = o2.getSchema().getName();
                        }
                        Integer o1Order = dependencyOrder.indexOf(o1Schema + "." + o1.getName());
                        int o2Order = dependencyOrder.indexOf(o2Schema + "." + o2.getName());
                        int order = o1Order.compareTo(o2Order);
                        if ("unexpected".equals(type)) {
                            order *= -1;
                        }
                        return order;
                    });
                    toSort.addAll(toNotSort);
                    return toSort;
                }
            }
            catch (DatabaseException e) {
                Scope.getCurrentScope().getLog(this.getClass()).fine("Cannot get object dependencies: " + e.getMessage());
            }
            catch (StackOverflowError e) {
                Scope.getCurrentScope().getLog(this.getClass()).warning("You have too many or recursive database object dependencies! Liquibase is going to ignore dependency sorting and resume processing. To skip this message (and save a lot of processing time) use flag " + AbstractChangelogCommandStep.SKIP_OBJECT_SORTING.getName(), e);
            }
        }
        return new ArrayList<DatabaseObject>(objects);
    }

    private static Integer determineOrderingForTablesAndStoredLogic(DatabaseObject o1, DatabaseObject o2) {
        if (o1 instanceof Table && o2 instanceof StoredDatabaseLogic) {
            return -1;
        }
        if (o1 instanceof StoredDatabaseLogic && o2 instanceof Table) {
            return 1;
        }
        return null;
    }

    private static String convertStoredLogicObjectName(String schemaName, String objectName, Database database) {
        String name = schemaName + "." + objectName;
        if (!(database instanceof PostgresDatabase && objectName.contains("(") && objectName.contains(")"))) {
            return name;
        }
        Pattern p = Pattern.compile(".*?[(]+(.*)?[)]+[\\s]*?$");
        Matcher m4 = p.matcher(objectName);
        if (m4.matches()) {
            String[] parameters;
            String originalParameters;
            String editedParameters = originalParameters = m4.group(1);
            for (String parameter : parameters = m4.group(1).split(",")) {
                parameter = parameter.trim();
                String[] parts = parameter.split(" ");
                String[] rest = Arrays.copyOfRange(parts, 1, parts.length);
                String part = StringUtil.join(rest, " ");
                editedParameters = editedParameters.replace(parameter, part);
            }
            name = schemaName + "." + objectName.replace(originalParameters, editedParameters).replace(", ", ",");
        }
        return name;
    }

    private List<Map<String, ?>> queryForDependenciesOracle(Executor executor2, List<String> schemas) throws DatabaseException {
        List<Map<String, ?>> rs = null;
        try {
            rs = this.tryDbaDependencies ? executor2.queryForList(new RawSqlStatement("select OWNER, NAME, REFERENCED_OWNER, REFERENCED_NAME from DBA_DEPENDENCIES where REFERENCED_OWNER != 'SYS' AND NOT(NAME LIKE 'BIN$%') AND NOT(OWNER = REFERENCED_OWNER AND NAME = REFERENCED_NAME) AND (" + StringUtil.join(schemas, " OR ", obj -> "OWNER='" + obj + "'") + ")")) : executor2.queryForList(new RawSqlStatement("select NAME, REFERENCED_OWNER, REFERENCED_NAME from USER_DEPENDENCIES where REFERENCED_OWNER != 'SYS' AND NOT(NAME LIKE 'BIN$%') AND NOT(NAME = REFERENCED_NAME) AND (" + StringUtil.join(schemas, " OR ", obj -> "REFERENCED_OWNER='" + obj + "'") + ")"));
        }
        catch (DatabaseException dbe) {
            String message = dbe.getMessage();
            if (!message.contains("ORA-00942") || !this.tryDbaDependencies) {
                throw new DatabaseException(dbe);
            }
            Scope.getCurrentScope().getLog(this.getClass()).warning("Unable to query DBA_DEPENDENCIES table. Switching to USER_DEPENDENCIES");
            this.tryDbaDependencies = false;
            return this.queryForDependenciesOracle(executor2, schemas);
        }
        return rs;
    }

    protected boolean supportsSortingObjects(Database database) {
        if (this.skipObjectSorting) {
            return false;
        }
        return database instanceof AbstractDb2Database || database instanceof MSSQLDatabase || database instanceof OracleDatabase || database instanceof PostgresDatabase;
    }

    protected void addDependencies(DependencyUtil.DependencyGraph<String> graph, List<String> schemas, Database database) throws DatabaseException {
        block7: {
            block10: {
                block9: {
                    block8: {
                        block6: {
                            if (!(database instanceof DB2Database)) break block6;
                            Executor executor2 = Scope.getCurrentScope().getSingleton(ExecutorService.class).getExecutor("jdbc", database);
                            String sql = "select TABSCHEMA, TABNAME, BSCHEMA, BNAME from syscat.tabdep where TABSCHEMA in (" + StringUtil.join(schemas, ", ", obj -> "?") + ")";
                            List<Map<String, ?>> rs = executor2.queryForList(new RawParameterizedSqlStatement(sql, schemas.toArray()));
                            for (Map<String, ?> row : rs) {
                                String tabName = StringUtil.trimToNull((String)row.get("TABSCHEMA")) + "." + StringUtil.trimToNull((String)row.get("TABNAME"));
                                String bName = StringUtil.trimToNull((String)row.get("BSCHEMA")) + "." + StringUtil.trimToNull((String)row.get("BNAME"));
                                graph.add(bName, tabName);
                            }
                            break block7;
                        }
                        if (!(database instanceof Db2zDatabase)) break block8;
                        Executor executor3 = Scope.getCurrentScope().getSingleton(ExecutorService.class).getExecutor("jdbc", database);
                        String sql = "SELECT DSCHEMA AS TABSCHEMA, DNAME AS TABNAME, BSCHEMA, BNAME FROM SYSIBM.SYSDEPENDENCIES WHERE DSCHEMA IN (" + StringUtil.join(schemas, ", ", obj -> "?") + ")";
                        List<Map<String, ?>> rs = executor3.queryForList(new RawParameterizedSqlStatement(sql, schemas.toArray()));
                        for (Map<String, ?> row : rs) {
                            String tabName = StringUtil.trimToNull((String)row.get("TABSCHEMA")) + "." + StringUtil.trimToNull((String)row.get("TABNAME"));
                            String bName = StringUtil.trimToNull((String)row.get("BSCHEMA")) + "." + StringUtil.trimToNull((String)row.get("BNAME"));
                            graph.add(bName, tabName);
                        }
                        break block7;
                    }
                    if (!(database instanceof OracleDatabase)) break block9;
                    Executor executor4 = Scope.getCurrentScope().getSingleton(ExecutorService.class).getExecutor("jdbc", database);
                    List<Map<String, ?>> rs = this.queryForDependenciesOracle(executor4, schemas);
                    for (Map<String, ?> row : rs) {
                        String tabName = null;
                        tabName = this.tryDbaDependencies ? StringUtil.trimToNull((String)row.get("OWNER")) + "." + StringUtil.trimToNull((String)row.get("NAME")) : StringUtil.trimToNull((String)row.get("REFERENCED_OWNER")) + "." + StringUtil.trimToNull((String)row.get("NAME"));
                        String bName = StringUtil.trimToNull((String)row.get("REFERENCED_OWNER")) + "." + StringUtil.trimToNull((String)row.get("REFERENCED_NAME"));
                        graph.add(bName, tabName);
                    }
                    break block7;
                }
                if (!(database instanceof MSSQLDatabase)) break block10;
                Executor executor5 = Scope.getCurrentScope().getSingleton(ExecutorService.class).getExecutor("jdbc", database);
                String sql = "select object_schema_name(referencing_id) as referencing_schema_name, object_name(referencing_id) as referencing_name, object_name(referenced_id) as referenced_name, object_schema_name(referenced_id) as referenced_schema_name  from sys.sql_expression_dependencies depz where (" + StringUtil.join(schemas, " OR ", obj -> "object_schema_name(referenced_id)='" + obj + "'") + ")";
                sql = sql + " UNION select object_schema_name(object_id) as referencing_schema_name, object_name(object_id) as referencing_name, object_name(parent_object_id) as referenced_name, object_schema_name(parent_object_id) as referenced_schema_name from sys.objects where parent_object_id > 0 and is_ms_shipped=0 and (" + StringUtil.join(schemas, " OR ", obj -> "object_schema_name(object_id)='" + obj + "'") + ")";
                sql = sql + " UNION select object_schema_name(fk.object_id) as referencing_schema_name, fk.name as referencing_name, i.name as referenced_name, object_schema_name(i.object_id) as referenced_schema_name from sys.foreign_keys fk join sys.indexes i on fk.referenced_object_id=i.object_id and fk.key_index_id=i.index_id where fk.is_ms_shipped=0 and (" + StringUtil.join(schemas, " OR ", obj -> "object_schema_name(fk.object_id)='" + obj + "'") + ")";
                sql = sql + " UNION select object_schema_name(i.object_id) as referencing_schema_name, object_name(i.object_id) as referencing_name, s.name as referenced_name, null as referenced_schema_name from sys.indexes i join sys.partition_schemes s on i.data_space_id = s.data_space_id";
                sql = sql + " UNION select null as referencing_schema_name, s.name as referencing_name, f.name as referenced_name, null as referenced_schema_name from sys.partition_functions f join sys.partition_schemes s on s.function_id=f.function_id";
                sql = sql + " UNION select null as referencing_schema_name, s.name as referencing_name, fg.name as referenced_name, null as referenced_schema_name from sys.partition_schemes s join sys.destination_data_spaces ds on s.data_space_id=ds.partition_scheme_id join sys.filegroups fg on ds.data_space_id=fg.data_space_id";
                sql = sql + " UNION select distinct null as referencing_schema_name, f.name as referencing_name, ds.name as referenced_name, null as referenced_schema_name from sys.database_files f join sys.data_spaces ds on f.data_space_id=ds.data_space_id where f.data_space_id > 1";
                sql = sql + " UNION select object_schema_name(t.object_id) as referencing_schema_name, t.name as referencing_name, ds.name as referenced_name, null as referenced_schema_name from sys.tables t join sys.data_spaces ds on t.filestream_data_space_id=ds.data_space_id where t.filestream_data_space_id > 1";
                sql = sql + " UNION select object_schema_name(t.object_id) as referencing_schema_name, t.name as referencing_name, ds.name as referenced_name, null as referenced_schema_name from sys.tables t join sys.data_spaces ds on t.lob_data_space_id=ds.data_space_id where t.lob_data_space_id > 1";
                sql = sql + " UNION select object_schema_name(i.object_id) as referencing_schema_name, i.name as referencing_name, ds.name as referenced_name, null as referenced_schema_name from sys.indexes i join sys.data_spaces ds on i.data_space_id=ds.data_space_id where i.data_space_id > 1";
                sql = sql + " UNION select object_schema_name(i.object_id) as referencing_schema_name, i.name as referencing_name, object_name(i.object_id) as referenced_name, object_schema_name(i.object_id) as referenced_schema_name from sys.indexes i where " + StringUtil.join(schemas, " OR ", obj -> "object_schema_name(i.object_id)='" + obj + "'");
                sql = sql + " UNION SELECT SCHEMA_NAME(SCHEMA_ID) as referencing_schema_name, name as referencing_name, PARSENAME(BASE_OBJECT_NAME,1) AS referenced_name, (CASE WHEN PARSENAME(BASE_OBJECT_NAME,2) IS NULL THEN schema_name(schema_id) else PARSENAME(BASE_OBJECT_NAME,2) END) AS referenced_schema_name FROM sys.synonyms WHERE is_ms_shipped='false' AND " + StringUtil.join(schemas, " OR ", obj -> "SCHEMA_NAME(SCHEMA_ID)='" + obj + "'");
                List<Map<String, ?>> rs = executor5.queryForList(new RawSqlStatement(sql = sql + " UNION select object_schema_name(c.object_id) as referencing_schema_name, c.name as referencing_name, object_schema_name(nc.object_id) as referenced_schema_name, nc.name as referenced_name from sys.indexes c join sys.indexes nc on c.object_id=nc.object_id JOIN sys.objects o ON c.object_id = o.object_id where  c.index_id != nc.index_id and c.type_desc='CLUSTERED' and c.is_unique='true' and (not(nc.type_desc='CLUSTERED') OR nc.is_unique='false') AND o.type_desc='VIEW' AND o.name='AR_DETAIL_OPEN'"));
                if (rs.isEmpty()) break block7;
                for (Map<String, ?> row : rs) {
                    String tabName;
                    String bName = StringUtil.trimToNull((String)row.get("REFERENCED_SCHEMA_NAME")) + "." + StringUtil.trimToNull((String)row.get("REFERENCED_NAME"));
                    if (bName.equals(tabName = StringUtil.trimToNull((String)row.get("REFERENCING_SCHEMA_NAME")) + "." + StringUtil.trimToNull((String)row.get("REFERENCING_NAME")))) continue;
                    graph.add(bName, tabName);
                }
                break block7;
            }
            if (database instanceof PostgresDatabase) {
                String sql = this.queryForDependenciesPostgreSql(schemas);
                Executor executor6 = Scope.getCurrentScope().getSingleton(ExecutorService.class).getExecutor("jdbc", database);
                List<Map<String, ?>> queryForListResult = executor6.queryForList(new RawSqlStatement(sql));
                for (Map<String, ?> row : queryForListResult) {
                    String bName = StringUtil.trimToEmpty((String)row.get("REFERENCING_SCHEMA_NAME")) + "." + StringUtil.trimToEmpty((String)row.get("REFERENCING_NAME"));
                    String tabName = StringUtil.trimToEmpty((String)row.get("REFERENCED_SCHEMA_NAME")) + "." + StringUtil.trimToEmpty((String)row.get("REFERENCED_NAME"));
                    if (tabName.isEmpty() || bName.isEmpty()) continue;
                    graph.add(bName.replace("\"", ""), tabName.replace("\"", ""));
                    graph.add(bName.replace("\"", "").replaceAll("\\s*\\([^)]*\\)\\s*", ""), tabName.replace("\"", "").replaceAll("\\s*\\([^)]*\\)\\s*", ""));
                }
            }
        }
    }

    private String queryForDependenciesPostgreSql(List<String> schemas) {
        return "WITH RECURSIVE preference AS (\n    SELECT 10 AS max_depth  -- The deeper the recursion goes, the slower it performs.\n         , 16384 AS min_oid -- user objects only\n         , '^(londiste|pgq|pg_toast)'::text AS schema_exclusion\n         , '^pg_(conversion|language|ts_(dict|template))'::text AS class_exclusion\n         , '{\"SCHEMA\":\"00\", \"TABLE\":\"01\", \"CONSTRAINT\":\"02\", \"DEFAULT\":\"03\",\n      \"INDEX\":\"05\", \"SEQUENCE\":\"06\", \"TRIGGER\":\"07\", \"FUNCTION\":\"08\",\n      \"VIEW\":\"10\", \"MVIEW\":\"11\", \"FOREIGN\":\"12\"}'::json AS type_ranks),\n               dependency_pair AS (\n                   WITH relation_object AS ( SELECT oid, oid::regclass::text AS object_name  FROM pg_class )\n                   SELECT DISTINCT                          substring(pg_identify_object(classid, objid, 0)::text, E'(\\\\w+?)\\\\.') as referenced_schema_name,                          CASE classid\n                              WHEN 'pg_constraint'::regclass THEN (SELECT CONTYPE::text FROM pg_constraint WHERE oid = objid)\n                              ELSE objid::text\n                              END AS CONTYPE,\n                         CASE classid\n                              WHEN 'pg_attrdef'::regclass THEN (SELECT attname FROM pg_attrdef d JOIN pg_attribute c ON (c.attrelid,c.attnum)=(d.adrelid,d.adnum) WHERE d.oid = objid)\n                              WHEN 'pg_cast'::regclass THEN (SELECT concat(castsource::regtype::text, ' AS ', casttarget::regtype::text,' WITH ', castfunc::regprocedure::text) FROM pg_cast WHERE oid = objid)\n                              WHEN 'pg_class'::regclass THEN rel.object_name\n                              WHEN 'pg_constraint'::regclass THEN (SELECT conname FROM pg_constraint WHERE oid = objid)\n                              WHEN 'pg_extension'::regclass THEN (SELECT extname FROM pg_extension WHERE oid = objid)\n                              WHEN 'pg_namespace'::regclass THEN (SELECT nspname FROM pg_namespace WHERE oid = objid)\n                              WHEN 'pg_opclass'::regclass THEN (SELECT opcname FROM pg_opclass WHERE oid = objid)\n                              WHEN 'pg_operator'::regclass THEN (SELECT oprname FROM pg_operator WHERE oid = objid)\n                              WHEN 'pg_opfamily'::regclass THEN (SELECT opfname FROM pg_opfamily WHERE oid = objid)\n                              WHEN 'pg_proc'::regclass THEN objid::regprocedure::text\n                              WHEN 'pg_rewrite'::regclass THEN (SELECT ev_class::regclass::text FROM pg_rewrite WHERE oid = objid)\n                              WHEN 'pg_trigger'::regclass THEN (SELECT tgname FROM pg_trigger WHERE oid = objid)\n                              WHEN 'pg_type'::regclass THEN objid::regtype::text\n                              ELSE objid::text\n                              END AS REFERENCED_NAME,\n                          substring(pg_identify_object(refclassid, refobjid, 0)::text, E'(\\\\w+?)\\\\.') as referencing_schema_name,                           CASE refclassid\n                              WHEN 'pg_namespace'::regclass THEN (SELECT nspname FROM pg_namespace WHERE oid = refobjid)\n                              WHEN 'pg_class'::regclass THEN rrel.object_name\n                              WHEN 'pg_opfamily'::regclass THEN (SELECT opfname FROM pg_opfamily WHERE oid = refobjid)\n                              WHEN 'pg_proc'::regclass THEN refobjid::regprocedure::text\n                              WHEN 'pg_type'::regclass THEN refobjid::regtype::text\n                              ELSE refobjid::text\n                              END AS REFERENCING_NAME\n                   FROM pg_depend dep\n                            LEFT JOIN relation_object rel ON rel.oid = dep.objid\n                            LEFT JOIN relation_object rrel ON rrel.oid = dep.refobjid, preference\n                   WHERE deptype = ANY('{n,a}')\n                     AND objid >= preference.min_oid\n                     AND (refobjid >= preference.min_oid OR refobjid = 2200) -- need public schema as root node\n                     AND classid::regclass::text !~ preference.class_exclusion\n                     AND refclassid::regclass::text !~ preference.class_exclusion\n                     AND COALESCE(SUBSTRING(objid::regclass::text, E'^(\\\\\\\\w+)\\\\\\\\.'),'') !~ preference.schema_exclusion\n                     AND COALESCE(SUBSTRING(refobjid::regclass::text, E'^(\\\\\\\\w+)\\\\\\\\.'),'') !~ preference.schema_exclusion\n                   GROUP BY classid, objid, refclassid, refobjid, deptype, rel.object_name, rrel.object_name\n               )\n select referenced_schema_name,\n    (CASE\n      WHEN position('.' in referenced_name) >0 THEN substring(referenced_name from position('.' in referenced_name)+1 for length(referenced_name))\n      ELSE referenced_name\n    END)  AS referenced_name, \n   referencing_schema_name,\n   (CASE\n      WHEN position('.' in referencing_name) >0 THEN substring(referencing_name from position('.' in referencing_name)+1 for length(referencing_name))\n      ELSE referencing_name\n    END)  AS referencing_name from dependency_pair where REFERENCED_NAME != REFERENCING_NAME  AND (" + StringUtil.join(schemas, " OR ", obj -> " REFERENCED_NAME like '" + obj + ".%' OR REFERENCED_NAME NOT LIKE '%.%'") + ")\n AND (CONTYPE::text != 'p' AND CONTYPE::text != 'f')\n AND referencing_schema_name is not null and referencing_name is not null";
    }

    protected List<Class<? extends DatabaseObject>> getOrderedOutputTypes(Class<? extends ChangeGenerator> generatorType) {
        Database comparisonDatabase = this.diffResult.getComparisonSnapshot().getDatabase();
        DependencyGraph graph = new DependencyGraph();
        for (Class<? extends DatabaseObject> type : this.diffResult.getReferenceSnapshot().getSnapshotControl().getTypesToInclude()) {
            graph.addType(type);
        }
        List<Class<? extends DatabaseObject>> types = graph.sort(comparisonDatabase, generatorType);
        if (!loggedOrderFor.contains(generatorType)) {
            StringBuilder log = new StringBuilder(generatorType.getSimpleName() + " type order: ");
            for (Class<? extends DatabaseObject> type : types) {
                log.append("    ").append(type.getName());
            }
            Scope.getCurrentScope().getLog(this.getClass()).fine(log.toString());
            loggedOrderFor.add(generatorType);
        }
        return types;
    }

    private void addToChangeSets(Change[] changes, List<ChangeSet> changeSets, ObjectQuotingStrategy quotingStrategy, String created) {
        if (changes != null) {
            ChangeSetService service2 = ChangeSetServiceFactory.getInstance().createChangeSetService();
            if (this.useSeparateChangeSets(changes)) {
                for (Change change : changes) {
                    boolean runOnChange = this.isContainedInRunOnChangeTypes(change);
                    ChangeSet changeSet = service2.createChangeSet(this.generateId(changes), this.getChangeSetAuthor(), false, runOnChange, this.changeSetPath, this.changeSetContext, null, null, null, true, quotingStrategy, null);
                    changeSet.setCreated(created);
                    if (this.diffOutputControl.getLabels() != null) {
                        changeSet.setLabels(this.diffOutputControl.getLabels());
                    } else {
                        changeSet.setLabels(new Labels(this.changeSetLabels));
                    }
                    if (change instanceof ReplaceIfExists && this.isContainedInReplaceIfExistsTypes(change)) {
                        ((ReplaceIfExists)((Object)change)).setReplaceIfExists(true);
                    }
                    changeSet.addChange(change);
                    changeSets.add(changeSet);
                }
            } else {
                boolean runOnChange = Arrays.asList(changes).stream().allMatch(this::isContainedInRunOnChangeTypes);
                ChangeSet changeSet = service2.createChangeSet(this.generateId(changes), this.getChangeSetAuthor(), false, runOnChange, this.changeSetPath, this.changeSetContext, null, null, null, true, quotingStrategy, null);
                changeSet.setCreated(created);
                if (this.diffOutputControl.getLabels() != null) {
                    changeSet.setLabels(this.diffOutputControl.getLabels());
                } else {
                    changeSet.setLabels(new Labels(this.changeSetLabels));
                }
                for (Change change : changes) {
                    if (change instanceof ReplaceIfExists && this.isContainedInReplaceIfExistsTypes(change)) {
                        ((ReplaceIfExists)((Object)change)).setReplaceIfExists(true);
                    }
                    changeSet.addChange(change);
                }
                changeSets.add(changeSet);
            }
        }
    }

    protected boolean useSeparateChangeSets(Change[] changes) {
        boolean sawAutocommitBefore = false;
        for (Change change : changes) {
            boolean thisStatementAutocommits;
            boolean bl = thisStatementAutocommits = !(change instanceof InsertDataChange) && !(change instanceof DeleteDataChange) && !(change instanceof UpdateDataChange) && !(change instanceof LoadDataChange);
            if (change instanceof RawSQLChange && ((RawSQLChange)change).getSql().trim().matches("SET\\s+\\w+\\s+\\w+")) {
                thisStatementAutocommits = false;
            }
            if (!thisStatementAutocommits) continue;
            if (sawAutocommitBefore) {
                return true;
            }
            sawAutocommitBefore = true;
        }
        return false;
    }

    protected String getChangeSetAuthor() {
        if (this.changeSetAuthor != null) {
            return this.changeSetAuthor;
        }
        String author = System.getProperty("user.name");
        if (StringUtil.trimToNull(author) == null) {
            return "diff-generated";
        }
        return author + " (generated)";
    }

    public void setChangeSetAuthor(String changeSetAuthor) {
        this.changeSetAuthor = changeSetAuthor;
    }

    public String getChangeSetPath() {
        return this.changeSetPath;
    }

    public void setChangeSetPath(String changeSetPath) {
        this.changeSetPath = changeSetPath;
    }

    public void setChangeSetRunOnChangeTypes(String[] runOnChangeTypes) {
        this.changeSetRunOnChangeTypes = runOnChangeTypes;
    }

    protected String[] getChangeSetRunOnChangeTypes() {
        return this.changeSetRunOnChangeTypes;
    }

    private boolean isContainedInRunOnChangeTypes(Change change) {
        return this.getChangeSetRunOnChangeTypes() != null && Arrays.asList(this.getChangeSetRunOnChangeTypes()).contains(change.getSerializedObjectName());
    }

    public void setChangeReplaceIfExistsTypes(String[] replaceIfExistsTypes) {
        this.changeReplaceIfExistsTypes = replaceIfExistsTypes;
    }

    protected String[] getChangeReplaceIfExistsTypes() {
        return this.changeReplaceIfExistsTypes;
    }

    private boolean isContainedInReplaceIfExistsTypes(Change change) {
        return this.getChangeReplaceIfExistsTypes() != null && Arrays.asList(this.getChangeReplaceIfExistsTypes()).contains(change.getSerializedObjectName());
    }

    public void setIdRoot(String idRoot) {
        this.idRoot = idRoot;
        this.overriddenIdRoot = true;
    }

    protected String generateId(Change[] changes) {
        String desc = "";
        if (GlobalConfiguration.GENERATED_CHANGESET_IDS_INCLUDE_DESCRIPTION.getCurrentValue().booleanValue()) {
            if (!this.overriddenIdRoot) {
                this.idRoot = Long.toString(Long.decode(this.idRoot), 36);
                this.idRoot = this.idRoot.substring(this.idRoot.length() - 4);
                this.overriddenIdRoot = true;
            }
            if (changes != null && changes.length > 0) {
                desc = " (" + StringUtil.join(changes, " :: ", Change::getDescription) + ")";
            }
            if (desc.length() > 150) {
                desc = desc.substring(0, 146) + "...)";
            }
        }
        return this.idRoot + "-" + this.changeNumber++ + desc;
    }

    private static class DependencyGraph {
        private final Map<Class<? extends DatabaseObject>, Node> allNodes = new HashMap<Class<? extends DatabaseObject>, Node>();

        private DependencyGraph() {
        }

        private void addType(Class<? extends DatabaseObject> type) {
            this.allNodes.put(type, new Node(type));
        }

        public List<Class<? extends DatabaseObject>> sort(Database database, Class<? extends ChangeGenerator> generatorType) {
            HashMap<Class<? extends DatabaseObject>, Node> newNodes = new HashMap<Class<? extends DatabaseObject>, Node>();
            ChangeGeneratorFactory changeGeneratorFactory = ChangeGeneratorFactory.getInstance();
            for (Class<? extends DatabaseObject> type : this.allNodes.keySet()) {
                for (Class<? extends DatabaseObject> clazz : changeGeneratorFactory.runBeforeTypes(type, database, generatorType)) {
                    Node typeNode = this.retrieveOrCreateNode(newNodes, type);
                    Node afterTypeNode = this.retrieveOrCreateNode(newNodes, clazz);
                    typeNode.addEdge(afterTypeNode);
                }
                for (Class clazz : changeGeneratorFactory.runAfterTypes(type, database, generatorType)) {
                    Node beforeTypeNode = this.retrieveOrCreateNode(newNodes, clazz);
                    Node typeNode = this.retrieveOrCreateNode(newNodes, type);
                    beforeTypeNode.addEdge(typeNode);
                }
            }
            for (Node newNode : newNodes.values()) {
                if (this.allNodes.containsKey(newNode.type)) continue;
                this.allNodes.put(newNode.type, newNode);
            }
            ArrayList<Node> returnNodes = new ArrayList<Node>();
            TreeSet<Node> nodesWithNoIncomingEdges = new TreeSet<Node>(Comparator.comparing(o -> o.type.getName()));
            for (Node node : this.allNodes.values()) {
                if (!node.inEdges.isEmpty()) continue;
                nodesWithNoIncomingEdges.add(node);
            }
            while (!nodesWithNoIncomingEdges.isEmpty()) {
                Node node = (Node)nodesWithNoIncomingEdges.iterator().next();
                nodesWithNoIncomingEdges.remove(node);
                returnNodes.add(node);
                Iterator<Edge> iterator2 = node.outEdges.iterator();
                while (iterator2.hasNext()) {
                    Edge edge = iterator2.next();
                    Node nodePointedTo = edge.to;
                    iterator2.remove();
                    nodePointedTo.inEdges.remove(edge);
                    if (!nodePointedTo.inEdges.isEmpty()) continue;
                    nodesWithNoIncomingEdges.add(nodePointedTo);
                }
            }
            this.checkForCycleInDependencies(generatorType);
            ArrayList<Class<? extends DatabaseObject>> returnList = new ArrayList<Class<? extends DatabaseObject>>();
            for (Node node : returnNodes) {
                returnList.add(node.type);
            }
            return returnList;
        }

        private Node retrieveOrCreateNode(Map<Class<? extends DatabaseObject>, Node> newNodes, Class<? extends DatabaseObject> type) {
            Node node;
            if (this.allNodes.containsKey(type)) {
                node = this.allNodes.get(type);
            } else if (newNodes.containsKey(type)) {
                node = newNodes.get(type);
            } else {
                node = new Node(type);
                newNodes.put(type, node);
            }
            return node;
        }

        private void checkForCycleInDependencies(Class<? extends ChangeGenerator> generatorType) {
            for (Node n : this.allNodes.values()) {
                if (n.inEdges.isEmpty()) continue;
                StringBuilder message = new StringBuilder("Could not resolve " + generatorType.getSimpleName() + " dependencies due to dependency cycle. Dependencies: \n");
                for (Node node : this.allNodes.values()) {
                    TreeSet<String> fromTypes = new TreeSet<String>();
                    TreeSet<String> toTypes = new TreeSet<String>();
                    for (Edge edge : node.inEdges) {
                        fromTypes.add(edge.from.type.getSimpleName());
                    }
                    for (Edge edge : node.outEdges) {
                        toTypes.add(edge.to.type.getSimpleName());
                    }
                    String from = StringUtil.join(fromTypes, ",");
                    String to = StringUtil.join(toTypes, ",");
                    message.append("    [").append(from).append("] -> ").append(node.type.getSimpleName()).append(" -> [").append(to).append("]\n");
                }
                throw new UnexpectedLiquibaseException(message.toString());
            }
        }

        static class Node {
            public final Class<? extends DatabaseObject> type;
            public final Set<Edge> inEdges;
            public final Set<Edge> outEdges;

            public Node(Class<? extends DatabaseObject> type) {
                this.type = type;
                this.inEdges = new HashSet<Edge>();
                this.outEdges = new HashSet<Edge>();
            }

            public Node addEdge(Node node) {
                Edge e = new Edge(this, node);
                this.outEdges.add(e);
                node.inEdges.add(e);
                return this;
            }

            public String toString() {
                return this.type.getName();
            }
        }

        static class Edge {
            public final Node from;
            public final Node to;

            public Edge(Node from, Node to) {
                this.from = from;
                this.to = to;
            }

            public boolean equals(Object obj) {
                if (obj == null) {
                    return false;
                }
                if (!(obj instanceof Edge)) {
                    return false;
                }
                Edge e = (Edge)obj;
                return e.from == this.from && e.to == this.to;
            }

            public int hashCode() {
                return (this.from.toString() + "." + this.to.toString()).hashCode();
            }
        }
    }
}

