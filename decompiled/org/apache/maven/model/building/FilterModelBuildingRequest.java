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
import org.apache.maven.model.building.ModelBuildingRequest;
import org.apache.maven.model.building.ModelCache;
import org.apache.maven.model.building.ModelSource;
import org.apache.maven.model.resolution.ModelResolver;
import org.apache.maven.model.resolution.WorkspaceModelResolver;

class FilterModelBuildingRequest
implements ModelBuildingRequest {
    protected ModelBuildingRequest request;

    FilterModelBuildingRequest(ModelBuildingRequest request) {
        this.request = request;
    }

    @Override
    public File getPomFile() {
        return this.request.getPomFile();
    }

    @Override
    public FilterModelBuildingRequest setPomFile(File pomFile) {
        this.request.setPomFile(pomFile);
        return this;
    }

    @Override
    public ModelSource getModelSource() {
        return this.request.getModelSource();
    }

    @Override
    public FilterModelBuildingRequest setModelSource(ModelSource modelSource) {
        this.request.setModelSource(modelSource);
        return this;
    }

    @Override
    public int getValidationLevel() {
        return this.request.getValidationLevel();
    }

    @Override
    public FilterModelBuildingRequest setValidationLevel(int validationLevel) {
        this.request.setValidationLevel(validationLevel);
        return this;
    }

    @Override
    public boolean isProcessPlugins() {
        return this.request.isProcessPlugins();
    }

    @Override
    public FilterModelBuildingRequest setProcessPlugins(boolean processPlugins) {
        this.request.setProcessPlugins(processPlugins);
        return this;
    }

    @Override
    public boolean isTwoPhaseBuilding() {
        return this.request.isTwoPhaseBuilding();
    }

    @Override
    public FilterModelBuildingRequest setTwoPhaseBuilding(boolean twoPhaseBuilding) {
        this.request.setTwoPhaseBuilding(twoPhaseBuilding);
        return this;
    }

    @Override
    public boolean isLocationTracking() {
        return this.request.isLocationTracking();
    }

    @Override
    public FilterModelBuildingRequest setLocationTracking(boolean locationTracking) {
        this.request.setLocationTracking(locationTracking);
        return this;
    }

    @Override
    public List<Profile> getProfiles() {
        return this.request.getProfiles();
    }

    @Override
    public FilterModelBuildingRequest setProfiles(List<Profile> profiles) {
        this.request.setProfiles(profiles);
        return this;
    }

    @Override
    public List<String> getActiveProfileIds() {
        return this.request.getActiveProfileIds();
    }

    @Override
    public FilterModelBuildingRequest setActiveProfileIds(List<String> activeProfileIds) {
        this.request.setActiveProfileIds(activeProfileIds);
        return this;
    }

    @Override
    public List<String> getInactiveProfileIds() {
        return this.request.getInactiveProfileIds();
    }

    @Override
    public FilterModelBuildingRequest setInactiveProfileIds(List<String> inactiveProfileIds) {
        this.request.setInactiveProfileIds(inactiveProfileIds);
        return this;
    }

    @Override
    public Properties getSystemProperties() {
        return this.request.getSystemProperties();
    }

    @Override
    public FilterModelBuildingRequest setSystemProperties(Properties systemProperties) {
        this.request.setSystemProperties(systemProperties);
        return this;
    }

    @Override
    public Properties getUserProperties() {
        return this.request.getUserProperties();
    }

    @Override
    public FilterModelBuildingRequest setUserProperties(Properties userProperties) {
        this.request.setUserProperties(userProperties);
        return this;
    }

    @Override
    public Date getBuildStartTime() {
        return this.request.getBuildStartTime();
    }

    @Override
    public ModelBuildingRequest setBuildStartTime(Date buildStartTime) {
        this.request.setBuildStartTime(buildStartTime);
        return this;
    }

    @Override
    public ModelResolver getModelResolver() {
        return this.request.getModelResolver();
    }

    @Override
    public FilterModelBuildingRequest setModelResolver(ModelResolver modelResolver) {
        this.request.setModelResolver(modelResolver);
        return this;
    }

    @Override
    public ModelBuildingListener getModelBuildingListener() {
        return this.request.getModelBuildingListener();
    }

    @Override
    public ModelBuildingRequest setModelBuildingListener(ModelBuildingListener modelBuildingListener) {
        this.request.setModelBuildingListener(modelBuildingListener);
        return this;
    }

    @Override
    public ModelCache getModelCache() {
        return this.request.getModelCache();
    }

    @Override
    public FilterModelBuildingRequest setModelCache(ModelCache modelCache) {
        this.request.setModelCache(modelCache);
        return this;
    }

    @Override
    public Model getRawModel() {
        return this.request.getRawModel();
    }

    @Override
    public ModelBuildingRequest setRawModel(Model rawModel) {
        this.request.setRawModel(rawModel);
        return this;
    }

    @Override
    public WorkspaceModelResolver getWorkspaceModelResolver() {
        return this.request.getWorkspaceModelResolver();
    }

    @Override
    public ModelBuildingRequest setWorkspaceModelResolver(WorkspaceModelResolver workspaceResolver) {
        this.request.setWorkspaceModelResolver(workspaceResolver);
        return this;
    }
}

