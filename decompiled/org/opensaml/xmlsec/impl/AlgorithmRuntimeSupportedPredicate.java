/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.impl;

import com.google.common.base.Predicate;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.opensaml.xmlsec.algorithm.AlgorithmRegistry;

public class AlgorithmRuntimeSupportedPredicate
implements Predicate<String> {
    private AlgorithmRegistry registry;

    public AlgorithmRuntimeSupportedPredicate(AlgorithmRegistry algorithmRegistry) {
        this.registry = Constraint.isNotNull(algorithmRegistry, "AlgorithmRegistry may not be null");
    }

    @Override
    public boolean apply(@Nullable String input) {
        return this.registry.isRuntimeSupported(input);
    }
}

