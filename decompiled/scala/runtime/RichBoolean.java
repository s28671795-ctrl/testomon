/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime;

import scala.Proxy;
import scala.math.Ordered;
import scala.math.Ordering$Boolean$;
import scala.reflect.ScalaSignature;
import scala.runtime.OrderedProxy;
import scala.runtime.RichBoolean$;

@ScalaSignature(bytes="\u0006\u0005]3A!\u0004\b\u0003'!Aq\u0004\u0001BC\u0002\u0013\u0005\u0001\u0005\u0003\u0005\"\u0001\t\u0005\t\u0015!\u0003\u001d\u0011\u0015\u0011\u0003\u0001\"\u0001$\u0011\u00151\u0003\u0001\"\u0005(\u0011\u001dA\u0004!!A\u0005BeBq!\u0010\u0001\u0002\u0002\u0013\u0005chB\u0004E\u001d\u0005\u0005\t\u0012A#\u0007\u000f5q\u0011\u0011!E\u0001\r\")!\u0005\u0003C\u0001\u0015\")1\n\u0003C\u0003\u0019\"9q\nCA\u0001\n\u000b\u0001\u0006b\u0002*\t\u0003\u0003%)a\u0015\u0002\f%&\u001c\u0007NQ8pY\u0016\fgN\u0003\u0002\u0010!\u00059!/\u001e8uS6,'\"A\t\u0002\u000bM\u001c\u0017\r\\1\u0004\u0001M\u0019\u0001\u0001\u0006\r\u0011\u0005U1R\"\u0001\t\n\u0005]\u0001\"AB!osZ\u000bG\u000eE\u0002\u001a5qi\u0011AD\u0005\u000379\u0011Ab\u0014:eKJ,G\r\u0015:pqf\u0004\"!F\u000f\n\u0005y\u0001\"a\u0002\"p_2,\u0017M\\\u0001\u0005g\u0016dg-F\u0001\u001d\u0003\u0015\u0019X\r\u001c4!\u0003\u0019a\u0014N\\5u}Q\u0011A%\n\t\u00033\u0001AQaH\u0002A\u0002q\t1a\u001c:e+\u0005A\u0003CA\u00156\u001d\tQ#G\u0004\u0002,a9\u0011AfL\u0007\u0002[)\u0011aFE\u0001\u0007yI|w\u000e\u001e \n\u0003EI!!\r\t\u0002\t5\fG\u000f[\u0005\u0003gQ\n\u0001b\u0014:eKJLgn\u001a\u0006\u0003cAQ!AN\u001c\u0002\u000f\t{w\u000e\\3b]*\u00111\u0007N\u0001\tQ\u0006\u001c\bnQ8eKR\t!\b\u0005\u0002\u0016w%\u0011A\b\u0005\u0002\u0004\u0013:$\u0018AB3rk\u0006d7\u000f\u0006\u0002\u001d\u007f!9\u0001IBA\u0001\u0002\u0004\t\u0015a\u0001=%cA\u0011QCQ\u0005\u0003\u0007B\u00111!\u00118z\u0003-\u0011\u0016n\u00195C_>dW-\u00198\u0011\u0005eA1C\u0001\u0005H!\t)\u0002*\u0003\u0002J!\t1\u0011I\\=SK\u001a$\u0012!R\u0001\u000e_J$G%\u001a=uK:\u001c\u0018n\u001c8\u0015\u0005!j\u0005\"\u0002(\u000b\u0001\u0004!\u0013!\u0002\u0013uQ&\u001c\u0018A\u00055bg\"\u001cu\u000eZ3%Kb$XM\\:j_:$\"!O)\t\u000b9[\u0001\u0019\u0001\u0013\u0002!\u0015\fX/\u00197tI\u0015DH/\u001a8tS>tGC\u0001+W)\taR\u000bC\u0004A\u0019\u0005\u0005\t\u0019A!\t\u000b9c\u0001\u0019\u0001\u0013")
public final class RichBoolean
implements OrderedProxy<Object> {
    private final boolean self;

    public static boolean equals$extension(boolean $this, Object x$1) {
        return RichBoolean$.MODULE$.equals$extension($this, x$1);
    }

    public static int hashCode$extension(boolean $this) {
        return Boolean.hashCode($this);
    }

    public static Ordering$Boolean$ ord$extension(boolean $this) {
        return Ordering$Boolean$.MODULE$;
    }

    @Override
    public int compare(Object y) {
        return OrderedProxy.compare$(this, y);
    }

    @Override
    public String toString() {
        return Proxy.toString$(this);
    }

    @Override
    public boolean $less(Object that) {
        return Ordered.$less$(this, that);
    }

    @Override
    public boolean $greater(Object that) {
        return Ordered.$greater$(this, that);
    }

    @Override
    public boolean $less$eq(Object that) {
        return Ordered.$less$eq$(this, that);
    }

    @Override
    public boolean $greater$eq(Object that) {
        return Ordered.$greater$eq$(this, that);
    }

    @Override
    public int compareTo(Object that) {
        return Ordered.compareTo$(this, that);
    }

    @Override
    public boolean self() {
        return this.self;
    }

    public Ordering$Boolean$ ord() {
        this.self();
        return Ordering$Boolean$.MODULE$;
    }

    @Override
    public int hashCode() {
        return Boolean.hashCode(this.self());
    }

    @Override
    public boolean equals(Object x$1) {
        return RichBoolean$.MODULE$.equals$extension(this.self(), x$1);
    }

    public RichBoolean(boolean self) {
        this.self = self;
    }
}

