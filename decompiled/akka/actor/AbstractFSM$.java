/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.FSM$NullFunction$;
import scala.PartialFunction;

public final class AbstractFSM$ {
    public static final AbstractFSM$ MODULE$ = new AbstractFSM$();

    public <S, D> PartialFunction<S, D> NullFunction() {
        return FSM$NullFunction$.MODULE$;
    }

    private AbstractFSM$() {
    }
}

