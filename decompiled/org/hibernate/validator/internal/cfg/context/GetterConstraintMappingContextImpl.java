/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.cfg.context;

import org.hibernate.validator.cfg.ConstraintDef;
import org.hibernate.validator.cfg.context.PropertyConstraintMappingContext;
import org.hibernate.validator.internal.cfg.context.AbstractPropertyConstraintMappingContextImpl;
import org.hibernate.validator.internal.cfg.context.ConfiguredConstraint;
import org.hibernate.validator.internal.cfg.context.TypeConstraintMappingContextImpl;
import org.hibernate.validator.internal.engine.ConstraintCreationContext;
import org.hibernate.validator.internal.metadata.location.ConstraintLocation;
import org.hibernate.validator.internal.metadata.raw.ConfigurationSource;
import org.hibernate.validator.internal.metadata.raw.ConstrainedElement;
import org.hibernate.validator.internal.metadata.raw.ConstrainedExecutable;
import org.hibernate.validator.internal.properties.Callable;
import org.hibernate.validator.internal.properties.javabean.JavaBeanGetter;

final class GetterConstraintMappingContextImpl
extends AbstractPropertyConstraintMappingContextImpl<JavaBeanGetter> {
    GetterConstraintMappingContextImpl(TypeConstraintMappingContextImpl<?> typeContext, JavaBeanGetter javaBeanGetter) {
        super(typeContext, javaBeanGetter, ConstraintLocation.forGetter(javaBeanGetter));
    }

    @Override
    public PropertyConstraintMappingContext constraint(ConstraintDef<?, ?> definition) {
        super.addConstraint(ConfiguredConstraint.forGetter(definition, (JavaBeanGetter)this.getProperty()));
        return this;
    }

    @Override
    ConstrainedElement build(ConstraintCreationContext constraintCreationContext) {
        return new ConstrainedExecutable(ConfigurationSource.API, (Callable)this.getProperty(), this.getConstraints(constraintCreationContext), this.getTypeArgumentConstraints(constraintCreationContext), this.getCascadingMetaDataBuilder());
    }
}

