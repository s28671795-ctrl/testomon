/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.algorithm;

import javax.annotation.Nonnull;
import org.opensaml.xmlsec.algorithm.AlgorithmDescriptor;

public interface KeyLengthSpecifiedAlgorithm
extends AlgorithmDescriptor {
    @Nonnull
    public Integer getKeyLength();
}

