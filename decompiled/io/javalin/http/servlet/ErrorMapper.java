/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.http.servlet;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001\u0012B\u0005\u00a2\u0006\u0002\u0010\u0002J\u001e\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rJ\u0016\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\u0010\u001a\u00020\u0011R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0013"}, d2={"Lio/javalin/http/servlet/ErrorMapper;", "", "()V", "errorHandlers", "", "Lio/javalin/http/servlet/ErrorMapper$MapperEntry;", "addHandler", "", "statusCode", "", "contentType", "", "handler", "Lio/javalin/http/Handler;", "handle", "", "ctx", "Lio/javalin/http/Context;", "MapperEntry", "javalin"})
@SourceDebugExtension(value={"SMAP\nErrorMapper.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ErrorMapper.kt\nio/javalin/http/servlet/ErrorMapper\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,29:1\n766#2:30\n857#2,2:31\n1855#2,2:33\n*S KotlinDebug\n*F\n+ 1 ErrorMapper.kt\nio/javalin/http/servlet/ErrorMapper\n*L\n22#1:30\n22#1:31,2\n22#1:33,2\n*E\n"})
public final class ErrorMapper {
    @NotNull
    private final Set<MapperEntry> errorHandlers = new LinkedHashSet();

    public final boolean addHandler(int statusCode, @NotNull String contentType, @NotNull Handler handler) {
        Intrinsics.checkNotNullParameter(contentType, "contentType");
        Intrinsics.checkNotNullParameter(handler, "handler");
        return this.errorHandlers.add(new MapperEntry(statusCode, contentType, handler));
    }

    /*
     * WARNING - void declaration
     */
    public final void handle(int statusCode, @NotNull Context ctx) {
        void $this$filterTo$iv$iv;
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Iterable $this$filter$iv = this.errorHandlers;
        boolean $i$f$filter = false;
        Iterable iterable = $this$filter$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$filterTo = false;
        for (Object element$iv$iv : $this$filterTo$iv$iv) {
            MapperEntry it = (MapperEntry)element$iv$iv;
            boolean bl = false;
            if (!(it.getStatusCode() == statusCode)) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        Iterable $this$forEach$iv = (List)destination$iv$iv;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            MapperEntry it = (MapperEntry)element$iv;
            boolean bl = false;
            if (!Intrinsics.areEqual(it.getContentType(), "*")) {
                String string = ctx.header("Accept");
                boolean bl2 = string != null ? StringsKt.contains((CharSequence)string, it.getContentType(), true) : false;
                if (!bl2) continue;
            }
            it.getHandler().handle(ctx);
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\t\u0010\u000f\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0010\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u0011\u001a\u00020\u0007H\u00c6\u0003J'\u0010\u0012\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007H\u00c6\u0001J\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0016\u001a\u00020\u0003H\u00d6\u0001J\t\u0010\u0017\u001a\u00020\u0005H\u00d6\u0001R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e\u00a8\u0006\u0018"}, d2={"Lio/javalin/http/servlet/ErrorMapper$MapperEntry;", "", "statusCode", "", "contentType", "", "handler", "Lio/javalin/http/Handler;", "(ILjava/lang/String;Lio/javalin/http/Handler;)V", "getContentType", "()Ljava/lang/String;", "getHandler", "()Lio/javalin/http/Handler;", "getStatusCode", "()I", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "toString", "javalin"})
    public static final class MapperEntry {
        private final int statusCode;
        @NotNull
        private final String contentType;
        @NotNull
        private final Handler handler;

        public MapperEntry(int statusCode, @NotNull String contentType, @NotNull Handler handler) {
            Intrinsics.checkNotNullParameter(contentType, "contentType");
            Intrinsics.checkNotNullParameter(handler, "handler");
            this.statusCode = statusCode;
            this.contentType = contentType;
            this.handler = handler;
        }

        public final int getStatusCode() {
            return this.statusCode;
        }

        @NotNull
        public final String getContentType() {
            return this.contentType;
        }

        @NotNull
        public final Handler getHandler() {
            return this.handler;
        }

        public final int component1() {
            return this.statusCode;
        }

        @NotNull
        public final String component2() {
            return this.contentType;
        }

        @NotNull
        public final Handler component3() {
            return this.handler;
        }

        @NotNull
        public final MapperEntry copy(int statusCode, @NotNull String contentType, @NotNull Handler handler) {
            Intrinsics.checkNotNullParameter(contentType, "contentType");
            Intrinsics.checkNotNullParameter(handler, "handler");
            return new MapperEntry(statusCode, contentType, handler);
        }

        public static /* synthetic */ MapperEntry copy$default(MapperEntry mapperEntry, int n, String string, Handler handler, int n2, Object object) {
            if ((n2 & 1) != 0) {
                n = mapperEntry.statusCode;
            }
            if ((n2 & 2) != 0) {
                string = mapperEntry.contentType;
            }
            if ((n2 & 4) != 0) {
                handler = mapperEntry.handler;
            }
            return mapperEntry.copy(n, string, handler);
        }

        @NotNull
        public String toString() {
            return "MapperEntry(statusCode=" + this.statusCode + ", contentType=" + this.contentType + ", handler=" + this.handler + ")";
        }

        public int hashCode() {
            int result = Integer.hashCode(this.statusCode);
            result = result * 31 + this.contentType.hashCode();
            result = result * 31 + this.handler.hashCode();
            return result;
        }

        public boolean equals(@Nullable Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof MapperEntry)) {
                return false;
            }
            MapperEntry mapperEntry = (MapperEntry)other;
            if (this.statusCode != mapperEntry.statusCode) {
                return false;
            }
            if (!Intrinsics.areEqual(this.contentType, mapperEntry.contentType)) {
                return false;
            }
            return Intrinsics.areEqual(this.handler, mapperEntry.handler);
        }
    }
}

