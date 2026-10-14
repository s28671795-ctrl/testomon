/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.middleware.http;

import io.javalin.security.RouteRole;

public enum AppRole implements RouteRole
{
    ANYONE,
    LOGGED_IN,
    API_CALL;

}

