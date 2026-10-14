/*
 * Decompiled with CFR 0.152.
 */
package scala.jdk;

import java.util.NoSuchElementException;
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.LongConsumer;
import scala.collection.Iterator;
import scala.collection.LongStepper;
import scala.collection.Stepper;
import scala.jdk.LongAccumulator;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005%b!B\u000e\u001d\u0001q\u0001\u0003\u0002\u0003\u001e\u0001\u0005\u000b\u0007I\u0011B\u001e\t\u0011\u0001\u0003!\u0011!Q\u0001\nqBQ!\u0011\u0001\u0005\u0002\tCq!\u0012\u0001A\u0002\u0013%a\tC\u0004K\u0001\u0001\u0007I\u0011B&\t\rE\u0003\u0001\u0015)\u0003H\u0011\u001d\u0011\u0006\u00011A\u0005\n\u0019Cqa\u0015\u0001A\u0002\u0013%A\u000b\u0003\u0004W\u0001\u0001\u0006Ka\u0012\u0005\b/\u0002\u0001\r\u0011\"\u0003Y\u0011\u001dy\u0006\u00011A\u0005\n\u0001DaA\u0019\u0001!B\u0013I\u0006bB2\u0001\u0001\u0004%I\u0001\u001a\u0005\bK\u0002\u0001\r\u0011\"\u0003g\u0011\u0019A\u0007\u0001)Q\u00059\"9\u0011\u000e\u0001a\u0001\n\u0013!\u0007b\u00026\u0001\u0001\u0004%Ia\u001b\u0005\u0007[\u0002\u0001\u000b\u0015\u0002/\t\u000b9\u0004A\u0011B8\t\u000bI\u0004A\u0011B:\t\u000bQ\u0004A\u0011\u0001$\t\u000bU\u0004A\u0011\u00013\t\u000bY\u0004A\u0011A<\t\u000bm\u0004A\u0011\u0001?\t\u000bu\u0004A\u0011\u0001@\t\r}\u0004A\u0011IA\u0001\u0005YauN\\4BG\u000e,X.\u001e7bi>\u00148\u000b^3qa\u0016\u0014(BA\u000f\u001f\u0003\rQGm\u001b\u0006\u0002?\u0005)1oY1mCN!\u0001!I\u0013,!\t\u00113%D\u0001\u001f\u0013\t!cD\u0001\u0004B]f\u0014VM\u001a\t\u0003M%j\u0011a\n\u0006\u0003Qy\t!bY8mY\u0016\u001cG/[8o\u0013\tQsEA\u0006M_:<7\u000b^3qa\u0016\u0014\bC\u0001\u00178\u001d\tiSG\u0004\u0002/i9\u0011qfM\u0007\u0002a)\u0011\u0011GM\u0001\u0007yI|w\u000e\u001e \u0004\u0001%\tq$\u0003\u0002)=%\u0011agJ\u0001\b'R,\u0007\u000f]3s\u0013\tA\u0014H\u0001\bFM\u001aL7-[3oiN\u0003H.\u001b;\u000b\u0005Y:\u0013aA1dGV\tA\b\u0005\u0002>}5\tA$\u0003\u0002@9\tyAj\u001c8h\u0003\u000e\u001cW/\\;mCR|'/\u0001\u0003bG\u000e\u0004\u0013A\u0002\u001fj]&$h\b\u0006\u0002D\tB\u0011Q\b\u0001\u0005\u0006u\r\u0001\r\u0001P\u0001\u0002QV\tq\t\u0005\u0002#\u0011&\u0011\u0011J\b\u0002\u0004\u0013:$\u0018!\u00025`I\u0015\fHC\u0001'P!\t\u0011S*\u0003\u0002O=\t!QK\\5u\u0011\u001d\u0001V!!AA\u0002\u001d\u000b1\u0001\u001f\u00132\u0003\tA\u0007%A\u0001j\u0003\u0015Iw\fJ3r)\taU\u000bC\u0004Q\u0011\u0005\u0005\t\u0019A$\u0002\u0005%\u0004\u0013!A1\u0016\u0003e\u00032A\t.]\u0013\tYfDA\u0003BeJ\f\u0017\u0010\u0005\u0002#;&\u0011aL\b\u0002\u0005\u0019>tw-A\u0003b?\u0012*\u0017\u000f\u0006\u0002MC\"9\u0001kCA\u0001\u0002\u0004I\u0016AA1!\u0003\u0005qW#\u0001/\u0002\u000b9|F%Z9\u0015\u00051;\u0007b\u0002)\u000f\u0003\u0003\u0005\r\u0001X\u0001\u0003]\u0002\n\u0011AT\u0001\u0006\u001d~#S-\u001d\u000b\u0003\u00192Dq\u0001U\t\u0002\u0002\u0003\u0007A,\u0001\u0002OA\u0005iA-\u001e9mS\u000e\fG/Z*fY\u001a$\"a\u00119\t\u000bE\u001c\u0002\u0019\u0001/\u0002\u000b1LW.\u001b;\u0002\u00111|\u0017\rZ'pe\u0016$\u0012\u0001T\u0001\u0010G\"\f'/Y2uKJL7\u000f^5dg\u0006aQm\u001d;j[\u0006$XmU5{K\u00069\u0001.Y:Ti\u0016\u0004X#\u0001=\u0011\u0005\tJ\u0018B\u0001>\u001f\u0005\u001d\u0011un\u001c7fC:\f\u0001B\\3yiN#X\r\u001d\u000b\u00029\u0006AAO]=Ta2LG\u000fF\u0001&\u0003-\u0019\b\u000f\\5uKJ\fGo\u001c:\u0016\t\u0005\r\u0011QD\u000b\u0003\u0003\u000b\u0001B!a\u0002\u0002\u00189!\u0011\u0011BA\n\u001b\t\tYA\u0003\u0003\u0002\u000e\u0005=\u0011\u0001B;uS2T!!!\u0005\u0002\t)\fg/Y\u0005\u0005\u0003+\tY!A\u0006Ta2LG/\u001a:bi>\u0014\u0018\u0002BA\r\u00037\u0011aa\u00144M_:<'\u0002BA\u000b\u0003\u0017!q!a\b\u001b\u0005\u0004\t\tCA\u0001C#\ra\u00161\u0005\t\u0004E\u0005\u0015\u0012bAA\u0014=\t\u0019\u0011I\\=")
public class LongAccumulatorStepper
implements LongStepper,
Stepper.EfficientSplit {
    private final LongAccumulator acc;
    private int h;
    private int scala$jdk$LongAccumulatorStepper$$i;
    private long[] scala$jdk$LongAccumulatorStepper$$a;
    private long scala$jdk$LongAccumulatorStepper$$n;
    private long scala$jdk$LongAccumulatorStepper$$N;

    @Override
    public <B> PrimitiveIterator.OfLong javaIterator() {
        return LongStepper.javaIterator$(this);
    }

    @Override
    public <B> java.util.Iterator<?> javaIterator$mcJ$sp() {
        return LongStepper.javaIterator$mcJ$sp$(this);
    }

    @Override
    public double nextStep$mcD$sp() {
        return Stepper.nextStep$mcD$sp$(this);
    }

    @Override
    public int nextStep$mcI$sp() {
        return Stepper.nextStep$mcI$sp$(this);
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
    public <B> Spliterator<?> spliterator$mcI$sp() {
        return Stepper.spliterator$mcI$sp$(this);
    }

    @Override
    public <B> java.util.Iterator<?> javaIterator$mcD$sp() {
        return Stepper.javaIterator$mcD$sp$(this);
    }

    @Override
    public <B> java.util.Iterator<?> javaIterator$mcI$sp() {
        return Stepper.javaIterator$mcI$sp$(this);
    }

    @Override
    public Iterator<Object> iterator() {
        return Stepper.iterator$(this);
    }

    private LongAccumulator acc() {
        return this.acc;
    }

    private int h() {
        return this.h;
    }

    private void h_$eq(int x$1) {
        this.h = x$1;
    }

    public int scala$jdk$LongAccumulatorStepper$$i() {
        return this.scala$jdk$LongAccumulatorStepper$$i;
    }

    public void scala$jdk$LongAccumulatorStepper$$i_$eq(int x$1) {
        this.scala$jdk$LongAccumulatorStepper$$i = x$1;
    }

    public long[] scala$jdk$LongAccumulatorStepper$$a() {
        return this.scala$jdk$LongAccumulatorStepper$$a;
    }

    private void a_$eq(long[] x$1) {
        this.scala$jdk$LongAccumulatorStepper$$a = x$1;
    }

    public long scala$jdk$LongAccumulatorStepper$$n() {
        return this.scala$jdk$LongAccumulatorStepper$$n;
    }

    public void scala$jdk$LongAccumulatorStepper$$n_$eq(long x$1) {
        this.scala$jdk$LongAccumulatorStepper$$n = x$1;
    }

    public long scala$jdk$LongAccumulatorStepper$$N() {
        return this.scala$jdk$LongAccumulatorStepper$$N;
    }

    public void scala$jdk$LongAccumulatorStepper$$N_$eq(long x$1) {
        this.scala$jdk$LongAccumulatorStepper$$N = x$1;
    }

    private LongAccumulatorStepper duplicateSelf(long limit) {
        LongAccumulatorStepper ans = new LongAccumulatorStepper(this.acc());
        ans.h_$eq(this.h());
        ans.scala$jdk$LongAccumulatorStepper$$i_$eq(this.scala$jdk$LongAccumulatorStepper$$i());
        ans.a_$eq(this.scala$jdk$LongAccumulatorStepper$$a());
        ans.scala$jdk$LongAccumulatorStepper$$n_$eq(this.scala$jdk$LongAccumulatorStepper$$n());
        ans.scala$jdk$LongAccumulatorStepper$$N_$eq(limit);
        return ans;
    }

    public void scala$jdk$LongAccumulatorStepper$$loadMore() {
        this.h_$eq(this.h() + 1);
        if (this.h() < this.acc().hIndex()) {
            this.a_$eq(this.acc().history()[this.h()]);
            this.scala$jdk$LongAccumulatorStepper$$n_$eq(this.acc().cumulative(this.h()) - this.acc().cumulative(this.h() - 1));
        } else {
            this.a_$eq(this.acc().current());
            this.scala$jdk$LongAccumulatorStepper$$n_$eq(this.acc().index());
        }
        this.scala$jdk$LongAccumulatorStepper$$i_$eq(0);
    }

    @Override
    public int characteristics() {
        return 16720;
    }

    @Override
    public long estimateSize() {
        return this.scala$jdk$LongAccumulatorStepper$$N();
    }

    @Override
    public boolean hasStep() {
        return this.scala$jdk$LongAccumulatorStepper$$N() > 0L;
    }

    @Override
    public long nextStep() {
        return this.nextStep$mcJ$sp();
    }

    @Override
    public LongStepper trySplit() {
        if (this.scala$jdk$LongAccumulatorStepper$$N() <= 1L) {
            return null;
        }
        long half = this.scala$jdk$LongAccumulatorStepper$$N() >> 1;
        long R = (this.h() <= 0 ? 0L : this.acc().cumulative(this.h() - 1)) + (long)this.scala$jdk$LongAccumulatorStepper$$i() + half;
        LongAccumulatorStepper ans = this.duplicateSelf(half);
        if (this.h() < this.acc().hIndex()) {
            long w = this.acc().seekSlot(R);
            this.h_$eq((int)(w >>> 32));
            if (this.h() < this.acc().hIndex()) {
                this.a_$eq(this.acc().history()[this.h()]);
                this.scala$jdk$LongAccumulatorStepper$$n_$eq(this.acc().cumulative(this.h()) - (this.h() > 0 ? this.acc().cumulative(this.h() - 1) : 0L));
            } else {
                this.a_$eq(this.acc().current());
                this.scala$jdk$LongAccumulatorStepper$$n_$eq(this.acc().index());
            }
            this.scala$jdk$LongAccumulatorStepper$$i_$eq((int)(w & 0xFFFFFFFFL));
        } else {
            this.scala$jdk$LongAccumulatorStepper$$i_$eq(this.scala$jdk$LongAccumulatorStepper$$i() + (int)half);
        }
        this.scala$jdk$LongAccumulatorStepper$$N_$eq(this.scala$jdk$LongAccumulatorStepper$$N() - half);
        return ans;
    }

    @Override
    public <B> Spliterator.OfLong spliterator() {
        return (Spliterator.OfLong)this.spliterator$mcJ$sp();
    }

    @Override
    public long nextStep$mcJ$sp() {
        if (this.scala$jdk$LongAccumulatorStepper$$n() <= 0L) {
            throw new NoSuchElementException("next on empty Stepper");
        }
        if ((long)this.scala$jdk$LongAccumulatorStepper$$i() >= this.scala$jdk$LongAccumulatorStepper$$n()) {
            this.scala$jdk$LongAccumulatorStepper$$loadMore();
        }
        long ans = this.scala$jdk$LongAccumulatorStepper$$a()[this.scala$jdk$LongAccumulatorStepper$$i()];
        this.scala$jdk$LongAccumulatorStepper$$i_$eq(this.scala$jdk$LongAccumulatorStepper$$i() + 1);
        this.scala$jdk$LongAccumulatorStepper$$N_$eq(this.scala$jdk$LongAccumulatorStepper$$N() - 1L);
        return ans;
    }

    @Override
    public <B> Spliterator<?> spliterator$mcJ$sp() {
        return new LongStepper.LongStepperSpliterator(this){
            private final /* synthetic */ LongAccumulatorStepper $outer;

            public boolean tryAdvance(LongConsumer c) {
                if (this.$outer.scala$jdk$LongAccumulatorStepper$$N() <= 0L) {
                    return false;
                }
                if ((long)this.$outer.scala$jdk$LongAccumulatorStepper$$i() >= this.$outer.scala$jdk$LongAccumulatorStepper$$n()) {
                    this.$outer.scala$jdk$LongAccumulatorStepper$$loadMore();
                }
                c.accept(this.$outer.scala$jdk$LongAccumulatorStepper$$a()[this.$outer.scala$jdk$LongAccumulatorStepper$$i()]);
                this.$outer.scala$jdk$LongAccumulatorStepper$$i_$eq(this.$outer.scala$jdk$LongAccumulatorStepper$$i() + 1);
                this.$outer.scala$jdk$LongAccumulatorStepper$$N_$eq(this.$outer.scala$jdk$LongAccumulatorStepper$$N() - 1L);
                return true;
            }

            public boolean tryAdvance(Consumer<? super Long> c) {
                if (c instanceof LongConsumer) {
                    LongConsumer longConsumer = (LongConsumer)((Object)c);
                    return this.tryAdvance(longConsumer);
                }
                if (this.$outer.scala$jdk$LongAccumulatorStepper$$N() <= 0L) {
                    return false;
                }
                if ((long)this.$outer.scala$jdk$LongAccumulatorStepper$$i() >= this.$outer.scala$jdk$LongAccumulatorStepper$$n()) {
                    this.$outer.scala$jdk$LongAccumulatorStepper$$loadMore();
                }
                c.accept((Long)this.$outer.scala$jdk$LongAccumulatorStepper$$a()[this.$outer.scala$jdk$LongAccumulatorStepper$$i()]);
                this.$outer.scala$jdk$LongAccumulatorStepper$$i_$eq(this.$outer.scala$jdk$LongAccumulatorStepper$$i() + 1);
                this.$outer.scala$jdk$LongAccumulatorStepper$$N_$eq(this.$outer.scala$jdk$LongAccumulatorStepper$$N() - 1L);
                return true;
            }

            public void forEachRemaining(LongConsumer c) {
                while (this.$outer.scala$jdk$LongAccumulatorStepper$$N() > 0L) {
                    if ((long)this.$outer.scala$jdk$LongAccumulatorStepper$$i() >= this.$outer.scala$jdk$LongAccumulatorStepper$$n()) {
                        this.$outer.scala$jdk$LongAccumulatorStepper$$loadMore();
                    }
                    int i0 = this.$outer.scala$jdk$LongAccumulatorStepper$$i();
                    if (this.$outer.scala$jdk$LongAccumulatorStepper$$n() - (long)this.$outer.scala$jdk$LongAccumulatorStepper$$i() > this.$outer.scala$jdk$LongAccumulatorStepper$$N()) {
                        this.$outer.scala$jdk$LongAccumulatorStepper$$n_$eq(this.$outer.scala$jdk$LongAccumulatorStepper$$i() + (int)this.$outer.scala$jdk$LongAccumulatorStepper$$N());
                    }
                    while ((long)this.$outer.scala$jdk$LongAccumulatorStepper$$i() < this.$outer.scala$jdk$LongAccumulatorStepper$$n()) {
                        c.accept(this.$outer.scala$jdk$LongAccumulatorStepper$$a()[this.$outer.scala$jdk$LongAccumulatorStepper$$i()]);
                        this.$outer.scala$jdk$LongAccumulatorStepper$$i_$eq(this.$outer.scala$jdk$LongAccumulatorStepper$$i() + 1);
                    }
                    this.$outer.scala$jdk$LongAccumulatorStepper$$N_$eq(this.$outer.scala$jdk$LongAccumulatorStepper$$N() - (this.$outer.scala$jdk$LongAccumulatorStepper$$n() - (long)i0));
                }
            }

            public void forEachRemaining(Consumer<? super Long> c) {
                if (c instanceof LongConsumer) {
                    LongConsumer longConsumer = (LongConsumer)((Object)c);
                    this.forEachRemaining(longConsumer);
                    return;
                }
                while (this.$outer.scala$jdk$LongAccumulatorStepper$$N() > 0L) {
                    if ((long)this.$outer.scala$jdk$LongAccumulatorStepper$$i() >= this.$outer.scala$jdk$LongAccumulatorStepper$$n()) {
                        this.$outer.scala$jdk$LongAccumulatorStepper$$loadMore();
                    }
                    int i0 = this.$outer.scala$jdk$LongAccumulatorStepper$$i();
                    if (this.$outer.scala$jdk$LongAccumulatorStepper$$n() - (long)this.$outer.scala$jdk$LongAccumulatorStepper$$i() > this.$outer.scala$jdk$LongAccumulatorStepper$$N()) {
                        this.$outer.scala$jdk$LongAccumulatorStepper$$n_$eq(this.$outer.scala$jdk$LongAccumulatorStepper$$i() + (int)this.$outer.scala$jdk$LongAccumulatorStepper$$N());
                    }
                    while ((long)this.$outer.scala$jdk$LongAccumulatorStepper$$i() < this.$outer.scala$jdk$LongAccumulatorStepper$$n()) {
                        c.accept((Long)this.$outer.scala$jdk$LongAccumulatorStepper$$a()[this.$outer.scala$jdk$LongAccumulatorStepper$$i()]);
                        this.$outer.scala$jdk$LongAccumulatorStepper$$i_$eq(this.$outer.scala$jdk$LongAccumulatorStepper$$i() + 1);
                    }
                    this.$outer.scala$jdk$LongAccumulatorStepper$$N_$eq(this.$outer.scala$jdk$LongAccumulatorStepper$$N() - (this.$outer.scala$jdk$LongAccumulatorStepper$$n() - (long)i0));
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

    public LongAccumulatorStepper(LongAccumulator acc) {
        this.acc = acc;
        this.h = 0;
        this.scala$jdk$LongAccumulatorStepper$$i = 0;
        this.scala$jdk$LongAccumulatorStepper$$a = acc.hIndex() > 0 ? acc.history()[0] : acc.current();
        this.scala$jdk$LongAccumulatorStepper$$n = acc.hIndex() > 0 ? acc.cumulative(0) : (long)acc.index();
        this.scala$jdk$LongAccumulatorStepper$$N = acc.totalSize();
    }
}

