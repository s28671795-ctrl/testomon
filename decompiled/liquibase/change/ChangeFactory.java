/*
 * Decompiled with CFR 0.152.
 */
package liquibase.change;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import liquibase.ChecksumVersion;
import liquibase.Scope;
import liquibase.change.Change;
import liquibase.change.ChangeMetaData;
import liquibase.change.ChangeParameterMetaData;
import liquibase.database.Database;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.plugin.AbstractPluginFactory;

public class ChangeFactory
extends AbstractPluginFactory<Change> {
    private final Map<String, ChangeMetaData> cachedMetadata = new ConcurrentHashMap<String, ChangeMetaData>();
    private boolean performSupportsDatabaseValidation = true;

    private ChangeFactory() {
    }

    @Override
    protected Class<Change> getPluginClass() {
        return Change.class;
    }

    @Override
    protected int getPriority(Change obj, Object ... args2) {
        String commandName = (String)args2[0];
        ChangeMetaData changeMetaData = this.getChangeMetaData(obj);
        if (commandName.equals(changeMetaData.getName())) {
            return changeMetaData.getPriority();
        }
        return -1;
    }

    public ChangeMetaData getChangeMetaData(String change) {
        Change changeObj = this.create(change);
        if (changeObj == null) {
            return null;
        }
        return this.getChangeMetaData(changeObj);
    }

    public ChangeMetaData getChangeMetaData(Change change) {
        String cacheKey = this.generateCacheKey(change);
        this.cachedMetadata.computeIfAbsent(cacheKey, c -> change.createChangeMetaData());
        return this.cachedMetadata.get(cacheKey);
    }

    private String generateCacheKey(Change change) {
        String key;
        try {
            ChecksumVersion version = Scope.getCurrentScope().getChecksumVersion();
            if (version == null) {
                throw new NullPointerException();
            }
            key = change.getClass().getName() + (Object)((Object)version);
        }
        catch (Exception ignored) {
            key = change.getClass().getName();
        }
        return key;
    }

    public void unregister(String name) {
        for (Change change : new ArrayList(this.findAllInstances())) {
            if (!this.getChangeMetaData(change).getName().equals(name)) continue;
            this.removeInstance(change);
        }
    }

    public SortedSet<String> getDefinedChanges() {
        TreeSet<String> names = new TreeSet<String>();
        for (Change change : this.findAllInstances()) {
            names.add(this.getChangeMetaData(change).getName());
        }
        return Collections.unmodifiableSortedSet(names);
    }

    public Change create(String name) {
        Database database;
        Set plugins = this.getPlugins(name);
        if (plugins.isEmpty()) {
            return null;
        }
        if (plugins.size() > 1 && (database = Scope.getCurrentScope().getDatabase()) != null && this.performSupportsDatabaseValidation) {
            plugins.removeIf(a -> !a.supports(database));
            if (plugins.isEmpty()) {
                throw new UnexpectedLiquibaseException(String.format("No registered %s plugin found for %s database", name, database.getDisplayName()));
            }
        }
        try {
            return (Change)((Change)plugins.iterator().next()).getClass().getConstructor(new Class[0]).newInstance(new Object[0]);
        }
        catch (Exception e) {
            throw new UnexpectedLiquibaseException(e);
        }
    }

    public Map<String, Object> getParameters(Change change) {
        HashMap<String, Object> returnMap = new HashMap<String, Object>();
        ChangeMetaData changeMetaData = this.getChangeMetaData(change);
        for (ChangeParameterMetaData param2 : changeMetaData.getParameters().values()) {
            Object currentValue = param2.getCurrentValue(change);
            if (currentValue == null) continue;
            returnMap.put(param2.getParameterName(), currentValue);
        }
        return returnMap;
    }

    @Deprecated
    public static ChangeFactory getInstance() {
        return Scope.getCurrentScope().getSingleton(ChangeFactory.class);
    }

    public void setPerformSupportsDatabaseValidation(boolean performSupportsDatabaseValidation) {
        this.performSupportsDatabaseValidation = performSupportsDatabaseValidation;
    }
}

