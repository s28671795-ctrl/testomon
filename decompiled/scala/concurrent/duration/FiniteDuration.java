/*
 * Decompiled with CFR 0.152.
 */
package scala.concurrent.duration;

import java.util.concurrent.TimeUnit;
import scala.MatchError;
import scala.concurrent.duration.Deadline;
import scala.concurrent.duration.Deadline$;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.Duration$;
import scala.concurrent.duration.FiniteDuration$;
import scala.math.Ordered;
import scala.reflect.ScalaSignature;
import scala.runtime.OrderedProxy;
import scala.runtime.RichLong;

@ScalaSignature(bytes="\u0006\u0005\t-t!\u0002#F\u0011\u0003ae!\u0002(F\u0011\u0003y\u0005\"\u0002/\u0002\t\u0003iv!\u00020\u0002\u0011\u0007yf!B1\u0002\u0011\u0003\u0011\u0007B\u0002/\u0005\t\u0003\u0011Y\u0001C\u0004\u0002j\u0011!\tA!\u0004\t\u0013\tMA!!A\u0005\n\tU\u0001b\u0002B\f\u0003\u0011\u0005!\u0011\u0004\u0005\b\u0005/\tA\u0011\u0001B\u0010\u0011%\u0011)#\u0001b\u0001\n\u001b\u00119\u0003\u0003\u0005\u0003.\u0005\u0001\u000bQ\u0002B\u0015\u0011%\u0011y#\u0001b\u0001\n\u001b\u0011\t\u0004\u0003\u0005\u00038\u0005\u0001\u000bQ\u0002B\u001a\u0011%\u0011I$\u0001b\u0001\n\u001b\u0011Y\u0004\u0003\u0005\u0003B\u0005\u0001\u000bQ\u0002B\u001f\u0011%\u0011\u0019%\u0001b\u0001\n\u001b\u0011)\u0005\u0003\u0005\u0003L\u0005\u0001\u000bQ\u0002B$\u0011%\u0011i%\u0001b\u0001\n\u001b\u0011y\u0005\u0003\u0005\u0003V\u0005\u0001\u000bQ\u0002B)\u0011%\u00119&\u0001b\u0001\n\u001b\u0011I\u0006\u0003\u0005\u0003`\u0005\u0001\u000bQ\u0002B.\u0011%\u0011\t'\u0001b\u0001\n\u001b\u0011\u0019\u0007\u0003\u0005\u0003j\u0005\u0001\u000bQ\u0002B3\u0011%\u0011\u0019\"AA\u0001\n\u0013\u0011)B\u0002\u0003O\u000b\n1\b\u0002\u0003>\u001a\u0005\u000b\u0007I\u0011A>\t\u0011}L\"\u0011!Q\u0001\nqD!\"!\u0001\u001a\u0005\u000b\u0007I\u0011AA\u0002\u0011)\t\t\"\u0007B\u0001B\u0003%\u0011Q\u0001\u0005\u00079f!\t!a\u0005\t\u0011\u0005e\u0011\u0004)C\u0005\u00037Aa!a\n\u001a\t\u0003Y\bBBA\u00153\u0011\u00051\u0010\u0003\u0004\u0002,e!\ta\u001f\u0005\u0007\u0003[IB\u0011A>\t\r\u0005=\u0012\u0004\"\u0001|\u0011\u0019\t\t$\u0007C\u0001w\"1\u00111G\r\u0005\u0002mDq!!\u000e\u001a\t\u0003\t9\u0004C\u0004\u0002De!\t!!\u0012\t\u0011\u00055\u0013\u0004)C\u0005\u0003\u001fBq!a\u0016\u001a\t\u0003\nI\u0006C\u0004\u0002je!\t!a\u001b\t\u0011\u0005]\u0014\u0004)C\u0005\u0003sB\u0001\"a!\u001aA\u0013%\u0011Q\u0011\u0005\b\u0003\u001fKB\u0011AAI\u0011\u001d\t)*\u0007C\u0001\u0003/Cq!a'\u001a\t\u0003\ti\nC\u0004\u0002$f!\t!!*\t\u0011\u0005-\u0016\u0004)C\u0005\u0003[Cq!a)\u001a\t\u0003\ty\u000bC\u0004\u0002\u0010f!\t!a-\t\u000f\u0005U\u0015\u0004\"\u0001\u00028\"9\u00111X\r\u0005\u0002\u0005u\u0006bBAa3\u0011\u0005\u00111\u0019\u0005\b\u0003\u000fLB\u0011AAe\u0011\u001d\t)#\u0007C\u0001\u0003\u001bDq!a)\u001a\t\u0003\t\t\u000eC\u0004\u0002\u001cf!\t!!6\t\u000f\u0005e\u0017\u0004\"\u0003\u0002\\\"9\u0011Q]\r\u0005\u0002\u0005\u001d\bbBAv3\u0011\u0005\u0011Q\u001e\u0005\b\u0003cLB\u0011AAz\u0011\u001d\t)0\u0007C\u0003\u0003oDq!!?\u001a\t\u000b\n\u0019\u0010C\u0004\u0002|f!\t%!@\t\u000f\t\u001d\u0011\u0004\"\u0011\u0003\n\u0005qa)\u001b8ji\u0016$UO]1uS>t'B\u0001$H\u0003!!WO]1uS>t'B\u0001%J\u0003)\u0019wN\\2veJ,g\u000e\u001e\u0006\u0002\u0015\u0006)1oY1mC\u000e\u0001\u0001CA'\u0002\u001b\u0005)%A\u0004$j]&$X\rR;sCRLwN\\\n\u0004\u0003A#\u0006CA)S\u001b\u0005I\u0015BA*J\u0005\u0019\te.\u001f*fMB\u0011QKW\u0007\u0002-*\u0011q\u000bW\u0001\u0003S>T\u0011!W\u0001\u0005U\u00064\u0018-\u0003\u0002\\-\na1+\u001a:jC2L'0\u00192mK\u00061A(\u001b8jiz\"\u0012\u0001T\u0001\u0018\r&t\u0017\u000e^3EkJ\fG/[8o\u0013N|%\u000fZ3sK\u0012\u0004\"\u0001\u0019\u0003\u000e\u0003\u0005\u0011qCR5oSR,G)\u001e:bi&|g.S:Pe\u0012,'/\u001a3\u0014\u0007\u0011\u0019\u0017\u000e\u0005\u0002eO6\tQM\u0003\u0002g1\u0006!A.\u00198h\u0013\tAWM\u0001\u0004PE*,7\r\u001e\t\u0004UJ,hBA6q\u001d\taw.D\u0001n\u0015\tq7*\u0001\u0004=e>|GOP\u0005\u0002\u0015&\u0011\u0011/S\u0001\ba\u0006\u001c7.Y4f\u0013\t\u0019HO\u0001\u0005Pe\u0012,'/\u001b8h\u0015\t\t\u0018\n\u0005\u0002N3M\u0011\u0011d\u001e\t\u0003\u001bbL!!_#\u0003\u0011\u0011+(/\u0019;j_:\fa\u0001\\3oORDW#\u0001?\u0011\u0005Ek\u0018B\u0001@J\u0005\u0011auN\\4\u0002\u000f1,gn\u001a;iA\u0005!QO\\5u+\t\t)\u0001\u0005\u0003\u0002\b\u0005-abA'\u0002\n%\u0011\u0011/R\u0005\u0005\u0003\u001b\tyA\u0001\u0005US6,WK\\5u\u0015\t\tX)A\u0003v]&$\b\u0005F\u0003v\u0003+\t9\u0002C\u0003{=\u0001\u0007A\u0010C\u0004\u0002\u0002y\u0001\r!!\u0002\u0002\u000f\t|WO\u001c3fIR!\u0011QDA\u0012!\r\t\u0016qD\u0005\u0004\u0003CI%a\u0002\"p_2,\u0017M\u001c\u0005\u0007\u0003Ky\u0002\u0019\u0001?\u0002\u00075\f\u00070A\u0004u_:\u000bgn\\:\u0002\u0011Q|W*[2s_N\f\u0001\u0002^8NS2d\u0017n]\u0001\ni>\u001cVmY8oIN\f\u0011\u0002^8NS:,H/Z:\u0002\u000fQ|\u0007j\\;sg\u00061Ao\u001c#bsN\fa\u0001^8V]&$H\u0003BA\u001d\u0003\u007f\u00012!UA\u001e\u0013\r\ti$\u0013\u0002\u0007\t>,(\r\\3\t\u000f\u0005\u0005s\u00051\u0001\u0002\u0006\u0005\tQ/A\u0004ge>lgj\\<\u0016\u0005\u0005\u001d\u0003cA'\u0002J%\u0019\u00111J#\u0003\u0011\u0011+\u0017\r\u001a7j]\u0016\f!\"\u001e8jiN#(/\u001b8h+\t\t\t\u0006E\u0002e\u0003'J1!!\u0016f\u0005\u0019\u0019FO]5oO\u0006AAo\\*ue&tw\r\u0006\u0002\u0002\\A!\u0011QLA3\u001d\u0011\ty&!\u0019\u0011\u00051L\u0015bAA2\u0013\u00061\u0001K]3eK\u001aLA!!\u0016\u0002h)\u0019\u00111M%\u0002\u000f\r|W\u000e]1sKR!\u0011QNA:!\r\t\u0016qN\u0005\u0004\u0003cJ%aA%oi\"1\u0011QO\u0016A\u0002]\fQa\u001c;iKJ\fqa]1gK\u0006#G\rF\u0003}\u0003w\ny\b\u0003\u0004\u0002~1\u0002\r\u0001`\u0001\u0002C\"1\u0011\u0011\u0011\u0017A\u0002q\f\u0011AY\u0001\u0004C\u0012$G#B;\u0002\b\u0006-\u0005BBAE[\u0001\u0007A0A\u0006pi\",'\u000fT3oORD\u0007bBAG[\u0001\u0007\u0011QA\u0001\n_RDWM]+oSR\fQ\u0001\n9mkN$2a^AJ\u0011\u0019\t)H\fa\u0001o\u00061A%\\5okN$2a^AM\u0011\u0019\t)h\fa\u0001o\u00061A\u0005^5nKN$2a^AP\u0011\u001d\t\t\u000b\ra\u0001\u0003s\taAZ1di>\u0014\u0018\u0001\u0002\u0013eSZ$2a^AT\u0011\u001d\tI+\ra\u0001\u0003s\tq\u0001Z5wSN|'/A\u0005nS:,8OW3s_V\u0011\u0011\u0011\b\u000b\u0005\u0003s\t\t\f\u0003\u0004\u0002*N\u0002\ra\u001e\u000b\u0004k\u0006U\u0006BBA;i\u0001\u0007Q\u000fF\u0002v\u0003sCa!!\u001e6\u0001\u0004)\u0018\u0001\u00029mkN$2!^A`\u0011\u0019\t)H\u000ea\u0001k\u0006)Q.\u001b8vgR\u0019Q/!2\t\r\u0005Ut\u00071\u0001v\u0003\ri\u0017N\u001c\u000b\u0004k\u0006-\u0007BBA;q\u0001\u0007Q\u000fF\u0002v\u0003\u001fDa!!\u001e:\u0001\u0004)HcA;\u0002T\"1\u0011\u0011\u0016\u001eA\u0002q$2!^Al\u0011\u0019\t\tk\u000fa\u0001y\u000691/\u00194f\u001bVdG#\u0002?\u0002^\u0006\u0005\bBBApy\u0001\u0007A0\u0001\u0002`C\"1\u00111\u001d\u001fA\u0002q\f!a\u00182\u0002\u0007\u0011Lg\u000fF\u0002v\u0003SDa!!+>\u0001\u0004a\u0018aA7vYR\u0019Q/a<\t\r\u0005\u0005f\b1\u0001}\u00031)h.\u0019:z?\u0012j\u0017N\\;t+\u0005)\u0018\u0001C5t\r&t\u0017\u000e^3\u0016\u0005\u0005u\u0011A\u0003;p\u0007>\f'o]3ti\u00061Q-];bYN$B!!\b\u0002\u0000\"9\u0011Q\u000f\"A\u0002\t\u0005\u0001cA)\u0003\u0004%\u0019!QA%\u0003\u0007\u0005s\u00170\u0001\u0005iCND7i\u001c3f)\t\ti\u0007F\u0001`)\u0019\tiGa\u0004\u0003\u0012!1\u0011Q\u0010\u0004A\u0002UDa!!!\u0007\u0001\u0004)\u0018\u0001D<sSR,'+\u001a9mC\u000e,G#A2\u0002\u000b\u0005\u0004\b\u000f\\=\u0015\u000bU\u0014YB!\b\t\u000biD\u0001\u0019\u0001?\t\u000f\u0005\u0005\u0001\u00021\u0001\u0002\u0006Q)QO!\t\u0003$!)!0\u0003a\u0001y\"9\u0011\u0011A\u0005A\u0002\u0005m\u0013AB7bq~s7/\u0006\u0002\u0003*=\u0011!1\u0006\u0010\t\u007f~\u0000\u0000\u0000\u0000\u0000\u0000\u0000\b9Q.\u0019=`]N\u0004\u0013aB7bq~\u0013]w]\u000b\u0003\u0005gy!A!\u000e\u001f\u000f\u0001\"=4jrTo\bAQ.\u0019=`\u0005X\u001e\b%\u0001\u0004nCb|Vn]\u000b\u0003\u0005{y!Aa\u0010\u001f\r!\u00197\u00105.w\u0004\u001di\u0017\r_0ng\u0002\nQ!\\1y?N,\"Aa\u0012\u0010\u0005\t%c$\u0002\u0002&\u0003x$\u0011AB7bq~\u001b\b%A\u0004nCb|V.\u001b8\u0016\u0005\tEsB\u0001B*=\u0011I\u0011fhz\u0002\u00115\f\u0007pX7j]\u0002\nQ!\\1y?\",\"Aa\u0017\u0010\u0005\tucdA\u0014\u0018\u007f\b1Q.\u0019=`Q\u0002\nQ!\\1y?\u0012,\"A!\u001a\u0010\u0005\t\u001dddA\u0001!\u0000\b1Q.\u0019=`I\u0002\u0002")
public final class FiniteDuration
extends Duration {
    private final long length;
    private final TimeUnit unit;

    public static FiniteDuration apply(long length, String unit) {
        return FiniteDuration$.MODULE$.apply(length, unit);
    }

    public static FiniteDuration apply(long length, TimeUnit unit) {
        return new FiniteDuration(length, unit);
    }

    @Override
    public long length() {
        return this.length;
    }

    @Override
    public TimeUnit unit() {
        return this.unit;
    }

    private boolean bounded(long max) {
        return -max <= this.length() && this.length() <= max;
    }

    @Override
    public long toNanos() {
        return this.unit().toNanos(this.length());
    }

    @Override
    public long toMicros() {
        return this.unit().toMicros(this.length());
    }

    @Override
    public long toMillis() {
        return this.unit().toMillis(this.length());
    }

    @Override
    public long toSeconds() {
        return this.unit().toSeconds(this.length());
    }

    @Override
    public long toMinutes() {
        return this.unit().toMinutes(this.length());
    }

    @Override
    public long toHours() {
        return this.unit().toHours(this.length());
    }

    @Override
    public long toDays() {
        return this.unit().toDays(this.length());
    }

    @Override
    public double toUnit(TimeUnit u) {
        return (double)this.toNanos() / (double)TimeUnit.NANOSECONDS.convert(1L, u);
    }

    public Deadline fromNow() {
        return Deadline$.MODULE$.now().$plus(this);
    }

    private String unitString() {
        return new StringBuilder(0).append((String)Duration$.MODULE$.timeUnitName().apply(this.unit())).append((Object)(this.length() == 1L ? "" : "s")).toString();
    }

    public String toString() {
        return new StringBuilder(1).append(this.length()).append(" ").append(this.unitString()).toString();
    }

    @Override
    public int compare(Duration other) {
        if (other instanceof FiniteDuration) {
            FiniteDuration finiteDuration = (FiniteDuration)other;
            return OrderedProxy.compare$(new RichLong(this.toNanos()), finiteDuration.toNanos());
        }
        return -other.compare(this);
    }

    private long safeAdd(long a, long b) {
        if (b > 0L && a > Long.MAX_VALUE - b || b < 0L && a < Long.MIN_VALUE - b) {
            throw new IllegalArgumentException("integer overflow");
        }
        return a + b;
    }

    private FiniteDuration add(long otherLength, TimeUnit otherUnit) {
        TimeUnit commonUnit = otherUnit.convert(1L, this.unit()) == 0L ? this.unit() : otherUnit;
        long totalLength = this.safeAdd(commonUnit.convert(this.length(), this.unit()), commonUnit.convert(otherLength, otherUnit));
        return new FiniteDuration(totalLength, commonUnit);
    }

    @Override
    public Duration $plus(Duration other) {
        if (other instanceof FiniteDuration) {
            FiniteDuration finiteDuration = (FiniteDuration)other;
            return this.add(finiteDuration.length(), finiteDuration.unit());
        }
        return other;
    }

    @Override
    public Duration $minus(Duration other) {
        if (other instanceof FiniteDuration) {
            FiniteDuration finiteDuration = (FiniteDuration)other;
            return this.add(-finiteDuration.length(), finiteDuration.unit());
        }
        return other.unary_$minus();
    }

    @Override
    public Duration $times(double factor) {
        if (!Double.isInfinite(factor)) {
            return Duration$.MODULE$.fromNanos((double)this.toNanos() * factor);
        }
        if (Double.isNaN(factor)) {
            return Duration$.MODULE$.Undefined();
        }
        FiniteDuration $less_that = Duration$.MODULE$.Zero();
        Object var3_2 = null;
        if (factor > 0.0 ^ Ordered.$less$(this, $less_that)) {
            return Duration$.MODULE$.Inf();
        }
        return Duration$.MODULE$.MinusInf();
    }

    @Override
    public Duration $div(double divisor) {
        if (!Double.isInfinite(divisor)) {
            return Duration$.MODULE$.fromNanos((double)this.toNanos() / divisor);
        }
        if (Double.isNaN(divisor)) {
            return Duration$.MODULE$.Undefined();
        }
        return Duration$.MODULE$.Zero();
    }

    private double minusZero() {
        return -0.0;
    }

    @Override
    public double $div(Duration divisor) {
        if (divisor.isFinite()) {
            return (double)this.toNanos() / (double)divisor.toNanos();
        }
        if (divisor == Duration$.MODULE$.Undefined()) {
            return Double.NaN;
        }
        if (this.length() < 0L ^ divisor.$greater(Duration$.MODULE$.Zero())) {
            return 0.0;
        }
        return -0.0;
    }

    public FiniteDuration $plus(FiniteDuration other) {
        return this.add(other.length(), other.unit());
    }

    public FiniteDuration $minus(FiniteDuration other) {
        return this.add(-other.length(), other.unit());
    }

    public FiniteDuration plus(FiniteDuration other) {
        return this.$plus(other);
    }

    public FiniteDuration minus(FiniteDuration other) {
        return this.$minus(other);
    }

    public FiniteDuration min(FiniteDuration other) {
        if (Ordered.$less$(this, other)) {
            return this;
        }
        return other;
    }

    public FiniteDuration max(FiniteDuration other) {
        if (Ordered.$greater$(this, other)) {
            return this;
        }
        return other;
    }

    public FiniteDuration $div(long divisor) {
        return Duration$.MODULE$.fromNanos(this.toNanos() / divisor);
    }

    public FiniteDuration $times(long factor) {
        return new FiniteDuration(this.safeMul(this.length(), factor), this.unit());
    }

    private long safeMul(long _a, long _b) {
        long a = Math.abs(_a);
        long b = Math.abs(_b);
        if (Long.numberOfLeadingZeros(a) + Long.numberOfLeadingZeros(b) < 64) {
            throw new IllegalArgumentException("multiplication overflow");
        }
        long product = a * b;
        if (product < 0L) {
            throw new IllegalArgumentException("multiplication overflow");
        }
        if (a == _a ^ b == _b) {
            return -product;
        }
        return product;
    }

    public FiniteDuration div(long divisor) {
        return this.$div(divisor);
    }

    public FiniteDuration mul(long factor) {
        return this.$times(factor);
    }

    @Override
    public FiniteDuration unary_$minus() {
        TimeUnit apply_unit = this.unit();
        long apply_length = -this.length();
        return new FiniteDuration(apply_length, apply_unit);
    }

    @Override
    public final boolean isFinite() {
        return true;
    }

    @Override
    public final FiniteDuration toCoarsest() {
        TimeUnit timeUnit = this.unit();
        TimeUnit timeUnit2 = TimeUnit.DAYS;
        if (!(timeUnit == null ? timeUnit2 != null : !((Object)((Object)timeUnit)).equals((Object)timeUnit2)) || this.length() == 0L) {
            return this;
        }
        return this.loop$1(this.length(), this.unit());
    }

    public boolean equals(Object other) {
        if (other instanceof FiniteDuration) {
            FiniteDuration finiteDuration = (FiniteDuration)other;
            return this.toNanos() == finiteDuration.toNanos();
        }
        return super.equals(other);
    }

    public int hashCode() {
        return (int)this.toNanos();
    }

    public static final /* synthetic */ String $anonfun$new$1() {
        return "Duration is limited to +-(2^63-1)ns (ca. 292 years)";
    }

    private final FiniteDuration coarserOrThis$1(TimeUnit coarser, int divider, long length$1, TimeUnit unit$3) {
        if (length$1 % (long)divider == 0L) {
            return this.loop$1(length$1 / (long)divider, coarser);
        }
        TimeUnit timeUnit = unit$3;
        TimeUnit timeUnit2 = this.unit();
        if (!(timeUnit != null ? !((Object)((Object)timeUnit)).equals((Object)timeUnit2) : timeUnit2 != null)) {
            return this;
        }
        return new FiniteDuration(length$1, unit$3);
    }

    private final FiniteDuration loop$1(long length, TimeUnit unit) {
        if (((Object)((Object)TimeUnit.DAYS)).equals((Object)unit)) {
            return new FiniteDuration(length, unit);
        }
        if (((Object)((Object)TimeUnit.HOURS)).equals((Object)unit)) {
            return this.coarserOrThis$1(TimeUnit.DAYS, 24, length, unit);
        }
        if (((Object)((Object)TimeUnit.MINUTES)).equals((Object)unit)) {
            return this.coarserOrThis$1(TimeUnit.HOURS, 60, length, unit);
        }
        if (((Object)((Object)TimeUnit.SECONDS)).equals((Object)unit)) {
            return this.coarserOrThis$1(TimeUnit.MINUTES, 60, length, unit);
        }
        if (((Object)((Object)TimeUnit.MILLISECONDS)).equals((Object)unit)) {
            return this.coarserOrThis$1(TimeUnit.SECONDS, 1000, length, unit);
        }
        if (((Object)((Object)TimeUnit.MICROSECONDS)).equals((Object)unit)) {
            return this.coarserOrThis$1(TimeUnit.MILLISECONDS, 1000, length, unit);
        }
        if (((Object)((Object)TimeUnit.NANOSECONDS)).equals((Object)unit)) {
            return this.coarserOrThis$1(TimeUnit.MICROSECONDS, 1000, length, unit);
        }
        throw new MatchError((Object)unit);
    }

    public FiniteDuration(long length, TimeUnit unit) {
        long v;
        this.length = length;
        this.unit = unit;
        if (!(((Object)((Object)TimeUnit.NANOSECONDS)).equals((Object)unit) ? this.bounded(Long.MAX_VALUE) : (((Object)((Object)TimeUnit.MICROSECONDS)).equals((Object)unit) ? this.bounded(9223372036854775L) : (((Object)((Object)TimeUnit.MILLISECONDS)).equals((Object)unit) ? this.bounded(9223372036854L) : (((Object)((Object)TimeUnit.SECONDS)).equals((Object)unit) ? this.bounded(9223372036L) : (((Object)((Object)TimeUnit.MINUTES)).equals((Object)unit) ? this.bounded(153722867L) : (((Object)((Object)TimeUnit.HOURS)).equals((Object)unit) ? this.bounded(2562047L) : (((Object)((Object)TimeUnit.DAYS)).equals((Object)unit) ? this.bounded(106751L) : -106751L <= (v = TimeUnit.DAYS.convert(length, unit)) && v <= 106751L)))))))) {
            throw new IllegalArgumentException(new StringBuilder(20).append("requirement failed: ").append((Object)"Duration is limited to +-(2^63-1)ns (ca. 292 years)").toString());
        }
    }
}

