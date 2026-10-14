/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.DoubleSupplier;
import scala.Function0;
import scala.Function0$mcD$sp;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005-2A\u0001B\u0003\u0001\u001d!A\u0011\u0004\u0001B\u0001B\u0003%!\u0004C\u0003%\u0001\u0011\u0005Q\u0005C\u0003*\u0001\u0011\u0005!F\u0001\fGe>l'*\u0019<b\t>,(\r\\3TkB\u0004H.[3s\u0015\t1q!\u0001\fgk:\u001cG/[8o\u0007>tg/\u001a:uKJLU\u000e\u001d7t\u0015\tA\u0011\"A\u0003kCZ\f\u0007H\u0003\u0002\u000b\u0017\u000511m\\7qCRT\u0011\u0001D\u0001\u0006g\u000e\fG.Y\u0002\u0001'\r\u0001qb\u0005\t\u0003!Ei\u0011aC\u0005\u0003%-\u0011a!\u00118z%\u00164\u0007c\u0001\t\u0015-%\u0011Qc\u0003\u0002\n\rVt7\r^5p]B\u0002\"\u0001E\f\n\u0005aY!A\u0002#pk\ndW-\u0001\u0002kMB\u00111DI\u0007\u00029)\u0011QDH\u0001\tMVt7\r^5p]*\u0011q\u0004I\u0001\u0005kRLGNC\u0001\"\u0003\u0011Q\u0017M^1\n\u0005\rb\"A\u0004#pk\ndWmU;qa2LWM]\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0005\u0019B\u0003CA\u0014\u0001\u001b\u0005)\u0001\"B\r\u0003\u0001\u0004Q\u0012!B1qa2LH#\u0001\f")
public class FromJavaDoubleSupplier
implements Function0$mcD$sp {
    public final DoubleSupplier scala$compat$java8$functionConverterImpls$FromJavaDoubleSupplier$$jf;

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
    public float apply$mcF$sp() {
        return Function0.apply$mcF$sp$(this);
    }

    @Override
    public int apply$mcI$sp() {
        return Function0.apply$mcI$sp$(this);
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
    public double apply() {
        return this.apply$mcD$sp();
    }

    @Override
    public double apply$mcD$sp() {
        return this.scala$compat$java8$functionConverterImpls$FromJavaDoubleSupplier$$jf.getAsDouble();
    }

    public FromJavaDoubleSupplier(DoubleSupplier jf) {
        this.scala$compat$java8$functionConverterImpls$FromJavaDoubleSupplier$$jf = jf;
        Function0.$init$(this);
    }
}

