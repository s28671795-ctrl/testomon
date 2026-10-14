/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.convert.impl;

import java.util.NoSuchElementException;
import java.util.Spliterator;
import scala.Function1;
import scala.collection.AnyStepper;
import scala.collection.Iterator;
import scala.collection.Stepper;
import scala.collection.convert.impl.BinaryTreeStepper$;
import scala.collection.convert.impl.BinaryTreeStepperBase;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005q4Q\u0001E\t\u0003+eA\u0001b\u000f\u0001\u0003\u0002\u0003\u0006I\u0001\u0010\u0005\t\u007f\u0001\u0011\t\u0011)A\u0005Y!A\u0001\t\u0001B\u0001B\u0003%\u0011\t\u0003\u0005E\u0001\t\u0005\t\u0015!\u0003=\u0011%)\u0005A!A!\u0002\u00131\u0015\nC\u0005L\u0001\t\u0005\t\u0015!\u0003G\u0019\"Aa\n\u0001BC\u0002\u0013Eq\n\u0003\u0005R\u0001\t\u0005\t\u0015!\u0003Q\u0011\u0015\u0011\u0006\u0001\"\u0001T\u0011\u0015Y\u0006\u0001\"\u0001]\u0011\u0015i\u0006\u0001\"\u0001_\u000f\u00199\u0017\u0003#\u0001\u0016Q\u001a1\u0001#\u0005E\u0001+%DQAU\u0007\u0005\u0002)DQa[\u0007\u0005\u00021\u0014A#\u00118z\u0005&t\u0017M]=Ue\u0016,7\u000b^3qa\u0016\u0014(B\u0001\n\u0014\u0003\u0011IW\u000e\u001d7\u000b\u0005Q)\u0012aB2p]Z,'\u000f\u001e\u0006\u0003-]\t!bY8mY\u0016\u001cG/[8o\u0015\u0005A\u0012!B:dC2\fWc\u0001\u000e\"[M\u0019\u0001a\u0007\u001c\u0011\rqir\u0004\f\u001c;\u001b\u0005\t\u0012B\u0001\u0010\u0012\u0005U\u0011\u0015N\\1ssR\u0013X-Z*uKB\u0004XM\u001d\"bg\u0016\u0004\"\u0001I\u0011\r\u0001\u0011)!\u0005\u0001b\u0001I\t\t\u0011i\u0001\u0001\u0012\u0005\u0015J\u0003C\u0001\u0014(\u001b\u00059\u0012B\u0001\u0015\u0018\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"A\n\u0016\n\u0005-:\"aA!osB\u0011\u0001%\f\u0003\u0006]\u0001\u0011\ra\f\u0002\u0002)F\u0011\u0001g\r\t\u0003MEJ!AM\f\u0003\t9+H\u000e\u001c\t\u0003MQJ!!N\f\u0003\r\u0005s\u0017PU3g!\r9\u0004hH\u0007\u0002+%\u0011\u0011(\u0006\u0002\u000b\u0003:L8\u000b^3qa\u0016\u0014\b\u0003\u0002\u000f\u0001?1\n!bX7bq2+gn\u001a;i!\t1S(\u0003\u0002?/\t\u0019\u0011J\u001c;\u0002\u0015}k\u0017pQ;se\u0016tG/\u0001\u0004`gR\f7m\u001b\t\u0004M\t\u001b\u0014BA\"\u0018\u0005\u0015\t%O]1z\u0003\u0019y\u0016N\u001c3fq\u0006)q\f\\3giB!ae\u0012\u0017-\u0013\tAuCA\u0005Gk:\u001cG/[8oc%\u0011!*H\u0001\u0005Y\u00164G/\u0001\u0004`e&<\u0007\u000e^\u0005\u0003\u001bv\tQA]5hQR\fq!\u001a=ue\u0006\u001cG/F\u0001Q!\u00111s\tL\u0010\u0002\u0011\u0015DHO]1di\u0002\na\u0001P5oSRtD\u0003\u0003\u001eU+Z;\u0006,\u0017.\t\u000bmJ\u0001\u0019\u0001\u001f\t\u000b}J\u0001\u0019\u0001\u0017\t\u000b\u0001K\u0001\u0019A!\t\u000b\u0011K\u0001\u0019\u0001\u001f\t\u000b\u0015K\u0001\u0019\u0001$\t\u000b-K\u0001\u0019\u0001$\t\u000b9K\u0001\u0019\u0001)\u0002\u00119,\u0007\u0010^*uKB$\u0012aH\u0001\ng\u0016l\u0017n\u00197p]\u0016$RAO0bG\u0016DQ\u0001Y\u0006A\u0002q\nA!\\1y\u0019\")!m\u0003a\u0001Y\u0005\u0019Q._\"\t\u000b\u0011\\\u0001\u0019A!\u0002\u0007M$8\u000eC\u0003g\u0017\u0001\u0007A(\u0001\u0002jq\u0006!\u0012I\\=CS:\f'/\u001f+sK\u0016\u001cF/\u001a9qKJ\u0004\"\u0001H\u0007\u0014\u00055\u0019D#\u00015\u0002\t\u0019\u0014x.\\\u000b\u0004[B\u0014HC\u00028tk^L(\u0010\u0005\u0003\u001d\u0001=\f\bC\u0001\u0011q\t\u0015\u0011sB1\u0001%!\t\u0001#\u000fB\u0003/\u001f\t\u0007q\u0006C\u0003u\u001f\u0001\u0007A(A\u0005nCbdUM\\4uQ\")ao\u0004a\u0001c\u0006!!o\\8u\u0011\u0015Qu\u00021\u0001y!\u00111s)]9\t\u000b5{\u0001\u0019\u0001=\t\u000b9{\u0001\u0019A>\u0011\t\u0019:\u0015o\u001c")
public final class AnyBinaryTreeStepper<A, T>
extends BinaryTreeStepperBase<A, T, AnyStepper<A>, AnyBinaryTreeStepper<A, T>>
implements AnyStepper<A> {
    private final Function1<T, A> extract;

    public static <A, T> AnyBinaryTreeStepper<A, T> from(int maxLength, T root, Function1<T, T> left, Function1<T, T> right, Function1<T, A> extract) {
        AnyBinaryTreeStepper<A, Object> from_ans = new AnyBinaryTreeStepper<A, Object>(0, null, BinaryTreeStepper$.MODULE$.emptyStack(), -1, left, right, extract);
        from_ans.initialize(root, maxLength);
        return from_ans;
    }

    @Override
    public <B> Spliterator<B> spliterator() {
        return AnyStepper.spliterator$(this);
    }

    @Override
    public <B> java.util.Iterator<B> javaIterator() {
        return AnyStepper.javaIterator$(this);
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
    public Iterator<A> iterator() {
        return Stepper.iterator$(this);
    }

    public Function1<T, A> extract() {
        return this.extract;
    }

    @Override
    public A nextStep() {
        if (this.hasStep()) {
            A ans = this.extract().apply(this.myCurrent());
            this.myCurrent_$eq(null);
            this.maxLength_$eq(this.maxLength() - 1);
            return ans;
        }
        throw new NoSuchElementException("Empty Stepper");
    }

    @Override
    public AnyBinaryTreeStepper<A, T> semiclone(int maxL, T myC, Object[] stk, int ix) {
        return new AnyBinaryTreeStepper<A, T>(maxL, myC, stk, ix, this.left(), this.right(), this.extract());
    }

    public AnyBinaryTreeStepper(int _maxLength, T _myCurrent, Object[] _stack, int _index, Function1<T, T> _left, Function1<T, T> _right, Function1<T, A> extract) {
        this.extract = extract;
        super(_maxLength, _myCurrent, _stack, _index, _left, _right);
    }
}

