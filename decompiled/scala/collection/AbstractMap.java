/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import java.util.NoSuchElementException;
import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.Option;
import scala.PartialFunction;
import scala.Tuple2;
import scala.collection.AbstractIterable;
import scala.collection.Iterable;
import scala.collection.IterableOnce;
import scala.collection.IterableOps;
import scala.collection.Iterator;
import scala.collection.Map;
import scala.collection.MapFactory;
import scala.collection.MapFactoryDefaults;
import scala.collection.MapOps;
import scala.collection.MapView;
import scala.collection.Set;
import scala.collection.Stepper;
import scala.collection.StepperShape;
import scala.collection.immutable.Seq;
import scala.collection.mutable.Builder;
import scala.collection.mutable.StringBuilder;
import scala.reflect.ScalaSignature;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@ScalaSignature(bytes="\u0006\u0005\u00192QAA\u0002\u0002\u0002!AQa\t\u0001\u0005\u0002\u0011\u00121\"\u00112tiJ\f7\r^'ba*\u0011A!B\u0001\u000bG>dG.Z2uS>t'\"\u0001\u0004\u0002\u000bM\u001c\u0017\r\\1\u0004\u0001U\u0019\u0011\u0002\u0006\u0010\u0014\u0007\u0001Q\u0001\u0005E\u0002\f\u00199i\u0011aA\u0005\u0003\u001b\r\u0011\u0001#\u00112tiJ\f7\r^%uKJ\f'\r\\3\u0011\t=\u0001\"#H\u0007\u0002\u000b%\u0011\u0011#\u0002\u0002\u0007)V\u0004H.\u001a\u001a\u0011\u0005M!B\u0002\u0001\u0003\u0006+\u0001\u0011\rA\u0006\u0002\u0002\u0017F\u0011qC\u0007\t\u0003\u001faI!!G\u0003\u0003\u000f9{G\u000f[5oOB\u0011qbG\u0005\u00039\u0015\u00111!\u00118z!\t\u0019b\u0004\u0002\u0004 \u0001\u0011\u0015\rA\u0006\u0002\u0002-B!1\"\t\n\u001e\u0013\t\u00113AA\u0002NCB\fa\u0001P5oSRtD#A\u0013\u0011\t-\u0001!#\b")
public abstract class AbstractMap<K, V>
extends AbstractIterable<Tuple2<K, V>>
implements Map<K, V> {
    @Override
    public MapFactory<Map> mapFactory() {
        return Map.mapFactory$(this);
    }

    @Override
    public boolean canEqual(Object that) {
        return Map.canEqual$(this, that);
    }

    @Override
    public boolean equals(Object o) {
        return Map.equals$(this, o);
    }

    @Override
    public int hashCode() {
        return Map.hashCode$(this);
    }

    @Override
    public String stringPrefix() {
        return Map.stringPrefix$(this);
    }

    @Override
    public String toString() {
        return Map.toString$(this);
    }

    @Override
    public IterableOps fromSpecific(IterableOnce coll) {
        return MapFactoryDefaults.fromSpecific$(this, coll);
    }

    @Override
    public Builder<Tuple2<K, V>, Map<K, V>> newSpecificBuilder() {
        return MapFactoryDefaults.newSpecificBuilder$(this);
    }

    @Override
    public IterableOps empty() {
        return MapFactoryDefaults.empty$(this);
    }

    @Override
    public MapOps.WithFilter<K, V, Iterable, Map> withFilter(Function1<Tuple2<K, V>, Object> p) {
        return MapFactoryDefaults.withFilter$(this, p);
    }

    @Override
    public MapView<K, V> view() {
        return MapOps.view$(this);
    }

    @Override
    public <S extends Stepper<?>> S keyStepper(StepperShape<K, S> shape) {
        return (S)MapOps.keyStepper$(this, shape);
    }

    @Override
    public <S extends Stepper<?>> S valueStepper(StepperShape<V, S> shape) {
        return (S)MapOps.valueStepper$(this, shape);
    }

    @Override
    public final IterableOps mapFromIterable(Iterable it) {
        return MapOps.mapFromIterable$(this, it);
    }

    @Override
    public <V1> V1 getOrElse(K key, Function0<V1> function0) {
        return (V1)MapOps.getOrElse$(this, key, function0);
    }

    @Override
    public V apply(K key) throws NoSuchElementException {
        return (V)MapOps.apply$(this, key);
    }

    @Override
    public <K1 extends K, V1> V1 applyOrElse(K1 x, Function1<K1, V1> function1) {
        return (V1)MapOps.applyOrElse$(this, x, function1);
    }

    @Override
    public Set<K> keySet() {
        return MapOps.keySet$(this);
    }

    @Override
    public Iterable<K> keys() {
        return MapOps.keys$(this);
    }

    @Override
    public Iterable<V> values() {
        return MapOps.values$(this);
    }

    @Override
    public Iterator<K> keysIterator() {
        return MapOps.keysIterator$(this);
    }

    @Override
    public Iterator<V> valuesIterator() {
        return MapOps.valuesIterator$(this);
    }

    @Override
    public <U> void foreachEntry(Function2<K, V, U> f) {
        MapOps.foreachEntry$(this, f);
    }

    @Override
    public MapView<K, V> filterKeys(Function1<K, Object> p) {
        return MapOps.filterKeys$(this, p);
    }

    @Override
    public <W> MapView<K, W> mapValues(Function1<V, W> f) {
        return MapOps.mapValues$(this, f);
    }

    @Override
    public V default(K key) throws NoSuchElementException {
        return (V)MapOps.default$(this, key);
    }

    @Override
    public boolean contains(K key) {
        return MapOps.contains$(this, key);
    }

    @Override
    public boolean isDefinedAt(K key) {
        return MapOps.isDefinedAt$(this, key);
    }

    @Override
    public IterableOps map(Function1 f) {
        return MapOps.map$(this, f);
    }

    @Override
    public IterableOps collect(PartialFunction pf) {
        return MapOps.collect$(this, pf);
    }

    @Override
    public IterableOps flatMap(Function1 f) {
        return MapOps.flatMap$(this, f);
    }

    @Override
    public IterableOps concat(IterableOnce suffix) {
        return MapOps.concat$(this, suffix);
    }

    @Override
    public IterableOps $plus$plus(IterableOnce xs) {
        return MapOps.$plus$plus$(this, xs);
    }

    @Override
    public StringBuilder addString(StringBuilder sb, String start2, String sep, String end) {
        return MapOps.addString$(this, sb, start2, sep, end);
    }

    @Override
    public IterableOps $plus(Tuple2 kv) {
        return MapOps.$plus$(this, kv);
    }

    @Override
    public IterableOps $plus(Tuple2 elem1, Tuple2 elem2, Seq elems) {
        return MapOps.$plus$(this, elem1, elem2, elems);
    }

    @Override
    public Object $minus$minus(IterableOnce keys2) {
        return MapOps.$minus$minus$(this, keys2);
    }

    @Override
    public IterableOps $plus$plus$colon(IterableOnce that) {
        return MapOps.$plus$plus$colon$(this, that);
    }

    @Override
    public Option<V> unapply(K a) {
        return PartialFunction.unapply$(this, a);
    }

    @Override
    public PartialFunction<K, V> elementWise() {
        return PartialFunction.elementWise$(this);
    }

    @Override
    public <A1 extends K, B1> PartialFunction<A1, B1> orElse(PartialFunction<A1, B1> that) {
        return PartialFunction.orElse$(this, that);
    }

    @Override
    public <C$> PartialFunction<K, C$> andThen(Function1<V, C$> k) {
        return PartialFunction.andThen$((PartialFunction)this, k);
    }

    @Override
    public <C$> PartialFunction<K, C$> andThen(PartialFunction<V, C$> k) {
        return PartialFunction.andThen$((PartialFunction)this, k);
    }

    @Override
    public <R$> PartialFunction<R$, V> compose(PartialFunction<R$, K> k) {
        return PartialFunction.compose$(this, k);
    }

    @Override
    public Function1<K, Option<V>> lift() {
        return PartialFunction.lift$(this);
    }

    @Override
    public <U> Function1<K, Object> runWith(Function1<V, U> action) {
        return PartialFunction.runWith$(this, action);
    }

    @Override
    public boolean apply$mcZD$sp(double v1) {
        return Function1.apply$mcZD$sp$(this, v1);
    }

    @Override
    public double apply$mcDD$sp(double v1) {
        return Function1.apply$mcDD$sp$(this, v1);
    }

    @Override
    public float apply$mcFD$sp(double v1) {
        return Function1.apply$mcFD$sp$(this, v1);
    }

    @Override
    public int apply$mcID$sp(double v1) {
        return Function1.apply$mcID$sp$(this, v1);
    }

    @Override
    public long apply$mcJD$sp(double v1) {
        return Function1.apply$mcJD$sp$(this, v1);
    }

    @Override
    public void apply$mcVD$sp(double v1) {
        Function1.apply$mcVD$sp$(this, v1);
    }

    @Override
    public boolean apply$mcZF$sp(float v1) {
        return Function1.apply$mcZF$sp$(this, v1);
    }

    @Override
    public double apply$mcDF$sp(float v1) {
        return Function1.apply$mcDF$sp$(this, v1);
    }

    @Override
    public float apply$mcFF$sp(float v1) {
        return Function1.apply$mcFF$sp$(this, v1);
    }

    @Override
    public int apply$mcIF$sp(float v1) {
        return Function1.apply$mcIF$sp$(this, v1);
    }

    @Override
    public long apply$mcJF$sp(float v1) {
        return Function1.apply$mcJF$sp$(this, v1);
    }

    @Override
    public void apply$mcVF$sp(float v1) {
        Function1.apply$mcVF$sp$(this, v1);
    }

    @Override
    public boolean apply$mcZI$sp(int v1) {
        return Function1.apply$mcZI$sp$(this, v1);
    }

    @Override
    public double apply$mcDI$sp(int v1) {
        return Function1.apply$mcDI$sp$(this, v1);
    }

    @Override
    public float apply$mcFI$sp(int v1) {
        return Function1.apply$mcFI$sp$(this, v1);
    }

    @Override
    public int apply$mcII$sp(int v1) {
        return Function1.apply$mcII$sp$(this, v1);
    }

    @Override
    public long apply$mcJI$sp(int v1) {
        return Function1.apply$mcJI$sp$(this, v1);
    }

    @Override
    public void apply$mcVI$sp(int v1) {
        Function1.apply$mcVI$sp$(this, v1);
    }

    @Override
    public boolean apply$mcZJ$sp(long v1) {
        return Function1.apply$mcZJ$sp$(this, v1);
    }

    @Override
    public double apply$mcDJ$sp(long v1) {
        return Function1.apply$mcDJ$sp$(this, v1);
    }

    @Override
    public float apply$mcFJ$sp(long v1) {
        return Function1.apply$mcFJ$sp$(this, v1);
    }

    @Override
    public int apply$mcIJ$sp(long v1) {
        return Function1.apply$mcIJ$sp$(this, v1);
    }

    @Override
    public long apply$mcJJ$sp(long v1) {
        return Function1.apply$mcJJ$sp$(this, v1);
    }

    @Override
    public void apply$mcVJ$sp(long v1) {
        Function1.apply$mcVJ$sp$(this, v1);
    }

    @Override
    public <A$> Function1<A$, V> compose(Function1<A$, K> g2) {
        return Function1.compose$(this, g2);
    }
}

