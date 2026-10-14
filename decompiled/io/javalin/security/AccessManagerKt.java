/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.security;

import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=2, xi=48, d1={"\u0000\f\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\n\u0010\u0000\u001a\u00060\u0001j\u0002`\u0002\u00a8\u0006\u0003"}, d2={"accessManagerNotConfiguredException", "Ljava/lang/IllegalStateException;", "Lkotlin/IllegalStateException;", "javalin"})
public final class AccessManagerKt {
    @NotNull
    public static final IllegalStateException accessManagerNotConfiguredException() {
        return new IllegalStateException("No access-manager configured. Add an access-manager using 'Javalin.create(c -> c.accessManager(...))'.");
    }
}

