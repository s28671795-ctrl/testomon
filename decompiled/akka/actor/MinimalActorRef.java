/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.Actor$;
import akka.actor.ActorRef;
import akka.actor.InternalActorRef;
import akka.actor.LocalRef;
import akka.actor.Nobody$;
import akka.actor.SerializedActorRef$;
import akka.dispatch.sysmsg.SystemMessage;
import java.io.ObjectStreamException;
import java.io.Serializable;
import scala.Function1;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;

@ScalaSignature(bytes="\u0006\u0005\u0005Ea\u0001\u0003\b\u0010!\u0003\r\t!E\n\t\u000bm\u0001A\u0011A\u000f\t\u000b\u0011\u0002A\u0011I\u0013\t\u000b\u0019\u0002A\u0011I\u0014\t\u000by\u0002A\u0011I\u000f\t\u000b}\u0002A\u0011I\u000f\t\u000b\u0001\u0003A\u0011I!\t\u000b\u001d\u0003A\u0011I\u000f\t\r!\u0003A\u0011I\tJ\u0011\u0015i\u0005\u0001\"\u0011O\u0011\u001dQ\u0006!%A\u0005\u0002mCQa\u001a\u0001\u0005B!DQA\u001d\u0001\u0005BMDQA\u001e\u0001\u0005\u0012]\u0014q\"T5oS6\fG.Q2u_J\u0014VM\u001a\u0006\u0003!E\tQ!Y2u_JT\u0011AE\u0001\u0005C.\\\u0017mE\u0002\u0001)a\u0001\"!\u0006\f\u000e\u0003=I!aF\b\u0003!%sG/\u001a:oC2\f5\r^8s%\u00164\u0007CA\u000b\u001a\u0013\tQrB\u0001\u0005M_\u000e\fGNU3g\u0003\u0019!\u0013N\\5uI\r\u0001A#\u0001\u0010\u0011\u0005}\u0011S\"\u0001\u0011\u000b\u0003\u0005\nQa]2bY\u0006L!a\t\u0011\u0003\tUs\u0017\u000e^\u0001\nO\u0016$\b+\u0019:f]R,\u0012\u0001F\u0001\tO\u0016$8\t[5mIR\u0011A\u0003\u000b\u0005\u0006S\r\u0001\rAK\u0001\u0006]\u0006lWm\u001d\t\u0004WM2dB\u0001\u00172\u001d\ti\u0003'D\u0001/\u0015\tyC$\u0001\u0004=e>|GOP\u0005\u0002C%\u0011!\u0007I\u0001\ba\u0006\u001c7.Y4f\u0013\t!TG\u0001\u0005Ji\u0016\u0014\u0018\r^8s\u0015\t\u0011\u0004\u0005\u0005\u00028w9\u0011\u0001(\u000f\t\u0003[\u0001J!A\u000f\u0011\u0002\rA\u0013X\rZ3g\u0013\taTH\u0001\u0004TiJLgn\u001a\u0006\u0003u\u0001\nQa\u001d;beR\fqa];ta\u0016tG-\u0001\u0004sKN,X.\u001a\u000b\u0003=\tCQa\u0011\u0004A\u0002\u0011\u000bqbY1vg\u0016$')\u001f$bS2,(/\u001a\t\u0003W\u0015K!AR\u001b\u0003\u0013QC'o\\<bE2,\u0017\u0001B:u_B\fA\"[:UKJl\u0017N\\1uK\u0012,\u0012A\u0013\t\u0003?-K!\u0001\u0014\u0011\u0003\u000f\t{w\u000e\\3b]\u0006)AEY1oOR\u0011q*\u0016\u000b\u0003=ACq!U\u0005\u0011\u0002\u0003\u000f!+\u0001\u0004tK:$WM\u001d\t\u0003+MK!\u0001V\b\u0003\u0011\u0005\u001bGo\u001c:SK\u001aDQAV\u0005A\u0002]\u000bq!\\3tg\u0006<W\r\u0005\u0002 1&\u0011\u0011\f\t\u0002\u0004\u0003:L\u0018a\u0004\u0013cC:<G\u0005Z3gCVdG\u000f\n\u001a\u0015\u0005q3'F\u0001*^W\u0005q\u0006CA0e\u001b\u0005\u0001'BA1c\u0003%)hn\u00195fG.,GM\u0003\u0002dA\u0005Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\u0005\u0015\u0004'!E;oG\",7m[3e-\u0006\u0014\u0018.\u00198dK\")aK\u0003a\u0001/\u0006\t2/\u001a8e'f\u001cH/Z7NKN\u001c\u0018mZ3\u0015\u0005yI\u0007\"\u0002,\f\u0001\u0004Q\u0007CA6q\u001b\u0005a'BA7o\u0003\u0019\u0019\u0018p]7tO*\u0011q.E\u0001\tI&\u001c\b/\u0019;dQ&\u0011\u0011\u000f\u001c\u0002\u000e'f\u001cH/Z7NKN\u001c\u0018mZ3\u0002\u000fI,7\u000f^1siR\u0011a\u0004\u001e\u0005\u0006k2\u0001\r\u0001R\u0001\u0006G\u0006,8/Z\u0001\roJLG/\u001a*fa2\f7-\u001a\u000b\u0002qB\u0011q$_\u0005\u0003u\u0002\u0012a!\u00118z%\u00164\u0007\u0006B\u0007}\u0003\u001f\u00012aH?\u0000\u0013\tq\bE\u0001\u0004uQJ|wo\u001d\t\u0005\u0003\u0003\tY!\u0004\u0002\u0002\u0004)!\u0011QAA\u0004\u0003\tIwN\u0003\u0002\u0002\n\u0005!!.\u0019<b\u0013\u0011\ti!a\u0001\u0003+=\u0013'.Z2u'R\u0014X-Y7Fq\u000e,\u0007\u000f^5p]\u000e\nq\u0010")
public interface MinimalActorRef
extends LocalRef {
    public static /* synthetic */ InternalActorRef getParent$(MinimalActorRef $this) {
        return $this.getParent();
    }

    default public InternalActorRef getParent() {
        return Nobody$.MODULE$;
    }

    public static /* synthetic */ InternalActorRef getChild$(MinimalActorRef $this, Iterator names) {
        return $this.getChild(names);
    }

    default public InternalActorRef getChild(Iterator<String> names) {
        if (names.forall((Function1<String, Object> & Serializable)x$1 -> BoxesRunTime.boxToBoolean(x$1.isEmpty()))) {
            return (InternalActorRef)((Object)this);
        }
        return Nobody$.MODULE$;
    }

    public static /* synthetic */ void start$(MinimalActorRef $this) {
        $this.start();
    }

    default public void start() {
    }

    public static /* synthetic */ void suspend$(MinimalActorRef $this) {
        $this.suspend();
    }

    default public void suspend() {
    }

    public static /* synthetic */ void resume$(MinimalActorRef $this, Throwable causedByFailure) {
        $this.resume(causedByFailure);
    }

    default public void resume(Throwable causedByFailure) {
    }

    public static /* synthetic */ void stop$(MinimalActorRef $this) {
        $this.stop();
    }

    default public void stop() {
    }

    public static /* synthetic */ boolean isTerminated$(MinimalActorRef $this) {
        return $this.isTerminated();
    }

    default public boolean isTerminated() {
        return false;
    }

    public static /* synthetic */ void $bang$(MinimalActorRef $this, Object message, ActorRef sender) {
        $this.$bang(message, sender);
    }

    default public void $bang(Object message, ActorRef sender) {
    }

    public static /* synthetic */ void sendSystemMessage$(MinimalActorRef $this, SystemMessage message) {
        $this.sendSystemMessage(message);
    }

    default public void sendSystemMessage(SystemMessage message) {
    }

    public static /* synthetic */ void restart$(MinimalActorRef $this, Throwable cause) {
        $this.restart(cause);
    }

    default public void restart(Throwable cause) {
    }

    public static /* synthetic */ Object writeReplace$(MinimalActorRef $this) {
        return $this.writeReplace();
    }

    default public Object writeReplace() throws ObjectStreamException {
        return SerializedActorRef$.MODULE$.apply((ActorRef)((Object)this));
    }

    public static /* synthetic */ ActorRef $bang$default$2$(MinimalActorRef $this, Object message) {
        return $this.$bang$default$2(message);
    }

    default public ActorRef $bang$default$2(Object message) {
        return Actor$.MODULE$.noSender();
    }

    public static void $init$(MinimalActorRef $this) {
    }
}

