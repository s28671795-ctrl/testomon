/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.plugin.bundled;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.UnauthorizedResponse;
import io.javalin.plugin.Plugin;
import io.javalin.security.BasicAuthCredentials;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0005J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0016R\u000e\u0010\u0004\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\n"}, d2={"Lio/javalin/plugin/bundled/BasicAuthPlugin;", "Lio/javalin/plugin/Plugin;", "username", "", "password", "(Ljava/lang/String;Ljava/lang/String;)V", "apply", "", "app", "Lio/javalin/Javalin;", "javalin"})
@SourceDebugExtension(value={"SMAP\nBasicAuthPlugin.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BasicAuthPlugin.kt\nio/javalin/plugin/bundled/BasicAuthPlugin\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,36:1\n1#2:37\n*E\n"})
public final class BasicAuthPlugin
implements Plugin {
    @NotNull
    private final String username;
    @NotNull
    private final String password;

    public BasicAuthPlugin(@NotNull String username, @NotNull String password) {
        Intrinsics.checkNotNullParameter(username, "username");
        Intrinsics.checkNotNullParameter(password, "password");
        this.username = username;
        this.password = password;
    }

    @Override
    public void apply(@NotNull Javalin app) {
        Intrinsics.checkNotNullParameter(app, "app");
        app.before(arg_0 -> BasicAuthPlugin.apply$lambda$3(this, arg_0));
    }

    private static final void apply$lambda$3(BasicAuthPlugin this$0, Context ctx) {
        boolean bl;
        Object it;
        Object $this$apply_u24lambda_u243_u24lambda_u240;
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Object object = this$0;
        try {
            $this$apply_u24lambda_u243_u24lambda_u240 = object;
            boolean bl2 = false;
            $this$apply_u24lambda_u243_u24lambda_u240 = Result.constructor-impl(ctx.basicAuthCredentials());
        }
        catch (Throwable bl2) {
            $this$apply_u24lambda_u243_u24lambda_u240 = Result.constructor-impl(ResultKt.createFailure(bl2));
        }
        object = $this$apply_u24lambda_u243_u24lambda_u240;
        Throwable throwable = Result.exceptionOrNull-impl(object);
        if (throwable == null) {
            it = (BasicAuthCredentials)object;
            boolean bl3 = false;
            Object object2 = it;
            bl = Intrinsics.areEqual(object2 != null ? ((BasicAuthCredentials)object2).getUsername() : null, this$0.username) && Intrinsics.areEqual(((BasicAuthCredentials)it).getPassword(), this$0.password);
        } else {
            it = throwable;
            boolean bl4 = false;
            bl = false;
        }
        boolean matched = bl;
        if (!matched) {
            ctx.header("WWW-Authenticate", "Basic");
            throw new UnauthorizedResponse(null, null, 3, null);
        }
    }
}

