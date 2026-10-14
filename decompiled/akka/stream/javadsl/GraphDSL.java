/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.javadsl;

import akka.NotUsed;
import akka.japi.function.Function;
import akka.japi.function.Function10;
import akka.japi.function.Function11;
import akka.japi.function.Function12;
import akka.japi.function.Function13;
import akka.japi.function.Function14;
import akka.japi.function.Function15;
import akka.japi.function.Function16;
import akka.japi.function.Function17;
import akka.japi.function.Function18;
import akka.japi.function.Function19;
import akka.japi.function.Function2;
import akka.japi.function.Function20;
import akka.japi.function.Function21;
import akka.japi.function.Function22;
import akka.japi.function.Function3;
import akka.japi.function.Function4;
import akka.japi.function.Function5;
import akka.japi.function.Function6;
import akka.japi.function.Function7;
import akka.japi.function.Function8;
import akka.japi.function.Function9;
import akka.stream.FlowShape;
import akka.stream.Graph;
import akka.stream.Inlet;
import akka.stream.Outlet;
import akka.stream.Shape;
import akka.stream.SinkShape;
import akka.stream.SourceShape;
import akka.stream.UniformFanInShape;
import akka.stream.UniformFanOutShape;
import akka.stream.javadsl.GraphDSL$;
import akka.stream.scaladsl.GraphDSL;
import akka.stream.scaladsl.GraphDSL$Implicits$;
import java.util.List;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\r\u0015u!\u0002\u0016,\u0011\u0003\u0011d!\u0002\u001b,\u0011\u0003)\u0004\"B\u001d\u0002\t\u0003Q\u0004\"B\u001e\u0002\t\u0003a\u0004bBB\u001b\u0003\u0011\u00051q\u0007\u0004\u0005\u0001\u0006\u0011\u0011\tC\u0005J\u000b\t\u0015\r\u0011b\u0001.\u0015\"A\u0001-\u0002B\u0001B\u0003%1\nC\u0003:\u000b\u0011\u0005\u0011\rC\u0003f\u000b\u0011\u0005a\rC\u0003y\u000b\u0011\u0005\u0011\u0010C\u0004\u0002\u0010\u0015!\t!!\u0005\t\u000f\u0005=Q\u0001\"\u0001\u0002p\"9\u0011qB\u0003\u0005\u0002\t\r\u0001bBA\b\u000b\u0011\u0005!\u0011\u0004\u0005\b\u0003\u001f)A\u0011\u0001B\u0016\u0011\u001d\t9%\u0002C\u0001\u0005{Aq!a\u0012\u0006\t\u0003\u0011Y\u000fC\u0004\u0002H\u0015!\tA!?\t\u000f\u0005\u001dS\u0001\"\u0001\u0004\f!9\u0011qI\u0003\u0005\u0002\ruaABA\r\u000b\t\tY\u0002\u0003\u0006\u0002 U\u0011\t\u0011)A\u0005\u0003CAa!O\u000b\u0005\u0002\u0005%\u0002bBA\u0018+\u0011\u0005\u0011\u0011\u0007\u0005\b\u0003\u000f*B\u0011AA%\u0011\u001d\ti&\u0006C\u0001\u0003?Bq!a\u001f\u0016\t\u0003\ti\bC\u0004\u0002\u0016V!\t!a&\t\u000f\u0005MV\u0003\"\u0001\u00026\"9\u00111Z\u000b\u0005\u0002\u00055\u0007bBAr+\u0011\u0005\u0011Q\u001d\u0004\u0007\u0005\u0007*!A!\u0012\t\u0015\u0005\r\bE!A!\u0002\u0013\u0011I\u0005\u0003\u0004:A\u0011\u0005!q\n\u0005\b\u0005+\u0002C\u0011\u0001B,\u0011\u001d\ty\u0001\tC\u0001\u0005OBqA!\u001e!\t\u0003\u00119\bC\u0004\u0003\f\u0002\"\tA!$\t\u000f\u0005U\u0005\u0005\"\u0001\u0003\"\"9\u00111\u0017\u0011\u0005\u0002\t]\u0006bBAfA\u0011\u0005!QZ\u0001\t\u000fJ\f\u0007\u000f\u001b#T\u0019*\u0011A&L\u0001\bU\u00064\u0018\rZ:m\u0015\tqs&\u0001\u0004tiJ,\u0017-\u001c\u0006\u0002a\u0005!\u0011m[6b\u0007\u0001\u0001\"aM\u0001\u000e\u0003-\u0012\u0001b\u0012:ba\"$5\u000bT\n\u0003\u0003Y\u0002\"aM\u001c\n\u0005aZ#aC$sCBD7I]3bi\u0016\fa\u0001P5oSRtD#\u0001\u001a\u0002\u000f\t,\u0018\u000e\u001c3feV\u0019Qh!\r\u0015\u0003y\u0002BaP\u0003\u000405\t\u0011AA\u0004Ck&dG-\u001a:\u0016\u0005\t;6CA\u0003D!\t!u)D\u0001F\u0015\u00051\u0015!B:dC2\f\u0017B\u0001%F\u0005\u0019\te.\u001f*fM\u0006AA-\u001a7fO\u0006$X-F\u0001L!\ra5+\u0016\b\u0003\u001bFs!AT(\u000e\u00035J!\u0001U\u0017\u0002\u0011M\u001c\u0017\r\\1eg2L!A\u000b*\u000b\u0005Ak\u0013B\u0001!U\u0015\tQ#\u000b\u0005\u0002W/2\u0001AA\u0002-\u0006\t\u000b\u0007\u0011LA\u0002NCR\f\"AW/\u0011\u0005\u0011[\u0016B\u0001/F\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"\u0001\u00120\n\u0005}+%aA!os\u0006IA-\u001a7fO\u0006$X\r\t\u000b\u0002ER\u00111\r\u001a\t\u0004\u007f\u0015)\u0006\"B%\t\u0001\bY\u0015aA1eIV\u0011q-\u001b\u000b\u0003Q>\u0004\"AV5\u0005\u000b)L!\u0019A6\u0003\u0003M\u000b\"A\u00177\u0011\u00059k\u0017B\u00018.\u0005\u0015\u0019\u0006.\u00199f\u0011\u0015\u0001\u0018\u00021\u0001r\u0003\u00159'/\u00199ia\t\u0011h\u000f\u0005\u0003Og\",\u0018B\u0001;.\u0005\u00159%/\u00199i!\t1f\u000fB\u0005x_\u0006\u0005\t\u0011!B\u00013\n\u0019q\fJ\u0019\u0002#5\fG/\u001a:jC2L'0\u001a3WC2,X-F\u0001{!\rq50`\u0005\u0003y6\u0012aaT;uY\u0016$(FA+\u007fW\u0005y\b\u0003BA\u0001\u0003\u0017i!!a\u0001\u000b\t\u0005\u0015\u0011qA\u0001\nk:\u001c\u0007.Z2lK\u0012T1!!\u0003F\u0003)\tgN\\8uCRLwN\\\u0005\u0005\u0003\u001b\t\u0019AA\tv]\u000eDWmY6fIZ\u000b'/[1oG\u0016\fAA\u001a:p[V!\u00111CAu)\u0011\t)\"a;\u0011\u000b\u0005]Q#a:\u000e\u0003\u0015\u0011!BR8so\u0006\u0014Hm\u00149t+\u0011\ti\"!\n\u0014\u0005U\u0019\u0015\u0001B0pkR\u0004BAT>\u0002$A\u0019a+!\n\u0005\r\u0005\u001dRC1\u0001Z\u0005\u0005!F\u0003BA\u0016\u0003[\u0001R!a\u0006\u0016\u0003GAq!a\b\u0018\u0001\u0004\t\t#A\u0004u_&sG.\u001a;\u0015\u0007\r\f\u0019\u0004C\u0004\u00026a\u0001\r!a\u000e\u0002\u0005%t\u0007\u0007BA\u001d\u0003\u0003\u0002RATA\u001e\u0003\u007fI1!!\u0010.\u0005\u0015Ie\u000e\\3u!\r1\u0016\u0011\t\u0003\r\u0003\u0007\n\u0019$!A\u0001\u0002\u000b\u0005\u0011Q\t\u0002\u0004?\u0012\u0012\u0014cAA\u0012;\u0006\u0011Ao\u001c\u000b\u0004G\u0006-\u0003bBA'3\u0001\u0007\u0011qJ\u0001\u0004IN$\b\u0007BA)\u00033\u0002RATA*\u0003/J1!!\u0016.\u0005%\u0019\u0016N\\6TQ\u0006\u0004X\rE\u0002W\u00033\"A\"a\u0017\u0002L\u0005\u0005\t\u0011!B\u0001\u0003\u000b\u00121a\u0018\u00134\u0003\u001d!xNR1o\u0013:,B!!\u0019\u0002xQ\u00191-a\u0019\t\u000f\u0005\u0015$\u00041\u0001\u0002h\u0005\t!\u000e\r\u0003\u0002j\u0005E\u0004c\u0002(\u0002l\u0005=\u0014QO\u0005\u0004\u0003[j#!E+oS\u001a|'/\u001c$b]&s7\u000b[1qKB\u0019a+!\u001d\u0005\u0019\u0005M\u00141MA\u0001\u0002\u0003\u0015\t!!\u0012\u0003\u0007}#C\u0007E\u0002W\u0003o\"a!!\u001f\u001b\u0005\u0004I&!A+\u0002\u0011Q|g)\u00198PkR,B!a \u0002\u0014R\u00191-!!\t\u000f\u0005\u00154\u00041\u0001\u0002\u0004B\"\u0011QQAG!\u001dq\u0015qQAF\u0003#K1!!#.\u0005I)f.\u001b4pe64\u0015M\\(viNC\u0017\r]3\u0011\u0007Y\u000bi\t\u0002\u0007\u0002\u0010\u0006\u0005\u0015\u0011!A\u0001\u0006\u0003\t)EA\u0002`IU\u00022AVAJ\t\u0019\tIh\u0007b\u00013\u0006\u0019a/[1\u0016\t\u0005e\u0015q\u0014\u000b\u0005\u00037\u000b\t\u000bE\u0003\u0002\u0018U\ti\nE\u0002W\u0003?#a!!\u001f\u001d\u0005\u0004I\u0006bBAR9\u0001\u0007\u0011QU\u0001\u0002MB\"\u0011qUAX!\u001dq\u0015\u0011VAW\u0003;K1!a+.\u0005%1En\\<TQ\u0006\u0004X\rE\u0002W\u0003_#A\"!-\u0002\"\u0006\u0005\t\u0011!B\u0001\u0003\u000b\u00121a\u0018\u00137\u0003!1\u0018.\u0019$b]&sW\u0003BA\\\u0003{#B!!/\u0002@B)\u0011qC\u000b\u0002<B\u0019a+!0\u0005\r\u0005eTD1\u0001Z\u0011\u001d\t)'\ba\u0001\u0003\u0003\u0004D!a1\u0002HB9a*a\u001b\u0002F\u0006m\u0006c\u0001,\u0002H\u0012a\u0011\u0011ZA`\u0003\u0003\u0005\tQ!\u0001\u0002F\t\u0019q\fJ\u001c\u0002\u0013YL\u0017MR1o\u001fV$X\u0003BAh\u0003+$B!!5\u0002XB)\u0011qC\u000b\u0002TB\u0019a+!6\u0005\r\u0005edD1\u0001Z\u0011\u001d\t)G\ba\u0001\u00033\u0004D!a7\u0002`B9a*a\"\u0002^\u0006M\u0007c\u0001,\u0002`\u0012a\u0011\u0011]Al\u0003\u0003\u0005\tQ!\u0001\u0002F\t\u0019q\f\n\u001d\u0002\u0007=,H\u000f\u0006\u0002\u0002\"A\u0019a+!;\u0005\r\u0005\u001d2B1\u0001Z\u0011\u001d\t\u0019o\u0003a\u0001\u0003[\u0004BAT>\u0002hV!\u0011\u0011_A|)\u0011\t\u00190!?\u0011\u000b\u0005]Q#!>\u0011\u0007Y\u000b9\u0010\u0002\u0004\u0002(1\u0011\r!\u0017\u0005\b\u0003wd\u0001\u0019AA\u007f\u0003\r\u0019(o\u0019\t\u0006\u001d\u0006}\u0018Q_\u0005\u0004\u0005\u0003i#aC*pkJ\u001cWm\u00155ba\u0016,bA!\u0002\u0003\u0016\t-A\u0003\u0002B\u0004\u0005\u001f\u0001R!a\u0006\u0016\u0005\u0013\u00012A\u0016B\u0006\t\u0019\u0011i!\u0004b\u00013\n\tq\nC\u0004\u0002$6\u0001\rA!\u0005\u0011\u000f9\u000bIKa\u0005\u0003\nA\u0019aK!\u0006\u0005\r\t]QB1\u0001Z\u0005\u0005IUC\u0002B\u000e\u0005S\u0011\t\u0003\u0006\u0003\u0003\u001e\t\r\u0002#BA\f+\t}\u0001c\u0001,\u0003\"\u00111!Q\u0002\bC\u0002eCq!!\u001a\u000f\u0001\u0004\u0011)\u0003E\u0004O\u0003W\u00129Ca\b\u0011\u0007Y\u0013I\u0003\u0002\u0004\u0003\u00189\u0011\r!W\u000b\u0007\u0005[\u0011YDa\r\u0015\t\t=\"Q\u0007\t\u0006\u0003/)\"\u0011\u0007\t\u0004-\nMBA\u0002B\u0007\u001f\t\u0007\u0011\fC\u0004\u0002f=\u0001\rAa\u000e\u0011\u000f9\u000b9I!\u000f\u00032A\u0019aKa\u000f\u0005\r\t]qB1\u0001Z+\u0011\u0011yD!:\u0015\t\t\u0005#q\u001d\t\u0006\u0003/\u0001#1\u001d\u0002\u000b%\u00164XM]:f\u001fB\u001cX\u0003\u0002B$\u0005\u001b\u001a\"\u0001I\"\u0011\u000b9\u000bYDa\u0013\u0011\u0007Y\u0013i\u0005\u0002\u0004\u0002(\u0001\u0012\r!\u0017\u000b\u0005\u0005#\u0012\u0019\u0006E\u0003\u0002\u0018\u0001\u0012Y\u0005C\u0004\u0002d\n\u0002\rA!\u0013\u0002\u0015\u0019\u0014x.\\(vi2,G\u000fF\u0002d\u00053Bq!!\u0014$\u0001\u0004\u0011Y\u0006\r\u0003\u0003^\t\u0005\u0004\u0003\u0002(|\u0005?\u00022A\u0016B1\t1\u0011\u0019G!\u0017\u0002\u0002\u0003\u0005)\u0011\u0001B3\u0005\ryF%O\t\u00045\n-CcA2\u0003j!9\u0011Q\n\u0013A\u0002\t-\u0004\u0007\u0002B7\u0005c\u0002RATA\u0000\u0005_\u00022A\u0016B9\t1\u0011\u0019H!\u001b\u0002\u0002\u0003\u0005)\u0011\u0001B3\u0005\u0011yF%\r\u0019\u0002\u0013\u0019\u0014x.\u001c$b]&sW\u0003\u0002B=\u0005\u0007#2a\u0019B>\u0011\u001d\t)'\na\u0001\u0005{\u0002DAa \u0003\bB9a*a\u001b\u0003\u0002\n\u0015\u0005c\u0001,\u0003\u0004\u00121\u0011\u0011P\u0013C\u0002e\u00032A\u0016BD\t1\u0011IIa\u001f\u0002\u0002\u0003\u0005)\u0011\u0001B3\u0005\u0011yF%M\u0019\u0002\u0015\u0019\u0014x.\u001c$b]>+H/\u0006\u0003\u0003\u0010\neEcA2\u0003\u0012\"9\u0011Q\r\u0014A\u0002\tM\u0005\u0007\u0002BK\u0005;\u0003rATAD\u0005/\u0013Y\nE\u0002W\u00053#a!!\u001f'\u0005\u0004I\u0006c\u0001,\u0003\u001e\u0012a!q\u0014BI\u0003\u0003\u0005\tQ!\u0001\u0003f\t!q\fJ\u00193+\u0011\u0011\u0019K!+\u0015\t\t\u0015&1\u0016\t\u0006\u0003/\u0001#q\u0015\t\u0004-\n%FABA=O\t\u0007\u0011\fC\u0004\u0002$\u001e\u0002\rA!,1\t\t=&1\u0017\t\b\u001d\u0006%&q\u0015BY!\r1&1\u0017\u0003\r\u0005k\u0013Y+!A\u0001\u0002\u000b\u0005!Q\r\u0002\u0005?\u0012\n4'\u0006\u0003\u0003:\n}F\u0003\u0002B^\u0005\u0003\u0004R!a\u0006!\u0005{\u00032A\u0016B`\t\u0019\tI\b\u000bb\u00013\"9\u0011Q\r\u0015A\u0002\t\r\u0007\u0007\u0002Bc\u0005\u0013\u0004rATA6\u0005{\u00139\rE\u0002W\u0005\u0013$ABa3\u0003B\u0006\u0005\t\u0011!B\u0001\u0005K\u0012Aa\u0018\u00132iU!!q\u001aBk)\u0011\u0011\tNa6\u0011\u000b\u0005]\u0001Ea5\u0011\u0007Y\u0013)\u000e\u0002\u0004\u0002z%\u0012\r!\u0017\u0005\b\u0003KJ\u0003\u0019\u0001Bma\u0011\u0011YNa8\u0011\u000f9\u000b9Ia5\u0003^B\u0019aKa8\u0005\u0019\t\u0005(q[A\u0001\u0002\u0003\u0015\tA!\u001a\u0003\t}#\u0013'\u000e\t\u0004-\n\u0015HABA\u0014!\t\u0007\u0011\fC\u0004\u00026A\u0001\rA!;\u0011\u000b9\u000bYDa9\u0016\t\t5(1\u001f\u000b\u0005\u0005_\u0014)\u0010E\u0003\u0002\u0018\u0001\u0012\t\u0010E\u0002W\u0005g$a!a\n\u0012\u0005\u0004I\u0006bBA'#\u0001\u0007!q\u001f\t\u0006\u001d\u0006M#\u0011_\u000b\u0007\u0005w\u001c\ta!\u0003\u0015\t\tu81\u0001\t\u0006\u0003/\u0001#q \t\u0004-\u000e\u0005AA\u0002B\f%\t\u0007\u0011\fC\u0004\u0002$J\u0001\ra!\u0002\u0011\u000f9\u000bIKa@\u0004\bA\u0019ak!\u0003\u0005\r\t5!C1\u0001Z+\u0019\u0019iaa\u0005\u0004\u001cQ!1qBB\u000b!\u0015\t9\u0002IB\t!\r161\u0003\u0003\u0007\u0005/\u0019\"\u0019A-\t\u000f\u0005\u00154\u00031\u0001\u0004\u0018A9a*a\u001b\u0004\u0012\re\u0001c\u0001,\u0004\u001c\u00111!QB\nC\u0002e+baa\b\u0004&\r5B\u0003BB\u0011\u0007O\u0001R!a\u0006!\u0007G\u00012AVB\u0013\t\u0019\u00119\u0002\u0006b\u00013\"9\u0011Q\r\u000bA\u0002\r%\u0002c\u0002(\u0002\b\u000e\r21\u0006\t\u0004-\u000e5BA\u0002B\u0007)\t\u0007\u0011\fE\u0002W\u0007c!aaa\r\u0004\u0005\u0004I&!A'\u0002\r\r\u0014X-\u0019;f+)\u0019Ida\u001a\u0004@\rM3Q\f\u000b\u0007\u0007w\u0019)fa\u001b\u0011\r9\u001b8QHB!!\r16q\b\u0003\u0006U\u0012\u0011\ra\u001b\t\u0007\u0007\u0007\u001aie!\u0015\u000e\u0005\r\u0015#\u0002BB$\u0007\u0013\nA!\u001e;jY*\u001111J\u0001\u0005U\u00064\u0018-\u0003\u0003\u0004P\r\u0015#\u0001\u0002'jgR\u00042AVB*\t\u0019\u0019\u0019\u0004\u0002b\u00013\"91q\u000b\u0003A\u0002\re\u0013AB4sCBD7\u000f\u0005\u0004\u0004D\r531\f\t\u0004-\u000euCaBB0\t\t\u00071\u0011\r\u0002\u0002\u000fF\u0019!la\u0019\u0011\r9\u001b8QMB)!\r16q\r\u0003\u0007\u0007S\"!\u0019A6\u0003\u0005%\u001b\u0006bBB7\t\u0001\u00071qN\u0001\u000bEVLG\u000e\u001a\"m_\u000e\\\u0007CCB9\u0007w\u001ayha!\u0004>5\u001111\u000f\u0006\u0005\u0007k\u001a9(\u0001\u0005gk:\u001cG/[8o\u0015\r\u0019IhL\u0001\u0005U\u0006\u0004\u0018.\u0003\u0003\u0004~\rM$!\u0003$v]\u000e$\u0018n\u001c83!\u0015\u0019\t)BB!\u001d\t\u0019\u0004\u0001\u0005\u0004\u0004D\r53Q\r")
public final class GraphDSL {
    public static <IS extends Shape, S extends Shape, M, G extends Graph<IS, M>> Graph<S, List<M>> create(List<G> graphs, Function2<Builder<List<M>>, List<IS>, S> buildBlock) {
        return GraphDSL$.MODULE$.create(graphs, buildBlock);
    }

    public static <M> Builder<M> builder() {
        return GraphDSL$.MODULE$.builder();
    }

    public static <S1 extends Shape, S2 extends Shape, S3 extends Shape, S4 extends Shape, S5 extends Shape, S6 extends Shape, S7 extends Shape, S8 extends Shape, S9 extends Shape, S10 extends Shape, S11 extends Shape, S12 extends Shape, S13 extends Shape, S14 extends Shape, S15 extends Shape, S16 extends Shape, S17 extends Shape, S18 extends Shape, S19 extends Shape, S20 extends Shape, S21 extends Shape, S extends Shape, M1, M2, M3, M4, M5, M6, M7, M8, M9, M10, M11, M12, M13, M14, M15, M16, M17, M18, M19, M20, M21, M> Graph<S, M> create21(Graph<S1, M1> g1, Graph<S2, M2> g2, Graph<S3, M3> g3, Graph<S4, M4> g4, Graph<S5, M5> g5, Graph<S6, M6> g6, Graph<S7, M7> g7, Graph<S8, M8> g8, Graph<S9, M9> g9, Graph<S10, M10> g10, Graph<S11, M11> g11, Graph<S12, M12> g12, Graph<S13, M13> g13, Graph<S14, M14> g14, Graph<S15, M15> g15, Graph<S16, M16> g16, Graph<S17, M17> g17, Graph<S18, M18> g18, Graph<S19, M19> g19, Graph<S20, M20> g20, Graph<S21, M21> g21, Function21<M1, M2, M3, M4, M5, M6, M7, M8, M9, M10, M11, M12, M13, M14, M15, M16, M17, M18, M19, M20, M21, M> combineMat, Function22<Builder<M>, S1, S2, S3, S4, S5, S6, S7, S8, S9, S10, S11, S12, S13, S14, S15, S16, S17, S18, S19, S20, S21, S> block) {
        return GraphDSL$.MODULE$.create21(g1, g2, g3, g4, g5, g6, g7, g8, g9, g10, g11, g12, g13, g14, g15, g16, g17, g18, g19, g20, g21, combineMat, block);
    }

    public static <S1 extends Shape, S2 extends Shape, S3 extends Shape, S4 extends Shape, S5 extends Shape, S6 extends Shape, S7 extends Shape, S8 extends Shape, S9 extends Shape, S10 extends Shape, S11 extends Shape, S12 extends Shape, S13 extends Shape, S14 extends Shape, S15 extends Shape, S16 extends Shape, S17 extends Shape, S18 extends Shape, S19 extends Shape, S20 extends Shape, S extends Shape, M1, M2, M3, M4, M5, M6, M7, M8, M9, M10, M11, M12, M13, M14, M15, M16, M17, M18, M19, M20, M> Graph<S, M> create20(Graph<S1, M1> g1, Graph<S2, M2> g2, Graph<S3, M3> g3, Graph<S4, M4> g4, Graph<S5, M5> g5, Graph<S6, M6> g6, Graph<S7, M7> g7, Graph<S8, M8> g8, Graph<S9, M9> g9, Graph<S10, M10> g10, Graph<S11, M11> g11, Graph<S12, M12> g12, Graph<S13, M13> g13, Graph<S14, M14> g14, Graph<S15, M15> g15, Graph<S16, M16> g16, Graph<S17, M17> g17, Graph<S18, M18> g18, Graph<S19, M19> g19, Graph<S20, M20> g20, Function20<M1, M2, M3, M4, M5, M6, M7, M8, M9, M10, M11, M12, M13, M14, M15, M16, M17, M18, M19, M20, M> combineMat, Function21<Builder<M>, S1, S2, S3, S4, S5, S6, S7, S8, S9, S10, S11, S12, S13, S14, S15, S16, S17, S18, S19, S20, S> block) {
        return GraphDSL$.MODULE$.create20(g1, g2, g3, g4, g5, g6, g7, g8, g9, g10, g11, g12, g13, g14, g15, g16, g17, g18, g19, g20, combineMat, block);
    }

    public static <S1 extends Shape, S2 extends Shape, S3 extends Shape, S4 extends Shape, S5 extends Shape, S6 extends Shape, S7 extends Shape, S8 extends Shape, S9 extends Shape, S10 extends Shape, S11 extends Shape, S12 extends Shape, S13 extends Shape, S14 extends Shape, S15 extends Shape, S16 extends Shape, S17 extends Shape, S18 extends Shape, S19 extends Shape, S extends Shape, M1, M2, M3, M4, M5, M6, M7, M8, M9, M10, M11, M12, M13, M14, M15, M16, M17, M18, M19, M> Graph<S, M> create19(Graph<S1, M1> g1, Graph<S2, M2> g2, Graph<S3, M3> g3, Graph<S4, M4> g4, Graph<S5, M5> g5, Graph<S6, M6> g6, Graph<S7, M7> g7, Graph<S8, M8> g8, Graph<S9, M9> g9, Graph<S10, M10> g10, Graph<S11, M11> g11, Graph<S12, M12> g12, Graph<S13, M13> g13, Graph<S14, M14> g14, Graph<S15, M15> g15, Graph<S16, M16> g16, Graph<S17, M17> g17, Graph<S18, M18> g18, Graph<S19, M19> g19, Function19<M1, M2, M3, M4, M5, M6, M7, M8, M9, M10, M11, M12, M13, M14, M15, M16, M17, M18, M19, M> combineMat, Function20<Builder<M>, S1, S2, S3, S4, S5, S6, S7, S8, S9, S10, S11, S12, S13, S14, S15, S16, S17, S18, S19, S> block) {
        return GraphDSL$.MODULE$.create19(g1, g2, g3, g4, g5, g6, g7, g8, g9, g10, g11, g12, g13, g14, g15, g16, g17, g18, g19, combineMat, block);
    }

    public static <S1 extends Shape, S2 extends Shape, S3 extends Shape, S4 extends Shape, S5 extends Shape, S6 extends Shape, S7 extends Shape, S8 extends Shape, S9 extends Shape, S10 extends Shape, S11 extends Shape, S12 extends Shape, S13 extends Shape, S14 extends Shape, S15 extends Shape, S16 extends Shape, S17 extends Shape, S18 extends Shape, S extends Shape, M1, M2, M3, M4, M5, M6, M7, M8, M9, M10, M11, M12, M13, M14, M15, M16, M17, M18, M> Graph<S, M> create18(Graph<S1, M1> g1, Graph<S2, M2> g2, Graph<S3, M3> g3, Graph<S4, M4> g4, Graph<S5, M5> g5, Graph<S6, M6> g6, Graph<S7, M7> g7, Graph<S8, M8> g8, Graph<S9, M9> g9, Graph<S10, M10> g10, Graph<S11, M11> g11, Graph<S12, M12> g12, Graph<S13, M13> g13, Graph<S14, M14> g14, Graph<S15, M15> g15, Graph<S16, M16> g16, Graph<S17, M17> g17, Graph<S18, M18> g18, Function18<M1, M2, M3, M4, M5, M6, M7, M8, M9, M10, M11, M12, M13, M14, M15, M16, M17, M18, M> combineMat, Function19<Builder<M>, S1, S2, S3, S4, S5, S6, S7, S8, S9, S10, S11, S12, S13, S14, S15, S16, S17, S18, S> block) {
        return GraphDSL$.MODULE$.create18(g1, g2, g3, g4, g5, g6, g7, g8, g9, g10, g11, g12, g13, g14, g15, g16, g17, g18, combineMat, block);
    }

    public static <S1 extends Shape, S2 extends Shape, S3 extends Shape, S4 extends Shape, S5 extends Shape, S6 extends Shape, S7 extends Shape, S8 extends Shape, S9 extends Shape, S10 extends Shape, S11 extends Shape, S12 extends Shape, S13 extends Shape, S14 extends Shape, S15 extends Shape, S16 extends Shape, S17 extends Shape, S extends Shape, M1, M2, M3, M4, M5, M6, M7, M8, M9, M10, M11, M12, M13, M14, M15, M16, M17, M> Graph<S, M> create17(Graph<S1, M1> g1, Graph<S2, M2> g2, Graph<S3, M3> g3, Graph<S4, M4> g4, Graph<S5, M5> g5, Graph<S6, M6> g6, Graph<S7, M7> g7, Graph<S8, M8> g8, Graph<S9, M9> g9, Graph<S10, M10> g10, Graph<S11, M11> g11, Graph<S12, M12> g12, Graph<S13, M13> g13, Graph<S14, M14> g14, Graph<S15, M15> g15, Graph<S16, M16> g16, Graph<S17, M17> g17, Function17<M1, M2, M3, M4, M5, M6, M7, M8, M9, M10, M11, M12, M13, M14, M15, M16, M17, M> combineMat, Function18<Builder<M>, S1, S2, S3, S4, S5, S6, S7, S8, S9, S10, S11, S12, S13, S14, S15, S16, S17, S> block) {
        return GraphDSL$.MODULE$.create17(g1, g2, g3, g4, g5, g6, g7, g8, g9, g10, g11, g12, g13, g14, g15, g16, g17, combineMat, block);
    }

    public static <S1 extends Shape, S2 extends Shape, S3 extends Shape, S4 extends Shape, S5 extends Shape, S6 extends Shape, S7 extends Shape, S8 extends Shape, S9 extends Shape, S10 extends Shape, S11 extends Shape, S12 extends Shape, S13 extends Shape, S14 extends Shape, S15 extends Shape, S16 extends Shape, S extends Shape, M1, M2, M3, M4, M5, M6, M7, M8, M9, M10, M11, M12, M13, M14, M15, M16, M> Graph<S, M> create16(Graph<S1, M1> g1, Graph<S2, M2> g2, Graph<S3, M3> g3, Graph<S4, M4> g4, Graph<S5, M5> g5, Graph<S6, M6> g6, Graph<S7, M7> g7, Graph<S8, M8> g8, Graph<S9, M9> g9, Graph<S10, M10> g10, Graph<S11, M11> g11, Graph<S12, M12> g12, Graph<S13, M13> g13, Graph<S14, M14> g14, Graph<S15, M15> g15, Graph<S16, M16> g16, Function16<M1, M2, M3, M4, M5, M6, M7, M8, M9, M10, M11, M12, M13, M14, M15, M16, M> combineMat, Function17<Builder<M>, S1, S2, S3, S4, S5, S6, S7, S8, S9, S10, S11, S12, S13, S14, S15, S16, S> block) {
        return GraphDSL$.MODULE$.create16(g1, g2, g3, g4, g5, g6, g7, g8, g9, g10, g11, g12, g13, g14, g15, g16, combineMat, block);
    }

    public static <S1 extends Shape, S2 extends Shape, S3 extends Shape, S4 extends Shape, S5 extends Shape, S6 extends Shape, S7 extends Shape, S8 extends Shape, S9 extends Shape, S10 extends Shape, S11 extends Shape, S12 extends Shape, S13 extends Shape, S14 extends Shape, S15 extends Shape, S extends Shape, M1, M2, M3, M4, M5, M6, M7, M8, M9, M10, M11, M12, M13, M14, M15, M> Graph<S, M> create15(Graph<S1, M1> g1, Graph<S2, M2> g2, Graph<S3, M3> g3, Graph<S4, M4> g4, Graph<S5, M5> g5, Graph<S6, M6> g6, Graph<S7, M7> g7, Graph<S8, M8> g8, Graph<S9, M9> g9, Graph<S10, M10> g10, Graph<S11, M11> g11, Graph<S12, M12> g12, Graph<S13, M13> g13, Graph<S14, M14> g14, Graph<S15, M15> g15, Function15<M1, M2, M3, M4, M5, M6, M7, M8, M9, M10, M11, M12, M13, M14, M15, M> combineMat, Function16<Builder<M>, S1, S2, S3, S4, S5, S6, S7, S8, S9, S10, S11, S12, S13, S14, S15, S> block) {
        return GraphDSL$.MODULE$.create15(g1, g2, g3, g4, g5, g6, g7, g8, g9, g10, g11, g12, g13, g14, g15, combineMat, block);
    }

    public static <S1 extends Shape, S2 extends Shape, S3 extends Shape, S4 extends Shape, S5 extends Shape, S6 extends Shape, S7 extends Shape, S8 extends Shape, S9 extends Shape, S10 extends Shape, S11 extends Shape, S12 extends Shape, S13 extends Shape, S14 extends Shape, S extends Shape, M1, M2, M3, M4, M5, M6, M7, M8, M9, M10, M11, M12, M13, M14, M> Graph<S, M> create14(Graph<S1, M1> g1, Graph<S2, M2> g2, Graph<S3, M3> g3, Graph<S4, M4> g4, Graph<S5, M5> g5, Graph<S6, M6> g6, Graph<S7, M7> g7, Graph<S8, M8> g8, Graph<S9, M9> g9, Graph<S10, M10> g10, Graph<S11, M11> g11, Graph<S12, M12> g12, Graph<S13, M13> g13, Graph<S14, M14> g14, Function14<M1, M2, M3, M4, M5, M6, M7, M8, M9, M10, M11, M12, M13, M14, M> combineMat, Function15<Builder<M>, S1, S2, S3, S4, S5, S6, S7, S8, S9, S10, S11, S12, S13, S14, S> block) {
        return GraphDSL$.MODULE$.create14(g1, g2, g3, g4, g5, g6, g7, g8, g9, g10, g11, g12, g13, g14, combineMat, block);
    }

    public static <S1 extends Shape, S2 extends Shape, S3 extends Shape, S4 extends Shape, S5 extends Shape, S6 extends Shape, S7 extends Shape, S8 extends Shape, S9 extends Shape, S10 extends Shape, S11 extends Shape, S12 extends Shape, S13 extends Shape, S extends Shape, M1, M2, M3, M4, M5, M6, M7, M8, M9, M10, M11, M12, M13, M> Graph<S, M> create13(Graph<S1, M1> g1, Graph<S2, M2> g2, Graph<S3, M3> g3, Graph<S4, M4> g4, Graph<S5, M5> g5, Graph<S6, M6> g6, Graph<S7, M7> g7, Graph<S8, M8> g8, Graph<S9, M9> g9, Graph<S10, M10> g10, Graph<S11, M11> g11, Graph<S12, M12> g12, Graph<S13, M13> g13, Function13<M1, M2, M3, M4, M5, M6, M7, M8, M9, M10, M11, M12, M13, M> combineMat, Function14<Builder<M>, S1, S2, S3, S4, S5, S6, S7, S8, S9, S10, S11, S12, S13, S> block) {
        return GraphDSL$.MODULE$.create13(g1, g2, g3, g4, g5, g6, g7, g8, g9, g10, g11, g12, g13, combineMat, block);
    }

    public static <S1 extends Shape, S2 extends Shape, S3 extends Shape, S4 extends Shape, S5 extends Shape, S6 extends Shape, S7 extends Shape, S8 extends Shape, S9 extends Shape, S10 extends Shape, S11 extends Shape, S12 extends Shape, S extends Shape, M1, M2, M3, M4, M5, M6, M7, M8, M9, M10, M11, M12, M> Graph<S, M> create12(Graph<S1, M1> g1, Graph<S2, M2> g2, Graph<S3, M3> g3, Graph<S4, M4> g4, Graph<S5, M5> g5, Graph<S6, M6> g6, Graph<S7, M7> g7, Graph<S8, M8> g8, Graph<S9, M9> g9, Graph<S10, M10> g10, Graph<S11, M11> g11, Graph<S12, M12> g12, Function12<M1, M2, M3, M4, M5, M6, M7, M8, M9, M10, M11, M12, M> combineMat, Function13<Builder<M>, S1, S2, S3, S4, S5, S6, S7, S8, S9, S10, S11, S12, S> block) {
        return GraphDSL$.MODULE$.create12(g1, g2, g3, g4, g5, g6, g7, g8, g9, g10, g11, g12, combineMat, block);
    }

    public static <S1 extends Shape, S2 extends Shape, S3 extends Shape, S4 extends Shape, S5 extends Shape, S6 extends Shape, S7 extends Shape, S8 extends Shape, S9 extends Shape, S10 extends Shape, S11 extends Shape, S extends Shape, M1, M2, M3, M4, M5, M6, M7, M8, M9, M10, M11, M> Graph<S, M> create11(Graph<S1, M1> g1, Graph<S2, M2> g2, Graph<S3, M3> g3, Graph<S4, M4> g4, Graph<S5, M5> g5, Graph<S6, M6> g6, Graph<S7, M7> g7, Graph<S8, M8> g8, Graph<S9, M9> g9, Graph<S10, M10> g10, Graph<S11, M11> g11, Function11<M1, M2, M3, M4, M5, M6, M7, M8, M9, M10, M11, M> combineMat, Function12<Builder<M>, S1, S2, S3, S4, S5, S6, S7, S8, S9, S10, S11, S> block) {
        return GraphDSL$.MODULE$.create11(g1, g2, g3, g4, g5, g6, g7, g8, g9, g10, g11, combineMat, block);
    }

    public static <S1 extends Shape, S2 extends Shape, S3 extends Shape, S4 extends Shape, S5 extends Shape, S6 extends Shape, S7 extends Shape, S8 extends Shape, S9 extends Shape, S10 extends Shape, S extends Shape, M1, M2, M3, M4, M5, M6, M7, M8, M9, M10, M> Graph<S, M> create10(Graph<S1, M1> g1, Graph<S2, M2> g2, Graph<S3, M3> g3, Graph<S4, M4> g4, Graph<S5, M5> g5, Graph<S6, M6> g6, Graph<S7, M7> g7, Graph<S8, M8> g8, Graph<S9, M9> g9, Graph<S10, M10> g10, Function10<M1, M2, M3, M4, M5, M6, M7, M8, M9, M10, M> combineMat, Function11<Builder<M>, S1, S2, S3, S4, S5, S6, S7, S8, S9, S10, S> block) {
        return GraphDSL$.MODULE$.create10(g1, g2, g3, g4, g5, g6, g7, g8, g9, g10, combineMat, block);
    }

    public static <S1 extends Shape, S2 extends Shape, S3 extends Shape, S4 extends Shape, S5 extends Shape, S6 extends Shape, S7 extends Shape, S8 extends Shape, S9 extends Shape, S extends Shape, M1, M2, M3, M4, M5, M6, M7, M8, M9, M> Graph<S, M> create9(Graph<S1, M1> g1, Graph<S2, M2> g2, Graph<S3, M3> g3, Graph<S4, M4> g4, Graph<S5, M5> g5, Graph<S6, M6> g6, Graph<S7, M7> g7, Graph<S8, M8> g8, Graph<S9, M9> g9, Function9<M1, M2, M3, M4, M5, M6, M7, M8, M9, M> combineMat, Function10<Builder<M>, S1, S2, S3, S4, S5, S6, S7, S8, S9, S> block) {
        return GraphDSL$.MODULE$.create9(g1, g2, g3, g4, g5, g6, g7, g8, g9, combineMat, block);
    }

    public static <S1 extends Shape, S2 extends Shape, S3 extends Shape, S4 extends Shape, S5 extends Shape, S6 extends Shape, S7 extends Shape, S8 extends Shape, S extends Shape, M1, M2, M3, M4, M5, M6, M7, M8, M> Graph<S, M> create8(Graph<S1, M1> g1, Graph<S2, M2> g2, Graph<S3, M3> g3, Graph<S4, M4> g4, Graph<S5, M5> g5, Graph<S6, M6> g6, Graph<S7, M7> g7, Graph<S8, M8> g8, Function8<M1, M2, M3, M4, M5, M6, M7, M8, M> combineMat, Function9<Builder<M>, S1, S2, S3, S4, S5, S6, S7, S8, S> block) {
        return GraphDSL$.MODULE$.create8(g1, g2, g3, g4, g5, g6, g7, g8, combineMat, block);
    }

    public static <S1 extends Shape, S2 extends Shape, S3 extends Shape, S4 extends Shape, S5 extends Shape, S6 extends Shape, S7 extends Shape, S extends Shape, M1, M2, M3, M4, M5, M6, M7, M> Graph<S, M> create7(Graph<S1, M1> g1, Graph<S2, M2> g2, Graph<S3, M3> g3, Graph<S4, M4> g4, Graph<S5, M5> g5, Graph<S6, M6> g6, Graph<S7, M7> g7, Function7<M1, M2, M3, M4, M5, M6, M7, M> combineMat, Function8<Builder<M>, S1, S2, S3, S4, S5, S6, S7, S> block) {
        return GraphDSL$.MODULE$.create7(g1, g2, g3, g4, g5, g6, g7, combineMat, block);
    }

    public static <S1 extends Shape, S2 extends Shape, S3 extends Shape, S4 extends Shape, S5 extends Shape, S6 extends Shape, S extends Shape, M1, M2, M3, M4, M5, M6, M> Graph<S, M> create6(Graph<S1, M1> g1, Graph<S2, M2> g2, Graph<S3, M3> g3, Graph<S4, M4> g4, Graph<S5, M5> g5, Graph<S6, M6> g6, Function6<M1, M2, M3, M4, M5, M6, M> combineMat, Function7<Builder<M>, S1, S2, S3, S4, S5, S6, S> block) {
        return GraphDSL$.MODULE$.create6(g1, g2, g3, g4, g5, g6, combineMat, block);
    }

    public static <S1 extends Shape, S2 extends Shape, S3 extends Shape, S4 extends Shape, S5 extends Shape, S extends Shape, M1, M2, M3, M4, M5, M> Graph<S, M> create5(Graph<S1, M1> g1, Graph<S2, M2> g2, Graph<S3, M3> g3, Graph<S4, M4> g4, Graph<S5, M5> g5, Function5<M1, M2, M3, M4, M5, M> combineMat, Function6<Builder<M>, S1, S2, S3, S4, S5, S> block) {
        return GraphDSL$.MODULE$.create5(g1, g2, g3, g4, g5, combineMat, block);
    }

    public static <S1 extends Shape, S2 extends Shape, S3 extends Shape, S4 extends Shape, S extends Shape, M1, M2, M3, M4, M> Graph<S, M> create4(Graph<S1, M1> g1, Graph<S2, M2> g2, Graph<S3, M3> g3, Graph<S4, M4> g4, Function4<M1, M2, M3, M4, M> combineMat, Function5<Builder<M>, S1, S2, S3, S4, S> block) {
        return GraphDSL$.MODULE$.create4(g1, g2, g3, g4, combineMat, block);
    }

    public static <S1 extends Shape, S2 extends Shape, S3 extends Shape, S extends Shape, M1, M2, M3, M> Graph<S, M> create3(Graph<S1, M1> g1, Graph<S2, M2> g2, Graph<S3, M3> g3, Function3<M1, M2, M3, M> combineMat, Function4<Builder<M>, S1, S2, S3, S> block) {
        return GraphDSL$.MODULE$.create3(g1, g2, g3, combineMat, block);
    }

    public static <S1 extends Shape, S2 extends Shape, S extends Shape, M1, M2, M> Graph<S, M> create(Graph<S1, M1> g1, Graph<S2, M2> g2, Function2<M1, M2, M> combineMat, Function3<Builder<M>, S1, S2, S> block) {
        return GraphDSL$.MODULE$.create(g1, g2, combineMat, block);
    }

    public static <S1 extends Shape, S extends Shape, M> Graph<S, M> create(Graph<S1, M> g1, Function2<Builder<M>, S1, S> block) {
        return GraphDSL$.MODULE$.create(g1, block);
    }

    public static <S extends Shape> Graph<S, NotUsed> create(Function<Builder<NotUsed>, S> block) {
        return GraphDSL$.MODULE$.create(block);
    }

    public static final class Builder<Mat> {
        private final GraphDSL.Builder<Mat> delegate;

        public GraphDSL.Builder<Mat> delegate() {
            return this.delegate;
        }

        public <S extends Shape> S add(Graph<S, ?> graph) {
            return this.delegate().add(graph);
        }

        public Outlet<Mat> materializedValue() {
            return this.delegate().materializedValue();
        }

        public <T> ForwardOps<T> from(Outlet<T> out) {
            return new ForwardOps<T>(this, out);
        }

        public <T> ForwardOps<T> from(SourceShape<T> src) {
            return new ForwardOps<T>(this, src.out());
        }

        public <I, O> ForwardOps<O> from(FlowShape<I, O> f) {
            return new ForwardOps<O>(this, f.out());
        }

        public <I, O> ForwardOps<O> from(UniformFanInShape<I, O> j) {
            return new ForwardOps(this, j.out());
        }

        public <I, O> ForwardOps<O> from(UniformFanOutShape<I, O> j) {
            return new ForwardOps<O>(this, GraphDSL$Implicits$.MODULE$.findOut(this.delegate(), j, 0));
        }

        public <T> ReverseOps<T> to(Inlet<T> in) {
            return new ReverseOps<T>(this, in);
        }

        public <T> ReverseOps<T> to(SinkShape<T> dst) {
            return new ReverseOps<T>(this, dst.in());
        }

        public <I, O> ReverseOps<I> to(FlowShape<I, O> f) {
            return new ReverseOps<I>(this, f.in());
        }

        public <I, O> ReverseOps<I> to(UniformFanInShape<I, O> j) {
            return new ReverseOps<I>(this, GraphDSL$Implicits$.MODULE$.findIn(this.delegate(), j, 0));
        }

        public <I, O> ReverseOps<I> to(UniformFanOutShape<I, O> j) {
            return new ReverseOps(this, j.in());
        }

        public Builder(GraphDSL.Builder<Mat> delegate) {
            this.delegate = delegate;
        }

        public final class ForwardOps<T> {
            private final Outlet<T> _out;
            private final /* synthetic */ Builder $outer;

            public Builder<Mat> toInlet(Inlet<? super T> in) {
                GraphDSL$Implicits$.MODULE$.port2flow(this._out, this.$outer.delegate()).$tilde$greater(in, this.$outer.delegate());
                return this.$outer;
            }

            public Builder<Mat> to(SinkShape<? super T> dst) {
                GraphDSL$Implicits$.MODULE$.port2flow(this._out, this.$outer.delegate()).$tilde$greater(dst, this.$outer.delegate());
                return this.$outer;
            }

            public <U> Builder<Mat> toFanIn(UniformFanInShape<? super T, U> j) {
                GraphDSL$Implicits$.MODULE$.port2flow(this._out, this.$outer.delegate()).$tilde$greater(j, this.$outer.delegate());
                return this.$outer;
            }

            public <U> Builder<Mat> toFanOut(UniformFanOutShape<? super T, U> j) {
                GraphDSL$Implicits$.MODULE$.port2flow(this._out, this.$outer.delegate()).$tilde$greater(j, this.$outer.delegate());
                return this.$outer;
            }

            public <U> ForwardOps<U> via(FlowShape<? super T, U> f) {
                return this.$outer.from(GraphDSL$Implicits$.MODULE$.port2flow(this._out, this.$outer.delegate()).$tilde$greater(f, this.$outer.delegate()).outlet());
            }

            public <U> ForwardOps<U> viaFanIn(UniformFanInShape<? super T, U> j) {
                return this.$outer.from(GraphDSL$Implicits$.MODULE$.port2flow(this._out, this.$outer.delegate()).$tilde$greater(j, this.$outer.delegate()).outlet());
            }

            public <U> ForwardOps<U> viaFanOut(UniformFanOutShape<? super T, U> j) {
                return this.$outer.from(GraphDSL$Implicits$.MODULE$.port2flow(this._out, this.$outer.delegate()).$tilde$greater(j, this.$outer.delegate()).outlet());
            }

            public Outlet<T> out() {
                return this._out;
            }

            public ForwardOps(Builder $outer, Outlet<T> _out) {
                this._out = _out;
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
            }
        }

        public final class ReverseOps<T> {
            private final Inlet<T> out;
            private final /* synthetic */ Builder $outer;

            public Builder<Mat> fromOutlet(Outlet<? extends T> dst) {
                GraphDSL$Implicits$.MODULE$.ReversePortOps(this.out).$less$tilde(dst, this.$outer.delegate());
                return this.$outer;
            }

            public Builder<Mat> from(SourceShape<? extends T> dst) {
                GraphDSL$Implicits$.MODULE$.ReversePortOps(this.out).$less$tilde((SourceShape<T>)dst, (GraphDSL.Builder<?>)this.$outer.delegate());
                return this.$outer;
            }

            public <U> Builder<Mat> fromFanIn(UniformFanInShape<U, ? extends T> j) {
                GraphDSL$Implicits$.MODULE$.ReversePortOps(this.out).$less$tilde(j, (GraphDSL.Builder<?>)this.$outer.delegate());
                return this.$outer;
            }

            public <U> Builder<Mat> fromFanOut(UniformFanOutShape<U, ? extends T> j) {
                GraphDSL$Implicits$.MODULE$.ReversePortOps(this.out).$less$tilde(j, (GraphDSL.Builder<?>)this.$outer.delegate());
                return this.$outer;
            }

            public <U> ReverseOps<U> via(FlowShape<U, ? extends T> f) {
                return this.$outer.to(GraphDSL$Implicits$.MODULE$.ReversePortOps(this.out).$less$tilde(f, (GraphDSL.Builder<?>)this.$outer.delegate()).inlet());
            }

            public <U> ReverseOps<U> viaFanIn(UniformFanInShape<U, ? extends T> j) {
                return this.$outer.to(GraphDSL$Implicits$.MODULE$.ReversePortOps(this.out).$less$tilde(j, (GraphDSL.Builder<?>)this.$outer.delegate()).inlet());
            }

            public <U> ReverseOps<U> viaFanOut(UniformFanOutShape<U, ? extends T> j) {
                return this.$outer.to(GraphDSL$Implicits$.MODULE$.ReversePortOps(this.out).$less$tilde(j, (GraphDSL.Builder<?>)this.$outer.delegate()).inlet());
            }

            public ReverseOps(Builder $outer, Inlet<T> out) {
                this.out = out;
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
            }
        }
    }
}

