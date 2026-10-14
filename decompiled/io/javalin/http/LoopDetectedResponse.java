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

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010$\n\u0002\b\u0002\b\u0016\u0018\u00002\u00020\u0001B'\b\u0007\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\u0014\b\u0002\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u0005\u00a2\u0006\u0002\u0010\u0006\u00a8\u0006\u0007"}, d2={"Lio/javalin/http/LoopDetectedResponse;", "Lio/javalin/http/HttpResponseException;", "message", "", "details", "", "(Ljava/lang/String;Ljava/util/Map;)V", "javalin"})
public class LoopDetectedResponse
extends HttpResponseException {
    @JvmOverloads
    public LoopDetectedResponse(@NotNull String message, @NotNull Map<String, String> details) {
        Intrinsics.checkNotNullParameter(message, "message");
        Intrinsics.checkNotNullParameter(details, "details");
        super(HttpStatus.LOOP_DETECTED, message, details);
    }

    public /* synthetic */ LoopDetectedResponse(String string, Map map, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 1) != 0) {
            string = HttpStatus.LOOP_DETECTED.getMessage();
        }
        if ((n & 2) != 0) {
            map = MapsKt.emptyMap();
        }
        this(string, map);
    }

    @JvmOverloads
    public LoopDetectedResponse(@NotNull String message) {
        Intrinsics.checkNotNullParameter(message, "message");
        this(message, null, 2, null);
    }

    @JvmOverloads
    public LoopDetectedResponse() {
        this(null, null, 3, null);
    }
}

