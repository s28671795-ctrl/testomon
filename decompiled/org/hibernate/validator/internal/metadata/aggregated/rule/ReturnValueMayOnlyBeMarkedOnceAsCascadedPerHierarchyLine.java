/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.metadata.aggregated.rule;

import org.hibernate.validator.internal.metadata.aggregated.rule.MethodConfigurationRule;
import org.hibernate.validator.internal.metadata.raw.ConstrainedExecutable;

public class ReturnValueMayOnlyBeMarkedOnceAsCascadedPerHierarchyLine
extends MethodConfigurationRule {
    @Override
    public void apply(ConstrainedExecutable method2, ConstrainedExecutable otherMethod) {
        if (method2.getCascadingMetaDataBuilder().isMarkedForCascadingOnAnnotatedObjectOrContainerElements() && otherMethod.getCascadingMetaDataBuilder().isMarkedForCascadingOnAnnotatedObjectOrContainerElements() && (this.isDefinedOnSubType(method2, otherMethod) || this.isDefinedOnSubType(otherMethod, method2))) {
            throw LOG.getMethodReturnValueMustNotBeMarkedMoreThanOnceForCascadedValidationException(method2.getCallable(), otherMethod.getCallable());
        }
    }
}

