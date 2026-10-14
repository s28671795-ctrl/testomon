/*
 * Decompiled with CFR 0.152.
 */
package org.apache.maven.model.profile;

import java.io.File;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.apache.maven.model.profile.ProfileActivationContext;

public class DefaultProfileActivationContext
implements ProfileActivationContext {
    private List<String> activeProfileIds = Collections.emptyList();
    private List<String> inactiveProfileIds = Collections.emptyList();
    private Map<String, String> systemProperties = Collections.emptyMap();
    private Map<String, String> userProperties = Collections.emptyMap();
    private Map<String, String> projectProperties = Collections.emptyMap();
    private File projectDirectory;

    @Override
    public List<String> getActiveProfileIds() {
        return this.activeProfileIds;
    }

    public DefaultProfileActivationContext setActiveProfileIds(List<String> activeProfileIds) {
        this.activeProfileIds = activeProfileIds != null ? Collections.unmodifiableList(activeProfileIds) : Collections.emptyList();
        return this;
    }

    @Override
    public List<String> getInactiveProfileIds() {
        return this.inactiveProfileIds;
    }

    public DefaultProfileActivationContext setInactiveProfileIds(List<String> inactiveProfileIds) {
        this.inactiveProfileIds = inactiveProfileIds != null ? Collections.unmodifiableList(inactiveProfileIds) : Collections.emptyList();
        return this;
    }

    @Override
    public Map<String, String> getSystemProperties() {
        return this.systemProperties;
    }

    public DefaultProfileActivationContext setSystemProperties(Properties systemProperties) {
        this.systemProperties = systemProperties != null ? Collections.unmodifiableMap(systemProperties) : Collections.emptyMap();
        return this;
    }

    public DefaultProfileActivationContext setSystemProperties(Map<String, String> systemProperties) {
        this.systemProperties = systemProperties != null ? Collections.unmodifiableMap(systemProperties) : Collections.emptyMap();
        return this;
    }

    @Override
    public Map<String, String> getUserProperties() {
        return this.userProperties;
    }

    public DefaultProfileActivationContext setUserProperties(Properties userProperties) {
        this.userProperties = userProperties != null ? Collections.unmodifiableMap(userProperties) : Collections.emptyMap();
        return this;
    }

    public DefaultProfileActivationContext setUserProperties(Map<String, String> userProperties) {
        this.userProperties = userProperties != null ? Collections.unmodifiableMap(userProperties) : Collections.emptyMap();
        return this;
    }

    @Override
    public File getProjectDirectory() {
        return this.projectDirectory;
    }

    public DefaultProfileActivationContext setProjectDirectory(File projectDirectory) {
        this.projectDirectory = projectDirectory;
        return this;
    }

    @Override
    public Map<String, String> getProjectProperties() {
        return this.projectProperties;
    }

    public DefaultProfileActivationContext setProjectProperties(Properties projectProperties) {
        this.projectProperties = projectProperties != null ? Collections.unmodifiableMap(this.toMap(projectProperties)) : Collections.emptyMap();
        return this;
    }

    private Map<String, String> toMap(Properties properties) {
        if (properties == null) {
            return Collections.emptyMap();
        }
        HashMap<String, String> map = new HashMap<String, String>();
        Enumeration<Object> keys2 = properties.keys();
        while (keys2.hasMoreElements()) {
            String key = (String)keys2.nextElement();
            map.put(key, properties.getProperty(key));
        }
        return map;
    }
}

