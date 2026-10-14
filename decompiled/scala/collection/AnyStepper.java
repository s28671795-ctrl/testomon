/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import java.util.Comparator;
import java.util.Spliterator;
import java.util.function.Consumer;
import scala.collection.DoubleStepper;
import scala.collection.IntStepper;
import scala.collection.Iterator;
import scala.collection.LongStepper;
import scala.collection.Stepper;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005Mha\u0002\u00180!\u0003\r\t\u0001\u000e\u0005\u0006\u0013\u0002!\tA\u0013\u0005\u0006\u001d\u00021\ta\u0014\u0005\u0006#\u0002!\tA\u0015\u0005\u0006A\u0002!\t!Y\u0004\u0006Q>B\t!\u001b\u0004\u0006]=B\tA\u001b\u0005\u0006W\u001a!\t\u0001\u001c\u0004\u0005[\u001a\u0001a\u000e\u0003\u0005z\u0011\t\u0005\t\u0015!\u0003{\u0011\u0015Y\u0007\u0002\"\u0001|\u0011\u0019y\b\u0002\"\u0001\u0002\u0002!1a\n\u0003C\u0001\u0003GAq!!\n\t\t\u0003\t9\u0003C\u0004\u00020!!\t!!\r\t\u000f\u0005e\u0002\u0002\"\u0011\u0002<!9\u0011\u0011\n\u0004\u0005\u0002\u0005-\u0003bBA0\r\u0011\u0005\u0011\u0011\r\u0005\b\u0003\u00173A\u0011AAG\u0011\u001d\tIJ\u0002C\u0001\u00037Cq!a*\u0007\t\u0003\tI\u000bC\u0004\u00026\u001a!\t!a.\u0007\u000f\u0005\rg\u0001A\u0018\u0002F\"Q\u0011q\u000b\f\u0003\u0002\u0003\u0006I!!\u0017\t\r-4B\u0011AAd\u0011\u001d\tiM\u0006C\u0001\u0003\u001fDq!!5\u0017\t\u0003\t\u0019\u000eC\u0004\u0002&Y!\t!!6\t\u000f\u0005=b\u0003\"\u0001\u0002X\"1aJ\u0006C\u0001\u000334q!a7\u0007\u0001=\ni\u000e\u0003\u0006\u0002Xy\u0011\t\u0011)A\u0005\u0003'Caa\u001b\u0010\u0005\u0002\u0005}\u0007bBAg=\u0011\u0005\u0011q\u001a\u0005\b\u0003#tB\u0011AA\u0019\u0011\u001d\t)C\bC\u0001\u0003+Dq!a\f\u001f\t\u0003\t9\u000e\u0003\u0004O=\u0011\u0005\u0011Q\u001d\u0004\b\u0003O4\u0001aLAu\u0011)\t9F\nB\u0001B\u0003%\u0011q\u0016\u0005\u0007W\u001a\"\t!a;\t\u000f\u00055g\u0005\"\u0001\u0002P\"9\u0011\u0011\u001b\u0014\u0005\u0002\u0005\u001d\u0002bBA\u0013M\u0011\u0005\u0011Q\u001b\u0005\b\u0003_1C\u0011AAl\u0011\u0019qe\u0005\"\u0001\u0002r\nQ\u0011I\\=Ti\u0016\u0004\b/\u001a:\u000b\u0005A\n\u0014AC2pY2,7\r^5p]*\t!'A\u0003tG\u0006d\u0017m\u0001\u0001\u0016\u0005U\u00025c\u0001\u00017uA\u0011q\u0007O\u0007\u0002c%\u0011\u0011(\r\u0002\u0007\u0003:L(+\u001a4\u0011\u0007mbd(D\u00010\u0013\titFA\u0004Ti\u0016\u0004\b/\u001a:\u0011\u0005}\u0002E\u0002\u0001\u0003\u0007\u0003\u0002!)\u0019\u0001\"\u0003\u0003\u0005\u000b\"a\u0011$\u0011\u0005]\"\u0015BA#2\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"aN$\n\u0005!\u000b$aA!os\u00061A%\u001b8ji\u0012\"\u0012a\u0013\t\u0003o1K!!T\u0019\u0003\tUs\u0017\u000e^\u0001\tiJL8\u000b\u001d7jiR\t\u0001\u000bE\u0002<\u0001y\n1b\u001d9mSR,'/\u0019;peV\u00111+X\u000b\u0002)B\u0019QK\u0017/\u000e\u0003YS!a\u0016-\u0002\tU$\u0018\u000e\u001c\u0006\u00023\u0006!!.\u0019<b\u0013\tYfKA\u0006Ta2LG/\u001a:bi>\u0014\bCA ^\t\u0015q6A1\u0001`\u0005\u0005\u0011\u0015C\u0001 G\u00031Q\u0017M^1Ji\u0016\u0014\u0018\r^8s+\t\u0011w-F\u0001d!\r)FMZ\u0005\u0003KZ\u0013\u0001\"\u0013;fe\u0006$xN\u001d\t\u0003\u007f\u001d$QA\u0018\u0003C\u0002}\u000b!\"\u00118z'R,\u0007\u000f]3s!\tYda\u0005\u0002\u0007m\u00051A(\u001b8jiz\"\u0012!\u001b\u0002\u0016\u0003:L8\u000b^3qa\u0016\u00148\u000b\u001d7ji\u0016\u0014\u0018\r^8s+\ty\u0007pE\u0002\taZ\u0004\"!\u001d;\u000e\u0003IT!a\u001d-\u0002\t1\fgnZ\u0005\u0003kJ\u0014aa\u00142kK\u000e$\bcA+[oB\u0011q\b\u001f\u0003\u0006\u0003\"\u0011\rAQ\u0001\u0002gB\u00191\bA<\u0015\u0005qt\bcA?\to6\ta\u0001C\u0003z\u0015\u0001\u0007!0\u0001\u0006uef\fEM^1oG\u0016$B!a\u0001\u0002\nA\u0019q'!\u0002\n\u0007\u0005\u001d\u0011GA\u0004C_>dW-\u00198\t\u000f\u0005-1\u00021\u0001\u0002\u000e\u0005\t1\r\r\u0003\u0002\u0010\u0005u\u0001CBA\t\u0003/\tY\"\u0004\u0002\u0002\u0014)\u0019\u0011Q\u0003,\u0002\u0011\u0019,hn\u0019;j_:LA!!\u0007\u0002\u0014\tA1i\u001c8tk6,'\u000fE\u0002@\u0003;!A\"a\b\u0002\n\u0005\u0005\t\u0011!B\u0001\u0003C\u00111a\u0018\u00134#\t9h\tF\u0001w\u00031)7\u000f^5nCR,7+\u001b>f)\t\tI\u0003E\u00028\u0003WI1!!\f2\u0005\u0011auN\\4\u0002\u001f\rD\u0017M]1di\u0016\u0014\u0018n\u001d;jGN$\"!a\r\u0011\u0007]\n)$C\u0002\u00028E\u00121!\u00138u\u0003A1wN]#bG\"\u0014V-\\1j]&tw\rF\u0002L\u0003{Aq!a\u0003\u0010\u0001\u0004\ty\u0004\r\u0003\u0002B\u0005\u0015\u0003CBA\t\u0003/\t\u0019\u0005E\u0002@\u0003\u000b\"A\"a\u0012\u0002>\u0005\u0005\t\u0011!B\u0001\u0003C\u00111a\u0018\u00135\u0003IygmU3r\t>,(\r\\3Ti\u0016\u0004\b/\u001a:\u0015\t\u00055\u0013Q\u000b\t\u0005w\u0001\ty\u0005E\u00028\u0003#J1!a\u00152\u0005\u0019!u.\u001e2mK\"9\u0011q\u000b\tA\u0002\u0005e\u0013AA:u!\rY\u00141L\u0005\u0004\u0003;z#!\u0004#pk\ndWm\u0015;faB,'/\u0001\npMB\u000b'\u000fR8vE2,7\u000b^3qa\u0016\u0014H\u0003BA2\u0003\u000b\u0013b!!\u001a\u0002N\u0005%dABA4\r\u0001\t\u0019G\u0001\u0007=e\u00164\u0017N\\3nK:$h\b\u0005\u0003\u0002l\u0005}d\u0002BA7\u0003wrA!a\u001c\u0002z9!\u0011\u0011OA<\u001b\t\t\u0019HC\u0002\u0002vM\na\u0001\u0010:p_Rt\u0014\"\u0001\u001a\n\u0005A\n\u0014bAA?_\u000591\u000b^3qa\u0016\u0014\u0018\u0002BAA\u0003\u0007\u0013a\"\u00124gS\u000eLWM\u001c;Ta2LGOC\u0002\u0002~=Bq!a\u0016\u0012\u0001\u0004\t9I\u0005\u0004\u0002\n\u0006e\u0013\u0011\u000e\u0004\u0007\u0003O2\u0001!a\"\u0002\u001f=47+Z9J]R\u001cF/\u001a9qKJ$B!a$\u0002\u0012B!1\bAA\u001a\u0011\u001d\t9F\u0005a\u0001\u0003'\u00032aOAK\u0013\r\t9j\f\u0002\u000b\u0013:$8\u000b^3qa\u0016\u0014\u0018aD8g!\u0006\u0014\u0018J\u001c;Ti\u0016\u0004\b/\u001a:\u0015\t\u0005u\u0015\u0011\u0015\n\u0007\u0003?\u000by)!\u001b\u0007\r\u0005\u001dd\u0001AAO\u0011\u001d\t9f\u0005a\u0001\u0003G\u0013b!!*\u0002\u0014\u0006%dABA4\r\u0001\t\u0019+\u0001\tpMN+\u0017\u000fT8oON#X\r\u001d9feR!\u00111VAW!\u0011Y\u0004!!\u000b\t\u000f\u0005]C\u00031\u0001\u00020B\u00191(!-\n\u0007\u0005MvFA\u0006M_:<7\u000b^3qa\u0016\u0014\u0018\u0001E8g!\u0006\u0014Hj\u001c8h'R,\u0007\u000f]3s)\u0011\tI,!0\u0013\r\u0005m\u00161VA5\r\u0019\t9G\u0002\u0001\u0002:\"9\u0011qK\u000bA\u0002\u0005}&CBAa\u0003_\u000bIG\u0002\u0004\u0002h\u0019\u0001\u0011q\u0018\u0002\u0013\u0005>DX\r\u001a#pk\ndWm\u0015;faB,'o\u0005\u0003\u0017m\u00055C\u0003BAe\u0003\u0017\u0004\"! \f\t\u000f\u0005]\u0003\u00041\u0001\u0002Z\u00059\u0001.Y:Ti\u0016\u0004XCAA\u0002\u0003!qW\r\u001f;Ti\u0016\u0004HCAA(+\t\tI#\u0006\u0002\u00024Q\u0011\u0011Q\n\u0002\u0010\u0005>DX\rZ%oiN#X\r\u001d9feN!aDNAH)\u0011\t\t/a9\u0011\u0005ut\u0002bBA,A\u0001\u0007\u00111\u0013\u000b\u0003\u0003\u001f\u0013\u0001CQ8yK\u0012duN\\4Ti\u0016\u0004\b/\u001a:\u0014\t\u00192\u00141\u0016\u000b\u0005\u0003[\fy\u000f\u0005\u0002~M!9\u0011q\u000b\u0015A\u0002\u0005=FCAAV\u0001")
public interface AnyStepper<A>
extends Stepper<A> {
    public static AnyStepper<Object> ofParLongStepper(LongStepper st) {
        return new Stepper.EfficientSplit(st){};
    }

    public static AnyStepper<Object> ofSeqLongStepper(LongStepper st) {
        return new BoxedLongStepper(st);
    }

    public static AnyStepper<Object> ofParIntStepper(IntStepper st) {
        return new Stepper.EfficientSplit(st){};
    }

    public static AnyStepper<Object> ofSeqIntStepper(IntStepper st) {
        return new BoxedIntStepper(st);
    }

    public static AnyStepper<Object> ofParDoubleStepper(DoubleStepper st) {
        return new Stepper.EfficientSplit(st){};
    }

    public static AnyStepper<Object> ofSeqDoubleStepper(DoubleStepper st) {
        return new BoxedDoubleStepper(st);
    }

    @Override
    public AnyStepper<A> trySplit();

    @Override
    default public <B> Spliterator<B> spliterator() {
        return new AnyStepperSpliterator(this);
    }

    @Override
    default public <B> java.util.Iterator<B> javaIterator() {
        return new java.util.Iterator<B>(this){
            private final /* synthetic */ AnyStepper $outer;

            public void remove() {
                java.util.Iterator.super.remove();
            }

            public void forEachRemaining(Consumer<? super B> x$1) {
                java.util.Iterator.super.forEachRemaining(x$1);
            }

            public boolean hasNext() {
                return this.$outer.hasStep();
            }

            public B next() {
                return (B)this.$outer.nextStep();
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
            }
        };
    }

    public static void $init$(AnyStepper $this) {
    }

    public static class AnyStepperSpliterator<A>
    implements Spliterator<A> {
        private final AnyStepper<A> s;

        @Override
        public long getExactSizeIfKnown() {
            return Spliterator.super.getExactSizeIfKnown();
        }

        @Override
        public boolean hasCharacteristics(int x$1) {
            return Spliterator.super.hasCharacteristics(x$1);
        }

        @Override
        public Comparator<? super A> getComparator() {
            return Spliterator.super.getComparator();
        }

        @Override
        public boolean tryAdvance(Consumer<? super A> c) {
            if (this.s.hasStep()) {
                c.accept(this.s.nextStep());
                return true;
            }
            return false;
        }

        @Override
        public Spliterator<A> trySplit() {
            AnyStepper<A> sp2 = this.s.trySplit();
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
        public void forEachRemaining(Consumer<? super A> c) {
            while (this.s.hasStep()) {
                c.accept(this.s.nextStep());
            }
        }

        public AnyStepperSpliterator(AnyStepper<A> s2) {
            this.s = s2;
        }
    }

    public static class BoxedDoubleStepper
    implements AnyStepper<Object> {
        public final DoubleStepper scala$collection$AnyStepper$BoxedDoubleStepper$$st;

        @Override
        public <B> Spliterator<B> spliterator() {
            return ((AnyStepper)this).spliterator();
        }

        @Override
        public <B> java.util.Iterator<B> javaIterator() {
            return ((AnyStepper)this).javaIterator();
        }

        @Override
        public int nextStep$mcI$sp() {
            return Stepper.nextStep$mcI$sp$(this);
        }

        @Override
        public long nextStep$mcJ$sp() {
            return Stepper.nextStep$mcJ$sp$(this);
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
        public <B> Spliterator<?> spliterator$mcJ$sp() {
            return Stepper.spliterator$mcJ$sp$(this);
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
        public <B> java.util.Iterator<?> javaIterator$mcJ$sp() {
            return Stepper.javaIterator$mcJ$sp$(this);
        }

        @Override
        public Iterator<Object> iterator() {
            return Stepper.iterator$(this);
        }

        @Override
        public boolean hasStep() {
            return this.scala$collection$AnyStepper$BoxedDoubleStepper$$st.hasStep();
        }

        @Override
        public double nextStep() {
            return this.nextStep$mcD$sp();
        }

        @Override
        public long estimateSize() {
            return this.scala$collection$AnyStepper$BoxedDoubleStepper$$st.estimateSize();
        }

        @Override
        public int characteristics() {
            return this.scala$collection$AnyStepper$BoxedDoubleStepper$$st.characteristics();
        }

        @Override
        public AnyStepper<Object> trySplit() {
            return (AnyStepper)this.trySplit$mcD$sp();
        }

        @Override
        public double nextStep$mcD$sp() {
            return this.scala$collection$AnyStepper$BoxedDoubleStepper$$st.nextStep$mcD$sp();
        }

        @Override
        public Stepper<Object> trySplit$mcD$sp() {
            DoubleStepper s2 = this.scala$collection$AnyStepper$BoxedDoubleStepper$$st.trySplit();
            if (s2 == null) {
                return null;
            }
            return new BoxedDoubleStepper(s2);
        }

        public BoxedDoubleStepper(DoubleStepper st) {
            this.scala$collection$AnyStepper$BoxedDoubleStepper$$st = st;
        }
    }

    public static class BoxedIntStepper
    implements AnyStepper<Object> {
        public final IntStepper scala$collection$AnyStepper$BoxedIntStepper$$st;

        @Override
        public <B> Spliterator<B> spliterator() {
            return ((AnyStepper)this).spliterator();
        }

        @Override
        public <B> java.util.Iterator<B> javaIterator() {
            return ((AnyStepper)this).javaIterator();
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
        public <B> Spliterator<?> spliterator$mcJ$sp() {
            return Stepper.spliterator$mcJ$sp$(this);
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
        public <B> java.util.Iterator<?> javaIterator$mcJ$sp() {
            return Stepper.javaIterator$mcJ$sp$(this);
        }

        @Override
        public Iterator<Object> iterator() {
            return Stepper.iterator$(this);
        }

        @Override
        public boolean hasStep() {
            return this.scala$collection$AnyStepper$BoxedIntStepper$$st.hasStep();
        }

        @Override
        public int nextStep() {
            return this.nextStep$mcI$sp();
        }

        @Override
        public long estimateSize() {
            return this.scala$collection$AnyStepper$BoxedIntStepper$$st.estimateSize();
        }

        @Override
        public int characteristics() {
            return this.scala$collection$AnyStepper$BoxedIntStepper$$st.characteristics();
        }

        @Override
        public AnyStepper<Object> trySplit() {
            return (AnyStepper)this.trySplit$mcI$sp();
        }

        @Override
        public int nextStep$mcI$sp() {
            return this.scala$collection$AnyStepper$BoxedIntStepper$$st.nextStep$mcI$sp();
        }

        @Override
        public Stepper<Object> trySplit$mcI$sp() {
            IntStepper s2 = this.scala$collection$AnyStepper$BoxedIntStepper$$st.trySplit();
            if (s2 == null) {
                return null;
            }
            return new BoxedIntStepper(s2);
        }

        public BoxedIntStepper(IntStepper st) {
            this.scala$collection$AnyStepper$BoxedIntStepper$$st = st;
        }
    }

    public static class BoxedLongStepper
    implements AnyStepper<Object> {
        public final LongStepper scala$collection$AnyStepper$BoxedLongStepper$$st;

        @Override
        public <B> Spliterator<B> spliterator() {
            return ((AnyStepper)this).spliterator();
        }

        @Override
        public <B> java.util.Iterator<B> javaIterator() {
            return ((AnyStepper)this).javaIterator();
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
        public <B> Spliterator<?> spliterator$mcD$sp() {
            return Stepper.spliterator$mcD$sp$(this);
        }

        @Override
        public <B> Spliterator<?> spliterator$mcI$sp() {
            return Stepper.spliterator$mcI$sp$(this);
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
        public <B> java.util.Iterator<?> javaIterator$mcI$sp() {
            return Stepper.javaIterator$mcI$sp$(this);
        }

        @Override
        public <B> java.util.Iterator<?> javaIterator$mcJ$sp() {
            return Stepper.javaIterator$mcJ$sp$(this);
        }

        @Override
        public Iterator<Object> iterator() {
            return Stepper.iterator$(this);
        }

        @Override
        public boolean hasStep() {
            return this.scala$collection$AnyStepper$BoxedLongStepper$$st.hasStep();
        }

        @Override
        public long nextStep() {
            return this.nextStep$mcJ$sp();
        }

        @Override
        public long estimateSize() {
            return this.scala$collection$AnyStepper$BoxedLongStepper$$st.estimateSize();
        }

        @Override
        public int characteristics() {
            return this.scala$collection$AnyStepper$BoxedLongStepper$$st.characteristics();
        }

        @Override
        public AnyStepper<Object> trySplit() {
            return (AnyStepper)this.trySplit$mcJ$sp();
        }

        @Override
        public long nextStep$mcJ$sp() {
            return this.scala$collection$AnyStepper$BoxedLongStepper$$st.nextStep$mcJ$sp();
        }

        @Override
        public Stepper<Object> trySplit$mcJ$sp() {
            LongStepper s2 = this.scala$collection$AnyStepper$BoxedLongStepper$$st.trySplit();
            if (s2 == null) {
                return null;
            }
            return new BoxedLongStepper(s2);
        }

        public BoxedLongStepper(LongStepper st) {
            this.scala$collection$AnyStepper$BoxedLongStepper$$st = st;
        }
    }
}

