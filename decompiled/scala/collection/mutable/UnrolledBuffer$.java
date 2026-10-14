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
import scala.collection.ClassTagIterableFactory;
import scala.collection.ClassTagSeqFactory;
import scala.collection.EvidenceIterableFactory;
import scala.collection.Factory;
import scala.collection.IterableOnce;
import scala.collection.SeqFactory;
import scala.collection.SeqOps;
import scala.collection.StrictOptimizedClassTagSeqFactory;
import scala.collection.immutable.Seq;
import scala.collection.mutable.UnrolledBuffer;
import scala.math.Integral;
import scala.reflect.ClassTag;
import scala.runtime.ModuleSerializationProxy;

public final class UnrolledBuffer$
implements StrictOptimizedClassTagSeqFactory<UnrolledBuffer> {
    public static final UnrolledBuffer$ MODULE$ = new UnrolledBuffer$();
    private static final long serialVersionUID = 3L;
    private static final SeqFactory<UnrolledBuffer> untagged = new ClassTagSeqFactory.AnySeqDelegate<UnrolledBuffer>(MODULE$);
    private static final int waterline = 50;
    private static final int waterlineDelim = 100;
    private static final int unrolledlength = 32;

    @Override
    public SeqOps fill(int n, Function0 elem, ClassTag evidence$34) {
        return StrictOptimizedClassTagSeqFactory.fill$(this, n, elem, evidence$34);
    }

    @Override
    public SeqOps tabulate(int n, Function1 f, ClassTag evidence$35) {
        return StrictOptimizedClassTagSeqFactory.tabulate$(this, n, f, evidence$35);
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

    public SeqFactory<UnrolledBuffer> untagged() {
        return untagged;
    }

    @Override
    public <A> UnrolledBuffer<A> empty(ClassTag<A> evidence$1) {
        return new UnrolledBuffer<A>(evidence$1);
    }

    @Override
    public <A> UnrolledBuffer<A> from(IterableOnce<A> source2, ClassTag<A> evidence$2) {
        return (UnrolledBuffer)new UnrolledBuffer<A>(evidence$2).addAll(source2);
    }

    public <A> UnrolledBuffer<A> newBuilder(ClassTag<A> evidence$3) {
        return new UnrolledBuffer<A>(evidence$3);
    }

    public final int waterline() {
        return waterline;
    }

    public final int waterlineDenom() {
        return 100;
    }

    public final int waterlineDelim() {
        return waterlineDelim;
    }

    public int unrolledlength() {
        return unrolledlength;
    }

    private Object writeReplace() {
        return new ModuleSerializationProxy(UnrolledBuffer$.class);
    }

    private UnrolledBuffer$() {
    }
}

