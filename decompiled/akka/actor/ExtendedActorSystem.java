/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.ActorRef;
import akka.actor.ActorRefProvider;
import akka.actor.ActorSystem;
import akka.actor.DynamicAccess;
import akka.actor.InternalActorRef;
import akka.actor.Props;
import akka.annotation.DoNotInherit;
import akka.annotation.InternalApi;
import akka.event.LoggingFilter;
import java.util.concurrent.ThreadFactory;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005Y4Q!\u0004\b\u0002\u0002MAQ\u0001\u0007\u0001\u0005\u0002eAQa\u0007\u0001\u0007\u0002qAQ\u0001\t\u0001\u0007\u0002\u0005BQ!\n\u0001\u0007\u0002\u0005BQA\n\u0001\u0007\u0002\u001dBQa\u0010\u0001\u0007\u0002\u0001CQa\u0013\u0001\u0007\u00021CQ\u0001\u0015\u0001\u0007\u0002ECa\u0001\u0017\u0001\u0007\u0002AI\u0006\"\u0002.\u0001\r\u0003Y\u0006B\u00021\u0001\r\u0003\u0001\u0012\r\u0003\u0004m\u0001\u0019\u0005\u0001#\u001c\u0002\u0014\u000bb$XM\u001c3fI\u0006\u001bGo\u001c:TsN$X-\u001c\u0006\u0003\u001fA\tQ!Y2u_JT\u0011!E\u0001\u0005C.\\\u0017m\u0001\u0001\u0014\u0005\u0001!\u0002CA\u000b\u0017\u001b\u0005q\u0011BA\f\u000f\u0005-\t5\r^8s'f\u001cH/Z7\u0002\rqJg.\u001b;?)\u0005Q\u0002CA\u000b\u0001\u0003!\u0001(o\u001c<jI\u0016\u0014X#A\u000f\u0011\u0005Uq\u0012BA\u0010\u000f\u0005A\t5\r^8s%\u00164\u0007K]8wS\u0012,'/\u0001\u0005hk\u0006\u0014H-[1o+\u0005\u0011\u0003CA\u000b$\u0013\t!cB\u0001\tJ]R,'O\\1m\u0003\u000e$xN\u001d*fM\u0006q1/_:uK6<U/\u0019:eS\u0006t\u0017!D:zgR,W.Q2u_J|e\rF\u0002)WA\u0002\"!F\u0015\n\u0005)r!\u0001C!di>\u0014(+\u001a4\t\u000b1*\u0001\u0019A\u0017\u0002\u000bA\u0014x\u000e]:\u0011\u0005Uq\u0013BA\u0018\u000f\u0005\u0015\u0001&o\u001c9t\u0011\u0015\tT\u00011\u00013\u0003\u0011q\u0017-\\3\u0011\u0005MbdB\u0001\u001b;!\t)\u0004(D\u00017\u0015\t9$#\u0001\u0004=e>|GO\u0010\u0006\u0002s\u0005)1oY1mC&\u00111\bO\u0001\u0007!J,G-\u001a4\n\u0005ur$AB*ue&twM\u0003\u0002<q\u0005iA\u000f\u001b:fC\u00124\u0015m\u0019;pef,\u0012!\u0011\t\u0003\u0005&k\u0011a\u0011\u0006\u0003\t\u0016\u000b!bY8oGV\u0014(/\u001a8u\u0015\t1u)\u0001\u0003vi&d'\"\u0001%\u0002\t)\fg/Y\u0005\u0003\u0015\u000e\u0013Q\u0002\u00165sK\u0006$g)Y2u_JL\u0018!\u00043z]\u0006l\u0017nY!dG\u0016\u001c8/F\u0001N!\t)b*\u0003\u0002P\u001d\tiA)\u001f8b[&\u001c\u0017iY2fgN\f\u0011\u0002\\8h\r&dG/\u001a:\u0016\u0003I\u0003\"a\u0015,\u000e\u0003QS!!\u0016\t\u0002\u000b\u00154XM\u001c;\n\u0005]#&!\u0004'pO\u001eLgn\u001a$jYR,'/A\u0005qe&tG\u000f\u0016:fKV\t!'A\u0002vS\u0012,\u0012\u0001\u0018\t\u0003;zk\u0011\u0001O\u0005\u0003?b\u0012A\u0001T8oO\u0006qa-\u001b8bYR+'/\\5oCR,G#\u00012\u0011\u0005u\u001b\u0017B\u000139\u0005\u0011)f.\u001b;)\u0005-1\u0007CA4k\u001b\u0005A'BA5\u0011\u0003)\tgN\\8uCRLwN\\\u0005\u0003W\"\u00141\"\u00138uKJt\u0017\r\\!qS\u0006i\u0011n\u001d+fe6Lg.\u0019;j]\u001e$\u0012A\u001c\t\u0003;>L!\u0001\u001d\u001d\u0003\u000f\t{w\u000e\\3b]\"\u0012AB\u001a\u0015\u0003\u0001M\u0004\"a\u001a;\n\u0005UD'\u0001\u0004#p\u001d>$\u0018J\u001c5fe&$\b")
@DoNotInherit
public abstract class ExtendedActorSystem
extends ActorSystem {
    @Override
    public abstract ActorRefProvider provider();

    @Override
    public abstract InternalActorRef guardian();

    public abstract InternalActorRef systemGuardian();

    public abstract ActorRef systemActorOf(Props var1, String var2);

    public abstract ThreadFactory threadFactory();

    public abstract DynamicAccess dynamicAccess();

    public abstract LoggingFilter logFilter();

    public abstract String printTree();

    public abstract long uid();

    @InternalApi
    public abstract void finalTerminate();

    @InternalApi
    public abstract boolean isTerminating();
}

