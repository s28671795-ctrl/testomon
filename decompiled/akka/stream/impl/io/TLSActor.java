/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.io;

import akka.actor.Actor;
import akka.actor.ActorContext;
import akka.actor.ActorLogging;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.SupervisorStrategy;
import akka.annotation.InternalApi;
import akka.event.LoggingAdapter;
import akka.stream.TLSClosing;
import akka.stream.TLSProtocol;
import akka.stream.TLSProtocol$SessionTruncated$;
import akka.stream.impl.FanIn;
import akka.stream.impl.FanOut;
import akka.stream.impl.Pump;
import akka.stream.impl.TransferPhase;
import akka.stream.impl.TransferState;
import akka.stream.impl.fusing.ActorGraphInterpreter$Snapshot$;
import akka.stream.impl.io.TLSActor$;
import akka.stream.impl.io.TlsUtils$;
import akka.stream.snapshot.StreamSnapshotImpl;
import akka.util.ByteString;
import akka.util.ByteString$;
import java.io.Serializable;
import java.nio.ByteBuffer;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLEngineResult;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import scala.Function0;
import scala.Function1;
import scala.Function2;
import scala.MatchError;
import scala.Option;
import scala.PartialFunction;
import scala.collection.immutable.Seq;
import scala.package$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.Statics;
import scala.util.Failure;
import scala.util.Success;
import scala.util.Try;
import scala.util.control.NonFatal$;

@ScalaSignature(bytes="\u0006\u0005\r5wA\u00026l\u0011\u0003y7O\u0002\u0004vW\"\u0005qN\u001e\u0005\u0006{\u0006!\ta \u0005\b\u0003\u0003\tA\u0011AA\u0002\u0011%\t9(AI\u0001\n\u0003\tI\bC\u0005\u0002\u0010\u0006\u0011\r\u0011\"\u0002\u0002\u0012\"A\u0011qS\u0001!\u0002\u001b\t\u0019\nC\u0005\u0002\u001a\u0006\u0011\r\u0011\"\u0002\u0002\u0012\"A\u00111T\u0001!\u0002\u001b\t\u0019\nC\u0005\u0002\u001e\u0006\u0011\r\u0011\"\u0002\u0002 \"A\u0011QU\u0001!\u0002\u001b\t\t\u000bC\u0005\u0002(\u0006\u0011\r\u0011\"\u0002\u0002 \"A\u0011\u0011V\u0001!\u0002\u001b\t\tK\u0002\u0004vW\u0002y\u0017\u0011\u0018\u0005\u000b\u0003'i!\u0011!Q\u0001\n\u0005U\u0001BCA\u000f\u001b\t\u0005\t\u0015!\u0003\u0002 !Q\u0011\u0011I\u0007\u0003\u0002\u0003\u0006I!a\u0011\t\u0015\u0005\rTB!A!\u0002\u0013\t)\u0007\u0003\u0006\u0002p5\u0011\t\u0011)A\u0005\u0003cBa!`\u0007\u0005\u0002\u0005=\u0007\"CAo\u001b\u0001\u0007I\u0011BAp\u0011%\t\t/\u0004a\u0001\n\u0013\t\u0019\u000f\u0003\u0005\u0002j6\u0001\u000b\u0015BA9\u0011%\tY/\u0004b\u0001\n#\ti\u000f\u0003\u0005\u0003\u00105\u0001\u000b\u0011BAx\u0011%\u0011\t\"\u0004b\u0001\n#\u0011\u0019\u0002\u0003\u0005\u0003$5\u0001\u000b\u0011\u0002B\u000b\r\u0019\u0011)#\u0004\u0001\u0003(!Q!qF\u000e\u0003\u0002\u0003\u0006I!!\u0006\t\u0015\tE2D!A!\u0002\u0013\u0011\u0019\u0004\u0003\u0004~7\u0011\u0005!1\t\u0005\b\u0005\u001bZB\u0011IAp\u0011\u001d\u0011ye\u0007C!\u0003?D\u0011B!\u0015\u001c\u0001\u0004%IAa\u0015\t\u0013\t}3\u00041A\u0005\n\t\u0005\u0004\u0002\u0003B37\u0001\u0006KA!\u0016\t\u000f\t\u001d4\u0004\"\u0001\u0002`\"9!\u0011N\u000e\u0005\u0002\t-\u0004b\u0002BA7\u0011\u0005!1\u0011\u0005\b\u0005\u000f[B\u0011\u0001BE\u0011%\u0011i)\u0004b\u0001\n\u0013\u0011y\t\u0003\u0005\u0003\u00126\u0001\u000b\u0011\u0002B9\u0011%\u0011\u0019*\u0004b\u0001\n\u0013\u0011y\t\u0003\u0005\u0003\u00166\u0001\u000b\u0011\u0002B9\u0011%\u00119*\u0004b\u0001\n\u0013\u0011y\t\u0003\u0005\u0003\u001a6\u0001\u000b\u0011\u0002B9\u0011%\u0011Y*\u0004b\u0001\n\u0013\u0011y\t\u0003\u0005\u0003\u001e6\u0001\u000b\u0011\u0002B9\u0011%\u0011y*\u0004b\u0001\n\u0013\u0011\t\u000b\u0003\u0005\u0003$6\u0001\u000b\u0011\u0002B#\u0011%\u0011)+\u0004b\u0001\n\u0013\u0011\t\u000b\u0003\u0005\u0003(6\u0001\u000b\u0011\u0002B#\u0011%\u0011I+\u0004a\u0001\n\u0003\u0011Y\u000bC\u0005\u0003H6\u0001\r\u0011\"\u0001\u0003J\"A!QZ\u0007!B\u0013\u0011i\u000bC\u0005\u0003P6\u0001\r\u0011\"\u0001\u0002`\"I!\u0011[\u0007A\u0002\u0013\u0005!1\u001b\u0005\t\u0005/l\u0001\u0015)\u0003\u0002r!I!\u0011\\\u0007C\u0002\u0013\u0005!1\u001c\u0005\t\u0005;l\u0001\u0015!\u0003\u0002,!I!q\\\u0007A\u0002\u0013\u0005!\u0011\u001d\u0005\n\u0005Gl\u0001\u0019!C\u0001\u0005KD\u0001B!;\u000eA\u0003&\u0011\u0011\n\u0005\b\u0005WlA\u0011\u0001Bw\u0011%\u0019\t!\u0004b\u0001\n\u0003\u0019\u0019\u0001\u0003\u0005\u0004\f5\u0001\u000b\u0011BB\u0003\u0011%\u0019i!\u0004b\u0001\n\u0003\u0019y\u0001\u0003\u0005\u0004\u00165\u0001\u000b\u0011BB\t\u0011%\u00199\"\u0004b\u0001\n\u0003\u0019I\u0002\u0003\u0005\u0004 5\u0001\u000b\u0011BB\u000e\u0011%\u0019\t#\u0004b\u0001\n\u0003\u0019\u0019\u0003\u0003\u0005\u0004*5\u0001\u000b\u0011BB\u0013\u0011%\u0019Y#\u0004b\u0001\n\u0003\u0019i\u0003\u0003\u0005\u000405\u0001\u000b\u0011\u0002B\u0015\u0011%\u0019\t$\u0004b\u0001\n\u0003\u0019i\u0003\u0003\u0005\u000445\u0001\u000b\u0011\u0002B\u0015\u0011%\u0019)$\u0004b\u0001\n\u0003\u0019i\u0003\u0003\u0005\u000485\u0001\u000b\u0011\u0002B\u0015\u0011%\u0019I$\u0004b\u0001\n\u0003\u0019i\u0003\u0003\u0005\u0004<5\u0001\u000b\u0011\u0002B\u0015\u0011%\u0019i$\u0004b\u0001\n\u0003\u0019y\u0004\u0003\u0005\u0004H5\u0001\u000b\u0011BB!\u0011%\u0019I%\u0004b\u0001\n\u0003\u0019y\u0004\u0003\u0005\u0004L5\u0001\u000b\u0011BB!\u0011%\u0019i%\u0004b\u0001\n\u0003\u0019y\u0004\u0003\u0005\u0004P5\u0001\u000b\u0011BB!\u0011%\u0019\t&\u0004b\u0001\n\u0003\u0019y\u0004\u0003\u0005\u0004T5\u0001\u000b\u0011BB!\u0011%\u0019)&\u0004b\u0001\n\u0003\u0019y\u0004\u0003\u0005\u0004X5\u0001\u000b\u0011BB!\u0011\u001d\u0019I&\u0004C\u0001\u00077Bqa!\u0018\u000e\t\u0013\u0019y\u0006C\u0004\u0004j5!Iaa\u001b\t\u000f\rET\u0002\"\u0003\u0002`\"911O\u0007\u0005\u0002\rm\u0003bBB;\u001b\u0011\u000511\f\u0005\b\u0007ojA\u0011BB.\u0011\u001d\u0019I(\u0004C\u0005\u0007wBqaa#\u000e\t\u0013\u0019Y\u0006C\u0004\u0004\u00106!Iaa\u0017\t\u000f\rEU\u0002\"\u0011\u0004\u0014\"91\u0011U\u0007\u0005\u0012\r\r\u0006\"CB`\u001bE\u0005I\u0011CA=\u0011\u001d\u0019\t-\u0004C!\u00077Bqaa1\u000e\t#\u001a)\rC\u0004\u0004J6!\tfa\u0017\u0002\u0011Qc5+Q2u_JT!\u0001\\7\u0002\u0005%|'B\u00018p\u0003\u0011IW\u000e\u001d7\u000b\u0005A\f\u0018AB:ue\u0016\fWNC\u0001s\u0003\u0011\t7n[1\u0011\u0005Q\fQ\"A6\u0003\u0011Qc5+Q2u_J\u001c\"!A<\u0011\u0005a\\X\"A=\u000b\u0003i\fQa]2bY\u0006L!\u0001`=\u0003\r\u0005s\u0017PU3g\u0003\u0019a\u0014N\\5u}\r\u0001A#A:\u0002\u000bA\u0014x\u000e]:\u0015\u0019\u0005\u0015\u0011\u0011CA\u000e\u0003\u007f\t\t'!\u001c\u0011\t\u0005\u001d\u0011QB\u0007\u0003\u0003\u0013Q1!a\u0003r\u0003\u0015\t7\r^8s\u0013\u0011\ty!!\u0003\u0003\u000bA\u0013x\u000e]:\t\u000f\u0005M1\u00011\u0001\u0002\u0016\u0005\u0011R.\u0019=J]B,HOQ;gM\u0016\u00148+\u001b>f!\rA\u0018qC\u0005\u0004\u00033I(aA%oi\"9\u0011QD\u0002A\u0002\u0005}\u0011aD2sK\u0006$XmU*M\u000b:<\u0017N\\3\u0011\u000fa\f\t#!\n\u0002,%\u0019\u00111E=\u0003\u0013\u0019+hn\u0019;j_:\f\u0004\u0003BA\u0004\u0003OIA!!\u000b\u0002\n\tY\u0011i\u0019;peNK8\u000f^3n!\u0011\ti#a\u000f\u000e\u0005\u0005=\"\u0002BA\u0019\u0003g\t1a]:m\u0015\u0011\t)$a\u000e\u0002\u00079,GO\u0003\u0002\u0002:\u0005)!.\u0019<bq&!\u0011QHA\u0018\u0005%\u00196\u000bT#oO&tW\rC\u0004\u0002B\r\u0001\r!a\u0011\u0002\u001bY,'/\u001b4z'\u0016\u001c8/[8o!%A\u0018QIA\u0013\u0003\u0013\ny%C\u0002\u0002He\u0014\u0011BR;oGRLwN\u001c\u001a\u0011\t\u00055\u00121J\u0005\u0005\u0003\u001b\nyC\u0001\u0006T'2\u001bVm]:j_:\u0004b!!\u0015\u0002X\u0005mSBAA*\u0015\r\t)&_\u0001\u0005kRLG.\u0003\u0003\u0002Z\u0005M#a\u0001+ssB\u0019\u00010!\u0018\n\u0007\u0005}\u0013P\u0001\u0003V]&$\bbBA2\u0007\u0001\u0007\u0011QM\u0001\bG2|7/\u001b8h!\u0011\t9'!\u001b\u000e\u0003=L1!a\u001bp\u0005)!FjU\"m_NLgn\u001a\u0005\n\u0003_\u001a\u0001\u0013!a\u0001\u0003c\nq\u0001\u001e:bG&tw\rE\u0002y\u0003gJ1!!\u001ez\u0005\u001d\u0011un\u001c7fC:\fq\u0002\u001d:paN$C-\u001a4bk2$H%N\u000b\u0003\u0003wRC!!\u001d\u0002~-\u0012\u0011q\u0010\t\u0005\u0003\u0003\u000bY)\u0004\u0002\u0002\u0004*!\u0011QQAD\u0003%)hn\u00195fG.,GMC\u0002\u0002\nf\f!\"\u00198o_R\fG/[8o\u0013\u0011\ti)a!\u0003#Ut7\r[3dW\u0016$g+\u0019:jC:\u001cW-A\u0006Ue\u0006t7\u000f]8si&sWCAAJ\u001f\t\t)*H\u0001\u0001\u00031!&/\u00198ta>\u0014H/\u00138!\u00031!&/\u00198ta>\u0014HoT;u\u00035!&/\u00198ta>\u0014HoT;uA\u00059Qk]3s\u001fV$XCAAQ\u001f\t\t\u0019+H\u0001\u0002\u0003!)6/\u001a:PkR\u0004\u0013AB+tKJLe.A\u0004Vg\u0016\u0014\u0018J\u001c\u0011)\u0007\u0005\ti\u000b\u0005\u0003\u00020\u0006MVBAAY\u0015\r\tI)]\u0005\u0005\u0003k\u000b\tLA\u0006J]R,'O\\1m\u0003BL\u0007f\u0001\u0001\u0002.NAQb^A^\u0003\u0003\f9\r\u0005\u0003\u0002\b\u0005u\u0016\u0002BA`\u0003\u0013\u0011Q!Q2u_J\u0004B!a\u0002\u0002D&!\u0011QYA\u0005\u00051\t5\r^8s\u0019><w-\u001b8h!\u0011\tI-a3\u000e\u00035L1!!4n\u0005\u0011\u0001V/\u001c9\u0015\u0019\u0005E\u00171[Ak\u0003/\fI.a7\u0011\u0005Ql\u0001bBA\n'\u0001\u0007\u0011Q\u0003\u0005\b\u0003;\u0019\u0002\u0019AA\u0010\u0011\u001d\t\te\u0005a\u0001\u0003\u0007Bq!a\u0019\u0014\u0001\u0004\t)\u0007C\u0004\u0002pM\u0001\r!!\u001d\u0002\u000fM$x\u000e\u001d9fIV\u0011\u0011\u0011O\u0001\fgR|\u0007\u000f]3e?\u0012*\u0017\u000f\u0006\u0003\u0002\\\u0005\u0015\b\"CAt+\u0005\u0005\t\u0019AA9\u0003\rAH%M\u0001\tgR|\u0007\u000f]3eA\u0005Yq.\u001e;qkR\u0014UO\\2i+\t\ty\u000f\u0005\u0003\u0002r\n%a\u0002BAz\u0005\u000bqA!!>\u0003\u00049!\u0011q\u001fB\u0001\u001d\u0011\tI0a@\u000e\u0005\u0005m(bAA\u007f}\u00061AH]8pizJ\u0011A]\u0005\u0003aFL!A\\8\n\u0007\t\u001dQ.\u0001\u0004GC:|U\u000f^\u0005\u0005\u0005\u0017\u0011iAA\u0006PkR\u0004X\u000f\u001e\"v]\u000eD'b\u0001B\u0004[\u0006aq.\u001e;qkR\u0014UO\\2iA\u0005Q\u0011N\u001c9vi\n+hn\u00195\u0016\u0005\tU\u0001\u0003\u0002B\f\u0005;qA!a=\u0003\u001a%\u0019!1D7\u0002\u000b\u0019\u000bg.\u00138\n\t\t}!\u0011\u0005\u0002\u000b\u0013:\u0004X\u000f\u001e\"v]\u000eD'b\u0001B\u000e[\u0006Y\u0011N\u001c9vi\n+hn\u00195!\u00055\u0019\u0005n\u001c9qS:<'\t\\8dWN!1d\u001eB\u0015!\u0011\tIMa\u000b\n\u0007\t5RNA\u0007Ue\u0006t7OZ3s'R\fG/Z\u0001\u0004S\u0012D\u0018\u0001\u00028b[\u0016\u0004BA!\u000e\u0003>9!!q\u0007B\u001d!\r\tI0_\u0005\u0004\u0005wI\u0018A\u0002)sK\u0012,g-\u0003\u0003\u0003@\t\u0005#AB*ue&twMC\u0002\u0003<e$bA!\u0012\u0003J\t-\u0003c\u0001B$75\tQ\u0002C\u0004\u00030y\u0001\r!!\u0006\t\u000f\tEb\u00041\u0001\u00034\u00059\u0011n\u001d*fC\u0012L\u0018aC5t\u0007>l\u0007\u000f\\3uK\u0012\faAY;gM\u0016\u0014XC\u0001B+!\u0011\u00119Fa\u0017\u000e\u0005\te#bAA+c&!!Q\fB-\u0005)\u0011\u0015\u0010^3TiJLgnZ\u0001\u000bEV4g-\u001a:`I\u0015\fH\u0003BA.\u0005GB\u0011\"a:#\u0003\u0003\u0005\rA!\u0016\u0002\u000f\t,hMZ3sA\u00059\u0011n]#naRL\u0018\u0001C2i_BLe\u000e^8\u0015\t\u0005m#Q\u000e\u0005\b\u0005_*\u0003\u0019\u0001B9\u0003\u0005\u0011\u0007\u0003\u0002B:\u0005{j!A!\u001e\u000b\t\t]$\u0011P\u0001\u0004]&|'B\u0001B>\u0003\u0011Q\u0017M^1\n\t\t}$Q\u000f\u0002\u000b\u0005f$XMQ;gM\u0016\u0014\u0018a\u00029vi\n\u000b7m\u001b\u000b\u0005\u00037\u0012)\tC\u0004\u0003p\u0019\u0002\rA!\u001d\u0002\u000fA\u0014X\r]1sKR!\u00111\fBF\u0011\u001d\u0011yg\na\u0001\u0005c\n!\u0003\u001e:b]N\u0004xN\u001d;PkR\u0014UO\u001a4feV\u0011!\u0011O\u0001\u0014iJ\fgn\u001d9peR|U\u000f\u001e\"vM\u001a,'\u000fI\u0001\u000ekN,'oT;u\u0005V4g-\u001a:\u0002\u001dU\u001cXM](vi\n+hMZ3sA\u0005\tBO]1ogB|'\u000f^%o\u0005V4g-\u001a:\u0002%Q\u0014\u0018M\\:q_J$\u0018J\u001c\"vM\u001a,'\u000fI\u0001\rkN,'/\u00138Ck\u001a4WM]\u0001\u000ekN,'/\u00138Ck\u001a4WM\u001d\u0011\u0002'U\u001cXM]%o\u0007\"|\u0007\u000f]5oO\ncwnY6\u0016\u0005\t\u0015\u0013\u0001F;tKJLen\u00115paBLgn\u001a\"m_\u000e\\\u0007%\u0001\rue\u0006t7\u000f]8si&s7\t[8qa&twM\u00117pG.\f\u0011\u0004\u001e:b]N\u0004xN\u001d;J]\u000eCw\u000e\u001d9j]\u001e\u0014En\\2lA\u0005\u0019B.Y:u\u0011\u0006tGm\u001d5bW\u0016\u001cF/\u0019;vgV\u0011!Q\u0016\t\u0005\u0005_\u0013\tM\u0004\u0003\u00032\nuf\u0002\u0002BZ\u0005wsAA!.\u0003::!\u0011\u0011 B\\\u0013\t\tI$\u0003\u0003\u00026\u0005]\u0012\u0002BA\u0019\u0003gIAAa0\u00020\u0005y1k\u0015'F]\u001eLg.\u001a*fgVdG/\u0003\u0003\u0003D\n\u0015'a\u0004%b]\u0012\u001c\b.Y6f'R\fG/^:\u000b\t\t}\u0016qF\u0001\u0018Y\u0006\u001cH\u000fS1oIND\u0017m[3Ti\u0006$Xo]0%KF$B!a\u0017\u0003L\"I\u0011q]\u001b\u0002\u0002\u0003\u0007!QV\u0001\u0015Y\u0006\u001cH\u000fS1oIND\u0017m[3Ti\u0006$Xo\u001d\u0011\u0002\u0011\r|'o[+tKJ\fAbY8sWV\u001bXM]0%KF$B!a\u0017\u0003V\"I\u0011q\u001d\u001d\u0002\u0002\u0003\u0007\u0011\u0011O\u0001\nG>\u00148.V:fe\u0002\na!\u001a8hS:,WCAA\u0016\u0003\u001d)gnZ5oK\u0002\nabY;se\u0016tGoU3tg&|g.\u0006\u0002\u0002J\u0005\u00112-\u001e:sK:$8+Z:tS>tw\fJ3r)\u0011\tYFa:\t\u0013\u0005\u001dX(!AA\u0002\u0005%\u0013aD2veJ,g\u000e^*fgNLwN\u001c\u0011\u0002/M,GOT3x'\u0016\u001c8/[8o!\u0006\u0014\u0018-\\3uKJ\u001cH\u0003BA.\u0005_DqA!=@\u0001\u0004\u0011\u00190\u0001\u0004qCJ\fWn\u001d\t\u0005\u0005k\u0014YP\u0004\u0003\u0002v\n]\u0018b\u0001B}_\u0006YA\u000bT*Qe>$xnY8m\u0013\u0011\u0011iPa@\u0003'9+wm\u001c;jCR,g*Z<TKN\u001c\u0018n\u001c8\u000b\u0007\tex.A\bf]\u001eLg.\u001a(fK\u0012\u001cxK]1q+\t\u0019)AE\u0003\u0004\b]\u0014IC\u0002\u0004\u0004\n\u0005\u00031Q\u0001\u0002\ryI,g-\u001b8f[\u0016tGOP\u0001\u0011K:<\u0017N\\3OK\u0016$7o\u0016:ba\u0002\n\u0011#\u001a8hS:,\u0017J\u001c2pk:$w\n]3o+\t\u0019\tBE\u0003\u0004\u0014]\u0014IC\u0002\u0004\u0004\n\r\u00031\u0011C\u0001\u0013K:<\u0017N\\3J]\n|WO\u001c3Pa\u0016t\u0007%A\u0006vg\u0016\u0014\b*Y:ECR\fWCAB\u000e%\u0015\u0019ib\u001eB\u0015\r\u0019\u0019I!\u0012\u0001\u0004\u001c\u0005aQo]3s\u0011\u0006\u001cH)\u0019;bA\u0005\u0001Ro]3s\u001fV$8)\u00198dK2dW\rZ\u000b\u0003\u0007K\u0011Raa\nx\u0005S1aa!\u0003H\u0001\r\u0015\u0012!E;tKJ|U\u000f^\"b]\u000e,G\u000e\\3eA\u0005Aq.\u001e;c_VtG-\u0006\u0002\u0003*\u0005Iq.\u001e;c_VtG\rI\u0001\bS:\u0014w.\u001e8e\u0003!IgNY8v]\u0012\u0004\u0013AE8vi\n|WO\u001c3IC247\t\\8tK\u0012\f1c\\;uE>,h\u000e\u001a%bY\u001a\u001cEn\\:fI\u0002\n\u0011#\u001b8c_VtG\rS1mM\u000ecwn]3e\u0003IIgNY8v]\u0012D\u0015\r\u001c4DY>\u001cX\r\u001a\u0011\u0002\u001b\tLG-\u001b:fGRLwN\\1m+\t\u0019\t\u0005\u0005\u0003\u0002J\u000e\r\u0013bAB#[\niAK]1og\u001a,'\u000f\u00155bg\u0016\faBY5eSJ,7\r^5p]\u0006d\u0007%\u0001\tgYV\u001c\b.\u001b8h\u001fV$(m\\;oI\u0006\tb\r\\;tQ&twmT;uE>,h\u000e\u001a\u0011\u0002\u001b\u0005<\u0018-\u001b;j]\u001e\u001cEn\\:f\u00039\tw/Y5uS:<7\t\\8tK\u0002\nab\\;uE>,h\u000eZ\"m_N,G-A\bpkR\u0014w.\u001e8e\u00072|7/\u001a3!\u00035IgNY8v]\u0012\u001cEn\\:fI\u0006q\u0011N\u001c2pk:$7\t\\8tK\u0012\u0004\u0013aD2p[BdW\r^3Pe\u001acWo\u001d5\u0015\u0005\u0005m\u0013!\u00033p\u0013:\u0014w.\u001e8e)\u0019\t\th!\u0019\u0004f!911M.A\u0002\u0005E\u0014\u0001E5t\u001fV$(m\\;oI\u000ecwn]3e\u0011\u001d\u00199g\u0017a\u0001\u0005S\tA\"\u001b8c_VtGm\u0015;bi\u0016\f!\u0002Z8PkR\u0014w.\u001e8e)\u0011\tYf!\u001c\t\u000f\r=D\f1\u0001\u0002r\u0005y\u0011n]%oE>,h\u000eZ\"m_N,G-\u0001\tnCf\u001cEn\\:f\u001fV$(m\\;oI\u0006\u0001b\r\\;tQR{GK]1ogB|'\u000f^\u0001\fM2,8\u000f\u001b+p+N,'/\u0001\u0004e_^\u0013\u0018\r]\u0001\tI>,fn\u001e:baR!\u00111LB?\u0011\u001d\u0019y(\u0019a\u0001\u0003c\nA\"[4o_J,w*\u001e;qkRD3!YBB!\u0011\u0019)ia\"\u000e\u0005\u0005\u001d\u0015\u0002BBE\u0003\u000f\u0013q\u0001^1jYJ,7-A\tsk:$U\r\\3hCR,G\rV1tWND3AYBB\u0003EA\u0017M\u001c3tQ\u0006\\WMR5oSNDW\rZ\u0001\be\u0016\u001cW-\u001b<f+\t\u0019)\nE\u0004y\u0007/\u001bY*a\u0017\n\u0007\re\u0015PA\bQCJ$\u0018.\u00197Gk:\u001cG/[8o!\rA8QT\u0005\u0004\u0007?K(aA!os\u0006!a-Y5m)\u0019\tYf!*\u0004<\"91qU3A\u0002\r%\u0016!A3\u0011\t\r-6Q\u0017\b\u0005\u0007[\u001b\tL\u0004\u0003\u0002z\u000e=\u0016\"\u0001>\n\u0007\rM\u00160A\u0004qC\u000e\\\u0017mZ3\n\t\r]6\u0011\u0018\u0002\n)\"\u0014xn^1cY\u0016T1aa-z\u0011%\u0019i,\u001aI\u0001\u0002\u0004\t\t(\u0001\bdY>\u001cX\r\u0016:b]N\u0004xN\u001d;\u0002\u001d\u0019\f\u0017\u000e\u001c\u0013eK\u001a\fW\u000f\u001c;%e\u0005A\u0001o\\:u'R|\u0007/\u0001\u0006qk6\u0004h)Y5mK\u0012$B!a\u0017\u0004H\"91q\u00155A\u0002\r%\u0016\u0001\u00049v[B4\u0015N\\5tQ\u0016$\u0007fA\u0007\u0002.\u0002")
@InternalApi
public class TLSActor
implements Actor,
ActorLogging,
Pump {
    public final int akka$stream$impl$io$TLSActor$$maxInputBufferSize;
    private final Function1<ActorSystem, SSLEngine> createSSLEngine;
    private final Function2<ActorSystem, SSLSession, Try<BoxedUnit>> verifySession;
    private final TLSClosing closing;
    public final boolean akka$stream$impl$io$TLSActor$$tracing;
    private boolean stopped;
    private final FanOut.OutputBunch outputBunch;
    private final FanIn.InputBunch inputBunch;
    private final ByteBuffer transportOutBuffer;
    private final ByteBuffer userOutBuffer;
    private final ByteBuffer transportInBuffer;
    private final ByteBuffer userInBuffer;
    private final ChoppingBlock akka$stream$impl$io$TLSActor$$userInChoppingBlock;
    private final ChoppingBlock transportInChoppingBlock;
    private SSLEngineResult.HandshakeStatus lastHandshakeStatus;
    private boolean corkUser;
    private final SSLEngine engine;
    private SSLSession currentSession;
    private final TransferState engineNeedsWrap;
    private final TransferState engineInboundOpen;
    private final TransferState userHasData;
    private final TransferState userOutCancelled;
    private final TransferState outbound;
    private final TransferState inbound;
    private final TransferState outboundHalfClosed;
    private final TransferState inboundHalfClosed;
    private final TransferPhase bidirectional;
    private final TransferPhase flushingOutbound;
    private final TransferPhase awaitingClose;
    private final TransferPhase outboundClosed;
    private final TransferPhase inboundClosed;
    private TransferState akka$stream$impl$Pump$$transferState;
    private Function0<BoxedUnit> akka$stream$impl$Pump$$currentAction;
    private TransferPhase completedPhase;
    private LoggingAdapter akka$actor$ActorLogging$$_log;
    private ActorContext context;
    private ActorRef self;

    public static int UserIn() {
        return TLSActor$.MODULE$.UserIn();
    }

    public static int UserOut() {
        return TLSActor$.MODULE$.UserOut();
    }

    public static int TransportOut() {
        return TLSActor$.MODULE$.TransportOut();
    }

    public static int TransportIn() {
        return TLSActor$.MODULE$.TransportIn();
    }

    public static boolean props$default$5() {
        return TLSActor$.MODULE$.props$default$5();
    }

    public static Props props(int maxInputBufferSize, Function1<ActorSystem, SSLEngine> createSSLEngine, Function2<ActorSystem, SSLSession, Try<BoxedUnit>> verifySession, TLSClosing closing2, boolean tracing) {
        return TLSActor$.MODULE$.props(maxInputBufferSize, createSSLEngine, verifySession, closing2, tracing);
    }

    @Override
    public final void initialPhase(int waitForUpstream, TransferPhase andThen) {
        Pump.initialPhase$(this, waitForUpstream, andThen);
    }

    @Override
    public final void waitForUpstreams(int waitForUpstream) {
        Pump.waitForUpstreams$(this, waitForUpstream);
    }

    @Override
    public void gotUpstreamSubscription() {
        Pump.gotUpstreamSubscription$(this);
    }

    @Override
    public final void nextPhase(TransferPhase phase) {
        Pump.nextPhase$(this, phase);
    }

    @Override
    public final boolean isPumpFinished() {
        return Pump.isPumpFinished$(this);
    }

    @Override
    public final void pump() {
        Pump.pump$(this);
    }

    @Override
    public LoggingAdapter log() {
        return ActorLogging.log$(this);
    }

    @Override
    public final ActorRef sender() {
        return Actor.sender$(this);
    }

    @Override
    @InternalApi
    public void aroundReceive(PartialFunction<Object, BoxedUnit> receive2, Object msg) {
        Actor.aroundReceive$(this, receive2, msg);
    }

    @Override
    @InternalApi
    public void aroundPreStart() {
        Actor.aroundPreStart$(this);
    }

    @Override
    @InternalApi
    public void aroundPostStop() {
        Actor.aroundPostStop$(this);
    }

    @Override
    @InternalApi
    public void aroundPreRestart(Throwable reason, Option<Object> message) {
        Actor.aroundPreRestart$(this, reason, message);
    }

    @Override
    @InternalApi
    public void aroundPostRestart(Throwable reason) {
        Actor.aroundPostRestart$(this, reason);
    }

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return Actor.supervisorStrategy$(this);
    }

    @Override
    public void preStart() throws Exception {
        Actor.preStart$(this);
    }

    @Override
    public void preRestart(Throwable reason, Option<Object> message) throws Exception {
        Actor.preRestart$(this, reason, message);
    }

    @Override
    public void postRestart(Throwable reason) throws Exception {
        Actor.postRestart$(this, reason);
    }

    @Override
    public void unhandled(Object message) {
        Actor.unhandled$(this, message);
    }

    @Override
    public TransferState akka$stream$impl$Pump$$transferState() {
        return this.akka$stream$impl$Pump$$transferState;
    }

    @Override
    public void akka$stream$impl$Pump$$transferState_$eq(TransferState x$1) {
        this.akka$stream$impl$Pump$$transferState = x$1;
    }

    @Override
    public Function0<BoxedUnit> akka$stream$impl$Pump$$currentAction() {
        return this.akka$stream$impl$Pump$$currentAction;
    }

    @Override
    public void akka$stream$impl$Pump$$currentAction_$eq(Function0<BoxedUnit> x$1) {
        this.akka$stream$impl$Pump$$currentAction = x$1;
    }

    @Override
    public final TransferPhase completedPhase() {
        return this.completedPhase;
    }

    @Override
    public final void akka$stream$impl$Pump$_setter_$completedPhase_$eq(TransferPhase x$1) {
        this.completedPhase = x$1;
    }

    @Override
    public LoggingAdapter akka$actor$ActorLogging$$_log() {
        return this.akka$actor$ActorLogging$$_log;
    }

    @Override
    public void akka$actor$ActorLogging$$_log_$eq(LoggingAdapter x$1) {
        this.akka$actor$ActorLogging$$_log = x$1;
    }

    @Override
    public ActorContext context() {
        return this.context;
    }

    @Override
    public final ActorRef self() {
        return this.self;
    }

    @Override
    public void akka$actor$Actor$_setter_$context_$eq(ActorContext x$1) {
        this.context = x$1;
    }

    @Override
    public final void akka$actor$Actor$_setter_$self_$eq(ActorRef x$1) {
        this.self = x$1;
    }

    private boolean stopped() {
        return this.stopped;
    }

    private void stopped_$eq(boolean x$1) {
        this.stopped = x$1;
    }

    public FanOut.OutputBunch outputBunch() {
        return this.outputBunch;
    }

    public FanIn.InputBunch inputBunch() {
        return this.inputBunch;
    }

    private ByteBuffer transportOutBuffer() {
        return this.transportOutBuffer;
    }

    private ByteBuffer userOutBuffer() {
        return this.userOutBuffer;
    }

    private ByteBuffer transportInBuffer() {
        return this.transportInBuffer;
    }

    private ByteBuffer userInBuffer() {
        return this.userInBuffer;
    }

    public ChoppingBlock akka$stream$impl$io$TLSActor$$userInChoppingBlock() {
        return this.akka$stream$impl$io$TLSActor$$userInChoppingBlock;
    }

    private ChoppingBlock transportInChoppingBlock() {
        return this.transportInChoppingBlock;
    }

    public SSLEngineResult.HandshakeStatus lastHandshakeStatus() {
        return this.lastHandshakeStatus;
    }

    public void lastHandshakeStatus_$eq(SSLEngineResult.HandshakeStatus x$1) {
        this.lastHandshakeStatus = x$1;
    }

    public boolean corkUser() {
        return this.corkUser;
    }

    public void corkUser_$eq(boolean x$1) {
        this.corkUser = x$1;
    }

    public SSLEngine engine() {
        return this.engine;
    }

    public SSLSession currentSession() {
        return this.currentSession;
    }

    public void currentSession_$eq(SSLSession x$1) {
        this.currentSession = x$1;
    }

    public void setNewSessionParameters(TLSProtocol.NegotiateNewSession params) {
        if (this.akka$stream$impl$io$TLSActor$$tracing) {
            this.log().debug(new StringBuilder(9).append("applying ").append(params).toString());
        }
        this.currentSession().invalidate();
        TlsUtils$.MODULE$.applySessionParameters(this.engine(), params);
        this.engine().beginHandshake();
        this.lastHandshakeStatus_$eq(this.engine().getHandshakeStatus());
        this.corkUser_$eq(true);
    }

    public TransferState engineNeedsWrap() {
        return this.engineNeedsWrap;
    }

    public TransferState engineInboundOpen() {
        return this.engineInboundOpen;
    }

    public TransferState userHasData() {
        return this.userHasData;
    }

    public TransferState userOutCancelled() {
        return this.userOutCancelled;
    }

    public TransferState outbound() {
        return this.outbound;
    }

    public TransferState inbound() {
        return this.inbound;
    }

    public TransferState outboundHalfClosed() {
        return this.outboundHalfClosed;
    }

    public TransferState inboundHalfClosed() {
        return this.inboundHalfClosed;
    }

    public TransferPhase bidirectional() {
        return this.bidirectional;
    }

    public TransferPhase flushingOutbound() {
        return this.flushingOutbound;
    }

    public TransferPhase awaitingClose() {
        return this.awaitingClose;
    }

    public TransferPhase outboundClosed() {
        return this.outboundClosed;
    }

    public TransferPhase inboundClosed() {
        return this.inboundClosed;
    }

    public void completeOrFlush() {
        if (this.engine().isOutboundDone() || this.engine().isInboundDone() && this.akka$stream$impl$io$TLSActor$$userInChoppingBlock().isEmpty()) {
            this.nextPhase(this.completedPhase());
            return;
        }
        this.nextPhase(this.flushingOutbound());
    }

    private boolean doInbound(boolean isOutboundClosed, TransferState inboundState) {
        if (this.inputBunch().isDepleted(0) && this.transportInChoppingBlock().isEmpty()) {
            if (this.akka$stream$impl$io$TLSActor$$tracing) {
                this.log().debug("closing inbound");
            }
            try {
                this.engine().closeInbound();
            }
            catch (SSLException sSLException) {
                this.outputBunch().enqueue(1, TLSProtocol$SessionTruncated$.MODULE$);
            }
            this.lastHandshakeStatus_$eq(this.engine().getHandshakeStatus());
            this.completeOrFlush();
            return false;
        }
        TransferState transferState = inboundState;
        TransferState transferState2 = this.inboundHalfClosed();
        if ((transferState == null ? transferState2 != null : !transferState.equals(transferState2)) && this.outputBunch().isCancelled(1)) {
            if (!isOutboundClosed && this.closing.ignoreCancel()) {
                if (this.akka$stream$impl$io$TLSActor$$tracing) {
                    this.log().debug("ignoring UserIn cancellation");
                }
                this.nextPhase(this.inboundClosed());
            } else {
                if (this.akka$stream$impl$io$TLSActor$$tracing) {
                    this.log().debug("closing inbound due to UserOut cancellation");
                }
                this.engine().closeOutbound();
                this.lastHandshakeStatus_$eq(this.engine().getHandshakeStatus());
                this.nextPhase(this.flushingOutbound());
            }
            return true;
        }
        if (inboundState.isReady()) {
            boolean bl;
            this.transportInChoppingBlock().chopInto(this.transportInBuffer());
            try {
                this.doUnwrap(false);
                bl = true;
            }
            catch (SSLException ex) {
                if (this.akka$stream$impl$io$TLSActor$$tracing) {
                    this.log().debug(new StringBuilder(30).append("SSLException during doUnwrap: ").append(ex).toString());
                }
                this.fail(ex, false);
                this.engine().closeInbound();
                this.completeOrFlush();
                bl = false;
            }
            return bl;
        }
        return true;
    }

    private void doOutbound(boolean isInboundClosed) {
        if (this.inputBunch().isDepleted(1) && this.akka$stream$impl$io$TLSActor$$userInChoppingBlock().isEmpty() && this.mayCloseOutbound()) {
            if (!isInboundClosed && this.closing.ignoreComplete()) {
                if (this.akka$stream$impl$io$TLSActor$$tracing) {
                    this.log().debug("ignoring closeOutbound");
                }
            } else {
                if (this.akka$stream$impl$io$TLSActor$$tracing) {
                    this.log().debug("closing outbound directly");
                }
                this.engine().closeOutbound();
                this.lastHandshakeStatus_$eq(this.engine().getHandshakeStatus());
            }
            this.nextPhase(this.outboundClosed());
            return;
        }
        if (this.outputBunch().isCancelled(0)) {
            if (this.akka$stream$impl$io$TLSActor$$tracing) {
                this.log().debug("shutting down because TransportOut is cancelled");
            }
            this.nextPhase(this.completedPhase());
            return;
        }
        if (this.outbound().isReady()) {
            if (this.userHasData().isReady()) {
                this.akka$stream$impl$io$TLSActor$$userInChoppingBlock().chopInto(this.userInBuffer());
            }
            try {
                this.doWrap();
            }
            catch (SSLException ex) {
                if (this.akka$stream$impl$io$TLSActor$$tracing) {
                    this.log().debug(new StringBuilder(28).append("SSLException during doWrap: ").append(ex).toString());
                }
                this.fail(ex, false);
                this.completeOrFlush();
            }
            return;
        }
    }

    private boolean mayCloseOutbound() {
        SSLEngineResult.HandshakeStatus handshakeStatus = this.lastHandshakeStatus();
        return ((Object)((Object)SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING)).equals((Object)handshakeStatus) ? true : ((Object)((Object)SSLEngineResult.HandshakeStatus.FINISHED)).equals((Object)handshakeStatus);
    }

    public void flushToTransport() {
        if (this.akka$stream$impl$io$TLSActor$$tracing) {
            this.log().debug("flushToTransport");
        }
        this.transportOutBuffer().flip();
        if (this.transportOutBuffer().hasRemaining()) {
            ByteString bs = ByteString$.MODULE$.apply(this.transportOutBuffer());
            this.outputBunch().enqueue(0, bs);
            if (this.akka$stream$impl$io$TLSActor$$tracing) {
                this.log().debug(new StringBuilder(14).append("sending ").append(bs.size()).append(" bytes").toString());
            }
        }
        this.transportOutBuffer().clear();
    }

    public void flushToUser() {
        if (this.akka$stream$impl$io$TLSActor$$tracing) {
            this.log().debug("flushToUser");
        }
        this.userOutBuffer().flip();
        if (this.userOutBuffer().hasRemaining()) {
            ByteString bs = ByteString$.MODULE$.apply(this.userOutBuffer());
            this.outputBunch().enqueue(1, new TLSProtocol.SessionBytes(this.currentSession(), bs));
        }
        this.userOutBuffer().clear();
    }

    private void doWrap() {
        SSLEngineResult result = this.engine().wrap(this.userInBuffer(), this.transportOutBuffer());
        this.lastHandshakeStatus_$eq(result.getHandshakeStatus());
        if (this.akka$stream$impl$io$TLSActor$$tracing) {
            this.log().debug(new StringBuilder(40).append("wrap: status=").append((Object)result.getStatus()).append(" handshake=").append((Object)this.lastHandshakeStatus()).append(" remaining=").append(this.userInBuffer().remaining()).append(" out=").append(this.transportOutBuffer().position()).toString());
        }
        SSLEngineResult.HandshakeStatus handshakeStatus = this.lastHandshakeStatus();
        SSLEngineResult.HandshakeStatus handshakeStatus2 = SSLEngineResult.HandshakeStatus.FINISHED;
        if (!(handshakeStatus != null ? !((Object)((Object)handshakeStatus)).equals((Object)handshakeStatus2) : handshakeStatus2 != null)) {
            this.handshakeFinished();
        }
        this.runDelegatedTasks();
        SSLEngineResult.Status status = result.getStatus();
        if (((Object)((Object)SSLEngineResult.Status.OK)).equals((Object)status)) {
            if (this.transportOutBuffer().position() == 0) {
                SSLEngineResult.HandshakeStatus handshakeStatus3 = this.lastHandshakeStatus();
                SSLEngineResult.HandshakeStatus handshakeStatus4 = SSLEngineResult.HandshakeStatus.NEED_WRAP;
                if (!(handshakeStatus3 != null ? !((Object)((Object)handshakeStatus3)).equals((Object)handshakeStatus4) : handshakeStatus4 != null)) {
                    throw new IllegalStateException("SSLEngine trying to loop NEED_WRAP without producing output");
                }
            }
            this.flushToTransport();
            this.akka$stream$impl$io$TLSActor$$userInChoppingBlock().putBack(this.userInBuffer());
            return;
        }
        if (((Object)((Object)SSLEngineResult.Status.CLOSED)).equals((Object)status)) {
            this.flushToTransport();
            if (this.engine().isInboundDone()) {
                this.nextPhase(this.completedPhase());
                return;
            }
            this.nextPhase(this.awaitingClose());
            return;
        }
        this.fail(new IllegalStateException(new StringBuilder(30).append("unexpected status ").append((Object)status).append(" in doWrap()").toString()), this.fail$default$2());
    }

    private void doUnwrap(boolean ignoreOutput) {
        block14: {
            SSLEngineResult.Status status;
            block13: {
                while (true) {
                    int oldInPosition = this.transportInBuffer().position();
                    SSLEngineResult result = this.engine().unwrap(this.transportInBuffer(), this.userOutBuffer());
                    Object object = ignoreOutput ? this.userOutBuffer().clear() : BoxedUnit.UNIT;
                    this.lastHandshakeStatus_$eq(result.getHandshakeStatus());
                    if (this.akka$stream$impl$io$TLSActor$$tracing) {
                        this.log().debug(new StringBuilder(42).append("unwrap: status=").append((Object)result.getStatus()).append(" handshake=").append((Object)this.lastHandshakeStatus()).append(" remaining=").append(this.transportInBuffer().remaining()).append(" out=").append(this.userOutBuffer().position()).toString());
                    }
                    this.runDelegatedTasks();
                    status = result.getStatus();
                    if (!((Object)((Object)SSLEngineResult.Status.OK)).equals((Object)status)) break block13;
                    SSLEngineResult.HandshakeStatus handshakeStatus = result.getHandshakeStatus();
                    if (((Object)((Object)SSLEngineResult.HandshakeStatus.NEED_WRAP)).equals((Object)handshakeStatus)) {
                        this.transportInChoppingBlock().putBack(this.transportInBuffer());
                        break block14;
                    }
                    if (((Object)((Object)SSLEngineResult.HandshakeStatus.FINISHED)).equals((Object)handshakeStatus)) {
                        this.flushToUser();
                        this.handshakeFinished();
                        this.transportInChoppingBlock().putBack(this.transportInBuffer());
                        break block14;
                    }
                    if (((Object)((Object)SSLEngineResult.HandshakeStatus.NEED_UNWRAP)).equals((Object)handshakeStatus) && this.transportInBuffer().hasRemaining() && this.userOutBuffer().position() == 0 && this.transportInBuffer().position() == oldInPosition) {
                        throw new IllegalStateException("SSLEngine trying to loop NEED_UNWRAP without producing output");
                    }
                    if (!this.transportInBuffer().hasRemaining()) break;
                    ignoreOutput = false;
                }
                this.flushToUser();
                break block14;
            }
            if (((Object)((Object)SSLEngineResult.Status.CLOSED)).equals((Object)status)) {
                this.flushToUser();
                this.completeOrFlush();
            } else if (((Object)((Object)SSLEngineResult.Status.BUFFER_UNDERFLOW)).equals((Object)status)) {
                this.flushToUser();
            } else if (((Object)((Object)SSLEngineResult.Status.BUFFER_OVERFLOW)).equals((Object)status)) {
                this.flushToUser();
                this.transportInChoppingBlock().putBack(this.transportInBuffer());
            } else if (status == null) {
                this.fail(new IllegalStateException("unexpected status 'null' in doUnwrap()"), this.fail$default$2());
            } else {
                throw new MatchError((Object)status);
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void runDelegatedTasks() {
        BoxedUnit boxedUnit;
        Runnable task;
        while ((task = this.engine().getDelegatedTask()) != null) {
            if (this.akka$stream$impl$io$TLSActor$$tracing) {
                this.log().debug("running task");
            }
            task.run();
        }
        SSLEngineResult.HandshakeStatus st = this.lastHandshakeStatus();
        this.lastHandshakeStatus_$eq(this.engine().getHandshakeStatus());
        if (this.akka$stream$impl$io$TLSActor$$tracing) {
            SSLEngineResult.HandshakeStatus handshakeStatus = st;
            SSLEngineResult.HandshakeStatus handshakeStatus2 = this.lastHandshakeStatus();
            if (handshakeStatus == null ? handshakeStatus2 != null : !((Object)((Object)handshakeStatus)).equals((Object)handshakeStatus2)) {
                this.log().debug(new StringBuilder(30).append("handshake status after tasks: ").append((Object)this.lastHandshakeStatus()).toString());
                boxedUnit = BoxedUnit.UNIT;
                return;
            }
        }
        boxedUnit = BoxedUnit.UNIT;
    }

    private void handshakeFinished() {
        if (this.akka$stream$impl$io$TLSActor$$tracing) {
            this.log().debug("handshake finished");
        }
        SSLSession session = this.engine().getSession();
        Try<BoxedUnit> try_ = this.verifySession.apply(this.context().system(), session);
        if (try_ instanceof Success) {
            Success success = (Success)try_;
            BoxedUnit boxedUnit = (BoxedUnit)success.value();
            BoxedUnit boxedUnit2 = BoxedUnit.UNIT;
            BoxedUnit boxedUnit3 = boxedUnit;
            if (!(boxedUnit2 != null ? !((Object)boxedUnit2).equals(boxedUnit3) : boxedUnit3 != null)) {
                this.currentSession_$eq(session);
                this.corkUser_$eq(false);
                this.flushToUser();
                return;
            }
        }
        if (try_ instanceof Failure) {
            Failure failure = (Failure)try_;
            Throwable ex = failure.exception();
            this.fail(ex, true);
            return;
        }
        throw new MatchError(try_);
    }

    @Override
    public PartialFunction<Object, BoxedUnit> receive() {
        return this.inputBunch().subreceive().orElse(this.outputBunch().subreceive()).orElse(new Serializable(this){
            private static final long serialVersionUID = 0L;
            private final /* synthetic */ TLSActor $outer;

            public final <A1, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                A1 A1 = x1;
                if (ActorGraphInterpreter$Snapshot$.MODULE$.equals(A1)) {
                    this.$outer.sender().$bang(new StreamSnapshotImpl(this.$outer.self().path(), (Seq)package$.MODULE$.Seq().empty(), (Seq)package$.MODULE$.Seq().empty()), this.$outer.self());
                    return (B1)BoxedUnit.UNIT;
                }
                return function1.apply(x1);
            }

            public final boolean isDefinedAt(Object x1) {
                Object object = x1;
                return ActorGraphInterpreter$Snapshot$.MODULE$.equals(object);
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
            }
        });
    }

    public void fail(Throwable e, boolean closeTransport) {
        if (this.akka$stream$impl$io$TLSActor$$tracing) {
            this.log().debug("fail {} due to: {}", this.self(), e.getMessage());
        }
        this.inputBunch().cancel();
        if (closeTransport) {
            this.log().debug("closing output");
            this.outputBunch().error(0, e);
        }
        this.outputBunch().error(1, e);
        this.stopped_$eq(true);
        this.pump();
    }

    public boolean fail$default$2() {
        return true;
    }

    @Override
    public void postStop() {
        if (!this.stopped()) {
            RuntimeException e = new RuntimeException(new StringBuilder(55).append("Unexpected termination of TLS actor for connection to ").append(this.engine().getPeerHost()).append(":").append(this.engine().getPeerPort()).toString());
            this.log().warning(e.getMessage());
            this.inputBunch().cancel();
            this.outputBunch().error(0, e);
            this.outputBunch().error(1, e);
        } else if (this.akka$stream$impl$io$TLSActor$$tracing) {
            this.log().debug("postStop");
        }
        Actor.postStop$(this);
    }

    @Override
    public void pumpFailed(Throwable e) {
        this.fail(e, this.fail$default$2());
    }

    @Override
    public void pumpFinished() {
        this.inputBunch().cancel();
        this.outputBunch().complete();
        if (this.akka$stream$impl$io$TLSActor$$tracing) {
            this.log().debug(new StringBuilder(39).append("STOP Outbound Closed: ").append(this.engine().isOutboundDone()).append(" Inbound closed: ").append(this.engine().isInboundDone()).toString());
        }
        this.stopped_$eq(true);
        this.context().stop(this.self());
    }

    private final /* synthetic */ SSLEngine liftedTree1$1() {
        SSLEngine sSLEngine;
        try {
            sSLEngine = this.createSSLEngine.apply(this.context().system());
        }
        catch (Throwable throwable) {
            Option<Throwable> option;
            Throwable throwable2 = throwable;
            if (throwable2 != null && !(option = NonFatal$.MODULE$.unapply(throwable2)).isEmpty()) {
                Throwable ex = option.get();
                this.fail(ex, true);
                throw ex;
            }
            throw throwable;
        }
        return sSLEngine;
    }

    public TLSActor(int maxInputBufferSize, Function1<ActorSystem, SSLEngine> createSSLEngine, Function2<ActorSystem, SSLSession, Try<BoxedUnit>> verifySession, TLSClosing closing2, boolean tracing) {
        this.akka$stream$impl$io$TLSActor$$maxInputBufferSize = maxInputBufferSize;
        this.createSSLEngine = createSSLEngine;
        this.verifySession = verifySession;
        this.closing = closing2;
        this.akka$stream$impl$io$TLSActor$$tracing = tracing;
        Actor.$init$(this);
        ActorLogging.$init$(this);
        Pump.$init$(this);
        this.stopped = false;
        this.outputBunch = new FanOut.OutputBunch(2, this.self(), this);
        this.outputBunch().markAllOutputs();
        this.inputBunch = new FanIn.InputBunch(this){
            private final /* synthetic */ TLSActor $outer;

            public void onError(int input, Throwable e) {
                this.$outer.fail(e, this.$outer.fail$default$2());
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                super(2, $outer.akka$stream$impl$io$TLSActor$$maxInputBufferSize, $outer);
            }
        };
        this.transportOutBuffer = ByteBuffer.allocate(18713);
        this.userOutBuffer = ByteBuffer.allocate(35378);
        this.transportInBuffer = ByteBuffer.allocate(18713);
        this.userInBuffer = ByteBuffer.allocate(18713);
        this.akka$stream$impl$io$TLSActor$$userInChoppingBlock = new ChoppingBlock(this, 1, "UserIn");
        this.akka$stream$impl$io$TLSActor$$userInChoppingBlock().prepare(this.userInBuffer());
        this.transportInChoppingBlock = new ChoppingBlock(this, 0, "TransportIn");
        this.transportInChoppingBlock().prepare(this.transportInBuffer());
        this.lastHandshakeStatus = null;
        this.corkUser = true;
        this.engine = this.liftedTree1$1();
        this.engine().beginHandshake();
        this.lastHandshakeStatus_$eq(this.engine().getHandshakeStatus());
        this.currentSession = this.engine().getSession();
        this.engineNeedsWrap = new TransferState(this){
            private final /* synthetic */ TLSActor $outer;

            public boolean isExecutable() {
                return TransferState.isExecutable$(this);
            }

            public TransferState $bar$bar(TransferState other) {
                return TransferState.$bar$bar$(this, other);
            }

            public TransferState $amp$amp(TransferState other) {
                return TransferState.$amp$amp$(this, other);
            }

            public boolean isReady() {
                SSLEngineResult.HandshakeStatus handshakeStatus = this.$outer.lastHandshakeStatus();
                SSLEngineResult.HandshakeStatus handshakeStatus2 = SSLEngineResult.HandshakeStatus.NEED_WRAP;
                return !(handshakeStatus != null ? !((Object)((Object)handshakeStatus)).equals((Object)((Object)handshakeStatus2)) : handshakeStatus2 != null);
            }

            public boolean isCompleted() {
                return this.$outer.engine().isOutboundDone();
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                TransferState.$init$(this);
            }
        };
        this.engineInboundOpen = new TransferState(this){
            private final /* synthetic */ TLSActor $outer;

            public boolean isExecutable() {
                return TransferState.isExecutable$(this);
            }

            public TransferState $bar$bar(TransferState other) {
                return TransferState.$bar$bar$(this, other);
            }

            public TransferState $amp$amp(TransferState other) {
                return TransferState.$amp$amp$(this, other);
            }

            public boolean isReady() {
                return true;
            }

            public boolean isCompleted() {
                return this.$outer.engine().isInboundDone();
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                TransferState.$init$(this);
            }
        };
        this.userHasData = new TransferState(this){
            private final /* synthetic */ TLSActor $outer;

            public boolean isExecutable() {
                return TransferState.isExecutable$(this);
            }

            public TransferState $bar$bar(TransferState other) {
                return TransferState.$bar$bar$(this, other);
            }

            public TransferState $amp$amp(TransferState other) {
                return TransferState.$amp$amp$(this, other);
            }

            /*
             * Enabled force condition propagation
             * Lifted jumps to return sites
             */
            public boolean isReady() {
                if (this.$outer.corkUser()) return false;
                if (!this.$outer.akka$stream$impl$io$TLSActor$$userInChoppingBlock().isReady()) return false;
                SSLEngineResult.HandshakeStatus handshakeStatus = this.$outer.lastHandshakeStatus();
                SSLEngineResult.HandshakeStatus handshakeStatus2 = SSLEngineResult.HandshakeStatus.NEED_UNWRAP;
                if (handshakeStatus == null) {
                    if (handshakeStatus2 == null) return false;
                    return true;
                } else if (((Object)((Object)handshakeStatus)).equals((Object)((Object)handshakeStatus2))) return false;
                return true;
            }

            public boolean isCompleted() {
                return this.$outer.inputBunch().isCancelled(1) || this.$outer.inputBunch().isDepleted(1);
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                TransferState.$init$(this);
            }
        };
        this.userOutCancelled = new TransferState(this){
            private final /* synthetic */ TLSActor $outer;

            public boolean isExecutable() {
                return TransferState.isExecutable$(this);
            }

            public TransferState $bar$bar(TransferState other) {
                return TransferState.$bar$bar$(this, other);
            }

            public TransferState $amp$amp(TransferState other) {
                return TransferState.$amp$amp$(this, other);
            }

            public boolean isReady() {
                return this.$outer.outputBunch().isCancelled(1);
            }

            public boolean isCompleted() {
                return this.$outer.engine().isInboundDone() || this.$outer.outputBunch().isErrored(1);
            }
            {
                if ($outer == null) {
                    throw null;
                }
                this.$outer = $outer;
                TransferState.$init$(this);
            }
        };
        this.outbound = this.userHasData().$bar$bar(this.engineNeedsWrap()).$amp$amp(this.outputBunch().demandAvailableFor(0));
        this.inbound = this.transportInChoppingBlock().$amp$amp(this.outputBunch().demandAvailableFor(1)).$bar$bar(this.userOutCancelled());
        this.outboundHalfClosed = this.engineNeedsWrap().$amp$amp(this.outputBunch().demandAvailableFor(0));
        this.inboundHalfClosed = this.transportInChoppingBlock().$amp$amp(this.engineInboundOpen());
        this.bidirectional = new TransferPhase(this.outbound().$bar$bar(this.inbound()), () -> {
            boolean bl;
            if ($this.akka$stream$impl$io$TLSActor$$tracing) {
                this.log().debug("bidirectional");
            }
            if (bl = this.doInbound(false, this.inbound())) {
                if ($this.akka$stream$impl$io$TLSActor$$tracing) {
                    this.log().debug("bidirectional continue");
                }
                this.doOutbound(false);
                return;
            }
        });
        this.flushingOutbound = new TransferPhase(this.outboundHalfClosed(), () -> {
            if ($this.akka$stream$impl$io$TLSActor$$tracing) {
                this.log().debug("flushingOutbound");
            }
            try {
                this.doWrap();
            }
            catch (SSLException sSLException) {
                this.nextPhase(this.completedPhase());
            }
        });
        this.awaitingClose = new TransferPhase(this.inputBunch().inputsAvailableFor(0).$amp$amp(this.engineInboundOpen()), () -> {
            if ($this.akka$stream$impl$io$TLSActor$$tracing) {
                this.log().debug("awaitingClose");
            }
            this.transportInChoppingBlock().chopInto(this.transportInBuffer());
            try {
                this.doUnwrap(true);
            }
            catch (SSLException sSLException) {
                this.nextPhase(this.completedPhase());
            }
        });
        this.outboundClosed = new TransferPhase(this.outboundHalfClosed().$bar$bar(this.inbound()), () -> {
            boolean bl;
            if ($this.akka$stream$impl$io$TLSActor$$tracing) {
                this.log().debug("outboundClosed");
            }
            if ((bl = this.doInbound(true, this.inbound())) && this.outboundHalfClosed().isReady()) {
                if ($this.akka$stream$impl$io$TLSActor$$tracing) {
                    this.log().debug("outboundClosed continue");
                }
                try {
                    this.doWrap();
                }
                catch (SSLException sSLException) {
                    this.nextPhase(this.completedPhase());
                }
                return;
            }
        });
        this.inboundClosed = new TransferPhase(this.outbound().$bar$bar(this.inboundHalfClosed()), () -> {
            boolean bl;
            if ($this.akka$stream$impl$io$TLSActor$$tracing) {
                this.log().debug("inboundClosed");
            }
            if (bl = this.doInbound(false, this.inboundHalfClosed())) {
                if ($this.akka$stream$impl$io$TLSActor$$tracing) {
                    this.log().debug("inboundClosed continue");
                }
                this.doOutbound(true);
                return;
            }
        });
        this.initialPhase(2, this.bidirectional());
        Statics.releaseFence();
    }

    public class ChoppingBlock
    implements TransferState {
        private final int idx;
        private final String name;
        private ByteString buffer;
        public final /* synthetic */ TLSActor $outer;

        @Override
        public boolean isExecutable() {
            return TransferState.isExecutable$(this);
        }

        @Override
        public TransferState $bar$bar(TransferState other) {
            return TransferState.$bar$bar$(this, other);
        }

        @Override
        public TransferState $amp$amp(TransferState other) {
            return TransferState.$amp$amp$(this, other);
        }

        @Override
        public boolean isReady() {
            return this.buffer().nonEmpty() || this.akka$stream$impl$io$TLSActor$ChoppingBlock$$$outer().inputBunch().isPending(this.idx) || this.akka$stream$impl$io$TLSActor$ChoppingBlock$$$outer().inputBunch().isDepleted(this.idx);
        }

        @Override
        public boolean isCompleted() {
            return this.akka$stream$impl$io$TLSActor$ChoppingBlock$$$outer().inputBunch().isCancelled(this.idx);
        }

        private ByteString buffer() {
            return this.buffer;
        }

        private void buffer_$eq(ByteString x$1) {
            this.buffer = x$1;
        }

        public boolean isEmpty() {
            return this.buffer().isEmpty();
        }

        public void chopInto(ByteBuffer b) {
            b.compact();
            if (this.buffer().isEmpty()) {
                ByteString byteString;
                Object object = this.akka$stream$impl$io$TLSActor$ChoppingBlock$$$outer().inputBunch().dequeue(this.idx);
                if (object instanceof ByteString) {
                    ByteString byteString2;
                    byteString = byteString2 = (ByteString)object;
                } else if (object instanceof TLSProtocol.SendBytes) {
                    ByteString bs;
                    TLSProtocol.SendBytes sendBytes = (TLSProtocol.SendBytes)object;
                    byteString = bs = sendBytes.bytes();
                } else if (object instanceof TLSProtocol.NegotiateNewSession) {
                    TLSProtocol.NegotiateNewSession negotiateNewSession = (TLSProtocol.NegotiateNewSession)object;
                    this.akka$stream$impl$io$TLSActor$ChoppingBlock$$$outer().setNewSessionParameters(negotiateNewSession);
                    byteString = ByteString$.MODULE$.empty();
                } else {
                    throw new RuntimeException();
                }
                this.buffer_$eq(byteString);
                if (this.akka$stream$impl$io$TLSActor$ChoppingBlock$$$outer().akka$stream$impl$io$TLSActor$$tracing) {
                    this.akka$stream$impl$io$TLSActor$ChoppingBlock$$$outer().log().debug(new StringBuilder(36).append("chopping from new chunk of ").append(this.buffer().size()).append(" into ").append(this.name).append(" (").append(b.position()).append(")").toString());
                }
            } else if (this.akka$stream$impl$io$TLSActor$ChoppingBlock$$$outer().akka$stream$impl$io$TLSActor$$tracing) {
                this.akka$stream$impl$io$TLSActor$ChoppingBlock$$$outer().log().debug(new StringBuilder(36).append("chopping from old chunk of ").append(this.buffer().size()).append(" into ").append(this.name).append(" (").append(b.position()).append(")").toString());
            }
            int copied = this.buffer().copyToBuffer(b);
            this.buffer_$eq(this.buffer().drop(copied));
            b.flip();
        }

        public void putBack(ByteBuffer b) {
            if (b.hasRemaining()) {
                ByteString bs;
                if (this.akka$stream$impl$io$TLSActor$ChoppingBlock$$$outer().akka$stream$impl$io$TLSActor$$tracing) {
                    this.akka$stream$impl$io$TLSActor$ChoppingBlock$$$outer().log().debug(new StringBuilder(25).append("putting back ").append(b.remaining()).append(" bytes into ").append(this.name).toString());
                }
                if ((bs = ByteString$.MODULE$.apply(b)).nonEmpty()) {
                    this.buffer_$eq(bs.$plus$plus(this.buffer()));
                }
                this.prepare(b);
                return;
            }
        }

        public void prepare(ByteBuffer b) {
            b.clear();
            b.limit(0);
        }

        public /* synthetic */ TLSActor akka$stream$impl$io$TLSActor$ChoppingBlock$$$outer() {
            return this.$outer;
        }

        public ChoppingBlock(TLSActor $outer, int idx, String name) {
            this.idx = idx;
            this.name = name;
            if ($outer == null) {
                throw null;
            }
            this.$outer = $outer;
            TransferState.$init$(this);
            this.buffer = ByteString$.MODULE$.empty();
        }
    }
}

