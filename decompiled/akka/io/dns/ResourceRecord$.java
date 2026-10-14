/*
 * Decompiled with CFR 0.152.
 */
package akka.io.dns;

import akka.annotation.InternalApi;
import akka.io.dns.AAAARecord$;
import akka.io.dns.ARecord$;
import akka.io.dns.CNameRecord$;
import akka.io.dns.CachePolicy;
import akka.io.dns.CachePolicy$Ttl$;
import akka.io.dns.ResourceRecord;
import akka.io.dns.SRVRecord$;
import akka.io.dns.UnknownRecord$;
import akka.io.dns.internal.DomainName$;
import akka.io.dns.internal.package$;
import akka.util.ByteIterator;
import akka.util.ByteString;
import scala.concurrent.duration.package;

@InternalApi
public final class ResourceRecord$ {
    public static final ResourceRecord$ MODULE$ = new ResourceRecord$();

    @InternalApi
    public ResourceRecord parse(ByteIterator it, ByteString msg) {
        CachePolicy.Ttl ttl;
        String name = DomainName$.MODULE$.parse(it, msg);
        short recType = it.getShort(package$.MODULE$.networkByteOrder());
        short recClass = it.getShort(package$.MODULE$.networkByteOrder());
        int n = it.getInt(package$.MODULE$.networkByteOrder());
        switch (n) {
            case 0: {
                ttl = CachePolicy$Ttl$.MODULE$.never();
                break;
            }
            default: {
                ttl = CachePolicy$Ttl$.MODULE$.fromPositive(new package.DurationInt(scala.concurrent.duration.package$.MODULE$.DurationInt(n)).seconds());
                break;
            }
        }
        CachePolicy.Ttl ttl2 = ttl;
        short rdLength = it.getShort(package$.MODULE$.networkByteOrder());
        ByteIterator data = it.clone().take(rdLength);
        it.drop(rdLength);
        short s2 = recType;
        switch (s2) {
            case 1: {
                return ARecord$.MODULE$.parseBody(name, ttl2, rdLength, data);
            }
            case 5: {
                return CNameRecord$.MODULE$.parseBody(name, ttl2, rdLength, data, msg);
            }
            case 28: {
                return AAAARecord$.MODULE$.parseBody(name, ttl2, rdLength, data);
            }
            case 33: {
                return SRVRecord$.MODULE$.parseBody(name, ttl2, rdLength, data, msg);
            }
        }
        return UnknownRecord$.MODULE$.parseBody(name, ttl2, recType, recClass, rdLength, data);
    }

    private ResourceRecord$() {
    }
}

