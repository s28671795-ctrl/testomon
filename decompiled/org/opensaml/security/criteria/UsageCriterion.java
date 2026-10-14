/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.security.criteria;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.resolver.Criterion;
import org.opensaml.security.credential.UsageType;

public final class UsageCriterion
implements Criterion {
    private UsageType credUsage;

    public UsageCriterion(@Nullable UsageType usage) {
        this.setUsage(usage);
    }

    @Nonnull
    public UsageType getUsage() {
        return this.credUsage;
    }

    public void setUsage(@Nullable UsageType usage) {
        this.credUsage = usage != null ? usage : UsageType.UNSPECIFIED;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("UsageCriterion [credUsage=");
        builder.append((Object)this.credUsage);
        builder.append("]");
        return builder.toString();
    }

    public int hashCode() {
        return this.credUsage.hashCode();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj instanceof UsageCriterion) {
            return this.credUsage.equals((Object)((UsageCriterion)obj).credUsage);
        }
        return false;
    }
}

