/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime;

import scala.Proxy;
import scala.math.Fractional;
import scala.math.Numeric$DoubleIsFractional$;
import scala.math.Ordered;
import scala.math.Ordering;
import scala.math.Ordering$Double$TotalOrdering$;
import scala.math.ScalaNumericAnyConversions;
import scala.reflect.ScalaSignature;
import scala.runtime.FractionalProxy;
import scala.runtime.OrderedProxy;
import scala.runtime.RichDouble$;
import scala.runtime.ScalaNumberProxy;

@ScalaSignature(bytes="\u0006\u0005\t=b\u0001B!C\u0005\u001dC\u0001b\u0015\u0001\u0003\u0006\u0004%\t\u0001\u0016\u0005\t+\u0002\u0011\t\u0011)A\u0005!\")a\u000b\u0001C\u0001/\")!\f\u0001C\t7\")1\r\u0001C\tI\")\u0001\u000e\u0001C!)\")\u0011\u000e\u0001C!U\")a\u000e\u0001C!_\")1\u000f\u0001C!i\")\u0001\u0010\u0001C!s\")Q\u0010\u0001C!}\"9\u0011Q\u0001\u0001\u0005B\u0005\u001d\u0001bBA\b\u0001\u0011\u0005\u0013q\u0001\u0005\b\u0003#\u0001A\u0011IA\u0004\u0011\u001d\t\u0019\u0002\u0001C!\u0003\u000fAq!!\u0006\u0001\t\u0003\n9\u0001C\u0004\u0002\u0018\u0001!\t!a\u0002\t\u000f\u0005e\u0001\u0001\"\u0001\u0002\b!9\u00111\u0004\u0001\u0005\u0002\u0005\u001d\u0001bBA\u000f\u0001\u0011\u0005\u0011q\u0001\u0005\b\u0003?\u0001A\u0011AA\u0004\u0011\u0019\t\t\u0003\u0001C!)\"9\u00111\u0005\u0001\u0005B\u0005\u0015\u0002bBA\u0016\u0001\u0011\u0005\u0013Q\u0006\u0005\u0007\u0003c\u0001A\u0011\t;\t\r\u0005\u001d\u0003\u0001\"\u0001p\u0011\u0019\tI\u0005\u0001C\u0001)\"1\u00111\n\u0001\u0005\u0002QCa!!\u0014\u0001\t\u0003!\u0006BBA(\u0001\u0011\u0005A\u000bC\u0005\u0002R\u0001\t\t\u0011\"\u0011\u0002T!I\u0011Q\u000b\u0001\u0002\u0002\u0013\u0005\u0013qK\u0004\n\u0003G\u0012\u0015\u0011!E\u0001\u0003K2\u0001\"\u0011\"\u0002\u0002#\u0005\u0011q\r\u0005\u0007-\n\"\t!a\u001c\t\u000f\u0005E$\u0005\"\u0002\u0002t!9\u0011\u0011\u0010\u0012\u0005\u0006\u0005m\u0004bBA@E\u0011\u0015\u0011\u0011\u0011\u0005\b\u0003\u000b\u0013CQAAD\u0011\u001d\tYI\tC\u0003\u0003\u001bCq!!%#\t\u000b\t\u0019\nC\u0004\u0002\u0018\n\")!!'\t\u000f\u0005u%\u0005\"\u0002\u0002 \"9\u00111\u0015\u0012\u0005\u0006\u0005\u0015\u0006bBAUE\u0011\u0015\u00111\u0016\u0005\b\u0003_\u0013CQAAY\u0011\u001d\t)L\tC\u0003\u0003oCq!a/#\t\u000b\ti\fC\u0004\u0002B\n\")!a1\t\u000f\u0005\u001d'\u0005\"\u0002\u0002J\"9\u0011Q\u001a\u0012\u0005\u0006\u0005=\u0007bBAjE\u0011\u0015\u0011Q\u001b\u0005\b\u00033\u0014CQAAn\u0011\u001d\tyN\tC\u0003\u0003CDq!!:#\t\u000b\t9\u000fC\u0004\u0002p\n\")!!=\t\u000f\u0005e(\u0005\"\u0002\u0002|\"9!\u0011\u0001\u0012\u0005\u0006\t\r\u0001b\u0002B\u0004E\u0011\u0015!\u0011\u0002\u0005\b\u0005\u001b\u0011CQ\u0001B\b\u0011\u001d\u0011\u0019B\tC\u0003\u0005+AqA!\u0007#\t\u000b\u0011Y\u0002C\u0005\u0003 \t\n\t\u0011\"\u0002\u0003\"!I!Q\u0005\u0012\u0002\u0002\u0013\u0015!q\u0005\u0002\u000b%&\u001c\u0007\u000eR8vE2,'BA\"E\u0003\u001d\u0011XO\u001c;j[\u0016T\u0011!R\u0001\u0006g\u000e\fG.Y\u0002\u0001'\r\u0001\u0001\n\u0014\t\u0003\u0013*k\u0011\u0001R\u0005\u0003\u0017\u0012\u0013a!\u00118z-\u0006d\u0007cA'O!6\t!)\u0003\u0002P\u0005\nyaI]1di&|g.\u00197Qe>D\u0018\u0010\u0005\u0002J#&\u0011!\u000b\u0012\u0002\u0007\t>,(\r\\3\u0002\tM,GNZ\u000b\u0002!\u0006)1/\u001a7gA\u00051A(\u001b8jiz\"\"\u0001W-\u0011\u00055\u0003\u0001\"B*\u0004\u0001\u0004\u0001\u0016a\u00018v[V\tA\fE\u0002^ABs!!\u00130\n\u0005}#\u0015a\u00029bG.\fw-Z\u0005\u0003C\n\u0014!B\u0012:bGRLwN\\1m\u0015\tyF)A\u0002pe\u0012,\u0012!\u001a\t\u0004;\u001a\u0004\u0016BA4c\u0005!y%\u000fZ3sS:<\u0017a\u00033pk\ndWMV1mk\u0016\f!B\u001a7pCR4\u0016\r\\;f+\u0005Y\u0007CA%m\u0013\tiGIA\u0003GY>\fG/A\u0005m_:<g+\u00197vKV\t\u0001\u000f\u0005\u0002Jc&\u0011!\u000f\u0012\u0002\u0005\u0019>tw-\u0001\u0005j]R4\u0016\r\\;f+\u0005)\bCA%w\u0013\t9HIA\u0002J]R\f\u0011BY=uKZ\u000bG.^3\u0016\u0003i\u0004\"!S>\n\u0005q$%\u0001\u0002\"zi\u0016\f!b\u001d5peR4\u0016\r\\;f+\u0005y\bcA%\u0002\u0002%\u0019\u00111\u0001#\u0003\u000bMCwN\u001d;\u0002\u000f%\u001cx\u000b[8mKV\u0011\u0011\u0011\u0002\t\u0004\u0013\u0006-\u0011bAA\u0007\t\n9!i\\8mK\u0006t\u0017aC5t-\u0006d\u0017\u000e\u001a\"zi\u0016\fA\"[:WC2LGm\u00155peR\f1\"[:WC2LGm\u00115be\u0006Q\u0011n\u001d,bY&$\u0017J\u001c;\u0002\u000b%\u001ch*\u0019(\u0002\u0015%\u001c\u0018J\u001c4j]&$\u00180\u0001\u0005jg\u001aKg.\u001b;f\u00035I7\u000fU8t\u0013:4\u0017N\\5us\u0006i\u0011n\u001d(fO&sg-\u001b8jif\f1!\u00192t\u0003\ri\u0017\r\u001f\u000b\u0004!\u0006\u001d\u0002BBA\u0015/\u0001\u0007\u0001+\u0001\u0003uQ\u0006$\u0018aA7j]R\u0019\u0001+a\f\t\r\u0005%\u0002\u00041\u0001Q\u0003\u0019\u0019\u0018n\u001a8v[\"Z\u0011$!\u000e\u0002<\u0005u\u0012\u0011IA\"!\rI\u0015qG\u0005\u0004\u0003s!%A\u00033faJ,7-\u0019;fI\u00069Q.Z:tC\u001e,\u0017EAA \u0003\u0011\u001b\u0018n\u001a8v[\u0002\"w.Z:!]>$\b\u0005[1oI2,\u0007%\f\u0019/a\u0001z'\u000f\t#pk\ndWM\f(b\u001dn\u0002So]3!ANLwM\u001c1![\u0016$\bn\u001c3!S:\u001cH/Z1e\u0003\u0015\u0019\u0018N\\2fC\t\t)%\u0001\u00043]E\u001ad\u0006M\u0001\u0006e>,h\u000eZ\u0001\u0005G\u0016LG.A\u0003gY>|'/A\u0005u_J\u000bG-[1og\u0006IAo\u001c#fOJ,Wm]\u0001\tQ\u0006\u001c\bnQ8eKR\tQ/\u0001\u0004fcV\fGn\u001d\u000b\u0005\u0003\u0013\tI\u0006C\u0005\u0002\\\u0001\n\t\u00111\u0001\u0002^\u0005\u0019\u0001\u0010J\u0019\u0011\u0007%\u000by&C\u0002\u0002b\u0011\u00131!\u00118z\u0003)\u0011\u0016n\u00195E_V\u0014G.\u001a\t\u0003\u001b\n\u001a2AIA5!\rI\u00151N\u0005\u0004\u0003[\"%AB!osJ+g\r\u0006\u0002\u0002f\u0005ia.^7%Kb$XM\\:j_:$2\u0001XA;\u0011\u0019\t9\b\na\u00011\u0006)A\u0005\u001e5jg\u0006iqN\u001d3%Kb$XM\\:j_:$2!ZA?\u0011\u0019\t9(\na\u00011\u0006)Bm\\;cY\u00164\u0016\r\\;fI\u0015DH/\u001a8tS>tGc\u0001)\u0002\u0004\"1\u0011q\u000f\u0014A\u0002a\u000bAC\u001a7pCR4\u0016\r\\;fI\u0015DH/\u001a8tS>tGcA6\u0002\n\"1\u0011qO\u0014A\u0002a\u000b1\u0003\\8oOZ\u000bG.^3%Kb$XM\\:j_:$2\u0001]AH\u0011\u0019\t9\b\u000ba\u00011\u0006\u0011\u0012N\u001c;WC2,X\rJ3yi\u0016t7/[8o)\r)\u0018Q\u0013\u0005\u0007\u0003oJ\u0003\u0019\u0001-\u0002'\tLH/\u001a,bYV,G%\u001a=uK:\u001c\u0018n\u001c8\u0015\u0007i\fY\n\u0003\u0004\u0002x)\u0002\r\u0001W\u0001\u0015g\"|'\u000f\u001e,bYV,G%\u001a=uK:\u001c\u0018n\u001c8\u0015\u0007}\f\t\u000b\u0003\u0004\u0002x-\u0002\r\u0001W\u0001\u0012SN<\u0006n\u001c7fI\u0015DH/\u001a8tS>tG\u0003BA\u0005\u0003OCa!a\u001e-\u0001\u0004A\u0016!F5t-\u0006d\u0017\u000e\u001a\"zi\u0016$S\r\u001f;f]NLwN\u001c\u000b\u0005\u0003\u0013\ti\u000b\u0003\u0004\u0002x5\u0002\r\u0001W\u0001\u0017SN4\u0016\r\\5e'\"|'\u000f\u001e\u0013fqR,gn]5p]R!\u0011\u0011BAZ\u0011\u0019\t9H\fa\u00011\u0006)\u0012n\u001d,bY&$7\t[1sI\u0015DH/\u001a8tS>tG\u0003BA\u0005\u0003sCa!a\u001e0\u0001\u0004A\u0016\u0001F5t-\u0006d\u0017\u000eZ%oi\u0012*\u0007\u0010^3og&|g\u000e\u0006\u0003\u0002\n\u0005}\u0006BBA<a\u0001\u0007\u0001,A\bjg:\u000bg\nJ3yi\u0016t7/[8o)\u0011\tI!!2\t\r\u0005]\u0014\u00071\u0001Y\u0003QI7/\u00138gS:LG/\u001f\u0013fqR,gn]5p]R!\u0011\u0011BAf\u0011\u0019\t9H\ra\u00011\u0006\u0011\u0012n\u001d$j]&$X\rJ3yi\u0016t7/[8o)\u0011\tI!!5\t\r\u0005]4\u00071\u0001Y\u0003]I7\u000fU8t\u0013:4\u0017N\\5us\u0012*\u0007\u0010^3og&|g\u000e\u0006\u0003\u0002\n\u0005]\u0007BBA<i\u0001\u0007\u0001,A\fjg:+w-\u00138gS:LG/\u001f\u0013fqR,gn]5p]R!\u0011\u0011BAo\u0011\u0019\t9(\u000ea\u00011\u0006i\u0011MY:%Kb$XM\\:j_:$2\u0001UAr\u0011\u0019\t9H\u000ea\u00011\u0006iQ.\u0019=%Kb$XM\\:j_:$B!!;\u0002nR\u0019\u0001+a;\t\r\u0005%r\u00071\u0001Q\u0011\u0019\t9h\u000ea\u00011\u0006iQ.\u001b8%Kb$XM\\:j_:$B!a=\u0002xR\u0019\u0001+!>\t\r\u0005%\u0002\b1\u0001Q\u0011\u0019\t9\b\u000fa\u00011\u0006\u00012/[4ok6$S\r\u001f;f]NLwN\u001c\u000b\u0004k\u0006u\bBBA<s\u0001\u0007\u0001\fK\u0006:\u0003k\tY$!\u0010\u0002B\u0005\r\u0013a\u0004:pk:$G%\u001a=uK:\u001c\u0018n\u001c8\u0015\u0007A\u0014)\u0001\u0003\u0004\u0002xi\u0002\r\u0001W\u0001\u000fG\u0016LG\u000eJ3yi\u0016t7/[8o)\r\u0001&1\u0002\u0005\u0007\u0003oZ\u0004\u0019\u0001-\u0002\u001f\u0019dwn\u001c:%Kb$XM\\:j_:$2\u0001\u0015B\t\u0011\u0019\t9\b\u0010a\u00011\u0006\u0019Bo\u001c*bI&\fgn\u001d\u0013fqR,gn]5p]R\u0019\u0001Ka\u0006\t\r\u0005]T\b1\u0001Y\u0003M!x\u000eR3he\u0016,7\u000fJ3yi\u0016t7/[8o)\r\u0001&Q\u0004\u0005\u0007\u0003or\u0004\u0019\u0001-\u0002%!\f7\u000f[\"pI\u0016$S\r\u001f;f]NLwN\u001c\u000b\u0005\u0003'\u0012\u0019\u0003\u0003\u0004\u0002x}\u0002\r\u0001W\u0001\u0011KF,\u0018\r\\:%Kb$XM\\:j_:$BA!\u000b\u0003.Q!\u0011\u0011\u0002B\u0016\u0011%\tY\u0006QA\u0001\u0002\u0004\ti\u0006\u0003\u0004\u0002x\u0001\u0003\r\u0001\u0017")
public final class RichDouble
implements FractionalProxy<Object> {
    private final double self;

    public static boolean equals$extension(double $this, Object x$1) {
        return RichDouble$.MODULE$.equals$extension($this, x$1);
    }

    public static int hashCode$extension(double $this) {
        return Double.hashCode($this);
    }

    public static double toDegrees$extension(double $this) {
        return Math.toDegrees($this);
    }

    public static double toRadians$extension(double $this) {
        return Math.toRadians($this);
    }

    public static double floor$extension(double $this) {
        return Math.floor($this);
    }

    public static double ceil$extension(double $this) {
        return Math.ceil($this);
    }

    public static long round$extension(double $this) {
        return Math.round($this);
    }

    public static int signum$extension(double $this) {
        return (int)Math.signum($this);
    }

    public static double min$extension(double $this, double that) {
        return Math.min($this, that);
    }

    public static double max$extension(double $this, double that) {
        return Math.max($this, that);
    }

    public static double abs$extension(double $this) {
        return Math.abs($this);
    }

    public static boolean isNegInfinity$extension(double $this) {
        return RichDouble$.MODULE$.isNegInfinity$extension($this);
    }

    public static boolean isPosInfinity$extension(double $this) {
        return RichDouble$.MODULE$.isPosInfinity$extension($this);
    }

    public static boolean isFinite$extension(double $this) {
        return Double.isFinite($this);
    }

    public static boolean isInfinity$extension(double $this) {
        return Double.isInfinite($this);
    }

    public static boolean isNaN$extension(double $this) {
        return Double.isNaN($this);
    }

    public static boolean isValidInt$extension(double $this) {
        return RichDouble$.MODULE$.isValidInt$extension($this);
    }

    public static boolean isValidChar$extension(double $this) {
        return RichDouble$.MODULE$.isValidChar$extension($this);
    }

    public static boolean isValidShort$extension(double $this) {
        return RichDouble$.MODULE$.isValidShort$extension($this);
    }

    public static boolean isValidByte$extension(double $this) {
        return RichDouble$.MODULE$.isValidByte$extension($this);
    }

    public static boolean isWhole$extension(double $this) {
        return RichDouble$.MODULE$.isWhole$extension($this);
    }

    public static short shortValue$extension(double $this) {
        return (short)$this;
    }

    public static byte byteValue$extension(double $this) {
        return (byte)$this;
    }

    public static int intValue$extension(double $this) {
        return (int)$this;
    }

    public static long longValue$extension(double $this) {
        return (long)$this;
    }

    public static float floatValue$extension(double $this) {
        return (float)$this;
    }

    public static double doubleValue$extension(double $this) {
        return $this;
    }

    public static Ordering<Object> ord$extension(double $this) {
        return Ordering$Double$TotalOrdering$.MODULE$;
    }

    public static Fractional<Object> num$extension(double $this) {
        return Numeric$DoubleIsFractional$.MODULE$;
    }

    @Override
    public Object sign() {
        return ScalaNumberProxy.sign$(this);
    }

    @Override
    public int compare(Object y) {
        return OrderedProxy.compare$(this, y);
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

    @Override
    public String toString() {
        return Proxy.toString$(this);
    }

    @Override
    public char toChar() {
        return ScalaNumericAnyConversions.toChar$(this);
    }

    @Override
    public byte toByte() {
        return ScalaNumericAnyConversions.toByte$(this);
    }

    @Override
    public short toShort() {
        return ScalaNumericAnyConversions.toShort$(this);
    }

    @Override
    public int toInt() {
        return ScalaNumericAnyConversions.toInt$(this);
    }

    @Override
    public long toLong() {
        return ScalaNumericAnyConversions.toLong$(this);
    }

    @Override
    public float toFloat() {
        return ScalaNumericAnyConversions.toFloat$(this);
    }

    @Override
    public double toDouble() {
        return ScalaNumericAnyConversions.toDouble$(this);
    }

    @Override
    public int unifiedPrimitiveHashcode() {
        return ScalaNumericAnyConversions.unifiedPrimitiveHashcode$(this);
    }

    @Override
    public boolean unifiedPrimitiveEquals(Object x) {
        return ScalaNumericAnyConversions.unifiedPrimitiveEquals$(this, x);
    }

    @Override
    public double self() {
        return this.self;
    }

    @Override
    public Fractional<Object> num() {
        this.self();
        return Numeric$DoubleIsFractional$.MODULE$;
    }

    @Override
    public Ordering<Object> ord() {
        this.self();
        return Ordering$Double$TotalOrdering$.MODULE$;
    }

    @Override
    public double doubleValue() {
        return this.self();
    }

    @Override
    public float floatValue() {
        return (float)this.self();
    }

    @Override
    public long longValue() {
        return (long)this.self();
    }

    @Override
    public int intValue() {
        return (int)this.self();
    }

    @Override
    public byte byteValue() {
        return (byte)this.self();
    }

    @Override
    public short shortValue() {
        return (short)this.self();
    }

    @Override
    public boolean isWhole() {
        return RichDouble$.MODULE$.isWhole$extension(this.self());
    }

    @Override
    public boolean isValidByte() {
        return RichDouble$.MODULE$.isValidByte$extension(this.self());
    }

    @Override
    public boolean isValidShort() {
        return RichDouble$.MODULE$.isValidShort$extension(this.self());
    }

    @Override
    public boolean isValidChar() {
        return RichDouble$.MODULE$.isValidChar$extension(this.self());
    }

    @Override
    public boolean isValidInt() {
        return RichDouble$.MODULE$.isValidInt$extension(this.self());
    }

    public boolean isNaN() {
        return Double.isNaN(this.self());
    }

    public boolean isInfinity() {
        return Double.isInfinite(this.self());
    }

    public boolean isFinite() {
        return Double.isFinite(this.self());
    }

    public boolean isPosInfinity() {
        return RichDouble$.MODULE$.isPosInfinity$extension(this.self());
    }

    public boolean isNegInfinity() {
        return RichDouble$.MODULE$.isNegInfinity$extension(this.self());
    }

    @Override
    public double abs() {
        double abs$extension_$this = this.self();
        return Math.abs(abs$extension_$this);
    }

    @Override
    public double max(double that) {
        double max$extension_$this = this.self();
        return Math.max(max$extension_$this, that);
    }

    @Override
    public double min(double that) {
        double min$extension_$this = this.self();
        return Math.min(min$extension_$this, that);
    }

    @Override
    public int signum() {
        double signum$extension_$this = this.self();
        return (int)Math.signum(signum$extension_$this);
    }

    public long round() {
        double round$extension_$this = this.self();
        return Math.round(round$extension_$this);
    }

    public double ceil() {
        double ceil$extension_$this = this.self();
        return Math.ceil(ceil$extension_$this);
    }

    public double floor() {
        double floor$extension_$this = this.self();
        return Math.floor(floor$extension_$this);
    }

    public double toRadians() {
        double toRadians$extension_$this = this.self();
        return Math.toRadians(toRadians$extension_$this);
    }

    public double toDegrees() {
        double toDegrees$extension_$this = this.self();
        return Math.toDegrees(toDegrees$extension_$this);
    }

    @Override
    public int hashCode() {
        return Double.hashCode(this.self());
    }

    @Override
    public boolean equals(Object x$1) {
        return RichDouble$.MODULE$.equals$extension(this.self(), x$1);
    }

    public RichDouble(double self) {
        this.self = self;
    }
}

