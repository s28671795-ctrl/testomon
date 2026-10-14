/*
 * Decompiled with CFR 0.152.
 */
package org.apache.maven.model.building;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.apache.maven.model.Model;
import org.apache.maven.model.Profile;
import org.apache.maven.model.building.FileModelSource;
import org.apache.maven.model.building.ModelBuildingListener;
import org.apache.maven.model.building.ModelBuildingRequest;
import org.apache.maven.model.building.ModelCache;
import org.apache.maven.model.building.ModelSource;
import org.apache.maven.model.resolution.ModelResolver;
import org.apache.maven.model.resolution.WorkspaceModelResolver;

public class DefaultModelBuildingRequest
implements ModelBuildingRequest {
    private Model rawModel;
    private File pomFile;
    private ModelSource modelSource;
    private int validationLevel = 30;
    private boolean processPlugins;
    private boolean twoPhaseBuilding;
    private boolean locationTracking;
    private List<Profile> profiles;
    private List<String> activeProfileIds;
    private List<String> inactiveProfileIds;
    private Properties systemProperties;
    private Properties userProperties;
    private Date buildStartTime;
    private ModelResolver modelResolver;
    private ModelBuildingListener modelBuildingListener;
    private ModelCache modelCache;
    private WorkspaceModelResolver workspaceResolver;

    public DefaultModelBuildingRequest() {
    }

    public DefaultModelBuildingRequest(ModelBuildingRequest request) {
        this.setPomFile(request.getPomFile());
        this.setModelSource(request.getModelSource());
        this.setValidationLevel(request.getValidationLevel());
        this.setProcessPlugins(request.isProcessPlugins());
        this.setTwoPhaseBuilding(request.isTwoPhaseBuilding());
        this.setProfiles((List)request.getProfiles());
        this.setActiveProfileIds((List)request.getActiveProfileIds());
        this.setInactiveProfileIds((List)request.getInactiveProfileIds());
        this.setSystemProperties(request.getSystemProperties());
        this.setUserProperties(request.getUserProperties());
        this.setBuildStartTime(request.getBuildStartTime());
        this.setModelResolver(request.getModelResolver());
        this.setModelBuildingListener(request.getModelBuildingListener());
        this.setModelCache(request.getModelCache());
    }

    @Override
    public File getPomFile() {
        return this.pomFile;
    }

    @Override
    public DefaultModelBuildingRequest setPomFile(File pomFile) {
        this.pomFile = pomFile != null ? pomFile.getAbsoluteFile() : null;
        return this;
    }

    @Override
    public synchronized ModelSource getModelSource() {
        if (this.modelSource == null && this.pomFile != null) {
            this.modelSource = new FileModelSource(this.pomFile);
        }
        return this.modelSource;
    }

    @Override
    public DefaultModelBuildingRequest setModelSource(ModelSource modelSource) {
        this.modelSource = modelSource;
        return this;
    }

    @Override
    public int getValidationLevel() {
        return this.validationLevel;
    }

    @Override
    public DefaultModelBuildingRequest setValidationLevel(int validationLevel) {
        this.validationLevel = validationLevel;
        return this;
    }

    @Override
    public boolean isProcessPlugins() {
        return this.processPlugins;
    }

    @Override
    public DefaultModelBuildingRequest setProcessPlugins(boolean processPlugins) {
        this.processPlugins = processPlugins;
        return this;
    }

    @Override
    public boolean isTwoPhaseBuilding() {
        return this.twoPhaseBuilding;
    }

    @Override
    public DefaultModelBuildingRequest setTwoPhaseBuilding(boolean twoPhaseBuilding) {
        this.twoPhaseBuilding = twoPhaseBuilding;
        return this;
    }

    @Override
    public boolean isLocationTracking() {
        return this.locationTracking;
    }

    @Override
    public DefaultModelBuildingRequest setLocationTracking(boolean locationTracking) {
        this.locationTracking = locationTracking;
        return this;
    }

    @Override
    public List<Profile> getProfiles() {
        if (this.profiles == null) {
            this.profiles = new ArrayList<Profile>();
        }
        return this.profiles;
    }

    @Override
    public DefaultModelBuildingRequest setProfiles(List<Profile> profiles) {
        this.profiles = profiles != null ? new ArrayList<Profile>(profiles) : null;
        return this;
    }

    @Override
    public List<String> getActiveProfileIds() {
        if (this.activeProfileIds == null) {
            this.activeProfileIds = new ArrayList<String>();
        }
        return this.activeProfileIds;
    }

    @Override
    public DefaultModelBuildingRequest setActiveProfileIds(List<String> activeProfileIds) {
        this.activeProfileIds = activeProfileIds != null ? new ArrayList<String>(activeProfileIds) : null;
        return this;
    }

    @Override
    public List<String> getInactiveProfileIds() {
        if (this.inactiveProfileIds == null) {
            this.inactiveProfileIds = new ArrayList<String>();
        }
        return this.inactiveProfileIds;
    }

    @Override
    public DefaultModelBuildingRequest setInactiveProfileIds(List<String> inactiveProfileIds) {
        this.inactiveProfileIds = inactiveProfileIds != null ? new ArrayList<String>(inactiveProfileIds) : null;
        return this;
    }

    @Override
    public Properties getSystemProperties() {
        if (this.systemProperties == null) {
            this.systemProperties = new Properties();
        }
        return this.systemProperties;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public DefaultModelBuildingRequest setSystemProperties(Properties systemProperties) {
        if (systemProperties != null) {
            this.systemProperties = new Properties();
            Properties properties = systemProperties;
            synchronized (properties) {
                this.systemProperties.putAll((Map<?, ?>)systemProperties);
            }
        } else {
            this.systemProperties = null;
        }
        return this;
    }

    @Override
    public Properties getUserProperties() {
        if (this.userProperties == null) {
            this.userProperties = new Properties();
        }
        return this.userProperties;
    }

    @Override
    public DefaultModelBuildingRequest setUserProperties(Properties userProperties) {
        if (userProperties != null) {
            this.userProperties = new Properties();
            this.userProperties.putAll((Map<?, ?>)userProperties);
        } else {
            this.userProperties = null;
        }
        return this;
    }

    @Override
    public Date getBuildStartTime() {
        return this.buildStartTime;
    }

    @Override
    public ModelBuildingRequest setBuildStartTime(Date buildStartTime) {
        this.buildStartTime = buildStartTime;
        return this;
    }

    @Override
    public ModelResolver getModelResolver() {
        return this.modelResolver;
    }

    @Override
    public DefaultModelBuildingRequest setModelResolver(ModelResolver modelResolver) {
        this.modelResolver = modelResolver;
        return this;
    }

    @Override
    public ModelBuildingListener getModelBuildingListener() {
        return this.modelBuildingListener;
    }

    @Override
    public ModelBuildingRequest setModelBuildingListener(ModelBuildingListener modelBuildingListener) {
        this.modelBuildingListener = modelBuildingListener;
        return this;
    }

    @Override
    public ModelCache getModelCache() {
        return this.modelCache;
    }

    @Override
    public DefaultModelBuildingRequest setModelCache(ModelCache modelCache) {
        this.modelCache = modelCache;
        return this;
    }

    @Override
    public Model getRawModel() {
        return this.rawModel;
    }

    @Override
    public ModelBuildingRequest setRawModel(Model rawModel) {
        this.rawModel = rawModel;
        return this;
    }

    @Override
    public WorkspaceModelResolver getWorkspaceModelResolver() {
        return this.workspaceResolver;
    }

    @Override
    public ModelBuildingRequest setWorkspaceModelResolver(WorkspaceModelResolver workspaceResolver) {
        this.workspaceResolver = workspaceResolver;
        return this;
    }
}

