/*
 * Decompiled with CFR 0.152.
 */
package liquibase.servicelocator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;
import liquibase.Scope;
import liquibase.exception.ServiceNotFoundException;
import liquibase.logging.Logger;
import liquibase.servicelocator.ServiceLocator;

public class StandardServiceLocator
implements ServiceLocator {
    @Override
    public int getPriority() {
        return 1;
    }

    @Override
    public <T> List<T> findInstances(Class<T> interfaceType) throws ServiceNotFoundException {
        ArrayList<T> allInstances = new ArrayList<T>();
        Logger log = Scope.getCurrentScope().getLog(this.getClass());
        Iterator<T> services = ServiceLoader.load(interfaceType, Scope.getCurrentScope().getClassLoader(true)).iterator();
        while (services.hasNext()) {
            try {
                T service2 = services.next();
                log.fine("Loaded " + interfaceType.getName() + " instance " + service2.getClass().getName());
                allInstances.add(service2);
            }
            catch (Throwable e) {
                new ServiceLoadExceptionHandler().handleException(e);
                log.fine(e.getMessage(), e);
            }
        }
        return Collections.unmodifiableList(allInstances);
    }

    static class ServiceLoadExceptionHandler {
        ServiceLoadExceptionHandler() {
        }

        void handleException(Throwable e) {
            Logger log = Scope.getCurrentScope().getLog(this.getClass());
            log.info("Cannot load service", e);
        }
    }
}

