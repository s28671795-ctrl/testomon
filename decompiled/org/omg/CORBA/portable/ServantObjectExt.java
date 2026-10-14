/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA.portable;

import org.omg.CORBA.portable.ServantObject;

public abstract class ServantObjectExt
extends ServantObject {
    public abstract void normalCompletion();

    public abstract void exceptionalCompletion(Throwable var1);
}

