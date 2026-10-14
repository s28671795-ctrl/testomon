/*
 * Decompiled with CFR 0.152.
 */
package akka.pattern;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.pattern.FutureRef$;
import akka.util.Timeout;
import scala.concurrent.Future;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005Y3q!\u0003\u0006\u0011\u0002G\u0005q\u0002C\u0003\u0018\u0001\u0019\u0005\u0001\u0004C\u0003 \u0001\u0019\u0005\u0001eB\u00033\u0015!\u00051GB\u0003\n\u0015!\u0005Q\u0007C\u00037\t\u0011\u0005q\u0007\u0003\u00049\t\u0011\u0005A\"\u000f\u0005\u0006\u0005\u0012!\ta\u0011\u0005\u0006\u0005\u0012!\tA\u0015\u0002\n\rV$XO]3SK\u001aT!a\u0003\u0007\u0002\u000fA\fG\u000f^3s]*\tQ\"\u0001\u0003bW.\f7\u0001A\u000b\u0003!%\u001a\"\u0001A\t\u0011\u0005I)R\"A\n\u000b\u0003Q\tQa]2bY\u0006L!AF\n\u0003\r\u0005s\u0017PU3g\u0003\r\u0011XMZ\u000b\u00023A\u0011!$H\u0007\u00027)\u0011A\u0004D\u0001\u0006C\u000e$xN]\u0005\u0003=m\u0011\u0001\"Q2u_J\u0014VMZ\u0001\u0007MV$XO]3\u0016\u0003\u0005\u00022AI\u0013(\u001b\u0005\u0019#B\u0001\u0013\u0014\u0003)\u0019wN\\2veJ,g\u000e^\u0005\u0003M\r\u0012aAR;ukJ,\u0007C\u0001\u0015*\u0019\u0001!QA\u000b\u0001C\u0002-\u0012\u0011\u0001V\t\u0003Y=\u0002\"AE\u0017\n\u00059\u001a\"a\u0002(pi\"Lgn\u001a\t\u0003%AJ!!M\n\u0003\u0007\u0005s\u00170A\u0005GkR,(/\u001a*fMB\u0011A\u0007B\u0007\u0002\u0015M\u0011A!E\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0003M\nAa\u001e:baV\u0011!(\u0010\u000b\u0004wy\u0002\u0005c\u0001\u001b\u0001yA\u0011\u0001&\u0010\u0003\u0006U\u0019\u0011\ra\u000b\u0005\u0006\u007f\u0019\u0001\r!G\u0001\tC\u000e$xN\u001d*fM\")qD\u0002a\u0001\u0003B\u0019!%\n\u001f\u0002\u000b\u0005\u0004\b\u000f\\=\u0015\u0007\u0011+%\nE\u00025\u0001=BQAR\u0004A\u0002\u001d\u000baa]=ti\u0016l\u0007C\u0001\u000eI\u0013\tI5DA\u0006BGR|'oU=ti\u0016l\u0007\"B&\b\u0001\u0004a\u0015a\u0002;j[\u0016|W\u000f\u001e\t\u0003\u001bBk\u0011A\u0014\u0006\u0003\u001f2\tA!\u001e;jY&\u0011\u0011K\u0014\u0002\b)&lWm\\;u)\t\u0019V\u000b\u0006\u0002E)\")a\t\u0003a\u0002\u000f\")1\n\u0003a\u0001\u0019\u0002")
public interface FutureRef<T> {
    public static FutureRef<Object> apply(Timeout timeout2, ActorSystem system) {
        return FutureRef$.MODULE$.apply(timeout2, system);
    }

    public static FutureRef<Object> apply(ActorSystem system, Timeout timeout2) {
        return FutureRef$.MODULE$.apply(system, timeout2);
    }

    public ActorRef ref();

    public Future<T> future();
}

