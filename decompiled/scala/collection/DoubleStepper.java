/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import java.util.Iterator;
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import scala.collection.Stepper$mcD$sp;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005eaa\u0002\n\u0014!\u0003\r\t\u0001\u0007\u0005\u0006I\u0001!\t!\n\u0005\u0006S\u00011\tA\u000b\u0005\u0006Y\u0001!\t!\f\u0005\u0006\u0003\u0002!\tAQ\u0004\u0006\u0017NA\t\u0001\u0014\u0004\u0006%MA\t!\u0014\u0005\u0006\u001d\u001a!\ta\u0014\u0004\u0005!\u001a\u0001\u0011\u000b\u0003\u0005Y\u0011\t\u0005\t\u0015!\u0003,\u0011\u0015q\u0005\u0002\"\u0001Z\u0011\u0015i\u0006\u0002\"\u0001_\u0011\u0015i\u0006\u0002\"\u0011k\u0011\u0015I\u0003\u0002\"\u0011x\u0011\u0015A\b\u0002\"\u0001z\u0011\u0015i\b\u0002\"\u0001\u007f\u0011\u001d\t)\u0001\u0003C!\u0003\u000fAq!!\u0002\t\t\u0003\nYAA\u0007E_V\u0014G.Z*uKB\u0004XM\u001d\u0006\u0003)U\t!bY8mY\u0016\u001cG/[8o\u0015\u00051\u0012!B:dC2\f7\u0001A\n\u0004\u0001ei\u0002C\u0001\u000e\u001c\u001b\u0005)\u0012B\u0001\u000f\u0016\u0005\u0019\te.\u001f*fMB\u0019adH\u0011\u000e\u0003MI!\u0001I\n\u0003\u000fM#X\r\u001d9feB\u0011!DI\u0005\u0003GU\u0011a\u0001R8vE2,\u0017A\u0002\u0013j]&$H\u0005F\u0001'!\tQr%\u0003\u0002)+\t!QK\\5u\u0003!!(/_*qY&$H#A\u0016\u0011\u0005y\u0001\u0011aC:qY&$XM]1u_J,\"AL\u001e\u0016\u0003=\u0002\"\u0001\r\u001d\u000f\u0005E2T\"\u0001\u001a\u000b\u0005M\"\u0014\u0001B;uS2T\u0011!N\u0001\u0005U\u00064\u0018-\u0003\u00028e\u0005Y1\u000b\u001d7ji\u0016\u0014\u0018\r^8s\u0013\tI$H\u0001\u0005PM\u0012{WO\u00197f\u0015\t9$\u0007B\u0003=\u0007\t\u0007QHA\u0001C#\t\tc\b\u0005\u0002\u001b\u007f%\u0011\u0001)\u0006\u0002\u0004\u0003:L\u0018\u0001\u00046bm\u0006LE/\u001a:bi>\u0014XCA\"K+\u0005!\u0005CA#I\u001d\t\td)\u0003\u0002He\u0005\t\u0002K]5nSRLg/Z%uKJ\fGo\u001c:\n\u0005eJ%BA$3\t\u0015aDA1\u0001>\u00035!u.\u001e2mKN#X\r\u001d9feB\u0011aDB\n\u0003\re\ta\u0001P5oSRtD#\u0001'\u00031\u0011{WO\u00197f'R,\u0007\u000f]3s'Bd\u0017\u000e^3sCR|'oE\u0002\t%>\u0002\"a\u0015,\u000e\u0003QS!!\u0016\u001b\u0002\t1\fgnZ\u0005\u0003/R\u0013aa\u00142kK\u000e$\u0018!A:\u0015\u0005ic\u0006CA.\t\u001b\u00051\u0001\"\u0002-\u000b\u0001\u0004Y\u0013A\u0003;ss\u0006#g/\u00198dKR\u0011qL\u0019\t\u00035\u0001L!!Y\u000b\u0003\u000f\t{w\u000e\\3b]\")1m\u0003a\u0001I\u0006\t1\r\u0005\u0002fQ6\taM\u0003\u0002he\u0005Aa-\u001e8di&|g.\u0003\u0002jM\nqAi\\;cY\u0016\u001cuN\\:v[\u0016\u0014HCA0l\u0011\u0015\u0019G\u00021\u0001ma\ti'\u000fE\u0002f]BL!a\u001c4\u0003\u0011\r{gn];nKJ\u0004\"!\u001d:\r\u0001\u0011I1o[A\u0001\u0002\u0003\u0015\t\u0001\u001e\u0002\u0004?\u0012:\u0014CA;?!\t\u0019f/\u0003\u0002$)R\tq&\u0001\u0007fgRLW.\u0019;f'&TX\rF\u0001{!\tQ20\u0003\u0002}+\t!Aj\u001c8h\u0003=\u0019\u0007.\u0019:bGR,'/[:uS\u000e\u001cH#A@\u0011\u0007i\t\t!C\u0002\u0002\u0004U\u00111!\u00138u\u0003A1wN]#bG\"\u0014V-\\1j]&tw\rF\u0002'\u0003\u0013AQa\u0019\tA\u0002\u0011$2AJA\u0007\u0011\u0019\u0019\u0017\u00031\u0001\u0002\u0010A\"\u0011\u0011CA\u000b!\u0011)g.a\u0005\u0011\u0007E\f)\u0002B\u0006\u0002\u0018\u00055\u0011\u0011!A\u0001\u0006\u0003!(aA0%q\u0001")
public interface DoubleStepper
extends Stepper$mcD$sp {
    public DoubleStepper trySplit();

    public static /* synthetic */ Spliterator.OfDouble spliterator$(DoubleStepper $this) {
        return $this.spliterator();
    }

    default public <B> Spliterator.OfDouble spliterator() {
        return (Spliterator.OfDouble)this.spliterator$mcD$sp();
    }

    public static /* synthetic */ PrimitiveIterator.OfDouble javaIterator$(DoubleStepper $this) {
        return $this.javaIterator();
    }

    default public <B> PrimitiveIterator.OfDouble javaIterator() {
        return (PrimitiveIterator.OfDouble)this.javaIterator$mcD$sp();
    }

    public static /* synthetic */ Spliterator spliterator$mcD$sp$(DoubleStepper $this) {
        return $this.spliterator$mcD$sp();
    }

    @Override
    default public <B> Spliterator<?> spliterator$mcD$sp() {
        return new DoubleStepperSpliterator(this);
    }

    public static /* synthetic */ Iterator javaIterator$mcD$sp$(DoubleStepper $this) {
        return $this.javaIterator$mcD$sp();
    }

    @Override
    default public <B> Iterator<?> javaIterator$mcD$sp() {
        return new PrimitiveIterator.OfDouble(this){
            private final /* synthetic */ DoubleStepper $outer;

            public void forEachRemaining(DoubleConsumer x$1) {
                PrimitiveIterator.OfDouble.super.forEachRemaining(x$1);
            }

            public Double next() {
                return PrimitiveIterator.OfDouble.super.next();
            }

            public void forEachRemaining(Consumer<? super Double> x$1) {
                PrimitiveIterator.OfDouble.super.forEachRemaining(x$1);
            }

            public boolean hasNext() {
                return this.$outer.hasStep();
            }

            public double nextDouble() {
                return this.$outer.nextStep$mcD$sp();
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
            }
        };
    }

    public static void $init$(DoubleStepper $this) {
    }

    public static class DoubleStepperSpliterator
    implements Spliterator.OfDouble {
        private final DoubleStepper s;

        @Override
        public boolean tryAdvance(DoubleConsumer c) {
            if (this.s.hasStep()) {
                c.accept(this.s.nextStep$mcD$sp());
                return true;
            }
            return false;
        }

        @Override
        public boolean tryAdvance(Consumer<? super Double> c) {
            if (c instanceof DoubleConsumer) {
                DoubleConsumer doubleConsumer = (DoubleConsumer)((Object)c);
                return this.tryAdvance(doubleConsumer);
            }
            if (this.s.hasStep()) {
                c.accept((Double)this.s.nextStep$mcD$sp());
                return true;
            }
            return false;
        }

        @Override
        public Spliterator.OfDouble trySplit() {
            DoubleStepper sp2 = this.s.trySplit();
            if (sp2 == null) {
                return null;
            }
            return sp2.spliterator();
        }

        @Override
        public long estimateSize() {
            return this.s.estimateSize();
        }

        @Override
        public int characteristics() {
            return this.s.characteristics();
        }

        @Override
        public void forEachRemaining(DoubleConsumer c) {
            while (this.s.hasStep()) {
                c.accept(this.s.nextStep$mcD$sp());
            }
        }

        @Override
        public void forEachRemaining(Consumer<? super Double> c) {
            if (c instanceof DoubleConsumer) {
                DoubleConsumer doubleConsumer = (DoubleConsumer)((Object)c);
                this.forEachRemaining(doubleConsumer);
                return;
            }
            while (this.s.hasStep()) {
                c.accept((Double)this.s.nextStep$mcD$sp());
            }
        }

        public DoubleStepperSpliterator(DoubleStepper s2) {
            this.s = s2;
        }
    }
}

