/*
 * Decompiled with CFR 0.152.
 */
package scala.reflect;

import scala.None$;
import scala.collection.immutable.List;
import scala.collection.immutable.Nil$;
import scala.collection.immutable.Seq;
import scala.reflect.AnyValManifest;
import scala.reflect.ClassTag;
import scala.reflect.Manifest$;
import scala.reflect.ManifestFactory;
import scala.reflect.ManifestFactory$;
import scala.reflect.ScalaSignature;
import scala.runtime.Nothing$;
import scala.runtime.Null$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\rmba\u0002\u001a4!\u0003\r\t\u0001\u000f\u0005\u0006)\u0002!\t!\u0016\u0005\u00063\u0002!\tE\u0017\u0005\u0006M\u0002!\te\u001a\u0005\u0006Y\u0002!\t%\u001c\u0005\u0006g\u0002!\t\u0005\u001e\u0005\u0006m\u0002!\te^\u0004\b\u0003\u0017\u0019\u0004\u0012AA\u0007\r\u0019\u00114\u0007#\u0001\u0002\u0010!9\u0011\u0011\u0005\u0005\u0005\u0002\u0005\r\u0002bBA\u0013\u0011\u0011\u0005\u0011q\u0005\u0005\n\u0003sA!\u0019!C\u0001\u0003wA\u0001\"a\u0013\tA\u0003%\u0011Q\b\u0005\n\u0003\u001bB!\u0019!C\u0001\u0003\u001fB\u0001\"a\u0016\tA\u0003%\u0011\u0011\u000b\u0005\n\u00033B!\u0019!C\u0001\u00037B\u0001\"a\u0019\tA\u0003%\u0011Q\f\u0005\n\u0003KB!\u0019!C\u0001\u0003OB\u0001\"a\u001c\tA\u0003%\u0011\u0011\u000e\u0005\n\u0003cB!\u0019!C\u0001\u0003gB\u0001\"a\u001f\tA\u0003%\u0011Q\u000f\u0005\n\u0003{B!\u0019!C\u0001\u0003\u007fB\u0001\"a\"\tA\u0003%\u0011\u0011\u0011\u0005\n\u0003\u0013C!\u0019!C\u0001\u0003\u0017C\u0001\"a%\tA\u0003%\u0011Q\u0012\u0005\n\u0003+C!\u0019!C\u0001\u0003/C\u0001\"a(\tA\u0003%\u0011\u0011\u0014\u0005\n\u0003CC!\u0019!C\u0001\u0003GC\u0001\"a+\tA\u0003%\u0011Q\u0015\u0005\n\u0003[C!\u0019!C\u0001\u0003_C\u0001\"a-\tA\u0003%\u0011\u0011\u0017\u0005\n\u0003kC!\u0019!C\u0001\u0003oC\u0001\"a2\tA\u0003%\u0011\u0011\u0018\u0005\n\u0003\u0013D!\u0019!C\u0001\u0003\u0017D\u0001\"a4\tA\u0003%\u0011Q\u001a\u0005\n\u0003#D!\u0019!C\u0001\u0003'D\u0001\"!8\tA\u0003%\u0011Q\u001b\u0005\n\u0003?D!\u0019!C\u0001\u0003CD\u0001\"a;\tA\u0003%\u00111\u001d\u0005\n\u0003[D!\u0019!C\u0001\u0003_D\u0001\"a=\tA\u0003%\u0011\u0011\u001f\u0005\b\u0003kDA\u0011AA|\u0011\u001d\u00119\u0001\u0003C\u0001\u0005\u0013AqAa\u0002\t\t\u0003\u0011i\u0003C\u0004\u0003\b!!\tAa\u0018\t\u000f\tE\u0005\u0002\"\u0001\u0003\u0014\"9!Q\u0016\u0005\u0005\u0002\t=\u0006b\u0002B{\u0011\u0011\u0005!q\u001f\u0005\b\u00077AA\u0011AB\u000f\u0011%\u00199\u0004CA\u0001\n\u0013\u0019ID\u0001\u0005NC:Lg-Z:u\u0015\t!T'A\u0004sK\u001adWm\u0019;\u000b\u0003Y\nQa]2bY\u0006\u001c\u0001!\u0006\u0002:\u0011N!\u0001A\u000f R!\tYD(D\u00016\u0013\tiTG\u0001\u0004B]f\u0014VM\u001a\t\u0004\u007f\r3eB\u0001!B\u001b\u0005\u0019\u0014B\u0001\"4\u0003\u001d\u0001\u0018mY6bO\u0016L!\u0001R#\u0003\u001b\rc\u0017m]:NC:Lg-Z:u\u0015\t\u00115\u0007\u0005\u0002H\u00112\u0001A!B%\u0001\u0005\u0004Q%!\u0001+\u0012\u0005-s\u0005CA\u001eM\u0013\tiUGA\u0004O_RD\u0017N\\4\u0011\u0005mz\u0015B\u0001)6\u0005\r\te.\u001f\t\u0003wIK!aU\u001b\u0003\r\u0015\u000bX/\u00197t\u0003\u0019!\u0013N\\5uIQ\ta\u000b\u0005\u0002</&\u0011\u0001,\u000e\u0002\u0005+:LG/A\u0007usB,\u0017I]4v[\u0016tGo]\u000b\u00027B\u0019ALX1\u000f\u0005mj\u0016B\u0001\"6\u0013\ty\u0006M\u0001\u0003MSN$(B\u0001\"6a\t\u0011G\rE\u0002A\u0001\r\u0004\"a\u00123\u0005\u0013\u0015\u0014\u0011\u0011!A\u0001\u0006\u0003Q%aA0%c\u0005i\u0011M\u001d:bs6\u000bg.\u001b4fgR,\u0012\u0001\u001b\t\u0004\u0001\u0002I\u0007cA\u001ek\r&\u00111.\u000e\u0002\u0006\u0003J\u0014\u0018-_\u0001\tG\u0006tW)];bYR\u0011a.\u001d\t\u0003w=L!\u0001]\u001b\u0003\u000f\t{w\u000e\\3b]\")!\u000f\u0002a\u0001\u001d\u0006!A\u000f[1u\u0003\u0019)\u0017/^1mgR\u0011a.\u001e\u0005\u0006e\u0016\u0001\rAT\u0001\tQ\u0006\u001c\bnQ8eKR\t\u0001\u0010\u0005\u0002<s&\u0011!0\u000e\u0002\u0004\u0013:$\bF\u0002\u0001}\u0003\u000b\t9\u0001E\u0002~\u0003\u0003i\u0011A \u0006\u0003\u007fV\n!\"\u00198o_R\fG/[8o\u0013\r\t\u0019A \u0002\u0011S6\u0004H.[2ji:{GOR8v]\u0012\f1!\\:hC\t\tI!A\u0010O_\u0002j\u0015M\\5gKN$\b%\u0019<bS2\f'\r\\3!M>\u0014\b\u0005J>U{:\n\u0001\"T1oS\u001a,7\u000f\u001e\t\u0003\u0001\"\u0019B\u0001\u0003\u001e\u0002\u0012A!\u00111CA\u000f\u001b\t\t)B\u0003\u0003\u0002\u0018\u0005e\u0011AA5p\u0015\t\tY\"\u0001\u0003kCZ\f\u0017\u0002BA\u0010\u0003+\u0011AbU3sS\u0006d\u0017N_1cY\u0016\fa\u0001P5oSRtDCAA\u0007\u000391\u0018\r\\;f\u001b\u0006t\u0017NZ3tiN,\"!!\u000b\u0011\tqs\u00161\u0006\u0019\u0005\u0003[\t)\u0004E\u0003A\u0003_\t\u0019$C\u0002\u00022M\u0012a\"\u00118z-\u0006dW*\u00198jM\u0016\u001cH\u000fE\u0002H\u0003k!!\"a\u000e\u000b\u0003\u0003\u0005\tQ!\u0001K\u0005\ryFEM\u0001\u0005\u0005f$X-\u0006\u0002\u0002>A!\u0011qHA#\u001d\r\u0001\u0015\u0011I\u0005\u0004\u0003\u0007\u001a\u0014aD'b]&4Wm\u001d;GC\u000e$xN]=\n\t\u0005\u001d\u0013\u0011\n\u0002\r\u0005f$X-T1oS\u001a,7\u000f\u001e\u0006\u0004\u0003\u0007\u001a\u0014!\u0002\"zi\u0016\u0004\u0013!B*i_J$XCAA)!\u0011\ty$a\u0015\n\t\u0005U\u0013\u0011\n\u0002\u000e'\"|'\u000f^'b]&4Wm\u001d;\u0002\rMCwN\u001d;!\u0003\u0011\u0019\u0005.\u0019:\u0016\u0005\u0005u\u0003\u0003BA \u0003?JA!!\u0019\u0002J\ta1\t[1s\u001b\u0006t\u0017NZ3ti\u0006)1\t[1sA\u0005\u0019\u0011J\u001c;\u0016\u0005\u0005%\u0004\u0003BA \u0003WJA!!\u001c\u0002J\tY\u0011J\u001c;NC:Lg-Z:u\u0003\u0011Ie\u000e\u001e\u0011\u0002\t1{gnZ\u000b\u0003\u0003k\u0002B!a\u0010\u0002x%!\u0011\u0011PA%\u00051auN\\4NC:Lg-Z:u\u0003\u0015auN\\4!\u0003\u00151En\\1u+\t\t\t\t\u0005\u0003\u0002@\u0005\r\u0015\u0002BAC\u0003\u0013\u0012QB\u00127pCRl\u0015M\\5gKN$\u0018A\u0002$m_\u0006$\b%\u0001\u0004E_V\u0014G.Z\u000b\u0003\u0003\u001b\u0003B!a\u0010\u0002\u0010&!\u0011\u0011SA%\u00059!u.\u001e2mK6\u000bg.\u001b4fgR\fq\u0001R8vE2,\u0007%A\u0004C_>dW-\u00198\u0016\u0005\u0005e\u0005\u0003BA \u00037KA!!(\u0002J\ty!i\\8mK\u0006tW*\u00198jM\u0016\u001cH/\u0001\u0005C_>dW-\u00198!\u0003\u0011)f.\u001b;\u0016\u0005\u0005\u0015\u0006\u0003BA \u0003OKA!!+\u0002J\taQK\\5u\u001b\u0006t\u0017NZ3ti\u0006)QK\\5uA\u0005\u0019\u0011I\\=\u0016\u0005\u0005E\u0006c\u0001!\u0001\u001d\u0006!\u0011I\\=!\u0003\u0019y%M[3diV\u0011\u0011\u0011\u0018\t\u0005\u0001\u0002\tY\f\u0005\u0003\u0002>\u0006\rWBAA`\u0015\u0011\t\t-!\u0007\u0002\t1\fgnZ\u0005\u0005\u0003\u000b\fyL\u0001\u0004PE*,7\r^\u0001\b\u001f\nTWm\u0019;!\u0003\u0019\te.\u001f*fMV\u0011\u0011Q\u001a\t\u0004\u0001\u0002Q\u0014aB!osJ+g\rI\u0001\u0007\u0003:Lh+\u00197\u0016\u0005\u0005U\u0007\u0003\u0002!\u0001\u0003/\u00042aOAm\u0013\r\tY.\u000e\u0002\u0007\u0003:Lh+\u00197\u0002\u000f\u0005s\u0017PV1mA\u0005!a*\u001e7m+\t\t\u0019\u000f\u0005\u0003A\u0001\u0005\u0015\bcA\u001e\u0002h&\u0019\u0011\u0011^\u001b\u0003\t9+H\u000e\\\u0001\u0006\u001dVdG\u000eI\u0001\b\u001d>$\b.\u001b8h+\t\t\t\u0010E\u0002A\u0001-\u000b\u0001BT8uQ&tw\rI\u0001\u000bg&tw\r\\3UsB,W\u0003BA}\u0003\u007f$B!a?\u0003\u0004A!\u0001\tAA\u007f!\r9\u0015q \u0003\u0007\u0013&\u0012\rA!\u0001\u0012\u0005-S\u0004B\u0002B\u0003S\u0001\u0007!(A\u0003wC2,X-A\u0005dY\u0006\u001c8\u000fV=qKV!!1\u0002B\t)\u0011\u0011iAa\u0005\u0011\t\u0001\u0003!q\u0002\t\u0004\u000f\nEA!B%+\u0005\u0004Q\u0005b\u0002B\u000bU\u0001\u0007!qC\u0001\u0006G2\f'P\u001f\u0019\u0005\u00053\u0011I\u0003\u0005\u0004\u0003\u001c\t\u0005\"q\u0005\b\u0004w\tu\u0011b\u0001B\u0010k\u00051\u0001K]3eK\u001aLAAa\t\u0003&\t)1\t\\1tg*\u0019!qD\u001b\u0011\u0007\u001d\u0013I\u0003B\u0006\u0003,\tM\u0011\u0011!A\u0001\u0006\u0003Q%aA0%gU!!q\u0006B\u001b)!\u0011\tDa\u000e\u0003<\t%\u0003\u0003\u0002!\u0001\u0005g\u00012a\u0012B\u001b\t\u0015I5F1\u0001K\u0011\u001d\u0011)b\u000ba\u0001\u0005s\u0001bAa\u0007\u0003\"\tM\u0002b\u0002B\u001fW\u0001\u0007!qH\u0001\u0005CJ<\u0017\u0007\r\u0003\u0003B\t\u0015\u0003\u0003\u0002!\u0001\u0005\u0007\u00022a\u0012B#\t-\u00119Ea\u000f\u0002\u0002\u0003\u0005)\u0011\u0001&\u0003\u0007}#C\u0007C\u0004\u0003L-\u0002\rA!\u0014\u0002\t\u0005\u0014xm\u001d\t\u0006w\t=#1K\u0005\u0004\u0005#*$A\u0003\u001fsKB,\u0017\r^3e}A\"!Q\u000bB-!\u0011\u0001\u0005Aa\u0016\u0011\u0007\u001d\u0013I\u0006B\u0006\u0003\\\tu\u0013\u0011!A\u0001\u0006\u0003Q%aA0%k!9!1J\u0016A\u0002\t5S\u0003\u0002B1\u0005O\"\u0002Ba\u0019\u0003j\t]$1\u0011\t\u0005\u0001\u0002\u0011)\u0007E\u0002H\u0005O\"Q!\u0013\u0017C\u0002)CqAa\u001b-\u0001\u0004\u0011i'\u0001\u0004qe\u00164\u0017\u000e\u001f\u0019\u0005\u0005_\u0012\u0019\b\u0005\u0003A\u0001\tE\u0004cA$\u0003t\u0011Y!Q\u000fB5\u0003\u0003\u0005\tQ!\u0001K\u0005\ryFE\u000e\u0005\b\u0005+a\u0003\u0019\u0001B=a\u0011\u0011YHa \u0011\r\tm!\u0011\u0005B?!\r9%q\u0010\u0003\f\u0005\u0003\u00139(!A\u0001\u0002\u000b\u0005!JA\u0002`I]BqAa\u0013-\u0001\u0004\u0011)\tE\u0003<\u0005\u001f\u00129\t\r\u0003\u0003\n\n5\u0005\u0003\u0002!\u0001\u0005\u0017\u00032a\u0012BG\t-\u0011yIa!\u0002\u0002\u0003\u0005)\u0011\u0001&\u0003\u0007}#\u0003(A\u0005beJ\f\u0017\u0010V=qKV!!Q\u0013BO)\u0011\u00119Ja(\u0011\t\u0001\u0003!\u0011\u0014\t\u0005w)\u0014Y\nE\u0002H\u0005;#Q!S\u0017C\u0002)CqA!).\u0001\u0004\u0011\u0019+A\u0002be\u001e\u0004DA!*\u0003*B!\u0001\t\u0001BT!\r9%\u0011\u0016\u0003\f\u0005W\u0013y*!A\u0001\u0002\u000b\u0005!JA\u0002`Ie\nA\"\u00192tiJ\f7\r\u001e+za\u0016,BA!-\u00038RQ!1\u0017B]\u0005\u000b\u0014INa:\u0011\t\u0001\u0003!Q\u0017\t\u0004\u000f\n]F!B%/\u0005\u0004Q\u0005b\u0002B6]\u0001\u0007!1\u0018\u0019\u0005\u0005{\u0013\t\r\u0005\u0003A\u0001\t}\u0006cA$\u0003B\u0012Y!1\u0019B]\u0003\u0003\u0005\tQ!\u0001K\u0005\u0011yF%\r\u0019\t\u000f\t\u001dg\u00061\u0001\u0003J\u0006!a.Y7f!\u0011\u0011YM!6\u000f\t\t5'Q\u0004\t\u0004\u0005\u001f,TB\u0001Bi\u0015\r\u0011\u0019nN\u0001\u0007yI|w\u000e\u001e \n\t\t]'Q\u0005\u0002\u0007'R\u0014\u0018N\\4\t\u000f\tmg\u00061\u0001\u0003^\u0006QQ\u000f\u001d9fe\n{WO\u001c31\t\t}'1\u001d\t\u0007\u00057\u0011\tC!9\u0011\u0007\u001d\u0013\u0019\u000fB\u0006\u0003f\ne\u0017\u0011!A\u0001\u0006\u0003Q%\u0001B0%cEBqAa\u0013/\u0001\u0004\u0011I\u000fE\u0003<\u0005\u001f\u0012Y\u000f\r\u0003\u0003n\nE\b\u0003\u0002!\u0001\u0005_\u00042a\u0012By\t-\u0011\u0019Pa:\u0002\u0002\u0003\u0005)\u0011\u0001&\u0003\t}#\u0013GM\u0001\ro&dGmY1sIRK\b/Z\u000b\u0005\u0005s\u0014y\u0010\u0006\u0004\u0003|\u000e\u00051q\u0002\t\u0005\u0001\u0002\u0011i\u0010E\u0002H\u0005\u007f$Q!S\u0018C\u0002)Cqaa\u00010\u0001\u0004\u0019)!\u0001\u0006m_^,'OQ8v]\u0012\u0004Daa\u0002\u0004\fA!\u0001\tAB\u0005!\r951\u0002\u0003\f\u0007\u001b\u0019\t!!A\u0001\u0002\u000b\u0005!J\u0001\u0003`IE\u001a\u0004b\u0002Bn_\u0001\u00071\u0011\u0003\u0019\u0005\u0007'\u00199\u0002\u0005\u0003A\u0001\rU\u0001cA$\u0004\u0018\u0011Y1\u0011DB\b\u0003\u0003\u0005\tQ!\u0001K\u0005\u0011yF%\r\u001b\u0002!%tG/\u001a:tK\u000e$\u0018n\u001c8UsB,W\u0003BB\u0010\u0007K!Ba!\t\u0004(A!\u0001\tAB\u0012!\r95Q\u0005\u0003\u0006\u0013B\u0012\rA\u0013\u0005\b\u0007S\u0001\u0004\u0019AB\u0016\u0003\u001d\u0001\u0018M]3oiN\u0004Ra\u000fB(\u0007[\u0001Daa\f\u00044A!\u0001\tAB\u0019!\r951\u0007\u0003\f\u0007k\u00199#!A\u0001\u0002\u000b\u0005!J\u0001\u0003`IE*\u0014\u0001D<sSR,'+\u001a9mC\u000e,GCAA^\u0001")
public interface Manifest<T>
extends ClassTag<T> {
    public static <T> Manifest<T> intersectionType(Seq<Manifest<?>> parents) {
        return ManifestFactory$.MODULE$.intersectionType(parents);
    }

    public static <T> Manifest<T> wildcardType(Manifest<?> lowerBound, Manifest<?> upperBound) {
        return new ManifestFactory.WildcardManifest(lowerBound, upperBound);
    }

    public static <T> Manifest<T> abstractType(Manifest<?> prefix, String name, Class<?> upperBound, Seq<Manifest<?>> args2) {
        return new ManifestFactory.AbstractTypeManifest(prefix, name, upperBound, args2);
    }

    public static <T> Manifest<Object> arrayType(Manifest<?> arg) {
        return arg.arrayManifest();
    }

    public static <T> Manifest<T> classType(Manifest<?> prefix, Class<?> clazz, Seq<Manifest<?>> args2) {
        return ManifestFactory$.MODULE$.classType(prefix, clazz, args2);
    }

    public static <T> Manifest<T> classType(Class<T> clazz, Manifest<?> arg1, Seq<Manifest<?>> args2) {
        return ManifestFactory$.MODULE$.classType(clazz, arg1, args2);
    }

    public static <T> Manifest<T> classType(Class<?> clazz) {
        return new ManifestFactory.ClassTypeManifest(None$.MODULE$, clazz, Nil$.MODULE$);
    }

    public static <T> Manifest<T> singleType(Object value) {
        return new ManifestFactory.SingletonTypeManifest(value);
    }

    public static Manifest<Nothing$> Nothing() {
        return Manifest$.MODULE$.Nothing();
    }

    public static Manifest<Null$> Null() {
        return Manifest$.MODULE$.Null();
    }

    public static Manifest<Object> AnyVal() {
        return Manifest$.MODULE$.AnyVal();
    }

    public static Manifest<Object> AnyRef() {
        return Manifest$.MODULE$.AnyRef();
    }

    public static Manifest<Object> Object() {
        return Manifest$.MODULE$.Object();
    }

    public static Manifest<Object> Any() {
        return Manifest$.MODULE$.Any();
    }

    public static ManifestFactory.UnitManifest Unit() {
        return Manifest$.MODULE$.Unit();
    }

    public static ManifestFactory.BooleanManifest Boolean() {
        return Manifest$.MODULE$.Boolean();
    }

    public static ManifestFactory.DoubleManifest Double() {
        return Manifest$.MODULE$.Double();
    }

    public static ManifestFactory.FloatManifest Float() {
        return Manifest$.MODULE$.Float();
    }

    public static ManifestFactory.LongManifest Long() {
        return Manifest$.MODULE$.Long();
    }

    public static ManifestFactory.IntManifest Int() {
        return Manifest$.MODULE$.Int();
    }

    public static ManifestFactory.CharManifest Char() {
        return Manifest$.MODULE$.Char();
    }

    public static ManifestFactory.ShortManifest Short() {
        return Manifest$.MODULE$.Short();
    }

    public static ManifestFactory.ByteManifest Byte() {
        return Manifest$.MODULE$.Byte();
    }

    public static List<AnyValManifest<?>> valueManifests() {
        return ManifestFactory$.MODULE$.valueManifests();
    }

    public static /* synthetic */ List typeArguments$(Manifest $this) {
        return $this.typeArguments();
    }

    @Override
    default public List<Manifest<?>> typeArguments() {
        return Nil$.MODULE$;
    }

    public static /* synthetic */ Manifest arrayManifest$(Manifest $this) {
        return $this.arrayManifest();
    }

    default public Manifest<Object> arrayManifest() {
        Nil$ classType_args = Nil$.MODULE$;
        Class<Object> classType_clazz = this.arrayClass(this.runtimeClass());
        return ManifestFactory$.MODULE$.classType(classType_clazz, this, classType_args);
    }

    public static /* synthetic */ boolean canEqual$(Manifest $this, Object that) {
        return $this.canEqual(that);
    }

    @Override
    default public boolean canEqual(Object that) {
        return that instanceof Manifest;
    }

    public static /* synthetic */ boolean equals$(Manifest $this, Object that) {
        return $this.equals(that);
    }

    @Override
    default public boolean equals(Object that) {
        if (that instanceof Manifest) {
            Manifest manifest = (Manifest)that;
            if (manifest.canEqual(this)) {
                Class<?> clazz = this.runtimeClass();
                Class<?> clazz2 = manifest.runtimeClass();
                if (!(clazz != null ? !clazz.equals(clazz2) : clazz2 != null)) {
                    if (this.$less$colon$less(manifest) && manifest.$less$colon$less(this)) {
                        return true;
                    }
                }
            }
            return false;
        }
        return false;
    }

    public static /* synthetic */ int hashCode$(Manifest $this) {
        return $this.hashCode();
    }

    @Override
    default public int hashCode() {
        return Statics.anyHash(this.runtimeClass());
    }

    public static void $init$(Manifest $this) {
    }
}

