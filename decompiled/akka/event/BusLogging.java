/*
 * Decompiled with CFR 0.152.
 */
package akka.event;

import akka.event.DefaultLoggingFilter;
import akka.event.Logging;
import akka.event.Logging$Debug$;
import akka.event.Logging$Error$;
import akka.event.Logging$Info$;
import akka.event.Logging$Warning$;
import akka.event.LoggingAdapter;
import akka.event.LoggingBus;
import akka.event.LoggingFilter;
import java.io.Serializable;
import scala.Function0;
import scala.collection.immutable.Map;
import scala.collection.immutable.Seq;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\u0005]a\u0001\u0002\u000b\u0016\u0001iA\u0001\"\n\u0001\u0003\u0006\u0004%\tA\n\u0005\tU\u0001\u0011\t\u0011)A\u0005O!A1\u0006\u0001BC\u0002\u0013\u0005A\u0006\u0003\u00059\u0001\t\u0005\t\u0015!\u0003.\u0011!I\u0004A!b\u0001\n\u0003Q\u0004\u0002C\"\u0001\u0005\u0003\u0005\u000b\u0011B\u001e\t\u0011-\u0003!\u0011!Q\u0001\n1CQa\u0014\u0001\u0005\u0002ACQa\u0014\u0001\u0005\u0002iCQa\u0019\u0001\u0005\u0002\u0011DQ\u0001\u001b\u0001\u0005\u0002\u0011DQ!\u001b\u0001\u0005\u0002\u0011DQA\u001b\u0001\u0005\u0002\u0011DQa\u001b\u0001\u0005R1DQa\u001b\u0001\u0005RIDaa \u0001\u0005R\u0005\u0005\u0001BB@\u0001\t#\n)\u0001C\u0004\u0002\f\u0001!\t&!\u0004\t\u000f\u0005E\u0001\u0001\"\u0015\u0002\u0014\tQ!)^:M_\u001e<\u0017N\\4\u000b\u0005Y9\u0012!B3wK:$(\"\u0001\r\u0002\t\u0005\\7.Y\u0002\u0001'\r\u00011$\t\t\u00039}i\u0011!\b\u0006\u0002=\u0005)1oY1mC&\u0011\u0001%\b\u0002\u0007\u0003:L(+\u001a4\u0011\u0005\t\u001aS\"A\u000b\n\u0005\u0011*\"A\u0004'pO\u001eLgnZ!eCB$XM]\u0001\u0004EV\u001cX#A\u0014\u0011\u0005\tB\u0013BA\u0015\u0016\u0005)aunZ4j]\u001e\u0014Uo]\u0001\u0005EV\u001c\b%A\u0005m_\u001e\u001cv.\u001e:dKV\tQ\u0006\u0005\u0002/k9\u0011qf\r\t\u0003aui\u0011!\r\u0006\u0003ee\ta\u0001\u0010:p_Rt\u0014B\u0001\u001b\u001e\u0003\u0019\u0001&/\u001a3fM&\u0011ag\u000e\u0002\u0007'R\u0014\u0018N\\4\u000b\u0005Qj\u0012A\u00037pON{WO]2fA\u0005AAn\\4DY\u0006\u001c8/F\u0001<a\ta\u0014\tE\u0002/{}J!AP\u001c\u0003\u000b\rc\u0017m]:\u0011\u0005\u0001\u000bE\u0002\u0001\u0003\n\u0005\u001a\t\t\u0011!A\u0003\u0002\u0011\u0013Aa\u0018\u00138e\u0005IAn\\4DY\u0006\u001c8\u000fI\t\u0003\u000b\"\u0003\"\u0001\b$\n\u0005\u001dk\"a\u0002(pi\"Lgn\u001a\t\u00039%K!AS\u000f\u0003\u0007\u0005s\u00170A\u0007m_\u001e<\u0017N\\4GS2$XM\u001d\t\u0003E5K!AT\u000b\u0003\u001b1{wmZ5oO\u001aKG\u000e^3s\u0003\u0019a\u0014N\\5u}Q)\u0011KU*U3B\u0011!\u0005\u0001\u0005\u0006K!\u0001\ra\n\u0005\u0006W!\u0001\r!\f\u0005\u0006s!\u0001\r!\u0016\u0019\u0003-b\u00032AL\u001fX!\t\u0001\u0005\fB\u0005C)\u0006\u0005\t\u0011!B\u0001\t\")1\n\u0003a\u0001\u0019R!\u0011k\u0017/^\u0011\u0015)\u0013\u00021\u0001(\u0011\u0015Y\u0013\u00021\u0001.\u0011\u0015I\u0014\u00021\u0001_a\ty\u0016\rE\u0002/{\u0001\u0004\"\u0001Q1\u0005\u0013\tl\u0016\u0011!A\u0001\u0006\u0003!%\u0001B0%oM\na\"[:FeJ|'/\u00128bE2,G-F\u0001f!\tab-\u0003\u0002h;\t9!i\\8mK\u0006t\u0017\u0001E5t/\u0006\u0014h.\u001b8h\u000b:\f'\r\\3e\u00035I7/\u00138g_\u0016s\u0017M\u00197fI\u0006q\u0011n\u001d#fEV<WI\\1cY\u0016$\u0017a\u00038pi&4\u00170\u0012:s_J$\"!\u001c9\u0011\u0005qq\u0017BA8\u001e\u0005\u0011)f.\u001b;\t\u000bEt\u0001\u0019A\u0017\u0002\u000f5,7o]1hKR\u0019Qn\u001d@\t\u000bQ|\u0001\u0019A;\u0002\u000b\r\fWo]3\u0011\u0005Y\\hBA<z\u001d\t\u0001\u00040C\u0001\u001f\u0013\tQX$A\u0004qC\u000e\\\u0017mZ3\n\u0005ql(!\u0003+ie><\u0018M\u00197f\u0015\tQX\u0004C\u0003r\u001f\u0001\u0007Q&A\u0007o_RLg-_,be:Lgn\u001a\u000b\u0004[\u0006\r\u0001\"B9\u0011\u0001\u0004iC#B7\u0002\b\u0005%\u0001\"\u0002;\u0012\u0001\u0004)\b\"B9\u0012\u0001\u0004i\u0013A\u00038pi&4\u00170\u00138g_R\u0019Q.a\u0004\t\u000bE\u0014\u0002\u0019A\u0017\u0002\u00179|G/\u001b4z\t\u0016\u0014Wo\u001a\u000b\u0004[\u0006U\u0001\"B9\u0014\u0001\u0004i\u0003")
public class BusLogging
implements LoggingAdapter {
    private final LoggingBus bus;
    private final String logSource;
    private final Class<?> logClass;
    private final LoggingFilter loggingFilter;

    @Override
    public Map<String, Object> mdc() {
        return LoggingAdapter.mdc$(this);
    }

    @Override
    public void error(Throwable cause, String message) {
        LoggingAdapter.error$((LoggingAdapter)this, cause, message);
    }

    @Override
    public void error(Throwable cause, String template, Object arg1) {
        LoggingAdapter.error$((LoggingAdapter)this, cause, template, arg1);
    }

    @Override
    public void error(Throwable cause, String template, Object arg1, Object arg2) {
        LoggingAdapter.error$((LoggingAdapter)this, cause, template, arg1, arg2);
    }

    @Override
    public void error(Throwable cause, String template, Object arg1, Object arg2, Object arg3) {
        LoggingAdapter.error$((LoggingAdapter)this, cause, template, arg1, arg2, arg3);
    }

    @Override
    public void error(Throwable cause, String template, Object arg1, Object arg2, Object arg3, Object arg4) {
        LoggingAdapter.error$(this, cause, template, arg1, arg2, arg3, arg4);
    }

    @Override
    public void error(String message) {
        LoggingAdapter.error$(this, message);
    }

    @Override
    public void error(String template, Object arg1) {
        LoggingAdapter.error$((LoggingAdapter)this, template, arg1);
    }

    @Override
    public void error(String template, Object arg1, Object arg2) {
        LoggingAdapter.error$((LoggingAdapter)this, template, arg1, arg2);
    }

    @Override
    public void error(String template, Object arg1, Object arg2, Object arg3) {
        LoggingAdapter.error$((LoggingAdapter)this, template, arg1, arg2, arg3);
    }

    @Override
    public void error(String template, Object arg1, Object arg2, Object arg3, Object arg4) {
        LoggingAdapter.error$((LoggingAdapter)this, template, arg1, arg2, arg3, arg4);
    }

    @Override
    public void warning(Throwable cause, String message) {
        LoggingAdapter.warning$((LoggingAdapter)this, cause, message);
    }

    @Override
    public void warning(Throwable cause, String template, Object arg1) {
        LoggingAdapter.warning$((LoggingAdapter)this, cause, template, arg1);
    }

    @Override
    public void warning(Throwable cause, String template, Object arg1, Object arg2) {
        LoggingAdapter.warning$((LoggingAdapter)this, cause, template, arg1, arg2);
    }

    @Override
    public void warning(Throwable cause, String template, Object arg1, Object arg2, Object arg3) {
        LoggingAdapter.warning$((LoggingAdapter)this, cause, template, arg1, arg2, arg3);
    }

    @Override
    public void warning(Throwable cause, String template, Object arg1, Object arg2, Object arg3, Object arg4) {
        LoggingAdapter.warning$(this, cause, template, arg1, arg2, arg3, arg4);
    }

    @Override
    public void warning(String message) {
        LoggingAdapter.warning$(this, message);
    }

    @Override
    public void warning(String template, Object arg1) {
        LoggingAdapter.warning$((LoggingAdapter)this, template, arg1);
    }

    @Override
    public void warning(String template, Object arg1, Object arg2) {
        LoggingAdapter.warning$((LoggingAdapter)this, template, arg1, arg2);
    }

    @Override
    public void warning(String template, Object arg1, Object arg2, Object arg3) {
        LoggingAdapter.warning$((LoggingAdapter)this, template, arg1, arg2, arg3);
    }

    @Override
    public void warning(String template, Object arg1, Object arg2, Object arg3, Object arg4) {
        LoggingAdapter.warning$((LoggingAdapter)this, template, arg1, arg2, arg3, arg4);
    }

    @Override
    public void info(String message) {
        LoggingAdapter.info$(this, message);
    }

    @Override
    public void info(String template, Object arg1) {
        LoggingAdapter.info$(this, template, arg1);
    }

    @Override
    public void info(String template, Object arg1, Object arg2) {
        LoggingAdapter.info$(this, template, arg1, arg2);
    }

    @Override
    public void info(String template, Object arg1, Object arg2, Object arg3) {
        LoggingAdapter.info$(this, template, arg1, arg2, arg3);
    }

    @Override
    public void info(String template, Object arg1, Object arg2, Object arg3, Object arg4) {
        LoggingAdapter.info$(this, template, arg1, arg2, arg3, arg4);
    }

    @Override
    public void debug(String message) {
        LoggingAdapter.debug$(this, message);
    }

    @Override
    public void debug(String template, Object arg1) {
        LoggingAdapter.debug$(this, template, arg1);
    }

    @Override
    public void debug(String template, Object arg1, Object arg2) {
        LoggingAdapter.debug$(this, template, arg1, arg2);
    }

    @Override
    public void debug(String template, Object arg1, Object arg2, Object arg3) {
        LoggingAdapter.debug$(this, template, arg1, arg2, arg3);
    }

    @Override
    public void debug(String template, Object arg1, Object arg2, Object arg3, Object arg4) {
        LoggingAdapter.debug$(this, template, arg1, arg2, arg3, arg4);
    }

    @Override
    public void log(int level, String message) {
        LoggingAdapter.log$(this, level, message);
    }

    @Override
    public void log(int level, String template, Object arg1) {
        LoggingAdapter.log$(this, level, template, arg1);
    }

    @Override
    public void log(int level, String template, Object arg1, Object arg2) {
        LoggingAdapter.log$(this, level, template, arg1, arg2);
    }

    @Override
    public void log(int level, String template, Object arg1, Object arg2, Object arg3) {
        LoggingAdapter.log$(this, level, template, arg1, arg2, arg3);
    }

    @Override
    public void log(int level, String template, Object arg1, Object arg2, Object arg3, Object arg4) {
        LoggingAdapter.log$(this, level, template, arg1, arg2, arg3, arg4);
    }

    @Override
    public final boolean isEnabled(int level) {
        return LoggingAdapter.isEnabled$(this, level);
    }

    @Override
    public final void notifyLog(int level, String message) {
        LoggingAdapter.notifyLog$(this, level, message);
    }

    @Override
    public String format(String t2, Seq<Object> arg) {
        return LoggingAdapter.format$(this, t2, arg);
    }

    public LoggingBus bus() {
        return this.bus;
    }

    public String logSource() {
        return this.logSource;
    }

    public Class<?> logClass() {
        return this.logClass;
    }

    @Override
    public boolean isErrorEnabled() {
        return this.loggingFilter.isErrorEnabled(this.logClass(), this.logSource());
    }

    @Override
    public boolean isWarningEnabled() {
        return this.loggingFilter.isWarningEnabled(this.logClass(), this.logSource());
    }

    @Override
    public boolean isInfoEnabled() {
        return this.loggingFilter.isInfoEnabled(this.logClass(), this.logSource());
    }

    @Override
    public boolean isDebugEnabled() {
        return this.loggingFilter.isDebugEnabled(this.logClass(), this.logSource());
    }

    @Override
    public void notifyError(String message) {
        this.bus().publish(Logging$Error$.MODULE$.apply(this.logSource(), this.logClass(), (Object)message, this.mdc()));
    }

    @Override
    public void notifyError(Throwable cause, String message) {
        this.bus().publish(Logging$Error$.MODULE$.apply(cause, this.logSource(), this.logClass(), message, this.mdc()));
    }

    @Override
    public void notifyWarning(String message) {
        this.bus().publish(Logging$Warning$.MODULE$.apply(this.logSource(), this.logClass(), message, this.mdc()));
    }

    @Override
    public void notifyWarning(Throwable cause, String message) {
        this.bus().publish(Logging$Warning$.MODULE$.apply(cause, this.logSource(), this.logClass(), message, this.mdc()));
    }

    @Override
    public void notifyInfo(String message) {
        this.bus().publish(Logging$Info$.MODULE$.apply(this.logSource(), this.logClass(), message, this.mdc()));
    }

    @Override
    public void notifyDebug(String message) {
        this.bus().publish(Logging$Debug$.MODULE$.apply(this.logSource(), this.logClass(), message, this.mdc()));
    }

    public BusLogging(LoggingBus bus, String logSource, Class<?> logClass, LoggingFilter loggingFilter) {
        this.bus = bus;
        this.logSource = logSource;
        this.logClass = logClass;
        this.loggingFilter = loggingFilter;
        LoggingAdapter.$init$(this);
    }

    public BusLogging(LoggingBus bus, String logSource, Class<?> logClass) {
        this(bus, logSource, logClass, new DefaultLoggingFilter((Function0<Logging.LogLevel>)((Object)new Serializable(bus){
            private static final long serialVersionUID = 0L;
            private final LoggingBus bus$2;

            public final int apply() {
                return this.bus$2.logLevel();
            }
            {
                this.bus$2 = bus$2;
            }
        })));
    }
}

