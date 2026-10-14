/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.ActorRef;
import akka.actor.ActorRefFactory;
import akka.actor.ActorSystem;
import akka.actor.ClassicActorContextProvider;
import akka.actor.Props;
import java.io.NotSerializableException;
import java.io.ObjectOutputStream;
import scala.Option;
import scala.PartialFunction;
import scala.collection.immutable.Iterable;
import scala.concurrent.ExecutionContextExecutor;
import scala.concurrent.duration.Duration;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;

@ScalaSignature(bytes="\u0006\u0005\u0005\u001dcaB\n\u0015!\u0003\r\t!\u0007\u0005\u0006O\u0001!\t\u0001\u000b\u0005\u0006Y\u00011\t!\f\u0005\u0006c\u00011\tA\r\u0005\u0006m\u00011\ta\u000e\u0005\u0006\u0001\u00021\t!\u0011\u0005\u0006\t\u0002!\t!\u0012\u0005\u0006\t\u00021\ta\u0014\u0005\u0006-\u00021\t\u0001\u000b\u0005\u0006/\u00021\t\u0001\u0017\u0005\u00063\u00021\tA\u0017\u0005\u0006G\u00021\t\u0001\u001a\u0005\u0006k\u00021\u0019A\u001e\u0005\u0006w\u00021\u0019\u0001 \u0005\u0007\u0003\u0003\u0001a\u0011A\u0017\t\u000f\u0005\r\u0001A\"\u0001\u0002\u0006!9\u00111\u0002\u0001\u0007\u0002\u00055\u0001bBA\u000e\u0001\u0019\u0005\u0011Q\u0004\u0005\b\u0003C\u0001AQCA\u0012\u00051\t5\r^8s\u0007>tG/\u001a=u\u0015\t)b#A\u0003bGR|'OC\u0001\u0018\u0003\u0011\t7n[1\u0004\u0001M!\u0001A\u0007\u0011%!\tYb$D\u0001\u001d\u0015\u0005i\u0012!B:dC2\f\u0017BA\u0010\u001d\u0005\u0019\te.\u001f*fMB\u0011\u0011EI\u0007\u0002)%\u00111\u0005\u0006\u0002\u0010\u0003\u000e$xN\u001d*fM\u001a\u000b7\r^8ssB\u0011\u0011%J\u0005\u0003MQ\u00111d\u00117bgNL7-Q2u_J\u001cuN\u001c;fqR\u0004&o\u001c<jI\u0016\u0014\u0018A\u0002\u0013j]&$H\u0005F\u0001*!\tY\"&\u0003\u0002,9\t!QK\\5u\u0003\u0011\u0019X\r\u001c4\u0016\u00039\u0002\"!I\u0018\n\u0005A\"\"\u0001C!di>\u0014(+\u001a4\u0002\u000bA\u0014x\u000e]:\u0016\u0003M\u0002\"!\t\u001b\n\u0005U\"\"!\u0002)s_B\u001c\u0018A\u0004:fG\u0016Lg/\u001a+j[\u0016|W\u000f^\u000b\u0002qA\u0011\u0011HP\u0007\u0002u)\u00111\bP\u0001\tIV\u0014\u0018\r^5p]*\u0011Q\bH\u0001\u000bG>t7-\u001e:sK:$\u0018BA ;\u0005!!UO]1uS>t\u0017!E:fiJ+7-Z5wKRKW.Z8viR\u0011\u0011F\u0011\u0005\u0006\u0007\u0016\u0001\r\u0001O\u0001\bi&lWm\\;u\u0003\u0019\u0011WmY8nKR\u0011\u0011F\u0012\u0005\u0006\u000f\u001a\u0001\r\u0001S\u0001\tE\u0016D\u0017M^5peB\u0011\u0011\n\u0014\b\u0003C)K!a\u0013\u000b\u0002\u000b\u0005\u001bGo\u001c:\n\u00055s%a\u0002*fG\u0016Lg/\u001a\u0006\u0003\u0017R!2!\u000b)R\u0011\u00159u\u00011\u0001I\u0011\u0015\u0011v\u00011\u0001T\u0003)!\u0017n]2be\u0012|E\u000e\u001a\t\u00037QK!!\u0016\u000f\u0003\u000f\t{w\u000e\\3b]\u0006AQO\u001c2fG>lW-\u0001\u0004tK:$WM\u001d\u000b\u0002]\u0005A1\r[5mIJ,g.F\u0001\\!\ra\u0016ML\u0007\u0002;*\u0011alX\u0001\nS6lW\u000f^1cY\u0016T!\u0001\u0019\u000f\u0002\u0015\r|G\u000e\\3di&|g.\u0003\u0002c;\nA\u0011\n^3sC\ndW-A\u0003dQ&dG\r\u0006\u0002fQB\u00191D\u001a\u0018\n\u0005\u001dd\"AB(qi&|g\u000eC\u0003j\u0017\u0001\u0007!.\u0001\u0003oC6,\u0007CA6s\u001d\ta\u0007\u000f\u0005\u0002n95\taN\u0003\u0002p1\u00051AH]8pizJ!!\u001d\u000f\u0002\rA\u0013X\rZ3g\u0013\t\u0019HO\u0001\u0004TiJLgn\u001a\u0006\u0003cr\t!\u0002Z5ta\u0006$8\r[3s+\u00059\bC\u0001=z\u001b\u0005a\u0014B\u0001>=\u0005a)\u00050Z2vi&|gnQ8oi\u0016DH/\u0012=fGV$xN]\u0001\u0007gf\u001cH/Z7\u0016\u0003u\u0004\"!\t@\n\u0005}$\"aC!di>\u00148+_:uK6\fa\u0001]1sK:$\u0018!B<bi\u000eDGc\u0001\u0018\u0002\b!1\u0011\u0011B\bA\u00029\nqa];cU\u0016\u001cG/A\u0005xCR\u001c\u0007nV5uQR)a&a\u0004\u0002\u0012!1\u0011\u0011\u0002\tA\u00029Bq!a\u0005\u0011\u0001\u0004\t)\"A\u0002ng\u001e\u00042aGA\f\u0013\r\tI\u0002\b\u0002\u0004\u0003:L\u0018aB;oo\u0006$8\r\u001b\u000b\u0004]\u0005}\u0001BBA\u0005#\u0001\u0007a&A\u0006xe&$Xm\u00142kK\u000e$HcA\u0015\u0002&!9\u0011q\u0005\nA\u0002\u0005%\u0012!A8\u0011\t\u0005-\u0012QG\u0007\u0003\u0003[QA!a\f\u00022\u0005\u0011\u0011n\u001c\u0006\u0003\u0003g\tAA[1wC&!\u0011qGA\u0017\u0005Iy%M[3di>+H\u000f];u'R\u0014X-Y7)\t\u0005\u0015\u00121\b\t\u0005\u0003{\t\u0019%\u0004\u0002\u0002@)\u0019\u0011\u0011\t\f\u0002\tU$\u0018\u000e\\\u0005\u0005\u0003\u000b\nyD\u0001\u0004v]V\u001cX\r\u001a")
public interface ActorContext
extends ActorRefFactory,
ClassicActorContextProvider {
    public ActorRef self();

    public Props props();

    public Duration receiveTimeout();

    public void setReceiveTimeout(Duration var1);

    public static /* synthetic */ void become$(ActorContext $this, PartialFunction behavior) {
        $this.become(behavior);
    }

    default public void become(PartialFunction<Object, BoxedUnit> behavior) {
        this.become(behavior, true);
    }

    public void become(PartialFunction<Object, BoxedUnit> var1, boolean var2);

    public void unbecome();

    public ActorRef sender();

    public Iterable<ActorRef> children();

    public Option<ActorRef> child(String var1);

    @Override
    public ExecutionContextExecutor dispatcher();

    public ActorSystem system();

    public ActorRef parent();

    public ActorRef watch(ActorRef var1);

    public ActorRef watchWith(ActorRef var1, Object var2);

    public ActorRef unwatch(ActorRef var1);

    public static /* synthetic */ void writeObject$(ActorContext $this, ObjectOutputStream o) {
        $this.writeObject(o);
    }

    default public void writeObject(ObjectOutputStream o) {
        throw new NotSerializableException("ActorContext is not serializable!");
    }

    public static void $init$(ActorContext $this) {
    }
}

