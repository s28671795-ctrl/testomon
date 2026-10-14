/*
 * Decompiled with CFR 0.152.
 */
package org.apache.maven.model.profile;

import org.apache.maven.model.Model;
import org.apache.maven.model.Profile;
import org.apache.maven.model.building.ModelBuildingRequest;
import org.apache.maven.model.building.ModelProblemCollector;

public interface ProfileInjector {
    public void injectProfile(Model var1, Profile var2, ModelBuildingRequest var3, ModelProblemCollector var4);
}

