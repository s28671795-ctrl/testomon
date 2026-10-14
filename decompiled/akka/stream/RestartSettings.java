/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.stream.RestartSettings$;
import akka.util.JavaDurationConverters$;
import akka.util.JavaDurationConverters$JavaDurationOps$;
import java.time.Duration;
import java.util.function.Predicate;
import scala.Function1;
import scala.compat.java8.FunctionConverters.package$;
import scala.compat.java8.functionConverterImpls.RichPredicateAsFunction1$;
import scala.concurrent.duration.FiniteDuration;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\tUc\u0001\u0002\u001f>\u0005\tC\u0001\"\u0013\u0001\u0003\u0006\u0004%\tA\u0013\u0005\t'\u0002\u0011\t\u0011)A\u0005\u0017\"AA\u000b\u0001BC\u0002\u0013\u0005!\n\u0003\u0005V\u0001\t\u0005\t\u0015!\u0003L\u0011!1\u0006A!b\u0001\n\u00039\u0006\u0002C.\u0001\u0005\u0003\u0005\u000b\u0011\u0002-\t\u0011q\u0003!Q1A\u0005\u0002uC\u0001\"\u0019\u0001\u0003\u0002\u0003\u0006IA\u0018\u0005\tE\u0002\u0011)\u0019!C\u0001\u0015\"A1\r\u0001B\u0001B\u0003%1\n\u0003\u0005e\u0001\t\u0015\r\u0011\"\u0001f\u0011%\t9\n\u0001B\u0001B\u0003%a\r\u0003\u0006\u0002\u001a\u0002\u0011)\u0019!C\u0001\u00037C!\"a/\u0001\u0005\u0003\u0005\u000b\u0011BAO\u0011\u0019a\u0007\u0001\"\u0003\u0002>\"9\u0011Q\u001a\u0001\u0005\u0002\u0005=\u0007bBAg\u0001\u0011\u0005\u0011Q\u001b\u0005\b\u00033\u0004A\u0011AAn\u0011\u001d\tI\u000e\u0001C\u0001\u0003?Dq!a9\u0001\t\u0003\t)\u000fC\u0004\u0002j\u0002!\t!a;\t\u000f\u0005%\b\u0001\"\u0001\u0002v\"9\u00111 \u0001\u0005\u0002\u0005u\bb\u0002B\t\u0001\u0011\u0005!1\u0003\u0005\b\u00053\u0001A\u0011\tB\u000e\u0011\u001d\tI\u0006\u0001C\u0005\u0005[A\u0011\"a\u0019\u0001#\u0003%IA!\u0010\t\u0013\u0005m\u0004!%A\u0005\n\tu\u0002\"CA?\u0001E\u0005I\u0011\u0002B!\u0011%\u0011)\u0005AI\u0001\n\u0013\ty\bC\u0005\u0003H\u0001\t\n\u0011\"\u0003\u0003>!I!\u0011\n\u0001\u0012\u0002\u0013%!1\n\u0005\n\u0005\u001f\u0002\u0011\u0013!C\u0005\u0005#:Q![\u001f\t\u0002)4Q\u0001P\u001f\t\u0002-DQ\u0001\\\u0012\u0005\u00025DQA\\\u0012\u0005\u0002=DQ\u0001^\u0012\u0005\u0002UDq!a\u0001$\t\u0003\t)aB\u0004\u0002\u0006\u000eB\t!a\"\u0007\u000f\u0005-1\u0005#\u0001\u0002\n\"1A.\u000bC\u0001\u0003\u0017C!\"!$*\u0005\u0004%\taPAH\u0011!\t\t*\u000bQ\u0001\n\u0005\u001d\u0001B\u00028*\t\u0003\t\u0019J\u0002\u0004\u0002\f\r\u0012\u0011Q\u0002\u0005\u000b\u0003\u001fq#Q1A\u0005\u0002\u0005E\u0001BCA\u001a]\t\u0005\t\u0015!\u0003\u0002\u0014!Q\u0011Q\u0007\u0018\u0003\u0006\u0004%\t!!\u0005\t\u0015\u0005]bF!A!\u0002\u0013\t\u0019\u0002C\u0005\u0002:9\u0012)\u0019!C\u0001;\"I\u00111\b\u0018\u0003\u0002\u0003\u0006IA\u0018\u0005\u0007Y:\"\t!!\u0010\t\u000f\u0005\u0015c\u0006\"\u0001\u0002H!9\u0011Q\n\u0018\u0005\u0002\u0005=\u0003bBA-]\u0011%\u00111\f\u0005\n\u0003Gr\u0013\u0013!C\u0005\u0003KB\u0011\"a\u001f/#\u0003%I!!\u001a\t\u0013\u0005ud&%A\u0005\n\u0005}$a\u0004*fgR\f'\u000f^*fiRLgnZ:\u000b\u0005yz\u0014AB:ue\u0016\fWNC\u0001A\u0003\u0011\t7n[1\u0004\u0001M\u0011\u0001a\u0011\t\u0003\t\u001ek\u0011!\u0012\u0006\u0002\r\u0006)1oY1mC&\u0011\u0001*\u0012\u0002\u0007\u0003:L(+\u001a4\u0002\u00155LgNQ1dW>4g-F\u0001L!\ta\u0015+D\u0001N\u0015\tqu*\u0001\u0005ekJ\fG/[8o\u0015\t\u0001V)\u0001\u0006d_:\u001cWO\u001d:f]RL!AU'\u0003\u001d\u0019Kg.\u001b;f\tV\u0014\u0018\r^5p]\u0006YQ.\u001b8CC\u000e\\wN\u001a4!\u0003)i\u0017\r\u001f\"bG.|gMZ\u0001\f[\u0006D()Y2l_\u001a4\u0007%\u0001\u0007sC:$w.\u001c$bGR|'/F\u0001Y!\t!\u0015,\u0003\u0002[\u000b\n1Ai\\;cY\u0016\fQB]1oI>lg)Y2u_J\u0004\u0013aC7bqJ+7\u000f^1siN,\u0012A\u0018\t\u0003\t~K!\u0001Y#\u0003\u0007%sG/\u0001\u0007nCb\u0014Vm\u001d;beR\u001c\b%A\tnCb\u0014Vm\u001d;beR\u001cx+\u001b;iS:\f!#\\1y%\u0016\u001cH/\u0019:ug^KG\u000f[5oA\u0005YAn\\4TKR$\u0018N\\4t+\u00051\u0007CA4/\u001d\tA'%D\u0001>\u0003=\u0011Vm\u001d;beR\u001cV\r\u001e;j]\u001e\u001c\bC\u00015$'\t\u00193)\u0001\u0004=S:LGO\u0010\u000b\u0002U\u0006)\u0011\r\u001d9msR!\u0001/\u001d:t!\tA\u0007\u0001C\u0003JK\u0001\u00071\nC\u0003UK\u0001\u00071\nC\u0003WK\u0001\u0007\u0001,\u0001\u0004de\u0016\fG/\u001a\u000b\u0006aZ|\u0018\u0011\u0001\u0005\u0006\u0013\u001a\u0002\ra\u001e\t\u0003qvl\u0011!\u001f\u0006\u0003un\fA\u0001^5nK*\tA0\u0001\u0003kCZ\f\u0017B\u0001@z\u0005!!UO]1uS>t\u0007\"\u0002+'\u0001\u00049\b\"\u0002,'\u0001\u0004A\u0016!E2sK\u0006$X\rT8h'\u0016$H/\u001b8hgR!\u0011qAAB!\r\tIAL\u0007\u0002G\tYAj\\4TKR$\u0018N\\4t'\tq3)\u0001\u0005m_\u001edUM^3m+\t\t\u0019\u0002\u0005\u0003\u0002\u0016\u00055b\u0002BA\f\u0003OqA!!\u0007\u0002$9!\u00111DA\u0011\u001b\t\tiBC\u0002\u0002 \u0005\u000ba\u0001\u0010:p_Rt\u0014\"\u0001!\n\u0007\u0005\u0015r(A\u0003fm\u0016tG/\u0003\u0003\u0002*\u0005-\u0012a\u0002'pO\u001eLgn\u001a\u0006\u0004\u0003Ky\u0014\u0002BA\u0018\u0003c\u0011\u0001\u0002T8h\u0019\u00164X\r\u001c\u0006\u0005\u0003S\tY#A\u0005m_\u001edUM^3mA\u0005\u00012M]5uS\u000e\fG\u000eT8h\u0019\u00164X\r\\\u0001\u0012GJLG/[2bY2{w\rT3wK2\u0004\u0013!F2sSRL7-\u00197M_\u001edUM^3m\u0003\u001a$XM]\u0001\u0017GJLG/[2bY2{w\rT3wK2\fe\r^3sAQA\u0011qAA \u0003\u0003\n\u0019\u0005C\u0004\u0002\u0010U\u0002\r!a\u0005\t\u000f\u0005UR\u00071\u0001\u0002\u0014!1\u0011\u0011H\u001bA\u0002y\u000bAb^5uQ2{w\rT3wK2$B!a\u0002\u0002J!9\u00111\n\u001cA\u0002\u0005M\u0011!\u00027fm\u0016d\u0017\u0001F<ji\"\u001c%/\u001b;jG\u0006dGj\\4MKZ,G\u000e\u0006\u0004\u0002\b\u0005E\u0013Q\u000b\u0005\b\u0003':\u0004\u0019AA\n\u00035\u0019'/\u001b;jG\u0006dG*\u001a<fY\"1\u0011qK\u001cA\u0002y\u000b1\"\u00194uKJ,%O]8sg\u0006!1m\u001c9z)!\t9!!\u0018\u0002`\u0005\u0005\u0004\"CA\bqA\u0005\t\u0019AA\n\u0011%\t)\u0004\u000fI\u0001\u0002\u0004\t\u0019\u0002\u0003\u0005\u0002:a\u0002\n\u00111\u0001_\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIE*\"!a\u001a+\t\u0005M\u0011\u0011N\u0016\u0003\u0003W\u0002B!!\u001c\u0002x5\u0011\u0011q\u000e\u0006\u0005\u0003c\n\u0019(A\u0005v]\u000eDWmY6fI*\u0019\u0011QO#\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0003\u0002z\u0005=$!E;oG\",7m[3e-\u0006\u0014\u0018.\u00198dK\u0006q1m\u001c9zI\u0011,g-Y;mi\u0012\u0012\u0014AD2paf$C-\u001a4bk2$HeM\u000b\u0003\u0003\u0003S3AXA5\u0011\u001d\tya\na\u0001\u0003'\t1\u0002T8h'\u0016$H/\u001b8hgB\u0019\u0011\u0011B\u0015\u0014\u0005%\u001aECAAD\u0003=!WMZ1vYR\u001cV\r\u001e;j]\u001e\u001cXCAA\u0004\u0003A!WMZ1vYR\u001cV\r\u001e;j]\u001e\u001c\b\u0005\u0006\u0003\u0002\b\u0005U\u0005bBA\b[\u0001\u0007\u00111C\u0001\rY><7+\u001a;uS:<7\u000fI\u0001\ne\u0016\u001cH/\u0019:u\u001f:,\"!!(\u0011\u000f\u0011\u000by*a)\u00026&\u0019\u0011\u0011U#\u0003\u0013\u0019+hn\u0019;j_:\f\u0004\u0003BAS\u0003_sA!a*\u0002,:!\u00111DAU\u0013\u00051\u0015bAAW\u000b\u00069\u0001/Y2lC\u001e,\u0017\u0002BAY\u0003g\u0013\u0011\u0002\u00165s_^\f'\r\\3\u000b\u0007\u00055V\tE\u0002E\u0003oK1!!/F\u0005\u001d\u0011un\u001c7fC:\f!B]3ti\u0006\u0014Ho\u00148!)=\u0001\u0018qXAa\u0003\u0007\f)-a2\u0002J\u0006-\u0007\"B%\u0010\u0001\u0004Y\u0005\"\u0002+\u0010\u0001\u0004Y\u0005\"\u0002,\u0010\u0001\u0004A\u0006\"\u0002/\u0010\u0001\u0004q\u0006\"\u00022\u0010\u0001\u0004Y\u0005\"\u00023\u0010\u0001\u00041\u0007bBAM\u001f\u0001\u0007\u0011QT\u0001\u000fo&$\b.T5o\u0005\u0006\u001c7n\u001c4g)\r\u0001\u0018\u0011\u001b\u0005\u0007\u0003'\u0004\u0002\u0019A&\u0002\u000bY\fG.^3\u0015\u0007A\f9\u000e\u0003\u0004\u0002TF\u0001\ra^\u0001\u000fo&$\b.T1y\u0005\u0006\u001c7n\u001c4g)\r\u0001\u0018Q\u001c\u0005\u0007\u0003'\u0014\u0002\u0019A&\u0015\u0007A\f\t\u000f\u0003\u0004\u0002TN\u0001\ra^\u0001\u0011o&$\bNU1oI>lg)Y2u_J$2\u0001]At\u0011\u0019\t\u0019\u000e\u0006a\u00011\u0006yq/\u001b;i\u001b\u0006D(+Z:uCJ$8\u000fF\u0003q\u0003[\f\t\u0010\u0003\u0004\u0002pV\u0001\rAX\u0001\u0006G>,h\u000e\u001e\u0005\u0007\u0003g,\u0002\u0019A&\u0002\r]LG\u000f[5o)\u0015\u0001\u0018q_A}\u0011\u0019\tyO\u0006a\u0001=\"1\u00111\u001f\fA\u0002]\fQb^5uQJ+7\u000f^1si>sGc\u00019\u0002\u0000\"9\u0011\u0011T\fA\u0002\t\u0005\u0001C\u0002B\u0002\u0005\u001b\t\u0019+\u0004\u0002\u0003\u0006)!!q\u0001B\u0005\u0003!1WO\\2uS>t'b\u0001B\u0006w\u0006!Q\u000f^5m\u0013\u0011\u0011yA!\u0002\u0003\u0013A\u0013X\rZ5dCR,\u0017aD<ji\"dunZ*fiRLgnZ:\u0015\u0007A\u0014)\u0002\u0003\u0004\u0003\u0018a\u0001\rAZ\u0001\u000f]\u0016<Hj\\4TKR$\u0018N\\4t\u0003!!xn\u0015;sS:<GC\u0001B\u000f!\u0011\u0011yBa\n\u000f\t\t\u0005\"1\u0005\t\u0004\u00037)\u0015b\u0001B\u0013\u000b\u00061\u0001K]3eK\u001aLAA!\u000b\u0003,\t11\u000b\u001e:j]\u001eT1A!\nF)=\u0001(q\u0006B\u0019\u0005g\u0011)Da\u000e\u0003:\tm\u0002bB%\u001b!\u0003\u0005\ra\u0013\u0005\b)j\u0001\n\u00111\u0001L\u0011\u001d1&\u0004%AA\u0002aCq\u0001\u0018\u000e\u0011\u0002\u0003\u0007a\fC\u0004c5A\u0005\t\u0019A&\t\u000f\u0011T\u0002\u0013!a\u0001M\"I\u0011\u0011\u0014\u000e\u0011\u0002\u0003\u0007\u0011QT\u000b\u0003\u0005\u007fQ3aSA5+\t\u0011\u0019EK\u0002Y\u0003S\nabY8qs\u0012\"WMZ1vYR$C'\u0001\bd_BLH\u0005Z3gCVdG\u000fJ\u001b\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%mU\u0011!Q\n\u0016\u0004M\u0006%\u0014AD2paf$C-\u001a4bk2$HeN\u000b\u0003\u0005'RC!!(\u0002j\u0001")
public final class RestartSettings {
    private final FiniteDuration minBackoff;
    private final FiniteDuration maxBackoff;
    private final double randomFactor;
    private final int maxRestarts;
    private final FiniteDuration maxRestartsWithin;
    private final LogSettings logSettings;
    private final Function1<Throwable, Object> restartOn;

    public static LogSettings createLogSettings(int logLevel) {
        return RestartSettings$.MODULE$.createLogSettings(logLevel);
    }

    public static RestartSettings create(Duration minBackoff, Duration maxBackoff, double randomFactor) {
        return RestartSettings$.MODULE$.create(minBackoff, maxBackoff, randomFactor);
    }

    public static RestartSettings apply(FiniteDuration minBackoff, FiniteDuration maxBackoff, double randomFactor) {
        return RestartSettings$.MODULE$.apply(minBackoff, maxBackoff, randomFactor);
    }

    public FiniteDuration minBackoff() {
        return this.minBackoff;
    }

    public FiniteDuration maxBackoff() {
        return this.maxBackoff;
    }

    public double randomFactor() {
        return this.randomFactor;
    }

    public int maxRestarts() {
        return this.maxRestarts;
    }

    public FiniteDuration maxRestartsWithin() {
        return this.maxRestartsWithin;
    }

    public LogSettings logSettings() {
        return this.logSettings;
    }

    public Function1<Throwable, Object> restartOn() {
        return this.restartOn;
    }

    public RestartSettings withMinBackoff(FiniteDuration value) {
        return this.copy(value, this.copy$default$2(), this.copy$default$3(), this.copy$default$4(), this.copy$default$5(), this.copy$default$6(), this.copy$default$7());
    }

    public RestartSettings withMinBackoff(Duration value) {
        return this.copy(JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(value)), this.copy$default$2(), this.copy$default$3(), this.copy$default$4(), this.copy$default$5(), this.copy$default$6(), this.copy$default$7());
    }

    public RestartSettings withMaxBackoff(FiniteDuration value) {
        FiniteDuration x$1 = value;
        FiniteDuration x$2 = this.copy$default$1();
        double x$3 = this.copy$default$3();
        int x$4 = this.copy$default$4();
        FiniteDuration x$5 = this.copy$default$5();
        LogSettings x$6 = this.copy$default$6();
        Function1<Throwable, Object> x$7 = this.copy$default$7();
        return this.copy(x$2, x$1, x$3, x$4, x$5, x$6, x$7);
    }

    public RestartSettings withMaxBackoff(Duration value) {
        FiniteDuration x$1 = JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(value));
        FiniteDuration x$2 = this.copy$default$1();
        double x$3 = this.copy$default$3();
        int x$4 = this.copy$default$4();
        FiniteDuration x$5 = this.copy$default$5();
        LogSettings x$6 = this.copy$default$6();
        Function1<Throwable, Object> x$7 = this.copy$default$7();
        return this.copy(x$2, x$1, x$3, x$4, x$5, x$6, x$7);
    }

    public RestartSettings withRandomFactor(double value) {
        double x$1 = value;
        FiniteDuration x$2 = this.copy$default$1();
        FiniteDuration x$3 = this.copy$default$2();
        int x$4 = this.copy$default$4();
        FiniteDuration x$5 = this.copy$default$5();
        LogSettings x$6 = this.copy$default$6();
        Function1<Throwable, Object> x$7 = this.copy$default$7();
        return this.copy(x$2, x$3, x$1, x$4, x$5, x$6, x$7);
    }

    public RestartSettings withMaxRestarts(int count, FiniteDuration within) {
        int x$1 = count;
        FiniteDuration x$2 = within;
        FiniteDuration x$3 = this.copy$default$1();
        FiniteDuration x$4 = this.copy$default$2();
        double x$5 = this.copy$default$3();
        LogSettings x$6 = this.copy$default$6();
        Function1<Throwable, Object> x$7 = this.copy$default$7();
        return this.copy(x$3, x$4, x$5, x$1, x$2, x$6, x$7);
    }

    public RestartSettings withMaxRestarts(int count, Duration within) {
        int x$1 = count;
        FiniteDuration x$2 = JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(within));
        FiniteDuration x$3 = this.copy$default$1();
        FiniteDuration x$4 = this.copy$default$2();
        double x$5 = this.copy$default$3();
        LogSettings x$6 = this.copy$default$6();
        Function1<Throwable, Object> x$7 = this.copy$default$7();
        return this.copy(x$3, x$4, x$5, x$1, x$2, x$6, x$7);
    }

    public RestartSettings withRestartOn(Predicate<Throwable> restartOn) {
        Function1<Throwable, Object> x$1 = RichPredicateAsFunction1$.MODULE$.asScala$extension(package$.MODULE$.enrichAsScalaFromPredicate(restartOn));
        FiniteDuration x$2 = this.copy$default$1();
        FiniteDuration x$3 = this.copy$default$2();
        double x$4 = this.copy$default$3();
        int x$5 = this.copy$default$4();
        FiniteDuration x$6 = this.copy$default$5();
        LogSettings x$7 = this.copy$default$6();
        return this.copy(x$2, x$3, x$4, x$5, x$6, x$7, x$1);
    }

    public RestartSettings withLogSettings(LogSettings newLogSettings) {
        LogSettings x$1 = newLogSettings;
        FiniteDuration x$2 = this.copy$default$1();
        FiniteDuration x$3 = this.copy$default$2();
        double x$4 = this.copy$default$3();
        int x$5 = this.copy$default$4();
        FiniteDuration x$6 = this.copy$default$5();
        Function1<Throwable, Object> x$7 = this.copy$default$7();
        return this.copy(x$2, x$3, x$4, x$5, x$6, x$1, x$7);
    }

    public String toString() {
        return new StringBuilder(86).append("RestartSettings(").append("minBackoff=").append(this.minBackoff()).append(",").append("maxBackoff=").append(this.maxBackoff()).append(",").append("randomFactor=").append(this.randomFactor()).append(",").append("maxRestarts=").append(this.maxRestarts()).append(",").append("maxRestartsWithin=").append(this.maxRestartsWithin()).append(")").toString();
    }

    private RestartSettings copy(FiniteDuration minBackoff, FiniteDuration maxBackoff, double randomFactor, int maxRestarts, FiniteDuration maxRestartsWithin, LogSettings logSettings, Function1<Throwable, Object> restartOn) {
        return new RestartSettings(minBackoff, maxBackoff, randomFactor, maxRestarts, maxRestartsWithin, logSettings, restartOn);
    }

    private FiniteDuration copy$default$1() {
        return this.minBackoff();
    }

    private FiniteDuration copy$default$2() {
        return this.maxBackoff();
    }

    private double copy$default$3() {
        return this.randomFactor();
    }

    private int copy$default$4() {
        return this.maxRestarts();
    }

    private FiniteDuration copy$default$5() {
        return this.maxRestartsWithin();
    }

    private LogSettings copy$default$6() {
        return this.logSettings();
    }

    private Function1<Throwable, Object> copy$default$7() {
        return this.restartOn();
    }

    public RestartSettings(FiniteDuration minBackoff, FiniteDuration maxBackoff, double randomFactor, int maxRestarts, FiniteDuration maxRestartsWithin, LogSettings logSettings, Function1<Throwable, Object> restartOn) {
        this.minBackoff = minBackoff;
        this.maxBackoff = maxBackoff;
        this.randomFactor = randomFactor;
        this.maxRestarts = maxRestarts;
        this.maxRestartsWithin = maxRestartsWithin;
        this.logSettings = logSettings;
        this.restartOn = restartOn;
    }

    public static final class LogSettings {
        private final int logLevel;
        private final int criticalLogLevel;
        private final int criticalLogLevelAfter;

        public int logLevel() {
            return this.logLevel;
        }

        public int criticalLogLevel() {
            return this.criticalLogLevel;
        }

        public int criticalLogLevelAfter() {
            return this.criticalLogLevelAfter;
        }

        public LogSettings withLogLevel(int level) {
            return this.akka$stream$RestartSettings$LogSettings$$copy(level, this.akka$stream$RestartSettings$LogSettings$$copy$default$2(), this.akka$stream$RestartSettings$LogSettings$$copy$default$3());
        }

        public LogSettings withCriticalLogLevel(int criticalLevel, int afterErrors) {
            int x$1 = criticalLevel;
            int x$2 = afterErrors;
            int x$3 = this.copy$default$1();
            return this.akka$stream$RestartSettings$LogSettings$$copy(x$3, x$1, x$2);
        }

        public LogSettings akka$stream$RestartSettings$LogSettings$$copy(int logLevel, int criticalLogLevel, int criticalLogLevelAfter) {
            return new LogSettings(logLevel, criticalLogLevel, criticalLogLevelAfter);
        }

        private int copy$default$1() {
            return this.logLevel();
        }

        public int akka$stream$RestartSettings$LogSettings$$copy$default$2() {
            return this.criticalLogLevel();
        }

        public int akka$stream$RestartSettings$LogSettings$$copy$default$3() {
            return this.criticalLogLevelAfter();
        }

        public LogSettings(int logLevel, int criticalLogLevel, int criticalLogLevelAfter) {
            this.logLevel = logLevel;
            this.criticalLogLevel = criticalLogLevel;
            this.criticalLogLevelAfter = criticalLogLevelAfter;
        }
    }
}

