/*
 * Decompiled with CFR 0.152.
 */
package scala;

import java.io.Serializable;
import scala.Option;
import scala.Product;
import scala.Product17;
import scala.Tuple17$;
import scala.collection.AbstractIterator;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.Statics;
import scala.util.hashing.MurmurHash3$;

@ScalaSignature(bytes="\u0006\u0005\u0019-c\u0001\u0002#F\u0005\"C!\"a\u000e\u0001\u0005+\u0007I\u0011AA\u001d\u0011%\tY\u0004\u0001B\tB\u0003%\u0011\u000b\u0003\u0006\u0002>\u0001\u0011)\u001a!C\u0001\u0003\u007fA\u0011\"!\u0011\u0001\u0005#\u0005\u000b\u0011\u0002/\t\u0015\u0005\r\u0003A!f\u0001\n\u0003\t)\u0005C\u0005\u0002H\u0001\u0011\t\u0012)A\u0005?\"Q\u0011\u0011\n\u0001\u0003\u0016\u0004%\t!a\u0013\t\u0013\u00055\u0003A!E!\u0002\u0013\u0011\u0007BCA(\u0001\tU\r\u0011\"\u0001\u0002R!I\u00111\u000b\u0001\u0003\u0012\u0003\u0006I!\u001a\u0005\u000b\u0003+\u0002!Q3A\u0005\u0002\u0005]\u0003\"CA-\u0001\tE\t\u0015!\u0003i\u0011)\tY\u0006\u0001BK\u0002\u0013\u0005\u0011Q\f\u0005\n\u0003?\u0002!\u0011#Q\u0001\n-D!\"!\u0019\u0001\u0005+\u0007I\u0011AA2\u0011%\t)\u0007\u0001B\tB\u0003%a\u000e\u0003\u0006\u0002h\u0001\u0011)\u001a!C\u0001\u0003SB\u0011\"a\u001b\u0001\u0005#\u0005\u000b\u0011B9\t\u0015\u00055\u0004A!f\u0001\n\u0003\ty\u0007C\u0005\u0002r\u0001\u0011\t\u0012)A\u0005i\"Q\u00111\u000f\u0001\u0003\u0016\u0004%\t!!\u001e\t\u0013\u0005]\u0004A!E!\u0002\u00139\bBCA=\u0001\tU\r\u0011\"\u0001\u0002|!I\u0011Q\u0010\u0001\u0003\u0012\u0003\u0006IA\u001f\u0005\u000b\u0003\u007f\u0002!Q3A\u0005\u0002\u0005\u0005\u0005\"CAB\u0001\tE\t\u0015!\u0003~\u0011)\t)\t\u0001BK\u0002\u0013\u0005\u0011q\u0011\u0005\u000b\u0003\u0013\u0003!\u0011#Q\u0001\n\u0005\u0005\u0001BCAF\u0001\tU\r\u0011\"\u0001\u0002\u000e\"Q\u0011q\u0012\u0001\u0003\u0012\u0003\u0006I!a\u0002\t\u0015\u0005E\u0005A!f\u0001\n\u0003\t\u0019\n\u0003\u0006\u0002\u0016\u0002\u0011\t\u0012)A\u0005\u0003\u001bA!\"a&\u0001\u0005+\u0007I\u0011AAM\u0011)\tY\n\u0001B\tB\u0003%\u00111\u0003\u0005\b\u0003;\u0003A\u0011AAP\u0011\u001d\t)\r\u0001C!\u0003\u000fD\u0011\"!7\u0001\u0003\u0003%\t!a7\t\u0013\t\u001d\u0003!%A\u0005\u0002\t%\u0003\"\u0003BB\u0001E\u0005I\u0011\u0001BC\u0011%\u0011i\u000bAI\u0001\n\u0003\u0011y\u000bC\u0005\u0003X\u0002\t\n\u0011\"\u0001\u0003Z\"I1\u0011\u0001\u0001\u0012\u0002\u0013\u000511\u0001\u0005\n\u0007W\u0001\u0011\u0013!C\u0001\u0007[A\u0011b!\u0016\u0001#\u0003%\taa\u0016\t\u0013\r}\u0004!%A\u0005\u0002\r\u0005\u0005\"CBU\u0001E\u0005I\u0011ABV\u0011%\u0019\u0019\u000eAI\u0001\n\u0003\u0019)\u000eC\u0005\u0004~\u0002\t\n\u0011\"\u0001\u0004\u0000\"IAq\u0005\u0001\u0012\u0002\u0013\u0005A\u0011\u0006\u0005\n\t#\u0002\u0011\u0013!C\u0001\t'B\u0011\u0002b\u001f\u0001#\u0003%\t\u0001\" \t\u0013\u0011\u0015\u0006!%A\u0005\u0002\u0011\u001d\u0006\"\u0003Ch\u0001E\u0005I\u0011\u0001Ci\u0011%!I\u0010AI\u0001\n\u0003!Y\u0010C\u0005\u0006$\u0001\t\t\u0011\"\u0011\u0006&!IQQ\u0007\u0001\u0002\u0002\u0013\u0005Sq\u0007\u0005\n\u000b\u000b\u0002\u0011\u0011!C\u0001\u000b\u000fB\u0011\"b\u0015\u0001\u0003\u0003%\t%\"\u0016\t\u0013\u0015}\u0003!!A\u0005B\u0015\u0005\u0004\"CC2\u0001\u0005\u0005I\u0011IC3\u000f%)I'RA\u0001\u0012\u0003)YG\u0002\u0005E\u000b\u0006\u0005\t\u0012AC7\u0011\u001d\tiJ\u0010C\u0001\u000bsB\u0011\"!2?\u0003\u0003%)%b\u001f\t\u0013\u0015ud(!A\u0005\u0002\u0016}\u0004\"CCv}\u0005\u0005I\u0011QCw\u0011%1\tEPA\u0001\n\u00131\u0019EA\u0004UkBdW-M\u001c\u000b\u0003\u0019\u000bQa]2bY\u0006\u001c\u0001!\u0006\fJ'v\u00037MZ5m_J,\bp\u001f@\u0002\u0004\u0005%\u0011qBA\u000b'\u001d\u0001!JTA\r\u0003?\u0001\"a\u0013'\u000e\u0003\u0015K!!T#\u0003\r\u0005s\u0017PU3g!]Yu*\u0015/`E\u0016D7N\\9uojl\u0018\u0011AA\u0004\u0003\u001b\t\u0019\"\u0003\u0002Q\u000b\nI\u0001K]8ek\u000e$\u0018g\u000e\t\u0003%Nc\u0001\u0001\u0002\u0004U\u0001\u0011\u0015\r!\u0016\u0002\u0003)F\n\"AV-\u0011\u0005-;\u0016B\u0001-F\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"a\u0013.\n\u0005m+%aA!osB\u0011!+\u0018\u0003\u0007=\u0002!)\u0019A+\u0003\u0005Q\u0013\u0004C\u0001*a\t\u0019\t\u0007\u0001\"b\u0001+\n\u0011Ak\r\t\u0003%\u000e$a\u0001\u001a\u0001\u0005\u0006\u0004)&A\u0001+5!\t\u0011f\r\u0002\u0004h\u0001\u0011\u0015\r!\u0016\u0002\u0003)V\u0002\"AU5\u0005\r)\u0004AQ1\u0001V\u0005\t!f\u0007\u0005\u0002SY\u00121Q\u000e\u0001CC\u0002U\u0013!\u0001V\u001c\u0011\u0005I{GA\u00029\u0001\t\u000b\u0007QK\u0001\u0002UqA\u0011!K\u001d\u0003\u0007g\u0002!)\u0019A+\u0003\u0005QK\u0004C\u0001*v\t\u00191\b\u0001\"b\u0001+\n\u0019A+\r\u0019\u0011\u0005ICHAB=\u0001\t\u000b\u0007QKA\u0002UcE\u0002\"AU>\u0005\rq\u0004AQ1\u0001V\u0005\r!\u0016G\r\t\u0003%z$aa \u0001\u0005\u0006\u0004)&a\u0001+2gA\u0019!+a\u0001\u0005\u000f\u0005\u0015\u0001\u0001\"b\u0001+\n\u0019A+\r\u001b\u0011\u0007I\u000bI\u0001B\u0004\u0002\f\u0001!)\u0019A+\u0003\u0007Q\u000bT\u0007E\u0002S\u0003\u001f!q!!\u0005\u0001\t\u000b\u0007QKA\u0002UcY\u00022AUA\u000b\t\u001d\t9\u0002\u0001CC\u0002U\u00131\u0001V\u00198!\rY\u00151D\u0005\u0004\u0003;)%a\u0002)s_\u0012,8\r\u001e\t\u0005\u0003C\t\tD\u0004\u0003\u0002$\u00055b\u0002BA\u0013\u0003Wi!!a\n\u000b\u0007\u0005%r)\u0001\u0004=e>|GOP\u0005\u0002\r&\u0019\u0011qF#\u0002\u000fA\f7m[1hK&!\u00111GA\u001b\u00051\u0019VM]5bY&T\u0018M\u00197f\u0015\r\ty#R\u0001\u0003?F*\u0012!U\u0001\u0004?F\u0002\u0013AA03+\u0005a\u0016aA03A\u0005\u0011qlM\u000b\u0002?\u0006\u0019ql\r\u0011\u0002\u0005}#T#\u00012\u0002\u0007}#\u0004%\u0001\u0002`kU\tQ-A\u0002`k\u0001\n!a\u0018\u001c\u0016\u0003!\f1a\u0018\u001c!\u0003\tyv'F\u0001l\u0003\ryv\u0007I\u0001\u0003?b*\u0012A\\\u0001\u0004?b\u0002\u0013AA0:+\u0005\t\u0018aA0:A\u0005\u0019q,\r\u0019\u0016\u0003Q\fAaX\u00191A\u0005\u0019q,M\u0019\u0016\u0003]\fAaX\u00192A\u0005\u0019q,\r\u001a\u0016\u0003i\fAaX\u00193A\u0005\u0019q,M\u001a\u0016\u0003u\fAaX\u00194A\u0005\u0019q,\r\u001b\u0016\u0005\u0005\u0005\u0011\u0001B02i\u0001\n1aX\u00196+\t\t9!\u0001\u0003`cU\u0002\u0013aA02mU\u0011\u0011QB\u0001\u0005?F2\u0004%A\u0002`c]*\"!a\u0005\u0002\t}\u000bt\u0007I\u0001\u0007y%t\u0017\u000e\u001e \u0015I\u0005\u0005\u00161UAS\u0003O\u000bI+a+\u0002.\u0006=\u0016\u0011WAZ\u0003k\u000b9,!/\u0002<\u0006u\u0016qXAa\u0003\u0007\u0004rc\u0013\u0001R9~\u0013W\r[6ocR<(0`A\u0001\u0003\u000f\ti!a\u0005\t\r\u0005]2\u00051\u0001R\u0011\u0019\tid\ta\u00019\"1\u00111I\u0012A\u0002}Ca!!\u0013$\u0001\u0004\u0011\u0007BBA(G\u0001\u0007Q\r\u0003\u0004\u0002V\r\u0002\r\u0001\u001b\u0005\u0007\u00037\u001a\u0003\u0019A6\t\r\u0005\u00054\u00051\u0001o\u0011\u0019\t9g\ta\u0001c\"1\u0011QN\u0012A\u0002QDa!a\u001d$\u0001\u00049\bBBA=G\u0001\u0007!\u0010\u0003\u0004\u0002\u0000\r\u0002\r! \u0005\b\u0003\u000b\u001b\u0003\u0019AA\u0001\u0011\u001d\tYi\ta\u0001\u0003\u000fAq!!%$\u0001\u0004\ti\u0001C\u0004\u0002\u0018\u000e\u0002\r!a\u0005\u0002\u0011Q|7\u000b\u001e:j]\u001e$\"!!3\u0011\t\u0005-\u00171\u001b\b\u0005\u0003\u001b\fy\rE\u0002\u0002&\u0015K1!!5F\u0003\u0019\u0001&/\u001a3fM&!\u0011Q[Al\u0005\u0019\u0019FO]5oO*\u0019\u0011\u0011[#\u0002\t\r|\u0007/_\u000b%\u0003;\f\u0019/a:\u0002l\u0006=\u00181_A|\u0003w\fyPa\u0001\u0003\b\t-!q\u0002B\n\u0005/\u0011YBa\b\u0003$Q!\u0013q\u001cB\u0013\u0005O\u0011ICa\u000b\u0003.\t=\"\u0011\u0007B\u001a\u0005k\u00119D!\u000f\u0003<\tu\"q\bB!\u0005\u0007\u0012)\u0005\u0005\u0013L\u0001\u0005\u0005\u0018Q]Au\u0003[\f\t0!>\u0002z\u0006u(\u0011\u0001B\u0003\u0005\u0013\u0011iA!\u0005\u0003\u0016\te!Q\u0004B\u0011!\r\u0011\u00161\u001d\u0003\u0006)\u0016\u0012\r!\u0016\t\u0004%\u0006\u001dH!\u00020&\u0005\u0004)\u0006c\u0001*\u0002l\u0012)\u0011-\nb\u0001+B\u0019!+a<\u0005\u000b\u0011,#\u0019A+\u0011\u0007I\u000b\u0019\u0010B\u0003hK\t\u0007Q\u000bE\u0002S\u0003o$QA[\u0013C\u0002U\u00032AUA~\t\u0015iWE1\u0001V!\r\u0011\u0016q \u0003\u0006a\u0016\u0012\r!\u0016\t\u0004%\n\rA!B:&\u0005\u0004)\u0006c\u0001*\u0003\b\u0011)a/\nb\u0001+B\u0019!Ka\u0003\u0005\u000be,#\u0019A+\u0011\u0007I\u0013y\u0001B\u0003}K\t\u0007Q\u000bE\u0002S\u0005'!Qa`\u0013C\u0002U\u00032A\u0015B\f\t\u0019\t)!\nb\u0001+B\u0019!Ka\u0007\u0005\r\u0005-QE1\u0001V!\r\u0011&q\u0004\u0003\u0007\u0003#)#\u0019A+\u0011\u0007I\u0013\u0019\u0003\u0002\u0004\u0002\u0018\u0015\u0012\r!\u0016\u0005\n\u0003o)\u0003\u0013!a\u0001\u0003CD\u0011\"!\u0010&!\u0003\u0005\r!!:\t\u0013\u0005\rS\u0005%AA\u0002\u0005%\b\"CA%KA\u0005\t\u0019AAw\u0011%\ty%\nI\u0001\u0002\u0004\t\t\u0010C\u0005\u0002V\u0015\u0002\n\u00111\u0001\u0002v\"I\u00111L\u0013\u0011\u0002\u0003\u0007\u0011\u0011 \u0005\n\u0003C*\u0003\u0013!a\u0001\u0003{D\u0011\"a\u001a&!\u0003\u0005\rA!\u0001\t\u0013\u00055T\u0005%AA\u0002\t\u0015\u0001\"CA:KA\u0005\t\u0019\u0001B\u0005\u0011%\tI(\nI\u0001\u0002\u0004\u0011i\u0001C\u0005\u0002\u0000\u0015\u0002\n\u00111\u0001\u0003\u0012!I\u0011QQ\u0013\u0011\u0002\u0003\u0007!Q\u0003\u0005\n\u0003\u0017+\u0003\u0013!a\u0001\u00053A\u0011\"!%&!\u0003\u0005\rA!\b\t\u0013\u0005]U\u0005%AA\u0002\t\u0005\u0012AD2paf$C-\u001a4bk2$H%M\u000b%\u0005\u0017\u0012\tGa\u0019\u0003f\t\u001d$\u0011\u000eB6\u0005[\u0012yG!\u001d\u0003t\tU$q\u000fB=\u0005w\u0012iHa \u0003\u0002V\u0011!Q\n\u0016\u0004#\n=3F\u0001B)!\u0011\u0011\u0019F!\u0018\u000e\u0005\tU#\u0002\u0002B,\u00053\n\u0011\"\u001e8dQ\u0016\u001c7.\u001a3\u000b\u0007\tmS)\u0001\u0006b]:|G/\u0019;j_:LAAa\u0018\u0003V\t\tRO\\2iK\u000e\\W\r\u001a,be&\fgnY3\u0005\u000bQ3#\u0019A+\u0005\u000by3#\u0019A+\u0005\u000b\u00054#\u0019A+\u0005\u000b\u00114#\u0019A+\u0005\u000b\u001d4#\u0019A+\u0005\u000b)4#\u0019A+\u0005\u000b54#\u0019A+\u0005\u000bA4#\u0019A+\u0005\u000bM4#\u0019A+\u0005\u000bY4#\u0019A+\u0005\u000be4#\u0019A+\u0005\u000bq4#\u0019A+\u0005\u000b}4#\u0019A+\u0005\r\u0005\u0015aE1\u0001V\t\u0019\tYA\nb\u0001+\u00121\u0011\u0011\u0003\u0014C\u0002U#a!a\u0006'\u0005\u0004)\u0016AD2paf$C-\u001a4bk2$HEM\u000b%\u0005\u000f\u0013YI!$\u0003\u0010\nE%1\u0013BK\u0005/\u0013IJa'\u0003\u001e\n}%\u0011\u0015BR\u0005K\u00139K!+\u0003,V\u0011!\u0011\u0012\u0016\u00049\n=C!\u0002+(\u0005\u0004)F!\u00020(\u0005\u0004)F!B1(\u0005\u0004)F!\u00023(\u0005\u0004)F!B4(\u0005\u0004)F!\u00026(\u0005\u0004)F!B7(\u0005\u0004)F!\u00029(\u0005\u0004)F!B:(\u0005\u0004)F!\u0002<(\u0005\u0004)F!B=(\u0005\u0004)F!\u0002?(\u0005\u0004)F!B@(\u0005\u0004)FABA\u0003O\t\u0007Q\u000b\u0002\u0004\u0002\f\u001d\u0012\r!\u0016\u0003\u0007\u0003#9#\u0019A+\u0005\r\u0005]qE1\u0001V\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIM*BE!-\u00036\n]&\u0011\u0018B^\u0005{\u0013yL!1\u0003D\n\u0015'q\u0019Be\u0005\u0017\u0014iMa4\u0003R\nM'Q[\u000b\u0003\u0005gS3a\u0018B(\t\u0015!\u0006F1\u0001V\t\u0015q\u0006F1\u0001V\t\u0015\t\u0007F1\u0001V\t\u0015!\u0007F1\u0001V\t\u00159\u0007F1\u0001V\t\u0015Q\u0007F1\u0001V\t\u0015i\u0007F1\u0001V\t\u0015\u0001\bF1\u0001V\t\u0015\u0019\bF1\u0001V\t\u00151\bF1\u0001V\t\u0015I\bF1\u0001V\t\u0015a\bF1\u0001V\t\u0015y\bF1\u0001V\t\u0019\t)\u0001\u000bb\u0001+\u00121\u00111\u0002\u0015C\u0002U#a!!\u0005)\u0005\u0004)FABA\fQ\t\u0007Q+\u0001\bd_BLH\u0005Z3gCVdG\u000f\n\u001b\u0016I\tm'q\u001cBq\u0005G\u0014)Oa:\u0003j\n-(Q\u001eBx\u0005c\u0014\u0019P!>\u0003x\ne(1 B\u007f\u0005\u007f,\"A!8+\u0007\t\u0014y\u0005B\u0003US\t\u0007Q\u000bB\u0003_S\t\u0007Q\u000bB\u0003bS\t\u0007Q\u000bB\u0003eS\t\u0007Q\u000bB\u0003hS\t\u0007Q\u000bB\u0003kS\t\u0007Q\u000bB\u0003nS\t\u0007Q\u000bB\u0003qS\t\u0007Q\u000bB\u0003tS\t\u0007Q\u000bB\u0003wS\t\u0007Q\u000bB\u0003zS\t\u0007Q\u000bB\u0003}S\t\u0007Q\u000bB\u0003\u0000S\t\u0007Q\u000b\u0002\u0004\u0002\u0006%\u0012\r!\u0016\u0003\u0007\u0003\u0017I#\u0019A+\u0005\r\u0005E\u0011F1\u0001V\t\u0019\t9\"\u000bb\u0001+\u0006q1m\u001c9zI\u0011,g-Y;mi\u0012*T\u0003JB\u0003\u0007\u0013\u0019Ya!\u0004\u0004\u0010\rE11CB\u000b\u0007/\u0019Iba\u0007\u0004\u001e\r}1\u0011EB\u0012\u0007K\u00199c!\u000b\u0016\u0005\r\u001d!fA3\u0003P\u0011)AK\u000bb\u0001+\u0012)aL\u000bb\u0001+\u0012)\u0011M\u000bb\u0001+\u0012)AM\u000bb\u0001+\u0012)qM\u000bb\u0001+\u0012)!N\u000bb\u0001+\u0012)QN\u000bb\u0001+\u0012)\u0001O\u000bb\u0001+\u0012)1O\u000bb\u0001+\u0012)aO\u000bb\u0001+\u0012)\u0011P\u000bb\u0001+\u0012)AP\u000bb\u0001+\u0012)qP\u000bb\u0001+\u00121\u0011Q\u0001\u0016C\u0002U#a!a\u0003+\u0005\u0004)FABA\tU\t\u0007Q\u000b\u0002\u0004\u0002\u0018)\u0012\r!V\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00137+\u0011\u001ayca\r\u00046\r]2\u0011HB\u001e\u0007{\u0019yd!\u0011\u0004D\r\u00153qIB%\u0007\u0017\u001aiea\u0014\u0004R\rMSCAB\u0019U\rA'q\n\u0003\u0006).\u0012\r!\u0016\u0003\u0006=.\u0012\r!\u0016\u0003\u0006C.\u0012\r!\u0016\u0003\u0006I.\u0012\r!\u0016\u0003\u0006O.\u0012\r!\u0016\u0003\u0006U.\u0012\r!\u0016\u0003\u0006[.\u0012\r!\u0016\u0003\u0006a.\u0012\r!\u0016\u0003\u0006g.\u0012\r!\u0016\u0003\u0006m.\u0012\r!\u0016\u0003\u0006s.\u0012\r!\u0016\u0003\u0006y.\u0012\r!\u0016\u0003\u0006\u007f.\u0012\r!\u0016\u0003\u0007\u0003\u000bY#\u0019A+\u0005\r\u0005-1F1\u0001V\t\u0019\t\tb\u000bb\u0001+\u00121\u0011qC\u0016C\u0002U\u000babY8qs\u0012\"WMZ1vYR$s'\u0006\u0013\u0004Z\ru3qLB1\u0007G\u001a)ga\u001a\u0004j\r-4QNB8\u0007c\u001a\u0019h!\u001e\u0004x\re41PB?+\t\u0019YFK\u0002l\u0005\u001f\"Q\u0001\u0016\u0017C\u0002U#QA\u0018\u0017C\u0002U#Q!\u0019\u0017C\u0002U#Q\u0001\u001a\u0017C\u0002U#Qa\u001a\u0017C\u0002U#QA\u001b\u0017C\u0002U#Q!\u001c\u0017C\u0002U#Q\u0001\u001d\u0017C\u0002U#Qa\u001d\u0017C\u0002U#QA\u001e\u0017C\u0002U#Q!\u001f\u0017C\u0002U#Q\u0001 \u0017C\u0002U#Qa \u0017C\u0002U#a!!\u0002-\u0005\u0004)FABA\u0006Y\t\u0007Q\u000b\u0002\u0004\u0002\u00121\u0012\r!\u0016\u0003\u0007\u0003/a#\u0019A+\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%qU!31QBD\u0007\u0013\u001bYi!$\u0004\u0010\u000eE51SBK\u0007/\u001bIja'\u0004\u001e\u000e}5\u0011UBR\u0007K\u001b9+\u0006\u0002\u0004\u0006*\u001aaNa\u0014\u0005\u000bQk#\u0019A+\u0005\u000byk#\u0019A+\u0005\u000b\u0005l#\u0019A+\u0005\u000b\u0011l#\u0019A+\u0005\u000b\u001dl#\u0019A+\u0005\u000b)l#\u0019A+\u0005\u000b5l#\u0019A+\u0005\u000bAl#\u0019A+\u0005\u000bMl#\u0019A+\u0005\u000bYl#\u0019A+\u0005\u000bel#\u0019A+\u0005\u000bql#\u0019A+\u0005\u000b}l#\u0019A+\u0005\r\u0005\u0015QF1\u0001V\t\u0019\tY!\fb\u0001+\u00121\u0011\u0011C\u0017C\u0002U#a!a\u0006.\u0005\u0004)\u0016AD2paf$C-\u001a4bk2$H%O\u000b%\u0007[\u001b\tla-\u00046\u000e]6\u0011XB^\u0007{\u001byl!1\u0004D\u000e\u00157qYBe\u0007\u0017\u001cima4\u0004RV\u00111q\u0016\u0016\u0004c\n=C!\u0002+/\u0005\u0004)F!\u00020/\u0005\u0004)F!B1/\u0005\u0004)F!\u00023/\u0005\u0004)F!B4/\u0005\u0004)F!\u00026/\u0005\u0004)F!B7/\u0005\u0004)F!\u00029/\u0005\u0004)F!B:/\u0005\u0004)F!\u0002</\u0005\u0004)F!B=/\u0005\u0004)F!\u0002?/\u0005\u0004)F!B@/\u0005\u0004)FABA\u0003]\t\u0007Q\u000b\u0002\u0004\u0002\f9\u0012\r!\u0016\u0003\u0007\u0003#q#\u0019A+\u0005\r\u0005]aF1\u0001V\u0003=\u0019w\u000e]=%I\u00164\u0017-\u001e7uIE\u0002T\u0003JBl\u00077\u001cina8\u0004b\u000e\r8Q]Bt\u0007S\u001cYo!<\u0004p\u000eE81_B{\u0007o\u001cIpa?\u0016\u0005\re'f\u0001;\u0003P\u0011)Ak\fb\u0001+\u0012)al\fb\u0001+\u0012)\u0011m\fb\u0001+\u0012)Am\fb\u0001+\u0012)qm\fb\u0001+\u0012)!n\fb\u0001+\u0012)Qn\fb\u0001+\u0012)\u0001o\fb\u0001+\u0012)1o\fb\u0001+\u0012)ao\fb\u0001+\u0012)\u0011p\fb\u0001+\u0012)Ap\fb\u0001+\u0012)qp\fb\u0001+\u00121\u0011QA\u0018C\u0002U#a!a\u00030\u0005\u0004)FABA\t_\t\u0007Q\u000b\u0002\u0004\u0002\u0018=\u0012\r!V\u0001\u0010G>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00132cU!C\u0011\u0001C\u0003\t\u000f!I\u0001b\u0003\u0005\u000e\u0011=A\u0011\u0003C\n\t+!9\u0002\"\u0007\u0005\u001c\u0011uAq\u0004C\u0011\tG!)#\u0006\u0002\u0005\u0004)\u001aqOa\u0014\u0005\u000bQ\u0003$\u0019A+\u0005\u000by\u0003$\u0019A+\u0005\u000b\u0005\u0004$\u0019A+\u0005\u000b\u0011\u0004$\u0019A+\u0005\u000b\u001d\u0004$\u0019A+\u0005\u000b)\u0004$\u0019A+\u0005\u000b5\u0004$\u0019A+\u0005\u000bA\u0004$\u0019A+\u0005\u000bM\u0004$\u0019A+\u0005\u000bY\u0004$\u0019A+\u0005\u000be\u0004$\u0019A+\u0005\u000bq\u0004$\u0019A+\u0005\u000b}\u0004$\u0019A+\u0005\r\u0005\u0015\u0001G1\u0001V\t\u0019\tY\u0001\rb\u0001+\u00121\u0011\u0011\u0003\u0019C\u0002U#a!a\u00061\u0005\u0004)\u0016aD2paf$C-\u001a4bk2$H%\r\u001a\u0016I\u0011-Bq\u0006C\u0019\tg!)\u0004b\u000e\u0005:\u0011mBQ\bC \t\u0003\"\u0019\u0005\"\u0012\u0005H\u0011%C1\nC'\t\u001f*\"\u0001\"\f+\u0007i\u0014y\u0005B\u0003Uc\t\u0007Q\u000bB\u0003_c\t\u0007Q\u000bB\u0003bc\t\u0007Q\u000bB\u0003ec\t\u0007Q\u000bB\u0003hc\t\u0007Q\u000bB\u0003kc\t\u0007Q\u000bB\u0003nc\t\u0007Q\u000bB\u0003qc\t\u0007Q\u000bB\u0003tc\t\u0007Q\u000bB\u0003wc\t\u0007Q\u000bB\u0003zc\t\u0007Q\u000bB\u0003}c\t\u0007Q\u000bB\u0003\u0000c\t\u0007Q\u000b\u0002\u0004\u0002\u0006E\u0012\r!\u0016\u0003\u0007\u0003\u0017\t$\u0019A+\u0005\r\u0005E\u0011G1\u0001V\t\u0019\t9\"\rb\u0001+\u0006y1m\u001c9zI\u0011,g-Y;mi\u0012\n4'\u0006\u0013\u0005V\u0011eC1\fC/\t?\"\t\u0007b\u0019\u0005f\u0011\u001dD\u0011\u000eC6\t[\"y\u0007\"\u001d\u0005t\u0011UDq\u000fC=+\t!9FK\u0002~\u0005\u001f\"Q\u0001\u0016\u001aC\u0002U#QA\u0018\u001aC\u0002U#Q!\u0019\u001aC\u0002U#Q\u0001\u001a\u001aC\u0002U#Qa\u001a\u001aC\u0002U#QA\u001b\u001aC\u0002U#Q!\u001c\u001aC\u0002U#Q\u0001\u001d\u001aC\u0002U#Qa\u001d\u001aC\u0002U#QA\u001e\u001aC\u0002U#Q!\u001f\u001aC\u0002U#Q\u0001 \u001aC\u0002U#Qa \u001aC\u0002U#a!!\u00023\u0005\u0004)FABA\u0006e\t\u0007Q\u000b\u0002\u0004\u0002\u0012I\u0012\r!\u0016\u0003\u0007\u0003/\u0011$\u0019A+\u0002\u001f\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%cQ*B\u0005b \u0005\u0004\u0012\u0015Eq\u0011CE\t\u0017#i\tb$\u0005\u0012\u0012MEQ\u0013CL\t3#Y\n\"(\u0005 \u0012\u0005F1U\u000b\u0003\t\u0003SC!!\u0001\u0003P\u0011)Ak\rb\u0001+\u0012)al\rb\u0001+\u0012)\u0011m\rb\u0001+\u0012)Am\rb\u0001+\u0012)qm\rb\u0001+\u0012)!n\rb\u0001+\u0012)Qn\rb\u0001+\u0012)\u0001o\rb\u0001+\u0012)1o\rb\u0001+\u0012)ao\rb\u0001+\u0012)\u0011p\rb\u0001+\u0012)Ap\rb\u0001+\u0012)qp\rb\u0001+\u00121\u0011QA\u001aC\u0002U#a!a\u00034\u0005\u0004)FABA\tg\t\u0007Q\u000b\u0002\u0004\u0002\u0018M\u0012\r!V\u0001\u0010G>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00132kU!C\u0011\u0016CW\t_#\t\fb-\u00056\u0012]F\u0011\u0018C^\t{#y\f\"1\u0005D\u0012\u0015Gq\u0019Ce\t\u0017$i-\u0006\u0002\u0005,*\"\u0011q\u0001B(\t\u0015!FG1\u0001V\t\u0015qFG1\u0001V\t\u0015\tGG1\u0001V\t\u0015!GG1\u0001V\t\u00159GG1\u0001V\t\u0015QGG1\u0001V\t\u0015iGG1\u0001V\t\u0015\u0001HG1\u0001V\t\u0015\u0019HG1\u0001V\t\u00151HG1\u0001V\t\u0015IHG1\u0001V\t\u0015aHG1\u0001V\t\u0015yHG1\u0001V\t\u0019\t)\u0001\u000eb\u0001+\u00121\u00111\u0002\u001bC\u0002U#a!!\u00055\u0005\u0004)FABA\fi\t\u0007Q+A\bd_BLH\u0005Z3gCVdG\u000fJ\u00197+\u0011\"\u0019\u000eb6\u0005Z\u0012mGQ\u001cCp\tC$\u0019\u000f\":\u0005h\u0012%H1\u001eCw\t_$\t\u0010b=\u0005v\u0012]XC\u0001CkU\u0011\tiAa\u0014\u0005\u000bQ+$\u0019A+\u0005\u000by+$\u0019A+\u0005\u000b\u0005,$\u0019A+\u0005\u000b\u0011,$\u0019A+\u0005\u000b\u001d,$\u0019A+\u0005\u000b),$\u0019A+\u0005\u000b5,$\u0019A+\u0005\u000bA,$\u0019A+\u0005\u000bM,$\u0019A+\u0005\u000bY,$\u0019A+\u0005\u000be,$\u0019A+\u0005\u000bq,$\u0019A+\u0005\u000b},$\u0019A+\u0005\r\u0005\u0015QG1\u0001V\t\u0019\tY!\u000eb\u0001+\u00121\u0011\u0011C\u001bC\u0002U#a!a\u00066\u0005\u0004)\u0016aD2paf$C-\u001a4bk2$H%M\u001c\u0016I\u0011uX\u0011AC\u0002\u000b\u000b)9!\"\u0003\u0006\f\u00155QqBC\t\u000b'))\"b\u0006\u0006\u001a\u0015mQQDC\u0010\u000bC)\"\u0001b@+\t\u0005M!q\n\u0003\u0006)Z\u0012\r!\u0016\u0003\u0006=Z\u0012\r!\u0016\u0003\u0006CZ\u0012\r!\u0016\u0003\u0006IZ\u0012\r!\u0016\u0003\u0006OZ\u0012\r!\u0016\u0003\u0006UZ\u0012\r!\u0016\u0003\u0006[Z\u0012\r!\u0016\u0003\u0006aZ\u0012\r!\u0016\u0003\u0006gZ\u0012\r!\u0016\u0003\u0006mZ\u0012\r!\u0016\u0003\u0006sZ\u0012\r!\u0016\u0003\u0006yZ\u0012\r!\u0016\u0003\u0006\u007fZ\u0012\r!\u0016\u0003\u0007\u0003\u000b1$\u0019A+\u0005\r\u0005-aG1\u0001V\t\u0019\t\tB\u000eb\u0001+\u00121\u0011q\u0003\u001cC\u0002U\u000bQ\u0002\u001d:pIV\u001cG\u000f\u0015:fM&DXCAC\u0014!\u0011)I#b\r\u000e\u0005\u0015-\"\u0002BC\u0017\u000b_\tA\u0001\\1oO*\u0011Q\u0011G\u0001\u0005U\u00064\u0018-\u0003\u0003\u0002V\u0016-\u0012a\u00049s_\u0012,8\r^%uKJ\fGo\u001c:\u0016\u0005\u0015e\u0002#BC\u001e\u000b\u0003JVBAC\u001f\u0015\r)y$R\u0001\u000bG>dG.Z2uS>t\u0017\u0002BC\"\u000b{\u0011\u0001\"\u0013;fe\u0006$xN]\u0001\tG\u0006tW)];bYR!Q\u0011JC(!\rYU1J\u0005\u0004\u000b\u001b*%a\u0002\"p_2,\u0017M\u001c\u0005\t\u000b#J\u0014\u0011!a\u00013\u0006\u0019\u0001\u0010J\u0019\u0002%A\u0014x\u000eZ;di\u0016cW-\\3oi:\u000bW.\u001a\u000b\u0005\u000bO)9\u0006C\u0005\u0006Ri\n\t\u00111\u0001\u0006ZA\u00191*b\u0017\n\u0007\u0015uSIA\u0002J]R\f\u0001\u0002[1tQ\u000e{G-\u001a\u000b\u0003\u000b3\na!Z9vC2\u001cH\u0003BC%\u000bOB\u0001\"\"\u0015=\u0003\u0003\u0005\r!W\u0001\b)V\u0004H.Z\u00198!\tYeh\u0005\u0003?\u0015\u0016=\u0004\u0003BC9\u000boj!!b\u001d\u000b\t\u0015UTqF\u0001\u0003S>LA!a\r\u0006tQ\u0011Q1\u000e\u000b\u0003\u000bO\tQ!\u00199qYf,B%\"!\u0006\b\u0016-UqRCJ\u000b/+Y*b(\u0006$\u0016\u001dV1VCX\u000bg+9,b/\u0006@\u0016\rWq\u0019\u000b%\u000b\u0007+I-b3\u0006N\u0016=W\u0011[Cj\u000b+,9.\"7\u0006\\\u0016uWq\\Cq\u000bG,)/b:\u0006jB!3\nACC\u000b\u0013+i)\"%\u0006\u0016\u0016eUQTCQ\u000bK+I+\",\u00062\u0016UV\u0011XC_\u000b\u0003,)\rE\u0002S\u000b\u000f#Q\u0001V!C\u0002U\u00032AUCF\t\u0015q\u0016I1\u0001V!\r\u0011Vq\u0012\u0003\u0006C\u0006\u0013\r!\u0016\t\u0004%\u0016ME!\u00023B\u0005\u0004)\u0006c\u0001*\u0006\u0018\u0012)q-\u0011b\u0001+B\u0019!+b'\u0005\u000b)\f%\u0019A+\u0011\u0007I+y\nB\u0003n\u0003\n\u0007Q\u000bE\u0002S\u000bG#Q\u0001]!C\u0002U\u00032AUCT\t\u0015\u0019\u0018I1\u0001V!\r\u0011V1\u0016\u0003\u0006m\u0006\u0013\r!\u0016\t\u0004%\u0016=F!B=B\u0005\u0004)\u0006c\u0001*\u00064\u0012)A0\u0011b\u0001+B\u0019!+b.\u0005\u000b}\f%\u0019A+\u0011\u0007I+Y\f\u0002\u0004\u0002\u0006\u0005\u0013\r!\u0016\t\u0004%\u0016}FABA\u0006\u0003\n\u0007Q\u000bE\u0002S\u000b\u0007$a!!\u0005B\u0005\u0004)\u0006c\u0001*\u0006H\u00121\u0011qC!C\u0002UCq!a\u000eB\u0001\u0004))\tC\u0004\u0002>\u0005\u0003\r!\"#\t\u000f\u0005\r\u0013\t1\u0001\u0006\u000e\"9\u0011\u0011J!A\u0002\u0015E\u0005bBA(\u0003\u0002\u0007QQ\u0013\u0005\b\u0003+\n\u0005\u0019ACM\u0011\u001d\tY&\u0011a\u0001\u000b;Cq!!\u0019B\u0001\u0004)\t\u000bC\u0004\u0002h\u0005\u0003\r!\"*\t\u000f\u00055\u0014\t1\u0001\u0006*\"9\u00111O!A\u0002\u00155\u0006bBA=\u0003\u0002\u0007Q\u0011\u0017\u0005\b\u0003\u007f\n\u0005\u0019AC[\u0011\u001d\t))\u0011a\u0001\u000bsCq!a#B\u0001\u0004)i\fC\u0004\u0002\u0012\u0006\u0003\r!\"1\t\u000f\u0005]\u0015\t1\u0001\u0006F\u00069QO\\1qa2LX\u0003JCx\u000bw,yPb\u0001\u0007\b\u0019-aq\u0002D\n\r/1YBb\b\u0007$\u0019\u001db1\u0006D\u0018\rg19Db\u000f\u0015\t\u0015EhQ\b\t\u0006\u0017\u0016MXq_\u0005\u0004\u000bk,%AB(qi&|g\u000e\u0005\u0013L\u0001\u0015eXQ D\u0001\r\u000b1IA\"\u0004\u0007\u0012\u0019Ua\u0011\u0004D\u000f\rC1)C\"\u000b\u0007.\u0019EbQ\u0007D\u001d!\r\u0011V1 \u0003\u0006)\n\u0013\r!\u0016\t\u0004%\u0016}H!\u00020C\u0005\u0004)\u0006c\u0001*\u0007\u0004\u0011)\u0011M\u0011b\u0001+B\u0019!Kb\u0002\u0005\u000b\u0011\u0014%\u0019A+\u0011\u0007I3Y\u0001B\u0003h\u0005\n\u0007Q\u000bE\u0002S\r\u001f!QA\u001b\"C\u0002U\u00032A\u0015D\n\t\u0015i'I1\u0001V!\r\u0011fq\u0003\u0003\u0006a\n\u0013\r!\u0016\t\u0004%\u001amA!B:C\u0005\u0004)\u0006c\u0001*\u0007 \u0011)aO\u0011b\u0001+B\u0019!Kb\t\u0005\u000be\u0014%\u0019A+\u0011\u0007I39\u0003B\u0003}\u0005\n\u0007Q\u000bE\u0002S\rW!Qa \"C\u0002U\u00032A\u0015D\u0018\t\u0019\t)A\u0011b\u0001+B\u0019!Kb\r\u0005\r\u0005-!I1\u0001V!\r\u0011fq\u0007\u0003\u0007\u0003#\u0011%\u0019A+\u0011\u0007I3Y\u0004\u0002\u0004\u0002\u0018\t\u0013\r!\u0016\u0005\n\r\u007f\u0011\u0015\u0011!a\u0001\u000bo\f1\u0001\u001f\u00131\u000319(/\u001b;f%\u0016\u0004H.Y2f)\t1)\u0005\u0005\u0003\u0006*\u0019\u001d\u0013\u0002\u0002D%\u000bW\u0011aa\u00142kK\u000e$\b")
public final class Tuple17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>
implements Product17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>,
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
    private final T17 _17;

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> Option<Tuple17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>> unapply(Tuple17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> x$0) {
        return Tuple17$.MODULE$.unapply(x$0);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> Tuple17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> apply(T1 _1, T2 _2, T3 _3, T4 _4, T5 _5, T6 _6, T7 _7, T8 _8, T9 _9, T10 _10, T11 _11, T12 _12, T13 _13, T14 _14, T15 _15, T16 _16, T17 _17) {
        return new Tuple17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>(_1, _2, _3, _4, _5, _6, _7, _8, _9, _10, _11, _12, _13, _14, _15, _16, _17);
    }

    @Override
    public int productArity() {
        return Product17.productArity$(this);
    }

    @Override
    public Object productElement(int n) throws IndexOutOfBoundsException {
        return Product17.productElement$(this, n);
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

    @Override
    public T17 _17() {
        return this._17;
    }

    public String toString() {
        return new StringBuilder(18).append("(").append(this._1()).append(",").append(this._2()).append(",").append(this._3()).append(",").append(this._4()).append(",").append(this._5()).append(",").append(this._6()).append(",").append(this._7()).append(",").append(this._8()).append(",").append(this._9()).append(",").append(this._10()).append(",").append(this._11()).append(",").append(this._12()).append(",").append(this._13()).append(",").append(this._14()).append(",").append(this._15()).append(",").append(this._16()).append(",").append(this._17()).append(")").toString();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> Tuple17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> copy(T1 _1, T2 _2, T3 _3, T4 _4, T5 _5, T6 _6, T7 _7, T8 _8, T9 _9, T10 _10, T11 _11, T12 _12, T13 _13, T14 _14, T15 _15, T16 _16, T17 _17) {
        return new Tuple17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>(_1, _2, _3, _4, _5, _6, _7, _8, _9, _10, _11, _12, _13, _14, _15, _16, _17);
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> T1 copy$default$1() {
        return this._1();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> T10 copy$default$10() {
        return this._10();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> T11 copy$default$11() {
        return this._11();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> T12 copy$default$12() {
        return this._12();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> T13 copy$default$13() {
        return this._13();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> T14 copy$default$14() {
        return this._14();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> T15 copy$default$15() {
        return this._15();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> T16 copy$default$16() {
        return this._16();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> T17 copy$default$17() {
        return this._17();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> T2 copy$default$2() {
        return this._2();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> T3 copy$default$3() {
        return this._3();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> T4 copy$default$4() {
        return this._4();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> T5 copy$default$5() {
        return this._5();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> T6 copy$default$6() {
        return this._6();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> T7 copy$default$7() {
        return this._7();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> T8 copy$default$8() {
        return this._8();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17> T9 copy$default$9() {
        return this._9();
    }

    @Override
    public String productPrefix() {
        return "Tuple17";
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
        return x$1 instanceof Tuple17;
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
            case 16: {
                return "_17";
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
                if (!(x$1 instanceof Tuple17)) break block3;
                Tuple17 tuple17 = (Tuple17)x$1;
                if (!BoxesRunTime.equals(this._1(), tuple17._1()) || !BoxesRunTime.equals(this._2(), tuple17._2()) || !BoxesRunTime.equals(this._3(), tuple17._3()) || !BoxesRunTime.equals(this._4(), tuple17._4()) || !BoxesRunTime.equals(this._5(), tuple17._5()) || !BoxesRunTime.equals(this._6(), tuple17._6()) || !BoxesRunTime.equals(this._7(), tuple17._7()) || !BoxesRunTime.equals(this._8(), tuple17._8()) || !BoxesRunTime.equals(this._9(), tuple17._9()) || !BoxesRunTime.equals(this._10(), tuple17._10()) || !BoxesRunTime.equals(this._11(), tuple17._11()) || !BoxesRunTime.equals(this._12(), tuple17._12()) || !BoxesRunTime.equals(this._13(), tuple17._13()) || !BoxesRunTime.equals(this._14(), tuple17._14()) || !BoxesRunTime.equals(this._15(), tuple17._15()) || !BoxesRunTime.equals(this._16(), tuple17._16()) || !BoxesRunTime.equals(this._17(), tuple17._17())) break block3;
            }
            return true;
        }
        return false;
    }

    public Tuple17(T1 _1, T2 _2, T3 _3, T4 _4, T5 _5, T6 _6, T7 _7, T8 _8, T9 _9, T10 _10, T11 _11, T12 _12, T13 _13, T14 _14, T15 _15, T16 _16, T17 _17) {
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
        this._17 = _17;
    }
}

