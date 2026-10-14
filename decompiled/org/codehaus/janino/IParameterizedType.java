/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.janino;

import org.codehaus.janino.IType;

public interface IParameterizedType
extends IType {
    public IType[] getActualTypeArguments();

    public IType getRawType();
}

