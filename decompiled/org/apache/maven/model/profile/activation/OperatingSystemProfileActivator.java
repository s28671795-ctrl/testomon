/*
 * Decompiled with CFR 0.152.
 */
package org.apache.maven.model.profile.activation;

import org.apache.maven.model.Activation;
import org.apache.maven.model.ActivationOS;
import org.apache.maven.model.Profile;
import org.apache.maven.model.building.ModelProblemCollector;
import org.apache.maven.model.profile.ProfileActivationContext;
import org.apache.maven.model.profile.activation.ProfileActivator;
import org.codehaus.plexus.component.annotations.Component;
import org.codehaus.plexus.util.Os;

@Component(role=ProfileActivator.class, hint="os")
public class OperatingSystemProfileActivator
implements ProfileActivator {
    @Override
    public boolean isActive(Profile profile, ProfileActivationContext context, ModelProblemCollector problems) {
        Activation activation = profile.getActivation();
        if (activation == null) {
            return false;
        }
        ActivationOS os = activation.getOs();
        if (os == null) {
            return false;
        }
        boolean active = this.ensureAtLeastOneNonNull(os);
        if (active && os.getFamily() != null) {
            active = this.determineFamilyMatch(os.getFamily());
        }
        if (active && os.getName() != null) {
            active = this.determineNameMatch(os.getName());
        }
        if (active && os.getArch() != null) {
            active = this.determineArchMatch(os.getArch());
        }
        if (active && os.getVersion() != null) {
            active = this.determineVersionMatch(os.getVersion());
        }
        return active;
    }

    @Override
    public boolean presentInConfig(Profile profile, ProfileActivationContext context, ModelProblemCollector problems) {
        Activation activation = profile.getActivation();
        if (activation == null) {
            return false;
        }
        ActivationOS os = activation.getOs();
        return os != null;
    }

    private boolean ensureAtLeastOneNonNull(ActivationOS os) {
        return os.getArch() != null || os.getFamily() != null || os.getName() != null || os.getVersion() != null;
    }

    private boolean determineVersionMatch(String version) {
        String test = version;
        boolean reverse = false;
        if (test.startsWith("!")) {
            reverse = true;
            test = test.substring(1);
        }
        boolean result = Os.isVersion(test);
        return reverse ? !result : result;
    }

    private boolean determineArchMatch(String arch) {
        String test = arch;
        boolean reverse = false;
        if (test.startsWith("!")) {
            reverse = true;
            test = test.substring(1);
        }
        boolean result = Os.isArch(test);
        return reverse ? !result : result;
    }

    private boolean determineNameMatch(String name) {
        String test = name;
        boolean reverse = false;
        if (test.startsWith("!")) {
            reverse = true;
            test = test.substring(1);
        }
        boolean result = Os.isName(test);
        return reverse ? !result : result;
    }

    private boolean determineFamilyMatch(String family) {
        String test = family;
        boolean reverse = false;
        if (test.startsWith("!")) {
            reverse = true;
            test = test.substring(1);
        }
        boolean result = Os.isFamily(test);
        return reverse ? !result : result;
    }
}

