/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.javadsl;

import akka.NotUsed;
import akka.annotation.ApiMayChange;
import akka.event.LogMarker;
import akka.event.LoggingAdapter;
import akka.event.MarkerLoggingAdapter;
import akka.japi.Pair;
import akka.japi.Util$;
import akka.japi.function.Function;
import akka.japi.function.Function2;
import akka.japi.function.Predicate;
import akka.stream.Attributes;
import akka.stream.FlowShape;
import akka.stream.Graph;
import akka.stream.GraphDelegate;
import akka.stream.ThrottleMode;
import akka.stream.javadsl.Flow;
import akka.stream.javadsl.FlowWithContext$;
import akka.stream.scaladsl.Flow$;
import akka.stream.scaladsl.Keep$;
import akka.util.ConstantFun$;
import akka.util.JavaDurationConverters$;
import akka.util.JavaDurationConverters$JavaDurationOps$;
import akka.util.ccompat.package$JavaConverters$;
import java.io.Serializable;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.function.BiFunction;
import scala.Function1;
import scala.MatchError;
import scala.PartialFunction;
import scala.Predef$;
import scala.Tuple2;
import scala.collection.immutable.Seq;
import scala.compat.java8.FutureConverters$;
import scala.compat.java8.FutureConverters$CompletionStageOps$;
import scala.concurrent.Future;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;

@ScalaSignature(bytes="\u0006\u0005\r-v!B\u0014)\u0011\u0003yc!B\u0019)\u0011\u0003\u0011\u0004\"B\u001d\u0002\t\u0003Q\u0004\"B\u001e\u0002\t\u0003a\u0004bBBC\u0003\u0011\u00051q\u0011\u0004\u0005c!\u0012q\b\u0003\u0005g\u000b\t\u0005\t\u0015!\u0003h\u0011\u0015IT\u0001\"\u0001k\u0011\u0015iW\u0001\"\u0001o\u0011\u001d\tY\"\u0002C\u0001\u0003;Aq!!\u0010\u0006\t\u0003\ny\u0004C\u0004\u0002L\u0015!\t!!\u0014\t\u000f\u0005ET\u0001\"\u0001\u0002t!9\u0011QR\u0003\u0005\u0002\u0005=\u0005bBAJ\u000b\u0011\u0005\u0011Q\u0013\u0005\b\u0003G+A\u0011AAS\u0011\u001d\t\t,\u0002C\u0001\u0003gCq!a.\u0006\t\u0003\tI\fC\u0004\u0002Z\u0016!\t!a7\t\u000f\u0005%X\u0001\"\u0001\u0002l\"9!\u0011B\u0003\u0005\u0002\t-\u0001b\u0002B\u001b\u000b\u0011\u0005!q\u0007\u0005\b\u00057*A\u0011\u0001B/\u0011\u001d\u0011i'\u0002C\u0001\u0005_B\u0011Ba\u001e\u0006#\u0003%\tA!\u001f\t\u000f\tuT\u0001\"\u0001\u0003\u0000!9!QP\u0003\u0005\u0002\t%\u0006b\u0002B?\u000b\u0011\u0005!q\u0016\u0005\b\u0005{*A\u0011\u0001B[\u0011\u001d\u0011I,\u0002C\u0001\u0005wCqA!/\u0006\t\u0003\u0011I\u000eC\u0004\u0003:\u0016!\tA!9\t\u000f\teV\u0001\"\u0001\u0003j\"9!q^\u0003\u0005\u0002\tE\bb\u0002Bx\u000b\u0011\u00051q\u0001\u0005\b\u0005_,A\u0011AB\u000e\u0011\u001d\u0011y/\u0002C\u0001\u0007_Aqaa\u000f\u0006\t\u0003\u0019i\u0004\u0003\u0005\u0004J\u0015\u0001K\u0011BB&\u0003=1En\\<XSRD7i\u001c8uKb$(BA\u0015+\u0003\u001dQ\u0017M^1eg2T!a\u000b\u0017\u0002\rM$(/Z1n\u0015\u0005i\u0013\u0001B1lW\u0006\u001c\u0001\u0001\u0005\u00021\u00035\t\u0001FA\bGY><x+\u001b;i\u0007>tG/\u001a=u'\t\t1\u0007\u0005\u00025o5\tQGC\u00017\u0003\u0015\u00198-\u00197b\u0013\tATG\u0001\u0004B]f\u0014VMZ\u0001\u0007y%t\u0017\u000e\u001e \u0015\u0003=\naa\u0019:fCR,W#B\u001f\u0004v\reD#\u0001 \u0011\u0019A*11OB<\u0007g\u001a9h! \u0016\r\u0001\u0003&LX1e'\t)\u0011\t\u0005\u0003C\u0007\u0016\u001bW\"\u0001\u0016\n\u0005\u0011S#!D$sCBDG)\u001a7fO\u0006$X\r\u0005\u0003C\r\"c\u0016BA$+\u0005%1En\\<TQ\u0006\u0004X\r\u0005\u0003J\u0019:KV\"\u0001&\u000b\u0005-c\u0013\u0001\u00026ba&L!!\u0014&\u0003\tA\u000b\u0017N\u001d\t\u0003\u001fBc\u0001\u0001B\u0003R\u000b\t\u0007!K\u0001\u0002J]F\u00111K\u0016\t\u0003iQK!!V\u001b\u0003\u000f9{G\u000f[5oOB\u0011AgV\u0005\u00031V\u00121!\u00118z!\ty%\fB\u0003\\\u000b\t\u0007!KA\u0003DibLe\u000e\u0005\u0003J\u0019v\u0003\u0007CA(_\t\u0015yVA1\u0001S\u0005\ryU\u000f\u001e\t\u0003\u001f\u0006$QAY\u0003C\u0002I\u0013aa\u0011;y\u001fV$\bCA(e\t\u0019)W\u0001\"b\u0001%\n\u0019Q*\u0019;\u0002\u0011\u0011,G.Z4bi\u0016\u0004R\u0001\r5I9\u000eL!!\u001b\u0015\u0003\t\u0019cwn\u001e\u000b\u0003W2\u0004r\u0001M\u0003O3v\u00037\rC\u0003g\u000f\u0001\u0007q-A\u0002wS\u0006,Ra\u001c:v\u0003/!\"\u0001]<\u0011\u000fA*a*W9uGB\u0011qJ\u001d\u0003\u0006g\"\u0011\rA\u0015\u0002\u0005\u001fV$(\u0007\u0005\u0002Pk\u0012)a\u000f\u0003b\u0001%\n91\t\u001e=PkR\u0014\u0004\"\u0002=\t\u0001\u0004I\u0018a\u0002<jC\u001acwn\u001e\t\u0006\u0005jd\u0018QC\u0005\u0003w*\u0012Qa\u0012:ba\"\u0004RA\u0011$~\u0003'\u0001R!\u0013'\u007f\u0003#Q#!X@,\u0005\u0005\u0005\u0001\u0003BA\u0002\u0003\u001bi!!!\u0002\u000b\t\u0005\u001d\u0011\u0011B\u0001\nk:\u001c\u0007.Z2lK\u0012T1!a\u00036\u0003)\tgN\\8uCRLwN\\\u0005\u0005\u0003\u001f\t)AA\tv]\u000eDWmY6fIZ\u000b'/[1oG\u0016T#\u0001Y@\u0011\t%c\u0015\u000f\u001e\t\u0004\u001f\u0006]AABA\r\u0011\t\u0007!K\u0001\u0003NCR\u0014\u0014!D;og\u00064W\rR1uCZK\u0017-\u0006\u0004\u0002 \u0005\u0015\u0012q\u0006\u000b\u0005\u0003C\t9\u0003\u0005\u00051\u000b9K\u00161\u00051d!\ry\u0015Q\u0005\u0003\u0006g&\u0011\rA\u0015\u0005\u0007q&\u0001\r!!\u000b\u0011\r\tS\u00181FA\u0017!\u0015\u0011eI`A\u0012!\ry\u0015q\u0006\u0003\u0007\u00033I!\u0019\u0001*)\u0007%\t\u0019\u0004\u0005\u0003\u00026\u0005eRBAA\u001c\u0015\r\tY\u0001L\u0005\u0005\u0003w\t9D\u0001\u0007Ba&l\u0015-_\"iC:<W-\u0001\bxSRD\u0017\t\u001e;sS\n,H/Z:\u0015\u0007-\f\t\u0005C\u0004\u0002D)\u0001\r!!\u0012\u0002\t\u0005$HO\u001d\t\u0004\u0005\u0006\u001d\u0013bAA%U\tQ\u0011\t\u001e;sS\n,H/Z:\u0002\u00115\f\u0007/\u0012:s_J$2a[A(\u0011\u001d\t\tf\u0003a\u0001\u0003'\n!\u0001\u001d4\u0011\u000fQ\n)&!\u0017\u0002Z%\u0019\u0011qK\u001b\u0003\u001fA\u000b'\u000f^5bY\u001a+hn\u0019;j_:\u0004B!a\u0017\u0002l9!\u0011QLA4\u001d\u0011\ty&!\u001a\u000e\u0005\u0005\u0005$bAA2]\u00051AH]8pizJ\u0011AN\u0005\u0004\u0003S*\u0014a\u00029bG.\fw-Z\u0005\u0005\u0003[\nyGA\u0005UQJ|w/\u00192mK*\u0019\u0011\u0011N\u001b\u0002)5\f\u0007/T1uKJL\u0017\r\\5{K\u00124\u0016\r\\;f+\u0011\t)(a\u001f\u0015\t\u0005]\u0014Q\u0010\t\ta\u0015q\u0015,\u00181\u0002zA\u0019q*a\u001f\u0005\r\u0005eAB1\u0001S\u0011\u001d\ty\b\u0004a\u0001\u0003\u0003\u000b\u0011A\u001a\t\b\u0003\u0007\u000bIiYA=\u001b\t\t)IC\u0002\u0002\b*\u000b\u0001BZ;oGRLwN\\\u0005\u0005\u0003\u0017\u000b)I\u0001\u0005Gk:\u001cG/[8o\u0003\u0019\t7O\u00127poR\u0011\u0011\u0011\u0013\u0016\u0003O~\fqaY8mY\u0016\u001cG/\u0006\u0003\u0002\u0018\u0006uE\u0003BAM\u0003?\u0003\u0002\u0002M\u0003O3\u0006m\u0005m\u0019\t\u0004\u001f\u0006uE!B:\u000f\u0005\u0004\u0011\u0006bBA)\u001d\u0001\u0007\u0011\u0011\u0015\t\u0007i\u0005US,a'\u0002\r\u0019LG\u000e^3s)\rY\u0017q\u0015\u0005\b\u0003S{\u0001\u0019AAV\u0003\u0005\u0001\b#BAB\u0003[k\u0016\u0002BAX\u0003\u000b\u0013\u0011\u0002\u0015:fI&\u001c\u0017\r^3\u0002\u0013\u0019LG\u000e^3s\u001d>$HcA6\u00026\"9\u0011\u0011\u0016\tA\u0002\u0005-\u0016aB4s_V\u0004X\r\u001a\u000b\u0005\u0003w\u000by\rE\u00051\u000b9K\u0016QXAgGB)\u0011qXAe}6\u0011\u0011\u0011\u0019\u0006\u0005\u0003\u0007\f)-\u0001\u0003vi&d'BAAd\u0003\u0011Q\u0017M^1\n\t\u0005-\u0017\u0011\u0019\u0002\u0005\u0019&\u001cH\u000f\u0005\u0004\u0002@\u0006%\u0017\u0011\u0003\u0005\b\u0003#\f\u0002\u0019AAj\u0003\u0005q\u0007c\u0001\u001b\u0002V&\u0019\u0011q[\u001b\u0003\u0007%sG/A\u0002nCB,B!!8\u0002dR!\u0011q\\As!!\u0001TAT-\u0002b\u0002\u001c\u0007cA(\u0002d\u0012)1O\u0005b\u0001%\"9\u0011q\u0010\nA\u0002\u0005\u001d\bcBAB\u0003\u0013k\u0016\u0011]\u0001\t[\u0006\u0004\u0018i]=oGV!\u0011Q^Az)\u0019\ty/!>\u0002zBA\u0001'\u0002(Z\u0003c\u00047\rE\u0002P\u0003g$Qa]\nC\u0002ICq!a>\u0014\u0001\u0004\t\u0019.A\u0006qCJ\fG\u000e\\3mSNl\u0007bBA@'\u0001\u0007\u00111 \t\b\u0003\u0007\u000bI)XA\u007f!\u0019\tyP!\u0002\u0002r6\u0011!\u0011\u0001\u0006\u0005\u0005\u0007\t\t-\u0001\u0006d_:\u001cWO\u001d:f]RLAAa\u0002\u0003\u0002\ty1i\\7qY\u0016$\u0018n\u001c8Ti\u0006<W-A\nnCB\f5/\u001f8d!\u0006\u0014H/\u001b;j_:,G-\u0006\u0004\u0003\u000e\tM!1\u0005\u000b\u000b\u0005\u001f\u0011)Ba\u0006\u0003\u001c\t\u001d\u0002\u0003\u0003\u0019\u0006\u001df\u0013\t\u0002Y2\u0011\u0007=\u0013\u0019\u0002B\u0003t)\t\u0007!\u000bC\u0004\u0002xR\u0001\r!a5\t\u000f\teA\u00031\u0001\u0002T\u0006a\u0001/\u001a:QCJ$\u0018\u000e^5p]\"9!Q\u0004\u000bA\u0002\t}\u0011a\u00039beRLG/[8oKJ\u0004r!a!\u0002\nv\u0013\t\u0003E\u0002P\u0005G!aA!\n\u0015\u0005\u0004\u0011&!\u0001)\t\u000f\u0005}D\u00031\u0001\u0003*AI!1\u0006B\u0018;\n\u0005\"1G\u0007\u0003\u0005[QA!a\"\u0002B&!!\u0011\u0007B\u0017\u0005)\u0011\u0015NR;oGRLwN\u001c\t\u0007\u0003\u007f\u0014)A!\u0005\u0002\u00135\f\u0007oQ8oG\u0006$X\u0003\u0002B\u001d\u0005\u007f!BAa\u000f\u0003BAA\u0001'\u0002(Z\u0005{\u00017\rE\u0002P\u0005\u007f!Qa]\u000bC\u0002ICq!a \u0016\u0001\u0004\u0011\u0019\u0005\r\u0003\u0003F\t%\u0003cBAB\u0003\u0013k&q\t\t\u0004\u001f\n%C\u0001\u0004B&\u0005\u0003\n\t\u0011!A\u0003\u0002\t5#aA0%cE\u00191Ka\u0014\u0011\r\tE#q\u000bB\u001f\u001b\t\u0011\u0019F\u0003\u0003\u0003V\u0005\u0015\u0017\u0001\u00027b]\u001eLAA!\u0017\u0003T\tA\u0011\n^3sC\ndW-\u0001\u0006nCB\u001cuN\u001c;fqR,BAa\u0018\u0003fQ!!\u0011\rB4!!\u0001TAT-^\u0005G\u001a\u0007cA(\u0003f\u0011)aO\u0006b\u0001%\"9!\u0011\u000e\fA\u0002\t-\u0014AD3yiJ\f7\r^\"p]R,\u0007\u0010\u001e\t\b\u0003\u0007\u000bI\t\u0019B2\u0003\u001d\u0019H.\u001b3j]\u001e$b!a/\u0003r\tM\u0004bBAi/\u0001\u0007\u00111\u001b\u0005\n\u0005k:\u0002\u0013!a\u0001\u0003'\fAa\u001d;fa\u0006\t2\u000f\\5eS:<G\u0005Z3gCVdG\u000f\n\u001a\u0016\u0005\tm$fAAj\u007f\u0006\u0019An\\4\u0015\u000f-\u0014\tI!&\u0003\u001c\"9!1Q\rA\u0002\t\u0015\u0015\u0001\u00028b[\u0016\u0004BAa\"\u0003\u0010:!!\u0011\u0012BF!\r\ty&N\u0005\u0004\u0005\u001b+\u0014A\u0002)sK\u0012,g-\u0003\u0003\u0003\u0012\nM%AB*ue&twMC\u0002\u0003\u000eVBqAa&\u001a\u0001\u0004\u0011I*A\u0004fqR\u0014\u0018m\u0019;\u0011\r\u0005\r\u0015\u0011R/W\u0011\u001d\u0011i(\u0007a\u0001\u0005;\u0003BAa(\u0003&6\u0011!\u0011\u0015\u0006\u0004\u0005Gc\u0013!B3wK:$\u0018\u0002\u0002BT\u0005C\u0013a\u0002T8hO&tw-\u00113baR,'\u000fF\u0003l\u0005W\u0013i\u000bC\u0004\u0003\u0004j\u0001\rA!\"\t\u000f\t]%\u00041\u0001\u0003\u001aR)1N!-\u00034\"9!1Q\u000eA\u0002\t\u0015\u0005b\u0002B?7\u0001\u0007!Q\u0014\u000b\u0004W\n]\u0006b\u0002BB9\u0001\u0007!QQ\u0001\u000eY><w+\u001b;i\u001b\u0006\u00148.\u001a:\u0015\u0013-\u0014iLa0\u0003P\nE\u0007b\u0002BB;\u0001\u0007!Q\u0011\u0005\b\u0005\u0003l\u0002\u0019\u0001Bb\u0003\u0019i\u0017M]6feBA\u00111\u0011Bc;\u0002\u0014I-\u0003\u0003\u0003H\u0006\u0015%!\u0003$v]\u000e$\u0018n\u001c83!\u0011\u0011yJa3\n\t\t5'\u0011\u0015\u0002\n\u0019><W*\u0019:lKJDqAa&\u001e\u0001\u0004\u0011I\nC\u0004\u0003~u\u0001\rAa5\u0011\t\t}%Q[\u0005\u0005\u0005/\u0014\tK\u0001\u000bNCJ\\WM\u001d'pO\u001eLgnZ!eCB$XM\u001d\u000b\bW\nm'Q\u001cBp\u0011\u001d\u0011\u0019I\ba\u0001\u0005\u000bCqA!1\u001f\u0001\u0004\u0011\u0019\rC\u0004\u0003\u0018z\u0001\rA!'\u0015\u000f-\u0014\u0019O!:\u0003h\"9!1Q\u0010A\u0002\t\u0015\u0005b\u0002Ba?\u0001\u0007!1\u0019\u0005\b\u0005{z\u0002\u0019\u0001Bj)\u0015Y'1\u001eBw\u0011\u001d\u0011\u0019\t\ta\u0001\u0005\u000bCqA!1!\u0001\u0004\u0011\u0019-\u0001\u0005uQJ|G\u000f\u001e7f)\u0015Y'1\u001fB|\u0011\u001d\u0011)0\ta\u0001\u0003'\f\u0001\"\u001a7f[\u0016tGo\u001d\u0005\b\u0005s\f\u0003\u0019\u0001B~\u0003\r\u0001XM\u001d\t\u0005\u0005{\u001c\u0019!\u0004\u0002\u0003\u0000*!1\u0011AAc\u0003\u0011!\u0018.\\3\n\t\r\u0015!q \u0002\t\tV\u0014\u0018\r^5p]RI1n!\u0003\u0004\f\r51\u0011\u0003\u0005\b\u0005k\u0014\u0003\u0019AAj\u0011\u001d\u0011IP\ta\u0001\u0005wDqaa\u0004#\u0001\u0004\t\u0019.\u0001\u0007nCbLW.^7CkJ\u001cH\u000fC\u0004\u0004\u0014\t\u0002\ra!\u0006\u0002\t5|G-\u001a\t\u0004\u0005\u000e]\u0011bAB\rU\taA\u000b\u001b:piRdW-T8eKR91n!\b\u0004\"\r\r\u0002bBB\u0010G\u0001\u0007\u00111[\u0001\u0005G>\u001cH\u000fC\u0004\u0003z\u000e\u0002\rAa?\t\u000f\r\u00152\u00051\u0001\u0004(\u0005y1m\\:u\u0007\u0006d7-\u001e7bi&|g\u000eE\u0004\u0002\u0004\u0006%Ul!\u000b\u0011\t\tE31F\u0005\u0005\u0007[\u0011\u0019FA\u0004J]R,w-\u001a:\u0015\u0017-\u001c\tda\r\u00046\r]2\u0011\b\u0005\b\u0007?!\u0003\u0019AAj\u0011\u001d\u0011I\u0010\na\u0001\u0005wDqaa\u0004%\u0001\u0004\t\u0019\u000eC\u0004\u0004&\u0011\u0002\raa\n\t\u000f\rMA\u00051\u0001\u0004\u0016\u00059\u0011m]*dC2\fWCAB !%\u0019\tea\u0012O3v\u00037-\u0004\u0002\u0004D)\u00191Q\t\u0016\u0002\u0011M\u001c\u0017\r\\1eg2L1!MB\"\u0003!1\u0018.Y*dC2\fW\u0003DB'\u0007'\u001aIfa\u0018\u0004d\r\u001dD\u0003BB(\u0007S\u0002B\u0002M\u0003\u0004R\r]3QLB1\u0007K\u00022aTB*\t\u0019\u0019)F\nb\u0001%\n\u0019\u0011J\u001c\u001a\u0011\u0007=\u001bI\u0006\u0002\u0004\u0004\\\u0019\u0012\rA\u0015\u0002\u0007\u0007RD\u0018J\u001c\u001a\u0011\u0007=\u001by\u0006B\u0003tM\t\u0007!\u000bE\u0002P\u0007G\"QA\u001e\u0014C\u0002I\u00032aTB4\t\u0019\tIB\nb\u0001%\"9\u0011q\u0010\u0014A\u0002\r-\u0004c\u0002\u001b\u0004n\r}2\u0011O\u0005\u0004\u0007_*$!\u0003$v]\u000e$\u0018n\u001c82!9\u0019\tea\u0012\u0004R\r]3QLB1\u0007K\u00022aTB;\t\u0015\t6A1\u0001S!\ry5\u0011\u0010\u0003\u0007\u0007w\u001a!\u0019\u0001*\u0003\u0007\r#\b\u0010\u0005\u0003\u0004\u0000\r\u0005U\"\u0001\u0017\n\u0007\r\rEFA\u0004O_R,6/\u001a3\u0002\u0013\u0019\u0014x.\u001c)bSJ\u001cX\u0003DBE\u0007\u001f\u001b\u0019ja&\u0004\u001c\u000e}E\u0003BBF\u0007C\u0003B\u0002M\u0003\u0004\u000e\u000eE5QSBM\u0007;\u00032aTBH\t\u0015\tFA1\u0001S!\ry51\u0013\u0003\u00067\u0012\u0011\rA\u0015\t\u0004\u001f\u000e]E!B0\u0005\u0005\u0004\u0011\u0006cA(\u0004\u001c\u0012)!\r\u0002b\u0001%B\u0019qja(\u0005\u000b\u0015$!\u0019\u0001*\t\u000f\r\rF\u00011\u0001\u0004&\u0006)QO\u001c3feBA\u0001\u0007[BT\u0007S\u001bi\n\u0005\u0004J\u0019\u000e55\u0011\u0013\t\u0007\u00132\u001b)j!'")
public final class FlowWithContext<In, CtxIn, Out, CtxOut, Mat>
extends GraphDelegate<FlowShape<Pair<In, CtxIn>, Pair<Out, CtxOut>>, Mat> {
    private final Flow<Pair<In, CtxIn>, Pair<Out, CtxOut>, Mat> delegate;

    public static <In, CtxIn, Out, CtxOut, Mat> FlowWithContext<In, CtxIn, Out, CtxOut, Mat> fromPairs(Flow<Pair<In, CtxIn>, Pair<Out, CtxOut>, Mat> under) {
        return FlowWithContext$.MODULE$.fromPairs(under);
    }

    public static <In, Ctx> FlowWithContext<In, Ctx, In, Ctx, NotUsed> create() {
        return FlowWithContext$.MODULE$.create();
    }

    public <Out2, CtxOut2, Mat2> FlowWithContext<In, CtxIn, Out2, CtxOut2, Mat> via(Graph<FlowShape<Pair<Out, CtxOut>, Pair<Out2, CtxOut2>>, Mat2> viaFlow) {
        Flow<Pair<In, CtxIn>, Pair<Out2, CtxOut2>, Mat> under = this.asFlow().via(viaFlow);
        return FlowWithContext$.MODULE$.fromPairs(under);
    }

    @ApiMayChange
    public <Out2, Mat2> FlowWithContext<In, CtxIn, Out2, CtxOut, Mat> unsafeDataVia(Graph<FlowShape<Out, Out2>, Mat2> viaFlow) {
        return this.viaScala((Function1<akka.stream.scaladsl.FlowWithContext, akka.stream.scaladsl.FlowWithContext> & Serializable)x$1 -> x$1.unsafeDataVia(viaFlow));
    }

    public FlowWithContext<In, CtxIn, Out, CtxOut, Mat> withAttributes(Attributes attr) {
        return this.viaScala((Function1<akka.stream.scaladsl.FlowWithContext, akka.stream.scaladsl.FlowWithContext> & Serializable)x$2 -> x$2.withAttributes(attr));
    }

    public FlowWithContext<In, CtxIn, Out, CtxOut, Mat> mapError(PartialFunction<Throwable, Throwable> pf) {
        return this.viaScala((Function1<akka.stream.scaladsl.FlowWithContext, akka.stream.scaladsl.FlowWithContext> & Serializable)x$3 -> (akka.stream.scaladsl.FlowWithContext)x$3.mapError(pf));
    }

    public <Mat2> FlowWithContext<In, CtxIn, Out, CtxOut, Mat2> mapMaterializedValue(Function<Mat, Mat2> f) {
        return new FlowWithContext<In, CtxIn, Out, CtxOut, Mat2>(this.delegate.mapMaterializedValue(f));
    }

    public Flow<Pair<In, CtxIn>, Pair<Out, CtxOut>, Mat> asFlow() {
        return this.delegate;
    }

    public <Out2> FlowWithContext<In, CtxIn, Out2, CtxOut, Mat> collect(PartialFunction<Out, Out2> pf) {
        return this.viaScala((Function1<akka.stream.scaladsl.FlowWithContext, akka.stream.scaladsl.FlowWithContext> & Serializable)x$4 -> (akka.stream.scaladsl.FlowWithContext)x$4.collect(pf));
    }

    public FlowWithContext<In, CtxIn, Out, CtxOut, Mat> filter(Predicate<Out> p) {
        return this.viaScala((Function1<akka.stream.scaladsl.FlowWithContext, akka.stream.scaladsl.FlowWithContext> & Serializable)x$5 -> (akka.stream.scaladsl.FlowWithContext)x$5.filter((Function1<Object, Object> & Serializable)(Out param2) -> BoxesRunTime.boxToBoolean(p.test(param2))));
    }

    public FlowWithContext<In, CtxIn, Out, CtxOut, Mat> filterNot(Predicate<Out> p) {
        return this.viaScala((Function1<akka.stream.scaladsl.FlowWithContext, akka.stream.scaladsl.FlowWithContext> & Serializable)x$6 -> (akka.stream.scaladsl.FlowWithContext)x$6.filterNot((Function1<Object, Object> & Serializable)(Out param2) -> BoxesRunTime.boxToBoolean(p.test(param2))));
    }

    public FlowWithContext<In, CtxIn, List<Out>, List<CtxOut>, Mat> grouped(int n) {
        return this.viaScala((Function1<akka.stream.scaladsl.FlowWithContext, akka.stream.scaladsl.FlowWithContext> & Serializable)x$7 -> (akka.stream.scaladsl.FlowWithContext)x$7.grouped(n).map((Function1<Seq, List> & Serializable)(Out x$8) -> package$JavaConverters$.MODULE$.SeqHasAsJava(x$8).asJava()).mapContext((Function1<Seq, List> & Serializable)(Ctx x$9) -> package$JavaConverters$.MODULE$.SeqHasAsJava(x$9).asJava()));
    }

    public <Out2> FlowWithContext<In, CtxIn, Out2, CtxOut, Mat> map(Function<Out, Out2> f) {
        return this.viaScala((Function1<akka.stream.scaladsl.FlowWithContext, akka.stream.scaladsl.FlowWithContext> & Serializable)x$10 -> (akka.stream.scaladsl.FlowWithContext)x$10.map((Function1<Object, Object> & Serializable)(Out param2) -> f.apply(param2)));
    }

    public <Out2> FlowWithContext<In, CtxIn, Out2, CtxOut, Mat> mapAsync(int parallelism, Function<Out, CompletionStage<Out2>> f) {
        return this.viaScala((Function1<akka.stream.scaladsl.FlowWithContext, akka.stream.scaladsl.FlowWithContext> & Serializable)x$11 -> (akka.stream.scaladsl.FlowWithContext)x$11.mapAsync(parallelism, (Function1<Object, Future> & Serializable)(Out o) -> FutureConverters$CompletionStageOps$.MODULE$.toScala$extension(FutureConverters$.MODULE$.CompletionStageOps((CompletionStage)f.apply(o)))));
    }

    public <Out2, P> FlowWithContext<In, CtxIn, Out2, CtxOut, Mat> mapAsyncPartitioned(int parallelism, int perPartition, Function<Out, P> partitioner, BiFunction<Out, P, CompletionStage<Out2>> f) {
        return this.viaScala((Function1<akka.stream.scaladsl.FlowWithContext, akka.stream.scaladsl.FlowWithContext> & Serializable)x$12 -> (akka.stream.scaladsl.FlowWithContext)x$12.mapAsyncPartitioned(parallelism, perPartition, (Function1<Object, Object> & Serializable)(Out x) -> partitioner.apply(x), (scala.Function2<Object, Object, Future> & Serializable)(Out x, P p) -> FutureConverters$CompletionStageOps$.MODULE$.toScala$extension(FutureConverters$.MODULE$.CompletionStageOps((CompletionStage)f.apply(x, p)))));
    }

    public <Out2> FlowWithContext<In, CtxIn, Out2, CtxOut, Mat> mapConcat(Function<Out, ? extends Iterable<Out2>> f) {
        return this.viaScala((Function1<akka.stream.scaladsl.FlowWithContext, akka.stream.scaladsl.FlowWithContext> & Serializable)x$13 -> (akka.stream.scaladsl.FlowWithContext)x$13.mapConcat((Function1<Object, Seq> & Serializable)(Out elem) -> Util$.MODULE$.immutableSeq((Iterable)f.apply(elem))));
    }

    public <CtxOut2> FlowWithContext<In, CtxIn, Out, CtxOut2, Mat> mapContext(Function<CtxOut, CtxOut2> extractContext) {
        return this.viaScala((Function1<akka.stream.scaladsl.FlowWithContext, akka.stream.scaladsl.FlowWithContext> & Serializable)x$14 -> (akka.stream.scaladsl.FlowWithContext)x$14.mapContext((Function1<Object, Object> & Serializable)(CtxOut param2) -> extractContext.apply(param2)));
    }

    public FlowWithContext<In, CtxIn, List<Out>, List<CtxOut>, Mat> sliding(int n, int step) {
        return this.viaScala((Function1<akka.stream.scaladsl.FlowWithContext, akka.stream.scaladsl.FlowWithContext> & Serializable)x$15 -> (akka.stream.scaladsl.FlowWithContext)x$15.sliding(n, step).map((Function1<Seq, List> & Serializable)(Out x$16) -> package$JavaConverters$.MODULE$.SeqHasAsJava(x$16).asJava()).mapContext((Function1<Seq, List> & Serializable)(Ctx x$17) -> package$JavaConverters$.MODULE$.SeqHasAsJava(x$17).asJava()));
    }

    public int sliding$default$2() {
        return 1;
    }

    public FlowWithContext<In, CtxIn, Out, CtxOut, Mat> log(String name, Function<Out, Object> extract, LoggingAdapter log) {
        return this.viaScala((Function1<akka.stream.scaladsl.FlowWithContext, akka.stream.scaladsl.FlowWithContext> & Serializable)x$18 -> (akka.stream.scaladsl.FlowWithContext)x$18.log(name, (Function1<Object, Object> & Serializable)(Out e) -> extract.apply(e), log));
    }

    public FlowWithContext<In, CtxIn, Out, CtxOut, Mat> log(String name, Function<Out, Object> extract) {
        return this.log(name, extract, null);
    }

    public FlowWithContext<In, CtxIn, Out, CtxOut, Mat> log(String name, LoggingAdapter log) {
        return this.log(name, ConstantFun$.MODULE$.javaIdentityFunction(), log);
    }

    public FlowWithContext<In, CtxIn, Out, CtxOut, Mat> log(String name) {
        return this.log(name, ConstantFun$.MODULE$.javaIdentityFunction(), null);
    }

    public FlowWithContext<In, CtxIn, Out, CtxOut, Mat> logWithMarker(String name, Function2<Out, CtxOut, LogMarker> marker, Function<Out, Object> extract, MarkerLoggingAdapter log) {
        return this.viaScala((Function1<akka.stream.scaladsl.FlowWithContext, akka.stream.scaladsl.FlowWithContext> & Serializable)x$19 -> (akka.stream.scaladsl.FlowWithContext)x$19.logWithMarker(name, (scala.Function2<Object, Object, LogMarker> & Serializable)(Out e, CtxOut c) -> (LogMarker)marker.apply(e, c), (Function1<Object, Object> & Serializable)(Out e) -> extract.apply(e), log));
    }

    public FlowWithContext<In, CtxIn, Out, CtxOut, Mat> logWithMarker(String name, Function2<Out, CtxOut, LogMarker> marker, Function<Out, Object> extract) {
        return this.logWithMarker(name, marker, extract, null);
    }

    public FlowWithContext<In, CtxIn, Out, CtxOut, Mat> logWithMarker(String name, Function2<Out, CtxOut, LogMarker> marker, MarkerLoggingAdapter log) {
        return this.logWithMarker(name, marker, ConstantFun$.MODULE$.javaIdentityFunction(), log);
    }

    public FlowWithContext<In, CtxIn, Out, CtxOut, Mat> logWithMarker(String name, Function2<Out, CtxOut, LogMarker> marker) {
        return this.logWithMarker(name, marker, ConstantFun$.MODULE$.javaIdentityFunction(), null);
    }

    public FlowWithContext<In, CtxIn, Out, CtxOut, Mat> throttle(int elements, Duration per) {
        return this.viaScala((Function1<akka.stream.scaladsl.FlowWithContext, akka.stream.scaladsl.FlowWithContext> & Serializable)x$20 -> (akka.stream.scaladsl.FlowWithContext)x$20.throttle(elements, JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(per))));
    }

    public FlowWithContext<In, CtxIn, Out, CtxOut, Mat> throttle(int elements, Duration per, int maximumBurst, ThrottleMode mode) {
        return this.viaScala((Function1<akka.stream.scaladsl.FlowWithContext, akka.stream.scaladsl.FlowWithContext> & Serializable)x$21 -> (akka.stream.scaladsl.FlowWithContext)x$21.throttle(elements, JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(per)), maximumBurst, mode));
    }

    public FlowWithContext<In, CtxIn, Out, CtxOut, Mat> throttle(int cost, Duration per, Function<Out, Integer> costCalculation) {
        return this.viaScala((Function1<akka.stream.scaladsl.FlowWithContext, akka.stream.scaladsl.FlowWithContext> & Serializable)x$22 -> (akka.stream.scaladsl.FlowWithContext)x$22.throttle(cost, JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(per)), (Function1<Object, Object> & Serializable)(Out param2) -> BoxesRunTime.boxToInteger(Predef$.MODULE$.Integer2int((Integer)costCalculation.apply(param2)))));
    }

    public FlowWithContext<In, CtxIn, Out, CtxOut, Mat> throttle(int cost, Duration per, int maximumBurst, Function<Out, Integer> costCalculation, ThrottleMode mode) {
        return this.viaScala((Function1<akka.stream.scaladsl.FlowWithContext, akka.stream.scaladsl.FlowWithContext> & Serializable)x$23 -> (akka.stream.scaladsl.FlowWithContext)x$23.throttle(cost, JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(per)), maximumBurst, (Function1<Object, Object> & Serializable)(Out param2) -> BoxesRunTime.boxToInteger(Predef$.MODULE$.Integer2int((Integer)costCalculation.apply(param2))), mode));
    }

    public akka.stream.scaladsl.FlowWithContext<In, CtxIn, Out, CtxOut, Mat> asScala() {
        return akka.stream.scaladsl.FlowWithContext$.MODULE$.fromTuples(((akka.stream.scaladsl.Flow)Flow$.MODULE$.apply().map((Function1<Tuple2, Pair> & Serializable)(Out x0$1) -> {
            Tuple2 tuple2 = x0$1;
            if (tuple2 != null) {
                Object i = tuple2._1();
                Object c = tuple2._2();
                return new Pair(i, c);
            }
            throw new MatchError(tuple2);
        })).viaMat((Graph)((Object)this.delegate.asScala().map((Function1<Pair, Tuple2> & Serializable)(Out x$24) -> x$24.toScala())), Keep$.MODULE$.right()));
    }

    private <In2, CtxIn2, Out2, CtxOut2, Mat2> FlowWithContext<In2, CtxIn2, Out2, CtxOut2, Mat2> viaScala(Function1<akka.stream.scaladsl.FlowWithContext<In, CtxIn, Out, CtxOut, Mat>, akka.stream.scaladsl.FlowWithContext<In2, CtxIn2, Out2, CtxOut2, Mat2>> f) {
        return f.apply(this.asScala()).asJava();
    }

    public FlowWithContext(Flow<Pair<In, CtxIn>, Pair<Out, CtxOut>, Mat> delegate) {
        this.delegate = delegate;
        super(delegate);
    }
}

