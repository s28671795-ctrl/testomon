/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.mutable;

import scala.collection.mutable.Builder;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005)2qa\u0001\u0003\u0011\u0002G\u00051\u0002C\u0003$\u0001\u0019\u0005C\u0005C\u0003)\u0001\u0019\u0005\u0013FA\bSKV\u001c\u0018M\u00197f\u0005VLG\u000eZ3s\u0015\t)a!A\u0004nkR\f'\r\\3\u000b\u0005\u001dA\u0011AC2pY2,7\r^5p]*\t\u0011\"A\u0003tG\u0006d\u0017m\u0001\u0001\u0016\u000719\u0012eE\u0002\u0001\u001bE\u0001\"AD\b\u000e\u0003!I!\u0001\u0005\u0005\u0003\r\u0005s\u0017PU3g!\u0011\u00112#\u0006\u0011\u000e\u0003\u0011I!\u0001\u0006\u0003\u0003\u000f\t+\u0018\u000e\u001c3feB\u0011ac\u0006\u0007\u0001\t\u0019A\u0002\u0001#b\u00013\t!Q\t\\3n#\tQR\u0004\u0005\u0002\u000f7%\u0011A\u0004\u0003\u0002\b\u001d>$\b.\u001b8h!\tqa$\u0003\u0002 \u0011\t\u0019\u0011I\\=\u0011\u0005Y\tCA\u0002\u0012\u0001\t\u000b\u0007\u0011D\u0001\u0002U_\u0006)1\r\\3beR\tQ\u0005\u0005\u0002\u000fM%\u0011q\u0005\u0003\u0002\u0005+:LG/\u0001\u0004sKN,H\u000e\u001e\u000b\u0002A\u0001")
public interface ReusableBuilder<Elem, To>
extends Builder<Elem, To> {
    @Override
    public void clear();

    @Override
    public To result();
}

