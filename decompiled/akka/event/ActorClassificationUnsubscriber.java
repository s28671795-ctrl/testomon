/*
 * Decompiled with CFR 0.152.
 */
package akka.event;

import akka.actor.Actor;
import akka.actor.ActorContext;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Stash;
import akka.actor.StashSupport;
import akka.actor.SupervisorStrategy;
import akka.actor.Terminated;
import akka.actor.UnrestrictedStash;
import akka.annotation.InternalApi;
import akka.annotation.InternalStableApi;
import akka.dispatch.DequeBasedMessageQueueSemantics;
import akka.dispatch.Envelope;
import akka.event.ActorClassificationUnsubscriber$;
import akka.event.Logging;
import akka.event.Logging$;
import java.io.Serializable;
import scala.Function1;
import scala.Option;
import scala.PartialFunction;
import scala.Product;
import scala.collection.Iterator;
import scala.collection.immutable.Seq;
import scala.collection.immutable.Vector;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\t=e!B#G\u0011!S\u0005\u0002\u0003.\u0001\u0005\u0003\u0005\u000b\u0011\u0002/\t\u0011\u001d\u0004!\u0011!Q\u0001\n!D\u0001\"\u001d\u0001\u0003\u0002\u0003\u0006IA\u001d\u0005\u0006k\u0002!\tA\u001e\u0005\by\u0002\u0001\r\u0011\"\u0003~\u0011%\t\u0019\u0001\u0001a\u0001\n\u0013\t)\u0001C\u0004\u0002\f\u0001\u0001\u000b\u0015\u0002@\t\r\u00055\u0001\u0001\"\u0003~\u0011\u001d\ty\u0001\u0001C!\u0003#Aq!a\u0005\u0001\t\u0003\t)b\u0002\u0005\u0002$\u0019C\t\u0001SA\u0013\r\u001d)e\t#\u0001I\u0003OAa!\u001e\u0007\u0005\u0002\u0005%\u0002\"CA\u0016\u0019\t\u0007I\u0011BA\u0017\u0011!\t9\u0005\u0004Q\u0001\n\u0005=bABA%\u0019\t\u000bY\u0005C\u0005U!\tU\r\u0011\"\u0001\u0002f!I\u0011q\r\t\u0003\u0012\u0003\u0006Ia\u001b\u0005\n\u0003S\u0002\"Q3A\u0005\u0002uD\u0011\"a\u001b\u0011\u0005#\u0005\u000b\u0011\u0002@\t\rU\u0004B\u0011AA7\u0011%\t9\bEA\u0001\n\u0003\tI\bC\u0005\u0002\u0000A\t\n\u0011\"\u0001\u0002\u0002\"I\u0011q\u0013\t\u0012\u0002\u0013\u0005\u0011\u0011\u0014\u0005\n\u0003;\u0003\u0012\u0011!C!\u0003?C\u0001\"a+\u0011\u0003\u0003%\t! \u0005\n\u0003[\u0003\u0012\u0011!C\u0001\u0003_C\u0011\"a-\u0011\u0003\u0003%\t%!.\t\u0013\u0005\r\u0007#!A\u0005\u0002\u0005\u0015\u0007\"CAe!\u0005\u0005I\u0011IAf\u0011%\ty\rEA\u0001\n\u0003\n\t\u000eC\u0005\u0002TB\t\t\u0011\"\u0011\u0002V\"I\u0011q\u001b\t\u0002\u0002\u0013\u0005\u0013\u0011\\\u0004\n\u0003;d\u0011\u0011!E\u0001\u0003?4\u0011\"!\u0013\r\u0003\u0003E\t!!9\t\rU\u001cC\u0011AA}\u0011%\t\u0019nIA\u0001\n\u000b\n)\u000eC\u0005\u0002|\u000e\n\t\u0011\"!\u0002~\"I!1A\u0012\u0002\u0002\u0013\u0005%Q\u0001\u0005\n\u0005/\u0019\u0013\u0011!C\u0005\u000531aA!\t\r\u0005\n\r\u0002\"\u0003+*\u0005+\u0007I\u0011AA3\u0011%\t9'\u000bB\tB\u0003%1\u000eC\u0005\u0002j%\u0012)\u001a!C\u0001{\"I\u00111N\u0015\u0003\u0012\u0003\u0006IA \u0005\u0007k&\"\tA!\n\t\u0013\u0005]\u0014&!A\u0005\u0002\t5\u0002\"CA@SE\u0005I\u0011AAA\u0011%\t9*KI\u0001\n\u0003\tI\nC\u0005\u0002\u001e&\n\t\u0011\"\u0011\u0002 \"A\u00111V\u0015\u0002\u0002\u0013\u0005Q\u0010C\u0005\u0002.&\n\t\u0011\"\u0001\u00034!I\u00111W\u0015\u0002\u0002\u0013\u0005\u0013Q\u0017\u0005\n\u0003\u0007L\u0013\u0011!C\u0001\u0005oA\u0011\"!3*\u0003\u0003%\tEa\u000f\t\u0013\u0005=\u0017&!A\u0005B\u0005E\u0007\"CAjS\u0005\u0005I\u0011IAk\u0011%\t9.KA\u0001\n\u0003\u0012ydB\u0005\u0003D1\t\t\u0011#\u0001\u0003F\u0019I!\u0011\u0005\u0007\u0002\u0002#\u0005!q\t\u0005\u0007kr\"\tAa\u0013\t\u0013\u0005MG(!A\u0005F\u0005U\u0007\"CA~y\u0005\u0005I\u0011\u0011B'\u0011%\u0011\u0019\u0001PA\u0001\n\u0003\u0013\u0019\u0006C\u0005\u0003\u0018q\n\t\u0011\"\u0003\u0003\u001a!9!q\u000b\u0007\u0005\u0002\te\u0003\"\u0003B=\u0019E\u0005I\u0011\u0001B>\u0011\u001d\u0011y\b\u0004C\u0005\u0005\u0003\u0013q$Q2u_J\u001cE.Y:tS\u001aL7-\u0019;j_:,fn];cg\u000e\u0014\u0018NY3s\u0015\t9\u0005*A\u0003fm\u0016tGOC\u0001J\u0003\u0011\t7n[1\u0014\t\u0001Y\u0015k\u0016\t\u0003\u0019>k\u0011!\u0014\u0006\u0002\u001d\u0006)1oY1mC&\u0011\u0001+\u0014\u0002\u0007\u0003:L(+\u001a4\u0011\u0005I+V\"A*\u000b\u0005QC\u0015!B1di>\u0014\u0018B\u0001,T\u0005\u0015\t5\r^8s!\t\u0011\u0006,\u0003\u0002Z'\n)1\u000b^1tQ\u0006\u0019!-^:\u0004\u0001A\u0011Q\f\u001a\b\u0003=\n\u0004\"aX'\u000e\u0003\u0001T!!Y.\u0002\rq\u0012xn\u001c;?\u0013\t\u0019W*\u0001\u0004Qe\u0016$WMZ\u0005\u0003K\u001a\u0014aa\u0015;sS:<'BA2N\u0003-)hn];cg\u000e\u0014\u0018NY3\u0011\t1K7N\\\u0005\u0003U6\u0013\u0011BR;oGRLwN\\\u0019\u0011\u0005Ic\u0017BA7T\u0005!\t5\r^8s%\u00164\u0007C\u0001'p\u0013\t\u0001XJ\u0001\u0003V]&$\u0018!\u00023fEV<\u0007C\u0001't\u0013\t!XJA\u0004C_>dW-\u00198\u0002\rqJg.\u001b;?)\u00119\u0018P_>\u0011\u0005a\u0004Q\"\u0001$\t\u000bi#\u0001\u0019\u0001/\t\u000b\u001d$\u0001\u0019\u00015\t\u000bE$\u0001\u0019\u0001:\u0002\u000b\u0005$8+Z9\u0016\u0003y\u0004\"\u0001T@\n\u0007\u0005\u0005QJA\u0002J]R\f\u0011\"\u0019;TKF|F%Z9\u0015\u00079\f9\u0001\u0003\u0005\u0002\n\u0019\t\t\u00111\u0001\u007f\u0003\rAH%M\u0001\u0007CR\u001cV-\u001d\u0011\u0002\u000f9,\u0007\u0010^*fc\u0006A\u0001O]3Ti\u0006\u0014H\u000fF\u0001o\u0003\u001d\u0011XmY3jm\u0016,\"!a\u0006\u0011\r1\u000bI\"!\bo\u0013\r\tY\"\u0014\u0002\u0010!\u0006\u0014H/[1m\rVt7\r^5p]B\u0019A*a\b\n\u0007\u0005\u0005RJA\u0002B]f\fq$Q2u_J\u001cE.Y:tS\u001aL7-\u0019;j_:,fn];cg\u000e\u0014\u0018NY3s!\tAHb\u0005\u0002\r\u0017R\u0011\u0011QE\u0001\u0013k:\u001cXOY:de&\u0014WM]:D_VtG/\u0006\u0002\u00020A!\u0011\u0011GA\"\u001b\t\t\u0019D\u0003\u0003\u00026\u0005]\u0012AB1u_6L7M\u0003\u0003\u0002:\u0005m\u0012AC2p]\u000e,(O]3oi*!\u0011QHA \u0003\u0011)H/\u001b7\u000b\u0005\u0005\u0005\u0013\u0001\u00026bm\u0006LA!!\u0012\u00024\ti\u0011\t^8nS\u000eLe\u000e^3hKJ\f1#\u001e8tk\n\u001c8M]5cKJ\u001c8i\\;oi\u0002\u0012\u0001BU3hSN$XM]\n\u0007!-\u000bi%a\u0015\u0011\u00071\u000by%C\u0002\u0002R5\u0013q\u0001\u0015:pIV\u001cG\u000f\u0005\u0003\u0002V\u0005}c\u0002BA,\u00037r1aXA-\u0013\u0005q\u0015bAA/\u001b\u00069\u0001/Y2lC\u001e,\u0017\u0002BA1\u0003G\u0012AbU3sS\u0006d\u0017N_1cY\u0016T1!!\u0018N+\u0005Y\u0017AB1di>\u0014\b%A\u0002tKF\fAa]3rAQ1\u0011qNA:\u0003k\u00022!!\u001d\u0011\u001b\u0005a\u0001\"\u0002+\u0016\u0001\u0004Y\u0007BBA5+\u0001\u0007a0\u0001\u0003d_BLHCBA8\u0003w\ni\bC\u0004U-A\u0005\t\u0019A6\t\u0011\u0005%d\u0003%AA\u0002y\fabY8qs\u0012\"WMZ1vYR$\u0013'\u0006\u0002\u0002\u0004*\u001a1.!\",\u0005\u0005\u001d\u0005\u0003BAE\u0003'k!!a#\u000b\t\u00055\u0015qR\u0001\nk:\u001c\u0007.Z2lK\u0012T1!!%N\u0003)\tgN\\8uCRLwN\\\u0005\u0005\u0003+\u000bYIA\tv]\u000eDWmY6fIZ\u000b'/[1oG\u0016\fabY8qs\u0012\"WMZ1vYR$#'\u0006\u0002\u0002\u001c*\u001aa0!\"\u0002\u001bA\u0014x\u000eZ;diB\u0013XMZ5y+\t\t\t\u000b\u0005\u0003\u0002$\u0006%VBAAS\u0015\u0011\t9+a\u0010\u0002\t1\fgnZ\u0005\u0004K\u0006\u0015\u0016\u0001\u00049s_\u0012,8\r^!sSRL\u0018A\u00049s_\u0012,8\r^#mK6,g\u000e\u001e\u000b\u0005\u0003;\t\t\f\u0003\u0005\u0002\nm\t\t\u00111\u0001\u007f\u0003=\u0001(o\u001c3vGRLE/\u001a:bi>\u0014XCAA\\!\u0019\tI,a0\u0002\u001e5\u0011\u00111\u0018\u0006\u0004\u0003{k\u0015AC2pY2,7\r^5p]&!\u0011\u0011YA^\u0005!IE/\u001a:bi>\u0014\u0018\u0001C2b]\u0016\u000bX/\u00197\u0015\u0007I\f9\rC\u0005\u0002\nu\t\t\u00111\u0001\u0002\u001e\u0005\u0011\u0002O]8ek\u000e$X\t\\3nK:$h*Y7f)\u0011\t\t+!4\t\u0011\u0005%a$!AA\u0002y\f\u0001\u0002[1tQ\u000e{G-\u001a\u000b\u0002}\u0006AAo\\*ue&tw\r\u0006\u0002\u0002\"\u00061Q-];bYN$2A]An\u0011%\tI!IA\u0001\u0002\u0004\ti\"\u0001\u0005SK\u001eL7\u000f^3s!\r\t\thI\n\u0006G\u0005\r\u0018q\u001e\t\t\u0003K\fYo\u001b@\u0002p5\u0011\u0011q\u001d\u0006\u0004\u0003Sl\u0015a\u0002:v]RLW.Z\u0005\u0005\u0003[\f9OA\tBEN$(/Y2u\rVt7\r^5p]J\u0002B!!=\u0002x6\u0011\u00111\u001f\u0006\u0005\u0003k\fy$\u0001\u0002j_&!\u0011\u0011MAz)\t\ty.A\u0003baBd\u0017\u0010\u0006\u0004\u0002p\u0005}(\u0011\u0001\u0005\u0006)\u001a\u0002\ra\u001b\u0005\u0007\u0003S2\u0003\u0019\u0001@\u0002\u000fUt\u0017\r\u001d9msR!!q\u0001B\n!\u0015a%\u0011\u0002B\u0007\u0013\r\u0011Y!\u0014\u0002\u0007\u001fB$\u0018n\u001c8\u0011\u000b1\u0013ya\u001b@\n\u0007\tEQJ\u0001\u0004UkBdWM\r\u0005\n\u0005+9\u0013\u0011!a\u0001\u0003_\n1\u0001\u001f\u00131\u000319(/\u001b;f%\u0016\u0004H.Y2f)\t\u0011Y\u0002\u0005\u0003\u0002$\nu\u0011\u0002\u0002B\u0010\u0003K\u0013aa\u00142kK\u000e$(AC+oe\u0016<\u0017n\u001d;feN1\u0011fSA'\u0003'\"bAa\n\u0003*\t-\u0002cAA9S!)AK\fa\u0001W\"1\u0011\u0011\u000e\u0018A\u0002y$bAa\n\u00030\tE\u0002b\u0002+0!\u0003\u0005\ra\u001b\u0005\t\u0003Sz\u0003\u0013!a\u0001}R!\u0011Q\u0004B\u001b\u0011!\tI\u0001NA\u0001\u0002\u0004qHc\u0001:\u0003:!I\u0011\u0011\u0002\u001c\u0002\u0002\u0003\u0007\u0011Q\u0004\u000b\u0005\u0003C\u0013i\u0004\u0003\u0005\u0002\n]\n\t\u00111\u0001\u007f)\r\u0011(\u0011\t\u0005\n\u0003\u0013Q\u0014\u0011!a\u0001\u0003;\t!\"\u00168sK\u001eL7\u000f^3s!\r\t\t\bP\n\u0006y\t%\u0013q\u001e\t\t\u0003K\fYo\u001b@\u0003(Q\u0011!Q\t\u000b\u0007\u0005O\u0011yE!\u0015\t\u000bQ{\u0004\u0019A6\t\r\u0005%t\b1\u0001\u007f)\u0011\u00119A!\u0016\t\u0013\tU\u0001)!AA\u0002\t\u001d\u0012!B:uCJ$H#C6\u0003\\\t\u0015$\u0011\u000eB6\u0011\u001d\u0011iF\u0011a\u0001\u0005?\naa]=ti\u0016l\u0007c\u0001*\u0003b%\u0019!1M*\u0003\u0017\u0005\u001bGo\u001c:TsN$X-\u001c\u0005\u0007\u0005O\u0012\u0005\u0019\u0001/\u0002\u000f\t,8OT1nK\")qM\u0011a\u0001Q\"9\u0011O\u0011I\u0001\u0002\u0004\u0011\b\u0006\u0002B6\u0005_\u0002BA!\u001d\u0003v5\u0011!1\u000f\u0006\u0004\u0003{A\u0015\u0002\u0002B<\u0005g\u0012a!\u001e8vg\u0016$\u0017aD:uCJ$H\u0005Z3gCVdG\u000f\n\u001b\u0016\u0005\tu$f\u0001:\u0002\u0006\u0006)\u0001O]8qgRA!1\u0011BE\u0005\u0017\u0013i\tE\u0002S\u0005\u000bK1Aa\"T\u0005\u0015\u0001&o\u001c9t\u0011\u0019\u00119\u0007\u0012a\u00019\")q\r\u0012a\u0001Q\")\u0011\u000f\u0012a\u0001e\u0002")
public class ActorClassificationUnsubscriber
implements Stash {
    public final String akka$event$ActorClassificationUnsubscriber$$bus;
    public final Function1<ActorRef, BoxedUnit> akka$event$ActorClassificationUnsubscriber$$unsubscribe;
    public final boolean akka$event$ActorClassificationUnsubscriber$$debug;
    private int akka$event$ActorClassificationUnsubscriber$$atSeq;
    private Vector<Envelope> akka$actor$StashSupport$$theStash;
    private int akka$actor$StashSupport$$capacity;
    private DequeBasedMessageQueueSemantics mailbox;
    private ActorContext context;
    private ActorRef self;

    public static boolean start$default$4() {
        return ActorClassificationUnsubscriber$.MODULE$.start$default$4();
    }

    public static ActorRef start(ActorSystem system, String busName, Function1<ActorRef, BoxedUnit> unsubscribe, boolean debug) {
        return ActorClassificationUnsubscriber$.MODULE$.start(system, busName, unsubscribe, debug);
    }

    @Override
    public /* synthetic */ void akka$actor$UnrestrictedStash$$super$preRestart(Throwable reason, Option message) {
        Actor.preRestart$(this, reason, message);
    }

    @Override
    public /* synthetic */ void akka$actor$UnrestrictedStash$$super$postStop() {
        Actor.postStop$(this);
    }

    @Override
    public void preRestart(Throwable reason, Option<Object> message) throws Exception {
        UnrestrictedStash.preRestart$(this, reason, message);
    }

    @Override
    public void postStop() throws Exception {
        UnrestrictedStash.postStop$(this);
    }

    @Override
    public void stash() {
        StashSupport.stash$(this);
    }

    @Override
    public void prepend(Seq<Envelope> others) {
        StashSupport.prepend$(this, others);
    }

    @Override
    public void unstash() {
        StashSupport.unstash$(this);
    }

    @Override
    public void unstashAll() {
        StashSupport.unstashAll$(this);
    }

    @Override
    @InternalStableApi
    public void unstashAll(Function1<Object, Object> filterPredicate) {
        StashSupport.unstashAll$(this, filterPredicate);
    }

    @Override
    @InternalStableApi
    public Vector<Envelope> clearStash() {
        return StashSupport.clearStash$(this);
    }

    @Override
    public final ActorRef sender() {
        return Actor.sender$(this);
    }

    @Override
    @InternalApi
    public void aroundReceive(PartialFunction<Object, BoxedUnit> receive2, Object msg) {
        Actor.aroundReceive$(this, receive2, msg);
    }

    @Override
    @InternalApi
    public void aroundPreStart() {
        Actor.aroundPreStart$(this);
    }

    @Override
    @InternalApi
    public void aroundPostStop() {
        Actor.aroundPostStop$(this);
    }

    @Override
    @InternalApi
    public void aroundPreRestart(Throwable reason, Option<Object> message) {
        Actor.aroundPreRestart$(this, reason, message);
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
    public void postRestart(Throwable reason) throws Exception {
        Actor.postRestart$(this, reason);
    }

    @Override
    public void unhandled(Object message) {
        Actor.unhandled$(this, message);
    }

    @Override
    public Vector<Envelope> akka$actor$StashSupport$$theStash() {
        return this.akka$actor$StashSupport$$theStash;
    }

    @Override
    public void akka$actor$StashSupport$$theStash_$eq(Vector<Envelope> x$1) {
        this.akka$actor$StashSupport$$theStash = x$1;
    }

    @Override
    public int akka$actor$StashSupport$$capacity() {
        return this.akka$actor$StashSupport$$capacity;
    }

    @Override
    public DequeBasedMessageQueueSemantics mailbox() {
        return this.mailbox;
    }

    @Override
    public final void akka$actor$StashSupport$_setter_$akka$actor$StashSupport$$capacity_$eq(int x$1) {
        this.akka$actor$StashSupport$$capacity = x$1;
    }

    @Override
    public void akka$actor$StashSupport$_setter_$mailbox_$eq(DequeBasedMessageQueueSemantics x$1) {
        this.mailbox = x$1;
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

    private int atSeq() {
        return this.akka$event$ActorClassificationUnsubscriber$$atSeq;
    }

    public void akka$event$ActorClassificationUnsubscriber$$atSeq_$eq(int x$1) {
        this.akka$event$ActorClassificationUnsubscriber$$atSeq = x$1;
    }

    public int akka$event$ActorClassificationUnsubscriber$$nextSeq() {
        return this.atSeq() + 1;
    }

    @Override
    public void preStart() {
        Actor.preStart$(this);
        if (this.akka$event$ActorClassificationUnsubscriber$$debug) {
            this.context().system().eventStream().publish(new Logging.Debug(Logging$.MODULE$.simpleName(this.getClass()), this.getClass(), new StringBuilder(13).append("will monitor ").append(this.akka$event$ActorClassificationUnsubscriber$$bus).toString()));
            return;
        }
    }

    @Override
    public PartialFunction<Object, BoxedUnit> receive() {
        return new Serializable(this){
            private static final long serialVersionUID = 0L;
            private final /* synthetic */ ActorClassificationUnsubscriber $outer;

            public final <A1, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                boolean bl = false;
                Register register = null;
                boolean bl2 = false;
                Unregister unregister = null;
                A1 A1 = x1;
                if (A1 instanceof Register) {
                    bl = true;
                    register = (Register)A1;
                    ActorRef actor = register.actor();
                    int seq = register.seq();
                    if (seq == this.$outer.akka$event$ActorClassificationUnsubscriber$$nextSeq()) {
                        if (this.$outer.akka$event$ActorClassificationUnsubscriber$$debug) {
                            this.$outer.context().system().eventStream().publish(new Logging.Debug(Logging$.MODULE$.simpleName(this.$outer.getClass()), this.$outer.getClass(), new StringBuilder(25).append("registered watch for ").append(actor).append(" in ").append(this.$outer.akka$event$ActorClassificationUnsubscriber$$bus).toString()));
                        }
                        this.$outer.context().watch(actor);
                        this.$outer.akka$event$ActorClassificationUnsubscriber$$atSeq_$eq(this.$outer.akka$event$ActorClassificationUnsubscriber$$nextSeq());
                        this.$outer.unstashAll();
                        return (B1)BoxedUnit.UNIT;
                    }
                }
                if (bl) {
                    this.$outer.stash();
                    return (B1)BoxedUnit.UNIT;
                }
                if (A1 instanceof Unregister) {
                    bl2 = true;
                    unregister = (Unregister)A1;
                    ActorRef actor = unregister.actor();
                    int seq = unregister.seq();
                    if (seq == this.$outer.akka$event$ActorClassificationUnsubscriber$$nextSeq()) {
                        if (this.$outer.akka$event$ActorClassificationUnsubscriber$$debug) {
                            this.$outer.context().system().eventStream().publish(new Logging.Debug(Logging$.MODULE$.simpleName(this.$outer.getClass()), this.$outer.getClass(), new StringBuilder(26).append("unregistered watch of ").append(actor).append(" in ").append(this.$outer.akka$event$ActorClassificationUnsubscriber$$bus).toString()));
                        }
                        this.$outer.context().unwatch(actor);
                        this.$outer.akka$event$ActorClassificationUnsubscriber$$atSeq_$eq(this.$outer.akka$event$ActorClassificationUnsubscriber$$nextSeq());
                        this.$outer.unstashAll();
                        return (B1)BoxedUnit.UNIT;
                    }
                }
                if (bl2) {
                    this.$outer.stash();
                    return (B1)BoxedUnit.UNIT;
                }
                if (A1 instanceof Terminated) {
                    Terminated terminated = (Terminated)A1;
                    ActorRef actor = terminated.actor();
                    if (this.$outer.akka$event$ActorClassificationUnsubscriber$$debug) {
                        this.$outer.context().system().eventStream().publish(new Logging.Debug(Logging$.MODULE$.simpleName(this.$outer.getClass()), this.$outer.getClass(), new StringBuilder(45).append("actor ").append(actor).append(" has terminated, unsubscribing it from ").append(this.$outer.akka$event$ActorClassificationUnsubscriber$$bus).toString()));
                    }
                    return (B1)this.$outer.akka$event$ActorClassificationUnsubscriber$$unsubscribe.apply(actor);
                }
                return function1.apply(x1);
            }

            public final boolean isDefinedAt(Object x1) {
                boolean bl = false;
                Register register = null;
                boolean bl2 = false;
                Unregister unregister = null;
                Object object = x1;
                if (object instanceof Register) {
                    bl = true;
                    register = (Register)object;
                    int seq = register.seq();
                    if (seq == this.$outer.akka$event$ActorClassificationUnsubscriber$$nextSeq()) {
                        return true;
                    }
                }
                if (bl) {
                    return true;
                }
                if (object instanceof Unregister) {
                    bl2 = true;
                    unregister = (Unregister)object;
                    int seq = unregister.seq();
                    if (seq == this.$outer.akka$event$ActorClassificationUnsubscriber$$nextSeq()) {
                        return true;
                    }
                }
                if (bl2) {
                    return true;
                }
                return object instanceof Terminated;
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
            }
        };
    }

    public ActorClassificationUnsubscriber(String bus, Function1<ActorRef, BoxedUnit> unsubscribe, boolean debug) {
        this.akka$event$ActorClassificationUnsubscriber$$bus = bus;
        this.akka$event$ActorClassificationUnsubscriber$$unsubscribe = unsubscribe;
        this.akka$event$ActorClassificationUnsubscriber$$debug = debug;
        Actor.$init$(this);
        StashSupport.$init$(this);
        UnrestrictedStash.$init$(this);
        this.akka$event$ActorClassificationUnsubscriber$$atSeq = 0;
        Statics.releaseFence();
    }

    public static final class Register
    implements Product,
    Serializable {
        private final ActorRef actor;
        private final int seq;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public ActorRef actor() {
            return this.actor;
        }

        public int seq() {
            return this.seq;
        }

        public Register copy(ActorRef actor, int seq) {
            return new Register(actor, seq);
        }

        public ActorRef copy$default$1() {
            return this.actor();
        }

        public int copy$default$2() {
            return this.seq();
        }

        @Override
        public String productPrefix() {
            return "Register";
        }

        @Override
        public int productArity() {
            return 2;
        }

        @Override
        public Object productElement(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return this.actor();
                }
                case 1: {
                    return BoxesRunTime.boxToInteger(this.seq());
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
            return x$1 instanceof Register;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "actor";
                }
                case 1: {
                    return "seq";
                }
            }
            return (String)Statics.ioobe(x$1);
        }

        public int hashCode() {
            int n = -889275714;
            n = Statics.mix(n, this.productPrefix().hashCode());
            n = Statics.mix(n, Statics.anyHash(this.actor()));
            n = Statics.mix(n, this.seq());
            return Statics.finalizeHash(n, 2);
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
            if (!(object instanceof Register)) return false;
            boolean bl = true;
            if (!bl) return false;
            Register register = (Register)x$1;
            if (this.seq() != register.seq()) return false;
            ActorRef actorRef2 = this.actor();
            ActorRef actorRef3 = register.actor();
            if (actorRef2 != null) {
                if (!((Object)actorRef2).equals(actorRef3)) return false;
                return true;
            }
            if (actorRef3 == null) return true;
            return false;
        }

        public Register(ActorRef actor, int seq) {
            this.actor = actor;
            this.seq = seq;
            Product.$init$(this);
        }
    }

    public static final class Unregister
    implements Product,
    Serializable {
        private final ActorRef actor;
        private final int seq;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public ActorRef actor() {
            return this.actor;
        }

        public int seq() {
            return this.seq;
        }

        public Unregister copy(ActorRef actor, int seq) {
            return new Unregister(actor, seq);
        }

        public ActorRef copy$default$1() {
            return this.actor();
        }

        public int copy$default$2() {
            return this.seq();
        }

        @Override
        public String productPrefix() {
            return "Unregister";
        }

        @Override
        public int productArity() {
            return 2;
        }

        @Override
        public Object productElement(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return this.actor();
                }
                case 1: {
                    return BoxesRunTime.boxToInteger(this.seq());
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
            return x$1 instanceof Unregister;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "actor";
                }
                case 1: {
                    return "seq";
                }
            }
            return (String)Statics.ioobe(x$1);
        }

        public int hashCode() {
            int n = -889275714;
            n = Statics.mix(n, this.productPrefix().hashCode());
            n = Statics.mix(n, Statics.anyHash(this.actor()));
            n = Statics.mix(n, this.seq());
            return Statics.finalizeHash(n, 2);
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
            if (!(object instanceof Unregister)) return false;
            boolean bl = true;
            if (!bl) return false;
            Unregister unregister = (Unregister)x$1;
            if (this.seq() != unregister.seq()) return false;
            ActorRef actorRef2 = this.actor();
            ActorRef actorRef3 = unregister.actor();
            if (actorRef2 != null) {
                if (!((Object)actorRef2).equals(actorRef3)) return false;
                return true;
            }
            if (actorRef3 == null) return true;
            return false;
        }

        public Unregister(ActorRef actor, int seq) {
            this.actor = actor;
            this.seq = seq;
            Product.$init$(this);
        }
    }
}

