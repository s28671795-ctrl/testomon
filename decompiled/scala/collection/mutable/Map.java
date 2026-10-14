/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.mutable;

import java.io.Serializable;
import scala.Function1;
import scala.Option;
import scala.Tuple2;
import scala.collection.IterableOnce;
import scala.collection.Iterator;
import scala.collection.MapFactory;
import scala.collection.mutable.AbstractMap;
import scala.collection.mutable.Builder;
import scala.collection.mutable.Iterable;
import scala.collection.mutable.Map$;
import scala.collection.mutable.MapOps;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005}fa\u0002\u000f\u001e!\u0003\r\t\u0001\n\u0005\u0006#\u0002!\tA\u0015\u0005\u0006-\u0002!\te\u0016\u0005\u00067\u0002!\t\u0001\u0018\u0005\u0006E\u0002!\taY\u0004\u0006KvA\tA\u001a\u0004\u00069uA\ta\u001a\u0005\u0006_\u001a!\t\u0001\u001d\u0004\u0005c\u001a\u0001!\u000f\u0003\u0006\u0002\f!\u0011)\u0019!C\u0001\u0003\u001bA!\"!\u0005\t\u0005\u0003\u0005\u000b\u0011BA\b\u0011)\t\u0019\u0002\u0003BC\u0002\u0013\u0005\u0011Q\u0003\u0005\u000b\u00033A!\u0011!Q\u0001\n\u0005]\u0001BB8\t\t\u0003\tY\u0002C\u0004\u0002\"!!\t%a\t\t\u000f\u0005%\u0002\u0002\"\u0001\u0002,!9\u0011Q\u0007\u0005\u0005B\u0005]\u0002bBA \u0011\u0011\u0005\u0013\u0011\t\u0005\u0006-\"!\te\u0016\u0005\u0007\u0003\u0013BA\u0011\t*\t\u000f\u0005-\u0003\u0002\"\u0001\u0002N!9\u0011q\u000b\u0005\u0005\u0002\u0005e\u0003bBA1\u0011\u0011\u0005\u00111\r\u0005\b\u0003OBA\u0011IA5\u0011\u001d\t\u0019\t\u0003C!\u0003\u000bCq!a\"\t\t#\nI\tC\u0004\u0002\u0012\"!\t&a%\t\u0013\u0005\u001df!!A\u0005\n\u0005%&aA'ba*\u0011adH\u0001\b[V$\u0018M\u00197f\u0015\t\u0001\u0013%\u0001\u0006d_2dWm\u0019;j_:T\u0011AI\u0001\u0006g\u000e\fG.Y\u0002\u0001+\r)3'P\n\t\u0001\u0019RsHQ$K\u001bB\u0011q\u0005K\u0007\u0002C%\u0011\u0011&\t\u0002\u0007\u0003:L(+\u001a4\u0011\u0007-bc&D\u0001\u001e\u0013\tiSD\u0001\u0005Ji\u0016\u0014\u0018M\u00197f!\u00119s&\r\u001f\n\u0005A\n#A\u0002+va2,'\u0007\u0005\u00023g1\u0001A!\u0002\u001b\u0001\u0005\u0004)$!A&\u0012\u0005YJ\u0004CA\u00148\u0013\tA\u0014EA\u0004O_RD\u0017N\\4\u0011\u0005\u001dR\u0014BA\u001e\"\u0005\r\te.\u001f\t\u0003eu\"QA\u0010\u0001C\u0002U\u0012\u0011A\u0016\t\u0005\u0001\u0006\u000bD(D\u0001 \u0013\tar\u0004\u0005\u0004,\u0007FbTIR\u0005\u0003\tv\u0011a!T1q\u001fB\u001c\bCA\u0016\u0001!\u0011Y\u0003!\r\u001f\u0011\u0007-Be&\u0003\u0002J;\tAqI]8xC\ndW\rE\u0002,\u0017FJ!\u0001T\u000f\u0003\u0015MC'/\u001b8lC\ndW\r\u0005\u0004A\u001dFbT\tU\u0005\u0003\u001f~\u0011!#T1q\r\u0006\u001cGo\u001c:z\t\u00164\u0017-\u001e7ugB\u00111\u0006L\u0001\u0007I%t\u0017\u000e\u001e\u0013\u0015\u0003M\u0003\"a\n+\n\u0005U\u000b#\u0001B+oSR\f!\"\\1q\r\u0006\u001cGo\u001c:z+\u0005A\u0006c\u0001!Z\u000b&\u0011!l\b\u0002\u000b\u001b\u0006\u0004h)Y2u_JL\u0018aC<ji\"$UMZ1vYR$\"AR/\t\u000by\u001b\u0001\u0019A0\u0002\u0003\u0011\u0004Ba\n12y%\u0011\u0011-\t\u0002\n\rVt7\r^5p]F\n\u0001c^5uQ\u0012+g-Y;miZ\u000bG.^3\u0015\u0005\u0019#\u0007\"\u00020\u0005\u0001\u0004a\u0014aA'baB\u00111FB\n\u0003\r!\u00042!\u001b7F\u001d\t\u0001%.\u0003\u0002l?\u0005QQ*\u00199GC\u000e$xN]=\n\u00055t'\u0001\u0003#fY\u0016<\u0017\r^3\u000b\u0005-|\u0012A\u0002\u001fj]&$h\bF\u0001g\u0005-9\u0016\u000e\u001e5EK\u001a\fW\u000f\u001c;\u0016\u0007MD(p\u0005\u0003\tint\b\u0003B\u0016vofL!A^\u000f\u0003\u0017\u0005\u00137\u000f\u001e:bGRl\u0015\r\u001d\t\u0003ea$Q\u0001\u000e\u0005C\u0002U\u0002\"A\r>\u0005\u000byB!\u0019A\u001b\u0011\r-\u001au/_#}!\u0011i\bb^=\u000e\u0003\u0019\u00012a`A\u0003\u001d\r9\u0013\u0011A\u0005\u0004\u0003\u0007\t\u0013a\u00029bG.\fw-Z\u0005\u0005\u0003\u000f\tIA\u0001\u0007TKJL\u0017\r\\5{C\ndWMC\u0002\u0002\u0004\u0005\n!\"\u001e8eKJd\u00170\u001b8h+\t\ty\u0001\u0005\u0003,\u0001]L\u0018aC;oI\u0016\u0014H._5oO\u0002\nA\u0002Z3gCVdGOV1mk\u0016,\"!a\u0006\u0011\t\u001d\u0002w/_\u0001\u000eI\u00164\u0017-\u001e7u-\u0006dW/\u001a\u0011\u0015\u000bq\fi\"a\b\t\u000f\u0005-Q\u00021\u0001\u0002\u0010!9\u00111C\u0007A\u0002\u0005]\u0011a\u00023fM\u0006,H\u000e\u001e\u000b\u0004s\u0006\u0015\u0002BBA\u0014\u001d\u0001\u0007q/A\u0002lKf\f\u0001\"\u001b;fe\u0006$xN]\u000b\u0003\u0003[\u0001R\u0001QA\u0018\u0003gI1!!\r \u0005!IE/\u001a:bi>\u0014\b\u0003B\u00140of\fq![:F[B$\u00180\u0006\u0002\u0002:A\u0019q%a\u000f\n\u0007\u0005u\u0012EA\u0004C_>dW-\u00198\u0002\u0013-twn\u001e8TSj,WCAA\"!\r9\u0013QI\u0005\u0004\u0003\u000f\n#aA%oi\u0006)1\r\\3be\u0006\u0019q-\u001a;\u0015\t\u0005=\u0013Q\u000b\t\u0005O\u0005E\u00130C\u0002\u0002T\u0005\u0012aa\u00149uS>t\u0007BBA\u0014)\u0001\u0007q/A\u0006tk\n$(/Y2u\u001f:,G\u0003BA.\u0003;j\u0011\u0001\u0003\u0005\u0007\u0003?*\u0002\u0019A<\u0002\t\u0015dW-\\\u0001\u0007C\u0012$wJ\\3\u0015\t\u0005m\u0013Q\r\u0005\b\u0003?2\u0002\u0019AA\u001a\u0003\u0019\u0019wN\\2biV!\u00111NA9)\u0011\ti'a\u001e\u0011\u000b-\u0002q/a\u001c\u0011\u0007I\n\t\bB\u0004\u0002t]\u0011\r!!\u001e\u0003\u0005Y\u0013\u0014CA=:\u0011\u001d\tIh\u0006a\u0001\u0003w\naa];gM&D\b#\u0002!\u0002~\u0005\u0005\u0015bAA@?\ta\u0011\n^3sC\ndWm\u00148dKB)qeL<\u0002p\u0005)Q-\u001c9usV\tA0\u0001\u0007ge>l7\u000b]3dS\u001aL7\rF\u0002}\u0003\u0017Cq!!$\u001a\u0001\u0004\ty)\u0001\u0003d_2d\u0007#\u0002!\u0002~\u0005M\u0012A\u00058foN\u0003XmY5gS\u000e\u0014U/\u001b7eKJ,\"!!&\u0011\r-\n9*a\r}\u0013\r\tI*\b\u0002\b\u0005VLG\u000eZ3sQ\u001dA\u0011QTAR\u0003K\u00032aJAP\u0013\r\t\t+\t\u0002\u0011'\u0016\u0014\u0018.\u00197WKJ\u001c\u0018n\u001c8V\u0013\u0012\u000bQA^1mk\u0016t\u0012aA\u0001\roJLG/\u001a*fa2\f7-\u001a\u000b\u0003\u0003W\u0003B!!,\u000286\u0011\u0011q\u0016\u0006\u0005\u0003c\u000b\u0019,\u0001\u0003mC:<'BAA[\u0003\u0011Q\u0017M^1\n\t\u0005e\u0016q\u0016\u0002\u0007\u001f\nTWm\u0019;)\u000f\u0019\ti*a)\u0002&\":Q!!(\u0002$\u0006\u0015\u0006")
public interface Map<K, V>
extends Iterable<Tuple2<K, V>>,
scala.collection.Map<K, V>,
MapOps<K, V, Map, Map<K, V>> {
    public static <K, V> Builder<Tuple2<K, V>, Map<K, V>> newBuilder() {
        return Map$.MODULE$.newBuilder();
    }

    public static Object from(IterableOnce it) {
        return Map$.MODULE$.from(it);
    }

    public static /* synthetic */ MapFactory mapFactory$(Map $this) {
        return $this.mapFactory();
    }

    @Override
    default public MapFactory<Map> mapFactory() {
        return Map$.MODULE$;
    }

    public static /* synthetic */ Map withDefault$(Map $this, Function1 d) {
        return $this.withDefault(d);
    }

    default public Map<K, V> withDefault(Function1<K, V> d) {
        return new WithDefault<K, V>(this, d);
    }

    public static /* synthetic */ Map withDefaultValue$(Map $this, Object d) {
        return $this.withDefaultValue(d);
    }

    default public Map<K, V> withDefaultValue(V d) {
        return new WithDefault(this, (Function1<Object, Object> & Serializable)x -> d);
    }

    public static void $init$(Map $this) {
    }

    /*
     * Illegal identifiers - consider using --renameillegalidents true
     */
    public static class WithDefault<K, V>
    extends AbstractMap<K, V>
    implements Serializable {
        private static final long serialVersionUID = 3L;
        private final Map<K, V> underlying;
        private final Function1<K, V> defaultValue;

        public Map<K, V> underlying() {
            return this.underlying;
        }

        public Function1<K, V> defaultValue() {
            return this.defaultValue;
        }

        @Override
        public V default(K key) {
            return this.defaultValue().apply(key);
        }

        @Override
        public Iterator<Tuple2<K, V>> iterator() {
            return this.underlying().iterator();
        }

        @Override
        public boolean isEmpty() {
            return this.underlying().isEmpty();
        }

        @Override
        public int knownSize() {
            return this.underlying().knownSize();
        }

        @Override
        public MapFactory<Map> mapFactory() {
            return this.underlying().mapFactory();
        }

        @Override
        public void clear() {
            this.underlying().clear();
        }

        @Override
        public Option<V> get(K key) {
            return this.underlying().get(key);
        }

        public WithDefault<K, V> subtractOne(K elem) {
            this.underlying().subtractOne(elem);
            return this;
        }

        public WithDefault<K, V> addOne(Tuple2<K, V> elem) {
            this.underlying().addOne(elem);
            return this;
        }

        @Override
        public <V2> Map<K, V2> concat(IterableOnce<Tuple2<K, V2>> suffix) {
            return ((Map)this.underlying().concat(suffix)).withDefault(this.defaultValue());
        }

        @Override
        public WithDefault<K, V> empty() {
            return new WithDefault<K, V>((Map)this.underlying().empty(), this.defaultValue());
        }

        @Override
        public WithDefault<K, V> fromSpecific(IterableOnce<Tuple2<K, V>> coll) {
            return new WithDefault<K, V>(this.mapFactory().from(coll), this.defaultValue());
        }

        @Override
        public Builder<Tuple2<K, V>, WithDefault<K, V>> newSpecificBuilder() {
            return Map$.MODULE$.newBuilder().mapResult((Function1<Map, WithDefault> & Serializable)(To p) -> new WithDefault(p, this.defaultValue()));
        }

        public WithDefault(Map<K, V> underlying, Function1<K, V> defaultValue) {
            this.underlying = underlying;
            this.defaultValue = defaultValue;
        }
    }
}

