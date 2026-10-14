/*
 * Decompiled with CFR 0.152.
 */
package scala.jdk;

import java.util.NoSuchElementException;
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import scala.collection.IntStepper;
import scala.collection.Iterator;
import scala.collection.Stepper;
import scala.jdk.IntAccumulator;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005%b!B\u000e\u001d\u0001q\u0001\u0003\u0002\u0003\u001e\u0001\u0005\u000b\u0007I\u0011B\u001e\t\u0011\u0001\u0003!\u0011!Q\u0001\nqBQ!\u0011\u0001\u0005\u0002\tCq!\u0012\u0001A\u0002\u0013%a\tC\u0004K\u0001\u0001\u0007I\u0011B&\t\rE\u0003\u0001\u0015)\u0003H\u0011\u001d\u0011\u0006\u00011A\u0005\n\u0019Cqa\u0015\u0001A\u0002\u0013%A\u000b\u0003\u0004W\u0001\u0001\u0006Ka\u0012\u0005\b/\u0002\u0001\r\u0011\"\u0003Y\u0011\u001da\u0006\u00011A\u0005\nuCaa\u0018\u0001!B\u0013I\u0006b\u00021\u0001\u0001\u0004%I!\u0019\u0005\bK\u0002\u0001\r\u0011\"\u0003g\u0011\u0019A\u0007\u0001)Q\u0005E\"9\u0011\u000e\u0001a\u0001\n\u0013\t\u0007b\u00026\u0001\u0001\u0004%Ia\u001b\u0005\u0007[\u0002\u0001\u000b\u0015\u00022\t\u000b9\u0004A\u0011B8\t\u000bI\u0004A\u0011B:\t\u000bQ\u0004A\u0011\u0001$\t\u000bU\u0004A\u0011A1\t\u000bY\u0004A\u0011A<\t\u000bm\u0004A\u0011\u0001?\t\u000bu\u0004A\u0011\u0001@\t\r}\u0004A\u0011IA\u0001\u0005UIe\u000e^!dGVlW\u000f\\1u_J\u001cF/\u001a9qKJT!!\b\u0010\u0002\u0007)$7NC\u0001 \u0003\u0015\u00198-\u00197b'\u0011\u0001\u0011%J\u0016\u0011\u0005\t\u001aS\"\u0001\u0010\n\u0005\u0011r\"AB!osJ+g\r\u0005\u0002'S5\tqE\u0003\u0002)=\u0005Q1m\u001c7mK\u000e$\u0018n\u001c8\n\u0005):#AC%oiN#X\r\u001d9feB\u0011Af\u000e\b\u0003[Ur!A\f\u001b\u000f\u0005=\u001aT\"\u0001\u0019\u000b\u0005E\u0012\u0014A\u0002\u001fs_>$hh\u0001\u0001\n\u0003}I!\u0001\u000b\u0010\n\u0005Y:\u0013aB*uKB\u0004XM]\u0005\u0003qe\u0012a\"\u00124gS\u000eLWM\u001c;Ta2LGO\u0003\u00027O\u0005\u0019\u0011mY2\u0016\u0003q\u0002\"!\u0010 \u000e\u0003qI!a\u0010\u000f\u0003\u001d%sG/Q2dk6,H.\u0019;pe\u0006!\u0011mY2!\u0003\u0019a\u0014N\\5u}Q\u00111\t\u0012\t\u0003{\u0001AQAO\u0002A\u0002q\n\u0011\u0001[\u000b\u0002\u000fB\u0011!\u0005S\u0005\u0003\u0013z\u00111!\u00138u\u0003\u0015Aw\fJ3r)\tau\n\u0005\u0002#\u001b&\u0011aJ\b\u0002\u0005+:LG\u000fC\u0004Q\u000b\u0005\u0005\t\u0019A$\u0002\u0007a$\u0013'\u0001\u0002iA\u0005\t\u0011.A\u0003j?\u0012*\u0017\u000f\u0006\u0002M+\"9\u0001\u000bCA\u0001\u0002\u00049\u0015AA5!\u0003\u0005\tW#A-\u0011\u0007\tRv)\u0003\u0002\\=\t)\u0011I\u001d:bs\u0006)\u0011m\u0018\u0013fcR\u0011AJ\u0018\u0005\b!.\t\t\u00111\u0001Z\u0003\t\t\u0007%A\u0001o+\u0005\u0011\u0007C\u0001\u0012d\u0013\t!gD\u0001\u0003M_:<\u0017!\u00028`I\u0015\fHC\u0001'h\u0011\u001d\u0001f\"!AA\u0002\t\f!A\u001c\u0011\u0002\u00039\u000bQAT0%KF$\"\u0001\u00147\t\u000fA\u000b\u0012\u0011!a\u0001E\u0006\u0011a\nI\u0001\u000eIV\u0004H.[2bi\u0016\u001cV\r\u001c4\u0015\u0005\r\u0003\b\"B9\u0014\u0001\u0004\u0011\u0017!\u00027j[&$\u0018\u0001\u00037pC\u0012luN]3\u0015\u00031\u000bqb\u00195be\u0006\u001cG/\u001a:jgRL7m]\u0001\rKN$\u0018.\\1uKNK'0Z\u0001\bQ\u0006\u001c8\u000b^3q+\u0005A\bC\u0001\u0012z\u0013\tQhDA\u0004C_>dW-\u00198\u0002\u00119,\u0007\u0010^*uKB$\u0012aR\u0001\tiJL8\u000b\u001d7jiR\tQ%A\u0006ta2LG/\u001a:bi>\u0014X\u0003BA\u0002\u0003;)\"!!\u0002\u0011\t\u0005\u001d\u0011q\u0003\b\u0005\u0003\u0013\t\u0019\"\u0004\u0002\u0002\f)!\u0011QBA\b\u0003\u0011)H/\u001b7\u000b\u0005\u0005E\u0011\u0001\u00026bm\u0006LA!!\u0006\u0002\f\u0005Y1\u000b\u001d7ji\u0016\u0014\u0018\r^8s\u0013\u0011\tI\"a\u0007\u0003\u000b=3\u0017J\u001c;\u000b\t\u0005U\u00111\u0002\u0003\b\u0003?Q\"\u0019AA\u0011\u0005\u0005\u0011\u0015cA$\u0002$A\u0019!%!\n\n\u0007\u0005\u001dbDA\u0002B]f\u0004")
public class IntAccumulatorStepper
implements IntStepper,
Stepper.EfficientSplit {
    private final IntAccumulator acc;
    private int h;
    private int scala$jdk$IntAccumulatorStepper$$i;
    private int[] scala$jdk$IntAccumulatorStepper$$a;
    private long scala$jdk$IntAccumulatorStepper$$n;
    private long scala$jdk$IntAccumulatorStepper$$N;

    @Override
    public <B> PrimitiveIterator.OfInt javaIterator() {
        return IntStepper.javaIterator$(this);
    }

    @Override
    public <B> java.util.Iterator<?> javaIterator$mcI$sp() {
        return IntStepper.javaIterator$mcI$sp$(this);
    }

    @Override
    public double nextStep$mcD$sp() {
        return Stepper.nextStep$mcD$sp$(this);
    }

    @Override
    public long nextStep$mcJ$sp() {
        return Stepper.nextStep$mcJ$sp$(this);
    }

    @Override
    public Stepper<Object> trySplit$mcD$sp() {
        return Stepper.trySplit$mcD$sp$(this);
    }

    @Override
    public Stepper<Object> trySplit$mcI$sp() {
        return Stepper.trySplit$mcI$sp$(this);
    }

    @Override
    public Stepper<Object> trySplit$mcJ$sp() {
        return Stepper.trySplit$mcJ$sp$(this);
    }

    @Override
    public <B> Spliterator<?> spliterator$mcD$sp() {
        return Stepper.spliterator$mcD$sp$(this);
    }

    @Override
    public <B> Spliterator<?> spliterator$mcJ$sp() {
        return Stepper.spliterator$mcJ$sp$(this);
    }

    @Override
    public <B> java.util.Iterator<?> javaIterator$mcD$sp() {
        return Stepper.javaIterator$mcD$sp$(this);
    }

    @Override
    public <B> java.util.Iterator<?> javaIterator$mcJ$sp() {
        return Stepper.javaIterator$mcJ$sp$(this);
    }

    @Override
    public Iterator<Object> iterator() {
        return Stepper.iterator$(this);
    }

    private IntAccumulator acc() {
        return this.acc;
    }

    private int h() {
        return this.h;
    }

    private void h_$eq(int x$1) {
        this.h = x$1;
    }

    public int scala$jdk$IntAccumulatorStepper$$i() {
        return this.scala$jdk$IntAccumulatorStepper$$i;
    }

    public void scala$jdk$IntAccumulatorStepper$$i_$eq(int x$1) {
        this.scala$jdk$IntAccumulatorStepper$$i = x$1;
    }

    public int[] scala$jdk$IntAccumulatorStepper$$a() {
        return this.scala$jdk$IntAccumulatorStepper$$a;
    }

    private void a_$eq(int[] x$1) {
        this.scala$jdk$IntAccumulatorStepper$$a = x$1;
    }

    public long scala$jdk$IntAccumulatorStepper$$n() {
        return this.scala$jdk$IntAccumulatorStepper$$n;
    }

    public void scala$jdk$IntAccumulatorStepper$$n_$eq(long x$1) {
        this.scala$jdk$IntAccumulatorStepper$$n = x$1;
    }

    public long scala$jdk$IntAccumulatorStepper$$N() {
        return this.scala$jdk$IntAccumulatorStepper$$N;
    }

    public void scala$jdk$IntAccumulatorStepper$$N_$eq(long x$1) {
        this.scala$jdk$IntAccumulatorStepper$$N = x$1;
    }

    private IntAccumulatorStepper duplicateSelf(long limit) {
        IntAccumulatorStepper ans = new IntAccumulatorStepper(this.acc());
        ans.h_$eq(this.h());
        ans.scala$jdk$IntAccumulatorStepper$$i_$eq(this.scala$jdk$IntAccumulatorStepper$$i());
        ans.a_$eq(this.scala$jdk$IntAccumulatorStepper$$a());
        ans.scala$jdk$IntAccumulatorStepper$$n_$eq(this.scala$jdk$IntAccumulatorStepper$$n());
        ans.scala$jdk$IntAccumulatorStepper$$N_$eq(limit);
        return ans;
    }

    public void scala$jdk$IntAccumulatorStepper$$loadMore() {
        this.h_$eq(this.h() + 1);
        if (this.h() < this.acc().hIndex()) {
            this.a_$eq(this.acc().history()[this.h()]);
            this.scala$jdk$IntAccumulatorStepper$$n_$eq(this.acc().cumulative(this.h()) - this.acc().cumulative(this.h() - 1));
        } else {
            this.a_$eq(this.acc().current());
            this.scala$jdk$IntAccumulatorStepper$$n_$eq(this.acc().index());
        }
        this.scala$jdk$IntAccumulatorStepper$$i_$eq(0);
    }

    @Override
    public int characteristics() {
        return 16720;
    }

    @Override
    public long estimateSize() {
        return this.scala$jdk$IntAccumulatorStepper$$N();
    }

    @Override
    public boolean hasStep() {
        return this.scala$jdk$IntAccumulatorStepper$$N() > 0L;
    }

    @Override
    public int nextStep() {
        return this.nextStep$mcI$sp();
    }

    @Override
    public IntStepper trySplit() {
        if (this.scala$jdk$IntAccumulatorStepper$$N() <= 1L) {
            return null;
        }
        long half = this.scala$jdk$IntAccumulatorStepper$$N() >> 1;
        long R = (this.h() <= 0 ? 0L : this.acc().cumulative(this.h() - 1)) + (long)this.scala$jdk$IntAccumulatorStepper$$i() + half;
        IntAccumulatorStepper ans = this.duplicateSelf(half);
        if (this.h() < this.acc().hIndex()) {
            long w = this.acc().seekSlot(R);
            this.h_$eq((int)(w >>> 32));
            if (this.h() < this.acc().hIndex()) {
                this.a_$eq(this.acc().history()[this.h()]);
                this.scala$jdk$IntAccumulatorStepper$$n_$eq(this.acc().cumulative(this.h()) - (this.h() > 0 ? this.acc().cumulative(this.h() - 1) : 0L));
            } else {
                this.a_$eq(this.acc().current());
                this.scala$jdk$IntAccumulatorStepper$$n_$eq(this.acc().index());
            }
            this.scala$jdk$IntAccumulatorStepper$$i_$eq((int)(w & 0xFFFFFFFFL));
        } else {
            this.scala$jdk$IntAccumulatorStepper$$i_$eq(this.scala$jdk$IntAccumulatorStepper$$i() + (int)half);
        }
        this.scala$jdk$IntAccumulatorStepper$$N_$eq(this.scala$jdk$IntAccumulatorStepper$$N() - half);
        return ans;
    }

    @Override
    public <B> Spliterator.OfInt spliterator() {
        return (Spliterator.OfInt)this.spliterator$mcI$sp();
    }

    @Override
    public int nextStep$mcI$sp() {
        if (this.scala$jdk$IntAccumulatorStepper$$N() <= 0L) {
            throw new NoSuchElementException("next on empty Stepper");
        }
        if ((long)this.scala$jdk$IntAccumulatorStepper$$i() >= this.scala$jdk$IntAccumulatorStepper$$n()) {
            this.scala$jdk$IntAccumulatorStepper$$loadMore();
        }
        int ans = this.scala$jdk$IntAccumulatorStepper$$a()[this.scala$jdk$IntAccumulatorStepper$$i()];
        this.scala$jdk$IntAccumulatorStepper$$i_$eq(this.scala$jdk$IntAccumulatorStepper$$i() + 1);
        this.scala$jdk$IntAccumulatorStepper$$N_$eq(this.scala$jdk$IntAccumulatorStepper$$N() - 1L);
        return ans;
    }

    @Override
    public <B> Spliterator<?> spliterator$mcI$sp() {
        return new IntStepper.IntStepperSpliterator(this){
            private final /* synthetic */ IntAccumulatorStepper $outer;

            public boolean tryAdvance(IntConsumer c) {
                if (this.$outer.scala$jdk$IntAccumulatorStepper$$N() <= 0L) {
                    return false;
                }
                if ((long)this.$outer.scala$jdk$IntAccumulatorStepper$$i() >= this.$outer.scala$jdk$IntAccumulatorStepper$$n()) {
                    this.$outer.scala$jdk$IntAccumulatorStepper$$loadMore();
                }
                c.accept(this.$outer.scala$jdk$IntAccumulatorStepper$$a()[this.$outer.scala$jdk$IntAccumulatorStepper$$i()]);
                this.$outer.scala$jdk$IntAccumulatorStepper$$i_$eq(this.$outer.scala$jdk$IntAccumulatorStepper$$i() + 1);
                this.$outer.scala$jdk$IntAccumulatorStepper$$N_$eq(this.$outer.scala$jdk$IntAccumulatorStepper$$N() - 1L);
                return true;
            }

            public boolean tryAdvance(Consumer<? super Integer> c) {
                if (c instanceof IntConsumer) {
                    IntConsumer intConsumer = (IntConsumer)((Object)c);
                    return this.tryAdvance(intConsumer);
                }
                if (this.$outer.scala$jdk$IntAccumulatorStepper$$N() <= 0L) {
                    return false;
                }
                if ((long)this.$outer.scala$jdk$IntAccumulatorStepper$$i() >= this.$outer.scala$jdk$IntAccumulatorStepper$$n()) {
                    this.$outer.scala$jdk$IntAccumulatorStepper$$loadMore();
                }
                c.accept((Integer)this.$outer.scala$jdk$IntAccumulatorStepper$$a()[this.$outer.scala$jdk$IntAccumulatorStepper$$i()]);
                this.$outer.scala$jdk$IntAccumulatorStepper$$i_$eq(this.$outer.scala$jdk$IntAccumulatorStepper$$i() + 1);
                this.$outer.scala$jdk$IntAccumulatorStepper$$N_$eq(this.$outer.scala$jdk$IntAccumulatorStepper$$N() - 1L);
                return true;
            }

            public void forEachRemaining(IntConsumer c) {
                while (this.$outer.scala$jdk$IntAccumulatorStepper$$N() > 0L) {
                    if ((long)this.$outer.scala$jdk$IntAccumulatorStepper$$i() >= this.$outer.scala$jdk$IntAccumulatorStepper$$n()) {
                        this.$outer.scala$jdk$IntAccumulatorStepper$$loadMore();
                    }
                    int i0 = this.$outer.scala$jdk$IntAccumulatorStepper$$i();
                    if (this.$outer.scala$jdk$IntAccumulatorStepper$$n() - (long)this.$outer.scala$jdk$IntAccumulatorStepper$$i() > this.$outer.scala$jdk$IntAccumulatorStepper$$N()) {
                        this.$outer.scala$jdk$IntAccumulatorStepper$$n_$eq(this.$outer.scala$jdk$IntAccumulatorStepper$$i() + (int)this.$outer.scala$jdk$IntAccumulatorStepper$$N());
                    }
                    while ((long)this.$outer.scala$jdk$IntAccumulatorStepper$$i() < this.$outer.scala$jdk$IntAccumulatorStepper$$n()) {
                        c.accept(this.$outer.scala$jdk$IntAccumulatorStepper$$a()[this.$outer.scala$jdk$IntAccumulatorStepper$$i()]);
                        this.$outer.scala$jdk$IntAccumulatorStepper$$i_$eq(this.$outer.scala$jdk$IntAccumulatorStepper$$i() + 1);
                    }
                    this.$outer.scala$jdk$IntAccumulatorStepper$$N_$eq(this.$outer.scala$jdk$IntAccumulatorStepper$$N() - (this.$outer.scala$jdk$IntAccumulatorStepper$$n() - (long)i0));
                }
            }

            public void forEachRemaining(Consumer<? super Integer> c) {
                if (c instanceof IntConsumer) {
                    IntConsumer intConsumer = (IntConsumer)((Object)c);
                    this.forEachRemaining(intConsumer);
                    return;
                }
                while (this.$outer.scala$jdk$IntAccumulatorStepper$$N() > 0L) {
                    if ((long)this.$outer.scala$jdk$IntAccumulatorStepper$$i() >= this.$outer.scala$jdk$IntAccumulatorStepper$$n()) {
                        this.$outer.scala$jdk$IntAccumulatorStepper$$loadMore();
                    }
                    int i0 = this.$outer.scala$jdk$IntAccumulatorStepper$$i();
                    if (this.$outer.scala$jdk$IntAccumulatorStepper$$n() - (long)this.$outer.scala$jdk$IntAccumulatorStepper$$i() > this.$outer.scala$jdk$IntAccumulatorStepper$$N()) {
                        this.$outer.scala$jdk$IntAccumulatorStepper$$n_$eq(this.$outer.scala$jdk$IntAccumulatorStepper$$i() + (int)this.$outer.scala$jdk$IntAccumulatorStepper$$N());
                    }
                    while ((long)this.$outer.scala$jdk$IntAccumulatorStepper$$i() < this.$outer.scala$jdk$IntAccumulatorStepper$$n()) {
                        c.accept((Integer)this.$outer.scala$jdk$IntAccumulatorStepper$$a()[this.$outer.scala$jdk$IntAccumulatorStepper$$i()]);
                        this.$outer.scala$jdk$IntAccumulatorStepper$$i_$eq(this.$outer.scala$jdk$IntAccumulatorStepper$$i() + 1);
                    }
                    this.$outer.scala$jdk$IntAccumulatorStepper$$N_$eq(this.$outer.scala$jdk$IntAccumulatorStepper$$N() - (this.$outer.scala$jdk$IntAccumulatorStepper$$n() - (long)i0));
                }
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                super($outer);
            }
        };
    }

    public IntAccumulatorStepper(IntAccumulator acc) {
        this.acc = acc;
        this.h = 0;
        this.scala$jdk$IntAccumulatorStepper$$i = 0;
        this.scala$jdk$IntAccumulatorStepper$$a = acc.hIndex() > 0 ? acc.history()[0] : acc.current();
        this.scala$jdk$IntAccumulatorStepper$$n = acc.hIndex() > 0 ? acc.cumulative(0) : (long)acc.index();
        this.scala$jdk$IntAccumulatorStepper$$N = acc.totalSize();
    }
}

