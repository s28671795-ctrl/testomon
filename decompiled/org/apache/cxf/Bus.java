/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf;

import java.util.Collection;
import java.util.Map;
import org.apache.cxf.feature.Feature;
import org.apache.cxf.interceptor.InterceptorProvider;

public interface Bus
extends InterceptorProvider {
    public static final String DEFAULT_BUS_ID = "cxf";

    public <T> T getExtension(Class<T> var1);

    public <T> void setExtension(T var1, Class<T> var2);

    public boolean hasExtensionByName(String var1);

    public String getId();

    public void setId(String var1);

    public void shutdown(boolean var1);

    public void setProperty(String var1, Object var2);

    public Object getProperty(String var1);

    public void setProperties(Map<String, Object> var1);

    public Map<String, Object> getProperties();

    public Collection<Feature> getFeatures();

    public void setFeatures(Collection<? extends Feature> var1);

    public BusState getState();

    public static enum BusState {
        INITIAL,
        INITIALIZING,
        RUNNING,
        SHUTTING_DOWN,
        SHUTDOWN;

    }
}

