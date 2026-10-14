/*
 * Decompiled with CFR 0.152.
 */
package com.incoresoft.sessions.api;

import com.incoresoft.sessions.api.ApiToken;

public interface ApiTokenActionsListener {
    public void onApiTokenIssued(ApiToken var1);

    public void onApiTokenRevoked(ApiToken var1);

    public void onApiTokenExpired(ApiToken var1);
}

