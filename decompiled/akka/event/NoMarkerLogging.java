/*
 * Decompiled with CFR 0.152.
 */
package akka.event;

import akka.event.LogMarker;
import akka.event.LoggingBus;
import akka.event.LoggingFilterWithMarker;
import akka.event.NoMarkerLogging$;
import scala.collection.immutable.Map;
import scala.collection.immutable.Seq;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005]x!\u0002\u0014(\u0011\u0003ac!\u0002\u0018(\u0011\u0003y\u0003\"B\u001a\u0002\t\u0003!\u0004\"B\u001b\u0002\t\u00031\u0004\"B\u001c\u0002\t\u000bB\u0004\"B \u0002\t\u000bB\u0004\"\u0002!\u0002\t\u000bB\u0004\"B!\u0002\t\u000bB\u0004\"\u0002\"\u0002\t+\u001a\u0005\"\u0002\"\u0002\t+\"\u0006\"B1\u0002\t+\u0012\u0007\"B1\u0002\t+\"\u0007\"B4\u0002\t+B\u0007\"\u00026\u0002\t+Z\u0007\"B7\u0002\t\u000br\u0007\"B7\u0002\t\u000b2\bBB7\u0002\t\u000b\n\t\u0001\u0003\u0004n\u0003\u0011\u0015\u0013q\u0002\u0005\u0007[\u0006!)%a\b\t\r5\fAQIA\u0019\u0011\u0019i\u0017\u0001\"\u0012\u00028!1Q.\u0001C#\u0003\u007fAa!\\\u0001\u0005F\u0005%\u0003BB7\u0002\t\u000b\n)\u0006C\u0004\u0002d\u0005!)%!\u001a\t\u000f\u0005\r\u0014\u0001\"\u0012\u0002l!9\u00111M\u0001\u0005F\u0005M\u0004bBA2\u0003\u0011\u0015\u0013Q\u0010\u0005\b\u0003G\nAQIAE\u0011\u001d\t9*\u0001C#\u00033Cq!a&\u0002\t\u000b\ny\nC\u0004\u0002\u0018\u0006!)%!+\t\u000f\u0005]\u0015\u0001\"\u0012\u00026\"9\u00111Y\u0001\u0005F\u0005\u0015\u0007bBAb\u0003\u0011\u0015\u00131\u001a\u0005\b\u0003\u0007\fAQIAj\u0011\u001d\t\u0019-\u0001C#\u0003;Dq!a1\u0002\t\u000b\nI/A\bO_6\u000b'o[3s\u0019><w-\u001b8h\u0015\tA\u0013&A\u0003fm\u0016tGOC\u0001+\u0003\u0011\t7n[1\u0004\u0001A\u0011Q&A\u0007\u0002O\tyaj\\'be.,'\u000fT8hO&twm\u0005\u0002\u0002aA\u0011Q&M\u0005\u0003e\u001d\u0012A#T1sW\u0016\u0014Hj\\4hS:<\u0017\tZ1qi\u0016\u0014\u0018A\u0002\u001fj]&$h\bF\u0001-\u0003-9W\r^%ogR\fgnY3\u0016\u00031\na\"[:FeJ|'/\u00128bE2,G-F\u0001:!\tQT(D\u0001<\u0015\u0005a\u0014!B:dC2\f\u0017B\u0001 <\u0005\u001d\u0011un\u001c7fC:\f\u0001#[:XCJt\u0017N\\4F]\u0006\u0014G.\u001a3\u0002\u001b%\u001c\u0018J\u001c4p\u000b:\f'\r\\3e\u00039I7\u000fR3ck\u001e,e.\u00192mK\u0012\f1B\\8uS\u001aLXI\u001d:peR\u0011Ai\u0012\t\u0003u\u0015K!AR\u001e\u0003\tUs\u0017\u000e\u001e\u0005\u0006\u0011\"\u0001\r!S\u0001\b[\u0016\u001c8/Y4f!\tQ\u0015K\u0004\u0002L\u001fB\u0011AjO\u0007\u0002\u001b*\u0011ajK\u0001\u0007yI|w\u000e\u001e \n\u0005A[\u0014A\u0002)sK\u0012,g-\u0003\u0002S'\n11\u000b\u001e:j]\u001eT!\u0001U\u001e\u0015\u0007\u0011+\u0006\rC\u0003W\u0013\u0001\u0007q+A\u0003dCV\u001cX\r\u0005\u0002Y;:\u0011\u0011l\u0017\b\u0003\u0019jK\u0011\u0001P\u0005\u00039n\nq\u0001]1dW\u0006<W-\u0003\u0002_?\nIA\u000b\u001b:po\u0006\u0014G.\u001a\u0006\u00039nBQ\u0001S\u0005A\u0002%\u000bQB\\8uS\u001aLx+\u0019:oS:<GC\u0001#d\u0011\u0015A%\u00021\u0001J)\r!UM\u001a\u0005\u0006-.\u0001\ra\u0016\u0005\u0006\u0011.\u0001\r!S\u0001\u000b]>$\u0018NZ=J]\u001a|GC\u0001#j\u0011\u0015AE\u00021\u0001J\u0003-qw\u000e^5gs\u0012+'-^4\u0015\u0005\u0011c\u0007\"\u0002%\u000e\u0001\u0004I\u0015!B3se>\u0014H\u0003\u0002#piVDQ\u0001\u001d\bA\u0002E\fa!\\1sW\u0016\u0014\bCA\u0017s\u0013\t\u0019xEA\u0005M_\u001el\u0015M]6fe\")aK\u0004a\u0001/\")\u0001J\u0004a\u0001\u0013R)Ai\u001e=zw\")\u0001o\u0004a\u0001c\")ak\u0004a\u0001/\")!p\u0004a\u0001\u0013\u0006AA/Z7qY\u0006$X\rC\u0003}\u001f\u0001\u0007Q0\u0001\u0003be\u001e\f\u0004C\u0001\u001e\u007f\u0013\ty8HA\u0002B]f$2\u0002RA\u0002\u0003\u000b\t9!!\u0003\u0002\f!)\u0001\u000f\u0005a\u0001c\")a\u000b\u0005a\u0001/\")!\u0010\u0005a\u0001\u0013\")A\u0010\u0005a\u0001{\"1\u0011Q\u0002\tA\u0002u\fA!\u0019:heQiA)!\u0005\u0002\u0014\u0005U\u0011qCA\r\u00037AQ\u0001]\tA\u0002EDQAV\tA\u0002]CQA_\tA\u0002%CQ\u0001`\tA\u0002uDa!!\u0004\u0012\u0001\u0004i\bBBA\u000f#\u0001\u0007Q0\u0001\u0003be\u001e\u001cDc\u0004#\u0002\"\u0005\r\u0012QEA\u0014\u0003S\tY#!\f\t\u000bA\u0014\u0002\u0019A9\t\u000bY\u0013\u0002\u0019A,\t\u000bi\u0014\u0002\u0019A%\t\u000bq\u0014\u0002\u0019A?\t\r\u00055!\u00031\u0001~\u0011\u0019\tiB\u0005a\u0001{\"1\u0011q\u0006\nA\u0002u\fA!\u0019:hiQ)A)a\r\u00026!)\u0001o\u0005a\u0001c\")\u0001j\u0005a\u0001\u0013R9A)!\u000f\u0002<\u0005u\u0002\"\u00029\u0015\u0001\u0004\t\b\"\u0002>\u0015\u0001\u0004I\u0005\"\u0002?\u0015\u0001\u0004iH#\u0003#\u0002B\u0005\r\u0013QIA$\u0011\u0015\u0001X\u00031\u0001r\u0011\u0015QX\u00031\u0001J\u0011\u0015aX\u00031\u0001~\u0011\u0019\ti!\u0006a\u0001{RYA)a\u0013\u0002N\u0005=\u0013\u0011KA*\u0011\u0015\u0001h\u00031\u0001r\u0011\u0015Qh\u00031\u0001J\u0011\u0015ah\u00031\u0001~\u0011\u0019\tiA\u0006a\u0001{\"1\u0011Q\u0004\fA\u0002u$R\u0002RA,\u00033\nY&!\u0018\u0002`\u0005\u0005\u0004\"\u00029\u0018\u0001\u0004\t\b\"\u0002>\u0018\u0001\u0004I\u0005\"\u0002?\u0018\u0001\u0004i\bBBA\u0007/\u0001\u0007Q\u0010\u0003\u0004\u0002\u001e]\u0001\r! \u0005\u0007\u0003_9\u0002\u0019A?\u0002\u000f]\f'O\\5oOR)A)a\u001a\u0002j!)\u0001\u000f\u0007a\u0001c\")\u0001\n\u0007a\u0001\u0013R9A)!\u001c\u0002p\u0005E\u0004\"\u00029\u001a\u0001\u0004\t\b\"\u0002>\u001a\u0001\u0004I\u0005\"\u0002?\u001a\u0001\u0004iH#\u0003#\u0002v\u0005]\u0014\u0011PA>\u0011\u0015\u0001(\u00041\u0001r\u0011\u0015Q(\u00041\u0001J\u0011\u0015a(\u00041\u0001~\u0011\u0019\tiA\u0007a\u0001{RYA)a \u0002\u0002\u0006\r\u0015QQAD\u0011\u0015\u00018\u00041\u0001r\u0011\u0015Q8\u00041\u0001J\u0011\u0015a8\u00041\u0001~\u0011\u0019\tia\u0007a\u0001{\"1\u0011QD\u000eA\u0002u$R\u0002RAF\u0003\u001b\u000by)!%\u0002\u0014\u0006U\u0005\"\u00029\u001d\u0001\u0004\t\b\"\u0002>\u001d\u0001\u0004I\u0005\"\u0002?\u001d\u0001\u0004i\bBBA\u00079\u0001\u0007Q\u0010\u0003\u0004\u0002\u001eq\u0001\r! \u0005\u0007\u0003_a\u0002\u0019A?\u0002\t%tgm\u001c\u000b\u0006\t\u0006m\u0015Q\u0014\u0005\u0006av\u0001\r!\u001d\u0005\u0006\u0011v\u0001\r!\u0013\u000b\n\t\u0006\u0005\u00161UAS\u0003OCQ\u0001\u001d\u0010A\u0002EDQA\u001f\u0010A\u0002%CQ\u0001 \u0010A\u0002uDa!!\u0004\u001f\u0001\u0004iHc\u0003#\u0002,\u00065\u0016qVAY\u0003gCQ\u0001]\u0010A\u0002EDQA_\u0010A\u0002%CQ\u0001`\u0010A\u0002uDa!!\u0004 \u0001\u0004i\bBBA\u000f?\u0001\u0007Q\u0010F\u0007E\u0003o\u000bI,a/\u0002>\u0006}\u0016\u0011\u0019\u0005\u0006a\u0002\u0002\r!\u001d\u0005\u0006u\u0002\u0002\r!\u0013\u0005\u0006y\u0002\u0002\r! \u0005\u0007\u0003\u001b\u0001\u0003\u0019A?\t\r\u0005u\u0001\u00051\u0001~\u0011\u0019\ty\u0003\ta\u0001{\u0006)A-\u001a2vOR)A)a2\u0002J\")\u0001/\ta\u0001c\")\u0001*\ta\u0001\u0013R9A)!4\u0002P\u0006E\u0007\"\u00029#\u0001\u0004\t\b\"\u0002>#\u0001\u0004I\u0005\"\u0002?#\u0001\u0004iH#\u0003#\u0002V\u0006]\u0017\u0011\\An\u0011\u0015\u00018\u00051\u0001r\u0011\u0015Q8\u00051\u0001J\u0011\u0015a8\u00051\u0001~\u0011\u0019\tia\ta\u0001{RYA)a8\u0002b\u0006\r\u0018Q]At\u0011\u0015\u0001H\u00051\u0001r\u0011\u0015QH\u00051\u0001J\u0011\u0015aH\u00051\u0001~\u0011\u0019\ti\u0001\na\u0001{\"1\u0011Q\u0004\u0013A\u0002u$R\u0002RAv\u0003[\fy/!=\u0002t\u0006U\b\"\u00029&\u0001\u0004\t\b\"\u0002>&\u0001\u0004I\u0005\"\u0002?&\u0001\u0004i\bBBA\u0007K\u0001\u0007Q\u0010\u0003\u0004\u0002\u001e\u0015\u0002\r! \u0005\u0007\u0003_)\u0003\u0019A?")
public final class NoMarkerLogging {
    public static void debug(LogMarker marker, String template, Object arg1, Object arg2, Object arg3, Object arg4) {
        NoMarkerLogging$.MODULE$.debug(marker, template, arg1, arg2, arg3, arg4);
    }

    public static void debug(LogMarker marker, String template, Object arg1, Object arg2, Object arg3) {
        NoMarkerLogging$.MODULE$.debug(marker, template, arg1, arg2, arg3);
    }

    public static void debug(LogMarker marker, String template, Object arg1, Object arg2) {
        NoMarkerLogging$.MODULE$.debug(marker, template, arg1, arg2);
    }

    public static void debug(LogMarker marker, String template, Object arg1) {
        NoMarkerLogging$.MODULE$.debug(marker, template, arg1);
    }

    public static void debug(LogMarker marker, String message) {
        NoMarkerLogging$.MODULE$.debug(marker, message);
    }

    public static void info(LogMarker marker, String template, Object arg1, Object arg2, Object arg3, Object arg4) {
        NoMarkerLogging$.MODULE$.info(marker, template, arg1, arg2, arg3, arg4);
    }

    public static void info(LogMarker marker, String template, Object arg1, Object arg2, Object arg3) {
        NoMarkerLogging$.MODULE$.info(marker, template, arg1, arg2, arg3);
    }

    public static void info(LogMarker marker, String template, Object arg1, Object arg2) {
        NoMarkerLogging$.MODULE$.info(marker, template, arg1, arg2);
    }

    public static void info(LogMarker marker, String message) {
        NoMarkerLogging$.MODULE$.info(marker, message);
    }

    public static void warning(LogMarker marker, String template, Object arg1, Object arg2, Object arg3, Object arg4) {
        NoMarkerLogging$.MODULE$.warning(marker, template, arg1, arg2, arg3, arg4);
    }

    public static void warning(LogMarker marker, String template, Object arg1, Object arg2, Object arg3) {
        NoMarkerLogging$.MODULE$.warning(marker, template, arg1, arg2, arg3);
    }

    public static void warning(LogMarker marker, String template, Object arg1, Object arg2) {
        NoMarkerLogging$.MODULE$.warning(marker, template, arg1, arg2);
    }

    public static void warning(LogMarker marker, String template, Object arg1) {
        NoMarkerLogging$.MODULE$.warning(marker, template, arg1);
    }

    public static void warning(LogMarker marker, String message) {
        NoMarkerLogging$.MODULE$.warning(marker, message);
    }

    public static void error(LogMarker marker, String template, Object arg1, Object arg2, Object arg3, Object arg4) {
        NoMarkerLogging$.MODULE$.error(marker, template, arg1, arg2, arg3, arg4);
    }

    public static void error(LogMarker marker, String template, Object arg1, Object arg2, Object arg3) {
        NoMarkerLogging$.MODULE$.error(marker, template, arg1, arg2, arg3);
    }

    public static void error(LogMarker marker, String template, Object arg1, Object arg2) {
        NoMarkerLogging$.MODULE$.error(marker, template, arg1, arg2);
    }

    public static void error(LogMarker marker, String template, Object arg1) {
        NoMarkerLogging$.MODULE$.error(marker, template, arg1);
    }

    public static void error(LogMarker marker, String message) {
        NoMarkerLogging$.MODULE$.error(marker, message);
    }

    public static void error(LogMarker marker, Throwable cause, String template, Object arg1, Object arg2, Object arg3, Object arg4) {
        NoMarkerLogging$.MODULE$.error(marker, cause, template, arg1, arg2, arg3, arg4);
    }

    public static void error(LogMarker marker, Throwable cause, String template, Object arg1, Object arg2, Object arg3) {
        NoMarkerLogging$.MODULE$.error(marker, cause, template, arg1, arg2, arg3);
    }

    public static void error(LogMarker marker, Throwable cause, String template, Object arg1, Object arg2) {
        NoMarkerLogging$.MODULE$.error(marker, cause, template, arg1, arg2);
    }

    public static void error(LogMarker marker, Throwable cause, String template, Object arg1) {
        NoMarkerLogging$.MODULE$.error(marker, cause, template, arg1);
    }

    public static void error(LogMarker marker, Throwable cause, String message) {
        NoMarkerLogging$.MODULE$.error(marker, cause, message);
    }

    public static boolean isDebugEnabled() {
        return NoMarkerLogging$.MODULE$.isDebugEnabled();
    }

    public static boolean isInfoEnabled() {
        return NoMarkerLogging$.MODULE$.isInfoEnabled();
    }

    public static boolean isWarningEnabled() {
        return NoMarkerLogging$.MODULE$.isWarningEnabled();
    }

    public static boolean isErrorEnabled() {
        return NoMarkerLogging$.MODULE$.isErrorEnabled();
    }

    public static NoMarkerLogging$ getInstance() {
        return NoMarkerLogging$.MODULE$.getInstance();
    }

    public static void log(LogMarker marker, int level, String message) {
        NoMarkerLogging$.MODULE$.log(marker, level, message);
    }

    public static void info(LogMarker marker, String template, Object arg1) {
        NoMarkerLogging$.MODULE$.info(marker, template, arg1);
    }

    public static boolean isDebugEnabled(LogMarker marker) {
        return NoMarkerLogging$.MODULE$.isDebugEnabled(marker);
    }

    public static boolean isInfoEnabled(LogMarker marker) {
        return NoMarkerLogging$.MODULE$.isInfoEnabled(marker);
    }

    public static boolean isWarningEnabled(LogMarker marker) {
        return NoMarkerLogging$.MODULE$.isWarningEnabled(marker);
    }

    public static boolean isErrorEnabled(LogMarker marker) {
        return NoMarkerLogging$.MODULE$.isErrorEnabled(marker);
    }

    public static LoggingFilterWithMarker loggingFilterWithMarker() {
        return NoMarkerLogging$.MODULE$.loggingFilterWithMarker();
    }

    public static Class<?> logClass() {
        return NoMarkerLogging$.MODULE$.logClass();
    }

    public static String logSource() {
        return NoMarkerLogging$.MODULE$.logSource();
    }

    public static LoggingBus bus() {
        return NoMarkerLogging$.MODULE$.bus();
    }

    public static String format(String t2, Seq<Object> arg) {
        return NoMarkerLogging$.MODULE$.format(t2, arg);
    }

    public static void notifyLog(int level, String message) {
        NoMarkerLogging$.MODULE$.notifyLog(level, message);
    }

    public static boolean isEnabled(int level) {
        return NoMarkerLogging$.MODULE$.isEnabled(level);
    }

    public static void log(int level, String template, Object arg1, Object arg2, Object arg3, Object arg4) {
        NoMarkerLogging$.MODULE$.log(level, template, arg1, arg2, arg3, arg4);
    }

    public static void log(int level, String template, Object arg1, Object arg2, Object arg3) {
        NoMarkerLogging$.MODULE$.log(level, template, arg1, arg2, arg3);
    }

    public static void log(int level, String template, Object arg1, Object arg2) {
        NoMarkerLogging$.MODULE$.log(level, template, arg1, arg2);
    }

    public static void log(int level, String template, Object arg1) {
        NoMarkerLogging$.MODULE$.log(level, template, arg1);
    }

    public static void log(int level, String message) {
        NoMarkerLogging$.MODULE$.log(level, message);
    }

    public static void debug(String template, Object arg1, Object arg2, Object arg3, Object arg4) {
        NoMarkerLogging$.MODULE$.debug(template, arg1, arg2, arg3, arg4);
    }

    public static void debug(String template, Object arg1, Object arg2, Object arg3) {
        NoMarkerLogging$.MODULE$.debug(template, arg1, arg2, arg3);
    }

    public static void debug(String template, Object arg1, Object arg2) {
        NoMarkerLogging$.MODULE$.debug(template, arg1, arg2);
    }

    public static void debug(String template, Object arg1) {
        NoMarkerLogging$.MODULE$.debug(template, arg1);
    }

    public static void debug(String message) {
        NoMarkerLogging$.MODULE$.debug(message);
    }

    public static void info(String template, Object arg1, Object arg2, Object arg3, Object arg4) {
        NoMarkerLogging$.MODULE$.info(template, arg1, arg2, arg3, arg4);
    }

    public static void info(String template, Object arg1, Object arg2, Object arg3) {
        NoMarkerLogging$.MODULE$.info(template, arg1, arg2, arg3);
    }

    public static void info(String template, Object arg1, Object arg2) {
        NoMarkerLogging$.MODULE$.info(template, arg1, arg2);
    }

    public static void info(String template, Object arg1) {
        NoMarkerLogging$.MODULE$.info(template, arg1);
    }

    public static void info(String message) {
        NoMarkerLogging$.MODULE$.info(message);
    }

    public static void warning(String template, Object arg1, Object arg2, Object arg3, Object arg4) {
        NoMarkerLogging$.MODULE$.warning(template, arg1, arg2, arg3, arg4);
    }

    public static void warning(String template, Object arg1, Object arg2, Object arg3) {
        NoMarkerLogging$.MODULE$.warning(template, arg1, arg2, arg3);
    }

    public static void warning(String template, Object arg1, Object arg2) {
        NoMarkerLogging$.MODULE$.warning(template, arg1, arg2);
    }

    public static void warning(String template, Object arg1) {
        NoMarkerLogging$.MODULE$.warning(template, arg1);
    }

    public static void warning(String message) {
        NoMarkerLogging$.MODULE$.warning(message);
    }

    public static void warning(Throwable cause, String template, Object arg1, Object arg2, Object arg3, Object arg4) {
        NoMarkerLogging$.MODULE$.warning(cause, template, arg1, arg2, arg3, arg4);
    }

    public static void warning(Throwable cause, String template, Object arg1, Object arg2, Object arg3) {
        NoMarkerLogging$.MODULE$.warning(cause, template, arg1, arg2, arg3);
    }

    public static void warning(Throwable cause, String template, Object arg1, Object arg2) {
        NoMarkerLogging$.MODULE$.warning(cause, template, arg1, arg2);
    }

    public static void warning(Throwable cause, String template, Object arg1) {
        NoMarkerLogging$.MODULE$.warning(cause, template, arg1);
    }

    public static void warning(Throwable cause, String message) {
        NoMarkerLogging$.MODULE$.warning(cause, message);
    }

    public static void error(String template, Object arg1, Object arg2, Object arg3, Object arg4) {
        NoMarkerLogging$.MODULE$.error(template, arg1, arg2, arg3, arg4);
    }

    public static void error(String template, Object arg1, Object arg2, Object arg3) {
        NoMarkerLogging$.MODULE$.error(template, arg1, arg2, arg3);
    }

    public static void error(String template, Object arg1, Object arg2) {
        NoMarkerLogging$.MODULE$.error(template, arg1, arg2);
    }

    public static void error(String template, Object arg1) {
        NoMarkerLogging$.MODULE$.error(template, arg1);
    }

    public static void error(String message) {
        NoMarkerLogging$.MODULE$.error(message);
    }

    public static void error(Throwable cause, String template, Object arg1, Object arg2, Object arg3, Object arg4) {
        NoMarkerLogging$.MODULE$.error(cause, template, arg1, arg2, arg3, arg4);
    }

    public static void error(Throwable cause, String template, Object arg1, Object arg2, Object arg3) {
        NoMarkerLogging$.MODULE$.error(cause, template, arg1, arg2, arg3);
    }

    public static void error(Throwable cause, String template, Object arg1, Object arg2) {
        NoMarkerLogging$.MODULE$.error(cause, template, arg1, arg2);
    }

    public static void error(Throwable cause, String template, Object arg1) {
        NoMarkerLogging$.MODULE$.error(cause, template, arg1);
    }

    public static void error(Throwable cause, String message) {
        NoMarkerLogging$.MODULE$.error(cause, message);
    }

    public static Map<String, Object> mdc() {
        return NoMarkerLogging$.MODULE$.mdc();
    }
}

