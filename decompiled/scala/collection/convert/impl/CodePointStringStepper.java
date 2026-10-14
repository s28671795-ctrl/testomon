/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.convert.impl;

import java.util.NoSuchElementException;
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import scala.collection.IntStepper;
import scala.collection.Iterator;
import scala.collection.Stepper;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u00014QAD\b\u0003']A\u0001b\f\u0001\u0003\u0002\u0003\u0006I\u0001\r\u0005\tq\u0001\u0011\t\u0019!C\u0005s!AQ\b\u0001BA\u0002\u0013%a\b\u0003\u0005E\u0001\t\u0005\t\u0015)\u0003;\u0011!)\u0005A!a\u0001\n\u0013I\u0004\u0002\u0003$\u0001\u0005\u0003\u0007I\u0011B$\t\u0011%\u0003!\u0011!Q!\niBQA\u0013\u0001\u0005\u0002-CQ!\u0015\u0001\u0005\u0002eBQA\u0015\u0001\u0005\u0002MCQa\u0016\u0001\u0005\u0002aCQ\u0001\u0018\u0001\u0005\u0002uCQA\u0018\u0001\u0005\u0002}\u0013acQ8eKB{\u0017N\u001c;TiJLgnZ*uKB\u0004XM\u001d\u0006\u0003!E\tA![7qY*\u0011!cE\u0001\bG>tg/\u001a:u\u0015\t!R#\u0001\u0006d_2dWm\u0019;j_:T\u0011AF\u0001\u0006g\u000e\fG.Y\n\u0005\u0001aa\u0002\u0005\u0005\u0002\u001a55\tQ#\u0003\u0002\u001c+\t1\u0011I\\=SK\u001a\u0004\"!\b\u0010\u000e\u0003MI!aH\n\u0003\u0015%sGo\u0015;faB,'\u000f\u0005\u0002\"Y9\u0011!E\u000b\b\u0003G%r!\u0001\n\u0015\u000e\u0003\u0015R!AJ\u0014\u0002\rq\u0012xn\u001c;?\u0007\u0001I\u0011AF\u0005\u0003)UI!aK\n\u0002\u000fM#X\r\u001d9fe&\u0011QF\f\u0002\u000f\u000b\u001a4\u0017nY5f]R\u001c\u0006\u000f\\5u\u0015\tY3#\u0001\u0006v]\u0012,'\u000f\\=j]\u001e\u0004\"!M\u001b\u000f\u0005I\u001a\u0004C\u0001\u0013\u0016\u0013\t!T#\u0001\u0004Qe\u0016$WMZ\u0005\u0003m]\u0012aa\u0015;sS:<'B\u0001\u001b\u0016\u0003\tI\u0007'F\u0001;!\tI2(\u0003\u0002=+\t\u0019\u0011J\u001c;\u0002\r%\u0004t\fJ3r)\ty$\t\u0005\u0002\u001a\u0001&\u0011\u0011)\u0006\u0002\u0005+:LG\u000fC\u0004D\u0007\u0005\u0005\t\u0019\u0001\u001e\u0002\u0007a$\u0013'A\u0002ja\u0001\n!!\u001b(\u0002\r%tu\fJ3r)\ty\u0004\nC\u0004D\r\u0005\u0005\t\u0019\u0001\u001e\u0002\u0007%t\u0005%\u0001\u0004=S:LGO\u0010\u000b\u0005\u0019:{\u0005\u000b\u0005\u0002N\u00015\tq\u0002C\u00030\u0011\u0001\u0007\u0001\u0007C\u00039\u0011\u0001\u0007!\bC\u0003F\u0011\u0001\u0007!(A\bdQ\u0006\u0014\u0018m\u0019;fe&\u001cH/[2t\u00031)7\u000f^5nCR,7+\u001b>f+\u0005!\u0006CA\rV\u0013\t1VC\u0001\u0003M_:<\u0017a\u00025bgN#X\r]\u000b\u00023B\u0011\u0011DW\u0005\u00037V\u0011qAQ8pY\u0016\fg.\u0001\u0005oKb$8\u000b^3q)\u0005Q\u0014\u0001\u0003;ssN\u0003H.\u001b;\u0015\u00031\u0003")
public final class CodePointStringStepper
implements IntStepper,
Stepper.EfficientSplit {
    public final String scala$collection$convert$impl$CodePointStringStepper$$underlying;
    private int scala$collection$convert$impl$CodePointStringStepper$$i0;
    private int iN;

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

    public int scala$collection$convert$impl$CodePointStringStepper$$i0() {
        return this.scala$collection$convert$impl$CodePointStringStepper$$i0;
    }

    public void scala$collection$convert$impl$CodePointStringStepper$$i0_$eq(int x$1) {
        this.scala$collection$convert$impl$CodePointStringStepper$$i0 = x$1;
    }

    private int iN() {
        return this.iN;
    }

    private void iN_$eq(int x$1) {
        this.iN = x$1;
    }

    @Override
    public int characteristics() {
        return 1296;
    }

    @Override
    public long estimateSize() {
        return this.iN() - this.scala$collection$convert$impl$CodePointStringStepper$$i0();
    }

    @Override
    public boolean hasStep() {
        return this.scala$collection$convert$impl$CodePointStringStepper$$i0() < this.iN();
    }

    @Override
    public int nextStep() {
        return this.nextStep$mcI$sp();
    }

    @Override
    public CodePointStringStepper trySplit() {
        if (this.iN() - 3 > this.scala$collection$convert$impl$CodePointStringStepper$$i0()) {
            int half = this.scala$collection$convert$impl$CodePointStringStepper$$i0() + this.iN() >>> 1;
            if (Character.isLowSurrogate(this.scala$collection$convert$impl$CodePointStringStepper$$underlying.charAt(half))) {
                --half;
            }
            CodePointStringStepper ans = new CodePointStringStepper(this.scala$collection$convert$impl$CodePointStringStepper$$underlying, this.scala$collection$convert$impl$CodePointStringStepper$$i0(), half);
            this.scala$collection$convert$impl$CodePointStringStepper$$i0_$eq(half);
            return ans;
        }
        return null;
    }

    @Override
    public int nextStep$mcI$sp() {
        if (this.hasStep()) {
            int cp = this.scala$collection$convert$impl$CodePointStringStepper$$underlying.codePointAt(this.scala$collection$convert$impl$CodePointStringStepper$$i0());
            this.scala$collection$convert$impl$CodePointStringStepper$$i0_$eq(this.scala$collection$convert$impl$CodePointStringStepper$$i0() + Character.charCount(cp));
            return cp;
        }
        throw new NoSuchElementException("Empty Stepper");
    }

    public CodePointStringStepper(String underlying, int i0, int iN) {
        this.scala$collection$convert$impl$CodePointStringStepper$$underlying = underlying;
        this.scala$collection$convert$impl$CodePointStringStepper$$i0 = i0;
        this.iN = iN;
    }
}

