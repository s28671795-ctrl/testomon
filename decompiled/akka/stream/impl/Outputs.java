/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import akka.stream.impl.SubReceive;
import akka.stream.impl.TransferState;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005e3\u0001\"\u0004\b\u0011\u0002\u0007\u0005!\u0003\u0006\u0005\u00067\u0001!\t!\b\u0005\u0006C\u00011\tA\t\u0005\u0006O\u00011\tA\t\u0005\u0006Q\u00011\t!\u000b\u0005\u0006[\u00011\tA\f\u0005\u0006i\u00011\t!\u000e\u0005\u0006s\u0001!\tA\u000f\u0005\u0006}\u00011\t!\b\u0005\u0006\u007f\u00011\t!\b\u0005\u0006\u0001\u00021\t!\u0011\u0005\u0006!\u00021\t!\u000b\u0005\u0006#\u0002!\t!\u000b\u0002\b\u001fV$\b/\u001e;t\u0015\ty\u0001#\u0001\u0003j[Bd'BA\t\u0013\u0003\u0019\u0019HO]3b[*\t1#\u0001\u0003bW.\f7C\u0001\u0001\u0016!\t1\u0012$D\u0001\u0018\u0015\u0005A\u0012!B:dC2\f\u0017B\u0001\u000e\u0018\u0005\u0019\te.\u001f*fM\u00061A%\u001b8ji\u0012\u001a\u0001\u0001F\u0001\u001f!\t1r$\u0003\u0002!/\t!QK\\5u\u0003-qU-\u001a3t\t\u0016l\u0017M\u001c3\u0016\u0003\r\u0002\"\u0001J\u0013\u000e\u00039I!A\n\b\u0003\u001bQ\u0013\u0018M\\:gKJ\u001cF/\u0019;f\u0003MqU-\u001a3t\t\u0016l\u0017M\u001c3Pe\u000e\u000bgnY3m\u0003=!W-\\1oI\u00063\u0018-\u001b7bE2,W#\u0001\u0016\u0011\u0005YY\u0013B\u0001\u0017\u0018\u0005\u001d\u0011un\u001c7fC:\fA#\u001a8rk\u0016,XmT;uaV$X\t\\3nK:$HC\u0001\u00100\u0011\u0015\u0001T\u00011\u00012\u0003\u0011)G.Z7\u0011\u0005Y\u0011\u0014BA\u001a\u0018\u0005\r\te._\u0001\u000bgV\u0014'/Z2fSZ,W#\u0001\u001c\u0011\u0005\u0011:\u0014B\u0001\u001d\u000f\u0005)\u0019VO\u0019*fG\u0016Lg/Z\u0001\fI\u0016l\u0017M\u001c3D_VtG/F\u0001<!\t1B(\u0003\u0002>/\t!Aj\u001c8h\u0003!\u0019w.\u001c9mKR,\u0017AB2b]\u000e,G.A\u0003feJ|'\u000f\u0006\u0002\u001f\u0005\")1I\u0003a\u0001\t\u0006\tQ\r\u0005\u0002F\u001b:\u0011ai\u0013\b\u0003\u000f*k\u0011\u0001\u0013\u0006\u0003\u0013r\ta\u0001\u0010:p_Rt\u0014\"\u0001\r\n\u00051;\u0012a\u00029bG.\fw-Z\u0005\u0003\u001d>\u0013\u0011\u0002\u00165s_^\f'\r\\3\u000b\u00051;\u0012\u0001C5t\u00072|7/\u001a3\u0002\r%\u001cx\n]3oQ\t\u00011\u000b\u0005\u0002U/6\tQK\u0003\u0002W%\u0005Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\u0005a+&aC%oi\u0016\u0014h.\u00197Ba&\u0004")
@InternalApi
public interface Outputs {
    public TransferState NeedsDemand();

    public TransferState NeedsDemandOrCancel();

    public boolean demandAvailable();

    public void enqueueOutputElement(Object var1);

    public SubReceive subreceive();

    public static /* synthetic */ long demandCount$(Outputs $this) {
        return $this.demandCount();
    }

    default public long demandCount() {
        return -1L;
    }

    public void complete();

    public void cancel();

    public void error(Throwable var1);

    public boolean isClosed();

    public static /* synthetic */ boolean isOpen$(Outputs $this) {
        return $this.isOpen();
    }

    default public boolean isOpen() {
        return !this.isClosed();
    }

    public static void $init$(Outputs $this) {
    }
}

