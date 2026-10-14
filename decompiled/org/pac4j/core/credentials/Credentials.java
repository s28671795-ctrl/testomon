/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.credentials;

import java.io.Serializable;
import org.pac4j.core.profile.UserProfile;

public abstract class Credentials
implements Serializable {
    private static final long serialVersionUID = 4864923514027378583L;
    private UserProfile userProfile = null;

    public UserProfile getUserProfile() {
        return this.userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public abstract boolean equals(Object var1);

    public abstract int hashCode();
}

