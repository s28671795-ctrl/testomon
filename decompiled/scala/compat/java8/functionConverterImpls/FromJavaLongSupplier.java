/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.LongSupplier;
import scala.Function0;
import scala.Function0$mcJ$sp;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005-2A\u0001B\u0003\u0001\u001d!A\u0011\u0004\u0001B\u0001B\u0003%!\u0004C\u0003%\u0001\u0011\u0005Q\u0005C\u0003*\u0001\u0011\u0005!F\u0001\u000bGe>l'*\u0019<b\u0019>twmU;qa2LWM\u001d\u0006\u0003\r\u001d\taCZ;oGRLwN\\\"p]Z,'\u000f^3s\u00136\u0004Hn\u001d\u0006\u0003\u0011%\tQA[1wCbR!AC\u0006\u0002\r\r|W\u000e]1u\u0015\u0005a\u0011!B:dC2\f7\u0001A\n\u0004\u0001=\u0019\u0002C\u0001\t\u0012\u001b\u0005Y\u0011B\u0001\n\f\u0005\u0019\te.\u001f*fMB\u0019\u0001\u0003\u0006\f\n\u0005UY!!\u0003$v]\u000e$\u0018n\u001c81!\t\u0001r#\u0003\u0002\u0019\u0017\t!Aj\u001c8h\u0003\tQg\r\u0005\u0002\u001cE5\tAD\u0003\u0002\u001e=\u0005Aa-\u001e8di&|gN\u0003\u0002 A\u0005!Q\u000f^5m\u0015\u0005\t\u0013\u0001\u00026bm\u0006L!a\t\u000f\u0003\u00191{gnZ*vaBd\u0017.\u001a:\u0002\rqJg.\u001b;?)\t1\u0003\u0006\u0005\u0002(\u00015\tQ\u0001C\u0003\u001a\u0005\u0001\u0007!$A\u0003baBd\u0017\u0010F\u0001\u0017\u0001")
public class FromJavaLongSupplier
implements Function0$mcJ$sp {
    public final LongSupplier scala$compat$java8$functionConverterImpls$FromJavaLongSupplier$$jf;

    @Override
    public boolean apply$mcZ$sp() {
        return Function0.apply$mcZ$sp$(this);
    }

    @Override
    public byte apply$mcB$sp() {
        return Function0.apply$mcB$sp$(this);
    }

    @Override
    public char apply$mcC$sp() {
        return Function0.apply$mcC$sp$(this);
    }

    @Override
    public double apply$mcD$sp() {
        return Function0.apply$mcD$sp$(this);
    }

    @Override
    public float apply$mcF$sp() {
        return Function0.apply$mcF$sp$(this);
    }

    @Override
    public int apply$mcI$sp() {
        return Function0.apply$mcI$sp$(this);
    }

    @Override
    public short apply$mcS$sp() {
        return Function0.apply$mcS$sp$(this);
    }

    @Override
    public void apply$mcV$sp() {
        Function0.apply$mcV$sp$(this);
    }

    @Override
    public String toString() {
        return Function0.toString$(this);
    }

    @Override
    public long apply() {
        return this.apply$mcJ$sp();
    }

    @Override
    public long apply$mcJ$sp() {
        return this.scala$compat$java8$functionConverterImpls$FromJavaLongSupplier$$jf.getAsLong();
    }

    public FromJavaLongSupplier(LongSupplier jf) {
        this.scala$compat$java8$functionConverterImpls$FromJavaLongSupplier$$jf = jf;
        Function0.$init$(this);
    }
}

