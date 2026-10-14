/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.xml.mapping;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Type;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.ValidationException;
import javax.xml.namespace.QName;
import org.hibernate.validator.internal.engine.ConstraintCreationContext;
import org.hibernate.validator.internal.metadata.core.AnnotationProcessingOptionsImpl;
import org.hibernate.validator.internal.metadata.core.MetaConstraint;
import org.hibernate.validator.internal.metadata.location.ConstraintLocation;
import org.hibernate.validator.internal.metadata.raw.ConfigurationSource;
import org.hibernate.validator.internal.metadata.raw.ConstrainedParameter;
import org.hibernate.validator.internal.properties.Callable;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.hibernate.validator.internal.xml.mapping.AbstractConstrainedElementStaxBuilder;
import org.hibernate.validator.internal.xml.mapping.ClassLoadingHelper;
import org.hibernate.validator.internal.xml.mapping.ContainerElementTypeConfigurationBuilder;
import org.hibernate.validator.internal.xml.mapping.DefaultPackageStaxBuilder;

class ConstrainedParameterStaxBuilder
extends AbstractConstrainedElementStaxBuilder {
    private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
    private static final String PARAMETER_QNAME_LOCAL_PART = "parameter";
    private static final QName TYPE_QNAME = new QName("type");

    ConstrainedParameterStaxBuilder(ClassLoadingHelper classLoadingHelper, ConstraintCreationContext constraintCreationContext, DefaultPackageStaxBuilder defaultPackageStaxBuilder, AnnotationProcessingOptionsImpl annotationProcessingOptions) {
        super(classLoadingHelper, constraintCreationContext, defaultPackageStaxBuilder, annotationProcessingOptions);
    }

    @Override
    Optional<QName> getMainAttributeValueQname() {
        return Optional.of(TYPE_QNAME);
    }

    @Override
    protected String getAcceptableQName() {
        return PARAMETER_QNAME_LOCAL_PART;
    }

    public Class<?> getParameterType(Class<?> beanClass) {
        try {
            return this.classLoadingHelper.loadClass(this.mainAttributeValue, this.defaultPackageStaxBuilder.build().orElse(""));
        }
        catch (ValidationException e) {
            throw LOG.getInvalidParameterTypeException(this.mainAttributeValue, beanClass);
        }
    }

    ConstrainedParameter build(Callable callable, int index) {
        ConstraintLocation constraintLocation = ConstraintLocation.forParameter(callable, index);
        Type type = callable.getParameterGenericType(index);
        Set<MetaConstraint<?>> metaConstraints = this.constraintTypeStaxBuilders.stream().map(builder -> builder.build(constraintLocation, ConstraintLocation.ConstraintLocationKind.PARAMETER, null)).collect(Collectors.toSet());
        ContainerElementTypeConfigurationBuilder.ContainerElementTypeConfiguration containerElementTypeConfiguration = this.getContainerElementTypeConfiguration(type, constraintLocation);
        if (this.ignoreAnnotations.isPresent()) {
            this.annotationProcessingOptions.ignoreConstraintAnnotationsOnParameter(callable, index, (Boolean)this.ignoreAnnotations.get());
        }
        ConstrainedParameter constrainedParameter = new ConstrainedParameter(ConfigurationSource.XML, callable, type, index, metaConstraints, containerElementTypeConfiguration.getMetaConstraints(), this.getCascadingMetaData(containerElementTypeConfiguration.getTypeParametersCascadingMetaData(), type));
        return constrainedParameter;
    }
}

