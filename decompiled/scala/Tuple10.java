/*
 * Decompiled with CFR 0.152.
 */
package scala;

import java.io.Serializable;
import scala.Option;
import scala.Product;
import scala.Product10;
import scala.Tuple10$;
import scala.collection.AbstractIterator;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.Statics;
import scala.util.hashing.MurmurHash3$;

@ScalaSignature(bytes="\u0006\u0005\rue\u0001B\u00181\u0005NB\u0001\"\u001d\u0001\u0003\u0016\u0004%\tA\u001d\u0005\tg\u0002\u0011\t\u0012)A\u0005y!AA\u000f\u0001BK\u0002\u0013\u0005Q\u000f\u0003\u0005w\u0001\tE\t\u0015!\u0003H\u0011!9\bA!f\u0001\n\u0003A\b\u0002C=\u0001\u0005#\u0005\u000b\u0011\u0002&\t\u0011i\u0004!Q3A\u0005\u0002mD\u0001\u0002 \u0001\u0003\u0012\u0003\u0006I!\u0014\u0005\t{\u0002\u0011)\u001a!C\u0001}\"Aq\u0010\u0001B\tB\u0003%\u0001\u000b\u0003\u0006\u0002\u0002\u0001\u0011)\u001a!C\u0001\u0003\u0007A\u0011\"!\u0002\u0001\u0005#\u0005\u000b\u0011B*\t\u0015\u0005\u001d\u0001A!f\u0001\n\u0003\tI\u0001C\u0005\u0002\f\u0001\u0011\t\u0012)A\u0005-\"Q\u0011Q\u0002\u0001\u0003\u0016\u0004%\t!a\u0004\t\u0013\u0005E\u0001A!E!\u0002\u0013I\u0006BCA\n\u0001\tU\r\u0011\"\u0001\u0002\u0016!I\u0011q\u0003\u0001\u0003\u0012\u0003\u0006I\u0001\u0018\u0005\u000b\u00033\u0001!Q3A\u0005\u0002\u0005m\u0001\"CA\u000f\u0001\tE\t\u0015!\u0003`\u0011\u001d\ty\u0002\u0001C\u0001\u0003CAq!!\u000f\u0001\t\u0003\nY\u0004C\u0005\u0002N\u0001\t\t\u0011\"\u0001\u0002P!I\u0011\u0011\u0013\u0001\u0012\u0002\u0013\u0005\u00111\u0013\u0005\n\u0003\u007f\u0003\u0011\u0013!C\u0001\u0003\u0003D\u0011\"a7\u0001#\u0003%\t!!8\t\u0013\u0005]\b!%A\u0005\u0002\u0005e\b\"\u0003B\n\u0001E\u0005I\u0011\u0001B\u000b\u0011%\u0011y\u0003AI\u0001\n\u0003\u0011\t\u0004C\u0005\u0003L\u0001\t\n\u0011\"\u0001\u0003N!I!q\r\u0001\u0012\u0002\u0013\u0005!\u0011\u000e\u0005\n\u0005\u0007\u0003\u0011\u0013!C\u0001\u0005\u000bC\u0011Ba(\u0001#\u0003%\tA!)\t\u0013\tm\u0006!!A\u0005B\tu\u0006\"\u0003Bg\u0001\u0005\u0005I\u0011\tBh\u0011%\u0011i\u000eAA\u0001\n\u0003\u0011y\u000eC\u0005\u0003l\u0002\t\t\u0011\"\u0011\u0003n\"I!q\u001f\u0001\u0002\u0002\u0013\u0005#\u0011 \u0005\n\u0005w\u0004\u0011\u0011!C!\u0005{<\u0011b!\u00011\u0003\u0003E\taa\u0001\u0007\u0011=\u0002\u0014\u0011!E\u0001\u0007\u000bAq!a\b*\t\u0003\u0019\t\u0002C\u0005\u0002:%\n\t\u0011\"\u0012\u0004\u0014!I1QC\u0015\u0002\u0002\u0013\u00055q\u0003\u0005\n\u00073J\u0013\u0011!CA\u00077B\u0011ba%*\u0003\u0003%Ia!&\u0003\u000fQ+\b\u000f\\32a)\t\u0011'A\u0003tG\u0006d\u0017m\u0001\u0001\u0016\u0017Qr\u0004j\u0013(R)^SV\fY\n\u0006\u0001UJ$-\u001a\t\u0003m]j\u0011\u0001M\u0005\u0003qA\u0012a!\u00118z%\u00164\u0007\u0003\u0004\u001c;y\u001dSU\nU*W3r{\u0016BA\u001e1\u0005%\u0001&o\u001c3vGR\f\u0004\u0007\u0005\u0002>}1\u0001AAB \u0001\t\u000b\u0007\u0001I\u0001\u0002UcE\u0011\u0011\t\u0012\t\u0003m\tK!a\u0011\u0019\u0003\u000f9{G\u000f[5oOB\u0011a'R\u0005\u0003\rB\u00121!\u00118z!\ti\u0004\n\u0002\u0004J\u0001\u0011\u0015\r\u0001\u0011\u0002\u0003)J\u0002\"!P&\u0005\r1\u0003AQ1\u0001A\u0005\t!6\u0007\u0005\u0002>\u001d\u00121q\n\u0001CC\u0002\u0001\u0013!\u0001\u0016\u001b\u0011\u0005u\nFA\u0002*\u0001\t\u000b\u0007\u0001I\u0001\u0002UkA\u0011Q\b\u0016\u0003\u0007+\u0002!)\u0019\u0001!\u0003\u0005Q3\u0004CA\u001fX\t\u0019A\u0006\u0001\"b\u0001\u0001\n\u0011Ak\u000e\t\u0003{i#aa\u0017\u0001\u0005\u0006\u0004\u0001%A\u0001+9!\tiT\f\u0002\u0004_\u0001\u0011\u0015\r\u0001\u0011\u0002\u0003)f\u0002\"!\u00101\u0005\r\u0005\u0004AQ1\u0001A\u0005\r!\u0016\u0007\r\t\u0003m\rL!\u0001\u001a\u0019\u0003\u000fA\u0013x\u000eZ;diB\u0011aM\u001c\b\u0003O2t!\u0001[6\u000e\u0003%T!A\u001b\u001a\u0002\rq\u0012xn\u001c;?\u0013\u0005\t\u0014BA71\u0003\u001d\u0001\u0018mY6bO\u0016L!a\u001c9\u0003\u0019M+'/[1mSj\f'\r\\3\u000b\u00055\u0004\u0014AA02+\u0005a\u0014aA02A\u0005\u0011qLM\u000b\u0002\u000f\u0006\u0019qL\r\u0011\u0002\u0005}\u001bT#\u0001&\u0002\u0007}\u001b\u0004%\u0001\u0002`iU\tQ*A\u0002`i\u0001\n!aX\u001b\u0016\u0003A\u000b1aX\u001b!\u0003\tyf'F\u0001T\u0003\ryf\u0007I\u0001\u0003?^*\u0012AV\u0001\u0004?^\u0002\u0013AA09+\u0005I\u0016aA09A\u0005\u0011q,O\u000b\u00029\u0006\u0019q,\u000f\u0011\u0002\u0007}\u000b\u0004'F\u0001`\u0003\u0011y\u0016\u0007\r\u0011\u0002\rqJg.\u001b;?)Y\t\u0019#!\n\u0002(\u0005%\u00121FA\u0017\u0003_\t\t$a\r\u00026\u0005]\u0002\u0003\u0004\u001c\u0001y\u001dSU\nU*W3r{\u0006\"B9\u0016\u0001\u0004a\u0004\"\u0002;\u0016\u0001\u00049\u0005\"B<\u0016\u0001\u0004Q\u0005\"\u0002>\u0016\u0001\u0004i\u0005\"B?\u0016\u0001\u0004\u0001\u0006BBA\u0001+\u0001\u00071\u000b\u0003\u0004\u0002\bU\u0001\rA\u0016\u0005\u0007\u0003\u001b)\u0002\u0019A-\t\r\u0005MQ\u00031\u0001]\u0011\u0019\tI\"\u0006a\u0001?\u0006AAo\\*ue&tw\r\u0006\u0002\u0002>A!\u0011qHA$\u001d\u0011\t\t%a\u0011\u0011\u0005!\u0004\u0014bAA#a\u00051\u0001K]3eK\u001aLA!!\u0013\u0002L\t11\u000b\u001e:j]\u001eT1!!\u00121\u0003\u0011\u0019w\u000e]=\u0016-\u0005E\u0013qKA.\u0003?\n\u0019'a\u001a\u0002l\u0005=\u00141OA<\u0003w\"b#a\u0015\u0002~\u0005}\u0014\u0011QAB\u0003\u000b\u000b9)!#\u0002\f\u00065\u0015q\u0012\t\u0017m\u0001\t)&!\u0017\u0002^\u0005\u0005\u0014QMA5\u0003[\n\t(!\u001e\u0002zA\u0019Q(a\u0016\u0005\u000b}:\"\u0019\u0001!\u0011\u0007u\nY\u0006B\u0003J/\t\u0007\u0001\tE\u0002>\u0003?\"Q\u0001T\fC\u0002\u0001\u00032!PA2\t\u0015yuC1\u0001A!\ri\u0014q\r\u0003\u0006%^\u0011\r\u0001\u0011\t\u0004{\u0005-D!B+\u0018\u0005\u0004\u0001\u0005cA\u001f\u0002p\u0011)\u0001l\u0006b\u0001\u0001B\u0019Q(a\u001d\u0005\u000bm;\"\u0019\u0001!\u0011\u0007u\n9\bB\u0003_/\t\u0007\u0001\tE\u0002>\u0003w\"Q!Y\fC\u0002\u0001C\u0001\"]\f\u0011\u0002\u0003\u0007\u0011Q\u000b\u0005\ti^\u0001\n\u00111\u0001\u0002Z!Aqo\u0006I\u0001\u0002\u0004\ti\u0006\u0003\u0005{/A\u0005\t\u0019AA1\u0011!ix\u0003%AA\u0002\u0005\u0015\u0004\"CA\u0001/A\u0005\t\u0019AA5\u0011%\t9a\u0006I\u0001\u0002\u0004\ti\u0007C\u0005\u0002\u000e]\u0001\n\u00111\u0001\u0002r!I\u00111C\f\u0011\u0002\u0003\u0007\u0011Q\u000f\u0005\n\u000339\u0002\u0013!a\u0001\u0003s\nabY8qs\u0012\"WMZ1vYR$\u0013'\u0006\f\u0002\u0016\u0006-\u0016QVAX\u0003c\u000b\u0019,!.\u00028\u0006e\u00161XA_+\t\t9JK\u0002=\u00033[#!a'\u0011\t\u0005u\u0015qU\u0007\u0003\u0003?SA!!)\u0002$\u0006IQO\\2iK\u000e\\W\r\u001a\u0006\u0004\u0003K\u0003\u0014AC1o]>$\u0018\r^5p]&!\u0011\u0011VAP\u0005E)hn\u00195fG.,GMV1sS\u0006t7-\u001a\u0003\u0006\u007fa\u0011\r\u0001\u0011\u0003\u0006\u0013b\u0011\r\u0001\u0011\u0003\u0006\u0019b\u0011\r\u0001\u0011\u0003\u0006\u001fb\u0011\r\u0001\u0011\u0003\u0006%b\u0011\r\u0001\u0011\u0003\u0006+b\u0011\r\u0001\u0011\u0003\u00061b\u0011\r\u0001\u0011\u0003\u00067b\u0011\r\u0001\u0011\u0003\u0006=b\u0011\r\u0001\u0011\u0003\u0006Cb\u0011\r\u0001Q\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00133+Y\t\u0019-a2\u0002J\u0006-\u0017QZAh\u0003#\f\u0019.!6\u0002X\u0006eWCAAcU\r9\u0015\u0011\u0014\u0003\u0006\u007fe\u0011\r\u0001\u0011\u0003\u0006\u0013f\u0011\r\u0001\u0011\u0003\u0006\u0019f\u0011\r\u0001\u0011\u0003\u0006\u001ff\u0011\r\u0001\u0011\u0003\u0006%f\u0011\r\u0001\u0011\u0003\u0006+f\u0011\r\u0001\u0011\u0003\u00061f\u0011\r\u0001\u0011\u0003\u00067f\u0011\r\u0001\u0011\u0003\u0006=f\u0011\r\u0001\u0011\u0003\u0006Cf\u0011\r\u0001Q\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00134+Y\ty.a9\u0002f\u0006\u001d\u0018\u0011^Av\u0003[\fy/!=\u0002t\u0006UXCAAqU\rQ\u0015\u0011\u0014\u0003\u0006\u007fi\u0011\r\u0001\u0011\u0003\u0006\u0013j\u0011\r\u0001\u0011\u0003\u0006\u0019j\u0011\r\u0001\u0011\u0003\u0006\u001fj\u0011\r\u0001\u0011\u0003\u0006%j\u0011\r\u0001\u0011\u0003\u0006+j\u0011\r\u0001\u0011\u0003\u00061j\u0011\r\u0001\u0011\u0003\u00067j\u0011\r\u0001\u0011\u0003\u0006=j\u0011\r\u0001\u0011\u0003\u0006Cj\u0011\r\u0001Q\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00135+Y\tY0a@\u0003\u0002\t\r!Q\u0001B\u0004\u0005\u0013\u0011YA!\u0004\u0003\u0010\tEQCAA\u007fU\ri\u0015\u0011\u0014\u0003\u0006\u007fm\u0011\r\u0001\u0011\u0003\u0006\u0013n\u0011\r\u0001\u0011\u0003\u0006\u0019n\u0011\r\u0001\u0011\u0003\u0006\u001fn\u0011\r\u0001\u0011\u0003\u0006%n\u0011\r\u0001\u0011\u0003\u0006+n\u0011\r\u0001\u0011\u0003\u00061n\u0011\r\u0001\u0011\u0003\u00067n\u0011\r\u0001\u0011\u0003\u0006=n\u0011\r\u0001\u0011\u0003\u0006Cn\u0011\r\u0001Q\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00136+Y\u00119Ba\u0007\u0003\u001e\t}!\u0011\u0005B\u0012\u0005K\u00119C!\u000b\u0003,\t5RC\u0001B\rU\r\u0001\u0016\u0011\u0014\u0003\u0006\u007fq\u0011\r\u0001\u0011\u0003\u0006\u0013r\u0011\r\u0001\u0011\u0003\u0006\u0019r\u0011\r\u0001\u0011\u0003\u0006\u001fr\u0011\r\u0001\u0011\u0003\u0006%r\u0011\r\u0001\u0011\u0003\u0006+r\u0011\r\u0001\u0011\u0003\u00061r\u0011\r\u0001\u0011\u0003\u00067r\u0011\r\u0001\u0011\u0003\u0006=r\u0011\r\u0001\u0011\u0003\u0006Cr\u0011\r\u0001Q\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00137+Y\u0011\u0019Da\u000e\u0003:\tm\"Q\bB \u0005\u0003\u0012\u0019E!\u0012\u0003H\t%SC\u0001B\u001bU\r\u0019\u0016\u0011\u0014\u0003\u0006\u007fu\u0011\r\u0001\u0011\u0003\u0006\u0013v\u0011\r\u0001\u0011\u0003\u0006\u0019v\u0011\r\u0001\u0011\u0003\u0006\u001fv\u0011\r\u0001\u0011\u0003\u0006%v\u0011\r\u0001\u0011\u0003\u0006+v\u0011\r\u0001\u0011\u0003\u00061v\u0011\r\u0001\u0011\u0003\u00067v\u0011\r\u0001\u0011\u0003\u0006=v\u0011\r\u0001\u0011\u0003\u0006Cv\u0011\r\u0001Q\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00138+Y\u0011yEa\u0015\u0003V\t]#\u0011\fB.\u0005;\u0012yF!\u0019\u0003d\t\u0015TC\u0001B)U\r1\u0016\u0011\u0014\u0003\u0006\u007fy\u0011\r\u0001\u0011\u0003\u0006\u0013z\u0011\r\u0001\u0011\u0003\u0006\u0019z\u0011\r\u0001\u0011\u0003\u0006\u001fz\u0011\r\u0001\u0011\u0003\u0006%z\u0011\r\u0001\u0011\u0003\u0006+z\u0011\r\u0001\u0011\u0003\u00061z\u0011\r\u0001\u0011\u0003\u00067z\u0011\r\u0001\u0011\u0003\u0006=z\u0011\r\u0001\u0011\u0003\u0006Cz\u0011\r\u0001Q\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00139+Y\u0011YGa\u001c\u0003r\tM$Q\u000fB<\u0005s\u0012YH! \u0003\u0000\t\u0005UC\u0001B7U\rI\u0016\u0011\u0014\u0003\u0006\u007f}\u0011\r\u0001\u0011\u0003\u0006\u0013~\u0011\r\u0001\u0011\u0003\u0006\u0019~\u0011\r\u0001\u0011\u0003\u0006\u001f~\u0011\r\u0001\u0011\u0003\u0006%~\u0011\r\u0001\u0011\u0003\u0006+~\u0011\r\u0001\u0011\u0003\u00061~\u0011\r\u0001\u0011\u0003\u00067~\u0011\r\u0001\u0011\u0003\u0006=~\u0011\r\u0001\u0011\u0003\u0006C~\u0011\r\u0001Q\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u0013:+Y\u00119Ia#\u0003\u000e\n=%\u0011\u0013BJ\u0005+\u00139J!'\u0003\u001c\nuUC\u0001BEU\ra\u0016\u0011\u0014\u0003\u0006\u007f\u0001\u0012\r\u0001\u0011\u0003\u0006\u0013\u0002\u0012\r\u0001\u0011\u0003\u0006\u0019\u0002\u0012\r\u0001\u0011\u0003\u0006\u001f\u0002\u0012\r\u0001\u0011\u0003\u0006%\u0002\u0012\r\u0001\u0011\u0003\u0006+\u0002\u0012\r\u0001\u0011\u0003\u00061\u0002\u0012\r\u0001\u0011\u0003\u00067\u0002\u0012\r\u0001\u0011\u0003\u0006=\u0002\u0012\r\u0001\u0011\u0003\u0006C\u0002\u0012\r\u0001Q\u0001\u0010G>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00132aU1\"1\u0015BT\u0005S\u0013YK!,\u00030\nE&1\u0017B[\u0005o\u0013I,\u0006\u0002\u0003&*\u001aq,!'\u0005\u000b}\n#\u0019\u0001!\u0005\u000b%\u000b#\u0019\u0001!\u0005\u000b1\u000b#\u0019\u0001!\u0005\u000b=\u000b#\u0019\u0001!\u0005\u000bI\u000b#\u0019\u0001!\u0005\u000bU\u000b#\u0019\u0001!\u0005\u000ba\u000b#\u0019\u0001!\u0005\u000bm\u000b#\u0019\u0001!\u0005\u000by\u000b#\u0019\u0001!\u0005\u000b\u0005\f#\u0019\u0001!\u0002\u001bA\u0014x\u000eZ;diB\u0013XMZ5y+\t\u0011y\f\u0005\u0003\u0003B\n-WB\u0001Bb\u0015\u0011\u0011)Ma2\u0002\t1\fgn\u001a\u0006\u0003\u0005\u0013\fAA[1wC&!\u0011\u0011\nBb\u0003=\u0001(o\u001c3vGRLE/\u001a:bi>\u0014XC\u0001Bi!\u0015\u0011\u0019N!7E\u001b\t\u0011)NC\u0002\u0003XB\n!bY8mY\u0016\u001cG/[8o\u0013\u0011\u0011YN!6\u0003\u0011%#XM]1u_J\f\u0001bY1o\u000bF,\u0018\r\u001c\u000b\u0005\u0005C\u00149\u000fE\u00027\u0005GL1A!:1\u0005\u001d\u0011un\u001c7fC:D\u0001B!;%\u0003\u0003\u0005\r\u0001R\u0001\u0004q\u0012\n\u0014A\u00059s_\u0012,8\r^#mK6,g\u000e\u001e(b[\u0016$BAa0\u0003p\"I!\u0011^\u0013\u0002\u0002\u0003\u0007!\u0011\u001f\t\u0004m\tM\u0018b\u0001B{a\t\u0019\u0011J\u001c;\u0002\u0011!\f7\u000f[\"pI\u0016$\"A!=\u0002\r\u0015\fX/\u00197t)\u0011\u0011\tOa@\t\u0011\t%x%!AA\u0002\u0011\u000bq\u0001V;qY\u0016\f\u0004\u0007\u0005\u00027SM!\u0011&NB\u0004!\u0011\u0019Iaa\u0004\u000e\u0005\r-!\u0002BB\u0007\u0005\u000f\f!![8\n\u0007=\u001cY\u0001\u0006\u0002\u0004\u0004Q\u0011!qX\u0001\u0006CB\u0004H._\u000b\u0017\u00073\u0019yba\t\u0004(\r-2qFB\u001a\u0007o\u0019Yda\u0010\u0004DQ121DB#\u0007\u000f\u001aIea\u0013\u0004N\r=3\u0011KB*\u0007+\u001a9\u0006\u0005\f7\u0001\ru1\u0011EB\u0013\u0007S\u0019ic!\r\u00046\re2QHB!!\ri4q\u0004\u0003\u0006\u007f1\u0012\r\u0001\u0011\t\u0004{\r\rB!B%-\u0005\u0004\u0001\u0005cA\u001f\u0004(\u0011)A\n\fb\u0001\u0001B\u0019Qha\u000b\u0005\u000b=c#\u0019\u0001!\u0011\u0007u\u001ay\u0003B\u0003SY\t\u0007\u0001\tE\u0002>\u0007g!Q!\u0016\u0017C\u0002\u0001\u00032!PB\u001c\t\u0015AFF1\u0001A!\ri41\b\u0003\u000672\u0012\r\u0001\u0011\t\u0004{\r}B!\u00020-\u0005\u0004\u0001\u0005cA\u001f\u0004D\u0011)\u0011\r\fb\u0001\u0001\"1\u0011\u000f\fa\u0001\u0007;Aa\u0001\u001e\u0017A\u0002\r\u0005\u0002BB<-\u0001\u0004\u0019)\u0003\u0003\u0004{Y\u0001\u00071\u0011\u0006\u0005\u0007{2\u0002\ra!\f\t\u000f\u0005\u0005A\u00061\u0001\u00042!9\u0011q\u0001\u0017A\u0002\rU\u0002bBA\u0007Y\u0001\u00071\u0011\b\u0005\b\u0003'a\u0003\u0019AB\u001f\u0011\u001d\tI\u0002\fa\u0001\u0007\u0003\nq!\u001e8baBd\u00170\u0006\f\u0004^\r%4QNB9\u0007k\u001aIh! \u0004\u0002\u000e\u00155\u0011RBG)\u0011\u0019yfa$\u0011\u000bY\u001a\tg!\u001a\n\u0007\r\r\u0004G\u0001\u0004PaRLwN\u001c\t\u0017m\u0001\u00199ga\u001b\u0004p\rM4qOB>\u0007\u007f\u001a\u0019ia\"\u0004\fB\u0019Qh!\u001b\u0005\u000b}j#\u0019\u0001!\u0011\u0007u\u001ai\u0007B\u0003J[\t\u0007\u0001\tE\u0002>\u0007c\"Q\u0001T\u0017C\u0002\u0001\u00032!PB;\t\u0015yUF1\u0001A!\ri4\u0011\u0010\u0003\u0006%6\u0012\r\u0001\u0011\t\u0004{\ruD!B+.\u0005\u0004\u0001\u0005cA\u001f\u0004\u0002\u0012)\u0001,\fb\u0001\u0001B\u0019Qh!\"\u0005\u000bmk#\u0019\u0001!\u0011\u0007u\u001aI\tB\u0003_[\t\u0007\u0001\tE\u0002>\u0007\u001b#Q!Y\u0017C\u0002\u0001C\u0011b!%.\u0003\u0003\u0005\ra!\u001a\u0002\u0007a$\u0003'\u0001\u0007xe&$XMU3qY\u0006\u001cW\r\u0006\u0002\u0004\u0018B!!\u0011YBM\u0013\u0011\u0019YJa1\u0003\r=\u0013'.Z2u\u0001")
public final class Tuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>
implements Product10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>,
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

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> Option<Tuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> unapply(Tuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> x$0) {
        return Tuple10$.MODULE$.unapply(x$0);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> Tuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> apply(T1 _1, T2 _2, T3 _3, T4 _4, T5 _5, T6 _6, T7 _7, T8 _8, T9 _9, T10 _10) {
        return new Tuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>(_1, _2, _3, _4, _5, _6, _7, _8, _9, _10);
    }

    @Override
    public int productArity() {
        return Product10.productArity$(this);
    }

    @Override
    public Object productElement(int n) throws IndexOutOfBoundsException {
        return Product10.productElement$(this, n);
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

    public String toString() {
        return new StringBuilder(11).append("(").append(this._1()).append(",").append(this._2()).append(",").append(this._3()).append(",").append(this._4()).append(",").append(this._5()).append(",").append(this._6()).append(",").append(this._7()).append(",").append(this._8()).append(",").append(this._9()).append(",").append(this._10()).append(")").toString();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> Tuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> copy(T1 _1, T2 _2, T3 _3, T4 _4, T5 _5, T6 _6, T7 _7, T8 _8, T9 _9, T10 _10) {
        return new Tuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>(_1, _2, _3, _4, _5, _6, _7, _8, _9, _10);
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> T1 copy$default$1() {
        return this._1();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> T10 copy$default$10() {
        return this._10();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> T2 copy$default$2() {
        return this._2();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> T3 copy$default$3() {
        return this._3();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> T4 copy$default$4() {
        return this._4();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> T5 copy$default$5() {
        return this._5();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> T6 copy$default$6() {
        return this._6();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> T7 copy$default$7() {
        return this._7();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> T8 copy$default$8() {
        return this._8();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> T9 copy$default$9() {
        return this._9();
    }

    @Override
    public String productPrefix() {
        return "Tuple10";
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
        return x$1 instanceof Tuple10;
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
                if (!(x$1 instanceof Tuple10)) break block3;
                Tuple10 tuple10 = (Tuple10)x$1;
                if (!BoxesRunTime.equals(this._1(), tuple10._1()) || !BoxesRunTime.equals(this._2(), tuple10._2()) || !BoxesRunTime.equals(this._3(), tuple10._3()) || !BoxesRunTime.equals(this._4(), tuple10._4()) || !BoxesRunTime.equals(this._5(), tuple10._5()) || !BoxesRunTime.equals(this._6(), tuple10._6()) || !BoxesRunTime.equals(this._7(), tuple10._7()) || !BoxesRunTime.equals(this._8(), tuple10._8()) || !BoxesRunTime.equals(this._9(), tuple10._9()) || !BoxesRunTime.equals(this._10(), tuple10._10())) break block3;
            }
            return true;
        }
        return false;
    }

    public Tuple10(T1 _1, T2 _2, T3 _3, T4 _4, T5 _5, T6 _6, T7 _7, T8 _8, T9 _9, T10 _10) {
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
    }
}

