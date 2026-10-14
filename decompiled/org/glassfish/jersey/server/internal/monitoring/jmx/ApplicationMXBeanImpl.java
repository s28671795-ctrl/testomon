/*
 * Decompiled with CFR 0.152.
 */
package org.glassfish.jersey.server.internal.monitoring.jmx;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.internal.LocalizationMessages;
import org.glassfish.jersey.server.internal.monitoring.jmx.MBeanExposer;
import org.glassfish.jersey.server.monitoring.ApplicationInfo;
import org.glassfish.jersey.server.monitoring.ApplicationMXBean;

public class ApplicationMXBeanImpl
implements ApplicationMXBean {
    private final String applicationName;
    private final String applicationClass;
    private final Map<String, String> configurationProperties;
    private final Date startTime;
    private final Set<String> providers = new HashSet<String>();
    private final Set<String> registeredClasses = new HashSet<String>();
    private final Set<String> registeredInstances = new HashSet<String>();

    public ApplicationMXBeanImpl(ApplicationInfo applicationInfo, MBeanExposer mBeanExposer, String parentName) {
        for (Class<?> provider : applicationInfo.getProviders()) {
            this.providers.add(provider.getName());
        }
        for (Class<?> registeredClass : applicationInfo.getRegisteredClasses()) {
            this.registeredClasses.add(registeredClass.toString());
        }
        for (Object registeredInstance : applicationInfo.getRegisteredInstances()) {
            this.registeredInstances.add(registeredInstance.getClass().getName());
        }
        ResourceConfig resourceConfig = applicationInfo.getResourceConfig();
        this.applicationName = resourceConfig.getApplicationName();
        this.applicationClass = resourceConfig.getApplication().getClass().getName();
        this.configurationProperties = new HashMap<String, String>();
        for (Map.Entry<String, Object> entry : resourceConfig.getProperties().entrySet()) {
            String stringValue;
            Object value = entry.getValue();
            try {
                stringValue = value == null ? "[null]" : value.toString();
            }
            catch (Exception e) {
                stringValue = LocalizationMessages.PROPERTY_VALUE_TOSTRING_THROWS_EXCEPTION(e.getClass().getName(), e.getMessage());
            }
            this.configurationProperties.put(entry.getKey(), stringValue);
        }
        this.startTime = new Date(applicationInfo.getStartTime().getTime());
        mBeanExposer.registerMBean(this, parentName + ",global=Configuration");
    }

    @Override
    public String getApplicationName() {
        return this.applicationName;
    }

    @Override
    public String getApplicationClass() {
        return this.applicationClass;
    }

    @Override
    public Map<String, String> getProperties() {
        return this.configurationProperties;
    }

    @Override
    public Date getStartTime() {
        return this.startTime;
    }

    @Override
    public Set<String> getRegisteredClasses() {
        return this.registeredClasses;
    }

    @Override
    public Set<String> getRegisteredInstances() {
        return this.registeredInstances;
    }

    @Override
    public Set<String> getProviderClasses() {
        return this.providers;
    }
}

