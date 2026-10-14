/*
 * Decompiled with CFR 0.152.
 */
package akka.routing;

import akka.routing.NoRoutee$;
import akka.routing.RoundRobinRoutingLogic$;
import akka.routing.Routee;
import akka.routing.RoutingLogic;
import java.util.concurrent.atomic.AtomicLong;
import scala.collection.immutable.IndexedSeq;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005-;Q!\u0003\u0006\t\u0002=1Q!\u0005\u0006\t\u0002IAQ!G\u0001\u0005\u0002iAQaG\u0001\u0005\u0002q1A!\u0005\u0006\u0003=!)\u0011\u0004\u0002C\u00019!9!\u0005\u0002b\u0001\n\u0003\u0019\u0003B\u0002\u0019\u0005A\u0003%A\u0005C\u00032\t\u0011\u0005#'\u0001\fS_VtGMU8cS:\u0014v.\u001e;j]\u001edunZ5d\u0015\tYA\"A\u0004s_V$\u0018N\\4\u000b\u00035\tA!Y6lC\u000e\u0001\u0001C\u0001\t\u0002\u001b\u0005Q!A\u0006*pk:$'k\u001c2j]J{W\u000f^5oO2{w-[2\u0014\u0005\u0005\u0019\u0002C\u0001\u000b\u0018\u001b\u0005)\"\"\u0001\f\u0002\u000bM\u001c\u0017\r\\1\n\u0005a)\"AB!osJ+g-\u0001\u0004=S:LGO\u0010\u000b\u0002\u001f\u0005)\u0011\r\u001d9msR\tQ\u0004\u0005\u0002\u0011\tM\u0019AaE\u0010\u0011\u0005A\u0001\u0013BA\u0011\u000b\u00051\u0011v.\u001e;j]\u001edunZ5d\u0003\u0011qW\r\u001f;\u0016\u0003\u0011\u0002\"!\n\u0018\u000e\u0003\u0019R!a\n\u0015\u0002\r\u0005$x.\\5d\u0015\tI#&\u0001\u0006d_:\u001cWO\u001d:f]RT!a\u000b\u0017\u0002\tU$\u0018\u000e\u001c\u0006\u0002[\u0005!!.\u0019<b\u0013\tycE\u0001\u0006Bi>l\u0017n\u0019'p]\u001e\fQA\\3yi\u0002\naa]3mK\u000e$HcA\u001a7wA\u0011\u0001\u0003N\u0005\u0003k)\u0011aAU8vi\u0016,\u0007\"B\u001c\t\u0001\u0004A\u0014aB7fgN\fw-\u001a\t\u0003)eJ!AO\u000b\u0003\u0007\u0005s\u0017\u0010C\u0003=\u0011\u0001\u0007Q(A\u0004s_V$X-Z:\u0011\u0007y\u001a5'D\u0001@\u0015\t\u0001\u0015)A\u0005j[6,H/\u00192mK*\u0011!)F\u0001\u000bG>dG.Z2uS>t\u0017B\u0001#@\u0005)Ie\u000eZ3yK\u0012\u001cV-\u001d\u0015\u0005\t\u0019K%\n\u0005\u0002\u0015\u000f&\u0011\u0001*\u0006\u0002\u0011'\u0016\u0014\u0018.\u00197WKJ\u001c\u0018n\u001c8V\u0013\u0012\u000bQA^1mk\u0016t\u0012!\u0001")
public final class RoundRobinRoutingLogic
implements RoutingLogic {
    private static final long serialVersionUID = 1L;
    private final AtomicLong next = new AtomicLong();

    public static RoundRobinRoutingLogic apply() {
        return RoundRobinRoutingLogic$.MODULE$.apply();
    }

    public AtomicLong next() {
        return this.next;
    }

    @Override
    public Routee select(Object message, IndexedSeq<Routee> routees) {
        if (routees.nonEmpty()) {
            int size = routees.size();
            int index = (int)(this.next().getAndIncrement() % (long)size);
            return (Routee)routees.apply(index < 0 ? size + index : index);
        }
        return NoRoutee$.MODULE$;
    }
}

