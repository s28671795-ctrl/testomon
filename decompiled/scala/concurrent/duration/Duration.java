/*
 * Decompiled with CFR 0.152.
 */
package scala.concurrent.duration;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;
import scala.Option;
import scala.Tuple2;
import scala.concurrent.duration.Duration$;
import scala.concurrent.duration.FiniteDuration;
import scala.math.Ordered;
import scala.reflect.ScalaSignature;
import scala.runtime.Nothing$;
import scala.runtime.OrderedProxy;
import scala.runtime.RichDouble;

@ScalaSignature(bytes="\u0006\u0005\rEq!B/_\u0011\u0003)g!B4_\u0011\u0003A\u0007\"B;\u0002\t\u00031\b\"B<\u0002\t\u0003A\bBB<\u0002\t\u0003\u0011Y\u0001\u0003\u0004x\u0003\u0011\u0005!1\u0003\u0005\u0007o\u0006!\tA!\u0007\t\u0011\t}\u0011\u0001)C\u0005\u0005CA\u0001Ba\u0010\u0002A\u0013%!\u0011\t\u0005\t\u0005\u0017\n\u0001\u0015!\u0003\u0003N!Q!\u0011M\u0001C\u0002\u0013EaLa\u0019\t\u0011\t-\u0014\u0001)A\u0005\u0005KB!B!\u001c\u0002\u0005\u0004%\tB\u0018B8\u0011!\u0011\u0019(\u0001Q\u0001\n\tE\u0004b\u0002B;\u0003\u0011\u0005!q\u000f\u0005\b\u0005k\nA\u0011\u0001BB\u0011\u001d\u0011I)\u0001C\u0001\u0005\u0017C\u0001B!%\u0002A\u00035!1\u0013\u0005\t\u0005/\u000b\u0001\u0015!\u0004\u0003\u001a\"A!QT\u0001!\u0002\u001b\u0011y\n\u0003\u0005\u0003$\u0006\u0001\u000bQ\u0002BS\u0011!\u0011I+\u0001Q\u0001\u000e\t-\u0006\u0002\u0003BX\u0003\u0001\u0006iA!-\t\u000f\t%\u0015\u0001\"\u0001\u00036\"I!\u0011X\u0001C\u0002\u0013\u0005!1\u0018\u0005\t\u0005{\u000b\u0001\u0015!\u0003\u0003\u000e!I!qX\u0001C\u0002\u0013\u0005!\u0011\u0019\u0005\t\u0005\u0007\f\u0001\u0015!\u0003\u0002J\u001a9\u00111Y\u0001\u0002\"\u0005\u0015\u0007BB;\u001d\t\u0003\t9\rC\u0004\u0002Jq!\t!!4\t\u000f\u0005EC\u0004\"\u0001\u0002R\"9\u0011q\u000b\u000f\u0005\u0002\u0005U\u0007bBA09\u0011\u0005\u0011\u0011\u001c\u0005\b\u0003?bB\u0011AAo\u0011\u001d\ty\u0007\bC\u0003\u0003cB\u0001\"!9\u001dA\u0013%\u00111\u001d\u0005\b\u0003+aBQAA\f\u0011\u001d\ty\u0002\bC\u0003\u0003CAq!a\f\u001d\t\u000b\t9\u0002C\u0004\u00022q!)!a\u0006\t\u000f\u0005MB\u0004\"\u0002\u0002\u0018!9\u0011Q\u0007\u000f\u0005\u0006\u0005]\u0001bBA\u001c9\u0011\u0015\u0011q\u0003\u0005\b\u0003saBQAA\f\u0011\u001d\tY\u0004\bC\u0003\u0003/Aq!a/\u001d\t\u000b\ti\u0007C\u0005\u0003F\u0006\u0011\r\u0011\"\u0001\u0003B\"A!qY\u0001!\u0002\u0013\tI\rC\u0005\u0003J\u0006\u0011\r\u0011\"\u0001\u0003B\"A!1Z\u0001!\u0002\u0013\tI\rC\u0004\u0003N\u0006!\tAa4\t\u000f\t5\u0017\u0001\"\u0001\u0003V\"9!QZ\u0001\u0005\u0002\tm\u0007b\u0002Bg\u0003\u0011\u0005!\u0011]\u0004\b\u0005K\f\u00012\u0001Bt\r\u001d\u0011I/\u0001E\u0001\u0005WDa!\u001e\u001d\u0005\u0002\te\bb\u0002B~q\u0011\u0005!Q \u0005\n\u0007\u001bA\u0014\u0011!C\u0005\u0007\u001fA\u0011b!\u0004\u0002\u0003\u0003%Iaa\u0004\u0007\u000b\u001dt\u0016\u0011\u0005>\t\rUlD\u0011AA\n\u0011\u001d\t)\"\u0010D\u0001\u0003/Aq!a\b>\r\u0003\t\t\u0003C\u0004\u00020u2\t!a\u0006\t\u000f\u0005ERH\"\u0001\u0002\u0018!9\u00111G\u001f\u0007\u0002\u0005]\u0001bBA\u001b{\u0019\u0005\u0011q\u0003\u0005\b\u0003oid\u0011AA\f\u0011\u001d\tI$\u0010D\u0001\u0003/Aq!a\u000f>\r\u0003\t9\u0002C\u0004\u0002>u2\t!a\u0010\t\u000f\u0005%SH\"\u0001\u0002L!9\u0011\u0011K\u001f\u0007\u0002\u0005M\u0003bBA,{\u0019\u0005\u0011\u0011\f\u0005\b\u0003?jd\u0011AA1\u0011\u001d\ty&\u0010D\u0001\u0003OBq!a\u001b>\r\u0003\ti\u0007C\u0004\u0002pu2\t!!\u001d\t\u000f\u0005eT\b\"\u0001\u0002|!9\u0011qP\u001f\u0005\u0002\u0005\u0005\u0005bBAC{\u0011\u0005\u0011q\u0011\u0005\b\u0003\u000bkD\u0011AAF\u0011\u001d\ty)\u0010C\u0001\u0003#Cq!!&>\t\u0003\t9\nC\u0004\u0002\u001cv\"\t!!(\t\u000f\u0005\u0005V\b\"\u0001\u0002$\"9\u0011qU\u001f\u0005\u0002\u0005%\u0006bBAW{\u0011\u0005\u0011q\u0016\u0005\b\u0003gkD\u0011AA\n\u0011\u001d\t),\u0010C\u0001\u0003oCq!a/>\r\u0003\ti'\u0001\u0005EkJ\fG/[8o\u0015\ty\u0006-\u0001\u0005ekJ\fG/[8o\u0015\t\t'-\u0001\u0006d_:\u001cWO\u001d:f]RT\u0011aY\u0001\u0006g\u000e\fG.Y\u0002\u0001!\t1\u0017!D\u0001_\u0005!!UO]1uS>t7cA\u0001j[B\u0011!n[\u0007\u0002E&\u0011AN\u0019\u0002\u0007\u0003:L(+\u001a4\u0011\u00059\u001cX\"A8\u000b\u0005A\f\u0018AA5p\u0015\u0005\u0011\u0018\u0001\u00026bm\u0006L!\u0001^8\u0003\u0019M+'/[1mSj\f'\r\\3\u0002\rqJg.\u001b;?)\u0005)\u0017!B1qa2LH#B=\u0003\b\t%\u0001C\u00014>'\u0015i\u0014n_A\u0007!\ra\u0018\u0011\u0002\b\u0004{\u0006\u0015ab\u0001@\u0002\u00045\tqPC\u0002\u0002\u0002\u0011\fa\u0001\u0010:p_Rt\u0014\"A2\n\u0007\u0005\u001d!-A\u0004qC\u000e\\\u0017mZ3\n\u0007Q\fYAC\u0002\u0002\b\t\u0004B\u0001`A\bs&!\u0011\u0011CA\u0006\u0005\u001dy%\u000fZ3sK\u0012$\u0012!_\u0001\u0007Y\u0016tw\r\u001e5\u0016\u0005\u0005e\u0001c\u00016\u0002\u001c%\u0019\u0011Q\u00042\u0003\t1{gnZ\u0001\u0005k:LG/\u0006\u0002\u0002$A!\u0011QEA\u0015\u001d\r1\u0017qE\u0005\u0004\u0003\u000fq\u0016\u0002BA\u0016\u0003[\u0011\u0001\u0002V5nKVs\u0017\u000e\u001e\u0006\u0004\u0003\u000fq\u0016a\u0002;p\u001d\u0006twn]\u0001\ti>l\u0015n\u0019:pg\u0006AAo\\'jY2L7/A\u0005u_N+7m\u001c8eg\u0006IAo\\'j]V$Xm]\u0001\bi>Du.\u001e:t\u0003\u0019!x\u000eR1zg\u00061Ao\\+oSR$B!!\u0011\u0002HA\u0019!.a\u0011\n\u0007\u0005\u0015#M\u0001\u0004E_V\u0014G.\u001a\u0005\b\u0003?A\u0005\u0019AA\u0012\u0003\u0015!\u0003\u000f\\;t)\rI\u0018Q\n\u0005\u0007\u0003\u001fJ\u0005\u0019A=\u0002\u000b=$\b.\u001a:\u0002\r\u0011j\u0017N\\;t)\rI\u0018Q\u000b\u0005\u0007\u0003\u001fR\u0005\u0019A=\u0002\r\u0011\"\u0018.\\3t)\rI\u00181\f\u0005\b\u0003;Z\u0005\u0019AA!\u0003\u00191\u0017m\u0019;pe\u0006!A\u0005Z5w)\rI\u00181\r\u0005\b\u0003Kb\u0005\u0019AA!\u0003\u001d!\u0017N^5t_J$B!!\u0011\u0002j!1\u0011QM'A\u0002e\fA\"\u001e8bef|F%\\5okN,\u0012!_\u0001\tSN4\u0015N\\5uKV\u0011\u00111\u000f\t\u0004U\u0006U\u0014bAA<E\n9!i\\8mK\u0006t\u0017aA7j]R\u0019\u00110! \t\r\u0005=\u0003\u000b1\u0001z\u0003\ri\u0017\r\u001f\u000b\u0004s\u0006\r\u0005BBA(#\u0002\u0007\u00110A\u0002eSZ$2!_AE\u0011\u001d\t)G\u0015a\u0001\u0003\u0003\"B!!\u0011\u0002\u000e\"1\u0011qJ*A\u0002e\f!a\u001a;\u0015\t\u0005M\u00141\u0013\u0005\u0007\u0003\u001f\"\u0006\u0019A=\u0002\t\u001d$X-\u001d\u000b\u0005\u0003g\nI\n\u0003\u0004\u0002PU\u0003\r!_\u0001\u0003YR$B!a\u001d\u0002 \"1\u0011q\n,A\u0002e\fA\u0001\u001c;fcR!\u00111OAS\u0011\u0019\tye\u0016a\u0001s\u0006)Q.\u001b8vgR\u0019\u00110a+\t\r\u0005=\u0003\f1\u0001z\u0003\riW\u000f\u001c\u000b\u0004s\u0006E\u0006bBA/3\u0002\u0007\u0011\u0011I\u0001\u0004]\u0016<\u0017\u0001\u00029mkN$2!_A]\u0011\u0019\tye\u0017a\u0001s\u0006QAo\\\"pCJ\u001cXm\u001d;*\tu\ny\fH\u0005\u0004\u0003\u0003t&A\u0004$j]&$X\rR;sCRLwN\u001c\u0002\t\u0013:4\u0017N\\5uKN\u0011A$\u001f\u000b\u0003\u0003\u0013\u00042!a3\u001d\u001b\u0005\tAcA=\u0002P\"1\u0011q\n\u0010A\u0002e$2!_Aj\u0011\u0019\tye\ba\u0001sR\u0019\u00110a6\t\u000f\u0005u\u0003\u00051\u0001\u0002BQ\u0019\u00110a7\t\u000f\u0005\u0015\u0014\u00051\u0001\u0002BQ!\u0011\u0011IAp\u0011\u0019\t)G\ta\u0001s\u0006!a-Y5m)\u0011\t)/a;\u0011\u0007)\f9/C\u0002\u0002j\n\u0014qAT8uQ&tw\rC\u0004\u0002n\u0012\u0002\r!a<\u0002\t]D\u0017\r\u001e\t\u0005\u0003c\fIP\u0004\u0003\u0002t\u0006U\bC\u0001@c\u0013\r\t9PY\u0001\u0007!J,G-\u001a4\n\t\u0005m\u0018Q \u0002\u0007'R\u0014\u0018N\\4\u000b\u0007\u0005](-K\u0002\u001d\u0005\u00031aAa\u0001\u001d\u0001\t\u0015!!\u0004\u001fm_\u000e\fG\u000eI2iS2$gh\u0005\u0003\u0003\u0002\u0005%\u0007bBA\u000b\u0007\u0001\u0007\u0011\u0011\t\u0005\b\u0003?\u0019\u0001\u0019AA\u0012)\u0019\u0011iAa\u0004\u0003\u0012A\u0019a-a0\t\u000f\u0005UA\u00011\u0001\u0002\u001a!9\u0011q\u0004\u0003A\u0002\u0005\rBC\u0002B\u0007\u0005+\u00119\u0002C\u0004\u0002\u0016\u0015\u0001\r!!\u0007\t\u000f\u0005}Q\u00011\u0001\u0002pR\u0019\u0011Pa\u0007\t\u000f\tua\u00011\u0001\u0002p\u0006\t1/A\u0003x_J$7\u000f\u0006\u0003\u0003$\tu\u0002C\u0002B\u0013\u0005_\u0011\u0019$\u0004\u0002\u0003()!!\u0011\u0006B\u0016\u0003%IW.\\;uC\ndWMC\u0002\u0003.\t\f!bY8mY\u0016\u001cG/[8o\u0013\u0011\u0011\tDa\n\u0003\t1K7\u000f\u001e\t\u0005\u0005k\u0011Y$\u0004\u0002\u00038)\u0019!\u0011H9\u0002\t1\fgnZ\u0005\u0005\u0003w\u00149\u0004C\u0004\u0003\u001e\u001d\u0001\r!a<\u0002\u0019\u0015D\b/\u00198e\u0019\u0006\u0014W\r\\:\u0015\t\t\r#q\t\t\u0006y\n\u0015\u0013q^\u0005\u0005\u0005c\tY\u0001C\u0004\u0003J!\u0001\r!a<\u0002\r1\f'-\u001a7t\u00039!\u0018.\\3V]&$H*\u00192fYN\u0004bA!\n\u00030\t=\u0003c\u00026\u0003R\tU#1G\u0005\u0004\u0005'\u0012'A\u0002+va2,'\u0007\u0005\u0003\u0003X\t}SB\u0001B-\u0015\r\t'1\f\u0006\u0004\u0005;\n\u0018\u0001B;uS2LA!a\u000b\u0003Z\u0005aA/[7f+:LGOT1nKV\u0011!Q\r\t\t\u0003c\u00149'a\t\u0002p&!!\u0011NA\u007f\u0005\ri\u0015\r]\u0001\u000ei&lW-\u00168ji:\u000bW.\u001a\u0011\u0002\u0011QLW.Z+oSR,\"A!\u001d\u0011\u0011\u0005E(qMAx\u0003G\t\u0011\u0002^5nKVs\u0017\u000e\u001e\u0011\u0002\u000fUt\u0017\r\u001d9msR!!\u0011\u0010BA!\u0015Q'1\u0010B@\u0013\r\u0011iH\u0019\u0002\u0007\u001fB$\u0018n\u001c8\u0011\u000f)\u0014\t&!\u0007\u0002$!9!Q\u0004\bA\u0002\u0005=H\u0003\u0002B=\u0005\u000bCaAa\"\u0010\u0001\u0004I\u0018!\u00013\u0002\u0013\u0019\u0014x.\u001c(b]>\u001cHcA=\u0003\u000e\"9!q\u0012\tA\u0002\u0005\u0005\u0013!\u00028b]>\u001c\u0018A\u00038t?B,'o\u0018b6h>\u0011!Q\u0013\u0010\u0003\u0007!\u0010\u0011B\\:`a\u0016\u0014x,\\:\u0010\u0005\tmedA\bC\u0001\u0006Aan]0qKJ|6o\u0004\u0002\u0003\"z!1H'f\u0001\u0003)q7o\u00189fe~k\u0017N\\\b\u0003\u0005OsR!\u0004}H1\u0002\t\u0001B\\:`a\u0016\u0014x\f[\b\u0003\u0005[sba\u0001$1q\u0003\u0006\u0011\u0001\u00038t?B,'o\u00183\u0010\u0005\tMfD\u0002(\u0015$?\u0003\u0001\u0001\u0006\u0003\u0003\u000e\t]\u0006b\u0002BH/\u0001\u0007\u0011\u0011D\u0001\u00055\u0016\u0014x.\u0006\u0002\u0003\u000e\u0005)!,\u001a:pA\u0005IQK\u001c3fM&tW\rZ\u000b\u0003\u0003\u0013\f!\"\u00168eK\u001aLg.\u001a3!\u0003\rIeNZ\u0001\u0005\u0013:4\u0007%\u0001\u0005NS:,8/\u00138g\u0003%i\u0015N\\;t\u0013:4\u0007%\u0001\u0004de\u0016\fG/\u001a\u000b\u0007\u0005\u001b\u0011\tNa5\t\u000f\u0005U1\u00071\u0001\u0002\u001a!9\u0011qD\u001aA\u0002\u0005\rB#B=\u0003X\ne\u0007bBA\u000bi\u0001\u0007\u0011\u0011\t\u0005\b\u0003?!\u0004\u0019AA\u0012)\u0019\u0011iA!8\u0003`\"9\u0011QC\u001bA\u0002\u0005e\u0001bBA\u0010k\u0001\u0007\u0011q\u001e\u000b\u0004s\n\r\bb\u0002B\u000fm\u0001\u0007\u0011q^\u0001\u0012\tV\u0014\u0018\r^5p]&\u001bxJ\u001d3fe\u0016$\u0007cAAfq\t\tB)\u001e:bi&|g.S:Pe\u0012,'/\u001a3\u0014\u000ba\u0012iOa=\u0011\t\tU\"q^\u0005\u0005\u0005c\u00149D\u0001\u0004PE*,7\r\u001e\t\u0005y\nU\u00180\u0003\u0003\u0003x\u0006-!\u0001C(sI\u0016\u0014\u0018N\\4\u0015\u0005\t\u001d\u0018aB2p[B\f'/\u001a\u000b\u0007\u0005\u007f\u001c)a!\u0003\u0011\u0007)\u001c\t!C\u0002\u0004\u0004\t\u00141!\u00138u\u0011\u0019\u00199A\u000fa\u0001s\u0006\t\u0011\r\u0003\u0004\u0004\fi\u0002\r!_\u0001\u0002E\u0006aqO]5uKJ+\u0007\u000f\\1dKR\u0011!Q\u001e")
public abstract class Duration
implements Serializable,
Ordered<Duration> {
    public static Duration create(String s2) {
        return Duration$.MODULE$.apply(s2);
    }

    public static FiniteDuration create(long length, String unit) {
        return Duration$.MODULE$.apply(length, unit);
    }

    public static Duration create(double length, TimeUnit unit) {
        return Duration$.MODULE$.apply(length, unit);
    }

    public static FiniteDuration create(long length, TimeUnit unit) {
        return new FiniteDuration(length, unit);
    }

    public static Infinite MinusInf() {
        return Duration$.MODULE$.MinusInf();
    }

    public static Infinite Inf() {
        return Duration$.MODULE$.Inf();
    }

    public static Infinite Undefined() {
        return Duration$.MODULE$.Undefined();
    }

    public static FiniteDuration Zero() {
        return Duration$.MODULE$.Zero();
    }

    public static FiniteDuration fromNanos(long nanos) {
        return Duration$.MODULE$.fromNanos(nanos);
    }

    public static Duration fromNanos(double nanos) {
        return Duration$.MODULE$.fromNanos(nanos);
    }

    public static Option<Tuple2<Object, TimeUnit>> unapply(Duration d) {
        return Duration$.MODULE$.unapply(d);
    }

    public static Option<Tuple2<Object, TimeUnit>> unapply(String s2) {
        return Duration$.MODULE$.unapply(s2);
    }

    public static Duration apply(String s2) {
        return Duration$.MODULE$.apply(s2);
    }

    public static FiniteDuration apply(long length, String unit) {
        return Duration$.MODULE$.apply(length, unit);
    }

    public static FiniteDuration apply(long length, TimeUnit unit) {
        return new FiniteDuration(length, unit);
    }

    public static Duration apply(double length, TimeUnit unit) {
        return Duration$.MODULE$.apply(length, unit);
    }

    @Override
    public boolean $less(Object that) {
        return Ordered.$less$(this, that);
    }

    @Override
    public boolean $greater(Object that) {
        return Ordered.$greater$(this, that);
    }

    @Override
    public boolean $less$eq(Object that) {
        return Ordered.$less$eq$(this, that);
    }

    @Override
    public boolean $greater$eq(Object that) {
        return Ordered.$greater$eq$(this, that);
    }

    @Override
    public int compareTo(Object that) {
        return Ordered.compareTo$(this, that);
    }

    public abstract long length();

    public abstract TimeUnit unit();

    public abstract long toNanos();

    public abstract long toMicros();

    public abstract long toMillis();

    public abstract long toSeconds();

    public abstract long toMinutes();

    public abstract long toHours();

    public abstract long toDays();

    public abstract double toUnit(TimeUnit var1);

    public abstract Duration $plus(Duration var1);

    public abstract Duration $minus(Duration var1);

    public abstract Duration $times(double var1);

    public abstract Duration $div(double var1);

    public abstract double $div(Duration var1);

    public abstract Duration unary_$minus();

    public abstract boolean isFinite();

    public Duration min(Duration other) {
        if (this.$less(other)) {
            return this;
        }
        return other;
    }

    public Duration max(Duration other) {
        if (this.$greater(other)) {
            return this;
        }
        return other;
    }

    public Duration div(double divisor) {
        return this.$div(divisor);
    }

    public double div(Duration other) {
        return this.$div(other);
    }

    public boolean gt(Duration other) {
        return this.$greater(other);
    }

    public boolean gteq(Duration other) {
        return this.$greater$eq(other);
    }

    public boolean lt(Duration other) {
        return this.$less(other);
    }

    public boolean lteq(Duration other) {
        return this.$less$eq(other);
    }

    public Duration minus(Duration other) {
        return this.$minus(other);
    }

    public Duration mul(double factor) {
        return this.$times(factor);
    }

    public Duration neg() {
        return this.unary_$minus();
    }

    public Duration plus(Duration other) {
        return this.$plus(other);
    }

    public abstract Duration toCoarsest();

    public static abstract class Infinite
    extends Duration {
        @Override
        public Duration $plus(Duration other) {
            if (other == Duration$.MODULE$.Undefined()) {
                return Duration$.MODULE$.Undefined();
            }
            if (other instanceof Infinite && (Infinite)other != this) {
                return Duration$.MODULE$.Undefined();
            }
            return this;
        }

        @Override
        public Duration $minus(Duration other) {
            if (other == Duration$.MODULE$.Undefined()) {
                return Duration$.MODULE$.Undefined();
            }
            if (other instanceof Infinite && (Infinite)other == this) {
                return Duration$.MODULE$.Undefined();
            }
            return this;
        }

        @Override
        public Duration $times(double factor) {
            if (factor == 0.0 || Double.isNaN(factor)) {
                return Duration$.MODULE$.Undefined();
            }
            if (factor < 0.0) {
                return this.unary_$minus();
            }
            return this;
        }

        @Override
        public Duration $div(double divisor) {
            if (Double.isNaN(divisor) || Double.isInfinite(divisor)) {
                return Duration$.MODULE$.Undefined();
            }
            if (OrderedProxy.compare$(new RichDouble(divisor), 0.0) < 0) {
                return this.unary_$minus();
            }
            return this;
        }

        @Override
        public double $div(Duration divisor) {
            if (divisor instanceof Infinite) {
                return Double.NaN;
            }
            return Double.POSITIVE_INFINITY * (double)(this.$greater(Duration$.MODULE$.Zero()) ^ divisor.$greater$eq(Duration$.MODULE$.Zero()) ? -1 : 1);
        }

        @Override
        public final boolean isFinite() {
            return false;
        }

        private Nothing$ fail(String what) {
            throw new IllegalArgumentException(new StringBuilder(34).append(what).append(" not allowed on infinite Durations").toString());
        }

        @Override
        public final long length() {
            throw this.fail("length");
        }

        @Override
        public final TimeUnit unit() {
            throw this.fail("unit");
        }

        @Override
        public final long toNanos() {
            throw this.fail("toNanos");
        }

        @Override
        public final long toMicros() {
            throw this.fail("toMicros");
        }

        @Override
        public final long toMillis() {
            throw this.fail("toMillis");
        }

        @Override
        public final long toSeconds() {
            throw this.fail("toSeconds");
        }

        @Override
        public final long toMinutes() {
            throw this.fail("toMinutes");
        }

        @Override
        public final long toHours() {
            throw this.fail("toHours");
        }

        @Override
        public final long toDays() {
            throw this.fail("toDays");
        }

        @Override
        public final Duration toCoarsest() {
            return this;
        }
    }
}

