/*
 * Decompiled with CFR 0.152.
 */
package org.apache.maven.model.building;

import java.util.List;
import org.apache.maven.model.building.ModelProblem;
import org.apache.maven.model.building.ModelProblemCollector;

public interface ModelProblemCollectorExt
extends ModelProblemCollector {
    public List<ModelProblem> getProblems();
}

