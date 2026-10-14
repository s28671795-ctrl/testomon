/*
 * Decompiled with CFR 0.152.
 */
package akka.event;

import akka.event.DiagnosticLoggingAdapter;
import akka.event.LoggingBus;
import akka.event.LoggingFilter;
import akka.event.MarkerLoggingAdapter;
import java.util.Map;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005=3A!\u0003\u0006\u0003\u001f!Aq\u0003\u0001BC\u0002\u0013\u0005\u0003\u0004\u0003\u0005\u001d\u0001\t\u0005\t\u0015!\u0003\u001a\u0011!i\u0002A!b\u0001\n\u0003r\u0002\u0002\u0003\u0017\u0001\u0005\u0003\u0005\u000b\u0011B\u0010\t\u00115\u0002!Q1A\u0005B9B\u0001b\u000e\u0001\u0003\u0002\u0003\u0006Ia\f\u0005\t\u0001\u0002\u0011\t\u0011)A\u0005\u0003\")A\t\u0001C\u0001\u000b\n\tC)[1h]>\u001cH/[2NCJ\\WM\u001d\"vg2{wmZ5oO\u0006#\u0017\r\u001d;fe*\u00111\u0002D\u0001\u0006KZ,g\u000e\u001e\u0006\u0002\u001b\u0005!\u0011m[6b\u0007\u0001\u00192\u0001\u0001\t\u0015!\t\t\"#D\u0001\u000b\u0013\t\u0019\"B\u0001\u000bNCJ\\WM\u001d'pO\u001eLgnZ!eCB$XM\u001d\t\u0003#UI!A\u0006\u0006\u00031\u0011K\u0017m\u001a8pgRL7\rT8hO&tw-\u00113baR,'/A\u0002ckN,\u0012!\u0007\t\u0003#iI!a\u0007\u0006\u0003\u00151{wmZ5oO\n+8/\u0001\u0003ckN\u0004\u0013!\u00037pON{WO]2f+\u0005y\u0002C\u0001\u0011*\u001d\t\ts\u0005\u0005\u0002#K5\t1E\u0003\u0002%\u001d\u00051AH]8pizR\u0011AJ\u0001\u0006g\u000e\fG.Y\u0005\u0003Q\u0015\na\u0001\u0015:fI\u00164\u0017B\u0001\u0016,\u0005\u0019\u0019FO]5oO*\u0011\u0001&J\u0001\u000bY><7k\\;sG\u0016\u0004\u0013\u0001\u00037pO\u000ec\u0017m]:\u0016\u0003=\u0002$\u0001M\u001b\u0011\u0007\u0001\n4'\u0003\u00023W\t)1\t\\1tgB\u0011A'\u000e\u0007\u0001\t%1d!!A\u0001\u0002\u000b\u0005\u0001H\u0001\u0003`I]\n\u0014!\u00037pO\u000ec\u0017m]:!#\tIT\b\u0005\u0002;w5\tQ%\u0003\u0002=K\t9aj\u001c;iS:<\u0007C\u0001\u001e?\u0013\tyTEA\u0002B]f\fQ\u0002\\8hO&twMR5mi\u0016\u0014\bCA\tC\u0013\t\u0019%BA\u0007M_\u001e<\u0017N\\4GS2$XM]\u0001\u0007y%t\u0017\u000e\u001e \u0015\u000b\u0019;\u0005*\u0013(\u0011\u0005E\u0001\u0001\"B\f\t\u0001\u0004I\u0002\"B\u000f\t\u0001\u0004y\u0002\"B\u0017\t\u0001\u0004Q\u0005GA&N!\r\u0001\u0013\u0007\u0014\t\u0003i5#\u0011BN%\u0002\u0002\u0003\u0005)\u0011\u0001\u001d\t\u000b\u0001C\u0001\u0019A!")
public final class DiagnosticMarkerBusLoggingAdapter
extends MarkerLoggingAdapter
implements DiagnosticLoggingAdapter {
    private final LoggingBus bus;
    private final String logSource;
    private final Class<?> logClass;
    private scala.collection.immutable.Map<String, Object> akka$event$DiagnosticLoggingAdapter$$_mdc;

    @Override
    public scala.collection.immutable.Map<String, Object> mdc() {
        return DiagnosticLoggingAdapter.mdc$(this);
    }

    @Override
    public void mdc(scala.collection.immutable.Map<String, Object> mdc) {
        DiagnosticLoggingAdapter.mdc$(this, mdc);
    }

    @Override
    public Map<String, Object> getMDC() {
        return DiagnosticLoggingAdapter.getMDC$(this);
    }

    @Override
    public void setMDC(Map<String, Object> jMdc) {
        DiagnosticLoggingAdapter.setMDC$(this, jMdc);
    }

    @Override
    public void clearMDC() {
        DiagnosticLoggingAdapter.clearMDC$(this);
    }

    @Override
    public scala.collection.immutable.Map<String, Object> akka$event$DiagnosticLoggingAdapter$$_mdc() {
        return this.akka$event$DiagnosticLoggingAdapter$$_mdc;
    }

    @Override
    public void akka$event$DiagnosticLoggingAdapter$$_mdc_$eq(scala.collection.immutable.Map<String, Object> x$1) {
        this.akka$event$DiagnosticLoggingAdapter$$_mdc = x$1;
    }

    @Override
    public LoggingBus bus() {
        return this.bus;
    }

    @Override
    public String logSource() {
        return this.logSource;
    }

    @Override
    public Class<?> logClass() {
        return this.logClass;
    }

    public DiagnosticMarkerBusLoggingAdapter(LoggingBus bus, String logSource, Class<?> logClass, LoggingFilter loggingFilter) {
        this.bus = bus;
        this.logSource = logSource;
        this.logClass = logClass;
        super(bus, logSource, logClass, loggingFilter);
        DiagnosticLoggingAdapter.$init$(this);
    }
}

