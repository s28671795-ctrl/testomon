/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.immutable;

import java.io.Serializable;
import scala.Array$;
import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.Function3;
import scala.Function4;
import scala.Function5;
import scala.MatchError;
import scala.collection.ClassTagIterableFactory;
import scala.collection.ClassTagSeqFactory;
import scala.collection.EvidenceIterableFactory;
import scala.collection.Factory;
import scala.collection.IterableOnce;
import scala.collection.SeqFactory;
import scala.collection.SeqOps;
import scala.collection.StrictOptimizedClassTagSeqFactory;
import scala.collection.immutable.ArraySeq;
import scala.collection.immutable.Seq;
import scala.collection.mutable.ArrayBuffer;
import scala.collection.mutable.Builder;
import scala.collection.mutable.GrowableBuilder;
import scala.math.Integral;
import scala.reflect.ClassTag;
import scala.runtime.BoxedUnit;
import scala.runtime.ModuleSerializationProxy;
import scala.runtime.Nothing$;
import scala.runtime.ScalaRunTime$;

public final class ArraySeq$
implements StrictOptimizedClassTagSeqFactory<ArraySeq> {
    public static final ArraySeq$ MODULE$ = new ArraySeq$();
    private static final long serialVersionUID = 3L;
    private static ArraySeq.ofRef<Nothing$> emptyImpl;
    private static final SeqFactory<ArraySeq> untagged;
    private static volatile boolean bitmap$0;

    static {
        untagged = new ClassTagSeqFactory.AnySeqDelegate<ArraySeq>(MODULE$);
    }

    @Override
    public final SeqOps unapplySeq(SeqOps x) {
        return ClassTagSeqFactory.unapplySeq$(this, x);
    }

    @Override
    public Object range(Object start2, Object end, Integral evidence$22, ClassTag evidence$23) {
        return ClassTagIterableFactory.range$(this, start2, end, evidence$22, evidence$23);
    }

    @Override
    public Object range(Object start2, Object end, Object step, Integral evidence$24, ClassTag evidence$25) {
        return ClassTagIterableFactory.range$(this, start2, end, step, evidence$24, evidence$25);
    }

    @Override
    public Object fill(int n1, int n2, Function0 elem, ClassTag evidence$26) {
        return ClassTagIterableFactory.fill$(this, n1, n2, elem, evidence$26);
    }

    @Override
    public Object fill(int n1, int n2, int n3, Function0 elem, ClassTag evidence$27) {
        return ClassTagIterableFactory.fill$(this, n1, n2, n3, elem, evidence$27);
    }

    @Override
    public Object fill(int n1, int n2, int n3, int n4, Function0 elem, ClassTag evidence$28) {
        return ClassTagIterableFactory.fill$(this, n1, n2, n3, n4, elem, evidence$28);
    }

    @Override
    public Object fill(int n1, int n2, int n3, int n4, int n5, Function0 elem, ClassTag evidence$29) {
        return ClassTagIterableFactory.fill$(this, n1, n2, n3, n4, n5, elem, evidence$29);
    }

    @Override
    public Object tabulate(int n1, int n2, Function2 f, ClassTag evidence$30) {
        return ClassTagIterableFactory.tabulate$(this, n1, n2, f, evidence$30);
    }

    @Override
    public Object tabulate(int n1, int n2, int n3, Function3 f, ClassTag evidence$31) {
        return ClassTagIterableFactory.tabulate$(this, n1, n2, n3, f, evidence$31);
    }

    @Override
    public Object tabulate(int n1, int n2, int n3, int n4, Function4 f, ClassTag evidence$32) {
        return ClassTagIterableFactory.tabulate$(this, n1, n2, n3, n4, f, evidence$32);
    }

    @Override
    public Object tabulate(int n1, int n2, int n3, int n4, int n5, Function5 f, ClassTag evidence$33) {
        return ClassTagIterableFactory.tabulate$(this, n1, n2, n3, n4, n5, f, evidence$33);
    }

    @Override
    public Object apply(Seq xs, Object evidence$7) {
        return EvidenceIterableFactory.apply$(this, xs, evidence$7);
    }

    @Override
    public Object iterate(Object start2, int len, Function1 f, Object evidence$10) {
        return EvidenceIterableFactory.iterate$(this, start2, len, f, evidence$10);
    }

    @Override
    public Object unfold(Object init2, Function1 f, Object evidence$11) {
        return EvidenceIterableFactory.unfold$(this, init2, f, evidence$11);
    }

    @Override
    public Factory evidenceIterableFactory(Object evidence$13) {
        return EvidenceIterableFactory.evidenceIterableFactory$(this, evidence$13);
    }

    public SeqFactory<ArraySeq> untagged() {
        return untagged;
    }

    private ArraySeq.ofRef<Nothing$> emptyImpl$lzycompute() {
        synchronized (this) {
            if (!bitmap$0) {
                emptyImpl = new ArraySeq.ofRef<Object>(new Object[0]);
                bitmap$0 = true;
            }
        }
        return emptyImpl;
    }

    private ArraySeq.ofRef<Nothing$> emptyImpl() {
        if (!bitmap$0) {
            return this.emptyImpl$lzycompute();
        }
        return emptyImpl;
    }

    @Override
    public <A> ArraySeq<A> empty(ClassTag<A> evidence$1) {
        return this.emptyImpl();
    }

    @Override
    public <A> ArraySeq<A> from(IterableOnce<A> it, ClassTag<A> tag) {
        if (it instanceof ArraySeq) {
            return (ArraySeq)it;
        }
        return this.unsafeWrapArray(Array$.MODULE$.from(it, tag));
    }

    @Override
    public <A> Builder<A, ArraySeq<A>> newBuilder(ClassTag<A> evidence$2) {
        return new GrowableBuilder<A, ArrayBuffer<A>>(){

            public void sizeHint(int size) {
                ((ArrayBuffer)this.elems()).ensureSize(size);
            }
        }.mapResult((Function1<ArrayBuffer, ArraySeq> & Serializable)b -> MODULE$.unsafeWrapArray(b.toArray(evidence$2)));
    }

    @Override
    public <A> ArraySeq<A> fill(int n, Function0<A> elem, ClassTag<A> evidence$3) {
        int tabulate_max_y = 0;
        int tabulate_ofDim_n1 = Math.max(n, tabulate_max_y);
        Object tabulate_elements = evidence$3.newArray(tabulate_ofDim_n1);
        for (int tabulate_i = 0; tabulate_i < n; ++tabulate_i) {
            ScalaRunTime$.MODULE$.array_update(tabulate_elements, tabulate_i, elem.apply());
        }
        return this.unsafeWrapArray(tabulate_elements);
    }

    @Override
    public <A> ArraySeq<A> tabulate(int n, Function1<Object, A> f, ClassTag<A> evidence$4) {
        int max_y = 0;
        int ofDim_n1 = Math.max(n, max_y);
        Object elements = evidence$4.newArray(ofDim_n1);
        for (int i = 0; i < n; ++i) {
            ScalaRunTime$.MODULE$.array_update(elements, i, f.apply(i));
        }
        return this.unsafeWrapArray(elements);
    }

    public <T> ArraySeq<T> unsafeWrapArray(Object x) {
        if (x == null) {
            return null;
        }
        if (x instanceof Object[]) {
            Object[] objectArray = (Object[])x;
            return new ArraySeq.ofRef<Object>(objectArray);
        }
        if (x instanceof int[]) {
            int[] nArray = (int[])x;
            return new ArraySeq.ofInt(nArray);
        }
        if (x instanceof double[]) {
            double[] dArray = (double[])x;
            return new ArraySeq.ofDouble(dArray);
        }
        if (x instanceof long[]) {
            long[] lArray = (long[])x;
            return new ArraySeq.ofLong(lArray);
        }
        if (x instanceof float[]) {
            float[] fArray = (float[])x;
            return new ArraySeq.ofFloat(fArray);
        }
        if (x instanceof char[]) {
            char[] cArray = (char[])x;
            return new ArraySeq.ofChar(cArray);
        }
        if (x instanceof byte[]) {
            byte[] byArray = (byte[])x;
            return new ArraySeq.ofByte(byArray);
        }
        if (x instanceof short[]) {
            short[] sArray = (short[])x;
            return new ArraySeq.ofShort(sArray);
        }
        if (x instanceof boolean[]) {
            boolean[] blArray = (boolean[])x;
            return new ArraySeq.ofBoolean(blArray);
        }
        if (x instanceof BoxedUnit[]) {
            BoxedUnit[] boxedUnitArray = (BoxedUnit[])x;
            return new ArraySeq.ofUnit(boxedUnitArray);
        }
        throw new MatchError(x);
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(ArraySeq$.class);
    }

    private ArraySeq$() {
    }

    public static final /* synthetic */ Object $anonfun$fill$1$adapted(Function0 elem$1, Object x$1) {
        return elem$1.apply();
    }
}

