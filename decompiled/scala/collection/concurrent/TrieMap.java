/*
 * Decompiled with CFR 0.152.
 */
package scala.collection.concurrent;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple2;
import scala.collection.Iterable;
import scala.collection.IterableOnce;
import scala.collection.IterableOps;
import scala.collection.Iterator;
import scala.collection.Map;
import scala.collection.MapFactory;
import scala.collection.MapOps;
import scala.collection.MapView;
import scala.collection.Set;
import scala.collection.concurrent.Gen;
import scala.collection.concurrent.INode;
import scala.collection.concurrent.INode$;
import scala.collection.concurrent.INodeBase;
import scala.collection.concurrent.MainNode;
import scala.collection.concurrent.RDCSS_Descriptor;
import scala.collection.concurrent.TrieMap$;
import scala.collection.concurrent.TrieMapIterator;
import scala.collection.concurrent.TrieMapSerializationEnd$;
import scala.collection.generic.DefaultSerializable;
import scala.collection.mutable.AbstractMap;
import scala.collection.mutable.GrowableBuilder;
import scala.math.Equiv;
import scala.package$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.Statics;
import scala.util.Failure;
import scala.util.Success;
import scala.util.Try;
import scala.util.control.NonFatal$;
import scala.util.hashing.Hashing;

@ScalaSignature(bytes="\u0006\u0005\u0011\u0015f\u0001B+W\u0005uC!\"!\u0006\u0001\u0005\u0003\u0005\u000b\u0011BA\f\u0011)\ti\u0002\u0001B\u0001B\u0003%\u0011q\u0004\u0005\u000b\u0003k\u0001!\u0011!Q\u0001\n\u0005]\u0002BCA#\u0001\t\u0005\t\u0015!\u0003\u0002H!9\u0011Q\u000b\u0001\u0005\n\u0005]\u0003\u0002CA1\u0001\u0001\u0006K!a\u000e\t\u0011\u0005\r\u0004\u0001)Q\u0005\u0003\u000fB\u0001\"!\u001a\u0001A\u0003&\u0011q\u0004\u0005\b\u0003{\u0001A\u0011AA8\u0011\u001d\t\t\b\u0001C\u0001\u0003gB\u0011\"!\u001e\u0001\u0001\u0004%I!a\u001e\t\u0013\u0005e\u0004\u00011A\u0005\n\u0005m\u0004\u0002CAD\u0001\u0001\u0006K!a\u0006\t\u000f\u0005U\u0003\u0001\"\u0001\u0002\u0012\"9\u0011Q\u000b\u0001\u0005\u0002\u0005]\u0005bBAM\u0001\u0011\u0005\u00131\u0014\u0005\b\u0003G\u0003A\u0011BAS\u0011\u001d\t9\f\u0001C\u0005\u0003sCq!!2\u0001\t\u0013\t9\r\u0003\u0005\u0002X\u0002!\t\u0001WAm\u0011)\t)\u000fAI\u0001\n\u0003A\u0016q\u001d\u0005\t\u0003{\u0004A\u0011\u0001,\u0002\u0000\"Q!1\u0001\u0001\u0012\u0002\u0013\u0005a+a:\t\u000f\t\u0015\u0001\u0001\"\u0003\u0003\b!9!Q\u0003\u0001\u0005\n\t]\u0001b\u0002B\u0014\u0001\u0011%!\u0011\u0006\u0005\b\u0005\u007f\u0001A\u0011\u0002B!\u0011\u001d\u0011I\u0006\u0001C\u0005\u00057BqAa\u0019\u0001\t\u0013\u0011)\u0007C\u0004\u0003t\u0001!\tA!\u001e\t\u000f\t5\u0005\u0001\"\u0001\u0003\u0010\"9!\u0011\u0013\u0001\u0005\u0002\t=\u0005b\u0002BJ\u0001\u0011\u0005\u0011q\u0013\u0005\b\u0005/\u0003A\u0011\u0001BM\u0011\u001d\u0011\t\u000b\u0001C!\u0005GCqAa*\u0001\t\u0003\u0011I\u000bC\u0004\u0003.\u0002!\tAa,\t\u000f\t\u001d\u0007\u0001\"\u0011\u0003J\"9!Q\u001a\u0001\u0005\u0002\t=\u0007b\u0002Bj\u0001\u0011\u0005#Q\u001b\u0005\b\u0005?\u0004A\u0011\tBq\u0011\u001d\u00119\u000f\u0001C\u0001\u0005SDqAa>\u0001\t\u0003\u0012I\u0010C\u0004\u0003~\u0002!\tAa@\t\u000f\r\r\u0001\u0001\"\u0001\u0004\u0006!911\u0002\u0001\u0005B\r5\u0001b\u0002B|\u0001\u0011\u000511\u0004\u0005\t\u0007C\u0001A\u0011\t-\u0004$!91\u0011\u0006\u0001\u0005\u0002\r-\u0002\u0002CB\u001c\u0001\u0011\u0005\u0003l!\u000f\t\u000f\r%\u0002\u0001\"\u0001\u0004F!911\n\u0001\u0005\u0002\r5\u0003bBB+\u0001\u0011\u00053q\u000b\u0005\b\u0007;\u0002A\u0011IB0\u0011\u001d\u00199\u0007\u0001C!\u0007SBqa!\u001d\u0001\t\u0003\u001a\u0019\bC\u0004\u0004\u0006\u0002!\tea\"\t\u000f\r}\u0005\u0001\"\u0011\u0004\"\"911\u0015\u0001\u0005B\r\u0005\u0006bBBS\u0001\u0011\u0005#q\u0012\u0005\t\u0007O\u0003\u0001\u0015\"\u0015\u0004*\"91Q\u0017\u0001\u0005B\r]vaBBc-\"\u00051q\u0019\u0004\u0007+ZC\ta!3\t\u000f\u0005U\u0003\t\"\u0001\u0004L\"91Q\u001a!\u0005\u0002\r=\u0007bBBo\u0001\u0012\u00051q\u001c\u0005\b\u0007s\u0004E\u0011AB~\u0011%!\t\u0002\u0011b\u0001\n\u0003!\u0019\u0002\u0003\u0005\u0005&\u0001\u0003\u000b\u0011\u0002C\u000b\r\u0019!y\u0004\u0011\u0001\u0005B!9\u0011QK$\u0005\u0002\u0011-\u0003b\u0002C)\u000f\u0012\u0005A1K\u0004\t\t/\u0002\u0005\u0012\u0001,\u0005Z\u0019AA1\f!\t\u0002Y#i\u0006C\u0004\u0002V-#\t\u0001b\u0018\t\u0013\u0011\u00054J1A\u0005\u0006\u0011\r\u0004\u0002\u0003C5\u0017\u0002\u0006i\u0001\"\u001a\t\u0013\u0011-4J1A\u0005\u0006\u00115\u0004\u0002\u0003C:\u0017\u0002\u0006i\u0001b\u001c\t\u0013\u0011U4J1A\u0005\u0006\u0011]\u0004\u0002\u0003C?\u0017\u0002\u0006i\u0001\"\u001f\t\u000f\u0011}4\n\"\u0001\u0005\u0002\"IAQ\u0013!\u0002\u0002\u0013%Aq\u0013\u0002\b)JLW-T1q\u0015\t9\u0006,\u0001\u0006d_:\u001cWO\u001d:f]RT!!\u0017.\u0002\u0015\r|G\u000e\\3di&|gNC\u0001\\\u0003\u0015\u00198-\u00197b\u0007\u0001)2AX4s'\u001d\u0001q\f\u001e=~\u0003\u0013\u0001B\u0001Y2fc6\t\u0011M\u0003\u0002c1\u00069Q.\u001e;bE2,\u0017B\u00013b\u0005-\t%m\u001d;sC\u000e$X*\u00199\u0011\u0005\u0019<G\u0002\u0001\u0003\u0006Q\u0002\u0011\r!\u001b\u0002\u0002\u0017F\u0011!N\u001c\t\u0003W2l\u0011AW\u0005\u0003[j\u0013qAT8uQ&tw\r\u0005\u0002l_&\u0011\u0001O\u0017\u0002\u0004\u0003:L\bC\u00014s\t\u0015\u0019\bA1\u0001j\u0005\u00051\u0006\u0003B;wKFl\u0011AV\u0005\u0003oZ\u00131!T1q!\u0019\u0001\u00170Z9|y&\u0011!0\u0019\u0002\u0007\u001b\u0006\u0004x\n]:\u0011\u0005U\u0004\u0001\u0003B;\u0001KF\u0004rA`@fcn\f\u0019!D\u0001Y\u0013\r\t\t\u0001\u0017\u0002\u0013\u001b\u0006\u0004h)Y2u_JLH)\u001a4bk2$8\u000fE\u0002a\u0003\u000bI1!a\u0002b\u0005!IE/\u001a:bE2,\u0007\u0003BA\u0006\u0003#i!!!\u0004\u000b\u0007\u0005=\u0001,A\u0004hK:,'/[2\n\t\u0005M\u0011Q\u0002\u0002\u0014\t\u00164\u0017-\u001e7u'\u0016\u0014\u0018.\u00197ju\u0006\u0014G.Z\u0001\u0002eB\u00191.!\u0007\n\u0007\u0005m!L\u0001\u0004B]f\u0014VMZ\u0001\u0006eR,\b\u000f\u001a\t\b\u0003C\t\t\u0004`A\f\u001b\t\t\u0019C\u0003\u0003\u0002&\u0005\u001d\u0012AB1u_6L7MC\u0002X\u0003SQA!a\u000b\u0002.\u0005!Q\u000f^5m\u0015\t\ty#\u0001\u0003kCZ\f\u0017\u0002BA\u001a\u0003G\u00111$\u0011;p[&\u001c'+\u001a4fe\u0016t7-\u001a$jK2$W\u000b\u001d3bi\u0016\u0014\u0018!\u00025bg\"4\u0007#BA\u001d\u0003\u0003*WBAA\u001e\u0015\u0011\ti$a\u0010\u0002\u000f!\f7\u000f[5oO*\u0019\u00111\u0006.\n\t\u0005\r\u00131\b\u0002\b\u0011\u0006\u001c\b.\u001b8h\u0003\t)g\rE\u0003\u0002J\u0005=SMD\u0002l\u0003\u0017J1!!\u0014[\u0003\u001d\u0001\u0018mY6bO\u0016LA!!\u0015\u0002T\t)Q)];jm*\u0019\u0011Q\n.\u0002\rqJg.\u001b;?)%a\u0018\u0011LA.\u0003;\ny\u0006C\u0004\u0002\u0016\u0015\u0001\r!a\u0006\t\u000f\u0005uQ\u00011\u0001\u0002 !9\u0011QG\u0003A\u0002\u0005]\u0002bBA#\u000b\u0001\u0007\u0011qI\u0001\u000bQ\u0006\u001c\b.\u001b8h_\nT\u0017aC3rk\u0006d\u0017\u000e^=pE*\f1B]8piV\u0004H-\u0019;fe\"\u001a\u0001\"!\u001b\u0011\u0007-\fY'C\u0002\u0002ni\u0013\u0011\u0002\u001e:b]NLWM\u001c;\u0016\u0005\u0005]\u0012\u0001C3rk\u0006d\u0017\u000e^=\u0016\u0005\u0005\u001d\u0013\u0001\u0002:p_R,\"!a\u0006\u0002\u0011I|w\u000e^0%KF$B!! \u0002\u0004B\u00191.a \n\u0007\u0005\u0005%L\u0001\u0003V]&$\b\"CAC\u0019\u0005\u0005\t\u0019AA\f\u0003\rAH%M\u0001\u0006e>|G\u000f\t\u0015\u0004\u001b\u0005-\u0005cA6\u0002\u000e&\u0019\u0011q\u0012.\u0003\u0011Y|G.\u0019;jY\u0016$R\u0001`AJ\u0003+Cq!!\u000e\u000f\u0001\u0004\t9\u0004C\u0004\u0002F9\u0001\r!a\u0012\u0015\u0003q\f!\"\\1q\r\u0006\u001cGo\u001c:z+\t\ti\n\u0005\u0003\u007f\u0003?[\u0018bAAQ1\nQQ*\u00199GC\u000e$xN]=\u0002\u0017]\u0014\u0018\u000e^3PE*,7\r\u001e\u000b\u0005\u0003{\n9\u000bC\u0004\u0002*F\u0001\r!a+\u0002\u0007=,H\u000f\u0005\u0003\u0002.\u0006MVBAAX\u0015\u0011\t\t,!\f\u0002\u0005%|\u0017\u0002BA[\u0003_\u0013!c\u00142kK\u000e$x*\u001e;qkR\u001cFO]3b[\u0006Q!/Z1e\u001f\nTWm\u0019;\u0015\t\u0005u\u00141\u0018\u0005\b\u0003{\u0013\u0002\u0019AA`\u0003\tIg\u000e\u0005\u0003\u0002.\u0006\u0005\u0017\u0002BAb\u0003_\u0013\u0011c\u00142kK\u000e$\u0018J\u001c9viN#(/Z1n\u0003!\u0019\u0015iU0S\u001f>#FCBAe\u0003\u001f\f\u0019\u000eE\u0002l\u0003\u0017L1!!4[\u0005\u001d\u0011un\u001c7fC:Dq!!5\u0014\u0001\u0004\t9\"\u0001\u0002pm\"9\u0011Q[\nA\u0002\u0005]\u0011A\u00018w\u0003!\u0011X-\u00193S_>$H\u0003BAn\u0003C\u0004R!^AoKFL1!a8W\u0005\u0015Iej\u001c3f\u0011%\t\u0019\u000f\u0006I\u0001\u0002\u0004\tI-A\u0003bE>\u0014H/\u0001\nsK\u0006$'k\\8uI\u0011,g-Y;mi\u0012\nTCAAuU\u0011\tI-a;,\u0005\u00055\b\u0003BAx\u0003sl!!!=\u000b\t\u0005M\u0018Q_\u0001\nk:\u001c\u0007.Z2lK\u0012T1!a>[\u0003)\tgN\\8uCRLwN\\\u0005\u0005\u0003w\f\tPA\tv]\u000eDWmY6fIZ\u000b'/[1oG\u0016\fqB\u0015#D'N{&+R!E?J{u\n\u0016\u000b\u0005\u00037\u0014\t\u0001C\u0005\u0002dZ\u0001\n\u00111\u0001\u0002J\u0006I\"\u000bR\"T'~\u0013V)\u0011#`%>{E\u000b\n3fM\u0006,H\u000e\u001e\u00132\u00039\u0011FiQ*T?\u000e{W\u000e\u001d7fi\u0016$B!a7\u0003\n!9\u00111\u001d\rA\u0002\u0005%\u0007f\u0001\r\u0003\u000eA!!q\u0002B\t\u001b\t\t)0\u0003\u0003\u0003\u0014\u0005U(a\u0002;bS2\u0014XmY\u0001\u000b%\u0012\u001b5kU0S\u001f>#F\u0003CAe\u00053\u0011YB!\n\t\u000f\u0005E\u0017\u00041\u0001\u0002\\\"9!QD\rA\u0002\t}\u0011\u0001D3ya\u0016\u001cG/\u001a3nC&t\u0007#B;\u0003\"\u0015\f\u0018b\u0001B\u0012-\nAQ*Y5o\u001d>$W\rC\u0004\u0002Vf\u0001\r!a7\u0002\u0011%t7/\u001a:uQ\u000e$\u0002\"! \u0003,\t=\"\u0011\b\u0005\u0007\u0005[Q\u0002\u0019A3\u0002\u0003-DqA!\r\u001b\u0001\u0004\u0011\u0019$\u0001\u0002iGB\u00191N!\u000e\n\u0007\t]\"LA\u0002J]RDaAa\u000f\u001b\u0001\u0004\t\u0018!\u0001<)\u0007i\u0011i!\u0001\u0006j]N,'\u000f^5gQ\u000e$BBa\u0011\u0003J\t-#Q\nB(\u0005'\u0002Ba\u001bB#c&\u0019!q\t.\u0003\r=\u0003H/[8o\u0011\u0019\u0011ic\u0007a\u0001K\"9!\u0011G\u000eA\u0002\tM\u0002B\u0002B\u001e7\u0001\u0007\u0011\u000fC\u0004\u0003Rm\u0001\r!a\u0006\u0002\t\r|g\u000e\u001a\u0005\b\u0005+Z\u0002\u0019AAe\u0003)1W\u000f\u001c7FcV\fGn\u001d\u0015\u00047\t5\u0011\u0001\u00037p_.,\b\u000f[2\u0015\r\u0005]!Q\fB0\u0011\u0019\u0011i\u0003\ba\u0001K\"9!\u0011\u0007\u000fA\u0002\tM\u0002f\u0001\u000f\u0003\u000e\u0005A!/Z7pm\u0016D7\r\u0006\u0006\u0003D\t\u001d$\u0011\u000eB6\u0005_BaA!\f\u001e\u0001\u0004)\u0007B\u0002B\u001e;\u0001\u0007\u0011\u000fC\u0004\u0003nu\u0001\rAa\r\u0002\u001bI,Wn\u001c<bYB{G.[2z\u0011\u001d\u0011\t$\ba\u0001\u0005gA3!\bB\u0007\u0003\u0019\u0019HO]5oOV\u0011!q\u000f\t\u0005\u0005s\u00129I\u0004\u0003\u0003|\t\r\u0005c\u0001B?56\u0011!q\u0010\u0006\u0004\u0005\u0003c\u0016A\u0002\u001fs_>$h(C\u0002\u0003\u0006j\u000ba\u0001\u0015:fI\u00164\u0017\u0002\u0002BE\u0005\u0017\u0013aa\u0015;sS:<'b\u0001BC5\u0006Q\u0011n\u001d*fC\u0012|e\u000e\\=\u0016\u0005\u0005%\u0017a\u00038p]J+\u0017\rZ(oYf\f\u0001b\u001d8baNDw\u000e\u001e\u0015\u0004C\t5\u0011\u0001\u0005:fC\u0012|e\u000e\\=T]\u0006\u00048\u000f[8u)\t\u0011Y\nE\u0003\u007f\u0005;+\u0017/\u0003\u0002x1\"\u001a!E!\u0004\u0002\u000b\rdW-\u0019:\u0015\u0005\u0005u\u0004fA\u0012\u0003\u000e\u0005Y1m\\7qkR,\u0007*Y:i)\u0011\u0011\u0019Da+\t\r\t5B\u00051\u0001f\u0003\u0019awn\\6vaR\u0019\u0011O!-\t\r\t5R\u00051\u0001fQ-)#Q\u0017B^\u0005{\u0013\tMa1\u0011\u0007-\u00149,C\u0002\u0003:j\u0013!\u0002Z3qe\u0016\u001c\u0017\r^3e\u0003\u001diWm]:bO\u0016\f#Aa0\u0002?U\u001bX\rI4fi>\u0013X\t\\:fQ-d\u0003E\\;mY&\u0002\u0013N\\:uK\u0006$g&A\u0003tS:\u001cW-\t\u0002\u0003F\u00061!GL\u00194]A\nQ!\u00199qYf$2!\u001dBf\u0011\u0019\u0011iC\na\u0001K\u0006\u0019q-\u001a;\u0015\t\t\r#\u0011\u001b\u0005\u0007\u0005[9\u0003\u0019A3\u0002\u0007A,H\u000f\u0006\u0004\u0003D\t]'1\u001c\u0005\u0007\u00053D\u0003\u0019A3\u0002\u0007-,\u0017\u0010\u0003\u0004\u0003^\"\u0002\r!]\u0001\u0006m\u0006dW/Z\u0001\u0007kB$\u0017\r^3\u0015\r\u0005u$1\u001dBs\u0011\u0019\u0011i#\u000ba\u0001K\"1!1H\u0015A\u0002E\fa!\u00193e\u001f:,G\u0003\u0002Bv\u0005[l\u0011\u0001\u0001\u0005\b\u0005_T\u0003\u0019\u0001By\u0003\tYg\u000fE\u0003l\u0005g,\u0017/C\u0002\u0003vj\u0013a\u0001V;qY\u0016\u0014\u0014A\u0002:f[>4X\r\u0006\u0003\u0003D\tm\bB\u0002B\u0017W\u0001\u0007Q-A\u0006tk\n$(/Y2u\u001f:,G\u0003\u0002Bv\u0007\u0003AaA!\f-\u0001\u0004)\u0017a\u00039vi&3\u0017IY:f]R$bAa\u0011\u0004\b\r%\u0001B\u0002B\u0017[\u0001\u0007Q\r\u0003\u0004\u0003<5\u0002\r!]\u0001\u0010O\u0016$xJ]#mg\u0016,\u0006\u000fZ1uKR)\u0011oa\u0004\u0004\u0012!1!Q\u0006\u0018A\u0002\u0015D\u0001ba\u0005/\t\u0003\u00071QC\u0001\u0003_B\u0004Ba[B\fc&\u00191\u0011\u0004.\u0003\u0011q\u0012\u0017P\\1nKz\"b!!3\u0004\u001e\r}\u0001B\u0002B\u0017_\u0001\u0007Q\r\u0003\u0004\u0003<=\u0002\r!]\u0001\fe\u0016lwN^3SK\u001a,\u0015\u000f\u0006\u0004\u0002J\u000e\u00152q\u0005\u0005\u0007\u0005[\u0001\u0004\u0019A3\t\r\tm\u0002\u00071\u0001r\u0003\u001d\u0011X\r\u001d7bG\u0016$\u0002\"!3\u0004.\r=21\u0007\u0005\u0007\u0005[\t\u0004\u0019A3\t\r\rE\u0012\u00071\u0001r\u0003!yG\u000e\u001a<bYV,\u0007BBB\u001bc\u0001\u0007\u0011/\u0001\u0005oK^4\u0018\r\\;f\u00031\u0011X\r\u001d7bG\u0016\u0014VMZ#r)!\tIma\u000f\u0004>\r\u0005\u0003B\u0002B\u0017e\u0001\u0007Q\r\u0003\u0004\u0004@I\u0002\r!]\u0001\t_2$g+\u00197vK\"111\t\u001aA\u0002E\f\u0001B\\3x-\u0006dW/\u001a\u000b\u0007\u0005\u0007\u001a9e!\u0013\t\r\t52\u00071\u0001f\u0011\u0019\u0011Yd\ra\u0001c\u0006A\u0011\u000e^3sCR|'/\u0006\u0002\u0004PA)ap!\u0015\u0003r&\u001911\u000b-\u0003\u0011%#XM]1u_J\faA^1mk\u0016\u001cXCAB-!\u0011q81L9\n\u0007\u0005\u001d\u0001,\u0001\u0004lKf\u001cV\r^\u000b\u0003\u0007C\u0002BA`B2K&\u00191Q\r-\u0003\u0007M+G/\u0001\u0003wS\u0016<XCAB6!\u0015q8QN3r\u0013\r\u0019y\u0007\u0017\u0002\b\u001b\u0006\u0004h+[3x\u0003)1\u0017\u000e\u001c;fe.+\u0017p\u001d\u000b\u0005\u0007W\u001a)\bC\u0004\u0004xa\u0002\ra!\u001f\u0002\u0003A\u0004ba[B>K\u0006%\u0017bAB?5\nIa)\u001e8di&|g.\r\u0015\fq\tU&1XBA\u0005\u0003\u0014\u0019-\t\u0002\u0004\u0004\u0006aXk]3!]YLWm\u001e\u0018gS2$XM]&fsNDc-\u000b\u0018!\u0003\u00022W\u000f^;sK\u00022XM]:j_:\u0004s/\u001b7mA%t7\r\\;eK\u0002\n\u0007e\u001d;sS\u000e$\bE^3sg&|g\u000eI8gAQD\u0017n\u001d\u0011nKRDw\u000e\u001a\u0011)M>\u0014\bE\\8xY\u0001rc/[3x]\u0019LG\u000e^3s\u0017\u0016L8\u000f\u000b9*]Q|W*\u00199*]\u0005IQ.\u00199WC2,Xm]\u000b\u0005\u0007\u0013\u001by\t\u0006\u0003\u0004\f\u000eM\u0005C\u0002@\u0004n\u0015\u001ci\tE\u0002g\u0007\u001f#aa!%:\u0005\u0004I'!A,\t\u000f\rU\u0015\b1\u0001\u0004\u0018\u0006\ta\r\u0005\u0004l\u0007w\n8Q\u0012\u0015\fs\tU&1XBN\u0005\u0003\u0014\u0019-\t\u0002\u0004\u001e\u0006QXk]3!]YLWm\u001e\u0018nCB4\u0016\r\\;fg\"2\u0017F\f\u0011BA\u0019,H/\u001e:fAY,'o]5p]\u0002:\u0018\u000e\u001c7!S:\u001cG.\u001e3fA\u0005\u00043\u000f\u001e:jGR\u0004c/\u001a:tS>t\u0007e\u001c4!i\"L7\u000fI7fi\"|G\r\t\u0015g_J\u0004cn\\<-A92\u0018.Z</[\u0006\u0004h+\u00197vKNDc-\u000b\u0018u_6\u000b\u0007/\u000b\u0018\u0002\tML'0Z\u000b\u0003\u0005g\t\u0011b\u001b8po:\u001c\u0016N_3\u0002\u000f%\u001cX)\u001c9us\u0006I1\r\\1tg:\u000bW.Z\u000b\u0003\u0007W\u0003Ba!,\u000446\u00111q\u0016\u0006\u0005\u0007c\u000bi#\u0001\u0003mC:<\u0017\u0002\u0002BE\u0007_\u000b!\u0002\\1ti>\u0003H/[8o+\t\u0019I\fE\u0003l\u0005\u000b\u0012\t\u0010K\u0004\u0001\u0007{\u0013ina1\u0011\u0007-\u001cy,C\u0002\u0004Bj\u0013\u0001cU3sS\u0006dg+\u001a:tS>tW+\u0013#\u001f\u0011]N;\u0015\u0019Er+R\rq\u0001\u0016:jK6\u000b\u0007\u000f\u0005\u0002v\u0001N)\u0001)a\u0006\u0002\u001eR\u00111qY\u0001\u0006K6\u0004H/_\u000b\u0007\u0007#\u001c9na7\u0016\u0005\rM\u0007CB;\u0001\u0007+\u001cI\u000eE\u0002g\u0007/$Q\u0001\u001b\"C\u0002%\u00042AZBn\t\u0015\u0019(I1\u0001j\u0003\u00111'o\\7\u0016\r\r\u00058q]Bv)\u0011\u0019\u0019o!<\u0011\rU\u00041Q]Bu!\r17q\u001d\u0003\u0006Q\u000e\u0013\r!\u001b\t\u0004M\u000e-H!B:D\u0005\u0004I\u0007bBBx\u0007\u0002\u00071\u0011_\u0001\u0003SR\u0004RA`Bz\u0007oL1a!>Y\u00051IE/\u001a:bE2,wJ\\2f!\u001dY'1_Bs\u0007S\f!B\\3x\u0005VLG\u000eZ3s+\u0019\u0019i\u0010\"\u0003\u0005\u000eU\u00111q \t\bA\u0012\u0005AQ\u0001C\b\u0013\r!\u0019!\u0019\u0002\u0010\u000fJ|w/\u00192mK\n+\u0018\u000e\u001c3feB91Na=\u0005\b\u0011-\u0001c\u00014\u0005\n\u0011)\u0001\u000e\u0012b\u0001SB\u0019a\r\"\u0004\u0005\u000bM$%\u0019A5\u0011\rU\u0004Aq\u0001C\u0006\u00031Ign\u001c3fkB$\u0017\r^3s+\t!)\u0002\u0005\u0005\u0002\"\u0005EBq\u0003C\u0018a\u0019!I\u0002\"\t\u0005,A9Q\u000fb\u0007\u0005 \u0011%\u0012b\u0001C\u000f-\nI\u0011JT8eK\n\u000b7/\u001a\t\u0004M\u0012\u0005BA\u0003C\u0012\r\u0006\u0005\t\u0011!B\u0001S\n\u0019q\f\n\u001b\u0002\u001b%tw\u000eZ3va\u0012\fG/\u001a:!Q\r1\u0015\u0011\u000e\t\u0004M\u0012-BA\u0003C\u0017\r\u0006\u0005\t\u0011!B\u0001S\n\u0019q\fJ\u001b1\r\u0011EBQ\u0007C\u001e!\u001d)(\u0011\u0005C\u001a\ts\u00012A\u001aC\u001b\t)!9DRA\u0001\u0002\u0003\u0015\t!\u001b\u0002\u0004?\u00122\u0004c\u00014\u0005<\u0011QAQ\b$\u0002\u0002\u0003\u0005)\u0011A5\u0003\u0007}#sG\u0001\bNC:<G.\u001a3ICND\u0017N\\4\u0016\t\u0011\rC\u0011J\n\u0006\u000f\u0006]AQ\t\t\u0007\u0003s\t\t\u0005b\u0012\u0011\u0007\u0019$I\u0005B\u0003i\u000f\n\u0007\u0011\u000e\u0006\u0002\u0005NA)AqJ$\u0005H5\t\u0001)\u0001\u0003iCNDG\u0003\u0002B\u001a\t+BqA!\fJ\u0001\u0004!9%A\u0007SK6|g/\u00197Q_2L7-\u001f\t\u0004\t\u001fZ%!\u0004*f[>4\u0018\r\u001c)pY&\u001c\u0017pE\u0002L\u0003/!\"\u0001\"\u0017\u0002\r\u0005cw/Y=t+\t!)g\u0004\u0002\u0005hu\t\u0001!A\u0004BY^\f\u0017p\u001d\u0011\u0002\u0015\u0019+H\u000e\\#rk\u0006d7/\u0006\u0002\u0005p=\u0011A\u0011O\u000f\u0002\u0003\u0005Ya)\u001e7m\u000bF,\u0018\r\\:!\u0003-\u0011VMZ3sK:\u001cW-R9\u0016\u0005\u0011etB\u0001C>;\u0005\u0011\u0011\u0001\u0004*fM\u0016\u0014XM\\2f\u000bF\u0004\u0013\u0001D:i_VdGMU3n_Z,W\u0003\u0002CB\t\u001b#B\u0001\"\"\u0005\u0014R1\u0011\u0011\u001aCD\t\u001fCq\u0001\"#T\u0001\u0004!Y)A\u0001b!\r1GQ\u0012\u0003\u0006gN\u0013\r!\u001b\u0005\b\t#\u001b\u0006\u0019\u0001CF\u0003\u0005\u0011\u0007b\u0002B7'\u0002\u0007!1G\u0001\roJLG/\u001a*fa2\f7-\u001a\u000b\u0003\t3\u0003Ba!,\u0005\u001c&!AQTBX\u0005\u0019y%M[3di\":\u0001i!0\u0003^\u0012\u0005f$A\u0002)\u000f}\u001aiL!8\u0005\"\u0002")
public final class TrieMap<K, V>
extends AbstractMap<K, V>
implements scala.collection.concurrent.Map<K, V>,
DefaultSerializable {
    private static final long serialVersionUID = -5212455458703321708L;
    private Hashing<K> hashingobj;
    private Equiv<K> equalityobj;
    private transient AtomicReferenceFieldUpdater<TrieMap<K, V>, Object> rootupdater;
    private volatile Object root;

    public static AtomicReferenceFieldUpdater<INodeBase<?, ?>, MainNode<?, ?>> inodeupdater() {
        return TrieMap$.MODULE$.inodeupdater();
    }

    public static <K, V> GrowableBuilder<Tuple2<K, V>, TrieMap<K, V>> newBuilder() {
        return TrieMap$.MODULE$.newBuilder();
    }

    public static <K, V> TrieMap<K, V> from(IterableOnce<Tuple2<K, V>> it) {
        return TrieMap$.MODULE$.from((IterableOnce)it);
    }

    @Override
    public Object writeReplace() {
        return DefaultSerializable.writeReplace$(this);
    }

    @Override
    public Option<V> updateWith(K key, Function1<Option<V>, Option<V>> remappingFunction) {
        return scala.collection.concurrent.Map.updateWith$(this, key, remappingFunction);
    }

    @Override
    public scala.collection.concurrent.Map<K, V> filterInPlaceImpl(Function2<K, V, Object> p) {
        return scala.collection.concurrent.Map.filterInPlaceImpl$(this, p);
    }

    @Override
    public scala.collection.concurrent.Map<K, V> mapValuesInPlaceImpl(Function2<K, V, V> f) {
        return scala.collection.concurrent.Map.mapValuesInPlaceImpl$(this, f);
    }

    public Hashing<K> hashing() {
        return this.hashingobj;
    }

    public Equiv<K> equality() {
        return this.equalityobj;
    }

    private Object root() {
        return this.root;
    }

    private void root_$eq(Object x$1) {
        this.root = x$1;
    }

    @Override
    public MapFactory<TrieMap> mapFactory() {
        return TrieMap$.MODULE$;
    }

    /*
     * WARNING - void declaration
     */
    private void writeObject(ObjectOutputStream out) {
        out.writeObject(this.hashingobj);
        out.writeObject(this.equalityobj);
        Iterator<Tuple2<K, V>> it = this.iterator();
        while (it.hasNext()) {
            void var5_5;
            void var4_4;
            Tuple2<K, V> tuple2 = it.next();
            if (tuple2 == null) {
                throw new MatchError((Object)null);
            }
            K k = tuple2._1();
            V v = tuple2._2();
            out.writeObject(var4_4);
            out.writeObject(var5_5);
        }
        out.writeObject(TrieMapSerializationEnd$.MODULE$);
    }

    private void readObject(ObjectInputStream in) {
        this.root_$eq(INode$.MODULE$.newRootNode(this.equality()));
        this.rootupdater = AtomicReferenceFieldUpdater.newUpdater(TrieMap.class, Object.class, "root");
        this.hashingobj = (Hashing)in.readObject();
        this.equalityobj = (Equiv)in.readObject();
        Object obj = in.readObject();
        while (true) {
            Object object = obj;
            TrieMapSerializationEnd$ trieMapSerializationEnd$ = TrieMapSerializationEnd$.MODULE$;
            if (object != null && object.equals(trieMapSerializationEnd$)) break;
            Object object2 = obj = in.readObject();
            TrieMapSerializationEnd$ trieMapSerializationEnd$2 = TrieMapSerializationEnd$.MODULE$;
            if (object2 != null && object2.equals(trieMapSerializationEnd$2)) continue;
            Object v = in.readObject();
            this.update(obj, v);
        }
    }

    private boolean CAS_ROOT(Object ov, Object nv) {
        return this.rootupdater.compareAndSet(this, ov, nv);
    }

    public INode<K, V> readRoot(boolean abort) {
        return this.RDCSS_READ_ROOT(abort);
    }

    public boolean readRoot$default$1() {
        return false;
    }

    public INode<K, V> RDCSS_READ_ROOT(boolean abort) {
        Object r = this.root();
        if (r instanceof INode) {
            return (INode)r;
        }
        if (r instanceof RDCSS_Descriptor) {
            return this.RDCSS_Complete(abort);
        }
        throw new MatchError(r);
    }

    public boolean RDCSS_READ_ROOT$default$1() {
        return false;
    }

    /*
     * WARNING - void declaration
     */
    private INode<K, V> RDCSS_Complete(boolean abort) {
        Object v;
        while (true) {
            void var5_5;
            void var4_4;
            if ((v = this.root()) instanceof INode) {
                return (INode)v;
            }
            if (!(v instanceof RDCSS_Descriptor)) break;
            RDCSS_Descriptor rDCSS_Descriptor = (RDCSS_Descriptor)v;
            if (rDCSS_Descriptor == null) {
                throw new MatchError((Object)null);
            }
            INode ov = rDCSS_Descriptor.old();
            MainNode exp = rDCSS_Descriptor.expectedmain();
            INode nv = rDCSS_Descriptor.nv();
            if (abort) {
                if (!this.CAS_ROOT(rDCSS_Descriptor, var4_4)) continue;
                return var4_4;
            }
            if (var4_4 == null) {
                throw null;
            }
            if (var4_4.GCAS_READ(this) == var5_5) {
                void var6_6;
                if (!this.CAS_ROOT(rDCSS_Descriptor, var6_6)) continue;
                rDCSS_Descriptor.committed_$eq(true);
                return var6_6;
            }
            if (!this.CAS_ROOT(rDCSS_Descriptor, var4_4)) continue;
            return var4_4;
        }
        throw new MatchError(v);
    }

    private boolean RDCSS_ROOT(INode<K, V> ov, MainNode<K, V> expectedmain, INode<K, V> nv) {
        RDCSS_Descriptor<K, V> desc = new RDCSS_Descriptor<K, V>(ov, expectedmain, nv);
        if (this.CAS_ROOT(ov, desc)) {
            this.RDCSS_Complete(false);
            return desc.committed();
        }
        return false;
    }

    private void inserthc(K k, int hc, V v) {
        INode<K, V> r;
        while (!(r = this.RDCSS_READ_ROOT(false)).rec_insert(k, v, hc, 0, null, r.gen, this)) {
        }
    }

    private Option<V> insertifhc(K k, int hc, V v, Object cond, boolean fullEquals) {
        INode<K, V> r;
        Option<V> ret;
        while ((ret = (r = this.RDCSS_READ_ROOT(false)).rec_insertif(k, v, hc, cond, fullEquals, 0, null, r.gen, this)) == null) {
        }
        return ret;
    }

    private Object lookuphc(K k, int hc) {
        INode r;
        Object res;
        while ((res = (r = this.RDCSS_READ_ROOT(false)).rec_lookup(k, hc, 0, null, r.gen, this)) == INodeBase.RESTART) {
        }
        return res;
    }

    private Option<V> removehc(K k, V v, int removalPolicy, int hc) {
        while (true) {
            INode<K, V> r;
            Option<V> res;
            if ((res = (r = this.RDCSS_READ_ROOT(false)).rec_remove(k, v, removalPolicy, hc, 0, null, r.gen, this)) == null) continue;
            return res;
        }
    }

    public String string() {
        return this.RDCSS_READ_ROOT(false).string(0);
    }

    public boolean isReadOnly() {
        return this.rootupdater == null;
    }

    public boolean nonReadOnly() {
        return this.rootupdater != null;
    }

    public TrieMap<K, V> snapshot() {
        MainNode<K, V> expmain;
        INode<K, V> r;
        do {
            if ((r = this.RDCSS_READ_ROOT(false)) != null) continue;
            throw null;
        } while (!this.RDCSS_ROOT(r, expmain = r.GCAS_READ(this), r.copyToGen(new Gen(), this)));
        return new TrieMap<K, V>(r.copyToGen(new Gen(), this), this.rootupdater, this.hashing(), this.equality());
    }

    public Map<K, V> readOnlySnapshot() {
        MainNode<K, V> expmain;
        INode<K, V> r;
        do {
            if ((r = this.RDCSS_READ_ROOT(false)) != null) continue;
            throw null;
        } while (!this.RDCSS_ROOT(r, expmain = r.GCAS_READ(this), r.copyToGen(new Gen(), this)));
        return new TrieMap<K, V>(r, null, this.hashing(), this.equality());
    }

    @Override
    public void clear() {
        INode<K, V> r;
        do {
            if ((r = this.RDCSS_READ_ROOT(false)) != null) continue;
            throw null;
        } while (!this.RDCSS_ROOT(r, r.GCAS_READ(this), INode$.MODULE$.newRootNode(this.equality())));
    }

    public int computeHash(K k) {
        return this.hashingobj.hash(k);
    }

    public V lookup(K k) {
        int hc = this.computeHash(k);
        Object lookupRes = this.lookuphc(k, hc);
        if (BoxesRunTime.equals(lookupRes, INodeBase.NO_SUCH_ELEMENT_SENTINEL)) {
            return null;
        }
        return (V)lookupRes;
    }

    @Override
    public V apply(K k) {
        int hc = this.computeHash(k);
        Object res = this.lookuphc(k, hc);
        if (res == INodeBase.NO_SUCH_ELEMENT_SENTINEL) {
            throw new NoSuchElementException();
        }
        return (V)res;
    }

    @Override
    public Option<V> get(K k) {
        int hc = this.computeHash(k);
        Object res = this.lookuphc(k, hc);
        if (res == INodeBase.NO_SUCH_ELEMENT_SENTINEL) {
            return None$.MODULE$;
        }
        return new Some<Object>(res);
    }

    @Override
    public Option<V> put(K key, V value) {
        int hc = this.computeHash(key);
        return this.insertifhc(key, hc, value, INode$.MODULE$.KEY_PRESENT_OR_ABSENT(), false);
    }

    @Override
    public void update(K k, V v) {
        int hc = this.computeHash(k);
        this.inserthc(k, hc, v);
    }

    public TrieMap<K, V> addOne(Tuple2<K, V> kv) {
        this.update(kv._1(), kv._2());
        return this;
    }

    @Override
    public Option<V> remove(K k) {
        int hc = this.computeHash(k);
        return this.removehc(k, null, 0, hc);
    }

    public TrieMap<K, V> subtractOne(K k) {
        this.remove(k);
        return this;
    }

    @Override
    public Option<V> putIfAbsent(K k, V v) {
        int hc = this.computeHash(k);
        return this.insertifhc(k, hc, v, INode$.MODULE$.KEY_ABSENT(), false);
    }

    @Override
    public V getOrElseUpdate(K k, Function0<V> op) {
        int hc = this.computeHash(k);
        Object object = this.lookuphc(k, hc);
        if (BoxesRunTime.equals(INodeBase.NO_SUCH_ELEMENT_SENTINEL, object)) {
            V v = op.apply();
            Option<V> option = this.insertifhc(k, hc, v, INode$.MODULE$.KEY_ABSENT(), false);
            if (option instanceof Some) {
                return (V)((Some)option).value();
            }
            if (None$.MODULE$.equals(option)) {
                return v;
            }
            throw new MatchError(option);
        }
        return (V)object;
    }

    @Override
    public boolean remove(K k, V v) {
        int hc = this.computeHash(k);
        Option<V> option = this.removehc(k, v, 1, hc);
        if (option == null) {
            throw null;
        }
        return option.isDefined();
    }

    @Override
    public boolean removeRefEq(K k, V v) {
        int hc = this.computeHash(k);
        Option<V> option = this.removehc(k, v, 2, hc);
        if (option == null) {
            throw null;
        }
        return option.isDefined();
    }

    @Override
    public boolean replace(K k, V oldvalue, V newvalue) {
        int hc = this.computeHash(k);
        Option<V> option = this.insertifhc(k, hc, newvalue, oldvalue, true);
        if (option == null) {
            throw null;
        }
        return option.isDefined();
    }

    @Override
    public boolean replaceRefEq(K k, V oldValue, V newValue) {
        int hc = this.computeHash(k);
        Option<V> option = this.insertifhc(k, hc, newValue, oldValue, false);
        if (option == null) {
            throw null;
        }
        return option.isDefined();
    }

    @Override
    public Option<V> replace(K k, V v) {
        int hc = this.computeHash(k);
        return this.insertifhc(k, hc, v, INode$.MODULE$.KEY_PRESENT(), false);
    }

    @Override
    public Iterator<Tuple2<K, V>> iterator() {
        if (this.nonReadOnly()) {
            return this.readOnlySnapshot().iterator();
        }
        return new TrieMapIterator(0, this, true);
    }

    @Override
    public Iterable<V> values() {
        if (this.nonReadOnly()) {
            return this.readOnlySnapshot().values();
        }
        return new DefaultSerializable(this){
            private final /* synthetic */ MapOps $outer;

            public Object writeReplace() {
                return DefaultSerializable.writeReplace$(this);
            }

            public int knownSize() {
                return this.$outer.knownSize();
            }

            public Iterator<V> iterator() {
                return this.$outer.valuesIterator();
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
            }
        };
    }

    @Override
    public Set<K> keySet() {
        if (this.nonReadOnly()) {
            return this.readOnlySnapshot().keySet();
        }
        return new MapOps.KeySet();
    }

    @Override
    public MapView<K, V> view() {
        if (this.nonReadOnly()) {
            return this.readOnlySnapshot().view();
        }
        return new MapView.Id(this);
    }

    @Override
    public MapView<K, V> filterKeys(Function1<K, Object> p) {
        return this.view().filterKeys(p);
    }

    @Override
    public <W> MapView<K, W> mapValues(Function1<V, W> f) {
        return this.view().mapValues(f);
    }

    @Override
    public int size() {
        if (this.nonReadOnly()) {
            return this.readOnlySnapshot().size();
        }
        return this.RDCSS_READ_ROOT(false).cachedSize(this);
    }

    @Override
    public int knownSize() {
        if (this.nonReadOnly()) {
            return -1;
        }
        return this.RDCSS_READ_ROOT(false).knownSize(this);
    }

    @Override
    public boolean isEmpty() {
        return (this.nonReadOnly() ? this.readOnlySnapshot() : this).sizeIs().sizeCompare(0) == 0;
    }

    @Override
    public String className() {
        return "TrieMap";
    }

    @Override
    public Option<Tuple2<K, V>> lastOption() {
        Option<Throwable> option;
        Try try_;
        if (this.isEmpty()) {
            return None$.MODULE$;
        }
        try {
            try_ = new Success<Tuple2>((Tuple2)IterableOps.last$(this));
        }
        catch (Throwable throwable) {
            if (throwable != null && !(option = NonFatal$.MODULE$.unapply(throwable)).isEmpty()) {
                Throwable apply_e = option.get();
                try_ = new Failure(apply_e);
            }
            throw throwable;
        }
        Object var1_2 = null;
        option = null;
        Object var3_4 = null;
        return ((Try)try_).toOption();
    }

    public static final /* synthetic */ Tuple2 $anonfun$lastOption$1(TrieMap $this) {
        return (Tuple2)$this.last();
    }

    private TrieMap(Object r, AtomicReferenceFieldUpdater<TrieMap<K, V>, Object> rtupd, Hashing<K> hashf, Equiv<K> ef) {
        this.hashingobj = hashf instanceof Hashing.Default ? new MangledHashing() : hashf;
        this.equalityobj = ef;
        this.rootupdater = rtupd;
        this.root = r;
    }

    public TrieMap(Hashing<K> hashf, Equiv<K> ef) {
        this(INode$.MODULE$.newRootNode(ef), AtomicReferenceFieldUpdater.newUpdater(TrieMap.class, Object.class, "root"), hashf, ef);
    }

    public TrieMap() {
        this(new Hashing.Default(), package$.MODULE$.Equiv().universal());
    }

    public static class MangledHashing<K>
    implements Hashing<K> {
        @Override
        public int hash(K k) {
            return Integer.reverseBytes(Statics.anyHash(k) * -1640532531) * -1640532531;
        }
    }
}

