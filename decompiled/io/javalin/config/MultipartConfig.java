/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.config;

import io.javalin.config.SizeUnit;
import io.javalin.http.util.MultipartUtil;
import jakarta.servlet.MultipartConfigElement;
import jakarta.servlet.http.HttpServletRequest;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0004J\u0016\u0010\u0006\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u00072\u0006\u0010\u000e\u001a\u00020\u000fJ\u0016\u0010\b\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\t2\u0006\u0010\u000e\u001a\u00020\u000fJ\u0016\u0010\n\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u00072\u0006\u0010\u000e\u001a\u00020\u000fJ\b\u0010\u0010\u001a\u00020\u0011H\u0002R\u0016\u0010\u0003\u001a\n \u0005*\u0004\u0018\u00010\u00040\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0012"}, d2={"Lio/javalin/config/MultipartConfig;", "", "()V", "cacheDirectory", "", "kotlin.jvm.PlatformType", "maxFileSize", "", "maxInMemoryFileSize", "", "maxTotalRequestSize", "", "path", "size", "sizeUnit", "Lio/javalin/config/SizeUnit;", "multipartConfigElement", "Ljakarta/servlet/MultipartConfigElement;", "javalin"})
public final class MultipartConfig {
    private String cacheDirectory = System.getProperty("java.io.tmpdir");
    private long maxFileSize = -1L;
    private long maxTotalRequestSize = -1L;
    private int maxInMemoryFileSize = 1;

    public MultipartConfig() {
        MultipartUtil.INSTANCE.setPreUploadFunction((Function1<? super HttpServletRequest, Unit>)new Function1<HttpServletRequest, Unit>(){

            @Override
            public final void invoke(@NotNull HttpServletRequest req) {
                Intrinsics.checkNotNullParameter(req, "req");
                if (req.getAttribute("org.eclipse.jetty.multipartConfig") == null) {
                    req.setAttribute("org.eclipse.jetty.multipartConfig", this.multipartConfigElement());
                }
            }
        });
    }

    public final void cacheDirectory(@NotNull String path) {
        Intrinsics.checkNotNullParameter(path, "path");
        this.cacheDirectory = path;
    }

    public final void maxFileSize(long size, @NotNull SizeUnit sizeUnit) {
        Intrinsics.checkNotNullParameter((Object)sizeUnit, "sizeUnit");
        this.maxFileSize = size * (long)sizeUnit.getMultiplier();
    }

    public final void maxInMemoryFileSize(int size, @NotNull SizeUnit sizeUnit) {
        Intrinsics.checkNotNullParameter((Object)sizeUnit, "sizeUnit");
        this.maxInMemoryFileSize = size * sizeUnit.getMultiplier();
    }

    public final void maxTotalRequestSize(long size, @NotNull SizeUnit sizeUnit) {
        Intrinsics.checkNotNullParameter((Object)sizeUnit, "sizeUnit");
        this.maxTotalRequestSize = size * (long)sizeUnit.getMultiplier();
    }

    private final MultipartConfigElement multipartConfigElement() {
        return new MultipartConfigElement(this.cacheDirectory, this.maxFileSize, this.maxTotalRequestSize, this.maxInMemoryFileSize);
    }
}

