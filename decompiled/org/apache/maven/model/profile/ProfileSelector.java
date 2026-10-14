/*
 * Decompiled with CFR 0.152.
 */
package org.apache.maven.model.profile;

import java.util.Collection;
import java.util.List;
import org.apache.maven.model.Profile;
import org.apache.maven.model.building.ModelProblemCollector;
import org.apache.maven.model.profile.ProfileActivationContext;

public interface ProfileSelector {
    public List<Profile> getActiveProfiles(Collection<Profile> var1, ProfileActivationContext var2, ModelProblemCollector var3);
}

