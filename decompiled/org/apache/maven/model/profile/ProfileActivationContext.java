/*
 * Decompiled with CFR 0.152.
 */
package org.apache.maven.model.profile;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface ProfileActivationContext {
    public List<String> getActiveProfileIds();

    public List<String> getInactiveProfileIds();

    public Map<String, String> getSystemProperties();

    public Map<String, String> getUserProperties();

    public File getProjectDirectory();

    public Map<String, String> getProjectProperties();
}

