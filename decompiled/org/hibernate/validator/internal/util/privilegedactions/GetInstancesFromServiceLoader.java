/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.util.privilegedactions;

import java.lang.invoke.MethodHandles;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;

public class GetInstancesFromServiceLoader<T>
implements PrivilegedAction<List<T>> {
    private final ClassLoader primaryClassLoader;
    private final Class<T> clazz;
    private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());

    private GetInstancesFromServiceLoader(ClassLoader primaryClassLoader, Class<T> clazz) {
        this.primaryClassLoader = primaryClassLoader;
        this.clazz = clazz;
    }

    public static <T> GetInstancesFromServiceLoader<T> action(ClassLoader primaryClassLoader, Class<T> serviceClass) {
        return new GetInstancesFromServiceLoader<T>(primaryClassLoader, serviceClass);
    }

    @Override
    public List<T> run() {
        List<T> instances = this.loadInstances(this.primaryClassLoader);
        if (instances.isEmpty() && GetInstancesFromServiceLoader.class.getClassLoader() != this.primaryClassLoader) {
            instances = this.loadInstances(GetInstancesFromServiceLoader.class.getClassLoader());
        }
        return instances;
    }

    private List<T> loadInstances(ClassLoader classloader) {
        ServiceLoader<T> loader = ServiceLoader.load(this.clazz, classloader);
        Iterator<T> iterator2 = loader.iterator();
        ArrayList<T> instances = new ArrayList<T>();
        while (iterator2.hasNext()) {
            try {
                instances.add(iterator2.next());
            }
            catch (ServiceConfigurationError e) {
                LOG.unableToLoadInstanceOfService(loader.getClass().getName(), e);
            }
        }
        return instances;
    }
}

