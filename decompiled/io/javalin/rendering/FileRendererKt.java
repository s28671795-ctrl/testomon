/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.rendering;

import io.javalin.http.Context;
import io.javalin.rendering.FileRenderer;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=2, xi=48, d1={"\u0000\u0012\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\n\u0010\u0002\u001a\u00020\u0003*\u00020\u0004\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2={"FILE_RENDERER_KEY", "", "fileRenderer", "Lio/javalin/rendering/FileRenderer;", "Lio/javalin/http/Context;", "javalin"})
public final class FileRendererKt {
    @NotNull
    public static final String FILE_RENDERER_KEY = "javalin-file-renderer";

    @NotNull
    public static final FileRenderer fileRenderer(@NotNull Context $this$fileRenderer) {
        Intrinsics.checkNotNullParameter($this$fileRenderer, "<this>");
        return (FileRenderer)$this$fileRenderer.appAttribute(FILE_RENDERER_KEY);
    }
}

