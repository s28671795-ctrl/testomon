/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.immutable;

import java.io.Serializable;
import scala.Function0;
import scala.Function1;
import scala.Tuple2;
import scala.collection.Iterable;
import scala.collection.IterableOnce;
import scala.collection.immutable.HashMapBuilder;
import scala.collection.immutable.Map;
import scala.collection.immutable.Map$EmptyMap$;
import scala.collection.immutable.Seq;
import scala.collection.mutable.Builder;
import scala.collection.mutable.Growable;
import scala.collection.mutable.ReusableBuilder;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\t4Qa\u0003\u0007\u0003\u0019IAQ\u0001\u000e\u0001\u0005\u0002UBaa\u000e\u0001!B\u0013\u0001\u0004B\u0002\u001d\u0001A\u0003&\u0011\bC\u0005=\u0001\u0001\u0007\t\u0011)Q\u0005{!1\u0001\t\u0001C\u0001\u0019\u0005CQa\u0013\u0001\u0005B1CQ\u0001\u0015\u0001\u0005BECQA\u0015\u0001\u0005\u0002MCQA\u0015\u0001\u0005\u0002]CQA\u0017\u0001\u0005Bm\u0013a\"T1q\u0005VLG\u000eZ3s\u00136\u0004HN\u0003\u0002\u000e\u001d\u0005I\u0011.\\7vi\u0006\u0014G.\u001a\u0006\u0003\u001fA\t!bY8mY\u0016\u001cG/[8o\u0015\u0005\t\u0012!B:dC2\fWcA\n$]M\u0019\u0001\u0001\u0006\r\u0011\u0005U1R\"\u0001\t\n\u0005]\u0001\"AB!osJ+g\r\u0005\u0003\u001a9y\u0001T\"\u0001\u000e\u000b\u0005mq\u0011aB7vi\u0006\u0014G.Z\u0005\u0003;i\u0011qBU3vg\u0006\u0014G.\u001a\"vS2$WM\u001d\t\u0005+}\tS&\u0003\u0002!!\t1A+\u001e9mKJ\u0002\"AI\u0012\r\u0001\u0011)A\u0005\u0001b\u0001M\t\t1j\u0001\u0001\u0012\u0005\u001dR\u0003CA\u000b)\u0013\tI\u0003CA\u0004O_RD\u0017N\\4\u0011\u0005UY\u0013B\u0001\u0017\u0011\u0005\r\te.\u001f\t\u0003E9\"Qa\f\u0001C\u0002\u0019\u0012\u0011A\u0016\t\u0005cI\nS&D\u0001\r\u0013\t\u0019DBA\u0002NCB\fa\u0001P5oSRtD#\u0001\u001c\u0011\tE\u0002\u0011%L\u0001\u0006K2,Wn]\u0001\u0019g^LGo\u00195fIR{\u0007*Y:i\u001b\u0006\u0004()^5mI\u0016\u0014\bCA\u000b;\u0013\tY\u0004CA\u0004C_>dW-\u00198\u0002\u001d!\f7\u000f['ba\n+\u0018\u000e\u001c3feB!\u0011GP\u0011.\u0013\tyDB\u0001\bICNDW*\u00199Ck&dG-\u001a:\u0002\u0013\u001d,Go\u0014:FYN,WC\u0001\"E)\r\u0019u)\u0013\t\u0003E\u0011#Q!R\u0003C\u0002\u0019\u0013!A\u0016\u0019\u0012\u00055R\u0003\"\u0002%\u0006\u0001\u0004\t\u0013aA6fs\")!*\u0002a\u0001\u0007\u0006)a/\u00197vK\u0006)1\r\\3beR\tQ\n\u0005\u0002\u0016\u001d&\u0011q\n\u0005\u0002\u0005+:LG/\u0001\u0004sKN,H\u000e\u001e\u000b\u0002a\u00051\u0011\r\u001a3P]\u0016$2\u0001V+W\u001b\u0005\u0001\u0001\"\u0002%\t\u0001\u0004\t\u0003\"\u0002&\t\u0001\u0004iCC\u0001+Y\u0011\u0015I\u0016\u00021\u0001\u001f\u0003\u0011)G.Z7\u0002\r\u0005$G-\u00117m)\t!F\fC\u0003^\u0015\u0001\u0007a,\u0001\u0002ygB\u0019q\f\u0019\u0010\u000e\u00039I!!\u0019\b\u0003\u0019%#XM]1cY\u0016|enY3")
public final class MapBuilderImpl<K, V>
implements ReusableBuilder<Tuple2<K, V>, Map<K, V>> {
    private Map<K, V> elems = Map$EmptyMap$.MODULE$;
    private boolean switchedToHashMapBuilder = false;
    private HashMapBuilder<K, V> hashMapBuilder;

    @Override
    public void sizeHint(int size) {
        Builder.sizeHint$(this, size);
    }

    @Override
    public final void sizeHint(IterableOnce<?> coll, int delta) {
        Builder.sizeHint$(this, coll, delta);
    }

    @Override
    public final int sizeHint$default$2() {
        return Builder.sizeHint$default$2$(this);
    }

    @Override
    public final void sizeHintBounded(int size, Iterable<?> boundingColl) {
        Builder.sizeHintBounded$(this, size, boundingColl);
    }

    @Override
    public <NewTo> Builder<Tuple2<K, V>, NewTo> mapResult(Function1<Map<K, V>, NewTo> f) {
        return Builder.mapResult$(this, f);
    }

    @Override
    public final Growable $plus$eq(Object elem) {
        return Growable.$plus$eq$(this, elem);
    }

    @Override
    public final Growable $plus$eq(Object elem1, Object elem2, Seq elems) {
        return Growable.$plus$eq$(this, elem1, elem2, elems);
    }

    @Override
    public final Growable<Tuple2<K, V>> $plus$plus$eq(IterableOnce<Tuple2<K, V>> xs) {
        return Growable.$plus$plus$eq$(this, xs);
    }

    @Override
    public int knownSize() {
        return Growable.knownSize$(this);
    }

    public <V0> V0 getOrElse(K key, V0 value) {
        if (this.hashMapBuilder != null) {
            return this.hashMapBuilder.getOrElse(key, value);
        }
        return (V0)this.elems.getOrElse(key, (Function0<Object> & Serializable)() -> value);
    }

    @Override
    public void clear() {
        this.elems = Map$EmptyMap$.MODULE$;
        if (this.hashMapBuilder != null) {
            this.hashMapBuilder.clear();
        }
        this.switchedToHashMapBuilder = false;
    }

    @Override
    public Map<K, V> result() {
        if (this.switchedToHashMapBuilder) {
            return this.hashMapBuilder.result();
        }
        return this.elems;
    }

    public MapBuilderImpl<K, V> addOne(K key, V value) {
        if (this.switchedToHashMapBuilder) {
            this.hashMapBuilder.addOne(key, value);
        } else if (this.elems.size() < 4) {
            this.elems = (Map)this.elems.updated(key, value);
        } else if (this.elems.contains(key)) {
            this.elems = (Map)this.elems.updated(key, value);
        } else {
            this.switchedToHashMapBuilder = true;
            if (this.hashMapBuilder == null) {
                this.hashMapBuilder = new HashMapBuilder();
            }
            ((Map.Map4)this.elems).buildTo(this.hashMapBuilder);
            this.hashMapBuilder.addOne(key, value);
        }
        return this;
    }

    public MapBuilderImpl<K, V> addOne(Tuple2<K, V> elem) {
        return this.addOne(elem._1(), elem._2());
    }

    public MapBuilderImpl<K, V> addAll(IterableOnce<Tuple2<K, V>> xs) {
        if (this.switchedToHashMapBuilder) {
            this.hashMapBuilder.addAll((IterableOnce)xs);
            return this;
        }
        return (MapBuilderImpl)Growable.addAll$(this, xs);
    }
}

