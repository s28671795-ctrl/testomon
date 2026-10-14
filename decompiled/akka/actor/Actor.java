/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.Actor$;
import akka.actor.ActorCell$;
import akka.actor.ActorContext;
import akka.actor.ActorInitializationException$;
import akka.actor.ActorRef;
import akka.actor.DeathPactException$;
import akka.actor.SupervisorStrategy;
import akka.actor.SupervisorStrategy$;
import akka.actor.Terminated;
import akka.actor.UnhandledMessage;
import akka.annotation.InternalApi;
import java.io.Serializable;
import scala.Function1;
import scala.Option;
import scala.PartialFunction;
import scala.collection.immutable.List;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;

@ScalaSignature(bytes="\u0006\u0005\u0005mv!\u0002\u0014(\u0011\u0003ac!\u0002\u0018(\u0011\u0003y\u0003\"\u0002\u001c\u0002\t\u00039T\u0001\u0002\u001d\u0002\u0001e:QAQ\u0001\t\u0002\r3Q!R\u0001\t\u0002\u0019CQAN\u0003\u0005\u0002!CQ!S\u0003\u0005\u0002)CQ\u0001U\u0003\u0005\u0002E;Q!X\u0001\t\u0002y3QaX\u0001\t\u0002\u0001DQA\u000e\u0006\u0005\u0002\u0005DQ!\u0013\u0006\u0005\u0002\tDQ\u0001\u0015\u0006\u0005\u0002\u0011Dq\u0001[\u0001C\u0002\u0013\u0015\u0011\u000e\u0003\u0004n\u0003\u0001\u0006iA\u001b\u0005\b]\u0006\u0011\r\u0011\"\u0004p\u0011\u0019A\u0018\u0001)A\u0007a\"9\u00110\u0001b\u0001\n\u001bQ\bB\u0002@\u0002A\u000351PB\u0004/OA\u0005\u0019\u0011A@\t\u000f\u0005\u0005A\u0003\"\u0001\u0002\u0004\u0015)\u0001\b\u0006\u0001\u0002\u0006!I\u0011\u0011\u0002\u000bC\u0002\u0013\r\u00111\u0002\u0005\t\u0003'!\"\u0019!C\u0004S\"9\u0011Q\u0003\u000b\u0005\u0006\u0005]\u0001bBA\r)\u0019\u0005\u00111\u0004\u0005\t\u0003;!B\u0011C\u0015\u0002 !A\u0011Q\u0007\u000b\u0005\u0012%\n\u0019\u0001\u0003\u0005\u0002:Q!\t\"KA\u0002\u0011!\ti\u0004\u0006C\tS\u0005}\u0002\u0002CA5)\u0011E\u0011&a\u001b\t\u000f\u0005ED\u0003\"\u0001\u0002t!9\u00111\u0010\u000b\u0005\u0002\u0005\r\u0001bBAG)\u0011\u0005\u00111\u0001\u0005\b\u0003##B\u0011AAJ\u0011\u001d\tY\u000b\u0006C\u0001\u0003[Cq!!.\u0015\t\u0003\t9,A\u0003BGR|'O\u0003\u0002)S\u0005)\u0011m\u0019;pe*\t!&\u0001\u0003bW.\f7\u0001\u0001\t\u0003[\u0005i\u0011a\n\u0002\u0006\u0003\u000e$xN]\n\u0003\u0003A\u0002\"!\r\u001b\u000e\u0003IR\u0011aM\u0001\u0006g\u000e\fG.Y\u0005\u0003kI\u0012a!\u00118z%\u00164\u0017A\u0002\u001fj]&$h\bF\u0001-\u0005\u001d\u0011VmY3jm\u0016\u0004B!\r\u001e=\u007f%\u00111H\r\u0002\u0010!\u0006\u0014H/[1m\rVt7\r^5p]B\u0011\u0011'P\u0005\u0003}I\u00121!\u00118z!\t\t\u0004)\u0003\u0002Be\t!QK\\5u\u00035)W\u000e\u001d;z\u0005\u0016D\u0017M^5peB\u0011A)B\u0007\u0002\u0003\tiQ-\u001c9us\n+\u0007.\u0019<j_J\u001c2!\u0002\u0019H!\t!5\u0001F\u0001D\u0003-I7\u000fR3gS:,G-\u0011;\u0015\u0005-s\u0005CA\u0019M\u0013\ti%GA\u0004C_>dW-\u00198\t\u000b=;\u0001\u0019\u0001\u001f\u0002\u0003a\fQ!\u00199qYf$\"AU+\u0011\u0005E\u001a\u0016B\u0001+3\u0005\u001dqu\u000e\u001e5j]\u001eDQa\u0014\u0005A\u0002qBC!B,[7B\u0011\u0011\u0007W\u0005\u00033J\u0012\u0001cU3sS\u0006dg+\u001a:tS>tW+\u0013#\u0002\u000bY\fG.^3\u001f\u0003\u0005AC\u0001B,[7\u0006\u0001\u0012n\u001a8pe&twMQ3iCZLwN\u001d\t\u0003\t*\u0011\u0001#[4o_JLgn\u001a\"fQ\u00064\u0018n\u001c:\u0014\u0007)\u0001t\tF\u0001_)\tY5\rC\u0003P\u0019\u0001\u0007A\b\u0006\u0002@K\")q*\u0004a\u0001y!\"!b\u0016.\\Q\u0011IqKW.\u0002\u00119|7+\u001a8eKJ,\u0012A\u001b\t\u0003[-L!\u0001\\\u0014\u0003\u0011\u0005\u001bGo\u001c:SK\u001a\f\u0011B\\8TK:$WM\u001d\u0011\u0002\u00159{G\u000fS1oI2,G-F\u0001q!\t\th/D\u0001s\u0015\t\u0019H/\u0001\u0003mC:<'\"A;\u0002\t)\fg/Y\u0005\u0003oJ\u0014aa\u00142kK\u000e$\u0018a\u0003(pi\"\u000bg\u000e\u001a7fI\u0002\nQB\\8u\u0011\u0006tG\r\\3e\rVtW#A>\u0011\tEbH\b]\u0005\u0003{J\u0012\u0011BR;oGRLwN\\\u0019\u0002\u001d9|G\u000fS1oI2,GMR;oAM\u0011A\u0003M\u0001\u0007I%t\u0017\u000e\u001e\u0013\u0015\u0003}\u00022!a\u0002\u0004\u001d\ti\u0003!A\u0004d_:$X\r\u001f;\u0016\u0005\u00055\u0001cA\u0017\u0002\u0010%\u0019\u0011\u0011C\u0014\u0003\u0019\u0005\u001bGo\u001c:D_:$X\r\u001f;\u0002\tM,GNZ\u0001\u0007g\u0016tG-\u001a:\u0015\u0003)\fqA]3dK&4X-\u0006\u0002\u0002\u0006\u0005i\u0011M]8v]\u0012\u0014VmY3jm\u0016$RaPA\u0011\u0003GAq!!\u0007\u001c\u0001\u0004\t)\u0001\u0003\u0004\u0002&m\u0001\r\u0001P\u0001\u0004[N<\u0007fA\u000e\u0002*A!\u00111FA\u0019\u001b\t\tiCC\u0002\u00020%\n!\"\u00198o_R\fG/[8o\u0013\u0011\t\u0019$!\f\u0003\u0017%sG/\u001a:oC2\f\u0005/[\u0001\u000fCJ|WO\u001c3Qe\u0016\u001cF/\u0019:uQ\ra\u0012\u0011F\u0001\u000fCJ|WO\u001c3Q_N$8\u000b^8qQ\ri\u0012\u0011F\u0001\u0011CJ|WO\u001c3Qe\u0016\u0014Vm\u001d;beR$RaPA!\u0003;Bq!a\u0011\u001f\u0001\u0004\t)%\u0001\u0004sK\u0006\u001cxN\u001c\t\u0005\u0003\u000f\n9F\u0004\u0003\u0002J\u0005Mc\u0002BA&\u0003#j!!!\u0014\u000b\u0007\u0005=3&\u0001\u0004=e>|GOP\u0005\u0002g%\u0019\u0011Q\u000b\u001a\u0002\u000fA\f7m[1hK&!\u0011\u0011LA.\u0005%!\u0006N]8xC\ndWMC\u0002\u0002VIBq!a\u0018\u001f\u0001\u0004\t\t'A\u0004nKN\u001c\u0018mZ3\u0011\tE\n\u0019\u0007P\u0005\u0004\u0003K\u0012$AB(qi&|g\u000eK\u0002\u001f\u0003S\t\u0011#\u0019:pk:$\u0007k\\:u%\u0016\u001cH/\u0019:u)\ry\u0014Q\u000e\u0005\b\u0003\u0007z\u0002\u0019AA#Q\ry\u0012\u0011F\u0001\u0013gV\u0004XM\u001d<jg>\u00148\u000b\u001e:bi\u0016<\u00170\u0006\u0002\u0002vA\u0019Q&a\u001e\n\u0007\u0005etE\u0001\nTkB,'O^5t_J\u001cFO]1uK\u001eL\u0018\u0001\u00039sKN#\u0018M\u001d;)\u000b\u0005\ny(a#\u0011\u000bE\n\t)!\"\n\u0007\u0005\r%G\u0001\u0004uQJ|wo\u001d\t\u0005\u0003\u000f\n9)\u0003\u0003\u0002\n\u0006m#!C#yG\u0016\u0004H/[8oG\t\t))\u0001\u0005q_N$8\u000b^8qQ\u0015\u0011\u0013qPAF\u0003)\u0001(/\u001a*fgR\f'\u000f\u001e\u000b\u0006\u007f\u0005U\u0015Q\u0015\u0005\b\u0003\u0007\u001a\u0003\u0019AA#Q\u0011\t)*!'\u0011\t\u0005m\u0015\u0011U\u0007\u0003\u0003;S1!a(*\u0003\u0011)H/\u001b7\n\t\u0005\r\u0016Q\u0014\u0002\u0007k:,8/\u001a3\t\u000f\u0005}3\u00051\u0001\u0002b!\"\u0011QUAMQ\u0015\u0019\u0013qPAF\u0003-\u0001xn\u001d;SKN$\u0018M\u001d;\u0015\u0007}\ny\u000bC\u0004\u0002D\u0011\u0002\r!!\u0012)\t\u0005=\u0016\u0011\u0014\u0015\u0006I\u0005}\u00141R\u0001\nk:D\u0017M\u001c3mK\u0012$2aPA]\u0011\u0019\ty&\na\u0001y\u0001")
public interface Actor {
    public static ActorRef noSender() {
        return Actor$.MODULE$.noSender();
    }

    public void akka$actor$Actor$_setter_$context_$eq(ActorContext var1);

    public void akka$actor$Actor$_setter_$self_$eq(ActorRef var1);

    public ActorContext context();

    public ActorRef self();

    public static /* synthetic */ ActorRef sender$(Actor $this) {
        return $this.sender();
    }

    default public ActorRef sender() {
        return this.context().sender();
    }

    public PartialFunction<Object, BoxedUnit> receive();

    public static /* synthetic */ void aroundReceive$(Actor $this, PartialFunction receive2, Object msg) {
        $this.aroundReceive(receive2, msg);
    }

    @InternalApi
    default public void aroundReceive(PartialFunction<Object, BoxedUnit> receive2, Object msg) {
        if (receive2.applyOrElse(msg, Actor$.MODULE$.akka$actor$Actor$$notHandledFun()) == Actor$.MODULE$.akka$actor$Actor$$NotHandled()) {
            this.unhandled(msg);
            return;
        }
    }

    public static /* synthetic */ void aroundPreStart$(Actor $this) {
        $this.aroundPreStart();
    }

    @InternalApi
    default public void aroundPreStart() {
        this.preStart();
    }

    public static /* synthetic */ void aroundPostStop$(Actor $this) {
        $this.aroundPostStop();
    }

    @InternalApi
    default public void aroundPostStop() {
        this.postStop();
    }

    public static /* synthetic */ void aroundPreRestart$(Actor $this, Throwable reason, Option message) {
        $this.aroundPreRestart(reason, message);
    }

    @InternalApi
    default public void aroundPreRestart(Throwable reason, Option<Object> message) {
        this.preRestart(reason, message);
    }

    public static /* synthetic */ void aroundPostRestart$(Actor $this, Throwable reason) {
        $this.aroundPostRestart(reason);
    }

    @InternalApi
    default public void aroundPostRestart(Throwable reason) {
        this.postRestart(reason);
    }

    public static /* synthetic */ SupervisorStrategy supervisorStrategy$(Actor $this) {
        return $this.supervisorStrategy();
    }

    default public SupervisorStrategy supervisorStrategy() {
        return SupervisorStrategy$.MODULE$.defaultStrategy();
    }

    public static /* synthetic */ void preStart$(Actor $this) {
        $this.preStart();
    }

    default public void preStart() throws Exception {
    }

    public static /* synthetic */ void postStop$(Actor $this) {
        $this.postStop();
    }

    default public void postStop() throws Exception {
    }

    public static /* synthetic */ void preRestart$(Actor $this, Throwable reason, Option message) {
        $this.preRestart(reason, message);
    }

    default public void preRestart(Throwable reason, Option<Object> message) throws Exception {
        this.context().children().foreach((Function1<ActorRef, Object> & Serializable)child -> {
            Actor.$anonfun$preRestart$1(this, child);
            return BoxedUnit.UNIT;
        });
        this.postStop();
    }

    public static /* synthetic */ void postRestart$(Actor $this, Throwable reason) {
        $this.postRestart(reason);
    }

    default public void postRestart(Throwable reason) throws Exception {
        this.preStart();
    }

    public static /* synthetic */ void unhandled$(Actor $this, Object message) {
        $this.unhandled(message);
    }

    default public void unhandled(Object message) {
        Object object = message;
        if (object instanceof Terminated) {
            Terminated terminated = (Terminated)object;
            ActorRef dead = terminated.actor();
            throw DeathPactException$.MODULE$.apply(dead);
        }
        this.context().system().eventStream().publish(new UnhandledMessage(message, this.sender(), this.self()));
    }

    public static /* synthetic */ void $anonfun$preRestart$1(Actor $this, ActorRef child) {
        $this.context().unwatch(child);
        $this.context().stop(child);
    }

    /*
     * WARNING - void declaration
     */
    public static void $init$(Actor $this) {
        void var2_2;
        List<ActorContext> contextStack = ActorCell$.MODULE$.contextStack().get();
        if (contextStack.isEmpty() || contextStack.head() == null) {
            throw ActorInitializationException$.MODULE$.apply(new StringBuilder(174).append("You cannot create an instance of [").append($this.getClass().getName()).append("] explicitly using the constructor (new). ").append("You have to use one of the 'actorOf' factory methods to create a new actor. See the documentation.").toString());
        }
        ActorContext c = (ActorContext)contextStack.head();
        ActorCell$.MODULE$.contextStack().set(contextStack.$colon$colon(null));
        $this.akka$actor$Actor$_setter_$context_$eq((ActorContext)var2_2);
        $this.akka$actor$Actor$_setter_$self_$eq($this.context().self());
    }
}

