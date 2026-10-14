/*
 * Decompiled with CFR 0.152.
 */
package scala.util.control;

import scala.None$;
import scala.Option;
import scala.Some;
import scala.util.control.ControlThrowable;

public final class NonFatal$ {
    public static final NonFatal$ MODULE$ = new NonFatal$();

    public boolean apply(Throwable t2) {
        return !(t2 instanceof VirtualMachineError ? true : (t2 instanceof ThreadDeath ? true : (t2 instanceof InterruptedException ? true : (t2 instanceof LinkageError ? true : t2 instanceof ControlThrowable))));
    }

    public Option<Throwable> unapply(Throwable t2) {
        if (this.apply(t2)) {
            return new Some<Throwable>(t2);
        }
        return None$.MODULE$;
    }

    private NonFatal$() {
    }
}

