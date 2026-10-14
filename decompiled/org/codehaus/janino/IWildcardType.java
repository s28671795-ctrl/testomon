/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.janino;

import org.codehaus.commons.nullanalysis.Nullable;
import org.codehaus.janino.IType;

public interface IWildcardType
extends IType {
    public IType getUpperBound();

    @Nullable
    public IType getLowerBound();
}

