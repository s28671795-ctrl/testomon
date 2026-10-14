/*
 * Decompiled with CFR 0.152.
 */
package akka.routing;

import akka.routing.BalancingRoutingLogic$;
import akka.routing.NoRoutee$;
import akka.routing.Routee;
import akka.routing.RoutingLogic;
import scala.collection.immutable.IndexedSeq;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005i:aa\u0002\u0005\t\u0002)aaA\u0002\b\t\u0011\u0003Qq\u0002C\u0003\u0017\u0003\u0011\u0005\u0001\u0004C\u0003\u001a\u0003\u0011\u0005!DB\u0003\u000f\u0011\tQA\u0004C\u0003\u0017\t\u0011\u0005!\u0004C\u0003!\t\u0011\u0005\u0013%A\u000bCC2\fgnY5oOJ{W\u000f^5oO2{w-[2\u000b\u0005%Q\u0011a\u0002:pkRLgn\u001a\u0006\u0002\u0017\u0005!\u0011m[6b!\ti\u0011!D\u0001\t\u0005U\u0011\u0015\r\\1oG&twMU8vi&tw\rT8hS\u000e\u001c\"!\u0001\t\u0011\u0005E!R\"\u0001\n\u000b\u0003M\tQa]2bY\u0006L!!\u0006\n\u0003\r\u0005s\u0017PU3g\u0003\u0019a\u0014N\\5u}\r\u0001A#\u0001\u0007\u0002\u000b\u0005\u0004\b\u000f\\=\u0015\u0003m\u0001\"!\u0004\u0003\u0014\u0007\u0011\u0001R\u0004\u0005\u0002\u000e=%\u0011q\u0004\u0003\u0002\r%>,H/\u001b8h\u0019><\u0017nY\u0001\u0007g\u0016dWm\u0019;\u0015\u0007\t*#\u0006\u0005\u0002\u000eG%\u0011A\u0005\u0003\u0002\u0007%>,H/Z3\t\u000b\u00192\u0001\u0019A\u0014\u0002\u000f5,7o]1hKB\u0011\u0011\u0003K\u0005\u0003SI\u00111!\u00118z\u0011\u0015Yc\u00011\u0001-\u0003\u001d\u0011x.\u001e;fKN\u00042!\f\u001a#\u001b\u0005q#BA\u00181\u0003%IW.\\;uC\ndWM\u0003\u00022%\u0005Q1m\u001c7mK\u000e$\u0018n\u001c8\n\u0005Mr#AC%oI\u0016DX\rZ*fc\"\"A!\u000e\u001d:!\t\tb'\u0003\u00028%\t\u00012+\u001a:jC24VM]:j_:,\u0016\nR\u0001\u0006m\u0006dW/\u001a\u0010\u0002\u0003\u0001")
public final class BalancingRoutingLogic
implements RoutingLogic {
    private static final long serialVersionUID = 1L;

    public static BalancingRoutingLogic apply() {
        return BalancingRoutingLogic$.MODULE$.apply();
    }

    @Override
    public Routee select(Object message, IndexedSeq<Routee> routees) {
        if (routees.isEmpty()) {
            return NoRoutee$.MODULE$;
        }
        return (Routee)routees.head();
    }
}

