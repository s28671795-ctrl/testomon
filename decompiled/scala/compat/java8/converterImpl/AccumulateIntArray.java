/*
 * Decompiled with CFR 0.152.
 */
package scala.compat.java8.converterImpl;

import scala.compat.java8.converterImpl.AccumulateIntArray$;
import scala.jdk.IntAccumulator;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u00014A!\u0004\b\u0003/!aA\u0004\u0001C\u0001\u0002\u000b\u0015)\u0019!C\u0005;!IA\u0005\u0001B\u0003\u0002\u0003\u0006IA\b\u0005\u0006K\u0001!\tA\n\u0005\u0006W\u0001!\t\u0001\f\u0005\b\u0003\u0002\t\t\u0011\"\u0011C\u0011\u001d\u0019\u0005!!A\u0005B\u0011;q!\u0014\b\u0002\u0002#\u0005aJB\u0004\u000e\u001d\u0005\u0005\t\u0012A(\t\u000b\u0015BA\u0011A*\t\u000bQCAQA+\t\u000faC\u0011\u0011!C\u00033\"91\fCA\u0001\n\u000ba&AE!dGVlW\u000f\\1uK&sG/\u0011:sCfT!a\u0004\t\u0002\u001b\r|gN^3si\u0016\u0014\u0018*\u001c9m\u0015\t\t\"#A\u0003kCZ\f\u0007H\u0003\u0002\u0014)\u000511m\\7qCRT\u0011!F\u0001\u0006g\u000e\fG.Y\u0002\u0001'\t\u0001\u0001\u0004\u0005\u0002\u001a55\tA#\u0003\u0002\u001c)\t1\u0011I\\=WC2\fqh]2bY\u0006$3m\\7qCR$#.\u0019<bq\u0011\u001awN\u001c<feR,'/S7qY\u0012\n5mY;nk2\fG/Z%oi\u0006\u0013(/Y=%IUtG-\u001a:ms&tw-F\u0001\u001f!\rIr$I\u0005\u0003AQ\u0011Q!\u0011:sCf\u0004\"!\u0007\u0012\n\u0005\r\"\"aA%oi\u0006\u00015oY1mC\u0012\u001aw.\u001c9bi\u0012R\u0017M^19I\r|gN^3si\u0016\u0014\u0018*\u001c9mI\u0005\u001b7-^7vY\u0006$X-\u00138u\u0003J\u0014\u0018-\u001f\u0013%k:$WM\u001d7zS:<\u0007%\u0001\u0004=S:LGO\u0010\u000b\u0003O%\u0002\"\u0001\u000b\u0001\u000e\u00039AQAK\u0002A\u0002y\t!\"\u001e8eKJd\u00170\u001b8h\u0003)\t7mY;nk2\fG/Z\u000b\u0002[A\u0011aF\u0010\b\u0003_mr!\u0001M\u001d\u000f\u0005EBdB\u0001\u001a8\u001d\t\u0019d'D\u00015\u0015\t)d#\u0001\u0004=e>|GOP\u0005\u0002+%\u00111\u0003F\u0005\u0003#II!A\u000f\t\u0002\u001d\r|G\u000e\\3di&|g.S7qY&\u0011A(P\u0001\ba\u0006\u001c7.Y4f\u0015\tQ\u0004#\u0003\u0002@\u0001\nq\u0011J\u001c;BG\u000e,X.\u001e7bi>\u0014(B\u0001\u001f>\u0003!A\u0017m\u001d5D_\u0012,G#A\u0011\u0002\r\u0015\fX/\u00197t)\t)\u0005\n\u0005\u0002\u001a\r&\u0011q\t\u0006\u0002\b\u0005>|G.Z1o\u0011\u001dIe!!AA\u0002)\u000b1\u0001\u001f\u00132!\tI2*\u0003\u0002M)\t\u0019\u0011I\\=\u0002%\u0005\u001b7-^7vY\u0006$X-\u00138u\u0003J\u0014\u0018-\u001f\t\u0003Q!\u0019\"\u0001\u0003)\u0011\u0005e\t\u0016B\u0001*\u0015\u0005\u0019\te.\u001f*fMR\ta*\u0001\u000bbG\u000e,X.\u001e7bi\u0016$S\r\u001f;f]NLwN\u001c\u000b\u0003[YCQa\u0016\u0006A\u0002\u001d\nQ\u0001\n;iSN\f!\u0003[1tQ\u000e{G-\u001a\u0013fqR,gn]5p]R\u0011!I\u0017\u0005\u0006/.\u0001\raJ\u0001\u0011KF,\u0018\r\\:%Kb$XM\\:j_:$\"!X0\u0015\u0005\u0015s\u0006bB%\r\u0003\u0003\u0005\rA\u0013\u0005\u0006/2\u0001\ra\n")
public final class AccumulateIntArray {
    private final int[] scala$compat$java8$converterImpl$AccumulateIntArray$$underlying;

    public static boolean equals$extension(int[] nArray, Object object) {
        return AccumulateIntArray$.MODULE$.equals$extension(nArray, object);
    }

    public static int hashCode$extension(int[] nArray) {
        return AccumulateIntArray$.MODULE$.hashCode$extension(nArray);
    }

    public static IntAccumulator accumulate$extension(int[] nArray) {
        return AccumulateIntArray$.MODULE$.accumulate$extension(nArray);
    }

    public int[] scala$compat$java8$converterImpl$AccumulateIntArray$$underlying() {
        return this.scala$compat$java8$converterImpl$AccumulateIntArray$$underlying;
    }

    public IntAccumulator accumulate() {
        return AccumulateIntArray$.MODULE$.accumulate$extension(this.scala$compat$java8$converterImpl$AccumulateIntArray$$underlying());
    }

    public int hashCode() {
        return AccumulateIntArray$.MODULE$.hashCode$extension(this.scala$compat$java8$converterImpl$AccumulateIntArray$$underlying());
    }

    public boolean equals(Object x$1) {
        return AccumulateIntArray$.MODULE$.equals$extension(this.scala$compat$java8$converterImpl$AccumulateIntArray$$underlying(), x$1);
    }

    public AccumulateIntArray(int[] underlying) {
        this.scala$compat$java8$converterImpl$AccumulateIntArray$$underlying = underlying;
    }
}

