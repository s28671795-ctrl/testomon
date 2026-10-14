/*
 * Decompiled with CFR 0.152.
 */
package org.jvnet.hk2.internal;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Set;
import javax.inject.Named;
import javax.inject.Singleton;
import org.glassfish.hk2.api.ClassAnalyzer;
import org.glassfish.hk2.api.DescriptorVisibility;
import org.glassfish.hk2.api.MultiException;
import org.glassfish.hk2.api.Visibility;
import org.jvnet.hk2.internal.Collector;
import org.jvnet.hk2.internal.ServiceLocatorImpl;
import org.jvnet.hk2.internal.Utilities;

@Singleton
@Named(value="default")
@Visibility(value=DescriptorVisibility.LOCAL)
public class DefaultClassAnalyzer
implements ClassAnalyzer {
    private final ServiceLocatorImpl locator;

    public DefaultClassAnalyzer(ServiceLocatorImpl locator) {
        this.locator = locator;
    }

    @Override
    public <T> Constructor<T> getConstructor(Class<T> clazz) throws MultiException, NoSuchMethodException {
        Collector collector = new Collector();
        Constructor<?> retVal = Utilities.findProducerConstructor(clazz, this.locator, collector);
        try {
            collector.throwIfErrors();
        }
        catch (MultiException me) {
            for (Throwable th : me.getErrors()) {
                if (!(th instanceof NoSuchMethodException)) continue;
                throw (NoSuchMethodException)th;
            }
            throw me;
        }
        return retVal;
    }

    @Override
    public <T> Set<Method> getInitializerMethods(Class<T> clazz) throws MultiException {
        Collector collector = new Collector();
        Set<Method> retVal = Utilities.findInitializerMethods(clazz, this.locator, collector);
        collector.throwIfErrors();
        return retVal;
    }

    @Override
    public <T> Set<Field> getFields(Class<T> clazz) throws MultiException {
        Collector collector = new Collector();
        Set<Field> retVal = Utilities.findInitializerFields(clazz, this.locator, collector);
        collector.throwIfErrors();
        return retVal;
    }

    @Override
    public <T> Method getPostConstructMethod(Class<T> clazz) throws MultiException {
        Collector collector = new Collector();
        Method retVal = Utilities.findPostConstruct(clazz, this.locator, collector);
        collector.throwIfErrors();
        return retVal;
    }

    @Override
    public <T> Method getPreDestroyMethod(Class<T> clazz) throws MultiException {
        Collector collector = new Collector();
        Method retVal = Utilities.findPreDestroy(clazz, this.locator, collector);
        collector.throwIfErrors();
        return retVal;
    }
}

