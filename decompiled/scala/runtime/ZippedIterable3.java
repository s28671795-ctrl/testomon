/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime;

import scala.Tuple3;
import scala.collection.AbstractIterable;
import scala.collection.Iterable;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005]3qa\u0002\u0005\u0011\u0002G\u0005Q\u0002C\u0003\u0014\u0001\u0019\u0005A\u0003C\u0003.\u0001\u0019\u0005afB\u0003=\u0011!\u0005QHB\u0003\b\u0011!\u0005q\bC\u0003D\t\u0011\u0005A\tC\u0003F\t\u0011\raIA\b[SB\u0004X\rZ%uKJ\f'\r\\34\u0015\tI!\"A\u0004sk:$\u0018.\\3\u000b\u0003-\tQa]2bY\u0006\u001c\u0001!\u0006\u0003\u000fC!Z3C\u0001\u0001\u0010!\t\u0001\u0012#D\u0001\u000b\u0013\t\u0011\"BA\u0002B]f\f\u0001\"\u001b;fe\u0006$xN]\u000b\u0002+A\u0019a#\u0007\u000f\u000f\u0005A9\u0012B\u0001\r\u000b\u0003\u001d\u0001\u0018mY6bO\u0016L!AG\u000e\u0003\u0011%#XM]1u_JT!\u0001\u0007\u0006\u0011\u000bAird\n\u0016\n\u0005yQ!A\u0002+va2,7\u0007\u0005\u0002!C1\u0001AA\u0002\u0012\u0001\t\u000b\u00071EA\u0002FYF\n\"\u0001J\b\u0011\u0005A)\u0013B\u0001\u0014\u000b\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"\u0001\t\u0015\u0005\r%\u0002AQ1\u0001$\u0005\r)EN\r\t\u0003A-\"a\u0001\f\u0001\u0005\u0006\u0004\u0019#aA#mg\u00059\u0011n]#naRLX#A\u0018\u0011\u0005A\u0001\u0014BA\u0019\u000b\u0005\u001d\u0011un\u001c7fC:Dc\u0001A\u001a7oeR\u0004C\u0001\t5\u0013\t)$B\u0001\u0006eKB\u0014XmY1uK\u0012\fq!\\3tg\u0006<W-I\u00019\u0003y)6/\u001a\u0011tG\u0006d\u0017ML2pY2,7\r^5p]:b\u0015M_=[SB\u001cd&A\u0003tS:\u001cW-I\u0001<\u0003\u0019\u0011d&M\u001a/a\u0005y!,\u001b9qK\u0012LE/\u001a:bE2,7\u0007\u0005\u0002?\t5\t\u0001b\u0005\u0002\u0005\u0001B\u0011\u0001#Q\u0005\u0003\u0005*\u0011a!\u00118z%\u00164\u0017A\u0002\u001fj]&$h\bF\u0001>\u0003eQ\u0018\u000e\u001d9fI&#XM]1cY\u0016\u001cDk\\%uKJ\f'\r\\3\u0016\t\u001dku*\u0015\u000b\u0003\u0011J\u00032AF%L\u0013\tQ5D\u0001\u0005Ji\u0016\u0014\u0018M\u00197f!\u0015\u0001R\u0004\u0014(Q!\t\u0001S\nB\u0003#\r\t\u00071\u0005\u0005\u0002!\u001f\u0012)\u0011F\u0002b\u0001GA\u0011\u0001%\u0015\u0003\u0006Y\u0019\u0011\ra\t\u0005\u0006'\u001a\u0001\r\u0001V\u0001\u0003uj\u0004RA\u0010\u0001M\u001dBCc\u0001B\u001a7oeR\u0004FB\u00024m]J$\b")
public interface ZippedIterable3<El1, El2, El3> {
    public static <El1, El2, El3> Iterable<Tuple3<El1, El2, El3>> zippedIterable3ToIterable(ZippedIterable3<El1, El2, El3> zz) {
        return new AbstractIterable<Tuple3<El1, El2, El3>>(zz){
            private final ZippedIterable3 zz$1;

            public Iterator<Tuple3<El1, El2, El3>> iterator() {
                return this.zz$1.iterator();
            }

            public boolean isEmpty() {
                return this.zz$1.isEmpty();
            }
            {
                this.zz$1 = zz$1;
            }
        };
    }

    public Iterator<Tuple3<El1, El2, El3>> iterator();

    public boolean isEmpty();
}

