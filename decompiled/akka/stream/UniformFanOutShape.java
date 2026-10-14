/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.FanOutShape;
import akka.stream.Inlet;
import akka.stream.Outlet;
import akka.stream.UniformFanOutShape$;
import java.io.Serializable;
import scala.Function1;
import scala.Predef$;
import scala.collection.immutable.Seq;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.RichInt$;

@ScalaSignature(bytes="\u0006\u0005\u0005\u0005r!B\b\u0011\u0011\u0003)b!B\f\u0011\u0011\u0003A\u0002\"B\u0010\u0002\t\u0003\u0001\u0003\"B\u0011\u0002\t\u0003\u0011c\u0001B\f\u0011\u0001\u0015B\u0001\"\u000e\u0003\u0003\u0002\u0003\u0006IA\u000e\u0005\ts\u0011\u0011\t\u0011)A\u0005u!)q\u0004\u0002C\u0001\u0017\")q\u0004\u0002C\u0001%\")q\u0004\u0002C\u0001)\")q\u0004\u0002C\u0001G\")\u0011\u000f\u0002C)e\")Q\u000f\u0002C!m\")!\u000e\u0002C#o\"9\u0011Q\u0001\u0003\u0005\u0002\u0005\u001d\u0011AE+oS\u001a|'/\u001c$b]>+Ho\u00155ba\u0016T!!\u0005\n\u0002\rM$(/Z1n\u0015\u0005\u0019\u0012\u0001B1lW\u0006\u001c\u0001\u0001\u0005\u0002\u0017\u00035\t\u0001C\u0001\nV]&4wN]7GC:|U\u000f^*iCB,7CA\u0001\u001a!\tQR$D\u0001\u001c\u0015\u0005a\u0012!B:dC2\f\u0017B\u0001\u0010\u001c\u0005\u0019\te.\u001f*fM\u00061A(\u001b8jiz\"\u0012!F\u0001\u0006CB\u0004H._\u000b\u0006G\u00055\u0011\u0011\u0003\u000b\u0006I\u0005M\u0011q\u0003\t\u0007-\u0011\tY!a\u0004\u0016\u0007\u0019bcj\u0005\u0002\u0005OA\u0019a\u0003\u000b\u0016\n\u0005%\u0002\"a\u0003$b]>+Ho\u00155ba\u0016\u0004\"a\u000b\u0017\r\u0001\u00111Q\u0006\u0002EC\u00029\u0012\u0011!S\t\u0003_I\u0002\"A\u0007\u0019\n\u0005EZ\"a\u0002(pi\"Lgn\u001a\t\u00035MJ!\u0001N\u000e\u0003\u0007\u0005s\u00170A\u0001o!\tQr'\u0003\u000297\t\u0019\u0011J\u001c;\u0002\u000b}Kg.\u001b;\u0011\u0007mr\u0014I\u0004\u0002\u0017y%\u0011Q\bE\u0001\f\r\u0006tw*\u001e;TQ\u0006\u0004X-\u0003\u0002@\u0001\n!\u0011J\\5u\u0015\ti\u0004C\u000b\u0002+\u0005.\n1\t\u0005\u0002E\u00136\tQI\u0003\u0002G\u000f\u0006IQO\\2iK\u000e\\W\r\u001a\u0006\u0003\u0011n\t!\"\u00198o_R\fG/[8o\u0013\tQUIA\tv]\u000eDWmY6fIZ\u000b'/[1oG\u0016$2\u0001\u0014)R!\u00111BAK'\u0011\u0005-rEAB(\u0005\t\u000b\u0007aFA\u0001P\u0011\u0015)t\u00011\u00017\u0011\u0015It\u00011\u0001;)\ta5\u000bC\u00036\u0011\u0001\u0007a\u0007F\u0002M+ZCQ!N\u0005A\u0002YBQaV\u0005A\u0002a\u000bAA\\1nKB\u0011\u0011\f\u0019\b\u00035z\u0003\"aW\u000e\u000e\u0003qS!!\u0018\u000b\u0002\rq\u0012xn\u001c;?\u0013\ty6$\u0001\u0004Qe\u0016$WMZ\u0005\u0003C\n\u0014aa\u0015;sS:<'BA0\u001c)\raE-\u001b\u0005\u0006K*\u0001\rAZ\u0001\u0006S:dW\r\u001e\t\u0004-\u001dT\u0013B\u00015\u0011\u0005\u0015Ie\u000e\\3u\u0011\u0015Q'\u00021\u0001l\u0003\u001dyW\u000f\u001e7fiN\u00042A\u00077o\u0013\ti7DA\u0003BeJ\f\u0017\u0010E\u0002\u0017_6K!\u0001\u001d\t\u0003\r=+H\u000f\\3u\u0003%\u0019wN\\:ueV\u001cG\u000f\u0006\u0002(g\")Ao\u0003a\u0001u\u0005!\u0011N\\5u\u0003!!W-\u001a9D_BLH#\u0001'\u0016\u0003a\u0004B!\u001f@\u0002\u00025\t!P\u0003\u0002|y\u0006I\u0011.\\7vi\u0006\u0014G.\u001a\u0006\u0003{n\t!bY8mY\u0016\u001cG/[8o\u0013\ty(PA\u0002TKF\u0004BAF8\u0002\u0004)\u0012QJQ\u0001\u0004_V$H\u0003BA\u0001\u0003\u0013AQ!\u000e\bA\u0002Y\u00022aKA\u0007\t\u0015i3A1\u0001/!\rY\u0013\u0011\u0003\u0003\u0006\u001f\u000e\u0011\rA\f\u0005\u0007K\u000e\u0001\r!!\u0006\u0011\tY9\u00171\u0002\u0005\u0007U\u000e\u0001\r!!\u0007\u0011\u000bi\tY\"a\b\n\u0007\u0005u1D\u0001\u0006=e\u0016\u0004X-\u0019;fIz\u0002BAF8\u0002\u0010\u0001")
public class UniformFanOutShape<I, O>
extends FanOutShape<I> {
    private final int n;

    public static <I, O> UniformFanOutShape<I, O> apply(Inlet<I> inlet, Seq<Outlet<O>> outlets) {
        return UniformFanOutShape$.MODULE$.apply(inlet, outlets);
    }

    @Override
    public FanOutShape<I> construct(FanOutShape.Init<I> init2) {
        return new UniformFanOutShape<I, O>(this.n, init2);
    }

    @Override
    public UniformFanOutShape<I, O> deepCopy() {
        return (UniformFanOutShape)super.deepCopy();
    }

    @Override
    public final Seq<Outlet<O>> outlets() {
        return super.outlets();
    }

    public Outlet<O> out(int n) {
        return (Outlet)this.outlets().apply(n);
    }

    public UniformFanOutShape(int n, FanOutShape.Init<I> _init) {
        this.n = n;
        super(_init);
        RichInt$.MODULE$.until$extension(Predef$.MODULE$.intWrapper(0), n).foreach((Function1<Object, Outlet> & Serializable)i -> this.newOutlet(new StringBuilder(3).append("out").append(BoxesRunTime.unboxToInt(i)).toString()));
    }

    public UniformFanOutShape(int n) {
        this(n, new FanOutShape.Name("UniformFanOut"));
    }

    public UniformFanOutShape(int n, String name) {
        this(n, new FanOutShape.Name(name));
    }

    public UniformFanOutShape(Inlet<I> inlet, Outlet<O>[] outlets) {
        this(outlets.length, new FanOutShape.Ports<I>(inlet, Predef$.MODULE$.wrapRefArray((Object[])outlets).toList()));
    }
}

