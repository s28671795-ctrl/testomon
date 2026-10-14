/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import java.io.Serializable;
import org.jetbrains.annotations.Nullable;
import org.jooq.Configuration;

public interface Attachable
extends Serializable {
    public void attach(Configuration var1);

    public void detach();

    @Nullable
    public Configuration configuration();
}

