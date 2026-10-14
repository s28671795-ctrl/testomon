/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.Done;
import akka.Done$;
import akka.actor.Actor;
import akka.actor.ActorContext;
import akka.actor.ActorRef;
import akka.actor.CoordinatedShutdownTerminationWatcher$;
import akka.actor.Props;
import akka.actor.SupervisorStrategy;
import akka.actor.Terminated;
import akka.actor.TimerScheduler;
import akka.actor.TimerSchedulerImpl;
import akka.actor.Timers;
import akka.annotation.InternalApi;
import java.io.Serializable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.SerializedLambda;
import java.util.concurrent.TimeoutException;
import scala.Function1;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.PartialFunction;
import scala.Predef$;
import scala.Product;
import scala.Some;
import scala.collection.Iterator;
import scala.collection.immutable.Map;
import scala.collection.immutable.Seq;
import scala.collection.immutable.Set;
import scala.concurrent.Promise;
import scala.concurrent.duration.Deadline;
import scala.concurrent.duration.FiniteDuration;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.LambdaDeserialize;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\t%vAB#G\u0011\u0003A%J\u0002\u0004M\r\"\u0005\u0001*\u0014\u0005\u0006)\u0006!\tA\u0016\u0004\u0005/\u0006\u0011\u0005\f\u0003\u0005H\u0007\tU\r\u0011\"\u0001i\u0011!a7A!E!\u0002\u0013I\u0007\u0002C7\u0004\u0005+\u0007I\u0011\u00018\t\u0011]\u001c!\u0011#Q\u0001\n=D\u0001\u0002_\u0002\u0003\u0016\u0004%\t!\u001f\u0005\n\u0003\u000b\u0019!\u0011#Q\u0001\niDa\u0001V\u0002\u0005\u0002\u0005\u001d\u0001\"CA\n\u0007\u0005\u0005I\u0011AA\u000b\u0011%\tibAI\u0001\n\u0003\ty\u0002C\u0005\u00026\r\t\n\u0011\"\u0001\u00028!I\u00111H\u0002\u0012\u0002\u0013\u0005\u0011Q\b\u0005\n\u0003\u0003\u001a\u0011\u0011!C!\u0003\u0007B\u0011\"!\u0016\u0004\u0003\u0003%\t!a\u0016\t\u0013\u0005}3!!A\u0005\u0002\u0005\u0005\u0004\"CA7\u0007\u0005\u0005I\u0011IA8\u0011%\tihAA\u0001\n\u0003\ty\bC\u0005\u0002\n\u000e\t\t\u0011\"\u0011\u0002\f\"I\u0011qR\u0002\u0002\u0002\u0013\u0005\u0013\u0011\u0013\u0005\n\u0003'\u001b\u0011\u0011!C!\u0003+C\u0011\"a&\u0004\u0003\u0003%\t%!'\b\u0013\u0005u\u0015!!A\t\u0002\u0005}e\u0001C,\u0002\u0003\u0003E\t!!)\t\rQKB\u0011AA]\u0011%\t\u0019*GA\u0001\n\u000b\n)\nC\u0005\u0002<f\t\t\u0011\"!\u0002>\"I\u0011QY\r\u0002\u0002\u0013\u0005\u0015q\u0019\u0005\n\u00033L\u0012\u0011!C\u0005\u000374a!a9\u0002\r\u0006\u0015\b\u0002C$ \u0005+\u0007I\u0011\u00015\t\u00111|\"\u0011#Q\u0001\n%D\u0001\u0002_\u0010\u0003\u0016\u0004%\t!\u001f\u0005\n\u0003\u000by\"\u0011#Q\u0001\niD!\"a: \u0005+\u0007I\u0011AAu\u0011)\t\tp\bB\tB\u0003%\u00111\u001e\u0005\u0007)~!\t!a=\t\u0013\u0005Mq$!A\u0005\u0002\u0005u\b\"CA\u000f?E\u0005I\u0011AA\u0010\u0011%\t)dHI\u0001\n\u0003\ti\u0004C\u0005\u0002<}\t\n\u0011\"\u0001\u0003\u0006!I\u0011\u0011I\u0010\u0002\u0002\u0013\u0005\u00131\t\u0005\n\u0003+z\u0012\u0011!C\u0001\u0003/B\u0011\"a\u0018 \u0003\u0003%\tA!\u0003\t\u0013\u00055t$!A\u0005B\u0005=\u0004\"CA??\u0005\u0005I\u0011\u0001B\u0007\u0011%\tIiHA\u0001\n\u0003\u0012\t\u0002C\u0005\u0002\u0010~\t\t\u0011\"\u0011\u0002\u0012\"I\u00111S\u0010\u0002\u0002\u0013\u0005\u0013Q\u0013\u0005\n\u0003/{\u0012\u0011!C!\u0005+9\u0011B!\u0007\u0002\u0003\u0003EIAa\u0007\u0007\u0013\u0005\r\u0018!!A\t\n\tu\u0001B\u0002+6\t\u0003\u0011\t\u0003C\u0005\u0002\u0014V\n\t\u0011\"\u0012\u0002\u0016\"I\u00111X\u001b\u0002\u0002\u0013\u0005%1\u0005\u0005\n\u0003\u000b,\u0014\u0011!CA\u0005WA\u0011\"!76\u0003\u0003%I!a7\t\u000f\tM\u0012\u0001\"\u0001\u00036\u00191AJ\u0012\u0001I\u0005\u0017Ba\u0001\u0016\u001f\u0005\u0002\te\u0003\"\u0003B/y\u0001\u0007I\u0011\u0002B0\u0011%\u00119\b\u0010a\u0001\n\u0013\u0011I\b\u0003\u0005\u0003\u0004r\u0002\u000b\u0015\u0002B1\u0011%\u0011)\t\u0010a\u0001\n\u0013\u00119\tC\u0005\u0003\u0014r\u0002\r\u0011\"\u0003\u0003\u0016\"A!\u0011\u0014\u001f!B\u0013\u0011I\tC\u0004\u0003\u001cr\"\tE!(\u0002K\r{wN\u001d3j]\u0006$X\rZ*ikR$wn\u001e8UKJl\u0017N\\1uS>tw+\u0019;dQ\u0016\u0014(BA$I\u0003\u0015\t7\r^8s\u0015\u0005I\u0015\u0001B1lW\u0006\u0004\"aS\u0001\u000e\u0003\u0019\u0013QeQ8pe\u0012Lg.\u0019;fINCW\u000f\u001e3po:$VM]7j]\u0006$\u0018n\u001c8XCR\u001c\u0007.\u001a:\u0014\u0005\u0005q\u0005CA(S\u001b\u0005\u0001&\"A)\u0002\u000bM\u001c\u0017\r\\1\n\u0005M\u0003&AB!osJ+g-\u0001\u0004=S:LGOP\u0002\u0001)\u0005Q%!B,bi\u000eD7\u0003B\u0002O3r\u0003\"a\u0014.\n\u0005m\u0003&a\u0002)s_\u0012,8\r\u001e\t\u0003;\u0016t!AX2\u000f\u0005}\u0013W\"\u00011\u000b\u0005\u0005,\u0016A\u0002\u001fs_>$h(C\u0001R\u0013\t!\u0007+A\u0004qC\u000e\\\u0017mZ3\n\u0005\u0019<'\u0001D*fe&\fG.\u001b>bE2,'B\u00013Q+\u0005I\u0007CA&k\u0013\tYgI\u0001\u0005BGR|'OU3g\u0003\u0019\t7\r^8sA\u0005AA-Z1eY&tW-F\u0001p!\t\u0001X/D\u0001r\u0015\t\u00118/\u0001\u0005ekJ\fG/[8o\u0015\t!\b+\u0001\u0006d_:\u001cWO\u001d:f]RL!A^9\u0003\u0011\u0011+\u0017\r\u001a7j]\u0016\f\u0011\u0002Z3bI2Lg.\u001a\u0011\u0002#\r|W\u000e\u001d7fi&|g\u000e\u0015:p[&\u001cX-F\u0001{!\rYHP`\u0007\u0002g&\u0011Qp\u001d\u0002\b!J|W.[:f!\ry\u0018\u0011A\u0007\u0002\u0011&\u0019\u00111\u0001%\u0003\t\u0011{g.Z\u0001\u0013G>l\u0007\u000f\\3uS>t\u0007K]8nSN,\u0007\u0005\u0006\u0005\u0002\n\u00055\u0011qBA\t!\r\tYaA\u0007\u0002\u0003!)qI\u0003a\u0001S\")QN\u0003a\u0001_\")\u0001P\u0003a\u0001u\u0006!1m\u001c9z)!\tI!a\u0006\u0002\u001a\u0005m\u0001bB$\f!\u0003\u0005\r!\u001b\u0005\b[.\u0001\n\u00111\u0001p\u0011\u001dA8\u0002%AA\u0002i\fabY8qs\u0012\"WMZ1vYR$\u0013'\u0006\u0002\u0002\")\u001a\u0011.a\t,\u0005\u0005\u0015\u0002\u0003BA\u0014\u0003ci!!!\u000b\u000b\t\u0005-\u0012QF\u0001\nk:\u001c\u0007.Z2lK\u0012T1!a\fQ\u0003)\tgN\\8uCRLwN\\\u0005\u0005\u0003g\tICA\tv]\u000eDWmY6fIZ\u000b'/[1oG\u0016\fabY8qs\u0012\"WMZ1vYR$#'\u0006\u0002\u0002:)\u001aq.a\t\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%gU\u0011\u0011q\b\u0016\u0004u\u0006\r\u0012!\u00049s_\u0012,8\r\u001e)sK\u001aL\u00070\u0006\u0002\u0002FA!\u0011qIA)\u001b\t\tIE\u0003\u0003\u0002L\u00055\u0013\u0001\u00027b]\u001eT!!a\u0014\u0002\t)\fg/Y\u0005\u0005\u0003'\nIE\u0001\u0004TiJLgnZ\u0001\raJ|G-^2u\u0003JLG/_\u000b\u0003\u00033\u00022aTA.\u0013\r\ti\u0006\u0015\u0002\u0004\u0013:$\u0018A\u00049s_\u0012,8\r^#mK6,g\u000e\u001e\u000b\u0005\u0003G\nI\u0007E\u0002P\u0003KJ1!a\u001aQ\u0005\r\te.\u001f\u0005\n\u0003W\n\u0012\u0011!a\u0001\u00033\n1\u0001\u001f\u00132\u0003=\u0001(o\u001c3vGRLE/\u001a:bi>\u0014XCAA9!\u0019\t\u0019(!\u001f\u0002d5\u0011\u0011Q\u000f\u0006\u0004\u0003o\u0002\u0016AC2pY2,7\r^5p]&!\u00111PA;\u0005!IE/\u001a:bi>\u0014\u0018\u0001C2b]\u0016\u000bX/\u00197\u0015\t\u0005\u0005\u0015q\u0011\t\u0004\u001f\u0006\r\u0015bAAC!\n9!i\\8mK\u0006t\u0007\"CA6'\u0005\u0005\t\u0019AA2\u0003I\u0001(o\u001c3vGR,E.Z7f]Rt\u0015-\\3\u0015\t\u0005\u0015\u0013Q\u0012\u0005\n\u0003W\"\u0012\u0011!a\u0001\u00033\n\u0001\u0002[1tQ\u000e{G-\u001a\u000b\u0003\u00033\n\u0001\u0002^8TiJLgn\u001a\u000b\u0003\u0003\u000b\na!Z9vC2\u001cH\u0003BAA\u00037C\u0011\"a\u001b\u0018\u0003\u0003\u0005\r!a\u0019\u0002\u000b]\u000bGo\u00195\u0011\u0007\u0005-\u0011dE\u0003\u001a\u0003G\u000by\u000bE\u0005\u0002&\u0006-\u0016n\u001c>\u0002\n5\u0011\u0011q\u0015\u0006\u0004\u0003S\u0003\u0016a\u0002:v]RLW.Z\u0005\u0005\u0003[\u000b9KA\tBEN$(/Y2u\rVt7\r^5p]N\u0002B!!-\u000286\u0011\u00111\u0017\u0006\u0005\u0003k\u000bi%\u0001\u0002j_&\u0019a-a-\u0015\u0005\u0005}\u0015!B1qa2LH\u0003CA\u0005\u0003\u007f\u000b\t-a1\t\u000b\u001dc\u0002\u0019A5\t\u000b5d\u0002\u0019A8\t\u000bad\u0002\u0019\u0001>\u0002\u000fUt\u0017\r\u001d9msR!\u0011\u0011ZAk!\u0015y\u00151ZAh\u0013\r\ti\r\u0015\u0002\u0007\u001fB$\u0018n\u001c8\u0011\r=\u000b\t.[8{\u0013\r\t\u0019\u000e\u0015\u0002\u0007)V\u0004H.Z\u001a\t\u0013\u0005]W$!AA\u0002\u0005%\u0011a\u0001=%a\u0005aqO]5uKJ+\u0007\u000f\\1dKR\u0011\u0011Q\u001c\t\u0005\u0003\u000f\ny.\u0003\u0003\u0002b\u0006%#AB(cU\u0016\u001cGOA\bXCR\u001c\u0007.\u001a3US6,GmT;u'\u0011yb*\u0017/\u0002\u000fQLW.Z8viV\u0011\u00111\u001e\t\u0004a\u00065\u0018bAAxc\nqa)\u001b8ji\u0016$UO]1uS>t\u0017\u0001\u0003;j[\u0016|W\u000f\u001e\u0011\u0015\u0011\u0005U\u0018q_A}\u0003w\u00042!a\u0003 \u0011\u00159e\u00051\u0001j\u0011\u0015Ah\u00051\u0001{\u0011\u001d\t9O\na\u0001\u0003W$\u0002\"!>\u0002\u0000\n\u0005!1\u0001\u0005\b\u000f\u001e\u0002\n\u00111\u0001j\u0011\u001dAx\u0005%AA\u0002iD\u0011\"a:(!\u0003\u0005\r!a;\u0016\u0005\t\u001d!\u0006BAv\u0003G!B!a\u0019\u0003\f!I\u00111N\u0017\u0002\u0002\u0003\u0007\u0011\u0011\f\u000b\u0005\u0003\u0003\u0013y\u0001C\u0005\u0002l=\n\t\u00111\u0001\u0002dQ!\u0011Q\tB\n\u0011%\tY\u0007MA\u0001\u0002\u0004\tI\u0006\u0006\u0003\u0002\u0002\n]\u0001\"CA6g\u0005\u0005\t\u0019AA2\u0003=9\u0016\r^2iK\u0012$\u0016.\\3e\u001fV$\bcAA\u0006kM)QGa\b\u00020BQ\u0011QUAVSj\fY/!>\u0015\u0005\tmA\u0003CA{\u0005K\u00119C!\u000b\t\u000b\u001dC\u0004\u0019A5\t\u000baD\u0004\u0019\u0001>\t\u000f\u0005\u001d\b\b1\u0001\u0002lR!!Q\u0006B\u0019!\u0015y\u00151\u001aB\u0018!\u001dy\u0015\u0011[5{\u0003WD\u0011\"a6:\u0003\u0003\u0005\r!!>\u0002\u000bA\u0014x\u000e]:\u0016\u0005\t]\u0002cA&\u0003:%\u0019!1\b$\u0003\u000bA\u0013x\u000e]:)\u0007\u0005\u0011y\u0004\u0005\u0003\u0003B\t\u0015SB\u0001B\"\u0015\r\ty\u0003S\u0005\u0005\u0005\u000f\u0012\u0019EA\u0006J]R,'O\\1m\u0003BL\u0007f\u0001\u0001\u0003@M1AH\u0014B'\u0005'\u00022a\u0013B(\u0013\r\u0011\tF\u0012\u0002\u0006\u0003\u000e$xN\u001d\t\u0004\u0017\nU\u0013b\u0001B,\r\n1A+[7feN$\"Aa\u0017\u0011\u0005-c\u0014aD<bSRLgn\u001a$pe\u0006\u001bGo\u001c:\u0016\u0005\t\u0005\u0004c\u0002B2\u0005WJ'\u0011\u000f\b\u0005\u0005K\u00129\u0007\u0005\u0002`!&\u0019!\u0011\u000e)\u0002\rA\u0013X\rZ3g\u0013\u0011\u0011iGa\u001c\u0003\u00075\u000b\u0007OC\u0002\u0003jA\u0003RAa\u0019\u0003tiLAA!\u001e\u0003p\t\u00191+\u001a;\u0002']\f\u0017\u000e^5oO\u001a{'/Q2u_J|F%Z9\u0015\t\tm$\u0011\u0011\t\u0004\u001f\nu\u0014b\u0001B@!\n!QK\\5u\u0011%\tYgPA\u0001\u0002\u0004\u0011\t'\u0001\txC&$\u0018N\\4G_J\f5\r^8sA\u0005)\u0012\r\u001c:fC\u0012L8+Z3o)\u0016\u0014X.\u001b8bi\u0016$WC\u0001BE!\u0015\u0011YI!%j\u001b\t\u0011iI\u0003\u0003\u0003\u0010\u0006U\u0014!C5n[V$\u0018M\u00197f\u0013\u0011\u0011)H!$\u00023\u0005d'/Z1esN+WM\u001c+fe6Lg.\u0019;fI~#S-\u001d\u000b\u0005\u0005w\u00129\nC\u0005\u0002l\t\u000b\t\u00111\u0001\u0003\n\u00061\u0012\r\u001c:fC\u0012L8+Z3o)\u0016\u0014X.\u001b8bi\u0016$\u0007%A\u0004sK\u000e,\u0017N^3\u0016\u0005\t}\u0005\u0003\u0002BQ\u0005Gk\u0011\u0001P\u0005\u0005\u0005K\u0013yEA\u0004SK\u000e,\u0017N^3)\u0007q\u0012y\u0004")
@InternalApi
public class CoordinatedShutdownTerminationWatcher
implements Timers {
    private Map<ActorRef, Set<Promise<Done>>> akka$actor$CoordinatedShutdownTerminationWatcher$$waitingForActor;
    private Set<ActorRef> akka$actor$CoordinatedShutdownTerminationWatcher$$alreadySeenTerminated;
    private TimerSchedulerImpl akka$actor$Timers$$_timers;
    private ActorContext context;
    private ActorRef self;

    public static Props props() {
        return CoordinatedShutdownTerminationWatcher$.MODULE$.props();
    }

    @Override
    public /* synthetic */ void akka$actor$Timers$$super$aroundPreRestart(Throwable reason, Option message) {
        Actor.aroundPreRestart$(this, reason, message);
    }

    @Override
    public /* synthetic */ void akka$actor$Timers$$super$aroundPostStop() {
        Actor.aroundPostStop$(this);
    }

    @Override
    public /* synthetic */ void akka$actor$Timers$$super$aroundReceive(PartialFunction receive2, Object msg) {
        Actor.aroundReceive$(this, receive2, msg);
    }

    @Override
    public final TimerScheduler timers() {
        return Timers.timers$(this);
    }

    @Override
    public void aroundPreRestart(Throwable reason, Option<Object> message) {
        Timers.aroundPreRestart$(this, reason, message);
    }

    @Override
    public void aroundPostStop() {
        Timers.aroundPostStop$(this);
    }

    @Override
    public void aroundReceive(PartialFunction<Object, BoxedUnit> receive2, Object msg) {
        Timers.aroundReceive$(this, receive2, msg);
    }

    @Override
    public final ActorRef sender() {
        return Actor.sender$(this);
    }

    @Override
    @InternalApi
    public void aroundPreStart() {
        Actor.aroundPreStart$(this);
    }

    @Override
    @InternalApi
    public void aroundPostRestart(Throwable reason) {
        Actor.aroundPostRestart$(this, reason);
    }

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return Actor.supervisorStrategy$(this);
    }

    @Override
    public void preStart() throws Exception {
        Actor.preStart$(this);
    }

    @Override
    public void postStop() throws Exception {
        Actor.postStop$(this);
    }

    @Override
    public void preRestart(Throwable reason, Option<Object> message) throws Exception {
        Actor.preRestart$(this, reason, message);
    }

    @Override
    public void postRestart(Throwable reason) throws Exception {
        Actor.postRestart$(this, reason);
    }

    @Override
    public void unhandled(Object message) {
        Actor.unhandled$(this, message);
    }

    @Override
    public TimerSchedulerImpl akka$actor$Timers$$_timers() {
        return this.akka$actor$Timers$$_timers;
    }

    @Override
    public final void akka$actor$Timers$_setter_$akka$actor$Timers$$_timers_$eq(TimerSchedulerImpl x$1) {
        this.akka$actor$Timers$$_timers = x$1;
    }

    @Override
    public ActorContext context() {
        return this.context;
    }

    @Override
    public final ActorRef self() {
        return this.self;
    }

    @Override
    public void akka$actor$Actor$_setter_$context_$eq(ActorContext x$1) {
        this.context = x$1;
    }

    @Override
    public final void akka$actor$Actor$_setter_$self_$eq(ActorRef x$1) {
        this.self = x$1;
    }

    public Map<ActorRef, Set<Promise<Done>>> akka$actor$CoordinatedShutdownTerminationWatcher$$waitingForActor() {
        return this.akka$actor$CoordinatedShutdownTerminationWatcher$$waitingForActor;
    }

    public void akka$actor$CoordinatedShutdownTerminationWatcher$$waitingForActor_$eq(Map<ActorRef, Set<Promise<Done>>> x$1) {
        this.akka$actor$CoordinatedShutdownTerminationWatcher$$waitingForActor = x$1;
    }

    public Set<ActorRef> akka$actor$CoordinatedShutdownTerminationWatcher$$alreadySeenTerminated() {
        return this.akka$actor$CoordinatedShutdownTerminationWatcher$$alreadySeenTerminated;
    }

    public void akka$actor$CoordinatedShutdownTerminationWatcher$$alreadySeenTerminated_$eq(Set<ActorRef> x$1) {
        this.akka$actor$CoordinatedShutdownTerminationWatcher$$alreadySeenTerminated = x$1;
    }

    @Override
    public PartialFunction<Object, BoxedUnit> receive() {
        return new Serializable(this){
            private static final long serialVersionUID = 0L;
            private final /* synthetic */ CoordinatedShutdownTerminationWatcher $outer;

            public final <A1, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                A1 A1 = x1;
                if (A1 instanceof Watch) {
                    Set set;
                    Watch watch = (Watch)A1;
                    ActorRef actor = watch.actor();
                    Deadline deadline = watch.deadline();
                    Promise<Done> completionPromise = watch.completionPromise();
                    if (this.$outer.akka$actor$CoordinatedShutdownTerminationWatcher$$alreadySeenTerminated().apply(actor)) {
                        completionPromise.trySuccess(Done$.MODULE$);
                        return (B1)BoxedUnit.UNIT;
                    }
                    Option<V> option = this.$outer.akka$actor$CoordinatedShutdownTerminationWatcher$$waitingForActor().get(actor);
                    if (option instanceof Some) {
                        Some some = (Some)option;
                        Set alreadyWaiting = (Set)some.value();
                        set = (Set)alreadyWaiting.$plus(completionPromise);
                    } else if (None$.MODULE$.equals(option)) {
                        this.$outer.context().watch(actor);
                        set = (Set)Predef$.MODULE$.Set().apply((Seq)ScalaRunTime$.MODULE$.wrapRefArray((Object[])new Promise[]{completionPromise}));
                    } else {
                        throw new MatchError(option);
                    }
                    Set newWaiting = set;
                    this.$outer.akka$actor$CoordinatedShutdownTerminationWatcher$$waitingForActor_$eq((Map)this.$outer.akka$actor$CoordinatedShutdownTerminationWatcher$$waitingForActor().updated(actor, newWaiting));
                    this.$outer.timers().startSingleTimer(completionPromise, (Object)new WatchedTimedOut(actor, completionPromise, deadline.time()), deadline.timeLeft());
                    return (B1)BoxedUnit.UNIT;
                }
                if (A1 instanceof Terminated) {
                    Terminated terminated = (Terminated)A1;
                    ActorRef actor = terminated.actor();
                    this.$outer.akka$actor$CoordinatedShutdownTerminationWatcher$$waitingForActor().get(actor).foreach((Function1<Set, Object> & Serializable)waitingPromises -> {
                        $anonfun$receive$1.$anonfun$applyOrElse$1(this, waitingPromises);
                        return BoxedUnit.UNIT;
                    });
                    this.$outer.akka$actor$CoordinatedShutdownTerminationWatcher$$waitingForActor_$eq((Map)this.$outer.akka$actor$CoordinatedShutdownTerminationWatcher$$waitingForActor().$minus(actor));
                    this.$outer.akka$actor$CoordinatedShutdownTerminationWatcher$$alreadySeenTerminated_$eq((Set)this.$outer.akka$actor$CoordinatedShutdownTerminationWatcher$$alreadySeenTerminated().$plus(actor));
                    return (B1)BoxedUnit.UNIT;
                }
                if (A1 instanceof WatchedTimedOut) {
                    WatchedTimedOut watchedTimedOut = (WatchedTimedOut)A1;
                    ActorRef actor = watchedTimedOut.actor();
                    Promise<Done> completionPromise = watchedTimedOut.completionPromise();
                    FiniteDuration timeout2 = watchedTimedOut.timeout();
                    if (!completionPromise.isCompleted()) {
                        completionPromise.tryFailure(new TimeoutException(new StringBuilder(67).append("Actor [").append(actor).append("] termination timed out after [").append(timeout2).append("] during coordinated shutdown").toString()));
                        return (B1)BoxedUnit.UNIT;
                    }
                    return (B1)BoxedUnit.UNIT;
                }
                return function1.apply(x1);
            }

            public final boolean isDefinedAt(Object x1) {
                Object object = x1;
                if (object instanceof Watch) {
                    return true;
                }
                if (object instanceof Terminated) {
                    return true;
                }
                return object instanceof WatchedTimedOut;
            }

            public static final /* synthetic */ void $anonfun$applyOrElse$2($anonfun$receive$1 $this, Promise waitingPromise) {
                if (waitingPromise.trySuccess(Done$.MODULE$)) {
                    $this.$outer.timers().cancel(waitingPromise);
                    return;
                }
            }

            public static final /* synthetic */ void $anonfun$applyOrElse$1($anonfun$receive$1 $this, Set waitingPromises) {
                waitingPromises.foreach((Function1<Promise, Object> & Serializable)waitingPromise -> {
                    $anonfun$receive$1.$anonfun$applyOrElse$2($this, waitingPromise);
                    return BoxedUnit.UNIT;
                });
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
            }

            private static /* synthetic */ Object $deserializeLambda$(SerializedLambda serializedLambda) {
                return LambdaDeserialize.bootstrap("lambdaDeserialize", new MethodHandle[]{$anonfun$applyOrElse$1$adapted(akka.actor.CoordinatedShutdownTerminationWatcher$$anonfun$receive$1 scala.collection.immutable.Set ), $anonfun$applyOrElse$2$adapted(akka.actor.CoordinatedShutdownTerminationWatcher$$anonfun$receive$1 scala.concurrent.Promise )}, serializedLambda);
            }
        };
    }

    public CoordinatedShutdownTerminationWatcher() {
        Actor.$init$(this);
        Timers.$init$(this);
        this.akka$actor$CoordinatedShutdownTerminationWatcher$$waitingForActor = Predef$.MODULE$.Map().empty();
        this.akka$actor$CoordinatedShutdownTerminationWatcher$$alreadySeenTerminated = Predef$.MODULE$.Set().empty();
        Statics.releaseFence();
    }

    public static final class Watch
    implements Product,
    Serializable {
        private final ActorRef actor;
        private final Deadline deadline;
        private final Promise<Done> completionPromise;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public ActorRef actor() {
            return this.actor;
        }

        public Deadline deadline() {
            return this.deadline;
        }

        public Promise<Done> completionPromise() {
            return this.completionPromise;
        }

        public Watch copy(ActorRef actor, Deadline deadline, Promise<Done> completionPromise) {
            return new Watch(actor, deadline, completionPromise);
        }

        public ActorRef copy$default$1() {
            return this.actor();
        }

        public Deadline copy$default$2() {
            return this.deadline();
        }

        public Promise<Done> copy$default$3() {
            return this.completionPromise();
        }

        @Override
        public String productPrefix() {
            return "Watch";
        }

        @Override
        public int productArity() {
            return 3;
        }

        @Override
        public Object productElement(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return this.actor();
                }
                case 1: {
                    return this.deadline();
                }
                case 2: {
                    return this.completionPromise();
                }
            }
            return Statics.ioobe(x$1);
        }

        @Override
        public Iterator<Object> productIterator() {
            return ScalaRunTime$.MODULE$.typedProductIterator(this);
        }

        @Override
        public boolean canEqual(Object x$1) {
            return x$1 instanceof Watch;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "actor";
                }
                case 1: {
                    return "deadline";
                }
                case 2: {
                    return "completionPromise";
                }
            }
            return (String)Statics.ioobe(x$1);
        }

        public int hashCode() {
            return ScalaRunTime$.MODULE$._hashCode(this);
        }

        public String toString() {
            return ScalaRunTime$.MODULE$._toString(this);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public boolean equals(Object x$1) {
            if (this == x$1) return true;
            Object object = x$1;
            if (!(object instanceof Watch)) return false;
            boolean bl = true;
            if (!bl) return false;
            Watch watch = (Watch)x$1;
            ActorRef actorRef2 = this.actor();
            ActorRef actorRef3 = watch.actor();
            if (actorRef2 == null) {
                if (actorRef3 != null) {
                    return false;
                }
            } else if (!((Object)actorRef2).equals(actorRef3)) return false;
            Deadline deadline = this.deadline();
            Deadline deadline2 = watch.deadline();
            if (deadline == null) {
                if (deadline2 != null) {
                    return false;
                }
            } else if (!((Object)deadline).equals(deadline2)) return false;
            Promise<Done> promise = this.completionPromise();
            Promise<Done> promise2 = watch.completionPromise();
            if (promise == null) {
                if (promise2 == null) return true;
                return false;
            } else {
                if (!promise.equals(promise2)) return false;
                return true;
            }
        }

        public Watch(ActorRef actor, Deadline deadline, Promise<Done> completionPromise) {
            this.actor = actor;
            this.deadline = deadline;
            this.completionPromise = completionPromise;
            Product.$init$(this);
        }
    }

    private static final class WatchedTimedOut
    implements Product,
    Serializable {
        private final ActorRef actor;
        private final Promise<Done> completionPromise;
        private final FiniteDuration timeout;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public ActorRef actor() {
            return this.actor;
        }

        public Promise<Done> completionPromise() {
            return this.completionPromise;
        }

        public FiniteDuration timeout() {
            return this.timeout;
        }

        public WatchedTimedOut copy(ActorRef actor, Promise<Done> completionPromise, FiniteDuration timeout2) {
            return new WatchedTimedOut(actor, completionPromise, timeout2);
        }

        public ActorRef copy$default$1() {
            return this.actor();
        }

        public Promise<Done> copy$default$2() {
            return this.completionPromise();
        }

        public FiniteDuration copy$default$3() {
            return this.timeout();
        }

        @Override
        public String productPrefix() {
            return "WatchedTimedOut";
        }

        @Override
        public int productArity() {
            return 3;
        }

        @Override
        public Object productElement(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return this.actor();
                }
                case 1: {
                    return this.completionPromise();
                }
                case 2: {
                    return this.timeout();
                }
            }
            return Statics.ioobe(x$1);
        }

        @Override
        public Iterator<Object> productIterator() {
            return ScalaRunTime$.MODULE$.typedProductIterator(this);
        }

        @Override
        public boolean canEqual(Object x$1) {
            return x$1 instanceof WatchedTimedOut;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "actor";
                }
                case 1: {
                    return "completionPromise";
                }
                case 2: {
                    return "timeout";
                }
            }
            return (String)Statics.ioobe(x$1);
        }

        public int hashCode() {
            return ScalaRunTime$.MODULE$._hashCode(this);
        }

        public String toString() {
            return ScalaRunTime$.MODULE$._toString(this);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public boolean equals(Object x$1) {
            if (this == x$1) return true;
            Object object = x$1;
            if (!(object instanceof WatchedTimedOut)) return false;
            boolean bl = true;
            if (!bl) return false;
            WatchedTimedOut watchedTimedOut = (WatchedTimedOut)x$1;
            ActorRef actorRef2 = this.actor();
            ActorRef actorRef3 = watchedTimedOut.actor();
            if (actorRef2 == null) {
                if (actorRef3 != null) {
                    return false;
                }
            } else if (!((Object)actorRef2).equals(actorRef3)) return false;
            Promise<Done> promise = this.completionPromise();
            Promise<Done> promise2 = watchedTimedOut.completionPromise();
            if (promise == null) {
                if (promise2 != null) {
                    return false;
                }
            } else if (!promise.equals(promise2)) return false;
            FiniteDuration finiteDuration = this.timeout();
            FiniteDuration finiteDuration2 = watchedTimedOut.timeout();
            if (finiteDuration == null) {
                if (finiteDuration2 == null) return true;
                return false;
            } else {
                if (!((Object)finiteDuration).equals(finiteDuration2)) return false;
                return true;
            }
        }

        public WatchedTimedOut(ActorRef actor, Promise<Done> completionPromise, FiniteDuration timeout2) {
            this.actor = actor;
            this.completionPromise = completionPromise;
            this.timeout = timeout2;
            Product.$init$(this);
        }
    }
}

