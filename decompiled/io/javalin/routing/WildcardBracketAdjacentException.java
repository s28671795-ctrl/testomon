/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.routing;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\u0018\u00002\u00060\u0001j\u0002`\u0002B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0004\u00a2\u0006\u0002\u0010\u0006R\u0011\u0010\u0005\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b\u00a8\u0006\t"}, d2={"Lio/javalin/routing/WildcardBracketAdjacentException;", "Ljava/lang/IllegalArgumentException;", "Lkotlin/IllegalArgumentException;", "segment", "", "path", "(Ljava/lang/String;Ljava/lang/String;)V", "getPath", "()Ljava/lang/String;", "javalin"})
public final class WildcardBracketAdjacentException
extends IllegalArgumentException {
    @NotNull
    private final String path;

    public WildcardBracketAdjacentException(@NotNull String segment, @NotNull String path) {
        Intrinsics.checkNotNullParameter(segment, "segment");
        Intrinsics.checkNotNullParameter(path, "path");
        super("Wildcard and a path parameter bracket are adjacent in segment '" + segment + "' of path '" + path + "'. This is forbidden");
        this.path = path;
    }

    @NotNull
    public final String getPath() {
        return this.path;
    }
}

