/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.AbstractFSM;
import akka.actor.Actor;
import akka.actor.FSM;
import akka.actor.Stash;
import akka.actor.StashSupport;
import akka.actor.UnrestrictedStash;
import akka.annotation.InternalStableApi;
import akka.dispatch.DequeBasedMessageQueueSemantics;
import akka.dispatch.Envelope;
import scala.Function1;
import scala.Option;
import scala.collection.immutable.Seq;
import scala.collection.immutable.Vector;
import scala.reflect.ScalaSignature;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u00152QAA\u0002\u0002\u0002!AQA\t\u0001\u0005\u0002\r\u0012A#\u00112tiJ\f7\r\u001e$T\u001b^KG\u000f[*uCND'B\u0001\u0003\u0006\u0003\u0015\t7\r^8s\u0015\u00051\u0011\u0001B1lW\u0006\u001c\u0001!F\u0002\n!u\u00192\u0001\u0001\u0006 !\u0011YAB\u0004\u000f\u000e\u0003\rI!!D\u0002\u0003\u0017\u0005\u00137\u000f\u001e:bGR45+\u0014\t\u0003\u001fAa\u0001\u0001B\u0003\u0012\u0001\t\u0007!CA\u0001T#\t\u0019\u0012\u0004\u0005\u0002\u0015/5\tQCC\u0001\u0017\u0003\u0015\u00198-\u00197b\u0013\tARCA\u0004O_RD\u0017N\\4\u0011\u0005QQ\u0012BA\u000e\u0016\u0005\r\te.\u001f\t\u0003\u001fu!QA\b\u0001C\u0002I\u0011\u0011\u0001\u0012\t\u0003\u0017\u0001J!!I\u0002\u0003\u000bM#\u0018m\u001d5\u0002\rqJg.\u001b;?)\u0005!\u0003\u0003B\u0006\u0001\u001dq\u0001")
public abstract class AbstractFSMWithStash<S, D>
extends AbstractFSM<S, D>
implements Stash {
    private Vector<Envelope> akka$actor$StashSupport$$theStash;
    private int akka$actor$StashSupport$$capacity;
    private DequeBasedMessageQueueSemantics mailbox;

    @Override
    public /* synthetic */ void akka$actor$UnrestrictedStash$$super$preRestart(Throwable reason, Option message) {
        Actor.preRestart$(this, reason, message);
    }

    @Override
    public /* synthetic */ void akka$actor$UnrestrictedStash$$super$postStop() {
        FSM.postStop$(this);
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

    public AbstractFSMWithStash() {
        StashSupport.$init$(this);
        UnrestrictedStash.$init$(this);
        Statics.releaseFence();
    }
}

