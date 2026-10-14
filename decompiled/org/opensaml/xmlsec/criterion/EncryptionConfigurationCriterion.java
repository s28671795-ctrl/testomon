/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.criterion;

import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.annotation.constraint.NotLive;
import net.shibboleth.utilities.java.support.annotation.constraint.Unmodifiable;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.resolver.Criterion;
import org.opensaml.xmlsec.EncryptionConfiguration;

public class EncryptionConfigurationCriterion
implements Criterion {
    @Nonnull
    @NonnullElements
    private List<EncryptionConfiguration> configs;

    public EncryptionConfigurationCriterion(@Nonnull @NonnullElements @NotEmpty List<EncryptionConfiguration> configurations) {
        Constraint.isNotNull(configurations, "List of configurations cannot be null");
        this.configs = new ArrayList<EncryptionConfiguration>(Collections2.filter(configurations, Predicates.notNull()));
        Constraint.isGreaterThanOrEqual(1L, this.configs.size(), "At least one configuration is required");
    }

    public EncryptionConfigurationCriterion(EncryptionConfiguration ... configurations) {
        Constraint.isNotNull(configurations, "List of configurations cannot be null");
        this.configs = new ArrayList<EncryptionConfiguration>(Collections2.filter(Arrays.asList(configurations), Predicates.notNull()));
        Constraint.isGreaterThanOrEqual(1L, this.configs.size(), "At least one configuration is required");
    }

    @Nonnull
    @NonnullElements
    @NotLive
    @Unmodifiable
    @NotEmpty
    public List<EncryptionConfiguration> getConfigurations() {
        return ImmutableList.copyOf(this.configs);
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("EncryptionConfigurationCriterion [configs=");
        builder.append(this.configs);
        builder.append("]");
        return builder.toString();
    }

    public int hashCode() {
        return this.configs.hashCode();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj instanceof EncryptionConfigurationCriterion) {
            return this.configs.equals(((EncryptionConfigurationCriterion)obj).getConfigurations());
        }
        return false;
    }
}

