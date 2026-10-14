/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.ActorSystemImpl;
import akka.actor.ChildStats;
import akka.actor.InternalActorRef;
import akka.actor.Props;
import akka.actor.dungeon.ChildrenContainer;
import akka.annotation.InternalApi;
import akka.annotation.InternalStableApi;
import akka.dispatch.Envelope;
import akka.dispatch.Envelope$;
import akka.dispatch.sysmsg.SystemMessage;
import scala.Option;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005]c\u0001\u0003\f\u0018!\u0003\r\t!G\u000e\t\u000b\t\u0002A\u0011\u0001\u0013\t\u000b!\u0002a\u0011A\u0015\t\u000b9\u0002a\u0011A\u0018\t\u000bM\u0002a\u0011\u0001\u001b\t\u000ba\u0002a\u0011A\u001d\t\u000bm\u0002a\u0011\u0001\u0013\t\u000bq\u0002a\u0011A\u001f\t\u000b1\u0003a\u0011A'\t\u000bA\u0003a\u0011\u0001\u0013\t\rE\u0003a\u0011A\rS\u0011\u00151\u0006A\"\u0001X\u0011\u0015Y\u0006A\"\u0001]\u0011\u0015\u0019\u0007A\"\u0001e\u0011\u0015)\bA\"\u0001w\u0011\u0015A\bA\"\u0001z\u0011\u0019A\b\u0001\"\u0002\u0002\u0014!9\u0011Q\u0005\u0001\u0007\u0002\u0005\u001d\u0002BBA\u001c\u0001\u0019\u0005!\u000b\u0003\u0004\u0002:\u00011\tA\u0015\u0005\b\u0003w\u0001a\u0011AA\u001f\u0011\u001d\t)\u0005\u0001D\u0001\u0003\u000f\u0012AaQ3mY*\u0011\u0001$G\u0001\u0006C\u000e$xN\u001d\u0006\u00025\u0005!\u0011m[6b'\t\u0001A\u0004\u0005\u0002\u001eA5\taDC\u0001 \u0003\u0015\u00198-\u00197b\u0013\t\tcD\u0001\u0004B]f\u0014VMZ\u0001\u0007I%t\u0017\u000e\u001e\u0013\u0004\u0001Q\tQ\u0005\u0005\u0002\u001eM%\u0011qE\b\u0002\u0005+:LG/\u0001\u0003tK24W#\u0001\u0016\u0011\u0005-bS\"A\f\n\u00055:\"\u0001C!di>\u0014(+\u001a4\u0002\rML8\u000f^3n+\u0005\u0001\u0004CA\u00162\u0013\t\u0011tCA\u0006BGR|'oU=ti\u0016l\u0017AC:zgR,W.S7qYV\tQ\u0007\u0005\u0002,m%\u0011qg\u0006\u0002\u0010\u0003\u000e$xN]*zgR,W.S7qY\u0006)1\u000f^1siR\t!(D\u0001\u0001\u0003\u001d\u0019Xo\u001d9f]\u0012\faA]3tk6,GCA\u0013?\u0011\u0015yt\u00011\u0001A\u0003=\u0019\u0017-^:fI\nKh)Y5mkJ,\u0007CA!J\u001d\t\u0011uI\u0004\u0002D\r6\tAI\u0003\u0002FG\u00051AH]8pizJ\u0011aH\u0005\u0003\u0011z\tq\u0001]1dW\u0006<W-\u0003\u0002K\u0017\nIA\u000b\u001b:po\u0006\u0014G.\u001a\u0006\u0003\u0011z\tqA]3ti\u0006\u0014H\u000f\u0006\u0002&\u001d\")q\n\u0003a\u0001\u0001\u0006)1-Y;tK\u0006!1\u000f^8q\u00031I7\u000fV3s[&t\u0017\r^3e+\u0005\u0019\u0006CA\u000fU\u0013\t)fDA\u0004C_>dW-\u00198\u0002\rA\f'/\u001a8u+\u0005A\u0006CA\u0016Z\u0013\tQvC\u0001\tJ]R,'O\\1m\u0003\u000e$xN\u001d*fM\u0006a1\r[5mIJ,gNU3ggV\tQ\f\u0005\u0002_C6\tqL\u0003\u0002a/\u00059A-\u001e8hK>t\u0017B\u00012`\u0005E\u0019\u0005.\u001b7ee\u0016t7i\u001c8uC&tWM]\u0001\u000fO\u0016$8\t[5mI\nKh*Y7f)\t)7\u000eE\u0002\u001eM\"L!a\u001a\u0010\u0003\r=\u0003H/[8o!\tY\u0013.\u0003\u0002k/\tQ1\t[5mIN#\u0018\r^:\t\u000b1l\u0001\u0019A7\u0002\t9\fW.\u001a\t\u0003]Jt!a\u001c9\u0011\u0005\rs\u0012BA9\u001f\u0003\u0019\u0001&/\u001a3fM&\u00111\u000f\u001e\u0002\u0007'R\u0014\u0018N\\4\u000b\u0005Et\u0012AD4fiNKgn\u001a7f\u0007\"LG\u000e\u001a\u000b\u00031^DQ\u0001\u001c\bA\u00025\f1b]3oI6+7o]1hKR\u0011QE\u001f\u0005\u0006w>\u0001\r\u0001`\u0001\u0004[N<\u0007cA?\u0002\u00025\taP\u0003\u0002\u00003\u0005AA-[:qCR\u001c\u0007.C\u0002\u0002\u0004y\u0014\u0001\"\u00128wK2|\u0007/\u001a\u0015\u0004\u001f\u0005\u001d\u0001\u0003BA\u0005\u0003\u001fi!!a\u0003\u000b\u0007\u00055\u0011$\u0001\u0006b]:|G/\u0019;j_:LA!!\u0005\u0002\f\t\t\u0012J\u001c;fe:\fGn\u0015;bE2,\u0017\t]5\u0015\u000b\u0015\n)\"a\b\t\u000f\u0005]\u0001\u00031\u0001\u0002\u001a\u00059Q.Z:tC\u001e,\u0007cA\u000f\u0002\u001c%\u0019\u0011Q\u0004\u0010\u0003\u0007\u0005s\u0017\u0010\u0003\u0004\u0002\"A\u0001\rAK\u0001\u0007g\u0016tG-\u001a:)\u0007A\t9!A\ttK:$7+_:uK6lUm]:bO\u0016$2!JA\u0015\u0011\u0019Y\u0018\u00031\u0001\u0002,A!\u0011QFA\u001a\u001b\t\tyCC\u0002\u00022y\faa]=t[N<\u0017\u0002BA\u001b\u0003_\u0011QbU=ti\u0016lW*Z:tC\u001e,\u0017aB5t\u0019>\u001c\u0017\r\\\u0001\fQ\u0006\u001cX*Z:tC\u001e,7/\u0001\tok6\u0014WM](g\u001b\u0016\u001c8/Y4fgV\u0011\u0011q\b\t\u0004;\u0005\u0005\u0013bAA\"=\t\u0019\u0011J\u001c;\u0002\u000bA\u0014x\u000e]:\u0016\u0005\u0005%\u0003cA\u0016\u0002L%\u0019\u0011QJ\f\u0003\u000bA\u0013x\u000e]:)\u0007\u0001\t\t\u0006\u0005\u0003\u0002\n\u0005M\u0013\u0002BA+\u0003\u0017\u00111\"\u00138uKJt\u0017\r\\!qS\u0002")
@InternalApi
public interface Cell {
    public ActorRef self();

    public ActorSystem system();

    public ActorSystemImpl systemImpl();

    public Cell start();

    public void suspend();

    public void resume(Throwable var1);

    public void restart(Throwable var1);

    public void stop();

    public boolean isTerminated();

    public InternalActorRef parent();

    public ChildrenContainer childrenRefs();

    public Option<ChildStats> getChildByName(String var1);

    public InternalActorRef getSingleChild(String var1);

    @InternalStableApi
    public void sendMessage(Envelope var1);

    public static /* synthetic */ void sendMessage$(Cell $this, Object message, ActorRef sender) {
        $this.sendMessage(message, sender);
    }

    @InternalStableApi
    default public void sendMessage(Object message, ActorRef sender) {
        this.sendMessage(Envelope$.MODULE$.apply(message, sender, this.system()));
    }

    public void sendSystemMessage(SystemMessage var1);

    public boolean isLocal();

    public boolean hasMessages();

    public int numberOfMessages();

    public Props props();

    public static void $init$(Cell $this) {
    }
}

