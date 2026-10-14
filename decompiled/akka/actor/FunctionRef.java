/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.Actor$;
import akka.actor.ActorCell$;
import akka.actor.ActorPath;
import akka.actor.ActorRef;
import akka.actor.ActorRefProvider;
import akka.actor.ActorRefScope;
import akka.actor.ActorSystem;
import akka.actor.Address;
import akka.actor.AddressTerminated;
import akka.actor.FunctionRef$;
import akka.actor.InternalActorRef;
import akka.actor.LocalRef;
import akka.actor.MinimalActorRef;
import akka.actor.Terminated$;
import akka.annotation.InternalApi;
import akka.dispatch.sysmsg.DeathWatchNotification;
import akka.dispatch.sysmsg.SystemMessage;
import akka.dispatch.sysmsg.Unwatch;
import akka.dispatch.sysmsg.Watch;
import akka.event.AddressTerminatedTopic;
import akka.event.AddressTerminatedTopic$;
import akka.event.Logging;
import akka.event.Logging$Error$;
import akka.util.OptionVal$;
import akka.util.OptionVal$Some$;
import java.io.ObjectStreamException;
import java.io.Serializable;
import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.MatchError;
import scala.Option;
import scala.Predef$;
import scala.Tuple2;
import scala.collection.Iterator;
import scala.collection.immutable.Set;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.util.control.NonFatal$;

@ScalaSignature(bytes="\u0006\u0005\t\u0005qAB\u0012%\u0011\u00031\u0003F\u0002\u0004+I!\u0005ae\u000b\u0005\u0006u\u0005!\t\u0001\u0010\u0005\u0006{\u0005!\tA\u0010\u0005\b!\u0006\t\t\u0011\"\u0003R\r\u0015QCE\u0001\u0014a\u0011!9WA!b\u0001\n\u0003B\u0007\u0002\u00037\u0006\u0005\u0003\u0005\u000b\u0011B5\t\u00115,!Q1A\u0005B9D\u0001B]\u0003\u0003\u0002\u0003\u0006Ia\u001c\u0005\t\u0019\u0016\u0011\t\u0011)A\u0005\u001b\"A1/\u0002B\u0001B\u0003%q\bC\u0003;\u000b\u0011\u0005A\u000fC\u0004{\u000b\u0001\u0007I\u0011B>\t\u000fq,\u0001\u0019!C\u0005{\"9\u0011\u0011A\u0003!B\u0013y\u0004bBA\u0002\u000b\u0011\u0005\u0013Q\u0001\u0005\n\u0003#)\u0011\u0013!C\u0001\u0003'Aq!!\u000b\u0006\t\u0003\nY\u0003\u0003\u0005\u0002@\u0015\u0001\u000b\u0015BA!\u0011!\t9&\u0002Q!\n\u0005e\u0003\u0002CA3\u000b\u0011\u0005c%a\u001a\t\u000f\u0005ET\u0001\"\u0005\u0002t!9\u0011\u0011O\u0003\u0005\n\u0005U\u0004bBAA\u000b\u0011%\u00111\u0011\u0005\b\u0003\u001f+A\u0011IA:\u0011\u001d\t\t*\u0002C\u0005\u0003'Cq!a'\u0006\t\u0013\ti\nC\u0004\u0002$\u0016!I!!*\t\u000f\u0005}V\u0001\"\u0001\u0002B\"9\u0011qY\u0003\u0005\u0002\u0005%\u0007bBAg\u000b\u0011\u0005\u0011q\u001a\u0005\b\u0003',A\u0011BAk\u0011\u001d\tY0\u0002C\u0005\u0003gBq!!@\u0006\t\u0013\t\u0019(A\u0006Gk:\u001cG/[8o%\u00164'BA\u0013'\u0003\u0015\t7\r^8s\u0015\u00059\u0013\u0001B1lW\u0006\u0004\"!K\u0001\u000e\u0003\u0011\u00121BR;oGRLwN\u001c*fMN\u0019\u0011\u0001\f\u001a\u0011\u00055\u0002T\"\u0001\u0018\u000b\u0003=\nQa]2bY\u0006L!!\r\u0018\u0003\r\u0005s\u0017PU3g!\t\u0019\u0004(D\u00015\u0015\t)d'\u0001\u0002j_*\tq'\u0001\u0003kCZ\f\u0017BA\u001d5\u00051\u0019VM]5bY&T\u0018M\u00197f\u0003\u0019a\u0014N\\5u}\r\u0001A#\u0001\u0015\u00021\u0011,\u0017\r\u001a'fiR,'/T3tg\u0006<W\rS1oI2,'\u000f\u0006\u0002@\u0017B)Q\u0006\u0011\"F\u0011&\u0011\u0011I\f\u0002\n\rVt7\r^5p]J\u0002\"!K\"\n\u0005\u0011##\u0001C!di>\u0014(+\u001a4\u0011\u000552\u0015BA$/\u0005\r\te.\u001f\t\u0003[%K!A\u0013\u0018\u0003\tUs\u0017\u000e\u001e\u0005\u0006\u0019\u000e\u0001\r!T\u0001\u0007gf\u001cH/Z7\u0011\u0005%r\u0015BA(%\u0005-\t5\r^8s'f\u001cH/Z7\u0002\u0019]\u0014\u0018\u000e^3SKBd\u0017mY3\u0015\u0003I\u0003\"a\u0015,\u000e\u0003QS!!\u0016\u001c\u0002\t1\fgnZ\u0005\u0003/R\u0013aa\u00142kK\u000e$\bFA\u0001Z!\tQV,D\u0001\\\u0015\taf%\u0001\u0006b]:|G/\u0019;j_:L!AX.\u0003\u0017%sG/\u001a:oC2\f\u0005/\u001b\u0015\u0003\u0001e\u001b2!B1e!\tI#-\u0003\u0002dI\t\u0001\u0012J\u001c;fe:\fG.Q2u_J\u0014VM\u001a\t\u0003S\u0015L!A\u001a\u0013\u0003\u001f5Kg.[7bY\u0006\u001bGo\u001c:SK\u001a\fA\u0001]1uQV\t\u0011\u000e\u0005\u0002*U&\u00111\u000e\n\u0002\n\u0003\u000e$xN\u001d)bi\"\fQ\u0001]1uQ\u0002\n\u0001\u0002\u001d:pm&$WM]\u000b\u0002_B\u0011\u0011\u0006]\u0005\u0003c\u0012\u0012\u0001#Q2u_J\u0014VM\u001a)s_ZLG-\u001a:\u0002\u0013A\u0014xN^5eKJ\u0004\u0013!\u00014\u0015\u000bU4x\u000f_=\u0011\u0005%*\u0001\"B4\r\u0001\u0004I\u0007\"B7\r\u0001\u0004y\u0007\"\u0002'\r\u0001\u0004i\u0005\"B:\r\u0001\u0004y\u0014AD7fgN\fw-\u001a%b]\u0012dWM]\u000b\u0002\u007f\u0005\u0011R.Z:tC\u001e,\u0007*\u00198eY\u0016\u0014x\fJ3r)\tAe\u0010C\u0004\u0000\u001d\u0005\u0005\t\u0019A \u0002\u0007a$\u0013'A\bnKN\u001c\u0018mZ3IC:$G.\u001a:!\u0003\u0015!#-\u00198h)\u0011\t9!!\u0004\u0015\u0007!\u000bI\u0001\u0003\u0005\u0002\fA\u0001\n\u0011q\u0001C\u0003\u0019\u0019XM\u001c3fe\"1\u0011q\u0002\tA\u0002\u0015\u000bq!\\3tg\u0006<W-A\b%E\u0006tw\r\n3fM\u0006,H\u000e\u001e\u00133)\u0011\t)\"a\n+\u0007\t\u000b9b\u000b\u0002\u0002\u001aA!\u00111DA\u0012\u001b\t\tiB\u0003\u0003\u0002 \u0005\u0005\u0012!C;oG\",7m[3e\u0015\taf&\u0003\u0003\u0002&\u0005u!!E;oG\",7m[3e-\u0006\u0014\u0018.\u00198dK\"1\u0011qB\tA\u0002\u0015\u000b\u0011c]3oINK8\u000f^3n\u001b\u0016\u001c8/Y4f)\rA\u0015Q\u0006\u0005\b\u0003\u001f\u0011\u0002\u0019AA\u0018!\u0011\t\t$a\u000f\u000e\u0005\u0005M\"\u0002BA\u001b\u0003o\taa]=t[N<'bAA\u001dM\u0005AA-[:qCR\u001c\u0007.\u0003\u0003\u0002>\u0005M\"!D*zgR,W.T3tg\u0006<W-\u0001\u0005xCR\u001c\u0007.\u001b8h!\u0015\t\u0019%!\u0015C\u001d\u0011\t)%!\u0014\u0011\u0007\u0005\u001dc&\u0004\u0002\u0002J)\u0019\u00111J\u001e\u0002\rq\u0012xn\u001c;?\u0013\r\tyEL\u0001\u0007!J,G-\u001a4\n\t\u0005M\u0013Q\u000b\u0002\u0004'\u0016$(bAA(]\u0005Qql^1uG\",GMQ=\u0011\r\u0005m\u0013\u0011MA!\u001b\t\tiFC\u0002\u0002`\u0019\nA!\u001e;jY&!\u00111MA/\u0005%y\u0005\u000f^5p]Z\u000bG.\u0001\u0007jgR+'/\\5oCR,G-\u0006\u0002\u0002jA\u0019Q&a\u001b\n\u0007\u00055dFA\u0004C_>dW-\u00198)\u0005UI\u0016AD:f]\u0012$VM]7j]\u0006$X\r\u001a\u000b\u0002\u0011R!\u0011qOA?)\rA\u0015\u0011\u0010\u0005\u0007\u0003w:\u0002\u0019\u0001\"\u0002\u000f]\fGo\u00195fe\"9\u0011qP\fA\u0002\u0005%\u0014aB5g\u0019>\u001c\u0017\r\\\u0001\u0012C\u0012$'/Z:t)\u0016\u0014X.\u001b8bi\u0016$Gc\u0001%\u0002\u0006\"9\u0011q\u0011\rA\u0002\u0005%\u0015aB1eIJ,7o\u001d\t\u0004S\u0005-\u0015bAAGI\t9\u0011\t\u001a3sKN\u001c\u0018\u0001B:u_B\f!\"\u00193e/\u0006$8\r[3s)\u0015A\u0015QSAM\u0011\u0019\t9J\u0007a\u0001\u0005\u00069q/\u0019;dQ\u0016,\u0007BBA>5\u0001\u0007!)\u0001\u0006sK6<\u0016\r^2iKJ$R\u0001SAP\u0003CCa!a&\u001c\u0001\u0004\u0011\u0005BBA>7\u0001\u0007!)A\u0004qk\nd\u0017n\u001d5\u0015\u0007!\u000b9\u000bC\u0004\u0002*r\u0001\r!a+\u0002\u0003\u0015\u0004B!!,\u0002::!\u0011qVA[\u001b\t\t\tLC\u0002\u00024\u001a\nQ!\u001a<f]RLA!a.\u00022\u00069Aj\\4hS:<\u0017\u0002BA^\u0003{\u0013\u0001\u0002T8h\u000bZ,g\u000e\u001e\u0006\u0005\u0003o\u000b\t,A\u0003xCR\u001c\u0007\u000eF\u0002I\u0003\u0007Da!!2\u001e\u0001\u0004\u0011\u0015\u0001C1di>\u0014(+\u001a4\u0002\u000fUtw/\u0019;dQR\u0019\u0001*a3\t\r\u0005\u0015g\u00041\u0001C\u0003)I7oV1uG\"Lgn\u001a\u000b\u0005\u0003S\n\t\u000e\u0003\u0004\u0002F~\u0001\rAQ\u0001&[\u0006Lg\u000e^1j]\u0006#GM]3tgR+'/\\5oCR,GmU;cg\u000e\u0014\u0018\u000e\u001d;j_:,B!a6\u0002`R!\u0011\u0011\\A{)\u0011\tY.a;\u0011\t\u0005u\u0017q\u001c\u0007\u0001\t\u001d\t\t\u000f\tb\u0001\u0003G\u0014\u0011\u0001V\t\u0004\u0003K,\u0005cA\u0017\u0002h&\u0019\u0011\u0011\u001e\u0018\u0003\u000f9{G\u000f[5oO\"A\u0011Q\u001e\u0011\u0005\u0002\u0004\ty/A\u0003cY>\u001c7\u000eE\u0003.\u0003c\fY.C\u0002\u0002t:\u0012\u0001\u0002\u00102z]\u0006lWM\u0010\u0005\b\u0003o\u0004\u0003\u0019AA}\u0003\u0019\u0019\u0007.\u00198hKB)\u00111LA1\u0005\u0006aRO\\:vEN\u001c'/\u001b2f\u0003\u0012$'/Z:t)\u0016\u0014X.\u001b8bi\u0016$\u0017AG:vEN\u001c'/\u001b2f\u0003\u0012$'/Z:t)\u0016\u0014X.\u001b8bi\u0016$\u0007FA\u0003Z\u0001")
@InternalApi
public final class FunctionRef
extends InternalActorRef
implements MinimalActorRef {
    private final ActorPath path;
    private final ActorRefProvider provider;
    private final ActorSystem system;
    private Function2<ActorRef, Object, BoxedUnit> messageHandler;
    private Set<ActorRef> watching;
    private Set<ActorRef> _watchedBy;

    public static Function2<ActorRef, Object, BoxedUnit> deadLetterMessageHandler(ActorSystem system) {
        return FunctionRef$.MODULE$.deadLetterMessageHandler(system);
    }

    @Override
    public InternalActorRef getParent() {
        return MinimalActorRef.getParent$(this);
    }

    @Override
    public InternalActorRef getChild(Iterator<String> names) {
        return MinimalActorRef.getChild$(this, names);
    }

    @Override
    public void start() {
        MinimalActorRef.start$(this);
    }

    @Override
    public void suspend() {
        MinimalActorRef.suspend$(this);
    }

    @Override
    public void resume(Throwable causedByFailure) {
        MinimalActorRef.resume$(this, causedByFailure);
    }

    @Override
    public void restart(Throwable cause) {
        MinimalActorRef.restart$(this, cause);
    }

    @Override
    public Object writeReplace() throws ObjectStreamException {
        return MinimalActorRef.writeReplace$(this);
    }

    @Override
    public final boolean isLocal() {
        return LocalRef.isLocal$(this);
    }

    @Override
    public ActorPath path() {
        return this.path;
    }

    @Override
    public ActorRefProvider provider() {
        return this.provider;
    }

    private Function2<ActorRef, Object, BoxedUnit> messageHandler() {
        return this.messageHandler;
    }

    private void messageHandler_$eq(Function2<ActorRef, Object, BoxedUnit> x$1) {
        this.messageHandler = x$1;
    }

    @Override
    public void $bang(Object message, ActorRef sender) {
        Object object = message;
        if (object instanceof AddressTerminated) {
            AddressTerminated addressTerminated = (AddressTerminated)object;
            Address address = addressTerminated.address();
            this.addressTerminated(address);
            return;
        }
        this.messageHandler().apply(sender, message);
    }

    @Override
    public void sendSystemMessage(SystemMessage message) {
        SystemMessage systemMessage = message;
        if (systemMessage instanceof Watch) {
            Watch watch = (Watch)systemMessage;
            this.addWatcher(watch.watchee(), watch.watcher());
            return;
        }
        if (systemMessage instanceof Unwatch) {
            Unwatch unwatch = (Unwatch)systemMessage;
            this.remWatcher(unwatch.watchee(), unwatch.watcher());
            return;
        }
        if (systemMessage instanceof DeathWatchNotification) {
            DeathWatchNotification deathWatchNotification = (DeathWatchNotification)systemMessage;
            ActorRef actorRef2 = deathWatchNotification.actor();
            this.$bang(Terminated$.MODULE$.apply(actorRef2, true, false), actorRef2);
            return;
        }
    }

    @Override
    @InternalApi
    public boolean isTerminated() {
        return OptionVal$.MODULE$.isEmpty$extension(this._watchedBy);
    }

    public void sendTerminated() {
        Tuple2<Set<ActorRef>, Set<ActorRef>> tuple2;
        FunctionRef functionRef = this;
        synchronized (functionRef) {
            Tuple2<Set<ActorRef>, Set<ActorRef>> tuple22;
            Set<ActorRef> set = this._watchedBy;
            Set<ActorRef> set2 = OptionVal$Some$.MODULE$.unapply(set);
            if (!OptionVal$.MODULE$.isEmpty$extension(set2)) {
                Set<ActorRef> wBy = OptionVal$.MODULE$.get$extension(set2);
                Set<ActorRef> oldWatching = this.watching;
                this.watching = Predef$.MODULE$.Set().empty();
                this.unsubscribeAddressTerminated();
                OptionVal$.MODULE$.None();
                this._watchedBy = null;
                tuple22 = new Tuple2<Set<ActorRef>, Set<ActorRef>>(oldWatching, wBy);
            } else {
                tuple22 = new Tuple2<Set<ActorRef>, Set<ActorRef>>(ActorCell$.MODULE$.emptyActorRefSet(), ActorCell$.MODULE$.emptyActorRefSet());
            }
            tuple2 = tuple22;
        }
        Tuple2<Set<ActorRef>, Set<ActorRef>> tuple23 = tuple2;
        if (tuple23 == null) {
            throw new MatchError(tuple23);
        }
        Set<ActorRef> toUnwatch = tuple23._1();
        Set watchedBy = (Set)tuple23._2();
        Tuple2<Set<ActorRef>, Set> tuple24 = new Tuple2<Set<ActorRef>, Set>(toUnwatch, watchedBy);
        Set<ActorRef> toUnwatch2 = tuple24._1();
        Set watchedBy2 = tuple24._2();
        if (toUnwatch2.nonEmpty()) {
            toUnwatch2.foreach((Function1<ActorRef, Object> & Serializable)watched -> {
                this.unwatchWatched$1(watched);
                return BoxedUnit.UNIT;
            });
        }
        if (watchedBy2.nonEmpty()) {
            watchedBy2.foreach((Function1<ActorRef, Object> & Serializable)watcher -> {
                this.sendTerminated(false, watcher);
                return BoxedUnit.UNIT;
            });
            watchedBy2.foreach((Function1<ActorRef, Object> & Serializable)watcher -> {
                this.sendTerminated(true, watcher);
                return BoxedUnit.UNIT;
            });
            return;
        }
    }

    private void sendTerminated(boolean ifLocal, ActorRef watcher) {
        if (((ActorRefScope)((Object)watcher)).isLocal() == ifLocal) {
            ((InternalActorRef)watcher).sendSystemMessage(new DeathWatchNotification(this, true, false));
            return;
        }
    }

    private void addressTerminated(Address address) {
        Set<ActorRef> set;
        FunctionRef functionRef = this;
        synchronized (functionRef) {
            Set<ActorRef> set2;
            Set<ActorRef> set3 = this._watchedBy;
            Set<ActorRef> set4 = OptionVal$Some$.MODULE$.unapply(set3);
            if (!OptionVal$.MODULE$.isEmpty$extension(set4)) {
                Set<ActorRef> watchedBy = OptionVal$.MODULE$.get$extension(set4);
                OptionVal$.MODULE$.None();
                this.maintainAddressTerminatedSubscription(null, () -> {
                    $this._watchedBy = (Set)OptionVal$Some$.MODULE$.apply(watchedBy.filterNot((Function1<ActorRef, Object> & Serializable)x$4 -> BoxesRunTime.boxToBoolean(FunctionRef.$anonfun$addressTerminated$2(address, x$4))));
                });
                set2 = this.watching;
            } else {
                set2 = ActorCell$.MODULE$.emptyActorRefSet();
            }
            set = set2;
        }
        Set<ActorRef> toNotify = set;
        toNotify.withFilter((Function1<ActorRef, Object> & Serializable)a -> BoxesRunTime.boxToBoolean(FunctionRef.$anonfun$addressTerminated$3(address, a))).foreach((Function1<ActorRef, Object> & Serializable)a -> {
            this.sendSystemMessage(new DeathWatchNotification(a, false, true));
            return BoxedUnit.UNIT;
        });
    }

    @Override
    public void stop() {
        this.messageHandler_$eq(FunctionRef$.MODULE$.deadLetterMessageHandler(this.system));
        this.sendTerminated();
    }

    private void addWatcher(ActorRef watchee, ActorRef watcher) {
        boolean bl;
        FunctionRef functionRef = this;
        synchronized (functionRef) {
            boolean bl2;
            Set<ActorRef> set = this._watchedBy;
            Set<ActorRef> set2 = OptionVal$Some$.MODULE$.unapply(set);
            if (!OptionVal$.MODULE$.isEmpty$extension(set2)) {
                Object object;
                boolean watcherSelf;
                Set<ActorRef> watchedBy = OptionVal$.MODULE$.get$extension(set2);
                ActorRef actorRef2 = watchee;
                FunctionRef functionRef2 = this;
                boolean watcheeSelf = !(actorRef2 != null ? !((Object)actorRef2).equals(functionRef2) : functionRef2 != null);
                ActorRef actorRef3 = watcher;
                FunctionRef functionRef3 = this;
                boolean bl3 = !(actorRef3 != null ? !((Object)actorRef3).equals(functionRef3) : functionRef3 != null) ? true : (watcherSelf = false);
                if (watcheeSelf && !watcherSelf) {
                    object = !watchedBy.contains(watcher) ? this.maintainAddressTerminatedSubscription(OptionVal$Some$.MODULE$.apply(watcher), () -> {
                        $this._watchedBy = (Set)OptionVal$Some$.MODULE$.apply(watchedBy.$plus(watcher));
                    }) : BoxedUnit.UNIT;
                } else {
                    if (!watcheeSelf && watcherSelf) {
                        this.publish(new Logging.Warning(this.path().toString(), FunctionRef.class, new StringBuilder(62).append("externally triggered watch from ").append(watcher).append(" to ").append(watchee).append(" is illegal on FunctionRef").toString()));
                    } else {
                        this.publish(Logging$Error$.MODULE$.apply(this.path().toString(), FunctionRef.class, new StringBuilder(26).append("BUG: illegal Watch(").append(watchee).append(",").append(watcher).append(") for ").append(this).toString()));
                    }
                    object = BoxedUnit.UNIT;
                }
                bl2 = false;
            } else {
                bl2 = true;
            }
            bl = bl2;
        }
        boolean selfTerminated = bl;
        if (selfTerminated) {
            this.sendTerminated(true, watcher);
            this.sendTerminated(false, watcher);
            return;
        }
    }

    private synchronized void remWatcher(ActorRef watchee, ActorRef watcher) {
        Set<ActorRef> set = this._watchedBy;
        Set<ActorRef> set2 = OptionVal$Some$.MODULE$.unapply(set);
        if (!OptionVal$.MODULE$.isEmpty$extension(set2)) {
            boolean watcherSelf;
            Set<ActorRef> watchedBy = OptionVal$.MODULE$.get$extension(set2);
            ActorRef actorRef2 = watchee;
            FunctionRef functionRef = this;
            boolean watcheeSelf = !(actorRef2 != null ? !((Object)actorRef2).equals(functionRef) : functionRef != null);
            ActorRef actorRef3 = watcher;
            FunctionRef functionRef2 = this;
            boolean bl = !(actorRef3 != null ? !((Object)actorRef3).equals(functionRef2) : functionRef2 != null) ? true : (watcherSelf = false);
            if (watcheeSelf && !watcherSelf) {
                if (watchedBy.contains(watcher)) {
                    BoxedUnit cfr_ignored_0 = (BoxedUnit)this.maintainAddressTerminatedSubscription(OptionVal$Some$.MODULE$.apply(watcher), () -> {
                        $this._watchedBy = (Set)OptionVal$Some$.MODULE$.apply(watchedBy.$minus(watcher));
                    });
                    return;
                }
                return;
            }
            if (!watcheeSelf && watcherSelf) {
                this.publish(new Logging.Warning(this.path().toString(), FunctionRef.class, new StringBuilder(64).append("externally triggered unwatch from ").append(watcher).append(" to ").append(watchee).append(" is illegal on FunctionRef").toString()));
                return;
            }
            this.publish(Logging$Error$.MODULE$.apply(this.path().toString(), FunctionRef.class, new StringBuilder(28).append("BUG: illegal Unwatch(").append(watchee).append(",").append(watcher).append(") for ").append(this).toString()));
            return;
        }
    }

    private void publish(Logging.LogEvent e) {
        try {
            this.system.eventStream().publish(e);
        }
        catch (Throwable throwable) {
            Option<Throwable> option;
            Throwable throwable2 = throwable;
            if (throwable2 != null && !(option = NonFatal$.MODULE$.unapply(throwable2)).isEmpty()) {
            }
            throw throwable;
        }
    }

    public void watch(ActorRef actorRef2) {
        FunctionRef functionRef = this;
        synchronized (functionRef) {
            this.maintainAddressTerminatedSubscription(OptionVal$Some$.MODULE$.apply(actorRef2), () -> {
                $this.watching = (Set)$this.watching.$plus(actorRef2);
            });
        }
        ((InternalActorRef)actorRef2).sendSystemMessage(new Watch((InternalActorRef)actorRef2, this));
    }

    public void unwatch(ActorRef actorRef2) {
        FunctionRef functionRef = this;
        synchronized (functionRef) {
            this.maintainAddressTerminatedSubscription(OptionVal$Some$.MODULE$.apply(actorRef2), () -> {
                $this.watching = (Set)$this.watching.$minus(actorRef2);
            });
        }
        ((InternalActorRef)actorRef2).sendSystemMessage(new Unwatch((InternalActorRef)actorRef2, this));
    }

    public synchronized boolean isWatching(ActorRef actorRef2) {
        return this.watching.contains(actorRef2);
    }

    private <T> T maintainAddressTerminatedSubscription(ActorRef change, Function0<T> block) {
        T result;
        block2: {
            boolean has;
            boolean had;
            block1: {
                ActorRef ref;
                ActorRef actorRef2 = change;
                ActorRef actorRef3 = OptionVal$Some$.MODULE$.unapply(actorRef2);
                if (!OptionVal$.MODULE$.isEmpty$extension(actorRef3) && !FunctionRef.isNonLocal$1(ref = OptionVal$.MODULE$.get$extension(actorRef3))) {
                    return block.apply();
                }
                had = this.hasNonLocalAddress$1();
                result = block.apply();
                has = this.hasNonLocalAddress$1();
                if (!had || has) break block1;
                this.unsubscribeAddressTerminated();
                break block2;
            }
            if (had || !has) break block2;
            this.subscribeAddressTerminated();
        }
        return result;
    }

    private void unsubscribeAddressTerminated() {
        ((AddressTerminatedTopic)AddressTerminatedTopic$.MODULE$.apply(this.system)).unsubscribe(this);
    }

    private void subscribeAddressTerminated() {
        ((AddressTerminatedTopic)AddressTerminatedTopic$.MODULE$.apply(this.system)).subscribe(this);
    }

    @Override
    public ActorRef $bang$default$2(Object message) {
        return Actor$.MODULE$.noSender();
    }

    private final void unwatchWatched$1(ActorRef watched) {
        ((InternalActorRef)watched).sendSystemMessage(new Unwatch(watched, this));
    }

    public static final /* synthetic */ boolean $anonfun$addressTerminated$2(Address address$1, ActorRef x$4) {
        Address address = x$4.path().address();
        Address address2 = address$1;
        return !(address != null ? !((Object)address).equals(address2) : address2 != null);
    }

    public static final /* synthetic */ boolean $anonfun$addressTerminated$3(Address address$1, ActorRef a) {
        Address address = a.path().address();
        Address address2 = address$1;
        return !(address != null ? !((Object)address).equals(address2) : address2 != null);
    }

    private static final boolean isNonLocal$1(ActorRef ref) {
        InternalActorRef internalActorRef;
        ActorRef actorRef2 = ref;
        return actorRef2 instanceof InternalActorRef && !(internalActorRef = (InternalActorRef)actorRef2).isLocal();
    }

    private final Set watchedByOrEmpty$1() {
        Set<ActorRef> set = this._watchedBy;
        Set<ActorRef> set2 = OptionVal$Some$.MODULE$.unapply(set);
        if (!OptionVal$.MODULE$.isEmpty$extension(set2)) {
            Set<ActorRef> watchedBy = OptionVal$.MODULE$.get$extension(set2);
            return watchedBy;
        }
        return ActorCell$.MODULE$.emptyActorRefSet();
    }

    private final boolean hasNonLocalAddress$1() {
        return this.watching.exists((Function1<ActorRef, Object> & Serializable)ref -> BoxesRunTime.boxToBoolean(FunctionRef.isNonLocal$1(ref))) || this.watchedByOrEmpty$1().exists((Function1<ActorRef, Object> & Serializable)ref -> BoxesRunTime.boxToBoolean(FunctionRef.isNonLocal$1(ref)));
    }

    public FunctionRef(ActorPath path, ActorRefProvider provider, ActorSystem system, Function2<ActorRef, Object, BoxedUnit> f) {
        this.path = path;
        this.provider = provider;
        this.system = system;
        LocalRef.$init$(this);
        MinimalActorRef.$init$(this);
        this.messageHandler = f;
        this.watching = ActorCell$.MODULE$.emptyActorRefSet();
        this._watchedBy = OptionVal$Some$.MODULE$.apply(ActorCell$.MODULE$.emptyActorRefSet());
    }
}

