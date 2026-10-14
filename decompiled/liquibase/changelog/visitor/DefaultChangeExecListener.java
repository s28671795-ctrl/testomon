/*
 * Decompiled with CFR 0.152.
 */
package liquibase.changelog.visitor;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import liquibase.change.Change;
import liquibase.changelog.ChangeSet;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.changelog.visitor.ChangeExecListener;
import liquibase.changelog.visitor.ChangeLogSyncListener;
import liquibase.database.Database;
import liquibase.exception.PreconditionErrorException;
import liquibase.exception.PreconditionFailedException;
import liquibase.precondition.core.PreconditionContainer;

public class DefaultChangeExecListener
implements ChangeExecListener,
ChangeLogSyncListener {
    private final List<ChangeExecListener> listeners;
    private final List<ChangeSet> deployedChangeSets = new LinkedList<ChangeSet>();
    private final List<ChangeSet> rolledBackChangeSets = new LinkedList<ChangeSet>();
    private final List<ChangeSet> failedChangeSets = new LinkedList<ChangeSet>();
    private final List<ChangeSet> failedRollbackChangeSets = new LinkedList<ChangeSet>();
    private final Map<ChangeSet, List<Change>> deployedChangesPerChangeSet = new ConcurrentHashMap<ChangeSet, List<Change>>();

    public DefaultChangeExecListener(ChangeExecListener ... listeners) {
        this.listeners = Arrays.stream(listeners).filter(Objects::nonNull).collect(Collectors.toList());
    }

    @Override
    public void willRun(ChangeSet changeSet, DatabaseChangeLog databaseChangeLog, Database database, ChangeSet.RunStatus runStatus) {
        this.listeners.forEach(listener -> listener.willRun(changeSet, databaseChangeLog, database, runStatus));
    }

    @Override
    public void ran(ChangeSet changeSet, DatabaseChangeLog databaseChangeLog, Database database, ChangeSet.ExecType execType) {
        this.deployedChangeSets.add(changeSet);
        this.listeners.forEach(listener -> listener.ran(changeSet, databaseChangeLog, database, execType));
    }

    @Override
    public void willRollback(ChangeSet changeSet, DatabaseChangeLog databaseChangeLog, Database database) {
        this.listeners.forEach(listener -> listener.willRollback(changeSet, databaseChangeLog, database));
    }

    @Override
    public void rolledBack(ChangeSet changeSet, DatabaseChangeLog databaseChangeLog, Database database) {
        this.rolledBackChangeSets.add(changeSet);
        this.listeners.forEach(listener -> listener.rolledBack(changeSet, databaseChangeLog, database));
    }

    @Override
    public void preconditionFailed(PreconditionFailedException error, PreconditionContainer.FailOption onFail) {
        this.listeners.forEach(listener -> listener.preconditionFailed(error, onFail));
    }

    @Override
    public void preconditionErrored(PreconditionErrorException error, PreconditionContainer.ErrorOption onError2) {
        this.listeners.forEach(listener -> listener.preconditionErrored(error, onError2));
    }

    @Override
    public void willRun(Change change, ChangeSet changeSet, DatabaseChangeLog changeLog, Database database) {
        this.listeners.forEach(listener -> listener.willRun(change, changeSet, changeLog, database));
    }

    @Override
    public void ran(Change change, ChangeSet changeSet, DatabaseChangeLog changeLog, Database database) {
        this.deployedChangesPerChangeSet.computeIfAbsent(changeSet, val -> new LinkedList()).add(change);
        this.listeners.forEach(listener -> listener.ran(change, changeSet, changeLog, database));
    }

    @Override
    public void runFailed(ChangeSet changeSet, DatabaseChangeLog databaseChangeLog, Database database, Exception exception) {
        this.failedChangeSets.add(changeSet);
        this.listeners.forEach(listener -> listener.runFailed(changeSet, databaseChangeLog, database, exception));
    }

    @Override
    public void rollbackFailed(ChangeSet changeSet, DatabaseChangeLog databaseChangeLog, Database database, Exception exception) {
        this.failedRollbackChangeSets.add(changeSet);
        this.listeners.forEach(listener -> listener.rollbackFailed(changeSet, databaseChangeLog, database, exception));
    }

    public List<Change> getDeployedChanges(ChangeSet changeSet) {
        LinkedList changesDeployed = this.deployedChangesPerChangeSet.get(changeSet);
        return changesDeployed != null ? changesDeployed : new LinkedList();
    }

    public void addListener(ChangeExecListener listener) {
        if (listener != null) {
            this.listeners.add(listener);
        }
    }

    @Override
    public void markedRan(ChangeSet changeSet, DatabaseChangeLog databaseChangeLog, Database database) {
    }

    public void reset() {
        this.deployedChangeSets.clear();
        this.failedChangeSets.clear();
        this.deployedChangesPerChangeSet.clear();
    }

    public List<ChangeExecListener> getListeners() {
        return this.listeners;
    }

    public List<ChangeSet> getDeployedChangeSets() {
        return this.deployedChangeSets;
    }

    public List<ChangeSet> getRolledBackChangeSets() {
        return this.rolledBackChangeSets;
    }

    public List<ChangeSet> getFailedChangeSets() {
        return this.failedChangeSets;
    }

    public List<ChangeSet> getFailedRollbackChangeSets() {
        return this.failedRollbackChangeSets;
    }
}

