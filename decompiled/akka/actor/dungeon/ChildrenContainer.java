/*
 * Decompiled with CFR 0.152.
 */
package akka.actor.dungeon;

import akka.actor.ActorRef;
import akka.actor.ChildNameReserved$;
import akka.actor.ChildRestartStats;
import akka.actor.ChildStats;
import akka.actor.InvalidActorNameException;
import akka.actor.dungeon.ChildrenContainer$NormalChildrenContainer$;
import akka.actor.dungeon.ChildrenContainer$TerminatedChildrenContainer$;
import akka.actor.dungeon.ChildrenContainer$Termination$;
import akka.actor.dungeon.ChildrenContainer$UserRequest$;
import akka.util.Collections;
import akka.util.Collections$EmptyImmutableSeq$;
import java.io.Serializable;
import scala.None$;
import scala.Option;
import scala.Predef$;
import scala.Product;
import scala.Some;
import scala.collection.Iterator;
import scala.collection.immutable.Iterable;
import scala.collection.immutable.Map;
import scala.collection.immutable.Seq;
import scala.collection.immutable.Set;
import scala.collection.immutable.TreeMap;
import scala.collection.immutable.TreeMap$;
import scala.math.Ordering;
import scala.math.Ordering$String$;
import scala.reflect.ScalaSignature;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\u0011Eg\u0001DA/\u0003?\u0002\n1!\u0001\u0002h\u0005-\u0004bBA=\u0001\u0011\u0005\u0011Q\u0010\u0005\b\u0003\u000b\u0003a\u0011AAD\u0011\u001d\t\u0019\f\u0001D\u0001\u0003kCq!!1\u0001\r\u0003\t\u0019\rC\u0004\u0002T\u00021\t!!6\t\u000f\u0005m\u0007A\"\u0001\u0002^\"9\u0011\u0011\u0016\u0001\u0007\u0002\u0005=\bbBAz\u0001\u0019\u0005\u0011Q\u001f\u0005\b\u0003s\u0004a\u0011AA~\u0011\u001d\ty\u0010\u0001D\u0001\u0005\u0003AqA!\u0002\u0001\t\u0003\u00119\u0001C\u0004\u0003\u0010\u0001!\tAa\u0002\b\u0015\tE\u0011q\fE\u0001\u0003O\u0012\u0019B\u0002\u0006\u0002^\u0005}\u0003\u0012AA4\u0005+AqAa\u0006\u000f\t\u0003\u0011IBB\u0005\u0003\u001c9\u0001\n1%\t\u0003\u001e\u001d91\u0011\u0002\b\t\u0002\n}ha\u0002B}\u001d!\u0005%1 \u0005\b\u0005/\u0011B\u0011\u0001B\u007f\u0011%\u0011iEEA\u0001\n\u0003\u0012y\u0005C\u0005\u0003`I\t\t\u0011\"\u0001\u0003b!I!\u0011\u000e\n\u0002\u0002\u0013\u00051\u0011\u0001\u0005\n\u0005o\u0012\u0012\u0011!C!\u0005sB\u0011Ba!\u0013\u0003\u0003%\ta!\u0002\t\u0013\t=%#!A\u0005B\tE\u0005\"\u0003BJ%\u0005\u0005I\u0011\tBK\u0011%\u0011yOEA\u0001\n\u0013\u0011\tP\u0002\u0004\u0003\u001e:\u0011%q\u0014\u0005\u000b\u0005Cc\"Q3A\u0005\u0002\t\r\u0006B\u0003BV9\tE\t\u0015!\u0003\u0003&\"9!q\u0003\u000f\u0005\u0002\t5\u0006\"\u0003B&9\u0005\u0005I\u0011\u0001BZ\u0011%\u00119\fHI\u0001\n\u0003\u0011I\fC\u0005\u0003Nq\t\t\u0011\"\u0011\u0003P!I!q\f\u000f\u0002\u0002\u0013\u0005!\u0011\r\u0005\n\u0005Sb\u0012\u0011!C\u0001\u0005\u001fD\u0011Ba\u001e\u001d\u0003\u0003%\tE!\u001f\t\u0013\t\rE$!A\u0005\u0002\tM\u0007\"\u0003BE9\u0005\u0005I\u0011\tBl\u0011%\u0011y\tHA\u0001\n\u0003\u0012\t\nC\u0005\u0003\u0014r\t\t\u0011\"\u0011\u0003\u0016\"I!q\u0013\u000f\u0002\u0002\u0013\u0005#1\\\u0004\n\u0007\u0017q\u0011\u0011!E\u0001\u0007\u001b1\u0011B!(\u000f\u0003\u0003E\taa\u0004\t\u000f\t]A\u0006\"\u0001\u0004(!I!1\u0013\u0017\u0002\u0002\u0013\u0015#Q\u0013\u0005\n\u0007Sa\u0013\u0011!CA\u0007WA\u0011ba\f-\u0003\u0003%\ti!\r\t\u0013\t=H&!A\u0005\n\tEhA\u0002B\u0011\u001d\t\u0013\u0019\u0003C\u0004\u0003\u0018I\"\tAa\u0012\t\u0013\t-#'!A\u0005\u0002\t\u001d\u0003\"\u0003B'e\u0005\u0005I\u0011\tB(\u0011%\u0011yFMA\u0001\n\u0003\u0011\t\u0007C\u0005\u0003jI\n\t\u0011\"\u0001\u0003l!I!q\u000f\u001a\u0002\u0002\u0013\u0005#\u0011\u0010\u0005\n\u0005\u0007\u0013\u0014\u0011!C\u0001\u0005\u000bC\u0011B!#3\u0003\u0003%\tEa#\t\u0013\t=%'!A\u0005B\tE\u0005\"\u0003BJe\u0005\u0005I\u0011\tBK\u0011%\u00119JMA\u0001\n\u0003\u0012IjB\u0005\u0004:9\t\t\u0011#\u0001\u0004<\u0019I!\u0011\u0005\b\u0002\u0002#\u00051Q\b\u0005\b\u0005/yD\u0011AB#\u0011%\u0011\u0019jPA\u0001\n\u000b\u0012)\nC\u0005\u0004*}\n\t\u0011\"!\u0003H!I1qF \u0002\u0002\u0013\u00055q\t\u0005\n\u0005_|\u0014\u0011!C\u0005\u0005c<qaa\u0013\u000f\u0011\u0003\u0013)OB\u0004\u0003`:A\tI!9\t\u000f\t]a\t\"\u0001\u0003d\"I!Q\n$\u0002\u0002\u0013\u0005#q\n\u0005\n\u0005?2\u0015\u0011!C\u0001\u0005CB\u0011B!\u001bG\u0003\u0003%\tAa:\t\u0013\t]d)!A\u0005B\te\u0004\"\u0003BB\r\u0006\u0005I\u0011\u0001Bv\u0011%\u0011yIRA\u0001\n\u0003\u0012\t\nC\u0005\u0003\u0014\u001a\u000b\t\u0011\"\u0011\u0003\u0016\"I!q\u001e$\u0002\u0002\u0013%!\u0011\u001f\u0004\u0007\u0007\u001br\u0001aa\u0014\t\u0015\u0005%\u0006K!A!\u0002\u0013\u0019Y\u0007C\u0004\u0003\u0018A#\taa!\t\u000f\r%\u0002\u000b\"\u0012\u0004\u0012\"91q\u0013)\u0005F\re\u0005bBBO!\u0012\u00153q\u0014\u0004\u0007\u0007Gs\u0001a!*\t\u0015\u0005%fK!A!\u0002\u0013\u0019I\u000bC\u0004\u0003\u0018Y#\taa-\t\u000f\r%b\u000b\"\u0012\u0004B\"91q\u0013,\u0005F\r\u0015\u0007bBBO-\u0012\u00153q\u0014\u0004\n\u0005Wq\u0001\u0013aI\u0001\u0005[1\u0011b!3\u000f!\u0003\r\taa3\t\u000f\u0005eT\f\"\u0001\u0002~!I1QZ/C\u0002\u0013\u00051q\u001a\u0005\b\u0003\u000bkF\u0011IBl\u0011\u001d\t\u0019,\u0018C!\u0007;Dq!!1^\t\u0003\u001a\t\u000fC\u0004\u0002Tv#\te!:\t\u000f\u0005mW\f\"\u0011\u0002^\"9\u0011\u0011V/\u0005B\u0005=\bbBAz;\u0012\u00053\u0011\u001e\u0005\b\u0003slF\u0011IBw\u0011\u001d\ty0\u0018C!\u0007c<qa!>\u000f\u0011\u0003\u00199PB\u0004\u0004J:A\ta!?\t\u000f\t]!\u000e\"\u0001\u0004~\"9!1\u00136\u0005B\tUuaBB\u0000\u001d!\u0005A\u0011\u0001\u0004\b\t\u0007q\u0001\u0012\u0001C\u0003\u0011\u001d\u00119B\u001cC\u0001\t\u000fAq!!\"o\t\u0003\"I\u0001C\u0004\u0002z:$\t\u0005b\u0004\t\u000f\t\u0015a\u000e\"\u0011\u0003\b!9!q\u00028\u0005B\t\u001d\u0001b\u0002BJ]\u0012\u0005#Q\u0013\u0004\u0007\t'q\u0001\u0001\"\u0006\t\u0015\rUUO!b\u0001\n\u0003\u0019y\r\u0003\u0006\u0005\u0018U\u0014\t\u0011)A\u0005\u0007#DqAa\u0006v\t\u0003!I\u0002C\u0004\u0002\u0006V$\t\u0005b\b\t\u000f\u0005MV\u000f\"\u0011\u0005&!9\u0011\u0011Y;\u0005B\u0011%\u0002bBAjk\u0012\u0005CQ\u0006\u0005\b\u00037,H\u0011IAo\u0011\u001d\tI+\u001eC!\u0003_Dq!a=v\t\u0003\"\t\u0004C\u0004\u0002zV$\t\u0005\"\u000e\t\u000f\u0005}X\u000f\"\u0011\u0005:!9!1S;\u0005B\tUua\u0002C\u001f\u001d!\u0005Aq\b\u0004\b\t'q\u0001\u0012\u0001C!\u0011!\u00119\"!\u0003\u0005\u0002\u0011\r\u0003\u0002CB\u0015\u0003\u0013!\t\u0001\"\u0012\u0007\r\u0011%cB\u0011C&\u0011-\u0019)*a\u0004\u0003\u0016\u0004%\taa4\t\u0017\u0011]\u0011q\u0002B\tB\u0003%1\u0011\u001b\u0005\f\t\u001b\nyA!f\u0001\n\u0003!y\u0005C\u0006\u0005X\u0005=!\u0011#Q\u0001\n\u0011E\u0003b\u0003C-\u0003\u001f\u0011)\u001a!C\u0001\t7B1\u0002\"\u0018\u0002\u0010\tE\t\u0015!\u0003\u0003&!A!qCA\b\t\u0003!y\u0006\u0003\u0005\u0002\u0006\u0006=A\u0011\tC5\u0011!\t\u0019,a\u0004\u0005B\u0011=\u0004\u0002CAa\u0003\u001f!\t\u0005b\u001d\t\u0011\u0005M\u0017q\u0002C!\toB\u0001\"a7\u0002\u0010\u0011\u0005\u0013Q\u001c\u0005\t\u0003S\u000by\u0001\"\u0011\u0002p\"A\u00111_A\b\t\u0003\"Y\b\u0003\u0005\u0002z\u0006=A\u0011\tC@\u0011!\ty0a\u0004\u0005B\u0011\r\u0005\u0002\u0003B\u0003\u0003\u001f!\tEa\u0002\t\u0011\t=\u0011q\u0002C!\u0005\u000fA\u0001Ba%\u0002\u0010\u0011\u0005#Q\u0013\u0005\u000b\u0005\u0017\ny!!A\u0005\u0002\u0011\u001d\u0005B\u0003B\\\u0003\u001f\t\n\u0011\"\u0001\u0005\u0010\"QA1SA\b#\u0003%\t\u0001\"&\t\u0015\u0011e\u0015qBI\u0001\n\u0003!Y\n\u0003\u0006\u0003N\u0005=\u0011\u0011!C!\u0005\u001fB!Ba\u0018\u0002\u0010\u0005\u0005I\u0011\u0001B1\u0011)\u0011I'a\u0004\u0002\u0002\u0013\u0005Aq\u0014\u0005\u000b\u0005o\ny!!A\u0005B\te\u0004B\u0003BB\u0003\u001f\t\t\u0011\"\u0001\u0005$\"Q!\u0011RA\b\u0003\u0003%\t\u0005b*\t\u0015\t=\u0015qBA\u0001\n\u0003\u0012\t\n\u0003\u0006\u0003\u0018\u0006=\u0011\u0011!C!\tW;\u0011\u0002b,\u000f\u0003\u0003E\t\u0001\"-\u0007\u0013\u0011%c\"!A\t\u0002\u0011M\u0006\u0002\u0003B\f\u0003#\"\t\u0001b/\t\u0015\tM\u0015\u0011KA\u0001\n\u000b\u0012)\n\u0003\u0006\u0004*\u0005E\u0013\u0011!CA\t{C!ba\f\u0002R\u0005\u0005I\u0011\u0011Cc\u0011)\u0011y/!\u0015\u0002\u0002\u0013%!\u0011\u001f\u0002\u0012\u0007\"LG\u000e\u001a:f]\u000e{g\u000e^1j]\u0016\u0014(\u0002BA1\u0003G\nq\u0001Z;oO\u0016|gN\u0003\u0003\u0002f\u0005\u001d\u0014!B1di>\u0014(BAA5\u0003\u0011\t7n[1\u0014\u0007\u0001\ti\u0007\u0005\u0003\u0002p\u0005UTBAA9\u0015\t\t\u0019(A\u0003tG\u0006d\u0017-\u0003\u0003\u0002x\u0005E$AB!osJ+g-\u0001\u0004%S:LG\u000fJ\u0002\u0001)\t\ty\b\u0005\u0003\u0002p\u0005\u0005\u0015\u0002BAB\u0003c\u0012A!\u00168ji\u0006\u0019\u0011\r\u001a3\u0015\r\u0005%\u0015QRAT!\r\tY\tA\u0007\u0003\u0003?Bq!a$\u0003\u0001\u0004\t\t*\u0001\u0003oC6,\u0007\u0003BAJ\u0003CsA!!&\u0002\u001eB!\u0011qSA9\u001b\t\tIJ\u0003\u0003\u0002\u001c\u0006m\u0014A\u0002\u001fs_>$h(\u0003\u0003\u0002 \u0006E\u0014A\u0002)sK\u0012,g-\u0003\u0003\u0002$\u0006\u0015&AB*ue&twM\u0003\u0003\u0002 \u0006E\u0004bBAU\u0005\u0001\u0007\u00111V\u0001\u0006gR\fGo\u001d\t\u0005\u0003[\u000by+\u0004\u0002\u0002d%!\u0011\u0011WA2\u0005E\u0019\u0005.\u001b7e%\u0016\u001cH/\u0019:u'R\fGo]\u0001\u0007e\u0016lwN^3\u0015\t\u0005%\u0015q\u0017\u0005\b\u0003s\u001b\u0001\u0019AA^\u0003\u0015\u0019\u0007.\u001b7e!\u0011\ti+!0\n\t\u0005}\u00161\r\u0002\t\u0003\u000e$xN\u001d*fM\u0006Iq-\u001a;Cs:\u000bW.\u001a\u000b\u0005\u0003\u000b\f\t\u000e\u0005\u0004\u0002p\u0005\u001d\u00171Z\u0005\u0005\u0003\u0013\f\tH\u0001\u0004PaRLwN\u001c\t\u0005\u0003[\u000bi-\u0003\u0003\u0002P\u0006\r$AC\"iS2$7\u000b^1ug\"9\u0011q\u0012\u0003A\u0002\u0005E\u0015\u0001C4fi\nK(+\u001a4\u0015\t\u0005]\u0017\u0011\u001c\t\u0007\u0003_\n9-a+\t\u000f\u0005\u0015T\u00011\u0001\u0002<\u0006A1\r[5mIJ,g.\u0006\u0002\u0002`B1\u0011\u0011]Av\u0003wk!!a9\u000b\t\u0005\u0015\u0018q]\u0001\nS6lW\u000f^1cY\u0016TA!!;\u0002r\u0005Q1m\u001c7mK\u000e$\u0018n\u001c8\n\t\u00055\u00181\u001d\u0002\t\u0013R,'/\u00192mKV\u0011\u0011\u0011\u001f\t\u0007\u0003C\fY/a+\u0002\u0011MD\u0017\r\u001c7ES\u0016$B!!#\u0002x\"9\u0011Q\r\u0005A\u0002\u0005m\u0016a\u0002:fg\u0016\u0014h/\u001a\u000b\u0005\u0003\u0013\u000bi\u0010C\u0004\u0002\u0010&\u0001\r!!%\u0002\u0013Ut'/Z:feZ,G\u0003BAE\u0005\u0007Aq!a$\u000b\u0001\u0004\t\t*A\u0007jgR+'/\\5oCRLgnZ\u000b\u0003\u0005\u0013\u0001B!a\u001c\u0003\f%!!QBA9\u0005\u001d\u0011un\u001c7fC:\f\u0001\"[:O_Jl\u0017\r\\\u0001\u0012\u0007\"LG\u000e\u001a:f]\u000e{g\u000e^1j]\u0016\u0014\bcAAF\u001dM\u0019a\"!\u001c\u0002\rqJg.\u001b;?)\t\u0011\u0019BA\u0007TkN\u0004XM\u001c3SK\u0006\u001cxN\\\n\u0004!\u00055\u0014&\u0002\t39\u0019\u0013\"\u0001C\"sK\u0006$\u0018n\u001c8\u0014\u0017I\niG!\n\u0003*\t=\"Q\u0007\t\u0004\u0005O\u0001R\"\u0001\b\u0011\u0007\t\u001dBL\u0001\nXC&$\u0018N\\4G_J\u001c\u0005.\u001b7ee\u0016t7c\u0001/\u0002nA!\u0011q\u000eB\u0019\u0013\u0011\u0011\u0019$!\u001d\u0003\u000fA\u0013x\u000eZ;diB!!q\u0007B!\u001d\u0011\u0011ID!\u0010\u000f\t\u0005]%1H\u0005\u0003\u0003gJAAa\u0010\u0002r\u00059\u0001/Y2lC\u001e,\u0017\u0002\u0002B\"\u0005\u000b\u0012AbU3sS\u0006d\u0017N_1cY\u0016TAAa\u0010\u0002rQ\u0011!\u0011\n\t\u0004\u0005O\u0011\u0014\u0001B2paf\fQ\u0002\u001d:pIV\u001cG\u000f\u0015:fM&DXC\u0001B)!\u0011\u0011\u0019F!\u0018\u000e\u0005\tU#\u0002\u0002B,\u00053\nA\u0001\\1oO*\u0011!1L\u0001\u0005U\u00064\u0018-\u0003\u0003\u0002$\nU\u0013\u0001\u00049s_\u0012,8\r^!sSRLXC\u0001B2!\u0011\tyG!\u001a\n\t\t\u001d\u0014\u0011\u000f\u0002\u0004\u0013:$\u0018A\u00049s_\u0012,8\r^#mK6,g\u000e\u001e\u000b\u0005\u0005[\u0012\u0019\b\u0005\u0003\u0002p\t=\u0014\u0002\u0002B9\u0003c\u00121!\u00118z\u0011%\u0011)hNA\u0001\u0002\u0004\u0011\u0019'A\u0002yIE\nq\u0002\u001d:pIV\u001cG/\u0013;fe\u0006$xN]\u000b\u0003\u0005w\u0002bA! \u0003\u0000\t5TBAAt\u0013\u0011\u0011\t)a:\u0003\u0011%#XM]1u_J\f\u0001bY1o\u000bF,\u0018\r\u001c\u000b\u0005\u0005\u0013\u00119\tC\u0005\u0003ve\n\t\u00111\u0001\u0003n\u0005\u0011\u0002O]8ek\u000e$X\t\\3nK:$h*Y7f)\u0011\u0011\tF!$\t\u0013\tU$(!AA\u0002\t\r\u0014\u0001\u00035bg\"\u001cu\u000eZ3\u0015\u0005\t\r\u0014\u0001\u0003;p'R\u0014\u0018N\\4\u0015\u0005\tE\u0013AB3rk\u0006d7\u000f\u0006\u0003\u0003\n\tm\u0005\"\u0003B;{\u0005\u0005\t\u0019\u0001B7\u0005)\u0011Vm\u0019:fCRLwN\\\n\f9\u00055$Q\u0005B\u0015\u0005_\u0011)$A\u0003dCV\u001cX-\u0006\u0002\u0003&B!!q\u0007BT\u0013\u0011\u0011IK!\u0012\u0003\u0013QC'o\\<bE2,\u0017AB2bkN,\u0007\u0005\u0006\u0003\u00030\nE\u0006c\u0001B\u00149!9!\u0011U\u0010A\u0002\t\u0015F\u0003\u0002BX\u0005kC\u0011B!)!!\u0003\u0005\rA!*\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%cU\u0011!1\u0018\u0016\u0005\u0005K\u0013il\u000b\u0002\u0003@B!!\u0011\u0019Bf\u001b\t\u0011\u0019M\u0003\u0003\u0003F\n\u001d\u0017!C;oG\",7m[3e\u0015\u0011\u0011I-!\u001d\u0002\u0015\u0005tgn\u001c;bi&|g.\u0003\u0003\u0003N\n\r'!E;oG\",7m[3e-\u0006\u0014\u0018.\u00198dKR!!Q\u000eBi\u0011%\u0011)\bJA\u0001\u0002\u0004\u0011\u0019\u0007\u0006\u0003\u0003\n\tU\u0007\"\u0003B;M\u0005\u0005\t\u0019\u0001B7)\u0011\u0011\tF!7\t\u0013\tUt%!AA\u0002\t\rD\u0003\u0002B\u0005\u0005;D\u0011B!\u001e+\u0003\u0003\u0005\rA!\u001c\u0003\u0017Q+'/\\5oCRLwN\\\n\n\r\u00065$Q\u0005B\u0018\u0005k!\"A!:\u0011\u0007\t\u001db\t\u0006\u0003\u0003n\t%\b\"\u0003B;\u0015\u0006\u0005\t\u0019\u0001B2)\u0011\u0011IA!<\t\u0013\tUD*!AA\u0002\t5\u0014\u0001D<sSR,'+\u001a9mC\u000e,GC\u0001Bz!\u0011\u0011\u0019F!>\n\t\t](Q\u000b\u0002\u0007\u001f\nTWm\u0019;\u0003\u0017U\u001bXM\u001d*fcV,7\u000f^\n\n%\u00055$Q\u0005B\u0018\u0005k!\"Aa@\u0011\u0007\t\u001d\"\u0003\u0006\u0003\u0003n\r\r\u0001\"\u0003B;-\u0005\u0005\t\u0019\u0001B2)\u0011\u0011Iaa\u0002\t\u0013\tU\u0004$!AA\u0002\t5\u0014aC+tKJ\u0014V-];fgR\f!BU3de\u0016\fG/[8o!\r\u00119\u0003L\n\u0006Y\rE1Q\u0004\t\t\u0007'\u0019IB!*\u000306\u00111Q\u0003\u0006\u0005\u0007/\t\t(A\u0004sk:$\u0018.\\3\n\t\rm1Q\u0003\u0002\u0012\u0003\n\u001cHO]1di\u001a+hn\u0019;j_:\f\u0004\u0003BB\u0010\u0007Ki!a!\t\u000b\t\r\r\"\u0011L\u0001\u0003S>LAAa\u0011\u0004\"Q\u00111QB\u0001\u0006CB\u0004H.\u001f\u000b\u0005\u0005_\u001bi\u0003C\u0004\u0003\">\u0002\rA!*\u0002\u000fUt\u0017\r\u001d9msR!11GB\u001b!\u0019\ty'a2\u0003&\"I1q\u0007\u0019\u0002\u0002\u0003\u0007!qV\u0001\u0004q\u0012\u0002\u0014\u0001C\"sK\u0006$\u0018n\u001c8\u0011\u0007\t\u001drhE\u0003@\u0007\u007f\u0019i\u0002\u0005\u0004\u0004\u0014\r\u0005#\u0011J\u0005\u0005\u0007\u0007\u001a)BA\tBEN$(/Y2u\rVt7\r^5p]B\"\"aa\u000f\u0015\t\t%1\u0011\n\u0005\n\u0007o\u0019\u0015\u0011!a\u0001\u0005\u0013\n1\u0002V3s[&t\u0017\r^5p]\n)2\t[5mIJ+7\u000f^1siNLE/\u001a:bE2,7c\u0001)\u0004RAA11KB3\u0003\u0017\fYK\u0004\u0003\u0004V\r}c\u0002BB,\u00077rA!a&\u0004Z%\u0011\u0011\u0011N\u0005\u0005\u0007;\n9'\u0001\u0003vi&d\u0017\u0002BB1\u0007G\n1bQ8mY\u0016\u001cG/[8og*!1QLA4\u0013\u0011\u00199g!\u001b\u0003=A\u000b'\u000f^5bY&kW.\u001e;bE2,g+\u00197vKNLE/\u001a:bE2,'\u0002BB1\u0007G\u0002Da!\u001c\u0004xAA\u0011\u0011]B8\u0007g\nY-\u0003\u0003\u0004r\u0005\r(aA'baB!1QOB<\u0019\u0001!1b!\u001fR\u0003\u0003\u0005\tQ!\u0001\u0004|\t\u0019q\fJ\u0019\u0012\t\ru$Q\u000e\t\u0005\u0003_\u001ay(\u0003\u0003\u0004\u0002\u0006E$a\u0002(pi\"Lgn\u001a\u000b\u0005\u0007\u000b\u001b9\tE\u0002\u0003(ACq!!+S\u0001\u0004\u0019I\t\r\u0003\u0004\f\u000e=\u0005\u0003CAq\u0007_\u001ai)a3\u0011\t\rU4q\u0012\u0003\r\u0007s\u001a9)!A\u0001\u0002\u000b\u000511\u0010\u000b\u0005\u0003W\u001b\u0019\nC\u0004\u0004\u0016N\u0003\r!a3\u0002\u0003\r\f1\"[:EK\u001aLg.\u001a3BiR!!\u0011BBN\u0011\u001d\u0019)\n\u0016a\u0001\u0003\u0017\faB^1mk\u0016\u001c\u0018\n^3sCR|'/\u0006\u0002\u0004\"B1!Q\u0010B@\u0003\u0017\u0014\u0001c\u00115jY\u0012\u0014XM\\%uKJ\f'\r\\3\u0014\u0007Y\u001b9\u000b\u0005\u0005\u0004T\r\u0015\u00141ZA^a\u0011\u0019Yka,\u0011\u0011\u0005\u00058qNBW\u0003\u0017\u0004Ba!\u001e\u00040\u0012Y1\u0011W,\u0002\u0002\u0003\u0005)\u0011AB>\u0005\ryFE\r\u000b\u0005\u0007k\u001b9\fE\u0002\u0003(YCq!!+Y\u0001\u0004\u0019I\f\r\u0003\u0004<\u000e}\u0006\u0003CAq\u0007_\u001ai,a3\u0011\t\rU4q\u0018\u0003\r\u0007c\u001b9,!A\u0001\u0002\u000b\u000511\u0010\u000b\u0005\u0003w\u001b\u0019\rC\u0004\u0004\u0016f\u0003\r!a3\u0015\t\t%1q\u0019\u0005\b\u0007+S\u0006\u0019AAf\u0005Y)U\u000e\u001d;z\u0007\"LG\u000e\u001a:f]\u000e{g\u000e^1j]\u0016\u00148#B/\u0002n\u0005%\u0015AC3naRL8\u000b^1ugV\u00111\u0011\u001b\t\t\u0003C\u001c\u0019.!%\u0002L&!1Q[Ar\u0005\u001d!&/Z3NCB$b!!#\u0004Z\u000em\u0007bBAHA\u0002\u0007\u0011\u0011\u0013\u0005\b\u0003S\u0003\u0007\u0019AAV)\u0011\tIia8\t\u000f\u0005e\u0016\r1\u0001\u0002<R!\u0011q[Br\u0011\u001d\tyI\u0019a\u0001\u0003##B!a6\u0004h\"9\u0011QM2A\u0002\u0005mF\u0003BAE\u0007WDq!!\u001ag\u0001\u0004\tY\f\u0006\u0003\u0002\n\u000e=\bbBAHO\u0002\u0007\u0011\u0011\u0013\u000b\u0005\u0003\u0013\u001b\u0019\u0010C\u0004\u0002\u0010\"\u0004\r!!%\u0002-\u0015k\u0007\u000f^=DQ&dGM]3o\u0007>tG/Y5oKJ\u00042Aa\nk'\u0015Q\u0017QNB~!\r\u00119#\u0018\u000b\u0003\u0007o\f1\u0004V3s[&t\u0017\r^3e\u0007\"LG\u000e\u001a:f]\u000e{g\u000e^1j]\u0016\u0014\bc\u0001B\u0014]\nYB+\u001a:nS:\fG/\u001a3DQ&dGM]3o\u0007>tG/Y5oKJ\u001cRA\\A7\u0007w$\"\u0001\"\u0001\u0015\r\u0005%E1\u0002C\u0007\u0011\u001d\ty\t\u001da\u0001\u0003#Cq!!+q\u0001\u0004\tY\u000b\u0006\u0003\u0002\n\u0012E\u0001bBAHc\u0002\u0007\u0011\u0011\u0013\u0002\u0018\u001d>\u0014X.\u00197DQ&dGM]3o\u0007>tG/Y5oKJ\u001cR!^A7\u0003\u0013\u000b!a\u0019\u0011\u0015\t\u0011mAQ\u0004\t\u0004\u0005O)\bbBBKq\u0002\u00071\u0011\u001b\u000b\u0007\u0003\u0013#\t\u0003b\t\t\u000f\u0005=\u0015\u00101\u0001\u0002\u0012\"9\u0011\u0011V=A\u0002\u0005-F\u0003BAE\tOAq!!/{\u0001\u0004\tY\f\u0006\u0003\u0002F\u0012-\u0002bBAHw\u0002\u0007\u0011\u0011\u0013\u000b\u0005\u0003/$y\u0003C\u0004\u0002fq\u0004\r!a/\u0015\t\u0005%E1\u0007\u0005\b\u0003Kz\b\u0019AA^)\u0011\tI\tb\u000e\t\u0011\u0005=\u0015\u0011\u0001a\u0001\u0003##B!!#\u0005<!A\u0011qRA\u0002\u0001\u0004\t\t*A\fO_Jl\u0017\r\\\"iS2$'/\u001a8D_:$\u0018-\u001b8feB!!qEA\u0005'\u0011\tI!!\u001c\u0015\u0005\u0011}B\u0003BAE\t\u000fB\u0001b!&\u0002\u000e\u0001\u00071\u0011\u001b\u0002\u001d)\u0016\u0014X.\u001b8bi&twm\u00115jY\u0012\u0014XM\\\"p]R\f\u0017N\\3s')\ty!!\u001c\u0002\n\n=\"QG\u0001\u0006i>$\u0015.Z\u000b\u0003\t#\u0002b!a%\u0005T\u0005m\u0016\u0002\u0002C+\u0003K\u00131aU3u\u0003\u0019!x\u000eR5fA\u00051!/Z1t_:,\"A!\n\u0002\u000fI,\u0017m]8oAQAA\u0011\rC2\tK\"9\u0007\u0005\u0003\u0003(\u0005=\u0001\u0002CBK\u0003;\u0001\ra!5\t\u0011\u00115\u0013Q\u0004a\u0001\t#B\u0001\u0002\"\u0017\u0002\u001e\u0001\u0007!Q\u0005\u000b\u0007\u0003\u0013#Y\u0007\"\u001c\t\u0011\u0005=\u0015q\u0004a\u0001\u0003#C\u0001\"!+\u0002 \u0001\u0007\u00111\u0016\u000b\u0005\u0003\u0013#\t\b\u0003\u0005\u0002:\u0006\u0005\u0002\u0019AA^)\u0011\t)\r\"\u001e\t\u0011\u0005=\u00151\u0005a\u0001\u0003##B!a6\u0005z!A\u0011QMA\u0013\u0001\u0004\tY\f\u0006\u0003\u0002\n\u0012u\u0004\u0002CA3\u0003W\u0001\r!a/\u0015\t\u0005%E\u0011\u0011\u0005\t\u0003\u001f\u000bi\u00031\u0001\u0002\u0012R!\u0011\u0011\u0012CC\u0011!\ty)a\fA\u0002\u0005EE\u0003\u0003C1\t\u0013#Y\t\"$\t\u0015\rU\u0015q\u0007I\u0001\u0002\u0004\u0019\t\u000e\u0003\u0006\u0005N\u0005]\u0002\u0013!a\u0001\t#B!\u0002\"\u0017\u00028A\u0005\t\u0019\u0001B\u0013+\t!\tJ\u000b\u0003\u0004R\nu\u0016AD2paf$C-\u001a4bk2$HEM\u000b\u0003\t/SC\u0001\"\u0015\u0003>\u0006q1m\u001c9zI\u0011,g-Y;mi\u0012\u001aTC\u0001COU\u0011\u0011)C!0\u0015\t\t5D\u0011\u0015\u0005\u000b\u0005k\n\u0019%!AA\u0002\t\rD\u0003\u0002B\u0005\tKC!B!\u001e\u0002H\u0005\u0005\t\u0019\u0001B7)\u0011\u0011\t\u0006\"+\t\u0015\tU\u0014\u0011JA\u0001\u0002\u0004\u0011\u0019\u0007\u0006\u0003\u0003\n\u00115\u0006B\u0003B;\u0003\u001b\n\t\u00111\u0001\u0003n\u0005aB+\u001a:nS:\fG/\u001b8h\u0007\"LG\u000e\u001a:f]\u000e{g\u000e^1j]\u0016\u0014\b\u0003\u0002B\u0014\u0003#\u001ab!!\u0015\u00056\u000eu\u0001\u0003DB\n\to\u001b\t\u000e\"\u0015\u0003&\u0011\u0005\u0014\u0002\u0002C]\u0007+\u0011\u0011#\u00112tiJ\f7\r\u001e$v]\u000e$\u0018n\u001c84)\t!\t\f\u0006\u0005\u0005b\u0011}F\u0011\u0019Cb\u0011!\u0019)*a\u0016A\u0002\rE\u0007\u0002\u0003C'\u0003/\u0002\r\u0001\"\u0015\t\u0011\u0011e\u0013q\u000ba\u0001\u0005K!B\u0001b2\u0005PB1\u0011qNAd\t\u0013\u0004\"\"a\u001c\u0005L\u000eEG\u0011\u000bB\u0013\u0013\u0011!i-!\u001d\u0003\rQ+\b\u000f\\34\u0011)\u00199$!\u0017\u0002\u0002\u0003\u0007A\u0011\r")
public interface ChildrenContainer {
    public ChildrenContainer add(String var1, ChildRestartStats var2);

    public ChildrenContainer remove(ActorRef var1);

    public Option<ChildStats> getByName(String var1);

    public Option<ChildRestartStats> getByRef(ActorRef var1);

    public Iterable<ActorRef> children();

    public Iterable<ChildRestartStats> stats();

    public ChildrenContainer shallDie(ActorRef var1);

    public ChildrenContainer reserve(String var1);

    public ChildrenContainer unreserve(String var1);

    default public boolean isTerminating() {
        return false;
    }

    default public boolean isNormal() {
        return true;
    }

    public static void $init$(ChildrenContainer $this) {
    }

    public static class ChildRestartsIterable
    extends Collections.PartialImmutableValuesIterable<ChildStats, ChildRestartStats> {
        private final Map<?, ChildStats> stats;

        @Override
        public final ChildRestartStats apply(ChildStats c) {
            return (ChildRestartStats)c;
        }

        @Override
        public final boolean isDefinedAt(ChildStats c) {
            return c instanceof ChildRestartStats;
        }

        @Override
        public final Iterator<ChildStats> valuesIterator() {
            return this.stats.valuesIterator();
        }

        public ChildRestartsIterable(Map<?, ChildStats> stats) {
            this.stats = stats;
        }
    }

    public static class ChildrenIterable
    extends Collections.PartialImmutableValuesIterable<ChildStats, ActorRef> {
        private final Map<?, ChildStats> stats;

        @Override
        public final ActorRef apply(ChildStats c) {
            return ((ChildRestartStats)c).child();
        }

        @Override
        public final boolean isDefinedAt(ChildStats c) {
            return c instanceof ChildRestartStats;
        }

        @Override
        public final Iterator<ChildStats> valuesIterator() {
            return this.stats.valuesIterator();
        }

        public ChildrenIterable(Map<?, ChildStats> stats) {
            this.stats = stats;
        }
    }

    public static final class Creation
    implements SuspendReason,
    WaitingForChildren,
    Product,
    Serializable {
        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public Creation copy() {
            return new Creation();
        }

        @Override
        public String productPrefix() {
            return "Creation";
        }

        @Override
        public int productArity() {
            return 0;
        }

        @Override
        public Object productElement(int x$1) {
            int n = x$1;
            return Statics.ioobe(x$1);
        }

        @Override
        public Iterator<Object> productIterator() {
            return ScalaRunTime$.MODULE$.typedProductIterator(this);
        }

        @Override
        public boolean canEqual(Object x$1) {
            return x$1 instanceof Creation;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            return (String)Statics.ioobe(x$1);
        }

        public int hashCode() {
            return ScalaRunTime$.MODULE$._hashCode(this);
        }

        public String toString() {
            return ScalaRunTime$.MODULE$._toString(this);
        }

        @Override
        public boolean equals(Object x$1) {
            Object object = x$1;
            return object instanceof Creation;
        }

        public Creation() {
            Product.$init$(this);
        }
    }

    public static interface EmptyChildrenContainer
    extends ChildrenContainer {
        public void akka$actor$dungeon$ChildrenContainer$EmptyChildrenContainer$_setter_$emptyStats_$eq(TreeMap<String, ChildStats> var1);

        public TreeMap<String, ChildStats> emptyStats();

        public static /* synthetic */ ChildrenContainer add$(EmptyChildrenContainer $this, String name, ChildRestartStats stats) {
            return $this.add(name, stats);
        }

        @Override
        default public ChildrenContainer add(String name, ChildRestartStats stats) {
            return new NormalChildrenContainer((TreeMap<String, ChildStats>)this.emptyStats().updated((Object)name, stats));
        }

        public static /* synthetic */ ChildrenContainer remove$(EmptyChildrenContainer $this, ActorRef child) {
            return $this.remove(child);
        }

        @Override
        default public ChildrenContainer remove(ActorRef child) {
            return this;
        }

        public static /* synthetic */ Option getByName$(EmptyChildrenContainer $this, String name) {
            return $this.getByName(name);
        }

        default public Option<ChildRestartStats> getByName(String name) {
            return None$.MODULE$;
        }

        public static /* synthetic */ Option getByRef$(EmptyChildrenContainer $this, ActorRef actor) {
            return $this.getByRef(actor);
        }

        @Override
        default public Option<ChildRestartStats> getByRef(ActorRef actor) {
            return None$.MODULE$;
        }

        public static /* synthetic */ Iterable children$(EmptyChildrenContainer $this) {
            return $this.children();
        }

        @Override
        default public Iterable<ActorRef> children() {
            return Collections$EmptyImmutableSeq$.MODULE$;
        }

        public static /* synthetic */ Iterable stats$(EmptyChildrenContainer $this) {
            return $this.stats();
        }

        @Override
        default public Iterable<ChildRestartStats> stats() {
            return Collections$EmptyImmutableSeq$.MODULE$;
        }

        public static /* synthetic */ ChildrenContainer shallDie$(EmptyChildrenContainer $this, ActorRef actor) {
            return $this.shallDie(actor);
        }

        @Override
        default public ChildrenContainer shallDie(ActorRef actor) {
            return this;
        }

        public static /* synthetic */ ChildrenContainer reserve$(EmptyChildrenContainer $this, String name) {
            return $this.reserve(name);
        }

        @Override
        default public ChildrenContainer reserve(String name) {
            return new NormalChildrenContainer((TreeMap<String, ChildStats>)this.emptyStats().updated((Object)name, ChildNameReserved$.MODULE$));
        }

        public static /* synthetic */ ChildrenContainer unreserve$(EmptyChildrenContainer $this, String name) {
            return $this.unreserve(name);
        }

        @Override
        default public ChildrenContainer unreserve(String name) {
            return this;
        }

        public static void $init$(EmptyChildrenContainer $this) {
            $this.akka$actor$dungeon$ChildrenContainer$EmptyChildrenContainer$_setter_$emptyStats_$eq((TreeMap<String, ChildStats>)TreeMap$.MODULE$.empty((Ordering)Ordering$String$.MODULE$));
        }
    }

    public static class NormalChildrenContainer
    implements ChildrenContainer {
        private final TreeMap<String, ChildStats> c;

        @Override
        public boolean isTerminating() {
            return ((ChildrenContainer)this).isTerminating();
        }

        @Override
        public boolean isNormal() {
            return ((ChildrenContainer)this).isNormal();
        }

        public TreeMap<String, ChildStats> c() {
            return this.c;
        }

        @Override
        public ChildrenContainer add(String name, ChildRestartStats stats) {
            return new NormalChildrenContainer((TreeMap<String, ChildStats>)this.c().updated((Object)name, stats));
        }

        @Override
        public ChildrenContainer remove(ActorRef child) {
            return ChildrenContainer$NormalChildrenContainer$.MODULE$.apply((TreeMap)this.c().$minus((Object)child.path().name()));
        }

        @Override
        public Option<ChildStats> getByName(String name) {
            return this.c().get(name);
        }

        @Override
        public Option<ChildRestartStats> getByRef(ActorRef actor) {
            Some some;
            ChildStats crs;
            Option<ChildStats> option = this.c().get(actor.path().name());
            if (option instanceof Some && (crs = (ChildStats)(some = (Some)option).value()) instanceof ChildRestartStats) {
                ChildRestartStats childRestartStats = (ChildRestartStats)crs;
                ActorRef actorRef2 = childRestartStats.child();
                ActorRef actorRef3 = actor;
                if (!(actorRef2 != null ? !((Object)actorRef2).equals(actorRef3) : actorRef3 != null)) {
                    return some;
                }
            }
            return None$.MODULE$;
        }

        @Override
        public Iterable<ActorRef> children() {
            if (this.c().isEmpty()) {
                return Collections$EmptyImmutableSeq$.MODULE$;
            }
            return new ChildrenIterable(this.c());
        }

        @Override
        public Iterable<ChildRestartStats> stats() {
            if (this.c().isEmpty()) {
                return Collections$EmptyImmutableSeq$.MODULE$;
            }
            return new ChildRestartsIterable(this.c());
        }

        @Override
        public ChildrenContainer shallDie(ActorRef actor) {
            return new TerminatingChildrenContainer(this.c(), (Set)Predef$.MODULE$.Set().apply((Seq)ScalaRunTime$.MODULE$.wrapRefArray((Object[])new ActorRef[]{actor})), ChildrenContainer$UserRequest$.MODULE$);
        }

        @Override
        public ChildrenContainer reserve(String name) {
            if (this.c().contains(name)) {
                throw new InvalidActorNameException(new StringBuilder(28).append("actor name [").append(name).append("] is not unique!").toString());
            }
            return new NormalChildrenContainer((TreeMap<String, ChildStats>)this.c().updated((Object)name, ChildNameReserved$.MODULE$));
        }

        @Override
        public ChildrenContainer unreserve(String name) {
            Some some;
            ChildStats childStats;
            Option<ChildStats> option = this.c().get(name);
            if (option instanceof Some && ChildNameReserved$.MODULE$.equals(childStats = (ChildStats)(some = (Some)option).value())) {
                return ChildrenContainer$NormalChildrenContainer$.MODULE$.apply((TreeMap)this.c().$minus((Object)name));
            }
            return this;
        }

        public String toString() {
            if (this.c().size() > 20) {
                return new StringBuilder(9).append(Integer.toString(this.c().size())).append(" children").toString();
            }
            return this.c().mkString("children:\n    ", "\n    ", "");
        }

        public NormalChildrenContainer(TreeMap<String, ChildStats> c) {
            this.c = c;
            ChildrenContainer.$init$(this);
        }
    }

    public static final class Recreation
    implements SuspendReason,
    WaitingForChildren,
    Product,
    Serializable {
        private final Throwable cause;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public Throwable cause() {
            return this.cause;
        }

        public Recreation copy(Throwable cause) {
            return new Recreation(cause);
        }

        public Throwable copy$default$1() {
            return this.cause();
        }

        @Override
        public String productPrefix() {
            return "Recreation";
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
                    return this.cause();
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
            return x$1 instanceof Recreation;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "cause";
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
            if (!(object instanceof Recreation)) return false;
            boolean bl = true;
            if (!bl) return false;
            Recreation recreation = (Recreation)x$1;
            Throwable throwable = this.cause();
            Throwable throwable2 = recreation.cause();
            if (throwable != null) {
                if (!throwable.equals(throwable2)) return false;
                return true;
            }
            if (throwable2 == null) return true;
            return false;
        }

        public Recreation(Throwable cause) {
            this.cause = cause;
            Product.$init$(this);
        }
    }

    public static interface SuspendReason {
    }

    public static final class TerminatingChildrenContainer
    implements ChildrenContainer,
    Product,
    Serializable {
        private final TreeMap<String, ChildStats> c;
        private final Set<ActorRef> toDie;
        private final SuspendReason reason;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public TreeMap<String, ChildStats> c() {
            return this.c;
        }

        public Set<ActorRef> toDie() {
            return this.toDie;
        }

        public SuspendReason reason() {
            return this.reason;
        }

        @Override
        public ChildrenContainer add(String name, ChildRestartStats stats) {
            return this.copy((TreeMap<String, ChildStats>)this.c().updated((Object)name, stats), this.copy$default$2(), this.copy$default$3());
        }

        @Override
        public ChildrenContainer remove(ActorRef child) {
            Set t2 = (Set)this.toDie().$minus(child);
            if (t2.isEmpty()) {
                SuspendReason suspendReason = this.reason();
                if (ChildrenContainer$Termination$.MODULE$.equals(suspendReason)) {
                    return ChildrenContainer$TerminatedChildrenContainer$.MODULE$;
                }
                return ChildrenContainer$NormalChildrenContainer$.MODULE$.apply((TreeMap)this.c().$minus((Object)child.path().name()));
            }
            return this.copy((TreeMap)this.c().$minus((Object)child.path().name()), t2, this.copy$default$3());
        }

        @Override
        public Option<ChildStats> getByName(String name) {
            return this.c().get(name);
        }

        @Override
        public Option<ChildRestartStats> getByRef(ActorRef actor) {
            Some some;
            ChildStats crs;
            Option<ChildStats> option = this.c().get(actor.path().name());
            if (option instanceof Some && (crs = (ChildStats)(some = (Some)option).value()) instanceof ChildRestartStats) {
                ChildRestartStats childRestartStats = (ChildRestartStats)crs;
                ActorRef actorRef2 = childRestartStats.child();
                ActorRef actorRef3 = actor;
                if (!(actorRef2 != null ? !((Object)actorRef2).equals(actorRef3) : actorRef3 != null)) {
                    return some;
                }
            }
            return None$.MODULE$;
        }

        @Override
        public Iterable<ActorRef> children() {
            if (this.c().isEmpty()) {
                return Collections$EmptyImmutableSeq$.MODULE$;
            }
            return new ChildrenIterable(this.c());
        }

        @Override
        public Iterable<ChildRestartStats> stats() {
            if (this.c().isEmpty()) {
                return Collections$EmptyImmutableSeq$.MODULE$;
            }
            return new ChildRestartsIterable(this.c());
        }

        @Override
        public ChildrenContainer shallDie(ActorRef actor) {
            Set x$1 = (Set)this.toDie().$plus(actor);
            TreeMap<String, ChildStats> x$2 = this.copy$default$1();
            SuspendReason x$3 = this.copy$default$3();
            return this.copy(x$2, x$1, x$3);
        }

        @Override
        public ChildrenContainer reserve(String name) {
            SuspendReason suspendReason = this.reason();
            if (ChildrenContainer$Termination$.MODULE$.equals(suspendReason)) {
                throw new IllegalStateException(new StringBuilder(41).append("cannot reserve actor name '").append(name).append("': terminating").toString());
            }
            if (this.c().contains(name)) {
                throw new InvalidActorNameException(new StringBuilder(28).append("actor name [").append(name).append("] is not unique!").toString());
            }
            return this.copy((TreeMap<String, ChildStats>)this.c().updated((Object)name, ChildNameReserved$.MODULE$), this.copy$default$2(), this.copy$default$3());
        }

        @Override
        public ChildrenContainer unreserve(String name) {
            Some some;
            ChildStats childStats;
            Option<ChildStats> option = this.c().get(name);
            if (option instanceof Some && ChildNameReserved$.MODULE$.equals(childStats = (ChildStats)(some = (Some)option).value())) {
                return this.copy((TreeMap)this.c().$minus((Object)name), this.copy$default$2(), this.copy$default$3());
            }
            return this;
        }

        @Override
        public boolean isTerminating() {
            SuspendReason suspendReason = this.reason();
            ChildrenContainer$Termination$ childrenContainer$Termination$ = ChildrenContainer$Termination$.MODULE$;
            return !(suspendReason != null ? !suspendReason.equals(childrenContainer$Termination$) : childrenContainer$Termination$ != null);
        }

        @Override
        public boolean isNormal() {
            SuspendReason suspendReason = this.reason();
            ChildrenContainer$UserRequest$ childrenContainer$UserRequest$ = ChildrenContainer$UserRequest$.MODULE$;
            return !(suspendReason != null ? !suspendReason.equals(childrenContainer$UserRequest$) : childrenContainer$UserRequest$ != null);
        }

        public String toString() {
            if (this.c().size() > 20) {
                return new StringBuilder(9).append(Integer.toString(this.c().size())).append(" children").toString();
            }
            return new StringBuilder(0).append(this.c().mkString(new StringBuilder(29).append("children (").append(this.toDie().size()).append(" terminating):\n    ").toString(), "\n    ", "\n")).append(this.toDie()).toString();
        }

        public TerminatingChildrenContainer copy(TreeMap<String, ChildStats> c, Set<ActorRef> toDie, SuspendReason reason) {
            return new TerminatingChildrenContainer(c, toDie, reason);
        }

        public TreeMap<String, ChildStats> copy$default$1() {
            return this.c();
        }

        public Set<ActorRef> copy$default$2() {
            return this.toDie();
        }

        public SuspendReason copy$default$3() {
            return this.reason();
        }

        @Override
        public String productPrefix() {
            return "TerminatingChildrenContainer";
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
                    return this.c();
                }
                case 1: {
                    return this.toDie();
                }
                case 2: {
                    return this.reason();
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
            return x$1 instanceof TerminatingChildrenContainer;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "c";
                }
                case 1: {
                    return "toDie";
                }
                case 2: {
                    return "reason";
                }
            }
            return (String)Statics.ioobe(x$1);
        }

        public int hashCode() {
            return ScalaRunTime$.MODULE$._hashCode(this);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public boolean equals(Object x$1) {
            if (this == x$1) return true;
            Object object = x$1;
            if (!(object instanceof TerminatingChildrenContainer)) return false;
            boolean bl = true;
            if (!bl) return false;
            TerminatingChildrenContainer terminatingChildrenContainer = (TerminatingChildrenContainer)x$1;
            TreeMap<String, ChildStats> treeMap = this.c();
            TreeMap<String, ChildStats> treeMap2 = terminatingChildrenContainer.c();
            if (treeMap == null) {
                if (treeMap2 != null) {
                    return false;
                }
            } else if (!((Object)treeMap).equals(treeMap2)) return false;
            Set<ActorRef> set = this.toDie();
            Set<ActorRef> set2 = terminatingChildrenContainer.toDie();
            if (set == null) {
                if (set2 != null) {
                    return false;
                }
            } else if (!set.equals(set2)) return false;
            SuspendReason suspendReason = this.reason();
            SuspendReason suspendReason2 = terminatingChildrenContainer.reason();
            if (suspendReason == null) {
                if (suspendReason2 == null) return true;
                return false;
            } else {
                if (!suspendReason.equals(suspendReason2)) return false;
                return true;
            }
        }

        public TerminatingChildrenContainer(TreeMap<String, ChildStats> c, Set<ActorRef> toDie, SuspendReason reason) {
            this.c = c;
            this.toDie = toDie;
            this.reason = reason;
            ChildrenContainer.$init$(this);
            Product.$init$(this);
        }
    }

    public static interface WaitingForChildren {
    }
}

