/*
 * Decompiled with CFR 0.152.
 */
package akka.event;

import akka.event.LogMarker;
import akka.event.LoggingFilter;
import akka.event.LoggingFilterWithMarker;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005y3Aa\u0002\u0005\u0001\u001b!A\u0001\u0004\u0001B\u0001B\u0003%\u0011\u0004C\u0003\u001d\u0001\u0011\u0005Q\u0004C\u0003!\u0001\u0011\u0005\u0013\u0005C\u0003D\u0001\u0011\u0005C\tC\u0003M\u0001\u0011\u0005S\nC\u0003V\u0001\u0011\u0005cK\u0001\u0010M_\u001e<\u0017N\\4GS2$XM],ji\"l\u0015M]6fe^\u0013\u0018\r\u001d9fe*\u0011\u0011BC\u0001\u0006KZ,g\u000e\u001e\u0006\u0002\u0017\u0005!\u0011m[6b\u0007\u0001\u00192\u0001\u0001\b\u0015!\ty!#D\u0001\u0011\u0015\u0005\t\u0012!B:dC2\f\u0017BA\n\u0011\u0005\u0019\te.\u001f*fMB\u0011QCF\u0007\u0002\u0011%\u0011q\u0003\u0003\u0002\u0018\u0019><w-\u001b8h\r&dG/\u001a:XSRDW*\u0019:lKJ\fQ\u0002\\8hO&twMR5mi\u0016\u0014\bCA\u000b\u001b\u0013\tY\u0002BA\u0007M_\u001e<\u0017N\\4GS2$XM]\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0005yy\u0002CA\u000b\u0001\u0011\u0015A\"\u00011\u0001\u001a\u00039I7/\u0012:s_J,e.\u00192mK\u0012$2AI\u0013?!\ty1%\u0003\u0002%!\t9!i\\8mK\u0006t\u0007\"\u0002\u0014\u0004\u0001\u00049\u0013\u0001\u00037pO\u000ec\u0017m]:1\u0005!*\u0004cA\u00151g9\u0011!F\f\t\u0003WAi\u0011\u0001\f\u0006\u0003[1\ta\u0001\u0010:p_Rt\u0014BA\u0018\u0011\u0003\u0019\u0001&/\u001a3fM&\u0011\u0011G\r\u0002\u0006\u00072\f7o\u001d\u0006\u0003_A\u0001\"\u0001N\u001b\r\u0001\u0011Ia'JA\u0001\u0002\u0003\u0015\ta\u000e\u0002\u0005?\u00122\u0014'\u0005\u00029wA\u0011q\"O\u0005\u0003uA\u0011qAT8uQ&tw\r\u0005\u0002\u0010y%\u0011Q\b\u0005\u0002\u0004\u0003:L\b\"B \u0004\u0001\u0004\u0001\u0015!\u00037pON{WO]2f!\tI\u0013)\u0003\u0002Ce\t11\u000b\u001e:j]\u001e\f\u0001#[:XCJt\u0017N\\4F]\u0006\u0014G.\u001a3\u0015\u0007\t*5\nC\u0003'\t\u0001\u0007a\t\r\u0002H\u0013B\u0019\u0011\u0006\r%\u0011\u0005QJE!\u0003&F\u0003\u0003\u0005\tQ!\u00018\u0005\u0011yFE\u000e\u001a\t\u000b}\"\u0001\u0019\u0001!\u0002\u001b%\u001c\u0018J\u001c4p\u000b:\f'\r\\3e)\r\u0011c\n\u0016\u0005\u0006M\u0015\u0001\ra\u0014\u0019\u0003!J\u00032!\u000b\u0019R!\t!$\u000bB\u0005T\u001d\u0006\u0005\t\u0011!B\u0001o\t!q\f\n\u001c4\u0011\u0015yT\u00011\u0001A\u00039I7\u000fR3ck\u001e,e.\u00192mK\u0012$2AI,^\u0011\u00151c\u00011\u0001Ya\tI6\fE\u0002*ai\u0003\"\u0001N.\u0005\u0013q;\u0016\u0011!A\u0001\u0006\u00039$\u0001B0%mQBQa\u0010\u0004A\u0002\u0001\u0003")
public class LoggingFilterWithMarkerWrapper
implements LoggingFilterWithMarker {
    private final LoggingFilter loggingFilter;

    @Override
    public boolean isErrorEnabled(Class<?> logClass, String logSource, LogMarker marker) {
        return LoggingFilterWithMarker.isErrorEnabled$(this, logClass, logSource, marker);
    }

    @Override
    public boolean isWarningEnabled(Class<?> logClass, String logSource, LogMarker marker) {
        return LoggingFilterWithMarker.isWarningEnabled$(this, logClass, logSource, marker);
    }

    @Override
    public boolean isInfoEnabled(Class<?> logClass, String logSource, LogMarker marker) {
        return LoggingFilterWithMarker.isInfoEnabled$(this, logClass, logSource, marker);
    }

    @Override
    public boolean isDebugEnabled(Class<?> logClass, String logSource, LogMarker marker) {
        return LoggingFilterWithMarker.isDebugEnabled$(this, logClass, logSource, marker);
    }

    @Override
    public boolean isErrorEnabled(Class<?> logClass, String logSource) {
        return this.loggingFilter.isErrorEnabled(logClass, logSource);
    }

    @Override
    public boolean isWarningEnabled(Class<?> logClass, String logSource) {
        return this.loggingFilter.isWarningEnabled(logClass, logSource);
    }

    @Override
    public boolean isInfoEnabled(Class<?> logClass, String logSource) {
        return this.loggingFilter.isInfoEnabled(logClass, logSource);
    }

    @Override
    public boolean isDebugEnabled(Class<?> logClass, String logSource) {
        return this.loggingFilter.isDebugEnabled(logClass, logSource);
    }

    public LoggingFilterWithMarkerWrapper(LoggingFilter loggingFilter) {
        this.loggingFilter = loggingFilter;
        LoggingFilterWithMarker.$init$(this);
    }
}

