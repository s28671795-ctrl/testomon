/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.convert.impl;

import java.util.NoSuchElementException;
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import scala.Function1;
import scala.collection.DoubleStepper;
import scala.collection.Iterator;
import scala.collection.Stepper;
import scala.collection.convert.impl.BinaryTreeStepper$;
import scala.collection.convert.impl.BinaryTreeStepperBase;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;

@ScalaSignature(bytes="\u0006\u0005M4Q\u0001E\t\u0003+eA\u0001\u0002\u000e\u0001\u0003\u0002\u0003\u0006I!\u000e\u0005\tq\u0001\u0011\t\u0011)A\u0005G!A\u0011\b\u0001B\u0001B\u0003%!\b\u0003\u0005>\u0001\t\u0005\t\u0015!\u00036\u0011%q\u0004A!A!\u0002\u0013y$\tC\u0005E\u0001\t\u0005\t\u0015!\u0003@\u000b\"Aq\t\u0001BC\u0002\u0013E\u0001\n\u0003\u0005K\u0001\t\u0005\t\u0015!\u0003J\u0011\u0015Y\u0005\u0001\"\u0001M\u0011\u0015!\u0006\u0001\"\u0001V\u0011\u00151\u0006\u0001\"\u0001X\u000f\u0019\u0001\u0017\u0003#\u0001\u0016C\u001a1\u0001#\u0005E\u0001+\tDQaS\u0007\u0005\u0002\rDQ\u0001Z\u0007\u0005\u0002\u0015\u0014q\u0003R8vE2,')\u001b8bef$&/Z3Ti\u0016\u0004\b/\u001a:\u000b\u0005I\u0019\u0012\u0001B5na2T!\u0001F\u000b\u0002\u000f\r|gN^3si*\u0011acF\u0001\u000bG>dG.Z2uS>t'\"\u0001\r\u0002\u000bM\u001c\u0017\r\\1\u0016\u0005i)3c\u0001\u0001\u001c_A1A$H\u0010$_Mj\u0011!E\u0005\u0003=E\u0011QCQ5oCJLHK]3f'R,\u0007\u000f]3s\u0005\u0006\u001cX\r\u0005\u0002!C5\tq#\u0003\u0002#/\t1Ai\\;cY\u0016\u0004\"\u0001J\u0013\r\u0001\u0011)a\u0005\u0001b\u0001Q\t\tAk\u0001\u0001\u0012\u0005%b\u0003C\u0001\u0011+\u0013\tYsC\u0001\u0003Ok2d\u0007C\u0001\u0011.\u0013\tqsC\u0001\u0004B]f\u0014VM\u001a\t\u0003aEj\u0011!F\u0005\u0003eU\u0011Q\u0002R8vE2,7\u000b^3qa\u0016\u0014\bc\u0001\u000f\u0001G\u0005Qq,\\1y\u0019\u0016tw\r\u001e5\u0011\u0005\u00012\u0014BA\u001c\u0018\u0005\rIe\u000e^\u0001\u000b?6L8)\u001e:sK:$\u0018AB0ti\u0006\u001c7\u000eE\u0002!w1J!\u0001P\f\u0003\u000b\u0005\u0013(/Y=\u0002\r}Kg\u000eZ3y\u0003\u0015yF.\u001a4u!\u0011\u0001\u0003iI\u0012\n\u0005\u0005;\"!\u0003$v]\u000e$\u0018n\u001c82\u0013\t\u0019U$\u0001\u0003mK\u001a$\u0018AB0sS\u001eDG/\u0003\u0002G;\u0005)!/[4ii\u00069Q\r\u001f;sC\u000e$X#A%\u0011\t\u0001\u00025eH\u0001\tKb$(/Y2uA\u00051A(\u001b8jiz\"\u0002bM'O\u001fB\u000b&k\u0015\u0005\u0006i%\u0001\r!\u000e\u0005\u0006q%\u0001\ra\t\u0005\u0006s%\u0001\rA\u000f\u0005\u0006{%\u0001\r!\u000e\u0005\u0006}%\u0001\ra\u0010\u0005\u0006\t&\u0001\ra\u0010\u0005\u0006\u000f&\u0001\r!S\u0001\t]\u0016DHo\u0015;faR\tq$A\u0005tK6L7\r\\8oKR)1\u0007\u0017.]=\")\u0011l\u0003a\u0001k\u0005!Q.\u0019=M\u0011\u0015Y6\u00021\u0001$\u0003\ri\u0017p\u0011\u0005\u0006;.\u0001\rAO\u0001\u0004gR\\\u0007\"B0\f\u0001\u0004)\u0014AA5y\u0003]!u.\u001e2mK\nKg.\u0019:z)J,Wm\u0015;faB,'\u000f\u0005\u0002\u001d\u001bM\u0011Q\u0002\f\u000b\u0002C\u0006!aM]8n+\t1\u0017\u000e\u0006\u0004hU2t\u0007/\u001d\t\u00049\u0001A\u0007C\u0001\u0013j\t\u00151sB1\u0001)\u0011\u0015Yw\u00021\u00016\u0003%i\u0017\r\u001f'f]\u001e$\b\u000eC\u0003n\u001f\u0001\u0007\u0001.\u0001\u0003s_>$\b\"B\"\u0010\u0001\u0004y\u0007\u0003\u0002\u0011AQ\"DQAR\bA\u0002=DQaR\bA\u0002I\u0004B\u0001\t!i?\u0001")
public final class DoubleBinaryTreeStepper<T>
extends BinaryTreeStepperBase<Object, T, DoubleStepper, DoubleBinaryTreeStepper<T>>
implements DoubleStepper {
    private final Function1<T, Object> extract;

    public static <T> DoubleBinaryTreeStepper<T> from(int maxLength, T root, Function1<T, T> left, Function1<T, T> right, Function1<T, Object> extract) {
        DoubleBinaryTreeStepper<Object> from_ans = new DoubleBinaryTreeStepper<Object>(0, null, BinaryTreeStepper$.MODULE$.emptyStack(), -1, left, right, extract);
        from_ans.initialize(root, maxLength);
        return from_ans;
    }

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
        return Stepper.nextStep$mcI$sp$(this);
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
    public <B> Spliterator<?> spliterator$mcI$sp() {
        return Stepper.spliterator$mcI$sp$(this);
    }

    @Override
    public <B> Spliterator<?> spliterator$mcJ$sp() {
        return Stepper.spliterator$mcJ$sp$(this);
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

    public Function1<T, Object> extract() {
        return this.extract;
    }

    @Override
    public double nextStep() {
        return this.nextStep$mcD$sp();
    }

    @Override
    public DoubleBinaryTreeStepper<T> semiclone(int maxL, T myC, Object[] stk, int ix) {
        return new DoubleBinaryTreeStepper<T>(maxL, myC, stk, ix, this.left(), this.right(), this.extract());
    }

    @Override
    public double nextStep$mcD$sp() {
        if (this.hasStep()) {
            double ans = BoxesRunTime.unboxToDouble(this.extract().apply(this.myCurrent()));
            this.myCurrent_$eq(null);
            this.maxLength_$eq(this.maxLength() - 1);
            return ans;
        }
        throw new NoSuchElementException("Empty Stepper");
    }

    public DoubleBinaryTreeStepper(int _maxLength, T _myCurrent, Object[] _stack, int _index, Function1<T, T> _left, Function1<T, T> _right, Function1<T, Object> extract) {
        this.extract = extract;
        super(_maxLength, _myCurrent, _stack, _index, _left, _right);
    }
}

