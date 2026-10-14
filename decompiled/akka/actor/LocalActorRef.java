/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.Actor$;
import akka.actor.ActorCell;
import akka.actor.ActorContext;
import akka.actor.ActorPath;
import akka.actor.ActorRef;
import akka.actor.ActorRefProvider;
import akka.actor.ActorRefWithCell;
import akka.actor.ActorSystemImpl;
import akka.actor.InternalActorRef;
import akka.actor.LocalRef;
import akka.actor.Nobody$;
import akka.actor.Props;
import akka.actor.SerializedActorRef$;
import akka.annotation.InternalApi;
import akka.dispatch.MailboxType;
import akka.dispatch.MessageDispatcher;
import akka.dispatch.sysmsg.SystemMessage;
import java.io.ObjectStreamException;
import scala.collection.Iterator;
import scala.collection.immutable.Iterable;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005]g!B\u000f\u001f\u0001\u0001\u0012\u0003\u0002\u0003\u0016\u0001\u0005\u0003\u0005\u000b\u0011\u0002\u0017\t\u0011=\u0002!\u0011!Q\u0001\nAB\u0001b\r\u0001\u0003\u0002\u0003\u0006I\u0001\u000e\u0005\tu\u0001\u0011\t\u0011)A\u0005w!Aa\b\u0001B\u0001B\u0003%q\b\u0003\u0005C\u0001\t\u0015\r\u0011\"\u0011D\u0011!9\u0005A!A!\u0002\u0013!\u0005B\u0002%\u0001\t\u0003\u0001\u0013\nC\u0004R\u0001\t\u0007I\u0011\u0002*\t\rY\u0003\u0001\u0015!\u0003T\u0011\u00159\u0006\u0001\"\u0005Y\u0011\u0015\u0019\u0007\u0001\"\u0005e\u0011\u0019A\u0007\u0001\"\u0011!S\")q\u000f\u0001C!q\")A\u0010\u0001C!q\")Q\u0010\u0001C!}\"1\u00111\u0004\u0001\u0005BaDq!!\b\u0001\t\u0003\ny\u0002C\u0004\u0002\"\u0001!\t%a\t\t\u000f\u0005-\u0002\u0001\"\u0001\u0002.!9\u0011Q\t\u0001\u0005\u0002\u0005\u001d\u0003bBA/\u0001\u0011\u0005\u0013q\f\u0005\u0007\u0003W\u0002A\u0011\u0001*\t\u000f\u00055\u0004\u0001\"\u0011\u0002p!9\u0011\u0011\u0011\u0001\u0005B\u0005\r\u0005\"CAJ\u0001E\u0005I\u0011AAK\u0011\u001d\tY\u000b\u0001C!\u0003[Cq!a-\u0001\t#\t)LA\u0007M_\u000e\fG.Q2u_J\u0014VM\u001a\u0006\u0003?\u0001\nQ!Y2u_JT\u0011!I\u0001\u0005C.\\\u0017mE\u0002\u0001G\u001d\u0002\"\u0001J\u0013\u000e\u0003yI!A\n\u0010\u0003!\u0005\u001bGo\u001c:SK\u001a<\u0016\u000e\u001e5DK2d\u0007C\u0001\u0013)\u0013\tIcD\u0001\u0005M_\u000e\fGNU3g\u0003\u001dy6/_:uK6\u001c\u0001\u0001\u0005\u0002%[%\u0011aF\b\u0002\u0010\u0003\u000e$xN]*zgR,W.S7qY\u00061q\f\u001d:paN\u0004\"\u0001J\u0019\n\u0005Ir\"!\u0002)s_B\u001c\u0018aC0eSN\u0004\u0018\r^2iKJ\u0004\"!\u000e\u001d\u000e\u0003YR!a\u000e\u0011\u0002\u0011\u0011L7\u000f]1uG\"L!!\u000f\u001c\u0003#5+7o]1hK\u0012K7\u000f]1uG\",'/\u0001\u0007`[\u0006LGNY8y)f\u0004X\r\u0005\u00026y%\u0011QH\u000e\u0002\f\u001b\u0006LGNY8y)f\u0004X-A\u0006`gV\u0004XM\u001d<jg>\u0014\bC\u0001\u0013A\u0013\t\teD\u0001\tJ]R,'O\\1m\u0003\u000e$xN\u001d*fM\u0006!\u0001/\u0019;i+\u0005!\u0005C\u0001\u0013F\u0013\t1eDA\u0005BGR|'\u000fU1uQ\u0006)\u0001/\u0019;iA\u00051A(\u001b8jiz\"rAS&M\u001b:{\u0005\u000b\u0005\u0002%\u0001!)!\u0006\u0003a\u0001Y!)q\u0006\u0003a\u0001a!)1\u0007\u0003a\u0001i!)!\b\u0003a\u0001w!)a\b\u0003a\u0001\u007f!)!\t\u0003a\u0001\t\u0006I\u0011m\u0019;pe\u000e+G\u000e\\\u000b\u0002'B\u0011A\u0005V\u0005\u0003+z\u0011\u0011\"Q2u_J\u001cU\r\u001c7\u0002\u0015\u0005\u001cGo\u001c:DK2d\u0007%\u0001\u0007oK^\f5\r^8s\u0007\u0016dG\u000e\u0006\u0004T3nkv,\u0019\u0005\u00065.\u0001\r\u0001L\u0001\u0007gf\u001cH/Z7\t\u000bq[\u0001\u0019A \u0002\u0007I,g\rC\u0003_\u0017\u0001\u0007\u0001'A\u0003qe>\u00048\u000fC\u0003a\u0017\u0001\u0007A'\u0001\u0006eSN\u0004\u0018\r^2iKJDQAY\u0006A\u0002}\n!b];qKJ4\u0018n]8s\u00031\t7\r^8s\u0007>tG/\u001a=u+\u0005)\u0007C\u0001\u0013g\u0013\t9gD\u0001\u0007BGR|'oQ8oi\u0016DH/\u0001\u0007jgR+'/\\5oCR,G-F\u0001k!\tYg.D\u0001m\u0015\u0005i\u0017!B:dC2\f\u0017BA8m\u0005\u001d\u0011un\u001c7fC:D#!D9\u0011\u0005I,X\"A:\u000b\u0005Q\u0004\u0013AC1o]>$\u0018\r^5p]&\u0011ao\u001d\u0002\f\u0013:$XM\u001d8bY\u0006\u0003\u0018.A\u0003ti\u0006\u0014H\u000fF\u0001z!\tY'0\u0003\u0002|Y\n!QK\\5u\u0003\u001d\u0019Xo\u001d9f]\u0012\faA]3tk6,GCA=\u0000\u0011\u001d\t\t\u0001\u0005a\u0001\u0003\u0007\tqbY1vg\u0016$')\u001f$bS2,(/\u001a\t\u0005\u0003\u000b\t)B\u0004\u0003\u0002\b\u0005Ea\u0002BA\u0005\u0003\u001fi!!a\u0003\u000b\u0007\u000551&\u0001\u0004=e>|GOP\u0005\u0002[&\u0019\u00111\u00037\u0002\u000fA\f7m[1hK&!\u0011qCA\r\u0005%!\u0006N]8xC\ndWMC\u0002\u0002\u00141\fAa\u001d;pa\u0006Iq-\u001a;QCJ,g\u000e^\u000b\u0002\u007f\u0005A\u0001O]8wS\u0012,'/\u0006\u0002\u0002&A\u0019A%a\n\n\u0007\u0005%bD\u0001\tBGR|'OU3g!J|g/\u001b3fe\u0006A1\r[5mIJ,g.\u0006\u0002\u00020A1\u0011\u0011GA\u001e\u0003\u007fi!!a\r\u000b\t\u0005U\u0012qG\u0001\nS6lW\u000f^1cY\u0016T1!!\u000fm\u0003)\u0019w\u000e\u001c7fGRLwN\\\u0005\u0005\u0003{\t\u0019D\u0001\u0005Ji\u0016\u0014\u0018M\u00197f!\r!\u0013\u0011I\u0005\u0004\u0003\u0007r\"\u0001C!di>\u0014(+\u001a4\u0002\u001d\u001d,GoU5oO2,7\t[5mIR\u0019q(!\u0013\t\u000f\u0005-S\u00031\u0001\u0002N\u0005!a.Y7f!\u0011\ty%a\u0016\u000f\t\u0005E\u00131\u000b\t\u0004\u0003\u0013a\u0017bAA+Y\u00061\u0001K]3eK\u001aLA!!\u0017\u0002\\\t11\u000b\u001e:j]\u001eT1!!\u0016m\u0003!9W\r^\"iS2$GcA \u0002b!9\u00111\r\fA\u0002\u0005\u0015\u0014!\u00028b[\u0016\u001c\bCBA\u0003\u0003O\ni%\u0003\u0003\u0002j\u0005e!\u0001C%uKJ\fGo\u001c:\u0002\u0015UtG-\u001a:ms&tw-A\ttK:$7+_:uK6lUm]:bO\u0016$2!_A9\u0011\u001d\t\u0019\b\u0007a\u0001\u0003k\nq!\\3tg\u0006<W\r\u0005\u0003\u0002x\u0005uTBAA=\u0015\r\tYHN\u0001\u0007gf\u001cXn]4\n\t\u0005}\u0014\u0011\u0010\u0002\u000e'f\u001cH/Z7NKN\u001c\u0018mZ3\u0002\u000b\u0011\u0012\u0017M\\4\u0015\t\u0005\u0015\u00151\u0012\u000b\u0004s\u0006\u001d\u0005\"CAE3A\u0005\t9AA \u0003\u0019\u0019XM\u001c3fe\"9\u00111O\rA\u0002\u00055\u0005cA6\u0002\u0010&\u0019\u0011\u0011\u00137\u0003\u0007\u0005s\u00170A\b%E\u0006tw\r\n3fM\u0006,H\u000e\u001e\u00133)\u0011\t9*!++\t\u0005}\u0012\u0011T\u0016\u0003\u00037\u0003B!!(\u0002&6\u0011\u0011q\u0014\u0006\u0005\u0003C\u000b\u0019+A\u0005v]\u000eDWmY6fI*\u0011A\u000f\\\u0005\u0005\u0003O\u000byJA\tv]\u000eDWmY6fIZ\u000b'/[1oG\u0016Dq!a\u001d\u001b\u0001\u0004\ti)A\u0004sKN$\u0018M\u001d;\u0015\u0007e\fy\u000bC\u0004\u00022n\u0001\r!a\u0001\u0002\u000b\r\fWo]3\u0002\u0019]\u0014\u0018\u000e^3SKBd\u0017mY3\u0015\u0005\u0005]\u0006cA6\u0002:&\u0019\u00111\u00187\u0003\r\u0005s\u0017PU3gQ\u0015a\u0012qXAk!\u0015Y\u0017\u0011YAc\u0013\r\t\u0019\r\u001c\u0002\u0007i\"\u0014xn^:\u0011\t\u0005\u001d\u0017\u0011[\u0007\u0003\u0003\u0013TA!a3\u0002N\u0006\u0011\u0011n\u001c\u0006\u0003\u0003\u001f\fAA[1wC&!\u00111[Ae\u0005Uy%M[3diN#(/Z1n\u000bb\u001cW\r\u001d;j_:\u001c#!!2")
public class LocalActorRef
extends ActorRefWithCell
implements LocalRef {
    private final ActorPath path;
    private final ActorCell actorCell;

    @Override
    public final boolean isLocal() {
        return LocalRef.isLocal$(this);
    }

    @Override
    public ActorPath path() {
        return this.path;
    }

    private ActorCell actorCell() {
        return this.actorCell;
    }

    public ActorCell newActorCell(ActorSystemImpl system, InternalActorRef ref, Props props, MessageDispatcher dispatcher, InternalActorRef supervisor) {
        return new ActorCell(system, ref, props, dispatcher, supervisor);
    }

    public ActorContext actorContext() {
        return this.actorCell();
    }

    @Override
    @InternalApi
    public boolean isTerminated() {
        return this.actorCell().isTerminated();
    }

    @Override
    public void start() {
        this.actorCell().start();
    }

    @Override
    public void suspend() {
        this.actorCell().suspend();
    }

    @Override
    public void resume(Throwable causedByFailure) {
        this.actorCell().resume(causedByFailure);
    }

    @Override
    public void stop() {
        this.actorCell().stop();
    }

    @Override
    public InternalActorRef getParent() {
        return this.actorCell().parent();
    }

    @Override
    public ActorRefProvider provider() {
        return this.actorCell().provider();
    }

    @Override
    public Iterable<ActorRef> children() {
        return this.actorCell().children();
    }

    @Override
    public InternalActorRef getSingleChild(String name) {
        return this.actorCell().getSingleChild(name);
    }

    @Override
    public InternalActorRef getChild(Iterator<String> names) {
        if (names.isEmpty()) {
            return this;
        }
        return this.rec$1(this, names);
    }

    @Override
    public ActorCell underlying() {
        return this.actorCell();
    }

    @Override
    public void sendSystemMessage(SystemMessage message) {
        this.actorCell().sendSystemMessage(message);
    }

    @Override
    public void $bang(Object message, ActorRef sender) {
        this.actorCell().sendMessage(message, sender);
    }

    @Override
    public void restart(Throwable cause) {
        this.actorCell().restart(cause);
    }

    public Object writeReplace() throws ObjectStreamException {
        return SerializedActorRef$.MODULE$.apply(this);
    }

    @Override
    public ActorRef $bang$default$2(Object message) {
        return Actor$.MODULE$.noSender();
    }

    private final InternalActorRef rec$1(InternalActorRef ref, Iterator name) {
        InternalActorRef internalActorRef;
        while ((internalActorRef = ref) instanceof LocalActorRef) {
            InternalActorRef next;
            InternalActorRef internalActorRef2;
            block6: {
                LocalActorRef localActorRef = (LocalActorRef)internalActorRef;
                String string = (String)name.next();
                switch (string == null ? 0 : string.hashCode()) {
                    case 0: {
                        if (!"".equals(string)) break;
                        internalActorRef2 = localActorRef;
                        break block6;
                    }
                    case 1472: {
                        if (!"..".equals(string)) break;
                        internalActorRef2 = localActorRef.getParent();
                        break block6;
                    }
                }
                internalActorRef2 = localActorRef.getSingleChild(string);
            }
            InternalActorRef internalActorRef3 = next = internalActorRef2;
            Nobody$ nobody$ = Nobody$.MODULE$;
            if (!(internalActorRef3 == null ? nobody$ != null : !((Object)internalActorRef3).equals(nobody$)) || name.isEmpty()) {
                return next;
            }
            ref = next;
        }
        return ref.getChild(name);
    }

    public LocalActorRef(ActorSystemImpl _system, Props _props, MessageDispatcher _dispatcher, MailboxType _mailboxType, InternalActorRef _supervisor, ActorPath path) {
        this.path = path;
        LocalRef.$init$(this);
        this.actorCell = this.newActorCell(_system, this, _props, _dispatcher, _supervisor);
        this.actorCell().init(true, _mailboxType);
    }
}

