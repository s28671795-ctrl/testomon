/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.stream.Attributes;
import akka.stream.Attributes$;
import akka.stream.scaladsl.TcpAttributes;

public final class TcpAttributes$ {
    public static final TcpAttributes$ MODULE$ = new TcpAttributes$();

    public Attributes tcpWriteBufferSize(int size) {
        return Attributes$.MODULE$.apply(new TcpAttributes.TcpWriteBufferSize(size));
    }

    private TcpAttributes$() {
    }
}

