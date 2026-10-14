/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.configuration;

import java.util.Collection;
import java.util.List;

public interface ConfiguredBeanLocator {
    public List<String> getBeanNamesOfType(Class<?> var1);

    public <T> T getBeanOfType(String var1, Class<T> var2);

    public <T> Collection<? extends T> getBeansOfType(Class<T> var1);

    public <T> boolean loadBeansOfType(Class<T> var1, BeanLoaderListener<T> var2);

    public boolean hasConfiguredPropertyValue(String var1, String var2, String var3);

    public boolean hasBeanOfName(String var1);

    public static interface BeanLoaderListener<T> {
        public boolean loadBean(String var1, Class<? extends T> var2);

        public boolean beanLoaded(String var1, T var2);
    }
}

