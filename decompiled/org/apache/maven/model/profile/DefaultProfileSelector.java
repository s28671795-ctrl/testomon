/*
 * Decompiled with CFR 0.152.
 */
package org.apache.maven.model.profile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import org.apache.maven.model.Activation;
import org.apache.maven.model.Profile;
import org.apache.maven.model.building.ModelProblem;
import org.apache.maven.model.building.ModelProblemCollector;
import org.apache.maven.model.building.ModelProblemCollectorRequest;
import org.apache.maven.model.profile.ProfileActivationContext;
import org.apache.maven.model.profile.ProfileSelector;
import org.apache.maven.model.profile.activation.ProfileActivator;
import org.codehaus.plexus.component.annotations.Component;
import org.codehaus.plexus.component.annotations.Requirement;

@Component(role=ProfileSelector.class)
public class DefaultProfileSelector
implements ProfileSelector {
    @Requirement(role=ProfileActivator.class)
    private List<ProfileActivator> activators = new ArrayList<ProfileActivator>();

    public DefaultProfileSelector addProfileActivator(ProfileActivator profileActivator) {
        if (profileActivator != null) {
            this.activators.add(profileActivator);
        }
        return this;
    }

    @Override
    public List<Profile> getActiveProfiles(Collection<Profile> profiles, ProfileActivationContext context, ModelProblemCollector problems) {
        HashSet<String> activatedIds = new HashSet<String>(context.getActiveProfileIds());
        HashSet<String> deactivatedIds = new HashSet<String>(context.getInactiveProfileIds());
        ArrayList<Profile> activeProfiles = new ArrayList<Profile>(profiles.size());
        ArrayList<Profile> activePomProfilesByDefault = new ArrayList<Profile>();
        boolean activatedPomProfileNotByDefault = false;
        for (Profile profile : profiles) {
            if (deactivatedIds.contains(profile.getId())) continue;
            if (activatedIds.contains(profile.getId()) || this.isActive(profile, context, problems)) {
                activeProfiles.add(profile);
                if (!"pom".equals(profile.getSource())) continue;
                activatedPomProfileNotByDefault = true;
                continue;
            }
            if (!this.isActiveByDefault(profile)) continue;
            if ("pom".equals(profile.getSource())) {
                activePomProfilesByDefault.add(profile);
                continue;
            }
            activeProfiles.add(profile);
        }
        if (!activatedPomProfileNotByDefault) {
            activeProfiles.addAll(activePomProfilesByDefault);
        }
        return activeProfiles;
    }

    private boolean isActive(Profile profile, ProfileActivationContext context, ModelProblemCollector problems) {
        boolean isActive = false;
        for (ProfileActivator activator : this.activators) {
            if (!activator.presentInConfig(profile, context, problems)) continue;
            isActive = true;
        }
        for (ProfileActivator activator : this.activators) {
            try {
                if (!activator.presentInConfig(profile, context, problems)) continue;
                isActive &= activator.isActive(profile, context, problems);
            }
            catch (RuntimeException e) {
                problems.add(new ModelProblemCollectorRequest(ModelProblem.Severity.ERROR, ModelProblem.Version.BASE).setMessage("Failed to determine activation for profile " + profile.getId()).setLocation(profile.getLocation("")).setException(e));
                return false;
            }
        }
        return isActive;
    }

    private boolean isActiveByDefault(Profile profile) {
        Activation activation = profile.getActivation();
        return activation != null && activation.isActiveByDefault();
    }
}

