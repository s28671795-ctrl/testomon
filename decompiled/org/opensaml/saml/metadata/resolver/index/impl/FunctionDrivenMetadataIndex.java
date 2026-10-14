/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.metadata.resolver.index.impl;

import com.google.common.base.Function;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.annotation.constraint.NotLive;
import net.shibboleth.utilities.java.support.annotation.constraint.Unmodifiable;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import org.opensaml.saml.metadata.resolver.index.MetadataIndex;
import org.opensaml.saml.metadata.resolver.index.MetadataIndexKey;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;

public class FunctionDrivenMetadataIndex
implements MetadataIndex {
    @Nonnull
    private Function<CriteriaSet, Set<MetadataIndexKey>> criteriaStrategy;
    @Nonnull
    private Function<EntityDescriptor, Set<MetadataIndexKey>> descriptorStrategy;

    public FunctionDrivenMetadataIndex(@Nonnull Function<EntityDescriptor, Set<MetadataIndexKey>> descriptorFunction, @Nonnull Function<CriteriaSet, Set<MetadataIndexKey>> criteriaFunction) {
        this.descriptorStrategy = Constraint.isNotNull(descriptorFunction, "EntityDescriptor strategy function was null");
        this.criteriaStrategy = Constraint.isNotNull(criteriaFunction, "CriteriaSet strategy function was null");
    }

    @Override
    @Nullable
    @NonnullElements
    @Unmodifiable
    @NotLive
    public Set<MetadataIndexKey> generateKeys(@Nonnull CriteriaSet criteriaSet) {
        Constraint.isNotNull(criteriaSet, "CriteriaSet was null");
        return this.criteriaStrategy.apply(criteriaSet);
    }

    @Override
    @Nullable
    @NonnullElements
    @Unmodifiable
    @NotLive
    public Set<MetadataIndexKey> generateKeys(@Nonnull EntityDescriptor descriptor) {
        Constraint.isNotNull(descriptor, "EntityDescriptor was null");
        return this.descriptorStrategy.apply(descriptor);
    }
}

