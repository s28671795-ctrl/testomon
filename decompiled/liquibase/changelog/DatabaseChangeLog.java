/*
 * Decompiled with CFR 0.152.
 */
package liquibase.changelog;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import liquibase.ContextExpression;
import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Labels;
import liquibase.RuntimeEnvironment;
import liquibase.Scope;
import liquibase.change.visitor.ChangeVisitor;
import liquibase.change.visitor.ChangeVisitorFactory;
import liquibase.changelog.ChangeLogIterator;
import liquibase.changelog.ChangeLogParameters;
import liquibase.changelog.ChangeSet;
import liquibase.changelog.IncludeAllFilter;
import liquibase.changelog.ModifyChangeSets;
import liquibase.changelog.RanChangeSet;
import liquibase.changelog.filter.ContextChangeSetFilter;
import liquibase.changelog.filter.DbmsChangeSetFilter;
import liquibase.changelog.filter.LabelChangeSetFilter;
import liquibase.changelog.visitor.ValidatingVisitor;
import liquibase.changelog.visitor.ValidatingVisitorGenerator;
import liquibase.changelog.visitor.ValidatingVisitorGeneratorFactory;
import liquibase.changeset.ChangeSetService;
import liquibase.changeset.ChangeSetServiceFactory;
import liquibase.database.Database;
import liquibase.database.DatabaseList;
import liquibase.database.ObjectQuotingStrategy;
import liquibase.exception.ChangeLogParseException;
import liquibase.exception.LiquibaseException;
import liquibase.exception.SetupException;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.exception.UnknownChangeLogParameterException;
import liquibase.exception.UnknownChangelogFormatException;
import liquibase.exception.ValidationFailedException;
import liquibase.logging.Logger;
import liquibase.logging.mdc.customobjects.DuplicateChangesets;
import liquibase.logging.mdc.customobjects.MdcChangeset;
import liquibase.parser.ChangeLogParser;
import liquibase.parser.ChangeLogParserConfiguration;
import liquibase.parser.ChangeLogParserFactory;
import liquibase.parser.core.ParsedNode;
import liquibase.parser.core.ParsedNodeException;
import liquibase.precondition.Conditional;
import liquibase.precondition.Precondition;
import liquibase.precondition.core.PreconditionContainer;
import liquibase.resource.Resource;
import liquibase.resource.ResourceAccessor;
import liquibase.servicelocator.LiquibaseService;
import liquibase.util.FileUtil;
import liquibase.util.StringUtil;
import org.apache.commons.io.FilenameUtils;

public class DatabaseChangeLog
implements Comparable<DatabaseChangeLog>,
Conditional {
    private static final ThreadLocal<DatabaseChangeLog> ROOT_CHANGE_LOG = new ThreadLocal();
    private static final ThreadLocal<DatabaseChangeLog> PARENT_CHANGE_LOG = new ThreadLocal();
    private static final Logger LOG = Scope.getCurrentScope().getLog(DatabaseChangeLog.class);
    private static final Pattern SLASH_PATTERN = Pattern.compile("^/");
    private static final Pattern DOUBLE_BACK_SLASH_PATTERN = Pattern.compile("\\\\");
    private static final Pattern NO_LETTER_PATTERN = Pattern.compile("^[a-zA-Z]:");
    public static final String SEEN_CHANGELOGS_PATHS_SCOPE_KEY = "SEEN_CHANGELOG_PATHS";
    public static final String FILE = "file";
    public static final String CONTEXT_FILTER = "contextFilter";
    public static final String CONTEXT = "context";
    public static final String LABELS = "labels";
    public static final String IGNORE = "ignore";
    public static final String RELATIVE_TO_CHANGELOG_FILE = "relativeToChangelogFile";
    public static final String ERROR_IF_MISSING = "errorIfMissing";
    public static final String MODIFY_CHANGE_SETS = "modifyChangeSets";
    public static final String PATH = "path";
    public static final String FILTER = "filter";
    public static final String RESOURCE_FILTER = "resourceFilter";
    public static final String RESOURCE_COMPARATOR = "resourceComparator";
    public static final String MIN_DEPTH = "minDepth";
    public static final String MAX_DEPTH = "maxDepth";
    public static final String ENDS_WITH_FILTER = "endsWithFilter";
    public static final String ERROR_IF_MISSING_OR_EMPTY = "errorIfMissingOrEmpty";
    public static final String CHANGE_SET = "changeSet";
    public static final String DBMS = "dbms";
    public static final String INCLUDE = "include";
    public static final String INCLUDE_ALL = "includeAll";
    public static final String PRE_CONDITIONS = "preConditions";
    public static final String REMOVE_CHANGE_SET_PROPERTY = "removeChangeSetProperty";
    public static final String PROPERTY = "property";
    public static final String NAME = "name";
    public static final String VALUE = "value";
    public static final String GLOBAL = "global";
    private final PreconditionContainer preconditionContainer = new GlobalPreconditionContainer();
    private String physicalFilePath;
    private String logicalFilePath;
    private ObjectQuotingStrategy objectQuotingStrategy;
    private List<ChangeVisitor> changeVisitors = new ArrayList<ChangeVisitor>();
    private final List<ChangeSet> changeSets = new ArrayList<ChangeSet>();
    private final List<ChangeSet> skippedChangeSets = new ArrayList<ChangeSet>();
    private ChangeLogParameters changeLogParameters;
    private RuntimeEnvironment runtimeEnvironment;
    private DatabaseChangeLog rootChangeLog = ROOT_CHANGE_LOG.get();
    private DatabaseChangeLog parentChangeLog = PARENT_CHANGE_LOG.get();
    private ContextExpression contextFilter;
    private ContextExpression includeContextFilter;
    private Labels includeLabels;
    private boolean includeIgnore;

    public DatabaseChangeLog() {
    }

    public DatabaseChangeLog(String physicalFilePath) {
        this.physicalFilePath = physicalFilePath;
    }

    public void setRootChangeLog(DatabaseChangeLog rootChangeLog) {
        this.rootChangeLog = rootChangeLog;
    }

    public DatabaseChangeLog getRootChangeLog() {
        return this.rootChangeLog != null ? this.rootChangeLog : this;
    }

    public void setParentChangeLog(DatabaseChangeLog parentChangeLog) {
        this.parentChangeLog = parentChangeLog;
    }

    public DatabaseChangeLog getParentChangeLog() {
        return this.parentChangeLog;
    }

    public RuntimeEnvironment getRuntimeEnvironment() {
        return this.runtimeEnvironment;
    }

    public void setRuntimeEnvironment(RuntimeEnvironment runtimeEnvironment) {
        this.runtimeEnvironment = runtimeEnvironment;
    }

    @Override
    public PreconditionContainer getPreconditions() {
        return this.preconditionContainer;
    }

    @Override
    public void setPreconditions(PreconditionContainer precond) {
        this.preconditionContainer.addNestedPrecondition(precond);
    }

    public ChangeLogParameters getChangeLogParameters() {
        return this.changeLogParameters;
    }

    public void setChangeLogParameters(ChangeLogParameters changeLogParameters) {
        this.changeLogParameters = changeLogParameters;
    }

    public String getPhysicalFilePath() {
        return this.physicalFilePath;
    }

    public void setPhysicalFilePath(String physicalFilePath) {
        this.physicalFilePath = physicalFilePath;
    }

    public String getLogicalFilePath() {
        String returnPath = this.logicalFilePath;
        if (this.logicalFilePath == null) {
            returnPath = this.physicalFilePath;
        }
        if (returnPath == null) {
            return null;
        }
        String path = DOUBLE_BACK_SLASH_PATTERN.matcher(returnPath).replaceAll("/");
        return SLASH_PATTERN.matcher(path).replaceFirst("");
    }

    public void setLogicalFilePath(String logicalFilePath) {
        this.logicalFilePath = logicalFilePath;
    }

    public String getFilePath() {
        if (this.logicalFilePath == null) {
            return this.physicalFilePath;
        }
        return this.getLogicalFilePath();
    }

    public ObjectQuotingStrategy getObjectQuotingStrategy() {
        return this.objectQuotingStrategy;
    }

    public void setObjectQuotingStrategy(ObjectQuotingStrategy objectQuotingStrategy) {
        this.objectQuotingStrategy = objectQuotingStrategy;
    }

    public ContextExpression getContexts() {
        return this.getContextFilter();
    }

    public void setContexts(ContextExpression contexts) {
        this.setContextFilter(contexts);
    }

    public ContextExpression getContextFilter() {
        return this.contextFilter;
    }

    public void setContextFilter(ContextExpression contextFilter) {
        this.contextFilter = contextFilter;
    }

    public ContextExpression getIncludeContextFilter() {
        return this.includeContextFilter;
    }

    public void setIncludeLabels(LabelExpression labels) {
        this.includeLabels = new Labels(labels.toString());
    }

    public void setIncludeLabels(Labels labels) {
        this.includeLabels = labels;
    }

    public Labels getIncludeLabels() {
        return this.includeLabels;
    }

    public void setIncludeIgnore(boolean ignore) {
        this.includeIgnore = ignore;
    }

    public boolean isIncludeIgnore() {
        return this.includeIgnore;
    }

    public void setIncludeContexts(ContextExpression includeContexts) {
        this.setIncludeContextFilter(includeContexts);
    }

    public void setIncludeContextFilter(ContextExpression includeContextFilter) {
        this.includeContextFilter = includeContextFilter;
    }

    public String toString() {
        return this.getFilePath();
    }

    @Override
    public int compareTo(DatabaseChangeLog o) {
        return this.getFilePath().compareTo(o.getFilePath());
    }

    public List<ChangeVisitor> getChangeVisitors() {
        return this.changeVisitors;
    }

    public ChangeSet getChangeSet(String path, String author, String id) {
        List<ChangeSet> possibleChangeSets = this.getChangeSets(path, author, id);
        if (possibleChangeSets.isEmpty()) {
            return null;
        }
        return possibleChangeSets.get(0);
    }

    public List<ChangeSet> getChangeSets(String path, String author, String id) {
        ArrayList<ChangeSet> changeSetsToReturn = new ArrayList<ChangeSet>();
        String normalizedPath = DatabaseChangeLog.normalizePath(path);
        if (normalizedPath != null) {
            for (ChangeSet changeSet : this.changeSets) {
                String changesetNormalizedPath;
                if (!changeSet.getAuthor().equalsIgnoreCase(author) || !changeSet.getId().equalsIgnoreCase(id) || !this.isDbmsMatch(changeSet.getDbmsSet()) || (changesetNormalizedPath = DatabaseChangeLog.normalizePath(changeSet.getFilePath())) == null || !changesetNormalizedPath.equalsIgnoreCase(normalizedPath)) continue;
                changeSetsToReturn.add(changeSet);
            }
        }
        return changeSetsToReturn;
    }

    public List<ChangeSet> getChangeSets() {
        return this.changeSets;
    }

    public List<ChangeSet> getSkippedChangeSets() {
        return this.skippedChangeSets;
    }

    public void addChangeSet(ChangeSet changeSet) {
        if (changeSet.getRunOrder() == null) {
            ListIterator<ChangeSet> it = this.changeSets.listIterator(this.changeSets.size());
            boolean added = false;
            while (it.hasPrevious() && !added) {
                if ("last".equals(it.previous().getRunOrder())) continue;
                it.next();
                it.add(changeSet);
                added = true;
            }
            if (!added) {
                it.add(changeSet);
            }
        } else if ("first".equals(changeSet.getRunOrder())) {
            ListIterator<ChangeSet> it = this.changeSets.listIterator();
            boolean added = false;
            while (it.hasNext() && !added) {
                if ("first".equals(it.next().getRunOrder())) continue;
                it.previous();
                it.add(changeSet);
                added = true;
            }
            if (!added) {
                this.changeSets.add(changeSet);
            }
        } else if ("last".equals(changeSet.getRunOrder())) {
            this.changeSets.add(changeSet);
        } else {
            throw new UnexpectedLiquibaseException("Unknown runOrder: " + changeSet.getRunOrder());
        }
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        DatabaseChangeLog that = (DatabaseChangeLog)o;
        return this.getFilePath().equals(that.getFilePath());
    }

    public int hashCode() {
        return this.getFilePath().hashCode();
    }

    public void validate(Database database, String ... contexts) throws LiquibaseException {
        this.validate(database, new Contexts(contexts), new LabelExpression());
    }

    public void validate(Database database, Contexts contexts, LabelExpression labelExpression) throws LiquibaseException {
        database.setObjectQuotingStrategy(this.objectQuotingStrategy);
        ChangeLogIterator logIterator = new ChangeLogIterator(this, new DbmsChangeSetFilter(database), new ContextChangeSetFilter(contexts), new LabelChangeSetFilter(labelExpression));
        ValidatingVisitorGeneratorFactory validatingVisitorGeneratorFactory = Scope.getCurrentScope().getSingleton(ValidatingVisitorGeneratorFactory.class);
        ValidatingVisitorGenerator generator = validatingVisitorGeneratorFactory.getValidatingVisitorGenerator();
        ValidatingVisitor validatingVisitor = generator.generateValidatingVisitor(database.getRanChangeSetList());
        validatingVisitor.validate(database, this);
        logIterator.run(validatingVisitor, new RuntimeEnvironment(database, contexts, labelExpression));
        Logger log = Scope.getCurrentScope().getLog(this.getClass());
        for (String message : validatingVisitor.getWarnings().getMessages()) {
            log.warning(message);
        }
        if (!validatingVisitor.validationPassed()) {
            Scope.getCurrentScope().addMdcValue("deploymentOutcome", "fail");
            List<MdcChangeset> duplicateChangesetsMdc = validatingVisitor.getDuplicateChangeSets().stream().map(MdcChangeset::fromChangeset).collect(Collectors.toList());
            Scope.getCurrentScope().addMdcValue("duplicateChangesets", new DuplicateChangesets(duplicateChangesetsMdc));
            Scope.getCurrentScope().getLog(this.getClass()).info("Change failed validation!");
            throw new ValidationFailedException(validatingVisitor);
        }
    }

    public ChangeSet getChangeSet(RanChangeSet ranChangeSet) {
        ChangeSet changeSet = this.getChangeSet(ranChangeSet.getChangeLog(), ranChangeSet.getAuthor(), ranChangeSet.getId());
        if (changeSet != null) {
            changeSet.setStoredFilePath(ranChangeSet.getStoredChangeLog());
        }
        return changeSet;
    }

    public List<ChangeSet> getChangeSets(RanChangeSet ranChangeSet) {
        List<ChangeSet> changesets = this.getChangeSets(ranChangeSet.getChangeLog(), ranChangeSet.getAuthor(), ranChangeSet.getId());
        changesets.forEach(c -> c.setStoredFilePath(ranChangeSet.getStoredChangeLog()));
        return changesets;
    }

    public void load(ParsedNode parsedNode, ResourceAccessor resourceAccessor) throws ParsedNodeException, SetupException {
        this.setLogicalFilePath(parsedNode.getChildValue((String)null, "logicalFilePath", String.class));
        String context = parsedNode.getChildValue((String)null, CONTEXT_FILTER, String.class);
        if (context == null) {
            context = parsedNode.getChildValue((String)null, CONTEXT, String.class);
        }
        this.setContextFilter(new ContextExpression(context));
        String nodeObjectQuotingStrategy = parsedNode.getChildValue((String)null, "objectQuotingStrategy", String.class);
        if (nodeObjectQuotingStrategy != null) {
            this.setObjectQuotingStrategy(ObjectQuotingStrategy.valueOf(nodeObjectQuotingStrategy));
        }
        for (ParsedNode childNode : parsedNode.getChildren()) {
            this.handleChildNode(childNode, resourceAccessor, new HashMap<String, Object>());
        }
    }

    protected void expandExpressions(ParsedNode parsedNode) throws UnknownChangeLogParameterException {
        if (this.changeLogParameters == null) {
            return;
        }
        try {
            List<ParsedNode> children;
            Object value = parsedNode.getValue();
            if (value instanceof String) {
                parsedNode.setValue(this.changeLogParameters.expandExpressions(parsedNode.getValue(String.class), this));
            }
            if ((children = parsedNode.getChildren()) != null) {
                for (ParsedNode child : children) {
                    this.expandExpressions(child);
                }
            }
        }
        catch (ParsedNodeException e) {
            throw new UnexpectedLiquibaseException(e);
        }
    }

    protected void handleChildNode(ParsedNode node, ResourceAccessor resourceAccessor) throws ParsedNodeException, SetupException {
        this.handleChildNode(node, resourceAccessor, new HashMap<String, Object>());
    }

    protected void handleChildNode(ParsedNode node, ResourceAccessor resourceAccessor, Map<String, Object> nodeScratch) throws ParsedNodeException, SetupException {
        this.handleChildNodeHelper(node, resourceAccessor, nodeScratch);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void handleChildNodeHelper(ParsedNode node, ResourceAccessor resourceAccessor, Map<String, Object> nodeScratch) throws ParsedNodeException, SetupException {
        String nodeName;
        this.expandExpressions(node);
        switch (nodeName = node.getName()) {
            case "changeSet": {
                if (this.isDbmsMatch(node.getChildValue((String)null, DBMS, String.class))) {
                    this.addChangeSet(this.createChangeSet(node, resourceAccessor));
                    return;
                }
                this.handleSkippedChangeSet(node);
                return;
            }
            case "modifyChangeSets": {
                ModifyChangeSets modifyChangeSets = this.createModifyChangeSets(node);
                nodeScratch = new HashMap<String, Object>();
                nodeScratch.put(MODIFY_CHANGE_SETS, modifyChangeSets);
                for (ParsedNode modifyChildNode : node.getChildren()) {
                    this.handleChildNode(modifyChildNode, resourceAccessor, nodeScratch);
                }
                nodeScratch.remove(MODIFY_CHANGE_SETS);
                return;
            }
            case "include": {
                String path = node.getChildValue((String)null, FILE, String.class);
                if (path == null) {
                    throw new UnexpectedLiquibaseException("No 'file' attribute on 'include'");
                }
                path = path.replace('\\', '/');
                Scope.getCurrentScope().addMdcValue("changelogFile", path);
                ContextExpression includeContextFilter = new ContextExpression(node.getChildValue((String)null, CONTEXT_FILTER, String.class));
                if (includeContextFilter.isEmpty()) {
                    includeContextFilter = new ContextExpression(node.getChildValue((String)null, CONTEXT, String.class));
                }
                Labels labels = new Labels(node.getChildValue((String)null, LABELS, String.class));
                Boolean ignore = node.getChildValue((String)null, IGNORE, Boolean.class);
                try {
                    this.include(path, node.getChildValue(null, RELATIVE_TO_CHANGELOG_FILE, false), node.getChildValue(null, ERROR_IF_MISSING, true), resourceAccessor, includeContextFilter, labels, ignore, OnUnknownFileFormat.FAIL, (ModifyChangeSets)nodeScratch.get(MODIFY_CHANGE_SETS));
                    return;
                }
                catch (LiquibaseException e) {
                    throw new SetupException(e);
                }
            }
            case "includeAll": {
                String path = node.getChildValue((String)null, PATH, String.class);
                String resourceFilterDef = node.getChildValue((String)null, FILTER, String.class);
                if (resourceFilterDef == null) {
                    resourceFilterDef = node.getChildValue((String)null, RESOURCE_FILTER, String.class);
                }
                IncludeAllFilter resourceFilter = null;
                if (resourceFilterDef != null) {
                    try {
                        resourceFilter = (IncludeAllFilter)Class.forName(resourceFilterDef).getConstructor(new Class[0]).newInstance(new Object[0]);
                    }
                    catch (ReflectiveOperationException e) {
                        throw new SetupException(e);
                    }
                }
                String resourceComparatorDef = node.getChildValue((String)null, RESOURCE_COMPARATOR, String.class);
                Comparator<String> resourceComparator = this.determineResourceComparator(resourceComparatorDef);
                ContextExpression includeContextFilter = new ContextExpression(node.getChildValue((String)null, CONTEXT_FILTER, String.class));
                if (includeContextFilter.isEmpty()) {
                    includeContextFilter = new ContextExpression(node.getChildValue((String)null, CONTEXT, String.class));
                }
                Labels labels = new Labels(node.getChildValue((String)null, LABELS, String.class));
                Boolean ignore = node.getChildValue((String)null, IGNORE, Boolean.class);
                if (ignore == null) {
                    ignore = false;
                }
                this.includeAll(path, node.getChildValue(null, RELATIVE_TO_CHANGELOG_FILE, false), resourceFilter, node.getChildValue(null, ERROR_IF_MISSING_OR_EMPTY, true), resourceComparator, resourceAccessor, includeContextFilter, labels, ignore, node.getChildValue(null, MIN_DEPTH, 0), node.getChildValue(null, MAX_DEPTH, Integer.MAX_VALUE), node.getChildValue(null, ENDS_WITH_FILTER, ""), (ModifyChangeSets)nodeScratch.get(MODIFY_CHANGE_SETS));
                return;
            }
            case "preConditions": {
                PreconditionContainer parsedContainer = new PreconditionContainer();
                parsedContainer.load(node, resourceAccessor);
                this.preconditionContainer.addNestedPrecondition(parsedContainer);
                return;
            }
            case "removeChangeSetProperty": {
                ChangeVisitor changeVisitor;
                List<ParsedNode> childNodes = node.getChildren();
                Optional<ParsedNode> changeNode = childNodes.stream().filter(n -> n.getName().equalsIgnoreCase("change")).findFirst();
                if (!changeNode.isPresent() || (changeVisitor = ChangeVisitorFactory.getInstance().create((String)changeNode.get().getValue())) == null) return;
                changeVisitor.load(node, resourceAccessor);
                if (!DatabaseList.definitionMatches(changeVisitor.getDbms(), this.changeLogParameters.getDatabase(), false)) return;
                this.getChangeVisitors().add(changeVisitor);
                return;
            }
            case "property": {
                try {
                    String contextFilter = node.getChildValue((String)null, CONTEXT_FILTER, String.class);
                    if (StringUtil.isEmpty(contextFilter)) {
                        contextFilter = node.getChildValue((String)null, CONTEXT, String.class);
                    }
                    String dbms = node.getChildValue((String)null, DBMS, String.class);
                    String labels = node.getChildValue((String)null, LABELS, String.class);
                    Boolean global = node.getChildValue((String)null, GLOBAL, Boolean.class);
                    if (global == null) {
                        global = true;
                    }
                    String file = node.getChildValue((String)null, FILE, String.class);
                    Boolean relativeToChangelogFile = node.getChildValue(null, RELATIVE_TO_CHANGELOG_FILE, Boolean.FALSE);
                    Boolean errorIfMissing = node.getChildValue(null, ERROR_IF_MISSING, Boolean.TRUE);
                    if (file == null) {
                        String name = node.getChildValue((String)null, NAME, String.class);
                        String value = node.getChildValue((String)null, VALUE, String.class);
                        this.changeLogParameters.set(name, (Object)value, contextFilter, labels, dbms, (boolean)global, this);
                        return;
                    }
                    Resource resource = relativeToChangelogFile != false ? resourceAccessor.get(this.getPhysicalFilePath()).resolveSibling(file) : resourceAccessor.get(file);
                    Properties props = new Properties();
                    if (!resource.exists()) {
                        if (errorIfMissing.booleanValue()) {
                            throw new UnexpectedLiquibaseException(FileUtil.getFileNotFoundMessage(file));
                        }
                        Scope.getCurrentScope().getLog(this.getClass()).warning(FileUtil.getFileNotFoundMessage(file));
                        return;
                    }
                    try (InputStream propertiesStream = resource.openInputStream();){
                        props.load(propertiesStream);
                        for (Map.Entry<Object, Object> entry : props.entrySet()) {
                            this.changeLogParameters.set(entry.getKey().toString(), (Object)entry.getValue().toString(), contextFilter, labels, dbms, (boolean)global, this);
                        }
                        return;
                    }
                }
                catch (IOException e) {
                    throw new ParsedNodeException(e);
                }
            }
            default: {
                if (node.getChildren() == null || node.getChildren().isEmpty()) return;
                throw new ParsedNodeException("Unexpected node found under databaseChangeLog: " + nodeName);
            }
        }
    }

    public Comparator<String> determineResourceComparator(String resourceComparatorDef) {
        Comparator<String> resourceComparator = null;
        if (resourceComparatorDef == null) {
            resourceComparator = this.getStandardChangeLogComparator();
        } else {
            try {
                resourceComparator = (Comparator<String>)Class.forName(resourceComparatorDef).getConstructor(new Class[0]).newInstance(new Object[0]);
            }
            catch (ReflectiveOperationException e) {
                Scope.getCurrentScope().getLog(this.getClass()).info("no resourceComparator defined - taking default implementation");
                resourceComparator = this.getStandardChangeLogComparator();
            }
        }
        return resourceComparator;
    }

    private ModifyChangeSets createModifyChangeSets(ParsedNode node) throws ParsedNodeException {
        ChangeSetServiceFactory factory2 = ChangeSetServiceFactory.getInstance();
        ChangeSetService service2 = factory2.createChangeSetService();
        return service2.createModifyChangeSets(node);
    }

    private void handleSkippedChangeSet(ParsedNode node) throws ParsedNodeException {
        if (node.getChildValue((String)null, DBMS, String.class) == null) {
            return;
        }
        String id = node.getChildValue((String)null, "id", String.class);
        String author = node.getChildValue((String)null, "author", String.class);
        String filePath = StringUtil.trimToNull(node.getChildValue((String)null, "logicalFilePath", String.class));
        filePath = filePath == null ? this.getFilePath() : filePath.replace("\\\\", "/").replaceFirst("^/", "");
        String dbmsList = node.getChildValue((String)null, DBMS, String.class);
        ChangeSet skippedChangeSet = new ChangeSet(id, author, false, false, filePath, null, dbmsList, this);
        this.skippedChangeSets.add(skippedChangeSet);
    }

    public boolean isDbmsMatch(String dbmsList) {
        return this.isDbmsMatch(DatabaseList.toDbmsSet(dbmsList));
    }

    public boolean isDbmsMatch(Set<String> dbmsSet) {
        return dbmsSet == null || this.changeLogParameters == null || this.changeLogParameters.getValue("database.typeName", this) == null || DatabaseList.definitionMatches(dbmsSet, this.changeLogParameters.getValue("database.typeName", this).toString(), true);
    }

    @Deprecated
    public void includeAll(String pathName, boolean isRelativeToChangelogFile, IncludeAllFilter resourceFilter, boolean errorIfMissingOrEmpty, Comparator<String> resourceComparator, ResourceAccessor resourceAccessor, ContextExpression includeContextFilter, LabelExpression labelExpression, boolean ignore) throws SetupException {
        Labels labels = null;
        if (labelExpression != null && !labelExpression.isEmpty()) {
            labels = new Labels(labelExpression.toString());
        }
        this.includeAll(pathName, isRelativeToChangelogFile, resourceFilter, errorIfMissingOrEmpty, resourceComparator, resourceAccessor, includeContextFilter, labels, ignore, 0, Integer.MAX_VALUE);
    }

    public void includeAll(String pathName, boolean isRelativeToChangelogFile, IncludeAllFilter resourceFilter, boolean errorIfMissingOrEmpty, Comparator<String> resourceComparator, ResourceAccessor resourceAccessor, ContextExpression includeContextFilter, Labels labels, boolean ignore, int minDepth, int maxDepth) throws SetupException {
        ChangeSetService changeSetService = ChangeSetServiceFactory.getInstance().createChangeSetService();
        ModifyChangeSets modifyChangeSets = changeSetService.createModifyChangeSets(null, null);
        this.includeAll(pathName, isRelativeToChangelogFile, resourceFilter, errorIfMissingOrEmpty, resourceComparator, resourceAccessor, includeContextFilter, labels, ignore, minDepth, maxDepth, "", modifyChangeSets);
    }

    @Deprecated
    public void includeAll(String pathName, boolean isRelativeToChangelogFile, IncludeAllFilter resourceFilter, boolean errorIfMissingOrEmpty, Comparator<String> resourceComparator, ResourceAccessor resourceAccessor, ContextExpression includeContextFilter, Labels labels, boolean ignore, int minDepth, int maxDepth, ModifyChangeSets modifyChangeSets) throws SetupException {
        this.includeAll(pathName, isRelativeToChangelogFile, resourceFilter, errorIfMissingOrEmpty, resourceComparator, resourceAccessor, includeContextFilter, labels, ignore, minDepth, maxDepth, "", modifyChangeSets);
    }

    public void includeAll(String pathName, boolean isRelativeToChangelogFile, IncludeAllFilter resourceFilter, boolean errorIfMissingOrEmpty, Comparator<String> resourceComparator, ResourceAccessor resourceAccessor, ContextExpression includeContextFilter, Labels labels, boolean ignore, int minDepth, int maxDepth, String endsWithFilter, ModifyChangeSets modifyChangeSets) throws SetupException {
        if (pathName == null) {
            throw new SetupException("No path attribute for includeAll");
        }
        SortedSet<Resource> resources = this.findResources(pathName, isRelativeToChangelogFile, resourceFilter, errorIfMissingOrEmpty, resourceComparator, resourceAccessor, minDepth, maxDepth, endsWithFilter);
        if (resources.isEmpty() && errorIfMissingOrEmpty) {
            throw new SetupException("Could not find directory or directory was empty for includeAll '" + pathName + "'");
        }
        try {
            Set seenChangelogPaths = Scope.getCurrentScope().get(SEEN_CHANGELOGS_PATHS_SCOPE_KEY, new HashSet());
            Scope.child(Collections.singletonMap(SEEN_CHANGELOGS_PATHS_SCOPE_KEY, seenChangelogPaths), () -> {
                for (Resource resource : resources) {
                    Scope.getCurrentScope().getLog(this.getClass()).info("Reading resource: " + resource);
                    this.include(resource.getPath(), false, errorIfMissingOrEmpty, resourceAccessor, includeContextFilter, labels, ignore, OnUnknownFileFormat.WARN, modifyChangeSets);
                }
            });
        }
        catch (Exception e) {
            throw new SetupException(e);
        }
    }

    @Deprecated
    public SortedSet<Resource> findResources(String pathName, boolean isRelativeToChangelogFile, IncludeAllFilter resourceFilter, boolean errorIfMissingOrEmpty, Comparator<String> resourceComparator, ResourceAccessor resourceAccessor, int minDepth, int maxDepth) throws SetupException {
        return this.findResources(pathName, isRelativeToChangelogFile, resourceFilter, errorIfMissingOrEmpty, resourceComparator, resourceAccessor, minDepth, maxDepth, "");
    }

    public SortedSet<Resource> findResources(String pathName, boolean isRelativeToChangelogFile, IncludeAllFilter resourceFilter, boolean errorIfMissingOrEmpty, Comparator<String> resourceComparator, ResourceAccessor resourceAccessor, int minDepth, int maxDepth, String endsWithFilter) throws SetupException {
        try {
            List<Resource> unsortedResources;
            block10: {
                if (pathName == null) {
                    throw new SetupException("No path attribute for findResources");
                }
                String relativeTo = null;
                if (isRelativeToChangelogFile) {
                    relativeTo = this.getPhysicalFilePath();
                }
                ResourceAccessor.SearchOptions searchOptions = this.initializeAndSetMinAndMaxDepth(minDepth, maxDepth);
                searchOptions.setTrimmedEndsWithFilter(endsWithFilter);
                unsortedResources = null;
                Set seenChangelogPaths = Scope.getCurrentScope().get(SEEN_CHANGELOGS_PATHS_SCOPE_KEY, new HashSet());
                try {
                    String path = this.fixPath(pathName, resourceAccessor, relativeTo);
                    if (Boolean.TRUE.equals(ChangeLogParserConfiguration.ERROR_ON_CIRCULAR_INCLUDE_ALL.getCurrentValue()) && seenChangelogPaths.contains(path)) {
                        throw new SetupException("Circular reference detected in '" + path + "'. Set " + ChangeLogParserConfiguration.ERROR_ON_CIRCULAR_INCLUDE_ALL.getKey() + " if you'd like to ignore this error.");
                    }
                    seenChangelogPaths.add(path);
                    LOG.fine("includeAll for " + pathName);
                    LOG.fine("Using file opener for includeAll: " + resourceAccessor.toString());
                    unsortedResources = resourceAccessor.search(path, searchOptions);
                }
                catch (IOException e) {
                    if (!errorIfMissingOrEmpty) break block10;
                    throw new IOException(String.format("Could not find/read changelogs from %s directory", pathName));
                }
            }
            TreeSet<Resource> resources = new TreeSet<Resource>((o1, o2) -> resourceComparator.compare(o1.getPath(), o2.getPath()));
            if (unsortedResources != null) {
                for (Resource resourcePath : unsortedResources) {
                    if (resourceFilter != null && !resourceFilter.include(resourcePath.getPath())) continue;
                    resources.add(resourcePath);
                }
            }
            if (resources.isEmpty() && errorIfMissingOrEmpty) {
                throw new SetupException("Could not find directory, directory was empty, or no changelogs matched the provided search criteria for includeAll '" + pathName + "'");
            }
            return resources;
        }
        catch (IOException e) {
            throw new SetupException(e);
        }
    }

    private String fixPath(String pathName, ResourceAccessor resourceAccessor, String relativeTo) throws IOException {
        String path;
        if (relativeTo == null) {
            path = pathName;
        } else {
            path = resourceAccessor.get(relativeTo).resolveSibling(pathName).getPath();
            path = DatabaseChangeLog.normalizePath(path);
        }
        if (path != null && StringUtil.isNotEmpty(path = path.replace("\\", "/")) && !path.endsWith("/")) {
            path = path + '/';
        }
        return path;
    }

    @Deprecated
    public boolean include(String fileName, boolean isRelativePath, boolean errorIfMissing, ResourceAccessor resourceAccessor, ContextExpression includeContextFilter, LabelExpression labelExpression, Boolean ignore, boolean logEveryUnknownFileFormat) throws LiquibaseException {
        Labels labels = null;
        if (labelExpression != null && !labelExpression.isEmpty()) {
            labels = new Labels(labelExpression.getLabels());
        }
        return this.include(fileName, isRelativePath, errorIfMissing, resourceAccessor, includeContextFilter, labels, ignore, logEveryUnknownFileFormat);
    }

    public boolean include(String fileName, boolean isRelativePath, boolean errorIfMissing, ResourceAccessor resourceAccessor, ContextExpression includeContextFilter, Labels labels, Boolean ignore, boolean logEveryUnknownFileFormat) throws LiquibaseException {
        return this.include(fileName, isRelativePath, errorIfMissing, resourceAccessor, includeContextFilter, labels, ignore, logEveryUnknownFileFormat ? OnUnknownFileFormat.WARN : OnUnknownFileFormat.SKIP);
    }

    public boolean include(String fileName, boolean isRelativePath, boolean errorIfMissing, ResourceAccessor resourceAccessor, ContextExpression includeContextFilter, Labels labels, Boolean ignore, OnUnknownFileFormat onUnknownFileFormat) throws LiquibaseException {
        return this.include(fileName, isRelativePath, errorIfMissing, resourceAccessor, includeContextFilter, labels, ignore, onUnknownFileFormat, new ModifyChangeSets(null, null));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean include(String fileName, boolean isRelativePath, boolean errorIfMissing, ResourceAccessor resourceAccessor, ContextExpression includeContextFilter, Labels labels, Boolean ignore, OnUnknownFileFormat onUnknownFileFormat, ModifyChangeSets modifyChangeSets) throws LiquibaseException {
        DatabaseChangeLog changeLog;
        if (".svn".equalsIgnoreCase(fileName)) return false;
        if ("cvs".equalsIgnoreCase(fileName)) {
            return false;
        }
        if (isRelativePath) {
            try {
                fileName = resourceAccessor.get(this.getPhysicalFilePath()).resolveSibling(fileName).getPath();
                fileName = DatabaseChangeLog.normalizePath(Paths.get(fileName, new String[0]).toString());
            }
            catch (IOException e) {
                throw new UnexpectedLiquibaseException(e);
            }
        }
        String normalizedFilePath = fileName;
        try {
            DatabaseChangeLog rootChangeLog = ROOT_CHANGE_LOG.get();
            if (rootChangeLog == null) {
                ROOT_CHANGE_LOG.set(this);
            }
            DatabaseChangeLog parentChangeLog = PARENT_CHANGE_LOG.get();
            PARENT_CHANGE_LOG.set(this);
            try {
                if (!resourceAccessor.get(normalizedFilePath).exists()) {
                    if (!ChangeLogParserConfiguration.ON_MISSING_INCLUDE_CHANGELOG.getCurrentValue().equals((Object)ChangeLogParserConfiguration.MissingIncludeConfiguration.WARN)) {
                        if (errorIfMissing) throw new ChangeLogParseException(FileUtil.getFileNotFoundMessage(normalizedFilePath));
                    }
                    Scope.getCurrentScope().getLog(this.getClass()).warning(FileUtil.getFileNotFoundMessage(normalizedFilePath));
                    boolean bl = false;
                    return bl;
                }
                ChangeLogParser parser = ChangeLogParserFactory.getInstance().getParser(normalizedFilePath, resourceAccessor);
                changeLog = modifyChangeSets != null ? Scope.child(Collections.singletonMap(MODIFY_CHANGE_SETS, true), () -> parser.parse(normalizedFilePath, this.changeLogParameters, resourceAccessor)) : parser.parse(normalizedFilePath, this.changeLogParameters, resourceAccessor);
                changeLog.setIncludeContextFilter(includeContextFilter);
                changeLog.setIncludeLabels(labels);
                changeLog.setIncludeIgnore(ignore != null && ignore != false);
            }
            finally {
                if (rootChangeLog == null) {
                    ROOT_CHANGE_LOG.remove();
                }
                if (parentChangeLog == null) {
                    PARENT_CHANGE_LOG.remove();
                } else {
                    PARENT_CHANGE_LOG.set(parentChangeLog);
                }
            }
        }
        catch (UnknownChangelogFormatException e) {
            if (onUnknownFileFormat == OnUnknownFileFormat.FAIL) {
                throw e;
            }
            boolean matchesFileExtension = StringUtil.trimToEmpty(normalizedFilePath).matches("\\.\\w+$");
            if (!matchesFileExtension) {
                if (onUnknownFileFormat != OnUnknownFileFormat.WARN) return false;
            }
            Scope.getCurrentScope().getLog(this.getClass()).warning("included file " + normalizedFilePath + "/" + normalizedFilePath + " is not a recognized file type");
            return false;
        }
        catch (Exception e) {
            throw new LiquibaseException(e.getMessage(), e);
        }
        PreconditionContainer preconditions = changeLog.getPreconditions();
        if (preconditions != null) {
            if (null == this.getPreconditions()) {
                this.setPreconditions(new PreconditionContainer());
            }
            this.getPreconditions().addNestedPrecondition(preconditions);
        }
        Iterator<ChangeSet> iterator2 = changeLog.getChangeSets().iterator();
        while (true) {
            if (!iterator2.hasNext()) {
                this.skippedChangeSets.addAll(changeLog.getSkippedChangeSets());
                return true;
            }
            ChangeSet changeSet = iterator2.next();
            if (modifyChangeSets != null) {
                this.modifyChangeSets(modifyChangeSets, changeSet);
            }
            this.addChangeSet(changeSet);
        }
    }

    private void modifyChangeSets(ModifyChangeSets modifyChangeSets, ChangeSet changeSet) {
        ChangeSetServiceFactory factory2 = ChangeSetServiceFactory.getInstance();
        ChangeSetService service2 = factory2.createChangeSetService();
        service2.modifyChangeSets(changeSet, modifyChangeSets);
    }

    protected ChangeSet createChangeSet(ParsedNode node, ResourceAccessor resourceAccessor) throws ParsedNodeException {
        ChangeSetServiceFactory factory2 = ChangeSetServiceFactory.getInstance();
        ChangeSetService service2 = factory2.createChangeSetService();
        ChangeSet changeSet = service2.createChangeSet(this);
        changeSet.setChangeLogParameters(this.getChangeLogParameters());
        changeSet.load(node, resourceAccessor);
        return changeSet;
    }

    protected Comparator<String> getStandardChangeLogComparator() {
        return Comparator.comparing(o -> o.replace("WEB-INF/classes/", ""));
    }

    public static String normalizePath(String filePath) {
        String normalized;
        if (filePath == null) {
            return null;
        }
        if (filePath.startsWith("classpath:")) {
            filePath = filePath.substring("classpath:".length());
        }
        if (filePath.contains("\\")) {
            filePath = filePath.replace("\\", "/");
        }
        if (filePath.indexOf(":") == 1) {
            filePath = NO_LETTER_PATTERN.matcher(filePath).replaceFirst("");
        }
        if (filePath.startsWith("/")) {
            filePath = filePath.substring(1);
        }
        if ((normalized = FilenameUtils.normalizeNoEndSeparator(filePath)) == null) {
            normalized = Paths.get(filePath, new String[0]).normalize().toString();
        }
        if ((filePath = normalized).contains("\\")) {
            filePath = filePath.replace("\\", "/");
        }
        return filePath;
    }

    public void clearCheckSums() {
        for (ChangeSet changeSet : this.getChangeSets()) {
            changeSet.clearCheckSum();
        }
    }

    private ResourceAccessor.SearchOptions initializeAndSetMinAndMaxDepth(int minDepth, int maxDepth) throws SetupException {
        ResourceAccessor.SearchOptions searchOptions = new ResourceAccessor.SearchOptions();
        try {
            if (maxDepth < minDepth) {
                throw new IllegalArgumentException("maxDepth argument must be greater than minDepth");
            }
            searchOptions.setMinDepth(minDepth);
            searchOptions.setMaxDepth(maxDepth);
        }
        catch (IllegalArgumentException e) {
            throw new SetupException("Error in includeAll setup: " + e.getMessage(), e);
        }
        return searchOptions;
    }

    @LiquibaseService(skip=true)
    private static class GlobalPreconditionContainer
    extends PreconditionContainer {
        private GlobalPreconditionContainer() {
        }

        @Override
        public PreconditionContainer.OnSqlOutputOption getOnSqlOutput() {
            return PreconditionContainer.OnSqlOutputOption.TEST;
        }

        @Override
        public void addNestedPrecondition(Precondition precondition) {
            super.addNestedPrecondition(precondition);
        }
    }

    public static enum OnUnknownFileFormat {
        SKIP,
        WARN,
        FAIL;

    }
}

