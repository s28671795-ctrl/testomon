/*
 * Decompiled with CFR 0.152.
 */
package akka.io;

import akka.annotation.InternalApi;
import akka.io.Dns;
import akka.io.DnsProvider;
import akka.io.InetAddressDnsResolver;
import akka.io.SimpleDnsCache;
import akka.io.SimpleDnsManager;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005a2A!\u0002\u0004\u0001\u0017!)a\u0003\u0001C\u0001/!)\u0011\u0004\u0001C!5!)a\u0004\u0001C!?!)1\u0006\u0001C!Y\t1\u0012J\\3u\u0003\u0012$'/Z:t\t:\u001c\bK]8wS\u0012,'O\u0003\u0002\b\u0011\u0005\u0011\u0011n\u001c\u0006\u0002\u0013\u0005!\u0011m[6b\u0007\u0001\u00192\u0001\u0001\u0007\u0013!\ti\u0001#D\u0001\u000f\u0015\u0005y\u0011!B:dC2\f\u0017BA\t\u000f\u0005\u0019\te.\u001f*fMB\u00111\u0003F\u0007\u0002\r%\u0011QC\u0002\u0002\f\t:\u001c\bK]8wS\u0012,'/\u0001\u0004=S:LGO\u0010\u000b\u00021A\u00111\u0003A\u0001\u0006G\u0006\u001c\u0007.Z\u000b\u00027A\u00111\u0003H\u0005\u0003;\u0019\u00111\u0001\u00128t\u0003)\t7\r^8s\u00072\f7o]\u000b\u0002AA\u0019\u0011E\n\u0015\u000e\u0003\tR!a\t\u0013\u0002\t1\fgn\u001a\u0006\u0002K\u0005!!.\u0019<b\u0013\t9#EA\u0003DY\u0006\u001c8\u000f\u0005\u0002\u0014S%\u0011!F\u0002\u0002\u0017\u0013:,G/\u00113ee\u0016\u001c8\u000f\u00128t%\u0016\u001cx\u000e\u001c<fe\u0006aQ.\u00198bO\u0016\u00148\t\\1tgV\tQ\u0006E\u0002\"M9\u0002\"aE\u0018\n\u0005A2!\u0001E*j[BdW\r\u00128t\u001b\u0006t\u0017mZ3sQ\t\u0001!\u0007\u0005\u00024m5\tAG\u0003\u00026\u0011\u0005Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\u0005]\"$aC%oi\u0016\u0014h.\u00197Ba&\u0004")
@InternalApi
public class InetAddressDnsProvider
implements DnsProvider {
    @Override
    public Dns cache() {
        return new SimpleDnsCache();
    }

    public Class<InetAddressDnsResolver> actorClass() {
        return InetAddressDnsResolver.class;
    }

    public Class<SimpleDnsManager> managerClass() {
        return SimpleDnsManager.class;
    }

    public InetAddressDnsProvider() {
        DnsProvider.$init$(this);
    }
}

