/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.xml.mapping;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.xml.namespace.QName;
import org.hibernate.validator.internal.engine.ConstraintCreationContext;
import org.hibernate.validator.internal.metadata.aggregated.CascadingMetaDataBuilder;
import org.hibernate.validator.internal.metadata.core.AnnotationProcessingOptionsImpl;
import org.hibernate.validator.internal.metadata.core.MetaConstraint;
import org.hibernate.validator.internal.metadata.raw.ConfigurationSource;
import org.hibernate.validator.internal.metadata.raw.ConstrainedExecutable;
import org.hibernate.validator.internal.metadata.raw.ConstrainedParameter;
import org.hibernate.validator.internal.properties.javabean.JavaBeanConstructor;
import org.hibernate.validator.internal.properties.javabean.JavaBeanHelper;
import org.hibernate.validator.internal.util.CollectionHelper;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.hibernate.validator.internal.xml.mapping.AbstractConstrainedExecutableElementStaxBuilder;
import org.hibernate.validator.internal.xml.mapping.ClassLoadingHelper;
import org.hibernate.validator.internal.xml.mapping.ConstrainedParameterStaxBuilder;
import org.hibernate.validator.internal.xml.mapping.DefaultPackageStaxBuilder;

class ConstrainedConstructorStaxBuilder
extends AbstractConstrainedExecutableElementStaxBuilder {
    private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
    private static final String METHOD_QNAME_LOCAL_PART = "constructor";

    ConstrainedConstructorStaxBuilder(ClassLoadingHelper classLoadingHelper, ConstraintCreationContext constraintCreationContext, DefaultPackageStaxBuilder defaultPackageStaxBuilder, AnnotationProcessingOptionsImpl annotationProcessingOptions) {
        super(classLoadingHelper, constraintCreationContext, defaultPackageStaxBuilder, annotationProcessingOptions);
    }

    @Override
    Optional<QName> getMainAttributeValueQname() {
        return Optional.empty();
    }

    @Override
    protected String getAcceptableQName() {
        return METHOD_QNAME_LOCAL_PART;
    }

    public String getMethodName() {
        return this.mainAttributeValue;
    }

    ConstrainedExecutable build(JavaBeanHelper javaBeanHelper, Class<?> beanClass, List<JavaBeanConstructor> alreadyProcessedConstructors) {
        Class[] parameterTypes = (Class[])this.constrainedParameterStaxBuilders.stream().map(builder -> builder.getParameterType(beanClass)).toArray(Class[]::new);
        JavaBeanConstructor javaBeanConstructor = this.findConstructor(javaBeanHelper, beanClass, parameterTypes);
        if (alreadyProcessedConstructors.contains(javaBeanConstructor)) {
            throw LOG.getConstructorIsDefinedTwiceInMappingXmlForBeanException(javaBeanConstructor, beanClass);
        }
        alreadyProcessedConstructors.add(javaBeanConstructor);
        if (this.ignoreAnnotations.isPresent()) {
            this.annotationProcessingOptions.ignoreConstraintAnnotationsOnMember(javaBeanConstructor, (Boolean)this.ignoreAnnotations.get());
        }
        ArrayList<ConstrainedParameter> constrainedParameters = CollectionHelper.newArrayList(this.constrainedParameterStaxBuilders.size());
        for (int index = 0; index < this.constrainedParameterStaxBuilders.size(); ++index) {
            ConstrainedParameterStaxBuilder builder2 = (ConstrainedParameterStaxBuilder)this.constrainedParameterStaxBuilders.get(index);
            constrainedParameters.add(builder2.build(javaBeanConstructor, index));
        }
        Set<MetaConstraint<?>> crossParameterConstraints = this.getCrossParameterStaxBuilder().map(builder -> builder.build(javaBeanConstructor)).orElse(Collections.emptySet());
        HashSet returnValueConstraints = new HashSet();
        HashSet returnValueTypeArgumentConstraints = new HashSet();
        CascadingMetaDataBuilder cascadingMetaDataBuilder = this.getReturnValueStaxBuilder().map(builder -> builder.build(javaBeanConstructor, returnValueConstraints, returnValueTypeArgumentConstraints)).orElse(CascadingMetaDataBuilder.nonCascading());
        return new ConstrainedExecutable(ConfigurationSource.XML, javaBeanConstructor, constrainedParameters, crossParameterConstraints, returnValueConstraints, returnValueTypeArgumentConstraints, cascadingMetaDataBuilder);
    }

    private JavaBeanConstructor findConstructor(JavaBeanHelper javaBeanHelper, Class<?> beanClass, Class<?>[] parameterTypes) {
        return javaBeanHelper.findDeclaredConstructor(beanClass, parameterTypes).orElseThrow(() -> LOG.getBeanDoesNotContainConstructorException(beanClass, parameterTypes));
    }
}

