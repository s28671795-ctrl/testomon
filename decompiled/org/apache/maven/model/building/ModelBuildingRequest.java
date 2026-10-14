/*
 * Decompiled with CFR 0.152.
 */
package org.apache.maven.model.building;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import org.apache.maven.model.Model;
import org.apache.maven.model.Profile;
import org.apache.maven.model.building.ModelBuildingListener;
import org.apache.maven.model.building.ModelCache;
import org.apache.maven.model.building.ModelSource;
import org.apache.maven.model.resolution.ModelResolver;
import org.apache.maven.model.resolution.WorkspaceModelResolver;

public interface ModelBuildingRequest {
    public static final int VALIDATION_LEVEL_MINIMAL = 0;
    public static final int VALIDATION_LEVEL_MAVEN_2_0 = 20;
    public static final int VALIDATION_LEVEL_MAVEN_3_0 = 30;
    public static final int VALIDATION_LEVEL_MAVEN_3_1 = 31;
    public static final int VALIDATION_LEVEL_STRICT = 30;

    public Model getRawModel();

    public ModelBuildingRequest setRawModel(Model var1);

    public ModelSource getModelSource();

    public ModelBuildingRequest setModelSource(ModelSource var1);

    public File getPomFile();

    public ModelBuildingRequest setPomFile(File var1);

    public int getValidationLevel();

    public ModelBuildingRequest setValidationLevel(int var1);

    public boolean isProcessPlugins();

    public ModelBuildingRequest setProcessPlugins(boolean var1);

    public boolean isTwoPhaseBuilding();

    public ModelBuildingRequest setTwoPhaseBuilding(boolean var1);

    public boolean isLocationTracking();

    public ModelBuildingRequest setLocationTracking(boolean var1);

    public List<Profile> getProfiles();

    public ModelBuildingRequest setProfiles(List<Profile> var1);

    public List<String> getActiveProfileIds();

    public ModelBuildingRequest setActiveProfileIds(List<String> var1);

    public List<String> getInactiveProfileIds();

    public ModelBuildingRequest setInactiveProfileIds(List<String> var1);

    public Properties getSystemProperties();

    public ModelBuildingRequest setSystemProperties(Properties var1);

    public Properties getUserProperties();

    public ModelBuildingRequest setUserProperties(Properties var1);

    public Date getBuildStartTime();

    public ModelBuildingRequest setBuildStartTime(Date var1);

    public ModelResolver getModelResolver();

    public ModelBuildingRequest setModelResolver(ModelResolver var1);

    public ModelBuildingListener getModelBuildingListener();

    public ModelBuildingRequest setModelBuildingListener(ModelBuildingListener var1);

    public ModelCache getModelCache();

    public ModelBuildingRequest setModelCache(ModelCache var1);

    public WorkspaceModelResolver getWorkspaceModelResolver();

    public ModelBuildingRequest setWorkspaceModelResolver(WorkspaceModelResolver var1);
}

