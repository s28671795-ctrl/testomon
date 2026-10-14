/*
 * Decompiled with CFR 0.152.
 */
package scala.math;

import java.math.BigInteger;
import scala.Tuple2;
import scala.collection.immutable.NumericRange;
import scala.math.BigDecimal;
import scala.math.BigInt$;
import scala.math.Numeric$BigIntIsIntegral$;
import scala.math.Ordered;
import scala.math.ScalaNumber;
import scala.math.ScalaNumericAnyConversions;
import scala.math.ScalaNumericConversions;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.Statics;
import scala.util.Random;

@ScalaSignature(bytes="\u0006\u0005\r%u!\u00022d\u0011\u0003Ag!\u00026d\u0011\u0003Y\u0007\"\u0002=\u0002\t\u0003I\bb\u0002>\u0002\u0005\u0004%Ia\u001f\u0005\b\u0003\u0007\t\u0001\u0015!\u0003}\u0011%\t)!\u0001b\u0001\n\u0013\t9\u0001\u0003\u0005\u0003v\u0006\u0001\u000b\u0011BA\u0005\u0011!\u001190\u0001Q\u0001\n\u0005\r\u0004\u0002\u0003B}\u0003\u0001\u0006I!a\u0019\t\u0011\tm\u0018\u0001)A\u0005\u0005{D\u0001Ba@\u0002A\u0013%1\u0011\u0001\u0005\t\u0007\u000f\t!\u0019!C\u0005w\"91\u0011B\u0001!\u0002\u0013a\bbBB\u0006\u0003\u0011\u00051Q\u0002\u0005\b\u0007\u0017\tA\u0011AB\t\u0011\u001d\u0019Y!\u0001C\u0001\u0007/Aqaa\u0003\u0002\t\u0003\u0019i\u0002C\u0004\u0004\f\u0005!\ta!\n\t\u000f\r-\u0011\u0001\"\u0001\u0004>!911B\u0001\u0005\u0002\r\u0015\u0003bBB\u0006\u0003\u0011\u00051\u0011\n\u0005\b\u0007\u0017\tA\u0011AB(\u0011\u001d\u0019\u0019&\u0001C\u0001\u0007+Bqaa\u0017\u0002\t\u0007\u0019i\u0006C\u0004\u0004b\u0005!\u0019aa\u0019\t\u000f\r\u001d\u0014\u0001b\u0001\u0004j!91QN\u0001\u0005\n\r=\u0004\"CB=\u0003\u0005\u0005I\u0011BB>\r\u0015Q7MAA\u0006\u0011%\tY\u0003\bBA\u0002\u0013%1\u0010\u0003\u0006\u0002.q\u0011\t\u0019!C\u0005\u0003_A\u0011\"a\u000f\u001d\u0005\u0003\u0005\u000b\u0015\u0002?\t\u0015\u0005uBD!b\u0001\n\u0013\ty\u0004\u0003\u0006\u0002Hq\u0011\t\u0011)A\u0005\u0003\u0003Ba\u0001\u001f\u000f\u0005\n\u0005%\u0003B\u0002=\u001d\t\u0003\ty\u0005C\u0004\u0002Vq!I!a\u0016\t\r\u0005MC\u0004\"\u0001|\u0011\u001d\ty\u0006\bC!\u0003CBq!!\u001b\u001d\t\u0003\nY\u0007C\u0004\u0002xq!\t%a\u0016\t\u000f\u0005eD\u0004\"\u0011\u0002X!9\u00111\u0010\u000f\u0005B\u0005]\u0003bBA?9\u0011\u0005\u0013q\u000b\u0005\b\u0003\u007fbB\u0011AA,\u0011\u001d\t\t\t\bC\u0001\u0003/Bq!a!\u001d\t\u0003\t9\u0006C\u0004\u0002\u0006r!I!a\u0016\t\u000f\u0005\u001dE\u0004\"\u0001\u0002X!9\u0011\u0011\u0012\u000f\u0005\u0002\u0005-\u0005bBA59\u0011\u0005\u0011Q\u0012\u0005\b\u0003#cB\u0011AAJ\u0011\u001d\t9\n\bC\u0001\u00033Cq!!(\u001d\t\u0003\ty\nC\u0004\u0002$r!\t!!*\t\u000f\u0005%F\u0004\"\u0001\u0002,\"9\u0011q\u0016\u000f\u0005\u0002\u0005E\u0006bBA[9\u0011\u0005\u0011q\u0017\u0005\b\u0003\u0003dB\u0011AAb\u0011\u001d\tI\r\bC\u0001\u0003\u0017Dq!a4\u001d\t\u0003\t\t\u000eC\u0004\u0002Vr!\t!a6\t\u000f\u0005mG\u0004\"\u0001\u0002^\"9\u0011\u0011\u001d\u000f\u0005\u0002\u0005\r\bbBAt9\u0011\u0005\u0011\u0011\u001e\u0005\b\u0003[dB\u0011AAx\u0011\u001d\t\u0019\u0010\bC\u0001\u0003kDq!!?\u001d\t\u0003\tY\u0010C\u0004\u0002\u0000r!\tA!\u0001\t\u000f\t\u001dA\u0004\"\u0001\u0003\n!9!\u0011\u0003\u000f\u0005\u0002\tM\u0001b\u0002B\f9\u0011\u0005\u0011q\u0001\u0005\b\u00053aB\u0011AA\u0004\u0011\u001d\u0011Y\u0002\bC\u0001\u0005;AqAa\b\u001d\t\u0003\t9\u0001C\u0004\u0003\"q!\t!a\u0002\t\u000f\t\rB\u0004\"\u0001\u0003&!9!\u0011\u0006\u000f\u0005\u0002\t-\u0002b\u0002B\u00189\u0011\u0005!\u0011\u0007\u0005\b\u0005kaB\u0011\u0001B\u001c\u0011\u001d\u0011Y\u0004\bC\u0001\u0005;AqA!\u0010\u001d\t\u0003\u0011i\u0002C\u0004\u0003@q!\tA!\b\t\u000f\t\u0005C\u0004\"\u0001\u0003D!9!\u0011\n\u000f\u0005B\t-\u0003b\u0002B*9\u0011\u0005#Q\u000b\u0005\b\u0005;bB\u0011\u0001B0\u0011\u001d\u00119\u0007\bC\u0001\u0005;AqA!\u001b\u001d\t\u0003\ty\u0004C\u0004\u0003lq!\tA!\u001c\t\u000f\tUD\u0004\"\u0001\u0003x!9!q\u0010\u000f\u0005\u0002\t\u0005\u0005\"\u0003BR9E\u0005I\u0011\u0001BS\u0011\u001d\u0011Y\f\bC\u0001\u0005{C\u0011B!3\u001d#\u0003%\tA!*\t\u000f\t-G\u0004\"\u0011\u0003N\"9!1\u001a\u000f\u0005\u0002\t\u0015\bb\u0002Bv9\u0011\u0005!Q^\u0001\u0007\u0005&<\u0017J\u001c;\u000b\u0005\u0011,\u0017\u0001B7bi\"T\u0011AZ\u0001\u0006g\u000e\fG.Y\u0002\u0001!\tI\u0017!D\u0001d\u0005\u0019\u0011\u0015nZ%oiN\u0019\u0011\u0001\u001c9\u0011\u00055tW\"A3\n\u0005=,'AB!osJ+g\r\u0005\u0002rm6\t!O\u0003\u0002ti\u0006\u0011\u0011n\u001c\u0006\u0002k\u0006!!.\u0019<b\u0013\t9(O\u0001\u0007TKJL\u0017\r\\5{C\ndW-\u0001\u0004=S:LGO\u0010\u000b\u0002Q\u00061Bn\u001c8h\u001b&tg+\u00197vK\nKw-\u00138uK\u001e,'/F\u0001}!\tix0D\u0001\u007f\u0015\t!G/C\u0002\u0002\u0002y\u0014!BQ5h\u0013:$XmZ3s\u0003]awN\\4NS:4\u0016\r\\;f\u0005&<\u0017J\u001c;fO\u0016\u0014\b%\u0001\u0007m_:<W*\u001b8WC2,X-\u0006\u0002\u0002\nA\u0011\u0011\u000eH\n\n9\u00055\u00111CA\r\u0003K\u00012![A\b\u0013\r\t\tb\u0019\u0002\f'\u000e\fG.\u0019(v[\n,'\u000fE\u0002j\u0003+I1!a\u0006d\u0005]\u00196-\u00197b\u001dVlWM]5d\u0007>tg/\u001a:tS>t7\u000f\u0005\u0003\u0002\u001c\u0005\u0005bbA7\u0002\u001e%\u0019\u0011qD3\u0002\u000fA\f7m[1hK&\u0019q/a\t\u000b\u0007\u0005}Q\rE\u0003j\u0003O\tI!C\u0002\u0002*\r\u0014qa\u0014:eKJ,G-A\u0006`E&<\u0017J\u001c;fO\u0016\u0014\u0018aD0cS\u001eLe\u000e^3hKJ|F%Z9\u0015\t\u0005E\u0012q\u0007\t\u0004[\u0006M\u0012bAA\u001bK\n!QK\\5u\u0011!\tIDHA\u0001\u0002\u0004a\u0018a\u0001=%c\u0005aqLY5h\u0013:$XmZ3sA\u0005)q\f\\8oOV\u0011\u0011\u0011\t\t\u0004[\u0006\r\u0013bAA#K\n!Aj\u001c8h\u0003\u0019yFn\u001c8hAQ1\u0011\u0011BA&\u0003\u001bBa!a\u000b#\u0001\u0004a\bbBA\u001fE\u0001\u0007\u0011\u0011\t\u000b\u0005\u0003\u0013\t\t\u0006\u0003\u0004\u0002T\r\u0002\r\u0001`\u0001\u000bE&<\u0017J\u001c;fO\u0016\u0014\u0018\u0001\u00047p]\u001e,enY8eS:<WCAA-!\ri\u00171L\u0005\u0004\u0003;*'a\u0002\"p_2,\u0017M\\\u0001\tQ\u0006\u001c\bnQ8eKR\u0011\u00111\r\t\u0004[\u0006\u0015\u0014bAA4K\n\u0019\u0011J\u001c;\u0002\r\u0015\fX/\u00197t)\u0011\tI&!\u001c\t\u000f\u0005=t\u00051\u0001\u0002r\u0005!A\u000f[1u!\ri\u00171O\u0005\u0004\u0003k*'aA!os\u0006Y\u0011n\u001d,bY&$')\u001f;f\u00031I7OV1mS\u0012\u001c\u0006n\u001c:u\u0003-I7OV1mS\u0012\u001c\u0005.\u0019:\u0002\u0015%\u001ch+\u00197jI&sG/A\u0006jgZ\u000bG.\u001b3M_:<\u0017\u0001D5t-\u0006d\u0017\u000e\u001a$m_\u0006$\u0018!D5t-\u0006d\u0017\u000e\u001a#pk\ndW-A\tcSRdUM\\4uQ>3XM\u001d4m_^\fq![:XQ>dW-\u0001\u0006v]\u0012,'\u000f\\=j]\u001e$\u0012\u0001 \u000b\u0005\u00033\ny\tC\u0004\u0002pI\u0002\r!!\u0003\u0002\u000f\r|W\u000e]1sKR!\u00111MAK\u0011\u001d\tyg\ra\u0001\u0003\u0013\tQ\u0001\n9mkN$B!!\u0003\u0002\u001c\"9\u0011q\u000e\u001bA\u0002\u0005%\u0011A\u0002\u0013nS:,8\u000f\u0006\u0003\u0002\n\u0005\u0005\u0006bBA8k\u0001\u0007\u0011\u0011B\u0001\u0007IQLW.Z:\u0015\t\u0005%\u0011q\u0015\u0005\b\u0003_2\u0004\u0019AA\u0005\u0003\u0011!C-\u001b<\u0015\t\u0005%\u0011Q\u0016\u0005\b\u0003_:\u0004\u0019AA\u0005\u0003!!\u0003/\u001a:dK:$H\u0003BA\u0005\u0003gCq!a\u001c9\u0001\u0004\tI!\u0001\u0007%I&4H\u0005]3sG\u0016tG\u000f\u0006\u0003\u0002:\u0006}\u0006cB7\u0002<\u0006%\u0011\u0011B\u0005\u0004\u0003{+'A\u0002+va2,'\u0007C\u0004\u0002pe\u0002\r!!\u0003\u0002\u0015\u0011bWm]:%Y\u0016\u001c8\u000f\u0006\u0003\u0002\n\u0005\u0015\u0007bBAdu\u0001\u0007\u00111M\u0001\u0002]\u0006\u0001Be\u001a:fCR,'\u000fJ4sK\u0006$XM\u001d\u000b\u0005\u0003\u0013\ti\rC\u0004\u0002Hn\u0002\r!a\u0019\u0002\t\u0011\nW\u000e\u001d\u000b\u0005\u0003\u0013\t\u0019\u000eC\u0004\u0002pq\u0002\r!!\u0003\u0002\t\u0011\u0012\u0017M\u001d\u000b\u0005\u0003\u0013\tI\u000eC\u0004\u0002pu\u0002\r!!\u0003\u0002\u0007\u0011*\b\u000f\u0006\u0003\u0002\n\u0005}\u0007bBA8}\u0001\u0007\u0011\u0011B\u0001\u000bI\u0005l\u0007\u000f\n;jY\u0012,G\u0003BA\u0005\u0003KDq!a\u001c@\u0001\u0004\tI!A\u0002hG\u0012$B!!\u0003\u0002l\"9\u0011q\u000e!A\u0002\u0005%\u0011aA7pIR!\u0011\u0011BAy\u0011\u001d\ty'\u0011a\u0001\u0003\u0013\t1!\\5o)\u0011\tI!a>\t\u000f\u0005=$\t1\u0001\u0002\n\u0005\u0019Q.\u0019=\u0015\t\u0005%\u0011Q \u0005\b\u0003_\u001a\u0005\u0019AA\u0005\u0003\r\u0001xn\u001e\u000b\u0005\u0003\u0013\u0011\u0019\u0001C\u0004\u0003\u0006\u0011\u0003\r!a\u0019\u0002\u0007\u0015D\b/\u0001\u0004n_\u0012\u0004vn\u001e\u000b\u0007\u0003\u0013\u0011YA!\u0004\t\u000f\t\u0015Q\t1\u0001\u0002\n!9!qB#A\u0002\u0005%\u0011!A7\u0002\u00155|G-\u00138wKJ\u001cX\r\u0006\u0003\u0002\n\tU\u0001b\u0002B\b\r\u0002\u0007\u0011\u0011B\u0001\rk:\f'/_0%[&tWo]\u0001\u0004C\n\u001c\u0018AB:jO:,X.\u0006\u0002\u0002d\u0005!1/[4o\u00031)h.\u0019:z?\u0012\"\u0018\u000e\u001c3f\u0003\u001d!Xm\u001d;CSR$B!!\u0017\u0003(!9\u0011q\u0019'A\u0002\u0005\r\u0014AB:fi\nKG\u000f\u0006\u0003\u0002\n\t5\u0002bBAd\u001b\u0002\u0007\u00111M\u0001\tG2,\u0017M\u001d\"jiR!\u0011\u0011\u0002B\u001a\u0011\u001d\t9M\u0014a\u0001\u0003G\nqA\u001a7ja\nKG\u000f\u0006\u0003\u0002\n\te\u0002bBAd\u001f\u0002\u0007\u00111M\u0001\rY><Xm\u001d;TKR\u0014\u0015\u000e^\u0001\nE&$H*\u001a8hi\"\f\u0001BY5u\u0007>,h\u000e^\u0001\u0010SN\u0004&o\u001c2bE2,\u0007K]5nKR!\u0011\u0011\fB#\u0011\u001d\u00119e\u0015a\u0001\u0003G\n\u0011bY3si\u0006Lg\u000e^=\u0002\u0013\tLH/\u001a,bYV,WC\u0001B'!\ri'qJ\u0005\u0004\u0005#*'\u0001\u0002\"zi\u0016\f!b\u001d5peR4\u0016\r\\;f+\t\u00119\u0006E\u0002n\u00053J1Aa\u0017f\u0005\u0015\u0019\u0006n\u001c:u\u0003%\u0019\u0007.\u0019:WC2,X-\u0006\u0002\u0003bA\u0019QNa\u0019\n\u0007\t\u0015TM\u0001\u0003DQ\u0006\u0014\u0018\u0001C5oiZ\u000bG.^3\u0002\u00131|gn\u001a,bYV,\u0017A\u00034m_\u0006$h+\u00197vKV\u0011!q\u000e\t\u0004[\nE\u0014b\u0001B:K\n)a\t\\8bi\u0006YAm\\;cY\u00164\u0016\r\\;f+\t\u0011I\bE\u0002n\u0005wJ1A! f\u0005\u0019!u.\u001e2mK\u0006)QO\u001c;jYR1!1\u0011BN\u0005?\u0003bA!\"\u0003\u0016\u0006%a\u0002\u0002BD\u0005#k!A!#\u000b\t\t-%QR\u0001\nS6lW\u000f^1cY\u0016T1Aa$f\u0003)\u0019w\u000e\u001c7fGRLwN\\\u0005\u0005\u0005'\u0013I)\u0001\u0007Ok6,'/[2SC:<W-\u0003\u0003\u0003\u0018\ne%!C#yG2,8/\u001b<f\u0015\u0011\u0011\u0019J!#\t\u000f\tu5\f1\u0001\u0002\n\u0005\u0019QM\u001c3\t\u0013\t\u00056\f%AA\u0002\u0005%\u0011\u0001B:uKB\fq\"\u001e8uS2$C-\u001a4bk2$HEM\u000b\u0003\u0005OSC!!\u0003\u0003*.\u0012!1\u0016\t\u0005\u0005[\u00139,\u0004\u0002\u00030*!!\u0011\u0017BZ\u0003%)hn\u00195fG.,GMC\u0002\u00036\u0016\f!\"\u00198o_R\fG/[8o\u0013\u0011\u0011ILa,\u0003#Ut7\r[3dW\u0016$g+\u0019:jC:\u001cW-\u0001\u0002u_R1!q\u0018Bc\u0005\u000f\u0004bA!\"\u0003B\u0006%\u0011\u0002\u0002Bb\u00053\u0013\u0011\"\u00138dYV\u001c\u0018N^3\t\u000f\tuU\f1\u0001\u0002\n!I!\u0011U/\u0011\u0002\u0003\u0007\u0011\u0011B\u0001\ri>$C-\u001a4bk2$HEM\u0001\ti>\u001cFO]5oOR\u0011!q\u001a\t\u0005\u0005#\u0014yN\u0004\u0003\u0003T\nm\u0007c\u0001BkK6\u0011!q\u001b\u0006\u0004\u00053<\u0017A\u0002\u001fs_>$h(C\u0002\u0003^\u0016\fa\u0001\u0015:fI\u00164\u0017\u0002\u0002Bq\u0005G\u0014aa\u0015;sS:<'b\u0001BoKR!!q\u001aBt\u0011\u001d\u0011I\u000f\u0019a\u0001\u0003G\nQA]1eSb\f1\u0002^8CsR,\u0017I\u001d:bsV\u0011!q\u001e\t\u0006[\nE(QJ\u0005\u0004\u0005g,'!B!se\u0006L\u0018!\u00047p]\u001el\u0015N\u001c,bYV,\u0007%A\u0005nS:\u001c\u0015m\u00195fI\u0006IQ.\u0019=DC\u000eDW\rZ\u0001\u0006G\u0006\u001c\u0007.\u001a\t\u0006[\nE\u0018\u0011B\u0001\nO\u0016$8)Y2iK\u0012$B!!\u0003\u0004\u0004!91Q\u0001\u0006A\u0002\u0005\r\u0014!A5\u0002\u00115Lg.^:P]\u0016\f\u0011\"\\5okN|e.\u001a\u0011\u0002\u000b\u0005\u0004\b\u000f\\=\u0015\t\u0005%1q\u0002\u0005\b\u0007\u000bi\u0001\u0019AA2)\u0011\tIaa\u0005\t\u000f\rUa\u00021\u0001\u0002B\u0005\tA\u000e\u0006\u0003\u0002\n\re\u0001bBB\u000e\u001f\u0001\u0007!q^\u0001\u0002qR1\u0011\u0011BB\u0010\u0007CAqAa\u0007\u0011\u0001\u0004\t\u0019\u0007C\u0004\u0004$A\u0001\rAa<\u0002\u00135\fwM\\5uk\u0012,G\u0003CA\u0005\u0007O\u0019Yc!\f\t\u000f\r%\u0012\u00031\u0001\u0002d\u0005I!-\u001b;mK:<G\u000f\u001b\u0005\b\u0005\u000f\n\u0002\u0019AA2\u0011\u001d\u0019y#\u0005a\u0001\u0007c\t1A\u001d8e!\u0011\u0019\u0019d!\u000f\u000e\u0005\rU\"bAB\u001cK\u0006!Q\u000f^5m\u0013\u0011\u0019Yd!\u000e\u0003\rI\u000bg\u000eZ8n)\u0019\tIaa\u0010\u0004D!91\u0011\t\nA\u0002\u0005\r\u0014a\u00028v[\nLGo\u001d\u0005\b\u0007_\u0011\u0002\u0019AB\u0019)\u0011\tIaa\u0012\t\u000f\rm1\u00031\u0001\u0003PR1\u0011\u0011BB&\u0007\u001bBqaa\u0007\u0015\u0001\u0004\u0011y\rC\u0004\u0003jR\u0001\r!a\u0019\u0015\t\u0005%1\u0011\u000b\u0005\u0007\u00077)\u0002\u0019\u0001?\u0002\u001bA\u0014xNY1cY\u0016\u0004&/[7f)\u0019\tIaa\u0016\u0004Z!9!Q\b\fA\u0002\u0005\r\u0004bBB\u0018-\u0001\u00071\u0011G\u0001\u000bS:$(GY5h\u0013:$H\u0003BA\u0005\u0007?Bqa!\u0002\u0018\u0001\u0004\t\u0019'A\u0006m_:<'GY5h\u0013:$H\u0003BA\u0005\u0007KBqa!\u0006\u0019\u0001\u0004\t\t%A\u000bkCZ\f')[4J]R,w-\u001a:3E&<\u0017J\u001c;\u0015\t\u0005%11\u000e\u0005\u0007\u00077I\u0002\u0019\u0001?\u0002\u000f1|gnZ$dIR1\u0011\u0011IB9\u0007kBqaa\u001d\u001b\u0001\u0004\t\t%A\u0001b\u0011\u001d\u00199H\u0007a\u0001\u0003\u0003\n\u0011AY\u0001\roJLG/\u001a*fa2\f7-\u001a\u000b\u0003\u0007{\u0002Baa \u0004\u00066\u00111\u0011\u0011\u0006\u0004\u0007\u0007#\u0018\u0001\u00027b]\u001eLAaa\"\u0004\u0002\n1qJ\u00196fGR\u0004")
public final class BigInt
extends ScalaNumber
implements ScalaNumericConversions,
Ordered<BigInt> {
    private BigInteger _bigInteger;
    private final long _long;

    public static BigInt javaBigInteger2bigInt(BigInteger x) {
        return BigInt$.MODULE$.apply(x);
    }

    public static BigInt long2bigInt(long l) {
        return BigInt$.MODULE$.apply(l);
    }

    public static BigInt int2bigInt(int i) {
        return BigInt$.MODULE$.apply(i);
    }

    public static BigInt probablePrime(int bitLength, Random rnd) {
        return BigInt$.MODULE$.probablePrime(bitLength, rnd);
    }

    public static BigInt apply(BigInteger x) {
        return BigInt$.MODULE$.apply(x);
    }

    public static BigInt apply(String x, int radix) {
        return BigInt$.MODULE$.apply(x, radix);
    }

    public static BigInt apply(String x) {
        return BigInt$.MODULE$.apply(x);
    }

    public static BigInt apply(int numbits, Random rnd) {
        return BigInt$.MODULE$.apply(numbits, rnd);
    }

    public static BigInt apply(int bitlength, int certainty, Random rnd) {
        return BigInt$.MODULE$.apply(bitlength, certainty, rnd);
    }

    public static BigInt apply(int signum, byte[] magnitude) {
        return BigInt$.MODULE$.apply(signum, magnitude);
    }

    public static BigInt apply(byte[] x) {
        return BigInt$.MODULE$.apply(x);
    }

    public static BigInt apply(long l) {
        return BigInt$.MODULE$.apply(l);
    }

    public static BigInt apply(int i) {
        return BigInt$.MODULE$.apply(i);
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
    public int unifiedPrimitiveHashcode() {
        return ScalaNumericAnyConversions.unifiedPrimitiveHashcode$(this);
    }

    @Override
    public boolean unifiedPrimitiveEquals(Object x) {
        return ScalaNumericAnyConversions.unifiedPrimitiveEquals$(this, x);
    }

    private BigInteger _bigInteger() {
        return this._bigInteger;
    }

    private void _bigInteger_$eq(BigInteger x$1) {
        this._bigInteger = x$1;
    }

    private long _long() {
        return this._long;
    }

    private boolean longEncoding() {
        return this._long() != Long.MIN_VALUE;
    }

    public BigInteger bigInteger() {
        BigInteger read = this._bigInteger();
        if (read != null) {
            return read;
        }
        BigInteger write = BigInteger.valueOf(this._long());
        this._bigInteger_$eq(write);
        return write;
    }

    public int hashCode() {
        if (this.isValidLong()) {
            return ScalaNumericAnyConversions.unifiedPrimitiveHashcode$(this);
        }
        return Statics.anyHash(this.bigInteger());
    }

    public boolean equals(Object that) {
        if (that instanceof BigInt) {
            BigInt bigInt = (BigInt)that;
            return this.equals(bigInt);
        }
        if (that instanceof BigDecimal) {
            return ((BigDecimal)that).equals(this);
        }
        if (that instanceof Double) {
            double d = BoxesRunTime.unboxToDouble(that);
            return this.isValidDouble() && this.doubleValue() == d;
        }
        if (that instanceof Float) {
            float f = BoxesRunTime.unboxToFloat(that);
            return this.isValidFloat() && this.floatValue() == f;
        }
        return this.isValidLong() && ScalaNumericAnyConversions.unifiedPrimitiveEquals$(this, that);
    }

    @Override
    public boolean isValidByte() {
        return this._long() >= (long)-128 && this._long() <= (long)127;
    }

    @Override
    public boolean isValidShort() {
        return this._long() >= (long)Short.MIN_VALUE && this._long() <= (long)Short.MAX_VALUE;
    }

    @Override
    public boolean isValidChar() {
        return this._long() >= (long)0 && this._long() <= (long)65535;
    }

    @Override
    public boolean isValidInt() {
        return this._long() >= Integer.MIN_VALUE && this._long() <= Integer.MAX_VALUE;
    }

    public boolean isValidLong() {
        return this.longEncoding() || BoxesRunTime.equalsNumNum(this._bigInteger(), BigInt$.MODULE$.scala$math$BigInt$$longMinValueBigInteger());
    }

    public boolean isValidFloat() {
        block5: {
            block4: {
                int bitLen = this.bitLength();
                if (bitLen <= 24) break block4;
                int lowest = this.lowestSetBit();
                if (bitLen > 128 || lowest < bitLen - 24 || lowest >= 128) break block5;
            }
            if (!this.bitLengthOverflow()) {
                return true;
            }
        }
        return false;
    }

    public boolean isValidDouble() {
        block5: {
            block4: {
                int bitLen = this.bitLength();
                if (bitLen <= 53) break block4;
                int lowest = this.lowestSetBit();
                if (bitLen > 1024 || lowest < bitLen - 53 || lowest >= 1024) break block5;
            }
            if (!this.bitLengthOverflow()) {
                return true;
            }
        }
        return false;
    }

    private boolean bitLengthOverflow() {
        BigInteger shifted = this.bigInteger().shiftRight(Integer.MAX_VALUE);
        return shifted.signum() != 0 && !shifted.equals(BigInt$.MODULE$.scala$math$BigInt$$minusOne());
    }

    @Override
    public boolean isWhole() {
        return true;
    }

    @Override
    public BigInteger underlying() {
        return this.bigInteger();
    }

    public boolean equals(BigInt that) {
        if (this.longEncoding()) {
            return that.longEncoding() && this._long() == that._long();
        }
        return !that.longEncoding() && BoxesRunTime.equalsNumNum(this._bigInteger(), that._bigInteger());
    }

    @Override
    public int compare(BigInt that) {
        if (this.longEncoding()) {
            if (that.longEncoding()) {
                return Long.compare(this._long(), that._long());
            }
            return -that._bigInteger().signum();
        }
        if (that.longEncoding()) {
            return this._bigInteger().signum();
        }
        return this._bigInteger().compareTo(that._bigInteger());
    }

    public BigInt $plus(BigInt that) {
        long z;
        long y;
        long x;
        if (this.longEncoding() && that.longEncoding() && (((x = this._long()) ^ (y = that._long()) ^ 0xFFFFFFFFFFFFFFFFL) & (x ^ (z = x + y))) >= 0L) {
            return BigInt$.MODULE$.apply(z);
        }
        return BigInt$.MODULE$.apply(this.bigInteger().add(that.bigInteger()));
    }

    public BigInt $minus(BigInt that) {
        long z;
        long y;
        long x;
        if (this.longEncoding() && that.longEncoding() && (((x = this._long()) ^ (y = that._long())) & (x ^ (z = x - y))) >= 0L) {
            return BigInt$.MODULE$.apply(z);
        }
        return BigInt$.MODULE$.apply(this.bigInteger().subtract(that.bigInteger()));
    }

    public BigInt $times(BigInt that) {
        if (this.longEncoding() && that.longEncoding()) {
            long x = this._long();
            long y = that._long();
            long z = x * y;
            if (x == 0L || y == z / x) {
                return BigInt$.MODULE$.apply(z);
            }
        }
        return BigInt$.MODULE$.apply(this.bigInteger().multiply(that.bigInteger()));
    }

    public BigInt $div(BigInt that) {
        if (this.longEncoding() && that.longEncoding()) {
            return BigInt$.MODULE$.apply(this._long() / that._long());
        }
        return BigInt$.MODULE$.apply(this.bigInteger().divide(that.bigInteger()));
    }

    public BigInt $percent(BigInt that) {
        if (this.longEncoding() && that.longEncoding()) {
            return BigInt$.MODULE$.apply(this._long() % that._long());
        }
        return BigInt$.MODULE$.apply(this.bigInteger().remainder(that.bigInteger()));
    }

    public Tuple2<BigInt, BigInt> $div$percent(BigInt that) {
        if (this.longEncoding() && that.longEncoding()) {
            long x = this._long();
            long y = that._long();
            return new Tuple2<BigInt, BigInt>(BigInt$.MODULE$.apply(x / y), BigInt$.MODULE$.apply(x % y));
        }
        BigInteger[] dr = this.bigInteger().divideAndRemainder(that.bigInteger());
        return new Tuple2<BigInt, BigInt>(BigInt$.MODULE$.apply(dr[0]), BigInt$.MODULE$.apply(dr[1]));
    }

    public BigInt $less$less(int n) {
        if (this.longEncoding() && n <= 0) {
            return this.$greater$greater(-n);
        }
        return BigInt$.MODULE$.apply(this.bigInteger().shiftLeft(n));
    }

    public BigInt $greater$greater(int n) {
        if (this.longEncoding() && n >= 0) {
            if (n < 64) {
                return BigInt$.MODULE$.apply(this._long() >> n);
            }
            if (this._long() < 0L) {
                return BigInt$.MODULE$.apply(-1);
            }
            return BigInt$.MODULE$.apply(0);
        }
        return BigInt$.MODULE$.apply(this.bigInteger().shiftRight(n));
    }

    public BigInt $amp(BigInt that) {
        if (this.longEncoding() && that.longEncoding()) {
            return BigInt$.MODULE$.apply(this._long() & that._long());
        }
        return BigInt$.MODULE$.apply(this.bigInteger().and(that.bigInteger()));
    }

    public BigInt $bar(BigInt that) {
        if (this.longEncoding() && that.longEncoding()) {
            return BigInt$.MODULE$.apply(this._long() | that._long());
        }
        return BigInt$.MODULE$.apply(this.bigInteger().or(that.bigInteger()));
    }

    public BigInt $up(BigInt that) {
        if (this.longEncoding() && that.longEncoding()) {
            return BigInt$.MODULE$.apply(this._long() ^ that._long());
        }
        return BigInt$.MODULE$.apply(this.bigInteger().xor(that.bigInteger()));
    }

    public BigInt $amp$tilde(BigInt that) {
        if (this.longEncoding() && that.longEncoding()) {
            return BigInt$.MODULE$.apply(this._long() & (that._long() ^ 0xFFFFFFFFFFFFFFFFL));
        }
        return BigInt$.MODULE$.apply(this.bigInteger().andNot(that.bigInteger()));
    }

    public BigInt gcd(BigInt that) {
        while (this_.longEncoding()) {
            if (this_._long() == 0L) {
                return that.abs();
            }
            if (that.longEncoding()) {
                if (that._long() == 0L) {
                    return this_.abs();
                }
                long l = this_._long();
                long l2 = that._long();
                return BigInt$.MODULE$.apply(BigInt$.MODULE$.scala$math$BigInt$$longGcd(Math.abs(l), Math.abs(l2)));
            }
            BigInt bigInt = that;
            that = this_;
            BigInt this_ = bigInt;
        }
        if (that.longEncoding()) {
            if (that._long() == 0L) {
                return this_.abs();
            }
            long l = that._long();
            long red = this_._bigInteger().mod(BigInteger.valueOf(Math.abs(l))).longValue();
            if (red == 0L) {
                return that.abs();
            }
            long l3 = that._long();
            return BigInt$.MODULE$.apply(BigInt$.MODULE$.scala$math$BigInt$$longGcd(Math.abs(l3), red));
        }
        return BigInt$.MODULE$.apply(this_.bigInteger().gcd(that.bigInteger()));
    }

    public BigInt mod(BigInt that) {
        if (this.longEncoding() && that.longEncoding() && that._long() > 0L) {
            long res = this._long() % that._long();
            if (res >= 0L) {
                return BigInt$.MODULE$.apply(res);
            }
            return BigInt$.MODULE$.apply(res + that._long());
        }
        return BigInt$.MODULE$.apply(this.bigInteger().mod(that.bigInteger()));
    }

    public BigInt min(BigInt that) {
        if (Ordered.$less$eq$(this, that)) {
            return this;
        }
        return that;
    }

    public BigInt max(BigInt that) {
        if (Ordered.$greater$eq$(this, that)) {
            return this;
        }
        return that;
    }

    public BigInt pow(int exp) {
        return BigInt$.MODULE$.apply(this.bigInteger().pow(exp));
    }

    public BigInt modPow(BigInt exp, BigInt m4) {
        return BigInt$.MODULE$.apply(this.bigInteger().modPow(exp.bigInteger(), m4.bigInteger()));
    }

    public BigInt modInverse(BigInt m4) {
        return BigInt$.MODULE$.apply(this.bigInteger().modInverse(m4.bigInteger()));
    }

    public BigInt unary_$minus() {
        if (this.longEncoding()) {
            return BigInt$.MODULE$.apply(-this._long());
        }
        return BigInt$.MODULE$.apply(this.bigInteger().negate());
    }

    public BigInt abs() {
        if (this.signum() < 0) {
            return this.unary_$minus();
        }
        return this;
    }

    public int signum() {
        if (this.longEncoding()) {
            return Long.signum(this._long());
        }
        return this._bigInteger().signum();
    }

    public BigInt sign() {
        return BigInt$.MODULE$.apply(this.signum());
    }

    public BigInt unary_$tilde() {
        if (this.longEncoding() && this._long() != Long.MAX_VALUE) {
            return BigInt$.MODULE$.apply(-(this._long() + 1L));
        }
        return BigInt$.MODULE$.apply(this.bigInteger().not());
    }

    public boolean testBit(int n) {
        if (this.longEncoding() && n >= 0) {
            if (n <= 63) {
                return (this._long() & 1L << n) != 0L;
            }
            return this._long() < 0L;
        }
        return this._bigInteger().testBit(n);
    }

    public BigInt setBit(int n) {
        if (this.longEncoding() && n <= 62 && n >= 0) {
            return BigInt$.MODULE$.apply(this._long() | 1L << n);
        }
        return BigInt$.MODULE$.apply(this.bigInteger().setBit(n));
    }

    public BigInt clearBit(int n) {
        if (this.longEncoding() && n <= 62 && n >= 0) {
            return BigInt$.MODULE$.apply(this._long() & (1L << n ^ 0xFFFFFFFFFFFFFFFFL));
        }
        return BigInt$.MODULE$.apply(this.bigInteger().clearBit(n));
    }

    public BigInt flipBit(int n) {
        if (this.longEncoding() && n <= 62 && n >= 0) {
            return BigInt$.MODULE$.apply(this._long() ^ 1L << n);
        }
        return BigInt$.MODULE$.apply(this.bigInteger().flipBit(n));
    }

    public int lowestSetBit() {
        if (this.longEncoding()) {
            if (this._long() == 0L) {
                return -1;
            }
            return Long.numberOfTrailingZeros(this._long());
        }
        return this.bigInteger().getLowestSetBit();
    }

    public int bitLength() {
        if (this.longEncoding()) {
            if (this._long() < 0L) {
                return 64 - Long.numberOfLeadingZeros(-(this._long() + 1L));
            }
            return 64 - Long.numberOfLeadingZeros(this._long());
        }
        return this._bigInteger().bitLength();
    }

    public int bitCount() {
        if (this.longEncoding()) {
            if (this._long() < 0L) {
                return Long.bitCount(-(this._long() + 1L));
            }
            return Long.bitCount(this._long());
        }
        return this.bigInteger().bitCount();
    }

    public boolean isProbablePrime(int certainty) {
        return this.bigInteger().isProbablePrime(certainty);
    }

    @Override
    public byte byteValue() {
        return (byte)this.intValue();
    }

    @Override
    public short shortValue() {
        return (short)this.intValue();
    }

    public char charValue() {
        return (char)this.intValue();
    }

    @Override
    public int intValue() {
        if (this.longEncoding()) {
            return (int)this._long();
        }
        return this.bigInteger().intValue();
    }

    @Override
    public long longValue() {
        if (this.longEncoding()) {
            return this._long();
        }
        return this._bigInteger().longValue();
    }

    @Override
    public float floatValue() {
        return this.bigInteger().floatValue();
    }

    @Override
    public double doubleValue() {
        if (this.isValidLong() && -0x20000000000000L <= this._long() && this._long() <= 0x20000000000000L) {
            return this._long();
        }
        return this.bigInteger().doubleValue();
    }

    public NumericRange.Exclusive<BigInt> until(BigInt end, BigInt step) {
        Numeric$BigIntIsIntegral$ apply_apply_num = Numeric$BigIntIsIntegral$.MODULE$;
        return new NumericRange.Exclusive<BigInt>(this, end, step, apply_apply_num);
    }

    public BigInt until$default$2() {
        return BigInt$.MODULE$.apply(1);
    }

    public NumericRange.Inclusive<BigInt> to(BigInt end, BigInt step) {
        Numeric$BigIntIsIntegral$ inclusive_inclusive_num = Numeric$BigIntIsIntegral$.MODULE$;
        return new NumericRange.Inclusive<BigInt>(this, end, step, inclusive_inclusive_num);
    }

    public BigInt to$default$2() {
        return BigInt$.MODULE$.apply(1);
    }

    public String toString() {
        if (this.longEncoding()) {
            return Long.toString(this._long());
        }
        return this._bigInteger().toString();
    }

    public String toString(int radix) {
        return this.bigInteger().toString(radix);
    }

    public byte[] toByteArray() {
        return this.bigInteger().toByteArray();
    }

    public BigInt(BigInteger _bigInteger, long _long) {
        this._bigInteger = _bigInteger;
        this._long = _long;
    }

    public BigInt(BigInteger bigInteger) {
        this(bigInteger, bigInteger.bitLength() <= 63 ? bigInteger.longValue() : Long.MIN_VALUE);
    }
}

