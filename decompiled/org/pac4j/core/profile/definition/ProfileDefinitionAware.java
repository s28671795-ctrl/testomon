/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.profile.definition;

import org.pac4j.core.profile.definition.ProfileDefinition;
import org.pac4j.core.util.CommonHelper;
import org.pac4j.core.util.InitializableObject;

public abstract class ProfileDefinitionAware
extends InitializableObject {
    private ProfileDefinition profileDefinition;

    public ProfileDefinition getProfileDefinition() {
        return this.profileDefinition;
    }

    public void setProfileDefinition(ProfileDefinition profileDefinition) {
        CommonHelper.assertNotNull("profileDefinition", profileDefinition);
        this.profileDefinition = profileDefinition;
    }

    protected void defaultProfileDefinition(ProfileDefinition profileDefinition) {
        CommonHelper.assertNotNull("profileDefinition", profileDefinition);
        if (this.profileDefinition == null) {
            this.profileDefinition = profileDefinition;
        }
    }
}

