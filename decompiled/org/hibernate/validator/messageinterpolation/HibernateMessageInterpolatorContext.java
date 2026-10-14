/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.messageinterpolation;

import java.util.Map;
import javax.validation.MessageInterpolator;
import javax.validation.Path;
import org.hibernate.validator.messageinterpolation.ExpressionLanguageFeatureLevel;

public interface HibernateMessageInterpolatorContext
extends MessageInterpolator.Context {
    public Class<?> getRootBeanType();

    public Map<String, Object> getMessageParameters();

    public Map<String, Object> getExpressionVariables();

    public Path getPropertyPath();

    public ExpressionLanguageFeatureLevel getExpressionLanguageFeatureLevel();
}

