/*
 * Decompiled with CFR 0.152.
 */
package akka.serialization;

import akka.actor.ActorSystem;
import akka.actor.ClassicActorSystemProvider;
import akka.actor.ExtendedActorSystem;
import akka.actor.Extension;
import akka.serialization.Serialization;
import akka.serialization.SerializationExtension$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005e:Qa\u0002\u0005\t\u000251Qa\u0004\u0005\t\u0002AAQaI\u0001\u0005\u0002\u0011BQ!J\u0001\u0005B\u0019BQ!J\u0001\u0005B1BQ!M\u0001\u0005BIBQaM\u0001\u0005BQ\nacU3sS\u0006d\u0017N_1uS>tW\t\u001f;f]NLwN\u001c\u0006\u0003\u0013)\tQb]3sS\u0006d\u0017N_1uS>t'\"A\u0006\u0002\t\u0005\\7.Y\u0002\u0001!\tq\u0011!D\u0001\t\u0005Y\u0019VM]5bY&T\u0018\r^5p]\u0016CH/\u001a8tS>t7\u0003B\u0001\u0012/\u0001\u0002\"AE\u000b\u000e\u0003MQ\u0011\u0001F\u0001\u0006g\u000e\fG.Y\u0005\u0003-M\u0011a!\u00118z%\u00164\u0007c\u0001\r\u001c;5\t\u0011D\u0003\u0002\u001b\u0015\u0005)\u0011m\u0019;pe&\u0011A$\u0007\u0002\f\u000bb$XM\\:j_:LE\r\u0005\u0002\u000f=%\u0011q\u0004\u0003\u0002\u000e'\u0016\u0014\u0018.\u00197ju\u0006$\u0018n\u001c8\u0011\u0005a\t\u0013B\u0001\u0012\u001a\u0005M)\u0005\u0010^3og&|g.\u00133Qe>4\u0018\u000eZ3s\u0003\u0019a\u0014N\\5u}Q\tQ\"A\u0002hKR$\"!H\u0014\t\u000b!\u001a\u0001\u0019A\u0015\u0002\rML8\u000f^3n!\tA\"&\u0003\u0002,3\tY\u0011i\u0019;peNK8\u000f^3n)\tiR\u0006C\u0003)\t\u0001\u0007a\u0006\u0005\u0002\u0019_%\u0011\u0001'\u0007\u0002\u001b\u00072\f7o]5d\u0003\u000e$xN]*zgR,W\u000e\u0015:pm&$WM]\u0001\u0007Y>|7.\u001e9\u0016\u00035\tqb\u0019:fCR,W\t\u001f;f]NLwN\u001c\u000b\u0003;UBQ\u0001\u000b\u0004A\u0002Y\u0002\"\u0001G\u001c\n\u0005aJ\"aE#yi\u0016tG-\u001a3BGR|'oU=ti\u0016l\u0007")
public final class SerializationExtension {
    public static Serialization createExtension(ExtendedActorSystem system) {
        return SerializationExtension$.MODULE$.createExtension(system);
    }

    public static SerializationExtension$ lookup() {
        return SerializationExtension$.MODULE$.lookup();
    }

    public static Serialization get(ClassicActorSystemProvider system) {
        return SerializationExtension$.MODULE$.get(system);
    }

    public static Serialization get(ActorSystem system) {
        return SerializationExtension$.MODULE$.get(system);
    }

    public static boolean equals(Object other) {
        return SerializationExtension$.MODULE$.equals(other);
    }

    public static int hashCode() {
        return SerializationExtension$.MODULE$.hashCode();
    }

    public static Extension apply(ClassicActorSystemProvider system) {
        return SerializationExtension$.MODULE$.apply(system);
    }

    public static Extension apply(ActorSystem system) {
        return SerializationExtension$.MODULE$.apply(system);
    }
}

