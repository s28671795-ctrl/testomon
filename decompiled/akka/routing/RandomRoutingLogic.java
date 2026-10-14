/*
 * Decompiled with CFR 0.152.
 */
package akka.routing;

import akka.routing.NoRoutee$;
import akka.routing.RandomRoutingLogic$;
import akka.routing.Routee;
import akka.routing.RoutingLogic;
import java.util.concurrent.ThreadLocalRandom;
import scala.collection.immutable.IndexedSeq;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005i:Qa\u0002\u0005\t\u000251Qa\u0004\u0005\t\u0002AAQaF\u0001\u0005\u0002aAQ!G\u0001\u0005\u0002i1Aa\u0004\u0005\u00039!)q\u0003\u0002C\u00015!)\u0001\u0005\u0002C!C\u0005\u0011\"+\u00198e_6\u0014v.\u001e;j]\u001edunZ5d\u0015\tI!\"A\u0004s_V$\u0018N\\4\u000b\u0003-\tA!Y6lC\u000e\u0001\u0001C\u0001\b\u0002\u001b\u0005A!A\u0005*b]\u0012|WNU8vi&tw\rT8hS\u000e\u001c\"!A\t\u0011\u0005I)R\"A\n\u000b\u0003Q\tQa]2bY\u0006L!AF\n\u0003\r\u0005s\u0017PU3g\u0003\u0019a\u0014N\\5u}Q\tQ\"A\u0003baBd\u0017\u0010F\u0001\u001c!\tqAaE\u0002\u0005#u\u0001\"A\u0004\u0010\n\u0005}A!\u0001\u0004*pkRLgn\u001a'pO&\u001c\u0017AB:fY\u0016\u001cG\u000fF\u0002#K)\u0002\"AD\u0012\n\u0005\u0011B!A\u0002*pkR,W\rC\u0003'\r\u0001\u0007q%A\u0004nKN\u001c\u0018mZ3\u0011\u0005IA\u0013BA\u0015\u0014\u0005\r\te.\u001f\u0005\u0006W\u0019\u0001\r\u0001L\u0001\be>,H/Z3t!\ri#GI\u0007\u0002])\u0011q\u0006M\u0001\nS6lW\u000f^1cY\u0016T!!M\n\u0002\u0015\r|G\u000e\\3di&|g.\u0003\u00024]\tQ\u0011J\u001c3fq\u0016$7+Z9)\t\u0011)\u0004(\u000f\t\u0003%YJ!aN\n\u0003!M+'/[1m-\u0016\u00148/[8o+&#\u0015!\u0002<bYV,g$A\u0001")
public final class RandomRoutingLogic
implements RoutingLogic {
    private static final long serialVersionUID = 1L;

    public static RandomRoutingLogic apply() {
        return RandomRoutingLogic$.MODULE$.apply();
    }

    @Override
    public Routee select(Object message, IndexedSeq<Routee> routees) {
        if (routees.isEmpty()) {
            return NoRoutee$.MODULE$;
        }
        return (Routee)routees.apply(ThreadLocalRandom.current().nextInt(routees.size()));
    }
}

