/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.ActorLogMarker$;
import akka.annotation.ApiMayChange;
import akka.event.LogMarker;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005M:Q\u0001B\u0003\t\u0002)1Q\u0001D\u0003\t\u00025AQ\u0001F\u0001\u0005\u0002UAQAF\u0001\u0005\u0002]\ta\"Q2u_JdunZ'be.,'O\u0003\u0002\u0007\u000f\u0005)\u0011m\u0019;pe*\t\u0001\"\u0001\u0003bW.\f7\u0001\u0001\t\u0003\u0017\u0005i\u0011!\u0002\u0002\u000f\u0003\u000e$xN\u001d'pO6\u000b'o[3s'\t\ta\u0002\u0005\u0002\u0010%5\t\u0001CC\u0001\u0012\u0003\u0015\u00198-\u00197b\u0013\t\u0019\u0002C\u0001\u0004B]f\u0014VMZ\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0003)\t!\u0002Z3bI2+G\u000f^3s)\tAb\u0004\u0005\u0002\u001a95\t!D\u0003\u0002\u001c\u000f\u0005)QM^3oi&\u0011QD\u0007\u0002\n\u0019><W*\u0019:lKJDQaH\u0002A\u0002\u0001\nA\"\\3tg\u0006<Wm\u00117bgN\u0004\"!\t\u0015\u000f\u0005\t2\u0003CA\u0012\u0011\u001b\u0005!#BA\u0013\n\u0003\u0019a$o\\8u}%\u0011q\u0005E\u0001\u0007!J,G-\u001a4\n\u0005%R#AB*ue&twM\u0003\u0002(!!\u0012\u0011\u0001\f\t\u0003[Aj\u0011A\f\u0006\u0003_\u001d\t!\"\u00198o_R\fG/[8o\u0013\t\tdF\u0001\u0007Ba&l\u0015-_\"iC:<W\r\u000b\u0002\u0001Y\u0001")
@ApiMayChange
public final class ActorLogMarker {
    public static LogMarker deadLetter(String messageClass) {
        return ActorLogMarker$.MODULE$.deadLetter(messageClass);
    }
}

