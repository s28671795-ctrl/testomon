/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.immutable;

import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.Function3;
import scala.Function4;
import scala.Function5;
import scala.Tuple2;
import scala.collection.Factory;
import scala.collection.IterableFactory;
import scala.collection.IterableOnce;
import scala.collection.SeqFactory;
import scala.collection.SeqOps;
import scala.collection.StrictOptimizedSeqFactory;
import scala.collection.immutable.List;
import scala.collection.immutable.Nil$;
import scala.collection.immutable.Seq;
import scala.collection.mutable.Builder;
import scala.collection.mutable.ListBuffer;
import scala.math.Integral;
import scala.runtime.ModuleSerializationProxy;

public final class List$
implements StrictOptimizedSeqFactory<List> {
    public static final List$ MODULE$ = new List$();
    private static final long serialVersionUID = 3L;
    private static final Tuple2<Nil$, Nil$> scala$collection$immutable$List$$TupleOfNil = new Tuple2<Nil$, Nil$>(Nil$.MODULE$, Nil$.MODULE$);
    private static final transient Function1<Object, Object> partialNotApplied = new Function1<Object, Object>(){

        public boolean apply$mcZD$sp(double v1) {
            return Function1.apply$mcZD$sp$(this, v1);
        }

        public double apply$mcDD$sp(double v1) {
            return Function1.apply$mcDD$sp$(this, v1);
        }

        public float apply$mcFD$sp(double v1) {
            return Function1.apply$mcFD$sp$(this, v1);
        }

        public int apply$mcID$sp(double v1) {
            return Function1.apply$mcID$sp$(this, v1);
        }

        public long apply$mcJD$sp(double v1) {
            return Function1.apply$mcJD$sp$(this, v1);
        }

        public void apply$mcVD$sp(double v1) {
            Function1.apply$mcVD$sp$(this, v1);
        }

        public boolean apply$mcZF$sp(float v1) {
            return Function1.apply$mcZF$sp$(this, v1);
        }

        public double apply$mcDF$sp(float v1) {
            return Function1.apply$mcDF$sp$(this, v1);
        }

        public float apply$mcFF$sp(float v1) {
            return Function1.apply$mcFF$sp$(this, v1);
        }

        public int apply$mcIF$sp(float v1) {
            return Function1.apply$mcIF$sp$(this, v1);
        }

        public long apply$mcJF$sp(float v1) {
            return Function1.apply$mcJF$sp$(this, v1);
        }

        public void apply$mcVF$sp(float v1) {
            Function1.apply$mcVF$sp$(this, v1);
        }

        public boolean apply$mcZI$sp(int v1) {
            return Function1.apply$mcZI$sp$(this, v1);
        }

        public double apply$mcDI$sp(int v1) {
            return Function1.apply$mcDI$sp$(this, v1);
        }

        public float apply$mcFI$sp(int v1) {
            return Function1.apply$mcFI$sp$(this, v1);
        }

        public int apply$mcII$sp(int v1) {
            return Function1.apply$mcII$sp$(this, v1);
        }

        public long apply$mcJI$sp(int v1) {
            return Function1.apply$mcJI$sp$(this, v1);
        }

        public void apply$mcVI$sp(int v1) {
            Function1.apply$mcVI$sp$(this, v1);
        }

        public boolean apply$mcZJ$sp(long v1) {
            return Function1.apply$mcZJ$sp$(this, v1);
        }

        public double apply$mcDJ$sp(long v1) {
            return Function1.apply$mcDJ$sp$(this, v1);
        }

        public float apply$mcFJ$sp(long v1) {
            return Function1.apply$mcFJ$sp$(this, v1);
        }

        public int apply$mcIJ$sp(long v1) {
            return Function1.apply$mcIJ$sp$(this, v1);
        }

        public long apply$mcJJ$sp(long v1) {
            return Function1.apply$mcJJ$sp$(this, v1);
        }

        public void apply$mcVJ$sp(long v1) {
            Function1.apply$mcVJ$sp$(this, v1);
        }

        public <A$> Function1<A$, Object> compose(Function1<A$, Object> g2) {
            return Function1.compose$(this, g2);
        }

        public <A$> Function1<Object, A$> andThen(Function1<Object, A$> g2) {
            return Function1.andThen$(this, g2);
        }

        public String toString() {
            return Function1.toString$(this);
        }

        public Object apply(Object x) {
            return this;
        }
    };

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
    public <A> Factory<A, List<A>> iterableFactory() {
        return IterableFactory.iterableFactory$(this);
    }

    public Tuple2<Nil$, Nil$> scala$collection$immutable$List$$TupleOfNil() {
        return scala$collection$immutable$List$$TupleOfNil;
    }

    @Override
    public <B> List<B> from(IterableOnce<B> coll) {
        return Nil$.MODULE$.prependedAll((IterableOnce)coll);
    }

    @Override
    public <A> Builder<A, List<A>> newBuilder() {
        return new ListBuffer();
    }

    @Override
    public <A> List<A> empty() {
        return Nil$.MODULE$;
    }

    public Function1<Object, Object> partialNotApplied() {
        return partialNotApplied;
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(List$.class);
    }

    private List$() {
    }
}

