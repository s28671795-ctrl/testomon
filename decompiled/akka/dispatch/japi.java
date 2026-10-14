/*
 * Decompiled with CFR 0.152.
 */
package akka.dispatch;

import scala.Function1;
import scala.reflect.ScalaSignature;
import scala.runtime.AbstractPartialFunction;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;

@ScalaSignature(bytes="\u0006\u0005\u0005}t!B\r\u001b\u0011\u0003yb!B\u0011\u001b\u0011\u0003\u0011\u0003\"B\u0015\u0002\t\u0003Qc\u0001B\u0016\u0002\u00011BQ!K\u0002\u0005\u0002\tCQ!R\u0002\u0005F\u0019CQ\u0001T\u0002\u0005F5CQaT\u0002\u0005\u0012A3AaZ\u0001\u0001Q\")\u0011\u0006\u0003C\u0001s\")Q\t\u0003C#w\")A\n\u0003C#{\")q\n\u0003C\t\u007f\u001a1\u00111B\u0001\u0001\u0003\u001bAa!K\u0007\u0005\u0002\u0005m\u0001B\u0002'\u000e\t\u000b\ny\u0002\u0003\u0004P\u001b\u0011E\u00111\u0005\u0004\u0007\u0003W\t\u0001!!\f\t\r%\nB\u0011AA\u001c\u0011\u001d\tY$\u0005C\u0003\u0003{Aq!!\u0013\u0012\t\u000b\tY\u0005C\u0004\u0002XE!)!!\u0017\t\u000f\u0005\u0015\u0014\u0003\"\u0002\u0002h!1A*\u0005C#\u0003gBaaT\t\u0005\u0012\u0005]\u0014\u0001\u00026ba&T!a\u0007\u000f\u0002\u0011\u0011L7\u000f]1uG\"T\u0011!H\u0001\u0005C.\\\u0017m\u0001\u0001\u0011\u0005\u0001\nQ\"\u0001\u000e\u0003\t)\f\u0007/[\n\u0003\u0003\r\u0002\"\u0001J\u0014\u000e\u0003\u0015R\u0011AJ\u0001\u0006g\u000e\fG.Y\u0005\u0003Q\u0015\u0012a!\u00118z%\u00164\u0017A\u0002\u001fj]&$h\bF\u0001 \u00059\u0019\u0015\r\u001c7cC\u000e\\'I]5eO\u0016,\"!\f\u001c\u0014\u0005\rq\u0003\u0003B\u00183i}j\u0011\u0001\r\u0006\u0003c\u0015\nqA];oi&lW-\u0003\u00024a\t9\u0012IY:ue\u0006\u001cG\u000fU1si&\fGNR;oGRLwN\u001c\t\u0003kYb\u0001\u0001\u0002\u00048\u0007!\u0015\r\u0001\u000f\u0002\u0002)F\u0011\u0011\b\u0010\t\u0003IiJ!aO\u0013\u0003\u000f9{G\u000f[5oOB\u0011A%P\u0005\u0003}\u0015\u00121!\u00118z!\ty\u0003)\u0003\u0002Ba\tI!i\u001c=fIVs\u0017\u000e\u001e\u000b\u0002\u0007B\u0019Ai\u0001\u001b\u000e\u0003\u0005\t1\"[:EK\u001aLg.\u001a3BiR\u0011qI\u0013\t\u0003I!K!!S\u0013\u0003\u000f\t{w\u000e\\3b]\")1*\u0002a\u0001i\u0005\tA/A\u0003baBd\u0017\u0010\u0006\u0002@\u001d\")1J\u0002a\u0001i\u0005A\u0011N\u001c;fe:\fG\u000e\u0006\u0002R)B\u0011AEU\u0005\u0003'\u0016\u0012A!\u00168ji\")Qk\u0002a\u0001i\u00051!/Z:vYRD#\u0001V,\u0011\u0005a[V\"A-\u000b\u0005ic\u0012\u0001B;uS2L!\u0001X-\u0003\rUtWo]3eQ\u0019\u0019a,\u00192eKB\u0011AeX\u0005\u0003A\u0016\u0012!\u0002Z3qe\u0016\u001c\u0017\r^3e\u0003\u001diWm]:bO\u0016\f\u0013aY\u00011\t>\u0004cn\u001c;!kN,\u0007\u0005\u001e5jg\u0002\"\u0017N]3di2LH\u0006I;tK\u0002\u001aXOY2mCN\u001cXm\u001d\u0011pM\u0002\"\b.[:\u0002\u000bMLgnY3\"\u0003\u0019\f1A\r\u00181\u00055\u0011VmY8wKJ\u0014%/\u001b3hKV\u0011\u0011\u000e_\n\u0003\u0011)\u0004Ba\f\u001aloB\u0011A\u000e\u001e\b\u0003[Jt!A\\9\u000e\u0003=T!\u0001\u001d\u0010\u0002\rq\u0012xn\u001c;?\u0013\u00051\u0013BA:&\u0003\u001d\u0001\u0018mY6bO\u0016L!!\u001e<\u0003\u0013QC'o\\<bE2,'BA:&!\t)\u0004\u0010\u0002\u00048\u0011\u0011\u0015\r\u0001\u000f\u000b\u0002uB\u0019A\tC<\u0015\u0005\u001dc\b\"B&\u000b\u0001\u0004YGCA<\u007f\u0011\u0015Y5\u00021\u0001l)\r9\u0018\u0011\u0001\u0005\u0006+2\u0001\ra\u001b\u0015\u0004\u0003\u00039\u0006f\u0002\u0005_C\u0006\u001dA-Z\u0011\u0003\u0003\u0013\tq\u0005R8!]>$\b%^:fAQD\u0017n\u001d\u0011eSJ,7\r\u001e7zY\u0001*8/\u001a\u0011(%\u0016\u001cwN^3sO\t)\"i\\8mK\u0006tg)\u001e8di&|gN\u0011:jI\u001e,W\u0003BA\b\u00033\u0019B!D\u0012\u0002\u0012A1A%a\u0005\u0002\u0018\u001dK1!!\u0006&\u0005%1UO\\2uS>t\u0017\u0007E\u00026\u00033!aaN\u0007\t\u0006\u0004ADCAA\u000f!\u0011!U\"a\u0006\u0015\u0007\u001d\u000b\t\u0003\u0003\u0004L\u001f\u0001\u0007\u0011q\u0003\u000b\u0004\u000f\u0006\u0015\u0002BB+\u0011\u0001\u0004\t9\u0002K\u0002\u0002&]Cc!\u00040bE\u0012,'AE+oSR4UO\\2uS>t'I]5eO\u0016,B!a\f\u00026M!\u0011cIA\u0019!\u0019!\u00131CA\u001a\u007fA\u0019Q'!\u000e\u0005\r]\n\u0002R1\u00019)\t\tI\u0004\u0005\u0003E#\u0005M\u0012!D1qa2LH%\\2M\u0015\u0012\u001a\b\u000fF\u0002@\u0003\u007fAq!!\u0011\u0014\u0001\u0004\t\u0019%A\u0001m!\r!\u0013QI\u0005\u0004\u0003\u000f*#\u0001\u0002'p]\u001e\fQ\"\u00199qYf$Sn\u0019'JIM\u0004HcA \u0002N!9\u0011q\n\u000bA\u0002\u0005E\u0013!A5\u0011\u0007\u0011\n\u0019&C\u0002\u0002V\u0015\u00121!\u00138u\u00035\t\u0007\u000f\u001d7zI5\u001cGJ\u0012\u0013taR\u0019q(a\u0017\t\u000f\u0005uS\u00031\u0001\u0002`\u0005\ta\rE\u0002%\u0003CJ1!a\u0019&\u0005\u00151En\\1u\u00035\t\u0007\u000f\u001d7zI5\u001cG\n\u0012\u0013taR\u0019q(!\u001b\t\u000f\u0005-d\u00031\u0001\u0002n\u0005\tA\rE\u0002%\u0003_J1!!\u001d&\u0005\u0019!u.\u001e2mKR\u0019q(!\u001e\t\r-;\u0002\u0019AA\u001a)\r\t\u0016\u0011\u0010\u0005\u0007+b\u0001\r!a\r)\u0007\u0005et\u000b\u000b\u0004\u0012=\u0006\u0014G-\u001a")
public final class japi {

    public static class BooleanFunctionBridge<T>
    implements Function1<T, Object> {
        @Override
        public boolean apply$mcZD$sp(double v1) {
            return Function1.apply$mcZD$sp$(this, v1);
        }

        @Override
        public double apply$mcDD$sp(double v1) {
            return Function1.apply$mcDD$sp$(this, v1);
        }

        @Override
        public float apply$mcFD$sp(double v1) {
            return Function1.apply$mcFD$sp$(this, v1);
        }

        @Override
        public int apply$mcID$sp(double v1) {
            return Function1.apply$mcID$sp$(this, v1);
        }

        @Override
        public long apply$mcJD$sp(double v1) {
            return Function1.apply$mcJD$sp$(this, v1);
        }

        @Override
        public void apply$mcVD$sp(double v1) {
            Function1.apply$mcVD$sp$(this, v1);
        }

        @Override
        public boolean apply$mcZF$sp(float v1) {
            return Function1.apply$mcZF$sp$(this, v1);
        }

        @Override
        public double apply$mcDF$sp(float v1) {
            return Function1.apply$mcDF$sp$(this, v1);
        }

        @Override
        public float apply$mcFF$sp(float v1) {
            return Function1.apply$mcFF$sp$(this, v1);
        }

        @Override
        public int apply$mcIF$sp(float v1) {
            return Function1.apply$mcIF$sp$(this, v1);
        }

        @Override
        public long apply$mcJF$sp(float v1) {
            return Function1.apply$mcJF$sp$(this, v1);
        }

        @Override
        public void apply$mcVF$sp(float v1) {
            Function1.apply$mcVF$sp$(this, v1);
        }

        @Override
        public boolean apply$mcZI$sp(int v1) {
            return Function1.apply$mcZI$sp$(this, v1);
        }

        @Override
        public double apply$mcDI$sp(int v1) {
            return Function1.apply$mcDI$sp$(this, v1);
        }

        @Override
        public float apply$mcFI$sp(int v1) {
            return Function1.apply$mcFI$sp$(this, v1);
        }

        @Override
        public int apply$mcII$sp(int v1) {
            return Function1.apply$mcII$sp$(this, v1);
        }

        @Override
        public long apply$mcJI$sp(int v1) {
            return Function1.apply$mcJI$sp$(this, v1);
        }

        @Override
        public void apply$mcVI$sp(int v1) {
            Function1.apply$mcVI$sp$(this, v1);
        }

        @Override
        public boolean apply$mcZJ$sp(long v1) {
            return Function1.apply$mcZJ$sp$(this, v1);
        }

        @Override
        public double apply$mcDJ$sp(long v1) {
            return Function1.apply$mcDJ$sp$(this, v1);
        }

        @Override
        public float apply$mcFJ$sp(long v1) {
            return Function1.apply$mcFJ$sp$(this, v1);
        }

        @Override
        public int apply$mcIJ$sp(long v1) {
            return Function1.apply$mcIJ$sp$(this, v1);
        }

        @Override
        public long apply$mcJJ$sp(long v1) {
            return Function1.apply$mcJJ$sp$(this, v1);
        }

        @Override
        public void apply$mcVJ$sp(long v1) {
            Function1.apply$mcVJ$sp$(this, v1);
        }

        @Override
        public <A> Function1<A, Object> compose(Function1<A, T> g2) {
            return Function1.compose$(this, g2);
        }

        @Override
        public <A> Function1<T, A> andThen(Function1<Object, A> g2) {
            return Function1.andThen$(this, g2);
        }

        @Override
        public String toString() {
            return Function1.toString$(this);
        }

        @Override
        public final boolean apply(T t2) {
            return this.internal(t2);
        }

        public boolean internal(T result) {
            return false;
        }

        public BooleanFunctionBridge() {
            Function1.$init$(this);
        }
    }

    public static class CallbackBridge<T>
    extends AbstractPartialFunction<T, BoxedUnit> {
        @Override
        public final boolean isDefinedAt(T t2) {
            return true;
        }

        @Override
        public final BoxedUnit apply(T t2) {
            this.internal(t2);
            return BoxedUnit.UNIT;
        }

        public void internal(T result) {
        }
    }

    public static class RecoverBridge<T>
    extends AbstractPartialFunction<Throwable, T> {
        @Override
        public final boolean isDefinedAt(Throwable t2) {
            return true;
        }

        @Override
        public final T apply(Throwable t2) {
            return this.internal(t2);
        }

        public T internal(Throwable result) {
            return null;
        }
    }

    public static class UnitFunctionBridge<T>
    implements Function1<T, BoxedUnit> {
        @Override
        public boolean apply$mcZD$sp(double v1) {
            return Function1.apply$mcZD$sp$(this, v1);
        }

        @Override
        public double apply$mcDD$sp(double v1) {
            return Function1.apply$mcDD$sp$(this, v1);
        }

        @Override
        public float apply$mcFD$sp(double v1) {
            return Function1.apply$mcFD$sp$(this, v1);
        }

        @Override
        public int apply$mcID$sp(double v1) {
            return Function1.apply$mcID$sp$(this, v1);
        }

        @Override
        public long apply$mcJD$sp(double v1) {
            return Function1.apply$mcJD$sp$(this, v1);
        }

        @Override
        public void apply$mcVD$sp(double v1) {
            Function1.apply$mcVD$sp$(this, v1);
        }

        @Override
        public boolean apply$mcZF$sp(float v1) {
            return Function1.apply$mcZF$sp$(this, v1);
        }

        @Override
        public double apply$mcDF$sp(float v1) {
            return Function1.apply$mcDF$sp$(this, v1);
        }

        @Override
        public float apply$mcFF$sp(float v1) {
            return Function1.apply$mcFF$sp$(this, v1);
        }

        @Override
        public int apply$mcIF$sp(float v1) {
            return Function1.apply$mcIF$sp$(this, v1);
        }

        @Override
        public long apply$mcJF$sp(float v1) {
            return Function1.apply$mcJF$sp$(this, v1);
        }

        @Override
        public void apply$mcVF$sp(float v1) {
            Function1.apply$mcVF$sp$(this, v1);
        }

        @Override
        public boolean apply$mcZI$sp(int v1) {
            return Function1.apply$mcZI$sp$(this, v1);
        }

        @Override
        public double apply$mcDI$sp(int v1) {
            return Function1.apply$mcDI$sp$(this, v1);
        }

        @Override
        public float apply$mcFI$sp(int v1) {
            return Function1.apply$mcFI$sp$(this, v1);
        }

        @Override
        public int apply$mcII$sp(int v1) {
            return Function1.apply$mcII$sp$(this, v1);
        }

        @Override
        public long apply$mcJI$sp(int v1) {
            return Function1.apply$mcJI$sp$(this, v1);
        }

        @Override
        public void apply$mcVI$sp(int v1) {
            Function1.apply$mcVI$sp$(this, v1);
        }

        @Override
        public boolean apply$mcZJ$sp(long v1) {
            return Function1.apply$mcZJ$sp$(this, v1);
        }

        @Override
        public double apply$mcDJ$sp(long v1) {
            return Function1.apply$mcDJ$sp$(this, v1);
        }

        @Override
        public float apply$mcFJ$sp(long v1) {
            return Function1.apply$mcFJ$sp$(this, v1);
        }

        @Override
        public int apply$mcIJ$sp(long v1) {
            return Function1.apply$mcIJ$sp$(this, v1);
        }

        @Override
        public long apply$mcJJ$sp(long v1) {
            return Function1.apply$mcJJ$sp$(this, v1);
        }

        @Override
        public void apply$mcVJ$sp(long v1) {
            Function1.apply$mcVJ$sp$(this, v1);
        }

        @Override
        public <A> Function1<A, BoxedUnit> compose(Function1<A, T> g2) {
            return Function1.compose$(this, g2);
        }

        @Override
        public <A> Function1<T, A> andThen(Function1<BoxedUnit, A> g2) {
            return Function1.andThen$(this, g2);
        }

        @Override
        public String toString() {
            return Function1.toString$(this);
        }

        public final BoxedUnit apply$mcLJ$sp(long l) {
            this.internal(BoxesRunTime.boxToLong(l));
            return BoxedUnit.UNIT;
        }

        public final BoxedUnit apply$mcLI$sp(int i) {
            this.internal(BoxesRunTime.boxToInteger(i));
            return BoxedUnit.UNIT;
        }

        public final BoxedUnit apply$mcLF$sp(float f) {
            this.internal(BoxesRunTime.boxToFloat(f));
            return BoxedUnit.UNIT;
        }

        public final BoxedUnit apply$mcLD$sp(double d) {
            this.internal(BoxesRunTime.boxToDouble(d));
            return BoxedUnit.UNIT;
        }

        @Override
        public final BoxedUnit apply(T t2) {
            this.internal(t2);
            return BoxedUnit.UNIT;
        }

        public void internal(T result) {
        }

        public UnitFunctionBridge() {
            Function1.$init$(this);
        }
    }
}

