/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.cfg.context;

import java.lang.annotation.ElementType;
import org.hibernate.validator.cfg.context.ConstructorConstraintMappingContext;
import org.hibernate.validator.cfg.context.ContainerElementConstraintMappingContext;
import org.hibernate.validator.cfg.context.MethodConstraintMappingContext;
import org.hibernate.validator.cfg.context.PropertyConstraintMappingContext;
import org.hibernate.validator.internal.cfg.context.CascadableConstraintMappingContextImplBase;
import org.hibernate.validator.internal.cfg.context.TypeConstraintMappingContextImpl;
import org.hibernate.validator.internal.engine.ConstraintCreationContext;
import org.hibernate.validator.internal.metadata.descriptor.ConstraintDescriptorImpl;
import org.hibernate.validator.internal.metadata.location.ConstraintLocation;
import org.hibernate.validator.internal.metadata.raw.ConstrainedElement;
import org.hibernate.validator.internal.properties.Constrainable;
import org.hibernate.validator.internal.properties.Property;

abstract class AbstractPropertyConstraintMappingContextImpl<T extends Property>
extends CascadableConstraintMappingContextImplBase<PropertyConstraintMappingContext>
implements PropertyConstraintMappingContext {
    private final TypeConstraintMappingContextImpl<?> typeContext;
    private final T property;
    private final ConstraintLocation location;

    protected AbstractPropertyConstraintMappingContextImpl(TypeConstraintMappingContextImpl<?> typeContext, T property, ConstraintLocation location) {
        super(typeContext.getConstraintMapping(), property.getType());
        this.typeContext = typeContext;
        this.property = property;
        this.location = location;
    }

    @Override
    protected AbstractPropertyConstraintMappingContextImpl<?> getThis() {
        return this;
    }

    @Override
    public PropertyConstraintMappingContext ignoreAnnotations() {
        return this.ignoreAnnotations(true);
    }

    @Override
    public PropertyConstraintMappingContext ignoreAnnotations(boolean ignoreAnnotations) {
        this.mapping.getAnnotationProcessingOptions().ignoreConstraintAnnotationsOnMember((Constrainable)this.property, ignoreAnnotations);
        return this;
    }

    @Override
    public PropertyConstraintMappingContext property(String property, ElementType elementType) {
        return this.typeContext.property(property, elementType);
    }

    @Override
    public PropertyConstraintMappingContext field(String property) {
        return this.typeContext.field(property);
    }

    @Override
    public PropertyConstraintMappingContext getter(String property) {
        return this.typeContext.getter(property);
    }

    @Override
    public ConstructorConstraintMappingContext constructor(Class<?> ... parameterTypes) {
        return this.typeContext.constructor(parameterTypes);
    }

    @Override
    public MethodConstraintMappingContext method(String name, Class<?> ... parameterTypes) {
        return this.typeContext.method(name, parameterTypes);
    }

    @Override
    public ContainerElementConstraintMappingContext containerElementType() {
        return super.containerElement(this, this.typeContext, this.location);
    }

    @Override
    public ContainerElementConstraintMappingContext containerElementType(int index, int ... nestedIndexes) {
        return super.containerElement(this, this.typeContext, this.location, index, nestedIndexes);
    }

    abstract ConstrainedElement build(ConstraintCreationContext var1);

    @Override
    protected ConstraintDescriptorImpl.ConstraintType getConstraintType() {
        return ConstraintDescriptorImpl.ConstraintType.GENERIC;
    }

    protected T getProperty() {
        return this.property;
    }
}

