/*
 * Decompiled with CFR 0.152.
 */
package liquibase.lockservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import liquibase.Scope;
import liquibase.database.Database;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.lockservice.LockService;

public class LockServiceFactory {
    private static LockServiceFactory instance;
    private final List<LockService> registry = new ArrayList<LockService>();
    private final Map<Database, LockService> openLockServices = new ConcurrentHashMap<Database, LockService>();

    public static synchronized LockServiceFactory getInstance() {
        if (instance == null) {
            instance = new LockServiceFactory();
        }
        return instance;
    }

    public static synchronized void setInstance(LockServiceFactory lockServiceFactory) {
        instance = lockServiceFactory;
    }

    public static synchronized void reset() {
        instance = null;
    }

    private LockServiceFactory() {
        try {
            for (LockService lockService : Scope.getCurrentScope().getServiceLocator().findInstances(LockService.class)) {
                this.register(lockService);
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void register(LockService lockService) {
        this.registry.add(0, lockService);
    }

    public LockService getLockService(Database database) {
        if (!this.openLockServices.containsKey(database)) {
            TreeSet<LockService> foundServices = new TreeSet<LockService>((o1, o2) -> -1 * Integer.compare(o1.getPriority(), o2.getPriority()));
            for (LockService lockService : this.registry) {
                if (!lockService.supports(database)) continue;
                foundServices.add(lockService);
            }
            if (foundServices.isEmpty()) {
                throw new UnexpectedLiquibaseException("Cannot find LockService for " + database.getShortName());
            }
            try {
                LockService lockService = (LockService)((LockService)foundServices.iterator().next()).getClass().getConstructor(new Class[0]).newInstance(new Object[0]);
                lockService.setDatabase(database);
                this.openLockServices.put(database, lockService);
            }
            catch (Exception e) {
                throw new UnexpectedLiquibaseException(e);
            }
        }
        return this.openLockServices.get(database);
    }

    public void resetAll() {
        for (LockService lockService : this.registry) {
            lockService.reset();
        }
        LockServiceFactory.reset();
    }
}

