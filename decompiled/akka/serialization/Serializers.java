/*
 * Decompiled with CFR 0.152.
 */
package akka.serialization;

import akka.serialization.Serializer;
import akka.serialization.Serializers$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005):Q\u0001B\u0003\t\u0002)1Q\u0001D\u0003\t\u00025AQ\u0001F\u0001\u0005\u0002UAQAF\u0001\u0005\u0002]\t1bU3sS\u0006d\u0017N_3sg*\u0011aaB\u0001\u000eg\u0016\u0014\u0018.\u00197ju\u0006$\u0018n\u001c8\u000b\u0003!\tA!Y6lC\u000e\u0001\u0001CA\u0006\u0002\u001b\u0005)!aC*fe&\fG.\u001b>feN\u001c\"!\u0001\b\u0011\u0005=\u0011R\"\u0001\t\u000b\u0003E\tQa]2bY\u0006L!a\u0005\t\u0003\r\u0005s\u0017PU3g\u0003\u0019a\u0014N\\5u}Q\t!\"A\u0006nC:Lg-Z:u\r>\u0014Hc\u0001\r$QA\u0011\u0011\u0004\t\b\u00035y\u0001\"a\u0007\t\u000e\u0003qQ!!H\u0005\u0002\rq\u0012xn\u001c;?\u0013\ty\u0002#\u0001\u0004Qe\u0016$WMZ\u0005\u0003C\t\u0012aa\u0015;sS:<'BA\u0010\u0011\u0011\u0015!3\u00011\u0001&\u0003\u0005\u0019\bCA\u0006'\u0013\t9SA\u0001\u0006TKJL\u0017\r\\5{KJDQ!K\u0002A\u00029\tq!\\3tg\u0006<W\r")
public final class Serializers {
    public static String manifestFor(Serializer s2, Object message) {
        return Serializers$.MODULE$.manifestFor(s2, message);
    }
}

