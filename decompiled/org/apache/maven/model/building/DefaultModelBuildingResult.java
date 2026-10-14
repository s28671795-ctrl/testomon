/*
 * Decompiled with CFR 0.152.
 */
package org.apache.maven.model.building;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.apache.maven.model.Model;
import org.apache.maven.model.Profile;
import org.apache.maven.model.building.ModelBuildingResult;
import org.apache.maven.model.building.ModelProblem;

class DefaultModelBuildingResult
implements ModelBuildingResult {
    private Model effectiveModel;
    private List<String> modelIds = new ArrayList<String>();
    private Map<String, Model> rawModels = new HashMap<String, Model>();
    private Map<String, List<Profile>> activePomProfiles = new HashMap<String, List<Profile>>();
    private List<Profile> activeExternalProfiles = new ArrayList<Profile>();
    private List<ModelProblem> problems = new ArrayList<ModelProblem>();

    DefaultModelBuildingResult() {
    }

    @Override
    public Model getEffectiveModel() {
        return this.effectiveModel;
    }

    public DefaultModelBuildingResult setEffectiveModel(Model model) {
        this.effectiveModel = model;
        return this;
    }

    @Override
    public List<String> getModelIds() {
        return this.modelIds;
    }

    public DefaultModelBuildingResult addModelId(String modelId) {
        Objects.requireNonNull(modelId, "modelId cannot null");
        this.modelIds.add(modelId);
        return this;
    }

    @Override
    public Model getRawModel() {
        return this.rawModels.get(this.modelIds.get(0));
    }

    @Override
    public Model getRawModel(String modelId) {
        return this.rawModels.get(modelId);
    }

    public DefaultModelBuildingResult setRawModel(String modelId, Model rawModel) {
        Objects.requireNonNull(modelId, "modelId cannot null");
        this.rawModels.put(modelId, rawModel);
        return this;
    }

    @Override
    public List<Profile> getActivePomProfiles(String modelId) {
        return this.activePomProfiles.get(modelId);
    }

    public DefaultModelBuildingResult setActivePomProfiles(String modelId, List<Profile> activeProfiles) {
        Objects.requireNonNull(modelId, "modelId cannot null");
        if (activeProfiles != null) {
            this.activePomProfiles.put(modelId, new ArrayList<Profile>(activeProfiles));
        } else {
            this.activePomProfiles.remove(modelId);
        }
        return this;
    }

    @Override
    public List<Profile> getActiveExternalProfiles() {
        return this.activeExternalProfiles;
    }

    public DefaultModelBuildingResult setActiveExternalProfiles(List<Profile> activeProfiles) {
        if (activeProfiles != null) {
            this.activeExternalProfiles = new ArrayList<Profile>(activeProfiles);
        } else {
            this.activeExternalProfiles.clear();
        }
        return this;
    }

    @Override
    public List<ModelProblem> getProblems() {
        return this.problems;
    }

    public DefaultModelBuildingResult setProblems(List<ModelProblem> problems) {
        if (problems != null) {
            this.problems = new ArrayList<ModelProblem>(problems);
        } else {
            this.problems.clear();
        }
        return this;
    }
}

