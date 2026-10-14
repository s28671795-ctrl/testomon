/*
 * Decompiled with CFR 0.152.
 */
package org.pac4j.core.credentials;

import org.pac4j.core.credentials.Credentials;

public final class AnonymousCredentials
extends Credentials {
    private static final long serialVersionUID = 7526472295622776147L;
    public static final AnonymousCredentials INSTANCE = new AnonymousCredentials();

    @Override
    public boolean equals(Object o) {
        return o instanceof AnonymousCredentials;
    }

    @Override
    public int hashCode() {
        return 0;
    }
}

