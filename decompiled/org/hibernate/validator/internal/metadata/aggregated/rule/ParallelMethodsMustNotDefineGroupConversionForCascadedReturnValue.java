/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.metadata.aggregated.rule;

import org.hibernate.validator.internal.metadata.aggregated.rule.MethodConfigurationRule;
import org.hibernate.validator.internal.metadata.raw.ConstrainedExecutable;

public class ParallelMethodsMustNotDefineGroupConversionForCascadedReturnValue
extends MethodConfigurationRule {
    @Override
    public void apply(ConstrainedExecutable method2, ConstrainedExecutable otherMethod) {
        boolean hasGroupConversions;
        boolean isCascaded = method2.getCascadingMetaDataBuilder().isMarkedForCascadingOnAnnotatedObjectOrContainerElements() || otherMethod.getCascadingMetaDataBuilder().isMarkedForCascadingOnAnnotatedObjectOrContainerElements();
        boolean bl = hasGroupConversions = method2.getCascadingMetaDataBuilder().hasGroupConversionsOnAnnotatedObjectOrContainerElements() || otherMethod.getCascadingMetaDataBuilder().hasGroupConversionsOnAnnotatedObjectOrContainerElements();
        if (this.isDefinedOnParallelType(method2, otherMethod) && isCascaded && hasGroupConversions) {
            throw LOG.getMethodsFromParallelTypesMustNotDefineGroupConversionsForCascadedReturnValueException(method2.getCallable(), otherMethod.getCallable());
        }
    }
}

