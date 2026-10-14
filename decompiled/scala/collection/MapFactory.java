/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import java.io.Serializable;
import scala.Tuple2;
import scala.collection.BuildFrom;
import scala.collection.Factory;
import scala.collection.IterableOnce;
import scala.collection.immutable.Seq;
import scala.collection.mutable.Builder;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\tEbaB\r\u001b!\u0003\r\ta\b\u0005\u0006Y\u0001!\t!\f\u0005\u0006c\u00011\tA\r\u0005\u0006\u0015\u00021\ta\u0013\u0005\u00067\u0002!\t\u0001\u0018\u0005\u0006S\u00021\tA\u001b\u0005\u0006q\u0002!\u0019!_\u0004\b\u0003\u0013Q\u0002\u0012AA\u0006\r\u0019I\"\u0004#\u0001\u0002\u000e!9\u0011Q\u0004\u0005\u0005\u0002\u0005}\u0001bBA\u0011\u0011\u0011\r\u00111\u0005\u0004\t\u0003\u0013B\u0001\u0015!\u0003\u0002L!Q\u00111I\u0006\u0003\u0002\u0003\u0006I!!\u001b\t\u000f\u0005u1\u0002\"\u0001\u0002n!9\u0011QO\u0006\u0005\u0002\u0005]\u0004BB5\f\t\u0003\ti\bC\u0004\u0002\u000e\"!\u0019!a$\u0007\r\u0005]\u0006\u0002AA]\u0011)\ty-\u0005B\u0001B\u0003%\u0011Q\u0018\u0005\b\u0003;\tB\u0011AAi\u0011\u0019Y\u0016\u0003\"\u0011\u0002X\"1!*\u0005C\u0001\u0003WDa!M\t\u0005\u0002\u0005}\bBB5\u0012\t\u0003\u0011i\u0001C\u0005\u0003\"!\t\t\u0011\"\u0003\u0003$\tQQ*\u00199GC\u000e$xN]=\u000b\u0005ma\u0012AC2pY2,7\r^5p]*\tQ$A\u0003tG\u0006d\u0017m\u0001\u0001\u0016\u0005\u000124c\u0001\u0001\"KA\u0011!eI\u0007\u00029%\u0011A\u0005\b\u0002\u0007\u0003:L(+\u001a4\u0011\u0005\u0019JcB\u0001\u0012(\u0013\tAC$A\u0004qC\u000e\\\u0017mZ3\n\u0005)Z#\u0001D*fe&\fG.\u001b>bE2,'B\u0001\u0015\u001d\u0003\u0019!\u0013N\\5uIQ\ta\u0006\u0005\u0002#_%\u0011\u0001\u0007\b\u0002\u0005+:LG/A\u0003f[B$\u00180F\u00024\u000b\"+\u0012\u0001\u000e\t\u0005kY\"u\t\u0004\u0001\u0005\r]\u0002AQ1\u00019\u0005\t\u00195)F\u0002:\u0001\n\u000b\"AO\u001f\u0011\u0005\tZ\u0014B\u0001\u001f\u001d\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"A\t \n\u0005}b\"aA!os\u0012)\u0011I\u000eb\u0001s\t!q\f\n\u00137\t\u0015\u0019eG1\u0001:\u0005\u0011yF\u0005J\u001c\u0011\u0005U*E!\u0002$\u0003\u0005\u0004I$!A&\u0011\u0005UBE!B%\u0003\u0005\u0004I$!\u0001,\u0002\t\u0019\u0014x.\\\u000b\u0004\u0019>\u000bFCA'S!\u0011)dG\u0014)\u0011\u0005UzE!\u0002$\u0004\u0005\u0004I\u0004CA\u001bR\t\u0015I5A1\u0001:\u0011\u0015\u00196\u00011\u0001U\u0003\tIG\u000fE\u0002V-bk\u0011AG\u0005\u0003/j\u0011A\"\u0013;fe\u0006\u0014G.Z(oG\u0016\u0004BAI-O!&\u0011!\f\b\u0002\u0007)V\u0004H.\u001a\u001a\u0002\u000b\u0005\u0004\b\u000f\\=\u0016\u0007u\u0003'\r\u0006\u0002_GB!QGN0b!\t)\u0004\rB\u0003G\t\t\u0007\u0011\b\u0005\u00026E\u0012)\u0011\n\u0002b\u0001s!)A\r\u0002a\u0001K\u0006)Q\r\\3ngB\u0019!E\u001a5\n\u0005\u001dd\"A\u0003\u001fsKB,\u0017\r^3e}A!!%W0b\u0003)qWm\u001e\"vS2$WM]\u000b\u0004WR4X#\u00017\u0011\t5\u0004(o^\u0007\u0002]*\u0011qNG\u0001\b[V$\u0018M\u00197f\u0013\t\thNA\u0004Ck&dG-\u001a:\u0011\t\tJ6/\u001e\t\u0003kQ$QAR\u0003C\u0002e\u0002\"!\u000e<\u0005\u000b%+!\u0019A\u001d\u0011\tU24/^\u0001\u000b[\u0006\u0004h)Y2u_JLX#\u0002>\u0002\u0002\u0005\u0015Q#A>\u0011\u000bUch0a\u0002\n\u0005uT\"a\u0002$bGR|'/\u001f\t\u0006Ee{\u00181\u0001\t\u0004k\u0005\u0005A!\u0002$\u0007\u0005\u0004I\u0004cA\u001b\u0002\u0006\u0011)\u0011J\u0002b\u0001sA)QGN@\u0002\u0004\u0005QQ*\u00199GC\u000e$xN]=\u0011\u0005UC1\u0003\u0002\u0005\"\u0003\u001f\u0001B!!\u0005\u0002\u001c5\u0011\u00111\u0003\u0006\u0005\u0003+\t9\"\u0001\u0002j_*\u0011\u0011\u0011D\u0001\u0005U\u00064\u0018-C\u0002+\u0003'\ta\u0001P5oSRtDCAA\u0006\u0003%!xNR1di>\u0014\u00180\u0006\u0005\u0002&\u00055\u0012\u0011GA\u001b)\u0011\t9#!\u0011\u0011\rUc\u0018\u0011FA\u001a!\u0019\u0011\u0013,a\u000b\u00020A\u0019Q'!\f\u0005\u000b\u0019S!\u0019A\u001d\u0011\u0007U\n\t\u0004B\u0003J\u0015\t\u0007\u0011\bE\u00046\u0003k\tY#a\f\u0005\r]R!\u0019AA\u001c+\u0015I\u0014\u0011HA\u001f\t\u001d\tY$!\u000eC\u0002e\u0012Aa\u0018\u0013%q\u00119\u0011qHA\u001b\u0005\u0004I$\u0001B0%IeBq!a\u0011\u000b\u0001\u0004\t)%A\u0004gC\u000e$xN]=\u0011\tU\u0003\u0011q\t\t\u0004k\u0005U\"!\u0003+p\r\u0006\u001cGo\u001c:z+!\ti%!\u0016\u0002Z\u0005u3#B\u0006\"\u0003\u001f*\u0003CB+}\u0003#\nY\u0006\u0005\u0004#3\u0006M\u0013q\u000b\t\u0004k\u0005UC!\u0002$\f\u0005\u0004I\u0004cA\u001b\u0002Z\u0011)\u0011j\u0003b\u0001sA9Q'!\u0018\u0002T\u0005]CAB\u001c\f\u0005\u0004\ty&F\u0003:\u0003C\n)\u0007B\u0004\u0002d\u0005u#\u0019A\u001d\u0003\u000b}#C%\r\u0019\u0005\u000f\u0005\u001d\u0014Q\fb\u0001s\t)q\f\n\u00132cA!Q\u000bAA6!\r)\u0014Q\f\u000b\u0005\u0003_\n\u0019\bE\u0005\u0002r-\t\u0019&a\u0016\u0002l5\t\u0001\u0002C\u0004\u0002D5\u0001\r!!\u001b\u0002\u0019\u0019\u0014x.\\*qK\u000eLg-[2\u0015\t\u0005m\u0013\u0011\u0010\u0005\u0007':\u0001\r!a\u001f\u0011\tU3\u0016\u0011K\u000b\u0003\u0003\u007f\u0002b!\u001c9\u0002R\u0005m\u0003fB\u0006\u0002\u0004\u0006%\u00151\u0012\t\u0004E\u0005\u0015\u0015bAAD9\t\u00012+\u001a:jC24VM]:j_:,\u0016\nR\u0001\u0006m\u0006dW/\u001a\u0010\u0002\u0007\u0005YAo\u001c\"vS2$gI]8n+!\t\t*!(\u0002\"\u0006\u0015F\u0003BAJ\u0003c\u0003\u0002\"VAK{\u0005e\u00151U\u0005\u0004\u0003/S\"!\u0003\"vS2$gI]8n!\u0019\u0011\u0013,a'\u0002 B\u0019Q'!(\u0005\u000b\u0019\u0003\"\u0019A\u001d\u0011\u0007U\n\t\u000bB\u0003J!\t\u0007\u0011\bE\u00046\u0003K\u000bY*a(\u0005\r]\u0002\"\u0019AAT+\u0015I\u0014\u0011VAW\t\u001d\tY+!*C\u0002e\u0012Qa\u0018\u0013%cI\"q!a,\u0002&\n\u0007\u0011HA\u0003`I\u0011\n4\u0007C\u0004\u0002DA\u0001\r!a-\u0011\tU\u0003\u0011Q\u0017\t\u0004k\u0005\u0015&\u0001\u0003#fY\u0016<\u0017\r^3\u0016\t\u0005m\u0016\u0011Y\n\u0005#\u0005\ni\f\u0005\u0003V\u0001\u0005}\u0006cA\u001b\u0002B\u00129\u00111Y\tC\u0002\u0005\u0015'!A\"\u0016\u000be\n9-a3\u0005\u000f\u0005%\u0017\u0011\u0019b\u0001s\t)q\f\n\u00132i\u00119\u0011QZAa\u0005\u0004I$!B0%IE*\u0014\u0001\u00033fY\u0016<\u0017\r^3\u0015\t\u0005M\u0017Q\u001b\t\u0006\u0003c\n\u0012q\u0018\u0005\b\u0003\u001f\u001c\u0002\u0019AA_+\u0019\tI.a8\u0002dR!\u00111\\As!\u001d)\u0014\u0011YAo\u0003C\u00042!NAp\t\u00151EC1\u0001:!\r)\u00141\u001d\u0003\u0006\u0013R\u0011\r!\u000f\u0005\u0007IR\u0001\r!a:\u0011\t\t2\u0017\u0011\u001e\t\u0007Ee\u000bi.!9\u0016\r\u00055\u00181_A|)\u0011\ty/!?\u0011\u000fU\n\t-!=\u0002vB\u0019Q'a=\u0005\u000b\u0019+\"\u0019A\u001d\u0011\u0007U\n9\u0010B\u0003J+\t\u0007\u0011\b\u0003\u0004T+\u0001\u0007\u00111 \t\u0005+Z\u000bi\u0010\u0005\u0004#3\u0006E\u0018Q_\u000b\u0007\u0005\u0003\u00119Aa\u0003\u0016\u0005\t\r\u0001cB\u001b\u0002B\n\u0015!\u0011\u0002\t\u0004k\t\u001dA!\u0002$\u0017\u0005\u0004I\u0004cA\u001b\u0003\f\u0011)\u0011J\u0006b\u0001sU1!q\u0002B\f\u00057)\"A!\u0005\u0011\r5\u0004(1\u0003B\u000f!\u0019\u0011\u0013L!\u0006\u0003\u001aA\u0019QGa\u0006\u0005\u000b\u0019;\"\u0019A\u001d\u0011\u0007U\u0012Y\u0002B\u0003J/\t\u0007\u0011\bE\u00046\u0003\u0003\u0014)B!\u0007)\u000fE\t\u0019)!#\u0002\f\u0006aqO]5uKJ+\u0007\u000f\\1dKR\u0011!Q\u0005\t\u0005\u0005O\u0011i#\u0004\u0002\u0003*)!!1FA\f\u0003\u0011a\u0017M\\4\n\t\t=\"\u0011\u0006\u0002\u0007\u001f\nTWm\u0019;")
public interface MapFactory<CC>
extends Serializable {
    public static <K, V, CC> BuildFrom<Object, Tuple2<K, V>, CC> toBuildFrom(MapFactory<CC> factory2) {
        return new BuildFrom<Object, Tuple2<K, V>, CC>(factory2){
            private final MapFactory factory$2;

            public Builder<Tuple2<K, V>, CC> apply(Object from) {
                return BuildFrom.apply$(this, from);
            }

            public Factory<Tuple2<K, V>, CC> toFactory(Object from) {
                return BuildFrom.toFactory$(this, from);
            }

            public CC fromSpecific(Object from, IterableOnce<Tuple2<K, V>> it) {
                return this.factory$2.from(it);
            }

            public Builder<Tuple2<K, V>, CC> newBuilder(Object from) {
                return this.factory$2.newBuilder();
            }
            {
                this.factory$2 = factory$2;
            }
        };
    }

    public static <K, V, CC> Factory<Tuple2<K, V>, CC> toFactory(MapFactory<CC> factory2) {
        return new ToFactory(factory2);
    }

    public <K, V> CC empty();

    public <K, V> CC from(IterableOnce<Tuple2<K, V>> var1);

    public static /* synthetic */ Object apply$(MapFactory $this, Seq elems) {
        return $this.apply(elems);
    }

    default public <K, V> CC apply(Seq<Tuple2<K, V>> elems) {
        return this.from(elems);
    }

    public <K, V> Builder<Tuple2<K, V>, CC> newBuilder();

    default public <K, V> Factory<Tuple2<K, V>, CC> mapFactory() {
        return new ToFactory(this);
    }

    public static void $init$(MapFactory $this) {
    }

    public static class Delegate<C>
    implements MapFactory<C> {
        private static final long serialVersionUID = 3L;
        private final MapFactory<C> delegate;

        @Override
        public <K, V> Factory<Tuple2<K, V>, C> mapFactory() {
            return ((MapFactory)this).mapFactory();
        }

        @Override
        public <K, V> C apply(Seq<Tuple2<K, V>> elems) {
            return this.delegate.apply(elems);
        }

        @Override
        public <K, V> C from(IterableOnce<Tuple2<K, V>> it) {
            return this.delegate.from(it);
        }

        @Override
        public <K, V> C empty() {
            return this.delegate.empty();
        }

        @Override
        public <K, V> Builder<Tuple2<K, V>, C> newBuilder() {
            return this.delegate.newBuilder();
        }

        public Delegate(MapFactory<C> delegate) {
            this.delegate = delegate;
        }
    }

    private static class ToFactory<K, V, CC>
    implements Factory<Tuple2<K, V>, CC>,
    Serializable {
        private static final long serialVersionUID = 3L;
        private final MapFactory<CC> factory;

        @Override
        public CC fromSpecific(IterableOnce<Tuple2<K, V>> it) {
            return this.factory.from(it);
        }

        @Override
        public Builder<Tuple2<K, V>, CC> newBuilder() {
            return this.factory.newBuilder();
        }

        public ToFactory(MapFactory<CC> factory2) {
            this.factory = factory2;
        }
    }
}

