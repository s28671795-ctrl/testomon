/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.validation;

import java.util.Collections;
import java.util.Map;
import javax.validation.ConstraintValidatorFactory;
import javax.validation.MessageInterpolator;
import javax.validation.ParameterNameProvider;
import javax.validation.TraversableResolver;

public class ValidationConfiguration {
    private ParameterNameProvider parameterNameProvider;
    private MessageInterpolator messageInterpolator;
    private TraversableResolver traversableResolver;
    private ConstraintValidatorFactory constraintValidatorFactory;
    private Map<String, String> properties = Collections.emptyMap();

    public ValidationConfiguration() {
    }

    public ValidationConfiguration(ParameterNameProvider parameterNameProvider) {
        this.parameterNameProvider = parameterNameProvider;
    }

    public ParameterNameProvider getParameterNameProvider() {
        return this.parameterNameProvider;
    }

    public void setParameterNameProvider(ParameterNameProvider parameterNameProvider) {
        this.parameterNameProvider = parameterNameProvider;
    }

    public Map<String, String> getProperties() {
        return this.properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    public MessageInterpolator getMessageInterpolator() {
        return this.messageInterpolator;
    }

    public void setMessageInterpolator(MessageInterpolator messageInterpolator) {
        this.messageInterpolator = messageInterpolator;
    }

    public TraversableResolver getTraversableResolver() {
        return this.traversableResolver;
    }

    public void setTraversableResolver(TraversableResolver traversableResolver) {
        this.traversableResolver = traversableResolver;
    }

    public ConstraintValidatorFactory getConstraintValidatorFactory() {
        return this.constraintValidatorFactory;
    }

    public void setConstraintValidatorFactory(ConstraintValidatorFactory constraintValidatorFactory) {
        this.constraintValidatorFactory = constraintValidatorFactory;
    }
}

