/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime;

import scala.Proxy;
import scala.collection.immutable.NumericRange;
import scala.math.Numeric$LongIsIntegral$;
import scala.math.Ordered;
import scala.math.Ordering$Long$;
import scala.math.ScalaNumericAnyConversions;
import scala.reflect.ScalaSignature;
import scala.runtime.IntegralProxy;
import scala.runtime.OrderedProxy;
import scala.runtime.RichLong$;
import scala.runtime.ScalaNumberProxy;
import scala.runtime.ScalaWholeNumberProxy;

@ScalaSignature(bytes="\u0006\u0005\t\u001da\u0001B\u001a5\u0005eB\u0001\"\u0012\u0001\u0003\u0006\u0004%\tA\u0012\u0005\t\u000f\u0002\u0011\t\u0011)A\u0005\u0005\")\u0001\n\u0001C\u0001\u0013\")A\n\u0001C\t\u001b\")a\f\u0001C\t?\")q\r\u0001C!Q\")A\u000e\u0001C![\")\u0011\u000f\u0001C!\r\")!\u000f\u0001C!g\")q\u000f\u0001C!q\")A\u0010\u0001C!{\"9\u00111\u0001\u0001\u0005B\u0005\u0015\u0001bBA\u0007\u0001\u0011\u0005\u0013Q\u0001\u0005\b\u0003\u001f\u0001A\u0011IA\u0003\u0011\u001d\t\t\u0002\u0001C!\u0003\u000bAq!a\u0005\u0001\t\u0003\t)\u0001\u0003\u0004\u0002\u0016\u0001!\tE\u0012\u0005\b\u0003/\u0001A\u0011IA\r\u0011\u001d\ty\u0002\u0001C!\u0003CAa!!\n\u0001\t\u00031\u0005bBA\u001e\u0001\u0011\u0005\u0011Q\b\u0005\b\u0003\u001f\u0002A\u0011AA\u001f\u0011\u001d\t\t\u0006\u0001C\u0001\u0003{A\u0011\"a\u0015\u0001\u0003\u0003%\t%!\u0016\t\u0013\u0005]\u0003!!A\u0005B\u0005es!CA3i\u0005\u0005\t\u0012AA4\r!\u0019D'!A\t\u0002\u0005%\u0004B\u0002%\u001c\t\u0003\t\t\bC\u0004\u0002tm!)!!\u001e\t\u000f\u0005m4\u0004\"\u0002\u0002~!9\u0011\u0011Q\u000e\u0005\u0006\u0005\r\u0005bBAD7\u0011\u0015\u0011\u0011\u0012\u0005\b\u0003\u001b[BQAAH\u0011\u001d\t\u0019j\u0007C\u0003\u0003+Cq!!'\u001c\t\u000b\tY\nC\u0004\u0002 n!)!!)\t\u000f\u0005\u00156\u0004\"\u0002\u0002(\"9\u00111V\u000e\u0005\u0006\u00055\u0006bBAY7\u0011\u0015\u00111\u0017\u0005\b\u0003o[BQAA]\u0011\u001d\til\u0007C\u0003\u0003\u007fCq!a1\u001c\t\u000b\t)\rC\u0004\u0002Jn!)!a3\t\u000f\u0005M7\u0004\"\u0002\u0002V\"9\u0011Q\\\u000e\u0005\u0006\u0005}\u0007bBAs7\u0011\u0015\u0011q\u001d\u0005\b\u0003W\\BQAAw\u0011\u001d\t\tp\u0007C\u0003\u0003gD\u0011\"a>\u001c\u0003\u0003%)!!?\t\u0013\u0005u8$!A\u0005\u0006\u0005}(\u0001\u0003*jG\"duN\\4\u000b\u0005U2\u0014a\u0002:v]RLW.\u001a\u0006\u0002o\u0005)1oY1mC\u000e\u00011c\u0001\u0001;}A\u00111\bP\u0007\u0002m%\u0011QH\u000e\u0002\u0007\u0003:Lh+\u00197\u0011\u0007}\u0002%)D\u00015\u0013\t\tEGA\u0007J]R,wM]1m!J|\u00070\u001f\t\u0003w\rK!\u0001\u0012\u001c\u0003\t1{gnZ\u0001\u0005g\u0016dg-F\u0001C\u0003\u0015\u0019X\r\u001c4!\u0003\u0019a\u0014N\\5u}Q\u0011!j\u0013\t\u0003\u007f\u0001AQ!R\u0002A\u0002\t\u000b1A\\;n+\u0005q\u0005CA(\\\u001d\t\u0001\u0006L\u0004\u0002R-:\u0011!+V\u0007\u0002'*\u0011A\u000bO\u0001\u0007yI|w\u000e\u001e \n\u0003]J!a\u0016\u001c\u0002\t5\fG\u000f[\u0005\u00033j\u000bqAT;nKJL7M\u0003\u0002Xm)\u0011A,X\u0001\u000f\u0019>tw-S:J]R,wM]1m\u0015\tI&,A\u0002pe\u0012,\u0012\u0001\u0019\t\u0003C\u0012t!\u0001\u00152\n\u0005\rT\u0016\u0001C(sI\u0016\u0014\u0018N\\4\u000b\u0005\u00154\u0017\u0001\u0002'p]\u001eT!a\u0019.\u0002\u0017\u0011|WO\u00197f-\u0006dW/Z\u000b\u0002SB\u00111H[\u0005\u0003WZ\u0012a\u0001R8vE2,\u0017A\u00034m_\u0006$h+\u00197vKV\ta\u000e\u0005\u0002<_&\u0011\u0001O\u000e\u0002\u0006\r2|\u0017\r^\u0001\nY>twMV1mk\u0016\f\u0001\"\u001b8u-\u0006dW/Z\u000b\u0002iB\u00111(^\u0005\u0003mZ\u00121!\u00138u\u0003%\u0011\u0017\u0010^3WC2,X-F\u0001z!\tY$0\u0003\u0002|m\t!!)\u001f;f\u0003)\u0019\bn\u001c:u-\u0006dW/Z\u000b\u0002}B\u00111h`\u0005\u0004\u0003\u00031$!B*i_J$\u0018aC5t-\u0006d\u0017\u000e\u001a\"zi\u0016,\"!a\u0002\u0011\u0007m\nI!C\u0002\u0002\fY\u0012qAQ8pY\u0016\fg.\u0001\u0007jgZ\u000bG.\u001b3TQ>\u0014H/A\u0006jgZ\u000bG.\u001b3DQ\u0006\u0014\u0018AC5t-\u0006d\u0017\u000eZ%oi\u0006Y\u0011n\u001d,bY&$Gj\u001c8h\u0003\r\t'm]\u0001\u0004[\u0006DHc\u0001\"\u0002\u001c!1\u0011Q\u0004\nA\u0002\t\u000bA\u0001\u001e5bi\u0006\u0019Q.\u001b8\u0015\u0007\t\u000b\u0019\u0003\u0003\u0004\u0002\u001eM\u0001\rAQ\u0001\u0006e>,h\u000e\u001a\u0015\f)\u0005%\u0012qFA\u0019\u0003k\t9\u0004E\u0002<\u0003WI1!!\f7\u0005)!W\r\u001d:fG\u0006$X\rZ\u0001\b[\u0016\u001c8/Y4fC\t\t\u0019$A:uQ&\u001c\b%[:!C:\u0004\u0013N\u001c;fO\u0016\u0014\b\u0005^=qKn\u0002C\u000f[3sK\u0002J7\u000f\t8pAI,\u0017m]8oAQ|\u0007E]8v]\u0012\u0004\u0013\u000e\u001e\u0018!AA+'\u000f[1qg\u0002Jx.\u001e\u0011nK\u0006tG\u000f\t;pA\r\fG\u000e\u001c\u0011uQ&\u001c\be\u001c8!C\u00022Gn\\1uS:<W\u0006]8j]R\u0004c/\u00197vK~\nQa]5oG\u0016\f#!!\u000f\u0002\rIr\u0013'\r\u00181\u00039!xNQ5oCJL8\u000b\u001e:j]\u001e,\"!a\u0010\u0011\t\u0005\u0005\u0013\u0011\n\b\u0005\u0003\u0007\n)\u0005\u0005\u0002Sm%\u0019\u0011q\t\u001c\u0002\rA\u0013X\rZ3g\u0013\u0011\tY%!\u0014\u0003\rM#(/\u001b8h\u0015\r\t9EN\u0001\fi>DU\r_*ue&tw-A\u0007u_>\u001bG/\u00197TiJLgnZ\u0001\tQ\u0006\u001c\bnQ8eKR\tA/\u0001\u0004fcV\fGn\u001d\u000b\u0005\u0003\u000f\tY\u0006C\u0005\u0002^e\t\t\u00111\u0001\u0002`\u0005\u0019\u0001\u0010J\u0019\u0011\u0007m\n\t'C\u0002\u0002dY\u00121!\u00118z\u0003!\u0011\u0016n\u00195M_:<\u0007CA \u001c'\rY\u00121\u000e\t\u0004w\u00055\u0014bAA8m\t1\u0011I\\=SK\u001a$\"!a\u001a\u0002\u001b9,X\u000eJ3yi\u0016t7/[8o)\rq\u0015q\u000f\u0005\u0007\u0003sj\u0002\u0019\u0001&\u0002\u000b\u0011\"\b.[:\u0002\u001b=\u0014H\rJ3yi\u0016t7/[8o)\r\u0001\u0017q\u0010\u0005\u0007\u0003sr\u0002\u0019\u0001&\u0002+\u0011|WO\u00197f-\u0006dW/\u001a\u0013fqR,gn]5p]R\u0019\u0011.!\"\t\r\u0005et\u00041\u0001K\u0003Q1Gn\\1u-\u0006dW/\u001a\u0013fqR,gn]5p]R\u0019a.a#\t\r\u0005e\u0004\u00051\u0001K\u0003MawN\\4WC2,X\rJ3yi\u0016t7/[8o)\r\u0011\u0015\u0011\u0013\u0005\u0007\u0003s\n\u0003\u0019\u0001&\u0002%%tGOV1mk\u0016$S\r\u001f;f]NLwN\u001c\u000b\u0004i\u0006]\u0005BBA=E\u0001\u0007!*A\ncsR,g+\u00197vK\u0012*\u0007\u0010^3og&|g\u000eF\u0002z\u0003;Ca!!\u001f$\u0001\u0004Q\u0015\u0001F:i_J$h+\u00197vK\u0012*\u0007\u0010^3og&|g\u000eF\u0002\u007f\u0003GCa!!\u001f%\u0001\u0004Q\u0015!F5t-\u0006d\u0017\u000e\u001a\"zi\u0016$S\r\u001f;f]NLwN\u001c\u000b\u0005\u0003\u000f\tI\u000b\u0003\u0004\u0002z\u0015\u0002\rAS\u0001\u0017SN4\u0016\r\\5e'\"|'\u000f\u001e\u0013fqR,gn]5p]R!\u0011qAAX\u0011\u0019\tIH\na\u0001\u0015\u0006)\u0012n\u001d,bY&$7\t[1sI\u0015DH/\u001a8tS>tG\u0003BA\u0004\u0003kCa!!\u001f(\u0001\u0004Q\u0015\u0001F5t-\u0006d\u0017\u000eZ%oi\u0012*\u0007\u0010^3og&|g\u000e\u0006\u0003\u0002\b\u0005m\u0006BBA=Q\u0001\u0007!*A\u000bjgZ\u000bG.\u001b3M_:<G%\u001a=uK:\u001c\u0018n\u001c8\u0015\t\u0005\u001d\u0011\u0011\u0019\u0005\u0007\u0003sJ\u0003\u0019\u0001&\u0002\u001b\u0005\u00147\u000fJ3yi\u0016t7/[8o)\r\u0011\u0015q\u0019\u0005\u0007\u0003sR\u0003\u0019\u0001&\u0002\u001b5\f\u0007\u0010J3yi\u0016t7/[8o)\u0011\ti-!5\u0015\u0007\t\u000by\r\u0003\u0004\u0002\u001e-\u0002\rA\u0011\u0005\u0007\u0003sZ\u0003\u0019\u0001&\u0002\u001b5Lg\u000eJ3yi\u0016t7/[8o)\u0011\t9.a7\u0015\u0007\t\u000bI\u000e\u0003\u0004\u0002\u001e1\u0002\rA\u0011\u0005\u0007\u0003sb\u0003\u0019\u0001&\u0002\u001fI|WO\u001c3%Kb$XM\\:j_:$2AQAq\u0011\u0019\tI(\fa\u0001\u0015\"ZQ&!\u000b\u00020\u0005E\u0012QGA\u001c\u0003a!xNQ5oCJL8\u000b\u001e:j]\u001e$S\r\u001f;f]NLwN\u001c\u000b\u0005\u0003\u007f\tI\u000f\u0003\u0004\u0002z9\u0002\rAS\u0001\u0016i>DU\r_*ue&tw\rJ3yi\u0016t7/[8o)\u0011\ty$a<\t\r\u0005et\u00061\u0001K\u0003]!xnT2uC2\u001cFO]5oO\u0012*\u0007\u0010^3og&|g\u000e\u0006\u0003\u0002@\u0005U\bBBA=a\u0001\u0007!*\u0001\niCND7i\u001c3fI\u0015DH/\u001a8tS>tG\u0003BA+\u0003wDa!!\u001f2\u0001\u0004Q\u0015\u0001E3rk\u0006d7\u000fJ3yi\u0016t7/[8o)\u0011\u0011\tA!\u0002\u0015\t\u0005\u001d!1\u0001\u0005\n\u0003;\u0012\u0014\u0011!a\u0001\u0003?Ba!!\u001f3\u0001\u0004Q\u0005")
public final class RichLong
implements IntegralProxy<Object> {
    private final long self;

    public static boolean equals$extension(long $this, Object x$1) {
        return RichLong$.MODULE$.equals$extension($this, x$1);
    }

    public static int hashCode$extension(long $this) {
        return Long.hashCode($this);
    }

    public static String toOctalString$extension(long $this) {
        return Long.toOctalString($this);
    }

    public static String toHexString$extension(long $this) {
        return Long.toHexString($this);
    }

    public static String toBinaryString$extension(long $this) {
        return Long.toBinaryString($this);
    }

    public static long round$extension(long $this) {
        return $this;
    }

    public static long min$extension(long $this, long that) {
        return Math.min($this, that);
    }

    public static long max$extension(long $this, long that) {
        return Math.max($this, that);
    }

    public static long abs$extension(long $this) {
        return Math.abs($this);
    }

    public static boolean isValidLong$extension(long $this) {
        return true;
    }

    public static boolean isValidInt$extension(long $this) {
        return RichLong$.MODULE$.isValidInt$extension($this);
    }

    public static boolean isValidChar$extension(long $this) {
        return RichLong$.MODULE$.isValidChar$extension($this);
    }

    public static boolean isValidShort$extension(long $this) {
        return RichLong$.MODULE$.isValidShort$extension($this);
    }

    public static boolean isValidByte$extension(long $this) {
        return RichLong$.MODULE$.isValidByte$extension($this);
    }

    public static short shortValue$extension(long $this) {
        return (short)$this;
    }

    public static byte byteValue$extension(long $this) {
        return (byte)$this;
    }

    public static int intValue$extension(long $this) {
        return (int)$this;
    }

    public static long longValue$extension(long $this) {
        return $this;
    }

    public static float floatValue$extension(long $this) {
        return $this;
    }

    public static double doubleValue$extension(long $this) {
        return $this;
    }

    public static Ordering$Long$ ord$extension(long $this) {
        return Ordering$Long$.MODULE$;
    }

    public static Numeric$LongIsIntegral$ num$extension(long $this) {
        return Numeric$LongIsIntegral$.MODULE$;
    }

    @Override
    public NumericRange.Exclusive until(Object end) {
        return IntegralProxy.until$(this, end);
    }

    @Override
    public NumericRange.Exclusive until(Object end, Object step) {
        return IntegralProxy.until$(this, end, step);
    }

    @Override
    public NumericRange.Inclusive to(Object end) {
        return IntegralProxy.to$(this, end);
    }

    @Override
    public NumericRange.Inclusive to(Object end, Object step) {
        return IntegralProxy.to$(this, end, step);
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
    public int unifiedPrimitiveHashcode() {
        return ScalaNumericAnyConversions.unifiedPrimitiveHashcode$(this);
    }

    @Override
    public boolean unifiedPrimitiveEquals(Object x) {
        return ScalaNumericAnyConversions.unifiedPrimitiveEquals$(this, x);
    }

    @Override
    public long self() {
        return this.self;
    }

    public Numeric$LongIsIntegral$ num() {
        this.self();
        return Numeric$LongIsIntegral$.MODULE$;
    }

    public Ordering$Long$ ord() {
        this.self();
        return Ordering$Long$.MODULE$;
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
    public boolean isValidByte() {
        return RichLong$.MODULE$.isValidByte$extension(this.self());
    }

    @Override
    public boolean isValidShort() {
        return RichLong$.MODULE$.isValidShort$extension(this.self());
    }

    @Override
    public boolean isValidChar() {
        return RichLong$.MODULE$.isValidChar$extension(this.self());
    }

    @Override
    public boolean isValidInt() {
        return RichLong$.MODULE$.isValidInt$extension(this.self());
    }

    public boolean isValidLong() {
        this.self();
        return true;
    }

    @Override
    public long abs() {
        long abs$extension_$this = this.self();
        return Math.abs(abs$extension_$this);
    }

    @Override
    public long max(long that) {
        long max$extension_$this = this.self();
        return Math.max(max$extension_$this, that);
    }

    @Override
    public long min(long that) {
        long min$extension_$this = this.self();
        return Math.min(min$extension_$this, that);
    }

    public long round() {
        return this.self();
    }

    public String toBinaryString() {
        return Long.toBinaryString(this.self());
    }

    public String toHexString() {
        return Long.toHexString(this.self());
    }

    public String toOctalString() {
        return Long.toOctalString(this.self());
    }

    @Override
    public int hashCode() {
        return Long.hashCode(this.self());
    }

    @Override
    public boolean equals(Object x$1) {
        return RichLong$.MODULE$.equals$extension(this.self(), x$1);
    }

    public RichLong(long self) {
        this.self = self;
    }
}

