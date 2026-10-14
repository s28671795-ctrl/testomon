/*
 * Decompiled with CFR 0.152.
 */
package liquibase.changelog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import liquibase.ContextExpression;
import liquibase.GlobalConfiguration;
import liquibase.Labels;
import liquibase.RuntimeEnvironment;
import liquibase.Scope;
import liquibase.changelog.ChangeSet;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.changelog.RanChangeSet;
import liquibase.changelog.filter.ChangeSetFilter;
import liquibase.changelog.filter.ChangeSetFilterResult;
import liquibase.changelog.visitor.ChangeSetVisitor;
import liquibase.changelog.visitor.SkippedChangeSetVisitor;
import liquibase.changelog.visitor.ValidatingVisitor;
import liquibase.exception.LiquibaseException;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.exception.ValidationErrors;
import liquibase.executor.Executor;
import liquibase.executor.ExecutorService;
import liquibase.util.BooleanUtil;
import liquibase.util.StringUtil;

public class ChangeLogIterator {
    protected final DatabaseChangeLog databaseChangeLog;
    protected final List<ChangeSetFilter> changeSetFilters;
    private static final ResourceBundle coreBundle = ResourceBundle.getBundle("liquibase/i18n/liquibase-core");
    private static final String MSG_COULD_NOT_FIND_EXECUTOR = coreBundle.getString("no.executor.found");
    private final Set<String> seenChangeSets = new HashSet<String>();
    private final List<ChangeSet> exceptionChangeSets = new ArrayList<ChangeSet>();
    private final List<ChangeSet> skippedDueToExceptionChangeSets = new ArrayList<ChangeSet>();
    private final Boolean allowDuplicatedChangesetsIdentifiers = GlobalConfiguration.ALLOW_DUPLICATED_CHANGESETS_IDENTIFIERS.getCurrentValue();

    public ChangeLogIterator(DatabaseChangeLog databaseChangeLog, ChangeSetFilter ... changeSetFilters) {
        this(databaseChangeLog, Arrays.asList(changeSetFilters));
    }

    public ChangeLogIterator(DatabaseChangeLog databaseChangeLog, List<ChangeSetFilter> changeSetFilters) {
        this.databaseChangeLog = databaseChangeLog;
        this.changeSetFilters = changeSetFilters;
    }

    public ChangeLogIterator(List<RanChangeSet> changeSetList, DatabaseChangeLog changeLog, ChangeSetFilter ... changeSetFilters) {
        this(changeSetList, changeLog, Arrays.asList(changeSetFilters));
    }

    public ChangeLogIterator(List<RanChangeSet> changeSetList, DatabaseChangeLog changeLog, List<ChangeSetFilter> changeSetFilters) {
        final ArrayList<ChangeSet> changeSets = new ArrayList<ChangeSet>();
        for (RanChangeSet ranChangeSet : changeSetList) {
            List<ChangeSet> changeSetsForRanChangeSet = changeLog.getChangeSets(ranChangeSet);
            for (ChangeSet changeSet : changeSetsForRanChangeSet) {
                if (changeSet == null) continue;
                changeSet.setFilePath(DatabaseChangeLog.normalizePath(ranChangeSet.getChangeLog()));
                changeSet.setDeploymentId(ranChangeSet.getDeploymentId());
                changeSets.add(changeSet);
            }
        }
        this.databaseChangeLog = new DatabaseChangeLog(){

            @Override
            public List<ChangeSet> getChangeSets() {
                return changeSets;
            }

            @Override
            public String toString() {
                return "";
            }
        };
        this.changeSetFilters = changeSetFilters;
    }

    public void run(ChangeSetVisitor visitor, RuntimeEnvironment env) throws LiquibaseException {
        this.databaseChangeLog.setRuntimeEnvironment(env);
        try {
            Scope.child(Scope.Attr.databaseChangeLog, (Object)this.databaseChangeLog, () -> {
                ArrayList<ChangeSet> changeSetList = new ArrayList<ChangeSet>(this.databaseChangeLog.getChangeSets());
                if (visitor.getDirection().equals((Object)ChangeSetVisitor.Direction.REVERSE)) {
                    Collections.reverse(changeSetList);
                }
                int i = 0;
                while (i < changeSetList.size()) {
                    ChangeSet changeSet = (ChangeSet)changeSetList.get(i);
                    boolean shouldVisit = true;
                    HashSet<ChangeSetFilterResult> reasonsAccepted = new HashSet<ChangeSetFilterResult>();
                    HashSet<ChangeSetFilterResult> reasonsDenied = new HashSet<ChangeSetFilterResult>();
                    if (this.changeSetFilters != null) {
                        for (ChangeSetFilter filter2 : this.changeSetFilters) {
                            ChangeSetFilterResult acceptsResult = filter2.accepts(changeSet);
                            if (acceptsResult.isAccepted()) {
                                reasonsAccepted.add(acceptsResult);
                                continue;
                            }
                            shouldVisit = false;
                            reasonsDenied.add(acceptsResult);
                            break;
                        }
                    }
                    boolean finalShouldVisit = shouldVisit;
                    HashMap<String, Object> scopeValues = new HashMap<String, Object>();
                    scopeValues.put(Scope.Attr.changeSet.name(), changeSet);
                    scopeValues.put(Scope.Attr.database.name(), env.getTargetDatabase());
                    int finalI = i++;
                    Scope.child(scopeValues, () -> {
                        if (finalShouldVisit && !this.alreadySaw(changeSet)) {
                            if (visitor instanceof ValidatingVisitor) {
                                this.validateChangeSetExecutor(changeSet, env);
                            }
                            try {
                                visitor.visit(changeSet, this.databaseChangeLog, env.getTargetDatabase(), reasonsAccepted);
                            }
                            catch (Exception e) {
                                this.exceptionChangeSets.add(changeSet);
                                this.skippedDueToExceptionChangeSets.addAll(changeSetList.subList(finalI + 1, changeSetList.size()));
                                throw e;
                            }
                            this.markSeen(changeSet);
                        } else if (visitor instanceof SkippedChangeSetVisitor) {
                            ((SkippedChangeSetVisitor)((Object)visitor)).skipped(changeSet, this.databaseChangeLog, env.getTargetDatabase(), reasonsDenied);
                        }
                    });
                }
            });
        }
        catch (Exception e) {
            throw new LiquibaseException(e);
        }
        finally {
            this.databaseChangeLog.setRuntimeEnvironment(null);
        }
    }

    protected void validateChangeSetExecutor(ChangeSet changeSet, RuntimeEnvironment env) throws LiquibaseException {
        Executor executor2;
        if (changeSet.getRunWith() == null) {
            return;
        }
        String executorName = ChangeSet.lookupExecutor(changeSet.getRunWith());
        try {
            executor2 = Scope.getCurrentScope().getSingleton(ExecutorService.class).getExecutor(executorName, env.getTargetDatabase());
        }
        catch (UnexpectedLiquibaseException ule) {
            String message = String.format(MSG_COULD_NOT_FIND_EXECUTOR, executorName, changeSet);
            Scope.getCurrentScope().getLog(this.getClass()).severe(message);
            throw new LiquibaseException(message);
        }
        executor2.modifyChangeSet(changeSet);
        ValidationErrors errors2 = executor2.validate(changeSet);
        if (errors2.hasErrors()) {
            String message = errors2.toString();
            Scope.getCurrentScope().getLog(this.getClass()).severe(message);
            throw new LiquibaseException(message);
        }
    }

    protected void markSeen(ChangeSet changeSet) {
        if (changeSet.key == null) {
            changeSet.key = this.createKey(changeSet);
        }
        this.seenChangeSets.add(changeSet.key);
    }

    private boolean alreadySaw(ChangeSet changeSet) {
        if (BooleanUtil.isTrue(this.allowDuplicatedChangesetsIdentifiers)) {
            if (changeSet.key == null) {
                changeSet.key = this.createKey(changeSet);
            }
            return this.seenChangeSets.contains(changeSet.key);
        }
        return false;
    }

    protected String createKey(ChangeSet changeSet) {
        Labels labels = changeSet.getLabels();
        ContextExpression contexts = changeSet.getContextFilter();
        return changeSet.toString(false) + ":" + (labels == null ? null : labels.toString()) + ":" + (contexts == null ? null : contexts.toString()) + ":" + StringUtil.join(changeSet.getDbmsSet(), ",");
    }

    public List<ChangeSetFilter> getChangeSetFilters() {
        return Collections.unmodifiableList(this.changeSetFilters);
    }

    public List<ChangeSet> getExceptionChangeSets() {
        return this.exceptionChangeSets;
    }

    public List<ChangeSet> getSkippedDueToExceptionChangeSets() {
        return this.skippedDueToExceptionChangeSets;
    }
}

