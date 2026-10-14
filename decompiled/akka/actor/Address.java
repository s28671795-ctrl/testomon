/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.Address$;
import akka.annotation.InternalApi;
import java.io.Serializable;
import java.util.Optional;
import scala.Function0;
import scala.Function1;
import scala.None$;
import scala.Option;
import scala.Option$;
import scala.Predef$;
import scala.Product;
import scala.Some;
import scala.Tuple4;
import scala.collection.Iterator;
import scala.compat.java8.OptionConverters$;
import scala.compat.java8.OptionConverters$RichOptionForJava8$;
import scala.math.Ordering;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;
import scala.util.hashing.MurmurHash3$;
import scala.util.matching.Regex;

@ScalaSignature(bytes="\u0006\u0005\t\u0015b\u0001B\u0017/\u0005NB\u0001\"\u0013\u0001\u0003\u0016\u0004%\tA\u0013\u0005\t'\u0002\u0011\t\u0012)A\u0005\u0017\"AA\u000b\u0001BK\u0002\u0013\u0005!\n\u0003\u0005V\u0001\tE\t\u0015!\u0003L\u0011!1\u0006A!f\u0001\n\u00039\u0006\u0002C.\u0001\u0005#\u0005\u000b\u0011\u0002-\t\u0011q\u0003!Q3A\u0005\u0002uC\u0001B\u0019\u0001\u0003\u0012\u0003\u0006IA\u0018\u0005\u0007G\u0002!\t\u0001\r3\t\u000b\r\u0004A\u0011A6\t\u000b\r\u0004A\u0011\u00018\t\u000bM\u0004A\u0011\u0001;\t\u000fe\u0004\u0011\u0013!C\u0001u\"A\u00111\u0002\u0001\u0012\u0002\u0013\u0005!\u0010C\u0005\u0002\u000e\u0001\t\n\u0011\"\u0001\u0002\u0010!I\u00111\u0003\u0001\u0012\u0002\u0013\u0005\u0011Q\u0003\u0005\b\u00033\u0001A\u0011AA\u000e\u0011\u001d\ti\u0003\u0001C\u0001\u0003_Aq!a\u0010\u0001\t\u0003\t\t\u0005C\u0004\u0002J\u0001!\t!!\u0011\t\u0015\u0005-\u0003\u0001#b\u0001\n\u0003\ni\u0005C\u0005\u0002X\u0001A)\u0019!C!\u0015\"1\u00111\f\u0001\u0005\u0002)C\u0001\"!\u0018\u0001\t\u0003\u0001\u0014\u0011\t\u0005\t\u0003W\u0002A\u0011\u0001\u0019\u0002n!I\u0011q\u000f\u0001\u0002\u0002\u0013\u0005\u0013\u0011\u0010\u0005\n\u0003\u007f\u0002\u0011\u0011!C\u0001\u0003\u001bB\u0011\"!!\u0001\u0003\u0003%\t!a!\t\u0013\u0005=\u0005!!A\u0005B\u0005E\u0005\"CAP\u0001\u0005\u0005I\u0011AAQ\u0011%\t)\u000bAA\u0001\n\u0003\n9\u000bC\u0005\u0002,\u0002\t\t\u0011\"\u0011\u0002.\u001e9\u0011Q\u0018\u0018\t\u0002\u0005}fAB\u0017/\u0011\u0003\t\t\r\u0003\u0004dE\u0011\u0005\u0011Q\u001a\u0005\n\u0003\u001f\u0014#\u0019!C\u0001\u0003#D\u0001\"!9#A\u0003%\u00111\u001b\u0005\b\u0003G\u0014C\u0011AAs\u0011\u001d\t\u0019O\tC\u0001\u0003WD\u0011\"!>#\u0005\u0004%\u0019!a>\t\u0011\u0005}(\u0005)A\u0005\u0003sD\u0011\"a9#\u0003\u0003%\tI!\u0001\t\u0013\t-!%!A\u0005\u0002\n5\u0001\"\u0003B\u000eE\u0005\u0005I\u0011\u0002B\u000f\u0005\u001d\tE\r\u001a:fgNT!a\f\u0019\u0002\u000b\u0005\u001cGo\u001c:\u000b\u0003E\nA!Y6lC\u000e\u00011\u0003\u0002\u00015uu\u0002\"!\u000e\u001d\u000e\u0003YR\u0011aN\u0001\u0006g\u000e\fG.Y\u0005\u0003sY\u0012a!\u00118z%\u00164\u0007CA\u001b<\u0013\tadGA\u0004Qe>$Wo\u0019;\u0011\u0005y2eBA E\u001d\t\u00015)D\u0001B\u0015\t\u0011%'\u0001\u0004=e>|GOP\u0005\u0002o%\u0011QIN\u0001\ba\u0006\u001c7.Y4f\u0013\t9\u0005J\u0001\u0007TKJL\u0017\r\\5{C\ndWM\u0003\u0002Fm\u0005A\u0001O]8u_\u000e|G.F\u0001L!\ta\u0005K\u0004\u0002N\u001dB\u0011\u0001IN\u0005\u0003\u001fZ\na\u0001\u0015:fI\u00164\u0017BA)S\u0005\u0019\u0019FO]5oO*\u0011qJN\u0001\naJ|Go\\2pY\u0002\naa]=ti\u0016l\u0017aB:zgR,W\u000eI\u0001\u0005Q>\u001cH/F\u0001Y!\r)\u0014lS\u0005\u00035Z\u0012aa\u00149uS>t\u0017!\u00025pgR\u0004\u0013\u0001\u00029peR,\u0012A\u0018\t\u0004ke{\u0006CA\u001ba\u0013\t\tgGA\u0002J]R\fQ\u0001]8si\u0002\na\u0001P5oSRtD#B3hQ&T\u0007C\u00014\u0001\u001b\u0005q\u0003\"B%\n\u0001\u0004Y\u0005\"\u0002+\n\u0001\u0004Y\u0005\"\u0002,\n\u0001\u0004A\u0006\"\u0002/\n\u0001\u0004qFcA3m[\")\u0011J\u0003a\u0001\u0017\")AK\u0003a\u0001\u0017R)Qm\u001c9re\")\u0011j\u0003a\u0001\u0017\")Ak\u0003a\u0001\u0017\")ak\u0003a\u0001\u0017\")Al\u0003a\u0001?\u0006!1m\u001c9z)\u0015)WO^<y\u0011\u001dIE\u0002%AA\u0002-Cq\u0001\u0016\u0007\u0011\u0002\u0003\u00071\nC\u0004W\u0019A\u0005\t\u0019\u0001-\t\u000fqc\u0001\u0013!a\u0001=\u0006q1m\u001c9zI\u0011,g-Y;mi\u0012\nT#A>+\u0005-c8&A?\u0011\u0007y\f9!D\u0001\u0000\u0015\u0011\t\t!a\u0001\u0002\u0013Ut7\r[3dW\u0016$'bAA\u0003m\u0005Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\u0007\u0005%qPA\tv]\u000eDWmY6fIZ\u000b'/[1oG\u0016\fabY8qs\u0012\"WMZ1vYR$#'\u0001\bd_BLH\u0005Z3gCVdG\u000fJ\u001a\u0016\u0005\u0005E!F\u0001-}\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIQ*\"!a\u0006+\u0005yc\u0018aB4fi\"{7\u000f\u001e\u000b\u0003\u0003;\u0001R!a\b\u0002*-k!!!\t\u000b\t\u0005\r\u0012QE\u0001\u0005kRLGN\u0003\u0002\u0002(\u0005!!.\u0019<b\u0013\u0011\tY#!\t\u0003\u0011=\u0003H/[8oC2\fqaZ3u!>\u0014H\u000f\u0006\u0002\u00022A1\u0011qDA\u0015\u0003g\u0001B!!\u000e\u0002<5\u0011\u0011q\u0007\u0006\u0005\u0003s\t)#\u0001\u0003mC:<\u0017\u0002BA\u001f\u0003o\u0011q!\u00138uK\u001e,'/A\u0007iCNdunY1m'\u000e|\u0007/Z\u000b\u0003\u0003\u0007\u00022!NA#\u0013\r\t9E\u000e\u0002\b\u0005>|G.Z1o\u00039A\u0017m]$m_\n\fGnU2pa\u0016\f\u0001\u0002[1tQ\u000e{G-Z\u000b\u0002?\"\u001aQ#!\u0015\u0011\u0007U\n\u0019&C\u0002\u0002VY\u0012\u0011\u0002\u001e:b]NLWM\u001c;\u0002\u0011Q|7\u000b\u001e:j]\u001eD3AFA)\u0003!Awn\u001d;Q_J$\u0018\u0001\u00075bg&sg/\u00197jI\"{7\u000f^\"iCJ\f7\r^3sg\"\u001a\u0001$!\u0019\u0011\t\u0005\r\u0014qM\u0007\u0003\u0003KR1!!\u00021\u0013\u0011\tI'!\u001a\u0003\u0017%sG/\u001a:oC2\f\u0005/[\u0001\u0014G\",7m\u001b%pgR\u001c\u0005.\u0019:bGR,'o\u001d\u000b\u0003\u0003_\u00022!NA9\u0013\r\t\u0019H\u000e\u0002\u0005+:LG\u000fK\u0002\u001a\u0003C\nQ\u0002\u001d:pIV\u001cG\u000f\u0015:fM&DXCAA>!\u0011\t)$! \n\u0007E\u000b9$\u0001\u0007qe>$Wo\u0019;Be&$\u00180\u0001\bqe>$Wo\u0019;FY\u0016lWM\u001c;\u0015\t\u0005\u0015\u00151\u0012\t\u0004k\u0005\u001d\u0015bAAEm\t\u0019\u0011I\\=\t\u0011\u00055E$!AA\u0002}\u000b1\u0001\u001f\u00132\u0003=\u0001(o\u001c3vGRLE/\u001a:bi>\u0014XCAAJ!\u0019\t)*a'\u0002\u00066\u0011\u0011q\u0013\u0006\u0004\u000333\u0014AC2pY2,7\r^5p]&!\u0011QTAL\u0005!IE/\u001a:bi>\u0014\u0018\u0001C2b]\u0016\u000bX/\u00197\u0015\t\u0005\r\u00131\u0015\u0005\n\u0003\u001bs\u0012\u0011!a\u0001\u0003\u000b\u000b!\u0003\u001d:pIV\u001cG/\u00127f[\u0016tGOT1nKR!\u00111PAU\u0011!\tiiHA\u0001\u0002\u0004y\u0016AB3rk\u0006d7\u000f\u0006\u0003\u0002D\u0005=\u0006\"CAGA\u0005\u0005\t\u0019AACQ\u001d\u0001\u00111WA]\u0003w\u00032!NA[\u0013\r\t9L\u000e\u0002\u0011'\u0016\u0014\u0018.\u00197WKJ\u001c\u0018n\u001c8V\u0013\u0012\u000bQA^1mk\u0016t\u0012!A\u0001\b\u0003\u0012$'/Z:t!\t1'e\u0005\u0003#i\u0005\r\u0007\u0003BAc\u0003\u0017l!!a2\u000b\t\u0005%\u0017QE\u0001\u0003S>L1aRAd)\t\ty,\u0001\tJ]Z\fG.\u001b3I_N$(+Z4fqV\u0011\u00111\u001b\t\u0005\u0003+\fi.\u0004\u0002\u0002X*!\u0011\u0011\\An\u0003!i\u0017\r^2iS:<'bAA\u0012m%!\u0011q\\Al\u0005\u0015\u0011VmZ3y\u0003EIeN^1mS\u0012Dun\u001d;SK\u001e,\u0007\u0010I\u0001\u0006CB\u0004H.\u001f\u000b\u0006K\u0006\u001d\u0018\u0011\u001e\u0005\u0006\u0013\u001a\u0002\ra\u0013\u0005\u0006)\u001a\u0002\ra\u0013\u000b\nK\u00065\u0018q^Ay\u0003gDQ!S\u0014A\u0002-CQ\u0001V\u0014A\u0002-CQAV\u0014A\u0002-CQ\u0001X\u0014A\u0002}\u000bq\"\u00193ee\u0016\u001c8o\u0014:eKJLgnZ\u000b\u0003\u0003s\u0004BAPA~K&\u0019\u0011Q %\u0003\u0011=\u0013H-\u001a:j]\u001e\f\u0001#\u00193ee\u0016\u001c8o\u0014:eKJLgn\u001a\u0011\u0015\u0013\u0015\u0014\u0019A!\u0002\u0003\b\t%\u0001\"B%+\u0001\u0004Y\u0005\"\u0002++\u0001\u0004Y\u0005\"\u0002,+\u0001\u0004A\u0006\"\u0002/+\u0001\u0004q\u0016aB;oCB\u0004H.\u001f\u000b\u0005\u0005\u001f\u00119\u0002\u0005\u000363\nE\u0001cB\u001b\u0003\u0014-[\u0005LX\u0005\u0004\u0005+1$A\u0002+va2,G\u0007\u0003\u0005\u0003\u001a-\n\t\u00111\u0001f\u0003\rAH\u0005M\u0001\roJLG/\u001a*fa2\f7-\u001a\u000b\u0003\u0005?\u0001B!!\u000e\u0003\"%!!1EA\u001c\u0005\u0019y%M[3di\u0002")
public final class Address
implements Product,
Serializable {
    private static final long serialVersionUID = 1L;
    private transient int hashCode;
    private transient String toString;
    private final String protocol;
    private final String system;
    private final Option<String> host;
    private final Option<Object> port;
    private volatile transient byte bitmap$trans$0;

    public static Option<Tuple4<String, String, Option<String>, Option<Object>>> unapply(Address x$0) {
        return Address$.MODULE$.unapply(x$0);
    }

    public static Address apply(String protocol, String system, Option<String> host2, Option<Object> port) {
        return Address$.MODULE$.apply(protocol, system, host2, port);
    }

    public static Ordering<Address> addressOrdering() {
        return Address$.MODULE$.addressOrdering();
    }

    public static Address apply(String protocol, String system, String host2, int port) {
        return Address$.MODULE$.apply(protocol, system, host2, port);
    }

    public static Address apply(String protocol, String system) {
        return Address$.MODULE$.apply(protocol, system);
    }

    public static Regex InvalidHostRegex() {
        return Address$.MODULE$.InvalidHostRegex();
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public String protocol() {
        return this.protocol;
    }

    public String system() {
        return this.system;
    }

    public Option<String> host() {
        return this.host;
    }

    public Option<Object> port() {
        return this.port;
    }

    public Address copy(String protocol, String system, Option<String> host2, Option<Object> port) {
        return Address$.MODULE$.apply(protocol, system, host2, port);
    }

    public String copy$default$1() {
        return this.protocol();
    }

    public String copy$default$2() {
        return this.system();
    }

    public Option<String> copy$default$3() {
        return this.host();
    }

    public Option<Object> copy$default$4() {
        return this.port();
    }

    public Optional<String> getHost() {
        return OptionConverters$RichOptionForJava8$.MODULE$.asJava$extension(OptionConverters$.MODULE$.RichOptionForJava8(this.host()));
    }

    public Optional<Integer> getPort() {
        return OptionConverters$RichOptionForJava8$.MODULE$.asJava$extension(OptionConverters$.MODULE$.RichOptionForJava8(this.port()));
    }

    public boolean hasLocalScope() {
        return this.host().isEmpty();
    }

    public boolean hasGlobalScope() {
        return this.host().isDefined();
    }

    private int hashCode$lzycompute() {
        Address address = this;
        synchronized (address) {
            if ((byte)(this.bitmap$trans$0 & 1) == 0) {
                this.hashCode = MurmurHash3$.MODULE$.productHash(this);
                this.bitmap$trans$0 = (byte)(this.bitmap$trans$0 | 1);
            }
        }
        return this.hashCode;
    }

    public int hashCode() {
        if ((byte)(this.bitmap$trans$0 & 1) == 0) {
            return this.hashCode$lzycompute();
        }
        return this.hashCode;
    }

    private String toString$lzycompute() {
        Address address = this;
        synchronized (address) {
            if ((byte)(this.bitmap$trans$0 & 2) == 0) {
                StringBuilder sb = new StringBuilder(this.protocol()).append("://").append(this.system());
                Serializable serializable = this.host().isDefined() ? sb.append('@').append(this.host().get()) : BoxedUnit.UNIT;
                Serializable serializable2 = this.port().isDefined() ? sb.append(':').append(BoxesRunTime.unboxToInt(this.port().get())) : BoxedUnit.UNIT;
                this.toString = sb.toString();
                this.bitmap$trans$0 = (byte)(this.bitmap$trans$0 | 2);
            }
        }
        return this.toString;
    }

    public String toString() {
        if ((byte)(this.bitmap$trans$0 & 2) == 0) {
            return this.toString$lzycompute();
        }
        return this.toString;
    }

    public String hostPort() {
        return this.toString().substring(this.protocol().length() + 3);
    }

    @InternalApi
    public boolean hasInvalidHostCharacters() {
        return this.host().exists((Function1<String, Object> & Serializable)x$1 -> BoxesRunTime.boxToBoolean(Address.$anonfun$hasInvalidHostCharacters$1(x$1)));
    }

    @InternalApi
    public void checkHostCharacters() {
        Predef$.MODULE$.require(!this.hasInvalidHostCharacters(), (Function0<Object>)(Function0<String> & Serializable)() -> new StringBuilder(63).append("Using invalid host characters '").append(this.host()).append("' in the Address is not allowed.").toString());
    }

    @Override
    public String productPrefix() {
        return "Address";
    }

    @Override
    public int productArity() {
        return 4;
    }

    @Override
    public Object productElement(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return this.protocol();
            }
            case 1: {
                return this.system();
            }
            case 2: {
                return this.host();
            }
            case 3: {
                return this.port();
            }
        }
        return Statics.ioobe(x$1);
    }

    @Override
    public Iterator<Object> productIterator() {
        return ScalaRunTime$.MODULE$.typedProductIterator(this);
    }

    @Override
    public boolean canEqual(Object x$1) {
        return x$1 instanceof Address;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "protocol";
            }
            case 1: {
                return "system";
            }
            case 2: {
                return "host";
            }
            case 3: {
                return "port";
            }
        }
        return (String)Statics.ioobe(x$1);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public boolean equals(Object x$1) {
        if (this == x$1) return true;
        Object object = x$1;
        if (!(object instanceof Address)) return false;
        boolean bl = true;
        if (!bl) return false;
        Address address = (Address)x$1;
        String string = this.protocol();
        String string2 = address.protocol();
        if (string == null) {
            if (string2 != null) {
                return false;
            }
        } else if (!string.equals(string2)) return false;
        String string3 = this.system();
        String string4 = address.system();
        if (string3 == null) {
            if (string4 != null) {
                return false;
            }
        } else if (!string3.equals(string4)) return false;
        Option<String> option = this.host();
        Option<String> option2 = address.host();
        if (option == null) {
            if (option2 != null) {
                return false;
            }
        } else if (!option.equals(option2)) return false;
        Option<Object> option3 = this.port();
        Option<Object> option4 = address.port();
        if (option3 == null) {
            if (option4 == null) return true;
            return false;
        } else {
            if (!option3.equals(option4)) return false;
            return true;
        }
    }

    public static final /* synthetic */ boolean $anonfun$hasInvalidHostCharacters$1(String x$1) {
        return Address$.MODULE$.InvalidHostRegex().findFirstIn(x$1).nonEmpty();
    }

    public Address(String protocol, String system, Option<String> host2, Option<Object> port) {
        this.protocol = protocol;
        this.system = system;
        this.host = host2;
        this.port = port;
        Product.$init$(this);
    }

    public Address(String protocol, String system) {
        this(protocol, system, None$.MODULE$, None$.MODULE$);
    }

    public Address(String protocol, String system, String host2, int port) {
        this(protocol, system, Option$.MODULE$.apply(host2), new Some<Object>(BoxesRunTime.boxToInteger(port)));
    }
}

