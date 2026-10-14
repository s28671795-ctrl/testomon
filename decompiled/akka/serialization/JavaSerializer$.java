/*
 * Decompiled with CFR 0.152.
 */
package akka.serialization;

import akka.serialization.JavaSerializer;

public final class JavaSerializer$ {
    public static final JavaSerializer$ MODULE$ = new JavaSerializer$();
    private static final JavaSerializer.CurrentSystem currentSystem = new JavaSerializer.CurrentSystem();

    public JavaSerializer.CurrentSystem currentSystem() {
        return currentSystem;
    }

    private JavaSerializer$() {
    }
}

