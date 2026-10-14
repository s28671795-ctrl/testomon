/*
 * Decompiled with CFR 0.152.
 */
package liquibase.servicelocator;

import java.util.List;
import liquibase.exception.ServiceNotFoundException;
import liquibase.plugin.Plugin;

public interface ServiceLocator
extends Plugin {
    public int getPriority();

    public <T> List<T> findInstances(Class<T> var1) throws ServiceNotFoundException;
}

