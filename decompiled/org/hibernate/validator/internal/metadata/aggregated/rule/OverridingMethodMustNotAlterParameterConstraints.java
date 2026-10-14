/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.metadata.aggregated.rule;

import org.hibernate.validator.internal.metadata.aggregated.rule.MethodConfigurationRule;
import org.hibernate.validator.internal.metadata.raw.ConstrainedExecutable;

public class OverridingMethodMustNotAlterParameterConstraints
extends MethodConfigurationRule {
    @Override
    public void apply(ConstrainedExecutable method2, ConstrainedExecutable otherMethod) {
        if (this.isDefinedOnSubType(method2, otherMethod) && otherMethod.hasParameterConstraints() && !method2.isEquallyParameterConstrained(otherMethod)) {
            throw LOG.getParameterConfigurationAlteredInSubTypeException(method2.getCallable(), otherMethod.getCallable());
        }
    }
}

