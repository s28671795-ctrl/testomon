/*
 * Decompiled with CFR 0.152.
 */
package scala.math;

import scala.None$;
import scala.Option;
import scala.Some;
import scala.collection.StringParsers$;
import scala.math.BigDecimal;
import scala.math.BigDecimal$;
import scala.math.BigInt;
import scala.math.BigInt$;
import scala.math.Fractional;
import scala.math.Integral;
import scala.math.Ordering;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.util.Failure;
import scala.util.Success;
import scala.util.Try;
import scala.util.control.NonFatal$;

@ScalaSignature(bytes="\u0006\u0005\u0019]v\u0001CAc\u0003\u000fD\t!!5\u0007\u0011\u0005U\u0017q\u0019E\u0001\u0003/Dq!!=\u0002\t\u0003\t\u0019\u0010C\u0004\u0002v\u0006!\t!a>\u0007\u0013\r}\u0011\u0001%A\u0002\u0002\r\u0005\u0002b\u0002B\u0015\t\u0011\u0005!1\u0006\u0005\b\u0007G!A1AB\u0013\u000f\u001d\u00199$\u0001E\u0001\u0007s1qa!\u0010\u0002\u0011\u0003\u0019y\u0004C\u0004\u0002r\"!\taa\u0011\u0007\u0013\r\u0015\u0013\u0001%A\u0002\u0002\r\u001d\u0003b\u0002B\u0015\u0015\u0011\u0005!1\u0006\u0005\b\u0005gQA\u0011AB+\u0011\u001d\u0011yD\u0003C\u0001\u00077BqAa\u0012\u000b\t\u0003\u0019\t\u0007C\u0004\u0004h)!\ta!\u001b\t\u000f\r=$\u0002\"\u0001\u0004r!9!q\n\u0006\u0005\u0002\r]\u0004b\u0002B+\u0015\u0011\u000511\u0010\u0005\b\u0005CRA\u0011AB@\u0011\u001d\u0011)I\u0003C\u0001\u0007\u000bCqAa#\u000b\t\u0003\u0019I\tC\u0004\u0003\u0018*!\ta!$\t\u000f\t\r&\u0002\"\u0001\u0004\u0012\u001e91QS\u0001\t\u0004\r]eaBB#\u0003!\u00051\u0011\u0014\u0005\b\u0003cLB\u0011ABV\u0011%\u0019i+GA\u0001\n\u0013\u0019yKB\u0005\u00042\u0006\u0001\n1!\u0001\u00044\"9!\u0011\u0006\u000f\u0005\u0002\t-\u0002b\u0002B\u001a9\u0011\u00051q\u0017\u0005\b\u0005\u007faB\u0011AB_\u0011\u001d\u00119\u0005\bC\u0001\u0007\u0007Dqaa\u001a\u001d\t\u0003\u0019I\rC\u0004\u0004pq!\taa4\t\u000f\t=C\u0004\"\u0001\u0004V\"9!Q\u000b\u000f\u0005\u0002\re\u0007b\u0002B19\u0011\u00051Q\u001c\u0005\b\u0005\u000bcB\u0011ABr\u0011\u001d\u0011Y\t\bC\u0001\u0007ODqAa&\u001d\t\u0003\u0019Y\u000fC\u0004\u0003$r!\taa<\t\u000f\tmF\u0004\"\u0011\u0004t\"9!Q\u001b\u000f\u0005B\r]xaBB~\u0003!\r1Q \u0004\b\u0007c\u000b\u0001\u0012AB\u0000\u0011\u001d\t\t0\fC\u0001\t\u0013A\u0011b!,.\u0003\u0003%Iaa,\u0007\u0013\u0011-\u0011\u0001%A\u0002\u0002\u00115\u0001b\u0002B\u0015a\u0011\u0005!1\u0006\u0005\b\u0005g\u0001D\u0011\u0001C\f\u0011\u001d\u0011y\u0004\rC\u0001\t;AqAa\u00121\t\u0003!\u0019\u0003C\u0004\u0004hA\"\t\u0001\"\u000b\t\u000f\r=\u0004\u0007\"\u0001\u00050!9!q\n\u0019\u0005\u0002\u0011U\u0002b\u0002B+a\u0011\u0005A\u0011\b\u0005\b\u0005C\u0002D\u0011\u0001C\u001f\u0011\u001d\u0011)\t\rC\u0001\t\u0007BqAa#1\t\u0003!9\u0005C\u0004\u0003\u0018B\"\t\u0001b\u0013\t\u000f\t\r\u0006\u0007\"\u0001\u0005P!9!1\u0018\u0019\u0005B\u0011M\u0003b\u0002Bka\u0011\u0005CqK\u0004\b\t7\n\u00012\u0001C/\r\u001d!Y!\u0001E\u0001\t?Bq!!=B\t\u0003!I\u0007C\u0005\u0004.\u0006\u000b\t\u0011\"\u0003\u00040\u001aIA1N\u0001\u0011\u0002\u0007\u0005AQ\u000e\u0005\b\u0005S!E\u0011\u0001B\u0016\u0011\u001d\u0011\u0019\u0004\u0012C\u0001\toBqAa\u0010E\t\u0003!i\bC\u0004\u0003H\u0011#\t\u0001b!\t\u000f\r\u001dD\t\"\u0001\u0005\n\"91q\u000e#\u0005\u0002\u0011=\u0005b\u0002B(\t\u0012\u0005AQ\u0013\u0005\b\u0005+\"E\u0011\u0001CM\u0011\u001d\u0011\t\u0007\u0012C\u0001\t;CqA!\"E\t\u0003!\u0019\u000bC\u0004\u0003\f\u0012#\t\u0001b*\t\u000f\t]E\t\"\u0001\u0005,\"9!1\u0015#\u0005\u0002\u0011=\u0006b\u0002B^\t\u0012\u0005C1\u0017\u0005\b\u0005+$E\u0011\tC\\\u000f\u001d!Y,\u0001E\u0002\t{3q\u0001b\u001b\u0002\u0011\u0003!y\fC\u0004\u0002rV#\t\u0001\"3\t\u0013\r5V+!A\u0005\n\r=f!\u0003Cf\u0003A\u0005\u0019\u0011\u0001Cg\u0011\u001d\u0011I\u0003\u0017C\u0001\u0005WAqAa\rY\t\u0003!9\u000eC\u0004\u0003@a#\t\u0001\"8\t\u000f\t\u001d\u0003\f\"\u0001\u0005d\"91q\r-\u0005\u0002\u0011%\bbBB81\u0012\u0005Aq\u001e\u0005\b\u0005\u001fBF\u0011\u0001C{\u0011\u001d\u0011)\u0006\u0017C\u0001\tsDqA!\u0019Y\t\u0003!i\u0010C\u0004\u0003\u0006b#\t!b\u0001\t\u000f\t-\u0005\f\"\u0001\u0006\b!9!q\u0013-\u0005\u0002\u0015-\u0001b\u0002BR1\u0012\u0005Qq\u0002\u0005\b\u0005wCF\u0011IC\n\u0011\u001d\u0011)\u000e\u0017C!\u000b/9q!b\u0007\u0002\u0011\u0007)iBB\u0004\u0005L\u0006A\t!b\b\t\u000f\u0005E\u0018\u000e\"\u0001\u0006*!I1QV5\u0002\u0002\u0013%1q\u0016\u0004\n\u000bW\t\u0001\u0013aA\u0001\u000b[AqA!\u000bm\t\u0003\u0011Y\u0003C\u0004\u000341$\t!\"\r\t\u000f\t}B\u000e\"\u0001\u00068!9!q\t7\u0005\u0002\u0015u\u0002bBB4Y\u0012\u0005Q1\t\u0005\b\u0007_bG\u0011AC%\u0011\u001d\u0011y\u0005\u001cC\u0001\u000b\u001fBqA!\u0016m\t\u0003)\u0019\u0006C\u0004\u0003b1$\t!b\u0016\t\u000f\t\u0015E\u000e\"\u0001\u0006^!9!1\u00127\u0005\u0002\u0015\u0005\u0004b\u0002BLY\u0012\u0005QQ\r\u0005\b\u0005GcG\u0011AC5\u0011\u001d\u0011Y\f\u001cC!\u000b[BqA!6m\t\u0003*\thB\u0004\u0006v\u0005A\u0019!b\u001e\u0007\u000f\u0015-\u0012\u0001#\u0001\u0006z!9\u0011\u0011_?\u0005\u0002\u0015\r\u0005\"CBW{\u0006\u0005I\u0011BBX\r%)))\u0001I\u0001\u0004\u0003)9\t\u0003\u0005\u0003*\u0005\u0005A\u0011\u0001B\u0016\u0011!\u0011\u0019$!\u0001\u0005\u0002\u0015=\u0005\u0002\u0003B \u0003\u0003!\t!\"&\t\u0011\t\u001d\u0013\u0011\u0001C\u0001\u000b7C\u0001Ba\u0014\u0002\u0002\u0011\u0005Q\u0011\u0015\u0005\t\u0005+\n\t\u0001\"\u0001\u0006&\"A!\u0011MA\u0001\t\u0003)I\u000b\u0003\u0005\u0003\u0006\u0006\u0005A\u0011ACX\u0011!\u0011Y)!\u0001\u0005\u0002\u0015M\u0006\u0002\u0003BL\u0003\u0003!\t!b.\t\u0011\t\r\u0016\u0011\u0001C\u0001\u000bwC\u0001\"b0\u0002\u0002\u0011\u0005Q\u0011\u0019\u0005\t\u0005k\u000b\t\u0001\"\u0011\u0006H\"A!Q[A\u0001\t\u0003*YmB\u0004\u0006P\u0006A\u0019!\"5\u0007\u000f\u0015\u0015\u0015\u0001#\u0001\u0006T\"A\u0011\u0011_A\u0011\t\u0003))\u000f\u0003\u0006\u0004.\u0006\u0005\u0012\u0011!C\u0005\u0007_3\u0011\"b:\u0002!\u0003\r\t!\";\t\u0011\t%\u0012q\u0005C\u0001\u0005WA\u0001Ba\r\u0002(\u0011\u0005QQ\u001e\u0005\t\u0005\u007f\t9\u0003\"\u0001\u0006t\"A!qIA\u0014\t\u0003)I\u0010\u0003\u0005\u0003P\u0005\u001dB\u0011AC\u0000\u0011!\u0011)&a\n\u0005\u0002\u0019\r\u0001\u0002\u0003B1\u0003O!\tAb\u0002\t\u0011\t\u0015\u0015q\u0005C\u0001\r\u001bA\u0001Ba#\u0002(\u0011\u0005a\u0011\u0003\u0005\t\u0005/\u000b9\u0003\"\u0001\u0007\u0016!A!1UA\u0014\t\u00031I\u0002\u0003\u0005\u0006@\u0006\u001dB\u0011\u0001D\u000f\u0011!\u0011),a\n\u0005B\u0019\r\u0002\u0002\u0003Bk\u0003O!\tEb\n\b\u000f\u0019-\u0012\u0001c\u0001\u0007.\u00199Qq]\u0001\t\u0002\u0019=\u0002\u0002CAy\u0003\u000f\"\tAb\u0010\t\u0015\r5\u0016qIA\u0001\n\u0013\u0019yKB\u0005\u0007B\u0005\u0001\n1!\u0001\u0007D!A!\u0011FA'\t\u0003\u0011Y\u0003\u0003\u0005\u00034\u00055C\u0011\u0001D'\u0011!\u0011y$!\u0014\u0005\u0002\u0019M\u0003\u0002\u0003B$\u0003\u001b\"\tA\"\u0017\t\u0011\t=\u0013Q\nC\u0001\r?B\u0001B!\u0016\u0002N\u0011\u0005a1\r\u0005\t\u0005C\ni\u0005\"\u0001\u0007h!A!QQA'\t\u00031i\u0007\u0003\u0005\u0003\f\u00065C\u0011\u0001D9\u0011!\u00119*!\u0014\u0005\u0002\u0019U\u0004\u0002\u0003BR\u0003\u001b\"\tA\"\u001f\u0007\u0013\u0019u\u0014\u0001%A\u0002\u0002\u0019}\u0004\u0002\u0003B\u0015\u0003K\"\tAa\u000b\t\u0011\u0015}\u0016Q\rC\u0001\r\u000b3\u0011Bb#\u0002!\u0003\r\tA\"$\t\u0011\t%\u00121\u000eC\u0001\u0005WA\u0001ba\u001a\u0002l\u0011\u0005a\u0011\u0013\u0005\t\u0007_\nY\u0007\"\u0001\u0007\u0018\u001e9aQT\u0001\t\u0004\u0019}ea\u0002D?\u0003!\u0005a\u0011\u0015\u0005\t\u0003c\f)\b\"\u0001\u0007,\"Q1QVA;\u0003\u0003%Iaa,\b\u000f\u00195\u0016\u0001#\u0001\u00070\u001a9a1R\u0001\t\u0002\u0019E\u0006\u0002CAy\u0003{\"\tA\".\t\u0015\r5\u0016QPA\u0001\n\u0013\u0019y\u000bC\u0005\u0004.\u0006\t\t\u0011\"\u0003\u00040\u001aQ\u0011Q[Ad!\u0003\r\t!!@\t\u0011\t%\u0012Q\u0011C\u0001\u0005WA\u0001Ba\r\u0002\u0006\u001a\u0005!Q\u0007\u0005\t\u0005\u007f\t)I\"\u0001\u0003B!A!qIAC\r\u0003\u0011I\u0005\u0003\u0005\u0003P\u0005\u0015e\u0011\u0001B)\u0011!\u0011)&!\"\u0007\u0002\t]\u0003\u0002\u0003B1\u0003\u000b3\tAa\u0019\t\u0011\t\u0015\u0015Q\u0011D\u0001\u0005\u000fC\u0001Ba#\u0002\u0006\u001a\u0005!Q\u0012\u0005\t\u0005/\u000b)I\"\u0001\u0003\u001a\"A!1UAC\r\u0003\u0011)\u000b\u0003\u0005\u00030\u0006\u0015E\u0011\u0001BY\u0011!\u0011\u0019,!\"\u0005\u0002\tE\u0006\u0002\u0003B[\u0003\u000b#\tAa.\t\u0011\tm\u0016Q\u0011C\u0001\u0005{C\u0001B!6\u0002\u0006\u0012\u0005!q\u001b\u0004\b\u00057\f)\t\u0001Bo\u0011-\u0011y.a*\u0003\u0002\u0003\u0006IAa\u0005\t\u0011\u0005E\u0018q\u0015C\u0001\u0005CD\u0001B!;\u0002(\u0012\u0005!1\u001e\u0005\t\u0005c\f9\u000b\"\u0001\u0003t\"A!q_AT\t\u0003\u0011I\u0010\u0003\u0005\u0003~\u0006\u001dF\u0011\u0001BY\u0011!\u0011),a*\u0005\u0002\tE\u0006\u0002\u0003B^\u0003O#\tAa@\t\u0011\tU\u0017q\u0015C\u0001\u0005cC\u0001B!\"\u0002(\u0012\u0005!q \u0005\t\u0005\u0017\u000b9\u000b\"\u0001\u0004\u0004!A!qSAT\t\u0003\u0019)\u0001\u0003\u0005\u0003$\u0006\u001dF\u0011AB\u0004\u0011!\u0019I!!\"\u0005\u0004\r-\u0011a\u0002(v[\u0016\u0014\u0018n\u0019\u0006\u0005\u0003\u0013\fY-\u0001\u0003nCRD'BAAg\u0003\u0015\u00198-\u00197b\u0007\u0001\u00012!a5\u0002\u001b\t\t9MA\u0004Ok6,'/[2\u0014\u000b\u0005\tI.!9\u0011\t\u0005m\u0017Q\\\u0007\u0003\u0003\u0017LA!a8\u0002L\n1\u0011I\\=SK\u001a\u0004B!a9\u0002n6\u0011\u0011Q\u001d\u0006\u0005\u0003O\fI/\u0001\u0002j_*\u0011\u00111^\u0001\u0005U\u00064\u0018-\u0003\u0003\u0002p\u0006\u0015(\u0001D*fe&\fG.\u001b>bE2,\u0017A\u0002\u001fj]&$h\b\u0006\u0002\u0002R\u0006)\u0011\r\u001d9msV!\u0011\u0011`B\t)\u0011\tYpa\u0005\u0011\r\u0005M\u0017QQB\b+\u0011\tyPa\u0006\u0014\r\u0005\u0015%\u0011\u0001B\u0007!\u0011\u0011\u0019A!\u0003\u000e\u0005\t\u0015!\u0002\u0002B\u0004\u0003S\fA\u0001\\1oO&!!1\u0002B\u0003\u0005\u0019y%M[3diB1\u00111\u001bB\b\u0005'IAA!\u0005\u0002H\nAqJ\u001d3fe&tw\r\u0005\u0003\u0003\u0016\t]A\u0002\u0001\u0003\t\u00053\t)I1\u0001\u0003\u001c\t\tA+\u0005\u0003\u0003\u001e\t\r\u0002\u0003BAn\u0005?IAA!\t\u0002L\n9aj\u001c;iS:<\u0007\u0003BAn\u0005KIAAa\n\u0002L\n\u0019\u0011I\\=\u0002\r\u0011Jg.\u001b;%)\t\u0011i\u0003\u0005\u0003\u0002\\\n=\u0012\u0002\u0002B\u0019\u0003\u0017\u0014A!\u00168ji\u0006!\u0001\u000f\\;t)\u0019\u0011\u0019Ba\u000e\u0003<!A!\u0011HAE\u0001\u0004\u0011\u0019\"A\u0001y\u0011!\u0011i$!#A\u0002\tM\u0011!A=\u0002\u000b5Lg.^:\u0015\r\tM!1\tB#\u0011!\u0011I$a#A\u0002\tM\u0001\u0002\u0003B\u001f\u0003\u0017\u0003\rAa\u0005\u0002\u000bQLW.Z:\u0015\r\tM!1\nB'\u0011!\u0011I$!$A\u0002\tM\u0001\u0002\u0003B\u001f\u0003\u001b\u0003\rAa\u0005\u0002\r9,w-\u0019;f)\u0011\u0011\u0019Ba\u0015\t\u0011\te\u0012q\u0012a\u0001\u0005'\tqA\u001a:p[&sG\u000f\u0006\u0003\u0003\u0014\te\u0003\u0002\u0003B\u001d\u0003#\u0003\rAa\u0017\u0011\t\u0005m'QL\u0005\u0005\u0005?\nYMA\u0002J]R\f1\u0002]1sg\u0016\u001cFO]5oOR!!Q\rB6!\u0019\tYNa\u001a\u0003\u0014%!!\u0011NAf\u0005\u0019y\u0005\u000f^5p]\"A!QNAJ\u0001\u0004\u0011y'A\u0002tiJ\u0004BA!\u001d\u0003\u00009!!1\u000fB>!\u0011\u0011)(a3\u000e\u0005\t]$\u0002\u0002B=\u0003\u001f\fa\u0001\u0010:p_Rt\u0014\u0002\u0002B?\u0003\u0017\fa\u0001\u0015:fI\u00164\u0017\u0002\u0002BA\u0005\u0007\u0013aa\u0015;sS:<'\u0002\u0002B?\u0003\u0017\fQ\u0001^8J]R$BAa\u0017\u0003\n\"A!\u0011HAK\u0001\u0004\u0011\u0019\"\u0001\u0004u_2{gn\u001a\u000b\u0005\u0005\u001f\u0013)\n\u0005\u0003\u0002\\\nE\u0015\u0002\u0002BJ\u0003\u0017\u0014A\u0001T8oO\"A!\u0011HAL\u0001\u0004\u0011\u0019\"A\u0004u_\u001acw.\u0019;\u0015\t\tm%\u0011\u0015\t\u0005\u00037\u0014i*\u0003\u0003\u0003 \u0006-'!\u0002$m_\u0006$\b\u0002\u0003B\u001d\u00033\u0003\rAa\u0005\u0002\u0011Q|Gi\\;cY\u0016$BAa*\u0003.B!\u00111\u001cBU\u0013\u0011\u0011Y+a3\u0003\r\u0011{WO\u00197f\u0011!\u0011I$a'A\u0002\tM\u0011\u0001\u0002>fe>,\"Aa\u0005\u0002\u0007=tW-A\u0002bEN$BAa\u0005\u0003:\"A!\u0011HAQ\u0001\u0004\u0011\u0019\"\u0001\u0004tS\u001etW/\u001c\u000b\u0005\u00057\u0012y\f\u0003\u0005\u0003:\u0005\r\u0006\u0019\u0001B\nQ1\t\u0019Ka1\u0003J\n-'q\u001aBi!\u0011\tYN!2\n\t\t\u001d\u00171\u001a\u0002\u000bI\u0016\u0004(/Z2bi\u0016$\u0017aB7fgN\fw-Z\u0011\u0003\u0005\u001b\f\u0011$^:fA\u0001\u001c\u0018n\u001a8aA5,G\u000f[8eA%t7\u000f^3bI\u0006)1/\u001b8dK\u0006\u0012!1[\u0001\u0007e9\n4G\f\u0019\u0002\tMLwM\u001c\u000b\u0005\u0005'\u0011I\u000e\u0003\u0005\u0003:\u0005\u0015\u0006\u0019\u0001B\n\u0005)qU/\\3sS\u000e|\u0005o]\n\u0005\u0003O\u000bI.A\u0002mQN$BAa9\u0003hB!!Q]AT\u001b\t\t)\t\u0003\u0005\u0003`\u0006-\u0006\u0019\u0001B\n\u0003\u0015!\u0003\u000f\\;t)\u0011\u0011\u0019B!<\t\u0011\t=\u0018Q\u0016a\u0001\u0005'\t1A\u001d5t\u0003\u0019!S.\u001b8vgR!!1\u0003B{\u0011!\u0011y/a,A\u0002\tM\u0011A\u0002\u0013uS6,7\u000f\u0006\u0003\u0003\u0014\tm\b\u0002\u0003Bx\u0003c\u0003\rAa\u0005\u0002\u0019Ut\u0017M]=`I5Lg.^:\u0016\u0005\tm\u0003\u0006DA\\\u0005\u0007\u0014IMa3\u0003P\nEWC\u0001BH+\t\u0011Y*\u0006\u0002\u0003(\u0006aQn\u001b(v[\u0016\u0014\u0018nY(qgR!!1]B\u0007\u0011!\u0011y.a1A\u0002\tM\u0001\u0003\u0002B\u000b\u0007#!qA!\u0007\u0004\u0005\u0004\u0011Y\u0002C\u0004\u0004\u0016\r\u0001\u001d!a?\u0002\u00079,X\u000eK\u0002\u0004\u00073\u0001B!a7\u0004\u001c%!1QDAf\u0005\u0019Ig\u000e\\5oK\nqQ\t\u001f;sC&k\u0007\u000f\\5dSR\u001c8c\u0001\u0003\u0002Z\u0006y\u0011N\u001c4jq:+X.\u001a:jG>\u00038/\u0006\u0003\u0004(\rEB\u0003BB\u0015\u0007k!Baa\u000b\u00044A!1QFAT!\u0019\t\u0019.!\"\u00040A!!QCB\u0019\t\u001d\u0011IB\u0002b\u0001\u00057Aqa!\u0006\u0007\u0001\b\u0019i\u0003C\u0004\u0003:\u0019\u0001\raa\f\u0002\u0013%k\u0007\u000f\\5dSR\u001c\bcAB\u001e\u00115\t\u0011AA\u0005J[Bd\u0017nY5ugN)\u0001\"!7\u0004BA\u001911\b\u0003\u0015\u0005\re\"\u0001\u0005\"jO&sG/S:J]R,wM]1m'\u0015Q!\u0011AB%!\u0019\t\u0019na\u0013\u0004P%!1QJAd\u0005!Ie\u000e^3he\u0006d\u0007\u0003BAj\u0007#JAaa\u0015\u0002H\n1!)[4J]R$baa\u0014\u0004X\re\u0003b\u0002B\u001d\u0019\u0001\u00071q\n\u0005\b\u0005{a\u0001\u0019AB()\u0019\u0019ye!\u0018\u0004`!9!\u0011H\u0007A\u0002\r=\u0003b\u0002B\u001f\u001b\u0001\u00071q\n\u000b\u0007\u0007\u001f\u001a\u0019g!\u001a\t\u000f\teb\u00021\u0001\u0004P!9!Q\b\bA\u0002\r=\u0013\u0001B9v_R$baa\u0014\u0004l\r5\u0004b\u0002B\u001d\u001f\u0001\u00071q\n\u0005\b\u0005{y\u0001\u0019AB(\u0003\r\u0011X-\u001c\u000b\u0007\u0007\u001f\u001a\u0019h!\u001e\t\u000f\te\u0002\u00031\u0001\u0004P!9!Q\b\tA\u0002\r=C\u0003BB(\u0007sBqA!\u000f\u0012\u0001\u0004\u0019y\u0005\u0006\u0003\u0004P\ru\u0004b\u0002B\u001d%\u0001\u0007!1\f\u000b\u0005\u0007\u0003\u001b\u0019\t\u0005\u0004\u0002\\\n\u001d4q\n\u0005\b\u0005[\u001a\u0002\u0019\u0001B8)\u0011\u0011Yfa\"\t\u000f\teB\u00031\u0001\u0004PQ!!qRBF\u0011\u001d\u0011I$\u0006a\u0001\u0007\u001f\"BAa'\u0004\u0010\"9!\u0011\b\fA\u0002\r=C\u0003\u0002BT\u0007'CqA!\u000f\u0018\u0001\u0004\u0019y%\u0001\tCS\u001eLe\u000e^%t\u0013:$Xm\u001a:bYB\u001911H\r\u0014\u000fe\u0011\taa'\u0004\u001eB\u001911\b\u0006\u0011\t\r}5Q\u0015\b\u0005\u0003'\u001c\t+\u0003\u0003\u0004$\u0006\u001d\u0017\u0001C(sI\u0016\u0014\u0018N\\4\n\t\r\u001d6\u0011\u0016\u0002\u000f\u0005&<\u0017J\u001c;Pe\u0012,'/\u001b8h\u0015\u0011\u0019\u0019+a2\u0015\u0005\r]\u0015\u0001D<sSR,'+\u001a9mC\u000e,GC\u0001B\u0001\u00055Ie\u000e^%t\u0013:$Xm\u001a:bYN)AD!\u0001\u00046B1\u00111[B&\u00057\"bAa\u0017\u0004:\u000em\u0006b\u0002B\u001d=\u0001\u0007!1\f\u0005\b\u0005{q\u0002\u0019\u0001B.)\u0019\u0011Yfa0\u0004B\"9!\u0011H\u0010A\u0002\tm\u0003b\u0002B\u001f?\u0001\u0007!1\f\u000b\u0007\u00057\u001a)ma2\t\u000f\te\u0002\u00051\u0001\u0003\\!9!Q\b\u0011A\u0002\tmCC\u0002B.\u0007\u0017\u001ci\rC\u0004\u0003:\u0005\u0002\rAa\u0017\t\u000f\tu\u0012\u00051\u0001\u0003\\Q1!1LBi\u0007'DqA!\u000f#\u0001\u0004\u0011Y\u0006C\u0004\u0003>\t\u0002\rAa\u0017\u0015\t\tm3q\u001b\u0005\b\u0005s\u0019\u0003\u0019\u0001B.)\u0011\u0011Yfa7\t\u000f\teB\u00051\u0001\u0003\\Q!1q\\Bq!\u0019\tYNa\u001a\u0003\\!9!QN\u0013A\u0002\t=D\u0003\u0002B.\u0007KDqA!\u000f'\u0001\u0004\u0011Y\u0006\u0006\u0003\u0003\u0010\u000e%\bb\u0002B\u001dO\u0001\u0007!1\f\u000b\u0005\u00057\u001bi\u000fC\u0004\u0003:!\u0002\rAa\u0017\u0015\t\t\u001d6\u0011\u001f\u0005\b\u0005sI\u0003\u0019\u0001B.)\u0011\u0011Yf!>\t\u000f\te\"\u00061\u0001\u0003\\Q!!1LB}\u0011\u001d\u0011Id\u000ba\u0001\u00057\nQ\"\u00138u\u0013NLe\u000e^3he\u0006d\u0007cAB\u001e[M9QF!\u0001\u0005\u0002\u0011\r\u0001cAB\u001e9A!1q\u0014C\u0003\u0013\u0011!9a!+\u0003\u0017%sGo\u0014:eKJLgn\u001a\u000b\u0003\u0007{\u0014qb\u00155peRL5/\u00138uK\u001e\u0014\u0018\r\\\n\u0006a\t\u0005Aq\u0002\t\u0007\u0003'\u001cY\u0005\"\u0005\u0011\t\u0005mG1C\u0005\u0005\t+\tYMA\u0003TQ>\u0014H\u000f\u0006\u0004\u0005\u0012\u0011eA1\u0004\u0005\b\u0005s\u0011\u0004\u0019\u0001C\t\u0011\u001d\u0011iD\ra\u0001\t#!b\u0001\"\u0005\u0005 \u0011\u0005\u0002b\u0002B\u001dg\u0001\u0007A\u0011\u0003\u0005\b\u0005{\u0019\u0004\u0019\u0001C\t)\u0019!\t\u0002\"\n\u0005(!9!\u0011\b\u001bA\u0002\u0011E\u0001b\u0002B\u001fi\u0001\u0007A\u0011\u0003\u000b\u0007\t#!Y\u0003\"\f\t\u000f\teR\u00071\u0001\u0005\u0012!9!QH\u001bA\u0002\u0011EAC\u0002C\t\tc!\u0019\u0004C\u0004\u0003:Y\u0002\r\u0001\"\u0005\t\u000f\tub\u00071\u0001\u0005\u0012Q!A\u0011\u0003C\u001c\u0011\u001d\u0011Id\u000ea\u0001\t#!B\u0001\"\u0005\u0005<!9!\u0011\b\u001dA\u0002\tmC\u0003\u0002C \t\u0003\u0002b!a7\u0003h\u0011E\u0001b\u0002B7s\u0001\u0007!q\u000e\u000b\u0005\u00057\")\u0005C\u0004\u0003:i\u0002\r\u0001\"\u0005\u0015\t\t=E\u0011\n\u0005\b\u0005sY\u0004\u0019\u0001C\t)\u0011\u0011Y\n\"\u0014\t\u000f\teB\b1\u0001\u0005\u0012Q!!q\u0015C)\u0011\u001d\u0011I$\u0010a\u0001\t#!BAa\u0017\u0005V!9!\u0011\b A\u0002\u0011EA\u0003\u0002C\t\t3BqA!\u000f@\u0001\u0004!\t\"A\bTQ>\u0014H/S:J]R,wM]1m!\r\u0019Y$Q\n\b\u0003\n\u0005A\u0011\rC2!\r\u0019Y\u0004\r\t\u0005\u0007?#)'\u0003\u0003\u0005h\r%&!D*i_J$xJ\u001d3fe&tw\r\u0006\u0002\u0005^\tq!)\u001f;f\u0013NLe\u000e^3he\u0006d7#\u0002#\u0003\u0002\u0011=\u0004CBAj\u0007\u0017\"\t\b\u0005\u0003\u0002\\\u0012M\u0014\u0002\u0002C;\u0003\u0017\u0014AAQ=uKR1A\u0011\u000fC=\twBqA!\u000fG\u0001\u0004!\t\bC\u0004\u0003>\u0019\u0003\r\u0001\"\u001d\u0015\r\u0011EDq\u0010CA\u0011\u001d\u0011Id\u0012a\u0001\tcBqA!\u0010H\u0001\u0004!\t\b\u0006\u0004\u0005r\u0011\u0015Eq\u0011\u0005\b\u0005sA\u0005\u0019\u0001C9\u0011\u001d\u0011i\u0004\u0013a\u0001\tc\"b\u0001\"\u001d\u0005\f\u00125\u0005b\u0002B\u001d\u0013\u0002\u0007A\u0011\u000f\u0005\b\u0005{I\u0005\u0019\u0001C9)\u0019!\t\b\"%\u0005\u0014\"9!\u0011\b&A\u0002\u0011E\u0004b\u0002B\u001f\u0015\u0002\u0007A\u0011\u000f\u000b\u0005\tc\"9\nC\u0004\u0003:-\u0003\r\u0001\"\u001d\u0015\t\u0011ED1\u0014\u0005\b\u0005sa\u0005\u0019\u0001B.)\u0011!y\n\")\u0011\r\u0005m'q\rC9\u0011\u001d\u0011i'\u0014a\u0001\u0005_\"BAa\u0017\u0005&\"9!\u0011\b(A\u0002\u0011ED\u0003\u0002BH\tSCqA!\u000fP\u0001\u0004!\t\b\u0006\u0003\u0003\u001c\u00125\u0006b\u0002B\u001d!\u0002\u0007A\u0011\u000f\u000b\u0005\u0005O#\t\fC\u0004\u0003:E\u0003\r\u0001\"\u001d\u0015\t\tmCQ\u0017\u0005\b\u0005s\u0011\u0006\u0019\u0001C9)\u0011!\t\b\"/\t\u000f\te2\u000b1\u0001\u0005r\u0005q!)\u001f;f\u0013NLe\u000e^3he\u0006d\u0007cAB\u001e+N9QK!\u0001\u0005B\u0012\r\u0007cAB\u001e\tB!1q\u0014Cc\u0013\u0011!9m!+\u0003\u0019\tKH/Z(sI\u0016\u0014\u0018N\\4\u0015\u0005\u0011u&AD\"iCJL5/\u00138uK\u001e\u0014\u0018\r\\\n\u00061\n\u0005Aq\u001a\t\u0007\u0003'\u001cY\u0005\"5\u0011\t\u0005mG1[\u0005\u0005\t+\fYM\u0001\u0003DQ\u0006\u0014HC\u0002Ci\t3$Y\u000eC\u0004\u0003:i\u0003\r\u0001\"5\t\u000f\tu\"\f1\u0001\u0005RR1A\u0011\u001bCp\tCDqA!\u000f\\\u0001\u0004!\t\u000eC\u0004\u0003>m\u0003\r\u0001\"5\u0015\r\u0011EGQ\u001dCt\u0011\u001d\u0011I\u0004\u0018a\u0001\t#DqA!\u0010]\u0001\u0004!\t\u000e\u0006\u0004\u0005R\u0012-HQ\u001e\u0005\b\u0005si\u0006\u0019\u0001Ci\u0011\u001d\u0011i$\u0018a\u0001\t#$b\u0001\"5\u0005r\u0012M\bb\u0002B\u001d=\u0002\u0007A\u0011\u001b\u0005\b\u0005{q\u0006\u0019\u0001Ci)\u0011!\t\u000eb>\t\u000f\ter\f1\u0001\u0005RR!A\u0011\u001bC~\u0011\u001d\u0011I\u0004\u0019a\u0001\u00057\"B\u0001b@\u0006\u0002A1\u00111\u001cB4\t#DqA!\u001cb\u0001\u0004\u0011y\u0007\u0006\u0003\u0003\\\u0015\u0015\u0001b\u0002B\u001dE\u0002\u0007A\u0011\u001b\u000b\u0005\u0005\u001f+I\u0001C\u0004\u0003:\r\u0004\r\u0001\"5\u0015\t\tmUQ\u0002\u0005\b\u0005s!\u0007\u0019\u0001Ci)\u0011\u00119+\"\u0005\t\u000f\teR\r1\u0001\u0005RR!!1LC\u000b\u0011\u001d\u0011ID\u001aa\u0001\t#$B\u0001\"5\u0006\u001a!9!\u0011H4A\u0002\u0011E\u0017AD\"iCJL5/\u00138uK\u001e\u0014\u0018\r\u001c\t\u0004\u0007wI7cB5\u0003\u0002\u0015\u0005R1\u0005\t\u0004\u0007wA\u0006\u0003BBP\u000bKIA!b\n\u0004*\na1\t[1s\u001fJ$WM]5oOR\u0011QQ\u0004\u0002\u000f\u0019>tw-S:J]R,wM]1m'\u0015a'\u0011AC\u0018!\u0019\t\u0019na\u0013\u0003\u0010R1!qRC\u001a\u000bkAqA!\u000fo\u0001\u0004\u0011y\tC\u0004\u0003>9\u0004\rAa$\u0015\r\t=U\u0011HC\u001e\u0011\u001d\u0011Id\u001ca\u0001\u0005\u001fCqA!\u0010p\u0001\u0004\u0011y\t\u0006\u0004\u0003\u0010\u0016}R\u0011\t\u0005\b\u0005s\u0001\b\u0019\u0001BH\u0011\u001d\u0011i\u0004\u001da\u0001\u0005\u001f#bAa$\u0006F\u0015\u001d\u0003b\u0002B\u001dc\u0002\u0007!q\u0012\u0005\b\u0005{\t\b\u0019\u0001BH)\u0019\u0011y)b\u0013\u0006N!9!\u0011\b:A\u0002\t=\u0005b\u0002B\u001fe\u0002\u0007!q\u0012\u000b\u0005\u0005\u001f+\t\u0006C\u0004\u0003:M\u0004\rAa$\u0015\t\t=UQ\u000b\u0005\b\u0005s!\b\u0019\u0001B.)\u0011)I&b\u0017\u0011\r\u0005m'q\rBH\u0011\u001d\u0011i'\u001ea\u0001\u0005_\"BAa\u0017\u0006`!9!\u0011\b<A\u0002\t=E\u0003\u0002BH\u000bGBqA!\u000fx\u0001\u0004\u0011y\t\u0006\u0003\u0003\u001c\u0016\u001d\u0004b\u0002B\u001dq\u0002\u0007!q\u0012\u000b\u0005\u0005O+Y\u0007C\u0004\u0003:e\u0004\rAa$\u0015\t\tmSq\u000e\u0005\b\u0005sQ\b\u0019\u0001BH)\u0011\u0011y)b\u001d\t\u000f\te2\u00101\u0001\u0003\u0010\u0006qAj\u001c8h\u0013NLe\u000e^3he\u0006d\u0007cAB\u001e{N9QP!\u0001\u0006|\u0015u\u0004cAB\u001eYB!1qTC@\u0013\u0011)\ti!+\u0003\u00191{gnZ(sI\u0016\u0014\u0018N\\4\u0015\u0005\u0015]$!\u0005$m_\u0006$\u0018j\u001d$sC\u000e$\u0018n\u001c8bYN1\u0011\u0011\u0001B\u0001\u000b\u0013\u0003b!a5\u0006\f\nm\u0015\u0002BCG\u0003\u000f\u0014!B\u0012:bGRLwN\\1m)\u0019\u0011Y*\"%\u0006\u0014\"A!\u0011HA\u0003\u0001\u0004\u0011Y\n\u0003\u0005\u0003>\u0005\u0015\u0001\u0019\u0001BN)\u0019\u0011Y*b&\u0006\u001a\"A!\u0011HA\u0004\u0001\u0004\u0011Y\n\u0003\u0005\u0003>\u0005\u001d\u0001\u0019\u0001BN)\u0019\u0011Y*\"(\u0006 \"A!\u0011HA\u0005\u0001\u0004\u0011Y\n\u0003\u0005\u0003>\u0005%\u0001\u0019\u0001BN)\u0011\u0011Y*b)\t\u0011\te\u00121\u0002a\u0001\u00057#BAa'\u0006(\"A!\u0011HA\u0007\u0001\u0004\u0011Y\u0006\u0006\u0003\u0006,\u00165\u0006CBAn\u0005O\u0012Y\n\u0003\u0005\u0003n\u0005=\u0001\u0019\u0001B8)\u0011\u0011Y&\"-\t\u0011\te\u0012\u0011\u0003a\u0001\u00057#BAa$\u00066\"A!\u0011HA\n\u0001\u0004\u0011Y\n\u0006\u0003\u0003\u001c\u0016e\u0006\u0002\u0003B\u001d\u0003+\u0001\rAa'\u0015\t\t\u001dVQ\u0018\u0005\t\u0005s\t9\u00021\u0001\u0003\u001c\u0006\u0019A-\u001b<\u0015\r\tmU1YCc\u0011!\u0011I$!\u0007A\u0002\tm\u0005\u0002\u0003B\u001f\u00033\u0001\rAa'\u0015\t\tmU\u0011\u001a\u0005\t\u0005s\tY\u00021\u0001\u0003\u001cR!!1TCg\u0011!\u0011I$!\bA\u0002\tm\u0015!\u0005$m_\u0006$\u0018j\u001d$sC\u000e$\u0018n\u001c8bYB!11HA\u0011'!\t\tC!\u0001\u0006V\u0016]\u0007\u0003BB\u001e\u0003\u0003\u0001B!\"7\u0006`:!1qTCn\u0013\u0011)in!+\u0002\u000b\u0019cw.\u0019;\n\t\u0015\u0005X1\u001d\u0002\r\u0013\u0016,Wm\u0014:eKJLgn\u001a\u0006\u0005\u000b;\u001cI\u000b\u0006\u0002\u0006R\n\u0011Bi\\;cY\u0016L5O\u0012:bGRLwN\\1m'\u0019\t9C!\u0001\u0006lB1\u00111[CF\u0005O#bAa*\u0006p\u0016E\b\u0002\u0003B\u001d\u0003W\u0001\rAa*\t\u0011\tu\u00121\u0006a\u0001\u0005O#bAa*\u0006v\u0016]\b\u0002\u0003B\u001d\u0003[\u0001\rAa*\t\u0011\tu\u0012Q\u0006a\u0001\u0005O#bAa*\u0006|\u0016u\b\u0002\u0003B\u001d\u0003_\u0001\rAa*\t\u0011\tu\u0012q\u0006a\u0001\u0005O#BAa*\u0007\u0002!A!\u0011HA\u0019\u0001\u0004\u00119\u000b\u0006\u0003\u0003(\u001a\u0015\u0001\u0002\u0003B\u001d\u0003g\u0001\rAa\u0017\u0015\t\u0019%a1\u0002\t\u0007\u00037\u00149Ga*\t\u0011\t5\u0014Q\u0007a\u0001\u0005_\"BAa\u0017\u0007\u0010!A!\u0011HA\u001c\u0001\u0004\u00119\u000b\u0006\u0003\u0003\u0010\u001aM\u0001\u0002\u0003B\u001d\u0003s\u0001\rAa*\u0015\t\tmeq\u0003\u0005\t\u0005s\tY\u00041\u0001\u0003(R!!q\u0015D\u000e\u0011!\u0011I$!\u0010A\u0002\t\u001dFC\u0002BT\r?1\t\u0003\u0003\u0005\u0003:\u0005}\u0002\u0019\u0001BT\u0011!\u0011i$a\u0010A\u0002\t\u001dF\u0003\u0002BT\rKA\u0001B!\u000f\u0002B\u0001\u0007!q\u0015\u000b\u0005\u0005O3I\u0003\u0003\u0005\u0003:\u0005\r\u0003\u0019\u0001BT\u0003I!u.\u001e2mK&\u001bhI]1di&|g.\u00197\u0011\t\rm\u0012qI\n\t\u0003\u000f\u0012\tA\"\r\u00074A!11HA\u0014!\u00111)Db\u000f\u000f\t\r}eqG\u0005\u0005\rs\u0019I+\u0001\u0004E_V\u0014G.Z\u0005\u0005\u000bC4iD\u0003\u0003\u0007:\r%FC\u0001D\u0017\u0005Y\u0011\u0015n\u001a#fG&l\u0017\r\\%t\u0007>tg\r\\5di\u0016$7CBA'\u0005\u00031)\u0005\u0005\u0004\u0002T\u0006\u0015eq\t\t\u0005\u0003'4I%\u0003\u0003\u0007L\u0005\u001d'A\u0003\"jO\u0012+7-[7bYR1aq\tD(\r#B\u0001B!\u000f\u0002R\u0001\u0007aq\t\u0005\t\u0005{\t\t\u00061\u0001\u0007HQ1aq\tD+\r/B\u0001B!\u000f\u0002T\u0001\u0007aq\t\u0005\t\u0005{\t\u0019\u00061\u0001\u0007HQ1aq\tD.\r;B\u0001B!\u000f\u0002V\u0001\u0007aq\t\u0005\t\u0005{\t)\u00061\u0001\u0007HQ!aq\tD1\u0011!\u0011I$a\u0016A\u0002\u0019\u001dC\u0003\u0002D$\rKB\u0001B!\u000f\u0002Z\u0001\u0007!1\f\u000b\u0005\rS2Y\u0007\u0005\u0004\u0002\\\n\u001ddq\t\u0005\t\u0005[\nY\u00061\u0001\u0003pQ!!1\fD8\u0011!\u0011I$!\u0018A\u0002\u0019\u001dC\u0003\u0002BH\rgB\u0001B!\u000f\u0002`\u0001\u0007aq\t\u000b\u0005\u0005739\b\u0003\u0005\u0003:\u0005\u0005\u0004\u0019\u0001D$)\u0011\u00119Kb\u001f\t\u0011\te\u00121\ra\u0001\r\u000f\u0012aCQ5h\t\u0016\u001c\u0017.\\1m\u0013N4%/Y2uS>t\u0017\r\\\n\t\u0003K\u0012\tA\"!\u0007\u0004B!11HA'!\u0019\t\u0019.b#\u0007HQ1aq\tDD\r\u0013C\u0001B!\u000f\u0002j\u0001\u0007aq\t\u0005\t\u0005{\tI\u00071\u0001\u0007H\t1\")[4EK\u000eLW.\u00197Bg&3\u0017J\u001c;fOJ\fGn\u0005\u0005\u0002l\t\u0005a\u0011\u0011DH!\u0019\t\u0019na\u0013\u0007HQ1aq\tDJ\r+C\u0001B!\u000f\u0002p\u0001\u0007aq\t\u0005\t\u0005{\ty\u00071\u0001\u0007HQ1aq\tDM\r7C\u0001B!\u000f\u0002r\u0001\u0007aq\t\u0005\t\u0005{\t\t\b1\u0001\u0007H\u00051\")[4EK\u000eLW.\u00197Jg\u001a\u0013\u0018m\u0019;j_:\fG\u000e\u0005\u0003\u0004<\u0005U4\u0003CA;\u0005\u00031\u0019K\"*\u0011\t\rm\u0012Q\r\t\u0005\u0007?39+\u0003\u0003\u0007*\u000e%&A\u0005\"jO\u0012+7-[7bY>\u0013H-\u001a:j]\u001e$\"Ab(\u0002-\tKw\rR3dS6\fG.Q:JM&sG/Z4sC2\u0004Baa\u000f\u0002~MA\u0011Q\u0010B\u0001\rg3)\u000b\u0005\u0003\u0004<\u0005-DC\u0001DX\u0001")
public interface Numeric<T>
extends Ordering<T> {
    public static <T> Numeric<T> apply(Numeric<T> num) {
        return num;
    }

    public T plus(T var1, T var2);

    public T minus(T var1, T var2);

    public T times(T var1, T var2);

    public T negate(T var1);

    public T fromInt(int var1);

    public Option<T> parseString(String var1);

    public int toInt(T var1);

    public long toLong(T var1);

    public float toFloat(T var1);

    public double toDouble(T var1);

    public static /* synthetic */ Object zero$(Numeric $this) {
        return $this.zero();
    }

    default public T zero() {
        return this.fromInt(0);
    }

    public static /* synthetic */ Object one$(Numeric $this) {
        return $this.one();
    }

    default public T one() {
        return this.fromInt(1);
    }

    public static /* synthetic */ Object abs$(Numeric $this, Object x) {
        return $this.abs(x);
    }

    default public T abs(T x) {
        if (this.lt(x, this.zero())) {
            return this.negate(x);
        }
        return x;
    }

    public static /* synthetic */ int signum$(Numeric $this, Object x) {
        return $this.signum(x);
    }

    default public int signum(T x) {
        if (this.lt(x, this.zero())) {
            return -1;
        }
        if (this.gt(x, this.zero())) {
            return 1;
        }
        return 0;
    }

    public static /* synthetic */ Object sign$(Numeric $this, Object x) {
        return $this.sign(x);
    }

    default public T sign(T x) {
        if (this.lt(x, this.zero())) {
            return this.negate(this.one());
        }
        if (this.gt(x, this.zero())) {
            return this.one();
        }
        return this.zero();
    }

    public static /* synthetic */ NumericOps mkNumericOps$(Numeric $this, Object lhs) {
        return $this.mkNumericOps(lhs);
    }

    default public NumericOps mkNumericOps(T lhs) {
        return new NumericOps(this, lhs);
    }

    public static void $init$(Numeric $this) {
    }

    public static interface BigDecimalAsIfIntegral
    extends BigDecimalIsConflicted,
    Integral<BigDecimal> {
        public static /* synthetic */ BigDecimal quot$(BigDecimalAsIfIntegral $this, BigDecimal x, BigDecimal y) {
            return $this.quot(x, y);
        }

        @Override
        default public BigDecimal quot(BigDecimal x, BigDecimal y) {
            return x.quot(y);
        }

        public static /* synthetic */ BigDecimal rem$(BigDecimalAsIfIntegral $this, BigDecimal x, BigDecimal y) {
            return $this.rem(x, y);
        }

        @Override
        default public BigDecimal rem(BigDecimal x, BigDecimal y) {
            return x.remainder(y);
        }

        public static void $init$(BigDecimalAsIfIntegral $this) {
        }
    }

    public static interface BigDecimalIsConflicted
    extends Numeric<BigDecimal> {
        public static /* synthetic */ BigDecimal plus$(BigDecimalIsConflicted $this, BigDecimal x, BigDecimal y) {
            return $this.plus(x, y);
        }

        @Override
        default public BigDecimal plus(BigDecimal x, BigDecimal y) {
            return x.$plus(y);
        }

        public static /* synthetic */ BigDecimal minus$(BigDecimalIsConflicted $this, BigDecimal x, BigDecimal y) {
            return $this.minus(x, y);
        }

        @Override
        default public BigDecimal minus(BigDecimal x, BigDecimal y) {
            return x.$minus(y);
        }

        public static /* synthetic */ BigDecimal times$(BigDecimalIsConflicted $this, BigDecimal x, BigDecimal y) {
            return $this.times(x, y);
        }

        @Override
        default public BigDecimal times(BigDecimal x, BigDecimal y) {
            return x.$times(y);
        }

        public static /* synthetic */ BigDecimal negate$(BigDecimalIsConflicted $this, BigDecimal x) {
            return $this.negate(x);
        }

        @Override
        default public BigDecimal negate(BigDecimal x) {
            return x.unary_$minus();
        }

        public static /* synthetic */ BigDecimal fromInt$(BigDecimalIsConflicted $this, int x) {
            return $this.fromInt(x);
        }

        @Override
        default public BigDecimal fromInt(int x) {
            return BigDecimal$.MODULE$.apply(x);
        }

        public static /* synthetic */ Option parseString$(BigDecimalIsConflicted $this, String str) {
            return $this.parseString(str);
        }

        @Override
        default public Option<BigDecimal> parseString(String str) {
            Option<Throwable> option;
            Try try_;
            try {
                try_ = new Success<BigDecimal>(BigDecimal$.MODULE$.exact(str));
            }
            catch (Throwable throwable) {
                if (throwable != null && !(option = NonFatal$.MODULE$.unapply(throwable)).isEmpty()) {
                    Throwable apply_e = option.get();
                    try_ = new Failure(apply_e);
                }
                throw throwable;
            }
            Object var2_3 = null;
            option = null;
            Object var4_5 = null;
            return ((Try)try_).toOption();
        }

        public static /* synthetic */ int toInt$(BigDecimalIsConflicted $this, BigDecimal x) {
            return $this.toInt(x);
        }

        @Override
        default public int toInt(BigDecimal x) {
            return x.intValue();
        }

        public static /* synthetic */ long toLong$(BigDecimalIsConflicted $this, BigDecimal x) {
            return $this.toLong(x);
        }

        @Override
        default public long toLong(BigDecimal x) {
            return x.longValue();
        }

        public static /* synthetic */ float toFloat$(BigDecimalIsConflicted $this, BigDecimal x) {
            return $this.toFloat(x);
        }

        @Override
        default public float toFloat(BigDecimal x) {
            return x.floatValue();
        }

        public static /* synthetic */ double toDouble$(BigDecimalIsConflicted $this, BigDecimal x) {
            return $this.toDouble(x);
        }

        @Override
        default public double toDouble(BigDecimal x) {
            return x.doubleValue();
        }

        public static /* synthetic */ BigDecimal $anonfun$parseString$3(String str$3) {
            return BigDecimal$.MODULE$.apply(str$3);
        }

        public static void $init$(BigDecimalIsConflicted $this) {
        }
    }

    public static interface BigDecimalIsFractional
    extends BigDecimalIsConflicted,
    Fractional<BigDecimal> {
        public static /* synthetic */ BigDecimal div$(BigDecimalIsFractional $this, BigDecimal x, BigDecimal y) {
            return $this.div(x, y);
        }

        @Override
        default public BigDecimal div(BigDecimal x, BigDecimal y) {
            return x.$div(y);
        }

        public static void $init$(BigDecimalIsFractional $this) {
        }
    }

    public static interface BigIntIsIntegral
    extends Integral<BigInt> {
        public static /* synthetic */ BigInt plus$(BigIntIsIntegral $this, BigInt x, BigInt y) {
            return $this.plus(x, y);
        }

        @Override
        default public BigInt plus(BigInt x, BigInt y) {
            return x.$plus(y);
        }

        public static /* synthetic */ BigInt minus$(BigIntIsIntegral $this, BigInt x, BigInt y) {
            return $this.minus(x, y);
        }

        @Override
        default public BigInt minus(BigInt x, BigInt y) {
            return x.$minus(y);
        }

        public static /* synthetic */ BigInt times$(BigIntIsIntegral $this, BigInt x, BigInt y) {
            return $this.times(x, y);
        }

        @Override
        default public BigInt times(BigInt x, BigInt y) {
            return x.$times(y);
        }

        public static /* synthetic */ BigInt quot$(BigIntIsIntegral $this, BigInt x, BigInt y) {
            return $this.quot(x, y);
        }

        @Override
        default public BigInt quot(BigInt x, BigInt y) {
            return x.$div(y);
        }

        public static /* synthetic */ BigInt rem$(BigIntIsIntegral $this, BigInt x, BigInt y) {
            return $this.rem(x, y);
        }

        @Override
        default public BigInt rem(BigInt x, BigInt y) {
            return x.$percent(y);
        }

        public static /* synthetic */ BigInt negate$(BigIntIsIntegral $this, BigInt x) {
            return $this.negate(x);
        }

        @Override
        default public BigInt negate(BigInt x) {
            return x.unary_$minus();
        }

        public static /* synthetic */ BigInt fromInt$(BigIntIsIntegral $this, int x) {
            return $this.fromInt(x);
        }

        @Override
        default public BigInt fromInt(int x) {
            return BigInt$.MODULE$.apply(x);
        }

        public static /* synthetic */ Option parseString$(BigIntIsIntegral $this, String str) {
            return $this.parseString(str);
        }

        @Override
        default public Option<BigInt> parseString(String str) {
            Option<Throwable> option;
            Try try_;
            try {
                try_ = new Success<BigInt>(BigInt$.MODULE$.apply(str));
            }
            catch (Throwable throwable) {
                if (throwable != null && !(option = NonFatal$.MODULE$.unapply(throwable)).isEmpty()) {
                    Throwable apply_e = option.get();
                    try_ = new Failure(apply_e);
                }
                throw throwable;
            }
            Object var2_3 = null;
            option = null;
            Object var4_5 = null;
            return ((Try)try_).toOption();
        }

        public static /* synthetic */ int toInt$(BigIntIsIntegral $this, BigInt x) {
            return $this.toInt(x);
        }

        @Override
        default public int toInt(BigInt x) {
            return x.intValue();
        }

        public static /* synthetic */ long toLong$(BigIntIsIntegral $this, BigInt x) {
            return $this.toLong(x);
        }

        @Override
        default public long toLong(BigInt x) {
            return x.longValue();
        }

        public static /* synthetic */ float toFloat$(BigIntIsIntegral $this, BigInt x) {
            return $this.toFloat(x);
        }

        @Override
        default public float toFloat(BigInt x) {
            return x.floatValue();
        }

        public static /* synthetic */ double toDouble$(BigIntIsIntegral $this, BigInt x) {
            return $this.toDouble(x);
        }

        @Override
        default public double toDouble(BigInt x) {
            return x.doubleValue();
        }

        public static /* synthetic */ BigInt $anonfun$parseString$1(String str$1) {
            return BigInt$.MODULE$.apply(str$1);
        }

        public static void $init$(BigIntIsIntegral $this) {
        }
    }

    public static interface ByteIsIntegral
    extends Integral<Object> {
        public static /* synthetic */ byte plus$(ByteIsIntegral $this, byte x, byte y) {
            return $this.plus(x, y);
        }

        @Override
        default public byte plus(byte x, byte y) {
            return (byte)(x + y);
        }

        public static /* synthetic */ byte minus$(ByteIsIntegral $this, byte x, byte y) {
            return $this.minus(x, y);
        }

        @Override
        default public byte minus(byte x, byte y) {
            return (byte)(x - y);
        }

        public static /* synthetic */ byte times$(ByteIsIntegral $this, byte x, byte y) {
            return $this.times(x, y);
        }

        @Override
        default public byte times(byte x, byte y) {
            return (byte)(x * y);
        }

        public static /* synthetic */ byte quot$(ByteIsIntegral $this, byte x, byte y) {
            return $this.quot(x, y);
        }

        @Override
        default public byte quot(byte x, byte y) {
            return (byte)(x / y);
        }

        public static /* synthetic */ byte rem$(ByteIsIntegral $this, byte x, byte y) {
            return $this.rem(x, y);
        }

        @Override
        default public byte rem(byte x, byte y) {
            return (byte)(x % y);
        }

        public static /* synthetic */ byte negate$(ByteIsIntegral $this, byte x) {
            return $this.negate(x);
        }

        @Override
        default public byte negate(byte x) {
            return -x;
        }

        public static /* synthetic */ byte fromInt$(ByteIsIntegral $this, int x) {
            return $this.fromInt(x);
        }

        @Override
        default public byte fromInt(int x) {
            return (byte)x;
        }

        public static /* synthetic */ Option parseString$(ByteIsIntegral $this, String str) {
            return $this.parseString(str);
        }

        @Override
        default public Option<Object> parseString(String str) {
            return StringParsers$.MODULE$.parseByte(str);
        }

        public static /* synthetic */ int toInt$(ByteIsIntegral $this, byte x) {
            return $this.toInt(x);
        }

        @Override
        default public int toInt(byte x) {
            return x;
        }

        public static /* synthetic */ long toLong$(ByteIsIntegral $this, byte x) {
            return $this.toLong(x);
        }

        @Override
        default public long toLong(byte x) {
            return x;
        }

        public static /* synthetic */ float toFloat$(ByteIsIntegral $this, byte x) {
            return $this.toFloat(x);
        }

        @Override
        default public float toFloat(byte x) {
            return x;
        }

        public static /* synthetic */ double toDouble$(ByteIsIntegral $this, byte x) {
            return $this.toDouble(x);
        }

        @Override
        default public double toDouble(byte x) {
            return x;
        }

        public static /* synthetic */ int signum$(ByteIsIntegral $this, byte x) {
            return $this.signum(x);
        }

        @Override
        default public int signum(byte x) {
            return Integer.signum(x);
        }

        public static /* synthetic */ byte sign$(ByteIsIntegral $this, byte x) {
            return $this.sign(x);
        }

        @Override
        default public byte sign(byte x) {
            return (byte)Integer.signum(x);
        }

        public static void $init$(ByteIsIntegral $this) {
        }
    }

    public static interface CharIsIntegral
    extends Integral<Object> {
        public static /* synthetic */ char plus$(CharIsIntegral $this, char x, char y) {
            return $this.plus(x, y);
        }

        @Override
        default public char plus(char x, char y) {
            return (char)(x + y);
        }

        public static /* synthetic */ char minus$(CharIsIntegral $this, char x, char y) {
            return $this.minus(x, y);
        }

        @Override
        default public char minus(char x, char y) {
            return (char)(x - y);
        }

        public static /* synthetic */ char times$(CharIsIntegral $this, char x, char y) {
            return $this.times(x, y);
        }

        @Override
        default public char times(char x, char y) {
            return (char)(x * y);
        }

        public static /* synthetic */ char quot$(CharIsIntegral $this, char x, char y) {
            return $this.quot(x, y);
        }

        @Override
        default public char quot(char x, char y) {
            return (char)(x / y);
        }

        public static /* synthetic */ char rem$(CharIsIntegral $this, char x, char y) {
            return $this.rem(x, y);
        }

        @Override
        default public char rem(char x, char y) {
            return (char)(x % y);
        }

        public static /* synthetic */ char negate$(CharIsIntegral $this, char x) {
            return $this.negate(x);
        }

        @Override
        default public char negate(char x) {
            return -x;
        }

        public static /* synthetic */ char fromInt$(CharIsIntegral $this, int x) {
            return $this.fromInt(x);
        }

        @Override
        default public char fromInt(int x) {
            return (char)x;
        }

        public static /* synthetic */ Option parseString$(CharIsIntegral $this, String str) {
            return $this.parseString(str);
        }

        @Override
        default public Option<Object> parseString(String str) {
            Option<Throwable> option;
            Try try_;
            try {
                try_ = new Success<Character>(BoxesRunTime.boxToCharacter(CharIsIntegral.$anonfun$parseString$2(str)));
            }
            catch (Throwable throwable) {
                if (throwable != null && !(option = NonFatal$.MODULE$.unapply(throwable)).isEmpty()) {
                    Throwable apply_e = option.get();
                    try_ = new Failure(apply_e);
                }
                throw throwable;
            }
            Object var2_3 = null;
            option = null;
            Object var4_5 = null;
            return ((Try)try_).toOption();
        }

        public static /* synthetic */ int toInt$(CharIsIntegral $this, char x) {
            return $this.toInt(x);
        }

        @Override
        default public int toInt(char x) {
            return x;
        }

        public static /* synthetic */ long toLong$(CharIsIntegral $this, char x) {
            return $this.toLong(x);
        }

        @Override
        default public long toLong(char x) {
            return x;
        }

        public static /* synthetic */ float toFloat$(CharIsIntegral $this, char x) {
            return $this.toFloat(x);
        }

        @Override
        default public float toFloat(char x) {
            return x;
        }

        public static /* synthetic */ double toDouble$(CharIsIntegral $this, char x) {
            return $this.toDouble(x);
        }

        @Override
        default public double toDouble(char x) {
            return x;
        }

        public static /* synthetic */ int signum$(CharIsIntegral $this, char x) {
            return $this.signum(x);
        }

        @Override
        default public int signum(char x) {
            return Integer.signum(x);
        }

        public static /* synthetic */ char sign$(CharIsIntegral $this, char x) {
            return $this.sign(x);
        }

        @Override
        default public char sign(char x) {
            return (char)Integer.signum(x);
        }

        public static /* synthetic */ char $anonfun$parseString$2(String str$2) {
            return (char)Integer.parseInt(str$2);
        }

        public static void $init$(CharIsIntegral $this) {
        }
    }

    public static interface DoubleIsFractional
    extends Fractional<Object> {
        public static /* synthetic */ double plus$(DoubleIsFractional $this, double x, double y) {
            return $this.plus(x, y);
        }

        @Override
        default public double plus(double x, double y) {
            return x + y;
        }

        public static /* synthetic */ double minus$(DoubleIsFractional $this, double x, double y) {
            return $this.minus(x, y);
        }

        @Override
        default public double minus(double x, double y) {
            return x - y;
        }

        public static /* synthetic */ double times$(DoubleIsFractional $this, double x, double y) {
            return $this.times(x, y);
        }

        @Override
        default public double times(double x, double y) {
            return x * y;
        }

        public static /* synthetic */ double negate$(DoubleIsFractional $this, double x) {
            return $this.negate(x);
        }

        @Override
        default public double negate(double x) {
            return -x;
        }

        public static /* synthetic */ double fromInt$(DoubleIsFractional $this, int x) {
            return $this.fromInt(x);
        }

        @Override
        default public double fromInt(int x) {
            return x;
        }

        public static /* synthetic */ Option parseString$(DoubleIsFractional $this, String str) {
            return $this.parseString(str);
        }

        @Override
        default public Option<Object> parseString(String str) {
            if (StringParsers$.MODULE$.checkFloatFormat(str)) {
                return new Some<Object>(Double.parseDouble(str));
            }
            return None$.MODULE$;
        }

        public static /* synthetic */ int toInt$(DoubleIsFractional $this, double x) {
            return $this.toInt(x);
        }

        @Override
        default public int toInt(double x) {
            return (int)x;
        }

        public static /* synthetic */ long toLong$(DoubleIsFractional $this, double x) {
            return $this.toLong(x);
        }

        @Override
        default public long toLong(double x) {
            return (long)x;
        }

        public static /* synthetic */ float toFloat$(DoubleIsFractional $this, double x) {
            return $this.toFloat(x);
        }

        @Override
        default public float toFloat(double x) {
            return (float)x;
        }

        public static /* synthetic */ double toDouble$(DoubleIsFractional $this, double x) {
            return $this.toDouble(x);
        }

        @Override
        default public double toDouble(double x) {
            return x;
        }

        public static /* synthetic */ double div$(DoubleIsFractional $this, double x, double y) {
            return $this.div(x, y);
        }

        @Override
        default public double div(double x, double y) {
            return x / y;
        }

        public static /* synthetic */ double abs$(DoubleIsFractional $this, double x) {
            return $this.abs(x);
        }

        @Override
        default public double abs(double x) {
            return Math.abs(x);
        }

        public static /* synthetic */ double sign$(DoubleIsFractional $this, double x) {
            return $this.sign(x);
        }

        @Override
        default public double sign(double x) {
            return Math.signum(x);
        }

        public static void $init$(DoubleIsFractional $this) {
        }
    }

    public static interface ExtraImplicits {
        public static /* synthetic */ NumericOps infixNumericOps$(ExtraImplicits $this, Object x, Numeric num) {
            return $this.infixNumericOps(x, num);
        }

        default public <T> NumericOps infixNumericOps(T x, Numeric<T> num) {
            return new NumericOps(num, x);
        }

        public static void $init$(ExtraImplicits $this) {
        }
    }

    public static interface FloatIsFractional
    extends Fractional<Object> {
        public static /* synthetic */ float plus$(FloatIsFractional $this, float x, float y) {
            return $this.plus(x, y);
        }

        @Override
        default public float plus(float x, float y) {
            return x + y;
        }

        public static /* synthetic */ float minus$(FloatIsFractional $this, float x, float y) {
            return $this.minus(x, y);
        }

        @Override
        default public float minus(float x, float y) {
            return x - y;
        }

        public static /* synthetic */ float times$(FloatIsFractional $this, float x, float y) {
            return $this.times(x, y);
        }

        @Override
        default public float times(float x, float y) {
            return x * y;
        }

        public static /* synthetic */ float negate$(FloatIsFractional $this, float x) {
            return $this.negate(x);
        }

        @Override
        default public float negate(float x) {
            return -x;
        }

        public static /* synthetic */ float fromInt$(FloatIsFractional $this, int x) {
            return $this.fromInt(x);
        }

        @Override
        default public float fromInt(int x) {
            return x;
        }

        public static /* synthetic */ Option parseString$(FloatIsFractional $this, String str) {
            return $this.parseString(str);
        }

        @Override
        default public Option<Object> parseString(String str) {
            if (StringParsers$.MODULE$.checkFloatFormat(str)) {
                return new Some<Object>(Float.valueOf(Float.parseFloat(str)));
            }
            return None$.MODULE$;
        }

        public static /* synthetic */ int toInt$(FloatIsFractional $this, float x) {
            return $this.toInt(x);
        }

        @Override
        default public int toInt(float x) {
            return (int)x;
        }

        public static /* synthetic */ long toLong$(FloatIsFractional $this, float x) {
            return $this.toLong(x);
        }

        @Override
        default public long toLong(float x) {
            return (long)x;
        }

        public static /* synthetic */ float toFloat$(FloatIsFractional $this, float x) {
            return $this.toFloat(x);
        }

        @Override
        default public float toFloat(float x) {
            return x;
        }

        public static /* synthetic */ double toDouble$(FloatIsFractional $this, float x) {
            return $this.toDouble(x);
        }

        @Override
        default public double toDouble(float x) {
            return x;
        }

        public static /* synthetic */ float div$(FloatIsFractional $this, float x, float y) {
            return $this.div(x, y);
        }

        @Override
        default public float div(float x, float y) {
            return x / y;
        }

        public static /* synthetic */ float abs$(FloatIsFractional $this, float x) {
            return $this.abs(x);
        }

        @Override
        default public float abs(float x) {
            return Math.abs(x);
        }

        public static /* synthetic */ float sign$(FloatIsFractional $this, float x) {
            return $this.sign(x);
        }

        @Override
        default public float sign(float x) {
            return Math.signum(x);
        }

        public static void $init$(FloatIsFractional $this) {
        }
    }

    public static interface IntIsIntegral
    extends Integral<Object> {
        public static /* synthetic */ int plus$(IntIsIntegral $this, int x, int y) {
            return $this.plus(x, y);
        }

        @Override
        default public int plus(int x, int y) {
            return x + y;
        }

        public static /* synthetic */ int minus$(IntIsIntegral $this, int x, int y) {
            return $this.minus(x, y);
        }

        @Override
        default public int minus(int x, int y) {
            return x - y;
        }

        public static /* synthetic */ int times$(IntIsIntegral $this, int x, int y) {
            return $this.times(x, y);
        }

        @Override
        default public int times(int x, int y) {
            return x * y;
        }

        public static /* synthetic */ int quot$(IntIsIntegral $this, int x, int y) {
            return $this.quot(x, y);
        }

        @Override
        default public int quot(int x, int y) {
            return x / y;
        }

        public static /* synthetic */ int rem$(IntIsIntegral $this, int x, int y) {
            return $this.rem(x, y);
        }

        @Override
        default public int rem(int x, int y) {
            return x % y;
        }

        public static /* synthetic */ int negate$(IntIsIntegral $this, int x) {
            return $this.negate(x);
        }

        @Override
        default public int negate(int x) {
            return -x;
        }

        public static /* synthetic */ int fromInt$(IntIsIntegral $this, int x) {
            return $this.fromInt(x);
        }

        @Override
        default public int fromInt(int x) {
            return x;
        }

        public static /* synthetic */ Option parseString$(IntIsIntegral $this, String str) {
            return $this.parseString(str);
        }

        @Override
        default public Option<Object> parseString(String str) {
            return StringParsers$.MODULE$.parseInt(str);
        }

        public static /* synthetic */ int toInt$(IntIsIntegral $this, int x) {
            return $this.toInt(x);
        }

        @Override
        default public int toInt(int x) {
            return x;
        }

        public static /* synthetic */ long toLong$(IntIsIntegral $this, int x) {
            return $this.toLong(x);
        }

        @Override
        default public long toLong(int x) {
            return x;
        }

        public static /* synthetic */ float toFloat$(IntIsIntegral $this, int x) {
            return $this.toFloat(x);
        }

        @Override
        default public float toFloat(int x) {
            return x;
        }

        public static /* synthetic */ double toDouble$(IntIsIntegral $this, int x) {
            return $this.toDouble(x);
        }

        @Override
        default public double toDouble(int x) {
            return x;
        }

        public static /* synthetic */ int signum$(IntIsIntegral $this, int x) {
            return $this.signum(x);
        }

        @Override
        default public int signum(int x) {
            return Integer.signum(x);
        }

        public static /* synthetic */ int sign$(IntIsIntegral $this, int x) {
            return $this.sign(x);
        }

        @Override
        default public int sign(int x) {
            return Integer.signum(x);
        }

        public static void $init$(IntIsIntegral $this) {
        }
    }

    public static interface LongIsIntegral
    extends Integral<Object> {
        public static /* synthetic */ long plus$(LongIsIntegral $this, long x, long y) {
            return $this.plus(x, y);
        }

        @Override
        default public long plus(long x, long y) {
            return x + y;
        }

        public static /* synthetic */ long minus$(LongIsIntegral $this, long x, long y) {
            return $this.minus(x, y);
        }

        @Override
        default public long minus(long x, long y) {
            return x - y;
        }

        public static /* synthetic */ long times$(LongIsIntegral $this, long x, long y) {
            return $this.times(x, y);
        }

        @Override
        default public long times(long x, long y) {
            return x * y;
        }

        public static /* synthetic */ long quot$(LongIsIntegral $this, long x, long y) {
            return $this.quot(x, y);
        }

        @Override
        default public long quot(long x, long y) {
            return x / y;
        }

        public static /* synthetic */ long rem$(LongIsIntegral $this, long x, long y) {
            return $this.rem(x, y);
        }

        @Override
        default public long rem(long x, long y) {
            return x % y;
        }

        public static /* synthetic */ long negate$(LongIsIntegral $this, long x) {
            return $this.negate(x);
        }

        @Override
        default public long negate(long x) {
            return -x;
        }

        public static /* synthetic */ long fromInt$(LongIsIntegral $this, int x) {
            return $this.fromInt(x);
        }

        @Override
        default public long fromInt(int x) {
            return x;
        }

        public static /* synthetic */ Option parseString$(LongIsIntegral $this, String str) {
            return $this.parseString(str);
        }

        @Override
        default public Option<Object> parseString(String str) {
            return StringParsers$.MODULE$.parseLong(str);
        }

        public static /* synthetic */ int toInt$(LongIsIntegral $this, long x) {
            return $this.toInt(x);
        }

        @Override
        default public int toInt(long x) {
            return (int)x;
        }

        public static /* synthetic */ long toLong$(LongIsIntegral $this, long x) {
            return $this.toLong(x);
        }

        @Override
        default public long toLong(long x) {
            return x;
        }

        public static /* synthetic */ float toFloat$(LongIsIntegral $this, long x) {
            return $this.toFloat(x);
        }

        @Override
        default public float toFloat(long x) {
            return x;
        }

        public static /* synthetic */ double toDouble$(LongIsIntegral $this, long x) {
            return $this.toDouble(x);
        }

        @Override
        default public double toDouble(long x) {
            return x;
        }

        public static /* synthetic */ int signum$(LongIsIntegral $this, long x) {
            return $this.signum(x);
        }

        @Override
        default public int signum(long x) {
            return (int)((long)Long.signum(x));
        }

        public static /* synthetic */ long sign$(LongIsIntegral $this, long x) {
            return $this.sign(x);
        }

        @Override
        default public long sign(long x) {
            return Long.signum(x);
        }

        public static void $init$(LongIsIntegral $this) {
        }
    }

    public class NumericOps {
        private final T lhs;
        public final /* synthetic */ Numeric $outer;

        public T $plus(T rhs) {
            return this.scala$math$Numeric$NumericOps$$$outer().plus(this.lhs, rhs);
        }

        public T $minus(T rhs) {
            return this.scala$math$Numeric$NumericOps$$$outer().minus(this.lhs, rhs);
        }

        public T $times(T rhs) {
            return this.scala$math$Numeric$NumericOps$$$outer().times(this.lhs, rhs);
        }

        public T unary_$minus() {
            return this.scala$math$Numeric$NumericOps$$$outer().negate(this.lhs);
        }

        public T abs() {
            return this.scala$math$Numeric$NumericOps$$$outer().abs(this.lhs);
        }

        public int signum() {
            return this.scala$math$Numeric$NumericOps$$$outer().signum(this.lhs);
        }

        public T sign() {
            return this.scala$math$Numeric$NumericOps$$$outer().sign(this.lhs);
        }

        public int toInt() {
            return this.scala$math$Numeric$NumericOps$$$outer().toInt(this.lhs);
        }

        public long toLong() {
            return this.scala$math$Numeric$NumericOps$$$outer().toLong(this.lhs);
        }

        public float toFloat() {
            return this.scala$math$Numeric$NumericOps$$$outer().toFloat(this.lhs);
        }

        public double toDouble() {
            return this.scala$math$Numeric$NumericOps$$$outer().toDouble(this.lhs);
        }

        public /* synthetic */ Numeric scala$math$Numeric$NumericOps$$$outer() {
            return this.$outer;
        }

        public NumericOps(Numeric $outer, T lhs) {
            this.lhs = lhs;
            if ($outer == null) {
                throw null;
            }
            this.$outer = $outer;
        }
    }

    public static interface ShortIsIntegral
    extends Integral<Object> {
        public static /* synthetic */ short plus$(ShortIsIntegral $this, short x, short y) {
            return $this.plus(x, y);
        }

        @Override
        default public short plus(short x, short y) {
            return (short)(x + y);
        }

        public static /* synthetic */ short minus$(ShortIsIntegral $this, short x, short y) {
            return $this.minus(x, y);
        }

        @Override
        default public short minus(short x, short y) {
            return (short)(x - y);
        }

        public static /* synthetic */ short times$(ShortIsIntegral $this, short x, short y) {
            return $this.times(x, y);
        }

        @Override
        default public short times(short x, short y) {
            return (short)(x * y);
        }

        public static /* synthetic */ short quot$(ShortIsIntegral $this, short x, short y) {
            return $this.quot(x, y);
        }

        @Override
        default public short quot(short x, short y) {
            return (short)(x / y);
        }

        public static /* synthetic */ short rem$(ShortIsIntegral $this, short x, short y) {
            return $this.rem(x, y);
        }

        @Override
        default public short rem(short x, short y) {
            return (short)(x % y);
        }

        public static /* synthetic */ short negate$(ShortIsIntegral $this, short x) {
            return $this.negate(x);
        }

        @Override
        default public short negate(short x) {
            return -x;
        }

        public static /* synthetic */ short fromInt$(ShortIsIntegral $this, int x) {
            return $this.fromInt(x);
        }

        @Override
        default public short fromInt(int x) {
            return (short)x;
        }

        public static /* synthetic */ Option parseString$(ShortIsIntegral $this, String str) {
            return $this.parseString(str);
        }

        @Override
        default public Option<Object> parseString(String str) {
            return StringParsers$.MODULE$.parseShort(str);
        }

        public static /* synthetic */ int toInt$(ShortIsIntegral $this, short x) {
            return $this.toInt(x);
        }

        @Override
        default public int toInt(short x) {
            return x;
        }

        public static /* synthetic */ long toLong$(ShortIsIntegral $this, short x) {
            return $this.toLong(x);
        }

        @Override
        default public long toLong(short x) {
            return x;
        }

        public static /* synthetic */ float toFloat$(ShortIsIntegral $this, short x) {
            return $this.toFloat(x);
        }

        @Override
        default public float toFloat(short x) {
            return x;
        }

        public static /* synthetic */ double toDouble$(ShortIsIntegral $this, short x) {
            return $this.toDouble(x);
        }

        @Override
        default public double toDouble(short x) {
            return x;
        }

        public static /* synthetic */ int signum$(ShortIsIntegral $this, short x) {
            return $this.signum(x);
        }

        @Override
        default public int signum(short x) {
            return Integer.signum(x);
        }

        public static /* synthetic */ short sign$(ShortIsIntegral $this, short x) {
            return $this.sign(x);
        }

        @Override
        default public short sign(short x) {
            return (short)Integer.signum(x);
        }

        public static void $init$(ShortIsIntegral $this) {
        }
    }
}

