/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.http;

import io.javalin.http.HttpResponseException;
import io.javalin.http.HttpStatus;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.MapsKt;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010$\n\u0002\b\u0002\b\u0016\u0018\u00002\u00020\u0001B1\b\u0007\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\u0014\b\u0002\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u0007\u00a2\u0006\u0002\u0010\b\u00a8\u0006\t"}, d2={"Lio/javalin/http/RedirectResponse;", "Lio/javalin/http/HttpResponseException;", "status", "Lio/javalin/http/HttpStatus;", "message", "", "details", "", "(Lio/javalin/http/HttpStatus;Ljava/lang/String;Ljava/util/Map;)V", "javalin"})
public class RedirectResponse
extends HttpResponseException {
    @JvmOverloads
    public RedirectResponse(@NotNull HttpStatus status, @NotNull String message, @NotNull Map<String, String> details) {
        Intrinsics.checkNotNullParameter((Object)status, "status");
        Intrinsics.checkNotNullParameter(message, "message");
        Intrinsics.checkNotNullParameter(details, "details");
        super(status, message, details);
    }

    public /* synthetic */ RedirectResponse(HttpStatus httpStatus, String string, Map map, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 1) != 0) {
            httpStatus = HttpStatus.FOUND;
        }
        if ((n & 2) != 0) {
            string = httpStatus.getMessage();
        }
        if ((n & 4) != 0) {
            map = MapsKt.emptyMap();
        }
        this(httpStatus, string, map);
    }

    @JvmOverloads
    public RedirectResponse(@NotNull HttpStatus status, @NotNull String message) {
        Intrinsics.checkNotNullParameter((Object)status, "status");
        Intrinsics.checkNotNullParameter(message, "message");
        this(status, message, null, 4, null);
    }

    @JvmOverloads
    public RedirectResponse(@NotNull HttpStatus status) {
        Intrinsics.checkNotNullParameter((Object)status, "status");
        this(status, null, null, 6, null);
    }

    @JvmOverloads
    public RedirectResponse() {
        this(null, null, null, 7, null);
    }
}

