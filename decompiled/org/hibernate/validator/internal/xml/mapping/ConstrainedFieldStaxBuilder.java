/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.xml.mapping;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.xml.namespace.QName;
import org.hibernate.validator.internal.engine.ConstraintCreationContext;
import org.hibernate.validator.internal.metadata.core.AnnotationProcessingOptionsImpl;
import org.hibernate.validator.internal.metadata.core.MetaConstraint;
import org.hibernate.validator.internal.metadata.location.ConstraintLocation;
import org.hibernate.validator.internal.metadata.raw.ConfigurationSource;
import org.hibernate.validator.internal.metadata.raw.ConstrainedField;
import org.hibernate.validator.internal.properties.javabean.JavaBeanField;
import org.hibernate.validator.internal.properties.javabean.JavaBeanHelper;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.hibernate.validator.internal.xml.mapping.AbstractConstrainedElementStaxBuilder;
import org.hibernate.validator.internal.xml.mapping.ClassLoadingHelper;
import org.hibernate.validator.internal.xml.mapping.ContainerElementTypeConfigurationBuilder;
import org.hibernate.validator.internal.xml.mapping.DefaultPackageStaxBuilder;

class ConstrainedFieldStaxBuilder
extends AbstractConstrainedElementStaxBuilder {
    private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
    private static final String FIELD_QNAME_LOCAL_PART = "field";
    private static final QName NAME_QNAME = new QName("name");

    ConstrainedFieldStaxBuilder(ClassLoadingHelper classLoadingHelper, ConstraintCreationContext constraintCreationContext, DefaultPackageStaxBuilder defaultPackageStaxBuilder, AnnotationProcessingOptionsImpl annotationProcessingOptions) {
        super(classLoadingHelper, constraintCreationContext, defaultPackageStaxBuilder, annotationProcessingOptions);
    }

    @Override
    Optional<QName> getMainAttributeValueQname() {
        return Optional.of(NAME_QNAME);
    }

    @Override
    protected String getAcceptableQName() {
        return FIELD_QNAME_LOCAL_PART;
    }

    ConstrainedField build(JavaBeanHelper javaBeanHelper, Class<?> beanClass, List<String> alreadyProcessedFieldNames) {
        if (alreadyProcessedFieldNames.contains(this.mainAttributeValue)) {
            throw LOG.getIsDefinedTwiceInMappingXmlForBeanException(this.mainAttributeValue, beanClass);
        }
        alreadyProcessedFieldNames.add(this.mainAttributeValue);
        JavaBeanField javaBeanField = ConstrainedFieldStaxBuilder.findField(javaBeanHelper, beanClass, this.mainAttributeValue);
        ConstraintLocation constraintLocation = ConstraintLocation.forField(javaBeanField);
        Set<MetaConstraint<?>> metaConstraints = this.constraintTypeStaxBuilders.stream().map(builder -> builder.build(constraintLocation, ConstraintLocation.ConstraintLocationKind.FIELD, null)).collect(Collectors.toSet());
        ContainerElementTypeConfigurationBuilder.ContainerElementTypeConfiguration containerElementTypeConfiguration = this.getContainerElementTypeConfiguration(javaBeanField.getType(), constraintLocation);
        ConstrainedField constrainedField = new ConstrainedField(ConfigurationSource.XML, javaBeanField, metaConstraints, containerElementTypeConfiguration.getMetaConstraints(), this.getCascadingMetaData(containerElementTypeConfiguration.getTypeParametersCascadingMetaData(), javaBeanField.getType()));
        if (this.ignoreAnnotations.isPresent()) {
            this.annotationProcessingOptions.ignoreConstraintAnnotationsOnMember(javaBeanField, (Boolean)this.ignoreAnnotations.get());
        }
        return constrainedField;
    }

    private static JavaBeanField findField(JavaBeanHelper javaBeanHelper, Class<?> beanClass, String fieldName) {
        return javaBeanHelper.findDeclaredField(beanClass, fieldName).orElseThrow(() -> LOG.getBeanDoesNotContainTheFieldException(beanClass, fieldName));
    }
}

