/*
 * Decompiled with CFR 0.152.
 */
package akka.stream;

import akka.event.Logging;
import akka.stream.OverflowStrategy;
import scala.Product;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\r=uACA \u0003\u0003B\t!!\u0012\u0002J\u0019Q\u0011QJA!\u0011\u0003\t)%a\u0014\t\u000f\u0005u\u0013\u0001\"\u0001\u0002b\u0019A\u00111M\u0001A\u0003\u000b\n)\u0007\u0003\u0006\u0002\f\u000e\u0011)\u001a!C\u0001\u0003\u001bC!\"!+\u0004\u0005#\u0005\u000b\u0011BAH\u0011\u001d\tif\u0001C\u0001\u0003WCq!a-\u0004\t\u0003\n)\fC\u0005\u0002:\u000e!\t%!\u0012\u0002<\"I\u00111Y\u0002\u0002\u0002\u0013\u0005\u0011Q\u0019\u0005\n\u0003\u0013\u001c\u0011\u0013!C\u0001\u0003\u0017D\u0011\"!9\u0004\u0003\u0003%\t%a9\t\u0013\u0005U8!!A\u0005\u0002\u0005]\b\"CA\u0000\u0007\u0005\u0005I\u0011\u0001B\u0001\u0011%\u0011iaAA\u0001\n\u0003\u0012y\u0001C\u0005\u0003\u001e\r\t\t\u0011\"\u0001\u0003 !I!1E\u0002\u0002\u0002\u0013\u0005#Q\u0005\u0005\n\u0005S\u0019\u0011\u0011!C!\u0005WA\u0011B!\f\u0004\u0003\u0003%\tEa\f\t\u0013\tE2!!A\u0005B\tMra\u0003B\u001c\u0003\u0005\u0005\t\u0012AA#\u0005s11\"a\u0019\u0002\u0003\u0003E\t!!\u0012\u0003<!9\u0011QL\u000b\u0005\u0002\tM\u0003\"\u0003B\u0017+\u0005\u0005IQ\tB\u0018\u0011%\u0011)&FA\u0001\n\u0003\u00139\u0006C\u0005\u0003\\U\t\t\u0011\"!\u0003^!I!\u0011N\u000b\u0002\u0002\u0013%!1\u000e\u0004\t\u0005g\n\u0001)!\u0012\u0003v!Q\u00111R\u000e\u0003\u0016\u0004%\t!!$\t\u0015\u0005%6D!E!\u0002\u0013\ty\tC\u0004\u0002^m!\tAa\u001e\t\u000f\u0005M6\u0004\"\u0011\u0003~!I\u0011\u0011X\u000e\u0005B\u0005\u0015\u00131\u0018\u0005\n\u0003\u0007\\\u0012\u0011!C\u0001\u0005\u0003C\u0011\"!3\u001c#\u0003%\t!a3\t\u0013\u0005\u00058$!A\u0005B\u0005\r\b\"CA{7\u0005\u0005I\u0011AA|\u0011%\typGA\u0001\n\u0003\u0011)\tC\u0005\u0003\u000em\t\t\u0011\"\u0011\u0003\u0010!I!QD\u000e\u0002\u0002\u0013\u0005!\u0011\u0012\u0005\n\u0005GY\u0012\u0011!C!\u0005\u001bC\u0011B!\u000b\u001c\u0003\u0003%\tEa\u000b\t\u0013\t52$!A\u0005B\t=\u0002\"\u0003B\u00197\u0005\u0005I\u0011\tBI\u000f-\u0011)*AA\u0001\u0012\u0003\t)Ea&\u0007\u0017\tM\u0014!!A\t\u0002\u0005\u0015#\u0011\u0014\u0005\b\u0003;jC\u0011\u0001BO\u0011%\u0011i#LA\u0001\n\u000b\u0012y\u0003C\u0005\u0003V5\n\t\u0011\"!\u0003 \"I!1L\u0017\u0002\u0002\u0013\u0005%1\u0015\u0005\n\u0005Sj\u0013\u0011!C\u0005\u0005W2\u0001Ba*\u0002\u0001\u0006\u0015#\u0011\u0016\u0005\u000b\u0003\u0017\u001b$Q3A\u0005\u0002\u00055\u0005BCAUg\tE\t\u0015!\u0003\u0002\u0010\"9\u0011QL\u001a\u0005\u0002\t-\u0006bBAZg\u0011\u0005#\u0011\u0017\u0005\n\u0003s\u001bD\u0011IA#\u0003wC\u0011\"a14\u0003\u0003%\tA!.\t\u0013\u0005%7'%A\u0005\u0002\u0005-\u0007\"CAqg\u0005\u0005I\u0011IAr\u0011%\t)pMA\u0001\n\u0003\t9\u0010C\u0005\u0002\u0000N\n\t\u0011\"\u0001\u0003:\"I!QB\u001a\u0002\u0002\u0013\u0005#q\u0002\u0005\n\u0005;\u0019\u0014\u0011!C\u0001\u0005{C\u0011Ba\t4\u0003\u0003%\tE!1\t\u0013\t%2'!A\u0005B\t-\u0002\"\u0003B\u0017g\u0005\u0005I\u0011\tB\u0018\u0011%\u0011\tdMA\u0001\n\u0003\u0012)mB\u0006\u0003J\u0006\t\t\u0011#\u0001\u0002F\t-ga\u0003BT\u0003\u0005\u0005\t\u0012AA#\u0005\u001bDq!!\u0018F\t\u0003\u0011\t\u000eC\u0005\u0003.\u0015\u000b\t\u0011\"\u0012\u00030!I!QK#\u0002\u0002\u0013\u0005%1\u001b\u0005\n\u00057*\u0015\u0011!CA\u0005/D\u0011B!\u001bF\u0003\u0003%IAa\u001b\u0007\u0011\tm\u0017\u0001QA#\u0005;D!\"a#L\u0005+\u0007I\u0011AAG\u0011)\tIk\u0013B\tB\u0003%\u0011q\u0012\u0005\b\u0003;ZE\u0011\u0001Bp\u0011\u001d\t\u0019l\u0013C!\u0005KD\u0011\"!/L\t\u0003\n)%a/\t\u0013\u0005\r7*!A\u0005\u0002\t%\b\"CAe\u0017F\u0005I\u0011AAf\u0011%\t\toSA\u0001\n\u0003\n\u0019\u000fC\u0005\u0002v.\u000b\t\u0011\"\u0001\u0002x\"I\u0011q`&\u0002\u0002\u0013\u0005!Q\u001e\u0005\n\u0005\u001bY\u0015\u0011!C!\u0005\u001fA\u0011B!\bL\u0003\u0003%\tA!=\t\u0013\t\r2*!A\u0005B\tU\b\"\u0003B\u0015\u0017\u0006\u0005I\u0011\tB\u0016\u0011%\u0011icSA\u0001\n\u0003\u0012y\u0003C\u0005\u00032-\u000b\t\u0011\"\u0011\u0003z\u001eY!Q`\u0001\u0002\u0002#\u0005\u0011Q\tB\u0000\r-\u0011Y.AA\u0001\u0012\u0003\t)e!\u0001\t\u000f\u0005uS\f\"\u0001\u0004\u0006!I!QF/\u0002\u0002\u0013\u0015#q\u0006\u0005\n\u0005+j\u0016\u0011!CA\u0007\u000fA\u0011Ba\u0017^\u0003\u0003%\tia\u0003\t\u0013\t%T,!A\u0005\n\t-d\u0001CB\b\u0003\u0001\u000b)e!\u0005\t\u0015\u0005-5M!f\u0001\n\u0003\ti\t\u0003\u0006\u0002*\u000e\u0014\t\u0012)A\u0005\u0003\u001fCq!!\u0018d\t\u0003\u0019\u0019\u0002C\u0004\u00024\u000e$\te!\u0007\t\u0013\u0005e6\r\"\u0011\u0002F\u0005m\u0006\"CAbG\u0006\u0005I\u0011AB\u000f\u0011%\tImYI\u0001\n\u0003\tY\rC\u0005\u0002b\u000e\f\t\u0011\"\u0011\u0002d\"I\u0011Q_2\u0002\u0002\u0013\u0005\u0011q\u001f\u0005\n\u0003\u007f\u001c\u0017\u0011!C\u0001\u0007CA\u0011B!\u0004d\u0003\u0003%\tEa\u0004\t\u0013\tu1-!A\u0005\u0002\r\u0015\u0002\"\u0003B\u0012G\u0006\u0005I\u0011IB\u0015\u0011%\u0011IcYA\u0001\n\u0003\u0012Y\u0003C\u0005\u0003.\r\f\t\u0011\"\u0011\u00030!I!\u0011G2\u0002\u0002\u0013\u00053QF\u0004\f\u0007c\t\u0011\u0011!E\u0001\u0003\u000b\u001a\u0019DB\u0006\u0004\u0010\u0005\t\t\u0011#\u0001\u0002F\rU\u0002bBA/k\u0012\u00051\u0011\b\u0005\n\u0005[)\u0018\u0011!C#\u0005_A\u0011B!\u0016v\u0003\u0003%\tia\u000f\t\u0013\tmS/!A\u0005\u0002\u000e}\u0002\"\u0003B5k\u0006\u0005I\u0011\u0002B6\r!\u0019\u0019%\u0001!\u0002F\r\u0015\u0003BCAFw\nU\r\u0011\"\u0001\u0002\u000e\"Q\u0011\u0011V>\u0003\u0012\u0003\u0006I!a$\t\u000f\u0005u3\u0010\"\u0001\u0004H!9\u00111W>\u0005B\r5\u0003\"CA]w\u0012\u0005\u0013QIA^\u0011%\t\u0019m_A\u0001\n\u0003\u0019\t\u0006C\u0005\u0002Jn\f\n\u0011\"\u0001\u0002L\"I\u0011\u0011]>\u0002\u0002\u0013\u0005\u00131\u001d\u0005\n\u0003k\\\u0018\u0011!C\u0001\u0003oD\u0011\"a@|\u0003\u0003%\ta!\u0016\t\u0013\t510!A\u0005B\t=\u0001\"\u0003B\u000fw\u0006\u0005I\u0011AB-\u0011%\u0011\u0019c_A\u0001\n\u0003\u001ai\u0006C\u0005\u0003*m\f\t\u0011\"\u0011\u0003,!I!QF>\u0002\u0002\u0013\u0005#q\u0006\u0005\n\u0005cY\u0018\u0011!C!\u0007C:1b!\u001a\u0002\u0003\u0003E\t!!\u0012\u0004h\u0019Y11I\u0001\u0002\u0002#\u0005\u0011QIB5\u0011!\ti&a\u0007\u0005\u0002\r5\u0004B\u0003B\u0017\u00037\t\t\u0011\"\u0012\u00030!Q!QKA\u000e\u0003\u0003%\tia\u001c\t\u0015\tm\u00131DA\u0001\n\u0003\u001b\u0019\b\u0003\u0006\u0003j\u0005m\u0011\u0011!C\u0005\u0005W:\u0011ba\u001e\u0002\u0011\u0003\u000b)e!\u001f\u0007\u0013\rm\u0014\u0001#!\u0002F\ru\u0004\u0002CA/\u0003S!\ta!\"\t\u0015\u0005e\u0016\u0011\u0006C!\u0003\u000b\nY\f\u0003\u0006\u0002b\u0006%\u0012\u0011!C!\u0003GD!\"!>\u0002*\u0005\u0005I\u0011AA|\u0011)\ty0!\u000b\u0002\u0002\u0013\u00051q\u0011\u0005\u000b\u0005\u001b\tI#!A\u0005B\t=\u0001B\u0003B\u000f\u0003S\t\t\u0011\"\u0001\u0004\f\"Q!\u0011FA\u0015\u0003\u0003%\tEa\u000b\t\u0015\t5\u0012\u0011FA\u0001\n\u0003\u0012y\u0003\u0003\u0006\u0003j\u0005%\u0012\u0011!C\u0005\u0005W\n!c\u0014<fe\u001adwn^*ue\u0006$XmZ5fg*!\u00111IA#\u0003\u0019\u0019HO]3b[*\u0011\u0011qI\u0001\u0005C.\\\u0017\rE\u0002\u0002L\u0005i!!!\u0011\u0003%=3XM\u001d4m_^\u001cFO]1uK\u001eLWm]\n\u0004\u0003\u0005E\u0003\u0003BA*\u00033j!!!\u0016\u000b\u0005\u0005]\u0013!B:dC2\f\u0017\u0002BA.\u0003+\u0012a!\u00118z%\u00164\u0017A\u0002\u001fj]&$hh\u0001\u0001\u0015\u0005\u0005%#\u0001\u0003#s_BDU-\u00193\u0014\u000f\r\t9'!\u001c\u0002tA!\u00111JA5\u0013\u0011\tY'!\u0011\u0003!=3XM\u001d4m_^\u001cFO]1uK\u001eL\b\u0003BA*\u0003_JA!!\u001d\u0002V\t9\u0001K]8ek\u000e$\b\u0003BA;\u0003\u000bsA!a\u001e\u0002\u0002:!\u0011\u0011PA@\u001b\t\tYH\u0003\u0003\u0002~\u0005}\u0013A\u0002\u001fs_>$h(\u0003\u0002\u0002X%!\u00111QA+\u0003\u001d\u0001\u0018mY6bO\u0016LA!a\"\u0002\n\na1+\u001a:jC2L'0\u00192mK*!\u00111QA+\u0003!awn\u001a'fm\u0016dWCAAH!\u0011\t\t*a)\u000f\t\u0005M\u0015Q\u0014\b\u0005\u0003+\u000bIJ\u0004\u0003\u0002z\u0005]\u0015BAA$\u0013\u0011\tY*!\u0012\u0002\u000b\u00154XM\u001c;\n\t\u0005}\u0015\u0011U\u0001\b\u0019><w-\u001b8h\u0015\u0011\tY*!\u0012\n\t\u0005\u0015\u0016q\u0015\u0002\t\u0019><G*\u001a<fY*!\u0011qTAQ\u0003%awn\u001a'fm\u0016d\u0007\u0005\u0006\u0003\u0002.\u0006E\u0006cAAX\u00075\t\u0011\u0001C\u0004\u0002\f\u001a\u0001\r!a$\u0002\u0019]LG\u000f\u001b'pO2+g/\u001a7\u0015\t\u00055\u0016q\u0017\u0005\b\u0003\u0017;\u0001\u0019AAH\u00039I7OQ1dWB\u0014Xm]:ve\u0016,\"!!0\u0011\t\u0005M\u0013qX\u0005\u0005\u0003\u0003\f)FA\u0004C_>dW-\u00198\u0002\t\r|\u0007/\u001f\u000b\u0005\u0003[\u000b9\rC\u0005\u0002\f&\u0001\n\u00111\u0001\u0002\u0010\u0006q1m\u001c9zI\u0011,g-Y;mi\u0012\nTCAAgU\u0011\ty)a4,\u0005\u0005E\u0007\u0003BAj\u0003;l!!!6\u000b\t\u0005]\u0017\u0011\\\u0001\nk:\u001c\u0007.Z2lK\u0012TA!a7\u0002V\u0005Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\t\u0005}\u0017Q\u001b\u0002\u0012k:\u001c\u0007.Z2lK\u00124\u0016M]5b]\u000e,\u0017!\u00049s_\u0012,8\r\u001e)sK\u001aL\u00070\u0006\u0002\u0002fB!\u0011q]Ay\u001b\t\tIO\u0003\u0003\u0002l\u00065\u0018\u0001\u00027b]\u001eT!!a<\u0002\t)\fg/Y\u0005\u0005\u0003g\fIO\u0001\u0004TiJLgnZ\u0001\raJ|G-^2u\u0003JLG/_\u000b\u0003\u0003s\u0004B!a\u0015\u0002|&!\u0011Q`A+\u0005\rIe\u000e^\u0001\u000faJ|G-^2u\u000b2,W.\u001a8u)\u0011\u0011\u0019A!\u0003\u0011\t\u0005M#QA\u0005\u0005\u0005\u000f\t)FA\u0002B]fD\u0011Ba\u0003\u000e\u0003\u0003\u0005\r!!?\u0002\u0007a$\u0013'A\bqe>$Wo\u0019;Ji\u0016\u0014\u0018\r^8s+\t\u0011\t\u0002\u0005\u0004\u0003\u0014\te!1A\u0007\u0003\u0005+QAAa\u0006\u0002V\u0005Q1m\u001c7mK\u000e$\u0018n\u001c8\n\t\tm!Q\u0003\u0002\t\u0013R,'/\u0019;pe\u0006A1-\u00198FcV\fG\u000e\u0006\u0003\u0002>\n\u0005\u0002\"\u0003B\u0006\u001f\u0005\u0005\t\u0019\u0001B\u0002\u0003I\u0001(o\u001c3vGR,E.Z7f]Rt\u0015-\\3\u0015\t\u0005\u0015(q\u0005\u0005\n\u0005\u0017\u0001\u0012\u0011!a\u0001\u0003s\f\u0001\u0002[1tQ\u000e{G-\u001a\u000b\u0003\u0003s\f\u0001\u0002^8TiJLgn\u001a\u000b\u0003\u0003K\fa!Z9vC2\u001cH\u0003BA_\u0005kA\u0011Ba\u0003\u0014\u0003\u0003\u0005\rAa\u0001\u0002\u0011\u0011\u0013x\u000e\u001d%fC\u0012\u00042!a,\u0016'\u0015)\"Q\bB%!!\u0011yD!\u0012\u0002\u0010\u00065VB\u0001B!\u0015\u0011\u0011\u0019%!\u0016\u0002\u000fI,h\u000e^5nK&!!q\tB!\u0005E\t%m\u001d;sC\u000e$h)\u001e8di&|g.\r\t\u0005\u0005\u0017\u0012\t&\u0004\u0002\u0003N)!!qJAw\u0003\tIw.\u0003\u0003\u0002\b\n5CC\u0001B\u001d\u0003\u0015\t\u0007\u000f\u001d7z)\u0011\tiK!\u0017\t\u000f\u0005-\u0005\u00041\u0001\u0002\u0010\u00069QO\\1qa2LH\u0003\u0002B0\u0005K\u0002b!a\u0015\u0003b\u0005=\u0015\u0002\u0002B2\u0003+\u0012aa\u00149uS>t\u0007\"\u0003B43\u0005\u0005\t\u0019AAW\u0003\rAH\u0005M\u0001\roJLG/\u001a*fa2\f7-\u001a\u000b\u0003\u0005[\u0002B!a:\u0003p%!!\u0011OAu\u0005\u0019y%M[3di\nAAI]8q)\u0006LGnE\u0004\u001c\u0003O\ni'a\u001d\u0015\t\te$1\u0010\t\u0004\u0003_[\u0002bBAF=\u0001\u0007\u0011q\u0012\u000b\u0005\u0005s\u0012y\bC\u0004\u0002\f~\u0001\r!a$\u0015\t\te$1\u0011\u0005\n\u0003\u0017\u000b\u0003\u0013!a\u0001\u0003\u001f#BAa\u0001\u0003\b\"I!1B\u0013\u0002\u0002\u0003\u0007\u0011\u0011 \u000b\u0005\u0003{\u0013Y\tC\u0005\u0003\f\u001d\n\t\u00111\u0001\u0003\u0004Q!\u0011Q\u001dBH\u0011%\u0011Y\u0001KA\u0001\u0002\u0004\tI\u0010\u0006\u0003\u0002>\nM\u0005\"\u0003B\u0006W\u0005\u0005\t\u0019\u0001B\u0002\u0003!!%o\u001c9UC&d\u0007cAAX[M)QFa'\u0003JAA!q\bB#\u0003\u001f\u0013I\b\u0006\u0002\u0003\u0018R!!\u0011\u0010BQ\u0011\u001d\tY\t\ra\u0001\u0003\u001f#BAa\u0018\u0003&\"I!qM\u0019\u0002\u0002\u0003\u0007!\u0011\u0010\u0002\u000b\tJ|\u0007OQ;gM\u0016\u00148cB\u001a\u0002h\u00055\u00141\u000f\u000b\u0005\u0005[\u0013y\u000bE\u0002\u00020NBq!a#7\u0001\u0004\ty\t\u0006\u0003\u0003.\nM\u0006bBAFo\u0001\u0007\u0011q\u0012\u000b\u0005\u0005[\u00139\fC\u0005\u0002\ff\u0002\n\u00111\u0001\u0002\u0010R!!1\u0001B^\u0011%\u0011Y!PA\u0001\u0002\u0004\tI\u0010\u0006\u0003\u0002>\n}\u0006\"\u0003B\u0006\u007f\u0005\u0005\t\u0019\u0001B\u0002)\u0011\t)Oa1\t\u0013\t-\u0001)!AA\u0002\u0005eH\u0003BA_\u0005\u000fD\u0011Ba\u0003D\u0003\u0003\u0005\rAa\u0001\u0002\u0015\u0011\u0013x\u000e\u001d\"vM\u001a,'\u000fE\u0002\u00020\u0016\u001bR!\u0012Bh\u0005\u0013\u0002\u0002Ba\u0010\u0003F\u0005=%Q\u0016\u000b\u0003\u0005\u0017$BA!,\u0003V\"9\u00111\u0012%A\u0002\u0005=E\u0003\u0002B0\u00053D\u0011Ba\u001aJ\u0003\u0003\u0005\rA!,\u0003\u000f\u0011\u0013x\u000e\u001d(foN91*a\u001a\u0002n\u0005MD\u0003\u0002Bq\u0005G\u00042!a,L\u0011\u001d\tYI\u0014a\u0001\u0003\u001f#BA!9\u0003h\"9\u00111R(A\u0002\u0005=E\u0003\u0002Bq\u0005WD\u0011\"a#R!\u0003\u0005\r!a$\u0015\t\t\r!q\u001e\u0005\n\u0005\u0017)\u0016\u0011!a\u0001\u0003s$B!!0\u0003t\"I!1B,\u0002\u0002\u0003\u0007!1\u0001\u000b\u0005\u0003K\u00149\u0010C\u0005\u0003\fa\u000b\t\u00111\u0001\u0002zR!\u0011Q\u0018B~\u0011%\u0011YaWA\u0001\u0002\u0004\u0011\u0019!A\u0004Ee>\u0004h*Z<\u0011\u0007\u0005=VlE\u0003^\u0007\u0007\u0011I\u0005\u0005\u0005\u0003@\t\u0015\u0013q\u0012Bq)\t\u0011y\u0010\u0006\u0003\u0003b\u000e%\u0001bBAFA\u0002\u0007\u0011q\u0012\u000b\u0005\u0005?\u001ai\u0001C\u0005\u0003h\u0005\f\t\u00111\u0001\u0003b\na!)Y2laJ,7o];sKN91-a\u001a\u0002n\u0005MD\u0003BB\u000b\u0007/\u00012!a,d\u0011\u001d\tYI\u001aa\u0001\u0003\u001f#Ba!\u0006\u0004\u001c!9\u00111R4A\u0002\u0005=E\u0003BB\u000b\u0007?A\u0011\"a#j!\u0003\u0005\r!a$\u0015\t\t\r11\u0005\u0005\n\u0005\u0017i\u0017\u0011!a\u0001\u0003s$B!!0\u0004(!I!1B8\u0002\u0002\u0003\u0007!1\u0001\u000b\u0005\u0003K\u001cY\u0003C\u0005\u0003\fA\f\t\u00111\u0001\u0002zR!\u0011QXB\u0018\u0011%\u0011Ya]A\u0001\u0002\u0004\u0011\u0019!\u0001\u0007CC\u000e\\\u0007O]3tgV\u0014X\rE\u0002\u00020V\u001cR!^B\u001c\u0005\u0013\u0002\u0002Ba\u0010\u0003F\u0005=5Q\u0003\u000b\u0003\u0007g!Ba!\u0006\u0004>!9\u00111\u0012=A\u0002\u0005=E\u0003\u0002B0\u0007\u0003B\u0011Ba\u001az\u0003\u0003\u0005\ra!\u0006\u0003\t\u0019\u000b\u0017\u000e\\\n\bw\u0006\u001d\u0014QNA:)\u0011\u0019Iea\u0013\u0011\u0007\u0005=6\u0010C\u0004\u0002\fz\u0004\r!a$\u0015\t\r%3q\n\u0005\b\u0003\u0017{\b\u0019AAH)\u0011\u0019Iea\u0015\t\u0015\u0005-\u00151\u0001I\u0001\u0002\u0004\ty\t\u0006\u0003\u0003\u0004\r]\u0003B\u0003B\u0006\u0003\u0017\t\t\u00111\u0001\u0002zR!\u0011QXB.\u0011)\u0011Y!a\u0004\u0002\u0002\u0003\u0007!1\u0001\u000b\u0005\u0003K\u001cy\u0006\u0003\u0006\u0003\f\u0005E\u0011\u0011!a\u0001\u0003s$B!!0\u0004d!Q!1BA\f\u0003\u0003\u0005\rAa\u0001\u0002\t\u0019\u000b\u0017\u000e\u001c\t\u0005\u0003_\u000bYb\u0005\u0004\u0002\u001c\r-$\u0011\n\t\t\u0005\u007f\u0011)%a$\u0004JQ\u00111q\r\u000b\u0005\u0007\u0013\u001a\t\b\u0003\u0005\u0002\f\u0006\u0005\u0002\u0019AAH)\u0011\u0011yf!\u001e\t\u0015\t\u001d\u00141EA\u0001\u0002\u0004\u0019I%A\u0005F[&$X)\u0019:msB!\u0011qVA\u0015\u0005%)U.\u001b;FCJd\u0017p\u0005\u0005\u0002*\r}\u0014QNA:!\u0011\tYe!!\n\t\r\r\u0015\u0011\t\u0002\u0016\t\u0016d\u0017-_(wKJ4Gn\\<TiJ\fG/Z4z)\t\u0019I\b\u0006\u0003\u0003\u0004\r%\u0005B\u0003B\u0006\u0003g\t\t\u00111\u0001\u0002zR!\u0011QXBG\u0011)\u0011Y!a\u000e\u0002\u0002\u0003\u0007!1\u0001")
public final class OverflowStrategies {

    public static class Backpressure
    extends OverflowStrategy
    implements Product {
        private final int logLevel;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        @Override
        public int logLevel() {
            return this.logLevel;
        }

        @Override
        public Backpressure withLogLevel(int logLevel) {
            return new Backpressure(logLevel);
        }

        @Override
        public boolean isBackpressure() {
            return true;
        }

        public Backpressure copy(int logLevel) {
            return new Backpressure(logLevel);
        }

        public int copy$default$1() {
            return this.logLevel();
        }

        @Override
        public String productPrefix() {
            return "Backpressure";
        }

        @Override
        public int productArity() {
            return 1;
        }

        @Override
        public Object productElement(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return new Logging.LogLevel(this.logLevel());
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
            return x$1 instanceof Backpressure;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "logLevel";
                }
            }
            return (String)Statics.ioobe(x$1);
        }

        public int hashCode() {
            return ScalaRunTime$.MODULE$._hashCode(this);
        }

        public String toString() {
            return ScalaRunTime$.MODULE$._toString(this);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public boolean equals(Object x$1) {
            if (this == x$1) return true;
            Object object = x$1;
            if (!(object instanceof Backpressure)) return false;
            boolean bl = true;
            if (!bl) return false;
            Backpressure backpressure = (Backpressure)x$1;
            if (this.logLevel() != backpressure.logLevel()) return false;
            if (!backpressure.canEqual(this)) return false;
            return true;
        }

        public Backpressure(int logLevel) {
            this.logLevel = logLevel;
            Product.$init$(this);
        }
    }

    public static class DropBuffer
    extends OverflowStrategy
    implements Product {
        private final int logLevel;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        @Override
        public int logLevel() {
            return this.logLevel;
        }

        @Override
        public DropBuffer withLogLevel(int logLevel) {
            return new DropBuffer(logLevel);
        }

        @Override
        public boolean isBackpressure() {
            return false;
        }

        public DropBuffer copy(int logLevel) {
            return new DropBuffer(logLevel);
        }

        public int copy$default$1() {
            return this.logLevel();
        }

        @Override
        public String productPrefix() {
            return "DropBuffer";
        }

        @Override
        public int productArity() {
            return 1;
        }

        @Override
        public Object productElement(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return new Logging.LogLevel(this.logLevel());
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
            return x$1 instanceof DropBuffer;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "logLevel";
                }
            }
            return (String)Statics.ioobe(x$1);
        }

        public int hashCode() {
            return ScalaRunTime$.MODULE$._hashCode(this);
        }

        public String toString() {
            return ScalaRunTime$.MODULE$._toString(this);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public boolean equals(Object x$1) {
            if (this == x$1) return true;
            Object object = x$1;
            if (!(object instanceof DropBuffer)) return false;
            boolean bl = true;
            if (!bl) return false;
            DropBuffer dropBuffer = (DropBuffer)x$1;
            if (this.logLevel() != dropBuffer.logLevel()) return false;
            if (!dropBuffer.canEqual(this)) return false;
            return true;
        }

        public DropBuffer(int logLevel) {
            this.logLevel = logLevel;
            Product.$init$(this);
        }
    }

    public static class DropHead
    extends OverflowStrategy
    implements Product {
        private final int logLevel;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        @Override
        public int logLevel() {
            return this.logLevel;
        }

        @Override
        public DropHead withLogLevel(int logLevel) {
            return new DropHead(logLevel);
        }

        @Override
        public boolean isBackpressure() {
            return false;
        }

        public DropHead copy(int logLevel) {
            return new DropHead(logLevel);
        }

        public int copy$default$1() {
            return this.logLevel();
        }

        @Override
        public String productPrefix() {
            return "DropHead";
        }

        @Override
        public int productArity() {
            return 1;
        }

        @Override
        public Object productElement(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return new Logging.LogLevel(this.logLevel());
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
            return x$1 instanceof DropHead;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "logLevel";
                }
            }
            return (String)Statics.ioobe(x$1);
        }

        public int hashCode() {
            return ScalaRunTime$.MODULE$._hashCode(this);
        }

        public String toString() {
            return ScalaRunTime$.MODULE$._toString(this);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public boolean equals(Object x$1) {
            if (this == x$1) return true;
            Object object = x$1;
            if (!(object instanceof DropHead)) return false;
            boolean bl = true;
            if (!bl) return false;
            DropHead dropHead = (DropHead)x$1;
            if (this.logLevel() != dropHead.logLevel()) return false;
            if (!dropHead.canEqual(this)) return false;
            return true;
        }

        public DropHead(int logLevel) {
            this.logLevel = logLevel;
            Product.$init$(this);
        }
    }

    public static class DropNew
    extends OverflowStrategy
    implements Product {
        private final int logLevel;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        @Override
        public int logLevel() {
            return this.logLevel;
        }

        @Override
        public DropNew withLogLevel(int logLevel) {
            return new DropNew(logLevel);
        }

        @Override
        public boolean isBackpressure() {
            return false;
        }

        public DropNew copy(int logLevel) {
            return new DropNew(logLevel);
        }

        public int copy$default$1() {
            return this.logLevel();
        }

        @Override
        public String productPrefix() {
            return "DropNew";
        }

        @Override
        public int productArity() {
            return 1;
        }

        @Override
        public Object productElement(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return new Logging.LogLevel(this.logLevel());
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
            return x$1 instanceof DropNew;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "logLevel";
                }
            }
            return (String)Statics.ioobe(x$1);
        }

        public int hashCode() {
            return ScalaRunTime$.MODULE$._hashCode(this);
        }

        public String toString() {
            return ScalaRunTime$.MODULE$._toString(this);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public boolean equals(Object x$1) {
            if (this == x$1) return true;
            Object object = x$1;
            if (!(object instanceof DropNew)) return false;
            boolean bl = true;
            if (!bl) return false;
            DropNew dropNew = (DropNew)x$1;
            if (this.logLevel() != dropNew.logLevel()) return false;
            if (!dropNew.canEqual(this)) return false;
            return true;
        }

        public DropNew(int logLevel) {
            this.logLevel = logLevel;
            Product.$init$(this);
        }
    }

    public static class DropTail
    extends OverflowStrategy
    implements Product {
        private final int logLevel;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        @Override
        public int logLevel() {
            return this.logLevel;
        }

        @Override
        public DropTail withLogLevel(int logLevel) {
            return new DropTail(logLevel);
        }

        @Override
        public boolean isBackpressure() {
            return false;
        }

        public DropTail copy(int logLevel) {
            return new DropTail(logLevel);
        }

        public int copy$default$1() {
            return this.logLevel();
        }

        @Override
        public String productPrefix() {
            return "DropTail";
        }

        @Override
        public int productArity() {
            return 1;
        }

        @Override
        public Object productElement(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return new Logging.LogLevel(this.logLevel());
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
            return x$1 instanceof DropTail;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "logLevel";
                }
            }
            return (String)Statics.ioobe(x$1);
        }

        public int hashCode() {
            return ScalaRunTime$.MODULE$._hashCode(this);
        }

        public String toString() {
            return ScalaRunTime$.MODULE$._toString(this);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public boolean equals(Object x$1) {
            if (this == x$1) return true;
            Object object = x$1;
            if (!(object instanceof DropTail)) return false;
            boolean bl = true;
            if (!bl) return false;
            DropTail dropTail = (DropTail)x$1;
            if (this.logLevel() != dropTail.logLevel()) return false;
            if (!dropTail.canEqual(this)) return false;
            return true;
        }

        public DropTail(int logLevel) {
            this.logLevel = logLevel;
            Product.$init$(this);
        }
    }

    public static class Fail
    extends OverflowStrategy
    implements Product {
        private final int logLevel;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        @Override
        public int logLevel() {
            return this.logLevel;
        }

        @Override
        public Fail withLogLevel(int logLevel) {
            return new Fail(logLevel);
        }

        @Override
        public boolean isBackpressure() {
            return false;
        }

        public Fail copy(int logLevel) {
            return new Fail(logLevel);
        }

        public int copy$default$1() {
            return this.logLevel();
        }

        @Override
        public String productPrefix() {
            return "Fail";
        }

        @Override
        public int productArity() {
            return 1;
        }

        @Override
        public Object productElement(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return new Logging.LogLevel(this.logLevel());
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
            return x$1 instanceof Fail;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "logLevel";
                }
            }
            return (String)Statics.ioobe(x$1);
        }

        public int hashCode() {
            return ScalaRunTime$.MODULE$._hashCode(this);
        }

        public String toString() {
            return ScalaRunTime$.MODULE$._toString(this);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public boolean equals(Object x$1) {
            if (this == x$1) return true;
            Object object = x$1;
            if (!(object instanceof Fail)) return false;
            boolean bl = true;
            if (!bl) return false;
            Fail fail = (Fail)x$1;
            if (this.logLevel() != fail.logLevel()) return false;
            if (!fail.canEqual(this)) return false;
            return true;
        }

        public Fail(int logLevel) {
            this.logLevel = logLevel;
            Product.$init$(this);
        }
    }
}

