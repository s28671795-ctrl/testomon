/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.AbstractActor;
import akka.actor.StashSupport;
import akka.actor.UnboundedStash;
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

@ScalaSignature(bytes="\u0006\u0005M1QAA\u0002\u0002\u0002!AQ\u0001\u0005\u0001\u0005\u0002E\u0011q$\u00112tiJ\f7\r^!di>\u0014x+\u001b;i+:\u0014w.\u001e8eK\u0012\u001cF/Y:i\u0015\t!Q!A\u0003bGR|'OC\u0001\u0007\u0003\u0011\t7n[1\u0004\u0001M\u0019\u0001!C\u0007\u0011\u0005)YQ\"A\u0002\n\u00051\u0019!!D!cgR\u0014\u0018m\u0019;BGR|'\u000f\u0005\u0002\u000b\u001d%\u0011qb\u0001\u0002\u000f+:\u0014w.\u001e8eK\u0012\u001cF/Y:i\u0003\u0019a\u0014N\\5u}Q\t!\u0003\u0005\u0002\u000b\u0001\u0001")
public abstract class AbstractActorWithUnboundedStash
extends AbstractActor
implements UnboundedStash {
    private Vector<Envelope> akka$actor$StashSupport$$theStash;
    private int akka$actor$StashSupport$$capacity;
    private DequeBasedMessageQueueSemantics mailbox;

    @Override
    public /* synthetic */ void akka$actor$UnrestrictedStash$$super$preRestart(Throwable reason, Option message) {
        super.preRestart(reason, message);
    }

    @Override
    public /* synthetic */ void akka$actor$UnrestrictedStash$$super$postStop() {
        super.postStop();
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

    public AbstractActorWithUnboundedStash() {
        StashSupport.$init$(this);
        UnrestrictedStash.$init$(this);
        Statics.releaseFence();
    }
}

