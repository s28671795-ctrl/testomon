/*
 * Decompiled with CFR 0.152.
 */
package org.apache.maven.model.profile.activation;

import org.apache.maven.model.Profile;
import org.apache.maven.model.building.ModelProblemCollector;
import org.apache.maven.model.profile.ProfileActivationContext;

public interface ProfileActivator {
    public boolean isActive(Profile var1, ProfileActivationContext var2, ModelProblemCollector var3);

    public boolean presentInConfig(Profile var1, ProfileActivationContext var2, ModelProblemCollector var3);
}

