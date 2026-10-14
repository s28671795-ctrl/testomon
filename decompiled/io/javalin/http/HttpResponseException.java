/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.http;

import io.javalin.http.HttpStatus;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.MapsKt;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010$\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\b\u0016\u0018\u00002\u00060\u0001j\u0002`\u0002B/\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\u0014\b\u0002\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\b\u00a2\u0006\u0002\u0010\tB/\b\u0007\u0012\u0006\u0010\u0003\u001a\u00020\n\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\u0014\b\u0002\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\b\u00a2\u0006\u0002\u0010\u000bR\u001d\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0003\u001a\u00020\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f\u00a8\u0006\u0010"}, d2={"Lio/javalin/http/HttpResponseException;", "Ljava/lang/RuntimeException;", "Lkotlin/RuntimeException;", "status", "Lio/javalin/http/HttpStatus;", "message", "", "details", "", "(Lio/javalin/http/HttpStatus;Ljava/lang/String;Ljava/util/Map;)V", "", "(ILjava/lang/String;Ljava/util/Map;)V", "getDetails", "()Ljava/util/Map;", "getStatus", "()I", "javalin"})
public class HttpResponseException
extends RuntimeException {
    private final int status;
    @NotNull
    private final Map<String, String> details;

    @JvmOverloads
    public HttpResponseException(int status, @NotNull String message, @NotNull Map<String, String> details) {
        Intrinsics.checkNotNullParameter(message, "message");
        Intrinsics.checkNotNullParameter(details, "details");
        super(message);
        this.status = status;
        this.details = details;
    }

    public /* synthetic */ HttpResponseException(int n, String string, Map map, int n2, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n2 & 2) != 0) {
            string = "";
        }
        if ((n2 & 4) != 0) {
            map = MapsKt.emptyMap();
        }
        this(n, string, map);
    }

    public final int getStatus() {
        return this.status;
    }

    @NotNull
    public final Map<String, String> getDetails() {
        return this.details;
    }

    public HttpResponseException(@NotNull HttpStatus status, @NotNull String message, @NotNull Map<String, String> details) {
        Intrinsics.checkNotNullParameter((Object)status, "status");
        Intrinsics.checkNotNullParameter(message, "message");
        Intrinsics.checkNotNullParameter(details, "details");
        this(status.getCode(), message, details);
    }

    public /* synthetic */ HttpResponseException(HttpStatus httpStatus, String string, Map map, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 2) != 0) {
            string = httpStatus.getMessage();
        }
        if ((n & 4) != 0) {
            map = MapsKt.emptyMap();
        }
        this(httpStatus, string, map);
    }

    @JvmOverloads
    public HttpResponseException(int status, @NotNull String message) {
        Intrinsics.checkNotNullParameter(message, "message");
        this(status, message, null, 4, null);
    }

    @JvmOverloads
    public HttpResponseException(int status) {
        this(status, null, null, 6, null);
    }
}

