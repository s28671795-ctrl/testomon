/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.convert.impl;

import java.util.Arrays;
import scala.Function1;
import scala.collection.Stepper;
import scala.collection.convert.impl.BinaryTreeStepper$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005mdA\u0002\u000e\u001c\u0003\u0003y2\u0005\u0003\u00059\u0001\t\u0005\r\u0011\"\u0005:\u0011!i\u0004A!a\u0001\n#q\u0004\u0002\u0003#\u0001\u0005\u0003\u0005\u000b\u0015\u0002\u001e\t\u0011\u0015\u0003!\u00111A\u0005\u0012\u0019C\u0001b\u0014\u0001\u0003\u0002\u0004%\t\u0002\u0015\u0005\t%\u0002\u0011\t\u0011)Q\u0005\u000f\"A1\u000b\u0001BA\u0002\u0013EA\u000b\u0003\u0005Y\u0001\t\u0005\r\u0011\"\u0005Z\u0011!Y\u0006A!A!B\u0013)\u0006\u0002\u0003/\u0001\u0005\u0003\u0007I\u0011C\u001d\t\u0011u\u0003!\u00111A\u0005\u0012yC\u0001\u0002\u0019\u0001\u0003\u0002\u0003\u0006KA\u000f\u0005\tC\u0002\u0011)\u0019!C\tE\"Aa\r\u0001B\u0001B\u0003%1\r\u0003\u0005h\u0001\t\u0015\r\u0011\"\u0005c\u0011!A\u0007A!A!\u0002\u0013\u0019\u0007\"B5\u0001\t\u0003Q\u0007bBA\u0011\u0001\u0011U\u00111\u0005\u0005\b\u0003o\u0001AQCA\u001d\u0011!\t\t\u0005\u0001C\u00037\u0005\r\u0003bBA'\u0001\u0019E\u0011q\n\u0005\u0007\u0003C\u0002A\u0011A\u001d\t\u000f\u0005\r\u0004\u0001\"\u0001\u0002f!9\u0011Q\u000e\u0001\u0005\u0002\u0005=\u0004bBA<\u0001\u0011\u0005\u0011\u0011\u0010\u0002\u0016\u0005&t\u0017M]=Ue\u0016,7\u000b^3qa\u0016\u0014()Y:f\u0015\taR$\u0001\u0003j[Bd'B\u0001\u0010 \u0003\u001d\u0019wN\u001c<feRT!\u0001I\u0011\u0002\u0015\r|G\u000e\\3di&|gNC\u0001#\u0003\u0015\u00198-\u00197b+\u0015!c.\u0013=}'\r\u0001Q%\u000b\t\u0003M\u001dj\u0011!I\u0005\u0003Q\u0005\u0012a!\u00118z%\u00164\u0007C\u0001\u00166\u001d\tY3G\u0004\u0002-e9\u0011Q&M\u0007\u0002])\u0011q\u0006M\u0001\u0007yI|w\u000e\u001e \u0004\u0001%\t!%\u0003\u0002!C%\u0011AgH\u0001\b'R,\u0007\u000f]3s\u0013\t1tG\u0001\bFM\u001aL7-[3oiN\u0003H.\u001b;\u000b\u0005Qz\u0012!C7bq2+gn\u001a;i+\u0005Q\u0004C\u0001\u0014<\u0013\ta\u0014EA\u0002J]R\fQ\"\\1y\u0019\u0016tw\r\u001e5`I\u0015\fHCA C!\t1\u0003)\u0003\u0002BC\t!QK\\5u\u0011\u001d\u0019%!!AA\u0002i\n1\u0001\u001f\u00132\u0003)i\u0017\r\u001f'f]\u001e$\b\u000eI\u0001\n[f\u001cUO\u001d:f]R,\u0012a\u0012\t\u0003\u0011&c\u0001\u0001B\u0003K\u0001\t\u00071JA\u0001U#\taU\u0005\u0005\u0002'\u001b&\u0011a*\t\u0002\u0005\u001dVdG.A\u0007ns\u000e+(O]3oi~#S-\u001d\u000b\u0003\u007fECqaQ\u0003\u0002\u0002\u0003\u0007q)\u0001\u0006ns\u000e+(O]3oi\u0002\nQa\u001d;bG.,\u0012!\u0016\t\u0004MY+\u0013BA,\"\u0005\u0015\t%O]1z\u0003%\u0019H/Y2l?\u0012*\u0017\u000f\u0006\u0002@5\"91\tCA\u0001\u0002\u0004)\u0016AB:uC\u000e\\\u0007%A\u0003j]\u0012,\u00070A\u0005j]\u0012,\u0007p\u0018\u0013fcR\u0011qh\u0018\u0005\b\u0007.\t\t\u00111\u0001;\u0003\u0019Ig\u000eZ3yA\u0005!A.\u001a4u+\u0005\u0019\u0007\u0003\u0002\u0014e\u000f\u001eK!!Z\u0011\u0003\u0013\u0019+hn\u0019;j_:\f\u0014!\u00027fMR\u0004\u0013!\u0002:jO\"$\u0018A\u0002:jO\"$\b%\u0001\u0004=S:LGO\u0010\u000b\u000eW\u0006U\u0011qCA\r\u00037\ti\"a\b\u0011\r1\u0004QnR<|\u001b\u0005Y\u0002C\u0001%o\t\u0015y\u0007A1\u0001q\u0005\u0005\t\u0015CA9u!\t1#/\u0003\u0002tC\t9aj\u001c;iS:<\u0007C\u0001\u0014v\u0013\t1\u0018EA\u0002B]f\u0004\"\u0001\u0013=\u0005\u000be\u0004!\u0019\u0001>\u0003\u0007M+(-\u0005\u0002MiB\u0011\u0001\n \u0003\u0006{\u0002\u0011\rA \u0002\u0005'\u0016l\u0017.\u0005\u0002r\u007fJ)\u0011\u0011A<\u0002\u0006\u0019)\u00111\u0001\u0001\u0001\u007f\naAH]3gS:,W.\u001a8u}A2\u0011qAA\u0006\u0003#\u0001\u0002\u0002\u001c\u0001n\u000f\u0006%\u0011q\u0002\t\u0004\u0011\u0006-AACA\u0007y\u0006\u0005\t\u0011!B\u0001a\n\u0019q\fJ\u0019\u0011\u0007!\u000b\t\u0002\u0002\u0006\u0002\u0014q\f\t\u0011!A\u0003\u0002A\u00141a\u0018\u00133\u0011\u0015A\u0014\u00031\u0001;\u0011\u0015)\u0015\u00031\u0001H\u0011\u0015\u0019\u0016\u00031\u0001V\u0011\u0015a\u0016\u00031\u0001;\u0011\u0015\t\u0017\u00031\u0001d\u0011\u00159\u0017\u00031\u0001d\u0003\u0019)hN]8mYR\u0019q)!\n\t\r\u0005\u001d\"\u00031\u0001H\u0003\u00111'o\\7)\u0007I\tY\u0003\u0005\u0003\u0002.\u0005MRBAA\u0018\u0015\r\t\t$I\u0001\u000bC:tw\u000e^1uS>t\u0017\u0002BA\u001b\u0003_\u0011q\u0001^1jYJ,7-\u0001\u0004eKR\f7\r\u001b\u000b\u0005\u0003w\tiDD\u0002I\u0003{Aa!a\u0010\u0014\u0001\u00049\u0015\u0001\u00028pI\u0016\f!\"\u001b8ji&\fG.\u001b>f)\u0015y\u0014QIA%\u0011\u0019\t9\u0005\u0006a\u0001\u000f\u0006!!o\\8u\u0011\u0019\tY\u0005\u0006a\u0001u\u0005!1/\u001b>f\u0003%\u0019X-\\5dY>tW\rF\u0005|\u0003#\n)&!\u0017\u0002^!1\u00111K\u000bA\u0002i\nA!\\1y\u0019\"1\u0011qK\u000bA\u0002\u001d\u000b1!\\=D\u0011\u0019\tY&\u0006a\u0001+\u0006\u00191\u000f^6\t\r\u0005}S\u00031\u0001;\u0003\tI\u00070A\bdQ\u0006\u0014\u0018m\u0019;fe&\u001cH/[2t\u00031)7\u000f^5nCR,7+\u001b>f+\t\t9\u0007E\u0002'\u0003SJ1!a\u001b\"\u0005\u0011auN\\4\u0002\u000f!\f7o\u0015;faV\u0011\u0011\u0011\u000f\t\u0004M\u0005M\u0014bAA;C\t9!i\\8mK\u0006t\u0017\u0001\u0003;ssN\u0003H.\u001b;\u0015\u0003]\u0004")
public abstract class BinaryTreeStepperBase<A, T, Sub, Semi extends Sub>
implements Stepper.EfficientSplit {
    private int maxLength;
    private T myCurrent;
    private Object[] stack;
    private int index;
    private final Function1<T, T> left;
    private final Function1<T, T> right;

    public int maxLength() {
        return this.maxLength;
    }

    public void maxLength_$eq(int x$1) {
        this.maxLength = x$1;
    }

    public T myCurrent() {
        return this.myCurrent;
    }

    public void myCurrent_$eq(T x$1) {
        this.myCurrent = x$1;
    }

    public Object[] stack() {
        return this.stack;
    }

    public void stack_$eq(Object[] x$1) {
        this.stack = x$1;
    }

    public int index() {
        return this.index;
    }

    public void index_$eq(int x$1) {
        this.index = x$1;
    }

    public Function1<T, T> left() {
        return this.left;
    }

    public Function1<T, T> right() {
        return this.right;
    }

    public final T unroll(T from) {
        T l;
        while ((l = this.left().apply(from)) != null) {
            if (this.index() + 1 >= this.stack().length) {
                this.stack_$eq(Arrays.copyOf(this.stack(), 4 + this.stack().length * 2));
            }
            this.index_$eq(this.index() + 1);
            this.stack()[this.index()] = from;
            from = l;
        }
        return from;
    }

    public final T detach(T node) {
        T r = this.right().apply(node);
        if (r != null) {
            T last = this.unroll(r);
            if (this.index() + 1 >= this.stack().length) {
                this.stack_$eq(Arrays.copyOf(this.stack(), 4 + this.stack().length * 2));
            }
            this.index_$eq(this.index() + 1);
            this.stack()[this.index()] = last;
        }
        return node;
    }

    public final void initialize(T root, int size) {
        if (root == null) {
            this.maxLength_$eq(0);
            this.myCurrent_$eq(null);
            this.stack_$eq(BinaryTreeStepper$.MODULE$.emptyStack());
            this.index_$eq(-1);
            return;
        }
        this.maxLength_$eq(size);
        this.index_$eq(-1);
        this.myCurrent_$eq(this.detach(this.unroll(root)));
    }

    public abstract Semi semiclone(int var1, T var2, Object[] var3, int var4);

    public int characteristics() {
        return 16;
    }

    public long estimateSize() {
        if (this.hasStep()) {
            return this.maxLength();
        }
        return 0L;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean hasStep() {
        if (this.myCurrent() != null) return true;
        if (this.maxLength() <= 0) return false;
        if (this.index() < 0) {
            this.maxLength_$eq(0);
            this.stack_$eq(BinaryTreeStepper$.MODULE$.emptyStack());
            return false;
        } else {
            Object ans = this.stack()[this.index()];
            this.index_$eq(this.index() - 1);
            this.myCurrent_$eq(this.detach(ans));
            return true;
        }
    }

    public Sub trySplit() {
        if (!this.hasStep() || this.index() < 0) {
            return null;
        }
        Object root = this.stack()[0];
        Object[] leftStack = this.index() > 0 ? Arrays.copyOfRange(this.stack(), 1, this.index() + 1) : BinaryTreeStepper$.MODULE$.emptyStack();
        int leftIndex = this.index() - 1;
        T leftCurrent = this.myCurrent();
        int leftMax = this.maxLength();
        this.index_$eq(-1);
        this.detach(root);
        this.myCurrent_$eq(root);
        this.maxLength_$eq(this.maxLength() - (2 + leftIndex));
        return (Sub)this.semiclone(leftMax -= 2 + this.index(), leftCurrent, leftStack, leftIndex);
    }

    public BinaryTreeStepperBase(int maxLength, T myCurrent, Object[] stack, int index, Function1<T, T> left, Function1<T, T> right) {
        this.maxLength = maxLength;
        this.myCurrent = myCurrent;
        this.stack = stack;
        this.index = index;
        this.left = left;
        this.right = right;
    }
}

