/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.openapi.plugin.redoc;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B/\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010\bJ\b\u0010\u000b\u001a\u00020\u0003H\u0002J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\f\u0010\u0010\u001a\u00020\u0003*\u00020\u0003H\u0002R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0011"}, d2={"Lio/javalin/openapi/plugin/redoc/ReDocHandler;", "Lio/javalin/http/Handler;", "title", "", "documentationPath", "version", "routingPath", "basePath", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "multiplePathOperatorsRegex", "Lkotlin/text/Regex;", "createReDocUI", "handle", "", "context", "Lio/javalin/http/Context;", "removedDoubledPathOperators", "javalin-redoc-plugin"})
public final class ReDocHandler
implements Handler {
    @NotNull
    private final String title;
    @NotNull
    private final String documentationPath;
    @NotNull
    private final String version;
    @NotNull
    private final String routingPath;
    @Nullable
    private final String basePath;
    @NotNull
    private final Regex multiplePathOperatorsRegex;

    public ReDocHandler(@NotNull String title, @NotNull String documentationPath, @NotNull String version, @NotNull String routingPath, @Nullable String basePath) {
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(documentationPath, "documentationPath");
        Intrinsics.checkNotNullParameter(version, "version");
        Intrinsics.checkNotNullParameter(routingPath, "routingPath");
        this.title = title;
        this.documentationPath = documentationPath;
        this.version = version;
        this.routingPath = routingPath;
        this.basePath = basePath;
        this.multiplePathOperatorsRegex = new Regex("/+");
    }

    @Override
    public void handle(@NotNull Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        context.html(this.createReDocUI()).res().setCharacterEncoding("UTF-8");
    }

    private final String createReDocUI() {
        String string = this.basePath;
        if (string == null) {
            string = "";
        }
        String rootPath = string + this.routingPath;
        String publicBasePath = this.removedDoubledPathOperators(rootPath + "/webjars/redoc/" + this.version);
        String publicDocumentationPath = this.removedDoubledPathOperators(rootPath + this.documentationPath);
        return StringsKt.trimMargin$default("\n        |<!DOCTYPE html>\n        |<html>\n        |  <head>\n        |    <title>" + this.title + "</title>\n        |    <!-- Needed for adaptive design -->\n        |    <meta charset=\"utf-8\"/>\n        |    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n        |    <link href=\"https://fonts.googleapis.com/css?family=Montserrat:300,400,700|Roboto:300,400,700\" rel=\"stylesheet\">\n        |    <!-- ReDoc doesn't change outer page styles -->\n        |    <style>body{margin:0;padding:0;}</style>\n        |  </head>\n        |  <body>\n        |  <redoc id='redoc'></redoc>\n        |  <script src=\"" + publicBasePath + "/bundles/redoc.standalone.js\"></script>\n        |  <script>\n        |   window.onload = () => {\n        |     Redoc.init('" + publicDocumentationPath + "', {}, document.getElementById('redoc'))\n        |   }\n        | </script>\n        |  </body>\n        |</html>\n        |", null, 1, null);
    }

    private final String removedDoubledPathOperators(String $this$removedDoubledPathOperators) {
        CharSequence charSequence = $this$removedDoubledPathOperators;
        Regex regex2 = this.multiplePathOperatorsRegex;
        String string = "/";
        return regex2.replace(charSequence, string);
    }
}

