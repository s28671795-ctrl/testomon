/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.BooleanSupplier;
import scala.Function0;
import scala.Function0$mcZ$sp;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005-2A\u0001B\u0003\u0001\u001d!A\u0011\u0004\u0001B\u0001B\u0003%!\u0004C\u0003%\u0001\u0011\u0005Q\u0005C\u0003*\u0001\u0011\u0005!FA\fGe>l'*\u0019<b\u0005>|G.Z1o'V\u0004\b\u000f\\5fe*\u0011aaB\u0001\u0017MVt7\r^5p]\u000e{gN^3si\u0016\u0014\u0018*\u001c9mg*\u0011\u0001\"C\u0001\u0006U\u00064\u0018\r\u000f\u0006\u0003\u0015-\taaY8na\u0006$(\"\u0001\u0007\u0002\u000bM\u001c\u0017\r\\1\u0004\u0001M\u0019\u0001aD\n\u0011\u0005A\tR\"A\u0006\n\u0005IY!AB!osJ+g\rE\u0002\u0011)YI!!F\u0006\u0003\u0013\u0019+hn\u0019;j_:\u0004\u0004C\u0001\t\u0018\u0013\tA2BA\u0004C_>dW-\u00198\u0002\u0005)4\u0007CA\u000e#\u001b\u0005a\"BA\u000f\u001f\u0003!1WO\\2uS>t'BA\u0010!\u0003\u0011)H/\u001b7\u000b\u0003\u0005\nAA[1wC&\u00111\u0005\b\u0002\u0010\u0005>|G.Z1o'V\u0004\b\u000f\\5fe\u00061A(\u001b8jiz\"\"A\n\u0015\u0011\u0005\u001d\u0002Q\"A\u0003\t\u000be\u0011\u0001\u0019\u0001\u000e\u0002\u000b\u0005\u0004\b\u000f\\=\u0015\u0003Y\u0001")
public class FromJavaBooleanSupplier
implements Function0$mcZ$sp {
    public final BooleanSupplier scala$compat$java8$functionConverterImpls$FromJavaBooleanSupplier$$jf;

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
    public boolean apply() {
        return this.apply$mcZ$sp();
    }

    @Override
    public boolean apply$mcZ$sp() {
        return this.scala$compat$java8$functionConverterImpls$FromJavaBooleanSupplier$$jf.getAsBoolean();
    }

    public FromJavaBooleanSupplier(BooleanSupplier jf) {
        this.scala$compat$java8$functionConverterImpls$FromJavaBooleanSupplier$$jf = jf;
        Function0.$init$(this);
    }
}

