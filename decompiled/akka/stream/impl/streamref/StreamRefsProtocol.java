/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.streamref;

import akka.actor.ActorRef;
import akka.actor.DeadLetterSuppression;
import akka.annotation.InternalApi;
import akka.stream.impl.ReactiveStreamsCompliance$;
import java.io.Serializable;
import scala.Product;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\rEe\u0001DA\u0001\u0003\u0007\u0001\n1%\t\u0002\u0010\u0005MqACBG\u0003\u0007A\t!a\u0004\u0002.\u0019Q\u0011\u0011AA\u0002\u0011\u0003\ty!a\n\t\u000f\u0005%\"\u0001\"\u0001\u0002,\u0019A\u0011\u0011\u0007\u0002C\u0003\u001f\t\u0019\u0004\u0003\u0006\u0002d\u0011\u0011)\u001a!C\u0001\u0003KB!\"!\u001c\u0005\u0005#\u0005\u000b\u0011BA4\u0011)\ty\u0007\u0002BK\u0002\u0013\u0005\u0011\u0011\u000f\u0005\u000b\u0003\u0013#!\u0011#Q\u0001\n\u0005M\u0004bBA\u0015\t\u0011\u0005\u00111\u0012\u0005\n\u0003+#\u0011\u0011!C\u0001\u0003/C\u0011\"!*\u0005#\u0003%\t!a*\t\u0013\u0005\u0005G!%A\u0005\u0002\u0005\r\u0007\"CAf\t\u0005\u0005I\u0011IAg\u0011%\ty\u000eBA\u0001\n\u0003\t\t\u000fC\u0005\u0002j\u0012\t\t\u0011\"\u0001\u0002l\"I\u0011\u0011\u001f\u0003\u0002\u0002\u0013\u0005\u00131\u001f\u0005\n\u0005\u0003!\u0011\u0011!C\u0001\u0005\u0007A\u0011B!\u0004\u0005\u0003\u0003%\tEa\u0004\t\u0013\tMA!!A\u0005B\tU\u0001\"\u0003B\f\t\u0005\u0005I\u0011\tB\r\u0011%\u0011Y\u0002BA\u0001\n\u0003\u0012ibB\u0006\u0003.\t\t\t\u0011#\u0001\u0002\u0010\t=baCA\u0019\u0005\u0005\u0005\t\u0012AA\b\u0005cAq!!\u000b\u0018\t\u0003\u0011i\u0004C\u0005\u0003\u0018]\t\t\u0011\"\u0012\u0003\u001a!I!qH\f\u0002\u0002\u0013\u0005%\u0011\t\u0005\n\u0005\u001f:\u0012\u0011!CA\u0005#B\u0011Ba\u001b\u0018\u0003\u0003%IA!\u001c\u0007\u0011\tU$AQA\b\u0005oB!B!\u001f\u001e\u0005+\u0007I\u0011\u0001B>\u0011)\u0011\u0019)\bB\tB\u0003%!Q\u0010\u0005\b\u0003SiB\u0011\u0001BC\u0011%\t)*HA\u0001\n\u0003\u0011Y\tC\u0005\u0002&v\t\n\u0011\"\u0001\u0003\u0010\"I\u00111Z\u000f\u0002\u0002\u0013\u0005\u0013Q\u001a\u0005\n\u0003?l\u0012\u0011!C\u0001\u0003CD\u0011\"!;\u001e\u0003\u0003%\tAa%\t\u0013\u0005EX$!A\u0005B\u0005M\b\"\u0003B\u0001;\u0005\u0005I\u0011\u0001BL\u0011%\u0011i!HA\u0001\n\u0003\u0012Y\nC\u0005\u0003\u0014u\t\t\u0011\"\u0011\u0003\u0016!I!qC\u000f\u0002\u0002\u0013\u0005#\u0011\u0004\u0005\n\u00057i\u0012\u0011!C!\u0005?;1B!*\u0003\u0003\u0003E\t!a\u0004\u0003(\u001aY!Q\u000f\u0002\u0002\u0002#\u0005\u0011q\u0002BU\u0011\u001d\tI#\fC\u0001\u0005oC\u0011Ba\u0006.\u0003\u0003%)E!\u0007\t\u0013\t}R&!A\u0005\u0002\ne\u0006\"\u0003B([\u0005\u0005I\u0011\u0011B_\u0011%\u0011Y'LA\u0001\n\u0013\u0011iG\u0002\u0005\u0003D\n\u0011\u0015q\u0002Bc\u0011)\u00119m\rBK\u0002\u0013\u0005!\u0011\u001a\u0005\u000b\u00053\u001c$\u0011#Q\u0001\n\t-\u0007bBA\u0015g\u0011\u0005!1\u001c\u0005\n\u0003+\u001b\u0014\u0011!C\u0001\u0005CD\u0011\"!*4#\u0003%\tA!:\t\u0013\u0005-7'!A\u0005B\u00055\u0007\"CApg\u0005\u0005I\u0011AAq\u0011%\tIoMA\u0001\n\u0003\u0011I\u000fC\u0005\u0002rN\n\t\u0011\"\u0011\u0002t\"I!\u0011A\u001a\u0002\u0002\u0013\u0005!Q\u001e\u0005\n\u0005\u001b\u0019\u0014\u0011!C!\u0005cD\u0011Ba\u00054\u0003\u0003%\tE!\u0006\t\u0013\t]1'!A\u0005B\te\u0001\"\u0003B\u000eg\u0005\u0005I\u0011\tB{\u000f-\u0011YPAA\u0001\u0012\u0003\tyA!@\u0007\u0017\t\r'!!A\t\u0002\u0005=!q \u0005\b\u0003S\u0019E\u0011AB\u0002\u0011%\u00119bQA\u0001\n\u000b\u0012I\u0002C\u0005\u0003@\r\u000b\t\u0011\"!\u0004\u0006!I!qJ\"\u0002\u0002\u0013\u00055\u0011\u0002\u0005\n\u0005W\u001a\u0015\u0011!C\u0005\u0005[2\u0001ba\u0004\u0003\u0005\u0006=1\u0011\u0003\u0005\u000b\u0003GJ%Q3A\u0005\u0002\u0005\u0015\u0004BCA7\u0013\nE\t\u0015!\u0003\u0002h!9\u0011\u0011F%\u0005\u0002\rM\u0001\"CAK\u0013\u0006\u0005I\u0011AB\r\u0011%\t)+SI\u0001\n\u0003\tI\u000bC\u0005\u0002L&\u000b\t\u0011\"\u0011\u0002N\"I\u0011q\\%\u0002\u0002\u0013\u0005\u0011\u0011\u001d\u0005\n\u0003SL\u0015\u0011!C\u0001\u0007;A\u0011\"!=J\u0003\u0003%\t%a=\t\u0013\t\u0005\u0011*!A\u0005\u0002\r\u0005\u0002\"\u0003B\u0007\u0013\u0006\u0005I\u0011IB\u0013\u0011%\u0011\u0019\"SA\u0001\n\u0003\u0012)\u0002C\u0005\u0003\u0018%\u000b\t\u0011\"\u0011\u0003\u001a!I!1D%\u0002\u0002\u0013\u00053\u0011F\u0004\f\u0007_\u0011\u0011\u0011!E\u0001\u0003\u001f\u0019\tDB\u0006\u0004\u0010\t\t\t\u0011#\u0001\u0002\u0010\rM\u0002bBA\u00153\u0012\u00051q\u0007\u0005\n\u0005/I\u0016\u0011!C#\u00053A\u0011Ba\u0010Z\u0003\u0003%\ti!\u000f\t\u0013\t=\u0013,!A\u0005\u0002\u000eu\u0002\"\u0003B63\u0006\u0005I\u0011\u0002B7\r!\u0019\u0019E\u0001\"\u0002\u0010\r\u0015\u0003BCA2?\nU\r\u0011\"\u0001\u0002f!Q\u0011QN0\u0003\u0012\u0003\u0006I!a\u001a\t\u000f\u0005%r\f\"\u0001\u0004H!I\u0011QS0\u0002\u0002\u0013\u00051Q\n\u0005\n\u0003K{\u0016\u0013!C\u0001\u0003SC\u0011\"a3`\u0003\u0003%\t%!4\t\u0013\u0005}w,!A\u0005\u0002\u0005\u0005\b\"CAu?\u0006\u0005I\u0011AB)\u0011%\t\tpXA\u0001\n\u0003\n\u0019\u0010C\u0005\u0003\u0002}\u000b\t\u0011\"\u0001\u0004V!I!QB0\u0002\u0002\u0013\u00053\u0011\f\u0005\n\u0005'y\u0016\u0011!C!\u0005+A\u0011Ba\u0006`\u0003\u0003%\tE!\u0007\t\u0013\tmq,!A\u0005B\rusaCB2\u0005\u0005\u0005\t\u0012AA\b\u0007K21ba\u0011\u0003\u0003\u0003E\t!a\u0004\u0004h!9\u0011\u0011F8\u0005\u0002\r-\u0004\"\u0003B\f_\u0006\u0005IQ\tB\r\u0011%\u0011yd\\A\u0001\n\u0003\u001bi\u0007C\u0005\u0003P=\f\t\u0011\"!\u0004r!I!1N8\u0002\u0002\u0013%!QN\u0004\n\u0007k\u0012\u0001\u0012QA\b\u0007o2\u0011\"!\n\u0003\u0011\u0003\u000bya! \t\u000f\u0005%b\u000f\"\u0001\u0004\u0000!I\u00111\u001a<\u0002\u0002\u0013\u0005\u0013Q\u001a\u0005\n\u0003?4\u0018\u0011!C\u0001\u0003CD\u0011\"!;w\u0003\u0003%\ta!!\t\u0013\u0005Eh/!A\u0005B\u0005M\b\"\u0003B\u0001m\u0006\u0005I\u0011ABC\u0011%\u0011\u0019B^A\u0001\n\u0003\u0012)\u0002C\u0005\u0003\u0018Y\f\t\u0011\"\u0011\u0003\u001a!I!1\u000e<\u0002\u0002\u0013%!Q\u000e\u0002\u0013'R\u0014X-Y7SK\u001a\u001c\bK]8u_\u000e|GN\u0003\u0003\u0002\u0006\u0005\u001d\u0011!C:ue\u0016\fWN]3g\u0015\u0011\tI!a\u0003\u0002\t%l\u0007\u000f\u001c\u0006\u0005\u0003\u001b\ty!\u0001\u0004tiJ,\u0017-\u001c\u0006\u0003\u0003#\tA!Y6lCN\u0019\u0001!!\u0006\u0011\t\u0005]\u0011QD\u0007\u0003\u00033Q!!a\u0007\u0002\u000bM\u001c\u0017\r\\1\n\t\u0005}\u0011\u0011\u0004\u0002\u0007\u0003:L(+\u001a4\u0004\u0001%:\u0001A^0\u001e\u0013N\"!aA!dWN\u0019!!!\u0006\u0002\rqJg.\u001b;?)\t\ti\u0003E\u0002\u00020\ti!!a\u0001\u0003\u001fM+\u0017/^3oG\u0016$wJ\u001c(fqR,B!!\u000e\u0002xMYA!!\u0006\u00028\u0005e\u0012QIA&!\r\ty\u0003\u0001\t\u0005\u0003w\t\t%\u0004\u0002\u0002>)!\u0011qHA\b\u0003\u0015\t7\r^8s\u0013\u0011\t\u0019%!\u0010\u0003+\u0011+\u0017\r\u001a'fiR,'oU;qaJ,7o]5p]B!\u0011qCA$\u0013\u0011\tI%!\u0007\u0003\u000fA\u0013x\u000eZ;diB!\u0011QJA/\u001d\u0011\ty%!\u0017\u000f\t\u0005E\u0013qK\u0007\u0003\u0003'RA!!\u0016\u0002\"\u00051AH]8pizJ!!a\u0007\n\t\u0005m\u0013\u0011D\u0001\ba\u0006\u001c7.Y4f\u0013\u0011\ty&!\u0019\u0003\u0019M+'/[1mSj\f'\r\\3\u000b\t\u0005m\u0013\u0011D\u0001\u0006g\u0016\fhJ]\u000b\u0003\u0003O\u0002B!a\u0006\u0002j%!\u00111NA\r\u0005\u0011auN\\4\u0002\rM,\u0017O\u0014:!\u0003\u001d\u0001\u0018-\u001f7pC\u0012,\"!a\u001d\u0011\t\u0005U\u0014q\u000f\u0007\u0001\t\u001d\tI\b\u0002b\u0001\u0003w\u0012\u0011\u0001V\t\u0005\u0003{\n\u0019\t\u0005\u0003\u0002\u0018\u0005}\u0014\u0002BAA\u00033\u0011qAT8uQ&tw\r\u0005\u0003\u0002\u0018\u0005\u0015\u0015\u0002BAD\u00033\u00111!\u00118z\u0003!\u0001\u0018-\u001f7pC\u0012\u0004CCBAG\u0003#\u000b\u0019\nE\u0003\u0002\u0010\u0012\t\u0019(D\u0001\u0003\u0011\u001d\t\u0019'\u0003a\u0001\u0003OBq!a\u001c\n\u0001\u0004\t\u0019(\u0001\u0003d_BLX\u0003BAM\u0003?#b!a'\u0002\"\u0006\r\u0006#BAH\t\u0005u\u0005\u0003BA;\u0003?#q!!\u001f\u000b\u0005\u0004\tY\bC\u0005\u0002d)\u0001\n\u00111\u0001\u0002h!I\u0011q\u000e\u0006\u0011\u0002\u0003\u0007\u0011QT\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00132+\u0011\tI+a0\u0016\u0005\u0005-&\u0006BA4\u0003[[#!a,\u0011\t\u0005E\u00161X\u0007\u0003\u0003gSA!!.\u00028\u0006IQO\\2iK\u000e\\W\r\u001a\u0006\u0005\u0003s\u000bI\"\u0001\u0006b]:|G/\u0019;j_:LA!!0\u00024\n\tRO\\2iK\u000e\\W\r\u001a,be&\fgnY3\u0005\u000f\u0005e4B1\u0001\u0002|\u0005q1m\u001c9zI\u0011,g-Y;mi\u0012\u0012T\u0003BAc\u0003\u0013,\"!a2+\t\u0005M\u0014Q\u0016\u0003\b\u0003sb!\u0019AA>\u00035\u0001(o\u001c3vGR\u0004&/\u001a4jqV\u0011\u0011q\u001a\t\u0005\u0003#\fY.\u0004\u0002\u0002T*!\u0011Q[Al\u0003\u0011a\u0017M\\4\u000b\u0005\u0005e\u0017\u0001\u00026bm\u0006LA!!8\u0002T\n11\u000b\u001e:j]\u001e\fA\u0002\u001d:pIV\u001cG/\u0011:jif,\"!a9\u0011\t\u0005]\u0011Q]\u0005\u0005\u0003O\fIBA\u0002J]R\fa\u0002\u001d:pIV\u001cG/\u00127f[\u0016tG\u000f\u0006\u0003\u0002\u0004\u00065\b\"CAx\u001f\u0005\u0005\t\u0019AAr\u0003\rAH%M\u0001\u0010aJ|G-^2u\u0013R,'/\u0019;peV\u0011\u0011Q\u001f\t\u0007\u0003o\fi0a!\u000e\u0005\u0005e(\u0002BA~\u00033\t!bY8mY\u0016\u001cG/[8o\u0013\u0011\ty0!?\u0003\u0011%#XM]1u_J\f\u0001bY1o\u000bF,\u0018\r\u001c\u000b\u0005\u0005\u000b\u0011Y\u0001\u0005\u0003\u0002\u0018\t\u001d\u0011\u0002\u0002B\u0005\u00033\u0011qAQ8pY\u0016\fg\u000eC\u0005\u0002pF\t\t\u00111\u0001\u0002\u0004\u0006\u0011\u0002O]8ek\u000e$X\t\\3nK:$h*Y7f)\u0011\tyM!\u0005\t\u0013\u0005=(#!AA\u0002\u0005\r\u0018\u0001\u00035bg\"\u001cu\u000eZ3\u0015\u0005\u0005\r\u0018\u0001\u0003;p'R\u0014\u0018N\\4\u0015\u0005\u0005=\u0017AB3rk\u0006d7\u000f\u0006\u0003\u0003\u0006\t}\u0001\"CAx+\u0005\u0005\t\u0019AABQ\r!!1\u0005\t\u0005\u0005K\u0011I#\u0004\u0002\u0003()!\u0011\u0011XA\b\u0013\u0011\u0011YCa\n\u0003\u0017%sG/\u001a:oC2\f\u0005/[\u0001\u0010'\u0016\fX/\u001a8dK\u0012|eNT3yiB\u0019\u0011qR\f\u0014\u000b]\t)Ba\r\u0011\t\tU\"1H\u0007\u0003\u0005oQAA!\u000f\u0002X\u0006\u0011\u0011n\\\u0005\u0005\u0003?\u00129\u0004\u0006\u0002\u00030\u0005)\u0011\r\u001d9msV!!1\tB%)\u0019\u0011)Ea\u0013\u0003NA)\u0011q\u0012\u0003\u0003HA!\u0011Q\u000fB%\t\u001d\tIH\u0007b\u0001\u0003wBq!a\u0019\u001b\u0001\u0004\t9\u0007C\u0004\u0002pi\u0001\rAa\u0012\u0002\u000fUt\u0017\r\u001d9msV!!1\u000bB2)\u0011\u0011)F!\u001a\u0011\r\u0005]!q\u000bB.\u0013\u0011\u0011I&!\u0007\u0003\r=\u0003H/[8o!!\t9B!\u0018\u0002h\t\u0005\u0014\u0002\u0002B0\u00033\u0011a\u0001V;qY\u0016\u0014\u0004\u0003BA;\u0005G\"q!!\u001f\u001c\u0005\u0004\tY\bC\u0005\u0003hm\t\t\u00111\u0001\u0003j\u0005\u0019\u0001\u0010\n\u0019\u0011\u000b\u0005=EA!\u0019\u0002\u0019]\u0014\u0018\u000e^3SKBd\u0017mY3\u0015\u0005\t=\u0004\u0003BAi\u0005cJAAa\u001d\u0002T\n1qJ\u00196fGR\u0014Ac\u00148Tk\n\u001c8M]5cK\"\u000bg\u000eZ:iC.,7cC\u000f\u0002\u0016\u0005]\u0012\u0011HA#\u0003\u0017\n\u0011\u0002^1sO\u0016$(+\u001a4\u0016\u0005\tu\u0004\u0003BA\u001e\u0005\u007fJAA!!\u0002>\tA\u0011i\u0019;peJ+g-\u0001\u0006uCJ<W\r\u001e*fM\u0002\"BAa\"\u0003\nB\u0019\u0011qR\u000f\t\u000f\te\u0004\u00051\u0001\u0003~Q!!q\u0011BG\u0011%\u0011I(\tI\u0001\u0002\u0004\u0011i(\u0006\u0002\u0003\u0012*\"!QPAW)\u0011\t\u0019I!&\t\u0013\u0005=X%!AA\u0002\u0005\rH\u0003\u0002B\u0003\u00053C\u0011\"a<(\u0003\u0003\u0005\r!a!\u0015\t\u0005='Q\u0014\u0005\n\u0003_D\u0013\u0011!a\u0001\u0003G$BA!\u0002\u0003\"\"I\u0011q^\u0016\u0002\u0002\u0003\u0007\u00111\u0011\u0015\u0004;\t\r\u0012\u0001F(o'V\u00147o\u0019:jE\u0016D\u0015M\u001c3tQ\u0006\\W\rE\u0002\u0002\u00106\u001aR!\fBV\u0005g\u0001\u0002B!,\u00034\nu$qQ\u0007\u0003\u0005_SAA!-\u0002\u001a\u00059!/\u001e8uS6,\u0017\u0002\u0002B[\u0005_\u0013\u0011#\u00112tiJ\f7\r\u001e$v]\u000e$\u0018n\u001c82)\t\u00119\u000b\u0006\u0003\u0003\b\nm\u0006b\u0002B=a\u0001\u0007!Q\u0010\u000b\u0005\u0005\u007f\u0013\t\r\u0005\u0004\u0002\u0018\t]#Q\u0010\u0005\n\u0005O\n\u0014\u0011!a\u0001\u0005\u000f\u00131CU3n_R,7\u000b\u001e:fC64\u0015-\u001b7ve\u0016\u001c\u0012bMA\u000b\u0003o\t)%a\u0013\u0002\u00075\u001cx-\u0006\u0002\u0003LB!!Q\u001aBk\u001d\u0011\u0011yM!5\u0011\t\u0005E\u0013\u0011D\u0005\u0005\u0005'\fI\"\u0001\u0004Qe\u0016$WMZ\u0005\u0005\u0003;\u00149N\u0003\u0003\u0003T\u0006e\u0011\u0001B7tO\u0002\"BA!8\u0003`B\u0019\u0011qR\u001a\t\u000f\t\u001dg\u00071\u0001\u0003LR!!Q\u001cBr\u0011%\u00119m\u000eI\u0001\u0002\u0004\u0011Y-\u0006\u0002\u0003h*\"!1ZAW)\u0011\t\u0019Ia;\t\u0013\u0005=8(!AA\u0002\u0005\rH\u0003\u0002B\u0003\u0005_D\u0011\"a<>\u0003\u0003\u0005\r!a!\u0015\t\u0005='1\u001f\u0005\n\u0003_t\u0014\u0011!a\u0001\u0003G$BA!\u0002\u0003x\"I\u0011q^!\u0002\u0002\u0003\u0007\u00111\u0011\u0015\u0004g\t\r\u0012a\u0005*f[>$Xm\u0015;sK\u0006lg)Y5mkJ,\u0007cAAH\u0007N)1i!\u0001\u00034AA!Q\u0016BZ\u0005\u0017\u0014i\u000e\u0006\u0002\u0003~R!!Q\\B\u0004\u0011\u001d\u00119M\u0012a\u0001\u0005\u0017$Baa\u0003\u0004\u000eA1\u0011q\u0003B,\u0005\u0017D\u0011Ba\u001aH\u0003\u0003\u0005\rA!8\u0003+I+Wn\u001c;f'R\u0014X-Y7D_6\u0004H.\u001a;fINI\u0011*!\u0006\u00028\u0005\u0015\u00131\n\u000b\u0005\u0007+\u00199\u0002E\u0002\u0002\u0010&Cq!a\u0019M\u0001\u0004\t9\u0007\u0006\u0003\u0004\u0016\rm\u0001\"CA2\u001bB\u0005\t\u0019AA4)\u0011\t\u0019ia\b\t\u0013\u0005=\u0018+!AA\u0002\u0005\rH\u0003\u0002B\u0003\u0007GA\u0011\"a<T\u0003\u0003\u0005\r!a!\u0015\t\u0005=7q\u0005\u0005\n\u0003_$\u0016\u0011!a\u0001\u0003G$BA!\u0002\u0004,!I\u0011q^,\u0002\u0002\u0003\u0007\u00111\u0011\u0015\u0004\u0013\n\r\u0012!\u0006*f[>$Xm\u0015;sK\u0006l7i\\7qY\u0016$X\r\u001a\t\u0004\u0003\u001fK6#B-\u00046\tM\u0002\u0003\u0003BW\u0005g\u000b9g!\u0006\u0015\u0005\rEB\u0003BB\u000b\u0007wAq!a\u0019]\u0001\u0004\t9\u0007\u0006\u0003\u0004@\r\u0005\u0003CBA\f\u0005/\n9\u0007C\u0005\u0003hu\u000b\t\u00111\u0001\u0004\u0016\t\u00012)^7vY\u0006$\u0018N^3EK6\fg\u000eZ\n\f?\u0006U\u0011qGA\u001d\u0003\u000b\nY\u0005\u0006\u0003\u0004J\r-\u0003cAAH?\"9\u00111\r2A\u0002\u0005\u001dD\u0003BB%\u0007\u001fB\u0011\"a\u0019d!\u0003\u0005\r!a\u001a\u0015\t\u0005\r51\u000b\u0005\n\u0003_<\u0017\u0011!a\u0001\u0003G$BA!\u0002\u0004X!I\u0011q^5\u0002\u0002\u0003\u0007\u00111\u0011\u000b\u0005\u0003\u001f\u001cY\u0006C\u0005\u0002p*\f\t\u00111\u0001\u0002dR!!QAB0\u0011%\ty/\\A\u0001\u0002\u0004\t\u0019\tK\u0002`\u0005G\t\u0001cQ;nk2\fG/\u001b<f\t\u0016l\u0017M\u001c3\u0011\u0007\u0005=unE\u0003p\u0007S\u0012\u0019\u0004\u0005\u0005\u0003.\nM\u0016qMB%)\t\u0019)\u0007\u0006\u0003\u0004J\r=\u0004bBA2e\u0002\u0007\u0011q\r\u000b\u0005\u0007\u007f\u0019\u0019\bC\u0005\u0003hM\f\t\u00111\u0001\u0004J\u0005\u0019\u0011iY6\u0011\u0007\u0005=e\u000fK\u0002v\u0005GA3A\u0001B\u0012'-1\u0018QCA\u001c\u0003s\t)%a\u0013\u0015\u0005\r]D\u0003BAB\u0007\u0007C\u0011\"a<{\u0003\u0003\u0005\r!a9\u0015\t\t\u00151q\u0011\u0005\n\u0003_d\u0018\u0011!a\u0001\u0003\u0007C3A\u001eB\u0012Q\r\u0001!1E\u0001\u0013'R\u0014X-Y7SK\u001a\u001c\bK]8u_\u000e|G\u000eK\u0002\u0002\u0005G\u0001")
@InternalApi
public interface StreamRefsProtocol {

    @InternalApi
    public static final class CumulativeDemand
    implements StreamRefsProtocol,
    DeadLetterSuppression,
    Product,
    Serializable {
        private final long seqNr;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public long seqNr() {
            return this.seqNr;
        }

        public CumulativeDemand copy(long seqNr) {
            return new CumulativeDemand(seqNr);
        }

        public long copy$default$1() {
            return this.seqNr();
        }

        @Override
        public String productPrefix() {
            return "CumulativeDemand";
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
                    return BoxesRunTime.boxToLong(this.seqNr());
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
            return x$1 instanceof CumulativeDemand;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "seqNr";
                }
            }
            return (String)Statics.ioobe(x$1);
        }

        public int hashCode() {
            int n = -889275714;
            n = Statics.mix(n, this.productPrefix().hashCode());
            n = Statics.mix(n, Statics.longHash(this.seqNr()));
            return Statics.finalizeHash(n, 1);
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
            if (!(object instanceof CumulativeDemand)) return false;
            boolean bl = true;
            if (!bl) return false;
            CumulativeDemand cumulativeDemand = (CumulativeDemand)x$1;
            if (this.seqNr() != cumulativeDemand.seqNr()) return false;
            return true;
        }

        public CumulativeDemand(long seqNr) {
            this.seqNr = seqNr;
            Product.$init$(this);
            if (seqNr <= 0L) {
                throw ReactiveStreamsCompliance$.MODULE$.numberOfElementsInRequestMustBePositiveException();
            }
        }
    }

    @InternalApi
    public static final class OnSubscribeHandshake
    implements StreamRefsProtocol,
    DeadLetterSuppression,
    Product,
    Serializable {
        private final ActorRef targetRef;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public ActorRef targetRef() {
            return this.targetRef;
        }

        public OnSubscribeHandshake copy(ActorRef targetRef) {
            return new OnSubscribeHandshake(targetRef);
        }

        public ActorRef copy$default$1() {
            return this.targetRef();
        }

        @Override
        public String productPrefix() {
            return "OnSubscribeHandshake";
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
                    return this.targetRef();
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
            return x$1 instanceof OnSubscribeHandshake;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "targetRef";
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
            if (!(object instanceof OnSubscribeHandshake)) return false;
            boolean bl = true;
            if (!bl) return false;
            OnSubscribeHandshake onSubscribeHandshake = (OnSubscribeHandshake)x$1;
            ActorRef actorRef2 = this.targetRef();
            ActorRef actorRef3 = onSubscribeHandshake.targetRef();
            if (actorRef2 != null) {
                if (!((Object)actorRef2).equals(actorRef3)) return false;
                return true;
            }
            if (actorRef3 == null) return true;
            return false;
        }

        public OnSubscribeHandshake(ActorRef targetRef) {
            this.targetRef = targetRef;
            Product.$init$(this);
        }
    }

    @InternalApi
    public static final class RemoteStreamCompleted
    implements StreamRefsProtocol,
    Product,
    Serializable {
        private final long seqNr;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public long seqNr() {
            return this.seqNr;
        }

        public RemoteStreamCompleted copy(long seqNr) {
            return new RemoteStreamCompleted(seqNr);
        }

        public long copy$default$1() {
            return this.seqNr();
        }

        @Override
        public String productPrefix() {
            return "RemoteStreamCompleted";
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
                    return BoxesRunTime.boxToLong(this.seqNr());
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
            return x$1 instanceof RemoteStreamCompleted;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "seqNr";
                }
            }
            return (String)Statics.ioobe(x$1);
        }

        public int hashCode() {
            int n = -889275714;
            n = Statics.mix(n, this.productPrefix().hashCode());
            n = Statics.mix(n, Statics.longHash(this.seqNr()));
            return Statics.finalizeHash(n, 1);
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
            if (!(object instanceof RemoteStreamCompleted)) return false;
            boolean bl = true;
            if (!bl) return false;
            RemoteStreamCompleted remoteStreamCompleted = (RemoteStreamCompleted)x$1;
            if (this.seqNr() != remoteStreamCompleted.seqNr()) return false;
            return true;
        }

        public RemoteStreamCompleted(long seqNr) {
            this.seqNr = seqNr;
            Product.$init$(this);
        }
    }

    @InternalApi
    public static final class RemoteStreamFailure
    implements StreamRefsProtocol,
    Product,
    Serializable {
        private final String msg;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public String msg() {
            return this.msg;
        }

        public RemoteStreamFailure copy(String msg) {
            return new RemoteStreamFailure(msg);
        }

        public String copy$default$1() {
            return this.msg();
        }

        @Override
        public String productPrefix() {
            return "RemoteStreamFailure";
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
                    return this.msg();
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
            return x$1 instanceof RemoteStreamFailure;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "msg";
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
            if (!(object instanceof RemoteStreamFailure)) return false;
            boolean bl = true;
            if (!bl) return false;
            RemoteStreamFailure remoteStreamFailure = (RemoteStreamFailure)x$1;
            String string = this.msg();
            String string2 = remoteStreamFailure.msg();
            if (string != null) {
                if (!string.equals(string2)) return false;
                return true;
            }
            if (string2 == null) return true;
            return false;
        }

        public RemoteStreamFailure(String msg) {
            this.msg = msg;
            Product.$init$(this);
        }
    }

    @InternalApi
    public static final class SequencedOnNext<T>
    implements StreamRefsProtocol,
    DeadLetterSuppression,
    Product,
    Serializable {
        private final long seqNr;
        private final T payload;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public long seqNr() {
            return this.seqNr;
        }

        public T payload() {
            return this.payload;
        }

        public <T> SequencedOnNext<T> copy(long seqNr, T payload) {
            return new SequencedOnNext<T>(seqNr, payload);
        }

        public <T> long copy$default$1() {
            return this.seqNr();
        }

        public <T> T copy$default$2() {
            return this.payload();
        }

        @Override
        public String productPrefix() {
            return "SequencedOnNext";
        }

        @Override
        public int productArity() {
            return 2;
        }

        @Override
        public Object productElement(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return BoxesRunTime.boxToLong(this.seqNr());
                }
                case 1: {
                    return this.payload();
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
            return x$1 instanceof SequencedOnNext;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "seqNr";
                }
                case 1: {
                    return "payload";
                }
            }
            return (String)Statics.ioobe(x$1);
        }

        public int hashCode() {
            int n = -889275714;
            n = Statics.mix(n, this.productPrefix().hashCode());
            n = Statics.mix(n, Statics.longHash(this.seqNr()));
            n = Statics.mix(n, Statics.anyHash(this.payload()));
            return Statics.finalizeHash(n, 2);
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
            if (!(object instanceof SequencedOnNext)) return false;
            boolean bl = true;
            if (!bl) return false;
            SequencedOnNext sequencedOnNext = (SequencedOnNext)x$1;
            if (this.seqNr() != sequencedOnNext.seqNr()) return false;
            if (!BoxesRunTime.equals(this.payload(), sequencedOnNext.payload())) return false;
            return true;
        }

        public SequencedOnNext(long seqNr, T payload) {
            this.seqNr = seqNr;
            this.payload = payload;
            Product.$init$(this);
            if (payload == null) {
                throw ReactiveStreamsCompliance$.MODULE$.elementMustNotBeNullException();
            }
        }
    }
}

