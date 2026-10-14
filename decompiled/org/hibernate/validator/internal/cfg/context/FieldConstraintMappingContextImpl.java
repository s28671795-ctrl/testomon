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
import org.hibernate.validator.internal.metadata.descriptor.ConstraintDescriptorImpl;
import org.hibernate.validator.internal.metadata.location.ConstraintLocation;
import org.hibernate.validator.internal.metadata.raw.ConfigurationSource;
import org.hibernate.validator.internal.metadata.raw.ConstrainedElement;
import org.hibernate.validator.internal.metadata.raw.ConstrainedField;
import org.hibernate.validator.internal.properties.Field;
import org.hibernate.validator.internal.properties.javabean.JavaBeanField;

final class FieldConstraintMappingContextImpl
extends AbstractPropertyConstraintMappingContextImpl<JavaBeanField> {
    FieldConstraintMappingContextImpl(TypeConstraintMappingContextImpl<?> typeContext, JavaBeanField javaBeanField) {
        super(typeContext, javaBeanField, ConstraintLocation.forField(javaBeanField));
    }

    @Override
    public PropertyConstraintMappingContext constraint(ConstraintDef<?, ?> definition) {
        super.addConstraint(ConfiguredConstraint.forField(definition, (JavaBeanField)this.getProperty()));
        return this;
    }

    @Override
    ConstrainedElement build(ConstraintCreationContext constraintCreationContext) {
        return new ConstrainedField(ConfigurationSource.API, (Field)this.getProperty(), this.getConstraints(constraintCreationContext), this.getTypeArgumentConstraints(constraintCreationContext), this.getCascadingMetaDataBuilder());
    }

    @Override
    protected ConstraintDescriptorImpl.ConstraintType getConstraintType() {
        return ConstraintDescriptorImpl.ConstraintType.GENERIC;
    }
}

