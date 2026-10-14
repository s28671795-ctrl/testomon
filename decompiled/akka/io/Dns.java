/*
 * Decompiled with CFR 0.152.
 */
package akka.io;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.ClassicActorSystemProvider;
import akka.actor.ExtendedActorSystem;
import akka.actor.Extension;
import akka.annotation.DoNotInherit;
import akka.io.Dns$;
import akka.io.DnsExt;
import akka.io.IO$;
import akka.io.IpVersionSelector$;
import akka.io.dns.DnsProtocol;
import akka.routing.ConsistentHashingRouter;
import java.io.Serializable;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.Product;
import scala.Some;
import scala.collection.Iterator;
import scala.collection.immutable.Seq;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\r%f!B%K\u0003\u0003y\u0005\"\u0002,\u0001\t\u00039\u0006\"\u0002.\u0001\t\u0003Y\u0006bBB\u000e\u0001\u0011\u00051Q\u0010\u0005\u00075\u0002!\ta!$\t\u000f\rm\u0001\u0001\"\u0001\u0004\u0016\u001e)\u0011M\u0013E\u0001E\u001a)\u0011J\u0013E\u0001G\")ak\u0002C\u0001a\u001a9\u0011o\u0002I\u0001$C\u0011h\u0001\u0002;\b\u0001VD!\"!\u000b\u000b\u0005+\u0007I\u0011AA\u0016\u0011)\tiD\u0003B\tB\u0003%\u0011Q\u0006\u0005\u0007-*!\t!a\u0010\t\u000f\u0005\u0015#\u0002\"\u0011\u0002,!I\u0011q\t\u0006\u0002\u0002\u0013\u0005\u0011\u0011\n\u0005\n\u0003\u001bR\u0011\u0013!C\u0001\u0003\u001fB\u0011\"!\u001a\u000b\u0003\u0003%\t%a\u001a\t\u0013\u0005]$\"!A\u0005\u0002\u0005e\u0004\"CAA\u0015\u0005\u0005I\u0011AAB\u0011%\tyICA\u0001\n\u0003\n\t\nC\u0005\u0002 *\t\t\u0011\"\u0001\u0002\"\"I\u00111\u0016\u0006\u0002\u0002\u0013\u0005\u0013Q\u0016\u0005\n\u0003cS\u0011\u0011!C!\u0003gC\u0011\"!.\u000b\u0003\u0003%\t%a.\t\u0013\u0005e&\"!A\u0005B\u0005mv!\u0003BC\u000f\u0005\u0005\t\u0012\u0001BD\r!!x!!A\t\u0002\t%\u0005B\u0002,\u001c\t\u0003\u0011y\nC\u0005\u00026n\t\t\u0011\"\u0012\u00028\"I!\u0011U\u000e\u0002\u0002\u0013\u0005%1\u0015\u0005\n\u0005O[\u0012\u0011!CA\u0005SC\u0011B!-\u001c\u0003\u0003%IAa-\u0007\r\u0005Mw\u0001QAk\u0011)\tI#\tBK\u0002\u0013\u0005\u00111\u0006\u0005\u000b\u0003{\t#\u0011#Q\u0001\n\u00055\u0002BCAlC\tU\r\u0011\"\u0001\u0002Z\"Q\u00111_\u0011\u0003\u0012\u0003\u0006I!a7\t\u0015\u0005U\u0018E!f\u0001\n\u0003\t9\u0010\u0003\u0006\u0003\u0002\u0005\u0012\t\u0012)A\u0005\u0003sDaAV\u0011\u0005\u0002\t\r\u0001\"\u0003B\u0007C\t\u0007I\u0011\u0001B\b\u0011!\u0011I\"\tQ\u0001\n\tE\u0001b\u0002B\u000eC\u0011\u0005!Q\u0004\u0005\n\u0003\u000f\n\u0013\u0011!C\u0001\u00057B\u0011\"!\u0014\"#\u0003%\t!a\u0014\t\u0013\t\r\u0014%%A\u0005\u0002\t\u0015\u0004\"\u0003B5CE\u0005I\u0011\u0001B6\u0011%\t)'IA\u0001\n\u0003\n9\u0007C\u0005\u0002x\u0005\n\t\u0011\"\u0001\u0002z!I\u0011\u0011Q\u0011\u0002\u0002\u0013\u0005!q\u000e\u0005\n\u0003\u001f\u000b\u0013\u0011!C!\u0003#C\u0011\"a(\"\u0003\u0003%\tAa\u001d\t\u0013\u0005-\u0016%!A\u0005B\t]\u0004\"CAYC\u0005\u0005I\u0011IAZ\u0011%\t),IA\u0001\n\u0003\n9\fC\u0005\u0002:\u0006\n\t\u0011\"\u0011\u0003|\u001d9!1X\u0004\t\u0002\tufaBAj\u000f!\u0005!q\u0018\u0005\u0007-j\"\tA!1\t\u000f\t\u0005&\b\"\u0001\u0003D\"9!\u0011\u0015\u001e\u0005\u0002\tE\u0007\"\u0003BQu\u0005\u0005I\u0011\u0011Bv\u0011%\u00119KOA\u0001\n\u0003\u0013\u0019\u0010C\u0005\u00032j\n\t\u0011\"\u0003\u00034\"1!l\u0002C\u0001\u0007\u0007Aqaa\u0007\b\t\u0003\u0019i\u0002\u0003\u0004[\u000f\u0011\u00051Q\u0007\u0005\b\u000779A\u0011AB\"\u0011\u001d\u0019Ye\u0002C!\u0007\u001bBqaa\u0014\b\t\u0003\u001a\t\u0006C\u0004\u0004\\\u001d!\te!\u0018\t\u000f\rms\u0001\"\u0011\u0004b\t\u0019AI\\:\u000b\u0005-c\u0015AA5p\u0015\u0005i\u0015\u0001B1lW\u0006\u001c\u0001a\u0005\u0002\u0001!B\u0011\u0011\u000bV\u0007\u0002%*\t1+A\u0003tG\u0006d\u0017-\u0003\u0002V%\n1\u0011I\\=SK\u001a\fa\u0001P5oSRtD#\u0001-\u0011\u0005e\u0003Q\"\u0001&\u0002\r\r\f7\r[3e)\ra61\u000e\t\u0004#v{\u0016B\u00010S\u0005\u0019y\u0005\u000f^5p]B\u0011\u0001-\t\b\u00033\u001a\t1\u0001\u00128t!\tIva\u0005\u0003\b!\u0012l\u0007cA3iU6\taM\u0003\u0002h\u0019\u0006)\u0011m\u0019;pe&\u0011\u0011N\u001a\u0002\f\u000bb$XM\\:j_:LE\r\u0005\u0002ZW&\u0011AN\u0013\u0002\u0007\t:\u001cX\t\u001f;\u0011\u0005\u0015t\u0017BA8g\u0005M)\u0005\u0010^3og&|g.\u00133Qe>4\u0018\u000eZ3s)\u0005\u0011'aB\"p[6\fg\u000eZ\n\u0003\u0013AK3!\u0003\u0006\"\u0005\u001d\u0011Vm]8mm\u0016\u001c\u0002B\u0003)wq\u0006E\u0011q\u0003\t\u0003o&i\u0011a\u0002\t\u0004s\u0006-ab\u0001>\u0002\u00069\u001910!\u0001\u000f\u0005q|X\"A?\u000b\u0005yt\u0015A\u0002\u001fs_>$h(C\u0001N\u0013\r\t\u0019\u0001T\u0001\be>,H/\u001b8h\u0013\u0011\t9!!\u0003\u0002/\r{gn]5ti\u0016tG\u000fS1tQ&twMU8vi\u0016\u0014(bAA\u0002\u0019&!\u0011QBA\b\u0005I\u0019uN\\:jgR,g\u000e\u001e%bg\"\f'\r\\3\u000b\t\u0005\u001d\u0011\u0011\u0002\t\u0004#\u0006M\u0011bAA\u000b%\n9\u0001K]8ek\u000e$\b\u0003BA\r\u0003GqA!a\u0007\u0002 9\u0019A0!\b\n\u0003MK1!!\tS\u0003\u001d\u0001\u0018mY6bO\u0016LA!!\n\u0002(\ta1+\u001a:jC2L'0\u00192mK*\u0019\u0011\u0011\u0005*\u0002\t9\fW.Z\u000b\u0003\u0003[\u0001B!a\f\u000289!\u0011\u0011GA\u001a!\ta(+C\u0002\u00026I\u000ba\u0001\u0015:fI\u00164\u0017\u0002BA\u001d\u0003w\u0011aa\u0015;sS:<'bAA\u001b%\u0006)a.Y7fAQ!\u0011\u0011IA\"!\t9(\u0002C\u0004\u0002*5\u0001\r!!\f\u0002#\r|gn]5ti\u0016tG\u000fS1tQ.+\u00170\u0001\u0003d_BLH\u0003BA!\u0003\u0017B\u0011\"!\u000b\u0010!\u0003\u0005\r!!\f\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%cU\u0011\u0011\u0011\u000b\u0016\u0005\u0003[\t\u0019f\u000b\u0002\u0002VA!\u0011qKA1\u001b\t\tIF\u0003\u0003\u0002\\\u0005u\u0013!C;oG\",7m[3e\u0015\r\tyFU\u0001\u000bC:tw\u000e^1uS>t\u0017\u0002BA2\u00033\u0012\u0011#\u001e8dQ\u0016\u001c7.\u001a3WCJL\u0017M\\2f\u00035\u0001(o\u001c3vGR\u0004&/\u001a4jqV\u0011\u0011\u0011\u000e\t\u0005\u0003W\n)(\u0004\u0002\u0002n)!\u0011qNA9\u0003\u0011a\u0017M\\4\u000b\u0005\u0005M\u0014\u0001\u00026bm\u0006LA!!\u000f\u0002n\u0005a\u0001O]8ek\u000e$\u0018I]5usV\u0011\u00111\u0010\t\u0004#\u0006u\u0014bAA@%\n\u0019\u0011J\u001c;\u0002\u001dA\u0014x\u000eZ;di\u0016cW-\\3oiR!\u0011QQAF!\r\t\u0016qQ\u0005\u0004\u0003\u0013\u0013&aA!os\"I\u0011QR\n\u0002\u0002\u0003\u0007\u00111P\u0001\u0004q\u0012\n\u0014a\u00049s_\u0012,8\r^%uKJ\fGo\u001c:\u0016\u0005\u0005M\u0005CBAK\u00037\u000b))\u0004\u0002\u0002\u0018*\u0019\u0011\u0011\u0014*\u0002\u0015\r|G\u000e\\3di&|g.\u0003\u0003\u0002\u001e\u0006]%\u0001C%uKJ\fGo\u001c:\u0002\u0011\r\fg.R9vC2$B!a)\u0002*B\u0019\u0011+!*\n\u0007\u0005\u001d&KA\u0004C_>dW-\u00198\t\u0013\u00055U#!AA\u0002\u0005\u0015\u0015A\u00059s_\u0012,8\r^#mK6,g\u000e\u001e(b[\u0016$B!!\u001b\u00020\"I\u0011Q\u0012\f\u0002\u0002\u0003\u0007\u00111P\u0001\tQ\u0006\u001c\bnQ8eKR\u0011\u00111P\u0001\ti>\u001cFO]5oOR\u0011\u0011\u0011N\u0001\u0007KF,\u0018\r\\:\u0015\t\u0005\r\u0016Q\u0018\u0005\n\u0003\u001bK\u0012\u0011!a\u0001\u0003\u000bC3BCAa\u0003\u000f\fI-!4\u0002PB\u0019\u0011+a1\n\u0007\u0005\u0015'K\u0001\u0006eKB\u0014XmY1uK\u0012\fq!\\3tg\u0006<W-\t\u0002\u0002L\u0006yRk]3!G\u0006\u001c\u0007.\u001a3)\t:\u001c\bK]8u_\u000e|GN\f*fg>dg/Z\u0015\u0002\u000bMLgnY3\"\u0005\u0005E\u0017!\u0002\u001a/m9\u0002$\u0001\u0003*fg>dg/\u001a3\u0014\u000f\u0005\u0002f/!\u0005\u0002\u0018\u0005!\u0011\u000e\u001d<5+\t\tY\u000e\u0005\u0004\u0002^\u0006\r\u0018q]\u0007\u0003\u0003?TA!!9\u0002\u0018\u0006I\u0011.\\7vi\u0006\u0014G.Z\u0005\u0005\u0003K\fyNA\u0002TKF\u0004B!!;\u0002p6\u0011\u00111\u001e\u0006\u0005\u0003[\f\t(A\u0002oKRLA!!=\u0002l\na\u0011J\\3ui\u0005#GM]3tg\u0006)\u0011\u000e\u001d<5A\u0005!\u0011\u000e\u001d<7+\t\tI\u0010\u0005\u0004\u0002^\u0006\r\u00181 \t\u0005\u0003S\fi0\u0003\u0003\u0002\u0000\u0006-(\u0001D%oKR4\u0014\t\u001a3sKN\u001c\u0018!B5qmZ\u0002C\u0003\u0003B\u0003\u0005\u000f\u0011IAa\u0003\u0011\u0005]\f\u0003bBA\u0015Q\u0001\u0007\u0011Q\u0006\u0005\b\u0003/D\u0003\u0019AAn\u0011\u001d\t)\u0010\u000ba\u0001\u0003s\f!\"\u00193ee>\u0003H/[8o+\t\u0011\t\u0002\u0005\u0003R;\nM\u0001\u0003BAu\u0005+IAAa\u0006\u0002l\nY\u0011J\\3u\u0003\u0012$'/Z:t\u0003-\tG\r\u001a:PaRLwN\u001c\u0011\u0002\t\u0005$GM]\u000b\u0003\u0005'ASa\u000bB\u0011\u0005[\u0001R!\u0015B\u0012\u0005OI1A!\nS\u0005\u0019!\bN]8xgB!\u0011\u0011\u001eB\u0015\u0013\u0011\u0011Y#a;\u0003)Us7N\\8x]\"{7\u000f^#yG\u0016\u0004H/[8oc\u001dq\u0012Q\u0006B\u0018\u00053\n\u0014b\tB\u0019\u0005o\u0011yE!\u000f\u0016\t\u0005-\"1\u0007\u0003\b\u0005kq%\u0019\u0001B \u0005\u0005!\u0016\u0002\u0002B\u001d\u0005w\t1\u0004\n7fgNLg.\u001b;%OJ,\u0017\r^3sI\u0011,g-Y;mi\u0012\n$b\u0001B\u001f%\u00061A\u000f\u001b:poN\fBA!\u0011\u0003HA\u0019\u0011Ka\u0011\n\u0007\t\u0015#KA\u0004O_RD\u0017N\\4\u0011\t\t%#1\n\b\u0004#\u0006}\u0011\u0002\u0002B'\u0003O\u0011\u0011\u0002\u00165s_^\f'\r\\32\u0013\r\u0012\tFa\u0015\u0003V\tubbA)\u0003T%\u0019!Q\b*2\u000b\t\n&Ka\u0016\u0003\u000bM\u001c\u0017\r\\12\u0007\u0019\u00129\u0003\u0006\u0005\u0003\u0006\tu#q\fB1\u0011%\tI\u0003\fI\u0001\u0002\u0004\ti\u0003C\u0005\u0002X2\u0002\n\u00111\u0001\u0002\\\"I\u0011Q\u001f\u0017\u0011\u0002\u0003\u0007\u0011\u0011`\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00133+\t\u00119G\u000b\u0003\u0002\\\u0006M\u0013AD2paf$C-\u001a4bk2$HeM\u000b\u0003\u0005[RC!!?\u0002TQ!\u0011Q\u0011B9\u0011%\tiIMA\u0001\u0002\u0004\tY\b\u0006\u0003\u0002$\nU\u0004\"CAGi\u0005\u0005\t\u0019AAC)\u0011\tIG!\u001f\t\u0013\u00055U'!AA\u0002\u0005mD\u0003BAR\u0005{B\u0011\"!$9\u0003\u0003\u0005\r!!\")\u0017\u0005\n\t-a2\u0003\u0002\u00065\u0017qZ\u0011\u0003\u0005\u0007\u000b\u0001%V:fA\r\f7\r[3eQ\u0011s7\u000f\u0015:pi>\u001cw\u000e\u001c\u0018SKN|GN^3eS\u00059!+Z:pYZ,\u0007CA<\u001c'\u0015Y\"1\u0012BL!!\u0011iIa%\u0002.\u0005\u0005SB\u0001BH\u0015\r\u0011\tJU\u0001\beVtG/[7f\u0013\u0011\u0011)Ja$\u0003#\u0005\u00137\u000f\u001e:bGR4UO\\2uS>t\u0017\u0007\u0005\u0003\u0003\u001a\nuUB\u0001BN\u0015\rY\u0015\u0011O\u0005\u0005\u0003K\u0011Y\n\u0006\u0002\u0003\b\u0006)\u0011\r\u001d9msR!\u0011\u0011\tBS\u0011\u001d\tIC\ba\u0001\u0003[\tq!\u001e8baBd\u0017\u0010\u0006\u0003\u0003,\n5\u0006\u0003B)^\u0003[A\u0011Ba, \u0003\u0003\u0005\r!!\u0011\u0002\u0007a$\u0003'\u0001\u0007xe&$XMU3qY\u0006\u001cW\r\u0006\u0002\u00036B!\u00111\u000eB\\\u0013\u0011\u0011I,!\u001c\u0003\r=\u0013'.Z2u\u0003!\u0011Vm]8mm\u0016$\u0007CA<;'\u0011Q\u0004Ka&\u0015\u0005\tuFC\u0002B\u0003\u0005\u000b\u00149\rC\u0004\u0002*q\u0002\r!!\f\t\u000f\t%G\b1\u0001\u0003L\u0006I\u0011\r\u001a3sKN\u001cXm\u001d\t\u0007\u00033\u0011iMa\u0005\n\t\t=\u0017q\u0005\u0002\t\u0013R,'/\u00192mKR!!Q\u0001Bj\u0011\u001d\u0011).\u0010a\u0001\u0005/\f1B\\3x!J|Go\\2pYB!!\u0011\u001cBs\u001d\u0011\u0011YN!9\u000e\u0005\tu'b\u0001Bp\u0015\u0006\u0019AM\\:\n\t\t\r(Q\\\u0001\f\t:\u001c\bK]8u_\u000e|G.\u0003\u0003\u0002T\n\u001d(\u0002\u0002Br\u0005;D3\"PAa\u0003\u000f\fI-!4\u0002PRA!Q\u0001Bw\u0005_\u0014\t\u0010C\u0004\u0002*y\u0002\r!!\f\t\u000f\u0005]g\b1\u0001\u0002\\\"9\u0011Q\u001f A\u0002\u0005eH\u0003\u0002B{\u0005{\u0004B!U/\u0003xBI\u0011K!?\u0002.\u0005m\u0017\u0011`\u0005\u0004\u0005w\u0014&A\u0002+va2,7\u0007C\u0005\u00030~\n\t\u00111\u0001\u0003\u0006!Z!(!1\u0002H\n\u0005\u0015QZAhQ-I\u0014\u0011YAd\u0005\u0003\u000bi-a4\u0015\t\r\u001511\u0003\u000b\u0005\u0007\u000f\u0019I\u0001\u0005\u0003R;\n\u0015\u0001bBB\u0006\u0003\u0002\u00071QB\u0001\u0007gf\u001cH/Z7\u0011\u0007\u0015\u001cy!C\u0002\u0004\u0012\u0019\u00141\"Q2u_J\u001c\u0016p\u001d;f[\"9\u0011\u0011F!A\u0002\u00055\u0002fC!\u0002B\u0006\u001d7qCAg\u0003\u001f\f#a!\u0007\u0002?U\u001cX\rI2bG\",G\r\u000b#ogB\u0013x\u000e^8d_2t#+Z:pYZ,\u0017&A\u0004sKN|GN^3\u0015\t\r}1Q\u0006\u000b\u0007\u0007\u000f\u0019\tca\t\t\u000f\r-!\t1\u0001\u0004\u000e!91Q\u0005\"A\u0002\r\u001d\u0012AB:f]\u0012,'\u000fE\u0002f\u0007SI1aa\u000bg\u0005!\t5\r^8s%\u00164\u0007bBA\u0015\u0005\u0002\u0007\u0011Q\u0006\u0015\f\u0005\u0006\u0005\u0017qYB\u0019\u0003\u001b\fy-\t\u0002\u00044\u0005\u0001So]3!e\u0016\u001cx\u000e\u001c<fQ\u0011s7\u000f\u0015:pi>\u001cw\u000e\u001c\u0018SKN|GN^3*)\u0011\u00199d!\u0010\u0015\t\re21\b\t\u0005#v\u00139\u000eC\u0004\u0004\f\r\u0003\ra!\u0004\t\u000f\u0005%2\t1\u0001\u0004@A!!\u0011\\B!\u0013\r!(q\u001d\u000b\t\u0007s\u0019)ea\u0012\u0004J!9\u0011\u0011\u0006#A\u0002\r}\u0002bBB\u0006\t\u0002\u00071Q\u0002\u0005\b\u0007K!\u0005\u0019AB\u0014\u0003\u0019awn\\6vaV\t!-A\bde\u0016\fG/Z#yi\u0016t7/[8o)\rQ71\u000b\u0005\b\u0007\u00171\u0005\u0019AB+!\r)7qK\u0005\u0004\u000732'aE#yi\u0016tG-\u001a3BGR|'oU=ti\u0016l\u0017aA4fiR\u0019!na\u0018\t\u000f\r-q\t1\u0001\u0004\u000eQ\u0019!na\u0019\t\u000f\r-\u0001\n1\u0001\u0004fA\u0019Qma\u001a\n\u0007\r%dM\u0001\u000eDY\u0006\u001c8/[2BGR|'oU=ti\u0016l\u0007K]8wS\u0012,'\u000fC\u0004\u0002*\t\u0001\r!!\f)\t\r-4q\u000e\t\u0005\u0007c\u001a9(\u0004\u0002\u0004t)\u00191Q\u000f'\u0002\tU$\u0018\u000e\\\u0005\u0005\u0007s\u001a\u0019H\u0001\u0004v]V\u001cX\r\u001a\u0015\f\u0005\u0005\u0005\u0017qYAe\u0003\u001b\fy\r\u0006\u0003\u0004\u0000\r\u0015E#\u0002/\u0004\u0002\u000e\r\u0005bBB\u0006\u0007\u0001\u00071Q\u0002\u0005\b\u0007K\u0019\u0001\u0019AB\u0014\u0011\u001d\tIc\u0001a\u0001\u0003[A3bAAa\u0003\u000f\u001cI)!4\u0002P\u0006\u001211R\u0001!+N,\u0007E]3t_24X\r\u000b#ogB\u0013x\u000e^8d_2t#+Z:pYZ,\u0017\u0006\u0006\u0003\u0004:\r=\u0005bBBI\t\u0001\u00071qH\u0001\be\u0016\fX/Z:uQ\u0011\u0019yia\u001c\u0015\u0011\re2qSBM\u00077Cqa!%\u0006\u0001\u0004\u0019y\u0004C\u0004\u0004\f\u0015\u0001\ra!\u0004\t\u000f\r\u0015R\u00011\u0001\u0004(!\u001a\u0001aa(\u0011\t\r\u00056QU\u0007\u0003\u0007GS1!a\u0018M\u0013\u0011\u00199ka)\u0003\u0019\u0011{gj\u001c;J]\",'/\u001b;")
@DoNotInherit
public abstract class Dns {
    public static DnsExt get(ClassicActorSystemProvider system) {
        return Dns$.MODULE$.get(system);
    }

    public static DnsExt get(ActorSystem system) {
        return Dns$.MODULE$.get(system);
    }

    public static DnsExt createExtension(ExtendedActorSystem system) {
        return Dns$.MODULE$.createExtension(system);
    }

    public static Dns$ lookup() {
        return Dns$.MODULE$.lookup();
    }

    public static Extension apply(ClassicActorSystemProvider system) {
        return Dns$.MODULE$.apply(system);
    }

    public static Extension apply(ActorSystem system) {
        return Dns$.MODULE$.apply(system);
    }

    public Option<Resolved> cached(String name) {
        return None$.MODULE$;
    }

    public Option<Resolved> resolve(String name, ActorSystem system, ActorRef sender) {
        Option<Resolved> ret;
        block0: {
            ret = this.cached(name);
            if (!ret.isEmpty()) break block0;
            IO$.MODULE$.apply(Dns$.MODULE$, system).tell(new Resolve(name), sender);
        }
        return ret;
    }

    public Option<DnsProtocol.Resolved> cached(DnsProtocol.Resolve request) {
        return None$.MODULE$;
    }

    public Option<DnsProtocol.Resolved> resolve(DnsProtocol.Resolve request, ActorSystem system, ActorRef sender) {
        Option<DnsProtocol.Resolved> ret;
        block0: {
            ret = this.cached(request);
            if (!ret.isEmpty()) break block0;
            IO$.MODULE$.apply(Dns$.MODULE$, system).tell(request, sender);
        }
        return ret;
    }

    public static interface Command {
    }

    public static class Resolve
    implements Command,
    ConsistentHashingRouter.ConsistentHashable,
    Product,
    Serializable {
        private final String name;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public String name() {
            return this.name;
        }

        @Override
        public String consistentHashKey() {
            return this.name();
        }

        public Resolve copy(String name) {
            return new Resolve(name);
        }

        public String copy$default$1() {
            return this.name();
        }

        @Override
        public String productPrefix() {
            return "Resolve";
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
                    return this.name();
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
            return x$1 instanceof Resolve;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "name";
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
            if (!(object instanceof Resolve)) return false;
            boolean bl = true;
            if (!bl) return false;
            Resolve resolve = (Resolve)x$1;
            String string = this.name();
            String string2 = resolve.name();
            if (string == null) {
                if (string2 != null) {
                    return false;
                }
            } else if (!string.equals(string2)) return false;
            if (!resolve.canEqual(this)) return false;
            return true;
        }

        public Resolve(String name) {
            this.name = name;
            Product.$init$(this);
        }
    }

    public static class Resolved
    implements Command,
    Product,
    Serializable {
        private final String name;
        private final Seq<Inet4Address> ipv4;
        private final Seq<Inet6Address> ipv6;
        private final Option<InetAddress> addrOption;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public String name() {
            return this.name;
        }

        public Seq<Inet4Address> ipv4() {
            return this.ipv4;
        }

        public Seq<Inet6Address> ipv6() {
            return this.ipv6;
        }

        public Option<InetAddress> addrOption() {
            return this.addrOption;
        }

        public InetAddress addr() throws UnknownHostException {
            Option<InetAddress> option = this.addrOption();
            if (option instanceof Some) {
                Some some = (Some)option;
                InetAddress ipAddress = (InetAddress)some.value();
                return ipAddress;
            }
            if (None$.MODULE$.equals(option)) {
                throw new UnknownHostException(this.name());
            }
            throw new MatchError(option);
        }

        public Resolved copy(String name, Seq<Inet4Address> ipv4, Seq<Inet6Address> ipv6) {
            return new Resolved(name, ipv4, ipv6);
        }

        public String copy$default$1() {
            return this.name();
        }

        public Seq<Inet4Address> copy$default$2() {
            return this.ipv4();
        }

        public Seq<Inet6Address> copy$default$3() {
            return this.ipv6();
        }

        @Override
        public String productPrefix() {
            return "Resolved";
        }

        @Override
        public int productArity() {
            return 3;
        }

        @Override
        public Object productElement(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return this.name();
                }
                case 1: {
                    return this.ipv4();
                }
                case 2: {
                    return this.ipv6();
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
            return x$1 instanceof Resolved;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "name";
                }
                case 1: {
                    return "ipv4";
                }
                case 2: {
                    return "ipv6";
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
            if (!(object instanceof Resolved)) return false;
            boolean bl = true;
            if (!bl) return false;
            Resolved resolved = (Resolved)x$1;
            String string = this.name();
            String string2 = resolved.name();
            if (string == null) {
                if (string2 != null) {
                    return false;
                }
            } else if (!string.equals(string2)) return false;
            Seq<Inet4Address> seq = this.ipv4();
            Seq<Inet4Address> seq2 = resolved.ipv4();
            if (seq == null) {
                if (seq2 != null) {
                    return false;
                }
            } else if (!seq.equals(seq2)) return false;
            Seq<Inet6Address> seq3 = this.ipv6();
            Seq<Inet6Address> seq4 = resolved.ipv6();
            if (seq3 == null) {
                if (seq4 != null) {
                    return false;
                }
            } else if (!seq3.equals(seq4)) return false;
            if (!resolved.canEqual(this)) return false;
            return true;
        }

        public Resolved(String name, Seq<Inet4Address> ipv4, Seq<Inet6Address> ipv6) {
            this.name = name;
            this.ipv4 = ipv4;
            this.ipv6 = ipv6;
            Product.$init$(this);
            this.addrOption = IpVersionSelector$.MODULE$.getInetAddress(ipv4.headOption(), ipv6.headOption());
        }
    }
}

