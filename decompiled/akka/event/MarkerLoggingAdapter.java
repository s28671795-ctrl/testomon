/*
 * Decompiled with CFR 0.152.
 */
package akka.event;

import akka.event.BusLogging;
import akka.event.DefaultLoggingFilter;
import akka.event.LogMarker;
import akka.event.Logging;
import akka.event.Logging$;
import akka.event.Logging$Debug$;
import akka.event.Logging$Error$;
import akka.event.Logging$Info$;
import akka.event.Logging$Warning$;
import akka.event.LoggingBus;
import akka.event.LoggingFilter;
import akka.event.LoggingFilterWithMarker;
import akka.event.LoggingFilterWithMarker$;
import java.io.Serializable;
import scala.Function0;
import scala.Function1;
import scala.Predef$;
import scala.collection.ArrayOps$;
import scala.reflect.ClassTag$;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;

@ScalaSignature(bytes="\u0006\u0005\t}d\u0001B\u0016-\u0001EB\u0001B\u000e\u0001\u0003\u0006\u0004%\te\u000e\u0005\nw\u0001\u0011\t\u0011)A\u0005qqB\u0001\"\u0010\u0001\u0003\u0006\u0004%\tE\u0010\u0005\n\u0019\u0002\u0011\t\u0011)A\u0005\u007f5C\u0001B\u0014\u0001\u0003\u0006\u0004%\te\u0014\u0005\n1\u0002\u0011\t\u0011)A\u0005!fC\u0001B\u0019\u0001\u0003\u0002\u0003\u0006Ia\u0019\u0005\u0006M\u0002!\ta\u001a\u0005\u0006M\u0002!\t!\u001d\u0005\bu\u0002\u0011\r\u0011\"\u0001|\u0011\u0019y\b\u0001)A\u0005y\"9\u0011\u0011\u0001\u0001\u0005\u0002\u0005\r\u0001bBA\u000b\u0001\u0011\u0005\u0011q\u0003\u0005\b\u00037\u0001A\u0011AA\u000f\u0011\u001d\t\t\u0003\u0001C\u0001\u0003GAq!a\n\u0001\t\u0003\tI\u0003C\u0004\u0002(\u0001!\t!!\u0014\t\u000f\u0005\u001d\u0002\u0001\"\u0001\u0002\\!9\u0011q\u0005\u0001\u0005\u0002\u0005%\u0004bBA\u0014\u0001\u0011\u0005\u0011\u0011\u0010\u0005\b\u0003O\u0001A\u0011AAF\u0011\u001d\t9\u0003\u0001C\u0001\u0003#Cq!a\n\u0001\t\u0003\tI\nC\u0004\u0002(\u0001!\t!a)\t\u000f\u0005\u001d\u0002\u0001\"\u0001\u00020\"9\u0011Q\u0018\u0001\u0005\u0002\u0005}\u0006bBA_\u0001\u0011\u0005\u0011Q\u0019\u0005\b\u0003{\u0003A\u0011AAg\u0011\u001d\ti\f\u0001C\u0001\u0003/Dq!!0\u0001\t\u0003\t\u0019\u000fC\u0004\u0002r\u0002!\t!a=\t\u000f\u0005E\b\u0001\"\u0001\u0002z\"9\u0011\u0011\u001f\u0001\u0005\u0002\t\u0005\u0001bBAy\u0001\u0011\u0005!1\u0002\u0005\b\u0003c\u0004A\u0011\u0001B\f\u0011\u001d\u0011)\u0003\u0001C\u0001\u0005OAqA!\n\u0001\t\u0003\u0011i\u0003C\u0004\u0003&\u0001!\tA!\u000e\t\u000f\t\u0015\u0002\u0001\"\u0001\u0003@!9!Q\u0005\u0001\u0005\u0002\t-\u0003b\u0002B-\u0001\u0011\u0005!1\f\u0005\b\u0005g\u0002A\u0011\u0002B;\u0005Qi\u0015M]6fe2{wmZ5oO\u0006#\u0017\r\u001d;fe*\u0011QFL\u0001\u0006KZ,g\u000e\u001e\u0006\u0002_\u0005!\u0011m[6b\u0007\u0001\u0019\"\u0001\u0001\u001a\u0011\u0005M\"T\"\u0001\u0017\n\u0005Ub#A\u0003\"vg2{wmZ5oO\u0006\u0019!-^:\u0016\u0003a\u0002\"aM\u001d\n\u0005ib#A\u0003'pO\u001eLgn\u001a\"vg\u0006!!-^:!\u0013\t1D'A\u0005m_\u001e\u001cv.\u001e:dKV\tq\b\u0005\u0002A\u0013:\u0011\u0011i\u0012\t\u0003\u0005\u0016k\u0011a\u0011\u0006\u0003\tB\na\u0001\u0010:p_Rt$\"\u0001$\u0002\u000bM\u001c\u0017\r\\1\n\u0005!+\u0015A\u0002)sK\u0012,g-\u0003\u0002K\u0017\n11\u000b\u001e:j]\u001eT!\u0001S#\u0002\u00151|wmU8ve\u000e,\u0007%\u0003\u0002>i\u0005AAn\\4DY\u0006\u001c8/F\u0001Qa\t\tf\u000bE\u0002A%RK!aU&\u0003\u000b\rc\u0017m]:\u0011\u0005U3F\u0002\u0001\u0003\n/\u001a\t\t\u0011!A\u0003\u0002i\u0013Aa\u0018\u00137s\u0005IAn\\4DY\u0006\u001c8\u000fI\u0005\u0003\u001dR\n\"aW0\u0011\u0005qkV\"A#\n\u0005y+%a\u0002(pi\"Lgn\u001a\t\u00039\u0002L!!Y#\u0003\u0007\u0005s\u00170A\u0007m_\u001e<\u0017N\\4GS2$XM\u001d\t\u0003g\u0011L!!\u001a\u0017\u0003\u001b1{wmZ5oO\u001aKG\u000e^3s\u0003\u0019a\u0014N\\5u}Q)\u0001.\u001b6laB\u00111\u0007\u0001\u0005\u0006m!\u0001\r\u0001\u000f\u0005\u0006{!\u0001\ra\u0010\u0005\u0006\u001d\"\u0001\r\u0001\u001c\u0019\u0003[>\u00042\u0001\u0011*o!\t)v\u000eB\u0005XW\u0006\u0005\t\u0011!B\u00015\")!\r\u0003a\u0001GR!\u0001N]:u\u0011\u00151\u0014\u00021\u00019\u0011\u0015i\u0014\u00021\u0001@\u0011\u0015q\u0015\u00021\u0001va\t1\b\u0010E\u0002A%^\u0004\"!\u0016=\u0005\u0013e$\u0018\u0011!A\u0001\u0006\u0003Q&\u0001B0%oA\nq\u0003\\8hO&twMR5mi\u0016\u0014x+\u001b;i\u001b\u0006\u00148.\u001a:\u0016\u0003q\u0004\"aM?\n\u0005yd#a\u0006'pO\u001eLgn\u001a$jYR,'oV5uQ6\u000b'o[3s\u0003aawnZ4j]\u001e4\u0015\u000e\u001c;fe^KG\u000f['be.,'\u000fI\u0001\u000fSN,%O]8s\u000b:\f'\r\\3e)\u0011\t)!a\u0003\u0011\u0007q\u000b9!C\u0002\u0002\n\u0015\u0013qAQ8pY\u0016\fg\u000eC\u0004\u0002\u000e1\u0001\r!a\u0004\u0002\r5\f'o[3s!\r\u0019\u0014\u0011C\u0005\u0004\u0003'a#!\u0003'pO6\u000b'o[3s\u0003AI7oV1s]&tw-\u00128bE2,G\r\u0006\u0003\u0002\u0006\u0005e\u0001bBA\u0007\u001b\u0001\u0007\u0011qB\u0001\u000eSNLeNZ8F]\u0006\u0014G.\u001a3\u0015\t\u0005\u0015\u0011q\u0004\u0005\b\u0003\u001bq\u0001\u0019AA\b\u00039I7\u000fR3ck\u001e,e.\u00192mK\u0012$B!!\u0002\u0002&!9\u0011QB\bA\u0002\u0005=\u0011!B3se>\u0014H\u0003CA\u0016\u0003c\t\u0019$!\u0013\u0011\u0007q\u000bi#C\u0002\u00020\u0015\u0013A!\u00168ji\"9\u0011Q\u0002\tA\u0002\u0005=\u0001bBA\u001b!\u0001\u0007\u0011qG\u0001\u0006G\u0006,8/\u001a\t\u0005\u0003s\t\u0019E\u0004\u0003\u0002<\u0005}bb\u0001\"\u0002>%\ta)C\u0002\u0002B\u0015\u000bq\u0001]1dW\u0006<W-\u0003\u0003\u0002F\u0005\u001d#!\u0003+ie><\u0018M\u00197f\u0015\r\t\t%\u0012\u0005\u0007\u0003\u0017\u0002\u0002\u0019A \u0002\u000f5,7o]1hKRQ\u00111FA(\u0003#\n\u0019&a\u0016\t\u000f\u00055\u0011\u00031\u0001\u0002\u0010!9\u0011QG\tA\u0002\u0005]\u0002BBA+#\u0001\u0007q(\u0001\u0005uK6\u0004H.\u0019;f\u0011\u0019\tI&\u0005a\u0001?\u0006!\u0011M]42)1\tY#!\u0018\u0002`\u0005\u0005\u00141MA3\u0011\u001d\tiA\u0005a\u0001\u0003\u001fAq!!\u000e\u0013\u0001\u0004\t9\u0004\u0003\u0004\u0002VI\u0001\ra\u0010\u0005\u0007\u00033\u0012\u0002\u0019A0\t\r\u0005\u001d$\u00031\u0001`\u0003\u0011\t'o\u001a\u001a\u0015\u001d\u0005-\u00121NA7\u0003_\n\t(a\u001d\u0002v!9\u0011QB\nA\u0002\u0005=\u0001bBA\u001b'\u0001\u0007\u0011q\u0007\u0005\u0007\u0003+\u001a\u0002\u0019A \t\r\u0005e3\u00031\u0001`\u0011\u0019\t9g\u0005a\u0001?\"1\u0011qO\nA\u0002}\u000bA!\u0019:hgQ\u0001\u00121FA>\u0003{\ny(!!\u0002\u0004\u0006\u0015\u0015q\u0011\u0005\b\u0003\u001b!\u0002\u0019AA\b\u0011\u001d\t)\u0004\u0006a\u0001\u0003oAa!!\u0016\u0015\u0001\u0004y\u0004BBA-)\u0001\u0007q\f\u0003\u0004\u0002hQ\u0001\ra\u0018\u0005\u0007\u0003o\"\u0002\u0019A0\t\r\u0005%E\u00031\u0001`\u0003\u0011\t'o\u001a\u001b\u0015\r\u0005-\u0012QRAH\u0011\u001d\ti!\u0006a\u0001\u0003\u001fAa!a\u0013\u0016\u0001\u0004yD\u0003CA\u0016\u0003'\u000b)*a&\t\u000f\u00055a\u00031\u0001\u0002\u0010!1\u0011Q\u000b\fA\u0002}Ba!!\u0017\u0017\u0001\u0004yFCCA\u0016\u00037\u000bi*a(\u0002\"\"9\u0011QB\fA\u0002\u0005=\u0001BBA+/\u0001\u0007q\b\u0003\u0004\u0002Z]\u0001\ra\u0018\u0005\u0007\u0003O:\u0002\u0019A0\u0015\u0019\u0005-\u0012QUAT\u0003S\u000bY+!,\t\u000f\u00055\u0001\u00041\u0001\u0002\u0010!1\u0011Q\u000b\rA\u0002}Ba!!\u0017\u0019\u0001\u0004y\u0006BBA41\u0001\u0007q\f\u0003\u0004\u0002xa\u0001\ra\u0018\u000b\u000f\u0003W\t\t,a-\u00026\u0006]\u0016\u0011XA^\u0011\u001d\ti!\u0007a\u0001\u0003\u001fAa!!\u0016\u001a\u0001\u0004y\u0004BBA-3\u0001\u0007q\f\u0003\u0004\u0002he\u0001\ra\u0018\u0005\u0007\u0003oJ\u0002\u0019A0\t\r\u0005%\u0015\u00041\u0001`\u0003\u001d9\u0018M\u001d8j]\u001e$b!a\u000b\u0002B\u0006\r\u0007bBA\u00075\u0001\u0007\u0011q\u0002\u0005\u0007\u0003\u0017R\u0002\u0019A \u0015\u0011\u0005-\u0012qYAe\u0003\u0017Dq!!\u0004\u001c\u0001\u0004\ty\u0001\u0003\u0004\u0002Vm\u0001\ra\u0010\u0005\u0007\u00033Z\u0002\u0019A0\u0015\u0015\u0005-\u0012qZAi\u0003'\f)\u000eC\u0004\u0002\u000eq\u0001\r!a\u0004\t\r\u0005UC\u00041\u0001@\u0011\u0019\tI\u0006\ba\u0001?\"1\u0011q\r\u000fA\u0002}#B\"a\u000b\u0002Z\u0006m\u0017Q\\Ap\u0003CDq!!\u0004\u001e\u0001\u0004\ty\u0001\u0003\u0004\u0002Vu\u0001\ra\u0010\u0005\u0007\u00033j\u0002\u0019A0\t\r\u0005\u001dT\u00041\u0001`\u0011\u0019\t9(\ba\u0001?Rq\u00111FAs\u0003O\fI/a;\u0002n\u0006=\bbBA\u0007=\u0001\u0007\u0011q\u0002\u0005\u0007\u0003+r\u0002\u0019A \t\r\u0005ec\u00041\u0001`\u0011\u0019\t9G\ba\u0001?\"1\u0011q\u000f\u0010A\u0002}Ca!!#\u001f\u0001\u0004y\u0016\u0001B5oM>$b!a\u000b\u0002v\u0006]\bbBA\u0007?\u0001\u0007\u0011q\u0002\u0005\u0007\u0003\u0017z\u0002\u0019A \u0015\u0011\u0005-\u00121`A\u007f\u0003\u007fDq!!\u0004!\u0001\u0004\ty\u0001\u0003\u0004\u0002V\u0001\u0002\ra\u0010\u0005\u0007\u00033\u0002\u0003\u0019A0\u0015\u0015\u0005-\"1\u0001B\u0003\u0005\u000f\u0011I\u0001C\u0004\u0002\u000e\u0005\u0002\r!a\u0004\t\r\u0005U\u0013\u00051\u0001@\u0011\u0019\tI&\ta\u0001?\"1\u0011qM\u0011A\u0002}#B\"a\u000b\u0003\u000e\t=!\u0011\u0003B\n\u0005+Aq!!\u0004#\u0001\u0004\ty\u0001\u0003\u0004\u0002V\t\u0002\ra\u0010\u0005\u0007\u00033\u0012\u0003\u0019A0\t\r\u0005\u001d$\u00051\u0001`\u0011\u0019\t9H\ta\u0001?Rq\u00111\u0006B\r\u00057\u0011iBa\b\u0003\"\t\r\u0002bBA\u0007G\u0001\u0007\u0011q\u0002\u0005\u0007\u0003+\u001a\u0003\u0019A \t\r\u0005e3\u00051\u0001`\u0011\u0019\t9g\ta\u0001?\"1\u0011qO\u0012A\u0002}Ca!!#$\u0001\u0004y\u0016!\u00023fEV<GCBA\u0016\u0005S\u0011Y\u0003C\u0004\u0002\u000e\u0011\u0002\r!a\u0004\t\r\u0005-C\u00051\u0001@)!\tYCa\f\u00032\tM\u0002bBA\u0007K\u0001\u0007\u0011q\u0002\u0005\u0007\u0003+*\u0003\u0019A \t\r\u0005eS\u00051\u0001`))\tYCa\u000e\u0003:\tm\"Q\b\u0005\b\u0003\u001b1\u0003\u0019AA\b\u0011\u0019\t)F\na\u0001\u007f!1\u0011\u0011\f\u0014A\u0002}Ca!a\u001a'\u0001\u0004yF\u0003DA\u0016\u0005\u0003\u0012\u0019E!\u0012\u0003H\t%\u0003bBA\u0007O\u0001\u0007\u0011q\u0002\u0005\u0007\u0003+:\u0003\u0019A \t\r\u0005es\u00051\u0001`\u0011\u0019\t9g\na\u0001?\"1\u0011qO\u0014A\u0002}#b\"a\u000b\u0003N\t=#\u0011\u000bB*\u0005+\u00129\u0006C\u0004\u0002\u000e!\u0002\r!a\u0004\t\r\u0005U\u0003\u00061\u0001@\u0011\u0019\tI\u0006\u000ba\u0001?\"1\u0011q\r\u0015A\u0002}Ca!a\u001e)\u0001\u0004y\u0006BBAEQ\u0001\u0007q,A\u0002m_\u001e$\u0002\"a\u000b\u0003^\t}#\u0011\u000f\u0005\b\u0003\u001bI\u0003\u0019AA\b\u0011\u001d\u0011\t'\u000ba\u0001\u0005G\nQ\u0001\\3wK2\u0004BA!\u001a\u0003l9\u00191Ga\u001a\n\u0007\t%D&A\u0004M_\u001e<\u0017N\\4\n\t\t5$q\u000e\u0002\t\u0019><G*\u001a<fY*\u0019!\u0011\u000e\u0017\t\r\u0005-\u0013\u00061\u0001@\u0003\u001d1wN]7biF\"Ra\u0010B<\u0005wBaA!\u001f+\u0001\u0004y\u0014!\u0001;\t\r\tu$\u00061\u0001`\u0003\r\t'o\u001a")
public class MarkerLoggingAdapter
extends BusLogging {
    private final LoggingFilterWithMarker loggingFilterWithMarker;

    @Override
    public LoggingBus bus() {
        return super.bus();
    }

    @Override
    public String logSource() {
        return super.logSource();
    }

    @Override
    public Class<?> logClass() {
        return super.logClass();
    }

    public LoggingFilterWithMarker loggingFilterWithMarker() {
        return this.loggingFilterWithMarker;
    }

    public boolean isErrorEnabled(LogMarker marker) {
        return this.loggingFilterWithMarker().isErrorEnabled(this.logClass(), this.logSource(), marker);
    }

    public boolean isWarningEnabled(LogMarker marker) {
        return this.loggingFilterWithMarker().isWarningEnabled(this.logClass(), this.logSource(), marker);
    }

    public boolean isInfoEnabled(LogMarker marker) {
        return this.loggingFilterWithMarker().isInfoEnabled(this.logClass(), this.logSource(), marker);
    }

    public boolean isDebugEnabled(LogMarker marker) {
        return this.loggingFilterWithMarker().isDebugEnabled(this.logClass(), this.logSource(), marker);
    }

    public void error(LogMarker marker, Throwable cause, String message) {
        if (this.isErrorEnabled(marker)) {
            this.bus().publish(Logging$Error$.MODULE$.apply(cause, this.logSource(), this.logClass(), message, this.mdc(), marker));
            return;
        }
    }

    public void error(LogMarker marker, Throwable cause, String template, Object arg1) {
        if (this.isErrorEnabled(marker)) {
            this.bus().publish(Logging$Error$.MODULE$.apply(cause, this.logSource(), this.logClass(), this.format1(template, arg1), this.mdc(), marker));
            return;
        }
    }

    public void error(LogMarker marker, Throwable cause, String template, Object arg1, Object arg2) {
        if (this.isErrorEnabled(marker)) {
            this.bus().publish(Logging$Error$.MODULE$.apply(cause, this.logSource(), this.logClass(), this.format(template, ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{arg1, arg2})), this.mdc(), marker));
            return;
        }
    }

    public void error(LogMarker marker, Throwable cause, String template, Object arg1, Object arg2, Object arg3) {
        if (this.isErrorEnabled(marker)) {
            this.bus().publish(Logging$Error$.MODULE$.apply(cause, this.logSource(), this.logClass(), this.format(template, ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{arg1, arg2, arg3})), this.mdc(), marker));
            return;
        }
    }

    public void error(LogMarker marker, Throwable cause, String template, Object arg1, Object arg2, Object arg3, Object arg4) {
        if (this.isErrorEnabled(marker)) {
            this.bus().publish(Logging$Error$.MODULE$.apply(cause, this.logSource(), this.logClass(), this.format(template, ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{arg1, arg2, arg3, arg4})), this.mdc(), marker));
            return;
        }
    }

    public void error(LogMarker marker, String message) {
        if (this.isErrorEnabled(marker)) {
            this.bus().publish(Logging$Error$.MODULE$.apply(this.logSource(), this.logClass(), message, this.mdc(), marker));
            return;
        }
    }

    public void error(LogMarker marker, String template, Object arg1) {
        if (this.isErrorEnabled(marker)) {
            this.bus().publish(Logging$Error$.MODULE$.apply(this.logSource(), this.logClass(), this.format1(template, arg1), this.mdc(), marker));
            return;
        }
    }

    public void error(LogMarker marker, String template, Object arg1, Object arg2) {
        if (this.isErrorEnabled(marker)) {
            this.bus().publish(Logging$Error$.MODULE$.apply(this.logSource(), this.logClass(), this.format(template, ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{arg1, arg2})), this.mdc(), marker));
            return;
        }
    }

    public void error(LogMarker marker, String template, Object arg1, Object arg2, Object arg3) {
        if (this.isErrorEnabled(marker)) {
            this.bus().publish(Logging$Error$.MODULE$.apply(this.logSource(), this.logClass(), this.format(template, ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{arg1, arg2, arg3})), this.mdc(), marker));
            return;
        }
    }

    public void error(LogMarker marker, String template, Object arg1, Object arg2, Object arg3, Object arg4) {
        if (this.isErrorEnabled(marker)) {
            this.bus().publish(Logging$Error$.MODULE$.apply(this.logSource(), this.logClass(), this.format(template, ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{arg1, arg2, arg3, arg4})), this.mdc(), marker));
            return;
        }
    }

    public void warning(LogMarker marker, String message) {
        if (this.isWarningEnabled(marker)) {
            this.bus().publish(Logging$Warning$.MODULE$.apply(this.logSource(), this.logClass(), message, this.mdc(), marker));
            return;
        }
    }

    public void warning(LogMarker marker, String template, Object arg1) {
        if (this.isWarningEnabled(marker)) {
            this.bus().publish(Logging$Warning$.MODULE$.apply(this.logSource(), this.logClass(), this.format1(template, arg1), this.mdc(), marker));
            return;
        }
    }

    public void warning(LogMarker marker, String template, Object arg1, Object arg2) {
        if (this.isWarningEnabled(marker)) {
            this.bus().publish(Logging$Warning$.MODULE$.apply(this.logSource(), this.logClass(), this.format(template, ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{arg1, arg2})), this.mdc(), marker));
            return;
        }
    }

    public void warning(LogMarker marker, String template, Object arg1, Object arg2, Object arg3) {
        if (this.isWarningEnabled(marker)) {
            this.bus().publish(Logging$Warning$.MODULE$.apply(this.logSource(), this.logClass(), this.format(template, ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{arg1, arg2, arg3})), this.mdc(), marker));
            return;
        }
    }

    public void warning(LogMarker marker, String template, Object arg1, Object arg2, Object arg3, Object arg4) {
        if (this.isWarningEnabled(marker)) {
            this.bus().publish(Logging$Warning$.MODULE$.apply(this.logSource(), this.logClass(), this.format(template, ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{arg1, arg2, arg3, arg4})), this.mdc(), marker));
            return;
        }
    }

    public void info(LogMarker marker, String message) {
        if (this.isInfoEnabled(marker)) {
            this.bus().publish(Logging$Info$.MODULE$.apply(this.logSource(), this.logClass(), message, this.mdc(), marker));
            return;
        }
    }

    public void info(LogMarker marker, String template, Object arg1) {
        if (this.isInfoEnabled(marker)) {
            this.bus().publish(Logging$Info$.MODULE$.apply(this.logSource(), this.logClass(), this.format1(template, arg1), this.mdc(), marker));
            return;
        }
    }

    public void info(LogMarker marker, String template, Object arg1, Object arg2) {
        if (this.isInfoEnabled(marker)) {
            this.bus().publish(Logging$Info$.MODULE$.apply(this.logSource(), this.logClass(), this.format(template, ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{arg1, arg2})), this.mdc(), marker));
            return;
        }
    }

    public void info(LogMarker marker, String template, Object arg1, Object arg2, Object arg3) {
        if (this.isInfoEnabled(marker)) {
            this.bus().publish(Logging$Info$.MODULE$.apply(this.logSource(), this.logClass(), this.format(template, ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{arg1, arg2, arg3})), this.mdc(), marker));
            return;
        }
    }

    public void info(LogMarker marker, String template, Object arg1, Object arg2, Object arg3, Object arg4) {
        if (this.isInfoEnabled(marker)) {
            this.bus().publish(Logging$Info$.MODULE$.apply(this.logSource(), this.logClass(), this.format(template, ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{arg1, arg2, arg3, arg4})), this.mdc(), marker));
            return;
        }
    }

    public void debug(LogMarker marker, String message) {
        if (this.isDebugEnabled(marker)) {
            this.bus().publish(Logging$Debug$.MODULE$.apply(this.logSource(), this.logClass(), message, this.mdc(), marker));
            return;
        }
    }

    public void debug(LogMarker marker, String template, Object arg1) {
        if (this.isDebugEnabled(marker)) {
            this.bus().publish(Logging$Debug$.MODULE$.apply(this.logSource(), this.logClass(), this.format1(template, arg1), this.mdc(), marker));
            return;
        }
    }

    public void debug(LogMarker marker, String template, Object arg1, Object arg2) {
        if (this.isDebugEnabled(marker)) {
            this.bus().publish(Logging$Debug$.MODULE$.apply(this.logSource(), this.logClass(), this.format(template, ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{arg1, arg2})), this.mdc(), marker));
            return;
        }
    }

    public void debug(LogMarker marker, String template, Object arg1, Object arg2, Object arg3) {
        if (this.isDebugEnabled(marker)) {
            this.bus().publish(Logging$Debug$.MODULE$.apply(this.logSource(), this.logClass(), this.format(template, ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{arg1, arg2, arg3})), this.mdc(), marker));
            return;
        }
    }

    public void debug(LogMarker marker, String template, Object arg1, Object arg2, Object arg3, Object arg4) {
        if (this.isDebugEnabled(marker)) {
            this.bus().publish(Logging$Debug$.MODULE$.apply(this.logSource(), this.logClass(), this.format(template, ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{arg1, arg2, arg3, arg4})), this.mdc(), marker));
            return;
        }
    }

    public void log(LogMarker marker, int level, String message) {
        int n = level;
        if (Logging$.MODULE$.DebugLevel() == n) {
            this.debug(marker, message);
            return;
        }
        if (Logging$.MODULE$.InfoLevel() == n) {
            this.info(marker, message);
            return;
        }
        if (Logging$.MODULE$.WarningLevel() == n) {
            this.warning(marker, message);
            return;
        }
        if (Logging$.MODULE$.ErrorLevel() == n) {
            this.error(marker, message);
            return;
        }
    }

    private String format1(String t2, Object arg) {
        Object object;
        Object object2 = arg;
        if (ScalaRunTime$.MODULE$.isArray(object2, 1) && !(object = object2).getClass().getComponentType().isPrimitive()) {
            return this.format(t2, ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{ArrayOps$.MODULE$.toIndexedSeq$extension(Predef$.MODULE$.genericArrayOps(object))}));
        }
        if (ScalaRunTime$.MODULE$.isArray(object2, 1)) {
            Object object3 = object2;
            return this.format(t2, ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{ArrayOps$.MODULE$.toIndexedSeq$extension(Predef$.MODULE$.refArrayOps((Object[])ArrayOps$.MODULE$.map$extension(Predef$.MODULE$.genericArrayOps(object3), (Function1<Object, Object> & Serializable)x$13 -> x$13, ClassTag$.MODULE$.AnyRef())))}));
        }
        return this.format(t2, ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{object2}));
    }

    public MarkerLoggingAdapter(LoggingBus bus, String logSource, Class<?> logClass, LoggingFilter loggingFilter) {
        super(bus, logSource, logClass, loggingFilter);
        this.loggingFilterWithMarker = LoggingFilterWithMarker$.MODULE$.wrap(loggingFilter);
    }

    public MarkerLoggingAdapter(LoggingBus bus, String logSource, Class<?> logClass) {
        this(bus, logSource, logClass, new DefaultLoggingFilter((Function0<Logging.LogLevel>)((Object)new Serializable(bus){
            private static final long serialVersionUID = 0L;
            private final LoggingBus bus$1;

            public final int apply() {
                return this.bus$1.logLevel();
            }
            {
                this.bus$1 = bus$1;
            }
        })));
    }
}

