/*
 * Decompiled with CFR 0.152.
 */
package liquibase.changelog;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import liquibase.Scope;
import liquibase.changelog.ChangeLogHistoryService;
import liquibase.database.Database;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.plugin.AbstractPluginFactory;

public class ChangeLogHistoryServiceFactory
extends AbstractPluginFactory<ChangeLogHistoryService> {
    private final List<ChangeLogHistoryService> explicitRegistered = new ArrayList<ChangeLogHistoryService>();
    private final Map<Database, ChangeLogHistoryService> services = new ConcurrentHashMap<Database, ChangeLogHistoryService>();

    @Deprecated
    public static synchronized ChangeLogHistoryServiceFactory getInstance() {
        return Scope.getCurrentScope().getSingleton(ChangeLogHistoryServiceFactory.class);
    }

    private ChangeLogHistoryServiceFactory() {
    }

    @Override
    protected Class<ChangeLogHistoryService> getPluginClass() {
        return ChangeLogHistoryService.class;
    }

    @Override
    protected int getPriority(ChangeLogHistoryService changeLogHistoryService, Object ... args2) {
        Database database = (Database)args2[0];
        if (changeLogHistoryService.supports(database)) {
            return changeLogHistoryService.getPriority();
        }
        return -1;
    }

    @Override
    public synchronized void register(ChangeLogHistoryService plugin) {
        super.register(plugin);
        this.explicitRegistered.add(plugin);
    }

    public synchronized ChangeLogHistoryService getChangeLogService(Database database) {
        if (this.services.containsKey(database)) {
            return this.services.get(database);
        }
        ChangeLogHistoryService plugin = (ChangeLogHistoryService)this.getPlugin(database);
        if (plugin == null) {
            throw new UnexpectedLiquibaseException("Cannot find ChangeLogHistoryService for " + database.getShortName());
        }
        try {
            ChangeLogHistoryService service2;
            Class<?> aClass = plugin.getClass();
            try {
                aClass.getConstructor(new Class[0]);
                service2 = (ChangeLogHistoryService)aClass.getConstructor(new Class[0]).newInstance(new Object[0]);
                service2.setDatabase(database);
            }
            catch (NoSuchMethodException e) {
                service2 = plugin;
            }
            this.services.put(database, service2);
            return service2;
        }
        catch (Exception e) {
            throw new UnexpectedLiquibaseException(e);
        }
    }

    public synchronized void unregister(ChangeLogHistoryService service2) {
        this.removeInstance(service2);
    }

    public synchronized void resetAll() {
        for (ChangeLogHistoryService changeLogHistoryService : this.findAllInstances()) {
            changeLogHistoryService.reset();
        }
        this.services.clear();
        this.explicitRegistered.forEach(x$0 -> this.removeInstance(x$0));
        this.explicitRegistered.clear();
    }
}

