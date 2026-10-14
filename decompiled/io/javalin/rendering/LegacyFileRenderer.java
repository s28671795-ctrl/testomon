/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.rendering;

import io.javalin.http.Context;
import io.javalin.rendering.FileRenderer;
import io.javalin.rendering.JavalinRenderer;
import java.util.Map;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Deprecated(message="To be removed in Javalin 6")
@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J.\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00042\u0014\u0010\u0006\u001a\u0010\u0012\u0004\u0012\u00020\u0004\u0012\u0006\u0012\u0004\u0018\u00010\b0\u00072\u0006\u0010\t\u001a\u00020\nH\u0016\u00a8\u0006\u000b"}, d2={"Lio/javalin/rendering/LegacyFileRenderer;", "Lio/javalin/rendering/FileRenderer;", "()V", "render", "", "filePath", "model", "", "", "context", "Lio/javalin/http/Context;", "javalin"})
public final class LegacyFileRenderer
implements FileRenderer {
    public LegacyFileRenderer() {
        JavalinRenderer.INSTANCE.loadFileRenderers$javalin();
    }

    @Override
    @NotNull
    public String render(@NotNull String filePath, @NotNull Map<String, ? extends Object> model, @NotNull Context context) {
        Intrinsics.checkNotNullParameter(filePath, "filePath");
        Intrinsics.checkNotNullParameter(model, "model");
        Intrinsics.checkNotNullParameter(context, "context");
        return JavalinRenderer.renderBasedOnExtension(filePath, model, context);
    }
}

