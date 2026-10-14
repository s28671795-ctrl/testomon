/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.ActorRef;
import akka.actor.ActorRefProvider;
import akka.actor.InternalActorRef$;
import akka.actor.ScalaActorRef;
import akka.annotation.InternalApi;
import akka.dispatch.sysmsg.SystemMessage;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005%rA\u0002\n\u0014\u0011\u0003)rC\u0002\u0004\u001a'!\u0005QC\u0007\u0005\u0006S\u0005!\ta\u000b\u0005\u0006Y\u0005!\t!\f\u0005\bm\u0005\t\t\u0011\"\u00038\r!I2#!\u0001\u0016\r\u0006m\u0001\"B\u0015\u0006\t\u0003Q\u0005\"\u0002'\u0006\r\u0003i\u0005\"B)\u0006\r\u0003\u0011\u0006\"B1\u0006\r\u0003i\u0005\"\u00022\u0006\r\u0003\u0019\u0007\"\u00024\u0006\r\u0003i\u0005\"B4\u0006\r\u0003A\u0007\"B:\u0006\r\u0003!\b\"\u0002=\u0006\r\u0003I\b\"\u0002>\u0006\r\u0003Y\bbBA\n\u000b\u0019\u0005\u0011Q\u0003\u0005\t\u0003/)a\u0011A\u000b\u0002\u0016\u0005\u0001\u0012J\u001c;fe:\fG.Q2u_J\u0014VM\u001a\u0006\u0003)U\tQ!Y2u_JT\u0011AF\u0001\u0005C.\\\u0017\r\u0005\u0002\u0019\u00035\t1C\u0001\tJ]R,'O\\1m\u0003\u000e$xN\u001d*fMN\u0019\u0011aG\u0011\u0011\u0005qyR\"A\u000f\u000b\u0003y\tQa]2bY\u0006L!\u0001I\u000f\u0003\r\u0005s\u0017PU3g!\t\u0011s%D\u0001$\u0015\t!S%\u0001\u0002j_*\ta%\u0001\u0003kCZ\f\u0017B\u0001\u0015$\u00051\u0019VM]5bY&T\u0018M\u00197f\u0003\u0019a\u0014N\\5u}\r\u0001A#A\f\u0002\u001d%\u001cH+Z7q_J\f'/\u001f*fMR\u0011a&\r\t\u00039=J!\u0001M\u000f\u0003\u000f\t{w\u000e\\3b]\")!g\u0001a\u0001g\u0005\u0019!/\u001a4\u0011\u0005a!\u0014BA\u001b\u0014\u0005!\t5\r^8s%\u00164\u0017\u0001D<sSR,'+\u001a9mC\u000e,G#\u0001\u001d\u0011\u0005ebT\"\u0001\u001e\u000b\u0005m*\u0013\u0001\u00027b]\u001eL!!\u0010\u001e\u0003\r=\u0013'.Z2uQ\t\tq\b\u0005\u0002A\u00076\t\u0011I\u0003\u0002C+\u0005Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\u0005\u0011\u000b%aC%oi\u0016\u0014h.\u00197Ba&D#\u0001A \u0014\u0007\u0015\u0019t\t\u0005\u0002\u0019\u0011&\u0011\u0011j\u0005\u0002\u000e'\u000e\fG.Y!di>\u0014(+\u001a4\u0015\u0003-\u0003\"\u0001G\u0003\u0002\u000bM$\u0018M\u001d;\u0015\u00039\u0003\"\u0001H(\n\u0005Ak\"\u0001B+oSR\faA]3tk6,GC\u0001(T\u0011\u0015!\u0006\u00021\u0001V\u0003=\u0019\u0017-^:fI\nKh)Y5mkJ,\u0007C\u0001,_\u001d\t9FL\u0004\u0002Y76\t\u0011L\u0003\u0002[U\u00051AH]8pizJ\u0011AH\u0005\u0003;v\tq\u0001]1dW\u0006<W-\u0003\u0002`A\nIA\u000b\u001b:po\u0006\u0014G.\u001a\u0006\u0003;v\tqa];ta\u0016tG-A\u0004sKN$\u0018M\u001d;\u0015\u00059#\u0007\"B3\u000b\u0001\u0004)\u0016!B2bkN,\u0017\u0001B:u_B\f\u0011c]3oINK8\u000f^3n\u001b\u0016\u001c8/Y4f)\tq\u0015\u000eC\u0003k\u0019\u0001\u00071.A\u0004nKN\u001c\u0018mZ3\u0011\u00051\fX\"A7\u000b\u00059|\u0017AB:zg6\u001cxM\u0003\u0002q+\u0005AA-[:qCR\u001c\u0007.\u0003\u0002s[\ni1+_:uK6lUm]:bO\u0016\f\u0001\u0002\u001d:pm&$WM]\u000b\u0002kB\u0011\u0001D^\u0005\u0003oN\u0011\u0001#Q2u_J\u0014VM\u001a)s_ZLG-\u001a:\u0002\u0013\u001d,G\u000fU1sK:$X#A&\u0002\u0011\u001d,Go\u00115jY\u0012$\"a\u0013?\t\u000bu|\u0001\u0019\u0001@\u0002\t9\fW.\u001a\t\u0005-~\f\u0019!C\u0002\u0002\u0002\u0001\u0014\u0001\"\u0013;fe\u0006$xN\u001d\t\u0005\u0003\u000b\tiA\u0004\u0003\u0002\b\u0005%\u0001C\u0001-\u001e\u0013\r\tY!H\u0001\u0007!J,G-\u001a4\n\t\u0005=\u0011\u0011\u0003\u0002\u0007'R\u0014\u0018N\\4\u000b\u0007\u0005-Q$A\u0004jg2{7-\u00197\u0016\u00039\nA\"[:UKJl\u0017N\\1uK\u0012D#!E \u0013\u000b\u0005u1*!\t\u0007\r\u0005}\u0001\u0001AA\u000e\u00051a$/\u001a4j]\u0016lWM\u001c;?!\rA\u00121E\u0005\u0004\u0003K\u0019\"!D!di>\u0014(+\u001a4TG>\u0004X\r\u000b\u0002\u0006\u007f\u0001")
@InternalApi
public abstract class InternalActorRef
extends ActorRef
implements ScalaActorRef {
    public static boolean isTemporaryRef(ActorRef ref) {
        return InternalActorRef$.MODULE$.isTemporaryRef(ref);
    }

    @Override
    public ActorRef $bang$default$2(Object message) {
        return ScalaActorRef.$bang$default$2$(this, message);
    }

    public abstract void start();

    public abstract void resume(Throwable var1);

    public abstract void suspend();

    public abstract void restart(Throwable var1);

    public abstract void stop();

    public abstract void sendSystemMessage(SystemMessage var1);

    public abstract ActorRefProvider provider();

    public abstract InternalActorRef getParent();

    public abstract InternalActorRef getChild(Iterator<String> var1);

    public abstract boolean isLocal();

    @Override
    @InternalApi
    public abstract boolean isTerminated();
}

