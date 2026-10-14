/*
 * Decompiled with CFR 0.152.
 */
package org.apache.maven.model.building;

import java.util.List;
import org.apache.maven.model.Model;
import org.apache.maven.model.Profile;
import org.apache.maven.model.building.ModelProblem;

public interface ModelBuildingResult {
    public List<String> getModelIds();

    public Model getEffectiveModel();

    public Model getRawModel();

    public Model getRawModel(String var1);

    public List<Profile> getActivePomProfiles(String var1);

    public List<Profile> getActiveExternalProfiles();

    public List<ModelProblem> getProblems();
}

