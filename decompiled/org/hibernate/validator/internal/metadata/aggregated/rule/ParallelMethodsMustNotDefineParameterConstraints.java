/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.metadata.aggregated.rule;

import org.hibernate.validator.internal.metadata.aggregated.rule.MethodConfigurationRule;
import org.hibernate.validator.internal.metadata.raw.ConstrainedExecutable;

public class ParallelMethodsMustNotDefineParameterConstraints
extends MethodConfigurationRule {
    @Override
    public void apply(ConstrainedExecutable method2, ConstrainedExecutable otherMethod) {
        if (this.isDefinedOnParallelType(method2, otherMethod) && (method2.hasParameterConstraints() || otherMethod.hasParameterConstraints())) {
            throw LOG.getParameterConstraintsDefinedInMethodsFromParallelTypesException(method2.getCallable(), otherMethod.getCallable());
        }
    }
}

