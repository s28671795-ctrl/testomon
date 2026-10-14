/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.functionConverterImpls;

import java.util.function.DoubleBinaryOperator;
import scala.Function2;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005I2A\u0001B\u0003\u0001\u001d!Aq\u0004\u0001B\u0001B\u0003%\u0001\u0005C\u0003(\u0001\u0011\u0005\u0001\u0006C\u0003-\u0001\u0011\u0005QF\u0001\u000eBg*\u000bg/\u0019#pk\ndWMQ5oCJLx\n]3sCR|'O\u0003\u0002\u0007\u000f\u00051b-\u001e8di&|gnQ8om\u0016\u0014H/\u001a:J[Bd7O\u0003\u0002\t\u0013\u0005)!.\u0019<bq)\u0011!bC\u0001\u0007G>l\u0007/\u0019;\u000b\u00031\tQa]2bY\u0006\u001c\u0001aE\u0002\u0001\u001f]\u0001\"\u0001E\u000b\u000e\u0003EQ!AE\n\u0002\t1\fgn\u001a\u0006\u0002)\u0005!!.\u0019<b\u0013\t1\u0012C\u0001\u0004PE*,7\r\u001e\t\u00031ui\u0011!\u0007\u0006\u00035m\t\u0001BZ;oGRLwN\u001c\u0006\u00039M\tA!\u001e;jY&\u0011a$\u0007\u0002\u0015\t>,(\r\\3CS:\f'/_(qKJ\fGo\u001c:\u0002\u0005M4\u0007#B\u0011#I\u0011\"S\"A\u0006\n\u0005\rZ!!\u0003$v]\u000e$\u0018n\u001c83!\t\tS%\u0003\u0002'\u0017\t1Ai\\;cY\u0016\fa\u0001P5oSRtDCA\u0015,!\tQ\u0003!D\u0001\u0006\u0011\u0015y\"\u00011\u0001!\u00035\t\u0007\u000f\u001d7z\u0003N$u.\u001e2mKR\u0019AE\f\u0019\t\u000b=\u001a\u0001\u0019\u0001\u0013\u0002\u0005a\f\u0004\"B\u0019\u0004\u0001\u0004!\u0013A\u0001=3\u0001")
public class AsJavaDoubleBinaryOperator
implements DoubleBinaryOperator {
    private final Function2<Object, Object, Object> sf;

    @Override
    public double applyAsDouble(double x1, double x2) {
        return this.sf.apply$mcDDD$sp(x1, x2);
    }

    public AsJavaDoubleBinaryOperator(Function2<Object, Object, Object> sf) {
        this.sf = sf;
    }
}

