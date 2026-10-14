/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.xml.mapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import org.hibernate.validator.internal.engine.ConstraintCreationContext;
import org.hibernate.validator.internal.metadata.core.AnnotationProcessingOptionsImpl;
import org.hibernate.validator.internal.metadata.core.MetaConstraint;
import org.hibernate.validator.internal.metadata.descriptor.ConstraintDescriptorImpl;
import org.hibernate.validator.internal.metadata.location.ConstraintLocation;
import org.hibernate.validator.internal.properties.Callable;
import org.hibernate.validator.internal.xml.AbstractStaxBuilder;
import org.hibernate.validator.internal.xml.mapping.ClassLoadingHelper;
import org.hibernate.validator.internal.xml.mapping.ConstraintTypeStaxBuilder;
import org.hibernate.validator.internal.xml.mapping.DefaultPackageStaxBuilder;

class CrossParameterStaxBuilder
extends AbstractStaxBuilder {
    private static final String CROSS_PARAMETER_QNAME_LOCAL_PART = "cross-parameter";
    private static final QName IGNORE_ANNOTATIONS_QNAME = new QName("ignore-annotations");
    protected final ClassLoadingHelper classLoadingHelper;
    protected final ConstraintCreationContext constraintCreationContext;
    protected final DefaultPackageStaxBuilder defaultPackageStaxBuilder;
    protected final AnnotationProcessingOptionsImpl annotationProcessingOptions;
    protected Optional<Boolean> ignoreAnnotations;
    protected final List<ConstraintTypeStaxBuilder> constraintTypeStaxBuilders;

    CrossParameterStaxBuilder(ClassLoadingHelper classLoadingHelper, ConstraintCreationContext constraintCreationContext, DefaultPackageStaxBuilder defaultPackageStaxBuilder, AnnotationProcessingOptionsImpl annotationProcessingOptions) {
        this.classLoadingHelper = classLoadingHelper;
        this.defaultPackageStaxBuilder = defaultPackageStaxBuilder;
        this.constraintCreationContext = constraintCreationContext;
        this.annotationProcessingOptions = annotationProcessingOptions;
        this.constraintTypeStaxBuilders = new ArrayList<ConstraintTypeStaxBuilder>();
    }

    @Override
    protected String getAcceptableQName() {
        return CROSS_PARAMETER_QNAME_LOCAL_PART;
    }

    @Override
    protected void add(XMLEventReader xmlEventReader, XMLEvent xmlEvent) throws XMLStreamException {
        this.ignoreAnnotations = this.readAttribute(xmlEvent.asStartElement(), IGNORE_ANNOTATIONS_QNAME).map(Boolean::parseBoolean);
        ConstraintTypeStaxBuilder constraintTypeStaxBuilder = this.getNewConstraintTypeStaxBuilder();
        while (!xmlEvent.isEndElement() || !xmlEvent.asEndElement().getName().getLocalPart().equals(this.getAcceptableQName())) {
            xmlEvent = xmlEventReader.nextEvent();
            if (!constraintTypeStaxBuilder.process(xmlEventReader, xmlEvent)) continue;
            this.constraintTypeStaxBuilders.add(constraintTypeStaxBuilder);
            constraintTypeStaxBuilder = this.getNewConstraintTypeStaxBuilder();
        }
    }

    private ConstraintTypeStaxBuilder getNewConstraintTypeStaxBuilder() {
        return new ConstraintTypeStaxBuilder(this.classLoadingHelper, this.constraintCreationContext, this.defaultPackageStaxBuilder);
    }

    Set<MetaConstraint<?>> build(Callable callable) {
        ConstraintLocation constraintLocation = ConstraintLocation.forCrossParameter(callable);
        Set<MetaConstraint<?>> crossParameterConstraints = this.constraintTypeStaxBuilders.stream().map(builder -> builder.build(constraintLocation, ConstraintLocation.ConstraintLocationKind.of(callable.getConstrainedElementKind()), ConstraintDescriptorImpl.ConstraintType.CROSS_PARAMETER)).collect(Collectors.toSet());
        if (this.ignoreAnnotations.isPresent()) {
            this.annotationProcessingOptions.ignoreConstraintAnnotationsForCrossParameterConstraint(callable, this.ignoreAnnotations.get());
        }
        return crossParameterConstraints;
    }
}

