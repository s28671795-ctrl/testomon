/*
 * Decompiled with CFR 0.152.
 */
package org.codehaus.janino;

import org.codehaus.commons.compiler.CompileException;
import org.codehaus.janino.ITypeVariableOrIClass;

public interface ITypeVariable
extends ITypeVariableOrIClass {
    public String getName();

    public ITypeVariableOrIClass[] getBounds() throws CompileException;
}

