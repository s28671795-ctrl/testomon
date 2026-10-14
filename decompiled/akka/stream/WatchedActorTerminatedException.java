/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.actor.ActorRef;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005U2AAB\u0004\u0003\u0019!A1\u0004\u0001BC\u0002\u0013\u0005A\u0004\u0003\u0005&\u0001\t\u0005\t\u0015!\u0003\u001e\u0011!1\u0003A!b\u0001\n\u00039\u0003\u0002\u0003\u0018\u0001\u0005\u0003\u0005\u000b\u0011\u0002\u0015\t\u000b=\u0002A\u0011\u0001\u0019\u0003?]\u000bGo\u00195fI\u0006\u001bGo\u001c:UKJl\u0017N\\1uK\u0012,\u0005pY3qi&|gN\u0003\u0002\t\u0013\u000511\u000f\u001e:fC6T\u0011AC\u0001\u0005C.\\\u0017m\u0001\u0001\u0014\u0005\u0001i\u0001C\u0001\b\u0019\u001d\tyQC\u0004\u0002\u0011'5\t\u0011C\u0003\u0002\u0013\u0017\u00051AH]8pizJ\u0011\u0001F\u0001\u0006g\u000e\fG.Y\u0005\u0003-]\tq\u0001]1dW\u0006<WMC\u0001\u0015\u0013\tI\"D\u0001\tSk:$\u0018.\\3Fq\u000e,\u0007\u000f^5p]*\u0011acF\u0001\u0012o\u0006$8\r[5oON#\u0018mZ3OC6,W#A\u000f\u0011\u0005y\u0011cBA\u0010!!\t\u0001r#\u0003\u0002\"/\u00051\u0001K]3eK\u001aL!a\t\u0013\u0003\rM#(/\u001b8h\u0015\t\ts#\u0001\nxCR\u001c\u0007.\u001b8h'R\fw-\u001a(b[\u0016\u0004\u0013a\u0001:fMV\t\u0001\u0006\u0005\u0002*Y5\t!F\u0003\u0002,\u0013\u0005)\u0011m\u0019;pe&\u0011QF\u000b\u0002\t\u0003\u000e$xN\u001d*fM\u0006!!/\u001a4!\u0003\u0019a\u0014N\\5u}Q\u0019\u0011g\r\u001b\u0011\u0005I\u0002Q\"A\u0004\t\u000bm)\u0001\u0019A\u000f\t\u000b\u0019*\u0001\u0019\u0001\u0015")
public final class WatchedActorTerminatedException
extends RuntimeException {
    private final String watchingStageName;
    private final ActorRef ref;

    public String watchingStageName() {
        return this.watchingStageName;
    }

    public ActorRef ref() {
        return this.ref;
    }

    public WatchedActorTerminatedException(String watchingStageName, ActorRef ref) {
        this.watchingStageName = watchingStageName;
        this.ref = ref;
        super(new StringBuilder(41).append("Actor watched by [").append(watchingStageName).append("] has terminated! Was: ").append(ref).toString());
    }
}

