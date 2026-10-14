/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.ActorContext;
import akka.actor.ActorRef;
import akka.actor.StashSupport;
import akka.annotation.InternalStableApi;
import akka.dispatch.DequeBasedMessageQueueSemantics;
import akka.dispatch.Envelope;
import scala.Function1;
import scala.collection.immutable.Seq;
import scala.collection.immutable.Vector;
import scala.reflect.ScalaSignature;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u000552\u0011b\u0001\u0003\u0011\u0002\u0007\u0005a\u0001\u0003\u0014\t\u000b=\u0001A\u0011A\t\t\rU\u0001A\u0011\u0001\u0004\u0017\u00051\u0019F/Y:i\r\u0006\u001cGo\u001c:z\u0015\t)a!A\u0003bGR|'OC\u0001\b\u0003\u0011\t7n[1\u0014\u0005\u0001I\u0001C\u0001\u0006\u000e\u001b\u0005Y!\"\u0001\u0007\u0002\u000bM\u001c\u0017\r\\1\n\u00059Y!AB!osJ+g-\u0001\u0004%S:LG\u000fJ\u0002\u0001)\u0005\u0011\u0002C\u0001\u0006\u0014\u0013\t!2B\u0001\u0003V]&$\u0018aC2sK\u0006$Xm\u0015;bg\"$\u0012a\u0006\u000b\u00041q\t\u0003CA\r\u001b\u001b\u0005!\u0011BA\u000e\u0005\u00051\u0019F/Y:i'V\u0004\bo\u001c:u\u0011\u0015i\"\u0001q\u0001\u001f\u0003\r\u0019G\u000f\u001f\t\u00033}I!\u0001\t\u0003\u0003\u0019\u0005\u001bGo\u001c:D_:$X\r\u001f;\t\u000b\t\u0012\u00019A\u0012\u0002\u0007I,g\r\u0005\u0002\u001aI%\u0011Q\u0005\u0002\u0002\t\u0003\u000e$xN\u001d*fMJ\u0019q%\u000b\u0016\u0007\t!\u0002\u0001A\n\u0002\ryI,g-\u001b8f[\u0016tGO\u0010\t\u00033\u0001\u0001\"!G\u0016\n\u00051\"!!B!di>\u0014\b")
public interface StashFactory {
    public static /* synthetic */ StashSupport createStash$(StashFactory $this, ActorContext ctx, ActorRef ref) {
        return $this.createStash(ctx, ref);
    }

    default public StashSupport createStash(ActorContext ctx, ActorRef ref) {
        return new StashSupport(null, ctx, ref){
            private Vector<Envelope> akka$actor$StashSupport$$theStash;
            private int akka$actor$StashSupport$$capacity;
            private DequeBasedMessageQueueSemantics mailbox;
            private final ActorContext ctx$1;
            private final ActorRef ref$1;

            public void stash() {
                StashSupport.stash$(this);
            }

            public void prepend(Seq<Envelope> others) {
                StashSupport.prepend$(this, others);
            }

            public void unstash() {
                StashSupport.unstash$(this);
            }

            public void unstashAll() {
                StashSupport.unstashAll$(this);
            }

            @InternalStableApi
            public void unstashAll(Function1<Object, Object> filterPredicate) {
                StashSupport.unstashAll$(this, filterPredicate);
            }

            @InternalStableApi
            public Vector<Envelope> clearStash() {
                return StashSupport.clearStash$(this);
            }

            public Vector<Envelope> akka$actor$StashSupport$$theStash() {
                return this.akka$actor$StashSupport$$theStash;
            }

            public void akka$actor$StashSupport$$theStash_$eq(Vector<Envelope> x$1) {
                this.akka$actor$StashSupport$$theStash = x$1;
            }

            public int akka$actor$StashSupport$$capacity() {
                return this.akka$actor$StashSupport$$capacity;
            }

            public DequeBasedMessageQueueSemantics mailbox() {
                return this.mailbox;
            }

            public final void akka$actor$StashSupport$_setter_$akka$actor$StashSupport$$capacity_$eq(int x$1) {
                this.akka$actor$StashSupport$$capacity = x$1;
            }

            public void akka$actor$StashSupport$_setter_$mailbox_$eq(DequeBasedMessageQueueSemantics x$1) {
                this.mailbox = x$1;
            }

            public ActorContext context() {
                return this.ctx$1;
            }

            public ActorRef self() {
                return this.ref$1;
            }
            {
                this.ctx$1 = ctx$1;
                this.ref$1 = ref$1;
                StashSupport.$init$(this);
                Statics.releaseFence();
            }
        };
    }

    public static void $init$(StashFactory $this) {
    }
}

