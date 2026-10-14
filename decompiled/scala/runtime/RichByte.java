/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime;

import scala.Proxy;
import scala.math.Numeric$ByteIsIntegral$;
import scala.math.Ordered;
import scala.math.Ordering$Byte$;
import scala.math.ScalaNumericAnyConversions;
import scala.reflect.ScalaSignature;
import scala.runtime.OrderedProxy;
import scala.runtime.RichByte$;
import scala.runtime.ScalaNumberProxy;
import scala.runtime.ScalaWholeNumberProxy;

@ScalaSignature(bytes="\u0006\u0005\u0005}d\u0001B\u0012%\u0005%B\u0001\"\u000e\u0001\u0003\u0006\u0004%\tA\u000e\u0005\to\u0001\u0011\t\u0011)A\u0005e!)\u0001\b\u0001C\u0001s!)A\b\u0001C\t{!)a\n\u0001C\t\u001f\")q\u000b\u0001C!1\")A\f\u0001C!;\")\u0011\r\u0001C!E\")a\r\u0001C!O\")1\u000e\u0001C!m!)A\u000e\u0001C![\")\u0011\u000f\u0001C!e\")a\u000f\u0001C!m!)q\u000f\u0001C!q\")1\u0010\u0001C!y\"9a\u0010AA\u0001\n\u0003z\b\"CA\u0001\u0001\u0005\u0005I\u0011IA\u0002\u000f%\ty\u0001JA\u0001\u0012\u0003\t\tB\u0002\u0005$I\u0005\u0005\t\u0012AA\n\u0011\u0019A4\u0003\"\u0001\u0002\u001c!9\u0011QD\n\u0005\u0006\u0005}\u0001bBA\u0013'\u0011\u0015\u0011q\u0005\u0005\b\u0003W\u0019BQAA\u0017\u0011\u001d\t\td\u0005C\u0003\u0003gAq!a\u000e\u0014\t\u000b\tI\u0004C\u0004\u0002>M!)!a\u0010\t\u000f\u0005\r3\u0003\"\u0002\u0002F!9\u0011\u0011J\n\u0005\u0006\u0005-\u0003bBA('\u0011\u0015\u0011\u0011\u000b\u0005\b\u0003+\u001aBQAA,\u0011\u001d\tYf\u0005C\u0003\u0003;Bq!!\u001a\u0014\t\u000b\t9\u0007C\u0005\u0002pM\t\t\u0011\"\u0002\u0002r!I\u0011QO\n\u0002\u0002\u0013\u0015\u0011q\u000f\u0002\t%&\u001c\u0007NQ=uK*\u0011QEJ\u0001\beVtG/[7f\u0015\u00059\u0013!B:dC2\f7\u0001A\n\u0004\u0001)r\u0003CA\u0016-\u001b\u00051\u0013BA\u0017'\u0005\u0019\te.\u001f,bYB\u0019q\u0006\r\u001a\u000e\u0003\u0011J!!\r\u0013\u0003+M\u001b\u0017\r\\1XQ>dWMT;nE\u0016\u0014\bK]8ysB\u00111fM\u0005\u0003i\u0019\u0012AAQ=uK\u0006!1/\u001a7g+\u0005\u0011\u0014!B:fY\u001a\u0004\u0013A\u0002\u001fj]&$h\b\u0006\u0002;wA\u0011q\u0006\u0001\u0005\u0006k\r\u0001\rAM\u0001\u0004]VlW#\u0001 \u0011\u0005}ZeB\u0001!I\u001d\t\teI\u0004\u0002C\u000b6\t1I\u0003\u0002EQ\u00051AH]8pizJ\u0011aJ\u0005\u0003\u000f\u001a\nA!\\1uQ&\u0011\u0011JS\u0001\b\u001dVlWM]5d\u0015\t9eE\u0003\u0002M\u001b\u0006q!)\u001f;f\u0013NLe\u000e^3he\u0006d'BA%K\u0003\ry'\u000fZ\u000b\u0002!B\u0011\u0011\u000b\u0016\b\u0003\u0001JK!a\u0015&\u0002\u0011=\u0013H-\u001a:j]\u001eT!!\u0016,\u0002\t\tKH/\u001a\u0006\u0003'*\u000b1\u0002Z8vE2,g+\u00197vKV\t\u0011\f\u0005\u0002,5&\u00111L\n\u0002\u0007\t>,(\r\\3\u0002\u0015\u0019dw.\u0019;WC2,X-F\u0001_!\tYs,\u0003\u0002aM\t)a\t\\8bi\u0006IAn\u001c8h-\u0006dW/Z\u000b\u0002GB\u00111\u0006Z\u0005\u0003K\u001a\u0012A\u0001T8oO\u0006A\u0011N\u001c;WC2,X-F\u0001i!\tY\u0013.\u0003\u0002kM\t\u0019\u0011J\u001c;\u0002\u0013\tLH/\u001a,bYV,\u0017AC:i_J$h+\u00197vKV\ta\u000e\u0005\u0002,_&\u0011\u0001O\n\u0002\u0006'\"|'\u000f^\u0001\fSN4\u0016\r\\5e\u0005f$X-F\u0001t!\tYC/\u0003\u0002vM\t9!i\\8mK\u0006t\u0017aA1cg\u0006\u0019Q.\u0019=\u0015\u0005IJ\b\"\u0002>\u000f\u0001\u0004\u0011\u0014\u0001\u0002;iCR\f1!\\5o)\t\u0011T\u0010C\u0003{\u001f\u0001\u0007!'\u0001\u0005iCND7i\u001c3f)\u0005A\u0017AB3rk\u0006d7\u000fF\u0002t\u0003\u000bA\u0011\"a\u0002\u0012\u0003\u0003\u0005\r!!\u0003\u0002\u0007a$\u0013\u0007E\u0002,\u0003\u0017I1!!\u0004'\u0005\r\te._\u0001\t%&\u001c\u0007NQ=uKB\u0011qfE\n\u0004'\u0005U\u0001cA\u0016\u0002\u0018%\u0019\u0011\u0011\u0004\u0014\u0003\r\u0005s\u0017PU3g)\t\t\t\"A\u0007ok6$S\r\u001f;f]NLwN\u001c\u000b\u0004}\u0005\u0005\u0002BBA\u0012+\u0001\u0007!(A\u0003%i\"L7/A\u0007pe\u0012$S\r\u001f;f]NLwN\u001c\u000b\u0004!\u0006%\u0002BBA\u0012-\u0001\u0007!(A\u000be_V\u0014G.\u001a,bYV,G%\u001a=uK:\u001c\u0018n\u001c8\u0015\u0007e\u000by\u0003\u0003\u0004\u0002$]\u0001\rAO\u0001\u0015M2|\u0017\r\u001e,bYV,G%\u001a=uK:\u001c\u0018n\u001c8\u0015\u0007y\u000b)\u0004\u0003\u0004\u0002$a\u0001\rAO\u0001\u0014Y>twMV1mk\u0016$S\r\u001f;f]NLwN\u001c\u000b\u0004G\u0006m\u0002BBA\u00123\u0001\u0007!(\u0001\nj]R4\u0016\r\\;fI\u0015DH/\u001a8tS>tGc\u00015\u0002B!1\u00111\u0005\u000eA\u0002i\n1CY=uKZ\u000bG.^3%Kb$XM\\:j_:$2AMA$\u0011\u0019\t\u0019c\u0007a\u0001u\u0005!2\u000f[8siZ\u000bG.^3%Kb$XM\\:j_:$2A\\A'\u0011\u0019\t\u0019\u0003\ba\u0001u\u0005)\u0012n\u001d,bY&$')\u001f;fI\u0015DH/\u001a8tS>tGcA:\u0002T!1\u00111E\u000fA\u0002i\nQ\"\u00192tI\u0015DH/\u001a8tS>tGc\u0001\u001a\u0002Z!1\u00111\u0005\u0010A\u0002i\nQ\"\\1yI\u0015DH/\u001a8tS>tG\u0003BA0\u0003G\"2AMA1\u0011\u0015Qx\u00041\u00013\u0011\u0019\t\u0019c\ba\u0001u\u0005iQ.\u001b8%Kb$XM\\:j_:$B!!\u001b\u0002nQ\u0019!'a\u001b\t\u000bi\u0004\u0003\u0019\u0001\u001a\t\r\u0005\r\u0002\u00051\u0001;\u0003IA\u0017m\u001d5D_\u0012,G%\u001a=uK:\u001c\u0018n\u001c8\u0015\u0007}\f\u0019\b\u0003\u0004\u0002$\u0005\u0002\rAO\u0001\u0011KF,\u0018\r\\:%Kb$XM\\:j_:$B!!\u001f\u0002~Q\u00191/a\u001f\t\u0013\u0005\u001d!%!AA\u0002\u0005%\u0001BBA\u0012E\u0001\u0007!\b")
public final class RichByte
implements ScalaWholeNumberProxy<Object> {
    private final byte self;

    public static boolean equals$extension(byte $this, Object x$1) {
        return RichByte$.MODULE$.equals$extension($this, x$1);
    }

    public static int hashCode$extension(byte $this) {
        return Byte.hashCode($this);
    }

    public static byte min$extension(byte $this, byte that) {
        return (byte)Math.min($this, that);
    }

    public static byte max$extension(byte $this, byte that) {
        return (byte)Math.max($this, that);
    }

    public static byte abs$extension(byte $this) {
        return (byte)Math.abs($this);
    }

    public static boolean isValidByte$extension(byte $this) {
        return true;
    }

    public static short shortValue$extension(byte $this) {
        return $this;
    }

    public static byte byteValue$extension(byte $this) {
        return $this;
    }

    public static int intValue$extension(byte $this) {
        return $this;
    }

    public static long longValue$extension(byte $this) {
        return $this;
    }

    public static float floatValue$extension(byte $this) {
        return $this;
    }

    public static double doubleValue$extension(byte $this) {
        return $this;
    }

    public static Ordering$Byte$ ord$extension(byte $this) {
        return Ordering$Byte$.MODULE$;
    }

    public static Numeric$ByteIsIntegral$ num$extension(byte $this) {
        return Numeric$ByteIsIntegral$.MODULE$;
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
    public boolean isValidShort() {
        return ScalaNumericAnyConversions.isValidShort$(this);
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
    public byte self() {
        return this.self;
    }

    public Numeric$ByteIsIntegral$ num() {
        this.self();
        return Numeric$ByteIsIntegral$.MODULE$;
    }

    public Ordering$Byte$ ord() {
        this.self();
        return Ordering$Byte$.MODULE$;
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
        return this.self();
    }

    @Override
    public short shortValue() {
        return this.self();
    }

    @Override
    public boolean isValidByte() {
        this.self();
        return true;
    }

    @Override
    public byte abs() {
        byte abs$extension_$this = this.self();
        return (byte)Math.abs(abs$extension_$this);
    }

    @Override
    public byte max(byte that) {
        byte max$extension_$this = this.self();
        return (byte)Math.max(max$extension_$this, that);
    }

    @Override
    public byte min(byte that) {
        byte min$extension_$this = this.self();
        return (byte)Math.min(min$extension_$this, that);
    }

    @Override
    public int hashCode() {
        return Byte.hashCode(this.self());
    }

    @Override
    public boolean equals(Object x$1) {
        return RichByte$.MODULE$.equals$extension(this.self(), x$1);
    }

    public RichByte(byte self) {
        this.self = self;
    }
}

