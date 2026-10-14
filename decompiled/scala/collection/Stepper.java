/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import java.util.PrimitiveIterator;
import java.util.Spliterator;
import scala.collection.AbstractIterator;
import scala.collection.AnyStepper;
import scala.collection.DoubleStepper;
import scala.collection.IntStepper;
import scala.collection.Iterator;
import scala.collection.LongStepper;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;

@ScalaSignature(bytes="\u0006\u0005\t5aaB$I!\u0003\r\t!\u0014\u0005\u0006'\u0002!\t\u0001\u0016\u0005\u00061\u00021\t!\u0017\u0005\u0006;\u00021\tA\u0018\u0005\b\u0003\u000b\u0001a\u0011AA\u0004\u0011\u001d\ti\u0001\u0001D\u0001\u0003\u001fAq!a\u0006\u0001\r\u0003\tI\u0002C\u0004\u0002\"\u00011\t!a\t\t\u000f\u0005\u0015\u0003A\"\u0001\u0002H!9\u00111\f\u0001\u0005\u0002\u0005usaBA2\u0011\"\u0005\u0011Q\r\u0004\u0007\u000f\"C\t!a\u001a\t\u000f\u0005%4\u0002\"\u0001\u0002l\u0019I\u0011QN\u0006\u0011\u0002G\u0005\u0011q\u000e\u0005\t\u0003cZAQ\u0001%\u0002t\u00199\u0011QO\u0006\u0001\u0011\u0006]\u0004BCA@\u001f\t\u0005\t\u0015!\u0003\u0002\u0002\"9\u0011\u0011N\b\u0005\u0002\u00055\u0005\"\u0002-\u0010\t\u0003I\u0006BB/\u0010\t\u0003\t)\nC\u0004\u0002\u000e=!\t!a\u0004\t\u000f\u0005]q\u0002\"\u0001\u0002\u001a!9\u0011QA\b\u0005\u0002\u0005]eaBAM\u0017\u0001A\u00151\u0014\u0005\u000b\u0003\u007f:\"\u0011!Q\u0001\n\u0005\r\u0006bBA5/\u0011\u0005\u0011Q\u0015\u0005\u00061^!\t!\u0017\u0005\u0007;^!\t!a+\t\u000f\u00055q\u0003\"\u0001\u0002\u0010!9\u0011qC\f\u0005\u0002\u0005e\u0001bBA\u0003/\u0011\u0005\u0011Q\u0016\u0004\b\u0003_[\u0001\u0001SAY\u0011)\tyh\bB\u0001B\u0003%\u0011\u0011\u0018\u0005\b\u0003SzB\u0011AA^\u0011\u0015Av\u0004\"\u0001Z\u0011\u0019iv\u0004\"\u0001\u0002B\"9\u0011QB\u0010\u0005\u0002\u0005=\u0001bBA\f?\u0011\u0005\u0011\u0011\u0004\u0005\b\u0003\u000byB\u0011AAb\r\u001d\t)m\u0003\u0001I\u0003\u000fD!\"a (\u0005\u0003\u0005\u000b\u0011BAe\u0011\u001d\tIg\nC\u0001\u0003#DQ\u0001W\u0014\u0005\u0002eCa!X\u0014\u0005\u0002\u0005-\u0006bBA\u0007O\u0011\u0005\u0011q\u0002\u0005\b\u0003/9C\u0011AA\r\u0011\u001d\t)a\nC\u0001\u0003[3q!a6\f\u0001!\u000bI\u000e\u0003\u0006\u0002\u0000=\u0012\t\u0011)A\u0005\u00037Dq!!\u001b0\t\u0003\t\u0019\u000fC\u0003Y_\u0011\u0005\u0011\f\u0003\u0004^_\u0011\u0005\u00111\u0016\u0005\b\u0003\u001byC\u0011AA\b\u0011\u001d\t9b\fC\u0001\u00033Aq!!\u00020\t\u0003\tiKB\u0004\u0002j.\u0001\u0001*a;\t\u0015\u0005}tG!A!\u0002\u0013\ti\u000fC\u0004\u0002j]\"\t!!>\t\u000ba;D\u0011A-\t\ru;D\u0011AAV\u0011\u001d\tia\u000eC\u0001\u0003\u001fAq!a\u00068\t\u0003\tI\u0002C\u0004\u0002\u0006]\"\t!!,\u0007\u000f\u0005m8\u0002\u0001%\u0002~\"Q\u0011qP \u0003\u0002\u0003\u0006I!a@\t\u000f\u0005%t\b\"\u0001\u0003\b!)\u0001l\u0010C\u00013\"1Ql\u0010C\u0001\u0003+Cq!!\u0004@\t\u0003\ty\u0001C\u0004\u0002\u0018}\"\t!!\u0007\t\u000f\u0005\u0015q\b\"\u0001\u0002\u0018\n91\u000b^3qa\u0016\u0014(BA%K\u0003)\u0019w\u000e\u001c7fGRLwN\u001c\u0006\u0002\u0017\u0006)1oY1mC\u000e\u0001QC\u0001(b'\t\u0001q\n\u0005\u0002Q#6\t!*\u0003\u0002S\u0015\n1\u0011I\\=SK\u001a\fa\u0001J5oSR$C#A+\u0011\u0005A3\u0016BA,K\u0005\u0011)f.\u001b;\u0002\u000f!\f7o\u0015;faV\t!\f\u0005\u0002Q7&\u0011AL\u0013\u0002\b\u0005>|G.Z1o\u0003!qW\r\u001f;Ti\u0016\u0004H#A0\u0011\u0005\u0001\fG\u0002\u0001\u0003\nE\u0002\u0001\u000b\u0011!CC\u0002\r\u0014\u0011!Q\t\u0003I\u001e\u0004\"\u0001U3\n\u0005\u0019T%a\u0002(pi\"Lgn\u001a\t\u0003!\"L!!\u001b&\u0003\u0007\u0005s\u0017\u0010K\u0003bW:DX\u0010\u0005\u0002QY&\u0011QN\u0013\u0002\fgB,7-[1mSj,G-M\u0003$_B\u0014\u0018O\u0004\u0002Qa&\u0011\u0011OS\u0001\u0007\t>,(\r\\32\t\u0011\u001axo\u0013\b\u0003i^l\u0011!\u001e\u0006\u0003m2\u000ba\u0001\u0010:p_Rt\u0014\"A&2\u000b\rJ(\u0010`>\u000f\u0005AS\u0018BA>K\u0003\rIe\u000e^\u0019\u0005IM<8*M\u0004$}~\f\u0019!!\u0001\u000f\u0005A{\u0018bAA\u0001\u0015\u0006!Aj\u001c8hc\u0011!3o^&\u0002\u0011Q\u0014\u0018p\u00159mSR$\"!!\u0003\u0011\t\u0005-\u0001aX\u0007\u0002\u0011\u0006aQm\u001d;j[\u0006$XmU5{KV\u0011\u0011\u0011\u0003\t\u0004!\u0006M\u0011bAA\u000b\u0015\n!Aj\u001c8h\u0003=\u0019\u0007.\u0019:bGR,'/[:uS\u000e\u001cXCAA\u000e!\r\u0001\u0016QD\u0005\u0004\u0003?Q%aA%oi\u0006Y1\u000f\u001d7ji\u0016\u0014\u0018\r^8s+\u0011\t)#a\u0010\u0016\u0005\u0005\u001d\u0002\u0007BA\u0015\u0003w\u0001b!a\u000b\u00026\u0005eRBAA\u0017\u0015\u0011\ty#!\r\u0002\tU$\u0018\u000e\u001c\u0006\u0003\u0003g\tAA[1wC&!\u0011qGA\u0017\u0005-\u0019\u0006\u000f\\5uKJ\fGo\u001c:\u0011\u0007\u0001\fY\u0004\u0002\u0006\u0002>\u001d\t\t\u0011!A\u0003\u0002\r\u00141a\u0018\u00132\t\u001d\t\te\u0002b\u0001\u0003\u0007\u0012\u0011AQ\t\u0003?\u001e\fAB[1wC&#XM]1u_J,B!!\u0013\u0002ZU\u0011\u00111\n\u0019\u0005\u0003\u001b\n)\u0006\u0005\u0004\u0002,\u0005=\u00131K\u0005\u0005\u0003#\niC\u0001\u0005Ji\u0016\u0014\u0018\r^8s!\r\u0001\u0017Q\u000b\u0003\u000b\u0003/B\u0011\u0011!A\u0001\u0006\u0003\u0019'aA0%e\u00119\u0011\u0011\t\u0005C\u0002\u0005\r\u0013\u0001C5uKJ\fGo\u001c:\u0016\u0005\u0005}\u0003#BA\u0006\u0003Cz\u0016bAA)\u0011\u000691\u000b^3qa\u0016\u0014\bcAA\u0006\u0017M\u00111bT\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0005\u0005\u0015$AD#gM&\u001c\u0017.\u001a8u'Bd\u0017\u000e^\n\u0003\u001b=\u000b\u0011\u0002\u001e5s_^t5+R#\u0015\u0003\u0011\u0014Q#\u00168c_bLgn\u001a#pk\ndWm\u0015;faB,'o\u0005\u0003\u0010\u001f\u0006e\u0004\u0003BA\u0006\u0003wJ1!! I\u00055!u.\u001e2mKN#X\r\u001d9fe\u0006\u00111\u000f\u001e\t\u0007\u0003\u0017\t\u0019)a\"\n\u0007\u0005\u0015\u0005J\u0001\u0006B]f\u001cF/\u001a9qKJ\u00042\u0001UAE\u0013\r\tYI\u0013\u0002\u0007\t>,(\r\\3\u0015\t\u0005=\u00151\u0013\t\u0004\u0003#{Q\"A\u0006\t\u000f\u0005}\u0014\u00031\u0001\u0002\u0002R\u0011\u0011q\u0011\u000b\u0003\u0003s\u0012!#\u00168c_bLgnZ%oiN#X\r\u001d9feN!qcTAO!\u0011\tY!a(\n\u0007\u0005\u0005\u0006J\u0001\u0006J]R\u001cF/\u001a9qKJ\u0004b!a\u0003\u0002\u0004\u0006mA\u0003BAT\u0003S\u00032!!%\u0018\u0011\u001d\ty(\u0007a\u0001\u0003G#\"!a\u0007\u0015\u0005\u0005u%aE+oE>D\u0018N\\4M_:<7\u000b^3qa\u0016\u00148\u0003B\u0010P\u0003g\u0003B!a\u0003\u00026&\u0019\u0011q\u0017%\u0003\u00171{gnZ*uKB\u0004XM\u001d\t\u0007\u0003\u0017\t\u0019)!\u0005\u0015\t\u0005u\u0016q\u0018\t\u0004\u0003#{\u0002bBA@C\u0001\u0007\u0011\u0011\u0018\u000b\u0003\u0003#!\"!a-\u0003'Us'm\u001c=j]\u001e\u0014\u0015\u0010^3Ti\u0016\u0004\b/\u001a:\u0014\t\u001dz\u0015Q\u0014\t\u0007\u0003\u0017\t\u0019)a3\u0011\u0007A\u000bi-C\u0002\u0002P*\u0013AAQ=uKR!\u00111[Ak!\r\t\tj\n\u0005\b\u0003\u007fJ\u0003\u0019AAe\u0005M)fNY8yS:<7\t[1s'R,\u0007\u000f]3s'\u0011ys*!(\u0011\r\u0005-\u00111QAo!\r\u0001\u0016q\\\u0005\u0004\u0003CT%\u0001B\"iCJ$B!!:\u0002hB\u0019\u0011\u0011S\u0018\t\u000f\u0005}\u0014\u00071\u0001\u0002\\\n!RK\u001c2pq&twm\u00155peR\u001cF/\u001a9qKJ\u001cBaN(\u0002\u001eB1\u00111BAB\u0003_\u00042\u0001UAy\u0013\r\t\u0019P\u0013\u0002\u0006'\"|'\u000f\u001e\u000b\u0005\u0003o\fI\u0010E\u0002\u0002\u0012^Bq!a :\u0001\u0004\tiO\u0001\u000bV]\n|\u00070\u001b8h\r2|\u0017\r^*uKB\u0004XM]\n\u0005\u007f=\u000bI\b\u0005\u0004\u0002\f\u0005\r%\u0011\u0001\t\u0004!\n\r\u0011b\u0001B\u0003\u0015\n)a\t\\8biR!!\u0011\u0002B\u0006!\r\t\tj\u0010\u0005\b\u0003\u007f\n\u0005\u0019AA\u0000\u0001")
public interface Stepper<A> {
    public boolean hasStep();

    public A nextStep();

    public Stepper<A> trySplit();

    public long estimateSize();

    public int characteristics();

    public <B> Spliterator<?> spliterator();

    public <B> java.util.Iterator<?> javaIterator();

    default public Iterator<A> iterator() {
        return new AbstractIterator<A>(this){
            private final /* synthetic */ Stepper $outer;

            public boolean hasNext() {
                return this.$outer.hasStep();
            }

            public A next() {
                return this.$outer.nextStep();
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
            }
        };
    }

    default public double nextStep$mcD$sp() {
        return BoxesRunTime.unboxToDouble(this.nextStep());
    }

    default public int nextStep$mcI$sp() {
        return BoxesRunTime.unboxToInt(this.nextStep());
    }

    default public long nextStep$mcJ$sp() {
        return BoxesRunTime.unboxToLong(this.nextStep());
    }

    default public Stepper<Object> trySplit$mcD$sp() {
        return this.trySplit();
    }

    default public Stepper<Object> trySplit$mcI$sp() {
        return this.trySplit();
    }

    default public Stepper<Object> trySplit$mcJ$sp() {
        return this.trySplit();
    }

    default public <B> Spliterator<?> spliterator$mcD$sp() {
        return this.spliterator();
    }

    default public <B> Spliterator<?> spliterator$mcI$sp() {
        return this.spliterator();
    }

    default public <B> Spliterator<?> spliterator$mcJ$sp() {
        return this.spliterator();
    }

    default public <B> java.util.Iterator<?> javaIterator$mcD$sp() {
        return this.javaIterator();
    }

    default public <B> java.util.Iterator<?> javaIterator$mcI$sp() {
        return this.javaIterator();
    }

    default public <B> java.util.Iterator<?> javaIterator$mcJ$sp() {
        return this.javaIterator();
    }

    public static void $init$(Stepper $this) {
    }

    public static interface EfficientSplit {
    }

    public static class UnboxingByteStepper
    implements IntStepper {
        public final AnyStepper<Object> scala$collection$Stepper$UnboxingByteStepper$$st;

        @Override
        public <B> Spliterator.OfInt spliterator() {
            return IntStepper.spliterator$(this);
        }

        @Override
        public <B> PrimitiveIterator.OfInt javaIterator() {
            return IntStepper.javaIterator$(this);
        }

        @Override
        public <B> Spliterator<?> spliterator$mcI$sp() {
            return IntStepper.spliterator$mcI$sp$(this);
        }

        @Override
        public <B> java.util.Iterator<?> javaIterator$mcI$sp() {
            return IntStepper.javaIterator$mcI$sp$(this);
        }

        @Override
        public double nextStep$mcD$sp() {
            return ((Stepper)this).nextStep$mcD$sp();
        }

        @Override
        public long nextStep$mcJ$sp() {
            return ((Stepper)this).nextStep$mcJ$sp();
        }

        @Override
        public Stepper<Object> trySplit$mcD$sp() {
            return ((Stepper)this).trySplit$mcD$sp();
        }

        @Override
        public Stepper<Object> trySplit$mcI$sp() {
            return ((Stepper)this).trySplit$mcI$sp();
        }

        @Override
        public Stepper<Object> trySplit$mcJ$sp() {
            return ((Stepper)this).trySplit$mcJ$sp();
        }

        @Override
        public <B> Spliterator<?> spliterator$mcD$sp() {
            return ((Stepper)this).spliterator$mcD$sp();
        }

        @Override
        public <B> Spliterator<?> spliterator$mcJ$sp() {
            return ((Stepper)this).spliterator$mcJ$sp();
        }

        @Override
        public <B> java.util.Iterator<?> javaIterator$mcD$sp() {
            return ((Stepper)this).javaIterator$mcD$sp();
        }

        @Override
        public <B> java.util.Iterator<?> javaIterator$mcJ$sp() {
            return ((Stepper)this).javaIterator$mcJ$sp();
        }

        @Override
        public Iterator<Object> iterator() {
            return ((Stepper)this).iterator();
        }

        @Override
        public boolean hasStep() {
            return this.scala$collection$Stepper$UnboxingByteStepper$$st.hasStep();
        }

        @Override
        public int nextStep() {
            return this.nextStep$mcI$sp();
        }

        @Override
        public long estimateSize() {
            return this.scala$collection$Stepper$UnboxingByteStepper$$st.estimateSize();
        }

        @Override
        public int characteristics() {
            return this.scala$collection$Stepper$UnboxingByteStepper$$st.characteristics();
        }

        @Override
        public IntStepper trySplit() {
            AnyStepper<Object> s2 = this.scala$collection$Stepper$UnboxingByteStepper$$st.trySplit();
            if (s2 == null) {
                return null;
            }
            return new UnboxingByteStepper(s2);
        }

        @Override
        public int nextStep$mcI$sp() {
            return BoxesRunTime.unboxToByte(this.scala$collection$Stepper$UnboxingByteStepper$$st.nextStep());
        }

        public UnboxingByteStepper(AnyStepper<Object> st) {
            this.scala$collection$Stepper$UnboxingByteStepper$$st = st;
        }
    }

    public static class UnboxingCharStepper
    implements IntStepper {
        public final AnyStepper<Object> scala$collection$Stepper$UnboxingCharStepper$$st;

        @Override
        public <B> Spliterator.OfInt spliterator() {
            return IntStepper.spliterator$(this);
        }

        @Override
        public <B> PrimitiveIterator.OfInt javaIterator() {
            return IntStepper.javaIterator$(this);
        }

        @Override
        public <B> Spliterator<?> spliterator$mcI$sp() {
            return IntStepper.spliterator$mcI$sp$(this);
        }

        @Override
        public <B> java.util.Iterator<?> javaIterator$mcI$sp() {
            return IntStepper.javaIterator$mcI$sp$(this);
        }

        @Override
        public double nextStep$mcD$sp() {
            return ((Stepper)this).nextStep$mcD$sp();
        }

        @Override
        public long nextStep$mcJ$sp() {
            return ((Stepper)this).nextStep$mcJ$sp();
        }

        @Override
        public Stepper<Object> trySplit$mcD$sp() {
            return ((Stepper)this).trySplit$mcD$sp();
        }

        @Override
        public Stepper<Object> trySplit$mcI$sp() {
            return ((Stepper)this).trySplit$mcI$sp();
        }

        @Override
        public Stepper<Object> trySplit$mcJ$sp() {
            return ((Stepper)this).trySplit$mcJ$sp();
        }

        @Override
        public <B> Spliterator<?> spliterator$mcD$sp() {
            return ((Stepper)this).spliterator$mcD$sp();
        }

        @Override
        public <B> Spliterator<?> spliterator$mcJ$sp() {
            return ((Stepper)this).spliterator$mcJ$sp();
        }

        @Override
        public <B> java.util.Iterator<?> javaIterator$mcD$sp() {
            return ((Stepper)this).javaIterator$mcD$sp();
        }

        @Override
        public <B> java.util.Iterator<?> javaIterator$mcJ$sp() {
            return ((Stepper)this).javaIterator$mcJ$sp();
        }

        @Override
        public Iterator<Object> iterator() {
            return ((Stepper)this).iterator();
        }

        @Override
        public boolean hasStep() {
            return this.scala$collection$Stepper$UnboxingCharStepper$$st.hasStep();
        }

        @Override
        public int nextStep() {
            return this.nextStep$mcI$sp();
        }

        @Override
        public long estimateSize() {
            return this.scala$collection$Stepper$UnboxingCharStepper$$st.estimateSize();
        }

        @Override
        public int characteristics() {
            return this.scala$collection$Stepper$UnboxingCharStepper$$st.characteristics();
        }

        @Override
        public IntStepper trySplit() {
            AnyStepper<Object> s2 = this.scala$collection$Stepper$UnboxingCharStepper$$st.trySplit();
            if (s2 == null) {
                return null;
            }
            return new UnboxingCharStepper(s2);
        }

        @Override
        public int nextStep$mcI$sp() {
            return BoxesRunTime.unboxToChar(this.scala$collection$Stepper$UnboxingCharStepper$$st.nextStep());
        }

        public UnboxingCharStepper(AnyStepper<Object> st) {
            this.scala$collection$Stepper$UnboxingCharStepper$$st = st;
        }
    }

    public static class UnboxingDoubleStepper
    implements DoubleStepper {
        public final AnyStepper<Object> scala$collection$Stepper$UnboxingDoubleStepper$$st;

        @Override
        public <B> Spliterator.OfDouble spliterator() {
            return DoubleStepper.spliterator$(this);
        }

        @Override
        public <B> PrimitiveIterator.OfDouble javaIterator() {
            return DoubleStepper.javaIterator$(this);
        }

        @Override
        public <B> Spliterator<?> spliterator$mcD$sp() {
            return DoubleStepper.spliterator$mcD$sp$(this);
        }

        @Override
        public <B> java.util.Iterator<?> javaIterator$mcD$sp() {
            return DoubleStepper.javaIterator$mcD$sp$(this);
        }

        @Override
        public int nextStep$mcI$sp() {
            return ((Stepper)this).nextStep$mcI$sp();
        }

        @Override
        public long nextStep$mcJ$sp() {
            return ((Stepper)this).nextStep$mcJ$sp();
        }

        @Override
        public Stepper<Object> trySplit$mcD$sp() {
            return ((Stepper)this).trySplit$mcD$sp();
        }

        @Override
        public Stepper<Object> trySplit$mcI$sp() {
            return ((Stepper)this).trySplit$mcI$sp();
        }

        @Override
        public Stepper<Object> trySplit$mcJ$sp() {
            return ((Stepper)this).trySplit$mcJ$sp();
        }

        @Override
        public <B> Spliterator<?> spliterator$mcI$sp() {
            return ((Stepper)this).spliterator$mcI$sp();
        }

        @Override
        public <B> Spliterator<?> spliterator$mcJ$sp() {
            return ((Stepper)this).spliterator$mcJ$sp();
        }

        @Override
        public <B> java.util.Iterator<?> javaIterator$mcI$sp() {
            return ((Stepper)this).javaIterator$mcI$sp();
        }

        @Override
        public <B> java.util.Iterator<?> javaIterator$mcJ$sp() {
            return ((Stepper)this).javaIterator$mcJ$sp();
        }

        @Override
        public Iterator<Object> iterator() {
            return ((Stepper)this).iterator();
        }

        @Override
        public boolean hasStep() {
            return this.scala$collection$Stepper$UnboxingDoubleStepper$$st.hasStep();
        }

        @Override
        public double nextStep() {
            return this.nextStep$mcD$sp();
        }

        @Override
        public long estimateSize() {
            return this.scala$collection$Stepper$UnboxingDoubleStepper$$st.estimateSize();
        }

        @Override
        public int characteristics() {
            return this.scala$collection$Stepper$UnboxingDoubleStepper$$st.characteristics();
        }

        @Override
        public DoubleStepper trySplit() {
            AnyStepper<Object> s2 = this.scala$collection$Stepper$UnboxingDoubleStepper$$st.trySplit();
            if (s2 == null) {
                return null;
            }
            return new UnboxingDoubleStepper(s2);
        }

        @Override
        public double nextStep$mcD$sp() {
            return this.scala$collection$Stepper$UnboxingDoubleStepper$$st.nextStep$mcD$sp();
        }

        public UnboxingDoubleStepper(AnyStepper<Object> st) {
            this.scala$collection$Stepper$UnboxingDoubleStepper$$st = st;
        }
    }

    public static class UnboxingFloatStepper
    implements DoubleStepper {
        public final AnyStepper<Object> scala$collection$Stepper$UnboxingFloatStepper$$st;

        @Override
        public <B> Spliterator.OfDouble spliterator() {
            return DoubleStepper.spliterator$(this);
        }

        @Override
        public <B> PrimitiveIterator.OfDouble javaIterator() {
            return DoubleStepper.javaIterator$(this);
        }

        @Override
        public <B> Spliterator<?> spliterator$mcD$sp() {
            return DoubleStepper.spliterator$mcD$sp$(this);
        }

        @Override
        public <B> java.util.Iterator<?> javaIterator$mcD$sp() {
            return DoubleStepper.javaIterator$mcD$sp$(this);
        }

        @Override
        public int nextStep$mcI$sp() {
            return ((Stepper)this).nextStep$mcI$sp();
        }

        @Override
        public long nextStep$mcJ$sp() {
            return ((Stepper)this).nextStep$mcJ$sp();
        }

        @Override
        public Stepper<Object> trySplit$mcD$sp() {
            return ((Stepper)this).trySplit$mcD$sp();
        }

        @Override
        public Stepper<Object> trySplit$mcI$sp() {
            return ((Stepper)this).trySplit$mcI$sp();
        }

        @Override
        public Stepper<Object> trySplit$mcJ$sp() {
            return ((Stepper)this).trySplit$mcJ$sp();
        }

        @Override
        public <B> Spliterator<?> spliterator$mcI$sp() {
            return ((Stepper)this).spliterator$mcI$sp();
        }

        @Override
        public <B> Spliterator<?> spliterator$mcJ$sp() {
            return ((Stepper)this).spliterator$mcJ$sp();
        }

        @Override
        public <B> java.util.Iterator<?> javaIterator$mcI$sp() {
            return ((Stepper)this).javaIterator$mcI$sp();
        }

        @Override
        public <B> java.util.Iterator<?> javaIterator$mcJ$sp() {
            return ((Stepper)this).javaIterator$mcJ$sp();
        }

        @Override
        public Iterator<Object> iterator() {
            return ((Stepper)this).iterator();
        }

        @Override
        public boolean hasStep() {
            return this.scala$collection$Stepper$UnboxingFloatStepper$$st.hasStep();
        }

        @Override
        public double nextStep() {
            return this.nextStep$mcD$sp();
        }

        @Override
        public long estimateSize() {
            return this.scala$collection$Stepper$UnboxingFloatStepper$$st.estimateSize();
        }

        @Override
        public int characteristics() {
            return this.scala$collection$Stepper$UnboxingFloatStepper$$st.characteristics();
        }

        @Override
        public DoubleStepper trySplit() {
            AnyStepper<Object> s2 = this.scala$collection$Stepper$UnboxingFloatStepper$$st.trySplit();
            if (s2 == null) {
                return null;
            }
            return new UnboxingFloatStepper(s2);
        }

        @Override
        public double nextStep$mcD$sp() {
            return BoxesRunTime.unboxToFloat(this.scala$collection$Stepper$UnboxingFloatStepper$$st.nextStep());
        }

        public UnboxingFloatStepper(AnyStepper<Object> st) {
            this.scala$collection$Stepper$UnboxingFloatStepper$$st = st;
        }
    }

    public static class UnboxingIntStepper
    implements IntStepper {
        public final AnyStepper<Object> scala$collection$Stepper$UnboxingIntStepper$$st;

        @Override
        public <B> Spliterator.OfInt spliterator() {
            return IntStepper.spliterator$(this);
        }

        @Override
        public <B> PrimitiveIterator.OfInt javaIterator() {
            return IntStepper.javaIterator$(this);
        }

        @Override
        public <B> Spliterator<?> spliterator$mcI$sp() {
            return IntStepper.spliterator$mcI$sp$(this);
        }

        @Override
        public <B> java.util.Iterator<?> javaIterator$mcI$sp() {
            return IntStepper.javaIterator$mcI$sp$(this);
        }

        @Override
        public double nextStep$mcD$sp() {
            return ((Stepper)this).nextStep$mcD$sp();
        }

        @Override
        public long nextStep$mcJ$sp() {
            return ((Stepper)this).nextStep$mcJ$sp();
        }

        @Override
        public Stepper<Object> trySplit$mcD$sp() {
            return ((Stepper)this).trySplit$mcD$sp();
        }

        @Override
        public Stepper<Object> trySplit$mcI$sp() {
            return ((Stepper)this).trySplit$mcI$sp();
        }

        @Override
        public Stepper<Object> trySplit$mcJ$sp() {
            return ((Stepper)this).trySplit$mcJ$sp();
        }

        @Override
        public <B> Spliterator<?> spliterator$mcD$sp() {
            return ((Stepper)this).spliterator$mcD$sp();
        }

        @Override
        public <B> Spliterator<?> spliterator$mcJ$sp() {
            return ((Stepper)this).spliterator$mcJ$sp();
        }

        @Override
        public <B> java.util.Iterator<?> javaIterator$mcD$sp() {
            return ((Stepper)this).javaIterator$mcD$sp();
        }

        @Override
        public <B> java.util.Iterator<?> javaIterator$mcJ$sp() {
            return ((Stepper)this).javaIterator$mcJ$sp();
        }

        @Override
        public Iterator<Object> iterator() {
            return ((Stepper)this).iterator();
        }

        @Override
        public boolean hasStep() {
            return this.scala$collection$Stepper$UnboxingIntStepper$$st.hasStep();
        }

        @Override
        public int nextStep() {
            return this.nextStep$mcI$sp();
        }

        @Override
        public long estimateSize() {
            return this.scala$collection$Stepper$UnboxingIntStepper$$st.estimateSize();
        }

        @Override
        public int characteristics() {
            return this.scala$collection$Stepper$UnboxingIntStepper$$st.characteristics();
        }

        @Override
        public IntStepper trySplit() {
            AnyStepper<Object> s2 = this.scala$collection$Stepper$UnboxingIntStepper$$st.trySplit();
            if (s2 == null) {
                return null;
            }
            return new UnboxingIntStepper(s2);
        }

        @Override
        public int nextStep$mcI$sp() {
            return this.scala$collection$Stepper$UnboxingIntStepper$$st.nextStep$mcI$sp();
        }

        public UnboxingIntStepper(AnyStepper<Object> st) {
            this.scala$collection$Stepper$UnboxingIntStepper$$st = st;
        }
    }

    public static class UnboxingLongStepper
    implements LongStepper {
        public final AnyStepper<Object> scala$collection$Stepper$UnboxingLongStepper$$st;

        @Override
        public <B> Spliterator.OfLong spliterator() {
            return LongStepper.spliterator$(this);
        }

        @Override
        public <B> PrimitiveIterator.OfLong javaIterator() {
            return LongStepper.javaIterator$(this);
        }

        @Override
        public <B> Spliterator<?> spliterator$mcJ$sp() {
            return LongStepper.spliterator$mcJ$sp$(this);
        }

        @Override
        public <B> java.util.Iterator<?> javaIterator$mcJ$sp() {
            return LongStepper.javaIterator$mcJ$sp$(this);
        }

        @Override
        public double nextStep$mcD$sp() {
            return ((Stepper)this).nextStep$mcD$sp();
        }

        @Override
        public int nextStep$mcI$sp() {
            return ((Stepper)this).nextStep$mcI$sp();
        }

        @Override
        public Stepper<Object> trySplit$mcD$sp() {
            return ((Stepper)this).trySplit$mcD$sp();
        }

        @Override
        public Stepper<Object> trySplit$mcI$sp() {
            return ((Stepper)this).trySplit$mcI$sp();
        }

        @Override
        public Stepper<Object> trySplit$mcJ$sp() {
            return ((Stepper)this).trySplit$mcJ$sp();
        }

        @Override
        public <B> Spliterator<?> spliterator$mcD$sp() {
            return ((Stepper)this).spliterator$mcD$sp();
        }

        @Override
        public <B> Spliterator<?> spliterator$mcI$sp() {
            return ((Stepper)this).spliterator$mcI$sp();
        }

        @Override
        public <B> java.util.Iterator<?> javaIterator$mcD$sp() {
            return ((Stepper)this).javaIterator$mcD$sp();
        }

        @Override
        public <B> java.util.Iterator<?> javaIterator$mcI$sp() {
            return ((Stepper)this).javaIterator$mcI$sp();
        }

        @Override
        public Iterator<Object> iterator() {
            return ((Stepper)this).iterator();
        }

        @Override
        public boolean hasStep() {
            return this.scala$collection$Stepper$UnboxingLongStepper$$st.hasStep();
        }

        @Override
        public long nextStep() {
            return this.nextStep$mcJ$sp();
        }

        @Override
        public long estimateSize() {
            return this.scala$collection$Stepper$UnboxingLongStepper$$st.estimateSize();
        }

        @Override
        public int characteristics() {
            return this.scala$collection$Stepper$UnboxingLongStepper$$st.characteristics();
        }

        @Override
        public LongStepper trySplit() {
            AnyStepper<Object> s2 = this.scala$collection$Stepper$UnboxingLongStepper$$st.trySplit();
            if (s2 == null) {
                return null;
            }
            return new UnboxingLongStepper(s2);
        }

        @Override
        public long nextStep$mcJ$sp() {
            return this.scala$collection$Stepper$UnboxingLongStepper$$st.nextStep$mcJ$sp();
        }

        public UnboxingLongStepper(AnyStepper<Object> st) {
            this.scala$collection$Stepper$UnboxingLongStepper$$st = st;
        }
    }

    public static class UnboxingShortStepper
    implements IntStepper {
        public final AnyStepper<Object> scala$collection$Stepper$UnboxingShortStepper$$st;

        @Override
        public <B> Spliterator.OfInt spliterator() {
            return IntStepper.spliterator$(this);
        }

        @Override
        public <B> PrimitiveIterator.OfInt javaIterator() {
            return IntStepper.javaIterator$(this);
        }

        @Override
        public <B> Spliterator<?> spliterator$mcI$sp() {
            return IntStepper.spliterator$mcI$sp$(this);
        }

        @Override
        public <B> java.util.Iterator<?> javaIterator$mcI$sp() {
            return IntStepper.javaIterator$mcI$sp$(this);
        }

        @Override
        public double nextStep$mcD$sp() {
            return ((Stepper)this).nextStep$mcD$sp();
        }

        @Override
        public long nextStep$mcJ$sp() {
            return ((Stepper)this).nextStep$mcJ$sp();
        }

        @Override
        public Stepper<Object> trySplit$mcD$sp() {
            return ((Stepper)this).trySplit$mcD$sp();
        }

        @Override
        public Stepper<Object> trySplit$mcI$sp() {
            return ((Stepper)this).trySplit$mcI$sp();
        }

        @Override
        public Stepper<Object> trySplit$mcJ$sp() {
            return ((Stepper)this).trySplit$mcJ$sp();
        }

        @Override
        public <B> Spliterator<?> spliterator$mcD$sp() {
            return ((Stepper)this).spliterator$mcD$sp();
        }

        @Override
        public <B> Spliterator<?> spliterator$mcJ$sp() {
            return ((Stepper)this).spliterator$mcJ$sp();
        }

        @Override
        public <B> java.util.Iterator<?> javaIterator$mcD$sp() {
            return ((Stepper)this).javaIterator$mcD$sp();
        }

        @Override
        public <B> java.util.Iterator<?> javaIterator$mcJ$sp() {
            return ((Stepper)this).javaIterator$mcJ$sp();
        }

        @Override
        public Iterator<Object> iterator() {
            return ((Stepper)this).iterator();
        }

        @Override
        public boolean hasStep() {
            return this.scala$collection$Stepper$UnboxingShortStepper$$st.hasStep();
        }

        @Override
        public int nextStep() {
            return this.nextStep$mcI$sp();
        }

        @Override
        public long estimateSize() {
            return this.scala$collection$Stepper$UnboxingShortStepper$$st.estimateSize();
        }

        @Override
        public int characteristics() {
            return this.scala$collection$Stepper$UnboxingShortStepper$$st.characteristics();
        }

        @Override
        public IntStepper trySplit() {
            AnyStepper<Object> s2 = this.scala$collection$Stepper$UnboxingShortStepper$$st.trySplit();
            if (s2 == null) {
                return null;
            }
            return new UnboxingShortStepper(s2);
        }

        @Override
        public int nextStep$mcI$sp() {
            return BoxesRunTime.unboxToShort(this.scala$collection$Stepper$UnboxingShortStepper$$st.nextStep());
        }

        public UnboxingShortStepper(AnyStepper<Object> st) {
            this.scala$collection$Stepper$UnboxingShortStepper$$st = st;
        }
    }
}

