/*
 * Decompiled with CFR 0.152.
 */
package com.typesafe.sslconfig.ssl;

import com.typesafe.sslconfig.ssl.ClientAuth;
import com.typesafe.sslconfig.ssl.ClientAuth$Default$;
import com.typesafe.sslconfig.ssl.ClientAuth$Need$;
import com.typesafe.sslconfig.ssl.ClientAuth$None$;
import com.typesafe.sslconfig.ssl.ClientAuth$Want$;

public final class ClientAuth$ {
    public static final ClientAuth$ MODULE$ = new ClientAuth$();

    public ClientAuth none() {
        return ClientAuth$None$.MODULE$;
    }

    public ClientAuth want() {
        return ClientAuth$Want$.MODULE$;
    }

    public ClientAuth need() {
        return ClientAuth$Need$.MODULE$;
    }

    public ClientAuth defaultAuth() {
        return ClientAuth$Default$.MODULE$;
    }

    private ClientAuth$() {
    }
}

