/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.http.server;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.security.AccessManager;
import io.javalin.security.RouteRole;
import java.util.Set;
import org.jetbrains.annotations.NotNull;

public class UnrestrictedAccessManager
implements AccessManager {
    @Override
    public void manage(@NotNull Handler handler, @NotNull Context ctx, @NotNull Set<? extends RouteRole> set) throws Exception {
        handler.handle(ctx);
    }
}

