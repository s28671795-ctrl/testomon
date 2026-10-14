/*
 * Decompiled with CFR 0.152.
 */
package org.hibernate.validator.internal.metadata.aggregated.rule;

import org.hibernate.validator.internal.metadata.aggregated.rule.MethodConfigurationRule;
import org.hibernate.validator.internal.metadata.raw.ConstrainedExecutable;

public class VoidMethodsMustNotBeReturnValueConstrained
extends MethodConfigurationRule {
    @Override
    public void apply(ConstrainedExecutable executable, ConstrainedExecutable otherExecutable) {
        if (!(executable.getCallable().hasReturnValue() || executable.getConstraints().isEmpty() && !executable.getCascadingMetaDataBuilder().isMarkedForCascadingOnAnnotatedObjectOrContainerElements())) {
            throw LOG.getVoidMethodsMustNotBeConstrainedException(executable.getCallable());
        }
    }
}

