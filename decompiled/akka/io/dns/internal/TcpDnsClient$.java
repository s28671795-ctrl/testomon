/*
 * Decompiled with CFR 0.152.
 */
package akka.io.dns.internal;

import akka.AkkaException;
import akka.util.ByteString;
import akka.util.ByteString$;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.collection.immutable.Seq;
import scala.runtime.ScalaRunTime$;

public final class TcpDnsClient$ {
    public static final TcpDnsClient$ MODULE$ = new TcpDnsClient$();

    public ByteString encodeLength(int length) {
        return ByteString$.MODULE$.apply((Seq<Object>)ScalaRunTime$.MODULE$.wrapByteArray(new byte[]{(byte)(length / 256), (byte)length}));
    }

    public int decodeLength(ByteString data) {
        return (data.apply(0) + 256) % 256 * 256 + (data.apply(1) + 256) % 256;
    }

    public void throwFailure(String message, Option<Throwable> cause) {
        Option<Throwable> option = cause;
        if (None$.MODULE$.equals(option)) {
            throw new AkkaException(message);
        }
        if (option instanceof Some) {
            Some some = (Some)option;
            Throwable throwable = (Throwable)some.value();
            throw new AkkaException(message, throwable);
        }
        throw new MatchError(option);
    }

    private TcpDnsClient$() {
    }
}

