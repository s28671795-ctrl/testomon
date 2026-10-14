/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import akka.stream.impl.JavaFlowAndRsConverters$Implicits$;
import akka.stream.impl.JavaFlowAndRsConverters$Implicits$FlowSubscriberConverter$;
import java.util.concurrent.Flow;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005Y3Q!\u0002\u0004\u0003\u00151A\u0001\"\f\u0001\u0003\u0006\u0004%\tA\f\u0005\tu\u0001\u0011\t\u0011)A\u0005_!)1\b\u0001C\u0001y!)\u0001\t\u0001C!\u0003\na\"*\u0019<b\r2|w\u000fU;cY&\u001c\b.\u001a:U_J\u001b\u0018\tZ1qi\u0016\u0014(BA\u0004\t\u0003\u0011IW\u000e\u001d7\u000b\u0005%Q\u0011AB:ue\u0016\fWNC\u0001\f\u0003\u0011\t7n[1\u0016\u00055\u00013c\u0001\u0001\u000f-A\u0011q\u0002F\u0007\u0002!)\u0011\u0011CE\u0001\u0005Y\u0006twMC\u0001\u0014\u0003\u0011Q\u0017M^1\n\u0005U\u0001\"AB(cU\u0016\u001cG\u000fE\u0002\u00189yi\u0011\u0001\u0007\u0006\u00033i\tqB]3bGRLg/Z:ue\u0016\fWn\u001d\u0006\u00027\u0005\u0019qN]4\n\u0005uA\"!\u0003)vE2L7\u000f[3s!\ty\u0002\u0005\u0004\u0001\u0005\u000b\u0005\u0002!\u0019A\u0012\u0003\u0003Q\u001b\u0001!\u0005\u0002%UA\u0011Q\u0005K\u0007\u0002M)\tq%A\u0003tG\u0006d\u0017-\u0003\u0002*M\t9aj\u001c;iS:<\u0007CA\u0013,\u0013\tacEA\u0002B]f\f\u0001\u0002Z3mK\u001e\fG/Z\u000b\u0002_A\u0019\u0001\u0007\u000f\u0010\u000f\u0005E2T\"\u0001\u001a\u000b\u0005M\"\u0014AC2p]\u000e,(O]3oi*\u0011QGE\u0001\u0005kRLG.\u0003\u00028e\u0005!a\t\\8x\u0013\ti\u0012H\u0003\u00028e\u0005IA-\u001a7fO\u0006$X\rI\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0005uz\u0004c\u0001 \u0001=5\ta\u0001C\u0003.\u0007\u0001\u0007q&A\u0005tk\n\u001c8M]5cKR\u0011!)\u0012\t\u0003K\rK!\u0001\u0012\u0014\u0003\tUs\u0017\u000e\u001e\u0005\u0006\r\u0012\u0001\raR\u0001\reN\u001cVOY:de&\u0014WM\u001d\u0019\u0003\u00112\u00032aF%L\u0013\tQ\u0005D\u0001\u0006Tk\n\u001c8M]5cKJ\u0004\"a\b'\u0005\u00135+\u0015\u0011!A\u0001\u0006\u0003q%aA0%cE\u0011aD\u000b\u0015\u0003\u0001A\u0003\"!\u0015+\u000e\u0003IS!a\u0015\u0006\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0002V%\nY\u0011J\u001c;fe:\fG.\u00119j\u0001")
@InternalApi
public final class JavaFlowPublisherToRsAdapter<T>
implements Publisher<T> {
    private final Flow.Publisher<T> delegate;

    public Flow.Publisher<T> delegate() {
        return this.delegate;
    }

    @Override
    public void subscribe(Subscriber<? super T> rsSubscriber) {
        this.delegate().subscribe(JavaFlowAndRsConverters$Implicits$FlowSubscriberConverter$.MODULE$.asJava$extension(JavaFlowAndRsConverters$Implicits$.MODULE$.FlowSubscriberConverter(rsSubscriber)));
    }

    public JavaFlowPublisherToRsAdapter(Flow.Publisher<T> delegate) {
        this.delegate = delegate;
    }
}

