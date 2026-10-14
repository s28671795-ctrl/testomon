/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.StreamTimeoutException;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u00012Aa\u0001\u0003\u0003\u0013!Aa\u0002\u0001B\u0001B\u0003%q\u0002C\u0003\u001d\u0001\u0011\u0005QD\u0001\u000eTiJ,\u0017-\\%eY\u0016$\u0016.\\3pkR,\u0005pY3qi&|gN\u0003\u0002\u0006\r\u000511\u000f\u001e:fC6T\u0011aB\u0001\u0005C.\\\u0017m\u0001\u0001\u0014\u0005\u0001Q\u0001CA\u0006\r\u001b\u0005!\u0011BA\u0007\u0005\u0005Y\u0019FO]3b[RKW.Z8vi\u0016C8-\u001a9uS>t\u0017aA7tOB\u0011\u0001#\u0007\b\u0003#]\u0001\"AE\u000b\u000e\u0003MQ!\u0001\u0006\u0005\u0002\rq\u0012xn\u001c;?\u0015\u00051\u0012!B:dC2\f\u0017B\u0001\r\u0016\u0003\u0019\u0001&/\u001a3fM&\u0011!d\u0007\u0002\u0007'R\u0014\u0018N\\4\u000b\u0005a)\u0012A\u0002\u001fj]&$h\b\u0006\u0002\u001f?A\u00111\u0002\u0001\u0005\u0006\u001d\t\u0001\ra\u0004")
public final class StreamIdleTimeoutException
extends StreamTimeoutException {
    public StreamIdleTimeoutException(String msg) {
        super(msg);
    }
}

