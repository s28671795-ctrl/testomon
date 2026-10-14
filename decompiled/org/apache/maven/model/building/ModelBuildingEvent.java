/*
 * Decompiled with CFR 0.152.
 */
package org.apache.maven.model.building;

import org.apache.maven.model.Model;
import org.apache.maven.model.building.ModelBuildingRequest;
import org.apache.maven.model.building.ModelProblemCollector;

public interface ModelBuildingEvent {
    public Model getModel();

    public ModelBuildingRequest getRequest();

    public ModelProblemCollector getProblems();
}

