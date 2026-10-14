/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.profile;

import java.security.Principal;
import java.util.Objects;
import org.pac4j.core.profile.UserProfile;
import org.pac4j.core.util.CommonHelper;

public class Pac4JPrincipal
implements Principal {
    private final String name;

    public Pac4JPrincipal(UserProfile profile) {
        String username = profile.getUsername();
        this.name = CommonHelper.isNotBlank(username) ? username : profile.getId();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.getName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        Pac4JPrincipal principal = (Pac4JPrincipal)o;
        return CommonHelper.areEquals(this.getName(), principal.getName());
    }

    @Override
    public String toString() {
        return CommonHelper.toNiceString(this.getClass(), "profileId", this.name);
    }
}

