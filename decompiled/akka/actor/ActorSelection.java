/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.ActorContext;
import akka.actor.ActorIdentity;
import akka.actor.ActorNotFound;
import akka.actor.ActorPath;
import akka.actor.ActorRef;
import akka.actor.ActorRefWithCell;
import akka.actor.ActorSelection$;
import akka.actor.ActorSelectionMessage;
import akka.actor.Identify;
import akka.actor.InternalActorRef;
import akka.actor.ScalaActorSelection;
import akka.actor.SelectionPathElement;
import akka.dispatch.ExecutionContexts$;
import akka.pattern.AskableActorSelection$;
import akka.pattern.package$;
import akka.routing.MurmurHash$;
import akka.util.JavaDurationConverters$;
import akka.util.JavaDurationConverters$JavaDurationOps$;
import akka.util.Timeout;
import akka.util.Timeout$;
import java.io.Serializable;
import java.time.Duration;
import java.util.concurrent.CompletionStage;
import scala.Function1;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.collection.Iterable;
import scala.collection.immutable.IndexedSeq;
import scala.compat.java8.FutureConverters$;
import scala.concurrent.ExecutionContext;
import scala.concurrent.Future;
import scala.concurrent.Promise;
import scala.concurrent.Promise$;
import scala.concurrent.duration.FiniteDuration;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.Statics;
import scala.util.Success;
import scala.util.Try;

@ScalaSignature(bytes="\u0006\u0005\u0005]faB\f\u0019\u0003\u0003i\u0012q\t\u0005\u0006a\u0001!\t!\r\u0005\ti\u0001\u0011\rQ\"\u0005\u001bk!9\u0011\b\u0001b\u0001\u000e#Q\u0004\"\u0002$\u0001\t\u00039\u0005\"\u0002*\u0001\t\u0003\u0019\u0006\"\u0002/\u0001\t\u0003i\u0006\"\u0002/\u0001\t\u0003i\u0007\"\u0002/\u0001\t\u0003)\bbBA\u0006\u0001\u0011\u0005\u0013Q\u0002\u0005\b\u0003?\u0001A\u0011AA\u0011\u0011\u001d\tI\u0003\u0001C\u0001\u0003WAq!!\f\u0001\t\u0003\tY\u0003C\u0004\u00020\u0001!\t%!\r\t\u0015\u0005u\u0002\u0001#b\u0001\n\u0003\nydB\u0004\u0002`aA\t!!\u0019\u0007\r]A\u0002\u0012AA2\u0011\u0019\u0001\u0004\u0003\"\u0001\u0002p!9\u0011\u0011\u000f\t\u0005\u0004\u0005M\u0004bBA=!\u0011\u0005\u00111\u0010\u0005\b\u0003s\u0002B\u0011AAB\u0011!\t\t\n\u0005C\u00015\u0005M\u0005\"CAT!\u0005\u0005I\u0011BAU\u00059\t5\r^8s'\u0016dWm\u0019;j_:T!!\u0007\u000e\u0002\u000b\u0005\u001cGo\u001c:\u000b\u0003m\tA!Y6lC\u000e\u00011c\u0001\u0001\u001fIA\u0011qDI\u0007\u0002A)\t\u0011%A\u0003tG\u0006d\u0017-\u0003\u0002$A\t1\u0011I\\=SK\u001a\u0004\"!J\u0017\u000f\u0005\u0019ZcBA\u0014+\u001b\u0005A#BA\u0015\u001d\u0003\u0019a$o\\8u}%\t\u0011%\u0003\u0002-A\u00059\u0001/Y2lC\u001e,\u0017B\u0001\u00180\u00051\u0019VM]5bY&T\u0018M\u00197f\u0015\ta\u0003%\u0001\u0004=S:LGO\u0010\u000b\u0002eA\u00111\u0007A\u0007\u00021\u00051\u0011M\\2i_J,\u0012A\u000e\t\u0003g]J!\u0001\u000f\r\u0003\u0011\u0005\u001bGo\u001c:SK\u001a\fA\u0001]1uQV\t1\bE\u0002=\u0003\u000ek\u0011!\u0010\u0006\u0003}}\n\u0011\"[7nkR\f'\r\\3\u000b\u0005\u0001\u0003\u0013AC2pY2,7\r^5p]&\u0011!)\u0010\u0002\u000b\u0013:$W\r_3e'\u0016\f\bCA\u001aE\u0013\t)\u0005D\u0001\u000bTK2,7\r^5p]B\u000bG\u000f[#mK6,g\u000e^\u0001\u0005i\u0016dG\u000eF\u0002I\u0017B\u0003\"aH%\n\u0005)\u0003#\u0001B+oSRDQ\u0001\u0014\u0003A\u00025\u000b1!\\:h!\tyb*\u0003\u0002PA\t\u0019\u0011I\\=\t\u000bE#\u0001\u0019\u0001\u001c\u0002\rM,g\u000eZ3s\u0003\u001d1wN]<be\u0012$\"\u0001\u0016.\u0015\u0005!+\u0006\"\u0002,\u0006\u0001\b9\u0016aB2p]R,\u0007\u0010\u001e\t\u0003gaK!!\u0017\r\u0003\u0019\u0005\u001bGo\u001c:D_:$X\r\u001f;\t\u000bm+\u0001\u0019A'\u0002\u000f5,7o]1hK\u0006Q!/Z:pYZ,wJ\\3\u0015\u0003y#\"aX3\u0011\u0007\u0001\u001cg'D\u0001b\u0015\t\u0011\u0007%\u0001\u0006d_:\u001cWO\u001d:f]RL!\u0001Z1\u0003\r\u0019+H/\u001e:f\u0011\u00151g\u0001q\u0001h\u0003\u001d!\u0018.\\3pkR\u0004\"\u0001[6\u000e\u0003%T!A\u001b\u000e\u0002\tU$\u0018\u000e\\\u0005\u0003Y&\u0014q\u0001V5nK>,H\u000f\u0006\u0002`]\")am\u0002a\u0001_B\u0011\u0001o]\u0007\u0002c*\u0011!/Y\u0001\tIV\u0014\u0018\r^5p]&\u0011A/\u001d\u0002\u000f\r&t\u0017\u000e^3EkJ\fG/[8o)\t1h\u0010E\u0002xyZj\u0011\u0001\u001f\u0006\u0003EfT!A\u001b>\u000b\u0003m\fAA[1wC&\u0011Q\u0010\u001f\u0002\u0010\u0007>l\u0007\u000f\\3uS>t7\u000b^1hK\")a\r\u0003a\u0001\u007fB!\u0011\u0011AA\u0004\u001b\t\t\u0019AC\u0002\u0002\u0006i\fA\u0001^5nK&!\u0011\u0011BA\u0002\u0005!!UO]1uS>t\u0017\u0001\u0003;p'R\u0014\u0018N\\4\u0015\u0005\u0005=\u0001\u0003BA\t\u00033qA!a\u0005\u0002\u0016A\u0011q\u0005I\u0005\u0004\u0003/\u0001\u0013A\u0002)sK\u0012,g-\u0003\u0003\u0002\u001c\u0005u!AB*ue&twMC\u0002\u0002\u0018\u0001\n!\"\u00198dQ>\u0014\b+\u0019;i+\t\t\u0019\u0003E\u00024\u0003KI1!a\n\u0019\u0005%\t5\r^8s!\u0006$\b.\u0001\u0006qCRD7\u000b\u001e:j]\u001e,\"!a\u0004\u0002+Q|7+\u001a:jC2L'0\u0019;j_:4uN]7bi\u00061Q-];bYN$B!a\r\u0002:A\u0019q$!\u000e\n\u0007\u0005]\u0002EA\u0004C_>dW-\u00198\t\r\u0005mR\u00021\u0001N\u0003\ry'M[\u0001\tQ\u0006\u001c\bnQ8eKV\u0011\u0011\u0011\t\t\u0004?\u0005\r\u0013bAA#A\t\u0019\u0011J\u001c;\u0013\u000b\u0005%#'!\u0014\u0007\r\u0005-\u0003\u0001AA$\u00051a$/\u001a4j]\u0016lWM\u001c;?!\r\u0019\u0014qJ\u0005\u0004\u0003#B\"aE*dC2\f\u0017i\u0019;peN+G.Z2uS>t\u0007f\u0002\u0001\u0002V\u0005m\u0013Q\f\t\u0004?\u0005]\u0013bAA-A\t\u00012+\u001a:jC24VM]:j_:,\u0016\nR\u0001\u0006m\u0006dW/\u001a\u0010\u0002\u0003\u0005q\u0011i\u0019;peN+G.Z2uS>t\u0007CA\u001a\u0011'\u0011\u0001b$!\u001a\u0011\t\u0005\u001d\u0014QN\u0007\u0003\u0003SR1!a\u001b{\u0003\tIw.C\u0002/\u0003S\"\"!!\u0019\u0002\u000fQ|7kY1mCR!\u0011QJA;\u0011\u0019\t9H\u0005a\u0001e\u0005\u00191/\u001a7\u0002\u000b\u0005\u0004\b\u000f\\=\u0015\u000bI\ni(!!\t\r\u0005}4\u00031\u00017\u0003%\tgn\u00195peJ+g\r\u0003\u0004:'\u0001\u0007\u0011q\u0002\u000b\u0006e\u0005\u0015\u0015q\u0011\u0005\u0007\u0003\u007f\"\u0002\u0019\u0001\u001c\t\u000f\u0005%E\u00031\u0001\u0002\f\u0006AQ\r\\3nK:$8\u000fE\u0003&\u0003\u001b\u000by!C\u0002\u0002\u0010>\u0012\u0001\"\u0013;fe\u0006\u0014G.Z\u0001\u0011I\u0016d\u0017N^3s'\u0016dWm\u0019;j_:$r\u0001SAK\u0003;\u000by\n\u0003\u00045+\u0001\u0007\u0011q\u0013\t\u0004g\u0005e\u0015bAAN1\t\u0001\u0012J\u001c;fe:\fG.Q2u_J\u0014VM\u001a\u0005\u0006#V\u0001\rA\u000e\u0005\b\u0003o*\u0002\u0019AAQ!\r\u0019\u00141U\u0005\u0004\u0003KC\"!F!di>\u00148+\u001a7fGRLwN\\'fgN\fw-Z\u0001\roJLG/\u001a*fa2\f7-\u001a\u000b\u0003\u0003W\u0003B!!,\u000246\u0011\u0011q\u0016\u0006\u0004\u0003cS\u0018\u0001\u00027b]\u001eLA!!.\u00020\n1qJ\u00196fGR\u0004")
public abstract class ActorSelection
implements Serializable {
    private static final long serialVersionUID = 1L;
    private int hashCode;
    private volatile boolean bitmap$0;

    public static ActorSelection apply(ActorRef anchorRef, Iterable<String> elements) {
        return ActorSelection$.MODULE$.apply(anchorRef, elements);
    }

    public static ActorSelection apply(ActorRef anchorRef, String path) {
        return ActorSelection$.MODULE$.apply(anchorRef, path);
    }

    public static ScalaActorSelection toScala(ActorSelection sel) {
        return ActorSelection$.MODULE$.toScala(sel);
    }

    public abstract ActorRef anchor();

    public abstract IndexedSeq<SelectionPathElement> path();

    public void tell(Object msg, ActorRef sender) {
        ActorSelection$.MODULE$.deliverSelection((InternalActorRef)this.anchor(), sender, new ActorSelectionMessage(msg, this.path(), false));
    }

    public void forward(Object message, ActorContext context) {
        this.tell(message, context.sender());
    }

    public Future<ActorRef> resolveOne(Timeout timeout2) {
        ExecutionContext ec = ExecutionContexts$.MODULE$.parasitic();
        Promise p = Promise$.MODULE$.apply();
        ActorSelection qual$1 = package$.MODULE$.ask(this);
        Identify x$1 = new Identify(None$.MODULE$);
        Timeout x$2 = timeout2;
        ActorRef x$3 = AskableActorSelection$.MODULE$.ask$default$3$extension(qual$1, x$1);
        AskableActorSelection$.MODULE$.ask$extension(qual$1, x$1, x$2, x$3).onComplete((Function1<Try, Promise> & Serializable)x0$1 -> {
            ActorIdentity actorIdentity;
            Option<ActorRef> option;
            Success success;
            Object t2;
            Try try_ = x0$1;
            if (try_ instanceof Success && (t2 = (success = (Success)try_).value()) instanceof ActorIdentity && (option = (actorIdentity = (ActorIdentity)t2).ref()) instanceof Some) {
                Some some = (Some)option;
                ActorRef ref = (ActorRef)some.value();
                return p.success(ref);
            }
            return p.failure(new ActorNotFound(this));
        }, ec);
        return p.future();
    }

    public Future<ActorRef> resolveOne(FiniteDuration timeout2) {
        return this.resolveOne(Timeout$.MODULE$.durationToTimeout(timeout2));
    }

    public CompletionStage<ActorRef> resolveOne(Duration timeout2) {
        return FutureConverters$.MODULE$.toJava(this.resolveOne(JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(timeout2))));
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ActorSelection[Anchor(").append(this.anchor().path());
        Serializable serializable = this.anchor().path().uid() != 0 ? builder.append("#").append(this.anchor().path().uid()) : BoxedUnit.UNIT;
        builder.append("), Path(").append(this.path().mkString("/", "/", "")).append(")]");
        return builder.toString();
    }

    public ActorPath anchorPath() {
        return this.anchor().path();
    }

    public String pathString() {
        return this.path().mkString("/", "/", "");
    }

    public String toSerializationFormat() {
        String string;
        ActorRef actorRef2 = this.anchor();
        if (actorRef2 instanceof ActorRefWithCell) {
            ActorRefWithCell actorRefWithCell = (ActorRefWithCell)actorRef2;
            string = this.anchor().path().toStringWithAddress(actorRefWithCell.provider().getDefaultAddress());
        } else {
            string = this.anchor().path().toString();
        }
        String anchorPath = string;
        StringBuilder builder = new StringBuilder();
        builder.append(anchorPath);
        char lastChar = builder.charAt(builder.length() - 1);
        Serializable serializable = this.path().nonEmpty() && lastChar != '/' ? builder.append(this.path().mkString("/", "/", "")) : (this.path().nonEmpty() ? builder.append(this.path().mkString("/")) : BoxedUnit.UNIT);
        return builder.toString();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean equals(Object obj) {
        Object object = obj;
        if (!(object instanceof ActorSelection)) return false;
        ActorSelection actorSelection = (ActorSelection)object;
        ActorRef actorRef2 = this.anchor();
        ActorRef actorRef3 = actorSelection.anchor();
        if (actorRef2 == null) {
            if (actorRef3 != null) {
                return false;
            }
        } else if (!((Object)actorRef2).equals(actorRef3)) return false;
        IndexedSeq<SelectionPathElement> indexedSeq = this.path();
        IndexedSeq<SelectionPathElement> indexedSeq2 = actorSelection.path();
        if (indexedSeq == null) {
            if (indexedSeq2 == null) return true;
            return false;
        } else {
            if (!indexedSeq.equals(indexedSeq2)) return false;
            return true;
        }
    }

    private int hashCode$lzycompute() {
        ActorSelection actorSelection = this;
        synchronized (actorSelection) {
            if (!this.bitmap$0) {
                int h2 = MurmurHash$.MODULE$.startHash(Statics.anyHash(this.anchor()));
                h2 = MurmurHash$.MODULE$.extendHash(h2, Statics.anyHash(this.path()), MurmurHash$.MODULE$.startMagicA(), MurmurHash$.MODULE$.startMagicB());
                this.hashCode = MurmurHash$.MODULE$.finalizeHash(h2);
                this.bitmap$0 = true;
            }
        }
        return this.hashCode;
    }

    public int hashCode() {
        if (!this.bitmap$0) {
            return this.hashCode$lzycompute();
        }
        return this.hashCode;
    }
}

