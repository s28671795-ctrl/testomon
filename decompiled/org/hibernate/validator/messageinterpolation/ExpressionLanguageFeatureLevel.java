/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.messageinterpolation;

import org.hibernate.validator.Incubating;

@Incubating
public enum ExpressionLanguageFeatureLevel {
    DEFAULT("default"),
    NONE("none"),
    VARIABLES("variables"),
    BEAN_PROPERTIES("bean-properties"),
    BEAN_METHODS("bean-methods");

    private final String externalRepresentation;

    private ExpressionLanguageFeatureLevel(String externalRepresentation) {
        this.externalRepresentation = externalRepresentation;
    }

    public static ExpressionLanguageFeatureLevel of(String value) {
        for (ExpressionLanguageFeatureLevel level : ExpressionLanguageFeatureLevel.values()) {
            if (!level.externalRepresentation.equals(value)) continue;
            return level;
        }
        return ExpressionLanguageFeatureLevel.valueOf(value);
    }

    public static ExpressionLanguageFeatureLevel interpretDefaultForConstraints(ExpressionLanguageFeatureLevel value) {
        if (value == DEFAULT) {
            return BEAN_PROPERTIES;
        }
        return value;
    }

    public static ExpressionLanguageFeatureLevel interpretDefaultForCustomViolations(ExpressionLanguageFeatureLevel value) {
        if (value == DEFAULT) {
            return VARIABLES;
        }
        return value;
    }
}

