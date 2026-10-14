/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.Actor$;
import akka.actor.ActorContext;
import akka.actor.ActorPath;
import akka.actor.ActorRef$;
import akka.actor.ScalaActorRef;
import akka.annotation.InternalApi;
import java.io.Serializable;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005\u0005s!\u0002\n\u0014\u0011\u0003Ab!\u0002\u000e\u0014\u0011\u0003Y\u0002\"\u0002\u0016\u0002\t\u0003Y\u0003b\u0002\u0017\u0002\u0005\u0004%)!\f\u0005\b\u0003k\t\u0001\u0015!\u0004/\u0011%\t9$AA\u0001\n\u0013\tIDB\u0004\u001b'\u0005\u0005q&a\t\t\u000b)2A\u0011A!\t\u000b\t3a\u0011A\"\t\u000b\u001d3AQ\u0001%\t\u000b93AQA(\t\u000bi3a\u0011A.\t\u000f\u00014\u0011\u0013!C\u0001C\")QN\u0002C\u0001]\"1aO\u0002D\u0001+]Dq!a\u0001\u0007\t\u000b\n)\u0001C\u0004\u0002\b\u0019!)%!\u0003\t\u000f\u0005=a\u0001\"\u0011\u0002\u0012\u0005A\u0011i\u0019;peJ+gM\u0003\u0002\u0015+\u0005)\u0011m\u0019;pe*\ta#\u0001\u0003bW.\f7\u0001\u0001\t\u00033\u0005i\u0011a\u0005\u0002\t\u0003\u000e$xN\u001d*fMN\u0019\u0011\u0001\b\u0012\u0011\u0005u\u0001S\"\u0001\u0010\u000b\u0003}\tQa]2bY\u0006L!!\t\u0010\u0003\r\u0005s\u0017PU3g!\t\u0019\u0003&D\u0001%\u0015\t)c%\u0001\u0002j_*\tq%\u0001\u0003kCZ\f\u0017BA\u0015%\u00051\u0019VM]5bY&T\u0018M\u00197f\u0003\u0019a\u0014N\\5u}Q\t\u0001$\u0001\u0005o_N+g\u000eZ3s+\u0005q\u0003CA\r\u0007'\u00111A\u0004\r\u001c\u0011\u0007E\"d&D\u00013\u0015\t\u0019d%\u0001\u0003mC:<\u0017BA\u001b3\u0005)\u0019u.\u001c9be\u0006\u0014G.\u001a\t\u0003o}r!\u0001O\u001f\u000f\u0005ebT\"\u0001\u001e\u000b\u0005m:\u0012A\u0002\u001fs_>$h(C\u0001 \u0013\tqd$A\u0004qC\u000e\\\u0017mZ3\n\u0005%\u0002%B\u0001 \u001f)\u0005q\u0013\u0001\u00029bi\",\u0012\u0001\u0012\t\u00033\u0015K!AR\n\u0003\u0013\u0005\u001bGo\u001c:QCRD\u0017!C2p[B\f'/\u001a+p)\tIE\n\u0005\u0002\u001e\u0015&\u00111J\b\u0002\u0004\u0013:$\b\"B'\n\u0001\u0004q\u0013!B8uQ\u0016\u0014\u0018\u0001\u0002;fY2$2\u0001U*Y!\ti\u0012+\u0003\u0002S=\t!QK\\5u\u0011\u0015!&\u00021\u0001V\u0003\ri7o\u001a\t\u0003;YK!a\u0016\u0010\u0003\u0007\u0005s\u0017\u0010C\u0003Z\u0015\u0001\u0007a&\u0001\u0004tK:$WM]\u0001\u0006I\t\fgn\u001a\u000b\u00039z#\"\u0001U/\t\u000fe[\u0001\u0013!a\u0002]!)ql\u0003a\u0001+\u00069Q.Z:tC\u001e,\u0017a\u0004\u0013cC:<G\u0005Z3gCVdG\u000f\n\u001a\u0015\u0005\td'F\u0001\u0018dW\u0005!\u0007CA3k\u001b\u00051'BA4i\u0003%)hn\u00195fG.,GM\u0003\u0002j=\u0005Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\u0005-4'!E;oG\",7m[3e-\u0006\u0014\u0018.\u00198dK\")q\f\u0004a\u0001+\u00069am\u001c:xCJ$GCA8v)\t\u0001\u0006\u000fC\u0003r\u001b\u0001\u000f!/A\u0004d_:$X\r\u001f;\u0011\u0005e\u0019\u0018B\u0001;\u0014\u00051\t5\r^8s\u0007>tG/\u001a=u\u0011\u0015yV\u00021\u0001V\u00031I7\u000fV3s[&t\u0017\r^3e+\u0005A\bCA\u000fz\u0013\tQhDA\u0004C_>dW-\u00198)\u00059a\bCA?\u0000\u001b\u0005q(BA5\u0016\u0013\r\t\tA \u0002\f\u0013:$XM\u001d8bY\u0006\u0003\u0018.\u0001\u0005iCND7i\u001c3f)\u0005I\u0015AB3rk\u0006d7\u000fF\u0002y\u0003\u0017Aa!!\u0004\u0011\u0001\u0004)\u0016\u0001\u0002;iCR\f\u0001\u0002^8TiJLgn\u001a\u000b\u0003\u0003'\u0001B!!\u0006\u0002\u001e9!\u0011qCA\r!\tId$C\u0002\u0002\u001cy\ta\u0001\u0015:fI\u00164\u0017\u0002BA\u0010\u0003C\u0011aa\u0015;sS:<'bAA\u000e=I1\u0011QEA\u0015\u0003_1a!a\n\u0001\u0001\u0005\r\"\u0001\u0004\u001fsK\u001aLg.Z7f]Rt\u0004cA\r\u0002,%\u0019\u0011QF\n\u0003!%sG/\u001a:oC2\f5\r^8s%\u00164\u0007cA\r\u00022%\u0019\u00111G\n\u0003\u001b\u0005\u001bGo\u001c:SK\u001a\u001c6m\u001c9f\u0003%qwnU3oI\u0016\u0014\b%\u0001\u0007xe&$XMU3qY\u0006\u001cW\r\u0006\u0002\u0002<A\u0019\u0011'!\u0010\n\u0007\u0005}\"G\u0001\u0004PE*,7\r\u001e")
public abstract class ActorRef
implements Comparable<ActorRef>,
Serializable {
    public static ActorRef noSender() {
        return ActorRef$.MODULE$.noSender();
    }

    public abstract ActorPath path();

    @Override
    public final int compareTo(ActorRef other) {
        int x = this.path().compareTo(other.path());
        if (x == 0) {
            if (this.path().uid() < other.path().uid()) {
                return -1;
            }
            if (this.path().uid() == other.path().uid()) {
                return 0;
            }
            return 1;
        }
        return x;
    }

    public final void tell(Object msg, ActorRef sender) {
        ((ScalaActorRef)((Object)this)).$bang(msg, sender);
    }

    public abstract void $bang(Object var1, ActorRef var2);

    public void forward(Object message, ActorContext context) {
        this.tell(message, context.sender());
    }

    @InternalApi
    public abstract boolean isTerminated();

    public final int hashCode() {
        if (this.path().uid() == 0) {
            return this.path().hashCode();
        }
        return this.path().uid();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final boolean equals(Object that) {
        Object object = that;
        if (!(object instanceof ActorRef)) return false;
        ActorRef actorRef2 = (ActorRef)object;
        if (this.path().uid() != actorRef2.path().uid()) return false;
        ActorPath actorPath = this.path();
        ActorPath actorPath2 = actorRef2.path();
        if (actorPath != null) {
            if (!actorPath.equals(actorPath2)) return false;
            return true;
        }
        if (actorPath2 == null) return true;
        return false;
    }

    public String toString() {
        if (this.path().uid() == 0) {
            return new StringBuilder(7).append("Actor[").append(this.path()).append("]").toString();
        }
        return new StringBuilder(8).append("Actor[").append(this.path()).append("#").append(this.path().uid()).append("]").toString();
    }

    public ActorRef $bang$default$2(Object message) {
        return Actor$.MODULE$.noSender();
    }
}

