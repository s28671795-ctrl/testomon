/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.convert.impl;

import java.util.NoSuchElementException;
import java.util.Spliterator;
import scala.Function2;
import scala.collection.AnyStepper;
import scala.collection.Iterator;
import scala.collection.Stepper;
import scala.collection.convert.impl.ChampStepperBase;
import scala.collection.immutable.Node;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\t4Qa\u0003\u0007\u0003!QA\u0001\"\u000f\u0001\u0003\u0002\u0003\u0006IA\u000f\u0005\t{\u0001\u0011)\u0019!C\t}!A!\t\u0001B\u0001B\u0003%q\bC\u0003D\u0001\u0011\u0005A\tC\u0003H\u0001\u0011\u0005\u0001\nC\u0003J\u0001\u0011\u0005!j\u0002\u0004L\u0019!\u0005\u0001\u0003\u0014\u0004\u0007\u00171A\t\u0001E'\t\u000b\rCA\u0011A)\t\u000bICA\u0011A*\u0003\u001f\u0005s\u0017p\u00115b[B\u001cF/\u001a9qKJT!!\u0004\b\u0002\t%l\u0007\u000f\u001c\u0006\u0003\u001fA\tqaY8om\u0016\u0014HO\u0003\u0002\u0012%\u0005Q1m\u001c7mK\u000e$\u0018n\u001c8\u000b\u0003M\tQa]2bY\u0006,2!\u0006\u000f)'\r\u0001a\u0003\u000e\t\u0007/aQr\u0005\u000e\u001d\u000e\u00031I!!\u0007\u0007\u0003!\rC\u0017-\u001c9Ti\u0016\u0004\b/\u001a:CCN,\u0007CA\u000e\u001d\u0019\u0001!Q!\b\u0001C\u0002}\u0011\u0011!Q\u0002\u0001#\t\u0001C\u0005\u0005\u0002\"E5\t!#\u0003\u0002$%\t9aj\u001c;iS:<\u0007CA\u0011&\u0013\t1#CA\u0002B]f\u0004\"a\u0007\u0015\u0005\u000b%\u0002!\u0019\u0001\u0016\u0003\u0003Q\u000b\"a\u000b\u0018\u0011\u0005\u0005b\u0013BA\u0017\u0013\u0005\u0011qU\u000f\u001c7\u0011\u0007=\u0012t%D\u00011\u0015\t\t\u0004#A\u0005j[6,H/\u00192mK&\u00111\u0007\r\u0002\u0005\u001d>$W\rE\u00026mii\u0011\u0001E\u0005\u0003oA\u0011!\"\u00118z'R,\u0007\u000f]3s!\u00119\u0002AG\u0014\u0002\u0011}k\u0017\r_*ju\u0016\u0004\"!I\u001e\n\u0005q\u0012\"aA%oi\u00069Q\r\u001f;sC\u000e$X#A \u0011\u000b\u0005\u0002uE\u000f\u000e\n\u0005\u0005\u0013\"!\u0003$v]\u000e$\u0018n\u001c83\u0003!)\u0007\u0010\u001e:bGR\u0004\u0013A\u0002\u001fj]&$h\bF\u00029\u000b\u001aCQ!\u000f\u0003A\u0002iBQ!\u0010\u0003A\u0002}\n\u0001B\\3yiN#X\r\u001d\u000b\u00025\u0005I1/Z7jG2|g.\u001a\u000b\u0002q\u0005y\u0011I\\=DQ\u0006l\u0007o\u0015;faB,'\u000f\u0005\u0002\u0018\u0011M\u0011\u0001B\u0014\t\u0003C=K!\u0001\u0015\n\u0003\r\u0005s\u0017PU3g)\u0005a\u0015\u0001\u00024s_6,2\u0001V,Z)\u0011)FL\u00181\u0011\t]\u0001a\u000b\u0017\t\u00037]#Q!\b\u0006C\u0002}\u0001\"aG-\u0005\u000b%R!\u0019\u0001.\u0012\u0005-Z\u0006cA\u001831\")QL\u0003a\u0001u\u00059Q.\u0019=TSj,\u0007\"B0\u000b\u0001\u0004A\u0016\u0001\u0002:p_RDQ!\u0010\u0006A\u0002\u0005\u0004R!\t!YuY\u0003")
public final class AnyChampStepper<A, T extends Node<T>>
extends ChampStepperBase<A, T, AnyStepper<A>, AnyChampStepper<A, T>>
implements AnyStepper<A> {
    private final Function2<T, Object, A> extract;

    /*
     * WARNING - void declaration
     */
    public static <A, T extends Node<T>> AnyChampStepper<A, T> from(int maxSize, T root, Function2<T, Object, A> extract) {
        void var3_3;
        AnyChampStepper<A, T> from_ans = new AnyChampStepper<A, T>(maxSize, extract);
        from_ans.initRoot(root);
        return var3_3;
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

    public Function2<T, Object, A> extract() {
        return this.extract;
    }

    @Override
    public A nextStep() {
        if (this.hasStep()) {
            A ans = this.extract().apply(this.currentValueNode(), this.currentValueCursor());
            this.currentValueCursor_$eq(this.currentValueCursor() + 1);
            this.maxSize_$eq(this.maxSize() - 1);
            return ans;
        }
        throw new NoSuchElementException("Empty Stepper");
    }

    @Override
    public AnyChampStepper<A, T> semiclone() {
        return new AnyChampStepper<A, T>(0, this.extract());
    }

    public AnyChampStepper(int _maxSize, Function2<T, Object, A> extract) {
        this.extract = extract;
        super(_maxSize);
    }
}

