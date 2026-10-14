/*
 * Decompiled with CFR 0.152.
 */
package akka.io;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.ExtensionId;
import akka.io.IO$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005q:QAB\u0004\t\u000211QAD\u0004\t\u0002=AQAF\u0001\u0005\u0002]1q\u0001G\u0001\u0011\u0002G\u0005\u0011\u0004C\u0003 \u0007\u0019\u0005\u0001\u0005C\u0003%\u0003\u0011\u0005Q%\u0001\u0002J\u001f*\u0011\u0001\"C\u0001\u0003S>T\u0011AC\u0001\u0005C.\\\u0017m\u0001\u0001\u0011\u00055\tQ\"A\u0004\u0003\u0005%{5CA\u0001\u0011!\t\tB#D\u0001\u0013\u0015\u0005\u0019\u0012!B:dC2\f\u0017BA\u000b\u0013\u0005\u0019\te.\u001f*fM\u00061A(\u001b8jiz\"\u0012\u0001\u0004\u0002\n\u000bb$XM\\:j_:\u001c2a\u0001\t\u001b!\tYb$D\u0001\u001d\u0015\ti\u0012\"A\u0003bGR|'/\u0003\u0002\u00199\u00059Q.\u00198bO\u0016\u0014X#A\u0011\u0011\u0005m\u0011\u0013BA\u0012\u001d\u0005!\t5\r^8s%\u00164\u0017!B1qa2LXC\u0001\u00145)\t9S\u0006\u0006\u0002\"Q!)\u0011&\u0002a\u0002U\u000511/_:uK6\u0004\"aG\u0016\n\u00051b\"aC!di>\u00148+_:uK6DQAL\u0003A\u0002=\n1a[3z!\rY\u0002GM\u0005\u0003cq\u00111\"\u0012=uK:\u001c\u0018n\u001c8JIB\u00111\u0007\u000e\u0007\u0001\t\u0015)TA1\u00017\u0005\u0005!\u0016CA\u001c;!\t\t\u0002(\u0003\u0002:%\t9aj\u001c;iS:<\u0007CA\u001e\u0004\u001b\u0005\t\u0001")
public final class IO {
    public static <T extends Extension> ActorRef apply(ExtensionId<T> key, ActorSystem system) {
        return IO$.MODULE$.apply(key, system);
    }

    public static interface Extension
    extends akka.actor.Extension {
        public ActorRef manager();
    }
}

