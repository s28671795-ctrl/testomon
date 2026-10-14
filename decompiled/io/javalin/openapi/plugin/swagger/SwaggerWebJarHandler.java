/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.openapi.plugin.swagger;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.openapi.plugin.swagger.SwaggerPlugin;
import java.io.InputStream;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.eclipse.jetty.http.MimeTypes;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 7, 1}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\t"}, d2={"Lio/javalin/openapi/plugin/swagger/SwaggerWebJarHandler;", "Lio/javalin/http/Handler;", "swaggerWebJarPath", "", "(Ljava/lang/String;)V", "handle", "", "context", "Lio/javalin/http/Context;", "javalin-swagger-plugin"})
public final class SwaggerWebJarHandler
implements Handler {
    @NotNull
    private final String swaggerWebJarPath;

    public SwaggerWebJarHandler(@NotNull String swaggerWebJarPath) {
        Intrinsics.checkNotNullParameter(swaggerWebJarPath, "swaggerWebJarPath");
        this.swaggerWebJarPath = swaggerWebJarPath;
    }

    @Override
    public void handle(@NotNull Context context) {
        block1: {
            Intrinsics.checkNotNullParameter(context, "context");
            InputStream resource = SwaggerPlugin.class.getResourceAsStream("/META-INF/resources" + this.swaggerWebJarPath + StringsKt.replaceFirst$default(StringsKt.replaceFirst$default(context.path(), context.contextPath(), "", false, 4, null), this.swaggerWebJarPath, "", false, 4, null));
            if (resource == null) {
                context.status(404);
                return;
            }
            context.result(resource);
            context.res().setCharacterEncoding("UTF-8");
            String string = MimeTypes.getDefaultMimeByExtension(context.path());
            if (string == null) break block1;
            String it = string;
            boolean bl = false;
            context.contentType(it);
        }
    }
}

