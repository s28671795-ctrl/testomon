/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.converterImpl;

import scala.compat.java8.converterImpl.AccumulateDoubleArray$;
import scala.jdk.DoubleAccumulator;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\r4A!\u0004\b\u0003/!aA\u0004\u0001C\u0001\u0002\u000b\u0015)\u0019!C\u0005;!IA\u0005\u0001B\u0003\u0002\u0003\u0006IA\b\u0005\u0006K\u0001!\tA\n\u0005\u0006W\u0001!\t\u0001\f\u0005\b\u0003\u0002\t\t\u0011\"\u0011C\u0011\u001d1\u0005!!A\u0005B\u001d;q\u0001\u0015\b\u0002\u0002#\u0005\u0011KB\u0004\u000e\u001d\u0005\u0005\t\u0012\u0001*\t\u000b\u0015BA\u0011\u0001,\t\u000b]CAQ\u0001-\t\u000fmC\u0011\u0011!C\u00039\"9a\fCA\u0001\n\u000by&!F!dGVlW\u000f\\1uK\u0012{WO\u00197f\u0003J\u0014\u0018-\u001f\u0006\u0003\u001fA\tQbY8om\u0016\u0014H/\u001a:J[Bd'BA\t\u0013\u0003\u0015Q\u0017M^19\u0015\t\u0019B#\u0001\u0004d_6\u0004\u0018\r\u001e\u0006\u0002+\u0005)1oY1mC\u000e\u00011C\u0001\u0001\u0019!\tI\"$D\u0001\u0015\u0013\tYBC\u0001\u0004B]f4\u0016\r\\\u0001Cg\u000e\fG.\u0019\u0013d_6\u0004\u0018\r\u001e\u0013kCZ\f\u0007\bJ2p]Z,'\u000f^3s\u00136\u0004H\u000eJ!dGVlW\u000f\\1uK\u0012{WO\u00197f\u0003J\u0014\u0018-\u001f\u0013%k:$WM\u001d7zS:<W#\u0001\u0010\u0011\u0007ey\u0012%\u0003\u0002!)\t)\u0011I\u001d:bsB\u0011\u0011DI\u0005\u0003GQ\u0011a\u0001R8vE2,\u0017aQ:dC2\fGeY8na\u0006$HE[1wCb\"3m\u001c8wKJ$XM]%na2$\u0013iY2v[Vd\u0017\r^3E_V\u0014G.Z!se\u0006LH\u0005J;oI\u0016\u0014H._5oO\u0002\na\u0001P5oSRtDCA\u0014*!\tA\u0003!D\u0001\u000f\u0011\u0015Q3\u00011\u0001\u001f\u0003))h\u000eZ3sYfLgnZ\u0001\u000bC\u000e\u001cW/\\;mCR,W#A\u0017\u0011\u00059rdBA\u0018<\u001d\t\u0001\u0014H\u0004\u00022q9\u0011!g\u000e\b\u0003gYj\u0011\u0001\u000e\u0006\u0003kY\ta\u0001\u0010:p_Rt\u0014\"A\u000b\n\u0005M!\u0012BA\t\u0013\u0013\tQ\u0004#\u0001\bd_2dWm\u0019;j_:LU\u000e\u001d7\n\u0005qj\u0014a\u00029bG.\fw-\u001a\u0006\u0003uAI!a\u0010!\u0003#\u0011{WO\u00197f\u0003\u000e\u001cW/\\;mCR|'O\u0003\u0002={\u0005A\u0001.Y:i\u0007>$W\rF\u0001D!\tIB)\u0003\u0002F)\t\u0019\u0011J\u001c;\u0002\r\u0015\fX/\u00197t)\tA5\n\u0005\u0002\u001a\u0013&\u0011!\n\u0006\u0002\b\u0005>|G.Z1o\u0011\u001dae!!AA\u00025\u000b1\u0001\u001f\u00132!\tIb*\u0003\u0002P)\t\u0019\u0011I\\=\u0002+\u0005\u001b7-^7vY\u0006$X\rR8vE2,\u0017I\u001d:bsB\u0011\u0001\u0006C\n\u0003\u0011M\u0003\"!\u0007+\n\u0005U#\"AB!osJ+g\rF\u0001R\u0003Q\t7mY;nk2\fG/\u001a\u0013fqR,gn]5p]R\u0011Q&\u0017\u0005\u00065*\u0001\raJ\u0001\u0006IQD\u0017n]\u0001\u0013Q\u0006\u001c\bnQ8eK\u0012*\u0007\u0010^3og&|g\u000e\u0006\u0002C;\")!l\u0003a\u0001O\u0005\u0001R-];bYN$S\r\u001f;f]NLwN\u001c\u000b\u0003A\n$\"\u0001S1\t\u000f1c\u0011\u0011!a\u0001\u001b\")!\f\u0004a\u0001O\u0001")
public final class AccumulateDoubleArray {
    private final double[] scala$compat$java8$converterImpl$AccumulateDoubleArray$$underlying;

    public static boolean equals$extension(double[] dArray, Object object) {
        return AccumulateDoubleArray$.MODULE$.equals$extension(dArray, object);
    }

    public static int hashCode$extension(double[] dArray) {
        return AccumulateDoubleArray$.MODULE$.hashCode$extension(dArray);
    }

    public static DoubleAccumulator accumulate$extension(double[] dArray) {
        return AccumulateDoubleArray$.MODULE$.accumulate$extension(dArray);
    }

    public double[] scala$compat$java8$converterImpl$AccumulateDoubleArray$$underlying() {
        return this.scala$compat$java8$converterImpl$AccumulateDoubleArray$$underlying;
    }

    public DoubleAccumulator accumulate() {
        return AccumulateDoubleArray$.MODULE$.accumulate$extension(this.scala$compat$java8$converterImpl$AccumulateDoubleArray$$underlying());
    }

    public int hashCode() {
        return AccumulateDoubleArray$.MODULE$.hashCode$extension(this.scala$compat$java8$converterImpl$AccumulateDoubleArray$$underlying());
    }

    public boolean equals(Object x$1) {
        return AccumulateDoubleArray$.MODULE$.equals$extension(this.scala$compat$java8$converterImpl$AccumulateDoubleArray$$underlying(), x$1);
    }

    public AccumulateDoubleArray(double[] underlying) {
        this.scala$compat$java8$converterImpl$AccumulateDoubleArray$$underlying = underlying;
    }
}

