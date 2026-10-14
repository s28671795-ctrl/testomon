/*
 * Decompiled with CFR 0.152.
 */
package scala;

import java.io.Serializable;
import scala.Option;
import scala.Product;
import scala.Product16;
import scala.Tuple16$;
import scala.collection.AbstractIterator;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.Statics;
import scala.util.hashing.MurmurHash3$;

@ScalaSignature(bytes="\u0006\u0005\u0015ug\u0001B!C\u0005\u0016C!\"a\u000b\u0001\u0005+\u0007I\u0011AA\u0017\u0011%\ty\u0003\u0001B\tB\u0003%a\n\u0003\u0006\u00022\u0001\u0011)\u001a!C\u0001\u0003gA\u0011\"!\u000e\u0001\u0005#\u0005\u000b\u0011B-\t\u0015\u0005]\u0002A!f\u0001\n\u0003\tI\u0004C\u0005\u0002<\u0001\u0011\t\u0012)A\u00059\"Q\u0011Q\b\u0001\u0003\u0016\u0004%\t!a\u0010\t\u0013\u0005\u0005\u0003A!E!\u0002\u0013y\u0006BCA\"\u0001\tU\r\u0011\"\u0001\u0002F!I\u0011q\t\u0001\u0003\u0012\u0003\u0006IA\u0019\u0005\u000b\u0003\u0013\u0002!Q3A\u0005\u0002\u0005-\u0003\"CA'\u0001\tE\t\u0015!\u0003f\u0011)\ty\u0005\u0001BK\u0002\u0013\u0005\u0011\u0011\u000b\u0005\n\u0003'\u0002!\u0011#Q\u0001\n!D!\"!\u0016\u0001\u0005+\u0007I\u0011AA,\u0011%\tI\u0006\u0001B\tB\u0003%1\u000e\u0003\u0006\u0002\\\u0001\u0011)\u001a!C\u0001\u0003;B\u0011\"a\u0018\u0001\u0005#\u0005\u000b\u0011\u00028\t\u0015\u0005\u0005\u0004A!f\u0001\n\u0003\t\u0019\u0007C\u0005\u0002f\u0001\u0011\t\u0012)A\u0005c\"Q\u0011q\r\u0001\u0003\u0016\u0004%\t!!\u001b\t\u0013\u0005-\u0004A!E!\u0002\u0013!\bBCA7\u0001\tU\r\u0011\"\u0001\u0002p!I\u0011\u0011\u000f\u0001\u0003\u0012\u0003\u0006Ia\u001e\u0005\u000b\u0003g\u0002!Q3A\u0005\u0002\u0005U\u0004\"CA<\u0001\tE\t\u0015!\u0003{\u0011)\tI\b\u0001BK\u0002\u0013\u0005\u00111\u0010\u0005\n\u0003{\u0002!\u0011#Q\u0001\nuD!\"a \u0001\u0005+\u0007I\u0011AAA\u0011)\t\u0019\t\u0001B\tB\u0003%\u0011\u0011\u0001\u0005\u000b\u0003\u000b\u0003!Q3A\u0005\u0002\u0005\u001d\u0005BCAE\u0001\tE\t\u0015!\u0003\u0002\b!9\u00111\u0012\u0001\u0005\u0002\u00055\u0005bBAY\u0001\u0011\u0005\u00131\u0017\u0005\n\u0003\u000b\u0004\u0011\u0011!C\u0001\u0003\u000fD\u0011B!\f\u0001#\u0003%\tAa\f\t\u0013\t\u001d\u0004!%A\u0005\u0002\t%\u0004\"\u0003BH\u0001E\u0005I\u0011\u0001BI\u0011%\u00119\fAI\u0001\n\u0003\u0011I\fC\u0005\u0003`\u0002\t\n\u0011\"\u0001\u0003b\"I1q\u0001\u0001\u0012\u0002\u0013\u00051\u0011\u0002\u0005\n\u0007_\u0001\u0011\u0013!C\u0001\u0007cA\u0011ba\u0016\u0001#\u0003%\ta!\u0017\t\u0013\r}\u0004!%A\u0005\u0002\r\u0005\u0005\"CBT\u0001E\u0005I\u0011ABU\u0011%\u0019y\rAI\u0001\n\u0003\u0019\t\u000eC\u0005\u0004x\u0002\t\n\u0011\"\u0001\u0004z\"IAq\u0004\u0001\u0012\u0002\u0013\u0005A\u0011\u0005\u0005\n\t\u000f\u0002\u0011\u0013!C\u0001\t\u0013B\u0011\u0002b\u001c\u0001#\u0003%\t\u0001\"\u001d\t\u0013\u0011]\u0005!%A\u0005\u0002\u0011e\u0005\"\u0003C`\u0001\u0005\u0005I\u0011\tCa\u0011%!\t\u000eAA\u0001\n\u0003\"\u0019\u000eC\u0005\u0005b\u0002\t\t\u0011\"\u0001\u0005d\"IAq\u001e\u0001\u0002\u0002\u0013\u0005C\u0011\u001f\u0005\n\tw\u0004\u0011\u0011!C!\t{D\u0011\u0002b@\u0001\u0003\u0003%\t%\"\u0001\b\u0013\u0015\u0015!)!A\t\u0002\u0015\u001da\u0001C!C\u0003\u0003E\t!\"\u0003\t\u000f\u0005-5\b\"\u0001\u0006\u0016!I\u0011\u0011W\u001e\u0002\u0002\u0013\u0015Sq\u0003\u0005\n\u000b3Y\u0014\u0011!CA\u000b7A\u0011\"\"!<\u0003\u0003%\t)b!\t\u0013\u0015M7(!A\u0005\n\u0015U'a\u0002+va2,\u0017G\u000e\u0006\u0002\u0007\u0006)1oY1mC\u000e\u0001Qc\u0005$Q5v\u00037MZ5m_J,\bp\u001f@\u0002\u0004\u0005%1c\u0002\u0001H\u0017\u00065\u00111\u0003\t\u0003\u0011&k\u0011AQ\u0005\u0003\u0015\n\u0013a!\u00118z%\u00164\u0007\u0003\u0006%M\u001dfcvLY3iW:\fHo\u001e>~\u0003\u0003\t9!\u0003\u0002N\u0005\nI\u0001K]8ek\u000e$\u0018G\u000e\t\u0003\u001fBc\u0001\u0001\u0002\u0004R\u0001\u0011\u0015\rA\u0015\u0002\u0003)F\n\"a\u0015,\u0011\u0005!#\u0016BA+C\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"\u0001S,\n\u0005a\u0013%aA!osB\u0011qJ\u0017\u0003\u00077\u0002!)\u0019\u0001*\u0003\u0005Q\u0013\u0004CA(^\t\u0019q\u0006\u0001\"b\u0001%\n\u0011Ak\r\t\u0003\u001f\u0002$a!\u0019\u0001\u0005\u0006\u0004\u0011&A\u0001+5!\ty5\r\u0002\u0004e\u0001\u0011\u0015\rA\u0015\u0002\u0003)V\u0002\"a\u00144\u0005\r\u001d\u0004AQ1\u0001S\u0005\t!f\u0007\u0005\u0002PS\u00121!\u000e\u0001CC\u0002I\u0013!\u0001V\u001c\u0011\u0005=cGAB7\u0001\t\u000b\u0007!K\u0001\u0002UqA\u0011qj\u001c\u0003\u0007a\u0002!)\u0019\u0001*\u0003\u0005QK\u0004CA(s\t\u0019\u0019\b\u0001\"b\u0001%\n\u0019A+\r\u0019\u0011\u0005=+HA\u0002<\u0001\t\u000b\u0007!KA\u0002UcE\u0002\"a\u0014=\u0005\re\u0004AQ1\u0001S\u0005\r!\u0016G\r\t\u0003\u001fn$a\u0001 \u0001\u0005\u0006\u0004\u0011&a\u0001+2gA\u0011qJ \u0003\u0007\u007f\u0002!)\u0019\u0001*\u0003\u0007Q\u000bD\u0007E\u0002P\u0003\u0007!q!!\u0002\u0001\t\u000b\u0007!KA\u0002UcU\u00022aTA\u0005\t\u001d\tY\u0001\u0001CC\u0002I\u00131\u0001V\u00197!\rA\u0015qB\u0005\u0004\u0003#\u0011%a\u0002)s_\u0012,8\r\u001e\t\u0005\u0003+\t)C\u0004\u0003\u0002\u0018\u0005\u0005b\u0002BA\r\u0003?i!!a\u0007\u000b\u0007\u0005uA)\u0001\u0004=e>|GOP\u0005\u0002\u0007&\u0019\u00111\u0005\"\u0002\u000fA\f7m[1hK&!\u0011qEA\u0015\u00051\u0019VM]5bY&T\u0018M\u00197f\u0015\r\t\u0019CQ\u0001\u0003?F*\u0012AT\u0001\u0004?F\u0002\u0013AA03+\u0005I\u0016aA03A\u0005\u0011qlM\u000b\u00029\u0006\u0019ql\r\u0011\u0002\u0005}#T#A0\u0002\u0007}#\u0004%\u0001\u0002`kU\t!-A\u0002`k\u0001\n!a\u0018\u001c\u0016\u0003\u0015\f1a\u0018\u001c!\u0003\tyv'F\u0001i\u0003\ryv\u0007I\u0001\u0003?b*\u0012a[\u0001\u0004?b\u0002\u0013AA0:+\u0005q\u0017aA0:A\u0005\u0019q,\r\u0019\u0016\u0003E\fAaX\u00191A\u0005\u0019q,M\u0019\u0016\u0003Q\fAaX\u00192A\u0005\u0019q,\r\u001a\u0016\u0003]\fAaX\u00193A\u0005\u0019q,M\u001a\u0016\u0003i\fAaX\u00194A\u0005\u0019q,\r\u001b\u0016\u0003u\fAaX\u00195A\u0005\u0019q,M\u001b\u0016\u0005\u0005\u0005\u0011\u0001B02k\u0001\n1aX\u00197+\t\t9!\u0001\u0003`cY\u0002\u0013A\u0002\u001fj]&$h\b\u0006\u0012\u0002\u0010\u0006E\u00151SAK\u0003/\u000bI*a'\u0002\u001e\u0006}\u0015\u0011UAR\u0003K\u000b9+!+\u0002,\u00065\u0016q\u0016\t\u0015\u0011\u0002q\u0015\fX0cK\"\\g.\u001d;xuv\f\t!a\u0002\t\r\u0005-\u0012\u00051\u0001O\u0011\u0019\t\t$\ta\u00013\"1\u0011qG\u0011A\u0002qCa!!\u0010\"\u0001\u0004y\u0006BBA\"C\u0001\u0007!\r\u0003\u0004\u0002J\u0005\u0002\r!\u001a\u0005\u0007\u0003\u001f\n\u0003\u0019\u00015\t\r\u0005U\u0013\u00051\u0001l\u0011\u0019\tY&\ta\u0001]\"1\u0011\u0011M\u0011A\u0002EDa!a\u001a\"\u0001\u0004!\bBBA7C\u0001\u0007q\u000f\u0003\u0004\u0002t\u0005\u0002\rA\u001f\u0005\u0007\u0003s\n\u0003\u0019A?\t\u000f\u0005}\u0014\u00051\u0001\u0002\u0002!9\u0011QQ\u0011A\u0002\u0005\u001d\u0011\u0001\u0003;p'R\u0014\u0018N\\4\u0015\u0005\u0005U\u0006\u0003BA\\\u0003\u007fsA!!/\u0002<B\u0019\u0011\u0011\u0004\"\n\u0007\u0005u&)\u0001\u0004Qe\u0016$WMZ\u0005\u0005\u0003\u0003\f\u0019M\u0001\u0004TiJLgn\u001a\u0006\u0004\u0003{\u0013\u0015\u0001B2paf,\"%!3\u0002P\u0006M\u0017q[An\u0003?\f\u0019/a:\u0002l\u0006=\u00181_A|\u0003w\fyPa\u0001\u0003\b\t-ACIAf\u0005\u001b\u0011yA!\u0005\u0003\u0014\tU!q\u0003B\r\u00057\u0011iBa\b\u0003\"\t\r\"Q\u0005B\u0014\u0005S\u0011Y\u0003\u0005\u0012I\u0001\u00055\u0017\u0011[Ak\u00033\fi.!9\u0002f\u0006%\u0018Q^Ay\u0003k\fI0!@\u0003\u0002\t\u0015!\u0011\u0002\t\u0004\u001f\u0006=G!B)$\u0005\u0004\u0011\u0006cA(\u0002T\u0012)1l\tb\u0001%B\u0019q*a6\u0005\u000by\u001b#\u0019\u0001*\u0011\u0007=\u000bY\u000eB\u0003bG\t\u0007!\u000bE\u0002P\u0003?$Q\u0001Z\u0012C\u0002I\u00032aTAr\t\u001597E1\u0001S!\ry\u0015q\u001d\u0003\u0006U\u000e\u0012\rA\u0015\t\u0004\u001f\u0006-H!B7$\u0005\u0004\u0011\u0006cA(\u0002p\u0012)\u0001o\tb\u0001%B\u0019q*a=\u0005\u000bM\u001c#\u0019\u0001*\u0011\u0007=\u000b9\u0010B\u0003wG\t\u0007!\u000bE\u0002P\u0003w$Q!_\u0012C\u0002I\u00032aTA\u0000\t\u0015a8E1\u0001S!\ry%1\u0001\u0003\u0006\u007f\u000e\u0012\rA\u0015\t\u0004\u001f\n\u001dAABA\u0003G\t\u0007!\u000bE\u0002P\u0005\u0017!a!a\u0003$\u0005\u0004\u0011\u0006\"CA\u0016GA\u0005\t\u0019AAg\u0011%\t\td\tI\u0001\u0002\u0004\t\t\u000eC\u0005\u00028\r\u0002\n\u00111\u0001\u0002V\"I\u0011QH\u0012\u0011\u0002\u0003\u0007\u0011\u0011\u001c\u0005\n\u0003\u0007\u001a\u0003\u0013!a\u0001\u0003;D\u0011\"!\u0013$!\u0003\u0005\r!!9\t\u0013\u0005=3\u0005%AA\u0002\u0005\u0015\b\"CA+GA\u0005\t\u0019AAu\u0011%\tYf\tI\u0001\u0002\u0004\ti\u000fC\u0005\u0002b\r\u0002\n\u00111\u0001\u0002r\"I\u0011qM\u0012\u0011\u0002\u0003\u0007\u0011Q\u001f\u0005\n\u0003[\u001a\u0003\u0013!a\u0001\u0003sD\u0011\"a\u001d$!\u0003\u0005\r!!@\t\u0013\u0005e4\u0005%AA\u0002\t\u0005\u0001\"CA@GA\u0005\t\u0019\u0001B\u0003\u0011%\t)i\tI\u0001\u0002\u0004\u0011I!\u0001\bd_BLH\u0005Z3gCVdG\u000fJ\u0019\u0016E\tE\"q\tB%\u0005\u0017\u0012iEa\u0014\u0003R\tM#Q\u000bB,\u00053\u0012YF!\u0018\u0003`\t\u0005$1\rB3+\t\u0011\u0019DK\u0002O\u0005kY#Aa\u000e\u0011\t\te\"1I\u0007\u0003\u0005wQAA!\u0010\u0003@\u0005IQO\\2iK\u000e\\W\r\u001a\u0006\u0004\u0005\u0003\u0012\u0015AC1o]>$\u0018\r^5p]&!!Q\tB\u001e\u0005E)hn\u00195fG.,GMV1sS\u0006t7-\u001a\u0003\u0006#\u0012\u0012\rA\u0015\u0003\u00067\u0012\u0012\rA\u0015\u0003\u0006=\u0012\u0012\rA\u0015\u0003\u0006C\u0012\u0012\rA\u0015\u0003\u0006I\u0012\u0012\rA\u0015\u0003\u0006O\u0012\u0012\rA\u0015\u0003\u0006U\u0012\u0012\rA\u0015\u0003\u0006[\u0012\u0012\rA\u0015\u0003\u0006a\u0012\u0012\rA\u0015\u0003\u0006g\u0012\u0012\rA\u0015\u0003\u0006m\u0012\u0012\rA\u0015\u0003\u0006s\u0012\u0012\rA\u0015\u0003\u0006y\u0012\u0012\rA\u0015\u0003\u0006\u007f\u0012\u0012\rA\u0015\u0003\u0007\u0003\u000b!#\u0019\u0001*\u0005\r\u0005-AE1\u0001S\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uII*\"Ea\u001b\u0003p\tE$1\u000fB;\u0005o\u0012IHa\u001f\u0003~\t}$\u0011\u0011BB\u0005\u000b\u00139I!#\u0003\f\n5UC\u0001B7U\rI&Q\u0007\u0003\u0006#\u0016\u0012\rA\u0015\u0003\u00067\u0016\u0012\rA\u0015\u0003\u0006=\u0016\u0012\rA\u0015\u0003\u0006C\u0016\u0012\rA\u0015\u0003\u0006I\u0016\u0012\rA\u0015\u0003\u0006O\u0016\u0012\rA\u0015\u0003\u0006U\u0016\u0012\rA\u0015\u0003\u0006[\u0016\u0012\rA\u0015\u0003\u0006a\u0016\u0012\rA\u0015\u0003\u0006g\u0016\u0012\rA\u0015\u0003\u0006m\u0016\u0012\rA\u0015\u0003\u0006s\u0016\u0012\rA\u0015\u0003\u0006y\u0016\u0012\rA\u0015\u0003\u0006\u007f\u0016\u0012\rA\u0015\u0003\u0007\u0003\u000b)#\u0019\u0001*\u0005\r\u0005-QE1\u0001S\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIM*\"Ea%\u0003\u0018\ne%1\u0014BO\u0005?\u0013\tKa)\u0003&\n\u001d&\u0011\u0016BV\u0005[\u0013yK!-\u00034\nUVC\u0001BKU\ra&Q\u0007\u0003\u0006#\u001a\u0012\rA\u0015\u0003\u00067\u001a\u0012\rA\u0015\u0003\u0006=\u001a\u0012\rA\u0015\u0003\u0006C\u001a\u0012\rA\u0015\u0003\u0006I\u001a\u0012\rA\u0015\u0003\u0006O\u001a\u0012\rA\u0015\u0003\u0006U\u001a\u0012\rA\u0015\u0003\u0006[\u001a\u0012\rA\u0015\u0003\u0006a\u001a\u0012\rA\u0015\u0003\u0006g\u001a\u0012\rA\u0015\u0003\u0006m\u001a\u0012\rA\u0015\u0003\u0006s\u001a\u0012\rA\u0015\u0003\u0006y\u001a\u0012\rA\u0015\u0003\u0006\u007f\u001a\u0012\rA\u0015\u0003\u0007\u0003\u000b1#\u0019\u0001*\u0005\r\u0005-aE1\u0001S\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIQ*\"Ea/\u0003@\n\u0005'1\u0019Bc\u0005\u000f\u0014IMa3\u0003N\n='\u0011\u001bBj\u0005+\u00149N!7\u0003\\\nuWC\u0001B_U\ry&Q\u0007\u0003\u0006#\u001e\u0012\rA\u0015\u0003\u00067\u001e\u0012\rA\u0015\u0003\u0006=\u001e\u0012\rA\u0015\u0003\u0006C\u001e\u0012\rA\u0015\u0003\u0006I\u001e\u0012\rA\u0015\u0003\u0006O\u001e\u0012\rA\u0015\u0003\u0006U\u001e\u0012\rA\u0015\u0003\u0006[\u001e\u0012\rA\u0015\u0003\u0006a\u001e\u0012\rA\u0015\u0003\u0006g\u001e\u0012\rA\u0015\u0003\u0006m\u001e\u0012\rA\u0015\u0003\u0006s\u001e\u0012\rA\u0015\u0003\u0006y\u001e\u0012\rA\u0015\u0003\u0006\u007f\u001e\u0012\rA\u0015\u0003\u0007\u0003\u000b9#\u0019\u0001*\u0005\r\u0005-qE1\u0001S\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIU*\"Ea9\u0003h\n%(1\u001eBw\u0005_\u0014\tPa=\u0003v\n](\u0011 B~\u0005{\u0014yp!\u0001\u0004\u0004\r\u0015QC\u0001BsU\r\u0011'Q\u0007\u0003\u0006#\"\u0012\rA\u0015\u0003\u00067\"\u0012\rA\u0015\u0003\u0006=\"\u0012\rA\u0015\u0003\u0006C\"\u0012\rA\u0015\u0003\u0006I\"\u0012\rA\u0015\u0003\u0006O\"\u0012\rA\u0015\u0003\u0006U\"\u0012\rA\u0015\u0003\u0006[\"\u0012\rA\u0015\u0003\u0006a\"\u0012\rA\u0015\u0003\u0006g\"\u0012\rA\u0015\u0003\u0006m\"\u0012\rA\u0015\u0003\u0006s\"\u0012\rA\u0015\u0003\u0006y\"\u0012\rA\u0015\u0003\u0006\u007f\"\u0012\rA\u0015\u0003\u0007\u0003\u000bA#\u0019\u0001*\u0005\r\u0005-\u0001F1\u0001S\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIY*\"ea\u0003\u0004\u0010\rE11CB\u000b\u0007/\u0019Iba\u0007\u0004\u001e\r}1\u0011EB\u0012\u0007K\u00199c!\u000b\u0004,\r5RCAB\u0007U\r)'Q\u0007\u0003\u0006#&\u0012\rA\u0015\u0003\u00067&\u0012\rA\u0015\u0003\u0006=&\u0012\rA\u0015\u0003\u0006C&\u0012\rA\u0015\u0003\u0006I&\u0012\rA\u0015\u0003\u0006O&\u0012\rA\u0015\u0003\u0006U&\u0012\rA\u0015\u0003\u0006[&\u0012\rA\u0015\u0003\u0006a&\u0012\rA\u0015\u0003\u0006g&\u0012\rA\u0015\u0003\u0006m&\u0012\rA\u0015\u0003\u0006s&\u0012\rA\u0015\u0003\u0006y&\u0012\rA\u0015\u0003\u0006\u007f&\u0012\rA\u0015\u0003\u0007\u0003\u000bI#\u0019\u0001*\u0005\r\u0005-\u0011F1\u0001S\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uI]*\"ea\r\u00048\re21HB\u001f\u0007\u007f\u0019\tea\u0011\u0004F\r\u001d3\u0011JB&\u0007\u001b\u001aye!\u0015\u0004T\rUSCAB\u001bU\rA'Q\u0007\u0003\u0006#*\u0012\rA\u0015\u0003\u00067*\u0012\rA\u0015\u0003\u0006=*\u0012\rA\u0015\u0003\u0006C*\u0012\rA\u0015\u0003\u0006I*\u0012\rA\u0015\u0003\u0006O*\u0012\rA\u0015\u0003\u0006U*\u0012\rA\u0015\u0003\u0006[*\u0012\rA\u0015\u0003\u0006a*\u0012\rA\u0015\u0003\u0006g*\u0012\rA\u0015\u0003\u0006m*\u0012\rA\u0015\u0003\u0006s*\u0012\rA\u0015\u0003\u0006y*\u0012\rA\u0015\u0003\u0006\u007f*\u0012\rA\u0015\u0003\u0007\u0003\u000bQ#\u0019\u0001*\u0005\r\u0005-!F1\u0001S\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIa*\"ea\u0017\u0004`\r\u000541MB3\u0007O\u001aIga\u001b\u0004n\r=4\u0011OB:\u0007k\u001a9h!\u001f\u0004|\ruTCAB/U\rY'Q\u0007\u0003\u0006#.\u0012\rA\u0015\u0003\u00067.\u0012\rA\u0015\u0003\u0006=.\u0012\rA\u0015\u0003\u0006C.\u0012\rA\u0015\u0003\u0006I.\u0012\rA\u0015\u0003\u0006O.\u0012\rA\u0015\u0003\u0006U.\u0012\rA\u0015\u0003\u0006[.\u0012\rA\u0015\u0003\u0006a.\u0012\rA\u0015\u0003\u0006g.\u0012\rA\u0015\u0003\u0006m.\u0012\rA\u0015\u0003\u0006s.\u0012\rA\u0015\u0003\u0006y.\u0012\rA\u0015\u0003\u0006\u007f.\u0012\rA\u0015\u0003\u0007\u0003\u000bY#\u0019\u0001*\u0005\r\u0005-1F1\u0001S\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIe*\"ea!\u0004\b\u000e%51RBG\u0007\u001f\u001b\tja%\u0004\u0016\u000e]5\u0011TBN\u0007;\u001byj!)\u0004$\u000e\u0015VCABCU\rq'Q\u0007\u0003\u0006#2\u0012\rA\u0015\u0003\u000672\u0012\rA\u0015\u0003\u0006=2\u0012\rA\u0015\u0003\u0006C2\u0012\rA\u0015\u0003\u0006I2\u0012\rA\u0015\u0003\u0006O2\u0012\rA\u0015\u0003\u0006U2\u0012\rA\u0015\u0003\u0006[2\u0012\rA\u0015\u0003\u0006a2\u0012\rA\u0015\u0003\u0006g2\u0012\rA\u0015\u0003\u0006m2\u0012\rA\u0015\u0003\u0006s2\u0012\rA\u0015\u0003\u0006y2\u0012\rA\u0015\u0003\u0006\u007f2\u0012\rA\u0015\u0003\u0007\u0003\u000ba#\u0019\u0001*\u0005\r\u0005-AF1\u0001S\u0003=\u0019w\u000e]=%I\u00164\u0017-\u001e7uIE\u0002TCIBV\u0007_\u001b\tla-\u00046\u000e]6\u0011XB^\u0007{\u001byl!1\u0004D\u000e\u00157qYBe\u0007\u0017\u001ci-\u0006\u0002\u0004.*\u001a\u0011O!\u000e\u0005\u000bEk#\u0019\u0001*\u0005\u000bmk#\u0019\u0001*\u0005\u000byk#\u0019\u0001*\u0005\u000b\u0005l#\u0019\u0001*\u0005\u000b\u0011l#\u0019\u0001*\u0005\u000b\u001dl#\u0019\u0001*\u0005\u000b)l#\u0019\u0001*\u0005\u000b5l#\u0019\u0001*\u0005\u000bAl#\u0019\u0001*\u0005\u000bMl#\u0019\u0001*\u0005\u000bYl#\u0019\u0001*\u0005\u000bel#\u0019\u0001*\u0005\u000bql#\u0019\u0001*\u0005\u000b}l#\u0019\u0001*\u0005\r\u0005\u0015QF1\u0001S\t\u0019\tY!\fb\u0001%\u0006y1m\u001c9zI\u0011,g-Y;mi\u0012\n\u0014'\u0006\u0012\u0004T\u000e]7\u0011\\Bn\u0007;\u001cyn!9\u0004d\u000e\u00158q]Bu\u0007W\u001cioa<\u0004r\u000eM8Q_\u000b\u0003\u0007+T3\u0001\u001eB\u001b\t\u0015\tfF1\u0001S\t\u0015YfF1\u0001S\t\u0015qfF1\u0001S\t\u0015\tgF1\u0001S\t\u0015!gF1\u0001S\t\u00159gF1\u0001S\t\u0015QgF1\u0001S\t\u0015igF1\u0001S\t\u0015\u0001hF1\u0001S\t\u0015\u0019hF1\u0001S\t\u00151hF1\u0001S\t\u0015IhF1\u0001S\t\u0015ahF1\u0001S\t\u0015yhF1\u0001S\t\u0019\t)A\fb\u0001%\u00121\u00111\u0002\u0018C\u0002I\u000bqbY8qs\u0012\"WMZ1vYR$\u0013GM\u000b#\u0007w\u001cy\u0010\"\u0001\u0005\u0004\u0011\u0015Aq\u0001C\u0005\t\u0017!i\u0001b\u0004\u0005\u0012\u0011MAQ\u0003C\f\t3!Y\u0002\"\b\u0016\u0005\ru(fA<\u00036\u0011)\u0011k\fb\u0001%\u0012)1l\fb\u0001%\u0012)al\fb\u0001%\u0012)\u0011m\fb\u0001%\u0012)Am\fb\u0001%\u0012)qm\fb\u0001%\u0012)!n\fb\u0001%\u0012)Qn\fb\u0001%\u0012)\u0001o\fb\u0001%\u0012)1o\fb\u0001%\u0012)ao\fb\u0001%\u0012)\u0011p\fb\u0001%\u0012)Ap\fb\u0001%\u0012)qp\fb\u0001%\u00121\u0011QA\u0018C\u0002I#a!a\u00030\u0005\u0004\u0011\u0016aD2paf$C-\u001a4bk2$H%M\u001a\u0016E\u0011\rBq\u0005C\u0015\tW!i\u0003b\f\u00052\u0011MBQ\u0007C\u001c\ts!Y\u0004\"\u0010\u0005@\u0011\u0005C1\tC#+\t!)CK\u0002{\u0005k!Q!\u0015\u0019C\u0002I#Qa\u0017\u0019C\u0002I#QA\u0018\u0019C\u0002I#Q!\u0019\u0019C\u0002I#Q\u0001\u001a\u0019C\u0002I#Qa\u001a\u0019C\u0002I#QA\u001b\u0019C\u0002I#Q!\u001c\u0019C\u0002I#Q\u0001\u001d\u0019C\u0002I#Qa\u001d\u0019C\u0002I#QA\u001e\u0019C\u0002I#Q!\u001f\u0019C\u0002I#Q\u0001 \u0019C\u0002I#Qa \u0019C\u0002I#a!!\u00021\u0005\u0004\u0011FABA\u0006a\t\u0007!+A\bd_BLH\u0005Z3gCVdG\u000fJ\u00195+\t\"Y\u0005b\u0014\u0005R\u0011MCQ\u000bC,\t3\"Y\u0006\"\u0018\u0005`\u0011\u0005D1\rC3\tO\"I\u0007b\u001b\u0005nU\u0011AQ\n\u0016\u0004{\nUB!B)2\u0005\u0004\u0011F!B.2\u0005\u0004\u0011F!\u000202\u0005\u0004\u0011F!B12\u0005\u0004\u0011F!\u000232\u0005\u0004\u0011F!B42\u0005\u0004\u0011F!\u000262\u0005\u0004\u0011F!B72\u0005\u0004\u0011F!\u000292\u0005\u0004\u0011F!B:2\u0005\u0004\u0011F!\u0002<2\u0005\u0004\u0011F!B=2\u0005\u0004\u0011F!\u0002?2\u0005\u0004\u0011F!B@2\u0005\u0004\u0011FABA\u0003c\t\u0007!\u000b\u0002\u0004\u0002\fE\u0012\rAU\u0001\u0010G>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00132kU\u0011C1\u000fC<\ts\"Y\b\" \u0005\u0000\u0011\u0005E1\u0011CC\t\u000f#I\tb#\u0005\u000e\u0012=E\u0011\u0013CJ\t++\"\u0001\"\u001e+\t\u0005\u0005!Q\u0007\u0003\u0006#J\u0012\rA\u0015\u0003\u00067J\u0012\rA\u0015\u0003\u0006=J\u0012\rA\u0015\u0003\u0006CJ\u0012\rA\u0015\u0003\u0006IJ\u0012\rA\u0015\u0003\u0006OJ\u0012\rA\u0015\u0003\u0006UJ\u0012\rA\u0015\u0003\u0006[J\u0012\rA\u0015\u0003\u0006aJ\u0012\rA\u0015\u0003\u0006gJ\u0012\rA\u0015\u0003\u0006mJ\u0012\rA\u0015\u0003\u0006sJ\u0012\rA\u0015\u0003\u0006yJ\u0012\rA\u0015\u0003\u0006\u007fJ\u0012\rA\u0015\u0003\u0007\u0003\u000b\u0011$\u0019\u0001*\u0005\r\u0005-!G1\u0001S\u0003=\u0019w\u000e]=%I\u00164\u0017-\u001e7uIE2TC\tCN\t?#\t\u000bb)\u0005&\u0012\u001dF\u0011\u0016CV\t[#y\u000b\"-\u00054\u0012UFq\u0017C]\tw#i,\u0006\u0002\u0005\u001e*\"\u0011q\u0001B\u001b\t\u0015\t6G1\u0001S\t\u0015Y6G1\u0001S\t\u0015q6G1\u0001S\t\u0015\t7G1\u0001S\t\u0015!7G1\u0001S\t\u001597G1\u0001S\t\u0015Q7G1\u0001S\t\u0015i7G1\u0001S\t\u0015\u00018G1\u0001S\t\u0015\u00198G1\u0001S\t\u001518G1\u0001S\t\u0015I8G1\u0001S\t\u0015a8G1\u0001S\t\u0015y8G1\u0001S\t\u0019\t)a\rb\u0001%\u00121\u00111B\u001aC\u0002I\u000bQ\u0002\u001d:pIV\u001cG\u000f\u0015:fM&DXC\u0001Cb!\u0011!)\rb4\u000e\u0005\u0011\u001d'\u0002\u0002Ce\t\u0017\fA\u0001\\1oO*\u0011AQZ\u0001\u0005U\u00064\u0018-\u0003\u0003\u0002B\u0012\u001d\u0017a\u00049s_\u0012,8\r^%uKJ\fGo\u001c:\u0016\u0005\u0011U\u0007#\u0002Cl\t;4VB\u0001Cm\u0015\r!YNQ\u0001\u000bG>dG.Z2uS>t\u0017\u0002\u0002Cp\t3\u0014\u0001\"\u0013;fe\u0006$xN]\u0001\tG\u0006tW)];bYR!AQ\u001dCv!\rAEq]\u0005\u0004\tS\u0014%a\u0002\"p_2,\u0017M\u001c\u0005\t\t[4\u0014\u0011!a\u0001-\u0006\u0019\u0001\u0010J\u0019\u0002%A\u0014x\u000eZ;di\u0016cW-\\3oi:\u000bW.\u001a\u000b\u0005\t\u0007$\u0019\u0010C\u0005\u0005n^\n\t\u00111\u0001\u0005vB\u0019\u0001\nb>\n\u0007\u0011e(IA\u0002J]R\f\u0001\u0002[1tQ\u000e{G-\u001a\u000b\u0003\tk\fa!Z9vC2\u001cH\u0003\u0002Cs\u000b\u0007A\u0001\u0002\"<:\u0003\u0003\u0005\rAV\u0001\b)V\u0004H.Z\u00197!\tA5h\u0005\u0003<\u000f\u0016-\u0001\u0003BC\u0007\u000b'i!!b\u0004\u000b\t\u0015EA1Z\u0001\u0003S>LA!a\n\u0006\u0010Q\u0011Qq\u0001\u000b\u0003\t\u0007\fQ!\u00199qYf,\"%\"\b\u0006$\u0015\u001dR1FC\u0018\u000bg)9$b\u000f\u0006@\u0015\rSqIC&\u000b\u001f*\u0019&b\u0016\u0006\\\u0015}CCIC\u0010\u000bC*\u0019'\"\u001a\u0006h\u0015%T1NC7\u000b_*\t(b\u001d\u0006v\u0015]T\u0011PC>\u000b{*y\b\u0005\u0012I\u0001\u0015\u0005RQEC\u0015\u000b[)\t$\"\u000e\u0006:\u0015uR\u0011IC#\u000b\u0013*i%\"\u0015\u0006V\u0015eSQ\f\t\u0004\u001f\u0016\rB!B)?\u0005\u0004\u0011\u0006cA(\u0006(\u0011)1L\u0010b\u0001%B\u0019q*b\u000b\u0005\u000bys$\u0019\u0001*\u0011\u0007=+y\u0003B\u0003b}\t\u0007!\u000bE\u0002P\u000bg!Q\u0001\u001a C\u0002I\u00032aTC\u001c\t\u00159gH1\u0001S!\ryU1\b\u0003\u0006Uz\u0012\rA\u0015\t\u0004\u001f\u0016}B!B7?\u0005\u0004\u0011\u0006cA(\u0006D\u0011)\u0001O\u0010b\u0001%B\u0019q*b\u0012\u0005\u000bMt$\u0019\u0001*\u0011\u0007=+Y\u0005B\u0003w}\t\u0007!\u000bE\u0002P\u000b\u001f\"Q!\u001f C\u0002I\u00032aTC*\t\u0015ahH1\u0001S!\ryUq\u000b\u0003\u0006\u007fz\u0012\rA\u0015\t\u0004\u001f\u0016mCABA\u0003}\t\u0007!\u000bE\u0002P\u000b?\"a!a\u0003?\u0005\u0004\u0011\u0006bBA\u0016}\u0001\u0007Q\u0011\u0005\u0005\b\u0003cq\u0004\u0019AC\u0013\u0011\u001d\t9D\u0010a\u0001\u000bSAq!!\u0010?\u0001\u0004)i\u0003C\u0004\u0002Dy\u0002\r!\"\r\t\u000f\u0005%c\b1\u0001\u00066!9\u0011q\n A\u0002\u0015e\u0002bBA+}\u0001\u0007QQ\b\u0005\b\u00037r\u0004\u0019AC!\u0011\u001d\t\tG\u0010a\u0001\u000b\u000bBq!a\u001a?\u0001\u0004)I\u0005C\u0004\u0002ny\u0002\r!\"\u0014\t\u000f\u0005Md\b1\u0001\u0006R!9\u0011\u0011\u0010 A\u0002\u0015U\u0003bBA@}\u0001\u0007Q\u0011\f\u0005\b\u0003\u000bs\u0004\u0019AC/\u0003\u001d)h.\u00199qYf,\"%\"\"\u0006\u0012\u0016UU\u0011TCO\u000bC+)+\"+\u0006.\u0016EVQWC]\u000b{+\t-\"2\u0006J\u00165G\u0003BCD\u000b\u001f\u0004R\u0001SCE\u000b\u001bK1!b#C\u0005\u0019y\u0005\u000f^5p]B\u0011\u0003\nACH\u000b'+9*b'\u0006 \u0016\rVqUCV\u000b_+\u0019,b.\u0006<\u0016}V1YCd\u000b\u0017\u00042aTCI\t\u0015\tvH1\u0001S!\ryUQ\u0013\u0003\u00067~\u0012\rA\u0015\t\u0004\u001f\u0016eE!\u00020@\u0005\u0004\u0011\u0006cA(\u0006\u001e\u0012)\u0011m\u0010b\u0001%B\u0019q*\")\u0005\u000b\u0011|$\u0019\u0001*\u0011\u0007=+)\u000bB\u0003h\u007f\t\u0007!\u000bE\u0002P\u000bS#QA[ C\u0002I\u00032aTCW\t\u0015iwH1\u0001S!\ryU\u0011\u0017\u0003\u0006a~\u0012\rA\u0015\t\u0004\u001f\u0016UF!B:@\u0005\u0004\u0011\u0006cA(\u0006:\u0012)ao\u0010b\u0001%B\u0019q*\"0\u0005\u000be|$\u0019\u0001*\u0011\u0007=+\t\rB\u0003}\u007f\t\u0007!\u000bE\u0002P\u000b\u000b$Qa` C\u0002I\u00032aTCe\t\u0019\t)a\u0010b\u0001%B\u0019q*\"4\u0005\r\u0005-qH1\u0001S\u0011%)\tnPA\u0001\u0002\u0004)i)A\u0002yIA\nAb\u001e:ji\u0016\u0014V\r\u001d7bG\u0016$\"!b6\u0011\t\u0011\u0015W\u0011\\\u0005\u0005\u000b7$9M\u0001\u0004PE*,7\r\u001e")
public final class Tuple16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>
implements Product16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>,
Serializable {
    private final T1 _1;
    private final T2 _2;
    private final T3 _3;
    private final T4 _4;
    private final T5 _5;
    private final T6 _6;
    private final T7 _7;
    private final T8 _8;
    private final T9 _9;
    private final T10 _10;
    private final T11 _11;
    private final T12 _12;
    private final T13 _13;
    private final T14 _14;
    private final T15 _15;
    private final T16 _16;

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> Option<Tuple16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>> unapply(Tuple16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> x$0) {
        return Tuple16$.MODULE$.unapply(x$0);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> Tuple16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> apply(T1 _1, T2 _2, T3 _3, T4 _4, T5 _5, T6 _6, T7 _7, T8 _8, T9 _9, T10 _10, T11 _11, T12 _12, T13 _13, T14 _14, T15 _15, T16 _16) {
        return new Tuple16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>(_1, _2, _3, _4, _5, _6, _7, _8, _9, _10, _11, _12, _13, _14, _15, _16);
    }

    @Override
    public int productArity() {
        return Product16.productArity$(this);
    }

    @Override
    public Object productElement(int n) throws IndexOutOfBoundsException {
        return Product16.productElement$(this, n);
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    @Override
    public T1 _1() {
        return this._1;
    }

    @Override
    public T2 _2() {
        return this._2;
    }

    @Override
    public T3 _3() {
        return this._3;
    }

    @Override
    public T4 _4() {
        return this._4;
    }

    @Override
    public T5 _5() {
        return this._5;
    }

    @Override
    public T6 _6() {
        return this._6;
    }

    @Override
    public T7 _7() {
        return this._7;
    }

    @Override
    public T8 _8() {
        return this._8;
    }

    @Override
    public T9 _9() {
        return this._9;
    }

    @Override
    public T10 _10() {
        return this._10;
    }

    @Override
    public T11 _11() {
        return this._11;
    }

    @Override
    public T12 _12() {
        return this._12;
    }

    @Override
    public T13 _13() {
        return this._13;
    }

    @Override
    public T14 _14() {
        return this._14;
    }

    @Override
    public T15 _15() {
        return this._15;
    }

    @Override
    public T16 _16() {
        return this._16;
    }

    public String toString() {
        return new StringBuilder(17).append("(").append(this._1()).append(",").append(this._2()).append(",").append(this._3()).append(",").append(this._4()).append(",").append(this._5()).append(",").append(this._6()).append(",").append(this._7()).append(",").append(this._8()).append(",").append(this._9()).append(",").append(this._10()).append(",").append(this._11()).append(",").append(this._12()).append(",").append(this._13()).append(",").append(this._14()).append(",").append(this._15()).append(",").append(this._16()).append(")").toString();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> Tuple16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> copy(T1 _1, T2 _2, T3 _3, T4 _4, T5 _5, T6 _6, T7 _7, T8 _8, T9 _9, T10 _10, T11 _11, T12 _12, T13 _13, T14 _14, T15 _15, T16 _16) {
        return new Tuple16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>(_1, _2, _3, _4, _5, _6, _7, _8, _9, _10, _11, _12, _13, _14, _15, _16);
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> T1 copy$default$1() {
        return this._1();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> T10 copy$default$10() {
        return this._10();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> T11 copy$default$11() {
        return this._11();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> T12 copy$default$12() {
        return this._12();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> T13 copy$default$13() {
        return this._13();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> T14 copy$default$14() {
        return this._14();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> T15 copy$default$15() {
        return this._15();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> T16 copy$default$16() {
        return this._16();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> T2 copy$default$2() {
        return this._2();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> T3 copy$default$3() {
        return this._3();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> T4 copy$default$4() {
        return this._4();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> T5 copy$default$5() {
        return this._5();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> T6 copy$default$6() {
        return this._6();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> T7 copy$default$7() {
        return this._7();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> T8 copy$default$8() {
        return this._8();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16> T9 copy$default$9() {
        return this._9();
    }

    @Override
    public String productPrefix() {
        return "Tuple16";
    }

    @Override
    public Iterator<Object> productIterator() {
        return new AbstractIterator<T>(this){
            private int c;
            private final int cmax;
            private final Product x$2;

            public boolean hasNext() {
                return this.c < this.cmax;
            }

            public T next() {
                Object result = this.x$2.productElement(this.c);
                ++this.c;
                return (T)result;
            }
            {
                this.x$2 = x$2;
                this.c = 0;
                this.cmax = x$2.productArity();
            }
        };
    }

    @Override
    public boolean canEqual(Object x$1) {
        return x$1 instanceof Tuple16;
    }

    @Override
    public String productElementName(int x$1) {
        switch (x$1) {
            case 0: {
                return "_1";
            }
            case 1: {
                return "_2";
            }
            case 2: {
                return "_3";
            }
            case 3: {
                return "_4";
            }
            case 4: {
                return "_5";
            }
            case 5: {
                return "_6";
            }
            case 6: {
                return "_7";
            }
            case 7: {
                return "_8";
            }
            case 8: {
                return "_9";
            }
            case 9: {
                return "_10";
            }
            case 10: {
                return "_11";
            }
            case 11: {
                return "_12";
            }
            case 12: {
                return "_13";
            }
            case 13: {
                return "_14";
            }
            case 14: {
                return "_15";
            }
            case 15: {
                return "_16";
            }
        }
        return (String)Statics.ioobe(x$1);
    }

    public int hashCode() {
        return MurmurHash3$.MODULE$.productHash(this);
    }

    @Override
    public boolean equals(Object x$1) {
        block3: {
            block2: {
                if (this == x$1) break block2;
                if (!(x$1 instanceof Tuple16)) break block3;
                Tuple16 tuple16 = (Tuple16)x$1;
                if (!BoxesRunTime.equals(this._1(), tuple16._1()) || !BoxesRunTime.equals(this._2(), tuple16._2()) || !BoxesRunTime.equals(this._3(), tuple16._3()) || !BoxesRunTime.equals(this._4(), tuple16._4()) || !BoxesRunTime.equals(this._5(), tuple16._5()) || !BoxesRunTime.equals(this._6(), tuple16._6()) || !BoxesRunTime.equals(this._7(), tuple16._7()) || !BoxesRunTime.equals(this._8(), tuple16._8()) || !BoxesRunTime.equals(this._9(), tuple16._9()) || !BoxesRunTime.equals(this._10(), tuple16._10()) || !BoxesRunTime.equals(this._11(), tuple16._11()) || !BoxesRunTime.equals(this._12(), tuple16._12()) || !BoxesRunTime.equals(this._13(), tuple16._13()) || !BoxesRunTime.equals(this._14(), tuple16._14()) || !BoxesRunTime.equals(this._15(), tuple16._15()) || !BoxesRunTime.equals(this._16(), tuple16._16())) break block3;
            }
            return true;
        }
        return false;
    }

    public Tuple16(T1 _1, T2 _2, T3 _3, T4 _4, T5 _5, T6 _6, T7 _7, T8 _8, T9 _9, T10 _10, T11 _11, T12 _12, T13 _13, T14 _14, T15 _15, T16 _16) {
        this._1 = _1;
        this._2 = _2;
        this._3 = _3;
        this._4 = _4;
        this._5 = _5;
        this._6 = _6;
        this._7 = _7;
        this._8 = _8;
        this._9 = _9;
        this._10 = _10;
        this._11 = _11;
        this._12 = _12;
        this._13 = _13;
        this._14 = _14;
        this._15 = _15;
        this._16 = _16;
    }
}

