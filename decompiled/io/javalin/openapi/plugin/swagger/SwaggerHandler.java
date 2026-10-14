/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.openapi.plugin.swagger;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 7, 1}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B/\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\bJ\b\u0010\t\u001a\u00020\u0003H\u0002J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016R\u000e\u0010\u0007\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000e"}, d2={"Lio/javalin/openapi/plugin/swagger/SwaggerHandler;", "Lio/javalin/http/Handler;", "title", "", "documentationPath", "swaggerVersion", "validatorUrl", "basePath", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "createSwaggerUiHtml", "handle", "", "context", "Lio/javalin/http/Context;", "javalin-swagger-plugin"})
public final class SwaggerHandler
implements Handler {
    @NotNull
    private final String title;
    @NotNull
    private final String documentationPath;
    @NotNull
    private final String swaggerVersion;
    @Nullable
    private final String validatorUrl;
    @NotNull
    private final String basePath;

    public SwaggerHandler(@NotNull String title, @NotNull String documentationPath, @NotNull String swaggerVersion, @Nullable String validatorUrl, @NotNull String basePath) {
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(documentationPath, "documentationPath");
        Intrinsics.checkNotNullParameter(swaggerVersion, "swaggerVersion");
        Intrinsics.checkNotNullParameter(basePath, "basePath");
        this.title = title;
        this.documentationPath = documentationPath;
        this.swaggerVersion = swaggerVersion;
        this.validatorUrl = validatorUrl;
        this.basePath = basePath;
    }

    @Override
    public void handle(@NotNull Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        context.html(this.createSwaggerUiHtml()).res().setCharacterEncoding("UTF-8");
    }

    private final String createSwaggerUiHtml() {
        String publicSwaggerAssetsPath = StringsKt.replace$default("/" + this.basePath + "/webjars/swagger-ui/" + this.swaggerVersion, "//", "/", false, 4, null);
        String publicDocumentationPath = StringsKt.replace$default(this.basePath + this.documentationPath, "//", "/", false, 4, null);
        String html = StringsKt.trimIndent("\n        <!-- HTML for static distribution bundle build -->\n        <!DOCTYPE html>\n        <html lang=\"en\">\n            <head>\n                <meta charset=\"UTF-8\">\n                <title>" + this.title + "</title>\n                <link rel=\"stylesheet\" type=\"text/css\" href=\"" + publicSwaggerAssetsPath + "/swagger-ui.css\" >\n                <link rel=\"icon\" type=\"image/png\" href=\"" + publicSwaggerAssetsPath + "/favicon-32x32.png\" sizes=\"32x32\" />\n                <style>\n                    html {\n                        box-sizing: border-box;\n                        overflow: -moz-scrollbars-vertical;\n                        overflow-y: scroll;\n                    }\n                    *, *:before, *:after {\n                        box-sizing: inherit;\n                    }\n                    body {\n                        margin:0;\n                        background: #fafafa;\n                    }\n                </style>\n            </head>\n            <body>\n                <div id=\"swagger-ui\"></div>\n                <script src=\"" + publicSwaggerAssetsPath + "/swagger-ui-bundle.js\"> </script>\n                <script src=\"" + publicSwaggerAssetsPath + "/swagger-ui-standalone-preset.js\"> </script>\n                <script>\n                window.onload = function() {\n                    window.ui = SwaggerUIBundle({\n                        url: '" + publicDocumentationPath + "',\n                        dom_id: \"#swagger-ui\",\n                        deepLinking: true,\n                        presets: [\n                          SwaggerUIBundle.presets.apis,\n                          SwaggerUIStandalonePreset\n                        ],\n                        plugins: [\n                          SwaggerUIBundle.plugins.DownloadUrl\n                        ],\n                        layout: \"StandaloneLayout\",\n                        validatorUrl: " + (String)(this.validatorUrl != null ? "\"" + this.validatorUrl + "\"" : "null") + "\n                      })\n                }\n                </script>\n            </body>\n        </html>\n    ");
        return html;
    }
}

