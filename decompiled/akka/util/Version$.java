/*
 * Decompiled with CFR 0.152.
 */
package akka.util;

import akka.util.Version;

public final class Version$ {
    public static final Version$ MODULE$ = new Version$();
    private static final Version Zero = MODULE$.apply("0.0.0");
    private static final int akka$util$Version$$Undefined = 0;

    public Version Zero() {
        return Zero;
    }

    public int akka$util$Version$$Undefined() {
        return akka$util$Version$$Undefined;
    }

    public Version apply(String version) {
        Version v = new Version(version);
        return v.parse();
    }

    private Version$() {
    }
}

