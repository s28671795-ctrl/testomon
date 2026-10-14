/*
 * Decompiled with CFR 0.152.
 */
package akka.japi.pf;

import akka.actor.SupervisorStrategy;
import akka.japi.pf.FI;
import akka.japi.pf.Match;
import akka.japi.pf.PFBuilder;

public class DeciderBuilder {
    private DeciderBuilder() {
    }

    public static <P extends Throwable> PFBuilder<Throwable, SupervisorStrategy.Directive> match(Class<P> clazz, FI.Apply<P, SupervisorStrategy.Directive> apply2) {
        return Match.match(clazz, apply2);
    }

    public static <P extends Throwable> PFBuilder<Throwable, SupervisorStrategy.Directive> match(Class<P> clazz, FI.TypedPredicate<P> typedPredicate, FI.Apply<P, SupervisorStrategy.Directive> apply2) {
        return Match.match(clazz, typedPredicate, apply2);
    }

    public static PFBuilder<Throwable, SupervisorStrategy.Directive> matchAny(FI.Apply<Throwable, SupervisorStrategy.Directive> apply2) {
        return Match.matchAny(apply2);
    }
}

