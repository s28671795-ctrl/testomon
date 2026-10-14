/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.xml.mapping;

import java.lang.invoke.MethodHandles;
import java.util.Collections;
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
import org.hibernate.validator.internal.metadata.raw.ConstrainedExecutable;
import org.hibernate.validator.internal.properties.javabean.JavaBeanGetter;
import org.hibernate.validator.internal.properties.javabean.JavaBeanHelper;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.hibernate.validator.internal.xml.mapping.AbstractConstrainedElementStaxBuilder;
import org.hibernate.validator.internal.xml.mapping.ClassLoadingHelper;
import org.hibernate.validator.internal.xml.mapping.ContainerElementTypeConfigurationBuilder;
import org.hibernate.validator.internal.xml.mapping.DefaultPackageStaxBuilder;

class ConstrainedGetterStaxBuilder
extends AbstractConstrainedElementStaxBuilder {
    private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
    private static final QName NAME_QNAME = new QName("name");
    private static final String GETTER_QNAME_LOCAL_PART = "getter";

    ConstrainedGetterStaxBuilder(ClassLoadingHelper classLoadingHelper, ConstraintCreationContext constraintCreationContext, DefaultPackageStaxBuilder defaultPackageStaxBuilder, AnnotationProcessingOptionsImpl annotationProcessingOptions) {
        super(classLoadingHelper, constraintCreationContext, defaultPackageStaxBuilder, annotationProcessingOptions);
    }

    @Override
    Optional<QName> getMainAttributeValueQname() {
        return Optional.of(NAME_QNAME);
    }

    @Override
    protected String getAcceptableQName() {
        return GETTER_QNAME_LOCAL_PART;
    }

    ConstrainedExecutable build(JavaBeanHelper javaBeanHelper, Class<?> beanClass, List<String> alreadyProcessedGetterNames) {
        if (alreadyProcessedGetterNames.contains(this.mainAttributeValue)) {
            throw LOG.getIsDefinedTwiceInMappingXmlForBeanException(this.mainAttributeValue, beanClass);
        }
        alreadyProcessedGetterNames.add(this.mainAttributeValue);
        JavaBeanGetter javaBeanGetter = ConstrainedGetterStaxBuilder.findGetter(javaBeanHelper, beanClass, this.mainAttributeValue);
        ConstraintLocation constraintLocation = ConstraintLocation.forGetter(javaBeanGetter);
        Set<MetaConstraint<?>> metaConstraints = this.constraintTypeStaxBuilders.stream().map(builder -> builder.build(constraintLocation, ConstraintLocation.ConstraintLocationKind.GETTER, null)).collect(Collectors.toSet());
        ContainerElementTypeConfigurationBuilder.ContainerElementTypeConfiguration containerElementTypeConfiguration = this.getContainerElementTypeConfiguration(javaBeanGetter.getType(), constraintLocation);
        ConstrainedExecutable constrainedGetter = new ConstrainedExecutable(ConfigurationSource.XML, javaBeanGetter, Collections.emptyList(), Collections.emptySet(), metaConstraints, containerElementTypeConfiguration.getMetaConstraints(), this.getCascadingMetaData(containerElementTypeConfiguration.getTypeParametersCascadingMetaData(), javaBeanGetter.getType()));
        if (this.ignoreAnnotations.isPresent()) {
            this.annotationProcessingOptions.ignoreConstraintAnnotationsOnMember(javaBeanGetter, (Boolean)this.ignoreAnnotations.get());
        }
        return constrainedGetter;
    }

    private static JavaBeanGetter findGetter(JavaBeanHelper javaBeanHelper, Class<?> beanClass, String getterName) {
        Optional<JavaBeanGetter> property = javaBeanHelper.findGetter(beanClass, getterName);
        return property.orElseThrow(() -> LOG.getBeanDoesNotContainThePropertyException(beanClass, getterName));
    }
}

