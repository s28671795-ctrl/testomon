/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime;

import scala.Proxy;
import scala.math.Numeric$ShortIsIntegral$;
import scala.math.Ordered;
import scala.math.Ordering$Short$;
import scala.math.ScalaNumericAnyConversions;
import scala.reflect.ScalaSignature;
import scala.runtime.OrderedProxy;
import scala.runtime.RichShort$;
import scala.runtime.ScalaNumberProxy;
import scala.runtime.ScalaWholeNumberProxy;

@ScalaSignature(bytes="\u0006\u0005\u0005}d\u0001B\u0012%\u0005%B\u0001\"\u000e\u0001\u0003\u0006\u0004%\tA\u000e\u0005\to\u0001\u0011\t\u0011)A\u0005e!)\u0001\b\u0001C\u0001s!)A\b\u0001C\t{!)a\n\u0001C\t\u001f\")q\u000b\u0001C!1\")A\f\u0001C!;\")\u0011\r\u0001C!E\")a\r\u0001C!O\")1\u000e\u0001C!Y\")\u0001\u000f\u0001C!m!)\u0011\u000f\u0001C!e\")a\u000f\u0001C!m!)q\u000f\u0001C!q\")1\u0010\u0001C!y\"9a\u0010AA\u0001\n\u0003z\b\"CA\u0001\u0001\u0005\u0005I\u0011IA\u0002\u000f%\ty\u0001JA\u0001\u0012\u0003\t\tB\u0002\u0005$I\u0005\u0005\t\u0012AA\n\u0011\u0019A4\u0003\"\u0001\u0002\u001c!9\u0011QD\n\u0005\u0006\u0005}\u0001bBA\u0013'\u0011\u0015\u0011q\u0005\u0005\b\u0003W\u0019BQAA\u0017\u0011\u001d\t\td\u0005C\u0003\u0003gAq!a\u000e\u0014\t\u000b\tI\u0004C\u0004\u0002>M!)!a\u0010\t\u000f\u0005\r3\u0003\"\u0002\u0002F!9\u0011\u0011J\n\u0005\u0006\u0005-\u0003bBA('\u0011\u0015\u0011\u0011\u000b\u0005\b\u0003+\u001aBQAA,\u0011\u001d\tYf\u0005C\u0003\u0003;Bq!!\u001a\u0014\t\u000b\t9\u0007C\u0005\u0002pM\t\t\u0011\"\u0002\u0002r!I\u0011QO\n\u0002\u0002\u0013\u0015\u0011q\u000f\u0002\n%&\u001c\u0007n\u00155peRT!!\n\u0014\u0002\u000fI,h\u000e^5nK*\tq%A\u0003tG\u0006d\u0017m\u0001\u0001\u0014\u0007\u0001Qc\u0006\u0005\u0002,Y5\ta%\u0003\u0002.M\t1\u0011I\\=WC2\u00042a\f\u00193\u001b\u0005!\u0013BA\u0019%\u0005U\u00196-\u00197b/\"|G.\u001a(v[\n,'\u000f\u0015:pqf\u0004\"aK\u001a\n\u0005Q2#!B*i_J$\u0018\u0001B:fY\u001a,\u0012AM\u0001\u0006g\u0016dg\rI\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0005iZ\u0004CA\u0018\u0001\u0011\u0015)4\u00011\u00013\u0003\rqW/\\\u000b\u0002}A\u0011qh\u0013\b\u0003\u0001\"s!!\u0011$\u000f\u0005\t+U\"A\"\u000b\u0005\u0011C\u0013A\u0002\u001fs_>$h(C\u0001(\u0013\t9e%\u0001\u0003nCRD\u0017BA%K\u0003\u001dqU/\\3sS\u000eT!a\u0012\u0014\u000b\u00051k\u0015aD*i_J$\u0018j]%oi\u0016<'/\u00197\u000b\u0005%S\u0015aA8sIV\t\u0001\u000b\u0005\u0002R):\u0011\u0001IU\u0005\u0003'*\u000b\u0001b\u0014:eKJLgn\u001a\u0006\u0003+Z\u000bQa\u00155peRT!a\u0015&\u0002\u0017\u0011|WO\u00197f-\u0006dW/Z\u000b\u00023B\u00111FW\u0005\u00037\u001a\u0012a\u0001R8vE2,\u0017A\u00034m_\u0006$h+\u00197vKV\ta\f\u0005\u0002,?&\u0011\u0001M\n\u0002\u0006\r2|\u0017\r^\u0001\nY>twMV1mk\u0016,\u0012a\u0019\t\u0003W\u0011L!!\u001a\u0014\u0003\t1{gnZ\u0001\tS:$h+\u00197vKV\t\u0001\u000e\u0005\u0002,S&\u0011!N\n\u0002\u0004\u0013:$\u0018!\u00032zi\u00164\u0016\r\\;f+\u0005i\u0007CA\u0016o\u0013\tygE\u0001\u0003CsR,\u0017AC:i_J$h+\u00197vK\u0006a\u0011n\u001d,bY&$7\u000b[8siV\t1\u000f\u0005\u0002,i&\u0011QO\n\u0002\b\u0005>|G.Z1o\u0003\r\t'm]\u0001\u0004[\u0006DHC\u0001\u001az\u0011\u0015Qh\u00021\u00013\u0003\u0011!\b.\u0019;\u0002\u00075Lg\u000e\u0006\u00023{\")!p\u0004a\u0001e\u0005A\u0001.Y:i\u0007>$W\rF\u0001i\u0003\u0019)\u0017/^1mgR\u00191/!\u0002\t\u0013\u0005\u001d\u0011#!AA\u0002\u0005%\u0011a\u0001=%cA\u00191&a\u0003\n\u0007\u00055aEA\u0002B]f\f\u0011BU5dQNCwN\u001d;\u0011\u0005=\u001a2cA\n\u0002\u0016A\u00191&a\u0006\n\u0007\u0005eaE\u0001\u0004B]f\u0014VM\u001a\u000b\u0003\u0003#\tQB\\;nI\u0015DH/\u001a8tS>tGc\u0001 \u0002\"!1\u00111E\u000bA\u0002i\nQ\u0001\n;iSN\fQb\u001c:eI\u0015DH/\u001a8tS>tGc\u0001)\u0002*!1\u00111\u0005\fA\u0002i\nQ\u0003Z8vE2,g+\u00197vK\u0012*\u0007\u0010^3og&|g\u000eF\u0002Z\u0003_Aa!a\t\u0018\u0001\u0004Q\u0014\u0001\u00064m_\u0006$h+\u00197vK\u0012*\u0007\u0010^3og&|g\u000eF\u0002_\u0003kAa!a\t\u0019\u0001\u0004Q\u0014a\u00057p]\u001e4\u0016\r\\;fI\u0015DH/\u001a8tS>tGcA2\u0002<!1\u00111E\rA\u0002i\n!#\u001b8u-\u0006dW/\u001a\u0013fqR,gn]5p]R\u0019\u0001.!\u0011\t\r\u0005\r\"\u00041\u0001;\u0003M\u0011\u0017\u0010^3WC2,X\rJ3yi\u0016t7/[8o)\ri\u0017q\t\u0005\u0007\u0003GY\u0002\u0019\u0001\u001e\u0002)MDwN\u001d;WC2,X\rJ3yi\u0016t7/[8o)\r\u0011\u0014Q\n\u0005\u0007\u0003Ga\u0002\u0019\u0001\u001e\u0002-%\u001ch+\u00197jINCwN\u001d;%Kb$XM\\:j_:$2a]A*\u0011\u0019\t\u0019#\ba\u0001u\u0005i\u0011MY:%Kb$XM\\:j_:$2AMA-\u0011\u0019\t\u0019C\ba\u0001u\u0005iQ.\u0019=%Kb$XM\\:j_:$B!a\u0018\u0002dQ\u0019!'!\u0019\t\u000bi|\u0002\u0019\u0001\u001a\t\r\u0005\rr\u00041\u0001;\u00035i\u0017N\u001c\u0013fqR,gn]5p]R!\u0011\u0011NA7)\r\u0011\u00141\u000e\u0005\u0006u\u0002\u0002\rA\r\u0005\u0007\u0003G\u0001\u0003\u0019\u0001\u001e\u0002%!\f7\u000f[\"pI\u0016$S\r\u001f;f]NLwN\u001c\u000b\u0004\u007f\u0006M\u0004BBA\u0012C\u0001\u0007!(\u0001\tfcV\fGn\u001d\u0013fqR,gn]5p]R!\u0011\u0011PA?)\r\u0019\u00181\u0010\u0005\n\u0003\u000f\u0011\u0013\u0011!a\u0001\u0003\u0013Aa!a\t#\u0001\u0004Q\u0004")
public final class RichShort
implements ScalaWholeNumberProxy<Object> {
    private final short self;

    public static boolean equals$extension(short $this, Object x$1) {
        return RichShort$.MODULE$.equals$extension($this, x$1);
    }

    public static int hashCode$extension(short $this) {
        return Short.hashCode($this);
    }

    public static short min$extension(short $this, short that) {
        return (short)Math.min($this, that);
    }

    public static short max$extension(short $this, short that) {
        return (short)Math.max($this, that);
    }

    public static short abs$extension(short $this) {
        return (short)Math.abs($this);
    }

    public static boolean isValidShort$extension(short $this) {
        return true;
    }

    public static short shortValue$extension(short $this) {
        return $this;
    }

    public static byte byteValue$extension(short $this) {
        return (byte)$this;
    }

    public static int intValue$extension(short $this) {
        return $this;
    }

    public static long longValue$extension(short $this) {
        return $this;
    }

    public static float floatValue$extension(short $this) {
        return $this;
    }

    public static double doubleValue$extension(short $this) {
        return $this;
    }

    public static Ordering$Short$ ord$extension(short $this) {
        return Ordering$Short$.MODULE$;
    }

    public static Numeric$ShortIsIntegral$ num$extension(short $this) {
        return Numeric$ShortIsIntegral$.MODULE$;
    }

    @Override
    public boolean isWhole() {
        return ScalaWholeNumberProxy.isWhole$(this);
    }

    @Override
    public Object sign() {
        return ScalaNumberProxy.sign$(this);
    }

    @Override
    public int signum() {
        return ScalaNumberProxy.signum$(this);
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
    public boolean isValidByte() {
        return ScalaNumericAnyConversions.isValidByte$(this);
    }

    @Override
    public boolean isValidInt() {
        return ScalaNumericAnyConversions.isValidInt$(this);
    }

    @Override
    public boolean isValidChar() {
        return ScalaNumericAnyConversions.isValidChar$(this);
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
    public short self() {
        return this.self;
    }

    public Numeric$ShortIsIntegral$ num() {
        this.self();
        return Numeric$ShortIsIntegral$.MODULE$;
    }

    public Ordering$Short$ ord() {
        this.self();
        return Ordering$Short$.MODULE$;
    }

    @Override
    public double doubleValue() {
        return this.self();
    }

    @Override
    public float floatValue() {
        return this.self();
    }

    @Override
    public long longValue() {
        return this.self();
    }

    @Override
    public int intValue() {
        return this.self();
    }

    @Override
    public byte byteValue() {
        return (byte)this.self();
    }

    @Override
    public short shortValue() {
        return this.self();
    }

    @Override
    public boolean isValidShort() {
        this.self();
        return true;
    }

    @Override
    public short abs() {
        short abs$extension_$this = this.self();
        return (short)Math.abs(abs$extension_$this);
    }

    @Override
    public short max(short that) {
        short max$extension_$this = this.self();
        return (short)Math.max(max$extension_$this, that);
    }

    @Override
    public short min(short that) {
        short min$extension_$this = this.self();
        return (short)Math.min(min$extension_$this, that);
    }

    @Override
    public int hashCode() {
        return Short.hashCode(this.self());
    }

    @Override
    public boolean equals(Object x$1) {
        return RichShort$.MODULE$.equals$extension(this.self(), x$1);
    }

    public RichShort(short self) {
        this.self = self;
    }
}

