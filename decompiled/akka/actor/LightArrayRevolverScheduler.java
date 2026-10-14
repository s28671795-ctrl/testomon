/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.ActorRef;
import akka.actor.Cancellable;
import akka.actor.LightArrayRevolverScheduler$;
import akka.actor.Scheduler;
import akka.actor.SchedulerException;
import akka.dispatch.AbstractNodeQueue;
import akka.event.LoggingAdapter;
import akka.util.Helpers$;
import akka.util.Helpers$ConfigOps$;
import akka.util.Helpers$Requiring$;
import akka.util.Unsafe;
import com.typesafe.config.Config;
import java.io.Closeable;
import java.io.Serializable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.SerializedLambda;
import java.time.Duration;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicReference;
import scala.Array$;
import scala.Function0;
import scala.Function1;
import scala.Option;
import scala.Predef$;
import scala.collection.IterableOnceOps;
import scala.collection.IterableOps;
import scala.collection.immutable.Nil$;
import scala.collection.immutable.Seq;
import scala.collection.immutable.Vector;
import scala.concurrent.Await$;
import scala.concurrent.ExecutionContext;
import scala.concurrent.Future;
import scala.concurrent.Future$;
import scala.concurrent.Promise;
import scala.concurrent.Promise$;
import scala.concurrent.duration.Duration$;
import scala.concurrent.duration.FiniteDuration;
import scala.concurrent.duration.package;
import scala.package$;
import scala.reflect.ClassTag$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.LambdaDeserialize;
import scala.runtime.RichInt$;
import scala.util.control.NonFatal$;

@ScalaSignature(bytes="\u0006\u0005\t-f\u0001B!C\u0001\u001dC\u0001B\u0017\u0001\u0003\u0002\u0003\u0006Ia\u0017\u0005\tI\u0002\u0011\t\u0011)A\u0005K\"A1\u000e\u0001B\u0001B\u0003%A\u000eC\u0003u\u0001\u0011\u0005Q\u000fC\u0004{\u0001\t\u0007I\u0011A>\t\r}\u0004\u0001\u0015!\u0003}\u0011%\t\t\u0001\u0001b\u0001\n\u0003\t\u0019\u0001\u0003\u0005\u0002\u0014\u0001\u0001\u000b\u0011BA\u0003\u0011%\t)\u0002\u0001b\u0001\n\u0003\t\u0019\u0001\u0003\u0005\u0002\u0018\u0001\u0001\u000b\u0011BA\u0003\u0011\u001d\tI\u0002\u0001C\u0005\u00037Aq!!\t\u0001\t#\t\u0019\u0003\u0003\u0004\u0002,\u0001!\tb\u001f\u0005\b\u0003[\u0001A\u0011CA\u0002\u0011\u001d\ty\u0003\u0001C\t\u0003cAq!!\u0010\u0001\t\u0003\ny\u0004C\u0004\u0002p\u0001!\t%!\u001d\t\u000f\u0005u\u0004\u0001\"\u0011\u0002\u0000!9\u0011\u0011\u0012\u0001\u0005B\u0005-\u0005\"CAG\u0001\t\u0007I\u0011IAH\u0011!\t9\n\u0001Q\u0001\n\u0005E\u0005\"CAM\u0001\t\u0007I\u0011BAN\u0011!\ti\n\u0001Q\u0001\n\u0005\u0015\u0002\"CAP\u0001\t\u0007I\u0011BAN\u0011!\t\t\u000b\u0001Q\u0001\n\u0005\u0015\u0002\u0002CAR\u0001\t\u0007I\u0011B>\t\u000f\u0005\u0015\u0006\u0001)A\u0005y\"I\u0011q\u0015\u0001C\u0002\u0013%\u0011\u0011\u0016\u0005\t\u0005\u001b\u0002\u0001\u0015!\u0003\u0002,\"9\u0011q\u000e\u0001\u0005\n\t=\u0003b\u0002B/\u0001\u0011%!q\f\u0005\n\u0005K\u0002!\u0019!C\u0005\u0005OB\u0001Ba#\u0001A\u0003%!\u0011\u000e\u0005\b\u0005\u001b\u0003A\u0011\u0002BH\u0011%\u00119\n\u0001a\u0001\n\u0013\u0011I\nC\u0005\u0003\"\u0002\u0001\r\u0011\"\u0003\u0003$\"A!q\u0015\u0001!B\u0013\u0011YjB\u0004\u00020\nC\t!!-\u0007\r\u0005\u0013\u0005\u0012AAZ\u0011\u0019!x\u0005\"\u0001\u00026\"A\u0011qW\u0014!\u0002\u0013\t)C\u0002\u0004\u0002:\u001e\"\u00111\u0018\u0005\u0007i*\"\tAa\f\u0007\u0015\u0005ew\u0005%A\u0012\u0012\t\u000bYNB\u0004\u0002N\u001eB!)a4\t\u0015\u0005uWF!a\u0001\n\u0003\ty\u000e\u0003\u0006\u0002b6\u0012\t\u0019!C\u0001\u0003GD!\"!;.\u0005\u0003\u0005\u000b\u0015BA.\u0011%\t\u00190\fBA\u0002\u0013\u00051\u0010\u0003\u0006\u0002v6\u0012\t\u0019!C\u0001\u0003oD\u0011\"a?.\u0005\u0003\u0005\u000b\u0015\u0002?\t\u0015\u0005uXF!A!\u0002\u0013\ty\u0005\u0003\u0004u[\u0011\u0005\u0011q \u0005\b\u0005\u000fiCQ\u0002B\u0005\u0011!\u0011i\"\fC\u0003\t\n}\u0001b\u0002B\u0014[\u0011\u0005\u00131\u0012\u0005\b\u0005SiC\u0011\tB\u0010\u0011\u001d\u0011Y#\fC!\u0005[A\u0001Ba\r(A\u0003%!Q\u0007\u0005\t\u0005w9\u0003\u0015!\u0003\u0003>!I!\u0011I\u0014C\u0002\u0013%!1\t\u0005\t\u0005\u000b:\u0003\u0015!\u0003\u0002X\"I!qI\u0014C\u0002\u0013%!\u0011\n\u0005\t\u0005\u0017:\u0003\u0015!\u0003\u0002F\tYB*[4ii\u0006\u0013(/Y=SKZ|GN^3s'\u000eDW\rZ;mKJT!a\u0011#\u0002\u000b\u0005\u001cGo\u001c:\u000b\u0003\u0015\u000bA!Y6lC\u000e\u00011\u0003\u0002\u0001I\u001dJ\u0003\"!\u0013'\u000e\u0003)S\u0011aS\u0001\u0006g\u000e\fG.Y\u0005\u0003\u001b*\u0013a!\u00118z%\u00164\u0007CA(Q\u001b\u0005\u0011\u0015BA)C\u0005%\u00196\r[3ek2,'\u000f\u0005\u0002T16\tAK\u0003\u0002V-\u0006\u0011\u0011n\u001c\u0006\u0002/\u0006!!.\u0019<b\u0013\tIFKA\u0005DY>\u001cX-\u00192mK\u000611m\u001c8gS\u001e\u0004\"\u0001\u00182\u000e\u0003uS!A\u00170\u000b\u0005}\u0003\u0017\u0001\u0003;za\u0016\u001c\u0018MZ3\u000b\u0003\u0005\f1aY8n\u0013\t\u0019WL\u0001\u0004D_:4\u0017nZ\u0001\u0004Y><\u0007C\u00014j\u001b\u00059'B\u00015E\u0003\u0015)g/\u001a8u\u0013\tQwM\u0001\bM_\u001e<\u0017N\\4BI\u0006\u0004H/\u001a:\u0002\u001bQD'/Z1e\r\u0006\u001cGo\u001c:z!\ti'/D\u0001o\u0015\ty\u0007/\u0001\u0006d_:\u001cWO\u001d:f]RT!!\u001d,\u0002\tU$\u0018\u000e\\\u0005\u0003g:\u0014Q\u0002\u00165sK\u0006$g)Y2u_JL\u0018A\u0002\u001fj]&$h\b\u0006\u0003wobL\bCA(\u0001\u0011\u0015QF\u00011\u0001\\\u0011\u0015!G\u00011\u0001f\u0011\u0015YG\u00011\u0001m\u0003%9\u0006.Z3m'&TX-F\u0001}!\tIU0\u0003\u0002\u007f\u0015\n\u0019\u0011J\u001c;\u0002\u0015]CW-\u001a7TSj,\u0007%\u0001\u0007US\u000e\\G)\u001e:bi&|g.\u0006\u0002\u0002\u0006A!\u0011qAA\b\u001b\t\tIA\u0003\u0003\u0002\f\u00055\u0011\u0001\u00033ve\u0006$\u0018n\u001c8\u000b\u0005=T\u0015\u0002BA\t\u0003\u0013\u0011aBR5oSR,G)\u001e:bi&|g.A\u0007US\u000e\\G)\u001e:bi&|g\u000eI\u0001\u0010'\",H\u000fZ8x]RKW.Z8vi\u0006\u00012\u000b[;uI><h\u000eV5nK>,H\u000fI\u0001\be>,h\u000eZ+q)\u0011\t)!!\b\t\u000f\u0005}1\u00021\u0001\u0002\u0006\u0005\tA-A\u0003dY>\u001c7\u000e\u0006\u0002\u0002&A\u0019\u0011*a\n\n\u0007\u0005%\"J\u0001\u0003M_:<\u0017!C:uCJ$H+[2l\u0003I9W\r^*ikR$wn\u001e8US6,w.\u001e;\u0002\u0013]\f\u0017\u000e\u001e(b]>\u001cH\u0003BA\u001a\u0003s\u00012!SA\u001b\u0013\r\t9D\u0013\u0002\u0005+:LG\u000fC\u0004\u0002<=\u0001\r!!\n\u0002\u000b9\fgn\\:\u0002-M\u001c\u0007.\u001a3vY\u0016<\u0016\u000e\u001e5GSb,G\rR3mCf$b!!\u0011\u0002h\u0005-D\u0003BA\"\u0003/\"B!!\u0012\u0002LA\u0019q*a\u0012\n\u0007\u0005%#IA\u0006DC:\u001cW\r\u001c7bE2,\u0007bBA'!\u0001\u000f\u0011qJ\u0001\tKb,7-\u001e;peB!\u0011\u0011KA*\u001b\t\ti!\u0003\u0003\u0002V\u00055!\u0001E#yK\u000e,H/[8o\u0007>tG/\u001a=u\u0011\u001d\tI\u0006\u0005a\u0001\u00037\n\u0001B];o]\u0006\u0014G.\u001a\t\u0005\u0003;\n\u0019'\u0004\u0002\u0002`)\u0019\u0011\u0011\r,\u0002\t1\fgnZ\u0005\u0005\u0003K\nyF\u0001\u0005Sk:t\u0017M\u00197f\u0011\u001d\tI\u0007\u0005a\u0001\u0003\u000b\tA\"\u001b8ji&\fG\u000eR3mCfDq!!\u001c\u0011\u0001\u0004\t)!A\u0003eK2\f\u00170\u0001\u0005tG\",G-\u001e7f)!\t\u0019(a\u001e\u0002z\u0005mD\u0003BA#\u0003kBq!!\u0014\u0012\u0001\b\ty\u0005C\u0004\u0002jE\u0001\r!!\u0002\t\u000f\u00055\u0014\u00031\u0001\u0002\u0006!9\u0011\u0011L\tA\u0002\u0005m\u0013\u0001D:dQ\u0016$W\u000f\\3P]\u000e,GCBAA\u0003\u000b\u000b9\t\u0006\u0003\u0002F\u0005\r\u0005bBA'%\u0001\u000f\u0011q\n\u0005\b\u0003[\u0012\u0002\u0019AA\u0003\u0011\u001d\tIF\u0005a\u0001\u00037\nQa\u00197pg\u0016$\"!a\r\u0002\u00195\f\u0007P\u0012:fcV,gnY=\u0016\u0005\u0005E\u0005cA%\u0002\u0014&\u0019\u0011Q\u0013&\u0003\r\u0011{WO\u00197f\u00035i\u0017\r\u001f$sKF,XM\\2zA\u0005)1\u000f^1siV\u0011\u0011QE\u0001\u0007gR\f'\u000f\u001e\u0011\u0002\u0013QL7m\u001b(b]>\u001c\u0018A\u0003;jG.t\u0015M\\8tA\u0005Iq\u000f[3fY6\u000b7o[\u0001\u000bo\",W\r\\'bg.\u0004\u0013!B9vKV,WCAAV!\r\tiK\u000b\b\u0003\u001f\u001a\n1\u0004T5hQR\f%O]1z%\u00164x\u000e\u001c<feN\u001b\u0007.\u001a3vY\u0016\u0014\bCA(('\t9\u0003\n\u0006\u0002\u00022\u0006QA/Y:l\u001f\u001a47/\u001a;\u0003\u0013Q\u000b7o[)vKV,7c\u0001\u0016\u0002>B1\u0011qXAc\u0003\u0013l!!!1\u000b\u0007\u0005\rG)\u0001\u0005eSN\u0004\u0018\r^2i\u0013\u0011\t9-!1\u0003#\u0005\u00137\u000f\u001e:bGRtu\u000eZ3Rk\u0016,X\rE\u0002\u0002L6j\u0011a\n\u0002\u000b)\u0006\u001c8\u000eS8mI\u0016\u00148#B\u0017\u0002R\u0006]\u0007\u0003BA/\u0003'LA!!6\u0002`\t1qJ\u00196fGR\u00042!a3-\u0005%!\u0016.\\3s)\u0006\u001c8nE\u0004-\u0003#\fY&!\u0012\u0002\tQ\f7o[\u000b\u0003\u00037\n\u0001\u0002^1tW~#S-\u001d\u000b\u0005\u0003g\t)\u000fC\u0005\u0002h>\n\t\u00111\u0001\u0002\\\u0005\u0019\u0001\u0010J\u0019\u0002\u000bQ\f7o\u001b\u0011)\u0007A\ni\u000fE\u0002J\u0003_L1!!=K\u0005!1x\u000e\\1uS2,\u0017!\u0002;jG.\u001c\u0018!\u0003;jG.\u001cx\fJ3r)\u0011\t\u0019$!?\t\u0011\u0005\u001d('!AA\u0002q\fa\u0001^5dWN\u0004\u0013\u0001E3yK\u000e,H/[8o\u0007>tG/\u001a=u)!\tIM!\u0001\u0003\u0004\t\u0015\u0001bBAok\u0001\u0007\u00111\f\u0005\u0007\u0003g,\u0004\u0019\u0001?\t\u000f\u0005uX\u00071\u0001\u0002P\u0005YQ\r\u001f;sC\u000e$H+Y:l)\u0011\tYFa\u0003\t\u000f\t5a\u00071\u0001\u0002\\\u0005Y!/\u001a9mC\u000e,w+\u001b;iQ\r1$\u0011\u0003\t\u0005\u0005'\u0011I\"\u0004\u0002\u0003\u0016)\u0019!q\u0003&\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0003\u0003\u001c\tU!a\u0002;bS2\u0014XmY\u0001\fKb,7-\u001e;f)\u0006\u001c8\u000e\u0006\u0002\u0003\"A\u0019\u0011Ja\t\n\u0007\t\u0015\"JA\u0004C_>dW-\u00198\u0002\u0007I,h.\u0001\u0004dC:\u001cW\r\\\u0001\fSN\u001c\u0015M\\2fY2,G-\u0006\u0002\u0003\"Q\u0011!\u0011\u0007\t\u0004\u0003\u0017T\u0013!D\"b]\u000e,G\u000e\\3e)\u0006\u001c8N\u0005\u0004\u00038\u0005E\u00171\f\u0004\u0007\u0005sY\u0004A!\u000e\u0003\u0019q\u0012XMZ5oK6,g\u000e\u001e \u0002\u0019\u0015CXmY;uK\u0012$\u0016m]6\u0013\r\t}\u0012\u0011[A.\r\u0019\u0011I\u0004\u0010\u0001\u0003>\u0005qaj\u001c;DC:\u001cW\r\u001c7bE2,WCAAl\u0003=qu\u000e^\"b]\u000e,G\u000e\\1cY\u0016\u0004\u0013aE%oSRL\u0017\r\u001c*fa\u0016\fG/T1sW\u0016\u0014XCAA#\u0003QIe.\u001b;jC2\u0014V\r]3bi6\u000b'o[3sA\u00051\u0011/^3vK\u0002\"\u0002B!\u0015\u0003T\t]#1\f\t\u0004\u0003[c\u0003b\u0002B+=\u0001\u0007\u0011qJ\u0001\u0003K\u000eDqA!\u0017\u001f\u0001\u0004\tY&A\u0001s\u0011\u001d\tiG\ba\u0001\u0003\u000b\tQb\u00195fG.l\u0015\r\u001f#fY\u0006LH\u0003BA\u001a\u0005CBqAa\u0019 \u0001\u0004\t)#\u0001\u0006eK2\f\u0017PT1o_N\fqa\u001d;paB,G-\u0006\u0002\u0003jA1!1\u000eB9\u0005kj!A!\u001c\u000b\u0007\t=d.\u0001\u0004bi>l\u0017nY\u0005\u0005\u0005g\u0012iGA\bBi>l\u0017n\u0019*fM\u0016\u0014XM\\2f!\u0019\t\tFa\u001e\u0003|%!!\u0011PA\u0007\u0005\u001d\u0001&o\\7jg\u0016\u0004bA! \u0003\b\nESB\u0001B@\u0015\u0011\u0011\tIa!\u0002\u0013%lW.\u001e;bE2,'b\u0001BC\u0015\u0006Q1m\u001c7mK\u000e$\u0018n\u001c8\n\t\t%%q\u0010\u0002\u0004'\u0016\f\u0018\u0001C:u_B\u0004X\r\u001a\u0011\u0002\tM$x\u000e\u001d\u000b\u0003\u0005#\u0003b!!\u0015\u0003\u0014\nm\u0014\u0002\u0002BK\u0003\u001b\u0011aAR;ukJ,\u0017a\u0003;j[\u0016\u0014H\u000b\u001b:fC\u0012,\"Aa'\u0011\t\u0005u#QT\u0005\u0005\u0005?\u000byF\u0001\u0004UQJ,\u0017\rZ\u0001\u0010i&lWM\u001d+ie\u0016\fGm\u0018\u0013fcR!\u00111\u0007BS\u0011%\t9\u000fJA\u0001\u0002\u0004\u0011Y*\u0001\u0007uS6,'\u000f\u00165sK\u0006$\u0007\u0005K\u0002&\u0003[\u0004")
public class LightArrayRevolverScheduler
implements Scheduler,
Closeable {
    public final LoggingAdapter akka$actor$LightArrayRevolverScheduler$$log;
    public final ThreadFactory akka$actor$LightArrayRevolverScheduler$$threadFactory;
    private final int WheelSize;
    private final FiniteDuration TickDuration;
    private final FiniteDuration ShutdownTimeout;
    private final double maxFrequency;
    private final long akka$actor$LightArrayRevolverScheduler$$start;
    private final long akka$actor$LightArrayRevolverScheduler$$tickNanos;
    private final int akka$actor$LightArrayRevolverScheduler$$wheelMask;
    private final TaskQueue akka$actor$LightArrayRevolverScheduler$$queue;
    private final AtomicReference<Promise<Seq<TimerTask>>> akka$actor$LightArrayRevolverScheduler$$stopped;
    private volatile Thread akka$actor$LightArrayRevolverScheduler$$timerThread;

    @Override
    public final Cancellable scheduleWithFixedDelay(Duration initialDelay, Duration delay, Runnable runnable2, ExecutionContext executor2) {
        return Scheduler.scheduleWithFixedDelay$((Scheduler)this, initialDelay, delay, runnable2, executor2);
    }

    @Override
    public final Cancellable scheduleWithFixedDelay(FiniteDuration initialDelay, FiniteDuration delay, ActorRef receiver, Object message, ExecutionContext executor2, ActorRef sender) {
        return Scheduler.scheduleWithFixedDelay$((Scheduler)this, initialDelay, delay, receiver, message, executor2, sender);
    }

    @Override
    public final ActorRef scheduleWithFixedDelay$default$6(FiniteDuration initialDelay, FiniteDuration delay, ActorRef receiver, Object message) {
        return Scheduler.scheduleWithFixedDelay$default$6$(this, initialDelay, delay, receiver, message);
    }

    @Override
    public final Cancellable scheduleWithFixedDelay(Duration initialDelay, Duration delay, ActorRef receiver, Object message, ExecutionContext executor2, ActorRef sender) {
        return Scheduler.scheduleWithFixedDelay$((Scheduler)this, initialDelay, delay, receiver, message, executor2, sender);
    }

    @Override
    public final Cancellable scheduleAtFixedRate(FiniteDuration initialDelay, FiniteDuration interval, Runnable runnable2, ExecutionContext executor2) {
        return Scheduler.scheduleAtFixedRate$((Scheduler)this, initialDelay, interval, runnable2, executor2);
    }

    @Override
    public final Cancellable scheduleAtFixedRate(Duration initialDelay, Duration interval, Runnable runnable2, ExecutionContext executor2) {
        return Scheduler.scheduleAtFixedRate$((Scheduler)this, initialDelay, interval, runnable2, executor2);
    }

    @Override
    public final Cancellable scheduleAtFixedRate(FiniteDuration initialDelay, FiniteDuration interval, ActorRef receiver, Object message, ExecutionContext executor2, ActorRef sender) {
        return Scheduler.scheduleAtFixedRate$((Scheduler)this, initialDelay, interval, receiver, message, executor2, sender);
    }

    @Override
    public final ActorRef scheduleAtFixedRate$default$6(FiniteDuration initialDelay, FiniteDuration interval, ActorRef receiver, Object message) {
        return Scheduler.scheduleAtFixedRate$default$6$(this, initialDelay, interval, receiver, message);
    }

    @Override
    public final Cancellable scheduleAtFixedRate(Duration initialDelay, Duration interval, ActorRef receiver, Object message, ExecutionContext executor2, ActorRef sender) {
        return Scheduler.scheduleAtFixedRate$((Scheduler)this, initialDelay, interval, receiver, message, executor2, sender);
    }

    @Override
    public final Cancellable schedule(FiniteDuration initialDelay, FiniteDuration interval, ActorRef receiver, Object message, ExecutionContext executor2, ActorRef sender) {
        return Scheduler.schedule$((Scheduler)this, initialDelay, interval, receiver, message, executor2, sender);
    }

    @Override
    public final ActorRef schedule$default$6(FiniteDuration initialDelay, FiniteDuration interval, ActorRef receiver, Object message) {
        return Scheduler.schedule$default$6$(this, initialDelay, interval, receiver, message);
    }

    @Override
    public final Cancellable schedule(Duration initialDelay, Duration interval, ActorRef receiver, Object message, ExecutionContext executor2, ActorRef sender) {
        return Scheduler.schedule$((Scheduler)this, initialDelay, interval, receiver, message, executor2, sender);
    }

    @Override
    public final Cancellable schedule(FiniteDuration initialDelay, FiniteDuration interval, Function0<BoxedUnit> f, ExecutionContext executor2) {
        return Scheduler.schedule$((Scheduler)this, initialDelay, interval, f, executor2);
    }

    @Override
    public Cancellable schedule(Duration initialDelay, Duration interval, Runnable runnable2, ExecutionContext executor2) {
        return Scheduler.schedule$((Scheduler)this, initialDelay, interval, runnable2, executor2);
    }

    @Override
    public final Cancellable scheduleOnce(FiniteDuration delay, ActorRef receiver, Object message, ExecutionContext executor2, ActorRef sender) {
        return Scheduler.scheduleOnce$((Scheduler)this, delay, receiver, message, executor2, sender);
    }

    @Override
    public final ActorRef scheduleOnce$default$5(FiniteDuration delay, ActorRef receiver, Object message) {
        return Scheduler.scheduleOnce$default$5$(this, delay, receiver, message);
    }

    @Override
    public final Cancellable scheduleOnce(Duration delay, ActorRef receiver, Object message, ExecutionContext executor2, ActorRef sender) {
        return Scheduler.scheduleOnce$((Scheduler)this, delay, receiver, message, executor2, sender);
    }

    @Override
    public final Cancellable scheduleOnce(FiniteDuration delay, Function0<BoxedUnit> f, ExecutionContext executor2) {
        return Scheduler.scheduleOnce$((Scheduler)this, delay, f, executor2);
    }

    @Override
    public Cancellable scheduleOnce(Duration delay, Runnable runnable2, ExecutionContext executor2) {
        return Scheduler.scheduleOnce$((Scheduler)this, delay, runnable2, executor2);
    }

    public int WheelSize() {
        return this.WheelSize;
    }

    public FiniteDuration TickDuration() {
        return this.TickDuration;
    }

    public FiniteDuration ShutdownTimeout() {
        return this.ShutdownTimeout;
    }

    public FiniteDuration akka$actor$LightArrayRevolverScheduler$$roundUp(FiniteDuration d) {
        long dn = d.toNanos();
        long r = ((dn - 1L) / this.akka$actor$LightArrayRevolverScheduler$$tickNanos() + 1L) * this.akka$actor$LightArrayRevolverScheduler$$tickNanos();
        if (r != dn && r > 0L && dn > 0L) {
            return new package.DurationLong(scala.concurrent.duration.package$.MODULE$.DurationLong(r)).nanos();
        }
        return d;
    }

    public long clock() {
        return System.nanoTime();
    }

    public int startTick() {
        return 0;
    }

    public FiniteDuration getShutdownTimeout() {
        return this.ShutdownTimeout();
    }

    public void waitNanos(long nanos) {
        long sleepMs = Helpers$.MODULE$.isWindows() ? (nanos + 4999999L) / 10000000L * 10L : (nanos + 999999L) / 1000000L;
        try {
            Thread.sleep(sleepMs);
        }
        catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public Cancellable scheduleWithFixedDelay(FiniteDuration initialDelay, FiniteDuration delay, Runnable runnable2, ExecutionContext executor2) {
        this.checkMaxDelay(this.akka$actor$LightArrayRevolverScheduler$$roundUp(delay).toNanos());
        return Scheduler.scheduleWithFixedDelay$((Scheduler)this, initialDelay, delay, runnable2, executor2);
    }

    @Override
    public Cancellable schedule(FiniteDuration initialDelay, FiniteDuration delay, Runnable runnable2, ExecutionContext executor2) {
        Cancellable cancellable;
        this.checkMaxDelay(this.akka$actor$LightArrayRevolverScheduler$$roundUp(delay).toNanos());
        try {
            cancellable = new Cancellable(this, executor2, initialDelay, runnable2, delay){
                private final /* synthetic */ LightArrayRevolverScheduler $outer;

                public void akka$actor$LightArrayRevolverScheduler$$anon$$swap(Cancellable c) {
                    Cancellable cancellable;
                    do {
                        if ((cancellable = (Cancellable)this.get()) != null) continue;
                        if (c != null) {
                            c.cancel();
                        }
                        break;
                    } while (!this.compareAndSet(cancellable, c));
                }

                public final boolean cancel() {
                    return this.tailrecCancel$1();
                }

                public boolean isCancelled() {
                    return this.get() == null;
                }

                public /* synthetic */ LightArrayRevolverScheduler akka$actor$LightArrayRevolverScheduler$$anon$$$outer() {
                    return this.$outer;
                }

                private final boolean tailrecCancel$1() {
                    Cancellable cancellable;
                    do {
                        if ((cancellable = (Cancellable)this.get()) == null) {
                            return false;
                        }
                        if (!cancellable.cancel()) continue;
                        return this.compareAndSet(cancellable, null);
                    } while (!this.compareAndSet(cancellable, null));
                    return true;
                }
                {
                    if ($outer == null) {
                        throw null;
                    }
                    this.$outer = $outer;
                    super(LightArrayRevolverScheduler$.MODULE$.akka$actor$LightArrayRevolverScheduler$$InitialRepeatMarker());
                    this.compareAndSet(LightArrayRevolverScheduler$.MODULE$.akka$actor$LightArrayRevolverScheduler$$InitialRepeatMarker(), $outer.akka$actor$LightArrayRevolverScheduler$$schedule(executor$1, new Runnable(this, initialDelay$1, runnable$1, delay$1, executor$1){
                        private final /* synthetic */ $anon$1 $outer;
                        private final Runnable runnable$1;
                        private final FiniteDuration delay$1;
                        private final ExecutionContext executor$1;

                        public void run() {
                            try {
                                this.runnable$1.run();
                                long driftNanos = this.$outer.akka$actor$LightArrayRevolverScheduler$$anon$$$outer().clock() - this.getAndAdd(this.delay$1.toNanos());
                                if (this.$outer.get() != null) {
                                    this.$outer.akka$actor$LightArrayRevolverScheduler$$anon$$swap(this.$outer.akka$actor$LightArrayRevolverScheduler$$anon$$$outer().akka$actor$LightArrayRevolverScheduler$$schedule(this.executor$1, this, Duration$.MODULE$.fromNanos(Math.max(this.delay$1.toNanos() - driftNanos, 1L))));
                                }
                            }
                            catch (SchedulerException schedulerException) {}
                        }
                        {
                            if ($outer == null) {
                                throw null;
                            }
                            this.$outer = $outer;
                            this.runnable$1 = runnable$1;
                            this.delay$1 = delay$1;
                            this.executor$1 = executor$1;
                            super($outer.akka$actor$LightArrayRevolverScheduler$$anon$$$outer().clock() + initialDelay$1.toNanos());
                        }
                    }, $outer.akka$actor$LightArrayRevolverScheduler$$roundUp(initialDelay$1)));
                }
            };
        }
        catch (Throwable throwable) {
            Throwable throwable2 = throwable;
            if (throwable2 instanceof SchedulerException) {
                SchedulerException schedulerException = (SchedulerException)throwable2;
                String msg = schedulerException.msg();
                throw new IllegalStateException(msg, schedulerException);
            }
            throw throwable;
        }
        return cancellable;
    }

    @Override
    public Cancellable scheduleOnce(FiniteDuration delay, Runnable runnable2, ExecutionContext executor2) {
        TimerTask timerTask2;
        try {
            timerTask2 = this.akka$actor$LightArrayRevolverScheduler$$schedule(executor2, runnable2, this.akka$actor$LightArrayRevolverScheduler$$roundUp(delay));
        }
        catch (Throwable throwable) {
            Throwable throwable2 = throwable;
            if (throwable2 instanceof SchedulerException) {
                SchedulerException schedulerException = (SchedulerException)throwable2;
                String msg = schedulerException.msg();
                throw new IllegalStateException(msg, schedulerException);
            }
            throw throwable;
        }
        return timerTask2;
    }

    @Override
    public void close() {
        ((IterableOnceOps)Await$.MODULE$.result(this.stop(), this.getShutdownTimeout())).foreach((Function1<TimerTask, Object> & Serializable)x0$1 -> {
            LightArrayRevolverScheduler.$anonfun$close$1(this, x0$1);
            return BoxedUnit.UNIT;
        });
    }

    @Override
    public double maxFrequency() {
        return this.maxFrequency;
    }

    public long akka$actor$LightArrayRevolverScheduler$$start() {
        return this.akka$actor$LightArrayRevolverScheduler$$start;
    }

    public long akka$actor$LightArrayRevolverScheduler$$tickNanos() {
        return this.akka$actor$LightArrayRevolverScheduler$$tickNanos;
    }

    public int akka$actor$LightArrayRevolverScheduler$$wheelMask() {
        return this.akka$actor$LightArrayRevolverScheduler$$wheelMask;
    }

    public TaskQueue akka$actor$LightArrayRevolverScheduler$$queue() {
        return this.akka$actor$LightArrayRevolverScheduler$$queue;
    }

    public TimerTask akka$actor$LightArrayRevolverScheduler$$schedule(ExecutionContext ec, Runnable r, FiniteDuration delay) {
        if (delay.length() <= 0L) {
            if (this.akka$actor$LightArrayRevolverScheduler$$stopped().get() != null) {
                throw new SchedulerException("cannot enqueue after timer shutdown");
            }
            ec.execute(r);
            return LightArrayRevolverScheduler$.MODULE$.akka$actor$LightArrayRevolverScheduler$$NotCancellable();
        }
        if (this.akka$actor$LightArrayRevolverScheduler$$stopped().get() != null) {
            throw new SchedulerException("cannot enqueue after timer shutdown");
        }
        long delayNanos = delay.toNanos();
        this.checkMaxDelay(delayNanos);
        int ticks = (int)(delayNanos / this.akka$actor$LightArrayRevolverScheduler$$tickNanos());
        TaskHolder task = new TaskHolder(r, ticks, ec);
        this.akka$actor$LightArrayRevolverScheduler$$queue().add(task);
        if (this.akka$actor$LightArrayRevolverScheduler$$stopped().get() != null && task.cancel()) {
            throw new SchedulerException("cannot enqueue after timer shutdown");
        }
        return task;
    }

    private void checkMaxDelay(long delayNanos) {
        if (delayNanos / this.akka$actor$LightArrayRevolverScheduler$$tickNanos() > Integer.MAX_VALUE) {
            throw new IllegalArgumentException(new StringBuilder(93).append("Task scheduled with [").append(new package.DurationLong(scala.concurrent.duration.package$.MODULE$.DurationLong(delayNanos)).nanos().toSeconds()).append("] seconds delay, ").append("which is too far in future, maximum delay is [").append(new package.DurationLong(scala.concurrent.duration.package$.MODULE$.DurationLong(this.akka$actor$LightArrayRevolverScheduler$$tickNanos() * Integer.MAX_VALUE)).nanos().toSeconds() - 1L).append("] seconds").toString());
        }
    }

    public AtomicReference<Promise<Seq<TimerTask>>> akka$actor$LightArrayRevolverScheduler$$stopped() {
        return this.akka$actor$LightArrayRevolverScheduler$$stopped;
    }

    private Future<Seq<TimerTask>> stop() {
        Promise p = Promise$.MODULE$.apply();
        if (this.akka$actor$LightArrayRevolverScheduler$$stopped().compareAndSet(null, p)) {
            return p.future();
        }
        return Future$.MODULE$.successful(Nil$.MODULE$);
    }

    private Thread timerThread() {
        return this.akka$actor$LightArrayRevolverScheduler$$timerThread;
    }

    public void akka$actor$LightArrayRevolverScheduler$$timerThread_$eq(Thread x$1) {
        this.akka$actor$LightArrayRevolverScheduler$$timerThread = x$1;
    }

    public static final /* synthetic */ boolean $anonfun$TickDuration$1(FiniteDuration x$1) {
        return x$1.$greater$eq(new package.DurationInt(scala.concurrent.duration.package$.MODULE$.DurationInt(10)).millis()) || !Helpers$.MODULE$.isWindows();
    }

    private final void runTask$1(Runnable task) {
        try {
            task.run();
        }
        catch (Throwable throwable) {
            Option<Throwable> option;
            Throwable throwable2 = throwable;
            if (throwable2 instanceof InterruptedException) {
                InterruptedException interruptedException = (InterruptedException)throwable2;
                throw interruptedException;
            }
            if (throwable2 instanceof SchedulerException) {
            }
            if (throwable2 != null && !(option = NonFatal$.MODULE$.unapply(throwable2)).isEmpty()) {
                Throwable e = option.get();
                this.akka$actor$LightArrayRevolverScheduler$$log.error(e, "exception while executing timer task");
            }
            throw throwable;
        }
    }

    public static final /* synthetic */ void $anonfun$close$1(LightArrayRevolverScheduler $this, TimerTask x0$1) {
        TimerTask timerTask2 = x0$1;
        if (timerTask2 instanceof Scheduler.TaskRunOnClose) {
            TimerTask timerTask3 = timerTask2;
            $this.runTask$1(timerTask3);
            return;
        }
        if (timerTask2 instanceof TaskHolder) {
            TaskHolder taskHolder = (TaskHolder)timerTask2;
            Runnable runnable2 = taskHolder.task();
            if (runnable2 instanceof Scheduler.TaskRunOnClose) {
                Scheduler.TaskRunOnClose taskRunOnClose = (Scheduler.TaskRunOnClose)runnable2;
                $this.runTask$1(taskRunOnClose);
            }
            return;
        }
    }

    public LightArrayRevolverScheduler(Config config, LoggingAdapter log, ThreadFactory threadFactory) {
        this.akka$actor$LightArrayRevolverScheduler$$log = log;
        this.akka$actor$LightArrayRevolverScheduler$$threadFactory = threadFactory;
        Scheduler.$init$(this);
        this.WheelSize = BoxesRunTime.unboxToInt(Helpers$Requiring$.MODULE$.requiring$extension(Helpers$.MODULE$.Requiring(BoxesRunTime.boxToInteger(config.getInt("akka.scheduler.ticks-per-wheel"))), ticks -> (ticks & ticks - 1) == 0, (Function0<Object>)(Function0<String> & Serializable)() -> "ticks-per-wheel must be a power of 2"));
        this.TickDuration = Helpers$Requiring$.MODULE$.requiring$extension(Helpers$.MODULE$.Requiring(Helpers$Requiring$.MODULE$.requiring$extension(Helpers$.MODULE$.Requiring(Helpers$ConfigOps$.MODULE$.getMillisDuration$extension(Helpers$.MODULE$.ConfigOps(config), "akka.scheduler.tick-duration")), (Function1<FiniteDuration, Object> & Serializable)x$1 -> BoxesRunTime.boxToBoolean(LightArrayRevolverScheduler.$anonfun$TickDuration$1(x$1)), (Function0<Object>)(Function0<String> & Serializable)() -> "minimum supported akka.scheduler.tick-duration on Windows is 10ms")), (Function1<FiniteDuration, Object> & Serializable)x$2 -> BoxesRunTime.boxToBoolean(x$2.$greater$eq(new package.DurationInt(scala.concurrent.duration.package$.MODULE$.DurationInt(1)).millis())), (Function0<Object>)(Function0<String> & Serializable)() -> "minimum supported akka.scheduler.tick-duration is 1ms");
        this.ShutdownTimeout = Helpers$ConfigOps$.MODULE$.getMillisDuration$extension(Helpers$.MODULE$.ConfigOps(config), "akka.scheduler.shutdown-timeout");
        this.maxFrequency = new package.DurationInt(scala.concurrent.duration.package$.MODULE$.DurationInt(1)).second().$div(this.TickDuration());
        this.akka$actor$LightArrayRevolverScheduler$$start = this.clock();
        this.akka$actor$LightArrayRevolverScheduler$$tickNanos = this.TickDuration().toNanos();
        this.akka$actor$LightArrayRevolverScheduler$$wheelMask = this.WheelSize() - 1;
        this.akka$actor$LightArrayRevolverScheduler$$queue = new TaskQueue();
        this.akka$actor$LightArrayRevolverScheduler$$stopped = new AtomicReference();
        this.akka$actor$LightArrayRevolverScheduler$$timerThread = threadFactory.newThread(new Runnable(this){
            private int tick;
            private long totalTick;
            private final TaskQueue[] wheel;
            private TaskQueue spareTaskQueue;
            private final /* synthetic */ LightArrayRevolverScheduler $outer;

            private int tick() {
                return this.tick;
            }

            private void tick_$eq(int x$1) {
                this.tick = x$1;
            }

            private long totalTick() {
                return this.totalTick;
            }

            private void totalTick_$eq(long x$1) {
                this.totalTick = x$1;
            }

            private TaskQueue[] wheel() {
                return this.wheel;
            }

            private TaskQueue spareTaskQueue() {
                return this.spareTaskQueue;
            }

            private void spareTaskQueue_$eq(TaskQueue x$1) {
                this.spareTaskQueue = x$1;
            }

            private Seq<TimerTask> clearAll() {
                return (Seq)((IterableOps)RichInt$.MODULE$.until$extension(Predef$.MODULE$.intWrapper(0), this.$outer.WheelSize()).flatMap((Function1<Object, Vector> & Serializable)i -> $anon$3.$anonfun$clearAll$1(this, BoxesRunTime.unboxToInt(i)))).$plus$plus(this.collect$1(this.$outer.akka$actor$LightArrayRevolverScheduler$$queue(), (Vector)package$.MODULE$.Vector().empty()));
            }

            private void checkQueue(long time) {
                while (true) {
                    AbstractNodeQueue.Node<T> node;
                    if ((node = this.$outer.akka$actor$LightArrayRevolverScheduler$$queue().pollNode()) == null) break;
                    int n = ((TaskHolder)node.value).ticks();
                    switch (n) {
                        case 0: {
                            Serializable serializable = BoxesRunTime.boxToBoolean(((TaskHolder)node.value).executeTask());
                            break;
                        }
                        default: {
                            int futureTick = (int)((time - this.$outer.akka$actor$LightArrayRevolverScheduler$$start() + (long)n * this.$outer.akka$actor$LightArrayRevolverScheduler$$tickNanos() + this.$outer.akka$actor$LightArrayRevolverScheduler$$tickNanos() - 1L) / this.$outer.akka$actor$LightArrayRevolverScheduler$$tickNanos());
                            int offset = futureTick - this.tick();
                            int bucket = futureTick & this.$outer.akka$actor$LightArrayRevolverScheduler$$wheelMask();
                            ((TaskHolder)node.value).ticks_$eq(offset);
                            this.wheel()[bucket].addNode(node);
                            Serializable serializable = BoxedUnit.UNIT;
                            break;
                        }
                    }
                }
            }

            public final void run() {
                try {
                    this.nextTick();
                }
                catch (Throwable t2) {
                    this.$outer.akka$actor$LightArrayRevolverScheduler$$log.error(t2, "exception on LARS\u2019 timer thread");
                    Promise<Seq<TimerTask>> promise = this.$outer.akka$actor$LightArrayRevolverScheduler$$stopped().get();
                    if (promise == null) {
                        Object object;
                        Thread thread2 = this.$outer.akka$actor$LightArrayRevolverScheduler$$threadFactory.newThread(this);
                        this.$outer.akka$actor$LightArrayRevolverScheduler$$log.info("starting new LARS thread");
                        try {
                            thread2.start();
                            object = BoxedUnit.UNIT;
                        }
                        catch (Throwable e) {
                            this.$outer.akka$actor$LightArrayRevolverScheduler$$log.error(e, "LARS cannot start new thread, ship\u2019s going down!");
                            this.$outer.akka$actor$LightArrayRevolverScheduler$$stopped().set(Promise$.MODULE$.successful(Nil$.MODULE$));
                            object = this.clearAll();
                        }
                        this.$outer.akka$actor$LightArrayRevolverScheduler$$timerThread_$eq(thread2);
                    } else {
                        Predef$.MODULE$.assert(this.$outer.akka$actor$LightArrayRevolverScheduler$$stopped().compareAndSet(promise, Promise$.MODULE$.successful(Nil$.MODULE$)), (Function0<Object>)(Function0<String> & Serializable)() -> "Stop signal violated in LARS");
                        promise.success(this.clearAll());
                    }
                    throw t2;
                }
            }

            private final void nextTick() {
                Promise<Seq<TimerTask>> promise;
                do {
                    long time = this.$outer.clock();
                    long sleepTime = this.$outer.akka$actor$LightArrayRevolverScheduler$$start() + this.totalTick() * this.$outer.akka$actor$LightArrayRevolverScheduler$$tickNanos() - time;
                    if (sleepTime > 0L) {
                        this.checkQueue(time);
                        this.$outer.waitNanos(sleepTime);
                    } else {
                        int bucket = this.tick() & this.$outer.akka$actor$LightArrayRevolverScheduler$$wheelMask();
                        TaskQueue tasks2 = this.wheel()[bucket];
                        TaskQueue putBack = this.spareTaskQueue();
                        this.executeBucket$1(tasks2, putBack);
                        this.wheel()[bucket] = putBack;
                        this.spareTaskQueue_$eq(tasks2);
                        this.tick_$eq(this.tick() + 1);
                        this.totalTick_$eq(this.totalTick() + 1L);
                    }
                    promise = this.$outer.akka$actor$LightArrayRevolverScheduler$$stopped().get();
                } while (promise == null);
                Predef$.MODULE$.assert(this.$outer.akka$actor$LightArrayRevolverScheduler$$stopped().compareAndSet(promise, Promise$.MODULE$.successful(Nil$.MODULE$)), (Function0<Object>)(Function0<String> & Serializable)() -> "Stop signal violated in LARS");
                promise.success(this.clearAll());
            }

            private final Vector collect$1(TaskQueue q, Vector acc) {
                TaskHolder taskHolder;
                while ((taskHolder = (TaskHolder)q.poll()) != null) {
                    acc = (Vector)acc.$colon$plus(taskHolder);
                }
                return acc;
            }

            public static final /* synthetic */ Vector $anonfun$clearAll$1($anon$3 $this, int i) {
                return $this.collect$1($this.wheel()[i], (Vector)package$.MODULE$.Vector().empty());
            }

            private final void executeBucket$1(TaskQueue tasks$1, TaskQueue putBack$1) {
                while (true) {
                    Serializable serializable;
                    AbstractNodeQueue.Node<T> node;
                    if ((node = tasks$1.pollNode()) == null) break;
                    TaskHolder task = (TaskHolder)node.value;
                    if (!task.isCancelled()) {
                        if (task.ticks() >= this.$outer.WheelSize()) {
                            task.ticks_$eq(task.ticks() - this.$outer.WheelSize());
                            putBack$1.addNode(node);
                            serializable = BoxedUnit.UNIT;
                            continue;
                        }
                        serializable = BoxesRunTime.boxToBoolean(task.executeTask());
                        continue;
                    }
                    serializable = BoxedUnit.UNIT;
                }
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                this.tick = $outer.startTick();
                this.totalTick = this.tick();
                this.wheel = (TaskQueue[])Array$.MODULE$.fill($outer.WheelSize(), (Function0<TaskQueue> & Serializable)() -> new TaskQueue(), ClassTag$.MODULE$.apply(TaskQueue.class));
                this.spareTaskQueue = new TaskQueue();
            }

            private static /* synthetic */ Object $deserializeLambda$(SerializedLambda serializedLambda) {
                return LambdaDeserialize.bootstrap("lambdaDeserialize", new MethodHandle[]{$anonfun$clearAll$1$adapted(akka.actor.LightArrayRevolverScheduler$$anon$3 java.lang.Object ), $anonfun$nextTick$1(), $anonfun$run$1(), $anonfun$wheel$1()}, serializedLambda);
            }
        });
        this.timerThread().start();
    }

    public static class TaskHolder
    implements TimerTask {
        private volatile Runnable task;
        private int ticks;
        private final ExecutionContext executionContext;

        public Runnable task() {
            return this.task;
        }

        public void task_$eq(Runnable x$1) {
            this.task = x$1;
        }

        public int ticks() {
            return this.ticks;
        }

        public void ticks_$eq(int x$1) {
            this.ticks = x$1;
        }

        private final Runnable extractTask(Runnable replaceWith) {
            while (true) {
                boolean bl;
                Runnable runnable2 = this.task();
                Runnable runnable3 = LightArrayRevolverScheduler$.akka$actor$LightArrayRevolverScheduler$$ExecutedTask;
                Runnable runnable4 = runnable2;
                if (!(runnable3 != null ? !runnable3.equals(runnable4) : runnable4 != null)) {
                    bl = true;
                } else {
                    Runnable runnable5 = LightArrayRevolverScheduler$.akka$actor$LightArrayRevolverScheduler$$CancelledTask;
                    Runnable runnable6 = runnable2;
                    bl = !(runnable5 != null ? !runnable5.equals(runnable6) : runnable6 != null);
                }
                if (bl) {
                    return runnable2;
                }
                if (!Unsafe.instance.compareAndSwapObject(this, LightArrayRevolverScheduler$.akka$actor$LightArrayRevolverScheduler$$taskOffset, runnable2, replaceWith)) continue;
                return runnable2;
            }
        }

        public final boolean executeTask() {
            boolean bl;
            boolean bl2;
            Runnable runnable2 = this.extractTask(LightArrayRevolverScheduler$.akka$actor$LightArrayRevolverScheduler$$ExecutedTask);
            Runnable runnable3 = LightArrayRevolverScheduler$.akka$actor$LightArrayRevolverScheduler$$ExecutedTask;
            Runnable runnable4 = runnable2;
            if (!(runnable3 != null ? !runnable3.equals(runnable4) : runnable4 != null)) {
                bl2 = true;
            } else {
                Runnable runnable5 = LightArrayRevolverScheduler$.akka$actor$LightArrayRevolverScheduler$$CancelledTask;
                Runnable runnable6 = runnable2;
                bl2 = !(runnable5 != null ? !runnable5.equals(runnable6) : runnable6 != null);
            }
            if (bl2) {
                return false;
            }
            try {
                this.executionContext.execute(runnable2);
                bl = true;
            }
            catch (Throwable throwable) {
                Option<Throwable> option;
                Throwable throwable2 = throwable;
                if (throwable2 instanceof InterruptedException) {
                    Thread.currentThread().interrupt();
                    bl = false;
                }
                if (throwable2 != null && !(option = NonFatal$.MODULE$.unapply(throwable2)).isEmpty()) {
                    Throwable e = option.get();
                    this.executionContext.reportFailure(e);
                    bl = false;
                }
                throw throwable;
            }
            return bl;
        }

        @Override
        public void run() {
            this.extractTask(LightArrayRevolverScheduler$.akka$actor$LightArrayRevolverScheduler$$ExecutedTask).run();
        }

        @Override
        public boolean cancel() {
            boolean bl;
            Runnable runnable2 = this.extractTask(LightArrayRevolverScheduler$.akka$actor$LightArrayRevolverScheduler$$CancelledTask);
            Runnable runnable3 = LightArrayRevolverScheduler$.akka$actor$LightArrayRevolverScheduler$$ExecutedTask;
            Runnable runnable4 = runnable2;
            if (!(runnable3 != null ? !runnable3.equals(runnable4) : runnable4 != null)) {
                bl = true;
            } else {
                Runnable runnable5 = LightArrayRevolverScheduler$.akka$actor$LightArrayRevolverScheduler$$CancelledTask;
                Runnable runnable6 = runnable2;
                bl = !(runnable5 != null ? !runnable5.equals(runnable6) : runnable6 != null);
            }
            return !bl;
        }

        @Override
        public boolean isCancelled() {
            return this.task() == LightArrayRevolverScheduler$.akka$actor$LightArrayRevolverScheduler$$CancelledTask;
        }

        public TaskHolder(Runnable task, int ticks, ExecutionContext executionContext) {
            this.task = task;
            this.ticks = ticks;
            this.executionContext = executionContext;
        }
    }

    private static class TaskQueue
    extends AbstractNodeQueue<TaskHolder> {
    }

    public static interface TimerTask
    extends Runnable,
    Cancellable {
    }
}

