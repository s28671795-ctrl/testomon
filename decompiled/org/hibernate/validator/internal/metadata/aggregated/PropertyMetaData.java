/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.metadata.aggregated;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Type;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.ElementKind;
import org.hibernate.validator.internal.engine.ConstraintCreationContext;
import org.hibernate.validator.internal.metadata.aggregated.AbstractConstraintMetaData;
import org.hibernate.validator.internal.metadata.aggregated.AbstractPropertyCascadable;
import org.hibernate.validator.internal.metadata.aggregated.CascadingMetaData;
import org.hibernate.validator.internal.metadata.aggregated.MetaDataBuilder;
import org.hibernate.validator.internal.metadata.core.MetaConstraint;
import org.hibernate.validator.internal.metadata.core.MetaConstraints;
import org.hibernate.validator.internal.metadata.descriptor.PropertyDescriptorImpl;
import org.hibernate.validator.internal.metadata.facets.Cascadable;
import org.hibernate.validator.internal.metadata.location.ConstraintLocation;
import org.hibernate.validator.internal.metadata.location.GetterConstraintLocation;
import org.hibernate.validator.internal.metadata.location.TypeArgumentConstraintLocation;
import org.hibernate.validator.internal.metadata.raw.ConstrainedElement;
import org.hibernate.validator.internal.metadata.raw.ConstrainedExecutable;
import org.hibernate.validator.internal.metadata.raw.ConstrainedField;
import org.hibernate.validator.internal.properties.Getter;
import org.hibernate.validator.internal.properties.Property;
import org.hibernate.validator.internal.properties.javabean.JavaBeanGetter;
import org.hibernate.validator.internal.util.CollectionHelper;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;

public class PropertyMetaData
extends AbstractConstraintMetaData {
    private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
    private final Set<Cascadable> cascadables;

    private PropertyMetaData(String propertyName, Type type, Set<MetaConstraint<?>> constraints, Set<MetaConstraint<?>> containerElementsConstraints, Set<Cascadable> cascadables) {
        super(propertyName, type, constraints, containerElementsConstraints, !cascadables.isEmpty(), !cascadables.isEmpty() || !constraints.isEmpty() || !containerElementsConstraints.isEmpty());
        this.cascadables = CollectionHelper.toImmutableSet(cascadables);
    }

    @Override
    public PropertyDescriptorImpl asDescriptor(boolean defaultGroupSequenceRedefined, List<Class<?>> defaultGroupSequence) {
        CascadingMetaData firstCascadingMetaData = this.cascadables.isEmpty() ? null : this.cascadables.iterator().next().getCascadingMetaData();
        return new PropertyDescriptorImpl(this.getType(), this.getName(), this.asDescriptors(this.getDirectConstraints()), this.asContainerElementTypeDescriptors(this.getContainerElementsConstraints(), firstCascadingMetaData, defaultGroupSequenceRedefined, defaultGroupSequence), firstCascadingMetaData != null ? firstCascadingMetaData.isCascading() : false, defaultGroupSequenceRedefined, defaultGroupSequence, firstCascadingMetaData != null ? firstCascadingMetaData.getGroupConversionDescriptors() : Collections.emptySet());
    }

    public Set<Cascadable> getCascadables() {
        return this.cascadables;
    }

    @Override
    public String toString() {
        return "PropertyMetaData [type=" + this.getType() + ", propertyName=" + this.getName() + "]]";
    }

    @Override
    public ElementKind getKind() {
        return ElementKind.PROPERTY;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        return this.getClass() == obj.getClass();
    }

    public static class Builder
    extends MetaDataBuilder {
        private static final EnumSet<ConstrainedElement.ConstrainedElementKind> SUPPORTED_ELEMENT_KINDS = EnumSet.of(ConstrainedElement.ConstrainedElementKind.FIELD, ConstrainedElement.ConstrainedElementKind.GETTER);
        private final String propertyName;
        private final Map<Property, Cascadable.Builder> cascadableBuilders = new HashMap<Property, Cascadable.Builder>();
        private final Type propertyType;

        public Builder(Class<?> beanClass, ConstrainedField constrainedProperty, ConstraintCreationContext constraintCreationContext) {
            super(beanClass, constraintCreationContext);
            this.propertyName = constrainedProperty.getField().getName();
            this.propertyType = constrainedProperty.getField().getType();
            this.add(constrainedProperty);
        }

        public Builder(Class<?> beanClass, ConstrainedExecutable constrainedMethod, ConstraintCreationContext constraintCreationContext) {
            super(beanClass, constraintCreationContext);
            this.propertyName = constrainedMethod.getCallable().as(Property.class).getPropertyName();
            this.propertyType = constrainedMethod.getCallable().getType();
            this.add(constrainedMethod);
        }

        @Override
        public boolean accepts(ConstrainedElement constrainedElement) {
            if (!SUPPORTED_ELEMENT_KINDS.contains((Object)constrainedElement.getKind())) {
                return false;
            }
            return Objects.equals(this.getPropertyName(constrainedElement), this.propertyName);
        }

        @Override
        public final void add(ConstrainedElement constrainedElement) {
            super.add(constrainedElement);
            if (constrainedElement.getCascadingMetaDataBuilder().isMarkedForCascadingOnAnnotatedObjectOrContainerElements() || constrainedElement.getCascadingMetaDataBuilder().hasGroupConversionsOnAnnotatedObjectOrContainerElements()) {
                Property property = this.getConstrainableFromConstrainedElement(constrainedElement);
                Cascadable.Builder builder = this.cascadableBuilders.get(property);
                if (builder == null) {
                    builder = AbstractPropertyCascadable.AbstractBuilder.builder(this.constraintCreationContext.getValueExtractorManager(), property, constrainedElement.getCascadingMetaDataBuilder());
                    this.cascadableBuilders.put(property, builder);
                } else {
                    builder.mergeCascadingMetaData(constrainedElement.getCascadingMetaDataBuilder());
                }
            }
        }

        private Property getConstrainableFromConstrainedElement(ConstrainedElement constrainedElement) {
            switch (constrainedElement.getKind()) {
                case FIELD: {
                    if (constrainedElement instanceof ConstrainedField) {
                        return ((ConstrainedField)constrainedElement).getField();
                    }
                    throw LOG.getUnexpectedConstraintElementType(ConstrainedField.class, constrainedElement.getClass());
                }
                case GETTER: {
                    if (constrainedElement instanceof ConstrainedExecutable) {
                        return ((ConstrainedExecutable)constrainedElement).getCallable().as(Getter.class);
                    }
                    throw LOG.getUnexpectedConstraintElementType(ConstrainedExecutable.class, constrainedElement.getClass());
                }
            }
            throw LOG.getUnsupportedConstraintElementType(constrainedElement.getKind());
        }

        @Override
        protected Set<MetaConstraint<?>> adaptConstraints(ConstrainedElement constrainedElement, Set<MetaConstraint<?>> constraints) {
            if (constraints.isEmpty() || constrainedElement.getKind() != ConstrainedElement.ConstrainedElementKind.GETTER) {
                return constraints;
            }
            ConstraintLocation getterConstraintLocation = ConstraintLocation.forGetter(((ConstrainedExecutable)constrainedElement).getCallable().as(JavaBeanGetter.class));
            return constraints.stream().map(c -> this.withGetterLocation(getterConstraintLocation, (MetaConstraint<?>)c)).collect(Collectors.toSet());
        }

        private MetaConstraint<?> withGetterLocation(ConstraintLocation getterConstraintLocation, MetaConstraint<?> constraint) {
            ConstraintLocation converted = null;
            if (!(constraint.getLocation() instanceof TypeArgumentConstraintLocation)) {
                converted = constraint.getLocation() instanceof GetterConstraintLocation ? constraint.getLocation() : getterConstraintLocation;
            } else {
                ArrayDeque<ConstraintLocation> locationStack = new ArrayDeque<ConstraintLocation>();
                ConstraintLocation current = constraint.getLocation();
                do {
                    locationStack.addFirst(current);
                } while ((current = current instanceof TypeArgumentConstraintLocation ? ((TypeArgumentConstraintLocation)current).getDelegate() : null) != null);
                for (ConstraintLocation location : locationStack) {
                    if (!(location instanceof TypeArgumentConstraintLocation)) {
                        if (location instanceof GetterConstraintLocation) {
                            converted = location;
                            continue;
                        }
                        converted = getterConstraintLocation;
                        continue;
                    }
                    converted = ConstraintLocation.forTypeArgument(converted, ((TypeArgumentConstraintLocation)location).getTypeParameter(), location.getTypeForValidatorResolution());
                }
            }
            return MetaConstraints.create(this.constraintCreationContext.getTypeResolutionHelper(), this.constraintCreationContext.getValueExtractorManager(), this.constraintCreationContext.getConstraintValidatorManager(), constraint.getDescriptor(), converted);
        }

        private String getPropertyName(ConstrainedElement constrainedElement) {
            if (constrainedElement.getKind() == ConstrainedElement.ConstrainedElementKind.FIELD) {
                return ((ConstrainedField)constrainedElement).getField().getPropertyName();
            }
            if (constrainedElement.getKind() == ConstrainedElement.ConstrainedElementKind.GETTER) {
                return ((ConstrainedExecutable)constrainedElement).getCallable().as(Property.class).getPropertyName();
            }
            return null;
        }

        @Override
        public PropertyMetaData build() {
            Set cascadables = this.cascadableBuilders.values().stream().map(b -> b.build()).collect(Collectors.toSet());
            return new PropertyMetaData(this.propertyName, this.propertyType, this.adaptOriginsAndImplicitGroups(this.getDirectConstraints()), this.adaptOriginsAndImplicitGroups(this.getContainerElementConstraints()), cascadables);
        }
    }
}

