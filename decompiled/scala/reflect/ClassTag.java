/*
 * Decompiled with CFR 0.152.
 */
package scala.reflect;

import java.lang.reflect.Array;
import scala.Equals;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.collection.immutable.List;
import scala.collection.mutable.ArrayBuilder;
import scala.collection.mutable.ArraySeq;
import scala.reflect.ClassManifestDeprecatedApis;
import scala.reflect.ClassTag$;
import scala.reflect.ManifestFactory;
import scala.reflect.OptManifest;
import scala.reflect.ScalaSignature;
import scala.runtime.Nothing$;
import scala.runtime.Null$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\r]ba\u0002 @!\u0003\r\t\u0001\u0012\u0005\u0006G\u0002!\t\u0001\u001a\u0005\u0006Q\u00021\t!\u001b\u0005\u0006m\u0002!\ta\u001e\u0005\u0006y\u0002!\t! \u0005\b\u0003\u000f\u0001A\u0011AA\u0005\u0011\u001d\t)\u0002\u0001C!\u0003/Aq!!\t\u0001\t\u0003\n\u0019\u0003C\u0004\u0002(\u0001!\t%!\u000b\t\u000f\u0005-\u0002\u0001\"\u0011\u0002.\u001d9\u0011\u0011L \t\u0002\u0005mcA\u0002 @\u0011\u0003\ti\u0006C\u0004\u0002j-!\t!a\u001b\t\u0011\u000554\u0002)A\u0005\u0003_B\u0001\"a\u001e\fA\u0003%\u0011\u0011\u0010\u0005\t\u0003\u000f[\u0001\u0015!\u0003\u0002\n\"I\u0011\u0011S\u0006C\u0002\u0013\u0005\u00111\u0013\u0005\t\u0003G[\u0001\u0015!\u0003\u0002\u0016\"I\u0011QU\u0006C\u0002\u0013\u0005\u0011q\u0015\u0005\t\u0003_[\u0001\u0015!\u0003\u0002*\"I\u0011\u0011W\u0006C\u0002\u0013\u0005\u00111\u0017\u0005\t\u0003w[\u0001\u0015!\u0003\u00026\"I\u0011QX\u0006C\u0002\u0013\u0005\u0011q\u0018\u0005\t\u0003\u000f\\\u0001\u0015!\u0003\u0002B\"I\u0011\u0011Z\u0006C\u0002\u0013\u0005\u00111\u001a\u0005\t\u0003'\\\u0001\u0015!\u0003\u0002N\"I\u0011Q[\u0006C\u0002\u0013\u0005\u0011q\u001b\u0005\t\u0003?\\\u0001\u0015!\u0003\u0002Z\"I\u0011\u0011]\u0006C\u0002\u0013\u0005\u00111\u001d\u0005\t\u0003W\\\u0001\u0015!\u0003\u0002f\"I\u0011Q^\u0006C\u0002\u0013\u0005\u0011q\u001e\u0005\t\u0003o\\\u0001\u0015!\u0003\u0002r\"I\u0011\u0011`\u0006C\u0002\u0013\u0005\u00111 \u0005\t\u0005\u0007Y\u0001\u0015!\u0003\u0002~\"I!QA\u0006C\u0002\u0013\u0005!q\u0001\u0005\t\u0005\u0017Y\u0001\u0015!\u0003\u0003\n!I!QB\u0006C\u0002\u0013\u0005!q\u0002\u0005\t\u0005'Y\u0001\u0015!\u0003\u0003\u0012!I!QC\u0006C\u0002\u0013\u0005!q\u0003\u0005\t\u0005CY\u0001\u0015!\u0003\u0003\u001a!I!1E\u0006C\u0002\u0013\u0005!Q\u0005\u0005\t\u0005SY\u0001\u0015!\u0003\u0003(!I!1F\u0006C\u0002\u0013\u0005!Q\u0006\u0005\t\u0005cY\u0001\u0015!\u0003\u00030!I!1G\u0006C\u0002\u0013\u0005!Q\u0007\u0005\t\u0005\u007fY\u0001\u0015!\u0003\u00038!I!\u0011I\u0006C\u0002\u0013%!1\t\u0005\t\u0005\u000bZ\u0001\u0015!\u0003\u0002\u001a\u001dA!qI\u0006!\u0012\u0013\u0011IE\u0002\u0005\u0003N-\u0001\u000b\u0012\u0002B(\u0011\u001d\tI'\rC\u0001\u0005[BqAa\u001c2\t\u0003\u0012\t\bC\u0004\u0003\fF\"\tA!$\t\u000f\t\u0015\u0016\u0007\"\u0003\u0003(\u001a1!QY\u0006\u0005\u0005\u000fD\u0011\u0002\u001b\u001c\u0003\u0006\u0004%\tA!5\t\u0015\tugG!A!\u0002\u0013\u0011\u0019\u000eC\u0004\u0002jY\"\tAa8\t\rq4D\u0011\tBw\u0011\u001d\u0011yp\u0003C\u0001\u0007\u0003Aq!a\u0002\f\t\u0003\u0019I\u0002C\u0005\u00044-\t\t\u0011\"\u0003\u00046\tA1\t\\1tgR\u000bwM\u0003\u0002A\u0003\u00069!/\u001a4mK\u000e$(\"\u0001\"\u0002\u000bM\u001c\u0017\r\\1\u0004\u0001U\u0011Q\tU\n\u0006\u0001\u0019S\u0015\f\u0018\t\u0003\u000f\"k\u0011!Q\u0005\u0003\u0013\u0006\u0013a!\u00118z%\u00164\u0007cA&M\u001d6\tq(\u0003\u0002N\u007f\tY2\t\\1tg6\u000bg.\u001b4fgR$U\r\u001d:fG\u0006$X\rZ!qSN\u0004\"a\u0014)\r\u0001\u0011)\u0011\u000b\u0001b\u0001%\n\tA+\u0005\u0002T-B\u0011q\tV\u0005\u0003+\u0006\u0013qAT8uQ&tw\r\u0005\u0002H/&\u0011\u0001,\u0011\u0002\u0004\u0003:L\bCA$[\u0013\tY\u0016I\u0001\u0004FcV\fGn\u001d\t\u0003;\u0002t!a\u00120\n\u0005}\u000b\u0015a\u00029bG.\fw-Z\u0005\u0003C\n\u0014AbU3sS\u0006d\u0017N_1cY\u0016T!aX!\u0002\r\u0011Jg.\u001b;%)\u0005)\u0007CA$g\u0013\t9\u0017I\u0001\u0003V]&$\u0018\u0001\u0004:v]RLW.Z\"mCN\u001cX#\u000161\u0005-$\bc\u00017rg6\tQN\u0003\u0002o_\u0006!A.\u00198h\u0015\u0005\u0001\u0018\u0001\u00026bm\u0006L!A]7\u0003\u000b\rc\u0017m]:\u0011\u0005=#H!C;\u0003\u0003\u0003\u0005\tQ!\u0001S\u0005\ryF%M\u0001\u0005oJ\f\u0007/F\u0001y!\rY\u0005!\u001f\t\u0004\u000fjt\u0015BA>B\u0005\u0015\t%O]1z\u0003!qWm^!se\u0006LHCA=\u007f\u0011\u0019yH\u00011\u0001\u0002\u0002\u0005\u0019A.\u001a8\u0011\u0007\u001d\u000b\u0019!C\u0002\u0002\u0006\u0005\u00131!\u00138u\u0003\u001d)h.\u00199qYf$B!a\u0003\u0002\u0012A!q)!\u0004O\u0013\r\ty!\u0011\u0002\u0007\u001fB$\u0018n\u001c8\t\r\u0005MQ\u00011\u0001W\u0003\u0005A\u0018\u0001C2b]\u0016\u000bX/\u00197\u0015\t\u0005e\u0011q\u0004\t\u0004\u000f\u0006m\u0011bAA\u000f\u0003\n9!i\\8mK\u0006t\u0007BBA\n\r\u0001\u0007a+\u0001\u0004fcV\fGn\u001d\u000b\u0005\u00033\t)\u0003\u0003\u0004\u0002\u0014\u001d\u0001\rAV\u0001\tQ\u0006\u001c\bnQ8eKR\u0011\u0011\u0011A\u0001\ti>\u001cFO]5oOR\u0011\u0011q\u0006\t\u0005\u0003c\tyD\u0004\u0003\u00024\u0005m\u0002cAA\u001b\u00036\u0011\u0011q\u0007\u0006\u0004\u0003s\u0019\u0015A\u0002\u001fs_>$h(C\u0002\u0002>\u0005\u000ba\u0001\u0015:fI\u00164\u0017\u0002BA!\u0003\u0007\u0012aa\u0015;sS:<'bAA\u001f\u0003\":\u0001!a\u0012\u0002T\u0005U\u0003\u0003BA%\u0003\u001fj!!a\u0013\u000b\u0007\u00055\u0013)\u0001\u0006b]:|G/\u0019;j_:LA!!\u0015\u0002L\t\u0001\u0012.\u001c9mS\u000eLGOT8u\r>,h\u000eZ\u0001\u0004[N<\u0017EAA,\u0003yqu\u000eI\"mCN\u001cH+Y4!CZ\f\u0017\u000e\\1cY\u0016\u0004cm\u001c:!Im$V0\u0001\u0005DY\u0006\u001c8\u000fV1h!\tY5b\u0005\u0003\f\r\u0006}\u0003\u0003BA1\u0003Oj!!a\u0019\u000b\u0007\u0005\u0015t.\u0001\u0002j_&\u0019\u0011-a\u0019\u0002\rqJg.\u001b;?)\t\tY&\u0001\u0006PE*,7\r\u001e+Z!\u0016\u0003B\u0001\\9\u0002rA\u0019A.a\u001d\n\u0007\u0005UTN\u0001\u0004PE*,7\r^\u0001\f\u001d>$\b.\u001b8h)f\u0003V\t\u0005\u0003mc\u0006m\u0004\u0003BA?\u0003\u0007k!!a \u000b\u0007\u0005\u0005\u0015)A\u0004sk:$\u0018.\\3\n\t\u0005\u0015\u0015q\u0010\u0002\t\u001d>$\b.\u001b8hI\u0005Aa*\u001e7m)f\u0003V\t\u0005\u0003mc\u0006-\u0005\u0003BA?\u0003\u001bKA!a$\u0002\u0000\t)a*\u001e7mI\u0005!!)\u001f;f+\t\t)\n\u0005\u0003\u0002\u0018\u0006uebA&\u0002\u001a&\u0019\u00111T \u0002\u001f5\u000bg.\u001b4fgR4\u0015m\u0019;pefLA!a(\u0002\"\na!)\u001f;f\u001b\u0006t\u0017NZ3ti*\u0019\u00111T \u0002\u000b\tKH/\u001a\u0011\u0002\u000bMCwN\u001d;\u0016\u0005\u0005%\u0006\u0003BAL\u0003WKA!!,\u0002\"\ni1\u000b[8si6\u000bg.\u001b4fgR\faa\u00155peR\u0004\u0013\u0001B\"iCJ,\"!!.\u0011\t\u0005]\u0015qW\u0005\u0005\u0003s\u000b\tK\u0001\u0007DQ\u0006\u0014X*\u00198jM\u0016\u001cH/A\u0003DQ\u0006\u0014\b%A\u0002J]R,\"!!1\u0011\t\u0005]\u00151Y\u0005\u0005\u0003\u000b\f\tKA\u0006J]Rl\u0015M\\5gKN$\u0018\u0001B%oi\u0002\nA\u0001T8oOV\u0011\u0011Q\u001a\t\u0005\u0003/\u000by-\u0003\u0003\u0002R\u0006\u0005&\u0001\u0004'p]\u001el\u0015M\\5gKN$\u0018!\u0002'p]\u001e\u0004\u0013!\u0002$m_\u0006$XCAAm!\u0011\t9*a7\n\t\u0005u\u0017\u0011\u0015\u0002\u000e\r2|\u0017\r^'b]&4Wm\u001d;\u0002\r\u0019cw.\u0019;!\u0003\u0019!u.\u001e2mKV\u0011\u0011Q\u001d\t\u0005\u0003/\u000b9/\u0003\u0003\u0002j\u0006\u0005&A\u0004#pk\ndW-T1oS\u001a,7\u000f^\u0001\b\t>,(\r\\3!\u0003\u001d\u0011un\u001c7fC:,\"!!=\u0011\t\u0005]\u00151_\u0005\u0005\u0003k\f\tKA\bC_>dW-\u00198NC:Lg-Z:u\u0003!\u0011un\u001c7fC:\u0004\u0013\u0001B+oSR,\"!!@\u0011\t\u0005]\u0015q`\u0005\u0005\u0005\u0003\t\tK\u0001\u0007V]&$X*\u00198jM\u0016\u001cH/A\u0003V]&$\b%A\u0002B]f,\"A!\u0003\u0011\u0007-\u0003a+\u0001\u0003B]f\u0004\u0013AB(cU\u0016\u001cG/\u0006\u0002\u0003\u0012A!1\nAA9\u0003\u001dy%M[3di\u0002\na!\u00118z-\u0006dWC\u0001B\r!\u0011Y\u0005Aa\u0007\u0011\u0007\u001d\u0013i\"C\u0002\u0003 \u0005\u0013a!\u00118z-\u0006d\u0017aB!osZ\u000bG\u000eI\u0001\u0007\u0003:L(+\u001a4\u0016\u0005\t\u001d\u0002cA&\u0001\r\u00069\u0011I\\=SK\u001a\u0004\u0013a\u0002(pi\"LgnZ\u000b\u0003\u0005_\u00012a\u0013\u0001T\u0003!qu\u000e\u001e5j]\u001e\u0004\u0013\u0001\u0002(vY2,\"Aa\u000e\u0011\t-\u0003!\u0011\b\t\u0004\u000f\nm\u0012b\u0001B\u001f\u0003\n!a*\u001e7m\u0003\u0015qU\u000f\u001c7!\u00035\u0019\u0017m\u00195f\t&\u001c\u0018M\u00197fIV\u0011\u0011\u0011D\u0001\u000fG\u0006\u001c\u0007.\u001a#jg\u0006\u0014G.\u001a3!\u0003\u0015\u0019\u0017m\u00195f!\r\u0011Y%M\u0007\u0002\u0017\t)1-Y2iKN\u0019\u0011G!\u0015\u0011\r\u0005u$1\u000bB,\u0013\u0011\u0011)&a \u0003!\rc\u0017m]:WC2,XmQ8na\u0006$\bC\u0002B-\u0005?\u0012\u0019'\u0004\u0002\u0003\\)\u0019!QL7\u0002\u0007I,g-\u0003\u0003\u0003b\tm#!D,fC.\u0014VMZ3sK:\u001cW\r\r\u0003\u0003f\t%\u0004\u0003B&\u0001\u0005O\u00022a\u0014B5\t)\u0011Y'MA\u0001\u0002\u0003\u0015\tA\u0015\u0002\u0004?\u00122DC\u0001B%\u00031\u0019w.\u001c9vi\u00164\u0016\r\\;f)\u0011\u0011\u0019Ha \u0011\r\te#q\fB;a\u0011\u00119Ha\u001f\u0011\t-\u0003!\u0011\u0010\t\u0004\u001f\nmDA\u0003B?g\u0005\u0005\t\u0011!B\u0001%\n\u0019q\f\n\u001d\t\r!\u001c\u0004\u0019\u0001BAa\u0011\u0011\u0019Ia\"\u0011\t1\f(Q\u0011\t\u0004\u001f\n\u001dEa\u0003BE\u0005\u007f\n\t\u0011!A\u0003\u0002I\u00131a\u0018\u00138\u0003)\u0019w.\u001c9vi\u0016$\u0016m\u001a\u000b\u0005\u0005\u001f\u0013I\n\r\u0003\u0003\u0012\nU\u0005\u0003B&\u0001\u0005'\u00032a\u0014BK\t)\u00119\nNA\u0001\u0002\u0003\u0015\tA\u0015\u0002\u0005?\u0012\n\u0004\u0007\u0003\u0004ii\u0001\u0007!1\u0014\u0019\u0005\u0005;\u0013\t\u000b\u0005\u0003mc\n}\u0005cA(\u0003\"\u0012Y!1\u0015BM\u0003\u0003\u0005\tQ!\u0001S\u0005\ryF%O\u0001\u0012aJLW.\u001b;jm\u0016\u001cE.Y:t)\u0006<W\u0003\u0002BU\u0005\u0007$BAa+\u00036B\"!Q\u0016BY!\u0011Y\u0005Aa,\u0011\u0007=\u0013\t\f\u0002\u0006\u00034V\n\t\u0011!A\u0003\u0002I\u0013Aa\u0018\u00132e!1\u0001.\u000ea\u0001\u0005o\u0003DA!/\u0003@B1\u0011\u0011\u0007B^\u0005{K1A]A\"!\ry%q\u0018\u0003\f\u0005\u0003\u0014),!A\u0001\u0002\u000b\u0005!K\u0001\u0003`IE\nD!B)6\u0005\u0004\u0011&aD$f]\u0016\u0014\u0018nY\"mCN\u001cH+Y4\u0016\t\t%'qZ\n\u0005m\u0019\u0013Y\r\u0005\u0003L\u0001\t5\u0007cA(\u0003P\u0012)\u0011K\u000eb\u0001%V\u0011!1\u001b\u0019\u0005\u0005+\u0014I\u000e\u0005\u0003mc\n]\u0007cA(\u0003Z\u0012Q!1\u001c\u001d\u0002\u0002\u0003\u0005)\u0011\u0001*\u0003\t}#\u0013gM\u0001\u000eeVtG/[7f\u00072\f7o\u001d\u0011\u0015\t\t\u0005(1\u001d\t\u0006\u0005\u00172$Q\u001a\u0005\u0007Qf\u0002\rA!:1\t\t\u001d(1\u001e\t\u0005YF\u0014I\u000fE\u0002P\u0005W$1Ba7\u0003d\u0006\u0005\t\u0011!B\u0001%R!!q\u001eBy!\u00119%P!4\t\r}T\u0004\u0019AA\u0001Q\u001d1$Q\u001fB~\u0005{\u00042a\u0012B|\u0013\r\u0011I0\u0011\u0002\u0011'\u0016\u0014\u0018.\u00197WKJ\u001c\u0018n\u001c8V\u0013\u0012\u000bQA^1mk\u0016t\u0012!A\u0001\u0006CB\u0004H._\u000b\u0005\u0007\u0007\u0019I\u0001\u0006\u0003\u0004\u0006\r-\u0001\u0003B&\u0001\u0007\u000f\u00012aTB\u0005\t\u0015\t6H1\u0001S\u0011\u001d\u0019ia\u000fa\u0001\u0007\u001f\tQB];oi&lWm\u00117bgN\f\u0004\u0007BB\t\u0007+\u0001B\u0001\\9\u0004\u0014A\u0019qj!\u0006\u0005\u0017\r]11BA\u0001\u0002\u0003\u0015\tA\u0015\u0002\u0005?\u0012\nD'\u0006\u0003\u0004\u001c\rEB\u0003BB\u000f\u0007S\u0001RaRA\u0007\u0007?\u0001Da!\t\u0004&A1\u0011\u0011\u0007B^\u0007G\u00012aTB\u0013\t)\u00199\u0003PA\u0001\u0002\u0003\u0015\tA\u0015\u0002\u0005?\u0012\nT\u0007C\u0004\u0004,q\u0002\ra!\f\u0002\t\r$\u0018m\u001a\t\u0005\u0017\u0002\u0019y\u0003E\u0002P\u0007c!Q!\u0015\u001fC\u0002I\u000bAb\u001e:ji\u0016\u0014V\r\u001d7bG\u0016$\"!!\u001d")
public interface ClassTag<T>
extends ClassManifestDeprecatedApis<T>,
Equals {
    public static <T> ClassTag<T> apply(Class<?> runtimeClass1) {
        return ClassTag$.MODULE$.apply(runtimeClass1);
    }

    public static ClassTag<Null$> Null() {
        return ClassTag$.MODULE$.Null();
    }

    public static ClassTag<Nothing$> Nothing() {
        return ClassTag$.MODULE$.Nothing();
    }

    public static ClassTag<Object> AnyRef() {
        return ClassTag$.MODULE$.AnyRef();
    }

    public static ClassTag<Object> AnyVal() {
        return ClassTag$.MODULE$.AnyVal();
    }

    public static ClassTag<Object> Object() {
        return ClassTag$.MODULE$.Object();
    }

    public static ClassTag<Object> Any() {
        return ClassTag$.MODULE$.Any();
    }

    public static ManifestFactory.UnitManifest Unit() {
        return ClassTag$.MODULE$.Unit();
    }

    public static ManifestFactory.BooleanManifest Boolean() {
        return ClassTag$.MODULE$.Boolean();
    }

    public static ManifestFactory.DoubleManifest Double() {
        return ClassTag$.MODULE$.Double();
    }

    public static ManifestFactory.FloatManifest Float() {
        return ClassTag$.MODULE$.Float();
    }

    public static ManifestFactory.LongManifest Long() {
        return ClassTag$.MODULE$.Long();
    }

    public static ManifestFactory.IntManifest Int() {
        return ClassTag$.MODULE$.Int();
    }

    public static ManifestFactory.CharManifest Char() {
        return ClassTag$.MODULE$.Char();
    }

    public static ManifestFactory.ShortManifest Short() {
        return ClassTag$.MODULE$.Short();
    }

    public static ManifestFactory.ByteManifest Byte() {
        return ClassTag$.MODULE$.Byte();
    }

    public Class<?> runtimeClass();

    default public ClassTag<Object> wrap() {
        return ClassTag$.MODULE$.apply(this.arrayClass(this.runtimeClass()));
    }

    public static /* synthetic */ Object newArray$(ClassTag $this, int len) {
        return $this.newArray(len);
    }

    default public Object newArray(int len) {
        return Array.newInstance(this.runtimeClass(), len);
    }

    default public Option<T> unapply(Object x) {
        if (this.runtimeClass().isInstance(x)) {
            return new Some<Object>(x);
        }
        return None$.MODULE$;
    }

    @Override
    default public boolean canEqual(Object x) {
        return x instanceof ClassTag;
    }

    @Override
    default public boolean equals(Object x) {
        if (x instanceof ClassTag) {
            Class<?> clazz = this.runtimeClass();
            Class<?> clazz2 = ((ClassTag)x).runtimeClass();
            if (!(clazz != null ? !clazz.equals(clazz2) : clazz2 != null)) {
                return true;
            }
        }
        return false;
    }

    default public int hashCode() {
        return Statics.anyHash(this.runtimeClass());
    }

    default public String toString() {
        return ClassTag.prettyprint$1(this.runtimeClass());
    }

    private static String prettyprint$1(Class clazz) {
        if (clazz.isArray()) {
            return new StringBuilder(7).append("Array[").append(ClassTag.prettyprint$1(clazz.getComponentType())).append("]").toString();
        }
        return clazz.getName();
    }

    public static void $init$(ClassTag $this) {
    }

    private static class GenericClassTag<T>
    implements ClassTag<T> {
        private static final long serialVersionUID = 1L;
        private final Class<?> runtimeClass;

        @Override
        public ClassTag<Object> wrap() {
            return ((ClassTag)this).wrap();
        }

        @Override
        public Option<T> unapply(Object x) {
            return ((ClassTag)this).unapply(x);
        }

        @Override
        public boolean canEqual(Object x) {
            return ((ClassTag)this).canEqual(x);
        }

        @Override
        public boolean equals(Object x) {
            return ((ClassTag)this).equals(x);
        }

        @Override
        public int hashCode() {
            return ((ClassTag)this).hashCode();
        }

        @Override
        public String toString() {
            return ((ClassTag)this).toString();
        }

        @Override
        public Class<?> erasure() {
            return ClassManifestDeprecatedApis.erasure$(this);
        }

        @Override
        public boolean $less$colon$less(ClassTag<?> that) {
            return ClassManifestDeprecatedApis.$less$colon$less$(this, that);
        }

        @Override
        public boolean $greater$colon$greater(ClassTag<?> that) {
            return ClassManifestDeprecatedApis.$greater$colon$greater$(this, that);
        }

        @Override
        public <A> Class<Object> arrayClass(Class<?> tp) {
            return ClassManifestDeprecatedApis.arrayClass$(this, tp);
        }

        @Override
        public ClassTag<Object> arrayManifest() {
            return ClassManifestDeprecatedApis.arrayManifest$(this);
        }

        @Override
        public Object[] newArray2(int len) {
            return ClassManifestDeprecatedApis.newArray2$(this, len);
        }

        @Override
        public Object[][] newArray3(int len) {
            return ClassManifestDeprecatedApis.newArray3$(this, len);
        }

        @Override
        public Object[][][] newArray4(int len) {
            return ClassManifestDeprecatedApis.newArray4$(this, len);
        }

        @Override
        public Object[][][][] newArray5(int len) {
            return ClassManifestDeprecatedApis.newArray5$(this, len);
        }

        @Override
        public ArraySeq<T> newWrappedArray(int len) {
            return ClassManifestDeprecatedApis.newWrappedArray$(this, len);
        }

        @Override
        public ArrayBuilder<T> newArrayBuilder() {
            return ClassManifestDeprecatedApis.newArrayBuilder$(this);
        }

        @Override
        public List<OptManifest<?>> typeArguments() {
            return ClassManifestDeprecatedApis.typeArguments$(this);
        }

        @Override
        public String argString() {
            return ClassManifestDeprecatedApis.argString$(this);
        }

        @Override
        public Class<?> runtimeClass() {
            return this.runtimeClass;
        }

        @Override
        public Object newArray(int len) {
            return Array.newInstance(this.runtimeClass(), len);
        }

        public GenericClassTag(Class<?> runtimeClass) {
            this.runtimeClass = runtimeClass;
        }
    }
}

