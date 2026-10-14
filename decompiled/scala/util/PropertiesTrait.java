/*
 * Decompiled with CFR 0.152.
 */
package scala.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Properties;
import scala.Console$;
import scala.Function0;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.Option$;
import scala.Tuple2;
import scala.collection.immutable.$colon$colon;
import scala.collection.immutable.List;
import scala.collection.immutable.Nil$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.Nothing$;
import scala.runtime.Null$;

@ScalaSignature(bytes="\u0006\u0005\u0005eh\u0001C\u001f?!\u0003\r\t\u0001\u0011\"\t\u000b\u001d\u0003A\u0011A%\t\u000b5\u0003a\u0011\u0003(\t\u000bi\u0003a\u0011C.\t\u000f-\u0004!\u0019!C\tY\"AA\u000f\u0001EC\u0002\u0013EQ\u000fC\u0003|\u0001\u0011%A\u0010C\u0004\u0002\n\u0001!\t!a\u0003\t\u000f\u0005]\u0001\u0001\"\u0001\u0002\u001a!9\u0011\u0011\u0005\u0001\u0005\u0002\u0005\r\u0002bBA\u0017\u0001\u0011\u0005\u0011q\u0006\u0005\b\u0003g\u0001A\u0011AA\u001b\u0011\u001d\tI\u0004\u0001C\u0001\u0003wAq!!\u0012\u0001\t\u0003\t9\u0005C\u0004\u0002L\u0001!\t!!\u0014\t\u000f\u0005M\u0003\u0001\"\u0001\u0002V!9\u0011\u0011\f\u0001\u0005\u0002\u0005m\u0003bBA1\u0001\u0011\u0005\u00111\r\u0005\b\u0003O\u0002A\u0011AA5\u0011\u001d\t\u0019\b\u0001C\u0001\u0003kBq!a\u001f\u0001\t\u0003\ti\bC\u0004\u0002\u0002\u0002!\t!a!\t\u0013\u0005\u001d\u0005A1A\u0005\u0002\u0005%\u0005\"CAF\u0001\t\u0007I\u0011AAE\u0011\u0019\ti\t\u0001C\u0001\u001d\"A\u0011q\u0012\u0001C\u0002\u0013\u0005A\u000e\u0003\u0005\u0002\u0012\u0002\u0011\r\u0011\"\u0001O\u0011\u0019\t\u0019\n\u0001C\u0001\u001d\"1\u0011Q\u0013\u0001\u0005\u00029Ca!a&\u0001\t\u0003a\u0007BBAM\u0001\u0011\u0005A\u000e\u0003\u0004\u0002\u001c\u0002!\t\u0001\u001c\u0005\u0007\u0003;\u0003A\u0011\u00017\t\r\u0005}\u0005\u0001\"\u0001m\u0011\u0019\t\t\u000b\u0001C\u0001Y\"1\u00111\u0015\u0001\u0005\u00021Da!!*\u0001\t\u0003a\u0007BBAT\u0001\u0011\u0005A\u000e\u0003\u0004\u0002*\u0002!\t\u0001\u001c\u0005\u0007\u0003W\u0003A\u0011\u00017\t\r\u00055\u0006\u0001\"\u0001m\u0011\u0019\ty\u000b\u0001C\u0001Y\"1\u0011\u0011\u0017\u0001\u0005\u00021Da!a-\u0001\t\u0003a\u0007BBA[\u0001\u0011\u0005A\u000e\u0003\u0004\u00028\u0002!\t\u0001\u001c\u0005\u0007\u0003s\u0003A\u0011\u00017\t\r\u0005m\u0006\u0001\"\u0001m\u0011\u001d\ti\f\u0001C\u0001\u0003\u007fCq!!1\u0001\t\u0003\ty\fC\u0004\u0002D\u0002!\t!a0\t\u0011\u0005\u0015\u0007\u0001\"\u0001A\u0003\u007fC\u0001\"a2\u0001\t\u0003\u0001\u0015q\u0018\u0005\u0007\u0003\u0013\u0004A\u0011\u00017\t\u0011\u0005-\u0007\u0001\"\u0001A\u0003\u001bDa!a5\u0001\t\u0003a\u0007BBAk\u0001\u0011\u0005A\u000e\u0003\u0004\u0002X\u0002!\t\u0001\u001c\u0005\b\u00033\u0004A\u0011AAn\u0011\u001d\tI\u000e\u0001C\u0001\u0003CDq!a;\u0001\t\u0003\tiOA\bQe>\u0004XM\u001d;jKN$&/Y5u\u0015\ty\u0004)\u0001\u0003vi&d'\"A!\u0002\u000bM\u001c\u0017\r\\1\u0014\u0005\u0001\u0019\u0005C\u0001#F\u001b\u0005\u0001\u0015B\u0001$A\u0005\u0019\te.\u001f*fM\u00061A%\u001b8ji\u0012\u001a\u0001\u0001F\u0001K!\t!5*\u0003\u0002M\u0001\n!QK\\5u\u00031\u0001(o\u001c9DCR,wm\u001c:z+\u0005y\u0005C\u0001)X\u001d\t\tV\u000b\u0005\u0002S\u00016\t1K\u0003\u0002U\u0011\u00061AH]8pizJ!A\u0016!\u0002\rA\u0013X\rZ3g\u0013\tA\u0016L\u0001\u0004TiJLgn\u001a\u0006\u0003-\u0002\u000ba\u0002]5dW*\u000b'OQ1tK\u0012|e.F\u0001]a\ti&\rE\u0002Q=\u0002L!aX-\u0003\u000b\rc\u0017m]:\u0011\u0005\u0005\u0014G\u0002\u0001\u0003\nG\u000e\t\t\u0011!A\u0003\u0002\u0011\u00141a\u0018\u00133#\t)\u0007\u000e\u0005\u0002EM&\u0011q\r\u0011\u0002\b\u001d>$\b.\u001b8h!\t!\u0015.\u0003\u0002k\u0001\n\u0019\u0011I\\=\u0002\u0019A\u0014x\u000e\u001d$jY\u0016t\u0017-\\3\u0016\u00035\u0004\"A\\:\u000e\u0003=T!\u0001]9\u0002\t1\fgn\u001a\u0006\u0002e\u0006!!.\u0019<b\u0013\tAv.\u0001\u0006tG\u0006d\u0017\r\u0015:paN,\u0012A\u001e\t\u0003ofl\u0011\u0001\u001f\u0006\u0003\u007fEL!A\u001f=\u0003\u0015A\u0013x\u000e]3si&,7/\u0001\brk&,G\u000f\\=ESN\u0004xn]3\u0015\t)k\u0018Q\u0001\u0005\u0007}\u001a!\t\u0019A@\u0002\r\u0005\u001cG/[8o!\u0011!\u0015\u0011\u0001&\n\u0007\u0005\r\u0001I\u0001\u0005=Eft\u0017-\\3?\u0011\u001d\t9A\u0002CA\u0002}\f\u0001\u0002Z5ta>\u001c\u0018\r\\\u0001\naJ|\u0007/S:TKR$B!!\u0004\u0002\u0014A\u0019A)a\u0004\n\u0007\u0005E\u0001IA\u0004C_>dW-\u00198\t\r\u0005Uq\u00011\u0001P\u0003\u0011q\u0017-\\3\u0002\u0017A\u0014x\u000e]%t'\u0016$Hk\u001c\u000b\u0007\u0003\u001b\tY\"!\b\t\r\u0005U\u0001\u00021\u0001P\u0011\u0019\ty\u0002\u0003a\u0001\u001f\u0006)a/\u00197vK\u0006Q\u0001O]8q\u001fJ,En]3\u0015\u000b5\f)#a\n\t\r\u0005U\u0011\u00021\u0001P\u0011!\tI#\u0003CA\u0002\u0005-\u0012aA1miB!A)!\u0001P\u0003-\u0001(o\u001c9Pe\u0016k\u0007\u000f^=\u0015\u00075\f\t\u0004\u0003\u0004\u0002\u0016)\u0001\raT\u0001\u000baJ|\u0007o\u0014:Ok2dGcA7\u00028!1\u0011QC\u0006A\u0002=\u000b!\u0002\u001d:pa>\u0013hj\u001c8f)\u0011\ti$a\u0011\u0011\t\u0011\u000by$\\\u0005\u0004\u0003\u0003\u0002%AB(qi&|g\u000e\u0003\u0004\u0002\u00161\u0001\raT\u0001\faJ|\u0007o\u0014:GC2\u001cX\r\u0006\u0003\u0002\u000e\u0005%\u0003BBA\u000b\u001b\u0001\u0007q*A\u0004tKR\u0004&o\u001c9\u0015\u000b5\fy%!\u0015\t\r\u0005Ua\u00021\u0001P\u0011\u0019\tyB\u0004a\u0001\u001f\u0006I1\r\\3beB\u0013x\u000e\u001d\u000b\u0004[\u0006]\u0003BBA\u000b\u001f\u0001\u0007q*A\u0005f]Z|%/\u00127tKR)Q.!\u0018\u0002`!1\u0011Q\u0003\tA\u0002=C\u0001\"!\u000b\u0011\t\u0003\u0007\u00111F\u0001\nK:4xJ\u001d(p]\u0016$B!!\u0010\u0002f!1\u0011QC\tA\u0002=\u000b\u0011\"\u001a8w\u001fJ\u001cv.\\3\u0015\r\u0005u\u00121NA7\u0011\u0019\t)B\u0005a\u0001\u001f\"A\u0011\u0011\u0006\n\u0005\u0002\u0004\ty\u0007E\u0003E\u0003\u0003\t\t\b\u0005\u0003E\u0003\u007fy\u0015aD:dC2\f\u0007K]8q\u001fJ,En]3\u0015\u000b=\u000b9(!\u001f\t\r\u0005U1\u00031\u0001P\u0011!\tIc\u0005CA\u0002\u0005-\u0012\u0001E:dC2\f\u0007K]8q\u001fJ,U\u000e\u001d;z)\ry\u0015q\u0010\u0005\u0007\u0003+!\u0002\u0019A(\u0002\u001fM\u001c\u0017\r\\1Qe>\u0004xJ\u001d(p]\u0016$B!!\u001d\u0002\u0006\"1\u0011QC\u000bA\u0002=\u000baB]3mK\u0006\u001cXMV3sg&|g.\u0006\u0002\u0002r\u0005\u0011B-\u001a<fY>\u0004X.\u001a8u-\u0016\u00148/[8o\u0003M1XM]:j_:tU/\u001c2feN#(/\u001b8h\u000351XM]:j_:\u001cFO]5oO\u0006y1m\u001c9ze&<\u0007\u000e^*ue&tw-\u0001\bt_V\u00148-Z#oG>$\u0017N\\4\u0002\u0019M|WO]2f%\u0016\fG-\u001a:\u0002\u001d\u0015t7m\u001c3j]\u001e\u001cFO]5oO\u0006iA.\u001b8f'\u0016\u0004\u0018M]1u_J\fQB[1wC\u000ec\u0017m]:QCRD\u0017\u0001\u00036bm\u0006Du.\\3\u0002\u0015)\fg/\u0019,f]\u0012|'/A\u0006kCZ\fg+\u001a:tS>t\u0017A\u00036bm\u00064V.\u00138g_\u0006Q!.\u0019<b-6t\u0015-\\3\u0002\u0019)\fg/\u0019,n-\u0016tGm\u001c:\u0002\u001b)\fg/\u0019,n-\u0016\u00148/[8o\u0003=Q\u0017M^1Ta\u0016\u001cg+\u001a:tS>t\u0017A\u00046bm\u0006\u001c\u0006/Z2WK:$wN]\u0001\rU\u00064\u0018m\u00159fG:\u000bW.Z\u0001\u0007_Nt\u0015-\\3\u0002\u0013M\u001c\u0017\r\\1I_6,\u0017A\u0002;na\u0012K'/A\u0004vg\u0016\u0014H)\u001b:\u0002\u0011U\u001cXM\u001d%p[\u0016\f\u0001\"^:fe:\u000bW.Z\u0001\u0006SN<\u0016N\\\u000b\u0003\u0003\u001b\tQ![:NC\u000e\fq![:MS:,\b0A\u0004jg\u00063\u0018.\u00198\u0002)\r|Gn\u001c:fI>+H\u000f];u\u000b:\f'\r\\3e\u0003\u001dQGm\u001b%p[\u0016\f!B^3sg&|gNR8s)\ri\u0017q\u001a\u0005\u0007\u0003#4\u0004\u0019A(\u0002\u000f\r|W.\\1oI\u0006Qa/\u001a:tS>tWj]4\u0002\u0011M\u001c\u0017\r\\1D[\u0012\f\u0011b]2bY\u0006\u001c7)\u001c3\u0002\u001b%\u001c(*\u0019<b\u0003RdU-Y:u)\u0011\ti!!8\t\r\u0005}'\b1\u0001P\u0003\u001d1XM]:j_:$B!!\u0004\u0002d\"9\u0011q\\\u001eA\u0002\u0005\u0015\bc\u0001#\u0002h&\u0019\u0011\u0011\u001e!\u0003\u0007%sG/\u0001\u0003nC&tGc\u0001&\u0002p\"9\u0011\u0011\u001f\u001fA\u0002\u0005M\u0018\u0001B1sON\u0004B\u0001RA{\u001f&\u0019\u0011q\u001f!\u0003\u000b\u0005\u0013(/Y=")
public interface PropertiesTrait {
    public void scala$util$PropertiesTrait$_setter_$propFilename_$eq(String var1);

    public void scala$util$PropertiesTrait$_setter_$releaseVersion_$eq(Option<String> var1);

    public void scala$util$PropertiesTrait$_setter_$developmentVersion_$eq(Option<String> var1);

    public void scala$util$PropertiesTrait$_setter_$versionString_$eq(String var1);

    public void scala$util$PropertiesTrait$_setter_$copyrightString_$eq(String var1);

    public String propCategory();

    public Class<?> pickJarBasedOn();

    public String propFilename();

    public static /* synthetic */ Properties scalaProps$(PropertiesTrait $this) {
        return $this.scalaProps();
    }

    default public Properties scalaProps() {
        Properties props = new Properties();
        InputStream stream = this.pickJarBasedOn().getResourceAsStream(this.propFilename());
        if (stream != null) {
            try {
                props.load(stream);
            }
            catch (Throwable throwable) {
                try {
                    stream.close();
                }
                catch (IOException iOException) {}
                throw throwable;
            }
            try {
                stream.close();
            }
            catch (IOException iOException) {}
        }
        return props;
    }

    private void quietlyDispose(Function0<BoxedUnit> action, Function0<BoxedUnit> disposal) {
        try {
            action.apply$mcV$sp();
        }
        catch (Throwable throwable) {
            try {
                disposal.apply$mcV$sp();
            }
            catch (IOException iOException) {}
            throw throwable;
        }
        try {
            disposal.apply$mcV$sp();
            return;
        }
        catch (IOException iOException) {
            return;
        }
    }

    public static /* synthetic */ boolean propIsSet$(PropertiesTrait $this, String name) {
        return $this.propIsSet(name);
    }

    default public boolean propIsSet(String name) {
        return System.getProperty(name) != null;
    }

    public static /* synthetic */ boolean propIsSetTo$(PropertiesTrait $this, String name, String value) {
        return $this.propIsSetTo(name, value);
    }

    default public boolean propIsSetTo(String name, String value) {
        String string = this.propOrNull(name);
        return !(string != null ? !string.equals(value) : value != null);
    }

    public static /* synthetic */ String propOrElse$(PropertiesTrait $this, String name, Function0 alt) {
        return $this.propOrElse(name, alt);
    }

    default public String propOrElse(String name, Function0<String> alt) {
        Option<String> option = Option$.MODULE$.apply(System.getProperty(name));
        if (option == null) {
            throw null;
        }
        Option<String> getOrElse_this = option;
        return getOrElse_this.isEmpty() ? alt.apply() : getOrElse_this.get();
    }

    public static /* synthetic */ String propOrEmpty$(PropertiesTrait $this, String name) {
        return $this.propOrEmpty(name);
    }

    default public String propOrEmpty(String name) {
        return this.propOrElse(name, (Function0<String> & Serializable)() -> "");
    }

    public static /* synthetic */ String propOrNull$(PropertiesTrait $this, String name) {
        return $this.propOrNull(name);
    }

    default public String propOrNull(String name) {
        return this.propOrElse(name, (Function0<String>)(Function0<Null$> & Serializable)() -> null);
    }

    public static /* synthetic */ Option propOrNone$(PropertiesTrait $this, String name) {
        return $this.propOrNone(name);
    }

    default public Option<String> propOrNone(String name) {
        return Option$.MODULE$.apply(this.propOrNull(name));
    }

    public static /* synthetic */ boolean propOrFalse$(PropertiesTrait $this, String name) {
        return $this.propOrFalse(name);
    }

    default public boolean propOrFalse(String name) {
        Option<String> option = this.propOrNone(name);
        if (option == null) {
            throw null;
        }
        Option<String> exists_this = option;
        return !exists_this.isEmpty() && PropertiesTrait.$anonfun$propOrFalse$1(exists_this.get());
    }

    public static /* synthetic */ String setProp$(PropertiesTrait $this, String name, String value) {
        return $this.setProp(name, value);
    }

    default public String setProp(String name, String value) {
        return System.setProperty(name, value);
    }

    public static /* synthetic */ String clearProp$(PropertiesTrait $this, String name) {
        return $this.clearProp(name);
    }

    default public String clearProp(String name) {
        return System.clearProperty(name);
    }

    public static /* synthetic */ String envOrElse$(PropertiesTrait $this, String name, Function0 alt) {
        return $this.envOrElse(name, alt);
    }

    default public String envOrElse(String name, Function0<String> alt) {
        Option<String> option = Option$.MODULE$.apply(System.getenv(name));
        if (option == null) {
            throw null;
        }
        Option<String> getOrElse_this = option;
        return getOrElse_this.isEmpty() ? alt.apply() : getOrElse_this.get();
    }

    public static /* synthetic */ Option envOrNone$(PropertiesTrait $this, String name) {
        return $this.envOrNone(name);
    }

    default public Option<String> envOrNone(String name) {
        return Option$.MODULE$.apply(System.getenv(name));
    }

    public static /* synthetic */ Option envOrSome$(PropertiesTrait $this, String name, Function0 alt) {
        return $this.envOrSome(name, alt);
    }

    /*
     * WARNING - void declaration
     */
    default public Option<String> envOrSome(String name, Function0<Option<String>> alt) {
        void var3_3;
        Option<String> option = this.envOrNone(name);
        if (option == null) {
            throw null;
        }
        Option<String> orElse_this = option;
        if (orElse_this.isEmpty()) {
            return alt.apply();
        }
        return var3_3;
    }

    public static /* synthetic */ String scalaPropOrElse$(PropertiesTrait $this, String name, Function0 alt) {
        return $this.scalaPropOrElse(name, alt);
    }

    default public String scalaPropOrElse(String name, Function0<String> alt) {
        Option<String> option = this.scalaPropOrNone(name);
        if (option == null) {
            throw null;
        }
        Option<String> getOrElse_this = option;
        return getOrElse_this.isEmpty() ? alt.apply() : getOrElse_this.get();
    }

    public static /* synthetic */ String scalaPropOrEmpty$(PropertiesTrait $this, String name) {
        return $this.scalaPropOrEmpty(name);
    }

    default public String scalaPropOrEmpty(String name) {
        return this.scalaPropOrElse(name, (Function0<String> & Serializable)() -> "");
    }

    public static /* synthetic */ Option scalaPropOrNone$(PropertiesTrait $this, String name) {
        return $this.scalaPropOrNone(name);
    }

    /*
     * WARNING - void declaration
     */
    default public Option<String> scalaPropOrNone(String name) {
        void var2_2;
        Option<String> option = Option$.MODULE$.apply(this.scalaProps().getProperty(name));
        if (option == null) {
            throw null;
        }
        Option<String> orElse_this = option;
        if (orElse_this.isEmpty()) {
            return this.propOrNone(new StringBuilder(6).append("scala.").append(name).toString());
        }
        return var2_2;
    }

    public Option<String> releaseVersion();

    public Option<String> developmentVersion();

    public static /* synthetic */ String versionNumberString$(PropertiesTrait $this) {
        return $this.versionNumberString();
    }

    default public String versionNumberString() {
        return this.scalaPropOrEmpty("version.number");
    }

    public String versionString();

    public String copyrightString();

    public static /* synthetic */ String sourceEncoding$(PropertiesTrait $this) {
        return $this.sourceEncoding();
    }

    default public String sourceEncoding() {
        return this.scalaPropOrElse("file.encoding", (Function0<String> & Serializable)() -> "UTF-8");
    }

    public static /* synthetic */ String sourceReader$(PropertiesTrait $this) {
        return $this.sourceReader();
    }

    default public String sourceReader() {
        return this.scalaPropOrElse("source.reader", (Function0<String> & Serializable)() -> "scala.tools.nsc.io.SourceReader");
    }

    public static /* synthetic */ String encodingString$(PropertiesTrait $this) {
        return $this.encodingString();
    }

    default public String encodingString() {
        return this.propOrElse("file.encoding", (Function0<String> & Serializable)() -> "UTF-8");
    }

    public static /* synthetic */ String lineSeparator$(PropertiesTrait $this) {
        return $this.lineSeparator();
    }

    default public String lineSeparator() {
        return System.lineSeparator();
    }

    public static /* synthetic */ String javaClassPath$(PropertiesTrait $this) {
        return $this.javaClassPath();
    }

    default public String javaClassPath() {
        return this.propOrEmpty("java.class.path");
    }

    public static /* synthetic */ String javaHome$(PropertiesTrait $this) {
        return $this.javaHome();
    }

    default public String javaHome() {
        return this.propOrEmpty("java.home");
    }

    public static /* synthetic */ String javaVendor$(PropertiesTrait $this) {
        return $this.javaVendor();
    }

    default public String javaVendor() {
        return this.propOrEmpty("java.vendor");
    }

    public static /* synthetic */ String javaVersion$(PropertiesTrait $this) {
        return $this.javaVersion();
    }

    default public String javaVersion() {
        return this.propOrEmpty("java.version");
    }

    public static /* synthetic */ String javaVmInfo$(PropertiesTrait $this) {
        return $this.javaVmInfo();
    }

    default public String javaVmInfo() {
        return this.propOrEmpty("java.vm.info");
    }

    public static /* synthetic */ String javaVmName$(PropertiesTrait $this) {
        return $this.javaVmName();
    }

    default public String javaVmName() {
        return this.propOrEmpty("java.vm.name");
    }

    public static /* synthetic */ String javaVmVendor$(PropertiesTrait $this) {
        return $this.javaVmVendor();
    }

    default public String javaVmVendor() {
        return this.propOrEmpty("java.vm.vendor");
    }

    public static /* synthetic */ String javaVmVersion$(PropertiesTrait $this) {
        return $this.javaVmVersion();
    }

    default public String javaVmVersion() {
        return this.propOrEmpty("java.vm.version");
    }

    public static /* synthetic */ String javaSpecVersion$(PropertiesTrait $this) {
        return $this.javaSpecVersion();
    }

    default public String javaSpecVersion() {
        return this.propOrEmpty("java.specification.version");
    }

    public static /* synthetic */ String javaSpecVendor$(PropertiesTrait $this) {
        return $this.javaSpecVendor();
    }

    default public String javaSpecVendor() {
        return this.propOrEmpty("java.specification.vendor");
    }

    public static /* synthetic */ String javaSpecName$(PropertiesTrait $this) {
        return $this.javaSpecName();
    }

    default public String javaSpecName() {
        return this.propOrEmpty("java.specification.name");
    }

    public static /* synthetic */ String osName$(PropertiesTrait $this) {
        return $this.osName();
    }

    default public String osName() {
        return this.propOrEmpty("os.name");
    }

    public static /* synthetic */ String scalaHome$(PropertiesTrait $this) {
        return $this.scalaHome();
    }

    default public String scalaHome() {
        return this.propOrEmpty("scala.home");
    }

    public static /* synthetic */ String tmpDir$(PropertiesTrait $this) {
        return $this.tmpDir();
    }

    default public String tmpDir() {
        return this.propOrEmpty("java.io.tmpdir");
    }

    public static /* synthetic */ String userDir$(PropertiesTrait $this) {
        return $this.userDir();
    }

    default public String userDir() {
        return this.propOrEmpty("user.dir");
    }

    public static /* synthetic */ String userHome$(PropertiesTrait $this) {
        return $this.userHome();
    }

    default public String userHome() {
        return this.propOrEmpty("user.home");
    }

    public static /* synthetic */ String userName$(PropertiesTrait $this) {
        return $this.userName();
    }

    default public String userName() {
        return this.propOrEmpty("user.name");
    }

    public static /* synthetic */ boolean isWin$(PropertiesTrait $this) {
        return $this.isWin();
    }

    default public boolean isWin() {
        return this.osName().startsWith("Windows");
    }

    public static /* synthetic */ boolean isMac$(PropertiesTrait $this) {
        return $this.isMac();
    }

    default public boolean isMac() {
        return this.osName().startsWith("Mac OS X");
    }

    public static /* synthetic */ boolean isLinux$(PropertiesTrait $this) {
        return $this.isLinux();
    }

    default public boolean isLinux() {
        return this.osName().startsWith("Linux");
    }

    public static /* synthetic */ boolean isAvian$(PropertiesTrait $this) {
        return $this.isAvian();
    }

    default public boolean isAvian() {
        return this.javaVmName().contains("Avian");
    }

    public static /* synthetic */ boolean coloredOutputEnabled$(PropertiesTrait $this) {
        return $this.coloredOutputEnabled();
    }

    default public boolean coloredOutputEnabled() {
        String string = this.propOrElse("scala.color", (Function0<String> & Serializable)() -> "auto");
        switch (string == null ? 0 : string.hashCode()) {
            case 3005871: {
                if (!"auto".equals(string)) break;
                return System.console() != null && !this.isWin();
            }
        }
        String string2 = string;
        String string3 = "";
        return string2 != null && string2.equals(string3) || "true".equalsIgnoreCase(string);
    }

    public static /* synthetic */ String jdkHome$(PropertiesTrait $this) {
        return $this.jdkHome();
    }

    default public String jdkHome() {
        return this.envOrElse("JDK_HOME", (Function0<String> & Serializable)() -> this.envOrElse("JAVA_HOME", (Function0<String> & Serializable)() -> this.javaHome()));
    }

    public static /* synthetic */ String versionFor$(PropertiesTrait $this, String command) {
        return $this.versionFor(command);
    }

    default public String versionFor(String command) {
        return new StringBuilder(11).append("Scala ").append(command).append(" ").append(this.versionString()).append(" -- ").append(this.copyrightString()).toString();
    }

    public static /* synthetic */ String versionMsg$(PropertiesTrait $this) {
        return $this.versionMsg();
    }

    default public String versionMsg() {
        return this.versionFor(this.propCategory());
    }

    public static /* synthetic */ String scalaCmd$(PropertiesTrait $this) {
        return $this.scalaCmd();
    }

    default public String scalaCmd() {
        if (this.isWin()) {
            return "scala.bat";
        }
        return "scala";
    }

    public static /* synthetic */ String scalacCmd$(PropertiesTrait $this) {
        return $this.scalacCmd();
    }

    default public String scalacCmd() {
        if (this.isWin()) {
            return "scalac.bat";
        }
        return "scalac";
    }

    public static /* synthetic */ boolean isJavaAtLeast$(PropertiesTrait $this, String version) {
        return $this.isJavaAtLeast(version);
    }

    default public boolean isJavaAtLeast(String version) {
        int n = this.compareVersions$1(this.javaSpecVersion(), version, 0);
        switch (n) {
            case -2: {
                throw new NumberFormatException(new StringBuilder(15).append("Not a version: ").append(version).toString());
            }
        }
        return n >= 0;
    }

    public static /* synthetic */ boolean isJavaAtLeast$(PropertiesTrait $this, int version) {
        return $this.isJavaAtLeast(version);
    }

    default public boolean isJavaAtLeast(int version) {
        int max_y = 0;
        return this.isJavaAtLeast(Integer.toString(Math.max(version, max_y)));
    }

    public static /* synthetic */ void main$(PropertiesTrait $this, String[] args2) {
        $this.main(args2);
    }

    default public void main(String[] args2) {
        new PrintWriter(Console$.MODULE$.err(), true).println(this.versionMsg());
    }

    public static /* synthetic */ void $anonfun$scalaProps$1(Properties props$1, InputStream stream$1) {
        props$1.load(stream$1);
    }

    public static /* synthetic */ void $anonfun$scalaProps$2(InputStream stream$1) {
        stream$1.close();
    }

    public static /* synthetic */ boolean $anonfun$propOrFalse$1(String x) {
        return new $colon$colon<Nothing$>((Nothing$)((Object)"yes"), (List<Nothing$>)new $colon$colon<Nothing$>((Nothing$)((Object)"on"), (List<Nothing$>)new $colon$colon<Nothing$>((Nothing$)((Object)"true"), Nil$.MODULE$))).contains(x.toLowerCase());
    }

    public static /* synthetic */ Option $anonfun$developmentVersion$2(PropertiesTrait $this, String x$3) {
        return $this.scalaPropOrNone("version.number");
    }

    /*
     * WARNING - void declaration
     */
    private static Tuple2 versionOf$1(String s2, int depth) {
        int n;
        int n2 = s2.indexOf(46);
        switch (n2) {
            case 0: {
                return new Tuple2<Integer, String>(-2, s2.substring(1));
            }
            case 1: {
                void var6_6;
                void var5_5;
                if (depth != 0 || s2.charAt(0) != '1') break;
                String r0 = s2.substring(2);
                Tuple2 tuple2 = PropertiesTrait.versionOf$1(r0, 1);
                if (tuple2 == null) {
                    throw new MatchError((Object)null);
                }
                int v = tuple2._1$mcI$sp();
                String r = (String)tuple2._2();
                int n3 = var5_5 > 8 || r0.isEmpty() ? -2 : var5_5;
                return new Tuple2<Integer, void>(n3, var6_6);
            }
            case -1: {
                int n4 = !s2.isEmpty() ? Integer.parseInt(s2) : (depth == 0 ? -2 : 0);
                return new Tuple2<Integer, String>(n4, "");
            }
        }
        String r = s2.substring(n2 + 1);
        if (depth < 2 && r.isEmpty()) {
            n = -2;
        } else {
            String toInt$extension_$this = s2.substring(0, n2);
            n = Integer.parseInt(toInt$extension_$this);
            Object var11_10 = null;
        }
        int n5 = n;
        return new Tuple2<Integer, String>(n5, r);
    }

    /*
     * WARNING - void declaration
     */
    private int compareVersions$1(String s2, String v, int depth) {
        while (depth < 3) {
            void var6_6;
            void var9_9;
            void var5_5;
            void var8_8;
            Tuple2 tuple2 = PropertiesTrait.versionOf$1(s2, depth);
            if (tuple2 == null) {
                throw new MatchError((Object)null);
            }
            int sn = tuple2._1$mcI$sp();
            String srest = (String)tuple2._2();
            Tuple2 tuple22 = PropertiesTrait.versionOf$1(v, depth);
            if (tuple22 == null) {
                throw new MatchError((Object)null);
            }
            int vn = tuple22._1$mcI$sp();
            String vrest = (String)tuple22._2();
            if (var8_8 < false) {
                return -2;
            }
            if (var5_5 < var8_8) {
                return -1;
            }
            if (var5_5 > var8_8) {
                return 1;
            }
            ++depth;
            v = var9_9;
            s2 = var6_6;
        }
        return 0;
    }

    public static void $init$(PropertiesTrait $this) {
        Option option;
        $this.scala$util$PropertiesTrait$_setter_$propFilename_$eq(new StringBuilder(12).append("/").append($this.propCategory()).append(".properties").toString());
        None$ none$ = $this.scalaPropOrNone("maven.version.number");
        if (none$ == null) {
            throw null;
        }
        Object var1_1 = null;
        None$ filterNot_this = none$;
        $this.scala$util$PropertiesTrait$_setter_$releaseVersion_$eq(filterNot_this.isEmpty() || !((Option)filterNot_this).get().endsWith("-SNAPSHOT") ? filterNot_this : None$.MODULE$);
        None$ none$2 = $this.scalaPropOrNone("maven.version.number");
        if (none$2 == null) {
            throw null;
        }
        Object var2_2 = null;
        None$ filter_this = none$2;
        None$ flatMap_this = filter_this.isEmpty() || ((Option)filter_this).get().endsWith("-SNAPSHOT") ? filter_this : None$.MODULE$;
        if (flatMap_this.isEmpty()) {
            option = None$.MODULE$;
        } else {
            String cfr_ignored_0 = (String)((Option)flatMap_this).get();
            option = $this.scalaPropOrNone("version.number");
        }
        Object var3_3 = null;
        $this.scala$util$PropertiesTrait$_setter_$developmentVersion_$eq(option);
        $this.scala$util$PropertiesTrait$_setter_$versionString_$eq(new StringBuilder(8).append("version ").append($this.scalaPropOrElse("version.number", (Function0<String> & Serializable)() -> "(unknown)")).toString());
        $this.scala$util$PropertiesTrait$_setter_$copyrightString_$eq($this.scalaPropOrElse("copyright.string", (Function0<String> & Serializable)() -> "Copyright 2002-2022, LAMP/EPFL and Lightbend, Inc."));
    }

    public static /* synthetic */ Object $anonfun$propOrFalse$1$adapted(String x) {
        return BoxesRunTime.boxToBoolean(PropertiesTrait.$anonfun$propOrFalse$1(x));
    }

    public static /* synthetic */ Object $anonfun$releaseVersion$1$adapted(String x$1) {
        return BoxesRunTime.boxToBoolean(x$1.endsWith("-SNAPSHOT"));
    }

    public static /* synthetic */ Object $anonfun$developmentVersion$1$adapted(String x$2) {
        return BoxesRunTime.boxToBoolean(x$2.endsWith("-SNAPSHOT"));
    }
}

