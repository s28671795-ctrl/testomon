/*
 * Decompiled with CFR 0.152.
 */
package akka.japi.pf;

import scala.PartialFunction;
import scala.PartialFunction$;

public final class CaseStatement$ {
    public static final CaseStatement$ MODULE$ = new CaseStatement$();

    public <F, T> PartialFunction<F, T> empty() {
        return PartialFunction$.MODULE$.empty();
    }

    private CaseStatement$() {
    }
}

