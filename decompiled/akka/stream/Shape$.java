/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.annotation.InternalApi;
import scala.collection.immutable.List;
import scala.collection.immutable.Seq;

@InternalApi
public final class Shape$ {
    public static final Shape$ MODULE$ = new Shape$();

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean hasOnePort(Seq<?> ports) {
        if (!ports.nonEmpty()) return false;
        Seq<?> seq = ports;
        if (!(seq instanceof List)) {
            if (ports.size() != 1) return false;
            return true;
        }
        List list = (List)seq;
        boolean bl = ((List)list.tail()).isEmpty();
        if (!bl) return false;
        return true;
    }

    private Shape$() {
    }
}

