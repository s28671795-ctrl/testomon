/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.mutable;

import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.Function3;
import scala.Function4;
import scala.Function5;
import scala.collection.Factory;
import scala.collection.IterableFactory;
import scala.collection.IterableOnce;
import scala.collection.SeqFactory;
import scala.collection.SeqOps;
import scala.collection.StrictOptimizedSeqFactory;
import scala.collection.immutable.Seq;
import scala.collection.mutable.Builder;
import scala.collection.mutable.GrowableBuilder;
import scala.collection.mutable.Stack;
import scala.math.Integral;
import scala.runtime.ModuleSerializationProxy;

public final class Stack$
implements StrictOptimizedSeqFactory<Stack> {
    public static final Stack$ MODULE$ = new Stack$();
    private static final long serialVersionUID = 3L;

    @Override
    public SeqOps fill(int n, Function0 elem) {
        return StrictOptimizedSeqFactory.fill$(this, n, elem);
    }

    @Override
    public SeqOps tabulate(int n, Function1 f) {
        return StrictOptimizedSeqFactory.tabulate$(this, n, f);
    }

    @Override
    public SeqOps concat(Seq xss) {
        return StrictOptimizedSeqFactory.concat$(this, xss);
    }

    @Override
    public final SeqOps unapplySeq(SeqOps x) {
        return SeqFactory.unapplySeq$(this, x);
    }

    @Override
    public Object apply(Seq elems) {
        return IterableFactory.apply$(this, elems);
    }

    @Override
    public Object iterate(Object start2, int len, Function1 f) {
        return IterableFactory.iterate$(this, start2, len, f);
    }

    @Override
    public Object unfold(Object init2, Function1 f) {
        return IterableFactory.unfold$(this, init2, f);
    }

    @Override
    public Object range(Object start2, Object end, Integral evidence$3) {
        return IterableFactory.range$(this, start2, end, evidence$3);
    }

    @Override
    public Object range(Object start2, Object end, Object step, Integral evidence$4) {
        return IterableFactory.range$(this, start2, end, step, evidence$4);
    }

    @Override
    public Object fill(int n1, int n2, Function0 elem) {
        return IterableFactory.fill$(this, n1, n2, elem);
    }

    @Override
    public Object fill(int n1, int n2, int n3, Function0 elem) {
        return IterableFactory.fill$(this, n1, n2, n3, elem);
    }

    @Override
    public Object fill(int n1, int n2, int n3, int n4, Function0 elem) {
        return IterableFactory.fill$(this, n1, n2, n3, n4, elem);
    }

    @Override
    public Object fill(int n1, int n2, int n3, int n4, int n5, Function0 elem) {
        return IterableFactory.fill$(this, n1, n2, n3, n4, n5, elem);
    }

    @Override
    public Object tabulate(int n1, int n2, Function2 f) {
        return IterableFactory.tabulate$(this, n1, n2, f);
    }

    @Override
    public Object tabulate(int n1, int n2, int n3, Function3 f) {
        return IterableFactory.tabulate$(this, n1, n2, n3, f);
    }

    @Override
    public Object tabulate(int n1, int n2, int n3, int n4, Function4 f) {
        return IterableFactory.tabulate$(this, n1, n2, n3, n4, f);
    }

    @Override
    public Object tabulate(int n1, int n2, int n3, int n4, int n5, Function5 f) {
        return IterableFactory.tabulate$(this, n1, n2, n3, n4, n5, f);
    }

    @Override
    public <A> Factory<A, Stack<A>> iterableFactory() {
        return IterableFactory.iterableFactory$(this);
    }

    public <A> int $lessinit$greater$default$1() {
        return 16;
    }

    @Override
    public <A> Stack<A> from(IterableOnce<A> source2) {
        Object object = this.empty();
        if (object == null) {
            throw null;
        }
        return (Stack)object.addAll(source2);
    }

    @Override
    public <A> Stack<A> empty() {
        return new Stack(16);
    }

    @Override
    public <A> Builder<A, Stack<A>> newBuilder() {
        return new GrowableBuilder(this.empty());
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(Stack$.class);
    }

    private Stack$() {
    }
}

