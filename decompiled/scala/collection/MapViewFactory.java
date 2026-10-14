/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import scala.$less$colon$less$;
import scala.Tuple2;
import scala.collection.MapFactory;
import scala.collection.MapOps;
import scala.collection.MapView;
import scala.collection.immutable.Seq;
import scala.collection.mutable.Builder;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005}4qAB\u0004\u0011\u0002\u0007\u0005A\u0002C\u0003:\u0001\u0011\u0005!\bC\u0003?\u0001\u0019\u0005q\bC\u0003P\u0001\u0019\u0005\u0001\u000bC\u0003X\u0001\u0019\u0005\u0001\fC\u0003r\u0001\u0011\u0005#O\u0001\bNCB4\u0016.Z<GC\u000e$xN]=\u000b\u0005!I\u0011AC2pY2,7\r^5p]*\t!\"A\u0003tG\u0006d\u0017m\u0001\u0001\u0014\u0007\u0001i\u0011\u0003\u0005\u0002\u000f\u001f5\t\u0011\"\u0003\u0002\u0011\u0013\t1\u0011I\\=SK\u001a\u00042AE\n\u0016\u001b\u00059\u0011B\u0001\u000b\b\u0005)i\u0015\r\u001d$bGR|'/_\u000b\u0004-yA\u0004c\u0001\n\u00183%\u0011\u0001d\u0002\u0002\u0005-&,w\u000f\u0005\u0003\u000f5q9\u0014BA\u000e\n\u0005\u0019!V\u000f\u001d7feA\u0011QD\b\u0007\u0001\t\u0015y\u0002E1\u0001-\u0005\u0005A\u0006\u0002B\u0011#\u0001Y\n\u0011\u0002\u00107pG\u0006d\u0007\u0005\u001c \u0006\t\r\"\u0003a\n\u0002\u0002Y\u001a!Q\u0005\u0001\u0001'\u00051a$/\u001a4j]\u0016lWM\u001c;?%\t!S\"F\u0002)WQ\u00022AE\f*!\u0011q!DK\u001a\u0011\u0005uYC!B\u0010#\u0005\u0004a\u0013CA\u00171!\tqa&\u0003\u00020\u0013\t9aj\u001c;iS:<\u0007C\u0001\b2\u0013\t\u0011\u0014BA\u0002B]f\u0004\"!\b\u001b\u0005\u000bU\u0012#\u0019\u0001\u0017\u0003\u0003e[\u0001\u0001\u0005\u0002\u001eq\u0011)Q\u0007\tb\u0001Y\u00051A%\u001b8ji\u0012\"\u0012a\u000f\t\u0003\u001dqJ!!P\u0005\u0003\tUs\u0017\u000e^\u0001\u000b]\u0016<()^5mI\u0016\u0014Xc\u0001!J\u0017V\t\u0011\t\u0005\u0003C\u000b\u001ecU\"A\"\u000b\u0005\u0011;\u0011aB7vi\u0006\u0014G.Z\u0005\u0003\r\u000e\u0013qAQ;jY\u0012,'\u000f\u0005\u0003\u000f5!S\u0005CA\u000fJ\t\u0015y\"A1\u0001-!\ti2\nB\u00036\u0005\t\u0007A\u0006\u0005\u0003\u0013\u001b\"S\u0015B\u0001(\b\u0005\u001di\u0015\r\u001d,jK^\fQ!Z7qif,2!\u0015+W+\u0005\u0011\u0006\u0003\u0002\nN'V\u0003\"!\b+\u0005\u000b}\u0019!\u0019\u0001\u0017\u0011\u0005u1F!B\u001b\u0004\u0005\u0004a\u0013\u0001\u00024s_6,2!\u0017/`)\tQ\u0016\r\u0005\u0003\u0013\u001bns\u0006CA\u000f]\t\u0015iFA1\u0001-\u0005\u0005Y\u0005CA\u000f`\t\u0015\u0001GA1\u0001-\u0005\u00051\u0006\"\u00022\u0005\u0001\u0004\u0019\u0017AA5u!\u0011!gn\u00170\u000f\u0005\u0015dgB\u00014l\u001d\t9'.D\u0001i\u0015\tI7\"\u0001\u0004=e>|GOP\u0005\u0002\u0015%\u0011\u0001\"C\u0005\u0003[\u001e\tq!T1q-&,w/\u0003\u0002pa\nQ1k\\7f\u001b\u0006\u0004x\n]:\u000b\u00055<\u0011!B1qa2LXcA:wqR\u0011A/\u001f\t\u0005%5+x\u000f\u0005\u0002\u001em\u0012)Q,\u0002b\u0001YA\u0011Q\u0004\u001f\u0003\u0006A\u0016\u0011\r\u0001\f\u0005\u0006u\u0016\u0001\ra_\u0001\u0006K2,Wn\u001d\t\u0004\u001dqt\u0018BA?\n\u0005)a$/\u001a9fCR,GM\u0010\t\u0005\u001di)x\u000f")
public interface MapViewFactory
extends MapFactory<?> {
    @Override
    public <X, Y> Builder<Tuple2<X, Y>, MapView<X, Y>> newBuilder();

    @Override
    public <X, Y> MapView<X, Y> empty();

    public <K, V> MapView<K, V> from(MapOps<K, V, ?, Object> var1);

    public static /* synthetic */ MapView apply$(MapViewFactory $this, Seq elems) {
        return $this.apply(elems);
    }

    @Override
    default public <K, V> MapView<K, V> apply(Seq<Tuple2<K, V>> elems) {
        return this.from(elems.toMap($less$colon$less$.MODULE$.refl()));
    }

    public static void $init$(MapViewFactory $this) {
    }
}

