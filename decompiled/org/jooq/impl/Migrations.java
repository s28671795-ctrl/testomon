/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.util.Collections;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jooq.Commits;
import org.jooq.Configuration;
import org.jooq.ContentType;
import org.jooq.File;
import org.jooq.impl.CommitImpl;
import org.jooq.impl.CommitsImpl;
import org.jooq.impl.FileImpl;

@ApiStatus.Experimental
public final class Migrations {
    @ApiStatus.Experimental
    @NotNull
    public static final Commits commits(Configuration configuration) {
        return new CommitsImpl(configuration, new CommitImpl(configuration, "init", "init", Collections.emptyList(), Collections.emptyList()));
    }

    @ApiStatus.Experimental
    @NotNull
    public static final File file(String path, String content, ContentType type) {
        return new FileImpl(path, content, type);
    }
}

