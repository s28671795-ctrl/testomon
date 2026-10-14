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
import scala.collection.AbstractView;
import scala.collection.Iterable;
import scala.collection.IterableOnce;
import scala.collection.IterableOps;
import scala.collection.Iterator;
import scala.collection.MapOps;
import scala.collection.MapView;
import scala.collection.MapViewFactory;
import scala.collection.Set;
import scala.collection.Stepper;
import scala.collection.StepperShape;
import scala.collection.View;
import scala.collection.immutable.Seq;
import scala.collection.mutable.StringBuilder;
import scala.reflect.ScalaSignature;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@ScalaSignature(bytes="\u0006\u000512QAA\u0002\u0002\u0002!AQa\t\u0001\u0005\u0002\u0011\u0012q\"\u00112tiJ\f7\r^'baZKWm\u001e\u0006\u0003\t\u0015\t!bY8mY\u0016\u001cG/[8o\u0015\u00051\u0011!B:dC2\f7\u0001A\u000b\u0004\u0013Qq2c\u0001\u0001\u000bAA\u00191\u0002\u0004\b\u000e\u0003\rI!!D\u0002\u0003\u0019\u0005\u00137\u000f\u001e:bGR4\u0016.Z<\u0011\t=\u0001\"#H\u0007\u0002\u000b%\u0011\u0011#\u0002\u0002\u0007)V\u0004H.\u001a\u001a\u0011\u0005M!B\u0002\u0001\u0003\u0006+\u0001\u0011\rA\u0006\u0002\u0002\u0017F\u0011qC\u0007\t\u0003\u001faI!!G\u0003\u0003\u000f9{G\u000f[5oOB\u0011qbG\u0005\u00039\u0015\u00111!\u00118z!\t\u0019b\u0004\u0002\u0004 \u0001\u0011\u0015\rA\u0006\u0002\u0002-B!1\"\t\n\u001e\u0013\t\u00113AA\u0004NCB4\u0016.Z<\u0002\rqJg.\u001b;?)\u0005)\u0003\u0003B\u0006\u0001%uAC\u0001A\u0014+WA\u0011q\u0002K\u0005\u0003S\u0015\u0011\u0001cU3sS\u0006dg+\u001a:tS>tW+\u0013#\u0002\u000bY\fG.^3\u001f\u0003\r\u0001")
public abstract class AbstractMapView<K, V>
extends AbstractView<Tuple2<K, V>>
implements MapView<K, V> {
    private static final long serialVersionUID = 3L;

    @Override
    public MapView<K, V> view() {
        return MapView.view$(this);
    }

    @Override
    public Iterable<K> keys() {
        return MapView.keys$(this);
    }

    @Override
    public Iterable<V> values() {
        return MapView.values$(this);
    }

    @Override
    public MapView<K, V> filterKeys(Function1<K, Object> p) {
        return MapView.filterKeys$(this, p);
    }

    @Override
    public <W> MapView<K, W> mapValues(Function1<V, W> f) {
        return MapView.mapValues$(this, f);
    }

    @Override
    public MapView<K, V> filter(Function1<Tuple2<K, V>, Object> pred) {
        return MapView.filter$(this, pred);
    }

    @Override
    public MapView<K, V> filterNot(Function1<Tuple2<K, V>, Object> pred) {
        return MapView.filterNot$(this, pred);
    }

    @Override
    public Tuple2<MapView<K, V>, MapView<K, V>> partition(Function1<Tuple2<K, V>, Object> p) {
        return MapView.partition$(this, p);
    }

    @Override
    public <U> MapView<K, V> tapEach(Function1<Tuple2<K, V>, U> f) {
        return MapView.tapEach$(this, f);
    }

    @Override
    public MapViewFactory mapFactory() {
        return MapView.mapFactory$(this);
    }

    @Override
    public MapView<K, V> empty() {
        return MapView.empty$(this);
    }

    @Override
    public MapOps.WithFilter<K, V, View, ?> withFilter(Function1<Tuple2<K, V>, Object> p) {
        return MapView.withFilter$(this, p);
    }

    @Override
    public String toString() {
        return MapView.toString$(this);
    }

    @Override
    public String stringPrefix() {
        return MapView.stringPrefix$(this);
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

