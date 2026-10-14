/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.criterion;

import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import net.shibboleth.utilities.java.support.resolver.Criterion;

public final class BindingLocationCriterion
implements Criterion {
    @Nonnull
    @NotEmpty
    private final String location;

    public BindingLocationCriterion(@Nonnull @NotEmpty String locationUri) {
        this.location = Constraint.isNotNull(StringSupport.trimOrNull(locationUri), "Location cannot be null or empty");
    }

    @Nonnull
    @NotEmpty
    public String getLocation() {
        return this.location;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("BindingLocation [location=");
        builder.append(this.location);
        builder.append("]");
        return builder.toString();
    }

    public int hashCode() {
        return this.location.hashCode();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj instanceof BindingLocationCriterion) {
            return this.location.equals(((BindingLocationCriterion)obj).location);
        }
        return false;
    }
}

