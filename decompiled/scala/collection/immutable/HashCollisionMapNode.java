/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.immutable;

import java.io.Serializable;
import java.util.NoSuchElementException;
import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.Function3;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.Predef$;
import scala.Some;
import scala.Tuple2;
import scala.collection.IterableOnce;
import scala.collection.Iterator;
import scala.collection.immutable.BigVector;
import scala.collection.immutable.BitmapIndexedMapNode;
import scala.collection.immutable.HashMapBuilder;
import scala.collection.immutable.MapNode;
import scala.collection.immutable.MapNode$;
import scala.collection.immutable.Vector;
import scala.collection.immutable.Vector0$;
import scala.collection.immutable.Vector1;
import scala.collection.immutable.VectorBuilder;
import scala.collection.immutable.VectorStatics$;
import scala.reflect.ClassTag$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.Nothing$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\tec\u0001\u0002\u0015*\rAB\u0001\"\u0012\u0001\u0003\u0006\u0004%\tA\u0012\u0005\t\u0015\u0002\u0011\t\u0011)A\u0005\u000f\"A1\n\u0001BC\u0002\u0013\u0005a\t\u0003\u0005M\u0001\t\u0005\t\u0015!\u0003H\u0011!i\u0005A!a\u0001\n\u0003q\u0005\u0002C0\u0001\u0005\u0003\u0007I\u0011\u00011\t\u0011\u0019\u0004!\u0011!Q!\n=CQa\u001a\u0001\u0005\u0002!Da!\u001c\u0001\u0005\u0002%r\u0007\"B9\u0001\t\u00031\u0005\"\u0002:\u0001\t\u0003\u0019\b\"B=\u0001\t\u0003Q\bbBA\u0003\u0001\u0011\u0005\u0013q\u0001\u0005\b\u0003'\u0001A\u0011AA\u000b\u0011\u001d\t\u0019\u0004\u0001C!\u0003kAq!!\u0012\u0001\t\u0003\t9\u0005C\u0004\u0002Z\u0001!\t!a\u0017\t\u000f\u0005M\u0004\u0001\"\u0001\u0002v!9\u0011q\u0011\u0001\u0005\u0002\u0005%\u0005BBAF\u0001\u0011\u0005a\tC\u0004\u0002\u000e\u0002!\t!a$\t\u000f\u0005U\u0005\u0001\"\u0001\u0002\n\"1\u0011q\u0013\u0001\u0005\u0002\u0019Cq!!'\u0001\t\u0003\tY\nC\u0004\u0002 \u0002!\t!!)\t\u000f\u0005\u0015\u0006\u0001\"\u0001\u0002(\"9\u00111\u0016\u0001\u0005B\u00055\u0006bBAY\u0001\u0011\u0005\u00111\u0017\u0005\b\u0003\u000b\u0004A\u0011AAd\u0011\u001d\t9\u000e\u0001C!\u00033Dq!a9\u0001\t\u0003\n)\u000fC\u0004\u0002v\u0002!\t%a>\t\u000f\u0005u\b\u0001\"\u0011\u0002\u0000\"9!q\u0002\u0001\u0005B\tE\u0001b\u0002B\u001a\u0001\u0011\u0005#Q\u0007\u0005\b\u0005\u0003\u0002A\u0011\tB\"\u0011\u001d\u0011y\u0005\u0001C!\u0005#BqAa\u0015\u0001\t\u0003\u0012)\u0006\u0003\u0004\u0003X\u0001!\tE\u0012\u0002\u0015\u0011\u0006\u001c\bnQ8mY&\u001c\u0018n\u001c8NCBtu\u000eZ3\u000b\u0005)Z\u0013!C5n[V$\u0018M\u00197f\u0015\taS&\u0001\u0006d_2dWm\u0019;j_:T\u0011AL\u0001\u0006g\u000e\fG.Y\u0002\u0001+\r\t\u0004hQ\n\u0003\u0001I\u0002Ba\r\u001b7\u00056\t\u0011&\u0003\u00026S\t9Q*\u00199O_\u0012,\u0007CA\u001c9\u0019\u0001!Q!\u000f\u0001C\u0002i\u0012\u0011aS\t\u0003w}\u0002\"\u0001P\u001f\u000e\u00035J!AP\u0017\u0003\u000f9{G\u000f[5oOB\u0011A\bQ\u0005\u0003\u00036\u00121!\u00118z!\t94\t\u0002\u0004E\u0001\u0011\u0015\rA\u000f\u0002\u0002-\u0006aqN]5hS:\fG\u000eS1tQV\tq\t\u0005\u0002=\u0011&\u0011\u0011*\f\u0002\u0004\u0013:$\u0018!D8sS\u001eLg.\u00197ICND\u0007%\u0001\u0003iCND\u0017!\u00025bg\"\u0004\u0013aB2p]R,g\u000e^\u000b\u0002\u001fB\u00191\u0007\u0015*\n\u0005EK#A\u0002,fGR|'\u000f\u0005\u0003='Z*\u0016B\u0001+.\u0005\u0019!V\u000f\u001d7fe)\u0012!IV\u0016\u0002/B\u0011\u0001,X\u0007\u00023*\u0011!lW\u0001\nk:\u001c\u0007.Z2lK\u0012T!\u0001X\u0017\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0002_3\n\tRO\\2iK\u000e\\W\r\u001a,be&\fgnY3\u0002\u0017\r|g\u000e^3oi~#S-\u001d\u000b\u0003C\u0012\u0004\"\u0001\u00102\n\u0005\rl#\u0001B+oSRDq!\u001a\u0004\u0002\u0002\u0003\u0007q*A\u0002yIE\n\u0001bY8oi\u0016tG\u000fI\u0001\u0007y%t\u0017\u000e\u001e \u0015\t%T7\u000e\u001c\t\u0005g\u00011$\tC\u0003F\u0011\u0001\u0007q\tC\u0003L\u0011\u0001\u0007q\tC\u0003N\u0011\u0001\u0007q*A\u0004j]\u0012,\u0007p\u00144\u0015\u0005\u001d{\u0007\"\u00029\n\u0001\u0004y\u0014aA6fs\u0006!1/\u001b>f\u0003\u0015\t\u0007\u000f\u001d7z)\u0015\u0011E/\u001e<x\u0011\u0015\u00018\u00021\u00017\u0011\u0015)5\u00021\u0001H\u0011\u0015Y5\u00021\u0001H\u0011\u0015A8\u00021\u0001H\u0003\u0015\u0019\b.\u001b4u\u0003\r9W\r\u001e\u000b\bwz|\u0018\u0011AA\u0002!\raDPQ\u0005\u0003{6\u0012aa\u00149uS>t\u0007\"\u00029\r\u0001\u00041\u0004\"B#\r\u0001\u00049\u0005\"B&\r\u0001\u00049\u0005\"\u0002=\r\u0001\u00049\u0015\u0001C4fiR+\b\u000f\\3\u0015\u0015\u0005%\u00111BA\u0007\u0003\u001f\t\t\u0002\u0005\u0003='Z\u0012\u0005\"\u00029\u000e\u0001\u00041\u0004\"B#\u000e\u0001\u00049\u0005\"B&\u000e\u0001\u00049\u0005\"\u0002=\u000e\u0001\u00049\u0015!C4fi>\u0013X\t\\:f+\u0011\t9\"a\u0007\u0015\u0019\u0005e\u0011\u0011EA\u0012\u0003K\t9#!\u000b\u0011\u0007]\nY\u0002B\u0004\u0002\u001e9\u0011\r!a\b\u0003\u0005Y\u000b\u0014C\u0001\"@\u0011\u0015\u0001h\u00021\u00017\u0011\u0015)e\u00021\u0001H\u0011\u0015Ye\u00021\u0001H\u0011\u0015Ah\u00021\u0001H\u0011!\tYC\u0004CA\u0002\u00055\u0012!\u00014\u0011\u000bq\ny#!\u0007\n\u0007\u0005ERF\u0001\u0005=Eft\u0017-\\3?\u0003-\u0019wN\u001c;bS:\u001c8*Z=\u0015\u0015\u0005]\u0012QHA \u0003\u0003\n\u0019\u0005E\u0002=\u0003sI1!a\u000f.\u0005\u001d\u0011un\u001c7fC:DQ\u0001]\bA\u0002YBQ!R\bA\u0002\u001dCQaS\bA\u0002\u001dCQ\u0001_\bA\u0002\u001d\u000b\u0001bY8oi\u0006Lgn]\u000b\u0005\u0003\u0013\n\u0019\u0006\u0006\u0006\u00028\u0005-\u0013QJA+\u0003/BQ\u0001\u001d\tA\u0002YBq!a\u0014\u0011\u0001\u0004\t\t&A\u0003wC2,X\rE\u00028\u0003'\"q!!\b\u0011\u0005\u0004\ty\u0002C\u0003L!\u0001\u0007q\tC\u0003y!\u0001\u0007q)A\u0004va\u0012\fG/\u001a3\u0016\t\u0005u\u00131\r\u000b\u000f\u0003?\n)'a\u001a\u0002j\u0005-\u0014QNA8!\u0015\u0019DGNA1!\r9\u00141\r\u0003\b\u0003;\t\"\u0019AA\u0010\u0011\u0015\u0001\u0018\u00031\u00017\u0011\u001d\ty%\u0005a\u0001\u0003CBQ!R\tA\u0002\u001dCQaS\tA\u0002\u001dCQ\u0001_\tA\u0002\u001dCq!!\u001d\u0012\u0001\u0004\t9$\u0001\u0007sKBd\u0017mY3WC2,X-A\u0004sK6|g/\u001a3\u0016\t\u0005]\u0014Q\u0010\u000b\u000b\u0003s\ny(!!\u0002\u0004\u0006\u0015\u0005#B\u001a5m\u0005m\u0004cA\u001c\u0002~\u00119\u0011Q\u0004\nC\u0002\u0005}\u0001\"\u00029\u0013\u0001\u00041\u0004\"B#\u0013\u0001\u00049\u0005\"B&\u0013\u0001\u00049\u0005\"\u0002=\u0013\u0001\u00049\u0015\u0001\u00035bg:{G-Z:\u0016\u0005\u0005]\u0012!\u00038pI\u0016\f%/\u001b;z\u0003\u001d9W\r\u001e(pI\u0016$2AMAI\u0011\u0019\t\u0019*\u0006a\u0001\u000f\u0006)\u0011N\u001c3fq\u0006Q\u0001.Y:QCfdw.\u00193\u0002\u0019A\f\u0017\u0010\\8bI\u0006\u0013\u0018\u000e^=\u0002\r\u001d,GoS3z)\r1\u0014Q\u0014\u0005\u0007\u0003'C\u0002\u0019A$\u0002\u0011\u001d,GOV1mk\u0016$2AQAR\u0011\u0019\t\u0019*\u0007a\u0001\u000f\u0006Qq-\u001a;QCfdw.\u00193\u0015\t\u0005%\u0011\u0011\u0016\u0005\u0007\u0003'S\u0002\u0019A$\u0002\u000f\u001d,G\u000fS1tQR\u0019q)a,\t\r\u0005M5\u00041\u0001H\u0003\u001d1wN]3bG\",B!!.\u0002BR\u0019\u0011-a.\t\u000f\u0005-B\u00041\u0001\u0002:B9A(a/\u0002\n\u0005}\u0016bAA_[\tIa)\u001e8di&|g.\r\t\u0004o\u0005\u0005GABAb9\t\u0007!HA\u0001V\u000311wN]3bG\",e\u000e\u001e:z+\u0011\tI-!6\u0015\u0007\u0005\fY\rC\u0004\u0002,u\u0001\r!!4\u0011\u000fq\nyM\u000e\"\u0002T&\u0019\u0011\u0011[\u0017\u0003\u0013\u0019+hn\u0019;j_:\u0014\u0004cA\u001c\u0002V\u00121\u00111Y\u000fC\u0002i\nqBZ8sK\u0006\u001c\u0007nV5uQ\"\u000b7\u000f\u001b\u000b\u0004C\u0006m\u0007bBA\u0016=\u0001\u0007\u0011Q\u001c\t\by\u0005}gGQ$b\u0013\r\t\t/\f\u0002\n\rVt7\r^5p]N\n\u0011\u0002\u001e:b]N4wN]7\u0016\t\u0005\u001d\u0018Q\u001e\u000b\u0005\u0003S\f\t\u0010E\u00034\u0001Y\nY\u000fE\u00028\u0003[$a!a< \u0005\u0004Q$!A,\t\u000f\u0005-r\u00041\u0001\u0002tB9A(a47\u0005\u0006-\u0018AB3rk\u0006d7\u000f\u0006\u0003\u00028\u0005e\bBBA~A\u0001\u0007q(\u0001\u0003uQ\u0006$\u0018AB2p]\u000e\fG/\u0006\u0003\u0003\u0002\t\u001dAC\u0002B\u0002\u0005\u0013\u0011i\u0001E\u00034\u0001Y\u0012)\u0001E\u00028\u0005\u000f!q!!\b\"\u0005\u0004\ty\u0002C\u0004\u0002|\u0006\u0002\rAa\u0003\u0011\u000bM\"dG!\u0002\t\u000ba\f\u0003\u0019A$\u0002\u00135,'oZ3J]R|W\u0003\u0002B\n\u0005C!\u0002B!\u0006\u0003$\t\u001d\"\u0011\u0007\u000b\u0004C\n]\u0001b\u0002B\rE\u0001\u0007!1D\u0001\u0007[\u0016\u0014x-\u001a4\u0011\u0013q\ny-!\u0003\u0003\u001e\tu\u0001#\u0002\u001fTm\t}\u0001cA\u001c\u0003\"\u00119\u0011Q\u0004\u0012C\u0002\u0005}\u0001bBA~E\u0001\u0007!Q\u0005\t\u0006gQ2$q\u0004\u0005\b\u0005S\u0011\u0003\u0019\u0001B\u0016\u0003\u001d\u0011W/\u001b7eKJ\u0004ba\rB\u0017m\t}\u0011b\u0001B\u0018S\tq\u0001*Y:i\u001b\u0006\u0004()^5mI\u0016\u0014\b\"\u0002=#\u0001\u00049\u0015a\u00022vS2$Gk\\\u000b\u0005\u0005o\u0011y\u0004F\u0002b\u0005sAqA!\u000b$\u0001\u0004\u0011Y\u0004\u0005\u00044\u0005[1$Q\b\t\u0004o\t}BaBA\u000fG\t\u0007\u0011qD\u0001\u000bM&dG/\u001a:J[BdG#\u0002\u001a\u0003F\t-\u0003b\u0002B$I\u0001\u0007!\u0011J\u0001\u0005aJ,G\rE\u0004=\u0003w\u000bI!a\u000e\t\u000f\t5C\u00051\u0001\u00028\u00059a\r\\5qa\u0016$\u0017\u0001B2paf$\u0012![\u0001\tQ\u0006\u001c\bnQ8eKR\tq)\u0001\rdC\u000eDW\r\u001a&bm\u0006\\U-_*fi\"\u000b7\u000f[\"pI\u0016\u0004")
public final class HashCollisionMapNode<K, V>
extends MapNode<K, V> {
    private final int originalHash;
    private final int hash;
    private Vector<Tuple2<K, V>> content;

    public int originalHash() {
        return this.originalHash;
    }

    public int hash() {
        return this.hash;
    }

    public Vector<Tuple2<K, V>> content() {
        return this.content;
    }

    public void content_$eq(Vector<Tuple2<K, V>> x$1) {
        this.content = x$1;
    }

    public int indexOf(Object key) {
        Iterator<Tuple2<K, V>> iter = this.content().iterator();
        int i = 0;
        while (iter.hasNext()) {
            if (BoxesRunTime.equals(iter.next()._1(), key)) {
                return i;
            }
            ++i;
        }
        return -1;
    }

    @Override
    public int size() {
        return this.content().length();
    }

    @Override
    public V apply(K key, int originalHash, int hash, int shift) {
        Option<V> option = this.get(key, originalHash, hash, shift);
        if (option == null) {
            throw null;
        }
        Option<V> getOrElse_this = option;
        if (getOrElse_this.isEmpty()) {
            throw new NoSuchElementException();
        }
        return getOrElse_this.get();
    }

    @Override
    public Option<V> get(K key, int originalHash, int hash, int shift) {
        if (this.hash() == hash) {
            int index = this.indexOf(key);
            if (index >= 0) {
                return new Some(((Tuple2)this.content().apply(index))._2());
            }
            return None$.MODULE$;
        }
        return None$.MODULE$;
    }

    @Override
    public Tuple2<K, V> getTuple(K key, int originalHash, int hash, int shift) {
        int index = this.indexOf(key);
        if (index >= 0) {
            return (Tuple2)this.content().apply(index);
        }
        throw new NoSuchElementException();
    }

    @Override
    public <V1> V1 getOrElse(K key, int originalHash, int hash, int shift, Function0<V1> f) {
        if (this.hash() == hash) {
            int n = this.indexOf(key);
            switch (n) {
                case -1: {
                    return f.apply();
                }
            }
            return (V1)((Tuple2)this.content().apply(n))._2();
        }
        return f.apply();
    }

    @Override
    public boolean containsKey(K key, int originalHash, int hash, int shift) {
        return this.hash() == hash && this.indexOf(key) >= 0;
    }

    public <V1> boolean contains(K key, V1 value, int hash, int shift) {
        int index;
        return this.hash() == hash && (index = this.indexOf(key)) >= 0 && ((Tuple2)this.content().apply(index))._2() == value;
    }

    @Override
    public <V1> MapNode<K, V1> updated(K key, V1 value, int originalHash, int hash, int shift, boolean replaceValue) {
        int index = this.indexOf(key);
        if (index >= 0) {
            if (replaceValue) {
                if (((Tuple2)this.content().apply(index))._2() == value) {
                    return this;
                }
                return new HashCollisionMapNode<K, V>(originalHash, hash, this.content().updated(index, new Tuple2<K, V1>(key, value)));
            }
            return this;
        }
        return new HashCollisionMapNode<K, V>(originalHash, hash, this.content().appended(new Tuple2<K, V1>(key, value)));
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public <V1> MapNode<K, V1> removed(K key, int originalHash, int hash, int shift) {
        if (!this.containsKey(key, originalHash, hash, shift)) {
            return this;
        }
        Vector updatedContent = (Vector)this.content().filterNot((Function1<Tuple2, Object> & Serializable)keyValuePair -> BoxesRunTime.boxToBoolean(HashCollisionMapNode.$anonfun$removed$1(key, keyValuePair)));
        if (updatedContent == null) {
            throw null;
        }
        switch (updatedContent.length()) {
            case 1: {
                void var8_8;
                void var7_7;
                Tuple2 tuple2 = (Tuple2)updatedContent.apply(false);
                if (tuple2 == null) {
                    throw new MatchError((Object)null);
                }
                Object k = tuple2._1();
                Object v = tuple2._2();
                int maskFrom_shift = 0;
                int bitposFrom_mask = hash >>> maskFrom_shift & 0x1F;
                return new BitmapIndexedMapNode(1 << bitposFrom_mask, 0, new Object[]{var7_7, var8_8}, new int[]{originalHash}, 1, hash);
            }
        }
        return new HashCollisionMapNode<K, V>(originalHash, hash, updatedContent);
    }

    @Override
    public boolean hasNodes() {
        return false;
    }

    @Override
    public int nodeArity() {
        return 0;
    }

    @Override
    public MapNode<K, V> getNode(int index) {
        throw new IndexOutOfBoundsException("No sub-nodes present in hash-collision leaf node.");
    }

    @Override
    public boolean hasPayload() {
        return true;
    }

    @Override
    public int payloadArity() {
        return this.content().length();
    }

    @Override
    public K getKey(int index) {
        return (K)((Tuple2)this.getPayload(index))._1();
    }

    @Override
    public V getValue(int index) {
        return (V)((Tuple2)this.getPayload(index))._2();
    }

    @Override
    public Tuple2<K, V> getPayload(int index) {
        return (Tuple2)this.content().apply(index);
    }

    @Override
    public int getHash(int index) {
        return this.originalHash();
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public <U> void foreach(Function1<Tuple2<K, V>, U> f) {
        Vector<Tuple2<K, V>> vector = this.content();
        if (vector == null) {
            throw null;
        }
        Vector<Tuple2<K, V>> foreach_this = vector;
        int foreach_c = foreach_this.vectorSliceCount();
        for (int foreach_i = 0; foreach_i < foreach_c; ++foreach_i) {
            int foreach_foreachRec_i;
            void foreach_foreachRec_level;
            void foreach_foreachRec_a;
            int foreach_vectorSliceDim_c = foreach_c / 2;
            Object[] objectArray = foreach_this.vectorSlice(foreach_i);
            int n = foreach_vectorSliceDim_c + 1 - Math.abs(foreach_i - foreach_vectorSliceDim_c) - 1;
            VectorStatics$ foreach_foreachRec_this = VectorStatics$.MODULE$;
            int foreach_foreachRec_len = ((void)foreach_foreachRec_a).length;
            if (foreach_foreachRec_level == false) {
                for (foreach_foreachRec_i = 0; foreach_foreachRec_i < foreach_foreachRec_len; ++foreach_foreachRec_i) {
                    f.apply((Tuple2<K, V>)foreach_foreachRec_a[foreach_foreachRec_i]);
                }
            } else {
                void foreach_foreachRec_l = foreach_foreachRec_level - true;
                while (foreach_foreachRec_i < foreach_foreachRec_len) {
                    int foreachRec_i;
                    Object[] foreachRec_a = (Object[])foreach_foreachRec_a[foreach_foreachRec_i];
                    int foreachRec_len = foreachRec_a.length;
                    if (foreach_foreachRec_l == false) {
                        for (foreachRec_i = 0; foreachRec_i < foreachRec_len; ++foreachRec_i) {
                            f.apply((Tuple2<K, V>)foreachRec_a[foreachRec_i]);
                        }
                    } else {
                        void foreachRec_l = foreach_foreachRec_l - true;
                        while (foreachRec_i < foreachRec_len) {
                            foreach_foreachRec_this.foreachRec((int)foreachRec_l, (Object[])foreachRec_a[foreachRec_i], f);
                            ++foreachRec_i;
                        }
                    }
                    Object var12_12 = null;
                    ++foreach_foreachRec_i;
                }
            }
            Object var6_6 = null;
            objectArray = null;
        }
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public <U> void foreachEntry(Function2<K, V, U> f) {
        Vector<Tuple2<K, V>> vector = this.content();
        Function1<Tuple2, Object> & Serializable intersect = (Function1<Tuple2, Object> & Serializable)x0$1 -> {
            if (x0$1 != null) {
                Object k = x0$1._1();
                Object v = x0$1._2();
                return f.apply(k, v);
            }
            throw new MatchError((Object)null);
        };
        if (vector == null) {
            throw null;
        }
        Vector<Tuple2<K, V>> foreach_this = vector;
        int foreach_c = foreach_this.vectorSliceCount();
        for (int foreach_i = 0; foreach_i < foreach_c; ++foreach_i) {
            Tuple2 tuple2;
            int foreach_foreachRec_i;
            void foreach_foreachRec_level;
            void foreach_foreachRec_a;
            int foreach_vectorSliceDim_c = foreach_c / 2;
            Object[] objectArray = foreach_this.vectorSlice(foreach_i);
            int n = foreach_vectorSliceDim_c + 1 - Math.abs(foreach_i - foreach_vectorSliceDim_c) - 1;
            VectorStatics$ foreach_foreachRec_this = VectorStatics$.MODULE$;
            int foreach_foreachRec_len = ((void)foreach_foreachRec_a).length;
            if (foreach_foreachRec_level == false) {
                for (foreach_foreachRec_i = 0; foreach_foreachRec_i < foreach_foreachRec_len; ++foreach_foreachRec_i) {
                    tuple2 = (Tuple2)foreach_foreachRec_a[foreach_foreachRec_i];
                    if (tuple2 == null) {
                        throw new MatchError((Object)null);
                    }
                    Object $anonfun$foreachEntry$1_k = tuple2._1();
                    Object $anonfun$foreachEntry$1_v = tuple2._2();
                    f.apply($anonfun$foreachEntry$1_k, $anonfun$foreachEntry$1_v);
                    Object var18_18 = null;
                    Object var19_19 = null;
                }
            } else {
                void foreach_foreachRec_l = foreach_foreachRec_level - true;
                while (foreach_foreachRec_i < foreach_foreachRec_len) {
                    int foreachRec_i;
                    Object[] foreachRec_a = (Object[])foreach_foreachRec_a[foreach_foreachRec_i];
                    int foreachRec_len = foreachRec_a.length;
                    if (foreach_foreachRec_l == false) {
                        for (foreachRec_i = 0; foreachRec_i < foreachRec_len; ++foreachRec_i) {
                            tuple2 = (Tuple2)foreachRec_a[foreachRec_i];
                            if (tuple2 == null) {
                                throw new MatchError((Object)null);
                            }
                            Object $anonfun$foreachEntry$1_k = tuple2._1();
                            Object $anonfun$foreachEntry$1_v = tuple2._2();
                            f.apply($anonfun$foreachEntry$1_k, $anonfun$foreachEntry$1_v);
                            Object var20_20 = null;
                            Object var21_21 = null;
                        }
                    } else {
                        void foreachRec_l = foreach_foreachRec_l - true;
                        while (foreachRec_i < foreachRec_len) {
                            void foreach_f;
                            foreach_foreachRec_this.foreachRec((int)foreachRec_l, (T[])((Object[])foreachRec_a[foreachRec_i]), foreach_f);
                            ++foreachRec_i;
                        }
                    }
                    Object var13_13 = null;
                    ++foreach_foreachRec_i;
                }
            }
            Object var7_7 = null;
            objectArray = null;
        }
    }

    @Override
    public void foreachWithHash(Function3<K, V, Object, BoxedUnit> f) {
        Iterator<Tuple2<K, V>> iter = this.content().iterator();
        while (iter.hasNext()) {
            Tuple2<K, V> next = iter.next();
            f.apply(next._1(), next._2(), this.originalHash());
        }
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public <W> HashCollisionMapNode<K, W> transform(Function2<K, V, W> f) {
        VectorBuilder newContent = new VectorBuilder();
        Iterator<Tuple2<K, V>> contentIter = this.content().iterator();
        boolean anyChanges = false;
        while (contentIter.hasNext()) {
            void var7_7;
            void var6_6;
            Tuple2<K, V> tuple2 = contentIter.next();
            if (tuple2 == null) {
                throw new MatchError((Object)null);
            }
            K k = tuple2._1();
            V v = tuple2._2();
            W newValue = f.apply(var6_6, var7_7);
            Tuple2<void, W> addOne_elem = new Tuple2<void, W>(var6_6, newValue);
            newContent.addOne(addOne_elem);
            Object var9_9 = null;
            anyChanges = anyChanges || var7_7 != newValue;
        }
        if (anyChanges) {
            return new HashCollisionMapNode<K, V>(this.originalHash(), this.hash(), newContent.result());
        }
        return this;
    }

    /*
     * WARNING - void declaration
     */
    public boolean equals(Object that) {
        if (that instanceof HashCollisionMapNode) {
            HashCollisionMapNode hashCollisionMapNode = (HashCollisionMapNode)that;
            if (this != hashCollisionMapNode) {
                if (this.hash() == hashCollisionMapNode.hash() && this.content().length() == hashCollisionMapNode.content().length()) {
                    Iterator<Tuple2<K, V>> iter = this.content().iterator();
                    while (iter.hasNext()) {
                        void var6_6;
                        void var5_5;
                        Tuple2<K, V> tuple2 = iter.next();
                        if (tuple2 == null) {
                            throw new MatchError((Object)null);
                        }
                        K key = tuple2._1();
                        V value = tuple2._2();
                        int index = hashCollisionMapNode.indexOf(var5_5);
                        if (index >= 0 && BoxesRunTime.equals(var6_6, ((Tuple2)hashCollisionMapNode.content().apply(index))._2())) continue;
                        return false;
                    }
                }
            } else {
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public <V1> HashCollisionMapNode<K, V1> concat(MapNode<K, V1> that, int shift) {
        if (that instanceof HashCollisionMapNode) {
            HashCollisionMapNode hashCollisionMapNode = (HashCollisionMapNode)that;
            if (hashCollisionMapNode == this) {
                return this;
            }
            VectorBuilder newContent = null;
            Iterator<Tuple2<K, V>> iter = this.content().iterator();
            while (iter.hasNext()) {
                Tuple2<K, V> nextPayload = iter.next();
                if (hashCollisionMapNode.indexOf(nextPayload._1()) >= 0) continue;
                if (newContent == null) {
                    newContent = new VectorBuilder();
                    newContent.addAll((IterableOnce)hashCollisionMapNode.content());
                }
                newContent.addOne(nextPayload);
            }
            if (newContent == null) {
                return hashCollisionMapNode;
            }
            return new HashCollisionMapNode<K, V>(this.originalHash(), this.hash(), newContent.result());
        }
        if (that instanceof BitmapIndexedMapNode) {
            throw new UnsupportedOperationException("Cannot concatenate a HashCollisionMapNode with a BitmapIndexedMapNode");
        }
        throw new MatchError(that);
    }

    @Override
    public <V1> void mergeInto(MapNode<K, V1> that, HashMapBuilder<K, V1> builder, int shift, Function2<Tuple2<K, V>, Tuple2<K, V1>, Tuple2<K, V1>> mergef) {
        if (that instanceof HashCollisionMapNode) {
            HashCollisionMapNode hashCollisionMapNode = (HashCollisionMapNode)that;
            Iterator<Tuple2<K, V>> iter = this.content().iterator();
            Object[] rightArray = (Object[])hashCollisionMapNode.content().toArray(ClassTag$.MODULE$.AnyRef());
            while (iter.hasNext()) {
                Tuple2<K, V> nextPayload = iter.next();
                int index = HashCollisionMapNode.rightIndexOf$1(nextPayload._1(), rightArray);
                if (index == -1) {
                    builder.addOne(nextPayload);
                    continue;
                }
                Tuple2 rightPayload = (Tuple2)rightArray[index];
                rightArray[index] = null;
                builder.addOne(mergef.apply(nextPayload, rightPayload));
            }
            for (int i = 0; i < rightArray.length; ++i) {
                Object elem = rightArray[i];
                if (elem == null) continue;
                builder.addOne((Tuple2)elem);
            }
            return;
        }
        if (that instanceof BitmapIndexedMapNode) {
            throw new Exception("Cannot merge HashCollisionMapNode with BitmapIndexedMapNode");
        }
        throw new MatchError(that);
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public <V1> void buildTo(HashMapBuilder<K, V1> builder) {
        Iterator<Tuple2<K, V>> iter = this.content().iterator();
        while (iter.hasNext()) {
            void var5_5;
            void var4_4;
            Tuple2<K, V> tuple2 = iter.next();
            if (tuple2 == null) {
                throw new MatchError((Object)null);
            }
            K k = tuple2._1();
            V v = tuple2._2();
            builder.addOne(var4_4, var5_5, this.originalHash(), this.hash());
        }
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public MapNode<K, V> filterImpl(Function1<Tuple2<K, V>, Object> pred, boolean flipped) {
        Object var30_31;
        Object var35_35;
        Object[] objectArray;
        Function1<Object, Object> & Serializable intersect;
        Object object;
        Object var19_12;
        Object var24_16;
        Object[] objectArray2;
        Function1<Object, Object> & Serializable intersect2;
        block35: {
            Vector0$ vector0$ = this.content();
            if (vector0$ == null) {
                throw null;
            }
            Vector0$ filterImpl_this = vector0$;
            int filterImpl_len = filterImpl_this.prefix1().length;
            for (int filterImpl_i = 0; filterImpl_i != filterImpl_len; ++filterImpl_i) {
                if (BoxesRunTime.unboxToBoolean(pred.apply((Tuple2<K, V>)filterImpl_this.prefix1()[filterImpl_i])) != flipped) continue;
                int filterImpl_bitmap = 0;
                for (int filterImpl_j = filterImpl_i + 1; filterImpl_j < filterImpl_len; ++filterImpl_j) {
                    if (BoxesRunTime.unboxToBoolean(pred.apply((Tuple2<K, V>)filterImpl_this.prefix1()[filterImpl_j])) == flipped) continue;
                    filterImpl_bitmap |= 1 << filterImpl_j;
                }
                int filterImpl_newLen = filterImpl_i + Integer.bitCount(filterImpl_bitmap);
                if (filterImpl_this instanceof BigVector) {
                    int filterImpl_k;
                    VectorBuilder filterImpl_b = new VectorBuilder();
                    for (filterImpl_k = 0; filterImpl_k < filterImpl_i; ++filterImpl_k) {
                        filterImpl_b.addOne(filterImpl_this.prefix1()[filterImpl_k]);
                    }
                    filterImpl_k = filterImpl_i + 1;
                    while (filterImpl_i != filterImpl_newLen) {
                        if ((1 << filterImpl_k & filterImpl_bitmap) != 0) {
                            filterImpl_b.addOne(filterImpl_this.prefix1()[filterImpl_k]);
                            ++filterImpl_i;
                        }
                        ++filterImpl_k;
                    }
                    intersect2 = arg_0 -> Vector.$anonfun$filterImpl$1(pred, flipped, filterImpl_b, arg_0);
                    BigVector filterImpl_foreachRest_this = filterImpl_this;
                    int filterImpl_foreachRest_c = filterImpl_foreachRest_this.vectorSliceCount();
                    for (int filterImpl_foreachRest_i = 1; filterImpl_foreachRest_i < filterImpl_foreachRest_c; ++filterImpl_foreachRest_i) {
                        Object object2;
                        int filterImpl_foreachRest_foreachRec_i;
                        void filterImpl_foreachRest_foreachRec_level;
                        void filterImpl_foreachRest_foreachRec_a;
                        int filterImpl_foreachRest_vectorSliceDim_c = filterImpl_foreachRest_c / 2;
                        objectArray2 = filterImpl_foreachRest_this.vectorSlice(filterImpl_foreachRest_i);
                        int n = filterImpl_foreachRest_vectorSliceDim_c + 1 - Math.abs(filterImpl_foreachRest_i - filterImpl_foreachRest_vectorSliceDim_c) - 1;
                        VectorStatics$ filterImpl_foreachRest_foreachRec_this = VectorStatics$.MODULE$;
                        int filterImpl_foreachRest_foreachRec_len = ((void)filterImpl_foreachRest_foreachRec_a).length;
                        if (filterImpl_foreachRest_foreachRec_level == false) {
                            for (filterImpl_foreachRest_foreachRec_i = 0; filterImpl_foreachRest_foreachRec_i < filterImpl_foreachRest_foreachRec_len; ++filterImpl_foreachRest_foreachRec_i) {
                                object2 = filterImpl_foreachRest_foreachRec_a[filterImpl_foreachRest_foreachRec_i];
                                if (BoxesRunTime.unboxToBoolean(pred.apply((Tuple2<K, V>)object2)) == flipped) continue;
                                filterImpl_b.addOne(object2);
                            }
                        } else {
                            void filterImpl_foreachRest_foreachRec_l = filterImpl_foreachRest_foreachRec_level - true;
                            while (filterImpl_foreachRest_foreachRec_i < filterImpl_foreachRest_foreachRec_len) {
                                int foreachRec_i;
                                Object[] foreachRec_a = (Object[])filterImpl_foreachRest_foreachRec_a[filterImpl_foreachRest_foreachRec_i];
                                int foreachRec_len = foreachRec_a.length;
                                if (filterImpl_foreachRest_foreachRec_l == false) {
                                    for (foreachRec_i = 0; foreachRec_i < foreachRec_len; ++foreachRec_i) {
                                        object2 = foreachRec_a[foreachRec_i];
                                        if (BoxesRunTime.unboxToBoolean(pred.apply((Tuple2<K, V>)object2)) == flipped) continue;
                                        filterImpl_b.addOne(object2);
                                    }
                                } else {
                                    void foreachRec_l = filterImpl_foreachRest_foreachRec_l - true;
                                    while (foreachRec_i < foreachRec_len) {
                                        void filterImpl_foreachRest_f;
                                        filterImpl_foreachRest_foreachRec_this.foreachRec((int)foreachRec_l, (T[])((Object[])foreachRec_a[foreachRec_i]), filterImpl_foreachRest_f);
                                        ++foreachRec_i;
                                    }
                                }
                                Object var42_22 = null;
                                ++filterImpl_foreachRest_foreachRec_i;
                            }
                        }
                        var24_16 = null;
                        objectArray2 = null;
                    }
                    var19_12 = null;
                    intersect2 = null;
                    var24_16 = null;
                    objectArray2 = null;
                    object = filterImpl_b.result();
                    break block35;
                }
                if (filterImpl_newLen == 0) {
                    object = Vector0$.MODULE$;
                } else {
                    Object[] filterImpl_newData = new Object[filterImpl_newLen];
                    System.arraycopy(filterImpl_this.prefix1(), 0, filterImpl_newData, 0, filterImpl_i);
                    int filterImpl_k = filterImpl_i + 1;
                    while (filterImpl_i != filterImpl_newLen) {
                        if ((1 << filterImpl_k & filterImpl_bitmap) != 0) {
                            filterImpl_newData[filterImpl_i] = filterImpl_this.prefix1()[filterImpl_k];
                            ++filterImpl_i;
                        }
                        ++filterImpl_k;
                    }
                    object = new Vector1(filterImpl_newData);
                }
                break block35;
            }
            if (filterImpl_this instanceof BigVector) {
                VectorBuilder filterImpl_b = new VectorBuilder();
                filterImpl_b.initFrom(filterImpl_this.prefix1());
                intersect = arg_0 -> Vector.$anonfun$filterImpl$2(pred, flipped, filterImpl_b, arg_0);
                BigVector filterImpl_foreachRest_this = filterImpl_this;
                int filterImpl_foreachRest_c = filterImpl_foreachRest_this.vectorSliceCount();
                for (int filterImpl_foreachRest_i = 1; filterImpl_foreachRest_i < filterImpl_foreachRest_c; ++filterImpl_foreachRest_i) {
                    Object object3;
                    int filterImpl_foreachRest_foreachRec_i;
                    void filterImpl_foreachRest_foreachRec_level;
                    void filterImpl_foreachRest_foreachRec_a;
                    int filterImpl_foreachRest_vectorSliceDim_c = filterImpl_foreachRest_c / 2;
                    objectArray = filterImpl_foreachRest_this.vectorSlice(filterImpl_foreachRest_i);
                    int n = filterImpl_foreachRest_vectorSliceDim_c + 1 - Math.abs(filterImpl_foreachRest_i - filterImpl_foreachRest_vectorSliceDim_c) - 1;
                    VectorStatics$ filterImpl_foreachRest_foreachRec_this = VectorStatics$.MODULE$;
                    int filterImpl_foreachRest_foreachRec_len = ((void)filterImpl_foreachRest_foreachRec_a).length;
                    if (filterImpl_foreachRest_foreachRec_level == false) {
                        for (filterImpl_foreachRest_foreachRec_i = 0; filterImpl_foreachRest_foreachRec_i < filterImpl_foreachRest_foreachRec_len; ++filterImpl_foreachRest_foreachRec_i) {
                            object3 = filterImpl_foreachRest_foreachRec_a[filterImpl_foreachRest_foreachRec_i];
                            if (BoxesRunTime.unboxToBoolean(pred.apply((Tuple2<K, V>)object3)) == flipped) continue;
                            filterImpl_b.addOne(object3);
                        }
                    } else {
                        void filterImpl_foreachRest_foreachRec_l = filterImpl_foreachRest_foreachRec_level - true;
                        while (filterImpl_foreachRest_foreachRec_i < filterImpl_foreachRest_foreachRec_len) {
                            int foreachRec_i;
                            Object[] foreachRec_a = (Object[])filterImpl_foreachRest_foreachRec_a[filterImpl_foreachRest_foreachRec_i];
                            int foreachRec_len = foreachRec_a.length;
                            if (filterImpl_foreachRest_foreachRec_l == false) {
                                for (foreachRec_i = 0; foreachRec_i < foreachRec_len; ++foreachRec_i) {
                                    object3 = foreachRec_a[foreachRec_i];
                                    if (BoxesRunTime.unboxToBoolean(pred.apply((Tuple2<K, V>)object3)) == flipped) continue;
                                    filterImpl_b.addOne(object3);
                                }
                            } else {
                                void foreachRec_l = filterImpl_foreachRest_foreachRec_l - true;
                                while (foreachRec_i < foreachRec_len) {
                                    void filterImpl_foreachRest_f;
                                    filterImpl_foreachRest_foreachRec_this.foreachRec((int)foreachRec_l, (T[])((Object[])foreachRec_a[foreachRec_i]), filterImpl_foreachRest_f);
                                    ++foreachRec_i;
                                }
                            }
                            Object var46_41 = null;
                            ++filterImpl_foreachRest_foreachRec_i;
                        }
                    }
                    var35_35 = null;
                    objectArray = null;
                }
                var30_31 = null;
                intersect = null;
                var35_35 = null;
                objectArray = null;
                object = filterImpl_b.result();
            } else {
                object = filterImpl_this;
            }
        }
        Object var8_3 = null;
        Object var14_9 = null;
        Object var16_27 = null;
        Object var18_29 = null;
        var19_12 = null;
        intersect2 = null;
        var24_16 = null;
        objectArray2 = null;
        var30_31 = null;
        intersect = null;
        var35_35 = null;
        objectArray = null;
        Vector0$ newContent = object;
        int newContentLength = newContent.length();
        if (newContentLength == 0) {
            return MapNode$.MODULE$.empty();
        }
        if (newContentLength == 1) {
            void var7_50;
            void var6_49;
            Tuple2 tuple2 = (Tuple2)newContent.head();
            if (tuple2 == null) {
                throw new MatchError((Object)null);
            }
            Object k = tuple2._1();
            Object v = tuple2._2();
            int bitposFrom_mask = this.hash() >>> 0 & 0x1F;
            return new BitmapIndexedMapNode(1 << bitposFrom_mask, 0, new Object[]{var6_49, var7_50}, new int[]{this.originalHash()}, 1, this.hash());
        }
        if (newContentLength == this.content().length()) {
            return this;
        }
        return new HashCollisionMapNode<K, V>(this.originalHash(), this.hash(), newContent);
    }

    @Override
    public HashCollisionMapNode<K, V> copy() {
        return new HashCollisionMapNode<K, V>(this.originalHash(), this.hash(), this.content());
    }

    public int hashCode() {
        throw new UnsupportedOperationException("Trie nodes do not support hashing.");
    }

    @Override
    public int cachedJavaKeySetHashCode() {
        return this.size() * this.hash();
    }

    public static final /* synthetic */ Nothing$ $anonfun$apply$1() {
        throw new NoSuchElementException();
    }

    public static final /* synthetic */ boolean $anonfun$removed$1(Object key$1, Tuple2 keyValuePair) {
        return BoxesRunTime.equals(keyValuePair._1(), key$1);
    }

    private static final int rightIndexOf$1(Object key, Object[] rightArray$1) {
        for (int i = 0; i < rightArray$1.length; ++i) {
            Object elem = rightArray$1[i];
            if (elem == null || !BoxesRunTime.equals(((Tuple2)elem)._1(), key)) continue;
            return i;
        }
        return -1;
    }

    public HashCollisionMapNode(int originalHash, int hash, Vector<Tuple2<K, V>> content) {
        this.originalHash = originalHash;
        this.hash = hash;
        this.content = content;
        Predef$.MODULE$.require(this.content().length() >= 2);
        Statics.releaseFence();
    }
}

