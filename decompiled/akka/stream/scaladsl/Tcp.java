/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.scaladsl;

import akka.Done;
import akka.NotUsed;
import akka.actor.ActorSystem;
import akka.actor.ClassicActorSystemProvider;
import akka.actor.ExtendedActorSystem;
import akka.actor.Extension;
import akka.annotation.InternalApi;
import akka.io.IO$;
import akka.io.Inet;
import akka.stream.Graph;
import akka.stream.IgnoreComplete$;
import akka.stream.Materializer;
import akka.stream.TLSClosing;
import akka.stream.TLSProtocol;
import akka.stream.TLSRole$;
import akka.stream.impl.fusing.GraphStages$;
import akka.stream.impl.io.ConnectionSourceStage;
import akka.stream.impl.io.OutgoingConnectionStage;
import akka.stream.impl.io.TcpIdleTimeout$;
import akka.stream.scaladsl.BidiFlow;
import akka.stream.scaladsl.Flow;
import akka.stream.scaladsl.Flow$;
import akka.stream.scaladsl.FlowOps;
import akka.stream.scaladsl.Keep$;
import akka.stream.scaladsl.RunnableGraph;
import akka.stream.scaladsl.Sink$;
import akka.stream.scaladsl.Source;
import akka.stream.scaladsl.Source$;
import akka.stream.scaladsl.TLS$;
import akka.stream.scaladsl.Tcp$;
import akka.util.ByteString;
import akka.util.JavaDurationConverters$;
import akka.util.JavaDurationConverters$JavaDurationOps$;
import java.io.Serializable;
import java.net.InetSocketAddress;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLSession;
import scala.Function0;
import scala.Function1;
import scala.None$;
import scala.Option;
import scala.Product;
import scala.Some;
import scala.collection.Iterator;
import scala.collection.immutable.Iterable;
import scala.collection.immutable.Nil$;
import scala.collection.immutable.Seq;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.Duration$;
import scala.concurrent.duration.FiniteDuration;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.ScalaRunTime$;
import scala.runtime.Statics;
import scala.util.Success;
import scala.util.Try;

@ScalaSignature(bytes="\u0006\u0005\u0015ux\u0001CA\b\u0003#A\t!a\b\u0007\u0011\u0005\r\u0012\u0011\u0003E\u0001\u0003KAq!!\u0015\u0002\t\u0003)yD\u0002\u0004\u0003\u001a\u0006\u0011%1\u0014\u0005\u000b\u0003;\u001b!Q3A\u0005\u0002\u0005}\u0005BCAY\u0007\tE\t\u0015!\u0003\u0002\"\"Q!QT\u0002\u0003\u0006\u0004%IAa(\t\u0015\t=6A!A!\u0002\u0013\u0011\t\u000b\u0003\u0006\u00032\u000e\u0011)\u0019!C\u0001\u0005gC!B!0\u0004\u0005\u0003\u0005\u000b\u0011\u0002B[\u0011%\t\tf\u0001C\u0001\u00033\u0011y\fC\u0004\u0003X\u000e!\tA!7\t\u0013\tM1!!A\u0005\u0002\tm\u0007\"\u0003B\u000f\u0007E\u0005I\u0011\u0001B\u0010\u0011%\u0011idAA\u0001\n\u0003\u0012y\u0004C\u0005\u0003N\r\t\t\u0011\"\u0001\u0003P!I!qK\u0002\u0002\u0002\u0013\u0005!Q\u001d\u0005\n\u0005?\u001a\u0011\u0011!C!\u0005CB\u0011Ba\u001c\u0004\u0003\u0003%\tA!;\t\u0013\tm4!!A\u0005B\t5\b\"\u0003BA\u0007\u0005\u0005I\u0011\tBB\u0011%\u0011)iAA\u0001\n\u0003\u00129\tC\u0005\u0003\n\u000e\t\t\u0011\"\u0011\u0003r\u001eIQ\u0011I\u0001\u0002\u0002#\u0005Q1\t\u0004\n\u00053\u000b\u0011\u0011!E\u0001\u000b\u000bBq!!\u0015\u0019\t\u0003)y\u0005C\u0005\u0003\u0006b\t\t\u0011\"\u0012\u0003\b\"IQ\u0011\u000b\r\u0002\u0002\u0013\u0005U1\u000b\u0005\n\u000b;B\u0012\u0011!CA\u000b?B\u0011\"\"\u001a\u0019\u0003\u0003%I!b\u001a\u0007\r\u0005m\u0014AQA?\u0011)\tiJ\bBK\u0002\u0013\u0005\u0011q\u0014\u0005\u000b\u0003cs\"\u0011#Q\u0001\n\u0005\u0005\u0006BCAZ=\tU\r\u0011\"\u0001\u0002 \"Q\u0011Q\u0017\u0010\u0003\u0012\u0003\u0006I!!)\t\u0015\u0005]fD!f\u0001\n\u0003\tI\f\u0003\u0006\u0002Vz\u0011\t\u0012)A\u0005\u0003wCq!!\u0015\u001f\t\u0003\t9\u000eC\u0004\u0002dz!\t!!:\t\u0013\tMa$!A\u0005\u0002\tU\u0001\"\u0003B\u000f=E\u0005I\u0011\u0001B\u0010\u0011%\u0011)DHI\u0001\n\u0003\u0011y\u0002C\u0005\u00038y\t\n\u0011\"\u0001\u0003:!I!Q\b\u0010\u0002\u0002\u0013\u0005#q\b\u0005\n\u0005\u001br\u0012\u0011!C\u0001\u0005\u001fB\u0011Ba\u0016\u001f\u0003\u0003%\tA!\u0017\t\u0013\t}c$!A\u0005B\t\u0005\u0004\"\u0003B8=\u0005\u0005I\u0011\u0001B9\u0011%\u0011YHHA\u0001\n\u0003\u0012i\bC\u0005\u0003\u0002z\t\t\u0011\"\u0011\u0003\u0004\"I!Q\u0011\u0010\u0002\u0002\u0013\u0005#q\u0011\u0005\n\u0005\u0013s\u0012\u0011!C!\u0005\u0017;\u0011\"b\u001c\u0002\u0003\u0003E\t!\"\u001d\u0007\u0013\u0005m\u0014!!A\t\u0002\u0015M\u0004bBA)k\u0011\u0005Q\u0011\u0011\u0005\n\u0005\u000b+\u0014\u0011!C#\u0005\u000fC\u0011\"\"\u00156\u0003\u0003%\t)b!\t\u0013\u0015uS'!A\u0005\u0002\u0016-\u0005\"CC3k\u0005\u0005I\u0011BC4\r\u0019\u0019Y*\u0001\"\u0004\u001e\"Q\u00111W\u001e\u0003\u0016\u0004%\t!a(\t\u0015\u0005U6H!E!\u0002\u0013\t\t\u000b\u0003\u0006\u0002\u001en\u0012)\u001a!C\u0001\u0003?C!\"!-<\u0005#\u0005\u000b\u0011BAQ\u0011\u001d\t\tf\u000fC\u0001\u0007?C\u0011Ba\u0005<\u0003\u0003%\taa*\t\u0013\tu1(%A\u0005\u0002\t}\u0001\"\u0003B\u001bwE\u0005I\u0011\u0001B\u0010\u0011%\u0011idOA\u0001\n\u0003\u0012y\u0004C\u0005\u0003Nm\n\t\u0011\"\u0001\u0003P!I!qK\u001e\u0002\u0002\u0013\u00051Q\u0016\u0005\n\u0005?Z\u0014\u0011!C!\u0005CB\u0011Ba\u001c<\u0003\u0003%\ta!-\t\u0013\tm4(!A\u0005B\rU\u0006\"\u0003BAw\u0005\u0005I\u0011\tBB\u0011%\u0011)iOA\u0001\n\u0003\u00129\tC\u0005\u0003\nn\n\t\u0011\"\u0011\u0004:\u001eIQqS\u0001\u0002\u0002#\u0005Q\u0011\u0014\u0004\n\u00077\u000b\u0011\u0011!E\u0001\u000b7Cq!!\u0015O\t\u0003)\u0019\u000bC\u0005\u0003\u0006:\u000b\t\u0011\"\u0012\u0003\b\"IQ\u0011\u000b(\u0002\u0002\u0013\u0005UQ\u0015\u0005\n\u000b;r\u0015\u0011!CA\u000bWC\u0011\"\"\u001aO\u0003\u0003%I!b\u001a\t\u000f\u0015E\u0013\u0001\"\u0001\u00068\"9Q1Y\u0001\u0005B\u0015\u0015\u0007bBCb\u0003\u0011\u0005S\u0011\u001a\u0005\b\u000b'\fA\u0011ACk\u0011\u001d)9.\u0001C\u0001\u000b3D\u0011\"\"8\u0002\u0005\u0004%I!b8\t\u0011\u0015U\u0018\u0001)A\u0005\u000bCD1\"b>\u0002\u0005\u0004%\t!!\u0007\u0003P!AQ\u0011`\u0001!\u0002\u0013\u0011\tFB\u0004\u0002$\u0005E!!!\u0011\t\u0015\u0005%SL!A!\u0002\u0013\tY\u0005C\u0004\u0002Ru#\t!a\u0015\t\u0013\u0005]SL1A\u0005\u0002\u0005e\u0003\u0002CA6;\u0002\u0006I!a\u0017\t\u000f\u00055T\f\"\u0001\u0002p!I1qJ/\u0012\u0002\u0013\u00051\u0011\u000b\u0005\n\u0007+j\u0016\u0013!C\u0001\u0007/B\u0011ba\u0017^#\u0003%\ta!\u0018\t\u0013\r\u0005T,%A\u0005\u0002\r\r\u0004bBB4;\u0012\u00051\u0011\u000e\u0005\n\u0007\u0013k\u0016\u0013!C\u0001\u0007#B\u0011ba#^#\u0003%\taa\u0016\t\u0013\r5U,%A\u0005\u0002\ru\u0003\"CBH;F\u0005I\u0011AB2\u0011\u001d\u0019\t*\u0018C\u0001\u0007'C\u0011b!5^#\u0003%\taa5\t\u0013\r]W,%A\u0005\u0002\r]\u0003\"CBm;F\u0005I\u0011AB/\u0011%\u0019Y.XI\u0001\n\u0003\u0019\u0019\u0007C\u0005\u0004^v\u000b\n\u0011\"\u0001\u0004d!91\u0011S/\u0005\u0002\r}\u0007bBBt;\u0012\u00051\u0011\u001e\u0005\b\u0007OlF\u0011\u0001C\u0018\u0011%!\t%XI\u0001\n\u0003\u0019\u0019\u000eC\u0005\u0005Du\u000b\n\u0011\"\u0001\u0004X!IAQI/\u0012\u0002\u0013\u000511\r\u0005\n\t\u000fj\u0016\u0013!C\u0001\u0007GBq\u0001\"\u0013^\t\u0003!Y\u0005C\u0004\u0005Ju#\t\u0001b\u0017\t\u000f\u0011UU\f\"\u0001\u0005\u0018\"IAQV/\u0012\u0002\u0013\u00051\u0011\u000b\u0005\n\t_k\u0016\u0013!C\u0001\u0007/B\u0011\u0002\"-^#\u0003%\taa\u0019\t\u000f\u0011MV\f\"\u0001\u00056\"9A1W/\u0005\u0002\u0011u\u0006b\u0002Ch;\u0012\u0005A\u0011\u001b\u0005\b\t\u001flF\u0011\u0001Cu\u0011\u001d)Y!\u0018C\u0001\u000b\u001bA\u0011\"b\r^#\u0003%\ta!\u0015\t\u0013\u0015UR,%A\u0005\u0002\r]\u0003\"CC\u001c;F\u0005I\u0011AB2\u0003\r!6\r\u001d\u0006\u0005\u0003'\t)\"\u0001\u0005tG\u0006d\u0017\rZ:m\u0015\u0011\t9\"!\u0007\u0002\rM$(/Z1n\u0015\t\tY\"\u0001\u0003bW.\f7\u0001\u0001\t\u0004\u0003C\tQBAA\t\u0005\r!6\r]\n\b\u0003\u0005\u001d\u00121GC\u001d!\u0011\tI#a\f\u000e\u0005\u0005-\"BAA\u0017\u0003\u0015\u00198-\u00197b\u0013\u0011\t\t$a\u000b\u0003\r\u0005s\u0017PU3g!\u0019\t)$a\u000f\u0002@5\u0011\u0011q\u0007\u0006\u0005\u0003s\tI\"A\u0003bGR|'/\u0003\u0003\u0002>\u0005]\"aC#yi\u0016t7/[8o\u0013\u0012\u00042!!\t^'\u0015i\u0016qEA\"!\u0011\t)$!\u0012\n\t\u0005\u001d\u0013q\u0007\u0002\n\u000bb$XM\\:j_:\faa]=ti\u0016l\u0007\u0003BA\u001b\u0003\u001bJA!a\u0014\u00028\t\u0019R\t\u001f;f]\u0012,G-Q2u_J\u001c\u0016p\u001d;f[\u00061A(\u001b8jiz\"B!a\u0010\u0002V!9\u0011\u0011J0A\u0002\u0005-\u0013a\u00052j]\u0012\u001c\u0006.\u001e;e_^tG+[7f_V$XCAA.!\u0011\ti&a\u001a\u000e\u0005\u0005}#\u0002BA1\u0003G\n\u0001\u0002Z;sCRLwN\u001c\u0006\u0005\u0003K\nY#\u0001\u0006d_:\u001cWO\u001d:f]RLA!!\u001b\u0002`\tqa)\u001b8ji\u0016$UO]1uS>t\u0017\u0001\u00062j]\u0012\u001c\u0006.\u001e;e_^tG+[7f_V$\b%\u0001\u0003cS:$GCDA9\u0005k\u001c9aa\u0003\u0004\u0010\r\u00053Q\t\t\t\u0003C\t\u0019(a\u001e\u0003\u0010&!\u0011QOA\t\u0005\u0019\u0019v.\u001e:dKB\u0019\u0011\u0011\u0010\u0010\u000f\u0007\u0005\u0005\u0002A\u0001\nJ]\u000e|W.\u001b8h\u0007>tg.Z2uS>t7c\u0002\u0010\u0002(\u0005}\u0014Q\u0011\t\u0005\u0003S\t\t)\u0003\u0003\u0002\u0004\u0006-\"a\u0002)s_\u0012,8\r\u001e\t\u0005\u0003\u000f\u000b9J\u0004\u0003\u0002\n\u0006Me\u0002BAF\u0003#k!!!$\u000b\t\u0005=\u0015QD\u0001\u0007yI|w\u000e\u001e \n\u0005\u00055\u0012\u0002BAK\u0003W\tq\u0001]1dW\u0006<W-\u0003\u0003\u0002\u001a\u0006m%\u0001D*fe&\fG.\u001b>bE2,'\u0002BAK\u0003W\tA\u0002\\8dC2\fE\r\u001a:fgN,\"!!)\u0011\t\u0005\r\u0016QV\u0007\u0003\u0003KSA!a*\u0002*\u0006\u0019a.\u001a;\u000b\u0005\u0005-\u0016\u0001\u00026bm\u0006LA!a,\u0002&\n\t\u0012J\\3u'>\u001c7.\u001a;BI\u0012\u0014Xm]:\u0002\u001b1|7-\u00197BI\u0012\u0014Xm]:!\u00035\u0011X-\\8uK\u0006#GM]3tg\u0006q!/Z7pi\u0016\fE\r\u001a:fgN\u0004\u0013\u0001\u00024m_^,\"!a/\u0011\u0015\u0005\u0005\u0012QXAa\u0003\u0003\fi-\u0003\u0003\u0002@\u0006E!\u0001\u0002$m_^\u0004B!a1\u0002J6\u0011\u0011Q\u0019\u0006\u0005\u0003\u000f\fI\"\u0001\u0003vi&d\u0017\u0002BAf\u0003\u000b\u0014!BQ=uKN#(/\u001b8h!\u0011\ty-!5\u000e\u0005\u0005e\u0011\u0002BAj\u00033\u0011qAT8u+N,G-A\u0003gY><\b\u0005\u0006\u0005\u0002Z\u0006u\u0017q\\Aq!\r\tYNH\u0007\u0002\u0003!9\u0011QT\u0013A\u0002\u0005\u0005\u0006bBAZK\u0001\u0007\u0011\u0011\u0015\u0005\b\u0003o+\u0003\u0019AA^\u0003)A\u0017M\u001c3mK^KG\u000f[\u000b\u0005\u0003O\fy\u000f\u0006\u0003\u0002j\n5A\u0003BAv\u0005\u0003\u0001B!!<\u0002p2\u0001AaBAyM\t\u0007\u00111\u001f\u0002\u0004\u001b\u0006$\u0018\u0003BA{\u0003w\u0004B!!\u000b\u0002x&!\u0011\u0011`A\u0016\u0005\u001dqu\u000e\u001e5j]\u001e\u0004B!!\u000b\u0002~&!\u0011q`A\u0016\u0005\r\te.\u001f\u0005\b\u0005\u00071\u00039\u0001B\u0003\u00031i\u0017\r^3sS\u0006d\u0017N_3s!\u0011\u00119A!\u0003\u000e\u0005\u0005U\u0011\u0002\u0002B\u0006\u0003+\u0011A\"T1uKJL\u0017\r\\5{KJDqAa\u0004'\u0001\u0004\u0011\t\"A\u0004iC:$G.\u001a:\u0011\u0015\u0005\u0005\u0012QXAa\u0003\u0003\fY/\u0001\u0003d_BLH\u0003CAm\u0005/\u0011IBa\u0007\t\u0013\u0005uu\u0005%AA\u0002\u0005\u0005\u0006\"CAZOA\u0005\t\u0019AAQ\u0011%\t9l\nI\u0001\u0002\u0004\tY,\u0001\bd_BLH\u0005Z3gCVdG\u000fJ\u0019\u0016\u0005\t\u0005\"\u0006BAQ\u0005GY#A!\n\u0011\t\t\u001d\"\u0011G\u0007\u0003\u0005SQAAa\u000b\u0003.\u0005IQO\\2iK\u000e\\W\r\u001a\u0006\u0005\u0005_\tY#\u0001\u0006b]:|G/\u0019;j_:LAAa\r\u0003*\t\tRO\\2iK\u000e\\W\r\u001a,be&\fgnY3\u0002\u001d\r|\u0007/\u001f\u0013eK\u001a\fW\u000f\u001c;%e\u0005q1m\u001c9zI\u0011,g-Y;mi\u0012\u001aTC\u0001B\u001eU\u0011\tYLa\t\u0002\u001bA\u0014x\u000eZ;diB\u0013XMZ5y+\t\u0011\t\u0005\u0005\u0003\u0003D\t%SB\u0001B#\u0015\u0011\u00119%!+\u0002\t1\fgnZ\u0005\u0005\u0005\u0017\u0012)E\u0001\u0004TiJLgnZ\u0001\raJ|G-^2u\u0003JLG/_\u000b\u0003\u0005#\u0002B!!\u000b\u0003T%!!QKA\u0016\u0005\rIe\u000e^\u0001\u000faJ|G-^2u\u000b2,W.\u001a8u)\u0011\tYPa\u0017\t\u0013\tuS&!AA\u0002\tE\u0013a\u0001=%c\u0005y\u0001O]8ek\u000e$\u0018\n^3sCR|'/\u0006\u0002\u0003dA1!Q\rB6\u0003wl!Aa\u001a\u000b\t\t%\u00141F\u0001\u000bG>dG.Z2uS>t\u0017\u0002\u0002B7\u0005O\u0012\u0001\"\u0013;fe\u0006$xN]\u0001\tG\u0006tW)];bYR!!1\u000fB=!\u0011\tIC!\u001e\n\t\t]\u00141\u0006\u0002\b\u0005>|G.Z1o\u0011%\u0011ifLA\u0001\u0002\u0004\tY0\u0001\nqe>$Wo\u0019;FY\u0016lWM\u001c;OC6,G\u0003\u0002B!\u0005\u007fB\u0011B!\u00181\u0003\u0003\u0005\rA!\u0015\u0002\u0011!\f7\u000f[\"pI\u0016$\"A!\u0015\u0002\u0011Q|7\u000b\u001e:j]\u001e$\"A!\u0011\u0002\r\u0015\fX/\u00197t)\u0011\u0011\u0019H!$\t\u0013\tu3'!AA\u0002\u0005m\bC\u0002BI\u0005'\u00139*\u0004\u0002\u0002d%!!QSA2\u0005\u00191U\u000f^;sKB\u0019\u0011\u0011P\u0002\u0003\u001bM+'O^3s\u0005&tG-\u001b8h'\u001d\u0019\u0011qEA@\u0003\u000b\u000bA\"\u001e8cS:$\u0017i\u0019;j_:,\"A!)\u0011\r\u0005%\"1\u0015BT\u0013\u0011\u0011)+a\u000b\u0003\u0013\u0019+hn\u0019;j_:\u0004\u0004C\u0002BI\u0005'\u0013I\u000b\u0005\u0003\u0002*\t-\u0016\u0002\u0002BW\u0003W\u0011A!\u00168ji\u0006iQO\u001c2j]\u0012\f5\r^5p]\u0002\n1b\u001e5f]Vs'm\\;oIV\u0011!Q\u0017\t\u0007\u0005#\u0013\u0019Ja.\u0011\t\u0005='\u0011X\u0005\u0005\u0005w\u000bIB\u0001\u0003E_:,\u0017\u0001D<iK:,fNY8v]\u0012\u0004C\u0003\u0002Ba\u0005\u0013$bAa1\u0003F\n\u001d\u0007cAAn\u0007!9!Q\u0014\u0006A\u0002\t\u0005\u0006b\u0002BY\u0015\u0001\u0007!Q\u0017\u0005\b\u0003;S\u0001\u0019AAQQ\rQ!Q\u001a\t\u0005\u0005\u001f\u0014\u0019.\u0004\u0002\u0003R*!!qFA\r\u0013\u0011\u0011)N!5\u0003\u0017%sG/\u001a:oC2\f\u0005/[\u0001\u0007k:\u0014\u0017N\u001c3\u0015\u0005\t\u001dF\u0003\u0002Bo\u0005G$bAa1\u0003`\n\u0005\bb\u0002BO\u0019\u0001\u0007!\u0011\u0015\u0005\b\u0005cc\u0001\u0019\u0001B[\u0011%\ti\n\u0004I\u0001\u0002\u0004\t\t\u000b\u0006\u0003\u0002|\n\u001d\b\"\u0003B/!\u0005\u0005\t\u0019\u0001B))\u0011\u0011\u0019Ha;\t\u0013\tu##!AA\u0002\u0005mH\u0003\u0002B!\u0005_D\u0011B!\u0018\u0014\u0003\u0003\u0005\rA!\u0015\u0015\t\tM$1\u001f\u0005\n\u0005;2\u0012\u0011!a\u0001\u0003wDqAa>c\u0001\u0004\u0011I0A\u0005j]R,'OZ1dKB!!1`B\u0002\u001d\u0011\u0011iPa@\u0011\t\u0005-\u00151F\u0005\u0005\u0007\u0003\tY#\u0001\u0004Qe\u0016$WMZ\u0005\u0005\u0005\u0017\u001a)A\u0003\u0003\u0004\u0002\u0005-\u0002bBB\u0005E\u0002\u0007!\u0011K\u0001\u0005a>\u0014H\u000fC\u0005\u0004\u000e\t\u0004\n\u00111\u0001\u0003R\u00059!-Y2lY><\u0007\"CB\tEB\u0005\t\u0019AB\n\u0003\u001dy\u0007\u000f^5p]N\u0004ba!\u0006\u0004\"\r\u001db\u0002BB\f\u0007;qAA!\u001a\u0004\u001a%!11\u0004B4\u0003%IW.\\;uC\ndW-\u0003\u0003\u0002\u0016\u000e}!\u0002BB\u000e\u0005OJAaa\t\u0004&\tYAK]1wKJ\u001c\u0018M\u00197f\u0015\u0011\t)ja\b\u0011\t\r%21\b\b\u0005\u0007W\u0019)D\u0004\u0003\u0004.\rEb\u0002BAF\u0007_I!!a\u0007\n\t\rM\u0012\u0011D\u0001\u0003S>LAaa\u000e\u0004:\u0005!\u0011J\\3u\u0015\u0011\u0019\u0019$!\u0007\n\t\ru2q\b\u0002\r'>\u001c7.\u001a;PaRLwN\u001c\u0006\u0005\u0007o\u0019I\u0004C\u0005\u0004D\t\u0004\n\u00111\u0001\u0003t\u0005I\u0001.\u00197g\u00072|7/\u001a\u0005\n\u0007\u000f\u0012\u0007\u0013!a\u0001\u0007\u0013\n1\"\u001b3mKRKW.Z8viB!\u0011QLB&\u0013\u0011\u0019i%a\u0018\u0003\u0011\u0011+(/\u0019;j_:\faBY5oI\u0012\"WMZ1vYR$3'\u0006\u0002\u0004T)\"!\u0011\u000bB\u0012\u00039\u0011\u0017N\u001c3%I\u00164\u0017-\u001e7uIQ*\"a!\u0017+\t\rM!1E\u0001\u000fE&tG\r\n3fM\u0006,H\u000e\u001e\u00136+\t\u0019yF\u000b\u0003\u0003t\t\r\u0012A\u00042j]\u0012$C-\u001a4bk2$HEN\u000b\u0003\u0007KRCa!\u0013\u0003$\u0005i!-\u001b8e\u0003:$\u0007*\u00198eY\u0016$\u0002ca\u001b\u0004r\ru4qPBA\u0007\u0007\u001b)ia\"\u0015\t\t=5Q\u000e\u0005\b\u0007_:\u00079\u0001B\u0003\u0003\u0005i\u0007b\u0002B\bO\u0002\u000711\u000f\u0019\u0005\u0007k\u001aI\b\u0005\u0006\u0002\"\u0005u\u0016\u0011YAa\u0007o\u0002B!!<\u0004z\u0011a11PB9\u0003\u0003\u0005\tQ!\u0001\u0002t\n\u0019q\fJ\u0019\t\u000f\t]x\r1\u0001\u0003z\"91\u0011B4A\u0002\tE\u0003\"CB\u0007OB\u0005\t\u0019\u0001B)\u0011%\u0019\tb\u001aI\u0001\u0002\u0004\u0019\u0019\u0002C\u0005\u0004D\u001d\u0004\n\u00111\u0001\u0003t!I1qI4\u0011\u0002\u0003\u00071\u0011J\u0001\u0018E&tG-\u00118e\u0011\u0006tG\r\\3%I\u00164\u0017-\u001e7uIQ\nqCY5oI\u0006sG\rS1oI2,G\u0005Z3gCVdG\u000fJ\u001b\u0002/\tLg\u000eZ!oI\"\u000bg\u000e\u001a7fI\u0011,g-Y;mi\u00122\u0014a\u00062j]\u0012\fe\u000e\u001a%b]\u0012dW\r\n3fM\u0006,H\u000e\u001e\u00138\u0003IyW\u000f^4pS:<7i\u001c8oK\u000e$\u0018n\u001c8\u0015\u001d\rU5QXB`\u0007\u000f\u001cIma3\u0004PBQ\u0011\u0011EA_\u0003\u0003\f\tma&\u0011\r\tE%1SBM!\r\tIh\u000f\u0002\u0013\u001fV$xm\\5oO\u000e{gN\\3di&|gnE\u0004<\u0003O\ty(!\"\u0015\r\r\u000561UBS!\r\tYn\u000f\u0005\b\u0003g\u0003\u0005\u0019AAQ\u0011\u001d\ti\n\u0011a\u0001\u0003C#ba!)\u0004*\u000e-\u0006\"CAZ\u0003B\u0005\t\u0019AAQ\u0011%\ti*\u0011I\u0001\u0002\u0004\t\t\u000b\u0006\u0003\u0002|\u000e=\u0006\"\u0003B/\r\u0006\u0005\t\u0019\u0001B))\u0011\u0011\u0019ha-\t\u0013\tu\u0003*!AA\u0002\u0005mH\u0003\u0002B!\u0007oC\u0011B!\u0018J\u0003\u0003\u0005\rA!\u0015\u0015\t\tM41\u0018\u0005\n\u0005;b\u0015\u0011!a\u0001\u0003wDq!a-m\u0001\u0004\t\t\u000bC\u0005\u0002\u001e2\u0004\n\u00111\u0001\u0004BB1\u0011\u0011FBb\u0003CKAa!2\u0002,\t1q\n\u001d;j_:D\u0011b!\u0005m!\u0003\u0005\raa\u0005\t\u0013\r\rC\u000e%AA\u0002\tM\u0004\"CBgYB\u0005\t\u0019AB%\u00039\u0019wN\u001c8fGR$\u0016.\\3pkRD\u0011ba\u0012m!\u0003\u0005\ra!\u0013\u00029=,HoZ8j]\u001e\u001cuN\u001c8fGRLwN\u001c\u0013eK\u001a\fW\u000f\u001c;%eU\u00111Q\u001b\u0016\u0005\u0007\u0003\u0014\u0019#\u0001\u000fpkR<w.\u001b8h\u0007>tg.Z2uS>tG\u0005Z3gCVdG\u000fJ\u001a\u00029=,HoZ8j]\u001e\u001cuN\u001c8fGRLwN\u001c\u0013eK\u001a\fW\u000f\u001c;%i\u0005ar.\u001e;h_&twmQ8o]\u0016\u001cG/[8oI\u0011,g-Y;mi\u0012*\u0014\u0001H8vi\u001e|\u0017N\\4D_:tWm\u0019;j_:$C-\u001a4bk2$HE\u000e\u000b\u0007\u0007+\u001b\to!:\t\u000f\r\r(\u000f1\u0001\u0003z\u0006!\u0001n\\:u\u0011\u001d\u0019IA\u001da\u0001\u0005#\nQc\\;uO>Lgn\u001a+mg\u000e{gN\\3di&|g\u000e\u0006\u0006\u0004\u0016\u000e-8Q^Bx\t\u000bAqaa9t\u0001\u0004\u0011I\u0010C\u0004\u0004\nM\u0004\rA!\u0015\t\u000f\rE8\u000f1\u0001\u0004t\u0006Q1o\u001d7D_:$X\r\u001f;\u0011\t\rUH\u0011A\u0007\u0003\u0007oTAa!?\u0004|\u0006\u00191o\u001d7\u000b\t\u0005\u001d6Q \u0006\u0003\u0007\u007f\fQA[1wCbLA\u0001b\u0001\u0004x\nQ1k\u0015'D_:$X\r\u001f;\t\u000f\u0011\u001d1\u000f1\u0001\u0005\n\u0005\u0019b.Z4pi&\fG/\u001a(foN+7o]5p]B!A1\u0002C\u000b\u001d\u0011!i\u0001\"\u0005\u000f\t\r5BqB\u0005\u0005\u0003/\tI\"\u0003\u0003\u0005\u0014\u0005U\u0011a\u0003+M'B\u0013x\u000e^8d_2LA\u0001b\u0006\u0005\u001a\t\u0019b*Z4pi&\fG/\u001a(foN+7o]5p]*!A1CA\u000bQ-\u0019HQ\u0004C\u0012\tK!I\u0003b\u000b\u0011\t\u0005%BqD\u0005\u0005\tC\tYC\u0001\u0006eKB\u0014XmY1uK\u0012\fq!\\3tg\u0006<W-\t\u0002\u0005(\u0005\tXk]3!_V$xm\\5oO\u000e{gN\\3di&|gnV5uQRc7\u000f\t;iCR\u0004C/Y6fg\u0002\n\u0007eU*M\u000b:<\u0017N\\3!M\u0006\u001cGo\u001c:zA%t7\u000f^3bI:\u00023+\u001a;va\u0002\"\b.\u001a\u0011T'2+enZ5oK\u0002:\u0018\u000e\u001e5!]\u0016,G-\u001a3!a\u0006\u0014\u0018-\\3uKJ\u001ch&A\u0003tS:\u001cW-\t\u0002\u0005.\u0005)!G\f\u001c/aQ\u00012Q\u0013C\u0019\tg!)\u0004b\u000e\u0005:\u0011mBQ\b\u0005\b\u0003g#\b\u0019AAQ\u0011\u001d\u0019\t\u0010\u001ea\u0001\u0007gDq\u0001b\u0002u\u0001\u0004!I\u0001C\u0005\u0002\u001eR\u0004\n\u00111\u0001\u0004B\"I1\u0011\u0003;\u0011\u0002\u0003\u000711\u0003\u0005\n\u0007\u001b$\b\u0013!a\u0001\u0007\u0013B\u0011ba\u0012u!\u0003\u0005\ra!\u0013)\u0017Q$i\u0002b\t\u0005&\u0011%B1F\u0001 _V$xm\\5oORc7oQ8o]\u0016\u001cG/[8oI\u0011,g-Y;mi\u0012\"\u0014aH8vi\u001e|\u0017N\\4UYN\u001cuN\u001c8fGRLwN\u001c\u0013eK\u001a\fW\u000f\u001c;%k\u0005yr.\u001e;h_&tw\r\u00167t\u0007>tg.Z2uS>tG\u0005Z3gCVdG\u000f\n\u001c\u0002?=,HoZ8j]\u001e$Fn]\"p]:,7\r^5p]\u0012\"WMZ1vYR$s'A\rpkR<w.\u001b8h\u0007>tg.Z2uS>tw+\u001b;i)2\u001cHCBBK\t\u001b\"y\u0005C\u0004\u00024f\u0004\r!!)\t\u000f\u0011E\u0013\u00101\u0001\u0005T\u0005y1M]3bi\u0016\u001c6\u000bT#oO&tW\r\u0005\u0004\u0002*\t\rFQ\u000b\t\u0005\u0007k$9&\u0003\u0003\u0005Z\r](!C*T\u0019\u0016sw-\u001b8f)I\u0019)\n\"\u0018\u0005`\u0011\u0005D1\rC7\t_\"\t\bb#\t\u000f\u0005M&\u00101\u0001\u0002\"\"9A\u0011\u000b>A\u0002\u0011M\u0003bBAOu\u0002\u00071\u0011\u0019\u0005\b\u0007#Q\b\u0019\u0001C3!\u0019!9\u0007\"\u001b\u0004(5\u00111qD\u0005\u0005\tW\u001ayBA\u0002TKFDqa!4{\u0001\u0004\u0019I\u0005C\u0004\u0004Hi\u0004\ra!\u0013\t\u000f\u0011M$\u00101\u0001\u0005v\u0005ia/\u001a:jMf\u001cVm]:j_:\u0004\u0002\"!\u000b\u0005x\u0011mD\u0011Q\u0005\u0005\ts\nYCA\u0005Gk:\u001cG/[8ocA!1Q\u001fC?\u0013\u0011!yha>\u0003\u0015M\u001bFjU3tg&|g\u000e\u0005\u0004\u0005\u0004\u0012\u001d%\u0011V\u0007\u0003\t\u000bSA!a2\u0002,%!A\u0011\u0012CC\u0005\r!&/\u001f\u0005\b\t\u001bS\b\u0019\u0001CH\u0003\u001d\u0019Gn\\:j]\u001e\u0004BAa\u0002\u0005\u0012&!A1SA\u000b\u0005)!FjU\"m_NLgnZ\u0001\bE&tG\r\u00167t)A\t\t\b\"'\u0005\u001c\u0012uEq\u0014CQ\tG#)\u000bC\u0004\u0003xn\u0004\rA!?\t\u000f\r%1\u00101\u0001\u0003R!91\u0011_>A\u0002\rM\bb\u0002C\u0004w\u0002\u0007A\u0011\u0002\u0005\n\u0007\u001bY\b\u0013!a\u0001\u0005#B\u0011b!\u0005|!\u0003\u0005\raa\u0005\t\u0013\r\u001d3\u0010%AA\u0002\r%\u0003fC>\u0005\u001e\u0011\rB\u0011\u0016C\u0015\tW\t#\u0001b+\u0002GV\u001bX\r\t2j]\u0012<\u0016\u000e\u001e5UYN\u0004C\u000f[1uAQ\f7.Z:!C\u0002\u001a6\u000bT#oO&tW\r\t4bGR|'/\u001f\u0011j]N$X-\u00193/AM+G/\u001e9!i\",\u0007eU*M\u000b:<\u0017N\\3!o&$\b\u000e\t8fK\u0012,G\r\t9be\u0006lW\r^3sg:\n\u0011CY5oIRc7\u000f\n3fM\u0006,H\u000e\u001e\u00136\u0003E\u0011\u0017N\u001c3UYN$C-\u001a4bk2$HEN\u0001\u0012E&tG\r\u00167tI\u0011,g-Y;mi\u0012:\u0014a\u00032j]\u0012<\u0016\u000e\u001e5UYN$\u0002\"!\u001d\u00058\u0012eF1\u0018\u0005\b\u0005o|\b\u0019\u0001B}\u0011\u001d\u0019Ia a\u0001\u0005#Bq\u0001\"\u0015\u0000\u0001\u0004!\u0019\u0006\u0006\n\u0002r\u0011}F\u0011\u0019Cb\t\u000b$9\r\"3\u0005L\u00125\u0007\u0002\u0003B|\u0003\u0003\u0001\rA!?\t\u0011\r%\u0011\u0011\u0001a\u0001\u0005#B\u0001\u0002\"\u0015\u0002\u0002\u0001\u0007A1\u000b\u0005\t\u0007\u001b\t\t\u00011\u0001\u0003R!A1\u0011CA\u0001\u0001\u0004!)\u0007\u0003\u0005\u0004H\u0005\u0005\u0001\u0019AB%\u0011!!\u0019(!\u0001A\u0002\u0011U\u0004\u0002\u0003CG\u0003\u0003\u0001\r\u0001b$\u0002)\tLg\u000eZ!oI\"\u000bg\u000e\u001a7f/&$\b\u000e\u00167t))!\u0019\u000eb6\u0005d\u0012\u0015Hq\u001d\u000b\u0005\u0005\u001f#)\u000e\u0003\u0005\u0004p\u0005\r\u00019\u0001B\u0003\u0011!\u0011y!a\u0001A\u0002\u0011e\u0007\u0007\u0002Cn\t?\u0004\"\"!\t\u0002>\u0006\u0005\u0017\u0011\u0019Co!\u0011\ti\u000fb8\u0005\u0019\u0011\u0005Hq[A\u0001\u0002\u0003\u0015\t!a=\u0003\u0007}##\u0007\u0003\u0005\u0003x\u0006\r\u0001\u0019\u0001B}\u0011!\u0019I!a\u0001A\u0002\tE\u0003\u0002\u0003C)\u0003\u0007\u0001\r\u0001b\u0015\u0015)\u0011-Hq\u001eC~\t{$y0\"\u0001\u0006\u0004\u0015\u0015QqAC\u0005)\u0011\u0011y\t\"<\t\u0011\r=\u0014Q\u0001a\u0002\u0005\u000bA\u0001Ba\u0004\u0002\u0006\u0001\u0007A\u0011\u001f\u0019\u0005\tg$9\u0010\u0005\u0006\u0002\"\u0005u\u0016\u0011YAa\tk\u0004B!!<\u0005x\u0012aA\u0011 Cx\u0003\u0003\u0005\tQ!\u0001\u0002t\n\u0019q\fJ\u001a\t\u0011\t]\u0018Q\u0001a\u0001\u0005sD\u0001b!\u0003\u0002\u0006\u0001\u0007!\u0011\u000b\u0005\t\t#\n)\u00011\u0001\u0005T!A1QBA\u0003\u0001\u0004\u0011\t\u0006\u0003\u0005\u0004\u0012\u0005\u0015\u0001\u0019\u0001C3\u0011!\u00199%!\u0002A\u0002\r%\u0003\u0002\u0003C:\u0003\u000b\u0001\r\u0001\"\u001e\t\u0011\u00115\u0015Q\u0001a\u0001\t\u001f\u000b\u0001CY5oI\u0006sG\rS1oI2,G\u000b\\:\u0015%\u0015=Q1CC\u0010\u000bC)\u0019#\"\n\u0006(\u0015%R1\u0006\u000b\u0005\u0005\u001f+\t\u0002\u0003\u0005\u0004p\u0005\u001d\u00019\u0001B\u0003\u0011!\u0011y!a\u0002A\u0002\u0015U\u0001\u0007BC\f\u000b7\u0001\"\"!\t\u0002>\u0006\u0005\u0017\u0011YC\r!\u0011\ti/b\u0007\u0005\u0019\u0015uQ1CA\u0001\u0002\u0003\u0015\t!a=\u0003\u0007}#C\u0007\u0003\u0005\u0003x\u0006\u001d\u0001\u0019\u0001B}\u0011!\u0019I!a\u0002A\u0002\tE\u0003\u0002CBy\u0003\u000f\u0001\raa=\t\u0011\u0011\u001d\u0011q\u0001a\u0001\t\u0013A!b!\u0004\u0002\bA\u0005\t\u0019\u0001B)\u0011)\u0019\t\"a\u0002\u0011\u0002\u0003\u000711\u0003\u0005\u000b\u0007\u000f\n9\u0001%AA\u0002\r%\u0003\u0006DA\u0004\t;!\u0019#b\f\u0005*\u0011-\u0012EAC\u0019\u00031,6/\u001a\u0011cS:$\u0017I\u001c3IC:$G.Z,ji\"$Fn\u001d\u0011uQ\u0006$\b\u0005^1lKN\u0004\u0013\rI*T\u0019\u0016sw-\u001b8fA\u0019\f7\r^8ss\u0002Jgn\u001d;fC\u0012t\u0003eU3ukB\u0004C\u000f[3!'NcUI\\4j]\u0016\u0004s/\u001b;iA9,W\rZ3eAA\f'/Y7fi\u0016\u00148OL\u0001\u001bE&tG-\u00118e\u0011\u0006tG\r\\3UYN$C-\u001a4bk2$HEN\u0001\u001bE&tG-\u00118e\u0011\u0006tG\r\\3UYN$C-\u001a4bk2$HeN\u0001\u001bE&tG-\u00118e\u0011\u0006tG\r\\3UYN$C-\u001a4bk2$H\u0005\u000f\t\u0005\u0003k)Y$\u0003\u0003\u0006>\u0005]\"aE#yi\u0016t7/[8o\u0013\u0012\u0004&o\u001c<jI\u0016\u0014HCAA\u0010\u00035\u0019VM\u001d<fe\nKg\u000eZ5oOB\u0019\u00111\u001c\r\u0014\u000ba\t9#b\u0012\u0011\t\u0015%SQJ\u0007\u0003\u000b\u0017RAaa\r\u0002*&!\u0011\u0011TC&)\t)\u0019%A\u0003baBd\u0017\u0010\u0006\u0003\u0006V\u0015mCC\u0002Bb\u000b/*I\u0006C\u0004\u0003\u001en\u0001\rA!)\t\u000f\tE6\u00041\u0001\u00036\"9\u0011QT\u000eA\u0002\u0005\u0005\u0016aB;oCB\u0004H.\u001f\u000b\u0005\u0007\u0003,\t\u0007C\u0005\u0006dq\t\t\u00111\u0001\u0003D\u0006\u0019\u0001\u0010\n\u0019\u0002\u0019]\u0014\u0018\u000e^3SKBd\u0017mY3\u0015\u0005\u0015%\u0004\u0003\u0002B\"\u000bWJA!\"\u001c\u0003F\t1qJ\u00196fGR\f!#\u00138d_6LgnZ\"p]:,7\r^5p]B\u0019\u00111\\\u001b\u0014\u000bU*)(b\u0012\u0011\u0019\u0015]TQPAQ\u0003C\u000bY,!7\u000e\u0005\u0015e$\u0002BC>\u0003W\tqA];oi&lW-\u0003\u0003\u0006\u0000\u0015e$!E!cgR\u0014\u0018m\u0019;Gk:\u001cG/[8ogQ\u0011Q\u0011\u000f\u000b\t\u00033,))b\"\u0006\n\"9\u0011Q\u0014\u001dA\u0002\u0005\u0005\u0006bBAZq\u0001\u0007\u0011\u0011\u0015\u0005\b\u0003oC\u0004\u0019AA^)\u0011)i)\"&\u0011\r\u0005%21YCH!)\tI#\"%\u0002\"\u0006\u0005\u00161X\u0005\u0005\u000b'\u000bYC\u0001\u0004UkBdWm\r\u0005\n\u000bGJ\u0014\u0011!a\u0001\u00033\f!cT;uO>LgnZ\"p]:,7\r^5p]B\u0019\u00111\u001c(\u0014\u000b9+i*b\u0012\u0011\u0015\u0015]TqTAQ\u0003C\u001b\t+\u0003\u0003\u0006\"\u0016e$!E!cgR\u0014\u0018m\u0019;Gk:\u001cG/[8oeQ\u0011Q\u0011\u0014\u000b\u0007\u0007C+9+\"+\t\u000f\u0005M\u0016\u000b1\u0001\u0002\"\"9\u0011QT)A\u0002\u0005\u0005F\u0003BCW\u000bk\u0003b!!\u000b\u0004D\u0016=\u0006\u0003CA\u0015\u000bc\u000b\t+!)\n\t\u0015M\u00161\u0006\u0002\u0007)V\u0004H.\u001a\u001a\t\u0013\u0015\r$+!AA\u0002\r\u0005FCAC])\u0011\ty$b/\t\u000f\u0005%C\u000bq\u0001\u0006>B!\u0011QGC`\u0013\u0011)\t-a\u000e\u0003\u0017\u0005\u001bGo\u001c:TsN$X-\\\u0001\u0004O\u0016$H\u0003BA \u000b\u000fDq!!\u0013V\u0001\u0004)i\f\u0006\u0003\u0002@\u0015-\u0007bBA%-\u0002\u0007QQ\u001a\t\u0005\u0003k)y-\u0003\u0003\u0006R\u0006]\"AG\"mCN\u001c\u0018nY!di>\u00148+_:uK6\u0004&o\u001c<jI\u0016\u0014\u0018A\u00027p_.,\b/\u0006\u0002\u0002 \u0005y1M]3bi\u0016,\u0005\u0010^3og&|g\u000e\u0006\u0003\u0002@\u0015m\u0007bBA%1\u0002\u0007\u00111J\u0001\fi2\u001cxK]1qa&tw-\u0006\u0002\u0006bBq\u0011\u0011ECr\u0003\u0003,9/b<\u0002B\u00065\u0017\u0002BCs\u0003#\u0011\u0001BQ5eS\u001acwn\u001e\t\u0005\u000bS,YO\u0004\u0003\u0003\b\u0011E\u0011\u0002BCw\t3\u0011\u0011bU3oI\nKH/Z:\u0011\t\u0015%X\u0011_\u0005\u0005\u000bg$IBA\u0007Tg2$Fn]%oE>,h\u000eZ\u0001\ri2\u001cxK]1qa&tw\rI\u0001\u000fI\u00164\u0017-\u001e7u\u0005\u0006\u001c7\u000e\\8h\u0003=!WMZ1vYR\u0014\u0015mY6m_\u001e\u0004\u0003f\u0001/\u0003N\u0002")
public final class Tcp
implements Extension {
    private final ExtendedActorSystem system;
    private final FiniteDuration bindShutdownTimeout;

    public static Tcp createExtension(ExtendedActorSystem system) {
        return Tcp$.MODULE$.createExtension(system);
    }

    public static Tcp$ lookup() {
        return Tcp$.MODULE$.lookup();
    }

    public static Tcp get(ClassicActorSystemProvider system) {
        return Tcp$.MODULE$.get(system);
    }

    public static Tcp get(ActorSystem system) {
        return Tcp$.MODULE$.get(system);
    }

    public static Tcp apply(ActorSystem system) {
        return Tcp$.MODULE$.apply(system);
    }

    public static Extension apply(ClassicActorSystemProvider system) {
        return Tcp$.MODULE$.apply(system);
    }

    public FiniteDuration bindShutdownTimeout() {
        return this.bindShutdownTimeout;
    }

    public Source<IncomingConnection, Future<ServerBinding>> bind(String string, int port, int backlog, Iterable<Inet.SocketOption> options, boolean halfClose, Duration idleTimeout) {
        return Source$.MODULE$.fromGraph(new ConnectionSourceStage(IO$.MODULE$.apply(akka.io.Tcp$.MODULE$, this.system), new InetSocketAddress(string, port), backlog, options.toList(), halfClose, idleTimeout, this.bindShutdownTimeout()));
    }

    public int bind$default$3() {
        return Tcp$.MODULE$.defaultBacklog();
    }

    public Iterable<Inet.SocketOption> bind$default$4() {
        return Nil$.MODULE$;
    }

    public boolean bind$default$5() {
        return false;
    }

    public Duration bind$default$6() {
        return Duration$.MODULE$.Inf();
    }

    public Future<ServerBinding> bindAndHandle(Flow<ByteString, ByteString, ?> handler, String string, int port, int backlog, Iterable<Inet.SocketOption> options, boolean halfClose, Duration idleTimeout, Materializer m4) {
        return (Future)((RunnableGraph)this.bind(string, port, backlog, options, halfClose, idleTimeout).to((Graph)Sink$.MODULE$.foreach((Function1<IncomingConnection, Object> & Serializable)conn -> {
            Tcp.$anonfun$bindAndHandle$1(handler, m4, conn);
            return BoxedUnit.UNIT;
        }))).run(m4);
    }

    public int bindAndHandle$default$4() {
        return Tcp$.MODULE$.defaultBacklog();
    }

    public Iterable<Inet.SocketOption> bindAndHandle$default$5() {
        return Nil$.MODULE$;
    }

    public boolean bindAndHandle$default$6() {
        return false;
    }

    public Duration bindAndHandle$default$7() {
        return Duration$.MODULE$.Inf();
    }

    public Flow<ByteString, ByteString, Future<OutgoingConnection>> outgoingConnection(InetSocketAddress remoteAddress, Option<InetSocketAddress> localAddress, Iterable<Inet.SocketOption> options, boolean halfClose, Duration connectTimeout, Duration idleTimeout) {
        FlowOps tcpFlow = Flow$.MODULE$.fromGraph(new OutgoingConnectionStage(IO$.MODULE$.apply(akka.io.Tcp$.MODULE$, this.system), remoteAddress, localAddress, options.toList(), halfClose, connectTimeout)).via((Graph)GraphStages$.MODULE$.detacher());
        Duration duration = idleTimeout;
        if (duration instanceof FiniteDuration) {
            FiniteDuration finiteDuration = (FiniteDuration)duration;
            return ((Flow)tcpFlow).join(TcpIdleTimeout$.MODULE$.apply(finiteDuration, new Some<InetSocketAddress>(remoteAddress)));
        }
        return tcpFlow;
    }

    public Flow<ByteString, ByteString, Future<OutgoingConnection>> outgoingConnection(String host2, int port) {
        return this.outgoingConnection(InetSocketAddress.createUnresolved(host2, port), this.outgoingConnection$default$2(), this.outgoingConnection$default$3(), this.outgoingConnection$default$4(), this.outgoingConnection$default$5(), this.outgoingConnection$default$6());
    }

    public Option<InetSocketAddress> outgoingConnection$default$2() {
        return None$.MODULE$;
    }

    public Iterable<Inet.SocketOption> outgoingConnection$default$3() {
        return Nil$.MODULE$;
    }

    public boolean outgoingConnection$default$4() {
        return true;
    }

    public Duration outgoingConnection$default$5() {
        return Duration$.MODULE$.Inf();
    }

    public Duration outgoingConnection$default$6() {
        return Duration$.MODULE$.Inf();
    }

    public Flow<ByteString, ByteString, Future<OutgoingConnection>> outgoingTlsConnection(String host2, int port, SSLContext sslContext, TLSProtocol.NegotiateNewSession negotiateNewSession) {
        return this.outgoingTlsConnection(InetSocketAddress.createUnresolved(host2, port), sslContext, negotiateNewSession, this.outgoingTlsConnection$default$4(), this.outgoingTlsConnection$default$5(), this.outgoingTlsConnection$default$6(), this.outgoingTlsConnection$default$7());
    }

    public Flow<ByteString, ByteString, Future<OutgoingConnection>> outgoingTlsConnection(InetSocketAddress remoteAddress, SSLContext sslContext, TLSProtocol.NegotiateNewSession negotiateNewSession, Option<InetSocketAddress> localAddress, Iterable<Inet.SocketOption> options, Duration connectTimeout, Duration idleTimeout) {
        Flow<ByteString, ByteString, Future<OutgoingConnection>> connection = this.outgoingConnection(remoteAddress, localAddress, options, true, connectTimeout, idleTimeout);
        BidiFlow<TLSProtocol.SslTlsOutbound, ByteString, ByteString, TLSProtocol.SslTlsInbound, NotUsed> tls = TLS$.MODULE$.apply(sslContext, negotiateNewSession, TLSRole$.MODULE$.client());
        return connection.join(Tcp$.MODULE$.akka$stream$scaladsl$Tcp$$tlsWrapping().atop(tls).reversed());
    }

    public Option<InetSocketAddress> outgoingTlsConnection$default$4() {
        return None$.MODULE$;
    }

    public Iterable<Inet.SocketOption> outgoingTlsConnection$default$5() {
        return Nil$.MODULE$;
    }

    public Duration outgoingTlsConnection$default$6() {
        return Duration$.MODULE$.Inf();
    }

    public Duration outgoingTlsConnection$default$7() {
        return Duration$.MODULE$.Inf();
    }

    public Flow<ByteString, ByteString, Future<OutgoingConnection>> outgoingConnectionWithTls(InetSocketAddress remoteAddress, Function0<SSLEngine> createSSLEngine) {
        return this.outgoingConnectionWithTls(remoteAddress, createSSLEngine, None$.MODULE$, Nil$.MODULE$, Duration$.MODULE$.Inf(), Duration$.MODULE$.Inf(), (Function1<SSLSession, Try<BoxedUnit>>)(Function1<SSLSession, Success> & Serializable)x$1 -> new Success<BoxedUnit>(BoxedUnit.UNIT), IgnoreComplete$.MODULE$);
    }

    public Flow<ByteString, ByteString, Future<OutgoingConnection>> outgoingConnectionWithTls(InetSocketAddress remoteAddress, Function0<SSLEngine> createSSLEngine, Option<InetSocketAddress> localAddress, Seq<Inet.SocketOption> options, Duration connectTimeout, Duration idleTimeout, Function1<SSLSession, Try<BoxedUnit>> verifySession, TLSClosing closing2) {
        Flow<ByteString, ByteString, Future<OutgoingConnection>> connection = this.outgoingConnection(remoteAddress, localAddress, options, true, connectTimeout, idleTimeout);
        BidiFlow<TLSProtocol.SslTlsOutbound, ByteString, ByteString, TLSProtocol.SslTlsInbound, NotUsed> tls = TLS$.MODULE$.apply(createSSLEngine, verifySession, closing2);
        return connection.join(Tcp$.MODULE$.akka$stream$scaladsl$Tcp$$tlsWrapping().atop(tls).reversed());
    }

    public Source<IncomingConnection, Future<ServerBinding>> bindTls(String string, int port, SSLContext sslContext, TLSProtocol.NegotiateNewSession negotiateNewSession, int backlog, Iterable<Inet.SocketOption> options, Duration idleTimeout) {
        BidiFlow tls = Tcp$.MODULE$.akka$stream$scaladsl$Tcp$$tlsWrapping().atop(TLS$.MODULE$.apply(sslContext, negotiateNewSession, TLSRole$.MODULE$.server())).reversed();
        return (Source)this.bind(string, port, backlog, options, false, idleTimeout).map((Function1<IncomingConnection, IncomingConnection> & Serializable)incomingConnection -> {
            Flow<ByteString, ByteString, NotUsed> x$1 = incomingConnection.flow().join(tls);
            InetSocketAddress x$2 = incomingConnection.copy$default$1();
            InetSocketAddress x$3 = incomingConnection.copy$default$2();
            return incomingConnection.copy(x$2, x$3, x$1);
        });
    }

    public int bindTls$default$5() {
        return Tcp$.MODULE$.defaultBacklog();
    }

    public Iterable<Inet.SocketOption> bindTls$default$6() {
        return Nil$.MODULE$;
    }

    public Duration bindTls$default$7() {
        return Duration$.MODULE$.Inf();
    }

    public Source<IncomingConnection, Future<ServerBinding>> bindWithTls(String string, int port, Function0<SSLEngine> createSSLEngine) {
        return this.bindWithTls(string, port, createSSLEngine, Tcp$.MODULE$.defaultBacklog(), Nil$.MODULE$, Duration$.MODULE$.Inf(), (Function1<SSLSession, Try<BoxedUnit>>)(Function1<SSLSession, Success> & Serializable)x$2 -> new Success<BoxedUnit>(BoxedUnit.UNIT), IgnoreComplete$.MODULE$);
    }

    public Source<IncomingConnection, Future<ServerBinding>> bindWithTls(String string, int port, Function0<SSLEngine> createSSLEngine, int backlog, Seq<Inet.SocketOption> options, Duration idleTimeout, Function1<SSLSession, Try<BoxedUnit>> verifySession, TLSClosing closing2) {
        BidiFlow tls = Tcp$.MODULE$.akka$stream$scaladsl$Tcp$$tlsWrapping().atop(TLS$.MODULE$.apply(createSSLEngine, verifySession, closing2)).reversed();
        return (Source)this.bind(string, port, backlog, options, true, idleTimeout).map((Function1<IncomingConnection, IncomingConnection> & Serializable)incomingConnection -> {
            Flow<ByteString, ByteString, NotUsed> x$1 = incomingConnection.flow().join(tls);
            InetSocketAddress x$2 = incomingConnection.copy$default$1();
            InetSocketAddress x$3 = incomingConnection.copy$default$2();
            return incomingConnection.copy(x$2, x$3, x$1);
        });
    }

    public Future<ServerBinding> bindAndHandleWithTls(Flow<ByteString, ByteString, ?> handler, String string, int port, Function0<SSLEngine> createSSLEngine, Materializer m4) {
        return this.bindAndHandleWithTls(handler, string, port, createSSLEngine, Tcp$.MODULE$.defaultBacklog(), Nil$.MODULE$, Duration$.MODULE$.Inf(), (Function1<SSLSession, Try<BoxedUnit>>)(Function1<SSLSession, Success> & Serializable)x$3 -> new Success<BoxedUnit>(BoxedUnit.UNIT), IgnoreComplete$.MODULE$, m4);
    }

    public Future<ServerBinding> bindAndHandleWithTls(Flow<ByteString, ByteString, ?> handler, String string, int port, Function0<SSLEngine> createSSLEngine, int backlog, Seq<Inet.SocketOption> options, Duration idleTimeout, Function1<SSLSession, Try<BoxedUnit>> verifySession, TLSClosing closing2, Materializer m4) {
        return (Future)((RunnableGraph)this.bindWithTls(string, port, createSSLEngine, backlog, options, idleTimeout, verifySession, closing2).to((Graph)Sink$.MODULE$.foreach((Function1<IncomingConnection, Object> & Serializable)conn -> {
            conn.handleWith(handler, m4);
            return BoxedUnit.UNIT;
        }))).run(m4);
    }

    public Future<ServerBinding> bindAndHandleTls(Flow<ByteString, ByteString, ?> handler, String string, int port, SSLContext sslContext, TLSProtocol.NegotiateNewSession negotiateNewSession, int backlog, Iterable<Inet.SocketOption> options, Duration idleTimeout, Materializer m4) {
        return (Future)((RunnableGraph)this.bindTls(string, port, sslContext, negotiateNewSession, backlog, options, idleTimeout).to((Graph)Sink$.MODULE$.foreach((Function1<IncomingConnection, Object> & Serializable)conn -> {
            conn.handleWith(handler, m4);
            return BoxedUnit.UNIT;
        }))).run(m4);
    }

    public int bindAndHandleTls$default$6() {
        return Tcp$.MODULE$.defaultBacklog();
    }

    public Iterable<Inet.SocketOption> bindAndHandleTls$default$7() {
        return Nil$.MODULE$;
    }

    public Duration bindAndHandleTls$default$8() {
        return Duration$.MODULE$.Inf();
    }

    public static final /* synthetic */ void $anonfun$bindAndHandle$1(Flow handler$1, Materializer m$1, IncomingConnection conn) {
        conn.flow().join(handler$1).run(m$1);
    }

    public Tcp(ExtendedActorSystem system) {
        this.system = system;
        this.bindShutdownTimeout = JavaDurationConverters$JavaDurationOps$.MODULE$.asScala$extension(JavaDurationConverters$.MODULE$.JavaDurationOps(system.settings().config().getDuration("akka.stream.materializer.subscription-timeout.timeout")));
    }

    public static final class IncomingConnection
    implements Product,
    Serializable {
        private final InetSocketAddress localAddress;
        private final InetSocketAddress remoteAddress;
        private final Flow<ByteString, ByteString, NotUsed> flow;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public InetSocketAddress localAddress() {
            return this.localAddress;
        }

        public InetSocketAddress remoteAddress() {
            return this.remoteAddress;
        }

        public Flow<ByteString, ByteString, NotUsed> flow() {
            return this.flow;
        }

        public <Mat> Mat handleWith(Flow<ByteString, ByteString, Mat> handler, Materializer materializer) {
            return (Mat)this.flow().joinMat(handler, Keep$.MODULE$.right()).run(materializer);
        }

        public IncomingConnection copy(InetSocketAddress localAddress, InetSocketAddress remoteAddress, Flow<ByteString, ByteString, NotUsed> flow) {
            return new IncomingConnection(localAddress, remoteAddress, flow);
        }

        public InetSocketAddress copy$default$1() {
            return this.localAddress();
        }

        public InetSocketAddress copy$default$2() {
            return this.remoteAddress();
        }

        public Flow<ByteString, ByteString, NotUsed> copy$default$3() {
            return this.flow();
        }

        @Override
        public String productPrefix() {
            return "IncomingConnection";
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
                    return this.localAddress();
                }
                case 1: {
                    return this.remoteAddress();
                }
                case 2: {
                    return this.flow();
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
            return x$1 instanceof IncomingConnection;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "localAddress";
                }
                case 1: {
                    return "remoteAddress";
                }
                case 2: {
                    return "flow";
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
            if (!(object instanceof IncomingConnection)) return false;
            boolean bl = true;
            if (!bl) return false;
            IncomingConnection incomingConnection = (IncomingConnection)x$1;
            InetSocketAddress inetSocketAddress = this.localAddress();
            InetSocketAddress inetSocketAddress2 = incomingConnection.localAddress();
            if (inetSocketAddress == null) {
                if (inetSocketAddress2 != null) {
                    return false;
                }
            } else if (!((Object)inetSocketAddress).equals(inetSocketAddress2)) return false;
            InetSocketAddress inetSocketAddress3 = this.remoteAddress();
            InetSocketAddress inetSocketAddress4 = incomingConnection.remoteAddress();
            if (inetSocketAddress3 == null) {
                if (inetSocketAddress4 != null) {
                    return false;
                }
            } else if (!((Object)inetSocketAddress3).equals(inetSocketAddress4)) return false;
            Flow<ByteString, ByteString, NotUsed> flow = this.flow();
            Flow<ByteString, ByteString, NotUsed> flow2 = incomingConnection.flow();
            if (flow == null) {
                if (flow2 == null) return true;
                return false;
            } else {
                if (!flow.equals(flow2)) return false;
                return true;
            }
        }

        public IncomingConnection(InetSocketAddress localAddress, InetSocketAddress remoteAddress, Flow<ByteString, ByteString, NotUsed> flow) {
            this.localAddress = localAddress;
            this.remoteAddress = remoteAddress;
            this.flow = flow;
            Product.$init$(this);
        }
    }

    public static final class OutgoingConnection
    implements Product,
    Serializable {
        private final InetSocketAddress remoteAddress;
        private final InetSocketAddress localAddress;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public InetSocketAddress remoteAddress() {
            return this.remoteAddress;
        }

        public InetSocketAddress localAddress() {
            return this.localAddress;
        }

        public OutgoingConnection copy(InetSocketAddress remoteAddress, InetSocketAddress localAddress) {
            return new OutgoingConnection(remoteAddress, localAddress);
        }

        public InetSocketAddress copy$default$1() {
            return this.remoteAddress();
        }

        public InetSocketAddress copy$default$2() {
            return this.localAddress();
        }

        @Override
        public String productPrefix() {
            return "OutgoingConnection";
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
                    return this.remoteAddress();
                }
                case 1: {
                    return this.localAddress();
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
            return x$1 instanceof OutgoingConnection;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "remoteAddress";
                }
                case 1: {
                    return "localAddress";
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
            if (!(object instanceof OutgoingConnection)) return false;
            boolean bl = true;
            if (!bl) return false;
            OutgoingConnection outgoingConnection = (OutgoingConnection)x$1;
            InetSocketAddress inetSocketAddress = this.remoteAddress();
            InetSocketAddress inetSocketAddress2 = outgoingConnection.remoteAddress();
            if (inetSocketAddress == null) {
                if (inetSocketAddress2 != null) {
                    return false;
                }
            } else if (!((Object)inetSocketAddress).equals(inetSocketAddress2)) return false;
            InetSocketAddress inetSocketAddress3 = this.localAddress();
            InetSocketAddress inetSocketAddress4 = outgoingConnection.localAddress();
            if (inetSocketAddress3 == null) {
                if (inetSocketAddress4 == null) return true;
                return false;
            } else {
                if (!((Object)inetSocketAddress3).equals(inetSocketAddress4)) return false;
                return true;
            }
        }

        public OutgoingConnection(InetSocketAddress remoteAddress, InetSocketAddress localAddress) {
            this.remoteAddress = remoteAddress;
            this.localAddress = localAddress;
            Product.$init$(this);
        }
    }

    public static final class ServerBinding
    implements Product,
    Serializable {
        private final InetSocketAddress localAddress;
        private final Function0<Future<BoxedUnit>> unbindAction;
        private final Future<Done> whenUnbound;

        @Override
        public Iterator<String> productElementNames() {
            return Product.productElementNames$(this);
        }

        public InetSocketAddress localAddress() {
            return this.localAddress;
        }

        private Function0<Future<BoxedUnit>> unbindAction() {
            return this.unbindAction;
        }

        public Future<Done> whenUnbound() {
            return this.whenUnbound;
        }

        public Future<BoxedUnit> unbind() {
            return this.unbindAction().apply();
        }

        public ServerBinding copy(InetSocketAddress localAddress, Function0<Future<BoxedUnit>> unbindAction, Future<Done> whenUnbound) {
            return new ServerBinding(localAddress, unbindAction, whenUnbound);
        }

        public InetSocketAddress copy$default$1() {
            return this.localAddress();
        }

        @Override
        public String productPrefix() {
            return "ServerBinding";
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
                    return this.localAddress();
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
            return x$1 instanceof ServerBinding;
        }

        @Override
        public String productElementName(int x$1) {
            int n = x$1;
            switch (n) {
                case 0: {
                    return "localAddress";
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
            if (!(object instanceof ServerBinding)) return false;
            boolean bl = true;
            if (!bl) return false;
            ServerBinding serverBinding = (ServerBinding)x$1;
            InetSocketAddress inetSocketAddress = this.localAddress();
            InetSocketAddress inetSocketAddress2 = serverBinding.localAddress();
            if (inetSocketAddress != null) {
                if (!((Object)inetSocketAddress).equals(inetSocketAddress2)) return false;
                return true;
            }
            if (inetSocketAddress2 == null) return true;
            return false;
        }

        @InternalApi
        public ServerBinding(InetSocketAddress localAddress, Function0<Future<BoxedUnit>> unbindAction, Future<Done> whenUnbound) {
            this.localAddress = localAddress;
            this.unbindAction = unbindAction;
            this.whenUnbound = whenUnbound;
            Product.$init$(this);
        }
    }
}

