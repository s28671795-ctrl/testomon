/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.WrappedMessage$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005!:QAB\u0004\t\u000211QAD\u0004\t\u0002=AQAF\u0001\u0005\u0002]AQ\u0001G\u0001\u0005\u0002e1qAD\u0004\u0011\u0002G\u0005a\u0005C\u0003\u001f\t\u0019\u0005q%\u0001\bXe\u0006\u0004\b/\u001a3NKN\u001c\u0018mZ3\u000b\u0005!I\u0011!B1di>\u0014(\"\u0001\u0006\u0002\t\u0005\\7.Y\u0002\u0001!\ti\u0011!D\u0001\b\u000599&/\u00199qK\u0012lUm]:bO\u0016\u001c\"!\u0001\t\u0011\u0005E!R\"\u0001\n\u000b\u0003M\tQa]2bY\u0006L!!\u0006\n\u0003\r\u0005s\u0017PU3g\u0003\u0019a\u0014N\\5u}Q\tA\"\u0001\u0004v]^\u0014\u0018\r\u001d\u000b\u00035u\u0001\"!E\u000e\n\u0005q\u0011\"aA!os\")ad\u0001a\u00015\u00059Q.Z:tC\u001e,\u0007FA\u0002!!\t\tC%D\u0001#\u0015\t\u0019##\u0001\u0006b]:|G/\u0019;j_:L!!\n\u0012\u0003\u000fQ\f\u0017\u000e\u001c:fGN\u0011A\u0001E\u000b\u00025\u0001")
public interface WrappedMessage {
    public static Object unwrap(Object message) {
        return WrappedMessage$.MODULE$.unwrap(message);
    }

    public Object message();
}

