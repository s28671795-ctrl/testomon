/*
 * Decompiled with CFR 0.152.
 */
package scala.collection;

import scala.collection.AnyStepper;
import scala.collection.DoubleStepper;
import scala.collection.IntStepper;
import scala.collection.LongStepper;
import scala.collection.Stepper;
import scala.collection.StepperShape$;
import scala.collection.StepperShape$Shape$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\t\rda\u0002 @!\u0003\r\n\u0003\u0012\u0005\u0006\u0015\u00021\ta\u0013\u0005\b\u0003g\u0004a\u0011AA{\u0011\u001d\u00119\u0003\u0001D\u0001\u0005S9QaT \t\u0002A3QAP \t\u0002ECQ!V\u0003\u0005\u0002Y3AaV\u0003\u00031\"aAl\u0002C\u0001\u0002\u000b\u0015)\u0019!C\u0005;\"I\u0011m\u0002B\u0003\u0002\u0003\u0006IA\u0018\u0005\u0007+\u001e!\t!\u00022\t\u000f\u001d<\u0011\u0011!C!Q\"9\u0011nBA\u0001\n\u0003RwaB:\u0006\u0003\u0003E\t\u0001\u001e\u0004\b/\u0016\t\t\u0011#\u0001v\u0011\u0015)f\u0002\"\u0001w\u0011\u001d9h\"!A\u0005\u0006aDqa\u001f\b\u0002\u0002\u0013\u0015A\u0010C\u0005\u0002\u0002\u0015\u0011\r\u0011\"\u0001\u0002\u0004!9\u0011QA\u0003!\u0002\u0013\u0019\u0007\"CA\u0004\u000b\t\u0007I\u0011AA\u0002\u0011\u001d\tI!\u0002Q\u0001\n\rD\u0011\"a\u0003\u0006\u0005\u0004%\t!a\u0001\t\u000f\u00055Q\u0001)A\u0005G\"I\u0011qB\u0003C\u0002\u0013\u0005\u00111\u0001\u0005\b\u0003#)\u0001\u0015!\u0003d\u0011%\t\u0019\"\u0002b\u0001\n\u0003\t\u0019\u0001C\u0004\u0002\u0016\u0015\u0001\u000b\u0011B2\t\u0013\u0005]QA1A\u0005\u0002\u0005\r\u0001bBA\r\u000b\u0001\u0006Ia\u0019\u0005\n\u00037)!\u0019!C\u0001\u0003\u0007Aq!!\b\u0006A\u0003%1\rC\u0005\u0002 \u0015\u0011\r\u0011\"\u0001\u0002\u0004!9\u0011\u0011E\u0003!\u0002\u0013\u0019\u0007\"CA\u0012\u000b\t\u0007I1AA\u0013\u0011!\ty#\u0002Q\u0001\n\u0005\u001d\u0002\"CA\u0019\u000b\t\u0007I1AA\u001a\u0011!\t9%\u0002Q\u0001\n\u0005U\u0002\"CA%\u000b\t\u0007I1AA&\u0011!\tY&\u0002Q\u0001\n\u00055\u0003\"CA/\u000b\t\u0007I1AA0\u0011!\t9'\u0002Q\u0001\n\u0005\u0005\u0004\"CA5\u000b\t\u0007I1AA6\u0011!\tY(\u0002Q\u0001\n\u00055\u0004\"CA?\u000b\t\u0007I1AA@\u0011!\t9)\u0002Q\u0001\n\u0005\u0005\u0005\"CAE\u000b\t\u0007I1AAF\u0011!\t)*\u0002Q\u0001\n\u00055\u0005\"CAL\u000b\t\u0007I1AAM\u0011!\t\t+\u0002Q\u0001\n\u0005m\u0005\"CAR\u000b\t\u0007I1AAS\u0011!\ty+\u0002Q\u0001\n\u0005\u001d\u0006\"CAY\u000b\t\u0007I1AAZ\u0011!\tY,\u0002Q\u0001\n\u0005U\u0006\"CA_\u000b\t\u0007I1AA`\u0011!\tI-\u0002Q\u0001\n\u0005\u0005\u0007\"CAf\u000b\t\u0007I1AAg\u0011!\t9.\u0002Q\u0001\n\u0005=\u0007\"CAm\u000b\t\u0007I1AAn\u0011!\t)/\u0002Q\u0001\n\u0005u\u0007\"CAt\u000b\t\u0007I1AAu\u0011!\t\t0\u0002Q\u0001\n\u0005-(\u0001D*uKB\u0004XM]*iCB,'B\u0001!B\u0003)\u0019w\u000e\u001c7fGRLwN\u001c\u0006\u0002\u0005\u0006)1oY1mC\u000e\u0001Q#B#\u0003$\u0005m8C\u0001\u0001G!\t9\u0005*D\u0001B\u0013\tI\u0015I\u0001\u0004B]f\u0014VMZ\u0001\u0006g\"\f\u0007/Z\u000b\u0002\u0019B\u0011Qj\u0002\b\u0003\u001d\u0012i\u0011aP\u0001\r'R,\u0007\u000f]3s'\"\f\u0007/\u001a\t\u0003\u001d\u0016\u00192!\u0002$S!\tq5+\u0003\u0002U\u007f\tA2\u000b^3qa\u0016\u00148\u000b[1qK2{w\u000f\u0015:j_JLG/_\u0019\u0002\rqJg.\u001b;?)\u0005\u0001&!B*iCB,7CA\u0004Z!\t9%,\u0003\u0002\\\u0003\n1\u0011I\\=WC2\fae]2bY\u0006$3m\u001c7mK\u000e$\u0018n\u001c8%'R,\u0007\u000f]3s'\"\f\u0007/\u001a\u0013TQ\u0006\u0004X\r\n\u0013t+\u0005q\u0006CA$`\u0013\t\u0001\u0017IA\u0002J]R\fqe]2bY\u0006$3m\u001c7mK\u000e$\u0018n\u001c8%'R,\u0007\u000f]3s'\"\f\u0007/\u001a\u0013TQ\u0006\u0004X\r\n\u0013tAQ\u00111-\u001a\t\u0003I\u001ei\u0011!\u0002\u0005\u0006M*\u0001\rAX\u0001\u0002g\u0006A\u0001.Y:i\u0007>$W\rF\u0001_\u0003\u0019)\u0017/^1mgR\u00111N\u001c\t\u0003\u000f2L!!\\!\u0003\u000f\t{w\u000e\\3b]\"9q\u000eDA\u0001\u0002\u0004\u0001\u0018a\u0001=%cA\u0011q)]\u0005\u0003e\u0006\u00131!\u00118z\u0003\u0015\u0019\u0006.\u00199f!\t!gb\u0005\u0002\u000f\rR\tA/\u0001\niCND7i\u001c3fI\u0015DH/\u001a8tS>tGC\u00015z\u0011\u0015Q\b\u00031\u0001d\u0003\u0015!C\u000f[5t\u0003A)\u0017/^1mg\u0012*\u0007\u0010^3og&|g\u000e\u0006\u0002~\u007fR\u00111N \u0005\b_F\t\t\u00111\u0001q\u0011\u0015Q\u0018\u00031\u0001d\u00039\u0011VMZ3sK:\u001cWm\u00155ba\u0016,\u0012aY\u0001\u0010%\u00164WM]3oG\u0016\u001c\u0006.\u00199fA\u0005A\u0011J\u001c;TQ\u0006\u0004X-A\u0005J]R\u001c\u0006.\u00199fA\u0005IAj\u001c8h'\"\f\u0007/Z\u0001\u000b\u0019>twm\u00155ba\u0016\u0004\u0013a\u0003#pk\ndWm\u00155ba\u0016\fA\u0002R8vE2,7\u000b[1qK\u0002\n\u0011BQ=uKNC\u0017\r]3\u0002\u0015\tKH/Z*iCB,\u0007%\u0001\u0006TQ>\u0014Ho\u00155ba\u0016\f1b\u00155peR\u001c\u0006.\u00199fA\u0005I1\t[1s'\"\f\u0007/Z\u0001\u000b\u0007\"\f'o\u00155ba\u0016\u0004\u0013A\u0003$m_\u0006$8\u000b[1qK\u0006Ya\t\\8biNC\u0017\r]3!\u0003=Ig\u000e^*uKB\u0004XM]*iCB,WCAA\u0014!\u0015q\u0005AXA\u0015!\rq\u00151F\u0005\u0004\u0003[y$AC%oiN#X\r\u001d9fe\u0006\u0001\u0012N\u001c;Ti\u0016\u0004\b/\u001a:TQ\u0006\u0004X\rI\u0001\u0015U&sG/Z4feN#X\r\u001d9feNC\u0017\r]3\u0016\u0005\u0005U\u0002C\u0002(\u0001\u0003o\tI\u0003\u0005\u0003\u0002:\u0005\rSBAA\u001e\u0015\u0011\ti$a\u0010\u0002\t1\fgn\u001a\u0006\u0003\u0003\u0003\nAA[1wC&!\u0011QIA\u001e\u0005\u001dIe\u000e^3hKJ\fQC[%oi\u0016<WM]*uKB\u0004XM]*iCB,\u0007%\u0001\tm_:<7\u000b^3qa\u0016\u00148\u000b[1qKV\u0011\u0011Q\n\t\u0007\u001d\u0002\ty%!\u0016\u0011\u0007\u001d\u000b\t&C\u0002\u0002T\u0005\u0013A\u0001T8oOB\u0019a*a\u0016\n\u0007\u0005esHA\u0006M_:<7\u000b^3qa\u0016\u0014\u0018!\u00057p]\u001e\u001cF/\u001a9qKJ\u001c\u0006.\u00199fA\u0005\t\"\u000eT8oON#X\r\u001d9feNC\u0017\r]3\u0016\u0005\u0005\u0005\u0004C\u0002(\u0001\u0003G\n)\u0006\u0005\u0003\u0002:\u0005\u0015\u0014\u0002BA*\u0003w\t!C\u001b'p]\u001e\u001cF/\u001a9qKJ\u001c\u0006.\u00199fA\u0005\u0011Bm\\;cY\u0016\u001cF/\u001a9qKJ\u001c\u0006.\u00199f+\t\ti\u0007\u0005\u0004O\u0001\u0005=\u0014Q\u000f\t\u0004\u000f\u0006E\u0014bAA:\u0003\n1Ai\\;cY\u0016\u00042ATA<\u0013\r\tIh\u0010\u0002\u000e\t>,(\r\\3Ti\u0016\u0004\b/\u001a:\u0002'\u0011|WO\u00197f'R,\u0007\u000f]3s'\"\f\u0007/\u001a\u0011\u0002')$u.\u001e2mKN#X\r\u001d9feNC\u0017\r]3\u0016\u0005\u0005\u0005\u0005C\u0002(\u0001\u0003\u0007\u000b)\b\u0005\u0003\u0002:\u0005\u0015\u0015\u0002BA:\u0003w\tAC\u001b#pk\ndWm\u0015;faB,'o\u00155ba\u0016\u0004\u0013\u0001\u00052zi\u0016\u001cF/\u001a9qKJ\u001c\u0006.\u00199f+\t\ti\t\u0005\u0004O\u0001\u0005=\u0015\u0011\u0006\t\u0004\u000f\u0006E\u0015bAAJ\u0003\n!!)\u001f;f\u0003E\u0011\u0017\u0010^3Ti\u0016\u0004\b/\u001a:TQ\u0006\u0004X\rI\u0001\u0012U\nKH/Z*uKB\u0004XM]*iCB,WCAAN!\u0019q\u0005!!(\u0002*A!\u0011\u0011HAP\u0013\u0011\t\u0019*a\u000f\u0002%)\u0014\u0015\u0010^3Ti\u0016\u0004\b/\u001a:TQ\u0006\u0004X\rI\u0001\u0012g\"|'\u000f^*uKB\u0004XM]*iCB,WCAAT!\u0019q\u0005!!+\u0002*A\u0019q)a+\n\u0007\u00055\u0016IA\u0003TQ>\u0014H/\u0001\ntQ>\u0014Ho\u0015;faB,'o\u00155ba\u0016\u0004\u0013A\u00056TQ>\u0014Ho\u0015;faB,'o\u00155ba\u0016,\"!!.\u0011\r9\u0003\u0011qWA\u0015!\u0011\tI$!/\n\t\u00055\u00161H\u0001\u0014UNCwN\u001d;Ti\u0016\u0004\b/\u001a:TQ\u0006\u0004X\rI\u0001\u0011G\"\f'o\u0015;faB,'o\u00155ba\u0016,\"!!1\u0011\r9\u0003\u00111YA\u0015!\r9\u0015QY\u0005\u0004\u0003\u000f\f%\u0001B\"iCJ\f\u0011c\u00195beN#X\r\u001d9feNC\u0017\r]3!\u0003YQ7\t[1sC\u000e$XM]*uKB\u0004XM]*iCB,WCAAh!\u0019q\u0005!!5\u0002*A!\u0011\u0011HAj\u0013\u0011\t).a\u000f\u0003\u0013\rC\u0017M]1di\u0016\u0014\u0018a\u00066DQ\u0006\u0014\u0018m\u0019;feN#X\r\u001d9feNC\u0017\r]3!\u0003E1Gn\\1u'R,\u0007\u000f]3s'\"\f\u0007/Z\u000b\u0003\u0003;\u0004bA\u0014\u0001\u0002`\u0006U\u0004cA$\u0002b&\u0019\u00111]!\u0003\u000b\u0019cw.\u0019;\u0002%\u0019dw.\u0019;Ti\u0016\u0004\b/\u001a:TQ\u0006\u0004X\rI\u0001\u0013U\u001acw.\u0019;Ti\u0016\u0004\b/\u001a:TQ\u0006\u0004X-\u0006\u0002\u0002lB1a\nAAw\u0003k\u0002B!!\u000f\u0002p&!\u00111]A\u001e\u0003MQg\t\\8biN#X\r\u001d9feNC\u0017\r]3!\u0003!\u0019X-]+oE>DH\u0003BA|\u0005/\u0001B!!?\u0002|2\u0001AaBA\u007f\u0001\t\u0007\u0011q \u0002\u0002'F!!\u0011\u0001B\u0004!\r9%1A\u0005\u0004\u0005\u000b\t%a\u0002(pi\"Lgn\u001a\u0019\u0005\u0005\u0013\u0011\t\u0002E\u0003O\u0005\u0017\u0011y!C\u0002\u0003\u000e}\u0012qa\u0015;faB,'\u000f\u0005\u0003\u0002z\nEA\u0001\u0004B\n\u0003w\f\t\u0011!A\u0003\u0002\tU!aA0%cE\u0019!\u0011\u00019\t\u000f\te!\u00011\u0001\u0003\u001c\u0005\u00111\u000f\u001e\t\u0006\u001d\nu!\u0011E\u0005\u0004\u0005?y$AC!osN#X\r\u001d9feB!\u0011\u0011 B\u0012\t!\u0011)\u0003\u0001EC\u0002\tU!!\u0001+\u0002\u0011A\f'/\u00168c_b$BAa\u000b\u0003NI1!QFA|\u0005c1aAa\f\u0001\u0001\t-\"\u0001\u0004\u001fsK\u001aLg.Z7f]Rt\u0004\u0003\u0002B\u001a\u0005\u000frAA!\u000e\u0003D9!!q\u0007B!\u001d\u0011\u0011IDa\u0010\u000e\u0005\tm\"b\u0001B\u001f\u0007\u00061AH]8pizJ\u0011AQ\u0005\u0003\u0001\u0006K1A!\u0012@\u0003\u001d\u0019F/\u001a9qKJLAA!\u0013\u0003L\tqQI\u001a4jG&,g\u000e^*qY&$(b\u0001B#\u007f!9!\u0011D\u0002A\u0002\t=#C\u0002B)\u00057\u0011\tD\u0002\u0004\u00030\u0001\u0001!qJ\u0015\u0004\u0001\tUcA\u0002B,\u0001\u0001\u0011IFA\u0007=Y>\u001c\u0017\r\u001c\u0011dQ&dGMP\n\u0007\u0005+\u0012YF!\u0019\u0011\t\u0005e\"QL\u0005\u0005\u0005?\nYD\u0001\u0004PE*,7\r\u001e\t\u0007\u001d\u0002\u0011\t#a>")
public interface StepperShape<T, S extends Stepper<?>> {
    public static StepperShape<Float, DoubleStepper> jFloatStepperShape() {
        return StepperShape$.MODULE$.jFloatStepperShape();
    }

    public static StepperShape<Object, DoubleStepper> floatStepperShape() {
        return StepperShape$.MODULE$.floatStepperShape();
    }

    public static StepperShape<Character, IntStepper> jCharacterStepperShape() {
        return StepperShape$.MODULE$.jCharacterStepperShape();
    }

    public static StepperShape<Object, IntStepper> charStepperShape() {
        return StepperShape$.MODULE$.charStepperShape();
    }

    public static StepperShape<Short, IntStepper> jShortStepperShape() {
        return StepperShape$.MODULE$.jShortStepperShape();
    }

    public static StepperShape<Object, IntStepper> shortStepperShape() {
        return StepperShape$.MODULE$.shortStepperShape();
    }

    public static StepperShape<Byte, IntStepper> jByteStepperShape() {
        return StepperShape$.MODULE$.jByteStepperShape();
    }

    public static StepperShape<Object, IntStepper> byteStepperShape() {
        return StepperShape$.MODULE$.byteStepperShape();
    }

    public static StepperShape<Double, DoubleStepper> jDoubleStepperShape() {
        return StepperShape$.MODULE$.jDoubleStepperShape();
    }

    public static StepperShape<Object, DoubleStepper> doubleStepperShape() {
        return StepperShape$.MODULE$.doubleStepperShape();
    }

    public static StepperShape<Long, LongStepper> jLongStepperShape() {
        return StepperShape$.MODULE$.jLongStepperShape();
    }

    public static StepperShape<Object, LongStepper> longStepperShape() {
        return StepperShape$.MODULE$.longStepperShape();
    }

    public static StepperShape<Integer, IntStepper> jIntegerStepperShape() {
        return StepperShape$.MODULE$.jIntegerStepperShape();
    }

    public static StepperShape<Object, IntStepper> intStepperShape() {
        return StepperShape$.MODULE$.intStepperShape();
    }

    public static int FloatShape() {
        return StepperShape$.MODULE$.FloatShape();
    }

    public static int CharShape() {
        return StepperShape$.MODULE$.CharShape();
    }

    public static int ShortShape() {
        return StepperShape$.MODULE$.ShortShape();
    }

    public static int ByteShape() {
        return StepperShape$.MODULE$.ByteShape();
    }

    public static int DoubleShape() {
        return StepperShape$.MODULE$.DoubleShape();
    }

    public static int LongShape() {
        return StepperShape$.MODULE$.LongShape();
    }

    public static int IntShape() {
        return StepperShape$.MODULE$.IntShape();
    }

    public static int ReferenceShape() {
        return StepperShape$.MODULE$.ReferenceShape();
    }

    public static <T> StepperShape<T, AnyStepper<T>> anyStepperShape() {
        return StepperShape$.MODULE$.anyStepperShapePrototype();
    }

    public static <T> StepperShape<T, Stepper<T>> baseStepperShape() {
        return StepperShape$.MODULE$.anyStepperShapePrototype();
    }

    public int shape();

    public S seqUnbox(AnyStepper<T> var1);

    public S parUnbox(AnyStepper<T> var1);

    public static final class Shape {
        private final int scala$collection$StepperShape$Shape$$s;

        public int scala$collection$StepperShape$Shape$$s() {
            return this.scala$collection$StepperShape$Shape$$s;
        }

        public int hashCode() {
            return Integer.hashCode(this.scala$collection$StepperShape$Shape$$s());
        }

        public boolean equals(Object x$1) {
            return StepperShape$Shape$.MODULE$.equals$extension(this.scala$collection$StepperShape$Shape$$s(), x$1);
        }

        public Shape(int s2) {
            this.scala$collection$StepperShape$Shape$$s = s2;
        }
    }
}

