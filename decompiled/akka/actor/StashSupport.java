/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.ActorCell;
import akka.actor.ActorContext;
import akka.actor.ActorInitializationException$;
import akka.actor.ActorRef;
import akka.actor.StashOverflowException;
import akka.actor.StashOverflowException$;
import akka.actor.Terminated;
import akka.annotation.InternalStableApi;
import akka.dispatch.DequeBasedMessageQueueSemantics;
import akka.dispatch.Envelope;
import akka.dispatch.MessageQueue;
import java.io.Serializable;
import scala.Function1;
import scala.Function2;
import scala.None$;
import scala.Predef$;
import scala.collection.Iterator;
import scala.collection.StringOps$;
import scala.collection.immutable.Seq;
import scala.collection.immutable.Vector;
import scala.package$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;

@ScalaSignature(bytes="\u0006\u0005}4\u0001\u0002E\t\u0011\u0002\u0007\u00051#\u0006\u0005\u00069\u0001!\tA\b\u0005\u0007E\u00011\taE\u0012\t\r!\u0002a\u0011A\n*\u0011\u001di\u0003\u00011A\u0005\n9Bq!\u0010\u0001A\u0002\u0013%a\bC\u0003B\u0001\u0011%!\tC\u0004G\u0001\t\u0007I\u0011B$\t\u0011-\u0003!\u0019!C\u0001'1CQ\u0001\u0015\u0001\u0005\u0002yAa!\u0015\u0001\u0005\u0002M\u0011\u0006B\u0002-\u0001\t\u0003\u0019b\u0004C\u0003Z\u0001\u0011\u0005a\u0004\u0003\u0004Z\u0001\u0011\u00051C\u0017\u0005\u0007[\u0002!\ta\u00058\t\u000bm\u0004A\u0011\u0002?\u0003\u0019M#\u0018m\u001d5TkB\u0004xN\u001d;\u000b\u0005I\u0019\u0012!B1di>\u0014(\"\u0001\u000b\u0002\t\u0005\\7.Y\n\u0003\u0001Y\u0001\"a\u0006\u000e\u000e\u0003aQ\u0011!G\u0001\u0006g\u000e\fG.Y\u0005\u00037a\u0011a!\u00118z%\u00164\u0017A\u0002\u0013j]&$He\u0001\u0001\u0015\u0003}\u0001\"a\u0006\u0011\n\u0005\u0005B\"\u0001B+oSR\fqaY8oi\u0016DH/F\u0001%!\t)c%D\u0001\u0012\u0013\t9\u0013C\u0001\u0007BGR|'oQ8oi\u0016DH/\u0001\u0003tK24W#\u0001\u0016\u0011\u0005\u0015Z\u0013B\u0001\u0017\u0012\u0005!\t5\r^8s%\u00164\u0017\u0001\u0003;iKN#\u0018m\u001d5\u0016\u0003=\u00022\u0001M\u001b8\u001b\u0005\t$B\u0001\u001a4\u0003%IW.\\;uC\ndWM\u0003\u000251\u0005Q1m\u001c7mK\u000e$\u0018n\u001c8\n\u0005Y\n$A\u0002,fGR|'\u000f\u0005\u00029w5\t\u0011H\u0003\u0002;'\u0005AA-[:qCR\u001c\u0007.\u0003\u0002=s\tAQI\u001c<fY>\u0004X-\u0001\u0007uQ\u0016\u001cF/Y:i?\u0012*\u0017\u000f\u0006\u0002 \u007f!9\u0001)BA\u0001\u0002\u0004y\u0013a\u0001=%c\u0005I\u0011m\u0019;pe\u000e+G\u000e\\\u000b\u0002\u0007B\u0011Q\u0005R\u0005\u0003\u000bF\u0011\u0011\"Q2u_J\u001cU\r\u001c7\u0002\u0011\r\f\u0007/Y2jif,\u0012\u0001\u0013\t\u0003/%K!A\u0013\r\u0003\u0007%sG/A\u0004nC&d'm\u001c=\u0016\u00035\u0003\"\u0001\u000f(\n\u0005=K$a\b#fcV,')Y:fI6+7o]1hKF+X-^3TK6\fg\u000e^5dg\u0006)1\u000f^1tQ\u00069\u0001O]3qK:$GCA\u0010T\u0011\u0015!&\u00021\u0001V\u0003\u0019yG\u000f[3sgB\u0019\u0001GV\u001c\n\u0005]\u000b$aA*fc\u00069QO\\:uCND\u0017AC;ogR\f7\u000f[!mYR\u0011qd\u0017\u0005\u000696\u0001\r!X\u0001\u0010M&dG/\u001a:Qe\u0016$\u0017nY1uKB!qC\u00181d\u0013\ty\u0006DA\u0005Gk:\u001cG/[8ocA\u0011q#Y\u0005\u0003Eb\u00111!\u00118z!\t9B-\u0003\u0002f1\t9!i\\8mK\u0006t\u0007FA\u0007h!\tA7.D\u0001j\u0015\tQ7#\u0001\u0006b]:|G/\u0019;j_:L!\u0001\\5\u0003#%sG/\u001a:oC2\u001cF/\u00192mK\u0006\u0003\u0018.\u0001\u0006dY\u0016\f'o\u0015;bg\"$\u0012a\u001c\t\u0004ab<dBA9w\u001d\t\u0011X/D\u0001t\u0015\t!X$\u0001\u0004=e>|GOP\u0005\u00023%\u0011q\u000fG\u0001\ba\u0006\u001c7.Y4f\u0013\t1\u0014P\u0003\u0002x1!\u0012abZ\u0001\rK:\fX/Z;f\r&\u00148\u000f\u001e\u000b\u0003?uDQA`\bA\u0002]\n\u0001\"\u001a8wK2|\u0007/\u001a")
public interface StashSupport {
    public void akka$actor$StashSupport$_setter_$akka$actor$StashSupport$$capacity_$eq(int var1);

    public void akka$actor$StashSupport$_setter_$mailbox_$eq(DequeBasedMessageQueueSemantics var1);

    public ActorContext context();

    public ActorRef self();

    public Vector<Envelope> akka$actor$StashSupport$$theStash();

    public void akka$actor$StashSupport$$theStash_$eq(Vector<Envelope> var1);

    private ActorCell actorCell() {
        return (ActorCell)this.context();
    }

    public int akka$actor$StashSupport$$capacity();

    public DequeBasedMessageQueueSemantics mailbox();

    public static /* synthetic */ void stash$(StashSupport $this) {
        $this.stash();
    }

    default public void stash() {
        Envelope currMsg = this.actorCell().currentMessage();
        if (this.akka$actor$StashSupport$$theStash().nonEmpty() && currMsg == this.akka$actor$StashSupport$$theStash().last()) {
            throw new IllegalStateException(new StringBuilder(44).append("Can't stash the same message ").append(currMsg).append(" more than once").toString());
        }
        if (this.akka$actor$StashSupport$$capacity() <= 0 || this.akka$actor$StashSupport$$theStash().size() < this.akka$actor$StashSupport$$capacity()) {
            this.akka$actor$StashSupport$$theStash_$eq((Vector)this.akka$actor$StashSupport$$theStash().$colon$plus(currMsg));
            return;
        }
        throw new StashOverflowException(new StringBuilder(44).append("Couldn't enqueue message ").append(currMsg.message().getClass().getName()).append(" from ").append(currMsg.sender()).append(" to stash of ").append(this.self()).toString(), StashOverflowException$.MODULE$.$lessinit$greater$default$2());
    }

    public static /* synthetic */ void prepend$(StashSupport $this, Seq others) {
        $this.prepend(others);
    }

    default public void prepend(Seq<Envelope> others) {
        this.akka$actor$StashSupport$$theStash_$eq(others.foldRight(this.akka$actor$StashSupport$$theStash(), (Function2<Envelope, Vector, Vector> & Serializable)(e, s2) -> {
            Envelope envelope = e;
            return (Vector)s2.$plus$colon(envelope);
        }));
    }

    public static /* synthetic */ void unstash$(StashSupport $this) {
        $this.unstash();
    }

    default public void unstash() {
        if (this.akka$actor$StashSupport$$theStash().nonEmpty()) {
            try {
                this.enqueueFirst(this.akka$actor$StashSupport$$theStash().head());
            }
            finally {
                this.akka$actor$StashSupport$$theStash_$eq((Vector<Envelope>)this.akka$actor$StashSupport$$theStash().tail());
            }
            return;
        }
    }

    public static /* synthetic */ void unstashAll$(StashSupport $this) {
        $this.unstashAll();
    }

    default public void unstashAll() {
        this.unstashAll((Function1<Object, Object> & Serializable)x$1 -> BoxesRunTime.boxToBoolean(StashSupport.$anonfun$unstashAll$1(x$1)));
    }

    public static /* synthetic */ void unstashAll$(StashSupport $this, Function1 filterPredicate) {
        $this.unstashAll(filterPredicate);
    }

    @InternalStableApi
    default public void unstashAll(Function1<Object, Object> filterPredicate) {
        try {
            Iterator<Envelope> i = this.akka$actor$StashSupport$$theStash().reverseIterator().filter((Function1<Envelope, Object> & Serializable)envelope -> BoxesRunTime.boxToBoolean(StashSupport.$anonfun$unstashAll$2(filterPredicate, envelope)));
            while (i.hasNext()) {
                this.enqueueFirst(i.next());
            }
        }
        finally {
            this.akka$actor$StashSupport$$theStash_$eq((Vector<Envelope>)package$.MODULE$.Vector().empty());
        }
    }

    public static /* synthetic */ Vector clearStash$(StashSupport $this) {
        return $this.clearStash();
    }

    @InternalStableApi
    default public Vector<Envelope> clearStash() {
        Vector<Envelope> stashed = this.akka$actor$StashSupport$$theStash();
        this.akka$actor$StashSupport$$theStash_$eq((Vector<Envelope>)package$.MODULE$.Vector().empty());
        return stashed;
    }

    private void enqueueFirst(Envelope envelope) {
        this.mailbox().enqueueFirst(this.self(), envelope);
        Object object = envelope.message();
        if (object instanceof Terminated) {
            Terminated terminated = (Terminated)object;
            ActorRef ref = terminated.actor();
            this.actorCell().terminatedQueuedFor(ref, None$.MODULE$);
            return;
        }
    }

    public static /* synthetic */ boolean $anonfun$unstashAll$1(Object x$1) {
        return true;
    }

    public static /* synthetic */ boolean $anonfun$unstashAll$2(Function1 filterPredicate$1, Envelope envelope) {
        return BoxesRunTime.unboxToBoolean(filterPredicate$1.apply(envelope.message()));
    }

    public static void $init$(StashSupport $this) {
        $this.akka$actor$StashSupport$$theStash_$eq((Vector<Envelope>)package$.MODULE$.Vector().empty());
        $this.akka$actor$StashSupport$_setter_$akka$actor$StashSupport$$capacity_$eq($this.context().system().mailboxes().stashCapacity($this.context().props().dispatcher(), $this.context().props().mailbox()));
        MessageQueue messageQueue = $this.actorCell().mailbox().messageQueue();
        if (!(messageQueue instanceof DequeBasedMessageQueueSemantics)) {
            throw ActorInitializationException$.MODULE$.apply($this.self(), new StringBuilder(34).append("DequeBasedMailbox required, got: ").append(messageQueue.getClass().getName()).append("\n").append(StringOps$.MODULE$.stripMargin$extension(Predef$.MODULE$.augmentString("An (unbounded) deque-based mailbox can be configured as follows:\n          |  my-custom-mailbox {\n          |    mailbox-type = \"akka.dispatch.UnboundedDequeBasedMailbox\"\n          |  }\n          |"))).toString(), ActorInitializationException$.MODULE$.apply$default$3());
        }
        MessageQueue messageQueue2 = messageQueue;
        $this.akka$actor$StashSupport$_setter_$mailbox_$eq((DequeBasedMessageQueueSemantics)((Object)messageQueue2));
    }
}

