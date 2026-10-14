/*
 * Decompiled with CFR 0.152.
 */
package org.jooq;

import org.jetbrains.annotations.NotNull;
import org.jooq.File;
import org.jooq.Version;

public interface Files
extends Iterable<File> {
    @NotNull
    public Version from();

    @NotNull
    public Version to();
}

