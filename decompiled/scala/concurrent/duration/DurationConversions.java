/*
 * Decompiled with CFR 0.152.
 */
package scala.concurrent.duration;

import java.util.concurrent.TimeUnit;
import scala.concurrent.duration.FiniteDuration;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\t}gaB\u001e=!\u0003\r\ta\u0011\u0005\u0006\u0011\u0002!\t!\u0013\u0005\u0006\u001b\u00021\tB\u0014\u0005\u00069\u0002!\t!\u0018\u0005\u0006=\u0002!\t!\u0018\u0005\u0006?\u0002!\t!\u0018\u0005\u0006A\u0002!\t!\u0018\u0005\u0006C\u0002!\t!\u0018\u0005\u0006E\u0002!\t!\u0018\u0005\u0006G\u0002!\t!\u0018\u0005\u0006I\u0002!\t!\u0018\u0005\u0006K\u0002!\t!\u0018\u0005\u0006M\u0002!\t!\u0018\u0005\u0006O\u0002!\t!\u0018\u0005\u0006Q\u0002!\t!\u0018\u0005\u0006S\u0002!\t!\u0018\u0005\u0006U\u0002!\t!\u0018\u0005\u0006W\u0002!\t!\u0018\u0005\u0006Y\u0002!\t!\u0018\u0005\u0006[\u0002!\t!\u0018\u0005\u0006]\u0002!\t!\u0018\u0005\u0006_\u0002!\t!\u0018\u0005\u0006a\u0002!\t!\u0018\u0005\u00069\u0002!\t!\u001d\u0005\u0007=\u0002!\t!a\u0019\t\r}\u0003A\u0011AA<\u0011\u0019\u0001\u0007\u0001\"\u0001\u0002\f\"1\u0011\r\u0001C\u0001\u0003?CaA\u0019\u0001\u0005\u0002\u0005M\u0006BB2\u0001\t\u0003\t9\r\u0003\u0004e\u0001\u0011\u0005\u00111\u001c\u0005\u0007K\u0002!\t!a<\t\r\u0019\u0004A\u0011\u0001B\u0002\u0011\u00199\u0007\u0001\"\u0001\u0003\u0018!1\u0001\u000e\u0001C\u0001\u0005WAa!\u001b\u0001\u0005\u0002\t}\u0002B\u00026\u0001\t\u0003\u0011\u0019\u0006\u0003\u0004l\u0001\u0011\u0005!q\r\u0005\u0007Y\u0002!\tAa\u001f\t\r5\u0004A\u0011\u0001BH\u0011\u0019q\u0007\u0001\"\u0001\u0003$\"1q\u000e\u0001C\u0001\u0005oCa\u0001\u001d\u0001\u0005\u0002\t-w!B>=\u0011\u0003ah!B\u001e=\u0011\u0003i\bbBA\u0002Y\u0011\u0005\u0011Q\u0001\u0004\n\u0003\u000fa\u0003\u0013aI\u0001\u0003\u0013!q!!\u0004/\u0005\u0003\ty\u0001C\u0004\u0002\u001892\t!!\u0007\b\u000f\u0005\u001dB\u0006c\u0001\u0002*\u00199\u0011Q\u0006\u0017\t\u0002\u0005=\u0002bBA\u0002e\u0011\u0005\u0011\u0011H\u0003\u0006\u0003\u001b\u0011\u0004a\u0014\u0005\b\u0003/\u0011D\u0011AA\u001e\u000f\u001d\ty\u0004\fE\u0002\u0003\u00032q!a\u0011-\u0011\u0003\t)\u0005C\u0004\u0002\u0004]\"\t!a\u0014\u0006\r\u00055q\u0007AA)\u0011\u001d\t9b\u000eC\u0001\u0003/\u00121\u0003R;sCRLwN\\\"p]Z,'o]5p]NT!!\u0010 \u0002\u0011\u0011,(/\u0019;j_:T!a\u0010!\u0002\u0015\r|gnY;se\u0016tGOC\u0001B\u0003\u0015\u00198-\u00197b\u0007\u0001\u0019\"\u0001\u0001#\u0011\u0005\u00153U\"\u0001!\n\u0005\u001d\u0003%aA!os\u00061A%\u001b8ji\u0012\"\u0012A\u0013\t\u0003\u000b.K!\u0001\u0014!\u0003\tUs\u0017\u000e^\u0001\u000bIV\u0014\u0018\r^5p]&sGCA(T!\t\u0001\u0016+D\u0001=\u0013\t\u0011FH\u0001\bGS:LG/\u001a#ve\u0006$\u0018n\u001c8\t\u000bQ\u0013\u0001\u0019A+\u0002\tUt\u0017\u000e\u001e\t\u0003-fs!\u0001U,\n\u0005ac\u0014a\u00029bG.\fw-Z\u0005\u00035n\u0013\u0001\u0002V5nKVs\u0017\u000e\u001e\u0006\u00031r\n1B\\1o_N,7m\u001c8egV\tq*A\u0003oC:|7/\u0001\u0006oC:|7/Z2p]\u0012\fAA\\1o_\u0006aQ.[2s_N,7m\u001c8eg\u00061Q.[2s_N\f1\"\\5de>\u001cXmY8oI\u0006)Q.[2s_\u0006aQ.\u001b7mSN,7m\u001c8eg\u00061Q.\u001b7mSN\f1\"\\5mY&\u001cXmY8oI\u0006)Q.\u001b7mS\u000691/Z2p]\u0012\u001c\u0018AB:fG>tG-A\u0004nS:,H/Z:\u0002\r5Lg.\u001e;f\u0003\u0015Aw.\u001e:t\u0003\u0011Aw.\u001e:\u0002\t\u0011\f\u0017p]\u0001\u0004I\u0006LXc\u0001:\u0002^Q\u00191/a\u0018\u0015\u0005Q<\bCA;0\u001d\t1x\u000f\u0004\u0001\t\u000ba<\u00029A=\u0002\u0005\u00154\b\u0003\u0002>/\u00037r!\u0001U\u0016\u0002'\u0011+(/\u0019;j_:\u001cuN\u001c<feNLwN\\:\u0011\u0005Ac3C\u0001\u0017\u007f!\t)u0C\u0002\u0002\u0002\u0001\u0013a!\u00118z%\u00164\u0017A\u0002\u001fj]&$h\bF\u0001}\u0005)\u0019E.Y:tS\u001aLWM]\u000b\u0005\u0003\u0017\t\u0019c\u0005\u0002/}\n\t!+E\u0002\u0002\u0012\u0011\u00032!RA\n\u0013\r\t)\u0002\u0011\u0002\b\u001d>$\b.\u001b8h\u0003\u001d\u0019wN\u001c<feR$B!a\u0007\u0002 A\u0019\u0011QD\u0018\u000e\u00039Ba!!\t1\u0001\u0004y\u0015!\u00013\u0005\u000f\u0005\u0015bF1\u0001\u0002\u0010\t\t1)A\u0006ta\u0006t7i\u001c8wKJ$\bcAA\u0016e5\tAFA\u0006ta\u0006t7i\u001c8wKJ$8\u0003\u0002\u001a\u007f\u0003c\u0001R!a\u000b/\u0003gq1AVA\u001b\u0013\r\t9dW\u0001\u0005gB\fg\u000e\u0006\u0002\u0002*Q\u0019q*!\u0010\t\r\u0005\u0005R\u00071\u0001P\u000391'o\\7O_^\u001cuN\u001c<feR\u00042!a\u000b8\u000591'o\\7O_^\u001cuN\u001c<feR\u001cBa\u000e@\u0002HA)\u00111\u0006\u0018\u0002J9\u0019a+a\u0013\n\u0007\u000553,A\u0004ge>lgj\\<\u0015\u0005\u0005\u0005\u0003c\u0001)\u0002T%\u0019\u0011Q\u000b\u001f\u0003\u0011\u0011+\u0017\r\u001a7j]\u0016$B!!\u0015\u0002Z!1\u0011\u0011\u0005\u001eA\u0002=\u00032A^A/\t\u001d\t)c\u0006b\u0001\u0003\u001fAq!!\u0019\u0018\u0001\u0004\tY&A\u0001d+\u0011\t)'a\u001d\u0015\t\u0005\u001d\u0014Q\u000f\u000b\u0005\u0003S\ni\u0007E\u0002\u0002l=r1A^A7\u0011\u0019A\b\u0004q\u0001\u0002pA!!PLA9!\r1\u00181\u000f\u0003\b\u0003KA\"\u0019AA\b\u0011\u001d\t\t\u0007\u0007a\u0001\u0003c*B!!\u001f\u0002\bR!\u00111PAE)\u0011\ti(!!\u0011\u0007\u0005}tFD\u0002w\u0003\u0003Ca\u0001_\rA\u0004\u0005\r\u0005\u0003\u0002>/\u0003\u000b\u00032A^AD\t\u001d\t)#\u0007b\u0001\u0003\u001fAq!!\u0019\u001a\u0001\u0004\t))\u0006\u0003\u0002\u000e\u0006mE\u0003BAH\u0003;#B!!%\u0002\u0016B\u0019\u00111S\u0018\u000f\u0007Y\f)\n\u0003\u0004y5\u0001\u000f\u0011q\u0013\t\u0005u:\nI\nE\u0002w\u00037#q!!\n\u001b\u0005\u0004\ty\u0001C\u0004\u0002bi\u0001\r!!'\u0016\t\u0005\u0005\u0016q\u0016\u000b\u0005\u0003G\u000b\t\f\u0006\u0003\u0002&\u0006%\u0006cAAT_9\u0019a/!+\t\ra\\\u00029AAV!\u0011Qh&!,\u0011\u0007Y\fy\u000bB\u0004\u0002&m\u0011\r!a\u0004\t\u000f\u0005\u00054\u00041\u0001\u0002.V!\u0011QWAb)\u0011\t9,!2\u0015\t\u0005e\u0016Q\u0018\t\u0004\u0003w{cb\u0001<\u0002>\"1\u0001\u0010\ba\u0002\u0003\u007f\u0003BA\u001f\u0018\u0002BB\u0019a/a1\u0005\u000f\u0005\u0015BD1\u0001\u0002\u0010!9\u0011\u0011\r\u000fA\u0002\u0005\u0005W\u0003BAe\u0003/$B!a3\u0002ZR!\u0011QZAi!\r\tym\f\b\u0004m\u0006E\u0007B\u0002=\u001e\u0001\b\t\u0019\u000e\u0005\u0003{]\u0005U\u0007c\u0001<\u0002X\u00129\u0011QE\u000fC\u0002\u0005=\u0001bBA1;\u0001\u0007\u0011Q[\u000b\u0005\u0003;\fY\u000f\u0006\u0003\u0002`\u00065H\u0003BAq\u0003K\u00042!a90\u001d\r1\u0018Q\u001d\u0005\u0007qz\u0001\u001d!a:\u0011\tit\u0013\u0011\u001e\t\u0004m\u0006-HaBA\u0013=\t\u0007\u0011q\u0002\u0005\b\u0003Cr\u0002\u0019AAu+\u0011\t\t0a@\u0015\t\u0005M(\u0011\u0001\u000b\u0005\u0003k\fI\u0010E\u0002\u0002x>r1A^A}\u0011\u0019Ax\u0004q\u0001\u0002|B!!PLA\u007f!\r1\u0018q \u0003\b\u0003Ky\"\u0019AA\b\u0011\u001d\t\tg\ba\u0001\u0003{,BA!\u0002\u0003\u0014Q!!q\u0001B\u000b)\u0011\u0011IA!\u0004\u0011\u0007\t-qFD\u0002w\u0005\u001bAa\u0001\u001f\u0011A\u0004\t=\u0001\u0003\u0002>/\u0005#\u00012A\u001eB\n\t\u001d\t)\u0003\tb\u0001\u0003\u001fAq!!\u0019!\u0001\u0004\u0011\t\"\u0006\u0003\u0003\u001a\t\u001dB\u0003\u0002B\u000e\u0005S!BA!\b\u0003\"A\u0019!qD\u0018\u000f\u0007Y\u0014\t\u0003\u0003\u0004yC\u0001\u000f!1\u0005\t\u0005u:\u0012)\u0003E\u0002w\u0005O!q!!\n\"\u0005\u0004\ty\u0001C\u0004\u0002b\u0005\u0002\rA!\n\u0016\t\t5\"1\b\u000b\u0005\u0005_\u0011i\u0004\u0006\u0003\u00032\tU\u0002c\u0001B\u001a_9\u0019aO!\u000e\t\ra\u0014\u00039\u0001B\u001c!\u0011QhF!\u000f\u0011\u0007Y\u0014Y\u0004B\u0004\u0002&\t\u0012\r!a\u0004\t\u000f\u0005\u0005$\u00051\u0001\u0003:U!!\u0011\tB()\u0011\u0011\u0019E!\u0015\u0015\t\t\u0015#\u0011\n\t\u0004\u0005\u000fzcb\u0001<\u0003J!1\u0001p\ta\u0002\u0005\u0017\u0002BA\u001f\u0018\u0003NA\u0019aOa\u0014\u0005\u000f\u0005\u00152E1\u0001\u0002\u0010!9\u0011\u0011M\u0012A\u0002\t5S\u0003\u0002B+\u0005G\"BAa\u0016\u0003fQ!!\u0011\fB/!\r\u0011Yf\f\b\u0004m\nu\u0003B\u0002=%\u0001\b\u0011y\u0006\u0005\u0003{]\t\u0005\u0004c\u0001<\u0003d\u00119\u0011Q\u0005\u0013C\u0002\u0005=\u0001bBA1I\u0001\u0007!\u0011M\u000b\u0005\u0005S\u00129\b\u0006\u0003\u0003l\teD\u0003\u0002B7\u0005c\u00022Aa\u001c0\u001d\r1(\u0011\u000f\u0005\u0007q\u0016\u0002\u001dAa\u001d\u0011\tit#Q\u000f\t\u0004m\n]DaBA\u0013K\t\u0007\u0011q\u0002\u0005\b\u0003C*\u0003\u0019\u0001B;+\u0011\u0011iHa#\u0015\t\t}$Q\u0012\u000b\u0005\u0005\u0003\u0013)\tE\u0002\u0003\u0004>r1A\u001eBC\u0011\u0019Ah\u0005q\u0001\u0003\bB!!P\fBE!\r1(1\u0012\u0003\b\u0003K1#\u0019AA\b\u0011\u001d\t\tG\na\u0001\u0005\u0013+BA!%\u0003 R!!1\u0013BQ)\u0011\u0011)J!'\u0011\u0007\t]uFD\u0002w\u00053Ca\u0001_\u0014A\u0004\tm\u0005\u0003\u0002>/\u0005;\u00032A\u001eBP\t\u001d\t)c\nb\u0001\u0003\u001fAq!!\u0019(\u0001\u0004\u0011i*\u0006\u0003\u0003&\nMF\u0003\u0002BT\u0005k#BA!+\u0003.B\u0019!1V\u0018\u000f\u0007Y\u0014i\u000b\u0003\u0004yQ\u0001\u000f!q\u0016\t\u0005u:\u0012\t\fE\u0002w\u0005g#q!!\n)\u0005\u0004\ty\u0001C\u0004\u0002b!\u0002\rA!-\u0016\t\te&q\u0019\u000b\u0005\u0005w\u0013I\r\u0006\u0003\u0003>\n\u0005\u0007c\u0001B`_9\u0019aO!1\t\raL\u00039\u0001Bb!\u0011QhF!2\u0011\u0007Y\u00149\rB\u0004\u0002&%\u0012\r!a\u0004\t\u000f\u0005\u0005\u0014\u00061\u0001\u0003FV!!Q\u001aBn)\u0011\u0011yM!8\u0015\t\tE'Q\u001b\t\u0004\u0005'|cb\u0001<\u0003V\"1\u0001P\u000ba\u0002\u0005/\u0004BA\u001f\u0018\u0003ZB\u0019aOa7\u0005\u000f\u0005\u0015\"F1\u0001\u0002\u0010!9\u0011\u0011\r\u0016A\u0002\te\u0007")
public interface DurationConversions {
    public FiniteDuration durationIn(TimeUnit var1);

    public static /* synthetic */ FiniteDuration nanoseconds$(DurationConversions $this) {
        return $this.nanoseconds();
    }

    default public FiniteDuration nanoseconds() {
        return this.durationIn(TimeUnit.NANOSECONDS);
    }

    public static /* synthetic */ FiniteDuration nanos$(DurationConversions $this) {
        return $this.nanos();
    }

    default public FiniteDuration nanos() {
        return this.nanoseconds();
    }

    public static /* synthetic */ FiniteDuration nanosecond$(DurationConversions $this) {
        return $this.nanosecond();
    }

    default public FiniteDuration nanosecond() {
        return this.nanoseconds();
    }

    public static /* synthetic */ FiniteDuration nano$(DurationConversions $this) {
        return $this.nano();
    }

    default public FiniteDuration nano() {
        return this.nanoseconds();
    }

    public static /* synthetic */ FiniteDuration microseconds$(DurationConversions $this) {
        return $this.microseconds();
    }

    default public FiniteDuration microseconds() {
        return this.durationIn(TimeUnit.MICROSECONDS);
    }

    public static /* synthetic */ FiniteDuration micros$(DurationConversions $this) {
        return $this.micros();
    }

    default public FiniteDuration micros() {
        return this.microseconds();
    }

    public static /* synthetic */ FiniteDuration microsecond$(DurationConversions $this) {
        return $this.microsecond();
    }

    default public FiniteDuration microsecond() {
        return this.microseconds();
    }

    public static /* synthetic */ FiniteDuration micro$(DurationConversions $this) {
        return $this.micro();
    }

    default public FiniteDuration micro() {
        return this.microseconds();
    }

    public static /* synthetic */ FiniteDuration milliseconds$(DurationConversions $this) {
        return $this.milliseconds();
    }

    default public FiniteDuration milliseconds() {
        return this.durationIn(TimeUnit.MILLISECONDS);
    }

    public static /* synthetic */ FiniteDuration millis$(DurationConversions $this) {
        return $this.millis();
    }

    default public FiniteDuration millis() {
        return this.milliseconds();
    }

    public static /* synthetic */ FiniteDuration millisecond$(DurationConversions $this) {
        return $this.millisecond();
    }

    default public FiniteDuration millisecond() {
        return this.milliseconds();
    }

    public static /* synthetic */ FiniteDuration milli$(DurationConversions $this) {
        return $this.milli();
    }

    default public FiniteDuration milli() {
        return this.milliseconds();
    }

    public static /* synthetic */ FiniteDuration seconds$(DurationConversions $this) {
        return $this.seconds();
    }

    default public FiniteDuration seconds() {
        return this.durationIn(TimeUnit.SECONDS);
    }

    public static /* synthetic */ FiniteDuration second$(DurationConversions $this) {
        return $this.second();
    }

    default public FiniteDuration second() {
        return this.seconds();
    }

    public static /* synthetic */ FiniteDuration minutes$(DurationConversions $this) {
        return $this.minutes();
    }

    default public FiniteDuration minutes() {
        return this.durationIn(TimeUnit.MINUTES);
    }

    public static /* synthetic */ FiniteDuration minute$(DurationConversions $this) {
        return $this.minute();
    }

    default public FiniteDuration minute() {
        return this.minutes();
    }

    public static /* synthetic */ FiniteDuration hours$(DurationConversions $this) {
        return $this.hours();
    }

    default public FiniteDuration hours() {
        return this.durationIn(TimeUnit.HOURS);
    }

    public static /* synthetic */ FiniteDuration hour$(DurationConversions $this) {
        return $this.hour();
    }

    default public FiniteDuration hour() {
        return this.hours();
    }

    public static /* synthetic */ FiniteDuration days$(DurationConversions $this) {
        return $this.days();
    }

    default public FiniteDuration days() {
        return this.durationIn(TimeUnit.DAYS);
    }

    public static /* synthetic */ FiniteDuration day$(DurationConversions $this) {
        return $this.day();
    }

    default public FiniteDuration day() {
        return this.days();
    }

    public static /* synthetic */ Object nanoseconds$(DurationConversions $this, Object c, Classifier ev) {
        return $this.nanoseconds(c, ev);
    }

    default public <C> Object nanoseconds(C c, Classifier<C> ev) {
        return ev.convert(this.nanoseconds());
    }

    public static /* synthetic */ Object nanos$(DurationConversions $this, Object c, Classifier ev) {
        return $this.nanos(c, ev);
    }

    default public <C> Object nanos(C c, Classifier<C> ev) {
        return this.nanoseconds(c, ev);
    }

    public static /* synthetic */ Object nanosecond$(DurationConversions $this, Object c, Classifier ev) {
        return $this.nanosecond(c, ev);
    }

    default public <C> Object nanosecond(C c, Classifier<C> ev) {
        return this.nanoseconds(c, ev);
    }

    public static /* synthetic */ Object nano$(DurationConversions $this, Object c, Classifier ev) {
        return $this.nano(c, ev);
    }

    default public <C> Object nano(C c, Classifier<C> ev) {
        return this.nanoseconds(c, ev);
    }

    public static /* synthetic */ Object microseconds$(DurationConversions $this, Object c, Classifier ev) {
        return $this.microseconds(c, ev);
    }

    default public <C> Object microseconds(C c, Classifier<C> ev) {
        return ev.convert(this.microseconds());
    }

    public static /* synthetic */ Object micros$(DurationConversions $this, Object c, Classifier ev) {
        return $this.micros(c, ev);
    }

    default public <C> Object micros(C c, Classifier<C> ev) {
        return this.microseconds(c, ev);
    }

    public static /* synthetic */ Object microsecond$(DurationConversions $this, Object c, Classifier ev) {
        return $this.microsecond(c, ev);
    }

    default public <C> Object microsecond(C c, Classifier<C> ev) {
        return this.microseconds(c, ev);
    }

    public static /* synthetic */ Object micro$(DurationConversions $this, Object c, Classifier ev) {
        return $this.micro(c, ev);
    }

    default public <C> Object micro(C c, Classifier<C> ev) {
        return this.microseconds(c, ev);
    }

    public static /* synthetic */ Object milliseconds$(DurationConversions $this, Object c, Classifier ev) {
        return $this.milliseconds(c, ev);
    }

    default public <C> Object milliseconds(C c, Classifier<C> ev) {
        return ev.convert(this.milliseconds());
    }

    public static /* synthetic */ Object millis$(DurationConversions $this, Object c, Classifier ev) {
        return $this.millis(c, ev);
    }

    default public <C> Object millis(C c, Classifier<C> ev) {
        return this.milliseconds(c, ev);
    }

    public static /* synthetic */ Object millisecond$(DurationConversions $this, Object c, Classifier ev) {
        return $this.millisecond(c, ev);
    }

    default public <C> Object millisecond(C c, Classifier<C> ev) {
        return this.milliseconds(c, ev);
    }

    public static /* synthetic */ Object milli$(DurationConversions $this, Object c, Classifier ev) {
        return $this.milli(c, ev);
    }

    default public <C> Object milli(C c, Classifier<C> ev) {
        return this.milliseconds(c, ev);
    }

    public static /* synthetic */ Object seconds$(DurationConversions $this, Object c, Classifier ev) {
        return $this.seconds(c, ev);
    }

    default public <C> Object seconds(C c, Classifier<C> ev) {
        return ev.convert(this.seconds());
    }

    public static /* synthetic */ Object second$(DurationConversions $this, Object c, Classifier ev) {
        return $this.second(c, ev);
    }

    default public <C> Object second(C c, Classifier<C> ev) {
        return this.seconds(c, ev);
    }

    public static /* synthetic */ Object minutes$(DurationConversions $this, Object c, Classifier ev) {
        return $this.minutes(c, ev);
    }

    default public <C> Object minutes(C c, Classifier<C> ev) {
        return ev.convert(this.minutes());
    }

    public static /* synthetic */ Object minute$(DurationConversions $this, Object c, Classifier ev) {
        return $this.minute(c, ev);
    }

    default public <C> Object minute(C c, Classifier<C> ev) {
        return this.minutes(c, ev);
    }

    public static /* synthetic */ Object hours$(DurationConversions $this, Object c, Classifier ev) {
        return $this.hours(c, ev);
    }

    default public <C> Object hours(C c, Classifier<C> ev) {
        return ev.convert(this.hours());
    }

    public static /* synthetic */ Object hour$(DurationConversions $this, Object c, Classifier ev) {
        return $this.hour(c, ev);
    }

    default public <C> Object hour(C c, Classifier<C> ev) {
        return this.hours(c, ev);
    }

    public static /* synthetic */ Object days$(DurationConversions $this, Object c, Classifier ev) {
        return $this.days(c, ev);
    }

    default public <C> Object days(C c, Classifier<C> ev) {
        return ev.convert(this.days());
    }

    public static /* synthetic */ Object day$(DurationConversions $this, Object c, Classifier ev) {
        return $this.day(c, ev);
    }

    default public <C> Object day(C c, Classifier<C> ev) {
        return this.days(c, ev);
    }

    public static void $init$(DurationConversions $this) {
    }

    public static interface Classifier<C> {
        public Object convert(FiniteDuration var1);
    }
}

