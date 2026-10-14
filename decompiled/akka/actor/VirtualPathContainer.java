/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.Actor$;
import akka.actor.ActorPath;
import akka.actor.ActorRef;
import akka.actor.ActorRefProvider;
import akka.actor.ActorSelectionMessage;
import akka.actor.EmptyLocalActorRef;
import akka.actor.InternalActorRef;
import akka.actor.LocalRef;
import akka.actor.MinimalActorRef;
import akka.actor.Nobody$;
import akka.actor.SelectChildName;
import akka.actor.SelectionPathElement;
import akka.dispatch.sysmsg.SystemMessage;
import akka.event.MarkerLoggingAdapter;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;
import scala.Function1;
import scala.Predef$;
import scala.collection.Iterable;
import scala.collection.IterableOnceOps;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;

@ScalaSignature(bytes="\u0006\u0005\u0005\u0005c!B\u000b\u0017\u0001aQ\u0002\u0002\u0003\u0012\u0001\u0005\u000b\u0007I\u0011\t\u0013\t\u0011!\u0002!\u0011!Q\u0001\n\u0015B\u0001\"\u000b\u0001\u0003\u0006\u0004%\tE\u000b\u0005\t]\u0001\u0011\t\u0011)A\u0005W!Aq\u0006\u0001BC\u0002\u0013\u0005\u0003\u0007\u0003\u00052\u0001\t\u0005\t\u0015!\u0003\u001c\u0011!\u0011\u0004A!b\u0001\n\u0003\u0019\u0004\u0002\u0003\u001e\u0001\u0005\u0003\u0005\u000b\u0011\u0002\u001b\t\u000bm\u0002A\u0011\u0001\u001f\t\u000f\t\u0003!\u0019!C\u0005\u0007\"11\f\u0001Q\u0001\n\u0011CQ\u0001\u0018\u0001\u0005BuCq!\u001c\u0001\u0012\u0002\u0013\u0005a\u000eC\u0003{\u0001\u0011\u00051\u0010C\u0004\u0002\u0002\u0001!\t!a\u0001\t\u000f\u0005\u0005\u0001\u0001\"\u0005\u0002\b!9\u0011Q\u0002\u0001\u0005\u0002\u0005=\u0001bBA\u0007\u0001\u0011\u0005\u00131\u0003\u0005\b\u0003S\u0001A\u0011AA\u0016\u0011\u001d\t\u0019\u0004\u0001C\u0001\u0003k\u0011ACV5siV\fG\u000eU1uQ\u000e{g\u000e^1j]\u0016\u0014(BA\f\u0019\u0003\u0015\t7\r^8s\u0015\u0005I\u0012\u0001B1lW\u0006\u001c2\u0001A\u000e !\taR$D\u0001\u0017\u0013\tqbC\u0001\tJ]R,'O\\1m\u0003\u000e$xN\u001d*fMB\u0011A\u0004I\u0005\u0003CY\u0011q\"T5oS6\fG.Q2u_J\u0014VMZ\u0001\taJ|g/\u001b3fe\u000e\u0001Q#A\u0013\u0011\u0005q1\u0013BA\u0014\u0017\u0005A\t5\r^8s%\u00164\u0007K]8wS\u0012,'/A\u0005qe>4\u0018\u000eZ3sA\u0005!\u0001/\u0019;i+\u0005Y\u0003C\u0001\u000f-\u0013\ticCA\u0005BGR|'\u000fU1uQ\u0006)\u0001/\u0019;iA\u0005Iq-\u001a;QCJ,g\u000e^\u000b\u00027\u0005Qq-\u001a;QCJ,g\u000e\u001e\u0011\u0002\u00071|w-F\u00015!\t)\u0004(D\u00017\u0015\t9\u0004$A\u0003fm\u0016tG/\u0003\u0002:m\t!R*\u0019:lKJdunZ4j]\u001e\fE-\u00199uKJ\fA\u0001\\8hA\u00051A(\u001b8jiz\"R!\u0010 @\u0001\u0006\u0003\"\u0001\b\u0001\t\u000b\tJ\u0001\u0019A\u0013\t\u000b%J\u0001\u0019A\u0016\t\u000b=J\u0001\u0019A\u000e\t\u000bIJ\u0001\u0019\u0001\u001b\u0002\u0011\rD\u0017\u000e\u001c3sK:,\u0012\u0001\u0012\t\u0005\u000b2s5$D\u0001G\u0015\t9\u0005*\u0001\u0006d_:\u001cWO\u001d:f]RT!!\u0013&\u0002\tU$\u0018\u000e\u001c\u0006\u0002\u0017\u0006!!.\u0019<b\u0013\tieIA\tD_:\u001cWO\u001d:f]RD\u0015m\u001d5NCB\u0004\"a\u0014-\u000f\u0005A3\u0006CA)U\u001b\u0005\u0011&BA*$\u0003\u0019a$o\\8u})\tQ+A\u0003tG\u0006d\u0017-\u0003\u0002X)\u00061\u0001K]3eK\u001aL!!\u0017.\u0003\rM#(/\u001b8h\u0015\t9F+A\u0005dQ&dGM]3oA\u0005)AEY1oOR\u0011a\f\u001b\u000b\u0003?\u000e\u0004\"\u0001Y1\u000e\u0003QK!A\u0019+\u0003\tUs\u0017\u000e\u001e\u0005\bI2\u0001\n\u0011q\u0001f\u0003\u0019\u0019XM\u001c3feB\u0011ADZ\u0005\u0003OZ\u0011\u0001\"Q2u_J\u0014VM\u001a\u0005\u0006S2\u0001\rA[\u0001\b[\u0016\u001c8/Y4f!\t\u00017.\u0003\u0002m)\n\u0019\u0011I\\=\u0002\u001f\u0011\u0012\u0017M\\4%I\u00164\u0017-\u001e7uII\"\"a\\=+\u0005\u0015\u00048&A9\u0011\u0005I<X\"A:\u000b\u0005Q,\u0018!C;oG\",7m[3e\u0015\t1H+\u0001\u0006b]:|G/\u0019;j_:L!\u0001_:\u0003#Ut7\r[3dW\u0016$g+\u0019:jC:\u001cW\rC\u0003j\u001b\u0001\u0007!.\u0001\u0005bI\u0012\u001c\u0005.\u001b7e)\ryFP \u0005\u0006{:\u0001\rAT\u0001\u0005]\u0006lW\rC\u0003\u0000\u001d\u0001\u00071$A\u0002sK\u001a\f1B]3n_Z,7\t[5mIR\u0019q,!\u0002\t\u000bu|\u0001\u0019\u0001(\u0015\u000b}\u000bI!a\u0003\t\u000bu\u0004\u0002\u0019\u0001(\t\u000b}\u0004\u0002\u0019A3\u0002\u0011\u001d,Go\u00115jY\u0012$2aGA\t\u0011\u0015i\u0018\u00031\u0001O)\rY\u0012Q\u0003\u0005\u0007{J\u0001\r!a\u0006\u0011\u000b\u0005e\u00111\u0005(\u000f\t\u0005m\u0011q\u0004\b\u0004#\u0006u\u0011\"A+\n\u0007\u0005\u0005B+A\u0004qC\u000e\\\u0017mZ3\n\t\u0005\u0015\u0012q\u0005\u0002\t\u0013R,'/\u0019;pe*\u0019\u0011\u0011\u0005+\u0002\u0017!\f7o\u00115jY\u0012\u0014XM\\\u000b\u0003\u0003[\u00012\u0001YA\u0018\u0013\r\t\t\u0004\u0016\u0002\b\u0005>|G.Z1o\u000311wN]3bG\"\u001c\u0005.\u001b7e)\ry\u0016q\u0007\u0005\b\u0003s!\u0002\u0019AA\u001e\u0003\u00051\u0007#\u00021\u0002>\u0015|\u0016bAA )\nIa)\u001e8di&|g.\r")
public class VirtualPathContainer
extends InternalActorRef
implements MinimalActorRef {
    private final ActorRefProvider provider;
    private final ActorPath path;
    private final InternalActorRef getParent;
    private final MarkerLoggingAdapter log;
    private final ConcurrentHashMap<String, InternalActorRef> children;

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
    public void stop() {
        MinimalActorRef.stop$(this);
    }

    @Override
    public boolean isTerminated() {
        return MinimalActorRef.isTerminated$(this);
    }

    @Override
    public void sendSystemMessage(SystemMessage message) {
        MinimalActorRef.sendSystemMessage$(this, message);
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
    public ActorRefProvider provider() {
        return this.provider;
    }

    @Override
    public ActorPath path() {
        return this.path;
    }

    @Override
    public InternalActorRef getParent() {
        return this.getParent;
    }

    public MarkerLoggingAdapter log() {
        return this.log;
    }

    private ConcurrentHashMap<String, InternalActorRef> children() {
        return this.children;
    }

    @Override
    public void $bang(Object message, ActorRef sender) {
        Object object = message;
        if (object instanceof ActorSelectionMessage) {
            ActorSelectionMessage actorSelectionMessage = (ActorSelectionMessage)object;
            Object msg = actorSelectionMessage.msg();
            scala.collection.immutable.Iterable<SelectionPathElement> elements = actorSelectionMessage.elements();
            boolean wildcardFanOut = actorSelectionMessage.wildcardFanOut();
            Predef$.MODULE$.require(elements.nonEmpty());
            SelectionPathElement selectionPathElement = (SelectionPathElement)elements.head();
            if (selectionPathElement instanceof SelectChildName) {
                SelectChildName selectChildName = (SelectChildName)selectionPathElement;
                String name = selectChildName.name();
                InternalActorRef internalActorRef = this.getChild(name);
                if (internalActorRef == null) {
                    if (!wildcardFanOut) {
                        this.emptyRef$1(actorSelectionMessage).tell(msg, sender);
                    }
                } else if (((IterableOnceOps)elements.tail()).isEmpty()) {
                    internalActorRef.$bang(msg, sender);
                } else if (!wildcardFanOut) {
                    this.emptyRef$1(actorSelectionMessage).tell(msg, sender);
                }
            } else if (!wildcardFanOut) {
                this.emptyRef$1(actorSelectionMessage).tell(msg, sender);
            }
            return;
        }
        MinimalActorRef.$bang$(this, message, sender);
    }

    public void addChild(String name, InternalActorRef ref) {
        InternalActorRef internalActorRef = this.children().put(name, ref);
        if (internalActorRef == null) {
            return;
        }
        this.log().debug("{} replacing child {} ({} -> {})", this.path(), (Object)name, (Object)internalActorRef, (Object)ref);
        internalActorRef.stop();
    }

    public void removeChild(String name) {
        if (this.children().remove(name) == null) {
            this.log().warning("{} trying to remove non-child {}", this.path(), (Object)name);
            return;
        }
    }

    public void removeChild(String name, ActorRef ref) {
        InternalActorRef current = this.getChild(name);
        if (current == null) {
            this.log().warning("{} trying to remove non-child {}", this.path(), (Object)name);
            return;
        }
        InternalActorRef internalActorRef = current;
        ActorRef actorRef2 = ref;
        if (!(internalActorRef != null ? !((Object)internalActorRef).equals(actorRef2) : actorRef2 != null)) {
            this.children().remove(name, current);
            return;
        }
    }

    public InternalActorRef getChild(String name) {
        return this.children().get(name);
    }

    @Override
    public InternalActorRef getChild(Iterator<String> name) {
        if (name.isEmpty()) {
            return this;
        }
        String n = name.next();
        if (n.isEmpty()) {
            return this;
        }
        InternalActorRef internalActorRef = this.children().get(n);
        if (internalActorRef == null) {
            return Nobody$.MODULE$;
        }
        if (name.isEmpty()) {
            return internalActorRef;
        }
        return internalActorRef.getChild(name);
    }

    public boolean hasChildren() {
        return !this.children().isEmpty();
    }

    public void foreachChild(Function1<ActorRef, BoxedUnit> f) {
        java.util.Iterator<InternalActorRef> iter = this.children().values().iterator();
        while (iter.hasNext()) {
            f.apply(iter.next());
        }
    }

    @Override
    public ActorRef $bang$default$2(Object message) {
        return Actor$.MODULE$.noSender();
    }

    private final EmptyLocalActorRef emptyRef$1(ActorSelectionMessage x2$1) {
        return new EmptyLocalActorRef(this.provider(), this.path().$div((Iterable)x2$1.elements().map((Function1<SelectionPathElement, String> & Serializable)x$2 -> x$2.toString())), this.provider().systemGuardian().underlying().system().eventStream());
    }

    public VirtualPathContainer(ActorRefProvider provider, ActorPath path, InternalActorRef getParent, MarkerLoggingAdapter log) {
        this.provider = provider;
        this.path = path;
        this.getParent = getParent;
        this.log = log;
        LocalRef.$init$(this);
        MinimalActorRef.$init$(this);
        this.children = new ConcurrentHashMap();
    }
}

