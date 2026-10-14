/*
 * Decompiled with CFR 0.152.
 */
package scala.runtime;

import scala.Proxy;
import scala.collection.immutable.Range;
import scala.math.Numeric$IntIsIntegral$;
import scala.math.Ordered;
import scala.math.Ordering$Int$;
import scala.math.ScalaNumericAnyConversions;
import scala.reflect.ScalaSignature;
import scala.runtime.OrderedProxy;
import scala.runtime.RangedProxy;
import scala.runtime.RichInt$;
import scala.runtime.ScalaNumberProxy;

@ScalaSignature(bytes="\u0006\u0005\t-d\u0001\u0002\u001d:\u0005yB\u0001\"\u0014\u0001\u0003\u0006\u0004%\tA\u0014\u0005\t\u001f\u0002\u0011\t\u0011)A\u0005\u000f\")\u0001\u000b\u0001C\u0001#\")A\u000b\u0001C\t+\")a\r\u0001C\tO\")q\u000e\u0001C!a\")A\u000f\u0001C!k\")\u0011\u0010\u0001C!u\")a\u0010\u0001C!\u001d\"1q\u0010\u0001C!\u0003\u0003Aq!!\u0003\u0001\t\u0003\nY\u0001C\u0004\u0002\u0014\u0001!\t!!\u0006\t\u000f\u0005u\u0001\u0001\"\u0011\u0002\u0016!9\u0011q\u0004\u0001\u0005\u0002\u0005U\u0001BBA\u0011\u0001\u0011\u0005c\nC\u0004\u0002$\u0001!\t%!\n\t\u000f\u0005-\u0002\u0001\"\u0011\u0002.!1\u0011\u0011\u0007\u0001\u0005\u00029Cq!a\u0012\u0001\t\u0003\tI\u0005C\u0004\u0002\\\u0001!\t!!\u0013\t\u000f\u0005u\u0003\u0001\"\u0001\u0002J\u00151\u0011q\f\u0001\u0001\u0003CBq!!\u001d\u0001\t\u0003\t\u0019\bC\u0004\u0002r\u0001!\t!!\u001f\t\u000f\u0005\u0005\u0005\u0001\"\u0001\u0002\u0004\"9\u0011\u0011\u0011\u0001\u0005\u0002\u0005U\u0005\"CAN\u0001\u0005\u0005I\u0011IAO\u0011%\ty\nAA\u0001\n\u0003\n\tkB\u0005\u0002.f\n\t\u0011#\u0001\u00020\u001aA\u0001(OA\u0001\u0012\u0003\t\t\f\u0003\u0004Q=\u0011\u0005\u0011\u0011\u0018\u0005\b\u0003wsBQAA_\u0011\u001d\t\u0019M\bC\u0003\u0003\u000bDq!!3\u001f\t\u000b\tY\rC\u0004\u0002Pz!)!!5\t\u000f\u0005Ug\u0004\"\u0002\u0002X\"9\u00111\u001c\u0010\u0005\u0006\u0005u\u0007bBAq=\u0011\u0015\u00111\u001d\u0005\b\u0003OtBQAAu\u0011\u001d\tiO\bC\u0003\u0003_Dq!a=\u001f\t\u000b\t)\u0010C\u0004\u0002zz!)!a?\t\u000f\u0005}h\u0004\"\u0002\u0003\u0002!9!Q\u0001\u0010\u0005\u0006\t\u001d\u0001b\u0002B\b=\u0011\u0015!\u0011\u0003\u0005\b\u00053qBQ\u0001B\u000e\u0011\u001d\u0011\tC\bC\u0003\u0005GAqAa\n\u001f\t\u000b\u0011I\u0003C\u0004\u0003.y!)Aa\f\t\u000f\tMb\u0004\"\u0002\u00036!9!1\u0007\u0010\u0005\u0006\tu\u0002b\u0002B$=\u0011\u0015!\u0011\n\u0005\b\u0005\u000frBQ\u0001B)\u0011%\u0011YFHA\u0001\n\u000b\u0011i\u0006C\u0005\u0003by\t\t\u0011\"\u0002\u0003d\t9!+[2i\u0013:$(B\u0001\u001e<\u0003\u001d\u0011XO\u001c;j[\u0016T\u0011\u0001P\u0001\u0006g\u000e\fG.Y\u0002\u0001'\u0011\u0001qh\u0011&\u0011\u0005\u0001\u000bU\"A\u001e\n\u0005\t[$AB!osZ\u000bG\u000eE\u0002E\u000b\u001ek\u0011!O\u0005\u0003\rf\u0012\u0001cU2bY\u0006tU/\u001c2feB\u0013x\u000e_=\u0011\u0005\u0001C\u0015BA%<\u0005\rIe\u000e\u001e\t\u0004\t.;\u0015B\u0001':\u0005-\u0011\u0016M\\4fIB\u0013x\u000e_=\u0002\tM,GNZ\u000b\u0002\u000f\u0006)1/\u001a7gA\u00051A(\u001b8jiz\"\"AU*\u0011\u0005\u0011\u0003\u0001\"B'\u0004\u0001\u00049\u0015a\u00018v[V\ta\u000b\u0005\u0002XG:\u0011\u0001\f\u0019\b\u00033zs!AW/\u000e\u0003mS!\u0001X\u001f\u0002\rq\u0012xn\u001c;?\u0013\u0005a\u0014BA0<\u0003\u0011i\u0017\r\u001e5\n\u0005\u0005\u0014\u0017a\u0002(v[\u0016\u0014\u0018n\u0019\u0006\u0003?nR!\u0001Z3\u0002\u001b%sG/S:J]R,wM]1m\u0015\t\t'-A\u0002pe\u0012,\u0012\u0001\u001b\t\u0003S2t!\u0001\u00176\n\u0005-\u0014\u0017\u0001C(sI\u0016\u0014\u0018N\\4\u000b\u00055t\u0017aA%oi*\u00111NY\u0001\fI>,(\r\\3WC2,X-F\u0001r!\t\u0001%/\u0003\u0002tw\t1Ai\\;cY\u0016\f!B\u001a7pCR4\u0016\r\\;f+\u00051\bC\u0001!x\u0013\tA8HA\u0003GY>\fG/A\u0005m_:<g+\u00197vKV\t1\u0010\u0005\u0002Ay&\u0011Qp\u000f\u0002\u0005\u0019>tw-\u0001\u0005j]R4\u0016\r\\;f\u0003%\u0011\u0017\u0010^3WC2,X-\u0006\u0002\u0002\u0004A\u0019\u0001)!\u0002\n\u0007\u0005\u001d1H\u0001\u0003CsR,\u0017AC:i_J$h+\u00197vKV\u0011\u0011Q\u0002\t\u0004\u0001\u0006=\u0011bAA\tw\t)1\u000b[8si\u00069\u0011n],i_2,WCAA\f!\r\u0001\u0015\u0011D\u0005\u0004\u00037Y$a\u0002\"p_2,\u0017M\\\u0001\u000bSN4\u0016\r\\5e\u0013:$\u0018aC5t-\u0006d\u0017\u000e\u001a'p]\u001e\f1!\u00192t\u0003\ri\u0017\r\u001f\u000b\u0004\u000f\u0006\u001d\u0002BBA\u0015!\u0001\u0007q)\u0001\u0003uQ\u0006$\u0018aA7j]R\u0019q)a\f\t\r\u0005%\u0012\u00031\u0001H\u0003\u0015\u0011x.\u001e8eQ-\u0011\u0012QGA\u001e\u0003{\t\t%a\u0011\u0011\u0007\u0001\u000b9$C\u0002\u0002:m\u0012!\u0002Z3qe\u0016\u001c\u0017\r^3e\u0003\u001diWm]:bO\u0016\f#!a\u0010\u0002gRD\u0017n\u001d\u0011jg\u0002\ng\u000eI5oi\u0016<WM\u001d\u0011usB,7\b\t;iKJ,\u0007%[:!]>\u0004#/Z1t_:\u0004Co\u001c\u0011s_VtG\rI5u]\u0001\u0002\u0003+\u001a:iCB\u001c\b%_8vA5,\u0017M\u001c;!i>\u00043-\u00197mAQD\u0017n\u001d\u0011p]\u0002\n\u0007E\u001a7pCRLgnZ\u0017q_&tG\u000f\t<bYV,w(A\u0003tS:\u001cW-\t\u0002\u0002F\u00051!GL\u00192]A\na\u0002^8CS:\f'/_*ue&tw-\u0006\u0002\u0002LA!\u0011QJA+\u001d\u0011\ty%!\u0015\u0011\u0005i[\u0014bAA*w\u00051\u0001K]3eK\u001aLA!a\u0016\u0002Z\t11\u000b\u001e:j]\u001eT1!a\u0015<\u0003-!x\u000eS3y'R\u0014\u0018N\\4\u0002\u001bQ|wj\u0019;bYN#(/\u001b8h\u0005E\u0011Vm];mi^KG\u000f[8viN#X\r\u001d\t\u0005\u0003G\ni'\u0004\u0002\u0002f)!\u0011qMA5\u0003%IW.\\;uC\ndWMC\u0002\u0002lm\n!bY8mY\u0016\u001cG/[8o\u0013\u0011\ty'!\u001a\u0003\u000bI\u000bgnZ3\u0002\u000bUtG/\u001b7\u0015\t\u0005\u0005\u0014Q\u000f\u0005\u0007\u0003o:\u0002\u0019A$\u0002\u0007\u0015tG\r\u0006\u0004\u0002b\u0005m\u0014Q\u0010\u0005\u0007\u0003oB\u0002\u0019A$\t\r\u0005}\u0004\u00041\u0001H\u0003\u0011\u0019H/\u001a9\u0002\u0005Q|G\u0003BAC\u0003'\u0003B!a\"\u0002\u000e:!\u00111MAE\u0013\u0011\tY)!\u001a\u0002\u000bI\u000bgnZ3\n\t\u0005=\u0015\u0011\u0013\u0002\n\u0013:\u001cG.^:jm\u0016TA!a#\u0002f!1\u0011qO\rA\u0002\u001d#b!!\"\u0002\u0018\u0006e\u0005BBA<5\u0001\u0007q\t\u0003\u0004\u0002\u0000i\u0001\raR\u0001\tQ\u0006\u001c\bnQ8eKR\tq)\u0001\u0004fcV\fGn\u001d\u000b\u0005\u0003/\t\u0019\u000bC\u0005\u0002&r\t\t\u00111\u0001\u0002(\u0006\u0019\u0001\u0010J\u0019\u0011\u0007\u0001\u000bI+C\u0002\u0002,n\u00121!\u00118z\u0003\u001d\u0011\u0016n\u00195J]R\u0004\"\u0001\u0012\u0010\u0014\u0007y\t\u0019\fE\u0002A\u0003kK1!a.<\u0005\u0019\te.\u001f*fMR\u0011\u0011qV\u0001\u000e]VlG%\u001a=uK:\u001c\u0018n\u001c8\u0015\u0007Y\u000by\f\u0003\u0004\u0002B\u0002\u0002\rAU\u0001\u0006IQD\u0017n]\u0001\u000e_J$G%\u001a=uK:\u001c\u0018n\u001c8\u0015\u0007!\f9\r\u0003\u0004\u0002B\u0006\u0002\rAU\u0001\u0016I>,(\r\\3WC2,X\rJ3yi\u0016t7/[8o)\r\t\u0018Q\u001a\u0005\u0007\u0003\u0003\u0014\u0003\u0019\u0001*\u0002)\u0019dw.\u0019;WC2,X\rJ3yi\u0016t7/[8o)\r1\u00181\u001b\u0005\u0007\u0003\u0003\u001c\u0003\u0019\u0001*\u0002'1|gn\u001a,bYV,G%\u001a=uK:\u001c\u0018n\u001c8\u0015\u0007m\fI\u000e\u0003\u0004\u0002B\u0012\u0002\rAU\u0001\u0013S:$h+\u00197vK\u0012*\u0007\u0010^3og&|g\u000eF\u0002H\u0003?Da!!1&\u0001\u0004\u0011\u0016a\u00052zi\u00164\u0016\r\\;fI\u0015DH/\u001a8tS>tG\u0003BA\u0002\u0003KDa!!1'\u0001\u0004\u0011\u0016\u0001F:i_J$h+\u00197vK\u0012*\u0007\u0010^3og&|g\u000e\u0006\u0003\u0002\u000e\u0005-\bBBAaO\u0001\u0007!+A\tjg^Cw\u000e\\3%Kb$XM\\:j_:$B!a\u0006\u0002r\"1\u0011\u0011\u0019\u0015A\u0002I\u000bA#[:WC2LG-\u00138uI\u0015DH/\u001a8tS>tG\u0003BA\f\u0003oDa!!1*\u0001\u0004\u0011\u0016!F5t-\u0006d\u0017\u000e\u001a'p]\u001e$S\r\u001f;f]NLwN\u001c\u000b\u0005\u0003/\ti\u0010\u0003\u0004\u0002B*\u0002\rAU\u0001\u000eC\n\u001cH%\u001a=uK:\u001c\u0018n\u001c8\u0015\u0007\u001d\u0013\u0019\u0001\u0003\u0004\u0002B.\u0002\rAU\u0001\u000e[\u0006DH%\u001a=uK:\u001c\u0018n\u001c8\u0015\t\t%!Q\u0002\u000b\u0004\u000f\n-\u0001BBA\u0015Y\u0001\u0007q\t\u0003\u0004\u0002B2\u0002\rAU\u0001\u000e[&tG%\u001a=uK:\u001c\u0018n\u001c8\u0015\t\tM!q\u0003\u000b\u0004\u000f\nU\u0001BBA\u0015[\u0001\u0007q\t\u0003\u0004\u0002B6\u0002\rAU\u0001\u0010e>,h\u000e\u001a\u0013fqR,gn]5p]R\u0019qI!\b\t\r\u0005\u0005g\u00061\u0001SQ-q\u0013QGA\u001e\u0003{\t\t%a\u0011\u00021Q|')\u001b8bef\u001cFO]5oO\u0012*\u0007\u0010^3og&|g\u000e\u0006\u0003\u0002L\t\u0015\u0002BBAa_\u0001\u0007!+A\u000bu_\"+\u0007p\u0015;sS:<G%\u001a=uK:\u001c\u0018n\u001c8\u0015\t\u0005-#1\u0006\u0005\u0007\u0003\u0003\u0004\u0004\u0019\u0001*\u0002/Q|wj\u0019;bYN#(/\u001b8hI\u0015DH/\u001a8tS>tG\u0003BA&\u0005cAa!!12\u0001\u0004\u0011\u0016aD;oi&dG%\u001a=uK:\u001c\u0018n\u001c8\u0015\t\t]\"1\b\u000b\u0005\u0003C\u0012I\u0004\u0003\u0004\u0002xI\u0002\ra\u0012\u0005\u0007\u0003\u0003\u0014\u0004\u0019\u0001*\u0015\t\t}\"Q\t\u000b\u0007\u0003C\u0012\tEa\u0011\t\r\u0005]4\u00071\u0001H\u0011\u0019\tyh\ra\u0001\u000f\"1\u0011\u0011Y\u001aA\u0002I\u000bA\u0002^8%Kb$XM\\:j_:$BAa\u0013\u0003PQ!\u0011Q\u0011B'\u0011\u0019\t9\b\u000ea\u0001\u000f\"1\u0011\u0011\u0019\u001bA\u0002I#BAa\u0015\u0003ZQ1\u0011Q\u0011B+\u0005/Ba!a\u001e6\u0001\u00049\u0005BBA@k\u0001\u0007q\t\u0003\u0004\u0002BV\u0002\rAU\u0001\u0013Q\u0006\u001c\bnQ8eK\u0012*\u0007\u0010^3og&|g\u000e\u0006\u0003\u0002\u001e\n}\u0003BBAam\u0001\u0007!+\u0001\tfcV\fGn\u001d\u0013fqR,gn]5p]R!!Q\rB5)\u0011\t9Ba\u001a\t\u0013\u0005\u0015v'!AA\u0002\u0005\u001d\u0006BBAao\u0001\u0007!\u000b")
public final class RichInt
implements ScalaNumberProxy<Object>,
RangedProxy<Object> {
    private final int self;

    public static boolean equals$extension(int $this, Object x$1) {
        return RichInt$.MODULE$.equals$extension($this, x$1);
    }

    public static int hashCode$extension(int $this) {
        return Integer.hashCode($this);
    }

    public static Range.Inclusive to$extension(int $this, int end, int step) {
        return new Range.Inclusive($this, end, step);
    }

    public static Range.Inclusive to$extension(int $this, int end) {
        return new Range.Inclusive($this, end, 1);
    }

    public static Range until$extension(int $this, int end, int step) {
        return new Range.Exclusive($this, end, step);
    }

    public static Range until$extension(int $this, int end) {
        return new Range.Exclusive($this, end, 1);
    }

    public static String toOctalString$extension(int $this) {
        return Integer.toOctalString($this);
    }

    public static String toHexString$extension(int $this) {
        return Integer.toHexString($this);
    }

    public static String toBinaryString$extension(int $this) {
        return Integer.toBinaryString($this);
    }

    public static int round$extension(int $this) {
        return $this;
    }

    public static int min$extension(int $this, int that) {
        return Math.min($this, that);
    }

    public static int max$extension(int $this, int that) {
        return Math.max($this, that);
    }

    public static int abs$extension(int $this) {
        return Math.abs($this);
    }

    public static boolean isValidLong$extension(int $this) {
        return true;
    }

    public static boolean isValidInt$extension(int $this) {
        return true;
    }

    public static boolean isWhole$extension(int $this) {
        return true;
    }

    public static short shortValue$extension(int $this) {
        return (short)$this;
    }

    public static byte byteValue$extension(int $this) {
        return (byte)$this;
    }

    public static int intValue$extension(int $this) {
        return $this;
    }

    public static long longValue$extension(int $this) {
        return $this;
    }

    public static float floatValue$extension(int $this) {
        return $this;
    }

    public static double doubleValue$extension(int $this) {
        return $this;
    }

    public static Ordering$Int$ ord$extension(int $this) {
        return Ordering$Int$.MODULE$;
    }

    public static Numeric$IntIsIntegral$ num$extension(int $this) {
        return Numeric$IntIsIntegral$.MODULE$;
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
    public boolean isValidShort() {
        return ScalaNumericAnyConversions.isValidShort$(this);
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
    public int self() {
        return this.self;
    }

    public Numeric$IntIsIntegral$ num() {
        this.self();
        return Numeric$IntIsIntegral$.MODULE$;
    }

    public Ordering$Int$ ord() {
        this.self();
        return Ordering$Int$.MODULE$;
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
        return (short)this.self();
    }

    @Override
    public boolean isWhole() {
        this.self();
        return true;
    }

    @Override
    public boolean isValidInt() {
        this.self();
        return true;
    }

    public boolean isValidLong() {
        this.self();
        return true;
    }

    @Override
    public int abs() {
        int abs$extension_$this = this.self();
        return Math.abs(abs$extension_$this);
    }

    @Override
    public int max(int that) {
        int max$extension_$this = this.self();
        return Math.max(max$extension_$this, that);
    }

    @Override
    public int min(int that) {
        int min$extension_$this = this.self();
        return Math.min(min$extension_$this, that);
    }

    public int round() {
        return this.self();
    }

    public String toBinaryString() {
        return Integer.toBinaryString(this.self());
    }

    public String toHexString() {
        return Integer.toHexString(this.self());
    }

    public String toOctalString() {
        return Integer.toOctalString(this.self());
    }

    public Range until(int end) {
        int until$extension_$this = this.self();
        return new Range.Exclusive(until$extension_$this, end, 1);
    }

    public Range until(int end, int step) {
        int until$extension_$this = this.self();
        return new Range.Exclusive(until$extension_$this, end, step);
    }

    public Range.Inclusive to(int end) {
        int to$extension_$this = this.self();
        return new Range.Inclusive(to$extension_$this, end, 1);
    }

    public Range.Inclusive to(int end, int step) {
        int to$extension_$this = this.self();
        return new Range.Inclusive(to$extension_$this, end, step);
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(this.self());
    }

    @Override
    public boolean equals(Object x$1) {
        return RichInt$.MODULE$.equals$extension(this.self(), x$1);
    }

    public RichInt(int self) {
        this.self = self;
    }
}

