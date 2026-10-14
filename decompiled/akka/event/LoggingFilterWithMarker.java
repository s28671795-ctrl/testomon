/*
 * Decompiled with CFR 0.152.
 */
package akka.event;

import akka.event.LogMarker;
import akka.event.LoggingFilter;
import akka.event.LoggingFilterWithMarker$;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005A4qAC\u0006\u0011\u0002\u0007\u0005\u0001\u0003C\u0003\u001c\u0001\u0011\u0005A\u0004C\u0003!\u0001\u0011\u0005\u0011\u0005C\u0003I\u0001\u0011\u0005\u0011\nC\u0003S\u0001\u0011\u00051\u000bC\u0003]\u0001\u0011\u0005QlB\u0003g\u0017!\u0005qMB\u0003\u000b\u0017!\u0005\u0001\u000eC\u0003j\u000f\u0011\u0005!\u000eC\u0003l\u000f\u0011\u0005ANA\fM_\u001e<\u0017N\\4GS2$XM],ji\"l\u0015M]6fe*\u0011A\"D\u0001\u0006KZ,g\u000e\u001e\u0006\u0002\u001d\u0005!\u0011m[6b\u0007\u0001\u00192\u0001A\t\u0018!\t\u0011R#D\u0001\u0014\u0015\u0005!\u0012!B:dC2\f\u0017B\u0001\f\u0014\u0005\u0019\te.\u001f*fMB\u0011\u0001$G\u0007\u0002\u0017%\u0011!d\u0003\u0002\u000e\u0019><w-\u001b8h\r&dG/\u001a:\u0002\r\u0011Jg.\u001b;%)\u0005i\u0002C\u0001\n\u001f\u0013\ty2C\u0001\u0003V]&$\u0018AD5t\u000bJ\u0014xN]#oC\ndW\r\u001a\u000b\u0005E\u0015r4\t\u0005\u0002\u0013G%\u0011Ae\u0005\u0002\b\u0005>|G.Z1o\u0011\u00151#\u00011\u0001(\u0003!awnZ\"mCN\u001c\bG\u0001\u00156!\rI\u0003g\r\b\u0003U9\u0002\"aK\n\u000e\u00031R!!L\b\u0002\rq\u0012xn\u001c;?\u0013\ty3#\u0001\u0004Qe\u0016$WMZ\u0005\u0003cI\u0012Qa\u00117bgNT!aL\n\u0011\u0005Q*D\u0002\u0001\u0003\nm\u0015\n\t\u0011!A\u0003\u0002]\u0012Aa\u0018\u00136oE\u0011\u0001h\u000f\t\u0003%eJ!AO\n\u0003\u000f9{G\u000f[5oOB\u0011!\u0003P\u0005\u0003{M\u00111!\u00118z\u0011\u0015y$\u00011\u0001A\u0003%awnZ*pkJ\u001cW\r\u0005\u0002*\u0003&\u0011!I\r\u0002\u0007'R\u0014\u0018N\\4\t\u000b\u0011\u0013\u0001\u0019A#\u0002\r5\f'o[3s!\tAb)\u0003\u0002H\u0017\tIAj\\4NCJ\\WM]\u0001\u0011SN<\u0016M\u001d8j]\u001e,e.\u00192mK\u0012$BA\t&Q#\")ae\u0001a\u0001\u0017B\u0012AJ\u0014\t\u0004SAj\u0005C\u0001\u001bO\t%y%*!A\u0001\u0002\u000b\u0005qG\u0001\u0003`IUB\u0004\"B \u0004\u0001\u0004\u0001\u0005\"\u0002#\u0004\u0001\u0004)\u0015!D5t\u0013:4w.\u00128bE2,G\r\u0006\u0003#)j[\u0006\"\u0002\u0014\u0005\u0001\u0004)\u0006G\u0001,Y!\rI\u0003g\u0016\t\u0003ia#\u0011\"\u0017+\u0002\u0002\u0003\u0005)\u0011A\u001c\u0003\t}#S'\u000f\u0005\u0006\u007f\u0011\u0001\r\u0001\u0011\u0005\u0006\t\u0012\u0001\r!R\u0001\u000fSN$UMY;h\u000b:\f'\r\\3e)\u0011\u0011c\fZ3\t\u000b\u0019*\u0001\u0019A01\u0005\u0001\u0014\u0007cA\u00151CB\u0011AG\u0019\u0003\nGz\u000b\t\u0011!A\u0003\u0002]\u0012Aa\u0018\u00137a!)q(\u0002a\u0001\u0001\")A)\u0002a\u0001\u000b\u00069Bj\\4hS:<g)\u001b7uKJ<\u0016\u000e\u001e5NCJ\\WM\u001d\t\u00031\u001d\u0019\"aB\t\u0002\rqJg.\u001b;?)\u00059\u0017\u0001B<sCB$\"!\u001c8\u0011\u0005a\u0001\u0001\"B8\n\u0001\u00049\u0012!\u00047pO\u001eLgn\u001a$jYR,'\u000f")
public interface LoggingFilterWithMarker
extends LoggingFilter {
    public static LoggingFilterWithMarker wrap(LoggingFilter loggingFilter) {
        return LoggingFilterWithMarker$.MODULE$.wrap(loggingFilter);
    }

    public static /* synthetic */ boolean isErrorEnabled$(LoggingFilterWithMarker $this, Class logClass, String logSource, LogMarker marker) {
        return $this.isErrorEnabled(logClass, logSource, marker);
    }

    default public boolean isErrorEnabled(Class<?> logClass, String logSource, LogMarker marker) {
        return this.isErrorEnabled(logClass, logSource);
    }

    public static /* synthetic */ boolean isWarningEnabled$(LoggingFilterWithMarker $this, Class logClass, String logSource, LogMarker marker) {
        return $this.isWarningEnabled(logClass, logSource, marker);
    }

    default public boolean isWarningEnabled(Class<?> logClass, String logSource, LogMarker marker) {
        return this.isWarningEnabled(logClass, logSource);
    }

    public static /* synthetic */ boolean isInfoEnabled$(LoggingFilterWithMarker $this, Class logClass, String logSource, LogMarker marker) {
        return $this.isInfoEnabled(logClass, logSource, marker);
    }

    default public boolean isInfoEnabled(Class<?> logClass, String logSource, LogMarker marker) {
        return this.isInfoEnabled(logClass, logSource);
    }

    public static /* synthetic */ boolean isDebugEnabled$(LoggingFilterWithMarker $this, Class logClass, String logSource, LogMarker marker) {
        return $this.isDebugEnabled(logClass, logSource, marker);
    }

    default public boolean isDebugEnabled(Class<?> logClass, String logSource, LogMarker marker) {
        return this.isDebugEnabled(logClass, logSource);
    }

    public static void $init$(LoggingFilterWithMarker $this) {
    }
}

