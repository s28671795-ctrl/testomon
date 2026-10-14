/*
 * Decompiled with CFR 0.152.
 */
package org.apache.maven.model.building;

import org.apache.maven.model.building.ModelProblemCollectorRequest;

public interface ModelProblemCollector {
    public void add(ModelProblemCollectorRequest var1);
}

