/*
 * Decompiled with CFR 0.152.
 */
package akka.event;

import akka.event.Logging;
import akka.event.Logging$;
import java.io.Serializable;
import scala.Function1;
import scala.Predef$;
import scala.collection.ArrayOps$;
import scala.collection.immutable.Map;
import scala.collection.immutable.Seq;
import scala.reflect.ClassTag$;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;

@ScalaSignature(bytes="\u0006\u0005\t\u0005fa\u0002\u001c8!\u0003\r\t\u0001\u0010\u0005\u0006\u0007\u0002!\t\u0001R\u0003\u0005\u0011\u0002\u0001\u0011\nC\u0003Q\u0001\u0011\u0005\u0011\u000bC\u0003S\u0001\u0019\u00051\u000bC\u0003X\u0001\u0019\u00051\u000bC\u0003Y\u0001\u0019\u00051\u000bC\u0003Z\u0001\u0019\u00051\u000bC\u0003[\u0001\u0019E1\fC\u0003[\u0001\u0019E\u0011\u000eC\u0003w\u0001\u0019Eq\u000fC\u0003w\u0001\u0011E\u0011\u0010C\u0004\u0002\b\u00011\t\"!\u0003\t\u000f\u00055\u0001A\"\u0005\u0002\u0010!9\u00111\u0003\u0001\u0005\u0002\u0005U\u0001bBA\n\u0001\u0011\u0005\u00111\u0004\u0005\b\u0003'\u0001A\u0011AA\u0017\u0011\u001d\t\u0019\u0002\u0001C\u0001\u0003sAq!a\u0005\u0001\t\u0003\t9\u0005C\u0004\u0002\u0014\u0001!\t!a\u0016\t\u000f\u0005M\u0001\u0001\"\u0001\u0002\\!9\u00111\u0003\u0001\u0005\u0002\u0005\u0005\u0004bBA\n\u0001\u0011\u0005\u0011\u0011\u000e\u0005\b\u0003'\u0001A\u0011AA:\u0011\u001d\ty\b\u0001C\u0001\u0003\u0003Cq!a \u0001\t\u0003\t9\tC\u0004\u0002\u0000\u0001!\t!a$\t\u000f\u0005}\u0004\u0001\"\u0001\u0002\u001a\"9\u0011q\u0010\u0001\u0005\u0002\u0005\u0015\u0006bBA@\u0001\u0011\u0005\u00111\u0017\u0005\b\u0003\u007f\u0002A\u0011AA\\\u0011\u001d\ty\b\u0001C\u0001\u0003{Cq!a \u0001\t\u0003\t)\rC\u0004\u0002\u0000\u0001!\t!a4\t\u000f\u0005m\u0007\u0001\"\u0001\u0002^\"9\u00111\u001c\u0001\u0005\u0002\u0005\u0005\bbBAn\u0001\u0011\u0005\u0011q\u001d\u0005\b\u00037\u0004A\u0011AAx\u0011\u001d\tY\u000e\u0001C\u0001\u0003sDqA!\u0002\u0001\t\u0003\u00119\u0001C\u0004\u0003\u0006\u0001!\tAa\u0003\t\u000f\t\u0015\u0001\u0001\"\u0001\u0003\u0012!9!Q\u0001\u0001\u0005\u0002\te\u0001b\u0002B\u0003\u0001\u0011\u0005!1\u0005\u0005\b\u0005_\u0001A\u0011\u0001B\u0019\u0011\u001d\u0011y\u0003\u0001C\u0001\u0005\u007fAqAa\f\u0001\t\u0003\u00119\u0005C\u0004\u00030\u0001!\tA!\u0015\t\u000f\t=\u0002\u0001\"\u0001\u0003^!9!1\u000e\u0001\u0005\u0006\t5\u0004b\u0002B9\u0001\u0011\u0015!1\u000f\u0005\b\u0005s\u0002A\u0011\u0002B>\u0011\u001d\u0011)\t\u0001C\u0001\u0005\u000fCqAa%\u0001\t\u0013\u0011)J\u0001\bM_\u001e<\u0017N\\4BI\u0006\u0004H/\u001a:\u000b\u0005aJ\u0014!B3wK:$(\"\u0001\u001e\u0002\t\u0005\\7.Y\u0002\u0001'\t\u0001Q\b\u0005\u0002?\u00036\tqHC\u0001A\u0003\u0015\u00198-\u00197b\u0013\t\u0011uH\u0001\u0004B]f\u0014VMZ\u0001\u0007I%t\u0017\u000e\u001e\u0013\u0015\u0003\u0015\u0003\"A\u0010$\n\u0005\u001d{$\u0001B+oSR\u00141!\u0014#D!\tQeJ\u0004\u0002L\u00196\tq'\u0003\u0002No\u00059Aj\\4hS:<\u0017B\u0001%P\u0015\tiu'A\u0002nI\u000e,\u0012!S\u0001\u000fSN,%O]8s\u000b:\f'\r\\3e+\u0005!\u0006C\u0001 V\u0013\t1vHA\u0004C_>dW-\u00198\u0002!%\u001cx+\u0019:oS:<WI\\1cY\u0016$\u0017!D5t\u0013:4w.\u00128bE2,G-\u0001\bjg\u0012+'-^4F]\u0006\u0014G.\u001a3\u0002\u00179|G/\u001b4z\u000bJ\u0014xN\u001d\u000b\u0003\u000brCQ!\u0018\u0005A\u0002y\u000bq!\\3tg\u0006<W\r\u0005\u0002`M:\u0011\u0001\r\u001a\t\u0003C~j\u0011A\u0019\u0006\u0003Gn\na\u0001\u0010:p_Rt\u0014BA3@\u0003\u0019\u0001&/\u001a3fM&\u0011q\r\u001b\u0002\u0007'R\u0014\u0018N\\4\u000b\u0005\u0015|DcA#kk\")1.\u0003a\u0001Y\u0006)1-Y;tKB\u0011QN\u001d\b\u0003]Bt!!Y8\n\u0003\u0001K!!] \u0002\u000fA\f7m[1hK&\u00111\u000f\u001e\u0002\n)\"\u0014xn^1cY\u0016T!!] \t\u000buK\u0001\u0019\u00010\u0002\u001b9|G/\u001b4z/\u0006\u0014h.\u001b8h)\t)\u0005\u0010C\u0003^\u0015\u0001\u0007a\f\u0006\u0003Fu\u0006\u0015\u0001\"B6\f\u0001\u0004a\u0007F\u0001>}!\ri\u0018\u0011A\u0007\u0002}*\u0011q0O\u0001\u0005kRLG.C\u0002\u0002\u0004y\u0014a!\u001e8vg\u0016$\u0007\"B/\f\u0001\u0004q\u0016A\u00038pi&4\u00170\u00138g_R\u0019Q)a\u0003\t\u000buc\u0001\u0019\u00010\u0002\u00179|G/\u001b4z\t\u0016\u0014Wo\u001a\u000b\u0004\u000b\u0006E\u0001\"B/\u000e\u0001\u0004q\u0016!B3se>\u0014H#B#\u0002\u0018\u0005e\u0001\"B6\u000f\u0001\u0004a\u0007\"B/\u000f\u0001\u0004qFcB#\u0002\u001e\u0005}\u00111\u0005\u0005\u0006W>\u0001\r\u0001\u001c\u0005\u0007\u0003Cy\u0001\u0019\u00010\u0002\u0011Q,W\u000e\u001d7bi\u0016Dq!!\n\u0010\u0001\u0004\t9#\u0001\u0003be\u001e\f\u0004c\u0001 \u0002*%\u0019\u00111F \u0003\u0007\u0005s\u0017\u0010F\u0005F\u0003_\t\t$a\r\u00026!)1\u000e\u0005a\u0001Y\"1\u0011\u0011\u0005\tA\u0002yCq!!\n\u0011\u0001\u0004\t9\u0003C\u0004\u00028A\u0001\r!a\n\u0002\t\u0005\u0014xM\r\u000b\f\u000b\u0006m\u0012QHA \u0003\u0003\n\u0019\u0005C\u0003l#\u0001\u0007A\u000e\u0003\u0004\u0002\"E\u0001\rA\u0018\u0005\b\u0003K\t\u0002\u0019AA\u0014\u0011\u001d\t9$\u0005a\u0001\u0003OAq!!\u0012\u0012\u0001\u0004\t9#\u0001\u0003be\u001e\u001cD#D#\u0002J\u0005-\u0013QJA(\u0003#\n\u0019\u0006C\u0003l%\u0001\u0007A\u000e\u0003\u0004\u0002\"I\u0001\rA\u0018\u0005\b\u0003K\u0011\u0002\u0019AA\u0014\u0011\u001d\t9D\u0005a\u0001\u0003OAq!!\u0012\u0013\u0001\u0004\t9\u0003C\u0004\u0002VI\u0001\r!a\n\u0002\t\u0005\u0014x\r\u000e\u000b\u0004\u000b\u0006e\u0003\"B/\u0014\u0001\u0004qF#B#\u0002^\u0005}\u0003BBA\u0011)\u0001\u0007a\fC\u0004\u0002&Q\u0001\r!a\n\u0015\u000f\u0015\u000b\u0019'!\u001a\u0002h!1\u0011\u0011E\u000bA\u0002yCq!!\n\u0016\u0001\u0004\t9\u0003C\u0004\u00028U\u0001\r!a\n\u0015\u0013\u0015\u000bY'!\u001c\u0002p\u0005E\u0004BBA\u0011-\u0001\u0007a\fC\u0004\u0002&Y\u0001\r!a\n\t\u000f\u0005]b\u00031\u0001\u0002(!9\u0011Q\t\fA\u0002\u0005\u001dBcC#\u0002v\u0005]\u0014\u0011PA>\u0003{Ba!!\t\u0018\u0001\u0004q\u0006bBA\u0013/\u0001\u0007\u0011q\u0005\u0005\b\u0003o9\u0002\u0019AA\u0014\u0011\u001d\t)e\u0006a\u0001\u0003OAq!!\u0016\u0018\u0001\u0004\t9#A\u0004xCJt\u0017N\\4\u0015\u000b\u0015\u000b\u0019)!\"\t\u000b-D\u0002\u0019\u00017\t\u000buC\u0002\u0019\u00010\u0015\u000f\u0015\u000bI)a#\u0002\u000e\")1.\u0007a\u0001Y\"1\u0011\u0011E\rA\u0002yCq!!\n\u001a\u0001\u0004\t9\u0003F\u0005F\u0003#\u000b\u0019*!&\u0002\u0018\")1N\u0007a\u0001Y\"1\u0011\u0011\u0005\u000eA\u0002yCq!!\n\u001b\u0001\u0004\t9\u0003C\u0004\u00028i\u0001\r!a\n\u0015\u0017\u0015\u000bY*!(\u0002 \u0006\u0005\u00161\u0015\u0005\u0006Wn\u0001\r\u0001\u001c\u0005\u0007\u0003CY\u0002\u0019\u00010\t\u000f\u0005\u00152\u00041\u0001\u0002(!9\u0011qG\u000eA\u0002\u0005\u001d\u0002bBA#7\u0001\u0007\u0011q\u0005\u000b\u000e\u000b\u0006\u001d\u0016\u0011VAV\u0003[\u000by+!-\t\u000b-d\u0002\u0019\u00017\t\r\u0005\u0005B\u00041\u0001_\u0011\u001d\t)\u0003\ba\u0001\u0003OAq!a\u000e\u001d\u0001\u0004\t9\u0003C\u0004\u0002Fq\u0001\r!a\n\t\u000f\u0005UC\u00041\u0001\u0002(Q\u0019Q)!.\t\u000buk\u0002\u0019\u00010\u0015\u000b\u0015\u000bI,a/\t\r\u0005\u0005b\u00041\u0001_\u0011\u001d\t)C\ba\u0001\u0003O!r!RA`\u0003\u0003\f\u0019\r\u0003\u0004\u0002\"}\u0001\rA\u0018\u0005\b\u0003Ky\u0002\u0019AA\u0014\u0011\u001d\t9d\ba\u0001\u0003O!\u0012\"RAd\u0003\u0013\fY-!4\t\r\u0005\u0005\u0002\u00051\u0001_\u0011\u001d\t)\u0003\ta\u0001\u0003OAq!a\u000e!\u0001\u0004\t9\u0003C\u0004\u0002F\u0001\u0002\r!a\n\u0015\u0017\u0015\u000b\t.a5\u0002V\u0006]\u0017\u0011\u001c\u0005\u0007\u0003C\t\u0003\u0019\u00010\t\u000f\u0005\u0015\u0012\u00051\u0001\u0002(!9\u0011qG\u0011A\u0002\u0005\u001d\u0002bBA#C\u0001\u0007\u0011q\u0005\u0005\b\u0003+\n\u0003\u0019AA\u0014\u0003\u0011IgNZ8\u0015\u0007\u0015\u000by\u000eC\u0003^E\u0001\u0007a\fF\u0003F\u0003G\f)\u000f\u0003\u0004\u0002\"\r\u0002\rA\u0018\u0005\b\u0003K\u0019\u0003\u0019AA\u0014)\u001d)\u0015\u0011^Av\u0003[Da!!\t%\u0001\u0004q\u0006bBA\u0013I\u0001\u0007\u0011q\u0005\u0005\b\u0003o!\u0003\u0019AA\u0014)%)\u0015\u0011_Az\u0003k\f9\u0010\u0003\u0004\u0002\"\u0015\u0002\rA\u0018\u0005\b\u0003K)\u0003\u0019AA\u0014\u0011\u001d\t9$\na\u0001\u0003OAq!!\u0012&\u0001\u0004\t9\u0003F\u0006F\u0003w\fi0a@\u0003\u0002\t\r\u0001BBA\u0011M\u0001\u0007a\fC\u0004\u0002&\u0019\u0002\r!a\n\t\u000f\u0005]b\u00051\u0001\u0002(!9\u0011Q\t\u0014A\u0002\u0005\u001d\u0002bBA+M\u0001\u0007\u0011qE\u0001\u0006I\u0016\u0014Wo\u001a\u000b\u0004\u000b\n%\u0001\"B/(\u0001\u0004qF#B#\u0003\u000e\t=\u0001BBA\u0011Q\u0001\u0007a\fC\u0004\u0002&!\u0002\r!a\n\u0015\u000f\u0015\u0013\u0019B!\u0006\u0003\u0018!1\u0011\u0011E\u0015A\u0002yCq!!\n*\u0001\u0004\t9\u0003C\u0004\u00028%\u0002\r!a\n\u0015\u0013\u0015\u0013YB!\b\u0003 \t\u0005\u0002BBA\u0011U\u0001\u0007a\fC\u0004\u0002&)\u0002\r!a\n\t\u000f\u0005]\"\u00061\u0001\u0002(!9\u0011Q\t\u0016A\u0002\u0005\u001dBcC#\u0003&\t\u001d\"\u0011\u0006B\u0016\u0005[Aa!!\t,\u0001\u0004q\u0006bBA\u0013W\u0001\u0007\u0011q\u0005\u0005\b\u0003oY\u0003\u0019AA\u0014\u0011\u001d\t)e\u000ba\u0001\u0003OAq!!\u0016,\u0001\u0004\t9#A\u0002m_\u001e$R!\u0012B\u001a\u0005{AqA!\u000e-\u0001\u0004\u00119$A\u0003mKZ,G\u000eE\u0002K\u0005sI1Aa\u000fP\u0005!aun\u001a'fm\u0016d\u0007\"B/-\u0001\u0004qFcB#\u0003B\t\r#Q\t\u0005\b\u0005ki\u0003\u0019\u0001B\u001c\u0011\u0019\t\t#\fa\u0001=\"9\u0011QE\u0017A\u0002\u0005\u001dB#C#\u0003J\t-#Q\nB(\u0011\u001d\u0011)D\fa\u0001\u0005oAa!!\t/\u0001\u0004q\u0006bBA\u0013]\u0001\u0007\u0011q\u0005\u0005\b\u0003oq\u0003\u0019AA\u0014)-)%1\u000bB+\u0005/\u0012IFa\u0017\t\u000f\tUr\u00061\u0001\u00038!1\u0011\u0011E\u0018A\u0002yCq!!\n0\u0001\u0004\t9\u0003C\u0004\u00028=\u0002\r!a\n\t\u000f\u0005\u0015s\u00061\u0001\u0002(QiQIa\u0018\u0003b\t\r$Q\rB4\u0005SBqA!\u000e1\u0001\u0004\u00119\u0004\u0003\u0004\u0002\"A\u0002\rA\u0018\u0005\b\u0003K\u0001\u0004\u0019AA\u0014\u0011\u001d\t9\u0004\ra\u0001\u0003OAq!!\u00121\u0001\u0004\t9\u0003C\u0004\u0002VA\u0002\r!a\n\u0002\u0013%\u001cXI\\1cY\u0016$Gc\u0001+\u0003p!9!QG\u0019A\u0002\t]\u0012!\u00038pi&4\u0017\u0010T8h)\u0015)%Q\u000fB<\u0011\u001d\u0011)D\ra\u0001\u0005oAQ!\u0018\u001aA\u0002y\u000bqAZ8s[\u0006$\u0018\u0007F\u0003_\u0005{\u0012\t\t\u0003\u0004\u0003\u0000M\u0002\rAX\u0001\u0002i\"9!1Q\u001aA\u0002\u0005\u001d\u0012aA1sO\u00061am\u001c:nCR$RA\u0018BE\u0005\u0017CaAa 5\u0001\u0004q\u0006b\u0002BBi\u0001\u0007!Q\u0012\t\u0006}\t=\u0015qE\u0005\u0004\u0005#{$A\u0003\u001fsKB,\u0017\r^3e}\u0005Qam\u001c:nCRLU\u000e\u001d7\u0015\u000by\u00139J!'\t\r\t}T\u00071\u0001_\u0011\u001d\u0011\u0019)\u000ea\u0001\u00057\u0003R!\u001cBO\u0003OI1Aa(u\u0005\r\u0019V-\u001d")
public interface LoggingAdapter {
    public static /* synthetic */ Map mdc$(LoggingAdapter $this) {
        return $this.mdc();
    }

    default public Map<String, Object> mdc() {
        return Logging$.MODULE$.emptyMDC();
    }

    public boolean isErrorEnabled();

    public boolean isWarningEnabled();

    public boolean isInfoEnabled();

    public boolean isDebugEnabled();

    public void notifyError(String var1);

    public void notifyError(Throwable var1, String var2);

    public void notifyWarning(String var1);

    public static /* synthetic */ void notifyWarning$(LoggingAdapter $this, Throwable cause, String message) {
        $this.notifyWarning(cause, message);
    }

    default public void notifyWarning(Throwable cause, String message) {
        this.notifyWarning(message);
    }

    public void notifyInfo(String var1);

    public void notifyDebug(String var1);

    public static /* synthetic */ void error$(LoggingAdapter $this, Throwable cause, String message) {
        $this.error(cause, message);
    }

    default public void error(Throwable cause, String message) {
        if (this.isErrorEnabled()) {
            this.notifyError(cause, message);
            return;
        }
    }

    public static /* synthetic */ void error$(LoggingAdapter $this, Throwable cause, String template, Object arg1) {
        $this.error(cause, template, arg1);
    }

    default public void error(Throwable cause, String template, Object arg1) {
        if (this.isErrorEnabled()) {
            this.notifyError(cause, this.format1(template, arg1));
            return;
        }
    }

    public static /* synthetic */ void error$(LoggingAdapter $this, Throwable cause, String template, Object arg1, Object arg2) {
        $this.error(cause, template, arg1, arg2);
    }

    default public void error(Throwable cause, String template, Object arg1, Object arg2) {
        if (this.isErrorEnabled()) {
            this.notifyError(cause, this.format(template, ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{arg1, arg2})));
            return;
        }
    }

    public static /* synthetic */ void error$(LoggingAdapter $this, Throwable cause, String template, Object arg1, Object arg2, Object arg3) {
        $this.error(cause, template, arg1, arg2, arg3);
    }

    default public void error(Throwable cause, String template, Object arg1, Object arg2, Object arg3) {
        if (this.isErrorEnabled()) {
            this.notifyError(cause, this.format(template, ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{arg1, arg2, arg3})));
            return;
        }
    }

    public static /* synthetic */ void error$(LoggingAdapter $this, Throwable cause, String template, Object arg1, Object arg2, Object arg3, Object arg4) {
        $this.error(cause, template, arg1, arg2, arg3, arg4);
    }

    default public void error(Throwable cause, String template, Object arg1, Object arg2, Object arg3, Object arg4) {
        if (this.isErrorEnabled()) {
            this.notifyError(cause, this.format(template, ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{arg1, arg2, arg3, arg4})));
            return;
        }
    }

    public static /* synthetic */ void error$(LoggingAdapter $this, String message) {
        $this.error(message);
    }

    default public void error(String message) {
        if (this.isErrorEnabled()) {
            this.notifyError(message);
            return;
        }
    }

    public static /* synthetic */ void error$(LoggingAdapter $this, String template, Object arg1) {
        $this.error(template, arg1);
    }

    default public void error(String template, Object arg1) {
        if (this.isErrorEnabled()) {
            this.notifyError(this.format1(template, arg1));
            return;
        }
    }

    public static /* synthetic */ void error$(LoggingAdapter $this, String template, Object arg1, Object arg2) {
        $this.error(template, arg1, arg2);
    }

    default public void error(String template, Object arg1, Object arg2) {
        if (this.isErrorEnabled()) {
            this.notifyError(this.format(template, ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{arg1, arg2})));
            return;
        }
    }

    public static /* synthetic */ void error$(LoggingAdapter $this, String template, Object arg1, Object arg2, Object arg3) {
        $this.error(template, arg1, arg2, arg3);
    }

    default public void error(String template, Object arg1, Object arg2, Object arg3) {
        if (this.isErrorEnabled()) {
            this.notifyError(this.format(template, ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{arg1, arg2, arg3})));
            return;
        }
    }

    public static /* synthetic */ void error$(LoggingAdapter $this, String template, Object arg1, Object arg2, Object arg3, Object arg4) {
        $this.error(template, arg1, arg2, arg3, arg4);
    }

    default public void error(String template, Object arg1, Object arg2, Object arg3, Object arg4) {
        if (this.isErrorEnabled()) {
            this.notifyError(this.format(template, ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{arg1, arg2, arg3, arg4})));
            return;
        }
    }

    public static /* synthetic */ void warning$(LoggingAdapter $this, Throwable cause, String message) {
        $this.warning(cause, message);
    }

    default public void warning(Throwable cause, String message) {
        if (this.isWarningEnabled()) {
            this.notifyWarning(cause, message);
            return;
        }
    }

    public static /* synthetic */ void warning$(LoggingAdapter $this, Throwable cause, String template, Object arg1) {
        $this.warning(cause, template, arg1);
    }

    default public void warning(Throwable cause, String template, Object arg1) {
        if (this.isWarningEnabled()) {
            this.notifyWarning(cause, this.format1(template, arg1));
            return;
        }
    }

    public static /* synthetic */ void warning$(LoggingAdapter $this, Throwable cause, String template, Object arg1, Object arg2) {
        $this.warning(cause, template, arg1, arg2);
    }

    default public void warning(Throwable cause, String template, Object arg1, Object arg2) {
        if (this.isWarningEnabled()) {
            this.notifyWarning(cause, this.format(template, ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{arg1, arg2})));
            return;
        }
    }

    public static /* synthetic */ void warning$(LoggingAdapter $this, Throwable cause, String template, Object arg1, Object arg2, Object arg3) {
        $this.warning(cause, template, arg1, arg2, arg3);
    }

    default public void warning(Throwable cause, String template, Object arg1, Object arg2, Object arg3) {
        if (this.isWarningEnabled()) {
            this.notifyWarning(cause, this.format(template, ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{arg1, arg2, arg3})));
            return;
        }
    }

    public static /* synthetic */ void warning$(LoggingAdapter $this, Throwable cause, String template, Object arg1, Object arg2, Object arg3, Object arg4) {
        $this.warning(cause, template, arg1, arg2, arg3, arg4);
    }

    default public void warning(Throwable cause, String template, Object arg1, Object arg2, Object arg3, Object arg4) {
        if (this.isWarningEnabled()) {
            this.notifyWarning(cause, this.format(template, ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{arg1, arg2, arg3, arg4})));
            return;
        }
    }

    public static /* synthetic */ void warning$(LoggingAdapter $this, String message) {
        $this.warning(message);
    }

    default public void warning(String message) {
        if (this.isWarningEnabled()) {
            this.notifyWarning(message);
            return;
        }
    }

    public static /* synthetic */ void warning$(LoggingAdapter $this, String template, Object arg1) {
        $this.warning(template, arg1);
    }

    default public void warning(String template, Object arg1) {
        if (this.isWarningEnabled()) {
            this.notifyWarning(this.format1(template, arg1));
            return;
        }
    }

    public static /* synthetic */ void warning$(LoggingAdapter $this, String template, Object arg1, Object arg2) {
        $this.warning(template, arg1, arg2);
    }

    default public void warning(String template, Object arg1, Object arg2) {
        if (this.isWarningEnabled()) {
            this.notifyWarning(this.format(template, ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{arg1, arg2})));
            return;
        }
    }

    public static /* synthetic */ void warning$(LoggingAdapter $this, String template, Object arg1, Object arg2, Object arg3) {
        $this.warning(template, arg1, arg2, arg3);
    }

    default public void warning(String template, Object arg1, Object arg2, Object arg3) {
        if (this.isWarningEnabled()) {
            this.notifyWarning(this.format(template, ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{arg1, arg2, arg3})));
            return;
        }
    }

    public static /* synthetic */ void warning$(LoggingAdapter $this, String template, Object arg1, Object arg2, Object arg3, Object arg4) {
        $this.warning(template, arg1, arg2, arg3, arg4);
    }

    default public void warning(String template, Object arg1, Object arg2, Object arg3, Object arg4) {
        if (this.isWarningEnabled()) {
            this.notifyWarning(this.format(template, ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{arg1, arg2, arg3, arg4})));
            return;
        }
    }

    public static /* synthetic */ void info$(LoggingAdapter $this, String message) {
        $this.info(message);
    }

    default public void info(String message) {
        if (this.isInfoEnabled()) {
            this.notifyInfo(message);
            return;
        }
    }

    public static /* synthetic */ void info$(LoggingAdapter $this, String template, Object arg1) {
        $this.info(template, arg1);
    }

    default public void info(String template, Object arg1) {
        if (this.isInfoEnabled()) {
            this.notifyInfo(this.format1(template, arg1));
            return;
        }
    }

    public static /* synthetic */ void info$(LoggingAdapter $this, String template, Object arg1, Object arg2) {
        $this.info(template, arg1, arg2);
    }

    default public void info(String template, Object arg1, Object arg2) {
        if (this.isInfoEnabled()) {
            this.notifyInfo(this.format(template, ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{arg1, arg2})));
            return;
        }
    }

    public static /* synthetic */ void info$(LoggingAdapter $this, String template, Object arg1, Object arg2, Object arg3) {
        $this.info(template, arg1, arg2, arg3);
    }

    default public void info(String template, Object arg1, Object arg2, Object arg3) {
        if (this.isInfoEnabled()) {
            this.notifyInfo(this.format(template, ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{arg1, arg2, arg3})));
            return;
        }
    }

    public static /* synthetic */ void info$(LoggingAdapter $this, String template, Object arg1, Object arg2, Object arg3, Object arg4) {
        $this.info(template, arg1, arg2, arg3, arg4);
    }

    default public void info(String template, Object arg1, Object arg2, Object arg3, Object arg4) {
        if (this.isInfoEnabled()) {
            this.notifyInfo(this.format(template, ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{arg1, arg2, arg3, arg4})));
            return;
        }
    }

    public static /* synthetic */ void debug$(LoggingAdapter $this, String message) {
        $this.debug(message);
    }

    default public void debug(String message) {
        if (this.isDebugEnabled()) {
            this.notifyDebug(message);
            return;
        }
    }

    public static /* synthetic */ void debug$(LoggingAdapter $this, String template, Object arg1) {
        $this.debug(template, arg1);
    }

    default public void debug(String template, Object arg1) {
        if (this.isDebugEnabled()) {
            this.notifyDebug(this.format1(template, arg1));
            return;
        }
    }

    public static /* synthetic */ void debug$(LoggingAdapter $this, String template, Object arg1, Object arg2) {
        $this.debug(template, arg1, arg2);
    }

    default public void debug(String template, Object arg1, Object arg2) {
        if (this.isDebugEnabled()) {
            this.notifyDebug(this.format(template, ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{arg1, arg2})));
            return;
        }
    }

    public static /* synthetic */ void debug$(LoggingAdapter $this, String template, Object arg1, Object arg2, Object arg3) {
        $this.debug(template, arg1, arg2, arg3);
    }

    default public void debug(String template, Object arg1, Object arg2, Object arg3) {
        if (this.isDebugEnabled()) {
            this.notifyDebug(this.format(template, ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{arg1, arg2, arg3})));
            return;
        }
    }

    public static /* synthetic */ void debug$(LoggingAdapter $this, String template, Object arg1, Object arg2, Object arg3, Object arg4) {
        $this.debug(template, arg1, arg2, arg3, arg4);
    }

    default public void debug(String template, Object arg1, Object arg2, Object arg3, Object arg4) {
        if (this.isDebugEnabled()) {
            this.notifyDebug(this.format(template, ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{arg1, arg2, arg3, arg4})));
            return;
        }
    }

    public static /* synthetic */ void log$(LoggingAdapter $this, int level, String message) {
        $this.log(level, message);
    }

    default public void log(int level, String message) {
        if (this.isEnabled(level)) {
            this.notifyLog(level, message);
            return;
        }
    }

    public static /* synthetic */ void log$(LoggingAdapter $this, int level, String template, Object arg1) {
        $this.log(level, template, arg1);
    }

    default public void log(int level, String template, Object arg1) {
        if (this.isEnabled(level)) {
            this.notifyLog(level, this.format1(template, arg1));
            return;
        }
    }

    public static /* synthetic */ void log$(LoggingAdapter $this, int level, String template, Object arg1, Object arg2) {
        $this.log(level, template, arg1, arg2);
    }

    default public void log(int level, String template, Object arg1, Object arg2) {
        if (this.isEnabled(level)) {
            this.notifyLog(level, this.format(template, ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{arg1, arg2})));
            return;
        }
    }

    public static /* synthetic */ void log$(LoggingAdapter $this, int level, String template, Object arg1, Object arg2, Object arg3) {
        $this.log(level, template, arg1, arg2, arg3);
    }

    default public void log(int level, String template, Object arg1, Object arg2, Object arg3) {
        if (this.isEnabled(level)) {
            this.notifyLog(level, this.format(template, ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{arg1, arg2, arg3})));
            return;
        }
    }

    public static /* synthetic */ void log$(LoggingAdapter $this, int level, String template, Object arg1, Object arg2, Object arg3, Object arg4) {
        $this.log(level, template, arg1, arg2, arg3, arg4);
    }

    default public void log(int level, String template, Object arg1, Object arg2, Object arg3, Object arg4) {
        if (this.isEnabled(level)) {
            this.notifyLog(level, this.format(template, ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{arg1, arg2, arg3, arg4})));
            return;
        }
    }

    public static /* synthetic */ boolean isEnabled$(LoggingAdapter $this, int level) {
        return $this.isEnabled(level);
    }

    default public boolean isEnabled(int level) {
        int n = level;
        if (Logging$.MODULE$.ErrorLevel() == n) {
            return this.isErrorEnabled();
        }
        if (Logging$.MODULE$.WarningLevel() == n) {
            return this.isWarningEnabled();
        }
        if (Logging$.MODULE$.InfoLevel() == n) {
            return this.isInfoEnabled();
        }
        if (Logging$.MODULE$.DebugLevel() == n) {
            return this.isDebugEnabled();
        }
        return false;
    }

    public static /* synthetic */ void notifyLog$(LoggingAdapter $this, int level, String message) {
        $this.notifyLog(level, message);
    }

    default public void notifyLog(int level, String message) {
        int n = level;
        if (Logging$.MODULE$.ErrorLevel() == n) {
            if (this.isErrorEnabled()) {
                this.notifyError(message);
                return;
            }
            return;
        }
        if (Logging$.MODULE$.WarningLevel() == n) {
            if (this.isWarningEnabled()) {
                this.notifyWarning(message);
                return;
            }
            return;
        }
        if (Logging$.MODULE$.InfoLevel() == n) {
            if (this.isInfoEnabled()) {
                this.notifyInfo(message);
                return;
            }
            return;
        }
        if (Logging$.MODULE$.DebugLevel() == n) {
            if (this.isDebugEnabled()) {
                this.notifyDebug(message);
                return;
            }
            return;
        }
        throw new IllegalArgumentException(new StringBuilder(24).append("Unsupported log level [").append(new Logging.LogLevel(n)).append("]").toString());
    }

    private String format1(String t2, Object arg) {
        Object object;
        Object object2 = arg;
        if (ScalaRunTime$.MODULE$.isArray(object2, 1) && !(object = object2).getClass().getComponentType().isPrimitive()) {
            return this.formatImpl(t2, ArrayOps$.MODULE$.toSeq$extension(Predef$.MODULE$.genericArrayOps(object)));
        }
        if (ScalaRunTime$.MODULE$.isArray(object2, 1)) {
            Object object3 = object2;
            return this.formatImpl(t2, ArrayOps$.MODULE$.toSeq$extension(Predef$.MODULE$.refArrayOps((Object[])ArrayOps$.MODULE$.map$extension(Predef$.MODULE$.genericArrayOps(object3), (Function1<Object, Object> & Serializable)x$12 -> x$12, ClassTag$.MODULE$.AnyRef()))));
        }
        return this.format(t2, ScalaRunTime$.MODULE$.genericWrapArray(new Object[]{object2}));
    }

    public static /* synthetic */ String format$(LoggingAdapter $this, String t2, Seq arg) {
        return $this.format(t2, arg);
    }

    default public String format(String t2, Seq<Object> arg) {
        return this.formatImpl(t2, arg);
    }

    private String formatImpl(String t2, Seq<Object> arg) {
        StringBuilder sb = new StringBuilder(64);
        int p = 0;
        int startIndex = 0;
        while (p < arg.length()) {
            int index = t2.indexOf("{}", startIndex);
            if (index == -1) {
                sb.append(t2.substring(startIndex, t2.length())).append(" WARNING arguments left: ").append(arg.length() - p);
                p = arg.length();
                startIndex = t2.length();
                continue;
            }
            sb.append(t2.substring(startIndex, index)).append(arg.apply(p));
            startIndex = index + 2;
            ++p;
        }
        return sb.append(t2.substring(startIndex, t2.length())).toString();
    }

    public static void $init$(LoggingAdapter $this) {
    }
}

