/*
 * Decompiled with CFR 0.152.
 */
package akka.actor;

import akka.actor.Deploy;
import akka.actor.Props;
import akka.actor.Props$;
import akka.actor.TypedProps$;
import akka.japi.Creator;
import akka.util.Reflect$;
import akka.util.Timeout;
import java.io.Serializable;
import scala.Function0;
import scala.Option;
import scala.Option$;
import scala.Product;
import scala.Tuple6;
import scala.collection.Iterator;
import scala.collection.immutable.Seq;
import scala.reflect.ClassTag;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\rux!\u0002#F\u0011\u0003Qe!\u0002'F\u0011\u0003i\u0005\"\u0002/\u0002\t\u0003i\u0006b\u00020\u0002\u0005\u0004%\ta\u0018\u0005\u0007W\u0006\u0001\u000b\u0011\u00021\t\u000f1\f!\u0019!C\u0001[\"1q/\u0001Q\u0001\n9Dq\u0001_\u0001C\u0002\u0013\u0005\u0011\u0010C\u0004\u0002\u0004\u0005\u0001\u000b\u0011\u0002>\t\u000f\u0005\u0015\u0011\u0001\"\u0001\u0002\b!9\u0011QI\u0001\u0005\u0002\u0005\u001d\u0003bBA#\u0003\u0011\u00051Q\u0001\u0005\b\u0003\u000b\nA\u0011AB\u0011\u0011\u001d\t)%\u0001C\u0001\u0007\u0003B\u0001\"!\u0012\u0002\t\u000395Q\f\u0005\n\u0003\u000b\n\u0011\u0011!CA\u0007wB\u0011ba(\u0002#\u0003%\ta!)\t\u0013\r\u0015\u0016!%A\u0005\u0002\r\u001d\u0006\"CBV\u0003E\u0005I\u0011ABW\u0011%\u0019\t,AI\u0001\n\u0003\u0019\u0019\fC\u0005\u00048\u0006\t\t\u0011\"!\u0004:\"Q11\\\u0001\u0012\u0002\u0013E!d!8\t\u0015\r\u0005\u0018!%A\u0005\u0012i\u0019\u0019\u000f\u0003\u0006\u0004h\u0006\t\n\u0011\"\u0005\u001b\u0007SD!b!<\u0002#\u0003%\tBGBx\u0011%\u0019\u00190AA\u0001\n\u0013\u0019)PB\u0003M\u000b\n\u000bi\u0005\u0003\u0006\u0002hi\u0011)\u001a!C\u0001\u0003SB!\"a\u001e\u001b\u0005#\u0005\u000b\u0011BA6\u0011)\tIH\u0007BK\u0002\u0013\u0005\u00111\u0010\u0005\u000b\u0003\u0017S\"\u0011#Q\u0001\n\u0005u\u0004\"CAG5\tU\r\u0011\"\u0001`\u0011%\tyI\u0007B\tB\u0003%\u0001\r\u0003\u0006\u0002\u0012j\u0011)\u001a!C\u0001\u0003'C!\"a'\u001b\u0005#\u0005\u000b\u0011BAK\u0011%\tiJ\u0007BK\u0002\u0013\u0005Q\u000eC\u0005\u0002 j\u0011\t\u0012)A\u0005]\"I\u0011\u0011\u0015\u000e\u0003\u0016\u0004%\t!\u001f\u0005\n\u0003GS\"\u0011#Q\u0001\niDq\u0001\u0018\u000e\u0005\u0012i\t)\u000b\u0003\u0004]5\u0011\u0005\u0011q\u0018\u0005\u00079j!\t!a2\t\rqSB\u0011AAt\u0011\u001d\t9P\u0007C\u0001\u0003sDq!a@\u001b\t\u0003\u0011\t\u0001C\u0004\u0003\u0006i!\tAa\u0002\t\u000f\t\u0015!\u0004\"\u0001\u0003\f!9!q\u0002\u000e\u0005\u0002\tE\u0001b\u0002B\b5\u0011\u0005!Q\u0003\u0005\b\u00053QB\u0011\u0001B\u000e\u0011\u001d\u0011IC\u0007C\u0001\u0005WAqA!\u000f\u001b\t\u0003\u0011Y\u0004C\u0005\u0003Di\t\t\u0011\"\u0001\u0003F!I!Q\f\u000e\u0012\u0002\u0013\u0005!q\f\u0005\n\u0005sR\u0012\u0013!C\u0001\u0005wB\u0011Ba!\u001b#\u0003%\tA!\"\t\u0013\t5%$%A\u0005\u0002\t=\u0005\"\u0003BL5E\u0005I\u0011\u0001BM\u0011%\u0011\tKGI\u0001\n\u0003\u0011\u0019\u000bC\u0005\u0003,j\t\t\u0011\"\u0011\u0003.\"I!1\u0017\u000e\u0002\u0002\u0013\u0005!Q\u0017\u0005\n\u0005{S\u0012\u0011!C\u0001\u0005\u007fC\u0011B!2\u001b\u0003\u0003%\tEa2\t\u0013\tE'$!A\u0005\u0002\tM\u0007\"\u0003Bo5\u0005\u0005I\u0011\tBp\u0011%\u0011\u0019OGA\u0001\n\u0003\u0012)\u000fC\u0005\u0003hj\t\t\u0011\"\u0011\u0003j\"I!1\u001e\u000e\u0002\u0002\u0013\u0005#Q^\u0001\u000b)f\u0004X\r\u001a)s_B\u001c(B\u0001$H\u0003\u0015\t7\r^8s\u0015\u0005A\u0015\u0001B1lW\u0006\u001c\u0001\u0001\u0005\u0002L\u00035\tQI\u0001\u0006UsB,G\r\u0015:paN\u001c2!\u0001(U!\ty%+D\u0001Q\u0015\u0005\t\u0016!B:dC2\f\u0017BA*Q\u0005\u0019\te.\u001f*fMB\u0011QKW\u0007\u0002-*\u0011q\u000bW\u0001\u0003S>T\u0011!W\u0001\u0005U\u00064\u0018-\u0003\u0002\\-\na1+\u001a:jC2L'0\u00192mK\u00061A(\u001b8jiz\"\u0012AS\u0001\u0014I\u00164\u0017-\u001e7u\t&\u001c\b/\u0019;dQ\u0016\u0014\u0018\nZ\u000b\u0002AB\u0011\u0011\r\u001b\b\u0003E\u001a\u0004\"a\u0019)\u000e\u0003\u0011T!!Z%\u0002\rq\u0012xn\u001c;?\u0013\t9\u0007+\u0001\u0004Qe\u0016$WMZ\u0005\u0003S*\u0014aa\u0015;sS:<'BA4Q\u0003Q!WMZ1vYR$\u0015n\u001d9bi\u000eDWM]%eA\u0005qA-\u001a4bk2$H+[7f_V$X#\u00018\u0011\u0007={\u0017/\u0003\u0002q!\n1q\n\u001d;j_:\u0004\"A];\u000e\u0003MT!\u0001^$\u0002\tU$\u0018\u000e\\\u0005\u0003mN\u0014q\u0001V5nK>,H/A\beK\u001a\fW\u000f\u001c;US6,w.\u001e;!\u00035!WMZ1vYRdu.\u00193feV\t!\u0010E\u0002P_n\u0004\"\u0001`@\u000e\u0003uT!A -\u0002\t1\fgnZ\u0005\u0004\u0003\u0003i(aC\"mCN\u001cHj\\1eKJ\fa\u0002Z3gCVdG\u000fT8bI\u0016\u0014\b%A\tfqR\u0014\u0018m\u0019;J]R,'OZ1dKN$B!!\u0003\u00028A1\u00111BA\u000b\u00033i!!!\u0004\u000b\t\u0005=\u0011\u0011C\u0001\nS6lW\u000f^1cY\u0016T1!a\u0005Q\u0003)\u0019w\u000e\u001c7fGRLwN\\\u0005\u0005\u0003/\tiAA\u0002TKF\u0004D!a\u0007\u0002&A)\u0011-!\b\u0002\"%\u0019\u0011q\u00046\u0003\u000b\rc\u0017m]:\u0011\t\u0005\r\u0012Q\u0005\u0007\u0001\t-\t9#CA\u0001\u0002\u0003\u0015\t!!\u000b\u0003\u0007}#\u0003(\u0005\u0003\u0002,\u0005E\u0002cA(\u0002.%\u0019\u0011q\u0006)\u0003\u000f9{G\u000f[5oOB\u0019q*a\r\n\u0007\u0005U\u0002KA\u0002B]fDq!!\u000f\n\u0001\u0004\tY$A\u0003dY\u0006T(\u0010\r\u0003\u0002>\u0005\u0005\u0003#B1\u0002\u001e\u0005}\u0002\u0003BA\u0012\u0003\u0003\"A\"a\u0011\u00028\u0005\u0005\t\u0011!B\u0001\u0003S\u00111a\u0018\u00138\u0003\u0015\t\u0007\u000f\u001d7z+\u0011\tIEa@\u0015\t\u0005-3\u0011\u0001\t\u0005\u0017j\u0011i0\u0006\u0003\u0002P\u0005\u00155C\u0002\u000eO\u0003#\n9\u0006E\u0002P\u0003'J1!!\u0016Q\u0005\u001d\u0001&o\u001c3vGR\u0004B!!\u0017\u0002d9!\u00111LA0\u001d\r\u0019\u0017QL\u0005\u0002#&\u0019\u0011\u0011\r)\u0002\u000fA\f7m[1hK&\u00191,!\u001a\u000b\u0007\u0005\u0005\u0004+\u0001\u0006j]R,'OZ1dKN,\"!a\u001b\u0011\r\u0005-\u0011QCA7a\u0011\ty'a\u001d\u0011\u000b\u0005\fi\"!\u001d\u0011\t\u0005\r\u00121\u000f\u0003\f\u0003kb\u0012\u0011!A\u0001\u0006\u0003\tIC\u0001\u0003`IE\u0012\u0014aC5oi\u0016\u0014h-Y2fg\u0002\nqa\u0019:fCR|'/\u0006\u0002\u0002~A)q*a \u0002\u0004&\u0019\u0011\u0011\u0011)\u0003\u0013\u0019+hn\u0019;j_:\u0004\u0004\u0003BA\u0012\u0003\u000b#q!a\"\u001b\u0005\u0004\tIIA\u0001U#\r\tYCT\u0001\tGJ,\u0017\r^8sA\u0005QA-[:qCR\u001c\u0007.\u001a:\u0002\u0017\u0011L7\u000f]1uG\",'\u000fI\u0001\u0007I\u0016\u0004Hn\\=\u0016\u0005\u0005U\u0005cA&\u0002\u0018&\u0019\u0011\u0011T#\u0003\r\u0011+\u0007\u000f\\8z\u0003\u001d!W\r\u001d7ps\u0002\nq\u0001^5nK>,H/\u0001\u0005uS6,w.\u001e;!\u0003\u0019aw.\u00193fe\u00069An\\1eKJ\u0004CCDAT\u0003S\u000b),a.\u0002:\u0006m\u0016Q\u0018\t\u0005\u0017j\t\u0019\tC\u0004\u0002h\u001d\u0002\r!a+\u0011\r\u0005-\u0011QCAWa\u0011\ty+a-\u0011\u000b\u0005\fi\"!-\u0011\t\u0005\r\u00121\u0017\u0003\r\u0003k\nI+!A\u0001\u0002\u000b\u0005\u0011\u0011\u0006\u0005\b\u0003s:\u0003\u0019AA?\u0011!\tii\nI\u0001\u0002\u0004\u0001\u0007\"CAIOA\u0005\t\u0019AAK\u0011!\tij\nI\u0001\u0002\u0004q\u0007\u0002CAQOA\u0005\t\u0019\u0001>\u0015\t\u0005\u001d\u0016\u0011\u0019\u0005\b\u0003\u0007D\u0003\u0019AAc\u00039IW\u000e\u001d7f[\u0016tG/\u0019;j_:\u0004R!YA\u000f\u0003\u0007#b!a*\u0002J\u0006e\u0007bBAfS\u0001\u0007\u0011QZ\u0001\nS:$XM\u001d4bG\u0016\u0004D!a4\u0002TB)\u0011-!\b\u0002RB!\u00111EAj\t1\t).!3\u0002\u0002\u0003\u0005)\u0011AAl\u0005\u0011yF%M\u001a\u0012\t\u0005\r\u0015\u0011\u0007\u0005\b\u0003\u0007L\u0003\u0019AAn!\u0019\ti.a9\u0002\u00046\u0011\u0011q\u001c\u0006\u0004\u0003C<\u0015\u0001\u00026ba&LA!!:\u0002`\n91I]3bi>\u0014HCBAT\u0003S\f)\u0010C\u0004\u0002L*\u0002\r!a;1\t\u00055\u0018\u0011\u001f\t\u0006C\u0006u\u0011q\u001e\t\u0005\u0003G\t\t\u0010\u0002\u0007\u0002t\u0006%\u0018\u0011!A\u0001\u0006\u0003\t9N\u0001\u0003`IE\"\u0004bBAbU\u0001\u0007\u0011QY\u0001\u000fo&$\b\u000eR5ta\u0006$8\r[3s)\u0011\t9+a?\t\r\u0005u8\u00061\u0001a\u0003\u0005!\u0017AC<ji\"$U\r\u001d7psR!\u0011q\u0015B\u0002\u0011\u001d\ti\u0010\fa\u0001\u0003+\u000b!b^5uQ2{\u0017\rZ3s)\u0011\t9K!\u0003\t\r\u0005\u0005V\u00061\u0001|)\u0011\t9K!\u0004\t\r\u0005\u0005f\u00061\u0001{\u0003-9\u0018\u000e\u001e5US6,w.\u001e;\u0015\t\u0005\u001d&1\u0003\u0005\u0007\u0003;{\u0003\u0019A9\u0015\t\u0005\u001d&q\u0003\u0005\u0007\u0003;\u0003\u0004\u0019\u00018\u0002\u001b]LG\u000f[%oi\u0016\u0014h-Y2f)\u0011\t9K!\b\t\u000f\u0005-\u0017\u00071\u0001\u0003 A\"!\u0011\u0005B\u0013!\u0015\t\u0017Q\u0004B\u0012!\u0011\t\u0019C!\n\u0005\u0019\t\u001d\"QDA\u0001\u0002\u0003\u0015\t!a6\u0003\t}#\u0013'N\u0001\u0011o&$\bn\\;u\u0013:$XM\u001d4bG\u0016$B!a*\u0003.!9\u00111\u001a\u001aA\u0002\t=\u0002\u0007\u0002B\u0019\u0005k\u0001R!YA\u000f\u0005g\u0001B!a\t\u00036\u0011a!q\u0007B\u0017\u0003\u0003\u0005\tQ!\u0001\u0002X\n!q\fJ\u00197\u0003)\t7\r^8s!J|\u0007o\u001d\u000b\u0003\u0005{\u00012a\u0013B \u0013\r\u0011\t%\u0012\u0002\u0006!J|\u0007o]\u0001\u0005G>\u0004\u00180\u0006\u0003\u0003H\t5CC\u0004B%\u0005\u001f\u0012\tF!\u0016\u0003X\te#1\f\t\u0005\u0017j\u0011Y\u0005\u0005\u0003\u0002$\t5CaBADi\t\u0007\u0011\u0011\u0012\u0005\n\u0003O\"\u0004\u0013!a\u0001\u0003WC\u0011\"!\u001f5!\u0003\u0005\rAa\u0015\u0011\u000b=\u000byHa\u0013\t\u0011\u00055E\u0007%AA\u0002\u0001D\u0011\"!%5!\u0003\u0005\r!!&\t\u0011\u0005uE\u0007%AA\u00029D\u0001\"!)5!\u0003\u0005\rA_\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00132+\u0011\u0011\tGa\u001e\u0016\u0005\t\r$\u0006BA6\u0005KZ#Aa\u001a\u0011\t\t%$1O\u0007\u0003\u0005WRAA!\u001c\u0003p\u0005IQO\\2iK\u000e\\W\r\u001a\u0006\u0004\u0005c\u0002\u0016AC1o]>$\u0018\r^5p]&!!Q\u000fB6\u0005E)hn\u00195fG.,GMV1sS\u0006t7-\u001a\u0003\b\u0003\u000f+$\u0019AAE\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uII*BA! \u0003\u0002V\u0011!q\u0010\u0016\u0005\u0003{\u0012)\u0007B\u0004\u0002\bZ\u0012\r!!#\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%gU!!q\u0011BF+\t\u0011IIK\u0002a\u0005K\"q!a\"8\u0005\u0004\tI)\u0001\bd_BLH\u0005Z3gCVdG\u000f\n\u001b\u0016\t\tE%QS\u000b\u0003\u0005'SC!!&\u0003f\u00119\u0011q\u0011\u001dC\u0002\u0005%\u0015AD2paf$C-\u001a4bk2$H%N\u000b\u0005\u00057\u0013y*\u0006\u0002\u0003\u001e*\u001aaN!\u001a\u0005\u000f\u0005\u001d\u0015H1\u0001\u0002\n\u0006q1m\u001c9zI\u0011,g-Y;mi\u00122T\u0003\u0002BS\u0005S+\"Aa*+\u0007i\u0014)\u0007B\u0004\u0002\bj\u0012\r!!#\u0002\u001bA\u0014x\u000eZ;diB\u0013XMZ5y+\t\u0011y\u000bE\u0002}\u0005cK!![?\u0002\u0019A\u0014x\u000eZ;di\u0006\u0013\u0018\u000e^=\u0016\u0005\t]\u0006cA(\u0003:&\u0019!1\u0018)\u0003\u0007%sG/\u0001\bqe>$Wo\u0019;FY\u0016lWM\u001c;\u0015\t\u0005E\"\u0011\u0019\u0005\n\u0005\u0007l\u0014\u0011!a\u0001\u0005o\u000b1\u0001\u001f\u00132\u0003=\u0001(o\u001c3vGRLE/\u001a:bi>\u0014XC\u0001Be!\u0019\u0011YM!4\u000225\u0011\u0011\u0011C\u0005\u0005\u0005\u001f\f\tB\u0001\u0005Ji\u0016\u0014\u0018\r^8s\u0003!\u0019\u0017M\\#rk\u0006dG\u0003\u0002Bk\u00057\u00042a\u0014Bl\u0013\r\u0011I\u000e\u0015\u0002\b\u0005>|G.Z1o\u0011%\u0011\u0019mPA\u0001\u0002\u0004\t\t$\u0001\nqe>$Wo\u0019;FY\u0016lWM\u001c;OC6,G\u0003\u0002BX\u0005CD\u0011Ba1A\u0003\u0003\u0005\rAa.\u0002\u0011!\f7\u000f[\"pI\u0016$\"Aa.\u0002\u0011Q|7\u000b\u001e:j]\u001e$\"Aa,\u0002\r\u0015\fX/\u00197t)\u0011\u0011)Na<\t\u0013\t\r7)!AA\u0002\u0005E\u0002f\u0002\u000e\u0003t\ne(1 \t\u0004\u001f\nU\u0018b\u0001B|!\n\u00012+\u001a:jC24VM]:j_:,\u0016\nR\u0001\u0006m\u0006dW/\u001a\u0010\u0002\u0003A!\u00111\u0005B\u0000\t\u001d\t9I\u0003b\u0001\u0003\u0013Cq!a1\u000b\u0001\u0004\u0019\u0019\u0001E\u0003b\u0003;\u0011i0\u0006\u0003\u0004\b\r5ACBB\u0005\u0007\u001f\u0019i\u0002\u0005\u0003L5\r-\u0001\u0003BA\u0012\u0007\u001b!q!a\"\f\u0005\u0004\tI\tC\u0004\u0002L.\u0001\ra!\u00051\t\rM1q\u0003\t\u0006C\u0006u1Q\u0003\t\u0005\u0003G\u00199\u0002\u0002\u0007\u0004\u001a\r=\u0011\u0011!A\u0001\u0006\u0003\u0019YBA\u0002`Ie\nBaa\u0003\u00022!9\u00111Y\u0006A\u0002\r}\u0001#B1\u0002\u001e\r-Q\u0003BB\u0012\u0007S!ba!\n\u0004,\re\u0002\u0003B&\u001b\u0007O\u0001B!a\t\u0004*\u00119\u0011q\u0011\u0007C\u0002\u0005%\u0005bBAf\u0019\u0001\u00071Q\u0006\u0019\u0005\u0007_\u0019\u0019\u0004E\u0003b\u0003;\u0019\t\u0004\u0005\u0003\u0002$\rMB\u0001DB\u001b\u0007W\t\t\u0011!A\u0003\u0002\r]\"\u0001B0%cA\nBaa\n\u00022!A\u0011\u0011\u0010\u0007\u0005\u0002\u0004\u0019Y\u0004E\u0003P\u0007{\u00199#C\u0002\u0004@A\u0013\u0001\u0002\u00102z]\u0006lWMP\u000b\u0005\u0007\u0007\u001aY\u0005\u0006\u0002\u0004FQ!1qIB'!\u0011Y%d!\u0013\u0011\t\u0005\r21\n\u0003\b\u0003\u000fk!\u0019AAE\u0011%\u0019y%DA\u0001\u0002\b\u0019\t&\u0001\u0006fm&$WM\\2fIE\u0002baa\u0015\u0004Z\r%SBAB+\u0015\r\u00199\u0006U\u0001\be\u00164G.Z2u\u0013\u0011\u0019Yf!\u0016\u0003\u0011\rc\u0017m]:UC\u001e,Baa\u0018\u0004fQ11\u0011MB4\u0007o\u0002Ba\u0013\u000e\u0004dA!\u00111EB3\t\u001d\t9I\u0004b\u0001\u0003\u0013Cq!a\u001a\u000f\u0001\u0004\u0019I\u0007\u0005\u0004\u0002\f\u0005U11\u000e\u0019\u0005\u0007[\u001a\t\bE\u0003b\u0003;\u0019y\u0007\u0005\u0003\u0002$\rED\u0001DB:\u0007k\n\t\u0011!A\u0003\u0002\u0005%\"\u0001B0%cEBq!a\u001a\u000f\u0001\u0004\u0019I\u0007\u0003\u0005\u0002z9!\t\u0019AB=!\u0015y5QHB2+\u0011\u0019iha!\u0015\u001d\r}4QQBJ\u0007/\u001bIja'\u0004\u001eB!1JGBA!\u0011\t\u0019ca!\u0005\u000f\u0005\u001duB1\u0001\u0002\n\"9\u0011qM\bA\u0002\r\u001d\u0005CBA\u0006\u0003+\u0019I\t\r\u0003\u0004\f\u000e=\u0005#B1\u0002\u001e\r5\u0005\u0003BA\u0012\u0007\u001f#A\"!\u001e\u0004\u0012\u0006\u0005\t\u0011!B\u0001\u0003SAq!a\u001a\u0010\u0001\u0004\u00199\tC\u0004\u0002z=\u0001\ra!&\u0011\u000b=\u000byh!!\t\u0011\u00055u\u0002%AA\u0002\u0001D\u0011\"!%\u0010!\u0003\u0005\r!!&\t\u0011\u0005uu\u0002%AA\u00029D\u0001\"!)\u0010!\u0003\u0005\rA_\u0001\u0010CB\u0004H.\u001f\u0013eK\u001a\fW\u000f\u001c;%gU!!qQBR\t\u001d\t9\t\u0005b\u0001\u0003\u0013\u000bq\"\u00199qYf$C-\u001a4bk2$H\u0005N\u000b\u0005\u0005#\u001bI\u000bB\u0004\u0002\bF\u0011\r!!#\u0002\u001f\u0005\u0004\b\u000f\\=%I\u00164\u0017-\u001e7uIU*BAa'\u00040\u00129\u0011q\u0011\nC\u0002\u0005%\u0015aD1qa2LH\u0005Z3gCVdG\u000f\n\u001c\u0016\t\t\u00156Q\u0017\u0003\b\u0003\u000f\u001b\"\u0019AAE\u0003\u001d)h.\u00199qYf,Baa/\u0004TR!1QXBk!\u0011yuna0\u0011\u0019=\u001b\tm!2\u0004P\u0002\f)J\u001c>\n\u0007\r\r\u0007K\u0001\u0004UkBdWM\u000e\t\u0007\u0003\u0017\t)ba21\t\r%7Q\u001a\t\u0006C\u0006u11\u001a\t\u0005\u0003G\u0019i\rB\u0006\u0002vQ\t\t\u0011!A\u0003\u0002\u0005%\u0002#B(\u0002\u0000\rE\u0007\u0003BA\u0012\u0007'$q!a\"\u0015\u0005\u0004\tI\tC\u0005\u0004XR\t\t\u00111\u0001\u0004Z\u0006\u0019\u0001\u0010\n\u0019\u0011\t-S2\u0011[\u0001\u001cI1,7o]5oSR$sM]3bi\u0016\u0014H\u0005Z3gCVdG\u000fJ\u001a\u0016\t\t\u001d5q\u001c\u0003\b\u0003\u000f+\"\u0019AAE\u0003m!C.Z:tS:LG\u000fJ4sK\u0006$XM\u001d\u0013eK\u001a\fW\u000f\u001c;%iU!!\u0011SBs\t\u001d\t9I\u0006b\u0001\u0003\u0013\u000b1\u0004\n7fgNLg.\u001b;%OJ,\u0017\r^3sI\u0011,g-Y;mi\u0012*T\u0003\u0002BN\u0007W$q!a\"\u0018\u0005\u0004\tI)A\u000e%Y\u0016\u001c8/\u001b8ji\u0012:'/Z1uKJ$C-\u001a4bk2$HEN\u000b\u0005\u0005K\u001b\t\u0010B\u0004\u0002\bb\u0011\r!!#\u0002\u0019]\u0014\u0018\u000e^3SKBd\u0017mY3\u0015\u0005\r]\bc\u0001?\u0004z&\u001911`?\u0003\r=\u0013'.Z2u\u0001")
public final class TypedProps<T>
implements Product,
Serializable {
    private static final long serialVersionUID = 1L;
    private final Seq<Class<?>> interfaces;
    private final Function0<T> creator;
    private final String dispatcher;
    private final Deploy deploy;
    private final Option<Timeout> timeout;
    private final Option<ClassLoader> loader;

    public static <T> Option<Tuple6<Seq<Class<?>>, Function0<T>, String, Deploy, Option<Timeout>, Option<ClassLoader>>> unapply(TypedProps<T> x$0) {
        return TypedProps$.MODULE$.unapply(x$0);
    }

    public static <T> Option<ClassLoader> apply$default$6() {
        return TypedProps$.MODULE$.apply$default$6();
    }

    public static <T> Option<Timeout> apply$default$5() {
        return TypedProps$.MODULE$.apply$default$5();
    }

    public static <T> Deploy apply$default$4() {
        return TypedProps$.MODULE$.apply$default$4();
    }

    public static <T> String apply$default$3() {
        return TypedProps$.MODULE$.apply$default$3();
    }

    public static <T> TypedProps<T> apply(Seq<Class<?>> interfaces, Function0<T> creator, String dispatcher, Deploy deploy, Option<Timeout> timeout2, Option<ClassLoader> loader) {
        return TypedProps$.MODULE$.apply(interfaces, creator, dispatcher, deploy, timeout2, loader);
    }

    public static <T> TypedProps<T> apply(ClassTag<T> evidence$1) {
        return TypedProps$.MODULE$.apply(evidence$1);
    }

    public static <T> TypedProps<T> apply(Class<? super T> clazz, Function0<T> creator) {
        return TypedProps$.MODULE$.apply(clazz, creator);
    }

    public static <T> TypedProps<T> apply(Class<? super T> clazz, Class<T> implementation) {
        return TypedProps$.MODULE$.apply(clazz, implementation);
    }

    public static <T> TypedProps<T> apply(Class<T> implementation) {
        return TypedProps$.MODULE$.apply(implementation);
    }

    public static Seq<Class<?>> extractInterfaces(Class<?> clazz) {
        return TypedProps$.MODULE$.extractInterfaces(clazz);
    }

    public static Option<ClassLoader> defaultLoader() {
        return TypedProps$.MODULE$.defaultLoader();
    }

    public static Option<Timeout> defaultTimeout() {
        return TypedProps$.MODULE$.defaultTimeout();
    }

    public static String defaultDispatcherId() {
        return TypedProps$.MODULE$.defaultDispatcherId();
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    public Seq<Class<?>> interfaces() {
        return this.interfaces;
    }

    public Function0<T> creator() {
        return this.creator;
    }

    public String dispatcher() {
        return this.dispatcher;
    }

    public Deploy deploy() {
        return this.deploy;
    }

    public Option<Timeout> timeout() {
        return this.timeout;
    }

    public Option<ClassLoader> loader() {
        return this.loader;
    }

    public TypedProps<T> withDispatcher(String d) {
        String x$1 = d;
        Seq<Class<?>> x$2 = this.copy$default$1();
        Function0<T> x$3 = this.copy$default$2();
        Deploy x$4 = this.copy$default$4();
        Option<Timeout> x$5 = this.copy$default$5();
        Option<ClassLoader> x$6 = this.copy$default$6();
        return this.copy(x$2, x$3, x$1, x$4, x$5, x$6);
    }

    public TypedProps<T> withDeploy(Deploy d) {
        Deploy x$1 = d;
        Seq<Class<?>> x$2 = this.copy$default$1();
        Function0<T> x$3 = this.copy$default$2();
        String x$4 = this.copy$default$3();
        Option<Timeout> x$5 = this.copy$default$5();
        Option<ClassLoader> x$6 = this.copy$default$6();
        return this.copy(x$2, x$3, x$4, x$1, x$5, x$6);
    }

    public TypedProps<T> withLoader(ClassLoader loader) {
        return this.withLoader(Option$.MODULE$.apply(loader));
    }

    public TypedProps<T> withLoader(Option<ClassLoader> loader) {
        Option<ClassLoader> x$1 = loader;
        Seq<Class<?>> x$2 = this.copy$default$1();
        Function0<T> x$3 = this.copy$default$2();
        String x$4 = this.copy$default$3();
        Deploy x$5 = this.copy$default$4();
        Option<Timeout> x$6 = this.copy$default$5();
        return this.copy(x$2, x$3, x$4, x$5, x$6, x$1);
    }

    public TypedProps<T> withTimeout(Timeout timeout2) {
        Option<Timeout> x$1 = Option$.MODULE$.apply(timeout2);
        Seq<Class<?>> x$2 = this.copy$default$1();
        Function0<T> x$3 = this.copy$default$2();
        String x$4 = this.copy$default$3();
        Deploy x$5 = this.copy$default$4();
        Option<ClassLoader> x$6 = this.copy$default$6();
        return this.copy(x$2, x$3, x$4, x$5, x$1, x$6);
    }

    public TypedProps<T> withTimeout(Option<Timeout> timeout2) {
        Option<Timeout> x$1 = timeout2;
        Seq<Class<?>> x$2 = this.copy$default$1();
        Function0<T> x$3 = this.copy$default$2();
        String x$4 = this.copy$default$3();
        Deploy x$5 = this.copy$default$4();
        Option<ClassLoader> x$6 = this.copy$default$6();
        return this.copy(x$2, x$3, x$4, x$5, x$1, x$6);
    }

    public TypedProps<T> withInterface(Class<? super T> clazz) {
        return this.copy((Seq)this.interfaces().$plus$plus(TypedProps$.MODULE$.extractInterfaces(clazz)), this.copy$default$2(), this.copy$default$3(), this.copy$default$4(), this.copy$default$5(), this.copy$default$6());
    }

    public TypedProps<T> withoutInterface(Class<? super T> clazz) {
        return this.copy((Seq)this.interfaces().diff(TypedProps$.MODULE$.extractInterfaces(clazz)), this.copy$default$2(), this.copy$default$3(), this.copy$default$4(), this.copy$default$5(), this.copy$default$6());
    }

    public Props actorProps() {
        String string = this.dispatcher();
        String string2 = Props$.MODULE$.default().dispatcher();
        if (!(string != null ? !string.equals(string2) : string2 != null)) {
            return Props$.MODULE$.default().withDeploy(this.deploy());
        }
        return Props$.MODULE$.default().withDispatcher(this.dispatcher()).withDeploy(this.deploy());
    }

    public <T> TypedProps<T> copy(Seq<Class<?>> interfaces, Function0<T> creator, String dispatcher, Deploy deploy, Option<Timeout> timeout2, Option<ClassLoader> loader) {
        return new TypedProps<T>(interfaces, creator, dispatcher, deploy, timeout2, loader);
    }

    public <T> Seq<Class<?>> copy$default$1() {
        return this.interfaces();
    }

    public <T> Function0<T> copy$default$2() {
        return this.creator();
    }

    public <T> String copy$default$3() {
        return this.dispatcher();
    }

    public <T> Deploy copy$default$4() {
        return this.deploy();
    }

    public <T> Option<Timeout> copy$default$5() {
        return this.timeout();
    }

    public <T> Option<ClassLoader> copy$default$6() {
        return this.loader();
    }

    @Override
    public String productPrefix() {
        return "TypedProps";
    }

    @Override
    public int productArity() {
        return 6;
    }

    @Override
    public Object productElement(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return this.interfaces();
            }
            case 1: {
                return this.creator();
            }
            case 2: {
                return this.dispatcher();
            }
            case 3: {
                return this.deploy();
            }
            case 4: {
                return this.timeout();
            }
            case 5: {
                return this.loader();
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
        return x$1 instanceof TypedProps;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "interfaces";
            }
            case 1: {
                return "creator";
            }
            case 2: {
                return "dispatcher";
            }
            case 3: {
                return "deploy";
            }
            case 4: {
                return "timeout";
            }
            case 5: {
                return "loader";
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
        if (!(object instanceof TypedProps)) return false;
        boolean bl = true;
        if (!bl) return false;
        TypedProps typedProps = (TypedProps)x$1;
        Seq<Class<?>> seq = this.interfaces();
        Seq<Class<?>> seq2 = typedProps.interfaces();
        if (seq == null) {
            if (seq2 != null) {
                return false;
            }
        } else if (!seq.equals(seq2)) return false;
        Function0<T> function0 = this.creator();
        Function0<T> function02 = typedProps.creator();
        if (function0 == null) {
            if (function02 != null) {
                return false;
            }
        } else if (!function0.equals(function02)) return false;
        String string = this.dispatcher();
        String string2 = typedProps.dispatcher();
        if (string == null) {
            if (string2 != null) {
                return false;
            }
        } else if (!string.equals(string2)) return false;
        Deploy deploy = this.deploy();
        Deploy deploy2 = typedProps.deploy();
        if (deploy == null) {
            if (deploy2 != null) {
                return false;
            }
        } else if (!((Object)deploy).equals(deploy2)) return false;
        Option<Timeout> option = this.timeout();
        Option<Timeout> option2 = typedProps.timeout();
        if (option == null) {
            if (option2 != null) {
                return false;
            }
        } else if (!option.equals(option2)) return false;
        Option<ClassLoader> option3 = this.loader();
        Option<ClassLoader> option4 = typedProps.loader();
        if (option3 == null) {
            if (option4 == null) return true;
            return false;
        } else {
            if (!option3.equals(option4)) return false;
            return true;
        }
    }

    public TypedProps(Seq<Class<?>> interfaces, Function0<T> creator, String dispatcher, Deploy deploy, Option<Timeout> timeout2, Option<ClassLoader> loader) {
        this.interfaces = interfaces;
        this.creator = creator;
        this.dispatcher = dispatcher;
        this.deploy = deploy;
        this.timeout = timeout2;
        this.loader = loader;
        Product.$init$(this);
    }

    public TypedProps(Class<T> implementation) {
        this(TypedProps$.MODULE$.extractInterfaces(implementation), Reflect$.MODULE$.instantiator(implementation), TypedProps$.MODULE$.$lessinit$greater$default$3(), TypedProps$.MODULE$.$lessinit$greater$default$4(), TypedProps$.MODULE$.$lessinit$greater$default$5(), TypedProps$.MODULE$.$lessinit$greater$default$6());
    }

    public TypedProps(Class<? super T> clazz, Creator<T> implementation) {
        this(TypedProps$.MODULE$.extractInterfaces(clazz), (Function0<T>)((Object)new Serializable(implementation){
            private static final long serialVersionUID = 0L;
            private final Creator implementation$1;

            public final T apply() {
                return this.implementation$1.create();
            }
            {
                this.implementation$1 = implementation$1;
            }
        }), TypedProps$.MODULE$.$lessinit$greater$default$3(), TypedProps$.MODULE$.$lessinit$greater$default$4(), TypedProps$.MODULE$.$lessinit$greater$default$5(), TypedProps$.MODULE$.$lessinit$greater$default$6());
    }

    public TypedProps(Class<? super T> clazz, Class<T> implementation) {
        this(TypedProps$.MODULE$.extractInterfaces(clazz), Reflect$.MODULE$.instantiator(implementation), TypedProps$.MODULE$.$lessinit$greater$default$3(), TypedProps$.MODULE$.$lessinit$greater$default$4(), TypedProps$.MODULE$.$lessinit$greater$default$5(), TypedProps$.MODULE$.$lessinit$greater$default$6());
    }
}

