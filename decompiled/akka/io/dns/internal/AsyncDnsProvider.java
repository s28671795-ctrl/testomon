/*
 * Decompiled with CFR 0.152.
 */
package akka.io.dns.internal;

import akka.annotation.InternalApi;
import akka.io.Dns;
import akka.io.DnsProvider;
import akka.io.SimpleDnsCache;
import akka.io.dns.internal.AsyncDnsManager;
import akka.io.dns.internal.AsyncDnsResolver;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005u2Q!\u0002\u0004\u0001\u00199AQ!\u0007\u0001\u0005\u0002mAQA\b\u0001\u0005B}AQa\t\u0001\u0005B\u0011BQ\u0001\r\u0001\u0005BE\u0012\u0001#Q:z]\u000e$en\u001d)s_ZLG-\u001a:\u000b\u0005\u001dA\u0011\u0001C5oi\u0016\u0014h.\u00197\u000b\u0005%Q\u0011a\u00013og*\u00111\u0002D\u0001\u0003S>T\u0011!D\u0001\u0005C.\\\u0017mE\u0002\u0001\u001fU\u0001\"\u0001E\n\u000e\u0003EQ\u0011AE\u0001\u0006g\u000e\fG.Y\u0005\u0003)E\u0011a!\u00118z%\u00164\u0007C\u0001\f\u0018\u001b\u0005Q\u0011B\u0001\r\u000b\u0005-!en\u001d)s_ZLG-\u001a:\u0002\rqJg.\u001b;?\u0007\u0001!\u0012\u0001\b\t\u0003;\u0001i\u0011AB\u0001\u0006G\u0006\u001c\u0007.Z\u000b\u0002AA\u0011a#I\u0005\u0003E)\u00111\u0001\u00128t\u0003)\t7\r^8s\u00072\f7o]\u000b\u0002KA\u0019aeK\u0017\u000e\u0003\u001dR!\u0001K\u0015\u0002\t1\fgn\u001a\u0006\u0002U\u0005!!.\u0019<b\u0013\tasEA\u0003DY\u0006\u001c8\u000f\u0005\u0002\u001e]%\u0011qF\u0002\u0002\u0011\u0003NLhn\u0019#ogJ+7o\u001c7wKJ\fA\"\\1oC\u001e,'o\u00117bgN,\u0012A\r\t\u0004M-\u001a\u0004CA\u000f5\u0013\t)dAA\bBgft7\r\u00128t\u001b\u0006t\u0017mZ3sQ\t\u0001q\u0007\u0005\u00029w5\t\u0011H\u0003\u0002;\u0019\u0005Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\u0005qJ$aC%oi\u0016\u0014h.\u00197Ba&\u0004")
@InternalApi
public class AsyncDnsProvider
implements DnsProvider {
    @Override
    public Dns cache() {
        return new SimpleDnsCache();
    }

    public Class<AsyncDnsResolver> actorClass() {
        return AsyncDnsResolver.class;
    }

    public Class<AsyncDnsManager> managerClass() {
        return AsyncDnsManager.class;
    }

    public AsyncDnsProvider() {
        DnsProvider.$init$(this);
    }
}

