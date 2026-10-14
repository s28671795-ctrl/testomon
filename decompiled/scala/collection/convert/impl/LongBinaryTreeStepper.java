/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.convert.impl;

import java.util.NoSuchElementException;
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import scala.Function1;
import scala.collection.Iterator;
import scala.collection.LongStepper;
import scala.collection.Stepper;
import scala.collection.convert.impl.BinaryTreeStepper$;
import scala.collection.convert.impl.BinaryTreeStepperBase;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;

@ScalaSignature(bytes="\u0006\u0005M4Q\u0001E\t\u0003+eA\u0001\u0002\u000e\u0001\u0003\u0002\u0003\u0006I!\u000e\u0005\tq\u0001\u0011\t\u0011)A\u0005G!A\u0011\b\u0001B\u0001B\u0003%!\b\u0003\u0005>\u0001\t\u0005\t\u0015!\u00036\u0011%q\u0004A!A!\u0002\u0013y$\tC\u0005E\u0001\t\u0005\t\u0015!\u0003@\u000b\"Aq\t\u0001BC\u0002\u0013E\u0001\n\u0003\u0005K\u0001\t\u0005\t\u0015!\u0003J\u0011\u0015Y\u0005\u0001\"\u0001M\u0011\u0015!\u0006\u0001\"\u0001V\u0011\u00151\u0006\u0001\"\u0001X\u000f\u0019\u0001\u0017\u0003#\u0001\u0016C\u001a1\u0001#\u0005E\u0001+\tDQaS\u0007\u0005\u0002\rDQ\u0001Z\u0007\u0005\u0002\u0015\u0014Q\u0003T8oO\nKg.\u0019:z)J,Wm\u0015;faB,'O\u0003\u0002\u0013'\u0005!\u0011.\u001c9m\u0015\t!R#A\u0004d_:4XM\u001d;\u000b\u0005Y9\u0012AC2pY2,7\r^5p]*\t\u0001$A\u0003tG\u0006d\u0017-\u0006\u0002\u001bKM\u0019\u0001aG\u0018\u0011\rqirdI\u00184\u001b\u0005\t\u0012B\u0001\u0010\u0012\u0005U\u0011\u0015N\\1ssR\u0013X-Z*uKB\u0004XM\u001d\"bg\u0016\u0004\"\u0001I\u0011\u000e\u0003]I!AI\f\u0003\t1{gn\u001a\t\u0003I\u0015b\u0001\u0001B\u0003'\u0001\t\u0007\u0001FA\u0001U\u0007\u0001\t\"!\u000b\u0017\u0011\u0005\u0001R\u0013BA\u0016\u0018\u0005\u0011qU\u000f\u001c7\u0011\u0005\u0001j\u0013B\u0001\u0018\u0018\u0005\u0019\te.\u001f*fMB\u0011\u0001'M\u0007\u0002+%\u0011!'\u0006\u0002\f\u0019>twm\u0015;faB,'\u000fE\u0002\u001d\u0001\r\n!bX7bq2+gn\u001a;i!\t\u0001c'\u0003\u00028/\t\u0019\u0011J\u001c;\u0002\u0015}k\u0017pQ;se\u0016tG/\u0001\u0004`gR\f7m\u001b\t\u0004Amb\u0013B\u0001\u001f\u0018\u0005\u0015\t%O]1z\u0003\u0019y\u0016N\u001c3fq\u0006)q\f\\3giB!\u0001\u0005Q\u0012$\u0013\t\tuCA\u0005Gk:\u001cG/[8oc%\u00111)H\u0001\u0005Y\u00164G/\u0001\u0004`e&<\u0007\u000e^\u0005\u0003\rv\tQA]5hQR\fq!\u001a=ue\u0006\u001cG/F\u0001J!\u0011\u0001\u0003iI\u0010\u0002\u0011\u0015DHO]1di\u0002\na\u0001P5oSRtD\u0003C\u001aN\u001d>\u0003\u0016KU*\t\u000bQJ\u0001\u0019A\u001b\t\u000baJ\u0001\u0019A\u0012\t\u000beJ\u0001\u0019\u0001\u001e\t\u000buJ\u0001\u0019A\u001b\t\u000byJ\u0001\u0019A \t\u000b\u0011K\u0001\u0019A \t\u000b\u001dK\u0001\u0019A%\u0002\u00119,\u0007\u0010^*uKB$\u0012aH\u0001\ng\u0016l\u0017n\u00197p]\u0016$Ra\r-[9zCQ!W\u0006A\u0002U\nA!\\1y\u0019\")1l\u0003a\u0001G\u0005\u0019Q._\"\t\u000bu[\u0001\u0019\u0001\u001e\u0002\u0007M$8\u000eC\u0003`\u0017\u0001\u0007Q'\u0001\u0002jq\u0006)Bj\u001c8h\u0005&t\u0017M]=Ue\u0016,7\u000b^3qa\u0016\u0014\bC\u0001\u000f\u000e'\tiA\u0006F\u0001b\u0003\u00111'o\\7\u0016\u0005\u0019LGCB4kY:\u0004\u0018\u000fE\u0002\u001d\u0001!\u0004\"\u0001J5\u0005\u000b\u0019z!\u0019\u0001\u0015\t\u000b-|\u0001\u0019A\u001b\u0002\u00135\f\u0007\u0010T3oORD\u0007\"B7\u0010\u0001\u0004A\u0017\u0001\u0002:p_RDQaQ\bA\u0002=\u0004B\u0001\t!iQ\")ai\u0004a\u0001_\")qi\u0004a\u0001eB!\u0001\u0005\u00115 \u0001")
public final class LongBinaryTreeStepper<T>
extends BinaryTreeStepperBase<Object, T, LongStepper, LongBinaryTreeStepper<T>>
implements LongStepper {
    private final Function1<T, Object> extract;

    public static <T> LongBinaryTreeStepper<T> from(int maxLength, T root, Function1<T, T> left, Function1<T, T> right, Function1<T, Object> extract) {
        LongBinaryTreeStepper<Object> from_ans = new LongBinaryTreeStepper<Object>(0, null, BinaryTreeStepper$.MODULE$.emptyStack(), -1, left, right, extract);
        from_ans.initialize(root, maxLength);
        return from_ans;
    }

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

    public Function1<T, Object> extract() {
        return this.extract;
    }

    @Override
    public long nextStep() {
        return this.nextStep$mcJ$sp();
    }

    @Override
    public LongBinaryTreeStepper<T> semiclone(int maxL, T myC, Object[] stk, int ix) {
        return new LongBinaryTreeStepper<T>(maxL, myC, stk, ix, this.left(), this.right(), this.extract());
    }

    @Override
    public long nextStep$mcJ$sp() {
        if (this.hasStep()) {
            long ans = BoxesRunTime.unboxToLong(this.extract().apply(this.myCurrent()));
            this.myCurrent_$eq(null);
            this.maxLength_$eq(this.maxLength() - 1);
            return ans;
        }
        throw new NoSuchElementException("Empty Stepper");
    }

    public LongBinaryTreeStepper(int _maxLength, T _myCurrent, Object[] _stack, int _index, Function1<T, T> _left, Function1<T, T> _right, Function1<T, Object> extract) {
        this.extract = extract;
        super(_maxLength, _myCurrent, _stack, _index, _left, _right);
    }
}

