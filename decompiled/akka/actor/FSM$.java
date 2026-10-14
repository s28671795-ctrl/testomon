/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.FSM$$minus$greater$;
import scala.Some;
import scala.concurrent.duration.FiniteDuration;
import scala.concurrent.duration.package;
import scala.concurrent.duration.package$;

public final class FSM$ {
    public static final FSM$ MODULE$ = new FSM$();
    private static final FSM$$minus$greater$ $u2192 = FSM$$minus$greater$.MODULE$;
    private static final Some<FiniteDuration> akka$actor$FSM$$SomeMaxFiniteDuration = new Some<FiniteDuration>(new package.DurationLong(package$.MODULE$.DurationLong(Long.MAX_VALUE)).nanos());

    public FSM$$minus$greater$ $u2192() {
        return $u2192;
    }

    public final Some<FiniteDuration> akka$actor$FSM$$SomeMaxFiniteDuration() {
        return akka$actor$FSM$$SomeMaxFiniteDuration;
    }

    private FSM$() {
    }
}

