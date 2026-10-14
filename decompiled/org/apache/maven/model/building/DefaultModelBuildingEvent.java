/*
 * Decompiled with CFR 0.152.
 */
package org.apache.maven.model.building;

import org.apache.maven.model.Model;
import org.apache.maven.model.building.ModelBuildingEvent;
import org.apache.maven.model.building.ModelBuildingRequest;
import org.apache.maven.model.building.ModelProblemCollector;

class DefaultModelBuildingEvent
implements ModelBuildingEvent {
    private final Model model;
    private final ModelBuildingRequest request;
    private final ModelProblemCollector problems;

    DefaultModelBuildingEvent(Model model, ModelBuildingRequest request, ModelProblemCollector problems) {
        this.model = model;
        this.request = request;
        this.problems = problems;
    }

    @Override
    public Model getModel() {
        return this.model;
    }

    @Override
    public ModelBuildingRequest getRequest() {
        return this.request;
    }

    @Override
    public ModelProblemCollector getProblems() {
        return this.problems;
    }
}

