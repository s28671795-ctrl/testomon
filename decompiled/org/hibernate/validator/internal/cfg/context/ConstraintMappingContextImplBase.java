/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.cfg.context;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.validator.internal.cfg.context.ConfiguredConstraint;
import org.hibernate.validator.internal.cfg.context.ConstraintContextImplBase;
import org.hibernate.validator.internal.cfg.context.DefaultConstraintMapping;
import org.hibernate.validator.internal.engine.ConstraintCreationContext;
import org.hibernate.validator.internal.metadata.core.MetaConstraint;
import org.hibernate.validator.internal.metadata.core.MetaConstraints;
import org.hibernate.validator.internal.metadata.descriptor.ConstraintDescriptorImpl;
import org.hibernate.validator.internal.util.CollectionHelper;

abstract class ConstraintMappingContextImplBase
extends ConstraintContextImplBase {
    private final Set<ConfiguredConstraint<?>> constraints = CollectionHelper.newHashSet();

    ConstraintMappingContextImplBase(DefaultConstraintMapping mapping) {
        super(mapping);
    }

    protected abstract ConstraintDescriptorImpl.ConstraintType getConstraintType();

    protected DefaultConstraintMapping getConstraintMapping() {
        return this.mapping;
    }

    protected void addConstraint(ConfiguredConstraint<?> constraint) {
        this.constraints.add(constraint);
    }

    protected Set<MetaConstraint<?>> getConstraints(ConstraintCreationContext constraintCreationContext) {
        if (this.constraints == null) {
            return Collections.emptySet();
        }
        HashSet<MetaConstraint<?>> metaConstraints = CollectionHelper.newHashSet();
        for (ConfiguredConstraint<?> configuredConstraint : this.constraints) {
            metaConstraints.add(this.asMetaConstraint(configuredConstraint, constraintCreationContext));
        }
        return metaConstraints;
    }

    private <A extends Annotation> MetaConstraint<A> asMetaConstraint(ConfiguredConstraint<A> config, ConstraintCreationContext constraintCreationContext) {
        ConstraintDescriptorImpl<A> constraintDescriptor = new ConstraintDescriptorImpl<A>(constraintCreationContext.getConstraintHelper(), config.getLocation().getConstrainable(), config.createAnnotationDescriptor(), config.getLocation().getKind(), this.getConstraintType());
        return MetaConstraints.create(constraintCreationContext.getTypeResolutionHelper(), constraintCreationContext.getValueExtractorManager(), constraintCreationContext.getConstraintValidatorManager(), constraintDescriptor, config.getLocation());
    }
}

