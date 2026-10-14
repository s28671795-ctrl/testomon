/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.http.staticfiles;

import io.javalin.http.ContentType;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0004\n\u0002\u0010$\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tJ'\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0012\u0010\n\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00050\u000b\"\u00020\u0005\u00a2\u0006\u0002\u0010\fJ'\u0010\u0006\u001a\u00020\u00072\u0006\u0010\r\u001a\u00020\u00052\u0012\u0010\n\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00050\u000b\"\u00020\u0005\u00a2\u0006\u0002\u0010\u000eJ\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u0010R\u001a\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0011"}, d2={"Lio/javalin/http/staticfiles/MimeTypesConfig;", "", "()V", "extensionToMimeType", "", "", "add", "", "contentType", "Lio/javalin/http/ContentType;", "extensions", "", "(Lio/javalin/http/ContentType;[Ljava/lang/String;)V", "mimeType", "(Ljava/lang/String;[Ljava/lang/String;)V", "getMapping", "", "javalin"})
@SourceDebugExtension(value={"SMAP\nStaticFileConfig.kt\nKotlin\n*S Kotlin\n*F\n+ 1 StaticFileConfig.kt\nio/javalin/http/staticfiles/MimeTypesConfig\n+ 2 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n*L\n1#1,44:1\n13579#2,2:45\n*S KotlinDebug\n*F\n+ 1 StaticFileConfig.kt\nio/javalin/http/staticfiles/MimeTypesConfig\n*L\n39#1:45,2\n*E\n"})
public final class MimeTypesConfig {
    @NotNull
    private final Map<String, String> extensionToMimeType = new LinkedHashMap();

    @NotNull
    public final Map<String, String> getMapping() {
        return MapsKt.toMap(this.extensionToMimeType);
    }

    public final void add(@NotNull ContentType contentType) {
        Intrinsics.checkNotNullParameter((Object)contentType, "contentType");
        String[] stringArray = contentType.getExtensions();
        this.add(contentType.getMimeType(), Arrays.copyOf(stringArray, stringArray.length));
    }

    public final void add(@NotNull ContentType contentType, String ... extensions) {
        Intrinsics.checkNotNullParameter((Object)contentType, "contentType");
        Intrinsics.checkNotNullParameter(extensions, "extensions");
        this.add(contentType.getMimeType(), Arrays.copyOf(extensions, extensions.length));
    }

    public final void add(@NotNull String mimeType, String ... extensions) {
        Intrinsics.checkNotNullParameter(mimeType, "mimeType");
        Intrinsics.checkNotNullParameter(extensions, "extensions");
        String[] $this$forEach$iv = extensions;
        boolean $i$f$forEach = false;
        int n = $this$forEach$iv.length;
        for (int i = 0; i < n; ++i) {
            String element$iv;
            String ext = element$iv = $this$forEach$iv[i];
            boolean bl = false;
            this.extensionToMimeType.put(ext, mimeType);
        }
    }
}

