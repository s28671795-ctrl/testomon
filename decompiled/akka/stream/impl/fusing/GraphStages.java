/*
 * Decompiled with CFR 0.152.
 */
package akka.stream.impl.fusing;

import akka.Done;
import akka.actor.Cancellable;
import akka.annotation.InternalApi;
import akka.dispatch.ExecutionContexts$;
import akka.event.Logging$;
import akka.stream.AbruptStageTerminationException;
import akka.stream.ActorAttributes;
import akka.stream.Attributes;
import akka.stream.FlowMonitor;
import akka.stream.FlowMonitorState;
import akka.stream.FlowMonitorState$Finished$;
import akka.stream.FlowMonitorState$Initialized$;
import akka.stream.FlowShape;
import akka.stream.FlowShape$;
import akka.stream.Graph;
import akka.stream.Inlet;
import akka.stream.Inlet$;
import akka.stream.Outlet;
import akka.stream.Outlet$;
import akka.stream.SourceShape;
import akka.stream.StreamDetachedException;
import akka.stream.Supervision;
import akka.stream.Supervision$Restart$;
import akka.stream.Supervision$Resume$;
import akka.stream.Supervision$Stop$;
import akka.stream.impl.ReactiveStreamsCompliance$;
import akka.stream.impl.Stages$DefaultAttributes$;
import akka.stream.impl.fusing.GraphStages$;
import akka.stream.impl.fusing.GraphStages$FutureFlattenSource$;
import akka.stream.impl.fusing.GraphStages$FutureSource$;
import akka.stream.impl.fusing.GraphStages$IterableSource$;
import akka.stream.impl.fusing.GraphStages$TickSource$;
import akka.stream.scaladsl.Keep$;
import akka.stream.scaladsl.Source$;
import akka.stream.stage.AsyncCallback;
import akka.stream.stage.GraphStage;
import akka.stream.stage.GraphStageLogic;
import akka.stream.stage.GraphStageWithMaterializedValue;
import akka.stream.stage.InHandler;
import akka.stream.stage.OutHandler;
import java.io.Serializable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.SerializedLambda;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import scala.Function1;
import scala.MatchError;
import scala.None$;
import scala.Option;
import scala.Some;
import scala.Tuple2;
import scala.collection.Iterator;
import scala.collection.immutable.Iterable;
import scala.concurrent.Future;
import scala.concurrent.Promise;
import scala.concurrent.Promise$;
import scala.concurrent.duration.FiniteDuration;
import scala.reflect.ClassTag$;
import scala.reflect.ScalaSignature;
import scala.runtime.BoxedUnit;
import scala.runtime.BoxesRunTime;
import scala.runtime.LambdaDeserialize;
import scala.util.Failure;
import scala.util.Success;
import scala.util.Try;
import scala.util.control.NonFatal$;

@ScalaSignature(bytes="\u0006\u0005\u0011-rACA\u0002\u0003\u000bA\t!!\u0005\u0002\u0016\u0019Q\u0011\u0011DA\u0003\u0011\u0003\t\t\"a\u0007\t\u000f\u0005%\u0012\u0001\"\u0001\u0002.\u0019I\u0011qF\u0001\u0002\u0002\u0005E\u0011\u0011\u0007\u0005\b\u0003S\u0019A\u0011AA0\u0011%\t)g\u0001b\u0001\n\u0003\t9\u0007\u0003\u0005\u0002p\r\u0001\u000b\u0011BA5\u0011%\t\th\u0001b\u0001\n\u0003\t\u0019\b\u0003\u0005\u0002|\r\u0001\u000b\u0011BA;\u0011%\tih\u0001b\u0001\n\u0003\ny\b\u0003\u0005\u0002\u0002\u000e\u0001\u000b\u0011BA!\u000f\u001d\t\t*\u0001E\u0005\u0003'3q!!&\u0002\u0011\u0013\t9\nC\u0004\u0002*1!\t!a'\t\u000f\u0005uE\u0002\"\u0011\u0002 \"9\u0011q\u0015\u0007\u0005B\u0005%\u0006bBA[\u0019\u0011\u0005\u0013q\u0017\u0005\b\u0003\u0013\fA\u0011AAf\u000f%\t).\u0001E\u0001\u0003#\t9NB\u0005\u0002Z\u0006A\t!!\u0005\u0002\\\"9\u0011\u0011F\n\u0005\u0002\u0005u\u0007bBAO'\u0011\u0005\u0013q\u0014\u0005\b\u0003O\u001bB\u0011IAp\u0011\u001d\t)l\u0005C!\u0003oCq!a:\u0002\t\u0003\tIoB\u0004\u0002v\u0006AI!a>\u0007\u000f\u0005e\u0018\u0001#\u0003\u0002|\"9\u0011\u0011\u0006\u000e\u0005\u0002\te\u0001\"CA35\t\u0007I\u0011\u0001B\u000e\u0011!\tyG\u0007Q\u0001\n\tu\u0001\"CA95\t\u0007I\u0011\u0001B\u0010\u0011!\tYH\u0007Q\u0001\n\t\u0005\u0002\"CA?5\t\u0007I\u0011\tB\u0012\u0011!\t\tI\u0007Q\u0001\n\t\r\u0001bBAO5\u0011\u0005\u0013q\u0014\u0005\b\u0005KQB\u0011\tB\u0014\u0011\u001d\t)L\u0007C!\u0003oCqA!\r\u0002\t\u0003\u0011\u0019D\u0002\u0004\u0003@\u0005!!\u0011\t\u0005\b\u0003S1C\u0011\u0001B1\u0011\u001d\u0011)G\nC!\u0005O2aA!\"\u0002\t\t\u001d\u0005bBA\u0015S\u0011\u0005!Q\u0013\u0005\n\u0003KJ#\u0019!C\u0001\u00053C\u0001\"a\u001c*A\u0003%!1\u0014\u0005\n\u0003cJ#\u0019!C\u0001\u0005;C\u0001\"a\u001f*A\u0003%!q\u0014\u0005\n\u0003{J#\u0019!C\u0001\u0005CC\u0001\"!!*A\u0003%!Q\u0012\u0005\b\u0005KIC\u0011\tBR\u0011\u001d\t),\u000bC!\u0003oCqA!+\u0002\t\u0003\u0011YK\u0002\u0004\u0003:\u0006\u0011!1\u0018\u0005\u000b\u0005/$$Q1A\u0005\u0002\te\u0007B\u0003Bti\t\u0005\t\u0015!\u0003\u0003\\\"Q!\u0011\u001e\u001b\u0003\u0006\u0004%\tA!7\t\u0015\t-HG!A!\u0002\u0013\u0011Y\u000e\u0003\u0006\u0003nR\u0012)\u0019!C\u0001\u0005_D!B!=5\u0005\u0003\u0005\u000b\u0011\u0002Bd\u0011\u001d\tI\u0003\u000eC\u0001\u0005gD\u0011\"! 5\u0005\u0004%\tE!@\t\u0011\u0005\u0005E\u0007)A\u0005\u0005\u0003D\u0011\"!\u001d5\u0005\u0004%\tAa@\t\u0011\u0005mD\u0007)A\u0005\u0007\u0003Aq!!(5\t\u0003\ny\nC\u0004\u0003&Q\"\tea\u0001\t\u000f\u0005UF\u0007\"\u0011\u0004\n\u001911\u0011D\u0001\u0003\u00077A!ba\nD\u0005\u000b\u0007I\u0011AB\u0015\u0011)\u0019Yc\u0011B\u0001B\u0003%11\u0005\u0005\b\u0003S\u0019E\u0011AB\u0017\u0011\u001d\tij\u0011C!\u0003?C\u0011\"!\u001dD\u0005\u0004%\taa\r\t\u0011\u0005m4\t)A\u0005\u0007kA\u0011\"! D\u0005\u0004%\taa\u000e\t\u0011\u0005\u00055\t)A\u0005\u0007CAq!a*D\t\u0003\u0019I\u0004C\u0004\u00026\u000e#\te!\u0003\u0007\r\r-\u0013AAB'\u0011)\u0019IF\u0014BC\u0002\u0013\u000511\f\u0005\u000b\u0007[r%\u0011!Q\u0001\n\ru\u0003bBA\u0015\u001d\u0012\u00051q\u000e\u0005\b\u0003;sE\u0011KAP\u0011%\t\tH\u0014b\u0001\n\u0013\u0019)\b\u0003\u0005\u0002|9\u0003\u000b\u0011BB<\u0011%\tiH\u0014b\u0001\n\u0003\u001aI\b\u0003\u0005\u0002\u0002:\u0003\u000b\u0011BB*\u0011\u001d\t9K\u0014C!\u0007wBq!!.O\t\u0003\u001aIA\u0002\u0004\u0004\u0000\u0005\u00111\u0011\u0011\u0005\u000b\u0007+K&\u0011!Q\u0001\n\r]\u0005bBA\u00153\u0012\u00051q\u0014\u0005\n\u0003cJ&\u0019!C\u0001\u0007KC\u0001\"a\u001fZA\u0003%1q\u0015\u0005\n\u0003{J&\u0019!C!\u0007SC\u0001\"!!ZA\u0003%1q\u0011\u0005\b\u0003;KF\u0011IAP\u0011\u001d\u0011)#\u0017C!\u0007WCq!!.Z\t\u0003\u001aIA\u0002\u0004\u00042\u0006\u001111\u0017\u0005\u000b\u0007\u007f\u001b'Q1A\u0005\u0002\r\u0005\u0007BCBcG\n\u0005\t\u0015!\u0003\u0004D\"9\u0011\u0011F2\u0005\u0002\r\u001d\u0007\"CA?G\n\u0007I\u0011ABg\u0011!\t\ti\u0019Q\u0001\n\re\u0006\"CA9G\n\u0007I\u0011ABh\u0011!\tYh\u0019Q\u0001\n\rE\u0007bBAOG\u0012\u0005\u0013q\u0014\u0005\b\u0003O\u001bG\u0011IBj\u0011\u001d\t)l\u0019C!\u0007\u00139\u0011ba7\u0002\u0011\u0003\t\tb!8\u0007\u0013\r}\u0017\u0001#\u0001\u0002\u0012\r\u0005\bbBA\u0015_\u0012\u000511\u001e\u0005\n\u0003Kz'\u0019!C\u0001\u00057A\u0001\"a\u001cpA\u0003%!Q\u0004\u0005\n\u0003{z'\u0019!C\u0001\u0007[D\u0001\"!!pA\u0003%1Q\u001d\u0005\b\u0003;{G\u0011IAP\u0011\u001d\u0011)c\u001cC!\u0007_<\u0011ba>\u0002\u0011\u0003\t\tb!?\u0007\u0013\rm\u0018\u0001#\u0001\u0002\u0012\ru\bbBA\u0015q\u0012\u00051q \u0005\n\u0003KB(\u0019!C\u0005\u00057A\u0001\"a\u001cyA\u0003%!Q\u0004\u0005\n\u0003{B(\u0019!C\u0001\u0007[D\u0001\"!!yA\u0003%1Q\u001d\u0005\b\u0003;CH\u0011IAP\u0011\u001d\u0011)\u0003\u001fC!\t\u0003A\u0011\u0002\"\u0003\u0002\t\u0003\ti\u0001b\u0003\u0002\u0017\u001d\u0013\u0018\r\u001d5Ti\u0006<Wm\u001d\u0006\u0005\u0003\u000f\tI!\u0001\u0004gkNLgn\u001a\u0006\u0005\u0003\u0017\ti!\u0001\u0003j[Bd'\u0002BA\b\u0003#\taa\u001d;sK\u0006l'BAA\n\u0003\u0011\t7n[1\u0011\u0007\u0005]\u0011!\u0004\u0002\u0002\u0006\tYqI]1qQN#\u0018mZ3t'\r\t\u0011Q\u0004\t\u0005\u0003?\t)#\u0004\u0002\u0002\")\u0011\u00111E\u0001\u0006g\u000e\fG.Y\u0005\u0005\u0003O\t\tC\u0001\u0004B]f\u0014VMZ\u0001\u0007y%t\u0017\u000e\u001e \u0004\u0001Q\u0011\u0011Q\u0003\u0002\u0017'&l\u0007\u000f\\3MS:,\u0017M]$sCBD7\u000b^1hKV!\u00111GA''\r\u0019\u0011Q\u0007\t\u0007\u0003o\ti$!\u0011\u000e\u0005\u0005e\"\u0002BA\u001e\u0003\u001b\tQa\u001d;bO\u0016LA!a\u0010\u0002:\tQqI]1qQN#\u0018mZ3\u0011\u0011\u0005\r\u0013QIA%\u0003\u0013j!!!\u0004\n\t\u0005\u001d\u0013Q\u0002\u0002\n\r2|wo\u00155ba\u0016\u0004B!a\u0013\u0002N1\u0001AaBA(\u0007\t\u0007\u0011\u0011\u000b\u0002\u0002)F!\u00111KA-!\u0011\ty\"!\u0016\n\t\u0005]\u0013\u0011\u0005\u0002\b\u001d>$\b.\u001b8h!\u0011\ty\"a\u0017\n\t\u0005u\u0013\u0011\u0005\u0002\u0004\u0003:LHCAA1!\u0015\t\u0019gAA%\u001b\u0005\t\u0011AA5o+\t\tI\u0007\u0005\u0004\u0002D\u0005-\u0014\u0011J\u0005\u0005\u0003[\niAA\u0003J]2,G/A\u0002j]\u0002\n1a\\;u+\t\t)\b\u0005\u0004\u0002D\u0005]\u0014\u0011J\u0005\u0005\u0003s\niA\u0001\u0004PkRdW\r^\u0001\u0005_V$\b%A\u0003tQ\u0006\u0004X-\u0006\u0002\u0002B\u000511\u000f[1qK\u0002B3aAAC!\u0011\t9)!$\u000e\u0005\u0005%%\u0002BAF\u0003#\t!\"\u00198o_R\fG/[8o\u0013\u0011\ty)!#\u0003\u0017%sG/\u001a:oC2\f\u0005/[\u0001\t\u0013\u0012,g\u000e^5usB\u0019\u00111\r\u0007\u0003\u0011%#WM\u001c;jif\u001c2\u0001DAM!\u0015\t\u0019gAA-)\t\t\u0019*A\tj]&$\u0018.\u00197BiR\u0014\u0018NY;uKN,\"!!)\u0011\t\u0005\r\u00131U\u0005\u0005\u0003K\u000biA\u0001\u0006BiR\u0014\u0018NY;uKN\f1b\u0019:fCR,Gj\\4jGR!\u00111VAY!\u0011\t9$!,\n\t\u0005=\u0016\u0011\b\u0002\u0010\u000fJ\f\u0007\u000f[*uC\u001e,Gj\\4jG\"9\u00111W\bA\u0002\u0005\u0005\u0016aE5oQ\u0016\u0014\u0018\u000e^3e\u0003R$(/\u001b2vi\u0016\u001c\u0018\u0001\u0003;p'R\u0014\u0018N\\4\u0015\u0005\u0005e\u0006\u0003BA^\u0003\u000bl!!!0\u000b\t\u0005}\u0016\u0011Y\u0001\u0005Y\u0006twM\u0003\u0002\u0002D\u0006!!.\u0019<b\u0013\u0011\t9-!0\u0003\rM#(/\u001b8h\u0003!IG-\u001a8uSRLX\u0003BAg\u0003',\"!a4\u0011\u000b\u0005\r4!!5\u0011\t\u0005-\u00131\u001b\u0003\b\u0003\u001f\n\"\u0019AA)\u0003!!U\r^1dQ\u0016\u0014\bcAA2'\tAA)\u001a;bG\",'oE\u0002\u0014\u00033#\"!a6\u0015\t\u0005-\u0016\u0011\u001d\u0005\b\u0003g3\u0002\u0019AAQQ\r\u0019\u0012Q\u0011\u0015\u0004%\u0005\u0015\u0015\u0001\u00033fi\u0006\u001c\u0007.\u001a:\u0016\t\u0005-\u00181_\u000b\u0003\u0003[\u0004b!a\u000e\u0002>\u0005=\b\u0003CA\"\u0003\u000b\n\t0!=\u0011\t\u0005-\u00131\u001f\u0003\b\u0003\u001fB\"\u0019AA)\u0003I!VM]7j]\u0006$\u0018n\u001c8XCR\u001c\u0007.\u001a:\u0011\u0007\u0005\r$D\u0001\nUKJl\u0017N\\1uS>tw+\u0019;dQ\u0016\u00148c\u0001\u000e\u0002~BA\u0011qGA\u0000\u0005\u0007\u0011)!\u0003\u0003\u0003\u0002\u0005e\"aH$sCBD7\u000b^1hK^KG\u000f['bi\u0016\u0014\u0018.\u00197ju\u0016$g+\u00197vKBA\u00111IA#\u00033\nI\u0006\u0005\u0004\u0003\b\t5!\u0011C\u0007\u0003\u0005\u0013QAAa\u0003\u0002\"\u0005Q1m\u001c8dkJ\u0014XM\u001c;\n\t\t=!\u0011\u0002\u0002\u0007\rV$XO]3\u0011\t\tM!QC\u0007\u0003\u0003#IAAa\u0006\u0002\u0012\t!Ai\u001c8f)\t\t90\u0006\u0002\u0003\u001eA1\u00111IA6\u00033*\"A!\t\u0011\r\u0005\r\u0013qOA-+\t\u0011\u0019!A\u0010de\u0016\fG/\u001a'pO&\u001c\u0017I\u001c3NCR,'/[1mSj,GMV1mk\u0016$BA!\u000b\u00030AA\u0011q\u0004B\u0016\u0003W\u0013)!\u0003\u0003\u0003.\u0005\u0005\"A\u0002+va2,'\u0007C\u0004\u00024\u000e\u0002\r!!)\u0002%Q,'/\\5oCRLwN\\,bi\u000eDWM]\u000b\u0005\u0005k\u0011i$\u0006\u0002\u00038AA\u0011qGA\u0000\u0005s\u0011)\u0001\u0005\u0005\u0002D\u0005\u0015#1\bB\u001e!\u0011\tYE!\u0010\u0005\u000f\u0005=SE1\u0001\u0002R\tya\t\\8x\u001b>t\u0017\u000e^8s\u00136\u0004H.\u0006\u0003\u0003D\t}3#\u0002\u0014\u0003F\t]\u0003C\u0002B$\u0005'\nI&\u0004\u0002\u0003J)!!1\nB'\u0003\u0019\tGo\\7jG*!!1\u0002B(\u0015\u0011\u0011\t&!1\u0002\tU$\u0018\u000e\\\u0005\u0005\u0005+\u0012IEA\bBi>l\u0017n\u0019*fM\u0016\u0014XM\\2f!\u0019\t\u0019E!\u0017\u0003^%!!1LA\u0007\u0005-1En\\<N_:LGo\u001c:\u0011\t\u0005-#q\f\u0003\b\u0003\u001f2#\u0019AA))\t\u0011\u0019\u0007E\u0003\u0002d\u0019\u0012i&A\u0003ti\u0006$X-\u0006\u0002\u0003jA1!1\u000eB@\u0005;rAA!\u001c\u0003|9!!q\u000eB=\u001d\u0011\u0011\tHa\u001e\u000e\u0005\tM$\u0002\u0002B;\u0003W\ta\u0001\u0010:p_Rt\u0014BAA\n\u0013\u0011\ty!!\u0005\n\t\tu\u0014QB\u0001\u0011\r2|w/T8oSR|'o\u0015;bi\u0016LAA!!\u0003\u0004\nY1\u000b\u001e:fC6\u001cF/\u0019;f\u0015\u0011\u0011i(!\u0004\u0003\u00175{g.\u001b;pe\u001acwn^\u000b\u0005\u0005\u0013\u0013\tjE\u0002*\u0005\u0017\u0003\u0002\"a\u000e\u0002\u0000\n5%1\u0013\t\t\u0003\u0007\n)Ea$\u0003\u0010B!\u00111\nBI\t\u001d\ty%\u000bb\u0001\u0003#\u0002b!a\u0011\u0003Z\t=EC\u0001BL!\u0015\t\u0019'\u000bBH+\t\u0011Y\n\u0005\u0004\u0002D\u0005-$qR\u000b\u0003\u0005?\u0003b!a\u0011\u0002x\t=UC\u0001BG)\u0011\u0011)Ka*\u0011\u0011\u0005}!1FAV\u0005'Cq!a-2\u0001\u0004\t\t+A\u0004n_:LGo\u001c:\u0016\t\t5&QW\u000b\u0003\u0005_\u0003\u0002\"a\u000e\u0002\u0000\nE&q\u0017\t\t\u0003\u0007\n)Ea-\u00034B!\u00111\nB[\t\u001d\tye\rb\u0001\u0003#\u0002b!a\u0011\u0003Z\tM&A\u0003+jG.\u001cv.\u001e:dKV!!Q\u0018Be'\r!$q\u0018\t\t\u0003o\tyP!1\u0003LB1\u00111\tBb\u0005\u000fLAA!2\u0002\u000e\tY1k\\;sG\u0016\u001c\u0006.\u00199f!\u0011\tYE!3\u0005\u000f\u0005=CG1\u0001\u0002RA!!Q\u001aBj\u001b\t\u0011yM\u0003\u0003\u0003R\u0006E\u0011!B1di>\u0014\u0018\u0002\u0002Bk\u0005\u001f\u00141bQ1oG\u0016dG.\u00192mK\u0006a\u0011N\\5uS\u0006dG)\u001a7bsV\u0011!1\u001c\t\u0005\u0005;\u0014\u0019/\u0004\u0002\u0003`*!!\u0011\u001dB\u0005\u0003!!WO]1uS>t\u0017\u0002\u0002Bs\u0005?\u0014aBR5oSR,G)\u001e:bi&|g.A\u0007j]&$\u0018.\u00197EK2\f\u0017\u0010I\u0001\tS:$XM\u001d<bY\u0006I\u0011N\u001c;feZ\fG\u000eI\u0001\u0005i&\u001c7.\u0006\u0002\u0003H\u0006)A/[2lAQA!Q\u001fB|\u0005s\u0014Y\u0010E\u0003\u0002dQ\u00129\rC\u0004\u0003Xn\u0002\rAa7\t\u000f\t%8\b1\u0001\u0003\\\"9!Q^\u001eA\u0002\t\u001dWC\u0001Ba+\t\u0019\t\u0001\u0005\u0004\u0002D\u0005]$q\u0019\u000b\u0005\u0007\u000b\u00199\u0001\u0005\u0005\u0002 \t-\u00121\u0016Bf\u0011\u001d\t\u0019,\u0011a\u0001\u0003C#\"aa\u0003\u0011\t\r51Q\u0003\b\u0005\u0007\u001f\u0019\t\u0002\u0005\u0003\u0003r\u0005\u0005\u0012\u0002BB\n\u0003C\ta\u0001\u0015:fI\u00164\u0017\u0002BAd\u0007/QAaa\u0005\u0002\"\ta1+\u001b8hY\u0016\u001cv.\u001e:dKV!1QDB\u0013'\r\u00195q\u0004\t\u0007\u0003o\tid!\t\u0011\r\u0005\r#1YB\u0012!\u0011\tYe!\n\u0005\u000f\u0005=3I1\u0001\u0002R\u0005!Q\r\\3n+\t\u0019\u0019#A\u0003fY\u0016l\u0007\u0005\u0006\u0003\u00040\rE\u0002#BA2\u0007\u000e\r\u0002bBB\u0014\r\u0002\u000711E\u000b\u0003\u0007k\u0001b!a\u0011\u0002x\r\rRCAB\u0011)\u0011\u0019Yda\u0012\u0013\r\ru\u00121VB!\r\u0019\u0019y\u0004\u0014\u0001\u0004<\taAH]3gS:,W.\u001a8u}A!\u0011qGB\"\u0013\u0011\u0019)%!\u000f\u0003\u0015=+H\u000fS1oI2,'\u000fC\u0004\u0004J1\u0003\r!!)\u0002\t\u0005$HO\u001d\u0002\u000f\u0013R,'/\u00192mKN{WO]2f+\u0011\u0019yea\u0016\u0014\u00079\u001b\t\u0006\u0005\u0004\u00028\u0005u21\u000b\t\u0007\u0003\u0007\u0012\u0019m!\u0016\u0011\t\u0005-3q\u000b\u0003\b\u0003\u001fr%\u0019AA)\u0003!)G.Z7f]R\u001cXCAB/!\u0019\u0019yf!\u001b\u0004V5\u00111\u0011\r\u0006\u0005\u0007G\u001a)'A\u0005j[6,H/\u00192mK*!1qMA\u0011\u0003)\u0019w\u000e\u001c7fGRLwN\\\u0005\u0005\u0007W\u001a\tG\u0001\u0005Ji\u0016\u0014\u0018M\u00197f\u0003%)G.Z7f]R\u001c\b\u0005\u0006\u0003\u0004r\rM\u0004#BA2\u001d\u000eU\u0003bBB-#\u0002\u00071QL\u000b\u0003\u0007o\u0002b!a\u0011\u0002x\rUSCAB*)\u0011\tYk! \t\u000f\u0005Mv\u000b1\u0001\u0002\"\n\u0019b)\u001e;ve\u00164E.\u0019;uK:\u001cv.\u001e:dKV111QBF\u0007#\u001b2!WBC!!\t9$a@\u0004\b\u000e5\u0005CBA\"\u0005\u0007\u001cI\t\u0005\u0003\u0002L\r-EaBA(3\n\u0007\u0011\u0011\u000b\t\u0007\u0005\u000f\u0011iaa$\u0011\t\u0005-3\u0011\u0013\u0003\b\u0007'K&\u0019AA)\u0005\u0005i\u0015\u0001\u00044viV\u0014XmU8ve\u000e,\u0007C\u0002B\u0004\u0005\u001b\u0019I\n\u0005\u0005\u0002D\rm5qQBH\u0013\u0011\u0019i*!\u0004\u0003\u000b\u001d\u0013\u0018\r\u001d5\u0015\t\r\u000561\u0015\t\b\u0003GJ6\u0011RBH\u0011\u001d\u0019)j\u0017a\u0001\u0007/+\"aa*\u0011\r\u0005\r\u0013qOBE+\t\u00199\t\u0006\u0003\u0004.\u000e=\u0006\u0003CA\u0010\u0005W\tYk!$\t\u000f\r%\u0013\r1\u0001\u0002\"\naa)\u001e;ve\u0016\u001cv.\u001e:dKV!1QWB_'\r\u00197q\u0017\t\u0007\u0003o\tid!/\u0011\r\u0005\r#1YB^!\u0011\tYe!0\u0005\u000f\u0005=3M1\u0001\u0002R\u00051a-\u001e;ve\u0016,\"aa1\u0011\r\t\u001d!QBB^\u0003\u001d1W\u000f^;sK\u0002\"Ba!3\u0004LB)\u00111M2\u0004<\"91q\u00184A\u0002\r\rWCAB]+\t\u0019\t\u000e\u0005\u0004\u0002D\u0005]41\u0018\u000b\u0005\u0007+\u001cIN\u0005\u0004\u0004X\u0006-6\u0011\t\u0004\u0007\u0007\u007fa\u0007a!6\t\u000f\r%C\u000e1\u0001\u0002\"\u0006Q\u0011j\u001a8pe\u0016\u001c\u0016N\\6\u0011\u0007\u0005\rtN\u0001\u0006JO:|'/Z*j].\u001c2a\\Br!!\t9$a@\u0004f\n\u0015\u0001CBA\"\u0007O\fI&\u0003\u0003\u0004j\u00065!!C*j].\u001c\u0006.\u00199f)\t\u0019i.\u0006\u0002\u0004fR!!\u0011FBy\u0011\u001d\t\u0019L\u001ea\u0001\u0003CC3a\\ACQ\rq\u0017QQ\u0001\n\u001d\u00164XM]*j].\u00042!a\u0019y\u0005%qUM^3s'&t7nE\u0002y\u0007G$\"a!?\u0015\t\t%B1\u0001\u0005\b\u0003g{\b\u0019AAQQ\rA\u0018Q\u0011\u0015\u0004o\u0006\u0015\u0015AE<ji\"$U\r^1dQ\u0016$\u0017J\u001c9viN,B\u0001\"\u0004\u0005\u001aQ!Aq\u0002C\u0011!!\t\u0019ea'\u0005\u0012\u0011m\u0001\u0003CA\"\t'!9\u0002b\u0006\n\t\u0011U\u0011Q\u0002\u0002\u0012+:Lgm\u001c:n\r\u0006t\u0017J\\*iCB,\u0007\u0003BA&\t3!\u0001\"a\u0014\u0002\u0002\t\u0007\u0011\u0011\u000b\t\u0005\u0005'!i\"\u0003\u0003\u0005 \u0005E!a\u0002(piV\u001bX\r\u001a\u0005\t\u0003w\t\t\u00011\u0001\u0005$A1\u0011qGA\u001f\t#AC!!\u0001\u0002\u0006\"\u001a\u0011!!\")\u0007\u0001\t)\t")
@InternalApi
public final class GraphStages {
    public static <T> GraphStageWithMaterializedValue<FlowShape<T, T>, FlowMonitor<T>> monitor() {
        return GraphStages$.MODULE$.monitor();
    }

    public static <T> GraphStageWithMaterializedValue<FlowShape<T, T>, Future<Done>> terminationWatcher() {
        return GraphStages$.MODULE$.terminationWatcher();
    }

    public static <T> GraphStage<FlowShape<T, T>> detacher() {
        return GraphStages$.MODULE$.detacher();
    }

    public static <T> SimpleLinearGraphStage<T> identity() {
        return GraphStages$.MODULE$.identity();
    }

    private static class FlowMonitorImpl<T>
    extends AtomicReference<Object>
    implements FlowMonitor<T> {
        @Override
        public FlowMonitorState.StreamState<T> state() {
            Object v = this.get();
            if (v instanceof FlowMonitorState.StreamState) {
                FlowMonitorState.StreamState streamState = (FlowMonitorState.StreamState)v;
                return streamState;
            }
            return new FlowMonitorState.Received(v);
        }

        public FlowMonitorImpl() {
            super(FlowMonitorState$Initialized$.MODULE$);
        }
    }

    public static final class FutureFlattenSource<T, M>
    extends GraphStageWithMaterializedValue<SourceShape<T>, Future<M>> {
        public final Future<Graph<SourceShape<T>, M>> akka$stream$impl$fusing$GraphStages$FutureFlattenSource$$futureSource;
        private final Outlet<T> out;
        private final SourceShape<T> shape;

        public Outlet<T> out() {
            return this.out;
        }

        @Override
        public SourceShape<T> shape() {
            return this.shape;
        }

        @Override
        public Attributes initialAttributes() {
            return Stages$DefaultAttributes$.MODULE$.futureFlattenSource();
        }

        @Override
        public Tuple2<GraphStageLogic, Future<M>> createLogicAndMaterializedValue(Attributes attr) {
            Promise materialized = Promise$.MODULE$.apply();
            InHandler logic = new InHandler(this, materialized, attr){
                private final GraphStageLogic.SubSinkInlet<T> akka$stream$impl$fusing$GraphStages$FutureFlattenSource$$anon$$sinkIn;
                private final /* synthetic */ FutureFlattenSource $outer;
                private final Attributes attr$1;
                public final Promise materialized$1;

                public void onDownstreamFinish() throws Exception {
                    OutHandler.onDownstreamFinish$(this);
                }

                public void onUpstreamFailure(Throwable ex) throws Exception {
                    InHandler.onUpstreamFailure$(this, ex);
                }

                public GraphStageLogic.SubSinkInlet<T> akka$stream$impl$fusing$GraphStages$FutureFlattenSource$$anon$$sinkIn() {
                    return this.akka$stream$impl$fusing$GraphStages$FutureFlattenSource$$anon$$sinkIn;
                }

                public void preStart() {
                    Option<Try<Graph<SourceShape<T>, M>>> option = this.$outer.akka$stream$impl$fusing$GraphStages$FutureFlattenSource$$futureSource.value();
                    if (option instanceof Some) {
                        Some some = (Some)option;
                        Try it = (Try)some.value();
                        this.onFutureSourceCompleted(it);
                        return;
                    }
                    AsyncCallback<T> asyncCallback = this.getAsyncCallback((Function1<Try, Object> & Serializable)result -> {
                        FutureFlattenSource$$anon$8.$anonfun$preStart$2(this, result);
                        return BoxedUnit.UNIT;
                    });
                    Function1<Try, Object> & Serializable cb = (Function1<Try, Object> & Serializable)t2 -> {
                        FutureFlattenSource$$anon$8.$anonfun$preStart$3(asyncCallback, t2);
                        return BoxedUnit.UNIT;
                    };
                    this.$outer.akka$stream$impl$fusing$GraphStages$FutureFlattenSource$$futureSource.onComplete(cb, ExecutionContexts$.MODULE$.parasitic());
                }

                public void onPush() {
                    this.push(this.$outer.out(), this.akka$stream$impl$fusing$GraphStages$FutureFlattenSource$$anon$$sinkIn().grab());
                }

                public void onPull() {
                    this.akka$stream$impl$fusing$GraphStages$FutureFlattenSource$$anon$$sinkIn().pull();
                }

                public void onUpstreamFinish() {
                    this.completeStage();
                }

                public void onDownstreamFinish(Throwable cause) {
                    this.akka$stream$impl$fusing$GraphStages$FutureFlattenSource$$anon$$sinkIn().cancel(cause);
                    OutHandler.onDownstreamFinish$(this, cause);
                }

                public void onFutureSourceCompleted(Try<Graph<SourceShape<T>, M>> result) {
                    result.map((Function1<Graph, Object> & Serializable)graph -> {
                        FutureFlattenSource$$anon$8.$anonfun$onFutureSourceCompleted$1(this, graph);
                        return BoxedUnit.UNIT;
                    }).recover(new Serializable(this){
                        private static final long serialVersionUID = 0L;
                        private final /* synthetic */ FutureFlattenSource$$anon$8 $outer;

                        public final <A1 extends Throwable, B1> B1 applyOrElse(A1 x1, Function1<A1, B1> function1) {
                            A1 A1 = x1;
                            this.$outer.akka$stream$impl$fusing$GraphStages$FutureFlattenSource$$anon$$sinkIn().cancel();
                            this.$outer.materialized$1.failure(A1);
                            this.$outer.failStage(A1);
                            return (B1)BoxedUnit.UNIT;
                        }

                        public final boolean isDefinedAt(Throwable x1) {
                            Throwable throwable = x1;
                            return true;
                        }
                        {
                            if ($outer == null) {
                                throw null;
                            }
                            this.$outer = $outer;
                        }
                    });
                }

                public static final /* synthetic */ void $anonfun$preStart$2(FutureFlattenSource$$anon$8 $this, Try result) {
                    $this.onFutureSourceCompleted(result);
                }

                public static final /* synthetic */ void $anonfun$preStart$3(AsyncCallback eta$0$1$1, Try t2) {
                    eta$0$1$1.invoke(t2);
                }

                public static final /* synthetic */ void $anonfun$onFutureSourceCompleted$1(FutureFlattenSource$$anon$8 $this, Graph graph) {
                    Graph runnable2 = Source$.MODULE$.fromGraph(graph).toMat($this.akka$stream$impl$fusing$GraphStages$FutureFlattenSource$$anon$$sinkIn().sink(), Keep$.MODULE$.left());
                    Mat matVal = $this.interpreter().subFusingMaterializer().materialize(runnable2, $this.attr$1);
                    $this.materialized$1.success(matVal);
                    $this.setHandler($this.$outer.out(), $this);
                    $this.akka$stream$impl$fusing$GraphStages$FutureFlattenSource$$anon$$sinkIn().setHandler($this);
                    if ($this.isAvailable($this.$outer.out())) {
                        $this.akka$stream$impl$fusing$GraphStages$FutureFlattenSource$$anon$$sinkIn().pull();
                        return;
                    }
                }
                {
                    if ($outer == null) {
                        throw null;
                    }
                    this.$outer = $outer;
                    this.attr$1 = attr$1;
                    this.materialized$1 = materialized$1;
                    super($outer.shape());
                    InHandler.$init$(this);
                    OutHandler.$init$(this);
                    this.akka$stream$impl$fusing$GraphStages$FutureFlattenSource$$anon$$sinkIn = new GraphStageLogic.SubSinkInlet<T>(this, "FutureFlattenSource.in");
                    this.setHandler($outer.out(), new OutHandler(null, materialized$1){
                        private final Promise materialized$1;

                        public void onDownstreamFinish() throws Exception {
                            OutHandler.onDownstreamFinish$(this);
                        }

                        public void onPull() {
                        }

                        public void onDownstreamFinish(Throwable cause) {
                            Serializable serializable = !this.materialized$1.isCompleted() ? BoxesRunTime.boxToBoolean(this.materialized$1.tryFailure(new StreamDetachedException("Stream cancelled before Source Future completed").initCause(cause))) : BoxedUnit.UNIT;
                            OutHandler.onDownstreamFinish$(this, cause);
                        }
                        {
                            this.materialized$1 = materialized$1;
                            OutHandler.$init$(this);
                        }
                    });
                }

                private static /* synthetic */ Object $deserializeLambda$(SerializedLambda serializedLambda) {
                    return LambdaDeserialize.bootstrap("lambdaDeserialize", new MethodHandle[]{$anonfun$onFutureSourceCompleted$1$adapted(akka.stream.impl.fusing.GraphStages$FutureFlattenSource$$anon$8 akka.stream.Graph ), $anonfun$preStart$2$adapted(akka.stream.impl.fusing.GraphStages$FutureFlattenSource$$anon$8 scala.util.Try ), $anonfun$preStart$3$adapted(akka.stream.stage.AsyncCallback scala.util.Try )}, serializedLambda);
                }
            };
            return new Tuple2<GraphStageLogic, Future<M>>((GraphStageLogic)((Object)logic), materialized.future());
        }

        public String toString() {
            return "FutureFlattenSource";
        }

        public FutureFlattenSource(Future<Graph<SourceShape<T>, M>> futureSource) {
            this.akka$stream$impl$fusing$GraphStages$FutureFlattenSource$$futureSource = futureSource;
            ReactiveStreamsCompliance$.MODULE$.requireNonNullElement(futureSource);
            this.out = Outlet$.MODULE$.apply("FutureFlattenSource.out");
            this.shape = new SourceShape<T>(this.out());
        }
    }

    public static final class FutureSource<T>
    extends GraphStage<SourceShape<T>> {
        private final Future<T> future;
        private final SourceShape<T> shape;
        private final Outlet<T> out;

        public Future<T> future() {
            return this.future;
        }

        @Override
        public SourceShape<T> shape() {
            return this.shape;
        }

        public Outlet<T> out() {
            return this.out;
        }

        @Override
        public Attributes initialAttributes() {
            return Stages$DefaultAttributes$.MODULE$.futureSource();
        }

        @Override
        public GraphStageLogic createLogic(Attributes attr) {
            return new OutHandler(this){
                private final /* synthetic */ FutureSource $outer;

                public void onDownstreamFinish() throws Exception {
                    OutHandler.onDownstreamFinish$(this);
                }

                public void onDownstreamFinish(Throwable cause) throws Exception {
                    OutHandler.onDownstreamFinish$(this, cause);
                }

                public void onPull() {
                    Option<Try<T>> option = this.$outer.future().value();
                    if (option instanceof Some) {
                        Some some = (Some)option;
                        Try completed2 = (Try)some.value();
                        this.onFutureCompleted$1(completed2);
                    } else if (None$.MODULE$.equals(option)) {
                        AsyncCallback<T> asyncCallback = this.getAsyncCallback((Function1<Try, Object> & Serializable)result -> {
                            FutureSource$$anon$10.$anonfun$onPull$1(this, result);
                            return BoxedUnit.UNIT;
                        });
                        Function1<Try, Object> & Serializable cb = (Function1<Try, Object> & Serializable)t2 -> {
                            FutureSource$$anon$10.$anonfun$onPull$2(asyncCallback, t2);
                            return BoxedUnit.UNIT;
                        };
                        this.$outer.future().onComplete(cb, ExecutionContexts$.MODULE$.parasitic());
                    } else {
                        throw new MatchError(option);
                    }
                    this.setHandler(this.$outer.out(), this.eagerTerminateOutput());
                }

                public static final /* synthetic */ void $anonfun$onPull$1(FutureSource$$anon$10 $this, Try result) {
                    $this.onFutureCompleted$1(result);
                }

                public static final /* synthetic */ void $anonfun$onPull$2(AsyncCallback eta$0$1$2, Try t2) {
                    eta$0$1$2.invoke(t2);
                }

                private final void onFutureCompleted$1(Try result) {
                    boolean bl = false;
                    Success success = null;
                    Try try_ = result;
                    if (try_ instanceof Success) {
                        bl = true;
                        success = (Success)try_;
                        T t2 = success.value();
                        if (t2 == null) {
                            this.completeStage();
                            return;
                        }
                    }
                    if (bl) {
                        T v = success.value();
                        this.emit(this.$outer.out(), v, () -> this.completeStage());
                        return;
                    }
                    if (try_ instanceof Failure) {
                        Failure failure = (Failure)try_;
                        Throwable t3 = failure.exception();
                        this.failStage(t3);
                        return;
                    }
                    throw new MatchError(try_);
                }
                {
                    if ($outer == null) {
                        throw null;
                    }
                    this.$outer = $outer;
                    super($outer.shape());
                    OutHandler.$init$(this);
                    this.setHandler($outer.out(), this);
                }

                private static /* synthetic */ Object $deserializeLambda$(SerializedLambda serializedLambda) {
                    return LambdaDeserialize.bootstrap("lambdaDeserialize", new MethodHandle[]{$anonfun$onPull$1$adapted(akka.stream.impl.fusing.GraphStages$FutureSource$$anon$10 scala.util.Try ), $anonfun$onPull$2$adapted(akka.stream.stage.AsyncCallback scala.util.Try ), $anonfun$onPull$3(akka.stream.impl.fusing.GraphStages$FutureSource$$anon$10 )}, serializedLambda);
                }
            };
        }

        public String toString() {
            return "FutureSource";
        }

        public FutureSource(Future<T> future) {
            this.future = future;
            ReactiveStreamsCompliance$.MODULE$.requireNonNullElement(future);
            this.shape = new SourceShape(Outlet$.MODULE$.apply("FutureSource.out"));
            this.out = ((SourceShape)this.shape()).out();
        }
    }

    public static final class IterableSource<T>
    extends GraphStage<SourceShape<T>> {
        private final Iterable<T> elements;
        private final Outlet<T> akka$stream$impl$fusing$GraphStages$IterableSource$$out;
        private final SourceShape<T> shape;

        public Iterable<T> elements() {
            return this.elements;
        }

        @Override
        public Attributes initialAttributes() {
            return Stages$DefaultAttributes$.MODULE$.iterableSource();
        }

        public Outlet<T> akka$stream$impl$fusing$GraphStages$IterableSource$$out() {
            return this.akka$stream$impl$fusing$GraphStages$IterableSource$$out;
        }

        @Override
        public SourceShape<T> shape() {
            return this.shape;
        }

        @Override
        public GraphStageLogic createLogic(Attributes inheritedAttributes) {
            return new OutHandler(this, inheritedAttributes){
                private Function1<Throwable, Supervision.Directive> decider;
                private Iterator<T> currentIterator;
                private volatile boolean bitmap$0;
                private final /* synthetic */ IterableSource $outer;
                private Attributes inheritedAttributes$1;

                public void onDownstreamFinish() throws Exception {
                    OutHandler.onDownstreamFinish$(this);
                }

                public void onDownstreamFinish(Throwable cause) throws Exception {
                    OutHandler.onDownstreamFinish$(this, cause);
                }

                private Function1<Throwable, Supervision.Directive> decider$lzycompute() {
                    IterableSource$$anon$7 var1_1 = this;
                    synchronized (var1_1) {
                        if (!this.bitmap$0) {
                            this.decider = ((ActorAttributes.SupervisionStrategy)this.inheritedAttributes$1.mandatoryAttribute(ClassTag$.MODULE$.apply(ActorAttributes.SupervisionStrategy.class))).decider();
                            this.bitmap$0 = true;
                        }
                    }
                    this.inheritedAttributes$1 = null;
                    return this.decider;
                }

                private Function1<Throwable, Supervision.Directive> decider() {
                    if (!this.bitmap$0) {
                        return this.decider$lzycompute();
                    }
                    return this.decider;
                }

                private Iterator<T> currentIterator() {
                    return this.currentIterator;
                }

                private void currentIterator_$eq(Iterator<T> x$1) {
                    this.currentIterator = x$1;
                }

                public void onPull() {
                    try {
                        if (this.currentIterator() == null) {
                            this.currentIterator_$eq(this.$outer.elements().iterator());
                        }
                        this.tryPushNextOrComplete();
                    }
                    catch (Throwable throwable) {
                        Option<Throwable> option;
                        Throwable throwable2 = throwable;
                        if (throwable2 != null && !(option = NonFatal$.MODULE$.unapply(throwable2)).isEmpty()) {
                            Throwable ex = option.get();
                            Supervision.Directive directive = this.decider().apply(ex);
                            if (Supervision$Stop$.MODULE$.equals(directive)) {
                                this.failStage(ex);
                            }
                            if (Supervision$Resume$.MODULE$.equals(directive)) {
                                this.tryPushNextOrComplete();
                            }
                            if (Supervision$Restart$.MODULE$.equals(directive)) {
                                this.currentIterator_$eq(this.$outer.elements().iterator());
                                this.tryPushNextOrComplete();
                            }
                            throw new MatchError(directive);
                        }
                        throw throwable;
                    }
                }

                private void tryPushNextOrComplete() {
                    if (this.currentIterator().hasNext()) {
                        if (this.isAvailable(this.$outer.akka$stream$impl$fusing$GraphStages$IterableSource$$out())) {
                            this.push(this.$outer.akka$stream$impl$fusing$GraphStages$IterableSource$$out(), this.currentIterator().next());
                        }
                        if (!this.currentIterator().hasNext()) {
                            this.completeStage();
                            return;
                        }
                        return;
                    }
                    this.completeStage();
                }
                {
                    if ($outer == null) {
                        throw null;
                    }
                    this.$outer = $outer;
                    this.inheritedAttributes$1 = inheritedAttributes$1;
                    super($outer.shape());
                    OutHandler.$init$(this);
                    this.setHandler($outer.akka$stream$impl$fusing$GraphStages$IterableSource$$out(), this);
                }
            };
        }

        public String toString() {
            return "IterableSource";
        }

        public IterableSource(Iterable<T> elements) {
            this.elements = elements;
            ReactiveStreamsCompliance$.MODULE$.requireNonNullElement(elements);
            this.akka$stream$impl$fusing$GraphStages$IterableSource$$out = Outlet$.MODULE$.apply("IterableSource.out");
            this.shape = new SourceShape<T>(this.akka$stream$impl$fusing$GraphStages$IterableSource$$out());
        }
    }

    private static class MonitorFlow<T>
    extends GraphStageWithMaterializedValue<FlowShape<T, T>, FlowMonitor<T>> {
        private final Inlet<T> in = Inlet$.MODULE$.apply("FlowMonitor.in");
        private final Outlet<T> out = Outlet$.MODULE$.apply("FlowMonitor.out");
        private final FlowShape<T, T> shape = FlowShape$.MODULE$.of(this.in(), this.out());

        public Inlet<T> in() {
            return this.in;
        }

        public Outlet<T> out() {
            return this.out;
        }

        @Override
        public FlowShape<T, T> shape() {
            return this.shape;
        }

        @Override
        public Tuple2<GraphStageLogic, FlowMonitor<T>> createLogicAndMaterializedValue(Attributes inheritedAttributes) {
            FlowMonitorImpl monitor = new FlowMonitorImpl();
            InHandler logic = new InHandler(this, monitor){
                private final /* synthetic */ MonitorFlow $outer;
                private final FlowMonitorImpl monitor$1;

                public void onDownstreamFinish() throws Exception {
                    OutHandler.onDownstreamFinish$(this);
                }

                public void onPush() {
                    T msg = this.grab(this.$outer.in());
                    this.push(this.$outer.out(), msg);
                    this.monitor$1.set(msg instanceof FlowMonitorState.StreamState ? new FlowMonitorState.Received<T>(msg) : msg);
                }

                public void onUpstreamFinish() {
                    InHandler.onUpstreamFinish$(this);
                    this.monitor$1.set(FlowMonitorState$Finished$.MODULE$);
                }

                public void onUpstreamFailure(Throwable ex) {
                    InHandler.onUpstreamFailure$(this, ex);
                    this.monitor$1.set(new FlowMonitorState.Failed(ex));
                }

                public void onPull() {
                    this.pull(this.$outer.in());
                }

                public void onDownstreamFinish(Throwable cause) {
                    OutHandler.onDownstreamFinish$(this, cause);
                    this.monitor$1.set(FlowMonitorState$Finished$.MODULE$);
                }

                public void postStop() {
                    FlowMonitorState.StreamState<T> streamState = this.monitor$1.state();
                    if (FlowMonitorState$Finished$.MODULE$.equals(streamState) ? true : streamState instanceof FlowMonitorState.Failed) {
                        return;
                    }
                    this.monitor$1.set(new FlowMonitorState.Failed(new AbruptStageTerminationException(this)));
                }

                public String toString() {
                    return "MonitorFlowLogic";
                }
                {
                    if ($outer == null) {
                        throw null;
                    }
                    this.$outer = $outer;
                    this.monitor$1 = monitor$1;
                    super($outer.shape());
                    InHandler.$init$(this);
                    OutHandler.$init$(this);
                    this.setHandler($outer.in(), this);
                    this.setHandler($outer.out(), this);
                }
            };
            return new Tuple2<GraphStageLogic, FlowMonitor<T>>((GraphStageLogic)((Object)logic), monitor);
        }

        public String toString() {
            return "MonitorFlow";
        }
    }

    @InternalApi
    public static abstract class SimpleLinearGraphStage<T>
    extends GraphStage<FlowShape<T, T>> {
        private final Inlet<T> in = Inlet$.MODULE$.apply(new StringBuilder(3).append(Logging$.MODULE$.simpleName(this)).append(".in").toString());
        private final Outlet<T> out = Outlet$.MODULE$.apply(new StringBuilder(4).append(Logging$.MODULE$.simpleName(this)).append(".out").toString());
        private final FlowShape<T, T> shape = new FlowShape<T, T>(this.in(), this.out());

        public Inlet<T> in() {
            return this.in;
        }

        public Outlet<T> out() {
            return this.out;
        }

        @Override
        public FlowShape<T, T> shape() {
            return this.shape;
        }
    }

    public static final class SingleSource<T>
    extends GraphStage<SourceShape<T>> {
        private final T elem;
        private final Outlet<T> out;
        private final SourceShape<T> shape;

        public T elem() {
            return this.elem;
        }

        @Override
        public Attributes initialAttributes() {
            return Stages$DefaultAttributes$.MODULE$.singleSource();
        }

        public Outlet<T> out() {
            return this.out;
        }

        @Override
        public SourceShape<T> shape() {
            return this.shape;
        }

        @Override
        public GraphStageLogic createLogic(Attributes attr) {
            return new OutHandler(this){
                private final /* synthetic */ SingleSource $outer;

                public void onDownstreamFinish() throws Exception {
                    OutHandler.onDownstreamFinish$(this);
                }

                public void onDownstreamFinish(Throwable cause) throws Exception {
                    OutHandler.onDownstreamFinish$(this, cause);
                }

                public void onPull() {
                    this.push(this.$outer.out(), this.$outer.elem());
                    this.completeStage();
                }
                {
                    if ($outer == null) {
                        throw null;
                    }
                    this.$outer = $outer;
                    super($outer.shape());
                    OutHandler.$init$(this);
                    this.setHandler($outer.out(), this);
                }
            };
        }

        public String toString() {
            return "SingleSource";
        }

        public SingleSource(T elem) {
            this.elem = elem;
            ReactiveStreamsCompliance$.MODULE$.requireNonNullElement(elem);
            this.out = Outlet$.MODULE$.apply("single.out");
            this.shape = new SourceShape<T>(this.out());
        }
    }

    public static final class TickSource<T>
    extends GraphStageWithMaterializedValue<SourceShape<T>, Cancellable> {
        private final FiniteDuration initialDelay;
        private final FiniteDuration interval;
        private final T tick;
        private final SourceShape<T> shape;
        private final Outlet<T> out;

        public FiniteDuration initialDelay() {
            return this.initialDelay;
        }

        public FiniteDuration interval() {
            return this.interval;
        }

        public T tick() {
            return this.tick;
        }

        @Override
        public SourceShape<T> shape() {
            return this.shape;
        }

        public Outlet<T> out() {
            return this.out;
        }

        @Override
        public Attributes initialAttributes() {
            return Stages$DefaultAttributes$.MODULE$.tickSource();
        }

        @Override
        public Tuple2<GraphStageLogic, Cancellable> createLogicAndMaterializedValue(Attributes inheritedAttributes) {
            Cancellable logic = new Cancellable(this){
                private final AtomicBoolean cancelled;
                private final AtomicReference<Option<AsyncCallback<BoxedUnit>>> cancelCallback;
                private final /* synthetic */ TickSource $outer;

                public AtomicBoolean cancelled() {
                    return this.cancelled;
                }

                public AtomicReference<Option<AsyncCallback<BoxedUnit>>> cancelCallback() {
                    return this.cancelCallback;
                }

                public void preStart() {
                    this.cancelCallback().set(new Some<AsyncCallback<T>>(this.getAsyncCallback((Function1<BoxedUnit, Object> & Serializable)x$1 -> {
                        TickSource$$anon$5.$anonfun$preStart$1(this, x$1);
                        return BoxedUnit.UNIT;
                    })));
                    if (this.cancelled().get()) {
                        this.completeStage();
                        return;
                    }
                    this.scheduleWithFixedDelay((Object)"TickTimer", this.$outer.initialDelay(), this.$outer.interval());
                }

                public void onTimer(Object timerKey) {
                    if (this.isAvailable(this.$outer.out()) && !this.isCancelled()) {
                        this.push(this.$outer.out(), this.$outer.tick());
                        return;
                    }
                }

                public boolean cancel() {
                    boolean success;
                    block0: {
                        boolean bl = success = !this.cancelled().getAndSet(true);
                        if (!success) break block0;
                        this.cancelCallback().get().foreach((Function1<AsyncCallback, Object> & Serializable)x$2 -> {
                            TickSource$$anon$5.$anonfun$cancel$1(x$2);
                            return BoxedUnit.UNIT;
                        });
                    }
                    return success;
                }

                public boolean isCancelled() {
                    return this.cancelled().get();
                }

                public String toString() {
                    return "TickSourceLogic";
                }

                public static final /* synthetic */ void $anonfun$preStart$1(TickSource$$anon$5 $this, BoxedUnit x$1) {
                    $this.completeStage();
                }

                public static final /* synthetic */ void $anonfun$cancel$1(AsyncCallback x$2) {
                    x$2.invoke(BoxedUnit.UNIT);
                }
                {
                    if ($outer == null) {
                        throw null;
                    }
                    this.$outer = $outer;
                    super($outer.shape());
                    this.cancelled = new AtomicBoolean(false);
                    this.cancelCallback = new AtomicReference<None$>(None$.MODULE$);
                    this.setHandler($outer.out(), this.eagerTerminateOutput());
                }

                private static /* synthetic */ Object $deserializeLambda$(SerializedLambda serializedLambda) {
                    return LambdaDeserialize.bootstrap("lambdaDeserialize", new MethodHandle[]{$anonfun$cancel$1$adapted(akka.stream.stage.AsyncCallback ), $anonfun$preStart$1$adapted(akka.stream.impl.fusing.GraphStages$TickSource$$anon$5 scala.runtime.BoxedUnit )}, serializedLambda);
                }
            };
            return new Tuple2<GraphStageLogic, Cancellable>((GraphStageLogic)((Object)logic), logic);
        }

        public String toString() {
            return new StringBuilder(16).append("TickSource(").append(this.initialDelay()).append(", ").append(this.interval()).append(", ").append(this.tick()).append(")").toString();
        }

        public TickSource(FiniteDuration initialDelay, FiniteDuration interval, T tick) {
            this.initialDelay = initialDelay;
            this.interval = interval;
            this.tick = tick;
            this.shape = new SourceShape(Outlet$.MODULE$.apply("TickSource.out"));
            this.out = ((SourceShape)this.shape()).out();
        }
    }
}

