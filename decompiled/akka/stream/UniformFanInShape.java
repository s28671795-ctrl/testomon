/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.FanInShape;
import akka.stream.Inlet;
import akka.stream.Outlet;
import akka.stream.UniformFanInShape$;
import java.io.Serializable;
import java.util.List;
import scala.Function1;
import scala.Predef$;
import scala.collection.immutable.Seq;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.RichInt$;

@ScalaSignature(bytes="\u0006\u0005\u0005Us!B\t\u0013\u0011\u00039b!B\r\u0013\u0011\u0003Q\u0002\"B\u0011\u0002\t\u0003\u0011\u0003\"B\u0012\u0002\t\u0003!\u0003bBA\u0017\u0003\u0011\u0005\u0011q\u0006\u0004\u00053I\u0001q\u0005\u0003\u00058\u000b\t\u0015\r\u0011\"\u00019\u0011!aTA!A!\u0002\u0013I\u0004\u0002C\u001f\u0006\u0005\u0003\u0005\u000b\u0011\u0002 \t\u000b\u0005*A\u0011A#\t\u000b\u0005*A\u0011\u0001'\t\u000b\u0005*A\u0011\u0001(\t\u000b\u0005*A\u0011A/\t\u000b-,A\u0011\u000b7\t\u000bi,A\u0011I>\t\u000b\u0011,AQ\t?\t\u000f\u0005=Q\u0001\"\u0001\u0002\u0012\u0005\tRK\\5g_Jlg)\u00198J]NC\u0017\r]3\u000b\u0005M!\u0012AB:ue\u0016\fWNC\u0001\u0016\u0003\u0011\t7n[1\u0004\u0001A\u0011\u0001$A\u0007\u0002%\t\tRK\\5g_Jlg)\u00198J]NC\u0017\r]3\u0014\u0005\u0005Y\u0002C\u0001\u000f \u001b\u0005i\"\"\u0001\u0010\u0002\u000bM\u001c\u0017\r\\1\n\u0005\u0001j\"AB!osJ+g-\u0001\u0004=S:LGO\u0010\u000b\u0002/\u0005)\u0011\r\u001d9msV)Q%a\u0006\u0002\u001eQ)a%a\b\u0002$A1\u0001$BA\u000b\u00037)2\u0001\u000b%/'\t)\u0011\u0006E\u0002\u0019U1J!a\u000b\n\u0003\u0015\u0019\u000bg.\u00138TQ\u0006\u0004X\r\u0005\u0002.]1\u0001AAB\u0018\u0006\t\u000b\u0007\u0001GA\u0001P#\t\tD\u0007\u0005\u0002\u001de%\u00111'\b\u0002\b\u001d>$\b.\u001b8h!\taR'\u0003\u00027;\t\u0019\u0011I\\=\u0002\u00039,\u0012!\u000f\t\u00039iJ!aO\u000f\u0003\u0007%sG/\u0001\u0002oA\u0005)q,\u001b8jiB\u0019qH\u0011\u0017\u000f\u0005a\u0001\u0015BA!\u0013\u0003)1\u0015M\\%o'\"\f\u0007/Z\u0005\u0003\u0007\u0012\u0013A!\u00138ji*\u0011\u0011I\u0005\u000b\u0004\r*[\u0005\u0003\u0002\r\u0006\u000f2\u0002\"!\f%\u0005\r%+\u0001R1\u00011\u0005\u0005!\u0006\"B\u001c\n\u0001\u0004I\u0004\"B\u001f\n\u0001\u0004qDC\u0001$N\u0011\u00159$\u00021\u0001:)\r1u\n\u0015\u0005\u0006o-\u0001\r!\u000f\u0005\u0006#.\u0001\rAU\u0001\u0005]\u0006lW\r\u0005\u0002T5:\u0011A\u000b\u0017\t\u0003+vi\u0011A\u0016\u0006\u0003/Z\ta\u0001\u0010:p_Rt\u0014BA-\u001e\u0003\u0019\u0001&/\u001a3fM&\u00111\f\u0018\u0002\u0007'R\u0014\u0018N\\4\u000b\u0005ekBc\u0001$_G\")q\f\u0004a\u0001A\u00061q.\u001e;mKR\u00042\u0001G1-\u0013\t\u0011'C\u0001\u0004PkRdW\r\u001e\u0005\u0006I2\u0001\r!Z\u0001\u0007S:dW\r^:\u0011\u0007q1\u0007.\u0003\u0002h;\t)\u0011I\u001d:bsB\u0019\u0001$[$\n\u0005)\u0014\"!B%oY\u0016$\u0018!C2p]N$(/^2u)\tIS\u000eC\u0003o\u001b\u0001\u0007q.\u0001\u0003j]&$\bcA Ca*\u0012A&]\u0016\u0002eB\u00111\u000f_\u0007\u0002i*\u0011QO^\u0001\nk:\u001c\u0007.Z2lK\u0012T!a^\u000f\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0002zi\n\tRO\\2iK\u000e\\W\r\u001a,be&\fgnY3\u0002\u0011\u0011,W\r]\"paf$\u0012AR\u000b\u0002{B)a0a\u0002\u0002\f5\tqP\u0003\u0003\u0002\u0002\u0005\r\u0011!C5n[V$\u0018M\u00197f\u0015\r\t)!H\u0001\u000bG>dG.Z2uS>t\u0017bAA\u0005\u007f\n\u00191+Z9\u0011\taI\u0017Q\u0002\u0016\u0003\u000fF\f!!\u001b8\u0015\t\u0005-\u00111\u0003\u0005\u0006oA\u0001\r!\u000f\t\u0004[\u0005]AABA\r\u0007\t\u0007\u0001GA\u0001J!\ri\u0013Q\u0004\u0003\u0006_\r\u0011\r\u0001\r\u0005\u0007?\u000e\u0001\r!!\t\u0011\ta\t\u00171\u0004\u0005\u0007I\u000e\u0001\r!!\n\u0011\u000bq\t9#a\u000b\n\u0007\u0005%RD\u0001\u0006=e\u0016\u0004X-\u0019;fIz\u0002B\u0001G5\u0002\u0016\u000511M]3bi\u0016,b!!\r\u00028\u0005mBCBA\u001a\u0003{\t\t\u0005\u0005\u0004\u0019\u000b\u0005U\u0012\u0011\b\t\u0004[\u0005]BABA\r\t\t\u0007\u0001\u0007E\u0002.\u0003w!Qa\f\u0003C\u0002ABaa\u0018\u0003A\u0002\u0005}\u0002\u0003\u0002\rb\u0003sAa\u0001\u001a\u0003A\u0002\u0005\r\u0003CBA#\u0003\u001f\n\u0019&\u0004\u0002\u0002H)!\u0011\u0011JA&\u0003\u0011)H/\u001b7\u000b\u0005\u00055\u0013\u0001\u00026bm\u0006LA!!\u0015\u0002H\t!A*[:u!\u0011A\u0012.!\u000e")
public class UniformFanInShape<T, O>
extends FanInShape<O> {
    private final int n;

    public static <I, O> UniformFanInShape<I, O> create(Outlet<O> outlet, List<Inlet<I>> inlets) {
        return UniformFanInShape$.MODULE$.create(outlet, inlets);
    }

    public static <I, O> UniformFanInShape<I, O> apply(Outlet<O> outlet, Seq<Inlet<I>> inlets) {
        return UniformFanInShape$.MODULE$.apply(outlet, inlets);
    }

    public int n() {
        return this.n;
    }

    @Override
    public FanInShape<O> construct(FanInShape.Init<O> init2) {
        return new UniformFanInShape<T, O>(this.n(), init2);
    }

    @Override
    public UniformFanInShape<T, O> deepCopy() {
        return (UniformFanInShape)super.deepCopy();
    }

    @Override
    public final Seq<Inlet<T>> inlets() {
        return super.inlets();
    }

    public Inlet<T> in(int n) {
        return (Inlet)this.inlets().apply(n);
    }

    public UniformFanInShape(int n, FanInShape.Init<O> _init) {
        this.n = n;
        super(_init);
        RichInt$.MODULE$.until$extension(Predef$.MODULE$.intWrapper(0), n).foreach((Function1<Object, Inlet> & Serializable)i -> this.newInlet(new StringBuilder(2).append("in").append(BoxesRunTime.unboxToInt(i)).toString()));
    }

    public UniformFanInShape(int n) {
        this(n, new FanInShape.Name("UniformFanIn"));
    }

    public UniformFanInShape(int n, String name) {
        this(n, new FanInShape.Name(name));
    }

    public UniformFanInShape(Outlet<O> outlet, Inlet<T>[] inlets) {
        this(inlets.length, new FanInShape.Ports<O>(outlet, Predef$.MODULE$.wrapRefArray((Object[])inlets).toList()));
    }
}

