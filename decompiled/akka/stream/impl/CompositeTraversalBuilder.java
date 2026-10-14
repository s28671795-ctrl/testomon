/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl;

import akka.annotation.InternalApi;
import akka.stream.Attributes;
import akka.stream.InPort;
import akka.stream.Inlet;
import akka.stream.OutPort;
import akka.stream.Outlet;
import akka.stream.Shape;
import akka.stream.impl.AppendTraversal;
import akka.stream.impl.BuilderKey;
import akka.stream.impl.CompletedTraversalBuilder;
import akka.stream.impl.CompletedTraversalBuilder$;
import akka.stream.impl.Compose;
import akka.stream.impl.Compose$;
import akka.stream.impl.CompositeTraversalBuilder$;
import akka.stream.impl.EnterIsland;
import akka.stream.impl.ExitIsland$;
import akka.stream.impl.IslandTag;
import akka.stream.impl.Pop$;
import akka.stream.impl.PushNotUsed$;
import akka.stream.impl.Transform;
import akka.stream.impl.Traversal;
import akka.stream.impl.TraversalBuildStep;
import akka.stream.impl.TraversalBuilder;
import akka.stream.scaladsl.Keep$;
import akka.util.OptionVal;
import akka.util.OptionVal$;
import akka.util.OptionVal$Some$;
import java.io.Serializable;
import scala.Function1;
import scala.Function2;
import scala.MatchError;
import scala.Option;
import scala.Predef$;
import scala.Product;
import scala.Tuple10;
import scala.collection.Iterator;
import scala.collection.StringOps$;
import scala.collection.immutable.List;
import scala.collection.immutable.Map;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxesRunTime;
import scala.runtime.Nothing$;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;

@ScalaSignature(bytes="\u0006\u0005\r=c!B(Q\u0005R3\u0006\u0002C9\u0001\u0005+\u0007I\u0011\u0001:\t\u0011Y\u0004!\u0011#Q\u0001\nMD\u0001b\u001e\u0001\u0003\u0016\u0004%\t\u0001\u001f\u0005\t\u007f\u0002\u0011\t\u0012)A\u0005s\"Q\u0011\u0011\u0001\u0001\u0003\u0016\u0004%\t!a\u0001\t\u0015\u0005-\u0001A!E!\u0002\u0013\t)\u0001\u0003\u0006\u0002\u000e\u0001\u0011)\u001a!C\u0001\u0003\u001fA!\"!\u000b\u0001\u0005#\u0005\u000b\u0011BA\t\u0011)\tY\u0003\u0001BK\u0002\u0013\u0005\u0011Q\u0006\u0005\u000b\u0003o\u0001!\u0011#Q\u0001\n\u0005=\u0002BCA\u001d\u0001\tU\r\u0011\"\u0001\u0002<!Q\u0011Q\t\u0001\u0003\u0012\u0003\u0006I!!\u0010\t\u0015\u0005\u001d\u0003A!f\u0001\n\u0003\tI\u0005\u0003\u0006\u0002N\u0001\u0011\t\u0012)A\u0005\u0003\u0017B!\"a\u0014\u0001\u0005+\u0007I\u0011AA\u0002\u0011)\t\t\u0006\u0001B\tB\u0003%\u0011Q\u0001\u0005\u000b\u0003'\u0002!Q3A\u0005\u0002\u0005U\u0003BCA/\u0001\tE\t\u0015!\u0003\u0002X!Q\u0011q\f\u0001\u0003\u0016\u0004%\t!!\u0019\t\u0015\u0005U\u0004A!E!\u0002\u0013\t\u0019\u0007C\u0004\u0002x\u0001!\t!!\u001f\t\u000f\u0005E\u0005\u0001\"\u0011\u0002\u0014\"9\u00111\u0014\u0001\u0005B\u0005u\u0005bBAR\u0001\u0011\u0005\u0013Q\u0015\u0005\b\u0003G\u0003A\u0011IAX\u0011\u001d\t)\f\u0001C!\u0003oCq!a/\u0001\t\u0003\ni\fC\u0004\u0002@\u0002!\t%!1\t\u000f\u0005\u0015\u0007\u0001\"\u0001\u0002H\"9\u0011\u0011\u001a\u0001\u0005B\u0005-\u0007bBAj\u0001\u0011\u0005\u0011Q\u001b\u0005\b\u0005\u0007\u0001A\u0011\u0001B\u0003\u0011\u001d\u0011Y\u0001\u0001C!\u0005\u001bAqA!\u0007\u0001\t\u0003\u0012Y\u0002C\u0005\u0003 \u0001\t\t\u0011\"\u0001\u0003\"!I!q\u0007\u0001\u0012\u0002\u0013\u0005!\u0011\b\u0005\n\u0005\u001f\u0002\u0011\u0013!C\u0001\u0005#B\u0011B!\u0016\u0001#\u0003%\tAa\u0016\t\u0013\tm\u0003!%A\u0005\u0002\tu\u0003\"\u0003B1\u0001E\u0005I\u0011\u0001B2\u0011%\u00119\u0007AI\u0001\n\u0003\u0011I\u0007C\u0005\u0003n\u0001\t\n\u0011\"\u0001\u0003p!I!1\u000f\u0001\u0012\u0002\u0013\u0005!q\u000b\u0005\n\u0005k\u0002\u0011\u0013!C\u0001\u0005oB\u0011Ba\u001f\u0001#\u0003%\tA! \t\u0013\t\u0005\u0005!!A\u0005B\t\r\u0005\"\u0003BJ\u0001\u0005\u0005I\u0011AA\u0002\u0011%\u0011)\nAA\u0001\n\u0003\u00119\nC\u0005\u0003$\u0002\t\t\u0011\"\u0011\u0003&\"I!1\u0017\u0001\u0002\u0002\u0013\u0005!Q\u0017\u0005\n\u0005s\u0003\u0011\u0011!C!\u0005wC\u0011Ba0\u0001\u0003\u0003%\tE!1\t\u0013\t\r\u0007!!A\u0005B\t\u0015wA\u0003Bk!\u0006\u0005\t\u0012\u0001+\u0003X\u001aIq\nUA\u0001\u0012\u0003!&\u0011\u001c\u0005\b\u0003o:D\u0011\u0001By\u0011%\t\tjNA\u0001\n\u000b\u0012\u0019\u0010C\u0005\u0003v^\n\t\u0011\"!\u0003x\"I1QB\u001c\u0012\u0002\u0013\u0005!\u0011\b\u0005\n\u0007\u001f9\u0014\u0013!C\u0001\u0005#B\u0011b!\u00058#\u0003%\tAa\u0016\t\u0013\rMq'%A\u0005\u0002\tu\u0003\"CB\u000boE\u0005I\u0011\u0001B2\u0011%\u00199bNI\u0001\n\u0003\u0011I\u0007C\u0005\u0004\u001a]\n\n\u0011\"\u0001\u0003p!I11D\u001c\u0012\u0002\u0013\u0005!q\u000b\u0005\n\u0007;9\u0014\u0013!C\u0001\u0005{B\u0011ba\b8\u0003\u0003%\ti!\t\t\u0013\rMr'%A\u0005\u0002\te\u0002\"CB\u001boE\u0005I\u0011\u0001B)\u0011%\u00199dNI\u0001\n\u0003\u00119\u0006C\u0005\u0004:]\n\n\u0011\"\u0001\u0003^!I11H\u001c\u0012\u0002\u0013\u0005!1\r\u0005\n\u0007{9\u0014\u0013!C\u0001\u0005SB\u0011ba\u00108#\u0003%\tAa\u001c\t\u0013\r\u0005s'%A\u0005\u0002\t]\u0003\"CB\"oE\u0005I\u0011\u0001B?\u0011%\u0019)eNA\u0001\n\u0013\u00199EA\rD_6\u0004xn]5uKR\u0013\u0018M^3sg\u0006d')^5mI\u0016\u0014(BA)S\u0003\u0011IW\u000e\u001d7\u000b\u0005M#\u0016AB:ue\u0016\fWNC\u0001V\u0003\u0011\t7n[1\u0014\u000b\u00019V,\u00193\u0011\u0005a[V\"A-\u000b\u0003i\u000bQa]2bY\u0006L!\u0001X-\u0003\r\u0005s\u0017PU3g!\tqv,D\u0001Q\u0013\t\u0001\u0007K\u0001\tUe\u00064XM]:bY\n+\u0018\u000e\u001c3feB\u0011\u0001LY\u0005\u0003Gf\u0013q\u0001\u0015:pIV\u001cG\u000f\u0005\u0002f]:\u0011a\r\u001c\b\u0003O.l\u0011\u0001\u001b\u0006\u0003S*\fa\u0001\u0010:p_Rt4\u0001A\u0005\u00025&\u0011Q.W\u0001\ba\u0006\u001c7.Y4f\u0013\ty\u0007O\u0001\u0007TKJL\u0017\r\\5{C\ndWM\u0003\u0002n3\u0006Qa-\u001b8bYN#X\r]:\u0016\u0003M\u0004\"A\u0018;\n\u0005U\u0004&!\u0003+sCZ,'o]1m\u0003-1\u0017N\\1m'R,\u0007o\u001d\u0011\u0002#I,g/\u001a:tK\n+\u0018\u000e\u001c3Ti\u0016\u00048/F\u0001z!\r)'\u0010`\u0005\u0003wB\u0014A\u0001T5tiB\u0011a,`\u0005\u0003}B\u0013!\u0003\u0016:bm\u0016\u00148/\u00197Ck&dGm\u0015;fa\u0006\u0011\"/\u001a<feN,')^5mIN#X\r]:!\u0003\u001dIgn\u00157piN,\"!!\u0002\u0011\u0007a\u000b9!C\u0002\u0002\ne\u00131!\u00138u\u0003!Ign\u00157piN\u0004\u0013!C5o\u001f\u001a47/\u001a;t+\t\t\t\u0002\u0005\u0005\u0002\u0014\u0005m\u0011\u0011EA\u0003\u001d\u0011\t)\"a\u0006\u0011\u0005\u001dL\u0016bAA\r3\u00061\u0001K]3eK\u001aLA!!\b\u0002 \t\u0019Q*\u00199\u000b\u0007\u0005e\u0011\f\u0005\u0003\u0002$\u0005\u0015R\"\u0001*\n\u0007\u0005\u001d\"K\u0001\u0004J]B{'\u000f^\u0001\u000bS:|eMZ:fiN\u0004\u0013AE5o\u0005\u0006\u001cXm\u00144gg\u0016$hi\u001c:PkR,\"!a\f\u0011\u0011\u0005M\u00111DA\u0019\u0003\u000b\u0001B!a\t\u00024%\u0019\u0011Q\u0007*\u0003\u000f=+H\u000fU8si\u0006\u0019\u0012N\u001c\"bg\u0016|eMZ:fi\u001a{'oT;uA\u0005y\u0001/\u001a8eS:<')^5mI\u0016\u00148/\u0006\u0002\u0002>A9\u00111CA\u000e\u0003\u007fi\u0006c\u00010\u0002B%\u0019\u00111\t)\u0003\u0015\t+\u0018\u000e\u001c3fe.+\u00170\u0001\tqK:$\u0017N\\4Ck&dG-\u001a:tA\u0005Iq.\u001e;Po:,'o]\u000b\u0003\u0003\u0017\u0002\u0002\"a\u0005\u0002\u001c\u0005E\u0012qH\u0001\u000b_V$xj\u001e8feN\u0004\u0013aC;oo&\u0014X\rZ(viN\fA\"\u001e8xSJ,GmT;ug\u0002\n!\"\u0019;ue&\u0014W\u000f^3t+\t\t9\u0006\u0005\u0003\u0002$\u0005e\u0013bAA.%\nQ\u0011\t\u001e;sS\n,H/Z:\u0002\u0017\u0005$HO]5ckR,7\u000fI\u0001\nSNd\u0017M\u001c3UC\u001e,\"!a\u0019\u0011\r\u0005\u0015\u00141NA8\u001b\t\t9GC\u0002\u0002jQ\u000bA!\u001e;jY&!\u0011QNA4\u0005%y\u0005\u000f^5p]Z\u000bG\u000eE\u0002_\u0003cJ1!a\u001dQ\u0005%I5\u000f\\1oIR\u000bw-\u0001\u0006jg2\fg\u000e\u001a+bO\u0002\na\u0001P5oSRtDCFA>\u0003{\ny(!!\u0002\u0004\u0006\u0015\u0015qQAE\u0003\u0017\u000bi)a$\u0011\u0005y\u0003\u0001bB9\u0016!\u0003\u0005\ra\u001d\u0005\boV\u0001\n\u00111\u0001z\u0011%\t\t!\u0006I\u0001\u0002\u0004\t)\u0001C\u0005\u0002\u000eU\u0001\n\u00111\u0001\u0002\u0012!I\u00111F\u000b\u0011\u0002\u0003\u0007\u0011q\u0006\u0005\n\u0003s)\u0002\u0013!a\u0001\u0003{A\u0011\"a\u0012\u0016!\u0003\u0005\r!a\u0013\t\u0013\u0005=S\u0003%AA\u0002\u0005\u0015\u0001bBA*+\u0001\u0007\u0011q\u000b\u0005\n\u0003?*\u0002\u0013!a\u0001\u0003G\n\u0001\u0002^8TiJLgn\u001a\u000b\u0003\u0003+\u0003B!a\u0005\u0002\u0018&!\u0011\u0011TA\u0010\u0005\u0019\u0019FO]5oO\u0006qqN\u001a4tKR|e-T8ek2,G\u0003BA\u0003\u0003?Cq!!)\u0018\u0001\u0004\t\t$A\u0002pkR\f\u0011\"[:V]^L'/\u001a3\u0015\t\u0005\u001d\u0016Q\u0016\t\u00041\u0006%\u0016bAAV3\n9!i\\8mK\u0006t\u0007bBAQ1\u0001\u0007\u0011\u0011\u0007\u000b\u0005\u0003O\u000b\t\fC\u0004\u00024f\u0001\r!!\t\u0002\u0005%t\u0017\u0001C8gMN,Go\u00144\u0015\t\u0005\u0015\u0011\u0011\u0018\u0005\b\u0003gS\u0002\u0019AA\u0011\u0003MI7\u000f\u0016:bm\u0016\u00148/\u00197D_6\u0004H.\u001a;f+\t\t9+A\u000bj]R,'O\\1m'\u0016$\u0018\t\u001e;sS\n,H/Z:\u0015\u0007u\u000b\u0019\rC\u0004\u0002Tq\u0001\r!a\u0016\u0002%\r|W\u000e\u001d7fi\u0016Le\rU8tg&\u0014G.Z\u000b\u0002;\u00061\u0011m]:jO:$R!XAg\u0003\u001fDq!!)\u001f\u0001\u0004\t\t\u0004C\u0004\u0002Rz\u0001\r!!\u0002\u0002\u0019I,G.\u0019;jm\u0016\u001cFn\u001c;\u0002\u0007\u0005$G\rF\u0004^\u0003/\fY.!:\t\r\u0005ew\u00041\u0001^\u0003%\u0019XOY7pIVdW\rC\u0004\u0002^~\u0001\r!a8\u0002\u000bMD\u0017\r]3\u0011\t\u0005\r\u0012\u0011]\u0005\u0004\u0003G\u0014&!B*iCB,\u0007bBAt?\u0001\u0007\u0011\u0011^\u0001\u000bG>l'-\u001b8f\u001b\u0006$\b\u0003BAv\u0003{tA!!<\u0002z:!\u0011q^A|\u001d\u0011\t\t0!>\u000f\u0007\u001d\f\u00190C\u0001V\u0013\t\u0019F+\u0003\u0002R%&\u0019\u00111 )\u0002!Q\u0013\u0018M^3sg\u0006d')^5mI\u0016\u0014\u0018\u0002BA\u0000\u0005\u0003\u0011A\"\u00118z\rVt7\r^5p]JR1!a?Q\u0003\u00119\u0018N]3\u0015\u000bu\u00139A!\u0003\t\u000f\u0005\u0005\u0006\u00051\u0001\u00022!9\u00111\u0017\u0011A\u0002\u0005\u0005\u0012\u0001\u0004;sC:\u001chm\u001c:n\u001b\u0006$HcA/\u0003\u0010!9!\u0011C\u0011A\u0002\tM\u0011!\u00014\u0011\t\u0005-(QC\u0005\u0005\u0005/\u0011\tA\u0001\u0007B]f4UO\\2uS>t\u0017'\u0001\u0006nC.,\u0017j\u001d7b]\u0012$2!\u0018B\u000f\u0011\u001d\tyF\ta\u0001\u0003_\nAaY8qsR1\u00121\u0010B\u0012\u0005K\u00119C!\u000b\u0003,\t5\"q\u0006B\u0019\u0005g\u0011)\u0004C\u0004rGA\u0005\t\u0019A:\t\u000f]\u001c\u0003\u0013!a\u0001s\"I\u0011\u0011A\u0012\u0011\u0002\u0003\u0007\u0011Q\u0001\u0005\n\u0003\u001b\u0019\u0003\u0013!a\u0001\u0003#A\u0011\"a\u000b$!\u0003\u0005\r!a\f\t\u0013\u0005e2\u0005%AA\u0002\u0005u\u0002\"CA$GA\u0005\t\u0019AA&\u0011%\tye\tI\u0001\u0002\u0004\t)\u0001C\u0005\u0002T\r\u0002\n\u00111\u0001\u0002X!I\u0011qL\u0012\u0011\u0002\u0003\u0007\u00111M\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00132+\t\u0011YDK\u0002t\u0005{Y#Aa\u0010\u0011\t\t\u0005#1J\u0007\u0003\u0005\u0007RAA!\u0012\u0003H\u0005IQO\\2iK\u000e\\W\r\u001a\u0006\u0004\u0005\u0013J\u0016AC1o]>$\u0018\r^5p]&!!Q\nB\"\u0005E)hn\u00195fG.,GMV1sS\u0006t7-Z\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00133+\t\u0011\u0019FK\u0002z\u0005{\tabY8qs\u0012\"WMZ1vYR$3'\u0006\u0002\u0003Z)\"\u0011Q\u0001B\u001f\u00039\u0019w\u000e]=%I\u00164\u0017-\u001e7uIQ*\"Aa\u0018+\t\u0005E!QH\u0001\u000fG>\u0004\u0018\u0010\n3fM\u0006,H\u000e\u001e\u00136+\t\u0011)G\u000b\u0003\u00020\tu\u0012AD2paf$C-\u001a4bk2$HEN\u000b\u0003\u0005WRC!!\u0010\u0003>\u0005q1m\u001c9zI\u0011,g-Y;mi\u0012:TC\u0001B9U\u0011\tYE!\u0010\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%q\u0005q1m\u001c9zI\u0011,g-Y;mi\u0012JTC\u0001B=U\u0011\t9F!\u0010\u0002\u001f\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%cA*\"Aa +\t\u0005\r$QH\u0001\u000eaJ|G-^2u!J,g-\u001b=\u0016\u0005\t\u0015\u0005\u0003\u0002BD\u0005#k!A!#\u000b\t\t-%QR\u0001\u0005Y\u0006twM\u0003\u0002\u0003\u0010\u0006!!.\u0019<b\u0013\u0011\tIJ!#\u0002\u0019A\u0014x\u000eZ;di\u0006\u0013\u0018\u000e^=\u0002\u001dA\u0014x\u000eZ;di\u0016cW-\\3oiR!!\u0011\u0014BP!\rA&1T\u0005\u0004\u0005;K&aA!os\"I!\u0011\u0015\u0019\u0002\u0002\u0003\u0007\u0011QA\u0001\u0004q\u0012\n\u0014a\u00049s_\u0012,8\r^%uKJ\fGo\u001c:\u0016\u0005\t\u001d\u0006C\u0002BU\u0005_\u0013I*\u0004\u0002\u0003,*\u0019!QV-\u0002\u0015\r|G\u000e\\3di&|g.\u0003\u0003\u00032\n-&\u0001C%uKJ\fGo\u001c:\u0002\u0011\r\fg.R9vC2$B!a*\u00038\"I!\u0011\u0015\u001a\u0002\u0002\u0003\u0007!\u0011T\u0001\u0013aJ|G-^2u\u000b2,W.\u001a8u\u001d\u0006lW\r\u0006\u0003\u0003\u0006\nu\u0006\"\u0003BQg\u0005\u0005\t\u0019AA\u0003\u0003!A\u0017m\u001d5D_\u0012,GCAA\u0003\u0003\u0019)\u0017/^1mgR!\u0011q\u0015Bd\u0011%\u0011\t+NA\u0001\u0002\u0004\u0011I\nK\u0002\u0001\u0005\u0017\u0004BA!4\u0003R6\u0011!q\u001a\u0006\u0004\u0005\u0013\"\u0016\u0002\u0002Bj\u0005\u001f\u00141\"\u00138uKJt\u0017\r\\!qS\u0006I2i\\7q_NLG/\u001a+sCZ,'o]1m\u0005VLG\u000eZ3s!\tqvgE\u00038\u00057\u00149\u000f\u0005\r\u0003^\n\r8/_A\u0003\u0003#\ty#!\u0010\u0002L\u0005\u0015\u0011qKA2\u0003wj!Aa8\u000b\u0007\t\u0005\u0018,A\u0004sk:$\u0018.\\3\n\t\t\u0015(q\u001c\u0002\u0013\u0003\n\u001cHO]1di\u001a+hn\u0019;j_:\f\u0004\u0007\u0005\u0003\u0003j\n=XB\u0001Bv\u0015\u0011\u0011iO!$\u0002\u0005%|\u0017bA8\u0003lR\u0011!q\u001b\u000b\u0003\u0005\u000b\u000bQ!\u00199qYf$b#a\u001f\u0003z\nm(Q B\u0000\u0007\u0003\u0019\u0019a!\u0002\u0004\b\r%11\u0002\u0005\bcj\u0002\n\u00111\u0001t\u0011\u001d9(\b%AA\u0002eD\u0011\"!\u0001;!\u0003\u0005\r!!\u0002\t\u0013\u00055!\b%AA\u0002\u0005E\u0001\"CA\u0016uA\u0005\t\u0019AA\u0018\u0011%\tID\u000fI\u0001\u0002\u0004\ti\u0004C\u0005\u0002Hi\u0002\n\u00111\u0001\u0002L!I\u0011q\n\u001e\u0011\u0002\u0003\u0007\u0011Q\u0001\u0005\b\u0003'R\u0004\u0019AA,\u0011%\tyF\u000fI\u0001\u0002\u0004\t\u0019'A\bbaBd\u0017\u0010\n3fM\u0006,H\u000e\u001e\u00132\u0003=\t\u0007\u000f\u001d7zI\u0011,g-Y;mi\u0012\u0012\u0014aD1qa2LH\u0005Z3gCVdG\u000fJ\u001a\u0002\u001f\u0005\u0004\b\u000f\\=%I\u00164\u0017-\u001e7uIQ\nq\"\u00199qYf$C-\u001a4bk2$H%N\u0001\u0010CB\u0004H.\u001f\u0013eK\u001a\fW\u000f\u001c;%m\u0005y\u0011\r\u001d9ms\u0012\"WMZ1vYR$s'A\bbaBd\u0017\u0010\n3fM\u0006,H\u000e\u001e\u00139\u0003A\t\u0007\u000f\u001d7zI\u0011,g-Y;mi\u0012\n\u0004'A\u0004v]\u0006\u0004\b\u000f\\=\u0015\t\r\r2q\u0006\t\u00061\u000e\u00152\u0011F\u0005\u0004\u0007OI&AB(qi&|g\u000eE\u000bY\u0007W\u0019\u00180!\u0002\u0002\u0012\u0005=\u0012QHA&\u0003\u000b\t9&a\u0019\n\u0007\r5\u0012LA\u0004UkBdW-\r\u0019\t\u0013\rEB)!AA\u0002\u0005m\u0014a\u0001=%a\u0005YB\u0005\\3tg&t\u0017\u000e\u001e\u0013he\u0016\fG/\u001a:%I\u00164\u0017-\u001e7uIE\n1\u0004\n7fgNLg.\u001b;%OJ,\u0017\r^3sI\u0011,g-Y;mi\u0012\u0012\u0014a\u0007\u0013mKN\u001c\u0018N\\5uI\u001d\u0014X-\u0019;fe\u0012\"WMZ1vYR$3'A\u000e%Y\u0016\u001c8/\u001b8ji\u0012:'/Z1uKJ$C-\u001a4bk2$H\u0005N\u0001\u001cI1,7o]5oSR$sM]3bi\u0016\u0014H\u0005Z3gCVdG\u000fJ\u001b\u00027\u0011bWm]:j]&$He\u001a:fCR,'\u000f\n3fM\u0006,H\u000e\u001e\u00137\u0003m!C.Z:tS:LG\u000fJ4sK\u0006$XM\u001d\u0013eK\u001a\fW\u000f\u001c;%o\u0005YB\u0005\\3tg&t\u0017\u000e\u001e\u0013he\u0016\fG/\u001a:%I\u00164\u0017-\u001e7uIa\nA\u0004\n7fgNLg.\u001b;%OJ,\u0017\r^3sI\u0011,g-Y;mi\u0012\n\u0004'\u0001\u0007xe&$XMU3qY\u0006\u001cW\r\u0006\u0002\u0004JA!!qQB&\u0013\u0011\u0019iE!#\u0003\r=\u0013'.Z2u\u0001")
@InternalApi
public final class CompositeTraversalBuilder
implements TraversalBuilder,
Product,
Serializable {
    private final Traversal finalSteps;
    private final List<TraversalBuildStep> reverseBuildSteps;
    private final int inSlots;
    private final Map<InPort, Object> inOffsets;
    private final Map<OutPort, Object> inBaseOffsetForOut;
    private final Map<BuilderKey, TraversalBuilder> pendingBuilders;
    private final Map<OutPort, BuilderKey> outOwners;
    private final int unwiredOuts;
    private final Attributes attributes;
    private final IslandTag islandTag;

    public static IslandTag $lessinit$greater$default$10() {
        return CompositeTraversalBuilder$.MODULE$.$lessinit$greater$default$10();
    }

    public static int $lessinit$greater$default$8() {
        return CompositeTraversalBuilder$.MODULE$.$lessinit$greater$default$8();
    }

    public static Map<OutPort, BuilderKey> $lessinit$greater$default$7() {
        return CompositeTraversalBuilder$.MODULE$.$lessinit$greater$default$7();
    }

    public static Map<BuilderKey, TraversalBuilder> $lessinit$greater$default$6() {
        return CompositeTraversalBuilder$.MODULE$.$lessinit$greater$default$6();
    }

    public static Map<OutPort, Object> $lessinit$greater$default$5() {
        return CompositeTraversalBuilder$.MODULE$.$lessinit$greater$default$5();
    }

    public static Map<InPort, Object> $lessinit$greater$default$4() {
        return CompositeTraversalBuilder$.MODULE$.$lessinit$greater$default$4();
    }

    public static int $lessinit$greater$default$3() {
        return CompositeTraversalBuilder$.MODULE$.$lessinit$greater$default$3();
    }

    public static List<TraversalBuildStep> $lessinit$greater$default$2() {
        return CompositeTraversalBuilder$.MODULE$.$lessinit$greater$default$2();
    }

    public static Traversal $lessinit$greater$default$1() {
        return CompositeTraversalBuilder$.MODULE$.$lessinit$greater$default$1();
    }

    public static Option<Tuple10<Traversal, List<TraversalBuildStep>, Object, Map<InPort, Object>, Map<OutPort, Object>, Map<BuilderKey, TraversalBuilder>, Map<OutPort, BuilderKey>, Object, Attributes, OptionVal<IslandTag>>> unapply(CompositeTraversalBuilder x$0) {
        return CompositeTraversalBuilder$.MODULE$.unapply(x$0);
    }

    public static IslandTag apply$default$10() {
        return CompositeTraversalBuilder$.MODULE$.apply$default$10();
    }

    public static int apply$default$8() {
        return CompositeTraversalBuilder$.MODULE$.apply$default$8();
    }

    public static Map<OutPort, BuilderKey> apply$default$7() {
        return CompositeTraversalBuilder$.MODULE$.apply$default$7();
    }

    public static Map<BuilderKey, TraversalBuilder> apply$default$6() {
        return CompositeTraversalBuilder$.MODULE$.apply$default$6();
    }

    public static Map<OutPort, Object> apply$default$5() {
        return CompositeTraversalBuilder$.MODULE$.apply$default$5();
    }

    public static Map<InPort, Object> apply$default$4() {
        return CompositeTraversalBuilder$.MODULE$.apply$default$4();
    }

    public static int apply$default$3() {
        return CompositeTraversalBuilder$.MODULE$.apply$default$3();
    }

    public static List<TraversalBuildStep> apply$default$2() {
        return CompositeTraversalBuilder$.MODULE$.apply$default$2();
    }

    public static Traversal apply$default$1() {
        return CompositeTraversalBuilder$.MODULE$.apply$default$1();
    }

    public static CompositeTraversalBuilder apply(Traversal finalSteps, List<TraversalBuildStep> reverseBuildSteps, int inSlots, Map<InPort, Object> inOffsets, Map<OutPort, Object> inBaseOffsetForOut, Map<BuilderKey, TraversalBuilder> pendingBuilders, Map<OutPort, BuilderKey> outOwners, int unwiredOuts, Attributes attributes, IslandTag islandTag) {
        return CompositeTraversalBuilder$.MODULE$.apply(finalSteps, reverseBuildSteps, inSlots, inOffsets, inBaseOffsetForOut, pendingBuilders, outOwners, unwiredOuts, attributes, islandTag);
    }

    public static Function1<Tuple10<Traversal, List<TraversalBuildStep>, Object, Map<InPort, Object>, Map<OutPort, Object>, Map<BuilderKey, TraversalBuilder>, Map<OutPort, BuilderKey>, Object, Attributes, OptionVal<IslandTag>>, CompositeTraversalBuilder> tupled() {
        return CompositeTraversalBuilder$.MODULE$.tupled();
    }

    public static Function1<Traversal, Function1<List<TraversalBuildStep>, Function1<Object, Function1<Map<InPort, Object>, Function1<Map<OutPort, Object>, Function1<Map<BuilderKey, TraversalBuilder>, Function1<Map<OutPort, BuilderKey>, Function1<Object, Function1<Attributes, Function1<OptionVal<IslandTag>, CompositeTraversalBuilder>>>>>>>>>> curried() {
        return CompositeTraversalBuilder$.MODULE$.curried();
    }

    @Override
    public Iterator<String> productElementNames() {
        return Product.productElementNames$(this);
    }

    @Override
    public TraversalBuilder setAttributes(Attributes attributes) {
        return TraversalBuilder.setAttributes$(this, attributes);
    }

    @Override
    public Traversal traversal() {
        return TraversalBuilder.traversal$(this);
    }

    public Traversal finalSteps() {
        return this.finalSteps;
    }

    public List<TraversalBuildStep> reverseBuildSteps() {
        return this.reverseBuildSteps;
    }

    @Override
    public int inSlots() {
        return this.inSlots;
    }

    public Map<InPort, Object> inOffsets() {
        return this.inOffsets;
    }

    public Map<OutPort, Object> inBaseOffsetForOut() {
        return this.inBaseOffsetForOut;
    }

    public Map<BuilderKey, TraversalBuilder> pendingBuilders() {
        return this.pendingBuilders;
    }

    public Map<OutPort, BuilderKey> outOwners() {
        return this.outOwners;
    }

    @Override
    public int unwiredOuts() {
        return this.unwiredOuts;
    }

    @Override
    public Attributes attributes() {
        return this.attributes;
    }

    public IslandTag islandTag() {
        return this.islandTag;
    }

    public String toString() {
        return StringOps$.MODULE$.stripMargin$extension(Predef$.MODULE$.augmentString(new StringBuilder(247).append("\n         |CompositeTraversal(\n         |  reverseTraversal = ").append(this.reverseBuildSteps()).append("\n         |  pendingBuilders = ").append(this.pendingBuilders()).append("\n         |  inSlots = ").append(this.inSlots()).append("\n         |  inOffsets = ").append(this.inOffsets()).append("\n         |  inBaseOffsetForOut = ").append(this.inBaseOffsetForOut()).append("\n         |  outOwners = ").append(this.outOwners()).append("\n         |  unwiredOuts = ").append(this.unwiredOuts()).append("\n         |)\n       ").toString()));
    }

    @Override
    public int offsetOfModule(OutPort out) {
        return BoxesRunTime.unboxToInt(this.inBaseOffsetForOut().apply(out));
    }

    @Override
    public boolean isUnwired(OutPort out) {
        return this.inBaseOffsetForOut().contains(out);
    }

    @Override
    public boolean isUnwired(InPort in) {
        return this.inOffsets().contains(in);
    }

    @Override
    public int offsetOf(InPort in) {
        return BoxesRunTime.unboxToInt(this.inOffsets().apply(in));
    }

    @Override
    public boolean isTraversalComplete() {
        return false;
    }

    @Override
    public TraversalBuilder internalSetAttributes(Attributes attributes) {
        Attributes x$1 = attributes;
        Traversal x$2 = this.copy$default$1();
        List<TraversalBuildStep> x$3 = this.copy$default$2();
        int x$4 = this.copy$default$3();
        Map<InPort, Object> x$5 = this.copy$default$4();
        Map<OutPort, Object> x$6 = this.copy$default$5();
        Map<BuilderKey, TraversalBuilder> x$7 = this.copy$default$6();
        Map<OutPort, BuilderKey> x$8 = this.copy$default$7();
        int x$9 = this.copy$default$8();
        IslandTag x$10 = this.copy$default$10();
        return this.copy(x$2, x$3, x$4, x$5, x$6, x$7, x$8, x$9, x$1, x$10);
    }

    public TraversalBuilder completeIfPossible() {
        if (this.unwiredOuts() == 0) {
            Traversal traversal;
            Traversal traversal2 = this.finalSteps();
            List remaining = this.reverseBuildSteps();
            while (remaining.nonEmpty()) {
                TraversalBuildStep traversalBuildStep = (TraversalBuildStep)remaining.head();
                if (traversalBuildStep instanceof BuilderKey) {
                    BuilderKey builderKey = (BuilderKey)traversalBuildStep;
                    traversal2 = ((TraversalBuilder)this.pendingBuilders().apply(builderKey)).traversal().concat(traversal2);
                } else if (traversalBuildStep instanceof AppendTraversal) {
                    AppendTraversal appendTraversal = (AppendTraversal)traversalBuildStep;
                    Traversal toAppend = appendTraversal.traversal();
                    traversal2 = toAppend.concat(traversal2);
                } else {
                    throw new MatchError(traversalBuildStep);
                }
                remaining = (List)remaining.tail();
            }
            IslandTag islandTag = this.islandTag();
            IslandTag islandTag2 = OptionVal$Some$.MODULE$.unapply(islandTag);
            if (!OptionVal$.MODULE$.isEmpty$extension(islandTag2)) {
                IslandTag tag = OptionVal$.MODULE$.get$extension(islandTag2);
                traversal = new EnterIsland(tag).concat(traversal2).concat(ExitIsland$.MODULE$);
            } else {
                traversal = traversal2;
            }
            Traversal finalTraversal = traversal;
            return new CompletedTraversalBuilder(finalTraversal, this.inSlots(), this.inOffsets(), this.attributes(), CompletedTraversalBuilder$.MODULE$.apply$default$5());
        }
        return this;
    }

    @Override
    public TraversalBuilder assign(OutPort out, int relativeSlot) {
        CompositeTraversalBuilder compositeTraversalBuilder;
        BuilderKey builderKey = (BuilderKey)this.outOwners().apply(out);
        TraversalBuilder submodule = (TraversalBuilder)this.pendingBuilders().apply(builderKey);
        TraversalBuilder result = submodule.assign(out.mappedTo(), relativeSlot);
        if (result.isTraversalComplete()) {
            Map x$1 = (Map)this.inBaseOffsetForOut().$minus(out);
            Map x$2 = (Map)this.outOwners().$minus(out);
            Map x$3 = (Map)this.pendingBuilders().updated(builderKey, result);
            int x$4 = this.unwiredOuts() - 1;
            Traversal x$5 = this.copy$default$1();
            List<TraversalBuildStep> x$6 = this.copy$default$2();
            int x$7 = this.copy$default$3();
            Map<InPort, Object> x$8 = this.copy$default$4();
            Attributes x$9 = this.copy$default$9();
            IslandTag x$10 = this.copy$default$10();
            compositeTraversalBuilder = this.copy(x$5, x$6, x$7, x$8, x$1, x$3, x$2, x$4, x$9, x$10);
        } else {
            Map x$11 = (Map)this.inBaseOffsetForOut().$minus(out);
            int x$12 = this.unwiredOuts() - 1;
            Map x$13 = (Map)this.pendingBuilders().updated(builderKey, result);
            Traversal x$14 = this.copy$default$1();
            List<TraversalBuildStep> x$15 = this.copy$default$2();
            int x$16 = this.copy$default$3();
            Map<InPort, Object> x$17 = this.copy$default$4();
            Map<OutPort, BuilderKey> x$18 = this.copy$default$7();
            Attributes x$19 = this.copy$default$9();
            IslandTag x$20 = this.copy$default$10();
            compositeTraversalBuilder = this.copy(x$14, x$15, x$16, x$17, x$11, x$13, x$18, x$12, x$19, x$20);
        }
        CompositeTraversalBuilder wired = compositeTraversalBuilder;
        return wired.completeIfPossible();
    }

    @Override
    public TraversalBuilder add(TraversalBuilder submodule, Shape shape, Function2<Nothing$, Nothing$, Object> combineMat) {
        CompositeTraversalBuilder compositeTraversalBuilder;
        List<TraversalBuildStep> newBuildSteps;
        List<TraversalBuildStep> list;
        BuilderKey builderKey = new BuilderKey();
        Function2<Nothing$, Nothing$, Object> function2 = combineMat;
        Function2 function22 = Keep$.MODULE$.left();
        if (!(function2 != null ? !function2.equals(function22) : function22 != null)) {
            AppendTraversal appendTraversal = new AppendTraversal(Pop$.MODULE$);
            BuilderKey builderKey2 = builderKey;
            list = this.reverseBuildSteps().$colon$colon(builderKey2).$colon$colon(appendTraversal);
        } else {
            Function2<Nothing$, Nothing$, Object> function23 = combineMat;
            Function2 function24 = Keep$.MODULE$.right();
            if (!(function23 != null ? !function23.equals(function24) : function24 != null)) {
                BuilderKey builderKey3 = builderKey;
                AppendTraversal appendTraversal = new AppendTraversal(Pop$.MODULE$);
                list = this.reverseBuildSteps().$colon$colon(appendTraversal).$colon$colon(builderKey3);
            } else {
                Function2<Nothing$, Nothing$, Object> function25 = combineMat;
                Function2 function26 = Keep$.MODULE$.none();
                if (!(function25 != null ? !function25.equals(function26) : function26 != null)) {
                    AppendTraversal appendTraversal = new AppendTraversal(PushNotUsed$.MODULE$);
                    AppendTraversal appendTraversal2 = new AppendTraversal(Pop$.MODULE$);
                    AppendTraversal appendTraversal3 = new AppendTraversal(Pop$.MODULE$);
                    BuilderKey builderKey4 = builderKey;
                    list = this.reverseBuildSteps().$colon$colon(builderKey4).$colon$colon(appendTraversal3).$colon$colon(appendTraversal2).$colon$colon(appendTraversal);
                } else {
                    AppendTraversal appendTraversal = new AppendTraversal(new Compose(combineMat, Compose$.MODULE$.apply$default$2()));
                    BuilderKey builderKey5 = builderKey;
                    list = newBuildSteps = this.reverseBuildSteps().$colon$colon(builderKey5).$colon$colon(appendTraversal);
                }
            }
        }
        if (submodule.isTraversalComplete()) {
            Map newInOffsets = this.inOffsets();
            Iterator inIterator = shape.inlets().iterator();
            while (inIterator.hasNext()) {
                Inlet in = (Inlet)inIterator.next();
                newInOffsets = (Map)newInOffsets.updated(in, BoxesRunTime.boxToInteger(this.inSlots() + submodule.offsetOf(in.mappedTo())));
            }
            List<TraversalBuildStep> x$1 = newBuildSteps;
            int x$2 = this.inSlots() + submodule.inSlots();
            Map x$3 = (Map)this.pendingBuilders().updated(builderKey, submodule);
            Map x$4 = newInOffsets;
            Traversal x$5 = this.copy$default$1();
            Map<OutPort, Object> x$6 = this.copy$default$5();
            Map<OutPort, BuilderKey> x$7 = this.copy$default$7();
            int x$8 = this.copy$default$8();
            Attributes x$9 = this.copy$default$9();
            IslandTag x$10 = this.copy$default$10();
            compositeTraversalBuilder = this.copy(x$5, x$1, x$2, x$4, x$6, x$3, x$7, x$8, x$9, x$10);
        } else {
            Map newInOffsets = this.inOffsets();
            Map newBaseOffsetsForOut = this.inBaseOffsetForOut();
            Map newOutOwners = this.outOwners();
            Iterator inIterator = shape.inlets().iterator();
            while (inIterator.hasNext()) {
                Inlet in = (Inlet)inIterator.next();
                newInOffsets = (Map)newInOffsets.updated(in, BoxesRunTime.boxToInteger(this.inSlots() + submodule.offsetOf(in.mappedTo())));
            }
            Iterator outIterator = shape.outlets().iterator();
            while (outIterator.hasNext()) {
                Outlet out = (Outlet)outIterator.next();
                newBaseOffsetsForOut = (Map)newBaseOffsetsForOut.updated(out, BoxesRunTime.boxToInteger(this.inSlots() + submodule.offsetOfModule(out.mappedTo())));
                newOutOwners = (Map)newOutOwners.updated(out, builderKey);
            }
            List<TraversalBuildStep> x$11 = newBuildSteps;
            int x$12 = this.inSlots() + submodule.inSlots();
            Map x$13 = newInOffsets;
            Map x$14 = newBaseOffsetsForOut;
            Map x$15 = newOutOwners;
            Map x$16 = (Map)this.pendingBuilders().updated(builderKey, submodule);
            int x$17 = this.unwiredOuts() + submodule.unwiredOuts();
            Traversal x$18 = this.copy$default$1();
            Attributes x$19 = this.copy$default$9();
            IslandTag x$20 = this.copy$default$10();
            compositeTraversalBuilder = this.copy(x$18, x$11, x$12, x$13, x$14, x$16, x$15, x$17, x$19, x$20);
        }
        CompositeTraversalBuilder added = compositeTraversalBuilder;
        return added.completeIfPossible();
    }

    @Override
    public TraversalBuilder wire(OutPort out, InPort in) {
        Map x$1 = (Map)this.inOffsets().$minus(in);
        Traversal x$2 = this.copy$default$1();
        List<TraversalBuildStep> x$3 = this.copy$default$2();
        int x$4 = this.copy$default$3();
        Map<OutPort, Object> x$5 = this.copy$default$5();
        Map<BuilderKey, TraversalBuilder> x$6 = this.copy$default$6();
        Map<OutPort, BuilderKey> x$7 = this.copy$default$7();
        int x$8 = this.copy$default$8();
        Attributes x$9 = this.copy$default$9();
        IslandTag x$10 = this.copy$default$10();
        return this.copy(x$2, x$3, x$4, x$1, x$5, x$6, x$7, x$8, x$9, x$10).assign(out, this.offsetOf(in) - this.offsetOfModule(out));
    }

    @Override
    public TraversalBuilder transformMat(Function1<Nothing$, Object> f) {
        return this.copy(this.finalSteps().concat(new Transform(f)), this.copy$default$2(), this.copy$default$3(), this.copy$default$4(), this.copy$default$5(), this.copy$default$6(), this.copy$default$7(), this.copy$default$8(), this.copy$default$9(), this.copy$default$10());
    }

    @Override
    public TraversalBuilder makeIsland(IslandTag islandTag) {
        IslandTag islandTag2 = this.islandTag();
        OptionVal$.MODULE$.None();
        IslandTag islandTag3 = islandTag2;
        if (!(null != null ? !((Object)null).equals(islandTag3) : islandTag3 != null)) {
            IslandTag x$1 = OptionVal$.MODULE$.apply(islandTag);
            Traversal x$2 = this.copy$default$1();
            List<TraversalBuildStep> x$3 = this.copy$default$2();
            int x$4 = this.copy$default$3();
            Map<InPort, Object> x$5 = this.copy$default$4();
            Map<OutPort, Object> x$6 = this.copy$default$5();
            Map<BuilderKey, TraversalBuilder> x$7 = this.copy$default$6();
            Map<OutPort, BuilderKey> x$8 = this.copy$default$7();
            int x$9 = this.copy$default$8();
            Attributes x$10 = this.copy$default$9();
            return this.copy(x$2, x$3, x$4, x$5, x$6, x$7, x$8, x$9, x$10, x$1);
        }
        return this;
    }

    public CompositeTraversalBuilder copy(Traversal finalSteps, List<TraversalBuildStep> reverseBuildSteps, int inSlots, Map<InPort, Object> inOffsets, Map<OutPort, Object> inBaseOffsetForOut, Map<BuilderKey, TraversalBuilder> pendingBuilders, Map<OutPort, BuilderKey> outOwners, int unwiredOuts, Attributes attributes, IslandTag islandTag) {
        return new CompositeTraversalBuilder(finalSteps, reverseBuildSteps, inSlots, inOffsets, inBaseOffsetForOut, pendingBuilders, outOwners, unwiredOuts, attributes, islandTag);
    }

    public Traversal copy$default$1() {
        return this.finalSteps();
    }

    public IslandTag copy$default$10() {
        return this.islandTag();
    }

    public List<TraversalBuildStep> copy$default$2() {
        return this.reverseBuildSteps();
    }

    public int copy$default$3() {
        return this.inSlots();
    }

    public Map<InPort, Object> copy$default$4() {
        return this.inOffsets();
    }

    public Map<OutPort, Object> copy$default$5() {
        return this.inBaseOffsetForOut();
    }

    public Map<BuilderKey, TraversalBuilder> copy$default$6() {
        return this.pendingBuilders();
    }

    public Map<OutPort, BuilderKey> copy$default$7() {
        return this.outOwners();
    }

    public int copy$default$8() {
        return this.unwiredOuts();
    }

    public Attributes copy$default$9() {
        return this.attributes();
    }

    @Override
    public String productPrefix() {
        return "CompositeTraversalBuilder";
    }

    @Override
    public int productArity() {
        return 10;
    }

    @Override
    public Object productElement(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return this.finalSteps();
            }
            case 1: {
                return this.reverseBuildSteps();
            }
            case 2: {
                return BoxesRunTime.boxToInteger(this.inSlots());
            }
            case 3: {
                return this.inOffsets();
            }
            case 4: {
                return this.inBaseOffsetForOut();
            }
            case 5: {
                return this.pendingBuilders();
            }
            case 6: {
                return this.outOwners();
            }
            case 7: {
                return BoxesRunTime.boxToInteger(this.unwiredOuts());
            }
            case 8: {
                return this.attributes();
            }
            case 9: {
                return new OptionVal<IslandTag>(this.islandTag());
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
        return x$1 instanceof CompositeTraversalBuilder;
    }

    @Override
    public String productElementName(int x$1) {
        int n = x$1;
        switch (n) {
            case 0: {
                return "finalSteps";
            }
            case 1: {
                return "reverseBuildSteps";
            }
            case 2: {
                return "inSlots";
            }
            case 3: {
                return "inOffsets";
            }
            case 4: {
                return "inBaseOffsetForOut";
            }
            case 5: {
                return "pendingBuilders";
            }
            case 6: {
                return "outOwners";
            }
            case 7: {
                return "unwiredOuts";
            }
            case 8: {
                return "attributes";
            }
            case 9: {
                return "islandTag";
            }
        }
        return (String)Statics.ioobe(x$1);
    }

    public int hashCode() {
        int n = -889275714;
        n = Statics.mix(n, this.productPrefix().hashCode());
        n = Statics.mix(n, Statics.anyHash(this.finalSteps()));
        n = Statics.mix(n, Statics.anyHash(this.reverseBuildSteps()));
        n = Statics.mix(n, this.inSlots());
        n = Statics.mix(n, Statics.anyHash(this.inOffsets()));
        n = Statics.mix(n, Statics.anyHash(this.inBaseOffsetForOut()));
        n = Statics.mix(n, Statics.anyHash(this.pendingBuilders()));
        n = Statics.mix(n, Statics.anyHash(this.outOwners()));
        n = Statics.mix(n, this.unwiredOuts());
        n = Statics.mix(n, Statics.anyHash(this.attributes()));
        n = Statics.mix(n, Statics.anyHash(new OptionVal<IslandTag>(this.islandTag())));
        return Statics.finalizeHash(n, 10);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public boolean equals(Object x$1) {
        if (this == x$1) return true;
        Object object = x$1;
        if (!(object instanceof CompositeTraversalBuilder)) return false;
        boolean bl = true;
        if (!bl) return false;
        CompositeTraversalBuilder compositeTraversalBuilder = (CompositeTraversalBuilder)x$1;
        if (this.inSlots() != compositeTraversalBuilder.inSlots()) return false;
        if (this.unwiredOuts() != compositeTraversalBuilder.unwiredOuts()) return false;
        Traversal traversal = this.finalSteps();
        Traversal traversal2 = compositeTraversalBuilder.finalSteps();
        if (traversal == null) {
            if (traversal2 != null) {
                return false;
            }
        } else if (!traversal.equals(traversal2)) return false;
        List<TraversalBuildStep> list = this.reverseBuildSteps();
        List<TraversalBuildStep> list2 = compositeTraversalBuilder.reverseBuildSteps();
        if (list == null) {
            if (list2 != null) {
                return false;
            }
        } else if (!((Object)list).equals(list2)) return false;
        Map<InPort, Object> map = this.inOffsets();
        Map<InPort, Object> map2 = compositeTraversalBuilder.inOffsets();
        if (map == null) {
            if (map2 != null) {
                return false;
            }
        } else if (!map.equals(map2)) return false;
        Map<OutPort, Object> map3 = this.inBaseOffsetForOut();
        Map<OutPort, Object> map4 = compositeTraversalBuilder.inBaseOffsetForOut();
        if (map3 == null) {
            if (map4 != null) {
                return false;
            }
        } else if (!map3.equals(map4)) return false;
        Map<BuilderKey, TraversalBuilder> map5 = this.pendingBuilders();
        Map<BuilderKey, TraversalBuilder> map6 = compositeTraversalBuilder.pendingBuilders();
        if (map5 == null) {
            if (map6 != null) {
                return false;
            }
        } else if (!map5.equals(map6)) return false;
        Map<OutPort, BuilderKey> map7 = this.outOwners();
        Map<OutPort, BuilderKey> map8 = compositeTraversalBuilder.outOwners();
        if (map7 == null) {
            if (map8 != null) {
                return false;
            }
        } else if (!map7.equals(map8)) return false;
        Attributes attributes = this.attributes();
        Attributes attributes2 = compositeTraversalBuilder.attributes();
        if (attributes == null) {
            if (attributes2 != null) {
                return false;
            }
        } else if (!((Object)attributes).equals(attributes2)) return false;
        IslandTag islandTag = this.islandTag();
        IslandTag islandTag2 = compositeTraversalBuilder.islandTag();
        if (islandTag == null) {
            if (islandTag2 == null) return true;
            return false;
        } else {
            if (!islandTag.equals(islandTag2)) return false;
            return true;
        }
    }

    public CompositeTraversalBuilder(Traversal finalSteps, List<TraversalBuildStep> reverseBuildSteps, int inSlots, Map<InPort, Object> inOffsets, Map<OutPort, Object> inBaseOffsetForOut, Map<BuilderKey, TraversalBuilder> pendingBuilders, Map<OutPort, BuilderKey> outOwners, int unwiredOuts, Attributes attributes, IslandTag islandTag) {
        this.finalSteps = finalSteps;
        this.reverseBuildSteps = reverseBuildSteps;
        this.inSlots = inSlots;
        this.inOffsets = inOffsets;
        this.inBaseOffsetForOut = inBaseOffsetForOut;
        this.pendingBuilders = pendingBuilders;
        this.outOwners = outOwners;
        this.unwiredOuts = unwiredOuts;
        this.attributes = attributes;
        this.islandTag = islandTag;
        TraversalBuilder.$init$(this);
        Product.$init$(this);
    }
}

