/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.http.staticfiles;

import io.javalin.http.staticfiles.StaticFileConfig;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface ResourceHandler {
    public boolean handle(HttpServletRequest var1, HttpServletResponse var2);

    public boolean addStaticFileConfig(StaticFileConfig var1);
}

