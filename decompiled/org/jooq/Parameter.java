/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jooq.Named;
import org.jooq.ParamMode;
import org.jooq.Typed;
import org.jooq.Variable;

public interface Parameter<T>
extends Variable<T>,
Named,
Typed<T> {
    public boolean isDefaulted();

    public boolean isUnnamed();

    public ParamMode getParamMode();
}

