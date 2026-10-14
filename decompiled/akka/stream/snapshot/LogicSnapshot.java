/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.snapshot;

import akka.annotation.ApiMayChange;
import akka.annotation.DoNotInherit;
import akka.stream.Attributes;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005M2qa\u0001\u0003\u0011\u0002G\u00052\u0002C\u0003\u0013\u0001\u0019\u00051\u0003C\u0003 \u0001\u0019\u0005\u0001EA\u0007M_\u001eL7m\u00158baNDw\u000e\u001e\u0006\u0003\u000b\u0019\t\u0001b\u001d8baNDw\u000e\u001e\u0006\u0003\u000f!\taa\u001d;sK\u0006l'\"A\u0005\u0002\t\u0005\\7.Y\u0002\u0001'\t\u0001A\u0002\u0005\u0002\u000e!5\taBC\u0001\u0010\u0003\u0015\u00198-\u00197b\u0013\t\tbB\u0001\u0004B]f\u0014VMZ\u0001\u0006Y\u0006\u0014W\r\\\u000b\u0002)A\u0011Q\u0003\b\b\u0003-i\u0001\"a\u0006\b\u000e\u0003aQ!!\u0007\u0006\u0002\rq\u0012xn\u001c;?\u0013\tYb\"\u0001\u0004Qe\u0016$WMZ\u0005\u0003;y\u0011aa\u0015;sS:<'BA\u000e\u000f\u0003)\tG\u000f\u001e:jEV$Xm]\u000b\u0002CA\u0011!eI\u0007\u0002\r%\u0011AE\u0002\u0002\u000b\u0003R$(/\u001b2vi\u0016\u001c\u0018F\u0001\u0001'\u0013\t9CAA\tM_\u001eL7m\u00158baNDw\u000e^%na2D#\u0001A\u0015\u0011\u0005)jS\"A\u0016\u000b\u00051B\u0011AC1o]>$\u0018\r^5p]&\u0011af\u000b\u0002\r\u0003BLW*Y=DQ\u0006tw-\u001a\u0015\u0003\u0001A\u0002\"AK\u0019\n\u0005IZ#\u0001\u0004#p\u001d>$\u0018J\u001c5fe&$\b")
@DoNotInherit
@ApiMayChange
public interface LogicSnapshot {
    public String label();

    public Attributes attributes();
}

