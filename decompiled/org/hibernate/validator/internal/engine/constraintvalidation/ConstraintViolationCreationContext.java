/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.engine.constraintvalidation;

import java.util.Map;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.hibernate.validator.internal.util.CollectionHelper;
import org.hibernate.validator.messageinterpolation.ExpressionLanguageFeatureLevel;

public class ConstraintViolationCreationContext {
    private final String message;
    private final ExpressionLanguageFeatureLevel expressionLanguageFeatureLevel;
    private final boolean customViolation;
    private final PathImpl propertyPath;
    private final Map<String, Object> messageParameters;
    private final Map<String, Object> expressionVariables;
    private final Object dynamicPayload;

    public ConstraintViolationCreationContext(String message, ExpressionLanguageFeatureLevel expressionLanguageFeatureLevel, boolean customViolation, PathImpl property, Map<String, Object> messageParameters, Map<String, Object> expressionVariables, Object dynamicPayload) {
        this.message = message;
        this.expressionLanguageFeatureLevel = expressionLanguageFeatureLevel;
        this.customViolation = customViolation;
        this.propertyPath = property;
        this.messageParameters = CollectionHelper.toImmutableMap(messageParameters);
        this.expressionVariables = CollectionHelper.toImmutableMap(expressionVariables);
        this.dynamicPayload = dynamicPayload;
    }

    public final String getMessage() {
        return this.message;
    }

    public ExpressionLanguageFeatureLevel getExpressionLanguageFeatureLevel() {
        return this.expressionLanguageFeatureLevel;
    }

    public boolean isCustomViolation() {
        return this.customViolation;
    }

    public final PathImpl getPath() {
        return this.propertyPath;
    }

    public Map<String, Object> getMessageParameters() {
        return this.messageParameters;
    }

    public Map<String, Object> getExpressionVariables() {
        return this.expressionVariables;
    }

    public Object getDynamicPayload() {
        return this.dynamicPayload;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("ConstraintViolationCreationContext{");
        sb.append("message='").append(this.message).append('\'');
        sb.append(", expressionLanguageFeatureLevel=").append((Object)this.expressionLanguageFeatureLevel);
        sb.append(", customViolation=").append(this.customViolation);
        sb.append(", propertyPath=").append(this.propertyPath);
        sb.append(", messageParameters=").append(this.messageParameters);
        sb.append(", expressionVariables=").append(this.expressionVariables);
        sb.append(", dynamicPayload=").append(this.dynamicPayload);
        sb.append('}');
        return sb.toString();
    }
}

