/*
 * Decompiled with CFR 0.152.
 */
package akka.io.dns;

import akka.actor.NoSerializationVerificationNeeded;
import akka.io.IpVersionSelector$;
import akka.io.dns.AAAARecord;
import akka.io.dns.ARecord;
import akka.io.dns.DnsProtocol$;
import akka.io.dns.ResourceRecord;
import akka.routing.ConsistentHashingRouter;
import akka.util.ccompat.package$JavaConverters$;
import java.io.Serializable;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import scala.Function1;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.Product;
import scala.Some;
import scala.collection.Iterator;
import scala.collection.immutable.Seq;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\r\u001du!\u00027n\u0011\u0003!h!\u0002<n\u0011\u00039\b\"\u0002@\u0002\t\u0003yh!CA\u0001\u0003A\u0005\u0019\u0013EA\u0002\r\u0019\t9!\u0001\"\u0002\n!Q\u0011Q\u0006\u0003\u0003\u0016\u0004%\t!a\f\t\u0015\u0005]BA!E!\u0002\u0013\t\t\u0004\u0003\u0006\u0002:\u0011\u0011)\u001a!C\u0001\u0003_A!\"a\u000f\u0005\u0005#\u0005\u000b\u0011BA\u0019\u0011\u0019qH\u0001\"\u0001\u0002>!I\u0011Q\t\u0003\u0002\u0002\u0013\u0005\u0011q\t\u0005\n\u0003\u001b\"\u0011\u0013!C\u0001\u0003\u001fB\u0011\"!\u001a\u0005#\u0003%\t!a\u0014\t\u0013\u0005\u001dD!!A\u0005B\u0005%\u0004\"CA>\t\u0005\u0005I\u0011AA?\u0011%\t)\tBA\u0001\n\u0003\t9\tC\u0005\u0002\u0014\u0012\t\t\u0011\"\u0011\u0002\u0016\"I\u00111\u0015\u0003\u0002\u0002\u0013\u0005\u0011Q\u0015\u0005\n\u0003S#\u0011\u0011!C!\u0003WC\u0011\"a,\u0005\u0003\u0003%\t%!-\t\u0013\u0005MF!!A\u0005B\u0005U\u0006\"CA\\\t\u0005\u0005I\u0011IA]\u000f%\t9.AA\u0001\u0012\u0003\tINB\u0005\u0002\b\u0005\t\t\u0011#\u0001\u0002\\\"1ap\u0006C\u0001\u0003cD\u0011\"a-\u0018\u0003\u0003%)%!.\t\u0013\u0005Mx#!A\u0005\u0002\u0006U\b\"CA~/E\u0005I\u0011AA(\u0011%\tipFI\u0001\n\u0003\ty\u0005C\u0005\u0002\u0000^\t\t\u0011\"!\u0003\u0002!I!1C\f\u0012\u0002\u0013\u0005\u0011q\n\u0005\n\u0005+9\u0012\u0013!C\u0001\u0003\u001fB\u0011\"!4\u0018\u0003\u0003%I!a4\b\u000f\t]\u0011\u0001#!\u0002D\u001a9\u0011QX\u0001\t\u0002\u0006}\u0006B\u0002@#\t\u0003\t\t\rC\u0005\u0002h\t\n\t\u0011\"\u0011\u0002j!I\u00111\u0010\u0012\u0002\u0002\u0013\u0005\u0011Q\u0010\u0005\n\u0003\u000b\u0013\u0013\u0011!C\u0001\u0003\u000bD\u0011\"a%#\u0003\u0003%\t%!&\t\u0013\u0005\r&%!A\u0005\u0002\u0005%\u0007\"CAXE\u0005\u0005I\u0011IAY\u0011%\t\u0019LIA\u0001\n\u0003\n)\fC\u0005\u0002N\n\n\t\u0011\"\u0003\u0002P\"9!\u0011D\u0001\u0005\u0002\tm\u0001b\u0002B\r\u0003\u0011\u0005!\u0011\u0005\u0005\b\u0005G\tA\u0011\u0001B\u0011\r\u0019\u0011)#\u0001\"\u0003(!Q!1I\u0018\u0003\u0016\u0004%\tA!\u0012\t\u0015\tUsF!E!\u0002\u0013\u00119\u0005\u0003\u0006\u0003X=\u0012)\u001a!C\u0001\u00053B!Ba\u00170\u0005#\u0005\u000b\u0011BA\u0006\u0011\u0019qx\u0006\"\u0001\u0003^!9!QM\u0018\u0005B\t\u001d\u0004\"CA#_\u0005\u0005I\u0011\u0001B5\u0011%\tieLI\u0001\n\u0003\u0011y\u0007C\u0005\u0002f=\n\n\u0011\"\u0001\u0003t!I\u0011qM\u0018\u0002\u0002\u0013\u0005\u0013\u0011\u000e\u0005\n\u0003wz\u0013\u0011!C\u0001\u0003{B\u0011\"!\"0\u0003\u0003%\tAa\u001e\t\u0013\u0005Mu&!A\u0005B\u0005U\u0005\"CAR_\u0005\u0005I\u0011\u0001B>\u0011%\tIkLA\u0001\n\u0003\u0012y\bC\u0005\u00020>\n\t\u0011\"\u0011\u00022\"I\u00111W\u0018\u0002\u0002\u0013\u0005\u0013Q\u0017\u0005\n\u0003o{\u0013\u0011!C!\u0005\u0007;qAa\"\u0002\u0011\u0003\u0011IIB\u0004\u0003&\u0005A\tAa#\t\ry\u001cE\u0011\u0001BG\u0011\u001d\t\u0019p\u0011C\u0001\u0005\u001fC\u0011\"a=D\u0003\u0003%\tIa%\t\u0013\u0005}8)!A\u0005\u0002\ne\u0005\"CAg\u0007\u0006\u0005I\u0011BAh\u0011\u001d\u0011\t+\u0001C\u0001\u0005GCqA!)\u0002\t\u0003\u00119K\u0002\u0004\u0003.\u0006\u0011%q\u0016\u0005\u000b\u0005\u0007Z%Q3A\u0005\u0002\t\u0015\u0003B\u0003B+\u0017\nE\t\u0015!\u0003\u0003H!Q!QX&\u0003\u0016\u0004%\tAa0\t\u0015\tM7J!E!\u0002\u0013\u0011\t\r\u0003\u0006\u0003V.\u0013)\u001a!C\u0001\u0005\u007fC!Ba6L\u0005#\u0005\u000b\u0011\u0002Ba\u0011\u0019q8\n\"\u0001\u0003Z\"9!1]&\u0005\u0002\t\u0015\bb\u0002Bz\u0017\u0012\u0005!Q\u001d\u0005\n\u0005k\\%\u0019!C\u0005\u0005oD\u0001ba\u0002LA\u0003%!\u0011 \u0005\b\u0007\u0013YE\u0011AB\u0006\u0011%\t)eSA\u0001\n\u0003\u00199\u0005C\u0005\u0002N-\u000b\n\u0011\"\u0001\u0003p!I\u0011QM&\u0012\u0002\u0013\u00051q\n\u0005\n\u0007'Z\u0015\u0013!C\u0001\u0007\u001fB\u0011\"a\u001aL\u0003\u0003%\t%!\u001b\t\u0013\u0005m4*!A\u0005\u0002\u0005u\u0004\"CAC\u0017\u0006\u0005I\u0011AB+\u0011%\t\u0019jSA\u0001\n\u0003\n)\nC\u0005\u0002$.\u000b\t\u0011\"\u0001\u0004Z!I\u0011\u0011V&\u0002\u0002\u0013\u00053Q\f\u0005\n\u0003_[\u0015\u0011!C!\u0003cC\u0011\"a-L\u0003\u0003%\t%!.\t\u0013\u0005]6*!A\u0005B\r\u0005taBB3\u0003!\u00051q\r\u0004\b\u0005[\u000b\u0001\u0012AB5\u0011\u0019qh\r\"\u0001\u0004l!9\u00111\u001f4\u0005\u0002\r5\u0004\"CAzM\u0006\u0005I\u0011QB:\u0011%\tyPZA\u0001\n\u0003\u001bY\bC\u0005\u0002N\u001a\f\t\u0011\"\u0003\u0002P\u0006YAI\\:Qe>$xnY8m\u0015\tqw.A\u0002e]NT!\u0001]9\u0002\u0005%|'\"\u0001:\u0002\t\u0005\\7.Y\u0002\u0001!\t)\u0018!D\u0001n\u0005-!en\u001d)s_R|7m\u001c7\u0014\u0005\u0005A\bCA=}\u001b\u0005Q(\"A>\u0002\u000bM\u001c\u0017\r\\1\n\u0005uT(AB!osJ+g-\u0001\u0004=S:LGO\u0010\u000b\u0002i\nY!+Z9vKN$H+\u001f9f'\t\u0019\u00010K\u0002\u0004\t\t\u0012!!\u00139\u0014\u0011\u0011A\u00181BA\b\u0003+\u00012!!\u0004\u0004\u001b\u0005\t\u0001cA=\u0002\u0012%\u0019\u00111\u0003>\u0003\u000fA\u0013x\u000eZ;diB!\u0011qCA\u0014\u001d\u0011\tI\"a\t\u000f\t\u0005m\u0011\u0011E\u0007\u0003\u0003;Q1!a\bt\u0003\u0019a$o\\8u}%\t10C\u0002\u0002&i\fq\u0001]1dW\u0006<W-\u0003\u0003\u0002*\u0005-\"\u0001D*fe&\fG.\u001b>bE2,'bAA\u0013u\u0006!\u0011\u000e\u001d<5+\t\t\t\u0004E\u0002z\u0003gI1!!\u000e{\u0005\u001d\u0011un\u001c7fC:\fQ!\u001b9wi\u0001\nA!\u001b9wm\u0005)\u0011\u000e\u001d<7AQ1\u0011qHA!\u0003\u0007\u00022!!\u0004\u0005\u0011%\ti#\u0003I\u0001\u0002\u0004\t\t\u0004C\u0005\u0002:%\u0001\n\u00111\u0001\u00022\u0005!1m\u001c9z)\u0019\ty$!\u0013\u0002L!I\u0011Q\u0006\u0006\u0011\u0002\u0003\u0007\u0011\u0011\u0007\u0005\n\u0003sQ\u0001\u0013!a\u0001\u0003c\tabY8qs\u0012\"WMZ1vYR$\u0013'\u0006\u0002\u0002R)\"\u0011\u0011GA*W\t\t)\u0006\u0005\u0003\u0002X\u0005\u0005TBAA-\u0015\u0011\tY&!\u0018\u0002\u0013Ut7\r[3dW\u0016$'bAA0u\u0006Q\u0011M\u001c8pi\u0006$\u0018n\u001c8\n\t\u0005\r\u0014\u0011\f\u0002\u0012k:\u001c\u0007.Z2lK\u00124\u0016M]5b]\u000e,\u0017AD2paf$C-\u001a4bk2$HEM\u0001\u000eaJ|G-^2u!J,g-\u001b=\u0016\u0005\u0005-\u0004\u0003BA7\u0003oj!!a\u001c\u000b\t\u0005E\u00141O\u0001\u0005Y\u0006twM\u0003\u0002\u0002v\u0005!!.\u0019<b\u0013\u0011\tI(a\u001c\u0003\rM#(/\u001b8h\u00031\u0001(o\u001c3vGR\f%/\u001b;z+\t\ty\bE\u0002z\u0003\u0003K1!a!{\u0005\rIe\u000e^\u0001\u000faJ|G-^2u\u000b2,W.\u001a8u)\u0011\tI)a$\u0011\u0007e\fY)C\u0002\u0002\u000ej\u00141!\u00118z\u0011%\t\tjDA\u0001\u0002\u0004\ty(A\u0002yIE\nq\u0002\u001d:pIV\u001cG/\u0013;fe\u0006$xN]\u000b\u0003\u0003/\u0003b!!'\u0002 \u0006%UBAAN\u0015\r\tiJ_\u0001\u000bG>dG.Z2uS>t\u0017\u0002BAQ\u00037\u0013\u0001\"\u0013;fe\u0006$xN]\u0001\tG\u0006tW)];bYR!\u0011\u0011GAT\u0011%\t\t*EA\u0001\u0002\u0004\tI)\u0001\nqe>$Wo\u0019;FY\u0016lWM\u001c;OC6,G\u0003BA6\u0003[C\u0011\"!%\u0013\u0003\u0003\u0005\r!a \u0002\u0011!\f7\u000f[\"pI\u0016$\"!a \u0002\u0011Q|7\u000b\u001e:j]\u001e$\"!a\u001b\u0002\r\u0015\fX/\u00197t)\u0011\t\t$a/\t\u0013\u0005EU#!AA\u0002\u0005%%aA*smNA!\u0005_A\u0006\u0003\u001f\t)\u0002\u0006\u0002\u0002DB\u0019\u0011Q\u0002\u0012\u0015\t\u0005%\u0015q\u0019\u0005\n\u0003#3\u0013\u0011!a\u0001\u0003\u007f\"B!!\r\u0002L\"I\u0011\u0011\u0013\u0015\u0002\u0002\u0003\u0007\u0011\u0011R\u0001\roJLG/\u001a*fa2\f7-\u001a\u000b\u0003\u0003#\u0004B!!\u001c\u0002T&!\u0011Q[A8\u0005\u0019y%M[3di\u0006\u0011\u0011\n\u001d\t\u0004\u0003\u001b92#B\f\u0002^\u0006%\bCCAp\u0003K\f\t$!\r\u0002@5\u0011\u0011\u0011\u001d\u0006\u0004\u0003GT\u0018a\u0002:v]RLW.Z\u0005\u0005\u0003O\f\tOA\tBEN$(/Y2u\rVt7\r^5p]J\u0002B!a;\u0002p6\u0011\u0011Q\u001e\u0006\u0004a\u0006M\u0014\u0002BA\u0015\u0003[$\"!!7\u0002\u000b\u0005\u0004\b\u000f\\=\u0015\r\u0005}\u0012q_A}\u0011%\tiC\u0007I\u0001\u0002\u0004\t\t\u0004C\u0005\u0002:i\u0001\n\u00111\u0001\u00022\u0005y\u0011\r\u001d9ms\u0012\"WMZ1vYR$\u0013'A\bbaBd\u0017\u0010\n3fM\u0006,H\u000e\u001e\u00133\u0003\u001d)h.\u00199qYf$BAa\u0001\u0003\u0010A)\u0011P!\u0002\u0003\n%\u0019!q\u0001>\u0003\r=\u0003H/[8o!\u001dI(1BA\u0019\u0003cI1A!\u0004{\u0005\u0019!V\u000f\u001d7fe!I!\u0011C\u000f\u0002\u0002\u0003\u0007\u0011qH\u0001\u0004q\u0012\u0002\u0014a\u0007\u0013mKN\u001c\u0018N\\5uI\u001d\u0014X-\u0019;fe\u0012\"WMZ1vYR$\u0013'A\u000e%Y\u0016\u001c8/\u001b8ji\u0012:'/Z1uKJ$C-\u001a4bk2$HEM\u0001\u0004'J4\u0018!D5q%\u0016\fX/Z:u)f\u0004X\r\u0006\u0004\u0002\f\tu!q\u0004\u0005\b\u0003[a\u0003\u0019AA\u0019\u0011\u001d\tI\u0004\fa\u0001\u0003c!\"!a\u0003\u0002\u001dM\u0014hOU3rk\u0016\u001cH\u000fV=qK\n9!+Z:pYZ,7\u0003C\u0018y\u0005S\ty!!\u0006\u0011\t\t-\"Q\b\b\u0005\u0005[\u00119D\u0004\u0003\u00030\tMb\u0002BA\u000e\u0005cI\u0011A]\u0005\u0004\u0005k\t\u0018a\u0002:pkRLgnZ\u0005\u0005\u0005s\u0011Y$A\fD_:\u001c\u0018n\u001d;f]RD\u0015m\u001d5j]\u001e\u0014v.\u001e;fe*\u0019!QG9\n\t\t}\"\u0011\t\u0002\u0013\u0007>t7/[:uK:$\b*Y:iC\ndWM\u0003\u0003\u0003:\tm\u0012\u0001\u00028b[\u0016,\"Aa\u0012\u0011\t\t%#\u0011\u000b\b\u0005\u0005\u0017\u0012i\u0005E\u0002\u0002\u001ciL1Aa\u0014{\u0003\u0019\u0001&/\u001a3fM&!\u0011\u0011\u0010B*\u0015\r\u0011yE_\u0001\u0006]\u0006lW\rI\u0001\fe\u0016\fX/Z:u)f\u0004X-\u0006\u0002\u0002\f\u0005a!/Z9vKN$H+\u001f9fAQ1!q\fB1\u0005G\u00022!!\u00040\u0011\u001d\u0011\u0019\u0005\u000ea\u0001\u0005\u000fBqAa\u00165\u0001\u0004\tY!A\td_:\u001c\u0018n\u001d;f]RD\u0015m\u001d5LKf,\"!!#\u0015\r\t}#1\u000eB7\u0011%\u0011\u0019E\u000eI\u0001\u0002\u0004\u00119\u0005C\u0005\u0003XY\u0002\n\u00111\u0001\u0002\fU\u0011!\u0011\u000f\u0016\u0005\u0005\u000f\n\u0019&\u0006\u0002\u0003v)\"\u00111BA*)\u0011\tII!\u001f\t\u0013\u0005E5(!AA\u0002\u0005}D\u0003BA\u0019\u0005{B\u0011\"!%>\u0003\u0003\u0005\r!!#\u0015\t\u0005-$\u0011\u0011\u0005\n\u0003#s\u0014\u0011!a\u0001\u0003\u007f\"B!!\r\u0003\u0006\"I\u0011\u0011S!\u0002\u0002\u0003\u0007\u0011\u0011R\u0001\b%\u0016\u001cx\u000e\u001c<f!\r\tiaQ\n\u0005\u0007b\fI\u000f\u0006\u0002\u0003\nR!!q\fBI\u0011\u001d\u0011\u0019%\u0012a\u0001\u0005\u000f\"bAa\u0018\u0003\u0016\n]\u0005b\u0002B\"\r\u0002\u0007!q\t\u0005\b\u0005/2\u0005\u0019AA\u0006)\u0011\u0011YJa(\u0011\u000be\u0014)A!(\u0011\u000fe\u0014YAa\u0012\u0002\f!I!\u0011C$\u0002\u0002\u0003\u0007!qL\u0001\be\u0016\u001cx\u000e\u001c<f)\u0011\u0011yF!*\t\u000f\t\r\u0013\n1\u0001\u0003HQ1!q\fBU\u0005WCqAa\u0011K\u0001\u0004\u00119\u0005C\u0004\u0003X)\u0003\r!a\u0003\u0003\u0011I+7o\u001c7wK\u0012\u001c\u0002b\u0013=\u00032\u0006=\u0011Q\u0003\t\u0005\u0005g\u0013I,\u0004\u0002\u00036*\u0019!qW9\u0002\u000b\u0005\u001cGo\u001c:\n\t\tm&Q\u0017\u0002\"\u001d>\u001cVM]5bY&T\u0018\r^5p]Z+'/\u001b4jG\u0006$\u0018n\u001c8OK\u0016$W\rZ\u0001\be\u0016\u001cwN\u001d3t+\t\u0011\t\r\u0005\u0004\u0003D\n%'QZ\u0007\u0003\u0005\u000bTAAa2\u0002\u001c\u0006I\u0011.\\7vi\u0006\u0014G.Z\u0005\u0005\u0005\u0017\u0014)MA\u0002TKF\u00042!\u001eBh\u0013\r\u0011\t.\u001c\u0002\u000f%\u0016\u001cx.\u001e:dKJ+7m\u001c:e\u0003!\u0011XmY8sIN\u0004\u0013!E1eI&$\u0018n\u001c8bYJ+7m\u001c:eg\u0006\u0011\u0012\r\u001a3ji&|g.\u00197SK\u000e|'\u000fZ:!)!\u0011YN!8\u0003`\n\u0005\bcAA\u0007\u0017\"9!1\t*A\u0002\t\u001d\u0003b\u0002B_%\u0002\u0007!\u0011\u0019\u0005\b\u0005+\u0014\u0006\u0019\u0001Ba\u0003)9W\r\u001e*fG>\u0014Hm\u001d\u000b\u0003\u0005O\u0004bA!;\u0003p\n5WB\u0001Bv\u0015\u0011\u0011i/a\u001d\u0002\tU$\u0018\u000e\\\u0005\u0005\u0005c\u0014YO\u0001\u0003MSN$\u0018\u0001F4fi\u0006#G-\u001b;j_:\fGNU3d_J$7/\u0001\u0005`C\u0012$'/Z:t+\t\u0011I\u0010E\u0003z\u0005\u000b\u0011Y\u0010\u0005\u0003\u0003~\u000e\rQB\u0001B\u0000\u0015\u0011\u0019\t!a\u001d\u0002\u00079,G/\u0003\u0003\u0004\u0006\t}(aC%oKR\fE\r\u001a:fgN\f\u0011bX1eIJ,7o\u001d\u0011\u0002\u000f\u0005$GM]3tgR\u0011!1 \u0015\u0006/\u000e=11\u0004\t\u0006s\u000eE1QC\u0005\u0004\u0007'Q(A\u0002;ie><8\u000f\u0005\u0003\u0003~\u000e]\u0011\u0002BB\r\u0005\u007f\u0014A#\u00168l]><h\u000eS8ti\u0016C8-\u001a9uS>t\u0017g\u0002\u0010\u0003H\ru1QI\u0019\nG\r}1QEB\u001e\u0005')BA!\u0012\u0004\"\u0011911\u0005\u0001C\u0002\r-\"!\u0001+\n\t\tM1q\u0005\u0006\u0004\u0007SQ\u0018A\u0002;ie><8/\u0005\u0003\u0004.\rM\u0002cA=\u00040%\u00191\u0011\u0007>\u0003\u000f9{G\u000f[5oOB!1QGB\u001c\u001d\rI\u00181E\u0005\u0005\u0007s\tYCA\u0005UQJ|w/\u00192mKFJ1e!\u0010\u0004@\r\u00053\u0011\u0006\b\u0004s\u000e}\u0012bAB\u0015uF*!%\u001f>\u0004D\t)1oY1mCF\u001aae!\u0006\u0015\u0011\tm7\u0011JB&\u0007\u001bB\u0011Ba\u0011Y!\u0003\u0005\rAa\u0012\t\u0013\tu\u0006\f%AA\u0002\t\u0005\u0007\"\u0003Bk1B\u0005\t\u0019\u0001Ba+\t\u0019\tF\u000b\u0003\u0003B\u0006M\u0013AD2paf$C-\u001a4bk2$He\r\u000b\u0005\u0003\u0013\u001b9\u0006C\u0005\u0002\u0012z\u000b\t\u00111\u0001\u0002\u0000Q!\u0011\u0011GB.\u0011%\t\t\nYA\u0001\u0002\u0004\tI\t\u0006\u0003\u0002l\r}\u0003\"CAIC\u0006\u0005\t\u0019AA@)\u0011\t\tda\u0019\t\u0013\u0005EE-!AA\u0002\u0005%\u0015\u0001\u0003*fg>dg/\u001a3\u0011\u0007\u00055am\u0005\u0003gq\u0006%HCAB4)\u0019\u0011Yna\u001c\u0004r!9!1\t5A\u0002\t\u001d\u0003b\u0002B_Q\u0002\u0007!\u0011\u0019\u000b\t\u00057\u001c)ha\u001e\u0004z!9!1I5A\u0002\t\u001d\u0003b\u0002B_S\u0002\u0007!\u0011\u0019\u0005\b\u0005+L\u0007\u0019\u0001Ba)\u0011\u0019ih!\"\u0011\u000be\u0014)aa \u0011\u0013e\u001c\tIa\u0012\u0003B\n\u0005\u0017bABBu\n1A+\u001e9mKNB\u0011B!\u0005k\u0003\u0003\u0005\rAa7")
public final class DnsProtocol {
    public static Resolve resolve(String name, RequestType requestType) {
        return DnsProtocol$.MODULE$.resolve(name, requestType);
    }

    public static Resolve resolve(String name) {
        return DnsProtocol$.MODULE$.resolve(name);
    }

    public static RequestType srvRequestType() {
        return DnsProtocol$.MODULE$.srvRequestType();
    }

    public static RequestType ipRequestType() {
        return DnsProtocol$.MODULE$.ipRequestType();
    }

    public static RequestType ipRequestType(boolean ipv4, boolean ipv6) {
        return DnsProtocol$.MODULE$.ipRequestType(ipv4, ipv6);
    }

    public static final class Ip
    implements RequestType,
    Product,
    Serializable {
        private final boolean ipv4;
        private final boolean ipv6;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public boolean ipv4() {
            return this.ipv4;
        }

        public boolean ipv6() {
            return this.ipv6;
        }

        public Ip copy(boolean ipv4, boolean ipv6) {
            return new Ip(ipv4, ipv6);
        }

        public boolean copy$default$1() {
            return this.ipv4();
        }

        public boolean copy$default$2() {
            return this.ipv6();
        }

        @Override
        public String productPrefix() {
            return "Ip";
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
                    return BoxesRunTime.boxToBoolean(this.ipv4());
                }
                case 1: {
                    return BoxesRunTime.boxToBoolean(this.ipv6());
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
            return x$1 instanceof Ip;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "ipv4";
                }
                case 1: {
                    return "ipv6";
                }
            }
            return (String)Statics.ioobe(x$1);
        }

        public int hashCode() {
            int n = -889275714;
            n = Statics.mix(n, this.productPrefix().hashCode());
            n = Statics.mix(n, this.ipv4() ? 1231 : 1237);
            n = Statics.mix(n, this.ipv6() ? 1231 : 1237);
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
            if (!(object instanceof Ip)) return false;
            boolean bl = true;
            if (!bl) return false;
            Ip ip2 = (Ip)x$1;
            if (this.ipv4() != ip2.ipv4()) return false;
            if (this.ipv6() != ip2.ipv6()) return false;
            return true;
        }

        public Ip(boolean ipv4, boolean ipv6) {
            this.ipv4 = ipv4;
            this.ipv6 = ipv6;
            Product.$init$(this);
        }
    }

    public static interface RequestType {
    }

    public static final class Resolve
    implements ConsistentHashingRouter.ConsistentHashable,
    Product,
    Serializable {
        private final String name;
        private final RequestType requestType;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public String name() {
            return this.name;
        }

        public RequestType requestType() {
            return this.requestType;
        }

        @Override
        public Object consistentHashKey() {
            return this.name();
        }

        public Resolve copy(String name, RequestType requestType) {
            return new Resolve(name, requestType);
        }

        public String copy$default$1() {
            return this.name();
        }

        public RequestType copy$default$2() {
            return this.requestType();
        }

        @Override
        public String productPrefix() {
            return "Resolve";
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
                    return this.name();
                }
                case 1: {
                    return this.requestType();
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
                case 1: {
                    return "requestType";
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
            RequestType requestType = this.requestType();
            RequestType requestType2 = resolve.requestType();
            if (requestType == null) {
                if (requestType2 == null) return true;
                return false;
            } else {
                if (!requestType.equals(requestType2)) return false;
                return true;
            }
        }

        public Resolve(String name, RequestType requestType) {
            this.name = name;
            this.requestType = requestType;
            Product.$init$(this);
        }
    }

    public static final class Resolved
    implements NoSerializationVerificationNeeded,
    Product,
    Serializable {
        private final String name;
        private final Seq<ResourceRecord> records;
        private final Seq<ResourceRecord> additionalRecords;
        private final Option<InetAddress> _address;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public String name() {
            return this.name;
        }

        public Seq<ResourceRecord> records() {
            return this.records;
        }

        public Seq<ResourceRecord> additionalRecords() {
            return this.additionalRecords;
        }

        public List<ResourceRecord> getRecords() {
            return package$JavaConverters$.MODULE$.SeqHasAsJava(this.records()).asJava();
        }

        public List<ResourceRecord> getAdditionalRecords() {
            return package$JavaConverters$.MODULE$.SeqHasAsJava(this.additionalRecords()).asJava();
        }

        private Option<InetAddress> _address() {
            return this._address;
        }

        public InetAddress address() throws UnknownHostException {
            Option<InetAddress> option = this._address();
            if (None$.MODULE$.equals(option)) {
                throw new UnknownHostException(this.name());
            }
            if (option instanceof Some) {
                Some some = (Some)option;
                InetAddress ipAddress = (InetAddress)some.value();
                return ipAddress;
            }
            throw new MatchError(option);
        }

        public Resolved copy(String name, Seq<ResourceRecord> records, Seq<ResourceRecord> additionalRecords) {
            return new Resolved(name, records, additionalRecords);
        }

        public String copy$default$1() {
            return this.name();
        }

        public Seq<ResourceRecord> copy$default$2() {
            return this.records();
        }

        public Seq<ResourceRecord> copy$default$3() {
            return this.additionalRecords();
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
                    return this.records();
                }
                case 2: {
                    return this.additionalRecords();
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
                    return "records";
                }
                case 2: {
                    return "additionalRecords";
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
            Seq<ResourceRecord> seq = this.records();
            Seq<ResourceRecord> seq2 = resolved.records();
            if (seq == null) {
                if (seq2 != null) {
                    return false;
                }
            } else if (!seq.equals(seq2)) return false;
            Seq<ResourceRecord> seq3 = this.additionalRecords();
            Seq<ResourceRecord> seq4 = resolved.additionalRecords();
            if (seq3 == null) {
                if (seq4 == null) return true;
                return false;
            } else {
                if (!seq3.equals(seq4)) return false;
                return true;
            }
        }

        public Resolved(String name, Seq<ResourceRecord> records, Seq<ResourceRecord> additionalRecords) {
            this.name = name;
            this.records = records;
            this.additionalRecords = additionalRecords;
            Product.$init$(this);
            Option<Inet4Address> ipv4 = records.collectFirst(new Serializable(null){
                private static final long serialVersionUID = 0L;

                public final <A1 extends ResourceRecord, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                    ARecord aRecord;
                    InetAddress ip2;
                    A1 A1 = x1;
                    if (A1 instanceof ARecord && (ip2 = (aRecord = (ARecord)A1).ip()) instanceof Inet4Address) {
                        Inet4Address inet4Address = (Inet4Address)ip2;
                        return (B1)inet4Address;
                    }
                    return function1.apply(x1);
                }

                public final boolean isDefinedAt(ResourceRecord x1) {
                    ARecord aRecord;
                    InetAddress ip2;
                    ResourceRecord resourceRecord = x1;
                    return resourceRecord instanceof ARecord && (ip2 = (aRecord = (ARecord)resourceRecord).ip()) instanceof Inet4Address;
                }
            });
            Option<Inet6Address> ipv6 = records.collectFirst(new Serializable(null){
                private static final long serialVersionUID = 0L;

                public final <A1 extends ResourceRecord, B1> B1 applyOrElse(A1 x2, Function1<A1, B1> function1) {
                    A1 A1 = x2;
                    if (A1 instanceof AAAARecord) {
                        AAAARecord aAAARecord = (AAAARecord)A1;
                        Inet6Address ip2 = aAAARecord.ip();
                        return (B1)ip2;
                    }
                    return function1.apply(x2);
                }

                public final boolean isDefinedAt(ResourceRecord x2) {
                    ResourceRecord resourceRecord = x2;
                    return resourceRecord instanceof AAAARecord;
                }
            });
            this._address = IpVersionSelector$.MODULE$.getInetAddress(ipv4, ipv6);
        }
    }
}

