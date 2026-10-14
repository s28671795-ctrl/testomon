/*
 * Decompiled with CFR 0.152.
 */
package akka.io;

import akka.io.Inet;
import akka.io.Inet$SO$ReceiveBufferSize$;
import akka.io.Inet$SO$ReuseAddress$;
import akka.io.Inet$SO$SendBufferSize$;
import akka.io.Inet$SO$TrafficClass$;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.DatagramChannel;
import scala.reflect.ScalaSignature;

@ScalaSignature(bytes="\u0006\u0005\r}w\u0001CA\u000b\u0003/A\t!!\t\u0007\u0011\u0005\u0015\u0012q\u0003E\u0001\u0003OAq!!\u000e\u0002\t\u0003\t9DB\u0005\u0002:\u0005\u0001\n1!\u0001\u0002<!9\u0011QH\u0002\u0005\u0002\u0005}\u0002bBA$\u0007\u0011\u0005\u0011\u0011\n\u0005\b\u0003[\u001aA\u0011AA8\u0011\u001d\tih\u0001C\u0001\u0003\u007fBq!!$\u0004\t\u0003\tyIB\u0004\u0002\u0016\u0006\t\t!a&\t\u000f\u0005U\u0012\u0002\"\u0001\u0002\u001e\u001aI\u0011\u0011U\u0001\u0011\u0002\u0007\u0005\u00111\u0015\u0005\b\u0003{YA\u0011AA \u0011\u001d\t)k\u0003C\u0001\u0003OCq!!*\f\t\u0003\ti\u000bC\u0004\u0002\u000e.!\t!a-\u0007\u000f\u0005e\u0016!!\u0001\u0002<\"9\u0011Q\u0007\t\u0005\u0002\u0005}fABAb\u0003\u0001\t)\rC\u0004\u00026I!\t!a2\t\u000f\u0005-'\u0003\"\u0001\u0002N\u001e9!\u0011A\u0001\t\u0002\t\raaBAb\u0003!\u0005!Q\u0001\u0005\b\u0003k1B\u0011\u0001B\u0004\u0011%\u0011IA\u0006b\u0001\n\u0003\u0011Y\u0001\u0003\u0005\u0003\u000eY\u0001\u000b\u0011BAe\u0011\u001d\u0011yA\u0006C\u0001\u0003\u000f<qA!\u0005\u0002\u0011\u0003\u0011\u0019BB\u0004\u0003\u0016\u0005A\tAa\u0006\t\u000f\u0005UB\u0004\"\u0001\u0003\u001a\u00191!1\u0004\u000fC\u0005;A!Ba\u000b\u001f\u0005+\u0007I\u0011\u0001B\u0017\u0011)\u0011)D\bB\tB\u0003%!q\u0006\u0005\b\u0003kqB\u0011\u0001B\u001c\u0011\u001d\tiG\bC!\u0005\u007fAq!a\u0012\u001f\t\u0003\u0012\u0019\u0005C\u0004\u0002~y!\tEa\u0012\t\u0013\t-c$!A\u0005\u0002\t5\u0003\"\u0003B)=E\u0005I\u0011\u0001B*\u0011%\u0011IGHA\u0001\n\u0003\u0012Y\u0007C\u0005\u0003zy\t\t\u0011\"\u0001\u0003.!I!1\u0010\u0010\u0002\u0002\u0013\u0005!Q\u0010\u0005\n\u0005\u0013s\u0012\u0011!C!\u0005\u0017C\u0011B!'\u001f\u0003\u0003%\tAa'\t\u0013\t\u0015f$!A\u0005B\t\u001d\u0006\"\u0003BV=\u0005\u0005I\u0011\tBW\u0011%\u0011yKHA\u0001\n\u0003\u0012\t\fC\u0005\u00034z\t\t\u0011\"\u0011\u00036\u001eI!\u0011\u0018\u000f\u0002\u0002#\u0005!1\u0018\u0004\n\u00057a\u0012\u0011!E\u0001\u0005{Cq!!\u000e2\t\u0003\u0011\u0019\u000eC\u0005\u00030F\n\t\u0011\"\u0012\u00032\"I!qB\u0019\u0002\u0002\u0013\u0005%Q\u001b\u0005\n\u00053\f\u0014\u0011!CA\u00057D\u0011Ba:2\u0003\u0003%IA!;\u0007\r\tEHD\u0011Bz\u0011)\u0011)p\u000eBK\u0002\u0013\u0005!q\u001f\u0005\u000b\u0005s<$\u0011#Q\u0001\n\tu\u0005bBA\u001bo\u0011\u0005!1 \u0005\b\u0003[:D\u0011IB\u0001\u0011\u001d\t9e\u000eC!\u0007\u000bAq!! 8\t\u0003\u001aI\u0001C\u0005\u0003L]\n\t\u0011\"\u0001\u0004\u000e!I!\u0011K\u001c\u0012\u0002\u0013\u00051\u0011\u0003\u0005\n\u0005S:\u0014\u0011!C!\u0005WB\u0011B!\u001f8\u0003\u0003%\tA!\f\t\u0013\tmt'!A\u0005\u0002\rU\u0001\"\u0003BEo\u0005\u0005I\u0011\tBF\u0011%\u0011IjNA\u0001\n\u0003\u0019I\u0002C\u0005\u0003&^\n\t\u0011\"\u0011\u0004\u001e!I!1V\u001c\u0002\u0002\u0013\u0005#Q\u0016\u0005\n\u0005_;\u0014\u0011!C!\u0005cC\u0011Ba-8\u0003\u0003%\te!\t\b\u0013\r\u0015B$!A\t\u0002\r\u001db!\u0003By9\u0005\u0005\t\u0012AB\u0015\u0011\u001d\t)D\u0013C\u0001\u0007[A\u0011Ba,K\u0003\u0003%)E!-\t\u0013\t=!*!A\u0005\u0002\u000e=\u0002\"\u0003Bm\u0015\u0006\u0005I\u0011QB\u001a\u0011%\u00119OSA\u0001\n\u0013\u0011IO\u0002\u0004\u0004:q\u001151\b\u0005\u000b\u0005W\u0001&Q3A\u0005\u0002\t5\u0002B\u0003B\u001b!\nE\t\u0015!\u0003\u00030!9\u0011Q\u0007)\u0005\u0002\ru\u0002bBAG!\u0012\u000531\t\u0005\n\u0005\u0017\u0002\u0016\u0011!C\u0001\u0007\u000fB\u0011B!\u0015Q#\u0003%\tAa\u0015\t\u0013\t%\u0004+!A\u0005B\t-\u0004\"\u0003B=!\u0006\u0005I\u0011\u0001B\u0017\u0011%\u0011Y\bUA\u0001\n\u0003\u0019Y\u0005C\u0005\u0003\nB\u000b\t\u0011\"\u0011\u0003\f\"I!\u0011\u0014)\u0002\u0002\u0013\u00051q\n\u0005\n\u0005K\u0003\u0016\u0011!C!\u0007'B\u0011Ba+Q\u0003\u0003%\tE!,\t\u0013\t=\u0006+!A\u0005B\tE\u0006\"\u0003BZ!\u0006\u0005I\u0011IB,\u000f%\u0019Y\u0006HA\u0001\u0012\u0003\u0019iFB\u0005\u0004:q\t\t\u0011#\u0001\u0004`!9\u0011QG1\u0005\u0002\r\r\u0004\"\u0003BXC\u0006\u0005IQ\tBY\u0011%\u0011y!YA\u0001\n\u0003\u001b)\u0007C\u0005\u0003Z\u0006\f\t\u0011\"!\u0004j!I!q]1\u0002\u0002\u0013%!\u0011\u001e\u0004\u0007\u0007[b\"ia\u001c\t\u0015\rEtM!f\u0001\n\u0003\u0011i\u0003\u0003\u0006\u0004t\u001d\u0014\t\u0012)A\u0005\u0005_Aq!!\u000eh\t\u0003\u0019)\bC\u0004\u0002\u000e\u001e$\tea\u001f\t\u0013\t-s-!A\u0005\u0002\r}\u0004\"\u0003B)OF\u0005I\u0011\u0001B*\u0011%\u0011IgZA\u0001\n\u0003\u0012Y\u0007C\u0005\u0003z\u001d\f\t\u0011\"\u0001\u0003.!I!1P4\u0002\u0002\u0013\u000511\u0011\u0005\n\u0005\u0013;\u0017\u0011!C!\u0005\u0017C\u0011B!'h\u0003\u0003%\taa\"\t\u0013\t\u0015v-!A\u0005B\r-\u0005\"\u0003BVO\u0006\u0005I\u0011\tBW\u0011%\u0011ykZA\u0001\n\u0003\u0012\t\fC\u0005\u00034\u001e\f\t\u0011\"\u0011\u0004\u0010\u001eI11\u0013\u000f\u0002\u0002#\u00051Q\u0013\u0004\n\u0007[b\u0012\u0011!E\u0001\u0007/Cq!!\u000ey\t\u0003\u0019Y\nC\u0005\u00030b\f\t\u0011\"\u0012\u00032\"I!q\u0002=\u0002\u0002\u0013\u00055Q\u0014\u0005\n\u00053D\u0018\u0011!CA\u0007CC\u0011Ba:y\u0003\u0003%IA!;\u0007\u0013\r\u0015\u0016\u0001%A\u0002\u0002\r\u001d\u0006bBA\u001f}\u0012\u0005\u0011q\b\u0005\n\u0005ss(\u0019!C\u0001\u0007SC\u0011b!\n\u007f\u0005\u0004%\taa,\t\u0013\rmcP1A\u0005\u0002\rM\u0006\"CBJ}\n\u0007I\u0011AB\\\r%\u0019Y,\u0001I\u0001\u0004\u0003\u0019i\f\u0003\u0005\u0002>\u0005%A\u0011AA \u0011!\u0019y,!\u0003\u0005\u0002\r\u0005\u0007\u0002CBd\u0003\u0013!\ta!3\t\u0011\r=\u0017\u0011\u0002C\u0001\u0007#D\u0001ba6\u0002\n\u0011\u00051\u0011\\\u0001\u0005\u0013:,GO\u0003\u0003\u0002\u001a\u0005m\u0011AA5p\u0015\t\ti\"\u0001\u0003bW.\f7\u0001\u0001\t\u0004\u0003G\tQBAA\f\u0005\u0011Ie.\u001a;\u0014\u0007\u0005\tI\u0003\u0005\u0003\u0002,\u0005ERBAA\u0017\u0015\t\ty#A\u0003tG\u0006d\u0017-\u0003\u0003\u00024\u00055\"AB!osJ+g-\u0001\u0004=S:LGO\u0010\u000b\u0003\u0003C\u0011AbU8dW\u0016$x\n\u001d;j_:\u001c2aAA\u0015\u0003\u0019!\u0013N\\5uIQ\u0011\u0011\u0011\t\t\u0005\u0003W\t\u0019%\u0003\u0003\u0002F\u00055\"\u0001B+oSR\f!CY3g_J,G)\u0019;bOJ\fWNQ5oIR!\u0011\u0011IA&\u0011\u001d\ti%\u0002a\u0001\u0003\u001f\n!\u0001Z:\u0011\t\u0005E\u00131L\u0007\u0003\u0003'RA!!\u0016\u0002X\u0005\u0019a.\u001a;\u000b\u0005\u0005e\u0013\u0001\u00026bm\u0006LA!!\u0018\u0002T\tqA)\u0019;bOJ\fWnU8dW\u0016$\b\u0006BA&\u0003C\u0002B!a\u0019\u0002j5\u0011\u0011Q\r\u0006\u0005\u0003O\nY\"\u0001\u0003vi&d\u0017\u0002BA6\u0003K\u0012a!\u001e8vg\u0016$\u0017A\u00062fM>\u0014XmU3sm\u0016\u00148k\\2lKR\u0014\u0015N\u001c3\u0015\t\u0005\u0005\u0013\u0011\u000f\u0005\b\u0003g2\u0001\u0019AA;\u0003\t\u00198\u000f\u0005\u0003\u0002R\u0005]\u0014\u0002BA=\u0003'\u0012AbU3sm\u0016\u00148k\\2lKRDC!!\u001d\u0002b\u0005i!-\u001a4pe\u0016\u001cuN\u001c8fGR$B!!\u0011\u0002\u0002\"9\u00111Q\u0004A\u0002\u0005\u0015\u0015!A:\u0011\t\u0005E\u0013qQ\u0005\u0005\u0003\u0013\u000b\u0019F\u0001\u0004T_\u000e\\W\r\u001e\u0015\u0005\u0003\u0003\u000b\t'\u0001\u0007bMR,'oQ8o]\u0016\u001cG\u000f\u0006\u0003\u0002B\u0005E\u0005bBAB\u0011\u0001\u0007\u0011Q\u0011\u0015\u0005\u0003#\u000b\tG\u0001\u000bBEN$(/Y2u'>\u001c7.\u001a;PaRLwN\\\n\u0006\u0013\u0005%\u0012\u0011\u0014\t\u0004\u00037\u001bQ\"A\u0001\u0015\u0005\u0005}\u0005cAAN\u0013\tq1k\\2lKR|\u0005\u000f^5p]Z\u00134#B\u0006\u0002*\u0005e\u0015!C1gi\u0016\u0014()\u001b8e)\u0011\t\t%!+\t\u000f\u0005\rU\u00021\u0001\u0002P!\"\u0011\u0011VA1)\u0011\t\t%a,\t\u000f\u0005\re\u00021\u0001\u0002v!\"\u0011qVA1)\u0011\t\t%!.\t\u000f\u0005\ru\u00021\u0001\u0002P!\"\u0011QWA1\u0005Y\t%m\u001d;sC\u000e$8k\\2lKR|\u0005\u000f^5p]Z\u00134#\u0002\t\u0002*\u0005u\u0006cAAN\u0017Q\u0011\u0011\u0011\u0019\t\u0004\u00037\u0003\"A\u0006#bi\u0006<'/Y7DQ\u0006tg.\u001a7De\u0016\fGo\u001c:\u0014\u000bI\tI#!'\u0015\u0005\u0005%\u0007cAAN%\u000511M]3bi\u0016$\"!a4\u0011\t\u0005E\u00171\\\u0007\u0003\u0003'TA!!6\u0002X\u0006A1\r[1o]\u0016d7O\u0003\u0003\u0002Z\u0006]\u0013a\u00018j_&!\u0011Q\\Aj\u0005=!\u0015\r^1he\u0006l7\t[1o]\u0016d\u0007&\u0002\u000b\u0002b\u0006}\bCBA\u0016\u0003G\f9/\u0003\u0003\u0002f\u00065\"A\u0002;ie><8\u000f\u0005\u0003\u0002j\u0006eh\u0002BAv\u0003ktA!!<\u0002t6\u0011\u0011q\u001e\u0006\u0005\u0003c\fy\"\u0001\u0004=e>|GOP\u0005\u0003\u0003_IA!a>\u0002.\u00059\u0001/Y2lC\u001e,\u0017\u0002BA~\u0003{\u0014\u0011\"\u0012=dKB$\u0018n\u001c8\u000b\t\u0005]\u0018QF\u0012\u0003\u0003O\fa\u0003R1uC\u001e\u0014\u0018-\\\"iC:tW\r\\\"sK\u0006$xN\u001d\t\u0004\u0003732c\u0001\f\u0002*Q\u0011!1A\u0001\bI\u00164\u0017-\u001e7u+\t\tI-\u0001\u0005eK\u001a\fW\u000f\u001c;!\u0003\u0015\t\u0007\u000f\u001d7z\u0003\t\u0019v\nE\u0002\u0002\u001cr\u0011!aU(\u0014\u0007q\tI\u0003\u0006\u0002\u0003\u0014\t\t\"+Z2fSZ,')\u001e4gKJ\u001c\u0016N_3\u0014\u0013y\tI#!'\u0003 \t\u0015\u0002\u0003BA\u0016\u0005CIAAa\t\u0002.\t9\u0001K]8ek\u000e$\b\u0003BAu\u0005OIAA!\u000b\u0002~\na1+\u001a:jC2L'0\u00192mK\u0006!1/\u001b>f+\t\u0011y\u0003\u0005\u0003\u0002,\tE\u0012\u0002\u0002B\u001a\u0003[\u00111!\u00138u\u0003\u0015\u0019\u0018N_3!)\u0011\u0011ID!\u0010\u0011\u0007\tmb$D\u0001\u001d\u0011\u001d\u0011Y#\ta\u0001\u0005_!B!!\u0011\u0003B!9\u00111\u0011\u0012A\u0002\u0005UD\u0003BA!\u0005\u000bBq!a!$\u0001\u0004\ty\u0005\u0006\u0003\u0002B\t%\u0003bBABI\u0001\u0007\u0011QQ\u0001\u0005G>\u0004\u0018\u0010\u0006\u0003\u0003:\t=\u0003\"\u0003B\u0016KA\u0005\t\u0019\u0001B\u0018\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIE*\"A!\u0016+\t\t=\"qK\u0016\u0003\u00053\u0002BAa\u0017\u0003f5\u0011!Q\f\u0006\u0005\u0005?\u0012\t'A\u0005v]\u000eDWmY6fI*!!1MA\u0017\u0003)\tgN\\8uCRLwN\\\u0005\u0005\u0005O\u0012iFA\tv]\u000eDWmY6fIZ\u000b'/[1oG\u0016\fQ\u0002\u001d:pIV\u001cG\u000f\u0015:fM&DXC\u0001B7!\u0011\u0011yG!\u001e\u000e\u0005\tE$\u0002\u0002B:\u0003/\nA\u0001\\1oO&!!q\u000fB9\u0005\u0019\u0019FO]5oO\u0006a\u0001O]8ek\u000e$\u0018I]5us\u0006q\u0001O]8ek\u000e$X\t\\3nK:$H\u0003\u0002B@\u0005\u000b\u0003B!a\u000b\u0003\u0002&!!1QA\u0017\u0005\r\te.\u001f\u0005\n\u0005\u000fK\u0013\u0011!a\u0001\u0005_\t1\u0001\u001f\u00132\u0003=\u0001(o\u001c3vGRLE/\u001a:bi>\u0014XC\u0001BG!\u0019\u0011yI!&\u0003\u00005\u0011!\u0011\u0013\u0006\u0005\u0005'\u000bi#\u0001\u0006d_2dWm\u0019;j_:LAAa&\u0003\u0012\nA\u0011\n^3sCR|'/\u0001\u0005dC:,\u0015/^1m)\u0011\u0011iJa)\u0011\t\u0005-\"qT\u0005\u0005\u0005C\u000biCA\u0004C_>dW-\u00198\t\u0013\t\u001d5&!AA\u0002\t}\u0014A\u00059s_\u0012,8\r^#mK6,g\u000e\u001e(b[\u0016$BA!\u001c\u0003*\"I!q\u0011\u0017\u0002\u0002\u0003\u0007!qF\u0001\tQ\u0006\u001c\bnQ8eKR\u0011!qF\u0001\ti>\u001cFO]5oOR\u0011!QN\u0001\u0007KF,\u0018\r\\:\u0015\t\tu%q\u0017\u0005\n\u0005\u000f{\u0013\u0011!a\u0001\u0005\u007f\n\u0011CU3dK&4XMQ;gM\u0016\u00148+\u001b>f!\r\u0011Y$M\n\u0006c\t}&1\u001a\t\t\u0005\u0003\u00149Ma\f\u0003:5\u0011!1\u0019\u0006\u0005\u0005\u000b\fi#A\u0004sk:$\u0018.\\3\n\t\t%'1\u0019\u0002\u0012\u0003\n\u001cHO]1di\u001a+hn\u0019;j_:\f\u0004\u0003\u0002Bg\u0005#l!Aa4\u000b\t\u0005e\u0011qK\u0005\u0005\u0005S\u0011y\r\u0006\u0002\u0003<R!!\u0011\bBl\u0011\u001d\u0011Y\u0003\u000ea\u0001\u0005_\tq!\u001e8baBd\u0017\u0010\u0006\u0003\u0003^\n\r\bCBA\u0016\u0005?\u0014y#\u0003\u0003\u0003b\u00065\"AB(qi&|g\u000eC\u0005\u0003fV\n\t\u00111\u0001\u0003:\u0005\u0019\u0001\u0010\n\u0019\u0002\u0019]\u0014\u0018\u000e^3SKBd\u0017mY3\u0015\u0005\t-\b\u0003\u0002B8\u0005[LAAa<\u0003r\t1qJ\u00196fGR\u0014ABU3vg\u0016\fE\r\u001a:fgN\u001c\u0012bNA\u0015\u00033\u0013yB!\n\u0002\u0005=tWC\u0001BO\u0003\ryg\u000e\t\u000b\u0005\u0005{\u0014y\u0010E\u0002\u0003<]BqA!>;\u0001\u0004\u0011i\n\u0006\u0003\u0002B\r\r\u0001bBABw\u0001\u0007\u0011Q\u000f\u000b\u0005\u0003\u0003\u001a9\u0001C\u0004\u0002\u0004r\u0002\r!a\u0014\u0015\t\u0005\u000531\u0002\u0005\b\u0003\u0007k\u0004\u0019AAC)\u0011\u0011ipa\u0004\t\u0013\tUh\b%AA\u0002\tuUCAB\nU\u0011\u0011iJa\u0016\u0015\t\t}4q\u0003\u0005\n\u0005\u000f\u0013\u0015\u0011!a\u0001\u0005_!BA!(\u0004\u001c!I!q\u0011#\u0002\u0002\u0003\u0007!q\u0010\u000b\u0005\u0005[\u001ay\u0002C\u0005\u0003\b\u0016\u000b\t\u00111\u0001\u00030Q!!QTB\u0012\u0011%\u00119\tSA\u0001\u0002\u0004\u0011y(\u0001\u0007SKV\u001cX-\u00113ee\u0016\u001c8\u000fE\u0002\u0003<)\u001bRASB\u0016\u0005\u0017\u0004\u0002B!1\u0003H\nu%Q \u000b\u0003\u0007O!BA!@\u00042!9!Q_'A\u0002\tuE\u0003BB\u001b\u0007o\u0001b!a\u000b\u0003`\nu\u0005\"\u0003Bs\u001d\u0006\u0005\t\u0019\u0001B\u007f\u00059\u0019VM\u001c3Ck\u001a4WM]*ju\u0016\u001c\u0012\u0002UA\u0015\u00033\u0013yB!\n\u0015\t\r}2\u0011\t\t\u0004\u0005w\u0001\u0006b\u0002B\u0016'\u0002\u0007!q\u0006\u000b\u0005\u0003\u0003\u001a)\u0005C\u0004\u0002\u0004R\u0003\r!!\"\u0015\t\r}2\u0011\n\u0005\n\u0005W)\u0006\u0013!a\u0001\u0005_!BAa \u0004N!I!qQ-\u0002\u0002\u0003\u0007!q\u0006\u000b\u0005\u0005;\u001b\t\u0006C\u0005\u0003\bn\u000b\t\u00111\u0001\u0003\u0000Q!!QNB+\u0011%\u00119\tXA\u0001\u0002\u0004\u0011y\u0003\u0006\u0003\u0003\u001e\u000ee\u0003\"\u0003BD?\u0006\u0005\t\u0019\u0001B@\u00039\u0019VM\u001c3Ck\u001a4WM]*ju\u0016\u00042Aa\u000fb'\u0015\t7\u0011\rBf!!\u0011\tMa2\u00030\r}BCAB/)\u0011\u0019yda\u001a\t\u000f\t-B\r1\u0001\u00030Q!!Q\\B6\u0011%\u0011)/ZA\u0001\u0002\u0004\u0019yD\u0001\u0007Ue\u00064g-[2DY\u0006\u001c8oE\u0005h\u0003S\tIJa\b\u0003&\u0005\u0011AoY\u0001\u0004i\u000e\u0004C\u0003BB<\u0007s\u00022Aa\u000fh\u0011\u001d\u0019\tH\u001ba\u0001\u0005_!B!!\u0011\u0004~!9\u00111Q6A\u0002\u0005\u0015E\u0003BB<\u0007\u0003C\u0011b!\u001dm!\u0003\u0005\rAa\f\u0015\t\t}4Q\u0011\u0005\n\u0005\u000f\u0003\u0018\u0011!a\u0001\u0005_!BA!(\u0004\n\"I!q\u0011:\u0002\u0002\u0003\u0007!q\u0010\u000b\u0005\u0005[\u001ai\tC\u0005\u0003\bN\f\t\u00111\u0001\u00030Q!!QTBI\u0011%\u00119I^A\u0001\u0002\u0004\u0011y(\u0001\u0007Ue\u00064g-[2DY\u0006\u001c8\u000fE\u0002\u0003<a\u001cR\u0001_BM\u0005\u0017\u0004\u0002B!1\u0003H\n=2q\u000f\u000b\u0003\u0007+#Baa\u001e\u0004 \"91\u0011O>A\u0002\t=B\u0003\u0002Bo\u0007GC\u0011B!:}\u0003\u0003\u0005\raa\u001e\u0003\u0019M{gi\u001c:xCJ$WM]:\u0014\u0007y\fI#\u0006\u0002\u0004,:\u00191Q\u0016\u0019\u000f\u0007\u0005m5$\u0006\u0002\u00042:\u00191QV%\u0016\u0005\rUfbABWAV\u00111\u0011\u0018\b\u0004\u0007[;(aD*p\u0015\u00064\u0018MR1di>\u0014\u0018.Z:\u0014\t\u0005%\u0011\u0011F\u0001\u0012e\u0016\u001cW-\u001b<f\u0005V4g-\u001a:TSj,G\u0003BBb\u0007\u000b\u00042a!,\u001f\u0011!\u0011Y#!\u0004A\u0002\t=\u0012\u0001\u0004:fkN,\u0017\t\u001a3sKN\u001cH\u0003BBf\u0007\u001b\u00042a!,8\u0011!\u0011)0a\u0004A\u0002\tu\u0015AD:f]\u0012\u0014UO\u001a4feNK'0\u001a\u000b\u0005\u0007'\u001c)\u000eE\u0002\u0004.BC\u0001Ba\u000b\u0002\u0012\u0001\u0007!qF\u0001\riJ\fgMZ5d\u00072\f7o\u001d\u000b\u0005\u00077\u001ci\u000eE\u0002\u0004.\u001eD\u0001b!\u001d\u0002\u0014\u0001\u0007!q\u0006")
public final class Inet {

    public static abstract class AbstractSocketOption
    implements SocketOption {
        @Override
        public void beforeDatagramBind(DatagramSocket ds) {
            ((SocketOption)this).beforeDatagramBind(ds);
        }

        @Override
        public void beforeServerSocketBind(ServerSocket ss) {
            ((SocketOption)this).beforeServerSocketBind(ss);
        }

        @Override
        public void beforeConnect(Socket s2) {
            ((SocketOption)this).beforeConnect(s2);
        }

        @Override
        public void afterConnect(Socket s2) {
            ((SocketOption)this).afterConnect(s2);
        }

        public AbstractSocketOption() {
            SocketOption.$init$(this);
        }
    }

    public static abstract class AbstractSocketOptionV2
    implements SocketOptionV2 {
        @Override
        public void afterBind(DatagramSocket s2) {
            ((SocketOptionV2)this).afterBind(s2);
        }

        @Override
        public void afterBind(ServerSocket s2) {
            ((SocketOptionV2)this).afterBind(s2);
        }

        @Override
        public void afterConnect(DatagramSocket s2) {
            ((SocketOptionV2)this).afterConnect(s2);
        }

        @Override
        public void beforeDatagramBind(DatagramSocket ds) {
            ((SocketOption)this).beforeDatagramBind(ds);
        }

        @Override
        public void beforeServerSocketBind(ServerSocket ss) {
            ((SocketOption)this).beforeServerSocketBind(ss);
        }

        @Override
        public void beforeConnect(Socket s2) {
            ((SocketOption)this).beforeConnect(s2);
        }

        @Override
        public void afterConnect(Socket s2) {
            ((SocketOption)this).afterConnect(s2);
        }

        public AbstractSocketOptionV2() {
            SocketOption.$init$(this);
            SocketOptionV2.$init$(this);
        }
    }

    public static class DatagramChannelCreator
    implements SocketOption {
        @Override
        public void beforeDatagramBind(DatagramSocket ds) {
            ((SocketOption)this).beforeDatagramBind(ds);
        }

        @Override
        public void beforeServerSocketBind(ServerSocket ss) {
            ((SocketOption)this).beforeServerSocketBind(ss);
        }

        @Override
        public void beforeConnect(Socket s2) {
            ((SocketOption)this).beforeConnect(s2);
        }

        @Override
        public void afterConnect(Socket s2) {
            ((SocketOption)this).afterConnect(s2);
        }

        public DatagramChannel create() throws Exception {
            return DatagramChannel.open();
        }

        public DatagramChannelCreator() {
            SocketOption.$init$(this);
        }
    }

    public static interface SoForwarders {
        public void akka$io$Inet$SoForwarders$_setter_$ReceiveBufferSize_$eq(Inet$SO$ReceiveBufferSize$ var1);

        public void akka$io$Inet$SoForwarders$_setter_$ReuseAddress_$eq(Inet$SO$ReuseAddress$ var1);

        public void akka$io$Inet$SoForwarders$_setter_$SendBufferSize_$eq(Inet$SO$SendBufferSize$ var1);

        public void akka$io$Inet$SoForwarders$_setter_$TrafficClass_$eq(Inet$SO$TrafficClass$ var1);

        public Inet$SO$ReceiveBufferSize$ ReceiveBufferSize();

        public Inet$SO$ReuseAddress$ ReuseAddress();

        public Inet$SO$SendBufferSize$ SendBufferSize();

        public Inet$SO$TrafficClass$ TrafficClass();

        public static void $init$(SoForwarders $this) {
            $this.akka$io$Inet$SoForwarders$_setter_$ReceiveBufferSize_$eq(Inet$SO$ReceiveBufferSize$.MODULE$);
            $this.akka$io$Inet$SoForwarders$_setter_$ReuseAddress_$eq(Inet$SO$ReuseAddress$.MODULE$);
            $this.akka$io$Inet$SoForwarders$_setter_$SendBufferSize_$eq(Inet$SO$SendBufferSize$.MODULE$);
            $this.akka$io$Inet$SoForwarders$_setter_$TrafficClass_$eq(Inet$SO$TrafficClass$.MODULE$);
        }
    }

    public static interface SoJavaFactories {
        public static /* synthetic */ SO.ReceiveBufferSize receiveBufferSize$(SoJavaFactories $this, int size) {
            return $this.receiveBufferSize(size);
        }

        default public SO.ReceiveBufferSize receiveBufferSize(int size) {
            return new SO.ReceiveBufferSize(size);
        }

        public static /* synthetic */ SO.ReuseAddress reuseAddress$(SoJavaFactories $this, boolean on) {
            return $this.reuseAddress(on);
        }

        default public SO.ReuseAddress reuseAddress(boolean on) {
            return new SO.ReuseAddress(on);
        }

        public static /* synthetic */ SO.SendBufferSize sendBufferSize$(SoJavaFactories $this, int size) {
            return $this.sendBufferSize(size);
        }

        default public SO.SendBufferSize sendBufferSize(int size) {
            return new SO.SendBufferSize(size);
        }

        public static /* synthetic */ SO.TrafficClass trafficClass$(SoJavaFactories $this, int tc) {
            return $this.trafficClass(tc);
        }

        default public SO.TrafficClass trafficClass(int tc) {
            return new SO.TrafficClass(tc);
        }

        public static void $init$(SoJavaFactories $this) {
        }
    }

    public static interface SocketOption {
        default public void beforeDatagramBind(DatagramSocket ds) {
        }

        default public void beforeServerSocketBind(ServerSocket ss) {
        }

        default public void beforeConnect(Socket s2) {
        }

        default public void afterConnect(Socket s2) {
        }

        public static void $init$(SocketOption $this) {
        }
    }

    public static interface SocketOptionV2
    extends SocketOption {
        default public void afterBind(DatagramSocket s2) {
        }

        default public void afterBind(ServerSocket s2) {
        }

        default public void afterConnect(DatagramSocket s2) {
        }

        public static void $init$(SocketOptionV2 $this) {
        }
    }
}

