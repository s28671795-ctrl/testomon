/*
 * Decompiled with CFR 0.152.
 */
package com.burgstaller.okhttp.digest;

import java.io.IOException;
import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Route;

public interface CachingAuthenticator
extends Authenticator {
    public Request authenticateWithState(Route var1, Request var2) throws IOException;
}

