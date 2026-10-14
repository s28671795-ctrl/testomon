/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.IntSupplier;
import scala.Function0;
import scala.Function0$mcI$sp;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005-2A\u0001B\u0003\u0001\u001d!A\u0011\u0004\u0001B\u0001B\u0003%!\u0004C\u0003%\u0001\u0011\u0005Q\u0005C\u0003*\u0001\u0011\u0005!FA\nGe>l'*\u0019<b\u0013:$8+\u001e9qY&,'O\u0003\u0002\u0007\u000f\u00051b-\u001e8di&|gnQ8om\u0016\u0014H/\u001a:J[Bd7O\u0003\u0002\t\u0013\u0005)!.\u0019<bq)\u0011!bC\u0001\u0007G>l\u0007/\u0019;\u000b\u00031\tQa]2bY\u0006\u001c\u0001aE\u0002\u0001\u001fM\u0001\"\u0001E\t\u000e\u0003-I!AE\u0006\u0003\r\u0005s\u0017PU3g!\r\u0001BCF\u0005\u0003+-\u0011\u0011BR;oGRLwN\u001c\u0019\u0011\u0005A9\u0012B\u0001\r\f\u0005\rIe\u000e^\u0001\u0003U\u001a\u0004\"a\u0007\u0012\u000e\u0003qQ!!\b\u0010\u0002\u0011\u0019,hn\u0019;j_:T!a\b\u0011\u0002\tU$\u0018\u000e\u001c\u0006\u0002C\u0005!!.\u0019<b\u0013\t\u0019CDA\u0006J]R\u001cV\u000f\u001d9mS\u0016\u0014\u0018A\u0002\u001fj]&$h\b\u0006\u0002'QA\u0011q\u0005A\u0007\u0002\u000b!)\u0011D\u0001a\u00015\u0005)\u0011\r\u001d9msR\ta\u0003")
public class FromJavaIntSupplier
implements Function0$mcI$sp {
    public final IntSupplier scala$compat$java8$functionConverterImpls$FromJavaIntSupplier$$jf;

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
    public long apply$mcJ$sp() {
        return Function0.apply$mcJ$sp$(this);
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
    public int apply() {
        return this.apply$mcI$sp();
    }

    @Override
    public int apply$mcI$sp() {
        return this.scala$compat$java8$functionConverterImpls$FromJavaIntSupplier$$jf.getAsInt();
    }

    public FromJavaIntSupplier(IntSupplier jf) {
        this.scala$compat$java8$functionConverterImpls$FromJavaIntSupplier$$jf = jf;
        Function0.$init$(this);
    }
}

