/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.io;

import akka.annotation.InternalApi;
import akka.io.Tcp;
import akka.stream.impl.io.TcpConnectionStage$WriteDelayAck$;
import akka.util.ByteString$;

@InternalApi
public final class TcpConnectionStage$ {
    public static final TcpConnectionStage$ MODULE$ = new TcpConnectionStage$();
    private static final Tcp.Write akka$stream$impl$io$TcpConnectionStage$$WriteDelayMessage = new Tcp.Write(ByteString$.MODULE$.empty(), TcpConnectionStage$WriteDelayAck$.MODULE$);

    public Tcp.Write akka$stream$impl$io$TcpConnectionStage$$WriteDelayMessage() {
        return akka$stream$impl$io$TcpConnectionStage$$WriteDelayMessage;
    }

    private TcpConnectionStage$() {
    }
}

