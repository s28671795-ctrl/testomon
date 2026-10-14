/*
 * Decompiled with CFR 0.152.
 */
package akka.event;

import akka.event.NoLogging$;
import scala.collection.immutable.Map;
import scala.collection.immutable.Seq;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005a;QAD\b\t\u0002Q1QAF\b\t\u0002]AQ!I\u0001\u0005\u0002\tBQaI\u0001\u0005\u0002\u0011BQ!J\u0001\u0005F\u0019BQAK\u0001\u0005F\u0019BQaK\u0001\u0005F\u0019BQ\u0001L\u0001\u0005F\u0019BQ!L\u0001\u0005V9BQ!L\u0001\u0005V}BQ\u0001T\u0001\u0005V5CQ\u0001T\u0001\u0005V=CQAU\u0001\u0005VMCQ!V\u0001\u0005VY\u000b\u0011BT8M_\u001e<\u0017N\\4\u000b\u0005A\t\u0012!B3wK:$(\"\u0001\n\u0002\t\u0005\\7.Y\u0002\u0001!\t)\u0012!D\u0001\u0010\u0005%qu\u000eT8hO&twmE\u0002\u00021y\u0001\"!\u0007\u000f\u000e\u0003iQ\u0011aG\u0001\u0006g\u000e\fG.Y\u0005\u0003;i\u0011a!\u00118z%\u00164\u0007CA\u000b \u0013\t\u0001sB\u0001\bM_\u001e<\u0017N\\4BI\u0006\u0004H/\u001a:\u0002\rqJg.\u001b;?)\u0005!\u0012aC4fi&s7\u000f^1oG\u0016,\u0012\u0001F\u0001\u000fSN,%O]8s\u000b:\f'\r\\3e+\u00059\u0003CA\r)\u0013\tI#DA\u0004C_>dW-\u00198\u0002!%\u001cx+\u0019:oS:<WI\\1cY\u0016$\u0017!D5t\u0013:4w.\u00128bE2,G-\u0001\bjg\u0012+'-^4F]\u0006\u0014G.\u001a3\u0002\u00179|G/\u001b4z\u000bJ\u0014xN\u001d\u000b\u0003_I\u0002\"!\u0007\u0019\n\u0005ER\"\u0001B+oSRDQa\r\u0005A\u0002Q\nq!\\3tg\u0006<W\r\u0005\u00026y9\u0011aG\u000f\t\u0003oii\u0011\u0001\u000f\u0006\u0003sM\ta\u0001\u0010:p_Rt\u0014BA\u001e\u001b\u0003\u0019\u0001&/\u001a3fM&\u0011QH\u0010\u0002\u0007'R\u0014\u0018N\\4\u000b\u0005mRBcA\u0018A\u0017\")\u0011)\u0003a\u0001\u0005\u0006)1-Y;tKB\u00111\t\u0013\b\u0003\t\u001as!aN#\n\u0003mI!a\u0012\u000e\u0002\u000fA\f7m[1hK&\u0011\u0011J\u0013\u0002\n)\"\u0014xn^1cY\u0016T!a\u0012\u000e\t\u000bMJ\u0001\u0019\u0001\u001b\u0002\u001b9|G/\u001b4z/\u0006\u0014h.\u001b8h)\tyc\nC\u00034\u0015\u0001\u0007A\u0007F\u00020!FCQ!Q\u0006A\u0002\tCQaM\u0006A\u0002Q\n!B\\8uS\u001aL\u0018J\u001c4p)\tyC\u000bC\u00034\u0019\u0001\u0007A'A\u0006o_RLg-\u001f#fEV<GCA\u0018X\u0011\u0015\u0019T\u00021\u00015\u0001")
public final class NoLogging {
    public static boolean isDebugEnabled() {
        return NoLogging$.MODULE$.isDebugEnabled();
    }

    public static boolean isInfoEnabled() {
        return NoLogging$.MODULE$.isInfoEnabled();
    }

    public static boolean isWarningEnabled() {
        return NoLogging$.MODULE$.isWarningEnabled();
    }

    public static boolean isErrorEnabled() {
        return NoLogging$.MODULE$.isErrorEnabled();
    }

    public static NoLogging$ getInstance() {
        return NoLogging$.MODULE$.getInstance();
    }

    public static String format(String t2, Seq<Object> arg) {
        return NoLogging$.MODULE$.format(t2, arg);
    }

    public static void notifyLog(int level, String message) {
        NoLogging$.MODULE$.notifyLog(level, message);
    }

    public static boolean isEnabled(int level) {
        return NoLogging$.MODULE$.isEnabled(level);
    }

    public static void log(int level, String template, Object arg1, Object arg2, Object arg3, Object arg4) {
        NoLogging$.MODULE$.log(level, template, arg1, arg2, arg3, arg4);
    }

    public static void log(int level, String template, Object arg1, Object arg2, Object arg3) {
        NoLogging$.MODULE$.log(level, template, arg1, arg2, arg3);
    }

    public static void log(int level, String template, Object arg1, Object arg2) {
        NoLogging$.MODULE$.log(level, template, arg1, arg2);
    }

    public static void log(int level, String template, Object arg1) {
        NoLogging$.MODULE$.log(level, template, arg1);
    }

    public static void log(int level, String message) {
        NoLogging$.MODULE$.log(level, message);
    }

    public static void debug(String template, Object arg1, Object arg2, Object arg3, Object arg4) {
        NoLogging$.MODULE$.debug(template, arg1, arg2, arg3, arg4);
    }

    public static void debug(String template, Object arg1, Object arg2, Object arg3) {
        NoLogging$.MODULE$.debug(template, arg1, arg2, arg3);
    }

    public static void debug(String template, Object arg1, Object arg2) {
        NoLogging$.MODULE$.debug(template, arg1, arg2);
    }

    public static void debug(String template, Object arg1) {
        NoLogging$.MODULE$.debug(template, arg1);
    }

    public static void debug(String message) {
        NoLogging$.MODULE$.debug(message);
    }

    public static void info(String template, Object arg1, Object arg2, Object arg3, Object arg4) {
        NoLogging$.MODULE$.info(template, arg1, arg2, arg3, arg4);
    }

    public static void info(String template, Object arg1, Object arg2, Object arg3) {
        NoLogging$.MODULE$.info(template, arg1, arg2, arg3);
    }

    public static void info(String template, Object arg1, Object arg2) {
        NoLogging$.MODULE$.info(template, arg1, arg2);
    }

    public static void info(String template, Object arg1) {
        NoLogging$.MODULE$.info(template, arg1);
    }

    public static void info(String message) {
        NoLogging$.MODULE$.info(message);
    }

    public static void warning(String template, Object arg1, Object arg2, Object arg3, Object arg4) {
        NoLogging$.MODULE$.warning(template, arg1, arg2, arg3, arg4);
    }

    public static void warning(String template, Object arg1, Object arg2, Object arg3) {
        NoLogging$.MODULE$.warning(template, arg1, arg2, arg3);
    }

    public static void warning(String template, Object arg1, Object arg2) {
        NoLogging$.MODULE$.warning(template, arg1, arg2);
    }

    public static void warning(String template, Object arg1) {
        NoLogging$.MODULE$.warning(template, arg1);
    }

    public static void warning(String message) {
        NoLogging$.MODULE$.warning(message);
    }

    public static void warning(Throwable cause, String template, Object arg1, Object arg2, Object arg3, Object arg4) {
        NoLogging$.MODULE$.warning(cause, template, arg1, arg2, arg3, arg4);
    }

    public static void warning(Throwable cause, String template, Object arg1, Object arg2, Object arg3) {
        NoLogging$.MODULE$.warning(cause, template, arg1, arg2, arg3);
    }

    public static void warning(Throwable cause, String template, Object arg1, Object arg2) {
        NoLogging$.MODULE$.warning(cause, template, arg1, arg2);
    }

    public static void warning(Throwable cause, String template, Object arg1) {
        NoLogging$.MODULE$.warning(cause, template, arg1);
    }

    public static void warning(Throwable cause, String message) {
        NoLogging$.MODULE$.warning(cause, message);
    }

    public static void error(String template, Object arg1, Object arg2, Object arg3, Object arg4) {
        NoLogging$.MODULE$.error(template, arg1, arg2, arg3, arg4);
    }

    public static void error(String template, Object arg1, Object arg2, Object arg3) {
        NoLogging$.MODULE$.error(template, arg1, arg2, arg3);
    }

    public static void error(String template, Object arg1, Object arg2) {
        NoLogging$.MODULE$.error(template, arg1, arg2);
    }

    public static void error(String template, Object arg1) {
        NoLogging$.MODULE$.error(template, arg1);
    }

    public static void error(String message) {
        NoLogging$.MODULE$.error(message);
    }

    public static void error(Throwable cause, String template, Object arg1, Object arg2, Object arg3, Object arg4) {
        NoLogging$.MODULE$.error(cause, template, arg1, arg2, arg3, arg4);
    }

    public static void error(Throwable cause, String template, Object arg1, Object arg2, Object arg3) {
        NoLogging$.MODULE$.error(cause, template, arg1, arg2, arg3);
    }

    public static void error(Throwable cause, String template, Object arg1, Object arg2) {
        NoLogging$.MODULE$.error(cause, template, arg1, arg2);
    }

    public static void error(Throwable cause, String template, Object arg1) {
        NoLogging$.MODULE$.error(cause, template, arg1);
    }

    public static void error(Throwable cause, String message) {
        NoLogging$.MODULE$.error(cause, message);
    }

    public static Map<String, Object> mdc() {
        return NoLogging$.MODULE$.mdc();
    }
}

