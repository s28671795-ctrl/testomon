/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.actor.ActorSystem;
import akka.actor.ClassicActorSystemProvider;
import akka.actor.ExtendedActorSystem;
import akka.actor.Extension;
import akka.annotation.InternalApi;
import akka.stream.impl.FlowNames$;
import akka.stream.impl.SeqActorName$;
import akka.stream.impl.SeqActorNameImpl;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005E;aa\u0003\u0007\t\u0002A\u0011bA\u0002\u000b\r\u0011\u0003\u0001R\u0003C\u0003(\u0003\u0011\u0005!\bC\u0003<\u0003\u0011\u0005C\bC\u0003<\u0003\u0011\u0005#\tC\u0003H\u0003\u0011\u0005\u0003\nC\u0003J\u0003\u0011\u0005#JB\u0003\u0015\u0019\u0001\u00012\u0005C\u0003(\u000f\u0011\u0005\u0011\u0006C\u0004+\u000f\t\u0007I\u0011A\u0016\t\r=:\u0001\u0015!\u0003-\u0003%1En\\<OC6,7O\u0003\u0002\u000e\u001d\u0005!\u0011.\u001c9m\u0015\ty\u0001#\u0001\u0004tiJ,\u0017-\u001c\u0006\u0002#\u0005!\u0011m[6b!\t\u0019\u0012!D\u0001\r\u0005%1En\\<OC6,7o\u0005\u0003\u0002-q9\u0004CA\f\u001b\u001b\u0005A\"\"A\r\u0002\u000bM\u001c\u0017\r\\1\n\u0005mA\"AB!osJ+g\rE\u0002\u001eA\tj\u0011A\b\u0006\u0003?A\tQ!Y2u_JL!!\t\u0010\u0003\u0017\u0015CH/\u001a8tS>t\u0017\n\u001a\t\u0003'\u001d\u00192a\u0002\f%!\tiR%\u0003\u0002'=\tIQ\t\u001f;f]NLwN\\\u0001\u0007y%t\u0017\u000e\u001e \u0004\u0001Q\t!%\u0001\u0003oC6,W#\u0001\u0017\u0011\u0005Mi\u0013B\u0001\u0018\r\u0005A\u0019V-]!di>\u0014h*Y7f\u00136\u0004H.A\u0003oC6,\u0007\u0005\u000b\u0002\bcA\u0011!'N\u0007\u0002g)\u0011A\u0007E\u0001\u000bC:tw\u000e^1uS>t\u0017B\u0001\u001c4\u0005-Ie\u000e^3s]\u0006d\u0017\t]5\u0011\u0005uA\u0014BA\u001d\u001f\u0005M)\u0005\u0010^3og&|g.\u00133Qe>4\u0018\u000eZ3s)\u0005\u0011\u0012aA4fiR\u0011!%\u0010\u0005\u0006}\r\u0001\raP\u0001\u0007gf\u001cH/Z7\u0011\u0005u\u0001\u0015BA!\u001f\u0005-\t5\r^8s'f\u001cH/Z7\u0015\u0005\t\u001a\u0005\"\u0002 \u0005\u0001\u0004!\u0005CA\u000fF\u0013\t1eD\u0001\u000eDY\u0006\u001c8/[2BGR|'oU=ti\u0016l\u0007K]8wS\u0012,'/\u0001\u0004m_>\\W\u000f]\u000b\u0002%\u0005y1M]3bi\u0016,\u0005\u0010^3og&|g\u000e\u0006\u0002#\u0017\")aH\u0002a\u0001\u0019B\u0011Q$T\u0005\u0003\u001dz\u00111#\u0012=uK:$W\rZ!di>\u00148+_:uK6D#!A\u0019)\u0005\u0001\t\u0004")
@InternalApi
public class FlowNames
implements Extension {
    private final SeqActorNameImpl name = SeqActorName$.MODULE$.apply("Flow");

    public static FlowNames createExtension(ExtendedActorSystem system) {
        return FlowNames$.MODULE$.createExtension(system);
    }

    public static FlowNames$ lookup() {
        return FlowNames$.MODULE$.lookup();
    }

    public static FlowNames get(ClassicActorSystemProvider system) {
        return FlowNames$.MODULE$.get(system);
    }

    public static FlowNames get(ActorSystem system) {
        return FlowNames$.MODULE$.get(system);
    }

    public static Extension apply(ClassicActorSystemProvider system) {
        return FlowNames$.MODULE$.apply(system);
    }

    public static Extension apply(ActorSystem system) {
        return FlowNames$.MODULE$.apply(system);
    }

    public SeqActorNameImpl name() {
        return this.name;
    }
}

