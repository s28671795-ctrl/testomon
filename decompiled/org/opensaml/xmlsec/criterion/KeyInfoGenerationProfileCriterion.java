/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.criterion;

import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import net.shibboleth.utilities.java.support.resolver.Criterion;

public final class KeyInfoGenerationProfileCriterion
implements Criterion {
    @Nonnull
    @NotEmpty
    private final String name;

    public KeyInfoGenerationProfileCriterion(@Nonnull @NotEmpty String profileName) {
        this.name = Constraint.isNotNull(StringSupport.trimOrNull(profileName), "Name cannot be null or empty");
    }

    @Nonnull
    @NotEmpty
    public String getName() {
        return this.name;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("KeyInfoGenerationProfileCriterion [name=");
        builder.append(this.name);
        builder.append("]");
        return builder.toString();
    }

    public int hashCode() {
        return this.name.hashCode();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj instanceof KeyInfoGenerationProfileCriterion) {
            return this.name.equals(((KeyInfoGenerationProfileCriterion)obj).getName());
        }
        return false;
    }
}

