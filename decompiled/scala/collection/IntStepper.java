/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import java.util.Iterator;
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import scala.collection.Stepper$mcI$sp;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005Uaa\u0002\n\u0014!\u0003\r\t\u0001\u0007\u0005\u0006I\u0001!\t!\n\u0005\u0006S\u00011\tA\u000b\u0005\u0006Y\u0001!\t!\f\u0005\u0006\u0003\u0002!\tAQ\u0004\u0006\u0017NA\t\u0001\u0014\u0004\u0006%MA\t!\u0014\u0005\u0006\u001d\u001a!\ta\u0014\u0004\u0005!\u001a\u0001\u0011\u000b\u0003\u0005Y\u0011\t\u0005\t\u0015!\u0003,\u0011\u0015q\u0005\u0002\"\u0001Z\u0011\u0015i\u0006\u0002\"\u0001_\u0011\u0015i\u0006\u0002\"\u0011k\u0011\u0015I\u0003\u0002\"\u0011y\u0011\u0015I\b\u0002\"\u0001{\u0011\u0015q\b\u0002\"\u0001\u0000\u0011\u001d\t\t\u0001\u0003C!\u0003\u0007Aq!!\u0001\t\t\u0003\n9A\u0001\u0006J]R\u001cF/\u001a9qKJT!\u0001F\u000b\u0002\u0015\r|G\u000e\\3di&|gNC\u0001\u0017\u0003\u0015\u00198-\u00197b\u0007\u0001\u00192\u0001A\r\u001e!\tQ2$D\u0001\u0016\u0013\taRC\u0001\u0004B]f\u0014VM\u001a\t\u0004=}\tS\"A\n\n\u0005\u0001\u001a\"aB*uKB\u0004XM\u001d\t\u00035\tJ!aI\u000b\u0003\u0007%sG/\u0001\u0004%S:LG\u000f\n\u000b\u0002MA\u0011!dJ\u0005\u0003QU\u0011A!\u00168ji\u0006AAO]=Ta2LG\u000fF\u0001,!\tq\u0002!A\u0006ta2LG/\u001a:bi>\u0014XC\u0001\u0018<+\u0005y\u0003C\u0001\u00199\u001d\t\td'D\u00013\u0015\t\u0019D'\u0001\u0003vi&d'\"A\u001b\u0002\t)\fg/Y\u0005\u0003oI\n1b\u00159mSR,'/\u0019;pe&\u0011\u0011H\u000f\u0002\u0006\u001f\u001aLe\u000e\u001e\u0006\u0003oI\"Q\u0001P\u0002C\u0002u\u0012\u0011AQ\t\u0003Cy\u0002\"AG \n\u0005\u0001+\"aA!os\u0006a!.\u0019<b\u0013R,'/\u0019;peV\u00111IS\u000b\u0002\tB\u0011Q\t\u0013\b\u0003c\u0019K!a\u0012\u001a\u0002#A\u0013\u0018.\\5uSZ,\u0017\n^3sCR|'/\u0003\u0002:\u0013*\u0011qI\r\u0003\u0006y\u0011\u0011\r!P\u0001\u000b\u0013:$8\u000b^3qa\u0016\u0014\bC\u0001\u0010\u0007'\t1\u0011$\u0001\u0004=S:LGO\u0010\u000b\u0002\u0019\n)\u0012J\u001c;Ti\u0016\u0004\b/\u001a:Ta2LG/\u001a:bi>\u00148c\u0001\u0005S_A\u00111KV\u0007\u0002)*\u0011Q\u000bN\u0001\u0005Y\u0006tw-\u0003\u0002X)\n1qJ\u00196fGR\f\u0011a\u001d\u000b\u00035r\u0003\"a\u0017\u0005\u000e\u0003\u0019AQ\u0001\u0017\u0006A\u0002-\n!\u0002\u001e:z\u0003\u00124\u0018M\\2f)\ty&\r\u0005\u0002\u001bA&\u0011\u0011-\u0006\u0002\b\u0005>|G.Z1o\u0011\u0015\u00197\u00021\u0001e\u0003\u0005\u0019\u0007CA3i\u001b\u00051'BA43\u0003!1WO\\2uS>t\u0017BA5g\u0005-Ie\u000e^\"p]N,X.\u001a:\u0015\u0005}[\u0007\"B2\r\u0001\u0004a\u0007GA7s!\r)g\u000e]\u0005\u0003_\u001a\u0014\u0001bQ8ogVlWM\u001d\t\u0003cJd\u0001\u0001B\u0005tW\u0006\u0005\t\u0011!B\u0001i\n\u0019q\fJ\u001b\u0012\u0005Ut\u0004CA*w\u0013\t9HKA\u0004J]R,w-\u001a:\u0015\u0003=\nA\"Z:uS6\fG/Z*ju\u0016$\u0012a\u001f\t\u00035qL!!`\u000b\u0003\t1{gnZ\u0001\u0010G\"\f'/Y2uKJL7\u000f^5dgR\t\u0011%\u0001\tg_J,\u0015m\u00195SK6\f\u0017N\\5oOR\u0019a%!\u0002\t\u000b\r\u0004\u0002\u0019\u00013\u0015\u0007\u0019\nI\u0001\u0003\u0004d#\u0001\u0007\u00111\u0002\u0019\u0005\u0003\u001b\t\t\u0002\u0005\u0003f]\u0006=\u0001cA9\u0002\u0012\u0011Y\u00111CA\u0005\u0003\u0003\u0005\tQ!\u0001u\u0005\ryFE\u000e")
public interface IntStepper
extends Stepper$mcI$sp {
    public IntStepper trySplit();

    public static /* synthetic */ Spliterator.OfInt spliterator$(IntStepper $this) {
        return $this.spliterator();
    }

    default public <B> Spliterator.OfInt spliterator() {
        return (Spliterator.OfInt)this.spliterator$mcI$sp();
    }

    public static /* synthetic */ PrimitiveIterator.OfInt javaIterator$(IntStepper $this) {
        return $this.javaIterator();
    }

    default public <B> PrimitiveIterator.OfInt javaIterator() {
        return (PrimitiveIterator.OfInt)this.javaIterator$mcI$sp();
    }

    public static /* synthetic */ Spliterator spliterator$mcI$sp$(IntStepper $this) {
        return $this.spliterator$mcI$sp();
    }

    @Override
    default public <B> Spliterator<?> spliterator$mcI$sp() {
        return new IntStepperSpliterator(this);
    }

    public static /* synthetic */ Iterator javaIterator$mcI$sp$(IntStepper $this) {
        return $this.javaIterator$mcI$sp();
    }

    @Override
    default public <B> Iterator<?> javaIterator$mcI$sp() {
        return new PrimitiveIterator.OfInt(this){
            private final /* synthetic */ IntStepper $outer;

            public void forEachRemaining(IntConsumer x$1) {
                PrimitiveIterator.OfInt.super.forEachRemaining(x$1);
            }

            public Integer next() {
                return PrimitiveIterator.OfInt.super.next();
            }

            public void forEachRemaining(Consumer<? super Integer> x$1) {
                PrimitiveIterator.OfInt.super.forEachRemaining(x$1);
            }

            public boolean hasNext() {
                return this.$outer.hasStep();
            }

            public int nextInt() {
                return this.$outer.nextStep$mcI$sp();
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
            }
        };
    }

    public static void $init$(IntStepper $this) {
    }

    public static class IntStepperSpliterator
    implements Spliterator.OfInt {
        private final IntStepper s;

        @Override
        public boolean tryAdvance(IntConsumer c) {
            if (this.s.hasStep()) {
                c.accept(this.s.nextStep$mcI$sp());
                return true;
            }
            return false;
        }

        @Override
        public boolean tryAdvance(Consumer<? super Integer> c) {
            if (c instanceof IntConsumer) {
                IntConsumer intConsumer = (IntConsumer)((Object)c);
                return this.tryAdvance(intConsumer);
            }
            if (this.s.hasStep()) {
                c.accept((Integer)this.s.nextStep$mcI$sp());
                return true;
            }
            return false;
        }

        @Override
        public Spliterator.OfInt trySplit() {
            IntStepper sp2 = this.s.trySplit();
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
        public void forEachRemaining(IntConsumer c) {
            while (this.s.hasStep()) {
                c.accept(this.s.nextStep$mcI$sp());
            }
        }

        @Override
        public void forEachRemaining(Consumer<? super Integer> c) {
            if (c instanceof IntConsumer) {
                IntConsumer intConsumer = (IntConsumer)((Object)c);
                this.forEachRemaining(intConsumer);
                return;
            }
            while (this.s.hasStep()) {
                c.accept((Integer)this.s.nextStep$mcI$sp());
            }
        }

        public IntStepperSpliterator(IntStepper s2) {
            this.s = s2;
        }
    }
}

