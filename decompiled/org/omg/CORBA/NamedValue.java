/*
 * Decompiled with CFR 0.152.
 */
package org.omg.CORBA;

import org.omg.CORBA.Any;

public abstract class NamedValue {
    public abstract String name();

    public abstract Any value();

    public abstract int flags();
}

