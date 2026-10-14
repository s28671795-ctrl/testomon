/*
 * Decompiled with CFR 0.152.
 */
package scala;

import java.io.Serializable;
import scala.Option;
import scala.Product;
import scala.Product19;
import scala.Tuple19$;
import scala.collection.AbstractIterator;
import scala.collection.Iterator;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.Statics;
import scala.util.hashing.MurmurHash3$;

@ScalaSignature(bytes="\u0006\u0005\u001dMb\u0001\u0002&L\u0005:C!\"a\u0014\u0001\u0005+\u0007I\u0011AA)\u0011%\t\u0019\u0006\u0001B\tB\u0003%q\u000b\u0003\u0006\u0002V\u0001\u0011)\u001a!C\u0001\u0003/B\u0011\"!\u0017\u0001\u0005#\u0005\u000b\u0011\u00022\t\u0015\u0005m\u0003A!f\u0001\n\u0003\ti\u0006C\u0005\u0002`\u0001\u0011\t\u0012)A\u0005K\"Q\u0011\u0011\r\u0001\u0003\u0016\u0004%\t!a\u0019\t\u0013\u0005\u0015\u0004A!E!\u0002\u0013A\u0007BCA4\u0001\tU\r\u0011\"\u0001\u0002j!I\u00111\u000e\u0001\u0003\u0012\u0003\u0006Ia\u001b\u0005\u000b\u0003[\u0002!Q3A\u0005\u0002\u0005=\u0004\"CA9\u0001\tE\t\u0015!\u0003o\u0011)\t\u0019\b\u0001BK\u0002\u0013\u0005\u0011Q\u000f\u0005\n\u0003o\u0002!\u0011#Q\u0001\nED!\"!\u001f\u0001\u0005+\u0007I\u0011AA>\u0011%\ti\b\u0001B\tB\u0003%A\u000f\u0003\u0006\u0002\u0000\u0001\u0011)\u001a!C\u0001\u0003\u0003C\u0011\"a!\u0001\u0005#\u0005\u000b\u0011B<\t\u0015\u0005\u0015\u0005A!f\u0001\n\u0003\t9\tC\u0005\u0002\n\u0002\u0011\t\u0012)A\u0005u\"Q\u00111\u0012\u0001\u0003\u0016\u0004%\t!!$\t\u0013\u0005=\u0005A!E!\u0002\u0013i\bBCAI\u0001\tU\r\u0011\"\u0001\u0002\u0014\"Q\u0011Q\u0013\u0001\u0003\u0012\u0003\u0006I!!\u0001\t\u0015\u0005]\u0005A!f\u0001\n\u0003\tI\n\u0003\u0006\u0002\u001c\u0002\u0011\t\u0012)A\u0005\u0003\u000fA!\"!(\u0001\u0005+\u0007I\u0011AAP\u0011)\t\t\u000b\u0001B\tB\u0003%\u0011Q\u0002\u0005\u000b\u0003G\u0003!Q3A\u0005\u0002\u0005\u0015\u0006BCAT\u0001\tE\t\u0015!\u0003\u0002\u0014!Q\u0011\u0011\u0016\u0001\u0003\u0016\u0004%\t!a+\t\u0015\u00055\u0006A!E!\u0002\u0013\tI\u0002\u0003\u0006\u00020\u0002\u0011)\u001a!C\u0001\u0003cC!\"a-\u0001\u0005#\u0005\u000b\u0011BA\u0010\u0011)\t)\f\u0001BK\u0002\u0013\u0005\u0011q\u0017\u0005\u000b\u0003s\u0003!\u0011#Q\u0001\n\u0005\u0015\u0002BCA^\u0001\tU\r\u0011\"\u0001\u0002>\"Q\u0011q\u0018\u0001\u0003\u0012\u0003\u0006I!a\u000b\t\u000f\u0005\u0005\u0007\u0001\"\u0001\u0002D\"9\u0011Q\u001e\u0001\u0005B\u0005=\b\"\u0003B\u0001\u0001\u0005\u0005I\u0011\u0001B\u0002\u0011%\u0011Y\bAI\u0001\n\u0003\u0011i\bC\u0005\u0003<\u0002\t\n\u0011\"\u0001\u0003>\"I!\u0011\u001e\u0001\u0012\u0002\u0013\u0005!1\u001e\u0005\n\u0007/\u0001\u0011\u0013!C\u0001\u00073A\u0011b!\u0012\u0001#\u0003%\taa\u0012\t\u0013\rM\u0004!%A\u0005\u0002\rU\u0004\"CBQ\u0001E\u0005I\u0011ABR\u0011%\u0019y\rAI\u0001\n\u0003\u0019\t\u000eC\u0005\u0004~\u0002\t\n\u0011\"\u0001\u0004\u0000\"IA1\u0006\u0001\u0012\u0002\u0013\u0005AQ\u0006\u0005\n\t3\u0002\u0011\u0013!C\u0001\t7B\u0011\u0002b\"\u0001#\u0003%\t\u0001\"#\t\u0013\u0011U\u0006!%A\u0005\u0002\u0011]\u0006\"\u0003Cr\u0001E\u0005I\u0011\u0001Cs\u0011%)\t\u0002AI\u0001\n\u0003)\u0019\u0002C\u0005\u0006@\u0001\t\n\u0011\"\u0001\u0006B!IQQ\u000e\u0001\u0012\u0002\u0013\u0005Qq\u000e\u0005\n\u000b7\u0003\u0011\u0013!C\u0001\u000b;C\u0011\"\"3\u0001#\u0003%\t!b3\t\u0013\u0015]\b!!A\u0005B\u0015e\b\"\u0003D\u0005\u0001\u0005\u0005I\u0011\tD\u0006\u0011%1I\u0002AA\u0001\n\u00031Y\u0002C\u0005\u0007(\u0001\t\t\u0011\"\u0011\u0007*!Ia1\u0007\u0001\u0002\u0002\u0013\u0005cQ\u0007\u0005\n\ro\u0001\u0011\u0011!C!\rs9\u0011B\"\u0010L\u0003\u0003E\tAb\u0010\u0007\u0011)[\u0015\u0011!E\u0001\r\u0003Bq!!1E\t\u00031i\u0005C\u0005\u0002n\u0012\u000b\t\u0011\"\u0012\u0007P!Ia\u0011\u000b#\u0002\u0002\u0013\u0005e1\u000b\u0005\n\r\u0017$\u0015\u0011!CA\r\u001bD\u0011b\"\u000bE\u0003\u0003%Iab\u000b\u0003\u000fQ+\b\u000f\\32s)\tA*A\u0003tG\u0006d\u0017m\u0001\u0001\u00169=K6MZ5m_J,\bp\u001f@\u0002\u0004\u0005%\u0011qBA\u000b\u00037\t\t#a\n\u0002.M9\u0001\u0001\u0015+\u00022\u0005]\u0002CA)S\u001b\u0005Y\u0015BA*L\u0005\u0019\te.\u001f*fMBi\u0012+V,cK\"\\g.\u001d;xuv\f\t!a\u0002\u0002\u000e\u0005M\u0011\u0011DA\u0010\u0003K\tY#\u0003\u0002W\u0017\nI\u0001K]8ek\u000e$\u0018'\u000f\t\u00031fc\u0001\u0001\u0002\u0004[\u0001\u0011\u0015\ra\u0017\u0002\u0003)F\n\"\u0001X0\u0011\u0005Ek\u0016B\u00010L\u0005\u001dqu\u000e\u001e5j]\u001e\u0004\"!\u00151\n\u0005\u0005\\%aA!osB\u0011\u0001l\u0019\u0003\u0007I\u0002!)\u0019A.\u0003\u0005Q\u0013\u0004C\u0001-g\t\u00199\u0007\u0001\"b\u00017\n\u0011Ak\r\t\u00031&$aA\u001b\u0001\u0005\u0006\u0004Y&A\u0001+5!\tAF\u000e\u0002\u0004n\u0001\u0011\u0015\ra\u0017\u0002\u0003)V\u0002\"\u0001W8\u0005\rA\u0004AQ1\u0001\\\u0005\t!f\u0007\u0005\u0002Ye\u001211\u000f\u0001CC\u0002m\u0013!\u0001V\u001c\u0011\u0005a+HA\u0002<\u0001\t\u000b\u00071L\u0001\u0002UqA\u0011\u0001\f\u001f\u0003\u0007s\u0002!)\u0019A.\u0003\u0005QK\u0004C\u0001-|\t\u0019a\b\u0001\"b\u00017\n\u0019A+\r\u0019\u0011\u0005asHAB@\u0001\t\u000b\u00071LA\u0002UcE\u00022\u0001WA\u0002\t\u001d\t)\u0001\u0001CC\u0002m\u00131\u0001V\u00193!\rA\u0016\u0011\u0002\u0003\b\u0003\u0017\u0001AQ1\u0001\\\u0005\r!\u0016g\r\t\u00041\u0006=AaBA\t\u0001\u0011\u0015\ra\u0017\u0002\u0004)F\"\u0004c\u0001-\u0002\u0016\u00119\u0011q\u0003\u0001\u0005\u0006\u0004Y&a\u0001+2kA\u0019\u0001,a\u0007\u0005\u000f\u0005u\u0001\u0001\"b\u00017\n\u0019A+\r\u001c\u0011\u0007a\u000b\t\u0003B\u0004\u0002$\u0001!)\u0019A.\u0003\u0007Q\u000bt\u0007E\u0002Y\u0003O!q!!\u000b\u0001\t\u000b\u00071LA\u0002Uca\u00022\u0001WA\u0017\t\u001d\ty\u0003\u0001CC\u0002m\u00131\u0001V\u0019:!\r\t\u00161G\u0005\u0004\u0003kY%a\u0002)s_\u0012,8\r\u001e\t\u0005\u0003s\tIE\u0004\u0003\u0002<\u0005\u0015c\u0002BA\u001f\u0003\u0007j!!a\u0010\u000b\u0007\u0005\u0005S*\u0001\u0004=e>|GOP\u0005\u0002\u0019&\u0019\u0011qI&\u0002\u000fA\f7m[1hK&!\u00111JA'\u00051\u0019VM]5bY&T\u0018M\u00197f\u0015\r\t9eS\u0001\u0003?F*\u0012aV\u0001\u0004?F\u0002\u0013AA03+\u0005\u0011\u0017aA03A\u0005\u0011qlM\u000b\u0002K\u0006\u0019ql\r\u0011\u0002\u0005}#T#\u00015\u0002\u0007}#\u0004%\u0001\u0002`kU\t1.A\u0002`k\u0001\n!a\u0018\u001c\u0016\u00039\f1a\u0018\u001c!\u0003\tyv'F\u0001r\u0003\ryv\u0007I\u0001\u0003?b*\u0012\u0001^\u0001\u0004?b\u0002\u0013AA0:+\u00059\u0018aA0:A\u0005\u0019q,\r\u0019\u0016\u0003i\fAaX\u00191A\u0005\u0019q,M\u0019\u0016\u0003u\fAaX\u00192A\u0005\u0019q,\r\u001a\u0016\u0005\u0005\u0005\u0011\u0001B02e\u0001\n1aX\u00194+\t\t9!\u0001\u0003`cM\u0002\u0013aA02iU\u0011\u0011QB\u0001\u0005?F\"\u0004%A\u0002`cU*\"!a\u0005\u0002\t}\u000bT\u0007I\u0001\u0004?F2TCAA\r\u0003\u0011y\u0016G\u000e\u0011\u0002\u0007}\u000bt'\u0006\u0002\u0002 \u0005!q,M\u001c!\u0003\ry\u0016\u0007O\u000b\u0003\u0003K\tAaX\u00199A\u0005\u0019q,M\u001d\u0016\u0005\u0005-\u0012\u0001B02s\u0001\na\u0001P5oSRtD\u0003KAc\u0003\u000f\fI-a3\u0002N\u0006=\u0017\u0011[Aj\u0003+\f9.!7\u0002\\\u0006u\u0017q\\Aq\u0003G\f)/a:\u0002j\u0006-\b#H)\u0001/\n,\u0007n\u001b8ri^TX0!\u0001\u0002\b\u00055\u00111CA\r\u0003?\t)#a\u000b\t\r\u0005=s\u00051\u0001X\u0011\u0019\t)f\na\u0001E\"1\u00111L\u0014A\u0002\u0015Da!!\u0019(\u0001\u0004A\u0007BBA4O\u0001\u00071\u000e\u0003\u0004\u0002n\u001d\u0002\rA\u001c\u0005\u0007\u0003g:\u0003\u0019A9\t\r\u0005et\u00051\u0001u\u0011\u0019\tyh\na\u0001o\"1\u0011QQ\u0014A\u0002iDa!a#(\u0001\u0004i\bbBAIO\u0001\u0007\u0011\u0011\u0001\u0005\b\u0003/;\u0003\u0019AA\u0004\u0011\u001d\tij\na\u0001\u0003\u001bAq!a)(\u0001\u0004\t\u0019\u0002C\u0004\u0002*\u001e\u0002\r!!\u0007\t\u000f\u0005=v\u00051\u0001\u0002 !9\u0011QW\u0014A\u0002\u0005\u0015\u0002bBA^O\u0001\u0007\u00111F\u0001\ti>\u001cFO]5oOR\u0011\u0011\u0011\u001f\t\u0005\u0003g\fYP\u0004\u0003\u0002v\u0006]\bcAA\u001f\u0017&\u0019\u0011\u0011`&\u0002\rA\u0013X\rZ3g\u0013\u0011\ti0a@\u0003\rM#(/\u001b8h\u0015\r\tIpS\u0001\u0005G>\u0004\u00180\u0006\u0015\u0003\u0006\t-!q\u0002B\n\u0005/\u0011YBa\b\u0003$\t\u001d\"1\u0006B\u0018\u0005g\u00119Da\u000f\u0003@\t\r#q\tB&\u0005\u001f\u0012\u0019\u0006\u0006\u0015\u0003\b\tU#q\u000bB-\u00057\u0012iFa\u0018\u0003b\t\r$Q\rB4\u0005S\u0012YG!\u001c\u0003p\tE$1\u000fB;\u0005o\u0012I\b\u0005\u0015R\u0001\t%!Q\u0002B\t\u0005+\u0011IB!\b\u0003\"\t\u0015\"\u0011\u0006B\u0017\u0005c\u0011)D!\u000f\u0003>\t\u0005#Q\tB%\u0005\u001b\u0012\t\u0006E\u0002Y\u0005\u0017!QAW\u0015C\u0002m\u00032\u0001\u0017B\b\t\u0015!\u0017F1\u0001\\!\rA&1\u0003\u0003\u0006O&\u0012\ra\u0017\t\u00041\n]A!\u00026*\u0005\u0004Y\u0006c\u0001-\u0003\u001c\u0011)Q.\u000bb\u00017B\u0019\u0001La\b\u0005\u000bAL#\u0019A.\u0011\u0007a\u0013\u0019\u0003B\u0003tS\t\u00071\fE\u0002Y\u0005O!QA^\u0015C\u0002m\u00032\u0001\u0017B\u0016\t\u0015I\u0018F1\u0001\\!\rA&q\u0006\u0003\u0006y&\u0012\ra\u0017\t\u00041\nMB!B@*\u0005\u0004Y\u0006c\u0001-\u00038\u00111\u0011QA\u0015C\u0002m\u00032\u0001\u0017B\u001e\t\u0019\tY!\u000bb\u00017B\u0019\u0001La\u0010\u0005\r\u0005E\u0011F1\u0001\\!\rA&1\t\u0003\u0007\u0003/I#\u0019A.\u0011\u0007a\u00139\u0005\u0002\u0004\u0002\u001e%\u0012\ra\u0017\t\u00041\n-CABA\u0012S\t\u00071\fE\u0002Y\u0005\u001f\"a!!\u000b*\u0005\u0004Y\u0006c\u0001-\u0003T\u00111\u0011qF\u0015C\u0002mC\u0011\"a\u0014*!\u0003\u0005\rA!\u0003\t\u0013\u0005U\u0013\u0006%AA\u0002\t5\u0001\"CA.SA\u0005\t\u0019\u0001B\t\u0011%\t\t'\u000bI\u0001\u0002\u0004\u0011)\u0002C\u0005\u0002h%\u0002\n\u00111\u0001\u0003\u001a!I\u0011QN\u0015\u0011\u0002\u0003\u0007!Q\u0004\u0005\n\u0003gJ\u0003\u0013!a\u0001\u0005CA\u0011\"!\u001f*!\u0003\u0005\rA!\n\t\u0013\u0005}\u0014\u0006%AA\u0002\t%\u0002\"CACSA\u0005\t\u0019\u0001B\u0017\u0011%\tY)\u000bI\u0001\u0002\u0004\u0011\t\u0004C\u0005\u0002\u0012&\u0002\n\u00111\u0001\u00036!I\u0011qS\u0015\u0011\u0002\u0003\u0007!\u0011\b\u0005\n\u0003;K\u0003\u0013!a\u0001\u0005{A\u0011\"a)*!\u0003\u0005\rA!\u0011\t\u0013\u0005%\u0016\u0006%AA\u0002\t\u0015\u0003\"CAXSA\u0005\t\u0019\u0001B%\u0011%\t),\u000bI\u0001\u0002\u0004\u0011i\u0005C\u0005\u0002<&\u0002\n\u00111\u0001\u0003R\u0005q1m\u001c9zI\u0011,g-Y;mi\u0012\nT\u0003\u000bB@\u0005+\u00139J!'\u0003\u001c\nu%q\u0014BQ\u0005G\u0013)Ka*\u0003*\n-&Q\u0016BX\u0005c\u0013\u0019L!.\u00038\neVC\u0001BAU\r9&1Q\u0016\u0003\u0005\u000b\u0003BAa\"\u0003\u00126\u0011!\u0011\u0012\u0006\u0005\u0005\u0017\u0013i)A\u0005v]\u000eDWmY6fI*\u0019!qR&\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0003\u0003\u0014\n%%!E;oG\",7m[3e-\u0006\u0014\u0018.\u00198dK\u0012)!L\u000bb\u00017\u0012)AM\u000bb\u00017\u0012)qM\u000bb\u00017\u0012)!N\u000bb\u00017\u0012)QN\u000bb\u00017\u0012)\u0001O\u000bb\u00017\u0012)1O\u000bb\u00017\u0012)aO\u000bb\u00017\u0012)\u0011P\u000bb\u00017\u0012)AP\u000bb\u00017\u0012)qP\u000bb\u00017\u00121\u0011Q\u0001\u0016C\u0002m#a!a\u0003+\u0005\u0004YFABA\tU\t\u00071\f\u0002\u0004\u0002\u0018)\u0012\ra\u0017\u0003\u0007\u0003;Q#\u0019A.\u0005\r\u0005\r\"F1\u0001\\\t\u0019\tIC\u000bb\u00017\u00121\u0011q\u0006\u0016C\u0002m\u000babY8qs\u0012\"WMZ1vYR$#'\u0006\u0015\u0003@\n\r'Q\u0019Bd\u0005\u0013\u0014YM!4\u0003P\nE'1\u001bBk\u0005/\u0014INa7\u0003^\n}'\u0011\u001dBr\u0005K\u00149/\u0006\u0002\u0003B*\u001a!Ma!\u0005\u000bi[#\u0019A.\u0005\u000b\u0011\\#\u0019A.\u0005\u000b\u001d\\#\u0019A.\u0005\u000b)\\#\u0019A.\u0005\u000b5\\#\u0019A.\u0005\u000bA\\#\u0019A.\u0005\u000bM\\#\u0019A.\u0005\u000bY\\#\u0019A.\u0005\u000be\\#\u0019A.\u0005\u000bq\\#\u0019A.\u0005\u000b}\\#\u0019A.\u0005\r\u0005\u00151F1\u0001\\\t\u0019\tYa\u000bb\u00017\u00121\u0011\u0011C\u0016C\u0002m#a!a\u0006,\u0005\u0004YFABA\u000fW\t\u00071\f\u0002\u0004\u0002$-\u0012\ra\u0017\u0003\u0007\u0003SY#\u0019A.\u0005\r\u0005=2F1\u0001\\\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIM*\u0002F!<\u0003r\nM(Q\u001fB|\u0005s\u0014YP!@\u0003\u0000\u000e\u000511AB\u0003\u0007\u000f\u0019Iaa\u0003\u0004\u000e\r=1\u0011CB\n\u0007+)\"Aa<+\u0007\u0015\u0014\u0019\tB\u0003[Y\t\u00071\fB\u0003eY\t\u00071\fB\u0003hY\t\u00071\fB\u0003kY\t\u00071\fB\u0003nY\t\u00071\fB\u0003qY\t\u00071\fB\u0003tY\t\u00071\fB\u0003wY\t\u00071\fB\u0003zY\t\u00071\fB\u0003}Y\t\u00071\fB\u0003\u0000Y\t\u00071\f\u0002\u0004\u0002\u00061\u0012\ra\u0017\u0003\u0007\u0003\u0017a#\u0019A.\u0005\r\u0005EAF1\u0001\\\t\u0019\t9\u0002\fb\u00017\u00121\u0011Q\u0004\u0017C\u0002m#a!a\t-\u0005\u0004YFABA\u0015Y\t\u00071\f\u0002\u0004\u000201\u0012\raW\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00135+!\u001aYba\b\u0004\"\r\r2QEB\u0014\u0007S\u0019Yc!\f\u00040\rE21GB\u001b\u0007o\u0019Ida\u000f\u0004>\r}2\u0011IB\"+\t\u0019iBK\u0002i\u0005\u0007#QAW\u0017C\u0002m#Q\u0001Z\u0017C\u0002m#QaZ\u0017C\u0002m#QA[\u0017C\u0002m#Q!\\\u0017C\u0002m#Q\u0001]\u0017C\u0002m#Qa]\u0017C\u0002m#QA^\u0017C\u0002m#Q!_\u0017C\u0002m#Q\u0001`\u0017C\u0002m#Qa`\u0017C\u0002m#a!!\u0002.\u0005\u0004YFABA\u0006[\t\u00071\f\u0002\u0004\u0002\u00125\u0012\ra\u0017\u0003\u0007\u0003/i#\u0019A.\u0005\r\u0005uQF1\u0001\\\t\u0019\t\u0019#\fb\u00017\u00121\u0011\u0011F\u0017C\u0002m#a!a\f.\u0005\u0004Y\u0016AD2paf$C-\u001a4bk2$H%N\u000b)\u0007\u0013\u001aiea\u0014\u0004R\rM3QKB,\u00073\u001aYf!\u0018\u0004`\r\u000541MB3\u0007O\u001aIga\u001b\u0004n\r=4\u0011O\u000b\u0003\u0007\u0017R3a\u001bBB\t\u0015QfF1\u0001\\\t\u0015!gF1\u0001\\\t\u00159gF1\u0001\\\t\u0015QgF1\u0001\\\t\u0015igF1\u0001\\\t\u0015\u0001hF1\u0001\\\t\u0015\u0019hF1\u0001\\\t\u00151hF1\u0001\\\t\u0015IhF1\u0001\\\t\u0015ahF1\u0001\\\t\u0015yhF1\u0001\\\t\u0019\t)A\fb\u00017\u00121\u00111\u0002\u0018C\u0002m#a!!\u0005/\u0005\u0004YFABA\f]\t\u00071\f\u0002\u0004\u0002\u001e9\u0012\ra\u0017\u0003\u0007\u0003Gq#\u0019A.\u0005\r\u0005%bF1\u0001\\\t\u0019\tyC\fb\u00017\u0006q1m\u001c9zI\u0011,g-Y;mi\u00122T\u0003KB<\u0007w\u001aiha \u0004\u0002\u000e\r5QQBD\u0007\u0013\u001bYi!$\u0004\u0010\u000eE51SBK\u0007/\u001bIja'\u0004\u001e\u000e}UCAB=U\rq'1\u0011\u0003\u00065>\u0012\ra\u0017\u0003\u0006I>\u0012\ra\u0017\u0003\u0006O>\u0012\ra\u0017\u0003\u0006U>\u0012\ra\u0017\u0003\u0006[>\u0012\ra\u0017\u0003\u0006a>\u0012\ra\u0017\u0003\u0006g>\u0012\ra\u0017\u0003\u0006m>\u0012\ra\u0017\u0003\u0006s>\u0012\ra\u0017\u0003\u0006y>\u0012\ra\u0017\u0003\u0006\u007f>\u0012\ra\u0017\u0003\u0007\u0003\u000by#\u0019A.\u0005\r\u0005-qF1\u0001\\\t\u0019\t\tb\fb\u00017\u00121\u0011qC\u0018C\u0002m#a!!\b0\u0005\u0004YFABA\u0012_\t\u00071\f\u0002\u0004\u0002*=\u0012\ra\u0017\u0003\u0007\u0003_y#\u0019A.\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%oUA3QUBU\u0007W\u001bika,\u00042\u000eM6QWB\\\u0007s\u001bYl!0\u0004@\u000e\u000571YBc\u0007\u000f\u001cIma3\u0004NV\u00111q\u0015\u0016\u0004c\n\rE!\u0002.1\u0005\u0004YF!\u000231\u0005\u0004YF!B41\u0005\u0004YF!\u000261\u0005\u0004YF!B71\u0005\u0004YF!\u000291\u0005\u0004YF!B:1\u0005\u0004YF!\u0002<1\u0005\u0004YF!B=1\u0005\u0004YF!\u0002?1\u0005\u0004YF!B@1\u0005\u0004YFABA\u0003a\t\u00071\f\u0002\u0004\u0002\fA\u0012\ra\u0017\u0003\u0007\u0003#\u0001$\u0019A.\u0005\r\u0005]\u0001G1\u0001\\\t\u0019\ti\u0002\rb\u00017\u00121\u00111\u0005\u0019C\u0002m#a!!\u000b1\u0005\u0004YFABA\u0018a\t\u00071,\u0001\bd_BLH\u0005Z3gCVdG\u000f\n\u001d\u0016Q\rM7q[Bm\u00077\u001cina8\u0004b\u000e\r8Q]Bt\u0007S\u001cYo!<\u0004p\u000eE81_B{\u0007o\u001cIpa?\u0016\u0005\rU'f\u0001;\u0003\u0004\u0012)!,\rb\u00017\u0012)A-\rb\u00017\u0012)q-\rb\u00017\u0012)!.\rb\u00017\u0012)Q.\rb\u00017\u0012)\u0001/\rb\u00017\u0012)1/\rb\u00017\u0012)a/\rb\u00017\u0012)\u00110\rb\u00017\u0012)A0\rb\u00017\u0012)q0\rb\u00017\u00121\u0011QA\u0019C\u0002m#a!a\u00032\u0005\u0004YFABA\tc\t\u00071\f\u0002\u0004\u0002\u0018E\u0012\ra\u0017\u0003\u0007\u0003;\t$\u0019A.\u0005\r\u0005\r\u0012G1\u0001\\\t\u0019\tI#\rb\u00017\u00121\u0011qF\u0019C\u0002m\u000babY8qs\u0012\"WMZ1vYR$\u0013(\u0006\u0015\u0005\u0002\u0011\u0015Aq\u0001C\u0005\t\u0017!i\u0001b\u0004\u0005\u0012\u0011MAQ\u0003C\f\t3!Y\u0002\"\b\u0005 \u0011\u0005B1\u0005C\u0013\tO!I#\u0006\u0002\u0005\u0004)\u001aqOa!\u0005\u000bi\u0013$\u0019A.\u0005\u000b\u0011\u0014$\u0019A.\u0005\u000b\u001d\u0014$\u0019A.\u0005\u000b)\u0014$\u0019A.\u0005\u000b5\u0014$\u0019A.\u0005\u000bA\u0014$\u0019A.\u0005\u000bM\u0014$\u0019A.\u0005\u000bY\u0014$\u0019A.\u0005\u000be\u0014$\u0019A.\u0005\u000bq\u0014$\u0019A.\u0005\u000b}\u0014$\u0019A.\u0005\r\u0005\u0015!G1\u0001\\\t\u0019\tYA\rb\u00017\u00121\u0011\u0011\u0003\u001aC\u0002m#a!a\u00063\u0005\u0004YFABA\u000fe\t\u00071\f\u0002\u0004\u0002$I\u0012\ra\u0017\u0003\u0007\u0003S\u0011$\u0019A.\u0005\r\u0005=\"G1\u0001\\\u0003=\u0019w\u000e]=%I\u00164\u0017-\u001e7uIE\u0002T\u0003\u000bC\u0018\tg!)\u0004b\u000e\u0005:\u0011mBQ\bC \t\u0003\"\u0019\u0005\"\u0012\u0005H\u0011%C1\nC'\t\u001f\"\t\u0006b\u0015\u0005V\u0011]SC\u0001C\u0019U\rQ(1\u0011\u0003\u00065N\u0012\ra\u0017\u0003\u0006IN\u0012\ra\u0017\u0003\u0006ON\u0012\ra\u0017\u0003\u0006UN\u0012\ra\u0017\u0003\u0006[N\u0012\ra\u0017\u0003\u0006aN\u0012\ra\u0017\u0003\u0006gN\u0012\ra\u0017\u0003\u0006mN\u0012\ra\u0017\u0003\u0006sN\u0012\ra\u0017\u0003\u0006yN\u0012\ra\u0017\u0003\u0006\u007fN\u0012\ra\u0017\u0003\u0007\u0003\u000b\u0019$\u0019A.\u0005\r\u0005-1G1\u0001\\\t\u0019\t\tb\rb\u00017\u00121\u0011qC\u001aC\u0002m#a!!\b4\u0005\u0004YFABA\u0012g\t\u00071\f\u0002\u0004\u0002*M\u0012\ra\u0017\u0003\u0007\u0003_\u0019$\u0019A.\u0002\u001f\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%cE*\u0002\u0006\"\u0018\u0005b\u0011\rDQ\rC4\tS\"Y\u0007\"\u001c\u0005p\u0011ED1\u000fC;\to\"I\bb\u001f\u0005~\u0011}D\u0011\u0011CB\t\u000b+\"\u0001b\u0018+\u0007u\u0014\u0019\tB\u0003[i\t\u00071\fB\u0003ei\t\u00071\fB\u0003hi\t\u00071\fB\u0003ki\t\u00071\fB\u0003ni\t\u00071\fB\u0003qi\t\u00071\fB\u0003ti\t\u00071\fB\u0003wi\t\u00071\fB\u0003zi\t\u00071\fB\u0003}i\t\u00071\fB\u0003\u0000i\t\u00071\f\u0002\u0004\u0002\u0006Q\u0012\ra\u0017\u0003\u0007\u0003\u0017!$\u0019A.\u0005\r\u0005EAG1\u0001\\\t\u0019\t9\u0002\u000eb\u00017\u00121\u0011Q\u0004\u001bC\u0002m#a!a\t5\u0005\u0004YFABA\u0015i\t\u00071\f\u0002\u0004\u00020Q\u0012\raW\u0001\u0010G>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00132eUAC1\u0012CH\t##\u0019\n\"&\u0005\u0018\u0012eE1\u0014CO\t?#\t\u000bb)\u0005&\u0012\u001dF\u0011\u0016CV\t[#y\u000b\"-\u00054V\u0011AQ\u0012\u0016\u0005\u0003\u0003\u0011\u0019\tB\u0003[k\t\u00071\fB\u0003ek\t\u00071\fB\u0003hk\t\u00071\fB\u0003kk\t\u00071\fB\u0003nk\t\u00071\fB\u0003qk\t\u00071\fB\u0003tk\t\u00071\fB\u0003wk\t\u00071\fB\u0003zk\t\u00071\fB\u0003}k\t\u00071\fB\u0003\u0000k\t\u00071\f\u0002\u0004\u0002\u0006U\u0012\ra\u0017\u0003\u0007\u0003\u0017)$\u0019A.\u0005\r\u0005EQG1\u0001\\\t\u0019\t9\"\u000eb\u00017\u00121\u0011QD\u001bC\u0002m#a!a\t6\u0005\u0004YFABA\u0015k\t\u00071\f\u0002\u0004\u00020U\u0012\raW\u0001\u0010G>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00132gUAC\u0011\u0018C_\t\u007f#\t\rb1\u0005F\u0012\u001dG\u0011\u001aCf\t\u001b$y\r\"5\u0005T\u0012UGq\u001bCm\t7$i\u000eb8\u0005bV\u0011A1\u0018\u0016\u0005\u0003\u000f\u0011\u0019\tB\u0003[m\t\u00071\fB\u0003em\t\u00071\fB\u0003hm\t\u00071\fB\u0003km\t\u00071\fB\u0003nm\t\u00071\fB\u0003qm\t\u00071\fB\u0003tm\t\u00071\fB\u0003wm\t\u00071\fB\u0003zm\t\u00071\fB\u0003}m\t\u00071\fB\u0003\u0000m\t\u00071\f\u0002\u0004\u0002\u0006Y\u0012\ra\u0017\u0003\u0007\u0003\u00171$\u0019A.\u0005\r\u0005EaG1\u0001\\\t\u0019\t9B\u000eb\u00017\u00121\u0011Q\u0004\u001cC\u0002m#a!a\t7\u0005\u0004YFABA\u0015m\t\u00071\f\u0002\u0004\u00020Y\u0012\raW\u0001\u0010G>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00132iUACq\u001dCv\t[$y\u000f\"=\u0005t\u0012UHq\u001fC}\tw$i\u0010b@\u0006\u0002\u0015\rQQAC\u0004\u000b\u0013)Y!\"\u0004\u0006\u0010U\u0011A\u0011\u001e\u0016\u0005\u0003\u001b\u0011\u0019\tB\u0003[o\t\u00071\fB\u0003eo\t\u00071\fB\u0003ho\t\u00071\fB\u0003ko\t\u00071\fB\u0003no\t\u00071\fB\u0003qo\t\u00071\fB\u0003to\t\u00071\fB\u0003wo\t\u00071\fB\u0003zo\t\u00071\fB\u0003}o\t\u00071\fB\u0003\u0000o\t\u00071\f\u0002\u0004\u0002\u0006]\u0012\ra\u0017\u0003\u0007\u0003\u00179$\u0019A.\u0005\r\u0005EqG1\u0001\\\t\u0019\t9b\u000eb\u00017\u00121\u0011QD\u001cC\u0002m#a!a\t8\u0005\u0004YFABA\u0015o\t\u00071\f\u0002\u0004\u00020]\u0012\raW\u0001\u0010G>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00132kUASQCC\r\u000b7)i\"b\b\u0006\"\u0015\rRQEC\u0014\u000bS)Y#\"\f\u00060\u0015ER1GC\u001b\u000bo)I$b\u000f\u0006>U\u0011Qq\u0003\u0016\u0005\u0003'\u0011\u0019\tB\u0003[q\t\u00071\fB\u0003eq\t\u00071\fB\u0003hq\t\u00071\fB\u0003kq\t\u00071\fB\u0003nq\t\u00071\fB\u0003qq\t\u00071\fB\u0003tq\t\u00071\fB\u0003wq\t\u00071\fB\u0003zq\t\u00071\fB\u0003}q\t\u00071\fB\u0003\u0000q\t\u00071\f\u0002\u0004\u0002\u0006a\u0012\ra\u0017\u0003\u0007\u0003\u0017A$\u0019A.\u0005\r\u0005E\u0001H1\u0001\\\t\u0019\t9\u0002\u000fb\u00017\u00121\u0011Q\u0004\u001dC\u0002m#a!a\t9\u0005\u0004YFABA\u0015q\t\u00071\f\u0002\u0004\u00020a\u0012\raW\u0001\u0010G>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00132mUAS1IC$\u000b\u0013*Y%\"\u0014\u0006P\u0015ES1KC+\u000b/*I&b\u0017\u0006^\u0015}S\u0011MC2\u000bK*9'\"\u001b\u0006lU\u0011QQ\t\u0016\u0005\u00033\u0011\u0019\tB\u0003[s\t\u00071\fB\u0003es\t\u00071\fB\u0003hs\t\u00071\fB\u0003ks\t\u00071\fB\u0003ns\t\u00071\fB\u0003qs\t\u00071\fB\u0003ts\t\u00071\fB\u0003ws\t\u00071\fB\u0003zs\t\u00071\fB\u0003}s\t\u00071\fB\u0003\u0000s\t\u00071\f\u0002\u0004\u0002\u0006e\u0012\ra\u0017\u0003\u0007\u0003\u0017I$\u0019A.\u0005\r\u0005E\u0011H1\u0001\\\t\u0019\t9\"\u000fb\u00017\u00121\u0011QD\u001dC\u0002m#a!a\t:\u0005\u0004YFABA\u0015s\t\u00071\f\u0002\u0004\u00020e\u0012\raW\u0001\u0010G>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00132oUAS\u0011OC;\u000bo*I(b\u001f\u0006~\u0015}T\u0011QCB\u000b\u000b+9)\"#\u0006\f\u00165UqRCI\u000b'+)*b&\u0006\u001aV\u0011Q1\u000f\u0016\u0005\u0003?\u0011\u0019\tB\u0003[u\t\u00071\fB\u0003eu\t\u00071\fB\u0003hu\t\u00071\fB\u0003ku\t\u00071\fB\u0003nu\t\u00071\fB\u0003qu\t\u00071\fB\u0003tu\t\u00071\fB\u0003wu\t\u00071\fB\u0003zu\t\u00071\fB\u0003}u\t\u00071\fB\u0003\u0000u\t\u00071\f\u0002\u0004\u0002\u0006i\u0012\ra\u0017\u0003\u0007\u0003\u0017Q$\u0019A.\u0005\r\u0005E!H1\u0001\\\t\u0019\t9B\u000fb\u00017\u00121\u0011Q\u0004\u001eC\u0002m#a!a\t;\u0005\u0004YFABA\u0015u\t\u00071\f\u0002\u0004\u00020i\u0012\raW\u0001\u0010G>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00132qUASqTCR\u000bK+9+\"+\u0006,\u00165VqVCY\u000bg+),b.\u0006:\u0016mVQXC`\u000b\u0003,\u0019-\"2\u0006HV\u0011Q\u0011\u0015\u0016\u0005\u0003K\u0011\u0019\tB\u0003[w\t\u00071\fB\u0003ew\t\u00071\fB\u0003hw\t\u00071\fB\u0003kw\t\u00071\fB\u0003nw\t\u00071\fB\u0003qw\t\u00071\fB\u0003tw\t\u00071\fB\u0003ww\t\u00071\fB\u0003zw\t\u00071\fB\u0003}w\t\u00071\fB\u0003\u0000w\t\u00071\f\u0002\u0004\u0002\u0006m\u0012\ra\u0017\u0003\u0007\u0003\u0017Y$\u0019A.\u0005\r\u0005E1H1\u0001\\\t\u0019\t9b\u000fb\u00017\u00121\u0011QD\u001eC\u0002m#a!a\t<\u0005\u0004YFABA\u0015w\t\u00071\f\u0002\u0004\u00020m\u0012\raW\u0001\u0010G>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00132sUASQZCi\u000b',).b6\u0006Z\u0016mWQ\\Cp\u000bC,\u0019/\":\u0006h\u0016%X1^Cw\u000b_,\t0b=\u0006vV\u0011Qq\u001a\u0016\u0005\u0003W\u0011\u0019\tB\u0003[y\t\u00071\fB\u0003ey\t\u00071\fB\u0003hy\t\u00071\fB\u0003ky\t\u00071\fB\u0003ny\t\u00071\fB\u0003qy\t\u00071\fB\u0003ty\t\u00071\fB\u0003wy\t\u00071\fB\u0003zy\t\u00071\fB\u0003}y\t\u00071\fB\u0003\u0000y\t\u00071\f\u0002\u0004\u0002\u0006q\u0012\ra\u0017\u0003\u0007\u0003\u0017a$\u0019A.\u0005\r\u0005EAH1\u0001\\\t\u0019\t9\u0002\u0010b\u00017\u00121\u0011Q\u0004\u001fC\u0002m#a!a\t=\u0005\u0004YFABA\u0015y\t\u00071\f\u0002\u0004\u00020q\u0012\raW\u0001\u000eaJ|G-^2u!J,g-\u001b=\u0016\u0005\u0015m\b\u0003BC\u007f\r\u000fi!!b@\u000b\t\u0019\u0005a1A\u0001\u0005Y\u0006twM\u0003\u0002\u0007\u0006\u0005!!.\u0019<b\u0013\u0011\ti0b@\u0002\u001fA\u0014x\u000eZ;di&#XM]1u_J,\"A\"\u0004\u0011\u000b\u0019=aQC0\u000e\u0005\u0019E!b\u0001D\n\u0017\u0006Q1m\u001c7mK\u000e$\u0018n\u001c8\n\t\u0019]a\u0011\u0003\u0002\t\u0013R,'/\u0019;pe\u0006A1-\u00198FcV\fG\u000e\u0006\u0003\u0007\u001e\u0019\r\u0002cA)\u0007 %\u0019a\u0011E&\u0003\u000f\t{w\u000e\\3b]\"AaQE \u0002\u0002\u0003\u0007q,A\u0002yIE\n!\u0003\u001d:pIV\u001cG/\u00127f[\u0016tGOT1nKR!Q1 D\u0016\u0011%1)\u0003QA\u0001\u0002\u00041i\u0003E\u0002R\r_I1A\"\rL\u0005\rIe\u000e^\u0001\tQ\u0006\u001c\bnQ8eKR\u0011aQF\u0001\u0007KF,\u0018\r\\:\u0015\t\u0019ua1\b\u0005\t\rK\u0011\u0015\u0011!a\u0001?\u00069A+\u001e9mKFJ\u0004CA)E'\u0011!\u0005Kb\u0011\u0011\t\u0019\u0015c1J\u0007\u0003\r\u000fRAA\"\u0013\u0007\u0004\u0005\u0011\u0011n\\\u0005\u0005\u0003\u001729\u0005\u0006\u0002\u0007@Q\u0011Q1`\u0001\u0006CB\u0004H._\u000b)\r+2YFb\u0018\u0007d\u0019\u001dd1\u000eD8\rg29Hb\u001f\u0007\u0000\u0019\req\u0011DF\r\u001f3\u0019Jb&\u0007\u001c\u001a}e1\u0015\u000b)\r/2)Kb*\u0007*\u001a-fQ\u0016DX\rc3\u0019L\".\u00078\u001aef1\u0018D_\r\u007f3\tMb1\u0007F\u001a\u001dg\u0011\u001a\t)#\u00021IF\"\u0018\u0007b\u0019\u0015d\u0011\u000eD7\rc2)H\"\u001f\u0007~\u0019\u0005eQ\u0011DE\r\u001b3\tJ\"&\u0007\u001a\u001aue\u0011\u0015\t\u00041\u001amC!\u0002.H\u0005\u0004Y\u0006c\u0001-\u0007`\u0011)Am\u0012b\u00017B\u0019\u0001Lb\u0019\u0005\u000b\u001d<%\u0019A.\u0011\u0007a39\u0007B\u0003k\u000f\n\u00071\fE\u0002Y\rW\"Q!\\$C\u0002m\u00032\u0001\u0017D8\t\u0015\u0001xI1\u0001\\!\rAf1\u000f\u0003\u0006g\u001e\u0013\ra\u0017\t\u00041\u001a]D!\u0002<H\u0005\u0004Y\u0006c\u0001-\u0007|\u0011)\u0011p\u0012b\u00017B\u0019\u0001Lb \u0005\u000bq<%\u0019A.\u0011\u0007a3\u0019\tB\u0003\u0000\u000f\n\u00071\fE\u0002Y\r\u000f#a!!\u0002H\u0005\u0004Y\u0006c\u0001-\u0007\f\u00121\u00111B$C\u0002m\u00032\u0001\u0017DH\t\u0019\t\tb\u0012b\u00017B\u0019\u0001Lb%\u0005\r\u0005]qI1\u0001\\!\rAfq\u0013\u0003\u0007\u0003;9%\u0019A.\u0011\u0007a3Y\n\u0002\u0004\u0002$\u001d\u0013\ra\u0017\t\u00041\u001a}EABA\u0015\u000f\n\u00071\fE\u0002Y\rG#a!a\fH\u0005\u0004Y\u0006bBA(\u000f\u0002\u0007a\u0011\f\u0005\b\u0003+:\u0005\u0019\u0001D/\u0011\u001d\tYf\u0012a\u0001\rCBq!!\u0019H\u0001\u00041)\u0007C\u0004\u0002h\u001d\u0003\rA\"\u001b\t\u000f\u00055t\t1\u0001\u0007n!9\u00111O$A\u0002\u0019E\u0004bBA=\u000f\u0002\u0007aQ\u000f\u0005\b\u0003\u007f:\u0005\u0019\u0001D=\u0011\u001d\t)i\u0012a\u0001\r{Bq!a#H\u0001\u00041\t\tC\u0004\u0002\u0012\u001e\u0003\rA\"\"\t\u000f\u0005]u\t1\u0001\u0007\n\"9\u0011QT$A\u0002\u00195\u0005bBAR\u000f\u0002\u0007a\u0011\u0013\u0005\b\u0003S;\u0005\u0019\u0001DK\u0011\u001d\tyk\u0012a\u0001\r3Cq!!.H\u0001\u00041i\nC\u0004\u0002<\u001e\u0003\rA\")\u0002\u000fUt\u0017\r\u001d9msVAcq\u001aDn\r?4\u0019Ob:\u0007l\u001a=h1\u001fD|\rw4ypb\u0001\b\b\u001d-qqBD\n\u000f/9Ybb\b\b$Q!a\u0011[D\u0013!\u0015\tf1\u001bDl\u0013\r1)n\u0013\u0002\u0007\u001fB$\u0018n\u001c8\u0011QE\u0003a\u0011\u001cDo\rC4)O\";\u0007n\u001aEhQ\u001fD}\r{<\ta\"\u0002\b\n\u001d5q\u0011CD\u000b\u000f39ib\"\t\u0011\u0007a3Y\u000eB\u0003[\u0011\n\u00071\fE\u0002Y\r?$Q\u0001\u001a%C\u0002m\u00032\u0001\u0017Dr\t\u00159\u0007J1\u0001\\!\rAfq\u001d\u0003\u0006U\"\u0013\ra\u0017\t\u00041\u001a-H!B7I\u0005\u0004Y\u0006c\u0001-\u0007p\u0012)\u0001\u000f\u0013b\u00017B\u0019\u0001Lb=\u0005\u000bMD%\u0019A.\u0011\u0007a39\u0010B\u0003w\u0011\n\u00071\fE\u0002Y\rw$Q!\u001f%C\u0002m\u00032\u0001\u0017D\u0000\t\u0015a\bJ1\u0001\\!\rAv1\u0001\u0003\u0006\u007f\"\u0013\ra\u0017\t\u00041\u001e\u001dAABA\u0003\u0011\n\u00071\fE\u0002Y\u000f\u0017!a!a\u0003I\u0005\u0004Y\u0006c\u0001-\b\u0010\u00111\u0011\u0011\u0003%C\u0002m\u00032\u0001WD\n\t\u0019\t9\u0002\u0013b\u00017B\u0019\u0001lb\u0006\u0005\r\u0005u\u0001J1\u0001\\!\rAv1\u0004\u0003\u0007\u0003GA%\u0019A.\u0011\u0007a;y\u0002\u0002\u0004\u0002*!\u0013\ra\u0017\t\u00041\u001e\rBABA\u0018\u0011\n\u00071\fC\u0005\b(!\u000b\t\u00111\u0001\u0007X\u0006\u0019\u0001\u0010\n\u0019\u0002\u0019]\u0014\u0018\u000e^3SKBd\u0017mY3\u0015\u0005\u001d5\u0002\u0003BC\u007f\u000f_IAa\"\r\u0006\u0000\n1qJ\u00196fGR\u0004")
public final class Tuple19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>
implements Product19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>,
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
    private final T18 _18;
    private final T19 _19;

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> Option<Tuple19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>> unapply(Tuple19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> x$0) {
        return Tuple19$.MODULE$.unapply(x$0);
    }

    public static <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> Tuple19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> apply(T1 _1, T2 _2, T3 _3, T4 _4, T5 _5, T6 _6, T7 _7, T8 _8, T9 _9, T10 _10, T11 _11, T12 _12, T13 _13, T14 _14, T15 _15, T16 _16, T17 _17, T18 _18, T19 _19) {
        return new Tuple19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>(_1, _2, _3, _4, _5, _6, _7, _8, _9, _10, _11, _12, _13, _14, _15, _16, _17, _18, _19);
    }

    @Override
    public int productArity() {
        return Product19.productArity$(this);
    }

    @Override
    public Object productElement(int n) throws IndexOutOfBoundsException {
        return Product19.productElement$(this, n);
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

    @Override
    public T18 _18() {
        return this._18;
    }

    @Override
    public T19 _19() {
        return this._19;
    }

    public String toString() {
        return new StringBuilder(20).append("(").append(this._1()).append(",").append(this._2()).append(",").append(this._3()).append(",").append(this._4()).append(",").append(this._5()).append(",").append(this._6()).append(",").append(this._7()).append(",").append(this._8()).append(",").append(this._9()).append(",").append(this._10()).append(",").append(this._11()).append(",").append(this._12()).append(",").append(this._13()).append(",").append(this._14()).append(",").append(this._15()).append(",").append(this._16()).append(",").append(this._17()).append(",").append(this._18()).append(",").append(this._19()).append(")").toString();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> Tuple19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> copy(T1 _1, T2 _2, T3 _3, T4 _4, T5 _5, T6 _6, T7 _7, T8 _8, T9 _9, T10 _10, T11 _11, T12 _12, T13 _13, T14 _14, T15 _15, T16 _16, T17 _17, T18 _18, T19 _19) {
        return new Tuple19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>(_1, _2, _3, _4, _5, _6, _7, _8, _9, _10, _11, _12, _13, _14, _15, _16, _17, _18, _19);
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> T1 copy$default$1() {
        return this._1();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> T10 copy$default$10() {
        return this._10();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> T11 copy$default$11() {
        return this._11();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> T12 copy$default$12() {
        return this._12();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> T13 copy$default$13() {
        return this._13();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> T14 copy$default$14() {
        return this._14();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> T15 copy$default$15() {
        return this._15();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> T16 copy$default$16() {
        return this._16();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> T17 copy$default$17() {
        return this._17();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> T18 copy$default$18() {
        return this._18();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> T19 copy$default$19() {
        return this._19();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> T2 copy$default$2() {
        return this._2();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> T3 copy$default$3() {
        return this._3();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> T4 copy$default$4() {
        return this._4();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> T5 copy$default$5() {
        return this._5();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> T6 copy$default$6() {
        return this._6();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> T7 copy$default$7() {
        return this._7();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> T8 copy$default$8() {
        return this._8();
    }

    public <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19> T9 copy$default$9() {
        return this._9();
    }

    @Override
    public String productPrefix() {
        return "Tuple19";
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
        return x$1 instanceof Tuple19;
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
            case 17: {
                return "_18";
            }
            case 18: {
                return "_19";
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
                if (!(x$1 instanceof Tuple19)) break block3;
                Tuple19 tuple19 = (Tuple19)x$1;
                if (!BoxesRunTime.equals(this._1(), tuple19._1()) || !BoxesRunTime.equals(this._2(), tuple19._2()) || !BoxesRunTime.equals(this._3(), tuple19._3()) || !BoxesRunTime.equals(this._4(), tuple19._4()) || !BoxesRunTime.equals(this._5(), tuple19._5()) || !BoxesRunTime.equals(this._6(), tuple19._6()) || !BoxesRunTime.equals(this._7(), tuple19._7()) || !BoxesRunTime.equals(this._8(), tuple19._8()) || !BoxesRunTime.equals(this._9(), tuple19._9()) || !BoxesRunTime.equals(this._10(), tuple19._10()) || !BoxesRunTime.equals(this._11(), tuple19._11()) || !BoxesRunTime.equals(this._12(), tuple19._12()) || !BoxesRunTime.equals(this._13(), tuple19._13()) || !BoxesRunTime.equals(this._14(), tuple19._14()) || !BoxesRunTime.equals(this._15(), tuple19._15()) || !BoxesRunTime.equals(this._16(), tuple19._16()) || !BoxesRunTime.equals(this._17(), tuple19._17()) || !BoxesRunTime.equals(this._18(), tuple19._18()) || !BoxesRunTime.equals(this._19(), tuple19._19())) break block3;
            }
            return true;
        }
        return false;
    }

    public Tuple19(T1 _1, T2 _2, T3 _3, T4 _4, T5 _5, T6 _6, T7 _7, T8 _8, T9 _9, T10 _10, T11 _11, T12 _12, T13 _13, T14 _14, T15 _15, T16 _16, T17 _17, T18 _18, T19 _19) {
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
        this._18 = _18;
        this._19 = _19;
    }
}

