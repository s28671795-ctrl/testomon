/*
 * Decompiled with CFR 0.152.
 */
package akka.util;

import akka.util.Subclassification;
import akka.util.SubclassifiedIndex$;
import java.io.Serializable;
import scala.Function1;
import scala.Function2;
import scala.MatchError;
import scala.Predef$;
import scala.Predef$ArrowAssoc$;
import scala.Tuple2;
import scala.collection.IterableFactory$;
import scala.collection.IterableOnce;
import scala.collection.IterableOnceOps;
import scala.collection.SeqOps;
import scala.collection.SetOps;
import scala.collection.immutable.Map;
import scala.collection.immutable.Nil$;
import scala.collection.immutable.Seq;
import scala.collection.immutable.Seq$;
import scala.collection.immutable.Set;
import scala.collection.immutable.Vector;
import scala.package$;
import scala.reflect.ScalaSignature;
import scala.runtime.BooleanRef;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;

@ScalaSignature(bytes="\u0006\u0005\u0005}xA\u0002\u00180\u0011\u0003\t4G\u0002\u00046_!\u0005\u0011G\u000e\u0005\u0006{\u0005!\ta\u0010\u0004\u0005\u0001\u0006\u0001\u0011\t\u0003\u0006\u0002\u001a\r\u0011)\u0019!C!\u0003SC\u0011\"!\b\u0004\u0005\u0003\u0005\u000b\u0011B\"\t\u0015\u0005%2A!b\u0001\n\u0003\tY\u000b\u0003\u0006\u0002.\u000e\u0011\t\u0011)A\u0005\u0003CC!\"a,\u0004\u0005\u0003\u0005\u000b\u0011BAY\u0011%17A!A!\u0002\u0017\t\u0019\f\u0003\u0004>\u0007\u0011\u0005\u0011Q\u0017\u0005\b\u0003w\u0019A\u0011IAc\u0011\u001d\t\td\u0001C\u0005\u0003\u001fDq!a\u0013\u0004\t\u0003\n\u0019\u000eC\u0004\u0002D\r!\t%!7\t\u000f\u0005u3\u0001\"\u0011\u0002^\"9\u0011qQ\u0002\u0005B\u0005%\u0005\u0002CAq\u0003\u0011\u0005\u0011!a9\t\u0011\u0005]\u0018\u0001)A\u0005\u0003s4Q!N\u0018\u0001c\u0011C\u0001BR\n\u0003\u0002\u0004%\tb\u0012\u0005\t=N\u0011\t\u0019!C\t?\"AQm\u0005B\u0001B\u0003&\u0001\n\u0003\u0005g'\t\u0005\t\u0015a\u0003h\u0011\u0015i4\u0003\"\u0003n\u000b\u0011\u00118\u0003A:\t\u000fy\u001c\u0002\u0019!C\t\u007f\"I\u00111B\nA\u0002\u0013E\u0011Q\u0002\u0005\t\u0003#\u0019\u0002\u0015)\u0003\u0002\u0002!1Qh\u0005C\u0001\u0003'A\u0011\"!\u0007\u0014\u0005\u0004%\t\"a\u0007\t\u000f\u0005u1\u0003)A\u0005_\"9\u0011qD\n\u0005\u0002\u0005\u0005\u0002bBA\u0016'\u0011E\u0011Q\u0006\u0005\b\u0003c\u0019B\u0011AA\u001a\u0011\u001d\tYd\u0005C\t\u0003{Aq!a\u0011\u0014\t\u0003\t)\u0005C\u0004\u0002LM!\t\"!\u0014\t\u000f\u0005\r3\u0003\"\u0001\u0002T!9\u0011qK\n\u0005\u0016\u0005e\u0003bBA/'\u0011E\u0011q\f\u0005\b\u0003G\u001aBQCA3\u0011\u001d\tyh\u0005C\t\u0003\u0003Cq!a\"\u0014\t\u0003\nI\tC\u0004\u0002\u0012N!I!a%\t\u000f\u0005e5\u0003\"\u0003\u0002\u001c\u0006\u00112+\u001e2dY\u0006\u001c8/\u001b4jK\u0012Le\u000eZ3y\u0015\t\u0001\u0014'\u0001\u0003vi&d'\"\u0001\u001a\u0002\t\u0005\\7.\u0019\t\u0003i\u0005i\u0011a\f\u0002\u0013'V\u00147\r\\1tg&4\u0017.\u001a3J]\u0012,\u0007p\u0005\u0002\u0002oA\u0011\u0001hO\u0007\u0002s)\t!(A\u0003tG\u0006d\u0017-\u0003\u0002=s\t1\u0011I\\=SK\u001a\fa\u0001P5oSRt4\u0001\u0001\u000b\u0002g\t9aj\u001c8s_>$X#\u0002\"\u0002$\u0006\u001d6CA\u0002D!\u0019!4#!)\u0002&V\u0019Qi[+\u0014\u0005M9\u0014A\u0002<bYV,7/F\u0001I!\rI\u0005k\u0015\b\u0003\u0015:\u0003\"aS\u001d\u000e\u00031S!!\u0014 \u0002\rq\u0012xn\u001c;?\u0013\ty\u0015(\u0001\u0004Qe\u0016$WMZ\u0005\u0003#J\u00131aU3u\u0015\ty\u0015\b\u0005\u0002U+2\u0001A!\u0002,\u0014\u0005\u00049&!\u0001,\u0012\u0005a[\u0006C\u0001\u001dZ\u0013\tQ\u0016HA\u0004O_RD\u0017N\\4\u0011\u0005ab\u0016BA/:\u0005\r\te._\u0001\u000bm\u0006dW/Z:`I\u0015\fHC\u00011d!\tA\u0014-\u0003\u0002cs\t!QK\\5u\u0011\u001d!W#!AA\u0002!\u000b1\u0001\u001f\u00132\u0003\u001d1\u0018\r\\;fg\u0002\n!a]2\u0011\u0007QB'.\u0003\u0002j_\t\t2+\u001e2dY\u0006\u001c8/\u001b4jG\u0006$\u0018n\u001c8\u0011\u0005Q[G!\u00027\u0014\u0005\u00049&!A&\u0015\u00059\fHCA8q!\u0011!4C[*\t\u000b\u0019D\u00029A4\t\u000b\u0019C\u0002\u0019\u0001%\u0003\u000f\rC\u0017M\\4fgB\u0019A/_>\u000e\u0003UT!A^<\u0002\u0013%lW.\u001e;bE2,'B\u0001=:\u0003)\u0019w\u000e\u001c7fGRLwN\\\u0005\u0003uV\u00141aU3r!\u0011ADP\u001b%\n\u0005uL$A\u0002+va2,''A\u0004tk\n\\W-_:\u0016\u0005\u0005\u0005\u0001#\u0002;\u0002\u0004\u0005\u001d\u0011bAA\u0003k\n1a+Z2u_J\u0004R!!\u0003\u0004UNs!\u0001\u000e\u0001\u0002\u0017M,(m[3zg~#S-\u001d\u000b\u0004A\u0006=\u0001\u0002\u00033\u001c\u0003\u0003\u0005\r!!\u0001\u0002\u0011M,(m[3zg\u0002\"\"!!\u0006\u0015\u0007=\f9\u0002C\u0003g;\u0001\u000fq-\u0001\u0003s_>$X#A8\u0002\u000bI|w\u000e\u001e\u0011\u0002\r\u0005$GmS3z)\u0011\t\u0019#a\n\u0011\u0007\u0005\u0015\u0012$D\u0001\u0014\u0011\u0019\tI\u0003\ta\u0001U\u0006\u00191.Z=\u0002\u0017%tg.\u001a:BI\u0012\\U-\u001f\u000b\u0005\u0003G\ty\u0003\u0003\u0004\u0002*\u0005\u0002\rA[\u0001\tC\u0012$g+\u00197vKR1\u00111EA\u001b\u0003oAa!!\u000b#\u0001\u0004Q\u0007BBA\u001dE\u0001\u00071+A\u0003wC2,X-A\u0007j]:,'/\u00113e-\u0006dW/\u001a\u000b\u0007\u0003G\ty$!\u0011\t\r\u0005%2\u00051\u0001k\u0011\u0019\tId\ta\u0001'\u0006Y!/Z7pm\u00164\u0016\r\\;f)\u0019\t\u0019#a\u0012\u0002J!1\u0011\u0011\u0006\u0013A\u0002)Da!!\u000f%\u0001\u0004\u0019\u0016\u0001E5o]\u0016\u0014(+Z7pm\u00164\u0016\r\\;f)\u0019\t\u0019#a\u0014\u0002R!1\u0011\u0011F\u0013A\u0002)Da!!\u000f&\u0001\u0004\u0019F\u0003BA\u0012\u0003+Ba!!\u000f'\u0001\u0004\u0019\u0016A\u00034j]\u00124\u0016\r\\;fgR\u0019\u0001*a\u0017\t\r\u0005%r\u00051\u0001k\u0003=IgN\\3s\r&tGMV1mk\u0016\u001cHc\u0001%\u0002b!1\u0011\u0011\u0006\u0015A\u0002)\f\u0011CZ5oIN+(mS3zg\u0016C8-\u001a9u)\u0019\t9'!\u001b\u0002lA\u0019\u0011\n\u00156\t\r\u0005%\u0012\u00061\u0001k\u0011\u001d\ti'\u000ba\u0001\u0003_\na!\u001a=dKB$\bCBA9\u0003w\n9A\u0004\u0003\u0002t\u0005]dbA&\u0002v%\t!(C\u0002\u0002ze\nq\u0001]1dW\u0006<W-\u0003\u0003\u0002\u0006\u0005u$bAA=s\u0005\u0001\u0012N\u001c8fe\u001aKg\u000eZ*vE.+\u0017p\u001d\u000b\u0007\u0003O\n\u0019)!\"\t\r\u0005%\"\u00061\u0001k\u0011\u001d\tiG\u000ba\u0001\u0003_\n\u0001\u0002^8TiJLgn\u001a\u000b\u0003\u0003\u0017\u00032!SAG\u0013\r\tyI\u0015\u0002\u0007'R\u0014\u0018N\\4\u0002\u0013%tG/Z4sCR,G\u0003BA\u0012\u0003+Cq!a&-\u0001\u0004\t9!A\u0001o\u0003EiWM]4f\u0007\"\fgnZ3t\u0005f\\U-\u001f\u000b\u0005\u0003G\ti\nC\u0004\u0002 6\u0002\r!a\t\u0002\u000f\rD\u0017M\\4fgB\u0019A+a)\u0005\u000b1\u001c!\u0019A,\u0011\u0007Q\u000b9\u000bB\u0003W\u0007\t\u0007q+F\u0001D+\t\t\t+\u0001\u0003lKf\u0004\u0013aB0wC2,Xm\u001d\t\u0005\u0013B\u000b)\u000b\u0005\u00035Q\u0006\u0005F\u0003CA\\\u0003\u007f\u000b\t-a1\u0015\t\u0005e\u0016Q\u0018\t\b\u0003w\u001b\u0011\u0011UAS\u001b\u0005\t\u0001B\u00024\u000b\u0001\b\t\u0019\f\u0003\u0004\u0002\u001a)\u0001\ra\u0011\u0005\b\u0003SQ\u0001\u0019AAQ\u0011\u001d\tyK\u0003a\u0001\u0003c#b!a2\u0002L\u00065\u0007cAAe35\t1\u0001C\u0004\u0002*-\u0001\r!!)\t\u000f\u0005e2\u00021\u0001\u0002&R!\u0011qYAi\u0011\u001d\tI\u0004\u0004a\u0001\u0003K#b!a2\u0002V\u0006]\u0007bBA\u0015\u001b\u0001\u0007\u0011\u0011\u0015\u0005\b\u0003si\u0001\u0019AAS)\u0011\t9-a7\t\u000f\u0005eb\u00021\u0001\u0002&R!\u0011\u0011WAp\u0011\u001d\tIc\u0004a\u0001\u0003C\u000bQ\"Z7qiflUM]4f\u001b\u0006\u0004XCBAs\u0003_\f)0\u0006\u0002\u0002hB9\u0011*!;\u0002n\u0006E\u0018bAAv%\n\u0019Q*\u00199\u0011\u0007Q\u000by\u000fB\u0003m#\t\u0007q\u000b\u0005\u0003J!\u0006M\bc\u0001+\u0002v\u0012)a+\u0005b\u0001/\u0006)\u0012N\u001c;fe:\fG.R7qiflUM]4f\u001b\u0006\u0004\bC\u0002;\u0002|^\ni0C\u0002\u0002lV\u00042!\u0013)8\u0001")
public class SubclassifiedIndex<K, V> {
    private Set<V> values;
    private final Subclassification<K> sc;
    private Vector<Nonroot<K, V>> subkeys;
    private final SubclassifiedIndex<K, V> root;

    public Set<V> values() {
        return this.values;
    }

    public void values_$eq(Set<V> x$1) {
        this.values = x$1;
    }

    public Vector<Nonroot<K, V>> subkeys() {
        return this.subkeys;
    }

    public void subkeys_$eq(Vector<Nonroot<K, V>> x$1) {
        this.subkeys = x$1;
    }

    public SubclassifiedIndex<K, V> root() {
        return this.root;
    }

    public Seq<Tuple2<K, Set<V>>> addKey(K key) {
        return this.mergeChangesByKey(this.innerAddKey(key));
    }

    public Seq<Tuple2<K, Set<V>>> innerAddKey(K key) {
        BooleanRef found = BooleanRef.create(false);
        Vector ch = (Vector)this.subkeys().flatMap((Function1<Nonroot, Seq> & Serializable)n -> {
            if ($this.sc.isEqual(key, n.key())) {
                found$1.elem = true;
                return Nil$.MODULE$;
            }
            if ($this.sc.isSubclass(key, n.key())) {
                found$1.elem = true;
                return n.innerAddKey(key);
            }
            return Nil$.MODULE$;
        });
        if (!found.elem) {
            return (Seq)this.integrate(new Nonroot<K, V>(this.root(), key, this.values(), this.sc)).$colon$plus(new Tuple2<K, Set<V>>(key, this.values()));
        }
        return ch;
    }

    public Seq<Tuple2<K, Set<V>>> addValue(K key, V value) {
        return this.mergeChangesByKey(this.innerAddValue(key, value));
    }

    public Seq<Tuple2<K, Set<V>>> innerAddValue(K key, V value) {
        BooleanRef found = BooleanRef.create(false);
        Vector ch = (Vector)this.subkeys().flatMap((Function1<Nonroot, Seq> & Serializable)n -> {
            if ($this.sc.isSubclass(key, n.key())) {
                found$2.elem = true;
                return n.innerAddValue(key, value);
            }
            return Nil$.MODULE$;
        });
        if (!found.elem) {
            Set v = (Set)this.values().$plus(value);
            Nonroot<K, V> n2 = new Nonroot<K, V>(this.root(), key, v, this.sc);
            return (Seq)((SeqOps)this.integrate(n2).$plus$plus(n2.innerAddValue(key, value))).$colon$plus(Predef$ArrowAssoc$.MODULE$.$minus$greater$extension(Predef$.MODULE$.ArrowAssoc(key), v));
        }
        return ch;
    }

    public Seq<Tuple2<K, Set<V>>> removeValue(K key, V value) {
        return (Seq)this.mergeChangesByKey(this.innerRemoveValue(key, value)).map((Function1<Tuple2, Tuple2> & Serializable)x0$1 -> {
            Tuple2 tuple2 = x0$1;
            if (tuple2 != null) {
                Object k = tuple2._1();
                return new Tuple2(k, this.findValues(k));
            }
            throw new MatchError(tuple2);
        });
    }

    public Seq<Tuple2<K, Set<V>>> innerRemoveValue(K key, V value) {
        BooleanRef found = BooleanRef.create(false);
        Vector ch = (Vector)this.subkeys().flatMap((Function1<Nonroot, Seq> & Serializable)n -> {
            if ($this.sc.isSubclass(key, n.key())) {
                found$3.elem = true;
                return n.innerRemoveValue(key, value);
            }
            return Nil$.MODULE$;
        });
        if (!found.elem) {
            Nonroot<K, V> n2 = new Nonroot<K, V>(this.root(), key, this.values(), this.sc);
            return (Seq)this.integrate(n2).$plus$plus(n2.removeValue(value));
        }
        return ch;
    }

    public Seq<Tuple2<K, Set<V>>> removeValue(V value) {
        return this.mergeChangesByKey((Seq)this.subkeys().flatMap((Function1<Nonroot, Seq> & Serializable)x$3 -> x$3.removeValue(value)));
    }

    public final Set<V> findValues(K key) {
        return this.root().innerFindValues(key);
    }

    public Set<V> innerFindValues(K key) {
        return (Set)this.subkeys().foldLeft(Predef$.MODULE$.Set().empty(), (Function2<Set, Nonroot, Set> & Serializable)(s2, n) -> {
            if ($this.sc.isSubclass(key, n.key())) {
                return (Set)s2.$plus$plus(n.innerFindValues(key));
            }
            return s2;
        });
    }

    public final Set<K> findSubKeysExcept(K key, Vector<Nonroot<K, V>> except) {
        return this.root().innerFindSubKeys(key, except);
    }

    public Set<K> innerFindSubKeys(K key, Vector<Nonroot<K, V>> except) {
        return (Set)this.subkeys().foldLeft(Predef$.MODULE$.Set().empty(), (Function2<Set, Nonroot, Set> & Serializable)(s2, n) -> {
            if ($this.sc.isEqual(key, n.key())) {
                return s2;
            }
            return (Set)n.innerFindSubKeys(key, except).$plus$plus($this.sc.isSubclass(n.key(), key) && !except.exists((Function1<Nonroot, Object> & Serializable)e -> BoxesRunTime.boxToBoolean(this.sc.isEqual(key, e.key()))) ? s2.$plus(n.key()) : s2);
        });
    }

    public String toString() {
        return this.subkeys().mkString(new StringBuilder(21).append("SubclassifiedIndex(").append(this.values()).append(",\n").toString(), ",\n", ")");
    }

    private Seq<Tuple2<K, Set<V>>> integrate(Nonroot<K, V> n2) {
        Tuple2<Vector<Nonroot<K, V>>, Vector<Nonroot<K, V>>> tuple2 = this.subkeys().partition((Function1<Nonroot, Object> & Serializable)k -> BoxesRunTime.boxToBoolean(this.sc.isSubclass(k.key(), n2.key())));
        if (tuple2 == null) {
            throw new MatchError(tuple2);
        }
        Vector<Nonroot<K, V>> subsub = tuple2._1();
        Vector<Nonroot<K, V>> sub = tuple2._2();
        Tuple2<Vector<Nonroot<K, V>>, Vector<Nonroot<K, V>>> tuple22 = new Tuple2<Vector<Nonroot<K, V>>, Vector<Nonroot<K, V>>>(subsub, sub);
        Vector<Nonroot<K, V>> subsub2 = tuple22._1();
        Vector<Nonroot<K, V>> sub2 = tuple22._2();
        this.subkeys_$eq((Vector)sub2.$colon$plus(n2));
        n2.subkeys_$eq(subsub2.nonEmpty() ? subsub2 : n2.subkeys());
        n2.subkeys_$eq((Vector)n2.subkeys().$plus$plus((IterableOnce)this.findSubKeysExcept(n2.key(), n2.subkeys()).map((Function1<Object, Nonroot> & Serializable)k -> new Nonroot(this.root(), k, this.values(), $this.sc))));
        return (Seq)n2.subkeys().map((Function1<Nonroot, Tuple2> & Serializable)n -> new Tuple2(n.key(), n.values().toSet()));
    }

    private Seq<Tuple2<K, Set<V>>> mergeChangesByKey(Seq<Tuple2<K, Set<V>>> changes) {
        return ((IterableOnceOps)changes.foldLeft(SubclassifiedIndex$.MODULE$.emptyMergeMap(), (Function2<Map, Tuple2, Map> & Serializable)(x0$1, x1$1) -> {
            Tuple2<Map, Tuple2> tuple2 = new Tuple2<Map, Tuple2>((Map)x0$1, (Tuple2)x1$1);
            if (tuple2 != null) {
                Map m4 = tuple2._1();
                Tuple2 tuple22 = tuple2._2();
                if (tuple22 != null) {
                    Object k = tuple22._1();
                    Set s2 = (Set)tuple22._2();
                    return (Map)m4.updated(k, ((SetOps)m4.apply(k)).$plus$plus(s2));
                }
            }
            throw new MatchError(tuple2);
        })).to(IterableFactory$.MODULE$.toFactory(Seq$.MODULE$));
    }

    public SubclassifiedIndex(Set<V> values2, Subclassification<K> sc) {
        this.values = values2;
        this.sc = sc;
        this.subkeys = package$.MODULE$.Vector().empty();
        this.root = this;
    }

    public SubclassifiedIndex(Subclassification<K> sc) {
        this((Set<V>)Predef$.MODULE$.Set().empty(), sc);
    }

    public static class Nonroot<K, V>
    extends SubclassifiedIndex<K, V> {
        private final SubclassifiedIndex<K, V> root;
        private final K key;
        private final Subclassification<K> sc;

        @Override
        public SubclassifiedIndex<K, V> root() {
            return this.root;
        }

        public K key() {
            return this.key;
        }

        @Override
        public Seq<Tuple2<K, Set<V>>> innerAddValue(K key, V value) {
            if (this.sc.isEqual(key, this.key())) {
                return this.addValue(value);
            }
            return super.innerAddValue(key, value);
        }

        private Seq<Tuple2<K, Set<V>>> addValue(V value) {
            Vector kids = (Vector)this.subkeys().flatMap((Function1<Nonroot, Seq> & Serializable)x$1 -> x$1.addValue(value));
            if (!this.values().contains(value)) {
                this.values_$eq((Set)this.values().$plus(value));
                return (Seq)kids.$colon$plus(new Tuple2<K, Object>(this.key(), Predef$.MODULE$.Set().apply((Seq)ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{value}))));
            }
            return kids;
        }

        @Override
        public Seq<Tuple2<K, Set<V>>> innerRemoveValue(K key, V value) {
            if (this.sc.isEqual(key, this.key())) {
                return this.removeValue(value);
            }
            return super.innerRemoveValue(key, value);
        }

        @Override
        public Seq<Tuple2<K, Set<V>>> removeValue(V value) {
            Vector kids = (Vector)this.subkeys().flatMap((Function1<Nonroot, Seq> & Serializable)x$2 -> x$2.removeValue(value));
            if (this.values().contains(value)) {
                this.values_$eq((Set)this.values().$minus(value));
                return (Seq)kids.$colon$plus(new Tuple2<K, Object>(this.key(), Predef$.MODULE$.Set().apply((Seq)ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{value}))));
            }
            return kids;
        }

        @Override
        public Set<V> innerFindValues(K key) {
            if (this.sc.isEqual(key, this.key())) {
                return this.values();
            }
            return super.innerFindValues(key);
        }

        @Override
        public String toString() {
            return this.subkeys().mkString(new StringBuilder(12).append("Nonroot(").append(this.key()).append(", ").append(this.values()).append(",\n").toString(), ",\n", ")");
        }

        public Nonroot(SubclassifiedIndex<K, V> root, K key, Set<V> _values, Subclassification<K> sc) {
            this.root = root;
            this.key = key;
            this.sc = sc;
            super(_values, sc);
        }
    }
}

