/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.algorithm;

import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import org.opensaml.xmlsec.algorithm.AlgorithmDescriptor;

public interface KeySpecifiedAlgorithm
extends AlgorithmDescriptor {
    @Nonnull
    @NotEmpty
    public String getKey();
}

