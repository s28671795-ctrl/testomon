/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import akka.stream.impl.JavaFlowAndRsConverters$Implicits$;
import akka.stream.impl.JavaFlowAndRsConverters$Implicits$RsSubscriberConverter$;
import java.util.concurrent.Flow;
import org.reactivestreams.Publisher;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005Y3Q!\u0002\u0004\u0003\u00151A\u0001\"\r\u0001\u0003\u0006\u0004%\tA\r\u0005\tu\u0001\u0011\t\u0011)A\u0005g!)1\b\u0001C\u0001y!)\u0001\t\u0001C!\u0003\na\"k\u001d)vE2L7\u000f[3s)>T\u0015M^1GY><\u0018\tZ1qi\u0016\u0014(BA\u0004\t\u0003\u0011IW\u000e\u001d7\u000b\u0005%Q\u0011AB:ue\u0016\fWNC\u0001\f\u0003\u0011\t7n[1\u0016\u00055!3c\u0001\u0001\u000f-A\u0011q\u0002F\u0007\u0002!)\u0011\u0011CE\u0001\u0005Y\u0006twMC\u0001\u0014\u0003\u0011Q\u0017M^1\n\u0005U\u0001\"AB(cU\u0016\u001cG\u000fE\u0002\u0018?\tr!\u0001G\u000f\u000e\u0003eQ!AG\u000e\u0002\u0015\r|gnY;se\u0016tGO\u0003\u0002\u001d%\u0005!Q\u000f^5m\u0013\tq\u0012$\u0001\u0003GY><\u0018B\u0001\u0011\"\u0005%\u0001VO\u00197jg\",'O\u0003\u0002\u001f3A\u00111\u0005\n\u0007\u0001\t\u0015)\u0003A1\u0001(\u0005\u0005!6\u0001A\t\u0003Q9\u0002\"!\u000b\u0017\u000e\u0003)R\u0011aK\u0001\u0006g\u000e\fG.Y\u0005\u0003[)\u0012qAT8uQ&tw\r\u0005\u0002*_%\u0011\u0001G\u000b\u0002\u0004\u0003:L\u0018\u0001\u00033fY\u0016<\u0017\r^3\u0016\u0003M\u00022\u0001N\u001d#\u001b\u0005)$B\u0001\u001c8\u0003=\u0011X-Y2uSZ,7\u000f\u001e:fC6\u001c(\"\u0001\u001d\u0002\u0007=\u0014x-\u0003\u0002!k\u0005IA-\u001a7fO\u0006$X\rI\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0005uz\u0004c\u0001 \u0001E5\ta\u0001C\u00032\u0007\u0001\u00071'A\u0005tk\n\u001c8M]5cKR\u0011!)\u0012\t\u0003S\rK!\u0001\u0012\u0016\u0003\tUs\u0017\u000e\u001e\u0005\u0006\r\u0012\u0001\raR\u0001\u000fU\u00064\u0018mU;cg\u000e\u0014\u0018NY3sa\tAE\nE\u0002\u0018\u0013.K!AS\u0011\u0003\u0015M+(m]2sS\n,'\u000f\u0005\u0002$\u0019\u0012IQ*RA\u0001\u0002\u0003\u0015\tA\u0014\u0002\u0004?\u0012\u0012\u0014C\u0001\u0012/Q\t\u0001\u0001\u000b\u0005\u0002R)6\t!K\u0003\u0002T\u0015\u0005Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\u0005U\u0013&aC%oi\u0016\u0014h.\u00197Ba&\u0004")
@InternalApi
public final class RsPublisherToJavaFlowAdapter<T>
implements Flow.Publisher<T> {
    private final Publisher<T> delegate;

    public Publisher<T> delegate() {
        return this.delegate;
    }

    @Override
    public void subscribe(Flow.Subscriber<? super T> javaSubscriber) {
        this.delegate().subscribe(JavaFlowAndRsConverters$Implicits$RsSubscriberConverter$.MODULE$.asRs$extension(JavaFlowAndRsConverters$Implicits$.MODULE$.RsSubscriberConverter(javaSubscriber)));
    }

    public RsPublisherToJavaFlowAdapter(Publisher<T> delegate) {
        this.delegate = delegate;
    }
}

