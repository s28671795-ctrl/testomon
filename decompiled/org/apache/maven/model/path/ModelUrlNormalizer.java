/*
 * Decompiled with CFR 0.152.
 */
package org.apache.maven.model.path;

import org.apache.maven.model.Model;
import org.apache.maven.model.building.ModelBuildingRequest;

public interface ModelUrlNormalizer {
    public void normalize(Model var1, ModelBuildingRequest var2);
}

