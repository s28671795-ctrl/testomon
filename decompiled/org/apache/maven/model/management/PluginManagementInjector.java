/*
 * Decompiled with CFR 0.152.
 */
package org.apache.maven.model.management;

import org.apache.maven.model.Model;
import org.apache.maven.model.building.ModelBuildingRequest;
import org.apache.maven.model.building.ModelProblemCollector;

public interface PluginManagementInjector {
    public void injectManagement(Model var1, ModelBuildingRequest var2, ModelProblemCollector var3);
}

