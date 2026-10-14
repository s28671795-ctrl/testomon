/*
 * Decompiled with CFR 0.152.
 */
package akka.japi;

import akka.japi.JavaPartialFunction$NoMatch$;

public final class JavaPartialFunction$ {
    public static final JavaPartialFunction$ MODULE$ = new JavaPartialFunction$();

    public final RuntimeException noMatch() {
        return JavaPartialFunction$NoMatch$.MODULE$;
    }

    private JavaPartialFunction$() {
    }
}

